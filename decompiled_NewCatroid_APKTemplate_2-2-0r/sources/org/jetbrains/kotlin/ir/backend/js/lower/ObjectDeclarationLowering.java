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
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ObjectLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020!\u0018\u00010 2\u0006\u0010\"\u001a\u00020!H\u0016J\u0014\u0010#\u001a\u00020$*\u00020%2\u0006\u0010\u0012\u001a\u00020\u0011H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR3\u0010\u0012\u001a\u0004\u0018\u00010\u0011*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00118B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0017\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R3\u0010\u0019\u001a\u0004\u0018\u00010\u0018*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00188B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\u0010\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ObjectDeclarationLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "initEntryInstancesFun", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInitEntryInstancesFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setInitEntryInstancesFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "initEntryInstancesFun$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "instanceField", "getInstanceField", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setInstanceField", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "instanceField$delegate", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "syntheticPrimaryConstructor", "getSyntheticPrimaryConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setSyntheticPrimaryConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "syntheticPrimaryConstructor$delegate", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "irNullabilityCheck", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectDeclarationLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(ObjectDeclarationLowering.class), "instanceField", "getInstanceField(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrField;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(ObjectDeclarationLowering.class), "syntheticPrimaryConstructor", "getSyntheticPrimaryConstructor(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(ObjectDeclarationLowering.class), "initEntryInstancesFun", "getInitEntryInstancesFun(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: initEntryInstancesFun$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate initEntryInstancesFun;

    /* JADX INFO: renamed from: instanceField$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate instanceField;

    /* JADX INFO: renamed from: syntheticPrimaryConstructor$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate syntheticPrimaryConstructor;

    public ObjectDeclarationLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.instanceField = context.getMapping().getObjectToInstanceField();
        this.syntheticPrimaryConstructor = context.getMapping().getClassToSyntheticPrimaryConstructor();
        this.initEntryInstancesFun = context.getMapping().getEnumClassToInitEntryInstancesFun();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final void setInstanceField(IrClass irClass, IrField irField) {
        this.instanceField.setValue(irClass, $$delegatedProperties[0], irField);
    }

    private final IrConstructor getSyntheticPrimaryConstructor(IrClass irClass) {
        return (IrConstructor) this.syntheticPrimaryConstructor.getValue(irClass, $$delegatedProperties[1]);
    }

    private final IrSimpleFunction getInitEntryInstancesFun(IrClass irClass) {
        return (IrSimpleFunction) this.initEntryInstancesFun.getValue(irClass, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        final IrConstructor irConstructor;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrClass) {
            IrClass irClass = (IrClass) declaration;
            if (irClass.getKind() == ClassKind.OBJECT && !IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) declaration)) {
                final IrSimpleFunction orCreateGetInstanceFunction = ObjectLoweringKt.getOrCreateGetInstanceFunction(this.context, irClass);
                IrFactory irFactory = this.context.getIrFactory();
                IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                Name nameIdentifier = Name.identifier(irClass.getName().asString() + Namer.OBJECT_INSTANCE_VAR_SUFFIX);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFieldBuilder.setName(nameIdentifier);
                irFieldBuilder.setType(IrTypesKt.makeNullable(IrUtilsKt.getDefaultType(irClass)));
                irFieldBuilder.setStatic(true);
                irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE);
                final IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
                irFieldBuildField.setParent(declaration.getParent());
                irFieldBuildField.setInitializer(null);
                setInstanceField(irClass, irFieldBuildField);
                IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass);
                if (primaryConstructor == null) {
                    IrConstructor syntheticPrimaryConstructor = getSyntheticPrimaryConstructor(irClass);
                    Intrinsics.checkNotNull(syntheticPrimaryConstructor);
                    irConstructor = syntheticPrimaryConstructor;
                } else {
                    irConstructor = primaryConstructor;
                }
                IrDeclarationParent parent = declaration.getParent();
                if (!(parent instanceof IrClass)) {
                    parent = null;
                }
                IrClass irClass2 = (IrClass) parent;
                final IrSimpleFunction initEntryInstancesFun = irClass2 != null ? getInitEntryInstancesFun(irClass2) : null;
                orCreateGetInstanceFunction.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ObjectDeclarationLowering.transformFlat.1
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
                        List<IrStatement> statements = createBlockBody.getStatements();
                        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(ObjectDeclarationLowering.this.getContext(), orCreateGetInstanceFunction.getSymbol(), 0, 0, 6, (Object) null);
                        IrSimpleFunction irSimpleFunction = orCreateGetInstanceFunction;
                        IrSimpleFunction irSimpleFunction2 = initEntryInstancesFun;
                        ObjectDeclarationLowering objectDeclarationLowering = ObjectDeclarationLowering.this;
                        IrField irField = irFieldBuildField;
                        IrConstructor irConstructor2 = irConstructor;
                        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset());
                        if (irSimpleFunction2 != null) {
                            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irCall(irBlockBodyBuilder, irSimpleFunction2));
                        }
                        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                        irBlockBodyBuilder.unaryPlus(LowerUtilsKt.irIfThen(irBlockBodyBuilder2, objectDeclarationLowering.irNullabilityCheck(irBlockBodyBuilder2, irField), ExpressionHelpersKt.irCallConstructor(irBlockBodyBuilder2, irConstructor2.getSymbol(), CollectionsKt.emptyList())));
                        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, null, irField, null, 4, null)));
                        CollectionsKt.addAll(statements, irBlockBodyBuilder.getIrBlockBody().getStatements());
                    }
                }));
                return CollectionsKt.listOf((Object[]) new IrDeclaration[]{declaration, irFieldBuildField, orCreateGetInstanceFunction});
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression irNullabilityCheck(IrBuilderWithScope irBuilderWithScope, IrField irField) {
        JsCommonBackendContext jsCommonBackendContext = this.context;
        if ((jsCommonBackendContext instanceof JsIrBackendContext) && jsCommonBackendContext.getEs6mode()) {
            return ExpressionHelpersKt.irEqeqeqWithoutBox(irBuilderWithScope, ExpressionHelpersKt.irGetField$default(irBuilderWithScope, null, irField, null, 4, null), IrJsUtilsKt.getVoid((JsIrBackendContext) jsCommonBackendContext));
        }
        return ExpressionHelpersKt.irEqualsNull(irBuilderWithScope, ExpressionHelpersKt.irGetField$default(irBuilderWithScope, null, irField, null, 4, null));
    }
}
