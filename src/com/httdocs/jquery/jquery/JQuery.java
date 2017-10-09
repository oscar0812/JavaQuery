package com.httdocs.jquery.jquery;

import com.httdocs.jquery.Html.Constants;
import com.httdocs.jquery.Html.HtmlFile;

import java.util.Stack;

// this class parses the html and builds the tree of JQueryObjects
public class JQuery {

    private JQueryObject root = null;

    public JQuery(HtmlFile file) {
        String html = file.getText();

        // remove things like <? ?> and <% %>
        html = HtmlFile.ignoreEmbeddedLang(html);
        JQueryList jQueryObjects = getObjects(html.trim());

        // reverse to put the root on front of the list
        jQueryObjects.reverse();
        buildTree(jQueryObjects);
    }

    public JQueryObject getRoot() {
        return root;
    }

    // get html and make jqueryObjects with it
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

                if (!HtmlFile.isVoidTag(tag) && !HtmlFile.isSelfClosing(tag) && !HtmlFile.isNonImportant(tag)) {
                    // reach here if its a html tag that requires closing, such as
                    // <p>text</p>
                    if (!HtmlFile.isClosingTag(tag)) {
                        // not a closing tag
                        stack.push(x);
                        levelDown++;
                    } else {

                        levelDown--;
                        int start = stack.pop();
                        String htmlSnip = html.substring(start, end);
                        JQueryObject obj = new JQueryObject(htmlSnip, true);
                        obj.setDepth(levelDown);
                        queryObjects.add(obj);
                    }
                } else {
                    // is void tag, doesn't have a close tag
                    // or tag closes itself, like <link /> or is useless like <!--comment-->
                    String htmlSnip = html.substring(x, end);
                    JQueryObject obj = new JQueryObject(htmlSnip, false);
                    obj.setDepth(levelDown);
                    queryObjects.add(obj);
                }
            }
        }
        return queryObjects;
    }

    // get JqueryObjects and make a tree
    private void buildTree(JQueryList nodes) {

        for (int x = 0; x < nodes.size(); x++) {
            JQueryObject start = nodes.eq(x);
            for (int y = x + 1; y < nodes.size(); y++) {
                JQueryObject run = nodes.eq(y);

                if (run.parent() != null)
                    break;

                if (start.getDepth() + 1 == run.getDepth()) {
                    if (run.parent() == null) {
                        run.setParent(start);
                        start.addChild(run);
                    }
                }
            }
        }
        root = nodes.eq(0);
    }

    private void printArr(JQueryList list) {
        for (JQueryObject b : list.getList()) {
            System.out.println(b);
            System.out.println("\n=============\n");
        }
    }
}