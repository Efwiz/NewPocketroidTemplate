package org.jetbrains.kotlin.resolve.jvm.jvmSignature;

import java.util.List;
import java.util.Objects;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes6.dex */
public class JvmMethodGenericSignature extends JvmMethodSignature {
    private final String genericsSignature;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "asmMethod";
        } else {
            objArr[0] = "valueParameters";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodGenericSignature";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmMethodGenericSignature(Method method, List<JvmMethodParameterSignature> list, String str) {
        super(method, list);
        if (method == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        this.genericsSignature = str;
    }

    public String getGenericsSignature() {
        return this.genericsSignature;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JvmMethodGenericSignature)) {
            return false;
        }
        JvmMethodGenericSignature jvmMethodGenericSignature = (JvmMethodGenericSignature) obj;
        return super.equals(jvmMethodGenericSignature) && Objects.equals(this.genericsSignature, jvmMethodGenericSignature.genericsSignature);
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature
    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        String str = this.genericsSignature;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }
}
