package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: MainMethodGenerationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J1\u0010\t\u001a\u00020\n*\u00020\b2#\u0010\u000b\u001a\u001f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00060\f¢\u0006\u0002\b\u000fH\u0002J&\u0010\u0010\u001a\u00020\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0015\u001a\u00020\nH\u0002J\f\u0010\u0016\u001a\u00020\u0017*\u00020\nH\u0002J\f\u0010\u0018\u001a\u00020\u0017*\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/MainMethodGenerationLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "lower", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateMainMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "makeBody", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lkotlin/ExtensionFunctionType;", "irRunSuspend", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "target", "args", "newMain", "isMainMethod", "", "isParameterlessMainMethod", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MainMethodGenerationLowering implements ClassLoweringPass {
    private final JvmBackendContext context;

    public MainMethodGenerationLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        IrSimpleFunction irSimpleFunction;
        IrSimpleFunction next;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (CommonConfigurationKeysKt.getLanguageVersionSettings(this.context.getConfiguration()).supportsFeature(LanguageFeature.ExtendedMainConvention) && IrUtilsKt.isFileClass(irClass)) {
            Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(irClass).iterator();
            do {
                irSimpleFunction = null;
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!isMainMethod(next));
            final IrSimpleFunction irSimpleFunction2 = next;
            if (irSimpleFunction2 != null) {
                if (irSimpleFunction2.getIsSuspend()) {
                    generateMainMethod(irClass, new Function3<IrBlockBodyBuilder, IrSimpleFunction, IrValueParameter, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.MainMethodGenerationLowering$lower$2$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction3, IrValueParameter irValueParameter) {
                            invoke2(irBlockBodyBuilder, irSimpleFunction3, irValueParameter);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrBlockBodyBuilder generateMainMethod, IrSimpleFunction newMain, IrValueParameter args) {
                            Intrinsics.checkNotNullParameter(generateMainMethod, "$this$generateMainMethod");
                            Intrinsics.checkNotNullParameter(newMain, "newMain");
                            Intrinsics.checkNotNullParameter(args, "args");
                            generateMainMethod.unaryPlus(this.this$0.irRunSuspend(generateMainMethod, irSimpleFunction2, args, newMain));
                        }
                    });
                    return;
                }
                return;
            }
            for (IrSimpleFunction irSimpleFunction3 : IrUtilsKt.getFunctions(irClass)) {
                if (isParameterlessMainMethod(irSimpleFunction3)) {
                    irSimpleFunction = irSimpleFunction3;
                    break;
                }
            }
            final IrSimpleFunction irSimpleFunction4 = irSimpleFunction;
            if (irSimpleFunction4 != null) {
                generateMainMethod(irClass, new Function3<IrBlockBodyBuilder, IrSimpleFunction, IrValueParameter, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.MainMethodGenerationLowering$lower$4$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction5, IrValueParameter irValueParameter) {
                        invoke2(irBlockBodyBuilder, irSimpleFunction5, irValueParameter);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(IrBlockBodyBuilder generateMainMethod, IrSimpleFunction newMain, IrValueParameter irValueParameter) {
                        Intrinsics.checkNotNullParameter(generateMainMethod, "$this$generateMainMethod");
                        Intrinsics.checkNotNullParameter(newMain, "newMain");
                        Intrinsics.checkNotNullParameter(irValueParameter, "<anonymous parameter 1>");
                        if (irSimpleFunction4.getIsSuspend()) {
                            generateMainMethod.unaryPlus(this.irRunSuspend(generateMainMethod, irSimpleFunction4, null, newMain));
                        } else {
                            generateMainMethod.unaryPlus(ExpressionHelpersKt.irCall(generateMainMethod, irSimpleFunction4));
                        }
                    }
                });
            }
        }
    }

    private final boolean isParameterlessMainMethod(IrSimpleFunction irSimpleFunction) {
        return irSimpleFunction.getTypeParameters().isEmpty() && irSimpleFunction.getExtensionReceiverParameter() == null && irSimpleFunction.getValueParameters().isEmpty() && IrTypePredicatesKt.isUnit(irSimpleFunction.getReturnType()) && Intrinsics.areEqual(irSimpleFunction.getName().asString(), "main");
    }

    private final boolean isMainMethod(IrSimpleFunction irSimpleFunction) {
        IrValueParameter irValueParameter;
        String jvmNameFromAnnotation = JvmIrUtilsKt.getJvmNameFromAnnotation(irSimpleFunction);
        if (jvmNameFromAnnotation == null) {
            jvmNameFromAnnotation = irSimpleFunction.getName().asString();
            Intrinsics.checkNotNullExpressionValue(jvmNameFromAnnotation, "asString(...)");
        }
        if (!Intrinsics.areEqual(jvmNameFromAnnotation, "main") || !IrTypePredicatesKt.isUnit(irSimpleFunction.getReturnType()) || (irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) IrUtilsKt.getAllParameters(irSimpleFunction))) == null) {
            return false;
        }
        if (!IrTypePredicatesKt.isArray(irValueParameter.getType()) && !IrTypePredicatesKt.isNullableArray(irValueParameter.getType())) {
            return false;
        }
        IrType type = irValueParameter.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrTypeArgument irTypeArgument = (IrTypeArgument) CollectionsKt.first((List) ((IrSimpleType) type).getArguments());
        if (irTypeArgument instanceof IrTypeProjection) {
            IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
            return irTypeProjection.getVariance() != Variance.IN_VARIANCE && IrTypePredicatesKt.isStringClassType(irTypeProjection.getType());
        }
        if (irTypeArgument instanceof IrStarProjection) {
            return false;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression irRunSuspend(IrBuilderWithScope irBuilderWithScope, IrSimpleFunction irSimpleFunction, IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction2) {
        IrField irFieldBuildField;
        IrCall irCall;
        JvmBackendContext jvmBackendContext = this.context;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), null, null, false, 64, null);
        IrFactory irFactory = jvmBackendContext.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameSpecial = Name.special("<main-wrapper>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irClassBuilder.setName(nameSpecial);
        DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
        irClassBuilder.setVisibility(PACKAGE_VISIBILITY);
        irClassBuilder.setModality(Modality.FINAL);
        irClassBuilder.setOrigin(JvmLoweredDeclarationOrigin.FUNCTION_REFERENCE_IMPL.INSTANCE);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        irBlockBuilder.unaryPlus(irClassBuildClass);
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        IrClassSymbol lambdaClass = jvmBackendContext.getIr().getSymbols().getLambdaClass();
        IrClassSymbol jvmSuspendFunctionClass = jvmBackendContext.getIr().getSymbols().getJvmSuspendFunctionClass(0);
        irClassBuildClass.setSuperTypes(CollectionsKt.plus((Collection<? extends IrType>) irClassBuildClass.getSuperTypes(), IrTypesKt.getDefaultType(lambdaClass)));
        irClassBuildClass.setSuperTypes(CollectionsKt.plus((Collection<? extends IrSimpleType>) irClassBuildClass.getSuperTypes(), IrTypesKt.typeWith(jvmSuspendFunctionClass, jvmBackendContext.getIrBuiltIns().getAnyNType())));
        irClassBuildClass.setParent(irSimpleFunction2);
        IrSimpleType irSimpleTypeTypeWith = IrTypesKt.typeWith(jvmBackendContext.getIrBuiltIns().getArrayClass(), jvmBackendContext.getIrBuiltIns().getStringType());
        if (irValueParameter != null) {
            IrFactory factory = irClassBuildClass.getFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            Name nameIdentifier = Name.identifier("args");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(irSimpleTypeTypeWith);
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            irFieldBuilder.setVisibility(PRIVATE);
            irFieldBuilder.setOrigin(LocalDeclarationsLowering.DECLARATION_ORIGIN_FIELD_FOR_CAPTURED_VALUE.INSTANCE);
            irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
            irFieldBuildField.setParent(irClassBuildClass);
            irClassBuildClass.getDeclarations().add(irFieldBuildField);
        } else {
            irFieldBuildField = null;
        }
        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(irClassBuildClass, "invoke", jvmBackendContext.getIrBuiltIns().getAnyNType(), null, null, false, true, false, false, null, 0, 0, 2012, null);
        irSimpleFunctionAddFunction$default.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) irSimpleFunctionAddFunction$default.getOverriddenSymbols(), (IrSimpleFunctionSymbol) SequencesKt.single(IrUtilsKt.getFunctions(jvmSuspendFunctionClass))));
        JvmBackendContext jvmBackendContext2 = jvmBackendContext;
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(jvmBackendContext2, irSimpleFunctionAddFunction$default.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, irSimpleFunction.getSymbol());
        if (irValueParameter != null) {
            IrValueParameter dispatchReceiverParameter = irSimpleFunctionAddFunction$default.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter);
            Intrinsics.checkNotNull(irFieldBuildField);
            irCall = irCall2;
            irCall.putValueArgument(0, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, irGetValueImplIrGet, irFieldBuildField, null, 4, null));
        } else {
            irCall = irCall2;
        }
        Unit unit = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irCall));
        irSimpleFunctionAddFunction$default.setBody(irBlockBodyBuilder.getIrBlockBody());
        IrFactory factory2 = irClassBuildClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setPrimary(true);
        DescriptorVisibility PACKAGE_VISIBILITY2 = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY2, "PACKAGE_VISIBILITY");
        irFunctionBuilder.setVisibility(PACKAGE_VISIBILITY2);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClassBuildClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder);
        irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClassBuildClass);
        IrConstructor irConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(lambdaClass.getOwner()));
        IrValueParameter irValueParameterAddValueParameter$default = irValueParameter != null ? DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "args", irSimpleTypeTypeWith, (IrDeclarationOrigin) null, 4, (Object) null) : null;
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default2 = LowerUtilsKt.createIrBuilder$default(jvmBackendContext2, irConstructorBuildConstructor.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder3 = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default2.getContext(), declarationIrBuilderCreateIrBuilder$default2.getScope(), declarationIrBuilderCreateIrBuilder$default2.getStartOffset(), declarationIrBuilderCreateIrBuilder$default2.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder3;
        IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder4, irConstructor);
        irDelegatingConstructorCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(irBlockBodyBuilder4, 1, null, 2, null));
        irBlockBodyBuilder3.unaryPlus(irDelegatingConstructorCall);
        if (irValueParameter != null) {
            IrValueParameter thisReceiver = irClassBuildClass.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            IrGetValueImpl irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(irBlockBodyBuilder4, thisReceiver);
            Intrinsics.checkNotNull(irFieldBuildField);
            Intrinsics.checkNotNull(irValueParameterAddValueParameter$default);
            irBlockBodyBuilder3.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder4, irGetValueImplIrGet2, irFieldBuildField, ExpressionHelpersKt.irGet(irBlockBodyBuilder4, irValueParameterAddValueParameter$default), null, 8, null));
        }
        irConstructorBuildConstructor.setBody(irBlockBodyBuilder3.getIrBlockBody());
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrCall irCall3 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, jvmBackendContext.getIr().getSymbols().getRunSuspendFunction());
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, -1, -1, irConstructorBuildConstructor.getReturnType(), irConstructorBuildConstructor.getSymbol(), null, 16, null);
        if (irValueParameter != null) {
            irConstructorCallImplFromSymbolOwner$default.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBuilder2, irValueParameter));
        }
        Unit unit2 = Unit.INSTANCE;
        irCall3.putValueArgument(0, irConstructorCallImplFromSymbolOwner$default);
        irBlockBuilder.unaryPlus(irCall3);
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrSimpleFunction generateMainMethod(IrClass irClass, Function3<? super IrBlockBodyBuilder, ? super IrSimpleFunction, ? super IrValueParameter, Unit> function3) {
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier("main");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irFunctionBuilder.setVisibility(PUBLIC);
        irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getUnitType());
        irFunctionBuilder.setModality(Modality.OPEN);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.GENERATED_EXTENDED_MAIN.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier2 = Name.identifier("args");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier2);
        irValueParameterBuilder.setType(IrTypesKt.typeWith(this.context.getIrBuiltIns().getArrayClass(), this.context.getIrBuiltIns().getStringType()));
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
        }
        IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction);
        irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), irValueParameterBuildValueParameter));
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        function3.invoke(irBlockBodyBuilder, irSimpleFunctionBuildFunction, irValueParameterBuildValueParameter);
        irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
        return irSimpleFunctionBuildFunction;
    }
}
