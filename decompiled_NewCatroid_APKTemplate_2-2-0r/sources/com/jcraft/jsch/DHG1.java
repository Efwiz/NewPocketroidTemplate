package com.jcraft.jsch;

import androidx.core.view.ViewCompat;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.reandroid.dex.sections.Marker;
import org.billthefarmer.mididriver.MidiConstants;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;

/* JADX INFO: loaded from: classes10.dex */
public class DHG1 extends KeyExchange {
    private static final int SSH_MSG_KEXDH_INIT = 30;
    private static final int SSH_MSG_KEXDH_REPLY = 31;
    static final byte[] g = {2};
    static final byte[] p = {0, -1, -1, -1, -1, -1, -1, -1, -1, Opcodes.OPC_jsr_w, 15, -38, Opcodes.OPC_if_icmpge, 33, 104, Opcodes.OPC_monitorenter, 52, Opcodes.OPC_wide, Opcodes.OPC_ifnull, 98, Opcodes.OPC_f2i, -128, -36, 28, -47, 41, 2, 78, 8, Opcodes.OPC_l2d, 103, -52, 116, 2, 11, Opcodes.OPC_arraylength, Opcodes.OPC_if_acmpne, 59, 19, Opcodes.OPC_iflt, 34, 81, 74, 8, 121, Opcodes.OPC_d2i, 52, 4, -35, ByteSourceJsonBootstrapper.UTF8_BOM_1, Opcodes.OPC_fcmpl, 25, Opcodes.OPC_putstatic, -51, 58, 67, 27, 48, 43, 10, 109, MidiConstants.SONG_POSITION_POBYTEER, 95, 20, 55, 79, -31, 53, 109, 109, 81, Opcodes.OPC_monitorenter, 69, -28, Opcodes.OPC_i2l, Opcodes.OPC_putfield, 118, 98, 94, 126, Opcodes.OPC_ifnull, -12, 76, 66, -23, Opcodes.OPC_if_acmpne, 55, -19, 107, 11, -1, 92, Opcodes.OPC_invokevirtual, -12, 6, Opcodes.OPC_invokespecial, -19, -18, 56, 107, -5, 90, Opcodes.OPC_l2f, Opcodes.OPC_if_icmpeq, Opcodes.OPC_if_acmpeq, Opcodes.OPC_freturn, Opcodes.OPC_if_icmpeq, 36, 17, 124, 75, 31, -26, 73, 40, 102, 81, -20, -26, 83, Opcodes.OPC_lor, -1, -1, -1, -1, -1, -1, -1, -1};
    byte[] I_C;
    byte[] I_S;
    byte[] V_C;
    byte[] V_S;
    private Buffer buf;
    DH dh;
    byte[] e;
    private Packet packet;
    private int state;

    @Override // com.jcraft.jsch.KeyExchange
    public void init(Session session, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        this.session = session;
        this.V_S = bArr;
        this.V_C = bArr2;
        this.I_S = bArr3;
        this.I_C = bArr4;
        try {
            HASH hash = (HASH) Class.forName(session.getConfig(Marker.SHA1)).newInstance();
            this.sha = hash;
            this.sha.init();
        } catch (Exception e) {
            System.err.println(e);
        }
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        DH dh = (DH) Class.forName(session.getConfig("dh")).newInstance();
        this.dh = dh;
        dh.init();
        this.dh.setP(p);
        this.dh.setG(g);
        this.e = this.dh.getE();
        this.packet.reset();
        this.buf.putByte((byte) 30);
        this.buf.putMPInt(this.e);
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEXDH_INIT sent");
            JSch.getLogger().log(1, "expecting SSH_MSG_KEXDH_REPLY");
        }
        this.state = 31;
    }

    @Override // com.jcraft.jsch.KeyExchange
    public boolean next(Buffer buffer) throws Exception {
        if (this.state != 31) {
            return false;
        }
        buffer.getInt();
        buffer.getByte();
        int i = buffer.getByte();
        if (i != 31) {
            System.err.println("type: must be 31 " + i);
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
        this.buf.putMPInt(this.e);
        this.buf.putMPInt(mPInt);
        this.buf.putMPInt(this.K);
        int length = this.buf.getLength();
        byte[] bArr = new byte[length];
        this.buf.getByte(bArr);
        this.sha.update(bArr, 0, length);
        this.H = this.sha.digest();
        int i2 = ((this.K_S[0] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((this.K_S[1] << 16) & 16711680) | ((this.K_S[2] << 8) & 65280) | (this.K_S[3] & 255);
        boolean zVerify = verify(Util.byte2str(this.K_S, 4, i2), this.K_S, 4 + i2, string);
        this.state = 0;
        return zVerify;
    }

    @Override // com.jcraft.jsch.KeyExchange
    public int getState() {
        return this.state;
    }
}
