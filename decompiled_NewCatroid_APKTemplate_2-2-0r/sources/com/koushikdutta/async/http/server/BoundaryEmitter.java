package com.koushikdutta.async.http.server;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public class BoundaryEmitter extends FilteredDataEmitter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private byte[] boundary;
    int state = 2;

    protected void onBoundaryEnd() {
    }

    protected void onBoundaryStart() {
    }

    public void setBoundary(String str) {
        this.boundary = ("\r\n--" + str).getBytes();
    }

    public String getBoundary() {
        if (this.boundary == null) {
            return null;
        }
        byte[] bArr = this.boundary;
        return new String(bArr, 4, bArr.length - 4);
    }

    public String getBoundaryStart() {
        byte[] bArr = this.boundary;
        return new String(bArr, 2, bArr.length - 2);
    }

    public String getBoundaryEnd() {
        return getBoundaryStart() + "--\r\n";
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.callback.DataCallback
    public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
        if (this.state > 0) {
            ByteBuffer byteBufferObtain = ByteBufferList.obtain(this.boundary.length);
            byteBufferObtain.put(this.boundary, 0, this.state);
            byteBufferObtain.flip();
            byteBufferList.addFirst(byteBufferObtain);
            this.state = 0;
        }
        int iRemaining = byteBufferList.remaining();
        byte[] bArr = new byte[iRemaining];
        byteBufferList.get(bArr);
        int i = 0;
        int i2 = 0;
        while (i < iRemaining) {
            int i3 = this.state;
            if (i3 >= 0) {
                byte b = bArr[i];
                byte[] bArr2 = this.boundary;
                if (b == bArr2[i3]) {
                    int i4 = i3 + 1;
                    this.state = i4;
                    if (i4 == bArr2.length) {
                        this.state = -1;
                    }
                } else if (i3 > 0) {
                    i -= i3;
                    this.state = 0;
                }
            } else if (i3 == -1) {
                byte b2 = bArr[i];
                if (b2 == 13) {
                    this.state = -4;
                    int length = (i - i2) - this.boundary.length;
                    if (i2 != 0 || length != 0) {
                        ByteBuffer byteBufferPut = ByteBufferList.obtain(length).put(bArr, i2, length);
                        byteBufferPut.flip();
                        ByteBufferList byteBufferList2 = new ByteBufferList();
                        byteBufferList2.add(byteBufferPut);
                        super.onDataAvailable(this, byteBufferList2);
                    }
                    onBoundaryStart();
                } else if (b2 == 45) {
                    this.state = -2;
                } else {
                    report(new MimeEncodingException("Invalid multipart/form-data. Expected \r or -"));
                    return;
                }
            } else if (i3 == -2) {
                if (bArr[i] == 45) {
                    this.state = -3;
                } else {
                    report(new MimeEncodingException("Invalid multipart/form-data. Expected -"));
                    return;
                }
            } else if (i3 == -3) {
                if (bArr[i] == 13) {
                    this.state = -4;
                    int i5 = i - i2;
                    ByteBuffer byteBufferPut2 = ByteBufferList.obtain((i5 - this.boundary.length) - 2).put(bArr, i2, (i5 - this.boundary.length) - 2);
                    byteBufferPut2.flip();
                    ByteBufferList byteBufferList3 = new ByteBufferList();
                    byteBufferList3.add(byteBufferPut2);
                    super.onDataAvailable(this, byteBufferList3);
                    onBoundaryEnd();
                } else {
                    report(new MimeEncodingException("Invalid multipart/form-data. Expected \r"));
                    return;
                }
            } else if (i3 == -4) {
                if (bArr[i] == 10) {
                    i2 = i + 1;
                    this.state = 0;
                } else {
                    report(new MimeEncodingException("Invalid multipart/form-data. Expected \n"));
                }
            } else {
                report(new MimeEncodingException("Invalid multipart/form-data. Unknown state?"));
            }
            i++;
        }
        if (i2 < iRemaining) {
            int iMax = (iRemaining - i2) - Math.max(this.state, 0);
            ByteBuffer byteBufferPut3 = ByteBufferList.obtain(iMax).put(bArr, i2, iMax);
            byteBufferPut3.flip();
            ByteBufferList byteBufferList4 = new ByteBufferList();
            byteBufferList4.add(byteBufferPut3);
            super.onDataAvailable(this, byteBufferList4);
        }
    }
}
