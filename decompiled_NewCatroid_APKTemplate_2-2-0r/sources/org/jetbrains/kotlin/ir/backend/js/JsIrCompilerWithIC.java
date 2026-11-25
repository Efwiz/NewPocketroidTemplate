package org.jetbrains.kotlin.ir.backend.js;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.ic.JsIrCompilerICInterface;
import org.jetbrains.kotlin.ir.backend.js.lower.CollectNativeImplementationsKt;
import org.jetbrains.kotlin.ir.backend.js.lower.MoveBodilessDeclarationsToSeparatePlaceKt;
import org.jetbrains.kotlin.ir.backend.js.lower.TestGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformer;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsGenerationGranularity;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.StageController;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;

/* JADX INFO: compiled from: compilerWithIC.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJF\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00110\u00120\u00112\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00152\u000e\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u0011H\u0016R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsIrCompilerWithIC;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterface;", "mainModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "exportedDeclarations", "", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;Ljava/util/Set;)V", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "compile", "", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "allModules", "", "dirtyFiles", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mainArguments", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrCompilerWithIC implements JsIrCompilerICInterface {
    private final JsIrBackendContext context;
    private final IrModuleFragment mainModule;
    private final PhaseConfig phaseConfig;

    public JsIrCompilerWithIC(IrModuleFragment mainModule, CompilerConfiguration configuration, JsGenerationGranularity granularity, PhaseConfig phaseConfig, Set<FqName> exportedDeclarations) {
        Intrinsics.checkNotNullParameter(mainModule, "mainModule");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(granularity, "granularity");
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(exportedDeclarations, "exportedDeclarations");
        this.mainModule = mainModule;
        this.phaseConfig = phaseConfig;
        IrBuiltIns irBuiltins = mainModule.getIrBuiltins();
        Intrinsics.checkNotNull(irBuiltins, "null cannot be cast to non-null type org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors");
        this.context = new JsIrBackendContext(mainModule.getDescriptor(), irBuiltins, ((IrBuiltInsOverDescriptors) irBuiltins).getSymbolTable(), exportedDeclarations, SetsKt.emptySet(), configuration, null, false, null, null, granularity, true, XKeySym.XK_Amacron, null);
    }

    public /* synthetic */ JsIrCompilerWithIC(IrModuleFragment irModuleFragment, CompilerConfiguration compilerConfiguration, JsGenerationGranularity jsGenerationGranularity, PhaseConfig phaseConfig, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irModuleFragment, compilerConfiguration, jsGenerationGranularity, phaseConfig, (i & 16) != 0 ? SetsKt.emptySet() : set);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsIrCompilerICInterface
    public List<Function0<List<JsIrProgramFragment>>> compile(Collection<? extends IrModuleFragment> allModules, Collection<? extends IrFile> dirtyFiles, List<String> mainArguments) {
        Intrinsics.checkNotNullParameter(allModules, "allModules");
        Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
        boolean z = this.context.getConfiguration().getBoolean(JSConfigurationKeys.GENERATE_POLYFILLS);
        Collection<? extends IrModuleFragment> collection = allModules;
        for (IrModuleFragment irModuleFragment : collection) {
            if (z) {
                CollectNativeImplementationsKt.collectNativeImplementations(this.context, irModuleFragment);
            }
            MoveBodilessDeclarationsToSeparatePlaceKt.moveBodilessDeclarationsToSeparatePlace(this.context, irModuleFragment);
        }
        TestGeneratorKt.generateJsTests(this.context, this.mainModule);
        JsIrBackendContext jsIrBackendContext = this.context;
        PhaseConfig phaseConfig = this.phaseConfig;
        StageController stageController = jsIrBackendContext.getIrFactory().getStageController();
        Intrinsics.checkNotNull(stageController, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.backend.js.WholeWorldStageController");
        CompilerWithICKt.lowerPreservingTags(collection, jsIrBackendContext, phaseConfig, (WholeWorldStageController) stageController);
        return new IrModuleToJsTransformer(this.context, mainArguments, null, false, 12, null).makeIrFragmentsGenerators(dirtyFiles, allModules);
    }
}
