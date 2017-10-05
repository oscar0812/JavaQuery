package com.httdocs.jquery;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class HtmlFile {

    // variables
    private String content = "";

    HtmlFile(String url) {
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
}