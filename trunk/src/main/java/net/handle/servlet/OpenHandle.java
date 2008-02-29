/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: Nov 8, 2007
 * Time: 11:37:16 AM
 *
 * <p>Copyright (C) 2007 Nature Publishing Group, Inc.</p>
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

import org.apache.commons.lang.StringUtils;
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
 * TODO Document OpenHandle
 * </p>
 * 
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
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
     * @param args the handle rtf id as first argument (any other args are
     *            ignored)
     * @throws Exception if anything goes wrong
     */
    public static void main(String[] args) throws Exception {
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
     * @param handle the handle rtf id
     * @return the handle response
     * @throws HandleException if the handle could not be got
     */
    public HandleResponseAdapter get(String handle) throws HandleException {
        ResolutionRequest request = new ResolutionRequest(handle.getBytes(),
                null, null, null);
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

    public void setTraceMessages(boolean traceMessages) {
        resolver.traceMessages = traceMessages;
    }

    public void setPreferredProtocols(int[] preferredProtocols) {
        resolver.setPreferredProtocols(preferredProtocols);
    }

    /**
     * <p>
     * Attempts to convert a comma-separated string of protocol type names to an
     * integer array, representing them in a way a Handle resolver will
     * understand. If the supplied String is <code>null</code> or empty, then
     * <code>null</code> will be returned.
     * </p>
     * 
     * @param s a comma-separated list of protocols (http, tcp, udp) in order of
     *            preference
     * @return the protocols as an int array which can be used to set the
     *         preferred protocols for the handle resolver
     */
    public static int[] toProtocolArray(String s) {
        if (StringUtils.isNotBlank(s)) {
            String[] protocols = s.split(",");
            int[] array = new int[protocols.length];
            for (int i = 0; i < protocols.length; i++) {
                String p = protocols[i].trim();
                if (p.equals("http")) {
                    array[i] = new Byte(Interface.SP_HDL_HTTP).intValue();
                } else if (p.equals("tcp")) {
                    array[i] = new Byte(Interface.SP_HDL_TCP).intValue();
                } else if (p.equals("udp")) {
                    array[i] = new Byte(Interface.SP_HDL_UDP).intValue();
                }
            }

            return array;
        }

        return null;
    }
}
