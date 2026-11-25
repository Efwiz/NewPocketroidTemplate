package org.jetbrains.kotlin.backend.jvm.codegen;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.EnumEntriesIntrinsicMappingsCache;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: EnumEntriesIntrinsicMappingsCacheImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\b\u0012\u00060\bR\u00020\u00000\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/EnumEntriesIntrinsicMappingsCacheImpl;", "Lorg/jetbrains/kotlin/backend/jvm/EnumEntriesIntrinsicMappingsCache;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "storage", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/EnumEntriesIntrinsicMappingsCacheImpl$MappingsClass;", "generateMappingsClasses", "", "getEnumEntriesIntrinsicMappings", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "containingClass", "enumClass", "MappingsClass", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumEntriesIntrinsicMappingsCacheImpl extends EnumEntriesIntrinsicMappingsCache {
    private final JvmBackendContext context;
    private final Map<IrClass, MappingsClass> storage;

    public EnumEntriesIntrinsicMappingsCacheImpl(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.storage = new LinkedHashMap();
    }

    /* JADX INFO: compiled from: EnumEntriesIntrinsicMappingsCacheImpl.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R-\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t0\bj\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t`\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/EnumEntriesIntrinsicMappingsCacheImpl$MappingsClass;", "", "containingClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/EnumEntriesIntrinsicMappingsCacheImpl;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "getContainingClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "enums", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lkotlin/collections/HashMap;", "getEnums", "()Ljava/util/HashMap;", "irClass", "getIrClass", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class MappingsClass {
        private final IrClass containingClass;
        private final HashMap<IrClass, IrField> enums;
        private final IrClass irClass;
        final /* synthetic */ EnumEntriesIntrinsicMappingsCacheImpl this$0;

        public MappingsClass(EnumEntriesIntrinsicMappingsCacheImpl enumEntriesIntrinsicMappingsCacheImpl, IrClass containingClass) {
            Intrinsics.checkNotNullParameter(containingClass, "containingClass");
            this.this$0 = enumEntriesIntrinsicMappingsCacheImpl;
            this.containingClass = containingClass;
            IrFactory irFactory = enumEntriesIntrinsicMappingsCacheImpl.context.getIrFactory();
            IrClassBuilder irClassBuilder = new IrClassBuilder();
            Name nameIdentifier = Name.identifier("EntriesIntrinsicMappings");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irClassBuilder.setName(nameIdentifier);
            irClassBuilder.setOrigin(JvmLoweredDeclarationOrigin.ENUM_MAPPINGS_FOR_ENTRIES.INSTANCE);
            IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
            IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
            irClassBuildClass.setParent(containingClass);
            this.irClass = irClassBuildClass;
            this.enums = new HashMap<>();
        }

        public final IrClass getContainingClass() {
            return this.containingClass;
        }

        public final IrClass getIrClass() {
            return this.irClass;
        }

        public final HashMap<IrClass, IrField> getEnums() {
            return this.enums;
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.EnumEntriesIntrinsicMappingsCache
    public synchronized IrField getEnumEntriesIntrinsicMappings(IrClass containingClass, IrClass enumClass) {
        IrField irField;
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(enumClass, "enumClass");
        Map<IrClass, MappingsClass> map = this.storage;
        MappingsClass mappingsClass = map.get(containingClass);
        if (mappingsClass == null) {
            mappingsClass = new MappingsClass(this, containingClass);
            map.put(containingClass, mappingsClass);
        }
        MappingsClass mappingsClass2 = mappingsClass;
        HashMap<IrClass, IrField> enums = mappingsClass2.getEnums();
        irField = enums.get(enumClass);
        if (irField == null) {
            IrClass irClass = mappingsClass2.getIrClass();
            IrFactory factory = irClass.getFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            Name nameIdentifier = Name.identifier("entries$" + mappingsClass2.getEnums().size());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(IrTypesKt.typeWith(this.context.getIr().getSymbols().getEnumEntries(), IrUtilsKt.getDefaultType(enumClass)));
            irFieldBuilder.setOrigin(JvmLoweredDeclarationOrigin.ENUM_MAPPINGS_FOR_ENTRIES.INSTANCE);
            irFieldBuilder.setFinal(true);
            irFieldBuilder.setStatic(true);
            IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
            irFieldBuildField.setParent(irClass);
            irClass.getDeclarations().add(irFieldBuildField);
            enums.put(enumClass, irFieldBuildField);
            irField = irFieldBuildField;
        }
        return irField;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.EnumEntriesIntrinsicMappingsCache
    public void generateMappingsClasses() {
        JvmBackendContext jvmBackendContext = this.context;
        for (MappingsClass mappingsClass : this.storage.values()) {
            IrClass irClass = mappingsClass.getIrClass();
            IrType unitType = this.context.getIrBuiltIns().getUnitType();
            DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
            IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(irClass, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, unitType, null, PACKAGE_VISIBILITY, true, false, false, false, JvmLoweredDeclarationOrigin.CLASS_STATIC_INITIALIZER.INSTANCE, 0, 0, XKeySym.XK_Cyrillic_DE, null);
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionAddFunction$default.getSymbol(), 0, 0, 6, (Object) null);
            IrSimpleFunction irSimpleFunction = irSimpleFunctionAddFunction$default;
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset());
            Set<Map.Entry<IrClass, IrField>> setEntrySet = mappingsClass.getEnums().entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
            for (Map.Entry entry : CollectionsKt.sortedWith(setEntrySet, new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.EnumEntriesIntrinsicMappingsCacheImpl$generateMappingsClasses$lambda$5$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((IrField) ((Map.Entry) t).getValue()).getName(), ((IrField) ((Map.Entry) t2).getValue()).getName());
                }
            })) {
                Intrinsics.checkNotNull(entry);
                IrClass irClass2 = (IrClass) entry.getKey();
                IrField irField = (IrField) entry.getValue();
                Intrinsics.checkNotNull(irClass2);
                IrSimpleFunction irSimpleFunctionFindEnumValuesFunction = JvmIrUtilsKt.findEnumValuesFunction(irClass2, jvmBackendContext);
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                Intrinsics.checkNotNull(irField);
                IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, jvmBackendContext.getIr().getSymbols().getCreateEnumEntries());
                irCall.putValueArgument(0, ExpressionHelpersKt.irCall(irBlockBodyBuilder2, irSimpleFunctionFindEnumValuesFunction));
                Unit unit = Unit.INSTANCE;
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, null, irField, irCall, null, 8, null));
            }
            irSimpleFunctionAddFunction$default.setBody(irBlockBodyBuilder.getIrBlockBody());
            ClassCodegen.Companion.getOrCreate$default(ClassCodegen.INSTANCE, mappingsClass.getIrClass(), jvmBackendContext, null, 4, null).generate();
        }
    }
}
