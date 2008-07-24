/*
 * Created by: christopher
 * Date: Nov 8, 2007
 * Time: 12:21:23 PM
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
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
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
