package net.bytebuddy.agent;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.gaurav.avnc.vnc.XKeySym;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import io.netty.util.internal.StringUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileLock;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import net.bytebuddy.agent.utility.nullability.MaybeNull;
import net.bytebuddy.agent.utility.nullability.UnknownNull;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes12.dex */
public interface VirtualMachine {
    void detach() throws IOException;

    Properties getAgentProperties() throws IOException;

    Properties getSystemProperties() throws IOException;

    void loadAgent(String str) throws IOException;

    void loadAgent(String str, @MaybeNull String str2) throws IOException;

    void loadAgentLibrary(String str) throws IOException;

    void loadAgentLibrary(String str, @MaybeNull String str2) throws IOException;

    void loadAgentPath(String str) throws IOException;

    void loadAgentPath(String str, @MaybeNull String str2) throws IOException;

    String startLocalManagementAgent() throws IOException;

    void startManagementAgent(Properties properties) throws IOException;

    public enum Resolver implements PrivilegedAction<Class<? extends VirtualMachine>> {
        INSTANCE;

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.security.PrivilegedAction
        public Class<? extends VirtualMachine> run() {
            try {
                Class.forName("com.sun.jna.Platform");
                return System.getProperty(SystemProperties.JAVA_VM_NAME, "").toUpperCase(Locale.US).contains("J9") ? ForOpenJ9.class : ForHotSpot.class;
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Optional JNA dependency is not available", e);
            }
        }
    }

