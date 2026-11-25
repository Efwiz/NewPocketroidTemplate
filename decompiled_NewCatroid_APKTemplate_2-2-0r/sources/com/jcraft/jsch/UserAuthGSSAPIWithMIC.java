package com.jcraft.jsch;

import org.eclipse.jdt.internal.compiler.codegen.Opcodes;

/* JADX INFO: loaded from: classes10.dex */
public class UserAuthGSSAPIWithMIC extends UserAuth {
    private static final int SSH_MSG_USERAUTH_GSSAPI_ERROR = 64;
    private static final int SSH_MSG_USERAUTH_GSSAPI_ERRTOK = 65;
    private static final int SSH_MSG_USERAUTH_GSSAPI_EXCHANGE_COMPLETE = 63;
    private static final int SSH_MSG_USERAUTH_GSSAPI_MIC = 66;
    private static final int SSH_MSG_USERAUTH_GSSAPI_RESPONSE = 60;
    private static final int SSH_MSG_USERAUTH_GSSAPI_TOKEN = 61;
    private static final byte[][] supported_oid = {new byte[]{6, 9, 42, Opcodes.OPC_i2f, 72, Opcodes.OPC_i2f, -9, 18, 1, 2, 2}};
    private static final String[] supported_method = {"gssapi-with-mic.krb5"};

    @Override // com.jcraft.jsch.UserAuth
    public boolean start(Session session) throws Exception {
        String str;
        super.start(session);
        byte[] bArrStr2byte = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte) 50);
        this.buf.putString(bArrStr2byte);
        this.buf.putString(Util.str2byte("ssh-connection"));
        this.buf.putString(Util.str2byte("gssapi-with-mic"));
        this.buf.putInt(supported_oid.length);
        int i = 0;
        while (true) {
            byte[][] bArr = supported_oid;
            if (i >= bArr.length) {
                break;
            }
            this.buf.putString(bArr[i]);
            i++;
        }
        session.write(this.packet);
        while (true) {
            this.buf = session.read(this.buf);
            int command = this.buf.getCommand() & 255;
            if (command == 51) {
                return false;
            }
            if (command == 60) {
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                byte[] string = this.buf.getString();
                int i2 = 0;
                while (true) {
                    byte[][] bArr2 = supported_oid;
                    if (i2 >= bArr2.length) {
                        str = null;
                        break;
                    }
                    if (Util.array_equals(string, bArr2[i2])) {
                        str = supported_method[i2];
                        break;
                    }
                    i2++;
                }
                if (str == null) {
                    return false;
                }
                try {
                    GSSContext gSSContext = (GSSContext) Class.forName(session.getConfig(str)).newInstance();
                    gSSContext.create(this.username, session.host);
                    byte[] bArrInit = new byte[0];
                    while (!gSSContext.isEstablished()) {
                        try {
                            bArrInit = gSSContext.init(bArrInit, 0, bArrInit.length);
                            if (bArrInit != null) {
                                this.packet.reset();
                                this.buf.putByte((byte) 61);
                                this.buf.putString(bArrInit);
                                session.write(this.packet);
                            }
                            if (!gSSContext.isEstablished()) {
                                this.buf = session.read(this.buf);
                                int command2 = this.buf.getCommand() & 255;
                                if (command2 == 64 || command2 == 65) {
                                    this.buf = session.read(this.buf);
                                    byte command3 = this.buf.getCommand();
                                    command2 = command3 & 255;
                                }
                                if (command2 == 51) {
                                    return false;
                                }
                                this.buf.getInt();
                                this.buf.getByte();
                                this.buf.getByte();
                                bArrInit = this.buf.getString();
                            }
                        } catch (JSchException unused) {
                            return false;
                        }
                    }
                    Buffer buffer = new Buffer();
                    buffer.putString(session.getSessionId());
                    buffer.putByte((byte) 50);
                    buffer.putString(bArrStr2byte);
                    buffer.putString(Util.str2byte("ssh-connection"));
                    buffer.putString(Util.str2byte("gssapi-with-mic"));
                    byte[] mic = gSSContext.getMIC(buffer.buffer, 0, buffer.getLength());
                    if (mic == null) {
                        return false;
                    }
                    this.packet.reset();
                    this.buf.putByte((byte) 66);
                    this.buf.putString(mic);
                    session.write(this.packet);
                    gSSContext.dispose();
                    this.buf = session.read(this.buf);
                    int command4 = this.buf.getCommand() & 255;
                    if (command4 == 52) {
                        return true;
                    }
                    if (command4 == 51) {
                        this.buf.getInt();
                        this.buf.getByte();
                        this.buf.getByte();
                        byte[] string2 = this.buf.getString();
                        if (this.buf.getByte() != 0) {
                            throw new JSchPartialAuthException(Util.byte2str(string2));
                        }
                    }
                    return false;
                } catch (JSchException | Exception unused2) {
                }
            } else {
                if (command != 53) {
                    return false;
                }
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                byte[] string3 = this.buf.getString();
                this.buf.getString();
                String strByte2str = Util.byte2str(string3);
                if (this.userinfo != null) {
                    this.userinfo.showMessage(strByte2str);
                }
            }
        }
    }
}
