package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: EnumExternalEntriesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u0001\u0014B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0018\u00010\u0007R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/EnumExternalEntriesLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "state", "Lorg/jetbrains/kotlin/backend/jvm/lower/EnumExternalEntriesLowering$EntriesMappingState;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "EntriesMappingState", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumExternalEntriesLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final JvmBackendContext context;
    private EntriesMappingState state;

    public EnumExternalEntriesLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        if (this.context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.EnumEntries)) {
            IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
        }
    }

    /* JADX INFO: compiled from: EnumExternalEntriesLowering.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0005R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/EnumExternalEntriesLowering$EntriesMappingState;", "", "(Lorg/jetbrains/kotlin/backend/jvm/lower/EnumExternalEntriesLowering;)V", "mappings", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getMappings", "()Ljava/util/Map;", "mappingsClass", "getMappingsClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "mappingsClass$delegate", "Lkotlin/Lazy;", "getEntriesFieldForEnum", "enumClass", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class EntriesMappingState {
        private final Map<IrClass, IrField> mappings = new LinkedHashMap();

        /* JADX INFO: renamed from: mappingsClass$delegate, reason: from kotlin metadata */
        private final Lazy mappingsClass;

        public EntriesMappingState() {
            this.mappingsClass = LazyKt.lazy(new Function0<IrClass>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.EnumExternalEntriesLowering$EntriesMappingState$mappingsClass$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClass invoke() {
                    IrFactory irFactory = enumExternalEntriesLowering.context.getIrFactory();
                    IrClassBuilder irClassBuilder = new IrClassBuilder();
                    Name nameIdentifier = Name.identifier("EntriesMappings");
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    irClassBuilder.setName(nameIdentifier);
                    irClassBuilder.setOrigin(JvmLoweredDeclarationOrigin.ENUM_MAPPINGS_FOR_ENTRIES.INSTANCE);
                    IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    return irClassBuildClass;
                }
            });
        }

        public final Map<IrClass, IrField> getMappings() {
            return this.mappings;
        }

        public final IrClass getMappingsClass() {
            return (IrClass) this.mappingsClass.getValue();
        }

        public final IrField getEntriesFieldForEnum(IrClass enumClass) {
            Intrinsics.checkNotNullParameter(enumClass, "enumClass");
            Map<IrClass, IrField> map = this.mappings;
            EnumExternalEntriesLowering enumExternalEntriesLowering = EnumExternalEntriesLowering.this;
            IrField irField = map.get(enumClass);
            if (irField == null) {
                IrClass mappingsClass = getMappingsClass();
                IrFactory factory = mappingsClass.getFactory();
                IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                Name nameIdentifier = Name.identifier("entries$" + this.mappings.size());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFieldBuilder.setName(nameIdentifier);
                irFieldBuilder.setType(IrTypesKt.typeWith(enumExternalEntriesLowering.context.getIr().getSymbols().getEnumEntries(), IrUtilsKt.getDefaultType(enumClass)));
                irFieldBuilder.setOrigin(JvmLoweredDeclarationOrigin.ENUM_MAPPINGS_FOR_ENTRIES.INSTANCE);
                irFieldBuilder.setFinal(true);
                irFieldBuilder.setStatic(true);
                IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
                irFieldBuildField.setParent(mappingsClass);
                mappingsClass.getDeclarations().add(irFieldBuildField);
                map.put(enumClass, irFieldBuildField);
                irField = irFieldBuildField;
            }
            return irField;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? owner : null;
        IrDeclarationParent parent = irSimpleFunction != null ? irSimpleFunction.getParent() : null;
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass == null) {
            return super.visitCall(expression);
        }
        if (!Intrinsics.areEqual(irSimpleFunction.getName(), SpecialNames.ENUM_GET_ENTRIES) || !JvmIrUtilsKt.isEnumClassWhichRequiresExternalEntries(irClass)) {
            return super.visitCall(expression);
        }
        EntriesMappingState entriesMappingState = this.state;
        Intrinsics.checkNotNull(entriesMappingState);
        IrField entriesFieldForEnum = entriesMappingState.getEntriesFieldForEnum(irClass);
        return new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), entriesFieldForEnum.getSymbol(), entriesFieldForEnum.getType(), null, null, 48, null);
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        EntriesMappingState entriesMappingState = this.state;
        EntriesMappingState entriesMappingState2 = new EntriesMappingState();
        this.state = entriesMappingState2;
        super.visitClassNew(declaration);
        for (Map.Entry<IrClass, IrField> entry : entriesMappingState2.getMappings().entrySet()) {
            IrClass key = entry.getKey();
            IrField value = entry.getValue();
            IrSimpleFunction irSimpleFunctionFindEnumValuesFunction = JvmIrUtilsKt.findEnumValuesFunction(key, this.context);
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, value.getSymbol(), 0, 0, 6, (Object) null);
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilderCreateIrBuilder$default, this.context.getIr().getSymbols().getCreateEnumEntries());
            irCall.putValueArgument(0, ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, irSimpleFunctionFindEnumValuesFunction));
            value.setInitializer(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default, irCall));
        }
        if (!entriesMappingState2.getMappings().isEmpty()) {
            List<IrDeclaration> declarations = declaration.getDeclarations();
            IrClass mappingsClass = entriesMappingState2.getMappingsClass();
            mappingsClass.setParent(declaration);
            declarations.add(mappingsClass);
        }
        this.state = entriesMappingState;
        return declaration;
    }
}
