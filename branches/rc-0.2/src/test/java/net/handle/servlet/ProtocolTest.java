/*
 * Created by: christopher
 * Date: 3 Mar 2008
 * Time: 11:04:29
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

import java.util.ArrayList;
import java.util.List;

import net.handle.hdllib.Interface;

import org.junit.Test;

/**
 * <p>
 * JUnit test case for {@link Protocol}.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class ProtocolTest {

    @Test
    public void testForNameNull() {
        assertNull(Protocol.forName(null));
    }

    @Test
    public void testForNameHttp() {
        assertEquals(Protocol.HTTP, Protocol.forName("http"));
    }

    @Test
    public void testForNameTcp() {
        assertEquals(Protocol.TCP, Protocol.forName("tcp"));
    }

    @Test
    public void testForNameUdp() {
        assertEquals(Protocol.UDP, Protocol.forName("udp"));
    }

    @Test
    public void testForNameInvalid() {
        assertNull(Protocol.forName("a nonexistent protocol"));
    }

    @Test
    public void testForIntHttp() {
        assertEquals(Protocol.HTTP, Protocol.forInt(new Byte(
                Interface.SP_HDL_HTTP).intValue()));
    }

    @Test
    public void testForIntTcp() {
        assertEquals(Protocol.TCP, Protocol.forInt(new Byte(
                Interface.SP_HDL_TCP).intValue()));
    }

    @Test
    public void testForIntUdp() {
        assertEquals(Protocol.UDP, Protocol.forInt(new Byte(
                Interface.SP_HDL_UDP).intValue()));
    }

    @Test
    public void testForIntInvalid() {
        assertNull(Protocol.forInt(123456789));
    }

    @Test
    public void testIntsToStringNull() {
        assertEquals(null, Protocol.toString(null));
    }

    @Test
    public void testIntsToStringHttp() {
        assertEquals("http", Protocol.toString(new int[] { Protocol.HTTP
                .toInt() }));
    }

    @Test
    public void testIntsToStringTcp() {
        assertEquals("tcp", Protocol
                .toString(new int[] { Protocol.TCP.toInt() }));
    }

    @Test
    public void testIntsToStringUdp() {
        assertEquals("udp", Protocol
                .toString(new int[] { Protocol.UDP.toInt() }));
    }

    @Test
    public void testIntsToStringHttpTcp() {
        assertEquals("http, tcp", Protocol.toString(new int[] {
                Protocol.HTTP.toInt(), Protocol.TCP.toInt() }));
    }

    @Test
    public void testIntsToStringHttpTcpUdp() {
        assertEquals("http, tcp, udp", Protocol.toString(new int[] {
                Protocol.HTTP.toInt(), Protocol.TCP.toInt(),
                Protocol.UDP.toInt() }));
    }

    @Test
    public void testIntsToStringInvalidSingle() {
        assertNull(Protocol.toString(new int[] { 123456789 }));
    }

    @Test
    public void testIntsToStringInvalidMultiple() {
        assertNull(Protocol.toString(new int[] { 123456789, 987654321 }));
    }

    @Test
    public void testIntsToStringEmptyArray() {
        assertNull(Protocol.toString(new int[] {}));
    }

    @Test
    public void testIntsToStringMixedValidAndInvalid() {
        assertEquals("http, tcp, udp", Protocol.toString(new int[] {
                Protocol.HTTP.toInt(), 123456789, Protocol.TCP.toInt(),
                987654321, Protocol.UDP.toInt()}));
    }

    @Test
    public void testToIntArrayFromListNull() {
        assertNull(Protocol.toIntArrayFromList(null));
    }

    @Test
    public void testToIntArrayFromListEmpty() {
        assertNull(Protocol.toIntArrayFromList(new ArrayList<String>()));
    }

    @Test
    public void testToIntArrayFromListHttp() {
        List<String> list = new ArrayList<String>();
        list.add("http");
        assertEquals(Protocol.HTTP.toInt(), Protocol.toIntArrayFromList(list)[0]);
    }

    @Test
    public void testToIntArrayFromListTcp() {
        List<String> list = new ArrayList<String>();
        list.add("tcp");
        assertEquals(Protocol.TCP.toInt(), Protocol.toIntArrayFromList(list)[0]);
    }

    @Test
    public void testToIntArrayFromListUdp() {
        List<String> list = new ArrayList<String>();
        list.add("udp");
        assertEquals(Protocol.UDP.toInt(), Protocol.toIntArrayFromList(list)[0]);
    }

    @Test
    public void testToIntArrayFromListHttpTcp() {
        List<String> list = new ArrayList<String>();
        list.add("http");
        list.add("tcp");
        int[] array = Protocol.toIntArrayFromList(list);
        assertEquals(Protocol.HTTP.toInt(), array[0]);
        assertEquals(Protocol.TCP.toInt(), array[1]);
    }

    @Test
    public void testToIntArrayFromListHttpTcpUdp() {
        List<String> list = new ArrayList<String>();
        list.add("http");
        list.add("tcp");
        list.add("udp");
        int[] array = Protocol.toIntArrayFromList(list);
        assertEquals(Protocol.HTTP.toInt(), array[0]);
        assertEquals(Protocol.TCP.toInt(), array[1]);
        assertEquals(Protocol.UDP.toInt(), array[2]);
    }

    @Test
    public void testToIntArrayNull() {
        assertNull(Protocol.toIntArray(null));
    }

    @Test
    public void testToIntArrayEmptyString() {
        assertNull(Protocol.toIntArray(""));
    }

    @Test
    public void testToIntArrayHttp() {
        assertEquals(Protocol.HTTP.toInt(), Protocol.toIntArray("http")[0]);
    }

    @Test
    public void testToIntArrayTcp() {
        assertEquals(Protocol.TCP.toInt(), Protocol.toIntArray("tcp")[0]);
    }

    @Test
    public void testToIntArrayUdp() {
        assertEquals(Protocol.UDP.toInt(), Protocol.toIntArray("udp")[0]);
    }

    @Test
    public void testToIntArrayHttpTcp() {
        int[] array = Protocol.toIntArray("http, tcp");
        assertEquals(Protocol.HTTP.toInt(), array[0]);
        assertEquals(Protocol.TCP.toInt(), array[1]);
    }

    @Test
    public void testToIntArrayHttpTcpUdp() {
        int[] array = Protocol.toIntArray("http, tcp, udp");
        assertEquals(Protocol.HTTP.toInt(), array[0]);
        assertEquals(Protocol.TCP.toInt(), array[1]);
        assertEquals(Protocol.UDP.toInt(), array[2]);
    }

    @Test
    public void testToIntArrayHttpTcpUdpNoSpaces() {
        int[] array = Protocol.toIntArray("http,tcp,udp");
        assertEquals(Protocol.HTTP.toInt(), array[0]);
        assertEquals(Protocol.TCP.toInt(), array[1]);
        assertEquals(Protocol.UDP.toInt(), array[2]);
    }

    @Test
    public void testToIntArrayMixedValidAndInvalid() {
        int[] array = Protocol.toIntArray("http, non-existent, tcp, does, not, exist, udp");
        assertEquals(Protocol.HTTP.toInt(), array[0]);
        assertEquals(Protocol.TCP.toInt(), array[1]);
        assertEquals(Protocol.UDP.toInt(), array[2]);
    }

    @Test
    public void testToIntHttp() {
        assertEquals(new Byte(Interface.SP_HDL_HTTP).intValue(), Protocol.HTTP.toInt());
    }

    @Test
    public void testToIntTcp() {
        assertEquals(new Byte(Interface.SP_HDL_TCP).intValue(), Protocol.TCP.toInt());
    }

    @Test
    public void testToIntUdp() {
        assertEquals(new Byte(Interface.SP_HDL_UDP).intValue(), Protocol.UDP.toInt());
    }

    @Test
    public void testToStringHttp() {
        assertEquals("http", Protocol.HTTP.toString());
    }

    @Test
    public void testToStringTcp() {
        assertEquals("tcp", Protocol.TCP.toString());
    }

    @Test
    public void testToStringUdp() {
        assertEquals("udp", Protocol.UDP.toString());
    }

}
