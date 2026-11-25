package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginKt;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImplKt;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBaseKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.psi2ir.generators.ArgumentsGenerationUtilsKt;
import org.jetbrains.kotlin.psi2ir.generators.CallGenerator;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: compiled from: ArrayAccessAssignmentReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0005\b\u0000\u0018\u0000 ;2\u00020\u0001:\u0004;<=>B\u0089\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\u000e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\r\u0012\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\r\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017J\u001c\u0010 \u001a\u00020\u00032\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00030\"H\u0016J\u0010\u0010 \u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u0003H\u0016J2\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020)2\u0018\u0010*\u001a\u0014\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020)0+H\u0002J\u0018\u0010,\u001a\u00020\u00032\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u001dH\u0002J\u0018\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0002J@\u00106\u001a\u00020\u000e*\u00020\u000e2\u0006\u00107\u001a\u00020)2\f\u00108\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020)0:2\b\u0010$\u001a\u0004\u0018\u00010\u0003H\u0002R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001b\u001a\u00020\u001c*\u00020\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/AssignmentReceiver;", "irArray", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ktIndexExpressions", "", "Lorg/jetbrains/kotlin/psi/KtExpression;", "irIndexExpressions", "indexedGetResolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "indexedSetResolvedCall", "indexedGetCall", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "indexedSetCall", "callGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/CallGenerator;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/psi2ir/generators/CallGenerator;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "descriptor", "indexedGetDescriptor", "indexedSetDescriptor", "operandKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "getOperandKotlinType", "(Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;)Lorg/jetbrains/kotlin/types/KotlinType;", "assign", "withLValue", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValue;", "value", "createLValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValueWithGetterAndSetterCalls;", "kotlinType", "irArrayValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "createIndexValue", "Lkotlin/Function2;", "createSamConvertedVarInitializer", "irIndexVar", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "mostSpecificSamConversion", "postprocessSamConversionsInCompoundAssignment", "", "irBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "compoundAssignmentInfo", "Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver$CompoundAssignmentInfo;", "fillArguments", "arrayValue", "resolvedCall", "ktExpressionToIrIndexValue", "", "Companion", "CompoundAssignmentInfo", "SamConversionsCollector", "SamConversionsRewriter", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayAccessAssignmentReceiver implements AssignmentReceiver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CallGenerator callGenerator;
    private final FunctionDescriptor descriptor;
    private final int endOffset;
    private final Function0<CallBuilder> indexedGetCall;
    private final FunctionDescriptor indexedGetDescriptor;
    private final ResolvedCall<FunctionDescriptor> indexedGetResolvedCall;
    private final Function0<CallBuilder> indexedSetCall;
    private final FunctionDescriptor indexedSetDescriptor;
    private final ResolvedCall<FunctionDescriptor> indexedSetResolvedCall;
    private final IrExpression irArray;
    private final List<IrExpression> irIndexExpressions;
    private final List<KtExpression> ktIndexExpressions;
    private final IrStatementOrigin origin;
    private final int startOffset;

    /* JADX WARN: Multi-variable type inference failed */
    public ArrayAccessAssignmentReceiver(IrExpression irArray, List<? extends KtExpression> ktIndexExpressions, List<? extends IrExpression> irIndexExpressions, ResolvedCall<FunctionDescriptor> resolvedCall, ResolvedCall<FunctionDescriptor> resolvedCall2, Function0<CallBuilder> indexedGetCall, Function0<CallBuilder> indexedSetCall, CallGenerator callGenerator, int i, int i2, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(irArray, "irArray");
        Intrinsics.checkNotNullParameter(ktIndexExpressions, "ktIndexExpressions");
        Intrinsics.checkNotNullParameter(irIndexExpressions, "irIndexExpressions");
        Intrinsics.checkNotNullParameter(indexedGetCall, "indexedGetCall");
        Intrinsics.checkNotNullParameter(indexedSetCall, "indexedSetCall");
        Intrinsics.checkNotNullParameter(callGenerator, "callGenerator");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.irArray = irArray;
        this.ktIndexExpressions = ktIndexExpressions;
        this.irIndexExpressions = irIndexExpressions;
        this.indexedGetResolvedCall = resolvedCall;
        this.indexedSetResolvedCall = resolvedCall2;
        this.indexedGetCall = indexedGetCall;
        this.indexedSetCall = indexedSetCall;
        this.callGenerator = callGenerator;
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        FunctionDescriptor functionDescriptor = resolvedCall != null ? (FunctionDescriptor) resolvedCall.getResultingDescriptor() : null;
        this.indexedGetDescriptor = functionDescriptor;
        FunctionDescriptor functionDescriptor2 = resolvedCall2 != null ? (FunctionDescriptor) resolvedCall2.getResultingDescriptor() : null;
        this.indexedSetDescriptor = functionDescriptor2;
        if (functionDescriptor == null) {
            if (functionDescriptor2 == null) {
                throw new AssertionError("Array access should have either indexed-get call or indexed-set call");
            }
            functionDescriptor = functionDescriptor2;
        }
        this.descriptor = functionDescriptor;
    }

    /* JADX INFO: compiled from: ArrayAccessAssignmentReceiver.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver$CompoundAssignmentInfo;", "", "()V", "indexVariables", "Ljava/util/LinkedHashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lkotlin/collections/LinkedHashSet;", "getIndexVariables", "()Ljava/util/LinkedHashSet;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CompoundAssignmentInfo {
        private final LinkedHashSet<IrVariable> indexVariables = new LinkedHashSet<>();

        public final LinkedHashSet<IrVariable> getIndexVariables() {
            return this.indexVariables;
        }
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver
    public IrExpression assign(Function1<? super LValue, ? extends IrExpression> withLValue) {
        KotlinType type;
        SimpleType unit;
        FunctionDescriptor functionDescriptor;
        Intrinsics.checkNotNullParameter(withLValue, "withLValue");
        FunctionDescriptor functionDescriptor2 = this.indexedGetDescriptor;
        if (functionDescriptor2 == null || (type = functionDescriptor2.getReturnType()) == null) {
            FunctionDescriptor functionDescriptor3 = this.indexedSetDescriptor;
            if (functionDescriptor3 != null) {
                List<ValueParameterDescriptor> valueParameters = functionDescriptor3.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                type = ((ValueParameterDescriptor) CollectionsKt.last((List) valueParameters)).getType();
            } else {
                type = null;
            }
        }
        if (type == null) {
            throw new AssertionError("Array access should have either indexed-get call or indexed-set call");
        }
        if (IrStatementOriginKt.isAssignmentOperatorWithResult(this.origin)) {
            unit = type;
        } else {
            IrBuiltIns irBuiltIns = this.callGenerator.getContext().getIrBuiltIns();
            Intrinsics.checkNotNull(irBuiltIns, "null cannot be cast to non-null type org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors");
            unit = ((IrBuiltInsOverDescriptors) irBuiltIns).getUnit();
        }
        IrType irTypeTranslateType = this.callGenerator.translateType(unit);
        FunctionDescriptor functionDescriptor4 = this.indexedGetDescriptor;
        if ((functionDescriptor4 == null || DynamicCallsKt.isDynamic(functionDescriptor4)) && ((functionDescriptor = this.indexedSetDescriptor) == null || DynamicCallsKt.isDynamic(functionDescriptor))) {
            return withLValue.invoke(createLValue(type, new OnceExpressionValue(this.irArray), new Function2<Integer, IrExpression, IntermediateValue>() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.ArrayAccessAssignmentReceiver.assign.1
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ IntermediateValue invoke(Integer num, IrExpression irExpression) {
                    return invoke(num.intValue(), irExpression);
                }

                public final IntermediateValue invoke(int i, IrExpression irIndex) {
                    Intrinsics.checkNotNullParameter(irIndex, "irIndex");
                    return new OnceExpressionValue(irIndex);
                }
            }));
        }
        final IrBlockImpl irBlockImpl = new IrBlockImpl(this.startOffset, this.endOffset, irTypeTranslateType, this.origin);
        IntermediateValue intermediateValueCreateTemporaryVariableInBlock$default = RematerializableValueKt.createTemporaryVariableInBlock$default(this.callGenerator.getScope(), this.callGenerator.getContext(), this.irArray, irBlockImpl, "array", 0, 0, 48, null);
        final CompoundAssignmentInfo compoundAssignmentInfo = new CompoundAssignmentInfo();
        IrBlockImplKt.inlineStatement(irBlockImpl, withLValue.invoke(createLValue(type, intermediateValueCreateTemporaryVariableInBlock$default, new Function2<Integer, IrExpression, IntermediateValue>() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.ArrayAccessAssignmentReceiver.assign.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ IntermediateValue invoke(Integer num, IrExpression irExpression) {
                return invoke(num.intValue(), irExpression);
            }

            public final IntermediateValue invoke(int i, IrExpression irIndex) {
                Intrinsics.checkNotNullParameter(irIndex, "irIndex");
                IrVariable irVariableCreateTemporaryVariable = ArrayAccessAssignmentReceiver.this.callGenerator.getScope().createTemporaryVariable(irIndex, (108 & 2) != 0 ? null : "index" + i, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irIndex.getStartOffset() : 0, (108 & 64) != 0 ? irIndex.getEndOffset() : 0);
                compoundAssignmentInfo.getIndexVariables().add(irVariableCreateTemporaryVariable);
                irBlockImpl.getStatements().add(irVariableCreateTemporaryVariable);
                return new VariableLValue(ArrayAccessAssignmentReceiver.this.callGenerator.getContext(), irVariableCreateTemporaryVariable, null, 0, 0, 28, null);
            }
        })));
        postprocessSamConversionsInCompoundAssignment(irBlockImpl, compoundAssignmentInfo);
        return irBlockImpl;
    }

    private final void postprocessSamConversionsInCompoundAssignment(IrBlock irBlock, CompoundAssignmentInfo compoundAssignmentInfo) {
        SamConversionsCollector samConversionsCollector = new SamConversionsCollector(compoundAssignmentInfo);
        IrBlock irBlock2 = irBlock;
        IrVisitorsKt.acceptChildrenVoid(irBlock2, samConversionsCollector);
        if (samConversionsCollector.getSamConversionsPerVariable().isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        for (Map.Entry<IrVariable, List<IrTypeOperatorCall>> entry : samConversionsCollector.getSamConversionsPerVariable().entrySet()) {
            IrVariable key = entry.getKey();
            List<IrTypeOperatorCall> value = entry.getValue();
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) CollectionsKt.first((List) value);
            for (IrTypeOperatorCall irTypeOperatorCall2 : value) {
                if (irTypeOperatorCall2 != irTypeOperatorCall) {
                    KotlinType operandKotlinType = getOperandKotlinType(irTypeOperatorCall);
                    KotlinType operandKotlinType2 = getOperandKotlinType(irTypeOperatorCall2);
                    if (KotlinTypeChecker.DEFAULT.isSubtypeOf(operandKotlinType2, operandKotlinType)) {
                        irTypeOperatorCall = irTypeOperatorCall2;
                    } else if (!KotlinTypeChecker.DEFAULT.isSubtypeOf(operandKotlinType, operandKotlinType2)) {
                        throw new AssertionError("Unrelated types in SAM conversion for index variable: " + operandKotlinType + ", " + operandKotlinType2);
                    }
                }
            }
            IrExpression irExpressionCreateSamConvertedVarInitializer = createSamConvertedVarInitializer(key, irTypeOperatorCall);
            IrVariable irVariableCreateTemporaryVariable = this.callGenerator.getScope().createTemporaryVariable(irExpressionCreateSamConvertedVarInitializer, (108 & 2) != 0 ? null : "sam", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionCreateSamConvertedVarInitializer.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionCreateSamConvertedVarInitializer.getEndOffset() : 0);
            irBlock.getStatements().set(irBlock.getStatements().indexOf(key), irVariableCreateTemporaryVariable);
            map.put(key, irVariableCreateTemporaryVariable);
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irBlock2, new SamConversionsRewriter(map));
    }

    private final IrExpression createSamConvertedVarInitializer(IrVariable irIndexVar, IrTypeOperatorCall mostSpecificSamConversion) {
        IrExpression initializer = irIndexVar.getInitializer();
        Intrinsics.checkNotNull(initializer);
        int startOffset = initializer.getStartOffset();
        int endOffset = initializer.getEndOffset();
        IrExpression argument = mostSpecificSamConversion.getArgument();
        Intrinsics.checkNotNull(argument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall");
        IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) argument;
        return new IrTypeOperatorCallImpl(startOffset, endOffset, mostSpecificSamConversion.getType(), IrTypeOperator.SAM_CONVERSION, mostSpecificSamConversion.getTypeOperand(), new IrTypeOperatorCallImpl(startOffset, endOffset, irTypeOperatorCall.getType(), IrTypeOperator.IMPLICIT_CAST, irTypeOperatorCall.getTypeOperand(), initializer));
    }

    private final KotlinType getOperandKotlinType(IrTypeOperatorCall irTypeOperatorCall) {
        KotlinType originalKotlinType = IrTypeBaseKt.getOriginalKotlinType(irTypeOperatorCall.getTypeOperand());
        Intrinsics.checkNotNull(originalKotlinType);
        return originalKotlinType;
    }

    /* JADX INFO: compiled from: ArrayAccessAssignmentReceiver.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R9\u0010\u0005\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b`\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver$SamConversionsCollector;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "compoundAssignmentInfo", "Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver$CompoundAssignmentInfo;", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver$CompoundAssignmentInfo;)V", "samConversionsPerVariable", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "Lkotlin/collections/HashMap;", "getSamConversionsPerVariable", "()Ljava/util/HashMap;", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitTypeOperator", "expression", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SamConversionsCollector implements IrElementVisitorVoid {
        private final CompoundAssignmentInfo compoundAssignmentInfo;
        private final HashMap<IrVariable, List<IrTypeOperatorCall>> samConversionsPerVariable;

        public SamConversionsCollector(CompoundAssignmentInfo compoundAssignmentInfo) {
            Intrinsics.checkNotNullParameter(compoundAssignmentInfo, "compoundAssignmentInfo");
            this.compoundAssignmentInfo = compoundAssignmentInfo;
            this.samConversionsPerVariable = new HashMap<>();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
            visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
            IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock */
        public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
            visitBlock(irBlock, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlock(IrBlock irBlock) {
            IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlock(IrBlock irBlock, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody */
        public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
            visitBlockBody(irBlockBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlockBody(IrBlockBody irBlockBody) {
            IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody */
        public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
            visitBody(irBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBody(IrBody irBody) {
            IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBody(IrBody irBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch */
        public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
            visitBranch(irBranch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBranch(IrBranch irBranch) {
            IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBranch(IrBranch irBranch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak */
        public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
            visitBreak(irBreak, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreak(IrBreak irBreak) {
            IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreak(IrBreak irBreak, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue */
        public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
            visitBreakContinue(irBreakContinue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreakContinue(IrBreakContinue irBreakContinue) {
            IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall */
        public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
            visitCall(irCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCall(IrCall irCall) {
            IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCall(IrCall irCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCallableReference(IrCallableReference<?> irCallableReference) {
            IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch */
        public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
            visitCatch(irCatch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCatch(IrCatch irCatch) {
            IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCatch(IrCatch irCatch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass */
        public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
            visitClass(irClass, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        /* JADX INFO: renamed from: visitClass */
        public void mo6112visitClass(IrClass irClass) {
            IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClass(IrClass irClass, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference */
        public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
            visitClassReference(irClassReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClassReference(IrClassReference irClassReference) {
            IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClassReference(IrClassReference irClassReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite */
        public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
            visitComposite(irComposite, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitComposite(IrComposite irComposite) {
            IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitComposite(IrComposite irComposite, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
            visitConst((IrConst<?>) irConst, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConst(IrConst<?> irConst) {
            IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConst(IrConst<?> irConst, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray */
        public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
            visitConstantArray(irConstantArray, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantArray(IrConstantArray irConstantArray) {
            IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject */
        public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
            visitConstantObject(irConstantObject, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantObject(IrConstantObject irConstantObject) {
            IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive */
        public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
            visitConstantPrimitive(irConstantPrimitive, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
            IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue */
        public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
            visitConstantValue(irConstantValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantValue(IrConstantValue irConstantValue) {
            IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor */
        public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
            visitConstructor(irConstructor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructor(IrConstructor irConstructor) {
            IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructor(IrConstructor irConstructor, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall */
        public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
            visitConstructorCall(irConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructorCall(IrConstructorCall irConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression */
        public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
            visitContainerExpression(irContainerExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContainerExpression(IrContainerExpression irContainerExpression) {
            IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue */
        public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
            visitContinue(irContinue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContinue(IrContinue irContinue) {
            IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContinue(IrContinue irContinue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration */
        public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
            visitDeclaration(irDeclarationBase, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
            IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference */
        public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
            visitDeclarationReference(irDeclarationReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
            IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
        public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
            visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop */
        public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
            visitDoWhileLoop(irDoWhileLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
            IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
            visitDynamicExpression(irDynamicExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
            visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
            visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement */
        public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
            visitElement(irElement, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElement(IrElement irElement, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch */
        public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
            visitElseBranch(irElseBranch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElseBranch(IrElseBranch irElseBranch) {
            IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall */
        public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
            visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry */
        public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
            visitEnumEntry(irEnumEntry, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumEntry(IrEnumEntry irEnumEntry) {
            IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression */
        public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
            visitErrorCallExpression(irErrorCallExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
            IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration */
        public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
            visitErrorDeclaration(irErrorDeclaration, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
            IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression */
        public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
            visitErrorExpression(irErrorExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorExpression(IrErrorExpression irErrorExpression) {
            IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression */
        public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
            visitExpression(irExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpression(IrExpression irExpression) {
            IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpression(IrExpression irExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody */
        public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
            visitExpressionBody(irExpressionBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpressionBody(IrExpressionBody irExpressionBody) {
            IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment */
        public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
            visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
            IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField */
        public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
            visitField(irField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitField(IrField irField) {
            IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitField(IrField irField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess */
        public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
            visitFieldAccess(irFieldAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile */
        public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
            visitFile(irFile, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFile(IrFile irFile) {
            IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFile(IrFile irFile, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction */
        public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
            visitFunction(irFunction, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunction(IrFunction irFunction) {
            IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunction(IrFunction irFunction, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess */
        public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
            visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression */
        public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
            visitFunctionExpression(irFunctionExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference */
        public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
            visitFunctionReference(irFunctionReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionReference(IrFunctionReference irFunctionReference) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass */
        public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
            visitGetClass(irGetClass, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetClass(IrGetClass irGetClass) {
            IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetClass(IrGetClass irGetClass, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue */
        public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
            visitGetEnumValue(irGetEnumValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField */
        public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
            visitGetField(irGetField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetField(IrGetField irGetField) {
            IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetField(IrGetField irGetField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue */
        public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
            visitGetObjectValue(irGetObjectValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue */
        public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
            visitGetValue(irGetValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetValue(IrGetValue irGetValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetValue(IrGetValue irGetValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall */
        public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
            visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
            IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
        public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
            visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
        public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
            visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop */
        public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
            visitLoop(irLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLoop(IrLoop irLoop) {
            IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLoop(IrLoop irLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment */
        public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
            visitModuleFragment(irModuleFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitModuleFragment(IrModuleFragment irModuleFragment) {
            IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment */
        public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
            visitPackageFragment(irPackageFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPackageFragment(IrPackageFragment irPackageFragment) {
            IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty */
        public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
            visitProperty(irProperty, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitProperty(IrProperty irProperty) {
            IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitProperty(IrProperty irProperty, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference */
        public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
            visitPropertyReference(irPropertyReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPropertyReference(IrPropertyReference irPropertyReference) {
            IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference */
        public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
            visitRawFunctionReference(irRawFunctionReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
            IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn */
        public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
            visitReturn(irReturn, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitReturn(IrReturn irReturn) {
            IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitReturn(IrReturn irReturn, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript */
        public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
            visitScript(irScript, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitScript(IrScript irScript) {
            IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitScript(IrScript irScript, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField */
        public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
            visitSetField(irSetField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetField(IrSetField irSetField) {
            IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetField(IrSetField irSetField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue */
        public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
            visitSetValue(irSetValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetValue(IrSetValue irSetValue) {
            IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetValue(IrSetValue irSetValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction */
        public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
            visitSimpleFunction(irSimpleFunction, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
            IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference */
        public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
            visitSingletonReference(irGetSingletonValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
            IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement */
        public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
            visitSpreadElement(irSpreadElement, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSpreadElement(IrSpreadElement irSpreadElement) {
            IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation */
        public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
            visitStringConcatenation(irStringConcatenation, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
            IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression */
        public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
            visitSuspendableExpression(irSuspendableExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
            IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint */
        public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
            visitSuspensionPoint(irSuspensionPoint, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
            IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody */
        public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
            visitSyntheticBody(irSyntheticBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
            IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow */
        public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
            visitThrow(irThrow, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitThrow(IrThrow irThrow) {
            IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitThrow(IrThrow irThrow, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry */
        public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
            visitTry(irTry, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTry(IrTry irTry) {
            IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTry(IrTry irTry, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias */
        public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
            visitTypeAlias(irTypeAlias, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeAlias(IrTypeAlias irTypeAlias) {
            IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator */
        public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
            visitTypeOperator(irTypeOperatorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter */
        public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
            visitTypeParameter(irTypeParameter, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeParameter(IrTypeParameter irTypeParameter) {
            IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess */
        public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
            visitValueAccess(irValueAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter */
        public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
            visitValueParameter(irValueParameter, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueParameter(IrValueParameter irValueParameter) {
            IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg */
        public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
            visitVararg(irVararg, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVararg(IrVararg irVararg) {
            IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVararg(IrVararg irVararg, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable */
        public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
            visitVariable(irVariable, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVariable(IrVariable irVariable) {
            IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVariable(IrVariable irVariable, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen */
        public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
            visitWhen(irWhen, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhen(IrWhen irWhen) {
            IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhen(IrWhen irWhen, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop */
        public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
            visitWhileLoop(irWhileLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhileLoop(IrWhileLoop irWhileLoop) {
            IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
        }

        public final HashMap<IrVariable, List<IrTypeOperatorCall>> getSamConversionsPerVariable() {
            return this.samConversionsPerVariable;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        /* JADX INFO: renamed from: visitElement */
        public void mo6335visitElement(IrElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            IrVisitorsKt.acceptChildrenVoid(element, this);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeOperator(IrTypeOperatorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrVisitorsKt.acceptChildrenVoid(expression, this);
            IrGetValue samConvertedGetValue$ir_psi2ir = ArrayAccessAssignmentReceiver.INSTANCE.getSamConvertedGetValue$ir_psi2ir(expression);
            if (samConvertedGetValue$ir_psi2ir != null) {
                IrValueDeclaration owner = samConvertedGetValue$ir_psi2ir.getSymbol().getOwner();
                if ((owner instanceof IrVariable) && this.compoundAssignmentInfo.getIndexVariables().contains(owner)) {
                    AbstractMap abstractMap = this.samConversionsPerVariable;
                    Object obj = abstractMap.get(owner);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        abstractMap.put(owner, obj);
                    }
                    ((List) obj).add(expression);
                }
            }
        }
    }

    /* JADX INFO: compiled from: ArrayAccessAssignmentReceiver.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver$SamConversionsRewriter;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "replacementVars", "", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Ljava/util/Map;)V", "visitElement", "Lorg/jetbrains/kotlin/ir/IrElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SamConversionsRewriter extends IrElementTransformerVoid {
        private final Map<IrVariable, IrVariable> replacementVars;

        /* JADX WARN: Multi-variable type inference failed */
        public SamConversionsRewriter(Map<IrVariable, ? extends IrVariable> replacementVars) {
            Intrinsics.checkNotNullParameter(replacementVars, "replacementVars");
            this.replacementVars = replacementVars;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrElement visitElement(IrElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            transformChildrenVoid(element);
            return element;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrGetValue samConvertedGetValue$ir_psi2ir = ArrayAccessAssignmentReceiver.INSTANCE.getSamConvertedGetValue$ir_psi2ir(expression);
            if (samConvertedGetValue$ir_psi2ir != null) {
                IrVariable irVariable = this.replacementVars.get(samConvertedGetValue$ir_psi2ir.getSymbol().getOwner());
                if (irVariable != null) {
                    return new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irVariable.getSymbol(), null);
                }
            }
            transformChildrenVoid(expression);
            return expression;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitGetValue(IrGetValue expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrValueSymbol symbol = expression.getSymbol();
            if (this.replacementVars.containsKey(symbol.getOwner())) {
                throw new AssertionError("SAM-converted index variable " + symbol.getDescriptor() + " is present in get/set calls in non-converted");
            }
            return expression;
        }
    }

    private final LValueWithGetterAndSetterCalls createLValue(KotlinType kotlinType, final IntermediateValue irArrayValue, Function2<? super Integer, ? super IrExpression, ? extends IntermediateValue> createIndexValue) {
        final HashMap map = new HashMap();
        Iterator<IrExpression> it = this.irIndexExpressions.iterator();
        int i = 0;
        while (it.hasNext()) {
            map.put(this.ktIndexExpressions.get(i), createIndexValue.invoke(Integer.valueOf(i), it.next()));
            i++;
        }
        return new LValueWithGetterAndSetterCalls(this.callGenerator, this.descriptor, new Function0<CallBuilder>() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.ArrayAccessAssignmentReceiver.createLValue.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final CallBuilder invoke() {
                CallBuilder callBuilder = (CallBuilder) ArrayAccessAssignmentReceiver.this.indexedGetCall.invoke();
                if (callBuilder == null) {
                    return null;
                }
                ArrayAccessAssignmentReceiver arrayAccessAssignmentReceiver = ArrayAccessAssignmentReceiver.this;
                IntermediateValue intermediateValue = irArrayValue;
                ResolvedCall resolvedCall = arrayAccessAssignmentReceiver.indexedGetResolvedCall;
                Intrinsics.checkNotNull(resolvedCall);
                return arrayAccessAssignmentReceiver.fillArguments(callBuilder, intermediateValue, resolvedCall, map, null);
            }
        }, new Function1<IrExpression, CallBuilder>() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.ArrayAccessAssignmentReceiver.createLValue.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CallBuilder invoke(IrExpression it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                CallBuilder callBuilder = (CallBuilder) ArrayAccessAssignmentReceiver.this.indexedSetCall.invoke();
                if (callBuilder == null) {
                    return null;
                }
                ArrayAccessAssignmentReceiver arrayAccessAssignmentReceiver = ArrayAccessAssignmentReceiver.this;
                IntermediateValue intermediateValue = irArrayValue;
                ResolvedCall resolvedCall = arrayAccessAssignmentReceiver.indexedSetResolvedCall;
                Intrinsics.checkNotNull(resolvedCall);
                return arrayAccessAssignmentReceiver.fillArguments(callBuilder, intermediateValue, resolvedCall, map, it2);
            }
        }, this.callGenerator.translateType(kotlinType), this.startOffset, this.endOffset, this.origin);
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver
    public IrExpression assign(IrExpression value) {
        Intrinsics.checkNotNullParameter(value, "value");
        CallBuilder callBuilderInvoke = this.indexedSetCall.invoke();
        if (callBuilderInvoke == null) {
            throw new AssertionError("Array access without indexed-get call");
        }
        List<KtExpression> list = this.ktIndexExpressions;
        List<IrExpression> list2 = this.irIndexExpressions;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(new OnceExpressionValue((IrExpression) it.next()));
        }
        Map<KtExpression, ? extends IntermediateValue> map = MapsKt.toMap(CollectionsKt.zip(list, arrayList));
        OnceExpressionValue onceExpressionValue = new OnceExpressionValue(this.irArray);
        ResolvedCall<FunctionDescriptor> resolvedCall = this.indexedSetResolvedCall;
        Intrinsics.checkNotNull(resolvedCall);
        fillArguments(callBuilderInvoke, onceExpressionValue, resolvedCall, map, value);
        return this.callGenerator.generateCall(this.startOffset, this.endOffset, callBuilderInvoke, IrStatementOrigin.EQ.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallBuilder fillArguments(CallBuilder callBuilder, IntermediateValue intermediateValue, ResolvedCall<FunctionDescriptor> resolvedCall, final Map<KtExpression, ? extends IntermediateValue> map, IrExpression irExpression) {
        CallBuilderKt.setExplicitReceiverValue(callBuilder, intermediateValue);
        ArgumentsGenerationUtilsKt.pregenerateValueArgumentsUsing(this.callGenerator.getStatementGenerator(), callBuilder, resolvedCall, new Function1<KtExpression, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.ArrayAccessAssignmentReceiver$fillArguments$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(KtExpression ktExpression) {
                Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
                IntermediateValue intermediateValue2 = map.get(ktExpression);
                if (intermediateValue2 != null) {
                    return intermediateValue2.load();
                }
                return null;
            }
        });
        if (irExpression != null) {
            CallBuilderKt.setLastArgument(callBuilder, irExpression);
        }
        ArgumentsGenerationUtilsKt.generateSamConversionForValueArgumentsIfRequired(this.callGenerator.getStatementGenerator(), callBuilder, resolvedCall);
        return callBuilder;
    }

    /* JADX INFO: compiled from: ArrayAccessAssignmentReceiver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u0005H\u0000¢\u0006\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver$Companion;", "", "()V", "getSamConvertedGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "getSamConvertedGetValue$ir_psi2ir", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IrGetValue getSamConvertedGetValue$ir_psi2ir(IrTypeOperatorCall irTypeOperatorCall) {
            Intrinsics.checkNotNullParameter(irTypeOperatorCall, "<this>");
            if (irTypeOperatorCall.getOperator() != IrTypeOperator.SAM_CONVERSION) {
                return null;
            }
            IrExpression argument = irTypeOperatorCall.getArgument();
            if (!(argument instanceof IrTypeOperatorCall)) {
                return null;
            }
            IrTypeOperatorCall irTypeOperatorCall2 = (IrTypeOperatorCall) argument;
            if (irTypeOperatorCall2.getOperator() != IrTypeOperator.IMPLICIT_CAST) {
                return null;
            }
            IrExpression argument2 = irTypeOperatorCall2.getArgument();
            if (!(argument2 instanceof IrGetValue)) {
                return null;
            }
            IrGetValue irGetValue = (IrGetValue) argument2;
            if (irGetValue.getSymbol().getOwner() instanceof IrVariable) {
                return irGetValue;
            }
            return null;
        }
    }
}
