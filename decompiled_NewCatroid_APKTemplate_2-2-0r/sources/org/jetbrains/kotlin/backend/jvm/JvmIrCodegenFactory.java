package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.common.extensions.IrIntrinsicExtension;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContextImpl;
import org.jetbrains.kotlin.backend.common.phaser.CompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.CompilerPhaseKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.common.serialization.DescriptorByIdSignatureFinderImpl;
import org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker;
import org.jetbrains.kotlin.backend.jvm.codegen.EnumEntriesIntrinsicMappingsCacheImpl;
import org.jetbrains.kotlin.backend.jvm.codegen.JvmIrIntrinsicExtension;
import org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod;
import org.jetbrains.kotlin.backend.jvm.intrinsics.IrIntrinsicMethods;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.serialization.DisabledIdSignatureDescriptor;
import org.jetbrains.kotlin.backend.jvm.serialization.JvmIdSignatureDescriptor;
import org.jetbrains.kotlin.codegen.ClassFileFactory;
import org.jetbrains.kotlin.codegen.CodegenFactory;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmSerializeIrMode;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmDescriptorMangler;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmIrLinker;
import org.jetbrains.kotlin.ir.builders.TranslationPluginContext;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrModuleFragmentImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.util.ExternalDependenciesGenerator;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.IrMessageLoggerKt;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.metadata.DeserializedKlibModuleOrigin;
import org.jetbrains.kotlin.library.metadata.KlibModuleOrigin;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi2ir.Psi2IrConfiguration;
import org.jetbrains.kotlin.psi2ir.Psi2IrPostprocessingStep;
import org.jetbrains.kotlin.psi2ir.Psi2IrTranslator;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.psi2ir.generators.DeclarationStubGeneratorForNotFoundClasses;
import org.jetbrains.kotlin.psi2ir.generators.DeclarationStubGeneratorImpl;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;
import org.jetbrains.kotlin.psi2ir.generators.fragments.EvaluatorFragmentInfo;
import org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentContext;
import org.jetbrains.kotlin.psi2ir.preprocessing.SourceDeclarationsPreprocessor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.CleanableBindingContext;
import org.jetbrains.kotlin.utils.IDEAPlatforms;
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI;

