package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiCall;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightClassReference;

/* JADX INFO: loaded from: classes3.dex */
public class PsiEnumConstantInitializerImpl extends PsiClassImpl implements PsiEnumConstantInitializer {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiEnumConstantInitializerImpl.class);
    private PsiClassType myCachedBaseType;

    /* JADX WARN: Code duplicated, block: B:25:0x003b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "name";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiEnumConstantInitializerImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getBaseClassType";
        } else if (i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiEnumConstantInitializerImpl";
        } else if (i == 3) {
            objArr[1] = "getSuperTypes";
        } else if (i != 4) {
            objArr[1] = "getEnumConstant";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiEnumConstantInitializerImpl";
        }
        if (i == 2) {
            objArr[2] = "hasModifierProperty";
        } else if (i == 4) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getOriginalElement */
    public PsiElement getFunDelegate() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str != null) {
            return false;
        }
        $$$reportNull$$$0(2);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public boolean isInQualifiedNew() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return false;
    }

    public PsiEnumConstantInitializerImpl(PsiClassStub psiClassStub) {
        super(psiClassStub, JavaStubElementTypes.ENUM_CONSTANT_INITIALIZER);
    }

    public PsiEnumConstantInitializerImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        PsiEnumConstantInitializerImpl psiEnumConstantInitializerImpl = (PsiEnumConstantInitializerImpl) super.clone();
        psiEnumConstantInitializerImpl.myCachedBaseType = null;
        return psiEnumConstantInitializerImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        this.myCachedBaseType = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiExpressionList getArgumentList() {
        PsiElement parent = getLightParent();
        LOG.assertTrue(parent instanceof PsiEnumConstant);
        return ((PsiCall) parent).getArgumentList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiJavaCodeReferenceElement getBaseClassReference() {
        PsiClass baseClass = getBaseClass();
        return new LightClassReference(getManager(), baseClass.getName(), baseClass);
    }

    private PsiClass getBaseClass() {
        PsiElement parent = getLightParent();
        Logger logger = LOG;
        logger.assertTrue(parent instanceof PsiEnumConstant, parent);
        PsiClass containingClass = ((PsiEnumConstant) parent).getContainingClass();
        logger.assertTrue(containingClass != null);
        return containingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer
    public PsiEnumConstant getEnumConstant() {
        PsiEnumConstant psiEnumConstant = (PsiEnumConstant) getLightParent();
        if (psiEnumConstant == null) {
            $$$reportNull$$$0(0);
        }
        return psiEnumConstant;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass
    public PsiClassType getBaseClassType() {
        PsiClassType psiClassTypeCreateType = this.myCachedBaseType;
        if (psiClassTypeCreateType == null) {
            psiClassTypeCreateType = JavaPsiFacade.getElementFactory(getProject()).createType(getBaseClass());
            this.myCachedBaseType = psiClassTypeCreateType;
        }
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(1);
        }
        return psiClassTypeCreateType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        return new PsiClassType[]{getBaseClassType()};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitEnumConstantInitializer(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiAnonymousClass (PsiEnumConstantInitializerImpl)):";
    }
}
