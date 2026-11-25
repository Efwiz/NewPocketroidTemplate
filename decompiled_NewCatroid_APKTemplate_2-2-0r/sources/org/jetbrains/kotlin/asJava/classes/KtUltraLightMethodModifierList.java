package org.jetbrains.kotlin.asJava.classes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin;
import org.jetbrains.kotlin.asJava.elements.KtLightMember;
import org.jetbrains.kotlin.asJava.elements.KtUltraLightModifierList;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: ultraLightMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\u0000H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J*\u0010\u0012\u001a\u0004\u0018\u0001H\u0013\"\u0004\b\u0000\u0010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u0002H\u00130\u0015H\u0082\b¢\u0006\u0002\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\rH\u0016J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\rH\u0016J\b\u0010\u001c\u001a\u00020\u0019H\u0002R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethodModifierList;", "Lorg/jetbrains/kotlin/asJava/elements/KtUltraLightModifierList;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "owner", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "(Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;Lcom/intellij/psi/PsiMethod;)V", "getDelegate", "()Lcom/intellij/psi/PsiMethod;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getText", "", "getTextOffset", "", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTextVariantFromModifierListOfPropertyAccessorIfNeeded", "R", "retriever", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "hasExplicitModifier", "", "name", "hasModifierProperty", "isImplementationInInterface", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KtUltraLightMethodModifierList extends KtUltraLightModifierList<KtUltraLightMethod> {
    private final PsiMethod delegate;

    public final PsiMethod getDelegate() {
        return this.delegate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightMethodModifierList(KtUltraLightSupport support, KtUltraLightMethod owner, PsiMethod delegate) {
        super(owner, support);
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (Intrinsics.areEqual(name2, "abstract") && isImplementationInInterface()) {
            return false;
        }
        if (Intrinsics.areEqual(name2, "default") && isImplementationInInterface() && !hasModifierProperty("static")) {
            return true;
        }
        if (Intrinsics.areEqual(name2, "final")) {
            KtLightClass containingClass = ((KtUltraLightMethod) getOwner()).getContainingClass();
            if (!(containingClass instanceof KtLightClassForSourceDeclaration)) {
                containingClass = null;
            }
            KtLightClassForSourceDeclaration ktLightClassForSourceDeclaration = (KtLightClassForSourceDeclaration) containingClass;
            if (ktLightClassForSourceDeclaration != null && KtLightClassForSourceDeclarationKt.isPossiblyAffectedByAllOpen(ktLightClassForSourceDeclaration)) {
                return this.delegate.hasModifierProperty(name2);
            }
        }
        return this.delegate.hasModifierProperty(name2);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasExplicitModifier(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (Intrinsics.areEqual(name2, "default")) {
            return false;
        }
        return super.hasExplicitModifier(name2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isImplementationInInterface() {
        KtDeclaration kotlinOrigin;
        return ((KtUltraLightMethod) getOwner()).getContainingClass().isInterface() && (kotlinOrigin = ((KtUltraLightMethod) getOwner()).getKtDeclaration()) != null && KtPsiUtilKt.hasBody(kotlinOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightMethodModifierList copy() {
        return new KtUltraLightMethodModifierList(getSupport(), (KtUltraLightMethod) getOwner(), this.delegate);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        KtModifierList modifierList;
        LightMemberOrigin lightMemberOrigin;
        T owner = getOwner();
        String text = null;
        KtLightMember ktLightMember = owner instanceof KtLightMember ? (KtLightMember) owner : null;
        KtDeclaration auxiliaryOriginalElement = (ktLightMember == null || (lightMemberOrigin = ktLightMember.getLightMemberOrigin()) == null) ? null : lightMemberOrigin.getAuxiliaryOriginalElement();
        KtPropertyAccessor ktPropertyAccessor = auxiliaryOriginalElement instanceof KtPropertyAccessor ? (KtPropertyAccessor) auxiliaryOriginalElement : null;
        if (ktPropertyAccessor != null && (modifierList = ktPropertyAccessor.getModifierList()) != null) {
            Intrinsics.checkNotNull(modifierList);
            text = modifierList.getText();
        }
        return text == null ? super.getText() : text;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        KtModifierList modifierList;
        LightMemberOrigin lightMemberOrigin;
        T owner = getOwner();
        Integer numValueOf = null;
        KtLightMember ktLightMember = owner instanceof KtLightMember ? (KtLightMember) owner : null;
        KtDeclaration auxiliaryOriginalElement = (ktLightMember == null || (lightMemberOrigin = ktLightMember.getLightMemberOrigin()) == null) ? null : lightMemberOrigin.getAuxiliaryOriginalElement();
        KtPropertyAccessor ktPropertyAccessor = auxiliaryOriginalElement instanceof KtPropertyAccessor ? (KtPropertyAccessor) auxiliaryOriginalElement : null;
        if (ktPropertyAccessor != null && (modifierList = ktPropertyAccessor.getModifierList()) != null) {
            Intrinsics.checkNotNull(modifierList);
            numValueOf = Integer.valueOf(modifierList.getTextOffset());
        }
        return numValueOf != null ? numValueOf.intValue() : super.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        KtModifierList modifierList;
        LightMemberOrigin lightMemberOrigin;
        T owner = getOwner();
        TextRange textRange = null;
        KtLightMember ktLightMember = owner instanceof KtLightMember ? (KtLightMember) owner : null;
        KtDeclaration auxiliaryOriginalElement = (ktLightMember == null || (lightMemberOrigin = ktLightMember.getLightMemberOrigin()) == null) ? null : lightMemberOrigin.getAuxiliaryOriginalElement();
        KtPropertyAccessor ktPropertyAccessor = auxiliaryOriginalElement instanceof KtPropertyAccessor ? (KtPropertyAccessor) auxiliaryOriginalElement : null;
        if (ktPropertyAccessor != null && (modifierList = ktPropertyAccessor.getModifierList()) != null) {
            Intrinsics.checkNotNull(modifierList);
            textRange = modifierList.getTextRange();
        }
        if (textRange != null) {
            return textRange;
        }
        TextRange textRange2 = super.getTextRange();
        Intrinsics.checkNotNullExpressionValue(textRange2, "getTextRange(...)");
        return textRange2;
    }
}
