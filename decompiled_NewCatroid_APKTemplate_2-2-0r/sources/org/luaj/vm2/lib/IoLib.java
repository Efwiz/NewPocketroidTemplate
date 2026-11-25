package org.luaj.vm2.lib;

import com.beust.jcommander.Parameters;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes6.dex */
public abstract class IoLib extends TwoArgFunction {
    private static final int FILE_CLOSE = 11;
    private static final int FILE_FLUSH = 12;
    private static final int FILE_LINES = 13;
    private static final int FILE_READ = 14;
    private static final int FILE_SEEK = 15;
    private static final int FILE_SETVBUF = 16;
    private static final int FILE_WRITE = 17;
    protected static final int FTYPE_NAMED = 3;
    protected static final int FTYPE_STDERR = 2;
    protected static final int FTYPE_STDIN = 0;
    protected static final int FTYPE_STDOUT = 1;
    private static final int IO_CLOSE = 0;
    private static final int IO_FLUSH = 1;
    private static final int IO_INDEX = 18;
    private static final int IO_INPUT = 2;
    private static final int IO_LINES = 3;
    private static final int IO_OPEN = 4;
    private static final int IO_OUTPUT = 5;
    private static final int IO_POPEN = 6;
    private static final int IO_READ = 7;
    private static final int IO_TMPFILE = 8;
    private static final int IO_TYPE = 9;
    private static final int IO_WRITE = 10;
    private static final int LINES_ITER = 19;
    static /* synthetic */ Class class$org$luaj$vm2$lib$IoLib$IoLibV;
    LuaTable filemethods;
    protected Globals globals;
    private static final LuaValue STDIN = valueOf("stdin");
    private static final LuaValue STDOUT = valueOf("stdout");
    private static final LuaValue STDERR = valueOf("stderr");
    private static final LuaValue FILE = valueOf("file");
    private static final LuaValue CLOSED_FILE = valueOf("closed file");
    public static final String[] IO_NAMES = {"close", "flush", "input", "lines", "open", "output", "popen", "read", "tmpfile", ModuleXmlParser.TYPE, "write"};
    public static final String[] FILE_NAMES = {"close", "flush", "lines", "read", "seek", "setvbuf", "write"};
    private File infile = null;
    private File outfile = null;
    private File errfile = null;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract class File extends LuaValue {
        protected File() {
        }

        public abstract void close() throws IOException;

        public abstract void flush() throws IOException;

        @Override // org.luaj.vm2.LuaValue
        public LuaValue get(LuaValue luaValue) {
            return IoLib.this.filemethods.get(luaValue);
        }

        public abstract boolean isclosed();

        public abstract boolean isstdfile();

        public abstract int peek() throws IOException;

        public abstract int read() throws IOException;

        public abstract int read(byte[] bArr, int i, int i2) throws IOException;

        public abstract int remaining() throws IOException;

        public abstract int seek(String str, int i) throws IOException;

        public abstract void setvbuf(String str, int i);

        @Override // org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public String tojstring() {
            return new StringBuffer("file: ").append(Integer.toHexString(hashCode())).toString();
        }

        @Override // org.luaj.vm2.LuaValue
        public int type() {
            return 7;
        }

        @Override // org.luaj.vm2.LuaValue
        public String typename() {
            return "userdata";
        }

