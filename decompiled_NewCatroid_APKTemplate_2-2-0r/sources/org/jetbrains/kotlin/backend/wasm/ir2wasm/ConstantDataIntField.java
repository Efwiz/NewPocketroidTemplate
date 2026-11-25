package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.wasm.ir.WasmSymbol;

/* JADX INFO: compiled from: ConstantData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataIntField;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataElement;", "name", "", "value", "", "(Ljava/lang/String;I)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getName", "()Ljava/lang/String;", "sizeInBytes", "getSizeInBytes", "()I", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", ArchiveStreamFactory.DUMP, OutputKeys.INDENT, "startAddress", "toBytes", "", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantDataIntField extends ConstantDataElement {
    private final String name;
    private final int sizeInBytes;
    private final WasmSymbol<Integer> value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConstantDataIntField(String name2, WasmSymbol<Integer> value) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.name = name2;
        this.value = value;
        this.sizeInBytes = 4;
    }

    public final String getName() {
        return this.name;
    }

    public final WasmSymbol<Integer> getValue() {
        return this.value;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ConstantDataIntField(String name2, int i) {
        this(name2, (WasmSymbol<Integer>) new WasmSymbol(Integer.valueOf(i)));
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public byte[] toBytes() {
        return ConstantDataKt.toLittleEndianBytes(this.value.getOwner().intValue());
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public String dump(String indent, int startAddress) {
        Intrinsics.checkNotNullParameter(indent, "indent");
        return ConstantDataKt.addressToString(startAddress) + ": " + indent + " i32   : " + this.value.getOwner().intValue() + "    ;; " + this.name + '\n';
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public int getSizeInBytes() {
        return this.sizeInBytes;
    }
}
