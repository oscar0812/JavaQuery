package com.httdocs.jquery;

// this class will contain things like
// .attr(), .children(), .parent(), etc
// see https://api.jquery.com for info

// each object will have children and parents,
// unless its the root, or the tree is empty

public class JQueryObject {
    // original html
    private String html="";
    private int depthInTree=-1;
    private JQueryList children = new JQueryList();
    private JQueryObject parent = null;

    public JQueryObject(String html){
        this.html = html;
    }


    public void addChild(JQueryObject child){
        children.add(child);
    }

    public void setParent(JQueryObject parent){
        this.parent = parent;
    }

    public JQueryObject getParent() {
        return parent;
    }

    public void setDepth(int depth) {
        this.depthInTree = depth;
    }

    public int getDepth() {
        return depthInTree;
    }

    // try to implement following methods
    public void find(String selector){}

    public JQueryList children(){
        return children;
    }

    public void chidren(String selector){}

    public void parent(){}

    public void parent(String selector){}

    public void attr(String attribute, String changeTo){}

    public String toString(){
        return "[Depth = "+depthInTree+"]\n"+html;
    }
}
