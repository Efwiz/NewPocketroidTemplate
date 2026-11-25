package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: EnumWhenLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\"\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u0018\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\tH\u0014J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\f\u001a\u00020\rH\u0002J&\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0 H\u0002J&\u0010!\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0 H\u0002J&\u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020$2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0 H\u0002J\u0010\u0010%\u001a\u00020\t2\u0006\u0010#\u001a\u00020&H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/EnumWhenLowering;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getOther", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "lhs", "rhs", "subject", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mapConstEnumEntry", "", "entry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "mapRuntimeEnumEntry", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "possibleToGenerateJumpTable", "", "irWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "transformBranchSubexpression", "irExpression", "subjectOrdinalProvider", "Lkotlin/Lazy;", "transformBranches", "transformEnumEquals", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class EnumWhenLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final CommonBackendContext context;

    public EnumWhenLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    protected CommonBackendContext getContext() {
        return this.context;
    }

    protected int mapConstEnumEntry(IrEnumEntry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        List<IrDeclaration> declarations = IrUtilsKt.getParentAsClass(entry).getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrEnumEntry) {
                arrayList.add(obj);
            }
        }
        return arrayList.indexOf(entry);
    }

    protected IrExpression mapRuntimeEnumEntry(IrBuilderWithScope builder, IrExpression subject) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(subject, "subject");
        IrClass irClass = IrTypesKt.getClass(subject.getType());
        Intrinsics.checkNotNull(irClass);
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(irClass.getSymbol(), "ordinal");
        Intrinsics.checkNotNull(propertyGetter);
        IrCall irCall = ExpressionHelpersKt.irCall(builder, propertyGetter);
        irCall.setDispatchReceiver(subject);
        return irCall;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        visitFile(irFile);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitBlock(final IrBlock expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        if (!Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.WHEN.INSTANCE)) {
            return expression;
        }
        if (expression.getStatements().size() != 2) {
            return expression;
        }
        IrStatement irStatement = expression.getStatements().get(0);
        final IrVariable irVariable = irStatement instanceof IrVariable ? (IrVariable) irStatement : null;
        if (irVariable == null) {
            return expression;
        }
        IrClass irClass = IrTypesKt.getClass(irVariable.getType());
        if (irClass == null || irClass.getKind() != ClassKind.ENUM_CLASS || IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irClass)) {
            return expression;
        }
        IrStatement irStatement2 = expression.getStatements().get(1);
        IrWhen irWhen = irStatement2 instanceof IrWhen ? (IrWhen) irStatement2 : null;
        if (irWhen == null) {
            return expression;
        }
        Lazy<? extends IrVariable> lazy = LazyKt.lazy(new Function0<IrVariable>() { // from class: org.jetbrains.kotlin.backend.common.lower.EnumWhenLowering$visitBlock$subjectOrdinalProvider$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrVariable invoke() {
                IrIfThenElseImpl irIfThenElseImplMapRuntimeEnumEntry;
                CommonBackendContext context = this.this$0.getContext();
                ScopeWithIr currentScope = this.this$0.getCurrentScope();
                Intrinsics.checkNotNull(currentScope);
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(context, currentScope.getScope().getScopeOwnerSymbol(), irVariable.getStartOffset(), irVariable.getEndOffset());
                IrVariable irVariable2 = irVariable;
                EnumWhenLowering enumWhenLowering = this.this$0;
                IrBlock irBlock = expression;
                if (IrTypeUtilsKt.isNullable(irVariable2.getType())) {
                    DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                    IrVariable irVariable3 = irVariable2;
                    irIfThenElseImplMapRuntimeEnumEntry = ExpressionHelpersKt.irIfNull(declarationIrBuilder, declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getIntType(), ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable3), ExpressionHelpersKt.irInt$default(declarationIrBuilder, -1, null, 2, null), enumWhenLowering.mapRuntimeEnumEntry(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable3)));
                } else {
                    DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilderCreateIrBuilder;
                    irIfThenElseImplMapRuntimeEnumEntry = enumWhenLowering.mapRuntimeEnumEntry(declarationIrBuilder2, ExpressionHelpersKt.irGet(declarationIrBuilder2, irVariable2));
                }
                IrExpression irExpression = irIfThenElseImplMapRuntimeEnumEntry;
                IrVariable irVariableCreateTemporaryVariable = declarationIrBuilderCreateIrBuilder.getScope().createTemporaryVariable(irExpression, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpression.getStartOffset() : 0, (108 & 64) != 0 ? irExpression.getEndOffset() : 0);
                irBlock.getStatements().add(1, irVariableCreateTemporaryVariable);
                return irVariableCreateTemporaryVariable;
            }
        });
        if (possibleToGenerateJumpTable(irWhen, irVariable)) {
            transformBranches(irWhen, irVariable, lazy);
        }
        return expression;
    }

    private final boolean possibleToGenerateJumpTable(IrWhen irWhen, IrVariable subject) {
        Iterator<IrBranch> it = irWhen.getBranches().iterator();
        while (it.hasNext()) {
            IrExpression condition = it.next().getCondition();
            IrCall irCall = condition instanceof IrCall ? (IrCall) condition : null;
            if (irCall != null) {
                if (!Intrinsics.areEqual(irCall.getSymbol(), getContext().getIrBuiltIns().getEqeqSymbol())) {
                    return false;
                }
                IrExpression valueArgument = irCall.getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument);
                IrExpression valueArgument2 = irCall.getValueArgument(1);
                Intrinsics.checkNotNull(valueArgument2);
                if (getOther(valueArgument, valueArgument2, subject) instanceof IrCall) {
                    return false;
                }
            }
        }
        return true;
    }

    private final IrExpression getOther(IrExpression lhs, IrExpression rhs, IrVariable subject) {
        if ((lhs instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) lhs).getSymbol().getOwner(), subject)) {
            return rhs;
        }
        if ((rhs instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) rhs).getSymbol().getOwner(), subject)) {
            return lhs;
        }
        return null;
    }

    private final IrExpression transformBranches(IrWhen irWhen, IrVariable subject, Lazy<? extends IrVariable> subjectOrdinalProvider) {
        for (IrBranch irBranch : irWhen.getBranches()) {
            irBranch.setCondition(transformBranchSubexpression(irBranch.getCondition(), subject, subjectOrdinalProvider));
            irBranch.setResult(transformBranchSubexpression(irBranch.getResult(), subject, subjectOrdinalProvider));
        }
        return irWhen;
    }

    private final IrExpression transformBranchSubexpression(IrExpression irExpression, IrVariable subject, Lazy<? extends IrVariable> subjectOrdinalProvider) {
        if (irExpression instanceof IrCall) {
            return transformEnumEquals((IrCall) irExpression, subject, subjectOrdinalProvider);
        }
        return irExpression instanceof IrWhen ? transformBranches((IrWhen) irExpression, subject, subjectOrdinalProvider) : irExpression;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0072  */
    /* JADX WARN: Code duplicated, block: B:21:0x0078  */
    /* JADX WARN: Code duplicated, block: B:24:0x00ea  */
    private final IrExpression transformEnumEquals(IrCall expression, IrVariable subject, Lazy<? extends IrVariable> subjectOrdinalProvider) {
        int iMapConstEnumEntry;
        if (!Intrinsics.areEqual(expression.getSymbol(), getContext().getIrBuiltIns().getEqeqSymbol())) {
            return expression;
        }
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrExpression valueArgument2 = expression.getValueArgument(1);
        Intrinsics.checkNotNull(valueArgument2);
        IrExpression other = getOther(valueArgument, valueArgument2, subject);
        if (other == null) {
            return expression;
        }
        if (other instanceof IrGetEnumValue) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(subject.getType());
            IrGetEnumValue irGetEnumValue = (IrGetEnumValue) other;
            if (Intrinsics.areEqual(classifierOrNull != null ? classifierOrNull.getOwner() : null, irGetEnumValue.getSymbol().getOwner().getParent())) {
                iMapConstEnumEntry = mapConstEnumEntry(irGetEnumValue.getSymbol().getOwner());
            } else {
                if (IrUtilsKt.isNullConst(other)) {
                    return expression;
                }
                iMapConstEnumEntry = -1;
            }
        } else {
            if (IrUtilsKt.isNullConst(other)) {
                return expression;
            }
            iMapConstEnumEntry = -1;
        }
        IrVariable value = subjectOrdinalProvider.getValue();
        IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getSymbol(), 0, 2, null, null, 192, null);
        irCallImpl.putValueArgument(0, new IrGetValueImpl(valueArgument.getStartOffset(), valueArgument.getEndOffset(), value.getType(), value.getSymbol(), null, 16, null));
        irCallImpl.putValueArgument(1, IrConstImpl.INSTANCE.m7211int(valueArgument2.getStartOffset(), valueArgument2.getEndOffset(), getContext().getIrBuiltIns().getIntType(), iMapConstEnumEntry));
        return irCallImpl;
    }
}
