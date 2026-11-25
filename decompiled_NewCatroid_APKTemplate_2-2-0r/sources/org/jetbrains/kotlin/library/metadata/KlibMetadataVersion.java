package org.jetbrains.kotlin.library.metadata;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;

/* JADX INFO: compiled from: KlibMetadataMetadataVersion.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0011\u0012\n\u0010\u0002\u001a\u00020\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "numbers", "", "", "([I)V", "isCompatibleWithCurrentCompilerVersion", "", "Companion", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataVersion extends BinaryVersion {
    public static final KlibMetadataVersion INSTANCE = new KlibMetadataVersion(1, 4, 1);
    public static final KlibMetadataVersion INVALID_VERSION = new KlibMetadataVersion(new int[0]);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KlibMetadataVersion(int... numbers) {
        super(Arrays.copyOf(numbers, numbers.length));
        Intrinsics.checkNotNullParameter(numbers, "numbers");
    }

    @Override // org.jetbrains.kotlin.metadata.deserialization.BinaryVersion
    public boolean isCompatibleWithCurrentCompilerVersion() {
        return isCompatibleTo(INSTANCE);
    }
}
