/*
 * Created by: christopher
 * Date: 5 Oct 2007
 * Time: 10:17:15
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

import static net.handle.hdllib.Encoder.decodeAdminRecord;
import static net.handle.hdllib.Encoder.decodeSiteInfoRecord;
import static net.handle.hdllib.Encoder.decodeValueReferenceList;
import static net.handle.hdllib.Util.decodeHexString;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.handle.hdllib.AdminRecord;
import net.handle.hdllib.HandleException;
import net.handle.hdllib.HandleValue;
import net.handle.hdllib.SiteInfo;
import net.handle.hdllib.ValueReference;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * TODO Document HandleValueAdapter.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class HandleValueAdapter {

    /**
     * <p>
     * TODO Document Permission
     * </p>
     *
     * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
     */
    public enum Permission {
        ADMIN_READ, ADMIN_WRITE, PUBLIC_READ, PUBLIC_WRITE;

        /**
         * <p>
         * </p>
         *
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            String s = null;

            switch (this) {
            case ADMIN_READ:
                s = "adminRead";
                break;
            case ADMIN_WRITE:
                s = "adminWrite";
                break;
            case PUBLIC_READ:
                s = "publicRead";
                break;
            case PUBLIC_WRITE:
                s = "publicWrite";
                break;
            }

            return s;
        }
    }

    private AdminRecordAdapter admin;

    private HandleValue delegate;

    private Map<String, Boolean> permissions;

    private List<ValueReferenceAdapter> references;

    private SiteInfoAdapter site;

    private HandleTypeAdapter type;

    private List<ValueReferenceAdapter> valueReferences;

    /**
     * <p>
     * Instantiate the adapter for the supplied HandleValue.
     * </p>
     *
     * @param value the handleValue to adapt
     * @throws HandleException if an admin record could not be decoded
     */
    public HandleValueAdapter(HandleValue value) throws HandleException {
        delegate = value;
        type = new HandleTypeAdapter(value);

        if (type.isAdmin()) {
            AdminRecord record = new AdminRecord();
            decodeAdminRecord(value.getData(), 0, record);
            admin = new AdminRecordAdapter(record);
        } else if (type.isSite()) {
            SiteInfo info = new SiteInfo();
            decodeSiteInfoRecord(value.getData(), 0, info);
            site = new SiteInfoAdapter(info);
        } else if (type.isValueReferenceList()) {
            valueReferences = new ArrayList<ValueReferenceAdapter>();
            ValueReference[] references = decodeValueReferenceList(value
                    .getData(), 0);
            for (ValueReference v : references) {
                valueReferences.add(new ValueReferenceAdapter(v));
            }
        }

        ValueReference[] refs = value.getReferences();
        if (refs != null) {
            references = new ArrayList<ValueReferenceAdapter>();
            for (ValueReference r : refs) {
                references.add(new ValueReferenceAdapter(r));
            }
        }

        permissions = new HashMap<String, Boolean>();
        permissions.put(Permission.ADMIN_READ.toString(), value
                .getAdminCanRead());
        permissions.put(Permission.ADMIN_WRITE.toString(), value
                .getAdminCanWrite());
        permissions.put(Permission.PUBLIC_READ.toString(), value
                .getAnyoneCanRead());
        permissions.put(Permission.PUBLIC_WRITE.toString(), value
                .getAnyoneCanWrite());
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

        if (!(obj instanceof HandleValueAdapter)) {
            return false;
        }

        HandleValueAdapter that = (HandleValueAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                getAdmin(), that.getAdmin())
                .append(getAlias(), that.getAlias()).append(getData(),
                        that.getData()).append(getDate(), that.getDate())
                .append(getIndex(), that.getIndex()).append(getPermissions(),
                        that.getPermissions()).append(getPrimary(),
                        that.getPrimary()).append(getReferences(),
                        that.getReferences()).append(getService(),
                        that.getService()).append(getSite(), that.getSite())
                .append(getTimestamp(), that.getTimestamp()).append(getTtl(),
                        that.getTtl()).append(getTtlType(), that.getTtlType())
                .isEquals();
    }

    /**
     * <p>
     * Returns the admin.
     * </p>
     *
     * @return the admin
     */
    public AdminRecordAdapter getAdmin() {
        return admin;
    }

    /**
     * <p>
     * Returns the alias.
     * </p>
     *
     * @return the alias
     */
    public String getAlias() {
        return delegate.getDataAsString();
    }

    /**
     * <p>
     * Returns the data.
     * </p>
     *
     * @return the data
     */
    public String getData() {
        return (type.isBinary()) ? decodeHexString(delegate.getData(), false)
                : delegate.getDataAsString();
    }

    /**
     * <p>
     * Returns the timestamp.
     * </p>
     *
     * @return the timestamp
     * @see #getTimestamp()
     */
    public Date getDate() {
        return getTimestamp();
    }

    /**
     * <p>
     * Returns the index.
     * </p>
     *
     * @return the index
     */
    public Integer getIndex() {
        return delegate.getIndex();
    }

    /**
     * <p>
     * Returns the permissions.
     * </p>
     *
     * @return the permissions
     */
    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    /**
     * <p>
     * Returns the primary.
     * </p>
     *
     * @return the primary
     */
    public String getPrimary() {
        return delegate.getDataAsString();
    }

    /**
     * <p>
     * Returns the references.
     * </p>
     *
     * @return the references
     */
    public List<ValueReferenceAdapter> getReferences() {
        return references;
    }

    /**
     * <p>
     * Returns the service.
     * </p>
     *
     * @return the service
     */
    public String getService() {
        return delegate.getDataAsString();
    }

    /**
     * <p>
     * Returns the site.
     * </p>
     *
     * @return the site
     */
    public SiteInfoAdapter getSite() {
        return site;
    }

    /**
     * <p>
     * Returns the timestamp.
     * </p>
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return delegate.getTimestampAsDate();
    }

    /**
     * <p>
     * Returns the TTL.
     * </p>
     *
     * @return the TTL
     */
    public Integer getTtl() {
        return delegate.getTTL();
    }

    /**
     * <p>
     * Returns the TTLType.
     * </p>
     *
     * @return the TTLType
     */
    public String getTtlType() {
        return String.valueOf(delegate.getTTLType());
    }

    /**
     * <p>
     * Returns the type.
     * </p>
     *
     * @return the type
     */
    public HandleTypeAdapter getType() {
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
        return new HashCodeBuilder(23, 51).append(getAdmin())
                .append(getAlias()).append(getData()).append(getDate()).append(
                        getIndex()).append(getPermissions()).append(
                        getPrimary()).append(getReferences()).append(
                        getService()).append(getSite()).append(getTimestamp())
                .append(getTtl()).append(getTtlType()).toHashCode();
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
