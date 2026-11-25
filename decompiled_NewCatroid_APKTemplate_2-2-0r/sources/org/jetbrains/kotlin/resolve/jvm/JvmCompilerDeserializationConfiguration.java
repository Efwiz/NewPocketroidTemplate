package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;

/* JADX INFO: compiled from: JvmCompilerDeserializationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmCompilerDeserializationConfiguration;", "Lorg/jetbrains/kotlin/resolve/CompilerDeserializationConfiguration;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "binaryVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "getBinaryVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmCompilerDeserializationConfiguration extends CompilerDeserializationConfiguration {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmCompilerDeserializationConfiguration(LanguageVersionSettings languageVersionSettings) {
        super(languageVersionSettings);
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
    public JvmMetadataVersion getBinaryVersion() {
        return MetadataHelpersKt.toMetadataVersion(getLanguageVersionSettings().getLanguageVersion());
    }
}
