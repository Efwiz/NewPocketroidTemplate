package org.jetbrains.kotlin.js.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.translate.expression.InlineMetadata;

/* JADX INFO: compiled from: InlineSuspendFunctionSplitter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/InlineSuspendFunctionSplitter;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "scope", "Lorg/jetbrains/kotlin/js/inline/ImportIntoFragmentInliningScope;", "(Lorg/jetbrains/kotlin/js/inline/ImportIntoFragmentInliningScope;)V", "getScope", "()Lorg/jetbrains/kotlin/js/inline/ImportIntoFragmentInliningScope;", "visit", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineSuspendFunctionSplitter extends JsVisitorWithContextImpl {
    private final ImportIntoFragmentInliningScope scope;

    public final ImportIntoFragmentInliningScope getScope() {
        return this.scope;
    }

    public InlineSuspendFunctionSplitter(ImportIntoFragmentInliningScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.scope = scope;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsInvocation x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsInvocation jsInvocation = x;
        InlineMetadata inlineMetadataDecompose = InlineMetadata.INSTANCE.decompose(jsInvocation);
        if (inlineMetadataDecompose != null) {
            FunctionWithWrapper function = inlineMetadataDecompose.getFunction();
            if (MetadataProperties.getCoroutineMetadata(function.getFunction()) != null) {
                ctx.replaceMe(this.scope.importFunctionDefinition(new InlineFunctionDefinition(function, inlineMetadataDecompose.getTag().getValue())));
                function.getFunction().setName(null);
                MetadataProperties.setCoroutineMetadata(function.getFunction(), null);
                MetadataProperties.setInlineableCoroutineBody(function.getFunction(), true);
                getLastStatementLevelContext().addNext(x.makeStmt());
            }
            return false;
        }
        FunctionWithWrapper functionWithWrapperTryExtractFunction = InlineMetadata.INSTANCE.tryExtractFunction(jsInvocation);
        if (functionWithWrapperTryExtractFunction != null) {
            if (MetadataProperties.getCoroutineMetadata(functionWithWrapperTryExtractFunction.getFunction()) != null) {
                ctx.replaceMe(this.scope.importFunctionDefinition(new InlineFunctionDefinition(functionWithWrapperTryExtractFunction, null)));
            }
            return false;
        }
        return super.visit(x, ctx);
    }
}
