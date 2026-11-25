package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmAnnotation;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaData;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiAnnotation extends JvmAnnotation, PsiAnnotationMemberValue {
    public static final String DEFAULT_REFERENCED_METHOD_NAME = "value";
    public static final PsiAnnotation[] EMPTY_ARRAY = new PsiAnnotation[0];
    public static final ArrayFactory<PsiAnnotation> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiAnnotation.lambda$static$0(i);
        }
    };

    public enum TargetType {
        TYPE,
        FIELD,
        METHOD,
        PARAMETER,
        CONSTRUCTOR,
        LOCAL_VARIABLE,
        ANNOTATION_TYPE,
        PACKAGE,
        TYPE_USE,
        TYPE_PARAMETER,
        MODULE,
        RECORD_COMPONENT,
        UNKNOWN;

        public static final TargetType[] EMPTY_ARRAY = new TargetType[0];
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotation";
        } else {
            objArr[0] = "qualifiedName";
        }
        if (i != 1) {
            objArr[1] = "getAttributes";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotation";
        }
        if (i == 1) {
            objArr[2] = "hasQualifiedName";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    PsiAnnotationMemberValue findAttributeValue(String str);

    PsiAnnotationMemberValue findDeclaredAttributeValue(String str);

    @Deprecated
    default PsiMetaData getMetaData() {
        return null;
    }

    PsiJavaCodeReferenceElement getNameReferenceElement();

    PsiAnnotationOwner getOwner();

    PsiAnnotationParameterList getParameterList();

    String getQualifiedName();

    /* JADX INFO: renamed from: setDeclaredAttributeValue */
    <T extends PsiAnnotationMemberValue> T mo6097setDeclaredAttributeValue(String str, T t);

    static /* synthetic */ PsiAnnotation[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiAnnotation[i];
    }

    default PsiClass resolveAnnotationType() {
        PsiJavaCodeReferenceElement nameReferenceElement = getNameReferenceElement();
        PsiElement psiElementResolve = nameReferenceElement == null ? null : nameReferenceElement.resolve();
        if (psiElementResolve instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) psiElementResolve;
            if (psiClass.isAnnotationType()) {
                return psiClass;
            }
        }
        return null;
    }

    default List<JvmAnnotationAttribute> getAttributes() {
        List<JvmAnnotationAttribute> listAsList = Arrays.asList(getParameterList().getAttributes());
        if (listAsList == null) {
            $$$reportNull$$$0(0);
        }
        return listAsList;
    }

    default boolean hasQualifiedName(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str.equals(getQualifiedName());
    }
}
