package org.jetbrains.kotlin.metadata.deserialization;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: compiled from: VersionRequirement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\u0018\u0000 \u00192\u00020\u0001:\u0002\u0019\u001aB1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u0018\u001a\u00020\u000bH\u0016R\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "", "version", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Version;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement$VersionKind;", FirebaseAnalytics.Param.LEVEL, "Lkotlin/DeprecationLevel;", "errorCode", "", CrashHianalyticsData.MESSAGE, "", "(Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Version;Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement$VersionKind;Lkotlin/DeprecationLevel;Ljava/lang/Integer;Ljava/lang/String;)V", "getErrorCode", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getKind", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement$VersionKind;", "getLevel", "()Lkotlin/DeprecationLevel;", "getMessage", "()Ljava/lang/String;", "getVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Version;", "toString", "Companion", "Version", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VersionRequirement {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Integer errorCode;
    private final ProtoBuf.VersionRequirement.VersionKind kind;
    private final DeprecationLevel level;
    private final String message;
    private final Version version;

    public VersionRequirement(Version version, ProtoBuf.VersionRequirement.VersionKind kind, DeprecationLevel level, Integer num, String str) {
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(level, "level");
        this.version = version;
        this.kind = kind;
        this.level = level;
        this.errorCode = num;
        this.message = str;
    }

    public final Version getVersion() {
        return this.version;
    }

    public final ProtoBuf.VersionRequirement.VersionKind getKind() {
        return this.kind;
    }

    public final DeprecationLevel getLevel() {
        return this.level;
    }

    public final Integer getErrorCode() {
        return this.errorCode;
    }

    public final String getMessage() {
        return this.message;
    }

    /* JADX INFO: compiled from: VersionRequirement.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fJ\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J.\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00120\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00120\u0014J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001J\b\u0010\u001a\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Version;", "", "major", "", "minor", "patch", "(III)V", "getMajor", "()I", "getMinor", "getPatch", "asString", "", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "encode", "", "writeVersion", "Lkotlin/Function1;", "writeVersionFull", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "Companion", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Version {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        public static final Version INFINITY = new Version(256, 256, 256);
        private final int major;
        private final int minor;
        private final int patch;

        public static /* synthetic */ Version copy$default(Version version, int i, int i2, int i3, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i = version.major;
            }
            if ((i4 & 2) != 0) {
                i2 = version.minor;
            }
            if ((i4 & 4) != 0) {
                i3 = version.patch;
            }
            return version.copy(i, i2, i3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getMajor() {
            return this.major;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getMinor() {
            return this.minor;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getPatch() {
            return this.patch;
        }

        public final Version copy(int major, int minor, int patch) {
            return new Version(major, minor, patch);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Version)) {
                return false;
            }
            Version version = (Version) other;
            return this.major == version.major && this.minor == version.minor && this.patch == version.patch;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.major) * 31) + Integer.hashCode(this.minor)) * 31) + Integer.hashCode(this.patch);
        }

        public Version(int i, int i2, int i3) {
            this.major = i;
            this.minor = i2;
            this.patch = i3;
        }

        public /* synthetic */ Version(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, (i4 & 4) != 0 ? 0 : i3);
        }

        public final int getMajor() {
            return this.major;
        }

        public final int getMinor() {
            return this.minor;
        }

        public final int getPatch() {
            return this.patch;
        }

        public final String asString() {
            StringBuilder sbAppend;
            int i;
            if (this.patch == 0) {
                sbAppend = new StringBuilder().append(this.major).append('.');
                i = this.minor;
            } else {
                sbAppend = new StringBuilder().append(this.major).append('.').append(this.minor).append('.');
                i = this.patch;
            }
            return sbAppend.append(i).toString();
        }

        public final void encode(Function1<? super Integer, Unit> writeVersion, Function1<? super Integer, Unit> writeVersionFull) {
            int i;
            int i2;
            Intrinsics.checkNotNullParameter(writeVersion, "writeVersion");
            Intrinsics.checkNotNullParameter(writeVersionFull, "writeVersionFull");
            if (Intrinsics.areEqual(this, INFINITY)) {
                return;
            }
            int i3 = this.major;
            if (i3 > 7 || (i = this.minor) > 15 || (i2 = this.patch) > 127) {
                writeVersionFull.invoke(Integer.valueOf((this.minor << 8) | i3 | (this.patch << 16)));
            } else {
                writeVersion.invoke(Integer.valueOf((i << 3) | i3 | (i2 << 7)));
            }
        }

        public String toString() {
            return asString();
        }

        /* JADX INFO: compiled from: VersionRequirement.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\f\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u000fR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Version$Companion;", "", "()V", "INFINITY", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Version;", "MAJOR_BITS", "", "MAJOR_MASK", "MINOR_BITS", "MINOR_MASK", "PATCH_BITS", "PATCH_MASK", "decode", "version", "versionFull", "(Ljava/lang/Integer;Ljava/lang/Integer;)Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Version;", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Version decode(Integer version, Integer versionFull) {
                if (versionFull != null) {
                    return new Version(versionFull.intValue() & 255, (versionFull.intValue() >> 8) & 255, (versionFull.intValue() >> 16) & 255);
                }
                if (version != null) {
                    return new Version(version.intValue() & 7, (version.intValue() >> 3) & 15, (version.intValue() >> 7) & 127);
                }
                return Version.INFINITY;
            }
        }
    }

    public String toString() {
        return "since " + this.version + StringUtil.SPACE + this.level + (this.errorCode != null ? " error " + this.errorCode : "") + (this.message != null ? ": " + this.message : "");
    }

    /* JADX INFO: compiled from: VersionRequirement.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "id", "", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "table", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "", "proto", "Lorg/jetbrains/kotlin/protobuf/MessageLiteOrBuilder;", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: VersionRequirement.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ProtoBuf.VersionRequirement.Level.values().length];
                try {
                    iArr[ProtoBuf.VersionRequirement.Level.WARNING.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ProtoBuf.VersionRequirement.Level.ERROR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ProtoBuf.VersionRequirement.Level.HIDDEN.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<VersionRequirement> create(MessageLiteOrBuilder proto, NameResolver nameResolver, VersionRequirementTable table) {
            List<Integer> versionRequirementList;
            Intrinsics.checkNotNullParameter(proto, "proto");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(table, "table");
            if (proto instanceof ProtoBuf.Class) {
                versionRequirementList = ((ProtoBuf.Class) proto).getVersionRequirementList();
            } else if (proto instanceof ProtoBuf.Constructor) {
                versionRequirementList = ((ProtoBuf.Constructor) proto).getVersionRequirementList();
            } else if (proto instanceof ProtoBuf.Function) {
                versionRequirementList = ((ProtoBuf.Function) proto).getVersionRequirementList();
            } else if (proto instanceof ProtoBuf.Property) {
                versionRequirementList = ((ProtoBuf.Property) proto).getVersionRequirementList();
            } else {
                if (!(proto instanceof ProtoBuf.TypeAlias)) {
                    throw new IllegalStateException("Unexpected declaration: " + proto.getClass());
                }
                versionRequirementList = ((ProtoBuf.TypeAlias) proto).getVersionRequirementList();
            }
            Intrinsics.checkNotNull(versionRequirementList);
            ArrayList arrayList = new ArrayList();
            for (Integer num : versionRequirementList) {
                Companion companion = VersionRequirement.INSTANCE;
                Intrinsics.checkNotNull(num);
                VersionRequirement versionRequirementCreate = companion.create(num.intValue(), nameResolver, table);
                if (versionRequirementCreate != null) {
                    arrayList.add(versionRequirementCreate);
                }
            }
            return arrayList;
        }

        public final VersionRequirement create(int id2, NameResolver nameResolver, VersionRequirementTable table) {
            DeprecationLevel deprecationLevel;
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(table, "table");
            ProtoBuf.VersionRequirement versionRequirement = table.get(id2);
            if (versionRequirement == null) {
                return null;
            }
            Version versionDecode = Version.INSTANCE.decode(versionRequirement.hasVersion() ? Integer.valueOf(versionRequirement.getVersion()) : null, versionRequirement.hasVersionFull() ? Integer.valueOf(versionRequirement.getVersionFull()) : null);
            ProtoBuf.VersionRequirement.Level level = versionRequirement.getLevel();
            Intrinsics.checkNotNull(level);
            int i = WhenMappings.$EnumSwitchMapping$0[level.ordinal()];
            if (i == 1) {
                deprecationLevel = DeprecationLevel.WARNING;
            } else if (i == 2) {
                deprecationLevel = DeprecationLevel.ERROR;
            } else {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                deprecationLevel = DeprecationLevel.HIDDEN;
            }
            DeprecationLevel deprecationLevel2 = deprecationLevel;
            Integer numValueOf = versionRequirement.hasErrorCode() ? Integer.valueOf(versionRequirement.getErrorCode()) : null;
            String string = versionRequirement.hasMessage() ? nameResolver.getString(versionRequirement.getMessage()) : null;
            ProtoBuf.VersionRequirement.VersionKind versionKind = versionRequirement.getVersionKind();
            Intrinsics.checkNotNullExpressionValue(versionKind, "getVersionKind(...)");
            return new VersionRequirement(versionDecode, versionKind, deprecationLevel2, numValueOf, string);
        }
    }
}
