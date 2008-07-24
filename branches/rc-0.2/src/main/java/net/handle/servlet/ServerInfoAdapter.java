/*
 * Created by: christopher
 * Date: Nov 7, 2007
 * Time: 3:01:10 PM
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

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.handle.hdllib.Interface;
import net.handle.hdllib.ServerInfo;

/**
 * <p>
 * TODO Document ServerInfoAdapter
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class ServerInfoAdapter {

    private static final Log LOG = LogFactory.getLog(ServerInfoAdapter.class);

    private ServerInfo delegate;

    private List<InterfaceAdapter> interfaces;

    public ServerInfoAdapter(ServerInfo info) {
        delegate = info;
        interfaces = new ArrayList<InterfaceAdapter>();
        for (Interface i : info.interfaces) {
            interfaces.add(new InterfaceAdapter(i));
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

        if (!(obj instanceof ServerInfoAdapter)) {
            return false;
        }

        ServerInfoAdapter that = (ServerInfoAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                this.getAddress(), that.getAddress()).append(this.getId(),
                that.getId())
                .append(this.getInterfaces(), that.getInterfaces()).append(
                        this.getPublicKey(), that.getPublicKey()).isEquals();
    }

    /**
     * <p>
     * Returns the address.
     * </p>
     *
     * @return the address
     */
    public String getAddress() {
        return delegate.getAddressString();
    }

    /**
     * <p>
     * Returns the id.
     * </p>
     *
     * @return the id
     */
    public Integer getId() {
        return delegate.serverId;
    }

    /**
     * <p>
     * Returns the interfaces.
     * </p>
     *
     * @return the interfaces
     */
    public List<InterfaceAdapter> getInterfaces() {
        return interfaces;
    }

    /**
     * <p>
     * Returns the publicKey.
     * </p>
     *
     * @return the publicKey or <code>null</code>
     */
    public PublicKey getPublicKey() {
        try {
            return delegate.getPublicKey();
        } catch (Exception e) {
            LOG.warn(e);
            return null;
        }
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(31, 79).append(getAddress()).append(getId())
                .append(getInterfaces()).append(getPublicKey()).toHashCode();
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
