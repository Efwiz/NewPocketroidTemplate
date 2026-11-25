package com.jcraft.jsch;

import androidx.core.view.ViewCompat;
import com.reandroid.dex.sections.Marker;

/* JADX INFO: loaded from: classes10.dex */
public class DHGEX extends KeyExchange {
    private static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
    private static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
    private static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
    private static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
    static int min = 1024;
    static int preferred = 1024;
    byte[] I_C;
    byte[] I_S;
    byte[] V_C;
    byte[] V_S;
    private Buffer buf;
    DH dh;
    private byte[] e;
    private byte[] g;
    private byte[] p;
    private Packet packet;
    private int state;
    int max = 1024;
    protected String hash = Marker.SHA1;

    @Override // com.jcraft.jsch.KeyExchange
    public void init(Session session, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        this.session = session;
        this.V_S = bArr;
        this.V_C = bArr2;
        this.I_S = bArr3;
        this.I_C = bArr4;
        try {
            HASH hash = (HASH) Class.forName(session.getConfig(this.hash)).newInstance();
            this.sha = hash;
            this.sha.init();
        } catch (Exception e) {
            System.err.println(e);
        }
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        Class<?> cls = Class.forName(session.getConfig("dh"));
        int iCheck2048 = check2048(cls, this.max);
        this.max = iCheck2048;
        preferred = iCheck2048;
        DH dh = (DH) cls.newInstance();
        this.dh = dh;
        dh.init();
        this.packet.reset();
        this.buf.putByte((byte) 34);
        this.buf.putInt(min);
        this.buf.putInt(preferred);
        this.buf.putInt(this.max);
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEX_DH_GEX_REQUEST(" + min + "<" + preferred + "<" + this.max + ") sent");
            JSch.getLogger().log(1, "expecting SSH_MSG_KEX_DH_GEX_GROUP");
        }
        this.state = 31;
    }

    @Override // com.jcraft.jsch.KeyExchange
    public boolean next(Buffer buffer) throws Exception {
        int i = this.state;
        if (i == 31) {
            buffer.getInt();
            buffer.getByte();
            int i2 = buffer.getByte();
            if (i2 != 31) {
                System.err.println("type: must be SSH_MSG_KEX_DH_GEX_GROUP " + i2);
                return false;
            }
            this.p = buffer.getMPInt();
            this.g = buffer.getMPInt();
            this.dh.setP(this.p);
            this.dh.setG(this.g);
            this.e = this.dh.getE();
            this.packet.reset();
            this.buf.putByte((byte) 32);
            this.buf.putMPInt(this.e);
            this.session.write(this.packet);
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "SSH_MSG_KEX_DH_GEX_INIT sent");
                JSch.getLogger().log(1, "expecting SSH_MSG_KEX_DH_GEX_REPLY");
            }
            this.state = 33;
            return true;
        }
        if (i != 33) {
            return false;
        }
        buffer.getInt();
        buffer.getByte();
        int i3 = buffer.getByte();
        if (i3 != 33) {
            System.err.println("type: must be SSH_MSG_KEX_DH_GEX_REPLY " + i3);
            return false;
        }
        this.K_S = buffer.getString();
        byte[] mPInt = buffer.getMPInt();
        byte[] string = buffer.getString();
        this.dh.setF(mPInt);
        this.dh.checkRange();
        this.K = normalize(this.dh.getK());
        this.buf.reset();
        this.buf.putString(this.V_C);
        this.buf.putString(this.V_S);
        this.buf.putString(this.I_C);
        this.buf.putString(this.I_S);
        this.buf.putString(this.K_S);
        this.buf.putInt(min);
        this.buf.putInt(preferred);
        this.buf.putInt(this.max);
        this.buf.putMPInt(this.p);
        this.buf.putMPInt(this.g);
        this.buf.putMPInt(this.e);
        this.buf.putMPInt(mPInt);
        this.buf.putMPInt(this.K);
        int length = this.buf.getLength();
        byte[] bArr = new byte[length];
        this.buf.getByte(bArr);
        this.sha.update(bArr, 0, length);
        this.H = this.sha.digest();
        int i4 = ((this.K_S[0] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((this.K_S[1] << 16) & 16711680) | ((this.K_S[2] << 8) & 65280) | (this.K_S[3] & 255);
        boolean zVerify = verify(Util.byte2str(this.K_S, 4, i4), this.K_S, 4 + i4, string);
        this.state = 0;
        return zVerify;
    }

    @Override // com.jcraft.jsch.KeyExchange
    public int getState() {
        return this.state;
    }

    protected int check2048(Class cls, int i) throws Exception {
        DH dh = (DH) cls.newInstance();
        dh.init();
        byte[] bArr = new byte[257];
        bArr[1] = -35;
        bArr[256] = 115;
        dh.setP(bArr);
        dh.setG(new byte[]{2});
        try {
            dh.getE();
            return 2048;
        } catch (Exception unused) {
            return i;
        }
    }
}
