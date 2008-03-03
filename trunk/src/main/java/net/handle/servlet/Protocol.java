/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: 2 Mar 2008
 * Time: 19:14:41
 *
 * <p>Copyright (C) 2008 Nature Publishing Group, Inc.</p>
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

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import net.handle.hdllib.Interface;

/**
 * <p>
 * Enumeration of Handle client protocols.
 * </p>
 *
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 */
public enum Protocol {

    HTTP, TCP, UDP;

    /**
     * <p>
     * Returns a typed Protocol for the supplied string representation. NB. the
     * supplied string must match that produced by {@link #toString()}.
     * </p>
     *
     * @param protocol the protocol name
     * @return the protocol
     */
    public static Protocol forName(String protocol) {
        for (Protocol value : values()) {
            if (value.toString().equals(protocol)) {
                return value;
            }
        }

        return null;
    }

    /**
     * <p>
     * Returns a typed Protocol for the supplied int representation. NB. the
     * supplied int must match that produced by {@link #toInt()}.
     * </p>
     *
     * @param protocol the protocol number
     * @return the protocol
     */
    public static Protocol forInt(int protocol) {
        for (Protocol value : values()) {
            if (value.toInt() == protocol) {
                return value;
            }
        }

        return null;
    }

    /**
     * <p>
     * Utility method to output a string representation of an int array of
     * protocols.
     * </p>
     *
     * @param protocols the protocols
     * @return a friendly string output to see what the array really means
     */
    public static String toString(int[] protocols) {
        if (protocols == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();

        if (protocols != null && protocols.length > 0) {
            for (int i = 0; i < protocols.length; i++) {
                Protocol p = forInt(protocols[i]);
                if (p != null) {
                    if (i == (protocols.length - 1)) {
                        sb.append(p.toString());
                    } else {
                        sb.append(p.toString() + ", ");
                    }
                }
            }
        }

        if (isNotBlank(sb.toString())) {
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * <p>
     * Returns an array of ints for the supplied list of protocol names. Invalid
     * protocol names are excluded. All nulls are removed. The resulting array
     * is guaranteed to contain only valid, non-nulls.
     * </p>
     *
     * @param protocols the protocol names
     * @return the protocol ints
     */
    public static int[] toIntArrayFromList(List<String> protocols) {
        List<Integer> integerList = new ArrayList<Integer>();
        if (protocols != null && !(protocols.isEmpty())) {
            for (String s : protocols) {
                Protocol p = Protocol.forName(s);
                if (p != null) {
                    integerList.add(p.toInt());
                }
            }
        }

        int[] integerArray = new int[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            integerArray[i] = integerList.get(i);
        }

        if (integerArray != null && integerArray.length > 0) {
            return integerArray;
        } else {
            return null;
        }
    }

    /**
     * <p>
     * Returns an array of ints for the supplied comma-separated list of
     * protocol names.
     * </p>
     *
     * @param protocols the protocols
     * @return the protocol ints
     */
    public static int[] toIntArray(String protocols) {
        if (isNotBlank(protocols)) {
            String[] protocolArray = protocols.split(",");
            List<Protocol> protocolsTransitional = new ArrayList<Protocol>();
            for (String p : protocolArray) {
                Protocol protocol = forName(p.trim());
                if (protocol != null) {
                    protocolsTransitional.add(protocol);
                }
            }

            int[] protocolsConverted = new int[protocolsTransitional.size()];
            for (int i = 0; i < protocolsTransitional.size(); i++) {
                protocolsConverted[i] = protocolsTransitional.get(i).toInt();
            }
            return protocolsConverted;
        }
        return null;
    }

    /**
     * <p>
     * Returns an integer representing this protocol, as specified by
     * {@link Interface}.
     * </p>
     *
     * @return the int
     */
    public int toInt() {
        Integer i = null;

        switch (this) {
        case HTTP:
            i = new Byte(Interface.SP_HDL_HTTP).intValue();
            break;
        case TCP:
            i = new Byte(Interface.SP_HDL_TCP).intValue();
            break;
        case UDP:
            i = new Byte(Interface.SP_HDL_UDP).intValue();
        }

        return i;
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
        case HTTP:
            s = "http";
            break;
        case TCP:
            s = "tcp";
            break;
        case UDP:
            s = "udp";
            break;
        }

        return s;
    }

}
