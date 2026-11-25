package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: FunctionDelegateMemberNameClashChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/FunctionDelegateMemberNameClashChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionDelegateMemberNameClashChecker implements DeclarationChecker {
    public static final FunctionDelegateMemberNameClashChecker INSTANCE = new FunctionDelegateMemberNameClashChecker();

    private FunctionDelegateMemberNameClashChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtDeclaration nameIdentifier;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if ((containingDeclaration instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration).isFun() && callableMemberDescriptor.getExtensionReceiverParameter() == null) {
                List<ValueParameterDescriptor> valueParameters = callableMemberDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                if (valueParameters.isEmpty()) {
                    if (((descriptor instanceof FunctionDescriptor) && Intrinsics.areEqual(((FunctionDescriptor) descriptor).getShortName().asString(), "getFunctionDelegate")) || ((descriptor instanceof PropertyDescriptor) && Intrinsics.areEqual(((PropertyDescriptor) descriptor).getShortName().asString(), "functionDelegate"))) {
                        KtNamedDeclaration ktNamedDeclaration = declaration instanceof KtNamedDeclaration ? (KtNamedDeclaration) declaration : null;
                        if (ktNamedDeclaration == null || (nameIdentifier = ktNamedDeclaration.mo7392getNameIdentifier()) == null) {
                            nameIdentifier = declaration;
                        }
                        context.getTrace().report(ErrorsJvm.FUNCTION_DELEGATE_MEMBER_NAME_CLASH.on(nameIdentifier));
                    }
                }
            }
        }
    }
}