    public static abstract class AbstractBase implements VirtualMachine {
        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgent(String str) throws IOException {
            loadAgent(str, null);
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgentPath(String str) throws IOException {
            loadAgentPath(str, null);
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgentLibrary(String str) throws IOException {
            loadAgentLibrary(str, null);
        }
    }

    public static class ForHotSpot extends AbstractBase {
        private static final String ARGUMENT_DELIMITER = "=";
        private static final String INSTRUMENT_COMMAND = "instrument";
        private static final String LOAD_COMMAND = "load";
        private static final String PROTOCOL_VERSION = "1";
        private final Connection connection;

        protected ForHotSpot(Connection connection) {
            this.connection = connection;
        }

        public static VirtualMachine attach(String str) throws IOException {
            if (Platform.isWindows()) {
                return attach(str, new Connection.ForJnaWindowsNamedPipe.Factory());
            }
            if (Platform.isSolaris()) {
                return attach(str, new Connection.ForJnaSolarisDoor.Factory(15, 100L, TimeUnit.MILLISECONDS));
            }
            return attach(str, Connection.ForJnaPosixSocket.Factory.withDefaultTemporaryFolder(15, 100L, TimeUnit.MILLISECONDS));
        }

        public static VirtualMachine attach(String str, Connection.Factory factory) throws IOException {
            return new ForHotSpot(factory.connect(str));
        }

        private static void checkHeader(Connection.Response response) throws IOException {
            byte[] bArr = new byte[1];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int i = response.read(bArr);
                if (i == -1) {
                    break;
                }
                if (i > 0) {
                    byte b = bArr[0];
                    if (b == 10) {
                        break;
                    } else {
                        byteArrayOutputStream.write(b);
                    }
                }
            }
            int i2 = Integer.parseInt(byteArrayOutputStream.toString("UTF-8"));
            if (i2 == 0) {
                return;
            }
            if (i2 == 101) {
                throw new IOException("Protocol mismatch with target VM");
            }
            byte[] bArr2 = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            while (true) {
                int i3 = response.read(bArr2);
                if (i3 != -1) {
                    byteArrayOutputStream2.write(bArr2, 0, i3);
                } else {
                    throw new IllegalStateException(byteArrayOutputStream2.toString("UTF-8"));
                }
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public Properties getSystemProperties() throws IOException {
            return getProperties("properties");
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public Properties getAgentProperties() throws IOException {
            return getProperties("agentProperties");
        }

        private Properties getProperties(String str) throws IOException {
            Connection.Response responseExecute = this.connection.execute("1", str, null, null, null);
            try {
                checkHeader(responseExecute);
                byte[] bArr = new byte[1024];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int i = responseExecute.read(bArr);
                    if (i != -1) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    } else {
                        Properties properties = new Properties();
                        properties.load(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                        return properties;
                    }
                }
            } finally {
                responseExecute.close();
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgent(String str, @MaybeNull String str2) throws IOException {
            load(str, false, str2);
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgentPath(String str, @MaybeNull String str2) throws IOException {
            load(str, true, str2);
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgentLibrary(String str, @MaybeNull String str2) throws IOException {
            load(str, false, str2);
        }

        protected void load(String str, boolean z, @MaybeNull String str2) throws IOException {
            Connection connection = this.connection;
            String[] strArr = new String[4];
            strArr[0] = LOAD_COMMAND;
            strArr[1] = INSTRUMENT_COMMAND;
            strArr[2] = Boolean.toString(z);
            if (str2 != null) {
                str = str + "=" + str2;
            }
            strArr[3] = str;
            Connection.Response responseExecute = connection.execute("1", strArr);
            try {
                checkHeader(responseExecute);
            } finally {
                responseExecute.close();
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void startManagementAgent(Properties properties) throws IOException {
            StringBuilder sb = new StringBuilder("ManagementAgent.start ");
            boolean z = true;
            for (Map.Entry entry : properties.entrySet()) {
                if (!(entry.getKey() instanceof String) || !((String) entry.getKey()).startsWith("com.sun.management.")) {
                    throw new IllegalArgumentException("Illegal property name: " + entry.getKey());
                }
                if (z) {
                    z = false;
                } else {
                    sb.append(StringUtil.SPACE);
                }
                sb.append(((String) entry.getKey()).substring("com.sun.management.".length())).append('=');
                String string = entry.getValue().toString();
                if (string.contains(" ")) {
                    sb.append('\'').append(string).append('\'');
                } else {
                    sb.append(string);
                }
            }
            Connection.Response responseExecute = this.connection.execute("1", "jcmd", sb.toString(), null, null);
            try {
                checkHeader(responseExecute);
            } finally {
                responseExecute.close();
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public String startLocalManagementAgent() throws IOException {
            Connection.Response responseExecute = this.connection.execute("1", "jcmd", "ManagementAgent.start_local", null, null);
            try {
                checkHeader(responseExecute);
                return getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
            } finally {
                responseExecute.close();
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void detach() throws IOException {
            this.connection.close();
        }

        public interface Connection extends Closeable {

            public interface Response extends Closeable {
                int read(byte[] bArr) throws IOException;
            }

            Response execute(String str, String... strArr) throws IOException;

            public interface Factory {
                Connection connect(String str) throws IOException;

                public static abstract class ForSocketFile implements Factory {
                    private static final String ATTACH_FILE_PREFIX = ".attach_pid";
                    private static final String SOCKET_FILE_PREFIX = ".java_pid";
                    private final int attempts;
                    private final long pause;
                    private final String temporaryDirectory;
                    private final TimeUnit timeUnit;

                    protected abstract Connection doConnect(File file) throws IOException;

                    protected abstract void kill(String str, int i);

                    protected ForSocketFile(String str, int i, long j, TimeUnit timeUnit) {
                        this.temporaryDirectory = str;
                        this.attempts = i;
                        this.pause = j;
                        this.timeUnit = timeUnit;
                    }

                    /* JADX WARN: Code duplicated, block: B:40:0x00ea  */
                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Factory
                    public Connection connect(String str) throws IOException {
                        File file = new File(this.temporaryDirectory, SOCKET_FILE_PREFIX + str);
                        if (!file.exists()) {
                            String str2 = ATTACH_FILE_PREFIX + str;
                            File file2 = new File("/proc/" + str + "/cwd/" + str2);
                            try {
                                if (!file2.createNewFile() && !file2.isFile()) {
                                    throw new IllegalStateException("Could not create attach file: " + file2);
                                }
                            } catch (IOException unused) {
                                file2 = new File(this.temporaryDirectory, str2);
                                if (!file2.createNewFile() && !file2.isFile()) {
                                    throw new IllegalStateException("Could not create attach file: " + file2);
                                }
                            }
                            try {
                                try {
                                    kill(str, 3);
                                    int i = this.attempts;
                                    while (!file.exists()) {
                                        int i2 = i - 1;
                                        if (i <= 0) {
                                            break;
                                        }
                                        this.timeUnit.sleep(this.pause);
                                        i = i2;
                                    }
                                    if (!file.exists()) {
                                        throw new IllegalStateException("Target VM did not respond: " + str);
                                    }
                                    if (!file2.delete()) {
                                        file2.deleteOnExit();
                                    }
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    throw new IllegalStateException(e);
                                }
                            } catch (Throwable th) {
                                if (!file2.delete()) {
                                    file2.deleteOnExit();
                                }
                                throw th;
                            }
                            if (!file2.delete()) {
                                file2.deleteOnExit();
                            }
                            throw th;
                        }
                        return doConnect(file);
                    }
                }
            }

            public static abstract class OnPersistentByteChannel<T> implements Connection {
                private static final byte[] BLANK = {0};

                protected abstract void close(T t) throws IOException;

                protected abstract T connect() throws IOException;

                protected abstract int read(T t, byte[] bArr) throws IOException;

                protected abstract void write(T t, byte[] bArr) throws IOException;

                @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection
                public Response execute(String str, String... strArr) throws IOException {
                    T tConnect = connect();
                    try {
                        write(tConnect, str.getBytes("UTF-8"));
                        write(tConnect, BLANK);
                        for (String str2 : strArr) {
                            if (str2 != null) {
                                write(tConnect, str2.getBytes("UTF-8"));
                            }
                            write(tConnect, BLANK);
                        }
                        return new Response(tConnect);
                    } catch (Throwable th) {
                        close(tConnect);
                        if (th instanceof RuntimeException) {
                            throw ((RuntimeException) th);
                        }
                        if (th instanceof IOException) {
                            throw ((IOException) th);
                        }
                        throw new IllegalStateException(th);
                    }
                }

                private class Response implements Response {
                    private final T connection;

                    private Response(T t) {
                        this.connection = t;
                    }

                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response
                    public int read(byte[] bArr) throws IOException {
                        return OnPersistentByteChannel.this.read(this.connection, bArr);
                    }

                    @Override // java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws IOException {
                        OnPersistentByteChannel.this.close(this.connection);
                    }
                }
            }

            public static class ForJnaPosixSocket extends OnPersistentByteChannel<Integer> {
                private final PosixLibrary library;
                private final File socket;

                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() {
                }

                protected ForJnaPosixSocket(PosixLibrary posixLibrary, File file) {
                    this.library = posixLibrary;
                    this.socket = file;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                /* JADX WARN: Can't rename method to resolve collision */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r0v1, types: [int] */
                /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Integer] */
                /* JADX WARN: Type inference fix 'apply assigned field type' failed
                java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                 */
                @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.OnPersistentByteChannel
                public Integer connect() throws LastErrorException {
                    int iSocket = this.library.socket(1, 1, 0);
                    try {
                        PosixLibrary.SocketAddress socketAddress = new PosixLibrary.SocketAddress();
                        socketAddress.setPath(this.socket.getAbsolutePath());
                        this.library.connect(iSocket, socketAddress, socketAddress.size());
                        iSocket = Integer.valueOf((int) iSocket);
                        return iSocket;
                    } catch (RuntimeException e) {
                        this.library.close(iSocket);
                        throw e;
                    }
                }

                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.OnPersistentByteChannel
                public int read(Integer num, byte[] bArr) throws LastErrorException {
                    int i = this.library.read(num.intValue(), ByteBuffer.wrap(bArr), bArr.length);
                    if (i == 0) {
                        return -1;
                    }
                    return i;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.OnPersistentByteChannel
                public void write(Integer num, byte[] bArr) throws LastErrorException {
                    this.library.write(num.intValue(), ByteBuffer.wrap(bArr), bArr.length);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.OnPersistentByteChannel
                public void close(Integer num) throws LastErrorException {
                    this.library.close(num.intValue());
                }

                protected interface PosixLibrary extends Library {
                    int close(int i) throws LastErrorException;

                    int connect(int i, SocketAddress socketAddress, int i2) throws LastErrorException;

                    int kill(int i, int i2) throws LastErrorException;

                    int read(int i, ByteBuffer byteBuffer, int i2) throws LastErrorException;

                    int socket(int i, int i2, int i3) throws LastErrorException;

                    int write(int i, ByteBuffer byteBuffer, int i2) throws LastErrorException;

                    public static class SocketAddress extends Structure {
                        public short family = 1;
                        public byte[] path = new byte[100];

                        protected void setPath(String str) {
                            try {
                                System.arraycopy(str.getBytes("UTF-8"), 0, this.path, 0, str.length());
                                System.arraycopy(new byte[]{0}, 0, this.path, str.length(), 1);
                            } catch (UnsupportedEncodingException e) {
                                throw new IllegalStateException(e);
                            }
                        }

                        protected List<String> getFieldOrder() {
                            return Arrays.asList("family", "path");
                        }
                    }
                }

                public static class Factory extends Factory.ForSocketFile {
                    private final PosixLibrary library;

                    public interface MacLibrary extends Library {
                        public static final int CS_DARWIN_USER_TEMP_DIR = 65537;

                        long confstr(int i, Pointer pointer, long j);
                    }

                    public Factory(String str, int i, long j, TimeUnit timeUnit) {
                        super(str, i, j, timeUnit);
                        this.library = (PosixLibrary) Native.loadLibrary("c", PosixLibrary.class);
                    }

                    public static Factory withDefaultTemporaryFolder(int i, long j, TimeUnit timeUnit) {
                        String string = "/tmp";
                        if (Platform.isMac()) {
                            MacLibrary macLibrary = (MacLibrary) Native.loadLibrary("c", MacLibrary.class);
                            Memory memory = new Memory(4096L);
                            long jConfstr = macLibrary.confstr(MacLibrary.CS_DARWIN_USER_TEMP_DIR, memory, memory.size());
                            if (jConfstr != 0 && jConfstr <= 4096) {
                                string = memory.getString(0L);
                            }
                        }
                        return new Factory(string, i, j, timeUnit);
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Factory.ForSocketFile
                    protected void kill(String str, int i) throws LastErrorException {
                        this.library.kill(Integer.parseInt(str), i);
                    }

                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Factory.ForSocketFile
                    public Connection doConnect(File file) {
                        return new ForJnaPosixSocket(this.library, file);
                    }
                }
            }

            public static class ForJnaWindowsNamedPipe implements Connection {
                private static final int MEM_RELEASE = 32768;
                private final WindowsAttachLibrary attachLibrary;
                private final WinDef.LPVOID code;
                private final WindowsLibrary library;
                private final WinNT.HANDLE process;
                private final SecureRandom random = new SecureRandom();

                protected interface WindowsAttachLibrary extends StdCallLibrary {
                    @MaybeNull
                    WinDef.LPVOID allocate_remote_argument(WinNT.HANDLE handle, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String str4, @MaybeNull String str5);

                    @MaybeNull
                    WinDef.LPVOID allocate_remote_code(WinNT.HANDLE handle);
                }

                protected interface WindowsLibrary extends StdCallLibrary {
                    @MaybeNull
                    WinNT.HANDLE CreateRemoteThread(WinNT.HANDLE handle, @MaybeNull WinBase.SECURITY_ATTRIBUTES security_attributes, int i, Pointer pointer, Pointer pointer2, @MaybeNull WinDef.DWORD dword, @MaybeNull Pointer pointer3);

                    boolean GetExitCodeThread(WinNT.HANDLE handle, IntByReference intByReference);

                    Pointer VirtualAllocEx(WinNT.HANDLE handle, Pointer pointer, int i, int i2, int i3);

                    boolean VirtualFreeEx(WinNT.HANDLE handle, Pointer pointer, int i, int i2);
                }

                protected ForJnaWindowsNamedPipe(WindowsLibrary windowsLibrary, WindowsAttachLibrary windowsAttachLibrary, WinNT.HANDLE handle, WinDef.LPVOID lpvoid) {
                    this.library = windowsLibrary;
                    this.attachLibrary = windowsAttachLibrary;
                    this.process = handle;
                    this.code = lpvoid;
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection
                public Response execute(String str, String... strArr) throws Win32Exception {
                    int lastError;
                    if (!"1".equals(str)) {
                        throw new IllegalArgumentException("Unknown protocol version: " + str);
                    }
                    if (strArr.length > 4) {
                        throw new IllegalArgumentException("Cannot supply more then four arguments to Windows attach mechanism: " + Arrays.asList(strArr));
                    }
                    String str2 = "\\\\.\\pipe\\javatool" + Math.abs(this.random.nextInt() + 1);
                    WinNT.HANDLE handleCreateNamedPipe = Kernel32.INSTANCE.CreateNamedPipe(str2, 1, 0, 1, 4096, 8192, 0, (WinBase.SECURITY_ATTRIBUTES) null);
                    if (handleCreateNamedPipe == null) {
                        throw new Win32Exception(Native.getLastError());
                    }
                    try {
                        WinDef.LPVOID lpvoidAllocate_remote_argument = this.attachLibrary.allocate_remote_argument(this.process, str2, strArr.length < 1 ? null : strArr[0], strArr.length < 2 ? null : strArr[1], strArr.length < 3 ? null : strArr[2], strArr.length < 4 ? null : strArr[3]);
                        if (lpvoidAllocate_remote_argument == null) {
                            throw new Win32Exception(Native.getLastError());
                        }
                        try {
                            WinNT.HANDLE handleCreateRemoteThread = this.library.CreateRemoteThread(this.process, null, 0, this.code.getPointer(), lpvoidAllocate_remote_argument.getPointer(), null, null);
                            if (handleCreateRemoteThread == null) {
                                throw new Win32Exception(Native.getLastError());
                            }
                            try {
                                int iWaitForSingleObject = Kernel32.INSTANCE.WaitForSingleObject(handleCreateRemoteThread, -1);
                                if (iWaitForSingleObject != 0) {
                                    throw new Win32Exception(iWaitForSingleObject);
                                }
                                IntByReference intByReference = new IntByReference();
                                if (!this.library.GetExitCodeThread(handleCreateRemoteThread, intByReference)) {
                                    throw new Win32Exception(Native.getLastError());
                                }
                                if (intByReference.getValue() != 0) {
                                    throw new IllegalStateException("Target VM could not dispatch command successfully: " + intByReference.getValue());
                                }
                                if (!Kernel32.INSTANCE.ConnectNamedPipe(handleCreateNamedPipe, (WinBase.OVERLAPPED) null) && (lastError = Native.getLastError()) != 535) {
                                    throw new Win32Exception(lastError);
                                }
                                NamedPipeResponse namedPipeResponse = new NamedPipeResponse(handleCreateNamedPipe);
                                if (!Kernel32.INSTANCE.CloseHandle(handleCreateRemoteThread)) {
                                    throw new Win32Exception(Native.getLastError());
                                }
                                if (this.library.VirtualFreeEx(this.process, lpvoidAllocate_remote_argument.getPointer(), 0, 32768)) {
                                    return namedPipeResponse;
                                }
                                throw new Win32Exception(Native.getLastError());
                            } catch (Throwable th) {
                                if (!Kernel32.INSTANCE.CloseHandle(handleCreateRemoteThread)) {
                                    throw new Win32Exception(Native.getLastError());
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            if (!this.library.VirtualFreeEx(this.process, lpvoidAllocate_remote_argument.getPointer(), 0, 32768)) {
                                throw new Win32Exception(Native.getLastError());
                            }
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        if (!Kernel32.INSTANCE.CloseHandle(handleCreateNamedPipe)) {
                            throw new Win32Exception(Native.getLastError());
                        }
                        if (th3 instanceof RuntimeException) {
                            throw th3;
                        }
                        throw new IllegalStateException(th3);
                    }
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() throws Win32Exception {
                    try {
                        if (!this.library.VirtualFreeEx(this.process, this.code.getPointer(), 0, 32768)) {
                            throw new Win32Exception(Native.getLastError());
                        }
                        if (!Kernel32.INSTANCE.CloseHandle(this.process)) {
                            throw new Win32Exception(Native.getLastError());
                        }
                    } catch (Throwable th) {
                        if (!Kernel32.INSTANCE.CloseHandle(this.process)) {
                            throw new Win32Exception(Native.getLastError());
                        }
                        throw th;
                    }
                }

                protected static class NamedPipeResponse implements Response {
                    private final WinNT.HANDLE pipe;

                    protected NamedPipeResponse(WinNT.HANDLE handle) {
                        this.pipe = handle;
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response
                    public int read(byte[] bArr) throws Win32Exception {
                        IntByReference intByReference = new IntByReference();
                        if (!Kernel32.INSTANCE.ReadFile(this.pipe, bArr, bArr.length, intByReference, (WinBase.OVERLAPPED) null)) {
                            int lastError = Native.getLastError();
                            if (lastError == 109) {
                                return -1;
                            }
                            throw new Win32Exception(lastError);
                        }
                        return intByReference.getValue();
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                    @Override // java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws Win32Exception {
                        try {
                            if (!Kernel32.INSTANCE.DisconnectNamedPipe(this.pipe)) {
                                throw new Win32Exception(Native.getLastError());
                            }
                            if (!Kernel32.INSTANCE.CloseHandle(this.pipe)) {
                                throw new Win32Exception(Native.getLastError());
                            }
                        } catch (Throwable th) {
                            if (!Kernel32.INSTANCE.CloseHandle(this.pipe)) {
                                throw new Win32Exception(Native.getLastError());
                            }
                            throw th;
                        }
                    }
                }

                public static class Factory implements Factory {
                    public static final String LIBRARY_NAME = "net.bytebuddy.library.name";
                    private final WindowsLibrary library = (WindowsLibrary) Native.loadLibrary("kernel32", WindowsLibrary.class, W32APIOptions.DEFAULT_OPTIONS);
                    private final WindowsAttachLibrary attachLibrary = (WindowsAttachLibrary) Native.loadLibrary(System.getProperty(LIBRARY_NAME, "attach_hotspot_windows"), WindowsAttachLibrary.class);

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Factory
                    public Connection connect(String str) throws Win32Exception {
                        WinNT.HANDLE handleOpenProcess = Kernel32.INSTANCE.OpenProcess(2039803, false, Integer.parseInt(str));
                        if (handleOpenProcess == null) {
                            throw new Win32Exception(Native.getLastError());
                        }
                        try {
                            WinDef.LPVOID lpvoidAllocate_remote_code = this.attachLibrary.allocate_remote_code(handleOpenProcess);
                            if (lpvoidAllocate_remote_code == null) {
                                throw new Win32Exception(Native.getLastError());
                            }
                            return new ForJnaWindowsNamedPipe(this.library, this.attachLibrary, handleOpenProcess, lpvoidAllocate_remote_code);
                        } catch (Throwable th) {
                            if (!Kernel32.INSTANCE.CloseHandle(handleOpenProcess)) {
                                throw new Win32Exception(Native.getLastError());
                            }
                            if (th instanceof RuntimeException) {
                                throw th;
                            }
                            throw new IllegalStateException(th);
                        }
                    }
                }
            }

            public static class ForJnaSolarisDoor implements Connection {
                private final SolarisLibrary library;
                private final File socket;

                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() {
                }

                protected ForJnaSolarisDoor(SolarisLibrary solarisLibrary, File file) {
                    this.library = solarisLibrary;
                    this.socket = file;
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection
                public Response execute(String str, String... strArr) throws LastErrorException, IOException {
                    int iOpen = this.library.open(this.socket.getAbsolutePath(), 2);
                    try {
                        SolarisLibrary.DoorArgument doorArgument = new SolarisLibrary.DoorArgument();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byteArrayOutputStream.write(str.getBytes("UTF-8"));
                        byteArrayOutputStream.write(0);
                        for (String str2 : strArr) {
                            if (str2 != null) {
                                byteArrayOutputStream.write(str2.getBytes("UTF-8"));
                            }
                            byteArrayOutputStream.write(0);
                        }
                        doorArgument.dataSize = byteArrayOutputStream.size();
                        Memory memory = new Memory(byteArrayOutputStream.size());
                        memory.write(0L, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                        doorArgument.dataPointer = memory;
                        Memory memory2 = new Memory(128L);
                        doorArgument.resultPointer = memory2;
                        doorArgument.resultSize = (int) memory2.size();
                        if (this.library.door_call(iOpen, doorArgument) != 0) {
                            throw new IllegalStateException("Door call to target VM failed");
                        }
                        if (doorArgument.resultSize < 4 || doorArgument.resultPointer.getInt(0L) != 0) {
                            throw new IllegalStateException("Target VM could not execute door call");
                        }
                        if (doorArgument.descriptorCount != 1 || doorArgument.descriptorPointer == null) {
                            throw new IllegalStateException("Did not receive communication descriptor from target VM");
                        }
                        Response response = new Response(this.library, doorArgument.descriptorPointer.getInt(4L));
                        this.library.close(iOpen);
                        return response;
                    } catch (Throwable th) {
                        this.library.close(iOpen);
                        throw th;
                    }
                }

                protected interface SolarisLibrary extends Library {
                    int close(int i) throws LastErrorException;

                    int door_call(int i, DoorArgument doorArgument) throws LastErrorException;

                    int kill(int i, int i2) throws LastErrorException;

                    int open(String str, int i) throws LastErrorException;

                    int read(int i, ByteBuffer byteBuffer, int i2) throws LastErrorException;

                    public static class DoorArgument extends Structure {

                        @MaybeNull
                        public Pointer dataPointer;
                        public int dataSize;
                        public int descriptorCount;

                        @MaybeNull
                        public Pointer descriptorPointer;

                        @UnknownNull
                        public Pointer resultPointer;
                        public int resultSize;

                        protected List<String> getFieldOrder() {
                            return Arrays.asList("dataPointer", "dataSize", "descriptorPointer", "descriptorCount", "resultPointer", "resultSize");
                        }
                    }
                }

                protected static class Response implements Response {
                    private final int handle;
                    private final SolarisLibrary library;

                    protected Response(SolarisLibrary solarisLibrary, int i) {
                        this.library = solarisLibrary;
                        this.handle = i;
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response
                    public int read(byte[] bArr) throws LastErrorException {
                        int i = this.library.read(this.handle, ByteBuffer.wrap(bArr), bArr.length);
                        if (i == 0) {
                            return -1;
                        }
                        return i;
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                    @Override // java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws LastErrorException {
                        this.library.close(this.handle);
                    }
                }

                public static class Factory extends Factory.ForSocketFile {
                    private final SolarisLibrary library;

                    public Factory(int i, long j, TimeUnit timeUnit) {
                        super("/tmp", i, j, timeUnit);
                        this.library = (SolarisLibrary) Native.loadLibrary("c", SolarisLibrary.class);
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Factory.ForSocketFile
                    protected void kill(String str, int i) throws LastErrorException {
                        this.library.kill(Integer.parseInt(str), i);
                    }

                    @Override // net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Factory.ForSocketFile
                    protected Connection doConnect(File file) {
                        return new ForJnaSolarisDoor(this.library, file);
                    }
                }
            }
        }
    }

    public static class ForOpenJ9 extends AbstractBase {
        private static final String IBM_TEMPORARY_FOLDER = "com.ibm.tools.attach.directory";
        private static final SecureRandom SECURE_RANDOM = new SecureRandom();
        private final Socket socket;

        protected ForOpenJ9(Socket socket) {
            this.socket = socket;
        }

        public static VirtualMachine attach(String str) throws IOException {
            return attach(str, 5000, Platform.isWindows() ? new Dispatcher.ForJnaWindowsEnvironment() : new Dispatcher.ForJnaPosixEnvironment(15, 100L, TimeUnit.MILLISECONDS));
        }

        /* JADX WARN: Code duplicated, block: B:52:0x00f4  */
        public static VirtualMachine attach(String str, int i, Dispatcher dispatcher) throws IOException {
            Properties properties;
            String hexString;
            File[] fileArr;
            int i2;
            long ownerIdOf;
            File file = new File(System.getProperty(IBM_TEMPORARY_FOLDER, dispatcher.getTemporaryFolder(str)), ".com_ibm_tools_attach");
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(file, "_attachlock"), "rw");
            try {
                FileLock fileLockLock = randomAccessFile.getChannel().lock();
                try {
                    RandomAccessFile randomAccessFile2 = new RandomAccessFile(new File(file, "_master"), "rw");
                    try {
                        FileLock fileLockLock2 = randomAccessFile2.getChannel().lock();
                        try {
                            File[] fileArrListFiles = file.listFiles();
                            if (fileArrListFiles == null) {
                                throw new IllegalStateException("No descriptor files found in " + file);
                            }
                            long jUserId = dispatcher.userId();
                            ArrayList<Properties> arrayList = new ArrayList();
                            int length = fileArrListFiles.length;
                            int i3 = 0;
                            while (i3 < length) {
                                File file2 = fileArrListFiles[i3];
                                if (file2.isDirectory()) {
                                    i2 = i3;
                                    if (dispatcher.getOwnerIdOf(file2) == jUserId) {
                                        File file3 = new File(file2, "attachInfo");
                                        if (file3.isFile()) {
                                            Properties properties2 = new Properties();
                                            FileInputStream fileInputStream = new FileInputStream(file3);
                                            try {
                                                properties2.load(fileInputStream);
                                                fileInputStream.close();
                                                int i4 = Integer.parseInt(properties2.getProperty("processId"));
                                                fileArr = fileArrListFiles;
                                                try {
                                                    ownerIdOf = Long.parseLong(properties2.getProperty("userUid"));
                                                } catch (NumberFormatException unused) {
                                                    ownerIdOf = 0;
                                                }
                                                if (jUserId != 0 && ownerIdOf == 0) {
                                                    ownerIdOf = dispatcher.getOwnerIdOf(file3);
                                                }
                                                if (i4 == 0 || dispatcher.isExistingProcess(i4)) {
                                                    arrayList.add(properties2);
                                                } else if (jUserId == 0 || ownerIdOf == jUserId) {
                                                    File[] fileArrListFiles2 = file2.listFiles();
                                                    if (fileArrListFiles2 != null) {
                                                        for (File file4 : fileArrListFiles2) {
                                                            if (!file4.delete()) {
                                                                file4.deleteOnExit();
                                                            }
                                                        }
                                                    }
                                                    if (!file2.delete()) {
                                                        file2.deleteOnExit();
                                                    }
                                                }
                                            } catch (Throwable th) {
                                                fileInputStream.close();
                                                throw th;
                                            }
                                        } else {
                                            fileArr = fileArrListFiles;
                                            length = length;
                                        }
                                    } else {
                                        fileArr = fileArrListFiles;
                                        length = length;
                                    }
                                } else {
                                    fileArr = fileArrListFiles;
                                    length = length;
                                    i2 = i3;
                                }
                                i3 = i2 + 1;
                                fileArrListFiles = fileArr;
                                length = length;
                            }
                            fileLockLock2.release();
                            randomAccessFile2.close();
                            Iterator it = arrayList.iterator();
                            do {
                                if (!it.hasNext()) {
                                    properties = null;
                                    break;
                                }
                                properties = (Properties) it.next();
                            } while (!properties.getProperty("processId").equalsIgnoreCase(str));
                            if (properties == null) {
                                throw new IllegalStateException("Could not locate target process info in " + file);
                            }
                            int i5 = 0;
                            ServerSocket serverSocket = new ServerSocket(0);
                            try {
                                serverSocket.setSoTimeout(i);
                                File file5 = new File(file, properties.getProperty("vmId"));
                                SecureRandom secureRandom = SECURE_RANDOM;
                                synchronized (secureRandom) {
                                    hexString = Long.toHexString(secureRandom.nextLong());
                                }
                                File file6 = new File(file5, "replyInfo");
                                try {
                                    if (file6.createNewFile()) {
                                        dispatcher.setPermissions(file6, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP);
                                    }
                                    FileOutputStream fileOutputStream = new FileOutputStream(file6);
                                    try {
                                        fileOutputStream.write(hexString.getBytes("UTF-8"));
                                        fileOutputStream.write("\n".getBytes("UTF-8"));
                                        fileOutputStream.write(Long.toString(serverSocket.getLocalPort()).getBytes("UTF-8"));
                                        fileOutputStream.write("\n".getBytes("UTF-8"));
                                        fileOutputStream.close();
                                        HashMap map = new HashMap();
                                        try {
                                            String string = Long.toString(dispatcher.pid());
                                            for (Properties properties3 : arrayList) {
                                                if (!properties3.getProperty("processId").equalsIgnoreCase(string)) {
                                                    String property = properties3.getProperty("attachNotificationSync");
                                                    RandomAccessFile randomAccessFile3 = new RandomAccessFile(property == null ? new File(file, "attachNotificationSync") : new File(property), "rw");
                                                    try {
                                                        map.put(randomAccessFile3, randomAccessFile3.getChannel().lock());
                                                    } catch (IOException unused2) {
                                                        randomAccessFile3.close();
                                                    }
                                                }
                                            }
                                            File[] fileArrListFiles3 = file.listFiles();
                                            if (fileArrListFiles3 != null) {
                                                for (File file7 : fileArrListFiles3) {
                                                    String name2 = file7.getName();
                                                    if (!name2.startsWith(".trash_") && !name2.equalsIgnoreCase("_attachlock") && !name2.equalsIgnoreCase("_master") && !name2.equalsIgnoreCase("_notifier")) {
                                                        i5++;
                                                    }
                                                }
                                            }
                                            int i6 = i5;
                                            boolean z = Boolean.parseBoolean(properties.getProperty("globalSemaphore"));
                                            dispatcher.incrementSemaphore(file, "_notifier", z, i6);
                                            try {
                                                Socket socketAccept = serverSocket.accept();
                                                String str2 = new String(read(socketAccept), "UTF-8");
                                                if (str2.contains(StringUtil.SPACE + hexString + StringUtil.SPACE)) {
                                                    ForOpenJ9 forOpenJ9 = new ForOpenJ9(socketAccept);
                                                    dispatcher.decrementSemaphore(file, "_notifier", z, i6);
                                                    for (Map.Entry entry : map.entrySet()) {
                                                        try {
                                                            ((FileLock) entry.getValue()).release();
                                                            ((RandomAccessFile) entry.getKey()).close();
                                                        } catch (Throwable th2) {
                                                            ((RandomAccessFile) entry.getKey()).close();
                                                            throw th2;
                                                        }
                                                    }
                                                    if (!file6.delete()) {
                                                        file6.deleteOnExit();
                                                    }
                                                    serverSocket.close();
                                                    fileLockLock.release();
                                                    randomAccessFile.close();
                                                    return forOpenJ9;
                                                }
                                                socketAccept.close();
                                                throw new IllegalStateException("Unexpected answered to attachment: " + str2);
                                            } catch (Throwable th3) {
                                                dispatcher.decrementSemaphore(file, "_notifier", z, i6);
                                                throw th3;
                                            }
                                        } catch (Throwable th4) {
                                            for (Map.Entry entry2 : map.entrySet()) {
                                                try {
                                                    ((FileLock) entry2.getValue()).release();
                                                    ((RandomAccessFile) entry2.getKey()).close();
                                                } catch (Throwable th5) {
                                                    ((RandomAccessFile) entry2.getKey()).close();
                                                    throw th5;
                                                }
                                            }
                                            throw th4;
                                        }
                                    } catch (Throwable th6) {
                                        fileOutputStream.close();
                                        throw th6;
                                    }
                                } catch (Throwable th7) {
                                    if (!file6.delete()) {
                                        file6.deleteOnExit();
                                    }
                                    throw th7;
                                }
                            } catch (Throwable th8) {
                                serverSocket.close();
                                throw th8;
                            }
                        } catch (Throwable th9) {
                            fileLockLock2.release();
                            throw th9;
                        }
                    } catch (Throwable th10) {
                        randomAccessFile2.close();
                        throw th10;
                    }
                } catch (Throwable th11) {
                    fileLockLock.release();
                    throw th11;
                }
            } catch (Throwable th12) {
                randomAccessFile.close();
                throw th12;
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public Properties getSystemProperties() throws IOException {
            write(this.socket, "ATTACH_GETSYSTEMPROPERTIES".getBytes("UTF-8"));
            Properties properties = new Properties();
            properties.load(new ByteArrayInputStream(read(this.socket)));
            return properties;
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public Properties getAgentProperties() throws IOException {
            write(this.socket, "ATTACH_GETAGENTPROPERTIES".getBytes("UTF-8"));
            Properties properties = new Properties();
            properties.load(new ByteArrayInputStream(read(this.socket)));
            return properties;
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgent(String str, @MaybeNull String str2) throws IOException {
            Socket socket = this.socket;
            StringBuilder sbAppend = new StringBuilder("ATTACH_LOADAGENT(instrument,").append(str).append('=');
            if (str2 == null) {
                str2 = "";
            }
            write(socket, sbAppend.append(str2).append(')').toString().getBytes("UTF-8"));
            String str3 = new String(read(this.socket), "UTF-8");
            if (str3.startsWith("ATTACH_ERR")) {
                throw new IllegalStateException("Target VM failed loading agent: ".concat(str3));
            }
            if (!str3.startsWith("ATTACH_ACK") && !str3.startsWith("ATTACH_RESULT=")) {
                throw new IllegalStateException("Unexpected response: ".concat(str3));
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgentPath(String str, @MaybeNull String str2) throws IOException {
            write(this.socket, ("ATTACH_LOADAGENTPATH(" + str + (str2 == null ? "" : "," + str2) + ')').getBytes("UTF-8"));
            String str3 = new String(read(this.socket), "UTF-8");
            if (str3.startsWith("ATTACH_ERR")) {
                throw new IllegalStateException("Target VM failed loading native agent: ".concat(str3));
            }
            if (!str3.startsWith("ATTACH_ACK") && !str3.startsWith("ATTACH_RESULT=")) {
                throw new IllegalStateException("Unexpected response: ".concat(str3));
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void loadAgentLibrary(String str, @MaybeNull String str2) throws IOException {
            write(this.socket, ("ATTACH_LOADAGENTLIBRARY(" + str + (str2 == null ? "" : "," + str2) + ')').getBytes("UTF-8"));
            String str3 = new String(read(this.socket), "UTF-8");
            if (str3.startsWith("ATTACH_ERR")) {
                throw new IllegalStateException("Target VM failed loading native library: ".concat(str3));
            }
            if (!str3.startsWith("ATTACH_ACK") && !str3.startsWith("ATTACH_RESULT=")) {
                throw new IllegalStateException("Unexpected response: ".concat(str3));
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void startManagementAgent(Properties properties) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            properties.store(byteArrayOutputStream, (String) null);
            write(this.socket, "ATTACH_START_MANAGEMENT_AGENT".getBytes("UTF-8"));
            write(this.socket, byteArrayOutputStream.toByteArray());
            String str = new String(read(this.socket), "UTF-8");
            if (str.startsWith("ATTACH_ERR")) {
                throw new IllegalStateException("Target VM could not start management agent: ".concat(str));
            }
            if (!str.startsWith("ATTACH_ACK") && !str.startsWith("ATTACH_RESULT=")) {
                throw new IllegalStateException("Unexpected response: ".concat(str));
            }
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public String startLocalManagementAgent() throws IOException {
            write(this.socket, "ATTACH_START_LOCAL_MANAGEMENT_AGENT".getBytes("UTF-8"));
            String str = new String(read(this.socket), "UTF-8");
            if (str.startsWith("ATTACH_ERR")) {
                throw new IllegalStateException("Target VM could not start management agent: ".concat(str));
            }
            if (str.startsWith("ATTACH_ACK")) {
                return str.substring("ATTACH_ACK".length());
            }
            if (str.startsWith("ATTACH_RESULT=")) {
                return str.substring("ATTACH_RESULT=".length());
            }
            throw new IllegalStateException("Unexpected response: ".concat(str));
        }

        @Override // net.bytebuddy.agent.VirtualMachine
        public void detach() throws IOException {
            try {
                write(this.socket, "ATTACH_DETACH".getBytes("UTF-8"));
                read(this.socket);
            } finally {
                this.socket.close();
            }
        }

        private static void write(Socket socket, byte[] bArr) throws IOException {
            socket.getOutputStream().write(bArr);
            socket.getOutputStream().write(0);
            socket.getOutputStream().flush();
        }

        private static byte[] read(Socket socket) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i = socket.getInputStream().read(bArr);
                if (i == -1) {
                    break;
                }
                if (i > 0) {
                    int i2 = i - 1;
                    if (bArr[i2] == 0) {
                        byteArrayOutputStream.write(bArr, 0, i2);
                        break;
                    }
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
            return byteArrayOutputStream.toByteArray();
        }

        public interface Dispatcher {
            void decrementSemaphore(File file, String str, boolean z, int i);

            int getOwnerIdOf(File file);

            String getTemporaryFolder(String str);

            void incrementSemaphore(File file, String str, boolean z, int i);

            boolean isExistingProcess(int i);

            int pid();

            void setPermissions(File file, int i);

            int userId();

            public static class ForJnaPosixEnvironment implements Dispatcher {
                private final int attempts;
                private final PosixLibrary library = (PosixLibrary) Native.loadLibrary("c", PosixLibrary.class);
                private final long pause;
                private final TimeUnit timeUnit;

                public ForJnaPosixEnvironment(int i, long j, TimeUnit timeUnit) {
                    this.attempts = i;
                    this.pause = j;
                    this.timeUnit = timeUnit;
                }

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public String getTemporaryFolder(String str) {
                    if (Platform.isLinux()) {
                        File file = new File("/proc/" + str + "/root/tmp");
                        if (file.isDirectory() && file.canRead()) {
                            return file.getAbsolutePath();
                        }
                    }
                    String str2 = System.getenv("TMPDIR");
                    return str2 == null ? "/tmp" : str2;
                }

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public int pid() {
                    return this.library.getpid();
                }

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public int userId() {
                    return this.library.getuid();
                }

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public boolean isExistingProcess(int i) {
                    return this.library.kill(i, 0) != 3;
                }

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public int getOwnerIdOf(File file) {
                    try {
                        Runtime runtime = Runtime.getRuntime();
                        String[] strArr = new String[4];
                        strArr[0] = "stat";
                        strArr[1] = Platform.isMac() ? "-f" : "-c";
                        strArr[2] = "%u";
                        strArr[3] = file.getAbsolutePath();
                        Process processExec = runtime.exec(strArr);
                        int i = this.attempts;
                        String line = new BufferedReader(new InputStreamReader(processExec.getInputStream(), "UTF-8")).readLine();
                        while (processExec.exitValue() != 0) {
                            try {
                                try {
                                    throw new IllegalStateException("Error while executing stat");
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    throw new IllegalStateException(e);
                                }
                            } catch (IllegalThreadStateException unused) {
                                Thread.sleep(this.timeUnit.toMillis(this.pause));
                                i--;
                                if (i <= 0) {
                                    processExec.destroy();
                                    throw new IllegalStateException("Command for stat did not exit in time");
                                }
                            }
                        }
                        return Integer.parseInt(line);
                    } catch (IOException e2) {
                        throw new IllegalStateException("Unable to execute stat command", e2);
                    }
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public void setPermissions(File file, int i) throws LastErrorException {
                    this.library.chmod(file.getAbsolutePath(), i);
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public void incrementSemaphore(File file, String str, boolean z, int i) throws LastErrorException {
                    notifySemaphore(file, str, i, (short) 1, (short) 0, false);
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public void decrementSemaphore(File file, String str, boolean z, int i) throws LastErrorException {
                    notifySemaphore(file, str, i, (short) -1, (short) 6144, true);
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
                private void notifySemaphore(File file, String str, int i, short s, short s2, boolean z) throws LastErrorException {
                    PosixLibrary posixLibrary = this.library;
                    int iSemget = posixLibrary.semget(posixLibrary.ftok(new File(file, str).getAbsolutePath(), 161), 2, XKeySym.XK_sacute);
                    PosixLibrary.SemaphoreOperation semaphoreOperation = new PosixLibrary.SemaphoreOperation();
                    semaphoreOperation.operation = s;
                    semaphoreOperation.flags = s2;
                    while (true) {
                        int i2 = i - 1;
                        if (i <= 0) {
                            return;
                        }
                        try {
                            this.library.semop(iSemget, semaphoreOperation, 1);
                            i = i2;
                        } catch (LastErrorException e) {
                            if (!z || (Native.getLastError() != 11 && Native.getLastError() != 35)) {
                                throw e;
                            }
                            return;
                        }
                    }
                }

                protected interface PosixLibrary extends Library {
                    public static final int EAGAIN = 11;
                    public static final int EDEADLK = 35;
                    public static final int ESRCH = 3;
                    public static final short IPC_NOWAIT = 2048;
                    public static final int NULL_SIGNAL = 0;
                    public static final short SEM_UNDO = 4096;

                    int chmod(String str, int i) throws LastErrorException;

                    int ftok(String str, int i) throws LastErrorException;

                    int getpid() throws LastErrorException;

                    int getuid() throws LastErrorException;

                    int kill(int i, int i2) throws LastErrorException;

                    int semget(int i, int i2, int i3) throws LastErrorException;

                    int semop(int i, SemaphoreOperation semaphoreOperation, int i2) throws LastErrorException;

                    public static class SemaphoreOperation extends Structure {
                        public short flags;
                        public short number;
                        public short operation;

                        protected List<String> getFieldOrder() {
                            return Arrays.asList("number", "operation", OverlayablePolicy.NAME_flags);
                        }
                    }
                }
            }

            public static class ForJnaWindowsEnvironment implements Dispatcher {
                private static final String CREATION_MUTEX_NAME = "j9shsemcreationMutex";
                private static final int NO_USER_ID = 0;
                private final WindowsLibrary library = (WindowsLibrary) Native.loadLibrary("kernel32", WindowsLibrary.class, W32APIOptions.DEFAULT_OPTIONS);

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public int getOwnerIdOf(File file) {
                    return 0;
                }

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public void setPermissions(File file, int i) {
                }

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public int userId() {
                    return 0;
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public String getTemporaryFolder(String str) throws Win32Exception {
                    WinDef.DWORD dword = new WinDef.DWORD(260L);
                    char[] cArr = new char[dword.intValue()];
                    if (Kernel32.INSTANCE.GetTempPath(dword, cArr).intValue() == 0) {
                        throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                    }
                    return Native.toString(cArr);
                }

                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public int pid() {
                    return Kernel32.INSTANCE.GetCurrentProcessId();
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public boolean isExistingProcess(int i) throws Win32Exception {
                    WinNT.HANDLE handleOpenProcess = Kernel32.INSTANCE.OpenProcess(1024, false, i);
                    if (handleOpenProcess == null) {
                        throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                    }
                    IntByReference intByReference = new IntByReference();
                    if (Kernel32.INSTANCE.GetExitCodeProcess(handleOpenProcess, intByReference)) {
                        return intByReference.getValue() == 259;
                    }
                    throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public void incrementSemaphore(File file, String str, boolean z, int i) throws Win32Exception {
                    AttachmentHandle attachmentHandleOpenSemaphore = openSemaphore(file, str, z);
                    while (true) {
                        int i2 = i - 1;
                        if (i > 0) {
                            try {
                                if (!this.library.ReleaseSemaphore(attachmentHandleOpenSemaphore.getHandle(), 1L, null)) {
                                    throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                                }
                                i = i2;
                            } catch (Throwable th) {
                                attachmentHandleOpenSemaphore.close();
                                throw th;
                            }
                        } else {
                            attachmentHandleOpenSemaphore.close();
                            return;
                        }
                    }
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                @Override // net.bytebuddy.agent.VirtualMachine.ForOpenJ9.Dispatcher
                public void decrementSemaphore(File file, String str, boolean z, int i) throws Win32Exception {
                    AttachmentHandle attachmentHandleOpenSemaphore = openSemaphore(file, str, z);
                    while (true) {
                        int i2 = i - 1;
                        if (i > 0) {
                            try {
                                int iWaitForSingleObject = Kernel32.INSTANCE.WaitForSingleObject(attachmentHandleOpenSemaphore.getHandle(), 0);
                                if (iWaitForSingleObject != 0 && iWaitForSingleObject != 128) {
                                    if (iWaitForSingleObject != 258) {
                                        throw new Win32Exception(iWaitForSingleObject);
                                    }
                                    attachmentHandleOpenSemaphore.close();
                                    return;
                                }
                                i = i2;
                            } catch (Throwable th) {
                                attachmentHandleOpenSemaphore.close();
                                throw th;
                            }
                        } else {
                            attachmentHandleOpenSemaphore.close();
                            return;
                        }
                    }
                }

                /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                private AttachmentHandle openSemaphore(File file, String str, boolean z) throws Win32Exception {
                    WinNT.SECURITY_DESCRIPTOR security_descriptor = new WinNT.SECURITY_DESCRIPTOR(65536);
                    if (!Advapi32.INSTANCE.InitializeSecurityDescriptor(security_descriptor, 1)) {
                        throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                    }
                    if (!Advapi32.INSTANCE.SetSecurityDescriptorDacl(security_descriptor, true, (WinNT.ACL) null, true)) {
                        throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                    }
                    WindowsLibrary.SecurityAttributes securityAttributes = new WindowsLibrary.SecurityAttributes();
                    securityAttributes.length = new WinDef.DWORD(securityAttributes.size());
                    securityAttributes.securityDescriptor = security_descriptor.getPointer();
                    WinNT.HANDLE handleCreateMutex = this.library.CreateMutex(securityAttributes, false, CREATION_MUTEX_NAME);
                    if (handleCreateMutex == null) {
                        int iGetLastError = Kernel32.INSTANCE.GetLastError();
                        if (iGetLastError == 183) {
                            handleCreateMutex = this.library.OpenMutex(2031617, false, CREATION_MUTEX_NAME);
                            if (handleCreateMutex == null) {
                                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                            }
                        } else {
                            throw new Win32Exception(iGetLastError);
                        }
                    }
                    int iWaitForSingleObject = Kernel32.INSTANCE.WaitForSingleObject(handleCreateMutex, 2000);
                    if (iWaitForSingleObject == -1 || iWaitForSingleObject == 258) {
                        throw new Win32Exception(iWaitForSingleObject);
                    }
                    try {
                        String str2 = (z ? "Global\\" : "") + (file.getAbsolutePath() + '_' + str).replaceAll("[^a-zA-Z0-9_]", "") + "_semaphore";
                        WinNT.HANDLE handleOpenSemaphoreW = this.library.OpenSemaphoreW(WindowsLibrary.SEMAPHORE_ALL_ACCESS, false, str2);
                        if (handleOpenSemaphoreW != null) {
                            WinNT.HANDLE handleOpenSemaphoreW2 = this.library.OpenSemaphoreW(WindowsLibrary.SEMAPHORE_ALL_ACCESS, false, str2 + "_set0");
                            if (handleOpenSemaphoreW2 == null) {
                                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                            }
                            AttachmentHandle attachmentHandle = new AttachmentHandle(handleOpenSemaphoreW, handleOpenSemaphoreW2);
                            if (this.library.ReleaseMutex(handleCreateMutex)) {
                                return attachmentHandle;
                            }
                            throw new Win32Exception(Native.getLastError());
                        }
                        WinNT.HANDLE handleCreateSemaphoreW = this.library.CreateSemaphoreW(null, 0L, 2147483647L, str2);
                        if (handleCreateSemaphoreW != null) {
                            WinNT.HANDLE handleCreateSemaphoreW2 = this.library.CreateSemaphoreW(null, 0L, 2147483647L, str2 + "_set0");
                            if (handleCreateSemaphoreW2 == null) {
                                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                            }
                            AttachmentHandle attachmentHandle2 = new AttachmentHandle(handleCreateSemaphoreW, handleCreateSemaphoreW2);
                            if (this.library.ReleaseMutex(handleCreateMutex)) {
                                return attachmentHandle2;
                            }
                            throw new Win32Exception(Native.getLastError());
                        }
                        throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                    } catch (Throwable th) {
                        if (!this.library.ReleaseMutex(handleCreateMutex)) {
                            throw new Win32Exception(Native.getLastError());
                        }
                        throw th;
                    }
                }

                protected interface WindowsLibrary extends StdCallLibrary {
                    public static final int SEMAPHORE_ALL_ACCESS = 2031619;

                    @MaybeNull
                    WinNT.HANDLE CreateMutex(SecurityAttributes securityAttributes, boolean z, String str);

                    @MaybeNull
                    WinNT.HANDLE CreateSemaphoreW(@MaybeNull WinBase.SECURITY_ATTRIBUTES security_attributes, long j, long j2, String str);

                    WinNT.HANDLE OpenMutex(int i, boolean z, String str);

                    @MaybeNull
                    WinNT.HANDLE OpenSemaphoreW(int i, boolean z, String str);

                    boolean ReleaseMutex(WinNT.HANDLE handle);

                    boolean ReleaseSemaphore(WinNT.HANDLE handle, long j, @MaybeNull Long l);

                    public static class SecurityAttributes extends Structure {
                        public boolean inherit;

                        @MaybeNull
                        public WinDef.DWORD length;

                        @MaybeNull
                        public Pointer securityDescriptor;

                        protected List<String> getFieldOrder() {
                            return Arrays.asList("length", "securityDescriptor", "inherit");
                        }
                    }
                }

                protected static class AttachmentHandle implements Closeable {
                    private final WinNT.HANDLE child;
                    private final WinNT.HANDLE parent;

                    protected AttachmentHandle(WinNT.HANDLE handle, WinNT.HANDLE handle2) {
                        this.parent = handle;
                        this.child = handle2;
                    }

                    protected WinNT.HANDLE getHandle() {
                        return this.child;
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.platform.win32.Win32Exception */
                    @Override // java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws Win32Exception {
                        try {
                            if (!Kernel32.INSTANCE.CloseHandle(this.child)) {
                                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                            }
                            if (!Kernel32.INSTANCE.CloseHandle(this.parent)) {
                                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                            }
                        } catch (Throwable th) {
                            Kernel32.INSTANCE.CloseHandle(this.parent);
                            throw th;
                        }
                    }
                }
            }
        }
    }
}
