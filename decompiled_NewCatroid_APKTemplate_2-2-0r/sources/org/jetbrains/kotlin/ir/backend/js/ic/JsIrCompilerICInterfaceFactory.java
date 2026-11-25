package org.jetbrains.kotlin.ir.backend.js.ic;

import kotlin.Metadata;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: CacheUpdater.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterfaceFactory;", "", "createCompilerForIC", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrCompilerICInterface;", "mainModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JsIrCompilerICInterfaceFactory {
    JsIrCompilerICInterface createCompilerForIC(IrModuleFragment mainModule, CompilerConfiguration configuration);
}
