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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    private static final Log LOG = LogFactory.getLog(RequestAdapter.class);

    private final String handle;

    private final int[] indices;

    private final Template errorTemplate;

    private final Template handleTemplate;

    private final HandleRecordType[] types;

    /**
     * <p>
     * Instantiate with the request to be adapted and the settings which enable
     * it to be adapted.
     * </p>
     *
     * @param request the request
     * @param settings the settings
     */
    public RequestAdapter(HttpServletRequest request, Settings settings) {
        handle = initHandle(request, settings);
        indices = initIndices(request, settings);
        errorTemplate = initErrorTemplate(request, settings);
        handleTemplate = initHandleTemplate(request, settings);
        types = initTypes(request, settings);
    }

    /**
     * <p>
     * Returns the handle identifier supplied with the adapted request, or
     * <code>null</code> if no identifier was supplied.
     * </p>
     *
     * @return the handle identifier or <code>null</code>
     */
    public String getHandle() {
        return handle;
    }

    /**
     * <p>
     * Returns the indices which were requested in the adapted request, or
     * <code>null</code> if no specific indices were requested.
     * </p>
     *
     * @return the indices or <code>null</code>
     */
    public int[] getIndices() {
        return indices;
    }

    /**
     * <p>
     * Returns the error response template which most closely matches the
     * requirements of the adapted request. This method should <strong>never</strong>
     * return <code>null</code>.
     * </p>
     *
     * @return the error response template
     */
    public Template getErrorResponseTemplate() {
        return errorTemplate;
    }

    /**
     * <p>
     * Returns the handle response template which most closely matches the
     * requirements of the adapted request. This method should <strong>never</strong>
     * return null.
     * </p>
     *
     * @return the handle response template
     */
    public Template getHandleResponseTemplate() {
        return handleTemplate;
    }

    /**
     * <p>
     * Returns the handle record types which were requested in the adapted
     * request, or <code>null</code> if no specific types were requested.
     * </p>
     *
     * @return the handle record types or <code>null</code>
     */
    public HandleRecordType[] getTypes() {
        return types;
    }

    private String initHandle(HttpServletRequest request, Settings settings) {
        return request.getParameter(settings.getHandleIdRequestParameterName());
    }

    private int[] initIndices(HttpServletRequest request, Settings settings) {
        int[] theIndices = null;

        String[] indicesStringArray = request.getParameterValues(settings
                .getIndexRequestParameterName());

        if (indicesStringArray != null && indicesStringArray.length > 0) {
            Integer[] indicesTransitional = new Integer[indicesStringArray.length];
            for (int i = 0; i < indicesStringArray.length; i++) {
                try {
                    indicesTransitional[i] = new Integer(indicesStringArray[i]);
                } catch (NumberFormatException e) {
                    LOG.info(e);
                }
            }

            int i = 0;
            for (Integer integer : indicesTransitional) {
                if (integer != null) {
                    i++;
                }
            }

            theIndices = new int[i];
            i = 0;
            for (Integer integer : indicesTransitional) {
                if (integer != null) {
                    theIndices[i] = integer;
                    i++;
                }
            }
        }

        return theIndices;
    }

    private Template initErrorTemplate(HttpServletRequest request,
            Settings settings) {
        // start with a null template
        Template theTemplate = null;

        // try first to obtain template from extension supplied as param value
        Mimetype mimetype = Mimetype.forExtension(request.getParameter(settings
                .getFormatRequestParameterName()));
        if (mimetype != null
                && settings.getErrorResponseTemplates().containsKey(mimetype)) {
            theTemplate = settings.getErrorResponseTemplates().get(mimetype);
        }

        // if we have no template, try examining the Accept header
        // TODO implement content negotiation in RequestAdapter
//        if (theTemplate == null) {
//            AcceptsHeader header = new AcceptsHeader(request
//                    .getHeader("Accept"));
//            if (header != null && !(header.getEntries().isEmpty())) {
//                for (AcceptsHeaderEntry entry : header.getEntries()) {
//                    mimetype = entry.getType();
//                    if (settings.getErrorResponseTemplates().containsKey(
//                            mimetype)) {
//                        theTemplate = settings.getErrorResponseTemplates().get(
//                                mimetype);
//                        break;
//                    }
//                }
//            }
//        }

        // if we still have no template, use the default
        if (theTemplate == null) {
            theTemplate = settings.getDefaultErrorResponseTemplate();
        }

        if (theTemplate == null) {
            throw new IllegalStateException(
                    "error template is null - this should never happen");
        }

        return theTemplate;
    }

    private Template initHandleTemplate(HttpServletRequest request,
            Settings settings) {
        // start with a null template
        Template theTemplate = null;

        // try first to obtain template from extension supplied as param value
        Mimetype mimetype = Mimetype.forExtension(request.getParameter(settings
                .getFormatRequestParameterName()));
        if (mimetype != null
                && settings.getHandleResponseTemplates().containsKey(mimetype)) {
            theTemplate = settings.getHandleResponseTemplates().get(mimetype);
        }

        // if we have no template, try examining the Accept header
        // TODO implement content negotiation in RequestAdapter
//        if (theTemplate == null) {
//            AcceptsHeader header = new AcceptsHeader(request
//                    .getHeader("Accept"));
//            if (header != null && !(header.getEntries().isEmpty())) {
//                for (AcceptsHeaderEntry entry : header.getEntries()) {
//                    mimetype = entry.getType();
//                    if (settings.getHandleResponseTemplates().containsKey(
//                            mimetype)) {
//                        theTemplate = settings.getHandleResponseTemplates()
//                                .get(mimetype);
//                        break;
//                    }
//                }
//            }
//        }

        // if we still have no template, use the default
        if (theTemplate == null) {
            theTemplate = settings.getDefaultHandleResponseTemplate();
        }

        if (theTemplate == null) {
            throw new IllegalStateException(
                    "handle template is null - this should never happen");
        }

        return theTemplate;
    }

    private HandleRecordType[] initTypes(HttpServletRequest request,
            Settings settings) {
        HandleRecordType[] theTypes = null;
        String[] rawTypes = request.getParameterValues(settings
                .getTypeRequestParameterName());

        if (rawTypes != null && rawTypes.length > 0) {
            List<HandleRecordType> typesTransitional = new ArrayList<HandleRecordType>();
            for (String s : rawTypes) {
                HandleRecordType type = HandleRecordType.forName(s);
                if (type != null) {
                    typesTransitional.add(type);
                }
            }

            if (!(typesTransitional.isEmpty())) {
                theTypes = typesTransitional
                        .toArray(new HandleRecordType[typesTransitional.size()]);
            }
        }
        return theTypes;
    }

}
