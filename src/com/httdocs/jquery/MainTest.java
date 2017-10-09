package com.httdocs.jquery;

// ideas:
// Use hash table to make classes and ids easier to find?

import com.httdocs.jquery.Html.HtmlFile;
import com.httdocs.jquery.jquery.JQuery;
import com.httdocs.jquery.jquery.JQueryObject;

public class MainTest {
    public static void main(String[] args) {
        String html = "http://www.dictionary.com/browse/dog";
        HtmlFile file = new HtmlFile(html);
        JQuery jQuery = new JQuery(file);
        JQueryObject obj = jQuery.getRoot();

        System.out.println(obj.find(".def-set").html());
    }
}