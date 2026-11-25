package org.jetbrains.kotlin.backend.jvm.lower;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: DirectInvokeLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0018\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0010H\u0002J\u001c\u0010\u0013\u001a\u00020\u0007*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/DirectInvokeLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitFunctionReferenceInvoke", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitLambdaInvoke", "reference", "copyReceiverAndValueArgumentsForDirectInvoke", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "irFunRef", "irInvokeCall", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DirectInvokeLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final JvmBackendContext context;

    public DirectInvokeLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0042  */
    /* JADX WARN: Code duplicated, block: B:25:0x005f  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        IrCall irCallVisitFunctionReferenceInvoke;
        IrFunctionReference irFunctionReferenceAsInlinableFunctionReference;
        IrExpression irExpressionVisitLambdaInvoke;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        if (dispatchReceiver == null || !Intrinsics.areEqual(owner.getName(), OperatorNameConventions.INVOKE)) {
            return super.visitCall(expression);
        }
        if (dispatchReceiver instanceof IrFunctionReference) {
            IrFunctionReference irFunctionReference = (IrFunctionReference) dispatchReceiver;
            if (irFunctionReference.getSymbol().getOwner().getTypeParameters().isEmpty()) {
                irCallVisitFunctionReferenceInvoke = visitFunctionReferenceInvoke(expression, irFunctionReference);
            } else if ((dispatchReceiver instanceof IrBlock) || (irFunctionReferenceAsInlinableFunctionReference = IrInlineUtilsKt.asInlinableFunctionReference(dispatchReceiver)) == null) {
                irCallVisitFunctionReferenceInvoke = expression;
            } else {
                if (irFunctionReferenceAsInlinableFunctionReference.getExtensionReceiver() != null) {
                    irFunctionReferenceAsInlinableFunctionReference = null;
                }
                if (irFunctionReferenceAsInlinableFunctionReference == null || (irExpressionVisitLambdaInvoke = visitLambdaInvoke(expression, irFunctionReferenceAsInlinableFunctionReference)) == null) {
                    irCallVisitFunctionReferenceInvoke = expression;
                } else {
                    irCallVisitFunctionReferenceInvoke = irExpressionVisitLambdaInvoke;
                }
            }
        } else if (dispatchReceiver instanceof IrBlock) {
            irCallVisitFunctionReferenceInvoke = expression;
        } else {
            irCallVisitFunctionReferenceInvoke = expression;
        }
        transformChildrenVoid(irCallVisitFunctionReferenceInvoke);
        return irCallVisitFunctionReferenceInvoke;
    }

    private final IrExpression visitLambdaInvoke(IrCall expression, IrFunctionReference reference) {
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        Scope scope = currentScope.getScope();
        IrDeclarationParent localDeclarationParent = scope.getLocalDeclarationParent();
        IrFunction owner = reference.getSymbol().getOwner();
        if (expression.getValueArgumentsCount() == 0) {
            return IrInlineUtilsKt.inline$default(owner, localDeclarationParent, (List) null, 2, (Object) null);
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, scope.getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
        LowerUtilsKt.at(declarationIrBuilderCreateIrBuilder$default, expression);
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset(), null, null, false, 64, null);
        List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(owner);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(explicitParameters, 10));
        int i = 0;
        for (Object obj : explicitParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) obj;
            IrExpression valueArgument = expression.getValueArgument(i);
            Intrinsics.checkNotNull(valueArgument);
            IrVariableImpl irVariableImpl = new IrVariableImpl(valueArgument.getStartOffset(), valueArgument.getEndOffset(), IrDeclarationOrigin.DEFINED.INSTANCE, new IrVariableSymbolImpl(null, 1, null), irValueParameter.getName(), irValueParameter.getType(), false, false, false);
            irVariableImpl.setParent(localDeclarationParent);
            irVariableImpl.setInitializer(valueArgument);
            irBlockBuilder.unaryPlus(irVariableImpl);
            arrayList.add(irVariableImpl);
            i = i2;
        }
        irBlockBuilder.unaryPlus(IrInlineUtilsKt.inline(owner, localDeclarationParent, arrayList));
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression visitFunctionReferenceInvoke(IrCall expression, IrFunctionReference receiver) {
        IrFunction owner = receiver.getSymbol().getOwner();
        if (owner instanceof IrSimpleFunction) {
            IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), ((IrSimpleFunction) owner).getSymbol(), owner.getTypeParameters().size(), owner.getValueParameters().size(), null, null, 192, null);
            copyReceiverAndValueArgumentsForDirectInvoke(irCallImpl, receiver, expression);
            return irCallImpl;
        }
        if (owner instanceof IrConstructor) {
            IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), ((IrConstructor) owner).getSymbol(), owner.getTypeParameters().size(), 0, owner.getValueParameters().size(), null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
            copyReceiverAndValueArgumentsForDirectInvoke(irConstructorCallImpl, receiver, expression);
            return irConstructorCallImpl;
        }
        throw new AssertionError("Simple function or constructor expected: " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null));
    }

    private final void copyReceiverAndValueArgumentsForDirectInvoke(IrFunctionAccessExpression irFunctionAccessExpression, IrFunctionReference irFunctionReference, IrFunctionAccessExpression irFunctionAccessExpression2) {
        int i;
        IrFunction owner = irFunctionReference.getSymbol().getOwner();
        int i2 = 0;
        if (owner.getDispatchReceiverParameter() != null) {
            IrExpression dispatchReceiver = irFunctionReference.getDispatchReceiver();
            if (dispatchReceiver == null) {
                dispatchReceiver = irFunctionAccessExpression2.getValueArgument(0);
                i = 1;
            } else {
                i = 0;
            }
            irFunctionAccessExpression.setDispatchReceiver(dispatchReceiver);
        } else {
            i = 0;
        }
        if (owner.getExtensionReceiverParameter() != null) {
            IrExpression extensionReceiver = irFunctionReference.getExtensionReceiver();
            if (extensionReceiver == null) {
                IrExpression valueArgument = irFunctionAccessExpression2.getValueArgument(i);
                i++;
                extensionReceiver = valueArgument;
            }
            irFunctionAccessExpression.setExtensionReceiver(extensionReceiver);
        }
        if (irFunctionAccessExpression.getValueArgumentsCount() + i != irFunctionAccessExpression2.getValueArgumentsCount()) {
            throw new AssertionError("Mismatching value arguments: " + i + " arguments used for receivers\n" + DumpIrTreeKt.dump$default(irFunctionAccessExpression2, null, 1, null));
        }
        int valueArgumentsCount = irFunctionAccessExpression.getValueArgumentsCount();
        while (i2 < valueArgumentsCount) {
            irFunctionAccessExpression.putValueArgument(i2, irFunctionAccessExpression2.getValueArgument(i));
            i2++;
            i++;
        }
    }
}
