package org.apache.xerces.xs;

import java.util.Map;

/* JADX INFO: loaded from: classes13.dex */
public interface XSNamedMap extends Map {
    int getLength();

    XSObject item(int i);

    XSObject itemByName(String str, String str2);
}
