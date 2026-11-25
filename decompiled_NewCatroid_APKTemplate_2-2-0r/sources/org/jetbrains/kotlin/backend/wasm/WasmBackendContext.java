package org.jetbrains.kotlin.backend.wasm;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.Ir;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.JsModuleAndQualifierReference;
import org.jetbrains.kotlin.backend.wasm.lower.WasmSharedVariablesManager;
import org.jetbrains.kotlin.backend.wasm.utils.WasmInlineClassesUtils;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.SourceRangeInfo;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols;
import org.jetbrains.kotlin.ir.backend.js.JsMapping;
import org.jetbrains.kotlin.ir.backend.js.PropertyLazyInitialization;
import org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.lower.JsInnerClassesSupport;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.DescriptorlessExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: WasmBackendContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ü\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0086\u0001\u001a\u00020\u00162\u0007\u0010\u0087\u0001\u001a\u00020\u0015J\u0013\u0010\u0088\u0001\u001a\u00020\u00162\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001H\u0016J\u0010\u0010\u008b\u0001\u001a\u00020)2\u0007\u0010\u008c\u0001\u001a\u00020(J\u001a\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u000e\u0010\u008f\u0001\u001a\t\u0012\u0004\u0012\u00020\u00150\u0090\u0001H\u0016J4\u0010\u0091\u0001\u001a\u00030\u008e\u00012\n\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u00012\n\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u00012\u0007\u0010\u008f\u0001\u001a\u00020\u00152\u0007\u0010\u0094\u0001\u001a\u00020\u000bH\u0016J\u001b\u0010\u0095\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u0096\u0001\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\tH\u0002R\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020 X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u001a\u0010'\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020)0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010*\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001a\u0010-\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u0014\u00102\u001a\u000203X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0011\u00106\u001a\u000207¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u00020(X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u001b\u0010=\u001a\u00020>8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bA\u0010B\u001a\u0004\b?\u0010@R\u000e\u0010C\u001a\u00020DX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00000FX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0014\u0010K\u001a\u00020LX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\bO\u0010PR\u001d\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\b\n\u0000\u001a\u0004\bR\u0010\u0018R\u0017\u0010S\u001a\b\u0012\u0004\u0012\u00020U0T¢\u0006\b\n\u0000\u001a\u0004\bV\u0010WR\u001d\u0010X\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\b\n\u0000\u001a\u0004\bY\u0010\u0018R\u001d\u0010Z\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\b\n\u0000\u001a\u0004\b[\u0010\u0018R\u0011\u0010\\\u001a\u00020(¢\u0006\b\n\u0000\u001a\u0004\b]\u0010<R\u0011\u0010^\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b_\u0010,R\u0014\u0010`\u001a\u00020aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bb\u0010cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bd\u0010eR\u0014\u0010\n\u001a\u00020fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bg\u0010hR\u0014\u0010i\u001a\u00020j8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bk\u0010lR\u0014\u0010m\u001a\u00020\u000bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\bn\u0010/R\u0014\u0010o\u001a\u00020pX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bq\u0010rR\u0016\u0010s\u001a\u0004\u0018\u00010 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bt\u0010\"R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\bu\u0010vR\u001a\u0010w\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010x\u001a\b\u0012\u0004\u0012\u00020\u00160y8F¢\u0006\u0006\u001a\u0004\bz\u0010{R\u0016\u0010|\u001a\u0004\u0018\u00010 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b}\u0010\"R\u0016\u0010~\u001a\u00020\u007fX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001R\u0015\u0010\u0082\u0001\u001a\u00030\u0083\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0084\u0001\u0010\u0085\u0001¨\u0006\u0097\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "propertyLazyInitialization", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;ZLorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "closureCallExports", "", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getClosureCallExports", "()Ljava/util/Map;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "coroutineSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "getCoroutineSymbols", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "createEnumEntries", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getCreateEnumEntries", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "enumEntries", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getEnumEntries", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "excludedDeclarations", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "fieldInitFunction", "getFieldInitFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "inVerbosePhase", "getInVerbosePhase", "()Z", "setInVerbosePhase", "(Z)V", "inlineClassesUtils", "Lorg/jetbrains/kotlin/backend/wasm/utils/WasmInlineClassesUtils;", "getInlineClassesUtils", "()Lorg/jetbrains/kotlin/backend/wasm/utils/WasmInlineClassesUtils;", "innerClassesSupport", "Lorg/jetbrains/kotlin/ir/backend/js/lower/JsInnerClassesSupport;", "getInnerClassesSupport", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/JsInnerClassesSupport;", "internalPackageFqn", "getInternalPackageFqn", "()Lorg/jetbrains/kotlin/name/FqName;", "internalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrFileImpl;", "getInternalPackageFragment", "()Lorg/jetbrains/kotlin/ir/declarations/impl/IrFileImpl;", "internalPackageFragment$delegate", "Lkotlin/Lazy;", "internalPackageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/EmptyPackageFragmentDescriptor;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "getIr", "()Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "jsClosureCallers", "getJsClosureCallers", "jsModuleAndQualifierReferences", "", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/JsModuleAndQualifierReference;", "getJsModuleAndQualifierReferences", "()Ljava/util/Set;", "jsToKotlinClosures", "getJsToKotlinClosures", "kotlinClosureToJsConverters", "getKotlinClosureToJsConverters", "kotlinWasmInternalPackageFqn", "getKotlinWasmInternalPackageFqn", "mainCallsWrapperFunction", "getMainCallsWrapperFunction", "mapping", "Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;", "getMapping", "()Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "Lorg/jetbrains/kotlin/ir/backend/js/PropertyLazyInitialization;", "getPropertyLazyInitialization", "()Lorg/jetbrains/kotlin/ir/backend/js/PropertyLazyInitialization;", "reflectionSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "getReflectionSymbols", "()Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "scriptMode", "getScriptMode", "sharedVariablesManager", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmSharedVariablesManager;", "getSharedVariablesManager", "()Lorg/jetbrains/kotlin/backend/wasm/lower/WasmSharedVariablesManager;", "suiteFun", "getSuiteFun", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "testContainerFuns", "testEntryPoints", "", "getTestEntryPoints", "()Ljava/util/Collection;", "testFun", "getTestFun", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "wasmSymbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "getWasmSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "createInitFunction", "identifier", "createTestContainerFun", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getExcludedPackageFragment", "fqName", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "report", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "isError", "syntheticFile", "name", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmBackendContext implements JsCommonBackendContext {
    private final KotlinBuiltIns builtIns;
    private final Map<String, IrSimpleFunction> closureCallExports;
    private final CompilerConfiguration configuration;
    private final JsCommonCoroutineSymbols coroutineSymbols;
    private final IrSimpleFunctionSymbol createEnumEntries;
    private final IrClassSymbol enumEntries;
    private final Map<FqName, IrPackageFragment> excludedDeclarations;
    private final IrSimpleFunction fieldInitFunction;
    private boolean inVerbosePhase;
    private final WasmInlineClassesUtils inlineClassesUtils;
    private final JsInnerClassesSupport innerClassesSupport;
    private final FqName internalPackageFqn;

    /* JADX INFO: renamed from: internalPackageFragment$delegate, reason: from kotlin metadata */
    private final Lazy internalPackageFragment;
    private final EmptyPackageFragmentDescriptor internalPackageFragmentDescriptor;
    private final Ir<WasmBackendContext> ir;
    private final IrBuiltIns irBuiltIns;
    private final IrFactory irFactory;
    private final IrModuleFragment irModuleFragment;
    private final Map<String, IrSimpleFunction> jsClosureCallers;
    private final Set<JsModuleAndQualifierReference> jsModuleAndQualifierReferences;
    private final Map<String, IrSimpleFunction> jsToKotlinClosures;
    private final Map<String, IrSimpleFunction> kotlinClosureToJsConverters;
    private final FqName kotlinWasmInternalPackageFqn;
    private final IrSimpleFunction mainCallsWrapperFunction;
    private final JsMapping mapping;
    private final ModuleDescriptor module;
    private final PropertyLazyInitialization propertyLazyInitialization;
    private final boolean scriptMode;
    private final WasmSharedVariablesManager sharedVariablesManager;
    private final SymbolTable symbolTable;
    private final Map<IrModuleFragment, IrSimpleFunction> testContainerFuns;
    private final IrTypeSystemContext typeSystem;
    private final WasmSymbols wasmSymbols;

    public WasmBackendContext(ModuleDescriptor module, IrBuiltIns irBuiltIns, SymbolTable symbolTable, IrModuleFragment irModuleFragment, boolean z, CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.module = module;
        this.irBuiltIns = irBuiltIns;
        this.symbolTable = symbolTable;
        this.irModuleFragment = irModuleFragment;
        this.configuration = configuration;
        this.builtIns = module.getBuiltIns();
        this.typeSystem = new IrTypeSystemContextImpl(getIrBuiltIns());
        this.irFactory = symbolTable.getIrFactory();
        this.excludedDeclarations = new LinkedHashMap();
        this.mapping = new JsMapping();
        this.closureCallExports = new LinkedHashMap();
        this.kotlinClosureToJsConverters = new LinkedHashMap();
        this.jsClosureCallers = new LinkedHashMap();
        this.jsToKotlinClosures = new LinkedHashMap();
        this.jsModuleAndQualifierReferences = new LinkedHashSet();
        WasmBackendContext wasmBackendContext = this;
        this.coroutineSymbols = new JsCommonCoroutineSymbols(symbolTable, module, wasmBackendContext);
        this.innerClassesSupport = new JsInnerClassesSupport(getMapping(), getIrFactory());
        this.internalPackageFqn = new FqName("kotlin.wasm");
        FqName fqNameChild = getInternalPackageFqn().child(Name.identifier("internal"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        this.kotlinWasmInternalPackageFqn = fqNameChild;
        ModuleDescriptorImpl builtInsModule = getBuiltIns().getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
        this.internalPackageFragmentDescriptor = new EmptyPackageFragmentDescriptor(builtInsModule, fqNameChild);
        this.internalPackageFragment = LazyKt.lazy(new Function0<IrFileImpl>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmBackendContext$internalPackageFragment$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFileImpl invoke() {
                IrFileImpl irFileImpl = new IrFileImpl(new IrFileEntry() { // from class: org.jetbrains.kotlin.backend.wasm.WasmBackendContext$internalPackageFragment$2.1
                    private final String name = "<implicitDeclarations>";
                    private final int maxOffset = -1;

                    @Override // org.jetbrains.kotlin.ir.IrFileEntry
                    public int getColumnNumber(int offset) {
                        return -1;
                    }

                    @Override // org.jetbrains.kotlin.ir.IrFileEntry
                    public int getLineNumber(int offset) {
                        return -1;
                    }

                    @Override // org.jetbrains.kotlin.ir.IrFileEntry
                    public boolean getSupportsDebugInfo() {
                        return IrFileEntry.DefaultImpls.getSupportsDebugInfo(this);
                    }

                    @Override // org.jetbrains.kotlin.ir.IrFileEntry
                    public String getName() {
                        return this.name;
                    }

                    @Override // org.jetbrains.kotlin.ir.IrFileEntry
                    public int getMaxOffset() {
                        return this.maxOffset;
                    }

                    @Override // org.jetbrains.kotlin.ir.IrFileEntry
                    public SourceRangeInfo getSourceRangeInfo(int beginOffset, int endOffset) {
                        return new SourceRangeInfo("", -1, -1, -1, -1, -1, -1);
                    }
                }, this.this$0.internalPackageFragmentDescriptor, this.this$0.getIrModuleFragment());
                this.this$0.getIrModuleFragment().getFiles().add(irFileImpl);
                return irFileImpl;
            }
        });
        this.fieldInitFunction = createInitFunction("fieldInit");
        this.mainCallsWrapperFunction = createInitFunction("mainCallsWrapper");
        this.sharedVariablesManager = new WasmSharedVariablesManager(wasmBackendContext, getIrBuiltIns(), getInternalPackageFragment());
        WasmSymbols wasmSymbols = new WasmSymbols(this, symbolTable);
        this.wasmSymbols = wasmSymbols;
        this.enumEntries = wasmSymbols.getEnumEntries();
        this.createEnumEntries = wasmSymbols.getCreateEnumEntries();
        this.propertyLazyInitialization = new PropertyLazyInitialization(z, wasmSymbols.getEagerInitialization());
        this.ir = new Ir<WasmBackendContext>(this) { // from class: org.jetbrains.kotlin.backend.wasm.WasmBackendContext$ir$1
            private final Symbols symbols;

            @Override // org.jetbrains.kotlin.backend.common.ir.Ir
            public boolean shouldGenerateHandlerParameterForDefaultBodyFun() {
                return true;
            }

            {
                super(this);
                this.symbols = this.getWasmSymbols();
            }

            @Override // org.jetbrains.kotlin.backend.common.ir.Ir
            public Symbols getSymbols() {
                return this.symbols;
            }
        };
        this.inlineClassesUtils = new WasmInlineClassesUtils(wasmSymbols);
        this.testContainerFuns = new LinkedHashMap();
    }

    public final ModuleDescriptor getModule() {
        return this.module;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final IrModuleFragment getIrModuleFragment() {
        return this.irModuleFragment;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrTypeSystemContext getTypeSystem() {
        return this.typeSystem;
    }

    @Override // org.jetbrains.kotlin.backend.common.LoggingContext
    public boolean getInVerbosePhase() {
        return this.inVerbosePhase;
    }

    @Override // org.jetbrains.kotlin.backend.common.LoggingContext
    public void setInVerbosePhase(boolean z) {
        this.inVerbosePhase = z;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public boolean getScriptMode() {
        return this.scriptMode;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrFactory getIrFactory() {
        return this.irFactory;
    }

    public final IrPackageFragment getExcludedPackageFragment(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Map<FqName, IrPackageFragment> map = this.excludedDeclarations;
        IrExternalPackageFragmentImpl irExternalPackageFragmentImpl = map.get(fqName);
        if (irExternalPackageFragmentImpl == null) {
            irExternalPackageFragmentImpl = new IrExternalPackageFragmentImpl(new DescriptorlessExternalPackageFragmentSymbol(), fqName);
            map.put(fqName, irExternalPackageFragmentImpl);
        }
        return irExternalPackageFragmentImpl;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public JsMapping getMapping() {
        return this.mapping;
    }

    public final Map<String, IrSimpleFunction> getClosureCallExports() {
        return this.closureCallExports;
    }

    public final Map<String, IrSimpleFunction> getKotlinClosureToJsConverters() {
        return this.kotlinClosureToJsConverters;
    }

    public final Map<String, IrSimpleFunction> getJsClosureCallers() {
        return this.jsClosureCallers;
    }

    public final Map<String, IrSimpleFunction> getJsToKotlinClosures() {
        return this.jsToKotlinClosures;
    }

    public final Set<JsModuleAndQualifierReference> getJsModuleAndQualifierReferences() {
        return this.jsModuleAndQualifierReferences;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public JsCommonCoroutineSymbols getCoroutineSymbols() {
        return this.coroutineSymbols;
    }

    public final JsInnerClassesSupport getInnerClassesSupport() {
        return this.innerClassesSupport;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public FqName getInternalPackageFqn() {
        return this.internalPackageFqn;
    }

    public final FqName getKotlinWasmInternalPackageFqn() {
        return this.kotlinWasmInternalPackageFqn;
    }

    public final IrFileImpl getInternalPackageFragment() {
        return (IrFileImpl) this.internalPackageFragment.getValue();
    }

    public final IrSimpleFunction createInitFunction(String identifier) {
        Intrinsics.checkNotNullParameter(identifier, "identifier");
        IrFactory irFactory = getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(identifier);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(getIrBuiltIns().getUnitType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setBody(getIrFactory().createBlockBody(-1, -1));
        IrUtilsKt.addChild(getInternalPackageFragment(), irSimpleFunctionBuildFunction);
        return irSimpleFunctionBuildFunction;
    }

    public final IrSimpleFunction getFieldInitFunction() {
        return this.fieldInitFunction;
    }

    public final IrSimpleFunction getMainCallsWrapperFunction() {
        return this.mainCallsWrapperFunction;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public WasmSharedVariablesManager getSharedVariablesManager() {
        return this.sharedVariablesManager;
    }

    public final WasmSymbols getWasmSymbols() {
        return this.wasmSymbols;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public ReflectionSymbols getReflectionSymbols() {
        return this.wasmSymbols.getReflectionSymbols();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrClassSymbol getEnumEntries() {
        return this.enumEntries;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrSimpleFunctionSymbol getCreateEnumEntries() {
        return this.createEnumEntries;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public PropertyLazyInitialization getPropertyLazyInitialization() {
        return this.propertyLazyInitialization;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext, org.jetbrains.kotlin.backend.common.BackendContext
    public Ir<WasmBackendContext> getIr() {
        return this.ir;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext, org.jetbrains.kotlin.backend.common.CommonBackendContext
    public WasmInlineClassesUtils getInlineClassesUtils() {
        return this.inlineClassesUtils;
    }

    @Override // org.jetbrains.kotlin.backend.common.LoggingContext
    public void log(Function0<String> message) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (getInVerbosePhase()) {
            System.out.print((Object) message.invoke());
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.ErrorReportingContext
    public void report(IrElement element, IrFile irFile, String message, boolean isError) {
        Intrinsics.checkNotNullParameter(message, "message");
        System.out.print((Object) message);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrSimpleFunctionSymbol getSuiteFun() {
        return this.wasmSymbols.getSuiteFun();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrSimpleFunctionSymbol getTestFun() {
        return this.wasmSymbols.getTestFun();
    }

    private final IrFile syntheticFile(String name2, IrModuleFragment module) {
        IrFileImpl irFileImpl = new IrFileImpl(new IrFileEntry(name2) { // from class: org.jetbrains.kotlin.backend.wasm.WasmBackendContext.syntheticFile.1
            private final int maxOffset = -1;
            private final String name;

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public int getColumnNumber(int offset) {
                return -1;
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public int getLineNumber(int offset) {
                return -1;
            }

            {
                this.name = "<" + name2 + '>';
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public boolean getSupportsDebugInfo() {
                return IrFileEntry.DefaultImpls.getSupportsDebugInfo(this);
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public String getName() {
                return this.name;
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public int getMaxOffset() {
                return this.maxOffset;
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public SourceRangeInfo getSourceRangeInfo(int beginOffset, int endOffset) {
                return new SourceRangeInfo("", -1, -1, -1, -1, -1, -1);
            }
        }, this.internalPackageFragmentDescriptor, module);
        module.getFiles().add(irFileImpl);
        return irFileImpl;
    }

    public final Collection<IrSimpleFunction> getTestEntryPoints() {
        return this.testContainerFuns.values();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrSimpleFunction createTestContainerFun(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrModuleFragment module = irFile.getModule();
        Map<IrModuleFragment, IrSimpleFunction> map = this.testContainerFuns;
        IrSimpleFunction irSimpleFunction = map.get(module);
        if (irSimpleFunction == null) {
            IrFile irFileSyntheticFile = syntheticFile("tests", module);
            IrFactory irFactory = getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            Name nameIdentifier = Name.identifier("testContainer");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFunctionBuilder.setName(nameIdentifier);
            irFunctionBuilder.setReturnType(getIrBuiltIns().getUnitType());
            irFunctionBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            IrFile irFile2 = irFileSyntheticFile;
            irFile2.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irFile2);
            irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(getIrFactory(), -1, -1, (List<? extends IrStatement>) CollectionsKt.emptyList()));
            map.put(module, irSimpleFunctionBuildFunction);
            irSimpleFunction = irSimpleFunctionBuildFunction;
        }
        return irSimpleFunction;
    }
}
