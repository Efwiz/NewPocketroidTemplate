package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.gwt.dev.js.parserExceptions.AbortParsingException;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsRootScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.parser.ParserUtilsKt;
import org.jetbrains.kotlin.js.patterns.DescriptorPredicate;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.resolve.BindingContextSlicesJsKt;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: JsCallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "(Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;)V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "Companion", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCallChecker implements CallChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final DescriptorPredicate JS_PATTERN;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;

    @JvmStatic
    public static final String extractStringValue(CompileTimeConstant<?> compileTimeConstant) {
        return INSTANCE.extractStringValue(compileTimeConstant);
    }

    @JvmStatic
    public static final <F extends CallableDescriptor> boolean isJsCall(ResolvedCall<F> resolvedCall) {
        return INSTANCE.isJsCall(resolvedCall);
    }

    public JsCallChecker(ConstantExpressionEvaluator constantExpressionEvaluator) {
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        this.constantExpressionEvaluator = constantExpressionEvaluator;
    }

    /* JADX INFO: compiled from: JsCallChecker.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\bH\u0007J\u001e\u0010\t\u001a\u00020\n\"\n\b\u0000\u0010\u000b*\u0004\u0018\u00010\f*\b\u0012\u0004\u0012\u0002H\u000b0\rH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallChecker$Companion;", "", "()V", "JS_PATTERN", "Lorg/jetbrains/kotlin/js/patterns/DescriptorPredicate;", "extractStringValue", "", "compileTimeConstant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "isJsCall", "", "F", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final <F extends CallableDescriptor> boolean isJsCall(ResolvedCall<F> resolvedCall) {
            Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
            return (resultingDescriptor instanceof SimpleFunctionDescriptor) && JsCallChecker.JS_PATTERN.test(resultingDescriptor);
        }

        @JvmStatic
        public final String extractStringValue(CompileTimeConstant<?> compileTimeConstant) {
            TypedCompileTimeConstant typedCompileTimeConstant = compileTimeConstant instanceof TypedCompileTimeConstant ? (TypedCompileTimeConstant) compileTimeConstant : null;
            ConstantValue constantValue = typedCompileTimeConstant != null ? typedCompileTimeConstant.getConstantValue() : null;
            StringValue stringValue = constantValue instanceof StringValue ? (StringValue) constantValue : null;
            if (stringValue != null) {
                return stringValue.getValue();
            }
            return null;
        }
    }

    static {
        DescriptorPredicate descriptorPredicatePattern = PatternBuilder.pattern("kotlin.js.js(String)");
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern, "pattern(...)");
        JS_PATTERN = descriptorPredicatePattern;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtValueArgument ktValueArgument;
        KtExpression argumentExpression;
        String name2;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.isAnnotationContext()) {
            return;
        }
        Companion companion = INSTANCE;
        if (companion.isJsCall(resolvedCall)) {
            KtElement callElement = resolvedCall.getCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
            if (callElement instanceof KtCallExpression) {
                KtValueArgumentList valueArgumentList = ((KtCallExpression) callElement).getValueArgumentList();
                List<KtValueArgument> arguments = valueArgumentList != null ? valueArgumentList.getArguments() : null;
                if (arguments == null || (ktValueArgument = (KtValueArgument) CollectionsKt.firstOrNull((List) arguments)) == null || (argumentExpression = ktValueArgument.getArgumentExpression()) == null) {
                    return;
                }
                TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(context.getTrace(), "JsCallChecker");
                Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
                String strExtractStringValue = companion.extractStringValue(this.constantExpressionEvaluator.evaluateExpression(argumentExpression, temporaryBindingTraceCreate, TypeUtils.NO_EXPECTED_TYPE));
                if (strExtractStringValue == null) {
                    context.getTrace().report(ErrorsJs.JSCODE_ARGUMENT_SHOULD_BE_CONSTANT.on(argumentExpression));
                    return;
                }
                temporaryBindingTraceCreate.commit();
                try {
                    JsCodeErrorReporter jsCodeErrorReporter = new JsCodeErrorReporter(argumentExpression, strExtractStringValue, context.getTrace());
                    JsFunctionScope jsFunctionScope = new JsFunctionScope(new JsRootScope(new JsProgram()), "<js fun>");
                    CodePosition codePosition = new CodePosition(0, 0);
                    PsiFile containingFile = reportOn.getContainingFile();
                    if (containingFile == null || (name2 = containingFile.getName()) == null) {
                        name2 = "<unknown file>";
                    }
                    List<JsStatement> expressionOrStatement = ParserUtilsKt.parseExpressionOrStatement(strExtractStringValue, jsCodeErrorReporter, jsFunctionScope, codePosition, name2);
                    if (expressionOrStatement == null || expressionOrStatement.isEmpty()) {
                        context.getTrace().report(ErrorsJs.JSCODE_NO_JAVASCRIPT_PRODUCED.on(argumentExpression));
                    }
                } catch (AbortParsingException unused) {
                }
                context.getTrace().record(BindingContextSlicesJsKt.LEXICAL_SCOPE_FOR_JS, resolvedCall, context.getScope());
            }
        }
    }
}
