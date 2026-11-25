package org.jetbrains.kotlin.resolve.constants.evaluate;

import io.netty.util.internal.StringUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.BooleanValue;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ConstantExpressionEvaluator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a \u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a0\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0006\u001a@\u0010\u0010\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002\u001a \u0010\u0017\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0006\u001a0\u0010\u001a\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002\u001a\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001fH\u0002\u001a\u0010\u0010 \u001a\u00020!2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006\u001a\u0012\u0010\"\u001a\u00020!2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006H\u0002\u001a\u0010\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u0002H\u0002\u001a\u000e\u0010%\u001a\u00020!*\u0006\u0012\u0002\b\u00030&\u001a\u000e\u0010%\u001a\u00020!*\u0006\u0012\u0002\b\u00030\u0004\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"DIVISION_OPERATION_NAMES", "", "", "createCompileTimeConstantForCompareTo", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "result", "", "operationReference", "Lorg/jetbrains/kotlin/psi/KtExpression;", "createCompileTimeConstantForEquals", "evaluateBinary", "name", "receiverTypeStr", "receiverValue", "parameterTypeStr", "parameterValue", "evaluateBinaryAndCheck", "receiverType", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/CompileTimeType;", "parameterType", "reportIntegerOverflow", "Lkotlin/Function0;", "", "evaluateUnary", "typeStr", "value", "evaluateUnaryAndCheck", ModuleXmlParser.TYPE, "getReceiverExpressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "isIntegerType", "", "isZero", "typeStrToCompileTimeType", "str", "isStandaloneOnlyConstant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstantExpressionEvaluatorKt {
    private static final Set<String> DIVISION_OPERATION_NAMES;

    /* JADX INFO: compiled from: ConstantExpressionEvaluator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ExplicitReceiverKind.values().length];
            try {
                iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ExplicitReceiverKind.NO_EXPLICIT_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        List listListOf = CollectionsKt.listOf((Object[]) new Name[]{OperatorNameConventions.DIV, OperatorNameConventions.REM, OperatorNameConventions.MOD});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            arrayList.add(((Name) it.next()).asString());
        }
        DIVISION_OPERATION_NAMES = CollectionsKt.toSet(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConstantValue<?> createCompileTimeConstantForEquals(Object obj, KtExpression ktExpression) {
        boolean zBooleanValue;
        if (!(obj instanceof Boolean)) {
            return null;
        }
        boolean z = ktExpression instanceof KtSimpleNameExpression;
        Intrinsics.checkNotNull(ktExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSimpleNameExpression");
        KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) ktExpression;
        IElementType referencedNameElementType = ktSimpleNameExpression.getReferencedNameElementType();
        if (Intrinsics.areEqual(referencedNameElementType, KtTokens.EQEQ)) {
            zBooleanValue = ((Boolean) obj).booleanValue();
        } else if (Intrinsics.areEqual(referencedNameElementType, KtTokens.EXCLEQ)) {
            zBooleanValue = !((Boolean) obj).booleanValue();
        } else if (Intrinsics.areEqual(referencedNameElementType, KtTokens.IDENTIFIER)) {
            Intrinsics.areEqual(ktSimpleNameExpression.getReferencedNameAsName(), OperatorNameConventions.EQUALS);
            zBooleanValue = ((Boolean) obj).booleanValue();
        } else {
            throw new IllegalStateException("Unknown equals operation token: " + referencedNameElementType + StringUtil.SPACE + ktSimpleNameExpression.getText());
        }
        return new BooleanValue(zBooleanValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConstantValue<?> createCompileTimeConstantForCompareTo(Object obj, KtExpression ktExpression) {
        if (!(obj instanceof Integer)) {
            return null;
        }
        boolean z = ktExpression instanceof KtSimpleNameExpression;
        Intrinsics.checkNotNull(ktExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSimpleNameExpression");
        KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) ktExpression;
        IElementType referencedNameElementType = ktSimpleNameExpression.getReferencedNameElementType();
        if (Intrinsics.areEqual(referencedNameElementType, KtTokens.LT)) {
            return new BooleanValue(((Number) obj).intValue() < 0);
        }
        if (Intrinsics.areEqual(referencedNameElementType, KtTokens.LTEQ)) {
            return new BooleanValue(((Number) obj).intValue() <= 0);
        }
        if (Intrinsics.areEqual(referencedNameElementType, KtTokens.GT)) {
            return new BooleanValue(((Number) obj).intValue() > 0);
        }
        if (Intrinsics.areEqual(referencedNameElementType, KtTokens.GTEQ)) {
            return new BooleanValue(((Number) obj).intValue() >= 0);
        }
        if (Intrinsics.areEqual(referencedNameElementType, KtTokens.IDENTIFIER)) {
            Intrinsics.areEqual(ktSimpleNameExpression.getReferencedNameAsName(), OperatorNameConventions.COMPARE_TO);
            return new IntValue(((Number) obj).intValue());
        }
        throw new IllegalStateException("Unknown compareTo operation token: " + referencedNameElementType);
    }

    public static final boolean isIntegerType(Object obj) {
        return (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KotlinType getReceiverExpressionType(ResolvedCall<?> resolvedCall) {
        int i = WhenMappings.$EnumSwitchMapping$0[resolvedCall.getExplicitReceiverKind().ordinal()];
        if (i == 1) {
            ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            return dispatchReceiver.getType();
        }
        if (i != 2) {
            return null;
        }
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        return extensionReceiver.getType();
    }

    public static final boolean isStandaloneOnlyConstant(ConstantValue<?> constantValue) {
        Intrinsics.checkNotNullParameter(constantValue, "<this>");
        return (constantValue instanceof KClassValue) || (constantValue instanceof EnumValue) || (constantValue instanceof AnnotationValue) || (constantValue instanceof ArrayValue);
    }

    public static final boolean isStandaloneOnlyConstant(CompileTimeConstant<?> compileTimeConstant) {
        Intrinsics.checkNotNullParameter(compileTimeConstant, "<this>");
        if (compileTimeConstant instanceof TypedCompileTimeConstant) {
            return isStandaloneOnlyConstant((ConstantValue<?>) ((TypedCompileTimeConstant) compileTimeConstant).getConstantValue());
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isZero(Object obj) {
        if (isIntegerType(obj)) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
            if (((Number) obj).longValue() == 0) {
                return true;
            }
        } else if (((obj instanceof Float) || (obj instanceof Double)) && ((Number) obj).doubleValue() == 0.0d) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static final CompileTimeType typeStrToCompileTimeType(String str) {
        switch (str.hashCode()) {
            case -1808118735:
                if (str.equals(CommonClassNames.JAVA_LANG_STRING_SHORT)) {
                    return CompileTimeType.STRING;
                }
                break;
            case 65996:
                if (str.equals("Any")) {
                    return CompileTimeType.ANY;
                }
                break;
            case 73679:
                if (str.equals("Int")) {
                    return CompileTimeType.INT;
                }
                break;
            case 2086184:
                if (str.equals("Byte")) {
                    return CompileTimeType.BYTE;
                }
                break;
            case 2099062:
                if (str.equals("Char")) {
                    return CompileTimeType.CHAR;
                }
                break;
            case 2374300:
                if (str.equals("Long")) {
                    return CompileTimeType.LONG;
                }
                break;
            case 67973692:
                if (str.equals("Float")) {
                    return CompileTimeType.FLOAT;
                }
                break;
            case 79860828:
                if (str.equals("Short")) {
                    return CompileTimeType.SHORT;
                }
                break;
            case 1729365000:
                if (str.equals("Boolean")) {
                    return CompileTimeType.BOOLEAN;
                }
                break;
            case 2052876273:
                if (str.equals("Double")) {
                    return CompileTimeType.DOUBLE;
                }
                break;
        }
        throw new IllegalArgumentException("Unsupported type: " + str);
    }

    public static final Object evaluateUnary(String name2, String typeStr, Object value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(typeStr, "typeStr");
        Intrinsics.checkNotNullParameter(value, "value");
        return OperationsMapGeneratedKt.evalUnaryOp(name2, typeStrToCompileTimeType(typeStr), value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object evaluateUnaryAndCheck(String str, CompileTimeType compileTimeType, Object obj, Function0<Unit> function0) {
        Object objEvalUnaryOp = OperationsMapGeneratedKt.evalUnaryOp(str, compileTimeType, obj);
        if (isIntegerType(obj) && ((Intrinsics.areEqual(str, "minus") || Intrinsics.areEqual(str, "unaryMinus")) && Intrinsics.areEqual(obj, objEvalUnaryOp) && !isZero(obj))) {
            function0.invoke();
        }
        return objEvalUnaryOp;
    }

    public static final Object evaluateBinary(String name2, String receiverTypeStr, Object receiverValue, String parameterTypeStr, Object parameterValue) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(receiverTypeStr, "receiverTypeStr");
        Intrinsics.checkNotNullParameter(receiverValue, "receiverValue");
        Intrinsics.checkNotNullParameter(parameterTypeStr, "parameterTypeStr");
        Intrinsics.checkNotNullParameter(parameterValue, "parameterValue");
        try {
            return OperationsMapGeneratedKt.evalBinaryOp(name2, typeStrToCompileTimeType(receiverTypeStr), receiverValue, typeStrToCompileTimeType(parameterTypeStr), parameterValue);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object evaluateBinaryAndCheck(String str, CompileTimeType compileTimeType, Object obj, CompileTimeType compileTimeType2, Object obj2, Function0<Unit> function0) {
        Object objEvalBinaryOp;
        try {
            objEvalBinaryOp = OperationsMapGeneratedKt.evalBinaryOp(str, compileTimeType, obj, compileTimeType2, obj2);
        } catch (Exception unused) {
            objEvalBinaryOp = null;
        }
        if (objEvalBinaryOp != null && isIntegerType(obj) && isIntegerType(obj2)) {
            BigInteger bigIntegerEvaluateBinaryAndCheck$toBigInteger = evaluateBinaryAndCheck$toBigInteger(obj);
            Intrinsics.checkNotNullExpressionValue(bigIntegerEvaluateBinaryAndCheck$toBigInteger, "evaluateBinaryAndCheck$toBigInteger(...)");
            BigInteger bigIntegerEvaluateBinaryAndCheck$toBigInteger2 = evaluateBinaryAndCheck$toBigInteger(obj2);
            Intrinsics.checkNotNullExpressionValue(bigIntegerEvaluateBinaryAndCheck$toBigInteger2, "evaluateBinaryAndCheck$toBigInteger(...)");
            BigInteger bigIntegerCheckBinaryOp = OperationsMapGeneratedKt.checkBinaryOp(str, compileTimeType, bigIntegerEvaluateBinaryAndCheck$toBigInteger, compileTimeType2, bigIntegerEvaluateBinaryAndCheck$toBigInteger2);
            if (bigIntegerCheckBinaryOp != null && !Intrinsics.areEqual(evaluateBinaryAndCheck$toBigInteger(objEvalBinaryOp), bigIntegerCheckBinaryOp)) {
                function0.invoke();
            }
        }
        return objEvalBinaryOp;
    }

    private static final BigInteger evaluateBinaryAndCheck$toBigInteger(Object obj) {
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
        return BigInteger.valueOf(((Number) obj).longValue());
    }
}
