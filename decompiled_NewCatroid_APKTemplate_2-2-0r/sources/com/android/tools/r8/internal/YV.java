package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.retrace.RetraceStackTraceElementProxy;
import com.android.tools.r8.retrace.StackTraceElementProxy;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class YV extends StackTraceElementProxy {
    static final /* synthetic */ boolean j = true;
    private final String a;
    private final List b;
    private final VV c;
    private final XV d;
    private final XV e;
    private final XV f;
    private final XV g;
    private final XV h;
    private final XV i;

    private YV(String str, ArrayList arrayList, VV vv, XV xv, XV xv2, XV xv3, XV xv4, XV xv5, XV xv6) {
        this.a = str;
        this.b = arrayList;
        this.c = vv;
        this.d = xv;
        this.e = xv2;
        this.f = xv3;
        this.g = xv4;
        this.h = xv5;
        this.i = xv6;
    }

    static WV a(String str) {
        return new WV(str);
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final ClassReference getClassReference() {
        if (!this.c.a()) {
            return null;
        }
        VV vv = this.c;
        String strSubstring = this.a.substring(vv.a, vv.b);
        return vv.d == UV.a ? Reference.classFromBinaryName(strSubstring) : Reference.classFromTypeName(strSubstring);
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final String getFieldName() {
        if (this.g.a()) {
            return a(this.g);
        }
        return null;
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final String getFieldOrReturnType() {
        if (this.h.a()) {
            return a(this.h);
        }
        return null;
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final int getLineNumber() {
        if (!this.f.a()) {
            return -1;
        }
        try {
            String strA = a(this.f);
            if (strA.startsWith(":")) {
                strA = strA.substring(1);
            }
            if (strA.isEmpty()) {
                return -1;
            }
            return Integer.parseInt(strA);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final String getMethodArguments() {
        if (this.i.a()) {
            return a(this.i);
        }
        return null;
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final String getMethodName() {
        if (this.d.a()) {
            return a(this.d);
        }
        return null;
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final String getSourceFile() {
        if (this.e.a()) {
            return a(this.e);
        }
        return null;
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final boolean hasClassName() {
        return this.c.a();
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final boolean hasFieldName() {
        return this.g.a();
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final boolean hasFieldOrReturnType() {
        return this.h.a();
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final boolean hasLineNumber() {
        return this.f.a();
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final boolean hasMethodArguments() {
        return this.i.a();
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final boolean hasMethodName() {
        return this.d.a();
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final boolean hasSourceFile() {
        return this.e.a();
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxy
    public final Object toRetracedItem(RetraceStackTraceElementProxy retraceStackTraceElementProxy, boolean z) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (XV xv : this.b) {
            sb.append((CharSequence) this.a, i, xv.a);
            sb.append((String) xv.c.a(retraceStackTraceElementProxy, this, Boolean.valueOf(z)));
            i = xv.b;
        }
        String str = this.a;
        sb.append((CharSequence) str, i, str.length());
        return sb.toString();
    }

    public final String a() {
        return a(this.f);
    }

    private String a(XV xv) {
        if (j || xv != VV.e) {
            return this.a.substring(xv.a, xv.b);
        }
        throw new AssertionError();
    }
}
