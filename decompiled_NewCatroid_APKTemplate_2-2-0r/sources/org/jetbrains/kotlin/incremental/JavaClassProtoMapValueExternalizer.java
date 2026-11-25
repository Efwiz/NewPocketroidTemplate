package org.jetbrains.kotlin.incremental;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;

/* JADX INFO: compiled from: JavaClassesTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\f\u0010\f\u001a\u00020\r*\u00020\u0006H\u0002J\u0014\u0010\u000e\u001a\u00020\b*\u00020\n2\u0006\u0010\u000f\u001a\u00020\rH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/incremental/JavaClassProtoMapValueExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/incremental/SerializedJavaClass;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "value", "readBytesWithSize", "", "writeBytesWithSize", "bytes", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassProtoMapValueExternalizer implements DataExternalizer<SerializedJavaClass> {
    public static final JavaClassProtoMapValueExternalizer INSTANCE = new JavaClassProtoMapValueExternalizer();

    private JavaClassProtoMapValueExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, SerializedJavaClass value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(value, "value");
        byte[] byteArray = value.getProto().toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        writeBytesWithSize(output, byteArray);
        byte[] byteArray2 = value.getStringTable().toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray2, "toByteArray(...)");
        writeBytesWithSize(output, byteArray2);
        byte[] byteArray3 = value.getQualifiedNameTable().toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray3, "toByteArray(...)");
        writeBytesWithSize(output, byteArray3);
    }

    private final void writeBytesWithSize(DataOutput dataOutput, byte[] bArr) throws IOException {
        dataOutput.writeInt(bArr.length);
        dataOutput.write(bArr);
    }

    private final byte[] readBytesWithSize(DataInput dataInput) throws IOException {
        int i = dataInput.readInt();
        byte[] bArr = new byte[i];
        dataInput.readFully(bArr, 0, i);
        return bArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public SerializedJavaClass read(DataInput input) throws InvalidProtocolBufferException {
        Intrinsics.checkNotNullParameter(input, "input");
        ProtoBuf.Class from = ProtoBuf.Class.parseFrom(readBytesWithSize(input), JavaClassesTrackerImplKt.getJAVA_CLASS_PROTOBUF_REGISTRY());
        ProtoBuf.StringTable from2 = ProtoBuf.StringTable.parseFrom(readBytesWithSize(input), JavaClassesTrackerImplKt.getJAVA_CLASS_PROTOBUF_REGISTRY());
        ProtoBuf.QualifiedNameTable from3 = ProtoBuf.QualifiedNameTable.parseFrom(readBytesWithSize(input), JavaClassesTrackerImplKt.getJAVA_CLASS_PROTOBUF_REGISTRY());
        Intrinsics.checkNotNull(from);
        Intrinsics.checkNotNull(from2);
        Intrinsics.checkNotNull(from3);
        return new SerializedJavaClass(from, from2, from3);
    }
}
