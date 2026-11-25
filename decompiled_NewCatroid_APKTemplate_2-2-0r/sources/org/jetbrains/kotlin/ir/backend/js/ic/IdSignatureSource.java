package org.jetbrains.kotlin.ir.backend.js.ic;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IdSignatureSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0017\u0010\f\u001a\u00020\r8Fø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSource;", "", "lib", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "srcIrFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getLib-dAnkW_k", "()Ljava/lang/String;", "Ljava/lang/String;", "src", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "getSrc-zDqpWdY", "getSrcIrFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdSignatureSource {
    private final String lib;
    private final IrFile srcIrFile;
    private final IrSymbol symbol;

    public /* synthetic */ IdSignatureSource(String str, IrFile irFile, IrSymbol irSymbol, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, irFile, irSymbol);
    }

    private IdSignatureSource(String lib, IrFile srcIrFile, IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(lib, "lib");
        Intrinsics.checkNotNullParameter(srcIrFile, "srcIrFile");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.lib = lib;
        this.srcIrFile = srcIrFile;
        this.symbol = symbol;
    }

    /* JADX INFO: renamed from: getLib-dAnkW_k, reason: not valid java name and from getter */
    public final String getLib() {
        return this.lib;
    }

    public final IrFile getSrcIrFile() {
        return this.srcIrFile;
    }

    public final IrSymbol getSymbol() {
        return this.symbol;
    }

    /* JADX INFO: renamed from: getSrc-zDqpWdY, reason: not valid java name */
    public final String m7133getSrczDqpWdY() {
        return KotlinSourceFile.m7165constructorimpl(this.srcIrFile);
    }
}
