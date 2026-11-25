package com.android.tools.r8.internal;

import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.RetraceStackTraceElementProxy;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedFieldReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.retrace.RetracedTypeReference;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class WV {
    static final /* synthetic */ boolean k = true;
    private final String a;
    private final ArrayList b;
    private VV c;
    private XV d;
    private XV e;
    private XV f;
    private XV g;
    private XV h;
    private XV i;
    private int j;

    private WV(String str) {
        this.b = new ArrayList();
        VV vv = VV.e;
        this.c = vv;
        this.d = vv;
        this.e = vv;
        this.f = vv;
        this.g = vv;
        this.h = vv;
        this.i = vv;
        this.j = -1;
        this.a = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(UV uv, RetraceStackTraceElementProxy retraceStackTraceElementProxy, YV yv, Boolean bool) {
        if (!k && !retraceStackTraceElementProxy.hasRetracedClass()) {
            throw new AssertionError();
        }
        RetracedClassReference retracedClass = retraceStackTraceElementProxy.getRetracedClass();
        return uv == UV.a ? retracedClass.getBinaryName() : retracedClass.getTypeName();
    }

    public final void b(int i, int i2) {
        XV xv = new XV(i, i2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.WV$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return WV.b((RetraceStackTraceElementProxy) obj, (YV) obj2, (Boolean) obj3);
            }
        });
        this.h = xv;
        this.b.add(xv);
    }

    public final void c(int i, int i2) {
        XV xv = new XV(i, i2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.WV$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return WV.c((RetraceStackTraceElementProxy) obj, (YV) obj2, (Boolean) obj3);
            }
        });
        this.i = xv;
        this.b.add(xv);
    }

    public final void d(int i, int i2) {
        XV xv = new XV(i, i2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.WV$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return WV.d((RetraceStackTraceElementProxy) obj, (YV) obj2, (Boolean) obj3);
            }
        });
        this.d = xv;
        this.b.add(xv);
    }

    public final void e(int i, int i2) {
        XV xv = new XV(i, i2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.WV$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return WV.e((RetraceStackTraceElementProxy) obj, (YV) obj2, (Boolean) obj3);
            }
        });
        this.e = xv;
        this.b.add(xv);
    }

    public final void a(int i, int i2, final boolean z) {
        XV xv = new XV(i, i2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.WV$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return WV.a(z, (RetraceStackTraceElementProxy) obj, (YV) obj2, (Boolean) obj3);
            }
        });
        this.f = xv;
        this.b.add(xv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String e(RetraceStackTraceElementProxy retraceStackTraceElementProxy, YV yv, Boolean bool) {
        return retraceStackTraceElementProxy.hasSourceFile() ? retraceStackTraceElementProxy.getSourceFile() : yv.getSourceFile();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String c(RetraceStackTraceElementProxy retraceStackTraceElementProxy, YV yv, Boolean bool) {
        if (!retraceStackTraceElementProxy.hasRetracedMethodArguments()) {
            return yv.getMethodArguments();
        }
        return com.android.tools.r8.utils.Y2.a(",", retraceStackTraceElementProxy.getRetracedMethodArguments(), new Function() { // from class: com.android.tools.r8.internal.WV$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((RetracedTypeReference) obj).getTypeName();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(RetraceStackTraceElementProxy retraceStackTraceElementProxy, YV yv, Boolean bool) {
        if (!retraceStackTraceElementProxy.hasRetracedMethod()) {
            return yv.getMethodName();
        }
        RetracedMethodReference retracedMethod = retraceStackTraceElementProxy.getRetracedMethod();
        boolean zBooleanValue = bool.booleanValue();
        boolean z = NT.b;
        StringBuilder sb = new StringBuilder();
        if (zBooleanValue && !retracedMethod.isUnknown()) {
            if (!NT.b && !retracedMethod.isKnown()) {
                throw new AssertionError();
            }
            RetracedMethodReference.KnownRetracedMethodReference knownRetracedMethodReferenceAsKnown = retracedMethod.asKnown();
            sb.append(knownRetracedMethodReferenceAsKnown.isVoid() ? PsiKeyword.VOID : knownRetracedMethodReferenceAsKnown.getReturnType().getTypeName());
            sb.append(" ");
            sb.append(retracedMethod.getMethodName());
            sb.append("(");
            boolean z2 = false;
            for (TypeReference typeReference : knownRetracedMethodReferenceAsKnown.getFormalTypes()) {
                if (z2) {
                    sb.append(",");
                }
                sb.append(typeReference.getTypeName());
                z2 = true;
            }
            sb.append(Constant.AFTER_QUTO);
            return sb.toString();
        }
        return sb.append(retracedMethod.getMethodName()).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(RetraceStackTraceElementProxy retraceStackTraceElementProxy, YV yv, Boolean bool) {
        if (!retraceStackTraceElementProxy.hasRetracedFieldOrReturnType()) {
            return yv.getFieldOrReturnType();
        }
        if (retraceStackTraceElementProxy.getRetracedFieldOrReturnType().isVoid()) {
            return PsiKeyword.VOID;
        }
        return retraceStackTraceElementProxy.getRetracedFieldOrReturnType().getTypeName();
    }

    public final String b() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(boolean z, RetraceStackTraceElementProxy retraceStackTraceElementProxy, YV yv, Boolean bool) {
        if (retraceStackTraceElementProxy.hasLineNumber() && ((yv.hasLineNumber() && yv.getLineNumber() > -1) || !retraceStackTraceElementProxy.isAmbiguous() || bool.booleanValue())) {
            if (retraceStackTraceElementProxy.getLineNumber() <= 0) {
                return "";
            }
            return (z ? ":" : "") + retraceStackTraceElementProxy.getLineNumber();
        }
        return yv.a();
    }

    public final void a(int i, int i2) {
        XV xv = new XV(i, i2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.WV$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return WV.a((RetraceStackTraceElementProxy) obj, (YV) obj2, (Boolean) obj3);
            }
        });
        this.g = xv;
        this.b.add(xv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(RetraceStackTraceElementProxy retraceStackTraceElementProxy, YV yv, Boolean bool) {
        if (!retraceStackTraceElementProxy.hasRetracedField()) {
            return yv.getFieldName();
        }
        RetracedFieldReference retracedField = retraceStackTraceElementProxy.getRetracedField();
        if (bool.booleanValue() && !retracedField.isUnknown()) {
            return retracedField.asKnown().getFieldType().getTypeName() + " " + retracedField.getFieldName();
        }
        return retracedField.getFieldName();
    }

    public final YV a() {
        return new YV(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i);
    }

    public final void a(int i, int i2, final UV uv) {
        if (this.j < i) {
            this.j = i;
            VV vv = new VV(i, i2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.WV$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.G3
                public final Object a(Object obj, Object obj2, Object obj3) {
                    return WV.a(uv, (RetraceStackTraceElementProxy) obj, (YV) obj2, (Boolean) obj3);
                }
            }, uv);
            this.c = vv;
            this.b.add(vv);
            return;
        }
        throw new RuntimeException("Parsing has to be incremental in the order of characters.");
    }
}
