package org.jetbrains.kotlin.com.intellij.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationArrayValue;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationAttributeValue;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: compiled from: PsiAnnotationAttributeValues.java */
/* JADX INFO: loaded from: classes3.dex */
class PsiAnnotationArrayValue extends PsiAnnotationAttributeValue<PsiArrayInitializerMemberValue> implements JvmAnnotationArrayValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "value";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotationArrayValue";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnnotationArrayValue";
        } else {
            objArr[1] = "getValues";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiAnnotationArrayValue(PsiArrayInitializerMemberValue psiArrayInitializerMemberValue) {
        super(psiArrayInitializerMemberValue);
        if (psiArrayInitializerMemberValue == null) {
            $$$reportNull$$$0(0);
        }
    }

    public List<JvmAnnotationAttributeValue> getValues() {
        List<JvmAnnotationAttributeValue> map = ContainerUtil.map(((PsiArrayInitializerMemberValue) this.myElement).getInitializers(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationArrayValue$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return PsiJvmConversionHelper.getAnnotationAttributeValue((PsiAnnotationMemberValue) obj);
            }
        });
        if (map == null) {
            $$$reportNull$$$0(1);
        }
        return map;
    }
}
