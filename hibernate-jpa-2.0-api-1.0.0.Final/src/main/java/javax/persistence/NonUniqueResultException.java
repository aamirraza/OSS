// $Id: NonUniqueResultException.java 17752 2009-10-15 01:19:21Z steve.ebersole@jboss.com $
// EJB3 Specification Copyright 2004-2009 Sun Microsystems, Inc.
package javax.persistence;

/**
 * Thrown by the persistence provider when {@link
 * Query#getSingleResult Query.getSingleResult()} or {@link
 * TypedQuery#getSingleResult TypedQuery.getSingleResult()} is executed on a
 * query and there is more than one result from the query. This
 * exception will not cause the current transaction, if one is active,
 * to be marked for rollback.
 *
 * @see Query#getSingleResult()
 * @see TypedQuery#getSingleResult()
 *
 * @since Java Persistence 1.0
 */
public class NonUniqueResultException extends PersistenceException {

        /**
         * Constructs a new <code>NonUniqueResultException</code> exception
         * with <code>null</code> as its detail message.
         */
	public NonUniqueResultException() {
		super();
	}

        /**
         * Constructs a new <code>NonUniqueResultException</code> exception
         * with the specified detail message.
         * @param   message   the detail message.
         */
	public NonUniqueResultException(String message) {
		super(message);
	}
}
