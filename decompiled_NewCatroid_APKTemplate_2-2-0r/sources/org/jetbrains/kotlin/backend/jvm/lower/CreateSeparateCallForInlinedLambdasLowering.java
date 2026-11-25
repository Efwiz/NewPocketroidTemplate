package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: CreateSeparateCallForInlinedLambdasLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011*\u00020\u0012H\u0002J\f\u0010\u0013\u001a\u00020\u0014*\u00020\rH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/CreateSeparateCallForInlinedLambdasLowering;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "getOnlyInlinableArguments", "", "Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "isInlinableExpression", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateSeparateCallForInlinedLambdasLowering extends IrElementTransformerVoid implements FileLoweringPass {
    private final JvmBackendContext context;

    public CreateSeparateCallForInlinedLambdasLowering(JvmBackendContext context) {
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

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContainerExpression(IrContainerExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof IrInlinedFunctionBlock) {
            IrInlinedFunctionBlock irInlinedFunctionBlock = (IrInlinedFunctionBlock) expression;
            if (IrInlineUtilsKt.isFunctionInlining(irInlinedFunctionBlock)) {
                List<IrExpression> onlyInlinableArguments = getOnlyInlinableArguments(irInlinedFunctionBlock);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(onlyInlinableArguments, 10));
                for (IrExpression irExpression : onlyInlinableArguments) {
                    IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(-1, -1, this.context.getIr().getSymbols().getSingleArgumentInlineFunction());
                    irCallImplFromSymbolOwner.putValueArgument(0, irExpression.transform((IrElementTransformer<? super Object>) this, (Object) null));
                    arrayList.add(irCallImplFromSymbolOwner);
                }
                ArrayList arrayList2 = arrayList;
                Iterator<T> it = org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt.getAdditionalStatementsFromInlinedBlock(irInlinedFunctionBlock).iterator();
                while (it.hasNext()) {
                    transformChildrenVoid((IrStatement) it.next());
                }
                for (IrCallImpl irCallImpl : CollectionsKt.reversed(arrayList2)) {
                    org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt.putStatementBeforeActualInline(irInlinedFunctionBlock, JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irCallImpl.getSymbol(), 0, 0, 6, null), irCallImpl);
                }
                return expression;
            }
        }
        return super.visitContainerExpression(expression);
    }

    private final List<IrExpression> getOnlyInlinableArguments(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        List<Pair<IrValueParameter, IrExpression>> argumentsWithIr = IrUtilsKt.getArgumentsWithIr(irInlinedFunctionBlock.getInlineCall());
        ArrayList arrayList = new ArrayList();
        for (Object obj : argumentsWithIr) {
            Pair pair = (Pair) obj;
            IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
            IrExpression irExpression = (IrExpression) pair.component2();
            if (JvmIrInlineUtilsKt.isInlineParameter(irValueParameter) && isInlinableExpression(irExpression)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add((IrExpression) ((Pair) it.next()).getSecond());
        }
        return arrayList3;
    }

    private final boolean isInlinableExpression(IrExpression irExpression) {
        return (irExpression instanceof IrFunctionExpression) || (irExpression instanceof IrFunctionReference) || (irExpression instanceof IrPropertyReference) || ((irExpression instanceof IrBlock) && Intrinsics.areEqual(((IrBlock) irExpression).getOrigin(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE));
    }
}
