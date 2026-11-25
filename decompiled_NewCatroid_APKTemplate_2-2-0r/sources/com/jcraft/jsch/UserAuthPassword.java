package com.jcraft.jsch;

import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes10.dex */
class UserAuthPassword extends UserAuth {
    private final int SSH_MSG_USERAUTH_PASSWD_CHANGEREQ = 60;

    UserAuthPassword() {
    }

    @Override // com.jcraft.jsch.UserAuth
    public boolean start(Session session) throws Exception {
        int command;
        super.start(session);
        byte[] bArrStr2byte = session.password;
        String str = this.username + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + session.host;
        if (session.port != 22) {
            str = str + ":" + session.port;
        }
        String str2 = str;
        while (session.auth_failures < session.max_auth_tries) {
            try {
                if (bArrStr2byte == null) {
                    if (this.userinfo == null) {
                        if (bArrStr2byte != null) {
                            Util.bzero(bArrStr2byte);
                        }
                        return false;
                    }
                    if (!this.userinfo.promptPassword("Password for " + str2)) {
                        throw new JSchAuthCancelException(AmazonS3.Keys.PASSWORD);
                    }
                    String password = this.userinfo.getPassword();
                    if (password == null) {
                        throw new JSchAuthCancelException(AmazonS3.Keys.PASSWORD);
                    }
                    bArrStr2byte = Util.str2byte(password);
                }
                byte[] bArrStr2byte2 = Util.str2byte(this.username);
                this.packet.reset();
                this.buf.putByte((byte) 50);
                this.buf.putString(bArrStr2byte2);
                this.buf.putString(Util.str2byte("ssh-connection"));
                this.buf.putString(Util.str2byte(AmazonS3.Keys.PASSWORD));
                this.buf.putByte((byte) 0);
                this.buf.putString(bArrStr2byte);
                session.write(this.packet);
                while (true) {
                    this.buf = session.read(this.buf);
                    command = this.buf.getCommand() & 255;
                    if (command == 52) {
                        if (bArrStr2byte != null) {
                            Util.bzero(bArrStr2byte);
                        }
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
                    } else if (command == 60) {
                        this.buf.getInt();
                        this.buf.getByte();
                        this.buf.getByte();
                        byte[] string2 = this.buf.getString();
                        this.buf.getString();
                        if (this.userinfo != null && (this.userinfo instanceof UIKeyboardInteractive)) {
                            String[] strArrPromptKeyboardInteractive = ((UIKeyboardInteractive) this.userinfo).promptKeyboardInteractive(str2, "Password Change Required", Util.byte2str(string2), new String[]{"New Password: "}, new boolean[]{false});
                            if (strArrPromptKeyboardInteractive == null) {
                                throw new JSchAuthCancelException(AmazonS3.Keys.PASSWORD);
                            }
                            byte[] bArrStr2byte3 = Util.str2byte(strArrPromptKeyboardInteractive[0]);
                            this.packet.reset();
                            this.buf.putByte((byte) 50);
                            this.buf.putString(bArrStr2byte2);
                            this.buf.putString(Util.str2byte("ssh-connection"));
                            this.buf.putString(Util.str2byte(AmazonS3.Keys.PASSWORD));
                            this.buf.putByte((byte) 1);
                            this.buf.putString(bArrStr2byte);
                            this.buf.putString(bArrStr2byte3);
                            Util.bzero(bArrStr2byte3);
                            session.write(this.packet);
                        }
                        if (this.userinfo != null) {
                            this.userinfo.showMessage("Password must be changed.");
                        }
                        if (bArrStr2byte != null) {
                            Util.bzero(bArrStr2byte);
                        }
                        return false;
                    }
                }
                if (command != 51) {
                    if (bArrStr2byte != null) {
                        Util.bzero(bArrStr2byte);
                    }
                    return false;
                }
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                byte[] string3 = this.buf.getString();
                if (this.buf.getByte() != 0) {
                    throw new JSchPartialAuthException(Util.byte2str(string3));
                }
                session.auth_failures++;
                if (bArrStr2byte != null) {
                    Util.bzero(bArrStr2byte);
                    bArrStr2byte = null;
                }
            } catch (Throwable th) {
                if (bArrStr2byte != null) {
                    Util.bzero(bArrStr2byte);
                }
                throw th;
            }
        }
        if (bArrStr2byte != null) {
            Util.bzero(bArrStr2byte);
        }
        return false;
    }
}
