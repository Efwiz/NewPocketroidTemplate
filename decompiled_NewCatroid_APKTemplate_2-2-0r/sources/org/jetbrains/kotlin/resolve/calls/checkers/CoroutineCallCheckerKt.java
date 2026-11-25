package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: coroutineCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e\u001a,\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0014\u001a\u00020\u0015\u001a\n\u0010\u0018\u001a\u00020\u0019*\u00020\u0017\u001a\n\u0010\u0018\u001a\u00020\u0019*\u00020\u001a\u001a\n\u0010\u0018\u001a\u00020\u0019*\u00020\u0004\u001a\n\u0010\u001b\u001a\u00020\u0019*\u00020\u001c\u001a\u0014\u0010\u001d\u001a\u00020\u0019*\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0017H\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006 "}, d2 = {"ALLOWED_SCOPE_KINDS", "", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "COROUTINE_CONTEXT_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getCOROUTINE_CONTEXT_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "checkCoroutinesFeature", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "checkRestrictsSuspension", "enclosingSuspendCallableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "findEnclosingSuspendFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isBuiltInCoroutineContext", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "isRestrictsSuspensionReceiver", "Lorg/jetbrains/kotlin/types/KotlinType;", "isScopeForDefaultParameterValuesOf", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "enclosingSuspendFunction", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineCallCheckerKt {
    private static final Set<LexicalScopeKind> ALLOWED_SCOPE_KINDS;
    private static final FqName COROUTINE_CONTEXT_FQ_NAME;

    public static final FqName getCOROUTINE_CONTEXT_FQ_NAME() {
        return COROUTINE_CONTEXT_FQ_NAME;
    }

    static {
        FqName fqNameChild = StandardNames.COROUTINES_PACKAGE_FQ_NAME.child(Name.identifier("coroutineContext"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        COROUTINE_CONTEXT_FQ_NAME = fqNameChild;
        ALLOWED_SCOPE_KINDS = SetsKt.setOf((Object[]) new LexicalScopeKind[]{LexicalScopeKind.FUNCTION_INNER_SCOPE, LexicalScopeKind.FUNCTION_HEADER_FOR_DESTRUCTURING});
    }

    public static final boolean isBuiltInCoroutineContext(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        return Intrinsics.areEqual(fqName, StandardNames.COROUTINES_PACKAGE_FQ_NAME.child(Name.identifier("coroutineContext")));
    }

    public static final boolean isBuiltInCoroutineContext(FunctionDescriptor functionDescriptor) {
        PropertyDescriptor correspondingProperty;
        FqName fqNameSafe;
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        PropertyGetterDescriptor propertyGetterDescriptor = functionDescriptor instanceof PropertyGetterDescriptor ? (PropertyGetterDescriptor) functionDescriptor : null;
        return (propertyGetterDescriptor == null || (correspondingProperty = propertyGetterDescriptor.getCorrespondingProperty()) == null || (fqNameSafe = DescriptorUtilsKt.getFqNameSafe(correspondingProperty)) == null || !isBuiltInCoroutineContext(fqNameSafe)) ? false : true;
    }

    public static final boolean isBuiltInCoroutineContext(PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        return isBuiltInCoroutineContext(DescriptorUtilsKt.getFqNameSafe(propertyDescriptor));
    }

    public static final FunctionDescriptor findEnclosingSuspendFunction(CallCheckerContext context) {
        HierarchicalScope next;
        Intrinsics.checkNotNullParameter(context, "context");
        Iterator<HierarchicalScope> it = ScopeUtilsKt.getParentsWithSelf(context.getScope()).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            HierarchicalScope hierarchicalScope = next;
            if (hierarchicalScope instanceof LexicalScope) {
                LexicalScope lexicalScope = (LexicalScope) hierarchicalScope;
                if (ALLOWED_SCOPE_KINDS.contains(lexicalScope.getKind())) {
                    DeclarationDescriptor ownerDescriptor = lexicalScope.getOwnerDescriptor();
                    FunctionDescriptor functionDescriptor = ownerDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) ownerDescriptor : null;
                    if (functionDescriptor != null && functionDescriptor.isSuspend()) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        LexicalScope lexicalScope2 = (LexicalScope) next;
        return (FunctionDescriptor) (lexicalScope2 != null ? lexicalScope2.getOwnerDescriptor() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isScopeForDefaultParameterValuesOf(HierarchicalScope hierarchicalScope, FunctionDescriptor functionDescriptor) {
        if (hierarchicalScope instanceof LexicalScope) {
            LexicalScope lexicalScope = (LexicalScope) hierarchicalScope;
            if (lexicalScope.getKind() == LexicalScopeKind.DEFAULT_VALUE && Intrinsics.areEqual(lexicalScope.getOwnerDescriptor(), functionDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public static final void checkCoroutinesFeature(LanguageVersionSettings languageVersionSettings, DiagnosticSink diagnosticHolder, PsiElement reportOn) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        if (!languageVersionSettings.supportsFeature(LanguageFeature.ReleaseCoroutines) || languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_3) >= 0) {
            return;
        }
        diagnosticHolder.report(Errors.UNSUPPORTED.on(reportOn, "cannot use release coroutines with api version less than 1.3"));
    }

    public static final boolean isRestrictsSuspensionReceiver(KotlinType kotlinType) {
        Annotations annotations;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOf(kotlinType), (Iterable) TypeUtilsKt.supertypes(kotlinType));
        if ((listPlus instanceof Collection) && listPlus.isEmpty()) {
            return false;
        }
        Iterator it = listPlus.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor this$0 = ((KotlinType) it.next()).getConstructor().getThis$0();
            if (this$0 != null && (annotations = this$0.getAnnotations()) != null) {
                FqName fqNameChild = StandardNames.COROUTINES_PACKAGE_FQ_NAME.child(Name.identifier("RestrictsSuspension"));
                Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
                if (annotations.hasAnnotation(fqNameChild)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final boolean checkRestrictsSuspension$isRestrictsSuspensionReceiver(ReceiverValue receiverValue) {
        KotlinType type = receiverValue.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return isRestrictsSuspensionReceiver(type);
    }

    private static final boolean checkRestrictsSuspension$sameInstance(ReceiverValue receiverValue, CallCheckerContext callCheckerContext, ReceiverValue receiverValue2) {
        if (receiverValue2 == null) {
            return false;
        }
        if (receiverValue.getOriginal() == receiverValue2.getOriginal()) {
            return true;
        }
        ReceiverValue value = null;
        ExpressionReceiver expressionReceiver = receiverValue2 instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValue2 : null;
        KtExpression expression = expressionReceiver != null ? expressionReceiver.getExpression() : null;
        KtThisExpression ktThisExpression = expression instanceof KtThisExpression ? (KtThisExpression) expression : null;
        KtReferenceExpression instanceReference = ktThisExpression != null ? ktThisExpression.getInstanceReference() : null;
        DeclarationDescriptor declarationDescriptor = instanceReference != null ? (DeclarationDescriptor) callCheckerContext.getTrace().get(BindingContext.REFERENCE_TARGET, instanceReference) : null;
        if (declarationDescriptor instanceof CallableDescriptor) {
            ReceiverParameterDescriptor extensionReceiverParameter = ((CallableDescriptor) declarationDescriptor).getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                value = extensionReceiverParameter.getValue();
            }
        } else if (declarationDescriptor instanceof ClassDescriptor) {
            value = ((ClassDescriptor) declarationDescriptor).getThisAsReceiverParameter().getValue();
        }
        return receiverValue == value;
    }

    private static final void checkRestrictsSuspension$reportError(CallCheckerContext callCheckerContext, PsiElement psiElement) {
        callCheckerContext.getTrace().report(Errors.ILLEGAL_RESTRICTED_SUSPENDING_FUNCTION_CALL.on(psiElement));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkRestrictsSuspension(CallableDescriptor callableDescriptor, ResolvedCall<?> resolvedCall, PsiElement psiElement, CallCheckerContext callCheckerContext) {
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        ReceiverValue value = extensionReceiverParameter != null ? extensionReceiverParameter.getValue() : null;
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = callableDescriptor.mo6753getDispatchReceiverParameter();
        ReceiverValue value2 = receiverParameterDescriptorMo6753getDispatchReceiverParameter != null ? receiverParameterDescriptorMo6753getDispatchReceiverParameter.getValue() : null;
        for (ReceiverValue receiverValue : CollectionsKt.listOfNotNull((Object[]) new ReceiverValue[]{resolvedCall.mo7304getDispatchReceiver(), resolvedCall.mo7305getExtensionReceiver()})) {
            Intrinsics.checkNotNull(receiverValue);
            if (checkRestrictsSuspension$isRestrictsSuspensionReceiver(receiverValue) && (value == null || !checkRestrictsSuspension$sameInstance(value, callCheckerContext, receiverValue))) {
                if (value2 == null || !checkRestrictsSuspension$sameInstance(value2, callCheckerContext, receiverValue)) {
                    checkRestrictsSuspension$reportError(callCheckerContext, psiElement);
                    return;
                }
            }
        }
        if (value == null || !checkRestrictsSuspension$isRestrictsSuspensionReceiver(value) || checkRestrictsSuspension$sameInstance(value, callCheckerContext, resolvedCall.mo7304getDispatchReceiver())) {
            return;
        }
        if (checkRestrictsSuspension$sameInstance(value, callCheckerContext, resolvedCall.mo7305getExtensionReceiver())) {
            ReceiverParameterDescriptor extensionReceiverParameter2 = resolvedCall.get$suspendPropertyDescriptor().getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter2);
            ReceiverValue value3 = extensionReceiverParameter2.getValue();
            Intrinsics.checkNotNullExpressionValue(value3, "getValue(...)");
            if (checkRestrictsSuspension$isRestrictsSuspensionReceiver(value3)) {
                return;
            }
        }
        checkRestrictsSuspension$reportError(callCheckerContext, psiElement);
    }
}
