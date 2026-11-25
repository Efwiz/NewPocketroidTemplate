package org.jetbrains.kotlin.backend.jvm;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;

/* JADX INFO: compiled from: MemoizedValueClassAbstractReplacements.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJJ\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001f2\u0017\u0010 \u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020!0\f¢\u0006\u0002\b\"2\u0017\u0010#\u001a\u0013\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020!0\f¢\u0006\u0002\b\"H\u0004J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u000eH\u0002J\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\rH$J\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\rH$J\u0012\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020)H&J\u0010\u0010+\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001d\u001a\u00020\rJ\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001d\u001a\u00020\u000eJ\f\u0010-\u001a\u00020\u001f*\u00020\rH\u0004J\f\u0010.\u001a\u00020\u001f*\u00020\rH\u0004J\f\u0010/\u001a\u00020\u001f*\u00020\u000eH\u0004R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR \u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\fX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00180\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MemoizedValueClassAbstractReplacements;", "", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "storageManager", "Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getReplacementFunctionImpl", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getGetReplacementFunctionImpl", "()Lkotlin/jvm/functions/Function1;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "propertyMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "replaceOverriddenSymbolsImpl", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "commonBuildReplacementInner", "function", "noFakeOverride", "", "body", "", "Lkotlin/ExtensionFunctionType;", "builderBody", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrFunctionBuilder;", "computeOverrideReplacement", "createMethodReplacement", "createStaticReplacement", "getReplacementForRegularClassConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "getReplacementFunction", "replaceOverriddenSymbols", "isRemoveAtSpecialBuiltinStub", "isValueClassMemberFakeOverriddenFromJvmDefaultInterfaceMethod", "overridesOnlyMethodsFromJava", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class MemoizedValueClassAbstractReplacements {
    private final JvmBackendContext context;
    private final IrFactory irFactory;
    private final ConcurrentHashMap<IrPropertySymbol, IrProperty> propertyMap;
    private final Function1<IrSimpleFunction, List<IrSimpleFunctionSymbol>> replaceOverriddenSymbolsImpl;
    private final LockBasedStorageManager storageManager;

    protected abstract IrSimpleFunction createMethodReplacement(IrFunction function);

    protected abstract IrSimpleFunction createStaticReplacement(IrFunction function);

    protected abstract Function1<IrFunction, IrSimpleFunction> getGetReplacementFunctionImpl();

    public abstract IrConstructor getReplacementForRegularClassConstructor(IrConstructor constructor);

    public MemoizedValueClassAbstractReplacements(IrFactory irFactory, JvmBackendContext context, LockBasedStorageManager storageManager) {
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.irFactory = irFactory;
        this.context = context;
        this.storageManager = storageManager;
        this.propertyMap = new ConcurrentHashMap<>();
        MemoizedFunctionToNotNull memoizedFunctionToNotNullCreateMemoizedFunction = storageManager.createMemoizedFunction(new Function1<IrSimpleFunction, List<? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements$replaceOverriddenSymbolsImpl$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<IrSimpleFunctionSymbol> invoke(IrSimpleFunction irSimpleFunction) {
                List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
                MemoizedValueClassAbstractReplacements memoizedValueClassAbstractReplacements = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenSymbols, 10));
                Iterator<T> it = overriddenSymbols.iterator();
                while (it.hasNext()) {
                    arrayList.add(memoizedValueClassAbstractReplacements.computeOverrideReplacement(((IrSimpleFunctionSymbol) it.next()).getOwner()).getSymbol());
                }
                return arrayList;
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction, "createMemoizedFunction(...)");
        this.replaceOverriddenSymbolsImpl = memoizedFunctionToNotNullCreateMemoizedFunction;
    }

    protected final IrFactory getIrFactory() {
        return this.irFactory;
    }

    protected final JvmBackendContext getContext() {
        return this.context;
    }

    protected final LockBasedStorageManager getStorageManager() {
        return this.storageManager;
    }

    public final IrSimpleFunction getReplacementFunction(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return getGetReplacementFunctionImpl().invoke(function);
    }

    protected final boolean isRemoveAtSpecialBuiltinStub(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE) && Intrinsics.areEqual(irFunction.getName().asString(), ProductAction.ACTION_REMOVE) && irFunction.getValueParameters().size() == 1 && IrTypePredicatesKt.isInt(irFunction.getValueParameters().get(0).getType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final boolean isValueClassMemberFakeOverriddenFromJvmDefaultInterfaceMethod(IrFunction irFunction) {
        IrClass parentClassOrNull;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (!(irFunction instanceof IrSimpleFunction) || !((IrSimpleFunction) irFunction).getIsFakeOverride() || (parentClassOrNull = IrUtilsKt.getParentClassOrNull(irFunction)) == null) {
            return false;
        }
        if (!parentClassOrNull.getIsValue()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default((IrOverridableDeclaration) irFunction, false, null, 3, null);
        if (irSimpleFunction == null) {
            return false;
        }
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        if (JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(irSimpleFunction2)) && irSimpleFunction.getModality() != Modality.ABSTRACT) {
            return IrUtilsKt.isFromJava(irSimpleFunction2) || JvmIrUtilsKt.isCompiledToJvmDefault(irSimpleFunction, this.context.getConfig().getJvmDefaultMode());
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final IrSimpleFunction commonBuildReplacementInner(IrFunction function, boolean noFakeOverride, Function1<? super IrFunction, Unit> body, Function1<? super IrFunctionBuilder, Unit> builderBody) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(builderBody, "builderBody");
        IrFactory irFactory = this.irFactory;
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom(function);
        builderBody.invoke(irFunctionBuilder);
        if (noFakeOverride) {
            irFunctionBuilder.setFakeOverride(false);
        }
        irFunctionBuilder.setReturnType(function.getReturnType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(function.getParent());
        irSimpleFunctionBuildFunction.setAnnotations(function.getAnnotations());
        IrUtilsKt.copyTypeParameters$default(irSimpleFunctionBuildFunction, IrUtilsKt.getAllTypeParameters(function), null, null, 6, null);
        if (function.getMetadata() != null) {
            irSimpleFunctionBuildFunction.setMetadata(function.getMetadata());
            function.setMetadata(null);
        }
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, function instanceof IrAttributeContainer ? (IrAttributeContainer) function : null);
        if (function instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) function;
            IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol != null) {
                IrProperty owner = correspondingPropertySymbol.getOwner();
                ConcurrentHashMap<IrPropertySymbol, IrProperty> concurrentHashMap = this.propertyMap;
                IrProperty irPropertyBuildProperty = concurrentHashMap.get(correspondingPropertySymbol);
                if (irPropertyBuildProperty == null) {
                    IrFactory irFactory2 = this.irFactory;
                    IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
                    irPropertyBuilder.setName(owner.getName());
                    irPropertyBuilder.updateFrom(owner);
                    irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(irFactory2, irPropertyBuilder);
                    irPropertyBuildProperty.setParent(owner.getParent());
                    IrDeclarationsKt.copyAttributes(irPropertyBuildProperty, owner);
                    irPropertyBuildProperty.setAnnotations(owner.getAnnotations());
                    IrField staticBackingField = this.context.getCachedDeclarations().getStaticBackingField(owner);
                    if (staticBackingField == null) {
                        staticBackingField = owner.getBackingField();
                    }
                    if (staticBackingField != null) {
                        this.context.getMultiFieldValueClassReplacements().getMfvcFieldNode(staticBackingField);
                        if (!this.context.getMultiFieldValueClassReplacements().getFieldsToRemove(IrUtilsKt.getParentAsClass(owner)).contains(staticBackingField)) {
                            irPropertyBuildProperty.setBackingField(staticBackingField);
                        }
                    }
                    IrProperty irPropertyPutIfAbsent = concurrentHashMap.putIfAbsent(correspondingPropertySymbol, irPropertyBuildProperty);
                    if (irPropertyPutIfAbsent != null) {
                        irPropertyBuildProperty = irPropertyPutIfAbsent;
                    }
                }
                IrProperty irProperty = irPropertyBuildProperty;
                irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irProperty.getSymbol());
                if (Intrinsics.areEqual(function, owner.getGetter())) {
                    irProperty.setGetter(irSimpleFunctionBuildFunction);
                } else {
                    if (!Intrinsics.areEqual(function, owner.getSetter())) {
                        throw new IllegalStateException(("Orphaned property getter/setter: " + RenderIrElementKt.render$default(function, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    irProperty.setSetter(irSimpleFunctionBuildFunction);
                }
            }
            irSimpleFunctionBuildFunction.setOverriddenSymbols(replaceOverriddenSymbols(irSimpleFunction));
        }
        body.invoke(irSimpleFunctionBuildFunction);
        return irSimpleFunctionBuildFunction;
    }

    public final List<IrSimpleFunctionSymbol> replaceOverriddenSymbols(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return function.getOverriddenSymbols().isEmpty() ? CollectionsKt.emptyList() : this.replaceOverriddenSymbolsImpl.invoke(function);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction computeOverrideReplacement(IrSimpleFunction function) {
        IrSimpleFunction replacementFunction = getReplacementFunction(function);
        if (replacementFunction != null) {
            return replacementFunction;
        }
        function.setOverriddenSymbols(replaceOverriddenSymbols(function));
        return function;
    }

    protected final boolean overridesOnlyMethodsFromJava(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        List listAllOverridden$default = IrUtilsKt.allOverridden$default(irSimpleFunction, false, 1, null);
        if (!(listAllOverridden$default instanceof Collection) || !listAllOverridden$default.isEmpty()) {
            Iterator it = listAllOverridden$default.iterator();
            while (it.hasNext()) {
                if (!IrUtilsKt.isFromJava((IrSimpleFunction) it.next())) {
                    return false;
                }
            }
        }
        return true;
    }
}
