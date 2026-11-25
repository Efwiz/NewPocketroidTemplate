package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: WasmVarargExpressionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002:\u0002+,B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u001b\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0017H\u0016J&\u0010\u001c\u001a\u00020\u000f*\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u001f2\u0006\u0010!\u001a\u00020\"H\u0002J\u001c\u0010#\u001a\u00020\u0014*\u00020\u001d2\u0006\u0010$\u001a\u00020\r2\u0006\u0010%\u001a\u00020&H\u0002J\u001c\u0010'\u001a\u00020\r*\u00020\u001d2\u0006\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020\rH\u0002J\u0014\u0010*\u001a\u00020\r*\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006-²\u0006\n\u0010\u0018\u001a\u00020\u0019X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "symbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "isImmediatelyCreatedArray", "", "expr", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "transformFunctionAccessExpression", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "tryVisitWithNoSpread", "irVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "visitFunctionAccess", "visitVararg", "irCopyInto", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "destArr", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "indexVar", "segment", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder;", "irCreateArray", "size", "arrDescr", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$ArrayDescr;", "irIntPlus", "rhs", "lhs", "irSize", "ArrayDescr", "VarargSegmentBuilder", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmVarargExpressionLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final WasmBackendContext context;
    private final WasmSymbols symbols;

    public WasmVarargExpressionLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.symbols = context.getWasmSymbols();
    }

    public final WasmSymbols getSymbols() {
        return this.symbols;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    /* JADX INFO: compiled from: WasmVarargExpressionLowering.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\fR\u0011\u0010\u001a\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\u001c\u001a\u00020\u001d8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001eR\u0011\u0010\u001f\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b#\u0010\u0017R\u0011\u0010$\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b%\u0010\u0017¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$ArrayDescr;", "", "arrayType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "arrayClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getArrayClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getArrayType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "constructors", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getConstructors", "()Lkotlin/sequences/Sequence;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "copyInto", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getCopyInto", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "elementType", "getElementType", "getMethod", "getGetMethod", "isUnsigned", "", "()Z", "primaryConstructor", "getPrimaryConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setMethod", "getSetMethod", "sizeMethod", "getSizeMethod", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ArrayDescr {
        private final IrClass arrayClass;
        private final IrType arrayType;
        private final WasmBackendContext context;

        public ArrayDescr(IrType arrayType, WasmBackendContext context) {
            Intrinsics.checkNotNullParameter(arrayType, "arrayType");
            Intrinsics.checkNotNullParameter(context, "context");
            this.arrayType = arrayType;
            this.context = context;
            IrClass irClass = IrTypesKt.getClass(arrayType);
            if (irClass == null) {
                throw new IllegalArgumentException("Argument " + RenderIrElementKt.render$default(arrayType, (DumpIrTreeOptions) null, 1, (Object) null) + " must have a class");
            }
            this.arrayClass = irClass;
            if (!context.getWasmSymbols().getArrays().contains(irClass.getSymbol())) {
                throw new IllegalStateException(("Argument " + IrUtilsKt.ir2string(irClass) + " must be an array").toString());
            }
        }

        public final IrType getArrayType() {
            return this.arrayType;
        }

        public final WasmBackendContext getContext() {
            return this.context;
        }

        public final IrClass getArrayClass() {
            return this.arrayClass;
        }

        public final boolean isUnsigned() {
            return this.context.getWasmSymbols().getUnsignedTypesToUnsignedArrays().values().contains(this.arrayClass.getSymbol());
        }

        public final IrConstructor getPrimaryConstructor() {
            Object obj;
            IrConstructor next;
            IrValueParameter irValueParameter;
            if (isUnsigned()) {
                Iterator<IrConstructor> it = IrUtilsKt.getConstructors(this.arrayClass).iterator();
                do {
                    obj = null;
                    if (it.hasNext()) {
                        next = it.next();
                        irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) next.getValueParameters());
                    }
                    Intrinsics.checkNotNull(obj);
                    return (IrConstructor) obj;
                } while (!Intrinsics.areEqual(irValueParameter != null ? irValueParameter.getType() : null, this.context.getIrBuiltIns().getIntType()));
                obj = next;
                Intrinsics.checkNotNull(obj);
                return (IrConstructor) obj;
            }
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(this.arrayClass);
            Intrinsics.checkNotNull(primaryConstructor);
            return primaryConstructor;
        }

        public final Sequence<IrConstructor> getConstructors() {
            return IrUtilsKt.getConstructors(this.arrayClass);
        }

        public final IrSimpleFunction getSetMethod() {
            IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(this.arrayClass, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
            Intrinsics.checkNotNull(simpleFunction);
            return simpleFunction.getOwner();
        }

        public final IrSimpleFunction getGetMethod() {
            IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(this.arrayClass, "get");
            Intrinsics.checkNotNull(simpleFunction);
            return simpleFunction.getOwner();
        }

        public final IrSimpleFunction getSizeMethod() {
            IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(this.arrayClass, "size");
            Intrinsics.checkNotNull(propertyGetter);
            return propertyGetter.getOwner();
        }

        public final IrType getElementType() {
            if (IrTypeUtilsKt.isBoxedArray(this.arrayType)) {
                return IrTypeUtilsKt.getArrayElementType(this.arrayType, this.context.getIrBuiltIns());
            }
            return getGetMethod().getReturnType();
        }

        public final IrSimpleFunction getCopyInto() {
            Object next;
            IrSimpleFunction owner;
            IrValueParameter extensionReceiverParameter;
            IrType type;
            IrClassSymbol classOrNull;
            Iterator<T> it = this.context.getWasmSymbols().getArraysCopyInto().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                extensionReceiverParameter = ((IrSimpleFunctionSymbol) next).getOwner().getExtensionReceiverParameter();
            } while (!Intrinsics.areEqual((extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || (classOrNull = IrTypesKt.getClassOrNull(type)) == null) ? null : classOrNull.getOwner(), this.arrayClass));
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) next;
            if (irSimpleFunctionSymbol == null || (owner = irSimpleFunctionSymbol.getOwner()) == null) {
                throw new IllegalArgumentException("copyInto is not found for " + RenderIrElementKt.render$default(this.arrayType, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            return owner;
        }
    }

    private final IrFunctionAccessExpression irCreateArray(IrBlockBuilder irBlockBuilder, IrExpression irExpression, ArrayDescr arrayDescr) {
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder, arrayDescr.getPrimaryConstructor());
        irFunctionAccessExpressionIrCall.putValueArgument(0, irExpression);
        if (irFunctionAccessExpressionIrCall.getTypeArgumentsCount() >= 1) {
            if (irFunctionAccessExpressionIrCall.getTypeArgumentsCount() != 1 || arrayDescr.getArrayClass().getTypeParameters().size() != 1) {
                throw new IllegalStateException("Check failed.".toString());
            }
            irFunctionAccessExpressionIrCall.putTypeArgument(0, arrayDescr.getElementType());
        }
        irFunctionAccessExpressionIrCall.setType(arrayDescr.getArrayType());
        return irFunctionAccessExpressionIrCall;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: WasmVarargExpressionLowering.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u000f\u0010B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH&J\f\u0010\r\u001a\u00020\u000e*\u00020\tH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0011\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder;", "", "wasmContext", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getWasmContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "irCopyInto", "", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "destArr", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "indexVar", "irSize", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Plain", "Spread", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder$Plain;", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder$Spread;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static abstract class VarargSegmentBuilder {
        private final WasmBackendContext wasmContext;

        public /* synthetic */ VarargSegmentBuilder(WasmBackendContext wasmBackendContext, DefaultConstructorMarker defaultConstructorMarker) {
            this(wasmBackendContext);
        }

        public abstract void irCopyInto(IrBlockBuilder irBlockBuilder, IrVariable irVariable, IrVariable irVariable2);

        public abstract IrExpression irSize(IrBlockBuilder irBlockBuilder);

        private VarargSegmentBuilder(WasmBackendContext wasmBackendContext) {
            this.wasmContext = wasmBackendContext;
        }

        public final WasmBackendContext getWasmContext() {
            return this.wasmContext;
        }

        /* JADX INFO: compiled from: WasmVarargExpressionLowering.kt */
        @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0016J\u0012\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010*\u00020\fH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder$Plain;", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder;", "exprs", "", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "wasmContext", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Ljava/util/List;Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getExprs", "()Ljava/util/List;", "irCopyInto", "", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "destArr", "indexVar", "irSize", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Plain extends VarargSegmentBuilder {
            private final List<IrVariable> exprs;

            public final List<IrVariable> getExprs() {
                return this.exprs;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public Plain(List<? extends IrVariable> exprs, WasmBackendContext wasmContext) {
                super(wasmContext, null);
                Intrinsics.checkNotNullParameter(exprs, "exprs");
                Intrinsics.checkNotNullParameter(wasmContext, "wasmContext");
                this.exprs = exprs;
            }

            @Override // org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering.VarargSegmentBuilder
            public IrConstImpl<Integer> irSize(IrBlockBuilder irBlockBuilder) {
                Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
                return ExpressionHelpersKt.irInt$default(irBlockBuilder, this.exprs.size(), null, 2, null);
            }

            @Override // org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering.VarargSegmentBuilder
            public void irCopyInto(final IrBlockBuilder irBlockBuilder, IrVariable destArr, final IrVariable irVariable) {
                Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
                Intrinsics.checkNotNullParameter(destArr, "destArr");
                ArrayDescr arrayDescr = new ArrayDescr(destArr.getType(), getWasmContext());
                Sequence map = SequencesKt.map(SequencesKt.generateSequence(0, new Function1<Integer, Integer>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering$VarargSegmentBuilder$Plain$irCopyInto$indexes$1
                    public final Integer invoke(int i) {
                        return Integer.valueOf(i + 1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Integer invoke(Integer num) {
                        return invoke(num.intValue());
                    }
                }), new Function1<Integer, IrConstImpl<Integer>>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering$VarargSegmentBuilder$Plain$irCopyInto$indexes$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ IrConstImpl<Integer> invoke(Integer num) {
                        return invoke(num.intValue());
                    }

                    public final IrConstImpl<Integer> invoke(int i) {
                        return ExpressionHelpersKt.irInt$default(irBlockBuilder, i, null, 2, null);
                    }
                });
                if (irVariable != null) {
                    map = SequencesKt.map(map, new Function1<IrConstImpl<Integer>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering$VarargSegmentBuilder$Plain$irCopyInto$indexes$3$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrExpression invoke(IrConstImpl<Integer> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                            return WasmVarargExpressionLoweringKt.irIntPlus(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable), it, this.getWasmContext());
                        }
                    });
                }
                for (Pair pair : SequencesKt.zip(CollectionsKt.asSequence(this.exprs), map)) {
                    IrVariable irVariable2 = (IrVariable) pair.component1();
                    IrExpression irExpression = (IrExpression) pair.component2();
                    IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                    IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder2, arrayDescr.getSetMethod());
                    irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, destArr));
                    irFunctionAccessExpressionIrCall.putValueArgument(0, irExpression);
                    irFunctionAccessExpressionIrCall.putValueArgument(1, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable2));
                    irBlockBuilder.unaryPlus(irFunctionAccessExpressionIrCall);
                }
            }
        }

        /* JADX INFO: compiled from: WasmVarargExpressionLowering.kt */
        @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\r\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00032\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u0016J\f\u0010\u0012\u001a\u00020\u0013*\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder$Spread;", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$VarargSegmentBuilder;", "exprVar", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "wasmContext", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getExprVar", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "srcArrDescr", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$ArrayDescr;", "getSrcArrDescr", "()Lorg/jetbrains/kotlin/backend/wasm/lower/WasmVarargExpressionLowering$ArrayDescr;", "irCopyInto", "", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "destArr", "indexVar", "irSize", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Spread extends VarargSegmentBuilder {
            private final IrVariable exprVar;
            private final ArrayDescr srcArrDescr;

            public final IrVariable getExprVar() {
                return this.exprVar;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Spread(IrVariable exprVar, WasmBackendContext wasmContext) {
                super(wasmContext, null);
                Intrinsics.checkNotNullParameter(exprVar, "exprVar");
                Intrinsics.checkNotNullParameter(wasmContext, "wasmContext");
                this.exprVar = exprVar;
                this.srcArrDescr = new ArrayDescr(exprVar.getType(), wasmContext);
            }

            public final ArrayDescr getSrcArrDescr() {
                return this.srcArrDescr;
            }

            @Override // org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering.VarargSegmentBuilder
            public IrExpression irSize(IrBlockBuilder irBlockBuilder) {
                Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder2, this.srcArrDescr.getSizeMethod());
                irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, this.exprVar));
                return irFunctionAccessExpressionIrCall;
            }

            @Override // org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering.VarargSegmentBuilder
            public void irCopyInto(IrBlockBuilder irBlockBuilder, IrVariable destArr, IrVariable irVariable) {
                IrExpression irExpressionIrInt$default;
                Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
                Intrinsics.checkNotNullParameter(destArr, "destArr");
                Intrinsics.areEqual(this.srcArrDescr.getArrayClass(), IrTypesKt.getClass(destArr.getType()));
                if (irVariable == null || (irExpressionIrInt$default = ExpressionHelpersKt.irGet(irBlockBuilder, irVariable)) == null) {
                    irExpressionIrInt$default = ExpressionHelpersKt.irInt$default(irBlockBuilder, 0, null, 2, null);
                }
                IrExpression irExpression = irExpressionIrInt$default;
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder2, this.srcArrDescr.getCopyInto());
                if (irFunctionAccessExpressionIrCall.getTypeArgumentsCount() >= 1) {
                    if (irFunctionAccessExpressionIrCall.getTypeArgumentsCount() != 1 || this.srcArrDescr.getArrayClass().getTypeParameters().size() != 1) {
                        throw new IllegalStateException("Check failed.".toString());
                    }
                    irFunctionAccessExpressionIrCall.putTypeArgument(0, this.srcArrDescr.getElementType());
                }
                irFunctionAccessExpressionIrCall.setExtensionReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, this.exprVar));
                irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder2, destArr));
                irFunctionAccessExpressionIrCall.putValueArgument(1, irExpression);
                irFunctionAccessExpressionIrCall.putValueArgument(2, ExpressionHelpersKt.irInt$default(irBlockBuilder2, 0, null, 2, null));
                irFunctionAccessExpressionIrCall.putValueArgument(3, irSize(irBlockBuilder));
                irBlockBuilder.unaryPlus(irFunctionAccessExpressionIrCall);
            }
        }
    }

    private final void irCopyInto(IrBlockBuilder irBlockBuilder, IrVariable irVariable, IrVariable irVariable2, VarargSegmentBuilder varargSegmentBuilder) {
        varargSegmentBuilder.irCopyInto(irBlockBuilder, irVariable, irVariable2);
    }

    private final IrExpression irSize(IrBlockBuilder irBlockBuilder, VarargSegmentBuilder varargSegmentBuilder) {
        return varargSegmentBuilder.irSize(irBlockBuilder);
    }

    private final IrExpression tryVisitWithNoSpread(IrVararg irVararg, DeclarationIrBuilder builder) {
        IrType type = irVararg.getType();
        if (!org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isUnsignedArray(type)) {
            return irVararg;
        }
        IrClass irClass = IrTypesKt.getClass(type);
        Intrinsics.checkNotNull(irClass);
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass);
        Intrinsics.checkNotNull(primaryConstructor);
        IrType type2 = primaryConstructor.getValueParameters().get(0).getType();
        IrType arrayElementType = IrTypeUtilsKt.getArrayElementType(type2, this.context.getIrBuiltIns());
        irVararg.setType(type2);
        irVararg.setVarargElementType(arrayElementType);
        IrConstructorCall irConstructorCallIrCall$default = ExpressionHelpersKt.irCall$default(builder, primaryConstructor.getSymbol(), type, (IrClass) null, 4, (Object) null);
        irConstructorCallIrCall$default.putValueArgument(0, irVararg);
        return irConstructorCallIrCall$default;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitVararg(IrVararg expression) {
        Object next;
        IrExpression expression2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) expression.getElements());
        IrSpreadElement irSpreadElement = objSingleOrNull instanceof IrSpreadElement ? (IrSpreadElement) objSingleOrNull : null;
        if (irSpreadElement != null) {
            IrExpression expression3 = irSpreadElement.getExpression();
            if (isImmediatelyCreatedArray(expression3)) {
                return expression3.transform((IrElementTransformer<? super Object>) this, (Object) null);
            }
        }
        IrExpression irExpressionVisitVararg = super.visitVararg(expression);
        Intrinsics.checkNotNull(irExpressionVisitVararg, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrVararg");
        IrVararg irVararg = (IrVararg) irExpressionVisitVararg;
        WasmBackendContext wasmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(wasmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
        List<IrVarargElement> elements = irVararg.getElements();
        if (!(elements instanceof Collection) || !elements.isEmpty()) {
            Iterator<T> it = elements.iterator();
            while (it.hasNext()) {
                if (((IrVarargElement) it.next()) instanceof IrSpreadElement) {
                    List<IrVarargElement> elements2 = irVararg.getElements();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements2, 10));
                    for (IrVarargElement irVarargElement : elements2) {
                        if (irVarargElement instanceof IrSpreadElement) {
                            expression2 = ((IrSpreadElement) irVarargElement).getExpression();
                        } else {
                            Intrinsics.checkNotNull(irVarargElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                            expression2 = (IrExpression) irVarargElement;
                        }
                        IrExpression irExpression = expression2;
                        ScopeWithIr currentScope2 = getCurrentScope();
                        Intrinsics.checkNotNull(currentScope2);
                        arrayList.add(currentScope2.getScope().createTemporaryVariable(irExpression, (108 & 2) != 0 ? null : "vararg_temp", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpression.getStartOffset() : 0, (108 & 64) != 0 ? irExpression.getEndOffset() : 0));
                    }
                    ArrayList arrayList2 = arrayList;
                    List list = SequencesKt.toList(SequencesKt.sequence(new WasmVarargExpressionLowering$visitVararg$segments$1(irVararg, arrayList2, this, null)));
                    ArrayDescr arrayDescr = new ArrayDescr(irVararg.getType(), this.context);
                    DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                    IrVararg irVararg2 = irVararg;
                    IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irVararg2.getStartOffset(), irVararg2.getEndOffset(), null, irVararg2.getType(), true);
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        irBlockBuilder.unaryPlus((IrVariable) it2.next());
                    }
                    List<VarargSegmentBuilder> list2 = list;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator it3 = list2.iterator();
                    while (it3.hasNext()) {
                        arrayList3.add(irSize(irBlockBuilder, (VarargSegmentBuilder) it3.next()));
                    }
                    Iterator it4 = arrayList3.iterator();
                    if (it4.hasNext()) {
                        next = it4.next();
                        while (it4.hasNext()) {
                            next = irIntPlus(irBlockBuilder, (IrExpression) next, (IrExpression) it4.next());
                        }
                    } else {
                        next = null;
                    }
                    IrConstImpl irConstImplIrInt$default = (IrExpression) next;
                    if (irConstImplIrInt$default == null) {
                        irConstImplIrInt$default = ExpressionHelpersKt.irInt$default(irBlockBuilder, 0, null, 2, null);
                    }
                    IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                    IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, irCreateArray(irBlockBuilder, irConstImplIrInt$default, arrayDescr), "vararg_array", null, false, null, 28, null);
                    IrVariable irVariableIrTemporary$default2 = list.size() >= 2 ? ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, ExpressionHelpersKt.irInt$default(irBlockBuilder, 0, null, 2, null), "vararg_idx", null, false, null, 28, null) : null;
                    for (VarargSegmentBuilder varargSegmentBuilder : list2) {
                        irCopyInto(irBlockBuilder, irVariableIrTemporary$default, irVariableIrTemporary$default2, varargSegmentBuilder);
                        if (irVariableIrTemporary$default2 != null) {
                            IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
                            IrVariable irVariable = irVariableIrTemporary$default2;
                            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irSet$default(irBlockBuilder3, irVariable, irIntPlus(irBlockBuilder, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariable), irSize(irBlockBuilder, varargSegmentBuilder)), (IrStatementOrigin) null, 4, (Object) null));
                        }
                    }
                    irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder, irVariableIrTemporary$default));
                    return irBlockBuilder.getIrBlockBody();
                }
            }
        }
        return tryVisitWithNoSpread(irVararg, declarationIrBuilderCreateIrBuilder$default);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return transformFunctionAccessExpression(expression);
    }

    private final IrExpression transformFunctionAccessExpression(IrFunctionAccessExpression expression) {
        transformChildrenVoid(expression);
        Lazy lazy = LazyKt.lazy(new Function0<DeclarationIrBuilder>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering$transformFunctionAccessExpression$builder$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DeclarationIrBuilder invoke() {
                WasmBackendContext wasmBackendContext = this.this$0.context;
                ScopeWithIr currentScope = this.this$0.getCurrentScope();
                Intrinsics.checkNotNull(currentScope);
                return LowerUtilsKt.createIrBuilder$default(wasmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
            }
        });
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument = expression.getValueArgument(i);
            IrValueParameter irValueParameter = expression.getSymbol().getOwner().getValueParameters().get(i);
            IrType varargElementType = irValueParameter.getVarargElementType();
            if (valueArgument == null && varargElementType != null) {
                IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irValueParameter.getType());
                Intrinsics.checkNotNull(classOrNull);
                IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(classOrNull.getOwner());
                Intrinsics.checkNotNull(primaryConstructor);
                DeclarationIrBuilder declarationIrBuilderTransformFunctionAccessExpression$lambda$11 = transformFunctionAccessExpression$lambda$11(lazy);
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilderTransformFunctionAccessExpression$lambda$11, primaryConstructor);
                irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(declarationIrBuilderTransformFunctionAccessExpression$lambda$11, 0, null, 2, null));
                if (!primaryConstructor.getTypeParameters().isEmpty()) {
                    if (primaryConstructor.getTypeParameters().size() != 1) {
                        throw new IllegalStateException("Check failed.".toString());
                    }
                    irFunctionAccessExpressionIrCall.putTypeArgument(0, irValueParameter.getVarargElementType());
                }
                expression.putValueArgument(i, irFunctionAccessExpressionIrCall);
            }
        }
        return expression;
    }

    private static final DeclarationIrBuilder transformFunctionAccessExpression$lambda$11(Lazy<DeclarationIrBuilder> lazy) {
        return lazy.getValue();
    }

    private final IrExpression irIntPlus(IrBlockBuilder irBlockBuilder, IrExpression irExpression, IrExpression irExpression2) {
        return WasmVarargExpressionLoweringKt.irIntPlus(irBlockBuilder, irExpression, irExpression2, this.context);
    }

    private final boolean isImmediatelyCreatedArray(IrExpression expr) {
        if (expr instanceof IrFunctionAccessExpression) {
            Sequence<IrConstructor> constructors = new ArrayDescr(expr.getType(), this.context).getConstructors();
            IrFunctionAccessExpression irFunctionAccessExpression = (IrFunctionAccessExpression) expr;
            if (SequencesKt.contains(constructors, irFunctionAccessExpression.getSymbol().getOwner()) || Intrinsics.areEqual(irFunctionAccessExpression.getSymbol(), this.context.getWasmSymbols().getArrayOfNulls())) {
                return true;
            }
        } else {
            if (expr instanceof IrTypeOperatorCall) {
                return isImmediatelyCreatedArray(((IrTypeOperatorCall) expr).getArgument());
            }
            if (expr instanceof IrComposite) {
                IrComposite irComposite = (IrComposite) expr;
                if (irComposite.getStatements().size() == 1 && (irComposite.getStatements().get(0) instanceof IrExpression)) {
                    IrStatement irStatement = irComposite.getStatements().get(0);
                    Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    if (isImmediatelyCreatedArray((IrExpression) irStatement)) {
                        return true;
                    }
                }
            } else if (expr instanceof IrVararg) {
                return true;
            }
        }
        return false;
    }
}
