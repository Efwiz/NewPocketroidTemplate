package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionType;
import org.jetbrains.kotlin.backend.common.lower.loops.UtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: DefaultProgressionHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\b\u0010\u0011\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0002H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/DefaultProgressionHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "allowUnsignedBounds", "", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Z)V", "rangeClassesTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "matchIterable", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultProgressionHandler implements HeaderInfoHandler {
    private final boolean allowUnsignedBounds;
    private final CommonBackendContext context;
    private final Set<IrType> rangeClassesTypes;
    private final Symbols symbols;

    public DefaultProgressionHandler(CommonBackendContext context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.allowUnsignedBounds = z;
        Symbols symbols = context.getIr().getSymbols();
        this.symbols = symbols;
        List<IrClassSymbol> rangeClasses = symbols.getRangeClasses();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(rangeClasses, 10));
        Iterator<T> it = rangeClasses.iterator();
        while (it.hasNext()) {
            arrayList.add(IrTypesKt.getDefaultType((IrClassSymbol) it.next()));
        }
        this.rangeClassesTypes = CollectionsKt.toSet(arrayList);
    }

    public /* synthetic */ DefaultProgressionHandler(CommonBackendContext commonBackendContext, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? false : z);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return ProgressionType.INSTANCE.fromIrType(expression.getType(), this.symbols, this.allowUnsignedBounds) != null;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public HeaderInfo build(IrExpression expression, Void data, IrSymbol scopeOwner) {
        IrCall irCallIrInt$default;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, scopeOwner, expression.getStartOffset(), expression.getEndOffset());
        Pair pairCreateTemporaryVariableIfNecessary$default = UtilsKt.createTemporaryVariableIfNecessary$default(declarationIrBuilderCreateIrBuilder, expression, "progression", null, false, 12, null);
        IrVariable irVariable = (IrVariable) pairCreateTemporaryVariableIfNecessary$default.component1();
        IrExpression irExpression = (IrExpression) pairCreateTemporaryVariableIfNecessary$default.component2();
        IrClass irClass = IrTypesKt.getClass(expression.getType());
        Intrinsics.checkNotNull(irClass);
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(irClass.getSymbol(), "first");
        Intrinsics.checkNotNull(propertyGetter);
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, propertyGetter);
        irCall.setDispatchReceiver(IrUtilsKt.shallowCopy(irExpression));
        IrSimpleFunctionSymbol propertyGetter2 = AdditionalIrUtilsKt.getPropertyGetter(irClass.getSymbol(), "last");
        Intrinsics.checkNotNull(propertyGetter2);
        IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, propertyGetter2);
        irCall2.setDispatchReceiver(IrUtilsKt.shallowCopy(irExpression));
        boolean zContains = this.rangeClassesTypes.contains(expression.getType());
        if (zContains) {
            irCallIrInt$default = ExpressionHelpersKt.irInt$default(declarationIrBuilder, 1, null, 2, null);
        } else {
            IrSimpleFunctionSymbol propertyGetter3 = AdditionalIrUtilsKt.getPropertyGetter(irClass.getSymbol(), "step");
            Intrinsics.checkNotNull(propertyGetter3);
            IrCall irCall3 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, propertyGetter3);
            irCall3.setDispatchReceiver(IrUtilsKt.shallowCopy(irExpression));
            irCallIrInt$default = irCall3;
        }
        IrExpression irExpression2 = irCallIrInt$default;
        ProgressionDirection progressionDirection = zContains ? ProgressionDirection.INCREASING : ProgressionDirection.UNKNOWN;
        ProgressionType progressionTypeFromIrType = ProgressionType.INSTANCE.fromIrType(expression.getType(), this.symbols, this.allowUnsignedBounds);
        Intrinsics.checkNotNull(progressionTypeFromIrType);
        return new ProgressionHeaderInfo(progressionTypeFromIrType, irCall, irCall2, irExpression2, false, false, null, progressionDirection, CollectionsKt.listOfNotNull(irVariable), null, 624, null);
    }
}
