package org.jetbrains.kotlin.backend.wasm.lower;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TryCatchCanonicalization.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/CatchMerger;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "ctx", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "visitTry", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CatchMerger extends IrElementTransformerVoidWithContext {
    private final CommonBackendContext ctx;

    public CatchMerger(CommonBackendContext ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTry(IrTry aTry) {
        IrVariable catchParameter;
        IrVariableSymbol symbol;
        IrVariable owner;
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        IrElementTransformerVoidKt.transformChildrenVoid(aTry, this);
        if (!aTry.getCatches().isEmpty()) {
            IrCatch irCatch = (IrCatch) CollectionsKt.singleOrNull((List) aTry.getCatches());
            if (!Intrinsics.areEqual((irCatch == null || (catchParameter = irCatch.getCatchParameter()) == null || (symbol = catchParameter.getSymbol()) == null || (owner = symbol.getOwner()) == null) ? null : owner.getType(), this.ctx.getIrBuiltIns().getThrowableType())) {
                CommonBackendContext commonBackendContext = this.ctx;
                ScopeWithIr currentScope = getCurrentScope();
                Intrinsics.checkNotNull(currentScope);
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(commonBackendContext, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
                ScopeWithIr currentScope2 = getCurrentScope();
                Intrinsics.checkNotNull(currentScope2);
                IrDeclarationParent localDeclarationParent = currentScope2.getScope().getLocalDeclarationParent();
                int startOffset = declarationIrBuilderCreateIrBuilder$default.getStartOffset();
                int endOffset = declarationIrBuilderCreateIrBuilder$default.getEndOffset();
                IrDeclarationOrigin.CATCH_PARAMETER catch_parameter = IrDeclarationOrigin.CATCH_PARAMETER.INSTANCE;
                Name nameIdentifier = Name.identifier("merged_catch_param");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(localDeclarationParent, startOffset, endOffset, catch_parameter, nameIdentifier, this.ctx.getIrBuiltIns().getThrowableType(), (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
                DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                IrTry irTry = aTry;
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irTry.getStartOffset(), irTry.getEndOffset(), null, irTry.getType(), false, 64, null);
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                IrType type = aTry.getType();
                List<IrCatch> catches = aTry.getCatches();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(catches, 10));
                for (IrCatch irCatch2 : catches) {
                    IrVariable irVariable = irVariableBuildVariable;
                    IrTypeOperatorCallImpl irTypeOperatorCallImplIrIs = ExpressionHelpersKt.irIs(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable), irCatch2.getCatchParameter().getType());
                    IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
                    IrExpression result = irCatch2.getResult();
                    IrBlockBuilder irBlockBuilder4 = new IrBlockBuilder(irBlockBuilder3.getContext(), irBlockBuilder3.getScope(), result.getStartOffset(), result.getEndOffset(), null, result.getType(), false, 64, null);
                    IrBlockBuilder irBlockBuilder5 = irBlockBuilder4;
                    irCatch2.getCatchParameter().setInitializer(ExpressionHelpersKt.irImplicitCast(irBlockBuilder5, ExpressionHelpersKt.irGet(irBlockBuilder5, irVariable), irCatch2.getCatchParameter().getType()));
                    irCatch2.getCatchParameter().setOrigin(IrDeclarationOrigin.DEFINED.INSTANCE);
                    irBlockBuilder4.unaryPlus(irCatch2.getCatchParameter());
                    irBlockBuilder4.unaryPlus(irCatch2.getResult());
                    Unit unit = Unit.INSTANCE;
                    arrayList.add(ExpressionHelpersKt.irBranch(irBlockBuilder2, irTypeOperatorCallImplIrIs, irBlockBuilder4.getIrBlockBody()));
                }
                irBlockBuilder.unaryPlus(ExpressionHelpersKt.irWhen(irBlockBuilder2, type, CollectionsKt.plus((Collection<? extends IrElseBranchImpl>) arrayList, ExpressionHelpersKt.irElseBranch(irBlockBuilder2, LowerUtilsKt.irThrow(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableBuildVariable))))));
                DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilderCreateIrBuilder$default;
                return ExpressionHelpersKt.irTry(declarationIrBuilder2, aTry.getType(), aTry.getTryResult(), CollectionsKt.listOf(LowerUtilsKt.irCatch(declarationIrBuilder2, irVariableBuildVariable, irBlockBuilder.getIrBlockBody())), aTry.getFinallyExpression());
            }
        }
        return aTry;
    }
}
