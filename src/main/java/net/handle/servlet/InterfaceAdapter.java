/*
 * Created by: christopher
 * Date: Nov 7, 2007
 * Time: 4:47:59 PM
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import net.handle.hdllib.Interface;

/**
 * <p>
 * TODO Document InterfaceAdapter
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class InterfaceAdapter {

    private Interface delegate;

    /**
     * <p>
     * </p>
     *
     * @param face
     *            the interface
     */
    public InterfaceAdapter(Interface face) {
        delegate = face;
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

        if (!(obj instanceof InterfaceAdapter)) {
            return false;
        }

        InterfaceAdapter that = (InterfaceAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                this.getType(), that.getType()).append(this.getProtocol(),
                that.getProtocol()).append(this.getPort(), that.getPort())
                .isEquals();
    }

    /**
     * <p>
     * Returns the port.
     * </p>
     *
     * @return the port.
     */
    public Integer getPort() {
        return delegate.port;
    }

    /**
     * <p>
     * Returns the protocol.
     * </p>
     *
     * @return the protocol
     */
    public String getProtocol() {
        return Interface.protocolName(delegate.protocol);
    }

    /**
     * <p>
     * Returns the type.
     * </p>
     *
     * @return the type
     */
    public String getType() {
        return Interface.typeName(delegate.type);
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(9, 17).append(getPort()).append(
                getProtocol()).append(getType()).toHashCode();
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                OpenHandle.TO_STRING_STYLE);
    }

}
