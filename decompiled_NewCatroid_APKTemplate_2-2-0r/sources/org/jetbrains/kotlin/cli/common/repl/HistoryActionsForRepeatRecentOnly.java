package org.jetbrains.kotlin.cli.common.repl;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.sequences.Sequence;

/* JADX INFO: compiled from: GenericReplEvaluator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0012\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0007H\u0016J\u0018\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0007H\u0016J.\u0010\u0014\u001a\u001a\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017H\u0016J$\u0010\u0018\u001a\u0016\u0012\u0004\u0012\u00020\u0019\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u001a0\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/HistoryActionsForRepeatRecentOnly;", "Lorg/jetbrains/kotlin/cli/common/repl/HistoryActionsForNoRepeat;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluatorState;", "(Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluatorState;)V", "currentLast", "Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", "Lorg/jetbrains/kotlin/cli/common/repl/EvalClassWithInstanceAndLoader;", "getCurrentLast", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", "effectiveHistory", "", "getEffectiveHistory", "()Ljava/util/List;", "addFinal", "", "lineId", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "value", "addPlaceholder", "firstMismatch", "Lkotlin/Pair;", "other", "Lkotlin/sequences/Sequence;", "processClasses", "Ljava/lang/ClassLoader;", "Ljava/lang/Class;", "", "compileResult", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "removePlaceholder", "", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
class HistoryActionsForRepeatRecentOnly extends HistoryActionsForNoRepeat {
    private final ReplHistoryRecord<EvalClassWithInstanceAndLoader> currentLast;

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public void addPlaceholder(ILineId lineId, EvalClassWithInstanceAndLoader value) {
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        Intrinsics.checkNotNullParameter(value, "value");
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public boolean removePlaceholder(ILineId lineId) {
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HistoryActionsForRepeatRecentOnly(GenericReplEvaluatorState state) {
        super(state);
        Intrinsics.checkNotNullParameter(state, "state");
        ReplHistoryRecord<EvalClassWithInstanceAndLoader> replHistoryRecordPeek = state.getHistory().peek();
        Intrinsics.checkNotNull(replHistoryRecordPeek);
        this.currentLast = replHistoryRecordPeek;
    }

    public final ReplHistoryRecord<EvalClassWithInstanceAndLoader> getCurrentLast() {
        return this.currentLast;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public List<EvalClassWithInstanceAndLoader> getEffectiveHistory() {
        return CollectionsKt.dropLast(super.getEffectiveHistory(), 1);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public Pair<ReplHistoryRecord<EvalClassWithInstanceAndLoader>, ILineId> firstMismatch(Sequence<? extends ILineId> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return ReplStateKt.firstMismatchFiltered(getState().getHistory(), other, new Function1<ReplHistoryRecord<? extends EvalClassWithInstanceAndLoader>, Boolean>() { // from class: org.jetbrains.kotlin.cli.common.repl.HistoryActionsForRepeatRecentOnly.firstMismatch.1
            {
                super(1);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(ReplHistoryRecord<EvalClassWithInstanceAndLoader> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it.getId(), HistoryActionsForRepeatRecentOnly.this.getCurrentLast().getId()));
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(ReplHistoryRecord<? extends EvalClassWithInstanceAndLoader> replHistoryRecord) {
                return invoke2((ReplHistoryRecord<EvalClassWithInstanceAndLoader>) replHistoryRecord);
            }
        });
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public void addFinal(ILineId lineId, EvalClassWithInstanceAndLoader value) {
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        Intrinsics.checkNotNullParameter(value, "value");
        getState().getHistory().pop();
        getState().getHistory().push(lineId, value);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public Pair<ClassLoader, Class<? extends Object>> processClasses(ReplCompileResult.CompiledClasses compileResult) {
        Intrinsics.checkNotNullParameter(compileResult, "compileResult");
        return TuplesKt.to(this.currentLast.getItem().getClassLoader(), JvmClassMappingKt.getJavaClass((KClass) this.currentLast.getItem().getKlass()));
    }
}
