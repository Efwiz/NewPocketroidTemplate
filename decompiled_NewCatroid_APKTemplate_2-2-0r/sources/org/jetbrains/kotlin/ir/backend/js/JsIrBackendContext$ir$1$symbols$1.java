package org.jetbrains.kotlin.ir.backend.js;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;

/* JADX INFO: compiled from: JsIrBackendContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000O\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u001a\n\u0002\u0010\b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010B\u001a\u00020\r2\u0006\u0010C\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020\r2\u0006\u0010C\u001a\u00020DH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0014\u0010\u0015\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0014\u0010\u0017\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0014\u0010\u0019\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000fR\u0014\u0010\u001b\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u000fR\u0014\u0010\u001d\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0012R'\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00040\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b!\u0010\tR\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0012R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0012R\u0014\u0010.\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u0012R\u0014\u00100\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0012R\u0014\u00102\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u0010\u0012R\u0014\u00104\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u0012R\u0014\u00106\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\u0012R\u0014\u00108\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\u0012R'\u0010:\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00040\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b<\u0010#\u001a\u0004\b;\u0010\tR\u0014\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00040%X\u0082\u0004¢\u0006\u0002\n\u0000R'\u0010>\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00040\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b@\u0010#\u001a\u0004\b?\u0010\tR\u0014\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00040%X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"org/jetbrains/kotlin/ir/backend/js/JsIrBackendContext$ir$1$symbols$1", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "_arraysContentEquals", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "arraysContentEquals", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getArraysContentEquals", "()Ljava/util/Map;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "continuationClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getContinuationClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "coroutineContextGetter", "getCoroutineContextGetter", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "coroutineGetContext", "getCoroutineGetContext", "coroutineImpl", "getCoroutineImpl", "coroutineSuspendedGetter", "getCoroutineSuspendedGetter", "defaultConstructorMarker", "getDefaultConstructorMarker", "functionAdapter", "getFunctionAdapter", "getContinuation", "getGetContinuation", "getProgressionLastElementByReturnType", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getGetProgressionLastElementByReturnType", "getProgressionLastElementByReturnType$delegate", "Lkotlin/Lazy;", "getProgressionLastElementSymbols", "", "returnIfSuspended", "getReturnIfSuspended", "stringBuilder", "", "getStringBuilder", "()Ljava/lang/Void;", "suspendCoroutineUninterceptedOrReturn", "getSuspendCoroutineUninterceptedOrReturn", Namer.THROW_ILLEGAL_ARGUMENT_EXCEPTION_FUN_NAME, "getThrowIAE", "throwISE", "getThrowISE", "throwKotlinNothingValueException", "getThrowKotlinNothingValueException", "throwNullPointerException", "getThrowNullPointerException", "throwTypeCastException", "getThrowTypeCastException", "throwUninitializedPropertyAccessException", "getThrowUninitializedPropertyAccessException", "toUIntByExtensionReceiver", "getToUIntByExtensionReceiver", "toUIntByExtensionReceiver$delegate", "toUIntSymbols", "toULongByExtensionReceiver", "getToULongByExtensionReceiver", "toULongByExtensionReceiver$delegate", "toULongSymbols", "functionN", "n", "", "suspendFunctionN", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrBackendContext$ir$1$symbols$1 extends Symbols {
    private final List<IrSimpleFunctionSymbol> _arraysContentEquals;
    private final JsIrBackendContext context;
    private final IrClassSymbol continuationClass;
    private final IrSimpleFunctionSymbol coroutineContextGetter;
    private final IrSimpleFunctionSymbol coroutineGetContext;
    private final IrClassSymbol coroutineImpl;
    private final IrSimpleFunctionSymbol coroutineSuspendedGetter;
    private final IrClassSymbol defaultConstructorMarker;
    private final IrSimpleFunctionSymbol getContinuation;

    /* JADX INFO: renamed from: getProgressionLastElementByReturnType$delegate, reason: from kotlin metadata */
    private final Lazy getProgressionLastElementByReturnType;
    private final Iterable<IrSimpleFunctionSymbol> getProgressionLastElementSymbols;
    private final IrSimpleFunctionSymbol returnIfSuspended;
    private final IrSimpleFunctionSymbol suspendCoroutineUninterceptedOrReturn;
    final /* synthetic */ JsIrBackendContext this$0;
    private final IrSimpleFunctionSymbol throwIAE;
    private final IrSimpleFunctionSymbol throwISE;
    private final IrSimpleFunctionSymbol throwKotlinNothingValueException;
    private final IrSimpleFunctionSymbol throwNullPointerException;
    private final IrSimpleFunctionSymbol throwTypeCastException;
    private final IrSimpleFunctionSymbol throwUninitializedPropertyAccessException;

    /* JADX INFO: renamed from: toUIntByExtensionReceiver$delegate, reason: from kotlin metadata */
    private final Lazy toUIntByExtensionReceiver;
    private final Iterable<IrSimpleFunctionSymbol> toUIntSymbols;

    /* JADX INFO: renamed from: toULongByExtensionReceiver$delegate, reason: from kotlin metadata */
    private final Lazy toULongByExtensionReceiver;
    private final Iterable<IrSimpleFunctionSymbol> toULongSymbols;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Code duplicated, block: B:10:0x017a  */
    JsIrBackendContext$ir$1$symbols$1(JsIrBackendContext jsIrBackendContext, IrBuiltIns irBuiltIns, SymbolTable symbolTable) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction;
        super(irBuiltIns, symbolTable);
        this.this$0 = jsIrBackendContext;
        this.context = jsIrBackendContext;
        DescriptorSymbolTableExtension descriptorExtension = jsIrBackendContext.getSymbolTable().getDescriptorExtension();
        FqName fqNameChild = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier("THROW_NPE"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        this.throwNullPointerException = descriptorExtension.referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(fqNameChild)));
        DescriptorSymbolTableExtension descriptorExtension2 = jsIrBackendContext.getSymbolTable().getDescriptorExtension();
        FqName fqNameChild2 = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier(BuiltInOperatorNames.NO_WHEN_BRANCH_MATCHED_EXCEPTION));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        descriptorExtension2.referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(fqNameChild2)));
        DescriptorSymbolTableExtension descriptorExtension3 = jsIrBackendContext.getSymbolTable().getDescriptorExtension();
        FqName fqNameChild3 = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier(BuiltInOperatorNames.THROW_CCE));
        Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
        this.throwTypeCastException = descriptorExtension3.referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(fqNameChild3)));
        this.throwUninitializedPropertyAccessException = jsIrBackendContext.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(new FqName("kotlin.throwUninitializedPropertyAccessException"))));
        this.throwKotlinNothingValueException = jsIrBackendContext.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(new FqName("kotlin.throwKotlinNothingValueException"))));
        this.defaultConstructorMarker = jsIrBackendContext.getSymbolTable().getDescriptorExtension().referenceClass(jsIrBackendContext.getJsInternalClass$backend_js("DefaultConstructorMarker"));
        DescriptorSymbolTableExtension descriptorExtension4 = jsIrBackendContext.getSymbolTable().getDescriptorExtension();
        FqName fqNameChild4 = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier(BuiltInOperatorNames.THROW_ISE));
        Intrinsics.checkNotNullExpressionValue(fqNameChild4, "child(...)");
        this.throwISE = descriptorExtension4.referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(fqNameChild4)));
        DescriptorSymbolTableExtension descriptorExtension5 = jsIrBackendContext.getSymbolTable().getDescriptorExtension();
        FqName fqNameChild5 = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier("THROW_IAE"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild5, "child(...)");
        this.throwIAE = descriptorExtension5.referenceSimpleFunction(CollectionsKt.single((List) jsIrBackendContext.getFunctions(fqNameChild5)));
        this.coroutineImpl = jsIrBackendContext.getCoroutineSymbols().getCoroutineImpl();
        this.coroutineSuspendedGetter = jsIrBackendContext.getCoroutineSymbols().getCoroutineSuspendedGetter();
        List<SimpleFunctionDescriptor> functions = jsIrBackendContext.getFunctions(new FqName("kotlin.collections.contentEquals"));
        ArrayList arrayList = new ArrayList();
        for (SimpleFunctionDescriptor simpleFunctionDescriptor : functions) {
            if (simpleFunctionDescriptor.getExtensionReceiverParameter() != null) {
                ReceiverParameterDescriptor extensionReceiverParameter = simpleFunctionDescriptor.getExtensionReceiverParameter();
                Intrinsics.checkNotNull(extensionReceiverParameter);
                KotlinType type = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (KotlinTypeKt.isNullable(type)) {
                    irSimpleFunctionSymbolReferenceSimpleFunction = jsIrBackendContext.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(simpleFunctionDescriptor);
                } else {
                    irSimpleFunctionSymbolReferenceSimpleFunction = null;
                }
            } else {
                irSimpleFunctionSymbolReferenceSimpleFunction = null;
            }
            if (irSimpleFunctionSymbolReferenceSimpleFunction != null) {
                arrayList.add(irSimpleFunctionSymbolReferenceSimpleFunction);
            }
        }
        this._arraysContentEquals = arrayList;
        this.getContinuation = jsIrBackendContext.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(jsIrBackendContext.getJsInternalFunction$backend_js("getContinuation"));
        this.continuationClass = this.context.getCoroutineSymbols().getContinuationClass();
        DescriptorSymbolTableExtension descriptorExtension6 = jsIrBackendContext.getSymbolTable().getDescriptorExtension();
        PropertyGetterDescriptor getter = this.context.getCoroutineSymbols().getCoroutineContextProperty().getGetter();
        Intrinsics.checkNotNull(getter);
        this.coroutineContextGetter = descriptorExtension6.referenceSimpleFunction(getter);
        this.suspendCoroutineUninterceptedOrReturn = jsIrBackendContext.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(jsIrBackendContext.getJsInternalFunction$backend_js(JsIrBackendContext.COROUTINE_SUSPEND_OR_RETURN_JS_NAME));
        this.coroutineGetContext = jsIrBackendContext.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(jsIrBackendContext.getJsInternalFunction$backend_js(JsIrBackendContext.GET_COROUTINE_CONTEXT_NAME));
        this.returnIfSuspended = jsIrBackendContext.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(jsIrBackendContext.getJsInternalFunction$backend_js("returnIfSuspended"));
        IrBuiltIns irBuiltIns2 = getIrBuiltIns();
        Name nameIdentifier = Name.identifier("getProgressionLastElement");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.getProgressionLastElementSymbols = irBuiltIns2.findFunctions(nameIdentifier, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "internal");
        this.getProgressionLastElementByReturnType = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext$ir$1$symbols$1$getProgressionLastElementByReturnType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol> invoke() {
                Iterable iterable = this.this$0.getProgressionLastElementSymbols;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
                for (Object obj : iterable) {
                    linkedHashMap.put(IrTypesKt.getClassifierOrFail(((IrSimpleFunctionSymbol) obj).getOwner().getReturnType()), obj);
                }
                return linkedHashMap;
            }
        });
        IrBuiltIns irBuiltIns3 = getIrBuiltIns();
        Name nameIdentifier2 = Name.identifier(Namer.UINT_FROM_INT);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        this.toUIntSymbols = irBuiltIns3.findFunctions(nameIdentifier2, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        this.toUIntByExtensionReceiver = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext$ir$1$symbols$1$toUIntByExtensionReceiver$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol> invoke() {
                IrType type2;
                IrClassifierSymbol classifierOrFail;
                Iterable iterable = this.this$0.toUIntSymbols;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
                for (Object obj : iterable) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) obj;
                    IrValueParameter extensionReceiverParameter2 = irSimpleFunctionSymbol.getOwner().getExtensionReceiverParameter();
                    if (extensionReceiverParameter2 == null || (type2 = extensionReceiverParameter2.getType()) == null || (classifierOrFail = IrTypesKt.getClassifierOrFail(type2)) == null) {
                        throw new IllegalStateException(("Expected extension receiver for " + RenderIrElementKt.render$default(irSimpleFunctionSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    linkedHashMap.put(classifierOrFail, obj);
                }
                return linkedHashMap;
            }
        });
        IrBuiltIns irBuiltIns4 = getIrBuiltIns();
        Name nameIdentifier3 = Name.identifier("toULong");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        this.toULongSymbols = irBuiltIns4.findFunctions(nameIdentifier3, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        this.toULongByExtensionReceiver = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext$ir$1$symbols$1$toULongByExtensionReceiver$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol> invoke() {
                IrType type2;
                IrClassifierSymbol classifierOrFail;
                Iterable iterable = this.this$0.toULongSymbols;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
                for (Object obj : iterable) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) obj;
                    IrValueParameter extensionReceiverParameter2 = irSimpleFunctionSymbol.getOwner().getExtensionReceiverParameter();
                    if (extensionReceiverParameter2 == null || (type2 = extensionReceiverParameter2.getType()) == null || (classifierOrFail = IrTypesKt.getClassifierOrFail(type2)) == null) {
                        throw new IllegalStateException(("Expected extension receiver for " + RenderIrElementKt.render$default(irSimpleFunctionSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    linkedHashMap.put(classifierOrFail, obj);
                }
                return linkedHashMap;
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public /* bridge */ /* synthetic */ IrClassSymbol getStringBuilder() {
        return (IrClassSymbol) getStringBuilder();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowNullPointerException() {
        return this.throwNullPointerException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowTypeCastException() {
        return this.throwTypeCastException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowUninitializedPropertyAccessException() {
        return this.throwUninitializedPropertyAccessException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowKotlinNothingValueException() {
        return this.throwKotlinNothingValueException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getDefaultConstructorMarker() {
        return this.defaultConstructorMarker;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowISE() {
        return this.throwISE;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowIAE() {
        return this.throwIAE;
    }

    public Void getStringBuilder() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getCoroutineImpl() {
        return this.coroutineImpl;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineSuspendedGetter() {
        return this.coroutineSuspendedGetter;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public Map<IrType, IrSimpleFunctionSymbol> getArraysContentEquals() {
        List<IrSimpleFunctionSymbol> list = this._arraysContentEquals;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            IrValueParameter extensionReceiverParameter = ((IrSimpleFunctionSymbol) obj).getOwner().getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            linkedHashMap.put(IrTypesKt.makeNotNull(extensionReceiverParameter.getType()), obj);
        }
        return linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getGetContinuation() {
        return this.getContinuation;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getContinuationClass() {
        return this.continuationClass;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineContextGetter() {
        return this.coroutineContextGetter;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getSuspendCoroutineUninterceptedOrReturn() {
        return this.suspendCoroutineUninterceptedOrReturn;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineGetContext() {
        return this.coroutineGetContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getReturnIfSuspended() {
        return this.returnIfSuspended;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getFunctionAdapter() {
        throw new NotImplementedError("An operation is not implemented: Not implemented");
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public IrClassSymbol functionN(final int n) {
        return (IrClassSymbol) this.this$0.getIrFactory().getStageController().withInitialIr(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext$ir$1$symbols$1$functionN$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return super/*org.jetbrains.kotlin.backend.common.ir.Symbols*/.functionN(n);
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public IrClassSymbol suspendFunctionN(final int n) {
        return (IrClassSymbol) this.this$0.getIrFactory().getStageController().withInitialIr(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext$ir$1$symbols$1$suspendFunctionN$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return super/*org.jetbrains.kotlin.backend.common.ir.Symbols*/.suspendFunctionN(n);
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getGetProgressionLastElementByReturnType() {
        return (Map) this.getProgressionLastElementByReturnType.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getToUIntByExtensionReceiver() {
        return (Map) this.toUIntByExtensionReceiver.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getToULongByExtensionReceiver() {
        return (Map) this.toULongByExtensionReceiver.getValue();
    }
}
