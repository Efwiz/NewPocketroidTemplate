package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.BuiltInsPackageFragment;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: MissingBuiltInDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/MissingBuiltInDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "diagnosticFor", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "ClassifierUsage", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MissingBuiltInDeclarationChecker implements CallChecker {
    public static final MissingBuiltInDeclarationChecker INSTANCE = new MissingBuiltInDeclarationChecker();

    private MissingBuiltInDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        TypeConstructor constructor;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (((Boolean) context.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getSuppressMissingBuiltinsError())).booleanValue()) {
            return;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Diagnostic diagnosticDiagnosticFor = diagnosticFor(resultingDescriptor, reportOn);
        if (diagnosticDiagnosticFor == null) {
            KotlinType returnType = resultingDescriptor.getReturnType();
            ClassifierDescriptor this$0 = null;
            if (returnType != null) {
                if (CallCheckerKt.isComputingDeferredType(this, returnType)) {
                    returnType = null;
                }
                if (returnType != null && (constructor = returnType.getConstructor()) != null) {
                    this$0 = constructor.getThis$0();
                }
            }
            diagnosticDiagnosticFor = diagnosticFor(this$0, reportOn);
        }
        if (diagnosticDiagnosticFor != null) {
            context.getTrace().report(diagnosticDiagnosticFor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Diagnostic diagnosticFor(DeclarationDescriptor descriptor, PsiElement reportOn) {
        if (descriptor == null) {
            return null;
        }
        ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor = (ClassOrPackageFragmentDescriptor) DescriptorUtils.getParentOfType(descriptor, ClassOrPackageFragmentDescriptor.class);
        if (classOrPackageFragmentDescriptor instanceof ClassDescriptor) {
            PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(descriptor, PackageFragmentDescriptor.class);
            BuiltInsPackageFragment builtInsPackageFragment = packageFragmentDescriptor instanceof BuiltInsPackageFragment ? (BuiltInsPackageFragment) packageFragmentDescriptor : null;
            if (builtInsPackageFragment != null && builtInsPackageFragment.getIsFallback()) {
                return Errors.MISSING_BUILT_IN_DECLARATION.on(reportOn, DescriptorUtilsKt.getFqNameSafe(classOrPackageFragmentDescriptor));
            }
        } else if ((classOrPackageFragmentDescriptor instanceof BuiltInsPackageFragment) && ((BuiltInsPackageFragment) classOrPackageFragmentDescriptor).getIsFallback()) {
            return Errors.MISSING_BUILT_IN_DECLARATION.on(reportOn, DescriptorUtilsKt.getFqNameSafe(descriptor));
        }
        return null;
    }

    /* JADX INFO: compiled from: MissingBuiltInDeclarationChecker.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/MissingBuiltInDeclarationChecker$ClassifierUsage;", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "()V", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClassifierUsage implements ClassifierUsageChecker {
        public static final ClassifierUsage INSTANCE = new ClassifierUsage();

        private ClassifierUsage() {
        }

        @Override // org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker
        public void check(ClassifierDescriptor targetDescriptor, PsiElement element, ClassifierUsageCheckerContext context) {
            Diagnostic diagnosticDiagnosticFor;
            Intrinsics.checkNotNullParameter(targetDescriptor, "targetDescriptor");
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(context, "context");
            if (((Boolean) context.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getSuppressMissingBuiltinsError())).booleanValue() || (diagnosticDiagnosticFor = MissingBuiltInDeclarationChecker.INSTANCE.diagnosticFor(targetDescriptor, element)) == null) {
                return;
            }
            context.getTrace().report(diagnosticDiagnosticFor);
        }
    }
}
