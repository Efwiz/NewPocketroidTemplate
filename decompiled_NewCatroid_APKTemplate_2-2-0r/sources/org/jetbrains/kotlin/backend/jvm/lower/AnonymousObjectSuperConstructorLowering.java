package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;

/* JADX INFO: compiled from: AnonymousObjectSuperConstructorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/AnonymousObjectSuperConstructorLowering;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AnonymousObjectSuperConstructorLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final JvmBackendContext context;

    public AnonymousObjectSuperConstructorLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00c6  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitBlock(IrBlock expression) {
        IrElement irElementVisitBlock$transform$2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.OBJECT_LITERAL.INSTANCE)) {
            return super.visitBlock(expression);
        }
        Object objLast = CollectionsKt.last((List<? extends Object>) expression.getStatements());
        IrConstructorCall irConstructorCall = objLast instanceof IrConstructorCall ? (IrConstructorCall) objLast : null;
        if (irConstructorCall == null) {
            throw new AssertionError("object literal does not end in a constructor call");
        }
        IrConstructor owner = irConstructorCall.getSymbol().getOwner();
        IrBody body = owner.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody == null) {
            throw new AssertionError("object literal constructor body is not a block");
        }
        ArrayList<IrExpression> arrayList = new ArrayList();
        List<IrStatement> statements = irBlockBody.getStatements();
        int size = statements.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            IrElement irElement = (IrStatement) statements.get(i2);
            if (irElement instanceof IrDelegatingConstructorCall) {
                irElementVisitBlock$transform$2 = visitBlock$transform$2((IrDelegatingConstructorCall) irElement, arrayList, owner, CollectionsKt.emptyList());
            } else if (irElement instanceof IrBlock) {
                IrBlock irBlock = (IrBlock) irElement;
                if (Intrinsics.areEqual(irBlock.getOrigin(), IrStatementOrigin.ARGUMENTS_REORDERING_FOR_CALL.INSTANCE) && (CollectionsKt.last((List) irBlock.getStatements()) instanceof IrDelegatingConstructorCall)) {
                    Object objLast2 = CollectionsKt.last((List<? extends Object>) irBlock.getStatements());
                    Intrinsics.checkNotNull(objLast2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall");
                    IrDelegatingConstructorCall irDelegatingConstructorCall = (IrDelegatingConstructorCall) objLast2;
                    List<IrStatement> statements2 = irBlock.getStatements();
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj : statements2) {
                        if (obj instanceof IrVariable) {
                            arrayList2.add(obj);
                        }
                    }
                    irElementVisitBlock$transform$2 = visitBlock$transform$2(irDelegatingConstructorCall, arrayList, owner, arrayList2);
                } else {
                    irElementVisitBlock$transform$2 = irElement;
                }
            } else {
                irElementVisitBlock$transform$2 = irElement;
            }
            if (irElementVisitBlock$transform$2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
            }
            statements.set(i2, (IrStatement) irElementVisitBlock$transform$2);
        }
        int typeArgumentsCount = irConstructorCall.getTypeArgumentsCount() - irConstructorCall.getSymbol().getOwner().getTypeParameters().size();
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(jvmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
        List<IrStatement> statements3 = expression.getStatements();
        int size2 = expression.getStatements().size() - 1;
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        IrConstructorCall irConstructorCall2 = irConstructorCall;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irConstructorCall2.getStartOffset(), irConstructorCall2.getEndOffset(), null, irConstructorCall2.getType(), false, 64, null);
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner = IrConstructorCallImpl.INSTANCE.fromSymbolOwner(irConstructorCall.getStartOffset(), irConstructorCall.getEndOffset(), irConstructorCall.getType(), irConstructorCall.getSymbol(), typeArgumentsCount, irConstructorCall.getOrigin());
        int valueArgumentsCount = irConstructorCall.getValueArgumentsCount();
        for (int i3 = 0; i3 < valueArgumentsCount; i3++) {
            irConstructorCallImplFromSymbolOwner.putValueArgument(i3, irConstructorCall.getValueArgument(i3));
        }
        for (IrExpression irExpression : arrayList) {
            PatchDeclarationParentsKt.patchDeclarationParents(irExpression, getCurrentDeclarationParent());
            irConstructorCallImplFromSymbolOwner.putValueArgument(i + irConstructorCall.getValueArgumentsCount(), ExpressionHelpersKt.irGet(irBlockBuilder, ExpressionHelpersKt.irTemporary$default(irBlockBuilder, irExpression, null, null, false, null, 30, null)));
            i++;
        }
        irBlockBuilder.unaryPlus(irConstructorCallImplFromSymbolOwner);
        Unit unit = Unit.INSTANCE;
        statements3.set(size2, irBlockBuilder.getIrBlockBody());
        return super.visitBlock(expression);
    }

    private static final IrValueParameter visitBlock$addArgument(List<IrExpression> list, IrConstructor irConstructor, IrExpression irExpression) {
        list.add(irExpression);
        return DeclarationBuildersKt.addValueParameter(irConstructor, "$super_call_param$" + list.size(), irExpression.getType(), JvmLoweredDeclarationOrigin.OBJECT_SUPER_CONSTRUCTOR_PARAMETER.INSTANCE);
    }

    private static final IrExpression visitBlock$transform(IrExpression irExpression, List<IrExpression> list, IrConstructor irConstructor, Map<IrVariable, ? extends IrValueParameter> map) {
        IrValueParameterSymbol symbol;
        if (irExpression instanceof IrConst) {
            return irExpression;
        }
        if (irExpression instanceof IrGetValue) {
            int startOffset = irExpression.getStartOffset();
            int endOffset = irExpression.getEndOffset();
            IrGetValue irGetValue = (IrGetValue) irExpression;
            IrValueParameter irValueParameter = map.get(irGetValue.getSymbol().getOwner());
            return new IrGetValueImpl(startOffset, endOffset, (irValueParameter == null || (symbol = irValueParameter.getSymbol()) == null) ? irGetValue.getSymbol() : symbol, null, 8, null);
        }
        if (irExpression instanceof IrTypeOperatorCall) {
            int startOffset2 = irExpression.getStartOffset();
            int endOffset2 = irExpression.getEndOffset();
            IrType type = irExpression.getType();
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irExpression;
            return new IrTypeOperatorCallImpl(startOffset2, endOffset2, type, irTypeOperatorCall.getOperator(), irTypeOperatorCall.getTypeOperand(), visitBlock$transform(irTypeOperatorCall.getArgument(), list, irConstructor, map));
        }
        return new IrGetValueImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), visitBlock$addArgument(list, irConstructor, irExpression).getSymbol(), null, 8, null);
    }

    private static final IrDelegatingConstructorCall visitBlock$transform$2(IrDelegatingConstructorCall irDelegatingConstructorCall, List<IrExpression> list, IrConstructor irConstructor, List<? extends IrVariable> list2) {
        List<? extends IrVariable> list3 = list2;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list3, 10)), 16));
        for (Object obj : list3) {
            IrExpression initializer = ((IrVariable) obj).getInitializer();
            Intrinsics.checkNotNull(initializer);
            linkedHashMap.put(obj, visitBlock$addArgument(list, irConstructor, initializer));
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        int size = irDelegatingConstructorCall.getSymbol().getOwner().getValueParameters().size();
        for (int i = 0; i < size; i++) {
            IrExpression valueArgument = irDelegatingConstructorCall.getValueArgument(i);
            irDelegatingConstructorCall.putValueArgument(i, valueArgument != null ? visitBlock$transform(valueArgument, list, irConstructor, linkedHashMap2) : null);
        }
        return irDelegatingConstructorCall;
    }
}
