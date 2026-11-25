package org.jetbrains.kotlin.js.inline;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.clean.RemoveDefaultInitializersKt;
import org.jetbrains.kotlin.js.inline.context.InliningContext;
import org.jetbrains.kotlin.js.inline.context.NamingContext;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FunctionUtilsKt;
import org.jetbrains.kotlin.js.inline.util.InvocationUtilsKt;
import org.jetbrains.kotlin.js.inline.util.NamingUtilsKt;
import org.jetbrains.kotlin.js.inline.util.RewriteUtilsKt;
import org.jetbrains.kotlin.js.inline.util.rewriters.ReturnReplacingVisitor;

/* JADX INFO: compiled from: FunctionInlineMutator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 92\u00020\u0001:\u00019B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\"\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\u0007H\u0002J\u000e\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0002J\b\u0010\u000f\u001a\u00020)H\u0002J\u0012\u0010*\u001a\u0004\u0018\u00010\u00032\u0006\u0010+\u001a\u00020(H\u0002J\u0006\u0010,\u001a\u00020)J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020.0'H\u0002J\b\u0010/\u001a\u00020)H\u0002J\n\u00100\u001a\u0004\u0018\u00010\u001bH\u0002J\b\u00101\u001a\u00020)H\u0002J\u0010\u00102\u001a\u0002032\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\b\u00104\u001a\u00020#H\u0002J\b\u00105\u001a\u00020#H\u0002J\u0010\u00106\u001a\u00020#2\u0006\u00107\u001a\u00020\nH\u0002J\u0010\u00108\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0007H\u0002R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/FunctionInlineMutator;", "", "call", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "inliningContext", "Lorg/jetbrains/kotlin/js/inline/context/InliningContext;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Lorg/jetbrains/kotlin/js/inline/context/InliningContext;Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)V", "body", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "getBody", "()Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "breakLabel", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "getBreakLabel", "()Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "setBreakLabel", "(Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;)V", "currentStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "invokedFunction", "namingContext", "Lorg/jetbrains/kotlin/js/inline/context/NamingContext;", "getNamingContext", "()Lorg/jetbrains/kotlin/js/inline/context/NamingContext;", "resultExpr", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "getResultExpr", "()Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "setResultExpr", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)V", "resultName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "applyCapturedArgs", "", "inner", "outer", "getArguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "", "getInnerCall", "qualifier", "getLabelPrefix", "getParameters", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "getResultLabel", "getResultReference", "getThisAlias", "isResultNeeded", "", "process", "processReturns", "replaceThis", "block", "uncoverClosure", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionInlineMutator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final JsBlock body;
    private JsLabel breakLabel;
    private final JsInvocation call;
    private final JsStatement currentStatement;
    private final InliningContext inliningContext;
    private final JsFunction invokedFunction;
    private final NamingContext namingContext;
    private JsNameRef resultExpr;
    private JsName resultName;

    public /* synthetic */ FunctionInlineMutator(JsInvocation jsInvocation, InliningContext inliningContext, JsFunction jsFunction, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsInvocation, inliningContext, jsFunction);
    }

    @JvmStatic
    public static final InlineableResult getInlineableCallReplacement(JsInvocation jsInvocation, JsFunction jsFunction, InliningContext inliningContext) {
        return INSTANCE.getInlineableCallReplacement(jsInvocation, jsFunction, inliningContext);
    }

    private FunctionInlineMutator(JsInvocation jsInvocation, InliningContext inliningContext, JsFunction jsFunction) {
        this.call = jsInvocation;
        this.inliningContext = inliningContext;
        this.namingContext = inliningContext.newNamingContext();
        this.currentStatement = inliningContext.getCurrentStatement();
        JsFunction jsFunctionDeepCopy = jsFunction.deepCopy();
        Intrinsics.checkNotNullExpressionValue(jsFunctionDeepCopy, "deepCopy(...)");
        JsFunction jsFunctionUncoverClosure = uncoverClosure(jsFunctionDeepCopy);
        this.invokedFunction = jsFunctionUncoverClosure;
        JsBlock body = jsFunctionUncoverClosure.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        this.body = body;
    }

    public final NamingContext getNamingContext() {
        return this.namingContext;
    }

    public final JsBlock getBody() {
        return this.body;
    }

    public final JsNameRef getResultExpr() {
        return this.resultExpr;
    }

    public final void setResultExpr(JsNameRef jsNameRef) {
        this.resultExpr = jsNameRef;
    }

    public final JsLabel getBreakLabel() {
        return this.breakLabel;
    }

    public final void setBreakLabel(JsLabel jsLabel) {
        this.breakLabel = jsLabel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void process() {
        JsNameRef jsNameRef;
        List<JsExpression> arguments = getArguments();
        List<JsParameter> parameters = getParameters();
        if (arguments.size() > parameters.size()) {
            arguments.size();
            parameters.size();
            arguments = arguments.subList(0, parameters.size());
        }
        RemoveDefaultInitializersKt.removeDefaultInitializers(arguments, parameters, this.body);
        NamingUtilsKt.aliasArgumentsIfNeeded(this.namingContext, arguments, parameters, this.call.getSource());
        NamingUtilsKt.renameLocalNames(this.namingContext, this.invokedFunction);
        processReturns();
        this.namingContext.applyRenameTo(this.body);
        JsNameRef jsNameRef2 = this.resultExpr;
        if (jsNameRef2 != null) {
            JsNode jsNodeApplyRenameTo = this.namingContext.applyRenameTo(jsNameRef2);
            Intrinsics.checkNotNull(jsNodeApplyRenameTo, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsNameRef");
            jsNameRef = (JsNameRef) jsNodeApplyRenameTo;
        } else {
            jsNameRef = null;
        }
        this.resultExpr = jsNameRef;
    }

    private final JsFunction uncoverClosure(JsFunction invokedFunction) {
        JsFunction innerFunction = FunctionUtilsKt.getInnerFunction(invokedFunction);
        JsExpression qualifier = this.call.getQualifier();
        Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
        JsInvocation innerCall = getInnerCall(qualifier);
        if (innerCall != null && innerFunction != null) {
            JsBlock body = innerFunction.getBody();
            Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
            replaceThis(body);
            applyCapturedArgs(innerCall, innerFunction, invokedFunction);
            return innerFunction;
        }
        JsBlock body2 = invokedFunction.getBody();
        Intrinsics.checkNotNullExpressionValue(body2, "getBody(...)");
        replaceThis(body2);
        return invokedFunction;
    }

    private final JsInvocation getInnerCall(JsExpression qualifier) {
        JsExpression qualifier2;
        if (qualifier instanceof JsInvocation) {
            return (JsInvocation) qualifier;
        }
        if (qualifier instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) qualifier;
            if (Intrinsics.areEqual(jsNameRef.getIdent(), "call")) {
                qualifier2 = jsNameRef.getQualifier();
            } else {
                JsName name2 = jsNameRef.getName();
                JsNode staticRef = name2 != null ? MetadataProperties.getStaticRef(name2) : null;
                qualifier2 = staticRef instanceof JsExpression ? (JsExpression) staticRef : null;
            }
            if (qualifier2 != null) {
                return getInnerCall(qualifier2);
            }
        }
        return null;
    }

    private final void applyCapturedArgs(JsInvocation call, JsFunction inner, JsFunction outer) {
        NamingContext namingContextNewNamingContext = this.inliningContext.newNamingContext();
        List<JsExpression> arguments = call.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        List<JsParameter> parameters = outer.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        NamingUtilsKt.aliasArgumentsIfNeeded(namingContextNewNamingContext, arguments, parameters, call.getSource());
        namingContextNewNamingContext.applyRenameTo(inner);
    }

    private final void replaceThis(JsBlock block) {
        JsExpression thisReplacement;
        Companion companion = INSTANCE;
        if (!companion.hasThisReference(block) || (thisReplacement = companion.getThisReplacement(this.call)) == null || (thisReplacement instanceof JsThisRef)) {
            return;
        }
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(getThisAlias());
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        this.namingContext.newVar(jsNameDeclareTemporaryName, thisReplacement, this.call.getSource());
        RewriteUtilsKt.replaceThisReference(block, jsNameDeclareTemporaryName.makeRef());
    }

    private final void processReturns() {
        this.resultExpr = getResultReference();
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(getBreakLabel());
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        JsLabel jsLabel = new JsLabel(jsNameDeclareTemporaryName);
        MetadataProperties.setSynthetic(jsLabel, true);
        this.breakLabel = jsLabel;
        new ReturnReplacingVisitor(this.resultExpr, jsNameDeclareTemporaryName.makeRef(), this.invokedFunction, MetadataProperties.isSuspend(this.call)).accept(this.body);
    }

    private final JsNameRef getResultReference() {
        if (!isResultNeeded(this.call)) {
            return null;
        }
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(getResultLabel());
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        this.resultName = jsNameDeclareTemporaryName;
        NamingContext.newVar$default(this.namingContext, jsNameDeclareTemporaryName, null, this.call.getSource(), 2, null);
        return jsNameDeclareTemporaryName.makeRef();
    }

    private final List<JsExpression> getArguments() {
        List<JsExpression> arguments = this.call.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        return InvocationUtilsKt.isCallInvocation(this.call) ? arguments.subList(1, arguments.size()) : arguments;
    }

    private final boolean isResultNeeded(JsInvocation call) {
        JsStatement jsStatement = this.currentStatement;
        return ((jsStatement instanceof JsExpressionStatement) && Intrinsics.areEqual(call, ((JsExpressionStatement) jsStatement).getExpression())) ? false : true;
    }

    private final List<JsParameter> getParameters() {
        List<JsParameter> parameters = this.invokedFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        return parameters;
    }

    private final String getResultLabel() {
        return getLabelPrefix() + "result";
    }

    private final String getBreakLabel() {
        return getLabelPrefix() + PsiKeyword.BREAK;
    }

    private final String getThisAlias() {
        return "$this";
    }

    public final String getLabelPrefix() {
        String simpleIdent = InvocationUtilsKt.getSimpleIdent(this.call);
        if (simpleIdent == null) {
            simpleIdent = "inline$";
        }
        return StringsKt.endsWith$default(simpleIdent, "$", false, 2, (Object) null) ? simpleIdent : simpleIdent + '$';
    }

    /* JADX INFO: compiled from: FunctionInlineMutator.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0003J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/FunctionInlineMutator$Companion;", "", "()V", "getInlineableCallReplacement", "Lorg/jetbrains/kotlin/js/inline/InlineableResult;", "call", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "inliningContext", "Lorg/jetbrains/kotlin/js/inline/context/InliningContext;", "getThisReplacement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "hasThisReference", "", "body", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final InlineableResult getInlineableCallReplacement(JsInvocation call, JsFunction function, InliningContext inliningContext) {
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
            FunctionInlineMutator functionInlineMutator = new FunctionInlineMutator(call, inliningContext, function, null);
            functionInlineMutator.process();
            JsLabel body = functionInlineMutator.getBody();
            JsLabel breakLabel = functionInlineMutator.getBreakLabel();
            if (breakLabel != null) {
                breakLabel.setStatement(body);
                body = breakLabel;
            }
            return new InlineableResult(body, functionInlineMutator.getResultExpr());
        }

        /* JADX INFO: Access modifiers changed from: private */
        @JvmStatic
        public final JsExpression getThisReplacement(JsInvocation call) {
            if (InvocationUtilsKt.isCallInvocation(call)) {
                return call.getArguments().get(0);
            }
            if (InvocationUtilsKt.hasCallerQualifier(call)) {
                return InvocationUtilsKt.getCallerQualifier(call);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasThisReference(JsBlock body) {
            return !CollectUtilsKt.collectInstances(JsThisRef.class, body).isEmpty();
        }
    }
}
