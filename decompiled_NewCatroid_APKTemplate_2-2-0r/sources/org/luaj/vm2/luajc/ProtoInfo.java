package org.luaj.vm2.luajc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.Print;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.Upvaldesc;

/* JADX INFO: loaded from: classes6.dex */
public class ProtoInfo {
    public final BasicBlock[] blocklist;
    public final BasicBlock[] blocks;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final String f1060name;
    public final UpvalInfo[][] openups;
    public final VarInfo[] params;
    public final Prototype prototype;
    public final ProtoInfo[] subprotos;
    public final UpvalInfo[] upvals;
    public final VarInfo[][] vars;

    public ProtoInfo(Prototype prototype, String str) {
        this(prototype, str, null);
    }

    private ProtoInfo(Prototype prototype, String str, UpvalInfo[] upvalInfoArr) {
        this.f1060name = str;
        this.prototype = prototype;
        this.upvals = upvalInfoArr == null ? new UpvalInfo[]{new UpvalInfo(this)} : upvalInfoArr;
        this.subprotos = (prototype.p == null || prototype.p.length <= 0) ? null : new ProtoInfo[prototype.p.length];
        BasicBlock[] basicBlockArrFindBasicBlocks = BasicBlock.findBasicBlocks(prototype);
        this.blocks = basicBlockArrFindBasicBlocks;
        this.blocklist = BasicBlock.findLiveBlocks(basicBlockArrFindBasicBlocks);
        this.params = new VarInfo[prototype.maxstacksize];
        for (int i = 0; i < prototype.maxstacksize; i++) {
            this.params[i] = VarInfo.PARAM(i);
        }
        this.vars = findVariables();
        replaceTrivialPhiVariables();
        this.openups = new UpvalInfo[prototype.maxstacksize][];
        findUpvalues();
    }

