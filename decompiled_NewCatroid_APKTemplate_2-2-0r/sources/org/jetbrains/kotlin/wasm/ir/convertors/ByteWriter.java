package org.jetbrains.kotlin.wasm.ir.convertors;

import com.android.dx.rop.code.RegisterSpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WasmIrToBinary.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001:\u0001*B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\u0000H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0000H&J\u0010\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000eH&J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0010H\u0002J\u0018\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0012ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014J\u0018\u0010\u0015\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0016ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0018\u0010\u0019\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u001aø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001cJ\u001a\u0010\u001d\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0016H\u0002ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0018J\u000e\u0010\u001f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010 \u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0010J\u000e\u0010!\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\fJ\u000e\u0010\"\u001a\u00020\t2\u0006\u0010\n\u001a\u00020#J\u0018\u0010$\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0016ø\u0001\u0000¢\u0006\u0004\b%\u0010\u0018J\u0018\u0010&\u001a\u00020\t2\u0006\u0010\n\u001a\u00020'ø\u0001\u0000¢\u0006\u0004\b(\u0010)R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/ByteWriter;", "", "()V", "written", "", "getWritten", "()I", "createTemp", "write", "", RegisterSpec.PREFIX, "writeByte", "", "writeBytes", "", "writeSignedLeb128", "", "writeUByte", "Lkotlin/UByte;", "writeUByte-7apg3OU", "(B)V", "writeUInt32", "Lkotlin/UInt;", "writeUInt32-WZ4Q5Ns", "(I)V", "writeUInt64", "Lkotlin/ULong;", "writeUInt64-VKZWuLQ", "(J)V", "writeUnsignedLeb128", "writeUnsignedLeb128-WZ4Q5Ns", "writeVarInt32", "writeVarInt64", "writeVarInt7", "writeVarUInt1", "", "writeVarUInt32", "writeVarUInt32-WZ4Q5Ns", "writeVarUInt7", "Lkotlin/UShort;", "writeVarUInt7-xj2QHRw", "(S)V", "OutputStream", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ByteWriter {
    public abstract ByteWriter createTemp();

    public abstract int getWritten();

    public abstract void write(ByteWriter v);

    public abstract void writeByte(byte v);

    public abstract void writeBytes(byte[] v);

    /* JADX INFO: renamed from: writeUByte-7apg3OU, reason: not valid java name */
    public final void m7506writeUByte7apg3OU(byte v) {
        writeByte(v);
    }

    /* JADX INFO: renamed from: writeUInt32-WZ4Q5Ns, reason: not valid java name */
    public final void m7507writeUInt32WZ4Q5Ns(int v) {
        writeByte((byte) v);
        writeByte((byte) UInt.m2993constructorimpl(v >>> 8));
        writeByte((byte) UInt.m2993constructorimpl(v >>> 16));
        writeByte((byte) UInt.m2993constructorimpl(v >>> 24));
    }

    /* JADX INFO: renamed from: writeUInt64-VKZWuLQ, reason: not valid java name */
    public final void m7508writeUInt64VKZWuLQ(long v) {
        writeByte((byte) v);
        writeByte((byte) ULong.m3072constructorimpl(v >>> 8));
        writeByte((byte) ULong.m3072constructorimpl(v >>> 16));
        writeByte((byte) ULong.m3072constructorimpl(v >>> 24));
        writeByte((byte) ULong.m3072constructorimpl(v >>> 32));
        writeByte((byte) ULong.m3072constructorimpl(v >>> 40));
        writeByte((byte) ULong.m3072constructorimpl(v >>> 48));
        writeByte((byte) ULong.m3072constructorimpl(v >>> 56));
    }

    public final void writeVarInt7(byte v) {
        writeSignedLeb128(v);
    }

    public final void writeVarInt32(int v) {
        writeSignedLeb128(v);
    }

    public final void writeVarInt64(long v) {
        writeSignedLeb128(v);
    }

    public final void writeVarUInt1(boolean v) {
        m7505writeUnsignedLeb128WZ4Q5Ns(v ? 1 : 0);
    }

    /* JADX INFO: renamed from: writeVarUInt7-xj2QHRw, reason: not valid java name */
    public final void m7510writeVarUInt7xj2QHRw(short v) {
        m7505writeUnsignedLeb128WZ4Q5Ns(UInt.m2993constructorimpl(v & 65535));
    }

    /* JADX INFO: renamed from: writeVarUInt32-WZ4Q5Ns, reason: not valid java name */
    public final void m7509writeVarUInt32WZ4Q5Ns(int v) {
        m7505writeUnsignedLeb128WZ4Q5Ns(v);
    }

    /* JADX INFO: renamed from: writeUnsignedLeb128-WZ4Q5Ns, reason: not valid java name */
    private final void m7505writeUnsignedLeb128WZ4Q5Ns(int v) {
        int iM2993constructorimpl = UInt.m2993constructorimpl(v >>> 7);
        while (true) {
            int i = iM2993constructorimpl;
            int i2 = v;
            v = i;
            if (v != 0) {
                writeByte((byte) UInt.m2993constructorimpl(UInt.m2993constructorimpl(i2 & 127) | 128));
                iM2993constructorimpl = UInt.m2993constructorimpl(v >>> 7);
            } else {
                writeByte((byte) UInt.m2993constructorimpl(i2 & 127));
                return;
            }
        }
    }

    private final void writeSignedLeb128(long v) {
        long j = v >> 7;
        long j2 = (v & Long.MIN_VALUE) == 0 ? 0L : -1L;
        long j3 = v;
        boolean z = true;
        while (z) {
            z = (j == j2 && (j & 1) == (1 & (j3 >> 6))) ? false : true;
            writeByte((byte) ((j3 & 127) | (z ? 128L : 0L)));
            j3 = j;
            j >>= 7;
        }
    }

    /* JADX INFO: compiled from: WasmIrToBinary.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\u0000H\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0001H\u0016J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/ByteWriter$OutputStream;", "Lorg/jetbrains/kotlin/wasm/ir/convertors/ByteWriter;", "os", "Ljava/io/OutputStream;", "(Ljava/io/OutputStream;)V", "getOs", "()Ljava/io/OutputStream;", "<set-?>", "", "written", "getWritten", "()I", "createTemp", "write", "", RegisterSpec.PREFIX, "writeByte", "", "writeBytes", "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class OutputStream extends ByteWriter {
        private final java.io.OutputStream os;
        private int written;

        public OutputStream(java.io.OutputStream os) {
            Intrinsics.checkNotNullParameter(os, "os");
            this.os = os;
        }

        public final java.io.OutputStream getOs() {
            return this.os;
        }

        @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteWriter
        public int getWritten() {
            return this.written;
        }

        @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteWriter
        public void write(ByteWriter v) throws IOException {
            Intrinsics.checkNotNullParameter(v, "v");
            if (v instanceof OutputStream) {
                OutputStream outputStream = (OutputStream) v;
                java.io.OutputStream outputStream2 = outputStream.os;
                if (outputStream2 instanceof ByteArrayOutputStream) {
                    ((ByteArrayOutputStream) outputStream2).writeTo(this.os);
                    this.written = getWritten() + ((ByteArrayOutputStream) outputStream.os).size();
                    return;
                }
            }
            throw new IllegalStateException("Writer not created from createTemp".toString());
        }

        @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteWriter
        public void writeByte(byte v) throws IOException {
            this.os.write(v);
            this.written = getWritten() + 1;
        }

        @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteWriter
        public void writeBytes(byte[] v) throws IOException {
            Intrinsics.checkNotNullParameter(v, "v");
            this.os.write(v);
            this.written = getWritten() + v.length;
        }

        @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteWriter
        public OutputStream createTemp() {
            return new OutputStream(new ByteArrayOutputStream());
        }
    }
}
