package org.jetbrains.kotlin.backend.wasm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.linkage.issues.ChecksKt;
import org.jetbrains.kotlin.backend.common.phaser.CompilerPhaseKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.JsHelpersKt;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.JsModuleAndQualifierReference;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.WasmCompiledModuleFragment;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.WasmModuleFragmentGenerator;
import org.jetbrains.kotlin.backend.wasm.lower.MarkAdditionalExportedDeclarationsKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.IrModuleInfo;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.ir.backend.js.MainModule;
import org.jetbrains.kotlin.ir.backend.js.ModulesStructure;
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfo;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.ExternalDependenciesGenerator;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.sourceMap.SourceMap3Builder;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.wasm.ir.WasmModule;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToBinary;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToText;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocationMapping;

/* JADX INFO: compiled from: compiler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aH\u0010\u0000\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f\u001aL\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u000f2\b\b\u0002\u0010\u0018\u001a\u00020\u000f2\b\b\u0002\u0010\u0019\u001a\u00020\u000f\u001a\u000e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015\u001a\"\u0010\u001c\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u001d\u001a\u00020\u001e2\u000e\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020!\u0018\u00010 H\u0002\u001a\u001e\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0015\u001a \u0010(\u001a\u00020\u0015*\u00020)2\u0006\u0010*\u001a\u00020\u00152\f\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\f\u001a\u0012\u0010-\u001a\u00020\u0015*\u00020)2\u0006\u0010*\u001a\u00020\u0015¨\u0006."}, d2 = {"compileToLoweredIr", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "depsDescriptors", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "exportedDeclarations", "", "Lorg/jetbrains/kotlin/name/FqName;", "propertyLazyInitialization", "", "compileWasm", "Lorg/jetbrains/kotlin/backend/wasm/WasmCompilerResult;", "allModules", "backendContext", "baseFileName", "", "emitNameSection", "allowIncompleteImplementations", "generateWat", "generateSourceMaps", "generateEsmExportsWrapper", "asyncWrapperFileName", "generateSourceMap", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "sourceLocationMappings", "", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocationMapping;", "writeCompilationResult", "", "result", "dir", "Ljava/io/File;", "fileNameBase", "generateAsyncJsWrapper", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmCompiledModuleFragment;", "wasmFilePath", "jsModuleAndQualifierReferences", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/JsModuleAndQualifierReference;", "generateAsyncWasiWrapper", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Reader generateSourceMap$lambda$6$lambda$5() {
        return null;
    }

    public static /* synthetic */ Pair compileToLoweredIr$default(ModulesStructure modulesStructure, PhaseConfig phaseConfig, IrFactory irFactory, Set set, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return compileToLoweredIr(modulesStructure, phaseConfig, irFactory, set, z);
    }

    public static final Pair<List<IrModuleFragment>, WasmBackendContext> compileToLoweredIr(ModulesStructure depsDescriptors, PhaseConfig phaseConfig, IrFactory irFactory, Set<FqName> exportedDeclarations, boolean z) {
        Intrinsics.checkNotNullParameter(depsDescriptors, "depsDescriptors");
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(exportedDeclarations, "exportedDeclarations");
        MainModule mainModule = depsDescriptors.getMainModule();
        CompilerConfiguration compilerConfiguration = depsDescriptors.getCompilerConfiguration();
        IrModuleInfo irModuleInfoLoadIr$default = KlibKt.loadIr$default(depsDescriptors, irFactory, false, null, true, 8, null);
        IrModuleFragment module = irModuleInfoLoadIr$default.getModule();
        List<IrModuleFragment> listComponent2 = irModuleInfoLoadIr$default.component2();
        IrBuiltIns bultins = irModuleInfoLoadIr$default.getBultins();
        SymbolTable symbolTable = irModuleInfoLoadIr$default.getSymbolTable();
        JsIrLinker deserializer = irModuleInfoLoadIr$default.getDeserializer();
        if (mainModule instanceof MainModule.SourceFiles) {
            listComponent2 = CollectionsKt.plus((Collection) listComponent2, (Iterable) CollectionsKt.listOf(module));
        } else if (!(mainModule instanceof MainModule.Klib)) {
            throw new NoWhenBranchMatchedException();
        }
        List<IrModuleFragment> list = listComponent2;
        WasmBackendContext wasmBackendContext = new WasmBackendContext(module.getDescriptor(), bultins, symbolTable, module, z, compilerConfiguration);
        List<IrModuleFragment> list2 = list;
        for (IrModuleFragment irModuleFragment : list2) {
            new ExternalDependenciesGenerator(symbolTable, CollectionsKt.listOf(deserializer)).generateUnboundSymbolsAsDependencies();
        }
        new ExternalDependenciesGenerator(symbolTable, CollectionsKt.listOf(deserializer)).generateUnboundSymbolsAsDependencies();
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            PatchDeclarationParentsKt.patchDeclarationParents$default((IrModuleFragment) it.next(), null, 1, null);
        }
        deserializer.postProcess(true);
        ChecksKt.checkNoUnboundSymbols(deserializer, symbolTable, "at the end of IR linkage process");
        deserializer.clear();
        Iterator<IrModuleFragment> it2 = list.iterator();
        while (it2.hasNext()) {
            Iterator<IrFile> it3 = it2.next().getFiles().iterator();
            while (it3.hasNext()) {
                MarkAdditionalExportedDeclarationsKt.markExportedDeclarations(wasmBackendContext, it3.next(), exportedDeclarations);
            }
        }
        CompilerPhaseKt.invokeToplevel(WasmLoweringPhasesKt.getWasmPhases(), phaseConfig, wasmBackendContext, list);
        return new Pair<>(list, wasmBackendContext);
    }

    public static final WasmCompilerResult compileWasm(List<? extends IrModuleFragment> allModules, WasmBackendContext backendContext, String baseFileName, boolean z, boolean z2, boolean z3, boolean z4) {
        String string;
        String strGenerateAsyncWasiWrapper;
        Intrinsics.checkNotNullParameter(allModules, "allModules");
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(baseFileName, "baseFileName");
        WasmCompiledModuleFragment wasmCompiledModuleFragment = new WasmCompiledModuleFragment(backendContext.getIrBuiltIns(), backendContext.getConfiguration().getBoolean(JSConfigurationKeys.WASM_USE_TRAPS_INSTEAD_OF_EXCEPTIONS));
        WasmModuleFragmentGenerator wasmModuleFragmentGenerator = new WasmModuleFragmentGenerator(backendContext, wasmCompiledModuleFragment, z2);
        List<? extends IrModuleFragment> list = allModules;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            wasmModuleFragmentGenerator.collectInterfaceTables((IrModuleFragment) it.next());
        }
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            wasmModuleFragmentGenerator.generateModule((IrModuleFragment) it2.next());
        }
        WasmModule wasmModuleLinkWasmCompiledFragments = wasmCompiledModuleFragment.linkWasmCompiledFragments();
        String strGenerateAsyncJsWrapper = null;
        if (z3) {
            WasmIrToText wasmIrToText = new WasmIrToText();
            wasmIrToText.appendWasmModule(wasmModuleLinkWasmCompiledFragments);
            string = wasmIrToText.toString();
        } else {
            string = null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String str = z4 ? baseFileName + ".map" : null;
        ArrayList arrayList = z4 ? new ArrayList() : null;
        String strAsString = ((IrModuleFragment) CollectionsKt.last((List) allModules)).getDescriptor().getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        new WasmIrToBinary(byteArrayOutputStream, wasmModuleLinkWasmCompiledFragments, strAsString, z, str, arrayList).appendWasmModule();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (backendContext.getConfiguration().get(JSConfigurationKeys.WASM_TARGET, WasmTarget.JS) == WasmTarget.JS) {
            strGenerateAsyncJsWrapper = generateAsyncJsWrapper(wasmCompiledModuleFragment, "./" + baseFileName + ".wasm", backendContext.getJsModuleAndQualifierReferences());
            strGenerateAsyncWasiWrapper = generateEsmExportsWrapper("./" + baseFileName + ".uninstantiated.mjs");
        } else {
            strGenerateAsyncWasiWrapper = generateAsyncWasiWrapper(wasmCompiledModuleFragment, "./" + baseFileName + ".wasm");
        }
        Intrinsics.checkNotNull(byteArray);
        return new WasmCompilerResult(string, strGenerateAsyncJsWrapper, strGenerateAsyncWasiWrapper, byteArray, generateSourceMap(backendContext.getConfiguration(), arrayList));
    }

    private static final String generateSourceMap(CompilerConfiguration compilerConfiguration, List<SourceLocationMapping> list) {
        SourceMapsInfo sourceMapsInfoFrom;
        if (list == null || (sourceMapsInfoFrom = SourceMapsInfo.INSTANCE.from(compilerConfiguration)) == null) {
            return null;
        }
        SourceMap3Builder sourceMap3Builder = new SourceMap3Builder(null, new Function0<Integer>() { // from class: org.jetbrains.kotlin.backend.wasm.CompilerKt$generateSourceMap$sourceMapBuilder$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                throw new IllegalStateException("This should not be called for Kotlin/Wasm".toString());
            }
        }, sourceMapsInfoFrom.getSourceMapPrefix());
        SourceFilePathResolver sourceFilePathResolverCreate = SourceFilePathResolver.INSTANCE.create(sourceMapsInfoFrom.getSourceRoots(), sourceMapsInfoFrom.getSourceMapPrefix(), sourceMapsInfoFrom.getOutputDir());
        SourceLocation.Location location = null;
        for (SourceLocationMapping sourceLocationMapping : list) {
            SourceLocation sourceLocation = sourceLocationMapping.getSourceLocation();
            SourceLocation.Location location2 = sourceLocation instanceof SourceLocation.Location ? (SourceLocation.Location) sourceLocation : null;
            if (location2 != null && !Intrinsics.areEqual(location2, location)) {
                String strSubstring = sourceFilePathResolverCreate.getPathRelativeToSourceRoots(new File(location2.getFile())).substring(3);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                sourceMap3Builder.addMapping(strSubstring, null, new Supplier() { // from class: org.jetbrains.kotlin.backend.wasm.CompilerKt$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return CompilerKt.generateSourceMap$lambda$6$lambda$5();
                    }
                }, location2.getLine(), location2.getColumn(), null, sourceLocationMapping.getOffset());
                location = location2;
            }
        }
        return sourceMap3Builder.build();
    }

    public static final String generateAsyncWasiWrapper(WasmCompiledModuleFragment wasmCompiledModuleFragment, String wasmFilePath) {
        Intrinsics.checkNotNullParameter(wasmCompiledModuleFragment, "<this>");
        Intrinsics.checkNotNullParameter(wasmFilePath, "wasmFilePath");
        return "\nimport { WASI } from 'wasi';\nimport { argv, env } from 'node:process';\n\nconst wasi = new WASI({ version: 'preview1', args: argv, env, });\n\nconst module = await import(/* webpackIgnore: true */'node:module');\nconst require = module.default.createRequire(import.meta.url);\nconst fs = require('fs');\nconst path = require('path');\nconst url = require('url');\nconst filepath = url.fileURLToPath(import.meta.url);\nconst dirpath = path.dirname(filepath);\nconst wasmBuffer = fs.readFileSync(path.resolve(dirpath, '" + wasmFilePath + "'));\nconst wasmModule = new WebAssembly.Module(wasmBuffer);\nconst wasmInstance = new WebAssembly.Instance(wasmModule, wasi.getImportObject());\n\nwasi.initialize(wasmInstance);\n\nexport default wasmInstance.exports;\n";
    }

    public static final String generateAsyncJsWrapper(WasmCompiledModuleFragment wasmCompiledModuleFragment, String wasmFilePath, Set<JsModuleAndQualifierReference> jsModuleAndQualifierReferences) {
        Intrinsics.checkNotNullParameter(wasmCompiledModuleFragment, "<this>");
        Intrinsics.checkNotNullParameter(wasmFilePath, "wasmFilePath");
        Intrinsics.checkNotNullParameter(jsModuleAndQualifierReferences, "jsModuleAndQualifierReferences");
        String strPrependIndent = StringsKt.prependIndent(CollectionsKt.joinToString$default(wasmCompiledModuleFragment.getJsFuns(), ",\n", null, null, 0, null, new Function1<WasmCompiledModuleFragment.JsCodeSnippet, CharSequence>() { // from class: org.jetbrains.kotlin.backend.wasm.CompilerKt$generateAsyncJsWrapper$jsCodeBody$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(WasmCompiledModuleFragment.JsCodeSnippet it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ((Object) JsHelpersKt.toJsStringLiteral(it.getImportName())) + " : " + it.getJsCode();
            }
        }, 30, null), "        ");
        String strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.sorted(CollectionsKt.toList(wasmCompiledModuleFragment.getJsModuleImports())), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.backend.wasm.CompilerKt$generateAsyncJsWrapper$imports$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                CharSequence jsStringLiteral = JsHelpersKt.toJsStringLiteral(it);
                return "        " + ((Object) jsStringLiteral) + ": imports[" + ((Object) jsStringLiteral) + "] ?? await import(" + ((Object) jsStringLiteral) + "),\n";
            }
        }, 30, null);
        Set<JsModuleAndQualifierReference> set = jsModuleAndQualifierReferences;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        for (JsModuleAndQualifierReference jsModuleAndQualifierReference : set) {
            String module = jsModuleAndQualifierReference.getModule();
            String qualifier = jsModuleAndQualifierReference.getQualifier();
            StringBuilder sb = new StringBuilder("    const ");
            sb.append(jsModuleAndQualifierReference.getJsVariableName());
            sb.append(" = ");
            if (module != null) {
                sb.append("(imports[" + ((Object) JsHelpersKt.toJsStringLiteral(module)) + "] ?? await import(" + ((Object) JsHelpersKt.toJsStringLiteral(module)) + "))");
                if (qualifier != null) {
                    sb.append(".");
                }
            }
            if (qualifier != null) {
                sb.append(qualifier);
            }
            sb.append(";");
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            arrayList.add(string);
        }
        return "\nexport async function instantiate(imports={}, runInitializer=true) {\n    const externrefBoxes = new WeakMap();\n    // ref must be non-null\n    function tryGetOrSetExternrefBox(ref, ifNotCached) {\n        if (typeof ref !== 'object') return ifNotCached;\n        const cachedBox = externrefBoxes.get(ref);\n        if (cachedBox !== void 0) return cachedBox;\n        externrefBoxes.set(ref, ifNotCached);\n        return ifNotCached;\n    }\n\n" + CollectionsKt.joinToString$default(CollectionsKt.sorted(arrayList), "\n", null, null, 0, null, null, 62, null) + "\n    \n    const js_code = {\n" + strPrependIndent + "\n    }\n    \n    // Placed here to give access to it from externals (js_code)\n    let wasmInstance;\n    let require; \n    let wasmExports;\n\n    const isNodeJs = (typeof process !== 'undefined') && (process.release.name === 'node');\n    const isStandaloneJsVM =\n        !isNodeJs && (\n            typeof d8 !== 'undefined' // V8\n            || typeof inIon !== 'undefined' // SpiderMonkey\n            || typeof jscOptions !== 'undefined' // JavaScriptCore\n        );\n    const isBrowser = !isNodeJs && !isStandaloneJsVM && (typeof window !== 'undefined');\n    \n    if (!isNodeJs && !isStandaloneJsVM && !isBrowser) {\n      throw \"Supported JS engine not detected\";\n    }\n    \n    const wasmFilePath = " + ((Object) JsHelpersKt.toJsStringLiteral(wasmFilePath)) + ";\n    const importObject = {\n        js_code,\n" + strJoinToString$default + "\n    };\n    \n    try {\n      if (isNodeJs) {\n        const module = await import(/* webpackIgnore: true */'node:module');\n        require = module.default.createRequire(import.meta.url);\n        const fs = require('fs');\n        const path = require('path');\n        const url = require('url');\n        const filepath = url.fileURLToPath(import.meta.url);\n        const dirpath = path.dirname(filepath);\n        const wasmBuffer = fs.readFileSync(path.resolve(dirpath, wasmFilePath));\n        const wasmModule = new WebAssembly.Module(wasmBuffer);\n        wasmInstance = new WebAssembly.Instance(wasmModule, importObject);\n      }\n      \n      if (isStandaloneJsVM) {\n        const wasmBuffer = read(wasmFilePath, 'binary');\n        const wasmModule = new WebAssembly.Module(wasmBuffer);\n        wasmInstance = new WebAssembly.Instance(wasmModule, importObject);\n      }\n      \n      if (isBrowser) {\n        wasmInstance = (await WebAssembly.instantiateStreaming(fetch(wasmFilePath), importObject)).instance;\n      }\n    } catch (e) {\n      if (e instanceof WebAssembly.CompileError) {\n        let text = `Please make sure that your runtime environment supports the latest version of Wasm GC and Exception-Handling proposals.\nFor more information, see https://kotl.in/wasm-help\n`;\n        if (isBrowser) {\n          console.error(text);\n        } else {\n          const t = \"\\n\" + text;\n          if (typeof console !== \"undefined\" && console.log !== void 0) \n            console.log(t);\n          else \n            print(t);\n        }\n      }\n      throw e;\n    }\n    \n    wasmExports = wasmInstance.exports;\n    if (runInitializer) {\n        wasmExports._initialize();\n    }\n\n    return { instance: wasmInstance,  exports: wasmExports };\n}\n";
    }

    public static final String generateEsmExportsWrapper(String asyncWrapperFileName) {
        Intrinsics.checkNotNullParameter(asyncWrapperFileName, "asyncWrapperFileName");
        return "\nimport { instantiate } from " + ((Object) JsHelpersKt.toJsStringLiteral(asyncWrapperFileName)) + ";\nexport default (await instantiate()).exports;\n";
    }

    public static final void writeCompilationResult(WasmCompilerResult result, File dir, String fileNameBase) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(dir, "dir");
        Intrinsics.checkNotNullParameter(fileNameBase, "fileNameBase");
        dir.mkdirs();
        if (result.getWat() != null) {
            FilesKt.writeText$default(new File(dir, fileNameBase + ".wat"), result.getWat(), null, 2, null);
        }
        FilesKt.writeBytes(new File(dir, fileNameBase + ".wasm"), result.getWasm());
        if (result.getJsUninstantiatedWrapper() != null) {
            FilesKt.writeText$default(new File(dir, fileNameBase + ".uninstantiated.mjs"), result.getJsUninstantiatedWrapper(), null, 2, null);
        }
        FilesKt.writeText$default(new File(dir, fileNameBase + ".mjs"), result.getJsWrapper(), null, 2, null);
        if (result.getSourceMap() != null) {
            FilesKt.writeText$default(new File(dir, fileNameBase + ".map"), result.getSourceMap(), null, 2, null);
        }
    }
}
