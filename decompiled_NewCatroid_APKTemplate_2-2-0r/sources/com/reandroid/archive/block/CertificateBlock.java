package com.reandroid.archive.block;

import com.reandroid.json.JSONObject;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes10.dex */
public interface CertificateBlock {
    byte[] getCertificateBytes();

    void setCertificate(byte[] bArr);

    default X509Certificate getCertificate() {
        return CertificateUtil.generateCertificate(getCertificateBytes());
    }

    default String printCertificate() {
        return CertificateUtil.printCertificate(this);
    }

    default JSONObject toJson() {
        return CertificateUtil.toJson(this);
    }
}
