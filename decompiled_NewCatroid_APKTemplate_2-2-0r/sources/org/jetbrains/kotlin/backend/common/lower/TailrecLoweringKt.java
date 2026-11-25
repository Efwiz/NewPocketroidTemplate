package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.TailCalls;
import org.jetbrains.kotlin.backend.common.TailRecursionCallsCollectorKt;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;

/* JADX INFO: compiled from: TailrecLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0007H\u0002¨\u0006\b"}, d2 = {"lowerTailRecursionCalls", "", "Lorg/jetbrains/kotlin/backend/common/lower/TailrecLowering;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "suggestVariableName", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TailrecLoweringKt {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.lower.TailrecLoweringKt$lowerTailRecursionCalls$1, reason: invalid class name */
    /* JADX INFO: compiled from: TailrecLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<IrFunctionReference, Boolean> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "followFunctionReference(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "followFunctionReference";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(TailrecLowering.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(IrFunctionReference p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(((TailrecLowering) this.receiver).followFunctionReference(p0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void lowerTailRecursionCalls(TailrecLowering tailrecLowering, IrFunction irFunction) {
        IrValueParameter irValueParameterCreateTmpVariable$default;
        TailCalls tailCallsCollectTailRecursionCalls = TailRecursionCallsCollectorKt.collectTailRecursionCalls(irFunction, new AnonymousClass1(tailrecLowering));
        Set<IrCall> setComponent1 = tailCallsCollectTailRecursionCalls.component1();
        boolean fromManyFunctions = tailCallsCollectTailRecursionCalls.getFromManyFunctions();
        if (setComponent1.isEmpty()) {
            return;
        }
        IrBody body = irFunction.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody == null) {
            return;
        }
        ArrayList<IrStatement> arrayList = new ArrayList(irBlockBody.getStatements());
        IrBlockBody irBlockBody2 = irBlockBody;
        DeclarationIrBuilder declarationIrBuilder = (DeclarationIrBuilder) LowerUtilsKt.at(LowerUtilsKt.createIrBuilder$default(tailrecLowering.getContext(), irFunction.getSymbol(), 0, 0, 6, (Object) null), irBlockBody2);
        irBlockBody.getStatements().clear();
        List<IrStatement> statements = irBlockBody.getStatements();
        DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilder;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilder2.getContext(), declarationIrBuilder2.getScope(), declarationIrBuilder2.getStartOffset(), declarationIrBuilder2.getEndOffset());
        List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(irFunction);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(explicitParameters, 10)), 16));
        for (Object obj : explicitParameters) {
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            IrValueParameter irValueParameter = (IrValueParameter) obj;
            if (fromManyFunctions || !irValueParameter.getIsAssignable()) {
                irValueParameterCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBodyBuilder, (IrExpression) ExpressionHelpersKt.irGet(irBlockBodyBuilder, irValueParameter), suggestVariableName(irValueParameter.getSymbol()), true, (IrDeclarationOrigin) null, (IrType) null, 24, (Object) null);
            } else {
                irValueParameterCreateTmpVariable$default = irValueParameter;
            }
            linkedHashMap2.put(obj, irValueParameterCreateTmpVariable$default);
            fromManyFunctions = fromManyFunctions;
        }
        LinkedHashMap linkedHashMap3 = linkedHashMap;
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrDoWhileLoopImpl irDoWhileLoopImplIrDoWhile$default = IrBuilderKt.irDoWhile$default(irBlockBodyBuilder2, null, 1, null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBlockBodyBuilder2.getContext(), irBlockBodyBuilder2.getScope(), irDoWhileLoopImplIrDoWhile$default.getStartOffset(), irDoWhileLoopImplIrDoWhile$default.getEndOffset(), null, irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType(), false, 64, null);
        IrDoWhileLoopImpl irDoWhileLoopImpl = irDoWhileLoopImplIrDoWhile$default;
        BodyTransformer bodyTransformer = new BodyTransformer(tailrecLowering, declarationIrBuilder2, irFunction, irDoWhileLoopImpl, linkedHashMap3, setComponent1);
        for (IrStatement irStatement : arrayList) {
            Intrinsics.checkNotNull(irStatement);
            irBlockBuilder.unaryPlus(IrElementsKt.transformStatement(irStatement, bodyTransformer));
        }
        irBlockBuilder.unaryPlus(IrBuilderKt.irBreak(irBlockBuilder, irDoWhileLoopImpl));
        irDoWhileLoopImplIrDoWhile$default.setBody(irBlockBuilder.getIrBlockBody());
        IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(irBlockBodyBuilder2.getContext(), irBlockBodyBuilder2.getScope(), irBlockBodyBuilder2.getStartOffset(), irBlockBodyBuilder2.getEndOffset(), null, null, false, 64, null);
        for (Map.Entry entry : linkedHashMap3.entrySet()) {
            IrValueParameter irValueParameter2 = (IrValueParameter) entry.getKey();
            IrDeclaration irDeclaration = (IrDeclaration) entry.getValue();
            if (irValueParameter2.getIsAssignable() && irValueParameter2 != irDeclaration) {
                IrBlockBuilder irBlockBuilder3 = irBlockBuilder2;
                irBlockBuilder2.unaryPlus(ExpressionHelpersKt.irSet$default(irBlockBuilder3, irValueParameter2, ExpressionHelpersKt.irGet(irBlockBuilder3, (IrValueDeclaration) irDeclaration), (IrStatementOrigin) null, 4, (Object) null));
            }
        }
        irBlockBuilder2.unaryPlus(ExpressionHelpersKt.irTrue(irBlockBuilder2));
        irDoWhileLoopImplIrDoWhile$default.setCondition(irBlockBuilder2.getIrBlockBody());
        irBlockBodyBuilder.unaryPlus(irDoWhileLoopImplIrDoWhile$default);
        CollectionsKt.addAll(statements, irBlockBodyBuilder.getIrBlockBody().getStatements());
        PatchDeclarationParentsKt.patchDeclarationParents(irBlockBody2, irFunction);
    }

    private static final String suggestVariableName(IrValueParameterSymbol irValueParameterSymbol) {
        if (irValueParameterSymbol.getOwner().getName().isSpecial()) {
            String strAsString = irValueParameterSymbol.getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            StringBuilder sb = new StringBuilder("$");
            String strSubstring = strAsString.substring(1, strAsString.length() - 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return sb.append(strSubstring).toString();
        }
        String identifier = irValueParameterSymbol.getOwner().getName().getIdentifier();
        Intrinsics.checkNotNull(identifier);
        return identifier;
    }
}
