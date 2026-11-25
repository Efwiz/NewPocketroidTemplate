package org.jetbrains.kotlin.daemon.common;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v1 org.jetbrains.kotlin.daemon.common.OSKind, still in use, count: 1, list:
  (r1v1 org.jetbrains.kotlin.daemon.common.OSKind) from 0x005a: MOVE (r0v23 org.jetbrains.kotlin.daemon.common.OSKind) = (r1v1 org.jetbrains.kotlin.daemon.common.OSKind)
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: FileSystemUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u0000 \u00072\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/OSKind;", "", "(Ljava/lang/String;I)V", "Windows", "OSX", "Unix", "Unknown", "Companion", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OSKind {
    Windows,
    OSX,
    Unix,
    Unknown;

    private static final /* synthetic */ EnumEntries $ENTRIES;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final OSKind current;

    public static EnumEntries<OSKind> getEntries() {
        return $ENTRIES;
    }

    public static OSKind valueOf(String str) {
        return (OSKind) Enum.valueOf(OSKind.class, str);
    }

    public static OSKind[] values() {
        return (OSKind[]) $VALUES.clone();
    }

    private OSKind() {
        super(str, i);
    }

    static {
        OSKind oSKind;
        OSKind[] oSKindArrValues = values();
        $ENTRIES = EnumEntriesKt.enumEntries(oSKindArrValues);
        INSTANCE = new Companion(null);
        String lowerCaseAsciiOnly = CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(CompilerSystemProperties.OS_NAME.getSafeValue());
        if (!StringsKt.startsWith$default(lowerCaseAsciiOnly, "windows", false, 2, (Object) null)) {
            if (StringsKt.startsWith$default(lowerCaseAsciiOnly, "mac os", false, 2, (Object) null)) {
                oSKind = oSKind;
            } else {
                String str = lowerCaseAsciiOnly;
                oSKind = (StringsKt.contains$default((CharSequence) str, (CharSequence) "unix", false, 2, (Object) null) || StringsKt.startsWith$default(lowerCaseAsciiOnly, "linux", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "bsd", false, 2, (Object) null) || StringsKt.startsWith$default(lowerCaseAsciiOnly, "irix", false, 2, (Object) null) || StringsKt.startsWith$default(lowerCaseAsciiOnly, "mpe/ix", false, 2, (Object) null) || StringsKt.startsWith$default(lowerCaseAsciiOnly, "aix", false, 2, (Object) null) || StringsKt.startsWith$default(lowerCaseAsciiOnly, "hp-ux", false, 2, (Object) null) || StringsKt.startsWith$default(lowerCaseAsciiOnly, "sunos", false, 2, (Object) null) || StringsKt.startsWith$default(lowerCaseAsciiOnly, "sun os", false, 2, (Object) null) || StringsKt.startsWith$default(lowerCaseAsciiOnly, "solaris", false, 2, (Object) null)) ? oSKind : oSKind;
            }
        }
        current = oSKind;
    }

    /* JADX INFO: compiled from: FileSystemUtils.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/OSKind$Companion;", "", "()V", "current", "Lorg/jetbrains/kotlin/daemon/common/OSKind;", "getCurrent", "()Lorg/jetbrains/kotlin/daemon/common/OSKind;", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final OSKind getCurrent() {
            return OSKind.current;
        }
    }
}
