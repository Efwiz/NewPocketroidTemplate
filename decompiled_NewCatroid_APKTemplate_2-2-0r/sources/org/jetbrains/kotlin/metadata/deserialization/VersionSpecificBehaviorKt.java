package org.jetbrains.kotlin.metadata.deserialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: versionSpecificBehavior.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0005"}, d2 = {"isKotlin1Dot4OrLater", "", "version", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "isVersionRequirementTableWrittenCorrectly", "metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VersionSpecificBehaviorKt {
    public static final boolean isVersionRequirementTableWrittenCorrectly(BinaryVersion version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return isKotlin1Dot4OrLater(version);
    }

    public static final boolean isKotlin1Dot4OrLater(BinaryVersion version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return (version.getMajor() == 1 && version.getMinor() >= 4) || version.getMajor() > 1;
    }
}
