package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import okhttp3.HttpUrl;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2545mZ extends YU {
    private static final Map l;
    private final boolean a;
    private final StringBuilder b;
    private StringBuilder c;
    private StringBuilder d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    private int i;
    private int j;
    private String k;

    static {
        HashMap map = new HashMap();
        map.put('Z', "boolean");
        map.put(Character.valueOf(Util.C_BYTE), "byte");
        map.put(Character.valueOf(Util.C_CHAR), PsiKeyword.CHAR);
        map.put(Character.valueOf(Util.C_SHORT), "short");
        map.put('I', "int");
        map.put(Character.valueOf(Util.C_LONG), "long");
        map.put(Character.valueOf(Util.C_FLOAT), "float");
        map.put(Character.valueOf(Util.C_DOUBLE), "double");
        map.put(Character.valueOf(Util.C_VOID), PsiKeyword.VOID);
        l = Collections.unmodifiableMap(map);
    }

    public C2545mZ() {
        this.k = "";
        this.a = false;
        this.b = new StringBuilder();
    }

    private void e() {
        this.j *= 2;
    }

    public final void a(char c) {
        String str = (String) l.get(Character.valueOf(c));
        if (str == null) {
            throw new IllegalArgumentException();
        }
        this.b.append(str);
        a();
    }

    public final void b(String str) {
        this.b.append(this.e ? ", " : "<").append(str);
        this.e = true;
        this.f = false;
    }

    public final void c(String str) {
        if (this.i % 2 != 0) {
            this.b.append('>');
        }
        this.i /= 2;
        this.b.append('.');
        this.b.append(this.k).append(str.replace('/', '.'));
        this.k = "";
        this.i *= 2;
    }

    public final void d(String str) {
        this.b.append(this.k).append(str);
        this.k = "";
        a();
    }

    public final C2545mZ f() {
        e();
        this.j |= 1;
        return this;
    }

    public final C2545mZ g() {
        this.k = " extends ";
        e();
        return this;
    }

    public final void h() {
        if (this.i % 2 != 0) {
            this.b.append('>');
        }
        this.i /= 2;
        a();
    }

    public final C2545mZ i() {
        StringBuilder sb = this.d;
        if (sb == null) {
            this.d = new StringBuilder();
        } else {
            sb.append(", ");
        }
        return new C2545mZ(this.d);
    }

    public final C2545mZ j() {
        if (this.h) {
            this.k = ", ";
        } else {
            this.k = this.a ? " extends " : " implements ";
            this.h = true;
        }
        e();
        return this;
    }

    public final C2545mZ k() {
        this.k = this.f ? ", " : " extends ";
        this.f = true;
        e();
        return this;
    }

    public final C2545mZ l() {
        if (this.e) {
            this.b.append('>');
            this.e = false;
        }
        if (this.g) {
            this.b.append(", ");
        } else {
            this.b.append('(');
            this.g = true;
        }
        e();
        return this;
    }

    public final C2545mZ m() {
        if (this.e) {
            this.b.append('>');
            this.e = false;
        }
        if (this.g) {
            this.g = false;
        } else {
            this.b.append('(');
        }
        this.b.append(')');
        StringBuilder sb = new StringBuilder();
        this.c = sb;
        return new C2545mZ(sb);
    }

    public final C2545mZ n() {
        if (this.e) {
            this.b.append('>');
            this.e = false;
        }
        this.k = " extends ";
        e();
        return this;
    }

    public final void o() {
        int i = this.i;
        if (i % 2 == 0) {
            this.i = i + 1;
            this.b.append('<');
        } else {
            this.b.append(", ");
        }
        this.b.append(MangleConstant.Q_MARK);
    }

    public final C2545mZ b(char c) {
        int i = this.i;
        if (i % 2 == 0) {
            this.i = i + 1;
            this.b.append('<');
        } else {
            this.b.append(", ");
        }
        if (c == '+') {
            this.b.append(PsiWildcardType.EXTENDS_PREFIX);
        } else if (c == '-') {
            this.b.append(PsiWildcardType.SUPER_PREFIX);
        }
        e();
        return this;
    }

    public final String d() {
        StringBuilder sb = this.c;
        if (sb == null) {
            return null;
        }
        return sb.toString();
    }

    public final void a(String str) {
        if (!TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME.equals(str) || this.i % 2 != 0 || this.g) {
            this.b.append(this.k).append(str.replace('/', '.'));
        }
        this.k = "";
        this.i *= 2;
    }

    public final String c() {
        StringBuilder sb = this.d;
        if (sb == null) {
            return null;
        }
        return sb.toString();
    }

    private C2545mZ(StringBuilder sb) {
        this.k = "";
        this.a = false;
        this.b = sb;
    }

    public final String b() {
        return this.b.toString();
    }

    private void a() {
        int i = this.j;
        if (i % 2 == 0) {
            this.j = i / 2;
            return;
        }
        while (true) {
            int i2 = this.j;
            if (i2 % 2 == 0) {
                return;
            }
            this.j = i2 / 2;
            this.b.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
    }
}
