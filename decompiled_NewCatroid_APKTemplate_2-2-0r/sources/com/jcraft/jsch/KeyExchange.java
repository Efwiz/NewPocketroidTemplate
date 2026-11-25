package com.jcraft.jsch;

import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes10.dex */
public abstract class KeyExchange {
    static final int PROPOSAL_COMP_ALGS_CTOS = 6;
    static final int PROPOSAL_COMP_ALGS_STOC = 7;
    static final int PROPOSAL_ENC_ALGS_CTOS = 2;
    static final int PROPOSAL_ENC_ALGS_STOC = 3;
    static final int PROPOSAL_KEX_ALGS = 0;
    static final int PROPOSAL_LANG_CTOS = 8;
    static final int PROPOSAL_LANG_STOC = 9;
    static final int PROPOSAL_MAC_ALGS_CTOS = 4;
    static final int PROPOSAL_MAC_ALGS_STOC = 5;
    static final int PROPOSAL_MAX = 10;
    static final int PROPOSAL_SERVER_HOST_KEY_ALGS = 1;
    public static final int STATE_END = 0;
    static String enc_c2s = "blowfish-cbc";
    static String enc_s2c = "blowfish-cbc";
    static String kex = "diffie-hellman-group1-sha1";
    static String lang_c2s = "";
    static String lang_s2c = "";
    static String mac_c2s = "hmac-md5";
    static String mac_s2c = "hmac-md5";
    static String server_host_key = "ssh-rsa,ssh-dss";
    protected Session session = null;
    protected HASH sha = null;
    protected byte[] K = null;
    protected byte[] H = null;
    protected byte[] K_S = null;
    protected final int RSA = 0;
    protected final int DSS = 1;
    protected final int ECDSA = 2;
    private int type = 0;
    private String key_alg_name = "";

    public abstract int getState();

