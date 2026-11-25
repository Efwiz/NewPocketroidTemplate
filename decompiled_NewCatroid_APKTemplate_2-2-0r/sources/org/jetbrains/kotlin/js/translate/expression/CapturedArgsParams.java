package org.jetbrains.kotlin.js.translate.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LiteralFunctionTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/CapturedArgsParams;", "", "arguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "parameters", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "(Ljava/util/List;Ljava/util/List;)V", "getArguments", "()Ljava/util/List;", "getParameters", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class CapturedArgsParams {
    private final List<JsExpression> arguments;
    private final List<JsParameter> parameters;

    /* JADX WARN: Multi-variable type inference failed */
    public CapturedArgsParams() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CapturedArgsParams copy$default(CapturedArgsParams capturedArgsParams, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = capturedArgsParams.arguments;
        }
        if ((i & 2) != 0) {
            list2 = capturedArgsParams.parameters;
        }
        return capturedArgsParams.copy(list, list2);
    }

    public final List<JsExpression> component1() {
        return this.arguments;
    }

    public final List<JsParameter> component2() {
        return this.parameters;
    }

    public final CapturedArgsParams copy(List<? extends JsExpression> arguments, List<JsParameter> parameters) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        return new CapturedArgsParams(arguments, parameters);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CapturedArgsParams)) {
            return false;
        }
        CapturedArgsParams capturedArgsParams = (CapturedArgsParams) other;
        return Intrinsics.areEqual(this.arguments, capturedArgsParams.arguments) && Intrinsics.areEqual(this.parameters, capturedArgsParams.parameters);
    }

    public int hashCode() {
        return (this.arguments.hashCode() * 31) + this.parameters.hashCode();
    }

    public String toString() {
        return "CapturedArgsParams(arguments=" + this.arguments + ", parameters=" + this.parameters + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CapturedArgsParams(List<? extends JsExpression> arguments, List<JsParameter> parameters) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.arguments = arguments;
        this.parameters = parameters;
    }

    public /* synthetic */ CapturedArgsParams(List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, (i & 2) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public final List<JsExpression> getArguments() {
        return this.arguments;
    }

    public final List<JsParameter> getParameters() {
        return this.parameters;
    }
}
