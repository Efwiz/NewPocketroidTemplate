package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.lang.ref.Reference;
import java.util.Arrays;
import javax.swing.Icon;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaParameterType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class PsiParameterImpl extends JavaStubPsiElement<PsiParameterStub> implements PsiParameter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiParameterImpl.class);
    private volatile Reference<PsiType> myCachedType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            case 13:
            case 14:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                i2 = 2;
                break;
            case 6:
            case 13:
            case 14:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 3:
                objArr[0] = "node";
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiParameterImpl";
                break;
            case 6:
            case 13:
                objArr[0] = "name";
                break;
            case 14:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        switch (i) {
            case 4:
            case 5:
                objArr[1] = "getName";
                break;
            case 6:
            case 13:
            case 14:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiParameterImpl";
                break;
            case 7:
                objArr[1] = "getNameIdentifier";
                break;
            case 8:
                objArr[1] = "getNode";
                break;
            case 9:
            case 10:
            case 11:
                objArr[1] = "getType";
                break;
            case 12:
                objArr[1] = "getModifierList";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[1] = "getDeclarationScope";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                break;
            case 6:
                objArr[2] = "setName";
                break;
            case 13:
                objArr[2] = "hasModifierProperty";
                break;
            case 14:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                throw new IllegalStateException(str2);
            case 6:
            case 13:
            case 14:
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiParameterImpl(PsiParameterStub psiParameterStub) {
        this(psiParameterStub, JavaStubElementTypes.PARAMETER);
        if (psiParameterStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected PsiParameterImpl(PsiParameterStub psiParameterStub, IStubElementType iStubElementType) {
        super(psiParameterStub, iStubElementType);
        if (psiParameterStub == null) {
            $$$reportNull$$$0(1);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiParameterImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
    }

    public static PsiType getLambdaParameterType(PsiParameter psiParameter) {
        int parameterIndex;
        final PsiLambdaExpression psiLambdaExpression;
        PsiElement parent = psiParameter.getLightParent();
        if ((parent instanceof PsiParameterList) && (parameterIndex = ((PsiParameterList) parent).getParameterIndex(psiParameter)) > -1 && (psiLambdaExpression = (PsiLambdaExpression) PsiTreeUtil.getParentOfType(psiParameter, PsiLambdaExpression.class)) != null) {
            PsiType groundTargetType = psiLambdaExpression.getGroundTargetType((PsiType) MethodCandidateInfo.ourOverloadGuard.doPreventingRecursion(psiParameter, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiParameterImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return LambdaUtil.getFunctionalInterfaceType(psiLambdaExpression, true);
                }
            }));
            if (groundTargetType instanceof PsiIntersectionType) {
                for (PsiType psiType : ((PsiIntersectionType) groundTargetType).getConjuncts()) {
                    PsiType lambdaParameterFromType = LambdaUtil.getLambdaParameterFromType(psiType, parameterIndex);
                    if (lambdaParameterFromType != null) {
                        return lambdaParameterFromType;
                    }
                }
            } else {
                PsiType lambdaParameterFromType2 = LambdaUtil.getLambdaParameterFromType(groundTargetType, parameterIndex);
                if (lambdaParameterFromType2 != null) {
                    return lambdaParameterFromType2;
                }
            }
        }
        return new PsiLambdaParameterType(psiParameter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        this.myCachedType = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        PsiParameterImpl psiParameterImpl = (PsiParameterImpl) super.clone();
        psiParameterImpl.myCachedType = null;
        return psiParameterImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public final String get$name() {
        PsiParameterStub psiParameterStub = (PsiParameterStub) getGreenStub();
        if (psiParameterStub != null) {
            String name2 = psiParameterStub.getName();
            if (name2 == null) {
                $$$reportNull$$$0(4);
            }
            return name2;
        }
        String text = mo7392getNameIdentifier().getText();
        if (text == null) {
            $$$reportNull$$$0(5);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public final PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        PsiImplUtil.setName(mo7392getNameIdentifier(), str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public final PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifier = (PsiIdentifier) PsiTreeUtil.getRequiredChildOfType(this, PsiIdentifier.class);
        if (psiIdentifier == null) {
            $$$reportNull$$$0(7);
        }
        return psiIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public CompositeElement getNode() {
        CompositeElement compositeElement = (CompositeElement) super.getNode();
        if (compositeElement == null) {
            $$$reportNull$$$0(8);
        }
        return compositeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public PsiType getType() {
        PsiParameterStub psiParameterStub = (PsiParameterStub) getStub();
        if (psiParameterStub != null) {
            PsiType psiTypeCreateTypeFromStub = (PsiType) SoftReference.dereference(this.myCachedType);
            if (psiTypeCreateTypeFromStub == null) {
                psiTypeCreateTypeFromStub = JavaSharedImplUtil.createTypeFromStub(this, psiParameterStub.getType());
                this.myCachedType = new SoftReference(psiTypeCreateTypeFromStub);
            }
            if (psiTypeCreateTypeFromStub == null) {
                $$$reportNull$$$0(9);
            }
            return psiTypeCreateTypeFromStub;
        }
        this.myCachedType = null;
        PsiTypeElement typeElement = getTypeElement();
        if (typeElement == null || (isLambdaParameter() && typeElement.isInferredType())) {
            PsiType lambdaParameterType = getLambdaParameterType(this);
            if (lambdaParameterType == null) {
                $$$reportNull$$$0(10);
            }
            return lambdaParameterType;
        }
        PsiType type = JavaSharedImplUtil.getType(typeElement, mo7392getNameIdentifier());
        if (type == null) {
            $$$reportNull$$$0(11);
        }
        return type;
    }

    private boolean isLambdaParameter() {
        PsiElement parent = getLightParent();
        return (parent instanceof PsiParameterList) && (parent.getLightParent() instanceof PsiLambdaExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof PsiTypeElement) {
                return (PsiTypeElement) firstChild;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        PsiModifierList psiModifierList = (PsiModifierList) getStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
        if (psiModifierList == null) {
            $$$reportNull$$$0(12);
        }
        return psiModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        return getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        JavaSharedImplUtil.normalizeBrackets(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(14);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitParameter(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiParameter:" + get$name();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: getDeclarationScope */
    public PsiElement getMethod() {
        PsiElement psiElement;
        PsiElement parent = getLightParent();
        if (parent == null) {
            return this;
        }
        if (parent instanceof PsiParameterList) {
            PsiElement lightParent = parent.getLightParent();
            if (lightParent == null) {
                $$$reportNull$$$0(16);
            }
            return lightParent;
        }
        if (parent instanceof PsiForeachStatement) {
            if (parent == null) {
                $$$reportNull$$$0(17);
            }
            return parent;
        }
        if (parent instanceof PsiCatchSection) {
            if (parent == null) {
                $$$reportNull$$$0(18);
            }
            return parent;
        }
        PsiElement[] children = parent.getChildren();
        if (children != null) {
            int i = 0;
            while (i < children.length) {
                if (children[i].equals(this)) {
                    do {
                        i++;
                        if (i >= children.length) {
                            break;
                        }
                        psiElement = children[i];
                    } while (!(psiElement instanceof PsiCodeBlock));
                    if (psiElement == null) {
                        $$$reportNull$$$0(19);
                    }
                    return psiElement;
                }
                i++;
            }
        }
        LOG.error("Code block not found among parameter' (" + this + ") parent' (" + parent + ") children: " + Arrays.asList(children));
        $$$reportNull$$$0(20);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        PsiParameterStub psiParameterStub = (PsiParameterStub) getGreenStub();
        if (psiParameterStub != null) {
            return psiParameterStub.isParameterTypeEllipsis();
        }
        this.myCachedType = null;
        PsiTypeElement typeElement = getTypeElement();
        return (typeElement == null || SourceTreeToPsiMap.psiToTreeNotNull(typeElement).findChildByType(JavaTokenType.ELLIPSIS) == null) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, PlatformIcons.PARAMETER_ICON, 0));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        return new LocalSearchScope(getMethod());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getOriginalElement */
    public PsiElement getFunDelegate() {
        PsiElement parent = getLightParent();
        if (parent instanceof PsiParameterList) {
            PsiElement lightParent = parent.getLightParent();
            if (lightParent instanceof PsiMethod) {
                PsiElement originalElement = lightParent.getFunDelegate();
                if ((originalElement instanceof PsiMethod) && originalElement != lightParent) {
                    int parameterIndex = ((PsiParameterList) parent).getParameterIndex(this);
                    PsiParameter[] parameters = ((PsiMethod) originalElement).getParameterList().getParameters();
                    if (parameterIndex < parameters.length) {
                        return parameters[parameterIndex];
                    }
                }
            }
        }
        return this;
    }
}
