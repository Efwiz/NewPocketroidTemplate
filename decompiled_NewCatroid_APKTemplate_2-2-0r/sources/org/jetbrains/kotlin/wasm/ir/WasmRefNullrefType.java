package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import org.billthefarmer.mididriver.MidiConstants;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmRefNullrefType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmRefNullrefType extends WasmType {
    public static final WasmRefNullrefType INSTANCE = new WasmRefNullrefType();

    private WasmRefNullrefType() {
        super("nullref", MidiConstants.MIDI_TIME_CODE, null);
    }
}
