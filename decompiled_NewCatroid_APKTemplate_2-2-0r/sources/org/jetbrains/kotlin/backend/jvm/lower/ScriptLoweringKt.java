package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrScriptImplKt;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ScriptLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\f\u001a\u00020\r*\u00020\u000e2\u0019\b\u0002\u0010\u000f\u001a\u0013\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010¢\u0006\u0002\b\u0013H\u0082\b\u001a$\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u001c"}, d2 = {"scriptsToClassesPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getScriptsToClassesPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "needsReceiverProcessing", "", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "getNeedsReceiverProcessing", "(Lorg/jetbrains/kotlin/ir/declarations/IrScript;)Z", "addAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "builder", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrFunctionBuilder;", "", "Lkotlin/ExtensionFunctionType;", "createThisReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "context", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> scriptsToClassesPhase = PhaseBuildersKt.makeCustomPhase(new Function2<JvmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ScriptLoweringKt$scriptsToClassesPhase$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(JvmBackendContext jvmBackendContext, IrModuleFragment irModuleFragment) {
            invoke2(jvmBackendContext, irModuleFragment);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(JvmBackendContext context, IrModuleFragment input) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(input, "input");
            new ScriptsToClassesLowering(context, context.getInnerClassesSupport()).lower(input);
        }
    }, "ScriptsToClasses", "Put script declarations into classes", (504 & 8) != 0 ? SetsKt.emptySet() : null, (504 & 16) != 0 ? SetsKt.emptySet() : null, (504 & 32) != 0 ? SetsKt.emptySet() : null, (504 & 64) != 0 ? SetsKt.emptySet() : null, (504 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null, (504 & 256) != 0 ? 1 : 0);

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getScriptsToClassesPhase() {
        return scriptsToClassesPhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getNeedsReceiverProcessing(IrScript irScript) {
        if (Intrinsics.areEqual(irScript.getOrigin(), IrScriptImplKt.getSCRIPT_K2_ORIGIN())) {
            return true;
        }
        List<IrScriptSymbol> importedScripts = irScript.getImportedScripts();
        if (importedScripts != null && (!importedScripts.isEmpty())) {
            return true;
        }
        List<IrScriptSymbol> earlierScripts = irScript.getEarlierScripts();
        return (earlierScripts != null && (earlierScripts.isEmpty() ^ true)) || !irScript.getImplicitReceiversParameters().isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrValueParameter createThisReceiverParameter(IrDeclarationParent irDeclarationParent, JvmBackendContext jvmBackendContext, IrDeclarationOrigin irDeclarationOrigin, IrType irType) {
        IrValueParameter irValueParameterCreateValueParameter = jvmBackendContext.getSymbolTable().getIrFactory().createValueParameter(irDeclarationParent.getStartOffset(), irDeclarationParent.getEndOffset(), irDeclarationOrigin, SpecialNames.THIS, irType, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, null), -1, (IrType) null, false, false, false);
        irValueParameterCreateValueParameter.setParent(irDeclarationParent);
        return irValueParameterCreateValueParameter;
    }
}
