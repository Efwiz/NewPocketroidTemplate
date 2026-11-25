package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractLightClass extends LightElement implements PsiClass {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 13:
            case 14:
            case 15:
            case 23:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
                i2 = 2;
                break;
            case 13:
            case 14:
            case 15:
            case 23:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/AbstractLightClass";
                break;
            case 13:
                objArr[0] = "processor";
                break;
            case 14:
                objArr[0] = "state";
                break;
            case 15:
                objArr[0] = "place";
                break;
            case 23:
                objArr[0] = "baseClass";
                break;
            case 25:
            default:
                objArr[0] = "name";
                break;
            case 26:
                objArr[0] = "visitor";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getTypeParameters";
                break;
            case 2:
                objArr[1] = "getExtendsListTypes";
                break;
            case 3:
                objArr[1] = "getImplementsListTypes";
                break;
            case 4:
                objArr[1] = "getInterfaces";
                break;
            case 5:
                objArr[1] = "getNavigationElement";
                break;
            case 6:
                objArr[1] = "getSupers";
                break;
            case 7:
                objArr[1] = "getSuperTypes";
                break;
            case 8:
                objArr[1] = "getFields";
                break;
            case 9:
                objArr[1] = "getMethods";
                break;
            case 10:
                objArr[1] = "getConstructors";
                break;
            case 11:
                objArr[1] = "getInnerClasses";
                break;
            case 12:
                objArr[1] = "getInitializers";
                break;
            case 13:
            case 14:
            case 15:
            case 23:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/AbstractLightClass";
                break;
            case 16:
                objArr[1] = "getAllFields";
                break;
            case 17:
                objArr[1] = "getAllMethods";
                break;
            case 18:
                objArr[1] = "getAllInnerClasses";
                break;
            case 19:
                objArr[1] = "findMethodsBySignature";
                break;
            case 20:
                objArr[1] = "findMethodsByName";
                break;
            case 21:
                objArr[1] = "findMethodsAndTheirSubstitutorsByName";
                break;
            case 22:
                objArr[1] = "getAllMethodsAndTheirSubstitutors";
                break;
            case 24:
                objArr[1] = "getVisibleSignatures";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "processDeclarations";
                break;
            case 23:
                objArr[2] = "isInheritor";
                break;
            case 25:
                objArr[2] = "setName";
                break;
            case 26:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "hasModifierProperty";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
                throw new IllegalStateException(str2);
            case 13:
            case 14:
            case 15:
            case 23:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract PsiElement copy();

    /* JADX INFO: renamed from: getDelegate */
    public abstract PsiClass mo6090getDelegate();

    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return null;
    }

    protected AbstractLightClass(PsiManager psiManager, Language language) {
        super(psiManager, language);
    }

    protected AbstractLightClass(PsiManager psiManager) {
        super(psiManager, JavaLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return mo6090getDelegate().getName();
    }

    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return mo6090getDelegate().mo6095getModifierList();
    }

    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return mo6090getDelegate().hasModifierProperty(str);
    }

    public boolean isDeprecated() {
        return mo6090getDelegate().isDeprecated();
    }

    public boolean hasTypeParameters() {
        return PsiImplUtil.hasTypeParameters(this);
    }

    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return mo6090getDelegate().mo6093getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = mo6090getDelegate().getTypeParameters();
        if (typeParameters == null) {
            $$$reportNull$$$0(1);
        }
        return typeParameters;
    }

    public String getQualifiedName() {
        return mo6090getDelegate().getQualifiedName();
    }

    public boolean isInterface() {
        return mo6090getDelegate().isInterface();
    }

    public boolean isAnnotationType() {
        return mo6090getDelegate().isAnnotationType();
    }

    public boolean isEnum() {
        return mo6090getDelegate().isEnum();
    }

    public PsiReferenceList getExtendsList() {
        return mo6090getDelegate().getExtendsList();
    }

    public PsiReferenceList getImplementsList() {
        return mo6090getDelegate().getImplementsList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getExtendsListTypes() {
        PsiClassType[] extendsListTypes = PsiClassImplUtil.getExtendsListTypes(this);
        if (extendsListTypes == null) {
            $$$reportNull$$$0(2);
        }
        return extendsListTypes;
    }

    public PsiClassType[] getImplementsListTypes() {
        PsiClassType[] implementsListTypes = PsiClassImplUtil.getImplementsListTypes(this);
        if (implementsListTypes == null) {
            $$$reportNull$$$0(3);
        }
        return implementsListTypes;
    }

    public PsiClass getSuperClass() {
        return mo6090getDelegate().getSuperClass();
    }

    public PsiClass[] getInterfaces() {
        PsiClass[] interfaces = mo6090getDelegate().getInterfaces();
        if (interfaces == null) {
            $$$reportNull$$$0(4);
        }
        return interfaces;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement navigationElement = mo6090getDelegate().getNavigationElement();
        if (navigationElement == null) {
            $$$reportNull$$$0(5);
        }
        return navigationElement;
    }

    public PsiClass[] getSupers() {
        PsiClass[] supers = mo6090getDelegate().getSupers();
        if (supers == null) {
            $$$reportNull$$$0(6);
        }
        return supers;
    }

    public PsiClassType[] getSuperTypes() {
        PsiClassType[] superTypes = mo6090getDelegate().getSuperTypes();
        if (superTypes == null) {
            $$$reportNull$$$0(7);
        }
        return superTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        PsiField[] fields = mo6090getDelegate().getFields();
        if (fields == null) {
            $$$reportNull$$$0(8);
        }
        return fields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        PsiMethod[] methods = mo6090getDelegate().getMethods();
        if (methods == null) {
            $$$reportNull$$$0(9);
        }
        return methods;
    }

    public PsiMethod[] getConstructors() {
        PsiMethod[] constructors = mo6090getDelegate().getConstructors();
        if (constructors == null) {
            $$$reportNull$$$0(10);
        }
        return constructors;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        PsiClass[] innerClasses = mo6090getDelegate().getInnerClasses();
        if (innerClasses == null) {
            $$$reportNull$$$0(11);
        }
        return innerClasses;
    }

    public PsiClassInitializer[] getInitializers() {
        PsiClassInitializer[] initializers = mo6090getDelegate().getInitializers();
        if (initializers == null) {
            $$$reportNull$$$0(12);
        }
        return initializers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(13);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(14);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(15);
        }
        return PsiClassImplUtil.processDeclarationsInClass(this, psiScopeProcessor, resolveState, null, psiElement, psiElement2, PsiUtil.getLanguageLevel(psiElement2), false);
    }

    public PsiField[] getAllFields() {
        PsiField[] allFields = mo6090getDelegate().getAllFields();
        if (allFields == null) {
            $$$reportNull$$$0(16);
        }
        return allFields;
    }

    public PsiMethod[] getAllMethods() {
        PsiMethod[] allMethods = mo6090getDelegate().getAllMethods();
        if (allMethods == null) {
            $$$reportNull$$$0(17);
        }
        return allMethods;
    }

    public PsiClass[] getAllInnerClasses() {
        PsiClass[] allInnerClasses = mo6090getDelegate().getAllInnerClasses();
        if (allInnerClasses == null) {
            $$$reportNull$$$0(18);
        }
        return allInnerClasses;
    }

    public PsiField findFieldByName(String str, boolean z) {
        return PsiClassImplUtil.findFieldByName(this, str, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod findMethodBySignature(PsiMethod psiMethod, boolean z) {
        return PsiClassImplUtil.findMethodBySignature(this, psiMethod, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsBySignature(PsiMethod psiMethod, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsBySignature = PsiClassImplUtil.findMethodsBySignature(this, psiMethod, z);
        if (psiMethodArrFindMethodsBySignature == null) {
            $$$reportNull$$$0(19);
        }
        return psiMethodArrFindMethodsBySignature;
    }

    public PsiMethod[] findMethodsByName(String str, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsByName = PsiClassImplUtil.findMethodsByName(this, str, z);
        if (psiMethodArrFindMethodsByName == null) {
            $$$reportNull$$$0(20);
        }
        return psiMethodArrFindMethodsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(String str, boolean z) {
        List<Pair<PsiMethod, PsiSubstitutor>> listFindMethodsAndTheirSubstitutorsByName = PsiClassImplUtil.findMethodsAndTheirSubstitutorsByName(this, str, z);
        if (listFindMethodsAndTheirSubstitutorsByName == null) {
            $$$reportNull$$$0(21);
        }
        return listFindMethodsAndTheirSubstitutorsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> getAllMethodsAndTheirSubstitutors() {
        List<Pair<PsiMethod, PsiSubstitutor>> allWithSubstitutorsByMap = PsiClassImplUtil.getAllWithSubstitutorsByMap(this, PsiClassImplUtil.MemberType.METHOD);
        if (allWithSubstitutorsByMap == null) {
            $$$reportNull$$$0(22);
        }
        return allWithSubstitutorsByMap;
    }

    public PsiClass findInnerClassByName(String str, boolean z) {
        return mo6090getDelegate().findInnerClassByName(str, z);
    }

    public PsiElement getLBrace() {
        return mo6090getDelegate().getLBrace();
    }

    public PsiElement getRBrace() {
        return mo6090getDelegate().getRBrace();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return mo6090getDelegate().mo7392getNameIdentifier();
    }

    public PsiElement getScope() {
        return mo6090getDelegate().getScope();
    }

    public boolean isInheritor(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(23);
        }
        return mo6090getDelegate().isInheritor(psiClass, z);
    }

    public boolean isInheritorDeep(PsiClass psiClass, PsiClass psiClass2) {
        return mo6090getDelegate().isInheritorDeep(psiClass, psiClass2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return mo6090getDelegate().mo6092getContainingClass();
    }

    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = mo6090getDelegate().getVisibleSignatures();
        if (visibleSignatures == null) {
            $$$reportNull$$$0(24);
        }
        return visibleSignatures;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        return mo6090getDelegate().setName(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiClass:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return mo6090getDelegate().getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(26);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitClass(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return mo6090getDelegate().getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return mo6090getDelegate();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return mo6090getDelegate().isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return this == psiElement || ((psiElement instanceof AbstractLightClass) && mo6090getDelegate().isEquivalentTo(((AbstractLightClass) psiElement).mo6090getDelegate())) || mo6090getDelegate().isEquivalentTo(psiElement);
    }
}
