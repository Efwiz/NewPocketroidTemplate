package org.jetbrains.kotlin.library.impl;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.encodings.WobblyTF8;

/* JADX INFO: compiled from: IrFileWriters.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrMemoryStringWriter;", "Lorg/jetbrains/kotlin/library/impl/IrMemoryWriter;", "data", "", "", "(Ljava/util/List;)V", "writeData", "", "dataOutput", "Ljava/io/DataOutput;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrMemoryStringWriter extends IrMemoryWriter {
    private final List<String> data;

    public IrMemoryStringWriter(List<String> data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    @Override // org.jetbrains.kotlin.library.impl.IrMemoryWriter
    protected void writeData(DataOutput dataOutput) throws IOException {
        Intrinsics.checkNotNullParameter(dataOutput, "dataOutput");
        dataOutput.writeInt(this.data.size());
        List<String> list = this.data;
        WobblyTF8 wobblyTF8 = WobblyTF8.INSTANCE;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(wobblyTF8.encode((String) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            dataOutput.writeInt(((byte[]) it2.next()).length);
        }
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            dataOutput.write((byte[]) it3.next());
        }
    }
}
