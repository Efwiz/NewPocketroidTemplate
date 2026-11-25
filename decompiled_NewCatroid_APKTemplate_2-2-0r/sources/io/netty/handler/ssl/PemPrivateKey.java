package io.netty.handler.ssl;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;
import java.security.PrivateKey;

/* JADX INFO: loaded from: classes11.dex */
public final class PemPrivateKey extends AbstractReferenceCounted implements PrivateKey, PemEncoded {
    private static final byte[] BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] END_PRIVATE_KEY = "\n-----END PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final String PKCS8_FORMAT = "PKCS#8";
    private static final long serialVersionUID = 7978017465645018936L;
    private final ByteBuf content;

    @Override // io.netty.handler.ssl.PemEncoded
    public boolean isSensitive() {
        return true;
    }

    static PemEncoded toPEM(ByteBufAllocator byteBufAllocator, boolean z, PrivateKey privateKey) {
        if (privateKey instanceof PemEncoded) {
            return ((PemEncoded) privateKey).retain();
        }
        byte[] encoded = privateKey.getEncoded();
        if (encoded == null) {
            throw new IllegalArgumentException(privateKey.getClass().getName() + " does not support encoding");
        }
        return toPEM(byteBufAllocator, z, encoded);
    }

    static PemEncoded toPEM(ByteBufAllocator byteBufAllocator, boolean z, byte[] bArr) {
        ByteBuf byteBufWrappedBuffer = Unpooled.wrappedBuffer(bArr);
        try {
            ByteBuf base64 = SslUtils.toBase64(byteBufAllocator, byteBufWrappedBuffer);
            try {
                byte[] bArr2 = BEGIN_PRIVATE_KEY;
                int length = bArr2.length + base64.readableBytes();
                byte[] bArr3 = END_PRIVATE_KEY;
                int length2 = length + bArr3.length;
                ByteBuf byteBufDirectBuffer = z ? byteBufAllocator.directBuffer(length2) : byteBufAllocator.buffer(length2);
                try {
                    byteBufDirectBuffer.writeBytes(bArr2);
                    byteBufDirectBuffer.writeBytes(base64);
                    byteBufDirectBuffer.writeBytes(bArr3);
                    PemValue pemValue = new PemValue(byteBufDirectBuffer, true);
                    SslUtils.zerooutAndRelease(base64);
                    SslUtils.zerooutAndRelease(byteBufWrappedBuffer);
                    return pemValue;
                } catch (Throwable th) {
                    SslUtils.zerooutAndRelease(byteBufDirectBuffer);
                    throw th;
                }
            } catch (Throwable th2) {
                SslUtils.zerooutAndRelease(base64);
                throw th2;
            }
        } catch (Throwable th3) {
            SslUtils.zerooutAndRelease(byteBufWrappedBuffer);
            throw th3;
        }
    }

    public static PemPrivateKey valueOf(byte[] bArr) {
        return valueOf(Unpooled.wrappedBuffer(bArr));
    }

    public static PemPrivateKey valueOf(ByteBuf byteBuf) {
        return new PemPrivateKey(byteBuf);
    }

    private PemPrivateKey(ByteBuf byteBuf) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, FirebaseAnalytics.Param.CONTENT);
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        int iRefCnt = refCnt();
        if (iRefCnt <= 0) {
            throw new IllegalReferenceCountException(iRefCnt);
        }
        return this.content;
    }

    @Override // io.netty.handler.ssl.PemEncoded, io.netty.buffer.ByteBufHolder
    public PemPrivateKey copy() {
        return replace(this.content.copy());
    }

    @Override // io.netty.handler.ssl.PemEncoded, io.netty.buffer.ByteBufHolder
    public PemPrivateKey duplicate() {
        return replace(this.content.duplicate());
    }

    @Override // io.netty.handler.ssl.PemEncoded, io.netty.buffer.ByteBufHolder
    public PemPrivateKey retainedDuplicate() {
        return replace(this.content.retainedDuplicate());
    }

    @Override // io.netty.handler.ssl.PemEncoded, io.netty.buffer.ByteBufHolder
    public PemPrivateKey replace(ByteBuf byteBuf) {
        return new PemPrivateKey(byteBuf);
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public PemPrivateKey touch() {
        this.content.touch();
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public PemPrivateKey touch(Object obj) {
        this.content.touch(obj);
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public PemPrivateKey retain() {
        return (PemPrivateKey) super.retain();
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public PemPrivateKey retain(int i) {
        return (PemPrivateKey) super.retain(i);
    }

    @Override // io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        SslUtils.zerooutAndRelease(this.content);
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.Key
    public String getFormat() {
        return PKCS8_FORMAT;
    }

    @Override // javax.security.auth.Destroyable
    public void destroy() {
        release(refCnt());
    }

    @Override // javax.security.auth.Destroyable
    public boolean isDestroyed() {
        return refCnt() == 0;
    }
}
