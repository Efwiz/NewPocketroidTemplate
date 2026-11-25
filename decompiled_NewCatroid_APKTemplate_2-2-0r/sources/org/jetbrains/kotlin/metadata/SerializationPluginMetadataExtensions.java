package org.jetbrains.kotlin.metadata;

import java.util.List;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.WireFormat;

/* JADX INFO: loaded from: classes5.dex */
public final class SerializationPluginMetadataExtensions {
    public static final int PROPERTIES_NAMES_IN_PROGRAM_ORDER_FIELD_NUMBER = 18000;
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<Integer>> propertiesNamesInProgramOrder = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), null, null, 18000, WireFormat.FieldType.INT32, false, Integer.class);

    private SerializationPluginMetadataExtensions() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.add(propertiesNamesInProgramOrder);
    }
}
