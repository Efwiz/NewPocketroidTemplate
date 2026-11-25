package org.jetbrains.kotlin.ir.linkage.partial;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageMode, still in use, count: 1, list:
  (r0v0 org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageMode) from 0x002a: SPUT (r0v0 org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageMode) (LINE:26) org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageMode.DEFAULT org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageMode
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
/* JADX INFO: compiled from: PartialLinkageConfig.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u0000 \b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageMode;", "", "isEnabled", "", "(Ljava/lang/String;IZ)V", "()Z", "ENABLE", "DISABLE", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageMode {
    ENABLE(true),
    DISABLE(false);

    private final boolean isEnabled;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PartialLinkageMode DEFAULT = new PartialLinkageMode(true);

    public static EnumEntries<PartialLinkageMode> getEntries() {
        return $ENTRIES;
    }

    public static PartialLinkageMode valueOf(String str) {
        return (PartialLinkageMode) Enum.valueOf(PartialLinkageMode.class, str);
    }

    public static PartialLinkageMode[] values() {
        return (PartialLinkageMode[]) $VALUES.clone();
    }

    private PartialLinkageMode(boolean z) {
        super(str, i);
        this.isEnabled = z;
    }

    /* JADX INFO: renamed from: isEnabled, reason: from getter */
    public final boolean getIsEnabled() {
        return this.isEnabled;
    }

    static {
    }

    /* JADX INFO: compiled from: PartialLinkageConfig.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageMode$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageMode;", "getDEFAULT", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageMode;", "resolveMode", ASTExpr.DEFAULT_MAP_KEY_NAME, "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PartialLinkageMode getDEFAULT() {
            return PartialLinkageMode.DEFAULT;
        }

        public final PartialLinkageMode resolveMode(String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            for (PartialLinkageMode partialLinkageMode : PartialLinkageMode.values()) {
                if (StringsKt.equals(partialLinkageMode.name(), key, true)) {
                    return partialLinkageMode;
                }
            }
            return null;
        }
    }
}
