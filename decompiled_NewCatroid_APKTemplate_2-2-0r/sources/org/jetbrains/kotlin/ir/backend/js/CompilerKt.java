package org.jetbrains.kotlin.ir.backend.js;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.linkage.issues.ChecksKt;
import org.jetbrains.kotlin.backend.common.phaser.CompilerPhaseKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.lower.CollectNativeImplementationsKt;
import org.jetbrains.kotlin.ir.backend.js.lower.MoveBodilessDeclarationsToSeparatePlaceKt;
import org.jetbrains.kotlin.ir.backend.js.lower.TestGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsGenerationGranularity;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.StageController;
import org.jetbrains.kotlin.ir.util.ExternalDependenciesGenerator;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.RuntimeDiagnostic;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: compiler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0086\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000e2\u0010\b\u0002\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\t2\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u001a \u0001\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00180\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\f0 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0014\u001a\u00020\u0015¨\u0006'"}, d2 = {"compile", "Lorg/jetbrains/kotlin/ir/backend/js/LoweredIr;", "depsDescriptors", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "exportedDeclarations", "", "Lorg/jetbrains/kotlin/name/FqName;", "keep", "", "dceRuntimeDiagnostic", "Lorg/jetbrains/kotlin/js/config/RuntimeDiagnostic;", "verifySignatures", "", "safeExternalBoolean", "safeExternalBooleanDiagnostic", "filesToLower", "granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "compileIr", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "mainModule", "Lorg/jetbrains/kotlin/ir/backend/js/MainModule;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "dependencyModules", "", "moduleToName", "", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irLinker", "Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerKt {
    public static final LoweredIr compile(ModulesStructure depsDescriptors, PhaseConfig phaseConfig, IrFactory irFactory, Set<FqName> exportedDeclarations, Set<String> keep, RuntimeDiagnostic runtimeDiagnostic, boolean z, boolean z2, RuntimeDiagnostic runtimeDiagnostic2, Set<String> set, JsGenerationGranularity granularity) throws IOException {
        Intrinsics.checkNotNullParameter(depsDescriptors, "depsDescriptors");
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(exportedDeclarations, "exportedDeclarations");
        Intrinsics.checkNotNullParameter(keep, "keep");
        Intrinsics.checkNotNullParameter(granularity, "granularity");
        IrModuleInfo irModuleInfoLoadIr = KlibKt.loadIr(depsDescriptors, irFactory, z, set, true);
        IrModuleFragment module = irModuleInfoLoadIr.getModule();
        List<IrModuleFragment> listComponent2 = irModuleInfoLoadIr.component2();
        IrBuiltIns bultins = irModuleInfoLoadIr.getBultins();
        SymbolTable symbolTable = irModuleInfoLoadIr.getSymbolTable();
        JsIrLinker deserializer = irModuleInfoLoadIr.getDeserializer();
        return compileIr(module, depsDescriptors.getMainModule(), depsDescriptors.getCompilerConfiguration(), listComponent2, irModuleInfoLoadIr.component6(), bultins, symbolTable, deserializer, phaseConfig, exportedDeclarations, keep, runtimeDiagnostic, z2, runtimeDiagnostic2, granularity);
    }

    public static final LoweredIr compileIr(IrModuleFragment moduleFragment, MainModule mainModule, CompilerConfiguration configuration, List<? extends IrModuleFragment> dependencyModules, Map<IrModuleFragment, String> moduleToName, IrBuiltIns irBuiltIns, SymbolTable symbolTable, JsIrLinker irLinker, PhaseConfig phaseConfig, Set<FqName> exportedDeclarations, Set<String> keep, RuntimeDiagnostic runtimeDiagnostic, boolean z, RuntimeDiagnostic runtimeDiagnostic2, JsGenerationGranularity granularity) {
        List<? extends IrModuleFragment> listPlus;
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        Intrinsics.checkNotNullParameter(mainModule, "mainModule");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(dependencyModules, "dependencyModules");
        Intrinsics.checkNotNullParameter(moduleToName, "moduleToName");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irLinker, "irLinker");
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(exportedDeclarations, "exportedDeclarations");
        Intrinsics.checkNotNullParameter(keep, "keep");
        Intrinsics.checkNotNullParameter(granularity, "granularity");
        ModuleDescriptor descriptor = moduleFragment.getDescriptor();
        IrFactory irFactory = symbolTable.getIrFactory();
        boolean z2 = configuration.getBoolean(JSConfigurationKeys.GENERATE_POLYFILLS);
        if (mainModule instanceof MainModule.SourceFiles) {
            listPlus = CollectionsKt.plus((Collection) dependencyModules, (Iterable) CollectionsKt.listOf(moduleFragment));
        } else {
            if (!(mainModule instanceof MainModule.Klib)) {
                throw new NoWhenBranchMatchedException();
            }
            listPlus = dependencyModules;
        }
        JsIrBackendContext jsIrBackendContext = jsIrBackendContext;
        List<? extends IrModuleFragment> list = listPlus;
        JsIrBackendContext jsIrBackendContext2 = new JsIrBackendContext(descriptor, irBuiltIns, symbolTable, exportedDeclarations, keep, configuration, runtimeDiagnostic, z, runtimeDiagnostic2, null, granularity, false, 512, null);
        new ExternalDependenciesGenerator(symbolTable, CollectionsKt.listOf(irLinker)).generateUnboundSymbolsAsDependencies();
        irLinker.postProcess(true);
        ChecksKt.checkNoUnboundSymbols(irLinker, symbolTable, "at the end of IR linkage process");
        irLinker.clear();
        List<? extends IrModuleFragment> list2 = list;
        for (IrModuleFragment irModuleFragment : list2) {
            JsIrBackendContext jsIrBackendContext3 = jsIrBackendContext;
            if (z2) {
                CollectNativeImplementationsKt.collectNativeImplementations(jsIrBackendContext3, irModuleFragment);
            }
            MoveBodilessDeclarationsToSeparatePlaceKt.moveBodilessDeclarationsToSeparatePlace(jsIrBackendContext3, irModuleFragment);
            jsIrBackendContext = jsIrBackendContext3;
        }
        JsIrBackendContext jsIrBackendContext4 = jsIrBackendContext;
        TestGeneratorKt.generateJsTests(jsIrBackendContext4, (IrModuleFragment) CollectionsKt.last((List) list));
        StageController stageController = irFactory.getStageController();
        WholeWorldStageController wholeWorldStageController = stageController instanceof WholeWorldStageController ? (WholeWorldStageController) stageController : null;
        if (wholeWorldStageController != null) {
            CompilerWithICKt.lowerPreservingTags(list2, jsIrBackendContext4, phaseConfig, wholeWorldStageController);
        } else {
            CompilerPhaseKt.invokeToplevel(JsLoweringPhasesKt.getJsPhases(), phaseConfig, jsIrBackendContext4, list);
        }
        return new LoweredIr(jsIrBackendContext4, moduleFragment, list, moduleToName);
    }
}
