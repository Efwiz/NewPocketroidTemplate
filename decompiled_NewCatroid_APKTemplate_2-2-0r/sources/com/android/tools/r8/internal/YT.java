package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.RetraceClassResult;
import com.android.tools.r8.retrace.RetraceFieldResult;
import com.android.tools.r8.retrace.RetraceFrameResult;
import com.android.tools.r8.retrace.RetraceMethodResult;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.retrace.RetraceThrownExceptionResult;
import com.android.tools.r8.retrace.RetraceTypeResult;
import com.android.tools.r8.retrace.Retracer;
import java.util.OptionalInt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class YT implements Retracer {
    static final /* synthetic */ boolean c = true;
    private final DD a;
    private final DiagnosticsHandler b;

    private YT(DD dd, DiagnosticsHandler diagnosticsHandler) {
        this.a = dd;
        this.b = diagnosticsHandler;
        if (!c && dd == null) {
            throw new AssertionError();
        }
    }

    public final DiagnosticsHandler a() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.Retracer
    public final RetraceClassResult retraceClass(ClassReference classReference) {
        return C2992sT.a(classReference, this.a.a(classReference.getTypeName()), this);
    }

    @Override // com.android.tools.r8.retrace.Retracer
    public final RetraceFieldResult retraceField(FieldReference fieldReference) {
        ClassReference holderClass = fieldReference.getHolderClass();
        return C2992sT.a(holderClass, this.a.a(holderClass.getTypeName()), this).a(fieldReference);
    }

    @Override // com.android.tools.r8.retrace.Retracer
    public final RetraceFrameResult retraceFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, MethodReference methodReference) {
        ClassReference holderClass = methodReference.getHolderClass();
        return C2992sT.a(holderClass, this.a.a(holderClass.getTypeName()), this).lookupFrame(retraceStackTraceContext, optionalInt, methodReference.getMethodName(), methodReference.getFormalTypes(), methodReference.getReturnType());
    }

    @Override // com.android.tools.r8.retrace.Retracer
    public final RetraceMethodResult retraceMethod(MethodReference methodReference) {
        ClassReference holderClass = methodReference.getHolderClass();
        return C2992sT.a(holderClass, this.a.a(holderClass.getTypeName()), this).a(methodReference);
    }

    @Override // com.android.tools.r8.retrace.Retracer
    public final RetraceThrownExceptionResult retraceThrownException(ClassReference classReference) {
        return C2992sT.a(classReference, this.a.a(classReference.getTypeName()), this).lookupThrownException(RetraceStackTraceContext.empty());
    }

    @Override // com.android.tools.r8.retrace.Retracer
    public final RetraceTypeResult retraceType(TypeReference typeReference) {
        return KT.a(typeReference, this);
    }

    public final String a(ClassReference classReference) {
        return this.a.b(classReference.getTypeName());
    }

    public static YT a(DD dd, DiagnosticsHandler diagnosticsHandler) {
        return new YT(dd, diagnosticsHandler);
    }

    @Override // com.android.tools.r8.retrace.Retracer
    public final RetraceFrameResult retraceFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, ClassReference classReference, String str) {
        return C2992sT.a(classReference, this.a.a(classReference.getTypeName()), this).lookupFrame(retraceStackTraceContext, optionalInt, str);
    }
}
