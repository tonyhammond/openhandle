/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: Nov 8, 2007
 * Time: 12:21:23 PM
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

import static net.handle.hdllib.Util.decodeString;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import net.handle.hdllib.ValueReference;

/**
 * <p>
 * TODO Document ValueReferenceAdapter
 * </p>
 * 
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 */
public class ValueReferenceAdapter {

    private ValueReference delegate;

    /**
     * <p>
     * </p>
     * 
     * @param value
     *            the value
     */
    public ValueReferenceAdapter(ValueReference value) {
        delegate = value;
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

        if (!(obj instanceof ValueReferenceAdapter)) {
            return false;
        }

        ValueReferenceAdapter that = (ValueReferenceAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                this.getHandle(), that.getHandle()).append(this.getIndex(),
                that.getIndex()).isEquals();
    }

    /**
     * <p>
     * Returns the handle.
     * </p>
     * 
     * @return the handle
     */
    public String getHandle() {
        return decodeString(delegate.handle);
    }

    /**
     * <p>
     * Returns the index.
     * </p>
     * 
     * @return the index
     */
    public Integer getIndex() {
        return delegate.index;
    }

    /**
     * <p>
     * </p>
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 11).append(getHandle())
                .append(getIndex()).toHashCode();
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
