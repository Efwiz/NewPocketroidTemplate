package org.jetbrains.kotlin.backend.common.phaser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.LoggingContext;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: performByIrFile.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00040\u0003B-\u0012\u001e\u0010\u0005\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ0\u0010\f\u001a\"\u0012\u001e\u0012\u001c\u0012\u0004\u0012\u00020\u000e\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000f0\r0\u00062\u0006\u0010\u0010\u001a\u00020\u000eH\u0016J3\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u00152\u0006\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u0004H\u0016¢\u0006\u0002\u0010\u0018J;\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u00152\u0006\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u000eH\u0002¢\u0006\u0002\u0010\u001bJ3\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u00152\u0006\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010\u0018R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0005\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/PerformByIrFilePhase;", "Context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeCompilerPhase;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "lower", "", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "copyBeforeLowering", "", "(Ljava/util/List;Z)V", "getNamedSubphases", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "startDepth", "invoke", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "phaserState", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "invokeParallel", "nThreads", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;I)Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "invokeSequential", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PerformByIrFilePhase<Context extends CommonBackendContext> implements SameTypeCompilerPhase<Context, IrModuleFragment> {
    private final boolean copyBeforeLowering;
    private final List<CompilerPhase<Context, IrFile, IrFile>> lower;

    /* JADX WARN: Multi-variable type inference failed */
    public PerformByIrFilePhase(List<? extends CompilerPhase<? super Context, IrFile, IrFile>> lower, boolean z) {
        Intrinsics.checkNotNullParameter(lower, "lower");
        this.lower = lower;
        this.copyBeforeLowering = z;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public /* bridge */ /* synthetic */ Object invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
        return invoke(phaseConfigurationService, (PhaserState<IrModuleFragment>) phaserState, (CommonBackendContext) loggingContext, (IrModuleFragment) obj);
    }

    public IrModuleFragment invoke(PhaseConfigurationService phaseConfig, PhaserState<IrModuleFragment> phaserState, Context context, IrModuleFragment input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(input, "input");
        int i = (Integer) context.getConfiguration().get(CommonConfigurationKeys.PARALLEL_BACKEND_THREADS);
        if (i == null) {
            i = 1;
        }
        int iIntValue = i.intValue();
        if (iIntValue > 1) {
            return invokeParallel(phaseConfig, phaserState, context, input, iIntValue);
        }
        return invokeSequential(phaseConfig, phaserState, context, input);
    }

    private final IrModuleFragment invokeSequential(PhaseConfigurationService phaseConfig, PhaserState<IrModuleFragment> phaserState, Context context, IrModuleFragment input) {
        for (final IrFile irFile : input.getFiles()) {
            try {
                PhaserState<IrFile> phaserStateChangePhaserStateType = CompilerPhaseKt.changePhaserStateType(phaserState);
                Iterator<CompilerPhase<Context, IrFile, IrFile>> it = this.lower.iterator();
                while (it.hasNext()) {
                    it.next().invoke(phaseConfig, phaserStateChangePhaserStateType, context, irFile);
                }
            } catch (Throwable th) {
                CodegenUtil.reportBackendException$default(th, "IR lowering", irFile.getFileEntry().getName(), null, new Function1<Integer, Pair<? extends Integer, ? extends Integer>>() { // from class: org.jetbrains.kotlin.backend.common.phaser.PerformByIrFilePhase.invokeSequential.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Pair<? extends Integer, ? extends Integer> invoke(Integer num) {
                        return invoke(num.intValue());
                    }

                    public final Pair<Integer, Integer> invoke(int i) {
                        IrFileEntry fileEntry = irFile.getFileEntry();
                        if (!fileEntry.getSupportsDebugInfo()) {
                            fileEntry = null;
                        }
                        if (fileEntry != null) {
                            return TuplesKt.to(Integer.valueOf(fileEntry.getLineNumber(i)), Integer.valueOf(fileEntry.getColumnNumber(i)));
                        }
                        return null;
                    }
                }, 8, null);
                throw null;
            }
        }
        return input;
    }

    private final IrModuleFragment invokeParallel(final PhaseConfigurationService phaseConfig, PhaserState<IrModuleFragment> phaserState, final Context context, IrModuleFragment input, int nThreads) throws InterruptedException {
        Pair pair;
        if (input.getFiles().isEmpty()) {
            return input;
        }
        final AtomicReference atomicReference = new AtomicReference(null);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        List<IrFile> files = input.getFiles();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(files, 10));
        for (IrFile irFile : files) {
            if (this.copyBeforeLowering) {
                pair = TuplesKt.to(PerformByIrFileKt.copySavingMappings(irFile, linkedHashMap, linkedHashMap2, linkedHashMap3), phaserState.copyOf());
            } else {
                pair = TuplesKt.to(irFile, phaserState.copyOf());
            }
            arrayList.add(pair);
        }
        ArrayList<Pair> arrayList2 = arrayList;
        ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(nThreads);
        for (Pair pair2 : arrayList2) {
            final IrFile irFile2 = (IrFile) pair2.component1();
            final PhaserState phaserState2 = (PhaserState) pair2.component2();
            ExecutorService executorService = executorServiceNewFixedThreadPool;
            executorService.execute(new Runnable() { // from class: org.jetbrains.kotlin.backend.common.phaser.PerformByIrFilePhase$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PerformByIrFilePhase.invokeParallel$lambda$1(phaserState2, this, phaseConfig, context, irFile2, atomicReference);
                }
            });
            executorServiceNewFixedThreadPool = executorService;
        }
        ExecutorService executorService2 = executorServiceNewFixedThreadPool;
        executorService2.shutdown();
        executorService2.awaitTermination(1L, TimeUnit.DAYS);
        Pair pair3 = (Pair) atomicReference.get();
        if (pair3 != null) {
            Throwable th = (Throwable) pair3.component1();
            final IrFile irFile3 = (IrFile) pair3.component2();
            CodegenUtil.reportBackendException$default(th, "Experimental parallel IR backend", irFile3.getFileEntry().getName(), null, new Function1<Integer, Pair<? extends Integer, ? extends Integer>>() { // from class: org.jetbrains.kotlin.backend.common.phaser.PerformByIrFilePhase$invokeParallel$2$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Pair<? extends Integer, ? extends Integer> invoke(Integer num) {
                    return invoke(num.intValue());
                }

                public final Pair<Integer, Integer> invoke(int i) {
                    IrFileEntry fileEntry = irFile3.getFileEntry();
                    if (!fileEntry.getSupportsDebugInfo()) {
                        fileEntry = null;
                    }
                    if (fileEntry != null) {
                        return TuplesKt.to(Integer.valueOf(fileEntry.getLineNumber(i)), Integer.valueOf(fileEntry.getColumnNumber(i)));
                    }
                    return null;
                }
            }, 8, null);
            throw null;
        }
        phaserState.getAlreadyDone().addAll(((PhaserState) ((Pair) arrayList2.get(0)).getSecond()).getAlreadyDone());
        if (this.copyBeforeLowering) {
            input.getFiles().clear();
            List<IrFile> files2 = input.getFiles();
            ArrayList arrayList3 = arrayList2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                arrayList4.add((IrFile) ((Pair) it.next()).component1());
            }
            files2.addAll(CollectionsKt.toMutableList((Collection) arrayList4));
            PerformByIrFileKt.adjustDefaultArgumentStubs(context, linkedHashMap2);
            context.handleDeepCopy(linkedHashMap, linkedHashMap3, linkedHashMap2);
            PerformByIrFileKt.adjustDefaultArgumentStubs(context, linkedHashMap2);
            IrVisitorsKt.acceptChildrenVoid(input, new CrossFileCallAdjuster(linkedHashMap2));
        }
        return input;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invokeParallel$lambda$1(PhaserState state, PerformByIrFilePhase this$0, PhaseConfigurationService phaseConfig, CommonBackendContext context, IrFile irFile, AtomicReference thrownFromThread) {
        Intrinsics.checkNotNullParameter(state, "$state");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(phaseConfig, "$phaseConfig");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(irFile, "$irFile");
        Intrinsics.checkNotNullParameter(thrownFromThread, "$thrownFromThread");
        try {
            PhaserState<IrFile> phaserStateChangePhaserStateType = CompilerPhaseKt.changePhaserStateType(state);
            Iterator<CompilerPhase<Context, IrFile, IrFile>> it = this$0.lower.iterator();
            while (it.hasNext()) {
                it.next().invoke(phaseConfig, phaserStateChangePhaserStateType, context, irFile);
            }
        } catch (Throwable th) {
            thrownFromThread.set(new Pair(th, irFile));
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public List<Pair<Integer, AbstractNamedCompilerPhase<Context, ?, ?>>> getNamedSubphases(int startDepth) {
        List<CompilerPhase<Context, IrFile, IrFile>> list = this.lower;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((CompilerPhase) it.next()).getNamedSubphases(startDepth));
        }
        return arrayList;
    }
}
