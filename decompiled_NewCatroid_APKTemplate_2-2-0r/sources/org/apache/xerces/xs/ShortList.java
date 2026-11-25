package org.apache.xerces.xs;

import java.util.List;

/* JADX INFO: loaded from: classes13.dex */
public interface ShortList extends List {
    boolean contains(short s);

    int getLength();

    short item(int i) throws XSException;
}
