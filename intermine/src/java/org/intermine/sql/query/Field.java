package org.flymine.sql.query;

/*
 * Copyright (C) 2002-2003 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

/**
 * A representation of a field in a table.
 *
 * @author Andrew Varley
 */
public class Field extends AbstractValue
{
    protected String name;
    protected AbstractTable table;

    /**
     * Constructor for this Field object.
     *
     * @param name the name of the field, as the database knows it
     * @param table the name of the AbstractTable to which the field belongs, as the database
     * knows it
     */
    public Field(String name, AbstractTable table) {
        if (name == null) {
            throw (new NullPointerException("Field names cannot be null"));
        }
        if (table == null) {
            throw (new NullPointerException("Cannot accept null values for table"));
        }
        this.name = name;
        this.table = table;
    }

    /**
     * Returns a String representation of this Field object, suitable for forming part of an SQL
     * query.
     *
     * @return the String representation
     */
    public String getSQLString() {
        return table.getAlias() + "." + name;
    }

    /**
     * Overrides Object.equals().
     *
     * @param obj an Object to compare to
     * @return true if the object is of the same class, and with the same name
     */
    public boolean equals(Object obj) {
        if (obj instanceof Field) {
            Field objField = (Field) obj;
            return name.equals(objField.name)
                && table.equals(objField.table);
        }
        return false;
    }

    /**
     * Overrides Object.hashcode().
     *
     * @return an arbitrary integer based on the contents of the Field
     */
    public int hashCode() {
        return name.hashCode() + table.hashCode();
    }

    /**
     * Compare this Field to another AbstractValue, including only the table alias.
     *
     * @param obj an AbstractField to compare to
     * @return true if the object is of the same class, and with the same value
     */

    public boolean equalsTableOnlyAlias(AbstractValue obj) {
        if (obj instanceof Field) {
            Field objField = (Field) obj;
            return name.equals(objField.name) && table.equalsOnlyAlias(objField.table);
        }
        return false;
    }

    /**
     * Returns the table of this field.
     *
     * @return table
     */
    public AbstractTable getTable() {
        return table;
    }
}
