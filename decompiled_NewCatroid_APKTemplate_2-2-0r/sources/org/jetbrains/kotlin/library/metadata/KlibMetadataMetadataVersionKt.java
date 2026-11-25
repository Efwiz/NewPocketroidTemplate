package org.jetbrains.kotlin.library.metadata;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;

/* JADX INFO: compiled from: KlibMetadataMetadataVersion.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"metadataVersion", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "getMetadataVersion", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataMetadataVersionKt {
    public static final KlibMetadataVersion getMetadataVersion(KotlinLibrary kotlinLibrary) {
        int[] versionArray;
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        String property = kotlinLibrary.getManifestProperties().getProperty(KotlinLibraryKt.KLIB_PROPERTY_METADATA_VERSION);
        if (property == null || (versionArray = BinaryVersion.INSTANCE.parseVersionArray(property)) == null) {
            return null;
        }
        return new KlibMetadataVersion(Arrays.copyOf(versionArray, versionArray.length));
    }
}
