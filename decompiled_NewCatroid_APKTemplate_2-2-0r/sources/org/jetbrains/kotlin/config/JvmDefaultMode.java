package org.jetbrains.kotlin.config;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 org.jetbrains.kotlin.config.JvmDefaultMode, still in use, count: 1, list:
  (r0v0 org.jetbrains.kotlin.config.JvmDefaultMode) from 0x0052: SPUT (r0v0 org.jetbrains.kotlin.config.JvmDefaultMode) (LINE:26) org.jetbrains.kotlin.config.JvmDefaultMode.DEFAULT org.jetbrains.kotlin.config.JvmDefaultMode
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
/* JADX INFO: compiled from: JvmDefaultMode.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u0000 \u00122\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0012B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\f\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\nj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "forAllMethodsWithBody", "", "getForAllMethodsWithBody", "()Z", "isCompatibility", "isEnabled", "DISABLE", "ENABLE", "ENABLE_WITH_DEFAULT_IMPLS", "ALL_COMPATIBILITY", "ALL_INCOMPATIBLE", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDefaultMode {
    DISABLE("disable"),
    ENABLE(CommonCompilerArguments.ENABLE),
    ENABLE_WITH_DEFAULT_IMPLS("compatibility"),
    ALL_COMPATIBILITY("all-compatibility"),
    ALL_INCOMPATIBLE(DevModeOverwritingStrategies.ALL);

    private final String description;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final JvmDefaultMode DEFAULT = new JvmDefaultMode("disable");

    @JvmStatic
    public static final JvmDefaultMode fromStringOrNull(String str) {
        return INSTANCE.fromStringOrNull(str);
    }

    public static EnumEntries<JvmDefaultMode> getEntries() {
        return $ENTRIES;
    }

    public static JvmDefaultMode valueOf(String str) {
        return (JvmDefaultMode) Enum.valueOf(JvmDefaultMode.class, str);
    }

    public static JvmDefaultMode[] values() {
        return (JvmDefaultMode[]) $VALUES.clone();
    }

    private JvmDefaultMode(String str) {
        super(str, i);
        this.description = str;
    }

    public final String getDescription() {
        return this.description;
    }

    static {
    }

    public final boolean isEnabled() {
        return this != DISABLE;
    }

    public final boolean isCompatibility() {
        return this == ENABLE_WITH_DEFAULT_IMPLS || this == ALL_COMPATIBILITY;
    }

    public final boolean getForAllMethodsWithBody() {
        return this == ALL_COMPATIBILITY || this == ALL_INCOMPATIBLE;
    }

    /* JADX INFO: compiled from: JvmDefaultMode.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/config/JvmDefaultMode$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "fromStringOrNull", "string", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final JvmDefaultMode fromStringOrNull(String string) {
            if (Intrinsics.areEqual(string, JvmDefaultMode.DISABLE.getDescription())) {
                return JvmDefaultMode.DISABLE;
            }
            if (Intrinsics.areEqual(string, JvmDefaultMode.ALL_COMPATIBILITY.getDescription())) {
                return JvmDefaultMode.ALL_COMPATIBILITY;
            }
            if (Intrinsics.areEqual(string, JvmDefaultMode.ALL_INCOMPATIBLE.getDescription())) {
                return JvmDefaultMode.ALL_INCOMPATIBLE;
            }
            return null;
        }
    }
}
