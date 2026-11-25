package org.jetbrains.kotlin.backend.common.lower;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: SingleAbstractMethodLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012,\u0010\b\u001a(\u0012\u0004\u0012\u00020\n\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\u000f¢\u0006\u0002\u0010\u0010J\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u0019H\u0002J\u0010\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R4\u0010\b\u001a(\u0012\u0004\u0012\u00020\n\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/SamEqualsHashCodeMethodsGenerator;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "samSuperType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "obtainFunctionDelegate", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lkotlin/ParameterName;", "name", "receiver", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/types/IrType;Lkotlin/jvm/functions/Function2;)V", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "functionAdapterClass", "getFunctionDelegate", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", CompilerOptions.GENERATE, "", "generateEquals", "anyGenerator", "Lorg/jetbrains/kotlin/backend/common/lower/MethodsFromAnyGeneratorForLowerings;", "generateGetFunctionDelegate", "generateHashCode", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamEqualsHashCodeMethodsGenerator {
    private final CommonBackendContext context;
    private final IrClass functionAdapterClass;
    private final IrSimpleFunction getFunctionDelegate;
    private final IrClass klass;
    private final Function2<IrBuilderWithScope, IrExpression, IrExpression> obtainFunctionDelegate;
    private final IrType samSuperType;

    /* JADX WARN: Multi-variable type inference failed */
    public SamEqualsHashCodeMethodsGenerator(CommonBackendContext context, IrClass klass, IrType samSuperType, Function2<? super IrBuilderWithScope, ? super IrExpression, ? extends IrExpression> obtainFunctionDelegate) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(samSuperType, "samSuperType");
        Intrinsics.checkNotNullParameter(obtainFunctionDelegate, "obtainFunctionDelegate");
        this.context = context;
        this.klass = klass;
        this.samSuperType = samSuperType;
        this.obtainFunctionDelegate = obtainFunctionDelegate;
        IrClass owner = context.getIr().getSymbols().getFunctionAdapter().getOwner();
        this.functionAdapterClass = owner;
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(owner)) {
            if (Intrinsics.areEqual(irSimpleFunction2.getName().asString(), "getFunctionDelegate")) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            this.getFunctionDelegate = irSimpleFunction;
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final IrBuiltIns getBuiltIns() {
        return this.context.getIrBuiltIns();
    }

    public final void generate() {
        generateGetFunctionDelegate();
        MethodsFromAnyGeneratorForLowerings methodsFromAnyGeneratorForLowerings = new MethodsFromAnyGeneratorForLowerings(this.context, this.klass, IrDeclarationOrigin.SYNTHETIC_GENERATED_SAM_IMPLEMENTATION.INSTANCE);
        generateEquals(methodsFromAnyGeneratorForLowerings);
        generateHashCode(methodsFromAnyGeneratorForLowerings);
    }

    private final void generateGetFunctionDelegate() {
        IrClass irClass = this.klass;
        String strAsString = this.getFunctionDelegate.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(irClass, strAsString, this.getFunctionDelegate.getReturnType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
        irSimpleFunctionAddFunction$default.setOverriddenSymbols(CollectionsKt.listOf(this.getFunctionDelegate.getSymbol()));
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionAddFunction$default.getSymbol(), 0, 0, 6, (Object) null);
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        Function2<IrBuilderWithScope, IrExpression, IrExpression> function2 = this.obtainFunctionDelegate;
        IrValueParameter dispatchReceiverParameter = irSimpleFunctionAddFunction$default.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irSimpleFunctionAddFunction$default.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilder, function2.invoke(declarationIrBuilderCreateIrBuilder$default, ExpressionHelpersKt.irGet(declarationIrBuilder, dispatchReceiverParameter))));
    }

    private final void generateEquals(MethodsFromAnyGeneratorForLowerings anyGenerator) {
        IrSimpleFunction irSimpleFunctionCreateEqualsMethodDeclaration = anyGenerator.createEqualsMethodDeclaration();
        IrValueParameter irValueParameter = irSimpleFunctionCreateEqualsMethodDeclaration.getValueParameters().get(0);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionCreateEqualsMethodDeclaration.getSymbol(), 0, 0, 6, (Object) null);
        IrType booleanType = getBuiltIns().getBooleanType();
        IrValueParameter irValueParameter2 = irValueParameter;
        IrTypeOperatorCallImpl irTypeOperatorCallImplIrIs = ExpressionHelpersKt.irIs(declarationIrBuilderCreateIrBuilder$default, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, irValueParameter2), this.samSuperType);
        IrType booleanType2 = getBuiltIns().getBooleanType();
        IrTypeOperatorCallImpl irTypeOperatorCallImplIrIs2 = ExpressionHelpersKt.irIs(declarationIrBuilderCreateIrBuilder$default, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, irValueParameter2), IrTypesKt.typeWith(this.functionAdapterClass, new IrType[0]));
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, this.getFunctionDelegate);
        IrValueParameter dispatchReceiverParameter = irSimpleFunctionCreateEqualsMethodDeclaration.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, dispatchReceiverParameter));
        Unit unit = Unit.INSTANCE;
        IrFunctionAccessExpression irFunctionAccessExpression = irFunctionAccessExpressionIrCall;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, this.getFunctionDelegate);
        irFunctionAccessExpressionIrCall2.setDispatchReceiver(ExpressionHelpersKt.irImplicitCast(declarationIrBuilderCreateIrBuilder$default, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, irValueParameter2), IrTypesKt.typeWith(this.functionAdapterClass, new IrType[0])));
        Unit unit2 = Unit.INSTANCE;
        irSimpleFunctionCreateEqualsMethodDeclaration.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default, ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilderCreateIrBuilder$default, booleanType, irTypeOperatorCallImplIrIs, ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilderCreateIrBuilder$default, booleanType2, irTypeOperatorCallImplIrIs2, ExpressionHelpersKt.irEquals$default(declarationIrBuilderCreateIrBuilder$default, irFunctionAccessExpression, irFunctionAccessExpressionIrCall2, null, 4, null), ExpressionHelpersKt.irFalse(declarationIrBuilderCreateIrBuilder$default), null, 16, null), ExpressionHelpersKt.irFalse(declarationIrBuilderCreateIrBuilder$default), null, 16, null)));
    }

    private final void generateHashCode(MethodsFromAnyGeneratorForLowerings anyGenerator) {
        IrSimpleFunction irSimpleFunctionCreateHashCodeMethodDeclaration = anyGenerator.createHashCodeMethodDeclaration();
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(this.context.getIrBuiltIns().getFunctionClass().getOwner())) {
            if (IrUtilsKt.isHashCode(irSimpleFunction2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            IrSimpleFunctionSymbol symbol = irSimpleFunction.getSymbol();
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionCreateHashCodeMethodDeclaration.getSymbol(), 0, 0, 6, (Object) null);
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilderCreateIrBuilder$default, symbol);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, this.getFunctionDelegate);
            IrValueParameter dispatchReceiverParameter = irSimpleFunctionCreateHashCodeMethodDeclaration.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, dispatchReceiverParameter));
            irCall.setDispatchReceiver(irFunctionAccessExpressionIrCall);
            irSimpleFunctionCreateHashCodeMethodDeclaration.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default, irCall));
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }
}
