package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmElementVisitor;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeParameter;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmReferenceType;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTypeParameter extends JvmTypeParameter, PsiAnnotationOwner, PsiClass {
    public static final PsiTypeParameter[] EMPTY_ARRAY = new PsiTypeParameter[0];
    public static final ArrayFactory<PsiTypeParameter> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiTypeParameter.lambda$static$0(i);
        }
    };

    /* JADX WARN: Code duplicated, block: B:23:0x0038  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "fqn";
        } else if (i != 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter";
        } else if (i == 2) {
            objArr[1] = "getBounds";
        } else if (i != 3) {
            objArr[1] = "getAnnotations";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter";
        }
        if (i == 1) {
            objArr[2] = "hasAnnotation";
        } else if (i == 3) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    PsiReferenceList getExtendsList();

    int getIndex();

    @Override // 
    PsiTypeParameterListOwner getOwner();

    static /* synthetic */ PsiTypeParameter[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiTypeParameter[i];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmModifiersOwner
    default PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] annotations = super.getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(0);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    default boolean hasAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return super.hasAnnotation(str);
    }

    default JvmReferenceType[] getBounds() {
        PsiClassType[] referencedTypes = getExtendsList().getReferencedTypes();
        if (referencedTypes == null) {
            $$$reportNull$$$0(2);
        }
        return referencedTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeParameter
    default <T> T accept(JvmElementVisitor<T> jvmElementVisitor) {
        if (jvmElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return (T) super.accept(jvmElementVisitor);
    }
}
