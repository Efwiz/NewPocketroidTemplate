package org.jetbrains.kotlin.resolve.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: MissingDependencyClassChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0015B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u0010\u0010\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001c\u0010\u0012\u001a\u0004\u0018\u00010\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/MissingDependencyClassChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "collectDiagnostics", "", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "diagnosticFor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "incompatibilityDiagnosticFor", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "ClassifierUsage", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MissingDependencyClassChecker implements CallChecker {
    public static final MissingDependencyClassChecker INSTANCE = new MissingDependencyClassChecker();

    private MissingDependencyClassChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNull(resultingDescriptor);
        Iterator<Diagnostic> it = collectDiagnostics(reportOn, resultingDescriptor).iterator();
        while (it.hasNext()) {
            context.getTrace().report(it.next());
        }
        DeserializedMemberDescriptor deserializedMemberDescriptor = resultingDescriptor instanceof DeserializedMemberDescriptor ? (DeserializedMemberDescriptor) resultingDescriptor : null;
        Diagnostic diagnosticIncompatibilityDiagnosticFor = incompatibilityDiagnosticFor(deserializedMemberDescriptor != null ? deserializedMemberDescriptor.getContainerSource() : null, reportOn);
        if (diagnosticIncompatibilityDiagnosticFor != null) {
            context.getTrace().report(diagnosticIncompatibilityDiagnosticFor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Diagnostic diagnosticFor(ClassifierDescriptor descriptor, PsiElement reportOn) {
        if (descriptor instanceof NotFoundClasses.MockClassDescriptor) {
            return Errors.MISSING_DEPENDENCY_CLASS.on(reportOn, DescriptorUtilsKt.getFqNameSafe(descriptor));
        }
        return incompatibilityDiagnosticFor(descriptor.getSourceElement(), reportOn);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Diagnostic incompatibilityDiagnosticFor(SourceElement source, PsiElement reportOn) {
        if (!(source instanceof DeserializedContainerSource)) {
            return null;
        }
        DeserializedContainerSource deserializedContainerSource = (DeserializedContainerSource) source;
        IncompatibleVersionErrorData<?> incompatibleVersionErrorDataMo6784getIncompatibility = deserializedContainerSource.mo6784getIncompatibility();
        if (incompatibleVersionErrorDataMo6784getIncompatibility != null) {
            return Errors.INCOMPATIBLE_CLASS.on(reportOn, deserializedContainerSource.getPresentableString(), incompatibleVersionErrorDataMo6784getIncompatibility);
        }
        if (deserializedContainerSource.getIsPreReleaseInvisible()) {
            return Errors.PRE_RELEASE_CLASS.on(reportOn, deserializedContainerSource.getPresentableString());
        }
        if (deserializedContainerSource.getAbiStability() == DeserializedContainerAbiStability.FIR_UNSTABLE) {
            return Errors.FIR_COMPILED_CLASS.on(reportOn, deserializedContainerSource.getPresentableString());
        }
        if (deserializedContainerSource.getAbiStability() == DeserializedContainerAbiStability.IR_UNSTABLE) {
            return Errors.IR_WITH_UNSTABLE_ABI_COMPILED_CLASS.on(reportOn, deserializedContainerSource.getPresentableString());
        }
        return null;
    }

    private final Set<Diagnostic> collectDiagnostics(PsiElement reportOn, CallableDescriptor descriptor) {
        ReceiverValue value;
        KotlinType type;
        LinkedHashSet linkedHashSetNewLinkedHashSetWithExpectedSize = CollectionsKt.newLinkedHashSetWithExpectedSize(1);
        KotlinType returnType = descriptor.getReturnType();
        if (returnType != null) {
            collectDiagnostics$consider$2(reportOn, linkedHashSetNewLinkedHashSetWithExpectedSize, returnType);
        }
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null && (value = extensionReceiverParameter.getValue()) != null && (type = value.getType()) != null) {
            collectDiagnostics$consider$2(reportOn, linkedHashSetNewLinkedHashSetWithExpectedSize, type);
        }
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            KotlinType type2 = ((ValueParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            collectDiagnostics$consider$2(reportOn, linkedHashSetNewLinkedHashSetWithExpectedSize, type2);
        }
        return linkedHashSetNewLinkedHashSetWithExpectedSize;
    }

    private static final void collectDiagnostics$consider(PsiElement psiElement, Set<Diagnostic> set, ClassDescriptor classDescriptor) {
        Diagnostic diagnosticDiagnosticFor = INSTANCE.diagnosticFor(classDescriptor, psiElement);
        if (diagnosticDiagnosticFor != null) {
            set.add(diagnosticDiagnosticFor);
            return;
        }
        DeclarationDescriptor declarationDescriptor = classDescriptor.getContainingModule();
        ClassDescriptor classDescriptor2 = declarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptor : null;
        if (classDescriptor2 != null) {
            collectDiagnostics$consider(psiElement, set, classDescriptor2);
        }
    }

    private static final void collectDiagnostics$consider$2(PsiElement psiElement, Set<Diagnostic> set, KotlinType kotlinType) {
        if (CallCheckerKt.isComputingDeferredType(INSTANCE, kotlinType)) {
            return;
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor != null) {
            collectDiagnostics$consider(psiElement, set, classDescriptor);
        }
    }

    /* JADX INFO: compiled from: MissingDependencyClassChecker.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/MissingDependencyClassChecker$ClassifierUsage;", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "()V", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClassifierUsage implements ClassifierUsageChecker {
        public static final ClassifierUsage INSTANCE = new ClassifierUsage();

        private ClassifierUsage() {
        }

        @Override // org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker
        public void check(ClassifierDescriptor targetDescriptor, PsiElement element, ClassifierUsageCheckerContext context) {
            Intrinsics.checkNotNullParameter(targetDescriptor, "targetDescriptor");
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(context, "context");
            Diagnostic diagnosticDiagnosticFor = MissingDependencyClassChecker.INSTANCE.diagnosticFor(targetDescriptor, element);
            if (diagnosticDiagnosticFor != null) {
                context.getTrace().report(diagnosticDiagnosticFor);
            }
            DeserializedMemberDescriptor deserializedMemberDescriptor = targetDescriptor instanceof DeserializedMemberDescriptor ? (DeserializedMemberDescriptor) targetDescriptor : null;
            Diagnostic diagnosticIncompatibilityDiagnosticFor = MissingDependencyClassChecker.INSTANCE.incompatibilityDiagnosticFor(deserializedMemberDescriptor != null ? deserializedMemberDescriptor.getContainerSource() : null, element);
            if (diagnosticIncompatibilityDiagnosticFor != null) {
                context.getTrace().report(diagnosticIncompatibilityDiagnosticFor);
            }
        }
    }
}
