package org.jetbrains.kotlin.codegen.signature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.load.kotlin.JvmDescriptorTypeWriter;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public class JvmSignatureWriter extends JvmDescriptorTypeWriter<Type> {
    private JvmMethodParameterKind currentParameterKind;
    private int currentSignatureSize;
    private Type jvmReturnType;
    private final List<JvmMethodParameterSignature> kotlinParameterTypes;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "asmType";
        } else if (i == 2) {
            objArr[0] = "projectionKind";
        } else if (i != 3) {
            objArr[0] = "objectType";
        } else {
            objArr[0] = "name";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/signature/JvmSignatureWriter";
        if (i == 1) {
            objArr[2] = "writeAsmType";
        } else if (i == 2) {
            objArr[2] = "writeTypeArgument";
        } else if (i != 3) {
            objArr[2] = "writeClass";
        } else {
            objArr[2] = "makeJvmMethodSignature";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public String makeJavaGenericSignature() {
        return null;
    }

    public boolean skipGenericSignature() {
        return true;
    }

    public void writeClassBound() {
    }

    public void writeClassBoundEnd() {
    }

    public void writeClassEnd() {
    }

    public void writeFormalTypeParameter(String str) {
    }

    public void writeInnerClass(String str) {
    }

    public void writeInterface() {
    }

    public void writeInterfaceBound() {
    }

    public void writeInterfaceBoundEnd() {
    }

    public void writeInterfaceEnd() {
    }

    public void writeReturnType() {
    }

    public void writeSuperclass() {
    }

    public void writeSuperclassEnd() {
    }

    public void writeTypeArgument(Variance variance) {
        if (variance == null) {
            $$$reportNull$$$0(2);
        }
    }

    public void writeTypeArgumentEnd() {
    }

    public void writeUnboundedWildcard() {
    }

    public JvmSignatureWriter() {
        super(AsmTypeFactory.INSTANCE);
        this.kotlinParameterTypes = new ArrayList();
        this.currentSignatureSize = 0;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmDescriptorTypeWriter
    public void writeClass(Type type) {
        if (type == null) {
            $$$reportNull$$$0(0);
        }
        writeClassBegin(type);
        writeClassEnd();
    }

    public void writeAsmType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(1);
        }
        int sort = type.getSort();
        if (sort == 9) {
            writeArrayType();
            writeAsmType(AsmUtil.correctElementType(type));
            writeArrayEnd();
        } else if (sort == 10) {
            writeClassBegin(type);
            writeClassEnd();
        } else {
            writeJvmTypeAsIs(type);
        }
    }

    public void writeClassBegin(Type type) {
        writeJvmTypeAsIs(type);
    }

    public void writeOuterClassBegin(Type type, String str) {
        writeJvmTypeAsIs(type);
    }

    public void writeParametersStart() {
        clearCurrentType();
    }

    public void writeParameterType(JvmMethodParameterKind jvmMethodParameterKind) {
        this.currentParameterKind = jvmMethodParameterKind;
    }

    public void writeParameterTypeEnd() {
        this.kotlinParameterTypes.add(new JvmMethodParameterSignature(getJvmCurrentType(), this.currentParameterKind));
        this.currentSignatureSize += getJvmCurrentType().getSize();
        this.currentParameterKind = null;
        clearCurrentType();
    }

    public void writeReturnTypeEnd() {
        this.jvmReturnType = getJvmCurrentType();
        clearCurrentType();
    }

    public JvmMethodGenericSignature makeJvmMethodSignature(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        ArrayList arrayList = new ArrayList(this.kotlinParameterTypes.size());
        Iterator<JvmMethodParameterSignature> it = this.kotlinParameterTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getAsmType());
        }
        return new JvmMethodGenericSignature(new Method(str, this.jvmReturnType, (Type[]) arrayList.toArray(new Type[arrayList.size()])), this.kotlinParameterTypes, makeJavaGenericSignature());
    }

    public int getCurrentSignatureSize() {
        return this.currentSignatureSize;
    }

    public String toString() {
        return "empty";
    }
}
