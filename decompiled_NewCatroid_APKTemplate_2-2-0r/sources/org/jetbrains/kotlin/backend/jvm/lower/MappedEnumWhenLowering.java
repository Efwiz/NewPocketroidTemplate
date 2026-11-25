package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.EnumWhenLowering;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.when.WhenByEnumsMapping;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetEnumValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: MappedEnumWhenLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001:\u0002\u001f B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0017H\u0014J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0018\u00010\u0011R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/MappedEnumWhenLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/EnumWhenLowering;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "intArray", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "intArrayConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "intArrayGet", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "intArraySet", "refArraySize", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "state", "Lorg/jetbrains/kotlin/backend/jvm/lower/MappedEnumWhenLowering$EnumMappingState;", "mapConstEnumEntry", "", "entry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "mapRuntimeEnumEntry", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "subject", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "EnumMappingClass", "EnumMappingState", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MappedEnumWhenLowering extends EnumWhenLowering {
    private final JvmBackendContext context;
    private final IrClassSymbol intArray;
    private final IrConstructorSymbol intArrayConstructor;
    private final IrSimpleFunctionSymbol intArrayGet;
    private final IrSimpleFunctionSymbol intArraySet;
    private final IrSimpleFunction refArraySize;
    private EnumMappingState state;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MappedEnumWhenLowering(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        IrClassSymbol irClassSymbol = (IrClassSymbol) MapsKt.getValue(getContext().getIrBuiltIns().getPrimitiveArrayForType(), getContext().getIrBuiltIns().getIntType());
        this.intArray = irClassSymbol;
        IrProperty irProperty = null;
        boolean z = false;
        IrConstructorSymbol irConstructorSymbol = null;
        boolean z2 = false;
        for (IrConstructorSymbol irConstructorSymbol2 : IrUtilsKt.getConstructors(irClassSymbol)) {
            if (irConstructorSymbol2.getOwner().getValueParameters().size() == 1) {
                if (z2) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irConstructorSymbol = irConstructorSymbol2;
                z2 = true;
            }
        }
        if (z2) {
            this.intArrayConstructor = irConstructorSymbol;
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
            boolean z3 = false;
            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(this.intArray)) {
                if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName(), OperatorNameConventions.GET)) {
                    if (z3) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    z3 = true;
                    irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                }
            }
            if (z3) {
                this.intArrayGet = irSimpleFunctionSymbol;
                IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = null;
                boolean z4 = false;
                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol4 : IrUtilsKt.getFunctions(this.intArray)) {
                    if (Intrinsics.areEqual(irSimpleFunctionSymbol4.getOwner().getName(), OperatorNameConventions.SET)) {
                        if (z4) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z4 = true;
                        irSimpleFunctionSymbol3 = irSimpleFunctionSymbol4;
                    }
                }
                if (z4) {
                    this.intArraySet = irSimpleFunctionSymbol3;
                    for (IrProperty irProperty2 : IrUtilsKt.getProperties(getContext().getIrBuiltIns().getArrayClass().getOwner())) {
                        if (Intrinsics.areEqual(irProperty2.getName().toString(), "size")) {
                            if (z) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            irProperty = irProperty2;
                            z = true;
                        }
                    }
                    if (z) {
                        IrSimpleFunction getter = irProperty.getGetter();
                        Intrinsics.checkNotNull(getter);
                        this.refArraySize = getter;
                        return;
                    }
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.lower.EnumWhenLowering
    public JvmBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: compiled from: MappedEnumWhenLowering.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/MappedEnumWhenLowering$EnumMappingClass;", "", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "ordinals", "", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "", "isPublicAbi", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;Ljava/util/Map;Z)V", "getField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "()Z", "setPublicAbi", "(Z)V", "getOrdinals", "()Ljava/util/Map;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class EnumMappingClass {
        private final IrField field;
        private boolean isPublicAbi;
        private final Map<IrEnumEntry, Integer> ordinals;

        public EnumMappingClass(IrField field, Map<IrEnumEntry, Integer> ordinals, boolean z) {
            Intrinsics.checkNotNullParameter(field, "field");
            Intrinsics.checkNotNullParameter(ordinals, "ordinals");
            this.field = field;
            this.ordinals = ordinals;
            this.isPublicAbi = z;
        }

        public final IrField getField() {
            return this.field;
        }

        public /* synthetic */ EnumMappingClass(IrField irField, LinkedHashMap linkedHashMap, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(irField, (i & 2) != 0 ? new LinkedHashMap() : linkedHashMap, (i & 4) != 0 ? false : z);
        }

        public final Map<IrEnumEntry, Integer> getOrdinals() {
            return this.ordinals;
        }

        /* JADX INFO: renamed from: isPublicAbi, reason: from getter */
        public final boolean getIsPublicAbi() {
            return this.isPublicAbi;
        }

        public final void setPublicAbi(boolean z) {
            this.isPublicAbi = z;
        }
    }

    /* JADX INFO: compiled from: MappedEnumWhenLowering.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\f\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/MappedEnumWhenLowering$EnumMappingState;", "", "(Lorg/jetbrains/kotlin/backend/jvm/lower/MappedEnumWhenLowering;)V", "isPublicAbi", "", "()Z", "mappings", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/backend/jvm/lower/MappedEnumWhenLowering$EnumMappingClass;", "getMappings", "()Ljava/util/Map;", "mappingsClass", "getMappingsClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "mappingsClass$delegate", "Lkotlin/Lazy;", "getMappingForClass", "enumClass", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class EnumMappingState {
        private final Map<IrClass, EnumMappingClass> mappings = new LinkedHashMap();

        /* JADX INFO: renamed from: mappingsClass$delegate, reason: from kotlin metadata */
        private final Lazy mappingsClass;

        public EnumMappingState() {
            this.mappingsClass = LazyKt.lazy(new Function0<IrClass>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.MappedEnumWhenLowering$EnumMappingState$mappingsClass$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClass invoke() {
                    IrFactory irFactory = mappedEnumWhenLowering.getContext().getIrFactory();
                    IrClassBuilder irClassBuilder = new IrClassBuilder();
                    Name nameIdentifier = Name.identifier("WhenMappings");
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    irClassBuilder.setName(nameIdentifier);
                    irClassBuilder.setOrigin(JvmLoweredDeclarationOrigin.ENUM_MAPPINGS_FOR_WHEN.INSTANCE);
                    IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    return irClassBuildClass;
                }
            });
        }

        public final Map<IrClass, EnumMappingClass> getMappings() {
            return this.mappings;
        }

        public final IrClass getMappingsClass() {
            return (IrClass) this.mappingsClass.getValue();
        }

        public final EnumMappingClass getMappingForClass(IrClass enumClass) {
            Intrinsics.checkNotNullParameter(enumClass, "enumClass");
            Map<IrClass, EnumMappingClass> map = this.mappings;
            MappedEnumWhenLowering mappedEnumWhenLowering = MappedEnumWhenLowering.this;
            EnumMappingClass enumMappingClass = map.get(enumClass);
            if (enumMappingClass == null) {
                IrClass mappingsClass = getMappingsClass();
                IrFactory factory = mappingsClass.getFactory();
                IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                Name nameIdentifier = Name.identifier(WhenByEnumsMapping.MAPPING_ARRAY_FIELD_PREFIX + this.mappings.size());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFieldBuilder.setName(nameIdentifier);
                irFieldBuilder.setType(IrTypesKt.getDefaultType(mappedEnumWhenLowering.intArray));
                irFieldBuilder.setOrigin(JvmLoweredDeclarationOrigin.ENUM_MAPPINGS_FOR_WHEN.INSTANCE);
                irFieldBuilder.setFinal(true);
                irFieldBuilder.setStatic(true);
                IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
                irFieldBuildField.setParent(mappingsClass);
                mappingsClass.getDeclarations().add(irFieldBuildField);
                enumMappingClass = new EnumMappingClass(irFieldBuildField, null, false, 6, null);
                map.put(enumClass, enumMappingClass);
            }
            return enumMappingClass;
        }

        public final boolean isPublicAbi() {
            Collection<EnumMappingClass> collectionValues = this.mappings.values();
            if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                return false;
            }
            Iterator<T> it = collectionValues.iterator();
            while (it.hasNext()) {
                if (((EnumMappingClass) it.next()).getIsPublicAbi()) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.EnumWhenLowering
    protected int mapConstEnumEntry(IrEnumEntry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        EnumMappingState enumMappingState = this.state;
        Intrinsics.checkNotNull(enumMappingState);
        Map<IrEnumEntry, Integer> ordinals = enumMappingState.getMappingForClass(IrUtilsKt.getParentAsClass(entry)).getOrdinals();
        Integer numValueOf = ordinals.get(entry);
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(ordinals.size() + 1);
            ordinals.put(entry, numValueOf);
        }
        return numValueOf.intValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.EnumWhenLowering
    protected IrExpression mapRuntimeEnumEntry(IrBuilderWithScope builder, IrExpression subject) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(subject, "subject");
        IrCall irCall = ExpressionHelpersKt.irCall(builder, this.intArrayGet);
        EnumMappingState enumMappingState = this.state;
        Intrinsics.checkNotNull(enumMappingState);
        IrClass irClass = IrTypesKt.getClass(subject.getType());
        Intrinsics.checkNotNull(irClass);
        EnumMappingClass mappingForClass = enumMappingState.getMappingForClass(irClass);
        boolean z = true;
        if (!mappingForClass.getIsPublicAbi()) {
            IrSymbolOwner owner = builder.getScope().getScopeOwnerSymbol().getOwner();
            IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
            if (irDeclaration == null || !JvmIrInlineUtilsKt.isInPublicInlineScope(irDeclaration)) {
                z = false;
            }
        }
        mappingForClass.setPublicAbi(z);
        irCall.setDispatchReceiver(ExpressionHelpersKt.irGetField$default(builder, null, mappingForClass.getField(), null, 4, null));
        irCall.putValueArgument(0, super.mapRuntimeEnumEntry(builder, subject));
        return irCall;
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        EnumMappingState enumMappingState = this.state;
        EnumMappingState enumMappingState2 = new EnumMappingState();
        this.state = enumMappingState2;
        super.visitClassNew(declaration);
        Iterator<Map.Entry<IrClass, EnumMappingClass>> it = enumMappingState2.getMappings().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<IrClass, EnumMappingClass> next = it.next();
            IrClass key = next.getKey();
            EnumMappingClass value = next.getValue();
            IrSimpleFunction irSimpleFunctionFindEnumValuesFunction = JvmIrUtilsKt.findEnumValuesFunction(key, getContext());
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(getContext(), value.getField().getSymbol(), 0, 0, 6, (Object) null);
            IrField field = value.getField();
            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
            Iterator<Map.Entry<IrClass, EnumMappingClass>> it2 = it;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset(), null, null, false, 64, null);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder2, this.refArraySize);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irCall(irBlockBuilder2, irSimpleFunctionFindEnumValuesFunction));
            IrConstructorCall irConstructorCallIrCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, this.intArrayConstructor);
            irConstructorCallIrCall.putValueArgument(0, irFunctionAccessExpressionIrCall);
            IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, irConstructorCallIrCall, null, null, false, null, 30, null);
            for (Map.Entry<IrEnumEntry, Integer> entry : value.getOrdinals().entrySet()) {
                IrEnumEntry key2 = entry.getKey();
                int iIntValue = entry.getValue().intValue();
                IrGetEnumValueImpl irGetEnumValueImpl = new IrGetEnumValueImpl(-1, -1, IrUtilsKt.getDefaultType(key), key2.getSymbol());
                IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, this.intArraySet);
                irCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default));
                irCall.putValueArgument(0, super.mapRuntimeEnumEntry(declarationIrBuilder, irGetEnumValueImpl));
                irCall.putValueArgument(1, ExpressionHelpersKt.irInt$default(irBlockBuilder2, iIntValue, null, 2, null));
                irBlockBuilder.unaryPlus(ExpressionHelpersKt.irTry(irBlockBuilder2, irBlockBuilder.getContext().getIrBuiltIns().getUnitType(), irCall, CollectionsKt.listOf(LowerUtilsKt.irCatch(irBlockBuilder2, Scope.createTemporaryVariableDeclaration$default(irBlockBuilder.getScope(), getContext().getIr().getSymbols().getNoSuchFieldErrorType(), null, false, null, -1, -1, 14, null), ExpressionHelpersKt.irUnit(irBlockBuilder2))), null));
            }
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default));
            Unit unit = Unit.INSTANCE;
            field.setInitializer(ExpressionHelpersKt.irExprBody(declarationIrBuilder, irBlockBuilder.getIrBlockBody()));
            it = it2;
        }
        if (!enumMappingState2.getMappings().isEmpty()) {
            List<IrDeclaration> declarations = declaration.getDeclarations();
            IrClass mappingsClass = enumMappingState2.getMappingsClass();
            mappingsClass.setParent(declaration);
            if (enumMappingState2.isPublicAbi()) {
                getContext().getPublicAbiSymbols().add(mappingsClass.getSymbol());
            }
            declarations.add(mappingsClass);
        }
        this.state = enumMappingState;
        return declaration;
    }
}
