package org.jetbrains.kotlin.backend.common.serialization;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FileFingerprints.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0017\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006B\u0011\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\u0005\u0010\tB\u000f\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0005\u0010\fJ\u001a\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u0016HÖ\u0001¢\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\u001aH\u0016¢\u0006\u0004\b\u001b\u0010\u001cR\u0019\u0010\n\u001a\u00020\u000bø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e\u0088\u0001\n\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/SerializedKlibFingerprint;", "", "fileFingerprints", "", "Lorg/jetbrains/kotlin/backend/common/serialization/SerializedIrFileFingerprint;", "constructor-impl", "(Ljava/util/List;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "klibFile", "Ljava/io/File;", "(Ljava/io/File;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "klibFingerprint", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "getKlibFingerprint-3sOkxDc", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;Ljava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)I", "toString", "", "toString-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class SerializedKlibFingerprint {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Hash128Bits klibFingerprint;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ SerializedKlibFingerprint m6157boximpl(Hash128Bits hash128Bits) {
        return new SerializedKlibFingerprint(hash128Bits);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Hash128Bits m6160constructorimpl(Hash128Bits klibFingerprint) {
        Intrinsics.checkNotNullParameter(klibFingerprint, "klibFingerprint");
        return klibFingerprint;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6161equalsimpl(Hash128Bits hash128Bits, Object obj) {
        return (obj instanceof SerializedKlibFingerprint) && FingerprintHash.m6126equalsimpl0(hash128Bits, ((SerializedKlibFingerprint) obj).m6166unboximpl());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6162equalsimpl0(Hash128Bits hash128Bits, Hash128Bits hash128Bits2) {
        return FingerprintHash.m6126equalsimpl0(hash128Bits, hash128Bits2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6163hashCodeimpl(Hash128Bits hash128Bits) {
        return FingerprintHash.m6127hashCodeimpl(hash128Bits);
    }

    public boolean equals(Object obj) {
        return m6161equalsimpl(this.klibFingerprint, obj);
    }

    public int hashCode() {
        return m6163hashCodeimpl(this.klibFingerprint);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ Hash128Bits m6166unboximpl() {
        return this.klibFingerprint;
    }

    /* JADX INFO: compiled from: FileFingerprints.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\t\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\r\u0010\u000eJ \u0010\u000f\u001a\u00020\u0010*\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u0010H\u0002\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/SerializedKlibFingerprint$Companion;", "", "()V", "fromString", "Lorg/jetbrains/kotlin/backend/common/serialization/SerializedKlibFingerprint;", "s", "", "fromString-68069Vk", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "calculateKlibFingerprint", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/SerializedIrFileFingerprint;", "calculateKlibFingerprint-uGak_n4", "(Ljava/util/List;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "calculateKlibHash", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "Ljava/io/File;", "prefix", "seed", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: calculateKlibFingerprint-uGak_n4, reason: not valid java name */
        public final Hash128Bits m6168calculateKlibFingerprintuGak_n4(List<SerializedIrFileFingerprint> list) {
            Hash128Bits hash128Bits = new Hash128Bits(ULong.m3072constructorimpl(list.size()), 0L, 2, null);
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                hash128Bits = hash128Bits.combineWith(((SerializedIrFileFingerprint) it.next()).m6151unboximpl());
            }
            return FingerprintHash.m6124constructorimpl(hash128Bits);
        }

        static /* synthetic */ Hash128Bits calculateKlibHash$default(Companion companion, File file, String str, Hash128Bits hash128Bits, int i, Object obj) {
            if ((i & 1) != 0) {
                str = "";
            }
            if ((i & 2) != 0) {
                hash128Bits = new Hash128Bits(0L, 0L, 3, null);
            }
            return companion.calculateKlibHash(file, str, hash128Bits);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r13v7, types: [T, org.jetbrains.kotlin.backend.common.serialization.Hash128Bits] */
        private final Hash128Bits calculateKlibHash(File file, String str, Hash128Bits hash128Bits) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = hash128Bits;
            if (file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                Intrinsics.checkNotNull(fileArrListFiles);
                for (File file2 : ArraysKt.sortedWith(fileArrListFiles, new Comparator() { // from class: org.jetbrains.kotlin.backend.common.serialization.SerializedKlibFingerprint$Companion$calculateKlibHash$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(((File) t).getName(), ((File) t2).getName());
                    }
                })) {
                    String str2 = str + file2.getName() + '/';
                    Companion companion = SerializedKlibFingerprint.INSTANCE;
                    Intrinsics.checkNotNull(file2);
                    Hash128Bits hash128Bits2 = (Hash128Bits) objectRef.element;
                    byte[] bytes = str2.getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                    objectRef.element = companion.calculateKlibHash(file2, str2, CityHashKt.cityHash128WithSeed$default(hash128Bits2, bytes, 0, 0, 12, null));
                }
            } else {
                FilesKt.forEachBlock(file, new Function2<byte[], Integer, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.SerializedKlibFingerprint$Companion$calculateKlibHash$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(byte[] bArr, Integer num) {
                        invoke(bArr, num.intValue());
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Type inference failed for: r4v1, types: [T, org.jetbrains.kotlin.backend.common.serialization.Hash128Bits] */
                    public final void invoke(byte[] buffer, int i) {
                        Intrinsics.checkNotNullParameter(buffer, "buffer");
                        Ref.ObjectRef<Hash128Bits> objectRef2 = objectRef;
                        objectRef2.element = CityHashKt.cityHash128WithSeed(objectRef2.element, buffer, 0, i);
                    }
                });
            }
            return (Hash128Bits) objectRef.element;
        }

        /* JADX INFO: renamed from: fromString-68069Vk, reason: not valid java name */
        public final Hash128Bits m6169fromString68069Vk(String s) {
            Intrinsics.checkNotNullParameter(s, "s");
            Hash128Bits hash128BitsM6132fromStringLA3xOQI = FingerprintHash.INSTANCE.m6132fromStringLA3xOQI(s);
            if (hash128BitsM6132fromStringLA3xOQI != null) {
                return SerializedKlibFingerprint.m6160constructorimpl(hash128BitsM6132fromStringLA3xOQI);
            }
            return null;
        }
    }

    private /* synthetic */ SerializedKlibFingerprint(Hash128Bits hash128Bits) {
        this.klibFingerprint = hash128Bits;
    }

    /* JADX INFO: renamed from: getKlibFingerprint-3sOkxDc, reason: not valid java name and from getter */
    public final Hash128Bits getKlibFingerprint() {
        return this.klibFingerprint;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Hash128Bits m6159constructorimpl(List<SerializedIrFileFingerprint> fileFingerprints) {
        Intrinsics.checkNotNullParameter(fileFingerprints, "fileFingerprints");
        return m6160constructorimpl(INSTANCE.m6168calculateKlibFingerprintuGak_n4(fileFingerprints));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Hash128Bits m6158constructorimpl(File klibFile) {
        Intrinsics.checkNotNullParameter(klibFile, "klibFile");
        return m6160constructorimpl(FingerprintHash.m6124constructorimpl(Companion.calculateKlibHash$default(INSTANCE, klibFile, null, null, 3, null)));
    }

    public String toString() {
        return m6164toStringimpl(this.klibFingerprint);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6164toStringimpl(Hash128Bits hash128Bits) {
        return FingerprintHash.m6129toStringimpl(hash128Bits);
    }
}
