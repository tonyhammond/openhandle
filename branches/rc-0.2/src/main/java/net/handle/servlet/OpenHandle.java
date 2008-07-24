/*
 * Created by: christopher
 * Date: Nov 8, 2007
 * Time: 11:37:16 AM
 *
 * <p>Copyright (C) 2007 Nature Publishing Group, Inc.</p>
 *
 * <p><a rel="license" href="http://creativecommons.org/licenses/GPL/2.0/">
 * <img alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/GPL/2.0/88x62.png" /></a><br />
 * This work is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/GPL/2.0/">Creative Commons GNU
 * General Public License License</a>.</p>
 */

package net.handle.servlet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringStyle;

import net.handle.hdllib.AbstractMessage;
import net.handle.hdllib.AbstractResponse;
import net.handle.hdllib.HandleException;
import net.handle.hdllib.HandleResolver;
import net.handle.hdllib.HandleValue;
import net.handle.hdllib.Interface;
import net.handle.hdllib.ResolutionRequest;
import net.handle.hdllib.ResolutionResponse;

/**
 * <p>
 * OpenHandle Entry Point
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class OpenHandle {

    protected static ToStringStyle TO_STRING_STYLE = ToStringStyle.MULTI_LINE_STYLE;

    private static final int[] PREFERRED_PROTOCOLS = { Interface.SP_HDL_TCP,
            Interface.SP_HDL_HTTP };

    private static final boolean TRACE_MESSAGES = true;

    private HandleResolver resolver;

    /**
     * <p>
     * </p>
     *
     * @param args the handle id as first argument (currently, additional args
     *        are ignored)
     * @throws Exception if anything goes wrong
     */
    public static void main(String[] args) throws Exception {
        // TODO handle indices, types, traceMessage and protocols args
        if (args.length < 1) {
            System.out.println("usage: please provide the handle identifier.");
        } else {
            System.out.println(new OpenHandle().get(args[0]));
        }
    }

    /**
     * <p>
     * </p>
     */
    public OpenHandle() {
        resolver = new HandleResolver();
        resolver.traceMessages = TRACE_MESSAGES;
        resolver.setPreferredProtocols(PREFERRED_PROTOCOLS);
    }

    /**
     * <p>
     * </p>
     *
     * @param handleResolver the ready-configured resolver
     */
    public OpenHandle(HandleResolver handleResolver) {
        resolver = handleResolver;
    }

    /**
     * <p>
     * </p>
     *
     * @param protocols the preferred protocols
     * @param traceMessages
     */
    public OpenHandle(int[] protocols, boolean traceMessages) {
        resolver = new HandleResolver();
        resolver.traceMessages = traceMessages;
        resolver.setPreferredProtocols(protocols);
    }

    /**
     * <p>
     * Get the handle response.
     * </p>
     *
     * @param handle the handle identifier
     * @return the handle response
     * @throws HandleException if the handle could not be got
     */
    public HandleResponseAdapter get(String handle) throws HandleException {
        ResolutionRequest request = new ResolutionRequest(handle.getBytes(),
                null, null, null);
        return get(handle, request);
    }

    /**
     * <p>
     * Get the handle response; limit the values included in the response to the
     * specified indices and record types. Pass <code>null</code> to either
     * indices or types to specify no limiting for either of these parameters.
     * </p>
     *
     * @param handle the handle identifier
     * @param indices the indices to include in the response
     * @param types the record types to include in the response
     * @return the handle response
     * @throws HandleException if the handle could not be got
     */
    public HandleResponseAdapter get(String handle, int[] indices,
            HandleRecordType[] types) throws HandleException {
        ResolutionRequest request = new ResolutionRequest(handle.getBytes(),
                HandleRecordType.encode(types), indices, null);
        return get(handle, request);
    }

    /**
     * <p>
     * Set handle client option to traceMessages.
     * </p>
     *
     * @param traceMessages the traceMessages
     */
    public void setTraceMessages(boolean traceMessages) {
        resolver.traceMessages = traceMessages;
    }

    /**
     * <p>
     * Set handle client option for preferredProtocols
     * </p>
     *
     * @param preferredProtocols the preferredProtocols
     */
    public void setPreferredProtocols(int[] preferredProtocols) {
        resolver.setPreferredProtocols(preferredProtocols);
    }

    private HandleResponseAdapter get(String handle, ResolutionRequest request)
            throws HandleException {
        AbstractResponse response = resolver.processRequest(request);
        int responseCode = response.responseCode;
        if (responseCode == AbstractMessage.RC_SUCCESS) {
            List<HandleValueAdapter> handleData = new ArrayList<HandleValueAdapter>();
            HandleValue[] values = ((ResolutionResponse) response)
                    .getHandleValues();
            for (HandleValue value : values) {
                handleData.add(new HandleValueAdapter(value));
            }
            return new HandleResponseAdapter(handle, handleData);
        } else {
            throw new HandleException(responseCode, AbstractMessage
                    .getResponseCodeMessage(responseCode));
        }
    }
}
