package com.httdocs.jquery;

import com.httdocs.jquery.Html.Constants;
import com.httdocs.jquery.Html.HtmlFile;

import java.util.Collections;
import java.util.Stack;

public class JQuery {

    private JQueryObject root = null;

    public JQuery(HtmlFile file) {
        String html = file.getText();
        JQueryList jQueryObjects = getObjects(html.trim());

        // reverse to put the root on front of the list
        Collections.reverse(jQueryObjects);
        buildTree(jQueryObjects);
    }

    private void buildTree(JQueryList nodes) {

        for (int x = 0; x < nodes.size(); x++) {
            JQueryObject start = nodes.get(x);
            for (int y = x+1; y < nodes.size(); y++) {
                JQueryObject run = nodes.get(y);

                if(run.getParent() != null)
                    break;

                if(start.getDepth()+1 == run.getDepth()){
                    //System.out.println("START = "+start.getDepth()+", RUN = "+run.getDepth()+" ["+x+", "+y+"]");

                    if(run.getParent() == null){
                        run.setParent(start);
                        start.addChild(run);
                    }
                    else{
                        // has a parent already
                        break;
                    }
                }
            }
        }

        root = nodes.get(0);
        printList(root.children().eq(0).children());
    }

    private JQueryList getObjects(String html) {
        JQueryList queryObjects = new JQueryList();
        Stack<Integer> stack = new Stack<>();
        // need to know depth of node in tree
        int levelDown = 0;
        for (int x = 0; x < html.length(); x++) {
            // get tag
            if (html.charAt(x) == Constants.LT) {
                int end = (html.substring(x).indexOf(Constants.GT) + x) + 1;
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

    private void printList(JQueryList a) {
        for (Object b : a) {
            System.out.println(b + "\n======================");
        }
    }

    public String closeTag(String tag) {
        if (tag.isEmpty())
            return tag;

        if (!tag.startsWith(Constants.LT + "")) {
            tag = Constants.LT + tag;
        }
        if (!tag.endsWith(Constants.GT + "")) {
            tag = tag + Constants.GT;
        }

        if (!tag.startsWith(Constants.LT + "/")) {
            int i = tag.indexOf(Constants.LT) + 1;
            tag = tag.substring(0, i) + "/" + tag.substring(i);
        }

        return tag;
    }

    private boolean isComplete(String html) {
        if (html.startsWith(Constants.LT + "")) {
            int i = html.indexOf(Constants.GT);

            String tag = html.substring(0, i + 1);

            return html.startsWith(tag) && html.endsWith(closeTag(tag));
        }
        return false;
    }
}