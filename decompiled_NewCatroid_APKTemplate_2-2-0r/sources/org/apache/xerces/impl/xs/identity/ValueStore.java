package org.apache.xerces.impl.xs.identity;

import org.apache.xerces.xs.ShortList;

/* JADX INFO: loaded from: classes13.dex */
public interface ValueStore {
    void addValue(Field field, boolean z, Object obj, short s, ShortList shortList);

    void reportError(String str, Object[] objArr);
}
