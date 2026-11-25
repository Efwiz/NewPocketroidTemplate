package com.jcraft.jsch;

import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes10.dex */
class UserAuthKeyboardInteractive extends UserAuth {
    UserAuthKeyboardInteractive() {
    }

    @Override // com.jcraft.jsch.UserAuth
    public boolean start(Session session) throws Exception {
        String[] strArrPromptKeyboardInteractive;
        byte[][] bArr;
        super.start(session);
        if (this.userinfo != null && !(this.userinfo instanceof UIKeyboardInteractive)) {
            return false;
        }
        String str = this.username + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + session.host;
        if (session.port != 22) {
            str = str + ":" + session.port;
        }
        byte[] bArr2 = session.password;
        byte[] bArrStr2byte = Util.str2byte(this.username);
        boolean z = false;
        while (session.auth_failures < session.max_auth_tries) {
            this.packet.reset();
            this.buf.putByte((byte) 50);
            this.buf.putString(bArrStr2byte);
            this.buf.putString(Util.str2byte("ssh-connection"));
            this.buf.putString(Util.str2byte("keyboard-interactive"));
            this.buf.putString(Util.empty);
            this.buf.putString(Util.empty);
            session.write(this.packet);
            byte[] bArr3 = bArr2;
            boolean z2 = z;
            boolean z3 = true;
            while (true) {
                this.buf = session.read(this.buf);
                int command = this.buf.getCommand() & 255;
                if (command == 52) {
                    return true;
                }
                if (command == 53) {
                    this.buf.getInt();
                    this.buf.getByte();
                    this.buf.getByte();
                    byte[] string = this.buf.getString();
                    this.buf.getString();
                    String strByte2str = Util.byte2str(string);
                    if (this.userinfo != null) {
                        this.userinfo.showMessage(strByte2str);
                    }
                } else {
                    if (command == 51) {
                        break;
                    }
                    if (command != 60) {
                        return false;
                    }
                    this.buf.getInt();
                    this.buf.getByte();
                    this.buf.getByte();
                    String strByte2str2 = Util.byte2str(this.buf.getString());
                    String strByte2str3 = Util.byte2str(this.buf.getString());
                    Util.byte2str(this.buf.getString());
                    int i = this.buf.getInt();
                    String[] strArr = new String[i];
                    boolean[] zArr = new boolean[i];
                    for (int i2 = 0; i2 < i; i2++) {
                        strArr[i2] = Util.byte2str(this.buf.getString());
                        zArr[i2] = this.buf.getByte() != 0;
                    }
                    if (bArr3 != null && i == 1 && !zArr[0] && strArr[0].toLowerCase().indexOf("password:") >= 0) {
                        bArr = new byte[][]{bArr3};
                        bArr3 = null;
                    } else if ((i <= 0 && strByte2str2.length() <= 0 && strByte2str3.length() <= 0) || this.userinfo == null || (strArrPromptKeyboardInteractive = ((UIKeyboardInteractive) this.userinfo).promptKeyboardInteractive(str, strByte2str2, strByte2str3, strArr, zArr)) == null) {
                        bArr = null;
                    } else {
                        byte[][] bArr4 = new byte[strArrPromptKeyboardInteractive.length][];
                        for (int i3 = 0; i3 < strArrPromptKeyboardInteractive.length; i3++) {
                            bArr4[i3] = Util.str2byte(strArrPromptKeyboardInteractive[i3]);
                        }
                        bArr = bArr4;
                    }
                    this.packet.reset();
                    this.buf.putByte((byte) 61);
                    if (i <= 0 || (bArr != null && i == bArr.length)) {
                        this.buf.putInt(i);
                        for (int i4 = 0; i4 < i; i4++) {
                            this.buf.putString(bArr[i4]);
                        }
                    } else {
                        if (bArr == null) {
                            this.buf.putInt(i);
                            for (int i5 = 0; i5 < i; i5++) {
                                this.buf.putString(Util.empty);
                            }
                        } else {
                            this.buf.putInt(0);
                        }
                        if (bArr == null) {
                            z2 = true;
                        }
                    }
                    session.write(this.packet);
                    z3 = false;
                }
            }
            this.buf.getInt();
            this.buf.getByte();
            this.buf.getByte();
            byte[] string2 = this.buf.getString();
            if (this.buf.getByte() != 0) {
                throw new JSchPartialAuthException(Util.byte2str(string2));
            }
            if (z3) {
                return false;
            }
            session.auth_failures++;
            if (z2) {
                throw new JSchAuthCancelException("keyboard-interactive");
            }
            bArr2 = bArr3;
            z = z2;
        }
        return false;
    }
}
