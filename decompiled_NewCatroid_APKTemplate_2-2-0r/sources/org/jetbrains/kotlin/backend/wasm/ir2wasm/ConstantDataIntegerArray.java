package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import java.util.Arrays;
import java.util.List;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

/* JADX INFO: compiled from: ConstantData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0018\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\bH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataIntegerArray;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataElement;", "name", "", "value", "", "", "integerSize", "", "(Ljava/lang/String;Ljava/util/List;I)V", "getName", "()Ljava/lang/String;", "sizeInBytes", "getSizeInBytes", "()I", "getValue", "()Ljava/util/List;", ArchiveStreamFactory.DUMP, OutputKeys.INDENT, "startAddress", "toBytes", "", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantDataIntegerArray extends ConstantDataElement {
    private final int integerSize;
    private final String name;
    private final int sizeInBytes;
    private final List<Long> value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConstantDataIntegerArray(String name2, List<Long> value, int i) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.name = name2;
        this.value = value;
        this.integerSize = i;
        this.sizeInBytes = value.size() * i;
    }

    public final String getName() {
        return this.name;
    }

    public final List<Long> getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public byte[] toBytes() {
        byte[] bArr = new byte[this.value.size() * this.integerSize];
        int size = this.value.size();
        for (int i = 0; i < size; i++) {
            long jLongValue = this.value.get(i).longValue();
            int i2 = this.integerSize;
            ConstantDataKt.toLittleEndianBytesTo(jLongValue, bArr, i * i2, i2);
        }
        return bArr;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public String dump(String indent, int startAddress) {
        Intrinsics.checkNotNullParameter(indent, "indent");
        if (this.value.isEmpty()) {
            return "";
        }
        StringBuilder sbAppend = new StringBuilder().append(ConstantDataKt.addressToString(startAddress)).append(": ").append(indent).append(" i").append(this.integerSize * 8).append("[] : ");
        String string = Arrays.toString(toBytes());
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return sbAppend.append(string).append("   ;; ").append(this.name).append('\n').toString();
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public int getSizeInBytes() {
        return this.sizeInBytes;
    }
}
