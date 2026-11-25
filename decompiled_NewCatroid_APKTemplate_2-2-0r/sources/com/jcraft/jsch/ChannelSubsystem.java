package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes10.dex */
public class ChannelSubsystem extends ChannelSession {
    boolean xforwading = false;
    boolean pty = false;
    boolean want_reply = true;
    String subsystem = "";

    @Override // com.jcraft.jsch.ChannelSession, com.jcraft.jsch.Channel, java.lang.Runnable
    public /* bridge */ /* synthetic */ void run() {
        super.run();
    }

    @Override // com.jcraft.jsch.ChannelSession
    public /* bridge */ /* synthetic */ void setAgentForwarding(boolean z) {
        super.setAgentForwarding(z);
    }

    @Override // com.jcraft.jsch.ChannelSession
    public /* bridge */ /* synthetic */ void setEnv(String str, String str2) {
        super.setEnv(str, str2);
    }

    @Override // com.jcraft.jsch.ChannelSession
    public /* bridge */ /* synthetic */ void setEnv(Hashtable hashtable) {
        super.setEnv(hashtable);
    }

    @Override // com.jcraft.jsch.ChannelSession
    public /* bridge */ /* synthetic */ void setEnv(byte[] bArr, byte[] bArr2) {
        super.setEnv(bArr, bArr2);
    }

    @Override // com.jcraft.jsch.ChannelSession
    public /* bridge */ /* synthetic */ void setPtySize(int i, int i2, int i3, int i4) {
        super.setPtySize(i, i2, i3, i4);
    }

    @Override // com.jcraft.jsch.ChannelSession
    public /* bridge */ /* synthetic */ void setPtyType(String str) {
        super.setPtyType(str);
    }

    @Override // com.jcraft.jsch.ChannelSession
    public /* bridge */ /* synthetic */ void setPtyType(String str, int i, int i2, int i3, int i4) {
        super.setPtyType(str, i, i2, i3, i4);
    }

    @Override // com.jcraft.jsch.ChannelSession
    public /* bridge */ /* synthetic */ void setTerminalMode(byte[] bArr) {
        super.setTerminalMode(bArr);
    }

    @Override // com.jcraft.jsch.ChannelSession, com.jcraft.jsch.Channel
    public void setXForwarding(boolean z) {
        this.xforwading = z;
    }

    @Override // com.jcraft.jsch.ChannelSession
    public void setPty(boolean z) {
        this.pty = z;
    }

    public void setWantReply(boolean z) {
        this.want_reply = z;
    }

    public void setSubsystem(String str) {
        this.subsystem = str;
    }

    @Override // com.jcraft.jsch.Channel
    public void start() throws JSchException {
        Session session = getSession();
        try {
            if (this.xforwading) {
                new RequestX11().request(session, this);
            }
            if (this.pty) {
                new RequestPtyReq().request(session, this);
            }
            RequestSubsystem requestSubsystem = new RequestSubsystem();
            requestSubsystem.request(session, this, this.subsystem, this.want_reply);
            if (this.f201io.in != null) {
                this.thread = new Thread(this);
                this.thread.setName("Subsystem for " + session.host);
                if (session.daemon_thread) {
                    this.thread.setDaemon(session.daemon_thread);
                }
                this.thread.start();
            }
        } catch (Exception e) {
            if (e instanceof JSchException) {
                throw ((JSchException) e);
            }
            if (e instanceof Throwable) {
                throw new JSchException("ChannelSubsystem", e);
            }
            throw new JSchException("ChannelSubsystem");
        }
    }

    @Override // com.jcraft.jsch.Channel
    void init() throws JSchException {
        this.f201io.setInputStream(getSession().in);
        this.f201io.setOutputStream(getSession().out);
    }

    public void setErrStream(OutputStream outputStream) {
        setExtOutputStream(outputStream);
    }

    public InputStream getErrStream() throws IOException {
        return getExtInputStream();
    }
}
