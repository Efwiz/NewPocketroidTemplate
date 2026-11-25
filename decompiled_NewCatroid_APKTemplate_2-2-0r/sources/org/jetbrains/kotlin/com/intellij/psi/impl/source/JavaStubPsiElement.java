package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaStubPsiElement<T extends StubElement> extends StubBasedPsiElementBase<T> implements StubBasedPsiElement<T> {
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaStubPsiElement.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 11 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 11 || i == 12) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "nodeType";
                break;
            case 2:
                objArr[0] = "node";
                break;
            case 3:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaStubPsiElement";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
                objArr[0] = "newElement";
                break;
            case 9:
                objArr[0] = "visitor";
                break;
            case 10:
                objArr[0] = "treeElementClone";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 3) {
            objArr[1] = "getLanguage";
        } else if (i == 11 || i == 12) {
            objArr[1] = "getChildren";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/JavaStubPsiElement";
        }
        switch (i) {
            case 3:
            case 11:
            case 12:
                break;
            case 4:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 5:
                objArr[2] = "addBefore";
                break;
            case 6:
                objArr[2] = "addAfter";
                break;
            case 7:
                objArr[2] = "checkAdd";
                break;
            case 8:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 9:
                objArr[2] = "acceptChildren";
                break;
            case 10:
                objArr[2] = "cloneImpl";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 11 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaStubPsiElement(T t, IStubElementType iStubElementType) {
        super(t, iStubElementType);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaStubPsiElement(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        JavaLanguage javaLanguage = JavaLanguage.INSTANCE;
        if (javaLanguage == null) {
            $$$reportNull$$$0(3);
        }
        return javaLanguage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return calcTreeElement().getTextOffset();
    }

    protected CompositeElement calcTreeElement() {
        return (CompositeElement) getNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        calcTreeElement().addInternal(treeElementCopyToElement, treeElementCopyToElement, null, null);
        return SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        calcTreeElement().addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.TRUE);
        return SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        calcTreeElement().addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.FALSE);
        return SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        CompositeElement compositeElementCalcTreeElement = calcTreeElement();
        LOG.assertTrue(compositeElementCalcTreeElement.getTreeParent() != null);
        CheckUtil.checkWritable(this);
        ((CompositeElement) compositeElementCalcTreeElement.getTreeParent()).deleteChildInternal(compositeElementCalcTreeElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void deleteChildRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        if (psiElement == null) {
            LOG.assertTrue(psiElement2 == null);
            return;
        }
        ASTNode aSTNodePsiElementToTree = SourceTreeToPsiMap.psiElementToTree(psiElement);
        ASTNode aSTNodePsiElementToTree2 = SourceTreeToPsiMap.psiElementToTree(psiElement2);
        CompositeElement compositeElementCalcTreeElement = calcTreeElement();
        Logger logger = LOG;
        logger.assertTrue(aSTNodePsiElementToTree.getTreeParent() == compositeElementCalcTreeElement);
        logger.assertTrue(aSTNodePsiElementToTree2.getTreeParent() == compositeElementCalcTreeElement);
        CodeEditUtil.removeChildren(compositeElementCalcTreeElement, aSTNodePsiElementToTree, aSTNodePsiElementToTree2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        return SharedImplUtil.doReplace(this, calcTreeElement(), psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void acceptChildren(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(9);
        }
        SharedImplUtil.acceptChildren(psiElementVisitor, calcTreeElement());
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        CompositeElement compositeElementCalcTreeElement = calcTreeElement();
        return cloneImpl((CompositeElement) (compositeElementCalcTreeElement.getTreeParent() != null ? compositeElementCalcTreeElement.copyElement() : compositeElementCalcTreeElement.clone()));
    }

    protected StubBasedPsiElementBase cloneImpl(CompositeElement compositeElement) {
        if (compositeElement == null) {
            $$$reportNull$$$0(10);
        }
        StubBasedPsiElementBase stubBasedPsiElementBase = (StubBasedPsiElementBase) super.clone();
        stubBasedPsiElementBase.setNode(compositeElement);
        compositeElement.setPsi(stubBasedPsiElementBase);
        return stubBasedPsiElementBase;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement firstChild = getFirstChild();
        if (firstChild == null) {
            PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
            if (psiElementArr == null) {
                $$$reportNull$$$0(11);
            }
            return psiElementArr;
        }
        int i = 0;
        int i2 = 0;
        while (firstChild != null) {
            i2++;
            firstChild = firstChild.getNextSibling();
        }
        PsiElement[] psiElementArr2 = new PsiElement[i2];
        PsiElement firstChild2 = getFirstChild();
        while (firstChild2 != null) {
            psiElementArr2[i] = firstChild2;
            firstChild2 = firstChild2.getNextSibling();
            i++;
        }
        return psiElementArr2;
    }
}
