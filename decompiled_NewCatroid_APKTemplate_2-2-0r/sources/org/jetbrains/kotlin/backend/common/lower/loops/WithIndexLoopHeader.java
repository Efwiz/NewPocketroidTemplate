package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: WithIndexLoopHeader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J<\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00142\b\u0010\"\u001a\u0004\u0018\u00010\u00102\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\u00100$2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u0007H\u0016R\u0014\u0010\t\u001a\u00020\nX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/WithIndexLoopHeader;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopHeader;", "headerInfo", "Lorg/jetbrains/kotlin/backend/common/lower/loops/WithIndexHeaderInfo;", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/lower/loops/WithIndexHeaderInfo;Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "consumesLoopVariableComponents", "", "getConsumesLoopVariableComponents", "()Z", "incrementIndexStatement", "Lorg/jetbrains/kotlin/ir/IrStatement;", "indexVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getIndexVariable", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "loopInitStatements", "", "getLoopInitStatements", "()Ljava/util/List;", "nestedLoopHeader", "getNestedLoopHeader", "()Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopHeader;", "ownsIndexVariable", "buildLoop", "Lorg/jetbrains/kotlin/backend/common/lower/loops/LoopReplacement;", "oldLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "newBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "initializeIteration", "loopVariable", "loopVariableComponents", "", "", "backendContext", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WithIndexLoopHeader implements ForLoopHeader {
    private final boolean consumesLoopVariableComponents;
    private final IrStatement incrementIndexStatement;
    private final IrVariable indexVariable;
    private final List<IrStatement> loopInitStatements;
    private final ForLoopHeader nestedLoopHeader;
    private final boolean ownsIndexVariable;

    /* JADX WARN: Code duplicated, block: B:31:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:34:0x00db  */
    /* JADX WARN: Code duplicated, block: B:41:0x010d  */
    /* JADX WARN: Code duplicated, block: B:64:0x0160 A[SYNTHETIC] */
    public WithIndexLoopHeader(WithIndexHeaderInfo headerInfo, DeclarationIrBuilder builder, CommonBackendContext context) {
        IterableLoopHeader iterableLoopHeader;
        Iterator<IrSimpleFunction> it;
        IrSimpleFunction next;
        boolean z;
        Long constLongValue;
        Long constLongValue2;
        Intrinsics.checkNotNullParameter(headerInfo, "headerInfo");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(context, "context");
        HeaderInfo nestedInfo = headerInfo.getNestedInfo();
        if (nestedInfo instanceof IndexedGetHeaderInfo) {
            iterableLoopHeader = new IndexedGetLoopHeader((IndexedGetHeaderInfo) nestedInfo, builder, context);
        } else if (nestedInfo instanceof ProgressionHeaderInfo) {
            iterableLoopHeader = new ProgressionLoopHeader((ProgressionHeaderInfo) nestedInfo, builder, context);
        } else {
            if (!(nestedInfo instanceof IterableHeaderInfo)) {
                if (nestedInfo instanceof WithIndexHeaderInfo) {
                    throw new IllegalStateException("Nested WithIndexHeaderInfo not allowed for WithIndexLoopHeader");
                }
                if (!(nestedInfo instanceof FloatingPointRangeHeaderInfo ? true : nestedInfo instanceof ComparableRangeInfo)) {
                    throw new NoWhenBranchMatchedException();
                }
                throw new IllegalStateException(("Unexpected " + Reflection.getOrCreateKotlinClass(nestedInfo.getClass()).getSimpleName() + " for loops").toString());
            }
            iterableLoopHeader = new IterableLoopHeader((IterableHeaderInfo) nestedInfo);
        }
        this.nestedLoopHeader = iterableLoopHeader;
        if ((iterableLoopHeader instanceof NumericForLoopHeader) && IrTypePredicatesKt.isInt(((NumericForLoopHeader) iterableLoopHeader).getInductionVariable().getType())) {
            IrExpression initializer = ((NumericForLoopHeader) iterableLoopHeader).getInductionVariable().getInitializer();
            if (((initializer == null || (constLongValue2 = UtilsKt.getConstLongValue(initializer)) == null || constLongValue2.longValue() != 0) ? false : true) && (constLongValue = UtilsKt.getConstLongValue(((NumericForLoopHeader) iterableLoopHeader).getStepExpression())) != null && constLongValue.longValue() == 1) {
                this.indexVariable = ((NumericForLoopHeader) iterableLoopHeader).getInductionVariable();
                this.ownsIndexVariable = false;
                this.incrementIndexStatement = null;
            } else {
                DeclarationIrBuilder declarationIrBuilder = builder;
                IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(builder.getScope(), (IrExpression) ExpressionHelpersKt.irInt$default(declarationIrBuilder, 0, null, 2, null), "index", true, (IrDeclarationOrigin) null, (IrType) null, 24, (Object) null);
                this.indexVariable = irVariableCreateTmpVariable$default;
                this.ownsIndexVariable = true;
                IrClass irClass = IrTypesKt.getClass(irVariableCreateTmpVariable$default.getType());
                Intrinsics.checkNotNull(irClass);
                it = IrUtilsKt.getFunctions(irClass).iterator();
                do {
                    if (it.hasNext()) {
                        next = it.next();
                        if (!Intrinsics.areEqual(next.getName(), OperatorNameConventions.PLUS)) {
                            z = false;
                        } else {
                            z = false;
                        }
                    } else {
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                } while (!z);
                this.incrementIndexStatement = ExpressionHelpersKt.irSet$default(declarationIrBuilder, this.indexVariable.getSymbol(), ExpressionHelpersKt.irCallOp(declarationIrBuilder, next.getSymbol(), next.getReturnType(), ExpressionHelpersKt.irGet(declarationIrBuilder, this.indexVariable), (24 & 8) != 0 ? null : ExpressionHelpersKt.irInt$default(declarationIrBuilder, 1, null, 2, null), (24 & 16) != 0 ? null : null), (IrStatementOrigin) null, 4, (Object) null);
            }
        } else {
            DeclarationIrBuilder declarationIrBuilder2 = builder;
            IrVariable irVariableCreateTmpVariable$default2 = IrBuilderKt.createTmpVariable$default(builder.getScope(), (IrExpression) ExpressionHelpersKt.irInt$default(declarationIrBuilder2, 0, null, 2, null), "index", true, (IrDeclarationOrigin) null, (IrType) null, 24, (Object) null);
            this.indexVariable = irVariableCreateTmpVariable$default2;
            this.ownsIndexVariable = true;
            IrClass irClass2 = IrTypesKt.getClass(irVariableCreateTmpVariable$default2.getType());
            Intrinsics.checkNotNull(irClass2);
            it = IrUtilsKt.getFunctions(irClass2).iterator();
            do {
                if (it.hasNext()) {
                    next = it.next();
                    if (!Intrinsics.areEqual(next.getName(), OperatorNameConventions.PLUS) && next.getValueParameters().size() == 1 && IrTypePredicatesKt.isInt(next.getValueParameters().get(0).getType())) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
            } while (!z);
            this.incrementIndexStatement = ExpressionHelpersKt.irSet$default(declarationIrBuilder2, this.indexVariable.getSymbol(), ExpressionHelpersKt.irCallOp(declarationIrBuilder2, next.getSymbol(), next.getReturnType(), ExpressionHelpersKt.irGet(declarationIrBuilder2, this.indexVariable), (24 & 8) != 0 ? null : ExpressionHelpersKt.irInt$default(declarationIrBuilder2, 1, null, 2, null), (24 & 16) != 0 ? null : null), (IrStatementOrigin) null, 4, (Object) null);
        }
        List<IrStatement> loopInitStatements = this.nestedLoopHeader.getLoopInitStatements();
        this.loopInitStatements = this.ownsIndexVariable ? CollectionsKt.plus((Collection<? extends IrVariable>) loopInitStatements, this.indexVariable) : loopInitStatements;
        this.consumesLoopVariableComponents = true;
    }

    public final ForLoopHeader getNestedLoopHeader() {
        return this.nestedLoopHeader;
    }

    public final IrVariable getIndexVariable() {
        return this.indexVariable;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public List<IrStatement> getLoopInitStatements() {
        return this.loopInitStatements;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public boolean getConsumesLoopVariableComponents() {
        return this.consumesLoopVariableComponents;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public List<IrStatement> initializeIteration(IrVariable loopVariable, Map<Integer, ? extends IrVariable> loopVariableComponents, DeclarationIrBuilder builder, CommonBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(loopVariableComponents, "loopVariableComponents");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        IrVariable irVariable = loopVariableComponents.get(1);
        if (irVariable != null) {
            irVariable.setInitializer(ExpressionHelpersKt.irGet(builder, this.indexVariable));
        }
        return CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new IrStatement[]{loopVariableComponents.get(1), this.incrementIndexStatement}), (Iterable) this.nestedLoopHeader.initializeIteration(loopVariableComponents.get(2), new LinkedHashMap(), builder, backendContext));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public LoopReplacement buildLoop(DeclarationIrBuilder builder, IrLoop oldLoop, IrExpression newBody) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(oldLoop, "oldLoop");
        return this.nestedLoopHeader.buildLoop(builder, oldLoop, newBody);
    }
}
