package org.luaj.vm2;

import com.huawei.hms.framework.common.ContainerUtils;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes6.dex */
public class LoadState {
    public static final int LUAC_FORMAT = 0;
    public static final int LUAC_HEADERSIZE = 12;
    public static final int LUAC_VERSION = 82;
    public static final int LUA_TBOOLEAN = 1;
    public static final int LUA_TFUNCTION = 6;
    public static final int LUA_TINT = -2;
    public static final int LUA_TLIGHTUSERDATA = 2;
    public static final int LUA_TNIL = 0;
    public static final int LUA_TNONE = -1;
    public static final int LUA_TNUMBER = 3;
    public static final int LUA_TSTRING = 4;
    public static final int LUA_TTABLE = 5;
    public static final int LUA_TTHREAD = 8;
    public static final int LUA_TUSERDATA = 7;
    public static final int LUA_TVALUE = 9;
    public static final int NUMBER_FORMAT_FLOATS_OR_DOUBLES = 0;
    public static final int NUMBER_FORMAT_INTS_ONLY = 1;
    public static final int NUMBER_FORMAT_NUM_PATCH_INT32 = 4;
    public static final String SOURCE_BINARY_STRING = "binary string";
    private byte[] buf = new byte[512];
    public final DataInputStream is;
    private int luacFormat;
    private boolean luacLittleEndian;
    private int luacNumberFormat;
    private int luacSizeofInstruction;
    private int luacSizeofInt;
    private int luacSizeofLuaNumber;
    private int luacSizeofSizeT;
    private int luacVersion;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    String f1042name;
    public static final Globals.Undumper instance = new GlobalsUndumper();
    public static String encoding = null;
    public static final byte[] LUA_SIGNATURE = {27, 76, 117, 97};
    public static final byte[] LUAC_TAIL = {25, Opcodes.OPC_i2s, 13, 10, 26, 10};
    private static final LuaValue[] NOVALUES = new LuaValue[0];
    private static final Prototype[] NOPROTOS = new Prototype[0];
    private static final LocVars[] NOLOCVARS = new LocVars[0];
    private static final LuaString[] NOSTRVALUES = new LuaString[0];
    private static final Upvaldesc[] NOUPVALDESCS = new Upvaldesc[0];
    private static final int[] NOINTS = new int[0];

    private static final class GlobalsUndumper implements Globals.Undumper {
        private GlobalsUndumper() {
        }

        @Override // org.luaj.vm2.Globals.Undumper
        public Prototype undump(InputStream inputStream, String str) throws IOException {
            return LoadState.undump(inputStream, str);
        }
    }

    private LoadState(InputStream inputStream, String str) {
        this.f1042name = str;
        this.is = new DataInputStream(inputStream);
    }

