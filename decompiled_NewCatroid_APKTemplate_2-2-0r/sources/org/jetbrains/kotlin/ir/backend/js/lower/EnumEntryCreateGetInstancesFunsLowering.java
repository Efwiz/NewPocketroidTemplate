package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.PropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\nH\u0002J\u0018\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u001cH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR!\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0010\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumEntryCreateGetInstancesFunsLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "correspondingField", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getCorrespondingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setCorrespondingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "correspondingField$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "initEntryInstancesFun", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInitEntryInstancesFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "initEntryInstancesFun$delegate", "createGetEntryInstanceFun", "irClass", "enumEntry", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumEntryCreateGetInstancesFunsLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumEntryCreateGetInstancesFunsLowering.class), "correspondingField", "getCorrespondingField(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrField;")), Reflection.property2(new PropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumEntryCreateGetInstancesFunsLowering.class), "initEntryInstancesFun", "getInitEntryInstancesFun(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: correspondingField$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate correspondingField;

    /* JADX INFO: renamed from: initEntryInstancesFun$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate initEntryInstancesFun;

    public EnumEntryCreateGetInstancesFunsLowering(JsCommonBackendContext context) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction getInitEntryInstancesFun(IrClass irClass) {
        return (IrSimpleFunction) this.initEntryInstancesFun.getValue(irClass, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrEnumEntry)) {
            return null;
        }
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(declaration);
        if (!IrJsUtilsKt.isInstantiableEnum(parentAsClass)) {
            return null;
        }
        IrSimpleFunction irSimpleFunctionCreateGetEntryInstanceFun = createGetEntryInstanceFun(parentAsClass, (IrEnumEntry) declaration);
        irSimpleFunctionCreateGetEntryInstanceFun.setParent(parentAsClass.getParent());
        IrDeclarationParent parent = parentAsClass.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
        ((IrDeclarationContainer) parent).getDeclarations().add(irSimpleFunctionCreateGetEntryInstanceFun);
        return CollectionsKt.listOf(declaration);
    }

    private final IrSimpleFunction createGetEntryInstanceFun(final IrClass irClass, final IrEnumEntry enumEntry) {
        final IrSimpleFunction irSimpleFunction = (IrSimpleFunction) MappingsKt.getOrPut(this.context.getMapping().getEnumEntryToGetInstanceFun(), enumEntry, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.EnumEntryCreateGetInstancesFunsLowering.createGetEntryInstanceFun.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrFactory irFactory = EnumEntryCreateGetInstancesFunsLowering.this.getContext().getIrFactory();
                IrClass irClass2 = irClass;
                IrEnumEntry irEnumEntry = enumEntry;
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                String identifier = irClass2.getName().getIdentifier();
                Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                Name nameIdentifier = Name.identifier(EnumClassLoweringKt.createEntryAccessorName(identifier, irEnumEntry));
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setReturnType(EnumClassLoweringKt.getType(irEnumEntry, irClass2));
                irFunctionBuilder.setOrigin(JsLoweredDeclarationOrigin.ENUM_GET_INSTANCE_FUNCTION.INSTANCE);
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                irSimpleFunctionBuildFunction.setParent(irClass);
                return irSimpleFunctionBuildFunction;
            }
        });
        irSimpleFunction.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.EnumEntryCreateGetInstancesFunsLowering$createGetEntryInstanceFun$2$1
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
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.this$0.getContext(), irSimpleFunction.getSymbol(), 0, 0, 6, (Object) null);
                IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                EnumEntryCreateGetInstancesFunsLowering enumEntryCreateGetInstancesFunsLowering = this.this$0;
                IrClass irClass2 = irClass;
                IrEnumEntry irEnumEntry = enumEntry;
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunction2.getStartOffset(), irSimpleFunction2.getEndOffset());
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                IrSimpleFunction initEntryInstancesFun = enumEntryCreateGetInstancesFunsLowering.getInitEntryInstancesFun(irClass2);
                Intrinsics.checkNotNull(initEntryInstancesFun);
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irCall(irBlockBodyBuilder2, initEntryInstancesFun));
                IrField correspondingField = enumEntryCreateGetInstancesFunsLowering.getCorrespondingField(irEnumEntry);
                Intrinsics.checkNotNull(correspondingField);
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, null, correspondingField, null, 4, null)));
                CollectionsKt.addAll(statements, irBlockBodyBuilder.getIrBlockBody().getStatements());
            }
        }));
        return irSimpleFunction;
    }
}
