package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmClass;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmClassKind;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmMethod;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmReferenceType;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiClass extends JvmClass, PomRenameableTarget<PsiElement>, PsiDocCommentOwner, PsiModifierListOwner, PsiNameIdentifierOwner, PsiQualifiedNamedElement, PsiTarget, PsiTypeParameterListOwner {
    public static final PsiClass[] EMPTY_ARRAY = new PsiClass[0];
    public static final ArrayFactory<PsiClass> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiClass$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiClass.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 2 ? 2 : 3];
        if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiClass";
        } else {
            objArr[0] = "methodName";
        }
        if (i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiClass";
        } else if (i == 3) {
            objArr[1] = "findMethodsByName";
        } else if (i == 4) {
            objArr[1] = "getClassKind";
        } else if (i == 5) {
            objArr[1] = "getInterfaceTypes";
        } else if (i != 6) {
            objArr[1] = "getPermitsListTypes";
        } else {
            objArr[1] = "getRecordComponents";
        }
        if (i == 2) {
            objArr[2] = "findMethodsByName";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalArgumentException(str2);
        }
    }

    PsiField findFieldByName(String str, boolean z);

    PsiClass findInnerClassByName(String str, boolean z);

    PsiMethod findMethodBySignature(PsiMethod psiMethod, boolean z);

    List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(String str, boolean z);

    PsiMethod[] findMethodsByName(String str, boolean z);

    PsiMethod[] findMethodsBySignature(PsiMethod psiMethod, boolean z);

    PsiField[] getAllFields();

    PsiClass[] getAllInnerClasses();

    PsiMethod[] getAllMethods();

    List<Pair<PsiMethod, PsiSubstitutor>> getAllMethodsAndTheirSubstitutors();

    PsiMethod[] getConstructors();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass, reason: merged with bridge method [inline-methods] */
    PsiClass mo6092getContainingClass();

    PsiReferenceList getExtendsList();

    PsiClassType[] getExtendsListTypes();

    @Override // 
    PsiField[] getFields();

    PsiReferenceList getImplementsList();

    PsiClassType[] getImplementsListTypes();

    PsiClassInitializer[] getInitializers();

    @Override // 
    PsiClass[] getInnerClasses();

    PsiClass[] getInterfaces();

    PsiElement getLBrace();

    @Override // 
    PsiMethod[] getMethods();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    PsiIdentifier mo7392getNameIdentifier();

    default PsiReferenceList getPermitsList() {
        return null;
    }

    String getQualifiedName();

    PsiElement getRBrace();

    default PsiRecordHeader getRecordHeader() {
        return null;
    }

    PsiElement getScope();

    PsiClass getSuperClass();

    PsiClassType[] getSuperTypes();

    PsiClass[] getSupers();

    Collection<HierarchicalMethodSignature> getVisibleSignatures();

    boolean isAnnotationType();

    boolean isEnum();

    boolean isInheritor(PsiClass psiClass, boolean z);

    boolean isInheritorDeep(PsiClass psiClass, PsiClass psiClass2);

    boolean isInterface();

    default boolean isRecord() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    PsiElement setName(String str) throws IncorrectOperationException;

    static /* synthetic */ PsiClass[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiClass[i];
    }

    default PsiClassType[] getPermitsListTypes() {
        PsiReferenceList permitsList = getPermitsList();
        if (permitsList != null) {
            PsiClassType[] referencedTypes = permitsList.getReferencedTypes();
            if (referencedTypes == null) {
                $$$reportNull$$$0(0);
            }
            return referencedTypes;
        }
        PsiClassType[] psiClassTypeArr = PsiClassType.EMPTY_ARRAY;
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiClassTypeArr;
    }

    default JvmMethod[] findMethodsByName(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiMethod[] psiMethodArrFindMethodsByName = findMethodsByName(str, false);
        if (psiMethodArrFindMethodsByName == null) {
            $$$reportNull$$$0(3);
        }
        return psiMethodArrFindMethodsByName;
    }

    default JvmClassKind getClassKind() {
        JvmClassKind jvmClassKind = PsiJvmConversionHelper.getJvmClassKind(this);
        if (jvmClassKind == null) {
            $$$reportNull$$$0(4);
        }
        return jvmClassKind;
    }

    default JvmReferenceType getSuperClassType() {
        return PsiJvmConversionHelper.getClassSuperType(this);
    }

    default JvmReferenceType[] getInterfaceTypes() {
        JvmReferenceType[] classInterfaces = PsiJvmConversionHelper.getClassInterfaces(this);
        if (classInterfaces == null) {
            $$$reportNull$$$0(5);
        }
        return classInterfaces;
    }

    default PsiRecordComponent[] getRecordComponents() {
        PsiRecordComponent[] psiRecordComponentArr = PsiRecordComponent.EMPTY_ARRAY;
        if (psiRecordComponentArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiRecordComponentArr;
    }
}
