package com.android.tools.r8.retrace;

import com.android.tools.r8.references.TypeReference;
import java.util.List;
import java.util.OptionalInt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface RetraceClassResult extends RetraceResult<RetraceClassElement> {
    RetraceFieldResult lookupField(String str);

    RetraceFieldResult lookupField(String str, TypeReference typeReference);

    RetraceFrameResult lookupFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, String str);

    RetraceFrameResult lookupFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, String str, List<TypeReference> list, TypeReference typeReference);

    RetraceMethodResult lookupMethod(String str);

    RetraceMethodResult lookupMethod(String str, List<TypeReference> list, TypeReference typeReference);

    RetraceThrownExceptionResult lookupThrownException(RetraceStackTraceContext retraceStackTraceContext);
}
