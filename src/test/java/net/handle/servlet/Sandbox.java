package net.handle.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.handle.hdllib.Util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

public class Sandbox {

    public static void main(String[] args) throws Exception {
        for (int i : Protocol.toIntArray("http,tcp,udp")) {
            System.out.println(i);
        }

        String text = "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8; mxb=100000; mxs=5.0,image/png,*/*;q=0.5";
        // "((application|audio|image|multipart|text|video|\*)/[a-zA-Z_0-9\-+*]+,?\s?)+;?((q|mxb|mxs)=[0-9]?.[0-9]*;?\s?)*"
        String regex = "((application|audio|image|multipart|text|video|\\*)/[a-zA-Z_0-9\\-+*]+,?\\s?)+;?((q|mxb|mxs)=[0-9]?.[0-9]*;?\\s?)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<String> matches = new ArrayList<String>();
        while (matcher.find()) {
            matches.add(text.substring(matcher.start(), matcher.end()));
        }

        for (String match : matches) {
            String[] split = match.split(";");

            if (split != null && split.length > 0) {
                String rawTypes = split[0];

                if (split.length > 1) {
                    // the first is types, the others are params
                    String[] rawParams = new String[split.length - 1];
                    for (int i = 1; i < split.length; i++) {
                        rawParams[i - 1] = split[i];
                    }
                }
            }
        }

//        String[] indices = new String[] {"0", "1", "2", "", null, "4", "crap"};
//        int[] indicesConverted = Sandbox.getIndices(indices);
//        System.out.println("-------------------");
//        for (int i : indicesConverted) {
//            System.out.println(i);
//        }
//        System.out.println("-------------------");
//
//        String[] types = new String[] {"HS_ADMIN", "HS_SITE", "HS_NA_DELEGATE", "HS_SERV", "", "HS_PRIMARY", "HS_VLIST"};
//        byte[][] encoded = encode(types);
//        for (byte[] a : encoded) {
//            for (byte b : a) {
//                System.out.println(b);
//            }
//        }
//        System.out.println("--------");
//        byte[][] enumEncoded = HandleRecordType.encode(types);
//        for (byte[] a : enumEncoded) {
//            for (byte b : a) {
//                System.out.println(b);
//            }
//        }
//        System.out.println("--------");
//        System.out.println(encoded.length == enumEncoded.length);
//        for (int i = 0; i < encoded.length; i++) {
//            System.out.println(Arrays.equals(encoded[i], enumEncoded[i]));
//        }
    }

//    private static int[] getIndices(String[] indices) {
//        if (indices != null && indices.length > 0) {
//            Integer[] indicesTransitional = new Integer[indices.length];
//            for (int i = 0; i < indices.length; i++) {
//                try {
//                    indicesTransitional[i] = new Integer(indices[i]);
//                } catch (NumberFormatException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//
//            int i = 0;
//            for (Integer integer : indicesTransitional) {
//                if (integer != null) {
//                    i++;
//                }
//            }
//
//            int[] indicesConverted = new int[i];
//            i = 0;
//            for (Integer integer : indicesTransitional) {
//                if (integer != null) {
//                    indicesConverted[i] = integer;
//                    i++;
//                }
//            }
//
//            return indicesConverted;
//        }
//
//        return null;
//    }
//
//    private static byte[][] encode(String[] types) {
//        byte[][] encoded = new byte[types.length][];
//        for (int i = 0; i < types.length; i++) {
//            byte[] type = Util.encodeString(types[i]);
//            encoded[i] = type;
//        }
//
//        return encoded;
//    }

}
