package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.stubs.KotlinClassStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtEnumEntry extends KtClass {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtEnumEntry";
        } else if (i != 4) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getSuperTypeListEntries";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtEnumEntry";
        }
        if (i != 2 && i != 3) {
            if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtClassOrObject, org.jetbrains.kotlin.psi.KtClassLikeDeclaration
    public ClassId getClassId() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtEnumEntry(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtEnumEntry(KotlinClassStub kotlinClassStub) {
        super(kotlinClassStub);
        if (kotlinClassStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtClassOrObject, org.jetbrains.kotlin.psi.KtPureClassOrObject
    public List<KtSuperTypeListEntry> getSuperTypeListEntries() {
        KtInitializerList initializerList = getInitializerList();
        if (initializerList == null) {
            List<KtSuperTypeListEntry> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(2);
            }
            return listEmptyList;
        }
        List<KtSuperTypeListEntry> initializers = initializerList.getInitializers();
        if (initializers == null) {
            $$$reportNull$$$0(3);
        }
        return initializers;
    }

    public boolean hasInitializer() {
        return !getSuperTypeListEntries().isEmpty();
    }

    public KtInitializerList getInitializerList() {
        return (KtInitializerList) getStubOrPsiChild(KtStubElementTypes.INITIALIZER_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtClass, org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(4);
        }
        return ktVisitor.visitEnumEntry(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtClassOrObject, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        PsiEnumConstant psiEnumConstant;
        PsiClass containingClass;
        String qualifiedName;
        if (!(psiElement instanceof PsiEnumConstant) || (containingClass = (psiEnumConstant = (PsiEnumConstant) psiElement).getContainingClass()) == null || (qualifiedName = containingClass.getQualifiedName()) == null || psiEnumConstant.get_name() == null || !(qualifiedName + "." + psiEnumConstant.get_name()).equals(getQualifiedName())) {
            return super.isEquivalentTo(psiElement);
        }
        return true;
    }
}
