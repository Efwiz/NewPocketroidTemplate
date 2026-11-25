package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import org.eclipse.jgit.transport.SshConstants;
import org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface;

/* JADX INFO: loaded from: classes10.dex */
public class Session implements Runnable {
    private static final int PACKET_MAX_SIZE = 262144;
    static final int SSH_MSG_CHANNEL_CLOSE = 97;
    static final int SSH_MSG_CHANNEL_DATA = 94;
    static final int SSH_MSG_CHANNEL_EOF = 96;
    static final int SSH_MSG_CHANNEL_EXTENDED_DATA = 95;
    static final int SSH_MSG_CHANNEL_FAILURE = 100;
    static final int SSH_MSG_CHANNEL_OPEN = 90;
    static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
    static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
    static final int SSH_MSG_CHANNEL_REQUEST = 98;
    static final int SSH_MSG_CHANNEL_SUCCESS = 99;
    static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
    static final int SSH_MSG_DEBUG = 4;
    static final int SSH_MSG_DISCONNECT = 1;
    static final int SSH_MSG_GLOBAL_REQUEST = 80;
    static final int SSH_MSG_IGNORE = 2;
    static final int SSH_MSG_KEXDH_INIT = 30;
    static final int SSH_MSG_KEXDH_REPLY = 31;
    static final int SSH_MSG_KEXINIT = 20;
    static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
    static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
    static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
    static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
    static final int SSH_MSG_NEWKEYS = 21;
    static final int SSH_MSG_REQUEST_FAILURE = 82;
    static final int SSH_MSG_REQUEST_SUCCESS = 81;
    static final int SSH_MSG_SERVICE_ACCEPT = 6;
    static final int SSH_MSG_SERVICE_REQUEST = 5;
    static final int SSH_MSG_UNIMPLEMENTED = 3;
    static final int buffer_margin = 128;
    private static final byte[] keepalivemsg = Util.str2byte("keepalive@jcraft.com");
    private static final byte[] nomoresessions = Util.str2byte("no-more-sessions@openssh.com");
    static Random random;
    private byte[] Ec2s;
    private byte[] Es2c;
    private byte[] IVc2s;
    private byte[] IVs2c;
    private byte[] I_C;
    private byte[] I_S;
    private byte[] K_S;
    private byte[] MACc2s;
    private byte[] MACs2c;
    private byte[] V_S;
    private Cipher c2scipher;
    private MAC c2smac;
    private Compression deflater;
    String host;
    private Compression inflater;

    /* JADX INFO: renamed from: io, reason: collision with root package name */
    private IO f206io;
    JSch jsch;
    String org_host;
    int port;
    private Cipher s2ccipher;
    private MAC s2cmac;
    private byte[] s2cmac_result1;
    private byte[] s2cmac_result2;
    private byte[] session_id;
    private Socket socket;
    Runnable thread;
    private UserInfo userinfo;
    String username;
    private byte[] V_C = Util.str2byte("SSH-2.0-JSCH-0.1.54");
    private int seqi = 0;
    private int seqo = 0;
    String[] guess = null;
    private int timeout = 0;
    private volatile boolean isConnected = false;
    private boolean isAuthed = false;
    private Thread connectThread = null;
    private Object lock = new Object();
    boolean x11_forwarding = false;
    boolean agent_forwarding = false;
    InputStream in = null;
    OutputStream out = null;
    SocketFactory socket_factory = null;
    private Hashtable config = null;
    private Proxy proxy = null;
    private String hostKeyAlias = null;
    private int serverAliveInterval = 0;
    private int serverAliveCountMax = 1;
    private IdentityRepository identityRepository = null;
    private HostKeyRepository hostkeyRepository = null;
    protected boolean daemon_thread = false;
    private long kex_start_time = 0;
    int max_auth_tries = 6;
    int auth_failures = 0;
    byte[] password = null;
    private volatile boolean in_kex = false;
    private volatile boolean in_prompt = false;
    int[] uncompress_len = new int[1];
    int[] compress_len = new int[1];
    private int s2ccipher_size = 8;
    private int c2scipher_size = 8;
    private GlobalRequestReply grr = new GlobalRequestReply();
    private HostKey hostkey = null;
    Buffer buf = new Buffer();
    Packet packet = new Packet(this.buf);

    Session(JSch jSch, String str, String str2, int i) throws Throwable {
        this.host = LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS;
        this.org_host = LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS;
        this.port = 22;
        this.username = null;
        this.jsch = jSch;
        this.username = str;
        this.host = str2;
        this.org_host = str2;
        this.port = i;
        applyConfig();
        if (this.username == null) {
            try {
                String str3 = (String) System.getProperties().get("user.name");
                this.username = str3;
            } catch (SecurityException unused) {
            }
        }
        if (this.username == null) {
            throw new JSchException("username is not given.");
        }
    }

    public void connect() throws JSchException {
        connect(this.timeout);
    }

