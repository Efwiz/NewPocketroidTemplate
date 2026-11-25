package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.SerializedIrFile;

/* JADX INFO: compiled from: FileFingerprints.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\nB\u0011\b\u0002\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0004\b\u0004\u0010\rJ\u001a\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\tHÖ\u0001¢\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\u001aH\u0016¢\u0006\u0004\b\u001b\u0010\u001cR\u0019\u0010\u000b\u001a\u00020\fø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000f\u0088\u0001\u000b\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/SerializedIrFileFingerprint;", "", "file", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "constructor-impl", "(Lorg/jetbrains/kotlin/library/SerializedIrFile;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "lib", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "fileIndex", "", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;I)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "fileFingerprint", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "getFileFingerprint-3sOkxDc", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;Ljava/lang/Object;)Z", "hashCode", "hashCode-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)I", "toString", "", "toString-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class SerializedIrFileFingerprint {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Hash128Bits fileFingerprint;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ SerializedIrFileFingerprint m6142boximpl(Hash128Bits hash128Bits) {
        return new SerializedIrFileFingerprint(hash128Bits);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Hash128Bits m6143constructorimpl(Hash128Bits hash128Bits) {
        return hash128Bits;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6146equalsimpl(Hash128Bits hash128Bits, Object obj) {
        return (obj instanceof SerializedIrFileFingerprint) && FingerprintHash.m6126equalsimpl0(hash128Bits, ((SerializedIrFileFingerprint) obj).m6151unboximpl());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6147equalsimpl0(Hash128Bits hash128Bits, Hash128Bits hash128Bits2) {
        return FingerprintHash.m6126equalsimpl0(hash128Bits, hash128Bits2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6148hashCodeimpl(Hash128Bits hash128Bits) {
        return FingerprintHash.m6127hashCodeimpl(hash128Bits);
    }

    public boolean equals(Object obj) {
        return m6146equalsimpl(this.fileFingerprint, obj);
    }

    public int hashCode() {
        return m6148hashCodeimpl(this.fileFingerprint);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ Hash128Bits m6151unboximpl() {
        return this.fileFingerprint;
    }

    /* JADX INFO: compiled from: FileFingerprints.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\t\u0010\nJ\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0013\u0010\u0014\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/SerializedIrFileFingerprint$Companion;", "", "()V", "calculateFileFingerprint", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "lib", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "fileIndex", "", "calculateFileFingerprint-0Syv5Yw", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;I)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "file", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "calculateFileFingerprint-uGak_n4", "(Lorg/jetbrains/kotlin/library/SerializedIrFile;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "fromString", "Lorg/jetbrains/kotlin/backend/common/serialization/SerializedIrFileFingerprint;", "s", "", "fromString-8eX_dPE", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: fromString-8eX_dPE, reason: not valid java name */
        public final Hash128Bits m6156fromString8eX_dPE(String s) {
            Intrinsics.checkNotNullParameter(s, "s");
            Hash128Bits hash128BitsM6132fromStringLA3xOQI = FingerprintHash.INSTANCE.m6132fromStringLA3xOQI(s);
            if (hash128BitsM6132fromStringLA3xOQI != null) {
                return SerializedIrFileFingerprint.m6143constructorimpl(hash128BitsM6132fromStringLA3xOQI);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: calculateFileFingerprint-uGak_n4, reason: not valid java name */
        public final Hash128Bits m6155calculateFileFingerprintuGak_n4(SerializedIrFile file) {
            return FingerprintHash.m6124constructorimpl(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128$default(file.getFileData(), 0, 0, 6, null), file.getTypes(), 0, 0, 12, null), file.getSignatures(), 0, 0, 12, null), file.getStrings(), 0, 0, 12, null), file.getBodies(), 0, 0, 12, null), file.getDeclarations(), 0, 0, 12, null));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: calculateFileFingerprint-0Syv5Yw, reason: not valid java name */
        public final Hash128Bits m6154calculateFileFingerprint0Syv5Yw(KotlinLibrary lib, int fileIndex) {
            return FingerprintHash.m6124constructorimpl(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128WithSeed$default(CityHashKt.cityHash128$default(lib.file(fileIndex), 0, 0, 6, null), lib.types(fileIndex), 0, 0, 12, null), lib.signatures(fileIndex), 0, 0, 12, null), lib.strings(fileIndex), 0, 0, 12, null), lib.bodies(fileIndex), 0, 0, 12, null), lib.declarations(fileIndex), 0, 0, 12, null));
        }
    }

    private /* synthetic */ SerializedIrFileFingerprint(Hash128Bits hash128Bits) {
        this.fileFingerprint = hash128Bits;
    }

    /* JADX INFO: renamed from: getFileFingerprint-3sOkxDc, reason: not valid java name and from getter */
    public final Hash128Bits getFileFingerprint() {
        return this.fileFingerprint;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Hash128Bits m6145constructorimpl(SerializedIrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return m6143constructorimpl(INSTANCE.m6155calculateFileFingerprintuGak_n4(file));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Hash128Bits m6144constructorimpl(KotlinLibrary lib, int i) {
        Intrinsics.checkNotNullParameter(lib, "lib");
        return m6143constructorimpl(INSTANCE.m6154calculateFileFingerprint0Syv5Yw(lib, i));
    }

    public String toString() {
        return m6149toStringimpl(this.fileFingerprint);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6149toStringimpl(Hash128Bits hash128Bits) {
        return FingerprintHash.m6129toStringimpl(hash128Bits);
    }
}
