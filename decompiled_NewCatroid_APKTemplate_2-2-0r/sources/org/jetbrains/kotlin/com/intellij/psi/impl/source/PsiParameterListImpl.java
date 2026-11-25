package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiParameterListImpl extends JavaStubPsiElement<PsiParameterListStub> implements PsiParameterList {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiParameterListImpl.class);

    /* JADX WARN: Code duplicated, block: B:21:0x0033  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiParameterListImpl";
        } else if (i == 3) {
            objArr[0] = "parameter";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiParameterListImpl";
        } else if (i != 5) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 2) {
            objArr[1] = "getParameters";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiParameterListImpl";
        } else {
            objArr[1] = "getNode";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "getParameterIndex";
            } else if (i != 4) {
                if (i != 5) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "accept";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiParameterListImpl(PsiParameterListStub psiParameterListStub) {
        super(psiParameterListStub, JavaStubElementTypes.PARAMETER_LIST);
        if (psiParameterListStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiParameterListImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public PsiParameter[] getParameters() {
        PsiParameter[] psiParameterArr = (PsiParameter[]) getStubOrPsiChildren(JavaStubElementTypes.PARAMETER, PsiParameter.ARRAY_FACTORY);
        if (psiParameterArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParameterIndex(PsiParameter psiParameter) {
        if (psiParameter == null) {
            $$$reportNull$$$0(3);
        }
        LOG.assertTrue(psiParameter.getLightParent() == this);
        return PsiImplUtil.getParameterIndex(psiParameter, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public CompositeElement getNode() {
        CompositeElement compositeElement = (CompositeElement) super.getNode();
        if (compositeElement == null) {
            $$$reportNull$$$0(4);
        }
        return compositeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParametersCount() {
        PsiParameterListStub psiParameterListStub = (PsiParameterListStub) getGreenStub();
        if (psiParameterListStub != null) {
            Iterator<StubElement> it = psiParameterListStub.getChildrenStubs().iterator();
            int i = 0;
            while (it.hasNext()) {
                if (it.next().getStubType() == JavaStubElementTypes.PARAMETER) {
                    i++;
                }
            }
            return i;
        }
        return getNode().countChildren(Constants.PARAMETER_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public PsiParameter getParameter(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index is negative: " + i);
        }
        PsiParameterListStub psiParameterListStub = (PsiParameterListStub) getGreenStub();
        int i2 = 0;
        if (psiParameterListStub != null) {
            for (StubElement stubElement : psiParameterListStub.getChildrenStubs()) {
                if (stubElement.getStubType() == JavaStubElementTypes.PARAMETER) {
                    if (i2 == i) {
                        return (PsiParameter) stubElement.getPsi();
                    }
                    i2++;
                }
            }
            return null;
        }
        for (ASTNode firstChildNode = getNode().getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getElementType() == JavaStubElementTypes.PARAMETER) {
                if (i2 == i) {
                    return (PsiParameter) firstChildNode.getPsi();
                }
                i2++;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public boolean isEmpty() {
        PsiParameterListStub psiParameterListStub = (PsiParameterListStub) getGreenStub();
        if (psiParameterListStub == null) {
            return getNode().findChildByType(Constants.PARAMETER_BIT_SET) == null;
        }
        Iterator<StubElement> it = psiParameterListStub.getChildrenStubs().iterator();
        while (it.hasNext()) {
            if (it.next().getStubType() == JavaStubElementTypes.PARAMETER) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiParameterList:" + getText();
    }
}
