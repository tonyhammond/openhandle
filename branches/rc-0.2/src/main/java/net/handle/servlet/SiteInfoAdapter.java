/*
 * Created by: christopher
 * Date: Nov 7, 2007
 * Time: 2:48:39 PM
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.handle.hdllib.Attribute;
import net.handle.hdllib.ServerInfo;
import net.handle.hdllib.SiteInfo;

/**
 * <p>
 * TODO Document SiteInfoAdapter
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class SiteInfoAdapter {

    /**
     * <p>
     * TODO Document HashOption
     * </p>
     *
     * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
     */
    public enum HashOption {
        HASH_TYPE_BY_ALL, HASH_TYPE_BY_ID, HASH_TYPE_BY_NA;

        /**
         * <p>
         * </p>
         *
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            String s = "";

            switch (this) {
            case HASH_TYPE_BY_NA:
                s = "HASH_TYPE_BY_NA";
                break;

            case HASH_TYPE_BY_ID:
                s = "HASH_TYPE_BY_ID";
                break;

            case HASH_TYPE_BY_ALL:
                s = "HASH_TYPE_BY_ALL";
                break;
            }

            return s;
        }
    }

    private static final Log LOG = LogFactory.getLog(SiteInfoAdapter.class);

    private Map<String, String> attributes;

    private SiteInfo delegate;

    private List<ServerInfoAdapter> servers;

    /**
     * <p>
     * </p>
     *
     * @param info
     *            the site info
     */
    public SiteInfoAdapter(SiteInfo info) {
        try {
            delegate = info;
            attributes = new HashMap<String, String>();
            for (Attribute a : info.attributes) {
                attributes.put(decodeString(a.name), decodeString(a.value));
            }

            for (ServerInfo s : info.servers) {
                servers.add(new ServerInfoAdapter(s));
            }
        } catch (NullPointerException e) {
            LOG.warn(e);
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

        if (!(obj instanceof SiteInfoAdapter)) {
            return false;
        }

        SiteInfoAdapter that = (SiteInfoAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                getAttributes(), that.getAttributes()).append(
                this.getDataFormatVersion(), that.getDataFormatVersion())
                .append(this.getHashFilter(), that.getHashFilter()).append(
                        this.getHashOption(), that.getHashOption()).append(
                        this.getMajorProtocolVersion(),
                        that.getMajorProtocolVersion()).append(
                        this.getMinorProtocolVersion(),
                        that.getMinorProtocolVersion()).append(
                        this.getSerialNumber(), that.getSerialNumber()).append(
                        this.getServers(), that.getServers()).append(
                        this.isMultiPrimary(), that.isMultiPrimary()).append(
                        this.isPrimary(), that.isPrimary()).isEquals();
    }

    /**
     * <p>
     * Returns the attributes.
     * </p>
     *
     * @return the attributes
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * <p>
     * Returns the dataFormatVersion.
     * </p>
     *
     * @return the dataFormatVersion
     */
    public int getDataFormatVersion() {
        return delegate.dataFormatVersion;
    }

    /**
     * <p>
     * Returns the hashFilter.
     * </p>
     *
     * @return the hashFilter
     */
    public String getHashFilter() {
        return decodeString(delegate.hashFilter);
    }

    /**
     * <p>
     * Returns the hashOption.
     * </p>
     *
     * @return the hashOption
     */
    public HashOption getHashOption() {
        HashOption h = null;

        switch (delegate.hashOption) {
        case 0:
            h = HashOption.HASH_TYPE_BY_NA;
            break;
        case 1:
            h = HashOption.HASH_TYPE_BY_ID;
            break;
        case 2:
            h = HashOption.HASH_TYPE_BY_ALL;
            break;
        }

        return h;
    }

    /**
     * <p>
     * Returns the majorProtocolVersion.
     * </p>
     *
     * @return the majorProtocolVersion
     */
    public String getMajorProtocolVersion() {
        return String.valueOf(delegate.majorProtocolVersion);
    }

    /**
     * <p>
     * Returns the minorProtocolVersion.
     * </p>
     *
     * @return the minorProtocolVersion
     */
    public String getMinorProtocolVersion() {
        return String.valueOf(delegate.minorProtocolVersion);
    }

    /**
     * <p>
     * Returns the serialNumber.
     * </p>
     *
     * @return the serialNumber
     */
    public Integer getSerialNumber() {
        return delegate.serialNumber;
    }

    /**
     * <p>
     * Returns the servers.
     * </p>
     *
     * @return the servers
     */
    public List<ServerInfoAdapter> getServers() {
        return servers;
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 29).append(getAttributes()).append(
                getDataFormatVersion()).append(getHashFilter()).append(
                getHashOption()).append(getMajorProtocolVersion()).append(
                getMinorProtocolVersion()).append(getSerialNumber()).append(
                getServers()).append(isMultiPrimary()).append(isPrimary())
                .toHashCode();
    }

    /**
     * <p>
     * Returns the multiPrimary.
     * </p>
     *
     * @return the multiPrimary
     */
    public Boolean isMultiPrimary() {
        return delegate.multiPrimary;
    }

    /**
     * <p>
     * Returns the primary.
     * </p>
     *
     * @return the primary
     */
    public Boolean isPrimary() {
        return delegate.isPrimary;
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
