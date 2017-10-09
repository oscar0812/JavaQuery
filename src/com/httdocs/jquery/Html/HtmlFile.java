package com.httdocs.jquery.Html;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class HtmlFile {

    // variables
    private String content = "";

    public HtmlFile(String url) {
        setText(url);
    }

    // set the text once in a constructor call
    // to reduce file I/O redundancy
    private void setText(String urlPath) {
        InputStream stream = getStream(urlPath);
        if (stream == null) {
            // if its not a url or file, its html as a string
            content = urlPath;
        } else {
            // get text from stream
            content = streamToStr(stream);
        }
    }

    // in order to work with url, files, and normal text
    private InputStream getStream(String path) {
        // check if URL is valid, if not, it might be a local file
        try {
            if (new java.io.File(path).exists()) {
                // if its a local file, return the stream of that
                return new FileInputStream(path);
            } else {
                // not a file
                try {
                    return new URL(path).openStream();
                } catch (Exception e) {
                    // if opening the url stream fails, its not a valid url
                    return null;
                }
            }
        } catch (Exception e) {
            // error occurred along the way
            System.out.println(e.toString());
            return null;
        }
    }

    // attach a bufferedReader to inputStream reader and return contents
    private String streamToStr(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // String builder is just a fancy class to build strings
            StringBuilder builder = new StringBuilder();
            // will hold current text line
            String line = reader.readLine();

            // keep reading till end of InputStream
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = reader.readLine();
            }
            // remove the extra \n
            builder.deleteCharAt(builder.length() - 1);

            return builder.toString();
        } catch (Exception e) {
            // error caught, something wong
            System.out.println(e.toString());
            return "";
        }
    }

    public String getText() {
        return content;
    }

    // -------------- help methods --------------
    public static boolean isVoidTag(String tag) {
        if (tag.startsWith(Constants.LT + "")) {
            String innerTag = parseElementOut(tag);

            for (String t : Constants.VOID_ELEMENTS) {
                if (t.equals(innerTag)) {
                    return true;
                }
            }
            return false;
        } else {
            System.out.println("NOT A TAG = " + tag);
            return false;
        }
    }

    public static String parseElementOut(String tag) {
        if (tag.startsWith(Constants.LT + "")) {
            String innerTag = tag.substring(0, tag.indexOf(Constants.GT));
            innerTag = innerTag.replaceAll(Constants.GT+"","");
            innerTag = innerTag.replaceAll(Constants.LT+"","");
            innerTag = innerTag.replaceAll("/","");

            innerTag = innerTag.split("\\s+")[0];
            return innerTag.trim();
        } else {
            System.out.println("NOT A TAG = " + tag);
            return tag;
        }
    }

    public static boolean isNonImportant(String tag){
        if(tag.startsWith(Constants.LT+"")) {
            String fixed = tag.replaceAll("\\s+", "");
            return fixed.startsWith(Constants.LT+"!");

        }else{
            System.out.println("NOT A TAG = " + tag);
            return false;
        }
    }

    public static boolean isSelfClosing(String tag) {
        tag = tag.replaceAll("\\s+", "");
        return tag.endsWith("/" + Constants.GT);
    }

    public static boolean isClosingTag(String tag){
        tag = tag.replaceAll("\\s+","");
        return tag.startsWith(Constants.LT+"/");
    }

    public static String ignoreEmbeddedLang(String html){
        String question = "<[?].*?[?]>";
        String mod = "<[%].*?[%]>";
        String comment = "<[!].*?>";

        html = html.replaceAll(question, "").replaceAll(mod, "").
                replaceAll(comment, "").replaceAll("< <","<").
                replaceAll("\n+","\n");

        return removeFrom(removeFrom(html, "<!--","-->"), "<script", "/script>");
    }

    // remove all the elements in html that are in the range from ind of first to ind of secondTag
    private static String removeFrom(String html, String firstTag, String secondTag){

        StringBuilder builder = new StringBuilder(html);

        while(builder.indexOf(firstTag) !=-1){
            int a = builder.indexOf(firstTag);
            int b = builder.indexOf(secondTag)+secondTag.length();

            //System.out.println(builder.substring(a, b));
            builder.delete(a,b);
        }

        return builder.toString();
    }
}