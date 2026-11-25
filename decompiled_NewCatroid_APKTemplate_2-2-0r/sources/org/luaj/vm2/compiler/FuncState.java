package org.luaj.vm2.compiler;

import java.util.Hashtable;
import org.luaj.vm2.LocVars;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.Upvaldesc;

/* JADX INFO: loaded from: classes6.dex */
public class FuncState extends Constants {
    LuaC.CompileState L;
    BlockCnt bl;
    Prototype f;
    int firstlocal;
    short freereg;
    Hashtable h;
    IntPtr jpc;
    int lasttarget;
    LexState ls;
    short nactvar;
    int nk;
    short nlocvars;
    int np;
    short nups;
    int pc;
    FuncState prev;

    static class BlockCnt {
        short firstgoto;
        short firstlabel;
        boolean isloop;
        short nactvar;
        BlockCnt previous;
        boolean upval;

        BlockCnt() {
        }
    }

    FuncState() {
    }

    static int singlevaraux(FuncState funcState, LuaString luaString, LexState.expdesc expdescVar, int i) {
        if (funcState == null) {
            return 0;
        }
        int iSearchvar = funcState.searchvar(luaString);
        if (iSearchvar >= 0) {
            expdescVar.init(7, iSearchvar);
            if (i == 0) {
                funcState.markupval(iSearchvar);
            }
            return 7;
        }
        int iSearchupvalue = funcState.searchupvalue(luaString);
        if (iSearchupvalue < 0) {
            if (singlevaraux(funcState.prev, luaString, expdescVar, 0) == 0) {
                return 0;
            }
            iSearchupvalue = funcState.newupvalue(luaString, expdescVar);
        }
        expdescVar.init(8, iSearchupvalue);
        return 8;
    }

    static boolean vkisinreg(int i) {
        return i == 6 || i == 7;
    }

    int addk(LuaValue luaValue) {
        Hashtable hashtable = this.h;
        if (hashtable == null) {
            this.h = new Hashtable();
        } else if (hashtable.containsKey(luaValue)) {
            return ((Integer) this.h.get(luaValue)).intValue();
        }
        int i = this.nk;
        this.h.put(luaValue, new Integer(i));
        Prototype prototype = this.f;
        if (prototype.k == null || this.nk + 1 >= prototype.k.length) {
            prototype.k = realloc(prototype.k, (this.nk * 2) + 1);
        }
        LuaValue[] luaValueArr = prototype.k;
        int i2 = this.nk;
        this.nk = i2 + 1;
        luaValueArr[i2] = luaValue;
        return i;
    }

    int boolK(boolean z) {
        return addk(z ? LuaValue.TRUE : LuaValue.FALSE);
    }

    void checklimit(int i, int i2, String str) {
        if (i > i2) {
            errorlimit(i2, str);
        }
    }

    void checkrepeated(LexState.Labeldesc[] labeldescArr, int i, LuaString luaString) {
        for (int i2 = this.bl.firstlabel; i2 < i; i2++) {
            if (luaString.eq_b(labeldescArr[i2].f1056name)) {
                this.ls.semerror(this.ls.L.pushfstring(new StringBuffer("label '").append(luaString).append(" already defined on line ").append(labeldescArr[i2].line).toString()));
            }
        }
    }

    void checkstack(int i) {
        int i2 = this.freereg + i;
        if (i2 > this.f.maxstacksize) {
            if (i2 >= 250) {
                this.ls.syntaxerror("function or expression too complex");
            }
            this.f.maxstacksize = i2;
        }
    }

    void closelistfield(LexState.ConsControl consControl) {
        if (consControl.v.k == 0) {
            return;
        }
        exp2nextreg(consControl.v);
        consControl.v.k = 0;
        if (consControl.tostore == 50) {
            setlist(consControl.t.u.info, consControl.na, consControl.tostore);
            consControl.tostore = 0;
        }
    }

    int code(int i, int i2) {
        Prototype prototype = this.f;
        dischargejpc();
        if (prototype.code == null || this.pc + 1 > prototype.code.length) {
            prototype.code = LuaC.realloc(prototype.code, (this.pc * 2) + 1);
        }
        prototype.code[this.pc] = i;
        if (prototype.lineinfo == null || this.pc + 1 > prototype.lineinfo.length) {
            prototype.lineinfo = LuaC.realloc(prototype.lineinfo, (this.pc * 2) + 1);
        }
        int[] iArr = prototype.lineinfo;
        int i3 = this.pc;
        iArr[i3] = i2;
        this.pc = i3 + 1;
        return i3;
    }

