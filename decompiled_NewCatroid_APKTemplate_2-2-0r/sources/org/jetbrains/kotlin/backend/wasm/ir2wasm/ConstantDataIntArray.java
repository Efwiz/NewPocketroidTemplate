package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.wasm.ir.WasmSymbol;

/* JADX INFO: compiled from: ConstantData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\u0002\u0010\bJ\u0018\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0007H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataIntArray;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataElement;", "name", "", "value", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "", "(Ljava/lang/String;Ljava/util/List;)V", "getName", "()Ljava/lang/String;", "sizeInBytes", "getSizeInBytes", "()I", "getValue", "()Ljava/util/List;", ArchiveStreamFactory.DUMP, OutputKeys.INDENT, "startAddress", "toBytes", "", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantDataIntArray extends ConstantDataElement {
    private final String name;
    private final int sizeInBytes;
    private final List<WasmSymbol<Integer>> value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConstantDataIntArray(String name2, List<WasmSymbol<Integer>> value) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.name = name2;
        this.value = value;
        this.sizeInBytes = value.size() * 4;
    }

    public final String getName() {
        return this.name;
    }

    public final List<WasmSymbol<Integer>> getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public byte[] toBytes() {
        byte[] bArrPlus = new byte[0];
        Iterator<T> it = this.value.iterator();
        while (it.hasNext()) {
            bArrPlus = ArraysKt.plus(bArrPlus, ConstantDataKt.toLittleEndianBytes(((Number) ((WasmSymbol) it.next()).getOwner()).intValue()));
        }
        return bArrPlus;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public String dump(String indent, int startAddress) {
        Intrinsics.checkNotNullParameter(indent, "indent");
        if (this.value.isEmpty()) {
            return "";
        }
        StringBuilder sbAppend = new StringBuilder().append(ConstantDataKt.addressToString(startAddress)).append(": ").append(indent).append(" i32[] : ");
        List<WasmSymbol<Integer>> list = this.value;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((Number) ((WasmSymbol) it.next()).getOwner()).intValue()));
        }
        String string = Arrays.toString(CollectionsKt.toIntArray(arrayList));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return sbAppend.append(string).append("   ;; ").append(this.name).append('\n').toString();
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public int getSizeInBytes() {
        return this.sizeInBytes;
    }
}
