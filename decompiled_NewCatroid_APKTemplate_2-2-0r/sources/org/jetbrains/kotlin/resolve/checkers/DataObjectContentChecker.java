package org.jetbrains.kotlin.resolve.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: DataObjectContentChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/DataObjectContentChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "isDeclaredInAny", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataObjectContentChecker implements DeclarationChecker {
    public static final DataObjectContentChecker INSTANCE = new DataObjectContentChecker();

    private DataObjectContentChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtDeclaration modifier;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            if (Intrinsics.areEqual(functionDescriptor.getShortName(), OperatorNameConventions.EQUALS) || Intrinsics.areEqual(functionDescriptor.getShortName(), OperatorNameConventions.HASH_CODE)) {
                DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                if (containingDeclaration instanceof ClassDescriptor) {
                    ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
                    if (classDescriptor.isData() && classDescriptor.getKind() == ClassKind.OBJECT) {
                        Set allOverriddenDescriptors = DescriptorUtils.getAllOverriddenDescriptors((CallableDescriptor) descriptor);
                        Intrinsics.checkNotNullExpressionValue(allOverriddenDescriptors, "getAllOverriddenDescriptors(...)");
                        Set set = allOverriddenDescriptors;
                        if ((set instanceof Collection) && set.isEmpty()) {
                            return;
                        }
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            if (isDeclaredInAny((FunctionDescriptor) it.next())) {
                                KtModifierList modifierList = declaration.getModifierList();
                                if (modifierList == null || (modifier = modifierList.getModifier(KtTokens.OVERRIDE_KEYWORD)) == null) {
                                    modifier = declaration;
                                }
                                context.getTrace().report(Errors.DATA_OBJECT_CUSTOM_EQUALS_OR_HASH_CODE.on(modifier));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private final boolean isDeclaredInAny(FunctionDescriptor descriptor) {
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return (containingDeclaration instanceof ClassDescriptor) && KotlinBuiltIns.isAny((ClassDescriptor) containingDeclaration);
    }
}
