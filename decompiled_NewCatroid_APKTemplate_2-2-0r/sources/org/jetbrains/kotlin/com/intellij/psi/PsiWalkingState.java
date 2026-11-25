package org.jetbrains.kotlin.com.intellij.psi;

import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.WalkingState;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiWalkingState extends WalkingState<PsiElement> {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiWalkingState.class);
    private final PsiElementVisitor myVisitor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2) {
            objArr[0] = "guide";
        } else if (i == 3 || i == 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiWalkingState";
        if (i == 3) {
            objArr[2] = "visit";
        } else if (i != 4) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "elementStarted";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private static class PsiTreeGuide implements WalkingState.TreeGuide<PsiElement> {
        private static final PsiTreeGuide instance = new PsiTreeGuide();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiWalkingState$PsiTreeGuide";
            if (i == 1) {
                objArr[2] = "getPrevSibling";
            } else if (i == 2) {
                objArr[2] = "getFirstChild";
            } else if (i != 3) {
                objArr[2] = "getNextSibling";
            } else {
                objArr[2] = "getParent";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private PsiTreeGuide() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public PsiElement getNextSibling(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            return checkSanity(psiElement, psiElement.getNextSibling());
        }

        private static PsiElement checkSanity(PsiElement psiElement, PsiElement psiElement2) {
            if (psiElement2 != PsiUtilCore.NULL_PSI_ELEMENT) {
                return psiElement2;
            }
            throw new PsiInvalidElementAccessException(psiElement, "Sibling of " + psiElement + " is NULL_PSI_ELEMENT");
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public PsiElement getPrevSibling(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(1);
            }
            return checkSanity(psiElement, psiElement.getPrevSibling());
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public PsiElement getFirstChild(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(2);
            }
            return psiElement.getFirstChild();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public PsiElement getParent(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(3);
            }
            return psiElement.getLightParent();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected PsiWalkingState(PsiElementVisitor psiElementVisitor) {
        this(psiElementVisitor, PsiTreeGuide.instance);
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected PsiWalkingState(PsiElementVisitor psiElementVisitor, WalkingState.TreeGuide<PsiElement> treeGuide) {
        super(treeGuide);
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (treeGuide == null) {
            $$$reportNull$$$0(2);
        }
        this.myVisitor = psiElementVisitor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState
    public void visit(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        psiElement.accept(this.myVisitor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState
    public void elementStarted(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        if (!this.startedWalking && (psiElement instanceof PsiCompiledElement)) {
            LOG.error(psiElement + "; Do not use walking visitor inside compiled PSI since getNextSibling() is too slow there");
        }
        super.elementStarted(psiElement);
    }
}
