package com.android.tools.r8.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.utils.m3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3934m3 {
    public final Object a(InterfaceC3949p3 interfaceC3949p3) {
        return a() ? b() : interfaceC3949p3.get();
    }

    public abstract boolean a();

    public abstract Object b();

    public final ArrayList a(int i) {
        ArrayList arrayList = new ArrayList(i);
        while (i > 0) {
            if (a()) {
                arrayList.add(b());
                i--;
            } else {
                throw new NoSuchElementException();
            }
        }
        return arrayList;
    }

    public static AbstractC3934m3 a(Iterator it) {
        return new C3929l3(it);
    }
}
