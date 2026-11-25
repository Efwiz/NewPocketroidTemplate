package org.jetbrains.kotlin.resolve.checkers;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: SuspendFunctionAsSupertypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/SuspendFunctionAsSupertypeChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspendFunctionAsSupertypeChecker implements DeclarationChecker {
    public static final SuspendFunctionAsSupertypeChecker INSTANCE = new SuspendFunctionAsSupertypeChecker();

    private SuspendFunctionAsSupertypeChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtSuperTypeList superTypeList;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.SuspendFunctionAsSupertype) && (descriptor instanceof ClassDescriptor)) {
            Sequence sequenceFilter = SequencesKt.filter(DescriptorUtilsKt.getAllSuperClassifiers((ClassifierDescriptor) descriptor), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.checkers.SuspendFunctionAsSupertypeChecker$check$$inlined$filterIsInstance$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof FunctionClassDescriptor);
                }
            });
            Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            List<FunctionClassDescriptor> list = SequencesKt.toList(sequenceFilter);
            boolean z = list instanceof Collection;
            if (z && list.isEmpty()) {
                return;
            }
            for (FunctionClassDescriptor functionClassDescriptor : list) {
                if (Intrinsics.areEqual(functionClassDescriptor.getFunctionTypeKind(), FunctionTypeKind.SuspendFunction.INSTANCE) || Intrinsics.areEqual(functionClassDescriptor.getFunctionTypeKind(), FunctionTypeKind.KSuspendFunction.INSTANCE)) {
                    if (z && list.isEmpty()) {
                        return;
                    }
                    for (FunctionClassDescriptor functionClassDescriptor2 : list) {
                        if (Intrinsics.areEqual(functionClassDescriptor2.getFunctionTypeKind(), FunctionTypeKind.Function.INSTANCE) || Intrinsics.areEqual(functionClassDescriptor2.getFunctionTypeKind(), FunctionTypeKind.KFunction.INSTANCE)) {
                            KtClassOrObject ktClassOrObject = declaration instanceof KtClassOrObject ? (KtClassOrObject) declaration : null;
                            context.getTrace().report(Errors.MIXING_SUSPEND_AND_NON_SUSPEND_SUPERTYPES.on((ktClassOrObject == null || (superTypeList = ktClassOrObject.getSuperTypeList()) == null) ? declaration : superTypeList));
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }
}
