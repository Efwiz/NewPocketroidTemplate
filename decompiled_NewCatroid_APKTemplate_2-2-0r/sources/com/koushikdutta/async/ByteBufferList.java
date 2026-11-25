package com.koushikdutta.async;

import android.os.Looper;
import com.koushikdutta.async.util.Charsets;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/* JADX INFO: loaded from: classes10.dex */
public class ByteBufferList {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    ArrayDeque<ByteBuffer> mBuffers = new ArrayDeque<>();
    ByteOrder order = ByteOrder.BIG_ENDIAN;
    private int remaining = 0;
    static PriorityQueue<ByteBuffer> reclaimed = new PriorityQueue<>(8, new Reclaimer());
    private static int MAX_SIZE = 1048576;
    public static int MAX_ITEM_SIZE = 262144;
    static int currentSize = 0;
    static int maxItem = 0;
    private static final Object LOCK = new Object();
    public static final ByteBuffer EMPTY_BYTEBUFFER = ByteBuffer.allocate(0);

    public ByteOrder order() {
        return this.order;
    }

    public ByteBufferList order(ByteOrder byteOrder) {
        this.order = byteOrder;
        return this;
    }

    public ByteBufferList() {
    }

    public ByteBufferList(ByteBuffer... byteBufferArr) {
        addAll(byteBufferArr);
    }

    public ByteBufferList(byte[] bArr) {
        add(ByteBuffer.wrap(bArr));
    }

    public ByteBufferList addAll(ByteBuffer... byteBufferArr) {
        for (ByteBuffer byteBuffer : byteBufferArr) {
            add(byteBuffer);
        }
        return this;
    }

    public ByteBufferList addAll(ByteBufferList... byteBufferListArr) {
        for (ByteBufferList byteBufferList : byteBufferListArr) {
            byteBufferList.get(this);
        }
        return this;
    }

    public byte[] getBytes(int i) {
        byte[] bArr = new byte[i];
        get(bArr);
        return bArr;
    }

    public byte[] getAllByteArray() {
        if (this.mBuffers.size() == 1) {
            ByteBuffer byteBufferPeek = this.mBuffers.peek();
            if (byteBufferPeek.capacity() == remaining() && byteBufferPeek.isDirect()) {
                this.remaining = 0;
                return this.mBuffers.remove().array();
            }
        }
        byte[] bArr = new byte[remaining()];
        get(bArr);
        return bArr;
    }

    public ByteBuffer[] getAllArray() {
        ByteBuffer[] byteBufferArr = (ByteBuffer[]) this.mBuffers.toArray(new ByteBuffer[this.mBuffers.size()]);
        this.mBuffers.clear();
        this.remaining = 0;
        return byteBufferArr;
    }

    public boolean isEmpty() {
        return this.remaining == 0;
    }

    public int remaining() {
        return this.remaining;
    }

    public boolean hasRemaining() {
        return remaining() > 0;
    }

    public short peekShort() {
        return read(2).duplicate().getShort();
    }

    public int peekInt() {
        return read(4).duplicate().getInt();
    }

    public long peekLong() {
        return read(8).duplicate().getLong();
    }

    public byte[] peekBytes(int i) {
        byte[] bArr = new byte[i];
        read(i).duplicate().get(bArr);
        return bArr;
    }

    public ByteBufferList skip(int i) {
        get(null, 0, i);
        return this;
    }

    public int getInt() {
        int i = read(4).getInt();
        this.remaining -= 4;
        return i;
    }

    public char getByteChar() {
        char c = (char) read(1).get();
        this.remaining--;
        return c;
    }

    public short getShort() {
        short s = read(2).getShort();
        this.remaining -= 2;
        return s;
    }

    public byte get() {
        byte b = read(1).get();
        this.remaining--;
        return b;
    }

    public long getLong() {
        long j = read(8).getLong();
        this.remaining -= 8;
        return j;
    }

    public void get(byte[] bArr) {
        get(bArr, 0, bArr.length);
    }

    public void get(byte[] bArr, int i, int i2) {
        if (remaining() < i2) {
            throw new IllegalArgumentException("length");
        }
        int i3 = i2;
        while (i3 > 0) {
            ByteBuffer byteBufferPeek = this.mBuffers.peek();
            int iMin = Math.min(byteBufferPeek.remaining(), i3);
            if (bArr != null) {
                byteBufferPeek.get(bArr, i, iMin);
            } else {
                byteBufferPeek.position(byteBufferPeek.position() + iMin);
            }
            i3 -= iMin;
            i += iMin;
            if (byteBufferPeek.remaining() == 0) {
                this.mBuffers.remove();
                reclaim(byteBufferPeek);
            }
        }
        this.remaining -= i2;
    }

