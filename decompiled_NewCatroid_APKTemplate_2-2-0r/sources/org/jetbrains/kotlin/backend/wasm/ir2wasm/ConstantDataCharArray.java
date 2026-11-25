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
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007¢\u0006\u0002\u0010\nJ\u0018\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u000eH\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataCharArray;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataElement;", "name", "", "value", "", "(Ljava/lang/String;[C)V", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "", "(Ljava/lang/String;Ljava/util/List;)V", "getName", "()Ljava/lang/String;", "sizeInBytes", "", "getSizeInBytes", "()I", "getValue", "()Ljava/util/List;", ArchiveStreamFactory.DUMP, OutputKeys.INDENT, "startAddress", "toBytes", "", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantDataCharArray extends ConstantDataElement {
    private final String name;
    private final int sizeInBytes;
    private final List<WasmSymbol<Character>> value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConstantDataCharArray(String name2, List<WasmSymbol<Character>> value) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.name = name2;
        this.value = value;
        this.sizeInBytes = value.size() * 2;
    }

    public final String getName() {
        return this.name;
    }

    public final List<WasmSymbol<Character>> getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public byte[] toBytes() {
        List<WasmSymbol<Character>> list = this.value;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(ConstantDataKt.toLittleEndianBytes(((Character) ((WasmSymbol) it.next()).getOwner()).charValue()));
        }
        byte[] bArrPlus = new byte[0];
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            bArrPlus = ArraysKt.plus(bArrPlus, (byte[]) it2.next());
        }
        return bArrPlus;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public String dump(String indent, int startAddress) {
        Intrinsics.checkNotNullParameter(indent, "indent");
        if (this.value.isEmpty()) {
            return "";
        }
        StringBuilder sbAppend = new StringBuilder().append(ConstantDataKt.addressToString(startAddress)).append(": ").append(indent).append(" i16[] : ");
        List<WasmSymbol<Character>> list = this.value;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Character.valueOf(((Character) ((WasmSymbol) it.next()).getOwner()).charValue()));
        }
        String string = Arrays.toString(CollectionsKt.toCharArray(arrayList));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return sbAppend.append(string).append("   ;; ").append(this.name).append('\n').toString();
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public int getSizeInBytes() {
        return this.sizeInBytes;
    }

    public ConstantDataCharArray(String name2, char[] value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        ArrayList arrayList = new ArrayList(value.length);
        for (char c : value) {
            arrayList.add(new WasmSymbol(Character.valueOf(c)));
        }
        this(name2, arrayList);
    }
}
