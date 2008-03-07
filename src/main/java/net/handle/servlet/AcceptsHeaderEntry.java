/*
 * Created by: christopher
 * Date: Feb 28, 2008
 * Time: 2:11:07 PM
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

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * Bean representing an entry in an HTTP Accepts header.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class AcceptsHeaderEntry implements Comparable<AcceptsHeaderEntry>,
        Serializable {

    private static final long serialVersionUID = 3721487090982940305L;

    private Float mxb;

    private Float mxs;

    private Float q = 1.0F;

    private Mimetype type;

    /**
     * <p>
     * Default, no-argument constructor.
     * </p>
     */
    public AcceptsHeaderEntry() {
        // do nothing
    }

    /**
     * <p>
     * </p>
     *
     * @param type
     * @param q
     * @param mxb
     * @param mxs
     */
    public AcceptsHeaderEntry(Mimetype type, float q, float mxb, float mxs) {
        this.type = type;
        this.q = q;
        this.mxb = mxb;
        this.mxs = mxs;
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(AcceptsHeaderEntry entry) {
        if (q < entry.q) {
            return 1;
        } else if (q == entry.q) {
            return 0;
        } else {
            return -1;
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
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * <p>
     * Returns the mxb.
     * </p>
     *
     * @return the mxb
     */
    public Float getMxb() {
        return mxb;
    }

    /**
     * <p>
     * Returns the mxs.
     * </p>
     *
     * @return the mxs
     */
    public Float getMxs() {
        return mxs;
    }

    /**
     * <p>
     * Returns the q.
     * </p>
     *
     * @return the q
     */
    public Float getQ() {
        return q;
    }

    /**
     * <p>
     * Returns the type.
     * </p>
     *
     * @return the type
     */
    public Mimetype getType() {
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
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * <p>
     * Sets the mxb.
     * </p>
     *
     * @param mxb the mxb to set
     */
    public void setMxb(Float mxb) {
        this.mxb = mxb;
    }

    /**
     * <p>
     * Sets the mxs.
     * </p>
     *
     * @param mxs the mxs to set
     */
    public void setMxs(Float mxs) {
        this.mxs = mxs;
    }

    /**
     * <p>
     * Sets the q.
     * </p>
     *
     * @param q the q to set
     */
    public void setQ(Float q) {
        this.q = q;
    }

    /**
     * <p>
     * Sets the type.
     * </p>
     *
     * @param type the type to set
     */
    public void setType(Mimetype type) {
        this.type = type;
    }

    /**
     * <p>
     * </p>
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return type + ((q == null) ? "" : "; q=" + q)
                + ((mxs == null) ? "" : "; mxs=" + mxs)
                + ((mxb == null) ? "" : "; mxb=" + mxb);
    }

}
