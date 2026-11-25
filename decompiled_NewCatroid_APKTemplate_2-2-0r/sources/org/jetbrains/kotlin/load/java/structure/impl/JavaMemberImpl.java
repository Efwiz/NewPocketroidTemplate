package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JavaMemberImpl<Psi extends PsiMember> extends JavaElementImpl<Psi> implements JavaMember, JavaAnnotationOwnerImpl, JavaModifierListOwnerImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 3 ? 2 : 3];
        if (i != 3) {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaMemberImpl";
        } else {
            objArr[0] = "fqName";
        }
        if (i == 1) {
            objArr[1] = "getVisibility";
        } else if (i == 2) {
            objArr[1] = "getAnnotations";
        } else if (i != 3) {
            objArr[1] = "getName";
        } else {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaMemberImpl";
        }
        if (i == 3) {
            objArr[2] = "findAnnotation";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public final boolean isFromSource() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaElementImpl, org.jetbrains.kotlin.load.java.structure.impl.JavaModifierListOwnerImpl
    public /* bridge */ /* synthetic */ PsiModifierListOwner getPsi() {
        return (PsiModifierListOwner) super.getPsi();
    }

    protected JavaMemberImpl(JavaElementPsiSource<Psi> javaElementPsiSource) {
        super(javaElementPsiSource);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaAnnotationOwnerImpl
    public PsiAnnotationOwner getAnnotationOwnerPsi() {
        return ((PsiMember) getPsi()).mo6095getModifierList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(((PsiMember) getPsi()).getName());
        if (nameIdentifier == null) {
            $$$reportNull$$$0(0);
        }
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMember
    public JavaClass getContainingClass() {
        return new JavaClassImpl((JavaElementPsiSource<PsiClass>) createPsiSource(((PsiMember) getPsi()).mo6092getContainingClass()));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        return JavaElementUtil.isAbstract(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
        return JavaElementUtil.isStatic(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        return JavaElementUtil.isFinal(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        Visibility visibility = JavaElementUtil.getVisibility(this);
        if (visibility == null) {
            $$$reportNull$$$0(1);
        }
        return visibility;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        Collection<JavaAnnotation> regularAndExternalAnnotations = JavaElementUtil.getRegularAndExternalAnnotations(this, getSourceFactory());
        if (regularAndExternalAnnotations == null) {
            $$$reportNull$$$0(2);
        }
        return regularAndExternalAnnotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public JavaAnnotation mo7251findAnnotation(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(3);
        }
        return JavaElementUtil.findAnnotation(this, fqName, getSourceFactory());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        PsiMember psiMember = (PsiMember) getPsi();
        return (psiMember instanceof PsiDocCommentOwner) && ((PsiDocCommentOwner) psiMember).isDeprecated();
    }
}
