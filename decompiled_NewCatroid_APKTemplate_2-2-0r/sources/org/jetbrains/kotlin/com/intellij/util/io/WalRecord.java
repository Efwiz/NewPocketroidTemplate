package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.ByteArraySequence;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalRecord;", "", "opCode", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalOpCode;", "checksum", "", "payload", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/io/ByteArraySequence;", "(Lcom/intellij/util/io/WalOpCode;JLcom/intellij/openapi/util/io/ByteArraySequence;)V", "getOpCode", "()Lcom/intellij/util/io/WalOpCode;", "getPayload", "()Lcom/intellij/openapi/util/io/ByteArraySequence;", "toString", "", "write", "", "target", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataOutputStream;", "Companion", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
final class WalRecord {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long checksum;
    private final WalOpCode opCode;
    private final ByteArraySequence payload;

    public WalRecord(WalOpCode opCode, long j, ByteArraySequence payload) {
        Intrinsics.checkNotNullParameter(opCode, "opCode");
        Intrinsics.checkNotNullParameter(payload, "payload");
        this.opCode = opCode;
        this.checksum = j;
        this.payload = payload;
    }

    public final WalOpCode getOpCode() {
        return this.opCode;
    }

    public final ByteArraySequence getPayload() {
        return this.payload;
    }

    public String toString() {
        return "record(" + this.opCode + ", checksum = " + this.checksum + ", len = " + this.payload.length() + ", payload = " + StringUtil.toHexString(this.payload.toBytes()) + ')';
    }

    public final void write(DataOutputStream target) throws IOException {
        Intrinsics.checkNotNullParameter(target, "target");
        target.writeByte(this.opCode.getCode());
        DataOutputStream dataOutputStream = target;
        DataInputOutputUtil.writeLONG(dataOutputStream, this.checksum);
        DataInputOutputUtil.writeINT(dataOutputStream, this.payload.getLength());
        target.write(this.payload.getInternalBuffer(), this.payload.getOffset(), this.payload.getLength());
    }

    /* JADX INFO: compiled from: writeAheadLog.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\"\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalRecord$Companion;", "", "()V", "read", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalRecord;", "input", "Ljava/io/DataInputStream;", "writeRecord", "opCode", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalOpCode;", "writer", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataOutputStream;", "", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WalRecord writeRecord(WalOpCode opCode, Function1<? super DataOutputStream, Unit> writer) throws IOException {
            Intrinsics.checkNotNullParameter(opCode, "opCode");
            Intrinsics.checkNotNullParameter(writer, "writer");
            UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
            ChecksumOutputStream checksumOutputStream = new ChecksumOutputStream(unsyncByteArrayOutputStream, WriteAheadLogKt.checksumGen);
            DataOutputStream dataOutputStream = new DataOutputStream(checksumOutputStream);
            try {
                writer.invoke(dataOutputStream);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(dataOutputStream, null);
                long jChecksum = checksumOutputStream.checksum();
                ByteArraySequence byteArraySequence = unsyncByteArrayOutputStream.toByteArraySequence();
                Intrinsics.checkNotNullExpressionValue(byteArraySequence, "baos.toByteArraySequence()");
                return new WalRecord(opCode, jChecksum, byteArraySequence);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(dataOutputStream, th);
                    throw th2;
                }
            }
        }

        public final WalRecord read(DataInputStream input) throws IOException {
            Intrinsics.checkNotNullParameter(input, "input");
            try {
                byte b = input.readByte();
                if (b >= WalOpCode.INSTANCE.getSize()) {
                    throw new CorruptionException("no opcode present for code " + ((int) b));
                }
                DataInputStream dataInputStream = input;
                long j = DataInputOutputUtil.readLONG(dataInputStream);
                int i = DataInputOutputUtil.readINT(dataInputStream);
                ChecksumInputStream checksumInputStream = new ChecksumInputStream(input, WriteAheadLogKt.checksumGen);
                byte[] nBytes = checksumInputStream.readNBytes(i);
                long jChecksum = checksumInputStream.checksum();
                if (jChecksum != j) {
                    throw new CorruptionException("checksum is wrong for log record: expected = " + j + " but actual = " + jChecksum);
                }
                return new WalRecord(WalOpCode.values()[b], j, new ByteArraySequence(nBytes));
            } catch (EOFException unused) {
                throw new EndOfLog();
            }
        }
    }
}
