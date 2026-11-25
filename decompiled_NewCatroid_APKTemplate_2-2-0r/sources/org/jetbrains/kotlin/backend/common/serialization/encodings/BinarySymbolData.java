package org.jetbrains.kotlin.backend.common.serialization.encodings;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BinarySymbolData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0087@\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001d\u001eB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b\u0016\u0010\u000fJ\u000f\u0010\u0017\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0018\u0010\u000fJ\u0010\u0010\u0019\u001a\u00020\u001aHÖ\u0001¢\u0006\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0088\u0001\u0002¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "constructor-impl", "(J)J", "getCode", "()J", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "getKind-impl", "(J)Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "signatureId", "", "getSignatureId-impl", "(J)I", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "symbolKindId", "symbolKindId-impl", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "SymbolKind", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class BinarySymbolData {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long code;

    /* JADX INFO: compiled from: BinarySymbolData.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0012\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "", "(Ljava/lang/String;I)V", "FUNCTION_SYMBOL", "CONSTRUCTOR_SYMBOL", "ENUM_ENTRY_SYMBOL", "FIELD_SYMBOL", "VALUE_PARAMETER_SYMBOL", "RETURNABLE_BLOCK_SYMBOL", "CLASS_SYMBOL", "TYPE_PARAMETER_SYMBOL", "VARIABLE_SYMBOL", "ANONYMOUS_INIT_SYMBOL", "STANDALONE_FIELD_SYMBOL", "RECEIVER_PARAMETER_SYMBOL", "PROPERTY_SYMBOL", "LOCAL_DELEGATED_PROPERTY_SYMBOL", "TYPEALIAS_SYMBOL", "FILE_SYMBOL", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum SymbolKind {
        FUNCTION_SYMBOL,
        CONSTRUCTOR_SYMBOL,
        ENUM_ENTRY_SYMBOL,
        FIELD_SYMBOL,
        VALUE_PARAMETER_SYMBOL,
        RETURNABLE_BLOCK_SYMBOL,
        CLASS_SYMBOL,
        TYPE_PARAMETER_SYMBOL,
        VARIABLE_SYMBOL,
        ANONYMOUS_INIT_SYMBOL,
        STANDALONE_FIELD_SYMBOL,
        RECEIVER_PARAMETER_SYMBOL,
        PROPERTY_SYMBOL,
        LOCAL_DELEGATED_PROPERTY_SYMBOL,
        TYPEALIAS_SYMBOL,
        FILE_SYMBOL;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<SymbolKind> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BinarySymbolData m6201boximpl(long j) {
        return new BinarySymbolData(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6202constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6203equalsimpl(long j, Object obj) {
        return (obj instanceof BinarySymbolData) && j == ((BinarySymbolData) obj).m6210unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6204equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getSignatureId-impl, reason: not valid java name */
    public static final int m6206getSignatureIdimpl(long j) {
        return (int) (j >>> 8);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6207hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: symbolKindId-impl, reason: not valid java name */
    private static final int m6208symbolKindIdimpl(long j) {
        return (int) (j & 255);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6209toStringimpl(long j) {
        return "BinarySymbolData(code=" + j + ')';
    }

    public boolean equals(Object obj) {
        return m6203equalsimpl(this.code, obj);
    }

    public int hashCode() {
        return m6207hashCodeimpl(this.code);
    }

    public String toString() {
        return m6209toStringimpl(this.code);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m6210unboximpl() {
        return this.code;
    }

    private /* synthetic */ BinarySymbolData(long j) {
        this.code = j;
    }

    public final long getCode() {
        return this.code;
    }

    /* JADX INFO: renamed from: getKind-impl, reason: not valid java name */
    public static final SymbolKind m6205getKindimpl(long j) {
        return SymbolKind.values()[m6208symbolKindIdimpl(j)];
    }

    /* JADX INFO: compiled from: BinarySymbolData.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0016\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-9x8F8T0", "(J)J", "encode", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "signatureId", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(SymbolKind kind, int signatureId) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            return ((long) kind.ordinal()) | (((long) signatureId) << 8);
        }

        /* JADX INFO: renamed from: decode-9x8F8T0, reason: not valid java name */
        public final long m6211decode9x8F8T0(long code) {
            return BinarySymbolData.m6202constructorimpl(code);
        }
    }
}
