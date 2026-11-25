package org.luaj.vm2.lib;

import java.util.Random;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/* JADX INFO: loaded from: classes6.dex */
public class MathLib extends TwoArgFunction {
    public static MathLib MATHLIB;

    /* JADX INFO: Access modifiers changed from: protected */
    public static abstract class BinaryOp extends TwoArgFunction {
        protected BinaryOp() {
        }

        protected abstract double call(double d, double d2);

        @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return valueOf(call(luaValue.checkdouble(), luaValue2.checkdouble()));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static abstract class UnaryOp extends OneArgFunction {
        protected UnaryOp() {
        }

        protected abstract double call(double d);

        @Override // org.luaj.vm2.lib.OneArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return valueOf(call(luaValue.checkdouble()));
        }
    }

    static final class abs extends UnaryOp {
        abs() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.abs(d);
        }
    }

    static final class ceil extends UnaryOp {
        ceil() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.ceil(d);
        }
    }

    static final class cos extends UnaryOp {
        cos() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.cos(d);
        }
    }

    static final class deg extends UnaryOp {
        deg() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.toDegrees(d);
        }
    }

    static final class exp extends UnaryOp {
        final MathLib mathlib;

        exp(MathLib mathLib) {
            this.mathlib = mathLib;
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return this.mathlib.dpow_lib(2.718281828459045d, d);
        }
    }

    static final class floor extends UnaryOp {
        floor() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.floor(d);
        }
    }

    static final class fmod extends BinaryOp {
        fmod() {
        }

        @Override // org.luaj.vm2.lib.MathLib.BinaryOp
        protected double call(double d, double d2) {
            double d3 = d / d2;
            return d - (d2 * (d3 >= 0.0d ? Math.floor(d3) : Math.ceil(d3)));
        }
    }

    static class frexp extends VarArgFunction {
        frexp() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaNumber luaNumberValueOf;
            LuaNumber luaNumberValueOf2;
            double dCheckdouble = varargs.checkdouble(1);
            if (dCheckdouble == 0.0d) {
                luaNumberValueOf = ZERO;
                luaNumberValueOf2 = ZERO;
            } else {
                long jDoubleToLongBits = Double.doubleToLongBits(dCheckdouble);
                luaNumberValueOf = valueOf(((4503599627370495L & jDoubleToLongBits) + 4503599627370496L) * (jDoubleToLongBits >= 0 ? 1.1102230246251565E-16d : -1.1102230246251565E-16d));
                luaNumberValueOf2 = valueOf((((int) (jDoubleToLongBits >> 52)) & 2047) - 1022);
            }
            return varargsOf(luaNumberValueOf, luaNumberValueOf2);
        }
    }

    static final class ldexp extends BinaryOp {
        ldexp() {
        }

        @Override // org.luaj.vm2.lib.MathLib.BinaryOp
        protected double call(double d, double d2) {
            return d * Double.longBitsToDouble((((long) d2) + 1023) << 52);
        }
    }

    static class max extends VarArgFunction {
        max() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            double dCheckdouble = varargs.checkdouble(1);
            int iNarg = varargs.narg();
            for (int i = 2; i <= iNarg; i++) {
                dCheckdouble = Math.max(dCheckdouble, varargs.checkdouble(i));
            }
            return valueOf(dCheckdouble);
        }
    }

    static class min extends VarArgFunction {
        min() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            double dCheckdouble = varargs.checkdouble(1);
            int iNarg = varargs.narg();
            for (int i = 2; i <= iNarg; i++) {
                dCheckdouble = Math.min(dCheckdouble, varargs.checkdouble(i));
            }
            return valueOf(dCheckdouble);
        }
    }

    static class modf extends VarArgFunction {
        modf() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            double dCheckdouble = varargs.checkdouble(1);
            double dFloor = dCheckdouble > 0.0d ? Math.floor(dCheckdouble) : Math.ceil(dCheckdouble);
            return varargsOf(valueOf(dFloor), valueOf(dCheckdouble - dFloor));
        }
    }

    static final class pow extends BinaryOp {
        pow() {
        }

        @Override // org.luaj.vm2.lib.MathLib.BinaryOp
        protected double call(double d, double d2) {
            return MathLib.dpow_default(d, d2);
        }
    }

    static final class rad extends UnaryOp {
        rad() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.toRadians(d);
        }
    }

    static class random extends LibFunction {
        Random random = new Random();

        random() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call() {
            return valueOf(this.random.nextDouble());
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            int iCheckint = luaValue.checkint();
            if (iCheckint < 1) {
                argerror(1, "interval is empty");
            }
            return valueOf(this.random.nextInt(iCheckint) + 1);
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            int iCheckint = luaValue.checkint();
            int iCheckint2 = luaValue2.checkint();
            if (iCheckint2 < iCheckint) {
                argerror(2, "interval is empty");
            }
            return valueOf(iCheckint + this.random.nextInt((iCheckint2 + 1) - iCheckint));
        }
    }

    static class randomseed extends OneArgFunction {
        final random random;

        randomseed(random randomVar) {
            this.random = randomVar;
        }

        @Override // org.luaj.vm2.lib.OneArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            long jChecklong = luaValue.checklong();
            this.random.random = new Random(jChecklong);
            return NONE;
        }
    }

    static final class sin extends UnaryOp {
        sin() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.sin(d);
        }
    }

    static final class sqrt extends UnaryOp {
        sqrt() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.sqrt(d);
        }
    }

    static final class tan extends UnaryOp {
        tan() {
        }

        @Override // org.luaj.vm2.lib.MathLib.UnaryOp
        protected double call(double d) {
            return Math.tan(d);
        }
    }

    public MathLib() {
        MATHLIB = this;
    }

    public static LuaValue dpow(double d, double d2) {
        MathLib mathLib = MATHLIB;
        return LuaDouble.valueOf(mathLib != null ? mathLib.dpow_lib(d, d2) : dpow_default(d, d2));
    }

    public static double dpow_d(double d, double d2) {
        MathLib mathLib = MATHLIB;
        return mathLib != null ? mathLib.dpow_lib(d, d2) : dpow_default(d, d2);
    }

    protected static double dpow_default(double d, double d2) {
        double d3 = 1.0d;
        if (d2 < 0.0d) {
            return 1.0d / dpow_default(d, -d2);
        }
        int i = (int) d2;
        double d4 = d;
        while (i > 0) {
            if ((i & 1) != 0) {
                d3 *= d4;
            }
            i >>= 1;
            d4 *= d4;
        }
        double d5 = d2 - ((double) i);
        if (d5 > 0.0d) {
            for (int i2 = (int) (d5 * 65536.0d); (65535 & i2) != 0; i2 <<= 1) {
                d = Math.sqrt(d);
                if ((32768 & i2) != 0) {
                    d3 *= d;
                }
            }
        }
        return d3;
    }

    @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaValue luaTable = new LuaTable(0, 30);
        luaTable.set("abs", new abs());
        luaTable.set("ceil", new ceil());
        luaTable.set("cos", new cos());
        luaTable.set("deg", new deg());
        luaTable.set("exp", new exp(this));
        luaTable.set("floor", new floor());
        luaTable.set("fmod", new fmod());
        luaTable.set("frexp", new frexp());
        luaTable.set("huge", LuaDouble.POSINF);
        luaTable.set("ldexp", new ldexp());
        luaTable.set("max", new max());
        luaTable.set("min", new min());
        luaTable.set("modf", new modf());
        luaTable.set("pi", 3.141592653589793d);
        luaTable.set("pow", new pow());
        random randomVar = new random();
        luaTable.set("random", randomVar);
        luaTable.set("randomseed", new randomseed(randomVar));
        luaTable.set("rad", new rad());
        luaTable.set("sin", new sin());
        luaTable.set("sqrt", new sqrt());
        luaTable.set("tan", new tan());
        luaValue2.set("math", luaTable);
        luaValue2.get("package").get("loaded").set("math", luaTable);
        return luaTable;
    }

    public double dpow_lib(double d, double d2) {
        return dpow_default(d, d2);
    }
}
