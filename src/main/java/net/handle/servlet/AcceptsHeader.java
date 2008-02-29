/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: Feb 28, 2008
 * Time: 2:20:11 PM
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

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * TODO Document AcceptsHeader
 * </p>
 * 
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 */
public class AcceptsHeader {

    private SortedSet<AcceptsHeaderEntry> entries = new TreeSet<AcceptsHeaderEntry>();

    /**
     * <p>
     * Initialize with the raw header string.
     * </p>
     * 
     * @param header the raw header string
     */
    public AcceptsHeader(String header) {
        if (StringUtils.isNotBlank(header)) {
            // split entries, which are comma-separated
            String[] rawEntries = header.split(",");
            for (String rawEntry : rawEntries) {
                rawEntry = rawEntry.trim();
                AcceptsHeaderEntry entry = new AcceptsHeaderEntry();
                // split entry into type & params, which are semi-colon
                // separated
                String[] parts = rawEntry.split(";");
                for (String part : parts) {
                    part = part.trim();
                    if (part.split("=").length > 1) {
                        // it's a param
                        String[] rawParam = part.split("=");
                        if (rawParam.length == 2) {
                            String key = rawParam[0].trim();
                            String value = rawParam[1].trim();
                            if (key.equals("q")) {
                                entry.setQ(new Float(value));
                            } else if (key.equals("mxb")) {
                                entry.setMxb(new Float(value));
                            } else if (key.equals("mxs")) {
                                entry.setMxs(new Float(value));
                            }
                        }
                    } else {
                        // it's the type
                        Mimetype mimetype = Mimetype.forName(part);

                        if (mimetype != null) {
                            entry.setType(mimetype);
                        }
                    }
                }

                if (entry.getType() != null) {
                    entries.add(entry);
                }
            }
        }
    }

    /**
     * <p>
     * </p>
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (AcceptsHeaderEntry entry : entries) {
            if (entry.equals(entries.last())) {
                sb.append(entry.toString());
            } else {
                sb.append(entry.toString() + ", ");
            }
        }
        return sb.toString();
    }

    /**
     * <p>
     * Returns the preferred mimetype as specified in this Accepts header.
     * </p>
     * 
     * @return the preferred mimetype
     */
    public Mimetype getPreferredMimetype() {
        return entries.first().getType();
    }

    /**
     * <p>
     * Returns the entries.
     * </p>
     * 
     * @return the entries
     */
    public SortedSet<AcceptsHeaderEntry> getEntries() {
        return entries;
    }

    /**
     * <p>
     * Sets the entries.
     * </p>
     * 
     * @param entries the entries to set
     */
    public void setEntries(SortedSet<AcceptsHeaderEntry> entries) {
        this.entries = entries;
    }

}
