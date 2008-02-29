/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: Feb 28, 2008
 * Time: 1:54:53 PM
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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * An adapter to an {@link HttpServletRequest} which provides access to useful
 * methods not provided as part of the servlet spec (such as to facilitate
 * content negotiation).
 * </p>
 * 
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 */
public class RequestAdapter {

    private HttpServletRequest request;

    /**
     * <p>
     * Instantiate with the request to be adapted.
     * </p>
     * 
     * @param request the request
     */
    public RequestAdapter(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * <p>
     * Returns a list of mimetypes specified by the client using the Accepts
     * header in order of preference.
     * </p>
     * 
     * @return the preferred mimetypes
     */
    public List<String> getPreferredMimetypes() {
        // TODO implement getPreferredMimetypes() in RequestAdapter
        List<String> preferredMimetypes = new ArrayList<String>();
        String accepts = request.getHeader("Accepts");

        if (StringUtils.isNotBlank(accepts)) {
            // split entries which are separate by commas
            String[] entries = accepts.split(",");
            for (String entry : entries) {
                entry = entry.trim();
                
                // split entry in type & params, which are semi-colon separated
                String[] parts = entry.split(";");
                for (String part : parts) {
                    part = part.trim();
                    if (part.split("=").length > 1) {
                        // it's a params
                    } else {
                        // it's the type
                    }
                }
            }
        }

        return preferredMimetypes;
    }

}
