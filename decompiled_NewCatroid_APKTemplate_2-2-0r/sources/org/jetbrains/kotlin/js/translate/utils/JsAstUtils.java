package org.jetbrains.kotlin.js.translate.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes5.dex */
public final class JsAstUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final JsNameRef DEFINE_PROPERTY;
    private static final JsPropertyInitializer ENUMERABLE;
    private static final JsNameRef VALUE;
    private static final JsPropertyInitializer WRITABLE;

    /* JADX WARN: Code duplicated, block: B:17:0x0033  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 7 && i != 8 && i != 15 && i != 16 && i != 93 && i != 94 && i != 97 && i != 98) {
            switch (i) {
                case 35:
                case 37:
                case 39:
                case 42:
                case 49:
                case 72:
                case 104:
                case 106:
                case 110:
                case 112:
                case 114:
                case 121:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 7 && i != 8 && i != 15 && i != 16 && i != 93 && i != 94 && i != 97 && i != 98) {
            switch (i) {
                case 35:
                case 37:
                case 39:
                case 42:
                case 49:
                case 72:
                case 104:
                case 106:
                case 110:
                case 112:
                case 114:
                case 121:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 15:
            case 16:
            case 35:
            case 37:
            case 39:
            case 42:
            case 49:
            case 72:
            case 93:
            case 94:
            case 97:
            case 98:
            case 104:
            case 106:
            case 110:
            case 112:
            case 114:
            case 121:
                objArr[0] = "org/jetbrains/kotlin/js/translate/utils/JsAstUtils";
                break;
            case 3:
            default:
                objArr[0] = "jsNode";
                break;
            case 6:
            case 13:
            case 17:
            case 109:
                objArr[0] = "statement";
                break;
            case 9:
            case 11:
                objArr[0] = "ifExpression";
                break;
            case 10:
            case 12:
                objArr[0] = "thenStatement";
                break;
            case 14:
                objArr[0] = "block";
                break;
            case 18:
            case 21:
            case 91:
            case 100:
            case 102:
            case 118:
                objArr[0] = "name";
                break;
            case 19:
                objArr[0] = "argument";
                break;
            case 20:
                objArr[0] = "thisObject";
                break;
            case 22:
                objArr[0] = "arguments";
                break;
            case 23:
            case 24:
            case 25:
            case 26:
            case 31:
            case 32:
            case 33:
            case 40:
            case 43:
            case 48:
            case 73:
            case 88:
            case 89:
            case 120:
                objArr[0] = "expression";
                break;
            case 27:
            case 29:
            case 46:
            case 68:
            case 76:
            case 78:
            case 80:
            case 82:
            case 84:
            case 86:
                objArr[0] = "left";
                break;
            case 28:
            case 30:
            case 47:
            case 69:
            case 71:
            case 77:
            case 79:
            case 81:
            case 83:
            case 85:
            case 87:
                objArr[0] = "right";
                break;
            case 34:
            case 36:
            case 38:
            case 41:
            case 44:
                objArr[0] = "context";
                break;
            case 45:
                objArr[0] = "unsignedClassId";
                break;
            case 50:
            case 52:
                objArr[0] = "op1";
                break;
            case 51:
            case 53:
                objArr[0] = "op2";
                break;
            case 54:
            case 55:
                objArr[0] = "selector";
                break;
            case 56:
            case 58:
            case 60:
            case 62:
            case 64:
            case 66:
                objArr[0] = "arg1";
                break;
            case 57:
            case 59:
            case 61:
            case 63:
            case 65:
            case 67:
                objArr[0] = "arg2";
                break;
            case 70:
                objArr[0] = "fieldName";
                break;
            case 74:
            case 75:
                objArr[0] = "expr";
                break;
            case 90:
                objArr[0] = "string";
                break;
            case 92:
                objArr[0] = "expressions";
                break;
            case 95:
                objArr[0] = "parent";
                break;
            case 96:
                objArr[0] = "strings";
                break;
            case 99:
            case 115:
            case 117:
                objArr[0] = "receiver";
                break;
            case 101:
            case 103:
            case 105:
            case 108:
                objArr[0] = "value";
                break;
            case 107:
                objArr[0] = CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME;
                break;
            case 111:
            case 113:
                objArr[0] = "identifier";
                break;
            case 116:
                objArr[0] = "method";
                break;
            case 119:
                objArr[0] = "body";
                break;
        }
        if (i != 1 && i != 2) {
            if (i != 4 && i != 5) {
                if (i != 7 && i != 8) {
                    if (i != 15 && i != 16) {
                        if (i != 93 && i != 94) {
                            if (i != 97 && i != 98) {
                                switch (i) {
                                    case 35:
                                        objArr[1] = "byteToUByte";
                                        break;
                                    case 37:
                                        objArr[1] = "shortToUShort";
                                        break;
                                    case 39:
                                        objArr[1] = "intToUInt";
                                        break;
                                    case 42:
                                        objArr[1] = "longToULong";
                                        break;
                                    case 49:
                                        objArr[1] = "notOptimized";
                                        break;
                                    case 72:
                                        objArr[1] = "assignmentToThisField";
                                        break;
                                    case 104:
                                        objArr[1] = "defineSimpleProperty";
                                        break;
                                    case 106:
                                        objArr[1] = "createDataDescriptor";
                                        break;
                                    case 110:
                                        objArr[1] = "flattenStatement";
                                        break;
                                    case 112:
                                    case 114:
                                        objArr[1] = "pureFqn";
                                        break;
                                    case 121:
                                        objArr[1] = "stateMachineReceiver";
                                        break;
                                    default:
                                        objArr[1] = "org/jetbrains/kotlin/js/translate/utils/JsAstUtils";
                                        break;
                                }
                            } else {
                                objArr[1] = "toStringLiteralList";
                            }
                        } else {
                            objArr[1] = "newSequence";
                        }
                    } else {
                        objArr[1] = "mergeStatementInBlockIfNeeded";
                    }
                } else {
                    objArr[1] = "deBlockIfPossible";
                }
            } else {
                objArr[1] = "convertToBlock";
            }
        } else {
            objArr[1] = "convertToStatement";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 15:
            case 16:
            case 35:
            case 37:
            case 39:
            case 42:
            case 49:
            case 72:
            case 93:
            case 94:
            case 97:
            case 98:
            case 104:
            case 106:
            case 110:
            case 112:
            case 114:
            case 121:
                break;
            case 3:
                objArr[2] = "convertToBlock";
                break;
            case 6:
                objArr[2] = "deBlockIfPossible";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "newJsIf";
                break;
            case 13:
            case 14:
                objArr[2] = "mergeStatementInBlockIfNeeded";
                break;
            case 17:
                objArr[2] = "isEmptyStatement";
                break;
            case 18:
            case 19:
                objArr[2] = "invokeKotlinFunction";
                break;
            case 20:
            case 21:
            case 22:
                objArr[2] = "invokeMethod";
                break;
            case 23:
                objArr[2] = "toInt32";
                break;
            case 24:
                objArr[2] = "extractToInt32Argument";
                break;
            case 25:
                objArr[2] = "charToInt";
                break;
            case 26:
                objArr[2] = "charToString";
                break;
            case 27:
            case 28:
                objArr[2] = "compareTo";
                break;
            case 29:
            case 30:
                objArr[2] = Namer.PRIMITIVE_COMPARE_TO;
                break;
            case 31:
                objArr[2] = "longFromInt";
                break;
            case 32:
                objArr[2] = "longFromNumber";
                break;
            case 33:
                objArr[2] = "longToNumber";
                break;
            case 34:
                objArr[2] = "byteToUByte";
                break;
            case 36:
                objArr[2] = "shortToUShort";
                break;
            case 38:
                objArr[2] = "intToUInt";
                break;
            case 40:
            case 41:
                objArr[2] = "longToULong";
                break;
            case 43:
            case 44:
            case 45:
                objArr[2] = "toUnsignedNumber";
                break;
            case 46:
            case 47:
                objArr[2] = "compareForObject";
                break;
            case 48:
                objArr[2] = "notOptimized";
                break;
            case 50:
            case 51:
                objArr[2] = "and";
                break;
            case 52:
            case 53:
                objArr[2] = "or";
                break;
            case 54:
                objArr[2] = "setQualifier";
                break;
            case 55:
                objArr[2] = "setQualifierForNameRef";
                break;
            case 56:
            case 57:
                objArr[2] = "equality";
                break;
            case 58:
            case 59:
                objArr[2] = "inequality";
                break;
            case 60:
            case 61:
                objArr[2] = "lessThanEq";
                break;
            case 62:
            case 63:
                objArr[2] = "lessThan";
                break;
            case 64:
            case 65:
                objArr[2] = "greaterThan";
                break;
            case 66:
            case 67:
                objArr[2] = "greaterThanEq";
                break;
            case 68:
            case 69:
                objArr[2] = "assignment";
                break;
            case 70:
            case 71:
                objArr[2] = "assignmentToThisField";
                break;
            case 73:
                objArr[2] = "asSyntheticStatement";
                break;
            case 74:
                objArr[2] = "decomposeAssignment";
                break;
            case 75:
                objArr[2] = "decomposeAssignmentToVariable";
                break;
            case 76:
            case 77:
                objArr[2] = "sum";
                break;
            case 78:
            case 79:
                objArr[2] = "addAssign";
                break;
            case 80:
            case 81:
                objArr[2] = "subtract";
                break;
            case 82:
            case 83:
                objArr[2] = "mul";
                break;
            case 84:
            case 85:
                objArr[2] = "div";
                break;
            case 86:
            case 87:
                objArr[2] = "mod";
                break;
            case 88:
                objArr[2] = "not";
                break;
            case 89:
            case 90:
                objArr[2] = "typeOfIs";
                break;
            case 91:
                objArr[2] = "newVar";
                break;
            case 92:
                objArr[2] = "newSequence";
                break;
            case 95:
                objArr[2] = "createFunctionWithEmptyBody";
                break;
            case 96:
                objArr[2] = "toStringLiteralList";
                break;
            case 99:
            case 100:
            case 101:
                objArr[2] = "defineProperty";
                break;
            case 102:
            case 103:
                objArr[2] = "defineSimpleProperty";
                break;
            case 105:
                objArr[2] = "createDataDescriptor";
                break;
            case 107:
            case 108:
                objArr[2] = "wrapValue";
                break;
            case 109:
                objArr[2] = "flattenStatement";
                break;
            case 111:
            case 113:
                objArr[2] = "pureFqn";
                break;
            case 115:
            case 116:
                objArr[2] = "invokeBind";
                break;
            case 117:
            case 118:
            case 119:
                objArr[2] = "defineGetter";
                break;
            case 120:
                objArr[2] = "prototypeOf";
                break;
            default:
                objArr[2] = "convertToStatement";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 7 && i != 8 && i != 15 && i != 16 && i != 93 && i != 94 && i != 97 && i != 98) {
            switch (i) {
                case 35:
                case 37:
                case 39:
                case 42:
                case 49:
                case 72:
                case 104:
                case 106:
                case 110:
                case 112:
                case 114:
                case 121:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        JsNameRef jsNameRefPureFqn = pureFqn("defineProperty", (JsExpression) null);
        DEFINE_PROPERTY = jsNameRefPureFqn;
        VALUE = new JsNameRef("value");
        WRITABLE = new JsPropertyInitializer(pureFqn("writable", (JsExpression) null), new JsBooleanLiteral(true));
        ENUMERABLE = new JsPropertyInitializer(pureFqn("enumerable", (JsExpression) null), new JsBooleanLiteral(false));
        jsNameRefPureFqn.setQualifier(new JsNameRef(CommonClassNames.JAVA_LANG_OBJECT_SHORT));
    }

    private JsAstUtils() {
    }

    public static JsStatement convertToStatement(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(0);
        }
        if (jsNode instanceof JsExpression) {
            JsExpression jsExpression = (JsExpression) jsNode;
            JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(jsExpression);
            if ((jsExpression instanceof JsNullLiteral) || MetadataProperties.getSynthetic(jsExpression)) {
                MetadataProperties.setSynthetic(jsExpressionStatement, true);
            }
            return jsExpressionStatement;
        }
        JsStatement jsStatement = (JsStatement) jsNode;
        if (jsStatement == null) {
            $$$reportNull$$$0(2);
        }
        return jsStatement;
    }

    public static JsBlock convertToBlock(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(3);
        }
        if (jsNode instanceof JsBlock) {
            JsBlock jsBlock = (JsBlock) jsNode;
            if (jsBlock == null) {
                $$$reportNull$$$0(4);
            }
            return jsBlock;
        }
        JsBlock jsBlock2 = new JsBlock();
        jsBlock2.getStatements().add(convertToStatement(jsNode));
        return jsBlock2;
    }

    private static JsStatement deBlockIfPossible(JsStatement jsStatement) {
        if (jsStatement == null) {
            $$$reportNull$$$0(6);
        }
        if (jsStatement instanceof JsBlock) {
            JsBlock jsBlock = (JsBlock) jsStatement;
            if (jsBlock.getStatements().size() == 1) {
                JsStatement jsStatement2 = jsBlock.getStatements().get(0);
                if (jsStatement2 == null) {
                    $$$reportNull$$$0(7);
                }
                return jsStatement2;
            }
        }
        if (jsStatement == null) {
            $$$reportNull$$$0(8);
        }
        return jsStatement;
    }

    public static JsIf newJsIf(JsExpression jsExpression, JsStatement jsStatement, JsStatement jsStatement2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(9);
        }
        if (jsStatement == null) {
            $$$reportNull$$$0(10);
        }
        return new JsIf(jsExpression, deBlockIfPossible(jsStatement), jsStatement2 != null ? deBlockIfPossible(jsStatement2) : null);
    }

    public static JsIf newJsIf(JsExpression jsExpression, JsStatement jsStatement) {
        if (jsExpression == null) {
            $$$reportNull$$$0(11);
        }
        if (jsStatement == null) {
            $$$reportNull$$$0(12);
        }
        return newJsIf(jsExpression, jsStatement, null);
    }

    public static JsExpression extractExpressionFromStatement(JsStatement jsStatement) {
        if (jsStatement instanceof JsExpressionStatement) {
            return ((JsExpressionStatement) jsStatement).getExpression();
        }
        return null;
    }

    public static JsStatement mergeStatementInBlockIfNeeded(JsStatement jsStatement, JsBlock jsBlock) {
        if (jsStatement == null) {
            $$$reportNull$$$0(13);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(14);
        }
        if (jsBlock.isEmpty()) {
            if (jsStatement == null) {
                $$$reportNull$$$0(15);
            }
            return jsStatement;
        }
        if (isEmptyStatement(jsStatement)) {
            return deBlockIfPossible(jsBlock);
        }
        jsBlock.getStatements().add(jsStatement);
        if (jsBlock == null) {
            $$$reportNull$$$0(16);
        }
        return jsBlock;
    }

    public static boolean isEmptyStatement(JsStatement jsStatement) {
        if (jsStatement == null) {
            $$$reportNull$$$0(17);
        }
        return jsStatement instanceof JsEmpty;
    }

    public static JsInvocation invokeKotlinFunction(String str, JsExpression... jsExpressionArr) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        if (jsExpressionArr == null) {
            $$$reportNull$$$0(19);
        }
        return invokeMethod(Namer.kotlinObject(), str, jsExpressionArr);
    }

    public static JsInvocation invokeMethod(JsExpression jsExpression, String str, JsExpression... jsExpressionArr) {
        if (jsExpression == null) {
            $$$reportNull$$$0(20);
        }
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        if (jsExpressionArr == null) {
            $$$reportNull$$$0(22);
        }
        return new JsInvocation(pureFqn(str, jsExpression), jsExpressionArr);
    }

    public static JsExpression toInt32(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(23);
        }
        return new JsBinaryOperation(JsBinaryOperator.BIT_OR, jsExpression, new JsIntLiteral(0));
    }

    public static JsExpression extractToInt32Argument(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(24);
        }
        if (!(jsExpression instanceof JsBinaryOperation)) {
            return null;
        }
        JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) jsExpression;
        if (jsBinaryOperation.getOperator() == JsBinaryOperator.BIT_OR && (jsBinaryOperation.getArg2() instanceof JsIntLiteral) && ((JsIntLiteral) jsBinaryOperation.getArg2()).value == 0) {
            return jsBinaryOperation.getArg1();
        }
        return null;
    }

    public static JsExpression charToInt(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(25);
        }
        return toInt32(jsExpression);
    }

    public static JsExpression charToString(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(26);
        }
        return new JsInvocation(new JsNameRef("fromCharCode", new JsNameRef(CommonClassNames.JAVA_LANG_STRING_SHORT)), jsExpression);
    }

    public static JsExpression compareTo(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(27);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(28);
        }
        return invokeKotlinFunction(OperatorNameConventions.COMPARE_TO.getIdentifier(), jsExpression, jsExpression2);
    }

    public static JsExpression primitiveCompareTo(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(29);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(30);
        }
        return invokeKotlinFunction(Namer.PRIMITIVE_COMPARE_TO, jsExpression, jsExpression2);
    }

    public static JsExpression newLong(long j) {
        JsExpression jsNew;
        if (j < -2147483648L || j > 2147483647L) {
            if (j == Long.MAX_VALUE) {
                return new JsNameRef(Namer.LONG_MAX_VALUE, Namer.kotlinLong());
            }
            if (j == Long.MIN_VALUE) {
                return new JsNameRef(Namer.LONG_MIN_VALUE, Namer.kotlinLong());
            }
            SmartList smartList = new SmartList();
            smartList.add(new JsIntLiteral((int) j));
            smartList.add(new JsIntLiteral((int) (j >> 32)));
            jsNew = new JsNew(Namer.kotlinLong(), smartList);
        } else if (j == 0) {
            jsNew = new JsNameRef(Namer.LONG_ZERO, Namer.kotlinLong());
        } else if (j == 1) {
            jsNew = new JsNameRef(Namer.LONG_ONE, Namer.kotlinLong());
        } else if (j == -1) {
            jsNew = new JsNameRef(Namer.LONG_NEG_ONE, Namer.kotlinLong());
        } else {
            jsNew = longFromInt(new JsIntLiteral((int) j));
        }
        MetadataProperties.setSideEffects(jsNew, SideEffectKind.PURE);
        return jsNew;
    }

    public static JsExpression longFromInt(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(31);
        }
        return invokeMethod(Namer.kotlinLong(), Namer.LONG_FROM_INT, jsExpression);
    }

    public static JsExpression longFromNumber(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(32);
        }
        return invokeMethod(Namer.kotlinLong(), Namer.LONG_FROM_NUMBER, jsExpression);
    }

    public static JsExpression longToNumber(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(33);
        }
        return invokeMethod(jsExpression, Namer.LONG_TO_NUMBER, new JsExpression[0]);
    }

    public static JsExpression byteToUByte(byte b, TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(34);
        }
        JsExpression unsignedNumber = toUnsignedNumber(new JsIntLiteral(b), translationContext, StandardNames.FqNames.uByte);
        if (unsignedNumber == null) {
            $$$reportNull$$$0(35);
        }
        return unsignedNumber;
    }

    public static JsExpression shortToUShort(short s, TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(36);
        }
        JsExpression unsignedNumber = toUnsignedNumber(new JsIntLiteral(s), translationContext, StandardNames.FqNames.uShort);
        if (unsignedNumber == null) {
            $$$reportNull$$$0(37);
        }
        return unsignedNumber;
    }

    public static JsExpression intToUInt(int i, TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(38);
        }
        JsExpression unsignedNumber = toUnsignedNumber(new JsIntLiteral(i), translationContext, StandardNames.FqNames.uInt);
        if (unsignedNumber == null) {
            $$$reportNull$$$0(39);
        }
        return unsignedNumber;
    }

    public static JsExpression longToULong(JsExpression jsExpression, TranslationContext translationContext) {
        if (jsExpression == null) {
            $$$reportNull$$$0(40);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(41);
        }
        JsExpression unsignedNumber = toUnsignedNumber(jsExpression, translationContext, StandardNames.FqNames.uLong);
        if (unsignedNumber == null) {
            $$$reportNull$$$0(42);
        }
        return unsignedNumber;
    }

    private static JsExpression toUnsignedNumber(JsExpression jsExpression, TranslationContext translationContext, ClassId classId) {
        if (jsExpression == null) {
            $$$reportNull$$$0(43);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(44);
        }
        if (classId == null) {
            $$$reportNull$$$0(45);
        }
        return new JsNew(ReferenceTranslator.translateAsTypeReference(FindClassInModuleKt.findClassAcrossModuleDependencies(translationContext.getCurrentModule(), classId), translationContext), Collections.singletonList(jsExpression));
    }

    public static JsExpression compareForObject(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(46);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(47);
        }
        return invokeMethod(jsExpression, Namer.COMPARE_TO_METHOD_NAME, jsExpression2);
    }

    public static JsExpression notOptimized(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(48);
        }
        if (jsExpression instanceof JsUnaryOperation) {
            JsUnaryOperation jsUnaryOperation = (JsUnaryOperation) jsExpression;
            if (jsUnaryOperation.getOperator() == JsUnaryOperator.NOT) {
                JsExpression arg = jsUnaryOperation.getArg();
                if (arg == null) {
                    $$$reportNull$$$0(49);
                }
                return arg;
            }
        } else if (jsExpression instanceof JsBinaryOperation) {
            JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) jsExpression;
            switch (AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[jsBinaryOperation.getOperator().ordinal()]) {
                case 1:
                    return or(notOptimized(jsBinaryOperation.getArg1()), notOptimized(jsBinaryOperation.getArg2()));
                case 2:
                    return and(notOptimized(jsBinaryOperation.getArg1()), notOptimized(jsBinaryOperation.getArg2()));
                case 3:
                    return new JsBinaryOperation(JsBinaryOperator.NEQ, jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
                case 4:
                    return new JsBinaryOperation(JsBinaryOperator.EQ, jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
                case 5:
                    return inequality(jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
                case 6:
                    return equality(jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
                case 7:
                    return greaterThanEq(jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
                case 8:
                    return greaterThan(jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
                case 9:
                    return lessThanEq(jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
                case 10:
                    return lessThan(jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
            }
        }
        return not(jsExpression);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.translate.utils.JsAstUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator;

        static {
            int[] iArr = new int[JsBinaryOperator.values().length];
            $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator = iArr;
            try {
                iArr[JsBinaryOperator.AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.EQ.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.NEQ.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.REF_EQ.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.REF_NEQ.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.LT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.LTE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.GT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.GTE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public static JsBinaryOperation and(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(50);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(51);
        }
        return new JsBinaryOperation(JsBinaryOperator.AND, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation or(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(52);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(53);
        }
        return new JsBinaryOperation(JsBinaryOperator.OR, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation equality(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(56);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(57);
        }
        return new JsBinaryOperation(JsBinaryOperator.REF_EQ, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation inequality(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(58);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(59);
        }
        return new JsBinaryOperation(JsBinaryOperator.REF_NEQ, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation lessThanEq(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(60);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(61);
        }
        return new JsBinaryOperation(JsBinaryOperator.LTE, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation lessThan(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(62);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(63);
        }
        return new JsBinaryOperation(JsBinaryOperator.LT, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation greaterThan(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(64);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(65);
        }
        return new JsBinaryOperation(JsBinaryOperator.GT, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation greaterThanEq(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(66);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(67);
        }
        return new JsBinaryOperation(JsBinaryOperator.GTE, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation assignment(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(68);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(69);
        }
        return new JsBinaryOperation(JsBinaryOperator.ASG, jsExpression, jsExpression2);
    }

    public static JsStatement assignmentToThisField(String str, JsExpression jsExpression) {
        if (str == null) {
            $$$reportNull$$$0(70);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(71);
        }
        JsStatement jsStatementMakeStmt = assignment(new JsNameRef(str, new JsThisRef()), jsExpression).source(jsExpression.getSource()).makeStmt();
        if (jsStatementMakeStmt == null) {
            $$$reportNull$$$0(72);
        }
        return jsStatementMakeStmt;
    }

    public static JsStatement asSyntheticStatement(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(73);
        }
        JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(jsExpression);
        MetadataProperties.setSynthetic(jsExpressionStatement, true);
        return jsExpressionStatement;
    }

    public static Pair<JsExpression, JsExpression> decomposeAssignment(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(74);
        }
        if (!(jsExpression instanceof JsBinaryOperation)) {
            return null;
        }
        JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) jsExpression;
        if (jsBinaryOperation.getOperator() != JsBinaryOperator.ASG) {
            return null;
        }
        return new Pair<>(jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
    }

    public static Pair<JsName, JsExpression> decomposeAssignmentToVariable(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(75);
        }
        Pair<JsExpression, JsExpression> pairDecomposeAssignment = decomposeAssignment(jsExpression);
        if (pairDecomposeAssignment == null || !(pairDecomposeAssignment.getFirst() instanceof JsNameRef)) {
            return null;
        }
        JsNameRef jsNameRef = (JsNameRef) pairDecomposeAssignment.getFirst();
        if (jsNameRef.getName() == null || jsNameRef.getQualifier() != null) {
            return null;
        }
        return new Pair<>(jsNameRef.getName(), pairDecomposeAssignment.getSecond());
    }

    public static JsBinaryOperation sum(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(76);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(77);
        }
        return new JsBinaryOperation(JsBinaryOperator.ADD, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation addAssign(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(78);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(79);
        }
        return new JsBinaryOperation(JsBinaryOperator.ASG_ADD, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation subtract(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(80);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(81);
        }
        return new JsBinaryOperation(JsBinaryOperator.SUB, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation mul(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(82);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(83);
        }
        return new JsBinaryOperation(JsBinaryOperator.MUL, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation div(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(84);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(85);
        }
        return new JsBinaryOperation(JsBinaryOperator.DIV, jsExpression, jsExpression2);
    }

    public static JsBinaryOperation mod(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(86);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(87);
        }
        return new JsBinaryOperation(JsBinaryOperator.MOD, jsExpression, jsExpression2);
    }

    public static JsPrefixOperation not(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(88);
        }
        return new JsPrefixOperation(JsUnaryOperator.NOT, jsExpression);
    }

    public static JsBinaryOperation typeOfIs(JsExpression jsExpression, JsStringLiteral jsStringLiteral) {
        if (jsExpression == null) {
            $$$reportNull$$$0(89);
        }
        if (jsStringLiteral == null) {
            $$$reportNull$$$0(90);
        }
        return equality(new JsPrefixOperation(JsUnaryOperator.TYPEOF, jsExpression), jsStringLiteral);
    }

    public static JsVars newVar(JsName jsName, JsExpression jsExpression) {
        if (jsName == null) {
            $$$reportNull$$$0(91);
        }
        return new JsVars(new JsVars.JsVar(jsName, jsExpression));
    }

    public static JsExpression newSequence(List<JsExpression> list) {
        if (list == null) {
            $$$reportNull$$$0(92);
        }
        int i = 1;
        if (list.size() == 1) {
            JsExpression jsExpression = list.get(0);
            if (jsExpression == null) {
                $$$reportNull$$$0(93);
            }
            return jsExpression;
        }
        JsExpression jsExpression2 = list.get(0);
        while (i < list.size()) {
            JsBinaryOperation jsBinaryOperation = new JsBinaryOperation(JsBinaryOperator.COMMA, jsExpression2, list.get(i));
            i++;
            jsExpression2 = jsBinaryOperation;
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(94);
        }
        return jsExpression2;
    }

    public static JsFunction createFunctionWithEmptyBody(JsScope jsScope) {
        if (jsScope == null) {
            $$$reportNull$$$0(95);
        }
        return new JsFunction(jsScope, new JsBlock(), SpecialNames.ANONYMOUS_STRING);
    }

    public static List<JsExpression> toStringLiteralList(List<String> list) {
        if (list == null) {
            $$$reportNull$$$0(96);
        }
        if (list.isEmpty()) {
            List<JsExpression> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(97);
            }
            return listEmptyList;
        }
        SmartList smartList = new SmartList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            smartList.add(new JsStringLiteral(it.next()));
        }
        return smartList;
    }

    public static JsInvocation defineProperty(JsExpression jsExpression, String str, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(99);
        }
        if (str == null) {
            $$$reportNull$$$0(100);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(101);
        }
        return new JsInvocation(DEFINE_PROPERTY.deepCopy(), jsExpression, new JsStringLiteral(str), jsExpression2);
    }

    public static JsStatement defineSimpleProperty(JsName jsName, JsExpression jsExpression, SourceElement sourceElement) {
        if (jsName == null) {
            $$$reportNull$$$0(102);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(103);
        }
        JsBinaryOperation jsBinaryOperationAssignment = assignment(new JsNameRef(jsName, new JsThisRef()), jsExpression);
        if (sourceElement != null) {
            jsBinaryOperationAssignment.setSource(PsiSourceElementKt.getPsi(sourceElement));
        }
        JsStatement jsStatementMakeStmt = jsBinaryOperationAssignment.makeStmt();
        if (jsStatementMakeStmt == null) {
            $$$reportNull$$$0(104);
        }
        return jsStatementMakeStmt;
    }

    public static JsObjectLiteral createDataDescriptor(JsExpression jsExpression, boolean z, boolean z2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(105);
        }
        JsObjectLiteral jsObjectLiteral = new JsObjectLiteral();
        jsObjectLiteral.getPropertyInitializers().add(new JsPropertyInitializer(VALUE.deepCopy(), jsExpression));
        if (z) {
            jsObjectLiteral.getPropertyInitializers().add(WRITABLE.deepCopy());
        }
        if (z2) {
            jsObjectLiteral.getPropertyInitializers().add(ENUMERABLE.deepCopy());
        }
        return jsObjectLiteral;
    }

    public static JsObjectLiteral wrapValue(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(107);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(108);
        }
        return new JsObjectLiteral((List<JsPropertyInitializer>) Collections.singletonList(new JsPropertyInitializer(jsExpression, jsExpression2)));
    }

    public static List<JsStatement> flattenStatement(JsStatement jsStatement) {
        if (jsStatement == null) {
            $$$reportNull$$$0(109);
        }
        if (jsStatement instanceof JsBlock) {
            List<JsStatement> statements = ((JsBlock) jsStatement).getStatements();
            if (statements == null) {
                $$$reportNull$$$0(110);
            }
            return statements;
        }
        return new SmartList(jsStatement);
    }

    public static JsNameRef pureFqn(String str, JsExpression jsExpression) {
        if (str == null) {
            $$$reportNull$$$0(111);
        }
        JsNameRef jsNameRef = new JsNameRef(str, jsExpression);
        MetadataProperties.setSideEffects(jsNameRef, SideEffectKind.PURE);
        return jsNameRef;
    }

    public static JsNameRef pureFqn(JsName jsName, JsExpression jsExpression) {
        if (jsName == null) {
            $$$reportNull$$$0(113);
        }
        JsNameRef jsNameRef = new JsNameRef(jsName, jsExpression);
        MetadataProperties.setSideEffects(jsNameRef, SideEffectKind.PURE);
        return jsNameRef;
    }

    public static JsInvocation invokeBind(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(115);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(116);
        }
        return invokeMethod(jsExpression2, "bind", jsExpression);
    }

    public static boolean isUndefinedExpression(JsExpression jsExpression) {
        return (jsExpression instanceof JsUnaryOperation) && ((JsUnaryOperation) jsExpression).getOperator() == JsUnaryOperator.VOID;
    }

    public static JsExpression defineGetter(JsExpression jsExpression, String str, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(117);
        }
        if (str == null) {
            $$$reportNull$$$0(118);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(119);
        }
        JsObjectLiteral jsObjectLiteral = new JsObjectLiteral(true);
        jsObjectLiteral.getPropertyInitializers().add(new JsPropertyInitializer(new JsNameRef("get"), jsExpression2));
        return defineProperty(jsExpression, str, jsObjectLiteral);
    }

    public static JsExpression prototypeOf(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(120);
        }
        return pureFqn("prototype", jsExpression);
    }

    public static JsExpression stateMachineReceiver() {
        JsNameRef jsNameRefPureFqn = pureFqn(AsmUtil.LABELED_THIS_PARAMETER, (JsExpression) null);
        MetadataProperties.setCoroutineReceiver(jsNameRefPureFqn, true);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(121);
        }
        return jsNameRefPureFqn;
    }
}
