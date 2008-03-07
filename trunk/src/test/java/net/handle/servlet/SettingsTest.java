/*
 * Created by: christopher
 * Date: 2 Mar 2008
 * Time: 17:44:01
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

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

/**
 * <p>
 * JUnit test case for {@link Settings}.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class SettingsTest {

    @Test
    public void testValidConfiguration() throws Exception {
        // control variables
        String datatypesRequestParameterName = "include";
        Template defaultErrorResponseTemplate = new Template(
                Mimetype.APPLICATION_RDF_XML, "error",
                Template.Type.ERROR_RESPONSE);
        Template defaultHandleResponseTemplate = new Template(
                Mimetype.APPLICATION_RDF_XML, "handle",
                Template.Type.HANDLE_RESPONSE);
        Map<Mimetype, Template> errorResponseTemplates = new HashMap<Mimetype, Template>();
        errorResponseTemplates.put(Mimetype.APPLICATION_RDF_XML, new Template(
                Mimetype.APPLICATION_RDF_XML, "error",
                Template.Type.ERROR_RESPONSE));
        errorResponseTemplates.put(Mimetype.TEXT_PLAIN, new Template(
                Mimetype.TEXT_PLAIN, "error", Template.Type.ERROR_RESPONSE));
        errorResponseTemplates.put(Mimetype.APPLICATION_XHTML_XML,
                new Template(Mimetype.APPLICATION_XHTML_XML, "error",
                        Template.Type.ERROR_RESPONSE));
        String formatRequestParameterName = "format";
        String handleIdRequestParameterName = "id";
        Map<Mimetype, Template> handleResponseTemplates = new HashMap<Mimetype, Template>();
        handleResponseTemplates.put(Mimetype.APPLICATION_RDF_XML, new Template(
                Mimetype.APPLICATION_RDF_XML, "handle",
                Template.Type.HANDLE_RESPONSE));
        handleResponseTemplates.put(Mimetype.APPLICATION_JSON, new Template(
                Mimetype.APPLICATION_JSON, "handle",
                Template.Type.HANDLE_RESPONSE));
        handleResponseTemplates.put(Mimetype.TEXT_RDF_N3, new Template(
                Mimetype.TEXT_RDF_N3, "handle", Template.Type.HANDLE_RESPONSE));
        String indexRequestParameterName = "index";
        int[] preferredProtocols = Protocol.toIntArray("http, tcp");
        String typeRequestParameterName = "type";
        boolean traceMessages = true;

        // instantiate configuration
        Configuration configuration = new XMLConfiguration(SettingsTest.class
                .getResource("/OpenHandleTest.xml"));
        Settings settings = new Settings(configuration);

        // settings should match control
        assertEquals(datatypesRequestParameterName, settings
                .getDatatypesRequestParameterName());
        assertEquals(defaultErrorResponseTemplate, settings
                .getDefaultErrorResponseTemplate());
        assertEquals(defaultHandleResponseTemplate, settings
                .getDefaultHandleResponseTemplate());
        assertEquals(errorResponseTemplates, settings
                .getErrorResponseTemplates());
        assertEquals(formatRequestParameterName, settings
                .getFormatRequestParameterName());
        assertEquals(handleIdRequestParameterName, settings
                .getHandleIdRequestParameterName());
        assertEquals(handleResponseTemplates, settings
                .getHandleResponseTemplates());
        assertEquals(indexRequestParameterName, settings
                .getIndexRequestParameterName());
        assertEquals(Protocol.toString(preferredProtocols), Protocol
                .toString(settings.getPreferredProtocols()));
        assertEquals(typeRequestParameterName, settings
                .getTypeRequestParameterName());
        assertEquals(traceMessages, settings.isTraceMessages());
    }

    @Test(expected = SettingsException.class)
    public void testInvalidConfiguration() throws Exception {
        Configuration configuration = new XMLConfiguration(SettingsTest.class
                .getResource("/OpenHandleTestInvalid.xml"));
        Settings settings = new Settings(configuration);
        assertNull(settings);
    }

}
