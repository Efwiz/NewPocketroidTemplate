package org.apache.xerces.xs;

import java.util.List;

/* JADX INFO: loaded from: classes13.dex */
public interface StringList extends List {
    boolean contains(String str);

    int getLength();

    String item(int i);
}