    public void get(ByteBufferList byteBufferList, int i) {
        if (remaining() < i) {
            throw new IllegalArgumentException("length");
        }
        int i2 = 0;
        while (i2 < i) {
            ByteBuffer byteBufferRemove = this.mBuffers.remove();
            int iRemaining = byteBufferRemove.remaining();
            if (iRemaining == 0) {
                reclaim(byteBufferRemove);
            } else {
                int i3 = iRemaining + i2;
                if (i3 > i) {
                    int i4 = i - i2;
                    ByteBuffer byteBufferObtain = obtain(i4);
                    byteBufferObtain.limit(i4);
                    byteBufferRemove.get(byteBufferObtain.array(), 0, i4);
                    byteBufferList.add(byteBufferObtain);
                    this.mBuffers.addFirst(byteBufferRemove);
                    break;
                }
                byteBufferList.add(byteBufferRemove);
                i2 = i3;
            }
        }
        this.remaining -= i;
    }

    public void get(ByteBufferList byteBufferList) {
        get(byteBufferList, remaining());
    }

    public ByteBufferList get(int i) {
        ByteBufferList byteBufferList = new ByteBufferList();
        get(byteBufferList, i);
        return byteBufferList.order(this.order);
    }

    public ByteBuffer getAll() {
        if (remaining() == 0) {
            return EMPTY_BYTEBUFFER;
        }
        read(remaining());
        return remove();
    }

    private ByteBuffer read(int i) {
        ByteBuffer byteBufferRemove;
        if (remaining() < i) {
            throw new IllegalArgumentException("count : " + remaining() + "/" + i);
        }
        ByteBuffer byteBufferPeek = this.mBuffers.peek();
        while (byteBufferPeek != null && !byteBufferPeek.hasRemaining()) {
            reclaim(this.mBuffers.remove());
            byteBufferPeek = this.mBuffers.peek();
        }
        if (byteBufferPeek == null) {
            return EMPTY_BYTEBUFFER;
        }
        if (byteBufferPeek.remaining() >= i) {
            return byteBufferPeek.order(this.order);
        }
        ByteBuffer byteBufferObtain = obtain(i);
        byteBufferObtain.limit(i);
        byte[] bArrArray = byteBufferObtain.array();
        int i2 = 0;
        loop1: while (true) {
            byteBufferRemove = null;
            do {
                if (i2 >= i) {
                    break loop1;
                }
                byteBufferRemove = this.mBuffers.remove();
                int iMin = Math.min(i - i2, byteBufferRemove.remaining());
                byteBufferRemove.get(bArrArray, i2, iMin);
                i2 += iMin;
            } while (byteBufferRemove.remaining() != 0);
            reclaim(byteBufferRemove);
        }
        if (byteBufferRemove != null && byteBufferRemove.remaining() > 0) {
            this.mBuffers.addFirst(byteBufferRemove);
        }
        this.mBuffers.addFirst(byteBufferObtain);
        return byteBufferObtain.order(this.order);
    }

    public void trim() {
        read(0);
    }

    public ByteBufferList add(ByteBufferList byteBufferList) {
        byteBufferList.get(this);
        return this;
    }

