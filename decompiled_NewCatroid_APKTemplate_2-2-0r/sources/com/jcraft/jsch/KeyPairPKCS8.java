package com.jcraft.jsch;

import java.math.BigInteger;
import java.util.Vector;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes10.dex */
public class KeyPairPKCS8 extends KeyPair {
    private KeyPair kpair;
    private static final byte[] rsaEncryption = {42, Opcodes.OPC_i2f, 72, Opcodes.OPC_i2f, -9, 13, 1, 1, 1};
    private static final byte[] dsaEncryption = {42, Opcodes.OPC_i2f, 72, WasmBinary.REC_GROUP, 56, 4, 1};
    private static final byte[] pbes2 = {42, Opcodes.OPC_i2f, 72, Opcodes.OPC_i2f, -9, 13, 1, 5, 13};
    private static final byte[] pbkdf2 = {42, Opcodes.OPC_i2f, 72, Opcodes.OPC_i2f, -9, 13, 1, 5, 12};
    private static final byte[] aes128cbc = {96, Opcodes.OPC_i2f, 72, 1, 101, 3, 4, 1, 2};
    private static final byte[] aes192cbc = {96, Opcodes.OPC_i2f, 72, 1, 101, 3, 4, 1, 22};
    private static final byte[] aes256cbc = {96, Opcodes.OPC_i2f, 72, 1, 101, 3, 4, 1, 42};
    private static final byte[] pbeWithMD5AndDESCBC = {42, Opcodes.OPC_i2f, 72, Opcodes.OPC_i2f, -9, 13, 1, 5, 3};
    private static final byte[] begin = Util.str2byte("-----BEGIN DSA PRIVATE KEY-----");
    private static final byte[] end = Util.str2byte("-----END DSA PRIVATE KEY-----");

    @Override // com.jcraft.jsch.KeyPair
    void generate(int i) throws JSchException {
    }

    @Override // com.jcraft.jsch.KeyPair
    byte[] getPrivateKey() {
        return null;
    }

    public KeyPairPKCS8(JSch jSch) {
        super(jSch);
        this.kpair = null;
    }

    @Override // com.jcraft.jsch.KeyPair
    byte[] getBegin() {
        return begin;
    }

    @Override // com.jcraft.jsch.KeyPair
    byte[] getEnd() {
        return end;
    }

    @Override // com.jcraft.jsch.KeyPair
    boolean parse(byte[] bArr) {
        try {
            Vector vector = new Vector();
            KeyPair.ASN1[] contents = new KeyPair.ASN1(this, bArr).getContents();
            KeyPair.ASN1 asn1 = contents[1];
            KeyPair.ASN1 asn2 = contents[2];
            KeyPair.ASN1[] contents2 = asn1.getContents();
            byte[] content = contents2[0].getContent();
            KeyPair.ASN1[] contents3 = contents2[1].getContents();
            if (contents3.length > 0) {
                for (KeyPair.ASN1 asn3 : contents3) {
                    vector.addElement(asn3.getContent());
                }
            }
            byte[] content2 = asn2.getContent();
            if (Util.array_equals(content, rsaEncryption)) {
                KeyPairRSA keyPairRSA = new KeyPairRSA(this.jsch);
                keyPairRSA.copy(this);
                if (keyPairRSA.parse(content2)) {
                    this.kpair = keyPairRSA;
                }
            } else if (Util.array_equals(content, dsaEncryption)) {
                KeyPair.ASN1 asn4 = new KeyPair.ASN1(this, content2);
                if (vector.size() == 0) {
                    KeyPair.ASN1[] contents4 = asn4.getContents();
                    byte[] content3 = contents4[1].getContent();
                    for (KeyPair.ASN1 asn5 : contents4[0].getContents()) {
                        vector.addElement(asn5.getContent());
                    }
                    vector.addElement(content3);
                } else {
                    vector.addElement(asn4.getContent());
                }
                byte[] bArr2 = (byte[]) vector.elementAt(0);
                byte[] bArr3 = (byte[]) vector.elementAt(1);
                byte[] bArr4 = (byte[]) vector.elementAt(2);
                byte[] bArr5 = (byte[]) vector.elementAt(3);
                byte[] privateKey = new KeyPairDSA(this.jsch, bArr2, bArr3, bArr4, new BigInteger(bArr4).modPow(new BigInteger(bArr5), new BigInteger(bArr2)).toByteArray(), bArr5).getPrivateKey();
                KeyPairDSA keyPairDSA = new KeyPairDSA(this.jsch);
                keyPairDSA.copy(this);
                if (keyPairDSA.parse(privateKey)) {
                    this.kpair = keyPairDSA;
                }
            }
            return this.kpair != null;
        } catch (KeyPair.ASN1Exception | Exception unused) {
            return false;
        }
    }

