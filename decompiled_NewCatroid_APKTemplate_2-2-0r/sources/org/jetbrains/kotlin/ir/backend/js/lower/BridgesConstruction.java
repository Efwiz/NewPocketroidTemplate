package org.jetbrains.kotlin.ir.backend.js.lower;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.bridges.Bridge;
import org.jetbrains.kotlin.backend.common.bridges.BridgesKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.SpecialBridgeMethods;
import org.jetbrains.kotlin.backend.common.lower.SpecialMethodWithDefaultInfo;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsAstUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: BridgesConstruction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001/B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J*\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J&\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0014J\u0018\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0013\u001a\u00020\u0012H&J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u0011\u001a\u00020\u0012H&J\u0018\u0010#\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u00182\u0006\u0010$\u001a\u00020\u001eH\u0016J(\u0010%\u001a\u00020&*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020*0(H\u0002J\u001c\u0010+\u001a\u00020,*\u00020\u001b2\u0006\u0010-\u001a\u00020,2\u0006\u0010.\u001a\u00020*H\u0002R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\nX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/BridgesConstruction;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "shouldCastDispatchReceiver", "", "getShouldCastDispatchReceiver", "()Z", "specialBridgeMethods", "Lorg/jetbrains/kotlin/backend/common/lower/SpecialBridgeMethods;", "createBridge", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "bridge", "delegateTo", "specialMethodInfo", "Lorg/jetbrains/kotlin/backend/common/lower/SpecialMethodWithDefaultInfo;", "extractValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "blockBodyBuilder", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "irFunction", "generateBridges", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getBridgeOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getFunctionSignature", "", "transformFlat", "declaration", "copyValueParametersFrom", "", "substitutionMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "irCastIfNeeded", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "argument", ModuleXmlParser.TYPE, "FunctionAndSignature", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BridgesConstruction<T extends JsCommonBackendContext> implements DeclarationTransformer {
    private final T context;
    private final boolean shouldCastDispatchReceiver;
    private final SpecialBridgeMethods specialBridgeMethods;

    public abstract IrDeclarationOrigin getBridgeOrigin(IrSimpleFunction bridge);

    public abstract Object getFunctionSignature(IrSimpleFunction function);

    public BridgesConstruction(T context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.specialBridgeMethods = new SpecialBridgeMethods(context);
    }

    public final T getContext() {
        return this.context;
    }

    protected List<IrValueDeclaration> extractValueParameters(IrBlockBodyBuilder blockBodyBuilder, IrSimpleFunction irFunction, IrSimpleFunction bridge) {
        Intrinsics.checkNotNullParameter(blockBodyBuilder, "blockBodyBuilder");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(bridge, "bridge");
        return irFunction.getValueParameters();
    }

    public boolean getShouldCastDispatchReceiver() {
        return this.shouldCastDispatchReceiver;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        List<IrDeclaration> listGenerateBridges;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrSimpleFunction) || IrDeclarationsKt.isStaticMethodOfClass((IrFunction) declaration) || !(declaration.getParent() instanceof IrClass) || (listGenerateBridges = generateBridges((IrSimpleFunction) declaration)) == null) {
            return null;
        }
        return CollectionsKt.plus((Collection) CollectionsKt.listOf(declaration), (Iterable) listGenerateBridges);
    }

    private final List<IrDeclaration> generateBridges(IrSimpleFunction function) {
        IrFunction irFunctionCreateBridge;
        Pair pairFindSpecialWithOverride$default = SpecialBridgeMethods.findSpecialWithOverride$default(this.specialBridgeMethods, function, false, 2, null);
        if (pairFindSpecialWithOverride$default == null) {
            pairFindSpecialWithOverride$default = new Pair(null, null);
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) pairFindSpecialWithOverride$default.component1();
        SpecialMethodWithDefaultInfo specialMethodWithDefaultInfo = (SpecialMethodWithDefaultInfo) pairFindSpecialWithOverride$default.component2();
        FunctionAndSignature functionAndSignature = irSimpleFunction != null ? new FunctionAndSignature(this, irSimpleFunction) : null;
        Set<Bridge> setGenerateBridges = BridgesKt.generateBridges(new IrBasedFunctionHandle(function), new Function1<IrBasedFunctionHandle, BridgesConstruction<T>.FunctionAndSignature>(this) { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction$generateBridges$bridgesToGenerate$1
            final /* synthetic */ BridgesConstruction<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final BridgesConstruction<T>.FunctionAndSignature invoke(IrBasedFunctionHandle it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new BridgesConstruction.FunctionAndSignature(this.this$0, it.getFunction());
            }
        });
        if (setGenerateBridges.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Bridge bridge : setGenerateBridges) {
            FunctionAndSignature functionAndSignature2 = (FunctionAndSignature) bridge.component1();
            FunctionAndSignature functionAndSignature3 = (FunctionAndSignature) bridge.component2();
            if (IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(functionAndSignature2.getFunction())) || !AdditionalIrUtilsKt.isReal(functionAndSignature2.getFunction()) || functionAndSignature2.getFunction().getModality() == Modality.ABSTRACT || AdditionalIrUtilsKt.isReal(functionAndSignature3.getFunction())) {
                if (!Intrinsics.areEqual(new FunctionAndSignature(this, IrResolveUtilsKt.getRealOverrideTarget(functionAndSignature3.getFunction())), new FunctionAndSignature(this, IrResolveUtilsKt.getRealOverrideTarget(functionAndSignature2.getFunction()))) && (functionAndSignature2.getFunction().getCorrespondingPropertySymbol() == null || !IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) functionAndSignature2.getFunction()))) {
                    if (Intrinsics.areEqual(functionAndSignature, functionAndSignature2)) {
                        irFunctionCreateBridge = createBridge(function, functionAndSignature2.getFunction(), functionAndSignature3.getFunction(), specialMethodWithDefaultInfo);
                    } else {
                        irFunctionCreateBridge = createBridge(function, functionAndSignature2.getFunction(), functionAndSignature3.getFunction(), null);
                    }
                    arrayList.add(irFunctionCreateBridge);
                }
            }
        }
        return arrayList;
    }

    private final IrFunction createBridge(IrSimpleFunction function, final IrSimpleFunction bridge, final IrSimpleFunction delegateTo, final SpecialMethodWithDefaultInfo specialMethodInfo) {
        IrDeclarationOrigin bridgeOrigin = getBridgeOrigin(bridge);
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrSimpleFunction irSimpleFunction = bridge;
        irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction);
        irFunctionBuilder.setStartOffset(-1);
        irFunctionBuilder.setEndOffset(-1);
        irFunctionBuilder.setName(bridge.getName());
        irFunctionBuilder.setReturnType(bridge.getReturnType());
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setFakeOverride(false);
        irFunctionBuilder.setOrigin(bridgeOrigin);
        final IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(function.getParent());
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
        IrSimpleFunction irSimpleFunction3 = bridge;
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunction2, irSimpleFunction3, null, null, 6, null);
        Map<IrTypeParameterSymbol, IrType> mapMakeTypeParameterSubstitutionMap = IrUtilsKt.makeTypeParameterSubstitutionMap(irSimpleFunction3, irSimpleFunction2);
        IrSimpleFunction irSimpleFunction4 = irSimpleFunctionBuildFunction;
        IrUtilsKt.copyReceiverParametersFrom(irSimpleFunction4, irSimpleFunction, mapMakeTypeParameterSubstitutionMap);
        copyValueParametersFrom(irSimpleFunctionBuildFunction, bridge, mapMakeTypeParameterSubstitutionMap);
        irSimpleFunctionBuildFunction.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irSimpleFunctionBuildFunction.getAnnotations(), (List) bridge.getAnnotations()));
        irSimpleFunctionBuildFunction.setOverriddenSymbols(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irSimpleFunctionBuildFunction.getOverriddenSymbols(), bridge.getSymbol()), (List) delegateTo.getOverriddenSymbols()));
        irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>(this) { // from class: org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction.createBridge.1
            final /* synthetic */ BridgesConstruction<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                IrGetValueImpl irGetValueImplIrCastIfNeeded;
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                List<IrStatement> statements = createBlockBody.getStatements();
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.this$0.getContext(), irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
                IrSimpleFunction irSimpleFunction5 = irSimpleFunctionBuildFunction;
                IrSimpleFunction irSimpleFunction6 = irSimpleFunction5;
                BridgesConstruction<T> bridgesConstruction = this.this$0;
                IrSimpleFunction irSimpleFunction7 = bridge;
                SpecialMethodWithDefaultInfo specialMethodWithDefaultInfo = specialMethodInfo;
                IrSimpleFunction irSimpleFunction8 = delegateTo;
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunction6.getStartOffset(), irSimpleFunction6.getEndOffset());
                List<IrValueDeclaration> listExtractValueParameters = bridgesConstruction.extractValueParameters(irBlockBodyBuilder, irSimpleFunction5, irSimpleFunction7);
                int i = 0;
                if (specialMethodWithDefaultInfo != null) {
                    int i2 = 0;
                    for (Object obj : CollectionsKt.take(listExtractValueParameters, specialMethodWithDefaultInfo.getArgumentsToCheck())) {
                        int i3 = i2 + 1;
                        if (i2 < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irIfThen$default(irBlockBodyBuilder2, irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType(), LowerUtilsKt.irNot(irBlockBodyBuilder2, ExpressionHelpersKt.irIs(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueDeclaration) obj), irSimpleFunction8.getValueParameters().get(i2).getType())), ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, specialMethodWithDefaultInfo.getDefaultValueGenerator().invoke(irSimpleFunction5)), null, 8, null));
                        i2 = i3;
                    }
                }
                IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
                IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder3, irSimpleFunction8.getSymbol());
                IrValueParameter dispatchReceiverParameter = irSimpleFunction5.getDispatchReceiverParameter();
                Intrinsics.checkNotNull(dispatchReceiverParameter);
                IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder3, dispatchReceiverParameter);
                if (bridgesConstruction.getShouldCastDispatchReceiver()) {
                    IrValueParameter dispatchReceiverParameter2 = irSimpleFunction8.getDispatchReceiverParameter();
                    Intrinsics.checkNotNull(dispatchReceiverParameter2);
                    irGetValueImplIrCastIfNeeded = bridgesConstruction.irCastIfNeeded(irBlockBodyBuilder, irGetValueImplIrGet, dispatchReceiverParameter2.getType());
                } else {
                    irGetValueImplIrCastIfNeeded = irGetValueImplIrGet;
                }
                irCall.setDispatchReceiver(irGetValueImplIrCastIfNeeded);
                IrValueParameter extensionReceiverParameter = irSimpleFunction5.getExtensionReceiverParameter();
                if (extensionReceiverParameter != null) {
                    IrGetValueImpl irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(irBlockBodyBuilder3, extensionReceiverParameter);
                    IrValueParameter extensionReceiverParameter2 = irSimpleFunction8.getExtensionReceiverParameter();
                    Intrinsics.checkNotNull(extensionReceiverParameter2);
                    irCall.setExtensionReceiver(bridgesConstruction.irCastIfNeeded(irBlockBodyBuilder, irGetValueImplIrGet2, extensionReceiverParameter2.getType()));
                }
                int size = listExtractValueParameters.size();
                boolean zIsSuspend = IrUtilsKt.isSuspend(irCall);
                for (Object obj2 : listExtractValueParameters.subList(0, size - ((irSimpleFunction5.getIsSuspend() ? 1 : 0) ^ (zIsSuspend ? 1 : 0)))) {
                    int i4 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    irCall.putValueArgument(i, bridgesConstruction.irCastIfNeeded(irBlockBodyBuilder, ExpressionHelpersKt.irGet(irBlockBodyBuilder3, (IrValueDeclaration) obj2), irSimpleFunction8.getValueParameters().get(i).getType()));
                    i = i4;
                }
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder3, irCall));
                CollectionsKt.addAll(statements, irBlockBodyBuilder.getIrBlockBody().getStatements());
            }
        }));
        return irSimpleFunction4;
    }

    private final void copyValueParametersFrom(IrSimpleFunction irSimpleFunction, IrSimpleFunction irSimpleFunction2, Map<IrTypeParameterSymbol, ? extends IrType> map) {
        int iVarargParameterIndex;
        List<IrValueParameter> valueParameters = irSimpleFunction2.getValueParameters();
        if (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irSimpleFunction2) && (iVarargParameterIndex = JsAstUtilsKt.varargParameterIndex(irSimpleFunction2)) != -1) {
            valueParameters = CollectionsKt.take(irSimpleFunction2.getValueParameters(), iVarargParameterIndex);
        }
        List<IrValueParameter> valueParameters2 = irSimpleFunction.getValueParameters();
        List<IrValueParameter> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (IrValueParameter irValueParameter : list) {
            arrayList.add(IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunction, null, 0, 0, 0, null, null, IrTypeUtilsKt.substitute(irValueParameter.getType(), map), null, null, false, false, false, 8062, null));
        }
        irSimpleFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) valueParameters2, (List) arrayList));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression irCastIfNeeded(IrBlockBodyBuilder irBlockBodyBuilder, IrExpression irExpression, IrType irType) {
        return Intrinsics.areEqual(IrTypesKt.getClassifierOrNull(irExpression.getType()), IrTypesKt.getClassifierOrNull(irType)) ? irExpression : ExpressionHelpersKt.irAs(irBlockBodyBuilder, irExpression, irType);
    }

    /* JADX INFO: compiled from: BridgesConstruction.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/BridgesConstruction$FunctionAndSignature;", "", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/BridgesConstruction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "signature", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class FunctionAndSignature {
        private final IrSimpleFunction function;
        private final Object signature;
        final /* synthetic */ BridgesConstruction<T> this$0;

        public FunctionAndSignature(BridgesConstruction bridgesConstruction, IrSimpleFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            this.this$0 = bridgesConstruction;
            this.function = function;
            this.signature = bridgesConstruction.getFunctionSignature(function);
        }

        public final IrSimpleFunction getFunction() {
            return this.function;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof FunctionAndSignature) {
                return Intrinsics.areEqual(this.signature, ((FunctionAndSignature) other).signature);
            }
            return false;
        }

        public int hashCode() {
            return this.signature.hashCode();
        }
    }
}
