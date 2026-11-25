package org.jetbrains.kotlin.resolve.calls.model;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.mlsdk.common.internal.client.event.MonitorResult;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'TYPE_MISMATCH' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: ArgumentMapping.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchStatus;", "", "isError", "", "(Ljava/lang/String;IZ)V", "()Z", MonitorResult.SUCCESS, "TYPE_MISMATCH", "ARGUMENT_HAS_NO_TYPE", "MATCH_MODULO_UNINFERRED_TYPES", GrsBaseInfo.CountryCodeSource.UNKNOWN, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentMatchStatus {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ArgumentMatchStatus[] $VALUES;
    public static final ArgumentMatchStatus MATCH_MODULO_UNINFERRED_TYPES;
    public static final ArgumentMatchStatus TYPE_MISMATCH;
    private final boolean isError;
    public static final ArgumentMatchStatus SUCCESS = new ArgumentMatchStatus(MonitorResult.SUCCESS, 0, false);
    public static final ArgumentMatchStatus ARGUMENT_HAS_NO_TYPE = new ArgumentMatchStatus("ARGUMENT_HAS_NO_TYPE", 2, false, 1, null);
    public static final ArgumentMatchStatus UNKNOWN = new ArgumentMatchStatus(GrsBaseInfo.CountryCodeSource.UNKNOWN, 4, false, 1, null);

    private static final /* synthetic */ ArgumentMatchStatus[] $values() {
        return new ArgumentMatchStatus[]{SUCCESS, TYPE_MISMATCH, ARGUMENT_HAS_NO_TYPE, MATCH_MODULO_UNINFERRED_TYPES, UNKNOWN};
    }

    public static EnumEntries<ArgumentMatchStatus> getEntries() {
        return $ENTRIES;
    }

    public static ArgumentMatchStatus valueOf(String str) {
        return (ArgumentMatchStatus) Enum.valueOf(ArgumentMatchStatus.class, str);
    }

    public static ArgumentMatchStatus[] values() {
        return (ArgumentMatchStatus[]) $VALUES.clone();
    }

    private ArgumentMatchStatus(String str, int i, boolean z) {
        super(str, i);
        this.isError = z;
    }

    /* synthetic */ ArgumentMatchStatus(String str, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i2 & 1) != 0 ? true : z);
    }

    /* JADX INFO: renamed from: isError, reason: from getter */
    public final boolean getIsError() {
        return this.isError;
    }

    static {
        int i = 1;
        TYPE_MISMATCH = new ArgumentMatchStatus("TYPE_MISMATCH", i, false, 1, null);
        MATCH_MODULO_UNINFERRED_TYPES = new ArgumentMatchStatus("MATCH_MODULO_UNINFERRED_TYPES", 3, false, i, null);
        ArgumentMatchStatus[] argumentMatchStatusArr$values = $values();
        $VALUES = argumentMatchStatusArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(argumentMatchStatusArr$values);
    }
}
