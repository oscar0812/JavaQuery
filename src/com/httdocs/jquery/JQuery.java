package com.httdocs.jquery;

import com.httdocs.jquery.Html.Constants;
import com.httdocs.jquery.Html.HtmlFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class JQuery {

    private JQueryObject root = null;

    public JQuery(HtmlFile file) {
        String html = file.getText();
        ArrayList<JQueryObject> jQueryObjects = getObjects(html.trim());

        // reverse to put the root on front of the list
        Collections.reverse(jQueryObjects);
        buildTree(jQueryObjects);
    }

    private void buildTree(ArrayList<JQueryObject> nodes){
        printList(nodes);
        // the patter is this
        // html = 0
        // head = 1
        // title = 2
        // body = 1
        // div = 2
        // if number keeps incrementing, its a child
        // if you get to a number >= then its not a child
    }

    private ArrayList<JQueryObject> getObjects(String html) {
        ArrayList<JQueryObject> queryObjects = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        // need to know depth of node in tree
        int levelDown = 0;
        for (int x = 0; x < html.length(); x++) {
            // get tag
            if (html.charAt(x) == Constants.lt) {
                int end = (html.substring(x).indexOf(Constants.gt) + x) + 1;
                String tag = html.substring(x, end);

                if (!tag.contains("/")) {
                    // not a closing tag
                    stack.push(x);
                    levelDown++;
                } else {
                    levelDown--;
                    int start = stack.pop();
                    String htmlSnip = html.substring(start, end);
                    JQueryObject obj = new JQueryObject(htmlSnip);
                    obj.setDepth(levelDown);
                    queryObjects.add(obj);
                }
            }
        }
        return queryObjects;
    }

    private void printList(ArrayList a) {
        for (Object b : a) {
            System.out.println(b + "\n======================");
        }
    }

    public String closeTag(String tag) {
        if (tag.isEmpty())
            return tag;

        if (!tag.startsWith(Constants.lt + "")) {
            tag = Constants.lt + tag;
        }
        if (!tag.endsWith(Constants.gt + "")) {
            tag = tag + Constants.gt;
        }

        if (!tag.startsWith(Constants.lt + "/")) {
            int i = tag.indexOf(Constants.lt) + 1;
            tag = tag.substring(0, i) + "/" + tag.substring(i);
        }

        return tag;
    }

    private boolean isComplete(String html) {
        if (html.startsWith(Constants.lt + "")) {
            int i = html.indexOf(Constants.gt);

            String tag = html.substring(0, i + 1);

            return html.startsWith(tag) && html.endsWith(closeTag(tag));
        }
        return false;
    }
}