    @Override // com.jcraft.jsch.KeyPair
    public byte[] getPublicKeyBlob() {
        return this.kpair.getPublicKeyBlob();
    }

    @Override // com.jcraft.jsch.KeyPair
    byte[] getKeyTypeName() {
        return this.kpair.getKeyTypeName();
    }

    @Override // com.jcraft.jsch.KeyPair
    public int getKeyType() {
        return this.kpair.getKeyType();
    }

    @Override // com.jcraft.jsch.KeyPair
    public int getKeySize() {
        return this.kpair.getKeySize();
    }

    @Override // com.jcraft.jsch.KeyPair
    public byte[] getSignature(byte[] bArr) {
        return this.kpair.getSignature(bArr);
    }

    @Override // com.jcraft.jsch.KeyPair
    public Signature getVerifier() {
        return this.kpair.getVerifier();
    }

    @Override // com.jcraft.jsch.KeyPair
    public byte[] forSSHAgent() throws JSchException {
        return this.kpair.forSSHAgent();
    }

    @Override // com.jcraft.jsch.KeyPair
    public boolean decrypt(byte[] bArr) {
        byte[] key;
        if (!isEncrypted()) {
            return true;
        }
        if (bArr == null) {
            return !isEncrypted();
        }
        try {
            KeyPair.ASN1[] contents = new KeyPair.ASN1(this, this.data).getContents();
            byte[] content = contents[1].getContent();
            KeyPair.ASN1[] contents2 = contents[0].getContents();
            byte[] content2 = contents2[0].getContent();
            KeyPair.ASN1 asn1 = contents2[1];
            if (Util.array_equals(content2, pbes2)) {
                KeyPair.ASN1[] contents3 = asn1.getContents();
                KeyPair.ASN1 asn2 = contents3[0];
                KeyPair.ASN1 asn3 = contents3[1];
                KeyPair.ASN1[] contents4 = asn2.getContents();
                contents4[0].getContent();
                KeyPair.ASN1[] contents5 = contents4[1].getContents();
                byte[] content3 = contents5[0].getContent();
                int i = Integer.parseInt(new BigInteger(contents5[1].getContent()).toString());
                KeyPair.ASN1[] contents6 = asn3.getContents();
                byte[] content4 = contents6[0].getContent();
                byte[] content5 = contents6[1].getContent();
                Cipher cipher = getCipher(content4);
                if (cipher == null) {
                    return false;
                }
                try {
                    JSch jSch = this.jsch;
                    PBKDF pbkdf = (PBKDF) Class.forName(JSch.getConfig("pbkdf")).newInstance();
                    key = pbkdf.getKey(bArr, content3, i, cipher.getBlockSize());
                } catch (Exception unused) {
                    key = null;
                }
                if (key == null) {
                    return false;
                }
                cipher.init(1, key, content5);
                Util.bzero(key);
                byte[] bArr2 = new byte[content.length];
                cipher.update(content, 0, content.length, bArr2, 0);
                if (parse(bArr2)) {
                    this.encrypted = false;
                    return true;
                }
            } else {
                Util.array_equals(content2, pbeWithMD5AndDESCBC);
            }
        } catch (KeyPair.ASN1Exception | Exception unused2) {
        }
        return false;
    }

    Cipher getCipher(byte[] bArr) {
        String str;
        String str2;
        try {
            if (Util.array_equals(bArr, aes128cbc)) {
                str = "aes128-cbc";
            } else if (Util.array_equals(bArr, aes192cbc)) {
                str = "aes192-cbc";
            } else {
                str = Util.array_equals(bArr, aes256cbc) ? "aes256-cbc" : null;
            }
            try {
                JSch jSch = this.jsch;
                Cipher cipher = (Cipher) Class.forName(JSch.getConfig(str)).newInstance();
                return cipher;
            } catch (Exception unused) {
                if (!JSch.getLogger().isEnabled(4)) {
                    return null;
                }
                if (str == null) {
                    str2 = "unknown oid: " + Util.toHex(bArr);
                } else {
                    str2 = "function " + str + " is not supported";
                }
                JSch.getLogger().log(4, "PKCS8: " + str2);
                return null;
            }
        } catch (Exception unused2) {
            str = null;
        }
    }
}
