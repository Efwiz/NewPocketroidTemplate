package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class PsiClassObjectAccessExpressionImpl extends ExpressionPsiElement implements PsiClassObjectAccessExpression, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiClassObjectAccessExpressionImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "child";
        } else if (i != 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiClassObjectAccessExpressionImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getOperand";
        } else if (i == 2 || i == 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiClassObjectAccessExpressionImpl";
        } else if (i != 4) {
            objArr[1] = "getType";
        } else {
            objArr[1] = "getElementIcon";
        }
        if (i == 2) {
            objArr[2] = "getChildRole";
        } else if (i == 3) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiClassObjectAccessExpressionImpl() {
        super(CLASS_OBJECT_ACCESS_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiType type = PsiImplUtil.getType(this);
        if (type == null) {
            $$$reportNull$$$0(0);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiTypeElement getOperand() {
        PsiTypeElement psiTypeElement = (PsiTypeElement) findChildByRoleAsPsiElement(10);
        if (psiTypeElement == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 10) {
            return findChildByType(TYPE);
        }
        if (i == 55) {
            return findChildByType(DOT);
        }
        if (i != 90) {
            return null;
        }
        return findChildByType(CLASS_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == TYPE) {
            return 10;
        }
        if (elementType == DOT) {
            return 55;
        }
        return elementType == CLASS_KEYWORD ? 90 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitClassObjectAccessExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiClassObjectAccessExpression:" + getText();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    /* JADX INFO: renamed from: computeBaseIcon */
    public Icon m6547xac440668(int i) {
        return getElementIcon(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        RowIcon rowIconCreateLayeredIcon = IconManager.getInstance().createLayeredIcon(this, PlatformIcons.FIELD_ICON, 0);
        rowIconCreateLayeredIcon.setIcon(PlatformIcons.PUBLIC_ICON, 1);
        if (rowIconCreateLayeredIcon == null) {
            $$$reportNull$$$0(4);
        }
        return rowIconCreateLayeredIcon;
    }
}
