package com.android.tools.r8.utils;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import org.apache.commons.compress.harmony.pack200.PackingOptions;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class y3 {
    private static final y3 d = new r3();
    static final /* synthetic */ boolean e = true;
    private final t3 a;
    private final ArrayDeque b;
    private final boolean c;

    public y3(String str) {
        this(str, false);
    }

    public static y3 a(A1 a1, String str) {
        y3 y3Var = (a1.m || A1.b()) ? new y3(str, a1.n) : b();
        return a1.c != null ? new x3(a1, y3Var) : y3Var;
    }

    public static y3 b() {
        return d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(long j) {
        return (j / PackingOptions.SEGMENT_LIMIT) + LanguageCodeUtil.MS;
    }

    public void d() {
        boolean z = e;
        if (!z && this.b.size() != 1) {
            throw new AssertionError();
        }
        t3 t3Var = (t3) this.b.peek();
        if (!z && t3Var != this.a) {
            throw new AssertionError();
        }
        t3Var.b();
        System.out.println("Recorded timings:");
        t3Var.a(0, t3Var);
    }

    private y3(String str, boolean z) {
        this.c = z;
        ArrayDeque arrayDeque = new ArrayDeque();
        this.b = arrayDeque;
        t3 t3Var = new t3(str, z);
        this.a = t3Var;
        arrayDeque.push(t3Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(long j, long j2) {
        return a(j, j2) + "%";
    }

    public void c() {
        ((t3) this.b.peek()).b();
        this.b.pop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(long j) {
        return a(j / 1024) + JvmAnnotationNames.KIND_FIELD_NAME;
    }

    public static y3 a(boolean z) {
        return new y3("R8 retrace", z);
    }

    public w3 a(int i, String str) {
        return new w3(str, i, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long a(long j, long j2) {
        return (j * 100) / j2;
    }

    private static String a(long j) {
        String string = new StringBuilder().append(Math.abs(j)).toString();
        if (string.length() < 4) {
            return new StringBuilder().append(j).toString();
        }
        StringBuilder sb = new StringBuilder();
        if (j < 0) {
            sb.append('-');
        }
        int length = string.length() % 3;
        sb.append((CharSequence) string, 0, length);
        while (length < string.length()) {
            if (length > 0) {
                sb.append('.');
            }
            int i = length + 3;
            sb.append((CharSequence) string, length, i);
            length = i;
        }
        return sb.toString();
    }

    public void a(String str) {
        t3 t3Var;
        t3 t3Var2 = (t3) this.b.peek();
        if (t3Var2.c.containsKey(str)) {
            t3Var = (t3) t3Var2.c.get(str);
            if (!t3.h && t3Var.e != -1) {
                throw new AssertionError();
            }
            if (t3Var.b) {
                t3Var.f = a();
            }
            t3Var.e = System.nanoTime();
        } else {
            t3 t3Var3 = new t3(str, this.c);
            t3Var2.c.put(str, t3Var3);
            t3Var = t3Var3;
        }
        this.b.push(t3Var);
    }

    public void a(String str, InterfaceC3885d3 interfaceC3885d3) {
        a(str);
        try {
            interfaceC3885d3.a();
        } finally {
            c();
        }
    }

    public Object a(String str, InterfaceC3949p3 interfaceC3949p3) {
        a(str);
        try {
            return interfaceC3949p3.get();
        } finally {
            c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LinkedHashMap a() {
        System.gc();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("Memory", new s3(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
        return linkedHashMap;
    }
}
