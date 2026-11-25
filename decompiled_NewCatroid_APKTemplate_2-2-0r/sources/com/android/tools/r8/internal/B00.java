package com.android.tools.r8.internal;

import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B00 {
    public static Set a(LinkedHashSet linkedHashSet) {
        if (!(linkedHashSet instanceof InterfaceC2032fv)) {
            return linkedHashSet;
        }
        throw ((ClassCastException) AbstractC0936Ds.a(B00.class.getName(), (RuntimeException) new ClassCastException(linkedHashSet.getClass().getName().concat(" cannot be cast to kotlin.collections.MutableSet"))));
    }
}
