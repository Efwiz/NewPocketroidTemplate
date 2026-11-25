package com.jcraft.jsch;

import com.beust.jcommander.Parameters;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes10.dex */
public class ChannelSftp extends ChannelSession {
    public static final int APPEND = 2;
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 32768;
    private static final int LOCAL_WINDOW_SIZE_MAX = 2097152;
    private static final int MAX_MSG_LENGTH = 262144;
    public static final int OVERWRITE = 0;
    public static final int RESUME = 1;
    private static final int SSH_FILEXFER_ATTR_ACMODTIME = 8;
    private static final int SSH_FILEXFER_ATTR_EXTENDED = Integer.MIN_VALUE;
    private static final int SSH_FILEXFER_ATTR_PERMISSIONS = 4;
    private static final int SSH_FILEXFER_ATTR_SIZE = 1;
    private static final int SSH_FILEXFER_ATTR_UIDGID = 2;
    private static final int SSH_FXF_APPEND = 4;
    private static final int SSH_FXF_CREAT = 8;
    private static final int SSH_FXF_EXCL = 32;
    private static final int SSH_FXF_READ = 1;
    private static final int SSH_FXF_TRUNC = 16;
    private static final int SSH_FXF_WRITE = 2;
    private static final byte SSH_FXP_ATTRS = 105;
    private static final byte SSH_FXP_CLOSE = 4;
    private static final byte SSH_FXP_DATA = 103;
    private static final byte SSH_FXP_EXTENDED = -56;
    private static final byte SSH_FXP_EXTENDED_REPLY = -55;
    private static final byte SSH_FXP_FSETSTAT = 10;
    private static final byte SSH_FXP_FSTAT = 8;
    private static final byte SSH_FXP_HANDLE = 102;
    private static final byte SSH_FXP_INIT = 1;
    private static final byte SSH_FXP_LSTAT = 7;
    private static final byte SSH_FXP_MKDIR = 14;
    private static final byte SSH_FXP_NAME = 104;
    private static final byte SSH_FXP_OPEN = 3;
    private static final byte SSH_FXP_OPENDIR = 11;
    private static final byte SSH_FXP_READ = 5;
    private static final byte SSH_FXP_READDIR = 12;
    private static final byte SSH_FXP_READLINK = 19;
    private static final byte SSH_FXP_REALPATH = 16;
    private static final byte SSH_FXP_REMOVE = 13;
    private static final byte SSH_FXP_RENAME = 18;
    private static final byte SSH_FXP_RMDIR = 15;
    private static final byte SSH_FXP_SETSTAT = 9;
    private static final byte SSH_FXP_STAT = 17;
    private static final byte SSH_FXP_STATUS = 101;
    private static final byte SSH_FXP_SYMLINK = 20;
    private static final byte SSH_FXP_VERSION = 2;
    private static final byte SSH_FXP_WRITE = 6;
    public static final int SSH_FX_BAD_MESSAGE = 5;
    public static final int SSH_FX_CONNECTION_LOST = 7;
    public static final int SSH_FX_EOF = 1;
    public static final int SSH_FX_FAILURE = 4;
    public static final int SSH_FX_NO_CONNECTION = 6;
    public static final int SSH_FX_NO_SUCH_FILE = 2;
    public static final int SSH_FX_OK = 0;
    public static final int SSH_FX_OP_UNSUPPORTED = 8;
    public static final int SSH_FX_PERMISSION_DENIED = 3;
    private static final String UTF8 = "UTF-8";
    private static final String file_separator = File.separator;
    private static final char file_separatorc = File.separatorChar;
    private static boolean fs_is_bs;
    private Buffer buf;
    private String cwd;
    private String home;
    private String lcwd;
    private Buffer obuf;
    private Packet opacket;
    private Packet packet;
    private boolean interactive = false;
    private int seq = 1;
    private int[] ackid = new int[1];
    private int client_version = 3;
    private int server_version = 3;
    private String version = String.valueOf(3);
    private Hashtable extensions = null;
    private InputStream io_in = null;
    private boolean extension_posix_rename = false;
    private boolean extension_statvfs = false;
    private boolean extension_hardlink = false;
    private String fEncoding = "UTF-8";
    private boolean fEncoding_is_utf8 = true;
    private RequestQueue rq = new RequestQueue(16);

    public interface LsEntrySelector {
        public static final int BREAK = 1;
        public static final int CONTINUE = 0;

        int select(LsEntry lsEntry);
    }

    @Override // com.jcraft.jsch.Channel
    void init() {
    }

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
    public /* bridge */ /* synthetic */ void setPty(boolean z) {
        super.setPty(z);
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
    public /* bridge */ /* synthetic */ void setXForwarding(boolean z) {
        super.setXForwarding(z);
    }

    static {
        fs_is_bs = ((byte) File.separatorChar) == 92;
    }

    public void setBulkRequests(int i) throws JSchException {
        if (i > 0) {
            this.rq = new RequestQueue(i);
            return;
        }
        throw new JSchException("setBulkRequests: " + i + " must be greater than 0.");
    }

    public int getBulkRequests() {
        return this.rq.size();
    }

    public ChannelSftp() {
        setLocalWindowSizeMax(2097152);
        setLocalWindowSize(2097152);
        setLocalPacketSize(32768);
    }

    @Override // com.jcraft.jsch.Channel
    public void start() throws JSchException {
        try {
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            this.f201io.setOutputStream(pipedOutputStream);
            this.f201io.setInputStream(new Channel.MyPipedInputStream(pipedOutputStream, this.rmpsize));
            InputStream inputStream = this.f201io.in;
            this.io_in = inputStream;
            if (inputStream == null) {
                throw new JSchException("channel is down");
            }
            new RequestSftp().request(getSession(), this);
            this.buf = new Buffer(this.lmpsize);
            this.packet = new Packet(this.buf);
            this.obuf = new Buffer(this.rmpsize);
            this.opacket = new Packet(this.obuf);
            sendINIT();
            Header header = header(this.buf, new Header());
            int length = header.length;
            if (length > 262144) {
                throw new SftpException(4, "Received message is too long: " + length);
            }
            int i = header.type;
            this.server_version = header.rid;
            this.extensions = new Hashtable();
            if (length > 0) {
                fill(this.buf, length);
                while (length > 0) {
                    byte[] string = this.buf.getString();
                    int length2 = length - (string.length + 4);
                    byte[] string2 = this.buf.getString();
                    length = length2 - (string2.length + 4);
                    this.extensions.put(Util.byte2str(string), Util.byte2str(string2));
                }
            }
            if (this.extensions.get("posix-rename@openssh.com") != null && this.extensions.get("posix-rename@openssh.com").equals(SchemaSymbols.ATTVAL_TRUE_1)) {
                this.extension_posix_rename = true;
            }
            if (this.extensions.get("statvfs@openssh.com") != null && this.extensions.get("statvfs@openssh.com").equals("2")) {
                this.extension_statvfs = true;
            }
            if (this.extensions.get("hardlink@openssh.com") != null && this.extensions.get("hardlink@openssh.com").equals(SchemaSymbols.ATTVAL_TRUE_1)) {
                this.extension_hardlink = true;
            }
            this.lcwd = new File(".").getCanonicalPath();
        } catch (Exception e) {
            if (e instanceof JSchException) {
                throw ((JSchException) e);
            }
            if (!(e instanceof Throwable)) {
                throw new JSchException(e.toString());
            }
            throw new JSchException(e.toString(), e);
        }
    }

    public void quit() {
        disconnect();
    }

    public void exit() {
        disconnect();
    }

    public void lcd(String str) throws SftpException {
        String strLocalAbsolutePath = localAbsolutePath(str);
        if (new File(strLocalAbsolutePath).isDirectory()) {
            try {
                strLocalAbsolutePath = new File(strLocalAbsolutePath).getCanonicalPath();
            } catch (Exception unused) {
            }
            this.lcwd = strLocalAbsolutePath;
            return;
        }
        throw new SftpException(2, "No such directory");
    }

    public void cd(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strIsUnique = isUnique(remoteAbsolutePath(str));
            byte[] bArr_realpath = _realpath(strIsUnique);
            SftpATTRS sftpATTRS_stat = _stat(bArr_realpath);
            if ((sftpATTRS_stat.getFlags() & 4) == 0) {
                throw new SftpException(4, "Can't change directory: " + strIsUnique);
            }
            if (!sftpATTRS_stat.isDir()) {
                throw new SftpException(4, "Can't change directory: " + strIsUnique);
            }
            setCwd(Util.byte2str(bArr_realpath, this.fEncoding));
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void put(String str, String str2) throws Throwable {
        put(str, str2, (SftpProgressMonitor) null, 0);
    }

    public void put(String str, String str2, int i) throws Throwable {
        put(str, str2, (SftpProgressMonitor) null, i);
    }

    public void put(String str, String str2, SftpProgressMonitor sftpProgressMonitor) throws Throwable {
        put(str, str2, sftpProgressMonitor, 0);
    }

    public void put(String str, String str2, SftpProgressMonitor sftpProgressMonitor, int i) throws Throwable {
        String str3;
        StringBuffer stringBuffer;
        String str4;
        String str5;
        int i2;
        String str6;
        StringBuffer stringBuffer2;
        FileInputStream fileInputStream;
        int iLastIndexOf;
        int i3 = 4;
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strLocalAbsolutePath = localAbsolutePath(str);
            String strRemoteAbsolutePath = remoteAbsolutePath(str2);
            Vector vectorGlob_remote = glob_remote(strRemoteAbsolutePath);
            int size = vectorGlob_remote.size();
            if (size != 1) {
                if (size == 0) {
                    if (isPattern(strRemoteAbsolutePath)) {
                        throw new SftpException(4, strRemoteAbsolutePath);
                    }
                    Util.unquote(strRemoteAbsolutePath);
                }
                throw new SftpException(4, vectorGlob_remote.toString());
            }
            String str7 = (String) vectorGlob_remote.elementAt(0);
            boolean zIsRemoteDir = isRemoteDir(str7);
            Vector vectorGlob_local = glob_local(strLocalAbsolutePath);
            int size2 = vectorGlob_local.size();
            if (zIsRemoteDir) {
                if (!str7.endsWith("/")) {
                    str7 = str7 + "/";
                }
                stringBuffer = new StringBuffer(str7);
                str3 = str7;
            } else {
                if (size2 > 1) {
                    throw new SftpException(4, "Copying multiple files, but the destination is missing or a file.");
                }
                str3 = str7;
                stringBuffer = null;
            }
            int i4 = 0;
            while (i4 < size2) {
                String str8 = (String) vectorGlob_local.elementAt(i4);
                if (zIsRemoteDir) {
                    int iLastIndexOf2 = str8.lastIndexOf(file_separatorc);
                    if (fs_is_bs && (iLastIndexOf = str8.lastIndexOf(47)) != -1 && iLastIndexOf > iLastIndexOf2) {
                        iLastIndexOf2 = iLastIndexOf;
                    }
                    if (iLastIndexOf2 == -1) {
                        stringBuffer.append(str8);
                    } else {
                        stringBuffer.append(str8.substring(iLastIndexOf2 + 1));
                    }
                    String string = stringBuffer.toString();
                    stringBuffer.delete(str3.length(), string.length());
                    str4 = string;
                } else {
                    str4 = str3;
                }
                long size3 = 0;
                if (i == 1) {
                    try {
                        size3 = _stat(str4).getSize();
                    } catch (Exception unused) {
                    }
                    long length = new File(str8).length();
                    if (length < size3) {
                        throw new SftpException(i3, "failed to resume for " + str4);
                    }
                    if (length == size3) {
                        return;
                    }
                }
                long j = size3;
                if (sftpProgressMonitor != null) {
                    stringBuffer2 = stringBuffer;
                    str5 = str4;
                    i2 = i4;
                    str6 = str8;
                    sftpProgressMonitor.init(0, str8, str4, new File(str8).length());
                    if (i == 1) {
                        sftpProgressMonitor.count(j);
                    }
                } else {
                    str5 = str4;
                    i2 = i4;
                    str6 = str8;
                    stringBuffer2 = stringBuffer;
                }
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(str6);
                    try {
                        _put(fileInputStream2, str5, sftpProgressMonitor, i);
                        fileInputStream2.close();
                        i4 = i2 + 1;
                        stringBuffer = stringBuffer2;
                        i3 = 4;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = null;
                }
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, e.toString(), e);
            }
            throw new SftpException(4, e.toString());
        }
    }

