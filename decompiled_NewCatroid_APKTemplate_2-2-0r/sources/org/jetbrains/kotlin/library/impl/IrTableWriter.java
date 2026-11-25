package org.jetbrains.kotlin.library.impl;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrFileWriters.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0003¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014R \u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrTableWriter;", "Lorg/jetbrains/kotlin/library/impl/IrFileWriter;", "data", "", "Lkotlin/Pair;", "", "", "(Ljava/util/List;)V", "writeData", "", "dataOutput", "Ljava/io/DataOutput;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrTableWriter extends IrFileWriter {
    private final List<Pair<Long, byte[]>> data;

    public IrTableWriter(List<Pair<Long, byte[]>> data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    @Override // org.jetbrains.kotlin.library.impl.IrFileWriter
    protected void writeData(DataOutput dataOutput) throws IOException {
        Intrinsics.checkNotNullParameter(dataOutput, "dataOutput");
        dataOutput.writeInt(this.data.size());
        int size = (this.data.size() * 16) + 4;
        Iterator<T> it = this.data.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            dataOutput.writeLong(((Number) pair.getFirst()).longValue());
            dataOutput.writeInt(size);
            dataOutput.writeInt(((byte[]) pair.getSecond()).length);
            size += ((byte[]) pair.getSecond()).length;
        }
        Iterator<T> it2 = this.data.iterator();
        while (it2.hasNext()) {
            dataOutput.write((byte[]) ((Pair) it2.next()).getSecond());
        }
    }
}
