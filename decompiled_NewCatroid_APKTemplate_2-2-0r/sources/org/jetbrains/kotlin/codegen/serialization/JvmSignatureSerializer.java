package org.jetbrains.kotlin.codegen.serialization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.serialization.StringTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: JvmSignatureSerializer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J)\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00018\u00002\b\u0010\u0015\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0016\u001a\u00020\u0017¢\u0006\u0002\u0010\u0018JT\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0015\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u00132\b\u0010\u001d\u001a\u0004\u0018\u00010\u00132\b\u0010\u001e\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0010\u001a\u00020\u0011J\u001d\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010 \u001a\u00020\u000eH$¢\u0006\u0002\u0010!J\u001d\u0010\"\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00028\u00012\u0006\u0010 \u001a\u00020\u000eH&¢\u0006\u0002\u0010!R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/codegen/serialization/JvmSignatureSerializer;", "F", "P", "", "stringTable", "Lorg/jetbrains/kotlin/metadata/serialization/StringTable;", "(Lorg/jetbrains/kotlin/metadata/serialization/StringTable;)V", "getStringTable", "()Lorg/jetbrains/kotlin/metadata/serialization/StringTable;", "fieldSignature", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmFieldSignature;", "propertyName", "Lorg/jetbrains/kotlin/name/Name;", "fieldName", "", "fieldDesc", "requiresFieldSignature", "", "methodSignature", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmMethodSignature;", "descriptor", "descriptorName", "method", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/org/objectweb/asm/commons/Method;)Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmMethodSignature;", "propertySignature", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmPropertySignature;", "syntheticMethod", "delegateMethod", "getter", "setter", "requiresFunctionSignature", "desc", "(Ljava/lang/Object;Ljava/lang/String;)Z", "requiresPropertySignature", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JvmSignatureSerializer<F, P> {
    private final StringTable stringTable;

    protected abstract boolean requiresFunctionSignature(F descriptor, String desc);

    public abstract boolean requiresPropertySignature(P descriptor, String desc);

    public JvmSignatureSerializer(StringTable stringTable) {
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        this.stringTable = stringTable;
    }

    public final StringTable getStringTable() {
        return this.stringTable;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0041  */
    /* JADX WARN: Code duplicated, block: B:9:0x001e  */
    public final JvmProtoBuf.JvmMethodSignature methodSignature(F descriptor, Name descriptorName, Method method) {
        Intrinsics.checkNotNullParameter(method, "method");
        JvmProtoBuf.JvmMethodSignature.Builder builderNewBuilder = JvmProtoBuf.JvmMethodSignature.newBuilder();
        if (descriptor == null) {
            StringTable stringTable = this.stringTable;
            String name2 = method.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            builderNewBuilder.setName(stringTable.getStringIndex(name2));
        } else {
            if (!Intrinsics.areEqual(descriptorName != null ? descriptorName.asString() : null, method.getName())) {
                StringTable stringTable2 = this.stringTable;
                String name3 = method.getName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                builderNewBuilder.setName(stringTable2.getStringIndex(name3));
            }
        }
        if (descriptor != null) {
            String descriptor2 = method.getDescriptor();
            Intrinsics.checkNotNullExpressionValue(descriptor2, "getDescriptor(...)");
            if (requiresFunctionSignature(descriptor, descriptor2)) {
                StringTable stringTable3 = this.stringTable;
                String descriptor3 = method.getDescriptor();
                Intrinsics.checkNotNullExpressionValue(descriptor3, "getDescriptor(...)");
                builderNewBuilder.setDesc(stringTable3.getStringIndex(descriptor3));
            }
        } else {
            StringTable stringTable4 = this.stringTable;
            String descriptor4 = method.getDescriptor();
            Intrinsics.checkNotNullExpressionValue(descriptor4, "getDescriptor(...)");
            builderNewBuilder.setDesc(stringTable4.getStringIndex(descriptor4));
        }
        if (builderNewBuilder.hasName() || builderNewBuilder.hasDesc()) {
            return builderNewBuilder.build();
        }
        return null;
    }

    public final JvmProtoBuf.JvmPropertySignature propertySignature(Name descriptorName, String fieldName, String fieldDesc, JvmProtoBuf.JvmMethodSignature syntheticMethod, JvmProtoBuf.JvmMethodSignature delegateMethod, JvmProtoBuf.JvmMethodSignature getter, JvmProtoBuf.JvmMethodSignature setter, boolean requiresFieldSignature) {
        Intrinsics.checkNotNullParameter(descriptorName, "descriptorName");
        JvmProtoBuf.JvmPropertySignature.Builder builderNewBuilder = JvmProtoBuf.JvmPropertySignature.newBuilder();
        if (fieldDesc != null) {
            Intrinsics.checkNotNull(fieldName);
            builderNewBuilder.setField(fieldSignature(descriptorName, fieldName, fieldDesc, requiresFieldSignature, this.stringTable));
        }
        if (syntheticMethod != null) {
            builderNewBuilder.setSyntheticMethod(syntheticMethod);
        }
        if (delegateMethod != null) {
            builderNewBuilder.setDelegateMethod(delegateMethod);
        }
        if (getter != null) {
            builderNewBuilder.setGetter(getter);
        }
        if (setter != null) {
            builderNewBuilder.setSetter(setter);
        }
        JvmProtoBuf.JvmPropertySignature jvmPropertySignatureBuild = builderNewBuilder.build();
        if (jvmPropertySignatureBuild.getSerializedSize() > 0) {
            return jvmPropertySignatureBuild;
        }
        return null;
    }

    private final JvmProtoBuf.JvmFieldSignature fieldSignature(Name propertyName, String fieldName, String fieldDesc, boolean requiresFieldSignature, StringTable stringTable) {
        JvmProtoBuf.JvmFieldSignature.Builder builderNewBuilder = JvmProtoBuf.JvmFieldSignature.newBuilder();
        if (!Intrinsics.areEqual(propertyName.asString(), fieldName)) {
            builderNewBuilder.setName(stringTable.getStringIndex(fieldName));
        }
        if (requiresFieldSignature) {
            builderNewBuilder.setDesc(stringTable.getStringIndex(fieldDesc));
        }
        JvmProtoBuf.JvmFieldSignature jvmFieldSignatureBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(jvmFieldSignatureBuild, "build(...)");
        return jvmFieldSignatureBuild;
    }
}