    private void appendOpenUps(StringBuffer stringBuffer, int i) {
        for (int i2 = 0; i2 < this.prototype.maxstacksize; i2++) {
            VarInfo varInfo = i < 0 ? this.params[i2] : this.vars[i2][i];
            if (varInfo != null && varInfo.pc == i && varInfo.allocupvalue) {
                stringBuffer.append(new StringBuffer("    open: ").append(varInfo.upvalue).append("\n").toString());
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0057 A[PHI: r7
      0x0057: PHI (r7v9 org.luaj.vm2.LuaString) = (r7v8 org.luaj.vm2.LuaString), (r7v18 org.luaj.vm2.LuaString) binds: [B:15:0x0055, B:12:0x0046] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:20:0x0073  */
    private String[] findInnerprotoNames() {
        String str;
        LuaString luaString;
        if (this.prototype.p.length <= 0) {
            return null;
        }
        String[] strArr = new String[this.prototype.p.length];
        Hashtable hashtable = new Hashtable();
        int[] iArr = this.prototype.code;
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (Lua.GET_OPCODE(iArr[i]) == 37) {
                int iGETARG_Bx = Lua.GETARG_Bx(iArr[i]);
                int i2 = i + 1;
                int i3 = iArr[i2];
                int i4 = 1;
                switch (Lua.GET_OPCODE(i3)) {
                    case 8:
                    case 10:
                        int iGETARG_B = Lua.GETARG_B(i3);
                        if (Lua.ISK(iGETARG_B)) {
                            str = this.prototype.k[iGETARG_B & 255].tojstring();
                        } else {
                            str = null;
                        }
                        break;
                    case 9:
                        luaString = this.prototype.upvalues[Lua.GETARG_B(i3)].f1043name;
                        if (luaString != null) {
                            str = luaString.tojstring();
                        } else {
                            str = null;
                        }
                        break;
                    default:
                        luaString = this.prototype.getlocalname(Lua.GETARG_A(iArr[i]) + 1, i2);
                        if (luaString != null) {
                            str = luaString.tojstring();
                        } else {
                            str = null;
                        }
                        break;
                }
                String javaClassPart = str != null ? toJavaClassPart(str) : String.valueOf(iGETARG_Bx);
                if (hashtable.containsKey(javaClassPart)) {
                    while (true) {
                        int i5 = i4 + 1;
                        String string = new StringBuffer().append(javaClassPart).append('$').append(i4).toString();
                        if (hashtable.containsKey(string)) {
                            i4 = i5;
                        } else {
                            javaClassPart = string;
                        }
                    }
                }
                hashtable.put(javaClassPart, Boolean.TRUE);
                strArr[iGETARG_Bx] = javaClassPart;
            }
        }
        return strArr;
    }

    private UpvalInfo findOpenUp(int i, int i2) {
        UpvalInfo[][] upvalInfoArr = this.openups;
        if (upvalInfoArr[i2] == null) {
            upvalInfoArr[i2] = new UpvalInfo[this.prototype.code.length];
        }
        UpvalInfo upvalInfo = this.openups[i2][i];
        if (upvalInfo != null) {
            return upvalInfo;
        }
        UpvalInfo upvalInfo2 = new UpvalInfo(this, i, i2);
        int length = this.prototype.code.length;
        for (int i3 = 0; i3 < length; i3++) {
            VarInfo varInfo = this.vars[i2][i3];
            if (varInfo != null && varInfo.upvalue == upvalInfo2) {
                this.openups[i2][i3] = upvalInfo2;
            }
        }
        return upvalInfo2;
    }

    private void findUpvalues() {
        int[] iArr = this.prototype.code;
        int length = iArr.length;
        String[] strArrFindInnerprotoNames = findInnerprotoNames();
        for (int i = 0; i < length; i++) {
            if (Lua.GET_OPCODE(iArr[i]) == 37) {
                int iGETARG_Bx = Lua.GETARG_Bx(iArr[i]);
                Prototype prototype = this.prototype.p[iGETARG_Bx];
                UpvalInfo[] upvalInfoArr = new UpvalInfo[prototype.upvalues.length];
                String string = new StringBuffer().append(this.f1060name).append("$").append(strArrFindInnerprotoNames[iGETARG_Bx]).toString();
                for (int i2 = 0; i2 < prototype.upvalues.length; i2++) {
                    Upvaldesc upvaldesc = prototype.upvalues[i2];
                    upvalInfoArr[i2] = upvaldesc.instack ? findOpenUp(i, upvaldesc.idx) : this.upvals[upvaldesc.idx];
                }
                this.subprotos[iGETARG_Bx] = new ProtoInfo(prototype, string, upvalInfoArr);
            }
        }
        for (int i3 = 0; i3 < length; i3++) {
            if (Lua.GET_OPCODE(iArr[i3]) == 9) {
                this.upvals[Lua.GETARG_B(iArr[i3])].rw = true;
            }
        }
    }

    private VarInfo[][] findVariables() {
        VarInfo varInfo;
        VarInfo varInfoPHI;
        int length = this.prototype.code.length;
        int i = this.prototype.maxstacksize;
        VarInfo[][] varInfoArr = new VarInfo[i][];
        for (int i2 = 0; i2 < i; i2++) {
            varInfoArr[i2] = new VarInfo[length];
        }
        int i3 = 0;
        while (true) {
            BasicBlock[] basicBlockArr = this.blocklist;
            if (i3 >= basicBlockArr.length) {
                return varInfoArr;
            }
            BasicBlock basicBlock = basicBlockArr[i3];
            int length2 = basicBlock.prev != null ? basicBlock.prev.length : 0;
            for (int i4 = 0; i4 < i; i4++) {
                if (length2 == 0) {
                    varInfoPHI = this.params[i4];
                } else if (length2 == 1) {
                    varInfoPHI = varInfoArr[i4][basicBlock.prev[0].pc1];
                } else {
                    varInfoPHI = null;
                    for (int i5 = 0; i5 < length2; i5++) {
                        if (varInfoArr[i4][basicBlock.prev[i5].pc1] == VarInfo.INVALID) {
                            varInfoPHI = VarInfo.INVALID;
                        }
                    }
                }
                if (varInfoPHI == null) {
                    varInfoPHI = VarInfo.PHI(this, i4, basicBlock.pc0);
                }
                varInfoArr[i4][basicBlock.pc0] = varInfoPHI;
            }
            for (int i6 = basicBlock.pc0; i6 <= basicBlock.pc1; i6++) {
                if (i6 > basicBlock.pc0) {
                    propogateVars(varInfoArr, i6 - 1, i6);
                }
                int i7 = this.prototype.code[i6];
                switch (Lua.GET_OPCODE(i7)) {
                    case 0:
                    case 19:
                    case 20:
                    case 21:
                    case 28:
                        int iGETARG_A = Lua.GETARG_A(i7);
                        varInfoArr[Lua.GETARG_B(i7)][i6].isreferenced = true;
                        varInfoArr[iGETARG_A][i6] = new VarInfo(iGETARG_A, i6);
                        break;
                    case 1:
                    case 3:
                    case 5:
                    case 11:
                        int iGETARG_A2 = Lua.GETARG_A(i7);
                        varInfoArr[iGETARG_A2][i6] = new VarInfo(iGETARG_A2, i6);
                        break;
                    case 2:
                    default:
                        throw new IllegalStateException(new StringBuffer("unhandled opcode: ").append(i7).toString());
                    case 4:
                        int iGETARG_A3 = Lua.GETARG_A(i7);
                        int iGETARG_B = Lua.GETARG_B(i7);
                        while (true) {
                            int i8 = iGETARG_B - 1;
                            if (iGETARG_B >= 0) {
                                varInfoArr[iGETARG_A3][i6] = new VarInfo(iGETARG_A3, i6);
                                iGETARG_A3++;
                                iGETARG_B = i8;
                            }
                        }
                        break;
                    case 6:
                        int iGETARG_A4 = Lua.GETARG_A(i7);
                        int iGETARG_C = Lua.GETARG_C(i7);
                        if (!Lua.ISK(iGETARG_C)) {
                            varInfoArr[iGETARG_C][i6].isreferenced = true;
                        }
                        varInfoArr[iGETARG_A4][i6] = new VarInfo(iGETARG_A4, i6);
                        break;
                    case 7:
                        int iGETARG_A5 = Lua.GETARG_A(i7);
                        int iGETARG_B2 = Lua.GETARG_B(i7);
                        int iGETARG_C2 = Lua.GETARG_C(i7);
                        varInfoArr[iGETARG_B2][i6].isreferenced = true;
                        if (!Lua.ISK(iGETARG_C2)) {
                            varInfoArr[iGETARG_C2][i6].isreferenced = true;
                        }
                        varInfoArr[iGETARG_A5][i6] = new VarInfo(iGETARG_A5, i6);
                        break;
                    case 8:
                        int iGETARG_B3 = Lua.GETARG_B(i7);
                        int iGETARG_C3 = Lua.GETARG_C(i7);
                        if (!Lua.ISK(iGETARG_B3)) {
                            varInfoArr[iGETARG_B3][i6].isreferenced = true;
                        }
                        if (!Lua.ISK(iGETARG_C3)) {
                            varInfo = varInfoArr[iGETARG_C3][i6];
                            varInfo.isreferenced = true;
                        }
                        break;
                    case 9:
                    case 27:
                        varInfo = varInfoArr[Lua.GETARG_A(i7)][i6];
                        varInfo.isreferenced = true;
                        break;
                    case 10:
                        int iGETARG_A6 = Lua.GETARG_A(i7);
                        int iGETARG_B4 = Lua.GETARG_B(i7);
                        int iGETARG_C4 = Lua.GETARG_C(i7);
                        varInfoArr[iGETARG_A6][i6].isreferenced = true;
                        if (!Lua.ISK(iGETARG_B4)) {
                            varInfoArr[iGETARG_B4][i6].isreferenced = true;
                        }
                        if (!Lua.ISK(iGETARG_C4)) {
                            varInfo = varInfoArr[iGETARG_C4][i6];
                            varInfo.isreferenced = true;
                        }
                        break;
                    case 12:
                        int iGETARG_A7 = Lua.GETARG_A(i7);
                        int iGETARG_B5 = Lua.GETARG_B(i7);
                        int iGETARG_C5 = Lua.GETARG_C(i7);
                        varInfoArr[iGETARG_B5][i6].isreferenced = true;
                        if (!Lua.ISK(iGETARG_C5)) {
                            varInfoArr[iGETARG_C5][i6].isreferenced = true;
                        }
                        varInfoArr[iGETARG_A7][i6] = new VarInfo(iGETARG_A7, i6);
                        int i9 = iGETARG_A7 + 1;
                        varInfoArr[i9][i6] = new VarInfo(i9, i6);
                        break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        int iGETARG_A8 = Lua.GETARG_A(i7);
                        int iGETARG_B6 = Lua.GETARG_B(i7);
                        int iGETARG_C6 = Lua.GETARG_C(i7);
                        if (!Lua.ISK(iGETARG_B6)) {
                            varInfoArr[iGETARG_B6][i6].isreferenced = true;
                        }
                        if (!Lua.ISK(iGETARG_C6)) {
                            varInfoArr[iGETARG_C6][i6].isreferenced = true;
                        }
                        varInfoArr[iGETARG_A8][i6] = new VarInfo(iGETARG_A8, i6);
                        break;
                    case 22:
                        int iGETARG_A9 = Lua.GETARG_A(i7);
                        int iGETARG_C7 = Lua.GETARG_C(i7);
                        for (int iGETARG_B7 = Lua.GETARG_B(i7); iGETARG_B7 <= iGETARG_C7; iGETARG_B7++) {
                            varInfoArr[iGETARG_B7][i6].isreferenced = true;
                        }
                        varInfoArr[iGETARG_A9][i6] = new VarInfo(iGETARG_A9, i6);
                        break;
                    case 23:
                        int iGETARG_A10 = Lua.GETARG_A(i7);
                        if (iGETARG_A10 > 0) {
                            for (int i10 = iGETARG_A10 - 1; i10 < i; i10++) {
                                varInfoArr[i10][i6] = VarInfo.INVALID;
                            }
                        }
                        break;
                    case 24:
                    case 25:
                    case 26:
                        int iGETARG_B8 = Lua.GETARG_B(i7);
                        int iGETARG_C8 = Lua.GETARG_C(i7);
                        if (!Lua.ISK(iGETARG_B8)) {
                            varInfoArr[iGETARG_B8][i6].isreferenced = true;
                        }
                        if (!Lua.ISK(iGETARG_C8)) {
                            varInfo = varInfoArr[iGETARG_C8][i6];
                            varInfo.isreferenced = true;
                        }
                        break;
                    case 29:
                        int iGETARG_A11 = Lua.GETARG_A(i7);
                        int iGETARG_B9 = Lua.GETARG_B(i7);
                        int iGETARG_C9 = Lua.GETARG_C(i7);
                        varInfoArr[iGETARG_A11][i6].isreferenced = true;
                        varInfoArr[iGETARG_A11][i6].isreferenced = true;
                        for (int i11 = 1; i11 <= iGETARG_B9 - 1; i11++) {
                            varInfoArr[iGETARG_A11 + i11][i6].isreferenced = true;
                        }
                        int i12 = 0;
                        while (i12 <= iGETARG_C9 - 2) {
                            varInfoArr[iGETARG_A11][i6] = new VarInfo(iGETARG_A11, i6);
                            i12++;
                            iGETARG_A11++;
                        }
                        while (iGETARG_A11 < i) {
                            varInfoArr[iGETARG_A11][i6] = VarInfo.INVALID;
                            iGETARG_A11++;
                        }
                        break;
                    case 30:
                        int iGETARG_A12 = Lua.GETARG_A(i7);
                        int iGETARG_B10 = Lua.GETARG_B(i7);
                        varInfoArr[iGETARG_A12][i6].isreferenced = true;
                        for (int i13 = 1; i13 <= iGETARG_B10 - 1; i13++) {
                            varInfoArr[iGETARG_A12 + i13][i6].isreferenced = true;
                        }
                        break;
                    case 31:
                        int iGETARG_A13 = Lua.GETARG_A(i7);
                        int iGETARG_B11 = Lua.GETARG_B(i7);
                        for (int i14 = 0; i14 <= iGETARG_B11 - 2; i14++) {
                            varInfoArr[iGETARG_A13 + i14][i6].isreferenced = true;
                        }
                        break;
                    case 32:
                        int iGETARG_A14 = Lua.GETARG_A(i7);
                        varInfoArr[iGETARG_A14][i6].isreferenced = true;
                        varInfoArr[iGETARG_A14 + 2][i6].isreferenced = true;
                        varInfoArr[iGETARG_A14][i6] = new VarInfo(iGETARG_A14, i6);
                        varInfoArr[iGETARG_A14][i6].isreferenced = true;
                        varInfoArr[iGETARG_A14 + 1][i6].isreferenced = true;
                        int i15 = iGETARG_A14 + 3;
                        varInfoArr[i15][i6] = new VarInfo(i15, i6);
                        break;
                    case 33:
                        int iGETARG_A15 = Lua.GETARG_A(i7);
                        varInfoArr[iGETARG_A15 + 2][i6].isreferenced = true;
                        varInfoArr[iGETARG_A15][i6] = new VarInfo(iGETARG_A15, i6);
                        break;
                    case 34:
                        int iGETARG_A16 = Lua.GETARG_A(i7);
                        int iGETARG_C10 = Lua.GETARG_C(i7);
                        varInfoArr[iGETARG_A16][i6].isreferenced = true;
                        int i16 = iGETARG_A16 + 2;
                        varInfoArr[iGETARG_A16 + 1][i6].isreferenced = true;
                        int i17 = iGETARG_A16 + 3;
                        varInfoArr[i16][i6].isreferenced = true;
                        int i18 = 0;
                        while (i18 < iGETARG_C10) {
                            varInfoArr[i17][i6] = new VarInfo(i17, i6);
                            i18++;
                            i17++;
                        }
                        while (i17 < i) {
                            varInfoArr[i17][i6] = VarInfo.INVALID;
                            i17++;
                        }
                        break;
                    case 35:
                        int iGETARG_A17 = Lua.GETARG_A(i7);
                        varInfoArr[iGETARG_A17 + 1][i6].isreferenced = true;
                        varInfoArr[iGETARG_A17][i6] = new VarInfo(iGETARG_A17, i6);
                        break;
                    case 36:
                        int iGETARG_A18 = Lua.GETARG_A(i7);
                        int iGETARG_B12 = Lua.GETARG_B(i7);
                        varInfoArr[iGETARG_A18][i6].isreferenced = true;
                        for (int i19 = 1; i19 <= iGETARG_B12; i19++) {
                            varInfoArr[iGETARG_A18 + i19][i6].isreferenced = true;
                        }
                        break;
                    case 37:
                        int iGETARG_A19 = Lua.GETARG_A(i7);
                        Upvaldesc[] upvaldescArr = this.prototype.p[Lua.GETARG_Bx(i7)].upvalues;
                        int length3 = upvaldescArr.length;
                        for (int i20 = 0; i20 < length3; i20++) {
                            if (upvaldescArr[i20].instack) {
                                varInfoArr[upvaldescArr[i20].idx][i6].isreferenced = true;
                            }
                        }
                        varInfoArr[iGETARG_A19][i6] = new VarInfo(iGETARG_A19, i6);
                        break;
                    case 38:
                        int iGETARG_A20 = Lua.GETARG_A(i7);
                        int iGETARG_B13 = Lua.GETARG_B(i7);
                        int i21 = 1;
                        while (i21 < iGETARG_B13) {
                            varInfoArr[iGETARG_A20][i6] = new VarInfo(iGETARG_A20, i6);
                            i21++;
                            iGETARG_A20++;
                        }
                        if (iGETARG_B13 == 0) {
                            while (iGETARG_A20 < i) {
                                varInfoArr[iGETARG_A20][i6] = VarInfo.INVALID;
                                iGETARG_A20++;
                            }
                        }
                        break;
                }
            }
            i3++;
        }
    }

    private static void propogateVars(VarInfo[][] varInfoArr, int i, int i2) {
        for (VarInfo[] varInfoArr2 : varInfoArr) {
            varInfoArr2[i2] = varInfoArr2[i];
        }
    }

    private void replaceAll(VarInfo[] varInfoArr, int i, VarInfo varInfo, VarInfo varInfo2) {
        for (int i2 = 0; i2 < i; i2++) {
            if (varInfoArr[i2] == varInfo) {
                varInfoArr[i2] = varInfo2;
            }
        }
    }

    private void replaceTrivialPhiVariables() {
        int i = 0;
        while (true) {
            BasicBlock[] basicBlockArr = this.blocklist;
            if (i >= basicBlockArr.length) {
                return;
            }
            BasicBlock basicBlock = basicBlockArr[i];
            for (int i2 = 0; i2 < this.prototype.maxstacksize; i2++) {
                VarInfo varInfo = this.vars[i2][basicBlock.pc0];
                VarInfo varInfoResolvePhiVariableValues = varInfo.resolvePhiVariableValues();
                if (varInfoResolvePhiVariableValues != null) {
                    substituteVariable(i2, varInfo, varInfoResolvePhiVariableValues);
                }
            }
            i++;
        }
    }

    private void substituteVariable(int i, VarInfo varInfo, VarInfo varInfo2) {
        int length = this.prototype.code.length;
        for (int i2 = 0; i2 < length; i2++) {
            VarInfo[] varInfoArr = this.vars[i];
            replaceAll(varInfoArr, varInfoArr.length, varInfo, varInfo2);
        }
    }

    private static String toJavaClassPart(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            stringBuffer.append(Character.isJavaIdentifierPart(str.charAt(i)) ? str.charAt(i) : '_');
        }
        return stringBuffer.toString();
    }

    public boolean isInitialValueUsed(int i) {
        return this.params[i].isreferenced;
    }

    public boolean isReadWriteUpvalue(UpvalInfo upvalInfo) {
        return upvalInfo.rw;
    }

    public boolean isUpvalueAssign(int i, int i2) {
        VarInfo varInfo = i < 0 ? this.params[i2] : this.vars[i2][i];
        return (varInfo == null || varInfo.upvalue == null || !varInfo.upvalue.rw) ? false : true;
    }

    public boolean isUpvalueCreate(int i, int i2) {
        VarInfo varInfo = i < 0 ? this.params[i2] : this.vars[i2][i];
        return varInfo != null && varInfo.upvalue != null && varInfo.upvalue.rw && varInfo.allocupvalue && i == varInfo.pc;
    }

    public boolean isUpvalueRefer(int i, int i2) {
        VarInfo varInfo;
        if (i > 0 && (varInfo = this.vars[i2][i]) != null && varInfo.pc == i && this.vars[i2][i - 1] != null) {
            i--;
        }
        VarInfo varInfo2 = i < 0 ? this.params[i2] : this.vars[i2][i];
        return (varInfo2 == null || varInfo2.upvalue == null || !varInfo2.upvalue.rw) ? false : true;
    }

    public String toString() {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new StringBuffer("proto '").append(this.f1060name).append("'\n").toString());
        UpvalInfo[] upvalInfoArr = this.upvals;
        int length = upvalInfoArr != null ? upvalInfoArr.length : 0;
        for (int i = 0; i < length; i++) {
            stringBuffer.append(new StringBuffer(" up[").append(i).append("]: ").append(this.upvals[i]).append("\n").toString());
        }
        int i2 = 0;
        while (true) {
            BasicBlock[] basicBlockArr = this.blocklist;
            if (i2 >= basicBlockArr.length) {
                break;
            }
            BasicBlock basicBlock = basicBlockArr[i2];
            int i3 = basicBlock.pc0;
            stringBuffer.append(new StringBuffer("  block ").append(basicBlock.toString()).toString());
            appendOpenUps(stringBuffer, -1);
            while (i3 <= basicBlock.pc1) {
                appendOpenUps(stringBuffer, i3);
                stringBuffer.append("     ");
                for (int i4 = 0; i4 < this.prototype.maxstacksize; i4++) {
                    VarInfo varInfo = this.vars[i4][i3];
                    if (varInfo == null) {
                        str = "";
                    } else if (varInfo.upvalue == null) {
                        str = "    ";
                    } else if (varInfo.upvalue.rw) {
                        str = (varInfo.allocupvalue && varInfo.pc == i3) ? "[*] " : "[]  ";
                    } else {
                        str = "[C] ";
                    }
                    stringBuffer.append(new StringBuffer().append(varInfo == null ? "null   " : String.valueOf(varInfo)).append(str).toString());
                }
                stringBuffer.append("  ");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = Print.ps;
                Print.ps = new PrintStream(byteArrayOutputStream);
                try {
                    Print.printOpCode(this.prototype, i3);
                    Print.ps.close();
                    Print.ps = printStream;
                    stringBuffer.append(byteArrayOutputStream.toString());
                    stringBuffer.append("\n");
                    i3++;
                } catch (Throwable th) {
                    Print.ps.close();
                    Print.ps = printStream;
                    throw th;
                }
            }
            i2++;
        }
        ProtoInfo[] protoInfoArr = this.subprotos;
        int length2 = protoInfoArr != null ? protoInfoArr.length : 0;
        for (int i5 = 0; i5 < length2; i5++) {
            stringBuffer.append(this.subprotos[i5].toString());
        }
        return stringBuffer.toString();
    }
}
