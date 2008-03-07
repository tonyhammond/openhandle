/*
 * Created by: christopher
 * Date: 2 Mar 2008
 * Time: 12:41:13
 *
 * <p>Copyright (C) 2008 Nature Publishing Group, Inc.</p>
 *
 * <p><a rel="license" href="http://creativecommons.org/licenses/GPL/2.0/">
 * <img alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/GPL/2.0/88x62.png" /></a><br />
 * This work is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/GPL/2.0/">Creative Commons GNU
 * General Public License License</a>.</p>
 */

package net.handle.servlet;

import java.util.ArrayList;
import java.util.List;

import net.handle.hdllib.Util;

/**
 * <p>
 * Enumeration of Handle Record Types.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public enum HandleRecordType {

    HS_ADMIN, HS_ALIAS, HS_NA_DELEGATE, HS_PRIMARY, HS_SERV, HS_SITE, HS_VLIST;

    /**
     * <p>
     * Returns the array of handle record type names encoded as an array of byte
     * arrays. If the supplied string array is <code>null</code> or of 0 length,
     * then null is returned. Likewise, if none of the supplied typeNames is
     * a valid type name (determined using {@link #forName(String)}), then
     * <code>null</code> is returned.
     * </p>
     *
     * @param types the type names to encode
     * @return the type names encoded as an array of byte arrays or <code>null</code>
     */
    public static byte[][] encode(HandleRecordType[] types) {
        if (types != null && types.length > 0) {
            List<byte[]> transitionalList = new ArrayList<byte[]>();
            for (HandleRecordType type : types) {
                if (type != null) {
                    transitionalList.add(type.toByteArray());
                }
            }

            return transitionalList.toArray(new byte[transitionalList.size()][]);
        } else {
            return null;
        }
    }

    /**
     * <p>
     * Returns the handle record type for specified handle record type name.
     * N.B. the supplied string must match those produced by {@link #toString()}
     * in this enumeration.
     * </p>
     *
     * @param name the name
     * @return the handle record type
     */
    public static HandleRecordType forName(String name) {
        for (HandleRecordType value : values()) {
            if (value.toString().equals(name)) {
                return value;
            }
        }

        return null;
    }

    /**
     * <p>
     * Returns this handle record type encoded as a byte array.
     * </p>
     *
     * @return this type encoded as a byte array
     */
    public byte[] toByteArray() {
        return Util.encodeString(this.toString());
    }

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
        case HS_ADMIN:
            s = "HS_ADMIN";
            break;
        case HS_ALIAS:
            s = "HS_ALIAS";
            break;
        case HS_NA_DELEGATE:
            s = "HS_NA_DELEGATE";
            break;
        case HS_PRIMARY:
            s = "HS_PRIMARY";
            break;
        case HS_SERV:
            s = "HS_SERV";
            break;
        case HS_SITE:
            s = "HS_SITE";
            break;
        case HS_VLIST:
            s = "HS_VLIST";
            break;
        }

        return s;
    }

}
