package org.jetbrains.kotlin.config;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v3 org.jetbrains.kotlin.config.JVMAssertionsMode, still in use, count: 1, list:
  (r0v3 org.jetbrains.kotlin.config.JVMAssertionsMode) from 0x0046: SPUT (r0v3 org.jetbrains.kotlin.config.JVMAssertionsMode) (LINE:16) org.jetbrains.kotlin.config.JVMAssertionsMode.DEFAULT org.jetbrains.kotlin.config.JVMAssertionsMode
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
/* JADX INFO: compiled from: JVMAssertionsMode.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/config/JVMAssertionsMode;", "", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "ALWAYS_ENABLE", "ALWAYS_DISABLE", "JVM", "LEGACY", "Companion", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JVMAssertionsMode {
    ALWAYS_ENABLE("always-enable"),
    ALWAYS_DISABLE("always-disable"),
    JVM("jvm"),
    LEGACY("legacy");

    private final String description;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final JVMAssertionsMode DEFAULT = new JVMAssertionsMode("legacy");

    @JvmStatic
    public static final JVMAssertionsMode fromString(String str) {
        return INSTANCE.fromString(str);
    }

    @JvmStatic
    public static final JVMAssertionsMode fromStringOrNull(String str) {
        return INSTANCE.fromStringOrNull(str);
    }

    public static EnumEntries<JVMAssertionsMode> getEntries() {
        return $ENTRIES;
    }

    public static JVMAssertionsMode valueOf(String str) {
        return (JVMAssertionsMode) Enum.valueOf(JVMAssertionsMode.class, str);
    }

    public static JVMAssertionsMode[] values() {
        return (JVMAssertionsMode[]) $VALUES.clone();
    }

    private JVMAssertionsMode(String str) {
        super(str, i);
        this.description = str;
    }

    public final String getDescription() {
        return this.description;
    }

    static {
    }

    /* JADX INFO: compiled from: JVMAssertionsMode.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0007J\u0014\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/config/JVMAssertionsMode$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/config/JVMAssertionsMode;", "fromString", "string", "", "fromStringOrNull", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final JVMAssertionsMode fromStringOrNull(String string) {
            for (JVMAssertionsMode jVMAssertionsMode : JVMAssertionsMode.values()) {
                if (Intrinsics.areEqual(jVMAssertionsMode.getDescription(), string)) {
                    return jVMAssertionsMode;
                }
            }
            return null;
        }

        @JvmStatic
        public final JVMAssertionsMode fromString(String string) {
            JVMAssertionsMode jVMAssertionsModeFromStringOrNull = fromStringOrNull(string);
            return jVMAssertionsModeFromStringOrNull == null ? JVMAssertionsMode.DEFAULT : jVMAssertionsModeFromStringOrNull;
        }
    }
}
