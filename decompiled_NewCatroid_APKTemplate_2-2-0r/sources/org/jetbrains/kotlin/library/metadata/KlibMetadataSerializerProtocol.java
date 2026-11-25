package org.jetbrains.kotlin.library.metadata;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;

/* JADX INFO: compiled from: KlibMetadataSerializerProtocol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataSerializerProtocol;", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "()V", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataSerializerProtocol extends SerializerExtensionProtocol {
    public static final KlibMetadataSerializerProtocol INSTANCE = new KlibMetadataSerializerProtocol();

    /* JADX WARN: Illegal instructions before constructor call */
    private KlibMetadataSerializerProtocol() {
        ExtensionRegistryLite extensionRegistryLiteNewInstance = ExtensionRegistryLite.newInstance();
        KlibMetadataProtoBuf.registerAllExtensions(extensionRegistryLiteNewInstance);
        Intrinsics.checkNotNullExpressionValue(extensionRegistryLiteNewInstance, "apply(...)");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName = KlibMetadataProtoBuf.packageFqName;
        Intrinsics.checkNotNullExpressionValue(packageFqName, "packageFqName");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation = KlibMetadataProtoBuf.constructorAnnotation;
        Intrinsics.checkNotNullExpressionValue(constructorAnnotation, "constructorAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation = KlibMetadataProtoBuf.classAnnotation;
        Intrinsics.checkNotNullExpressionValue(classAnnotation, "classAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation = KlibMetadataProtoBuf.functionAnnotation;
        Intrinsics.checkNotNullExpressionValue(functionAnnotation, "functionAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> generatedExtension = KlibMetadataProtoBuf.functionExtensionReceiverAnnotation;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation = KlibMetadataProtoBuf.propertyAnnotation;
        Intrinsics.checkNotNullExpressionValue(propertyAnnotation, "propertyAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation = KlibMetadataProtoBuf.propertyGetterAnnotation;
        Intrinsics.checkNotNullExpressionValue(propertyGetterAnnotation, "propertyGetterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation = KlibMetadataProtoBuf.propertySetterAnnotation;
        Intrinsics.checkNotNullExpressionValue(propertySetterAnnotation, "propertySetterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension2 = KlibMetadataProtoBuf.propertyExtensionReceiverAnnotation;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension3 = KlibMetadataProtoBuf.propertyBackingFieldAnnotation;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension4 = KlibMetadataProtoBuf.propertyDelegatedFieldAnnotation;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation = KlibMetadataProtoBuf.enumEntryAnnotation;
        Intrinsics.checkNotNullExpressionValue(enumEntryAnnotation, "enumEntryAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue = KlibMetadataProtoBuf.compileTimeValue;
        Intrinsics.checkNotNullExpressionValue(compileTimeValue, "compileTimeValue");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation = KlibMetadataProtoBuf.parameterAnnotation;
        Intrinsics.checkNotNullExpressionValue(parameterAnnotation, "parameterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation = KlibMetadataProtoBuf.typeAnnotation;
        Intrinsics.checkNotNullExpressionValue(typeAnnotation, "typeAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation = KlibMetadataProtoBuf.typeParameterAnnotation;
        Intrinsics.checkNotNullExpressionValue(typeParameterAnnotation, "typeParameterAnnotation");
        super(extensionRegistryLiteNewInstance, packageFqName, constructorAnnotation, classAnnotation, functionAnnotation, generatedExtension, propertyAnnotation, propertyGetterAnnotation, propertySetterAnnotation, generatedExtension2, generatedExtension3, generatedExtension4, enumEntryAnnotation, compileTimeValue, parameterAnnotation, typeAnnotation, typeParameterAnnotation);
    }
}
