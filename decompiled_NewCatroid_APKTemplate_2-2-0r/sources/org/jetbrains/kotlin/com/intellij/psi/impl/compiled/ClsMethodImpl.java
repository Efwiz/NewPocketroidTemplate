package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.VolatileNullableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
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
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class ClsMethodImpl extends ClsMemberImpl<PsiMethodStub> implements PsiAnnotationMethod {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final NullableLazyValue<PsiAnnotationMemberValue> myDefaultValue;
    private final NotNullLazyValue<PsiTypeElement> myReturnType;

    /* JADX WARN: Code duplicated, block: B:13:0x001a  */
    /* JADX WARN: Code duplicated, block: B:7:0x000e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 8 && i != 11) {
            switch (i) {
                case 13:
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
        if (i != 8 && i != 11) {
            switch (i) {
                case 13:
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
        if (i == 8) {
            objArr[0] = "name";
        } else if (i != 11) {
            switch (i) {
                case 13:
                    objArr[0] = "buffer";
                    break;
                case 14:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
                case 15:
                    objArr[0] = "visitor";
                    break;
                case 16:
                    objArr[0] = "processor";
                    break;
                case 17:
                    objArr[0] = "state";
                    break;
                case 18:
                    objArr[0] = "place";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsMethodImpl";
                    break;
            }
        } else {
            objArr[0] = "substitutor";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[1] = "findSuperMethods";
                break;
            case 4:
                objArr[1] = "findSuperMethodSignaturesIncludingStatic";
                break;
            case 5:
                objArr[1] = "findDeepestSuperMethods";
                break;
            case 6:
                objArr[1] = "getHierarchicalMethodSignature";
                break;
            case 7:
                objArr[1] = "getModifierList";
                break;
            case 8:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsMethodImpl";
                break;
            case 9:
                objArr[1] = "getParameterList";
                break;
            case 10:
                objArr[1] = "getThrowsList";
                break;
            case 12:
                objArr[1] = "getSignature";
                break;
            case 19:
            case 20:
            case 21:
                objArr[1] = "getNavigationElement";
                break;
            case 22:
                objArr[1] = "getTypeParameters";
                break;
            case 23:
                objArr[1] = "getUseScope";
                break;
            default:
                objArr[1] = "getChildren";
                break;
        }
        if (i == 8) {
            objArr[2] = "hasModifierProperty";
        } else if (i != 11) {
            switch (i) {
                case 13:
                    objArr[2] = "appendMirrorText";
                    break;
                case 14:
                    objArr[2] = "setMirror";
                    break;
                case 15:
                    objArr[2] = "accept";
                    break;
                case 16:
                case 17:
                case 18:
                    objArr[2] = "processDeclarations";
                    break;
            }
        } else {
            objArr[2] = "getSignature";
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 11) {
            switch (i) {
                case 13:
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiCodeBlock getBody() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public /* bridge */ /* synthetic */ Object setName(String str) {
        return super.setName(str);
    }

    public ClsMethodImpl(PsiMethodStub psiMethodStub) {
        super(psiMethodStub);
        this.myReturnType = isConstructor() ? null : NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsMethodImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6592x377bd162();
            }
        });
        final String defaultValueText = ((PsiMethodStub) getStub()).getDefaultValueText();
        this.myDefaultValue = StringUtil.isEmptyOrSpaces(defaultValueText) ? null : new VolatileNullableLazyValue<PsiAnnotationMemberValue>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsMethodImpl.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.VolatileNullableLazyValue, org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
            public PsiAnnotationMemberValue compute() {
                return ClsParsingUtil.createMemberValueFromText(defaultValueText, ClsMethodImpl.this.getManager(), ClsMethodImpl.this);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsMethodImpl, reason: not valid java name */
    /* synthetic */ PsiTypeElement m6592x377bd162() {
        return new ClsTypeElementImpl(this, ((PsiMethodStub) getStub()).getReturnTypeText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRepositoryPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] children = getChildren(mo6091getDocComment(), getModifierList(), getReturnTypeElement(), mo7392getNameIdentifier(), getParameterList(), getThrowsList(), getDefaultValue());
        if (children == null) {
            $$$reportNull$$$0(0);
        }
        return children;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        return (PsiClass) getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods() {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(1);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(boolean z) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, z);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(2);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(PsiClass psiClass) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, psiClass);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(3);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean z) {
        List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = PsiSuperMethodImplUtil.findSuperMethodSignaturesIncludingStatic(this, z);
        if (listFindSuperMethodSignaturesIncludingStatic == null) {
            $$$reportNull$$$0(4);
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
            $$$reportNull$$$0(5);
        }
        return psiMethodArrFindDeepestSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public HierarchicalMethodSignature getHierarchicalMethodSignature() {
        HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(6);
        }
        return hierarchicalMethodSignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiTypeElement getReturnTypeElement() {
        NotNullLazyValue<PsiTypeElement> notNullLazyValue = this.myReturnType;
        if (notNullLazyValue != null) {
            return notNullLazyValue.getValue();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        PsiTypeElement returnTypeElement = getReturnTypeElement();
        if (returnTypeElement == null) {
            return null;
        }
        return returnTypeElement.getType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        PsiModifierList psi = ((PsiModifierListStub) Objects.requireNonNull((PsiModifierListStub) ((PsiMethodStub) getStub()).findChildStubByType(JavaStubElementTypes.MODIFIER_LIST))).getPsi();
        if (psi == null) {
            $$$reportNull$$$0(7);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        PsiParameterList psi = ((PsiParameterListStub) Objects.requireNonNull((PsiParameterListStub) ((PsiMethodStub) getStub()).findChildStubByType(JavaStubElementTypes.PARAMETER_LIST))).getPsi();
        if (psi == null) {
            $$$reportNull$$$0(9);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        PsiReferenceList psi = ((PsiClassReferenceListStub) Objects.requireNonNull((PsiClassReferenceListStub) ((PsiMethodStub) getStub()).findChildStubByType(JavaStubElementTypes.THROWS_LIST))).getPsi();
        if (psi == null) {
            $$$reportNull$$$0(10);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return ((PsiTypeParameterListStub) Objects.requireNonNull((PsiTypeParameterListStub) ((PsiMethodStub) getStub()).findChildStubByType(JavaStubElementTypes.TYPE_PARAMETER_LIST))).getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return ((PsiMethodStub) getStub()).isDeprecated() || PsiImplUtil.isDeprecatedByAnnotation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod
    public PsiAnnotationMemberValue getDefaultValue() {
        NullableLazyValue<PsiAnnotationMemberValue> nullableLazyValue = this.myDefaultValue;
        if (nullableLazyValue != null) {
            return nullableLazyValue.getValue();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isConstructor() {
        return ((PsiMethodStub) getStub()).isConstructor();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isVarArgs() {
        return ((PsiMethodStub) getStub()).isVarArgs();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public MethodSignature getSignature(PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(11);
        }
        MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, psiSubstitutor);
        if (methodSignatureBackedByPsiMethodCreate == null) {
            $$$reportNull$$$0(12);
        }
        return methodSignatureBackedByPsiMethodCreate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(13);
        }
        appendText(mo6091getDocComment(), i, sb, "go_to_next_line_and_indent");
        appendText(getModifierList(), i, sb, "");
        appendText(mo6093getTypeParameterList(), i, sb, " ");
        if (!isConstructor()) {
            appendText(getReturnTypeElement(), i, sb, " ");
        }
        appendText(mo7392getNameIdentifier(), i, sb, "");
        appendText(getParameterList(), i, sb);
        PsiReferenceList throwsList = getThrowsList();
        if (throwsList.getReferencedTypes().length > 0) {
            sb.append(io.netty.util.internal.StringUtil.SPACE);
            appendText(throwsList, i, sb);
        }
        PsiAnnotationMemberValue defaultValue = getDefaultValue();
        if (defaultValue != null) {
            sb.append(" default ");
            appendText(defaultValue, i, sb);
        }
        if (hasModifierProperty("abstract") || hasModifierProperty("native")) {
            sb.append(";");
        } else {
            sb.append(" { /* compiled code */ }");
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(14);
        }
        setMirrorCheckingType(treeElement, null);
        PsiMethod psiMethod = (PsiMethod) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirrorIfPresent(mo6091getDocComment(), psiMethod.mo6091getDocComment());
        setMirror(getModifierList(), psiMethod.getModifierList());
        setMirror(mo6093getTypeParameterList(), psiMethod.mo6093getTypeParameterList());
        if (!isConstructor()) {
            setMirror(getReturnTypeElement(), psiMethod.getReturnTypeElement());
        }
        setMirror(mo7392getNameIdentifier(), psiMethod.mo7392getNameIdentifier());
        setMirror(getParameterList(), psiMethod.getParameterList());
        setMirror(getThrowsList(), psiMethod.getThrowsList());
        PsiAnnotationMemberValue defaultValue = getDefaultValue();
        if (defaultValue != null) {
            setMirror(defaultValue, ((PsiAnnotationMethod) psiMethod).getDefaultValue());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(15);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitMethod(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(16);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(17);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(18);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        if (psiElement == null) {
            return true;
        }
        if (!PsiScopesUtil.walkChildrenScopes(this, psiScopeProcessor, resolveState, psiElement, psiElement2)) {
            return false;
        }
        for (PsiParameter psiParameter : getParameterList().getParameters()) {
            if (!psiScopeProcessor.execute(psiParameter, resolveState)) {
                return false;
            }
        }
        return true;
    }

    public PsiMethod getSourceMirrorMethod() {
        return (PsiMethod) CachedValuesManager.getProjectPsiDependentCache(this, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsMethodImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6591xa45bb374((ClsMethodImpl) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getSourceMirrorMethod$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsMethodImpl, reason: not valid java name */
    /* synthetic */ PsiMethod m6591xa45bb374(ClsMethodImpl clsMethodImpl) {
        return calcSourceMirrorMethod();
    }

    private PsiMethod calcSourceMirrorMethod() {
        PsiClass sourceMirrorClass = ((ClsClassImpl) getLightParent()).getSourceMirrorClass();
        if (sourceMirrorClass == null) {
            return null;
        }
        for (PsiMethod psiMethod : sourceMirrorClass.findMethodsByName(get$name(), false)) {
            if (MethodSignatureUtil.areParametersErasureEqual(this, psiMethod)) {
                return psiMethod;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        Iterator<ClsCustomNavigationPolicy> it = ClsCustomNavigationPolicy.EP_NAME.getExtensionList().iterator();
        while (it.hasNext()) {
            try {
                PsiElement navigationElement = it.next().getNavigationElement(this);
                if (navigationElement != null) {
                    if (navigationElement == null) {
                        $$$reportNull$$$0(19);
                    }
                    return navigationElement;
                }
            } catch (IndexNotReadyException unused) {
            }
        }
        try {
            PsiMethod sourceMirrorMethod = getSourceMirrorMethod();
            if (sourceMirrorMethod != null) {
                PsiElement navigationElement2 = sourceMirrorMethod.getNavigationElement();
                if (navigationElement2 == null) {
                    $$$reportNull$$$0(20);
                }
                return navigationElement2;
            }
        } catch (IndexNotReadyException unused2) {
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return PsiImplUtil.hasTypeParameters(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = PsiImplUtil.getTypeParameters(this);
        if (typeParameters == null) {
            $$$reportNull$$$0(22);
        }
        return typeParameters;
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
        SearchScope memberUseScope = PsiImplUtil.getMemberUseScope(this);
        if (memberUseScope == null) {
            $$$reportNull$$$0(23);
        }
        return memberUseScope;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiMethod:" + get$name();
    }
}
