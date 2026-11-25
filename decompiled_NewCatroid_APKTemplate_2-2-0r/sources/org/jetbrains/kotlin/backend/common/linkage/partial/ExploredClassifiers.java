package org.jetbrains.kotlin.backend.common.linkage.partial;

import java.util.HashMap;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ExploredClassifiers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u0005H\u0086\u0002J\u0016\u0010\u000e\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0006J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u00050\tj\b\u0012\u0004\u0012\u00020\u0005`\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/ExploredClassifiers;", "", "()V", "unusableSymbols", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "Lkotlin/collections/HashMap;", "usableSymbols", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "get", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier;", NativeSymbol.TYPE_NAME, "registerUnusable", "exploredClassifier", "registerUsable", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Usable;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExploredClassifiers {
    private final HashSet<IrClassifierSymbol> usableSymbols = new HashSet<>();
    private final HashMap<IrClassifierSymbol, ExploredClassifier.Unusable> unusableSymbols = new HashMap<>();

    public final ExploredClassifier get(IrClassifierSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.usableSymbols.contains(symbol) ? ExploredClassifier.Usable.INSTANCE : this.unusableSymbols.get(symbol);
    }

    public final ExploredClassifier.Unusable registerUnusable(IrClassifierSymbol symbol, ExploredClassifier.Unusable exploredClassifier) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(exploredClassifier, "exploredClassifier");
        this.unusableSymbols.put(symbol, exploredClassifier);
        return exploredClassifier;
    }

    public final ExploredClassifier.Usable registerUsable(IrClassifierSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.usableSymbols.add(symbol);
        return ExploredClassifier.Usable.INSTANCE;
    }
}
