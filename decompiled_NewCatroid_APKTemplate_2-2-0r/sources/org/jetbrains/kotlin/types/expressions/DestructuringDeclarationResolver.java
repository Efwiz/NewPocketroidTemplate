package org.jetbrains.kotlin.types.expressions;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DataClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.LocalVariableResolver;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.checkers.TrailingCommaChecker;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: DestructuringDeclarationResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ2\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J4\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J2\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001eJ8\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DestructuringDeclarationResolver;", "", "fakeCallResolver", "Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver;", "localVariableResolver", "Lorg/jetbrains/kotlin/resolve/LocalVariableResolver;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "(Lorg/jetbrains/kotlin/types/expressions/FakeCallResolver;Lorg/jetbrains/kotlin/resolve/LocalVariableResolver;Lorg/jetbrains/kotlin/resolve/TypeResolver;)V", "defineLocalVariablesFromDestructuringDeclaration", "", "writableScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;", "destructuringDeclaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "initializer", "Lorg/jetbrains/kotlin/psi/KtExpression;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "getExpectedTypeForComponent", "Lorg/jetbrains/kotlin/types/KotlinType;", "entry", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "resolveComponentFunctionAndGetType", "componentName", "Lorg/jetbrains/kotlin/name/Name;", "resolveInitializer", "componentIndex", "", "resolveLocalVariablesFromDestructuringDeclaration", "", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DestructuringDeclarationResolver {
    private final FakeCallResolver fakeCallResolver;
    private final LocalVariableResolver localVariableResolver;
    private final TypeResolver typeResolver;

    public DestructuringDeclarationResolver(FakeCallResolver fakeCallResolver, LocalVariableResolver localVariableResolver, TypeResolver typeResolver) {
        Intrinsics.checkNotNullParameter(fakeCallResolver, "fakeCallResolver");
        Intrinsics.checkNotNullParameter(localVariableResolver, "localVariableResolver");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        this.fakeCallResolver = fakeCallResolver;
        this.localVariableResolver = localVariableResolver;
        this.typeResolver = typeResolver;
    }

    public final List<VariableDescriptor> resolveLocalVariablesFromDestructuringDeclaration(LexicalScope scope, KtDestructuringDeclaration destructuringDeclaration, ReceiverValue receiver, KtExpression initializer, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(destructuringDeclaration, "destructuringDeclaration");
        Intrinsics.checkNotNullParameter(context, "context");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : destructuringDeclaration.getEntries()) {
            Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
            KotlinType kotlinTypeResolveInitializer = resolveInitializer(ktDestructuringDeclarationEntry, receiver, initializer, context, i);
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            arrayList.add(this.localVariableResolver.resolveLocalVariableDescriptorWithType$frontend(scope, ktDestructuringDeclarationEntry, kotlinTypeResolveInitializer, trace));
            i++;
        }
        TrailingCommaChecker trailingCommaChecker = TrailingCommaChecker.INSTANCE;
        PsiElement trailingComma = destructuringDeclaration.getTrailingComma();
        BindingTrace trace2 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace2, "trace");
        LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        trailingCommaChecker.check(trailingComma, trace2, languageVersionSettings);
        return arrayList;
    }

    public final void defineLocalVariablesFromDestructuringDeclaration(LexicalWritableScope writableScope, KtDestructuringDeclaration destructuringDeclaration, ReceiverValue receiver, KtExpression initializer, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(writableScope, "writableScope");
        Intrinsics.checkNotNullParameter(destructuringDeclaration, "destructuringDeclaration");
        Intrinsics.checkNotNullParameter(context, "context");
        LexicalWritableScope lexicalWritableScope = writableScope;
        for (VariableDescriptor variableDescriptor : resolveLocalVariablesFromDestructuringDeclaration(lexicalWritableScope, destructuringDeclaration, receiver, initializer, context)) {
            ExpressionTypingUtils.checkVariableShadowing(lexicalWritableScope, context.trace, variableDescriptor);
            writableScope.addVariableDescriptor(variableDescriptor);
        }
    }

    public final KotlinType resolveInitializer(KtDestructuringDeclarationEntry entry, ReceiverValue receiver, KtExpression initializer, ExpressionTypingContext context, int componentIndex) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        Intrinsics.checkNotNullParameter(context, "context");
        return resolveComponentFunctionAndGetType(DataClassDescriptorResolver.INSTANCE.createComponentName(componentIndex + 1), context, entry, receiver, initializer);
    }

    private static final ErrorType resolveComponentFunctionAndGetType$errorType(Name name2) {
        ErrorTypeKind errorTypeKind = ErrorTypeKind.ERROR_TYPE_FOR_DESTRUCTURING_COMPONENT;
        String string = name2.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return ErrorUtils.createErrorType(errorTypeKind, string);
    }

    private final KotlinType resolveComponentFunctionAndGetType(Name componentName, ExpressionTypingContext context, KtDestructuringDeclarationEntry entry, ReceiverValue receiver, KtExpression initializer) {
        if (receiver == null) {
            return resolveComponentFunctionAndGetType$errorType(componentName);
        }
        KotlinType expectedTypeForComponent = getExpectedTypeForComponent(context, entry);
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = context.replaceExpectedType(expectedTypeForComponent).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceContextDependency, "replaceContextDependency(...)");
        ExpressionTypingContext expressionTypingContext = expressionTypingContextReplaceContextDependency;
        KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry = entry;
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveFakeCall = this.fakeCallResolver.resolveFakeCall(expressionTypingContext, receiver, componentName, ktDestructuringDeclarationEntry, initializer == null ? ktDestructuringDeclarationEntry : initializer, FakeCallKind.COMPONENT, CollectionsKt.emptyList());
        if (!overloadResolutionResultsResolveFakeCall.isSuccess()) {
            return resolveComponentFunctionAndGetType$errorType(componentName);
        }
        context.trace.record(BindingContext.COMPONENT_RESOLVED_CALL, entry, overloadResolutionResultsResolveFakeCall.mo7436getResultingCall());
        KotlinType returnType = ((FunctionDescriptor) overloadResolutionResultsResolveFakeCall.mo7437getResultingDescriptor()).getReturnType();
        if (returnType != null && !TypeUtils.noExpectedType(expectedTypeForComponent) && !KotlinTypeChecker.DEFAULT.isSubtypeOf(returnType, expectedTypeForComponent)) {
            BindingTrace bindingTrace = context.trace;
            DiagnosticFactory3<KtExpression, Name, KotlinType, KotlinType> diagnosticFactory3 = Errors.COMPONENT_FUNCTION_RETURN_TYPE_MISMATCH;
            if (initializer == null) {
                initializer = ktDestructuringDeclarationEntry;
            }
            bindingTrace.report(diagnosticFactory3.on(initializer, componentName, returnType, expectedTypeForComponent));
        }
        return returnType == null ? resolveComponentFunctionAndGetType$errorType(componentName) : returnType;
    }

    private final KotlinType getExpectedTypeForComponent(ExpressionTypingContext context, KtDestructuringDeclarationEntry entry) {
        KtTypeReference ktTypeReferenceMo7396getTypeReference = entry.mo7396getTypeReference();
        if (ktTypeReferenceMo7396getTypeReference == null) {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            return NO_EXPECTED_TYPE;
        }
        TypeResolver typeResolver = this.typeResolver;
        LexicalScope scope = context.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        return typeResolver.resolveType(scope, ktTypeReferenceMo7396getTypeReference, trace, true);
    }
}
