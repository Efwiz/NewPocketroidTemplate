package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.Collection;
import java.util.List;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.InheritanceImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightEmptyImplementsList;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTypeParameterImpl extends JavaStubPsiElement<PsiTypeParameterStub> implements PsiTypeParameter {
    /* JADX WARN: Code duplicated, block: B:10:0x0019  */
    /* JADX WARN: Code duplicated, block: B:19:0x002b  */
    /* JADX WARN: Code duplicated, block: B:33:0x005a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 6 && i != 26 && i != 27 && i != 30 && i != 31) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 6 && i != 26 && i != 27 && i != 30 && i != 31) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
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
        if (i == 6) {
            objArr[0] = "baseClass";
        } else if (i == 26) {
            objArr[0] = "name";
        } else if (i == 27) {
            objArr[0] = "visitor";
        } else if (i != 30 && i != 31) {
            switch (i) {
                case 8:
                    objArr[0] = "processor";
                    break;
                case 9:
                    objArr[0] = "state";
                    break;
                case 10:
                    objArr[0] = "place";
                    break;
                case 11:
                    objArr[0] = "name";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTypeParameterImpl";
                    break;
            }
        } else {
            objArr[0] = "qualifiedName";
        }
        switch (i) {
            case 1:
                objArr[1] = "getMethods";
                break;
            case 2:
                objArr[1] = "findMethodsBySignature";
                break;
            case 3:
                objArr[1] = "findMethodsByName";
                break;
            case 4:
                objArr[1] = "findMethodsAndTheirSubstitutorsByName";
                break;
            case 5:
                objArr[1] = "getAllMethodsAndTheirSubstitutors";
                break;
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 26:
            case 27:
            case 30:
            case 31:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTypeParameterImpl";
                break;
            case 7:
                objArr[1] = "getNameIdentifier";
                break;
            case 12:
                objArr[1] = "getConstructors";
                break;
            case 13:
                objArr[1] = "getExtendsList";
                break;
            case 14:
                objArr[1] = "getExtendsListTypes";
                break;
            case 15:
                objArr[1] = "getImplementsListTypes";
                break;
            case 16:
                objArr[1] = "getInnerClasses";
                break;
            case 17:
                objArr[1] = "getAllFields";
                break;
            case 18:
                objArr[1] = "getAllMethods";
                break;
            case 19:
                objArr[1] = "getAllInnerClasses";
                break;
            case 20:
                objArr[1] = "getInitializers";
                break;
            case 21:
                objArr[1] = "getTypeParameters";
                break;
            case 22:
                objArr[1] = "getInterfaces";
                break;
            case 23:
                objArr[1] = "getSupers";
                break;
            case 24:
                objArr[1] = "getSuperTypes";
                break;
            case 25:
                objArr[1] = "getVisibleSignatures";
                break;
            case 28:
                objArr[1] = "getUseScope";
                break;
            case 29:
                objArr[1] = "getAnnotations";
                break;
            case 32:
                objArr[1] = "getApplicableAnnotations";
                break;
            default:
                objArr[1] = "getFields";
                break;
        }
        if (i == 6) {
            objArr[2] = "isInheritor";
        } else if (i == 26) {
            objArr[2] = "hasModifierProperty";
        } else if (i == 27) {
            objArr[2] = "accept";
        } else if (i == 30) {
            objArr[2] = "findAnnotation";
        } else if (i != 31) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                    objArr[2] = "processDeclarations";
                    break;
                case 11:
                    objArr[2] = "setName";
                    break;
            }
        } else {
            objArr[2] = "addAnnotation";
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 26 && i != 27 && i != 30 && i != 31) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiJavaToken getLBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiJavaToken getRBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str != null) {
            return false;
        }
        $$$reportNull$$$0(26);
        return false;
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

    public PsiTypeParameterImpl(PsiTypeParameterStub psiTypeParameterStub) {
        super(psiTypeParameterStub, JavaStubElementTypes.TYPE_PARAMETER);
    }

    public PsiTypeParameterImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        PsiField[] psiFieldArr = PsiField.EMPTY_ARRAY;
        if (psiFieldArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiFieldArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod findMethodBySignature(PsiMethod psiMethod, boolean z) {
        return PsiClassImplUtil.findMethodBySignature(this, psiMethod, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsBySignature(PsiMethod psiMethod, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsBySignature = PsiClassImplUtil.findMethodsBySignature(this, psiMethod, z);
        if (psiMethodArrFindMethodsBySignature == null) {
            $$$reportNull$$$0(2);
        }
        return psiMethodArrFindMethodsBySignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField findFieldByName(String str, boolean z) {
        return PsiClassImplUtil.findFieldByName(this, str, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsByName(String str, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsByName = PsiClassImplUtil.findMethodsByName(this, str, z);
        if (psiMethodArrFindMethodsByName == null) {
            $$$reportNull$$$0(3);
        }
        return psiMethodArrFindMethodsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(String str, boolean z) {
        List<Pair<PsiMethod, PsiSubstitutor>> listFindMethodsAndTheirSubstitutorsByName = PsiClassImplUtil.findMethodsAndTheirSubstitutorsByName(this, str, z);
        if (listFindMethodsAndTheirSubstitutorsByName == null) {
            $$$reportNull$$$0(4);
        }
        return listFindMethodsAndTheirSubstitutorsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> getAllMethodsAndTheirSubstitutors() {
        List<Pair<PsiMethod, PsiSubstitutor>> allWithSubstitutorsByMap = PsiClassImplUtil.getAllWithSubstitutorsByMap(this, PsiClassImplUtil.MemberType.METHOD);
        if (allWithSubstitutorsByMap == null) {
            $$$reportNull$$$0(5);
        }
        return allWithSubstitutorsByMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass findInnerClassByName(String str, boolean z) {
        return PsiClassImplUtil.findInnerByName(this, str, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return getLightParent().getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritorDeep(PsiClass psiClass, PsiClass psiClass2) {
        return InheritanceImplUtil.isInheritorDeep(this, psiClass, psiClass2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        return InheritanceImplUtil.isInheritor(this, psiClass, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public PsiTypeParameterListOwner getOwner() {
        PsiElement parent = getLightParent();
        if (parent == null) {
            throw new PsiInvalidElementAccessException(this);
        }
        PsiElement lightParent = parent.getLightParent();
        if (lightParent instanceof PsiTypeParameterListOwner) {
            return (PsiTypeParameterListOwner) lightParent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public int getIndex() {
        PsiTypeParameterStub psiTypeParameterStub = (PsiTypeParameterStub) getGreenStub();
        if (psiTypeParameterStub != null) {
            return ((PsiTypeParameterListStub) psiTypeParameterStub.getParentStub()).getChildrenStubs().indexOf(psiTypeParameterStub);
        }
        int i = 0;
        for (PsiElement prevSibling = getPrevSibling(); prevSibling != null; prevSibling = prevSibling.getPrevSibling()) {
            if (prevSibling instanceof PsiTypeParameter) {
                i++;
            }
        }
        return i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifier = (PsiIdentifier) PsiTreeUtil.getRequiredChildOfType(this, PsiIdentifier.class);
        if (psiIdentifier == null) {
            $$$reportNull$$$0(7);
        }
        return psiIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(8);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(10);
        }
        return PsiClassImplUtil.processDeclarationsInClass(this, psiScopeProcessor, resolveState, null, psiElement, psiElement2, PsiUtil.getLanguageLevel(psiElement2), false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        PsiTypeParameterStub psiTypeParameterStub = (PsiTypeParameterStub) getGreenStub();
        if (psiTypeParameterStub != null) {
            return psiTypeParameterStub.getName();
        }
        return mo7392getNameIdentifier().getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        PsiImplUtil.setName(mo7392getNameIdentifier(), str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getConstructors() {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(12);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        PsiReferenceList psiReferenceList = (PsiReferenceList) getRequiredStubOrPsiChild(JavaStubElementTypes.EXTENDS_BOUND_LIST);
        if (psiReferenceList == null) {
            $$$reportNull$$$0(13);
        }
        return psiReferenceList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        return new LightEmptyImplementsList(getManager());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getExtendsListTypes() {
        PsiClassType[] extendsListTypes = PsiClassImplUtil.getExtendsListTypes(this);
        if (extendsListTypes == null) {
            $$$reportNull$$$0(14);
        }
        return extendsListTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getImplementsListTypes() {
        PsiClassType[] psiClassTypeArr = PsiClassType.EMPTY_ARRAY;
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(15);
        }
        return psiClassTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (psiClassArr == null) {
            $$$reportNull$$$0(16);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getAllFields() {
        PsiField[] psiFieldArr = PsiField.EMPTY_ARRAY;
        if (psiFieldArr == null) {
            $$$reportNull$$$0(17);
        }
        return psiFieldArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getAllMethods() {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(18);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getAllInnerClasses() {
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (psiClassArr == null) {
            $$$reportNull$$$0(19);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        PsiClassInitializer[] psiClassInitializerArr = PsiClassInitializer.EMPTY_ARRAY;
        if (psiClassInitializerArr == null) {
            $$$reportNull$$$0(20);
        }
        return psiClassInitializerArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] psiTypeParameterArr = PsiTypeParameter.EMPTY_ARRAY;
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(21);
        }
        return psiTypeParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass getSuperClass() {
        return PsiClassImplUtil.getSuperClass(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInterfaces() {
        PsiClass[] interfaces = PsiClassImplUtil.getInterfaces((PsiTypeParameter) this);
        if (interfaces == null) {
            $$$reportNull$$$0(22);
        }
        return interfaces;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getSupers() {
        PsiClass[] supers = PsiClassImplUtil.getSupers(this);
        if (supers == null) {
            $$$reportNull$$$0(23);
        }
        return supers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        PsiClassType[] superTypes = PsiClassImplUtil.getSuperTypes(this);
        if (superTypes == null) {
            $$$reportNull$$$0(24);
        }
        return superTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        if (visibleSignatures == null) {
            $$$reportNull$$$0(25);
        }
        return visibleSignatures;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(27);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeParameter(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeParameter:" + get$name();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return PsiClassImplUtil.getClassIcon(i, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isClassEquivalentTo(this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope classUseScope = PsiClassImplUtil.getClassUseScope(this);
        if (classUseScope == null) {
            $$$reportNull$$$0(28);
        }
        return classUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter, org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) getStubOrPsiChildren(JavaStubElementTypes.ANNOTATION, PsiAnnotation.ARRAY_FACTORY);
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(29);
        }
        return psiAnnotationArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        return PsiImplUtil.findAnnotation(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(31);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] annotations = getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(32);
        }
        return annotations;
    }
}
