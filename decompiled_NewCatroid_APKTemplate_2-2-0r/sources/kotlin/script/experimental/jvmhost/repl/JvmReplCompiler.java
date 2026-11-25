package kotlin.script.experimental.jvmhost.repl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.impl.RunSuspendKt;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import kotlin.script.experimental.jvm.util.DiagnosticsHelpersKt;
import kotlin.script.experimental.util.LinkedSnippet;
import org.jetbrains.kotlin.cli.common.repl.IReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.LineId;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.cli.common.repl.ReplCompileResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCompilerWithoutCheck;
import org.jetbrains.kotlin.cli.common.repl.ReplHistoryRecord;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.ReplCompilationState;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerState;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase;

/* JADX INFO: compiled from: legacyReplCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lkotlin/script/experimental/jvmhost/repl/JvmReplCompiler;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompilerWithoutCheck;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "(Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/script/experimental/host/ScriptingHostConfiguration;)V", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getScriptCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "compile", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "createState", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmReplCompiler implements ReplCompilerWithoutCheck {
    private final ScriptingHostConfiguration hostConfiguration;
    private final ScriptCompilationConfiguration scriptCompilationConfiguration;

    public JvmReplCompiler(ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration hostConfiguration) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        this.scriptCompilationConfiguration = scriptCompilationConfiguration;
        this.hostConfiguration = hostConfiguration;
    }

    public final ScriptCompilationConfiguration getScriptCompilationConfiguration() {
        return this.scriptCompilationConfiguration;
    }

    public /* synthetic */ JvmReplCompiler(ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration scriptingHostConfiguration, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(scriptCompilationConfiguration, (i & 2) != 0 ? JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration() : scriptingHostConfiguration);
    }

    public final ScriptingHostConfiguration getHostConfiguration() {
        return this.hostConfiguration;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.CreateReplStageStateAction
    public IReplStageState<?> createState(ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        return new JvmReplCompilerState(new Function1<ScriptCompilationConfiguration, ReplCompilationState<ReplCodeAnalyzerBase>>() { // from class: kotlin.script.experimental.jvmhost.repl.JvmReplCompiler.createState.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ReplCompilationState<ReplCodeAnalyzerBase> invoke(ScriptCompilationConfiguration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return KJvmReplCompilerBase.INSTANCE.createCompilationState(it, JvmReplCompiler.this.getHostConfiguration());
            }
        }, lock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplCompileAction
    public ReplCompileResult compile(IReplStageState<?> state, ReplCodeLine codeLine) {
        ReplCompileResult.Error error;
        KotlinType second;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        ReentrantReadWriteLock lock = state.getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            JvmReplCompilerState jvmReplCompilerState = (JvmReplCompilerState) state.asState(JvmReplCompilerState.class);
            SourceCode sourceCode = LegacyReplCompilationKt.toSourceCode(codeLine, this.scriptCompilationConfiguration);
            KJvmReplCompilerBase kJvmReplCompilerBase = new KJvmReplCompilerBase(HostConfigurationKt.withDefaultsFrom(this.hostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), jvmReplCompilerState);
            LineId lineId = new LineId(codeLine.getNo(), codeLine.getGeneration(), sourceCode.hashCode());
            ResultWithDiagnostics resultWithDiagnostics = (ResultWithDiagnostics) RunSuspendKt.internalScriptingRunSuspend(new JvmReplCompiler$compile$1$res$1(kJvmReplCompilerBase, sourceCode, this, lineId, null));
            if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
                IReplStageHistory<ScriptDescriptor> history = kJvmReplCompilerBase.getState().getHistory();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(history, 10));
                Iterator<ScriptDescriptor> it = history.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ReplHistoryRecord) it.next()).getId());
                }
                ArrayList arrayList2 = arrayList;
                String name2 = sourceCode.getName();
                Intrinsics.checkNotNull(name2);
                List listEmptyList = CollectionsKt.emptyList();
                boolean z = ((KJvmCompiledScript) ((LinkedSnippet) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue()).get()).getResultField() != null;
                List listEmptyList2 = CollectionsKt.emptyList();
                Pair<String, KotlinType> resultField = ((KJvmCompiledScript) ((LinkedSnippet) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue()).get()).getResultField();
                error = new ReplCompileResult.CompiledClasses(lineId, arrayList2, name2, listEmptyList, z, listEmptyList2, (resultField == null || (second = resultField.getSecond()) == null) ? null : second.getTypeName(), ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue());
            } else {
                String strJoinToString$default = CollectionsKt.joinToString$default(resultWithDiagnostics.getReports(), "\n", null, null, 0, null, null, 62, null);
                if (DiagnosticsHelpersKt.isIncomplete(resultWithDiagnostics)) {
                    error = new ReplCompileResult.Incomplete(strJoinToString$default);
                } else {
                    error = new ReplCompileResult.Error(strJoinToString$default, null, 2, null);
                }
            }
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            return error;
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
        }
    }
}
