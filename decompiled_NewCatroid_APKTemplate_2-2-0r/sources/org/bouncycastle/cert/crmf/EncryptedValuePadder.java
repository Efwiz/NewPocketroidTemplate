package org.bouncycastle.cert.crmf;

/* JADX INFO: loaded from: classes13.dex */
public interface EncryptedValuePadder {
    byte[] getPaddedData(byte[] bArr);

    byte[] getUnpaddedData(byte[] bArr);
}
