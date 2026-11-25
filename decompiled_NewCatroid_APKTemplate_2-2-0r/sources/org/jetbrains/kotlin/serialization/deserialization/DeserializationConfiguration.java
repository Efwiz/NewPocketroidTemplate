package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;

/* JADX INFO: compiled from: DeserializationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\bf\u0018\u00002\u00020\u0001:\u0001\u0017R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0005R\u0014\u0010\u000b\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0005R\u0014\u0010\r\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0005R\u0014\u0010\u000f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0005R\u0014\u0010\u0011\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0005R\u0014\u0010\u0013\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0005R\u0014\u0010\u0015\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "", "allowUnstableDependencies", "", "getAllowUnstableDependencies", "()Z", "binaryVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getBinaryVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "isJvmPackageNameSupported", "preserveDeclarationsOrdering", "getPreserveDeclarationsOrdering", "readDeserializedContracts", "getReadDeserializedContracts", "reportErrorsOnPreReleaseDependencies", "getReportErrorsOnPreReleaseDependencies", "skipMetadataVersionCheck", "getSkipMetadataVersionCheck", "skipPrereleaseCheck", "getSkipPrereleaseCheck", "typeAliasesAllowed", "getTypeAliasesAllowed", "Default", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DeserializationConfiguration {
    default boolean getAllowUnstableDependencies() {
        return false;
    }

    default BinaryVersion getBinaryVersion() {
        return null;
    }

    default boolean getPreserveDeclarationsOrdering() {
        return false;
    }

    default boolean getReadDeserializedContracts() {
        return false;
    }

    default boolean getReportErrorsOnPreReleaseDependencies() {
        return false;
    }

    default boolean getSkipMetadataVersionCheck() {
        return false;
    }

    default boolean getSkipPrereleaseCheck() {
        return false;
    }

    default boolean getTypeAliasesAllowed() {
        return true;
    }

    default boolean isJvmPackageNameSupported() {
        return true;
    }

    /* JADX INFO: compiled from: DeserializationConfiguration.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration$Default;", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "()V", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements DeserializationConfiguration {
        public static final Default INSTANCE = new Default();

        private Default() {
        }
    }
}
