package org.jetbrains.kotlin.resolve.calls.smartcasts;

import com.google.android.gms.cast.MediaError;
import com.google.android.gms.cast.MediaTrack;
import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: DataFlowValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001e\u001fB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u001a\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\fR\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "", "identifierInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "immanentNullability", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;", "(Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;)V", "canBeBound", "", "getCanBeBound", "()Z", "hashCode", "", "getIdentifierInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "getImmanentNullability", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;", "isStable", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", Namer.EQUALS_METHOD_NAME, "other", "toString", "", "Companion", Namer.CLASS_KIND_ENUM, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataFlowValue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final DataFlowValue ERROR = new DataFlowValue(IdentifierInfo.ERROR.INSTANCE, ErrorUtils.createErrorType(ErrorTypeKind.ERROR_DATA_FLOW_TYPE, new String[0]), Nullability.IMPOSSIBLE);
    private int hashCode;
    private final IdentifierInfo identifierInfo;
    private final Nullability immanentNullability;
    private final boolean isStable;
    private final KotlinType type;

    @JvmStatic
    public static final DataFlowValue nullValue(KotlinBuiltIns kotlinBuiltIns) {
        return INSTANCE.nullValue(kotlinBuiltIns);
    }

    public DataFlowValue(IdentifierInfo identifierInfo, KotlinType type, Nullability immanentNullability) {
        Intrinsics.checkNotNullParameter(identifierInfo, "identifierInfo");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(immanentNullability, "immanentNullability");
        this.identifierInfo = identifierInfo;
        this.type = type;
        this.immanentNullability = immanentNullability;
        this.isStable = getKind() == Kind.STABLE_VALUE || getKind() == Kind.STABLE_VARIABLE || getKind() == Kind.STABLE_COMPLEX_EXPRESSION || getKind() == Kind.LEGACY_STABLE_LOCAL_DELEGATED_PROPERTY || getKind() == Kind.LEGACY_ALIEN_BASE_PROPERTY || getKind() == Kind.LEGACY_ALIEN_BASE_PROPERTY_INHERITED_IN_INVISIBLE_CLASS;
    }

    public final IdentifierInfo getIdentifierInfo() {
        return this.identifierInfo;
    }

    public final KotlinType getType() {
        return this.type;
    }

    public /* synthetic */ DataFlowValue(IdentifierInfo identifierInfo, KotlinType kotlinType, Nullability nullability, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(identifierInfo, kotlinType, (i & 4) != 0 ? DataFlowValueKt.getImmanentNullability(kotlinType) : nullability);
    }

    public final Nullability getImmanentNullability() {
        return this.immanentNullability;
    }

    public final Kind getKind() {
        return this.identifierInfo.getKind();
    }

    /* JADX INFO: compiled from: DataFlowValue.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "", "str", "", MediaTrack.ROLE_DESCRIPTION, "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "toString", "STABLE_VALUE", "STABLE_COMPLEX_EXPRESSION", "LEGACY_STABLE_LOCAL_DELEGATED_PROPERTY", "PROPERTY_WITH_GETTER", "LEGACY_ALIEN_BASE_PROPERTY", "LEGACY_ALIEN_BASE_PROPERTY_INHERITED_IN_INVISIBLE_CLASS", "ALIEN_PUBLIC_PROPERTY", "STABLE_VARIABLE", "CAPTURED_VARIABLE", "MUTABLE_PROPERTY", "OTHER", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        STABLE_VALUE("stable val", null, 2, null),
        STABLE_COMPLEX_EXPRESSION("complex expression", ""),
        LEGACY_STABLE_LOCAL_DELEGATED_PROPERTY("local delegated property", null, 2, null),
        PROPERTY_WITH_GETTER("custom getter", "property that has open or custom getter"),
        LEGACY_ALIEN_BASE_PROPERTY("alien derived", "property declared in base class from different module"),
        LEGACY_ALIEN_BASE_PROPERTY_INHERITED_IN_INVISIBLE_CLASS("alien inherited in invisible", "property declared in base class from different module inherited in non-public API class"),
        ALIEN_PUBLIC_PROPERTY("alien public", "public API property declared in different module"),
        STABLE_VARIABLE("stable var", "local variable that can be changed since the check in a loop"),
        CAPTURED_VARIABLE("captured var", "local variable that is captured by a changing closure"),
        MUTABLE_PROPERTY("member", "mutable property that could have been changed by this time"),
        OTHER("other", "complex expression");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String description;
        private final String str;

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }

        Kind(String str, String str2) {
            this.str = str;
            this.description = str2;
        }

        /* synthetic */ Kind(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? str : str2);
        }

        public final String getDescription() {
            return this.description;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.str;
        }
    }

    /* JADX INFO: renamed from: isStable, reason: from getter */
    public final boolean getIsStable() {
        return this.isStable;
    }

    public final boolean getCanBeBound() {
        return this.identifierInfo.getCanBeBound();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DataFlowValue)) {
            return false;
        }
        DataFlowValue dataFlowValue = (DataFlowValue) other;
        return Intrinsics.areEqual(this.identifierInfo, dataFlowValue.identifierInfo) && Intrinsics.areEqual(this.type, dataFlowValue.type);
    }

    public String toString() {
        return new StringBuilder().append(getKind()).append(StringUtil.SPACE).append(this.identifierInfo).append(StringUtil.SPACE).append(this.immanentNullability).toString();
    }

    public int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.type.hashCode() + (this.identifierInfo.hashCode() * 31);
        this.hashCode = iHashCode;
        return iHashCode;
    }

    /* JADX INFO: compiled from: DataFlowValue.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Companion;", "", "()V", MediaError.ERROR_TYPE_ERROR, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "nullValue", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final DataFlowValue nullValue(KotlinBuiltIns builtIns) {
            Intrinsics.checkNotNullParameter(builtIns, "builtIns");
            IdentifierInfo.NULL r1 = IdentifierInfo.NULL.INSTANCE;
            SimpleType nullableNothingType = builtIns.getNullableNothingType();
            Intrinsics.checkNotNullExpressionValue(nullableNothingType, "getNullableNothingType(...)");
            return new DataFlowValue(r1, nullableNothingType, Nullability.NULL);
        }
    }
}
