package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;

/* JADX INFO: compiled from: KtLightMemberImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u00032\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u0017\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0010\u001a\u00020\bH\u0016J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H&J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0016H&J\b\u0010\u001c\u001a\u00020\u001aH&J\u0012\u0010\u001d\u001a\u00020\u001a2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u001aH\u0016J\b\u0010!\u001a\u00020\u0016H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightMemberImpl;", "D", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMember;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMember;", "lightMemberOrigin", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "(Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;)V", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getLightMemberOrigin", "()Lorg/jetbrains/kotlin/asJava/builder/LightMemberOriginForDeclaration;", "getContainingClass", "getDocComment", "Lorg/jetbrains/kotlin/com/intellij/psi/javadoc/PsiDocComment;", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "hasModifierProperty", "", "name", "isDeprecated", "isEquivalentTo", "another", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isValid", "toString", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightMemberImpl<D extends PsiMember> extends KtLightElementBase implements PsiMember, KtLightMember<D> {
    private final KtLightClass containingClass;
    private final LightMemberOriginForDeclaration lightMemberOrigin;

    /* JADX INFO: renamed from: getDocComment */
    public abstract PsiDocComment mo6091getDocComment();

    /* JADX INFO: renamed from: getModifierList */
    public abstract PsiModifierList mo6095getModifierList();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public abstract String get_name();

    public abstract boolean hasModifierProperty(String name2);

    public abstract boolean isDeprecated();

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightMember
    public LightMemberOriginForDeclaration getLightMemberOrigin() {
        return this.lightMemberOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightMemberImpl(LightMemberOriginForDeclaration lightMemberOriginForDeclaration, KtLightClass containingClass) {
        super(containingClass);
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        this.lightMemberOrigin = lightMemberOriginForDeclaration;
        this.containingClass = containingClass;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getClass().getSimpleName() + ':' + get_name();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public KtLightClass getContainingClass() {
        return this.containingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        KtLightMemberImpl<D> ktLightMemberImpl = this;
        KtDeclaration kotlinOrigin = getKotlinOrigin();
        return new KtLightIdentifier(ktLightMemberImpl, kotlinOrigin instanceof KtNamedDeclaration ? (KtNamedDeclaration) kotlinOrigin : null, null, 4, null);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtDeclaration getKotlinOrigin() {
        LightMemberOriginForDeclaration lightMemberOrigin = getLightMemberOrigin();
        if (lightMemberOrigin != null) {
            return lightMemberOrigin.getOriginalElement();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        LightMemberOriginForDeclaration lightMemberOrigin;
        return getLightOwner().isValid() && ((lightMemberOrigin = getLightMemberOrigin()) == null || lightMemberOrigin.isValid());
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        LightMemberOriginForDeclaration lightMemberOrigin;
        if (Intrinsics.areEqual(this, another)) {
            return true;
        }
        LightMemberOriginForDeclaration lightMemberOrigin2 = getLightMemberOrigin();
        if (lightMemberOrigin2 == null || !lightMemberOrigin2.isEquivalentTo(another)) {
            return (another instanceof KtLightMember) && (lightMemberOrigin = getLightMemberOrigin()) != null && lightMemberOrigin.isEquivalentTo(((KtLightMember) another).getLightMemberOrigin());
        }
        return true;
    }
}
