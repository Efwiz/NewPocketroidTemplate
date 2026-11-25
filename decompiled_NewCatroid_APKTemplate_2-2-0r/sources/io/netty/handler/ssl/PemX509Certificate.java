package io.netty.handler.ssl;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import org.bouncycastle.jce.provider.X509CertificateObject;

/* JADX INFO: loaded from: classes11.dex */
public final class PemX509Certificate extends X509Certificate implements PemEncoded {
    private static final byte[] BEGIN_CERT = "-----BEGIN CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] END_CERT = "\n-----END CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private final ByteBuf content;

    @Override // io.netty.handler.ssl.PemEncoded
    public boolean isSensitive() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static PemEncoded toPEM(ByteBufAllocator byteBufAllocator, boolean z, X509Certificate... x509CertificateArr) throws CertificateEncodingException {
        if (x509CertificateArr == 0 || x509CertificateArr.length == 0) {
            throw new IllegalArgumentException("X.509 certificate chain can't be null or empty");
        }
        if (x509CertificateArr.length == 1) {
            Object[] objArr = x509CertificateArr[0];
            if (objArr instanceof PemEncoded) {
                return ((PemEncoded) objArr).retain();
            }
        }
        ByteBuf byteBufAppend = null;
        try {
            for (X509CertificateObject x509CertificateObject : x509CertificateArr) {
                if (x509CertificateObject == 0) {
                    throw new IllegalArgumentException("Null element in chain: " + Arrays.toString(x509CertificateArr));
                }
                if (x509CertificateObject instanceof PemEncoded) {
                    byteBufAppend = append(byteBufAllocator, z, (PemEncoded) x509CertificateObject, x509CertificateArr.length, byteBufAppend);
                } else {
                    byteBufAppend = append(byteBufAllocator, z, x509CertificateObject, x509CertificateArr.length, byteBufAppend);
                }
            }
            return new PemValue(byteBufAppend, false);
        } catch (Throwable th) {
            if (0 != 0) {
                byteBufAppend.release();
            }
            throw th;
        }
    }

    private static ByteBuf append(ByteBufAllocator byteBufAllocator, boolean z, PemEncoded pemEncoded, int i, ByteBuf byteBuf) {
        ByteBuf byteBufContent = pemEncoded.content();
        if (byteBuf == null) {
            byteBuf = newBuffer(byteBufAllocator, z, byteBufContent.readableBytes() * i);
        }
        byteBuf.writeBytes(byteBufContent.slice());
        return byteBuf;
    }

    private static ByteBuf append(ByteBufAllocator byteBufAllocator, boolean z, X509Certificate x509Certificate, int i, ByteBuf byteBuf) throws CertificateEncodingException {
        ByteBuf byteBufWrappedBuffer = Unpooled.wrappedBuffer(x509Certificate.getEncoded());
        try {
            ByteBuf base64 = SslUtils.toBase64(byteBufAllocator, byteBufWrappedBuffer);
            if (byteBuf == null) {
                try {
                    byteBuf = newBuffer(byteBufAllocator, z, (BEGIN_CERT.length + base64.readableBytes() + END_CERT.length) * i);
                } catch (Throwable th) {
                    base64.release();
                    throw th;
                }
            }
            byteBuf.writeBytes(BEGIN_CERT);
            byteBuf.writeBytes(base64);
            byteBuf.writeBytes(END_CERT);
            base64.release();
            byteBufWrappedBuffer.release();
            return byteBuf;
        } catch (Throwable th2) {
            byteBufWrappedBuffer.release();
            throw th2;
        }
    }

    private static ByteBuf newBuffer(ByteBufAllocator byteBufAllocator, boolean z, int i) {
        return z ? byteBufAllocator.directBuffer(i) : byteBufAllocator.buffer(i);
    }

    public static PemX509Certificate valueOf(byte[] bArr) {
        return valueOf(Unpooled.wrappedBuffer(bArr));
    }

    public static PemX509Certificate valueOf(ByteBuf byteBuf) {
        return new PemX509Certificate(byteBuf);
    }

    private PemX509Certificate(ByteBuf byteBuf) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, FirebaseAnalytics.Param.CONTENT);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.content.refCnt();
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
    public PemX509Certificate copy() {
        return replace(this.content.copy());
    }

    @Override // io.netty.handler.ssl.PemEncoded, io.netty.buffer.ByteBufHolder
    public PemX509Certificate duplicate() {
        return replace(this.content.duplicate());
    }

    @Override // io.netty.handler.ssl.PemEncoded, io.netty.buffer.ByteBufHolder
    public PemX509Certificate retainedDuplicate() {
        return replace(this.content.retainedDuplicate());
    }

    @Override // io.netty.handler.ssl.PemEncoded, io.netty.buffer.ByteBufHolder
    public PemX509Certificate replace(ByteBuf byteBuf) {
        return new PemX509Certificate(byteBuf);
    }

    @Override // io.netty.util.ReferenceCounted
    public PemX509Certificate retain() {
        this.content.retain();
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public PemX509Certificate retain(int i) {
        this.content.retain(i);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public PemX509Certificate touch() {
        this.content.touch();
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public PemX509Certificate touch(Object obj) {
        this.content.touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return this.content.release();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return this.content.release(i);
    }

    @Override // java.security.cert.Certificate
    public byte[] getEncoded() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Extension
    public Set<String> getCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Extension
    public Set<String> getNonCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity(Date date) {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public int getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public BigInteger getSerialNumber() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public Principal getIssuerDN() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public Principal getSubjectDN() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public Date getNotBefore() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public Date getNotAfter() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getTBSCertificate() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSignature() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public String getSigAlgName() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public String getSigAlgOID() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSigAlgParams() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getIssuerUniqueID() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getSubjectUniqueID() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getKeyUsage() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.X509Certificate
    public int getBasicConstraints() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.Certificate
    public void verify(PublicKey publicKey) {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.Certificate
    public void verify(PublicKey publicKey, String str) {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.Certificate
    public PublicKey getPublicKey() {
        throw new UnsupportedOperationException();
    }

    @Override // java.security.cert.Certificate
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PemX509Certificate) {
            return this.content.equals(((PemX509Certificate) obj).content);
        }
        return false;
    }

    @Override // java.security.cert.Certificate
    public int hashCode() {
        return this.content.hashCode();
    }

    @Override // java.security.cert.Certificate
    public String toString() {
        return this.content.toString(CharsetUtil.UTF_8);
    }
}
