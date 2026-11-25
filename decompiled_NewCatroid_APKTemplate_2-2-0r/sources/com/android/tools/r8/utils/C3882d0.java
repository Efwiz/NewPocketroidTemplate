package com.android.tools.r8.utils;

import com.android.tools.r8.references.ClassReference;
import java.util.Comparator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.utils.d0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3882d0 {
    private static final Comparator a = Comparator.comparing(new Function() { // from class: com.android.tools.r8.utils.d0$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((ClassReference) obj).getDescriptor();
        }
    });
    public static final /* synthetic */ int b = 0;

    public static Comparator<ClassReference> a() {
        return a;
    }

    public static String a(ClassReference classReference) {
        return classReference.getDescriptor();
    }
}
