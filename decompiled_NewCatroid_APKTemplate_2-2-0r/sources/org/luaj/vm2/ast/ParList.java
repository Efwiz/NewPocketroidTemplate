package org.luaj.vm2.ast;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class ParList extends SyntaxElement {
    public static final List EMPTY_NAMELIST;
    public static final ParList EMPTY_PARLIST;
    public final boolean isvararg;
    public final List names;

    static {
        ArrayList arrayList = new ArrayList();
        EMPTY_NAMELIST = arrayList;
        EMPTY_PARLIST = new ParList(arrayList, false);
    }

    public ParList(List list, boolean z) {
        this.names = list;
        this.isvararg = z;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
