/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: 14 Sep 2007
 * Time: 14:49:55
 *
 * <p>Copyright (C) 2007 Nature Publishing Group, Inc.</p>
 *
 * <p><abbr title="Nature Publishing Group">NPG</abbr> reserves all rights in this
 * program. The program or any portion thereof may not be reproduced in any form
 * whatsoever without the written consent of <abbr title="Nature Publishing
 * Group">NPG</abbr>.</p>
 *
 * <p>Nature Publishing Group is a division of Macmillan Publishers Limited, of
 * Brunel Road, Houndmills, Basingstoke, Hampshire, RG21 6XS. Registered Number
 * 785998 England.</p>
 */

package net.handle.servlet;

import static org.apache.commons.lang.StringUtils.*;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.handle.hdllib.HandleException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * TODO Document OpenHandleServlet
 * </p>
 * 
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher
 *         Townson</a>
 * @author <a href="mailto:t.hammond@nature.com">Tony Hammond</a>
 */
public class OpenHandleServlet extends HttpServlet {

    /**
     * The init param key to use in the web.xml when specifying the name of the
     * error response template
     */
    public static final String ERROR_TEMPLATE_NAME_INIT_PARAM_KEY = "errorTemplateName";

    /**
     * The init param key to use in the web.xml when specifying the name of the
     * handle response template.
     */
    public static final String RESPONSE_TEMPLATE_NAME_INIT_PARAM_KEY = "responseTemplateName";

    /**
     * The init param key to use in the web.xml when specifying the name of the
     * handle id request parameter. The value of a parameter with this name is
     * used to obtain the handle id from the request. If no value is supplied
     * for a parameter with this name in the request then an error response will
     * be returned with the {@link #MISSING_HANDLE_MESSAGE} ({@value #MISSING_HANDLE_MESSAGE}).
     */
    public static final String HANDLE_ID_REQUEST_PARAMETER_NAME_INIT_PARAM_KEY = "handleIdRequestParameterName";

    /**
     * The init param key to use in the web.xml when specifying the name of the
     * format request parameter. The value of a parameter with this name is used
     * to obtain the client's preferred response format (if that is included in
     * the {@link #supportedFormats}). If this parameter is not present, or the
     * supplied value is not contained within the {@link #supportedFormats},
     * then the {@link #DEFAULT_FORMAT} is used.
     */
    public static final String FORMAT_REQUEST_PARAMETER_NAME_INIT_PARAM_KEY = "formatRequestParameterName";

    /**
     * The init param key to use in the web.xml when specifying a
     * comma-separated list of the names of formats for which response templates
     * have been provided. This is optional and, if not specified,
     * {@link #DEFAULT_SUPPORTED_FORMATS} is used.
     */
    public static final String SUPPORTED_FORMATS_INIT_PARAM_KEY = "supportedFormats";

    /**
     * Use this key in the web.xml to set trace messages in the handle client.
     * The supplied value is evaluated using <code>new Boolean(value)</code>.
     * This is optional and the default is <code>false</code>.
     */
    public static final String TRACE_MESSAGES_INIT_PARAM_KEY = "traceMessages";

    /**
     * Use this key in the web.xml to set the preferred protocols for the
     * handle client. <strong>Note: this is not yet implemented.</strong>
     */
    public static final String PREFERRED_PROTOCOLS_INIT_PARAM_KEY = "preferredProtocols";

    private static final String DEFAULT_FORMAT = "rdf";

    private static final String DEFAULT_ERROR_TEMPLATE_NAME = "error";

    private static final String DEFAULT_RESPONSE_TEMPLATE_NAME = "handle";

    private static final String DEFAULT_FORMAT_REQUEST_PARAMETER_NAME = "format";

    private static final String DEFAULT_HANDLE_ID_REQUEST_PARAMETER_NAME = "id";

    private static final String[] DEFAULT_SUPPORTED_FORMATS = { "rdf", "json",
            "n3" };

    private static final long serialVersionUID = -8490509879747909276L;

    private static final Log LOG = LogFactory.getLog(OpenHandleServlet.class);

    private static final String MISSING_HANDLE_MESSAGE = "Missing handle rft id request parameter value";

    private String errorTemplate;

    private String responseTemplate;

    private String formatRequestParameterName;

    private String handleIdRequestParameterName;

    private Boolean traceMessages;

    private OpenHandle resolver;

    private String[] supportedFormats;

    private int[] preferredProtocols;

