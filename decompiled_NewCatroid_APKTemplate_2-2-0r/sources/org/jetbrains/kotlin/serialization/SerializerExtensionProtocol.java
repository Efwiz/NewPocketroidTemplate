package org.jetbrains.kotlin.serialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;

/* JADX INFO: compiled from: SerializerExtensionProtocol.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\b\u0016\u0018\u00002\u00020\u0001B©\u0003\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u0005\u0012\u0018\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u001a\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u0005\u0012\u001a\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u0005\u0012\u001a\u0010\u0017\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u0005\u0012\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001b0\u0005\u0012\u0018\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u0018\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005\u0012\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\u0002\u0010\"R#\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001d\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b%\u0010$R#\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b&\u0010$R#\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b'\u0010$R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R#\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b*\u0010$R%\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b+\u0010$R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b,\u0010$R#\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b-\u0010$R#\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b.\u0010$R%\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b/\u0010$R%\u0010\u0017\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b0\u0010$R%\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b1\u0010$R#\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b2\u0010$R#\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b3\u0010$R#\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b4\u0010$R#\u0010 \u001a\u0014\u0012\u0004\u0012\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b5\u0010$¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "", "extensionRegistry", "Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "packageFqName", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "", "constructorAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "classAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "functionAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "functionExtensionReceiverAnnotation", "propertyAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "propertyGetterAnnotation", "propertySetterAnnotation", "propertyExtensionReceiverAnnotation", "propertyBackingFieldAnnotation", "propertyDelegatedFieldAnnotation", "enumEntryAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry;", "compileTimeValue", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value;", "parameterAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "typeAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "typeParameterAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "(Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;)V", "getClassAnnotation", "()Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;", "getCompileTimeValue", "getConstructorAnnotation", "getEnumEntryAnnotation", "getExtensionRegistry", "()Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "getFunctionAnnotation", "getFunctionExtensionReceiverAnnotation", "getPackageFqName", "getParameterAnnotation", "getPropertyAnnotation", "getPropertyBackingFieldAnnotation", "getPropertyDelegatedFieldAnnotation", "getPropertyExtensionReceiverAnnotation", "getPropertyGetterAnnotation", "getPropertySetterAnnotation", "getTypeAnnotation", "getTypeParameterAnnotation", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SerializerExtensionProtocol {
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation;
    private final ExtensionRegistryLite extensionRegistry;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionExtensionReceiverAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyBackingFieldAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyDelegatedFieldAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyExtensionReceiverAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation;
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation;

    public SerializerExtensionProtocol(ExtensionRegistryLite extensionRegistry, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> generatedExtension, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension2, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension3, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension4, GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue, GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation, GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation) {
        Intrinsics.checkNotNullParameter(extensionRegistry, "extensionRegistry");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(constructorAnnotation, "constructorAnnotation");
        Intrinsics.checkNotNullParameter(classAnnotation, "classAnnotation");
        Intrinsics.checkNotNullParameter(functionAnnotation, "functionAnnotation");
        Intrinsics.checkNotNullParameter(propertyAnnotation, "propertyAnnotation");
        Intrinsics.checkNotNullParameter(propertyGetterAnnotation, "propertyGetterAnnotation");
        Intrinsics.checkNotNullParameter(propertySetterAnnotation, "propertySetterAnnotation");
        Intrinsics.checkNotNullParameter(enumEntryAnnotation, "enumEntryAnnotation");
        Intrinsics.checkNotNullParameter(compileTimeValue, "compileTimeValue");
        Intrinsics.checkNotNullParameter(parameterAnnotation, "parameterAnnotation");
        Intrinsics.checkNotNullParameter(typeAnnotation, "typeAnnotation");
        Intrinsics.checkNotNullParameter(typeParameterAnnotation, "typeParameterAnnotation");
        this.extensionRegistry = extensionRegistry;
        this.packageFqName = packageFqName;
        this.constructorAnnotation = constructorAnnotation;
        this.classAnnotation = classAnnotation;
        this.functionAnnotation = functionAnnotation;
        this.functionExtensionReceiverAnnotation = generatedExtension;
        this.propertyAnnotation = propertyAnnotation;
        this.propertyGetterAnnotation = propertyGetterAnnotation;
        this.propertySetterAnnotation = propertySetterAnnotation;
        this.propertyExtensionReceiverAnnotation = generatedExtension2;
        this.propertyBackingFieldAnnotation = generatedExtension3;
        this.propertyDelegatedFieldAnnotation = generatedExtension4;
        this.enumEntryAnnotation = enumEntryAnnotation;
        this.compileTimeValue = compileTimeValue;
        this.parameterAnnotation = parameterAnnotation;
        this.typeAnnotation = typeAnnotation;
        this.typeParameterAnnotation = typeParameterAnnotation;
    }

    public final ExtensionRegistryLite getExtensionRegistry() {
        return this.extensionRegistry;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> getPackageFqName() {
        return this.packageFqName;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> getConstructorAnnotation() {
        return this.constructorAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> getClassAnnotation() {
        return this.classAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> getFunctionAnnotation() {
        return this.functionAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> getFunctionExtensionReceiverAnnotation() {
        return this.functionExtensionReceiverAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyAnnotation() {
        return this.propertyAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyGetterAnnotation() {
        return this.propertyGetterAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertySetterAnnotation() {
        return this.propertySetterAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyExtensionReceiverAnnotation() {
        return this.propertyExtensionReceiverAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyBackingFieldAnnotation() {
        return this.propertyBackingFieldAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyDelegatedFieldAnnotation() {
        return this.propertyDelegatedFieldAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> getEnumEntryAnnotation() {
        return this.enumEntryAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> getCompileTimeValue() {
        return this.compileTimeValue;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> getParameterAnnotation() {
        return this.parameterAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> getTypeAnnotation() {
        return this.typeAnnotation;
    }

    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> getTypeParameterAnnotation() {
        return this.typeParameterAnnotation;
    }
}
