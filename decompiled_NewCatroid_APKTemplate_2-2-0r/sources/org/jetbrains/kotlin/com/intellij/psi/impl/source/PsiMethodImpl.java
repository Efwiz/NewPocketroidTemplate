package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.Icon;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSyntheticClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightCompactConstructorParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stub.JavaStubImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class PsiMethodImpl extends JavaStubPsiElement<PsiMethodStub> implements Queryable, PsiMethod {
    private SoftReference<PsiType> myCachedType;

    /* JADX WARN: Code duplicated, block: B:15:0x001f  */
    /* JADX WARN: Code duplicated, block: B:8:0x0011  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 6 && i != 9 && i != 22) {
            switch (i) {
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 6 && i != 9 && i != 22) {
            switch (i) {
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 6 || i == 9) {
            objArr[0] = "name";
        } else if (i != 22) {
            switch (i) {
                case 14:
                    objArr[0] = "visitor";
                    break;
                case 15:
                    objArr[0] = "processor";
                    break;
                case 16:
                    objArr[0] = "state";
                    break;
                case 17:
                    objArr[0] = "place";
                    break;
                case 18:
                    objArr[0] = "substitutor";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiMethodImpl";
                    break;
            }
        } else {
            objArr[0] = CompilerOptions.INFO;
        }
        switch (i) {
            case 3:
                objArr[1] = "findSuperMethodSignaturesIncludingStatic";
                break;
            case 4:
                objArr[1] = "findDeepestSuperMethods";
                break;
            case 5:
                objArr[1] = "getHierarchicalMethodSignature";
                break;
            case 6:
            case 9:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 22:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiMethodImpl";
                break;
            case 7:
                objArr[1] = "getTypeParameters";
                break;
            case 8:
                objArr[1] = "getModifierList";
                break;
            case 10:
            case 11:
                objArr[1] = "getParameterList";
                break;
            case 12:
                objArr[1] = "getThrowsList";
                break;
            case 13:
                objArr[1] = "getNode";
                break;
            case 19:
            case 20:
                objArr[1] = "getSignature";
                break;
            case 21:
                objArr[1] = "getUseScope";
                break;
            default:
                objArr[1] = "findSuperMethods";
                break;
        }
        if (i == 6) {
            objArr[2] = "setName";
        } else if (i == 9) {
            objArr[2] = "hasModifierProperty";
        } else if (i != 22) {
            switch (i) {
                case 14:
                    objArr[2] = "accept";
                    break;
                case 15:
                case 16:
                case 17:
                    objArr[2] = "processDeclarations";
                    break;
                case 18:
                    objArr[2] = "getSignature";
                    break;
            }
        } else {
            objArr[2] = "putInfo";
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 9 && i != 22) {
            switch (i) {
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    public PsiMethodImpl(PsiMethodStub psiMethodStub) {
        this(psiMethodStub, JavaStubElementTypes.METHOD);
    }

    protected PsiMethodImpl(PsiMethodStub psiMethodStub, IStubElementType iStubElementType) {
        super(psiMethodStub, iStubElementType);
    }

    public PsiMethodImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        dropCached();
    }

    protected void dropCached() {
        this.myCachedType = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        PsiMethodImpl psiMethodImpl = (PsiMethodImpl) super.clone();
        psiMethodImpl.dropCached();
        return psiMethodImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return (PsiIdentifier) getNode().findChildByRoleAsPsiElement(9);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods() {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(0);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(boolean z) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, z);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(1);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(PsiClass psiClass) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, psiClass);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(2);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean z) {
        List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = PsiSuperMethodImplUtil.findSuperMethodSignaturesIncludingStatic(this, z);
        if (listFindSuperMethodSignaturesIncludingStatic == null) {
            $$$reportNull$$$0(3);
        }
        return listFindSuperMethodSignaturesIncludingStatic;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod findDeepestSuperMethod() {
        return PsiSuperMethodImplUtil.findDeepestSuperMethod(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findDeepestSuperMethods() {
        PsiMethod[] psiMethodArrFindDeepestSuperMethods = PsiSuperMethodImplUtil.findDeepestSuperMethods(this);
        if (psiMethodArrFindDeepestSuperMethods == null) {
            $$$reportNull$$$0(4);
        }
        return psiMethodArrFindDeepestSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String text;
        PsiMethodStub psiMethodStub = (PsiMethodStub) getGreenStub();
        if (psiMethodStub != null) {
            text = psiMethodStub.getName();
        } else {
            PsiIdentifier psiIdentifierMo7392getNameIdentifier = mo7392getNameIdentifier();
            text = psiIdentifierMo7392getNameIdentifier == null ? null : psiIdentifierMo7392getNameIdentifier.getText();
        }
        return text != null ? text : "<unnamed>";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public HierarchicalMethodSignature getHierarchicalMethodSignature() {
        HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(5);
        }
        return hierarchicalMethodSignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        PsiIdentifier psiIdentifierMo7392getNameIdentifier = mo7392getNameIdentifier();
        if (psiIdentifierMo7392getNameIdentifier == null) {
            throw new IncorrectOperationException("Empty name: " + this);
        }
        PsiImplUtil.setName(psiIdentifierMo7392getNameIdentifier, str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiTypeElement getReturnTypeElement() {
        if (isConstructor()) {
            return null;
        }
        return (PsiTypeElement) getNode().findChildByRoleAsPsiElement(10);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return (PsiTypeParameterList) getRequiredStubOrPsiChild(JavaStubElementTypes.TYPE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return PsiImplUtil.hasTypeParameters(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = PsiImplUtil.getTypeParameters(this);
        if (typeParameters == null) {
            $$$reportNull$$$0(7);
        }
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        if (isConstructor()) {
            return null;
        }
        PsiMethodStub psiMethodStub = (PsiMethodStub) getStub();
        if (psiMethodStub != null) {
            PsiType psiType = (PsiType) SoftReference.dereference(this.myCachedType);
            if (psiType != null) {
                return psiType;
            }
            PsiType psiTypeCreateTypeFromStub = JavaSharedImplUtil.createTypeFromStub(this, psiMethodStub.getReturnTypeText());
            this.myCachedType = new SoftReference<>(psiTypeCreateTypeFromStub);
            return psiTypeCreateTypeFromStub;
        }
        this.myCachedType = null;
        PsiTypeElement returnTypeElement = getReturnTypeElement();
        if (returnTypeElement != null) {
            return JavaSharedImplUtil.getType(returnTypeElement, getParameterList());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        PsiModifierList psiModifierList = (PsiModifierList) getRequiredStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
        if (psiModifierList == null) {
            $$$reportNull$$$0(8);
        }
        return psiModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        PsiParameterList psiParameterList = (PsiParameterList) getStubOrPsiChild(JavaStubElementTypes.PARAMETER_LIST);
        if (psiParameterList != null) {
            if (psiParameterList == null) {
                $$$reportNull$$$0(11);
            }
            return psiParameterList;
        }
        PsiParameterList psiParameterList2 = (PsiParameterList) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6656x9c5ff833();
            }
        });
        if (psiParameterList2 == null) {
            $$$reportNull$$$0(10);
        }
        return psiParameterList2;
    }

    /* JADX INFO: renamed from: lambda$getParameterList$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiMethodImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6656x9c5ff833() {
        LightParameterListBuilder lightParameterListBuilder = new LightParameterListBuilder(getManager(), getLanguage()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            public String getText() {
                return null;
            }
        };
        PsiClass psiClassMo6092getContainingClass = getContainingClass();
        if (psiClassMo6092getContainingClass != null) {
            for (PsiRecordComponent psiRecordComponent : psiClassMo6092getContainingClass.getRecordComponents()) {
                String name2 = psiRecordComponent.getName();
                if (name2 != null) {
                    lightParameterListBuilder.addParameter(new LightCompactConstructorParameter(name2, psiRecordComponent.getType(), this, psiRecordComponent));
                }
            }
        }
        return CachedValueProvider.Result.create(lightParameterListBuilder, this, PsiModificationTracker.MODIFICATION_COUNT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        Stream map;
        PsiReferenceList psiReferenceList = (PsiReferenceList) getStubOrPsiChild(JavaStubElementTypes.THROWS_LIST);
        if (psiReferenceList != null) {
            if (psiReferenceList == null) {
                $$$reportNull$$$0(12);
            }
            return psiReferenceList;
        }
        PsiMethodStub psiMethodStub = (PsiMethodStub) getStub();
        if (psiMethodStub != null) {
            map = psiMethodStub.getChildrenStubs().stream().map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PsiMethodImpl.lambda$getThrowsList$1((StubElement) obj);
                }
            });
        } else {
            map = Stream.of((Object[]) getChildren()).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PsiMethodImpl.lambda$getThrowsList$2((PsiElement) obj);
                }
            });
        }
        throw new AssertionError("Missing throws list, file=" + getContainingFile() + " children:\n" + ((String) map.collect(Collectors.joining("\n"))));
    }

    static /* synthetic */ String lambda$getThrowsList$1(StubElement stubElement) {
        return stubElement.getClass().getSimpleName() + " : " + stubElement.getStubType();
    }

    static /* synthetic */ String lambda$getThrowsList$2(PsiElement psiElement) {
        return psiElement.getClass().getSimpleName() + " : " + psiElement.getNode().getElementType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiCodeBlock getBody() {
        return (PsiCodeBlock) getNode().findChildByRoleAsPsiElement(17);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public CompositeElement getNode() {
        CompositeElement compositeElement = (CompositeElement) super.getNode();
        if (compositeElement == null) {
            $$$reportNull$$$0(13);
        }
        return compositeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return JavaStubImplUtil.isMemberDeprecated(this, (PsiMemberStub) getGreenStub());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        PsiMethodStub psiMethodStub = (PsiMethodStub) getGreenStub();
        if (psiMethodStub == null || psiMethodStub.hasDocComment()) {
            return (PsiDocComment) getNode().findChildByRoleAsPsiElement(7);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isConstructor() {
        PsiMethodStub psiMethodStub = (PsiMethodStub) getGreenStub();
        if (psiMethodStub != null) {
            return psiMethodStub.isConstructor();
        }
        return getNode().findChildByRole(10) == null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isVarArgs() {
        PsiMethodStub psiMethodStub = (PsiMethodStub) getGreenStub();
        if (psiMethodStub != null) {
            return psiMethodStub.isVarArgs();
        }
        return PsiImplUtil.isVarArgs(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(14);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitMethod(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiMethod:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(15);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(16);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(17);
        }
        return PsiImplUtil.processDeclarationsInMethod(this, psiScopeProcessor, resolveState, psiElement, psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public MethodSignature getSignature(PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(18);
        }
        if (psiSubstitutor == PsiSubstitutor.EMPTY) {
            MethodSignature methodSignature = (MethodSignature) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return this.f$0.m6657xf4d62047();
                }
            });
            if (methodSignature == null) {
                $$$reportNull$$$0(19);
            }
            return methodSignature;
        }
        MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, psiSubstitutor);
        if (methodSignatureBackedByPsiMethodCreate == null) {
            $$$reportNull$$$0(20);
        }
        return methodSignatureBackedByPsiMethodCreate;
    }

    /* JADX INFO: renamed from: lambda$getSignature$3$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiMethodImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6657xf4d62047() {
        return CachedValueProvider.Result.create(MethodSignatureBackedByPsiMethod.create(this, PsiSubstitutor.EMPTY), PsiModificationTracker.MODIFICATION_COUNT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getOriginalElement */
    public PsiElement getFunDelegate() {
        PsiElement originalElement;
        PsiMethod psiMethodFindMethodBySignature;
        PsiClass psiClassMo6092getContainingClass = getContainingClass();
        return (psiClassMo6092getContainingClass == null || (originalElement = psiClassMo6092getContainingClass.getFunDelegate()) == psiClassMo6092getContainingClass || (psiMethodFindMethodBySignature = ((PsiClass) originalElement).findMethodBySignature(this, false)) == null) ? this : psiMethodFindMethodBySignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, hasModifierProperty("abstract") ? PlatformIcons.ABSTRACT_METHOD_ICON : PlatformIcons.METHOD_ICON, ElementPresentationUtil.getFlags(this, false)));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isMethodEquivalentTo(this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope searchScope = (SearchScope) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6658x53af2bef();
            }
        });
        if (searchScope == null) {
            $$$reportNull$$$0(21);
        }
        return searchScope;
    }

    /* JADX INFO: renamed from: lambda$getUseScope$4$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiMethodImpl, reason: not valid java name */
    /* synthetic */ SearchScope m6658x53af2bef() throws RuntimeException {
        return PsiImplUtil.getMemberUseScope(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable
    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(22);
        }
        map.put("methodName", getName());
    }
}