    int codeABC(int i, int i2, int i3, int i4) {
        boolean z = true;
        _assert(getOpMode(i) == 0);
        _assert(getBMode(i) != 0 || i3 == 0);
        if (getCMode(i) == 0 && i4 != 0) {
            z = false;
        }
        _assert(z);
        return code(CREATE_ABC(i, i2, i3, i4), this.ls.lastline);
    }

    int codeABx(int i, int i2, int i3) {
        boolean z = false;
        _assert(getOpMode(i) == 1 || getOpMode(i) == 2);
        _assert(getCMode(i) == 0);
        if (i3 >= 0 && i3 <= 262143) {
            z = true;
        }
        _assert(z);
        return code(CREATE_ABx(i, i2, i3), this.ls.lastline);
    }

    int codeAsBx(int i, int i2, int i3) {
        return codeABx(i, i2, i3 + Lua.MAXARG_sBx);
    }

    int code_label(int i, int i2, int i3) {
        getlabel();
        return codeABC(3, i, i2, i3);
    }

    void codearith(int i, LexState.expdesc expdescVar, LexState.expdesc expdescVar2, int i2) {
        if (constfolding(i, expdescVar, expdescVar2)) {
            return;
        }
        int iExp2RK = (i == 19 || i == 21) ? 0 : exp2RK(expdescVar2);
        int iExp2RK2 = exp2RK(expdescVar);
        if (iExp2RK2 > iExp2RK) {
            freeexp(expdescVar);
            freeexp(expdescVar2);
        } else {
            freeexp(expdescVar2);
            freeexp(expdescVar);
        }
        expdescVar.u.info = codeABC(i, 0, iExp2RK2, iExp2RK);
        expdescVar.k = 11;
        fixline(i2);
    }

