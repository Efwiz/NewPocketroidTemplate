package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: ultraLightClassForLocalDeclaration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u0000H\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\bH\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u001d\u0010\u0007\u001a\u0004\u0018\u00010\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForLocalDeclaration;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)V", "_parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "get_parent", "()Lcom/intellij/psi/PsiElement;", "_parent$delegate", "Lkotlin/Lazy;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getParent", "getQualifiedName", "", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtUltraLightClassForLocalDeclaration extends KtUltraLightClass {

    /* JADX INFO: renamed from: _parent$delegate, reason: from kotlin metadata */
    private final Lazy _parent;

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightClassForLocalDeclaration(final KtClassOrObject classOrObject, KtUltraLightSupport support) {
        super(classOrObject, support);
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(support, "support");
        this._parent = ImplUtilsKt.lazyPub(new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForLocalDeclaration$_parent$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiElement invoke() {
                return LightClassPlatformUtilsKt.getParentForLocalDeclaration(classOrObject);
            }
        });
    }

    private final PsiElement get_parent() {
        return (PsiElement) this._parent.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtUltraLightClassForLocalDeclaration copy() {
        PsiElement psiElementCopy = getClassOrObject().copy();
        Intrinsics.checkNotNull(psiElementCopy, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
        return new KtUltraLightClassForLocalDeclaration((KtClassOrObject) psiElementCopy, getSupport());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightClass, org.jetbrains.kotlin.asJava.classes.KtLightClassImpl, org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return get_parent();
    }
}
