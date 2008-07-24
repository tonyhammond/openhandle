/*
 * Created by: christopher
 * Date: 5 Oct 2007
 * Time: 10:07:10
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * TODO Document HandleResponseAdapter.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class HandleResponseAdapter {

    public static final String DEFAULT_FORMAT_URI = "http://nascent.nature.com/schemas/rdf/handle#";

    private static final Log LOG = LogFactory
            .getLog(HandleResponseAdapter.class);

    private Date date;

    private URI formatUri;

    private String handle;

    private List<HandleValueAdapter> values;

    /**
     * <p>
     * Default, no-argument constructor. Will initialize with <code>null</code>
     * fields, except the date which is set to now.
     * </p>
     */
    public HandleResponseAdapter() {
        date = new Date();
    }

    /**
     * <p>
     * Shortcut constructor. Will initialize with {@link #DEFAULT_FORMAT_URI}
     * and a new {@link java.util.Date}.
     * </p>
     *
     * @param handle
     * @param values
     */
    public HandleResponseAdapter(String handle, List<HandleValueAdapter> values) {
        this(handle, values, new Date(), DEFAULT_FORMAT_URI);
    }

    /**
     * <p>
     * </p>
     *
     * @param handle
     * @param values
     * @param date
     * @param formatUri
     */
    public HandleResponseAdapter(String handle,
            List<HandleValueAdapter> values, Date date, String formatUri) {
        this.date = date;
        this.handle = handle;
        this.values = values;
        try {
            this.formatUri = new URI(formatUri);
        } catch (URISyntaxException e) {
            LOG.error(e);
        }
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

        if (!(obj instanceof HandleResponseAdapter)) {
            return false;
        }

        HandleResponseAdapter that = (HandleResponseAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                getDate(), that.getDate()).append(getFormatUri(),
                that.getFormatUri()).append(getHandle(), that.getHandle())
                .append(getValues(), that.getValues()).isEquals();
    }

    /**
     * <p>
     * Returns the date.
     * </p>
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * <p>
     * Returns the formatUri.
     * </p>
     *
     * @return the formatUri
     */
    public URI getFormatUri() {
        return formatUri;
    }

    /**
     * <p>
     * Returns the handle.
     * </p>
     *
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * <p>
     * Returns the values.
     * </p>
     *
     * @return the values
     */
    public List<HandleValueAdapter> getValues() {
        return values;
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(27, 33).append(getDate()).append(
                getFormatUri()).append(getHandle()).append(getValues())
                .toHashCode();
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
