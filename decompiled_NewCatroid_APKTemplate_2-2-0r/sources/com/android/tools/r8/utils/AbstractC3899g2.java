package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0727q0;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.utils.g2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3899g2 {
    public static boolean a(AbstractC0727q0 abstractC0727q0, Predicate predicate, boolean z) {
        return abstractC0727q0 != null ? predicate.test(abstractC0727q0) : z;
    }

    public static Object a(Function function, Object obj, Object obj2) {
        return obj != null ? function.apply(obj) : obj2;
    }
}