    public abstract void init(Session session, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception;

    public abstract boolean next(Buffer buffer) throws Exception;

    public String getKeyType() {
        int i = this.type;
        if (i == 1) {
            return "DSA";
        }
        if (i == 0) {
            return "RSA";
        }
        return "ECDSA";
    }

    public String getKeyAlgorithName() {
        return this.key_alg_name;
    }

    protected static String[] guess(byte[] bArr, byte[] bArr2) {
        String[] strArr = new String[10];
        Buffer buffer = new Buffer(bArr);
        buffer.setOffSet(17);
        Buffer buffer2 = new Buffer(bArr2);
        buffer2.setOffSet(17);
        if (JSch.getLogger().isEnabled(1)) {
            for (int i = 0; i < 10; i++) {
                JSch.getLogger().log(1, "kex: server: " + Util.byte2str(buffer.getString()));
            }
            for (int i2 = 0; i2 < 10; i2++) {
                JSch.getLogger().log(1, "kex: client: " + Util.byte2str(buffer2.getString()));
            }
            buffer.setOffSet(17);
            buffer2.setOffSet(17);
        }
        for (int i3 = 0; i3 < 10; i3++) {
            byte[] string = buffer.getString();
            byte[] string2 = buffer2.getString();
            int i4 = 0;
            int i5 = 0;
            while (i4 < string2.length) {
                while (i4 < string2.length && string2[i4] != 44) {
                    i4++;
                }
                if (i5 == i4) {
                    return null;
                }
                String strByte2str = Util.byte2str(string2, i5, i4 - i5);
                int i6 = 0;
                int i7 = 0;
                while (i6 < string.length) {
                    while (i6 < string.length && string[i6] != 44) {
                        i6++;
                    }
                    if (i7 == i6) {
                        return null;
                    }
                    if (strByte2str.equals(Util.byte2str(string, i7, i6 - i7))) {
                        strArr[i3] = strByte2str;
                        break;
                    }
                    i7 = i6 + 1;
                    i6 = i7;
                }
                i5 = i4 + 1;
                i4 = i5;
            }
            if (i4 == 0) {
                strArr[i3] = "";
            } else if (strArr[i3] == null) {
                return null;
            }
        }
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "kex: server->client " + strArr[3] + " " + strArr[5] + " " + strArr[7]);
            JSch.getLogger().log(1, "kex: client->server " + strArr[2] + " " + strArr[4] + " " + strArr[6]);
        }
        return strArr;
    }

    public String getFingerPrint() {
        HASH hash;
        try {
            hash = (HASH) Class.forName(this.session.getConfig("md5")).newInstance();
        } catch (Exception e) {
            System.err.println("getFingerPrint: " + e);
            hash = null;
        }
        return Util.getFingerPrint(hash, getHostKey());
    }

    byte[] getK() {
        return this.K;
    }

    byte[] getH() {
        return this.H;
    }

    HASH getHash() {
        return this.sha;
    }

    byte[] getHostKey() {
        return this.K_S;
    }

    protected byte[] normalize(byte[] bArr) {
        if (bArr.length <= 1 || bArr[0] != 0 || (bArr[1] & 128) != 0) {
            return bArr;
        }
        int length = bArr.length - 1;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 1, bArr2, 0, length);
        return normalize(bArr2);
    }

    protected boolean verify(String str, byte[] bArr, int i, byte[] bArr2) throws Exception {
        SignatureECDSA signatureECDSA;
        SignatureDSA signatureDSA;
        SignatureRSA signatureRSA;
        SignatureECDSA signatureECDSA2 = null;
        SignatureRSA signatureRSA2 = null;
        SignatureDSA signatureDSA2 = null;
        if (str.equals("ssh-rsa")) {
            this.type = 0;
            this.key_alg_name = str;
            int i2 = i + 3;
            int i3 = ((bArr[i + 1] << 16) & 16711680) | ((bArr[i] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[i + 2] << 8) & 65280);
            int i4 = i + 4;
            int i5 = i3 | (bArr[i2] & 255);
            byte[] bArr3 = new byte[i5];
            System.arraycopy(bArr, i4, bArr3, 0, i5);
            int i6 = i4 + i5;
            int i7 = ((bArr[i6 + 1] << 16) & 16711680) | ((-16777216) & (bArr[i6] << 24)) | (65280 & (bArr[i6 + 2] << 8)) | (bArr[i6 + 3] & 255);
            byte[] bArr4 = new byte[i7];
            System.arraycopy(bArr, i6 + 4, bArr4, 0, i7);
            try {
                signatureRSA = (SignatureRSA) Class.forName(this.session.getConfig("signature.rsa")).newInstance();
                try {
                    signatureRSA.init();
                } catch (Exception e) {
                    e = e;
                    signatureRSA2 = signatureRSA;
                    System.err.println(e);
                    signatureRSA = signatureRSA2;
                }
            } catch (Exception e2) {
                e = e2;
            }
            signatureRSA.setPubKey(bArr3, bArr4);
            signatureRSA.update(this.H);
            boolean zVerify = signatureRSA.verify(bArr2);
            if (!JSch.getLogger().isEnabled(1)) {
                return zVerify;
            }
            JSch.getLogger().log(1, "ssh_rsa_verify: signature " + zVerify);
            return zVerify;
        }
        if (str.equals("ssh-dss")) {
            this.type = 1;
            this.key_alg_name = str;
            int i8 = i + 3;
            int i9 = ((bArr[i + 1] << 16) & 16711680) | ((bArr[i] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[i + 2] << 8) & 65280);
            int i10 = i + 4;
            int i11 = i9 | (bArr[i8] & 255);
            byte[] bArr5 = new byte[i11];
            System.arraycopy(bArr, i10, bArr5, 0, i11);
            int i12 = i10 + i11;
            int i13 = i12 + 3;
            int i14 = ((bArr[i12 + 1] << 16) & 16711680) | ((bArr[i12] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[i12 + 2] << 8) & 65280);
            int i15 = i12 + 4;
            int i16 = i14 | (bArr[i13] & 255);
            byte[] bArr6 = new byte[i16];
            System.arraycopy(bArr, i15, bArr6, 0, i16);
            int i17 = i15 + i16;
            int i18 = i17 + 3;
            int i19 = ((bArr[i17 + 1] << 16) & 16711680) | ((bArr[i17] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[i17 + 2] << 8) & 65280);
            int i20 = i17 + 4;
            int i21 = i19 | (bArr[i18] & 255);
            byte[] bArr7 = new byte[i21];
            System.arraycopy(bArr, i20, bArr7, 0, i21);
            int i22 = i20 + i21;
            int i23 = ((bArr[i22 + 1] << 16) & 16711680) | ((-16777216) & (bArr[i22] << 24)) | (65280 & (bArr[i22 + 2] << 8)) | (bArr[i22 + 3] & 255);
            byte[] bArr8 = new byte[i23];
            System.arraycopy(bArr, i22 + 4, bArr8, 0, i23);
            try {
                signatureDSA = (SignatureDSA) Class.forName(this.session.getConfig("signature.dss")).newInstance();
                try {
                    signatureDSA.init();
                } catch (Exception e3) {
                    e = e3;
                    signatureDSA2 = signatureDSA;
                    System.err.println(e);
                    signatureDSA = signatureDSA2;
                }
            } catch (Exception e4) {
                e = e4;
            }
            signatureDSA.setPubKey(bArr8, bArr5, bArr6, bArr7);
            signatureDSA.update(this.H);
            boolean zVerify2 = signatureDSA.verify(bArr2);
            if (!JSch.getLogger().isEnabled(1)) {
                return zVerify2;
            }
            JSch.getLogger().log(1, "ssh_dss_verify: signature " + zVerify2);
            return zVerify2;
        }
        if (str.equals("ecdsa-sha2-nistp256") || str.equals("ecdsa-sha2-nistp384") || str.equals("ecdsa-sha2-nistp521")) {
            this.type = 2;
            this.key_alg_name = str;
            int i24 = i + 3;
            int i25 = ((bArr[i + 1] << 16) & 16711680) | ((bArr[i] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[i + 2] << 8) & 65280);
            int i26 = i + 4;
            int i27 = i25 | (bArr[i24] & 255);
            System.arraycopy(bArr, i26, new byte[i27], 0, i27);
            int i28 = i26 + i27;
            int i29 = (65280 & (bArr[i28 + 2] << 8)) | (16711680 & (bArr[i28 + 1] << 16)) | ((-16777216) & (bArr[i28] << 24)) | (bArr[i28 + 3] & 255);
            int i30 = i28 + 5;
            int i31 = (i29 - 1) / 2;
            byte[] bArr9 = new byte[i31];
            System.arraycopy(bArr, i30, bArr9, 0, i31);
            byte[] bArr10 = new byte[i31];
            System.arraycopy(bArr, i30 + i31, bArr10, 0, i31);
            try {
                signatureECDSA = (SignatureECDSA) Class.forName(this.session.getConfig(str)).newInstance();
                try {
                    signatureECDSA.init();
                } catch (Exception e5) {
                    e = e5;
                    signatureECDSA2 = signatureECDSA;
                    System.err.println(e);
                    signatureECDSA = signatureECDSA2;
                }
            } catch (Exception e6) {
                e = e6;
            }
            signatureECDSA.setPubKey(bArr9, bArr10);
            signatureECDSA.update(this.H);
            return signatureECDSA.verify(bArr2);
        }
        System.err.println("unknown alg");
        return false;
    }
}
