package com.httdocs.jquery;

import java.util.ArrayList;

class JQueryList extends ArrayList<JQueryObject> {
    public JQueryObject eq(int index){
        return super.get(index);
    }

    /*
     * @deprecated Use eq method instead
     */
    @Deprecated
    public JQueryObject get(int index) {
        throw new UnsupportedOperationException();
    }
}
