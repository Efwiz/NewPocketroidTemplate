package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: OperatorNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b)\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006R\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\nR\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0006R\u0011\u0010/\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0006¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/OperatorNames;", "", "()V", "ADD", "Lorg/jetbrains/kotlin/name/Name;", "getADD", "()Lorg/jetbrains/kotlin/name/Name;", "ALL", "", "getALL", "()Ljava/util/Set;", "AND", "getAND", "BINARY", "getBINARY", "DEC", "getDEC", "DIV", "getDIV", "INC", "getINC", "INV", "getINV", "MOD", "getMOD", "MUL", "getMUL", "NOT", "getNOT", "OR", "getOR", "REM", "getREM", "SHL", "getSHL", "SHR", "getSHR", "SHRU", "getSHRU", "SUB", "getSUB", "UNARY", "getUNARY", "UNARY_MINUS", "getUNARY_MINUS", "UNARY_PLUS", "getUNARY_PLUS", "XOR", "getXOR", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OperatorNames {
    private static final Name ADD;
    private static final Set<Name> ALL;
    private static final Name AND;
    private static final Set<Name> BINARY;
    private static final Name DEC;
    private static final Name DIV;
    private static final Name INC;
    public static final OperatorNames INSTANCE = new OperatorNames();
    private static final Name INV;
    private static final Name MOD;
    private static final Name MUL;
    private static final Name NOT;
    private static final Name OR;
    private static final Name REM;
    private static final Name SHL;
    private static final Name SHR;
    private static final Name SHRU;
    private static final Name SUB;
    private static final Set<Name> UNARY;
    private static final Name UNARY_MINUS;
    private static final Name UNARY_PLUS;
    private static final Name XOR;

    private OperatorNames() {
    }

    static {
        Name name2 = OperatorNameConventions.UNARY_PLUS;
        UNARY_PLUS = name2;
        Name name3 = OperatorNameConventions.UNARY_MINUS;
        UNARY_MINUS = name3;
        Name name4 = OperatorNameConventions.PLUS;
        ADD = name4;
        Name name5 = OperatorNameConventions.MINUS;
        SUB = name5;
        Name name6 = OperatorNameConventions.TIMES;
        MUL = name6;
        Name name7 = OperatorNameConventions.DIV;
        DIV = name7;
        Name name8 = OperatorNameConventions.MOD;
        MOD = name8;
        Name name9 = OperatorNameConventions.REM;
        REM = name9;
        Name name10 = OperatorNameConventions.AND;
        AND = name10;
        Name name11 = OperatorNameConventions.OR;
        OR = name11;
        Name name12 = OperatorNameConventions.XOR;
        XOR = name12;
        Name name13 = OperatorNameConventions.INV;
        INV = name13;
        Name name14 = OperatorNameConventions.SHL;
        SHL = name14;
        Name name15 = OperatorNameConventions.SHR;
        SHR = name15;
        Name name16 = OperatorNameConventions.USHR;
        SHRU = name16;
        Name name17 = OperatorNameConventions.NOT;
        NOT = name17;
        Name name18 = OperatorNameConventions.INC;
        INC = name18;
        Name name19 = OperatorNameConventions.DEC;
        DEC = name19;
        Set<Name> of = SetsKt.setOf((Object[]) new Name[]{name4, name5, name6, name7, name8, name9, name10, name11, name12, name14, name15, name16});
        BINARY = of;
        Set<Name> of2 = SetsKt.setOf((Object[]) new Name[]{name2, name3, name13, name17, name18, name19});
        UNARY = of2;
        ALL = SetsKt.plus((Set) of, (Iterable) of2);
    }

    public final Name getUNARY_PLUS() {
        return UNARY_PLUS;
    }

    public final Name getUNARY_MINUS() {
        return UNARY_MINUS;
    }

    public final Name getADD() {
        return ADD;
    }

    public final Name getSUB() {
        return SUB;
    }

    public final Name getMUL() {
        return MUL;
    }

    public final Name getDIV() {
        return DIV;
    }

    public final Name getMOD() {
        return MOD;
    }

    public final Name getREM() {
        return REM;
    }

    public final Name getAND() {
        return AND;
    }

    public final Name getOR() {
        return OR;
    }

    public final Name getXOR() {
        return XOR;
    }

    public final Name getINV() {
        return INV;
    }

    public final Name getSHL() {
        return SHL;
    }

    public final Name getSHR() {
        return SHR;
    }

    public final Name getSHRU() {
        return SHRU;
    }

    public final Name getNOT() {
        return NOT;
    }

    public final Name getINC() {
        return INC;
    }

    public final Name getDEC() {
        return DEC;
    }

    public final Set<Name> getBINARY() {
        return BINARY;
    }

    public final Set<Name> getUNARY() {
        return UNARY;
    }

    public final Set<Name> getALL() {
        return ALL;
    }
}
