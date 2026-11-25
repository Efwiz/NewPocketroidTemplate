package org.jetbrains.kotlin.backend.common.phaser;

import com.google.android.gms.cast.MediaTrack;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.DeepCopySavingMetadataKt;

/* JADX INFO: compiled from: performByIrFile.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H\u0002\u001aj\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\n`\u000b\"\b\b\u0000\u0010\t*\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u001e\u0010\u0011\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u00130\u0012\u001aF\u0010\u0015\u001a\u00020\u0014*\u00020\u00142\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u00052\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00052\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00190\u0005¨\u0006\u001a"}, d2 = {"adjustDefaultArgumentStubs", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "remappedFunctions", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "performByIrFile", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Context", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "name", "", MediaTrack.ROLE_DESCRIPTION, "copyBeforeLowering", "", "lower", "", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "copySavingMappings", "remappedFiles", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "remappedClasses", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PerformByIrFileKt {
    public static /* synthetic */ NamedCompilerPhase performByIrFile$default(String str, String str2, boolean z, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "PerformByIrFile";
        }
        if ((i & 2) != 0) {
            str2 = "Perform phases by IrFile";
        }
        if ((i & 4) != 0) {
            z = true;
        }
        return performByIrFile(str, str2, z, list);
    }

    public static final <Context extends CommonBackendContext> NamedCompilerPhase<Context, IrModuleFragment> performByIrFile(String name2, String description, boolean z, List<? extends CompilerPhase<? super Context, IrFile, IrFile>> lower) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(lower, "lower");
        return new NamedCompilerPhase<>(name2, description, SetsKt.emptySet(), new PerformByIrFilePhase(lower, z), SetsKt.emptySet(), SetsKt.emptySet(), SetsKt.emptySet(), SetsKt.setOf(DumperVerifierKt.getDefaultDumper()), 1);
    }

    public static final IrFile copySavingMappings(IrFile irFile, Map<IrFileSymbol, IrFileSymbol> remappedFiles, Map<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> remappedFunctions, Map<IrClassSymbol, IrClassSymbol> remappedClasses) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        Intrinsics.checkNotNullParameter(remappedFiles, "remappedFiles");
        Intrinsics.checkNotNullParameter(remappedFunctions, "remappedFunctions");
        Intrinsics.checkNotNullParameter(remappedClasses, "remappedClasses");
        DeepCopySymbolRemapperSavingFunctions deepCopySymbolRemapperSavingFunctions = new DeepCopySymbolRemapperSavingFunctions();
        IrFile irFile2 = (IrFile) DeepCopySavingMetadataKt.deepCopySavingMetadata$default(irFile, null, deepCopySymbolRemapperSavingFunctions, 1, null);
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : deepCopySymbolRemapperSavingFunctions.getDeclaredFunctions()) {
            remappedFunctions.put(irSimpleFunctionSymbol, deepCopySymbolRemapperSavingFunctions.getReferencedSimpleFunction(irSimpleFunctionSymbol));
        }
        for (IrClassSymbol irClassSymbol : deepCopySymbolRemapperSavingFunctions.getDeclaredClasses()) {
            remappedClasses.put(irClassSymbol, deepCopySymbolRemapperSavingFunctions.getReferencedClass(irClassSymbol));
        }
        remappedFiles.put(irFile.getSymbol(), irFile2.getSymbol());
        return irFile2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void adjustDefaultArgumentStubs(CommonBackendContext commonBackendContext, Map<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> map) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        IrSimpleFunction owner;
        IrFunction irFunction;
        for (K k : commonBackendContext.getMapping().getDefaultArgumentsOriginalFunction().getKeys()) {
            if (k instanceof IrSimpleFunction) {
                IrFunction irFunction2 = commonBackendContext.getMapping().getDefaultArgumentsOriginalFunction().get(k);
                IrSimpleFunction irSimpleFunction = irFunction2 instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction2 : null;
                if (irSimpleFunction != null && (irSimpleFunctionSymbol = map.get(irSimpleFunction.getSymbol())) != null && (owner = irSimpleFunctionSymbol.getOwner()) != null && (irFunction = commonBackendContext.getMapping().getDefaultArgumentsDispatchFunction().get(owner)) != null) {
                    IrSimpleFunctionSymbol symbol = ((IrSimpleFunction) k).getSymbol();
                    IrFunctionSymbol symbol2 = irFunction.getSymbol();
                    Intrinsics.checkNotNull(symbol2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                    map.put(symbol, (IrSimpleFunctionSymbol) symbol2);
                }
            }
        }
    }
}
