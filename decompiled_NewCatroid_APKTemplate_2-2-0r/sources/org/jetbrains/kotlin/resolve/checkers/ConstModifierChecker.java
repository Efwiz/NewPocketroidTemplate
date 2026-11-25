package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstUtilKt;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;

/* JADX INFO: compiled from: ConstModifierChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ConstModifierChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "canBeConst", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "constModifierPsiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "check", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkCanBeConst", "Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstModifierChecker implements DeclarationChecker {
    public static final ConstModifierChecker INSTANCE = new ConstModifierChecker();

    private ConstModifierChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof VariableDescriptor) && declaration.hasModifier(KtTokens.CONST_KEYWORD)) {
            KtModifierList modifierList = declaration.getModifierList();
            Intrinsics.checkNotNull(modifierList);
            PsiElement modifier = modifierList.getModifier(KtTokens.CONST_KEYWORD);
            Intrinsics.checkNotNull(modifier);
            Diagnostic diagnostic = checkCanBeConst(declaration, modifier, (VariableDescriptor) descriptor).getDiagnostic();
            if (diagnostic != null) {
                context.getTrace().report(diagnostic);
            }
        }
    }

    public final boolean canBeConst(KtDeclaration declaration, PsiElement constModifierPsiElement, VariableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(constModifierPsiElement, "constModifierPsiElement");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return checkCanBeConst(declaration, constModifierPsiElement, descriptor).getCanBeConst();
    }

    private final ConstApplicability checkCanBeConst(KtDeclaration declaration, PsiElement constModifierPsiElement, VariableDescriptor descriptor) {
        if (descriptor.isVar()) {
            Diagnostic diagnosticOn = Errors.WRONG_MODIFIER_TARGET.on(constModifierPsiElement, KtTokens.CONST_KEYWORD, "vars");
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            return ConstModifierCheckerKt.nonApplicable(diagnosticOn);
        }
        DeclarationDescriptor containingDeclaration = descriptor.getDeclarationDescriptor();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if ((containingDeclaration instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration).getKind() != ClassKind.OBJECT) {
            Diagnostic diagnosticOn2 = Errors.CONST_VAL_NOT_TOP_LEVEL_OR_OBJECT.on(constModifierPsiElement);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
            return ConstModifierCheckerKt.nonApplicable(diagnosticOn2);
        }
        if (!(declaration instanceof KtProperty) || !(descriptor instanceof PropertyDescriptor)) {
            return new ConstApplicability.NonApplicable(null, 1, null);
        }
        KtProperty ktProperty = (KtProperty) declaration;
        if (ktProperty.hasDelegate()) {
            DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.CONST_VAL_WITH_DELEGATE;
            KtPropertyDelegate delegate = ktProperty.getDelegate();
            Intrinsics.checkNotNull(delegate);
            Diagnostic diagnosticOn3 = diagnosticFactory0.on(delegate);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
            return ConstModifierCheckerKt.nonApplicable(diagnosticOn3);
        }
        KtPropertyAccessor getter = ktProperty.getGetter();
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
        PropertyGetterDescriptor getter2 = propertyDescriptor.getGetter();
        Intrinsics.checkNotNull(getter2);
        if (!getter2.isDefault() && getter != null) {
            Diagnostic diagnosticOn4 = Errors.CONST_VAL_WITH_GETTER.on(getter);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
            return ConstModifierCheckerKt.nonApplicable(diagnosticOn4);
        }
        KotlinType type = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if (KotlinTypeKt.isError(type)) {
            return new ConstApplicability.NonApplicable(null, 1, null);
        }
        KotlinType type2 = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        if (!ConstUtilKt.canBeUsedForConstVal(type2)) {
            Diagnostic diagnosticOn5 = Errors.TYPE_CANT_BE_USED_FOR_CONST_VAL.on(constModifierPsiElement, propertyDescriptor.getType());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn5, "on(...)");
            return ConstModifierCheckerKt.nonApplicable(diagnosticOn5);
        }
        if (ktProperty.getInitializer() == null) {
            Diagnostic diagnosticOn6 = Errors.CONST_VAL_WITHOUT_INITIALIZER.on(constModifierPsiElement);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn6, "on(...)");
            return ConstModifierCheckerKt.nonApplicable(diagnosticOn6);
        }
        if (propertyDescriptor.mo6754getCompileTimeInitializer() == null) {
            DiagnosticFactory0<KtExpression> diagnosticFactory1 = Errors.CONST_VAL_WITH_NON_CONST_INITIALIZER;
            KtExpression initializer = ktProperty.getInitializer();
            Intrinsics.checkNotNull(initializer);
            Diagnostic diagnosticOn7 = diagnosticFactory1.on(initializer);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn7, "on(...)");
            return ConstModifierCheckerKt.nonApplicable(diagnosticOn7);
        }
        return ConstApplicability.Applicable.INSTANCE;
    }
}
