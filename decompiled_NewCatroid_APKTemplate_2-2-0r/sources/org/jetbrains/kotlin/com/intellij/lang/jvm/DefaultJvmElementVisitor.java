package org.jetbrains.kotlin.com.intellij.lang.jvm;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: loaded from: classes3.dex */
public interface DefaultJvmElementVisitor<T> extends JvmElementVisitor<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "member";
                break;
            case 2:
                objArr[0] = "field";
                break;
            case 3:
                objArr[0] = "method";
                break;
            case 4:
                objArr[0] = "parameter";
                break;
            case 5:
                objArr[0] = "clazz";
                break;
            case 6:
                objArr[0] = "typeParameter";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/jvm/DefaultJvmElementVisitor";
        switch (i) {
            case 1:
                objArr[2] = "visitMember";
                break;
            case 2:
                objArr[2] = "visitField";
                break;
            case 3:
                objArr[2] = "visitMethod";
                break;
            case 4:
                objArr[2] = "visitParameter";
                break;
            case 5:
                objArr[2] = "visitClass";
                break;
            case 6:
                objArr[2] = "visitTypeParameter";
                break;
            default:
                objArr[2] = "visitElement";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default T visitElement(JvmElement jvmElement) {
        if (jvmElement != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.jvm.JvmElementVisitor
    default T visitTypeParameter(JvmTypeParameter jvmTypeParameter) {
        if (jvmTypeParameter == null) {
            $$$reportNull$$$0(6);
        }
        return visitElement(jvmTypeParameter);
    }
}
