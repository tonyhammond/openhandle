/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: 2 Mar 2008
 * Time: 12:12:06
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Encapsulation of the various configuration settings that can be made for this
 * application (basically, to separate them from the servlet for testability).
 * </p>
 *
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 */
public class Settings {

    private static final String DATATYPES_KEY = "servlet.parameterNames.datatypes";

    private static final String ERROR_TEMPLATE_MIMETYPE_KEY = "servlet.templates.errorResponse.template.mimetype";

    private static final String ERROR_TEMPLATE_NAME_KEY = "servlet.templates.errorResponse.template.name";

    private static final String FORMAT_KEY = "servlet.parameterNames.format";

    private static final String HANDLE_ID_KEY = "servlet.parameterNames.handleId";

    private static final String INDEX_KEY = "servlet.parameterNames.index";

    private static final Log LOG = LogFactory.getLog(Settings.class);

    private static final String PREFERRED_PROTOCOLS_KEY = "preferredProtocols";

    private static final String RESPONSE_TEMPLATE_MIMETYPE_KEY = "servlet.templates.handleResponse.template.mimetype";

    private static final String RESPONSE_TEMPLATE_NAME_KEY = "servlet.templates.handleResponse.template.name";

    private static final String TRACE_MESSAGES_KEY = "traceMessages";

    private static final String TYPE_KEY = "servlet.parameterNames.type";

    // TODO make all private members final in Settings
    private String datatypesRequestParameterName;

    private Template defaultErrorResponseTemplate;

    private Template defaultHandleResponseTemplate;

    private Map<Mimetype, Template> errorResponseTemplates;

    private String formatRequestParameterName;

    private String handleIdRequestParameterName;

    private Map<Mimetype, Template> handleResponseTemplates;

    private String indexRequestParameterName;

    private int[] preferredProtocols;

    private String typeRequestParameterName;

    private boolean traceMessages;

    public Settings(Configuration configuration) throws SettingsException {
        if (configuration != null) {
            initRequestParameters(configuration);
            initErrorResponseTemplates(configuration);
            initHandleResponseTemplates(configuration);
            initHandleClientOptions(configuration);
        } else {
            throw new SettingsException("Configuration is null");
        }
    }

    public String getDatatypesRequestParameterName() {
        return datatypesRequestParameterName;
    }

    public Template getDefaultErrorResponseTemplate() {
        return defaultErrorResponseTemplate;
    }

    public Template getDefaultHandleResponseTemplate() {
        return defaultHandleResponseTemplate;
    }

    public Map<Mimetype, Template> getErrorResponseTemplates() {
        return errorResponseTemplates;
    }

    public String getFormatRequestParameterName() {
        return formatRequestParameterName;
    }

    public String getHandleIdRequestParameterName() {
        return handleIdRequestParameterName;
    }

    public Map<Mimetype, Template> getHandleResponseTemplates() {
        return handleResponseTemplates;
    }

    public String getIndexRequestParameterName() {
        return indexRequestParameterName;
    }

    public int[] getPreferredProtocols() {
        return preferredProtocols;
    }

    public String getTypeRequestParameterName() {
        return typeRequestParameterName;
    }

    public boolean isTraceMessages() {
        return traceMessages;
    }

