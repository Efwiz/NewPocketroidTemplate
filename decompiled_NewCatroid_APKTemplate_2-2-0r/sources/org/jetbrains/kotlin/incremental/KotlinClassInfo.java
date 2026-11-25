package org.jetbrains.kotlin.incremental;

import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.storage.ProtoMapValue;
import org.jetbrains.kotlin.inline.InlineFunctionOrAccessor;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.BitEncoding;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: KotlinClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 22\u00020\u0001:\u000223BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rR\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0016\u001a\u00020\u00178FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001c\u001a\u0004\u0018\u00010\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001b\u001a\u0004\b\u001d\u0010\u0013R#\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010 8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u001b\u001a\u0004\b!\u0010\"R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0013\u0010\n\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u001b\u0010(\u001a\u00020)8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u001b\u001a\u0004\b*\u0010+R\u001b\u0010-\u001a\u00020.8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\u001b\u001a\u0004\b/\u00100¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classKind", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "classHeaderData", "", "", "classHeaderStrings", "multifileClassName", "extraInfo", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo$ExtraInfo;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;[Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/incremental/KotlinClassInfo$ExtraInfo;)V", "getClassHeaderData", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getClassHeaderStrings", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getClassKind", "()Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getClassName", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "className$delegate", "Lkotlin/Lazy;", "companionObject", "getCompanionObject", "companionObject$delegate", "constantsInCompanionObject", "", "getConstantsInCompanionObject", "()Ljava/util/List;", "constantsInCompanionObject$delegate", "getExtraInfo", "()Lorg/jetbrains/kotlin/incremental/KotlinClassInfo$ExtraInfo;", "getMultifileClassName", "()Ljava/lang/String;", "protoData", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "getProtoData", "()Lorg/jetbrains/kotlin/incremental/ProtoData;", "protoData$delegate", "protoMapValue", "Lorg/jetbrains/kotlin/incremental/storage/ProtoMapValue;", "getProtoMapValue", "()Lorg/jetbrains/kotlin/incremental/storage/ProtoMapValue;", "protoMapValue$delegate", "Companion", "ExtraInfo", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String[] classHeaderData;
    private final String[] classHeaderStrings;
    private final ClassId classId;
    private final KotlinClassHeader.Kind classKind;

    /* JADX INFO: renamed from: className$delegate, reason: from kotlin metadata */
    private final Lazy className;

    /* JADX INFO: renamed from: companionObject$delegate, reason: from kotlin metadata */
    private final Lazy companionObject;

    /* JADX INFO: renamed from: constantsInCompanionObject$delegate, reason: from kotlin metadata */
    private final Lazy constantsInCompanionObject;
    private final ExtraInfo extraInfo;
    private final String multifileClassName;

    /* JADX INFO: renamed from: protoData$delegate, reason: from kotlin metadata */
    private final Lazy protoData;

    /* JADX INFO: renamed from: protoMapValue$delegate, reason: from kotlin metadata */
    private final Lazy protoMapValue;

    public KotlinClassInfo(ClassId classId, KotlinClassHeader.Kind classKind, String[] classHeaderData, String[] classHeaderStrings, String str, ExtraInfo extraInfo) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        Intrinsics.checkNotNullParameter(classHeaderData, "classHeaderData");
        Intrinsics.checkNotNullParameter(classHeaderStrings, "classHeaderStrings");
        Intrinsics.checkNotNullParameter(extraInfo, "extraInfo");
        this.classId = classId;
        this.classKind = classKind;
        this.classHeaderData = classHeaderData;
        this.classHeaderStrings = classHeaderStrings;
        this.multifileClassName = str;
        this.extraInfo = extraInfo;
        this.className = LazyKt.lazy(new Function0<JvmClassName>() { // from class: org.jetbrains.kotlin.incremental.KotlinClassInfo$className$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JvmClassName invoke() {
                JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(this.this$0.getClassId());
                Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(...)");
                return jvmClassNameByClassId;
            }
        });
        this.protoMapValue = LazyKt.lazy(new Function0<ProtoMapValue>() { // from class: org.jetbrains.kotlin.incremental.KotlinClassInfo$protoMapValue$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ProtoMapValue invoke() {
                boolean z = this.this$0.getClassKind() != KotlinClassHeader.Kind.CLASS;
                byte[] bArrDecodeBytes = BitEncoding.decodeBytes(this.this$0.getClassHeaderData());
                Intrinsics.checkNotNullExpressionValue(bArrDecodeBytes, "decodeBytes(...)");
                return new ProtoMapValue(z, bArrDecodeBytes, this.this$0.getClassHeaderStrings());
            }
        });
        this.protoData = LazyKt.lazy(new Function0<ProtoData>() { // from class: org.jetbrains.kotlin.incremental.KotlinClassInfo$protoData$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ProtoData invoke() {
                boolean z = this.this$0.getClassKind() != KotlinClassHeader.Kind.MULTIFILE_CLASS;
                KotlinClassInfo kotlinClassInfo = this.this$0;
                if (!z) {
                    throw new IllegalStateException(("Proto data is not available for KotlinClassHeader.Kind.MULTIFILE_CLASS: " + kotlinClassInfo.getClassId()).toString());
                }
                ProtoMapValue protoMapValue = kotlinClassInfo.getProtoMapValue();
                FqName packageFqName = this.this$0.getClassId().getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                return ProtoDifferenceUtilsKt.toProtoData(protoMapValue, packageFqName);
            }
        });
        this.companionObject = LazyKt.lazy(new Function0<ClassId>() { // from class: org.jetbrains.kotlin.incremental.KotlinClassInfo$companionObject$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassId invoke() {
                if (this.this$0.getClassKind() != KotlinClassHeader.Kind.CLASS) {
                    return null;
                }
                ProtoData protoData = this.this$0.getProtoData();
                Intrinsics.checkNotNull(protoData, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.ClassProtoData");
                String companionObjectName = ProtoDifferenceUtilsKt.getCompanionObjectName((ClassProtoData) protoData);
                if (companionObjectName != null) {
                    return this.this$0.getClassId().createNestedClassId(Name.identifier(companionObjectName));
                }
                return null;
            }
        });
        this.constantsInCompanionObject = LazyKt.lazy(new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.incremental.KotlinClassInfo$constantsInCompanionObject$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends String> invoke() {
                if (this.this$0.getClassKind() != KotlinClassHeader.Kind.CLASS) {
                    return null;
                }
                ProtoData protoData = this.this$0.getProtoData();
                Intrinsics.checkNotNull(protoData, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.ClassProtoData");
                ClassProtoData classProtoData = (ClassProtoData) protoData;
                if (ProtoDifferenceUtilsKt.isCompanionObject(classProtoData.getProto())) {
                    return ProtoDifferenceUtilsKt.getConstants(classProtoData);
                }
                return null;
            }
        });
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    public final KotlinClassHeader.Kind getClassKind() {
        return this.classKind;
    }

    public final String[] getClassHeaderData() {
        return this.classHeaderData;
    }

    public final String[] getClassHeaderStrings() {
        return this.classHeaderStrings;
    }

    public final String getMultifileClassName() {
        return this.multifileClassName;
    }

    public final ExtraInfo getExtraInfo() {
        return this.extraInfo;
    }

    /* JADX INFO: compiled from: KotlinClassInfo.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\tR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/incremental/KotlinClassInfo$ExtraInfo;", "", "classSnapshotExcludingMembers", "", "constantSnapshots", "", "", "inlineFunctionOrAccessorSnapshots", "Lorg/jetbrains/kotlin/inline/InlineFunctionOrAccessor;", "(Ljava/lang/Long;Ljava/util/Map;Ljava/util/Map;)V", "getClassSnapshotExcludingMembers", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getConstantSnapshots", "()Ljava/util/Map;", "getInlineFunctionOrAccessorSnapshots", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExtraInfo {
        private final Long classSnapshotExcludingMembers;
        private final Map<String, Long> constantSnapshots;
        private final Map<InlineFunctionOrAccessor, Long> inlineFunctionOrAccessorSnapshots;

        public ExtraInfo(Long l, Map<String, Long> constantSnapshots, Map<InlineFunctionOrAccessor, Long> inlineFunctionOrAccessorSnapshots) {
            Intrinsics.checkNotNullParameter(constantSnapshots, "constantSnapshots");
            Intrinsics.checkNotNullParameter(inlineFunctionOrAccessorSnapshots, "inlineFunctionOrAccessorSnapshots");
            this.classSnapshotExcludingMembers = l;
            this.constantSnapshots = constantSnapshots;
            this.inlineFunctionOrAccessorSnapshots = inlineFunctionOrAccessorSnapshots;
        }

        public final Long getClassSnapshotExcludingMembers() {
            return this.classSnapshotExcludingMembers;
        }

        public final Map<String, Long> getConstantSnapshots() {
            return this.constantSnapshots;
        }

        public final Map<InlineFunctionOrAccessor, Long> getInlineFunctionOrAccessorSnapshots() {
            return this.inlineFunctionOrAccessorSnapshots;
        }
    }

    public final JvmClassName getClassName() {
        return (JvmClassName) this.className.getValue();
    }

    public final ProtoMapValue getProtoMapValue() {
        return (ProtoMapValue) this.protoMapValue.getValue();
    }

    public final ProtoData getProtoData() {
        return (ProtoData) this.protoData.getValue();
    }

    public final ClassId getCompanionObject() {
        return (ClassId) this.companionObject.getValue();
    }

    public final List<String> getConstantsInCompanionObject() {
        return (List) this.constantsInCompanionObject.getValue();
    }

    /* JADX INFO: compiled from: KotlinClassInfo.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/KotlinClassInfo$Companion;", "", "()V", "createFrom", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "kotlinClass", "Lorg/jetbrains/kotlin/incremental/LocalFileKotlinClass;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "classContents", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinClassInfo createFrom(LocalFileKotlinClass kotlinClass) {
            Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
            ClassId classId = kotlinClass.getClassId();
            Intrinsics.checkNotNullExpressionValue(classId, "<get-classId>(...)");
            KotlinClassHeader classHeader = kotlinClass.getClassHeader();
            Intrinsics.checkNotNullExpressionValue(classHeader, "<get-classHeader>(...)");
            return createFrom(classId, classHeader, kotlinClass.getFileContents());
        }

        public final KotlinClassInfo createFrom(ClassId classId, KotlinClassHeader classHeader, byte[] classContents) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(classHeader, "classHeader");
            Intrinsics.checkNotNullParameter(classContents, "classContents");
            KotlinClassHeader.Kind kind = classHeader.getKind();
            String[] data = classHeader.getData();
            if (data == null && (data = classHeader.getIncompatibleData()) == null) {
                data = new String[0];
            }
            String[] strArr = data;
            String[] strings = classHeader.getStrings();
            if (strings == null) {
                strings = new String[0];
            }
            return new KotlinClassInfo(classId, kind, strArr, strings, classHeader.getMultifileClassName(), KotlinClassInfoKt.getExtraInfo(classHeader, classContents));
        }
    }
}