    public ByteBufferList add(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() <= 0) {
            reclaim(byteBuffer);
            return this;
        }
        addRemaining(byteBuffer.remaining());
        if (this.mBuffers.size() > 0) {
            ByteBuffer last = this.mBuffers.getLast();
            if (last.capacity() - last.limit() >= byteBuffer.remaining()) {
                last.mark();
                last.position(last.limit());
                last.limit(last.capacity());
                last.put(byteBuffer);
                last.limit(last.position());
                last.reset();
                reclaim(byteBuffer);
                trim();
                return this;
            }
        }
        this.mBuffers.add(byteBuffer);
        trim();
        return this;
    }

    public void addFirst(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() <= 0) {
            reclaim(byteBuffer);
            return;
        }
        addRemaining(byteBuffer.remaining());
        if (this.mBuffers.size() > 0) {
            ByteBuffer first = this.mBuffers.getFirst();
            if (first.position() >= byteBuffer.remaining()) {
                first.position(first.position() - byteBuffer.remaining());
                first.mark();
                first.put(byteBuffer);
                first.reset();
                reclaim(byteBuffer);
                return;
            }
        }
        this.mBuffers.addFirst(byteBuffer);
    }

    private void addRemaining(int i) {
        if (remaining() >= 0) {
            this.remaining += i;
        }
    }

    public void recycle() {
        while (this.mBuffers.size() > 0) {
            reclaim(this.mBuffers.remove());
        }
        this.remaining = 0;
    }

    public ByteBuffer remove() {
        ByteBuffer byteBufferRemove = this.mBuffers.remove();
        this.remaining -= byteBufferRemove.remaining();
        return byteBufferRemove;
    }

    public int size() {
        return this.mBuffers.size();
    }

    public void spewString() {
        System.out.println(peekString());
    }

    public String peekString() {
        return peekString(null);
    }

    public String peekString(Charset charset) {
        byte[] bArrArray;
        int iRemaining;
        int i;
        if (charset == null) {
            charset = Charsets.US_ASCII;
        }
        StringBuilder sb = new StringBuilder();
        for (ByteBuffer byteBuffer : this.mBuffers) {
            if (byteBuffer.isDirect()) {
                bArrArray = new byte[byteBuffer.remaining()];
                iRemaining = byteBuffer.remaining();
                byteBuffer.get(bArrArray);
                i = 0;
            } else {
                bArrArray = byteBuffer.array();
                int iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
                iRemaining = byteBuffer.remaining();
                i = iArrayOffset;
            }
            sb.append(new String(bArrArray, i, iRemaining, charset));
        }
        return sb.toString();
    }

    public String readString() {
        return readString(null);
    }

    public String readString(Charset charset) {
        String strPeekString = peekString(charset);
        recycle();
        return strPeekString;
    }

    static class Reclaimer implements Comparator<ByteBuffer> {
        Reclaimer() {
        }

        @Override // java.util.Comparator
        public int compare(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
            if (byteBuffer.capacity() == byteBuffer2.capacity()) {
                return 0;
            }
            return byteBuffer.capacity() > byteBuffer2.capacity() ? 1 : -1;
        }
    }

    private static PriorityQueue<ByteBuffer> getReclaimed() {
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper == null || Thread.currentThread() != mainLooper.getThread()) {
            return reclaimed;
        }
        return null;
    }

    public static void setMaxPoolSize(int i) {
        MAX_SIZE = i;
    }

    public static void setMaxItemSize(int i) {
        MAX_ITEM_SIZE = i;
    }

    private static boolean reclaimedContains(ByteBuffer byteBuffer) {
        Iterator<ByteBuffer> it = reclaimed.iterator();
        while (it.hasNext()) {
            if (it.next() == byteBuffer) {
                return true;
            }
        }
        return false;
    }

    public static void reclaim(ByteBuffer byteBuffer) {
        PriorityQueue<ByteBuffer> reclaimed2;
        if (byteBuffer == null || byteBuffer.isDirect() || byteBuffer.arrayOffset() != 0 || byteBuffer.array().length != byteBuffer.capacity() || byteBuffer.capacity() < 8192 || byteBuffer.capacity() > MAX_ITEM_SIZE || (reclaimed2 = getReclaimed()) == null) {
            return;
        }
        synchronized (LOCK) {
            while (currentSize > MAX_SIZE && reclaimed2.size() > 0 && reclaimed2.peek().capacity() < byteBuffer.capacity()) {
                currentSize -= reclaimed2.remove().capacity();
            }
            if (currentSize > MAX_SIZE) {
                return;
            }
            byteBuffer.position(0);
            byteBuffer.limit(byteBuffer.capacity());
            currentSize += byteBuffer.capacity();
            reclaimed2.add(byteBuffer);
            maxItem = Math.max(maxItem, byteBuffer.capacity());
        }
    }

    public static ByteBuffer obtain(int i) {
        PriorityQueue<ByteBuffer> reclaimed2;
        if (i <= maxItem && (reclaimed2 = getReclaimed()) != null) {
            synchronized (LOCK) {
                while (reclaimed2.size() > 0) {
                    ByteBuffer byteBufferRemove = reclaimed2.remove();
                    if (reclaimed2.size() == 0) {
                        maxItem = 0;
                    }
                    currentSize -= byteBufferRemove.capacity();
                    if (byteBufferRemove.capacity() >= i) {
                        return byteBufferRemove;
                    }
                }
            }
        }
        return ByteBuffer.allocate(Math.max(8192, i));
    }

    public static void obtainArray(ByteBuffer[] byteBufferArr, int i) {
        int i2;
        PriorityQueue<ByteBuffer> reclaimed2 = getReclaimed();
        int iMin = 0;
        if (reclaimed2 != null) {
            synchronized (LOCK) {
                i2 = 0;
                while (reclaimed2.size() > 0 && iMin < i && i2 < byteBufferArr.length - 1) {
                    ByteBuffer byteBufferRemove = reclaimed2.remove();
                    currentSize -= byteBufferRemove.capacity();
                    iMin += Math.min(i - iMin, byteBufferRemove.capacity());
                    byteBufferArr[i2] = byteBufferRemove;
                    i2++;
                }
            }
        } else {
            i2 = 0;
        }
        if (iMin < i) {
            byteBufferArr[i2] = ByteBuffer.allocate(Math.max(8192, i - iMin));
            i2++;
        }
        while (i2 < byteBufferArr.length) {
            byteBufferArr[i2] = EMPTY_BYTEBUFFER;
            i2++;
        }
    }

    public static void writeOutputStream(OutputStream outputStream, ByteBuffer byteBuffer) throws IOException {
        byte[] bArrArray;
        int iRemaining;
        int i;
        if (byteBuffer.isDirect()) {
            bArrArray = new byte[byteBuffer.remaining()];
            iRemaining = byteBuffer.remaining();
            byteBuffer.get(bArrArray);
            i = 0;
        } else {
            bArrArray = byteBuffer.array();
            int iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            iRemaining = byteBuffer.remaining();
            i = iArrayOffset;
        }
        outputStream.write(bArrArray, i, iRemaining);
    }
}
