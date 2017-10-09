package com.httdocs.jquery.jquery;

// this class will contain things like
// .attr(), .children(), .parent(), etc
// see https://api.jquery.com for info

// each object will have children and parents,
// unless its the root, or the tree is empty


import com.httdocs.jquery.Html.Constants;
import com.httdocs.jquery.Html.HtmlFile;

import java.util.ArrayList;
import java.util.Collections;

public class JQueryObject extends JSkeleton implements Comparable{
    // variables
    private String original ="";
    private String element = "";
    private String html = "";
    private String text = "";

    private int depthInTree=-1;
    private JQueryList children = new JQueryList();
    private JQueryObject parent = null;

    private String id ="";
    private ArrayList<String> classes = new ArrayList<>();

    JQueryObject(String original, boolean hasCloseTag) {
        original = original.trim();
        this.original = original;//setAlign(original);
        element = HtmlFile.parseElementOut(original);
        if(hasCloseTag) {
            html = original.substring(original.indexOf(Constants.GT) + 1);
            html = html.substring(0, html.lastIndexOf(Constants.LT)).trim();
            //html = html.replaceAll("\n\\s+","\n");
            text = html.replaceAll("<.*?>", "");
        }
    }

    // useful for checking if a certain object = object
    @Override
    public int compareTo(Object o) {
        JQueryObject obj = (JQueryObject)o;

        if(this.getID().equals(obj.getID()) &&
                (this.getDepth() == obj.getDepth()) && this.getOriginal().equals(obj.getOriginal())){
            return 0;
        }
        return -1;
    }

    // -------------- stuff I like to see --------------
    void addChild(JQueryObject child){
        children.addFront(child);
    }

    void setParent(JQueryObject parent){
        this.parent = parent;
    }

    void setDepth(int depth) {
        this.depthInTree = depth;
    }

    int getDepth() {
        return depthInTree;
    }

    private String setAlign(String html){
        String[] arr = html.split("\n");
        if(arr.length <2){
            return html;
        }
        else{
            String del = arr[arr.length-1];
            del = del.substring(0, del.indexOf(Constants.LT));
            StringBuilder build = new StringBuilder();
            for(int x = 0; x<arr.length; x++){
                build.append(arr[x].replaceFirst(del, ""));
                build.append("\n");
            }

            build.deleteCharAt(build.length()-1);

            return build.toString();
        }
    }

    String getOriginal() {
        return original;
    }

    String extractIdOrClass(String header, String parseOut){
        // parseOut == "id' || parseOut == "class"
        for(JQueryObject object: children.getList()){
            header  = header.replaceAll(object.getOriginal(),"");
        }

        header = header.replaceAll("\n\\s+","\n");
        header = header.substring(0, header.indexOf(Constants.GT)+1);

        if(header.replaceAll("\\s+","").contains(parseOut+"=")) {
            // get rid of annoying whitespace
            header = header.replaceAll("("+parseOut+")\\s+(=)\\s+", parseOut+"=");

            int start = header.indexOf(parseOut+"=")+parseOut.length()+1;
            // quotation or apostrophe
            char qa =header.charAt(start);
            int end = header.indexOf(qa, start+1);
            header = header.substring(start+1, end);
            return header;
        }

        return "";
    }

    // helps search a list with a query

    // -------------- methods other users will see --------------

    public JQueryList children(){
        return children;
    }

    public String getID(){
        if(!id.isEmpty())
            return id;
        id = extractIdOrClass(original, "id");

        return id;
    }

    public ArrayList<String> getClasses(){
        if(!classes.isEmpty())
            return classes;
        String c = extractIdOrClass(original, "class");
        Collections.addAll(classes, c.split("\\s+"));
        return classes;
    }

    public JQueryList children(String selector){
        String[] broken = breakElClID(selector);
        JQueryList list = new JQueryList();
        list.add(children);
        for(int x =0; x<broken.length; x++){
            list = search(list, broken[x]);
        }

        return list;
    }

    public JQueryObject parent() {
        return parent;
    }

    public JQueryList parent(String selector){
        JQueryList list = new JQueryList();
        JQueryObject runner = this;

        while(runner.parent()!=null){
            list.add(runner);
            runner = runner.parent();
        }

        String[] br = breakElClID(selector);

        for(int x = 0; x<br.length; x++){
            list = search(list, br[x]);
        }

        return list;
    }

    public JQueryList find(String selector){
        JQueryList allChildren = new JQueryList();
        addAllCOC(allChildren, children);

        // elements, ids, and classes could be searched
        // elements could only be in index 0 i think

        String[] broken = breakElClID(selector);

        for(int x =0; x<broken.length; x++){
            allChildren = search(allChildren, broken[x]);
        }
        return allChildren;
    }

    public String element() {
        return element;
    }

    public String html(){
        return setAlign(html);
    }
    public String text(){
        return text;
    }

    public String toString(){
        return setAlign(original);
    }
}
