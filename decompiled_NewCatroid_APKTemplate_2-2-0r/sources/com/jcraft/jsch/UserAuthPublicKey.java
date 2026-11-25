package com.jcraft.jsch;

import java.util.Vector;

/* JADX INFO: loaded from: classes10.dex */
class UserAuthPublicKey extends UserAuth {
    UserAuthPublicKey() {
    }

    /* JADX WARN: Code duplicated, block: B:110:0x021b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:111:0x0215 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:113:0x01db A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:29:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:32:0x00d3 A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x00d7 A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:43:0x010d A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x012a A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x014d  */
    /* JADX WARN: Code duplicated, block: B:60:0x014f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:61:0x0151 A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:63:0x0157  */
    /* JADX WARN: Code duplicated, block: B:64:0x0159 A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:67:0x01d1 A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:72:0x01f1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:73:0x01f3 A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:78:0x021f A[Catch: all -> 0x0257, TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:80:0x023c A[Catch: all -> 0x0257, EDGE_INSN: B:80:0x023c->B:83:0x024c BREAK  A[LOOP:3: B:68:0x01db->B:112:0x01db], TryCatch #0 {, blocks: (B:4:0x0010, B:6:0x0017, B:8:0x0019, B:9:0x0021, B:11:0x0027, B:13:0x002d, B:15:0x002f, B:17:0x0044, B:18:0x0085, B:84:0x024d, B:24:0x009f, B:26:0x00c1, B:30:0x00cd, B:32:0x00d3, B:34:0x00d7, B:36:0x00dd, B:39:0x00fd, B:40:0x0104, B:41:0x0105, B:43:0x010d, B:44:0x0112, B:45:0x0119, B:46:0x011a, B:55:0x013c, B:57:0x0144, B:61:0x0151, B:64:0x0159, B:67:0x01d1, B:68:0x01db, B:70:0x01ef, B:73:0x01f3, B:75:0x0215, B:78:0x021f, B:80:0x023c, B:81:0x0242, B:82:0x024b, B:49:0x0122, B:52:0x012a, B:54:0x0132, B:86:0x0255), top: B:91:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:94:0x0112 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x01cf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:97:0x01ef A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:98:0x0242 A[SYNTHETIC] */
    @Override // com.jcraft.jsch.UserAuth
    public boolean start(Session session) throws Exception {
        int i;
        byte[] signature;
        int command;
        byte[] string;
        String strByte2str;
        String passphrase;
        int command2;
        super.start(session);
        Vector identities = session.getIdentityRepository().getIdentities();
        synchronized (identities) {
            if (identities.size() <= 0) {
                return false;
            }
            byte[] bArrStr2byte = Util.str2byte(this.username);
            byte[] bArrStr2byte2 = null;
            for (int i2 = 0; i2 < identities.size(); i2++) {
                if (session.auth_failures >= session.max_auth_tries) {
                    return false;
                }
                Identity identity = (Identity) identities.elementAt(i2);
                byte[] publicKeyBlob = identity.getPublicKeyBlob();
                if (publicKeyBlob != null) {
                    this.packet.reset();
                    this.buf.putByte((byte) 50);
                    this.buf.putString(bArrStr2byte);
                    this.buf.putString(Util.str2byte("ssh-connection"));
                    this.buf.putString(Util.str2byte("publickey"));
                    this.buf.putByte((byte) 0);
                    this.buf.putString(Util.str2byte(identity.getAlgName()));
                    this.buf.putString(publicKeyBlob);
                    session.write(this.packet);
                    while (true) {
                        this.buf = session.read(this.buf);
                        command2 = this.buf.getCommand() & 255;
                        if (command2 == 60 || command2 == 51 || command2 != 53) {
                            break;
                            break;
                            break;
                        }
                        this.buf.getInt();
                        this.buf.getByte();
                        this.buf.getByte();
                        byte[] string2 = this.buf.getString();
                        this.buf.getString();
                        String strByte2str2 = Util.byte2str(string2);
                        if (this.userinfo != null) {
                            this.userinfo.showMessage(strByte2str2);
                        }
                    }
                    if (command2 == 60) {
                        i = 5;
                        do {
                            if (identity.isEncrypted()) {
                                if (this.userinfo != null) {
                                    throw new JSchException("USERAUTH fail");
                                }
                                if (identity.isEncrypted() && !this.userinfo.promptPassphrase("Passphrase for " + identity.getName())) {
                                    throw new JSchAuthCancelException("publickey");
                                }
                                passphrase = this.userinfo.getPassphrase();
                                if (passphrase != null) {
                                    bArrStr2byte2 = Util.str2byte(passphrase);
                                }
                            }
                            if ((!identity.isEncrypted() || bArrStr2byte2 != null) && identity.setPassphrase(bArrStr2byte2)) {
                                if (bArrStr2byte2 == null || !(session.getIdentityRepository() instanceof IdentityRepository.Wrapper)) {
                                    break;
                                    break;
                                }
                                ((IdentityRepository.Wrapper) session.getIdentityRepository()).check();
                                break;
                            }
                            Util.bzero(bArrStr2byte2);
                            i--;
                            bArrStr2byte2 = null;
                        } while (i != 0);
                        Util.bzero(bArrStr2byte2);
                        if (!identity.isEncrypted()) {
                            if (publicKeyBlob == null) {
                                publicKeyBlob = identity.getPublicKeyBlob();
                            }
                            if (publicKeyBlob == null) {
                                this.packet.reset();
                                this.buf.putByte((byte) 50);
                                this.buf.putString(bArrStr2byte);
                                this.buf.putString(Util.str2byte("ssh-connection"));
                                this.buf.putString(Util.str2byte("publickey"));
                                this.buf.putByte((byte) 1);
                                this.buf.putString(Util.str2byte(identity.getAlgName()));
                                this.buf.putString(publicKeyBlob);
                                byte[] sessionId = session.getSessionId();
                                int length = sessionId.length;
                                int i3 = length + 4;
                                byte[] bArr = new byte[(this.buf.index + i3) - 5];
                                bArr[0] = (byte) (length >>> 24);
                                bArr[1] = (byte) (length >>> 16);
                                bArr[2] = (byte) (length >>> 8);
                                bArr[3] = (byte) length;
                                System.arraycopy(sessionId, 0, bArr, 4, length);
                                System.arraycopy(this.buf.buffer, 5, bArr, i3, this.buf.index - 5);
                                signature = identity.getSignature(bArr);
                                if (signature == null) {
                                    break;
                                }
                                this.buf.putString(signature);
                                session.write(this.packet);
                                while (true) {
                                    this.buf = session.read(this.buf);
                                    command = this.buf.getCommand() & 255;
                                    if (command == 52) {
                                        return true;
                                    }
                                    if (command == 53) {
                                        if (command == 51) {
                                            break;
                                        }
                                        this.buf.getInt();
                                        this.buf.getByte();
                                        this.buf.getByte();
                                        string = this.buf.getString();
                                        if (this.buf.getByte() == 0) {
                                            throw new JSchPartialAuthException(Util.byte2str(string));
                                        }
                                        session.auth_failures++;
                                        break;
                                    }
                                    this.buf.getInt();
                                    this.buf.getByte();
                                    this.buf.getByte();
                                    byte[] string3 = this.buf.getString();
                                    this.buf.getString();
                                    strByte2str = Util.byte2str(string3);
                                    if (this.userinfo != null) {
                                        this.userinfo.showMessage(strByte2str);
                                    }
                                }
                            }
                        }
                        bArrStr2byte2 = null;
                    }
                } else {
                    i = 5;
                    do {
                        if (identity.isEncrypted()) {
                            if (this.userinfo != null) {
                                throw new JSchException("USERAUTH fail");
                            }
                            if (identity.isEncrypted()) {
                                throw new JSchAuthCancelException("publickey");
                            }
                            passphrase = this.userinfo.getPassphrase();
                            if (passphrase != null) {
                                bArrStr2byte2 = Util.str2byte(passphrase);
                            }
                        }
                        if (!identity.isEncrypted()) {
                            if (bArrStr2byte2 == null) {
                                break;
                            }
                            ((IdentityRepository.Wrapper) session.getIdentityRepository()).check();
                            break;
                        }
                        if (bArrStr2byte2 == null) {
                            break;
                        }
                        ((IdentityRepository.Wrapper) session.getIdentityRepository()).check();
                        break;
                    } while (i != 0);
                    Util.bzero(bArrStr2byte2);
                    if (!identity.isEncrypted()) {
                        if (publicKeyBlob == null) {
                            publicKeyBlob = identity.getPublicKeyBlob();
                        }
                        if (publicKeyBlob == null) {
                            this.packet.reset();
                            this.buf.putByte((byte) 50);
                            this.buf.putString(bArrStr2byte);
                            this.buf.putString(Util.str2byte("ssh-connection"));
                            this.buf.putString(Util.str2byte("publickey"));
                            this.buf.putByte((byte) 1);
                            this.buf.putString(Util.str2byte(identity.getAlgName()));
                            this.buf.putString(publicKeyBlob);
                            byte[] sessionId2 = session.getSessionId();
                            int length2 = sessionId2.length;
                            int i4 = length2 + 4;
                            byte[] bArr2 = new byte[(this.buf.index + i4) - 5];
                            bArr2[0] = (byte) (length2 >>> 24);
                            bArr2[1] = (byte) (length2 >>> 16);
                            bArr2[2] = (byte) (length2 >>> 8);
                            bArr2[3] = (byte) length2;
                            System.arraycopy(sessionId2, 0, bArr2, 4, length2);
                            System.arraycopy(this.buf.buffer, 5, bArr2, i4, this.buf.index - 5);
                            signature = identity.getSignature(bArr2);
                            if (signature == null) {
                                break;
                                break;
                            }
                            this.buf.putString(signature);
                            session.write(this.packet);
                            while (true) {
                                this.buf = session.read(this.buf);
                                command = this.buf.getCommand() & 255;
                                if (command == 52) {
                                    return true;
                                }
                                if (command == 53) {
                                    if (command == 51) {
                                        break;
                                    }
                                    this.buf.getInt();
                                    this.buf.getByte();
                                    this.buf.getByte();
                                    string = this.buf.getString();
                                    if (this.buf.getByte() == 0) {
                                        throw new JSchPartialAuthException(Util.byte2str(string));
                                    }
                                    session.auth_failures++;
                                    break;
                                }
                                this.buf.getInt();
                                this.buf.getByte();
                                this.buf.getByte();
                                byte[] string4 = this.buf.getString();
                                this.buf.getString();
                                strByte2str = Util.byte2str(string4);
                                if (this.userinfo != null) {
                                    this.userinfo.showMessage(strByte2str);
                                }
                            }
                        }
                    }
                    bArrStr2byte2 = null;
                }
            }
            return false;
        }
    }
}
