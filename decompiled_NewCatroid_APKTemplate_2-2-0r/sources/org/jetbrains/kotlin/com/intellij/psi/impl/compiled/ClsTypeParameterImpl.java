package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
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
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightEmptyImplementsList;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ClsTypeParameterImpl extends ClsRepositoryPsiElement<PsiTypeParameterStub> implements PsiTypeParameter {
    private final LightEmptyImplementsList myLightEmptyImplementsList;

    /* JADX WARN: Code duplicated, block: B:8:0x0010 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 35) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 30:
                                case 31:
                                case 32:
                                    break;
                                default:
                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                    break;
                            }
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 35) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 30:
                                case 31:
                                case 32:
                                    i2 = 2;
                                    break;
                                default:
                                    i2 = 3;
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 30:
            case 31:
            case 32:
            case 35:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsTypeParameterImpl";
                break;
            case 7:
                objArr[0] = "baseClass";
                break;
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
            case 26:
                objArr[0] = "name";
                break;
            case 27:
                objArr[0] = "visitor";
                break;
            case 28:
                objArr[0] = "buffer";
                break;
            case 29:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 33:
            case 34:
                objArr[0] = "qualifiedName";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i != 35) {
            switch (i) {
                case 1:
                    objArr[1] = "getFields";
                    break;
                case 2:
                    objArr[1] = "getMethods";
                    break;
                case 3:
                    objArr[1] = "findMethodsBySignature";
                    break;
                case 4:
                    objArr[1] = "findMethodsByName";
                    break;
                case 5:
                    objArr[1] = "findMethodsAndTheirSubstitutorsByName";
                    break;
                case 6:
                    objArr[1] = "getAllMethodsAndTheirSubstitutors";
                    break;
                default:
                    switch (i) {
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
                        default:
                            switch (i) {
                                case 30:
                                    objArr[1] = "getChildren";
                                    break;
                                case 31:
                                    objArr[1] = "getUseScope";
                                    break;
                                case 32:
                                    objArr[1] = "getAnnotations";
                                    break;
                                default:
                                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsTypeParameterImpl";
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getApplicableAnnotations";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 30:
            case 31:
            case 32:
            case 35:
                break;
            case 7:
                objArr[2] = "isInheritor";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "processDeclarations";
                break;
            case 11:
                objArr[2] = "setName";
                break;
            case 26:
                objArr[2] = "hasModifierProperty";
                break;
            case 27:
                objArr[2] = "accept";
                break;
            case 28:
                objArr[2] = "appendMirrorText";
                break;
            case 29:
                objArr[2] = "setMirror";
                break;
            case 33:
                objArr[2] = "findAnnotation";
                break;
            case 34:
                objArr[2] = "addAnnotation";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 35) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    break;
                default:
                    switch (i) {
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                            break;
                        default:
                            switch (i) {
                                case 30:
                                case 31:
                                case 32:
                                    break;
                                default:
                                    throw new IllegalArgumentException(str2);
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsTypeParameterImpl(PsiTypeParameterStub psiTypeParameterStub) {
        super(psiTypeParameterStub);
        if (psiTypeParameterStub == null) {
            $$$reportNull$$$0(0);
        }
        this.myLightEmptyImplementsList = new LightEmptyImplementsList(getManager());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        PsiField[] psiFieldArr = PsiField.EMPTY_ARRAY;
        if (psiFieldArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiFieldArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(2);
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
            $$$reportNull$$$0(3);
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
            $$$reportNull$$$0(4);
        }
        return psiMethodArrFindMethodsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(String str, boolean z) {
        List<Pair<PsiMethod, PsiSubstitutor>> listFindMethodsAndTheirSubstitutorsByName = PsiClassImplUtil.findMethodsAndTheirSubstitutorsByName(this, str, z);
        if (listFindMethodsAndTheirSubstitutorsByName == null) {
            $$$reportNull$$$0(5);
        }
        return listFindMethodsAndTheirSubstitutorsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> getAllMethodsAndTheirSubstitutors() {
        List<Pair<PsiMethod, PsiSubstitutor>> allWithSubstitutorsByMap = PsiClassImplUtil.getAllWithSubstitutorsByMap(this, PsiClassImplUtil.MemberType.METHOD);
        if (allWithSubstitutorsByMap == null) {
            $$$reportNull$$$0(6);
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
            $$$reportNull$$$0(7);
        }
        return InheritanceImplUtil.isInheritor(this, psiClass, z);
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
        return getStub().getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        throw new IncorrectOperationException("Cannot change compiled classes");
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
        PsiReferenceList psi = ((PsiClassReferenceListStub) getStub().findChildStubByType(JavaStubElementTypes.EXTENDS_BOUND_LIST)).getPsi();
        if (psi == null) {
            $$$reportNull$$$0(13);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        return this.myLightEmptyImplementsList;
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(28);
        }
        Iterator<PsiAnnotationStub> it = getStub().getAnnotations().iterator();
        while (it.hasNext()) {
            sb.append(it.next().getText()).append(" ");
        }
        sb.append(get$name());
        appendText(getExtendsList(), i, sb);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(29);
        }
        setMirrorCheckingType(treeElement, null);
        setMirror(getExtendsList(), ((PsiTypeParameter) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getExtendsList());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRepositoryPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] psiElementArr = (PsiElement[]) ArrayUtil.append((PsiReferenceList[]) getAnnotations(), getExtendsList(), (Class<PsiReferenceList>) PsiElement.class);
        if (psiElementArr == null) {
            $$$reportNull$$$0(30);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public PsiTypeParameterListOwner getOwner() {
        return (PsiTypeParameterListOwner) getLightParent().getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter
    public int getIndex() {
        PsiTypeParameterStub stub = getStub();
        return stub.getParentStub().getChildrenStubs().indexOf(stub);
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
            $$$reportNull$$$0(31);
        }
        return classUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter, org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) ContainerUtil.map2Array(getStub().getAnnotations(), PsiAnnotation.class, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeParameterImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiAnnotationStub) obj).getPsi();
            }
        });
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(32);
        }
        return psiAnnotationArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(final String str) {
        if (str == null) {
            $$$reportNull$$$0(33);
        }
        return (PsiAnnotation) ContainerUtil.find(getAnnotations(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeParameterImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return ((PsiAnnotation) obj).hasQualifiedName(str);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] annotations = getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(35);
        }
        return annotations;
    }
}
