package org.jetbrains.kotlin.backend.wasm.lower;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.IrWhenUtils;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: WasmStringSwitchOptimizerLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\u0018\u00002\u00020\u00012\u00020\u0002:\u000267B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0016\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u0014H\u0002J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u001dH\u0016J\u0014\u0010\u001e\u001a\u00020\u001f*\u00020 2\u0006\u0010!\u001a\u00020\u0012H\u0002JL\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#*\u00020 2\u0014\u0010%\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010'\u0012\u0004\u0012\u00020(0&2\u001a\u0010)\u001a\u0016\u0012\u0004\u0012\u00020*\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010'0#0&2\u0006\u0010+\u001a\u00020*H\u0002J\u001c\u0010,\u001a\u00020\u0012*\u00020 2\u0006\u0010-\u001a\u00020\u001f2\u0006\u0010.\u001a\u00020*H\u0002JL\u0010/\u001a\b\u0012\u0004\u0012\u00020$0#*\u00020 2\u0014\u0010%\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010'\u0012\u0004\u0012\u00020(0&2\u001a\u0010)\u001a\u0016\u0012\u0004\u0012\u00020*\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010'0#0&2\u0006\u00100\u001a\u00020\u001dH\u0002J\u001c\u00101\u001a\u00020\u001d*\u00020 2\u0006\u0010-\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\u001dH\u0002J4\u00102\u001a\u00020\u001d*\u00020 2\u0006\u0010-\u001a\u00020\u001f2\f\u00103\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u00104\u001a\u00020\u00072\b\u00105\u001a\u0004\u0018\u00010\u0014H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmStringSwitchOptimizerLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "booleanType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "intType", "stringHashCode", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getStringHashCode", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "stringHashCode$delegate", "Lkotlin/Lazy;", "symbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "asEqCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "tryMatchCaseToNullableStringConstant", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "condition", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "addHashCodeVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "firstEqCall", "createBucketSelectors", "", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmStringSwitchOptimizerLowering$BucketSelector;", "stringConstantToMatchedCase", "", "", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmStringSwitchOptimizerLowering$MatchedCase;", "buckets", "", "elseBranchIndex", "createEqEqForIntVariable", "tempIntVariable", "value", "createSimpleBucketSelectors", "transformedWhen", "createTransformedWhen", "createWhenForBucketSelectors", "bucketsSelectors", "selectorsType", "elseBranchExpression", "BucketSelector", "MatchedCase", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmStringSwitchOptimizerLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final IrType booleanType;
    private final WasmBackendContext context;
    private final IrType intType;

    /* JADX INFO: renamed from: stringHashCode$delegate, reason: from kotlin metadata */
    private final Lazy stringHashCode;
    private final WasmSymbols symbols;

    public WasmStringSwitchOptimizerLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        WasmSymbols wasmSymbols = context.getWasmSymbols();
        this.symbols = wasmSymbols;
        this.stringHashCode = LazyKt.lazy(new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmStringSwitchOptimizerLowering$stringHashCode$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunctionSymbol invoke() {
                IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(this.this$0.symbols.getIrBuiltIns().getStringClass(), "hashCode");
                Intrinsics.checkNotNull(simpleFunction);
                return simpleFunction;
            }
        });
        this.intType = wasmSymbols.getIrBuiltIns().getIntType();
        this.booleanType = wasmSymbols.getIrBuiltIns().getBooleanType();
    }

    private final IrSimpleFunctionSymbol getStringHashCode() {
        return (IrSimpleFunctionSymbol) this.stringHashCode.getValue();
    }

    private final IrCall createEqEqForIntVariable(IrBlockBuilder irBlockBuilder, IrVariable irVariable, int i) {
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBuilder2, irBlockBuilder.getContext().getIrBuiltIns().getEqeqSymbol(), this.booleanType, 0, 0, null, 28, null);
        irCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
        irCallIrCall$default.putValueArgument(1, IrUtilsKt.toIrConst$default(Integer.valueOf(i), this.intType, 0, 0, 6, null));
        return irCallIrCall$default;
    }

    private final IrCall asEqCall(IrExpression expression) {
        IrCall irCall = expression instanceof IrCall ? (IrCall) expression : null;
        if (irCall == null || !Intrinsics.areEqual(irCall.getSymbol(), this.context.getIrBuiltIns().getEqeqSymbol())) {
            return null;
        }
        return irCall;
    }

    /* JADX INFO: compiled from: WasmStringSwitchOptimizerLowering.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmStringSwitchOptimizerLowering$MatchedCase;", "", "condition", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "branchIndex", "", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;I)V", "getBranchIndex", "()I", "getCondition", "()Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class MatchedCase {
        private final int branchIndex;
        private final IrCall condition;

        public MatchedCase(IrCall condition, int i) {
            Intrinsics.checkNotNullParameter(condition, "condition");
            this.condition = condition;
            this.branchIndex = i;
        }

        public final int getBranchIndex() {
            return this.branchIndex;
        }

        public final IrCall getCondition() {
            return this.condition;
        }
    }

    /* JADX INFO: compiled from: WasmStringSwitchOptimizerLowering.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmStringSwitchOptimizerLowering$BucketSelector;", "", "hashCode", "", "selector", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(ILorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getHashCode", "()I", "getSelector", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class BucketSelector {
        private final int hashCode;
        private final IrExpression selector;

        public BucketSelector(int i, IrExpression selector) {
            Intrinsics.checkNotNullParameter(selector, "selector");
            this.hashCode = i;
            this.selector = selector;
        }

        public final int getHashCode() {
            return this.hashCode;
        }

        public final IrExpression getSelector() {
            return this.selector;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    private final IrConst<?> tryMatchCaseToNullableStringConstant(IrExpression condition) {
        IrCall irCallAsEqCall = asEqCall(condition);
        if (irCallAsEqCall == null || irCallAsEqCall.getValueArgumentsCount() < 2) {
            return null;
        }
        IrExpression valueArgument = irCallAsEqCall.getValueArgument(0);
        IrConst<?> irConst = valueArgument instanceof IrConst ? (IrConst) valueArgument : null;
        if (irConst == null) {
            IrExpression valueArgument2 = irCallAsEqCall.getValueArgument(1);
            irConst = valueArgument2 instanceof IrConst ? (IrConst) valueArgument2 : null;
            if (irConst == null) {
                return null;
            }
        }
        IrConstKind<?> kind = irConst.getKind();
        if (Intrinsics.areEqual(kind, IrConstKind.String.INSTANCE) ? true : Intrinsics.areEqual(kind, IrConstKind.Null.INSTANCE)) {
            return irConst;
        }
        return null;
    }

    private final IrVariable addHashCodeVariable(IrBlockBuilder irBlockBuilder, IrCall irCall) {
        int i;
        IrCall irCallIrIfThenElse$default;
        IrExpression valueArgument = irCall.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        if (valueArgument instanceof IrConst) {
            valueArgument = irCall.getValueArgument(1);
            Intrinsics.checkNotNull(valueArgument);
            i = 1;
        } else {
            i = 0;
        }
        IrType type = valueArgument.getType();
        IrDeclarationParent localDeclarationParent = irBlockBuilder.getScope().getLocalDeclarationParent();
        int startOffset = irBlockBuilder.getStartOffset();
        int endOffset = irBlockBuilder.getEndOffset();
        OPTIMISED_WHEN_SUBJECT optimised_when_subject = OPTIMISED_WHEN_SUBJECT.INSTANCE;
        Name nameIdentifier = Name.identifier("tmp_when_subject");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(localDeclarationParent, startOffset, endOffset, optimised_when_subject, nameIdentifier, type, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
        irVariableBuildVariable.setInitializer(valueArgument);
        irBlockBuilder.unaryPlus(irVariableBuildVariable);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrVariable irVariable = irVariableBuildVariable;
        irCall.putValueArgument(i, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
        IrDeclarationParent localDeclarationParent2 = irBlockBuilder.getScope().getLocalDeclarationParent();
        int startOffset2 = irBlockBuilder.getStartOffset();
        int endOffset2 = irBlockBuilder.getEndOffset();
        OPTIMISED_WHEN_SUBJECT optimised_when_subject2 = OPTIMISED_WHEN_SUBJECT.INSTANCE;
        Name nameIdentifier2 = Name.identifier("tmp_int_when_subject");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        IrVariable irVariableBuildVariable2 = DeclarationBuildersKt.buildVariable(localDeclarationParent2, startOffset2, endOffset2, optimised_when_subject2, nameIdentifier2, this.intType, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBuilder2, getStringHashCode(), this.intType, 0, 0, null, 28, null);
        irCallIrCall$default.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
        if (IrTypeUtilsKt.isNullable(type)) {
            IrCall irCallIrCall$default2 = ExpressionHelpersKt.irCall$default(irBlockBuilder2, irBlockBuilder.getContext().getIrBuiltIns().getEqeqeqSymbol(), this.booleanType, 0, 0, null, 28, null);
            irCallIrCall$default2.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
            irCallIrCall$default2.putValueArgument(1, ExpressionHelpersKt.irNull(irBlockBuilder2, type));
            irCallIrIfThenElse$default = ExpressionHelpersKt.irIfThenElse$default(irBlockBuilder2, this.intType, irCallIrCall$default2, IrUtilsKt.toIrConst$default(0, this.intType, 0, 0, 6, null), irCallIrCall$default, null, 16, null);
        } else {
            irCallIrIfThenElse$default = irCallIrCall$default;
        }
        irVariableBuildVariable2.setInitializer(irCallIrIfThenElse$default);
        irBlockBuilder.unaryPlus(irVariableBuildVariable2);
        return irVariableBuildVariable2;
    }

    private final List<BucketSelector> createSimpleBucketSelectors(IrBlockBuilder irBlockBuilder, Map<String, MatchedCase> map, Map<Integer, ? extends List<String>> map2, IrWhen irWhen) {
        IrWhen irWhen2;
        Set<Map.Entry<Integer, ? extends List<String>>> setEntrySet = map2.entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (((List) entry.getValue()).size() == 1) {
                MatchedCase matchedCase = (MatchedCase) MapsKt.getValue(map, (String) ((List) entry.getValue()).get(0));
                irWhen2 = ExpressionHelpersKt.irIfThen$default(irBlockBuilder, irWhen.getType(), matchedCase.getCondition(), irWhen.getBranches().get(matchedCase.getBranchIndex()).getResult(), null, 8, null);
            } else {
                ArrayList arrayList2 = new ArrayList();
                Iterator it2 = ((Iterable) entry.getValue()).iterator();
                while (it2.hasNext()) {
                    MatchedCase matchedCase2 = (MatchedCase) MapsKt.getValue(map, (String) it2.next());
                    arrayList2.add(ExpressionHelpersKt.irBranch(irBlockBuilder, matchedCase2.getCondition(), irWhen.getBranches().get(matchedCase2.getBranchIndex()).getResult()));
                }
                irWhen2 = ExpressionHelpersKt.irWhen(irBlockBuilder, irWhen.getType(), arrayList2);
            }
            arrayList.add(new BucketSelector(((Number) entry.getKey()).intValue(), irWhen2));
        }
        return arrayList;
    }

    private final IrWhen createWhenForBucketSelectors(IrBlockBuilder irBlockBuilder, IrVariable irVariable, List<BucketSelector> list, IrType irType, IrExpression irExpression) {
        ArrayList arrayList = new ArrayList();
        for (BucketSelector bucketSelector : list) {
            arrayList.add(ExpressionHelpersKt.irBranch(irBlockBuilder, createEqEqForIntVariable(irBlockBuilder, irVariable, bucketSelector.getHashCode()), bucketSelector.getSelector()));
        }
        if (irExpression != null) {
            arrayList.add(ExpressionHelpersKt.irElseBranch(irBlockBuilder, irExpression));
        }
        return ExpressionHelpersKt.irWhen(irBlockBuilder, irType, arrayList);
    }

    private final List<BucketSelector> createBucketSelectors(IrBlockBuilder irBlockBuilder, Map<String, MatchedCase> map, Map<Integer, ? extends List<String>> map2, int i) {
        IrWhen irWhen;
        Set<Map.Entry<Integer, ? extends List<String>>> setEntrySet = map2.entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (((List) entry.getValue()).size() == 1) {
                MatchedCase matchedCase = (MatchedCase) MapsKt.getValue(map, (String) ((List) entry.getValue()).get(0));
                irWhen = ExpressionHelpersKt.irIfThenElse$default(irBlockBuilder, this.intType, matchedCase.getCondition(), IrUtilsKt.toIrConst$default(Integer.valueOf(matchedCase.getBranchIndex()), this.intType, 0, 0, 6, null), IrUtilsKt.toIrConst$default(Integer.valueOf(i), this.intType, 0, 0, 6, null), null, 16, null);
            } else {
                ArrayList arrayList2 = new ArrayList();
                Iterator it2 = ((Iterable) entry.getValue()).iterator();
                while (it2.hasNext()) {
                    MatchedCase matchedCase2 = (MatchedCase) MapsKt.getValue(map, (String) it2.next());
                    arrayList2.add(ExpressionHelpersKt.irBranch(irBlockBuilder, matchedCase2.getCondition(), IrUtilsKt.toIrConst$default(Integer.valueOf(matchedCase2.getBranchIndex()), this.intType, 0, 0, 6, null)));
                }
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                arrayList2.add(ExpressionHelpersKt.irElseBranch(irBlockBuilder2, IrUtilsKt.toIrConst$default(Integer.valueOf(i), this.intType, 0, 0, 6, null)));
                irWhen = ExpressionHelpersKt.irWhen(irBlockBuilder2, this.intType, arrayList2);
            }
            arrayList.add(new BucketSelector(((Number) entry.getKey()).intValue(), irWhen));
        }
        return arrayList;
    }

    private final IrWhen createTransformedWhen(IrBlockBuilder irBlockBuilder, IrVariable irVariable, IrWhen irWhen) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : irWhen.getBranches()) {
            ArrayList arrayList2 = arrayList;
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrBranchImpl irBranchImplIrBranch = (IrBranch) obj;
            if (!IrUtilsKt.isElseBranch(irBranchImplIrBranch)) {
                irBranchImplIrBranch = ExpressionHelpersKt.irBranch(irBlockBuilder, createEqEqForIntVariable(irBlockBuilder, irVariable, i), irBranchImplIrBranch.getResult());
            }
            arrayList2.add(irBranchImplIrBranch);
            i = i2;
        }
        return ExpressionHelpersKt.irWhen(irBlockBuilder, irWhen.getType(), arrayList);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitWhen(IrWhen expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrExpression irExpressionVisitWhen = super.visitWhen(expression);
        Intrinsics.checkNotNull(irExpressionVisitWhen, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrWhen");
        IrWhen irWhen = (IrWhen) irExpressionVisitWhen;
        if (irWhen.getBranches().size() <= 2) {
            return irWhen;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        boolean z = true;
        IrCall irCallAsEqCall = null;
        int i = 0;
        for (Object obj : irWhen.getBranches()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrBranch irBranch = (IrBranch) obj;
            if (!IrUtilsKt.isElseBranch(irBranch)) {
                ArrayList<IrCall> arrayListMatchConditions = IrWhenUtils.INSTANCE.matchConditions(this.context.getIrBuiltIns().getOrorSymbol(), irBranch.getCondition());
                if (arrayListMatchConditions != null && !arrayListMatchConditions.isEmpty()) {
                    z = z && arrayListMatchConditions.size() == 1;
                    for (IrCall irCall : arrayListMatchConditions) {
                        Intrinsics.checkNotNull(irCall);
                        IrCall irCall2 = irCall;
                        IrConst<?> irConstTryMatchCaseToNullableStringConstant = tryMatchCaseToNullableStringConstant(irCall2);
                        if (irConstTryMatchCaseToNullableStringConstant == null) {
                            return irWhen;
                        }
                        Object value = irConstTryMatchCaseToNullableStringConstant.getValue();
                        String str = value instanceof String ? (String) value : null;
                        if (!linkedHashMap.containsKey(str)) {
                            linkedHashMap.put(str, new MatchedCase(irCall, i));
                            if (irCallAsEqCall == null) {
                                irCallAsEqCall = asEqCall(irCall2);
                            }
                        }
                    }
                }
                return irWhen;
            }
            z = false;
            i = i2;
        }
        if (irCallAsEqCall == null || linkedHashMap.size() < 2) {
            return irWhen;
        }
        WasmBackendContext wasmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(wasmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset(), null, irWhen.getType(), false, 64, null);
        IrVariable irVariableAddHashCodeVariable = addHashCodeVariable(irBlockBuilder, irCallAsEqCall);
        Set<String> setKeySet = linkedHashMap.keySet();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj2 : setKeySet) {
            String str2 = (String) obj2;
            Integer numValueOf = Integer.valueOf(str2 != null ? str2.hashCode() : 0);
            Object obj3 = linkedHashMap2.get(numValueOf);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap2.put(numValueOf, obj3);
            }
            ((List) obj3).add(obj2);
        }
        if (z) {
            irBlockBuilder.unaryPlus(createWhenForBucketSelectors(irBlockBuilder, irVariableAddHashCodeVariable, createSimpleBucketSelectors(irBlockBuilder, linkedHashMap, linkedHashMap2, expression), expression.getType(), null));
        } else {
            int size = expression.getBranches().size();
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irSet$default(irBlockBuilder, irVariableAddHashCodeVariable, createWhenForBucketSelectors(irBlockBuilder, irVariableAddHashCodeVariable, createBucketSelectors(irBlockBuilder, linkedHashMap, linkedHashMap2, size), this.intType, IrUtilsKt.toIrConst$default(Integer.valueOf(size), this.intType, 0, 0, 6, null)), (IrStatementOrigin) null, 4, (Object) null));
            irBlockBuilder.unaryPlus(createTransformedWhen(irBlockBuilder, irVariableAddHashCodeVariable, expression));
        }
        return irBlockBuilder.getIrBlockBody();
    }
}
