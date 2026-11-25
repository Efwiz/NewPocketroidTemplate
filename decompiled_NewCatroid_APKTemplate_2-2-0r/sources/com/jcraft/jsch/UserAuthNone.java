package com.jcraft.jsch;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes10.dex */
class UserAuthNone extends UserAuth {
    private static final int SSH_MSG_SERVICE_ACCEPT = 6;
    private String methods = null;

    UserAuthNone() {
    }

    @Override // com.jcraft.jsch.UserAuth
    public boolean start(Session session) throws Exception {
        super.start(session);
        this.packet.reset();
        this.buf.putByte((byte) 5);
        this.buf.putString(Util.str2byte("ssh-userauth"));
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_SERVICE_REQUEST sent");
        }
        this.buf = session.read(this.buf);
        boolean z = this.buf.getCommand() == 6;
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_SERVICE_ACCEPT received");
        }
        if (!z) {
            return false;
        }
        byte[] bArrStr2byte = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte) 50);
        this.buf.putString(bArrStr2byte);
        this.buf.putString(Util.str2byte("ssh-connection"));
        this.buf.putString(Util.str2byte("none"));
        session.write(this.packet);
        while (true) {
            this.buf = session.read(this.buf);
            int command = this.buf.getCommand() & 255;
            if (command == 52) {
                return true;
            }
            if (command != 53) {
                if (command == 51) {
                    this.buf.getInt();
                    this.buf.getByte();
                    this.buf.getByte();
                    byte[] string = this.buf.getString();
                    this.buf.getByte();
                    this.methods = Util.byte2str(string);
                    return false;
                }
                throw new JSchException("USERAUTH fail (" + command + Constant.AFTER_QUTO);
            }
            this.buf.getInt();
            this.buf.getByte();
            this.buf.getByte();
            byte[] string2 = this.buf.getString();
            this.buf.getString();
            String strByte2str = Util.byte2str(string2);
            if (this.userinfo != null) {
                try {
                    this.userinfo.showMessage(strByte2str);
                } catch (RuntimeException unused) {
                }
            }
        }
    }

    String getMethods() {
        return this.methods;
    }
}
