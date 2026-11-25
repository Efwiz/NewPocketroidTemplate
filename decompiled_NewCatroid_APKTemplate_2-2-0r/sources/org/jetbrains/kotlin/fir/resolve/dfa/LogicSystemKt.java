package org.jetbrains.kotlin.fir.resolve.dfa;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;

/* JADX INFO: compiled from: LogicSystem.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00020\u0004H\u0082\b\u001a5\u0010\u0005\u001a\u00020\u0006*\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00010\u00072\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0003¢\u0006\u0002\b\r\u001a/\u0010\u0005\u001a\u00020\u0006*\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\u00072\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0003¢\u0006\u0002\b\u000f\u001a\u001c\u0010\u0005\u001a\u00020\t*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u001a\u001c\u0010\u0005\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u001a\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\u0002\u001a\f\u0010\u0014\u001a\u00020\u000e*\u00020\u0013H\u0002¨\u0006\u0015"}, d2 = {"replaceAll", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function1;", "replaceVariable", "", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Implication;", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "to", "replaceVariableInImplications", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentTypeStatement;", "replaceVariableInStatements", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Statement;", "toMutable", "Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableTypeStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "toPersistent", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LogicSystemKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final PersistentTypeStatement toPersistent(TypeStatement typeStatement) {
        return typeStatement instanceof PersistentTypeStatement ? (PersistentTypeStatement) typeStatement : new PersistentTypeStatement(typeStatement.getVariable(), ExtensionsKt.toPersistentSet(typeStatement.getExactType()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MutableTypeStatement toMutable(TypeStatement typeStatement) {
        return typeStatement instanceof PersistentTypeStatement ? new MutableTypeStatement(typeStatement.getVariable(), ((PersistentTypeStatement) typeStatement).getExactType().builder()) : new MutableTypeStatement(typeStatement.getVariable(), new LinkedHashSet(typeStatement.getExactType()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void replaceVariableInStatements(Map<RealVariable, PersistentTypeStatement> map, RealVariable realVariable, RealVariable realVariable2) {
        PersistentTypeStatement persistentTypeStatementRemove = map.remove(realVariable);
        if (persistentTypeStatementRemove == null || realVariable2 == null) {
            return;
        }
        map.put(realVariable2, PersistentTypeStatement.copy$default(persistentTypeStatementRemove, realVariable2, null, 2, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void replaceVariableInImplications(Map<DataFlowVariable, PersistentList<Implication>> map, final RealVariable realVariable, RealVariable realVariable2) {
        PersistentList persistentListRemoveAll;
        PersistentList<Implication> persistentListRemove = map.remove(realVariable);
        Set<Map.Entry<DataFlowVariable, PersistentList<Implication>>> setEntrySet = map.entrySet();
        ArrayList<Pair> arrayList = new ArrayList();
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            DataFlowVariable dataFlowVariable = (DataFlowVariable) entry.getKey();
            PersistentList persistentList = (PersistentList) entry.getValue();
            if (realVariable2 == null) {
                persistentListRemoveAll = persistentList.removeAll(new Function1<Implication, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.LogicSystemKt$replaceVariable$toReplace$1$newImplications$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(Implication it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it2.getEffect().getVariable(), realVariable));
                    }
                });
            } else {
                PersistentList.Builder builder = persistentList.builder();
                ListIterator<E> listIterator = builder.listIterator();
                while (listIterator.hasNext()) {
                    listIterator.set(replaceVariable((Implication) listIterator.next(), realVariable, realVariable2));
                }
                persistentListRemoveAll = builder.build();
            }
            Pair pair = !Intrinsics.areEqual(persistentListRemoveAll, persistentList) ? TuplesKt.to(dataFlowVariable, persistentListRemoveAll) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        for (Pair pair2 : arrayList) {
            DataFlowVariable dataFlowVariable2 = (DataFlowVariable) pair2.component1();
            PersistentList<Implication> persistentList2 = (PersistentList) pair2.component2();
            if (persistentList2.isEmpty()) {
                map.remove(dataFlowVariable2);
            } else {
                map.put(dataFlowVariable2, persistentList2);
            }
        }
        if (persistentListRemove == null || realVariable2 == null) {
            return;
        }
        PersistentList.Builder<Implication> builder2 = persistentListRemove.builder();
        ListIterator<Implication> listIterator2 = builder2.listIterator();
        while (listIterator2.hasNext()) {
            listIterator2.set(replaceVariable(listIterator2.next(), realVariable, realVariable2));
        }
        map.put(realVariable2, builder2.build());
    }

    private static final Implication replaceVariable(Implication implication, RealVariable realVariable, RealVariable realVariable2) {
        Implication implication2;
        if (Intrinsics.areEqual(implication.getCondition().getVariable(), realVariable)) {
            implication2 = new Implication(OperationStatement.copy$default(implication.getCondition(), realVariable2, null, 2, null), replaceVariable(implication.getEffect(), realVariable, realVariable2));
        } else {
            if (!Intrinsics.areEqual(implication.getEffect().getVariable(), realVariable)) {
                return implication;
            }
            implication2 = new Implication(implication.getCondition(), replaceVariable(implication.getEffect(), realVariable, realVariable2));
        }
        return implication2;
    }

    private static final Statement replaceVariable(Statement statement, RealVariable realVariable, RealVariable realVariable2) {
        if (!Intrinsics.areEqual(statement.getVariable(), realVariable)) {
            return statement;
        }
        if (statement instanceof OperationStatement) {
            return OperationStatement.copy$default((OperationStatement) statement, realVariable2, null, 2, null);
        }
        if (statement instanceof PersistentTypeStatement) {
            return PersistentTypeStatement.copy$default((PersistentTypeStatement) statement, realVariable2, null, 2, null);
        }
        if (statement instanceof MutableTypeStatement) {
            return new MutableTypeStatement(realVariable2, ((MutableTypeStatement) statement).getExactType());
        }
        throw new NoWhenBranchMatchedException();
    }
}
