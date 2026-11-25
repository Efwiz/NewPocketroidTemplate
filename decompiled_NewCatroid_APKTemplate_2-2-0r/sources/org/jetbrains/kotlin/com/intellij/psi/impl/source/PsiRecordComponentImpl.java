package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.lang.ref.Reference;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordComponentStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiRecordComponentImpl extends JavaStubPsiElement<PsiRecordComponentStub> implements PsiRecordComponent {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile Reference<PsiType> myCachedType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 4:
            case 9:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                i2 = 2;
                break;
            case 4:
            case 9:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "node";
                break;
            case 2:
                objArr[0] = "visitor";
                break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiRecordComponentImpl";
                break;
            case 4:
            case 9:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getModifierList";
                break;
            case 4:
            case 9:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiRecordComponentImpl";
                break;
            case 5:
            case 6:
                objArr[1] = "getType";
                break;
            case 7:
                objArr[1] = "getTypeElement";
                break;
            case 8:
                objArr[1] = "getNameIdentifier";
                break;
            case 10:
            case 11:
                objArr[1] = "getName";
                break;
            case 12:
                objArr[1] = "getNode";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "accept";
                break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                break;
            case 4:
                objArr[2] = "hasModifierProperty";
                break;
            case 9:
                objArr[2] = "setName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
                throw new IllegalStateException(str2);
            case 4:
            case 9:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRecordComponentImpl(PsiRecordComponentStub psiRecordComponentStub) {
        super(psiRecordComponentStub, JavaStubElementTypes.RECORD_COMPONENT);
        if (psiRecordComponentStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRecordComponentImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitRecordComponent(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        PsiElement parent = getLightParent();
        if (parent == null) {
            return null;
        }
        PsiElement lightParent = parent.getLightParent();
        if (lightParent instanceof PsiClass) {
            return (PsiClass) lightParent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        PsiModifierList psiModifierList = (PsiModifierList) getStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
        if (psiModifierList == null) {
            $$$reportNull$$$0(3);
        }
        return psiModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiType getType() {
        PsiRecordComponentStub psiRecordComponentStub = (PsiRecordComponentStub) getStub();
        if (psiRecordComponentStub != null) {
            PsiType psiTypeCreateTypeFromStub = (PsiType) SoftReference.dereference(this.myCachedType);
            if (psiTypeCreateTypeFromStub == null) {
                psiTypeCreateTypeFromStub = JavaSharedImplUtil.createTypeFromStub(this, psiRecordComponentStub.getType());
                this.myCachedType = new SoftReference(psiTypeCreateTypeFromStub);
            }
            if (psiTypeCreateTypeFromStub == null) {
                $$$reportNull$$$0(5);
            }
            return psiTypeCreateTypeFromStub;
        }
        this.myCachedType = null;
        PsiType type = JavaSharedImplUtil.getType(getTypeElement(), mo7392getNameIdentifier());
        if (type == null) {
            $$$reportNull$$$0(6);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiTypeElement psiTypeElement = (PsiTypeElement) findNotNullChildByType(JavaElementType.TYPE);
        if (psiTypeElement == null) {
            $$$reportNull$$$0(7);
        }
        return psiTypeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        JavaSharedImplUtil.normalizeBrackets(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifier = (PsiIdentifier) findNotNullChildByType(JavaTokenType.IDENTIFIER);
        if (psiIdentifier == null) {
            $$$reportNull$$$0(8);
        }
        return psiIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return mo7392getNameIdentifier().getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return PsiImplUtil.setName(mo7392getNameIdentifier(), str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        PsiRecordComponentStub psiRecordComponentStub = (PsiRecordComponentStub) getGreenStub();
        if (psiRecordComponentStub != null) {
            String name2 = psiRecordComponentStub.getName();
            if (name2 == null) {
                $$$reportNull$$$0(10);
            }
            return name2;
        }
        String text = mo7392getNameIdentifier().getText();
        if (text == null) {
            $$$reportNull$$$0(11);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public CompositeElement getNode() {
        CompositeElement compositeElement = (CompositeElement) super.getNode();
        if (compositeElement == null) {
            $$$reportNull$$$0(12);
        }
        return compositeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent
    public boolean isVarArgs() {
        PsiRecordComponentStub psiRecordComponentStub = (PsiRecordComponentStub) getGreenStub();
        if (psiRecordComponentStub != null) {
            return psiRecordComponentStub.isVararg();
        }
        return getType() instanceof PsiEllipsisType;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiRecordComponent:" + get$name();
    }
}
