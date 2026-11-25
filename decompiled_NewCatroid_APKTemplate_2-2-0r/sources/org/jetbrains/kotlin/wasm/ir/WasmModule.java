package org.jetbrains.kotlin.wasm.ir;

import com.reandroid.archive.PathTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.luaj.vm2.Lua;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0017\u0018\u00002\u00020\u0001B\u009f\u0002\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0003\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0003\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u0012\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003\u0012\u0012\b\u0002\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190\u0003\u0012\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0003\u0012\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\u0003\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u0012\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0003\u0012\b\b\u0002\u0010!\u001a\u00020\"¢\u0006\u0002\u0010#R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010%R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010%R\u001b\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190\u0003¢\u0006\b\n\u0000\u001a\u0004\b*\u0010%R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b+\u0010%R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010%R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003¢\u0006\b\n\u0000\u001a\u0004\b-\u0010%R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003¢\u0006\b\n\u0000\u001a\u0004\b.\u0010%R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003¢\u0006\b\n\u0000\u001a\u0004\b/\u0010%R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003¢\u0006\b\n\u0000\u001a\u0004\b0\u0010%R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0003¢\u0006\b\n\u0000\u001a\u0004\b1\u0010%R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\b\n\u0000\u001a\u0004\b2\u0010%R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u0003¢\u0006\b\n\u0000\u001a\u0004\b3\u0010%R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b4\u0010%R\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u001e¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003¢\u0006\b\n\u0000\u001a\u0004\b7\u0010%R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\u0003¢\u0006\b\n\u0000\u001a\u0004\b8\u0010%¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmModule;", "", "functionTypes", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "recGroupTypes", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "importsInOrder", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "importedFunctions", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Imported;", "importedMemories", "Lorg/jetbrains/kotlin/wasm/ir/WasmMemory;", "importedTables", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", "importedGlobals", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "importedTags", "Lorg/jetbrains/kotlin/wasm/ir/WasmTag;", "definedFunctions", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Defined;", "tables", "memories", "globals", PsiKeyword.EXPORTS, "Lorg/jetbrains/kotlin/wasm/ir/WasmExport;", PathTree.NAME_elements, "Lorg/jetbrains/kotlin/wasm/ir/WasmElement;", "tags", "startFunction", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "data", "Lorg/jetbrains/kotlin/wasm/ir/WasmData;", "dataCount", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;Ljava/util/List;Z)V", "getData", "()Ljava/util/List;", "getDataCount", "()Z", "getDefinedFunctions", "getElements", "getExports", "getFunctionTypes", "getGlobals", "getImportedFunctions", "getImportedGlobals", "getImportedMemories", "getImportedTables", "getImportedTags", "getImportsInOrder", "getMemories", "getRecGroupTypes", "getStartFunction", "()Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "getTables", "getTags", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmModule {
    private final List<WasmData> data;
    private final boolean dataCount;
    private final List<WasmFunction.Defined> definedFunctions;
    private final List<WasmElement> elements;
    private final List<WasmExport<?>> exports;
    private final List<WasmFunctionType> functionTypes;
    private final List<WasmGlobal> globals;
    private final List<WasmFunction.Imported> importedFunctions;
    private final List<WasmGlobal> importedGlobals;
    private final List<WasmMemory> importedMemories;
    private final List<WasmTable> importedTables;
    private final List<WasmTag> importedTags;
    private final List<WasmNamedModuleField> importsInOrder;
    private final List<WasmMemory> memories;
    private final List<WasmTypeDeclaration> recGroupTypes;
    private final WasmFunction startFunction;
    private final List<WasmTable> tables;
    private final List<WasmTag> tags;

    public WasmModule() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, Lua.MAXARG_Bx, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public WasmModule(List<WasmFunctionType> functionTypes, List<? extends WasmTypeDeclaration> recGroupTypes, List<? extends WasmNamedModuleField> importsInOrder, List<WasmFunction.Imported> importedFunctions, List<WasmMemory> importedMemories, List<WasmTable> importedTables, List<WasmGlobal> importedGlobals, List<WasmTag> importedTags, List<WasmFunction.Defined> definedFunctions, List<WasmTable> tables, List<WasmMemory> memories, List<WasmGlobal> globals, List<? extends WasmExport<?>> exports, List<WasmElement> elements, List<WasmTag> tags, WasmFunction wasmFunction, List<WasmData> data, boolean z) {
        Intrinsics.checkNotNullParameter(functionTypes, "functionTypes");
        Intrinsics.checkNotNullParameter(recGroupTypes, "recGroupTypes");
        Intrinsics.checkNotNullParameter(importsInOrder, "importsInOrder");
        Intrinsics.checkNotNullParameter(importedFunctions, "importedFunctions");
        Intrinsics.checkNotNullParameter(importedMemories, "importedMemories");
        Intrinsics.checkNotNullParameter(importedTables, "importedTables");
        Intrinsics.checkNotNullParameter(importedGlobals, "importedGlobals");
        Intrinsics.checkNotNullParameter(importedTags, "importedTags");
        Intrinsics.checkNotNullParameter(definedFunctions, "definedFunctions");
        Intrinsics.checkNotNullParameter(tables, "tables");
        Intrinsics.checkNotNullParameter(memories, "memories");
        Intrinsics.checkNotNullParameter(globals, "globals");
        Intrinsics.checkNotNullParameter(exports, "exports");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullParameter(tags, "tags");
        Intrinsics.checkNotNullParameter(data, "data");
        this.functionTypes = functionTypes;
        this.recGroupTypes = recGroupTypes;
        this.importsInOrder = importsInOrder;
        this.importedFunctions = importedFunctions;
        this.importedMemories = importedMemories;
        this.importedTables = importedTables;
        this.importedGlobals = importedGlobals;
        this.importedTags = importedTags;
        this.definedFunctions = definedFunctions;
        this.tables = tables;
        this.memories = memories;
        this.globals = globals;
        this.exports = exports;
        this.elements = elements;
        this.tags = tags;
        this.startFunction = wasmFunction;
        this.data = data;
        this.dataCount = z;
    }

    public /* synthetic */ WasmModule(List list, List list2, List list3, List list4, List list5, List list6, List list7, List list8, List list9, List list10, List list11, List list12, List list13, List list14, List list15, WasmFunction wasmFunction, List list16, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, (i & 2) != 0 ? CollectionsKt.emptyList() : list2, (i & 4) != 0 ? CollectionsKt.emptyList() : list3, (i & 8) != 0 ? CollectionsKt.emptyList() : list4, (i & 16) != 0 ? CollectionsKt.emptyList() : list5, (i & 32) != 0 ? CollectionsKt.emptyList() : list6, (i & 64) != 0 ? CollectionsKt.emptyList() : list7, (i & 128) != 0 ? CollectionsKt.emptyList() : list8, (i & 256) != 0 ? CollectionsKt.emptyList() : list9, (i & 512) != 0 ? CollectionsKt.emptyList() : list10, (i & 1024) != 0 ? CollectionsKt.emptyList() : list11, (i & 2048) != 0 ? CollectionsKt.emptyList() : list12, (i & 4096) != 0 ? CollectionsKt.emptyList() : list13, (i & 8192) != 0 ? CollectionsKt.emptyList() : list14, (i & 16384) != 0 ? CollectionsKt.emptyList() : list15, (i & 32768) != 0 ? null : wasmFunction, (i & 65536) != 0 ? CollectionsKt.emptyList() : list16, (i & 131072) != 0 ? true : z);
    }

    public final List<WasmFunctionType> getFunctionTypes() {
        return this.functionTypes;
    }

    public final List<WasmTypeDeclaration> getRecGroupTypes() {
        return this.recGroupTypes;
    }

    public final List<WasmNamedModuleField> getImportsInOrder() {
        return this.importsInOrder;
    }

    public final List<WasmFunction.Imported> getImportedFunctions() {
        return this.importedFunctions;
    }

    public final List<WasmMemory> getImportedMemories() {
        return this.importedMemories;
    }

    public final List<WasmTable> getImportedTables() {
        return this.importedTables;
    }

    public final List<WasmGlobal> getImportedGlobals() {
        return this.importedGlobals;
    }

    public final List<WasmTag> getImportedTags() {
        return this.importedTags;
    }

    public final List<WasmFunction.Defined> getDefinedFunctions() {
        return this.definedFunctions;
    }

    public final List<WasmTable> getTables() {
        return this.tables;
    }

    public final List<WasmMemory> getMemories() {
        return this.memories;
    }

    public final List<WasmGlobal> getGlobals() {
        return this.globals;
    }

    public final List<WasmExport<?>> getExports() {
        return this.exports;
    }

    public final List<WasmElement> getElements() {
        return this.elements;
    }

    public final List<WasmTag> getTags() {
        return this.tags;
    }

    public final WasmFunction getStartFunction() {
        return this.startFunction;
    }

    public final List<WasmData> getData() {
        return this.data;
    }

    public final boolean getDataCount() {
        return this.dataCount;
    }
}
