/*
 * Created by: christopher
 * Date: 3 Mar 2008
 * Time: 17:20:18
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

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * <p>
 * JUnit test case for {@link RequestAdapter}.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class RequestAdapterTest {

    private MockHttpServletRequest request;

    private Settings settings;

    private String handle;

    @Before
    public void setup() throws Exception {
        settings = new Settings(new XMLConfiguration(RequestAdapterTest.class
                .getResource("/OpenHandleTest.xml")));
        handle = "10101/nature";
        request = new MockHttpServletRequest();
    }

    @Test
    public void testHandleIdNull() {
        assertNull(new RequestAdapter(request, settings).getHandle());
    }

    @Test
    public void testHandleId() {
        request
                .addParameter(settings.getHandleIdRequestParameterName(),
                        handle);
        assertEquals(handle, new RequestAdapter(request, settings).getHandle());
    }

    @Test
    public void testIndicesNull() {
        assertNull(new RequestAdapter(request, settings).getIndices());
    }

    @Test
    public void testIndicesSingle() {
        request.addParameter(settings.getIndexRequestParameterName(), "1");
        assertEquals(1, new RequestAdapter(request, settings).getIndices()[0]);
    }

    @Test
    public void testIndicesMultiple() {
        request.addParameter(settings.getIndexRequestParameterName(),
                new String[] { "1", "2", "3" });
        RequestAdapter adapter = new RequestAdapter(request, settings);
        assertEquals(1, adapter.getIndices()[0]);
        assertEquals(2, adapter.getIndices()[1]);
        assertEquals(3, adapter.getIndices()[2]);
    }

    /*
     * the test settings file has 3 configured error templates - let's make sure
     * we can get each one with the relevant param and that we get the first
     * configured for null
     */

    @Test
    public void testGetErrorResponseTemplateNull() {
        assertEquals(new Template(Mimetype.APPLICATION_RDF_XML, "error",
                Template.Type.ERROR_RESPONSE), new RequestAdapter(request,
                settings).getErrorResponseTemplate());
    }

    @Test
    public void testGetErrorResponseTemplateTextPlain() {
        request.addParameter(settings.getFormatRequestParameterName(), "txt");
        assertEquals(new Template(Mimetype.TEXT_PLAIN, "error",
                Template.Type.ERROR_RESPONSE), new RequestAdapter(request,
                settings).getErrorResponseTemplate());
    }

    @Test
    public void testGetErrorResponseTemplateXhtml() {
        request.addParameter(settings.getFormatRequestParameterName(), "xhtml");
        assertEquals(new Template(Mimetype.APPLICATION_XHTML_XML, "error",
                Template.Type.ERROR_RESPONSE), new RequestAdapter(request,
                settings).getErrorResponseTemplate());
    }

    @Test
    public void testGetErrorResponseTemplateRdf() {
        request.addParameter(settings.getFormatRequestParameterName(), "rdf");
        assertEquals(new Template(Mimetype.APPLICATION_RDF_XML, "error",
                Template.Type.ERROR_RESPONSE), new RequestAdapter(request,
                settings).getErrorResponseTemplate());
    }

    /*
     * and the same for the handle response templates ...
     */

    @Test
    public void testGetHandleResponseTemplateNull() {
        assertEquals(new Template(Mimetype.APPLICATION_RDF_XML, "handle",
                Template.Type.HANDLE_RESPONSE), new RequestAdapter(request,
                settings).getHandleResponseTemplate());
    }

    @Test
    public void testGetHandleResponseTemplateJson() {
        request.addParameter(settings.getFormatRequestParameterName(), "json");
        assertEquals(new Template(Mimetype.APPLICATION_JSON, "handle",
                Template.Type.HANDLE_RESPONSE), new RequestAdapter(request,
                settings).getHandleResponseTemplate());
    }

    @Test
    public void testGetHandleResponseTemplateN3() {
        request.addParameter(settings.getFormatRequestParameterName(), "n3");
        assertEquals(new Template(Mimetype.TEXT_RDF_N3, "handle",
                Template.Type.HANDLE_RESPONSE), new RequestAdapter(request,
                settings).getHandleResponseTemplate());
    }

    @Test
    public void testGetHandleResponseTemplateRdf() {
        request.addParameter(settings.getFormatRequestParameterName(), "rdf");
        assertEquals(new Template(Mimetype.APPLICATION_RDF_XML, "handle",
                Template.Type.HANDLE_RESPONSE), new RequestAdapter(request,
                settings).getHandleResponseTemplate());
    }

    @Test
    public void testGetTypesNull() {
        assertNull(new RequestAdapter(request, settings).getTypes());
    }

    @Test
    public void testGetTypesSingleValid() {
        request.addParameter(settings.getTypeRequestParameterName(),
                HandleRecordType.HS_ADMIN.toString());
        assertEquals(HandleRecordType.HS_ADMIN, new RequestAdapter(request,
                settings).getTypes()[0]);
    }

    @Test
    public void testGetTypesSingleInvalid() {
        request.addParameter(settings.getTypeRequestParameterName(),
                "non-existent type");
        assertNull(new RequestAdapter(request, settings).getTypes());
    }

    @Test
    public void testGetTypesMultipleValid() {
        request.addParameter(settings.getTypeRequestParameterName(),
                new String[] { HandleRecordType.HS_ADMIN.toString(),
                        HandleRecordType.HS_ALIAS.toString(),
                        HandleRecordType.HS_NA_DELEGATE.toString() });
        RequestAdapter adapter = new RequestAdapter(request, settings);
        assertEquals(HandleRecordType.HS_ADMIN, adapter.getTypes()[0]);
        assertEquals(HandleRecordType.HS_ALIAS, adapter.getTypes()[1]);
        assertEquals(HandleRecordType.HS_NA_DELEGATE, adapter.getTypes()[2]);
    }

    @Test
    public void testGetTypesMultipleInvalid() {
        request.addParameter(settings.getTypeRequestParameterName(),
                new String[] { "this", "type", "does", "not", "exist" });
        assertNull(new RequestAdapter(request, settings).getTypes());
    }

    @Test
    public void testGetTypesMultipleMixedValidAndInvalid() {
        request.addParameter(settings.getTypeRequestParameterName(),
                new String[] { HandleRecordType.HS_ADMIN.toString(), "this",
                        HandleRecordType.HS_ALIAS.toString(), "does not",
                        HandleRecordType.HS_NA_DELEGATE.toString(), "exist" });
        RequestAdapter adapter = new RequestAdapter(request, settings);
        assertEquals(HandleRecordType.HS_ADMIN, adapter.getTypes()[0]);
        assertEquals(HandleRecordType.HS_ALIAS, adapter.getTypes()[1]);
        assertEquals(HandleRecordType.HS_NA_DELEGATE, adapter.getTypes()[2]);
    }

}
