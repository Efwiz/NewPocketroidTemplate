package org.jetbrains.kotlin.js.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;

/* JADX INFO: compiled from: InlinerCycleReporter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/JsCallInfo;", "", "call", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "containingFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)V", "getCall", "()Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "getContainingFunction", "()Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JsCallInfo {
    private final JsInvocation call;
    private final JsFunction containingFunction;

    public JsCallInfo(JsInvocation call, JsFunction containingFunction) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(containingFunction, "containingFunction");
        this.call = call;
        this.containingFunction = containingFunction;
    }

    public final JsInvocation getCall() {
        return this.call;
    }

    public final JsFunction getContainingFunction() {
        return this.containingFunction;
    }
}
