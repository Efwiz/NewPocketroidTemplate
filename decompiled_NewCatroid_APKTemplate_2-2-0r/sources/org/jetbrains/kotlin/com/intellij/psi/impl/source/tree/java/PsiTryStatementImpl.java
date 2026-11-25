package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.ArrayList;
import org.jetbrains.kotlin.com.intellij.codeInsight.BlockUtils;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTryStatementImpl extends CompositePsiElement implements PsiTryStatement, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiTryStatementImpl.class);
    private volatile PsiParameter[] myCachedCatchParameters;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 6:
            case 11:
                objArr[0] = "child";
                break;
            case 7:
                objArr[0] = "visitor";
                break;
            case 8:
                objArr[0] = "processor";
                break;
            case 9:
                objArr[0] = "state";
                break;
            case 10:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTryStatementImpl";
                break;
        }
        switch (i) {
            case 3:
            case 4:
                objArr[1] = "getCatchBlockParameters";
                break;
            case 5:
                objArr[1] = "getCatchSections";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTryStatementImpl";
                break;
            default:
                objArr[1] = "getCatchBlocks";
                break;
        }
        switch (i) {
            case 6:
                objArr[2] = "getChildRole";
                break;
            case 7:
                objArr[2] = "accept";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "processDeclarations";
                break;
            case 11:
                objArr[2] = "deleteChildInternal";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    public PsiTryStatementImpl() {
        super(TRY_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        this.myCachedCatchParameters = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement
    public PsiCodeBlock getTryBlock() {
        return (PsiCodeBlock) findChildByRoleAsPsiElement(48);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement
    public PsiCodeBlock[] getCatchBlocks() {
        if (SourceTreeToPsiMap.psiElementToTree(getTryBlock()) != null) {
            PsiCatchSection[] catchSections = getCatchSections();
            if (catchSections.length == 0) {
                PsiCodeBlock[] psiCodeBlockArr = PsiCodeBlock.EMPTY_ARRAY;
                if (psiCodeBlockArr == null) {
                    $$$reportNull$$$0(0);
                }
                return psiCodeBlockArr;
            }
            int length = catchSections[catchSections.length + (-1)].getCatchBlock() == null ? catchSections.length - 1 : catchSections.length;
            PsiCodeBlock[] psiCodeBlockArr2 = new PsiCodeBlock[length];
            for (int i = 0; i < length; i++) {
                psiCodeBlockArr2[i] = catchSections[i].getCatchBlock();
            }
            return psiCodeBlockArr2;
        }
        PsiCodeBlock[] psiCodeBlockArr3 = PsiCodeBlock.EMPTY_ARRAY;
        if (psiCodeBlockArr3 == null) {
            $$$reportNull$$$0(2);
        }
        return psiCodeBlockArr3;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement
    public PsiParameter[] getCatchBlockParameters() {
        PsiParameter[] psiParameterArr = this.myCachedCatchParameters;
        if (psiParameterArr == null) {
            PsiCatchSection[] catchSections = getCatchSections();
            if (catchSections.length == 0) {
                PsiParameter[] psiParameterArr2 = PsiParameter.EMPTY_ARRAY;
                if (psiParameterArr2 == null) {
                    $$$reportNull$$$0(3);
                }
                return psiParameterArr2;
            }
            int length = catchSections[catchSections.length + (-1)].getCatchBlock() == null ? catchSections.length - 1 : catchSections.length;
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                PsiParameter parameter = catchSections[i].getParameter();
                if (parameter != null) {
                    arrayList.add(parameter);
                }
            }
            psiParameterArr = (PsiParameter[]) arrayList.toArray(PsiParameter.EMPTY_ARRAY);
            this.myCachedCatchParameters = psiParameterArr;
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement
    public PsiCatchSection[] getCatchSections() {
        PsiCatchSection[] psiCatchSectionArr = (PsiCatchSection[]) getChildrenAsPsiElements(CATCH_SECTION_BIT_SET, PsiCatchSection.ARRAY_FACTORY);
        if (psiCatchSectionArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiCatchSectionArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement
    public PsiCodeBlock getFinallyBlock() {
        return (PsiCodeBlock) findChildByRoleAsPsiElement(52);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement
    public PsiResourceList getResourceList() {
        return (PsiResourceList) PsiTreeUtil.getChildOfType(this, PsiResourceList.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 45) {
            return findChildByType(TRY_KEYWORD);
        }
        if (i != 52) {
            if (i == 47) {
                return findChildByType(FINALLY_KEYWORD);
            }
            if (i != 48) {
                return null;
            }
            return findChildByType(CODE_BLOCK);
        }
        ASTNode aSTNodeFindChildByRole = findChildByRole(47);
        if (aSTNodeFindChildByRole == null) {
            return null;
        }
        do {
            aSTNodeFindChildByRole = aSTNodeFindChildByRole.getTreeNext();
            if (aSTNodeFindChildByRole == null) {
                return null;
            }
        } while (aSTNodeFindChildByRole.getElementType() != CODE_BLOCK);
        return aSTNodeFindChildByRole;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == TRY_KEYWORD) {
            return 45;
        }
        if (elementType == FINALLY_KEYWORD) {
            return 47;
        }
        if (elementType == CATCH_SECTION) {
            return 251;
        }
        if (aSTNode.getElementType() != CODE_BLOCK) {
            return 0;
        }
        int childRole = getChildRole(aSTNode, 48);
        return childRole != 0 ? childRole : getChildRole(aSTNode, 52);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTryStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(8);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(10);
        }
        PsiResourceList resourceList = getResourceList();
        if (resourceList != null && (psiElement instanceof PsiCodeBlock) && psiElement == getTryBlock()) {
            return PsiImplUtil.processDeclarationsInResourceList(resourceList, psiScopeProcessor, resolveState, psiElement);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(11);
        }
        if ((aSTNode.getPsi() instanceof PsiResourceList) && getCatchBlocks().length == 0 && getFinallyBlock() == null && getTryBlock() != null) {
            BlockUtils.unwrapTryBlock(this);
        } else {
            super.deleteChildInternal(aSTNode);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTryStatement";
    }
}
