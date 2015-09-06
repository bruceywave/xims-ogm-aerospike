/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.redis.test.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

/**
 * @author Gunnar Morling
 */
@Indexed
@Entity
public class Family {

	@Id
	private String id;
	@Field(analyze=Analyze.NO)
	private String name;

	@OneToMany
	private List<Plant> members;

	Family() {
	}

	public Family(String id, String name, Plant... members) {
		this.id = id;
		this.name = name;
		this.members = new ArrayList<Plant>(
				members != null ?
						Arrays.asList( members ) :
						Collections.<Plant>emptyList()
		);
	}

	public Family(String id, String name, List<Plant> memebers) {
		this.id = id;
		this.name = name;
		this.members = memebers;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMembers(List<Plant> members) {
		this.members = members;
	}

	public List<Plant> getMembers() {
		return members;
	}
}
