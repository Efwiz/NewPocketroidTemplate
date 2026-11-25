package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinAbiVersion;

/* JADX INFO: compiled from: IrModuleDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "", "abiVersion", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "(Lorg/jetbrains/kotlin/library/KotlinAbiVersion;)V", "getAbiVersion", "()Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "oldSignatures", "", "getOldSignatures", "()Z", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompatibilityMode {
    private final KotlinAbiVersion abiVersion;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final KotlinAbiVersion LAST_PRIVATE_SIG_ABI_VERSION = new KotlinAbiVersion(1, 5, 0);
    private static final CompatibilityMode CURRENT = new CompatibilityMode(KotlinAbiVersion.INSTANCE.getCURRENT());

    public CompatibilityMode(KotlinAbiVersion abiVersion) {
        Intrinsics.checkNotNullParameter(abiVersion, "abiVersion");
        this.abiVersion = abiVersion;
        if (!abiVersion.isCompatible()) {
            throw new IllegalArgumentException("Incompatible KLIB should have been discarded in libraryMatch".toString());
        }
    }

    public final KotlinAbiVersion getAbiVersion() {
        return this.abiVersion;
    }

    public final boolean getOldSignatures() {
        int minor = this.abiVersion.getMinor();
        KotlinAbiVersion kotlinAbiVersion = LAST_PRIVATE_SIG_ABI_VERSION;
        if (minor == kotlinAbiVersion.getMinor()) {
            return this.abiVersion.getPatch() <= kotlinAbiVersion.getPatch();
        }
        return this.abiVersion.getMinor() < kotlinAbiVersion.getMinor();
    }

    /* JADX INFO: compiled from: IrModuleDeserializer.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode$Companion;", "", "()V", "CURRENT", "Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "getCURRENT", "()Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "LAST_PRIVATE_SIG_ABI_VERSION", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "getLAST_PRIVATE_SIG_ABI_VERSION", "()Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinAbiVersion getLAST_PRIVATE_SIG_ABI_VERSION() {
            return CompatibilityMode.LAST_PRIVATE_SIG_ABI_VERSION;
        }

        public final CompatibilityMode getCURRENT() {
            return CompatibilityMode.CURRENT;
        }
    }
}
