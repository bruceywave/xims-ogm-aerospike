/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.xinhuagroup.ogm.aerospike;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.ogm.datastore.redis.test.id.GuitarPlayer;

/**
 * @author Gunnar Morling
 */
@Entity
@Table(name="testPianoPlayer")
public class PianoPlayer {

	private String id;
	private String name;
	private GuitarPlayer guitarPlayer;

	@OneToOne
	public GuitarPlayer getGuitarPlayer() {
		return guitarPlayer;
	}

	public void setGuitarPlayer(GuitarPlayer guitarPlayer) {
		this.guitarPlayer = guitarPlayer;
	}

	PianoPlayer() {
	}

	public PianoPlayer(String name) {
		this.name = name;
	}
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
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
}
