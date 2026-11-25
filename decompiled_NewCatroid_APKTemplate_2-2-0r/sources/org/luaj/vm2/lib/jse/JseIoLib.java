package org.luaj.vm2.lib.jse;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.lib.IoLib;

/* JADX INFO: loaded from: classes6.dex */
public class JseIoLib extends IoLib {

    private final class FileImpl extends IoLib.File {
        private boolean closed;
        private final RandomAccessFile file;
        private final InputStream is;
        private boolean nobuffer;
        private final OutputStream os;
        private final /* synthetic */ JseIoLib this$0;

        private FileImpl(JseIoLib jseIoLib, InputStream inputStream) {
            this(jseIoLib, null, inputStream, null);
        }

        private FileImpl(JseIoLib jseIoLib, OutputStream outputStream) {
            this(jseIoLib, null, null, outputStream);
        }

        private FileImpl(JseIoLib jseIoLib, RandomAccessFile randomAccessFile) {
            this(jseIoLib, randomAccessFile, null, null);
        }

        private FileImpl(JseIoLib jseIoLib, RandomAccessFile randomAccessFile, InputStream inputStream, OutputStream outputStream) {
            super();
            this.this$0 = jseIoLib;
            this.closed = false;
            this.nobuffer = false;
            this.file = randomAccessFile;
            if (inputStream == null) {
                inputStream = null;
            } else if (!inputStream.markSupported()) {
                inputStream = new BufferedInputStream(inputStream);
            }
            this.is = inputStream;
            this.os = outputStream;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void close() throws IOException {
            this.closed = true;
            RandomAccessFile randomAccessFile = this.file;
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void flush() throws IOException {
            OutputStream outputStream = this.os;
            if (outputStream != null) {
                outputStream.flush();
            }
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public boolean isclosed() {
            return this.closed;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public boolean isstdfile() {
            return this.file == null;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int peek() throws IOException {
            InputStream inputStream = this.is;
            if (inputStream != null) {
                inputStream.mark(1);
                int i = this.is.read();
                this.is.reset();
                return i;
            }
            RandomAccessFile randomAccessFile = this.file;
            if (randomAccessFile == null) {
                JseIoLib.notimplemented();
                return 0;
            }
            long filePointer = randomAccessFile.getFilePointer();
            int i2 = this.file.read();
            this.file.seek(filePointer);
            return i2;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int read() throws IOException {
            InputStream inputStream = this.is;
            if (inputStream != null) {
                return inputStream.read();
            }
            RandomAccessFile randomAccessFile = this.file;
            if (randomAccessFile != null) {
                return randomAccessFile.read();
            }
            JseIoLib.notimplemented();
            return 0;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int read(byte[] bArr, int i, int i2) throws IOException {
            RandomAccessFile randomAccessFile = this.file;
            if (randomAccessFile != null) {
                return randomAccessFile.read(bArr, i, i2);
            }
            InputStream inputStream = this.is;
            if (inputStream != null) {
                return inputStream.read(bArr, i, i2);
            }
            JseIoLib.notimplemented();
            return i2;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int remaining() throws IOException {
            RandomAccessFile randomAccessFile = this.file;
            if (randomAccessFile != null) {
                return (int) (randomAccessFile.length() - this.file.getFilePointer());
            }
            return -1;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int seek(String str, int i) throws IOException {
            RandomAccessFile randomAccessFile;
            long filePointer;
            long j;
            if (this.file == null) {
                JseIoLib.notimplemented();
                return 0;
            }
            if (FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX.equals(str)) {
                randomAccessFile = this.file;
                j = i;
            } else {
                if ("end".equals(str)) {
                    randomAccessFile = this.file;
                    filePointer = randomAccessFile.length();
                } else {
                    randomAccessFile = this.file;
                    filePointer = randomAccessFile.getFilePointer();
                }
                j = filePointer + ((long) i);
            }
            randomAccessFile.seek(j);
            return (int) this.file.getFilePointer();
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void setvbuf(String str, int i) {
            this.nobuffer = "no".equals(str);
        }

        @Override // org.luaj.vm2.lib.IoLib.File, org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public String tojstring() {
            return new StringBuffer("file (").append(hashCode()).append(Constant.AFTER_QUTO).toString();
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void write(LuaString luaString) throws IOException {
            OutputStream outputStream = this.os;
            if (outputStream != null) {
                outputStream.write(luaString.m_bytes, luaString.m_offset, luaString.m_length);
            } else {
                RandomAccessFile randomAccessFile = this.file;
                if (randomAccessFile != null) {
                    randomAccessFile.write(luaString.m_bytes, luaString.m_offset, luaString.m_length);
                } else {
                    JseIoLib.notimplemented();
                }
            }
            if (this.nobuffer) {
                flush();
            }
        }
    }

    private final class StdinFile extends IoLib.File {
        private final /* synthetic */ JseIoLib this$0;

        private StdinFile(JseIoLib jseIoLib) {
            super();
            this.this$0 = jseIoLib;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void close() throws IOException {
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void flush() throws IOException {
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public boolean isclosed() {
            return false;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public boolean isstdfile() {
            return true;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int peek() throws IOException {
            this.this$0.globals.STDIN.mark(1);
            int i = this.this$0.globals.STDIN.read();
            this.this$0.globals.STDIN.reset();
            return i;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int read() throws IOException {
            return this.this$0.globals.STDIN.read();
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int read(byte[] bArr, int i, int i2) throws IOException {
            return this.this$0.globals.STDIN.read(bArr, i, i2);
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int remaining() throws IOException {
            return 0;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int seek(String str, int i) throws IOException {
            return 0;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void setvbuf(String str, int i) {
        }

        @Override // org.luaj.vm2.lib.IoLib.File, org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public String tojstring() {
            return new StringBuffer("file (").append(hashCode()).append(Constant.AFTER_QUTO).toString();
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void write(LuaString luaString) throws IOException {
        }
    }

    private final class StdoutFile extends IoLib.File {
        private final int file_type;
        private final /* synthetic */ JseIoLib this$0;

        private StdoutFile(JseIoLib jseIoLib, int i) {
            super();
            this.this$0 = jseIoLib;
            this.file_type = i;
        }

        private final PrintStream getPrintStream() {
            return this.file_type == 2 ? this.this$0.globals.STDERR : this.this$0.globals.STDOUT;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void close() throws IOException {
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void flush() throws IOException {
            getPrintStream().flush();
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public boolean isclosed() {
            return false;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public boolean isstdfile() {
            return true;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int peek() throws IOException {
            return 0;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int read() throws IOException {
            return 0;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int read(byte[] bArr, int i, int i2) throws IOException {
            return 0;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int remaining() throws IOException {
            return 0;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public int seek(String str, int i) throws IOException {
            return 0;
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void setvbuf(String str, int i) {
        }

        @Override // org.luaj.vm2.lib.IoLib.File, org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public String tojstring() {
            return new StringBuffer("file (").append(hashCode()).append(Constant.AFTER_QUTO).toString();
        }

        @Override // org.luaj.vm2.lib.IoLib.File
        public void write(LuaString luaString) throws IOException {
            getPrintStream().write(luaString.m_bytes, luaString.m_offset, luaString.m_length);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notimplemented() {
        throw new LuaError("not implemented");
    }

    @Override // org.luaj.vm2.lib.IoLib
    protected IoLib.File openFile(String str, boolean z, boolean z2, boolean z3, boolean z4) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, z ? "r" : "rw");
        if (z2) {
            randomAccessFile.seek(randomAccessFile.length());
        } else if (!z) {
            randomAccessFile.setLength(0L);
        }
        return new FileImpl(randomAccessFile);
    }

    @Override // org.luaj.vm2.lib.IoLib
    protected IoLib.File openProgram(String str, String str2) throws IOException {
        Process processExec = Runtime.getRuntime().exec(str);
        return "w".equals(str2) ? new FileImpl(processExec.getOutputStream()) : new FileImpl(processExec.getInputStream());
    }

    @Override // org.luaj.vm2.lib.IoLib
    protected IoLib.File tmpFile() throws IOException {
        File fileCreateTempFile = File.createTempFile(".luaj", "bin");
        fileCreateTempFile.deleteOnExit();
        return new FileImpl(new RandomAccessFile(fileCreateTempFile, "rw"));
    }

    @Override // org.luaj.vm2.lib.IoLib
    protected IoLib.File wrapStderr() throws IOException {
        return new StdoutFile(2);
    }

    @Override // org.luaj.vm2.lib.IoLib
    protected IoLib.File wrapStdin() throws IOException {
        return new StdinFile();
    }

    @Override // org.luaj.vm2.lib.IoLib
    protected IoLib.File wrapStdout() throws IOException {
        return new StdoutFile(1);
    }
}
