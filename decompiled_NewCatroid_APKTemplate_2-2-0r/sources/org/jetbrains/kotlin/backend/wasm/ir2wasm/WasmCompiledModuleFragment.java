package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.UInt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.wasm.ir.TypesKt;
import org.jetbrains.kotlin.wasm.ir.UtilsKt;
import org.jetbrains.kotlin.wasm.ir.WasmArrayDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmData;
import org.jetbrains.kotlin.wasm.ir.WasmDataMode;
import org.jetbrains.kotlin.wasm.ir.WasmExport;
import org.jetbrains.kotlin.wasm.ir.WasmFunction;
import org.jetbrains.kotlin.wasm.ir.WasmFunctionType;
import org.jetbrains.kotlin.wasm.ir.WasmGlobal;
import org.jetbrains.kotlin.wasm.ir.WasmHeapType;
import org.jetbrains.kotlin.wasm.ir.WasmI16;
import org.jetbrains.kotlin.wasm.ir.WasmI32;
import org.jetbrains.kotlin.wasm.ir.WasmI64;
import org.jetbrains.kotlin.wasm.ir.WasmI8;
import org.jetbrains.kotlin.wasm.ir.WasmIrExpressionBuilder;
import org.jetbrains.kotlin.wasm.ir.WasmLimits;
import org.jetbrains.kotlin.wasm.ir.WasmMemory;
import org.jetbrains.kotlin.wasm.ir.WasmModule;
import org.jetbrains.kotlin.wasm.ir.WasmRefNullType;
import org.jetbrains.kotlin.wasm.ir.WasmStructDeclaration;
import org.jetbrains.kotlin.wasm.ir.WasmSymbol;
import org.jetbrains.kotlin.wasm.ir.WasmSymbolReadOnly;
import org.jetbrains.kotlin.wasm.ir.WasmTag;
import org.jetbrains.kotlin.wasm.ir.WasmType;
import org.jetbrains.kotlin.wasm.ir.WasmTypeDeclaration;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: WasmCompiledModuleFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0004RSTUB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010P\u001a\u00020QR\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR/\u0010\u0010\u001a \u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u0004\u0012\u00020\u00150\u0012\u0012\u0004\u0012\u00020\u000e0\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a0\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\b¢\u0006\b\n\u0000\u001a\u0004\b \u0010\fR\u001d\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\"0\b¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\fR\u001d\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\fR\u001d\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020'0\b¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\fR\u001d\u0010)\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020'0\b¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\fR\u001d\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020'0\b¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\fR\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u0019¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0017\u00103\u001a\b\u0012\u0004\u0012\u0002040\u0019¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u001cR\u0017\u00106\u001a\b\u0012\u0004\u0012\u00020807¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0017\u0010;\u001a\b\u0012\u0004\u0012\u00020\u000e0<¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u001d\u0010?\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020\u000e0\u0011¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\u0017R\u001d\u0010A\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020\u000e0\u0011¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0017R\u0017\u0010C\u001a\b\u0012\u0004\u0012\u00020\u000e0<¢\u0006\b\n\u0000\u001a\u0004\bD\u0010>R\u000e\u0010E\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010F\u001a\b\u0012\u0004\u0012\u00020G0\u0013¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\u001cR\u001d\u0010I\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\u0011¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\u0017R\u001d\u0010K\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020L0\b¢\u0006\b\n\u0000\u001a\u0004\bM\u0010\fR\u001d\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\bO\u0010\f¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment;", "", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "generateTrapsInsteadOfExceptions", "", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Z)V", "classITableGcType", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$ReferencableAndDefinable;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "getClassITableGcType", "()Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$ReferencableAndDefinable;", "classITableInterfaceSlot", "", "getClassITableInterfaceSlot", "constantArrayDataSegmentId", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$ReferencableElements;", "Lkotlin/Pair;", "", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "getConstantArrayDataSegmentId", "()Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$ReferencableElements;", PsiKeyword.EXPORTS, "", "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", "getExports", "()Ljava/util/List;", "functionTypes", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "getFunctionTypes", "functions", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "getFunctions", "gcTypes", "getGcTypes", "globalClassITables", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "getGlobalClassITables", "globalFields", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getGlobalFields", "globalVTables", "getGlobalVTables", "initFunctions", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$FunWithPriority;", "getInitFunctions", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "jsFuns", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$JsCodeSnippet;", "getJsFuns", "jsModuleImports", "", "", "getJsModuleImports", "()Ljava/util/Set;", "scratchMemAddr", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "getScratchMemAddr", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "stringLiteralAddress", "getStringLiteralAddress", "stringLiteralPoolId", "getStringLiteralPoolId", "stringPoolSize", "getStringPoolSize", "tagFuncType", "tags", "Lorg/jetbrains/kotlin/wasm/ir/WasmTag;", "getTags", "typeIds", "getTypeIds", "typeInfo", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataElement;", "getTypeInfo", "vTableGcTypes", "getVTableGcTypes", "linkWasmCompiledFragments", "Lorg/jetbrains/kotlin/wasm/ir/WasmModule;", "FunWithPriority", "JsCodeSnippet", "ReferencableAndDefinable", "ReferencableElements", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmCompiledModuleFragment {
    private final ReferencableAndDefinable<IrClassSymbol, WasmTypeDeclaration> classITableGcType;
    private final ReferencableAndDefinable<IrClassSymbol, Integer> classITableInterfaceSlot;
    private final ReferencableElements<Pair<List<Long>, WasmType>, Integer> constantArrayDataSegmentId;
    private final List<WasmExport<?>> exports;
    private final ReferencableAndDefinable<IrFunctionSymbol, WasmFunctionType> functionTypes;
    private final ReferencableAndDefinable<IrFunctionSymbol, WasmFunction> functions;
    private final ReferencableAndDefinable<IrClassSymbol, WasmTypeDeclaration> gcTypes;
    private final ReferencableAndDefinable<IrClassSymbol, WasmGlobal> globalClassITables;
    private final ReferencableAndDefinable<IrFieldSymbol, WasmGlobal> globalFields;
    private final ReferencableAndDefinable<IrClassSymbol, WasmGlobal> globalVTables;
    private final List<FunWithPriority> initFunctions;
    private final IrBuiltIns irBuiltIns;
    private final List<JsCodeSnippet> jsFuns;
    private final Set<String> jsModuleImports;
    private final WasmSymbol<Integer> scratchMemAddr;
    private final ReferencableElements<String, Integer> stringLiteralAddress;
    private final ReferencableElements<String, Integer> stringLiteralPoolId;
    private final WasmSymbol<Integer> stringPoolSize;
    private final WasmFunctionType tagFuncType;
    private final List<WasmTag> tags;
    private final ReferencableElements<IrClassSymbol, Integer> typeIds;
    private final ReferencableAndDefinable<IrClassSymbol, ConstantDataElement> typeInfo;
    private final ReferencableAndDefinable<IrClassSymbol, WasmTypeDeclaration> vTableGcTypes;

    public WasmCompiledModuleFragment(IrBuiltIns irBuiltIns, boolean z) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.irBuiltIns = irBuiltIns;
        this.functions = new ReferencableAndDefinable<>();
        this.globalFields = new ReferencableAndDefinable<>();
        this.globalVTables = new ReferencableAndDefinable<>();
        this.globalClassITables = new ReferencableAndDefinable<>();
        this.functionTypes = new ReferencableAndDefinable<>();
        ReferencableAndDefinable<IrClassSymbol, WasmTypeDeclaration> referencableAndDefinable = new ReferencableAndDefinable<>();
        this.gcTypes = referencableAndDefinable;
        this.vTableGcTypes = new ReferencableAndDefinable<>();
        this.classITableGcType = new ReferencableAndDefinable<>();
        this.classITableInterfaceSlot = new ReferencableAndDefinable<>();
        this.typeIds = new ReferencableElements<>();
        this.stringLiteralAddress = new ReferencableElements<>();
        this.stringLiteralPoolId = new ReferencableElements<>();
        this.constantArrayDataSegmentId = new ReferencableElements<>();
        WasmFunctionType wasmFunctionType = new WasmFunctionType(CollectionsKt.listOf(new WasmRefNullType(new WasmHeapType.Type(referencableAndDefinable.reference(irBuiltIns.getThrowableClass())))), CollectionsKt.emptyList());
        this.tagFuncType = wasmFunctionType;
        this.tags = z ? CollectionsKt.emptyList() : CollectionsKt.listOf(new WasmTag(wasmFunctionType, null, 2, null));
        this.typeInfo = new ReferencableAndDefinable<>();
        this.exports = new ArrayList();
        this.jsFuns = new ArrayList();
        this.jsModuleImports = new LinkedHashSet();
        this.initFunctions = new ArrayList();
        this.scratchMemAddr = new WasmSymbol<>(null, 1, null);
        this.stringPoolSize = new WasmSymbol<>(null, 1, null);
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final ReferencableAndDefinable<IrFunctionSymbol, WasmFunction> getFunctions() {
        return this.functions;
    }

    public final ReferencableAndDefinable<IrFieldSymbol, WasmGlobal> getGlobalFields() {
        return this.globalFields;
    }

    public final ReferencableAndDefinable<IrClassSymbol, WasmGlobal> getGlobalVTables() {
        return this.globalVTables;
    }

    public final ReferencableAndDefinable<IrClassSymbol, WasmGlobal> getGlobalClassITables() {
        return this.globalClassITables;
    }

    public final ReferencableAndDefinable<IrFunctionSymbol, WasmFunctionType> getFunctionTypes() {
        return this.functionTypes;
    }

    public final ReferencableAndDefinable<IrClassSymbol, WasmTypeDeclaration> getGcTypes() {
        return this.gcTypes;
    }

    public final ReferencableAndDefinable<IrClassSymbol, WasmTypeDeclaration> getVTableGcTypes() {
        return this.vTableGcTypes;
    }

    public final ReferencableAndDefinable<IrClassSymbol, WasmTypeDeclaration> getClassITableGcType() {
        return this.classITableGcType;
    }

    public final ReferencableAndDefinable<IrClassSymbol, Integer> getClassITableInterfaceSlot() {
        return this.classITableInterfaceSlot;
    }

    public final ReferencableElements<IrClassSymbol, Integer> getTypeIds() {
        return this.typeIds;
    }

    public final ReferencableElements<String, Integer> getStringLiteralAddress() {
        return this.stringLiteralAddress;
    }

    public final ReferencableElements<String, Integer> getStringLiteralPoolId() {
        return this.stringLiteralPoolId;
    }

    public final ReferencableElements<Pair<List<Long>, WasmType>, Integer> getConstantArrayDataSegmentId() {
        return this.constantArrayDataSegmentId;
    }

    public final List<WasmTag> getTags() {
        return this.tags;
    }

    public final ReferencableAndDefinable<IrClassSymbol, ConstantDataElement> getTypeInfo() {
        return this.typeInfo;
    }

    public final List<WasmExport<?>> getExports() {
        return this.exports;
    }

    /* JADX INFO: compiled from: WasmCompiledModuleFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$JsCodeSnippet;", "", "importName", "", "jsCode", "(Ljava/lang/String;Ljava/lang/String;)V", "getImportName", "()Ljava/lang/String;", "getJsCode", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JsCodeSnippet {
        private final String importName;
        private final String jsCode;

        public JsCodeSnippet(String importName, String jsCode) {
            Intrinsics.checkNotNullParameter(importName, "importName");
            Intrinsics.checkNotNullParameter(jsCode, "jsCode");
            this.importName = importName;
            this.jsCode = jsCode;
        }

        public final String getImportName() {
            return this.importName;
        }

        public final String getJsCode() {
            return this.jsCode;
        }
    }

    public final List<JsCodeSnippet> getJsFuns() {
        return this.jsFuns;
    }

    public final Set<String> getJsModuleImports() {
        return this.jsModuleImports;
    }

    /* JADX INFO: compiled from: WasmCompiledModuleFragment.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$FunWithPriority;", "", "function", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "priority", "", "(Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;Ljava/lang/String;)V", "getFunction", "()Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "getPriority", "()Ljava/lang/String;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FunWithPriority {
        private final WasmFunction function;
        private final String priority;

        public FunWithPriority(WasmFunction function, String priority) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(priority, "priority");
            this.function = function;
            this.priority = priority;
        }

        public final WasmFunction getFunction() {
            return this.function;
        }

        public final String getPriority() {
            return this.priority;
        }
    }

    public final List<FunWithPriority> getInitFunctions() {
        return this.initFunctions;
    }

    public final WasmSymbol<Integer> getScratchMemAddr() {
        return this.scratchMemAddr;
    }

    public final WasmSymbol<Integer> getStringPoolSize() {
        return this.stringPoolSize;
    }

    /* JADX INFO: compiled from: WasmCompiledModuleFragment.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0019\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00072\u0006\u0010\u000b\u001a\u00028\u0000¢\u0006\u0002\u0010\fR#\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$ReferencableElements;", "Ir", "Wasm", "", "()V", "unbound", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "getUnbound", "()Ljava/util/Map;", "reference", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class ReferencableElements<Ir, Wasm> {
        private final Map<Ir, WasmSymbol<Wasm>> unbound = new LinkedHashMap();

        public final Map<Ir, WasmSymbol<Wasm>> getUnbound() {
            return this.unbound;
        }

        public final WasmSymbol<Wasm> reference(Ir ir) {
            IrSymbol irSymbol = ir instanceof IrSymbol ? (IrSymbol) ir : null;
            IrSymbolOwner owner = irSymbol != null ? irSymbol.getOwner() : null;
            IrDeclarationWithName irDeclarationWithName = owner instanceof IrDeclarationWithName ? (IrDeclarationWithName) owner : null;
            if (irDeclarationWithName != null && (IrUtilsKt.getPackageFragment((IrDeclaration) irDeclarationWithName) instanceof IrExternalPackageFragment)) {
                throw new IllegalStateException(("Referencing declaration without package fragment " + IrUtilsKt.getFqNameWhenAvailable(irDeclarationWithName)).toString());
            }
            Map<Ir, WasmSymbol<Wasm>> map = this.unbound;
            WasmSymbol<Wasm> wasmSymbol = map.get(ir);
            if (wasmSymbol == null) {
                wasmSymbol = new WasmSymbol<>(null, 1, null);
                map.put(ir, wasmSymbol);
            }
            return wasmSymbol;
        }
    }

    /* JADX INFO: compiled from: WasmCompiledModuleFragment.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u001b\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00028\u0001¢\u0006\u0002\u0010\u0017R-\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001`\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00000\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$ReferencableAndDefinable;", "Ir", "Wasm", "", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment$ReferencableElements;", "()V", "defined", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "getDefined", "()Ljava/util/LinkedHashMap;", PathTree.NAME_elements, "", "getElements", "()Ljava/util/List;", "wasmToIr", "", "getWasmToIr", "()Ljava/util/Map;", "define", "", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "wasm", "(Ljava/lang/Object;Ljava/lang/Object;)V", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ReferencableAndDefinable<Ir, Wasm> extends ReferencableElements<Ir, Wasm> {
        private final LinkedHashMap<Ir, Wasm> defined = new LinkedHashMap<>();
        private final List<Wasm> elements = new ArrayList();
        private final Map<Wasm, Ir> wasmToIr = new LinkedHashMap();

        public final void define(Ir ir, Wasm wasm) {
            Intrinsics.checkNotNullParameter(wasm, "wasm");
            if (this.defined.containsKey(ir)) {
                throw new IllegalStateException(("Trying to redefine element: IR: " + ir + " Wasm: " + wasm).toString());
            }
            this.elements.add(wasm);
            this.defined.put(ir, wasm);
            this.wasmToIr.put(wasm, ir);
        }

        public final LinkedHashMap<Ir, Wasm> getDefined() {
            return this.defined;
        }

        public final List<Wasm> getElements() {
            return this.elements;
        }

        public final Map<Wasm, Ir> getWasmToIr() {
            return this.wasmToIr;
        }
    }

    public final WasmModule linkWasmCompiledFragments() {
        WasmCompiledModuleFragmentKt.bind(this.functions.getUnbound(), this.functions.getDefined());
        WasmCompiledModuleFragmentKt.bind(this.globalFields.getUnbound(), this.globalFields.getDefined());
        WasmCompiledModuleFragmentKt.bind(this.globalVTables.getUnbound(), this.globalVTables.getDefined());
        WasmCompiledModuleFragmentKt.bind(this.gcTypes.getUnbound(), this.gcTypes.getDefined());
        WasmCompiledModuleFragmentKt.bind(this.vTableGcTypes.getUnbound(), this.vTableGcTypes.getDefined());
        WasmCompiledModuleFragmentKt.bind(this.classITableGcType.getUnbound(), this.classITableGcType.getDefined());
        WasmCompiledModuleFragmentKt.bind(this.classITableInterfaceSlot.getUnbound(), this.classITableInterfaceSlot.getDefined());
        WasmCompiledModuleFragmentKt.bind(this.globalClassITables.getUnbound(), this.globalClassITables.getDefined());
        List<WasmFunctionType> elements = this.functionTypes.getElements();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : elements) {
            linkedHashMap.put(obj, (WasmFunctionType) obj);
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        for (Map.Entry<IrFunctionSymbol, WasmSymbol<WasmFunctionType>> entry : this.functionTypes.getUnbound().entrySet()) {
            IrFunctionSymbol key = entry.getKey();
            WasmSymbol<WasmFunctionType> value = entry.getValue();
            if (!this.functionTypes.getDefined().containsKey(key)) {
                throw new IllegalStateException(("Can't link symbol " + WasmCompiledModuleFragmentKt.irSymbolDebugDump(key)).toString());
            }
            value.bind(MapsKt.getValue(linkedHashMap2, MapsKt.getValue(this.functionTypes.getDefined(), key)));
        }
        int sizeInBytes = 0;
        int i = 0;
        for (Map.Entry<IrClassSymbol, WasmSymbol<Integer>> entry2 : this.typeIds.getUnbound().entrySet()) {
            IrClassSymbol key2 = entry2.getKey();
            WasmSymbol<Integer> value2 = entry2.getValue();
            if (IrUtilsKt.isInterface(key2.getOwner())) {
                i--;
                value2.bind(Integer.valueOf(i));
            } else {
                value2.bind(Integer.valueOf(sizeInBytes));
                sizeInBytes += ((ConstantDataElement) MapsKt.getValue(this.typeInfo.getDefined(), key2)).getSizeInBytes();
            }
        }
        int iAlignUp = WasmCompiledModuleFragmentKt.alignUp(sizeInBytes, 4);
        this.scratchMemAddr.bind(Integer.valueOf(iAlignUp));
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int sizeInBytes2 = 0;
        for (Map.Entry<String, WasmSymbol<Integer>> entry3 : this.stringLiteralAddress.getUnbound().entrySet()) {
            String key3 = entry3.getKey();
            entry3.getValue().bind(Integer.valueOf(sizeInBytes2));
            this.stringLiteralPoolId.reference(key3).bind(Integer.valueOf(i2));
            char[] charArray = key3.toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
            ConstantDataCharArray constantDataCharArray = new ConstantDataCharArray("string_literal", charArray);
            CollectionsKt.addAll(arrayList, ArraysKt.toList(constantDataCharArray.toBytes()));
            sizeInBytes2 += constantDataCharArray.getSizeInBytes();
            i2++;
        }
        this.stringPoolSize.bind(Integer.valueOf(i2));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new WasmData(WasmDataMode.Passive.INSTANCE, CollectionsKt.toByteArray(arrayList)));
        Iterator<Map.Entry<Pair<List<Long>, WasmType>, WasmSymbol<Integer>>> it = this.constantArrayDataSegmentId.getUnbound().entrySet().iterator();
        while (true) {
            int i3 = 2;
            if (it.hasNext()) {
                Map.Entry<Pair<List<Long>, WasmType>, WasmSymbol<Integer>> next = it.next();
                Pair<List<Long>, WasmType> key4 = next.getKey();
                next.getValue().bind(Integer.valueOf(arrayList2.size()));
                WasmType second = key4.getSecond();
                if (Intrinsics.areEqual(second, WasmI8.INSTANCE)) {
                    i3 = 1;
                } else if (Intrinsics.areEqual(second, WasmI16.INSTANCE)) {
                    continue;
                } else if (Intrinsics.areEqual(second, WasmI32.INSTANCE)) {
                    i3 = 4;
                } else {
                    if (!Intrinsics.areEqual(second, WasmI64.INSTANCE)) {
                        throw new NotImplementedError("An operation is not implemented: " + ("type " + key4.getSecond() + " is not implemented"));
                    }
                    i3 = 8;
                }
                arrayList2.add(new WasmData(WasmDataMode.Passive.INSTANCE, new ConstantDataIntegerArray("constant_array", key4.getFirst(), i3).toBytes()));
            } else {
                for (Map.Entry<IrClassSymbol, WasmSymbol<Integer>> entry4 : this.typeIds.getUnbound().entrySet()) {
                    IrClassSymbol key5 = entry4.getKey();
                    WasmSymbol<Integer> value3 = entry4.getValue();
                    if (!IrUtilsKt.isInterface(key5.getOwner())) {
                        ArrayList arrayList3 = new ArrayList();
                        new WasmIrExpressionBuilder(arrayList3).buildConstI32(value3.getOwner().intValue(), SourceLocation.INSTANCE.NoLocation("Compile time data per class"));
                        arrayList2.add(new WasmData(new WasmDataMode.Active(0, arrayList3), ((ConstantDataElement) MapsKt.getValue(this.typeInfo.getDefined(), key5)).toBytes()));
                    }
                }
                WasmFunctionType wasmFunctionType = new WasmFunctionType(CollectionsKt.emptyList(), CollectionsKt.emptyList());
                WasmFunction.Defined defined = new WasmFunction.Defined("_initialize", new WasmSymbol(wasmFunctionType), null, null, 12, null);
                WasmIrExpressionBuilder wasmIrExpressionBuilder = new WasmIrExpressionBuilder(defined.getInstructions());
                Iterator it2 = CollectionsKt.sortedWith(this.initFunctions, new Comparator() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.WasmCompiledModuleFragment$linkWasmCompiledFragments$lambda$7$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(((WasmCompiledModuleFragment.FunWithPriority) t).getPriority(), ((WasmCompiledModuleFragment.FunWithPriority) t2).getPriority());
                    }
                }).iterator();
                while (it2.hasNext()) {
                    wasmIrExpressionBuilder.buildCall(new WasmSymbol<>(((FunWithPriority) it2.next()).getFunction()), SourceLocation.INSTANCE.NoLocation("Generated service code"));
                }
                this.exports.add(new WasmExport.Function("_initialize", defined));
                WasmMemory wasmMemory = new WasmMemory(new WasmLimits(UInt.m2993constructorimpl((iAlignUp / 65536) + 1), null, null), null, 2, null);
                this.exports.add(new WasmExport.Memory("memory", wasmMemory));
                List<WasmFunction> elements2 = this.functions.getElements();
                ArrayList arrayList4 = new ArrayList();
                for (Object obj2 : elements2) {
                    if (obj2 instanceof WasmFunction.Imported) {
                        arrayList4.add(obj2);
                    }
                }
                ArrayList arrayList5 = arrayList4;
                ArrayList arrayList6 = new ArrayList();
                arrayList6.addAll(this.vTableGcTypes.getElements());
                arrayList6.addAll(this.gcTypes.getElements());
                arrayList6.addAll(CollectionsKt.distinct(this.classITableGcType.getElements()));
                if (arrayList6.size() > 1) {
                    CollectionsKt.sortWith(arrayList6, new Comparator() { // from class: org.jetbrains.kotlin.backend.wasm.ir2wasm.WasmCompiledModuleFragment$linkWasmCompiledFragments$$inlined$sortBy$1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            return ComparisonsKt.compareValues(Integer.valueOf(WasmCompiledModuleFragment.linkWasmCompiledFragments$wasmTypeDeclarationOrderKey((WasmTypeDeclaration) t)), Integer.valueOf(WasmCompiledModuleFragment.linkWasmCompiledFragments$wasmTypeDeclarationOrderKey((WasmTypeDeclaration) t2)));
                        }
                    });
                }
                ArrayList arrayList7 = new ArrayList();
                arrayList7.addAll(this.globalFields.getElements());
                arrayList7.addAll(this.globalVTables.getElements());
                arrayList7.addAll(CollectionsKt.distinct(this.globalClassITables.getElements()));
                Collection collectionValues = linkedHashMap2.values();
                Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
                List listPlus = CollectionsKt.plus((Collection<? extends WasmFunctionType>) CollectionsKt.plus((Collection<? extends WasmFunctionType>) CollectionsKt.toList(collectionValues), this.tagFuncType), wasmFunctionType);
                ArrayList arrayList8 = new ArrayList();
                ArrayList arrayList9 = new ArrayList();
                for (Object obj3 : listPlus) {
                    WasmFunctionType wasmFunctionType2 = (WasmFunctionType) obj3;
                    Intrinsics.checkNotNull(wasmFunctionType2);
                    if (TypesKt.referencesTypeDeclarations(wasmFunctionType2)) {
                        arrayList8.add(obj3);
                    } else {
                        arrayList9.add(obj3);
                    }
                }
                Pair pair = new Pair(arrayList8, arrayList9);
                List list = (List) pair.component1();
                List list2 = (List) pair.component2();
                arrayList6.addAll(list);
                List<WasmFunction> elements3 = this.functions.getElements();
                ArrayList arrayList10 = new ArrayList();
                for (Object obj4 : elements3) {
                    if (obj4 instanceof WasmFunction.Defined) {
                        arrayList10.add(obj4);
                    }
                }
                WasmModule wasmModule = new WasmModule(list2, arrayList6, arrayList5, arrayList5, null, null, null, null, CollectionsKt.plus((Collection<? extends WasmFunction.Defined>) arrayList10, defined), CollectionsKt.emptyList(), CollectionsKt.listOf(wasmMemory), arrayList7, this.exports, CollectionsKt.emptyList(), this.tags, null, arrayList2, true, 240, null);
                UtilsKt.calculateIds(wasmModule);
                return wasmModule;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int linkWasmCompiledFragments$wasmTypeDeclarationOrderKey(WasmTypeDeclaration wasmTypeDeclaration) {
        if ((wasmTypeDeclaration instanceof WasmArrayDeclaration) || (wasmTypeDeclaration instanceof WasmFunctionType)) {
            return 0;
        }
        if (!(wasmTypeDeclaration instanceof WasmStructDeclaration)) {
            throw new NoWhenBranchMatchedException();
        }
        WasmSymbolReadOnly<WasmTypeDeclaration> superType = ((WasmStructDeclaration) wasmTypeDeclaration).getSuperType();
        if (superType != null) {
            return linkWasmCompiledFragments$wasmTypeDeclarationOrderKey(superType.getOwner()) + 1;
        }
        return 0;
    }
}
