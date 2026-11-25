package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaPropertyDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.LanguageVersionSettingsProvider;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: InterfaceDefaultMethodCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J$\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\"\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u0018H\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/InterfaceDefaultMethodCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lorg/jetbrains/kotlin/config/JvmTarget;Lcom/intellij/openapi/project/Project;)V", "ideService", "Lorg/jetbrains/kotlin/resolve/LanguageVersionSettingsProvider;", "getJvmTarget", "()Lorg/jetbrains/kotlin/config/JvmTarget;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "findInterfaceMember", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "descriptorToSearch", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "startExpression", "Lorg/jetbrains/kotlin/psi/KtSuperExpression;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getSuperCallLabelTarget", "expression", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InterfaceDefaultMethodCallChecker implements CallChecker {
    private final LanguageVersionSettingsProvider ideService;
    private final JvmTarget jvmTarget;

    public InterfaceDefaultMethodCallChecker(JvmTarget jvmTarget, Project project) {
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        Intrinsics.checkNotNullParameter(project, "project");
        this.jvmTarget = jvmTarget;
        this.ideService = LanguageVersionSettingsProvider.INSTANCE.getInstance(project);
    }

    public final JvmTarget getJvmTarget() {
        return this.jvmTarget;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        CallableMemberDescriptor callableMemberDescriptorFindInterfaceMember;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        CallableMemberDescriptor callableMemberDescriptor = resultingDescriptor instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) resultingDescriptor : null;
        if (callableMemberDescriptor == null || (callableMemberDescriptor instanceof JavaPropertyDescriptor)) {
            return;
        }
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        KtSuperExpression superCallExpression = CallResolverUtilKt.getSuperCallExpression(call);
        if (superCallExpression != null && DescriptorUtils.isInterface(callableMemberDescriptor.getOriginal().getContainingModule())) {
            CallableMemberDescriptor callableMemberDescriptorUnwrapFakeOverride = DescriptorUtils.unwrapFakeOverride(callableMemberDescriptor);
            Intrinsics.checkNotNullExpressionValue(callableMemberDescriptorUnwrapFakeOverride, "unwrapFakeOverride(...)");
            DeclarationDescriptor containingDeclaration = callableMemberDescriptorUnwrapFakeOverride.getContainingModule();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor == null) {
                return;
            }
            JvmDefaultMode jvmDefaultMode = (JvmDefaultMode) context.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getJvmDefaultMode());
            if (DescriptorUtils.isInterface(classDescriptor)) {
                if ((callableMemberDescriptorUnwrapFakeOverride instanceof JavaCallableMemberDescriptor) || JvmDefaultCheckerKt.isCompiledToJvmDefaultWithProperMode(callableMemberDescriptorUnwrapFakeOverride, this.ideService, jvmDefaultMode)) {
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    ClassDescriptor superCallLabelTarget = getSuperCallLabelTarget(context2, superCallExpression);
                    if (superCallLabelTarget == null || !DescriptorUtils.isInterface(superCallLabelTarget) || (callableMemberDescriptorFindInterfaceMember = findInterfaceMember(superCallLabelTarget, superCallExpression, context2)) == null || JvmDefaultCheckerKt.isCompiledToJvmDefaultWithProperMode(callableMemberDescriptorFindInterfaceMember, this.ideService, jvmDefaultMode)) {
                        return;
                    }
                    context.getTrace().report(ErrorsJvm.INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER.on(reportOn));
                }
            }
        }
    }

    private final CallableMemberDescriptor findInterfaceMember(ClassDescriptor descriptorToSearch, final KtSuperExpression startExpression, BindingContext bindingContext) {
        Iterator it = SequencesKt.generateSequence((Function0) new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.InterfaceDefaultMethodCallChecker$findInterfaceMember$parents$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiElement invoke() {
                return startExpression.getLightParent();
            }
        }, (Function1) new Function1<PsiElement, PsiElement>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.InterfaceDefaultMethodCallChecker$findInterfaceMember$parents$2
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(PsiElement it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getLightParent();
            }
        }).iterator();
        PsiElement psiElement = startExpression;
        while (true) {
            if (!it.hasNext()) {
                return null;
            }
            PsiElement psiElement2 = (PsiElement) it.next();
            if ((psiElement2 instanceof KtClassBody) && Intrinsics.areEqual(descriptorToSearch, bindingContext.get(BindingContext.CLASS, ((KtClassBody) psiElement2).getLightParent()))) {
                if (psiElement instanceof KtNamedFunction) {
                    return (CallableMemberDescriptor) bindingContext.get(BindingContext.FUNCTION, psiElement);
                }
                if (!(psiElement instanceof KtProperty)) {
                    return null;
                }
                Object obj = bindingContext.get(BindingContext.VARIABLE, psiElement);
                return obj instanceof PropertyDescriptor ? (PropertyDescriptor) obj : null;
            }
            psiElement = psiElement2;
        }
    }

    private final ClassDescriptor getSuperCallLabelTarget(BindingContext bindingContext, KtSuperExpression expression) {
        KotlinType kotlinType = (KotlinType) bindingContext.get(BindingContext.THIS_TYPE_FOR_SUPER_EXPRESSION, expression);
        if (kotlinType == null) {
            return null;
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 instanceof ClassDescriptor) {
            return (ClassDescriptor) this$0;
        }
        return null;
    }
}
