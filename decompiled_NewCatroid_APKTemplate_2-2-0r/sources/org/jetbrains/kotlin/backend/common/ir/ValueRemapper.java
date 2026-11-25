package org.jetbrains.kotlin.backend.common.ir;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;

/* JADX INFO: compiled from: ValueRemapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0014R \u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/ValueRemapper;", "Lorg/jetbrains/kotlin/backend/common/ir/AbstractValueRemapper;", "map", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "(Ljava/util/Map;)V", "getMap", "()Ljava/util/Map;", "remapValue", "oldValue", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ValueRemapper extends AbstractValueRemapper {
    private final Map<IrValueSymbol, IrValueSymbol> map;

    /* JADX WARN: Multi-variable type inference failed */
    public ValueRemapper(Map<IrValueSymbol, ? extends IrValueSymbol> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.map = map;
    }

    protected Map<IrValueSymbol, IrValueSymbol> getMap() {
        return this.map;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.AbstractValueRemapper
    protected IrValueSymbol remapValue(IrValueSymbol oldValue) {
        Intrinsics.checkNotNullParameter(oldValue, "oldValue");
        return getMap().get(oldValue);
    }
}
