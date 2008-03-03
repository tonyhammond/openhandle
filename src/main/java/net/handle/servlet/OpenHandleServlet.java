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

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.handle.hdllib.HandleException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
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

    private static final long serialVersionUID = 3323381446195576928L;

    private static final Log LOG = LogFactory.getLog(OpenHandleServlet.class);

    private OpenHandle resolver;

    private Settings settings;

    /**
     * <p>
     * Performs steps necessary to correctly configure and initialize this
     * servlet. At present, this consists of the following:
     * </p>
     * <ol>
     * <li>Attempting to load a custom config, if one is specified as an
     * init-param using the key 'config'.</li>
     * <li>Loading the default config if no custom config is available.</li>
     * <li>Loading up an instance of {@link Settings} from the config.</li>
     * <li>Initializing an instance of {@link OpenHandle} to act as a resolver.</li>
     * </ol>
     * <p>
     * A custom configuration detailing available templates, handle client
     * options, and request parameter names can be specified as an init-param:
     * </p>
     *
     * <pre>
     * &lt;init-param&gt;
     *     &lt;param-name&gt;config&lt;/param-name&gt;
     *     &lt;param-value&gt;/WEB-INF/OpenHandle.xml&lt;/param-value&gt;
     * &lt;/init-param&gt;
     * </pre>
     *
     * <p>
     * The path specified as a value must point to a resource obtainable via
     * {@link ServletContext#getResource(String)}.
     * </p>
     *
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // start with a null configuration
        Configuration configuration = null;

        // has a custom config been specified?
        String customConfig = config.getInitParameter("config");
        if (isNotBlank(customConfig)) {
            try {
                configuration = new XMLConfiguration(getServletContext()
                        .getResource(customConfig));
            } catch (Exception e) {
                LOG.error("Could not load custom configuration '"
                        + customConfig
                        + "'. Proceeding to load default configuration.", e);
            }
        }

        // load the default config
        try {
            configuration = new XMLConfiguration(OpenHandleServlet.class
                    .getResource("/OpenHandle.xml"));
        } catch (Exception e) {
            throw new ServletException("Could not load default configuration",
                    e);
        }

        try {
            // load settings
            settings = new Settings(configuration);

            // initialize resolver
            resolver = new OpenHandle(settings.getPreferredProtocols(),
                    settings.isTraceMessages());
        } catch (SettingsException e) {
            throw new ServletException(e);
        }
    }

    /**
     * <p>
     * </p>
     *
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        RequestAdapter adapter = new RequestAdapter(request, settings);
        if (isBlank(adapter.getHandle())) {
            doErrorResponse(adapter, request, response,
                    "No handle identifier specified",
                    HttpServletResponse.SC_BAD_REQUEST,
                    HttpServletResponse.SC_BAD_REQUEST);
        } else {
            doHandleResponse(adapter, request, response);
        }
    }

    /*
     * Method that gets delegated to to handle an error response
     */
    private void doErrorResponse(RequestAdapter adapter,
            HttpServletRequest request, HttpServletResponse response,
            String message, int httpStatus, int handleErrorCode)
            throws ServletException, IOException {
        Template template = adapter.getErrorResponseTemplate();
        response.setStatus(httpStatus);
        response.setContentType(template.getMimetype().toString());
        request.setAttribute("error", new HandleExceptionAdapter(
                handleErrorCode, message));
        request.getRequestDispatcher(template.toString());
    }

    /*
     * Method that gets delegated to to handle a handle response
     */
    private void doHandleResponse(RequestAdapter adapter,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HandleResponseAdapter handleData = resolver.get(
                    adapter.getHandle(), adapter.getIndices(), adapter
                            .getTypes());
            Template template = adapter.getHandleResponseTemplate();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(template.getMimetype().toString());
            request.setAttribute(settings.getHandleIdRequestParameterName(),
                    handleData);
            request.getRequestDispatcher(template.toString());
        } catch (HandleException e) {
            doErrorResponse(adapter, request, response, e.getMessage(),
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getCode());
        }
    }
}
