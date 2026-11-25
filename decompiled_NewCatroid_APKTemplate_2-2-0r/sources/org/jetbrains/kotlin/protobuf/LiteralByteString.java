package org.jetbrains.kotlin.protobuf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes4.dex */
class LiteralByteString extends ByteString {
    protected final byte[] bytes;
    private int hash = 0;

    protected int getOffsetIntoBytes() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    protected int getTreeDepth() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    protected boolean isBalanced() {
        return true;
    }

    LiteralByteString(byte[] bArr) {
        this.bytes = bArr;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public byte byteAt(int i) {
        return this.bytes[i];
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public int size() {
        return this.bytes.length;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public ByteString substring(int i, int i2) {
        if (i < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(32).append("Beginning index: ").append(i).append(" < 0").toString());
        }
        if (i2 > size()) {
            throw new IndexOutOfBoundsException(new StringBuilder(36).append("End index: ").append(i2).append(" > ").append(size()).toString());
        }
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(66).append("Beginning index larger than ending index: ").append(i).append(", ").append(i2).toString());
        }
        if (i3 == 0) {
            return ByteString.EMPTY;
        }
        return new BoundedByteString(this.bytes, getOffsetIntoBytes() + i, i3);
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    protected void copyToInternal(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.bytes, i, bArr, i2, i3);
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public void copyTo(ByteBuffer byteBuffer) {
        byteBuffer.put(this.bytes, getOffsetIntoBytes(), size());
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(this.bytes, getOffsetIntoBytes(), size()).asReadOnlyBuffer();
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(asReadOnlyByteBuffer());
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(toByteArray());
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    void writeToInternal(OutputStream outputStream, int i, int i2) throws IOException {
        outputStream.write(this.bytes, getOffsetIntoBytes() + i, i2);
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public String toString(String str) throws UnsupportedEncodingException {
        return new String(this.bytes, getOffsetIntoBytes(), size(), str);
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public boolean isValidUtf8() {
        int offsetIntoBytes = getOffsetIntoBytes();
        return Utf8.isValidUtf8(this.bytes, offsetIntoBytes, size() + offsetIntoBytes);
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    protected int partialIsValidUtf8(int i, int i2, int i3) {
        int offsetIntoBytes = getOffsetIntoBytes() + i2;
        return Utf8.partialIsValidUtf8(i, this.bytes, offsetIntoBytes, i3 + offsetIntoBytes);
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ByteString) || size() != ((ByteString) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (obj instanceof LiteralByteString) {
            return equalsRange((LiteralByteString) obj, 0, size());
        }
        if (obj instanceof RopeByteString) {
            return obj.equals(this);
        }
        String strValueOf = String.valueOf(String.valueOf(obj.getClass()));
        throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 49).append("Has a new type of ByteString been created? Found ").append(strValueOf).toString());
    }

    boolean equalsRange(LiteralByteString literalByteString, int i, int i2) {
        if (i2 > literalByteString.size()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(size()).toString());
        }
        if (i + i2 > literalByteString.size()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: ").append(i).append(", ").append(i2).append(", ").append(literalByteString.size()).toString());
        }
        byte[] bArr = this.bytes;
        byte[] bArr2 = literalByteString.bytes;
        int offsetIntoBytes = getOffsetIntoBytes() + i2;
        int offsetIntoBytes2 = getOffsetIntoBytes();
        int offsetIntoBytes3 = literalByteString.getOffsetIntoBytes() + i;
        while (offsetIntoBytes2 < offsetIntoBytes) {
            if (bArr[offsetIntoBytes2] != bArr2[offsetIntoBytes3]) {
                return false;
            }
            offsetIntoBytes2++;
            offsetIntoBytes3++;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public int hashCode() {
        int iPartialHash = this.hash;
        if (iPartialHash == 0) {
            int size = size();
            iPartialHash = partialHash(size, 0, size);
            if (iPartialHash == 0) {
                iPartialHash = 1;
            }
            this.hash = iPartialHash;
        }
        return iPartialHash;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    protected int peekCachedHashCode() {
        return this.hash;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    protected int partialHash(int i, int i2, int i3) {
        return hashCode(i, this.bytes, getOffsetIntoBytes() + i2, i3);
    }

    static int hashCode(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static int hashCode(byte[] bArr) {
        int iHashCode = hashCode(bArr.length, bArr, 0, bArr.length);
        if (iHashCode == 0) {
            return 1;
        }
        return iHashCode;
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public InputStream newInput() {
        return new ByteArrayInputStream(this.bytes, getOffsetIntoBytes(), size());
    }

    @Override // org.jetbrains.kotlin.protobuf.ByteString
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(this);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.protobuf.ByteString, java.lang.Iterable
    public Iterator<Byte> iterator() {
        return new LiteralByteIterator();
    }

    private class LiteralByteIterator implements ByteString.ByteIterator {
        private final int limit;
        private int position;

        private LiteralByteIterator() {
            this.position = 0;
            this.limit = LiteralByteString.this.size();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.position < this.limit;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public Byte next() {
            return Byte.valueOf(nextByte());
        }

        @Override // org.jetbrains.kotlin.protobuf.ByteString.ByteIterator
        public byte nextByte() {
            try {
                byte[] bArr = LiteralByteString.this.bytes;
                int i = this.position;
                this.position = i + 1;
                return bArr[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException(e.getMessage());
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
