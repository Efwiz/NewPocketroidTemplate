package org.jetbrains.kotlin.resolve.jvm.jvmSignature;

import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes6.dex */
public final class JvmMethodParameterSignature {
    private final Type asmType;
    private final JvmMethodParameterKind kind;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = Namer.METADATA_CLASS_KIND;
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodParameterSignature";
        } else {
            objArr[0] = "asmType";
        }
        if (i == 2) {
            objArr[1] = "getAsmType";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodParameterSignature";
        } else {
            objArr[1] = "getKind";
        }
        if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public JvmMethodParameterSignature(Type type, JvmMethodParameterKind jvmMethodParameterKind) {
        if (type == null) {
            $$$reportNull$$$0(0);
        }
        if (jvmMethodParameterKind == null) {
            $$$reportNull$$$0(1);
        }
        this.asmType = type;
        this.kind = jvmMethodParameterKind;
    }

    public Type getAsmType() {
        Type type = this.asmType;
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        return type;
    }

    public JvmMethodParameterKind getKind() {
        JvmMethodParameterKind jvmMethodParameterKind = this.kind;
        if (jvmMethodParameterKind == null) {
            $$$reportNull$$$0(3);
        }
        return jvmMethodParameterKind;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JvmMethodParameterSignature)) {
            return false;
        }
        JvmMethodParameterSignature jvmMethodParameterSignature = (JvmMethodParameterSignature) obj;
        return this.asmType.equals(jvmMethodParameterSignature.asmType) && this.kind == jvmMethodParameterSignature.kind;
    }

    public int hashCode() {
        return (this.asmType.hashCode() * 31) + this.kind.hashCode();
    }

    public String toString() {
        return this.kind + " " + this.asmType;
    }
}