    /* JADX WARN: Code restructure failed: missing block: B:207:0x0530, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x0531, code lost:
    
        r16.in_kex = false;
        r16.in_prompt = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0537, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void connect(int r17) throws com.jcraft.jsch.JSchException {
        /*
            Method dump skipped, instruction units count: 1581
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Session.connect(int):void");
    }

    private KeyExchange receive_kexinit(Buffer buffer) throws Exception {
        int i = buffer.getInt();
        if (i != buffer.getLength()) {
            buffer.getByte();
            this.I_S = new byte[buffer.index - 5];
        } else {
            this.I_S = new byte[(i - 1) - buffer.getByte()];
        }
        byte[] bArr = buffer.buffer;
        int i2 = buffer.s;
        byte[] bArr2 = this.I_S;
        System.arraycopy(bArr, i2, bArr2, 0, bArr2.length);
        if (!this.in_kex) {
            send_kexinit();
        }
        String[] strArrGuess = KeyExchange.guess(this.I_S, this.I_C);
        this.guess = strArrGuess;
        if (strArrGuess == null) {
            throw new JSchException("Algorithm negotiation fail");
        }
        if (!this.isAuthed && (strArrGuess[2].equals("none") || this.guess[3].equals("none"))) {
            throw new JSchException("NONE Cipher should not be chosen before authentification is successed.");
        }
        try {
            KeyExchange keyExchange = (KeyExchange) Class.forName(getConfig(this.guess[0])).newInstance();
            keyExchange.init(this, this.V_S, this.V_C, this.I_S, this.I_C);
            return keyExchange;
        } catch (Exception e) {
            throw new JSchException(e.toString(), e);
        }
    }

    public void rekey() throws Exception {
        send_kexinit();
    }

    private void send_kexinit() throws Exception {
        if (this.in_kex) {
            return;
        }
        String config = getConfig("cipher.c2s");
        String config2 = getConfig("cipher.s2c");
        String[] strArrCheckCiphers = checkCiphers(getConfig("CheckCiphers"));
        if (strArrCheckCiphers != null && strArrCheckCiphers.length > 0) {
            config = Util.diffString(config, strArrCheckCiphers);
            config2 = Util.diffString(config2, strArrCheckCiphers);
            if (config == null || config2 == null) {
                throw new JSchException("There are not any available ciphers.");
            }
        }
        String config3 = getConfig("kex");
        String[] strArrCheckKexes = checkKexes(getConfig("CheckKexes"));
        if (strArrCheckKexes != null && strArrCheckKexes.length > 0 && (config3 = Util.diffString(config3, strArrCheckKexes)) == null) {
            throw new JSchException("There are not any available kexes.");
        }
        String config4 = getConfig("server_host_key");
        String[] strArrCheckSignatures = checkSignatures(getConfig("CheckSignatures"));
        if (strArrCheckSignatures != null && strArrCheckSignatures.length > 0 && (config4 = Util.diffString(config4, strArrCheckSignatures)) == null) {
            throw new JSchException("There are not any available sig algorithm.");
        }
        this.in_kex = true;
        this.kex_start_time = System.currentTimeMillis();
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 20);
        synchronized (random) {
            random.fill(buffer.buffer, buffer.index, 16);
            buffer.skip(16);
        }
        buffer.putString(Util.str2byte(config3));
        buffer.putString(Util.str2byte(config4));
        buffer.putString(Util.str2byte(config));
        buffer.putString(Util.str2byte(config2));
        buffer.putString(Util.str2byte(getConfig("mac.c2s")));
        buffer.putString(Util.str2byte(getConfig("mac.s2c")));
        buffer.putString(Util.str2byte(getConfig("compression.c2s")));
        buffer.putString(Util.str2byte(getConfig("compression.s2c")));
        buffer.putString(Util.str2byte(getConfig("lang.c2s")));
        buffer.putString(Util.str2byte(getConfig("lang.s2c")));
        buffer.putByte((byte) 0);
        buffer.putInt(0);
        buffer.setOffSet(5);
        byte[] bArr = new byte[buffer.getLength()];
        this.I_C = bArr;
        buffer.getByte(bArr);
        write(packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEXINIT sent");
        }
    }

    private void send_newkeys() throws Exception {
        this.packet.reset();
        this.buf.putByte((byte) 21);
        write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_NEWKEYS sent");
        }
    }

    private void checkHost(String str, int i, KeyExchange keyExchange) throws JSchException {
        int iCheck;
        String knownHostsRepositoryID;
        boolean zPromptYesNo;
        boolean z;
        String config = getConfig(SshConstants.STRICT_HOST_KEY_CHECKING);
        String str2 = this.hostKeyAlias;
        if (str2 != null) {
            str = str2;
        }
        byte[] hostKey = keyExchange.getHostKey();
        String keyType = keyExchange.getKeyType();
        String fingerPrint = keyExchange.getFingerPrint();
        if (this.hostKeyAlias == null && i != 22) {
            str = "[" + str + "]:" + i;
        }
        HostKeyRepository hostKeyRepository = getHostKeyRepository();
        if (getConfig(SshConstants.HASH_KNOWN_HOSTS).equals("yes") && (hostKeyRepository instanceof KnownHosts)) {
            this.hostkey = ((KnownHosts) hostKeyRepository).createHashedHostKey(str, hostKey);
        } else {
            this.hostkey = new HostKey(str, hostKey);
        }
        synchronized (hostKeyRepository) {
            iCheck = hostKeyRepository.check(str, hostKey);
        }
        if ((config.equals("ask") || config.equals("yes")) && iCheck == 2) {
            synchronized (hostKeyRepository) {
                knownHostsRepositoryID = hostKeyRepository.getKnownHostsRepositoryID();
            }
            if (knownHostsRepositoryID == null) {
                knownHostsRepositoryID = SshConstants.KNOWN_HOSTS;
            }
            if (this.userinfo != null) {
                String str3 = "WARNING: REMOTE HOST IDENTIFICATION HAS CHANGED!\nIT IS POSSIBLE THAT SOMEONE IS DOING SOMETHING NASTY!\nSomeone could be eavesdropping on you right now (man-in-the-middle attack)!\nIt is also possible that the " + keyType + " host key has just been changed.\nThe fingerprint for the " + keyType + " key sent by the remote host " + str + " is\n" + fingerPrint + ".\nPlease contact your system administrator.\nAdd correct host key in " + knownHostsRepositoryID + " to get rid of this message.";
                if (config.equals("ask")) {
                    zPromptYesNo = this.userinfo.promptYesNo(str3 + "\nDo you want to delete the old key and insert the new key?");
                } else {
                    this.userinfo.showMessage(str3);
                    zPromptYesNo = false;
                }
            } else {
                zPromptYesNo = false;
            }
            if (!zPromptYesNo) {
                throw new JSchException("HostKey has been changed: " + str);
            }
            synchronized (hostKeyRepository) {
                hostKeyRepository.remove(str, keyExchange.getKeyAlgorithName(), null);
            }
            z = true;
        } else {
            z = false;
        }
        if ((config.equals("ask") || config.equals("yes")) && iCheck != 0 && !z) {
            if (config.equals("yes")) {
                throw new JSchException("reject HostKey: " + this.host);
            }
            UserInfo userInfo = this.userinfo;
            if (userInfo == null) {
                if (iCheck == 1) {
                    throw new JSchException("UnknownHostKey: " + this.host + ". " + keyType + " key fingerprint is " + fingerPrint);
                }
                throw new JSchException("HostKey has been changed: " + this.host);
            }
            if (!userInfo.promptYesNo("The authenticity of host '" + this.host + "' can't be established.\n" + keyType + " key fingerprint is " + fingerPrint + ".\nAre you sure you want to continue connecting?")) {
                throw new JSchException("reject HostKey: " + this.host);
            }
            z = true;
        }
        if (config.equals("no") && 1 == iCheck) {
            z = true;
        }
        if (iCheck == 0) {
            HostKey[] hostKey2 = hostKeyRepository.getHostKey(str, keyExchange.getKeyAlgorithName());
            String strByte2str = Util.byte2str(Util.toBase64(hostKey, 0, hostKey.length));
            for (HostKey hostKey3 : hostKey2) {
                if (hostKey2[iCheck].getKey().equals(strByte2str) && hostKey3.getMarker().equals("@revoked")) {
                    UserInfo userInfo2 = this.userinfo;
                    if (userInfo2 != null) {
                        userInfo2.showMessage("The " + keyType + " host key for " + this.host + " is marked as revoked.\nThis could mean that a stolen key is being used to impersonate this host.");
                    }
                    if (JSch.getLogger().isEnabled(1)) {
                        JSch.getLogger().log(1, "Host '" + this.host + "' has provided revoked key.");
                    }
                    throw new JSchException("revoked HostKey: " + this.host);
                }
            }
        }
        if (iCheck == 0 && JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "Host '" + this.host + "' is known and matches the " + keyType + " host key");
        }
        if (z && JSch.getLogger().isEnabled(2)) {
            JSch.getLogger().log(2, "Permanently added '" + this.host + "' (" + keyType + ") to the list of known hosts.");
        }
        if (z) {
            synchronized (hostKeyRepository) {
                hostKeyRepository.add(this.hostkey, this.userinfo);
            }
        }
    }

    public Channel openChannel(String str) throws JSchException {
        if (!this.isConnected) {
            throw new JSchException("session is down");
        }
        try {
            Channel channel = Channel.getChannel(str);
            addChannel(channel);
            channel.init();
            if (channel instanceof ChannelSession) {
                applyConfigChannel((ChannelSession) channel);
            }
            return channel;
        } catch (Exception unused) {
            return null;
        }
    }

    public void encode(Packet packet) throws Exception {
        if (this.deflater != null) {
            this.compress_len[0] = packet.buffer.index;
            packet.buffer.buffer = this.deflater.compress(packet.buffer.buffer, 5, this.compress_len);
            packet.buffer.index = this.compress_len[0];
        }
        if (this.c2scipher != null) {
            packet.padding(this.c2scipher_size);
            byte b = packet.buffer.buffer[4];
            synchronized (random) {
                random.fill(packet.buffer.buffer, packet.buffer.index - b, b);
            }
        } else {
            packet.padding(8);
        }
        MAC mac = this.c2smac;
        if (mac != null) {
            mac.update(this.seqo);
            this.c2smac.update(packet.buffer.buffer, 0, packet.buffer.index);
            this.c2smac.doFinal(packet.buffer.buffer, packet.buffer.index);
        }
        if (this.c2scipher != null) {
            byte[] bArr = packet.buffer.buffer;
            this.c2scipher.update(bArr, 0, packet.buffer.index, bArr, 0);
        }
        if (this.c2smac != null) {
            packet.buffer.skip(this.c2smac.getBlockSize());
        }
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v20 ??, new type: int
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException
        */
    public com.jcraft.jsch.Buffer read(com.jcraft.jsch.Buffer r22) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 568
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Session.read(com.jcraft.jsch.Buffer):com.jcraft.jsch.Buffer");
    }

    private void start_discard(Buffer buffer, Cipher cipher, MAC mac, int i, int i2) throws IOException, JSchException {
        if (!cipher.isCBC()) {
            throw new JSchException("Packet corrupt");
        }
        if (i == 262144 || mac == null) {
            mac = null;
        }
        int i3 = i2 - buffer.index;
        while (i3 > 0) {
            buffer.reset();
            int length = i3 > buffer.buffer.length ? buffer.buffer.length : i3;
            this.f206io.getByte(buffer.buffer, 0, length);
            if (mac != null) {
                mac.update(buffer.buffer, 0, length);
            }
            i3 -= length;
        }
        if (mac != null) {
            mac.doFinal(buffer.buffer, 0);
        }
        throw new JSchException("Packet corrupt");
    }

    byte[] getSessionId() {
        return this.session_id;
    }

    private void receive_newkeys(Buffer buffer, KeyExchange keyExchange) throws Exception {
        updateKeys(keyExchange);
        this.in_kex = false;
    }

    private void updateKeys(KeyExchange keyExchange) throws Exception {
        byte[] bArr;
        byte[] k = keyExchange.getK();
        byte[] h = keyExchange.getH();
        HASH hash = keyExchange.getHash();
        if (this.session_id == null) {
            byte[] bArr2 = new byte[h.length];
            this.session_id = bArr2;
            System.arraycopy(h, 0, bArr2, 0, h.length);
        }
        this.buf.reset();
        this.buf.putMPInt(k);
        this.buf.putByte(h);
        this.buf.putByte((byte) 65);
        this.buf.putByte(this.session_id);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.IVc2s = hash.digest();
        int length = (this.buf.index - this.session_id.length) - 1;
        byte[] bArr3 = this.buf.buffer;
        bArr3[length] = (byte) (bArr3[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.IVs2c = hash.digest();
        byte[] bArr4 = this.buf.buffer;
        bArr4[length] = (byte) (bArr4[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.Ec2s = hash.digest();
        byte[] bArr5 = this.buf.buffer;
        bArr5[length] = (byte) (bArr5[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.Es2c = hash.digest();
        byte[] bArr6 = this.buf.buffer;
        bArr6[length] = (byte) (bArr6[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.MACc2s = hash.digest();
        byte[] bArr7 = this.buf.buffer;
        bArr7[length] = (byte) (bArr7[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.MACs2c = hash.digest();
        try {
            Cipher cipher = (Cipher) Class.forName(getConfig(this.guess[3])).newInstance();
            this.s2ccipher = cipher;
            while (true) {
                int blockSize = this.s2ccipher.getBlockSize();
                bArr = this.Es2c;
                if (blockSize <= bArr.length) {
                    break;
                }
                this.buf.reset();
                this.buf.putMPInt(k);
                this.buf.putByte(h);
                this.buf.putByte(this.Es2c);
                hash.update(this.buf.buffer, 0, this.buf.index);
                byte[] bArrDigest = hash.digest();
                byte[] bArr8 = this.Es2c;
                byte[] bArr9 = new byte[bArr8.length + bArrDigest.length];
                System.arraycopy(bArr8, 0, bArr9, 0, bArr8.length);
                System.arraycopy(bArrDigest, 0, bArr9, this.Es2c.length, bArrDigest.length);
                this.Es2c = bArr9;
            }
            this.s2ccipher.init(1, bArr, this.IVs2c);
            this.s2ccipher_size = this.s2ccipher.getIVSize();
            MAC mac = (MAC) Class.forName(getConfig(this.guess[5])).newInstance();
            this.s2cmac = mac;
            byte[] bArrExpandKey = expandKey(this.buf, k, h, this.MACs2c, hash, mac.getBlockSize());
            this.MACs2c = bArrExpandKey;
            this.s2cmac.init(bArrExpandKey);
            this.s2cmac_result1 = new byte[this.s2cmac.getBlockSize()];
            this.s2cmac_result2 = new byte[this.s2cmac.getBlockSize()];
            Cipher cipher2 = (Cipher) Class.forName(getConfig(this.guess[2])).newInstance();
            this.c2scipher = cipher2;
            while (true) {
                int blockSize2 = this.c2scipher.getBlockSize();
                byte[] bArr10 = this.Ec2s;
                if (blockSize2 > bArr10.length) {
                    this.buf.reset();
                    this.buf.putMPInt(k);
                    this.buf.putByte(h);
                    this.buf.putByte(this.Ec2s);
                    hash.update(this.buf.buffer, 0, this.buf.index);
                    byte[] bArrDigest2 = hash.digest();
                    byte[] bArr11 = this.Ec2s;
                    byte[] bArr12 = new byte[bArr11.length + bArrDigest2.length];
                    System.arraycopy(bArr11, 0, bArr12, 0, bArr11.length);
                    System.arraycopy(bArrDigest2, 0, bArr12, this.Ec2s.length, bArrDigest2.length);
                    this.Ec2s = bArr12;
                } else {
                    this.c2scipher.init(0, bArr10, this.IVc2s);
                    this.c2scipher_size = this.c2scipher.getIVSize();
                    MAC mac2 = (MAC) Class.forName(getConfig(this.guess[4])).newInstance();
                    this.c2smac = mac2;
                    byte[] bArrExpandKey2 = expandKey(this.buf, k, h, this.MACc2s, hash, mac2.getBlockSize());
                    this.MACc2s = bArrExpandKey2;
                    this.c2smac.init(bArrExpandKey2);
                    initDeflater(this.guess[6]);
                    initInflater(this.guess[7]);
                    return;
                }
            }
        } catch (Exception e) {
            if (e instanceof JSchException) {
                throw e;
            }
            throw new JSchException(e.toString(), e);
        }
    }

    private byte[] expandKey(Buffer buffer, byte[] bArr, byte[] bArr2, byte[] bArr3, HASH hash, int i) throws Exception {
        int blockSize = hash.getBlockSize();
        while (bArr3.length < i) {
            buffer.reset();
            buffer.putMPInt(bArr);
            buffer.putByte(bArr2);
            buffer.putByte(bArr3);
            hash.update(buffer.buffer, 0, buffer.index);
            byte[] bArr4 = new byte[bArr3.length + blockSize];
            System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
            System.arraycopy(hash.digest(), 0, bArr4, bArr3.length, blockSize);
            Util.bzero(bArr3);
            bArr3 = bArr4;
        }
        return bArr3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0063, code lost:
    
        if (r14.close != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0069, code lost:
    
        if (r14.isConnected() == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x006b, code lost:
    
        monitor-enter(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x006c, code lost:
    
        r3 = 0;
        r3 = 0;
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0071, code lost:
    
        if (r14.rwsize <= 0) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0073, code lost:
    
        r9 = r14.rwsize;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0077, code lost:
    
        if (r9 <= r7) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0079, code lost:
    
        r9 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x007c, code lost:
    
        if (r9 == r7) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x007e, code lost:
    
        r15 = (int) r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0081, code lost:
    
        if (r12.c2scipher == null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0083, code lost:
    
        r2 = r12.c2scipher_size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0086, code lost:
    
        r2 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0088, code lost:
    
        r4 = r12.c2smac;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x008a, code lost:
    
        if (r4 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x008c, code lost:
    
        r3 = r4.getBlockSize();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0090, code lost:
    
        r3 = r13.shift(r15, r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0095, code lost:
    
        r15 = r13.buffer.getCommand();
        r2 = r14.getRecipient();
        r14.rwsize -= r9;
        r11 = r3;
        r3 = r15;
        r15 = (int) (r7 - r9);
        r4 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00ab, code lost:
    
        r2 = -1;
        r4 = 0;
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ae, code lost:
    
        monitor-exit(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00af, code lost:
    
        if (r5 == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b1, code lost:
    
        _write(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b4, code lost:
    
        if (r15 != 0) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00b6, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00b7, code lost:
    
        r13.unshift(r3, r2, r4, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ba, code lost:
    
        monitor-enter(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00bd, code lost:
    
        if (r12.in_kex == false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00bf, code lost:
    
        monitor-exit(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00c2, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00c7, code lost:
    
        if (r14.rwsize < r4) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00c9, code lost:
    
        r14.rwsize -= r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ce, code lost:
    
        monitor-exit(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00d3, code lost:
    
        monitor-exit(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00e3, code lost:
    
        throw new java.io.IOException("channel is broken");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void write(com.jcraft.jsch.Packet r13, com.jcraft.jsch.Channel r14, int r15) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 231
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Session.write(com.jcraft.jsch.Packet, com.jcraft.jsch.Channel, int):void");
    }

    public void write(Packet packet) throws Exception {
        long timeout = getTimeout();
        while (this.in_kex) {
            if (timeout > 0 && System.currentTimeMillis() - this.kex_start_time > timeout && !this.in_prompt) {
                throw new JSchException("timeout in waiting for rekeying process.");
            }
            byte command = packet.buffer.getCommand();
            if (command == 20 || command == 21 || command == 30 || command == 31 || command == 31 || command == 32 || command == 33 || command == 34 || command == 1) {
                break;
            } else {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException unused) {
                }
            }
        }
        _write(packet);
    }

    private void _write(Packet packet) throws Exception {
        synchronized (this.lock) {
            encode(packet);
            IO io2 = this.f206io;
            if (io2 != null) {
                io2.put(packet);
                this.seqo++;
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        int i;
        int i2;
        byte b;
        this.thread = this;
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        while (true) {
            KeyExchange keyExchangeReceive_kexinit = null;
            while (true) {
                int i3 = 0;
                while (true) {
                    try {
                        if (this.isConnected && this.thread != null) {
                            try {
                                buffer = read(buffer);
                                int command = buffer.getCommand() & 255;
                                if (keyExchangeReceive_kexinit == null || keyExchangeReceive_kexinit.getState() != command) {
                                    if (command != 20) {
                                        if (command == 21) {
                                            send_newkeys();
                                            receive_newkeys(buffer, keyExchangeReceive_kexinit);
                                        } else {
                                            switch (command) {
                                                case 80:
                                                    buffer.getInt();
                                                    buffer.getShort();
                                                    buffer.getString();
                                                    if (buffer.getByte() != 0) {
                                                        packet.reset();
                                                        buffer.putByte((byte) 82);
                                                        write(packet);
                                                    }
                                                    break;
                                                case 81:
                                                case 82:
                                                    Thread thread = this.grr.getThread();
                                                    if (thread != null) {
                                                        this.grr.setReply(command == 81 ? 1 : 0);
                                                        if (command == 81 && this.grr.getPort() == 0) {
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            this.grr.setPort(buffer.getInt());
                                                        }
                                                        thread.interrupt();
                                                    }
                                                    break;
                                                default:
                                                    switch (command) {
                                                        case 90:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            String strByte2str = Util.byte2str(buffer.getString());
                                                            if (!"forwarded-tcpip".equals(strByte2str) && ((!"x11".equals(strByte2str) || !this.x11_forwarding) && (!"auth-agent@openssh.com".equals(strByte2str) || !this.agent_forwarding))) {
                                                                packet.reset();
                                                                buffer.putByte((byte) 92);
                                                                buffer.putInt(buffer.getInt());
                                                                buffer.putInt(1);
                                                                buffer.putString(Util.empty);
                                                                buffer.putString(Util.empty);
                                                                write(packet);
                                                            } else {
                                                                Channel channel = Channel.getChannel(strByte2str);
                                                                addChannel(channel);
                                                                channel.getData(buffer);
                                                                channel.init();
                                                                Thread thread2 = new Thread(channel);
                                                                thread2.setName("Channel " + strByte2str + " " + this.host);
                                                                boolean z = this.daemon_thread;
                                                                if (z) {
                                                                    thread2.setDaemon(z);
                                                                }
                                                                thread2.start();
                                                            }
                                                            break;
                                                        case 91:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            Channel channel2 = Channel.getChannel(buffer.getInt(), this);
                                                            int i4 = buffer.getInt();
                                                            long uInt = buffer.getUInt();
                                                            int i5 = buffer.getInt();
                                                            if (channel2 != null) {
                                                                channel2.setRemoteWindowSize(uInt);
                                                                channel2.setRemotePacketSize(i5);
                                                                channel2.open_confirmation = true;
                                                                channel2.setRecipient(i4);
                                                            }
                                                            break;
                                                        case 92:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            Channel channel3 = Channel.getChannel(buffer.getInt(), this);
                                                            if (channel3 != null) {
                                                                channel3.setExitStatus(buffer.getInt());
                                                                channel3.close = true;
                                                                channel3.eof_remote = true;
                                                                channel3.setRecipient(0);
                                                            }
                                                            break;
                                                        case 93:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            Channel channel4 = Channel.getChannel(buffer.getInt(), this);
                                                            if (channel4 != null) {
                                                                channel4.addRemoteWindowSize(buffer.getUInt());
                                                            }
                                                            break;
                                                        case 94:
                                                            buffer.getInt();
                                                            buffer.getByte();
                                                            buffer.getByte();
                                                            Channel channel5 = Channel.getChannel(buffer.getInt(), this);
                                                            byte[] string = buffer.getString(iArr, iArr2);
                                                            if (channel5 != null && (i = iArr2[0]) != 0) {
                                                                try {
                                                                    try {
                                                                        channel5.write(string, iArr[0], i);
                                                                        channel5.setLocalWindowSize(channel5.lwsize - iArr2[0]);
                                                                        if (channel5.lwsize >= channel5.lwsize_max / 2) {
                                                                            continue;
                                                                        } else {
                                                                            packet.reset();
                                                                            buffer.putByte((byte) 93);
                                                                            buffer.putInt(channel5.getRecipient());
                                                                            buffer.putInt(channel5.lwsize_max - channel5.lwsize);
                                                                            synchronized (channel5) {
                                                                                if (!channel5.close) {
                                                                                    write(packet);
                                                                                }
                                                                            }
                                                                            channel5.setLocalWindowSize(channel5.lwsize_max);
                                                                        }
                                                                    } catch (Exception unused) {
                                                                        continue;
                                                                    }
                                                                } catch (Exception unused2) {
                                                                    channel5.disconnect();
                                                                }
                                                            }
                                                            break;
                                                        case 95:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            Channel channel6 = Channel.getChannel(buffer.getInt(), this);
                                                            buffer.getInt();
                                                            byte[] string2 = buffer.getString(iArr, iArr2);
                                                            if (channel6 != null && (i2 = iArr2[0]) != 0) {
                                                                channel6.write_ext(string2, iArr[0], i2);
                                                                channel6.setLocalWindowSize(channel6.lwsize - iArr2[0]);
                                                                if (channel6.lwsize >= channel6.lwsize_max / 2) {
                                                                    continue;
                                                                } else {
                                                                    packet.reset();
                                                                    buffer.putByte((byte) 93);
                                                                    buffer.putInt(channel6.getRecipient());
                                                                    buffer.putInt(channel6.lwsize_max - channel6.lwsize);
                                                                    synchronized (channel6) {
                                                                        if (!channel6.close) {
                                                                            write(packet);
                                                                        }
                                                                    }
                                                                    channel6.setLocalWindowSize(channel6.lwsize_max);
                                                                }
                                                            }
                                                            break;
                                                        case 96:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            Channel channel7 = Channel.getChannel(buffer.getInt(), this);
                                                            if (channel7 != null) {
                                                                channel7.eof_remote();
                                                            }
                                                            break;
                                                        case 97:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            Channel channel8 = Channel.getChannel(buffer.getInt(), this);
                                                            if (channel8 != null) {
                                                                channel8.disconnect();
                                                            }
                                                            break;
                                                        case 98:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            int i6 = buffer.getInt();
                                                            byte[] string3 = buffer.getString();
                                                            boolean z2 = buffer.getByte() != 0;
                                                            Channel channel9 = Channel.getChannel(i6, this);
                                                            if (channel9 != null) {
                                                                if (Util.byte2str(string3).equals("exit-status")) {
                                                                    channel9.setExitStatus(buffer.getInt());
                                                                    b = 99;
                                                                } else {
                                                                    b = 100;
                                                                }
                                                                if (z2) {
                                                                    packet.reset();
                                                                    buffer.putByte(b);
                                                                    buffer.putInt(channel9.getRecipient());
                                                                    write(packet);
                                                                }
                                                            }
                                                            break;
                                                        case 99:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            Channel channel10 = Channel.getChannel(buffer.getInt(), this);
                                                            if (channel10 != null) {
                                                                channel10.reply = 1;
                                                            }
                                                            break;
                                                        case 100:
                                                            buffer.getInt();
                                                            buffer.getShort();
                                                            Channel channel11 = Channel.getChannel(buffer.getInt(), this);
                                                            if (channel11 != null) {
                                                                channel11.reply = 0;
                                                            }
                                                            break;
                                                        default:
                                                            throw new IOException("Unknown SSH message type " + command);
                                                    }
                                                    break;
                                            }
                                        }
                                    } else {
                                        keyExchangeReceive_kexinit = receive_kexinit(buffer);
                                    }
                                } else {
                                    this.kex_start_time = System.currentTimeMillis();
                                    boolean next = keyExchangeReceive_kexinit.next(buffer);
                                    if (!next) {
                                        throw new JSchException("verify: " + next);
                                    }
                                }
                            } catch (InterruptedIOException e) {
                                if (!this.in_kex && i3 < this.serverAliveCountMax) {
                                    sendKeepAliveMsg();
                                } else if (!this.in_kex || i3 >= this.serverAliveCountMax) {
                                    throw e;
                                }
                                i3++;
                            }
                        }
                    } catch (Exception e2) {
                        this.in_kex = false;
                        if (JSch.getLogger().isEnabled(1)) {
                            JSch.getLogger().log(1, "Caught an exception, leaving main loop due to " + e2.getMessage());
                        }
                    }
                }
            }
        }
        try {
            disconnect();
        } catch (NullPointerException | Exception unused3) {
        }
        this.isConnected = false;
    }

    public void disconnect() {
        if (this.isConnected) {
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "Disconnecting from " + this.host + " port " + this.port);
            }
            Channel.disconnect(this);
            this.isConnected = false;
            PortWatcher.delPort(this);
            ChannelForwardedTCPIP.delPort(this);
            ChannelX11.removeFakedCookie(this);
            synchronized (this.lock) {
                if (this.connectThread != null) {
                    Thread.yield();
                    this.connectThread.interrupt();
                    this.connectThread = null;
                }
            }
            this.thread = null;
            try {
                IO io2 = this.f206io;
                if (io2 != null) {
                    if (io2.in != null) {
                        this.f206io.in.close();
                    }
                    if (this.f206io.out != null) {
                        this.f206io.out.close();
                    }
                    if (this.f206io.out_ext != null) {
                        this.f206io.out_ext.close();
                    }
                }
                Proxy proxy = this.proxy;
                if (proxy == null) {
                    Socket socket = this.socket;
                    if (socket != null) {
                        socket.close();
                    }
                } else {
                    synchronized (proxy) {
                        this.proxy.close();
                    }
                    this.proxy = null;
                }
            } catch (Exception unused) {
            }
            this.f206io = null;
            this.socket = null;
            this.jsch.removeSession(this);
        }
    }

    public int setPortForwardingL(int i, String str, int i2) throws JSchException {
        return setPortForwardingL(LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS, i, str, i2);
    }

    public int setPortForwardingL(String str, int i, String str2, int i2) throws JSchException {
        return setPortForwardingL(str, i, str2, i2, null);
    }

    public int setPortForwardingL(String str, int i, String str2, int i2, ServerSocketFactory serverSocketFactory) throws JSchException {
        return setPortForwardingL(str, i, str2, i2, serverSocketFactory, 0);
    }

    public int setPortForwardingL(String str, int i, String str2, int i2, ServerSocketFactory serverSocketFactory, int i3) throws JSchException {
        PortWatcher portWatcherAddPort = PortWatcher.addPort(this, str, i, str2, i2, serverSocketFactory);
        portWatcherAddPort.setConnectTimeout(i3);
        Thread thread = new Thread(portWatcherAddPort);
        thread.setName("PortWatcher Thread for " + str2);
        boolean z = this.daemon_thread;
        if (z) {
            thread.setDaemon(z);
        }
        thread.start();
        return portWatcherAddPort.lport;
    }

    public void delPortForwardingL(int i) throws JSchException {
        delPortForwardingL(LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS, i);
    }

    public void delPortForwardingL(String str, int i) throws JSchException {
        PortWatcher.delPort(this, str, i);
    }

    public String[] getPortForwardingL() throws JSchException {
        return PortWatcher.getPortForwarding(this);
    }

    public void setPortForwardingR(int i, String str, int i2) throws JSchException {
        setPortForwardingR(null, i, str, i2, null);
    }

    public void setPortForwardingR(String str, int i, String str2, int i2) throws JSchException {
        setPortForwardingR(str, i, str2, i2, null);
    }

    public void setPortForwardingR(int i, String str, int i2, SocketFactory socketFactory) throws JSchException {
        setPortForwardingR(null, i, str, i2, socketFactory);
    }

    public void setPortForwardingR(String str, int i, String str2, int i2, SocketFactory socketFactory) throws JSchException {
        ChannelForwardedTCPIP.addPort(this, str, i, _setPortForwardingR(str, i), str2, i2, socketFactory);
    }

    public void setPortForwardingR(int i, String str) throws JSchException {
        setPortForwardingR((String) null, i, str, (Object[]) null);
    }

    public void setPortForwardingR(int i, String str, Object[] objArr) throws JSchException {
        setPortForwardingR((String) null, i, str, objArr);
    }

    public void setPortForwardingR(String str, int i, String str2, Object[] objArr) throws JSchException {
        ChannelForwardedTCPIP.addPort(this, str, i, _setPortForwardingR(str, i), str2, objArr);
    }

    public String[] getPortForwardingR() throws JSchException {
        return ChannelForwardedTCPIP.getPortForwarding(this);
    }

    private class Forwarding {
        String bind_address;
        String host;
        int hostport;
        int port;

        private Forwarding() {
            this.bind_address = null;
            this.port = -1;
            this.host = null;
            this.hostport = -1;
        }
    }

    private Forwarding parseForwarding(String str) throws JSchException {
        String[] strArrSplit = str.split(" ");
        if (strArrSplit.length > 1) {
            Vector vector = new Vector();
            for (int i = 0; i < strArrSplit.length; i++) {
                if (strArrSplit[i].length() != 0) {
                    vector.addElement(strArrSplit[i].trim());
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            int i2 = 0;
            while (i2 < vector.size()) {
                stringBuffer.append((String) vector.elementAt(i2));
                i2++;
                if (i2 < vector.size()) {
                    stringBuffer.append(":");
                }
            }
            str = stringBuffer.toString();
        }
        Forwarding forwarding = new Forwarding();
        try {
            if (str.lastIndexOf(":") == -1) {
                throw new JSchException("parseForwarding: " + str);
            }
            forwarding.hostport = Integer.parseInt(str.substring(str.lastIndexOf(":") + 1));
            String strSubstring = str.substring(0, str.lastIndexOf(":"));
            if (strSubstring.lastIndexOf(":") == -1) {
                throw new JSchException("parseForwarding: " + str);
            }
            forwarding.host = strSubstring.substring(strSubstring.lastIndexOf(":") + 1);
            String strSubstring2 = strSubstring.substring(0, strSubstring.lastIndexOf(":"));
            int iLastIndexOf = strSubstring2.lastIndexOf(":");
            String str2 = LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS;
            if (iLastIndexOf != -1) {
                forwarding.port = Integer.parseInt(strSubstring2.substring(strSubstring2.lastIndexOf(":") + 1));
                String strSubstring3 = strSubstring2.substring(0, strSubstring2.lastIndexOf(":"));
                if (strSubstring3.length() == 0 || strSubstring3.equals("*")) {
                    strSubstring3 = "0.0.0.0";
                }
                if (!strSubstring3.equals("localhost")) {
                    str2 = strSubstring3;
                }
                forwarding.bind_address = str2;
            } else {
                forwarding.port = Integer.parseInt(strSubstring2);
                forwarding.bind_address = LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS;
            }
            return forwarding;
        } catch (NumberFormatException e) {
            throw new JSchException("parseForwarding: " + e.toString());
        }
    }

    public int setPortForwardingL(String str) throws JSchException {
        Forwarding forwarding = parseForwarding(str);
        return setPortForwardingL(forwarding.bind_address, forwarding.port, forwarding.host, forwarding.hostport);
    }

    public int setPortForwardingR(String str) throws JSchException {
        Forwarding forwarding = parseForwarding(str);
        int i_setPortForwardingR = _setPortForwardingR(forwarding.bind_address, forwarding.port);
        ChannelForwardedTCPIP.addPort(this, forwarding.bind_address, forwarding.port, i_setPortForwardingR, forwarding.host, forwarding.hostport, null);
        return i_setPortForwardingR;
    }

    public Channel getStreamForwarder(String str, int i) throws JSchException {
        ChannelDirectTCPIP channelDirectTCPIP = new ChannelDirectTCPIP();
        channelDirectTCPIP.init();
        addChannel(channelDirectTCPIP);
        channelDirectTCPIP.setHost(str);
        channelDirectTCPIP.setPort(i);
        return channelDirectTCPIP;
    }

    private class GlobalRequestReply {
        private int port;
        private int reply;
        private Thread thread;

        private GlobalRequestReply() {
            this.thread = null;
            this.reply = -1;
            this.port = 0;
        }

        void setThread(Thread thread) {
            this.thread = thread;
            this.reply = -1;
        }

        Thread getThread() {
            return this.thread;
        }

        void setReply(int i) {
            this.reply = i;
        }

        int getReply() {
            return this.reply;
        }

        int getPort() {
            return this.port;
        }

        void setPort(int i) {
            this.port = i;
        }
    }

    private int _setPortForwardingR(String str, int i) throws JSchException {
        int port;
        synchronized (this.grr) {
            Buffer buffer = new Buffer(100);
            Packet packet = new Packet(buffer);
            String strNormalize = ChannelForwardedTCPIP.normalize(str);
            this.grr.setThread(Thread.currentThread());
            this.grr.setPort(i);
            try {
                packet.reset();
                buffer.putByte((byte) 80);
                buffer.putString(Util.str2byte("tcpip-forward"));
                buffer.putByte((byte) 1);
                buffer.putString(Util.str2byte(strNormalize));
                buffer.putInt(i);
                write(packet);
                int reply = this.grr.getReply();
                int i2 = 0;
                while (i2 < 10 && reply == -1) {
                    try {
                        Thread.sleep(1000L);
                    } catch (Exception unused) {
                    }
                    i2++;
                    reply = this.grr.getReply();
                }
                this.grr.setThread(null);
                if (reply != 1) {
                    throw new JSchException("remote port forwarding failed for listen port " + i);
                }
                port = this.grr.getPort();
            } catch (Exception e) {
                this.grr.setThread(null);
                if (e instanceof Throwable) {
                    throw new JSchException(e.toString(), e);
                }
                throw new JSchException(e.toString());
            }
        }
        return port;
    }

    public void delPortForwardingR(int i) throws JSchException {
        delPortForwardingR(null, i);
    }

    public void delPortForwardingR(String str, int i) throws JSchException {
        ChannelForwardedTCPIP.delPort(this, str, i);
    }

    private void initDeflater(String str) throws JSchException {
        int i;
        if (str.equals("none")) {
            this.deflater = null;
            return;
        }
        String config = getConfig(str);
        if (config != null) {
            if (str.equals("zlib") || (this.isAuthed && str.equals("zlib@openssh.com"))) {
                try {
                    try {
                        Compression compression = (Compression) Class.forName(config).newInstance();
                        this.deflater = compression;
                        try {
                            i = Integer.parseInt(getConfig("compression_level"));
                        } catch (Exception unused) {
                            i = 6;
                        }
                        this.deflater.init(1, i);
                    } catch (NoClassDefFoundError e) {
                        throw new JSchException(e.toString(), e);
                    }
                } catch (Exception e2) {
                    throw new JSchException(e2.toString(), e2);
                }
            }
        }
    }

    private void initInflater(String str) throws JSchException {
        if (str.equals("none")) {
            this.inflater = null;
            return;
        }
        String config = getConfig(str);
        if (config != null) {
            if (str.equals("zlib") || (this.isAuthed && str.equals("zlib@openssh.com"))) {
                try {
                    Compression compression = (Compression) Class.forName(config).newInstance();
                    this.inflater = compression;
                    compression.init(0, 0);
                } catch (Exception e) {
                    throw new JSchException(e.toString(), e);
                }
            }
        }
    }

    void addChannel(Channel channel) {
        channel.setSession(this);
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setPort(int i) {
        this.port = i;
    }

    void setUserName(String str) {
        this.username = str;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userinfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return this.userinfo;
    }

    public void setInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void setX11Host(String str) {
        ChannelX11.setHost(str);
    }

    public void setX11Port(int i) {
        ChannelX11.setPort(i);
    }

    public void setX11Cookie(String str) {
        ChannelX11.setCookie(str);
    }

    public void setPassword(String str) {
        if (str != null) {
            this.password = Util.str2byte(str);
        }
    }

    public void setPassword(byte[] bArr) {
        if (bArr != null) {
            byte[] bArr2 = new byte[bArr.length];
            this.password = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        }
    }

    public void setConfig(Properties properties) {
        setConfig((Hashtable) properties);
    }

    public void setConfig(Hashtable hashtable) {
        synchronized (this.lock) {
            if (this.config == null) {
                this.config = new Hashtable();
            }
            Enumeration enumerationKeys = hashtable.keys();
            while (enumerationKeys.hasMoreElements()) {
                String str = (String) enumerationKeys.nextElement();
                Hashtable hashtable2 = this.config;
                String str2 = (String) hashtable.get(str);
                hashtable2.put(str, str2);
            }
        }
    }

    public void setConfig(String str, String str2) {
        synchronized (this.lock) {
            if (this.config == null) {
                this.config = new Hashtable();
            }
            this.config.put(str, str2);
        }
    }

    public String getConfig(String str) {
        Hashtable hashtable = this.config;
        if (hashtable != null) {
            Object obj = hashtable.get(str);
            if (obj instanceof String) {
                return (String) obj;
            }
        }
        String config = JSch.getConfig(str);
        if (config instanceof String) {
            return config;
        }
        return null;
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this.socket_factory = socketFactory;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int i) throws JSchException {
        Socket socket = this.socket;
        if (socket == null) {
            if (i < 0) {
                throw new JSchException("invalid timeout value");
            }
            this.timeout = i;
        } else {
            try {
                socket.setSoTimeout(i);
                this.timeout = i;
            } catch (Exception e) {
                if (e instanceof Throwable) {
                    throw new JSchException(e.toString(), e);
                }
                throw new JSchException(e.toString());
            }
        }
    }

    public String getServerVersion() {
        return Util.byte2str(this.V_S);
    }

    public String getClientVersion() {
        return Util.byte2str(this.V_C);
    }

    public void setClientVersion(String str) {
        this.V_C = Util.str2byte(str);
    }

    public void sendIgnore() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 2);
        write(packet);
    }

    public void sendKeepAliveMsg() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 80);
        buffer.putString(keepalivemsg);
        buffer.putByte((byte) 1);
        write(packet);
    }

    public void noMoreSessionChannels() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 80);
        buffer.putString(nomoresessions);
        buffer.putByte((byte) 0);
        write(packet);
    }

    public HostKey getHostKey() {
        return this.hostkey;
    }

    public String getHost() {
        return this.host;
    }

    public String getUserName() {
        return this.username;
    }

    public int getPort() {
        return this.port;
    }

    public void setHostKeyAlias(String str) {
        this.hostKeyAlias = str;
    }

    public String getHostKeyAlias() {
        return this.hostKeyAlias;
    }

    public void setServerAliveInterval(int i) throws JSchException {
        setTimeout(i);
        this.serverAliveInterval = i;
    }

    public int getServerAliveInterval() {
        return this.serverAliveInterval;
    }

    public void setServerAliveCountMax(int i) {
        this.serverAliveCountMax = i;
    }

    public int getServerAliveCountMax() {
        return this.serverAliveCountMax;
    }

    public void setDaemonThread(boolean z) {
        this.daemon_thread = z;
    }

    private String[] checkCiphers(String str) {
        String[] strArr = null;
        if (str != null && str.length() != 0) {
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "CheckCiphers: " + str);
            }
            String config = getConfig("cipher.c2s");
            String config2 = getConfig("cipher.s2c");
            Vector vector = new Vector();
            for (String str2 : Util.split(str, ",")) {
                if ((config2.indexOf(str2) != -1 || config.indexOf(str2) != -1) && !checkCipher(getConfig(str2))) {
                    vector.addElement(str2);
                }
            }
            if (vector.size() == 0) {
                return null;
            }
            int size = vector.size();
            strArr = new String[size];
            System.arraycopy(vector.toArray(), 0, strArr, 0, vector.size());
            if (JSch.getLogger().isEnabled(1)) {
                for (int i = 0; i < size; i++) {
                    JSch.getLogger().log(1, strArr[i] + " is not available.");
                }
            }
        }
        return strArr;
    }

    static boolean checkCipher(String str) {
        try {
            Cipher cipher = (Cipher) Class.forName(str).newInstance();
            cipher.init(0, new byte[cipher.getBlockSize()], new byte[cipher.getIVSize()]);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private String[] checkKexes(String str) {
        String[] strArr = null;
        if (str != null && str.length() != 0) {
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "CheckKexes: " + str);
            }
            Vector vector = new Vector();
            String[] strArrSplit = Util.split(str, ",");
            for (int i = 0; i < strArrSplit.length; i++) {
                if (!checkKex(this, getConfig(strArrSplit[i]))) {
                    vector.addElement(strArrSplit[i]);
                }
            }
            if (vector.size() == 0) {
                return null;
            }
            int size = vector.size();
            strArr = new String[size];
            System.arraycopy(vector.toArray(), 0, strArr, 0, vector.size());
            if (JSch.getLogger().isEnabled(1)) {
                for (int i2 = 0; i2 < size; i2++) {
                    JSch.getLogger().log(1, strArr[i2] + " is not available.");
                }
            }
        }
        return strArr;
    }

    static boolean checkKex(Session session, String str) {
        try {
            KeyExchange keyExchange = (KeyExchange) Class.forName(str).newInstance();
            keyExchange.init(session, null, null, null, null);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private String[] checkSignatures(String str) {
        String[] strArr = null;
        if (str != null && str.length() != 0) {
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "CheckSignatures: " + str);
            }
            Vector vector = new Vector();
            String[] strArrSplit = Util.split(str, ",");
            for (int i = 0; i < strArrSplit.length; i++) {
                try {
                    Signature signature = (Signature) Class.forName(JSch.getConfig(strArrSplit[i])).newInstance();
                    signature.init();
                } catch (Exception unused) {
                    vector.addElement(strArrSplit[i]);
                }
            }
            if (vector.size() == 0) {
                return null;
            }
            int size = vector.size();
            strArr = new String[size];
            System.arraycopy(vector.toArray(), 0, strArr, 0, vector.size());
            if (JSch.getLogger().isEnabled(1)) {
                for (int i2 = 0; i2 < size; i2++) {
                    JSch.getLogger().log(1, strArr[i2] + " is not available.");
                }
            }
        }
        return strArr;
    }

    public void setIdentityRepository(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    IdentityRepository getIdentityRepository() {
        IdentityRepository identityRepository = this.identityRepository;
        return identityRepository == null ? this.jsch.getIdentityRepository() : identityRepository;
    }

    public void setHostKeyRepository(HostKeyRepository hostKeyRepository) {
        this.hostkeyRepository = hostKeyRepository;
    }

    public HostKeyRepository getHostKeyRepository() {
        HostKeyRepository hostKeyRepository = this.hostkeyRepository;
        return hostKeyRepository == null ? this.jsch.getHostKeyRepository() : hostKeyRepository;
    }

    private void applyConfig() throws Throwable {
        String user;
        ConfigRepository configRepository = this.jsch.getConfigRepository();
        if (configRepository == null) {
            return;
        }
        ConfigRepository.Config config = configRepository.getConfig(this.org_host);
        if (this.username == null && (user = config.getUser()) != null) {
            this.username = user;
        }
        String hostname = config.getHostname();
        if (hostname != null) {
            this.host = hostname;
        }
        int port = config.getPort();
        if (port != -1) {
            this.port = port;
        }
        checkConfig(config, "kex");
        checkConfig(config, "server_host_key");
        checkConfig(config, "cipher.c2s");
        checkConfig(config, "cipher.s2c");
        checkConfig(config, "mac.c2s");
        checkConfig(config, "mac.s2c");
        checkConfig(config, "compression.c2s");
        checkConfig(config, "compression.s2c");
        checkConfig(config, "compression_level");
        checkConfig(config, SshConstants.STRICT_HOST_KEY_CHECKING);
        checkConfig(config, SshConstants.HASH_KNOWN_HOSTS);
        checkConfig(config, SshConstants.PREFERRED_AUTHENTICATIONS);
        checkConfig(config, "MaxAuthTries");
        checkConfig(config, "ClearAllForwardings");
        String value = config.getValue("HostKeyAlias");
        if (value != null) {
            setHostKeyAlias(value);
        }
        String value2 = config.getValue(SshConstants.USER_KNOWN_HOSTS_FILE);
        if (value2 != null) {
            KnownHosts knownHosts = new KnownHosts(this.jsch);
            knownHosts.setKnownHosts(value2);
            setHostKeyRepository(knownHosts);
        }
        String[] values = config.getValues(SshConstants.IDENTITY_FILE);
        if (values != null) {
            String[] values2 = configRepository.getConfig("").getValues(SshConstants.IDENTITY_FILE);
            if (values2 != null) {
                for (String str : values2) {
                    this.jsch.addIdentity(str);
                }
            } else {
                values2 = new String[0];
            }
            if (values.length - values2.length > 0) {
                IdentityRepository.Wrapper wrapper = new IdentityRepository.Wrapper(this.jsch.getIdentityRepository(), true);
                for (String str2 : values) {
                    for (String str3 : values2) {
                        if (str2.equals(str3)) {
                            str2 = null;
                            break;
                        }
                    }
                    if (str2 != null) {
                        wrapper.add(IdentityFile.newInstance(str2, null, this.jsch));
                    }
                }
                setIdentityRepository(wrapper);
            }
        }
        String value3 = config.getValue("ServerAliveInterval");
        if (value3 != null) {
            try {
                setServerAliveInterval(Integer.parseInt(value3));
            } catch (NumberFormatException unused) {
            }
        }
        String value4 = config.getValue("ConnectTimeout");
        if (value4 != null) {
            try {
                setTimeout(Integer.parseInt(value4));
            } catch (NumberFormatException unused2) {
            }
        }
        String value5 = config.getValue("MaxAuthTries");
        if (value5 != null) {
            setConfig("MaxAuthTries", value5);
        }
        String value6 = config.getValue("ClearAllForwardings");
        if (value6 != null) {
            setConfig("ClearAllForwardings", value6);
        }
    }

    private void applyConfigChannel(ChannelSession channelSession) throws JSchException {
        ConfigRepository configRepository = this.jsch.getConfigRepository();
        if (configRepository == null) {
            return;
        }
        ConfigRepository.Config config = configRepository.getConfig(this.org_host);
        String value = config.getValue("ForwardAgent");
        if (value != null) {
            channelSession.setAgentForwarding(value.equals("yes"));
        }
        String value2 = config.getValue("RequestTTY");
        if (value2 != null) {
            channelSession.setPty(value2.equals("yes"));
        }
    }

    private void requestPortForwarding() throws JSchException {
        ConfigRepository configRepository;
        if (getConfig("ClearAllForwardings").equals("yes") || (configRepository = this.jsch.getConfigRepository()) == null) {
            return;
        }
        ConfigRepository.Config config = configRepository.getConfig(this.org_host);
        String[] values = config.getValues(SshConstants.LOCAL_FORWARD);
        if (values != null) {
            for (String str : values) {
                setPortForwardingL(str);
            }
        }
        String[] values2 = config.getValues(SshConstants.REMOTE_FORWARD);
        if (values2 != null) {
            for (String str2 : values2) {
                setPortForwardingR(str2);
            }
        }
    }

    private void checkConfig(ConfigRepository.Config config, String str) {
        String value = config.getValue(str);
        if (value != null) {
            setConfig(str, value);
        }
    }
}