    /**
     * <p>
     * Delegates to {@link #doPost(HttpServletRequest, HttpServletResponse)}.
     * </p>
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * <p>
     * </p>
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String handle = request
                .getParameter(DEFAULT_HANDLE_ID_REQUEST_PARAMETER_NAME);

        // TODO setup content negotiation to determine this format from headers

        // specifying format using req param overrides headers
        String format = request.getParameter("format");
        if (isBlank(format)
                || !(Arrays.asList(supportedFormats).contains(format))) {
            format = DEFAULT_FORMAT;
        }

        if (isEmpty(handle)) {
            response.setContentType("application/rdf+xml");
            request.setAttribute("error", new HandleExceptionAdapter(400,
                    MISSING_HANDLE_MESSAGE));
            request.getRequestDispatcher("/" + errorTemplate + "." + format)
                    .forward(request, response);
            LOG.error(MISSING_HANDLE_MESSAGE);
        } else {
            try {
                HandleResponseAdapter handleData = resolver.get(handle);
                response.setContentType("application/rdf+xml");
                request.setAttribute(handleIdRequestParameterName, handleData);
                request.getRequestDispatcher(
                        "/" + responseTemplate + "." + format).forward(request,
                        response);
            } catch (HandleException e) {
                response.setContentType("application/rdf+xml");
                request.setAttribute("error", new HandleExceptionAdapter(e
                        .getCode(), e.getMessage()));
                request
                        .getRequestDispatcher(
                                "/" + errorTemplate + "." + format).forward(
                                request, response);
                LOG.error(e);
            }
        }
    }

    /**
     * <p>
     * </p>
     * 
     * @see javax.servlet.GenericServlet#getServletInfo()
     */
    @Override
    public String getServletInfo() {
        return "This servlet is used to get handle values and return as RDF/XML.";
    }

    /**
     * <p>
     * </p>
     * 
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initTemplates(config);
        initParameterNames(config);
        initSupportedFormats(config);
        initHandleServerOptions(config);

        resolver = new OpenHandle();

        if (traceMessages != null) {
            resolver.setTraceMessages(traceMessages);
        }

        if ((preferredProtocols != null) && (preferredProtocols.length > 0)) {
            resolver.setPreferredProtocols(preferredProtocols);
        }
    }

    /*
     * obtains names for response + error templates (to which the format name is
     * appended as a file extension)
     */
    private void initTemplates(ServletConfig servletConfig) {
        errorTemplate = servletConfig
                .getInitParameter(ERROR_TEMPLATE_NAME_INIT_PARAM_KEY);
        responseTemplate = servletConfig
                .getInitParameter(RESPONSE_TEMPLATE_NAME_INIT_PARAM_KEY);

        if (isBlank(errorTemplate)) {
            errorTemplate = DEFAULT_ERROR_TEMPLATE_NAME;
        }

        if (isBlank(responseTemplate)) {
            responseTemplate = DEFAULT_RESPONSE_TEMPLATE_NAME;
        }
    }

    /*
     * obtains names for the various request parameters which can be used to
     * determine response formats etc
     */
    private void initParameterNames(ServletConfig servletConfig) {
        handleIdRequestParameterName = servletConfig
                .getInitParameter(HANDLE_ID_REQUEST_PARAMETER_NAME_INIT_PARAM_KEY);

        if (isBlank(handleIdRequestParameterName)) {
            handleIdRequestParameterName = DEFAULT_HANDLE_ID_REQUEST_PARAMETER_NAME;
        }

        formatRequestParameterName = servletConfig
                .getInitParameter(FORMAT_REQUEST_PARAMETER_NAME_INIT_PARAM_KEY);

        if (isBlank(formatRequestParameterName)) {
            formatRequestParameterName = DEFAULT_FORMAT_REQUEST_PARAMETER_NAME;
        }
    }

    /*
     * obtains a list of supported response formats (i.e. what templates with
     * what extensions do we have)
     */
    private void initSupportedFormats(ServletConfig servletConfig) {
        String sf = servletConfig
                .getInitParameter(SUPPORTED_FORMATS_INIT_PARAM_KEY);
        if (isNotBlank(sf)) {
            supportedFormats = sf.split(",");
            for (String format : supportedFormats) {
                format = format.trim();
            }
        }

        if (supportedFormats == null || supportedFormats.length == 0) {
            supportedFormats = DEFAULT_SUPPORTED_FORMATS;
        }
    }

    /*
     * obtains values for configurable aspects of handle
     */
    private void initHandleServerOptions(ServletConfig servletConfig) {
        String trace = servletConfig
                .getInitParameter(TRACE_MESSAGES_INIT_PARAM_KEY);
        String protocols = servletConfig
                .getInitParameter(PREFERRED_PROTOCOLS_INIT_PARAM_KEY);

        if (isNotBlank(trace)) {
            traceMessages = new Boolean(trace);
        }

        if (isNotBlank(protocols)) {
            preferredProtocols = OpenHandle.toProtocolArray(protocols);
        }
    }
}
