package org.jetbrains.kotlin.cli.common.repl;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: GenericReplEvaluator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B5\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0014\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J0\u0010\u0018\u001a\u00020\u00192\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\t\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluator;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvaluator;", "baseClasspath", "", "Ljava/io/File;", "baseClassloader", "Ljava/lang/ClassLoader;", "fallbackScriptArgs", "Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "repeatingMode", "Lorg/jetbrains/kotlin/cli/common/repl/ReplRepeatingMode;", "(Ljava/lang/Iterable;Ljava/lang/ClassLoader;Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;Lorg/jetbrains/kotlin/cli/common/repl/ReplRepeatingMode;)V", "getBaseClassloader", "()Ljava/lang/ClassLoader;", "getBaseClasspath", "()Ljava/lang/Iterable;", "getFallbackScriptArgs", "()Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "getRepeatingMode", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplRepeatingMode;", "createState", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "eval", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "state", "compileResult", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "scriptArgs", "invokeWrapper", "Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class GenericReplEvaluator implements ReplEvaluator {
    private final ClassLoader baseClassloader;
    private final Iterable<File> baseClasspath;
    private final ScriptArgsWithTypes fallbackScriptArgs;
    private final ReplRepeatingMode repeatingMode;

    /* JADX INFO: compiled from: GenericReplEvaluator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ReplRepeatingMode.values().length];
            try {
                iArr[ReplRepeatingMode.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ReplRepeatingMode.REPEAT_ONLY_MOST_RECENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ReplRepeatingMode.REPEAT_ANY_PREVIOUS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public GenericReplEvaluator(Iterable<? extends File> baseClasspath, ClassLoader classLoader, ScriptArgsWithTypes scriptArgsWithTypes, ReplRepeatingMode repeatingMode) {
        Intrinsics.checkNotNullParameter(baseClasspath, "baseClasspath");
        Intrinsics.checkNotNullParameter(repeatingMode, "repeatingMode");
        this.baseClasspath = baseClasspath;
        this.baseClassloader = classLoader;
        this.fallbackScriptArgs = scriptArgsWithTypes;
        this.repeatingMode = repeatingMode;
    }

    public final Iterable<File> getBaseClasspath() {
        return this.baseClasspath;
    }

    public /* synthetic */ GenericReplEvaluator(Iterable iterable, ClassLoader classLoader, ScriptArgsWithTypes scriptArgsWithTypes, ReplRepeatingMode replRepeatingMode, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iterable, (i & 2) != 0 ? Thread.currentThread().getContextClassLoader() : classLoader, (i & 4) != 0 ? null : scriptArgsWithTypes, (i & 8) != 0 ? ReplRepeatingMode.REPEAT_ONLY_MOST_RECENT : replRepeatingMode);
    }

    public final ClassLoader getBaseClassloader() {
        return this.baseClassloader;
    }

    protected final ScriptArgsWithTypes getFallbackScriptArgs() {
        return this.fallbackScriptArgs;
    }

    protected final ReplRepeatingMode getRepeatingMode() {
        return this.repeatingMode;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.CreateReplStageStateAction
    public IReplStageState<?> createState(ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        return new GenericReplEvaluatorState(this.baseClasspath, this.baseClassloader, lock);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v26, types: [java.lang.Thread] */
    /* JADX WARN: Type inference failed for: r0v33, types: [java.lang.Thread] */
    /* JADX WARN: Type inference failed for: r10v11, types: [java.lang.ClassLoader] */
    /* JADX WARN: Type inference failed for: r10v12, types: [java.lang.ClassLoader] */
    /* JADX WARN: Type inference failed for: r10v13, types: [java.lang.ClassLoader] */
    /* JADX WARN: Type inference failed for: r10v14, types: [org.jetbrains.kotlin.cli.common.repl.EvalClassWithInstanceAndLoader] */
    /* JADX WARN: Type inference failed for: r11v12, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v16, types: [kotlin.reflect.KClass] */
    /* JADX WARN: Type inference failed for: r11v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r12v3, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r12v5, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.Thread] */
    /* JADX WARN: Type inference failed for: r7v25, types: [java.lang.Thread] */
    /* JADX WARN: Type inference failed for: r8v24 */
    /* JADX WARN: Type inference failed for: r8v25 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v7, types: [org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat] */
    @Override // org.jetbrains.kotlin.cli.common.repl.ReplEvalAction
    public ReplEvalResult eval(IReplStageState<?> state, ReplCompileResult.CompiledClasses compileResult, ScriptArgsWithTypes scriptArgs, InvokeWrapper invokeWrapper) throws Throwable {
        int i;
        ?? historyActionsForNoRepeat;
        ArrayList arrayList;
        ArrayList arrayListEmptyList;
        ReplEvalResult.UnitResult unitResult;
        Field field;
        Field field2;
        Class<?> cls;
        KClass<? extends Object>[] scriptArgsTypes;
        int no;
        ILineId id2;
        Object next;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(compileResult, "compileResult");
        ReentrantReadWriteLock lock = state.getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            GenericReplEvaluatorState genericReplEvaluatorState = (GenericReplEvaluatorState) state.asState(GenericReplEvaluatorState.class);
            int i3 = WhenMappings.$EnumSwitchMapping$0[this.repeatingMode.ordinal()];
            i = 2;
            if (i3 == 1) {
                historyActionsForNoRepeat = new HistoryActionsForNoRepeat(genericReplEvaluatorState);
            } else if (i3 == 2) {
                ReplHistoryRecord<EvalClassWithInstanceAndLoader> replHistoryRecordPeek = genericReplEvaluatorState.getHistory().peek();
                historyActionsForNoRepeat = (replHistoryRecordPeek == null || !Intrinsics.areEqual(replHistoryRecordPeek.getId(), compileResult.getLineId())) ? new HistoryActionsForNoRepeat(genericReplEvaluatorState) : new HistoryActionsForRepeatRecentOnly(genericReplEvaluatorState);
            } else {
                if (i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                Iterator<EvalClassWithInstanceAndLoader> it = genericReplEvaluatorState.getHistory().iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((ReplHistoryRecord) next).getId(), compileResult.getLineId()));
                ReplHistoryRecord replHistoryRecord = (ReplHistoryRecord) next;
                historyActionsForNoRepeat = replHistoryRecord == null ? new HistoryActionsForNoRepeat(genericReplEvaluatorState) : new HistoryActionsForRepeatAny(genericReplEvaluatorState, replHistoryRecord);
            }
            Pair<ReplHistoryRecord<EvalClassWithInstanceAndLoader>, ILineId> pairFirstMismatch = historyActionsForNoRepeat.firstMismatch(CollectionsKt.asSequence(compileResult.getPreviousLines()));
            try {
                if (pairFirstMismatch != null) {
                    ReplHistoryRecord<EvalClassWithInstanceAndLoader> first = pairFirstMismatch.getFirst();
                    if (first == null || (id2 = first.getId()) == null) {
                        ILineId second = pairFirstMismatch.getSecond();
                        no = second != null ? second.getNo() : -1;
                    } else {
                        no = id2.getNo();
                    }
                    ReplEvalResult.HistoryMismatch historyMismatch = new ReplEvalResult.HistoryMismatch(no);
                    for (int i4 = 0; i4 < readHoldCount; i4++) {
                        lock2.lock();
                    }
                    writeLock.unlock();
                    return historyMismatch;
                }
                try {
                    Pair<ClassLoader, Class<? extends Object>> pairProcessClasses = historyActionsForNoRepeat.processClasses(compileResult);
                    ClassLoader classLoaderComponent1 = pairProcessClasses.component1();
                    Class<? extends Object> clsComponent2 = pairProcessClasses.component2();
                    ScriptArgsWithTypes scriptArgsWithTypes = scriptArgs == null ? this.fallbackScriptArgs : scriptArgs;
                    Object[] scriptArgs2 = scriptArgsWithTypes != null ? scriptArgsWithTypes.getScriptArgs() : null;
                    if (scriptArgsWithTypes == null || (scriptArgsTypes = scriptArgsWithTypes.getScriptArgsTypes()) == null) {
                        arrayList = null;
                    } else {
                        ArrayList arrayList2 = new ArrayList(scriptArgsTypes.length);
                        for (KClass<? extends Object> kClass : scriptArgsTypes) {
                            arrayList2.add(JvmClassMappingKt.getJavaClass((KClass) kClass));
                        }
                        arrayList = arrayList2;
                    }
                    Class[] clsArr = new Class[1];
                    clsArr[0] = Object[].class;
                    if (scriptArgs2 != null) {
                        ArrayList arrayList3 = new ArrayList(scriptArgs2.length);
                        int length = scriptArgs2.length;
                        int i5 = 0;
                        int i6 = 0;
                        while (i5 < length) {
                            Object obj = scriptArgs2[i5];
                            int i7 = i6 + 1;
                            if (arrayList == null || (cls = (Class) CollectionsKt.getOrNull(arrayList, i6)) == null) {
                                cls = obj != null ? obj.getClass() : Object.class;
                            }
                            arrayList3.add(cls);
                            i5++;
                            i6 = i7;
                        }
                        arrayListEmptyList = arrayList3;
                    } else {
                        arrayListEmptyList = CollectionsKt.emptyList();
                    }
                    Class[] clsArr2 = (Class[]) ArraysKt.plus((Object[]) clsArr, (Collection) arrayListEmptyList);
                    SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                    List<EvalClassWithInstanceAndLoader> effectiveHistory = historyActionsForNoRepeat.getEffectiveHistory();
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(effectiveHistory, 10));
                    Iterator it2 = effectiveHistory.iterator();
                    while (it2.hasNext()) {
                        arrayList4.add(((EvalClassWithInstanceAndLoader) it2.next()).getInstance());
                    }
                    ArrayList arrayList5 = arrayList4;
                    if (!Boolean.valueOf(!arrayList5.isEmpty()).booleanValue()) {
                        arrayList5 = null;
                    }
                    spreadBuilder.add(arrayList5 != null ? arrayList5.toArray(new Object[0]) : null);
                    if (scriptArgs2 == null) {
                        i = 0;
                        scriptArgs2 = new Object[0];
                    } else {
                        i = 0;
                    }
                    spreadBuilder.addSpread(scriptArgs2);
                    final Object[] array = spreadBuilder.toArray(new Object[spreadBuilder.size()]);
                    final Constructor<? extends Object> constructor = clsComponent2.getConstructor((Class[]) Arrays.copyOf(clsArr2, clsArr2.length));
                    historyActionsForNoRepeat.addPlaceholder(compileResult.getLineId(), new EvalClassWithInstanceAndLoader(JvmClassMappingKt.getKotlinClass(clsComponent2), null, classLoaderComponent1, invokeWrapper));
                    ?? contextClassLoader = Thread.currentThread().getContextClassLoader();
                    Thread.currentThread().setContextClassLoader(classLoaderComponent1);
                    ?? kotlinClass = ".<init>";
                    try {
                        try {
                            try {
                                Object objInvoke = invokeWrapper != null ? invokeWrapper.invoke(new Function0<Object>() { // from class: org.jetbrains.kotlin.cli.common.repl.GenericReplEvaluator$eval$1$scriptInstance$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        Constructor<? extends Object> constructor2 = constructor;
                                        Object[] objArr = array;
                                        return constructor2.newInstance(Arrays.copyOf(objArr, objArr.length));
                                    }
                                }) : constructor.newInstance(Arrays.copyOf(array, array.length));
                                historyActionsForNoRepeat.removePlaceholder(compileResult.getLineId());
                                Thread.currentThread().setContextClassLoader(contextClassLoader);
                                LineId lineId = compileResult.getLineId();
                                kotlinClass = JvmClassMappingKt.getKotlinClass(clsComponent2);
                                contextClassLoader = new EvalClassWithInstanceAndLoader(kotlinClass, objInvoke, classLoaderComponent1, invokeWrapper);
                                historyActionsForNoRepeat.addFinal(lineId, contextClassLoader);
                                if (compileResult.getHasResult()) {
                                    String strScriptResultFieldName = ReplUtilKt.scriptResultFieldName(compileResult.getLineId().getNo());
                                    Field[] declaredFields = clsComponent2.getDeclaredFields();
                                    Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
                                    Field[] fieldArr = declaredFields;
                                    int length2 = fieldArr.length;
                                    int i8 = i;
                                    while (true) {
                                        if (i8 >= length2) {
                                            field = null;
                                            break;
                                        }
                                        field = fieldArr[i8];
                                        if (Intrinsics.areEqual(field.getName(), strScriptResultFieldName)) {
                                            break;
                                        }
                                        i8++;
                                    }
                                    Field field3 = field;
                                    if (field3 != null) {
                                        field3.setAccessible(true);
                                        field2 = field3;
                                    } else {
                                        field2 = null;
                                    }
                                    Intrinsics.checkNotNull(field2);
                                    unitResult = new ReplEvalResult.ValueResult(strScriptResultFieldName, field2.get(objInvoke), compileResult.getType(), null, 8, null);
                                } else {
                                    unitResult = new ReplEvalResult.UnitResult();
                                }
                                for (int i9 = i; i9 < readHoldCount; i9++) {
                                    lock2.lock();
                                }
                                writeLock.unlock();
                                return unitResult;
                            } catch (InvocationTargetException e) {
                                Throwable cause = e.getCause();
                                Intrinsics.checkNotNull(cause);
                                String strRenderReplStackTrace = ReplUtilKt.renderReplStackTrace(cause, clsComponent2.getName() + kotlinClass);
                                Throwable targetException = e.getTargetException();
                                ReplEvalResult.Error.Runtime runtime = new ReplEvalResult.Error.Runtime(strRenderReplStackTrace, targetException instanceof Exception ? (Exception) targetException : null);
                                historyActionsForNoRepeat.removePlaceholder(compileResult.getLineId());
                                Thread.currentThread().setContextClassLoader(contextClassLoader);
                                for (int i10 = i; i10 < readHoldCount; i10++) {
                                    lock2.lock();
                                }
                                writeLock.unlock();
                                return runtime;
                            }
                        } catch (Throwable th) {
                            historyActionsForNoRepeat.removePlaceholder(compileResult.getLineId());
                            Thread.currentThread().setContextClassLoader(contextClassLoader);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        Throwable cause2 = th2.getCause();
                        Intrinsics.checkNotNull(cause2);
                        ReplEvalResult.Error.Runtime runtime2 = new ReplEvalResult.Error.Runtime(ReplUtilKt.renderReplStackTrace(cause2, clsComponent2.getName() + kotlinClass), th2 instanceof Exception ? th2 : null);
                        historyActionsForNoRepeat.removePlaceholder(compileResult.getLineId());
                        Thread.currentThread().setContextClassLoader(contextClassLoader);
                        for (int i11 = i; i11 < readHoldCount; i11++) {
                            lock2.lock();
                        }
                        writeLock.unlock();
                        return runtime2;
                    }
                } catch (Exception e2) {
                    String message = e2.getMessage();
                    if (message == null) {
                        message = "unknown";
                    }
                    ReplEvalResult.Error.Runtime runtime3 = new ReplEvalResult.Error.Runtime(message, e2);
                    for (int i12 = 0; i12 < readHoldCount; i12++) {
                        lock2.lock();
                    }
                    writeLock.unlock();
                    return runtime3;
                }
            } catch (Throwable th3) {
                th = th3;
                for (int i13 = i; i13 < readHoldCount; i13++) {
                    lock2.lock();
                }
                writeLock.unlock();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            i = 0;
        }
    }
}
