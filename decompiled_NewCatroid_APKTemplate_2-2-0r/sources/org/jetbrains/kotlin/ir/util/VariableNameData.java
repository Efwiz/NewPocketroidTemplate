package org.jetbrains.kotlin.ir.util;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;

/* JADX INFO: compiled from: RenderIrElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/VariableNameData;", "", "normalizeNames", "", "(Z)V", "nameMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "", "getNameMap", "()Ljava/util/Map;", "getNormalizeNames", "()Z", "temporaryIndex", "", "getTemporaryIndex", "()I", "setTemporaryIndex", "(I)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class VariableNameData {
    private final Map<IrVariableSymbol, String> nameMap = new LinkedHashMap();
    private final boolean normalizeNames;
    private int temporaryIndex;

    public VariableNameData(boolean z) {
        this.normalizeNames = z;
    }

    public final boolean getNormalizeNames() {
        return this.normalizeNames;
    }

    public final Map<IrVariableSymbol, String> getNameMap() {
        return this.nameMap;
    }

    public final int getTemporaryIndex() {
        return this.temporaryIndex;
    }

    public final void setTemporaryIndex(int i) {
        this.temporaryIndex = i;
    }
}
