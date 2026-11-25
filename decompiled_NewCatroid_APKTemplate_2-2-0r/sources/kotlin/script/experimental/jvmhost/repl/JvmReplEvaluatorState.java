package kotlin.script.experimental.jvmhost.repl;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;

/* JADX INFO: compiled from: legacyReplEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u001c\u0012\u0018\u0012\u0016\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00020\u0001B\u0017\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR.\u0010\u000e\u001a\u001c\u0012\u0018\u0012\u0016\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lkotlin/script/experimental/jvmhost/repl/JvmReplEvaluatorState;", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "Lkotlin/Pair;", "Lkotlin/reflect/KClass;", "", "scriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "currentGeneration", "", "getCurrentGeneration", "()I", "history", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "getHistory", "()Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmReplEvaluatorState implements IReplStageState<Pair<? extends KClass<?>, ? extends Object>> {
    private final IReplStageHistory<Pair<KClass<?>, Object>> history;
    private final ReentrantReadWriteLock lock;

    public JvmReplEvaluatorState(ScriptEvaluationConfiguration scriptEvaluationConfiguration, ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfiguration, "scriptEvaluationConfiguration");
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
        this.history = new ReplStageHistoryWithReplace(getLock());
    }

    public /* synthetic */ JvmReplEvaluatorState(ScriptEvaluationConfiguration scriptEvaluationConfiguration, ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(scriptEvaluationConfiguration, (i & 2) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public ReentrantReadWriteLock getLock() {
        return this.lock;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public IReplStageHistory<Pair<? extends KClass<?>, ? extends Object>> getHistory() {
        return this.history;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getCurrentGeneration() {
        IReplStageHistory<Pair<? extends KClass<?>, ? extends Object>> history = getHistory();
        Intrinsics.checkNotNull(history, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory<*>");
        return ((BasicReplStageHistory) history).getCurrentGeneration().get();
    }
}
