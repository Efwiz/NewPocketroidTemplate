package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class ClsClassObjectAccessExpressionImpl extends ClsElementImpl implements PsiClassObjectAccessExpression {
    private final ClsElementImpl myParent;
    private final ClsTypeElementImpl myTypeElement;

    /* JADX WARN: Code duplicated, block: B:22:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsClassObjectAccessExpressionImpl";
        } else if (i == 3) {
            objArr[0] = "visitor";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsClassObjectAccessExpressionImpl";
        } else {
            objArr[0] = "buffer";
        }
        if (i == 2) {
            objArr[1] = "getChildren";
        } else if (i == 4) {
            objArr[1] = "getOperand";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsClassObjectAccessExpressionImpl";
        } else {
            objArr[1] = "getType";
        }
        if (i == 1) {
            objArr[2] = "setMirror";
        } else if (i != 2) {
            if (i == 3) {
                objArr[2] = "accept";
            } else if (i != 4 && i != 5) {
                objArr[2] = "appendMirrorText";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ClsClassObjectAccessExpressionImpl(ClsElementImpl clsElementImpl, String str) {
        this.myParent = clsElementImpl;
        this.myTypeElement = new ClsTypeElementImpl(this, str, (char) 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(0);
        }
        this.myTypeElement.appendMirrorText(0, sb);
        sb.append('.').append("class");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(1);
        }
        setMirrorCheckingType(treeElement, null);
        setMirror(getOperand(), ((PsiClassObjectAccessExpression) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getOperand());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        return new PsiElement[]{this.myTypeElement};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiTypeElement getOperand() {
        ClsTypeElementImpl clsTypeElementImpl = this.myTypeElement;
        if (clsTypeElementImpl == null) {
            $$$reportNull$$$0(4);
        }
        return clsTypeElementImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiType type = PsiImplUtil.getType(this);
        if (type == null) {
            $$$reportNull$$$0(5);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        StringBuilder sb = new StringBuilder();
        appendMirrorText(0, sb);
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        RowIcon rowIconCreateLayeredIcon = IconManager.getInstance().createLayeredIcon(this, PlatformIcons.FIELD_ICON, 0);
        rowIconCreateLayeredIcon.setIcon(PlatformIcons.PUBLIC_ICON, 1);
        return rowIconCreateLayeredIcon;
    }
}
