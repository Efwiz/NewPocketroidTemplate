package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMapping;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.utils.DeserializationHelpersKt;

/* JADX INFO: compiled from: ModuleMappingUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u001a8\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¨\u0006\r"}, d2 = {"loadModuleMapping", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping$Companion;", "bytes", "", "debugName", "", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "reportIncompatibleVersionError", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModuleMappingUtilKt {
    public static final ModuleMapping loadModuleMapping(ModuleMapping.Companion companion, byte[] bArr, String debugName, DeserializationConfiguration configuration, Function1<? super JvmMetadataVersion, Unit> reportIncompatibleVersionError) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(reportIncompatibleVersionError, "reportIncompatibleVersionError");
        return companion.loadModuleMapping(bArr, debugName, configuration.getSkipMetadataVersionCheck(), configuration.isJvmPackageNameSupported(), DeserializationHelpersKt.jvmMetadataVersionOrDefault(configuration), reportIncompatibleVersionError);
    }
}
