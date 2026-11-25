package org.jetbrains.kotlin.wasm.ir.convertors;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WasmBinaryToIR.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001)B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001b0!H\u0086\bø\u0001\u0000J\u0010\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u001dH\u0016J\b\u0010$\u001a\u00020%H\u0016J\u0017\u0010&\u001a\u00020'2\b\u0010#\u001a\u0004\u0018\u00010\u001dH\u0016¢\u0006\u0002\u0010(R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\b\"\u0004\b\u0012\u0010\nR \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/MyByteReader;", "Lorg/jetbrains/kotlin/wasm/ir/convertors/ByteReader;", "ins", "Ljava/io/InputStream;", "(Ljava/io/InputStream;)V", "currentMaxSize", "", "getCurrentMaxSize", "()J", "setCurrentMaxSize", "(J)V", "getIns", "()Ljava/io/InputStream;", "isEof", "", "()Z", TypedValues.CycleType.S_WAVE_OFFSET, "getOffset", "setOffset", "sizeLimits", "", "Lorg/jetbrains/kotlin/wasm/ir/convertors/MyByteReader$SizeLimit;", "getSizeLimits", "()Ljava/util/List;", "setSizeLimits", "(Ljava/util/List;)V", "limitSize", "", "size", "", "reason", "", "block", "Lkotlin/Function0;", "read", "amount", "readByte", "", "readBytes", "", "(Ljava/lang/Integer;)[B", "SizeLimit", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MyByteReader extends ByteReader {
    private long currentMaxSize;
    private final InputStream ins;
    private long offset;
    private List<SizeLimit> sizeLimits;

    public MyByteReader(InputStream ins) {
        Intrinsics.checkNotNullParameter(ins, "ins");
        this.ins = ins;
        this.sizeLimits = CollectionsKt.mutableListOf(new SizeLimit(Long.MAX_VALUE, "Root"));
        this.currentMaxSize = Long.MAX_VALUE;
    }

    public final InputStream getIns() {
        return this.ins;
    }

    public final long getOffset() {
        return this.offset;
    }

    public final void setOffset(long j) {
        this.offset = j;
    }

    /* JADX INFO: compiled from: WasmBinaryToIR.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/MyByteReader$SizeLimit;", "", "maxSize", "", "reason", "", "(JLjava/lang/String;)V", "getMaxSize", "()J", "getReason", "()Ljava/lang/String;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SizeLimit {
        private final long maxSize;
        private final String reason;

        public SizeLimit(long j, String reason) {
            Intrinsics.checkNotNullParameter(reason, "reason");
            this.maxSize = j;
            this.reason = reason;
        }

        public final long getMaxSize() {
            return this.maxSize;
        }

        public final String getReason() {
            return this.reason;
        }
    }

    public final List<SizeLimit> getSizeLimits() {
        return this.sizeLimits;
    }

    public final void setSizeLimits(List<SizeLimit> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.sizeLimits = list;
    }

    public final long getCurrentMaxSize() {
        return this.currentMaxSize;
    }

    public final void setCurrentMaxSize(long j) {
        this.currentMaxSize = j;
    }

    @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteReader
    public boolean isEof() {
        throw new IllegalStateException("Not implemented".toString());
    }

    @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteReader
    public ByteReader read(int amount) {
        throw new IllegalStateException("Not implemented".toString());
    }

    public final void limitSize(int size, String reason, Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Intrinsics.checkNotNullParameter(block, "block");
        long offset = getOffset() + ((long) size);
        getSizeLimits().add(new SizeLimit(offset, reason));
        setCurrentMaxSize(offset);
        block.invoke();
        if (getOffset() != getCurrentMaxSize()) {
            throw new IllegalArgumentException(("Ending size-limited block \"" + reason + "\". We haven't read all " + size + " bytes.").toString());
        }
        CollectionsKt.removeLast(getSizeLimits());
        setCurrentMaxSize(((SizeLimit) CollectionsKt.last((List) getSizeLimits())).getMaxSize());
    }

    @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteReader
    public byte readByte() throws IOException {
        int i = this.ins.read();
        if (i == -1) {
            throw new IllegalStateException("UnexpectedEnd".toString());
        }
        long j = this.offset + 1;
        this.offset = j;
        if (j <= this.currentMaxSize) {
            return (byte) i;
        }
        throw new IllegalStateException(("Reading bytes past limit " + this.currentMaxSize + " Reason: " + ((SizeLimit) CollectionsKt.last((List) this.sizeLimits)).getReason()).toString());
    }

    @Override // org.jetbrains.kotlin.wasm.ir.convertors.ByteReader
    public byte[] readBytes(Integer amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        byte[] bArr = new byte[amount.intValue()];
        for (int i = 0; i < amount.intValue(); i++) {
            bArr[i] = readByte();
        }
        return bArr;
    }
}
