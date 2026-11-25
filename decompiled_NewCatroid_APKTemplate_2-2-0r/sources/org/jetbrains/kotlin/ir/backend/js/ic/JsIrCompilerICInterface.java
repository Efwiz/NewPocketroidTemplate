package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: CacheUpdater.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001JF\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00030\u00040\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00072\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterface;", "", "compile", "", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "allModules", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "dirtyFiles", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mainArguments", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JsIrCompilerICInterface {
    List<Function0<List<JsIrProgramFragment>>> compile(Collection<? extends IrModuleFragment> allModules, Collection<? extends IrFile> dirtyFiles, List<String> mainArguments);
}
