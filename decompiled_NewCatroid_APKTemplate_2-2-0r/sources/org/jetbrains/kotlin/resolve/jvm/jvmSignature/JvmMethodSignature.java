package org.jetbrains.kotlin.resolve.jvm.jvmSignature;

import java.util.List;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes6.dex */
public class JvmMethodSignature {
    private final Method asmMethod;
    private final List<JvmMethodParameterSignature> valueParameters;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "valueParameters";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature";
        } else {
            objArr[0] = "asmMethod";
        }
        if (i == 2) {
            objArr[1] = "getAsmMethod";
        } else if (i == 3) {
            objArr[1] = "getValueParameters";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature";
        } else {
            objArr[1] = "getReturnType";
        }
        if (i != 2 && i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public JvmMethodSignature(Method method, List<JvmMethodParameterSignature> list) {
        if (method == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        this.asmMethod = method;
        this.valueParameters = list;
    }

    public Method getAsmMethod() {
        Method method = this.asmMethod;
        if (method == null) {
            $$$reportNull$$$0(2);
        }
        return method;
    }

    public List<JvmMethodParameterSignature> getValueParameters() {
        List<JvmMethodParameterSignature> list = this.valueParameters;
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        return list;
    }

    public Type getReturnType() {
        Type returnType = this.asmMethod.getReturnType();
        if (returnType == null) {
            $$$reportNull$$$0(4);
        }
        return returnType;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JvmMethodSignature)) {
            return false;
        }
        JvmMethodSignature jvmMethodSignature = (JvmMethodSignature) obj;
        return this.asmMethod.equals(jvmMethodSignature.asmMethod) && this.valueParameters.equals(jvmMethodSignature.valueParameters);
    }

    public int hashCode() {
        return (this.asmMethod.hashCode() * 31) + this.valueParameters.hashCode();
    }

    public String toString() {
        return this.asmMethod.toString();
    }
}
