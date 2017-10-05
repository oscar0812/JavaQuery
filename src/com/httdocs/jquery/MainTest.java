package com.httdocs.jquery;

// ideas:
// Use hash table to make classes and ids easier to find?

import com.httdocs.jquery.Html.HtmlFile;

public class MainTest {
    public static void main(String[] args) throws Exception {
        String html = "<html>\n" +
                "\n" +
                "<head>\n" +
                "  <title></title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "  <h1>some_text</h1>\n" +
                "  <h3><p>hello</p></h3>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        HtmlFile file = new HtmlFile(html);

        JQuery jQuery = new JQuery(file);
    }
}