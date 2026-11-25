package com.koushikdutta.async.http.spdy;

import com.koushikdutta.async.ByteBufferList;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* JADX INFO: loaded from: classes10.dex */
class HeaderReader {
    Inflater inflater = new Inflater() { // from class: com.koushikdutta.async.http.spdy.HeaderReader.1
        @Override // java.util.zip.Inflater
        public int inflate(byte[] bArr, int i, int i2) throws DataFormatException {
            int iInflate = super.inflate(bArr, i, i2);
            if (iInflate != 0 || !needsDictionary()) {
                return iInflate;
            }
            setDictionary(Spdy3.DICTIONARY);
            return super.inflate(bArr, i, i2);
        }
    };

    public List<Header> readHeader(ByteBufferList byteBufferList, int i) throws IOException {
        byte[] bArr = new byte[i];
        byteBufferList.get(bArr);
        this.inflater.setInput(bArr);
        ByteBufferList byteBufferListOrder = new ByteBufferList().order(ByteOrder.BIG_ENDIAN);
        while (!this.inflater.needsInput()) {
            ByteBuffer byteBufferObtain = ByteBufferList.obtain(8192);
            try {
                byteBufferObtain.limit(this.inflater.inflate(byteBufferObtain.array()));
                byteBufferListOrder.add(byteBufferObtain);
            } catch (DataFormatException e) {
                throw new IOException(e);
            }
        }
        int i2 = byteBufferListOrder.getInt();
        ArrayList arrayList = new ArrayList(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            ByteString asciiLowercase = readByteString(byteBufferListOrder).toAsciiLowercase();
            ByteString byteString = readByteString(byteBufferListOrder);
            if (asciiLowercase.size() == 0) {
                throw new IOException("name.size == 0");
            }
            arrayList.add(new Header(asciiLowercase, byteString));
        }
        return arrayList;
    }

    private static ByteString readByteString(ByteBufferList byteBufferList) {
        return ByteString.of(byteBufferList.getBytes(byteBufferList.getInt()));
    }
}
