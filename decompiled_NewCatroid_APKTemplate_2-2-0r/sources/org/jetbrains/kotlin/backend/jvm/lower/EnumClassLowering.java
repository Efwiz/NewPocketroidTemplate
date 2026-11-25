package org.jetbrains.kotlin.backend.jvm.lower;

import gnu.trove.TObjectIntHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.IrArrayBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.ImplementationBodyCodegen;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.ir.expressions.impl.IrExpressionBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/EnumClassLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "lower", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "EnumClassTransformer", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class EnumClassLowering implements ClassLoweringPass {
    private final JvmBackendContext context;

    public EnumClassLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (IrUtilsKt.isEnumClass(irClass)) {
            new EnumClassTransformer(this, irClass, this.context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.EnumEntries)).run();
        }
    }

    /* JADX INFO: compiled from: EnumClassLowering.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001:\u0002'(B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\nH\u0002J\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020#H\u0002J\u0006\u0010%\u001a\u00020&R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010j\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0014\u001a\u001e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0010j\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0017\u001a\u00020\u0005*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\u00020\u0005*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/EnumClassLowering$EnumClassTransformer;", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "supportsEnumEntries", "", "(Lorg/jetbrains/kotlin/backend/jvm/lower/EnumClassLowering;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Z)V", "declarationToEnumEntry", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "enumArrayType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "enumEntryOrdinals", "Lgnu/trove/TObjectIntHashMap;", "loweredEnumConstructorParameters", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lkotlin/collections/HashMap;", "loweredEnumConstructors", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "hasGetEntriesFunction", "getHasGetEntriesFunction", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "isGetEntriesFunction", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "buildEntriesField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "valuesField", "buildEnumEntryField", "enumEntry", "buildValuesField", "valuesHelperFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "buildValuesHelperFunction", "run", "", "EnumClassCallTransformer", "EnumClassDeclarationsTransformer", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class EnumClassTransformer {
        private final Map<IrDeclaration, IrEnumEntry> declarationToEnumEntry;
        private final IrSimpleType enumArrayType;
        private final TObjectIntHashMap<IrEnumEntry> enumEntryOrdinals;
        private final IrClass irClass;
        private final HashMap<IrValueParameterSymbol, IrValueParameter> loweredEnumConstructorParameters;
        private final HashMap<IrConstructorSymbol, IrConstructor> loweredEnumConstructors;
        private final boolean supportsEnumEntries;
        final /* synthetic */ EnumClassLowering this$0;

        public EnumClassTransformer(EnumClassLowering enumClassLowering, IrClass irClass, boolean z) {
            Intrinsics.checkNotNullParameter(irClass, "irClass");
            this.this$0 = enumClassLowering;
            this.irClass = irClass;
            this.supportsEnumEntries = z;
            this.loweredEnumConstructors = new HashMap<>();
            this.loweredEnumConstructorParameters = new HashMap<>();
            this.enumEntryOrdinals = new TObjectIntHashMap<>();
            this.declarationToEnumEntry = new LinkedHashMap();
            this.enumArrayType = IrTypesKt.typeWith(enumClassLowering.context.getIrBuiltIns().getArrayClass(), IrUtilsKt.getDefaultType(irClass));
        }

        public final void run() {
            IrField irFieldBuildEntriesField;
            Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(this.irClass.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.EnumClassLowering$EnumClassTransformer$run$$inlined$filterIsInstance$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof IrEnumEntry);
                }
            });
            Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            for (IndexedValue indexedValue : SequencesKt.withIndex(sequenceFilter)) {
                int index = indexedValue.getIndex();
                IrEnumEntry irEnumEntry = (IrEnumEntry) indexedValue.component2();
                this.enumEntryOrdinals.put(irEnumEntry, index);
                IrClass correspondingClass = irEnumEntry.getCorrespondingClass();
                if (correspondingClass != null) {
                    this.declarationToEnumEntry.put(correspondingClass, irEnumEntry);
                }
                this.declarationToEnumEntry.put(buildEnumEntryField(irEnumEntry), irEnumEntry);
            }
            CollectionsKt.removeAll((List) this.irClass.getDeclarations(), (Function1) new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.EnumClassLowering$EnumClassTransformer$run$2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it instanceof IrEnumEntry);
                }
            });
            CollectionsKt.addAll(this.irClass.getDeclarations(), this.declarationToEnumEntry.keySet());
            IrField irFieldBuildValuesField = buildValuesField(buildValuesHelperFunction());
            if (!getHasGetEntriesFunction(this.irClass)) {
                irFieldBuildEntriesField = null;
            } else {
                if (!this.supportsEnumEntries) {
                    throw new IllegalStateException("The frontend must have checked if the feature is supported while emitting the IR".toString());
                }
                irFieldBuildEntriesField = buildEntriesField(irFieldBuildValuesField);
            }
            IrElementTransformerVoidKt.transformChildrenVoid(this.irClass, new EnumClassDeclarationsTransformer(this, irFieldBuildValuesField, irFieldBuildEntriesField));
            IrElementTransformerVoidKt.transformChildrenVoid(this.irClass, new EnumClassCallTransformer());
        }

        private final boolean getHasGetEntriesFunction(IrClass irClass) {
            List<IrDeclaration> declarations = irClass.getDeclarations();
            if ((declarations instanceof Collection) && declarations.isEmpty()) {
                return false;
            }
            Iterator<T> it = declarations.iterator();
            while (it.hasNext()) {
                if (isGetEntriesFunction((IrDeclaration) it.next())) {
                    return true;
                }
            }
            return false;
        }

        private final boolean isGetEntriesFunction(IrDeclaration irDeclaration) {
            return (irDeclaration instanceof IrFunction) && Intrinsics.areEqual(((IrFunction) irDeclaration).getName(), SpecialNames.ENUM_GET_ENTRIES) && Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE);
        }

        private final IrField buildEnumEntryField(IrEnumEntry enumEntry) {
            IrField fieldForEnumEntry = this.this$0.context.getCachedDeclarations().getFieldForEnumEntry(enumEntry);
            IrExpressionBody initializerExpression = enumEntry.getInitializerExpression();
            fieldForEnumEntry.setInitializer(initializerExpression != null ? new IrExpressionBodyImpl((IrExpression) PatchDeclarationParentsKt.patchDeclarationParents(initializerExpression.getExpression(), fieldForEnumEntry)) : null);
            fieldForEnumEntry.setAnnotations(CollectionsKt.plus((Collection) fieldForEnumEntry.getAnnotations(), (Iterable) enumEntry.getAnnotations()));
            return fieldForEnumEntry;
        }

        private final IrFunction buildValuesHelperFunction() {
            IrClass irClass = this.irClass;
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            Name nameIdentifier = Name.identifier("$values");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFunctionBuilder.setName(nameIdentifier);
            irFunctionBuilder.setReturnType(this.enumArrayType);
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            irFunctionBuilder.setVisibility(PRIVATE);
            irFunctionBuilder.setOrigin(IrDeclarationOrigin.SYNTHETIC_HELPER_FOR_ENUM_VALUES.INSTANCE);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
            IrClass irClass2 = irClass;
            irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irClass2);
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.this$0.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, null);
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
            IrArrayBuilder irArrayBuilder = new IrArrayBuilder(jvmIrBuilderCreateJvmIrBuilder$default, irSimpleFunctionBuildFunction.getReturnType());
            Set<IrDeclaration> setKeySet = this.declarationToEnumEntry.keySet();
            ArrayList arrayList = new ArrayList();
            for (Object obj : setKeySet) {
                if (obj instanceof IrField) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                irArrayBuilder.unaryPlus(ExpressionHelpersKt.irGetField$default(jvmIrBuilder, null, (IrField) it.next(), null, 4, null));
            }
            irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilder, irArrayBuilder.build()));
            return irSimpleFunctionBuildFunction;
        }

        private final IrField buildValuesField(IrFunction valuesHelperFunction) {
            IrClass irClass = this.irClass;
            IrFactory factory = irClass.getFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            Name nameIdentifier = Name.identifier(ImplementationBodyCodegen.ENUM_VALUES_FIELD_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(this.enumArrayType);
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            irFieldBuilder.setVisibility(PRIVATE);
            irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_ENUM_VALUES.INSTANCE);
            irFieldBuilder.setFinal(true);
            irFieldBuilder.setStatic(true);
            IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
            irFieldBuildField.setParent(irClass);
            irClass.getDeclarations().add(irFieldBuildField);
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.this$0.context, irFieldBuildField.getSymbol(), 0, 0, 6, null);
            irFieldBuildField.setInitializer(ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder$default, ExpressionHelpersKt.irCall(jvmIrBuilderCreateJvmIrBuilder$default, valuesHelperFunction.getSymbol())));
            return irFieldBuildField;
        }

        private final IrField buildEntriesField(IrField valuesField) {
            IrClass irClass = this.irClass;
            EnumClassLowering enumClassLowering = this.this$0;
            IrFactory factory = irClass.getFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            Name nameIdentifier = Name.identifier("$ENTRIES");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(IrTypesKt.getDefaultType(enumClassLowering.context.getIr().getSymbols().getEnumEntries()));
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            irFieldBuilder.setVisibility(PRIVATE);
            irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRIES.INSTANCE);
            irFieldBuilder.setFinal(true);
            irFieldBuilder.setStatic(true);
            IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
            irFieldBuildField.setParent(irClass);
            irClass.getDeclarations().add(irFieldBuildField);
            EnumClassLowering enumClassLowering2 = this.this$0;
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(enumClassLowering2.context, irFieldBuildField.getSymbol(), 0, 0, 6, null);
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilderCreateJvmIrBuilder$default, enumClassLowering2.context.getIr().getSymbols().getCreateEnumEntries());
            irCall.putValueArgument(0, ExpressionHelpersKt.irGetField$default(jvmIrBuilderCreateJvmIrBuilder$default, null, valuesField, null, 4, null));
            irFieldBuildField.setInitializer(ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder$default, irCall));
            return irFieldBuildField;
        }

        /* JADX INFO: compiled from: EnumClassLowering.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\rH\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/EnumClassLowering$EnumClassTransformer$EnumClassDeclarationsTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "valuesField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "entriesField", "(Lorg/jetbrains/kotlin/backend/jvm/lower/EnumClassLowering$EnumClassTransformer;Lorg/jetbrains/kotlin/ir/declarations/IrField;Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class EnumClassDeclarationsTransformer extends IrElementTransformerVoid {
            private final IrField entriesField;
            final /* synthetic */ EnumClassTransformer this$0;
            private final IrField valuesField;

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

            public EnumClassDeclarationsTransformer(EnumClassTransformer enumClassTransformer, IrField valuesField, IrField irField) {
                Intrinsics.checkNotNullParameter(valuesField, "valuesField");
                this.this$0 = enumClassTransformer;
                this.valuesField = valuesField;
                this.entriesField = irField;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitClass(IrClass declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return IrUtilsKt.isEnumEntry(declaration) ? super.visitClass(declaration) : declaration;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitConstructor(IrConstructor declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                IrFactory irFactory = this.this$0.this$0.context.getIrFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                irFunctionBuilder.updateFrom((IrFunction) declaration);
                irFunctionBuilder.setReturnType(declaration.getReturnType());
                IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder);
                EnumClassLowering enumClassLowering = this.this$0.this$0;
                EnumClassTransformer enumClassTransformer = this.this$0;
                irConstructorBuildConstructor.setParent(declaration.getParent());
                irConstructorBuildConstructor.setAnnotations(declaration.getAnnotations());
                IrConstructor irConstructor = irConstructorBuildConstructor;
                DeclarationBuildersKt.addValueParameter(irConstructor, "$enum$name", enumClassLowering.context.getIrBuiltIns().getStringType(), JvmLoweredDeclarationOrigin.ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER.INSTANCE);
                DeclarationBuildersKt.addValueParameter(irConstructor, "$enum$ordinal", enumClassLowering.context.getIrBuiltIns().getIntType(), JvmLoweredDeclarationOrigin.ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER.INSTANCE);
                List<IrValueParameter> valueParameters = irConstructorBuildConstructor.getValueParameters();
                List<IrValueParameter> valueParameters2 = declaration.getValueParameters();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters2, 10));
                for (IrValueParameter irValueParameter : valueParameters2) {
                    ArrayList arrayList2 = arrayList;
                    IrValueParameter irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(irValueParameter, irConstructor, null, irValueParameter.getIndex() + 2, 0, 0, null, null, null, null, null, false, false, false, 8186, null);
                    enumClassTransformer.loweredEnumConstructorParameters.put(irValueParameter.getSymbol(), irValueParameterCopyTo$default);
                    arrayList2.add(irValueParameterCopyTo$default);
                    arrayList = arrayList2;
                }
                irConstructorBuildConstructor.setValueParameters(CollectionsKt.plus((Collection) valueParameters, (Iterable) arrayList));
                IrBody body = declaration.getBody();
                irConstructorBuildConstructor.setBody(body != null ? (IrBody) PatchDeclarationParentsKt.patchDeclarationParents(body, irConstructorBuildConstructor) : null);
                enumClassTransformer.loweredEnumConstructors.put(declaration.getSymbol(), irConstructorBuildConstructor);
                irConstructorBuildConstructor.setMetadata(declaration.getMetadata());
                return irConstructorBuildConstructor;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitSimpleFunction(IrSimpleFunction declaration) {
                IrCall irCallIrGetField$default;
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                IrBody body = declaration.getBody();
                IrSyntheticBody irSyntheticBody = body instanceof IrSyntheticBody ? (IrSyntheticBody) body : null;
                if (irSyntheticBody == null) {
                    return declaration;
                }
                JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.this$0.this$0.context, declaration.getSymbol(), 0, 0, 6, null);
                EnumClassLowering enumClassLowering = this.this$0.this$0;
                EnumClassTransformer enumClassTransformer = this.this$0;
                JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
                int i = WhenMappings.$EnumSwitchMapping$0[irSyntheticBody.getKind().ordinal()];
                if (i == 1) {
                    IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(jvmIrBuilder, enumClassLowering.context.getIr().getSymbols().getObjectCloneFunction(), declaration.getReturnType(), 0, 0, null, 28, null);
                    irCallIrCall$default.setDispatchReceiver(ExpressionHelpersKt.irGetField$default(jvmIrBuilder, null, this.valuesField, null, 4, null));
                    irCallIrGetField$default = irCallIrCall$default;
                } else if (i == 2) {
                    IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder, jvmIrBuilderCreateJvmIrBuilder$default.getBackendContext().getIr().getSymbols().getEnumValueOfFunction());
                    irCall.putValueArgument(0, JvmIrUtilsKt.javaClassReference(jvmIrBuilderCreateJvmIrBuilder$default, IrUtilsKt.getDefaultType(enumClassTransformer.irClass)));
                    irCall.putValueArgument(1, ExpressionHelpersKt.irGet(jvmIrBuilder, declaration.getValueParameters().get(0)));
                    irCallIrGetField$default = irCall;
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    IrField irField = this.entriesField;
                    Intrinsics.checkNotNull(irField);
                    irCallIrGetField$default = ExpressionHelpersKt.irGetField$default(jvmIrBuilder, null, irField, null, 4, null);
                }
                declaration.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilder, irCallIrGetField$default));
                return declaration;
            }
        }

        /* JADX INFO: compiled from: EnumClassLowering.kt */
        @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0010H\u0016J(\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/EnumClassLowering$EnumClassTransformer$EnumClassCallTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "(Lorg/jetbrains/kotlin/backend/jvm/lower/EnumClassLowering$EnumClassTransformer;)V", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "passConstructorArguments", "", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "original", "enumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class EnumClassCallTransformer extends IrElementTransformerVoidWithContext {
            public EnumClassCallTransformer() {
            }

            @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
            public IrStatement visitClassNew(IrClass declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return IrUtilsKt.isEnumEntry(declaration) ? super.visitClassNew(declaration) : declaration;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrValueParameter irValueParameter = (IrValueParameter) EnumClassTransformer.this.loweredEnumConstructorParameters.get(expression.getSymbol());
                if (irValueParameter != null) {
                    return new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueParameter.getType(), irValueParameter.getSymbol(), expression.getOrigin());
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitSetValue(IrSetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                transformChildrenVoid(expression);
                IrValueParameter irValueParameter = (IrValueParameter) EnumClassTransformer.this.loweredEnumConstructorParameters.get(expression.getSymbol());
                if (irValueParameter != null) {
                    return new IrSetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueParameter.getType(), irValueParameter.getSymbol(), expression.getValue(), expression.getOrigin());
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitEnumConstructorCall(IrEnumConstructorCall expression) {
                IrDelegatingConstructorCall irDelegatingConstructorCallIrCall;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrEnumConstructorCall irEnumConstructorCall = expression;
                IrElementTransformerVoidKt.transformChildrenVoid(irEnumConstructorCall, this);
                ScopeWithIr currentScope = getCurrentScope();
                Intrinsics.checkNotNull(currentScope);
                IrSymbol scopeOwnerSymbol = currentScope.getScope().getScopeOwnerSymbol();
                IrBuilder irBuilderAt = LowerUtilsKt.at(LowerUtilsKt.createIrBuilder$default(EnumClassTransformer.this.this$0.context, scopeOwnerSymbol, 0, 0, 6, (Object) null), irEnumConstructorCall);
                EnumClassTransformer enumClassTransformer = EnumClassTransformer.this;
                DeclarationIrBuilder declarationIrBuilder = (DeclarationIrBuilder) irBuilderAt;
                IrConstructor owner = (IrConstructor) enumClassTransformer.loweredEnumConstructors.get(expression.getSymbol());
                if (owner == null) {
                    owner = expression.getSymbol().getOwner();
                }
                Intrinsics.checkNotNull(owner);
                if (scopeOwnerSymbol instanceof IrConstructorSymbol) {
                    irDelegatingConstructorCallIrCall = ExpressionHelpersKt.irDelegatingConstructorCall(declarationIrBuilder, owner);
                } else {
                    irDelegatingConstructorCallIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, owner);
                }
                Map map = enumClassTransformer.declarationToEnumEntry;
                IrSymbolOwner owner2 = scopeOwnerSymbol.getOwner();
                Intrinsics.checkNotNull(owner2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                passConstructorArguments(declarationIrBuilder, irDelegatingConstructorCallIrCall, expression, (IrEnumEntry) map.get((IrDeclaration) owner2));
                return irDelegatingConstructorCallIrCall;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrDelegatingConstructorCall irDelegatingConstructorCall = expression;
                IrElementTransformerVoidKt.transformChildrenVoid(irDelegatingConstructorCall, this);
                IrConstructor irConstructor = (IrConstructor) EnumClassTransformer.this.loweredEnumConstructors.get(expression.getSymbol());
                if (irConstructor != null) {
                    JvmBackendContext jvmBackendContext = EnumClassTransformer.this.this$0.context;
                    ScopeWithIr currentScope = getCurrentScope();
                    Intrinsics.checkNotNull(currentScope);
                    DeclarationIrBuilder declarationIrBuilder = (DeclarationIrBuilder) LowerUtilsKt.at(LowerUtilsKt.createIrBuilder$default(jvmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null), irDelegatingConstructorCall);
                    IrDelegatingConstructorCall irDelegatingConstructorCall2 = ExpressionHelpersKt.irDelegatingConstructorCall(declarationIrBuilder, irConstructor);
                    passConstructorArguments$default(this, declarationIrBuilder, irDelegatingConstructorCall2, expression, null, 4, null);
                    return irDelegatingConstructorCall2;
                }
                return expression;
            }

            static /* synthetic */ void passConstructorArguments$default(EnumClassCallTransformer enumClassCallTransformer, IrBuilderWithScope irBuilderWithScope, IrFunctionAccessExpression irFunctionAccessExpression, IrFunctionAccessExpression irFunctionAccessExpression2, IrEnumEntry irEnumEntry, int i, Object obj) {
                if ((i & 4) != 0) {
                    irEnumEntry = null;
                }
                enumClassCallTransformer.passConstructorArguments(irBuilderWithScope, irFunctionAccessExpression, irFunctionAccessExpression2, irEnumEntry);
            }

            private final void passConstructorArguments(IrBuilderWithScope irBuilderWithScope, IrFunctionAccessExpression irFunctionAccessExpression, IrFunctionAccessExpression irFunctionAccessExpression2, IrEnumEntry irEnumEntry) {
                IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionAccessExpression, irFunctionAccessExpression2, 0, 2, null);
                if (irEnumEntry != null) {
                    String strAsString = irEnumEntry.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    irFunctionAccessExpression.putValueArgument(0, ExpressionHelpersKt.irString(irBuilderWithScope, strAsString));
                    irFunctionAccessExpression.putValueArgument(1, ExpressionHelpersKt.irInt$default(irBuilderWithScope, EnumClassTransformer.this.enumEntryOrdinals.get(irEnumEntry), null, 2, null));
                } else {
                    ScopeWithIr currentScope = getCurrentScope();
                    Intrinsics.checkNotNull(currentScope);
                    IrSymbol scopeOwnerSymbol = currentScope.getScope().getScopeOwnerSymbol();
                    Intrinsics.checkNotNull(scopeOwnerSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
                    IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) scopeOwnerSymbol;
                    irFunctionAccessExpression.putValueArgument(0, ExpressionHelpersKt.irGet(irBuilderWithScope, irConstructorSymbol.getOwner().getValueParameters().get(0)));
                    irFunctionAccessExpression.putValueArgument(1, ExpressionHelpersKt.irGet(irBuilderWithScope, irConstructorSymbol.getOwner().getValueParameters().get(1)));
                }
                int valueArgumentsCount = irFunctionAccessExpression2.getValueArgumentsCount();
                for (int i = 0; i < valueArgumentsCount; i++) {
                    IrExpression valueArgument = irFunctionAccessExpression2.getValueArgument(i);
                    if (valueArgument != null) {
                        irFunctionAccessExpression.putValueArgument(i + 2, valueArgument);
                    }
                }
            }
        }
    }
}
