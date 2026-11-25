package org.catrobat.paintroid.command.implementation;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.model.CommandManagerModel;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: AsyncCommandManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0013H\u0016J\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\bH\u0016J\u0012\u0010\u001e\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\u001f\u001a\u00020\u001aH\u0016J\b\u0010 \u001a\u00020\u001aH\u0016J\b\u0010!\u001a\u00020\"H\u0016J\n\u0010#\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010$\u001a\u00020\"H\u0016J\b\u0010%\u001a\u00020\u000eH\u0016J\u0012\u0010&\u001a\u00020\u001a2\b\u0010'\u001a\u0004\u0018\u00010\nH\u0016J\u001e\u0010(\u001a\u00020\u001a2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001a0*2\u0006\u0010+\u001a\u00020\u000eH\u0002J\b\u0010,\u001a\u00020\u001aH\u0002J\b\u0010-\u001a\u00020\u001aH\u0016J\b\u0010.\u001a\u00020\u001aH\u0016J\b\u0010/\u001a\u00020\u001aH\u0016J\b\u00100\u001a\u00020\u001aH\u0016J\u0010\u00101\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\bH\u0016J\b\u00102\u001a\u00020\u001aH\u0016J\u0010\u00103\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0013H\u0016J\b\u00104\u001a\u00020\u001aH\u0016J\b\u00105\u001a\u00020\u001aH\u0016J\b\u00106\u001a\u00020\u001aH\u0016J\u0010\u00107\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0013H\u0016J\b\u00108\u001a\u00020\u001aH\u0016J\b\u00109\u001a\u00020\u001aH\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000fR\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/AsyncCommandManager;", "Lorg/catrobat/paintroid/command/CommandManager;", "commandManager", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "(Lorg/catrobat/paintroid/command/CommandManager;Lorg/catrobat/paintroid/contract/LayerContracts$Model;)V", "commandListeners", "", "Lorg/catrobat/paintroid/command/CommandManager$CommandListener;", "commandManagerModel", "Lorg/catrobat/paintroid/model/CommandManagerModel;", "getCommandManagerModel", "()Lorg/catrobat/paintroid/model/CommandManagerModel;", "isBusy", "", "()Z", "isRedoAvailable", "isUndoAvailable", "lastExecutedCommand", "Lorg/catrobat/paintroid/command/Command;", "getLastExecutedCommand", "()Lorg/catrobat/paintroid/command/Command;", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "shuttingDown", "addCommand", "", "command", "addCommandListener", "commandListener", "addCommandWithoutUndo", "adjustUndoListForClippingTool", "executeAllCommands", "getColorCommandCount", "", "getCommandManagerModelForCatrobatImage", "getUndoCommandCount", "isLastColorCommandOnTop", "loadCommandsCatrobatImage", "model", "manageUndoAndRedo", "callFunction", "Lkotlin/Function0;", "condition", "notifyCommandPostExecute", "popFirstCommandInRedo", "popFirstCommandInUndo", LineReader.REDO, "redoInConnectedLinesMode", "removeCommandListener", "reset", "setInitialStateCommand", "shutdown", LineReader.UNDO, "undoIgnoringColorChanges", "undoIgnoringColorChangesAndAddCommand", "undoInClippingTool", "undoInConnectedLinesMode", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class AsyncCommandManager implements CommandManager {
    private final List<CommandManager.CommandListener> commandListeners;
    private final CommandManager commandManager;
    private final LayerContracts.Model layerModel;
    private Mutex mutex;
    private boolean shuttingDown;

    public AsyncCommandManager(CommandManager commandManager, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        this.commandManager = commandManager;
        this.layerModel = layerModel;
        this.commandListeners = new ArrayList();
        this.mutex = MutexKt.Mutex$default(false, 1, null);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public boolean isBusy() {
        return this.mutex.isLocked();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public Command getLastExecutedCommand() {
        return this.commandManager.getLastExecutedCommand();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public CommandManagerModel getCommandManagerModel() {
        return this.commandManager.getCommandManagerModel();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public boolean isUndoAvailable() {
        return this.commandManager.isUndoAvailable();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public boolean isRedoAvailable() {
        return this.commandManager.isRedoAvailable();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void addCommandListener(CommandManager.CommandListener commandListener) {
        Intrinsics.checkNotNullParameter(commandListener, "commandListener");
        this.commandListeners.add(commandListener);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void removeCommandListener(CommandManager.CommandListener commandListener) {
        Intrinsics.checkNotNullParameter(commandListener, "commandListener");
        this.commandListeners.remove(commandListener);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$addCommand$1, reason: invalid class name */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.command.implementation.AsyncCommandManager$addCommand$1", f = "AsyncCommandManager.kt", i = {0, 1}, l = {XKeySym.XK_egrave, 70}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$withLock$iv", "$this$withLock$iv"}, s = {"L$0", "L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Command $command;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Command command, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$command = command;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AsyncCommandManager.this.new AnonymousClass1(this.$command, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Mutex mutex;
            AsyncCommandManager asyncCommandManager;
            Command command;
            Mutex mutex2;
            Throwable th;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    mutex = AsyncCommandManager.this.mutex;
                    AsyncCommandManager asyncCommandManager2 = AsyncCommandManager.this;
                    Command command2 = this.$command;
                    this.L$0 = mutex;
                    this.L$1 = asyncCommandManager2;
                    this.L$2 = command2;
                    this.label = 1;
                    if (mutex.lock(null, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    asyncCommandManager = asyncCommandManager2;
                    command = command2;
                } else {
                    if (i != 1) {
                        if (i == 2) {
                            mutex2 = (Mutex) this.L$0;
                            try {
                                ResultKt.throwOnFailure(obj);
                                Unit unit = Unit.INSTANCE;
                                mutex2.unlock(null);
                                return Unit.INSTANCE;
                            } catch (Throwable th2) {
                                th = th2;
                                mutex2.unlock(null);
                                throw th;
                            }
                        }
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    command = (Command) this.L$2;
                    asyncCommandManager = (AsyncCommandManager) this.L$1;
                    Mutex mutex3 = (Mutex) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutex = mutex3;
                }
                if (!asyncCommandManager.shuttingDown) {
                    synchronized (asyncCommandManager.layerModel) {
                        asyncCommandManager.commandManager.addCommand(command);
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AsyncCommandManager$addCommand$1$1$2 asyncCommandManager$addCommand$1$1$2 = new AsyncCommandManager$addCommand$1$1$2(asyncCommandManager, null);
                this.L$0 = mutex;
                this.L$1 = null;
                this.L$2 = null;
                this.label = 2;
                if (BuildersKt.withContext(main, asyncCommandManager$addCommand$1$1$2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                mutex2 = mutex;
                Unit unit3 = Unit.INSTANCE;
                mutex2.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                mutex2 = mutex;
                th = th3;
                mutex2.unlock(null);
                throw th;
            }
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void addCommand(Command command) {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new AnonymousClass1(command, null), 3, null);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$addCommandWithoutUndo$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.command.implementation.AsyncCommandManager$addCommandWithoutUndo$1", f = "AsyncCommandManager.kt", i = {0, 1}, l = {XKeySym.XK_egrave, 84}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$withLock$iv", "$this$withLock$iv"}, s = {"L$0", "L$0"})
    static final class C42141 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Command $command;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C42141(Command command, Continuation<? super C42141> continuation) {
            super(2, continuation);
            this.$command = command;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AsyncCommandManager.this.new C42141(this.$command, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42141) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Mutex mutex;
            AsyncCommandManager asyncCommandManager;
            Command command;
            Mutex mutex2;
            Throwable th;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    mutex = AsyncCommandManager.this.mutex;
                    AsyncCommandManager asyncCommandManager2 = AsyncCommandManager.this;
                    Command command2 = this.$command;
                    this.L$0 = mutex;
                    this.L$1 = asyncCommandManager2;
                    this.L$2 = command2;
                    this.label = 1;
                    if (mutex.lock(null, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    asyncCommandManager = asyncCommandManager2;
                    command = command2;
                } else {
                    if (i != 1) {
                        if (i == 2) {
                            mutex2 = (Mutex) this.L$0;
                            try {
                                ResultKt.throwOnFailure(obj);
                                Unit unit = Unit.INSTANCE;
                                mutex2.unlock(null);
                                return Unit.INSTANCE;
                            } catch (Throwable th2) {
                                th = th2;
                                mutex2.unlock(null);
                                throw th;
                            }
                        }
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    command = (Command) this.L$2;
                    asyncCommandManager = (AsyncCommandManager) this.L$1;
                    Mutex mutex3 = (Mutex) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutex = mutex3;
                }
                if (!asyncCommandManager.shuttingDown) {
                    synchronized (asyncCommandManager.layerModel) {
                        asyncCommandManager.commandManager.addCommandWithoutUndo(command);
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AsyncCommandManager$addCommandWithoutUndo$1$1$2 asyncCommandManager$addCommandWithoutUndo$1$1$2 = new AsyncCommandManager$addCommandWithoutUndo$1$1$2(asyncCommandManager, null);
                this.L$0 = mutex;
                this.L$1 = null;
                this.L$2 = null;
                this.label = 2;
                if (BuildersKt.withContext(main, asyncCommandManager$addCommandWithoutUndo$1$1$2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                mutex2 = mutex;
                Unit unit3 = Unit.INSTANCE;
                mutex2.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                mutex2 = mutex;
                th = th3;
                mutex2.unlock(null);
                throw th;
            }
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void addCommandWithoutUndo(Command command) {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42141(command, null), 3, null);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$loadCommandsCatrobatImage$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.command.implementation.AsyncCommandManager$loadCommandsCatrobatImage$1", f = "AsyncCommandManager.kt", i = {0, 1}, l = {XKeySym.XK_egrave, 97}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$withLock$iv", "$this$withLock$iv"}, s = {"L$0", "L$0"})
    static final class C42161 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CommandManagerModel $model;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C42161(CommandManagerModel commandManagerModel, Continuation<? super C42161> continuation) {
            super(2, continuation);
            this.$model = commandManagerModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AsyncCommandManager.this.new C42161(this.$model, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42161) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Mutex mutex;
            AsyncCommandManager asyncCommandManager;
            CommandManagerModel commandManagerModel;
            Mutex mutex2;
            Throwable th;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    mutex = AsyncCommandManager.this.mutex;
                    AsyncCommandManager asyncCommandManager2 = AsyncCommandManager.this;
                    CommandManagerModel commandManagerModel2 = this.$model;
                    this.L$0 = mutex;
                    this.L$1 = asyncCommandManager2;
                    this.L$2 = commandManagerModel2;
                    this.label = 1;
                    if (mutex.lock(null, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    asyncCommandManager = asyncCommandManager2;
                    commandManagerModel = commandManagerModel2;
                } else {
                    if (i != 1) {
                        if (i == 2) {
                            mutex2 = (Mutex) this.L$0;
                            try {
                                ResultKt.throwOnFailure(obj);
                                Unit unit = Unit.INSTANCE;
                                mutex2.unlock(null);
                                return Unit.INSTANCE;
                            } catch (Throwable th2) {
                                th = th2;
                                mutex2.unlock(null);
                                throw th;
                            }
                        }
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    commandManagerModel = (CommandManagerModel) this.L$2;
                    asyncCommandManager = (AsyncCommandManager) this.L$1;
                    Mutex mutex3 = (Mutex) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutex = mutex3;
                }
                if (!asyncCommandManager.shuttingDown) {
                    synchronized (asyncCommandManager.layerModel) {
                        asyncCommandManager.commandManager.loadCommandsCatrobatImage(commandManagerModel);
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AsyncCommandManager$loadCommandsCatrobatImage$1$1$2 asyncCommandManager$loadCommandsCatrobatImage$1$1$2 = new AsyncCommandManager$loadCommandsCatrobatImage$1$1$2(asyncCommandManager, null);
                this.L$0 = mutex;
                this.L$1 = null;
                this.L$2 = null;
                this.label = 2;
                if (BuildersKt.withContext(main, asyncCommandManager$loadCommandsCatrobatImage$1$1$2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                mutex2 = mutex;
                Unit unit3 = Unit.INSTANCE;
                mutex2.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                mutex2 = mutex;
                th = th3;
                mutex2.unlock(null);
                throw th;
            }
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void loadCommandsCatrobatImage(CommandManagerModel model) {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42161(model, null), 3, null);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$undo$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* synthetic */ class C42201 extends FunctionReferenceImpl implements Function0<Unit> {
        C42201(Object obj) {
            super(0, obj, CommandManager.class, LineReader.UNDO, "undo()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((CommandManager) this.receiver).undo();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undo() {
        manageUndoAndRedo(new C42201(this.commandManager), isUndoAvailable());
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$redo$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* synthetic */ class C42181 extends FunctionReferenceImpl implements Function0<Unit> {
        C42181(Object obj) {
            super(0, obj, CommandManager.class, LineReader.REDO, "redo()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((CommandManager) this.receiver).redo();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void redo() {
        manageUndoAndRedo(new C42181(this.commandManager), isRedoAvailable());
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void reset() {
        synchronized (this.layerModel) {
            this.commandManager.reset();
            Unit unit = Unit.INSTANCE;
        }
        notifyCommandPostExecute();
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void shutdown() {
        this.shuttingDown = true;
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$undoIgnoringColorChanges$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* synthetic */ class C42211 extends FunctionReferenceImpl implements Function0<Unit> {
        C42211(Object obj) {
            super(0, obj, CommandManager.class, "undoIgnoringColorChanges", "undoIgnoringColorChanges()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((CommandManager) this.receiver).undoIgnoringColorChanges();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undoIgnoringColorChanges() {
        manageUndoAndRedo(new C42211(this.commandManager), isUndoAvailable());
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$undoIgnoringColorChangesAndAddCommand$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.command.implementation.AsyncCommandManager$undoIgnoringColorChangesAndAddCommand$1", f = "AsyncCommandManager.kt", i = {0, 1}, l = {XKeySym.XK_egrave, 135}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$withLock$iv", "$this$withLock$iv"}, s = {"L$0", "L$0"})
    static final class C42221 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Command $command;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C42221(Command command, Continuation<? super C42221> continuation) {
            super(2, continuation);
            this.$command = command;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AsyncCommandManager.this.new C42221(this.$command, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42221) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Mutex mutex;
            AsyncCommandManager asyncCommandManager;
            Command command;
            Mutex mutex2;
            Throwable th;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    mutex = AsyncCommandManager.this.mutex;
                    AsyncCommandManager asyncCommandManager2 = AsyncCommandManager.this;
                    Command command2 = this.$command;
                    this.L$0 = mutex;
                    this.L$1 = asyncCommandManager2;
                    this.L$2 = command2;
                    this.label = 1;
                    if (mutex.lock(null, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    asyncCommandManager = asyncCommandManager2;
                    command = command2;
                } else {
                    if (i != 1) {
                        if (i == 2) {
                            mutex2 = (Mutex) this.L$0;
                            try {
                                ResultKt.throwOnFailure(obj);
                                Unit unit = Unit.INSTANCE;
                                mutex2.unlock(null);
                                return Unit.INSTANCE;
                            } catch (Throwable th2) {
                                th = th2;
                                mutex2.unlock(null);
                                throw th;
                            }
                        }
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    command = (Command) this.L$2;
                    asyncCommandManager = (AsyncCommandManager) this.L$1;
                    Mutex mutex3 = (Mutex) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutex = mutex3;
                }
                if (!asyncCommandManager.shuttingDown) {
                    synchronized (asyncCommandManager.layerModel) {
                        if (asyncCommandManager.isUndoAvailable()) {
                            asyncCommandManager.commandManager.undoIgnoringColorChangesAndAddCommand(command);
                        }
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AsyncCommandManager$undoIgnoringColorChangesAndAddCommand$1$1$2 asyncCommandManager$undoIgnoringColorChangesAndAddCommand$1$1$2 = new AsyncCommandManager$undoIgnoringColorChangesAndAddCommand$1$1$2(asyncCommandManager, null);
                this.L$0 = mutex;
                this.L$1 = null;
                this.L$2 = null;
                this.label = 2;
                if (BuildersKt.withContext(main, asyncCommandManager$undoIgnoringColorChangesAndAddCommand$1$1$2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                mutex2 = mutex;
                Unit unit3 = Unit.INSTANCE;
                mutex2.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                mutex2 = mutex;
                th = th3;
                mutex2.unlock(null);
                throw th;
            }
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undoIgnoringColorChangesAndAddCommand(Command command) {
        Intrinsics.checkNotNullParameter(command, "command");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42221(command, null), 3, null);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$undoInConnectedLinesMode$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* synthetic */ class C42231 extends FunctionReferenceImpl implements Function0<Unit> {
        C42231(Object obj) {
            super(0, obj, CommandManager.class, "undoInConnectedLinesMode", "undoInConnectedLinesMode()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((CommandManager) this.receiver).undoInConnectedLinesMode();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undoInConnectedLinesMode() {
        manageUndoAndRedo(new C42231(this.commandManager), isUndoAvailable());
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$redoInConnectedLinesMode$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* synthetic */ class C42191 extends FunctionReferenceImpl implements Function0<Unit> {
        C42191(Object obj) {
            super(0, obj, CommandManager.class, "redoInConnectedLinesMode", "redoInConnectedLinesMode()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((CommandManager) this.receiver).redoInConnectedLinesMode();
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void redoInConnectedLinesMode() {
        manageUndoAndRedo(new C42191(this.commandManager), isRedoAvailable());
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public CommandManagerModel getCommandManagerModelForCatrobatImage() {
        CommandManagerModel commandManagerModelForCatrobatImage;
        synchronized (this.layerModel) {
            commandManagerModelForCatrobatImage = this.commandManager.getCommandManagerModelForCatrobatImage();
        }
        return commandManagerModelForCatrobatImage;
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void setInitialStateCommand(Command command) {
        Intrinsics.checkNotNullParameter(command, "command");
        synchronized (this.layerModel) {
            this.commandManager.setInitialStateCommand(command);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void adjustUndoListForClippingTool() {
        synchronized (this.layerModel) {
            this.commandManager.adjustUndoListForClippingTool();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void undoInClippingTool() {
        synchronized (this.layerModel) {
            this.commandManager.undoInClippingTool();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void popFirstCommandInUndo() {
        synchronized (this.layerModel) {
            this.commandManager.popFirstCommandInUndo();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void popFirstCommandInRedo() {
        synchronized (this.layerModel) {
            this.commandManager.popFirstCommandInRedo();
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$manageUndoAndRedo$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.command.implementation.AsyncCommandManager$manageUndoAndRedo$1", f = "AsyncCommandManager.kt", i = {0, 1}, l = {XKeySym.XK_egrave, 184}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$withLock$iv", "$this$withLock$iv"}, s = {"L$0", "L$0"})
    static final class C42171 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function0<Unit> $callFunction;
        final /* synthetic */ boolean $condition;
        Object L$0;
        Object L$1;
        Object L$2;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C42171(boolean z, Function0<Unit> function0, Continuation<? super C42171> continuation) {
            super(2, continuation);
            this.$condition = z;
            this.$callFunction = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AsyncCommandManager.this.new C42171(this.$condition, this.$callFunction, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42171) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Mutex mutex;
            AsyncCommandManager asyncCommandManager;
            boolean z;
            Function0<Unit> function0;
            Mutex mutex2;
            Throwable th;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    mutex = AsyncCommandManager.this.mutex;
                    asyncCommandManager = AsyncCommandManager.this;
                    z = this.$condition;
                    Function0<Unit> function1 = this.$callFunction;
                    this.L$0 = mutex;
                    this.L$1 = asyncCommandManager;
                    this.L$2 = function1;
                    this.Z$0 = z;
                    this.label = 1;
                    if (mutex.lock(null, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    function0 = function1;
                } else {
                    if (i != 1) {
                        if (i == 2) {
                            mutex2 = (Mutex) this.L$0;
                            try {
                                ResultKt.throwOnFailure(obj);
                                Unit unit = Unit.INSTANCE;
                                mutex2.unlock(null);
                                return Unit.INSTANCE;
                            } catch (Throwable th2) {
                                th = th2;
                                mutex2.unlock(null);
                                throw th;
                            }
                        }
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    z = this.Z$0;
                    function0 = (Function0) this.L$2;
                    asyncCommandManager = (AsyncCommandManager) this.L$1;
                    Mutex mutex3 = (Mutex) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutex = mutex3;
                }
                if (!asyncCommandManager.shuttingDown) {
                    synchronized (asyncCommandManager.layerModel) {
                        if (z) {
                            function0.invoke();
                        }
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AsyncCommandManager$manageUndoAndRedo$1$1$2 asyncCommandManager$manageUndoAndRedo$1$1$2 = new AsyncCommandManager$manageUndoAndRedo$1$1$2(asyncCommandManager, null);
                this.L$0 = mutex;
                this.L$1 = null;
                this.L$2 = null;
                this.label = 2;
                if (BuildersKt.withContext(main, asyncCommandManager$manageUndoAndRedo$1$1$2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                mutex2 = mutex;
                Unit unit3 = Unit.INSTANCE;
                mutex2.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                mutex2 = mutex;
                th = th3;
                mutex2.unlock(null);
                throw th;
            }
        }
    }

    private final void manageUndoAndRedo(Function0<Unit> callFunction, boolean condition) {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42171(condition, callFunction, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyCommandPostExecute() {
        if (this.shuttingDown) {
            return;
        }
        Iterator<CommandManager.CommandListener> it = this.commandListeners.iterator();
        while (it.hasNext()) {
            it.next().commandPostExecute();
        }
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.command.implementation.AsyncCommandManager$executeAllCommands$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AsyncCommandManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.command.implementation.AsyncCommandManager$executeAllCommands$1", f = "AsyncCommandManager.kt", i = {0, 1}, l = {XKeySym.XK_egrave, 207}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$withLock$iv", "$this$withLock$iv"}, s = {"L$0", "L$0"})
    static final class C42151 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        int label;

        C42151(Continuation<? super C42151> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AsyncCommandManager.this.new C42151(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42151) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Mutex mutex;
            AsyncCommandManager asyncCommandManager;
            Mutex mutex2;
            Throwable th;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    mutex = AsyncCommandManager.this.mutex;
                    asyncCommandManager = AsyncCommandManager.this;
                    this.L$0 = mutex;
                    this.L$1 = asyncCommandManager;
                    this.label = 1;
                    if (mutex.lock(null, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        if (i == 2) {
                            mutex2 = (Mutex) this.L$0;
                            try {
                                ResultKt.throwOnFailure(obj);
                                Unit unit = Unit.INSTANCE;
                                mutex2.unlock(null);
                                return Unit.INSTANCE;
                            } catch (Throwable th2) {
                                th = th2;
                                mutex2.unlock(null);
                                throw th;
                            }
                        }
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    asyncCommandManager = (AsyncCommandManager) this.L$1;
                    Mutex mutex3 = (Mutex) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutex = mutex3;
                }
                if (!asyncCommandManager.shuttingDown) {
                    synchronized (asyncCommandManager.layerModel) {
                        asyncCommandManager.commandManager.executeAllCommands();
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AsyncCommandManager$executeAllCommands$1$1$2 asyncCommandManager$executeAllCommands$1$1$2 = new AsyncCommandManager$executeAllCommands$1$1$2(asyncCommandManager, null);
                this.L$0 = mutex;
                this.L$1 = null;
                this.label = 2;
                if (BuildersKt.withContext(main, asyncCommandManager$executeAllCommands$1$1$2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                mutex2 = mutex;
                Unit unit3 = Unit.INSTANCE;
                mutex2.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                mutex2 = mutex;
                th = th3;
                mutex2.unlock(null);
                throw th;
            }
        }
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public void executeAllCommands() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C42151(null), 3, null);
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public int getUndoCommandCount() {
        int undoCommandCount;
        synchronized (this.layerModel) {
            undoCommandCount = this.commandManager.getUndoCommandCount();
        }
        return undoCommandCount;
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public int getColorCommandCount() {
        int colorCommandCount;
        synchronized (this.layerModel) {
            colorCommandCount = this.commandManager.getColorCommandCount();
        }
        return colorCommandCount;
    }

    @Override // org.catrobat.paintroid.command.CommandManager
    public boolean isLastColorCommandOnTop() {
        boolean zIsLastColorCommandOnTop;
        synchronized (this.layerModel) {
            zIsLastColorCommandOnTop = this.commandManager.isLastColorCommandOnTop();
        }
        return zIsLastColorCommandOnTop;
    }
}
