package com.android.tools.r8.shaking;

import com.android.dx.cf.attrib.AttSourceDebugExtension;
import com.android.tools.r8.internal.AbstractC1639am;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.f3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3718f3 {
    public static final List t = AbstractC1639am.c("*");
    public boolean a = false;
    public boolean b = false;
    public boolean c = false;
    public boolean d = false;
    public boolean e = false;
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    public boolean i = false;
    public boolean j = false;
    public boolean k = false;
    public boolean l = false;
    public boolean m = false;
    public boolean n = false;
    public boolean o = false;
    public boolean p = false;
    public boolean q = false;
    public boolean r = false;
    public boolean s = false;

    private C3718f3() {
    }

    private static boolean a(String str, List list, boolean z) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (z) {
                return true;
            }
            if (str2.length() <= 0 || str2.charAt(0) != '!') {
                z = a(0, 0, str2, str);
            } else if (a(1, 0, str2, str)) {
                break;
            }
        }
        return z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3718f3)) {
            return false;
        }
        C3718f3 c3718f3 = (C3718f3) obj;
        return this.a == c3718f3.a && this.b == c3718f3.b && this.c == c3718f3.c && this.d == c3718f3.d && this.e == c3718f3.e && this.f == c3718f3.f && this.j == c3718f3.j && this.k == c3718f3.k && this.l == c3718f3.l && this.m == c3718f3.m && this.n == c3718f3.n && this.o == c3718f3.o && this.p == c3718f3.p && this.q == c3718f3.q && this.r == c3718f3.r && this.s == c3718f3.s;
    }

    public final int hashCode() {
        return (this.a ? 1 : 0) + (this.b ? 2 : 0) + (this.c ? 4 : 0) + (this.d ? 8 : 0) + (this.e ? 16 : 0) + (this.f ? 32 : 0) + (this.k ? 64 : 0) + (this.l ? 128 : 0) + (this.m ? 256 : 0) + (this.n ? 512 : 0) + (this.o ? 1024 : 0) + (this.p ? 2048 : 0) + (this.q ? 4096 : 0) + (this.r ? 8192 : 0) + (this.s ? 16384 : 0) + (this.j ? 32768 : 0);
    }

    public String toString() {
        return a(new StringBuilder()).toString();
    }

    private static boolean a(int i, int i2, String str, String str2) {
        while (i < str.length()) {
            int i3 = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt == '*') {
                while (i2 < str2.length()) {
                    int i4 = i2 + 1;
                    if (a(i3, i2, str, str2)) {
                        return true;
                    }
                    i2 = i4;
                }
                return i3 >= str.length();
            }
            if (i2 >= str2.length() || str2.charAt(i2) != cCharAt) {
                return false;
            }
            i2++;
            i = i3;
        }
        return i2 == str2.length();
    }

    public static C3718f3 a(List<String> list) {
        C3718f3 c3718f3 = new C3718f3();
        c3718f3.a = a("SourceFile", list, c3718f3.a);
        c3718f3.b = a("SourceDir", list, c3718f3.b);
        c3718f3.c = a("InnerClasses", list, c3718f3.c);
        c3718f3.d = a("EnclosingMethod", list, c3718f3.d);
        c3718f3.g = a("LineNumberTable", list, c3718f3.g);
        c3718f3.h = a("LocalVariableTable", list, c3718f3.h);
        c3718f3.i = a("LocalVariableTypeTable", list, c3718f3.i);
        c3718f3.f = a("Exceptions", list, c3718f3.f);
        c3718f3.j = a("MethodParameters", list, c3718f3.j);
        c3718f3.e = a("Signature", list, c3718f3.e);
        c3718f3.k = a(AttSourceDebugExtension.ATTRIBUTE_NAME, list, c3718f3.k);
        c3718f3.l = a("RuntimeVisibleAnnotations", list, c3718f3.l);
        c3718f3.m = a("RuntimeInvisibleAnnotations", list, c3718f3.m);
        c3718f3.n = a("RuntimeVisibleParameterAnnotations", list, c3718f3.n);
        c3718f3.o = a("RuntimeInvisibleParameterAnnotations", list, c3718f3.o);
        c3718f3.p = a("RuntimeVisibleTypeAnnotations", list, c3718f3.p);
        c3718f3.q = a("RuntimeInvisibleTypeAnnotations", list, c3718f3.q);
        c3718f3.r = a("AnnotationDefault", list, c3718f3.r);
        c3718f3.s = a("StackMapTable", list, c3718f3.s);
        return c3718f3;
    }

    public final StringBuilder a(StringBuilder sb) {
        ArrayList arrayList = new ArrayList();
        if (this.a) {
            arrayList.add("SourceFile");
        }
        if (this.b) {
            arrayList.add("SourceDir");
        }
        if (this.c) {
            arrayList.add("InnerClasses");
        }
        if (this.d) {
            arrayList.add("EnclosingMethod");
        }
        if (this.e) {
            arrayList.add("Signature");
        }
        if (this.f) {
            arrayList.add("Exceptions");
        }
        if (this.j) {
            arrayList.add("MethodParameters");
        }
        if (this.k) {
            arrayList.add(AttSourceDebugExtension.ATTRIBUTE_NAME);
        }
        if (this.l) {
            arrayList.add("RuntimeVisibleAnnotations");
        }
        if (this.m) {
            arrayList.add("RuntimeInvisibleAnnotations");
        }
        if (this.n) {
            arrayList.add("RuntimeVisibleParameterAnnotations");
        }
        if (this.o) {
            arrayList.add("RuntimeInvisibleParameterAnnotations");
        }
        if (this.p) {
            arrayList.add("RuntimeVisibleTypeAnnotations");
        }
        if (this.q) {
            arrayList.add("RuntimeInvisibleTypeAnnotations");
        }
        if (this.r) {
            arrayList.add("AnnotationDefault");
        }
        if (this.s) {
            arrayList.add("StackMapTable");
        }
        if (arrayList.size() > 0) {
            sb.append("-keepattributes ");
            sb.append(String.join(",", arrayList));
        }
        return sb;
    }
}
