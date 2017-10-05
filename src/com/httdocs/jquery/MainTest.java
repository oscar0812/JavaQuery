package com.httdocs.jquery;


// ideas:
// Use hash table to make classes and ids easier to find?

import java.net.URL;

public class MainTest {
    public static void main(String[] args) throws Exception{
        HtmlFile file = new HtmlFile("file:///Users/oscartorres/Dropbox/Homepage/main/main_page.html");
        System.out.println(file.getText());
    }
}