package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

/* JADX INFO: compiled from: ConstantData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataStruct;", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ConstantDataElement;", "name", "", PathTree.NAME_elements, "", "(Ljava/lang/String;Ljava/util/List;)V", "getElements", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "sizeInBytes", "", "getSizeInBytes", "()I", ArchiveStreamFactory.DUMP, OutputKeys.INDENT, "startAddress", "toBytes", "", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantDataStruct extends ConstantDataElement {
    private final List<ConstantDataElement> elements;
    private final String name;
    private final int sizeInBytes;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ConstantDataStruct(String name2, List<? extends ConstantDataElement> elements) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(elements, "elements");
        this.name = name2;
        this.elements = elements;
        List<? extends ConstantDataElement> list = elements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((ConstantDataElement) it.next()).getSizeInBytes()));
        }
        this.sizeInBytes = CollectionsKt.sumOfInt(arrayList);
    }

    public final List<ConstantDataElement> getElements() {
        return this.elements;
    }

    public final String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public byte[] toBytes() {
        byte[] bArrPlus = new byte[0];
        Iterator<T> it = this.elements.iterator();
        while (it.hasNext()) {
            bArrPlus = ArraysKt.plus(bArrPlus, ((ConstantDataElement) it.next()).toBytes());
        }
        return bArrPlus;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public String dump(String indent, int startAddress) {
        Intrinsics.checkNotNullParameter(indent, "indent");
        String str = indent + ";; " + this.name + '\n';
        for (ConstantDataElement constantDataElement : this.elements) {
            str = str + constantDataElement.dump(indent + "  ", startAddress);
            startAddress += constantDataElement.getSizeInBytes();
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.backend.wasm.ir2wasm.ConstantDataElement
    public int getSizeInBytes() {
        return this.sizeInBytes;
    }
}
