package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerState.Compilation;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmReplCompilation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u00020\u00040\u0003:\u0001'B#\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u00000\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020 H\u0004J\u0013\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00020\u0007¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0013H\u0016J\u0010\u0010&\u001a\u00020 2\u0006\u0010#\u001a\u00020\u0007H\u0002R\u0012\u0010\u000b\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\fR\u0014\u0010\r\u001a\u00028\u00008@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001cR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState;", "CompilationT", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState$Compilation;", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "createCompilation", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Lkotlin/jvm/functions/Function1;Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "_compilation", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState$Compilation;", "compilation", "getCompilation$kotlin_scripting_compiler", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState$Compilation;", "getCreateCompilation", "()Lkotlin/jvm/functions/Function1;", "currentGeneration", "", "getCurrentGeneration", "()I", "history", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerStageHistory;", "getHistory", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerStageHistory;", "isCompilationInitialized", "", "()Z", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "dispose", "", "finalize", "getCompilationState", "scriptCompilationConfiguration", "(Lkotlin/script/experimental/api/ScriptCompilationConfiguration;)Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState$Compilation;", "getNextLineNo", "initializeCompilation", "Compilation", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmReplCompilerState<CompilationT extends Compilation> implements IReplStageState<ScriptDescriptor> {
    private CompilationT _compilation;
    private final Function1<ScriptCompilationConfiguration, CompilationT> createCompilation;
    private final JvmReplCompilerStageHistory<CompilationT> history;
    private final ReentrantReadWriteLock lock;

    /* JADX INFO: compiled from: jvmReplCompilation.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState$Compilation;", "", "baseScriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getBaseScriptCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "getDisposable", "()Lcom/intellij/openapi/Disposable;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "getEnvironment", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Compilation {
        ScriptCompilationConfiguration getBaseScriptCompilationConfiguration();

        Disposable getDisposable();

        KotlinCoreEnvironment getEnvironment();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JvmReplCompilerState(Function1<? super ScriptCompilationConfiguration, ? extends CompilationT> createCompilation, ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(createCompilation, "createCompilation");
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.createCompilation = createCompilation;
        this.lock = lock;
        this.history = new JvmReplCompilerStageHistory<>(this);
    }

    public final Function1<ScriptCompilationConfiguration, CompilationT> getCreateCompilation() {
        return this.createCompilation;
    }

    public /* synthetic */ JvmReplCompilerState(Function1 function1, ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, (i & 2) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public ReentrantReadWriteLock getLock() {
        return this.lock;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public IReplStageHistory<ScriptDescriptor> getHistory() {
        return this.history;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getCurrentGeneration() {
        IReplStageHistory<ScriptDescriptor> history = getHistory();
        Intrinsics.checkNotNull(history, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory<*>");
        return ((BasicReplStageHistory) history).getCurrentGeneration().get();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerStageHistory] */
    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public int getNextLineNo() {
        return getHistory().getCurrentLineNumber().getAndIncrement();
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageState
    public void dispose() {
        Disposable disposable;
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            CompilationT compilationt = this._compilation;
            if (compilationt != null && (disposable = compilationt.getDisposable()) != null) {
                Disposer.dispose(disposable);
            }
            this._compilation = null;
            super.dispose();
            Unit unit = Unit.INSTANCE;
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    protected final void finalize() {
        dispose();
    }

    public final CompilationT getCompilationState(ScriptCompilationConfiguration scriptCompilationConfiguration) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            if (this._compilation == null) {
                initializeCompilation(scriptCompilationConfiguration);
            }
            CompilationT compilationt = this._compilation;
            Intrinsics.checkNotNull(compilationt);
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            return compilationt;
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    public final CompilationT getCompilation$kotlin_scripting_compiler() {
        CompilationT compilationt = this._compilation;
        if (compilationt != null) {
            return compilationt;
        }
        throw new IllegalStateException("Compilation state is either not initializad or already destroyed");
    }

    public final boolean isCompilationInitialized() {
        return this._compilation != null;
    }

    private final void initializeCompilation(ScriptCompilationConfiguration scriptCompilationConfiguration) {
        if (this._compilation != null) {
            throw new IllegalStateException("Compilation state is already initialized");
        }
        this._compilation = this.createCompilation.invoke(scriptCompilationConfiguration);
    }
}
