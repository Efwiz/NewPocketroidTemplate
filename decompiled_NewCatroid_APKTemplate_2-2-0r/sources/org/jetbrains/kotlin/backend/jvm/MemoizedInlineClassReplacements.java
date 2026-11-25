package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.storage.CacheWithNotNullValues;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;

/* JADX INFO: compiled from: MemoizedInlineClassReplacements.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ;\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020$2\b\b\u0002\u0010%\u001a\u00020\u00032\u0017\u0010&\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020'0\n¢\u0006\u0002\b(H\u0002JA\u0010)\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010*\u001a\u00020\u00032\u0017\u0010&\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020'0\n¢\u0006\u0002\b(H\u0002J\u0010\u0010+\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u0010H\u0014J\u0010\u0010,\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u0010H\u0014J\u0012\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020.H\u0016J\u0016\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\u000b2\u0006\u00102\u001a\u000203R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\f0\nX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u001d\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R*\u0010\u001b\u001a\u001e\u0012\f\u0012\n \u001d*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \u001d*\u0004\u0018\u00010\f0\f0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001e\u001a\u00020\u0003*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MemoizedInlineClassReplacements;", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedValueClassAbstractReplacements;", "mangleReturnTypes", "", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(ZLorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getBoxFunction", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getGetBoxFunction", "()Lkotlin/jvm/functions/Function1;", "getReplacementFunctionImpl", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getGetReplacementFunctionImpl", "getUnboxFunction", "getGetUnboxFunction", "mangleCallsToJavaMethodsWithValueClasses", "originalFunctionForMethodReplacement", "", "getOriginalFunctionForMethodReplacement", "()Ljava/util/Map;", "originalFunctionForStaticReplacement", "getOriginalFunctionForStaticReplacement", "specializedEqualsCache", "Lorg/jetbrains/kotlin/storage/CacheWithNotNullValues;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "needsReplacement", "getNeedsReplacement", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z", "buildReplacement", "function", "replacementOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "noFakeOverride", "body", "", "Lkotlin/ExtensionFunctionType;", "buildReplacementInner", "useOldManglingScheme", "createMethodReplacement", "createStaticReplacement", "getReplacementForRegularClassConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "getSpecializedEqualsMethod", "irClass", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MemoizedInlineClassReplacements extends MemoizedValueClassAbstractReplacements {
    private final Function1<IrClass, IrSimpleFunction> getBoxFunction;
    private final Function1<IrFunction, IrSimpleFunction> getReplacementFunctionImpl;
    private final Function1<IrClass, IrSimpleFunction> getUnboxFunction;
    private final boolean mangleCallsToJavaMethodsWithValueClasses;
    private final boolean mangleReturnTypes;
    private final Map<IrFunction, IrFunction> originalFunctionForMethodReplacement;
    private final Map<IrFunction, IrFunction> originalFunctionForStaticReplacement;
    private final CacheWithNotNullValues<IrClass, IrSimpleFunction> specializedEqualsCache;

    @Override // org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements
    public IrConstructor getReplacementForRegularClassConstructor(IrConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MemoizedInlineClassReplacements(boolean z, final IrFactory irFactory, JvmBackendContext context) {
        super(irFactory, context, new LockBasedStorageManager("inline-class-replacements"));
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(context, "context");
        this.mangleReturnTypes = z;
        this.originalFunctionForStaticReplacement = new ConcurrentHashMap();
        this.originalFunctionForMethodReplacement = new ConcurrentHashMap();
        this.mangleCallsToJavaMethodsWithValueClasses = context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.MangleCallsToJavaMethodsWithValueClasses);
        MemoizedFunctionToNullable memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues = getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<IrFunction, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedInlineClassReplacements$getReplacementFunctionImpl$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrFunction irFunction) {
                Intrinsics.checkNotNull(irFunction);
                IrFunction irFunction2 = irFunction;
                if (AdditionalIrUtilsKt.isLocal(irFunction2) && (irFunction instanceof IrSimpleFunction) && ((IrSimpleFunction) irFunction).getOverriddenSymbols().isEmpty()) {
                    return null;
                }
                if ((Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE) && Intrinsics.areEqual(irFunction.getVisibility(), DescriptorVisibilities.LOCAL)) || JvmIrUtilsKt.isStaticValueClassReplacement(irFunction2) || Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD.INSTANCE)) {
                    return null;
                }
                if (irFunction.getOrigin().getIsSynthetic() && !Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.SYNTHETIC_GENERATED_SAM_IMPLEMENTATION.INSTANCE)) {
                    return null;
                }
                if (InlineClassAbiKt.isInlineClassFieldGetter(irFunction)) {
                    if (InlineClassAbiKt.getHasMangledReturnType(irFunction)) {
                        return this.this$0.createMethodReplacement(irFunction);
                    }
                    return null;
                }
                IrDeclarationParent parent = irFunction.getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                if (irClass != null && IrDeclarationsKt.isSingleFieldValueClass(irClass)) {
                    if (IrUtilsKt.isValueClassTypedEquals(irFunction)) {
                        final IrSimpleFunction irSimpleFunctionCreateStaticReplacement = this.this$0.createStaticReplacement(irFunction);
                        MemoizedInlineClassReplacements memoizedInlineClassReplacements = this.this$0;
                        irSimpleFunctionCreateStaticReplacement.setName(InlineClassDescriptorResolver.SPECIALIZED_EQUALS_NAME);
                        memoizedInlineClassReplacements.specializedEqualsCache.computeIfAbsent(IrUtilsKt.getParentAsClass(irSimpleFunctionCreateStaticReplacement), new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedInlineClassReplacements$getReplacementFunctionImpl$1$1$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final IrSimpleFunction invoke() {
                                return irSimpleFunctionCreateStaticReplacement;
                            }
                        });
                        return irSimpleFunctionCreateStaticReplacement;
                    }
                    if (this.this$0.isRemoveAtSpecialBuiltinStub(irFunction)) {
                        return null;
                    }
                    if (this.this$0.isValueClassMemberFakeOverriddenFromJvmDefaultInterfaceMethod(irFunction) || Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE)) {
                        return this.this$0.createMethodReplacement(irFunction);
                    }
                    return this.this$0.createStaticReplacement(irFunction);
                }
                if ((irFunction instanceof IrSimpleFunction) && this.this$0.getNeedsReplacement((IrSimpleFunction) irFunction)) {
                    return this.this$0.createMethodReplacement(irFunction);
                }
                return null;
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues, "createMemoizedFunctionWithNullableValues(...)");
        this.getReplacementFunctionImpl = memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues;
        MemoizedFunctionToNotNull memoizedFunctionToNotNullCreateMemoizedFunction = getStorageManager().createMemoizedFunction(new Function1<IrClass, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedInlineClassReplacements$getBoxFunction$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrClass irClass) {
                Intrinsics.checkNotNull(irClass);
                if (!IrDeclarationsKt.isSingleFieldValueClass(irClass)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                IrFactory irFactory2 = irFactory;
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                Name nameIdentifier = Name.identifier(KotlinTypeMapper.BOX_JVM_METHOD_NAME);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_INLINE_CLASS_MEMBER.INSTANCE);
                irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory2, irFunctionBuilder);
                irSimpleFunctionBuildFunction.setParent(irClass);
                IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionBuildFunction, irClass, null, null, 6, null);
                IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                irValueParameterBuilder.setName(InlineClassDescriptorResolver.INSTANCE.getBOXING_VALUE_PARAMETER_NAME());
                InlineClassRepresentation<IrSimpleType> inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(irClass);
                Intrinsics.checkNotNull(inlineClassRepresentation);
                irValueParameterBuilder.setType((IrType) inlineClassRepresentation.getUnderlyingType());
                if (irValueParameterBuilder.getIndex() == -1) {
                    irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
                }
                irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction)));
                return irSimpleFunctionBuildFunction;
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction, "createMemoizedFunction(...)");
        this.getBoxFunction = memoizedFunctionToNotNullCreateMemoizedFunction;
        MemoizedFunctionToNotNull memoizedFunctionToNotNullCreateMemoizedFunction2 = getStorageManager().createMemoizedFunction(new Function1<IrClass, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedInlineClassReplacements$getUnboxFunction$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrClass irClass) {
                Intrinsics.checkNotNull(irClass);
                if (!IrDeclarationsKt.isSingleFieldValueClass(irClass)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                IrFactory irFactory2 = irFactory;
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                Name nameIdentifier = Name.identifier(KotlinTypeMapper.UNBOX_JVM_METHOD_NAME);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_INLINE_CLASS_MEMBER.INSTANCE);
                InlineClassRepresentation<IrSimpleType> inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(irClass);
                Intrinsics.checkNotNull(inlineClassRepresentation);
                irFunctionBuilder.setReturnType((IrType) inlineClassRepresentation.getUnderlyingType());
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory2, irFunctionBuilder);
                irSimpleFunctionBuildFunction.setParent(irClass);
                IrUtilsKt.createDispatchReceiverParameter$default(irSimpleFunctionBuildFunction, null, 1, null);
                return irSimpleFunctionBuildFunction;
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction2, "createMemoizedFunction(...)");
        this.getUnboxFunction = memoizedFunctionToNotNullCreateMemoizedFunction2;
        CacheWithNotNullValues<IrClass, IrSimpleFunction> cacheWithNotNullValuesCreateCacheWithNotNullValues = getStorageManager().createCacheWithNotNullValues();
        Intrinsics.checkNotNullExpressionValue(cacheWithNotNullValuesCreateCacheWithNotNullValues, "createCacheWithNotNullValues(...)");
        this.specializedEqualsCache = cacheWithNotNullValuesCreateCacheWithNotNullValues;
    }

    public final Map<IrFunction, IrFunction> getOriginalFunctionForStaticReplacement() {
        return this.originalFunctionForStaticReplacement;
    }

    public final Map<IrFunction, IrFunction> getOriginalFunctionForMethodReplacement() {
        return this.originalFunctionForMethodReplacement;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements
    protected Function1<IrFunction, IrSimpleFunction> getGetReplacementFunctionImpl() {
        return this.getReplacementFunctionImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getNeedsReplacement(IrSimpleFunction irSimpleFunction) {
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        if (!InlineClassAbiKt.hasMangledParameters$default(irSimpleFunction2, false, false, 1, null) && (!this.mangleReturnTypes || !InlineClassAbiKt.getHasMangledReturnType(irSimpleFunction2))) {
            return false;
        }
        if (IrUtilsKt.isFromJava(irSimpleFunction)) {
            return this.mangleCallsToJavaMethodsWithValueClasses && !overridesOnlyMethodsFromJava(irSimpleFunction);
        }
        return true;
    }

    public final Function1<IrClass, IrSimpleFunction> getGetBoxFunction() {
        return this.getBoxFunction;
    }

    public final Function1<IrClass, IrSimpleFunction> getGetUnboxFunction() {
        return this.getUnboxFunction;
    }

    public final IrSimpleFunction getSpecializedEqualsMethod(final IrClass irClass, final IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        if (!IrDeclarationsKt.isSingleFieldValueClass(irClass)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrSimpleFunction irSimpleFunctionComputeIfAbsent = this.specializedEqualsCache.computeIfAbsent(irClass, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedInlineClassReplacements.getSpecializedEqualsMethod.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrFactory irFactory = MemoizedInlineClassReplacements.this.getIrFactory();
                IrBuiltIns irBuiltIns2 = irBuiltIns;
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                irFunctionBuilder.setName(InlineClassDescriptorResolver.SPECIALIZED_EQUALS_NAME);
                irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.INLINE_CLASS_GENERATED_IMPL_METHOD.INSTANCE);
                irFunctionBuilder.setReturnType(irBuiltIns2.getBooleanType());
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                IrClass irClass2 = irClass;
                irSimpleFunctionBuildFunction.setParent(irClass2);
                IrClassSymbol symbol = irClass2.getSymbol();
                int size = irClass2.getTypeParameters().size();
                ArrayList arrayList = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    arrayList.add(IrStarProjectionImpl.INSTANCE);
                }
                IrSimpleTypeImpl irSimpleTypeImpl = new IrSimpleTypeImpl((IrClassifierSymbol) symbol, false, (List) arrayList, CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
                IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                irValueParameterBuilder.setName(InlineClassDescriptorResolver.INSTANCE.getSPECIALIZED_EQUALS_FIRST_PARAMETER_NAME());
                IrSimpleTypeImpl irSimpleTypeImpl2 = irSimpleTypeImpl;
                irValueParameterBuilder.setType(irSimpleTypeImpl2);
                if (irValueParameterBuilder.getIndex() == -1) {
                    irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
                }
                IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction2)));
                IrValueParameterBuilder irValueParameterBuilder2 = new IrValueParameterBuilder();
                irValueParameterBuilder2.setName(InlineClassDescriptorResolver.INSTANCE.getSPECIALIZED_EQUALS_SECOND_PARAMETER_NAME());
                irValueParameterBuilder2.setType(irSimpleTypeImpl2);
                if (irValueParameterBuilder2.getIndex() == -1) {
                    irValueParameterBuilder2.setIndex(irSimpleFunction.getValueParameters().size());
                }
                irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder2, irSimpleFunction2)));
                return irSimpleFunctionBuildFunction;
            }
        });
        Intrinsics.checkNotNullExpressionValue(irSimpleFunctionComputeIfAbsent, "computeIfAbsent(...)");
        return irSimpleFunctionComputeIfAbsent;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements
    protected IrSimpleFunction createMethodReplacement(final IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return buildReplacement$default(this, function, function.getOrigin(), false, new Function1<IrFunction, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedInlineClassReplacements.createMethodReplacement.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrFunction irFunction) {
                invoke2(irFunction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFunction buildReplacement) {
                IrValueParameter irValueParameterCopyTo$default;
                IrFunction irFunction;
                IrExpressionBody irExpressionBody;
                Intrinsics.checkNotNullParameter(buildReplacement, "$this$buildReplacement");
                MemoizedInlineClassReplacements.this.getOriginalFunctionForMethodReplacement().put(buildReplacement, function);
                IrValueParameter dispatchReceiverParameter = function.getDispatchReceiverParameter();
                buildReplacement.setDispatchReceiverParameter(dispatchReceiverParameter != null ? IrUtilsKt.copyTo$default(dispatchReceiverParameter, buildReplacement, null, -1, 0, 0, null, null, null, null, null, false, false, false, 8186, null) : null);
                IrValueParameter extensionReceiverParameter = function.getExtensionReceiverParameter();
                if (extensionReceiverParameter != null) {
                    Name nameIdentifier = Name.identifier(JvmIrUtilsKt.extensionReceiverName(function, MemoizedInlineClassReplacements.this.getContext().getState()));
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(extensionReceiverParameter, buildReplacement, null, -1, 0, 0, nameIdentifier, null, null, null, null, false, false, false, 8154, null);
                } else {
                    irValueParameterCopyTo$default = null;
                }
                buildReplacement.setExtensionReceiverParameter(irValueParameterCopyTo$default);
                buildReplacement.setContextReceiverParametersCount(function.getContextReceiverParametersCount());
                List<IrValueParameter> valueParameters = function.getValueParameters();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
                int i = 0;
                for (Object obj : valueParameters) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrValueParameter irValueParameter = (IrValueParameter) obj;
                    ArrayList arrayList2 = arrayList;
                    IrValueParameter irValueParameterCopyTo$default2 = IrUtilsKt.copyTo$default(irValueParameter, buildReplacement, null, i, 0, 0, null, null, null, null, null, false, false, false, 7674, null);
                    IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                    if (defaultValue != null) {
                        irFunction = buildReplacement;
                        irExpressionBody = (IrExpressionBody) PatchDeclarationParentsKt.patchDeclarationParents(defaultValue, irFunction);
                    } else {
                        irFunction = buildReplacement;
                        irExpressionBody = null;
                    }
                    irValueParameterCopyTo$default2.setDefaultValue(irExpressionBody);
                    arrayList2.add(irValueParameterCopyTo$default2);
                    arrayList = arrayList2;
                    buildReplacement = irFunction;
                    i = i2;
                }
                IrFunction irFunction2 = buildReplacement;
                irFunction2.setValueParameters(arrayList);
                MemoizedInlineClassReplacements.this.getContext().remapMultiFieldValueClassStructure(function, irFunction2, null);
            }
        }, 4, null);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements
    protected IrSimpleFunction createStaticReplacement(final IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return buildReplacement(function, JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_REPLACEMENT.INSTANCE, true, new Function1<IrFunction, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedInlineClassReplacements.createStaticReplacement.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrFunction irFunction) {
                invoke2(irFunction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFunction buildReplacement) {
                ArrayList arrayList;
                Intrinsics.checkNotNullParameter(buildReplacement, "$this$buildReplacement");
                MemoizedInlineClassReplacements.this.getOriginalFunctionForStaticReplacement().put(buildReplacement, function);
                ArrayList arrayList2 = new ArrayList();
                if (function.getDispatchReceiverParameter() != null) {
                    IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(function).getThisReceiver();
                    Intrinsics.checkNotNull(thisReceiver);
                    int size = arrayList2.size();
                    Name nameIdentifier = Name.identifier(ParameterDescription.NAME_PREFIX + arrayList2.size());
                    IrSimpleType defaultType = IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(function));
                    IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER moved_dispatch_receiver = IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE;
                    Intrinsics.checkNotNull(nameIdentifier);
                    arrayList2.add(IrUtilsKt.copyTo$default(thisReceiver, buildReplacement, moved_dispatch_receiver, size, 0, 0, nameIdentifier, null, defaultType, null, null, false, false, false, 8024, null));
                }
                if (function.getContextReceiverParametersCount() != 0) {
                    int i = 0;
                    for (Object obj : CollectionsKt.take(function.getValueParameters(), function.getContextReceiverParametersCount())) {
                        int i2 = i + 1;
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        ArrayList arrayList3 = arrayList2;
                        int size2 = arrayList3.size();
                        Name nameIdentifier2 = Name.identifier("contextReceiver" + i);
                        IrDeclarationOrigin.MOVED_CONTEXT_RECEIVER moved_context_receiver = IrDeclarationOrigin.MOVED_CONTEXT_RECEIVER.INSTANCE;
                        Intrinsics.checkNotNull(nameIdentifier2);
                        arrayList3.add(IrUtilsKt.copyTo$default((IrValueParameter) obj, buildReplacement, moved_context_receiver, size2, 0, 0, nameIdentifier2, null, null, null, null, false, false, false, 8152, null));
                        i = i2;
                        arrayList2 = arrayList3;
                    }
                }
                ArrayList arrayList4 = arrayList2;
                IrValueParameter extensionReceiverParameter = function.getExtensionReceiverParameter();
                if (extensionReceiverParameter != null) {
                    IrFunction irFunction = function;
                    MemoizedInlineClassReplacements memoizedInlineClassReplacements = MemoizedInlineClassReplacements.this;
                    int size3 = arrayList4.size();
                    Name nameIdentifier3 = Name.identifier(JvmIrUtilsKt.extensionReceiverName(irFunction, memoizedInlineClassReplacements.getContext().getState()));
                    IrDeclarationOrigin.MOVED_EXTENSION_RECEIVER moved_extension_receiver = IrDeclarationOrigin.MOVED_EXTENSION_RECEIVER.INSTANCE;
                    Intrinsics.checkNotNull(nameIdentifier3);
                    arrayList = arrayList4;
                    arrayList4.add(IrUtilsKt.copyTo$default(extensionReceiverParameter, buildReplacement, moved_extension_receiver, size3, 0, 0, nameIdentifier3, null, null, null, null, false, false, false, 8152, null));
                } else {
                    arrayList = arrayList4;
                }
                for (IrValueParameter irValueParameter : CollectionsKt.drop(function.getValueParameters(), function.getContextReceiverParametersCount())) {
                    ArrayList arrayList5 = arrayList;
                    ArrayList arrayList6 = arrayList5;
                    IrValueParameter irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(irValueParameter, buildReplacement, null, arrayList5.size(), 0, 0, null, null, null, null, null, false, false, false, 7674, null);
                    IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                    irValueParameterCopyTo$default.setDefaultValue(defaultValue != null ? (IrExpressionBody) PatchDeclarationParentsKt.patchDeclarationParents(defaultValue, buildReplacement) : null);
                    arrayList6.add(irValueParameterCopyTo$default);
                    arrayList = arrayList5;
                }
                buildReplacement.setValueParameters(arrayList);
                MemoizedInlineClassReplacements.this.getContext().remapMultiFieldValueClassStructure(function, buildReplacement, null);
            }
        });
    }

    static /* synthetic */ IrSimpleFunction buildReplacement$default(MemoizedInlineClassReplacements memoizedInlineClassReplacements, IrFunction irFunction, IrDeclarationOrigin irDeclarationOrigin, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return memoizedInlineClassReplacements.buildReplacement(irFunction, irDeclarationOrigin, z, function1);
    }

    private final IrSimpleFunction buildReplacement(IrFunction function, IrDeclarationOrigin replacementOrigin, boolean noFakeOverride, Function1<? super IrFunction, Unit> body) {
        ClassId parentClassId;
        boolean useOldManglingSchemeForFunctionsWithInlineClassesInSignatures = getContext().getConfig().getUseOldManglingSchemeForFunctionsWithInlineClassesInSignatures();
        IrSimpleFunction irSimpleFunctionBuildReplacementInner = buildReplacementInner(function, replacementOrigin, noFakeOverride, useOldManglingSchemeForFunctionsWithInlineClassesInSignatures, body);
        if (!useOldManglingSchemeForFunctionsWithInlineClassesInSignatures) {
            String strAsString = irSimpleFunctionBuildReplacementInner.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            boolean zAreEqual = false;
            if (StringsKt.contains$default((CharSequence) strAsString, '-', false, 2, (Object) null) && JvmIrUtilsKt.getParentClassId(function) != null) {
                IrSimpleFunction irSimpleFunction = function instanceof IrSimpleFunction ? (IrSimpleFunction) function : null;
                IrSimpleFunction irSimpleFunction2 = irSimpleFunction != null ? (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction, true, null, 2, null) : null;
                if (irSimpleFunction2 != null && (parentClassId = JvmIrUtilsKt.getParentClassId(irSimpleFunction2)) != null) {
                    zAreEqual = Intrinsics.areEqual((Object) JvmIrUtilsKt.classFileContainsMethod(parentClassId, irSimpleFunctionBuildReplacementInner, getContext()), (Object) false);
                }
                if (zAreEqual) {
                    return buildReplacementInner(function, replacementOrigin, noFakeOverride, true, body);
                }
            }
        }
        return irSimpleFunctionBuildReplacementInner;
    }

    private final IrSimpleFunction buildReplacementInner(final IrFunction function, final IrDeclarationOrigin replacementOrigin, boolean noFakeOverride, final boolean useOldManglingScheme, Function1<? super IrFunction, Unit> body) {
        return commonBuildReplacementInner(function, noFakeOverride, body, new Function1<IrFunctionBuilder, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedInlineClassReplacements.buildReplacementInner.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrFunctionBuilder irFunctionBuilder) {
                invoke2(irFunctionBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFunctionBuilder commonBuildReplacementInner) {
                JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_CONSTRUCTOR static_inline_class_constructor;
                Intrinsics.checkNotNullParameter(commonBuildReplacementInner, "$this$commonBuildReplacementInner");
                if (function instanceof IrConstructor) {
                    commonBuildReplacementInner.setModality(Modality.OPEN);
                }
                if (Intrinsics.areEqual(function.getOrigin(), IrDeclarationOrigin.GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER.INSTANCE)) {
                    static_inline_class_constructor = JvmLoweredDeclarationOrigin.INLINE_CLASS_GENERATED_IMPL_METHOD.INSTANCE;
                } else {
                    IrFunction irFunction = function;
                    if ((irFunction instanceof IrConstructor) && IrDeclarationsKt.isSingleFieldValueClass(AdditionalIrUtilsKt.getConstructedClass((IrConstructor) irFunction))) {
                        static_inline_class_constructor = JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_CONSTRUCTOR.INSTANCE;
                    } else {
                        static_inline_class_constructor = replacementOrigin;
                    }
                }
                commonBuildReplacementInner.setOrigin(static_inline_class_constructor);
                commonBuildReplacementInner.setName(InlineClassAbi.INSTANCE.mangledNameFor(this.getContext(), function, this.mangleReturnTypes, useOldManglingScheme));
            }
        });
    }
}
