package org.apache.xerces.dom;

import java.util.ArrayList;
import java.util.Vector;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;

/* JADX INFO: loaded from: classes13.dex */
public class DOMImplementationListImpl implements DOMImplementationList {
    private final ArrayList fImplementations;

    public DOMImplementationListImpl() {
        this.fImplementations = new ArrayList();
    }

    public DOMImplementationListImpl(ArrayList arrayList) {
        this.fImplementations = arrayList;
    }

    public DOMImplementationListImpl(Vector vector) {
        this.fImplementations = new ArrayList(vector);
    }

    @Override // org.w3c.dom.DOMImplementationList
    public int getLength() {
        return this.fImplementations.size();
    }

    @Override // org.w3c.dom.DOMImplementationList
    public DOMImplementation item(int i) {
        int length = getLength();
        if (i < 0 || i >= length) {
            return null;
        }
        return (DOMImplementation) this.fImplementations.get(i);
    }
}
