package org.flymine.objectstore.query;

/*
 * Copyright (C) 2002-2003 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.lang.reflect.Field;

import org.flymine.util.TypeUtil;

/**
 * Represents a field of a QueryClass that is a business object
 *
 * @author Mark Woodbridge
 * @author Richard Smith
 */
public class QueryObjectReference extends QueryReference
{
    /**
     * Constructs a QueryObjectReference representing the specified field of a QueryClass
     *
     * @param qc the QueryClass
     * @param fieldName the name of the relevant field
     * @throws NullPointerException if the field name is null
     * @throws NoSuchFieldException if the field does not exist
     * @throws IllegalArgumentException if the field is a collection
     */    
    public QueryObjectReference(QueryClass qc, String fieldName) 
        throws NullPointerException, NoSuchFieldException, IllegalArgumentException {
        if (fieldName == null) {
            throw new NullPointerException("Field name parameter is null");
        }
        Field field = TypeUtil.getField(qc.getType(), fieldName);
        if (field == null) {
            throw new NoSuchFieldException("Field " + fieldName + " not found in "
                                           + qc.getType());
        }
        if (java.util.Collection.class.isAssignableFrom(field.getType())) {
            throw new IllegalArgumentException("Field " + fieldName + " is a collection type");
        }
        if (Number.class.isAssignableFrom(field.getType())
                || String.class.isAssignableFrom(field.getType())
                || Boolean.class.isAssignableFrom(field.getType())
                || java.util.Date.class.isAssignableFrom(field.getType())
                || field.getType().isPrimitive()) {
            throw new IllegalArgumentException("Field " + fieldName + " is not a separate database "
                    + "object");
        }
        this.qc = qc;
        this.fieldName = fieldName;
        this.type = field.getType();
    }
}
