package org.jetbrains.kotlin.cfg;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtClassInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtScriptInitializer;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;

/* JADX INFO: compiled from: cfgContainingDeclarationUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u0004\u0018\u00010\u00012\u0006\u0010\u0007\u001a\u00020\b\u001a\f\u0010\t\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\n"}, d2 = {"containingDeclarationForPseudocode", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/psi/KtElement;", "getContainingDeclarationForPseudocode", "(Lorg/jetbrains/kotlin/psi/KtElement;)Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getDeclarationDescriptorIncludingConstructors", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getElementParentDeclaration", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CfgContainingDeclarationUtilsKt {
    public static final KtDeclaration getContainingDeclarationForPseudocode(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        KtElement ktElement2 = ktElement;
        KtDeclaration ktDeclaration = (KtDeclaration) PsiTreeUtil.getParentOfType(ktElement2, KtDeclarationWithBody.class, KtClassOrObject.class, KtScript.class);
        return ktDeclaration == null ? (KtDeclaration) PsiTreeUtil.getParentOfType(ktElement2, KtProperty.class, false) : ktDeclaration;
    }

    public static final KtDeclaration getElementParentDeclaration(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        return (KtDeclaration) PsiTreeUtil.getParentOfType(ktElement, KtDeclarationWithBody.class, KtClassOrObject.class, KtClassInitializer.class, KtScriptInitializer.class);
    }

    public static final DeclarationDescriptor getDeclarationDescriptorIncludingConstructors(KtDeclaration ktDeclaration, BindingContext context) {
        LexicalScope scopeForInitializerResolution;
        KtClassOrObject containingDeclaration;
        Intrinsics.checkNotNullParameter(context, "context");
        ReadOnlySlice<PsiElement, DeclarationDescriptor> readOnlySlice = BindingContext.DECLARATION_TO_DESCRIPTOR;
        boolean z = ktDeclaration instanceof KtClassInitializer;
        KtClassInitializer ktClassInitializer = z ? (KtClassInitializer) ktDeclaration : null;
        if (ktClassInitializer != null && (containingDeclaration = ktClassInitializer.getContainingDeclaration()) != null) {
            ktDeclaration = containingDeclaration;
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) context.get(readOnlySlice, ktDeclaration);
        if (!(declarationDescriptor instanceof ClassDescriptor) || !z) {
            return declarationDescriptor;
        }
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = ((ClassDescriptor) declarationDescriptor).mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor != null) {
            return classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
        }
        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes = declarationDescriptor instanceof ClassDescriptorWithResolutionScopes ? (ClassDescriptorWithResolutionScopes) declarationDescriptor : null;
        if (classDescriptorWithResolutionScopes == null || (scopeForInitializerResolution = classDescriptorWithResolutionScopes.getScopeForInitializerResolution()) == null) {
            return null;
        }
        return scopeForInitializerResolution.getOwnerDescriptor();
    }
}