    public static String getSourceName(String str) {
        if (str.startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT) || str.startsWith(ContainerUtils.KEY_VALUE_DELIMITER)) {
            return str.substring(1);
        }
        return str.startsWith("\u001b") ? SOURCE_BINARY_STRING : str;
    }

    public static void install(Globals globals) {
        globals.undumper = instance;
    }

    public static LuaValue longBitsToLuaNumber(long j) {
        if ((Long.MAX_VALUE & j) == 0) {
            return LuaValue.ZERO;
        }
        int i = ((int) ((j >> 52) & 2047)) - 1023;
        if (i >= 0 && i < 31) {
            long j2 = 4503599627370495L & j;
            int i2 = 52 - i;
            if ((j2 & ((1 << i2) - 1)) == 0) {
                int i3 = (1 << i) | ((int) (j2 >> i2));
                if ((j >> 63) != 0) {
                    i3 = -i3;
                }
                return LuaInteger.valueOf(i3);
            }
        }
        return LuaValue.valueOf(Double.longBitsToDouble(j));
    }

    public static Prototype undump(InputStream inputStream, String str) throws IOException {
        int i = inputStream.read();
        byte[] bArr = LUA_SIGNATURE;
        if (i != bArr[0] || inputStream.read() != bArr[1] || inputStream.read() != bArr[2] || inputStream.read() != bArr[3]) {
            return null;
        }
        String sourceName = getSourceName(str);
        LoadState loadState = new LoadState(inputStream, sourceName);
        loadState.loadHeader();
        int i2 = loadState.luacNumberFormat;
        if (i2 == 0 || i2 == 1 || i2 == 4) {
            return loadState.loadFunction(LuaString.valueOf(sourceName));
        }
        throw new LuaError("unsupported int size");
    }

    void loadConstants(Prototype prototype) throws IOException {
        int iLoadInt = loadInt();
        LuaValue[] luaValueArr = iLoadInt > 0 ? new LuaValue[iLoadInt] : NOVALUES;
        for (int i = 0; i < iLoadInt; i++) {
            byte b = this.is.readByte();
            if (b == -2) {
                luaValueArr[i] = LuaInteger.valueOf(loadInt());
            } else if (b == 0) {
                luaValueArr[i] = LuaValue.NIL;
            } else if (b == 1) {
                luaValueArr[i] = this.is.readUnsignedByte() != 0 ? LuaValue.TRUE : LuaValue.FALSE;
            } else if (b == 3) {
                luaValueArr[i] = loadNumber();
            } else {
                if (b != 4) {
                    throw new IllegalStateException("bad constant");
                }
                luaValueArr[i] = loadString();
            }
        }
        prototype.k = luaValueArr;
        int iLoadInt2 = loadInt();
        Prototype[] prototypeArr = iLoadInt2 > 0 ? new Prototype[iLoadInt2] : NOPROTOS;
        for (int i2 = 0; i2 < iLoadInt2; i2++) {
            prototypeArr[i2] = loadFunction(prototype.source);
        }
        prototype.p = prototypeArr;
    }

    void loadDebug(Prototype prototype) throws IOException {
        prototype.source = loadString();
        prototype.lineinfo = loadIntArray();
        int iLoadInt = loadInt();
        prototype.locvars = iLoadInt > 0 ? new LocVars[iLoadInt] : NOLOCVARS;
        for (int i = 0; i < iLoadInt; i++) {
            prototype.locvars[i] = new LocVars(loadString(), loadInt(), loadInt());
        }
        int iLoadInt2 = loadInt();
        for (int i2 = 0; i2 < iLoadInt2; i2++) {
            prototype.upvalues[i2].f1043name = loadString();
        }
    }

    public Prototype loadFunction(LuaString luaString) throws IOException {
        Prototype prototype = new Prototype();
        prototype.linedefined = loadInt();
        prototype.lastlinedefined = loadInt();
        prototype.numparams = this.is.readUnsignedByte();
        prototype.is_vararg = this.is.readUnsignedByte();
        prototype.maxstacksize = this.is.readUnsignedByte();
        prototype.code = loadIntArray();
        loadConstants(prototype);
        loadUpvalues(prototype);
        loadDebug(prototype);
        return prototype;
    }

    public void loadHeader() throws IOException {
        this.luacVersion = this.is.readByte();
        this.luacFormat = this.is.readByte();
        int i = 0;
        this.luacLittleEndian = this.is.readByte() != 0;
        this.luacSizeofInt = this.is.readByte();
        this.luacSizeofSizeT = this.is.readByte();
        this.luacSizeofInstruction = this.is.readByte();
        this.luacSizeofLuaNumber = this.is.readByte();
        this.luacNumberFormat = this.is.readByte();
        while (true) {
            byte[] bArr = LUAC_TAIL;
            if (i >= bArr.length) {
                return;
            }
            if (this.is.readByte() != bArr[i]) {
                throw new LuaError(new StringBuffer("Unexpeted byte in luac tail of header, index=").append(i).toString());
            }
            i++;
        }
    }

    int loadInt() throws IOException {
        this.is.readFully(this.buf, 0, 4);
        if (this.luacLittleEndian) {
            byte[] bArr = this.buf;
            return (bArr[0] & 255) | (bArr[3] << 24) | ((bArr[2] & 255) << 16) | ((bArr[1] & 255) << 8);
        }
        byte[] bArr2 = this.buf;
        return (bArr2[3] & 255) | ((bArr2[2] & 255) << 8) | (bArr2[0] << 24) | ((bArr2[1] & 255) << 16);
    }

    long loadInt64() throws IOException {
        int iLoadInt;
        int iLoadInt2;
        if (this.luacLittleEndian) {
            iLoadInt2 = loadInt();
            iLoadInt = loadInt();
        } else {
            iLoadInt = loadInt();
            iLoadInt2 = loadInt();
        }
        return (((long) iLoadInt) << 32) | (((long) iLoadInt2) & 4294967295L);
    }

    int[] loadIntArray() throws IOException {
        int i;
        byte b;
        int iLoadInt = loadInt();
        if (iLoadInt == 0) {
            return NOINTS;
        }
        int i2 = iLoadInt << 2;
        if (this.buf.length < i2) {
            this.buf = new byte[i2];
        }
        int i3 = 0;
        this.is.readFully(this.buf, 0, i2);
        int[] iArr = new int[iLoadInt];
        int i4 = 0;
        while (i3 < iLoadInt) {
            if (this.luacLittleEndian) {
                byte[] bArr = this.buf;
                i = (bArr[i4 + 3] << 24) | ((bArr[i4 + 2] & 255) << 16) | ((bArr[i4 + 1] & 255) << 8);
                b = bArr[i4];
            } else {
                byte[] bArr2 = this.buf;
                i = (bArr2[i4] << 24) | ((bArr2[i4 + 1] & 255) << 16) | ((bArr2[i4 + 2] & 255) << 8);
                b = bArr2[i4 + 3];
            }
            iArr[i3] = (b & 255) | i;
            i3++;
            i4 += 4;
        }
        return iArr;
    }

    LuaValue loadNumber() throws IOException {
        return this.luacNumberFormat == 1 ? LuaInteger.valueOf(loadInt()) : longBitsToLuaNumber(loadInt64());
    }

    LuaString loadString() throws IOException {
        int iLoadInt64 = this.luacSizeofSizeT == 8 ? (int) loadInt64() : loadInt();
        if (iLoadInt64 == 0) {
            return null;
        }
        byte[] bArr = new byte[iLoadInt64];
        this.is.readFully(bArr, 0, iLoadInt64);
        return LuaString.valueUsing(bArr, 0, iLoadInt64 - 1);
    }

    void loadUpvalues(Prototype prototype) throws IOException {
        int iLoadInt = loadInt();
        prototype.upvalues = iLoadInt > 0 ? new Upvaldesc[iLoadInt] : NOUPVALDESCS;
        for (int i = 0; i < iLoadInt; i++) {
            prototype.upvalues[i] = new Upvaldesc(null, this.is.readByte() != 0, this.is.readByte() & 255);
        }
    }
}
