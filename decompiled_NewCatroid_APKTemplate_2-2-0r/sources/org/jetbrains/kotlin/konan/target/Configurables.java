package org.jetbrains.kotlin.konan.target;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.konan.properties.TargetableExternalStorage;

/* JADX INFO: compiled from: Configurables.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0006R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u000eR\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u000eR\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u000eR\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0006R\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0006R\u0016\u0010\u001f\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u0006R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u000eR\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00040\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\u000eR\u0012\u0010%\u001a\u00020&X¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0016\u0010)\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\u0006R\u0016\u0010+\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0006R\u0016\u0010-\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\u0006R\u0016\u0010/\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u0006R\u0014\u00101\u001a\u0002028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00104ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00065À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Configurables;", "Lorg/jetbrains/kotlin/konan/properties/TargetableExternalStorage;", "Lorg/jetbrains/kotlin/konan/target/RelocationModeFlags;", "absoluteLlvmHome", "", "getAbsoluteLlvmHome", "()Ljava/lang/String;", "absoluteTargetSysRoot", "getAbsoluteTargetSysRoot", "absoluteTargetToolchain", "getAbsoluteTargetToolchain", "additionalCacheFlags", "", "getAdditionalCacheFlags", "()Ljava/util/List;", "cacheableTargets", "getCacheableTargets", "libffiDir", "getLibffiDir", "linkerDynamicFlags", "getLinkerDynamicFlags", "linkerKonanFlags", "getLinkerKonanFlags", "linkerNoDebugFlags", "getLinkerNoDebugFlags", "linkerOptimizationFlags", "getLinkerOptimizationFlags", "llvmHome", "getLlvmHome", "llvmInlineThreshold", "getLlvmInlineThreshold", "llvmVersion", "getLlvmVersion", "mimallocLinkerDependencies", "getMimallocLinkerDependencies", "runtimeDefinitions", "getRuntimeDefinitions", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetCpu", "getTargetCpu", "targetCpuFeatures", "getTargetCpuFeatures", "targetSysRoot", "getTargetSysRoot", "targetToolchain", "getTargetToolchain", "targetTriple", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "getTargetTriple", "()Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface Configurables extends TargetableExternalStorage, RelocationModeFlags {
    KonanTarget getTarget();

    default TargetTriple getTargetTriple() {
        TargetTriple targetTripleFromString;
        String strTargetString = targetString("targetTriple");
        if (strTargetString == null || (targetTripleFromString = TargetTriple.INSTANCE.fromString(strTargetString)) == null) {
            throw new IllegalStateException(("quadruple for " + getTarget() + " is not set.").toString());
        }
        return targetTripleFromString;
    }

    default String getLlvmHome() {
        return hostString("llvmHome");
    }

    default String getLlvmVersion() {
        return hostString("llvmVersion");
    }

    default String getLibffiDir() {
        return hostString("libffiDir");
    }

    default List<String> getCacheableTargets() {
        return hostList("cacheableTargets");
    }

    default List<String> getAdditionalCacheFlags() {
        return targetList("additionalCacheFlags");
    }

    default List<String> getLinkerOptimizationFlags() {
        return targetList("linkerOptimizationFlags");
    }

    default List<String> getLinkerKonanFlags() {
        return targetList("linkerKonanFlags");
    }

    default List<String> getMimallocLinkerDependencies() {
        return targetList("mimallocLinkerDependencies");
    }

    default List<String> getLinkerNoDebugFlags() {
        return targetList("linkerNoDebugFlags");
    }

    default List<String> getLinkerDynamicFlags() {
        return targetList("linkerDynamicFlags");
    }

    default String getTargetSysRoot() {
        return targetString("targetSysRoot");
    }

    default String getTargetToolchain() {
        return hostTargetString("targetToolchain");
    }

    default String getAbsoluteTargetSysRoot() {
        return absolute(getTargetSysRoot());
    }

    default String getAbsoluteTargetToolchain() {
        return absolute(getTargetToolchain());
    }

    default String getAbsoluteLlvmHome() {
        return absolute(getLlvmHome());
    }

    default String getTargetCpu() {
        return targetString("targetCpu");
    }

    default String getTargetCpuFeatures() {
        return targetString("targetCpuFeatures");
    }

    default String getLlvmInlineThreshold() {
        return targetString("llvmInlineThreshold");
    }

    default List<String> getRuntimeDefinitions() {
        return targetList("runtimeDefinitions");
    }
}
