package org.hibernate.ogm.datastore.redis.test.query;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.ogm.utils.OgmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QueryTest extends OgmTestCase {
	private Session session;
	private Transaction transaction;

	@Test
	public void testInsertData() {
		for(int i = 0;i < 100000; i++){
			// 插入第一条成绩记录
			ReportCard card1 = new ReportCard("语文" + i, "89.0");
			session.persist(card1);
			// 插入第二条成绩记录
			ReportCard card2 = new ReportCard("数学" + i, "99.5");
			session.persist(card2);
			// 插入第三条成绩记录
			ReportCard card3 = new ReportCard("英语" + i, "85.5");
			session.persist(card3);
			// 插入一条学生记录
			Student student = new Student();
			student.setAddress("四川省成都市成华区润新公寓1#209-" + i);
			student.setAge(i);
			student.setName("王佳怡");
			student.setPhoneNumber(i*2);
			student.setSex("女");
			// 插入成绩表
			List<ReportCard> reportCards = new ArrayList<ReportCard>();
			reportCards.add(card1);
			reportCards.add(card2);
			reportCards.add(card3);
			student.setReportCards(reportCards);
			session.persist(student);
		}
	}

	@Test
	public void testStudentById() {
		String id = "21c44101-e36a-489e-8dc0-8cc0e764a32f";
		Student student = session.get(Student.class, id);
		System.out.println(student);
	}

	@Test
	public void testMultiConditionWidthUnit() {
		long startTime = System.currentTimeMillis();
		StringBuffer sb = new StringBuffer();
			String xql = "from Student s where s.name=:name";
			Query query = session.createQuery(xql);
			query.setParameter("name","王佳怡");
			List<Student> students = query.list();
			if (students == null)
				System.out.println("数据没有查到哦");
			else
				for (Student student : students) {
					sb.append("姓名:" + student.getName() +" \t年龄:" + student.getAge() + "\t地址：" + student.getAddress() + "\t下面是学科成绩 :");
					for (ReportCard reportCard : student.getReportCards()) {
						sb.append(reportCard.getSubject() + ":" + reportCard.getScore() + "\t");
					}
				}
			System.out.println(sb.toString());
		long endTime = System.currentTimeMillis();
		System.out.println("你查询执行的事件为：time=" + (endTime - startTime) + " 毫秒");
	}
	
	@Test
	public void deleteStudentTest(){
	}

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] { Student.class, ReportCard.class };
	}

	@Before
	public void startTransaction() {
		session = sessions.openSession();
		transaction = session.getTransaction();
		transaction.begin();
	}

	@After
	public void commitTransaction() {
		transaction.commit();
		session.close();
	}
}
