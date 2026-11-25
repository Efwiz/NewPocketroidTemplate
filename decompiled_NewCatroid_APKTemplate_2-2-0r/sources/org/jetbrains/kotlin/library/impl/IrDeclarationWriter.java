package org.jetbrains.kotlin.library.impl;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.SerializedDeclaration;

/* JADX INFO: compiled from: IrFileWriters.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrDeclarationWriter;", "Lorg/jetbrains/kotlin/library/impl/IrFileWriter;", "declarations", "", "Lorg/jetbrains/kotlin/library/SerializedDeclaration;", "(Ljava/util/List;)V", "INDEX_HEADER_SIZE", "", "SINGLE_INDEX_RECORD_SIZE", "writeData", "", "dataOutput", "Ljava/io/DataOutput;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrDeclarationWriter extends IrFileWriter {
    private final int INDEX_HEADER_SIZE;
    private final int SINGLE_INDEX_RECORD_SIZE;
    private final List<SerializedDeclaration> declarations;

    public IrDeclarationWriter(List<SerializedDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        this.declarations = declarations;
        this.SINGLE_INDEX_RECORD_SIZE = 12;
        this.INDEX_HEADER_SIZE = 4;
    }

    @Override // org.jetbrains.kotlin.library.impl.IrFileWriter
    protected void writeData(DataOutput dataOutput) throws IOException {
        Intrinsics.checkNotNullParameter(dataOutput, "dataOutput");
        dataOutput.writeInt(this.declarations.size());
        int size = this.INDEX_HEADER_SIZE + (this.SINGLE_INDEX_RECORD_SIZE * this.declarations.size());
        for (SerializedDeclaration serializedDeclaration : this.declarations) {
            dataOutput.writeInt(serializedDeclaration.getId());
            dataOutput.writeInt(size);
            dataOutput.writeInt(serializedDeclaration.getSize());
            size += serializedDeclaration.getSize();
        }
        Iterator<SerializedDeclaration> it = this.declarations.iterator();
        while (it.hasNext()) {
            dataOutput.write(it.next().getBytes());
        }
    }
}
