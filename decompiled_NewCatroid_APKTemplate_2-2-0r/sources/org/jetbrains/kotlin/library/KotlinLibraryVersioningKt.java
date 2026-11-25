package org.jetbrains.kotlin.library;

import java.util.Properties;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinLibraryVersioning.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u0005*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0006\u001a\u00020\u0001¨\u0006\u0007"}, d2 = {"readKonanLibraryVersioning", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "writeKonanLibraryVersioning", "", "versions", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryVersioningKt {
    public static final void writeKonanLibraryVersioning(Properties properties, KotlinLibraryVersioning versions) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(versions, "versions");
        KotlinAbiVersion abiVersion = versions.getAbiVersion();
        if (abiVersion != null) {
            properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_ABI_VERSION, abiVersion.toString());
        }
        String libraryVersion = versions.getLibraryVersion();
        if (libraryVersion != null) {
            properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_LIBRARY_VERSION, libraryVersion);
        }
        String compilerVersion = versions.getCompilerVersion();
        if (compilerVersion != null) {
            properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_COMPILER_VERSION, compilerVersion);
        }
        String metadataVersion = versions.getMetadataVersion();
        if (metadataVersion != null) {
            properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_METADATA_VERSION, metadataVersion);
        }
        properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_IR_SIGNATURE_VERSIONS, KotlinIrSignatureVersionKt.toManifestValue(versions.getIrSignatureVersions()));
    }

    public static final KotlinLibraryVersioning readKonanLibraryVersioning(Properties properties) {
        Set<KotlinIrSignatureVersion> of;
        Intrinsics.checkNotNullParameter(properties, "<this>");
        String property = properties.getProperty(KotlinLibraryKt.KLIB_PROPERTY_ABI_VERSION);
        KotlinAbiVersion kotlinAbiVersion = property != null ? KotlinAbiVersionKt.parseKotlinAbiVersion(property) : null;
        String property2 = properties.getProperty(KotlinLibraryKt.KLIB_PROPERTY_LIBRARY_VERSION);
        String property3 = properties.getProperty(KotlinLibraryKt.KLIB_PROPERTY_COMPILER_VERSION);
        String property4 = properties.getProperty(KotlinLibraryKt.KLIB_PROPERTY_METADATA_VERSION);
        String property5 = properties.getProperty(KotlinLibraryKt.KLIB_PROPERTY_IR_SIGNATURE_VERSIONS);
        if (property5 != null && (of = KotlinIrSignatureVersionKt.parseIrSignatureVersions(property5)) != null) {
            if (of.isEmpty()) {
                throw new IllegalStateException("Malformed manifest: Empty set of IR signature versions".toString());
            }
        } else {
            of = SetsKt.setOf(KotlinIrSignatureVersion.INSTANCE.getV1());
        }
        return new KotlinLibraryVersioning(property2, property3, kotlinAbiVersion, property4, of);
    }
}
