package com.jcraft.jsch;

import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import com.reandroid.dex.sections.Marker;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;

/* JADX INFO: loaded from: classes10.dex */
public abstract class KeyPair {
    public static final int DSA = 1;
    public static final int ECDSA = 3;
    public static final int ERROR = 0;
    public static final int RSA = 2;
    public static final int UNKNOWN = 4;
    static final int VENDOR_FSECURE = 1;
    static final int VENDOR_OPENSSH = 0;
    static final int VENDOR_PKCS8 = 3;
    static final int VENDOR_PUTTY = 2;
    private Cipher cipher;
    private HASH hash;
    JSch jsch;
    private byte[] passphrase;
    private Random random;
    private static final byte[] cr = Util.str2byte("\n");
    static byte[][] header = {Util.str2byte("Proc-Type: 4,ENCRYPTED"), Util.str2byte("DEK-Info: DES-EDE3-CBC,")};
    private static byte[] space = Util.str2byte(" ");
    private static final String[] header1 = {"PuTTY-User-Key-File-2: ", "Encryption: ", "Comment: ", "Public-Lines: "};
    private static final String[] header2 = {"Private-Lines: "};
    private static final String[] header3 = {"Private-MAC: "};
    int vendor = 0;
    protected String publicKeyComment = "no comment";
    protected boolean encrypted = false;
    protected byte[] data = null;
    private byte[] iv = null;
    private byte[] publickeyblob = null;

    private static byte a2b(byte b) {
        return (byte) ((48 > b || b > 57) ? b + Opcodes.OPC_ret : b - 48);
    }

    private static byte b2a(byte b) {
        return (byte) ((b < 0 || b > 9) ? b + 55 : b + 48);
    }

    int countLength(int i) {
        int i2 = 1;
        if (i <= 127) {
            return 1;
        }
        while (i > 0) {
            i >>>= 8;
            i2++;
        }
        return i2;
    }

    public abstract byte[] forSSHAgent() throws JSchException;

    abstract void generate(int i) throws JSchException;

    abstract byte[] getBegin();

    abstract byte[] getEnd();

    abstract int getKeySize();

    public abstract int getKeyType();

    abstract byte[] getKeyTypeName();

    abstract byte[] getPrivateKey();

    public abstract byte[] getSignature(byte[] bArr);

    public abstract Signature getVerifier();

    abstract boolean parse(byte[] bArr);

    public static KeyPair genKeyPair(JSch jSch, int i) throws JSchException {
        return genKeyPair(jSch, i, 1024);
    }

    public static KeyPair genKeyPair(JSch jSch, int i, int i2) throws JSchException {
        KeyPair keyPairECDSA;
        if (i == 1) {
            keyPairECDSA = new KeyPairDSA(jSch);
        } else if (i == 2) {
            keyPairECDSA = new KeyPairRSA(jSch);
        } else {
            keyPairECDSA = i == 3 ? new KeyPairECDSA(jSch) : null;
        }
        if (keyPairECDSA != null) {
            keyPairECDSA.generate(i2);
        }
        return keyPairECDSA;
    }

    public String getPublicKeyComment() {
        return this.publicKeyComment;
    }

    public void setPublicKeyComment(String str) {
        this.publicKeyComment = str;
    }

    public KeyPair(JSch jSch) {
        this.jsch = jSch;
    }

    public void writePrivateKey(OutputStream outputStream) {
        writePrivateKey(outputStream, (byte[]) null);
    }

    public void writePrivateKey(OutputStream outputStream, byte[] bArr) {
        if (bArr == null) {
            bArr = this.passphrase;
        }
        byte[] privateKey = getPrivateKey();
        byte[][] bArr2 = new byte[1][];
        byte[] bArrEncrypt = encrypt(privateKey, bArr2, bArr);
        if (bArrEncrypt != privateKey) {
            Util.bzero(privateKey);
        }
        int i = 0;
        byte[] bArr3 = bArr2[0];
        byte[] base64 = Util.toBase64(bArrEncrypt, 0, bArrEncrypt.length);
        try {
            outputStream.write(getBegin());
            byte[] bArr4 = cr;
            outputStream.write(bArr4);
            if (bArr != null) {
                outputStream.write(header[0]);
                outputStream.write(bArr4);
                outputStream.write(header[1]);
                for (int i2 = 0; i2 < bArr3.length; i2++) {
                    outputStream.write(b2a((byte) ((bArr3[i2] >>> 4) & 15)));
                    outputStream.write(b2a((byte) (bArr3[i2] & 15)));
                }
                byte[] bArr5 = cr;
                outputStream.write(bArr5);
                outputStream.write(bArr5);
            }
            while (i < base64.length) {
                int i3 = i + 64;
                if (i3 < base64.length) {
                    outputStream.write(base64, i, 64);
                    outputStream.write(cr);
                    i = i3;
                } else {
                    outputStream.write(base64, i, base64.length - i);
                    outputStream.write(cr);
                    break;
                }
            }
            outputStream.write(getEnd());
            outputStream.write(cr);
        } catch (Exception unused) {
        }
    }

    public byte[] getPublicKeyBlob() {
        return this.publickeyblob;
    }

    public void writePublicKey(OutputStream outputStream, String str) {
        byte[] publicKeyBlob = getPublicKeyBlob();
        byte[] base64 = Util.toBase64(publicKeyBlob, 0, publicKeyBlob.length);
        try {
            outputStream.write(getKeyTypeName());
            outputStream.write(space);
            outputStream.write(base64, 0, base64.length);
            outputStream.write(space);
            outputStream.write(Util.str2byte(str));
            outputStream.write(cr);
        } catch (Exception unused) {
        }
    }

