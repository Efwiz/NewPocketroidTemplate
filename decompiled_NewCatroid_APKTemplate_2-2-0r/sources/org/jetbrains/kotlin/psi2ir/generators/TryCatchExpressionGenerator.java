package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCatchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFinallySection;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: TryCatchExpressionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/TryCatchExpressionGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "generateTryCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ktTry", "Lorg/jetbrains/kotlin/psi/KtTryExpression;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TryCatchExpressionGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TryCatchExpressionGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    public final IrExpression generateTryCatch(KtTryExpression ktTry) {
        IrExpression irExpressionGenExpr;
        Intrinsics.checkNotNullParameter(ktTry, "ktTry");
        TryCatchExpressionGenerator tryCatchExpressionGenerator = this;
        KtTryExpression ktTryExpression = ktTry;
        IrTryImpl irTryImpl = new IrTryImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktTryExpression), PsiUtilsKt.getEndOffset(ktTryExpression), toIrType(GeneratorKt.getExpressionTypeWithCoercionToUnitOrFail(tryCatchExpressionGenerator, ktTry)));
        KtBlockExpression tryBlock = ktTry.getTryBlock();
        Intrinsics.checkNotNullExpressionValue(tryBlock, "getTryBlock(...)");
        irTryImpl.setTryResult(genExpr(tryBlock));
        for (KtCatchClause ktCatchClause : ktTry.getCatchClauses()) {
            KtParameter catchParameter = ktCatchClause.getCatchParameter();
            Intrinsics.checkNotNull(catchParameter);
            KtExpression catchBody = ktCatchClause.getCatchBody();
            Intrinsics.checkNotNull(catchBody);
            WritableSlice<KtParameter, VariableDescriptor> VALUE_PARAMETER = BindingContext.VALUE_PARAMETER;
            Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER, "VALUE_PARAMETER");
            VariableDescriptor variableDescriptor = (VariableDescriptor) GeneratorKt.getOrFail(tryCatchExpressionGenerator, VALUE_PARAMETER, catchParameter);
            Intrinsics.checkNotNull(ktCatchClause);
            KtCatchClause ktCatchClause2 = ktCatchClause;
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktCatchClause2);
            int endOffset = PsiUtilsKt.getEndOffset(ktCatchClause2);
            DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
            KtParameter ktParameter = catchParameter;
            int startOffsetSkippingComments2 = PsiUtilsKt.getStartOffsetSkippingComments(ktParameter);
            int endOffset2 = PsiUtilsKt.getEndOffset(ktParameter);
            IrDeclarationOrigin.CATCH_PARAMETER catch_parameter = IrDeclarationOrigin.CATCH_PARAMETER.INSTANCE;
            Intrinsics.checkNotNull(variableDescriptor);
            KotlinType type = variableDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            irTryImpl.getCatches().add(new IrCatchImpl(startOffsetSkippingComments, endOffset, descriptorExtension.declareVariable(startOffsetSkippingComments2, endOffset2, catch_parameter, variableDescriptor, toIrType(type)), genExpr(catchBody)));
        }
        KtFinallySection finallyBlock = ktTry.getFinallyBlock();
        if (finallyBlock != null) {
            KtBlockExpression finalExpression = finallyBlock.getFinalExpression();
            Intrinsics.checkNotNullExpressionValue(finalExpression, "getFinalExpression(...)");
            irExpressionGenExpr = genExpr(finalExpression);
        } else {
            irExpressionGenExpr = null;
        }
        irTryImpl.setFinallyExpression(irExpressionGenExpr);
        return irTryImpl;
    }
}
