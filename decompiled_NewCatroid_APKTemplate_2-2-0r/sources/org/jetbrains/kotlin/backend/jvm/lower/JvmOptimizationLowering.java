package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.AbstractVariableRemapper;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderProcessorKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocatorKt;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineScopeResolver;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
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
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
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
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
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
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImplKt;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImplKt;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: JvmOptimizationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00142\u00020\u0001:\u0002\u0014\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmOptimizationLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "isObjectEquals", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "getOperandsIfCallToEQEQOrEquals", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Companion", "Transformer", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmOptimizationLowering implements FileLoweringPass {
    private static final Companion Companion = new Companion(null);
    private final JvmBackendContext context;

    /* JADX INFO: compiled from: JvmOptimizationLowering.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmOptimizationLowering$Companion;", "", "()V", "isNegation", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isNegation(IrExpression expression) {
            IrValueParameter dispatchReceiverParameter;
            if (expression instanceof IrCall) {
                IrSimpleFunction owner = ((IrCall) expression).getSymbol().getOwner();
                if (Intrinsics.areEqual(owner.getName(), OperatorNameConventions.NOT) && owner.getExtensionReceiverParameter() == null && owner.getValueParameters().isEmpty() && (dispatchReceiverParameter = owner.getDispatchReceiverParameter()) != null && IrTypePredicatesKt.isBoolean(dispatchReceiverParameter.getType())) {
                    return true;
                }
            }
            return false;
        }
    }

    public JvmOptimizationLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    private final boolean isObjectEquals(IrFunction irFunction) {
        return Intrinsics.areEqual(irFunction.getName().asString(), Namer.EQUALS_METHOD_NAME) && irFunction.getValueParameters().size() == 1 && IrTypePredicatesKt.isNullableAny(irFunction.getValueParameters().get(0).getType()) && irFunction.getExtensionReceiverParameter() == null && irFunction.getDispatchReceiverParameter() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<IrExpression, IrExpression> getOperandsIfCallToEQEQOrEquals(IrCall call) {
        if (Intrinsics.areEqual(call.getSymbol(), this.context.getIrBuiltIns().getEqeqSymbol())) {
            IrExpression valueArgument = call.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument);
            IrExpression valueArgument2 = call.getValueArgument(1);
            Intrinsics.checkNotNull(valueArgument2);
            return TuplesKt.to(valueArgument, valueArgument2);
        }
        if (!isObjectEquals(call.getSymbol().getOwner())) {
            return null;
        }
        IrExpression dispatchReceiver = call.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        IrExpression valueArgument3 = call.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument3);
        return TuplesKt.to(dispatchReceiver, valueArgument3);
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.transformChildren(new Transformer(this, irFile.getFileEntry(), IrInlineReferenceLocatorKt.findInlineCallSites(irFile, this.context)), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JvmOptimizationLowering.kt */
    @Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001e\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000fH\u0002J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J\u001a\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0002J\u0018\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000fH\u0002J\u0016\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00110%H\u0002J\u0010\u0010&\u001a\u00020#2\u0006\u0010'\u001a\u00020\u000eH\u0002J$\u0010(\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u001c\u001a\u00020 2\b\u0010)\u001a\u0004\u0018\u00010\u00132\u0006\u0010*\u001a\u00020\u0017H\u0002J\u0012\u0010+\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u001c\u001a\u00020 H\u0002J\u001a\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u00100\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u00101\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u000e2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u00102\u001a\u00020\u00112\u0006\u00103\u001a\u0002042\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u00105\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u0002062\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u00107\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020 2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u00108\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u0002092\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u0016J\f\u0010:\u001a\u00020\u0017*\u00020\u0011H\u0002J\u000e\u0010;\u001a\u0004\u0018\u00010\u001d*\u00020\u000eH\u0002R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmOptimizationLowering$Transformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "fileEntry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "inlineScopeResolver", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;", "(Lorg/jetbrains/kotlin/backend/jvm/lower/JvmOptimizationLowering;Lorg/jetbrains/kotlin/ir/IrFileEntry;Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;)V", "dontTouchTemporaryVals", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lkotlin/collections/HashSet;", "findLoopVariablePosition", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "", "statement", "Lorg/jetbrains/kotlin/ir/IrStatement;", "getInlineableValueForTemporaryVal", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getLineNumberForOffset", TypedValues.CycleType.S_WAVE_OFFSET, "hasSameLineNumber", "", "e1", "Lorg/jetbrains/kotlin/ir/IrElement;", "e2", "optimizePropertyAccess", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "data", "prefixIncr", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "delta", "removeUnnecessaryTemporaryVariables", "", "statements", "", "reuseLoopVariableAsInductionVariableIfPossible", "irForLoopBlock", "rewriteCompoundAssignmentAsPrefixIncrDecr", "value", "isMinus", "rewritePrefixIncrDecr", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitCall", "visitContainerExpression", "visitDeclaration", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitSetValue", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "isLoopVariable", "rewritePostfixIncrDecr", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class Transformer implements IrElementTransformer<IrDeclaration> {
        private final HashSet<IrVariable> dontTouchTemporaryVals;
        private final IrFileEntry fileEntry;
        private final IrInlineScopeResolver inlineScopeResolver;
        final /* synthetic */ JvmOptimizationLowering this$0;

        public Transformer(JvmOptimizationLowering jvmOptimizationLowering, IrFileEntry fileEntry, IrInlineScopeResolver inlineScopeResolver) {
            Intrinsics.checkNotNullParameter(fileEntry, "fileEntry");
            Intrinsics.checkNotNullParameter(inlineScopeResolver, "inlineScopeResolver");
            this.this$0 = jvmOptimizationLowering;
            this.fileEntry = fileEntry;
            this.inlineScopeResolver = inlineScopeResolver;
            this.dontTouchTemporaryVals = new HashSet<>();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
        public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
        public IrExpression visitBlock2(IrBlock irBlock, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitBody(this, irBody, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
        public IrBranch visitBranch2(IrBranch irBranch, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
        public IrExpression visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclaration) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclaration) obj);
        }

        public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
        public IrCatch visitCatch2(IrCatch irCatch, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
        public IrStatement visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitClass(this, irClass, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitClassReference2(IrClassReference irClassReference, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
        public IrExpression visitComposite2(IrComposite irComposite, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (IrDeclaration) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (IrDeclaration) obj);
        }

        public IrExpression visitConst(IrConst<?> irConst, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitConst(this, irConst, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
        public IrStatement visitConstructor2(IrConstructor irConstructor, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
        public IrElement visitElement2(IrElement irElement, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitElement(this, irElement, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
        public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
        public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
        public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
        public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
        public IrStatement visitField2(IrField irField, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitField(this, irField, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
        public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
        public IrFile visitFile2(IrFile irFile, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitFile(this, irFile, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
        public IrStatement visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetClass2(IrGetClass irGetClass, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetField2(IrGetField irGetField, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
        public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
        public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclaration) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclaration) obj);
        }

        public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
        public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
        public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
        public IrStatement visitProperty2(IrProperty irProperty, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
        public IrExpression visitReturn2(IrReturn irReturn, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
        public IrStatement visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitScript(this, irScript, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSetField2(IrSetField irSetField, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
        public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
        public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
        public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
        public IrExpression visitThrow2(IrThrow irThrow, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
        public IrExpression visitTry2(IrTry irTry, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitTry(this, irTry, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
        public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
        public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
        public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
        public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
        public IrStatement visitValueParameter2(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
        public IrExpression visitVararg2(IrVararg irVararg, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
        public IrStatement visitVariable2(IrVariable irVariable, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclaration irDeclaration) {
            return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
        public IrStatement visitDeclaration2(IrDeclarationBase declaration, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return IrElementTransformer.DefaultImpls.visitDeclaration(this, declaration, declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
        public IrExpression visitCall2(IrCall expression, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.transformChildren(this, data);
            if (!Intrinsics.areEqual(expression.getSymbol().getOwner().getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE)) {
                IrCall irCall = expression;
                if (JvmOptimizationLowering.Companion.isNegation(irCall)) {
                    Companion companion = JvmOptimizationLowering.Companion;
                    IrExpression dispatchReceiver = expression.getDispatchReceiver();
                    Intrinsics.checkNotNull(dispatchReceiver);
                    if (companion.isNegation(dispatchReceiver)) {
                        IrExpression dispatchReceiver2 = expression.getDispatchReceiver();
                        Intrinsics.checkNotNull(dispatchReceiver2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCall");
                        IrExpression dispatchReceiver3 = ((IrCall) dispatchReceiver2).getDispatchReceiver();
                        Intrinsics.checkNotNull(dispatchReceiver3);
                        return dispatchReceiver3;
                    }
                }
                Pair operandsIfCallToEQEQOrEquals = this.this$0.getOperandsIfCallToEQEQOrEquals(expression);
                if (operandsIfCallToEQEQOrEquals != null) {
                    JvmOptimizationLowering jvmOptimizationLowering = this.this$0;
                    IrExpression irExpression = (IrExpression) operandsIfCallToEQEQOrEquals.component1();
                    IrExpression irExpression2 = (IrExpression) operandsIfCallToEQEQOrEquals.component2();
                    if (IrUtilsKt.isNullConst(irExpression) && IrUtilsKt.isNullConst(irExpression2)) {
                        return IrConstImpl.INSTANCE.constTrue(expression.getStartOffset(), expression.getEndOffset(), jvmOptimizationLowering.getContext().getIrBuiltIns().getBooleanType());
                    }
                    if ((IrUtilsKt.isNullConst(irExpression) && (irExpression2 instanceof IrConst)) || (IrUtilsKt.isNullConst(irExpression2) && (irExpression instanceof IrConst))) {
                        return IrConstImpl.INSTANCE.constFalse(expression.getStartOffset(), expression.getEndOffset(), jvmOptimizationLowering.getContext().getIrBuiltIns().getBooleanType());
                    }
                }
                return irCall;
            }
            return optimizePropertyAccess(expression, data);
        }

        private final IrExpression optimizePropertyAccess(IrCall expression, IrDeclaration data) {
            IrProperty owner;
            IrField backingField;
            IrDeclarationContainer irDeclarationContainerFindContainer;
            IrBlockBuilder irBlockBuilder;
            IrSimpleFunction owner2 = expression.getSymbol().getOwner();
            IrSimpleFunction irSimpleFunction = owner2 instanceof IrSimpleFunction ? owner2 : null;
            if (irSimpleFunction == null) {
                return expression;
            }
            if (irSimpleFunction.getModality() != Modality.FINAL || irSimpleFunction.getIsExternal()) {
                return expression;
            }
            IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
                return expression;
            }
            if (!owner.getIsLateinit() && (backingField = owner.getBackingField()) != null) {
                if (data == null || (irDeclarationContainerFindContainer = this.inlineScopeResolver.findContainer(data)) == null) {
                    return expression;
                }
                if (!Intrinsics.areEqual(irDeclarationContainerFindContainer, irSimpleFunction.getParent()) || !Intrinsics.areEqual(irDeclarationContainerFindContainer, backingField.getParent())) {
                    return expression;
                }
                IrExpression dispatchReceiver = expression.getDispatchReceiver();
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.this$0.getContext(), expression.getSymbol(), expression.getStartOffset(), expression.getEndOffset());
                JvmOptimizationLowering jvmOptimizationLowering = this.this$0;
                IrCall irCall = expression;
                IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), irCall.getStartOffset(), irCall.getEndOffset(), null, irCall.getType(), false, 64, null);
                if (backingField.getIsStatic() && dispatchReceiver != null && !(dispatchReceiver instanceof IrGetValue)) {
                    irBlockBuilder2.unaryPlus(IrUtilsKt.coerceToUnit(dispatchReceiver, irBlockBuilder2.getContext().getIrBuiltIns(), jvmOptimizationLowering.getContext().getTypeSystem()));
                }
                if (!irSimpleFunction.getValueParameters().isEmpty()) {
                    IrBlockBuilder irBlockBuilder3 = irBlockBuilder2;
                    if (backingField.getIsStatic()) {
                        dispatchReceiver = null;
                    }
                    IrExpression valueArgument = expression.getValueArgument(expression.getValueArgumentsCount() - 1);
                    Intrinsics.checkNotNull(valueArgument);
                    irBlockBuilder = irBlockBuilder2;
                    irBlockBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBuilder3, dispatchReceiver, backingField, valueArgument, null, 8, null));
                } else {
                    irBlockBuilder = irBlockBuilder2;
                    IrBlockBuilder irBlockBuilder4 = irBlockBuilder;
                    if (backingField.getIsStatic()) {
                        dispatchReceiver = null;
                    }
                    irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGetField$default(irBlockBuilder4, dispatchReceiver, backingField, null, 4, null));
                }
                return irBlockBuilder.getIrBlockBody();
            }
            return expression;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
        public IrExpression visitWhen2(IrWhen expression, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            final boolean z = expression.getOrigin() == null;
            expression.transformChildren(this, data);
            List<IrBranch> branches = expression.getBranches();
            final Function1<IrBranch, Boolean> function1 = new Function1<IrBranch, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmOptimizationLowering$Transformer$visitWhen$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrBranch it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(IrUtilsKt.isFalseConst(it.getCondition()) && z);
                }
            };
            branches.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmOptimizationLowering$Transformer$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return JvmOptimizationLowering.Transformer.visitWhen$lambda$4(function1, obj);
                }
            });
            if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.ANDAND.INSTANCE)) {
                if (IrTypePredicatesKt.isBoolean(expression.getType()) && expression.getBranches().size() == 2 && IrUtilsKt.isTrueConst(expression.getBranches().get(1).getCondition())) {
                    IrUtilsKt.isFalseConst(expression.getBranches().get(1).getResult());
                }
                IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
                int startOffset = expression.getStartOffset();
                int endOffset = expression.getEndOffset();
                IrType booleanType = this.this$0.getContext().getIrBuiltIns().getBooleanType();
                IrSimpleFunctionSymbol andandSymbol = this.this$0.getContext().getIrBuiltIns().getAndandSymbol();
                IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(startOffset, endOffset, booleanType, andandSymbol, (240 & 16) != 0 ? andandSymbol.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? andandSymbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
                irCallImplFromSymbolOwner.putValueArgument(0, expression.getBranches().get(0).getCondition());
                irCallImplFromSymbolOwner.putValueArgument(1, expression.getBranches().get(0).getResult());
                return irCallImplFromSymbolOwner;
            }
            if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.OROR.INSTANCE)) {
                if (IrTypePredicatesKt.isBoolean(expression.getType()) && expression.getBranches().size() == 2 && IrUtilsKt.isTrueConst(expression.getBranches().get(0).getResult())) {
                    IrUtilsKt.isTrueConst(expression.getBranches().get(1).getCondition());
                }
                IrCallImpl.Companion companion2 = IrCallImpl.INSTANCE;
                int startOffset2 = expression.getStartOffset();
                int endOffset2 = expression.getEndOffset();
                IrType booleanType2 = this.this$0.getContext().getIrBuiltIns().getBooleanType();
                IrSimpleFunctionSymbol ororSymbol = this.this$0.getContext().getIrBuiltIns().getOrorSymbol();
                IrCallImpl irCallImplFromSymbolOwner2 = companion2.fromSymbolOwner(startOffset2, endOffset2, booleanType2, ororSymbol, (240 & 16) != 0 ? ororSymbol.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? ororSymbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
                irCallImplFromSymbolOwner2.putValueArgument(0, expression.getBranches().get(0).getCondition());
                irCallImplFromSymbolOwner2.putValueArgument(1, expression.getBranches().get(1).getResult());
                return irCallImplFromSymbolOwner2;
            }
            if (expression.getBranches().size() == 0) {
                return new IrBlockImpl(expression.getStartOffset(), expression.getEndOffset(), this.this$0.getContext().getIrBuiltIns().getUnitType(), null, 8, null);
            }
            IrBranch irBranch = (IrBranch) CollectionsKt.first((List) expression.getBranches());
            if (IrUtilsKt.isTrueConst(irBranch.getCondition()) && z) {
                return irBranch.getResult();
            }
            return expression;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean visitWhen$lambda$4(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return ((Boolean) tmp0.invoke(obj)).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrExpression getInlineableValueForTemporaryVal(IrStatement statement) {
            IrExpression inlineableValueForTemporaryVal;
            IrVariable irVariable = statement instanceof IrVariable ? (IrVariable) statement : null;
            if (irVariable == null || !Intrinsics.areEqual(irVariable.getOrigin(), IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE) || irVariable.getIsVar() || this.dontTouchTemporaryVals.contains(irVariable)) {
                return null;
            }
            IrExpression initializer = irVariable.getInitializer();
            if (initializer instanceof IrConst) {
                return initializer;
            }
            if (!(initializer instanceof IrGetValue)) {
                return null;
            }
            IrValueDeclaration owner = ((IrGetValue) initializer).getSymbol().getOwner();
            if (owner instanceof IrVariable) {
                if (((IrVariable) owner).getIsVar()) {
                    return null;
                }
                return (!Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE) || (inlineableValueForTemporaryVal = getInlineableValueForTemporaryVal(owner)) == null) ? initializer : inlineableValueForTemporaryVal;
            }
            if (!(owner instanceof IrValueParameter) || owner.getIsAssignable()) {
                return null;
            }
            return initializer;
        }

        private final void removeUnnecessaryTemporaryVariables(List<IrStatement> statements) {
            final Function1<IrStatement, Boolean> function1 = new Function1<IrStatement, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmOptimizationLowering$Transformer$removeUnnecessaryTemporaryVariables$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrStatement it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(this.this$0.getInlineableValueForTemporaryVal(it) != null);
                }
            };
            statements.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmOptimizationLowering$Transformer$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return JvmOptimizationLowering.Transformer.removeUnnecessaryTemporaryVariables$lambda$9(function1, obj);
                }
            });
            if (statements.size() == 2) {
                IrStatement irStatement = statements.get(0);
                IrStatement irStatement2 = statements.get(1);
                if (irStatement instanceof IrVariable) {
                    IrVariable irVariable = (IrVariable) irStatement;
                    if (Intrinsics.areEqual(irVariable.getOrigin(), IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE) && (irStatement2 instanceof IrGetValue) && Intrinsics.areEqual(irVariable.getSymbol(), ((IrGetValue) irStatement2).getSymbol())) {
                        statements.clear();
                        IrExpression initializer = irVariable.getInitializer();
                        if (initializer != null) {
                            statements.add(initializer);
                        }
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean removeUnnecessaryTemporaryVariables$lambda$9(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return ((Boolean) tmp0.invoke(obj)).booleanValue();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitBlockBody2(IrBlockBody body, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(body, "body");
            body.transformChildren(this, data);
            removeUnnecessaryTemporaryVariables(body.getStatements());
            return body;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitContainerExpression2(IrContainerExpression expression, IrDeclaration data) {
            IrCall irCallRewritePostfixIncrDecr;
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.WHEN.INSTANCE)) {
                IrStatement irStatement = (IrStatement) CollectionsKt.firstOrNull((List) expression.getStatements());
                if ((irStatement instanceof IrVariable) && (((IrVariable) irStatement).getInitializer() instanceof IrGetValue)) {
                    this.dontTouchTemporaryVals.add((IrVariable) irStatement);
                }
            }
            if ((Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.POSTFIX_DECR.INSTANCE) || Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.POSTFIX_INCR.INSTANCE)) && (irCallRewritePostfixIncrDecr = rewritePostfixIncrDecr(expression)) != null) {
                return irCallRewritePostfixIncrDecr;
            }
            if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.FOR_LOOP.INSTANCE)) {
                reuseLoopVariableAsInductionVariableIfPossible(expression);
            }
            expression.transformChildren(this, data);
            removeUnnecessaryTemporaryVariables(expression.getStatements());
            return expression;
        }

        private final void reuseLoopVariableAsInductionVariableIfPossible(IrContainerExpression irForLoopBlock) {
            Pair<IrContainerExpression, Integer> pairFindLoopVariablePosition;
            IrExpression initializer;
            if (irForLoopBlock.getStatements().size() != 2) {
                return;
            }
            int i = 0;
            IrStatement irStatement = irForLoopBlock.getStatements().get(0);
            IrComposite irComposite = irStatement instanceof IrComposite ? (IrComposite) irStatement : null;
            if (irComposite == null) {
                return;
            }
            List<IrStatement> statements = irComposite.getStatements();
            JvmOptimizationLowering jvmOptimizationLowering = this.this$0;
            Iterator<IrStatement> it = statements.iterator();
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                } else if (HeaderProcessorKt.isInductionVariable(it.next(), jvmOptimizationLowering.getContext())) {
                    break;
                } else {
                    i++;
                }
            }
            if (i < 0) {
                return;
            }
            IrStatement irStatement2 = irComposite.getStatements().get(i);
            final IrVariable irVariable = irStatement2 instanceof IrVariable ? (IrVariable) irStatement2 : null;
            if (irVariable == null || (pairFindLoopVariablePosition = findLoopVariablePosition(irForLoopBlock.getStatements().get(1))) == null) {
                return;
            }
            IrContainerExpression irContainerExpressionComponent1 = pairFindLoopVariablePosition.component1();
            int iIntValue = pairFindLoopVariablePosition.component2().intValue();
            IrStatement irStatement3 = irContainerExpressionComponent1.getStatements().get(iIntValue);
            final IrVariable irVariable2 = irStatement3 instanceof IrVariable ? (IrVariable) irStatement3 : null;
            if (irVariable2 != null && (initializer = irVariable2.getInitializer()) != null && (initializer instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) initializer).getSymbol(), irVariable.getSymbol())) {
                IrType type = irVariable.getType();
                IrType type2 = irVariable2.getType();
                if (!IrTypeUtilsKt.isNullable(type2) && Intrinsics.areEqual(IrTypesKt.getClassifierOrNull(type2), IrTypesKt.getClassifierOrNull(type))) {
                    final IrVariableImpl irVariableImpl = new IrVariableImpl(irVariable2.getStartOffset(), irVariable2.getEndOffset(), irVariable2.getOrigin(), new IrVariableSymbolImpl(null, 1, null), irVariable2.getName(), type2, true, false, false);
                    irVariableImpl.setInitializer(irVariable.getInitializer());
                    irVariableImpl.setParent(irVariable.getParent());
                    irComposite.getStatements().set(i, irVariableImpl);
                    irContainerExpressionComponent1.getStatements().remove(iIntValue);
                    irForLoopBlock.getStatements().get(1).transformChildren(new AbstractVariableRemapper() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmOptimizationLowering$Transformer$reuseLoopVariableAsInductionVariableIfPossible$remapper$1
                        @Override // org.jetbrains.kotlin.backend.common.lower.AbstractVariableRemapper
                        protected IrValueDeclaration remapVariable(IrValueDeclaration value) {
                            Intrinsics.checkNotNullParameter(value, "value");
                            if (Intrinsics.areEqual(value, irVariable) || Intrinsics.areEqual(value, irVariable2)) {
                                return irVariableImpl;
                            }
                            return null;
                        }
                    }, null);
                }
            }
        }

        /* JADX WARN: Code duplicated, block: B:36:0x006c A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:37:0x006d  */
        private final Pair<IrContainerExpression, Integer> findLoopVariablePosition(IrStatement statement) {
            boolean z = statement instanceof IrDoWhileLoop;
            int i = 0;
            if (z) {
                IrDoWhileLoop irDoWhileLoop = z ? (IrDoWhileLoop) statement : null;
                if (irDoWhileLoop == null || !Intrinsics.areEqual(irDoWhileLoop.getOrigin(), JvmLoweredStatementOrigin.DO_WHILE_COUNTER_LOOP.INSTANCE)) {
                    return null;
                }
                IrExpression body = irDoWhileLoop.getBody();
                IrComposite irComposite = body instanceof IrComposite ? (IrComposite) body : null;
                if (irComposite == null || !Intrinsics.areEqual(irComposite.getOrigin(), IrStatementOrigin.FOR_LOOP_INNER_WHILE.INSTANCE)) {
                    return null;
                }
                IrStatement irStatement = irComposite.getStatements().get(0);
                IrComposite irComposite2 = irStatement instanceof IrComposite ? (IrComposite) irStatement : null;
                if (irComposite2 == null) {
                    return null;
                }
                Iterator<IrStatement> it = irComposite2.getStatements().iterator();
                while (it.hasNext()) {
                    if (isLoopVariable(it.next())) {
                        if (i < 0) {
                            return null;
                        }
                        return TuplesKt.to(irComposite2, Integer.valueOf(i));
                    }
                    i++;
                }
                i = -1;
                if (i < 0) {
                    return null;
                }
                return TuplesKt.to(irComposite2, Integer.valueOf(i));
            }
            if (!(statement instanceof IrWhen)) {
                return null;
            }
            IrExpression result = ((IrWhen) statement).getBranches().get(0).getResult();
            IrDoWhileLoop irDoWhileLoop2 = result instanceof IrDoWhileLoop ? (IrDoWhileLoop) result : null;
            if (irDoWhileLoop2 == null) {
                return null;
            }
            return findLoopVariablePosition(irDoWhileLoop2);
        }

        private final boolean isLoopVariable(IrStatement irStatement) {
            return (irStatement instanceof IrVariable) && Intrinsics.areEqual(((IrVariable) irStatement).getOrigin(), IrDeclarationOrigin.FOR_LOOP_VARIABLE.INSTANCE);
        }

        private final IrCall rewritePostfixIncrDecr(IrContainerExpression irContainerExpression) {
            IrConstImpl<Integer> irConstImplM7211int;
            if (!Intrinsics.areEqual(irContainerExpression.getOrigin(), IrStatementOrigin.POSTFIX_INCR.INSTANCE) && !Intrinsics.areEqual(irContainerExpression.getOrigin(), IrStatementOrigin.POSTFIX_DECR.INSTANCE)) {
                return null;
            }
            IrStatement irStatement = irContainerExpression.getStatements().get(0);
            IrVariable irVariable = irStatement instanceof IrVariable ? (IrVariable) irStatement : null;
            if (irVariable == null) {
                return null;
            }
            IrExpression initializer = irVariable.getInitializer();
            IrGetValue irGetValue = initializer instanceof IrGetValue ? (IrGetValue) initializer : null;
            if (irGetValue == null || !IrTypePredicatesKt.isInt(irGetValue.getType())) {
                return null;
            }
            IrStatement irStatement2 = irContainerExpression.getStatements().get(1);
            IrSetValue irSetValue = irStatement2 instanceof IrSetValue ? (IrSetValue) irStatement2 : null;
            if (irSetValue == null || !Intrinsics.areEqual(irSetValue.getSymbol(), irGetValue.getSymbol())) {
                return null;
            }
            IrExpression value = irSetValue.getValue();
            IrCall irCall = value instanceof IrCall ? (IrCall) value : null;
            if (irCall == null) {
                return null;
            }
            Name name2 = irCall.getSymbol().getOwner().getName();
            if (!Intrinsics.areEqual(name2, OperatorNameConventions.INC) && !Intrinsics.areEqual(name2, OperatorNameConventions.DEC)) {
                return null;
            }
            IrExpression dispatchReceiver = irCall.getDispatchReceiver();
            IrGetValue irGetValue2 = dispatchReceiver instanceof IrGetValue ? (IrGetValue) dispatchReceiver : null;
            if (irGetValue2 == null || !Intrinsics.areEqual(irGetValue2.getSymbol(), irVariable.getSymbol())) {
                return null;
            }
            IrStatement irStatement3 = irContainerExpression.getStatements().get(2);
            IrGetValue irGetValue3 = irStatement3 instanceof IrGetValue ? (IrGetValue) irStatement3 : null;
            if (irGetValue3 == null || !Intrinsics.areEqual(irGetValue3.getSymbol(), irVariable.getSymbol())) {
                return null;
            }
            if (Intrinsics.areEqual(name2, OperatorNameConventions.INC)) {
                irConstImplM7211int = IrConstImpl.INSTANCE.m7211int(irContainerExpression.getStartOffset(), irContainerExpression.getEndOffset(), this.this$0.getContext().getIrBuiltIns().getIntType(), 1);
            } else {
                irConstImplM7211int = IrConstImpl.INSTANCE.m7211int(irContainerExpression.getStartOffset(), irContainerExpression.getEndOffset(), this.this$0.getContext().getIrBuiltIns().getIntType(), -1);
            }
            IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(irContainerExpression.getStartOffset(), irContainerExpression.getEndOffset(), this.this$0.getContext().getIr().getSymbols().getIntPostfixIncrDecr());
            irCallImplFromSymbolOwner.putValueArgument(0, irGetValue);
            irCallImplFromSymbolOwner.putValueArgument(1, irConstImplM7211int);
            return irCallImplFromSymbolOwner;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetValue2(IrGetValue expression, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrExpression inlineableValueForTemporaryVal = getInlineableValueForTemporaryVal(expression.getSymbol().getOwner());
            if (inlineableValueForTemporaryVal instanceof IrConst) {
                return IrConstImplKt.copyWithOffsets((IrConst) inlineableValueForTemporaryVal, expression.getStartOffset(), expression.getEndOffset());
            }
            if (inlineableValueForTemporaryVal instanceof IrGetValue) {
                return IrGetValueImplKt.copyWithOffsets((IrGetValue) inlineableValueForTemporaryVal, expression.getStartOffset(), expression.getEndOffset());
            }
            return expression;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSetValue2(IrSetValue expression, IrDeclaration data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            expression.transformChildren(this, data);
            IrExpression irExpressionRewritePrefixIncrDecr = rewritePrefixIncrDecr(expression);
            return irExpressionRewritePrefixIncrDecr == null ? expression : irExpressionRewritePrefixIncrDecr;
        }

        private final IrExpression rewritePrefixIncrDecr(IrSetValue expression) {
            IrCall irCall;
            IrExpression dispatchReceiver;
            if (!IrTypePredicatesKt.isInt(expression.getSymbol().getOwner().getType())) {
                return null;
            }
            IrStatementOrigin origin = expression.getOrigin();
            if (Intrinsics.areEqual(origin, IrStatementOrigin.PREFIX_INCR.INSTANCE) ? true : Intrinsics.areEqual(origin, IrStatementOrigin.PREFIX_DECR.INSTANCE)) {
                return prefixIncr(expression, Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.PREFIX_INCR.INSTANCE) ? 1 : -1);
            }
            if (Intrinsics.areEqual(origin, IrStatementOrigin.PLUSEQ.INSTANCE) ? true : Intrinsics.areEqual(origin, IrStatementOrigin.MINUSEQ.INSTANCE)) {
                IrExpression value = expression.getValue();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCall");
                IrExpression valueArgument = ((IrCall) value).getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument);
                if (hasSameLineNumber(valueArgument, expression)) {
                    return rewriteCompoundAssignmentAsPrefixIncrDecr(expression, valueArgument, expression.getOrigin() instanceof IrStatementOrigin.MINUSEQ);
                }
                return null;
            }
            if (Intrinsics.areEqual(origin, IrStatementOrigin.EQ.INSTANCE)) {
                IrExpression value2 = expression.getValue();
                IrSetValue irSetValue = expression;
                if (!hasSameLineNumber(value2, irSetValue) || !(value2 instanceof IrCall) || (dispatchReceiver = (irCall = (IrCall) value2).getDispatchReceiver()) == null) {
                    return null;
                }
                IrValueSymbol symbol = expression.getSymbol();
                if (!hasSameLineNumber(dispatchReceiver, irSetValue)) {
                    return null;
                }
                if (Intrinsics.areEqual(irCall.getOrigin(), IrStatementOrigin.PLUS.INSTANCE) || Intrinsics.areEqual(irCall.getOrigin(), IrStatementOrigin.MINUS.INSTANCE)) {
                    IrExpression valueArgument2 = irCall.getValueArgument(0);
                    Intrinsics.checkNotNull(valueArgument2);
                    if ((dispatchReceiver instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) dispatchReceiver).getSymbol(), symbol) && hasSameLineNumber(valueArgument2, irSetValue)) {
                        return rewriteCompoundAssignmentAsPrefixIncrDecr(expression, valueArgument2, Intrinsics.areEqual(irCall.getOrigin(), IrStatementOrigin.MINUS.INSTANCE));
                    }
                }
            }
            return null;
        }

        private final IrExpression rewriteCompoundAssignmentAsPrefixIncrDecr(IrSetValue expression, IrExpression value, boolean isMinus) {
            if (!(value instanceof IrConst)) {
                return null;
            }
            IrConst<?> irConst = (IrConst) value;
            if (!Intrinsics.areEqual(irConst.getKind(), IrConstKind.Int.INSTANCE)) {
                return null;
            }
            int iIntValue = IrConstKind.Int.INSTANCE.valueOf(irConst).intValue();
            int i = (isMinus ? 1 : 0) + 127;
            if ((isMinus ? 1 : 0) - 128 > iIntValue || iIntValue > i) {
                return null;
            }
            if (isMinus) {
                iIntValue = -iIntValue;
            }
            return prefixIncr(expression, iIntValue);
        }

        private final IrExpression prefixIncr(IrSetValue expression, int delta) {
            int startOffset = expression.getStartOffset();
            int endOffset = expression.getEndOffset();
            IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(startOffset, endOffset, this.this$0.getContext().getIr().getSymbols().getIntPrefixIncrDecr());
            JvmOptimizationLowering jvmOptimizationLowering = this.this$0;
            irCallImplFromSymbolOwner.putValueArgument(0, new IrGetValueImpl(startOffset, endOffset, expression.getSymbol(), null, 8, null));
            irCallImplFromSymbolOwner.putValueArgument(1, IrConstImpl.INSTANCE.m7211int(startOffset, endOffset, jvmOptimizationLowering.getContext().getIrBuiltIns().getIntType(), delta));
            return irCallImplFromSymbolOwner;
        }

        private final boolean hasSameLineNumber(IrElement e1, IrElement e2) {
            return getLineNumberForOffset(e1.getStartOffset()) == getLineNumberForOffset(e2.getStartOffset());
        }

        private final int getLineNumberForOffset(int offset) {
            return this.fileEntry.getLineNumber(offset) + 1;
        }
    }
}
