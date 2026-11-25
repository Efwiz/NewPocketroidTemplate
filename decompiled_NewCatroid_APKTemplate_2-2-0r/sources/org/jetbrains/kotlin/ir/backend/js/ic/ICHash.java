package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;

/* JADX INFO: compiled from: HashCalculatorForIC.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0011\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\r\u001a\u00020\u000eHÖ\u0001¢\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014¢\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u0018HÖ\u0001¢\u0006\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "", "hash", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "constructor-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "getHash", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;Ljava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)I", "toProtoStream", "", "out", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "toProtoStream-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;)V", "toString", "", "toString-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)Ljava/lang/String;", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class ICHash {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Hash128Bits hash;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ICHash m7107boximpl(Hash128Bits hash128Bits) {
        return new ICHash(hash128Bits);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Hash128Bits m7108constructorimpl(Hash128Bits hash) {
        Intrinsics.checkNotNullParameter(hash, "hash");
        return hash;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m7110equalsimpl(Hash128Bits hash128Bits, Object obj) {
        return (obj instanceof ICHash) && Intrinsics.areEqual(hash128Bits, ((ICHash) obj).m7115unboximpl());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m7111equalsimpl0(Hash128Bits hash128Bits, Hash128Bits hash128Bits2) {
        return Intrinsics.areEqual(hash128Bits, hash128Bits2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m7112hashCodeimpl(Hash128Bits hash128Bits) {
        return hash128Bits.hashCode();
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m7114toStringimpl(Hash128Bits hash128Bits) {
        return "ICHash(hash=" + hash128Bits + ')';
    }

    public boolean equals(Object obj) {
        return m7110equalsimpl(this.hash, obj);
    }

    public int hashCode() {
        return m7112hashCodeimpl(this.hash);
    }

    public String toString() {
        return m7114toStringimpl(this.hash);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ Hash128Bits m7115unboximpl() {
        return this.hash;
    }

    private /* synthetic */ ICHash(Hash128Bits hash128Bits) {
        this.hash = hash128Bits;
    }

    /* JADX INFO: renamed from: constructor-impl$default, reason: not valid java name */
    public static /* synthetic */ Hash128Bits m7109constructorimpl$default(Hash128Bits hash128Bits, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            hash128Bits = new Hash128Bits(0L, 0L, 3, null);
        }
        return m7108constructorimpl(hash128Bits);
    }

    public final Hash128Bits getHash() {
        return this.hash;
    }

    /* JADX INFO: renamed from: toProtoStream-impl, reason: not valid java name */
    public static final void m7113toProtoStreamimpl(Hash128Bits hash128Bits, CodedOutputStream out) throws IOException {
        Intrinsics.checkNotNullParameter(out, "out");
        HashCalculatorForICKt.toProtoStream(hash128Bits, out);
    }

    /* JADX INFO: compiled from: HashCalculatorForIC.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash$Companion;", "", "()V", "fromProtoStream", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "input", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "fromProtoStream-TVZkk9w", "(Lorg/jetbrains/kotlin/protobuf/CodedInputStream;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: fromProtoStream-TVZkk9w, reason: not valid java name */
        public final Hash128Bits m7116fromProtoStreamTVZkk9w(CodedInputStream input) {
            Intrinsics.checkNotNullParameter(input, "input");
            return ICHash.m7108constructorimpl(HashCalculatorForICKt.readHash128BitsFromProtoStream(input));
        }
    }
}
