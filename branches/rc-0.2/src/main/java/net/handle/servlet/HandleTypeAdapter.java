/*
 * Created by: christopher
 * Date: Nov 7, 2007
 * Time: 11:36:59 AM
 *
 * <p>Copyright (C) 2007 Nature Publishing Group, Inc.</p>
 *
 * <p><a rel="license" href="http://creativecommons.org/licenses/GPL/2.0/">
 * <img alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/GPL/2.0/88x62.png" /></a><br />
 * This work is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/GPL/2.0/">Creative Commons GNU
 * General Public License License</a>.</p>
 */
package net.handle.servlet;

import static net.handle.hdllib.Util.looksLikeBinary;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import net.handle.hdllib.HandleValue;

/**
 * <p>
 * TODO Document HandleTypeAdapter
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class HandleTypeAdapter {

    private boolean binary;

    private String type;

    /**
     * <p>
     * </p>
     *
     * @param value
     *            the handleValue to determine the type of and adapt.
     */
    public HandleTypeAdapter(HandleValue value) {
        type = value.getTypeAsString();
        binary = looksLikeBinary(value.getData());
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof HandleTypeAdapter)) {
            return false;
        }

        HandleTypeAdapter that = (HandleTypeAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                this.getType(), that.getType()).append(this.isAdmin(),
                that.isAdmin()).append(this.isAlias(), that.isAlias()).append(
                this.isBinary(), that.isBinary()).append(this.isPrimary(),
                that.isPrimary()).append(this.isService(), that.isService())
                .append(this.isSite(), that.isSite()).append(
                        this.isValueReferenceList(),
                        that.isValueReferenceList()).isEquals();
    }

    /**
     * <p>
     * Returns the type.
     * </p>
     *
     * @return the type
     */
    public String getType() {
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
        return new HashCodeBuilder(21, 35).append(getType()).append(isAdmin())
                .append(isAlias()).append(isBinary()).append(isPrimary())
                .append(isService()).append(isSite()).append(
                        isValueReferenceList()).toHashCode();
    }

    /**
     * <p>
     * Returns the admin.
     * </p>
     *
     * @return the admin
     */
    public boolean isAdmin() {
        return type.equals("HS_ADMIN");
    }

    /**
     * <p>
     * Returns the alias.
     * </p>
     *
     * @return the alias
     */
    public boolean isAlias() {
        return type.equals("HS_ALIAS");
    }

    /**
     * <p>
     * Returns the binary.
     * </p>
     *
     * @return the binary
     */
    public boolean isBinary() {
        return binary;
    }

    /**
     * <p>
     * Returns the primary.
     * </p>
     *
     * @return the primary
     */
    public boolean isPrimary() {
        return type.equals("HS_PRIMARY");
    }

    /**
     * <p>
     * Returns the service.
     * </p>
     *
     * @return the service
     */
    public boolean isService() {
        return type.equals("HS_SERV");
    }

    /**
     * <p>
     * Returns the site.
     * </p>
     *
     * @return the site
     */
    public boolean isSite() {
        return type.equals("HS_SITE");
    }

    /**
     * <p>
     * Returns the valueReferenceList.
     * </p>
     *
     * @return the valueReferenceList
     */
    public boolean isValueReferenceList() {
        return type.equals("HS_VLIST");
    }

    /**
     * <p>
     * Returns {@link HandleTypeAdapter#getType()}.
     * </p>
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getType();
    }
}
