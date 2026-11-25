package org.jetbrains.kotlin.cfg.variable;

import com.beust.jcommander.Parameters;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: VariableControlFlowInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\n\u001a\u00020\u0005J\u0006\u0010\u000b\u001a\u00020\u0005J\b\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState;", "", "initState", "Lorg/jetbrains/kotlin/cfg/variable/InitState;", "isDeclared", "", "(Lorg/jetbrains/kotlin/cfg/variable/InitState;Z)V", "getInitState", "()Lorg/jetbrains/kotlin/cfg/variable/InitState;", "()Z", "definitelyInitialized", "mayBeInitialized", "toString", "", "Companion", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableControlFlowState {
    private final InitState initState;
    private final boolean isDeclared;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final VariableControlFlowState VS_IT = new VariableControlFlowState(InitState.INITIALIZED, true);
    private static final VariableControlFlowState VS_IF = new VariableControlFlowState(InitState.INITIALIZED, false);
    private static final VariableControlFlowState VS_ET = new VariableControlFlowState(InitState.INITIALIZED_EXHAUSTIVELY, true);
    private static final VariableControlFlowState VS_EF = new VariableControlFlowState(InitState.INITIALIZED_EXHAUSTIVELY, false);
    private static final VariableControlFlowState VS_UT = new VariableControlFlowState(InitState.UNKNOWN, true);
    private static final VariableControlFlowState VS_UF = new VariableControlFlowState(InitState.UNKNOWN, false);
    private static final VariableControlFlowState VS_NT = new VariableControlFlowState(InitState.NOT_INITIALIZED, true);
    private static final VariableControlFlowState VS_NF = new VariableControlFlowState(InitState.NOT_INITIALIZED, false);

    private VariableControlFlowState(InitState initState, boolean z) {
        this.initState = initState;
        this.isDeclared = z;
    }

    public final InitState getInitState() {
        return this.initState;
    }

    /* JADX INFO: renamed from: isDeclared, reason: from getter */
    public final boolean getIsDeclared() {
        return this.isDeclared;
    }

    public final boolean definitelyInitialized() {
        return this.initState == InitState.INITIALIZED;
    }

    public final boolean mayBeInitialized() {
        return this.initState != InitState.NOT_INITIALIZED;
    }

    public String toString() {
        if (this.initState != InitState.NOT_INITIALIZED || this.isDeclared) {
            return this.initState + (this.isDeclared ? "D" : "");
        }
        return Parameters.DEFAULT_OPTION_PREFIXES;
    }

    /* JADX INFO: compiled from: VariableControlFlowInfo.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eJ\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004J\u0016\u0010\f\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u000eJ\u000e\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState$Companion;", "", "()V", "VS_EF", "Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState;", "VS_ET", "VS_IF", "VS_IT", "VS_NF", "VS_NT", "VS_UF", "VS_UT", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "isInitialized", "", "isDeclared", "isDeclaredHere", "mergedEdgesData", "initState", "Lorg/jetbrains/kotlin/cfg/variable/InitState;", "createInitializedExhaustively", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: VariableControlFlowInfo.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[InitState.values().length];
                try {
                    iArr[InitState.INITIALIZED.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[InitState.INITIALIZED_EXHAUSTIVELY.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[InitState.UNKNOWN.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[InitState.NOT_INITIALIZED.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final VariableControlFlowState create(InitState initState, boolean isDeclared) {
            Intrinsics.checkNotNullParameter(initState, "initState");
            int i = WhenMappings.$EnumSwitchMapping$0[initState.ordinal()];
            if (i == 1) {
                return isDeclared ? VariableControlFlowState.VS_IT : VariableControlFlowState.VS_IF;
            }
            if (i == 2) {
                return isDeclared ? VariableControlFlowState.VS_ET : VariableControlFlowState.VS_EF;
            }
            if (i == 3) {
                return isDeclared ? VariableControlFlowState.VS_UT : VariableControlFlowState.VS_UF;
            }
            if (i == 4) {
                return isDeclared ? VariableControlFlowState.VS_NT : VariableControlFlowState.VS_NF;
            }
            throw new NoWhenBranchMatchedException();
        }

        public final VariableControlFlowState createInitializedExhaustively(boolean isDeclared) {
            return create(InitState.INITIALIZED_EXHAUSTIVELY, isDeclared);
        }

        public static /* synthetic */ VariableControlFlowState create$default(Companion companion, boolean z, boolean z2, int i, Object obj) {
            if ((i & 2) != 0) {
                z2 = false;
            }
            return companion.create(z, z2);
        }

        public final VariableControlFlowState create(boolean isInitialized, boolean isDeclared) {
            return create(isInitialized ? InitState.INITIALIZED : InitState.NOT_INITIALIZED, isDeclared);
        }

        public final VariableControlFlowState create(boolean isDeclaredHere, VariableControlFlowState mergedEdgesData) {
            return create(true, isDeclaredHere || (mergedEdgesData != null && mergedEdgesData.getIsDeclared()));
        }
    }
}
