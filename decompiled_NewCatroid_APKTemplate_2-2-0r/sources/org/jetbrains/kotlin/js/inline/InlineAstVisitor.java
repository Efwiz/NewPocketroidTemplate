package org.jetbrains.kotlin.js.inline;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor;
import org.jetbrains.kotlin.js.inline.clean.RemoveUnusedLocalFunctionDeclarationsKt;
import org.jetbrains.kotlin.js.inline.clean.SubstituteKTypesKt;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.inline.util.NamingUtilsKt;
import org.jetbrains.kotlin.js.translate.expression.InlineMetadata;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: InlineAstVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0016\u0010\r\u001a\u00020\b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0014J \u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00160\u0015H\u0016J\u001c\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00182\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0016J\u001e\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J \u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\n2\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00160\u0015H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0019\u001a\u00020\fH\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0016H\u0002J\u0010\u0010\u001d\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0012\u001a\u00020\nH\u0002J\u0012\u0010\u001f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u001c\u0010 \u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00182\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0016J\u001c\u0010 \u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\f2\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/InlineAstVisitor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "jsInliner", "Lorg/jetbrains/kotlin/js/inline/JsInliner;", "scope", "Lorg/jetbrains/kotlin/js/inline/InliningScope;", "(Lorg/jetbrains/kotlin/js/inline/JsInliner;Lorg/jetbrains/kotlin/js/inline/InliningScope;)V", "copyInlineMetadata", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "to", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "doAcceptStatementList", "statements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "endVisit", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "call", "hasToBeInlined", "", "node", "patchReturnsFromSecondaryConstructor", "tryCreatePropertyGetterInvocation", "tryCreatePropertySetterInvocation", "visit", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineAstVisitor extends JsVisitorWithContextImpl {
    private final JsInliner jsInliner;
    private final InliningScope scope;

    public InlineAstVisitor(JsInliner jsInliner, InliningScope scope) {
        Intrinsics.checkNotNullParameter(jsInliner, "jsInliner");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.jsInliner = jsInliner;
        this.scope = scope;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsInvocation x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsInvocation jsInvocation = x;
        InlineMetadata inlineMetadataDecompose = InlineMetadata.INSTANCE.decompose(jsInvocation);
        if (inlineMetadataDecompose != null) {
            this.jsInliner.process(new InlineFunctionDefinition(inlineMetadataDecompose.getFunction(), inlineMetadataDecompose.getTag().getValue()), x, this.scope.getFragment(), this.scope);
            return false;
        }
        FunctionWithWrapper functionWithWrapperTryExtractFunction = InlineMetadata.INSTANCE.tryExtractFunction(jsInvocation);
        if (functionWithWrapperTryExtractFunction != null) {
            this.jsInliner.process(new InlineFunctionDefinition(functionWithWrapperTryExtractFunction, null), x, this.scope.getFragment(), this.scope);
            return false;
        }
        return super.visit(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(final JsFunction x, final JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return ((Boolean) this.jsInliner.getCycleReporter().withFunction(x, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.InlineAstVisitor.visit.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(InlineAstVisitor.super.visit(x, ctx));
            }
        })).booleanValue();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsFunction function, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        patchReturnsFromSecondaryConstructor(function);
        JsBlock body = function.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        JsFunctionScope scope = function.getScope();
        Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
        NamingUtilsKt.refreshLabelNames(body, scope);
        JsFunction jsFunction = function;
        RemoveUnusedLocalFunctionDeclarationsKt.removeUnusedLocalFunctionDeclarations(jsFunction);
        new FunctionPostProcessor(function).apply();
        SubstituteKTypesKt.substituteKTypes(jsFunction);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsNameRef x, JsContext<? super JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsInvocation jsInvocationTryCreatePropertyGetterInvocation = tryCreatePropertyGetterInvocation(x);
        if (jsInvocationTryCreatePropertyGetterInvocation != null) {
            endVisit(jsInvocationTryCreatePropertyGetterInvocation, (JsContext<JsNode>) ctx);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsBinaryOperation x, JsContext<? super JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsInvocation jsInvocationTryCreatePropertySetterInvocation = tryCreatePropertySetterInvocation(x);
        if (jsInvocationTryCreatePropertySetterInvocation != null) {
            endVisit(jsInvocationTryCreatePropertySetterInvocation, (JsContext<JsNode>) ctx);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsInvocation call, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (hasToBeInlined(call)) {
            InlineableResult inlineableResultInline = this.jsInliner.inline(this.scope, call, (JsStatement) getLastStatementLevelContext().getCurrentNode());
            JsStatement inlineableBody = inlineableResultInline.getInlineableBody();
            JsExpression resultExpression = inlineableResultInline.getResultExpression();
            getLastStatementLevelContext().addPrevious(JsAstUtils.flattenStatement(inlineableBody));
            if (resultExpression == null) {
                getLastStatementLevelContext().removeMe();
            } else {
                ctx.replaceMe(resultExpression);
            }
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    protected void doAcceptStatementList(List<JsStatement> statements) {
        Intrinsics.checkNotNullParameter(statements, "statements");
        int size = 0;
        while (size < statements.size()) {
            List<JsStatement> listPreserveEvaluationOrder = ExpressionDecomposer.INSTANCE.preserveEvaluationOrder(statements.get(size), new InlineAstVisitor$doAcceptStatementList$additionalStatements$1(this));
            statements.addAll(size, listPreserveEvaluationOrder);
            size += listPreserveEvaluationOrder.size() + 1;
        }
        super.doAcceptStatementList(statements);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:31:0x005b  */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0025, code lost:
    
        if ((r5 != null ? hasToBeInlined(r5) : false) != false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x005c, code lost:
    
        if (r5 != false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:?, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean hasToBeInlined(org.jetbrains.kotlin.js.backend.ast.JsNode r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof org.jetbrains.kotlin.js.backend.ast.JsInvocation
            if (r0 == 0) goto Lb
            org.jetbrains.kotlin.js.backend.ast.JsInvocation r5 = (org.jetbrains.kotlin.js.backend.ast.JsInvocation) r5
            boolean r5 = r4.hasToBeInlined(r5)
            goto L60
        Lb:
            boolean r0 = r5 instanceof org.jetbrains.kotlin.js.backend.ast.JsNameRef
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L29
            org.jetbrains.kotlin.js.backend.ast.JsNameRef r5 = (org.jetbrains.kotlin.js.backend.ast.JsNameRef) r5
            java.lang.Boolean r0 = org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties.isInline(r5)
            if (r0 == 0) goto L5f
            org.jetbrains.kotlin.js.backend.ast.JsInvocation r5 = r4.tryCreatePropertyGetterInvocation(r5)
            if (r5 == 0) goto L24
            boolean r5 = r4.hasToBeInlined(r5)
            goto L25
        L24:
            r5 = r2
        L25:
            if (r5 == 0) goto L5f
        L27:
            r5 = r1
            goto L60
        L29:
            boolean r0 = r5 instanceof org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation
            if (r0 == 0) goto L5f
            org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation r5 = (org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation) r5
            org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator r0 = r5.getOperator()
            boolean r0 = r0.isAssignment()
            if (r0 == 0) goto L5f
            org.jetbrains.kotlin.js.backend.ast.JsExpression r0 = r5.getArg1()
            if (r0 == 0) goto L5b
            boolean r3 = r0 instanceof org.jetbrains.kotlin.js.backend.ast.JsNameRef
            if (r3 == 0) goto L5b
            org.jetbrains.kotlin.js.backend.ast.JsNameRef r0 = (org.jetbrains.kotlin.js.backend.ast.JsNameRef) r0
            java.lang.Boolean r0 = org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties.isInline(r0)
            if (r0 == 0) goto L5b
            org.jetbrains.kotlin.js.backend.ast.JsInvocation r5 = r4.tryCreatePropertySetterInvocation(r5)
            if (r5 == 0) goto L56
            boolean r5 = r4.hasToBeInlined(r5)
            goto L57
        L56:
            r5 = r2
        L57:
            if (r5 == 0) goto L5b
            r5 = r1
            goto L5c
        L5b:
            r5 = r2
        L5c:
            if (r5 == 0) goto L5f
            goto L27
        L5f:
            r5 = r2
        L60:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.js.inline.InlineAstVisitor.hasToBeInlined(org.jetbrains.kotlin.js.backend.ast.JsNode):boolean");
    }

    private final boolean hasToBeInlined(JsInvocation call) {
        return Intrinsics.areEqual((Object) MetadataProperties.isInline(call), (Object) true) && this.jsInliner.getFunctionDefinitionLoader().hasFunctionDefinition(call, this.scope);
    }

    private final void patchReturnsFromSecondaryConstructor(JsFunction function) {
        final JsName forcedReturnVariable = MetadataProperties.getForcedReturnVariable(function);
        if (forcedReturnVariable != null) {
            function.getBody().accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.InlineAstVisitor$patchReturnsFromSecondaryConstructor$1$1
                @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                public void visitReturn(JsReturn x) {
                    Intrinsics.checkNotNullParameter(x, "x");
                    x.setExpression(forcedReturnVariable.makeRef());
                }
            });
        }
    }

    private final JsInvocation tryCreatePropertyGetterInvocation(JsNameRef x) {
        if (MetadataProperties.isInline(x) == null || !(MetadataProperties.getDescriptor(x) instanceof PropertyGetterDescriptor)) {
            return null;
        }
        JsInvocation jsInvocation = new JsInvocation(x, new JsExpression[0]);
        copyInlineMetadata(x, jsInvocation);
        return jsInvocation;
    }

    private final JsInvocation tryCreatePropertySetterInvocation(JsBinaryOperation x) {
        if (!x.getOperator().isAssignment() || !(x.getArg1() instanceof JsNameRef)) {
            return null;
        }
        JsExpression arg1 = x.getArg1();
        Intrinsics.checkNotNull(arg1, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsNameRef");
        JsNameRef jsNameRef = (JsNameRef) arg1;
        if (MetadataProperties.isInline(jsNameRef) == null || !(MetadataProperties.getDescriptor(jsNameRef) instanceof PropertySetterDescriptor)) {
            return null;
        }
        JsInvocation jsInvocation = new JsInvocation(jsNameRef, x.getArg2());
        copyInlineMetadata(jsNameRef, jsInvocation);
        return jsInvocation;
    }

    private final void copyInlineMetadata(JsNameRef from, JsInvocation to) {
        MetadataProperties.setInline(to, MetadataProperties.isInline(from));
        MetadataProperties.setDescriptor(to, MetadataProperties.getDescriptor(from));
        MetadataProperties.setPsiElement(to, MetadataProperties.getPsiElement(from));
    }
}
