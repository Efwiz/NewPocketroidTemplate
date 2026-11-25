package org.jetbrains.kotlin.cfg.variable;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: VariableControlFlowInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00002\b\u0010\u0006\u001a\u0004\u0018\u00010\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/VariableUseState;", "", "priority", "", "(Ljava/lang/String;II)V", "merge", "variableUseState", "READ", "WRITTEN_AFTER_READ", "ONLY_WRITTEN_NEVER_READ", "UNUSED", "Companion", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum VariableUseState {
    READ(3),
    WRITTEN_AFTER_READ(2),
    ONLY_WRITTEN_NEVER_READ(1),
    UNUSED(0);

    private final int priority;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<VariableUseState> getEntries() {
        return $ENTRIES;
    }

    @JvmStatic
    public static final boolean isUsed(VariableUseState variableUseState) {
        return INSTANCE.isUsed(variableUseState);
    }

    VariableUseState(int i) {
        this.priority = i;
    }

    public final VariableUseState merge(VariableUseState variableUseState) {
        return (variableUseState == null || this.priority > variableUseState.priority) ? this : variableUseState;
    }

    /* JADX INFO: compiled from: VariableControlFlowInfo.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/VariableUseState$Companion;", "", "()V", "isUsed", "", "variableUseState", "Lorg/jetbrains/kotlin/cfg/variable/VariableUseState;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final boolean isUsed(VariableUseState variableUseState) {
            return (variableUseState == null || variableUseState == VariableUseState.UNUSED) ? false : true;
        }
    }
}
