package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: FirScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\t\u0010\u0005\u001a\u00020\u0004H\u0086\u0002J\u0011\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0000H\u0086\u0002J\u0006\u0010\b\u001a\u00020\u0004j\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "", "(Ljava/lang/String;I)V", ES6Iterator.NEXT_METHOD, "", "not", "plus", "other", "stop", "STOP", "NEXT", "NONE", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ProcessorAction {
    STOP,
    NEXT,
    NONE;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: compiled from: FirScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProcessorAction.values().length];
            try {
                iArr[ProcessorAction.STOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProcessorAction.NEXT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProcessorAction.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static EnumEntries<ProcessorAction> getEntries() {
        return $ENTRIES;
    }

    public final boolean not() {
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2 || i == 3) {
            return false;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final boolean stop() {
        return this == STOP;
    }

    public final boolean next() {
        return this != STOP;
    }

    public final ProcessorAction plus(ProcessorAction other) {
        Intrinsics.checkNotNullParameter(other, "other");
        ProcessorAction processorAction = NEXT;
        return (this == processorAction || other == processorAction) ? processorAction : this;
    }
}
