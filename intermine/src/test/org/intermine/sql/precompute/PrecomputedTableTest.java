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

import junit.framework.*;
import org.flymine.sql.query.*;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PrecomputedTableTest extends TestCase
{
    private PrecomputedTable pt1, pt2, pt3, pt4;

    public PrecomputedTableTest(String arg1) {
        super(arg1);
    }

    public void setUp()
    {
        Query q1 = new Query();
        Table t = new Table("mytable");
        Constant c = new Constant("1");
        Field f = new Field("a", t);
        SelectValue sv = new SelectValue(f, null);
        q1.addFrom(t);
        q1.addSelect(sv);
        q1.addWhere(new Constraint(f, Constraint.EQ, c));

        Query q2 = new Query();
        t = new Table("anothertable");
        c = new Constant("2");
        f = new Field("b", t);
        sv = new SelectValue(f, null);
        q2.addFrom(t);
        q2.addSelect(sv);
        q2.addWhere(new Constraint(f, Constraint.LT, c));

        pt1 = new PrecomputedTable(q1, "precomp1");
        pt2 = new PrecomputedTable(q1, "precomp1");
        pt3 = new PrecomputedTable(q1, "precomp2");
        pt4 = new PrecomputedTable(q2, "precomp2");

    }

    public void testPrecomputedTableWithNullName() throws Exception {
        try {
            PrecomputedTable pt = new PrecomputedTable(new Query(), null);
            fail("Expected: NullPointerException");
        }
        catch (NullPointerException e) {
        }
    }

    public void testPrecomputedTableWithNullQuery() throws Exception {
        try {
            PrecomputedTable pt = new PrecomputedTable(null, "precomp");
            fail("Expected: NullPointerException");
        }
        catch (NullPointerException e) {
        }
    }

    public void testSQLString() throws Exception {
        String createString = "CREATE TABLE precomp1 AS SELECT mytable.a FROM mytable WHERE mytable.a = 1";
        assertEquals(createString, pt1.getSQLString());
    }


    public void testEquals() throws Exception {
        assertEquals(pt1, pt1);
        assertEquals(pt1, pt2);
        assertTrue("Expected pt1 not to equal pt3", !pt1.equals(pt3));
        assertTrue("Expected pt1 not to equal pt4", !pt1.equals(pt4));
        assertTrue("Expected pt3 not to equal pt4", !pt3.equals(pt4));
        assertTrue("Expected pt1 not to equal null", !pt1.equals(null));
    }

    public void testHashCode() throws Exception {
        assertEquals(pt1.hashCode(), pt1.hashCode());
        assertEquals(pt1.hashCode(), pt2.hashCode());
        assertTrue("Expected pt1.hashCode() not to equal pt3.hashCode()",
                   !(pt1.hashCode() == pt3.hashCode()));
        assertTrue("Expected pt1.hashCode() not to equal pt4.hashCode()",
                   !(pt1.hashCode() == pt4.hashCode()));
        assertTrue("Expected pt1.hashCode() not to equal pt5.hashCode()",
                   !(pt3.hashCode() == pt4.hashCode()));
    }

    public void testValueMap() throws Exception {
        Set values = new HashSet();
        AbstractValue v1 = new Constant("'c1'");
        AbstractValue v2 = new Constant("'c2'");
        AbstractValue v3 = new Constant("'c3'");
        SelectValue s1 = new SelectValue(v1, "alias1");
        SelectValue s2 = new SelectValue(v2, "alias2");
        SelectValue s3 = new SelectValue(v3, "alias3");
        values.add(s1);
        values.add(s2);
        values.add(s3);

        Map result = new HashMap();
        result.put(v1, s1);
        result.put(v2, s2);
        result.put(v3, s3);

        Query q1 = new Query("SELECT 'c1' AS alias1, 'c2' AS alias2, 'c3' AS alias3 FROM table");
        PrecomputedTable pt = new PrecomputedTable(q1, "name");
        assertEquals(result, pt.getValueMap());
    }

}
