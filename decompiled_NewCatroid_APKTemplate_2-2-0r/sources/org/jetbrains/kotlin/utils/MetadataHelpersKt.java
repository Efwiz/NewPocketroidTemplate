package org.jetbrains.kotlin.utils;

import java.util.EnumMap;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;

/* JADX INFO: compiled from: metadataHelpers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0002\u001a\n\u0010\b\u001a\u00020\u0003*\u00020\u0002\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"LANGUAGE_TO_METADATA_VERSION", "Ljava/util/EnumMap;", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "languageVersion", "toMetadataVersion", "frontend.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MetadataHelpersKt {
    private static final EnumMap<LanguageVersion, JvmMetadataVersion> LANGUAGE_TO_METADATA_VERSION;

    static {
        EnumMap<LanguageVersion, JvmMetadataVersion> enumMap = new EnumMap<>(LanguageVersion.class);
        JvmMetadataVersion jvmMetadataVersion = new JvmMetadataVersion(1, 1, 18);
        EnumMap<LanguageVersion, JvmMetadataVersion> enumMap2 = enumMap;
        enumMap2.put(LanguageVersion.KOTLIN_1_0, jvmMetadataVersion);
        enumMap2.put(LanguageVersion.KOTLIN_1_1, jvmMetadataVersion);
        enumMap2.put(LanguageVersion.KOTLIN_1_2, jvmMetadataVersion);
        enumMap2.put(LanguageVersion.KOTLIN_1_3, jvmMetadataVersion);
        enumMap2.put(LanguageVersion.KOTLIN_1_4, new JvmMetadataVersion(1, 4, 3));
        enumMap2.put(LanguageVersion.KOTLIN_1_5, new JvmMetadataVersion(1, 5, 1));
        enumMap2.put(LanguageVersion.KOTLIN_1_6, new JvmMetadataVersion(1, 6, 0));
        enumMap2.put(LanguageVersion.KOTLIN_1_7, new JvmMetadataVersion(1, 7, 0));
        enumMap2.put(LanguageVersion.KOTLIN_1_8, new JvmMetadataVersion(1, 8, 0));
        enumMap2.put(LanguageVersion.KOTLIN_1_9, JvmMetadataVersion.INSTANCE);
        enumMap2.put(LanguageVersion.KOTLIN_2_0, new JvmMetadataVersion(2, 0, 0));
        enumMap2.put(LanguageVersion.KOTLIN_2_1, new JvmMetadataVersion(2, 1, 0));
        if (enumMap.size() == LanguageVersion.values().length) {
            LANGUAGE_TO_METADATA_VERSION = enumMap;
            return;
        }
        throw new IllegalStateException("Please add mappings from the missing LanguageVersion instances to the corresponding JvmMetadataVersion in `LANGUAGE_TO_METADATA_VERSION`".toString());
    }

    public static final JvmMetadataVersion toMetadataVersion(LanguageVersion languageVersion) {
        Intrinsics.checkNotNullParameter(languageVersion, "<this>");
        Object value = MapsKt.getValue(LANGUAGE_TO_METADATA_VERSION, languageVersion);
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (JvmMetadataVersion) value;
    }

    public static /* synthetic */ BinaryVersion metadataVersion$default(CompilerConfiguration compilerConfiguration, LanguageVersion languageVersion, int i, Object obj) {
        if ((i & 1) != 0) {
            languageVersion = CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration).getLanguageVersion();
        }
        return metadataVersion(compilerConfiguration, languageVersion);
    }

    public static final BinaryVersion metadataVersion(CompilerConfiguration compilerConfiguration, LanguageVersion languageVersion) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        BinaryVersion binaryVersion = (BinaryVersion) compilerConfiguration.get(CommonConfigurationKeys.METADATA_VERSION);
        return binaryVersion == null ? toMetadataVersion(languageVersion) : binaryVersion;
    }
}
