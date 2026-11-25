package org.jetbrains.kotlin.types.expressions;

import gnu.trove.THashSet;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import javaslang.Tuple2;
import javaslang.collection.Iterator;
import javaslang.collection.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLoopExpression;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.Nullability;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: PreliminaryLoopVisitor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PreliminaryLoopVisitor;", "Lorg/jetbrains/kotlin/types/expressions/AssignedVariablesSearcher;", "()V", "clearDataFlowInfoForAssignedLocalVariables", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "dataFlowInfo", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PreliminaryLoopVisitor extends AssignedVariablesSearcher {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ PreliminaryLoopVisitor(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final PreliminaryLoopVisitor visitCatchBlocks(KtTryExpression ktTryExpression) {
        return INSTANCE.visitCatchBlocks(ktTryExpression);
    }

    @JvmStatic
    public static final PreliminaryLoopVisitor visitCatchBlocks(KtTryExpression ktTryExpression, List<Boolean> list) {
        return INSTANCE.visitCatchBlocks(ktTryExpression, list);
    }

    @JvmStatic
    public static final PreliminaryLoopVisitor visitLoop(KtLoopExpression ktLoopExpression) {
        return INSTANCE.visitLoop(ktLoopExpression);
    }

    @JvmStatic
    public static final PreliminaryLoopVisitor visitTryBlock(KtTryExpression ktTryExpression) {
        return INSTANCE.visitTryBlock(ktTryExpression);
    }

    private PreliminaryLoopVisitor() {
    }

    public final DataFlowInfo clearDataFlowInfoForAssignedLocalVariables(DataFlowInfo dataFlowInfo, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        THashSet<DataFlowValue> tHashSet = new THashSet();
        THashSet tHashSet2 = tHashSet;
        Iterator<Tuple2<DataFlowValue, Nullability>> it = dataFlowInfo.getCompleteNullabilityInfo().iterator();
        final PreliminaryLoopVisitor$clearDataFlowInfoForAssignedLocalVariables$nonTrivialValues$1$1 preliminaryLoopVisitor$clearDataFlowInfoForAssignedLocalVariables$nonTrivialValues$1$1 = new Function1<Tuple2<DataFlowValue, Nullability>, DataFlowValue>() { // from class: org.jetbrains.kotlin.types.expressions.PreliminaryLoopVisitor$clearDataFlowInfoForAssignedLocalVariables$nonTrivialValues$1$1
            @Override // kotlin.jvm.functions.Function1
            public final DataFlowValue invoke(Tuple2<DataFlowValue, Nullability> tuple2) {
                return tuple2._1;
            }
        };
        Iterable map = it.map(new Function() { // from class: org.jetbrains.kotlin.types.expressions.PreliminaryLoopVisitor$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PreliminaryLoopVisitor.clearDataFlowInfoForAssignedLocalVariables$lambda$2$lambda$0(preliminaryLoopVisitor$clearDataFlowInfoForAssignedLocalVariables$nonTrivialValues$1$1, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "map(...)");
        CollectionsKt.addAll(tHashSet2, map);
        Iterator<Tuple2<DataFlowValue, Set<KotlinType>>> it2 = dataFlowInfo.getCompleteTypeInfo().iterator();
        final PreliminaryLoopVisitor$clearDataFlowInfoForAssignedLocalVariables$nonTrivialValues$1$2 preliminaryLoopVisitor$clearDataFlowInfoForAssignedLocalVariables$nonTrivialValues$1$2 = new Function1<Tuple2<DataFlowValue, Set<KotlinType>>, DataFlowValue>() { // from class: org.jetbrains.kotlin.types.expressions.PreliminaryLoopVisitor$clearDataFlowInfoForAssignedLocalVariables$nonTrivialValues$1$2
            @Override // kotlin.jvm.functions.Function1
            public final DataFlowValue invoke(Tuple2<DataFlowValue, Set<KotlinType>> tuple2) {
                return tuple2._1;
            }
        };
        Iterable map2 = it2.map(new Function() { // from class: org.jetbrains.kotlin.types.expressions.PreliminaryLoopVisitor$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PreliminaryLoopVisitor.clearDataFlowInfoForAssignedLocalVariables$lambda$2$lambda$1(preliminaryLoopVisitor$clearDataFlowInfoForAssignedLocalVariables$nonTrivialValues$1$2, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map2, "map(...)");
        CollectionsKt.addAll(tHashSet2, map2);
        LinkedHashSet<DataFlowValue> linkedHashSet = new LinkedHashSet();
        for (DataFlowValue dataFlowValue : tHashSet) {
            IdentifierInfo identifierInfo = dataFlowValue.getIdentifierInfo();
            if (dataFlowValue.getKind() == DataFlowValue.Kind.STABLE_VARIABLE && (identifierInfo instanceof IdentifierInfo.Variable)) {
                VariableDescriptor variable = ((IdentifierInfo.Variable) identifierInfo).getVariable();
                if ((variable instanceof LocalVariableDescriptor) && hasWriters(variable)) {
                    linkedHashSet.add(dataFlowValue);
                }
            }
        }
        for (DataFlowValue dataFlowValue2 : linkedHashSet) {
            Intrinsics.checkNotNull(dataFlowValue2);
            dataFlowInfo = dataFlowInfo.clearValueInfo(dataFlowValue2, languageVersionSettings);
        }
        return dataFlowInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DataFlowValue clearDataFlowInfoForAssignedLocalVariables$lambda$2$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (DataFlowValue) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DataFlowValue clearDataFlowInfoForAssignedLocalVariables$lambda$2$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (DataFlowValue) tmp0.invoke(obj);
    }

    /* JADX INFO: compiled from: PreliminaryLoopVisitor.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/PreliminaryLoopVisitor$Companion;", "", "()V", "visitCatchBlocks", "Lorg/jetbrains/kotlin/types/expressions/PreliminaryLoopVisitor;", "tryExpression", "Lorg/jetbrains/kotlin/psi/KtTryExpression;", "isBlockShouldBeVisited", "", "", "visitLoop", "loopExpression", "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "visitTryBlock", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final PreliminaryLoopVisitor visitLoop(KtLoopExpression loopExpression) {
            Intrinsics.checkNotNullParameter(loopExpression, "loopExpression");
            PreliminaryLoopVisitor preliminaryLoopVisitor = new PreliminaryLoopVisitor(null);
            loopExpression.accept(preliminaryLoopVisitor, null);
            return preliminaryLoopVisitor;
        }

        @JvmStatic
        public final PreliminaryLoopVisitor visitTryBlock(KtTryExpression tryExpression) {
            Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
            PreliminaryLoopVisitor preliminaryLoopVisitor = new PreliminaryLoopVisitor(null);
            tryExpression.getTryBlock().accept(preliminaryLoopVisitor, null);
            return preliminaryLoopVisitor;
        }

        @JvmStatic
        public final PreliminaryLoopVisitor visitCatchBlocks(KtTryExpression tryExpression) {
            Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
            List<KtCatchClause> catchClauses = tryExpression.getCatchClauses();
            Intrinsics.checkNotNullExpressionValue(catchClauses, "getCatchClauses(...)");
            List<KtCatchClause> list = catchClauses;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (KtCatchClause ktCatchClause : list) {
                arrayList.add(true);
            }
            return visitCatchBlocks(tryExpression, arrayList);
        }

        @JvmStatic
        public final PreliminaryLoopVisitor visitCatchBlocks(KtTryExpression tryExpression, List<Boolean> isBlockShouldBeVisited) {
            Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
            Intrinsics.checkNotNullParameter(isBlockShouldBeVisited, "isBlockShouldBeVisited");
            List<KtCatchClause> catchClauses = tryExpression.getCatchClauses();
            Intrinsics.checkNotNullExpressionValue(catchClauses, "getCatchClauses(...)");
            catchClauses.size();
            isBlockShouldBeVisited.size();
            PreliminaryLoopVisitor preliminaryLoopVisitor = new PreliminaryLoopVisitor(null);
            List listZip = CollectionsKt.zip(catchClauses, isBlockShouldBeVisited);
            ArrayList arrayList = new ArrayList();
            for (Object obj : listZip) {
                if (((Boolean) ((Pair) obj).component2()).booleanValue()) {
                    arrayList.add(obj);
                }
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                KtExpression catchBody = ((KtCatchClause) ((Pair) it.next()).component1()).getCatchBody();
                if (catchBody != null) {
                }
            }
            return preliminaryLoopVisitor;
        }
    }
}
