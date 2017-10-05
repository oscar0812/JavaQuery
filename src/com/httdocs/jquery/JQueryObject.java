package com.httdocs.jquery;

// this class will contain things like
// .attr(), .children(), .parent(), etc
// see https://api.jquery.com for info

// each object will have children and parents,
// unless its the root, or the tree is empty

public class JQueryObject {
    // original html
    private String html="";

    public JQueryObject(String html){
        this.html = html;
    }

    // try to implement following methods
    public void find(String selector){}

    public void children(){}

    public void chidren(String selector){}

    public void parent(){}

    public void parent(String selector){}

    public void attr(String attribute, String changeTo){}

    public String toString(){
        return html;
    }
}
