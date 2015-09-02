/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.redis.test.id;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.GenericGenerator;

import com.xinhuagroup.ogm.aerospike.PianoPlayer;

/**
 * @author Gunnar Morling
 */
@Entity
@Table(name="testPlayer")
public class GuitarPlayer {

	private String id;
	private String name;
	private List<PianoPlayer> pianoPlayers = new ArrayList<PianoPlayer>(0);
	GuitarPlayer() {
	}

	public GuitarPlayer(String name) {
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

	@OneToMany
	public List<PianoPlayer> getPianoPlayers() {
		return pianoPlayers;
	}

	public void setPianoPlayers(List<PianoPlayer> pianoPlayers) {
		this.pianoPlayers = pianoPlayers;
	}
}
