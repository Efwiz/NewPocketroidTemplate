package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.jvm.JvmPlatformKt;
import org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport;

/* JADX INFO: compiled from: EnumEntriesDeserializationSupportImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010\u0007R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/EnumEntriesDeserializationSupportImpl;", "Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "(Lorg/jetbrains/kotlin/platform/TargetPlatform;)V", "canSynthesizeEnumEntries", "", "()Ljava/lang/Boolean;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumEntriesDeserializationSupportImpl implements EnumEntriesDeserializationSupport {
    private final TargetPlatform platform;

    public EnumEntriesDeserializationSupportImpl(TargetPlatform targetPlatform) {
        this.platform = targetPlatform;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport
    public Boolean canSynthesizeEnumEntries() {
        return Boolean.valueOf(JvmPlatformKt.isJvm(this.platform));
    }
}
