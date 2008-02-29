/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: 5 Oct 2007
 * Time: 10:06:23
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

import java.util.HashMap;
import java.util.Map;

import net.handle.hdllib.AdminRecord;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * TODO Document AdminRecordAdapter.
 * </p>
 * 
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 */
public class AdminRecordAdapter {

    /**
     * <p>
     * TODO Document AdminPermission.
     * </p>
     * 
     * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
     */
    public enum AdminPermission {
        ADD_ADMIN, ADD_HANDLE, ADD_NA, ADD_VALUE, DELETE_HANDLE, DELETE_NA, LIST_HANDLES, MODIFY_ADMIN, MODIFY_VALUE, READ_VALUE, REMOVE_ADMIN, REMOVE_VALUE;

        @Override
        public String toString() {
            String s = null;

            switch (this) {
            case ADD_HANDLE:
                s = "addHandle";
                break;

            case DELETE_HANDLE:
                s = "deleteHandle";
                break;

            case ADD_NA:
                s = "addNa";
                break;

            case DELETE_NA:
                s = "deleteNa";
                break;

            case MODIFY_VALUE:
                s = "modifyValue";
                break;

            case REMOVE_VALUE:
                s = "removeValue";
                break;

            case ADD_VALUE:
                s = "addValue";
                break;

            case READ_VALUE:
                s = "readValue";
                break;

            case MODIFY_ADMIN:
                s = "modifyAdmin";
                break;

            case REMOVE_ADMIN:
                s = "removeAdmin";
                break;

            case ADD_ADMIN:
                s = "addAdmin";
                break;

            case LIST_HANDLES:
                s = "listHandles";
                break;

            default:
                s = null;
                break;
            }

            return s;
        }
    }

    private String id;

    private Integer index;

    private Map<String, Boolean> permissions;

    /**
     * <p>
     * </p>
     * 
     * @param record the admin record
     */
    public AdminRecordAdapter(AdminRecord record) {
        id = decodeString(record.adminId);
        index = record.adminIdIndex;
        permissions = toPermissionsMap(record);
    }

    /**
     * <p>
     * </p>
     * 
     * @param permission the permission
     * @param value the value
     * @return
     */
    public boolean addPermission(AdminPermission permission, boolean value) {
        if (permissions == null) {
            permissions = new HashMap<String, Boolean>();
        }

        return permissions.put(permission.toString(), value);
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

        if (!(obj instanceof AdminRecordAdapter)) {
            return false;
        }

        AdminRecordAdapter that = (AdminRecordAdapter) obj;

        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                getId(), that.getId()).append(getIndex(), that.getIndex())
                .append(getPermissions(), that.getPermissions()).isEquals();
    }

    /**
     * <p>
     * Returns the id.
     * </p>
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * <p>
     * Returns the index.
     * </p>
     * 
     * @return the index
     */
    public Integer getIndex() {
        return index;
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
     * </p>
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 23).append(getId()).append(getIndex())
                .append(getPermissions()).toHashCode();
    }

    /**
     * <p>
     * Extracts permissions data from a Handle admin record and returns it as a
     * map in which the key is a permission type and the value is a boolean
     * indicating whether the record has this permission.
     * </p>
     * 
     * @param record the record
     * @return the permissions map
     * @throws IllegalArgumentException if the permissions array in the record
     *             does not contain <strong>precisely</strong> 12 elements
     */
    public Map<String, Boolean> toPermissionsMap(AdminRecord record)
            throws IllegalArgumentException {
        /*
         * "AddHandle", "DeleteHandle", "AddNA", "DeleteNA", "ModifyValue",
         * "RemoveValue", "AddValue", "ReadValue", "ModifyAdmin", "RemoveAdmin",
         * "AddAdmin", "ListHandles",
         */

        /*
         * Permissions are stored by handle in a really idiotic array structure.
         * To make them useful, and represent them properly, we need to do a bit
         * of a hacky conversion to a map. This way we can actually determine
         * what named, enumerated permission each boolean permission value
         * refers to without having to peer at the internals of a particular
         * implementation.
         */
        Map<String, Boolean> permissions = new HashMap<String, Boolean>();
        if (record.perms.length == 12) {
            try {
                permissions.put(AdminPermission.ADD_HANDLE.toString(),
                        record.perms[0]);
                permissions.put(AdminPermission.DELETE_HANDLE.toString(),
                        record.perms[1]);
                permissions.put(AdminPermission.ADD_NA.toString(),
                        record.perms[2]);
                permissions.put(AdminPermission.DELETE_NA.toString(),
                        record.perms[3]);
                permissions.put(AdminPermission.MODIFY_VALUE.toString(),
                        record.perms[4]);
                permissions.put(AdminPermission.REMOVE_VALUE.toString(),
                        record.perms[5]);
                permissions.put(AdminPermission.ADD_VALUE.toString(),
                        record.perms[6]);
                permissions.put(AdminPermission.READ_VALUE.toString(),
                        record.perms[7]);
                permissions.put(AdminPermission.MODIFY_ADMIN.toString(),
                        record.perms[8]);
                permissions.put(AdminPermission.REMOVE_ADMIN.toString(),
                        record.perms[9]);
                permissions.put(AdminPermission.ADD_ADMIN.toString(),
                        record.perms[10]);
                permissions.put(AdminPermission.LIST_HANDLES.toString(),
                        record.perms[11]);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Invalid permissions array in admin record.", e);
            }
        } else {
            throw new IllegalArgumentException(
                    "Invalid permissions array in admin record: there are "
                            + record.perms.length
                            + " entries. 12 is the required number.");
        }

        return permissions;
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
