package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaField;

/* JADX INFO: compiled from: ConstantEvaluator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u0004\u0018\u00010\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00012\u0006\u0010\n\u001a\u00020\rH\u0002J&\u0010\u000e\u001a\u0004\u0018\u00010\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u00012\b\u0010\u0010\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/ConstantEvaluator;", "", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "evaluateBinaryExpression", "value", "Lcom/sun/tools/javac/tree/JCTree$JCBinary;", "evaluateUnaryExpression", "Lcom/sun/tools/javac/tree/JCTree$JCUnary;", "evaluateValue", "lhsValue", "rhsValue", "opcode", "Lcom/sun/tools/javac/tree/JCTree$Tag;", "getValue", "expr", "Lcom/sun/tools/javac/tree/JCTree$JCExpression;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantEvaluator {
    private final CompilationUnitTree compilationUnit;
    private final JavaClass containingClass;
    private final JavacWrapper javac;

    /* JADX INFO: compiled from: ConstantEvaluator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JCTree.Tag.values().length];
            try {
                iArr[JCTree.Tag.COMPL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JCTree.Tag.NOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JCTree.Tag.AND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[JCTree.Tag.OR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[JCTree.Tag.EQ.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[JCTree.Tag.NE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[JCTree.Tag.BITXOR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[JCTree.Tag.BITAND.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[JCTree.Tag.BITOR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[JCTree.Tag.PLUS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[JCTree.Tag.MINUS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[JCTree.Tag.MUL.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[JCTree.Tag.DIV.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[JCTree.Tag.MOD.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[JCTree.Tag.SR.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[JCTree.Tag.SL.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[JCTree.Tag.USR.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[JCTree.Tag.LT.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[JCTree.Tag.LE.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[JCTree.Tag.GT.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[JCTree.Tag.GE.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ConstantEvaluator(JavaClass containingClass, JavacWrapper javac, CompilationUnitTree compilationUnit) {
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        this.containingClass = containingClass;
        this.javac = javac;
        this.compilationUnit = compilationUnit;
    }

    public final Object getValue(JCTree.JCExpression expr) {
        Intrinsics.checkNotNullParameter(expr, "expr");
        if (expr instanceof JCTree.JCLiteral) {
            JCTree.JCLiteral jCLiteral = (JCTree.JCLiteral) expr;
            if (jCLiteral.typetag == TypeTag.BOOLEAN) {
                return Boolean.valueOf(!Intrinsics.areEqual(jCLiteral.value, (Object) 0));
            }
            return jCLiteral.value;
        }
        if (expr instanceof JCTree.JCIdent ? true : expr instanceof JCTree.JCFieldAccess) {
            JavaField javaFieldResolveField = this.javac.resolveField((JCTree) expr, this.compilationUnit, this.containingClass);
            if (javaFieldResolveField != null) {
                return javaFieldResolveField.getInitializerValue();
            }
        } else {
            if (expr instanceof JCTree.JCBinary) {
                return evaluateBinaryExpression((JCTree.JCBinary) expr);
            }
            if (expr instanceof JCTree.JCParens) {
                JCTree.JCExpression expr2 = ((JCTree.JCParens) expr).expr;
                Intrinsics.checkNotNullExpressionValue(expr2, "expr");
                return getValue(expr2);
            }
            if (expr instanceof JCTree.JCUnary) {
                return evaluateUnaryExpression((JCTree.JCUnary) expr);
            }
        }
        return null;
    }

    private final Object evaluateUnaryExpression(JCTree.JCUnary value) {
        JCTree.JCExpression arg = value.arg;
        Intrinsics.checkNotNullExpressionValue(arg, "arg");
        Object value2 = getValue(arg);
        JCTree.Tag tag = value.getTag();
        int i = tag == null ? -1 : WhenMappings.$EnumSwitchMapping$0[tag.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return null;
            }
            Boolean bool = value2 instanceof Boolean ? (Boolean) value2 : null;
            if (bool != null) {
                return Boolean.valueOf(!bool.booleanValue());
            }
            return null;
        }
        if (value2 instanceof Integer) {
            return Integer.valueOf(~((Number) value2).intValue());
        }
        if (value2 instanceof Long) {
            return Long.valueOf(~((Number) value2).longValue());
        }
        if (value2 instanceof Short) {
            return Short.valueOf((short) (~((Number) value2).shortValue()));
        }
        if (value2 instanceof Byte) {
            return Byte.valueOf((byte) (~((Number) value2).byteValue()));
        }
        return null;
    }

    private final Object evaluateBinaryExpression(JCTree.JCBinary value) {
        JCTree.JCExpression lhs = value.lhs;
        Intrinsics.checkNotNullExpressionValue(lhs, "lhs");
        Object value2 = getValue(lhs);
        if (value2 == null) {
            return null;
        }
        JCTree.JCExpression rhs = value.rhs;
        Intrinsics.checkNotNullExpressionValue(rhs, "rhs");
        Object value3 = getValue(rhs);
        if (value3 == null) {
            return null;
        }
        JCTree.Tag tag = value.getTag();
        Intrinsics.checkNotNullExpressionValue(tag, "getTag(...)");
        return evaluateValue(value2, value3, tag);
    }

    private final Object evaluateValue(Object lhsValue, Object rhsValue, JCTree.Tag opcode) {
        if ((lhsValue instanceof String) && opcode == JCTree.Tag.PLUS) {
            return ((String) lhsValue) + rhsValue;
        }
        boolean z = false;
        if ((lhsValue instanceof Boolean) && (rhsValue instanceof Boolean)) {
            switch (WhenMappings.$EnumSwitchMapping$0[opcode.ordinal()]) {
                case 3:
                    if (((Boolean) lhsValue).booleanValue() && ((Boolean) rhsValue).booleanValue()) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                case 4:
                    return Boolean.valueOf(((Boolean) lhsValue).booleanValue() || ((Boolean) rhsValue).booleanValue());
                case 5:
                    return Boolean.valueOf(Intrinsics.areEqual(lhsValue, rhsValue));
                case 6:
                    return Boolean.valueOf(!Intrinsics.areEqual(lhsValue, rhsValue));
                case 7:
                    return Boolean.valueOf(((Boolean) lhsValue).booleanValue() ^ ((Boolean) rhsValue).booleanValue());
                case 8:
                    return Boolean.valueOf(((Boolean) lhsValue).booleanValue() & ((Boolean) rhsValue).booleanValue());
                case 9:
                    return Boolean.valueOf(((Boolean) lhsValue).booleanValue() | ((Boolean) rhsValue).booleanValue());
                default:
                    return null;
            }
        }
        if (!(lhsValue instanceof Number) || !(rhsValue instanceof Number)) {
            return null;
        }
        boolean z2 = ((lhsValue instanceof Float) || (lhsValue instanceof Double) || (rhsValue instanceof Float) || (rhsValue instanceof Double)) ? false : true;
        boolean z3 = !z2 ? !((lhsValue instanceof Double) || (rhsValue instanceof Double)) : !((lhsValue instanceof Long) || (rhsValue instanceof Long));
        switch (WhenMappings.$EnumSwitchMapping$0[opcode.ordinal()]) {
            case 5:
                if (!z2 ? ((Number) lhsValue).doubleValue() == ((Number) rhsValue).doubleValue() : ((Number) lhsValue).longValue() == ((Number) rhsValue).longValue()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 6:
                if (!z2 ? ((Number) lhsValue).doubleValue() != ((Number) rhsValue).doubleValue() : ((Number) lhsValue).longValue() != ((Number) rhsValue).longValue()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 7:
                if (z3) {
                    return Long.valueOf(((Number) rhsValue).longValue() ^ ((Number) lhsValue).longValue());
                }
                return Integer.valueOf(((Number) lhsValue).intValue() ^ ((Number) rhsValue).intValue());
            case 8:
                if (z3) {
                    return Long.valueOf(((Number) rhsValue).longValue() & ((Number) lhsValue).longValue());
                }
                return Integer.valueOf(((Number) lhsValue).intValue() & ((Number) rhsValue).intValue());
            case 9:
                if (z3) {
                    return Long.valueOf(((Number) rhsValue).longValue() | ((Number) lhsValue).longValue());
                }
                return Integer.valueOf(((Number) lhsValue).intValue() | ((Number) rhsValue).intValue());
            case 10:
                if (z2) {
                    if (z3) {
                        return Long.valueOf(((Number) lhsValue).longValue() + ((Number) rhsValue).longValue());
                    }
                    return Integer.valueOf(((Number) lhsValue).intValue() + ((Number) rhsValue).intValue());
                }
                if (z3) {
                    return Double.valueOf(((Number) lhsValue).doubleValue() + ((Number) rhsValue).doubleValue());
                }
                return Float.valueOf(((Number) lhsValue).floatValue() + ((Number) rhsValue).floatValue());
            case 11:
                if (z2) {
                    if (z3) {
                        return Long.valueOf(((Number) lhsValue).longValue() - ((Number) rhsValue).longValue());
                    }
                    return Integer.valueOf(((Number) lhsValue).intValue() - ((Number) rhsValue).intValue());
                }
                if (z3) {
                    return Double.valueOf(((Number) lhsValue).doubleValue() - ((Number) rhsValue).doubleValue());
                }
                return Float.valueOf(((Number) lhsValue).floatValue() - ((Number) rhsValue).floatValue());
            case 12:
                if (z2) {
                    if (z3) {
                        return Long.valueOf(((Number) lhsValue).longValue() * ((Number) rhsValue).longValue());
                    }
                    return Integer.valueOf(((Number) lhsValue).intValue() * ((Number) rhsValue).intValue());
                }
                if (z3) {
                    return Double.valueOf(((Number) lhsValue).doubleValue() * ((Number) rhsValue).doubleValue());
                }
                return Float.valueOf(((Number) lhsValue).floatValue() * ((Number) rhsValue).floatValue());
            case 13:
                if (z2) {
                    if (z3) {
                        return Long.valueOf(((Number) lhsValue).longValue() / ((Number) rhsValue).longValue());
                    }
                    return Integer.valueOf(((Number) lhsValue).intValue() / ((Number) rhsValue).intValue());
                }
                if (z3) {
                    return Double.valueOf(((Number) lhsValue).doubleValue() / ((Number) rhsValue).doubleValue());
                }
                return Float.valueOf(((Number) lhsValue).floatValue() / ((Number) rhsValue).floatValue());
            case 14:
                if (z2) {
                    if (z3) {
                        return Long.valueOf(((Number) lhsValue).longValue() % ((Number) rhsValue).longValue());
                    }
                    return Integer.valueOf(((Number) lhsValue).intValue() % ((Number) rhsValue).intValue());
                }
                if (z3) {
                    return Double.valueOf(((Number) lhsValue).doubleValue() % ((Number) rhsValue).doubleValue());
                }
                return Float.valueOf(((Number) lhsValue).floatValue() % ((Number) rhsValue).floatValue());
            case 15:
                if (z3) {
                    return Long.valueOf(((Number) lhsValue).longValue() >> ((Number) rhsValue).intValue());
                }
                return Integer.valueOf(((Number) lhsValue).intValue() >> ((Number) rhsValue).intValue());
            case 16:
                if (z3) {
                    return Long.valueOf(((Number) lhsValue).longValue() << ((Number) rhsValue).intValue());
                }
                return Integer.valueOf(((Number) lhsValue).intValue() << ((Number) rhsValue).intValue());
            case 17:
                if (z3) {
                    return Long.valueOf(((Number) lhsValue).longValue() >>> ((Number) rhsValue).intValue());
                }
                return Integer.valueOf(((Number) lhsValue).intValue() >>> ((Number) rhsValue).intValue());
            case 18:
                if (!z2 ? ((Number) lhsValue).doubleValue() < ((Number) rhsValue).doubleValue() : ((Number) lhsValue).longValue() < ((Number) rhsValue).longValue()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 19:
                if (!z2 ? ((Number) lhsValue).doubleValue() <= ((Number) rhsValue).doubleValue() : ((Number) lhsValue).longValue() <= ((Number) rhsValue).longValue()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 20:
                if (!z2 ? ((Number) lhsValue).doubleValue() > ((Number) rhsValue).doubleValue() : ((Number) lhsValue).longValue() > ((Number) rhsValue).longValue()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 21:
                if (!z2 ? ((Number) lhsValue).doubleValue() >= ((Number) rhsValue).doubleValue() : ((Number) lhsValue).longValue() >= ((Number) rhsValue).longValue()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            default:
                return null;
        }
    }
}
