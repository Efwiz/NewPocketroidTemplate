package org.jetbrains.kotlin.resolve.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: SuspendLimitationsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/SuspendLimitationsChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "UNSUPPORTED_OPERATOR_NAMES", "", "Lorg/jetbrains/kotlin/name/Name;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspendLimitationsChecker implements DeclarationChecker {
    public static final SuspendLimitationsChecker INSTANCE = new SuspendLimitationsChecker();
    private static final Set<Name> UNSUPPORTED_OPERATOR_NAMES = SetsKt.setOf((Object[]) new Name[]{OperatorNameConventions.CONTAINS, OperatorNameConventions.GET, OperatorNameConventions.SET, OperatorNameConventions.PROVIDE_DELEGATE, OperatorNameConventions.GET_VALUE, OperatorNameConventions.SET_VALUE});

    private SuspendLimitationsChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        PsiElement modifier;
        KtModifierList modifierList;
        PsiElement modifier2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            if (functionDescriptor.isSuspend()) {
                if (functionDescriptor.isOperator() && UNSUPPORTED_OPERATOR_NAMES.contains(functionDescriptor.getShortName()) && (modifierList = declaration.getModifierList()) != null && (modifier2 = modifierList.getModifier(KtTokens.OPERATOR_KEYWORD)) != null) {
                    context.getTrace().report(Errors.UNSUPPORTED.on(modifier2, "suspend operator \"" + functionDescriptor.getShortName() + '\"'));
                }
                Annotations annotations = descriptor.getAnnotations();
                if ((annotations instanceof Collection) && ((Collection) annotations).isEmpty()) {
                    return;
                }
                Iterator<AnnotationDescriptor> it = annotations.iterator();
                while (it.hasNext()) {
                    if (SuspendLimitationsCheckerKt.isKotlinTestAnnotation(it.next())) {
                        KtModifierList modifierList2 = declaration.getModifierList();
                        if (modifierList2 == null || (modifier = modifierList2.getModifier(KtTokens.SUSPEND_KEYWORD)) == null) {
                            return;
                        }
                        context.getTrace().report(Errors.UNSUPPORTED_SUSPEND_TEST.on(modifier));
                        return;
                    }
                }
            }
        }
    }
}
