// $Id: MapJoin.java 17752 2009-10-15 01:19:21Z steve.ebersole@jboss.com $
// EJB3 Specification Copyright 2004-2009 Sun Microsystems, Inc.
package javax.persistence.criteria;

import java.util.Map;
import javax.persistence.metamodel.MapAttribute;

/**
 * The <code>MapJoin</code> interface is the type of the result of
 * joining to a collection over an association or element
 * collection that has been specified as a <code>java.util.Map</code>.
 *
 * @param <Z> the source type of the join
 * @param <K> the type of the target Map key
 * @param <V> the type of the target Map value
 * @since Java Persistence 2.0
 */
public interface MapJoin<Z, K, V> extends PluralJoin<Z, Map<K, V>, V> {
	/**
	 * Return the metamodel representation for the map attribute.
	 *
	 * @return metamodel type representing the <code>Map</code> that is
	 *         the target of the join
	 */
	MapAttribute<? super Z, K, V> getModel();

	/**
	 * Create a path expression that corresponds to the map key.
	 *
	 * @return path corresponding to map key
	 */
	Path<K> key();

	/**
	 * Create a path expression that corresponds to the map value.
	 * This method is for stylistic use only: it just returns this.
	 *
	 * @return path corresponding to the map value
	 */
	Path<V> value();

	/**
	 * Create an expression that corresponds to the map entry.
	 *
	 * @return expression corresponding to the map entry
	 */
	Expression<Map.Entry<K, V>> entry();
}