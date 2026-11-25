package com.android.tools.r8.internal;

import com.android.tools.r8.keepanno.annotations.FieldAccessFlags;
import com.android.tools.r8.keepanno.annotations.KeepEdge;
import com.android.tools.r8.keepanno.annotations.KeepTarget;
import com.android.tools.r8.keepanno.annotations.MemberAccessFlags;
import com.android.tools.r8.keepanno.annotations.MethodAccessFlags;
import com.android.tools.r8.keepanno.annotations.UsesReflection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2043g2 {

    /* JADX INFO: renamed from: com.android.tools.r8.internal.g2$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a {
        public static final Class<KeepEdge> a = KeepEdge.class;
        public static final String b = C2043g2.a(KeepEdge.class);
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.g2$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class b {
        public static final String a = C2043g2.a(FieldAccessFlags.class);
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.g2$c */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class c {
        public static final String a = C2043g2.a(MemberAccessFlags.class);
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.g2$d */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class d {
        public static final String a = C2043g2.a(MethodAccessFlags.class);
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.g2$e */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class e {
        public static final String a = C2043g2.a(KeepTarget.class);
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.g2$f */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class f {
        public static final String a = C2043g2.a(UsesReflection.class);
    }

    public static String a(Class cls) {
        return a(cls.getTypeName());
    }

    public static String a(String str) {
        return "L" + str.replace('.', '/') + ";";
    }

    public static boolean a(String str, boolean z) {
        if (z) {
            return false;
        }
        return str.equals(a.b) || str.equals(f.a) || str.equals(AbstractC1967f2.a) || str.equals(e.a);
    }
}
