package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.DeepCopyIrTreeWithDeclarationsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: TailrecLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\u0002\u0010\u0011J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016J\u0010\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001aH\u0016J\u0014\u0010\u001b\u001a\u00020\u0017*\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0010H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/BodyTransformer;", "Lorg/jetbrains/kotlin/backend/common/lower/VariableRemapper;", "lowering", "Lorg/jetbrains/kotlin/backend/common/lower/TailrecLowering;", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "parameterToVariable", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "tailRecursionCalls", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/backend/common/lower/TailrecLowering;Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/expressions/IrLoop;Ljava/util/Map;Ljava/util/Set;)V", "parameters", "", "getParameters", "()Ljava/util/List;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "genTailCall", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class BodyTransformer extends VariableRemapper {
    private final IrBuilderWithScope builder;
    private final IrLoop loop;
    private final TailrecLowering lowering;
    private final Map<IrValueParameter, IrValueDeclaration> parameterToVariable;
    private final List<IrValueParameter> parameters;
    private final Set<IrCall> tailRecursionCalls;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BodyTransformer(TailrecLowering lowering, IrBuilderWithScope builder, IrFunction irFunction, IrLoop loop, Map<IrValueParameter, ? extends IrValueDeclaration> parameterToVariable, Set<? extends IrCall> tailRecursionCalls) {
        super(parameterToVariable);
        Intrinsics.checkNotNullParameter(lowering, "lowering");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(parameterToVariable, "parameterToVariable");
        Intrinsics.checkNotNullParameter(tailRecursionCalls, "tailRecursionCalls");
        this.lowering = lowering;
        this.builder = builder;
        this.loop = loop;
        this.parameterToVariable = parameterToVariable;
        this.tailRecursionCalls = tailRecursionCalls;
        this.parameters = IrUtilsKt.getExplicitParameters(irFunction);
    }

    public final List<IrValueParameter> getParameters() {
        return this.parameters;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrCall irCall = expression;
        IrElementTransformerVoidKt.transformChildrenVoid(irCall, this);
        if (!this.tailRecursionCalls.contains(expression)) {
            return expression;
        }
        return genTailCall((IrBuilderWithScope) LowerUtilsKt.at(this.builder, irCall), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionReference(IrFunctionReference expression) {
        IrBody body;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (this.lowering.followFunctionReference(expression) && (body = expression.getSymbol().getOwner().getBody()) != null) {
            IrElementTransformerVoidKt.transformChildrenVoid(body, this);
        }
        return super.visitFunctionReference(expression);
    }

    private final IrExpression genTailCall(IrBuilderWithScope irBuilderWithScope, IrCall irCall) {
        IrExpression expression;
        IrBuilderWithScope irBuilderWithScope2 = irBuilderWithScope;
        IrCall irCall2 = irCall;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope2.getContext(), irBuilderWithScope2.getScope(), irCall2.getStartOffset(), irCall2.getEndOffset(), null, irCall2.getType(), false, 64, null);
        List<Pair<IrValueParameter, IrExpression>> argumentsWithIr = IrUtilsKt.getArgumentsWithIr(irCall);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<T> it = argumentsWithIr.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            Pair pair2 = TuplesKt.to((IrValueParameter) pair.component1(), ExpressionHelpersKt.irTemporary$default(irBlockBuilder, (IrExpression) pair.component2(), null, null, false, null, 30, null));
            linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
        }
        List<IrValueParameter> list = this.parameters;
        List arrayList = new ArrayList();
        for (Object obj : list) {
            if (!linkedHashMap.containsKey((IrValueParameter) obj)) {
                arrayList.add(obj);
            }
        }
        List listAsReversed = arrayList;
        for (Object obj2 : listAsReversed) {
            linkedHashMap.put(obj2, ExpressionHelpersKt.irTemporary$default(irBlockBuilder, this.lowering.nullConst(-1, -1, ((IrValueParameter) obj2).getType()), null, null, false, null, 30, null));
        }
        VariableRemapper variableRemapper = new VariableRemapper(linkedHashMap);
        if (!this.lowering.getUseProperComputationOrderOfTailrecDefaultParameters()) {
            listAsReversed = CollectionsKt.asReversed(listAsReversed);
        }
        for (Object obj3 : listAsReversed) {
            IrValueParameter irValueParameter = (IrValueParameter) obj3;
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            if (defaultValue == null || (expression = defaultValue.getExpression()) == null) {
                throw new Error("no argument specified for " + irValueParameter);
            }
            linkedHashMap.put(obj3, ExpressionHelpersKt.irTemporary$default(irBlockBuilder, ((IrExpression) PatchDeclarationParentsKt.patchDeclarationParents(DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables(expression), ExpressionHelpersKt.getParent(irBlockBuilder))).transform((IrElementTransformer<? super Object>) variableRemapper, (Object) null), null, null, false, null, 30, null));
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            IrValueParameter irValueParameter2 = (IrValueParameter) entry.getKey();
            IrVariable irVariable = (IrVariable) entry.getValue();
            LowerUtilsKt.at(irBlockBuilder, irVariable);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            IrValueDeclaration irValueDeclaration = this.parameterToVariable.get(irValueParameter2);
            Intrinsics.checkNotNull(irValueDeclaration);
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irSet$default(irBlockBuilder2, irValueDeclaration.getSymbol(), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable), (IrStatementOrigin) null, 4, (Object) null));
        }
        irBlockBuilder.unaryPlus(IrBuilderKt.irContinue(irBlockBuilder, this.loop));
        return irBlockBuilder.getIrBlockBody();
    }
}
