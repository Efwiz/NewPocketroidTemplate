package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SourceMapsInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"sourceMapsInfo", "Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "getSourceMapsInfo", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SourceMapsInfoKt {
    public static final SourceMapsInfo getSourceMapsInfo(JsCommonBackendContext jsCommonBackendContext) {
        Intrinsics.checkNotNullParameter(jsCommonBackendContext, "<this>");
        return SourceMapsInfo.INSTANCE.from(jsCommonBackendContext.getConfiguration());
    }
}
