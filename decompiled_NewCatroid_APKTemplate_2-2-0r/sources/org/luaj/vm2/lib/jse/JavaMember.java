package org.luaj.vm2.lib.jse;

import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/* JADX INFO: loaded from: classes6.dex */
abstract class JavaMember extends VarArgFunction {
    static final int METHOD_MODIFIERS_VARARGS = 128;
    final CoerceLuaToJava.Coercion[] fixedargs;
    final CoerceLuaToJava.Coercion varargs;

    protected JavaMember(Class[] clsArr, int i) {
        int i2 = 0;
        boolean z = (i & 128) != 0;
        int length = clsArr.length;
        this.fixedargs = new CoerceLuaToJava.Coercion[z ? length - 1 : length];
        while (true) {
            CoerceLuaToJava.Coercion[] coercionArr = this.fixedargs;
            if (i2 >= coercionArr.length) {
                break;
            }
            coercionArr[i2] = CoerceLuaToJava.getCoercion(clsArr[i2]);
            i2++;
        }
        this.varargs = z ? CoerceLuaToJava.getCoercion(clsArr[clsArr.length - 1]) : null;
    }

    protected Object[] convertArgs(Varargs varargs) {
        Object[] objArr;
        CoerceLuaToJava.Coercion[] coercionArr;
        int i = 0;
        if (this.varargs == null) {
            int length = this.fixedargs.length;
            objArr = new Object[length];
            while (i < length) {
                int i2 = i + 1;
                objArr[i] = this.fixedargs[i].coerce(varargs.arg(i2));
                i = i2;
            }
        } else {
            int iMax = Math.max(this.fixedargs.length, varargs.narg());
            objArr = new Object[iMax];
            while (true) {
                coercionArr = this.fixedargs;
                if (i >= coercionArr.length) {
                    break;
                }
                int i3 = i + 1;
                objArr[i] = coercionArr[i].coerce(varargs.arg(i3));
                i = i3;
            }
            int length2 = coercionArr.length;
            while (length2 < iMax) {
                int i4 = length2 + 1;
                objArr[length2] = this.varargs.coerce(varargs.arg(i4));
                length2 = i4;
            }
        }
        return objArr;
    }

    int score(Varargs varargs) {
        CoerceLuaToJava.Coercion[] coercionArr;
        int iNarg = varargs.narg();
        int i = 0;
        int length = iNarg > this.fixedargs.length ? CoerceLuaToJava.SCORE_WRONG_TYPE * (iNarg - this.fixedargs.length) : 0;
        while (true) {
            coercionArr = this.fixedargs;
            if (i >= coercionArr.length) {
                break;
            }
            CoerceLuaToJava.Coercion coercion = coercionArr[i];
            i++;
            length += coercion.score(varargs.arg(i));
        }
        if (this.varargs != null) {
            int length2 = coercionArr.length;
            while (length2 < iNarg) {
                length2++;
                length += this.varargs.score(varargs.arg(length2));
            }
        }
        return length;
    }
}
