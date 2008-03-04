package net.handle.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    }

}
