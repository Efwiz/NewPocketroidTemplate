package org.jetbrains.kotlin.ir.backend.js;

import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;

/* JADX INFO: compiled from: PropertyLazyInitialization.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001f\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00030\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/PropertyLazyInitialization;", "", CompilerOptions.ENABLED, "", "eagerInitialization", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(ZLorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getEagerInitialization", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getEnabled", "()Z", "fileToInitializationFuns", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFileToInitializationFuns", "()Ljava/util/Map;", "fileToInitializerPureness", "getFileToInitializerPureness", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyLazyInitialization {
    private final IrClassSymbol eagerInitialization;
    private final boolean enabled;
    private final Map<IrFile, IrSimpleFunction> fileToInitializationFuns;
    private final Map<IrFile, Boolean> fileToInitializerPureness;

    public PropertyLazyInitialization(boolean z, IrClassSymbol eagerInitialization) {
        Intrinsics.checkNotNullParameter(eagerInitialization, "eagerInitialization");
        this.enabled = z;
        this.fileToInitializationFuns = new HashMap();
        this.fileToInitializerPureness = new HashMap();
        this.eagerInitialization = eagerInitialization;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final Map<IrFile, IrSimpleFunction> getFileToInitializationFuns() {
        return this.fileToInitializationFuns;
    }

    public final Map<IrFile, Boolean> getFileToInitializerPureness() {
        return this.fileToInitializerPureness;
    }

    public final IrClassSymbol getEagerInitialization() {
        return this.eagerInitialization;
    }
}
