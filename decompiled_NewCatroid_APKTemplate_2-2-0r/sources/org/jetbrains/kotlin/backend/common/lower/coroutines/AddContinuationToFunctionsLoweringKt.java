package org.jetbrains.kotlin.backend.common.lower.coroutines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.VariableRemapper;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: AddContinuationToFunctionsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0007H\u0002\u001a\u0014\u0010\n\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u0014\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u0012\u0010\f\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\b\u001a\u00020\t¨\u0006\r"}, d2 = {"loweredSuspendFunctionReturnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "transformSuspendFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "continuationType", "createSuspendFunctionStub", "getOrCreateFunctionWithContinuationStub", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AddContinuationToFunctionsLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction transformSuspendFunction(CommonBackendContext commonBackendContext, IrSimpleFunction irSimpleFunction) {
        IrSimpleFunction orCreateFunctionWithContinuationStub = getOrCreateFunctionWithContinuationStub(irSimpleFunction, commonBackendContext);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        IrSimpleFunction irSimpleFunction3 = orCreateFunctionWithContinuationStub;
        Map map = MapsKt.toMap(CollectionsKt.zip(IrUtilsKt.getExplicitParameters(irSimpleFunction2), IrUtilsKt.getExplicitParameters(irSimpleFunction3)));
        IrBody irBodyMoveBodyTo = IrInlineUtilsKt.moveBodyTo(irSimpleFunction2, irSimpleFunction3, map);
        for (Map.Entry entry : map.entrySet()) {
            IrValueParameter irValueParameter = (IrValueParameter) entry.getKey();
            IrValueParameter irValueParameter2 = (IrValueParameter) entry.getValue();
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            IrExpressionBody irExpressionBodyTransform = null;
            if (defaultValue != null) {
                irExpressionBodyTransform = defaultValue.transform((IrElementTransformer<? super Object>) new VariableRemapper(map), (Object) null);
            }
            irValueParameter2.setDefaultValue(irExpressionBodyTransform);
        }
        if (Intrinsics.areEqual(irSimpleFunction.getReturnType(), commonBackendContext.getIrBuiltIns().getUnitType()) && (irBodyMoveBodyTo instanceof IrBlockBody)) {
            IrBlockBody irBlockBody = (IrBlockBody) irBodyMoveBodyTo;
            if (!(CollectionsKt.lastOrNull((List) irBlockBody.getStatements()) instanceof IrReturn)) {
                irBlockBody.getStatements().add(ExpressionHelpersKt.irReturnUnit(LowerUtilsKt.createIrBuilder(commonBackendContext, orCreateFunctionWithContinuationStub.getSymbol(), IrUtilsKt.getPreviousOffset(irBlockBody.getEndOffset()), IrUtilsKt.getPreviousOffset(irBlockBody.getEndOffset()))));
            }
        }
        orCreateFunctionWithContinuationStub.setBody(irBodyMoveBodyTo);
        return orCreateFunctionWithContinuationStub;
    }

    public static final IrSimpleFunction getOrCreateFunctionWithContinuationStub(final IrSimpleFunction irSimpleFunction, final CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return (IrSimpleFunction) MappingsKt.getOrPut(context.getMapping().getSuspendFunctionsToFunctionWithContinuations(), irSimpleFunction, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.common.lower.coroutines.AddContinuationToFunctionsLoweringKt.getOrCreateFunctionWithContinuationStub.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunction irSimpleFunctionCreateSuspendFunctionStub = AddContinuationToFunctionsLoweringKt.createSuspendFunctionStub(irSimpleFunction, context);
                CommonBackendContext commonBackendContext = context;
                commonBackendContext.getMapping().getFunctionWithContinuationsToSuspendFunctions().set(irSimpleFunctionCreateSuspendFunctionStub, irSimpleFunction);
                return irSimpleFunctionCreateSuspendFunctionStub;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction createSuspendFunctionStub(IrSimpleFunction irSimpleFunction, final CommonBackendContext commonBackendContext) {
        if (!irSimpleFunction.getIsSuspend()) {
            throw new IllegalArgumentException((IrUtilsKt.getFqNameWhenAvailable(irSimpleFunction) + " should be a suspend function to create version with contunation").toString());
        }
        IrFactory factory = irSimpleFunction.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction2);
        irFunctionBuilder.setSuspend(false);
        irFunctionBuilder.setName(irSimpleFunction.getName());
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.LOWERED_SUSPEND_FUNCTION.INSTANCE);
        irFunctionBuilder.setReturnType(loweredSuspendFunctionReturnType(irSimpleFunction2, commonBackendContext.getIrBuiltIns()));
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irSimpleFunction.getParent());
        irSimpleFunctionBuildFunction.setMetadata(irSimpleFunction.getMetadata());
        IrUtilsKt.copyAnnotationsFrom(irSimpleFunctionBuildFunction, irSimpleFunction);
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, irSimpleFunction);
        IrSimpleFunction irSimpleFunction3 = irSimpleFunctionBuildFunction;
        IrSimpleFunction irSimpleFunction4 = irSimpleFunction;
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunction3, irSimpleFunction4, null, null, 6, null);
        Map<IrTypeParameterSymbol, IrType> mapMakeTypeParameterSubstitutionMap = IrUtilsKt.makeTypeParameterSubstitutionMap(irSimpleFunction4, irSimpleFunction3);
        IrSimpleFunction irSimpleFunction5 = irSimpleFunctionBuildFunction;
        IrUtilsKt.copyReceiverParametersFrom(irSimpleFunction5, irSimpleFunction2, mapMakeTypeParameterSubstitutionMap);
        List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunctionBuildFunction.getOverriddenSymbols();
        List<IrSimpleFunctionSymbol> overriddenSymbols2 = irSimpleFunction.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenSymbols2, 10));
        for (final IrSimpleFunctionSymbol irSimpleFunctionSymbol : overriddenSymbols2) {
            arrayList.add((IrSimpleFunctionSymbol) irSimpleFunction.getFactory().getStageController().restrictTo(irSimpleFunctionSymbol.getOwner(), new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.backend.common.lower.coroutines.AddContinuationToFunctionsLoweringKt$createSuspendFunctionStub$3$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    return AddContinuationToFunctionsLoweringKt.getOrCreateFunctionWithContinuationStub(irSimpleFunctionSymbol.getOwner(), commonBackendContext).getSymbol();
                }
            }));
        }
        irSimpleFunctionBuildFunction.setOverriddenSymbols(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) overriddenSymbols, (List) arrayList));
        List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
        ArrayList arrayList2 = new ArrayList(valueParameters.size());
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            ArrayList arrayList3 = arrayList2;
            arrayList3.add(IrUtilsKt.copyTo$default((IrValueParameter) it.next(), irSimpleFunction5, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
            irSimpleFunction5 = irSimpleFunction5;
            arrayList2 = arrayList3;
        }
        IrSimpleFunction irSimpleFunction6 = irSimpleFunction5;
        irSimpleFunctionBuildFunction.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setStartOffset(irSimpleFunctionBuildFunction.getStartOffset());
        irValueParameterBuilder.setEndOffset(irSimpleFunctionBuildFunction.getEndOffset());
        irValueParameterBuilder.setOrigin(IrDeclarationOrigin.CONTINUATION.INSTANCE);
        Name nameIdentifier = Name.identifier(CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setType(IrTypeUtilsKt.substitute(continuationType(irSimpleFunction2, commonBackendContext), mapMakeTypeParameterSubstitutionMap));
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunction6.getValueParameters().size());
        }
        irSimpleFunction6.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction6.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction6.getFactory(), irValueParameterBuilder, irSimpleFunction6)));
        return irSimpleFunctionBuildFunction;
    }

    private static final IrType continuationType(IrFunction irFunction, CommonBackendContext commonBackendContext) {
        return IrTypesKt.typeWith(commonBackendContext.getIr().getSymbols().getContinuationClass(), irFunction.getReturnType());
    }

    public static final IrType loweredSuspendFunctionReturnType(IrFunction function, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        return org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(function.getReturnType()) ? irBuiltIns.getAnyNType() : irBuiltIns.getAnyType();
    }
}
