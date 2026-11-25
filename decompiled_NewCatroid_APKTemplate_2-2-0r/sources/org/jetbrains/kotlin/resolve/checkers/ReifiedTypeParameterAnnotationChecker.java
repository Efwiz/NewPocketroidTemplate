package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;

/* JADX INFO: compiled from: ReifiedTypeParameterAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001e\u0010\u000b\u001a\u00020\u00042\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ReifiedTypeParameterAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkTypeParameterDescriptorsAreNotReified", "typeParameterDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReifiedTypeParameterAnnotationChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof CallableDescriptor) && !InlineUtil.isInline(descriptor) && !InlineUtil.isPropertyWithAllAccessorsAreInline(descriptor)) {
            List<TypeParameterDescriptor> typeParameters = ((CallableDescriptor) descriptor).getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            checkTypeParameterDescriptorsAreNotReified(typeParameters, context.getTrace());
        }
        if (descriptor instanceof ClassDescriptor) {
            List<TypeParameterDescriptor> declaredTypeParameters = ((ClassDescriptor) descriptor).getDeclaredTypeParameters();
            Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
            checkTypeParameterDescriptorsAreNotReified(declaredTypeParameters, context.getTrace());
        }
    }

    private final void checkTypeParameterDescriptorsAreNotReified(List<? extends TypeParameterDescriptor> typeParameterDescriptors, DiagnosticSink diagnosticHolder) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : typeParameterDescriptors) {
            if (((TypeParameterDescriptor) obj).isReified()) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration((TypeParameterDescriptor) it.next());
            if (!(psiElementDescriptorToDeclaration instanceof KtTypeParameter)) {
                throw new AssertionError("KtTypeParameter expected");
            }
            DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.REIFIED_TYPE_PARAMETER_NO_INLINE;
            KtModifierList modifierList = ((KtTypeParameter) psiElementDescriptorToDeclaration).getModifierList();
            Intrinsics.checkNotNull(modifierList);
            PsiElement modifier = modifierList.getModifier(KtTokens.REIFIED_KEYWORD);
            Intrinsics.checkNotNull(modifier);
            diagnosticHolder.report(diagnosticFactory0.on(modifier));
        }
    }
}
