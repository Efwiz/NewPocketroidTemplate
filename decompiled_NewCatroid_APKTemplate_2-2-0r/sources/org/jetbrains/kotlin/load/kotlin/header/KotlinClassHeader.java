package org.jetbrains.kotlin.load.kotlin.header;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.reandroid.arsc.chunk.SpecBlock;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;

/* JADX INFO: compiled from: KotlinClassHeader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\f\u0018\u00002\u00020\u0001:\u000267Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\b\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011J\b\u00103\u001a\u00020\bH\u0016J\u0014\u00104\u001a\u00020\u0019*\u00020\r2\u0006\u00105\u001a\u00020\rH\u0002R\u001b\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0017\u0010\u0013R\u0011\u0010\u0018\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u001c\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001aR\u0011\u0010\u001d\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0019\u0010\"\u001a\u0004\u0018\u00010#8F¢\u0006\f\u0012\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\u0013\u0010(\u001a\u0004\u0018\u00010\b8F¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020\b0,8F¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b/\u0010*R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u001b\u0010\n\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b2\u0010\u0013¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "data", "", "", "incompatibleData", "strings", "extraString", "extraInt", "", "packageName", "serializedIr", "", "(Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;[B)V", "getData", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getExtraInt", "()I", "getIncompatibleData", "isPreRelease", "", "()Z", "isScript", "isUnstableFirBinary", "isUnstableJvmIrBinary", "getKind", "()Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "multifileClassKind", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$MultifileClassKind;", "getMultifileClassKind$annotations", "()V", "getMultifileClassKind", "()Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$MultifileClassKind;", "multifileClassName", "getMultifileClassName", "()Ljava/lang/String;", "multifilePartNames", "", "getMultifilePartNames", "()Ljava/util/List;", "getPackageName", "getSerializedIr", "()[B", "getStrings", "toString", "has", SpecBlock.NAME_flag, Namer.CLASS_KIND_ENUM, "MultifileClassKind", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassHeader {
    private final String[] data;
    private final int extraInt;
    private final String extraString;
    private final String[] incompatibleData;
    private final Kind kind;
    private final JvmMetadataVersion metadataVersion;
    private final String packageName;
    private final byte[] serializedIr;
    private final String[] strings;

    /* JADX INFO: compiled from: KotlinClassHeader.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$MultifileClassKind;", "", "(Ljava/lang/String;I)V", "DELEGATING", "INHERITING", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum MultifileClassKind {
        DELEGATING,
        INHERITING;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<MultifileClassKind> getEntries() {
            return $ENTRIES;
        }
    }

    public static /* synthetic */ void getMultifileClassKind$annotations() {
    }

    private final boolean has(int i, int i2) {
        return (i & i2) != 0;
    }

    public KotlinClassHeader(Kind kind, JvmMetadataVersion metadataVersion, String[] strArr, String[] strArr2, String[] strArr3, String str, int i, String str2, byte[] bArr) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.kind = kind;
        this.metadataVersion = metadataVersion;
        this.data = strArr;
        this.incompatibleData = strArr2;
        this.strings = strArr3;
        this.extraString = str;
        this.extraInt = i;
        this.packageName = str2;
        this.serializedIr = bArr;
    }

    public final Kind getKind() {
        return this.kind;
    }

    public final JvmMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    public final String[] getData() {
        return this.data;
    }

    public final String[] getIncompatibleData() {
        return this.incompatibleData;
    }

    public final String[] getStrings() {
        return this.strings;
    }

    public final int getExtraInt() {
        return this.extraInt;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final byte[] getSerializedIr() {
        return this.serializedIr;
    }

    /* JADX INFO: compiled from: KotlinClassHeader.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u0000 \r2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\rB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "", "id", "", "(Ljava/lang/String;II)V", "getId", "()I", GrsBaseInfo.CountryCodeSource.UNKNOWN, "CLASS", "FILE_FACADE", "SYNTHETIC_CLASS", "MULTIFILE_CLASS", "MULTIFILE_CLASS_PART", "Companion", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        UNKNOWN(0),
        CLASS(1),
        FILE_FACADE(2),
        SYNTHETIC_CLASS(3),
        MULTIFILE_CLASS(4),
        MULTIFILE_CLASS_PART(5);

        private static final Map<Integer, Kind> entryById;
        private final int id;
        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        @JvmStatic
        public static final Kind getById(int i) {
            return INSTANCE.getById(i);
        }

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }

        Kind(int i) {
            this.id = i;
        }

        public final int getId() {
            return this.id;
        }

        static {
            Kind[] kindArrValues = values();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kindArrValues.length), 16));
            for (Kind kind : kindArrValues) {
                linkedHashMap.put(Integer.valueOf(kind.id), kind);
            }
            entryById = linkedHashMap;
        }

        /* JADX INFO: compiled from: KotlinClassHeader.kt */
        @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0005H\u0007R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind$Companion;", "", "()V", "entryById", "", "", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "getById", "id", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            public final Kind getById(int id2) {
                Kind kind = (Kind) Kind.entryById.get(Integer.valueOf(id2));
                return kind == null ? Kind.UNKNOWN : kind;
            }
        }
    }

    public final String getMultifileClassName() {
        String str = this.extraString;
        if (this.kind == Kind.MULTIFILE_CLASS_PART) {
            return str;
        }
        return null;
    }

    public final List<String> getMultifilePartNames() {
        String[] strArr = this.data;
        if (this.kind != Kind.MULTIFILE_CLASS) {
            strArr = null;
        }
        List<String> listAsList = strArr != null ? ArraysKt.asList(strArr) : null;
        return listAsList == null ? CollectionsKt.emptyList() : listAsList;
    }

    public final MultifileClassKind getMultifileClassKind() {
        if (this.kind != Kind.MULTIFILE_CLASS && this.kind != Kind.MULTIFILE_CLASS_PART) {
            return null;
        }
        if (has(this.extraInt, 1)) {
            return MultifileClassKind.INHERITING;
        }
        return MultifileClassKind.DELEGATING;
    }

    public final boolean isUnstableJvmIrBinary() {
        return has(this.extraInt, 16) && !has(this.extraInt, 32);
    }

    public final boolean isUnstableFirBinary() {
        return has(this.extraInt, 64) && !has(this.extraInt, 32);
    }

    public final boolean isPreRelease() {
        return has(this.extraInt, 2);
    }

    public final boolean isScript() {
        return has(this.extraInt, 4);
    }

    public String toString() {
        return this.kind + " version=" + this.metadataVersion;
    }
}
