package org.flymine.sql.precompute;

/*
 * Copyright (C) 2002-2003 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.flymine.sql.query.Query;
import java.sql.SQLException;

/**
 * This object is an abstract superclass for a Best Query tracker. Queries can be added to these
 * objects, and they will keep track of them.
 *
 * @author Matthew Wakeling
 * @author Andrew Varley
 */
public abstract class BestQuery
{
    /**
     * Allows a Query to be added to this tracker.
     *
     * @param q a Query to be added to the tracker
     * @throws BestQueryException when adding should stop
     * @throws SQLException if error occurs in the underlying database
     */
    public abstract void add(Query q) throws BestQueryException, SQLException;

    /**
     * Allows a Query to be added to this tracker.
     *
     * @param q a query String to be added to the tracker
     * @throws BestQueryException when adding should stop
     * @throws SQLException if error occurs in the underlying database
     */
    public abstract void add(String q) throws BestQueryException, SQLException;
}
