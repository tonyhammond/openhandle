/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: Nov 7, 2007
 * Time: 4:47:59 PM
 *
 * <p>Copyright (C) 2007 Nature Publishing Group, Inc.</p>
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
import org.apache.commons.lang.builder.ToStringBuilder;

import net.handle.hdllib.Interface;

/**
 * <p>
 * TODO Document InterfaceAdapter
 * </p>
 * 
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
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
