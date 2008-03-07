/*
 * Created by: christopher
 * Date: Nov 7, 2007
 * Time: 10:52:42 AM
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

/**
 * <p>
 * TODO Document HandleExceptionAdapter
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class HandleExceptionAdapter {
    private int code;

    private String message;

    /**
     * <p>
     * Default, no-argument constructor. Initializes with <code>null</code>
     * fields.
     * </p>
     */
    public HandleExceptionAdapter() {
        // do nothing
    }

    /**
     * <p>
     * </p>
     *
     * @param code
     * @param message
     */
    public HandleExceptionAdapter(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * <p>
     * Returns the code.
     * </p>
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * <p>
     * Returns the message.
     * </p>
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>
     * Sets the code.
     * </p>
     *
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * <p>
     * Sets the message.
     * </p>
     *
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
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

        if (!(obj instanceof HandleExceptionAdapter)) {
            return false;
        }

        HandleExceptionAdapter that = (HandleExceptionAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.getCode(), that.getCode())
                .append(this.getMessage(), that.getMessage()).isEquals();
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(15, 3).append(getMessage())
                .append(getCode()).toHashCode();
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
