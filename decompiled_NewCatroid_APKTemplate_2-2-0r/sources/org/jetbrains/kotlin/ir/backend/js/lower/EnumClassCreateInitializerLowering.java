package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0013H\u0002J\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\bH\u0002J\u0018\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020\u001fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR3\u0010\u0012\u001a\u0004\u0018\u00010\u0011*\u00020\u00132\b\u0010\u0007\u001a\u0004\u0018\u00010\u00118B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0018\u0010\u0010\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumClassCreateInitializerLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "correspondingField", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getCorrespondingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setCorrespondingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "correspondingField$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "initEntryInstancesFun", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInitEntryInstancesFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setInitEntryInstancesFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "initEntryInstancesFun$delegate", "createEntryInstancesInitializedVar", "irClass", "createInitEntryInstancesFun", "entryInstancesInitializedField", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumClassCreateInitializerLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumClassCreateInitializerLowering.class), "correspondingField", "getCorrespondingField(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrField;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumClassCreateInitializerLowering.class), "initEntryInstancesFun", "getInitEntryInstancesFun(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: correspondingField$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate correspondingField;

    /* JADX INFO: renamed from: initEntryInstancesFun$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate initEntryInstancesFun;

    public EnumClassCreateInitializerLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.correspondingField = context.getMapping().getEnumEntryToCorrespondingField();
        this.initEntryInstancesFun = context.getMapping().getEnumClassToInitEntryInstancesFun();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrField getCorrespondingField(IrEnumEntry irEnumEntry) {
        return (IrField) this.correspondingField.getValue(irEnumEntry, $$delegatedProperties[0]);
    }

    private final void setInitEntryInstancesFun(IrClass irClass, IrSimpleFunction irSimpleFunction) {
        this.initEntryInstancesFun.setValue(irClass, $$delegatedProperties[1], irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrClass) {
            IrClass irClass = (IrClass) declaration;
            if (IrJsUtilsKt.isInstantiableEnum(irClass)) {
                IrField irFieldCreateEntryInstancesInitializedVar = createEntryInstancesInitializedVar(irClass);
                IrSimpleFunction irSimpleFunctionCreateInitEntryInstancesFun = createInitEntryInstancesFun(irClass, irFieldCreateEntryInstancesInitializedVar);
                setInitEntryInstancesFun(irClass, irSimpleFunctionCreateInitEntryInstancesFun);
                irClass.getDeclarations().add(irFieldCreateEntryInstancesInitializedVar);
                irClass.getDeclarations().add(irSimpleFunctionCreateInitEntryInstancesFun);
            }
        }
        return null;
    }

    private final IrField createEntryInstancesInitializedVar(IrClass irClass) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        String identifier = irClass.getName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        Name nameIdentifier = Name.identifier(identifier + "_entriesInitialized");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFieldBuilder.setName(nameIdentifier);
        irFieldBuilder.setType(this.context.getIrBuiltIns().getBooleanType());
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFieldBuilder.setVisibility(PRIVATE);
        irFieldBuilder.setStatic(true);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        irFieldBuildField.setInitializer(null);
        return irFieldBuildField;
    }

    private final IrSimpleFunction createInitEntryInstancesFun(final IrClass irClass, final IrField entryInstancesInitializedField) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(irClass.getName().getIdentifier() + "_initEntries");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getUnitType());
        irFunctionBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
        final IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irClass);
        irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.EnumClassCreateInitializerLowering$createInitEntryInstancesFun$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                IrExpressionBody initializerExpression;
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                List<IrStatement> statements = createBlockBody.getStatements();
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.this$0.getContext(), irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
                IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
                IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                IrField irField = entryInstancesInitializedField;
                IrClass irClass2 = irClass;
                EnumClassCreateInitializerLowering enumClassCreateInitializerLowering = this.this$0;
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunction2.getStartOffset(), irSimpleFunction2.getEndOffset());
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                irBlockBodyBuilder.unaryPlus(LowerUtilsKt.irIfThen(irBlockBodyBuilder2, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, null, irField, null, 4, null), ExpressionHelpersKt.irReturnUnit(irBlockBodyBuilder2)));
                int i = 1;
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, null, irField, ExpressionHelpersKt.irBoolean(irBlockBodyBuilder2, true), null, 8, null));
                for (IrEnumEntry irEnumEntry : EnumClassLoweringKt.getEnumEntries(irClass2)) {
                    IrField correspondingField = enumClassCreateInitializerLowering.getCorrespondingField(irEnumEntry);
                    if (correspondingField != null && (initializerExpression = irEnumEntry.getInitializerExpression()) != null) {
                        IrExpression expression = initializerExpression.getExpression();
                        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, i, 0 == true ? 1 : 0);
                        IrVisitorsKt.acceptVoid(expression, deepCopySymbolRemapper);
                        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(expression.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunction);
                        if (irElementPatchDeclarationParents != null) {
                            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, null, correspondingField, (IrExpression) irElementPatchDeclarationParents, null, 8, null));
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                        }
                    }
                }
                IrClass irClassCompanionObject = AdditionalIrUtilsKt.companionObject(irClass2);
                if (irClassCompanionObject != null) {
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irGetObjectValue(irBlockBodyBuilder2, IrUtilsKt.getDefaultType(irClassCompanionObject), irClassCompanionObject.getSymbol()));
                }
                CollectionsKt.addAll(statements, irBlockBodyBuilder.doBuild().getStatements());
            }
        }));
        return irSimpleFunctionBuildFunction;
    }
}
