package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSyntheticClass;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassInitializerStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class PsiClassInitializerImpl extends JavaStubPsiElement<PsiClassInitializerStub> implements PsiClassInitializer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiClassInitializerImpl";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3) {
            objArr[0] = "processor";
        } else if (i == 4) {
            objArr[0] = "state";
        } else if (i != 5) {
            objArr[0] = "name";
        } else {
            objArr[0] = "place";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiClassInitializerImpl";
        } else {
            objArr[1] = "getBody";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "accept";
            } else if (i == 3 || i == 4 || i == 5) {
                objArr[2] = "processDeclarations";
            } else {
                objArr[2] = "hasModifierProperty";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public PsiClassInitializerImpl(PsiClassInitializerStub psiClassInitializerStub) {
        super(psiClassInitializerStub, JavaStubElementTypes.CLASS_INITIALIZER);
    }

    public PsiClassInitializerImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        PsiElement parent = getLightParent();
        if (!(parent instanceof PsiClass)) {
            parent = PsiTreeUtil.getParentOfType(this, (Class<PsiElement>) PsiSyntheticClass.class);
        }
        return (PsiClass) parent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        PsiClass psiClassMo6092getContainingClass = getContainingClass();
        return psiClassMo6092getContainingClass != null ? psiClassMo6092getContainingClass : super.getContext();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return (PsiModifierList) getStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer
    public PsiCodeBlock getBody() {
        PsiCodeBlock psiCodeBlock = (PsiCodeBlock) ((CompositeElement) getNode()).findChildByRoleAsPsiElement(17);
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(1);
        }
        return psiCodeBlock;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitClassInitializer(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiClassInitializer";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(3);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(5);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        return psiElement == null || PsiScopesUtil.walkChildrenScopes(this, psiScopeProcessor, resolveState, psiElement, psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return IconManager.getInstance().createLayeredIcon(this, PlatformIcons.CLASS_INITIALIZER, ElementPresentationUtil.getFlags(this, false));
    }
}
