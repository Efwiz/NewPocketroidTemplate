package org.jetbrains.kotlin.js.inline.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: collectUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "wrapperBody", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;)V", "getFunction", "()Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "getWrapperBody", "()Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FunctionWithWrapper {
    private final JsFunction function;
    private final JsBlock wrapperBody;

    public static /* synthetic */ FunctionWithWrapper copy$default(FunctionWithWrapper functionWithWrapper, JsFunction jsFunction, JsBlock jsBlock, int i, Object obj) {
        if ((i & 1) != 0) {
            jsFunction = functionWithWrapper.function;
        }
        if ((i & 2) != 0) {
            jsBlock = functionWithWrapper.wrapperBody;
        }
        return functionWithWrapper.copy(jsFunction, jsBlock);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final JsFunction getFunction() {
        return this.function;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final JsBlock getWrapperBody() {
        return this.wrapperBody;
    }

    public final FunctionWithWrapper copy(JsFunction function, JsBlock wrapperBody) {
        Intrinsics.checkNotNullParameter(function, "function");
        return new FunctionWithWrapper(function, wrapperBody);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FunctionWithWrapper)) {
            return false;
        }
        FunctionWithWrapper functionWithWrapper = (FunctionWithWrapper) other;
        return Intrinsics.areEqual(this.function, functionWithWrapper.function) && Intrinsics.areEqual(this.wrapperBody, functionWithWrapper.wrapperBody);
    }

    public int hashCode() {
        int iHashCode = this.function.hashCode() * 31;
        JsBlock jsBlock = this.wrapperBody;
        return iHashCode + (jsBlock == null ? 0 : jsBlock.hashCode());
    }

    public String toString() {
        return "FunctionWithWrapper(function=" + this.function + ", wrapperBody=" + this.wrapperBody + ')';
    }

    public FunctionWithWrapper(JsFunction function, JsBlock jsBlock) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
        this.wrapperBody = jsBlock;
    }

    public final JsFunction getFunction() {
        return this.function;
    }

    public final JsBlock getWrapperBody() {
        return this.wrapperBody;
    }
}
