package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrElementBuilderKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;

/* JADX INFO: compiled from: JvmCachedDeclarations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\tJ\u0018\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u000bJ\u000e\u0010$\u001a\u00020\u00132\u0006\u0010%\u001a\u00020&J\u000e\u0010'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\tJ\u0010\u0010)\u001a\u0004\u0018\u00010\u000b2\u0006\u0010*\u001a\u00020\u000bJ\u000e\u0010+\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\tJ\u000e\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\tJ\u001a\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u00172\u0006\u0010/\u001a\u00020\u000bJ\u0010\u00100\u001a\u0004\u0018\u00010\u00132\u0006\u00101\u001a\u00020\u0015J\u001c\u00102\u001a\u00020\u000b*\u00020\t2\u0006\u00103\u001a\u00020\u000b2\u0006\u00104\u001a\u00020!H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00130\bX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0016\u001a\u001a\u0012\u0004\u0012\u00020\u000b\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u00170\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmCachedDeclarations;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "fieldsForObjectInstances", "Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;)V", "defaultImplsClasses", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "defaultImplsMethods", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "defaultImplsOriginalMethods", "defaultImplsRedirections", "getFieldsForObjectInstances", "()Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "repeatedAnnotationSyntheticContainers", "singletonFieldDeclarations", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "staticBackingFields", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "staticCompanionDeclarations", "Lkotlin/Pair;", "syntheticAccessorGenerator", "Lorg/jetbrains/kotlin/backend/jvm/CachedSyntheticDeclarations;", "getSyntheticAccessorGenerator", "()Lorg/jetbrains/kotlin/backend/jvm/CachedSyntheticDeclarations;", "getDefaultImplsClass", "interfaceClass", "getDefaultImplsFunction", "interfaceFun", "forCompatibilityMode", "", "getDefaultImplsRedirection", "fakeOverride", "getFieldForEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getFieldForObjectInstance", "singleton", "getOriginalFunctionForDefaultImpl", "defaultImplFun", "getPrivateFieldForObjectInstance", "getRepeatedAnnotationSyntheticContainer", "annotationClass", "getStaticAndCompanionDeclaration", "jvmStaticFunction", "getStaticBackingField", "irProperty", "makeProxy", "target", "isStatic", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmCachedDeclarations {
    private final JvmBackendContext context;
    private final ConcurrentHashMap<IrClass, IrClass> defaultImplsClasses;
    private final ConcurrentHashMap<IrSimpleFunction, IrSimpleFunction> defaultImplsMethods;
    private final ConcurrentHashMap<IrSimpleFunction, IrSimpleFunction> defaultImplsOriginalMethods;
    private final ConcurrentHashMap<IrSimpleFunction, IrSimpleFunction> defaultImplsRedirections;
    private final CachedFieldsForObjectInstances fieldsForObjectInstances;
    private final ConcurrentHashMap<IrClass, IrClass> repeatedAnnotationSyntheticContainers;
    private final ConcurrentHashMap<IrSymbolOwner, IrField> singletonFieldDeclarations;
    private final ConcurrentHashMap<IrProperty, IrField> staticBackingFields;
    private final ConcurrentHashMap<IrSimpleFunction, Pair<IrSimpleFunction, IrSimpleFunction>> staticCompanionDeclarations;
    private final CachedSyntheticDeclarations syntheticAccessorGenerator;

    public JvmCachedDeclarations(JvmBackendContext context, CachedFieldsForObjectInstances fieldsForObjectInstances) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fieldsForObjectInstances, "fieldsForObjectInstances");
        this.context = context;
        this.fieldsForObjectInstances = fieldsForObjectInstances;
        this.syntheticAccessorGenerator = new CachedSyntheticDeclarations(context);
        this.singletonFieldDeclarations = new ConcurrentHashMap<>();
        this.staticBackingFields = new ConcurrentHashMap<>();
        this.staticCompanionDeclarations = new ConcurrentHashMap<>();
        this.defaultImplsMethods = new ConcurrentHashMap<>();
        this.defaultImplsClasses = new ConcurrentHashMap<>();
        this.defaultImplsRedirections = new ConcurrentHashMap<>();
        this.defaultImplsOriginalMethods = new ConcurrentHashMap<>();
        this.repeatedAnnotationSyntheticContainers = new ConcurrentHashMap<>();
    }

    public final CachedFieldsForObjectInstances getFieldsForObjectInstances() {
        return this.fieldsForObjectInstances;
    }

    public final CachedSyntheticDeclarations getSyntheticAccessorGenerator() {
        return this.syntheticAccessorGenerator;
    }

    public final IrField getFieldForEnumEntry(IrEnumEntry enumEntry) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        ConcurrentHashMap<IrSymbolOwner, IrField> concurrentHashMap = this.singletonFieldDeclarations;
        IrField irFieldBuildField = concurrentHashMap.get(enumEntry);
        if (irFieldBuildField == null) {
            IrFactory irFactory = this.context.getIrFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            IrElementBuilderKt.setSourceRange(irFieldBuilder, enumEntry);
            irFieldBuilder.setName(enumEntry.getName());
            irFieldBuilder.setType(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(enumEntry)));
            irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRY.INSTANCE);
            irFieldBuilder.setFinal(true);
            irFieldBuilder.setStatic(true);
            irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
            irFieldBuildField.setParent(enumEntry.getParent());
            IrField irFieldPutIfAbsent = concurrentHashMap.putIfAbsent(enumEntry, irFieldBuildField);
            if (irFieldPutIfAbsent != null) {
                irFieldBuildField = irFieldPutIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(irFieldBuildField, "getOrPut(...)");
        return irFieldBuildField;
    }

    public final IrField getFieldForObjectInstance(IrClass singleton) {
        Intrinsics.checkNotNullParameter(singleton, "singleton");
        return this.fieldsForObjectInstances.getFieldForObjectInstance(singleton);
    }

    public final IrField getPrivateFieldForObjectInstance(IrClass singleton) {
        Intrinsics.checkNotNullParameter(singleton, "singleton");
        return this.fieldsForObjectInstances.getPrivateFieldForObjectInstance(singleton);
    }

    public final IrField getStaticBackingField(IrProperty irProperty) {
        IrField backingField;
        List<IrConstructorCall> annotations;
        Intrinsics.checkNotNullParameter(irProperty, "irProperty");
        if (irProperty.getIsFakeOverride() || (backingField = irProperty.getBackingField()) == null) {
            return null;
        }
        IrDeclarationParent parent = irProperty.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass == null || !IrUtilsKt.isObject(irClass)) {
            return null;
        }
        ConcurrentHashMap<IrProperty, IrField> concurrentHashMap = this.staticBackingFields;
        IrField irFieldBuildField = concurrentHashMap.get(irProperty);
        if (irFieldBuildField == null) {
            IrFactory irFactory = this.context.getIrFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            irFieldBuilder.updateFrom(backingField);
            irFieldBuilder.setName(backingField.getName());
            irFieldBuilder.setStatic(true);
            irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
            boolean zHasAnnotation = IrUtilsKt.hasAnnotation(backingField, JvmAbi.JVM_FIELD_ANNOTATION_FQ_NAME);
            if (irClass.getIsCompanion() && (!JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(irClass)) || zHasAnnotation)) {
                irFieldBuildField.setParent(IrUtilsKt.getParentAsClass(irClass));
                boolean zIsPrivate = DescriptorVisibilities.isPrivate(backingField.getVisibility());
                if (DescriptorVisibilities.isPrivate(irClass.getVisibility()) && !zIsPrivate) {
                    JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irFieldBuildField.getSymbol(), 0, 0, 6, null);
                    annotations = CollectionsKt.plus((Collection<? extends IrConstructorCall>) AdditionalIrUtilsKt.filterOutAnnotations(DeprecationResolver.INSTANCE.getJAVA_DEPRECATED(), backingField.getAnnotations()), ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilderCreateJvmIrBuilder$default, jvmIrBuilderCreateJvmIrBuilder$default.getIrSymbols().getJavaLangDeprecatedConstructorWithDeprecatedFlag()));
                } else {
                    annotations = backingField.getAnnotations();
                }
                irFieldBuildField.setAnnotations(annotations);
            } else {
                irFieldBuildField.setParent(irClass);
                irFieldBuildField.setAnnotations(backingField.getAnnotations());
            }
            IrExpressionBody initializer = backingField.getInitializer();
            irFieldBuildField.setInitializer(initializer != null ? (IrExpressionBody) PatchDeclarationParentsKt.patchDeclarationParents(initializer, irFieldBuildField) : null);
            CachedFieldsForObjectInstances cachedFieldsForObjectInstances = this.fieldsForObjectInstances;
            IrValueParameter thisReceiver = irClass.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            JvmIrUtilsKt.replaceThisByStaticReference(backingField, cachedFieldsForObjectInstances, irClass, thisReceiver);
            irFieldBuildField.setOrigin(IrUtilsKt.getParentAsClass(irProperty).getIsCompanion() ? JvmLoweredDeclarationOrigin.COMPANION_PROPERTY_BACKING_FIELD.INSTANCE : irFieldBuildField.getOrigin());
            IrField irFieldPutIfAbsent = concurrentHashMap.putIfAbsent(irProperty, irFieldBuildField);
            if (irFieldPutIfAbsent != null) {
                irFieldBuildField = irFieldPutIfAbsent;
            }
        }
        return irFieldBuildField;
    }

    public final Pair<IrSimpleFunction, IrSimpleFunction> getStaticAndCompanionDeclaration(IrSimpleFunction jvmStaticFunction) {
        Intrinsics.checkNotNullParameter(jvmStaticFunction, "jvmStaticFunction");
        ConcurrentHashMap<IrSimpleFunction, Pair<IrSimpleFunction, IrSimpleFunction>> concurrentHashMap = this.staticCompanionDeclarations;
        Pair<IrSimpleFunction, IrSimpleFunction> pair = concurrentHashMap.get(jvmStaticFunction);
        if (pair == null) {
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(jvmStaticFunction);
            parentAsClass.getIsCompanion();
            if (jvmStaticFunction.getIsExternal()) {
                IrFactory irFactory = this.context.getIrFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                IrSimpleFunction irSimpleFunction = jvmStaticFunction;
                irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction);
                irFunctionBuilder.setName(jvmStaticFunction.getName());
                irFunctionBuilder.setReturnType(jvmStaticFunction.getReturnType());
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                irSimpleFunctionBuildFunction.setParent(parentAsClass.getParent());
                IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, jvmStaticFunction);
                IrUtilsKt.copyAnnotationsFrom(irSimpleFunctionBuildFunction, jvmStaticFunction);
                JvmIrUtilsKt.copyCorrespondingPropertyFrom(irSimpleFunctionBuildFunction, jvmStaticFunction);
                IrUtilsKt.copyParameterDeclarationsFrom(irSimpleFunctionBuildFunction, irSimpleFunction);
                irSimpleFunctionBuildFunction.setDispatchReceiverParameter(null);
                irSimpleFunctionBuildFunction.setMetadata(jvmStaticFunction.getMetadata());
                pair = TuplesKt.to(irSimpleFunctionBuildFunction, makeProxy(parentAsClass, irSimpleFunctionBuildFunction, false));
            } else {
                pair = TuplesKt.to(makeProxy(IrUtilsKt.getParentAsClass(parentAsClass), jvmStaticFunction, true), jvmStaticFunction);
            }
            Pair<IrSimpleFunction, IrSimpleFunction> pairPutIfAbsent = concurrentHashMap.putIfAbsent(jvmStaticFunction, pair);
            if (pairPutIfAbsent != null) {
                pair = pairPutIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(pair, "getOrPut(...)");
        return pair;
    }

    private final IrSimpleFunction makeProxy(IrClass irClass, IrSimpleFunction irSimpleFunction, boolean z) {
        DescriptorVisibility PUBLIC;
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setReturnType(irSimpleFunction.getReturnType());
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.JVM_STATIC_WRAPPER.INSTANCE);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        int i = 0;
        Name nameIdentifier = Name.identifier(MethodSignatureMapper.mapFunctionName$default(this.context.getDefaultMethodSignatureMapper(), irSimpleFunction2, false, 2, null));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setModality(IrUtilsKt.isInterface(irClass) ? Modality.OPEN : irSimpleFunction.getModality());
        DescriptorVisibility visibility = irSimpleFunction.getVisibility();
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.INTERNAL)) {
            PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        } else if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PROTECTED)) {
            if (z) {
                PUBLIC = JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY;
            } else {
                PUBLIC = DescriptorVisibilities.PROTECTED;
            }
            Intrinsics.checkNotNull(PUBLIC);
        } else {
            PUBLIC = irSimpleFunction.getVisibility();
        }
        irFunctionBuilder.setVisibility(PUBLIC);
        irFunctionBuilder.setSuspend(irSimpleFunction.getIsSuspend());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irClass);
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, irSimpleFunction);
        IrSimpleFunction irSimpleFunction3 = irSimpleFunctionBuildFunction;
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunction3, irSimpleFunction, null, null, 6, null);
        IrUtilsKt.copyAnnotationsFrom(irSimpleFunctionBuildFunction, irSimpleFunction);
        if (!z) {
            IrValueParameter thisReceiver = irClass.getThisReceiver();
            irSimpleFunctionBuildFunction.setDispatchReceiverParameter(thisReceiver != null ? IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, IrUtilsKt.getDefaultType(irClass), null, null, false, false, false, 8062, null) : null);
        }
        IrValueParameter extensionReceiverParameter = irSimpleFunction.getExtensionReceiverParameter();
        irSimpleFunctionBuildFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? IrUtilsKt.copyTo$default(extensionReceiverParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
        List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(IrUtilsKt.copyTo$default((IrValueParameter) it.next(), irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
        }
        irSimpleFunctionBuildFunction.setValueParameters(arrayList);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, irSimpleFunction2);
        IrUtilsKt.passTypeArgumentsFrom$default(irFunctionAccessExpressionIrCall, irSimpleFunction3, 0, 2, null);
        if (irSimpleFunction.getDispatchReceiverParameter() != null) {
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder$default, null, getFieldForObjectInstance(IrUtilsKt.getParentAsClass(irSimpleFunction)), null, 4, null));
        }
        IrValueParameter extensionReceiverParameter2 = irSimpleFunctionBuildFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter2 != null) {
            irFunctionAccessExpressionIrCall.setExtensionReceiver(ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, extensionReceiverParameter2));
        }
        Iterator<IrValueParameter> it2 = irSimpleFunctionBuildFunction.getValueParameters().iterator();
        while (it2.hasNext()) {
            irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, it2.next()));
            i++;
        }
        irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default, irFunctionAccessExpressionIrCall));
        return irSimpleFunctionBuildFunction;
    }

    public static /* synthetic */ IrSimpleFunction getDefaultImplsFunction$default(JvmCachedDeclarations jvmCachedDeclarations, IrSimpleFunction irSimpleFunction, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return jvmCachedDeclarations.getDefaultImplsFunction(irSimpleFunction, z);
    }

    public final IrSimpleFunction getDefaultImplsFunction(IrSimpleFunction interfaceFun, boolean forCompatibilityMode) {
        IrDeclarationOrigin origin;
        DescriptorVisibility descriptorVisibility;
        Intrinsics.checkNotNullParameter(interfaceFun, "interfaceFun");
        IrSimpleFunction irSimpleFunction = interfaceFun;
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irSimpleFunction);
        JvmIrUtilsKt.isJvmInterface(parentAsClass);
        if (forCompatibilityMode) {
            this.defaultImplsMethods.containsKey(interfaceFun);
        }
        ConcurrentHashMap<IrSimpleFunction, IrSimpleFunction> concurrentHashMap = this.defaultImplsMethods;
        IrSimpleFunction irSimpleFunction2 = concurrentHashMap.get(interfaceFun);
        if (irSimpleFunction2 == null) {
            IrClass defaultImplsClass = getDefaultImplsClass(IrUtilsKt.getParentAsClass(irSimpleFunction));
            if (forCompatibilityMode || interfaceFun.getIsFakeOverride()) {
                IrOverridableDeclaration irOverridableDeclarationResolveFakeOverride$default = IrFakeOverrideUtilsKt.resolveFakeOverride$default(interfaceFun, false, null, 3, null);
                Intrinsics.checkNotNull(irOverridableDeclarationResolveFakeOverride$default);
                origin = ((IrSimpleFunction) irOverridableDeclarationResolveFakeOverride$default).getOrigin();
            } else {
                origin = interfaceFun.getOrigin();
            }
            IrDeclarationOrigin irDeclarationOrigin = origin;
            if (DescriptorVisibilities.isPrivate(interfaceFun.getVisibility())) {
                descriptorVisibility = DescriptorVisibilities.PRIVATE;
            } else {
                descriptorVisibility = DescriptorVisibilities.PUBLIC;
            }
            DescriptorVisibility descriptorVisibility2 = descriptorVisibility;
            Intrinsics.checkNotNull(descriptorVisibility2);
            IrSimpleFunction irSimpleFunctionCreateStaticFunctionWithReceivers$default = IrUtilsKt.createStaticFunctionWithReceivers$default(this.context.getIrFactory(), defaultImplsClass, interfaceFun.getName(), interfaceFun, IrUtilsKt.getDefaultType(parentAsClass), irDeclarationOrigin, Modality.OPEN, descriptorVisibility2, false, false, parentAsClass.getTypeParameters(), new JvmCachedDeclarations$getDefaultImplsFunction$3$1(this.context), 256, null);
            JvmIrUtilsKt.copyCorrespondingPropertyFrom(irSimpleFunctionCreateStaticFunctionWithReceivers$default, interfaceFun);
            if (forCompatibilityMode) {
                IrOverridableDeclaration irOverridableDeclarationResolveFakeOverride$default2 = IrFakeOverrideUtilsKt.resolveFakeOverride$default(interfaceFun, false, null, 3, null);
                Intrinsics.checkNotNull(irOverridableDeclarationResolveFakeOverride$default2);
                if (!((IrSimpleFunction) irOverridableDeclarationResolveFakeOverride$default2).getOrigin().getIsSynthetic()) {
                    JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irSimpleFunctionCreateStaticFunctionWithReceivers$default.getSymbol(), 0, 0, 6, null);
                    List<IrConstructorCall> annotations = irSimpleFunctionCreateStaticFunctionWithReceivers$default.getAnnotations();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : annotations) {
                        if (!AdditionalIrUtilsKt.hasEqualFqName(AdditionalIrUtilsKt.getConstructedClass(((IrConstructorCall) obj).getSymbol().getOwner()), DeprecationResolver.INSTANCE.getJAVA_DEPRECATED())) {
                            arrayList.add(obj);
                        }
                    }
                    irSimpleFunctionCreateStaticFunctionWithReceivers$default.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) arrayList, ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilderCreateJvmIrBuilder$default, jvmIrBuilderCreateJvmIrBuilder$default.getIrSymbols().getJavaLangDeprecatedConstructorWithDeprecatedFlag())));
                }
            }
            this.defaultImplsOriginalMethods.put(irSimpleFunctionCreateStaticFunctionWithReceivers$default, interfaceFun);
            IrSimpleFunction irSimpleFunctionPutIfAbsent = concurrentHashMap.putIfAbsent(interfaceFun, irSimpleFunctionCreateStaticFunctionWithReceivers$default);
            irSimpleFunction2 = irSimpleFunctionPutIfAbsent == null ? irSimpleFunctionCreateStaticFunctionWithReceivers$default : irSimpleFunctionPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irSimpleFunction2, "getOrPut(...)");
        return irSimpleFunction2;
    }

    public final IrSimpleFunction getOriginalFunctionForDefaultImpl(IrSimpleFunction defaultImplFun) {
        Intrinsics.checkNotNullParameter(defaultImplFun, "defaultImplFun");
        return this.defaultImplsOriginalMethods.get(defaultImplFun);
    }

    public final IrClass getDefaultImplsClass(IrClass interfaceClass) {
        Intrinsics.checkNotNullParameter(interfaceClass, "interfaceClass");
        ConcurrentHashMap<IrClass, IrClass> concurrentHashMap = this.defaultImplsClasses;
        IrClass irClassBuildClass = concurrentHashMap.get(interfaceClass);
        if (irClassBuildClass == null) {
            IrFactory irFactory = this.context.getIrFactory();
            IrClassBuilder irClassBuilder = new IrClassBuilder();
            irClassBuilder.setStartOffset(interfaceClass.getStartOffset());
            irClassBuilder.setEndOffset(interfaceClass.getEndOffset());
            irClassBuilder.setOrigin(JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE);
            Name nameIdentifier = Name.identifier(JvmAbi.DEFAULT_IMPLS_CLASS_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irClassBuilder.setName(nameIdentifier);
            irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
            irClassBuildClass.setParent(interfaceClass);
            IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
            IrClass irClassPutIfAbsent = concurrentHashMap.putIfAbsent(interfaceClass, irClassBuildClass);
            if (irClassPutIfAbsent != null) {
                irClassBuildClass = irClassPutIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(irClassBuildClass, "getOrPut(...)");
        return irClassBuildClass;
    }

    public final IrSimpleFunction getDefaultImplsRedirection(IrSimpleFunction fakeOverride) {
        Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
        ConcurrentHashMap<IrSimpleFunction, IrSimpleFunction> concurrentHashMap = this.defaultImplsRedirections;
        IrSimpleFunction irSimpleFunction = concurrentHashMap.get(fakeOverride);
        if (irSimpleFunction == null) {
            fakeOverride.getIsFakeOverride();
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(fakeOverride);
            IrFactory irFactory = this.context.getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SUPER_INTERFACE_METHOD_BRIDGE.INSTANCE);
            irFunctionBuilder.setName(fakeOverride.getName());
            irFunctionBuilder.setVisibility(fakeOverride.getVisibility());
            irFunctionBuilder.setModality(fakeOverride.getModality());
            irFunctionBuilder.setReturnType(fakeOverride.getReturnType());
            irFunctionBuilder.setInline(fakeOverride.getIsInline());
            irFunctionBuilder.setExternal(false);
            irFunctionBuilder.setTailrec(false);
            irFunctionBuilder.setSuspend(fakeOverride.getIsSuspend());
            irFunctionBuilder.setOperator(fakeOverride.getIsOperator());
            irFunctionBuilder.setInfix(fakeOverride.getIsInfix());
            irFunctionBuilder.setExpect(false);
            irFunctionBuilder.setFakeOverride(false);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            irSimpleFunctionBuildFunction.setParent(parentAsClass);
            irSimpleFunctionBuildFunction.setOverriddenSymbols(fakeOverride.getOverriddenSymbols());
            IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
            IrSimpleFunction irSimpleFunction3 = fakeOverride;
            IrUtilsKt.copyParameterDeclarationsFrom(irSimpleFunction2, irSimpleFunction3);
            IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
            if (dispatchReceiverParameter != null) {
                dispatchReceiverParameter.setType(IrUtilsKt.getDefaultType(parentAsClass));
            }
            irSimpleFunctionBuildFunction.setAnnotations(fakeOverride.getAnnotations());
            JvmIrUtilsKt.copyCorrespondingPropertyFrom(irSimpleFunctionBuildFunction, fakeOverride);
            this.context.remapMultiFieldValueClassStructure(irSimpleFunction3, irSimpleFunction2, null);
            IrSimpleFunction irSimpleFunctionPutIfAbsent = concurrentHashMap.putIfAbsent(fakeOverride, irSimpleFunctionBuildFunction);
            irSimpleFunction = irSimpleFunctionPutIfAbsent == null ? irSimpleFunctionBuildFunction : irSimpleFunctionPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irSimpleFunction, "getOrPut(...)");
        return irSimpleFunction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrClass getRepeatedAnnotationSyntheticContainer(IrClass annotationClass) {
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        ConcurrentHashMap<IrClass, IrClass> concurrentHashMap = this.repeatedAnnotationSyntheticContainers;
        IrClass irClassBuildClass = concurrentHashMap.get(annotationClass);
        if (irClassBuildClass == null) {
            IrFactory irFactory = this.context.getIrFactory();
            IrClassBuilder irClassBuilder = new IrClassBuilder();
            irClassBuilder.setKind(ClassKind.ANNOTATION_CLASS);
            Name nameIdentifier = Name.identifier(JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irClassBuilder.setName(nameIdentifier);
            irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
            IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
            irClassBuildClass.setParent(annotationClass);
            irClassBuildClass.setSuperTypes(CollectionsKt.listOf(this.context.getIrBuiltIns().getAnnotationType()));
            Name nameIdentifier2 = Name.identifier("value");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            int i = 1;
            IrSimpleType irSimpleTypeTypeWith = IrTypesKt.typeWith(this.context.getIrBuiltIns().getArrayClass(), IrTypesKt.typeWith(annotationClass, new IrType[0]));
            IrFactory factory = irClassBuildClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setPrimary(true);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClassBuildClass));
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
            irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
            IrClass irClass = irClassBuildClass;
            irConstructorBuildConstructor.setParent(irClass);
            IrSimpleType irSimpleType = irSimpleTypeTypeWith;
            DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, nameIdentifier2, irSimpleType, (IrDeclarationOrigin) null, 4, (Object) null);
            IrFactory factory2 = irClassBuildClass.getFactory();
            IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
            irPropertyBuilder.setName(nameIdentifier2);
            IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(factory2, irPropertyBuilder);
            irClassBuildClass.getDeclarations().add(irPropertyBuildProperty);
            irPropertyBuildProperty.setParent(irClass);
            IrFactory irFactory2 = this.context.getIrFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            irFieldBuilder.setName(nameIdentifier2);
            irFieldBuilder.setType(irSimpleType);
            IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory2, irFieldBuilder);
            irFieldBuildField.setParent(irClass);
            irFieldBuildField.setCorrespondingPropertySymbol(irPropertyBuildProperty.getSymbol());
            irPropertyBuildProperty.setBackingField(irFieldBuildField);
            DeclarationBuildersKt.addDefaultGetter(irPropertyBuildProperty, irClassBuildClass, this.context.getIrBuiltIns());
            List<IrConstructorCall> annotations = annotationClass.getAnnotations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : annotations) {
                IrConstructorCall irConstructorCall = (IrConstructorCall) obj;
                if (AdditionalIrUtilsKt.isAnnotationWithEqualFqName(irConstructorCall, StandardNames.FqNames.retention) || AdditionalIrUtilsKt.isAnnotationWithEqualFqName(irConstructorCall, StandardNames.FqNames.target)) {
                    arrayList.add(obj);
                }
            }
            ArrayList<IrConstructorCall> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (IrConstructorCall irConstructorCall2 : arrayList2) {
                DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, i, 0 == true ? 1 : 0);
                IrVisitorsKt.acceptVoid(irConstructorCall2, deepCopySymbolRemapper);
                DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irConstructorCall2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irClass);
                if (irElementPatchDeclarationParents != null) {
                    arrayList3.add((IrConstructorCall) irElementPatchDeclarationParents);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
                }
            }
            irClassBuildClass.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) arrayList3, ExpressionHelpersKt.irCall((IrBuilderWithScope) JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irClassBuildClass.getSymbol(), 0, 0, 6, null), (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(this.context.getIr().getSymbols().getRepeatableContainer())))));
            IrClass irClassPutIfAbsent = concurrentHashMap.putIfAbsent(annotationClass, irClassBuildClass);
            if (irClassPutIfAbsent != null) {
                irClassBuildClass = irClassPutIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(irClassBuildClass, "getOrPut(...)");
        return irClassBuildClass;
    }
}
