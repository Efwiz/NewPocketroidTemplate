package org.jetbrains.kotlin.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;

/* JADX INFO: compiled from: deserializationHelpers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"jvmMetadataVersionOrDefault", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeserializationHelpersKt {
    public static final JvmMetadataVersion jvmMetadataVersionOrDefault(DeserializationConfiguration deserializationConfiguration) {
        Intrinsics.checkNotNullParameter(deserializationConfiguration, "<this>");
        BinaryVersion binaryVersion = deserializationConfiguration.getBinaryVersion();
        JvmMetadataVersion jvmMetadataVersion = binaryVersion instanceof JvmMetadataVersion ? (JvmMetadataVersion) binaryVersion : null;
        return jvmMetadataVersion == null ? JvmMetadataVersion.INSTANCE : jvmMetadataVersion;
    }
}
