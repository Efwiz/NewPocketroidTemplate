package org.jetbrains.kotlin.cli.common.repl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0012\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0006H\u0016J\u0018\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0006H\u0016J.\u0010\u0014\u001a\u001a\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017H\u0016J$\u0010\u0018\u001a\u0016\u0012\u0004\u0012\u00020\u0019\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u001a0\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/HistoryActionsForRepeatAny;", "Lorg/jetbrains/kotlin/cli/common/repl/HistoryActionsForNoRepeat;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluatorState;", "matchingLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", "Lorg/jetbrains/kotlin/cli/common/repl/EvalClassWithInstanceAndLoader;", "(Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluatorState;Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;)V", "effectiveHistory", "", "getEffectiveHistory", "()Ljava/util/List;", "getMatchingLine", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", "addFinal", "", "lineId", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "value", "addPlaceholder", "firstMismatch", "Lkotlin/Pair;", "other", "Lkotlin/sequences/Sequence;", "processClasses", "Ljava/lang/ClassLoader;", "Ljava/lang/Class;", "", "compileResult", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "removePlaceholder", "", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
class HistoryActionsForRepeatAny extends HistoryActionsForNoRepeat {
    private final ReplHistoryRecord<EvalClassWithInstanceAndLoader> matchingLine;

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
    public HistoryActionsForRepeatAny(GenericReplEvaluatorState state, ReplHistoryRecord<EvalClassWithInstanceAndLoader> matchingLine) {
        super(state);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(matchingLine, "matchingLine");
        this.matchingLine = matchingLine;
    }

    public final ReplHistoryRecord<EvalClassWithInstanceAndLoader> getMatchingLine() {
        return this.matchingLine;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public List<EvalClassWithInstanceAndLoader> getEffectiveHistory() {
        IReplStageHistory<EvalClassWithInstanceAndLoader> history = getState().getHistory();
        ArrayList arrayList = new ArrayList();
        for (Object obj : history) {
            if (Intrinsics.areEqual(((ReplHistoryRecord) obj).getId(), this.matchingLine.getId())) {
                break;
            }
            arrayList.add(obj);
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add((EvalClassWithInstanceAndLoader) ((ReplHistoryRecord) it.next()).getItem());
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public Pair<ReplHistoryRecord<EvalClassWithInstanceAndLoader>, ILineId> firstMismatch(Sequence<? extends ILineId> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return ReplStateKt.firstMismatchWhile(getState().getHistory(), other, new Function1<ReplHistoryRecord<? extends EvalClassWithInstanceAndLoader>, Boolean>() { // from class: org.jetbrains.kotlin.cli.common.repl.HistoryActionsForRepeatAny.firstMismatch.1
            {
                super(1);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(ReplHistoryRecord<EvalClassWithInstanceAndLoader> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it.getId(), HistoryActionsForRepeatAny.this.getMatchingLine().getId()));
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(ReplHistoryRecord<? extends EvalClassWithInstanceAndLoader> replHistoryRecord) {
                return invoke2((ReplHistoryRecord<EvalClassWithInstanceAndLoader>) replHistoryRecord);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public void addFinal(ILineId lineId, EvalClassWithInstanceAndLoader value) {
        List<ReplHistoryRecord> list;
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        Intrinsics.checkNotNullParameter(value, "value");
        IReplStageHistory<EvalClassWithInstanceAndLoader> history = getState().getHistory();
        if (history.isEmpty()) {
            list = CollectionsKt.emptyList();
        } else {
            ListIterator<ReplHistoryRecord<? extends T>> listIterator = history.listIterator(history.size());
            while (true) {
                if (listIterator.hasPrevious()) {
                    if (!Intrinsics.areEqual(((ReplHistoryRecord) listIterator.previous()).getId(), this.matchingLine.getId())) {
                        listIterator.next();
                        int size = history.size() - listIterator.nextIndex();
                        if (size == 0) {
                            list = CollectionsKt.emptyList();
                            break;
                        }
                        ArrayList arrayList = new ArrayList(size);
                        while (listIterator.hasNext()) {
                            arrayList.add(listIterator.next());
                        }
                        list = arrayList;
                        break;
                    }
                } else {
                    list = CollectionsKt.toList(history);
                    break;
                }
            }
        }
        getState().getHistory().resetTo(lineId);
        getState().getHistory().pop();
        getState().getHistory().push(lineId, value);
        for (ReplHistoryRecord replHistoryRecord : list) {
            getState().getHistory().push(replHistoryRecord.getId(), replHistoryRecord.getItem());
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.HistoryActionsForNoRepeat
    public Pair<ClassLoader, Class<? extends Object>> processClasses(ReplCompileResult.CompiledClasses compileResult) {
        Intrinsics.checkNotNullParameter(compileResult, "compileResult");
        return TuplesKt.to(this.matchingLine.getItem().getClassLoader(), JvmClassMappingKt.getJavaClass((KClass) this.matchingLine.getItem().getKlass()));
    }
}
