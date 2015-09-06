/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.redis.test.mapping;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.ogm.OgmSession;
import org.hibernate.ogm.datastore.spi.DatastoreProvider;
import org.hibernate.ogm.utils.OgmTestCase;
import org.junit.Test;

import com.xinhuagroup.ogm.aerospike.impl.AerospikeDatastoreProvider;

/**
 * Base for tests.
 *
 * @author Mark Paluch
 */
public class RedisJsonMappingTest extends OgmTestCase {

	
	@SuppressWarnings("unused")
	@Test
	public void canStoreAndLoadEntitiesWithIdGeneratorAndAssociation() {
		OgmSession session = openSession();
//		session.getTransaction().begin();
//		Family loadedFamily = (Family) session.get( Family.class, "yangbo123" );
//		List<Plant> plants = null;
//		if(loadedFamily == null){
//			plants = new ArrayList<Plant>();
//			loadedFamily = new Family("yangbo123", "yangbo", plants);
//		}else{
//			 plants = loadedFamily.getMembers();
//		}
//		// given
//		Plant ficus = new Plant( 181 );
//		session.persist( ficus );
//		
//		plants.add(ficus);
//		Plant  dd= new Plant( 186 );
//		session.persist( dd );
//		plants.add(dd);
//		
//		loadedFamily.setMembers(plants);
//		session.persist(loadedFamily);
//		session.getTransaction().commit();
//
//		// when
//		session.getTransaction().begin();
//		Family loadedFamily = (Family) session.get( Family.class, "yangbo123" );
//		event.internal.DefaultLoadEventListener:142 - HHH000327: Error performing load command : 
//			org.hibernate.PropertyAccessException: Could not set field value [181] value by reflection : 
//				[class org.hibernate.ogm.datastore.redis.test.mapping.Plant.height] setter of 
//				org.hibernate.ogm.datastore.redis.test.mapping.Plant.height
		// then
//		assertThat( loadedFamily ).isNotNull();
//		System.out.println(loadedFamily.getMembers());
//		session.getTransaction().commit();
		
		
		session.getTransaction().begin();
		List<?> results = null;
		try {
			Query query = session.createQuery("from Family h where h.name=:name");
			query.setParameter("name", "yangbo");
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(results);
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void canStoreAndLoadEntities() {
		OgmSession session = openSession();
		session.getTransaction().begin();

		// given
		Donut donut = new Donut( "homers-donut", 7.5, Donut.Glaze.Pink, "pink-donut" );
		session.persist( donut );


		session.getTransaction().commit();

		// when
		session.getTransaction().begin();
		Donut loadedDonut = (Donut) session.get( Donut.class, "homers-donut" );

		// then
		assertThat( loadedDonut ).isNotNull();
		assertThat( loadedDonut.getId() ).isEqualTo( "homers-donut" );
		assertThat( loadedDonut.getGlaze() ).isEqualTo( Donut.Glaze.Pink );
		assertThat( loadedDonut.getAlias() ).isEqualTo( "pink-donut" );

		session.getTransaction().commit();

		session.close();
	}

	@Test
	public void verifyRedisRepresentation()  {
//		OgmSession session = openSession();
//		session.getTransaction().begin();
//
//		// given
//		Donut donut = new Donut( "homers-donut", 7.5, Donut.Glaze.Pink, "pink-donut" );
//		session.persist( donut );
//
//		session.getTransaction().commit();
//
//		// when
//		String representation = new String( getConnection().get( "Donut:homers-donut".getBytes() ) );
//
//		// then
//		JSONAssert.assertEquals( "{'alias':'pink-donut','radius':7.5,'glaze':2}", representation, JSONCompareMode.STRICT );
//
//		session.close();
	}
//
//	protected RedisConnection<byte[], byte[]> getConnection() {
//		return getProvider().getConnection();
//	}


	private AerospikeDatastoreProvider getProvider() {
		return (AerospikeDatastoreProvider) sfi()
				.getServiceRegistry()
				.getService( DatastoreProvider.class );
	}

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {Family.class, Plant.class, Donut.class};
	}
}
