package org.jetbrains.kotlin.ir.backend.js.lower;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.ir.ValueRemapper;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: ES6ConstructorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\bH\u0002J\u0016\u0010\u0019\u001a\u00020\u00172\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00170\u001bH\u0002J\u0018\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001b2\u0006\u0010\u001e\u001a\u00020\u001dH\u0016J\f\u0010\u001f\u001a\u00020 *\u00020\u001dH\u0002J\f\u0010!\u001a\u00020\b*\u00020\nH\u0002J\u0016\u0010\"\u001a\u0004\u0018\u00010\n*\u00020\n2\u0006\u0010#\u001a\u00020\bH\u0002J\f\u0010$\u001a\u00020\b*\u00020\nH\u0002J\u001c\u0010%\u001a\u00020&*\u00020'2\u0006\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u0017H\u0002J&\u0010*\u001a\u0004\u0018\u00010+*\u00020,2\u0006\u0010(\u001a\u00020\u00132\u0006\u0010-\u001a\u00020\b2\u0006\u0010.\u001a\u00020\nH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0018\u0010\u0011\u001a\u00020\u0012*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ES6ConstructorLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "constructorFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getConstructorFactory", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setConstructorFactory", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "constructorFactory$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "constructorPostfix", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getConstructorPostfix", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/String;", "getCurrentConstructorReference", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "currentFactoryFunction", "irAnyArray", PathTree.NAME_elements, "", "transformFlat", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "excludeFromExport", "", "generateCreateFunction", "generateExportedConstructorIfNeed", "factoryFunction", "generateInitFunction", "generateThisVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irClass", "initializer", "replaceSuperCallsAndThisUsages", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "constructorReplacement", "currentConstructor", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ES6ConstructorLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(ES6ConstructorLowering.class), "constructorFactory", "getConstructorFactory(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};

    /* JADX INFO: renamed from: constructorFactory$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate constructorFactory;
    private final JsIrBackendContext context;

    public ES6ConstructorLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.constructorFactory = context.getMapping().getSecondaryConstructorToFactory();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    private final void setConstructorFactory(IrConstructor irConstructor, IrSimpleFunction irSimpleFunction) {
        this.constructorFactory.setValue(irConstructor, $$delegatedProperties[0], irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!this.context.getEs6mode() || !(declaration instanceof IrConstructor)) {
            return null;
        }
        IrConstructor irConstructor = (IrConstructor) declaration;
        if (IrJsUtilsKt.hasStrictSignature(irConstructor, this.context)) {
            return null;
        }
        if (PrimaryConstructorLoweringKt.isSyntheticPrimaryConstructor(declaration)) {
            return CollectionsKt.listOf(generateInitFunction(irConstructor));
        }
        IrSimpleFunction irSimpleFunctionGenerateCreateFunction = generateCreateFunction(irConstructor);
        return CollectionsKt.listOfNotNull((Object[]) new IrFunction[]{irSimpleFunctionGenerateCreateFunction, generateExportedConstructorIfNeed(irConstructor, irSimpleFunctionGenerateCreateFunction)});
    }

    private final IrConstructor generateExportedConstructorIfNeed(final IrConstructor irConstructor, final IrSimpleFunction irSimpleFunction) {
        if (!(ExportModelGeneratorKt.isExported(irConstructor, this.context) && irConstructor.getIsPrimary())) {
            return null;
        }
        List<IrValueParameter> valueParameters = irConstructor.getValueParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            if (!ES6AddBoxParameterLoweringKt.isBoxParameter((IrValueParameter) obj)) {
                arrayList.add(obj);
            }
        }
        irConstructor.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        IrBody body = irConstructor.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        irConstructor.setBody(irBlockBody != null ? IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), irBlockBody.getStartOffset(), irBlockBody.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorLowering$generateExportedConstructorIfNeed$1$1$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody2) {
                invoke2(irBlockBody2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irSimpleFunction.getSymbol(), null, null, null, null, 30, null);
                IrConstructor irConstructor2 = irConstructor;
                ES6ConstructorLowering eS6ConstructorLowering = this;
                int i = 0;
                for (Object obj2 : irConstructor2.getValueParameters()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    irCallBuildCall$default.putValueArgument(i, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, ((IrValueParameter) obj2).getSymbol(), null, 2, null));
                    i = i2;
                }
                irCallBuildCall$default.setDispatchReceiver(JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, eS6ConstructorLowering.getContext().getIntrinsics().getJsNewTarget(), null, null, null, null, 30, null));
                createBlockBody.getStatements().add(JsIrBuilder.INSTANCE.buildReturn(irConstructor.getSymbol(), irCallBuildCall$default, irConstructor.getReturnType()));
            }
        }) : null);
        irConstructor.setOrigin(ES6_SYNTHETIC_EXPORT_CONSTRUCTOR.INSTANCE);
        return irConstructor;
    }

    private final IrSimpleFunction generateInitFunction(IrConstructor irConstructor) {
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
        String str = "init_" + getConstructorPostfix(parentAsClass);
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getUnitType());
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFunctionBuilder.setVisibility(PRIVATE);
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setInline(irConstructor.getIsInline());
        irFunctionBuilder.setExternal(irConstructor.getIsExternal());
        irFunctionBuilder.setOrigin(ES6_INIT_FUNCTION.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(parentAsClass);
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionBuildFunction, parentAsClass, null, null, 6, null);
        irSimpleFunctionBuildFunction.setAnnotations(irConstructor.getAnnotations());
        IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
        IrBody irBody = null;
        irSimpleFunctionBuildFunction.setExtensionReceiverParameter(thisReceiver != null ? IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
        IrBody body = irConstructor.getBody();
        if (body != null) {
            IrBody irBody2 = body;
            DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
            IrVisitorsKt.acceptVoid(irBody2, deepCopySymbolRemapper);
            DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
            IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irBody2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunctionBuildFunction);
            if (irElementPatchDeclarationParents != null) {
                IrBody irBody3 = (IrBody) irElementPatchDeclarationParents;
                if (irBody3 != null) {
                    IrValueParameter thisReceiver2 = parentAsClass.getThisReceiver();
                    Intrinsics.checkNotNull(thisReceiver2);
                    IrValueParameterSymbol symbol = thisReceiver2.getSymbol();
                    IrValueParameter extensionReceiverParameter = irSimpleFunctionBuildFunction.getExtensionReceiverParameter();
                    Intrinsics.checkNotNull(extensionReceiverParameter);
                    IrElementTransformerVoidKt.transformChildrenVoid(irBody3, new ValueRemapper(MapsKt.mapOf(TuplesKt.to(symbol, extensionReceiverParameter.getSymbol()))));
                    irBody = irBody3;
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
            }
        }
        irSimpleFunctionBuildFunction.setBody(irBody);
        setConstructorFactory(irConstructor, irSimpleFunctionBuildFunction);
        return irSimpleFunctionBuildFunction;
    }

    private final IrSimpleFunction generateCreateFunction(final IrConstructor irConstructor) {
        final IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(parentAsClass);
        String str = "new_" + getConstructorPostfix(parentAsClass);
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(defaultType);
        irFunctionBuilder.setVisibility(irConstructor.getVisibility());
        irFunctionBuilder.setModality(Modality.FINAL);
        irFunctionBuilder.setInline(irConstructor.getIsInline());
        irFunctionBuilder.setExternal(irConstructor.getIsExternal());
        irFunctionBuilder.setOrigin(irConstructor.getIsPrimary() ? ES6_PRIMARY_CONSTRUCTOR_REPLACEMENT.INSTANCE : ES6_CONSTRUCTOR_REPLACEMENT.INSTANCE);
        final IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(parentAsClass);
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionBuildFunction, parentAsClass, null, null, 6, null);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        IrUtilsKt.copyValueParametersFrom(irSimpleFunction, irConstructor);
        irSimpleFunctionBuildFunction.setAnnotations(irConstructor.getAnnotations());
        IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(thisReceiver != null ? IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
        if (ExportModelGeneratorKt.isExported(parentAsClass, this.context) && irConstructor.getIsPrimary()) {
            excludeFromExport(irSimpleFunctionBuildFunction);
        }
        irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorLowering$generateCreateFunction$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                IrBody body = irConstructor.getBody();
                if (body != null) {
                    IrBody irBody = body;
                    IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
                    DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                    IrVisitorsKt.acceptVoid(irBody, deepCopySymbolRemapper);
                    DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                    IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irBody.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunction2);
                    if (irElementPatchDeclarationParents != null) {
                        IrBody irBody2 = (IrBody) irElementPatchDeclarationParents;
                        if (irBody2 == null) {
                            return;
                        }
                        IrValueSymbol irValueSymbolReplaceSuperCallsAndThisUsages = this.replaceSuperCallsAndThisUsages(irBody2, parentAsClass, irSimpleFunctionBuildFunction, irConstructor);
                        createBlockBody.getStatements().addAll(IrUtilsKt.getStatements(irBody2));
                        if (irValueSymbolReplaceSuperCallsAndThisUsages != null) {
                            createBlockBody.getStatements().add(JsIrBuilder.INSTANCE.buildReturn(irSimpleFunctionBuildFunction.getSymbol(), JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irValueSymbolReplaceSuperCallsAndThisUsages, null, 2, null), IrUtilsKt.getDefaultType(parentAsClass)));
                            return;
                        }
                        return;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
                }
            }
        }));
        setConstructorFactory(irConstructor, irSimpleFunctionBuildFunction);
        return irSimpleFunctionBuildFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrVariable generateThisVariable(IrFunction irFunction, IrClass irClass, IrExpression irExpression) {
        return JsIrBuilder.INSTANCE.buildVar(IrUtilsKt.getDefaultType(irClass), irFunction, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : Namer.INSTANCE.getSYNTHETIC_RECEIVER_NAME(), (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : irExpression, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : ES6_DELEGATING_CONSTRUCTOR_CALL_REPLACEMENT.INSTANCE);
    }

    private final String getConstructorPostfix(IrClass irClass) {
        String strAsString;
        String strReplace$default;
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass);
        if (fqNameWhenAvailable != null && (strAsString = fqNameWhenAvailable.asString()) != null && (strReplace$default = StringsKt.replace$default(strAsString, '.', '_', false, 4, (Object) null)) != null) {
            return strReplace$default;
        }
        String string = irClass.getName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression irAnyArray(List<? extends IrExpression> elements) {
        return JsIrBuilder.INSTANCE.buildArray(elements, IrTypesKt.typeWith(this.context.getIrBuiltIns().getArrayClass(), this.context.getIrBuiltIns().getAnyNType()), this.context.getIrBuiltIns().getAnyNType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrValueSymbol replaceSuperCallsAndThisUsages(IrBody irBody, IrClass irClass, IrSimpleFunction irSimpleFunction, IrConstructor irConstructor) {
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new ValueRemapper(irSimpleFunction, thisReceiver.getSymbol(), irClass, this, booleanRef, ES6AddBoxParameterLoweringKt.getBoxParameter(irSimpleFunction), objectRef, MapsKt.emptyMap()) { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorLowering.replaceSuperCallsAndThisUsages.1
            final /* synthetic */ IrValueParameter $boxParameterSymbol;
            final /* synthetic */ IrSimpleFunction $constructorReplacement;
            final /* synthetic */ Ref.ObjectRef<IrValueSymbol> $generatedThisValueSymbol;
            final /* synthetic */ Ref.BooleanRef $gotLinkageErrorInsteadOfSuperCall;
            final /* synthetic */ IrClass $irClass;
            final /* synthetic */ IrValueParameterSymbol $selfParameterSymbol;
            private final Map<IrValueSymbol, IrValueSymbol> map;
            final /* synthetic */ ES6ConstructorLowering this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(map);
                this.$constructorReplacement = irSimpleFunction;
                this.$selfParameterSymbol = irValueParameterSymbol;
                this.$irClass = irClass;
                this.this$0 = this;
                this.$gotLinkageErrorInsteadOfSuperCall = booleanRef;
                this.$boxParameterSymbol = irValueParameter;
                this.$generatedThisValueSymbol = objectRef;
                Sequence<Pair> sequenceZip = SequencesKt.zip(CollectionsKt.asSequence(this.$currentConstructor.getValueParameters()), CollectionsKt.asSequence(irSimpleFunction.getValueParameters()));
                HashMap mapNewHashMapWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(this.$currentConstructor.getValueParameters().size());
                for (Pair pair : sequenceZip) {
                    Pair pair2 = TuplesKt.to(((IrValueParameter) pair.getFirst()).getSymbol(), ((IrValueParameter) pair.getSecond()).getSymbol());
                    mapNewHashMapWithExpectedSize.put(pair2.getFirst(), pair2.getSecond());
                }
                this.map = mapNewHashMapWithExpectedSize;
            }

            @Override // org.jetbrains.kotlin.backend.common.ir.ValueRemapper
            protected Map<IrValueSymbol, IrValueSymbol> getMap() {
                return this.map;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitReturn(IrReturn expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (Intrinsics.areEqual(expression.getReturnTargetSymbol(), this.$currentConstructor.getSymbol())) {
                    return super.visitReturn(JsIrBuilder.INSTANCE.buildReturn(this.$constructorReplacement.getSymbol(), JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, this.$selfParameterSymbol, null, 2, null), IrUtilsKt.getDefaultType(this.$irClass)));
                }
                return super.visitReturn(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (Intrinsics.areEqual(expression.getSymbol(), this.this$0.getContext().getIrBuiltIns().getLinkageErrorSymbol())) {
                    this.$gotLinkageErrorInsteadOfSuperCall.element = true;
                }
                return super.visitCall(expression);
            }

            /* JADX WARN: Type inference failed for: r4v10, types: [T, org.jetbrains.kotlin.ir.symbols.IrVariableSymbol] */
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                IrConstructorCall irConstructorCall;
                IrGetValueImpl irGetValueImplBuildGetValue$default;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrConstructor owner = expression.getSymbol().getOwner();
                IrConstructor irConstructor2 = owner;
                if (PrimaryConstructorLoweringKt.isSyntheticPrimaryConstructor(irConstructor2)) {
                    IrConstructorCall irConstructorCallBuildConstructorCall$default = JsIrBuilder.buildConstructorCall$default(JsIrBuilder.INSTANCE, expression.getSymbol(), null, null, ES6_INIT_CALL.INSTANCE, 6, null);
                    IrValueParameterSymbol irValueParameterSymbol = this.$selfParameterSymbol;
                    IrUtilsKt.copyValueArgumentsFrom$default(irConstructorCallBuildConstructorCall$default, expression, owner, false, false, 12, null);
                    irConstructorCallBuildConstructorCall$default.setExtensionReceiver(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irValueParameterSymbol, null, 2, null));
                    return visitConstructorCall(irConstructorCallBuildConstructorCall$default);
                }
                IrValueParameter irValueParameter = this.$boxParameterSymbol;
                IrGetValueImpl irGetValueImpl = (irValueParameter == null || (irGetValueImplBuildGetValue$default = JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irValueParameter.getSymbol(), null, 2, null)) == null) ? IrJsUtilsKt.getVoid(this.this$0.getContext()) : irGetValueImplBuildGetValue$default;
                if (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner)) {
                    IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.this$0.getContext().getIntrinsics().getJsCreateExternalThisSymbol(), null, null, null, null, 30, null);
                    ES6ConstructorLowering eS6ConstructorLowering = this.this$0;
                    irCallBuildCall$default.putValueArgument(0, eS6ConstructorLowering.getCurrentConstructorReference(this.$constructorReplacement));
                    irCallBuildCall$default.putValueArgument(1, IrJsUtilsKt.jsConstructorReference(IrUtilsKt.getParentAsClass(expression.getSymbol().getOwner()), eS6ConstructorLowering.getContext()));
                    List<IrExpression> valueArguments = IrJsUtilsKt.getValueArguments(expression);
                    ArrayList arrayList = new ArrayList(valueArguments.size());
                    for (IrExpression irExpression : valueArguments) {
                        if (irExpression == null) {
                            irExpression = IrJsUtilsKt.getVoid(eS6ConstructorLowering.getContext());
                        }
                        arrayList.add(irExpression);
                    }
                    irCallBuildCall$default.putValueArgument(2, eS6ConstructorLowering.irAnyArray(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList)));
                    irCallBuildCall$default.putValueArgument(3, irGetValueImpl);
                    irConstructorCall = irCallBuildCall$default;
                } else if (Intrinsics.areEqual(IrUtilsKt.getParentAsClass(irConstructor2).getSymbol(), this.this$0.getContext().getIrBuiltIns().getAnyClass())) {
                    IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.this$0.getContext().getIntrinsics().getJsCreateThisSymbol(), null, null, null, null, 30, null);
                    irCallBuildCall$default2.putValueArgument(0, this.this$0.getCurrentConstructorReference(this.$constructorReplacement));
                    irCallBuildCall$default2.putValueArgument(1, irGetValueImpl);
                    irConstructorCall = irCallBuildCall$default2;
                } else {
                    IrConstructorCall irConstructorCallBuildConstructorCall = JsIrBuilder.INSTANCE.buildConstructorCall(expression.getSymbol(), null, IrJsUtilsKt.getTypeArguments(expression), ES6_DELEGATING_CONSTRUCTOR_REPLACEMENT.INSTANCE);
                    IrUtilsKt.copyValueArgumentsFrom$default(irConstructorCallBuildConstructorCall, expression, owner, false, false, 12, null);
                    irConstructorCall = irConstructorCallBuildConstructorCall;
                }
                IrVariable irVariableGenerateThisVariable = this.this$0.generateThisVariable(this.$constructorReplacement, this.$irClass, irConstructorCall);
                Ref.ObjectRef<IrValueSymbol> objectRef2 = this.$generatedThisValueSymbol;
                IrValueParameterSymbol irValueParameterSymbol2 = this.$selfParameterSymbol;
                objectRef2.element = irVariableGenerateThisVariable.getSymbol();
                getMap().put(irValueParameterSymbol2, irVariableGenerateThisVariable.getSymbol());
                return super.visitComposite(JsIrBuilder.INSTANCE.buildComposite(this.this$0.getContext().getIrBuiltIns().getUnitType(), CollectionsKt.listOf(irVariableGenerateThisVariable)));
            }
        });
        IrValueSymbol irValueSymbol = (IrValueSymbol) objectRef.element;
        if (irValueSymbol != null) {
            return irValueSymbol;
        }
        if (booleanRef.element) {
            return null;
        }
        throw new IllegalStateException("Expect to have either super call or partial linkage stub inside constructor".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression getCurrentConstructorReference(IrSimpleFunction currentFactoryFunction) {
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrValueParameter dispatchReceiverParameter = currentFactoryFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        return JsIrBuilder.buildGetValue$default(jsIrBuilder, dispatchReceiverParameter.getSymbol(), null, 2, null);
    }

    private final void excludeFromExport(IrDeclaration irDeclaration) {
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(this.context.getIntrinsics().getJsExportIgnoreAnnotationSymbol().getOwner());
        if (primaryConstructor == null) {
            return;
        }
        irDeclaration.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irDeclaration.getAnnotations(), JsIrBuilder.buildConstructorCall$default(JsIrBuilder.INSTANCE, primaryConstructor.getSymbol(), null, null, null, 14, null)));
    }
}
