package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.OriginInfoAwareElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
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
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LightMethodBuilder extends LightElement implements OriginInfoAwareElement, PsiMethod {
    private Icon myBaseIcon;
    private boolean myConstructor;
    private PsiClass myContainingClass;
    private String myMethodKind;
    private final PsiModifierList myModifierList;
    private final String myName;
    private String myOriginInfo;
    private final PsiParameterList myParameterList;
    private Computable<? extends PsiType> myReturnType;
    private final PsiReferenceList myThrowsList;
    private final PsiTypeParameterList myTypeParameterList;

    /* JADX WARN: Code duplicated, block: B:13:0x0022  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 6 && i != 8 && i != 16 && i != 3 && i != 4 && i != 25 && i != 26) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 6 && i != 8 && i != 16 && i != 3 && i != 4 && i != 25 && i != 26) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightMethodBuilder";
                break;
            case 2:
            case 5:
            case 10:
            case 12:
            case 14:
            default:
                objArr[0] = "name";
                break;
            case 7:
                objArr[0] = "returnType";
                break;
            case 9:
                objArr[0] = "parameter";
                break;
            case 11:
            case 13:
            case 15:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 17:
                objArr[0] = "substitutor";
                break;
            case 24:
                objArr[0] = "visitor";
                break;
        }
        if (i == 1) {
            objArr[1] = "getTypeParameters";
        } else if (i == 6) {
            objArr[1] = "getModifierList";
        } else if (i == 8) {
            objArr[1] = "getParameterList";
        } else if (i == 16) {
            objArr[1] = "getThrowsList";
        } else if (i == 3) {
            objArr[1] = "getName";
        } else if (i == 4) {
            objArr[1] = "getHierarchicalMethodSignature";
        } else if (i == 25) {
            objArr[1] = "getUseScope";
        } else if (i != 26) {
            switch (i) {
                case 18:
                    objArr[1] = "getSignature";
                    break;
                case 19:
                case 20:
                case 21:
                    objArr[1] = "findSuperMethods";
                    break;
                case 22:
                    objArr[1] = "findSuperMethodSignaturesIncludingStatic";
                    break;
                case 23:
                    objArr[1] = "findDeepestSuperMethods";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightMethodBuilder";
                    break;
            }
        } else {
            objArr[1] = "getParameterTypes";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
                break;
            case 2:
                objArr[2] = "setName";
                break;
            case 5:
                objArr[2] = "hasModifierProperty";
                break;
            case 7:
                objArr[2] = "setMethodReturnType";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "addParameter";
                break;
            case 17:
                objArr[2] = "getSignature";
                break;
            case 24:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6 && i != 8 && i != 16 && i != 3 && i != 4 && i != 25 && i != 26) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiCodeBlock getBody() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiTypeElement getReturnTypeElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    public LightMethodBuilder(PsiClass psiClass, Language language) {
        this(psiClass.getManager(), language, psiClass.getMyName());
        setContainingClass(psiClass);
    }

    public LightMethodBuilder(PsiManager psiManager, String str) {
        this(psiManager, JavaLanguage.INSTANCE, str);
    }

    public LightMethodBuilder(PsiManager psiManager, Language language, String str) {
        this(psiManager, language, str, new LightParameterListBuilder(psiManager, language), new LightModifierList(psiManager, language, new String[0]));
    }

    public LightMethodBuilder(PsiManager psiManager, Language language, String str, PsiParameterList psiParameterList, PsiModifierList psiModifierList) {
        this(psiManager, language, str, psiParameterList, psiModifierList, new LightReferenceListBuilder(psiManager, language, PsiReferenceList.Role.THROWS_LIST), new LightTypeParameterListBuilder(psiManager, language));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightMethodBuilder(PsiManager psiManager, Language language, String str, PsiParameterList psiParameterList, PsiModifierList psiModifierList, PsiReferenceList psiReferenceList, PsiTypeParameterList psiTypeParameterList) {
        super(psiManager, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myMethodKind = "LightMethodBuilder";
        this.myName = str;
        this.myParameterList = psiParameterList;
        this.myModifierList = psiModifierList;
        this.myThrowsList = psiReferenceList;
        this.myTypeParameterList = psiTypeParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return PsiImplUtil.hasTypeParameters(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = PsiImplUtil.getTypeParameters(this);
        if (typeParameters == null) {
            $$$reportNull$$$0(1);
        }
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return this.myTypeParameterList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return PsiImplUtil.isDeprecatedByDocTag(this) || PsiImplUtil.isDeprecatedByAnnotation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiClass psiClass = this.myContainingClass;
        throw new UnsupportedOperationException("Please don't rename light methods: writable=" + isWritable() + "; class=" + getClass() + "; name=" + getMyName() + "; inClass=" + (psiClass == null ? "null" : psiClass.getQualifiedName()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public HierarchicalMethodSignature getHierarchicalMethodSignature() {
        HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(4);
        }
        return hierarchicalMethodSignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return mo6095getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        PsiModifierList psiModifierList = this.myModifierList;
        if (psiModifierList == null) {
            $$$reportNull$$$0(6);
        }
        return psiModifierList;
    }

    public LightMethodBuilder addModifiers(String... strArr) {
        for (String str : strArr) {
            addModifier(str);
        }
        return this;
    }

    public LightMethodBuilder addModifier(String str) {
        ((LightModifierList) this.myModifierList).addModifier(str);
        return this;
    }

    public LightMethodBuilder setModifiers(String... strArr) {
        ((LightModifierList) this.myModifierList).clearModifiers();
        addModifiers(strArr);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        Computable<? extends PsiType> computable = this.myReturnType;
        if (computable == null) {
            return null;
        }
        return computable.compute();
    }

    public LightMethodBuilder setMethodReturnType(Computable<? extends PsiType> computable) {
        this.myReturnType = computable;
        return this;
    }

    public LightMethodBuilder setMethodReturnType(PsiType psiType) {
        return setMethodReturnType(new Computable.PredefinedValueComputable(psiType));
    }

    public LightMethodBuilder setMethodReturnType(final String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        final NotNullLazyValue notNullLazyValueLazy = NotNullLazyValue.lazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6621x810b053b(str);
            }
        });
        Objects.requireNonNull(notNullLazyValueLazy);
        return setMethodReturnType(new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return (PsiType) notNullLazyValueLazy.getValue();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setMethodReturnType$0$org-jetbrains-kotlin-com-intellij-psi-impl-light-LightMethodBuilder, reason: not valid java name */
    /* synthetic */ PsiClassType m6621x810b053b(String str) {
        return JavaPsiFacade.getElementFactory(getProject()).createTypeByFQClassName(str, getResolveScope());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        PsiParameterList psiParameterList = this.myParameterList;
        if (psiParameterList == null) {
            $$$reportNull$$$0(8);
        }
        return psiParameterList;
    }

    public LightMethodBuilder addParameter(PsiParameter psiParameter) {
        if (psiParameter == null) {
            $$$reportNull$$$0(9);
        }
        ((LightParameterListBuilder) this.myParameterList).addParameter(psiParameter);
        return this;
    }

    public LightMethodBuilder addParameter(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (str2 == null) {
            $$$reportNull$$$0(11);
        }
        return addParameter(str, JavaPsiFacade.getElementFactory(getProject()).createTypeFromText(str2, this));
    }

    public LightMethodBuilder addParameter(String str, PsiType psiType) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (psiType == null) {
            $$$reportNull$$$0(13);
        }
        return addParameter(new LightParameter(str, psiType, this, JavaLanguage.INSTANCE));
    }

    public LightMethodBuilder addParameter(String str, PsiType psiType, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (psiType == null) {
            $$$reportNull$$$0(15);
        }
        return addParameter(new LightParameter(str, (!z || (psiType instanceof PsiEllipsisType)) ? psiType : new PsiEllipsisType(psiType), this, JavaLanguage.INSTANCE, z));
    }

    public LightMethodBuilder addException(PsiClassType psiClassType) {
        ((LightReferenceListBuilder) this.myThrowsList).addReference(psiClassType);
        return this;
    }

    public LightMethodBuilder addException(String str) {
        ((LightReferenceListBuilder) this.myThrowsList).addReference(str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        PsiReferenceList psiReferenceList = this.myThrowsList;
        if (psiReferenceList == null) {
            $$$reportNull$$$0(16);
        }
        return psiReferenceList;
    }

    public LightMethodBuilder setConstructor(boolean z) {
        this.myConstructor = z;
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isConstructor() {
        return this.myConstructor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isVarArgs() {
        return PsiImplUtil.isVarArgs(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public MethodSignature getSignature(PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(17);
        }
        MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, psiSubstitutor);
        if (methodSignatureBackedByPsiMethodCreate == null) {
            $$$reportNull$$$0(18);
        }
        return methodSignatureBackedByPsiMethodCreate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods() {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(19);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(boolean z) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, z);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(20);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(PsiClass psiClass) {
        PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, psiClass);
        if (psiMethodArrFindSuperMethods == null) {
            $$$reportNull$$$0(21);
        }
        return psiMethodArrFindSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean z) {
        List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = PsiSuperMethodImplUtil.findSuperMethodSignaturesIncludingStatic(this, z);
        if (listFindSuperMethodSignaturesIncludingStatic == null) {
            $$$reportNull$$$0(22);
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
            $$$reportNull$$$0(23);
        }
        return psiMethodArrFindDeepestSuperMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(24);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitMethod(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return this.myContainingClass;
    }

    public LightMethodBuilder setContainingClass(PsiClass psiClass) {
        this.myContainingClass = psiClass;
        return this;
    }

    public LightMethodBuilder setMethodKind(String str) {
        this.myMethodKind = str;
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return this.myMethodKind + ":" + getMyName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        Icon icon = this.myBaseIcon;
        if (icon == null) {
            icon = hasModifierProperty("abstract") ? PlatformIcons.ABSTRACT_METHOD_ICON : PlatformIcons.METHOD_ICON;
        }
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, icon, ElementPresentationUtil.getFlags(this, false)));
    }

    public LightMethodBuilder setBaseIcon(Icon icon) {
        this.myBaseIcon = icon;
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isMethodEquivalentTo(this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope memberUseScope = PsiImplUtil.getMemberUseScope(this);
        if (memberUseScope == null) {
            $$$reportNull$$$0(25);
        }
        return memberUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiClass psiClassMo6092getContainingClass = mo6092getContainingClass();
        if (psiClassMo6092getContainingClass == null) {
            return null;
        }
        return psiClassMo6092getContainingClass.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        PsiElement navigationElement = getNavigationElement();
        if (navigationElement != this) {
            return navigationElement;
        }
        PsiClass psiClassMo6092getContainingClass = mo6092getContainingClass();
        return psiClassMo6092getContainingClass != null ? psiClassMo6092getContainingClass : getContainingFile();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LightMethodBuilder lightMethodBuilder = (LightMethodBuilder) obj;
        return this.myConstructor == lightMethodBuilder.myConstructor && Objects.equals(this.myContainingClass, lightMethodBuilder.myContainingClass) && this.myMethodKind.equals(lightMethodBuilder.myMethodKind) && this.myName.equals(lightMethodBuilder.myName) && getParameterTypes().equals(lightMethodBuilder.getParameterTypes()) && Objects.equals(getReturnType(), lightMethodBuilder.getReturnType());
    }

    public int hashCode() {
        return Objects.hash(this.myName, getReturnType(), Boolean.valueOf(this.myConstructor), this.myMethodKind, this.myContainingClass, getParameterTypes());
    }

    private List<PsiType> getParameterTypes() {
        List<PsiType> map = ContainerUtil.map(getParameterList().getParameters(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiParameter) obj).getType();
            }
        });
        if (map == null) {
            $$$reportNull$$$0(26);
        }
        return map;
    }

    public LightMethodBuilder addTypeParameter(PsiTypeParameter psiTypeParameter) {
        ((LightTypeParameterListBuilder) this.myTypeParameterList).addParameter(new LightTypeParameter(psiTypeParameter));
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.OriginInfoAwareElement
    public String getOriginInfo() {
        return this.myOriginInfo;
    }

    public void setOriginInfo(String str) {
        this.myOriginInfo = str;
    }
}
