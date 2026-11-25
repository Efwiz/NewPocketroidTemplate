package org.jetbrains.kotlin.codegen.signature;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.load.kotlin.JvmTypeFactory;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: AsmTypeFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016J\u0010\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000bH\u0016J\u0010\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016R\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/signature/AsmTypeFactory;", "Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;", "Lorg/jetbrains/org/objectweb/asm/Type;", "()V", "javaLangClassType", "getJavaLangClassType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "boxType", "possiblyPrimitiveType", "createFromString", "representation", "", "createObjectType", "internalName", "createPrimitiveType", "primitiveType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "toString", ModuleXmlParser.TYPE, "backend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AsmTypeFactory implements JvmTypeFactory<Type> {
    public static final AsmTypeFactory INSTANCE = new AsmTypeFactory();

    private AsmTypeFactory() {
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public Type boxType(Type possiblyPrimitiveType) {
        Intrinsics.checkNotNullParameter(possiblyPrimitiveType, "possiblyPrimitiveType");
        Type typeBoxType = AsmUtil.boxType(possiblyPrimitiveType);
        Intrinsics.checkNotNullExpressionValue(typeBoxType, "boxType(...)");
        return typeBoxType;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public Type createFromString(String representation) {
        Intrinsics.checkNotNullParameter(representation, "representation");
        Type type = Type.getType(representation);
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return type;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public Type createPrimitiveType(PrimitiveType primitiveType) {
        Intrinsics.checkNotNullParameter(primitiveType, "primitiveType");
        Type typeValueTypeForPrimitive = AsmTypes.valueTypeForPrimitive(primitiveType);
        Intrinsics.checkNotNullExpressionValue(typeValueTypeForPrimitive, "valueTypeForPrimitive(...)");
        return typeValueTypeForPrimitive;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public Type createObjectType(String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Type objectType = Type.getObjectType(internalName);
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        return objectType;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public String toString(Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        String descriptor = type.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        return descriptor;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public Type getJavaLangClassType() {
        Type JAVA_CLASS_TYPE = AsmTypes.JAVA_CLASS_TYPE;
        Intrinsics.checkNotNullExpressionValue(JAVA_CLASS_TYPE, "JAVA_CLASS_TYPE");
        return JAVA_CLASS_TYPE;
    }
}
