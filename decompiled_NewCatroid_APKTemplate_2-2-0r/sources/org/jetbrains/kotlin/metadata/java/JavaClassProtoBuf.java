package org.jetbrains.kotlin.metadata.java;

import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.WireFormat;

/* JADX INFO: loaded from: classes5.dex */
public final class JavaClassProtoBuf {
    public static final int IS_PACKAGE_PRIVATE_CLASS_FIELD_NUMBER = 1000;
    public static final int IS_PACKAGE_PRIVATE_CONSTRUCTOR_FIELD_NUMBER = 1000;
    public static final int IS_PACKAGE_PRIVATE_FIELD_FIELD_NUMBER = 1001;
    public static final int IS_PACKAGE_PRIVATE_METHOD_FIELD_NUMBER = 1001;
    public static final int IS_STATIC_FIELD_FIELD_NUMBER = 1000;
    public static final int IS_STATIC_METHOD_FIELD_NUMBER = 1000;
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, Boolean> isStaticMethod = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), false, null, null, 1000, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, Boolean> isPackagePrivateMethod = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), false, null, null, 1001, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, Boolean> isStaticField = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), false, null, null, 1000, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, Boolean> isPackagePrivateField = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), false, null, null, 1001, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Boolean> isPackagePrivateClass = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), false, null, null, 1000, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, Boolean> isPackagePrivateConstructor = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Constructor.getDefaultInstance(), false, null, null, 1000, WireFormat.FieldType.BOOL, Boolean.class);

    private JavaClassProtoBuf() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.add(isStaticMethod);
        extensionRegistryLite.add(isPackagePrivateMethod);
        extensionRegistryLite.add(isStaticField);
        extensionRegistryLite.add(isPackagePrivateField);
        extensionRegistryLite.add(isPackagePrivateClass);
        extensionRegistryLite.add(isPackagePrivateConstructor);
    }
}
