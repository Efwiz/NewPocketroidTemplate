package org.jetbrains.kotlin.analysis.decompiler.psi.text;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;

/* JADX INFO: compiled from: incompatibleAbiVersion.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a%\u0010\u0005\u001a\u00020\u0006\"\b\b\u0000\u0010\u0007*\u00020\b2\u0006\u0010\t\u001a\u0002H\u00072\u0006\u0010\n\u001a\u0002H\u0007¢\u0006\u0002\u0010\u000b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"CURRENT_ABI_VERSION_MARKER", "", "FILE_ABI_VERSION_MARKER", "INCOMPATIBLE_ABI_VERSION_COMMENT", "INCOMPATIBLE_ABI_VERSION_GENERAL_COMMENT", "createIncompatibleAbiVersionDecompiledText", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/text/DecompiledText;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "expectedVersion", "actualVersion", "(Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;)Lorg/jetbrains/kotlin/analysis/decompiler/psi/text/DecompiledText;", "decompiler-to-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IncompatibleAbiVersionKt {
    public static final String INCOMPATIBLE_ABI_VERSION_GENERAL_COMMENT = "// This class file was compiled with different version of Kotlin compiler and can't be decompiled.";

    public static final <V extends BinaryVersion> DecompiledText createIncompatibleAbiVersionDecompiledText(V expectedVersion, V actualVersion) {
        Intrinsics.checkNotNullParameter(expectedVersion, "expectedVersion");
        Intrinsics.checkNotNullParameter(actualVersion, "actualVersion");
        return new DecompiledText(StringsKt.replace$default(StringsKt.replace$default("// This class file was compiled with different version of Kotlin compiler and can't be decompiled.\n//\n// Current compiler ABI version is CURRENT_ABI\n// File ABI version is FILE_ABI", "CURRENT_ABI", expectedVersion.toString(), false, 4, (Object) null), "FILE_ABI", actualVersion.toString(), false, 4, (Object) null));
    }
}
