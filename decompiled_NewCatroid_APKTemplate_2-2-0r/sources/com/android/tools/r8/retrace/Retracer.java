package com.android.tools.r8.retrace;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.TypeReference;
import java.util.OptionalInt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface Retracer {
    static Retracer createDefault(ProguardMapProducer proguardMapProducer, DiagnosticsHandler diagnosticsHandler) {
        try {
            return ProguardMappingSupplier.builder().setProguardMapProducer(proguardMapProducer).build().createRetracer(diagnosticsHandler);
        } catch (Exception e) {
            throw new InvalidMappingFileException(e);
        }
    }

    RetraceClassResult retraceClass(ClassReference classReference);

    RetraceFieldResult retraceField(FieldReference fieldReference);

    RetraceFrameResult retraceFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, ClassReference classReference, String str);

    RetraceFrameResult retraceFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, MethodReference methodReference);

    RetraceMethodResult retraceMethod(MethodReference methodReference);

    RetraceThrownExceptionResult retraceThrownException(ClassReference classReference);

    RetraceTypeResult retraceType(TypeReference typeReference);
}
