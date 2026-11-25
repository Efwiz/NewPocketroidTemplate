package org.apache.xerces.dom;

import java.util.ArrayList;
import java.util.Vector;
import org.w3c.dom.DOMStringList;

/* JADX INFO: loaded from: classes13.dex */
public class DOMStringListImpl implements DOMStringList {
    private final ArrayList fStrings;

    public DOMStringListImpl() {
        this.fStrings = new ArrayList();
    }

    public DOMStringListImpl(ArrayList arrayList) {
        this.fStrings = arrayList;
    }

    public DOMStringListImpl(Vector vector) {
        this.fStrings = new ArrayList(vector);
    }

    public void add(String str) {
        this.fStrings.add(str);
    }

    @Override // org.w3c.dom.DOMStringList
    public boolean contains(String str) {
        return this.fStrings.contains(str);
    }

    @Override // org.w3c.dom.DOMStringList
    public int getLength() {
        return this.fStrings.size();
    }

    @Override // org.w3c.dom.DOMStringList
    public String item(int i) {
        int length = getLength();
        if (i < 0 || i >= length) {
            return null;
        }
        return (String) this.fStrings.get(i);
    }
}
