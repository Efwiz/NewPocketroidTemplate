package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJShellImportHolder;
import org.jetbrains.kotlin.com.intellij.psi.PsiJShellRootClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.InheritanceImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJShellRootClassImpl extends ASTWrapperPsiElement implements PsiJShellRootClass {
    private final String myName;
    private final String myQName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
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
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 22:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 17:
            case 19:
            case 21:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
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
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 22:
                i2 = 2;
                break;
            case 17:
            case 19:
            case 21:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "state";
                break;
            case 2:
                objArr[0] = "place";
                break;
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
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 22:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJShellRootClassImpl";
                break;
            case 17:
                objArr[0] = "baseClass";
                break;
            case 19:
            case 21:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "processor";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getName";
                break;
            case 4:
                objArr[1] = "getExtendsListTypes";
                break;
            case 5:
                objArr[1] = "getImplementsListTypes";
                break;
            case 6:
                objArr[1] = "getInterfaces";
                break;
            case 7:
                objArr[1] = "getSupers";
                break;
            case 8:
                objArr[1] = "getSuperTypes";
                break;
            case 9:
                objArr[1] = "getConstructors";
                break;
            case 10:
                objArr[1] = "getAllFields";
                break;
            case 11:
                objArr[1] = "getAllMethods";
                break;
            case 12:
                objArr[1] = "getAllInnerClasses";
                break;
            case 13:
                objArr[1] = "findMethodsBySignature";
                break;
            case 14:
                objArr[1] = "findMethodsByName";
                break;
            case 15:
                objArr[1] = "findMethodsAndTheirSubstitutorsByName";
                break;
            case 16:
                objArr[1] = "getAllMethodsAndTheirSubstitutors";
                break;
            case 17:
            case 19:
            case 21:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJShellRootClassImpl";
                break;
            case 18:
                objArr[1] = "getVisibleSignatures";
                break;
            case 20:
                objArr[1] = "getTypeParameters";
                break;
            case 22:
                objArr[1] = "findChildren";
                break;
        }
        switch (i) {
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
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 22:
                break;
            case 17:
                objArr[2] = "isInheritor";
                break;
            case 19:
                objArr[2] = "setName";
                break;
            case 21:
                objArr[2] = "hasModifierProperty";
                break;
            default:
                objArr[2] = "processDeclarations";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
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
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 22:
                throw new IllegalStateException(str2);
            case 17:
            case 19:
            case 21:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiJavaToken getLBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiJavaToken getRBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass getSuperClass() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str != null) {
            return null;
        }
        $$$reportNull$$$0(19);
        return null;
    }

    public PsiJShellRootClassImpl(ASTNode aSTNode, int i) {
        super(aSTNode);
        String str = "$$jshell_root_class$$" + i;
        this.myName = str;
        this.myQName = "REPL." + str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJShellRootClass
    public PsiJShellImportHolder[] getSnippets() {
        return (PsiJShellImportHolder[]) findChildren(PsiJShellImportHolder.class, PsiJShellImportHolder.EMPTY_ARRAY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(0);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(2);
        }
        return PsiClassImplUtil.processDeclarationsInClass(this, psiScopeProcessor, resolveState, null, psiElement, psiElement2, PsiUtil.getLanguageLevel(psiElement2), false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        return this.myQName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getExtendsListTypes() {
        PsiClassType[] psiClassTypeArr = PsiClassType.EMPTY_ARRAY;
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiClassTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getImplementsListTypes() {
        PsiClassType[] psiClassTypeArr = PsiClassType.EMPTY_ARRAY;
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiClassTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInterfaces() {
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (psiClassArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getSupers() {
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (psiClassArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        PsiClassType[] psiClassTypeArr = PsiClassType.EMPTY_ARRAY;
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(8);
        }
        return psiClassTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        return (PsiField[]) findChildren(PsiField.class, PsiField.EMPTY_ARRAY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        return (PsiMethod[]) findChildren(PsiMethod.class, PsiMethod.EMPTY_ARRAY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getConstructors() {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(9);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        return (PsiClass[]) findChildren(PsiClass.class, PsiClass.EMPTY_ARRAY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        return (PsiClassInitializer[]) findChildren(PsiClassInitializer.class, PsiClassInitializer.EMPTY_ARRAY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getAllFields() {
        PsiField[] allFields = PsiClassImplUtil.getAllFields(this);
        if (allFields == null) {
            $$$reportNull$$$0(10);
        }
        return allFields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getAllMethods() {
        PsiMethod[] allMethods = PsiClassImplUtil.getAllMethods(this);
        if (allMethods == null) {
            $$$reportNull$$$0(11);
        }
        return allMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getAllInnerClasses() {
        PsiClass[] allInnerClasses = PsiClassImplUtil.getAllInnerClasses(this);
        if (allInnerClasses == null) {
            $$$reportNull$$$0(12);
        }
        return allInnerClasses;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
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
            $$$reportNull$$$0(13);
        }
        return psiMethodArrFindMethodsBySignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsByName(String str, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsByName = PsiClassImplUtil.findMethodsByName(this, str, z);
        if (psiMethodArrFindMethodsByName == null) {
            $$$reportNull$$$0(14);
        }
        return psiMethodArrFindMethodsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(String str, boolean z) {
        List<Pair<PsiMethod, PsiSubstitutor>> listFindMethodsAndTheirSubstitutorsByName = PsiClassImplUtil.findMethodsAndTheirSubstitutorsByName(this, str, z);
        if (listFindMethodsAndTheirSubstitutorsByName == null) {
            $$$reportNull$$$0(15);
        }
        return listFindMethodsAndTheirSubstitutorsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> getAllMethodsAndTheirSubstitutors() {
        List<Pair<PsiMethod, PsiSubstitutor>> allWithSubstitutorsByMap = PsiClassImplUtil.getAllWithSubstitutorsByMap(this, PsiClassImplUtil.MemberType.METHOD);
        if (allWithSubstitutorsByMap == null) {
            $$$reportNull$$$0(16);
        }
        return allWithSubstitutorsByMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass findInnerClassByName(String str, boolean z) {
        return PsiClassImplUtil.findInnerByName(this, str, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(17);
        }
        return InheritanceImplUtil.isInheritor(this, psiClass, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritorDeep(PsiClass psiClass, PsiClass psiClass2) {
        return InheritanceImplUtil.isInheritorDeep(this, psiClass, psiClass2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        if (visibleSignatures == null) {
            $$$reportNull$$$0(18);
        }
        return visibleSignatures;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] psiTypeParameterArr = PsiTypeParameter.EMPTY_ARRAY;
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(20);
        }
        return psiTypeParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        return "public".equals(str) || "final".equals(str);
    }

    private <T extends PsiElement> T[] findChildren(Class<T> cls, T[] tArr) {
        PsiElement[] childrenOfType = PsiTreeUtil.getChildrenOfType(this, cls);
        if (childrenOfType != null) {
            tArr = (T[]) childrenOfType;
        }
        if (tArr == null) {
            $$$reportNull$$$0(22);
        }
        return tArr;
    }
}
