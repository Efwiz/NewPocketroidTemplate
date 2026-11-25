package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.IrWhenUtils;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.DumpKotlinLikeKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.wasm.ir.WasmExpressionBuilder;
import org.jetbrains.kotlin.wasm.ir.WasmI32;
import org.jetbrains.kotlin.wasm.ir.WasmImmediate;
import org.jetbrains.kotlin.wasm.ir.WasmLocal;
import org.jetbrains.kotlin.wasm.ir.WasmOp;
import org.jetbrains.kotlin.wasm.ir.WasmType;
import org.jetbrains.kotlin.wasm.ir.WasmUnreachableType;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: OptimisedWhenGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0018\u00010\u00012\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0001H\u0002\u001a(\u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0001H\u0002\u001aD\u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u00012\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001ap\u0010\b\u001a\u00020\t\"\u0004\b\u0000\u0010\u0016*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0018\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u0002H\u00160\u00180\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\t0\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u001eH\u0002\u001aZ\u0010\u001f\u001a\u00020\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0012\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u00012\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010!\u001a\u00020\u000f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00012\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a\u001c\u0010#\u001a\u00020$*\u00020\n2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¨\u0006'"}, d2 = {"tryExtractEqEqNumberConditions", "", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ExtractedWhenCondition;", "", "symbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "conditions", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "createBinaryTable", "", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/BodyGenerator;", "selectorLocal", "Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "intBranches", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ExtractedWhenBranch;", "", "elseExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "resultType", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "expectedType", "Lorg/jetbrains/kotlin/ir/types/IrType;", ExifInterface.GPS_DIRECTION_TRUE, "sortedCases", "Lkotlin/Pair;", "fromIncl", "toExcl", "thenBody", "Lkotlin/Function1;", "elseBody", "Lkotlin/Function0;", "genTableIntSwitch", "branches", "shift", "brTable", "tryGenerateOptimisedWhen", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OptimisedWhenGeneratorKt {
    public static final boolean tryGenerateOptimisedWhen(BodyGenerator bodyGenerator, IrWhen expression, WasmSymbols symbols) {
        IrExpression valueArgument;
        boolean z;
        List<ExtractedWhenCondition<Object>> listTryExtractEqEqNumberConditions;
        Intrinsics.checkNotNullParameter(bodyGenerator, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(symbols, "symbols");
        boolean z2 = false;
        if (expression.getBranches().size() <= 2) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        boolean z3 = true;
        IrExpression result = null;
        for (IrBranch irBranch : expression.getBranches()) {
            if (IrUtilsKt.isElseBranch(irBranch)) {
                result = irBranch.getResult();
            } else {
                ArrayList<IrCall> arrayListMatchConditions = IrWhenUtils.INSTANCE.matchConditions(symbols.getIrBuiltIns().getOrorSymbol(), irBranch.getCondition());
                if (arrayListMatchConditions == null || (listTryExtractEqEqNumberConditions = tryExtractEqEqNumberConditions(symbols, arrayListMatchConditions)) == null) {
                    return z2;
                }
                List<ExtractedWhenCondition<Object>> list = listTryExtractEqEqNumberConditions;
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : list) {
                    if (!linkedHashSet.contains(((ExtractedWhenCondition) obj).getConst().getValue())) {
                        arrayList2.add(obj);
                    }
                }
                ArrayList arrayList3 = arrayList2;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList4.add(((ExtractedWhenCondition) it.next()).getConst().getValue());
                }
                linkedHashSet.addAll(arrayList4);
                if (!arrayList3.isEmpty()) {
                    z3 = z3 && arrayList3.size() == 1;
                    arrayList.add(new ExtractedWhenBranch(arrayList3, irBranch.getResult()));
                }
                z2 = false;
            }
        }
        if (arrayList.isEmpty() || (valueArgument = ((ExtractedWhenCondition) ((ExtractedWhenBranch) arrayList.get(0)).getConditions().get(0)).getCondition().getValueArgument(0)) == null || !(valueArgument instanceof IrGetValue)) {
            return false;
        }
        IrValueSymbol symbol = ((IrGetValue) valueArgument).getSymbol();
        ArrayList<ExtractedWhenBranch> arrayList5 = arrayList;
        if ((arrayList5 instanceof Collection) && arrayList5.isEmpty()) {
            z = true;
            break;
        }
        Iterator it2 = arrayList5.iterator();
        loop15: while (true) {
            if (!it2.hasNext()) {
                z = true;
                break;
            }
            List conditions = ((ExtractedWhenBranch) it2.next()).getConditions();
            if (!(conditions instanceof Collection) || !conditions.isEmpty()) {
                Iterator it3 = conditions.iterator();
                while (it3.hasNext()) {
                    IrExpression valueArgument2 = ((ExtractedWhenCondition) it3.next()).getCondition().getValueArgument(0);
                    IrGetValue irGetValue = valueArgument2 instanceof IrGetValue ? (IrGetValue) valueArgument2 : null;
                    if (!Intrinsics.areEqual(irGetValue != null ? irGetValue.getSymbol() : null, symbol)) {
                        z = false;
                        break loop15;
                    }
                }
            }
        }
        if (!z) {
            return false;
        }
        Iterator it4 = arrayList.iterator();
        while (it4.hasNext()) {
            List conditions2 = ((ExtractedWhenBranch) it4.next()).getConditions();
            if (!(conditions2 instanceof Collection) || !conditions2.isEmpty()) {
                Iterator it5 = conditions2.iterator();
                while (it5.hasNext()) {
                    if (!((ExtractedWhenCondition) it5.next()).getConst().getKind().equals(IrConstKind.Int.INSTANCE)) {
                        return false;
                    }
                }
            }
        }
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        for (ExtractedWhenBranch extractedWhenBranch : arrayList5) {
            Intrinsics.checkNotNull(extractedWhenBranch, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.wasm.ir2wasm.ExtractedWhenBranch<kotlin.Int>");
            arrayList6.add(extractedWhenBranch);
        }
        ArrayList arrayList7 = arrayList6;
        ArrayList arrayList8 = arrayList7;
        Iterator it6 = arrayList8.iterator();
        if (!it6.hasNext()) {
            throw new NoSuchElementException();
        }
        Iterator it7 = ((ExtractedWhenBranch) it6.next()).getConditions().iterator();
        if (!it7.hasNext()) {
            throw new NoSuchElementException();
        }
        int iIntValue = ((Number) ((ExtractedWhenCondition) it7.next()).getConst().getValue()).intValue();
        while (it7.hasNext()) {
            int iIntValue2 = ((Number) ((ExtractedWhenCondition) it7.next()).getConst().getValue()).intValue();
            if (iIntValue < iIntValue2) {
                iIntValue = iIntValue2;
            }
        }
        while (it6.hasNext()) {
            Iterator it8 = ((ExtractedWhenBranch) it6.next()).getConditions().iterator();
            if (!it8.hasNext()) {
                throw new NoSuchElementException();
            }
            int iIntValue3 = ((Number) ((ExtractedWhenCondition) it8.next()).getConst().getValue()).intValue();
            while (it8.hasNext()) {
                int iIntValue4 = ((Number) ((ExtractedWhenCondition) it8.next()).getConst().getValue()).intValue();
                if (iIntValue3 < iIntValue4) {
                    iIntValue3 = iIntValue4;
                }
            }
            if (iIntValue < iIntValue3) {
                iIntValue = iIntValue3;
            }
        }
        Iterator it9 = arrayList8.iterator();
        if (!it9.hasNext()) {
            throw new NoSuchElementException();
        }
        Iterator it10 = ((ExtractedWhenBranch) it9.next()).getConditions().iterator();
        if (!it10.hasNext()) {
            throw new NoSuchElementException();
        }
        int iIntValue5 = ((Number) ((ExtractedWhenCondition) it10.next()).getConst().getValue()).intValue();
        while (it10.hasNext()) {
            int iIntValue6 = ((Number) ((ExtractedWhenCondition) it10.next()).getConst().getValue()).intValue();
            if (iIntValue5 > iIntValue6) {
                iIntValue5 = iIntValue6;
            }
        }
        while (it9.hasNext()) {
            Iterator it11 = ((ExtractedWhenBranch) it9.next()).getConditions().iterator();
            if (!it11.hasNext()) {
                throw new NoSuchElementException();
            }
            int iIntValue7 = ((Number) ((ExtractedWhenCondition) it11.next()).getConst().getValue()).intValue();
            while (it11.hasNext()) {
                int iIntValue8 = ((Number) ((ExtractedWhenCondition) it11.next()).getConst().getValue()).intValue();
                if (iIntValue7 > iIntValue8) {
                    iIntValue7 = iIntValue8;
                }
            }
            if (iIntValue5 > iIntValue7) {
                iIntValue5 = iIntValue7;
            }
        }
        if (iIntValue5 == iIntValue) {
            return false;
        }
        int i = 0;
        WasmLocal wasmLocalReferenceLocal = bodyGenerator.getFunctionContext().referenceLocal(SyntheticLocalType.TABLE_SWITCH_SELECTOR);
        bodyGenerator.generateExpression$backend_wasm(valueArgument);
        SourceLocation sourceLocationNoLocation = SourceLocation.INSTANCE.NoLocation("When's binary search infra");
        bodyGenerator.getBody().buildSetLocal(wasmLocalReferenceLocal, sourceLocationNoLocation);
        WasmType wasmTypeTransformBlockResultType = bodyGenerator.getContext().transformBlockResultType(expression.getType());
        int i2 = iIntValue - iIntValue5;
        if (i2 > 0 && i2 <= linkedHashSet.size() * 2) {
            ArrayList arrayList9 = new ArrayList();
            if (iIntValue5 <= iIntValue) {
                int i3 = iIntValue5;
                while (true) {
                    Iterator it12 = arrayList7.iterator();
                    int size = i;
                    while (true) {
                        if (!it12.hasNext()) {
                            size = -1;
                            break;
                        }
                        List conditions3 = ((ExtractedWhenBranch) it12.next()).getConditions();
                        if (!(conditions3 instanceof Collection) || !conditions3.isEmpty()) {
                            Iterator it13 = conditions3.iterator();
                            while (it13.hasNext()) {
                                if (((Number) ((ExtractedWhenCondition) it13.next()).getConst().getValue()).intValue() == i3) {
                                    break;
                                }
                            }
                        }
                        size++;
                    }
                    if (size == -1) {
                        size = arrayList7.size();
                    }
                    arrayList9.add(Integer.valueOf(size));
                    if (i3 == iIntValue) {
                        break;
                    }
                    i3++;
                    i = 0;
                }
            }
            genTableIntSwitch(bodyGenerator, wasmLocalReferenceLocal, wasmTypeTransformBlockResultType, arrayList7, result, iIntValue5, arrayList9, expression.getType());
        } else if (z3) {
            createBinaryTable(bodyGenerator, wasmLocalReferenceLocal, arrayList7, result, wasmTypeTransformBlockResultType, expression.getType());
        } else {
            createBinaryTable(bodyGenerator, wasmLocalReferenceLocal, arrayList7);
            bodyGenerator.getBody().buildSetLocal(wasmLocalReferenceLocal, sourceLocationNoLocation);
            genTableIntSwitch(bodyGenerator, wasmLocalReferenceLocal, wasmTypeTransformBlockResultType, arrayList7, result, 0, CollectionsKt.toList(CollectionsKt.getIndices(arrayList7)), expression.getType());
        }
        return true;
    }

    private static final void createBinaryTable(final BodyGenerator bodyGenerator, WasmLocal wasmLocal, final List<ExtractedWhenBranch<Integer>> list) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            List conditions = ((ExtractedWhenBranch) obj).getConditions();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(conditions, 10));
            Iterator it = conditions.iterator();
            while (it.hasNext()) {
                arrayList2.add(TuplesKt.to(((ExtractedWhenCondition) it.next()).getConst().getValue(), Integer.valueOf(i)));
            }
            CollectionsKt.addAll(arrayList, arrayList2);
            i = i2;
        }
        if (arrayList.size() > 1) {
            CollectionsKt.sortWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.OptimisedWhenGeneratorKt$createBinaryTable$$inlined$sortBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues((Integer) ((Pair) t).getFirst(), (Integer) ((Pair) t2).getFirst());
                }
            });
        }
        final SourceLocation sourceLocationNoLocation = SourceLocation.INSTANCE.NoLocation("When's binary search infra");
        createBinaryTable(bodyGenerator, wasmLocal, WasmI32.INSTANCE, arrayList, 0, arrayList.size(), new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.OptimisedWhenGeneratorKt$createBinaryTable$thenBody$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i3) {
                bodyGenerator.getBody().buildConstI32(i3, sourceLocationNoLocation);
            }
        }, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.OptimisedWhenGeneratorKt$createBinaryTable$elseBody$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                bodyGenerator.getBody().buildConstI32(list.size(), sourceLocationNoLocation);
            }
        });
    }

    private static final List<ExtractedWhenCondition<Object>> tryExtractEqEqNumberConditions(WasmSymbols wasmSymbols, List<? extends IrCall> list) {
        if (list.isEmpty()) {
            return null;
        }
        IrCall irCall = list.get(0);
        IrSimpleFunctionSymbol symbol = irCall.getSymbol();
        if (!wasmSymbols.getEqualityFunctions().values().contains(symbol)) {
            symbol = null;
        }
        if (symbol == null || irCall.getValueArgumentsCount() != 2) {
            return null;
        }
        List<? extends IrCall> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (!Intrinsics.areEqual(((IrCall) it.next()).getSymbol(), symbol)) {
                    return null;
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        for (IrCall irCall2 : list) {
            if (!Intrinsics.areEqual(irCall2.getSymbol(), symbol)) {
                return null;
            }
            IrExpression valueArgument = irCall2.getValueArgument(1);
            IrConst irConst = valueArgument instanceof IrConst ? (IrConst) valueArgument : null;
            if (irConst == null) {
                return null;
            }
            arrayList.add(new ExtractedWhenCondition(irCall2, irConst));
        }
        return arrayList;
    }

    private static final void createBinaryTable(final BodyGenerator bodyGenerator, WasmLocal wasmLocal, List<ExtractedWhenBranch<Integer>> list, IrExpression irExpression, WasmType wasmType, final IrType irType) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            ExtractedWhenBranch extractedWhenBranch = (ExtractedWhenBranch) it.next();
            arrayList.add(TuplesKt.to(((ExtractedWhenCondition) extractedWhenBranch.getConditions().get(0)).getConst().getValue(), extractedWhenBranch.getExpression()));
        }
        if (arrayList.size() > 1) {
            CollectionsKt.sortWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.OptimisedWhenGeneratorKt$createBinaryTable$$inlined$sortBy$2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues((Integer) ((Pair) t).getFirst(), (Integer) ((Pair) t2).getFirst());
                }
            });
        }
        WasmExpressionBuilder body = bodyGenerator.getBody();
        body.setNumberOfNestedBlocks(body.getNumberOfNestedBlocks() + 1);
        body.buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(wasmType));
        final int numberOfNestedBlocks = body.getNumberOfNestedBlocks();
        createBinaryTable(bodyGenerator, wasmLocal, null, arrayList, 0, arrayList.size(), new Function1<IrExpression, Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.OptimisedWhenGeneratorKt$createBinaryTable$5$thenBody$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrExpression irExpression2) {
                invoke2(irExpression2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrExpression result) {
                Intrinsics.checkNotNullParameter(result, "result");
                bodyGenerator.generateWithExpectedType$backend_wasm(result, irType);
                bodyGenerator.getBody().buildBr(numberOfNestedBlocks, SourceLocation.INSTANCE.NoLocation("Break from a when"));
            }
        }, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.OptimisedWhenGeneratorKt$createBinaryTable$5$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }
        });
        if (irExpression != null) {
            bodyGenerator.generateWithExpectedType$backend_wasm(irExpression, irType);
        } else if (wasmType != null) {
            if (IrTypePredicatesKt.isUnit(irType)) {
                bodyGenerator.buildGetUnit(bodyGenerator.getBody());
            } else {
                throw new IllegalStateException(("'When' without else branch and non Unit type: " + DumpKotlinLikeKt.dumpKotlinLike(irType)).toString());
            }
        }
        body.buildEnd();
    }

    private static final <T> void createBinaryTable(BodyGenerator bodyGenerator, WasmLocal wasmLocal, WasmType wasmType, List<? extends Pair<Integer, ? extends T>> list, int i, int i2, Function1<? super T, Unit> function1, Function0<Unit> function0) {
        SourceLocation sourceLocationNoLocation = SourceLocation.INSTANCE.NoLocation("When's binary search infra");
        int i3 = i2 - i;
        if (i3 == 1) {
            Pair<Integer, ? extends T> pair = list.get(i);
            int iIntValue = pair.component1().intValue();
            T tComponent2 = pair.component2();
            bodyGenerator.getBody().buildGetLocal(wasmLocal, sourceLocationNoLocation);
            bodyGenerator.getBody().buildConstI32(iIntValue, sourceLocationNoLocation);
            bodyGenerator.getBody().buildInstr(WasmOp.I32_EQ, sourceLocationNoLocation, new WasmImmediate[0]);
            bodyGenerator.getBody().buildIf("binary_tree_branch", wasmType);
            function1.invoke(tComponent2);
            bodyGenerator.getBody().buildElse();
            function0.invoke();
            bodyGenerator.getBody().buildEnd();
            return;
        }
        int i4 = i + (i3 / 2);
        bodyGenerator.getBody().buildGetLocal(wasmLocal, sourceLocationNoLocation);
        bodyGenerator.getBody().buildConstI32(list.get(i4).getFirst().intValue(), sourceLocationNoLocation);
        bodyGenerator.getBody().buildInstr(WasmOp.I32_LT_S, sourceLocationNoLocation, new WasmImmediate[0]);
        bodyGenerator.getBody().buildIf("binary_tree_node", wasmType);
        createBinaryTable(bodyGenerator, wasmLocal, wasmType, list, i, i4, function1, function0);
        bodyGenerator.getBody().buildElse();
        createBinaryTable(bodyGenerator, wasmLocal, wasmType, list, i4, i2, function1, function0);
        bodyGenerator.getBody().buildEnd();
    }

    private static final void genTableIntSwitch(BodyGenerator bodyGenerator, WasmLocal wasmLocal, WasmType wasmType, List<ExtractedWhenBranch<Integer>> list, IrExpression irExpression, int i, List<Integer> list2, IrType irType) {
        SourceLocation sourceLocationNoLocation = SourceLocation.INSTANCE.NoLocation("When's binary search infra");
        int numberOfNestedBlocks = bodyGenerator.getBody().getNumberOfNestedBlocks();
        int size = list.size() + 2;
        for (int i2 = 0; i2 < size; i2++) {
            bodyGenerator.getBody().buildBlock(wasmType);
        }
        if (wasmType != null && !(wasmType instanceof WasmUnreachableType)) {
            DeclarationGeneratorKt.generateDefaultInitializerForType(wasmType, bodyGenerator.getBody());
        }
        bodyGenerator.getBody().buildGetLocal(wasmLocal, sourceLocationNoLocation);
        if (i != 0) {
            bodyGenerator.getBody().buildConstI32(i, sourceLocationNoLocation);
            bodyGenerator.getBody().buildInstr(WasmOp.I32_SUB, sourceLocationNoLocation, new WasmImmediate[0]);
        }
        bodyGenerator.getBody().buildInstr(WasmOp.BR_TABLE, sourceLocationNoLocation, new WasmImmediate.LabelIdxVector(list2), new WasmImmediate.LabelIdx(list.size()));
        bodyGenerator.getBody().buildEnd();
        for (ExtractedWhenBranch<Integer> extractedWhenBranch : list) {
            if (wasmType != null && !(wasmType instanceof WasmUnreachableType)) {
                bodyGenerator.getBody().buildDrop(sourceLocationNoLocation);
            }
            bodyGenerator.generateWithExpectedType$backend_wasm(extractedWhenBranch.getExpression(), irType);
            bodyGenerator.getBody().buildBr(numberOfNestedBlocks + 1, sourceLocationNoLocation);
            bodyGenerator.getBody().buildEnd();
        }
        if (irExpression != null) {
            if (wasmType != null && !(wasmType instanceof WasmUnreachableType)) {
                bodyGenerator.getBody().buildDrop(sourceLocationNoLocation);
            }
            bodyGenerator.generateWithExpectedType$backend_wasm(irExpression, irType);
        }
        bodyGenerator.getBody().buildEnd();
        if (numberOfNestedBlocks != bodyGenerator.getBody().getNumberOfNestedBlocks()) {
            throw new IllegalStateException("Check failed.".toString());
        }
    }
}
