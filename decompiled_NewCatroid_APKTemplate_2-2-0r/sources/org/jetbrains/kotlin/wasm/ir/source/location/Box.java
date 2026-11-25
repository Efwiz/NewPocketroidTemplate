package org.jetbrains.kotlin.wasm.ir.source.location;

import kotlin.Metadata;

/* JADX INFO: compiled from: Box.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/source/location/Box;", "", "value", "", "(I)V", "getValue", "()I", "setValue", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Box {
    private int value;

    public Box(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }

    public final void setValue(int i) {
        this.value = i;
    }
}
