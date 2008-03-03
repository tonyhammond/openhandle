/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: Feb 28, 2008
 * Time: 9:50:05 AM
 *
 * <p>Copyright (C) 2008 Nature Publishing Group, Inc.</p>
 *
 * <p><abbr title="Nature Publishing Group">NPG</abbr> reserves all rights in this
 * program. The program or any portion thereof may not be reproduced in any form
 * whatsoever without the written consent of
 * <abbr title="Nature Publishing Group">NPG</abbr>.</p>
 *
 * <p>Nature Publishing Group is a division of Macmillan Publishers Limited, of
 * Brunel Road, Houndmills, Basingstoke, Hampshire, RG21 6XS. Registered Number
 * 785998 England.</p>
 */
package net.handle.servlet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * Template bean.
 * </p>
 *
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 */
public class Template {

    /**
     * <p>
     * Simple enumeration of template types. Used to indicate whether this
     * template should be used to display handle or error responses.
     * </p>
     *
     * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
     */
    public enum Type {
        ERROR_RESPONSE, HANDLE_RESPONSE;
    }

    private Mimetype mimetype;

    private String name;

    private Type type;

    /**
     * <p>
     * You must supply all properties to initialize a template.
     * </p>
     *
     * @param mimetype the mimetype
     * @param name the name
     * @param type the type
     */
    public Template(Mimetype mimetype, String name, Type type) {
        this.mimetype = mimetype;
        this.name = name;
        this.type = type;
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * <p>
     * Returns the  mimetype.
     * </p>
     *
     * @return the mimetype
     */
    public Mimetype getMimetype() {
        return mimetype;
    }

    /**
     * <p>
     * Returns the  name.
     * </p>
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Returns the  type.
     * </p>
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * <p>
     * Sets the mimetype.
     * </p>
     *
     * @param mimetype the mimetype to set
     */
    public void setMimetype(Mimetype mimetype) {
        this.mimetype = mimetype;
    }

    /**
     * <p>
     * Sets the name.
     * </p>
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Sets the type.
     * </p>
     *
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name + "." + mimetype.getRecommendedFileExtension();
    }

}
