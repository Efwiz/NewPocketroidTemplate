package com.topjohnwu.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.apache.commons.lang3.CharEncoding;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/* JADX INFO: loaded from: classes11.dex */
public class SignBoot {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static boolean doSignature(String str, InputStream inputStream, OutputStream outputStream, InputStream inputStream2, InputStream inputStream3) {
        try {
            ByteArrayStream byteArrayStream = new ByteArrayStream();
            byteArrayStream.readFrom(inputStream);
            byte[] byteArray = byteArrayStream.toByteArray();
            byteArrayStream.close();
            int signableImageSize = getSignableImageSize(byteArray);
            if (signableImageSize < byteArray.length) {
                System.err.println("NOTE: truncating input from " + byteArray.length + " to " + signableImageSize + " bytes");
                byteArray = Arrays.copyOf(byteArray, signableImageSize);
            } else if (signableImageSize > byteArray.length) {
                throw new IllegalArgumentException("Invalid image: too short, expected " + signableImageSize + " bytes");
            }
            BootSignature bootSignature = new BootSignature(str, byteArray.length);
            if (inputStream2 == null) {
                inputStream2 = SignBoot.class.getResourceAsStream("/keys/testkey.x509.pem");
            }
            bootSignature.setCertificate(CryptoUtils.readCertificate(inputStream2));
            if (inputStream3 == null) {
                inputStream3 = SignBoot.class.getResourceAsStream("/keys/testkey.pk8");
            }
            PrivateKey privateKey = CryptoUtils.readPrivateKey(inputStream3);
            bootSignature.setSignature(bootSignature.sign(byteArray, privateKey), CryptoUtils.getSignatureAlgorithmIdentifier(privateKey));
            byte[] encoded = bootSignature.getEncoded();
            outputStream.write(byteArray);
            outputStream.write(encoded);
            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifySignature(InputStream inputStream, InputStream inputStream2) {
        try {
            ByteArrayStream byteArrayStream = new ByteArrayStream();
            byteArrayStream.readFrom(inputStream);
            byte[] byteArray = byteArrayStream.toByteArray();
            byteArrayStream.close();
            int signableImageSize = getSignableImageSize(byteArray);
            if (signableImageSize >= byteArray.length) {
                System.err.println("Invalid image: not signed");
                return false;
            }
            BootSignature bootSignature = new BootSignature(Arrays.copyOfRange(byteArray, signableImageSize, byteArray.length));
            if (inputStream2 != null) {
                bootSignature.setCertificate(CryptoUtils.readCertificate(inputStream2));
            }
            if (bootSignature.verify(Arrays.copyOf(byteArray, signableImageSize))) {
                System.err.println("Signature is VALID");
                return true;
            }
            System.err.println("Signature is INVALID");
            return false;
        } catch (Exception unused) {
            System.err.println("Invalid image: not signed");
        }
    }

    public static int getSignableImageSize(byte[] bArr) throws Exception {
        if (!Arrays.equals(Arrays.copyOfRange(bArr, 0, 8), "ANDROID!".getBytes(CharEncoding.US_ASCII))) {
            throw new IllegalArgumentException("Invalid image header: missing magic");
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferWrap.getLong();
        int i = byteBufferWrap.getInt();
        byteBufferWrap.getInt();
        int i2 = byteBufferWrap.getInt();
        byteBufferWrap.getInt();
        int i3 = byteBufferWrap.getInt();
        byteBufferWrap.getLong();
        int i4 = byteBufferWrap.getInt();
        int i5 = ((((((((((i + i4) - 1) / i4) * i4) + i4) + ((((i2 + i4) - 1) / i4) * i4)) + ((((i3 + i4) - 1) / i4) * i4)) + i4) - 1) / i4) * i4;
        if (i5 > 0) {
            return i5;
        }
        throw new IllegalArgumentException("Invalid image header: invalid length");
    }

    static class BootSignature extends ASN1Object {
        private static final int FORMAT_VERSION = 1;
        private AlgorithmIdentifier algorithmIdentifier;
        private ASN1Encodable certificate;
        private ASN1Integer formatVersion;
        private ASN1Integer length;
        private PublicKey publicKey;
        private DEROctetString signature;
        private DERPrintableString target;

        public BootSignature(String str, int i) {
            this.formatVersion = new ASN1Integer(1L);
            this.target = new DERPrintableString(str);
            this.length = new ASN1Integer(i);
        }

        public BootSignature(byte[] bArr) throws Exception {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) new ASN1InputStream(bArr).readObject();
            ASN1Integer aSN1Integer = (ASN1Integer) aSN1Sequence.getObjectAt(0);
            this.formatVersion = aSN1Integer;
            if (aSN1Integer.getValue().intValue() != 1) {
                throw new IllegalArgumentException("Unsupported format version");
            }
            ASN1Encodable objectAt = aSN1Sequence.getObjectAt(1);
            this.certificate = objectAt;
            this.publicKey = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(((ASN1Object) objectAt).getEncoded()))).getPublicKey();
            this.algorithmIdentifier = new AlgorithmIdentifier((ASN1ObjectIdentifier) ((ASN1Sequence) aSN1Sequence.getObjectAt(2)).getObjectAt(0));
            ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(3);
            this.target = (DERPrintableString) aSN1Sequence2.getObjectAt(0);
            this.length = (ASN1Integer) aSN1Sequence2.getObjectAt(1);
            this.signature = (DEROctetString) aSN1Sequence.getObjectAt(4);
        }

        public ASN1Object getAuthenticatedAttributes() {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            aSN1EncodableVector.add(this.target);
            aSN1EncodableVector.add(this.length);
            return new DERSequence(aSN1EncodableVector);
        }

        public byte[] getEncodedAuthenticatedAttributes() throws IOException {
            return getAuthenticatedAttributes().getEncoded();
        }

        public void setSignature(byte[] bArr, AlgorithmIdentifier algorithmIdentifier) {
            this.algorithmIdentifier = algorithmIdentifier;
            this.signature = new DEROctetString(bArr);
        }

        public void setCertificate(X509Certificate x509Certificate) throws Exception {
            this.certificate = new ASN1InputStream(x509Certificate.getEncoded()).readObject();
            this.publicKey = x509Certificate.getPublicKey();
        }

        public byte[] generateSignableImage(byte[] bArr) throws IOException {
            byte[] encodedAuthenticatedAttributes = getEncodedAuthenticatedAttributes();
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length + encodedAuthenticatedAttributes.length);
            for (int i = 0; i < encodedAuthenticatedAttributes.length; i++) {
                bArrCopyOf[bArr.length + i] = encodedAuthenticatedAttributes[i];
            }
            return bArrCopyOf;
        }

        public byte[] sign(byte[] bArr, PrivateKey privateKey) throws Exception {
            return CryptoUtils.sign(privateKey, generateSignableImage(bArr));
        }

        public boolean verify(byte[] bArr) throws Exception {
            if (this.length.getValue().intValue() != bArr.length) {
                throw new IllegalArgumentException("Invalid image length");
            }
            return CryptoUtils.verify(this.publicKey, generateSignableImage(bArr), this.signature.getOctets(), this.algorithmIdentifier);
        }

        @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
        public ASN1Primitive toASN1Primitive() {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            aSN1EncodableVector.add(this.formatVersion);
            aSN1EncodableVector.add(this.certificate);
            aSN1EncodableVector.add(this.algorithmIdentifier);
            aSN1EncodableVector.add(getAuthenticatedAttributes());
            aSN1EncodableVector.add(this.signature);
            return new DERSequence(aSN1EncodableVector);
        }
    }
}
