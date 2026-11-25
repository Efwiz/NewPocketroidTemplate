package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ComplexExternalDeclarationsToTopLevelFunctionsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u000f"}, d2 = {"createExternalJsFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "originalName", "Lorg/jetbrains/kotlin/name/Name;", "suffix", "", "resultType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "jsCode", "numDefaultParametersForExternalFunction", "", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ComplexExternalDeclarationsToTopLevelFunctionsLoweringKt {
    public static final IrSimpleFunction createExternalJsFunction(WasmBackendContext context, Name originalName, String suffix, IrType resultType, String jsCode) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(originalName, "originalName");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        Intrinsics.checkNotNullParameter(jsCode, "jsCode");
        IrFactory irFactory = context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(originalName.asStringStripSpecialMarkers() + suffix);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(resultType);
        irFunctionBuilder.setExternal(true);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
        List<IrConstructorCall> annotations = irSimpleFunctionBuildFunction.getAnnotations();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor(declarationIrBuilder, context.getWasmSymbols().getJsRelatedSymbols().getJsFunConstructor(), CollectionsKt.emptyList());
        irConstructorCallIrCallConstructor.putValueArgument(0, ExpressionHelpersKt.irString(declarationIrBuilder, jsCode));
        Unit unit = Unit.INSTANCE;
        irSimpleFunctionBuildFunction.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) annotations, irConstructorCallIrCallConstructor));
        return irSimpleFunctionBuildFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int numDefaultParametersForExternalFunction(IrFunction irFunction) {
        Object next;
        Integer numValueOf;
        if (irFunction instanceof IrSimpleFunction) {
            Iterator<T> it = ((IrSimpleFunction) irFunction).getOverriddenSymbols().iterator();
            if (it.hasNext()) {
                numValueOf = Integer.valueOf(numDefaultParametersForExternalFunction(((IrSimpleFunctionSymbol) it.next()).getOwner()));
                while (it.hasNext()) {
                    Integer numValueOf2 = Integer.valueOf(numDefaultParametersForExternalFunction(((IrSimpleFunctionSymbol) it.next()).getOwner()));
                    if (numValueOf.compareTo(numValueOf2) < 0) {
                        numValueOf = numValueOf2;
                    }
                }
            } else {
                numValueOf = null;
            }
            Integer num = numValueOf;
            int iIntValue = num != null ? num.intValue() : 0;
            if (iIntValue > 0) {
                return iIntValue;
            }
        }
        Iterator<T> it2 = irFunction.getValueParameters().iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
        } while (((IrValueParameter) next).getDefaultValue() == null);
        IrValueParameter irValueParameter = (IrValueParameter) next;
        Integer numValueOf3 = irValueParameter != null ? Integer.valueOf(irValueParameter.getIndex()) : null;
        if (numValueOf3 == null) {
            return 0;
        }
        return irFunction.getValueParameters().size() - numValueOf3.intValue();
    }
}