    void codecomp(int i, int i2, LexState.expdesc expdescVar, LexState.expdesc expdescVar2) {
        int iExp2RK = exp2RK(expdescVar);
        int iExp2RK2 = exp2RK(expdescVar2);
        freeexp(expdescVar2);
        freeexp(expdescVar);
        if (i2 == 0 && i != 24) {
            i2 = 1;
            iExp2RK2 = iExp2RK;
            iExp2RK = iExp2RK2;
        }
        expdescVar.u.info = condjump(i, i2, iExp2RK, iExp2RK2);
        expdescVar.k = 10;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001b  */
    void codenot(LexState.expdesc expdescVar) {
        int i;
        dischargevars(expdescVar);
        int i2 = expdescVar.k;
        if (i2 == 10) {
            invertjump(expdescVar);
        } else if (i2 != 11) {
            switch (i2) {
                case 1:
                case 3:
                    i = 2;
                    expdescVar.k = i;
                    break;
                case 2:
                case 4:
                case 5:
                    i = 3;
                    expdescVar.k = i;
                    break;
                case 6:
                    discharge2anyreg(expdescVar);
                    freeexp(expdescVar);
                    expdescVar.u.info = codeABC(20, 0, expdescVar.u.info, 0);
                    expdescVar.k = 11;
                    break;
                default:
                    _assert(false);
                    break;
            }
        } else {
            discharge2anyreg(expdescVar);
            freeexp(expdescVar);
            expdescVar.u.info = codeABC(20, 0, expdescVar.u.info, 0);
            expdescVar.k = 11;
        }
        int i3 = expdescVar.f.i;
        expdescVar.f.i = expdescVar.t.i;
        expdescVar.t.i = i3;
        removevalues(expdescVar.f.i);
        removevalues(expdescVar.t.i);
    }

    void concat(IntPtr intPtr, int i) {
        if (i == -1) {
            return;
        }
        if (intPtr.i == -1) {
            intPtr.i = i;
            return;
        }
        int i2 = intPtr.i;
        while (true) {
            int i3 = getjump(i2);
            if (i3 == -1) {
                fixjump(i2, i);
                return;
            }
            i2 = i3;
        }
    }

    int condjump(int i, int i2, int i3, int i4) {
        codeABC(i, i2, i3, i4);
        return jump();
    }

    boolean constfolding(int i, LexState.expdesc expdescVar, LexState.expdesc expdescVar2) {
        LuaValue luaValueAdd;
        if (!expdescVar.isnumeral() || !expdescVar2.isnumeral()) {
            return false;
        }
        if ((i == 16 || i == 17) && expdescVar2.u.nval().eq_b(LuaValue.ZERO)) {
            return false;
        }
        LuaValue luaValueNval = expdescVar.u.nval();
        LuaValue luaValueNval2 = expdescVar2.u.nval();
        switch (i) {
            case 13:
                luaValueAdd = luaValueNval.add(luaValueNval2);
                break;
            case 14:
                luaValueAdd = luaValueNval.sub(luaValueNval2);
                break;
            case 15:
                luaValueAdd = luaValueNval.mul(luaValueNval2);
                break;
            case 16:
                luaValueAdd = luaValueNval.div(luaValueNval2);
                break;
            case 17:
                luaValueAdd = luaValueNval.mod(luaValueNval2);
                break;
            case 18:
                luaValueAdd = luaValueNval.pow(luaValueNval2);
                break;
            case 19:
                luaValueAdd = luaValueNval.neg();
                break;
            case 20:
            default:
                _assert(false);
                luaValueAdd = null;
                break;
            case 21:
                return false;
        }
        if (Double.isNaN(luaValueAdd.todouble())) {
            return false;
        }
        expdescVar.u.setNval(luaValueAdd);
        return true;
    }

    void discharge2anyreg(LexState.expdesc expdescVar) {
        if (expdescVar.k != 6) {
            reserveregs(1);
            discharge2reg(expdescVar, this.freereg - 1);
        }
    }

    void discharge2reg(LexState.expdesc expdescVar, int i) {
        int iNumberK;
        dischargevars(expdescVar);
        int i2 = expdescVar.k;
        if (i2 != 11) {
            switch (i2) {
                case 1:
                    nil(i, 1);
                    break;
                case 2:
                case 3:
                    codeABC(3, i, expdescVar.k != 2 ? 0 : 1, 0);
                    break;
                case 4:
                    iNumberK = expdescVar.u.info;
                    codeABx(1, i, iNumberK);
                    break;
                case 5:
                    iNumberK = numberK(expdescVar.u.nval());
                    codeABx(1, i, iNumberK);
                    break;
                case 6:
                    if (i != expdescVar.u.info) {
                        codeABC(0, i, expdescVar.u.info, 0);
                    }
                    break;
                default:
                    _assert(expdescVar.k == 0 || expdescVar.k == 10);
                    break;
            }
            return;
        }
        SETARG_A(getcodePtr(expdescVar), i);
        expdescVar.u.info = i;
        expdescVar.k = 6;
    }

    void dischargejpc() {
        int i = this.jpc.i;
        int i2 = this.pc;
        patchlistaux(i, i2, 255, i2);
        this.jpc.i = -1;
    }

    void dischargevars(LexState.expdesc expdescVar) {
        LexState.expdesc.U u;
        int iCodeABC;
        int i = expdescVar.k;
        int i2 = 6;
        if (i == 7) {
            expdescVar.k = 6;
            return;
        }
        if (i == 8) {
            u = expdescVar.u;
            iCodeABC = codeABC(5, 0, expdescVar.u.info, 0);
        } else {
            if (i != 9) {
                if (i == 12 || i == 13) {
                    setoneret(expdescVar);
                    return;
                }
                return;
            }
            freereg(expdescVar.u.ind_idx);
            if (expdescVar.u.ind_vt == 7) {
                freereg(expdescVar.u.ind_t);
                i2 = 7;
            }
            u = expdescVar.u;
            iCodeABC = codeABC(i2, 0, expdescVar.u.ind_t, expdescVar.u.ind_idx);
        }
        u.info = iCodeABC;
        expdescVar.k = 11;
    }

    void enterblock(BlockCnt blockCnt, boolean z) {
        blockCnt.isloop = z;
        blockCnt.nactvar = this.nactvar;
        blockCnt.firstlabel = (short) this.ls.dyd.n_label;
        blockCnt.firstgoto = (short) this.ls.dyd.n_gt;
        blockCnt.upval = false;
        blockCnt.previous = this.bl;
        this.bl = blockCnt;
        _assert(this.freereg == this.nactvar);
    }

    void errorlimit(int i, String str) {
        LuaC.CompileState compileState;
        StringBuffer stringBufferAppend;
        if (this.f.linedefined == 0) {
            compileState = this.L;
            stringBufferAppend = new StringBuffer("main function has more than ");
        } else {
            compileState = this.L;
            stringBufferAppend = new StringBuffer("function at line ").append(this.f.linedefined).append(" has more than ");
        }
        this.ls.lexerror(compileState.pushfstring(stringBufferAppend.append(i).append(" ").append(str).toString()), 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002b, code lost:
    
        if (r7.u.info <= 255) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    int exp2RK(org.luaj.vm2.compiler.LexState.expdesc r7) {
        /*
            r6 = this;
            r6.exp2val(r7)
            int r0 = r7.k
            r1 = 255(0xff, float:3.57E-43)
            r2 = 2
            r3 = 4
            r4 = 1
            if (r0 == r4) goto L36
            if (r0 == r2) goto L36
            r5 = 3
            if (r0 == r5) goto L36
            if (r0 == r3) goto L27
            r2 = 5
            if (r0 == r2) goto L17
            goto L54
        L17:
            org.luaj.vm2.compiler.LexState$expdesc$U r0 = r7.u
            org.luaj.vm2.compiler.LexState$expdesc$U r2 = r7.u
            org.luaj.vm2.LuaValue r2 = r2.nval()
            int r2 = r6.numberK(r2)
            r0.info = r2
            r7.k = r3
        L27:
            org.luaj.vm2.compiler.LexState$expdesc$U r0 = r7.u
            int r0 = r0.info
            if (r0 > r1) goto L54
        L2d:
            org.luaj.vm2.compiler.LexState$expdesc$U r7 = r7.u
            int r7 = r7.info
            int r7 = RKASK(r7)
            return r7
        L36:
            int r0 = r6.nk
            if (r0 > r1) goto L54
            org.luaj.vm2.compiler.LexState$expdesc$U r0 = r7.u
            int r1 = r7.k
            if (r1 != r4) goto L45
            int r1 = r6.nilK()
            goto L4f
        L45:
            int r1 = r7.k
            if (r1 != r2) goto L4a
            goto L4b
        L4a:
            r4 = 0
        L4b:
            int r1 = r6.boolK(r4)
        L4f:
            r0.info = r1
            r7.k = r3
            goto L2d
        L54:
            int r7 = r6.exp2anyreg(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.luaj.vm2.compiler.FuncState.exp2RK(org.luaj.vm2.compiler.LexState$expdesc):int");
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0023  */
    int exp2anyreg(LexState.expdesc expdescVar) {
        dischargevars(expdescVar);
        if (expdescVar.k != 6) {
            exp2nextreg(expdescVar);
        } else if (expdescVar.hasjumps()) {
            if (expdescVar.u.info >= this.nactvar) {
                exp2reg(expdescVar, expdescVar.u.info);
            } else {
                exp2nextreg(expdescVar);
            }
        }
        return expdescVar.u.info;
    }

    void exp2anyregup(LexState.expdesc expdescVar) {
        if (expdescVar.k != 8 || expdescVar.hasjumps()) {
            exp2anyreg(expdescVar);
        }
    }

    void exp2nextreg(LexState.expdesc expdescVar) {
        dischargevars(expdescVar);
        freeexp(expdescVar);
        reserveregs(1);
        exp2reg(expdescVar, this.freereg - 1);
    }

    void exp2reg(LexState.expdesc expdescVar, int i) {
        int iCode_label;
        int iCode_label2;
        discharge2reg(expdescVar, i);
        if (expdescVar.k == 10) {
            concat(expdescVar.t, expdescVar.u.info);
        }
        if (expdescVar.hasjumps()) {
            if (need_value(expdescVar.t.i) || need_value(expdescVar.f.i)) {
                int iJump = expdescVar.k == 10 ? -1 : jump();
                iCode_label = code_label(i, 0, 1);
                iCode_label2 = code_label(i, 1, 0);
                patchtohere(iJump);
            } else {
                iCode_label2 = -1;
                iCode_label = -1;
            }
            int i2 = getlabel();
            patchlistaux(expdescVar.f.i, i2, i, iCode_label);
            patchlistaux(expdescVar.t.i, i2, i, iCode_label2);
        }
        IntPtr intPtr = expdescVar.f;
        expdescVar.t.i = -1;
        intPtr.i = -1;
        expdescVar.u.info = i;
        expdescVar.k = 6;
    }

    void exp2val(LexState.expdesc expdescVar) {
        if (expdescVar.hasjumps()) {
            exp2anyreg(expdescVar);
        } else {
            dischargevars(expdescVar);
        }
    }

    void fixjump(int i, int i2) {
        InstructionPtr instructionPtr = new InstructionPtr(this.f.code, i);
        int i3 = i2 - (i + 1);
        _assert(i2 != -1);
        if (Math.abs(i3) > 131071) {
            this.ls.syntaxerror("control structure too long");
        }
        SETARG_sBx(instructionPtr, i3);
    }

    void fixline(int i) {
        this.f.lineinfo[this.pc - 1] = i;
    }

    void freeexp(LexState.expdesc expdescVar) {
        if (expdescVar.k == 6) {
            freereg(expdescVar.u.info);
        }
    }

    void freereg(int i) {
        if (ISK(i) || i < this.nactvar) {
            return;
        }
        short s = (short) (this.freereg - 1);
        this.freereg = s;
        _assert(i == s);
    }

    int getcode(LexState.expdesc expdescVar) {
        return this.f.code[expdescVar.u.info];
    }

    InstructionPtr getcodePtr(LexState.expdesc expdescVar) {
        return new InstructionPtr(this.f.code, expdescVar.u.info);
    }

    int getjump(int i) {
        int iGETARG_sBx = GETARG_sBx(this.f.code[i]);
        if (iGETARG_sBx == -1) {
            return -1;
        }
        return i + 1 + iGETARG_sBx;
    }

    InstructionPtr getjumpcontrol(int i) {
        InstructionPtr instructionPtr = new InstructionPtr(this.f.code, i);
        return (i < 1 || !testTMode(GET_OPCODE(instructionPtr.code[instructionPtr.idx - 1]))) ? instructionPtr : new InstructionPtr(instructionPtr.code, instructionPtr.idx - 1);
    }

    int getlabel() {
        int i = this.pc;
        this.lasttarget = i;
        return i;
    }

    LocVars getlocvar(int i) {
        short s = this.ls.dyd.actvar[this.firstlocal + i].idx;
        _assert(s < this.nlocvars);
        return this.f.locvars[s];
    }

    void goiffalse(LexState.expdesc expdescVar) {
        int iJumponcond;
        dischargevars(expdescVar);
        int i = expdescVar.k;
        if (i == 1 || i == 3) {
            iJumponcond = -1;
        } else {
            iJumponcond = i != 10 ? jumponcond(expdescVar, 1) : expdescVar.u.info;
        }
        concat(expdescVar.t, iJumponcond);
        patchtohere(expdescVar.f.i);
        expdescVar.f.i = -1;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0021  */
    void goiftrue(LexState.expdesc expdescVar) {
        int iJumponcond;
        dischargevars(expdescVar);
        int i = expdescVar.k;
        if (i == 2) {
            iJumponcond = -1;
        } else if (i == 10) {
            invertjump(expdescVar);
            iJumponcond = expdescVar.u.info;
        } else if (i == 4 || i == 5) {
            iJumponcond = -1;
        } else {
            iJumponcond = jumponcond(expdescVar, 0);
        }
        concat(expdescVar.f, iJumponcond);
        patchtohere(expdescVar.t.i);
        expdescVar.t.i = -1;
    }

    boolean hasmultret(int i) {
        return i == 12 || i == 13;
    }

    void indexed(LexState.expdesc expdescVar, LexState.expdesc expdescVar2) {
        expdescVar.u.ind_t = (short) expdescVar.u.info;
        expdescVar.u.ind_idx = (short) exp2RK(expdescVar2);
        LuaC._assert(expdescVar.k == 8 || vkisinreg(expdescVar.k));
        expdescVar.u.ind_vt = (short) (expdescVar.k != 8 ? 7 : 8);
        expdescVar.k = 9;
    }

    void infix(int i, LexState.expdesc expdescVar) {
        if (i == 13) {
            goiftrue(expdescVar);
            return;
        }
        if (i == 14) {
            goiffalse(expdescVar);
            return;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                if (expdescVar.isnumeral()) {
                    return;
                }
                break;
            case 6:
                exp2nextreg(expdescVar);
                return;
        }
        exp2RK(expdescVar);
    }

    void invertjump(LexState.expdesc expdescVar) {
        InstructionPtr instructionPtr = getjumpcontrol(expdescVar.u.info);
        _assert((!testTMode(GET_OPCODE(instructionPtr.get())) || GET_OPCODE(instructionPtr.get()) == 28 || Lua.GET_OPCODE(instructionPtr.get()) == 27) ? false : true);
        SETARG_A(instructionPtr, GETARG_A(instructionPtr.get()) != 0 ? 0 : 1);
    }

    int jump() {
        int i = this.jpc.i;
        this.jpc.i = -1;
        IntPtr intPtr = new IntPtr(codeAsBx(23, 0, -1));
        concat(intPtr, i);
        return intPtr.i;
    }

    int jumponcond(LexState.expdesc expdescVar, int i) {
        if (expdescVar.k == 11) {
            int i2 = getcode(expdescVar);
            if (GET_OPCODE(i2) == 20) {
                this.pc--;
                return condjump(27, GETARG_B(i2), 0, i != 0 ? 0 : 1);
            }
        }
        discharge2anyreg(expdescVar);
        freeexp(expdescVar);
        return condjump(28, 255, expdescVar.u.info, i);
    }

    void lastlistfield(LexState.ConsControl consControl) {
        if (consControl.tostore == 0) {
            return;
        }
        if (hasmultret(consControl.v.k)) {
            setmultret(consControl.v);
            setlist(consControl.t.u.info, consControl.na, -1);
            consControl.na--;
        } else {
            if (consControl.v.k != 0) {
                exp2nextreg(consControl.v);
            }
            setlist(consControl.t.u.info, consControl.na, consControl.tostore);
        }
    }

    void leaveblock() {
        BlockCnt blockCnt = this.bl;
        if (blockCnt.previous != null && blockCnt.upval) {
            int iJump = jump();
            patchclose(iJump, blockCnt.nactvar);
            patchtohere(iJump);
        }
        if (blockCnt.isloop) {
            this.ls.breaklabel();
        }
        this.bl = blockCnt.previous;
        removevars(blockCnt.nactvar);
        _assert(blockCnt.nactvar == this.nactvar);
        this.freereg = this.nactvar;
        this.ls.dyd.n_label = blockCnt.firstlabel;
        if (blockCnt.previous != null) {
            movegotosout(blockCnt);
        } else if (blockCnt.firstgoto < this.ls.dyd.n_gt) {
            LexState lexState = this.ls;
            lexState.undefgoto(lexState.dyd.gt[blockCnt.firstgoto]);
        }
    }

    void markupval(int i) {
        BlockCnt blockCnt = this.bl;
        while (blockCnt.nactvar > i) {
            blockCnt = blockCnt.previous;
        }
        blockCnt.upval = true;
    }

    void movegotosout(BlockCnt blockCnt) {
        int i = blockCnt.firstgoto;
        LexState.Labeldesc[] labeldescArr = this.ls.dyd.gt;
        while (i < this.ls.dyd.n_gt) {
            LexState.Labeldesc labeldesc = labeldescArr[i];
            if (labeldesc.nactvar > blockCnt.nactvar) {
                if (blockCnt.upval) {
                    patchclose(labeldesc.pc, blockCnt.nactvar);
                }
                labeldesc.nactvar = blockCnt.nactvar;
            }
            if (!this.ls.findlabel(i)) {
                i++;
            }
        }
    }

    boolean need_value(int i) {
        while (i != -1) {
            if (GET_OPCODE(getjumpcontrol(i).get()) != 28) {
                return true;
            }
            i = getjump(i);
        }
        return false;
    }

    int newupvalue(LuaString luaString, LexState.expdesc expdescVar) {
        checklimit(this.nups + 1, 255, "upvalues");
        if (this.f.upvalues == null || this.nups + 1 > this.f.upvalues.length) {
            Prototype prototype = this.f;
            Upvaldesc[] upvaldescArr = prototype.upvalues;
            short s = this.nups;
            prototype.upvalues = realloc(upvaldescArr, s > 0 ? s * 2 : 1);
        }
        this.f.upvalues[this.nups] = new Upvaldesc(luaString, expdescVar.k == 7, expdescVar.u.info);
        short s2 = this.nups;
        this.nups = (short) (s2 + 1);
        return s2;
    }

    void nil(int i, int i2) {
        int i3 = i + i2;
        int i4 = i3 - 1;
        int i5 = this.pc;
        if (i5 > this.lasttarget && i5 > 0) {
            int i6 = this.f.code[this.pc - 1];
            if (GET_OPCODE(i6) == 4) {
                int iGETARG_A = GETARG_A(i6);
                int iGETARG_B = GETARG_B(i6) + iGETARG_A;
                if ((iGETARG_A <= i && i <= iGETARG_B + 1) || (i <= iGETARG_A && iGETARG_A <= i3)) {
                    if (iGETARG_A < i) {
                        i = iGETARG_A;
                    }
                    if (iGETARG_B > i4) {
                        i4 = iGETARG_B;
                    }
                    InstructionPtr instructionPtr = new InstructionPtr(this.f.code, this.pc - 1);
                    SETARG_A(instructionPtr, i);
                    SETARG_B(instructionPtr, i4 - i);
                    return;
                }
            }
        }
        codeABC(4, i, i2 - 1, 0);
    }

    int nilK() {
        return addk(LuaValue.NIL);
    }

    int numberK(LuaValue luaValue) {
        if (luaValue instanceof LuaDouble) {
            double d = luaValue.todouble();
            int i = (int) d;
            if (d == i) {
                luaValue = LuaInteger.valueOf(i);
            }
        }
        return addk(luaValue);
    }

    void patchclose(int i, int i2) {
        int i3 = i2 + 1;
        while (i != -1) {
            int i4 = getjump(i);
            _assert(GET_OPCODE(this.f.code[i]) == 23 && (GETARG_A(this.f.code[i]) == 0 || GETARG_A(this.f.code[i]) >= i3));
            SETARG_A(this.f.code, i, i3);
            i = i4;
        }
    }

    void patchlist(int i, int i2) {
        int i3 = this.pc;
        if (i2 == i3) {
            patchtohere(i);
        } else {
            _assert(i2 < i3);
            patchlistaux(i, i2, 255, i2);
        }
    }

    void patchlistaux(int i, int i2, int i3, int i4) {
        while (i != -1) {
            int i5 = getjump(i);
            if (patchtestreg(i, i3)) {
                fixjump(i, i2);
            } else {
                fixjump(i, i4);
            }
            i = i5;
        }
    }

    boolean patchtestreg(int i, int i2) {
        InstructionPtr instructionPtr = getjumpcontrol(i);
        if (GET_OPCODE(instructionPtr.get()) != 28) {
            return false;
        }
        if (i2 == 255 || i2 == GETARG_B(instructionPtr.get())) {
            instructionPtr.set(CREATE_ABC(27, GETARG_B(instructionPtr.get()), 0, Lua.GETARG_C(instructionPtr.get())));
            return true;
        }
        SETARG_A(instructionPtr, i2);
        return true;
    }

    void patchtohere(int i) {
        getlabel();
        concat(this.jpc, i);
    }

    void posfix(int i, LexState.expdesc expdescVar, LexState.expdesc expdescVar2, int i2) {
        int i3;
        IntPtr intPtr;
        IntPtr intPtr2;
        switch (i) {
            case 0:
                i3 = 13;
                codearith(i3, expdescVar, expdescVar2, i2);
                return;
            case 1:
                i3 = 14;
                codearith(i3, expdescVar, expdescVar2, i2);
                return;
            case 2:
                i3 = 15;
                codearith(i3, expdescVar, expdescVar2, i2);
                return;
            case 3:
                i3 = 16;
                codearith(i3, expdescVar, expdescVar2, i2);
                return;
            case 4:
                i3 = 17;
                codearith(i3, expdescVar, expdescVar2, i2);
                return;
            case 5:
                i3 = 18;
                codearith(i3, expdescVar, expdescVar2, i2);
                return;
            case 6:
                exp2val(expdescVar2);
                if (expdescVar2.k != 11 || GET_OPCODE(getcode(expdescVar2)) != 22) {
                    exp2nextreg(expdescVar2);
                    codearith(22, expdescVar, expdescVar2, i2);
                    return;
                }
                _assert(expdescVar.u.info == GETARG_B(getcode(expdescVar2)) - 1);
                freeexp(expdescVar);
                SETARG_B(getcodePtr(expdescVar2), expdescVar.u.info);
                expdescVar.k = 11;
                expdescVar.u.info = expdescVar2.u.info;
                return;
            case 7:
                codecomp(24, 0, expdescVar, expdescVar2);
                return;
            case 8:
                codecomp(24, 1, expdescVar, expdescVar2);
                return;
            case 9:
                codecomp(25, 1, expdescVar, expdescVar2);
                return;
            case 10:
                codecomp(26, 1, expdescVar, expdescVar2);
                return;
            case 11:
                codecomp(25, 0, expdescVar, expdescVar2);
                return;
            case 12:
                codecomp(26, 0, expdescVar, expdescVar2);
                return;
            case 13:
                _assert(expdescVar.t.i == -1);
                dischargevars(expdescVar2);
                intPtr = expdescVar2.f;
                intPtr2 = expdescVar.f;
                break;
            case 14:
                _assert(expdescVar.f.i == -1);
                dischargevars(expdescVar2);
                intPtr = expdescVar2.t;
                intPtr2 = expdescVar.t;
                break;
            default:
                _assert(false);
                return;
        }
        concat(intPtr, intPtr2.i);
        expdescVar.setvalue(expdescVar2);
    }

    void prefix(int i, LexState.expdesc expdescVar, int i2) {
        int i3;
        LexState.expdesc expdescVar2 = new LexState.expdesc();
        expdescVar2.init(5, 0);
        if (i != 0) {
            if (i == 1) {
                codenot(expdescVar);
                return;
            } else if (i != 2) {
                _assert(false);
                return;
            } else {
                exp2anyreg(expdescVar);
                i3 = 21;
            }
        } else if (expdescVar.isnumeral()) {
            expdescVar.u.setNval(expdescVar.u.nval().neg());
            return;
        } else {
            exp2anyreg(expdescVar);
            i3 = 19;
        }
        codearith(i3, expdescVar, expdescVar2, i2);
    }

    void removevalues(int i) {
        while (i != -1) {
            patchtestreg(i, 255);
            i = getjump(i);
        }
    }

    void removevars(int i) {
        this.ls.dyd.n_actvar -= this.nactvar - i;
        while (true) {
            short s = this.nactvar;
            if (s <= i) {
                return;
            }
            short s2 = (short) (s - 1);
            this.nactvar = s2;
            getlocvar(s2).endpc = this.pc;
        }
    }

    void reserveregs(int i) {
        checkstack(i);
        this.freereg = (short) (this.freereg + i);
    }

    void ret(int i, int i2) {
        codeABC(31, i, i2 + 1, 0);
    }

    int searchupvalue(LuaString luaString) {
        Upvaldesc[] upvaldescArr = this.f.upvalues;
        for (int i = 0; i < this.nups; i++) {
            if (upvaldescArr[i].f1043name.eq_b(luaString)) {
                return i;
            }
        }
        return -1;
    }

    int searchvar(LuaString luaString) {
        for (int i = this.nactvar - 1; i >= 0; i--) {
            if (luaString.eq_b(getlocvar(i).varname)) {
                return i;
            }
        }
        return -1;
    }

    void self(LexState.expdesc expdescVar, LexState.expdesc expdescVar2) {
        exp2anyreg(expdescVar);
        freeexp(expdescVar);
        short s = this.freereg;
        reserveregs(2);
        codeABC(12, s, expdescVar.u.info, exp2RK(expdescVar2));
        freeexp(expdescVar2);
        expdescVar.u.info = s;
        expdescVar.k = 6;
    }

    void setlist(int i, int i2, int i3) {
        int i4 = ((i2 - 1) / 50) + 1;
        int i5 = i3 == -1 ? 0 : i3;
        _assert(i3 != 0);
        if (i4 <= 511) {
            codeABC(36, i, i5, i4);
        } else {
            codeABC(36, i, i5, 0);
            code(i4, this.ls.lastline);
        }
        this.freereg = (short) (i + 1);
    }

    void setmultret(LexState.expdesc expdescVar) {
        setreturns(expdescVar, -1);
    }

    void setoneret(LexState.expdesc expdescVar) {
        if (expdescVar.k == 12) {
            expdescVar.k = 6;
            expdescVar.u.info = GETARG_A(getcode(expdescVar));
        } else if (expdescVar.k == 13) {
            SETARG_B(getcodePtr(expdescVar), 2);
            expdescVar.k = 11;
        }
    }

    void setreturns(LexState.expdesc expdescVar, int i) {
        if (expdescVar.k == 12) {
            SETARG_C(getcodePtr(expdescVar), i + 1);
        } else if (expdescVar.k == 13) {
            SETARG_B(getcodePtr(expdescVar), i + 1);
            SETARG_A(getcodePtr(expdescVar), this.freereg);
            reserveregs(1);
        }
    }

    void storevar(LexState.expdesc expdescVar, LexState.expdesc expdescVar2) {
        int i = expdescVar.k;
        if (i == 7) {
            freeexp(expdescVar2);
            exp2reg(expdescVar2, expdescVar.u.info);
            return;
        }
        if (i == 8) {
            codeABC(9, exp2anyreg(expdescVar2), expdescVar.u.info, 0);
        } else if (i != 9) {
            _assert(false);
        } else {
            codeABC(expdescVar.u.ind_vt == 7 ? 10 : 8, expdescVar.u.ind_t, expdescVar.u.ind_idx, exp2RK(expdescVar2));
        }
        freeexp(expdescVar2);
    }

    int stringK(LuaString luaString) {
        return addk(luaString);
    }
}
