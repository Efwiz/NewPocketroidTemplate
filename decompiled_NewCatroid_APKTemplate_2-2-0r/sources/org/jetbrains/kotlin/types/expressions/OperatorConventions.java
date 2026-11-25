package org.jetbrains.kotlin.types.expressions;

import org.jetbrains.kotlin.com.google.common.collect.ImmutableBiMap;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes6.dex */
public class OperatorConventions {
    public static final ImmutableBiMap<KtSingleValueToken, Name> ASSIGNMENT_OPERATIONS;
    public static final ImmutableBiMap<KtSingleValueToken, KtSingleValueToken> ASSIGNMENT_OPERATION_COUNTERPARTS;
    public static final Name ASSIGN_METHOD;
    public static final ImmutableBiMap<KtSingleValueToken, Name> BINARY_OPERATION_NAMES;
    public static final ImmutableBiMap<KtSingleValueToken, Name> BOOLEAN_OPERATIONS;
    public static final Name BYTE;
    public static final Name CHAR;
    public static final ImmutableSet<KtSingleValueToken> COMPARISON_OPERATIONS;
    public static final ImmutableSet<Name> CONVENTION_NAMES;
    public static final Name DOUBLE;
    public static final ImmutableSet<KtSingleValueToken> EQUALS_OPERATIONS;
    public static final Name FLOAT;
    public static final ImmutableSet<KtSingleValueToken> IDENTITY_EQUALS_OPERATIONS;
    public static final ImmutableSet<KtSingleValueToken> INCREMENT_OPERATIONS;
    public static final Name INT;
    public static final ImmutableSet<KtSingleValueToken> IN_OPERATIONS;
    public static final Name LONG;
    public static final ImmutableSet<KtSingleValueToken> NOT_OVERLOADABLE;
    public static final ImmutableSet<Name> NUMBER_CONVERSIONS;
    public static final ImmutableBiMap<Name, Name> REM_TO_MOD_OPERATION_NAMES;
    public static final Name SHORT;
    public static final ImmutableBiMap<KtSingleValueToken, Name> UNARY_OPERATION_NAMES;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2 || i == 3) {
            objArr[0] = "name";
        } else {
            objArr[0] = "token";
        }
        objArr[1] = "org/jetbrains/kotlin/types/expressions/OperatorConventions";
        if (i == 2) {
            objArr[2] = "getOperationSymbolForName";
        } else if (i != 3) {
            objArr[2] = "getNameForOperationSymbol";
        } else {
            objArr[2] = "isConventionName";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private OperatorConventions() {
    }

    static {
        Name nameIdentifier = Name.identifier("toDouble");
        DOUBLE = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("toFloat");
        FLOAT = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("toLong");
        LONG = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier("toInt");
        INT = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("toChar");
        CHAR = nameIdentifier5;
        Name nameIdentifier6 = Name.identifier("toShort");
        SHORT = nameIdentifier6;
        Name nameIdentifier7 = Name.identifier("toByte");
        BYTE = nameIdentifier7;
        NUMBER_CONVERSIONS = ImmutableSet.of(nameIdentifier, nameIdentifier2, nameIdentifier3, nameIdentifier4, nameIdentifier6, nameIdentifier7, nameIdentifier5);
        ImmutableBiMap<KtSingleValueToken, Name> immutableBiMapBuild = ImmutableBiMap.builder().put(KtTokens.PLUSPLUS, OperatorNameConventions.INC).put(KtTokens.MINUSMINUS, OperatorNameConventions.DEC).put(KtTokens.PLUS, OperatorNameConventions.UNARY_PLUS).put(KtTokens.MINUS, OperatorNameConventions.UNARY_MINUS).put(KtTokens.EXCL, OperatorNameConventions.NOT).build();
        UNARY_OPERATION_NAMES = immutableBiMapBuild;
        ImmutableBiMap<KtSingleValueToken, Name> immutableBiMapBuild2 = ImmutableBiMap.builder().put(KtTokens.MUL, OperatorNameConventions.TIMES).put(KtTokens.PLUS, OperatorNameConventions.PLUS).put(KtTokens.MINUS, OperatorNameConventions.MINUS).put(KtTokens.DIV, OperatorNameConventions.DIV).put(KtTokens.PERC, OperatorNameConventions.REM).put(KtTokens.RANGE, OperatorNameConventions.RANGE_TO).put(KtTokens.RANGE_UNTIL, OperatorNameConventions.RANGE_UNTIL).build();
        BINARY_OPERATION_NAMES = immutableBiMapBuild2;
        REM_TO_MOD_OPERATION_NAMES = ImmutableBiMap.builder().put(OperatorNameConventions.REM, OperatorNameConventions.MOD).put(OperatorNameConventions.REM_ASSIGN, OperatorNameConventions.MOD_ASSIGN).build();
        NOT_OVERLOADABLE = ImmutableSet.of(KtTokens.ANDAND, KtTokens.OROR, KtTokens.ELVIS, KtTokens.EQEQEQ, KtTokens.EXCLEQEQEQ);
        INCREMENT_OPERATIONS = ImmutableSet.of(KtTokens.PLUSPLUS, KtTokens.MINUSMINUS);
        COMPARISON_OPERATIONS = ImmutableSet.of(KtTokens.LT, KtTokens.GT, KtTokens.LTEQ, KtTokens.GTEQ);
        EQUALS_OPERATIONS = ImmutableSet.of(KtTokens.EQEQ, KtTokens.EXCLEQ);
        IDENTITY_EQUALS_OPERATIONS = ImmutableSet.of(KtTokens.EQEQEQ, KtTokens.EXCLEQEQEQ);
        IN_OPERATIONS = ImmutableSet.of((KtKeywordToken) KtTokens.IN_KEYWORD, KtTokens.NOT_IN);
        ImmutableBiMap<KtSingleValueToken, Name> immutableBiMapBuild3 = ImmutableBiMap.builder().put(KtTokens.MULTEQ, OperatorNameConventions.TIMES_ASSIGN).put(KtTokens.DIVEQ, OperatorNameConventions.DIV_ASSIGN).put(KtTokens.PERCEQ, OperatorNameConventions.REM_ASSIGN).put(KtTokens.PLUSEQ, OperatorNameConventions.PLUS_ASSIGN).put(KtTokens.MINUSEQ, OperatorNameConventions.MINUS_ASSIGN).build();
        ASSIGNMENT_OPERATIONS = immutableBiMapBuild3;
        ASSIGNMENT_OPERATION_COUNTERPARTS = ImmutableBiMap.builder().put(KtTokens.MULTEQ, KtTokens.MUL).put(KtTokens.DIVEQ, KtTokens.DIV).put(KtTokens.PERCEQ, KtTokens.PERC).put(KtTokens.PLUSEQ, KtTokens.PLUS).put(KtTokens.MINUSEQ, KtTokens.MINUS).build();
        ASSIGN_METHOD = Name.identifier("assign");
        BOOLEAN_OPERATIONS = ImmutableBiMap.builder().put(KtTokens.ANDAND, OperatorNameConventions.AND).put(KtTokens.OROR, OperatorNameConventions.OR).build();
        CONVENTION_NAMES = ImmutableSet.builder().add((Object[]) new Name[]{OperatorNameConventions.GET, OperatorNameConventions.SET, OperatorNameConventions.INVOKE, OperatorNameConventions.CONTAINS, OperatorNameConventions.ITERATOR, OperatorNameConventions.NEXT, OperatorNameConventions.HAS_NEXT, OperatorNameConventions.EQUALS, OperatorNameConventions.COMPARE_TO, OperatorNameConventions.GET_VALUE, OperatorNameConventions.SET_VALUE}).addAll((Iterable) immutableBiMapBuild.values()).addAll((Iterable) immutableBiMapBuild2.values()).addAll((Iterable) immutableBiMapBuild3.values()).build();
    }

    public static Name getNameForOperationSymbol(KtToken ktToken) {
        if (ktToken == null) {
            $$$reportNull$$$0(0);
        }
        return getNameForOperationSymbol(ktToken, true, true);
    }

    public static Name getNameForOperationSymbol(KtToken ktToken, boolean z, boolean z2) {
        Name name2;
        Name name3;
        if (ktToken == null) {
            $$$reportNull$$$0(1);
        }
        if (z2 && (name3 = BINARY_OPERATION_NAMES.get(ktToken)) != null) {
            return name3;
        }
        if (z && (name2 = UNARY_OPERATION_NAMES.get(ktToken)) != null) {
            return name2;
        }
        Name name4 = ASSIGNMENT_OPERATIONS.get(ktToken);
        if (name4 != null) {
            return name4;
        }
        if (COMPARISON_OPERATIONS.contains(ktToken)) {
            return OperatorNameConventions.COMPARE_TO;
        }
        if (EQUALS_OPERATIONS.contains(ktToken)) {
            return OperatorNameConventions.EQUALS;
        }
        if (IN_OPERATIONS.contains(ktToken)) {
            return OperatorNameConventions.CONTAINS;
        }
        return null;
    }

    public static KtToken getOperationSymbolForName(Name name2) {
        if (name2 == null) {
            $$$reportNull$$$0(2);
        }
        if (!isConventionName(name2)) {
            return null;
        }
        KtSingleValueToken ktSingleValueToken = BINARY_OPERATION_NAMES.inverse().get(name2);
        if (ktSingleValueToken != null) {
            return ktSingleValueToken;
        }
        KtSingleValueToken ktSingleValueToken2 = UNARY_OPERATION_NAMES.inverse().get(name2);
        if (ktSingleValueToken2 != null) {
            return ktSingleValueToken2;
        }
        KtSingleValueToken ktSingleValueToken3 = ASSIGNMENT_OPERATIONS.inverse().get(name2);
        if (ktSingleValueToken3 != null) {
            return ktSingleValueToken3;
        }
        return null;
    }

    public static boolean isConventionName(Name name2) {
        if (name2 == null) {
            $$$reportNull$$$0(3);
        }
        return CONVENTION_NAMES.contains(name2) || OperatorNameConventions.COMPONENT_REGEX.matches(name2.asString());
    }
}
