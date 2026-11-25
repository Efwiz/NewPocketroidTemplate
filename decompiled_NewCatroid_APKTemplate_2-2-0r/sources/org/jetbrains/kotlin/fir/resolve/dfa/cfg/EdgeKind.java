package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: ControlFlowGraph.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\tR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeKind;", "", "usedInDfa", "", "usedInDeadDfa", "usedInCfa", "isBack", "isDead", "(Ljava/lang/String;IZZZZZ)V", "()Z", "getUsedInCfa", "getUsedInDeadDfa", "getUsedInDfa", "Forward", "DeadForward", "DfgForward", "CfgForward", "CfgBackward", "DeadBackward", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum EdgeKind {
    Forward(true, true, true, false, false),
    DeadForward(false, true, true, false, true),
    DfgForward(true, true, false, false, false),
    CfgForward(false, false, true, false, false),
    CfgBackward(false, false, true, true, false),
    DeadBackward(false, false, true, true, true);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final boolean isBack;
    private final boolean isDead;
    private final boolean usedInCfa;
    private final boolean usedInDeadDfa;
    private final boolean usedInDfa;

    public static EnumEntries<EdgeKind> getEntries() {
        return $ENTRIES;
    }

    EdgeKind(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.usedInDfa = z;
        this.usedInDeadDfa = z2;
        this.usedInCfa = z3;
        this.isBack = z4;
        this.isDead = z5;
    }

    public final boolean getUsedInDfa() {
        return this.usedInDfa;
    }

    public final boolean getUsedInDeadDfa() {
        return this.usedInDeadDfa;
    }

    public final boolean getUsedInCfa() {
        return this.usedInCfa;
    }

    /* JADX INFO: renamed from: isBack, reason: from getter */
    public final boolean getIsBack() {
        return this.isBack;
    }

    /* JADX INFO: renamed from: isDead, reason: from getter */
    public final boolean getIsDead() {
        return this.isDead;
    }
}
