package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
class RequestEnv extends Request {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    byte[] f205name = new byte[0];
    byte[] value = new byte[0];

    RequestEnv() {
    }

    void setEnv(byte[] bArr, byte[] bArr2) {
        this.f205name = bArr;
        this.value = bArr2;
    }

    @Override // com.jcraft.jsch.Request
    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("env"));
        buffer.putByte(waitForReply() ? (byte) 1 : (byte) 0);
        buffer.putString(this.f205name);
        buffer.putString(this.value);
        write(packet);
    }
}
