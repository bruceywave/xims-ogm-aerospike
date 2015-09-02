/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.redis.test.id;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.ogm.OgmSession;
import org.hibernate.ogm.datastore.spi.DatastoreProvider;
import org.hibernate.ogm.utils.OgmTestCase;
import org.junit.Test;

import com.xinhuagroup.ogm.aerospike.PianoPlayer;
import com.xinhuagroup.ogm.aerospike.impl.AerospikeDatastoreProvider;

/**
 * Tests for id generators in Redis
 *
 * @author Mark Paluch
 */
public class TableGeneratorTest extends OgmTestCase {

	@Test
	public void canUseSpecificValueColumNames() {
		OgmSession session = openSession();
		Transaction tx = session.beginTransaction();

		// given
		GuitarPlayer buck = new GuitarPlayer("被应用类" );
		session.persist(buck);
		PianoPlayer ken = new PianoPlayer( "引用类" );
		ken.setGuitarPlayer(buck);
		// when
		session.persist( ken );
		List<PianoPlayer> pianoPlayers = buck.getPianoPlayers();
		pianoPlayers.add(ken);
		buck.setPianoPlayers(pianoPlayers);
//		tx.commit();
//		session.clear();
//		tx = session.beginTransaction();
//		ken = (PianoPlayer) session.load( PianoPlayer.class, ken.getId() );
		
//		GuitarPlayer buck = (GuitarPlayer) session.get(GuitarPlayer.class, "5ca41bf9-1f7e-4e68-ae13-3c7a2a0e10b7");
		tx.commit();
		System.out.println(buck.getName());
		session.close();
	}


	private AerospikeDatastoreProvider getProvider() {
		return (AerospikeDatastoreProvider) sfi()
				.getServiceRegistry()
				.getService( DatastoreProvider.class );
	}

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {PianoPlayer.class, GuitarPlayer.class};
	}
}
