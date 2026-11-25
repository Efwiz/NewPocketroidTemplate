package org.jetbrains.kotlin.resolve.checkers;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: MissingDependencySupertypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u000f\u0010B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/MissingDependencySupertypeChecker;", "", "()V", "checkSupertypes", "", "declaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "classifierType", "Lorg/jetbrains/kotlin/types/KotlinType;", "ForCalls", "ForDeclarations", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MissingDependencySupertypeChecker {
    public static final MissingDependencySupertypeChecker INSTANCE = new MissingDependencySupertypeChecker();

    /* JADX INFO: compiled from: MissingDependencySupertypeChecker.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/MissingDependencySupertypeChecker$ForDeclarations;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ForDeclarations implements DeclarationChecker {
        public static final ForDeclarations INSTANCE = new ForDeclarations();

        private ForDeclarations() {
        }

        @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
        public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(context, "context");
            BindingTrace trace = context.getTrace();
            if (descriptor instanceof ClassDescriptor) {
                MissingDependencySupertypeChecker.INSTANCE.checkSupertypes(descriptor, declaration, trace, context.getMissingSupertypesResolver());
            }
            if (declaration instanceof KtTypeParameterListOwner) {
                for (KtTypeParameter ktTypeParameter : ((KtTypeParameterListOwner) declaration).getTypeParameters()) {
                    TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) trace.getContext().get(BindingContext.TYPE_PARAMETER, ktTypeParameter);
                    if (typeParameterDescriptor != null) {
                        for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
                            MissingDependencySupertypeChecker missingDependencySupertypeChecker = MissingDependencySupertypeChecker.INSTANCE;
                            Intrinsics.checkNotNull(kotlinType);
                            Intrinsics.checkNotNull(ktTypeParameter);
                            missingDependencySupertypeChecker.checkSupertypes(kotlinType, ktTypeParameter, trace, context.getMissingSupertypesResolver());
                        }
                    }
                }
            }
        }
    }

    private MissingDependencySupertypeChecker() {
    }

    /* JADX INFO: compiled from: MissingDependencySupertypeChecker.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/MissingDependencySupertypeChecker$ForCalls;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "declaration", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDeclaration", "(Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;)Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ForCalls implements CallChecker {
        public static final ForCalls INSTANCE = new ForCalls();

        private ForCalls() {
        }

        @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
        public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            Intrinsics.checkNotNullParameter(reportOn, "reportOn");
            Intrinsics.checkNotNullParameter(context, "context");
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            MissingDependencySupertypeChecker missingDependencySupertypeChecker = MissingDependencySupertypeChecker.INSTANCE;
            ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = resultingDescriptor.mo6753getDispatchReceiverParameter();
            boolean zCheckSupertypes = missingDependencySupertypeChecker.checkSupertypes(receiverParameterDescriptorMo6753getDispatchReceiverParameter != null ? getDeclaration(receiverParameterDescriptorMo6753getDispatchReceiverParameter) : null, reportOn, context.getTrace(), context.getMissingSupertypesResolver());
            boolean zBooleanValue = ((Boolean) context.getLanguageVersionSettings().getFlag(AnalysisFlags.getExtendedCompilerChecks())).booleanValue();
            boolean z = (resultingDescriptor instanceof ConstructorDescriptor) || (resultingDescriptor instanceof FakeCallableDescriptorForObject);
            if (zBooleanValue || !(z || zCheckSupertypes)) {
                MissingDependencySupertypeChecker.INSTANCE.checkSupertypes(resultingDescriptor.getContainingModule(), reportOn, context.getTrace(), context.getMissingSupertypesResolver());
                MissingDependencySupertypeChecker missingDependencySupertypeChecker2 = MissingDependencySupertypeChecker.INSTANCE;
                ReceiverParameterDescriptor extensionReceiverParameter = resultingDescriptor.getExtensionReceiverParameter();
                missingDependencySupertypeChecker2.checkSupertypes(extensionReceiverParameter != null ? getDeclaration(extensionReceiverParameter) : null, reportOn, context.getTrace(), context.getMissingSupertypesResolver());
            }
        }

        private final ClassifierDescriptor getDeclaration(ReceiverParameterDescriptor receiverParameterDescriptor) {
            return receiverParameterDescriptor.getValue().getType().getConstructor().getThis$0();
        }
    }

    public final boolean checkSupertypes(KotlinType classifierType, PsiElement reportOn, BindingTrace trace, MissingSupertypesResolver missingSupertypesResolver) {
        Intrinsics.checkNotNullParameter(classifierType, "classifierType");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        return checkSupertypes(classifierType.getConstructor().getThis$0(), reportOn, trace, missingSupertypesResolver);
    }

    public final boolean checkSupertypes(DeclarationDescriptor declaration, PsiElement reportOn, BindingTrace trace, MissingSupertypesResolver missingSupertypesResolver) {
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        if (!(declaration instanceof ClassifierDescriptor)) {
            return false;
        }
        Set<ClassifierDescriptor> missingSuperClassifiers = missingSupertypesResolver.getMissingSuperClassifiers((ClassifierDescriptor) declaration);
        Iterator<ClassifierDescriptor> it = missingSuperClassifiers.iterator();
        while (it.hasNext()) {
            trace.report(Errors.MISSING_DEPENDENCY_SUPERCLASS.on(reportOn, DescriptorUtilsKt.getFqNameSafe(it.next()), DescriptorUtilsKt.getFqNameSafe(declaration)));
        }
        return !missingSuperClassifiers.isEmpty();
    }
}
