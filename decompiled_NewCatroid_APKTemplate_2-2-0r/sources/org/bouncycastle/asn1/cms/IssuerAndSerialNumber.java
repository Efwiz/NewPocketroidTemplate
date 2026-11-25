package org.bouncycastle.asn1.cms;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.asn1.x509.X509Name;

/* JADX INFO: loaded from: classes13.dex */
public class IssuerAndSerialNumber extends ASN1Object {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private X500Name f485name;
    private ASN1Integer serialNumber;

    public IssuerAndSerialNumber(ASN1Sequence aSN1Sequence) {
        this.f485name = X500Name.getInstance(aSN1Sequence.getObjectAt(0));
        this.serialNumber = (ASN1Integer) aSN1Sequence.getObjectAt(1);
    }

    public IssuerAndSerialNumber(X500Name x500Name, BigInteger bigInteger) {
        this.f485name = x500Name;
        this.serialNumber = new ASN1Integer(bigInteger);
    }

    public IssuerAndSerialNumber(Certificate certificate) {
        this.f485name = certificate.getIssuer();
        this.serialNumber = certificate.getSerialNumber();
    }

    public IssuerAndSerialNumber(X509CertificateStructure x509CertificateStructure) {
        this.f485name = x509CertificateStructure.getIssuer();
        this.serialNumber = x509CertificateStructure.getSerialNumber();
    }

    public IssuerAndSerialNumber(X509Name x509Name, BigInteger bigInteger) {
        this.f485name = X500Name.getInstance(x509Name);
        this.serialNumber = new ASN1Integer(bigInteger);
    }

    public IssuerAndSerialNumber(X509Name x509Name, ASN1Integer aSN1Integer) {
        this.f485name = X500Name.getInstance(x509Name);
        this.serialNumber = aSN1Integer;
    }

    public static IssuerAndSerialNumber getInstance(Object obj) {
        if (obj instanceof IssuerAndSerialNumber) {
            return (IssuerAndSerialNumber) obj;
        }
        if (obj != null) {
            return new IssuerAndSerialNumber(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public X500Name getName() {
        return this.f485name;
    }

    public ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.f485name);
        aSN1EncodableVector.add(this.serialNumber);
        return new DERSequence(aSN1EncodableVector);
    }
}
