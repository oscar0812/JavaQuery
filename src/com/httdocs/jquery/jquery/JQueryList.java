package com.httdocs.jquery.jquery;

import java.util.ArrayList;

public class JQueryList extends JSkeleton{
    private final ArrayList<JQueryObject> MAIN_LIST = new ArrayList<>();

    JQueryList() {
        super();
    }

    public JQueryObject eq(int index) {
        try {
            return MAIN_LIST.get(index);
        } catch (java.lang.IndexOutOfBoundsException e) {
            System.out.println("NO SUCH ELEMENT IN INDEX " + index);
            throw new IndexOutOfBoundsException();
        }
    }

    void addFront(JQueryObject object) {
        MAIN_LIST.add(0, object);
    }

    void remove(JQueryObject object){
        MAIN_LIST.remove(object);
    }

    public int size() {
        return MAIN_LIST.size();
    }

    void add(JQueryObject object) {
        MAIN_LIST.add(object);
    }

    void add(JQueryList list){
        for(JQueryObject obj: list.getList()) {
            if(!MAIN_LIST.contains(obj)) {
                MAIN_LIST.add(obj);
            }
        }
    }


    java.util.ArrayList<JQueryObject> getList() {
        return MAIN_LIST;
    }

    void reverse() {
        for (int x = 0; x < MAIN_LIST.size() / 2; x++) {
            int y = (MAIN_LIST.size() - x) - 1;

            JQueryObject last = eq(y);
            JQueryObject first = eq(x);

            MAIN_LIST.set(x, last);
            MAIN_LIST.set(y, first);
        }
    }

    @Override
    public String toString() {

        if(getList().isEmpty()){
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (int x = 0; x < MAIN_LIST.size(); x++) {
            builder.append(eq(x).element());
            builder.append(", ");
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);

        builder.append("]");
        return builder.toString().trim();
    }

    // ------ JQueryObject methods

    public JQueryList find(String selector){
        String[] br = breakElClID(selector);
        JQueryList list = new JQueryList();
        list.add(this);
        for(int x = 0; x<br.length; x++){
            list = search(list, br[x]);
        }

        return list;
    }

    // just return every child of the list
    public JQueryList children(){
        JQueryList list = new JQueryList();
        for(JQueryObject o: MAIN_LIST){
            list.add(o.children());
        }

        return list;
    }

    public JQueryList children(String selector){
        JQueryList list = new JQueryList();
        for(JQueryObject o: MAIN_LIST){
            list.add(o.children(selector));
        }

        return list;
    }

    public ArrayList<String> getClasses(){
        ArrayList<String> list = new ArrayList<>();
        for(JQueryObject obj: MAIN_LIST){
            list.addAll(obj.getClasses());
        }

        return list;
    }

    public JQueryObject parent(){
        return eq(0).parent();
    }

    public JQueryList parent(String selector){
        JQueryList list = new JQueryList();
        for(JQueryObject o: MAIN_LIST){
            list.add(o.parent(selector));
        }

        return list;
    }
    public String element(){
        StringBuilder builder = new StringBuilder();
        for(JQueryObject o: MAIN_LIST){
            builder.append(o.element());
            builder.append(" ");
        }

        return builder.toString().trim();
    }
    public String html(){
        StringBuilder builder = new StringBuilder();
        for(JQueryObject o: MAIN_LIST){
            builder.append(o.html());
            builder.append("\n");
        }

        return builder.toString().trim();
    }
    public String text(){
        StringBuilder builder = new StringBuilder();
        for(JQueryObject o: MAIN_LIST){
            builder.append(o.text());
            builder.append("\n");
        }

        return builder.toString().trim();
    }
}
