package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0011H\u0002J\u0018\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0011H\u0002J\u0018\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0011H\u0002J\u0018\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020!\u0018\u00010 2\u0006\u0010\"\u001a\u00020!H\u0016J\u0014\u0010#\u001a\u00020$*\u00020%2\u0006\u0010\u0019\u001a\u00020\u0011H\u0002J\f\u0010&\u001a\u00020'*\u00020\u0011H\u0002J\f\u0010(\u001a\u00020\u0018*\u00020\u0011H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR!\u0010\u0010\u001a\u0004\u0018\u00010\n*\u00020\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0012\u0010\u0013¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumSyntheticFunctionsAndPropertiesLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "throwIAESymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getInstanceFun", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getGetInstanceFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getInstanceFun$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "initEntryInstancesFun", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInitEntryInstancesFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "initEntryInstancesFun$delegate", "createEnumEntriesBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "entriesGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "enumClass", "createEnumValueOfBody", "valueOfFun", "irClass", "createEnumValuesBody", "valuesFun", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "arrayOfEnumEntriesOf", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrVarargImpl;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "buildEntriesField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "searchForValuesFunction", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumSyntheticFunctionsAndPropertiesLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property2(new PropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumSyntheticFunctionsAndPropertiesLowering.class), "getInstanceFun", "getGetInstanceFun(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;")), Reflection.property2(new PropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumSyntheticFunctionsAndPropertiesLowering.class), "initEntryInstancesFun", "getInitEntryInstancesFun(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: getInstanceFun$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate getInstanceFun;

    /* JADX INFO: renamed from: initEntryInstancesFun$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate initEntryInstancesFun;
    private final IrSimpleFunctionSymbol throwIAESymbol;

    public EnumSyntheticFunctionsAndPropertiesLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.getInstanceFun = context.getMapping().getEnumEntryToGetInstanceFun();
        this.initEntryInstancesFun = context.getMapping().getEnumClassToInitEntryInstancesFun();
        this.throwIAESymbol = context.getIr().getSymbols().getThrowIAE();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final IrSimpleFunction getGetInstanceFun(IrEnumEntry irEnumEntry) {
        return (IrSimpleFunction) this.getInstanceFun.getValue(irEnumEntry, $$delegatedProperties[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction getInitEntryInstancesFun(IrClass irClass) {
        return (IrSimpleFunction) this.initEntryInstancesFun.getValue(irClass, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(final IrDeclaration declaration) {
        IrClass parentClassOrNull;
        final IrClass parentEnumClassOrNull;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrConstructor) {
            IrConstructor irConstructor = (IrConstructor) declaration;
            if (irConstructor.getIsPrimary() && IrJsUtilsKt.getParentEnumClassOrNull(declaration) != null && (parentClassOrNull = IrUtilsKt.getParentClassOrNull(declaration)) != null && parentClassOrNull.getIsCompanion()) {
                IrBody body = irConstructor.getBody();
                final IrSyntheticBody irSyntheticBody = body instanceof IrSyntheticBody ? (IrSyntheticBody) body : null;
                if (irSyntheticBody != null && (parentEnumClassOrNull = IrJsUtilsKt.getParentEnumClassOrNull(declaration)) != null) {
                    irConstructor.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.EnumSyntheticFunctionsAndPropertiesLowering$transformFlat$1$1$1
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
                            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.this$0.getContext(), ((IrConstructor) declaration).getSymbol(), 0, 0, 6, (Object) null);
                            EnumSyntheticFunctionsAndPropertiesLowering enumSyntheticFunctionsAndPropertiesLowering = this.this$0;
                            IrClass irClass = parentEnumClassOrNull;
                            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                            IrSimpleFunction initEntryInstancesFun = enumSyntheticFunctionsAndPropertiesLowering.getInitEntryInstancesFun(irClass);
                            Intrinsics.checkNotNull(initEntryInstancesFun);
                            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder, initEntryInstancesFun.getSymbol()));
                            CollectionsKt.addAll(statements, MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irBlockBodyBuilder.getIrBlockBody().getStatements(), (List) IrUtilsKt.getStatements(irSyntheticBody)));
                        }
                    }));
                }
            }
        }
        if (declaration instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) declaration;
            IrBody body2 = irSimpleFunction.getBody();
            IrSyntheticBody irSyntheticBody2 = body2 instanceof IrSyntheticBody ? (IrSyntheticBody) body2 : null;
            if (irSyntheticBody2 != null) {
                final IrSyntheticBodyKind kind = irSyntheticBody2.getKind();
                final IrClass parentEnumClassOrNull2 = IrJsUtilsKt.getParentEnumClassOrNull(declaration);
                if (parentEnumClassOrNull2 != null) {
                    irSimpleFunction.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.EnumSyntheticFunctionsAndPropertiesLowering$transformFlat$2$1$1

                        /* JADX INFO: compiled from: EnumClassLowering.kt */
                        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                        public /* synthetic */ class WhenMappings {
                            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                            static {
                                int[] iArr = new int[IrSyntheticBodyKind.values().length];
                                try {
                                    iArr[IrSyntheticBodyKind.ENUM_VALUES.ordinal()] = 1;
                                } catch (NoSuchFieldError unused) {
                                }
                                try {
                                    iArr[IrSyntheticBodyKind.ENUM_VALUEOF.ordinal()] = 2;
                                } catch (NoSuchFieldError unused2) {
                                }
                                try {
                                    iArr[IrSyntheticBodyKind.ENUM_ENTRIES.ordinal()] = 3;
                                } catch (NoSuchFieldError unused3) {
                                }
                                $EnumSwitchMapping$0 = iArr;
                            }
                        }

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
                            IrBlockBody irBlockBodyCreateEnumValuesBody;
                            Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                            List<IrStatement> statements = createBlockBody.getStatements();
                            int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
                            if (i == 1) {
                                irBlockBodyCreateEnumValuesBody = this.createEnumValuesBody((IrFunction) declaration, parentEnumClassOrNull2);
                            } else if (i == 2) {
                                irBlockBodyCreateEnumValuesBody = this.createEnumValueOfBody((IrFunction) declaration, parentEnumClassOrNull2);
                            } else {
                                if (i != 3) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                irBlockBodyCreateEnumValuesBody = this.createEnumEntriesBody((IrFunction) declaration, parentEnumClassOrNull2);
                            }
                            CollectionsKt.addAll(statements, irBlockBodyCreateEnumValuesBody.getStatements());
                        }
                    }));
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrBlockBody createEnumEntriesBody(IrFunction entriesGetter, IrClass enumClass) {
        IrField irFieldBuildEntriesField = buildEntriesField(enumClass);
        IrFunction irFunctionSearchForValuesFunction = searchForValuesFunction(enumClass);
        IrSimpleFunctionSymbol createEnumEntries = this.context.getCreateEnumEntries();
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, entriesGetter.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrExpression irExpressionIrEqualsNull = ExpressionHelpersKt.irEqualsNull(irBlockBodyBuilder2, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, null, irFieldBuildEntriesField, null, 4, null));
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, createEnumEntries);
        irCall.putValueArgument(0, ExpressionHelpersKt.irCall(irBlockBodyBuilder2, irFunctionSearchForValuesFunction));
        Unit unit = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(LowerUtilsKt.irIfThen(irBlockBodyBuilder2, irExpressionIrEqualsNull, ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, null, irFieldBuildEntriesField, irCall, null, 8, null)));
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, null, irFieldBuildEntriesField, null, 4, null)));
        return irBlockBodyBuilder.getIrBlockBody();
    }

    private final IrFunction searchForValuesFunction(IrClass irClass) {
        for (Object obj : irClass.getDeclarations()) {
            IrDeclaration irDeclaration = (IrDeclaration) obj;
            if (irDeclaration instanceof IrFunction) {
                IrFunction irFunction = (IrFunction) irDeclaration;
                if (IrUtilsKt.isStatic(irFunction) && IrTypePredicatesKt.isArray(irFunction.getReturnType())) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
                    return (IrFunction) obj;
                }
            }
        }
        obj = null;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
        return (IrFunction) obj;
    }

    private final IrField buildEntriesField(IrClass irClass) {
        JsCommonBackendContext jsCommonBackendContext = this.context;
        IrFactory factory = irClass.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        Name nameIdentifier = Name.identifier("$ENTRIES");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFieldBuilder.setName(nameIdentifier);
        irFieldBuilder.setType(IrTypesKt.getDefaultType(jsCommonBackendContext.getEnumEntries()));
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFieldBuilder.setVisibility(PRIVATE);
        irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRIES.INSTANCE);
        irFieldBuilder.setFinal(true);
        irFieldBuilder.setStatic(true);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        irClass.getDeclarations().add(irFieldBuildField);
        return irFieldBuildField;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrBlockBody createEnumValueOfBody(IrFunction valueOfFun, IrClass irClass) {
        IrValueParameter irValueParameter = valueOfFun.getValueParameters().get(0);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, valueOfFun.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(irClass);
        List<IrEnumEntry> enumEntries = EnumClassLoweringKt.getEnumEntries(irClass);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(enumEntries, 10));
        for (IrEnumEntry irEnumEntry : enumEntries) {
            IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameter);
            String identifier = irEnumEntry.getName().getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            IrExpression irExpressionIrEquals$default = ExpressionHelpersKt.irEquals$default(irBlockBodyBuilder2, irGetValueImplIrGet, ExpressionHelpersKt.irString(irBlockBodyBuilder2, identifier), null, 4, null);
            IrSimpleFunction getInstanceFun = getGetInstanceFun(irEnumEntry);
            Intrinsics.checkNotNull(getInstanceFun);
            arrayList.add(ExpressionHelpersKt.irBranch(irBlockBodyBuilder2, irExpressionIrEquals$default, ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irCall(irBlockBodyBuilder2, getInstanceFun))));
        }
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBlockBodyBuilder2.getContext(), irBlockBodyBuilder2.getScope(), irBlockBodyBuilder2.getStartOffset(), irBlockBodyBuilder2.getEndOffset(), null, null, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrSimpleFunction initEntryInstancesFun = getInitEntryInstancesFun(irClass);
        Intrinsics.checkNotNull(initEntryInstancesFun);
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irCall(irBlockBuilder2, initEntryInstancesFun));
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, this.throwIAESymbol);
        irCall.putValueArgument(0, ExpressionHelpersKt.irString(irBlockBuilder2, "No enum constant " + irValueParameter.getName().getIdentifier() + '.'));
        irBlockBuilder.unaryPlus(irCall);
        Unit unit = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irWhen(irBlockBodyBuilder2, defaultType, MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(arrayList, ExpressionHelpersKt.irElseBranch(irBlockBodyBuilder2, irBlockBuilder.getIrBlockBody()))));
        return irBlockBodyBuilder.getIrBlockBody();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrBlockBody createEnumValuesBody(IrFunction valuesFun, IrClass irClass) {
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, valuesFun.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, arrayOfEnumEntriesOf(irBlockBodyBuilder2, irClass)));
        return irBlockBodyBuilder.getIrBlockBody();
    }

    private final IrVarargImpl arrayOfEnumEntriesOf(IrBuilderWithScope irBuilderWithScope, IrClass irClass) {
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(irClass);
        List enumEntries = EnumClassLoweringKt.getEnumEntries(irClass);
        ArrayList arrayList = new ArrayList(enumEntries.size());
        Iterator it = enumEntries.iterator();
        while (it.hasNext()) {
            IrSimpleFunction getInstanceFun = getGetInstanceFun((IrEnumEntry) it.next());
            Intrinsics.checkNotNull(getInstanceFun);
            arrayList.add(ExpressionHelpersKt.irCall(irBuilderWithScope, getInstanceFun));
        }
        return ExpressionHelpersKt.irVararg(irBuilderWithScope, defaultType, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
    }
}