/* JADX INFO: compiled from: JvmIrCodegenFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\b\u0016\u0018\u00002\u00020\u0001:\u000389:Bo\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\u001c\b\u0002\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u00010\r\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016JT\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\t2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u000e\b\u0002\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001d0,J\u001e\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.2\f\u00100\u001a\b\u0012\u0004\u0012\u00020201H\u0016J\u0010\u00103\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u000204H\u0016J\u0018\u00105\u001a\u0002042\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020.H\u0016J\u0012\u00106\u001a\b\u0012\u0004\u0012\u0002070#*\u000207H\u0002R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;²\u0006\n\u0010<\u001a\u00020=X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrCodegenFactory;", "Lorg/jetbrains/kotlin/codegen/CodegenFactory;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "externalMangler", "Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler;", "externalSymbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "jvmGeneratorExtensions", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl;", "prefixPhases", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "evaluatorFragmentInfoForPsi2Ir", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;", "shouldStubAndNotLinkUnboundSymbols", "", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl;Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;Z)V", "ideCodegenSettings", "Lorg/jetbrains/kotlin/backend/jvm/JvmIrCodegenFactory$IdeCodegenSettings;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl;Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;Lorg/jetbrains/kotlin/backend/jvm/JvmIrCodegenFactory$IdeCodegenSettings;)V", "convertToIr", "Lorg/jetbrains/kotlin/backend/jvm/JvmIrCodegenFactory$JvmIrBackendInput;", "input", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$IrConversionInput;", "generateModuleInFrontendIRMode", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "irModuleFragment", "symbolTable", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "extensions", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "backendExtension", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;", "irPluginContext", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "notifyCodegenStart", "Lkotlin/Function0;", "getModuleChunkBackendInput", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$BackendInput;", "wholeBackendInput", "sourceFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "invokeCodegen", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$CodegenInput;", "invokeLowerings", "collectAllDependencyModulesTransitively", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "IdeCodegenSettings", "JvmIrBackendInput", "JvmIrCodegenInput", "backend.jvm.entrypoint", "intrinsics", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IrIntrinsicMethods;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmIrCodegenFactory implements CodegenFactory {
    private final EvaluatorFragmentInfo evaluatorFragmentInfoForPsi2Ir;
    private final JvmDescriptorMangler externalMangler;
    private final SymbolTable externalSymbolTable;
    private final IdeCodegenSettings ideCodegenSettings;
    private final JvmGeneratorExtensionsImpl jvmGeneratorExtensions;
    private final PhaseConfig phaseConfig;

    public JvmIrCodegenFactory(CompilerConfiguration configuration, PhaseConfig phaseConfig, JvmDescriptorMangler jvmDescriptorMangler, SymbolTable symbolTable, JvmGeneratorExtensionsImpl jvmGeneratorExtensions, EvaluatorFragmentInfo evaluatorFragmentInfo, IdeCodegenSettings ideCodegenSettings) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(jvmGeneratorExtensions, "jvmGeneratorExtensions");
        Intrinsics.checkNotNullParameter(ideCodegenSettings, "ideCodegenSettings");
        this.phaseConfig = phaseConfig;
        this.externalMangler = jvmDescriptorMangler;
        this.externalSymbolTable = symbolTable;
        this.jvmGeneratorExtensions = jvmGeneratorExtensions;
        this.evaluatorFragmentInfoForPsi2Ir = evaluatorFragmentInfo;
        this.ideCodegenSettings = ideCodegenSettings;
        if (ideCodegenSettings.getShouldDeduplicateBuiltInSymbols() && !ideCodegenSettings.getShouldStubAndNotLinkUnboundSymbols()) {
            throw new IllegalStateException("`shouldDeduplicateBuiltInSymbols` depends on `shouldStubAndNotLinkUnboundSymbols` being enabled. Deduplication of built-in symbols hasn't been tested without stubbing and there is currently no use case for it without stubbing.");
        }
    }

    public /* synthetic */ JvmIrCodegenFactory(CompilerConfiguration compilerConfiguration, PhaseConfig phaseConfig, JvmDescriptorMangler jvmDescriptorMangler, SymbolTable symbolTable, JvmGeneratorExtensionsImpl jvmGeneratorExtensionsImpl, EvaluatorFragmentInfo evaluatorFragmentInfo, IdeCodegenSettings ideCodegenSettings, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerConfiguration, phaseConfig, (i & 4) != 0 ? null : jvmDescriptorMangler, (i & 8) != 0 ? null : symbolTable, (i & 16) != 0 ? new JvmGeneratorExtensionsImpl(compilerConfiguration, false, 2, null) : jvmGeneratorExtensionsImpl, (i & 32) != 0 ? null : evaluatorFragmentInfo, (i & 64) != 0 ? new IdeCodegenSettings(false, false, false, false, false, 31, null) : ideCodegenSettings);
    }

    public /* synthetic */ JvmIrCodegenFactory(CompilerConfiguration compilerConfiguration, PhaseConfig phaseConfig, JvmDescriptorMangler jvmDescriptorMangler, SymbolTable symbolTable, JvmGeneratorExtensionsImpl jvmGeneratorExtensionsImpl, CompilerPhase compilerPhase, EvaluatorFragmentInfo evaluatorFragmentInfo, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerConfiguration, phaseConfig, (i & 4) != 0 ? null : jvmDescriptorMangler, (i & 8) != 0 ? null : symbolTable, (i & 16) != 0 ? new JvmGeneratorExtensionsImpl(compilerConfiguration, false, 2, null) : jvmGeneratorExtensionsImpl, (i & 32) != 0 ? null : compilerPhase, (i & 64) != 0 ? null : evaluatorFragmentInfo, (i & 128) != 0 ? false : z);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @IDEAPluginsCompatibilityAPI(message = "Please migrate to the other constructor", plugins = "Android Studio", usedIn = {IDEAPlatforms._221})
    public JvmIrCodegenFactory(CompilerConfiguration configuration, PhaseConfig phaseConfig, JvmDescriptorMangler jvmDescriptorMangler, SymbolTable symbolTable, JvmGeneratorExtensionsImpl jvmGeneratorExtensions, CompilerPhase<? super JvmBackendContext, IrModuleFragment, IrModuleFragment> compilerPhase, EvaluatorFragmentInfo evaluatorFragmentInfo, boolean z) {
        this(configuration, phaseConfig, jvmDescriptorMangler, symbolTable, jvmGeneratorExtensions, evaluatorFragmentInfo, new IdeCodegenSettings(z, false, false, false, false, 30, null));
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(jvmGeneratorExtensions, "jvmGeneratorExtensions");
    }

    /* JADX INFO: compiled from: JvmIrCodegenFactory.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrCodegenFactory$IdeCodegenSettings;", "", "shouldStubAndNotLinkUnboundSymbols", "", "shouldStubOrphanedExpectSymbols", "shouldReferenceUndiscoveredExpectSymbols", "shouldDeduplicateBuiltInSymbols", "doNotLoadDependencyModuleHeaders", "(ZZZZZ)V", "getDoNotLoadDependencyModuleHeaders", "()Z", "getShouldDeduplicateBuiltInSymbols", "getShouldReferenceUndiscoveredExpectSymbols", "getShouldStubAndNotLinkUnboundSymbols", "getShouldStubOrphanedExpectSymbols", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class IdeCodegenSettings {
        private final boolean doNotLoadDependencyModuleHeaders;
        private final boolean shouldDeduplicateBuiltInSymbols;
        private final boolean shouldReferenceUndiscoveredExpectSymbols;
        private final boolean shouldStubAndNotLinkUnboundSymbols;
        private final boolean shouldStubOrphanedExpectSymbols;

        public IdeCodegenSettings() {
            this(false, false, false, false, false, 31, null);
        }

        public static /* synthetic */ IdeCodegenSettings copy$default(IdeCodegenSettings ideCodegenSettings, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i, Object obj) {
            if ((i & 1) != 0) {
                z = ideCodegenSettings.shouldStubAndNotLinkUnboundSymbols;
            }
            if ((i & 2) != 0) {
                z2 = ideCodegenSettings.shouldStubOrphanedExpectSymbols;
            }
            boolean z6 = z2;
            if ((i & 4) != 0) {
                z3 = ideCodegenSettings.shouldReferenceUndiscoveredExpectSymbols;
            }
            boolean z7 = z3;
            if ((i & 8) != 0) {
                z4 = ideCodegenSettings.shouldDeduplicateBuiltInSymbols;
            }
            boolean z8 = z4;
            if ((i & 16) != 0) {
                z5 = ideCodegenSettings.doNotLoadDependencyModuleHeaders;
            }
            return ideCodegenSettings.copy(z, z6, z7, z8, z5);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getShouldStubAndNotLinkUnboundSymbols() {
            return this.shouldStubAndNotLinkUnboundSymbols;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getShouldStubOrphanedExpectSymbols() {
            return this.shouldStubOrphanedExpectSymbols;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getShouldReferenceUndiscoveredExpectSymbols() {
            return this.shouldReferenceUndiscoveredExpectSymbols;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getShouldDeduplicateBuiltInSymbols() {
            return this.shouldDeduplicateBuiltInSymbols;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getDoNotLoadDependencyModuleHeaders() {
            return this.doNotLoadDependencyModuleHeaders;
        }

        public final IdeCodegenSettings copy(boolean shouldStubAndNotLinkUnboundSymbols, boolean shouldStubOrphanedExpectSymbols, boolean shouldReferenceUndiscoveredExpectSymbols, boolean shouldDeduplicateBuiltInSymbols, boolean doNotLoadDependencyModuleHeaders) {
            return new IdeCodegenSettings(shouldStubAndNotLinkUnboundSymbols, shouldStubOrphanedExpectSymbols, shouldReferenceUndiscoveredExpectSymbols, shouldDeduplicateBuiltInSymbols, doNotLoadDependencyModuleHeaders);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof IdeCodegenSettings)) {
                return false;
            }
            IdeCodegenSettings ideCodegenSettings = (IdeCodegenSettings) other;
            return this.shouldStubAndNotLinkUnboundSymbols == ideCodegenSettings.shouldStubAndNotLinkUnboundSymbols && this.shouldStubOrphanedExpectSymbols == ideCodegenSettings.shouldStubOrphanedExpectSymbols && this.shouldReferenceUndiscoveredExpectSymbols == ideCodegenSettings.shouldReferenceUndiscoveredExpectSymbols && this.shouldDeduplicateBuiltInSymbols == ideCodegenSettings.shouldDeduplicateBuiltInSymbols && this.doNotLoadDependencyModuleHeaders == ideCodegenSettings.doNotLoadDependencyModuleHeaders;
        }

        public int hashCode() {
            return (((((((Boolean.hashCode(this.shouldStubAndNotLinkUnboundSymbols) * 31) + Boolean.hashCode(this.shouldStubOrphanedExpectSymbols)) * 31) + Boolean.hashCode(this.shouldReferenceUndiscoveredExpectSymbols)) * 31) + Boolean.hashCode(this.shouldDeduplicateBuiltInSymbols)) * 31) + Boolean.hashCode(this.doNotLoadDependencyModuleHeaders);
        }

        public String toString() {
            return "IdeCodegenSettings(shouldStubAndNotLinkUnboundSymbols=" + this.shouldStubAndNotLinkUnboundSymbols + ", shouldStubOrphanedExpectSymbols=" + this.shouldStubOrphanedExpectSymbols + ", shouldReferenceUndiscoveredExpectSymbols=" + this.shouldReferenceUndiscoveredExpectSymbols + ", shouldDeduplicateBuiltInSymbols=" + this.shouldDeduplicateBuiltInSymbols + ", doNotLoadDependencyModuleHeaders=" + this.doNotLoadDependencyModuleHeaders + ')';
        }

        public IdeCodegenSettings(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.shouldStubAndNotLinkUnboundSymbols = z;
            this.shouldStubOrphanedExpectSymbols = z2;
            this.shouldReferenceUndiscoveredExpectSymbols = z3;
            this.shouldDeduplicateBuiltInSymbols = z4;
            this.doNotLoadDependencyModuleHeaders = z5;
        }

        public /* synthetic */ IdeCodegenSettings(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3, (i & 8) != 0 ? false : z4, (i & 16) != 0 ? false : z5);
        }

        public final boolean getShouldStubAndNotLinkUnboundSymbols() {
            return this.shouldStubAndNotLinkUnboundSymbols;
        }

        public final boolean getShouldStubOrphanedExpectSymbols() {
            return this.shouldStubOrphanedExpectSymbols;
        }

        public final boolean getShouldReferenceUndiscoveredExpectSymbols() {
            return this.shouldReferenceUndiscoveredExpectSymbols;
        }

        public final boolean getShouldDeduplicateBuiltInSymbols() {
            return this.shouldDeduplicateBuiltInSymbols;
        }

        public final boolean getDoNotLoadDependencyModuleHeaders() {
            return this.doNotLoadDependencyModuleHeaders;
        }
    }

    /* JADX INFO: compiled from: JvmIrCodegenFactory.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\u0002\u0010\u0014J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0005HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0003J\t\u0010)\u001a\u00020\fHÆ\u0003J\t\u0010*\u001a\u00020\u000eHÆ\u0003J\t\u0010+\u001a\u00020\u0010HÆ\u0003J\u000f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012HÆ\u0003Jg\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012HÆ\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u000101HÖ\u0003J\t\u00102\u001a\u000203HÖ\u0001J\t\u00104\u001a\u000205HÖ\u0001R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrCodegenFactory$JvmIrBackendInput;", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$BackendInput;", "irModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "extensions", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "backendExtension", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;", "pluginContext", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "notifyCodegenStart", "Lkotlin/Function0;", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;Ljava/util/List;Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;Lkotlin/jvm/functions/Function0;)V", "getBackendExtension", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;", "getExtensions", "()Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "getIrModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getIrProviders", "()Ljava/util/List;", "getNotifyCodegenStart", "()Lkotlin/jvm/functions/Function0;", "getPhaseConfig", "()Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "getPluginContext", "()Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class JvmIrBackendInput implements CodegenFactory.BackendInput {
        private final JvmBackendExtension backendExtension;
        private final JvmGeneratorExtensions extensions;
        private final IrModuleFragment irModuleFragment;
        private final List<IrProvider> irProviders;
        private final Function0<Unit> notifyCodegenStart;
        private final PhaseConfig phaseConfig;
        private final IrPluginContext pluginContext;
        private final SymbolTable symbolTable;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ JvmIrBackendInput copy$default(JvmIrBackendInput jvmIrBackendInput, IrModuleFragment irModuleFragment, SymbolTable symbolTable, PhaseConfig phaseConfig, List list, JvmGeneratorExtensions jvmGeneratorExtensions, JvmBackendExtension jvmBackendExtension, IrPluginContext irPluginContext, Function0 function0, int i, Object obj) {
            return jvmIrBackendInput.copy((i & 1) != 0 ? jvmIrBackendInput.irModuleFragment : irModuleFragment, (i & 2) != 0 ? jvmIrBackendInput.symbolTable : symbolTable, (i & 4) != 0 ? jvmIrBackendInput.phaseConfig : phaseConfig, (i & 8) != 0 ? jvmIrBackendInput.irProviders : list, (i & 16) != 0 ? jvmIrBackendInput.extensions : jvmGeneratorExtensions, (i & 32) != 0 ? jvmIrBackendInput.backendExtension : jvmBackendExtension, (i & 64) != 0 ? jvmIrBackendInput.pluginContext : irPluginContext, (i & 128) != 0 ? jvmIrBackendInput.notifyCodegenStart : function0);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrModuleFragment getIrModuleFragment() {
            return this.irModuleFragment;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final SymbolTable getSymbolTable() {
            return this.symbolTable;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final PhaseConfig getPhaseConfig() {
            return this.phaseConfig;
        }

        public final List<IrProvider> component4() {
            return this.irProviders;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final JvmGeneratorExtensions getExtensions() {
            return this.extensions;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final JvmBackendExtension getBackendExtension() {
            return this.backendExtension;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final IrPluginContext getPluginContext() {
            return this.pluginContext;
        }

        public final Function0<Unit> component8() {
            return this.notifyCodegenStart;
        }

        public final JvmIrBackendInput copy(IrModuleFragment irModuleFragment, SymbolTable symbolTable, PhaseConfig phaseConfig, List<? extends IrProvider> irProviders, JvmGeneratorExtensions extensions, JvmBackendExtension backendExtension, IrPluginContext pluginContext, Function0<Unit> notifyCodegenStart) {
            Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
            Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
            Intrinsics.checkNotNullParameter(irProviders, "irProviders");
            Intrinsics.checkNotNullParameter(extensions, "extensions");
            Intrinsics.checkNotNullParameter(backendExtension, "backendExtension");
            Intrinsics.checkNotNullParameter(pluginContext, "pluginContext");
            Intrinsics.checkNotNullParameter(notifyCodegenStart, "notifyCodegenStart");
            return new JvmIrBackendInput(irModuleFragment, symbolTable, phaseConfig, irProviders, extensions, backendExtension, pluginContext, notifyCodegenStart);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof JvmIrBackendInput)) {
                return false;
            }
            JvmIrBackendInput jvmIrBackendInput = (JvmIrBackendInput) other;
            return Intrinsics.areEqual(this.irModuleFragment, jvmIrBackendInput.irModuleFragment) && Intrinsics.areEqual(this.symbolTable, jvmIrBackendInput.symbolTable) && Intrinsics.areEqual(this.phaseConfig, jvmIrBackendInput.phaseConfig) && Intrinsics.areEqual(this.irProviders, jvmIrBackendInput.irProviders) && Intrinsics.areEqual(this.extensions, jvmIrBackendInput.extensions) && Intrinsics.areEqual(this.backendExtension, jvmIrBackendInput.backendExtension) && Intrinsics.areEqual(this.pluginContext, jvmIrBackendInput.pluginContext) && Intrinsics.areEqual(this.notifyCodegenStart, jvmIrBackendInput.notifyCodegenStart);
        }

        public int hashCode() {
            int iHashCode = ((this.irModuleFragment.hashCode() * 31) + this.symbolTable.hashCode()) * 31;
            PhaseConfig phaseConfig = this.phaseConfig;
            return ((((((((((iHashCode + (phaseConfig == null ? 0 : phaseConfig.hashCode())) * 31) + this.irProviders.hashCode()) * 31) + this.extensions.hashCode()) * 31) + this.backendExtension.hashCode()) * 31) + this.pluginContext.hashCode()) * 31) + this.notifyCodegenStart.hashCode();
        }

        public String toString() {
            return "JvmIrBackendInput(irModuleFragment=" + this.irModuleFragment + ", symbolTable=" + this.symbolTable + ", phaseConfig=" + this.phaseConfig + ", irProviders=" + this.irProviders + ", extensions=" + this.extensions + ", backendExtension=" + this.backendExtension + ", pluginContext=" + this.pluginContext + ", notifyCodegenStart=" + this.notifyCodegenStart + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public JvmIrBackendInput(IrModuleFragment irModuleFragment, SymbolTable symbolTable, PhaseConfig phaseConfig, List<? extends IrProvider> irProviders, JvmGeneratorExtensions extensions, JvmBackendExtension backendExtension, IrPluginContext pluginContext, Function0<Unit> notifyCodegenStart) {
            Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
            Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
            Intrinsics.checkNotNullParameter(irProviders, "irProviders");
            Intrinsics.checkNotNullParameter(extensions, "extensions");
            Intrinsics.checkNotNullParameter(backendExtension, "backendExtension");
            Intrinsics.checkNotNullParameter(pluginContext, "pluginContext");
            Intrinsics.checkNotNullParameter(notifyCodegenStart, "notifyCodegenStart");
            this.irModuleFragment = irModuleFragment;
            this.symbolTable = symbolTable;
            this.phaseConfig = phaseConfig;
            this.irProviders = irProviders;
            this.extensions = extensions;
            this.backendExtension = backendExtension;
            this.pluginContext = pluginContext;
            this.notifyCodegenStart = notifyCodegenStart;
        }

        public final IrModuleFragment getIrModuleFragment() {
            return this.irModuleFragment;
        }

        public final SymbolTable getSymbolTable() {
            return this.symbolTable;
        }

        public final PhaseConfig getPhaseConfig() {
            return this.phaseConfig;
        }

        public final List<IrProvider> getIrProviders() {
            return this.irProviders;
        }

        public final JvmGeneratorExtensions getExtensions() {
            return this.extensions;
        }

        public final JvmBackendExtension getBackendExtension() {
            return this.backendExtension;
        }

        public final IrPluginContext getPluginContext() {
            return this.pluginContext;
        }

        public final Function0<Unit> getNotifyCodegenStart() {
            return this.notifyCodegenStart;
        }
    }

    /* JADX INFO: compiled from: JvmIrCodegenFactory.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0003J7\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cHÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrCodegenFactory$JvmIrCodegenInput;", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$CodegenInput;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "notifyCodegenStart", "Lkotlin/Function0;", "", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Lkotlin/jvm/functions/Function0;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getModule", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getNotifyCodegenStart", "()Lkotlin/jvm/functions/Function0;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class JvmIrCodegenInput implements CodegenFactory.CodegenInput {
        private final JvmBackendContext context;
        private final IrModuleFragment module;
        private final Function0<Unit> notifyCodegenStart;
        private final GenerationState state;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ JvmIrCodegenInput copy$default(JvmIrCodegenInput jvmIrCodegenInput, GenerationState generationState, JvmBackendContext jvmBackendContext, IrModuleFragment irModuleFragment, Function0 function0, int i, Object obj) {
            if ((i & 1) != 0) {
                generationState = jvmIrCodegenInput.state;
            }
            if ((i & 2) != 0) {
                jvmBackendContext = jvmIrCodegenInput.context;
            }
            if ((i & 4) != 0) {
                irModuleFragment = jvmIrCodegenInput.module;
            }
            if ((i & 8) != 0) {
                function0 = jvmIrCodegenInput.notifyCodegenStart;
            }
            return jvmIrCodegenInput.copy(generationState, jvmBackendContext, irModuleFragment, function0);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final GenerationState getState() {
            return this.state;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final JvmBackendContext getContext() {
            return this.context;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final IrModuleFragment getModule() {
            return this.module;
        }

        public final Function0<Unit> component4() {
            return this.notifyCodegenStart;
        }

        public final JvmIrCodegenInput copy(GenerationState state, JvmBackendContext context, IrModuleFragment module, Function0<Unit> notifyCodegenStart) {
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(notifyCodegenStart, "notifyCodegenStart");
            return new JvmIrCodegenInput(state, context, module, notifyCodegenStart);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof JvmIrCodegenInput)) {
                return false;
            }
            JvmIrCodegenInput jvmIrCodegenInput = (JvmIrCodegenInput) other;
            return Intrinsics.areEqual(this.state, jvmIrCodegenInput.state) && Intrinsics.areEqual(this.context, jvmIrCodegenInput.context) && Intrinsics.areEqual(this.module, jvmIrCodegenInput.module) && Intrinsics.areEqual(this.notifyCodegenStart, jvmIrCodegenInput.notifyCodegenStart);
        }

        public int hashCode() {
            return (((((this.state.hashCode() * 31) + this.context.hashCode()) * 31) + this.module.hashCode()) * 31) + this.notifyCodegenStart.hashCode();
        }

        public String toString() {
            return "JvmIrCodegenInput(state=" + this.state + ", context=" + this.context + ", module=" + this.module + ", notifyCodegenStart=" + this.notifyCodegenStart + ')';
        }

        public JvmIrCodegenInput(GenerationState state, JvmBackendContext context, IrModuleFragment module, Function0<Unit> notifyCodegenStart) {
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(notifyCodegenStart, "notifyCodegenStart");
            this.state = state;
            this.context = context;
            this.module = module;
            this.notifyCodegenStart = notifyCodegenStart;
        }

        @Override // org.jetbrains.kotlin.codegen.CodegenFactory.CodegenInput
        public GenerationState getState() {
            return this.state;
        }

        public final JvmBackendContext getContext() {
            return this.context;
        }

        public final IrModuleFragment getModule() {
            return this.module;
        }

        public final Function0<Unit> getNotifyCodegenStart() {
            return this.notifyCodegenStart;
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0045  */
    @Override // org.jetbrains.kotlin.codegen.CodegenFactory
    public JvmIrBackendInput convertToIr(CodegenFactory.IrConversionInput input) {
        boolean z;
        DisabledIdSignatureDescriptor jvmIdSignatureDescriptor;
        Pair pair;
        ArrayList arrayListEmptyList;
        List<? extends IrProvider> listListOf;
        Intrinsics.checkNotNullParameter(input, "input");
        if (input.getConfiguration().getBoolean(JVMConfigurationKeys.LINK_VIA_SIGNATURES) || input.getConfiguration().get(JVMConfigurationKeys.SERIALIZE_IR, JvmSerializeIrMode.NONE) != JvmSerializeIrMode.NONE) {
            z = true;
        } else {
            Object obj = input.getConfiguration().get(JVMConfigurationKeys.KLIB_PATHS, CollectionsKt.emptyList());
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            if (((Collection) obj).isEmpty()) {
                z = false;
            } else {
                z = true;
            }
        }
        if (this.externalSymbolTable != null) {
            JvmDescriptorMangler jvmDescriptorMangler = this.externalMangler;
            Intrinsics.checkNotNull(jvmDescriptorMangler);
            pair = TuplesKt.to(jvmDescriptorMangler, this.externalSymbolTable);
        } else {
            JvmDescriptorMangler jvmDescriptorMangler2 = new JvmDescriptorMangler(new MainFunctionDetector(input.getBindingContext(), input.getLanguageVersionSettings()));
            if (z) {
                jvmIdSignatureDescriptor = new JvmIdSignatureDescriptor(jvmDescriptorMangler2);
            } else {
                jvmIdSignatureDescriptor = DisabledIdSignatureDescriptor.INSTANCE;
            }
            pair = TuplesKt.to(jvmDescriptorMangler2, this.ideCodegenSettings.getShouldDeduplicateBuiltInSymbols() ? new SymbolTableWithBuiltInsDeduplication(jvmIdSignatureDescriptor, IrFactoryImpl.INSTANCE) : new SymbolTable(jvmIdSignatureDescriptor, IrFactoryImpl.INSTANCE, null, 4, null));
        }
        JvmDescriptorMangler jvmDescriptorMangler3 = (JvmDescriptorMangler) pair.component1();
        final SymbolTable symbolTable = (SymbolTable) pair.component2();
        IrMessageLogger irMessageLogger = IrMessageLoggerKt.getIrMessageLogger(input.getConfiguration());
        Psi2IrTranslator psi2IrTranslator = new Psi2IrTranslator(input.getLanguageVersionSettings(), new Psi2IrConfiguration(input.getIgnoreErrors(), false, input.getSkipBodies()), new JvmIrCodegenFactory$convertToIr$psi2ir$1(irMessageLogger));
        final GeneratorContext generatorContextCreateGeneratorContext = psi2IrTranslator.createGeneratorContext(input.getModule(), input.getBindingContext(), symbolTable, this.jvmGeneratorExtensions, this.evaluatorFragmentInfoForPsi2Ir != null ? new FragmentContext(null, 1, null) : null);
        if (symbolTable instanceof SymbolTableWithBuiltInsDeduplication) {
            IrBuiltIns irBuiltIns = generatorContextCreateGeneratorContext.getIrBuiltIns();
            IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = irBuiltIns instanceof IrBuiltInsOverDescriptors ? (IrBuiltInsOverDescriptors) irBuiltIns : null;
            if (irBuiltInsOverDescriptors != null) {
                ((SymbolTableWithBuiltInsDeduplication) symbolTable).bindIrBuiltIns(irBuiltInsOverDescriptors);
            }
        }
        List<IrGenerationExtension> instances = IrGenerationExtension.INSTANCE.getInstances(input.getProject());
        final DeclarationStubGeneratorImpl declarationStubGeneratorImpl = new DeclarationStubGeneratorImpl(generatorContextCreateGeneratorContext.getModuleDescriptor(), symbolTable, generatorContextCreateGeneratorContext.getIrBuiltIns(), new DescriptorByIdSignatureFinderImpl(generatorContextCreateGeneratorContext.getModuleDescriptor(), jvmDescriptorMangler3, null, 4, null), this.jvmGeneratorExtensions);
        DeclarationStubGeneratorImpl declarationStubGeneratorImpl2 = declarationStubGeneratorImpl;
        JvmIrLinker jvmIrLinker = new JvmIrLinker(generatorContextCreateGeneratorContext.getModuleDescriptor(), irMessageLogger, new JvmIrTypeSystemContext(generatorContextCreateGeneratorContext.getIrBuiltIns()), symbolTable, new TranslationPluginContext() { // from class: org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory$convertToIr$frontEndContext$1
            @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContext
            public IrFactory getIrFactory() {
                return TranslationPluginContext.DefaultImpls.getIrFactory(this);
            }

            @Override // org.jetbrains.kotlin.ir.builders.TranslationPluginContext
            public ModuleDescriptor getModuleDescriptor() {
                return generatorContextCreateGeneratorContext.getModuleDescriptor();
            }

            @Override // org.jetbrains.kotlin.ir.builders.TranslationPluginContext
            public ReferenceSymbolTable getSymbolTable() {
                return symbolTable;
            }

            @Override // org.jetbrains.kotlin.ir.builders.TranslationPluginContext
            public TypeTranslator getTypeTranslator() {
                return generatorContextCreateGeneratorContext.getTypeTranslator();
            }

            @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContextInterface
            public IrBuiltIns getIrBuiltIns() {
                return generatorContextCreateGeneratorContext.getIrBuiltIns();
            }
        }, declarationStubGeneratorImpl2, jvmDescriptorMangler3, z);
        new SourceDeclarationsPreprocessor(generatorContextCreateGeneratorContext).run(input.getFiles());
        final IrPluginContextImpl irPluginContextImpl = new IrPluginContextImpl(generatorContextCreateGeneratorContext.getModuleDescriptor(), generatorContextCreateGeneratorContext.getBindingContext(), generatorContextCreateGeneratorContext.getLanguageVersionSettings(), symbolTable, generatorContextCreateGeneratorContext.getTypeTranslator(), generatorContextCreateGeneratorContext.getIrBuiltIns(), jvmIrLinker, irMessageLogger, null, 256, null);
        List<IrGenerationExtension> list = instances;
        if (!list.isEmpty()) {
            for (final IrGenerationExtension irGenerationExtension : instances) {
                if (generatorContextCreateGeneratorContext.getConfiguration().getGenerateBodies() || irGenerationExtension.getShouldAlsoBeAppliedInKaptStubGenerationMode()) {
                    psi2IrTranslator.addPostprocessingStep(new Psi2IrPostprocessingStep() { // from class: org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.psi2ir.Psi2IrPostprocessingStep
                        public final void invoke(IrModuleFragment irModuleFragment) {
                            JvmIrCodegenFactory.convertToIr$lambda$1(declarationStubGeneratorImpl, irGenerationExtension, irPluginContextImpl, irModuleFragment);
                        }
                    });
                }
            }
        }
        if (this.ideCodegenSettings.getDoNotLoadDependencyModuleHeaders()) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else {
            List<ModuleDescriptor> listCollectAllDependencyModulesTransitively = collectAllDependencyModulesTransitively(generatorContextCreateGeneratorContext.getModuleDescriptor());
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listCollectAllDependencyModulesTransitively, 10));
            for (ModuleDescriptor moduleDescriptor : listCollectAllDependencyModulesTransitively) {
                Object capability = moduleDescriptor.getCapability(KlibModuleOrigin.INSTANCE.getCAPABILITY());
                DeserializedKlibModuleOrigin deserializedKlibModuleOrigin = capability instanceof DeserializedKlibModuleOrigin ? (DeserializedKlibModuleOrigin) capability : null;
                arrayList.add(KotlinIrLinker.deserializeIrModuleHeader$default(jvmIrLinker, moduleDescriptor, deserializedKlibModuleOrigin != null ? deserializedKlibModuleOrigin.getLibrary() : null, null, moduleDescriptor.getShortName().asString(), 4, null));
            }
            arrayListEmptyList = arrayList;
        }
        Iterable iterable = arrayListEmptyList;
        if (this.ideCodegenSettings.getShouldStubAndNotLinkUnboundSymbols()) {
            listListOf = CollectionsKt.listOf(declarationStubGeneratorImpl);
        } else {
            listListOf = CollectionsKt.listOf((Object[]) new IrProvider[]{jvmIrLinker, new DeclarationStubGeneratorForNotFoundClasses(declarationStubGeneratorImpl)});
        }
        List<? extends IrProvider> list2 = listListOf;
        if (this.ideCodegenSettings.getShouldReferenceUndiscoveredExpectSymbols()) {
            UndiscoveredExpectUtilsKt.referenceUndiscoveredExpectSymbols(symbolTable, input.getFiles(), input.getBindingContext());
        }
        IrModuleFragment irModuleFragmentGenerateModuleFragment = psi2IrTranslator.generateModuleFragment(generatorContextCreateGeneratorContext, input.getFiles(), list2, list, null, this.evaluatorFragmentInfoForPsi2Ir);
        jvmIrLinker.postProcess(true);
        jvmIrLinker.clear();
        declarationStubGeneratorImpl.setUnboundSymbolGeneration(true);
        List<IrFile> files = irModuleFragmentGenerateModuleFragment.getFiles();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList2, ((IrModuleFragment) it.next()).getFiles());
        }
        files.addAll(arrayList2);
        if (this.ideCodegenSettings.getShouldStubOrphanedExpectSymbols()) {
            OrphanedExpectUtilsKt.stubOrphanedExpectSymbols(irModuleFragmentGenerateModuleFragment, declarationStubGeneratorImpl2);
        }
        if (!input.getConfiguration().getBoolean(JVMConfigurationKeys.DO_NOT_CLEAR_BINDING_CONTEXT)) {
            BindingContext bindingContext = input.getBindingContext();
            CleanableBindingContext cleanableBindingContext = bindingContext instanceof CleanableBindingContext ? (CleanableBindingContext) bindingContext : null;
            if (cleanableBindingContext == null) {
                throw new IllegalStateException(("BindingContext should be cleanable in JVM IR to avoid leaking memory: " + input.getBindingContext()).toString());
            }
            cleanableBindingContext.clear();
        }
        return new JvmIrBackendInput(irModuleFragmentGenerateModuleFragment, symbolTable, this.phaseConfig, list2, this.jvmGeneratorExtensions, JvmBackendExtension.Default.INSTANCE, irPluginContextImpl, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory.convertToIr.4
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convertToIr$lambda$1(DeclarationStubGeneratorImpl stubGenerator, IrGenerationExtension extension, IrPluginContextImpl pluginContext, IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(stubGenerator, "$stubGenerator");
        Intrinsics.checkNotNullParameter(extension, "$extension");
        Intrinsics.checkNotNullParameter(pluginContext, "$pluginContext");
        Intrinsics.checkNotNullParameter(module, "module");
        boolean unboundSymbolGeneration = stubGenerator.getUnboundSymbolGeneration();
        try {
            stubGenerator.setUnboundSymbolGeneration(true);
            extension.generate(module, pluginContext);
        } finally {
            stubGenerator.setUnboundSymbolGeneration(unboundSymbolGeneration);
        }
    }

    private final List<ModuleDescriptor> collectAllDependencyModulesTransitively(ModuleDescriptor moduleDescriptor) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        collectAllDependencyModulesTransitively$collectImpl(linkedHashSet, moduleDescriptor);
        return CollectionsKt.toList(linkedHashSet);
    }

    private static final void collectAllDependencyModulesTransitively$collectImpl(LinkedHashSet<ModuleDescriptor> linkedHashSet, ModuleDescriptor moduleDescriptor) {
        for (ModuleDescriptor moduleDescriptor2 : moduleDescriptor.getAllDependencyModules()) {
            if (linkedHashSet.add(moduleDescriptor2)) {
                collectAllDependencyModulesTransitively$collectImpl(linkedHashSet, moduleDescriptor2);
            }
        }
    }

    @Override // org.jetbrains.kotlin.codegen.CodegenFactory
    public CodegenFactory.BackendInput getModuleChunkBackendInput(CodegenFactory.BackendInput wholeBackendInput, Collection<? extends KtFile> sourceFiles) {
        Intrinsics.checkNotNullParameter(wholeBackendInput, "wholeBackendInput");
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        JvmIrBackendInput jvmIrBackendInput = (JvmIrBackendInput) wholeBackendInput;
        Set set = CollectionsKt.toSet(sourceFiles);
        IrModuleFragment irModuleFragment = jvmIrBackendInput.getIrModuleFragment();
        ModuleDescriptor descriptor = irModuleFragment.getDescriptor();
        IrBuiltIns irBuiltins = irModuleFragment.getIrBuiltins();
        List<IrFile> files = irModuleFragment.getFiles();
        ArrayList arrayList = new ArrayList();
        for (Object obj : files) {
            if (CollectionsKt.contains(set, JvmIrUtilsKt.getKtFile((IrFile) obj))) {
                arrayList.add(obj);
            }
        }
        return JvmIrBackendInput.copy$default(jvmIrBackendInput, new IrModuleFragmentImpl(descriptor, irBuiltins, arrayList), null, null, null, null, null, null, null, 254, null);
    }

    @Override // org.jetbrains.kotlin.codegen.CodegenFactory
    public CodegenFactory.CodegenInput invokeLowerings(GenerationState state, CodegenFactory.BackendInput input) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(input, "input");
        JvmIrBackendInput jvmIrBackendInput = (JvmIrBackendInput) input;
        final IrModuleFragment irModuleFragment = jvmIrBackendInput.getIrModuleFragment();
        SymbolTable symbolTable = jvmIrBackendInput.getSymbolTable();
        PhaseConfig phaseConfig = jvmIrBackendInput.getPhaseConfig();
        List<IrProvider> listComponent4 = jvmIrBackendInput.component4();
        JvmGeneratorExtensions extensions = jvmIrBackendInput.getExtensions();
        JvmBackendExtension backendExtension = jvmIrBackendInput.getBackendExtension();
        IrPluginContext pluginContext = jvmIrBackendInput.getPluginContext();
        Function0<Unit> function0Component8 = jvmIrBackendInput.component8();
        JvmIrSerializerImpl jvmIrSerializerImpl = state.getConfiguration().get(JVMConfigurationKeys.SERIALIZE_IR, JvmSerializeIrMode.NONE) != JvmSerializeIrMode.NONE ? new JvmIrSerializerImpl(state.getConfiguration()) : null;
        NamedCompilerPhase<JvmBackendContext, IrModuleFragment> jvmFragmentLoweringPhases = this.evaluatorFragmentInfoForPsi2Ir != null ? JvmLowerKt.getJvmFragmentLoweringPhases() : JvmLowerKt.getJvmLoweringPhases();
        if (phaseConfig == null) {
            phaseConfig = new PhaseConfig(jvmFragmentLoweringPhases, null, null, null, null, null, null, null, null, null, false, false, false, 8190, null);
        }
        PhaseConfig phaseConfig2 = phaseConfig;
        final JvmBackendContext jvmBackendContext = new JvmBackendContext(state, irModuleFragment.getIrBuiltins(), symbolTable, phaseConfig2, extensions, backendExtension, jvmIrSerializerImpl, new JvmIrDeserializerImpl(), listComponent4, pluginContext);
        if (this.evaluatorFragmentInfoForPsi2Ir != null) {
            jvmBackendContext.setLocalDeclarationsLoweringData(new LinkedHashMap());
        }
        List<IrGenerationExtension> instances = IrGenerationExtension.INSTANCE.getInstances(state.getProject());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = instances.iterator();
        while (it.hasNext()) {
            IrIntrinsicExtension platformIntrinsicExtension = ((IrGenerationExtension) it.next()).getPlatformIntrinsicExtension(jvmBackendContext);
            JvmIrIntrinsicExtension jvmIrIntrinsicExtension = platformIntrinsicExtension instanceof JvmIrIntrinsicExtension ? (JvmIrIntrinsicExtension) platformIntrinsicExtension : null;
            if (jvmIrIntrinsicExtension != null) {
                arrayList.add(jvmIrIntrinsicExtension);
            }
        }
        final ArrayList arrayList2 = arrayList;
        final Lazy lazy = LazyKt.lazy(new Function0<IrIntrinsicMethods>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory$invokeLowerings$intrinsics$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrIntrinsicMethods invoke() {
                return new IrIntrinsicMethods(irModuleFragment.getIrBuiltins(), jvmBackendContext.getIr().getSymbols());
            }
        });
        jvmBackendContext.setGetIntrinsic(new Function1<IrFunctionSymbol, IntrinsicMarker>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory.invokeLowerings.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IntrinsicMarker invoke(IrFunctionSymbol symbol) {
                IntrinsicMethod intrinsic;
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                IntrinsicMethod intrinsic2 = JvmIrCodegenFactory.invokeLowerings$lambda$7(lazy).getIntrinsic(symbol);
                if (intrinsic2 != null) {
                    return intrinsic2;
                }
                Iterator<T> it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    intrinsic = ((JvmIrIntrinsicExtension) it2.next()).getIntrinsic(symbol);
                    if (intrinsic != null) {
                        return intrinsic;
                    }
                }
                intrinsic = null;
                return intrinsic;
            }
        });
        jvmBackendContext.setEnumEntriesIntrinsicMappingsCache(new EnumEntriesIntrinsicMappingsCacheImpl(jvmBackendContext));
        new ExternalDependenciesGenerator(symbolTable, listComponent4).generateUnboundSymbolsAsDependencies();
        ClassFileFactory factory = jvmBackendContext.getState().getFactory();
        List<IrFile> files = irModuleFragment.getFiles();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(files, 10));
        Iterator<T> it2 = files.iterator();
        while (it2.hasNext()) {
            arrayList3.add(JvmIrUtilsKt.getIoFile((IrFile) it2.next()));
        }
        factory.registerSourceFiles(arrayList3);
        CompilerPhaseKt.invokeToplevel(jvmFragmentLoweringPhases, phaseConfig2, jvmBackendContext, irModuleFragment);
        return new JvmIrCodegenInput(state, jvmBackendContext, irModuleFragment, function0Component8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrIntrinsicMethods invokeLowerings$lambda$7(Lazy<IrIntrinsicMethods> lazy) {
        return lazy.getValue();
    }

    @Override // org.jetbrains.kotlin.codegen.CodegenFactory
    public void invokeCodegen(CodegenFactory.CodegenInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        JvmIrCodegenInput jvmIrCodegenInput = (JvmIrCodegenInput) input;
        GenerationState state = jvmIrCodegenInput.getState();
        JvmBackendContext context = jvmIrCodegenInput.getContext();
        IrModuleFragment module = jvmIrCodegenInput.getModule();
        Function0<Unit> function0Component4 = jvmIrCodegenInput.component4();
        if (invokeCodegen$hasErrors(state)) {
            return;
        }
        function0Component4.invoke();
        CompilerPhaseKt.invokeToplevel(JvmPhasesKt.getJvmCodegenPhases(), new PhaseConfig(JvmPhasesKt.getJvmCodegenPhases(), null, null, null, null, null, null, null, null, null, false, false, false, 8190, null), context, module);
        if (invokeCodegen$hasErrors(state)) {
            return;
        }
        state.afterIndependentPart();
    }

    private static final boolean invokeCodegen$hasErrors(GenerationState generationState) {
        DiagnosticReporter diagnosticReporter = generationState.getDiagnosticReporter();
        BaseDiagnosticsCollector baseDiagnosticsCollector = diagnosticReporter instanceof BaseDiagnosticsCollector ? (BaseDiagnosticsCollector) diagnosticReporter : null;
        return baseDiagnosticsCollector != null && baseDiagnosticsCollector.getHasErrors();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void generateModuleInFrontendIRMode$default(JvmIrCodegenFactory jvmIrCodegenFactory, GenerationState generationState, IrModuleFragment irModuleFragment, SymbolTable symbolTable, List list, JvmGeneratorExtensions jvmGeneratorExtensions, JvmBackendExtension jvmBackendExtension, IrPluginContext irPluginContext, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: generateModuleInFrontendIRMode");
        }
        jvmIrCodegenFactory.generateModuleInFrontendIRMode(generationState, irModuleFragment, symbolTable, list, jvmGeneratorExtensions, jvmBackendExtension, irPluginContext, (i & 128) != 0 ? new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory.generateModuleInFrontendIRMode.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }
        } : function0);
    }

    public final void generateModuleInFrontendIRMode(GenerationState state, IrModuleFragment irModuleFragment, SymbolTable symbolTable, List<? extends IrProvider> irProviders, JvmGeneratorExtensions extensions, JvmBackendExtension backendExtension, IrPluginContext irPluginContext, Function0<Unit> notifyCodegenStart) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irProviders, "irProviders");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        Intrinsics.checkNotNullParameter(backendExtension, "backendExtension");
        Intrinsics.checkNotNullParameter(irPluginContext, "irPluginContext");
        Intrinsics.checkNotNullParameter(notifyCodegenStart, "notifyCodegenStart");
        generateModule(state, new JvmIrBackendInput(irModuleFragment, symbolTable, this.phaseConfig, irProviders, extensions, backendExtension, irPluginContext, notifyCodegenStart));
    }
}