        public abstract void write(LuaString luaString) throws IOException;
    }

    static final class IoLibV extends VarArgFunction {
        private File f;
        public IoLib iolib;

        public IoLibV() {
        }

        public IoLibV(File file, String str, int i, IoLib ioLib) {
            this.f = file;
            this.f1059name = str;
            this.opcode = i;
            this.iolib = ioLib;
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            try {
                switch (this.opcode) {
                    case 0:
                        return this.iolib._io_close(varargs.arg1());
                    case 1:
                        return this.iolib._io_flush();
                    case 2:
                        return this.iolib._io_input(varargs.arg1());
                    case 3:
                        return this.iolib._io_lines(varargs.isvalue(1) ? varargs.checkjstring(1) : null);
                    case 4:
                        return this.iolib._io_open(varargs.checkjstring(1), varargs.optjstring(2, "r"));
                    case 5:
                        return this.iolib._io_output(varargs.arg1());
                    case 6:
                        return this.iolib._io_popen(varargs.checkjstring(1), varargs.optjstring(2, "r"));
                    case 7:
                        return this.iolib._io_read(varargs);
                    case 8:
                        return this.iolib._io_tmpfile();
                    case 9:
                        return this.iolib._io_type(varargs.arg1());
                    case 10:
                        return this.iolib._io_write(varargs);
                    case 11:
                        return this.iolib._file_close(varargs.arg1());
                    case 12:
                        return this.iolib._file_flush(varargs.arg1());
                    case 13:
                        return this.iolib._file_lines(varargs.arg1());
                    case 14:
                        return this.iolib._file_read(varargs.arg1(), varargs.subargs(2));
                    case 15:
                        return this.iolib._file_seek(varargs.arg1(), varargs.optjstring(2, "cur"), varargs.optint(3, 0));
                    case 16:
                        return this.iolib._file_setvbuf(varargs.arg1(), varargs.checkjstring(2), varargs.optint(3, 1024));
                    case 17:
                        return this.iolib._file_write(varargs.arg1(), varargs.subargs(2));
                    case 18:
                        return this.iolib._io_index(varargs.arg(2));
                    case 19:
                        return this.iolib._lines_iter(this.f);
                    default:
                        return NONE;
                }
            } catch (IOException e) {
                return IoLib.errorresult(e);
            }
        }
    }

    private static File checkfile(LuaValue luaValue) {
        File fileOptfile = optfile(luaValue);
        if (fileOptfile == null) {
            argerror(1, "file");
        }
        checkopen(fileOptfile);
        return fileOptfile;
    }

    private static File checkopen(File file) {
        if (file.isclosed()) {
            error("attempt to use a closed file");
        }
        return file;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Varargs errorresult(Exception exc) {
        String message = exc.getMessage();
        StringBuffer stringBuffer = new StringBuffer("io error: ");
        if (message == null) {
            message = exc.toString();
        }
        return errorresult(stringBuffer.append(message).toString());
    }

    private static Varargs errorresult(String str) {
        return varargsOf(NIL, valueOf(str));
    }

    private File errput() {
        File file = this.errfile;
        if (file != null) {
            return file;
        }
        File fileIoopenfile = ioopenfile(2, Parameters.DEFAULT_OPTION_PREFIXES, "w");
        this.errfile = fileIoopenfile;
        return fileIoopenfile;
    }

    public static LuaValue freadall(File file) throws IOException {
        int iRemaining = file.remaining();
        return iRemaining >= 0 ? freadbytes(file, iRemaining) : freaduntil(file, false);
    }

    public static LuaValue freadbytes(File file, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = file.read(bArr, 0, i);
        return i2 < 0 ? NIL : LuaString.valueUsing(bArr, 0, i2);
    }

    private static void freadchars(File file, String str, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        while (true) {
            int iPeek = file.peek();
            if (str.indexOf(iPeek) < 0) {
                return;
            }
            file.read();
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.write(iPeek);
            }
        }
    }

    public static LuaValue freadline(File file) throws IOException {
        return freaduntil(file, true);
    }

    public static LuaValue freadnumber(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        freadchars(file, " \t\r\n", null);
        freadchars(file, "-+", byteArrayOutputStream);
        freadchars(file, "0123456789", byteArrayOutputStream);
        freadchars(file, ".", byteArrayOutputStream);
        freadchars(file, "0123456789", byteArrayOutputStream);
        String string = byteArrayOutputStream.toString();
        return string.length() > 0 ? valueOf(Double.parseDouble(string)) : NIL;
    }

    public static LuaValue freaduntil(File file, boolean z) throws IOException {
        int i;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            if (!z) {
                while (true) {
                    i = file.read();
                    if (i <= 0) {
                        break;
                    }
                    byteArrayOutputStream.write(i);
                }
            } else {
                while (true) {
                    i = file.read();
                    if (i <= 0 || i == 10) {
                        break;
                    }
                    if (i != 13) {
                        byteArrayOutputStream.write(i);
                    }
                }
            }
        } catch (EOFException unused) {
            i = -1;
        }
        return (i >= 0 || byteArrayOutputStream.size() != 0) ? LuaString.valueUsing(byteArrayOutputStream.toByteArray()) : NIL;
    }

    private File input() {
        File file = this.infile;
        if (file != null) {
            return file;
        }
        File fileIoopenfile = ioopenfile(0, Parameters.DEFAULT_OPTION_PREFIXES, "r");
        this.infile = fileIoopenfile;
        return fileIoopenfile;
    }

    private static Varargs ioclose(File file) throws IOException {
        if (file.isstdfile()) {
            return errorresult("cannot close standard file");
        }
        file.close();
        return successresult();
    }

    private File ioopenfile(int i, String str, String str2) {
        try {
            return rawopenfile(i, str, str2);
        } catch (Exception e) {
            error(new StringBuffer("io error: ").append(e.getMessage()).toString());
            return null;
        }
    }

    private Varargs ioread(File file, Varargs varargs) throws IOException {
        LuaValue luaValueFreadbytes;
        int iNarg = varargs.narg();
        LuaValue[] luaValueArr = new LuaValue[iNarg];
        int i = 0;
        while (i < iNarg) {
            int i2 = i + 1;
            LuaValue luaValueArg = varargs.arg(i2);
            int iType = luaValueArg.type();
            if (iType != 3) {
                if (iType == 4) {
                    LuaString luaStringCheckstring = luaValueArg.checkstring();
                    if (luaStringCheckstring.m_length == 2 && luaStringCheckstring.m_bytes[luaStringCheckstring.m_offset] == 42) {
                        byte b = luaStringCheckstring.m_bytes[luaStringCheckstring.m_offset + 1];
                        if (b == 97) {
                            luaValueFreadbytes = freadall(file);
                        } else if (b == 108) {
                            luaValueFreadbytes = freadline(file);
                        } else if (b == 110) {
                            luaValueFreadbytes = freadnumber(file);
                        }
                    }
                }
                return argerror(i2, "(invalid format)");
            }
            luaValueFreadbytes = freadbytes(file, luaValueArg.toint());
            luaValueArr[i] = luaValueFreadbytes;
            if (luaValueFreadbytes.isnil()) {
                i = i2;
                break;
            }
            i = i2;
        }
        return i == 0 ? NIL : varargsOf(luaValueArr, 0, i);
    }

    private static Varargs iowrite(File file, Varargs varargs) throws IOException {
        int iNarg = varargs.narg();
        for (int i = 1; i <= iNarg; i++) {
            file.write(varargs.checkstring(i));
        }
        return file;
    }

    private Varargs lines(File file) {
        try {
            return new IoLibV(file, "lnext", 19, this);
        } catch (Exception e) {
            return error(new StringBuffer("lines: ").append(e).toString());
        }
    }

    private static File optfile(LuaValue luaValue) {
        if (luaValue instanceof File) {
            return (File) luaValue;
        }
        return null;
    }

    private File output() {
        File file = this.outfile;
        if (file != null) {
            return file;
        }
        File fileIoopenfile = ioopenfile(1, Parameters.DEFAULT_OPTION_PREFIXES, "w");
        this.outfile = fileIoopenfile;
        return fileIoopenfile;
    }

    private File rawopenfile(int i, String str, String str2) throws IOException {
        if (i == 0) {
            return wrapStdin();
        }
        if (i == 1) {
            return wrapStdout();
        }
        if (i != 2) {
            return openFile(str, str2.startsWith("r"), str2.startsWith("a"), str2.indexOf(Marker.ANY_NON_NULL_MARKER) > 0, str2.endsWith(JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME));
        }
        return wrapStderr();
    }

    private void setLibInstance(LuaTable luaTable) {
        for (LuaValue luaValue : luaTable.keys()) {
            ((IoLibV) luaTable.get(luaValue)).iolib = this;
        }
    }

    private static Varargs successresult() {
        return LuaValue.TRUE;
    }

    public Varargs _file_close(LuaValue luaValue) throws IOException {
        return ioclose(checkfile(luaValue));
    }

    public Varargs _file_flush(LuaValue luaValue) throws IOException {
        checkfile(luaValue).flush();
        return LuaValue.TRUE;
    }

    public Varargs _file_lines(LuaValue luaValue) {
        return lines(checkfile(luaValue));
    }

    public Varargs _file_read(LuaValue luaValue, Varargs varargs) throws IOException {
        return ioread(checkfile(luaValue), varargs);
    }

    public Varargs _file_seek(LuaValue luaValue, String str, int i) throws IOException {
        return valueOf(checkfile(luaValue).seek(str, i));
    }

    public Varargs _file_setvbuf(LuaValue luaValue, String str, int i) {
        checkfile(luaValue).setvbuf(str, i);
        return LuaValue.TRUE;
    }

    public Varargs _file_write(LuaValue luaValue, Varargs varargs) throws IOException {
        return iowrite(checkfile(luaValue), varargs);
    }

    public Varargs _io_close(LuaValue luaValue) throws IOException {
        File fileOutput = luaValue.isnil() ? output() : checkfile(luaValue);
        checkopen(fileOutput);
        return ioclose(fileOutput);
    }

    public Varargs _io_flush() throws IOException {
        checkopen(output());
        this.outfile.flush();
        return LuaValue.TRUE;
    }

    public Varargs _io_index(LuaValue luaValue) {
        if (luaValue.equals(STDOUT)) {
            return output();
        }
        if (luaValue.equals(STDIN)) {
            return input();
        }
        return luaValue.equals(STDERR) ? errput() : NIL;
    }

    public Varargs _io_input(LuaValue luaValue) {
        File fileIoopenfile;
        if (luaValue.isnil()) {
            fileIoopenfile = input();
        } else {
            fileIoopenfile = luaValue.isstring() ? ioopenfile(3, luaValue.checkjstring(), "r") : checkfile(luaValue);
        }
        this.infile = fileIoopenfile;
        return fileIoopenfile;
    }

    public Varargs _io_lines(String str) {
        File fileInput = str == null ? input() : ioopenfile(3, str, "r");
        this.infile = fileInput;
        checkopen(fileInput);
        return lines(this.infile);
    }

    public Varargs _io_open(String str, String str2) throws IOException {
        return rawopenfile(3, str, str2);
    }

    public Varargs _io_output(LuaValue luaValue) {
        File fileIoopenfile;
        if (luaValue.isnil()) {
            fileIoopenfile = output();
        } else {
            fileIoopenfile = luaValue.isstring() ? ioopenfile(3, luaValue.checkjstring(), "w") : checkfile(luaValue);
        }
        this.outfile = fileIoopenfile;
        return fileIoopenfile;
    }

    public Varargs _io_popen(String str, String str2) throws IOException {
        return openProgram(str, str2);
    }

    public Varargs _io_read(Varargs varargs) throws IOException {
        checkopen(input());
        return ioread(this.infile, varargs);
    }

    public Varargs _io_tmpfile() throws IOException {
        return tmpFile();
    }

    public Varargs _io_type(LuaValue luaValue) {
        File fileOptfile = optfile(luaValue);
        if (fileOptfile != null) {
            return fileOptfile.isclosed() ? CLOSED_FILE : FILE;
        }
        return NIL;
    }

    public Varargs _io_write(Varargs varargs) throws IOException {
        checkopen(output());
        return iowrite(this.outfile, varargs);
    }

    public Varargs _lines_iter(LuaValue luaValue) throws IOException {
        return freadline(checkfile(luaValue));
    }

    @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        this.globals = luaValue2.checkglobals();
        LuaTable luaTable = new LuaTable();
        Class clsClass$ = class$org$luaj$vm2$lib$IoLib$IoLibV;
        if (clsClass$ == null) {
            clsClass$ = class$("org.luaj.vm2.lib.IoLib$IoLibV");
            class$org$luaj$vm2$lib$IoLib$IoLibV = clsClass$;
        }
        bind(luaTable, clsClass$, IO_NAMES);
        LuaTable luaTable2 = new LuaTable();
        this.filemethods = luaTable2;
        Class clsClass$2 = class$org$luaj$vm2$lib$IoLib$IoLibV;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.luaj.vm2.lib.IoLib$IoLibV");
            class$org$luaj$vm2$lib$IoLib$IoLibV = clsClass$2;
        }
        bind(luaTable2, clsClass$2, FILE_NAMES, 11);
        LuaTable luaTable3 = new LuaTable();
        Class clsClass$3 = class$org$luaj$vm2$lib$IoLib$IoLibV;
        if (clsClass$3 == null) {
            clsClass$3 = class$("org.luaj.vm2.lib.IoLib$IoLibV");
            class$org$luaj$vm2$lib$IoLib$IoLibV = clsClass$3;
        }
        bind(luaTable3, clsClass$3, new String[]{"__index"}, 18);
        luaTable.setmetatable(luaTable3);
        setLibInstance(luaTable);
        setLibInstance(this.filemethods);
        setLibInstance(luaTable3);
        luaValue2.set("io", luaTable);
        luaValue2.get("package").get("loaded").set("io", luaTable);
        return luaTable;
    }

    protected abstract File openFile(String str, boolean z, boolean z2, boolean z3, boolean z4) throws IOException;

    protected abstract File openProgram(String str, String str2) throws IOException;

    protected abstract File tmpFile() throws IOException;

    protected abstract File wrapStderr() throws IOException;

    protected abstract File wrapStdin() throws IOException;

    protected abstract File wrapStdout() throws IOException;
}
