/*
 * Created by: christopher
 * Date: Feb 28, 2008
 * Time: 9:50:05 AM
 *
 * <p>Copyright (C) 2008 Nature Publishing Group, Inc.</p>
 *
 * <p><a rel="license" href="http://creativecommons.org/licenses/GPL/2.0/">
 * <img alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/GPL/2.0/88x62.png" /></a><br />
 * This work is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/GPL/2.0/">Creative Commons GNU
 * General Public License License</a>.</p>
 */
package net.handle.servlet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * Template bean.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
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
