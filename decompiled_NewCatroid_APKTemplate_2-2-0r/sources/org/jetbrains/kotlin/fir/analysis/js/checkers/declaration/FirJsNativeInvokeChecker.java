package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsNativeAnnotationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsNativeInvokeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsAbstractNativeAnnotationChecker;", "()V", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsNativeInvokeChecker extends FirJsAbstractNativeAnnotationChecker {
    public static final FirJsNativeInvokeChecker INSTANCE = new FirJsNativeInvokeChecker();

    private FirJsNativeInvokeChecker() {
        super(JsStandardClassIds.Annotations.JsNativeInvoke);
    }
}
