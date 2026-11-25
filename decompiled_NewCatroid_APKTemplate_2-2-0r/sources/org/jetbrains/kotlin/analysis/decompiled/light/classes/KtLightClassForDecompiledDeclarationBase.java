package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtExtensibleLightClass;
import org.jetbrains.kotlin.asJava.elements.KtLightElementBase;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KtLightClassForDecompiledDeclarationBase.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0004\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledDeclarationBase;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "Lorg/jetbrains/kotlin/asJava/classes/KtExtensibleLightClass;", "clsDelegate", "clsParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lcom/intellij/psi/PsiClass;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "getClsDelegate", "()Lcom/intellij/psi/PsiClass;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightClassForDecompiledDeclarationBase extends KtLightElementBase implements PsiClass, KtExtensibleLightClass {
    private final PsiClass clsDelegate;
    private final KtClassOrObject kotlinOrigin;

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public /* bridge */ /* synthetic */ KtElement getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    public final PsiClass getClsDelegate() {
        return this.clsDelegate;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public final KtClassOrObject getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightClassForDecompiledDeclarationBase(PsiClass clsDelegate, PsiElement clsParent, KtClassOrObject ktClassOrObject) {
        super(clsParent);
        Intrinsics.checkNotNullParameter(clsDelegate, "clsDelegate");
        Intrinsics.checkNotNullParameter(clsParent, "clsParent");
        this.clsDelegate = clsDelegate;
        this.kotlinOrigin = ktClassOrObject;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) visitor).visitClass(this);
        } else {
            visitor.visitElement(this);
        }
    }
}
