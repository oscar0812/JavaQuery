package com.httdocs.jquery.jquery;

import java.util.ArrayList;

// JQueryObjects must have these methods
public abstract class JSkeleton {
    abstract JQueryList children();

    abstract JQueryList children(String selector);
    abstract ArrayList<String> getClasses();
    abstract JQueryObject parent();
    abstract JQueryList parent(String selector);
    abstract JQueryList find(String selector);
    abstract String element();
    abstract String html();
    abstract String text();

    // actual implementations
    JQueryList search(JQueryList queryList, String queryString){
        // remove any trailing . or #
        String fixed = queryString;

        // Add to another list
        JQueryList finalList = new JQueryList();

        if(queryString.startsWith(".") || queryString.startsWith("#"))
            fixed = queryString.substring(1);

        if(queryString.startsWith(".")){
            // is a class
            for(JQueryObject object: queryList.getList()){
                if(object.getClasses().contains(fixed)){
                    finalList.add(object);
                }
            }
        }
        else if(queryString.startsWith("#")){
            // is an id
            for(JQueryObject object: queryList.getList()){
                if(object.getID().equals(fixed)){
                    finalList.add(object);
                }
            }
        }
        else{
            // is just an element name
            for(JQueryObject object: queryList.getList()){
                if(object.element().equals(fixed)){
                    finalList.add(object);
                }
            }
        }

        return finalList;
    }

    // break string into element, classes, and id
    // for example: a.name#id OR .class#hello
    // and return {a, .name, #id} OR {.class, #hello}
    String[] breakElClID(String query){
        //remove duplicates
        query = query.replaceAll("\\.+",".").replaceAll("#+","#");
        return query.split("(?=#)|(?=\\.)");
    }

    // addAllChildrenOfChildren
    void addAllCOC(JQueryList addList, JQueryList children){
        if(!children.getList().isEmpty()){
            addList.add(children);

            for(JQueryObject object: children.getList()){
                JQueryList list = object.children();
                addAllCOC(addList, list);
            }
        }
    }
}