    private void initRequestParameters(Configuration configuration)
            throws SettingsException {
        try {
            handleIdRequestParameterName = configuration
                    .getString(HANDLE_ID_KEY);
            if (isNotBlank(handleIdRequestParameterName)) {
                LOG.info("Setting " + HANDLE_ID_KEY + " to '"
                        + handleIdRequestParameterName + "'");
            } else {
                throw new SettingsException(HANDLE_ID_KEY + " is blank");
            }

            datatypesRequestParameterName = configuration
                    .getString(DATATYPES_KEY);
            if (isNotBlank(datatypesRequestParameterName)) {
                LOG.info("Setting " + DATATYPES_KEY + " to '"
                        + datatypesRequestParameterName + "'");
            } else {
                throw new SettingsException(DATATYPES_KEY + " is blank");
            }

            formatRequestParameterName = configuration.getString(FORMAT_KEY);
            if (isNotBlank(formatRequestParameterName)) {
                LOG.info("Setting " + FORMAT_KEY + " to '"
                        + formatRequestParameterName + "'");
            } else {
                throw new SettingsException(FORMAT_KEY + " is blank");
            }

            indexRequestParameterName = configuration.getString(INDEX_KEY);
            if (isNotBlank(indexRequestParameterName)) {
                LOG.info("Setting " + INDEX_KEY + " to '"
                        + indexRequestParameterName + "'");
            } else {
                throw new SettingsException(INDEX_KEY + " is blank");
            }

            typeRequestParameterName = configuration.getString(TYPE_KEY);
            if (isNotBlank(typeRequestParameterName)) {
                LOG.info("Setting " + TYPE_KEY + " to '"
                        + typeRequestParameterName + "'");
            } else {
                throw new SettingsException(TYPE_KEY + " is blank");
            }
        } catch (ConversionException e) {
            throw new SettingsException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void initErrorResponseTemplates(Configuration configuration)
            throws SettingsException {
        try {
            List<String> errorTemplateNames = configuration
                    .getList(ERROR_TEMPLATE_NAME_KEY);
            List<String> errorTemplateMimetypes = configuration
                    .getList(ERROR_TEMPLATE_MIMETYPE_KEY);

            if (!(isValidTemplateConfiguration(errorTemplateNames,
                    errorTemplateMimetypes))) {
                throw new SettingsException(
                        "Missing error template configuration. "
                                + "Please check that you have specified a name "
                                + "and a mimetype for each configured template");
            }

            errorResponseTemplates = new HashMap<Mimetype, Template>();
            for (int i = 0; i < errorTemplateNames.size(); i++) {
                String configuredMimetype = errorTemplateMimetypes.get(i);
                Mimetype mimetype = Mimetype.forName(configuredMimetype);
                if (mimetype == null) {
                    throw new SettingsException(
                            "Unsupported or invalid mimetype: "
                                    + configuredMimetype);
                }

                String name = errorTemplateNames.get(i);
                Template template = new Template(mimetype, name,
                        Template.Type.ERROR_RESPONSE);
                errorResponseTemplates.put(mimetype, template);

                if (i == 0) {
                    defaultErrorResponseTemplate = template;
                }
            }
        } catch (ConversionException e) {
            throw new SettingsException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void initHandleResponseTemplates(Configuration configuration)
            throws SettingsException {
        try {
            List<String> handleTemplateNames = configuration
                    .getList(RESPONSE_TEMPLATE_NAME_KEY);
            List<String> handleTemplateMimetypes = configuration
                    .getList(RESPONSE_TEMPLATE_MIMETYPE_KEY);

            if (!(isValidTemplateConfiguration(handleTemplateNames,
                    handleTemplateMimetypes))) {
                throw new SettingsException(
                        "Missing response template configuration. "
                                + "Please check that you have specified a name "
                                + "and a mimetype for each configured template");
            }

            handleResponseTemplates = new HashMap<Mimetype, Template>();
            for (int i = 0; i < handleTemplateNames.size(); i++) {
                String configuredMimetype = handleTemplateMimetypes.get(i);
                Mimetype mimetype = Mimetype.forName(configuredMimetype);
                if (mimetype == null) {
                    throw new SettingsException(
                            "Unsupported or invalid mimetype: "
                                    + configuredMimetype);
                }

                String name = handleTemplateNames.get(i);
                Template template = new Template(mimetype, name,
                        Template.Type.HANDLE_RESPONSE);
                handleResponseTemplates.put(mimetype, template);

                if (i == 0) {
                    defaultHandleResponseTemplate = template;
                }
            }
        } catch (ConversionException e) {
            throw new SettingsException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void initHandleClientOptions(Configuration configuration)
            throws SettingsException {
        try {
            preferredProtocols = Protocol.toIntArrayFromList(configuration.getList(PREFERRED_PROTOCOLS_KEY));
            if (preferredProtocols != null && preferredProtocols.length > 0) {
                for (int i : preferredProtocols) {
                    LOG.info("Adding preferred protocol '" + Protocol.forInt(i).toString() + "'");
                }
            } else {
                throw new SettingsException(PREFERRED_PROTOCOLS_KEY
                        + " is blank");
            }

            traceMessages = configuration.getBoolean(TRACE_MESSAGES_KEY);
            LOG.info("Setting traceMessages '" + traceMessages + "'");
        } catch (ConversionException e) {
            throw new SettingsException(e);
        }
    }

    private boolean isValidTemplateConfiguration(List<String> templateNames,
            List<String> templateMimetypes) {
        // [sum of list sizes] >= 2
        // [sum of list sizes] % 2 == 0
        // all sizes are equal
        // if not, we're missing a value somewhere
        int a = templateNames.size();
        int b = templateMimetypes.size();
        int sum = (a + b);
        if (!((sum >= 2) && (sum % 2 == 0)) || !(a == b)) {
            return false;
        } else {
            return true;
        }
    }

}