    public void writePublicKey(String str, String str2) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        writePublicKey(fileOutputStream, str2);
        fileOutputStream.close();
    }

    public void writeSECSHPublicKey(OutputStream outputStream, String str) {
        byte[] publicKeyBlob = getPublicKeyBlob();
        int i = 0;
        byte[] base64 = Util.toBase64(publicKeyBlob, 0, publicKeyBlob.length);
        try {
            outputStream.write(Util.str2byte("---- BEGIN SSH2 PUBLIC KEY ----"));
            byte[] bArr = cr;
            outputStream.write(bArr);
            outputStream.write(Util.str2byte("Comment: \"" + str + "\""));
            outputStream.write(bArr);
            while (i < base64.length) {
                int length = 70;
                if (base64.length - i < 70) {
                    length = base64.length - i;
                }
                outputStream.write(base64, i, length);
                outputStream.write(cr);
                i += length;
            }
            outputStream.write(Util.str2byte("---- END SSH2 PUBLIC KEY ----"));
            outputStream.write(cr);
        } catch (Exception unused) {
        }
    }

    public void writeSECSHPublicKey(String str, String str2) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        writeSECSHPublicKey(fileOutputStream, str2);
        fileOutputStream.close();
    }

    public void writePrivateKey(String str) throws IOException {
        writePrivateKey(str, (byte[]) null);
    }

    public void writePrivateKey(String str, byte[] bArr) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        writePrivateKey(fileOutputStream, bArr);
        fileOutputStream.close();
    }

    public String getFingerPrint() {
        if (this.hash == null) {
            this.hash = genHash();
        }
        byte[] publicKeyBlob = getPublicKeyBlob();
        if (publicKeyBlob == null) {
            return null;
        }
        return Util.getFingerPrint(this.hash, publicKeyBlob);
    }

    private byte[] encrypt(byte[] bArr, byte[][] bArr2, byte[] bArr3) {
        if (bArr3 == null) {
            return bArr;
        }
        if (this.cipher == null) {
            this.cipher = genCipher();
        }
        int iVSize = this.cipher.getIVSize();
        byte[] bArr4 = new byte[iVSize];
        bArr2[0] = bArr4;
        if (this.random == null) {
            this.random = genRandom();
        }
        this.random.fill(bArr4, 0, iVSize);
        byte[] bArrGenKey = genKey(bArr3, bArr4);
        int iVSize2 = this.cipher.getIVSize();
        int length = ((bArr.length / iVSize2) + 1) * iVSize2;
        byte[] bArr5 = new byte[length];
        System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        int length2 = iVSize2 - (bArr.length % iVSize2);
        for (int i = length - 1; length - length2 <= i; i--) {
            bArr5[i] = (byte) length2;
        }
        try {
            this.cipher.init(0, bArrGenKey, bArr4);
            this.cipher.update(bArr5, 0, length, bArr5, 0);
        } catch (Exception unused) {
        }
        Util.bzero(bArrGenKey);
        return bArr5;
    }

    private byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            byte[] bArrGenKey = genKey(bArr2, bArr3);
            this.cipher.init(1, bArrGenKey, bArr3);
            Util.bzero(bArrGenKey);
            byte[] bArr4 = new byte[bArr.length];
            this.cipher.update(bArr, 0, bArr.length, bArr4, 0);
            return bArr4;
        } catch (Exception unused) {
            return null;
        }
    }

    int writeSEQUENCE(byte[] bArr, int i, int i2) {
        bArr[i] = 48;
        return writeLength(bArr, i + 1, i2);
    }

    int writeINTEGER(byte[] bArr, int i, byte[] bArr2) {
        bArr[i] = 2;
        int iWriteLength = writeLength(bArr, i + 1, bArr2.length);
        System.arraycopy(bArr2, 0, bArr, iWriteLength, bArr2.length);
        return iWriteLength + bArr2.length;
    }

    int writeOCTETSTRING(byte[] bArr, int i, byte[] bArr2) {
        bArr[i] = 4;
        int iWriteLength = writeLength(bArr, i + 1, bArr2.length);
        System.arraycopy(bArr2, 0, bArr, iWriteLength, bArr2.length);
        return iWriteLength + bArr2.length;
    }

    int writeDATA(byte[] bArr, byte b, int i, byte[] bArr2) {
        bArr[i] = b;
        int iWriteLength = writeLength(bArr, i + 1, bArr2.length);
        System.arraycopy(bArr2, 0, bArr, iWriteLength, bArr2.length);
        return iWriteLength + bArr2.length;
    }

    int writeLength(byte[] bArr, int i, int i2) {
        int iCountLength = countLength(i2) - 1;
        if (iCountLength == 0) {
            int i3 = i + 1;
            bArr[i] = (byte) i2;
            return i3;
        }
        int i4 = i + 1;
        bArr[i] = (byte) (iCountLength | 128);
        int i5 = i4 + iCountLength;
        while (iCountLength > 0) {
            bArr[(i4 + iCountLength) - 1] = (byte) (i2 & 255);
            i2 >>>= 8;
            iCountLength--;
        }
        return i5;
    }

    private Random genRandom() {
        if (this.random == null) {
            try {
                Random random = (Random) Class.forName(JSch.getConfig("random")).newInstance();
                this.random = random;
            } catch (Exception e) {
                System.err.println("connect: random " + e);
            }
        }
        return this.random;
    }

    private HASH genHash() {
        try {
            HASH hash = (HASH) Class.forName(JSch.getConfig("md5")).newInstance();
            this.hash = hash;
            hash.init();
        } catch (Exception unused) {
        }
        return this.hash;
    }

    private Cipher genCipher() {
        try {
            Cipher cipher = (Cipher) Class.forName(JSch.getConfig("3des-cbc")).newInstance();
            this.cipher = cipher;
        } catch (Exception unused) {
        }
        return this.cipher;
    }

    synchronized byte[] genKey(byte[] bArr, byte[] bArr2) {
        byte[] bArr3;
        if (this.cipher == null) {
            this.cipher = genCipher();
        }
        if (this.hash == null) {
            this.hash = genHash();
        }
        int blockSize = this.cipher.getBlockSize();
        bArr3 = new byte[blockSize];
        int blockSize2 = this.hash.getBlockSize();
        int i = ((blockSize / blockSize2) * blockSize2) + (blockSize % blockSize2 == 0 ? 0 : blockSize2);
        byte[] bArr4 = new byte[i];
        try {
            int i2 = this.vendor;
            byte[] bArrDigest = null;
            if (i2 == 0) {
                int length = 0;
                while (length + blockSize2 <= i) {
                    if (bArrDigest != null) {
                        this.hash.update(bArrDigest, 0, bArrDigest.length);
                    }
                    this.hash.update(bArr, 0, bArr.length);
                    HASH hash = this.hash;
                    int length2 = 8;
                    if (bArr2.length <= 8) {
                        length2 = bArr2.length;
                    }
                    hash.update(bArr2, 0, length2);
                    bArrDigest = this.hash.digest();
                    System.arraycopy(bArrDigest, 0, bArr4, length, bArrDigest.length);
                    length += bArrDigest.length;
                }
                System.arraycopy(bArr4, 0, bArr3, 0, blockSize);
            } else if (i2 == 1) {
                int length3 = 0;
                while (length3 + blockSize2 <= i) {
                    if (bArrDigest != null) {
                        this.hash.update(bArrDigest, 0, bArrDigest.length);
                    }
                    this.hash.update(bArr, 0, bArr.length);
                    bArrDigest = this.hash.digest();
                    System.arraycopy(bArrDigest, 0, bArr4, length3, bArrDigest.length);
                    length3 += bArrDigest.length;
                }
                System.arraycopy(bArr4, 0, bArr3, 0, blockSize);
            } else if (i2 == 2) {
                HASH hash2 = (HASH) Class.forName(JSch.getConfig(Marker.SHA1)).newInstance();
                byte[] bArr5 = new byte[4];
                bArr3 = new byte[40];
                for (int i3 = 0; i3 < 2; i3++) {
                    hash2.init();
                    bArr5[3] = (byte) i3;
                    hash2.update(bArr5, 0, 4);
                    hash2.update(bArr, 0, bArr.length);
                    System.arraycopy(hash2.digest(), 0, bArr3, i3 * 20, 20);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return bArr3;
    }

    public void setPassphrase(String str) {
        if (str == null || str.length() == 0) {
            setPassphrase((byte[]) null);
        } else {
            setPassphrase(Util.str2byte(str));
        }
    }

    public void setPassphrase(byte[] bArr) {
        if (bArr != null && bArr.length == 0) {
            bArr = null;
        }
        this.passphrase = bArr;
    }

    public boolean isEncrypted() {
        return this.encrypted;
    }

    public boolean decrypt(String str) {
        if (str == null || str.length() == 0) {
            return !this.encrypted;
        }
        return decrypt(Util.str2byte(str));
    }

    public boolean decrypt(byte[] bArr) {
        boolean z = this.encrypted;
        if (!z) {
            return true;
        }
        if (bArr == null) {
            return !z;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        byte[] bArrDecrypt = decrypt(this.data, bArr2, this.iv);
        Util.bzero(bArr2);
        if (parse(bArrDecrypt)) {
            this.encrypted = false;
        }
        return !this.encrypted;
    }

    public static KeyPair load(JSch jSch, String str) throws JSchException {
        String str2 = str + ".pub";
        if (!new File(str2).exists()) {
            str2 = null;
        }
        return load(jSch, str, str2);
    }

    public static KeyPair load(JSch jSch, String str, String str2) throws JSchException {
        byte[] bArrFromFile;
        try {
            byte[] bArrFromFile2 = Util.fromFile(str);
            try {
                bArrFromFile = Util.fromFile(str2 == null ? str + ".pub" : str2);
            } catch (IOException e) {
                if (str2 != null) {
                    throw new JSchException(e.toString(), e);
                }
                bArrFromFile = null;
            }
            try {
                return load(jSch, bArrFromFile2, bArrFromFile);
            } finally {
                Util.bzero(bArrFromFile2);
            }
        } catch (IOException e2) {
            throw new JSchException(e2.toString(), e2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:325:0x0523  */
    /* JADX WARN: Code duplicated, block: B:394:0x05c7  */
    /* JADX WARN: Code duplicated, block: B:447:0x064f  */
    /* JADX WARN: Code duplicated, block: B:449:0x0653  */
    /* JADX WARN: Code duplicated, block: B:493:0x06bf A[PHI: r16
      0x06bf: PHI (r16v4 ??) = (r16v3 ??), (r16v2 ??), (r16v6 ??), (r16v6 ??), (r16v6 ??), (r16v6 ??), (r16v11 ??) binds: [B:492:0x06bd, B:520:0x06bf, B:448:0x0651, B:451:0x0658, B:453:0x065f, B:455:0x0664, B:381:0x05a9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v10 */
    /* JADX WARN: Type inference failed for: r16v11 */
    /* JADX WARN: Type inference failed for: r16v18 */
    /* JADX WARN: Type inference failed for: r16v19 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v20 */
    /* JADX WARN: Type inference failed for: r16v21 */
    /* JADX WARN: Type inference failed for: r16v22 */
    /* JADX WARN: Type inference failed for: r16v23 */
    /* JADX WARN: Type inference failed for: r16v24 */
    /* JADX WARN: Type inference failed for: r16v25 */
    /* JADX WARN: Type inference failed for: r16v26 */
    /* JADX WARN: Type inference failed for: r16v27 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r16v5 */
    /* JADX WARN: Type inference failed for: r16v6 */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r16v8 */
    /* JADX WARN: Type inference failed for: r16v9 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v118 */
    /* JADX WARN: Type inference failed for: r6v29 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v32 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v54, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r6v55 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v15, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v3 */
    public static KeyPair load(JSch jSch, byte[] bArr, byte[] bArr2) throws JSchException {
        byte[] bArrFromBase64;
        ?? r3;
        byte[] bArrFromBase65;
        ?? r8;
        ?? FromBase64;
        String str;
        ?? r6;
        KeyPair keyPairPKCS8;
        int i;
        byte b;
        int i2;
        byte b2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        char c;
        int i10;
        byte b3;
        byte[] bArr3 = new byte[8];
        if (bArr2 == 0 && bArr != null && bArr.length > 11 && bArr[0] == 0 && bArr[1] == 0 && bArr[2] == 0 && ((b3 = bArr[3]) == 7 || b3 == 19)) {
            Buffer buffer = new Buffer(bArr);
            buffer.skip(bArr.length);
            String str2 = new String(buffer.getString());
            buffer.rewind();
            if (str2.equals("ssh-rsa")) {
                return KeyPairRSA.fromSSHAgent(jSch, buffer);
            }
            if (str2.equals("ssh-dss")) {
                return KeyPairDSA.fromSSHAgent(jSch, buffer);
            }
            if (str2.equals("ecdsa-sha2-nistp256") || str2.equals("ecdsa-sha2-nistp384") || str2.equals("ecdsa-sha2-nistp521")) {
                return KeyPairECDSA.fromSSHAgent(jSch, buffer);
            }
            throw new JSchException("privatekey: invalid key ".concat(new String(bArr, 4, 7)));
        }
        if (bArr != null) {
            try {
                KeyPair keyPairLoadPPK = loadPPK(jSch, bArr);
                if (keyPairLoadPPK != null) {
                    return keyPairLoadPPK;
                }
            } catch (Exception e) {
                if (e instanceof JSchException) {
                    throw ((JSchException) e);
                }
                if (e instanceof Throwable) {
                    throw new JSchException(e.toString(), e);
                }
                throw new JSchException(e.toString());
            }
        }
        int length = bArr != null ? bArr.length : 0;
        int i11 = 0;
        while (i11 < length && (bArr[i11] != 45 || (i10 = i11 + 4) >= length || bArr[i11 + 1] != 45 || bArr[i11 + 2] != 45 || bArr[i11 + 3] != 45 || bArr[i10] != 45)) {
            i11++;
        }
        ?? str3 = 1;
        int i12 = 0;
        char c2 = 0;
        Cipher cipher = null;
        loop1: while (i11 < length) {
            byte b4 = bArr[i11];
            if (b4 == 66 && (i9 = i11 + 3) < length && bArr[i11 + 1] == 69 && bArr[i11 + 2] == 71 && bArr[i9] == 73) {
                int i13 = i11 + 6;
                int i14 = i11 + 8;
                if (i14 >= length) {
                    throw new JSchException("invalid privatekey: " + bArr);
                }
                byte b5 = bArr[i13];
                if (b5 == 68 && bArr[i11 + 7] == 83 && bArr[i14] == 65) {
                    c = 3;
                    c2 = 1;
                    str3 = str3;
                } else if (b5 == 82 && bArr[i11 + 7] == 83 && bArr[i14] == 65) {
                    c = 3;
                    c2 = 2;
                    str3 = str3;
                } else if (b5 == 69 && bArr[i11 + 7] == 67) {
                    c = 3;
                    c2 = 3;
                    str3 = str3;
                } else {
                    if (b5 != 83 || bArr[i11 + 7] != 83 || bArr[i14] != 72) {
                        int i15 = i11 + 12;
                        if (i15 < length && b5 == 80 && bArr[i11 + 7] == 82 && bArr[i14] == 73) {
                            i13 = i11 + 9;
                            if (bArr[i13] == 86 && bArr[i11 + 10] == 65 && bArr[i11 + 11] == 84 && bArr[i15] == 69) {
                                c = 3;
                                i12 = 3;
                                c2 = 4;
                                str3 = 0;
                            }
                        }
                        int i16 = i11 + 14;
                        if (i16 < length && b5 == 69 && bArr[i11 + 7] == 78 && bArr[i14] == 67 && bArr[i11 + 9] == 82 && bArr[i11 + 10] == 89) {
                            i13 = i11 + 11;
                            if (bArr[i13] == 80 && bArr[i15] == 84 && bArr[i11 + 13] == 69 && bArr[i16] == 68) {
                                c = 3;
                                i12 = 3;
                            }
                        }
                        throw new JSchException("invalid privatekey: " + bArr);
                    }
                    c = 3;
                    i12 = 1;
                    c2 = 4;
                    str3 = str3;
                }
                i11 = i13 + 3;
            } else if (b4 == 65 && (i8 = i11 + 7) < length && bArr[i11 + 1] == 69 && bArr[i11 + 2] == 83 && bArr[i11 + 3] == 45 && bArr[i11 + 4] == 50 && bArr[i11 + 5] == 53 && bArr[i11 + 6] == 54 && bArr[i8] == 45) {
                i11 += 8;
                if (Session.checkCipher(JSch.getConfig("aes256-cbc"))) {
                    cipher = (Cipher) Class.forName(JSch.getConfig("aes256-cbc")).newInstance();
                    bArr3 = new byte[cipher.getIVSize()];
                } else {
                    throw new JSchException("privatekey: aes256-cbc is not available " + bArr);
                }
            } else if (b4 == 65 && (i7 = i11 + 7) < length && bArr[i11 + 1] == 69 && bArr[i11 + 2] == 83 && bArr[i11 + 3] == 45 && bArr[i11 + 4] == 49 && bArr[i11 + 5] == 57 && bArr[i11 + 6] == 50 && bArr[i7] == 45) {
                i11 += 8;
                if (Session.checkCipher(JSch.getConfig("aes192-cbc"))) {
                    cipher = (Cipher) Class.forName(JSch.getConfig("aes192-cbc")).newInstance();
                    bArr3 = new byte[cipher.getIVSize()];
                } else {
                    throw new JSchException("privatekey: aes192-cbc is not available " + bArr);
                }
            } else if (b4 == 65 && (i6 = i11 + 7) < length && bArr[i11 + 1] == 69 && bArr[i11 + 2] == 83 && bArr[i11 + 3] == 45 && bArr[i11 + 4] == 49 && bArr[i11 + 5] == 50 && bArr[i11 + 6] == 56 && bArr[i6] == 45) {
                i11 += 8;
                if (Session.checkCipher(JSch.getConfig("aes128-cbc"))) {
                    cipher = (Cipher) Class.forName(JSch.getConfig("aes128-cbc")).newInstance();
                    bArr3 = new byte[cipher.getIVSize()];
                } else {
                    throw new JSchException("privatekey: aes128-cbc is not available " + bArr);
                }
            } else if (b4 == 67 && (i5 = i11 + 3) < length && bArr[i11 + 1] == 66 && bArr[i11 + 2] == 67 && bArr[i5] == 44) {
                i11 += 4;
                for (int i17 = 0; i17 < bArr3.length; i17++) {
                    int i18 = i11 + 1;
                    int iA2b = (a2b(bArr[i11]) << 4) & 240;
                    i11 += 2;
                    bArr3[i17] = (byte) (iA2b + (a2b(bArr[i18]) & 15));
                }
            } else {
                if (b4 != 13 || (i4 = i11 + 1) >= bArr.length) {
                    b = 10;
                } else {
                    b = 10;
                    if (bArr[i4] == 10) {
                        i11 = i4;
                    }
                }
                if (b4 == b && (i2 = i11 + 1) < bArr.length) {
                    byte b6 = bArr[i2];
                    if (b6 != b) {
                        if (b6 != 13 || (i3 = i11 + 2) >= bArr.length || bArr[i3] != 10) {
                            int i19 = i2;
                            while (true) {
                                if (i19 < bArr.length && (b2 = bArr[i19]) != 10) {
                                    if (b2 == 58) {
                                        break;
                                    }
                                    i19++;
                                }
                                i11 = i2;
                                if (i12 == 3) {
                                    break loop1;
                                }
                                str3 = 0;
                                break loop1;
                            }
                        }
                        i11 += 3;
                        break;
                    }
                    i11 += 2;
                    break;
                }
                i11++;
            }
            str3 = str3;
        }
        if (bArr == null) {
            bArrFromBase64 = null;
        } else {
            if (c2 == 0) {
                throw new JSchException("invalid privatekey: " + bArr);
            }
            int i20 = i11;
            while (i20 < length && bArr[i20] != 45) {
                i20++;
            }
            if (length - i20 == 0 || (i = i20 - i11) == 0) {
                throw new JSchException("invalid privatekey: " + bArr);
            }
            byte[] bArr4 = new byte[i];
            System.arraycopy(bArr, i11, bArr4, 0, i);
            int i21 = 0;
            while (i21 < i) {
                byte b7 = bArr4[i21];
                if (b7 == 10) {
                    int i22 = bArr4[i21 + (-1)] == 13 ? 1 : 0;
                    int i23 = i21 + 1;
                    System.arraycopy(bArr4, i23, bArr4, i21 - i22, i - i23);
                    if (i22 != 0) {
                        i--;
                    }
                    i--;
                } else {
                    if (b7 == 45) {
                        break;
                    }
                    i21++;
                }
            }
            bArrFromBase64 = i21 > 0 ? Util.fromBase64(bArr4, 0, i21) : null;
            Util.bzero(bArr4);
        }
        if (bArrFromBase64 != null && bArrFromBase64.length > 4 && bArrFromBase64[0] == 63 && bArrFromBase64[1] == 111 && bArrFromBase64[2] == -7 && bArrFromBase64[3] == -21) {
            Buffer buffer2 = new Buffer(bArrFromBase64);
            buffer2.getInt();
            buffer2.getInt();
            buffer2.getString();
            String strByte2str = Util.byte2str(buffer2.getString());
            if (strByte2str.equals("3des-cbc")) {
                buffer2.getInt();
                buffer2.getByte(new byte[bArrFromBase64.length - buffer2.getOffSet()]);
                throw new JSchException("unknown privatekey format: " + bArr);
            }
            if (strByte2str.equals("none")) {
                buffer2.getInt();
                buffer2.getInt();
                bArrFromBase64 = new byte[bArrFromBase64.length - buffer2.getOffSet()];
                buffer2.getByte(bArrFromBase64);
                r3 = 0;
            } else {
                r3 = str3;
            }
        } else {
            r3 = str3;
        }
        String str4 = "";
        if (bArr2 == 0) {
            r8 = "";
            r6 = 0;
        } else {
            try {
                int length2 = bArr2.length;
                try {
                    if (bArr2.length <= 4 || bArr2[0] != '-') {
                        str3 = "";
                        FromBase64 = bArr2[0];
                        try {
                            if (FromBase64 != 115 && bArr2[1] == 's' && bArr2[2] == 'h' && bArr2[3] == '-') {
                                if (bArr == null && bArr2.length > 7) {
                                    char c3 = bArr2[4];
                                    if (c3 == 'd') {
                                        c2 = 1;
                                    } else if (c3 == 'r') {
                                        c2 = 2;
                                    }
                                }
                                int i24 = 0;
                                while (i24 < length2 && bArr2[i24] != ' ') {
                                    i24++;
                                }
                                int i25 = i24 + 1;
                                if (i25 < length2) {
                                    int i26 = i25;
                                    while (i26 < length2 && bArr2[i26] != ' ') {
                                        i26++;
                                    }
                                    int i27 = i26;
                                    FromBase64 = Util.fromBase64(bArr2, i25, i26 - i25);
                                    i25 = i27;
                                } else {
                                    FromBase64 = 0;
                                }
                                int i28 = i25 + 1;
                                if (i25 < length2) {
                                    int i29 = i28;
                                    while (i29 < length2 && bArr2[i29] != '\n') {
                                        i29++;
                                    }
                                    if (i29 > 0 && bArr2[i29 - 1] == '\r') {
                                        i29--;
                                    }
                                    str3 = i28 < i29 ? new String(bArr2, i28, i29 - i28) : str3;
                                }
                            } else if (FromBase64 != 101 && bArr2[1] == 'c' && bArr2[2] == 'd' && bArr2[3] == 's') {
                                if (bArr == null && bArr2.length > 7) {
                                    c2 = 3;
                                }
                                int i30 = 0;
                                while (i30 < length2 && bArr2[i30] != ' ') {
                                    i30++;
                                }
                                int i31 = i30 + 1;
                                if (i31 < length2) {
                                    int i32 = i31;
                                    while (i32 < length2 && bArr2[i32] != ' ') {
                                        i32++;
                                    }
                                    int i33 = i32;
                                    FromBase64 = Util.fromBase64(bArr2, i31, i32 - i31);
                                    i31 = i33;
                                } else {
                                    FromBase64 = 0;
                                }
                                int i34 = i31 + 1;
                                if (i31 < length2) {
                                    int i35 = i34;
                                    while (i35 < length2 && bArr2[i35] != '\n') {
                                        i35++;
                                    }
                                    if (i35 > 0 && bArr2[i35 - 1] == '\r') {
                                        i35--;
                                    }
                                    if (i34 < i35) {
                                        str3 = new String(bArr2, i34, i35 - i34);
                                    }
                                }
                            } else {
                                bArrFromBase65 = 0;
                            }
                        } catch (Exception unused) {
                        }
                        bArrFromBase65 = FromBase64;
                    } else {
                        int i36 = 1;
                        if (bArr2[1] != '-' || bArr2[2] != '-' || bArr2[3] != '-') {
                            str3 = "";
                            FromBase64 = bArr2[0];
                            if (FromBase64 != 115) {
                                if (FromBase64 != 101) {
                                }
                                bArrFromBase65 = 0;
                            } else {
                                if (FromBase64 != 101) {
                                }
                                bArrFromBase65 = 0;
                            }
                        } else {
                            int i37 = 0;
                            while (true) {
                                i37 += i36;
                                if (bArr2.length <= i37 || bArr2[i37] == '\n') {
                                    break;
                                    break;
                                }
                                i36 = 1;
                            }
                            boolean z = bArr2.length > i37;
                            loop6: while (true) {
                                if (!z) {
                                    str3 = str4;
                                    break;
                                }
                                if (bArr2[i37] == '\n') {
                                    int i38 = i37 + 1;
                                    int i39 = i38;
                                    String str5 = str4;
                                    while (true) {
                                        if (i39 < bArr2.length) {
                                            char c4 = bArr2[i39];
                                            str = str5;
                                            if (c4 != '\n') {
                                                if (c4 != ':') {
                                                    i39++;
                                                    str5 = str;
                                                }
                                            }
                                        } else {
                                            str = str5;
                                        }
                                        i37 = i38;
                                        str3 = str;
                                        break loop6;
                                    }
                                }
                                str = str4;
                                i37++;
                                str4 = str;
                            }
                            if (bArr2.length <= i37) {
                                z = false;
                            }
                            int i40 = i37;
                            while (z && i40 < length2) {
                                char c5 = bArr2[i40];
                                if (c5 == '\n') {
                                    System.arraycopy(bArr2, i40 + 1, bArr2, i40, (length2 - i40) - 1);
                                    length2--;
                                } else {
                                    if (c5 == '-') {
                                        break;
                                    }
                                    i40++;
                                }
                            }
                            if (z) {
                                bArrFromBase65 = Util.fromBase64(bArr2, i37, i40 - i37);
                                if (bArr == null || c2 == 4) {
                                    try {
                                        char c6 = bArrFromBase65[8];
                                        if (c6 == 'd') {
                                            c2 = 1;
                                        } else if (c6 == 'r') {
                                            c2 = 2;
                                        }
                                    } catch (Exception unused2) {
                                    }
                                }
                            } else {
                                bArrFromBase65 = 0;
                            }
                        }
                    }
                } catch (Exception unused3) {
                }
            } catch (Exception unused4) {
                str3 = str4;
            }
            r8 = str3;
            r6 = bArrFromBase65;
        }
        if (c2 == 1) {
            keyPairPKCS8 = new KeyPairDSA(jSch);
        } else if (c2 == 2) {
            keyPairPKCS8 = new KeyPairRSA(jSch);
        } else if (c2 == 3) {
            keyPairPKCS8 = new KeyPairECDSA(jSch, bArr2);
        } else {
            keyPairPKCS8 = i12 == 3 ? new KeyPairPKCS8(jSch) : null;
        }
        if (keyPairPKCS8 != null) {
            keyPairPKCS8.encrypted = r3;
            keyPairPKCS8.publickeyblob = r6;
            keyPairPKCS8.vendor = i12;
            keyPairPKCS8.publicKeyComment = r8;
            keyPairPKCS8.cipher = cipher;
            if (r3 != 0) {
                keyPairPKCS8.encrypted = true;
                keyPairPKCS8.iv = bArr3;
                keyPairPKCS8.data = bArrFromBase64;
            } else {
                if (keyPairPKCS8.parse(bArrFromBase64)) {
                    keyPairPKCS8.encrypted = false;
                    return keyPairPKCS8;
                }
                throw new JSchException("invalid privatekey: " + bArr);
            }
        }
        return keyPairPKCS8;
    }

    public void dispose() {
        Util.bzero(this.passphrase);
    }

    public void finalize() {
        dispose();
    }

    static KeyPair loadPPK(JSch jSch, byte[] bArr) throws JSchException {
        KeyPair keyPairDSA;
        Buffer buffer = new Buffer(bArr);
        Hashtable hashtable = new Hashtable();
        while (parseHeader(buffer, hashtable)) {
        }
        String str = (String) hashtable.get("PuTTY-User-Key-File-2");
        if (str == null) {
            return null;
        }
        byte[] lines = parseLines(buffer, Integer.parseInt((String) hashtable.get("Public-Lines")));
        while (parseHeader(buffer, hashtable)) {
        }
        byte[] lines2 = parseLines(buffer, Integer.parseInt((String) hashtable.get("Private-Lines")));
        while (parseHeader(buffer, hashtable)) {
        }
        byte[] bArrFromBase64 = Util.fromBase64(lines2, 0, lines2.length);
        byte[] bArrFromBase65 = Util.fromBase64(lines, 0, lines.length);
        if (str.equals("ssh-rsa")) {
            Buffer buffer2 = new Buffer(bArrFromBase65);
            buffer2.skip(bArrFromBase65.length);
            buffer2.getByte(new byte[buffer2.getInt()]);
            byte[] bArr2 = new byte[buffer2.getInt()];
            buffer2.getByte(bArr2);
            byte[] bArr3 = new byte[buffer2.getInt()];
            buffer2.getByte(bArr3);
            keyPairDSA = new KeyPairRSA(jSch, bArr3, bArr2, null);
        } else {
            if (!str.equals("ssh-dss")) {
                return null;
            }
            Buffer buffer3 = new Buffer(bArrFromBase65);
            buffer3.skip(bArrFromBase65.length);
            buffer3.getByte(new byte[buffer3.getInt()]);
            byte[] bArr4 = new byte[buffer3.getInt()];
            buffer3.getByte(bArr4);
            byte[] bArr5 = new byte[buffer3.getInt()];
            buffer3.getByte(bArr5);
            byte[] bArr6 = new byte[buffer3.getInt()];
            buffer3.getByte(bArr6);
            byte[] bArr7 = new byte[buffer3.getInt()];
            buffer3.getByte(bArr7);
            keyPairDSA = new KeyPairDSA(jSch, bArr4, bArr5, bArr6, bArr7, null);
        }
        keyPairDSA.encrypted = !hashtable.get("Encryption").equals("none");
        keyPairDSA.vendor = 2;
        keyPairDSA.publicKeyComment = (String) hashtable.get(PngChunkTextVar.KEY_Comment);
        if (keyPairDSA.encrypted) {
            if (Session.checkCipher(JSch.getConfig("aes256-cbc"))) {
                try {
                    Cipher cipher = (Cipher) Class.forName(JSch.getConfig("aes256-cbc")).newInstance();
                    keyPairDSA.cipher = cipher;
                    keyPairDSA.iv = new byte[cipher.getIVSize()];
                    keyPairDSA.data = bArrFromBase64;
                } catch (Exception unused) {
                    throw new JSchException("The cipher 'aes256-cbc' is required, but it is not available.");
                }
            } else {
                throw new JSchException("The cipher 'aes256-cbc' is required, but it is not available.");
            }
        } else {
            keyPairDSA.data = bArrFromBase64;
            keyPairDSA.parse(bArrFromBase64);
        }
        return keyPairDSA;
    }

    private static byte[] parseLines(Buffer buffer, int i) {
        byte[] bArr;
        byte[] bArr2 = buffer.buffer;
        int i2 = buffer.index;
        byte[] bArr3 = null;
        while (true) {
            int i3 = i - 1;
            if (i <= 0) {
                break;
            }
            int i4 = i2;
            while (bArr2.length > i4) {
                int i5 = i4 + 1;
                if (bArr2[i4] == 13) {
                    if (bArr3 == null) {
                        int i6 = (i5 - i2) - 1;
                        bArr = new byte[i6];
                        System.arraycopy(bArr2, i2, bArr, 0, i6);
                    } else {
                        bArr = new byte[((bArr3.length + i5) - i2) - 1];
                        System.arraycopy(bArr3, 0, bArr, 0, bArr3.length);
                        System.arraycopy(bArr2, i2, bArr, bArr3.length, (i5 - i2) - 1);
                        for (int i7 = 0; i7 < bArr3.length; i7++) {
                            bArr3[i7] = 0;
                        }
                    }
                    i4 = i5;
                    bArr3 = bArr;
                    break;
                }
                i4 = i5;
            }
            if (bArr2[i4] == 10) {
                i4++;
            }
            i2 = i4;
            i = i3;
        }
        if (bArr3 != null) {
            buffer.index = i2;
        }
        return bArr3;
    }

    private static boolean parseHeader(Buffer buffer, Hashtable hashtable) {
        String str;
        String str2;
        byte b;
        byte[] bArr = buffer.buffer;
        int i = buffer.index;
        int i2 = i;
        while (true) {
            str = null;
            if (i2 >= bArr.length || (b = bArr[i2]) == 13) {
                str2 = null;
                break;
            }
            if (b == 58) {
                str2 = new String(bArr, i, i2 - i);
                i = i2 + 1;
                if (i >= bArr.length || bArr[i] != 32) {
                    break;
                    break;
                }
                i = i2 + 2;
                break;
            }
            i2++;
        }
        if (str2 == null) {
            return false;
        }
        for (int i3 = i; i3 < bArr.length; i3++) {
            if (bArr[i3] == 13) {
                str = new String(bArr, i, i3 - i);
                i = i3 + 1;
                if (i >= bArr.length || bArr[i] != 10) {
                    break;
                    break;
                }
                i = i3 + 2;
                break;
            }
        }
        if (str != null) {
            hashtable.put(str2, str);
            buffer.index = i;
        }
        return (str2 == null || str == null) ? false : true;
    }

    void copy(KeyPair keyPair) {
        this.publickeyblob = keyPair.publickeyblob;
        this.vendor = keyPair.vendor;
        this.publicKeyComment = keyPair.publicKeyComment;
        this.cipher = keyPair.cipher;
    }

    class ASN1Exception extends Exception {
        ASN1Exception() {
        }
    }

    class ASN1 {
        byte[] buf;
        int length;
        int start;

        ASN1(KeyPair keyPair, byte[] bArr) throws ASN1Exception {
            this(bArr, 0, bArr.length);
        }

        ASN1(byte[] bArr, int i, int i2) throws ASN1Exception {
            this.buf = bArr;
            this.start = i;
            this.length = i2;
            if (i + i2 > bArr.length) {
                throw KeyPair.this.new ASN1Exception();
            }
        }

        int getType() {
            return this.buf[this.start] & 255;
        }

        boolean isSEQUENCE() {
            return getType() == 48;
        }

        boolean isINTEGER() {
            return getType() == 2;
        }

        boolean isOBJECT() {
            return getType() == 6;
        }

        boolean isOCTETSTRING() {
            return getType() == 4;
        }

        private int getLength(int[] iArr) {
            int i = iArr[0];
            int i2 = i + 1;
            byte b = this.buf[i];
            int i3 = b & 255;
            if ((b & 128) != 0) {
                int i4 = b & 127;
                i3 = 0;
                while (true) {
                    int i5 = i4 - 1;
                    if (i4 <= 0) {
                        break;
                    }
                    i3 = (this.buf[i2] & 255) + (i3 << 8);
                    i4 = i5;
                    i2++;
                }
            }
            iArr[0] = i2;
            return i3;
        }

        byte[] getContent() {
            int[] iArr = {this.start + 1};
            int length = getLength(iArr);
            byte[] bArr = new byte[length];
            System.arraycopy(this.buf, iArr[0], bArr, 0, length);
            return bArr;
        }

        ASN1[] getContents() throws ASN1Exception {
            byte[] bArr = this.buf;
            int i = this.start;
            byte b = bArr[i];
            int[] iArr = {i + 1};
            int length = getLength(iArr);
            if (b == 5) {
                return new ASN1[0];
            }
            int i2 = iArr[0];
            Vector vector = new Vector();
            while (length > 0) {
                int i3 = i2 + 1;
                iArr[0] = i3;
                int length2 = getLength(iArr);
                int i4 = iArr[0];
                int i5 = i4 - i3;
                vector.addElement(KeyPair.this.new ASN1(this.buf, i2, i5 + 1 + length2));
                i2 = i4 + length2;
                length = ((length - 1) - i5) - length2;
            }
            ASN1[] asn1Arr = new ASN1[vector.size()];
            for (int i6 = 0; i6 < vector.size(); i6++) {
                asn1Arr[i6] = (ASN1) vector.elementAt(i6);
            }
            return asn1Arr;
        }
    }
}
