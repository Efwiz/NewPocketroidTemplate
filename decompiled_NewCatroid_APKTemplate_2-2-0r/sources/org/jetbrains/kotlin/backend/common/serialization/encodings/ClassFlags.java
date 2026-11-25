package org.jetbrains.kotlin.backend.common.serialization.encodings;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.serialization.IrFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlagsUtilsKt;

/* JADX INFO: compiled from: BinaryFlags.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 02\u00020\u0001:\u00010B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010$\u001a\u00020\t2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b&\u0010'J\u0010\u0010(\u001a\u00020)HÖ\u0001¢\u0006\u0004\b*\u0010+J\u0010\u0010,\u001a\u00020-HÖ\u0001¢\u0006\u0004\b.\u0010/R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0010\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000bR\u0011\u0010\u0012\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000bR\u0011\u0010\u0014\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000bR\u0011\u0010\u0016\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u000bR\u0011\u0010\u0018\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u001d8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020!8F¢\u0006\u0006\u001a\u0004\b\"\u0010#\u0088\u0001\u0002¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/ClassFlags;", "", OverlayablePolicy.NAME_flags, "", "constructor-impl", "(J)J", "getFlags", "()J", "isCompanion", "", "isCompanion-impl", "(J)Z", "isData", "isData-impl", "isExpect", "isExpect-impl", "isExternal", "isExternal-impl", "isFun", "isFun-impl", "isInner", "isInner-impl", "isValue", "isValue-impl", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getKind-impl", "(J)Lorg/jetbrains/kotlin/descriptors/ClassKind;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality-impl", "(J)Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getVisibility-impl", "(J)Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.EQUALS_METHOD_NAME, "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(J)I", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class ClassFlags {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long flags;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ClassFlags m6224boximpl(long j) {
        return new ClassFlags(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6225constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6226equalsimpl(long j, Object obj) {
        return (obj instanceof ClassFlags) && j == ((ClassFlags) obj).m6240unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6227equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6231hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6239toStringimpl(long j) {
        return "ClassFlags(flags=" + j + ')';
    }

    public boolean equals(Object obj) {
        return m6226equalsimpl(this.flags, obj);
    }

    public int hashCode() {
        return m6231hashCodeimpl(this.flags);
    }

    public String toString() {
        return m6239toStringimpl(this.flags);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m6240unboximpl() {
        return this.flags;
    }

    private /* synthetic */ ClassFlags(long j) {
        this.flags = j;
    }

    public final long getFlags() {
        return this.flags;
    }

    /* JADX INFO: renamed from: getModality-impl, reason: not valid java name */
    public static final Modality m6229getModalityimpl(long j) {
        return ProtoEnumFlags.INSTANCE.modality(IrFlags.MODALITY.get((int) j));
    }

    /* JADX INFO: renamed from: getVisibility-impl, reason: not valid java name */
    public static final DescriptorVisibility m6230getVisibilityimpl(long j) {
        return ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, IrFlags.VISIBILITY.get((int) j));
    }

    /* JADX INFO: renamed from: getKind-impl, reason: not valid java name */
    public static final ClassKind m6228getKindimpl(long j) {
        return ProtoEnumFlags.INSTANCE.classKind(IrFlags.CLASS_KIND.get((int) j));
    }

    /* JADX INFO: renamed from: isCompanion-impl, reason: not valid java name */
    public static final boolean m6232isCompanionimpl(long j) {
        return IrFlags.CLASS_KIND.get((int) j) == ProtoBuf.Class.Kind.COMPANION_OBJECT;
    }

    /* JADX INFO: renamed from: isInner-impl, reason: not valid java name */
    public static final boolean m6237isInnerimpl(long j) {
        Boolean bool = IrFlags.IS_INNER.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isData-impl, reason: not valid java name */
    public static final boolean m6233isDataimpl(long j) {
        Boolean bool = IrFlags.IS_DATA.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isValue-impl, reason: not valid java name */
    public static final boolean m6238isValueimpl(long j) {
        Boolean bool = IrFlags.IS_VALUE_CLASS.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isExpect-impl, reason: not valid java name */
    public static final boolean m6234isExpectimpl(long j) {
        Boolean bool = IrFlags.IS_EXPECT_CLASS.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isExternal-impl, reason: not valid java name */
    public static final boolean m6235isExternalimpl(long j) {
        Boolean bool = IrFlags.IS_EXTERNAL_CLASS.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isFun-impl, reason: not valid java name */
    public static final boolean m6236isFunimpl(long j) {
        Boolean bool = IrFlags.IS_FUN_INTERFACE.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: compiled from: BinaryFlags.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0016\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/ClassFlags$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/ClassFlags;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-K2PyDQs", "(J)J", "encode", "clazz", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(IrClass clazz, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            boolean z = !clazz.getAnnotations().isEmpty();
            ProtoBuf.Visibility visibilityDescriptorVisibility = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, clazz.getVisibility().normalize());
            ProtoBuf.Modality modality = ProtoEnumFlags.INSTANCE.modality(clazz.getModality());
            ProtoBuf.Class.Kind kindClassKind = ProtoEnumFlags.INSTANCE.classKind(clazz.getKind(), clazz.getIsCompanion());
            return IrFlags.getClassFlags(z, visibilityDescriptorVisibility, modality, kindClassKind, clazz.getIsInner(), clazz.getIsData(), clazz.getIsExternal(), clazz.getIsExpect(), clazz.getIsValue(), clazz.getIsFun(), kindClassKind == ProtoBuf.Class.Kind.ENUM_CLASS && languageVersionSettings.supportsFeature(LanguageFeature.EnumEntries));
        }

        /* JADX INFO: renamed from: decode-K2PyDQs, reason: not valid java name */
        public final long m6241decodeK2PyDQs(long code) {
            return ClassFlags.m6225constructorimpl(code);
        }
    }
}
