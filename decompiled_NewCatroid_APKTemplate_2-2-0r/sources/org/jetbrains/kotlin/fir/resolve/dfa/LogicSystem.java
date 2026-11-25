package org.jetbrains.kotlin.fir.resolve.dfa;

import com.reandroid.apk.PathMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeIntersector;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.AbstractTypeChecker;

/* JADX INFO: compiled from: LogicSystem.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u001e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0017J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00170\u001a2\u0006\u0010\u000e\u001a\u00020\u000f2\u0016\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001dJ\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u00172\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u001fJ\u0018\u0010\u001e\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010\u00172\u0006\u0010!\u001a\u00020\u0017JF\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001d2\u0016\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001d2\u0016\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001dJB\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001d2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u00020'\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110(0\u001c2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0002J.\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001d2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020*2\u0006\u0010+\u001a\u00020,J&\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001d2\u0006\u0010\u000e\u001a\u00020-2\u0006\u0010\u0018\u001a\u00020*J\u001e\u0010.\u001a\u00020,2\u0006\u0010 \u001a\u00020-2\u0006\u0010!\u001a\u00020-2\u0006\u0010/\u001a\u00020\u0014J\u001c\u00100\u001a\u00020\u000f2\f\u00101\u001a\b\u0012\u0004\u0012\u00020-0\u001f2\u0006\u00102\u001a\u00020,J\u0016\u00103\u001a\u0004\u0018\u00010\u00172\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u001fJF\u00104\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001d2\u0016\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001d2\u0016\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001cj\u0002`\u001dJ\u001e\u00105\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010/\u001a\u00020\u00142\u0006\u00106\u001a\u000207J6\u00108\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u00109\u001a\u00020'2\u0006\u0010:\u001a\u00020'2\u0016\b\u0002\u0010;\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00110<J\u001a\u0010=\u001a\u00020\r*\u00020\u000f2\f\u00101\u001a\b\u0012\u0004\u0012\u00020-0\u001fH\u0002J\"\u0010>\u001a\u00020\r*\u00020\u000f2\f\u00101\u001a\b\u0012\u0004\u0012\u00020-0\u001f2\u0006\u0010?\u001a\u00020-H\u0002J*\u0010@\u001a\u00020\r*\u00020\u000f2\f\u00101\u001a\b\u0012\u0004\u0012\u00020-0\u001f2\u0006\u0010?\u001a\u00020-2\u0006\u00102\u001a\u00020,H\u0002J\f\u0010A\u001a\u00020,*\u00020BH\u0014J\u001a\u0010C\u001a\u00020\r*\u00020\u000f2\f\u00101\u001a\b\u0012\u0004\u0012\u00020-0\u001fH\u0002J\u0015\u0010D\u001a\u00020\r*\u00020E2\u0006\u0010F\u001a\u00020\u0017H\u0082\u0002J\u001e\u0010G\u001a\u00020\r*\u00020\u000f2\u0006\u0010/\u001a\u00020\u00142\b\u0010H\u001a\u0004\u0018\u00010\u0014H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/LogicSystem;", "", "context", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "(Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;)V", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "nullableNothingType", "variableStorage", "Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorageImpl;", "getVariableStorage", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorageImpl;", "addImplication", "", "flow", "Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableFlow;", "implication", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Implication;", "addLocalVariableAlias", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "underlyingVariable", "addTypeStatement", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "statement", "addTypeStatements", "", "statements", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatements;", "and", "", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "andForTypeStatements", "left", "right", "approveOperationStatement", "logicStatements", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "approvedStatement", "Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;", "removeApprovedOrImpossible", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;", "isSameValueIn", "variable", "joinFlow", "flows", SchemaSymbols.ATTVAL_UNION, "or", "orForTypeStatements", "recordNewAssignment", "index", "", "translateVariableFromConditionInStatements", "originalVariable", "newVariable", "transform", "Lkotlin/Function1;", "copyCommonAliases", "copyNonConflictingAliases", "commonFlow", "copyStatements", "isAcceptableForSmartcast", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "mergeAssignments", "plusAssign", "Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableTypeStatement;", "other", "replaceVariable", "replacement", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LogicSystem {
    private final ConeClassLikeType anyType;
    private final ConeInferenceContext context;
    private final ConeClassLikeType nullableNothingType;

    public abstract VariableStorageImpl getVariableStorage();

    public LogicSystem(ConeInferenceContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.nullableNothingType = context.get$session().getBuiltinTypes().getNullableNothingType().getType();
        this.anyType = context.get$session().getBuiltinTypes().getAnyType().getType();
    }

    protected boolean isAcceptableForSmartcast(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return !ConeBuiltinTypeUtilsKt.isNullableNothing(coneKotlinType);
    }

    public final MutableFlow joinFlow(Collection<PersistentFlow> flows, boolean union) {
        Intrinsics.checkNotNullParameter(flows, "flows");
        int size = flows.size();
        if (size == 0) {
            return new MutableFlow();
        }
        if (size == 1) {
            return ((PersistentFlow) CollectionsKt.first(flows)).fork();
        }
        Iterator<T> it = flows.iterator();
        if (!it.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            PersistentFlow persistentFlow = (PersistentFlow) it.next();
            PersistentFlow persistentFlow2 = (PersistentFlow) next;
            PersistentFlow persistentFlowLowestCommonAncestor = persistentFlow2.lowestCommonAncestor(persistentFlow);
            if (persistentFlowLowestCommonAncestor == null) {
                throw new IllegalStateException(("no common ancestor in " + persistentFlow2 + ", " + persistentFlow).toString());
            }
            next = persistentFlowLowestCommonAncestor;
        }
        PersistentFlow persistentFlow3 = (PersistentFlow) next;
        MutableFlow mutableFlowFork = persistentFlow3.fork();
        mergeAssignments(mutableFlowFork, flows);
        if (union) {
            copyNonConflictingAliases(mutableFlowFork, flows, persistentFlow3);
        } else {
            copyCommonAliases(mutableFlowFork, flows);
        }
        copyStatements(mutableFlowFork, flows, persistentFlow3, union);
        return mutableFlowFork;
    }

    public final void addLocalVariableAlias(MutableFlow flow, RealVariable alias, RealVariable underlyingVariable) {
        PersistentSet<RealVariable> persistentSetPersistentSetOf;
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(alias, "alias");
        Intrinsics.checkNotNullParameter(underlyingVariable, "underlyingVariable");
        if (Intrinsics.areEqual(underlyingVariable, alias)) {
            return;
        }
        flow.getDirectAliasMap$semantics().put(alias, underlyingVariable);
        PersistentMap.Builder<RealVariable, PersistentSet<RealVariable>> backwardsAliasMap$semantics = flow.getBackwardsAliasMap$semantics();
        PersistentSet<RealVariable> persistentSet = flow.getBackwardsAliasMap$semantics().get(underlyingVariable);
        if (persistentSet == null || (persistentSetPersistentSetOf = persistentSet.add(alias)) == null) {
            persistentSetPersistentSetOf = ExtensionsKt.persistentSetOf(alias);
        }
        backwardsAliasMap$semantics.put(underlyingVariable, persistentSetPersistentSetOf);
    }

    public final TypeStatement addTypeStatement(MutableFlow flow, TypeStatement statement) {
        PersistentSet<ConeKotlinType> persistentSet;
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(statement, "statement");
        if (statement.getExactType().isEmpty()) {
            return null;
        }
        RealVariable variable = statement.getVariable();
        PersistentTypeStatement persistentTypeStatement = flow.getApprovedTypeStatements$semantics().get(variable);
        PersistentSet<ConeKotlinType> exactType = persistentTypeStatement != null ? persistentTypeStatement.getExactType() : null;
        if (exactType == null || (persistentSet = exactType.addAll((Collection<? extends ConeKotlinType>) statement.getExactType())) == null) {
            persistentSet = ExtensionsKt.toPersistentSet(statement.getExactType());
        }
        if (persistentSet == exactType) {
            return null;
        }
        PersistentTypeStatement persistentTypeStatement2 = new PersistentTypeStatement(variable, persistentSet);
        flow.getApprovedTypeStatements$semantics().put(variable, persistentTypeStatement2);
        return persistentTypeStatement2;
    }

    public final List<TypeStatement> addTypeStatements(MutableFlow flow, Map<RealVariable, ? extends TypeStatement> statements) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(statements, "statements");
        Collection<? extends TypeStatement> collectionValues = statements.values();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            TypeStatement typeStatementAddTypeStatement = addTypeStatement(flow, (TypeStatement) it.next());
            if (typeStatementAddTypeStatement != null) {
                arrayList.add(typeStatementAddTypeStatement);
            }
        }
        return arrayList;
    }

    public final void addImplication(MutableFlow flow, Implication implication) {
        PersistentList<Implication> persistentListPersistentListOf;
        PersistentSet<ConeKotlinType> exactType;
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(implication, "implication");
        Statement effect = implication.getEffect();
        if (Intrinsics.areEqual(effect, implication.getCondition())) {
            return;
        }
        if (effect instanceof TypeStatement) {
            TypeStatement typeStatement = (TypeStatement) effect;
            if (typeStatement.isEmpty()) {
                return;
            }
            PersistentTypeStatement persistentTypeStatement = flow.getApprovedTypeStatements$semantics().get(typeStatement.getVariable());
            if (persistentTypeStatement != null && (exactType = persistentTypeStatement.getExactType()) != null && exactType.containsAll(typeStatement.getExactType())) {
                return;
            }
        }
        DataFlowVariable variable = implication.getCondition().getVariable();
        PersistentMap.Builder<DataFlowVariable, PersistentList<Implication>> logicStatements$semantics = flow.getLogicStatements$semantics();
        PersistentList<Implication> persistentList = flow.getLogicStatements$semantics().get(variable);
        if (persistentList == null || (persistentListPersistentListOf = persistentList.add(implication)) == null) {
            persistentListPersistentListOf = ExtensionsKt.persistentListOf(implication);
        }
        logicStatements$semantics.put(variable, persistentListPersistentListOf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void translateVariableFromConditionInStatements$default(LogicSystem logicSystem, MutableFlow mutableFlow, DataFlowVariable dataFlowVariable, DataFlowVariable dataFlowVariable2, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: translateVariableFromConditionInStatements");
        }
        if ((i & 8) != 0) {
            function1 = new Function1<Implication, Implication>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.LogicSystem.translateVariableFromConditionInStatements.1
                @Override // kotlin.jvm.functions.Function1
                public final Implication invoke(Implication it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it;
                }
            };
        }
        logicSystem.translateVariableFromConditionInStatements(mutableFlow, dataFlowVariable, dataFlowVariable2, function1);
    }

    public final void translateVariableFromConditionInStatements(MutableFlow flow, DataFlowVariable originalVariable, DataFlowVariable newVariable, Function1<? super Implication, Implication> transform) {
        PersistentList<Implication> persistentListRemove;
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(originalVariable, "originalVariable");
        Intrinsics.checkNotNullParameter(newVariable, "newVariable");
        Intrinsics.checkNotNullParameter(transform, "transform");
        if (ModelKt.isSynthetic(originalVariable)) {
            persistentListRemove = flow.getLogicStatements$semantics().remove(originalVariable);
        } else {
            persistentListRemove = flow.getLogicStatements$semantics().get(originalVariable);
        }
        PersistentList<Implication> persistentList = persistentListRemove;
        if (persistentList == null || persistentList.isEmpty()) {
            return;
        }
        PersistentList<Implication> persistentListPersistentListOf = flow.getLogicStatements$semantics().get(newVariable);
        if (persistentListPersistentListOf == null) {
            persistentListPersistentListOf = ExtensionsKt.persistentListOf();
        }
        PersistentMap.Builder<DataFlowVariable, PersistentList<Implication>> logicStatements$semantics = flow.getLogicStatements$semantics();
        PersistentList.Builder<Implication> builder = persistentListPersistentListOf.builder();
        for (Implication implication : persistentListRemove) {
            Implication implicationInvoke = transform.invoke(ModelKt.implies(new OperationStatement(newVariable, implication.getCondition().getOperation()), implication.getEffect()));
            if (implicationInvoke != null) {
                builder.add(implicationInvoke);
            }
        }
        logicStatements$semantics.put(newVariable, builder.build());
    }

    public final Map<RealVariable, TypeStatement> approveOperationStatement(PersistentFlow flow, OperationStatement statement) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(statement, "statement");
        return approveOperationStatement((Map<DataFlowVariable, ? extends PersistentList<Implication>>) flow.getLogicStatements$semantics(), statement, false);
    }

    public final Map<RealVariable, TypeStatement> approveOperationStatement(MutableFlow flow, OperationStatement statement, boolean removeApprovedOrImpossible) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(statement, "statement");
        return approveOperationStatement(flow.getLogicStatements$semantics(), statement, removeApprovedOrImpossible);
    }

    public final void recordNewAssignment(MutableFlow flow, RealVariable variable, int index) {
        Intrinsics.checkNotNullParameter(flow, "flow");
        Intrinsics.checkNotNullParameter(variable, "variable");
        replaceVariable(flow, variable, null);
        flow.getAssignmentIndex$semantics().put(variable, Integer.valueOf(index));
    }

    public final boolean isSameValueIn(PersistentFlow a, PersistentFlow b, RealVariable variable) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(variable, "variable");
        return Intrinsics.areEqual(a.getAssignmentIndex$semantics().get(variable), b.getAssignmentIndex$semantics().get(variable));
    }

    private final void mergeAssignments(MutableFlow mutableFlow, Collection<PersistentFlow> collection) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<PersistentFlow> it = collection.iterator();
        while (it.hasNext()) {
            for (Map.Entry<RealVariable, Integer> entry : it.next().getAssignmentIndex$semantics().entrySet()) {
                RealVariable key = entry.getKey();
                int iIntValue = entry.getValue().intValue();
                Integer num = mutableFlow.getAssignmentIndex$semantics().get(key);
                if (num == null || num.intValue() != iIntValue) {
                    Integer num2 = (Integer) linkedHashMap.get(key);
                    linkedHashMap.put(key, Integer.valueOf(Math.max(iIntValue, num2 != null ? num2.intValue() : 0)));
                }
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            recordNewAssignment(mutableFlow, (RealVariable) entry2.getKey(), ((Number) entry2.getValue()).intValue());
        }
    }

    private final void copyCommonAliases(MutableFlow mutableFlow, Collection<PersistentFlow> collection) {
        Collection<PersistentFlow> collection2 = collection;
        for (Map.Entry<RealVariable, RealVariable> entry : ((PersistentFlow) CollectionsKt.first(collection2)).getDirectAliasMap$semantics().entrySet()) {
            RealVariable key = entry.getKey();
            RealVariable value = entry.getValue();
            if (!Intrinsics.areEqual(mutableFlow.getDirectAliasMap$semantics().get(key), value)) {
                if (!(collection2 instanceof Collection) || !collection2.isEmpty()) {
                    Iterator<T> it = collection2.iterator();
                    do {
                        if (it.hasNext()) {
                        }
                    } while (Intrinsics.areEqual(((PersistentFlow) it.next()).unwrapVariable(key), value));
                }
                addLocalVariableAlias(mutableFlow, key, value);
                break;
            }
        }
    }

    private final void copyNonConflictingAliases(MutableFlow mutableFlow, Collection<PersistentFlow> collection, PersistentFlow persistentFlow) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (PersistentFlow persistentFlow2 : collection) {
            for (Map.Entry<RealVariable, RealVariable> entry : persistentFlow2.getDirectAliasMap$semantics().entrySet()) {
                RealVariable key = entry.getKey();
                RealVariable value = entry.getValue();
                if (!Intrinsics.areEqual(persistentFlow.getAssignmentIndex$semantics().get(key), persistentFlow2.getAssignmentIndex$semantics().get(key))) {
                    if (linkedHashMap.containsKey(key) && !Intrinsics.areEqual(linkedHashMap.get(key), value)) {
                        value = null;
                    }
                    linkedHashMap.put(key, value);
                }
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            RealVariable realVariable = (RealVariable) entry2.getKey();
            RealVariable realVariable2 = (RealVariable) entry2.getValue();
            if (realVariable2 != null) {
                addLocalVariableAlias(mutableFlow, realVariable, realVariable2);
            }
        }
    }

    private final void copyStatements(MutableFlow mutableFlow, Collection<PersistentFlow> collection, PersistentFlow persistentFlow, boolean z) {
        TypeStatement typeStatementOr;
        Collection<PersistentFlow> collection2 = collection;
        LinkedHashSet<RealVariable> linkedHashSet = new LinkedHashSet();
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(linkedHashSet, ((PersistentFlow) it.next()).getKnownVariables());
        }
        for (RealVariable realVariable : linkedHashSet) {
            if (!mutableFlow.getDirectAliasMap$semantics().containsKey(realVariable)) {
                if (!z) {
                    LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                    Iterator<T> it2 = collection2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            TypeStatement typeStatement = ((PersistentFlow) it2.next()).getTypeStatement(realVariable);
                            if (typeStatement != null) {
                                linkedHashSet2.add(typeStatement);
                            }
                        } else {
                            typeStatementOr = or(linkedHashSet2);
                            if (typeStatementOr == null && typeStatementOr.isNotEmpty()) {
                                mutableFlow.getApprovedTypeStatements$semantics().put(realVariable, LogicSystemKt.toPersistent(typeStatementOr));
                            }
                        }
                    }
                } else {
                    if (Intrinsics.areEqual(mutableFlow.getAssignmentIndex$semantics().get(realVariable), persistentFlow.getAssignmentIndex$semantics().get(realVariable))) {
                        LinkedHashSet linkedHashSet3 = new LinkedHashSet();
                        Iterator<T> it3 = collection2.iterator();
                        while (it3.hasNext()) {
                            TypeStatement typeStatement2 = ((PersistentFlow) it3.next()).getTypeStatement(realVariable);
                            if (typeStatement2 != null) {
                                linkedHashSet3.add(typeStatement2);
                            }
                        }
                        typeStatementOr = and(linkedHashSet3);
                    } else {
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        Iterator<T> it4 = collection2.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                break;
                            }
                            PersistentFlow persistentFlow2 = (PersistentFlow) it4.next();
                            Integer num = (Integer) persistentFlow2.getAssignmentIndex$semantics().get(realVariable);
                            Integer numValueOf = Integer.valueOf(num != null ? num.intValue() : -1);
                            Object obj = linkedHashMap.get(numValueOf);
                            if (obj == null) {
                                obj = (List) new ArrayList();
                                linkedHashMap.put(numValueOf, obj);
                            }
                            ((List) obj).add(persistentFlow2.getTypeStatement(realVariable));
                        }
                        Integer num2 = (Integer) persistentFlow.getAssignmentIndex$semantics().get(realVariable);
                        linkedHashMap.remove(Integer.valueOf(num2 != null ? num2.intValue() : -1));
                        Collection collectionValues = linkedHashMap.values();
                        LinkedHashSet linkedHashSet4 = new LinkedHashSet();
                        Iterator it5 = collectionValues.iterator();
                        while (true) {
                            if (it5.hasNext()) {
                                TypeStatement typeStatementAnd = and(CollectionsKt.filterNotNull((List) it5.next()));
                                if (typeStatementAnd != null) {
                                    linkedHashSet4.add(typeStatementAnd);
                                }
                            } else {
                                typeStatementOr = or(linkedHashSet4);
                            }
                        }
                    }
                    if (typeStatementOr == null) {
                    }
                }
            }
        }
    }

    private final void replaceVariable(MutableFlow mutableFlow, RealVariable realVariable, RealVariable realVariable2) {
        PersistentSet<RealVariable> persistentSetAddAll;
        RealVariable realVariableRemove = mutableFlow.getDirectAliasMap$semantics().remove(realVariable);
        if (realVariableRemove != null) {
            if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
                mutableFlow.getBackwardsAliasMap$semantics().containsKey(realVariable);
                mutableFlow.getLogicStatements$semantics().containsKey(realVariable);
                mutableFlow.getApprovedTypeStatements$semantics().containsKey(realVariable);
            }
            PersistentSet persistentSet = (PersistentSet) MapsKt.getValue(mutableFlow.getBackwardsAliasMap$semantics(), realVariableRemove);
            if (persistentSet.size() > 1) {
                mutableFlow.getBackwardsAliasMap$semantics().put(realVariableRemove, persistentSet.remove(realVariable));
            } else {
                mutableFlow.getBackwardsAliasMap$semantics().remove(realVariableRemove);
            }
            if (realVariable2 != null) {
                addLocalVariableAlias(mutableFlow, realVariable2, realVariableRemove);
                return;
            }
            return;
        }
        PersistentSet<RealVariable> persistentSetRemove = mutableFlow.getBackwardsAliasMap$semantics().remove(realVariable);
        if (realVariable2 == null) {
            realVariable2 = persistentSetRemove != null ? (RealVariable) CollectionsKt.first(persistentSetRemove) : null;
        }
        for (RealVariable realVariable3 : realVariable.getDependentVariables()) {
            replaceVariable(mutableFlow, realVariable3, realVariable2 != null ? getVariableStorage().copyRealVariableWithRemapping(realVariable3, realVariable, realVariable2) : null);
        }
        LogicSystemKt.replaceVariableInImplications(mutableFlow.getLogicStatements$semantics(), realVariable, realVariable2);
        LogicSystemKt.replaceVariableInStatements(mutableFlow.getApprovedTypeStatements$semantics(), realVariable, realVariable2);
        if (persistentSetRemove == null || realVariable2 == null) {
            return;
        }
        mutableFlow.getDirectAliasMap$semantics().remove(realVariable2);
        PersistentSet<RealVariable> persistentSetRemove2 = persistentSetRemove.remove(realVariable2);
        PersistentSet<RealVariable> persistentSet2 = persistentSetRemove2;
        if (persistentSet2.isEmpty()) {
            return;
        }
        PersistentMap.Builder<RealVariable, RealVariable> directAliasMap$semantics = mutableFlow.getDirectAliasMap$semantics();
        Iterator<RealVariable> it = persistentSetRemove2.iterator();
        while (it.hasNext()) {
            directAliasMap$semantics.put(it.next(), realVariable2);
        }
        PersistentMap.Builder<RealVariable, PersistentSet<RealVariable>> backwardsAliasMap$semantics = mutableFlow.getBackwardsAliasMap$semantics();
        PersistentSet<RealVariable> persistentSet3 = mutableFlow.getBackwardsAliasMap$semantics().get(realVariable2);
        if (persistentSet3 != null && (persistentSetAddAll = persistentSet3.addAll((Collection<? extends RealVariable>) persistentSet2)) != null) {
            persistentSetRemove2 = persistentSetAddAll;
        }
        backwardsAliasMap$semantics.put(realVariable2, persistentSetRemove2);
    }

    private final Map<RealVariable, TypeStatement> approveOperationStatement(Map<DataFlowVariable, ? extends PersistentList<Implication>> logicStatements, OperationStatement approvedStatement, final boolean removeApprovedOrImpossible) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = linkedList;
        linkedList2.add(approvedStatement);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        while (!linkedList2.isEmpty()) {
            OperationStatement operationStatement = (OperationStatement) linkedList.removeFirst();
            if (!removeApprovedOrImpossible) {
                Intrinsics.checkNotNull(operationStatement);
                if (linkedHashSet.add(operationStatement)) {
                }
            }
            final Operation operation = operationStatement.getOperation();
            DataFlowVariable variable = operationStatement.getVariable();
            if (ModelKt.isReal(variable)) {
                ConeClassLikeType coneClassLikeType = operation == Operation.EqNull ? this.nullableNothingType : this.anyType;
                Object mutableTypeStatement = linkedHashMap.get(variable);
                if (mutableTypeStatement == null) {
                    mutableTypeStatement = new MutableTypeStatement((RealVariable) variable, null, 2, null);
                    linkedHashMap.put(variable, mutableTypeStatement);
                }
                ((MutableTypeStatement) mutableTypeStatement).getExactType().add(coneClassLikeType);
            }
            PersistentList<Implication> persistentList = logicStatements.get(variable);
            if (persistentList != null) {
                PersistentList<Implication> persistentListRemoveAll = persistentList.removeAll(new Function1<Implication, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.LogicSystem$approveOperationStatement$stillUnknown$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(Implication it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        Boolean boolValueIfKnown = it.getCondition().getOperation().valueIfKnown(operation);
                        if (Intrinsics.areEqual((Object) boolValueIfKnown, (Object) true)) {
                            Statement effect = it.getEffect();
                            if (effect instanceof OperationStatement) {
                                linkedList.add(effect);
                            } else if (effect instanceof TypeStatement) {
                                LogicSystem logicSystem = this;
                                Map<RealVariable, MutableTypeStatement> map = linkedHashMap;
                                TypeStatement typeStatement = (TypeStatement) effect;
                                RealVariable variable2 = typeStatement.getVariable();
                                MutableTypeStatement mutableTypeStatement2 = map.get(variable2);
                                if (mutableTypeStatement2 == null) {
                                    mutableTypeStatement2 = new MutableTypeStatement(typeStatement.getVariable(), null, 2, null);
                                    map.put(variable2, mutableTypeStatement2);
                                }
                                logicSystem.plusAssign(mutableTypeStatement2, typeStatement);
                            }
                        }
                        return Boolean.valueOf(removeApprovedOrImpossible && boolValueIfKnown != null);
                    }
                });
                if (!Intrinsics.areEqual(persistentListRemoveAll, persistentList) && TypeIntrinsics.isMutableMap(logicStatements)) {
                    if (persistentListRemoveAll.isEmpty()) {
                        logicStatements.remove(variable);
                    } else {
                        logicStatements.put(variable, persistentListRemoveAll);
                    }
                }
            }
        }
        return linkedHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Map<RealVariable, TypeStatement> orForTypeStatements(Map<RealVariable, ? extends TypeStatement> left, Map<RealVariable, ? extends TypeStatement> right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        if (left.isEmpty()) {
            return left;
        }
        if (right.isEmpty()) {
            return right;
        }
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (Map.Entry entry : left.entrySet()) {
            RealVariable realVariable = (RealVariable) entry.getKey();
            TypeStatement[] typeStatementArr = new TypeStatement[2];
            typeStatementArr[0] = (TypeStatement) entry.getValue();
            TypeStatement typeStatement = (TypeStatement) right.get(realVariable);
            if (typeStatement != null) {
                typeStatementArr[1] = typeStatement;
                TypeStatement typeStatementOr = or(CollectionsKt.listOf((Object[]) typeStatementArr));
                if (typeStatementOr != null) {
                    mapCreateMapBuilder.put(realVariable, typeStatementOr);
                }
            }
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Map<RealVariable, TypeStatement> andForTypeStatements(Map<RealVariable, ? extends TypeStatement> left, Map<RealVariable, ? extends TypeStatement> right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        if (left.isEmpty()) {
            return right;
        }
        if (right.isEmpty()) {
            return left;
        }
        Map<RealVariable, TypeStatement> mutableMap = MapsKt.toMutableMap(left);
        for (Map.Entry entry : right.entrySet()) {
            RealVariable realVariable = (RealVariable) entry.getKey();
            mutableMap.put(realVariable, and(mutableMap.get(realVariable), (TypeStatement) entry.getValue()));
        }
        return mutableMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void plusAssign(MutableTypeStatement mutableTypeStatement, TypeStatement typeStatement) {
        Intrinsics.checkNotNullParameter(mutableTypeStatement, "<this>");
        CollectionsKt.addAll(mutableTypeStatement.getExactType(), typeStatement.getExactType());
    }

    public final TypeStatement and(TypeStatement a, TypeStatement b) {
        MutableTypeStatement mutable;
        Intrinsics.checkNotNullParameter(b, "b");
        if (a == null || (mutable = LogicSystemKt.toMutable(a)) == null) {
            return b;
        }
        plusAssign(mutable, b);
        return mutable;
    }

    public final TypeStatement and(Collection<? extends TypeStatement> statements) {
        Intrinsics.checkNotNullParameter(statements, "statements");
        int size = statements.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return (TypeStatement) CollectionsKt.first(statements);
        }
        Iterator<? extends TypeStatement> it = statements.iterator();
        MutableTypeStatement mutable = LogicSystemKt.toMutable(it.next());
        while (it.hasNext()) {
            plusAssign(mutable, it.next());
        }
        return mutable;
    }

    public final TypeStatement or(Collection<? extends TypeStatement> statements) {
        Intrinsics.checkNotNullParameter(statements, "statements");
        int size = statements.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return (TypeStatement) CollectionsKt.first(statements);
        }
        Collection<? extends TypeStatement> collection = statements;
        RealVariable variable = ((TypeStatement) CollectionsKt.first(collection)).getVariable();
        Collection<? extends TypeStatement> collection2 = collection;
        if (!collection2.isEmpty()) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext() && Intrinsics.areEqual(((TypeStatement) it.next()).getVariable(), variable)) {
            }
        }
        if (!collection2.isEmpty()) {
            Iterator<T> it2 = collection.iterator();
            while (it2.hasNext()) {
                if (((TypeStatement) it2.next()).isEmpty()) {
                    return null;
                }
            }
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it3 = collection.iterator();
        while (it3.hasNext()) {
            arrayList.add(ConeTypeIntersector.INSTANCE.intersectTypes(this.context, CollectionsKt.toList(((TypeStatement) it3.next()).getExactType())));
        }
        ConeClassLikeType coneClassLikeTypeCommonSuperTypeOrNull = TypeUtilsKt.commonSuperTypeOrNull(this.context, arrayList);
        if (coneClassLikeTypeCommonSuperTypeOrNull == null || ConeBuiltinTypeUtilsKt.isNullableAny(coneClassLikeTypeCommonSuperTypeOrNull)) {
            return null;
        }
        if (!isAcceptableForSmartcast(coneClassLikeTypeCommonSuperTypeOrNull)) {
            if (FirTypeUtilsKt.getCanBeNull(coneClassLikeTypeCommonSuperTypeOrNull)) {
                return null;
            }
            coneClassLikeTypeCommonSuperTypeOrNull = this.context.anyType();
        }
        return new PersistentTypeStatement(variable, ExtensionsKt.persistentSetOf(coneClassLikeTypeCommonSuperTypeOrNull));
    }
}
