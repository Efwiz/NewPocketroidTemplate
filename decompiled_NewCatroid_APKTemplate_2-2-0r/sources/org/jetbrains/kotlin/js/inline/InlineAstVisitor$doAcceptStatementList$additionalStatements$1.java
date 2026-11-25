package org.jetbrains.kotlin.js.inline;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.js.backend.ast.JsNode;

/* JADX INFO: compiled from: InlineAstVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class InlineAstVisitor$doAcceptStatementList$additionalStatements$1 extends FunctionReference implements Function1<JsNode, Boolean> {
    InlineAstVisitor$doAcceptStatementList$additionalStatements$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "hasToBeInlined(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "hasToBeInlined";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(InlineAstVisitor.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(JsNode p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(((InlineAstVisitor) this.receiver).hasToBeInlined(p0));
    }
}
