package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class A0 implements InterfaceC3046tE, Cloneable {
    protected static void a(Collection collection, List list) {
        if (collection instanceof YA) {
            Iterator it = ((YA) collection).c0().iterator();
            while (it.hasNext()) {
                it.next().getClass();
            }
            list.addAll(collection);
            return;
        }
        if (collection instanceof Collection) {
            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                it2.next().getClass();
            }
            list.addAll(collection);
            return;
        }
        for (Object obj : collection) {
            obj.getClass();
            list.add(obj);
        }
    }

    public abstract A0 a(C2396kb c2396kb, C2867qh c2867qh);

    public abstract B0 c();
}
