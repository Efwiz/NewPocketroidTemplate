package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiSuperExpressionImpl extends ExpressionPsiElement implements PsiSuperExpression, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiSuperExpressionImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiSuperExpressionImpl";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiSuperExpressionImpl() {
        super(SUPER_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedExpression
    public PsiJavaCodeReferenceElement getQualifier() {
        return (PsiJavaCodeReferenceElement) findChildByRoleAsPsiElement(54);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiType superType;
        PsiJavaCodeReferenceElement qualifier = getQualifier();
        if (qualifier != null) {
            PsiElement psiElementResolve = qualifier.resolve();
            if (psiElementResolve instanceof PsiClass) {
                return getSuperType((PsiClass) psiElementResolve, PsiUtil.isLanguageLevel8OrHigher(this));
            }
            return null;
        }
        PsiElement context = getContext();
        while (context != null) {
            if (context instanceof PsiClass) {
                return getSuperType((PsiClass) context, false);
            }
            if ((context instanceof PsiExpressionList) && (context.getLightParent() instanceof PsiAnonymousClass)) {
                context = context.getLightParent();
            } else if ((context instanceof JavaCodeFragment) && (superType = ((JavaCodeFragment) context).getSuperType()) != null) {
                return superType;
            }
            context = context.getContext();
        }
        return null;
    }

    private PsiType getSuperType(PsiClass psiClass, boolean z) {
        PsiClassType[] implementsListTypes;
        if (CommonClassNames.JAVA_LANG_OBJECT.equals(psiClass.getQualifiedName())) {
            return null;
        }
        PsiClass psiClass2 = z ? (PsiClass) PsiTreeUtil.getContextOfType(this, PsiClass.class) : null;
        if (psiClass2 != null) {
            if (psiClass2.isInterface()) {
                implementsListTypes = psiClass2.getExtendsListTypes();
            } else if (psiClass2 instanceof PsiAnonymousClass) {
                PsiAnonymousClass psiAnonymousClass = (PsiAnonymousClass) psiClass2;
                if (PsiTreeUtil.isAncestor(psiAnonymousClass.getArgumentList(), this, true)) {
                    PsiClass psiClass3 = (PsiClass) PsiTreeUtil.getContextOfType((PsiElement) psiClass2, PsiClass.class, true);
                    implementsListTypes = psiClass3 != null ? psiClass3.getSuperTypes() : PsiClassType.EMPTY_ARRAY;
                } else {
                    implementsListTypes = new PsiClassType[]{psiAnonymousClass.getBaseClassType()};
                }
            } else {
                implementsListTypes = psiClass2.getImplementsListTypes();
            }
            for (PsiClassType psiClassType : implementsListTypes) {
                PsiClass psiClassResolve = psiClassType.resolve();
                if (psiClassResolve != null && psiClassResolve.isInterface() && psiClass.equals(psiClassResolve)) {
                    return psiClassType;
                }
            }
        }
        if (psiClass.isInterface()) {
            return PsiType.getJavaLangObject(getManager(), getResolveScope());
        }
        if (psiClass instanceof PsiAnonymousClass) {
            PsiClassType baseClassType = ((PsiAnonymousClass) psiClass).getBaseClassType();
            PsiClass psiClassResolve2 = baseClassType.resolve();
            return (psiClassResolve2 == null || psiClassResolve2.isInterface()) ? PsiType.getJavaLangObject(getManager(), getResolveScope()) : baseClassType;
        }
        PsiClassType[] extendsListTypes = psiClass.getExtendsListTypes();
        return extendsListTypes.length == 0 ? PsiType.getJavaLangObject(getManager(), getResolveScope()) : extendsListTypes[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 54) {
            if (getFirstChildNode().getElementType() == JAVA_CODE_REFERENCE) {
                return getFirstChildNode();
            }
            return null;
        }
        if (i == 55) {
            return findChildByType(DOT);
        }
        if (i != 96) {
            return null;
        }
        return getLastChildNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JAVA_CODE_REFERENCE) {
            return 54;
        }
        if (elementType == DOT) {
            return 55;
        }
        return elementType == SUPER_KEYWORD ? 96 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitSuperExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiSuperExpression:" + getText();
    }
}
