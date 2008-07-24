/*
 * Created by: christopher
 * Date: Feb 28, 2008
 * Time: 2:20:11 PM
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

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * TODO Document AcceptsHeader
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
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