    public void put(InputStream inputStream, String str) throws SftpException {
        put(inputStream, str, (SftpProgressMonitor) null, 0);
    }

    public void put(InputStream inputStream, String str, int i) throws SftpException {
        put(inputStream, str, (SftpProgressMonitor) null, i);
    }

    public void put(InputStream inputStream, String str, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        put(inputStream, str, sftpProgressMonitor, 0);
    }

    public void put(InputStream inputStream, String str, SftpProgressMonitor sftpProgressMonitor, int i) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strRemoteAbsolutePath = remoteAbsolutePath(str);
            Vector vectorGlob_remote = glob_remote(strRemoteAbsolutePath);
            int size = vectorGlob_remote.size();
            if (size != 1) {
                if (size == 0) {
                    if (isPattern(strRemoteAbsolutePath)) {
                        throw new SftpException(4, strRemoteAbsolutePath);
                    }
                    Util.unquote(strRemoteAbsolutePath);
                }
                throw new SftpException(4, vectorGlob_remote.toString());
            }
            String str2 = (String) vectorGlob_remote.elementAt(0);
            if (sftpProgressMonitor != null) {
                try {
                    sftpProgressMonitor.init(0, Parameters.DEFAULT_OPTION_PREFIXES, str2, -1L);
                } catch (Exception e) {
                    e = e;
                    str = str2;
                    if (e instanceof SftpException) {
                        SftpException sftpException = (SftpException) e;
                        if (sftpException.f207id == 4 && isRemoteDir(str)) {
                            throw new SftpException(4, str + " is a directory");
                        }
                        throw sftpException;
                    }
                    if (e instanceof Throwable) {
                        throw new SftpException(4, e.toString(), e);
                    }
                    throw new SftpException(4, e.toString());
                }
            }
            _put(inputStream, str2, sftpProgressMonitor, i);
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void _put(InputStream inputStream, String str, SftpProgressMonitor sftpProgressMonitor, int i) throws SftpException {
        long size;
        int i2;
        byte[] bArr;
        Header header;
        int i3;
        Header header2;
        int i4;
        InputStream inputStream2 = inputStream;
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            byte[] bArrStr2byte = Util.str2byte(str, this.fEncoding);
            if (i == 1 || i == 2) {
                try {
                    size = _stat(bArrStr2byte).getSize();
                } catch (Exception unused) {
                    size = 0;
                }
            } else {
                size = 0;
            }
            if (i == 1 && size > 0 && inputStream2.skip(size) < size) {
                throw new SftpException(4, "failed to resume for " + str);
            }
            if (i == 0) {
                sendOPENW(bArrStr2byte);
            } else {
                sendOPENA(bArrStr2byte);
            }
            Header header3 = header(this.buf, new Header());
            int i5 = header3.length;
            int i6 = header3.type;
            fill(this.buf, i5);
            if (i6 != 101 && i6 != 102) {
                throw new SftpException(4, "invalid type=" + i6);
            }
            if (i6 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            byte[] string = this.buf.getString();
            long j = (i == 1 || i == 2) ? size : 0L;
            int i7 = this.seq;
            byte[] bArr2 = this.obuf.buffer;
            int length = string.length + 39;
            int length2 = (this.obuf.buffer.length - length) - 128;
            int size2 = this.rq.size();
            int i8 = 0;
            while (true) {
                int i9 = length2;
                int i10 = length;
                int i11 = 0;
                while (true) {
                    int i12 = inputStream2.read(bArr2, i10, i9);
                    if (i12 > 0) {
                        i10 += i12;
                        i9 -= i12;
                        i11 += i12;
                    }
                    i2 = i11;
                    if (i9 <= 0 || i12 <= 0) {
                        break;
                        break;
                    }
                    i11 = i2;
                }
                if (i2 > 0) {
                    byte[] bArr3 = bArr2;
                    int length3 = length2;
                    int iSendWRITE = i2;
                    while (iSendWRITE > 0) {
                        int i13 = this.seq;
                        if (i13 - 1 == i7 || (i13 - i7) - i8 >= size2) {
                            while ((this.seq - i7) - i8 >= size2 && checkStatus(this.ackid, header3)) {
                                int i14 = this.ackid[0];
                                if (i7 > i14 || i14 > this.seq - 1) {
                                    if (i14 == this.seq) {
                                        System.err.println("ack error: startid=" + i7 + " seq=" + this.seq + " _ackid=" + i14);
                                    } else {
                                        throw new SftpException(4, "ack error: startid=" + i7 + " seq=" + this.seq + " _ackid=" + i14);
                                    }
                                }
                                i8++;
                                size2 = size2;
                                header3 = header3;
                            }
                            i3 = size2;
                            header2 = header3;
                            i4 = i8;
                        } else {
                            i4 = i8;
                            i3 = size2;
                            header2 = header3;
                        }
                        byte[] bArr4 = string;
                        int i15 = i3;
                        int i16 = i4;
                        Header header4 = header2;
                        iSendWRITE -= sendWRITE(string, j, bArr3, 0, iSendWRITE);
                        if (bArr3 != this.obuf.buffer) {
                            bArr3 = this.obuf.buffer;
                            length3 = (this.obuf.buffer.length - length) - 128;
                        }
                        i8 = i16;
                        header3 = header4;
                        size2 = i15;
                        string = bArr4;
                    }
                    bArr = string;
                    int i17 = size2;
                    header = header3;
                    long j2 = i2;
                    j += j2;
                    if (sftpProgressMonitor != null && !sftpProgressMonitor.count(j2)) {
                        break;
                    }
                    header3 = header;
                    string = bArr;
                    bArr2 = bArr3;
                    length2 = length3;
                    size2 = i17;
                    inputStream2 = inputStream;
                } else {
                    bArr = string;
                    header = header3;
                    break;
                }
            }
            int i18 = this.seq - i7;
            while (i18 > i8 && checkStatus(null, header)) {
                i8++;
            }
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.end();
            }
            _sendCLOSE(bArr, header);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, e.toString(), e);
            }
            throw new SftpException(4, e.toString());
        }
    }

    public OutputStream put(String str) throws SftpException {
        return put(str, (SftpProgressMonitor) null, 0);
    }

    public OutputStream put(String str, int i) throws SftpException {
        return put(str, (SftpProgressMonitor) null, i);
    }

    public OutputStream put(String str, SftpProgressMonitor sftpProgressMonitor, int i) throws SftpException {
        return put(str, sftpProgressMonitor, i, 0L);
    }

    public OutputStream put(String str, final SftpProgressMonitor sftpProgressMonitor, int i, long j) throws SftpException {
        long size;
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strIsUnique = isUnique(remoteAbsolutePath(str));
            if (isRemoteDir(strIsUnique)) {
                throw new SftpException(4, strIsUnique + " is a directory");
            }
            byte[] bArrStr2byte = Util.str2byte(strIsUnique, this.fEncoding);
            if (i == 1 || i == 2) {
                try {
                    size = _stat(bArrStr2byte).getSize();
                } catch (Exception unused) {
                    size = 0;
                }
            } else {
                size = 0;
            }
            long j2 = size;
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.init(0, Parameters.DEFAULT_OPTION_PREFIXES, strIsUnique, -1L);
            }
            if (i == 0) {
                sendOPENW(bArrStr2byte);
            } else {
                sendOPENA(bArrStr2byte);
            }
            Header header = header(this.buf, new Header());
            int i2 = header.length;
            int i3 = header.type;
            fill(this.buf, i2);
            if (i3 != 101 && i3 != 102) {
                throw new SftpException(4, "");
            }
            if (i3 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            final byte[] string = this.buf.getString();
            final long[] jArr = {(i == 1 || i == 2) ? j + j2 : j};
            return new OutputStream() { // from class: com.jcraft.jsch.ChannelSftp.1
                private Header header;
                private boolean init = true;
                private boolean isClosed = false;
                private int[] ackid = new int[1];
                private int startid = 0;
                private int _ackid = 0;
                private int ackcount = 0;
                private int writecount = 0;
                byte[] _data = new byte[1];

                {
                    this.header = ChannelSftp.this.new Header();
                }

                @Override // java.io.OutputStream
                public void write(byte[] bArr) throws IOException {
                    write(bArr, 0, bArr.length);
                }

                @Override // java.io.OutputStream
                public void write(byte[] bArr, int i4, int i5) throws IOException {
                    if (this.init) {
                        this.startid = ChannelSftp.this.seq;
                        this._ackid = ChannelSftp.this.seq;
                        this.init = false;
                    }
                    if (this.isClosed) {
                        throw new IOException("stream already closed");
                    }
                    int i6 = i5;
                    while (i6 > 0) {
                        try {
                            int iSendWRITE = ChannelSftp.this.sendWRITE(string, jArr[0], bArr, i4, i6);
                            this.writecount++;
                            long[] jArr2 = jArr;
                            jArr2[0] = jArr2[0] + ((long) iSendWRITE);
                            i4 += iSendWRITE;
                            i6 -= iSendWRITE;
                            if (ChannelSftp.this.seq - 1 == this.startid || ChannelSftp.this.io_in.available() >= 1024) {
                                while (ChannelSftp.this.io_in.available() > 0 && ChannelSftp.this.checkStatus(this.ackid, this.header)) {
                                    int i7 = this.ackid[0];
                                    this._ackid = i7;
                                    if (this.startid > i7 || i7 > ChannelSftp.this.seq - 1) {
                                        throw new SftpException(4, "");
                                    }
                                    this.ackcount++;
                                }
                            }
                        } catch (IOException e) {
                            throw e;
                        } catch (Exception e2) {
                            throw new IOException(e2.toString());
                        }
                    }
                    SftpProgressMonitor sftpProgressMonitor2 = sftpProgressMonitor;
                    if (sftpProgressMonitor2 != null && !sftpProgressMonitor2.count(i5)) {
                        close();
                        throw new IOException("canceled");
                    }
                }

                @Override // java.io.OutputStream
                public void write(int i4) throws IOException {
                    byte[] bArr = this._data;
                    bArr[0] = (byte) i4;
                    write(bArr, 0, 1);
                }

                @Override // java.io.OutputStream, java.io.Flushable
                public void flush() throws IOException {
                    if (this.isClosed) {
                        throw new IOException("stream already closed");
                    }
                    if (this.init) {
                        return;
                    }
                    while (this.writecount > this.ackcount && ChannelSftp.this.checkStatus(null, this.header)) {
                        try {
                            this.ackcount++;
                        } catch (SftpException e) {
                            throw new IOException(e.toString());
                        }
                    }
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    if (this.isClosed) {
                        return;
                    }
                    flush();
                    SftpProgressMonitor sftpProgressMonitor2 = sftpProgressMonitor;
                    if (sftpProgressMonitor2 != null) {
                        sftpProgressMonitor2.end();
                    }
                    try {
                        ChannelSftp.this._sendCLOSE(string, this.header);
                        this.isClosed = true;
                    } catch (IOException e) {
                        throw e;
                    } catch (Exception e2) {
                        throw new IOException(e2.toString());
                    }
                }
            };
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void get(String str, String str2) throws Throwable {
        get(str, str2, null, 0);
    }

    public void get(String str, String str2, SftpProgressMonitor sftpProgressMonitor) throws Throwable {
        get(str, str2, sftpProgressMonitor, 0);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0212  */
    /* JADX WARN: Code duplicated, block: B:65:0x019d A[Catch: Exception -> 0x01a1, TryCatch #6 {Exception -> 0x01a1, blocks: (B:40:0x0100, B:42:0x0108, B:46:0x0117, B:47:0x0130, B:49:0x0133, B:51:0x014a, B:53:0x0159, B:59:0x0188, B:65:0x019d, B:66:0x01a0), top: B:113:0x0100 }] */
    /* JADX WARN: Code duplicated, block: B:95:0x01fe  */
    /* JADX WARN: Code duplicated, block: B:97:0x0204  */
    /* JADX WARN: Code duplicated, block: B:99:0x020b  */
    public void get(String str, String str2, SftpProgressMonitor sftpProgressMonitor, int i) throws Throwable {
        boolean z;
        String str3;
        File file;
        String str4;
        StringBuffer stringBuffer;
        String str5;
        String str6;
        int i2;
        StringBuffer stringBuffer2;
        String str7;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        boolean z2;
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strRemoteAbsolutePath = remoteAbsolutePath(str);
            String strLocalAbsolutePath = localAbsolutePath(str2);
            Vector vectorGlob_remote = glob_remote(strRemoteAbsolutePath);
            int size = vectorGlob_remote.size();
            try {
                if (size == 0) {
                    throw new SftpException(2, "No such file");
                }
                boolean zIsDirectory = new File(strLocalAbsolutePath).isDirectory();
                if (zIsDirectory) {
                    String str8 = file_separator;
                    if (!strLocalAbsolutePath.endsWith(str8)) {
                        strLocalAbsolutePath = strLocalAbsolutePath + str8;
                    }
                    stringBuffer = new StringBuffer(strLocalAbsolutePath);
                    str4 = strLocalAbsolutePath;
                } else {
                    if (size > 1) {
                        throw new SftpException(4, "Copying multiple files, but destination is missing or a file.");
                    }
                    str4 = strLocalAbsolutePath;
                    stringBuffer = null;
                }
                String str9 = null;
                int i3 = 0;
                boolean zExists = false;
                while (i3 < size) {
                    try {
                        String str10 = (String) vectorGlob_remote.elementAt(i3);
                        SftpATTRS sftpATTRS_stat = _stat(str10);
                        if (sftpATTRS_stat.isDir()) {
                            throw new SftpException(4, "not supported to get directory " + str10);
                        }
                        if (zIsDirectory) {
                            try {
                                int iLastIndexOf = str10.lastIndexOf(47);
                                if (iLastIndexOf == -1) {
                                    stringBuffer.append(str10);
                                } else {
                                    stringBuffer.append(str10.substring(iLastIndexOf + 1));
                                }
                                String string = stringBuffer.toString();
                                if (string.indexOf("..") != -1) {
                                    String canonicalPath = new File(str4).getCanonicalPath();
                                    String canonicalPath2 = new File(string).getCanonicalPath();
                                    if (canonicalPath2.length() <= canonicalPath.length() || !canonicalPath2.substring(0, canonicalPath.length() + 1).equals(canonicalPath + file_separator)) {
                                        throw new SftpException(4, "writing to an unexpected file " + str10);
                                    }
                                }
                                stringBuffer.delete(str4.length(), string.length());
                                str5 = string;
                            } catch (Exception e) {
                                e = e;
                                z = zExists;
                            }
                        } else {
                            str5 = str4;
                        }
                        try {
                            File file2 = new File(str5);
                            if (i == 1) {
                                long size2 = sftpATTRS_stat.getSize();
                                long length = file2.length();
                                if (length > size2) {
                                    throw new SftpException(4, "failed to resume for " + str5);
                                }
                                if (length == size2) {
                                    return;
                                }
                            }
                            if (sftpProgressMonitor != null) {
                                str6 = str10;
                                i2 = i3;
                                stringBuffer2 = stringBuffer;
                                str7 = str4;
                                sftpProgressMonitor.init(1, str10, str5, sftpATTRS_stat.getSize());
                                if (i == 1) {
                                    sftpProgressMonitor.count(file2.length());
                                }
                            } else {
                                str6 = str10;
                                i2 = i3;
                                stringBuffer2 = stringBuffer;
                                str7 = str4;
                            }
                            zExists = file2.exists();
                            if (i == 0) {
                                try {
                                    fileOutputStream2 = new FileOutputStream(str5);
                                    z2 = true;
                                } catch (Throwable th) {
                                    th = th;
                                    fileOutputStream = null;
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    throw th;
                                }
                            } else {
                                z2 = true;
                                fileOutputStream2 = new FileOutputStream(str5, true);
                            }
                            try {
                                _get(str6, fileOutputStream2, sftpProgressMonitor, i, new File(str5).length());
                                fileOutputStream2.close();
                                i3 = i2 + 1;
                                str9 = str5;
                                str4 = str7;
                                stringBuffer = stringBuffer2;
                            } catch (Throwable th2) {
                                th = th2;
                                fileOutputStream = fileOutputStream2;
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            str3 = str5;
                            z = zExists;
                            if (!z) {
                                file = new File(str3);
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                            if (!(e instanceof SftpException)) {
                                throw ((SftpException) e);
                            }
                            if (e instanceof Throwable) {
                                throw new SftpException(4, "", e);
                            }
                            throw new SftpException(4, "");
                        }
                    } catch (Exception e3) {
                        e = e3;
                        str3 = str9;
                    }
                }
                return;
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Exception e5) {
            e = e5;
            z = false;
        }
        str3 = null;
        if (!z && str3 != null) {
            file = new File(str3);
            if (file.exists() && file.length() == 0) {
                file.delete();
            }
        }
        if (!(e instanceof SftpException)) {
            throw ((SftpException) e);
        }
        if (e instanceof Throwable) {
            throw new SftpException(4, "", e);
        }
        throw new SftpException(4, "");
    }

    public void get(String str, OutputStream outputStream) throws SftpException {
        get(str, outputStream, null, 0, 0L);
    }

    public void get(String str, OutputStream outputStream, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        get(str, outputStream, sftpProgressMonitor, 0, 0L);
    }

    public void get(String str, OutputStream outputStream, SftpProgressMonitor sftpProgressMonitor, int i, long j) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strIsUnique = isUnique(remoteAbsolutePath(str));
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.init(1, strIsUnique, "??", _stat(strIsUnique).getSize());
                if (i == 1) {
                    sftpProgressMonitor.count(j);
                }
            }
            _get(strIsUnique, outputStream, sftpProgressMonitor, i, j);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00b8 A[Catch: Exception -> 0x0190, TryCatch #0 {Exception -> 0x0190, blocks: (B:3:0x000f, B:8:0x002f, B:9:0x0034, B:11:0x0037, B:12:0x0042, B:16:0x0052, B:20:0x0067, B:22:0x006f, B:23:0x0087, B:24:0x0094, B:26:0x009e, B:49:0x010c, B:51:0x0111, B:52:0x0114, B:29:0x00ac, B:33:0x00b8, B:35:0x00d2, B:37:0x00d9, B:39:0x00e0, B:42:0x00ed, B:44:0x00fc, B:46:0x0102, B:48:0x0108, B:57:0x012b, B:58:0x012f, B:60:0x0136, B:62:0x015d, B:64:0x0167, B:68:0x0170), top: B:82:0x000f, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x00d2 A[Catch: Exception -> 0x0190, TryCatch #0 {Exception -> 0x0190, blocks: (B:3:0x000f, B:8:0x002f, B:9:0x0034, B:11:0x0037, B:12:0x0042, B:16:0x0052, B:20:0x0067, B:22:0x006f, B:23:0x0087, B:24:0x0094, B:26:0x009e, B:49:0x010c, B:51:0x0111, B:52:0x0114, B:29:0x00ac, B:33:0x00b8, B:35:0x00d2, B:37:0x00d9, B:39:0x00e0, B:42:0x00ed, B:44:0x00fc, B:46:0x0102, B:48:0x0108, B:57:0x012b, B:58:0x012f, B:60:0x0136, B:62:0x015d, B:64:0x0167, B:68:0x0170), top: B:82:0x000f, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x00d9 A[Catch: Exception -> 0x0190, TryCatch #0 {Exception -> 0x0190, blocks: (B:3:0x000f, B:8:0x002f, B:9:0x0034, B:11:0x0037, B:12:0x0042, B:16:0x0052, B:20:0x0067, B:22:0x006f, B:23:0x0087, B:24:0x0094, B:26:0x009e, B:49:0x010c, B:51:0x0111, B:52:0x0114, B:29:0x00ac, B:33:0x00b8, B:35:0x00d2, B:37:0x00d9, B:39:0x00e0, B:42:0x00ed, B:44:0x00fc, B:46:0x0102, B:48:0x0108, B:57:0x012b, B:58:0x012f, B:60:0x0136, B:62:0x015d, B:64:0x0167, B:68:0x0170), top: B:82:0x000f, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00df  */
    /* JADX WARN: Code duplicated, block: B:42:0x00ed A[Catch: Exception -> 0x0190, TryCatch #0 {Exception -> 0x0190, blocks: (B:3:0x000f, B:8:0x002f, B:9:0x0034, B:11:0x0037, B:12:0x0042, B:16:0x0052, B:20:0x0067, B:22:0x006f, B:23:0x0087, B:24:0x0094, B:26:0x009e, B:49:0x010c, B:51:0x0111, B:52:0x0114, B:29:0x00ac, B:33:0x00b8, B:35:0x00d2, B:37:0x00d9, B:39:0x00e0, B:42:0x00ed, B:44:0x00fc, B:46:0x0102, B:48:0x0108, B:57:0x012b, B:58:0x012f, B:60:0x0136, B:62:0x015d, B:64:0x0167, B:68:0x0170), top: B:82:0x000f, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:57:0x012b A[Catch: Exception -> 0x0190, TryCatch #0 {Exception -> 0x0190, blocks: (B:3:0x000f, B:8:0x002f, B:9:0x0034, B:11:0x0037, B:12:0x0042, B:16:0x0052, B:20:0x0067, B:22:0x006f, B:23:0x0087, B:24:0x0094, B:26:0x009e, B:49:0x010c, B:51:0x0111, B:52:0x0114, B:29:0x00ac, B:33:0x00b8, B:35:0x00d2, B:37:0x00d9, B:39:0x00e0, B:42:0x00ed, B:44:0x00fc, B:46:0x0102, B:48:0x0108, B:57:0x012b, B:58:0x012f, B:60:0x0136, B:62:0x015d, B:64:0x0167, B:68:0x0170), top: B:82:0x000f, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:60:0x0136 A[Catch: Exception -> 0x0190, TryCatch #0 {Exception -> 0x0190, blocks: (B:3:0x000f, B:8:0x002f, B:9:0x0034, B:11:0x0037, B:12:0x0042, B:16:0x0052, B:20:0x0067, B:22:0x006f, B:23:0x0087, B:24:0x0094, B:26:0x009e, B:49:0x010c, B:51:0x0111, B:52:0x0114, B:29:0x00ac, B:33:0x00b8, B:35:0x00d2, B:37:0x00d9, B:39:0x00e0, B:42:0x00ed, B:44:0x00fc, B:46:0x0102, B:48:0x0108, B:57:0x012b, B:58:0x012f, B:60:0x0136, B:62:0x015d, B:64:0x0167, B:68:0x0170), top: B:82:0x000f, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:61:0x015c  */
    /* JADX WARN: Code duplicated, block: B:64:0x0167 A[Catch: Exception -> 0x0190, TryCatch #0 {Exception -> 0x0190, blocks: (B:3:0x000f, B:8:0x002f, B:9:0x0034, B:11:0x0037, B:12:0x0042, B:16:0x0052, B:20:0x0067, B:22:0x006f, B:23:0x0087, B:24:0x0094, B:26:0x009e, B:49:0x010c, B:51:0x0111, B:52:0x0114, B:29:0x00ac, B:33:0x00b8, B:35:0x00d2, B:37:0x00d9, B:39:0x00e0, B:42:0x00ed, B:44:0x00fc, B:46:0x0102, B:48:0x0108, B:57:0x012b, B:58:0x012f, B:60:0x0136, B:62:0x015d, B:64:0x0167, B:68:0x0170), top: B:82:0x000f, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:65:0x016a  */
    /* JADX WARN: Code duplicated, block: B:88:0x00b5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x00ec A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x0125 A[SYNTHETIC] */
    private void _get(String str, OutputStream outputStream, SftpProgressMonitor sftpProgressMonitor, int i, long j) throws SftpException {
        Header header;
        int i2;
        int i3;
        int i4;
        long j2;
        Header header2;
        int length;
        int i5;
        try {
            sendOPENR(Util.str2byte(str, this.fEncoding));
            Header header3 = header(this.buf, new Header());
            int i6 = header3.length;
            int i7 = header3.type;
            fill(this.buf, i6);
            int i8 = 101;
            if (i7 != 101 && i7 != 102) {
                throw new SftpException(4, "");
            }
            if (i7 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            byte[] string = this.buf.getString();
            int i9 = 1;
            long j3 = i == 1 ? j : 0L;
            this.rq.init();
            int length2 = this.buf.buffer.length - 13;
            if (this.server_version == 0) {
                length2 = 1024;
            }
            long j4 = j3;
            int i10 = length2;
            int i11 = 1;
            loop0: while (true) {
                if (this.rq.count() < i11) {
                    int i12 = i10;
                    sendREAD(string, j4, i10, this.rq);
                    j4 += (long) i12;
                    i10 = i12;
                    i11 = i11;
                } else {
                    int i13 = i11;
                    int i14 = i10;
                    header = header(this.buf, header3);
                    int i15 = header.length;
                    int i16 = header.type;
                    try {
                        RequestQueue.Request request = this.rq.get(header.rid);
                        if (i16 != i8) {
                            if (i16 != 103) {
                                this.buf.rewind();
                                fill(this.buf.buffer, 0, 4);
                                i2 = this.buf.getInt();
                                i3 = (i15 - 4) - i2;
                                i4 = i2;
                                while (true) {
                                    if (i4 > 0) {
                                        if (i4 > this.buf.buffer.length) {
                                            length = this.buf.buffer.length;
                                        } else {
                                            length = i4;
                                        }
                                        i5 = this.io_in.read(this.buf.buffer, 0, length);
                                        if (i5 < 0) {
                                            outputStream.write(this.buf.buffer, 0, i5);
                                            int i17 = i14;
                                            long j5 = i5;
                                            i4 -= i5;
                                            if (sftpProgressMonitor == null) {
                                            }
                                            i14 = i17;
                                        }
                                    } else {
                                        int i18 = i14;
                                        if (i3 > 0) {
                                            skip(i3);
                                        }
                                        j2 = i2;
                                        if (j2 < request.length) {
                                            this.rq.cancel(header, this.buf);
                                            sendREAD(string, request.offset + j2, (int) (request.length - j2), this.rq);
                                            header2 = header;
                                            j4 = request.offset + request.length;
                                        } else {
                                            header2 = header;
                                        }
                                        if (i13 < this.rq.size()) {
                                            i11 = i13 + 1;
                                        } else {
                                            i11 = i13;
                                        }
                                        i10 = i18;
                                        header3 = header2;
                                        i8 = 101;
                                        i9 = 1;
                                    }
                                }
                            }
                            break;
                            break;
                        }
                        fill(this.buf, i15);
                        int i19 = this.buf.getInt();
                        if (i19 != i9) {
                            throwStatusError(this.buf, i19);
                            if (i16 != 103) {
                                this.buf.rewind();
                                fill(this.buf.buffer, 0, 4);
                                i2 = this.buf.getInt();
                                i3 = (i15 - 4) - i2;
                                i4 = i2;
                                while (true) {
                                    if (i4 > 0) {
                                        if (i4 > this.buf.buffer.length) {
                                            length = this.buf.buffer.length;
                                        } else {
                                            length = i4;
                                        }
                                        i5 = this.io_in.read(this.buf.buffer, 0, length);
                                        if (i5 < 0) {
                                            outputStream.write(this.buf.buffer, 0, i5);
                                            int i110 = i14;
                                            long j6 = i5;
                                            i4 -= i5;
                                            if (sftpProgressMonitor == null && !sftpProgressMonitor.count(j6)) {
                                                skip(i4);
                                                if (i3 <= 0) {
                                                    break loop0;
                                                }
                                                skip(i3);
                                                break loop0;
                                            }
                                            i14 = i110;
                                        }
                                    } else {
                                        int i111 = i14;
                                        if (i3 > 0) {
                                            skip(i3);
                                        }
                                        j2 = i2;
                                        if (j2 < request.length) {
                                            this.rq.cancel(header, this.buf);
                                            sendREAD(string, request.offset + j2, (int) (request.length - j2), this.rq);
                                            header2 = header;
                                            j4 = request.offset + request.length;
                                        } else {
                                            header2 = header;
                                        }
                                        if (i13 < this.rq.size()) {
                                            i11 = i13 + 1;
                                        } else {
                                            i11 = i13;
                                        }
                                        i10 = i111;
                                        header3 = header2;
                                        i8 = 101;
                                        i9 = 1;
                                    }
                                }
                            }
                        }
                        break;
                    } catch (RequestQueue.OutOfOrderException e) {
                        j4 = e.offset;
                        skip(header.length);
                        this.rq.cancel(header, this.buf);
                        i10 = i14;
                        header3 = header;
                        i11 = i13;
                    }
                }
            }
            outputStream.flush();
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.end();
            }
            this.rq.cancel(header, this.buf);
            _sendCLOSE(string, header);
        } catch (Exception e2) {
            if (e2 instanceof SftpException) {
                throw ((SftpException) e2);
            }
            if (e2 instanceof Throwable) {
                throw new SftpException(4, "", e2);
            }
            throw new SftpException(4, "");
        }
    }

    private class RequestQueue {
        int count;
        int head;
        Request[] rrq;

        class OutOfOrderException extends Exception {
            long offset;

            OutOfOrderException(long j) {
                this.offset = j;
            }
        }

        class Request {

            /* JADX INFO: renamed from: id, reason: collision with root package name */
            int f202id;
            long length;
            long offset;

            Request() {
            }
        }

        RequestQueue(int i) {
            this.rrq = null;
            this.rrq = new Request[i];
            int i2 = 0;
            while (true) {
                Request[] requestArr = this.rrq;
                if (i2 < requestArr.length) {
                    requestArr[i2] = new Request();
                    i2++;
                } else {
                    init();
                    return;
                }
            }
        }

        void init() {
            this.count = 0;
            this.head = 0;
        }

        void add(int i, long j, int i2) {
            int i3 = this.count;
            if (i3 == 0) {
                this.head = 0;
            }
            int length = this.head + i3;
            Request[] requestArr = this.rrq;
            if (length >= requestArr.length) {
                length -= requestArr.length;
            }
            requestArr[length].f202id = i;
            this.rrq[length].offset = j;
            this.rrq[length].length = i2;
            this.count++;
        }

        Request get(int i) throws OutOfOrderException, SftpException {
            this.count--;
            int i2 = this.head;
            int i3 = i2 + 1;
            this.head = i3;
            Request[] requestArr = this.rrq;
            if (i3 == requestArr.length) {
                this.head = 0;
            }
            if (requestArr[i2].f202id != i) {
                long offset = getOffset();
                int i4 = 0;
                while (true) {
                    Request[] requestArr2 = this.rrq;
                    if (i4 < requestArr2.length) {
                        if (requestArr2[i4].f202id == i) {
                            this.rrq[i4].f202id = 0;
                            throw new OutOfOrderException(offset);
                        }
                        i4++;
                    } else {
                        throw new SftpException(4, "RequestQueue: unknown request id " + i);
                    }
                }
            } else {
                this.rrq[i2].f202id = 0;
                return this.rrq[i2];
            }
        }

        int count() {
            return this.count;
        }

        int size() {
            return this.rrq.length;
        }

        void cancel(Header header, Buffer buffer) throws IOException {
            int i = this.count;
            for (int i2 = 0; i2 < i; i2++) {
                header = ChannelSftp.this.header(buffer, header);
                int i3 = header.length;
                int i4 = 0;
                while (true) {
                    Request[] requestArr = this.rrq;
                    if (i4 >= requestArr.length) {
                        break;
                    }
                    if (requestArr[i4].f202id == header.rid) {
                        this.rrq[i4].f202id = 0;
                        break;
                    }
                    i4++;
                }
                ChannelSftp.this.skip(i3);
            }
            init();
        }

        long getOffset() {
            long j = Long.MAX_VALUE;
            int i = 0;
            while (true) {
                Request[] requestArr = this.rrq;
                if (i >= requestArr.length) {
                    return j;
                }
                if (requestArr[i].f202id != 0 && j > this.rrq[i].offset) {
                    j = this.rrq[i].offset;
                }
                i++;
            }
        }
    }

    public InputStream get(String str) throws SftpException {
        return get(str, (SftpProgressMonitor) null, 0L);
    }

    public InputStream get(String str, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        return get(str, sftpProgressMonitor, 0L);
    }

    public InputStream get(String str, int i) throws SftpException {
        return get(str, (SftpProgressMonitor) null, 0L);
    }

    public InputStream get(String str, SftpProgressMonitor sftpProgressMonitor, int i) throws SftpException {
        return get(str, sftpProgressMonitor, 0L);
    }

    public InputStream get(String str, SftpProgressMonitor sftpProgressMonitor, long j) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strIsUnique = isUnique(remoteAbsolutePath(str));
            byte[] bArrStr2byte = Util.str2byte(strIsUnique, this.fEncoding);
            SftpATTRS sftpATTRS_stat = _stat(bArrStr2byte);
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.init(1, strIsUnique, "??", sftpATTRS_stat.getSize());
            }
            sendOPENR(bArrStr2byte);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 101 && i2 != 102) {
                throw new SftpException(4, "");
            }
            if (i2 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            byte[] string = this.buf.getString();
            this.rq.init();
            return new InputStream(j, sftpProgressMonitor, string) { // from class: com.jcraft.jsch.ChannelSftp.2
                Header header;
                long offset;
                long request_offset;
                final /* synthetic */ byte[] val$handle;
                final /* synthetic */ SftpProgressMonitor val$monitor;
                final /* synthetic */ long val$skip;
                boolean closed = false;
                int rest_length = 0;
                byte[] _data = new byte[1];
                byte[] rest_byte = new byte[1024];
                int request_max = 1;

                {
                    this.val$skip = j;
                    this.val$monitor = sftpProgressMonitor;
                    this.val$handle = string;
                    this.offset = j;
                    this.header = ChannelSftp.this.new Header();
                    this.request_offset = this.offset;
                }

                @Override // java.io.InputStream
                public int read() throws IOException {
                    if (this.closed || read(this._data, 0, 1) == -1) {
                        return -1;
                    }
                    return this._data[0] & 255;
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr) throws IOException {
                    if (this.closed) {
                        return -1;
                    }
                    return read(bArr, 0, bArr.length);
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr, int i3, int i4) throws IOException {
                    int length = i4;
                    if (this.closed) {
                        return -1;
                    }
                    bArr.getClass();
                    if (i3 < 0 || length < 0 || i3 + length > bArr.length) {
                        throw new IndexOutOfBoundsException();
                    }
                    int i5 = 0;
                    if (length == 0) {
                        return 0;
                    }
                    int i6 = this.rest_length;
                    if (i6 <= 0) {
                        if (ChannelSftp.this.buf.buffer.length - 13 < length) {
                            length = ChannelSftp.this.buf.buffer.length - 13;
                        }
                        if (ChannelSftp.this.server_version == 0 && length > 1024) {
                            length = 1024;
                        }
                        ChannelSftp.this.rq.count();
                        int length2 = ChannelSftp.this.server_version != 0 ? ChannelSftp.this.buf.buffer.length - 13 : 1024;
                        while (ChannelSftp.this.rq.count() < this.request_max) {
                            try {
                                ChannelSftp channelSftp = ChannelSftp.this;
                                channelSftp.sendREAD(this.val$handle, this.request_offset, length2, channelSftp.rq);
                                this.request_offset += (long) length2;
                            } catch (Exception unused) {
                                throw new IOException("error");
                            }
                        }
                        ChannelSftp channelSftp2 = ChannelSftp.this;
                        Header header2 = channelSftp2.header(channelSftp2.buf, this.header);
                        this.header = header2;
                        this.rest_length = header2.length;
                        int i7 = this.header.type;
                        int i8 = this.header.rid;
                        try {
                            RequestQueue.Request request = ChannelSftp.this.rq.get(this.header.rid);
                            if (i7 != 101 && i7 != 103) {
                                throw new IOException("error");
                            }
                            if (i7 != 101) {
                                ChannelSftp.this.buf.rewind();
                                ChannelSftp channelSftp3 = ChannelSftp.this;
                                channelSftp3.fill(channelSftp3.buf.buffer, 0, 4);
                                int i9 = ChannelSftp.this.buf.getInt();
                                int i10 = this.rest_length - 4;
                                this.rest_length = i10;
                                int i11 = i10 - i9;
                                long j2 = i9;
                                this.offset += j2;
                                if (i9 <= 0) {
                                    return 0;
                                }
                                if (i9 <= length) {
                                    length = i9;
                                }
                                int i12 = ChannelSftp.this.io_in.read(bArr, i3, length);
                                if (i12 < 0) {
                                    return -1;
                                }
                                int i13 = i9 - i12;
                                this.rest_length = i13;
                                if (i13 > 0) {
                                    if (this.rest_byte.length < i13) {
                                        this.rest_byte = new byte[i13];
                                    }
                                    while (i13 > 0) {
                                        int i14 = ChannelSftp.this.io_in.read(this.rest_byte, i5, i13);
                                        if (i14 <= 0) {
                                            break;
                                        }
                                        i5 += i14;
                                        i13 -= i14;
                                    }
                                }
                                if (i11 > 0) {
                                    ChannelSftp.this.io_in.skip(i11);
                                }
                                if (j2 < request.length) {
                                    ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                                    try {
                                        ChannelSftp.this.sendREAD(this.val$handle, request.offset + j2, (int) (request.length - j2), ChannelSftp.this.rq);
                                        this.request_offset = request.offset + request.length;
                                    } catch (Exception unused2) {
                                        throw new IOException("error");
                                    }
                                }
                                if (this.request_max < ChannelSftp.this.rq.size()) {
                                    this.request_max++;
                                }
                                SftpProgressMonitor sftpProgressMonitor2 = this.val$monitor;
                                if (sftpProgressMonitor2 == null || sftpProgressMonitor2.count(i12)) {
                                    return i12;
                                }
                                close();
                                return -1;
                            }
                            ChannelSftp channelSftp4 = ChannelSftp.this;
                            channelSftp4.fill(channelSftp4.buf, this.rest_length);
                            int i15 = ChannelSftp.this.buf.getInt();
                            this.rest_length = 0;
                            if (i15 == 1) {
                                close();
                                return -1;
                            }
                            throw new IOException("error");
                        } catch (RequestQueue.OutOfOrderException e) {
                            this.request_offset = e.offset;
                            skip(this.header.length);
                            ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                            return 0;
                        } catch (SftpException e2) {
                            throw new IOException("error: " + e2.toString());
                        }
                    }
                    if (i6 <= length) {
                        length = i6;
                    }
                    System.arraycopy(this.rest_byte, 0, bArr, i3, length);
                    int i16 = this.rest_length;
                    if (length != i16) {
                        byte[] bArr2 = this.rest_byte;
                        System.arraycopy(bArr2, length, bArr2, 0, i16 - length);
                    }
                    SftpProgressMonitor sftpProgressMonitor3 = this.val$monitor;
                    if (sftpProgressMonitor3 != null && !sftpProgressMonitor3.count(length)) {
                        close();
                        return -1;
                    }
                    this.rest_length -= length;
                    return length;
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    if (this.closed) {
                        return;
                    }
                    this.closed = true;
                    SftpProgressMonitor sftpProgressMonitor2 = this.val$monitor;
                    if (sftpProgressMonitor2 != null) {
                        sftpProgressMonitor2.end();
                    }
                    ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                    try {
                        ChannelSftp.this._sendCLOSE(this.val$handle, this.header);
                    } catch (Exception unused) {
                        throw new IOException("error");
                    }
                }
            };
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public Vector ls(String str) throws SftpException {
        final Vector vector = new Vector();
        ls(str, new LsEntrySelector() { // from class: com.jcraft.jsch.ChannelSftp.3
            @Override // com.jcraft.jsch.ChannelSftp.LsEntrySelector
            public int select(LsEntry lsEntry) {
                vector.addElement(lsEntry);
                return 0;
            }
        });
        return vector;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v9 */
    public void ls(String str, LsEntrySelector lsEntrySelector) throws SftpException {
        byte[] bArrStr2byte;
        byte[] bArrStr2byte2;
        String strByte2str;
        ?? Array_equals;
        String strByte2str2;
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strRemoteAbsolutePath = remoteAbsolutePath(str);
            new Vector();
            int iLastIndexOf = strRemoteAbsolutePath.lastIndexOf(47);
            int i = 1;
            int i2 = 0;
            String strSubstring = strRemoteAbsolutePath.substring(0, iLastIndexOf == 0 ? 1 : iLastIndexOf);
            String strSubstring2 = strRemoteAbsolutePath.substring(iLastIndexOf + 1);
            String strUnquote = Util.unquote(strSubstring);
            byte[][] bArr = new byte[1][];
            boolean zIsPattern = isPattern(strSubstring2, bArr);
            if (zIsPattern) {
                bArrStr2byte = bArr[0];
            } else {
                String strUnquote2 = Util.unquote(strRemoteAbsolutePath);
                if (_stat(strUnquote2).isDir()) {
                    strUnquote = strUnquote2;
                    bArrStr2byte = null;
                } else if (this.fEncoding_is_utf8) {
                    bArrStr2byte = Util.unquote(bArr[0]);
                } else {
                    bArrStr2byte = Util.str2byte(Util.unquote(strSubstring2), this.fEncoding);
                }
            }
            sendOPENDIR(Util.str2byte(strUnquote, this.fEncoding));
            Header header = header(this.buf, new Header());
            int i3 = header.length;
            int i4 = header.type;
            fill(this.buf, i3);
            int i5 = 101;
            if (i4 != 101 && i4 != 102) {
                throw new SftpException(4, "");
            }
            if (i4 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            byte[] string = this.buf.getString();
            int iSelect = 0;
            while (iSelect == 0) {
                sendREADDIR(string);
                header = header(this.buf, header);
                int i6 = header.length;
                int i7 = header.type;
                if (i7 != i5 && i7 != 104) {
                    throw new SftpException(4, "");
                }
                if (i7 == i5) {
                    fill(this.buf, i6);
                    int i8 = this.buf.getInt();
                    if (i8 == i) {
                        break;
                    } else {
                        throwStatusError(this.buf, i8);
                    }
                }
                this.buf.rewind();
                fill(this.buf.buffer, i2, 4);
                int i9 = i6 - 4;
                int i10 = this.buf.getInt();
                this.buf.reset();
                while (i10 > 0) {
                    if (i9 > 0) {
                        this.buf.shift();
                        int iFill = fill(this.buf.buffer, this.buf.index, this.buf.buffer.length > this.buf.index + i9 ? i9 : this.buf.buffer.length - this.buf.index);
                        this.buf.index += iFill;
                        i9 -= iFill;
                    }
                    byte[] string2 = this.buf.getString();
                    byte[] string3 = this.server_version <= 3 ? this.buf.getString() : null;
                    SftpATTRS attr = SftpATTRS.getATTR(this.buf);
                    if (iSelect == i) {
                        i10--;
                        i5 = 101;
                    } else {
                        if (bArrStr2byte == null) {
                            Array_equals = i;
                            strByte2str = null;
                        } else if (!zIsPattern) {
                            strByte2str = null;
                            Array_equals = Util.array_equals(bArrStr2byte, string2);
                        } else {
                            if (this.fEncoding_is_utf8) {
                                bArrStr2byte2 = string2;
                                strByte2str = null;
                            } else {
                                strByte2str = Util.byte2str(string2, this.fEncoding);
                                bArrStr2byte2 = Util.str2byte(strByte2str, "UTF-8");
                            }
                            Array_equals = Util.glob(bArrStr2byte, bArrStr2byte2);
                        }
                        if (Array_equals != 0) {
                            if (strByte2str == null) {
                                strByte2str = Util.byte2str(string2, this.fEncoding);
                            }
                            if (string3 == null) {
                                strByte2str2 = attr.toString() + " " + strByte2str;
                            } else {
                                strByte2str2 = Util.byte2str(string3, this.fEncoding);
                            }
                            iSelect = lsEntrySelector.select(new LsEntry(strByte2str, strByte2str2, attr));
                        }
                        i10--;
                        i = 1;
                        i5 = 101;
                        i2 = 0;
                    }
                }
                i = 1;
            }
            _sendCLOSE(string, header);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public String readlink(String str) throws SftpException {
        try {
            if (this.server_version < 3) {
                throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
            }
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            sendREADLINK(Util.str2byte(isUnique(remoteAbsolutePath(str)), this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 101 && i2 != 104) {
                throw new SftpException(4, "");
            }
            byte[] string = null;
            if (i2 == 104) {
                int i3 = this.buf.getInt();
                for (int i4 = 0; i4 < i3; i4++) {
                    string = this.buf.getString();
                    if (this.server_version <= 3) {
                        this.buf.getString();
                    }
                    SftpATTRS.getATTR(this.buf);
                }
                return Util.byte2str(string, this.fEncoding);
            }
            throwStatusError(this.buf, this.buf.getInt());
            return null;
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void symlink(String str, String str2) throws SftpException {
        if (this.server_version < 3) {
            throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
        }
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strRemoteAbsolutePath = remoteAbsolutePath(str);
            String strRemoteAbsolutePath2 = remoteAbsolutePath(str2);
            String strIsUnique = isUnique(strRemoteAbsolutePath);
            if (str.charAt(0) != '/') {
                String cwd = getCwd();
                strIsUnique = strIsUnique.substring(cwd.length() + (!cwd.endsWith("/")));
            }
            if (isPattern(strRemoteAbsolutePath2)) {
                throw new SftpException(4, strRemoteAbsolutePath2);
            }
            sendSYMLINK(Util.str2byte(strIsUnique, this.fEncoding), Util.str2byte(Util.unquote(strRemoteAbsolutePath2), this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 101) {
                throw new SftpException(4, "");
            }
            int i3 = this.buf.getInt();
            if (i3 == 0) {
                return;
            }
            throwStatusError(this.buf, i3);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void hardlink(String str, String str2) throws SftpException {
        if (!this.extension_hardlink) {
            throw new SftpException(8, "hardlink@openssh.com is not supported");
        }
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strRemoteAbsolutePath = remoteAbsolutePath(str);
            String strRemoteAbsolutePath2 = remoteAbsolutePath(str2);
            String strIsUnique = isUnique(strRemoteAbsolutePath);
            if (str.charAt(0) != '/') {
                String cwd = getCwd();
                strIsUnique = strIsUnique.substring(cwd.length() + (!cwd.endsWith("/")));
            }
            if (isPattern(strRemoteAbsolutePath2)) {
                throw new SftpException(4, strRemoteAbsolutePath2);
            }
            sendHARDLINK(Util.str2byte(strIsUnique, this.fEncoding), Util.str2byte(Util.unquote(strRemoteAbsolutePath2), this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 101) {
                throw new SftpException(4, "");
            }
            int i3 = this.buf.getInt();
            if (i3 == 0) {
                return;
            }
            throwStatusError(this.buf, i3);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void rename(String str, String str2) throws SftpException {
        String strUnquote;
        if (this.server_version < 2) {
            throw new SftpException(8, "The remote sshd is too old to support rename operation.");
        }
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String strRemoteAbsolutePath = remoteAbsolutePath(str);
            String strRemoteAbsolutePath2 = remoteAbsolutePath(str2);
            String strIsUnique = isUnique(strRemoteAbsolutePath);
            Vector vectorGlob_remote = glob_remote(strRemoteAbsolutePath2);
            int size = vectorGlob_remote.size();
            if (size >= 2) {
                throw new SftpException(4, vectorGlob_remote.toString());
            }
            if (size == 1) {
                strUnquote = (String) vectorGlob_remote.elementAt(0);
                String str3 = strUnquote;
            } else {
                if (isPattern(strRemoteAbsolutePath2)) {
                    throw new SftpException(4, strRemoteAbsolutePath2);
                }
                strUnquote = Util.unquote(strRemoteAbsolutePath2);
            }
            sendRENAME(Util.str2byte(strIsUnique, this.fEncoding), Util.str2byte(strUnquote, this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 101) {
                throw new SftpException(4, "");
            }
            int i3 = this.buf.getInt();
            if (i3 == 0) {
                return;
            }
            throwStatusError(this.buf, i3);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void rm(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector vectorGlob_remote = glob_remote(remoteAbsolutePath(str));
            int size = vectorGlob_remote.size();
            Header header = new Header();
            for (int i = 0; i < size; i++) {
                String str2 = (String) vectorGlob_remote.elementAt(i);
                sendREMOVE(Util.str2byte(str2, this.fEncoding));
                header = header(this.buf, header);
                int i2 = header.length;
                int i3 = header.type;
                fill(this.buf, i2);
                if (i3 != 101) {
                    throw new SftpException(4, "");
                }
                int i4 = this.buf.getInt();
                if (i4 != 0) {
                    throwStatusError(this.buf, i4);
                }
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    private boolean isRemoteDir(String str) {
        try {
            sendSTAT(Util.str2byte(str, this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 105) {
                return false;
            }
            return SftpATTRS.getATTR(this.buf).isDir();
        } catch (Exception unused) {
            return false;
        }
    }

    public void chgrp(int i, String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector vectorGlob_remote = glob_remote(remoteAbsolutePath(str));
            int size = vectorGlob_remote.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) vectorGlob_remote.elementAt(i2);
                SftpATTRS sftpATTRS_stat = _stat(str2);
                sftpATTRS_stat.setFLAGS(0);
                sftpATTRS_stat.setUIDGID(sftpATTRS_stat.uid, i);
                _setStat(str2, sftpATTRS_stat);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void chown(int i, String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector vectorGlob_remote = glob_remote(remoteAbsolutePath(str));
            int size = vectorGlob_remote.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) vectorGlob_remote.elementAt(i2);
                SftpATTRS sftpATTRS_stat = _stat(str2);
                sftpATTRS_stat.setFLAGS(0);
                sftpATTRS_stat.setUIDGID(i, sftpATTRS_stat.gid);
                _setStat(str2, sftpATTRS_stat);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void chmod(int i, String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector vectorGlob_remote = glob_remote(remoteAbsolutePath(str));
            int size = vectorGlob_remote.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) vectorGlob_remote.elementAt(i2);
                SftpATTRS sftpATTRS_stat = _stat(str2);
                sftpATTRS_stat.setFLAGS(0);
                sftpATTRS_stat.setPERMISSIONS(i);
                _setStat(str2, sftpATTRS_stat);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void setMtime(String str, int i) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector vectorGlob_remote = glob_remote(remoteAbsolutePath(str));
            int size = vectorGlob_remote.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) vectorGlob_remote.elementAt(i2);
                SftpATTRS sftpATTRS_stat = _stat(str2);
                sftpATTRS_stat.setFLAGS(0);
                sftpATTRS_stat.setACMODTIME(sftpATTRS_stat.getATime(), i);
                _setStat(str2, sftpATTRS_stat);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void rmdir(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector vectorGlob_remote = glob_remote(remoteAbsolutePath(str));
            int size = vectorGlob_remote.size();
            Header header = new Header();
            for (int i = 0; i < size; i++) {
                String str2 = (String) vectorGlob_remote.elementAt(i);
                sendRMDIR(Util.str2byte(str2, this.fEncoding));
                header = header(this.buf, header);
                int i2 = header.length;
                int i3 = header.type;
                fill(this.buf, i2);
                if (i3 != 101) {
                    throw new SftpException(4, "");
                }
                int i4 = this.buf.getInt();
                if (i4 != 0) {
                    throwStatusError(this.buf, i4);
                }
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public void mkdir(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            sendMKDIR(Util.str2byte(remoteAbsolutePath(str), this.fEncoding), null);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 101) {
                throw new SftpException(4, "");
            }
            int i3 = this.buf.getInt();
            if (i3 == 0) {
                return;
            }
            throwStatusError(this.buf, i3);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public SftpATTRS stat(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            return _stat(isUnique(remoteAbsolutePath(str)));
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    private SftpATTRS _stat(byte[] bArr) throws SftpException {
        try {
            sendSTAT(bArr);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 105) {
                if (i2 == 101) {
                    throwStatusError(this.buf, this.buf.getInt());
                }
                throw new SftpException(4, "");
            }
            return SftpATTRS.getATTR(this.buf);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    private SftpATTRS _stat(String str) throws SftpException {
        return _stat(Util.str2byte(str, this.fEncoding));
    }

    public SftpStatVFS statVFS(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            return _statVFS(isUnique(remoteAbsolutePath(str)));
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    private SftpStatVFS _statVFS(byte[] bArr) throws SftpException {
        if (!this.extension_statvfs) {
            throw new SftpException(8, "statvfs@openssh.com is not supported");
        }
        try {
            sendSTATVFS(bArr);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 201) {
                if (i2 == 101) {
                    throwStatusError(this.buf, this.buf.getInt());
                }
                throw new SftpException(4, "");
            }
            return SftpStatVFS.getStatVFS(this.buf);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    private SftpStatVFS _statVFS(String str) throws SftpException {
        return _statVFS(Util.str2byte(str, this.fEncoding));
    }

    public SftpATTRS lstat(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            return _lstat(isUnique(remoteAbsolutePath(str)));
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    private SftpATTRS _lstat(String str) throws SftpException {
        try {
            sendLSTAT(Util.str2byte(str, this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 105) {
                if (i2 == 101) {
                    throwStatusError(this.buf, this.buf.getInt());
                }
                throw new SftpException(4, "");
            }
            return SftpATTRS.getATTR(this.buf);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    private byte[] _realpath(String str) throws Exception {
        sendREALPATH(Util.str2byte(str, this.fEncoding));
        Header header = header(this.buf, new Header());
        int i = header.length;
        int i2 = header.type;
        fill(this.buf, i);
        if (i2 != 101 && i2 != 104) {
            throw new SftpException(4, "");
        }
        if (i2 == 101) {
            throwStatusError(this.buf, this.buf.getInt());
        }
        int i3 = this.buf.getInt();
        byte[] string = null;
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                return string;
            }
            string = this.buf.getString();
            if (this.server_version <= 3) {
                this.buf.getString();
            }
            SftpATTRS.getATTR(this.buf);
            i3 = i4;
        }
    }

    public void setStat(String str, SftpATTRS sftpATTRS) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector vectorGlob_remote = glob_remote(remoteAbsolutePath(str));
            int size = vectorGlob_remote.size();
            for (int i = 0; i < size; i++) {
                String str2 = (String) vectorGlob_remote.elementAt(i);
                _setStat(str2, sftpATTRS);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    private void _setStat(String str, SftpATTRS sftpATTRS) throws SftpException {
        try {
            sendSETSTAT(Util.str2byte(str, this.fEncoding), sftpATTRS);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 101) {
                throw new SftpException(4, "");
            }
            int i3 = this.buf.getInt();
            if (i3 != 0) {
                throwStatusError(this.buf, i3);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public String pwd() throws SftpException {
        return getCwd();
    }

    public String lpwd() {
        return this.lcwd;
    }

    public String version() {
        return this.version;
    }

    public String getHome() throws SftpException {
        if (this.home == null) {
            try {
                ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
                this.home = Util.byte2str(_realpath(""), this.fEncoding);
            } catch (Exception e) {
                if (e instanceof SftpException) {
                    throw ((SftpException) e);
                }
                if (e instanceof Throwable) {
                    throw new SftpException(4, "", e);
                }
                throw new SftpException(4, "");
            }
        }
        return this.home;
    }

    private String getCwd() throws SftpException {
        if (this.cwd == null) {
            this.cwd = getHome();
        }
        return this.cwd;
    }

    private void setCwd(String str) {
        this.cwd = str;
    }

    private void read(byte[] bArr, int i, int i2) throws SftpException, IOException {
        while (i2 > 0) {
            int i3 = this.io_in.read(bArr, i, i2);
            if (i3 <= 0) {
                throw new SftpException(4, "");
            }
            i += i3;
            i2 -= i3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkStatus(int[] iArr, Header header) throws SftpException, IOException {
        Header header2 = header(this.buf, header);
        int i = header2.length;
        int i2 = header2.type;
        if (iArr != null) {
            iArr[0] = header2.rid;
        }
        fill(this.buf, i);
        if (i2 != 101) {
            throw new SftpException(4, "");
        }
        int i3 = this.buf.getInt();
        if (i3 == 0) {
            return true;
        }
        throwStatusError(this.buf, i3);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean _sendCLOSE(byte[] bArr, Header header) throws Exception {
        sendCLOSE(bArr);
        return checkStatus(null, header);
    }

    private void sendINIT() throws Exception {
        this.packet.reset();
        putHEAD((byte) 1, 5);
        this.buf.putInt(3);
        getSession().write(this.packet, this, 9);
    }

    private void sendREALPATH(byte[] bArr) throws Exception {
        sendPacketPath((byte) 16, bArr);
    }

    private void sendSTAT(byte[] bArr) throws Exception {
        sendPacketPath((byte) 17, bArr);
    }

    private void sendSTATVFS(byte[] bArr) throws Exception {
        sendPacketPath((byte) 0, bArr, "statvfs@openssh.com");
    }

    private void sendLSTAT(byte[] bArr) throws Exception {
        sendPacketPath((byte) 7, bArr);
    }

    private void sendFSTAT(byte[] bArr) throws Exception {
        sendPacketPath((byte) 8, bArr);
    }

    private void sendSETSTAT(byte[] bArr, SftpATTRS sftpATTRS) throws Exception {
        this.packet.reset();
        putHEAD((byte) 9, bArr.length + 9 + sftpATTRS.length());
        Buffer buffer = this.buf;
        int i = this.seq;
        this.seq = i + 1;
        buffer.putInt(i);
        this.buf.putString(bArr);
        sftpATTRS.dump(this.buf);
        getSession().write(this.packet, this, bArr.length + 9 + sftpATTRS.length() + 4);
    }

    private void sendREMOVE(byte[] bArr) throws Exception {
        sendPacketPath((byte) 13, bArr);
    }

    private void sendMKDIR(byte[] bArr, SftpATTRS sftpATTRS) throws Exception {
        this.packet.reset();
        putHEAD((byte) 14, bArr.length + 9 + (sftpATTRS != null ? sftpATTRS.length() : 4));
        Buffer buffer = this.buf;
        int i = this.seq;
        this.seq = i + 1;
        buffer.putInt(i);
        this.buf.putString(bArr);
        if (sftpATTRS != null) {
            sftpATTRS.dump(this.buf);
        } else {
            this.buf.putInt(0);
        }
        getSession().write(this.packet, this, bArr.length + 9 + (sftpATTRS != null ? sftpATTRS.length() : 4) + 4);
    }

    private void sendRMDIR(byte[] bArr) throws Exception {
        sendPacketPath((byte) 15, bArr);
    }

    private void sendSYMLINK(byte[] bArr, byte[] bArr2) throws Exception {
        sendPacketPath((byte) 20, bArr, bArr2);
    }

    private void sendHARDLINK(byte[] bArr, byte[] bArr2) throws Exception {
        sendPacketPath((byte) 0, bArr, bArr2, "hardlink@openssh.com");
    }

    private void sendREADLINK(byte[] bArr) throws Exception {
        sendPacketPath((byte) 19, bArr);
    }

    private void sendOPENDIR(byte[] bArr) throws Exception {
        sendPacketPath((byte) 11, bArr);
    }

    private void sendREADDIR(byte[] bArr) throws Exception {
        sendPacketPath((byte) 12, bArr);
    }

    private void sendRENAME(byte[] bArr, byte[] bArr2) throws Exception {
        sendPacketPath((byte) 18, bArr, bArr2, this.extension_posix_rename ? "posix-rename@openssh.com" : null);
    }

    private void sendCLOSE(byte[] bArr) throws Exception {
        sendPacketPath((byte) 4, bArr);
    }

    private void sendOPENR(byte[] bArr) throws Exception {
        sendOPEN(bArr, 1);
    }

    private void sendOPENW(byte[] bArr) throws Exception {
        sendOPEN(bArr, 26);
    }

    private void sendOPENA(byte[] bArr) throws Exception {
        sendOPEN(bArr, 10);
    }

    private void sendOPEN(byte[] bArr, int i) throws Exception {
        this.packet.reset();
        putHEAD((byte) 3, bArr.length + 17);
        Buffer buffer = this.buf;
        int i2 = this.seq;
        this.seq = i2 + 1;
        buffer.putInt(i2);
        this.buf.putString(bArr);
        this.buf.putInt(i);
        this.buf.putInt(0);
        getSession().write(this.packet, this, bArr.length + 21);
    }

    private void sendPacketPath(byte b, byte[] bArr) throws Exception {
        sendPacketPath(b, bArr, (String) null);
    }

    private void sendPacketPath(byte b, byte[] bArr, String str) throws Exception {
        this.packet.reset();
        int length = bArr.length + 9;
        if (str == null) {
            putHEAD(b, length);
            Buffer buffer = this.buf;
            int i = this.seq;
            this.seq = i + 1;
            buffer.putInt(i);
        } else {
            length += str.length() + 4;
            putHEAD((byte) -56, length);
            Buffer buffer2 = this.buf;
            int i2 = this.seq;
            this.seq = i2 + 1;
            buffer2.putInt(i2);
            this.buf.putString(Util.str2byte(str));
        }
        this.buf.putString(bArr);
        getSession().write(this.packet, this, length + 4);
    }

    private void sendPacketPath(byte b, byte[] bArr, byte[] bArr2) throws Exception {
        sendPacketPath(b, bArr, bArr2, null);
    }

    private void sendPacketPath(byte b, byte[] bArr, byte[] bArr2, String str) throws Exception {
        this.packet.reset();
        int length = bArr.length + 13 + bArr2.length;
        if (str == null) {
            putHEAD(b, length);
            Buffer buffer = this.buf;
            int i = this.seq;
            this.seq = i + 1;
            buffer.putInt(i);
        } else {
            length += str.length() + 4;
            putHEAD((byte) -56, length);
            Buffer buffer2 = this.buf;
            int i2 = this.seq;
            this.seq = i2 + 1;
            buffer2.putInt(i2);
            this.buf.putString(Util.str2byte(str));
        }
        this.buf.putString(bArr);
        this.buf.putString(bArr2);
        getSession().write(this.packet, this, length + 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int sendWRITE(byte[] bArr, long j, byte[] bArr2, int i, int i2) throws Exception {
        this.opacket.reset();
        if (this.obuf.buffer.length < this.obuf.index + 34 + bArr.length + i2 + 128) {
            i2 = this.obuf.buffer.length - (((this.obuf.index + 34) + bArr.length) + 128);
        }
        putHEAD(this.obuf, (byte) 6, bArr.length + 21 + i2);
        Buffer buffer = this.obuf;
        int i3 = this.seq;
        this.seq = i3 + 1;
        buffer.putInt(i3);
        this.obuf.putString(bArr);
        this.obuf.putLong(j);
        if (this.obuf.buffer != bArr2) {
            this.obuf.putString(bArr2, i, i2);
        } else {
            this.obuf.putInt(i2);
            this.obuf.skip(i2);
        }
        getSession().write(this.opacket, this, bArr.length + 21 + i2 + 4);
        return i2;
    }

    private void sendREAD(byte[] bArr, long j, int i) throws Exception {
        sendREAD(bArr, j, i, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendREAD(byte[] bArr, long j, int i, RequestQueue requestQueue) throws Exception {
        this.packet.reset();
        putHEAD((byte) 5, bArr.length + 21);
        Buffer buffer = this.buf;
        int i2 = this.seq;
        this.seq = i2 + 1;
        buffer.putInt(i2);
        this.buf.putString(bArr);
        this.buf.putLong(j);
        this.buf.putInt(i);
        getSession().write(this.packet, this, bArr.length + 25);
        if (requestQueue != null) {
            requestQueue.add(this.seq - 1, j, i);
        }
    }

    private void putHEAD(Buffer buffer, byte b, int i) throws Exception {
        buffer.putByte((byte) 94);
        buffer.putInt(this.recipient);
        buffer.putInt(i + 4);
        buffer.putInt(i);
        buffer.putByte(b);
    }

    private void putHEAD(byte b, int i) throws Exception {
        putHEAD(this.buf, b, i);
    }

    private Vector glob_remote(String str) throws Exception {
        byte[] bArrStr2byte;
        String strByte2str;
        Vector vector = new Vector();
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            vector.addElement(Util.unquote(str));
            return vector;
        }
        int i = 0;
        String strSubstring = str.substring(0, iLastIndexOf == 0 ? 1 : iLastIndexOf);
        String strSubstring2 = str.substring(iLastIndexOf + 1);
        String strUnquote = Util.unquote(strSubstring);
        byte[][] bArr = new byte[1][];
        if (!isPattern(strSubstring2, bArr)) {
            if (!strUnquote.equals("/")) {
                strUnquote = strUnquote + "/";
            }
            vector.addElement(strUnquote + Util.unquote(strSubstring2));
            return vector;
        }
        byte[] bArr2 = bArr[0];
        sendOPENDIR(Util.str2byte(strUnquote, this.fEncoding));
        Header header = header(this.buf, new Header());
        int i2 = header.length;
        int i3 = header.type;
        fill(this.buf, i2);
        int i4 = 4;
        int i5 = 101;
        if (i3 != 101 && i3 != 102) {
            throw new SftpException(4, "");
        }
        if (i3 == 101) {
            throwStatusError(this.buf, this.buf.getInt());
        }
        byte[] string = this.buf.getString();
        String str2 = null;
        while (true) {
            sendREADDIR(string);
            header = header(this.buf, header);
            int i6 = header.length;
            int i7 = header.type;
            if (i7 != i5 && i7 != 104) {
                throw new SftpException(i4, "");
            }
            if (i7 == i5) {
                fill(this.buf, i6);
                if (_sendCLOSE(string, header)) {
                    return vector;
                }
                return null;
            }
            this.buf.rewind();
            fill(this.buf.buffer, i, i4);
            int i8 = i6 - 4;
            this.buf.reset();
            for (int i9 = this.buf.getInt(); i9 > 0; i9--) {
                if (i8 > 0) {
                    this.buf.shift();
                    int i10 = this.io_in.read(this.buf.buffer, this.buf.index, this.buf.buffer.length > this.buf.index + i8 ? i8 : this.buf.buffer.length - this.buf.index);
                    if (i10 <= 0) {
                        break;
                    }
                    this.buf.index += i10;
                    i8 -= i10;
                }
                byte[] string2 = this.buf.getString();
                if (this.server_version <= 3) {
                    this.buf.getString();
                }
                SftpATTRS.getATTR(this.buf);
                if (this.fEncoding_is_utf8) {
                    bArrStr2byte = string2;
                    strByte2str = null;
                } else {
                    strByte2str = Util.byte2str(string2, this.fEncoding);
                    bArrStr2byte = Util.str2byte(strByte2str, "UTF-8");
                }
                if (Util.glob(bArr2, bArrStr2byte)) {
                    if (strByte2str == null) {
                        strByte2str = Util.byte2str(string2, this.fEncoding);
                    }
                    if (str2 == null) {
                        str2 = !strUnquote.endsWith("/") ? strUnquote + "/" : strUnquote;
                    }
                    vector.addElement(str2 + strByte2str);
                }
            }
            i = 0;
            i4 = 4;
            i5 = 101;
        }
    }

    private boolean isPattern(byte[] bArr) {
        int i;
        int length = bArr.length;
        int i2 = 0;
        while (i2 < length) {
            byte b = bArr[i2];
            if (b == 42 || b == 63) {
                return true;
            }
            if (b == 92 && (i = i2 + 1) < length) {
                i2 = i;
            }
            i2++;
        }
        return false;
    }

    private Vector glob_local(String str) throws Exception {
        byte[] bArr;
        Vector vector = new Vector();
        byte[] bArrStr2byte = Util.str2byte(str, "UTF-8");
        int length = bArrStr2byte.length - 1;
        while (length >= 0) {
            byte b = bArrStr2byte[length];
            if (b == 42 || b == 63) {
                if (fs_is_bs || length <= 0 || bArrStr2byte[length - 1] != 92) {
                    break;
                }
                int i = length - 1;
                if (i <= 0 || bArrStr2byte[length - 2] != 92) {
                    length = i;
                    break;
                }
                length -= 3;
            } else {
                length--;
            }
        }
        if (length < 0) {
            if (!fs_is_bs) {
                str = Util.unquote(str);
            }
            vector.addElement(str);
            return vector;
        }
        while (length >= 0) {
            byte b2 = bArrStr2byte[length];
            if (b2 == file_separatorc || (fs_is_bs && b2 == 47)) {
                break;
            }
            length--;
        }
        if (length < 0) {
            if (!fs_is_bs) {
                str = Util.unquote(str);
            }
            vector.addElement(str);
            return vector;
        }
        if (length == 0) {
            bArr = new byte[]{(byte) file_separatorc};
        } else {
            bArr = new byte[length];
            System.arraycopy(bArrStr2byte, 0, bArr, 0, length);
        }
        int length2 = (bArrStr2byte.length - length) - 1;
        byte[] bArr2 = new byte[length2];
        System.arraycopy(bArrStr2byte, length + 1, bArr2, 0, length2);
        try {
            String[] list = new File(Util.byte2str(bArr, "UTF-8")).list();
            String str2 = Util.byte2str(bArr) + file_separator;
            for (int i2 = 0; i2 < list.length; i2++) {
                if (Util.glob(bArr2, Util.str2byte(list[i2], "UTF-8"))) {
                    vector.addElement(str2 + list[i2]);
                }
            }
        } catch (Exception unused) {
        }
        return vector;
    }

    private void throwStatusError(Buffer buffer, int i) throws SftpException {
        if (this.server_version >= 3 && buffer.getLength() >= 4) {
            throw new SftpException(i, Util.byte2str(buffer.getString(), "UTF-8"));
        }
        throw new SftpException(i, "Failure");
    }

    private static boolean isLocalAbsolutePath(String str) {
        return new File(str).isAbsolute();
    }

    @Override // com.jcraft.jsch.Channel
    public void disconnect() {
        super.disconnect();
    }

    private boolean isPattern(String str, byte[][] bArr) {
        byte[] bArrStr2byte = Util.str2byte(str, "UTF-8");
        if (bArr != null) {
            bArr[0] = bArrStr2byte;
        }
        return isPattern(bArrStr2byte);
    }

    private boolean isPattern(String str) {
        return isPattern(str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fill(Buffer buffer, int i) throws IOException {
        buffer.reset();
        fill(buffer.buffer, 0, i);
        buffer.skip(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int fill(byte[] bArr, int i, int i2) throws IOException {
        int i3 = i;
        while (i2 > 0) {
            int i4 = this.io_in.read(bArr, i3, i2);
            if (i4 <= 0) {
                throw new IOException("inputstream is closed");
            }
            i3 += i4;
            i2 -= i4;
        }
        return i3 - i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void skip(long j) throws IOException {
        while (j > 0) {
            long jSkip = this.io_in.skip(j);
            if (jSkip <= 0) {
                return;
            } else {
                j -= jSkip;
            }
        }
    }

    class Header {
        int length;
        int rid;
        int type;

        Header() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Header header(Buffer buffer, Header header) throws IOException {
        buffer.rewind();
        fill(buffer.buffer, 0, 9);
        header.length = buffer.getInt() - 5;
        header.type = buffer.getByte() & 255;
        header.rid = buffer.getInt();
        return header;
    }

    private String remoteAbsolutePath(String str) throws SftpException {
        if (str.charAt(0) == '/') {
            return str;
        }
        String cwd = getCwd();
        return cwd.endsWith("/") ? cwd + str : cwd + "/" + str;
    }

    private String localAbsolutePath(String str) {
        if (isLocalAbsolutePath(str)) {
            return str;
        }
        String str2 = this.lcwd;
        String str3 = file_separator;
        return str2.endsWith(str3) ? this.lcwd + str : this.lcwd + str3 + str;
    }

    private String isUnique(String str) throws Exception {
        Vector vectorGlob_remote = glob_remote(str);
        if (vectorGlob_remote.size() != 1) {
            throw new SftpException(4, str + " is not unique: " + vectorGlob_remote.toString());
        }
        return (String) vectorGlob_remote.elementAt(0);
    }

    public int getServerVersion() throws SftpException {
        if (!isConnected()) {
            throw new SftpException(4, "The channel is not connected.");
        }
        return this.server_version;
    }

    public void setFilenameEncoding(String str) throws SftpException {
        int serverVersion = getServerVersion();
        if (3 <= serverVersion && serverVersion <= 5 && !str.equals("UTF-8")) {
            throw new SftpException(4, "The encoding can not be changed for this sftp server.");
        }
        if (str.equals("UTF-8")) {
            str = "UTF-8";
        }
        this.fEncoding = str;
        this.fEncoding_is_utf8 = str.equals("UTF-8");
    }

    public String getExtension(String str) {
        Hashtable hashtable = this.extensions;
        if (hashtable == null) {
            return null;
        }
        return (String) hashtable.get(str);
    }

    public String realpath(String str) throws SftpException {
        try {
            return Util.byte2str(_realpath(remoteAbsolutePath(str)), this.fEncoding);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            }
            if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            }
            throw new SftpException(4, "");
        }
    }

    public class LsEntry implements Comparable {
        private SftpATTRS attrs;
        private String filename;
        private String longname;

        LsEntry(String str, String str2, SftpATTRS sftpATTRS) {
            setFilename(str);
            setLongname(str2);
            setAttrs(sftpATTRS);
        }

        public String getFilename() {
            return this.filename;
        }

        void setFilename(String str) {
            this.filename = str;
        }

        public String getLongname() {
            return this.longname;
        }

        void setLongname(String str) {
            this.longname = str;
        }

        public SftpATTRS getAttrs() {
            return this.attrs;
        }

        void setAttrs(SftpATTRS sftpATTRS) {
            this.attrs = sftpATTRS;
        }

        public String toString() {
            return this.longname;
        }

        @Override // java.lang.Comparable
        public int compareTo(Object obj) throws ClassCastException {
            if (obj instanceof LsEntry) {
                return this.filename.compareTo(((LsEntry) obj).getFilename());
            }
            throw new ClassCastException("a decendent of LsEntry must be given.");
        }
    }
}
