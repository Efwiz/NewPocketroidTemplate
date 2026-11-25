package org.jetbrains.kotlin.backend.common.serialization.encodings;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.serialization.IrFlags;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlagsUtilsKt;

/* JADX INFO: compiled from: BinaryFlags.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\u0010\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u0015HÖ\u0001¢\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u0019HÖ\u0001¢\u0006\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0088\u0001\u0002¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/TypeAliasFlags;", "", OverlayablePolicy.NAME_flags, "", "constructor-impl", "(J)J", "getFlags", "()J", "isActual", "", "isActual-impl", "(J)Z", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getVisibility-impl", "(J)Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.EQUALS_METHOD_NAME, "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(J)I", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class TypeAliasFlags {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long flags;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TypeAliasFlags m6303boximpl(long j) {
        return new TypeAliasFlags(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6304constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6305equalsimpl(long j, Object obj) {
        return (obj instanceof TypeAliasFlags) && j == ((TypeAliasFlags) obj).m6311unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6306equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6308hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6310toStringimpl(long j) {
        return "TypeAliasFlags(flags=" + j + ')';
    }

    public boolean equals(Object obj) {
        return m6305equalsimpl(this.flags, obj);
    }

    public int hashCode() {
        return m6308hashCodeimpl(this.flags);
    }

    public String toString() {
        return m6310toStringimpl(this.flags);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m6311unboximpl() {
        return this.flags;
    }

    private /* synthetic */ TypeAliasFlags(long j) {
        this.flags = j;
    }

    public final long getFlags() {
        return this.flags;
    }

    /* JADX INFO: renamed from: getVisibility-impl, reason: not valid java name */
    public static final DescriptorVisibility m6307getVisibilityimpl(long j) {
        return ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, IrFlags.VISIBILITY.get((int) j));
    }

    /* JADX INFO: renamed from: isActual-impl, reason: not valid java name */
    public static final boolean m6309isActualimpl(long j) {
        Boolean bool = IrFlags.IS_ACTUAL.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: compiled from: BinaryFlags.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/TypeAliasFlags$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/TypeAliasFlags;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-OrzgV44", "(J)J", "encode", "typeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(IrTypeAlias typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            return IrFlags.getTypeAliasFlags(!typeAlias.getAnnotations().isEmpty(), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, typeAlias.getVisibility().normalize()), typeAlias.getIsActual());
        }

        /* JADX INFO: renamed from: decode-OrzgV44, reason: not valid java name */
        public final long m6312decodeOrzgV44(long code) {
            return TypeAliasFlags.m6304constructorimpl(code);
        }
    }
}
