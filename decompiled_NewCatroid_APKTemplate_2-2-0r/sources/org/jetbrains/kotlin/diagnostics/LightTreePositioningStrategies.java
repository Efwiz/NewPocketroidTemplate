package org.jetbrains.kotlin.diagnostics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.stubs.elements.KtTokenSets;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: LightTreePositioningStrategies.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000;\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\\\n\u0002\bD\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001a\bÆ\u0002\u0018\u00002\u00020\u0001:\b¯\u0001°\u0001±\u0001²\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010¬\u0001\u001a\u0005\u0018\u00010ª\u0001*\n\u0012\u0005\u0012\u00030ª\u00010\u00ad\u00012\b\u0010®\u0001\u001a\u00030ª\u0001H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0017\u0010\u0011\u001a\u00020\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0012\u0010\u0002\u001a\u0004\b\u0013\u0010\u0006R\u0011\u0010\u0014\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0006R\u0011\u0010\u0016\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0006R\u0011\u0010\u0018\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0006R\u0011\u0010\u001a\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0006R\u0011\u0010\u001c\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0006R\u0011\u0010\u001e\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0006R\u0011\u0010 \u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0006R\u0011\u0010\"\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0006R\u0011\u0010$\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0006R\u0011\u0010&\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0006R\u0011\u0010(\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0006R\u0011\u0010*\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0006R\u0011\u0010,\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0006R\u0011\u0010.\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u0006R\u0011\u00100\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0006R\u0011\u00102\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u0010\u0006R\u0011\u00104\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u0006R\u0011\u00106\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\u0006R\u0011\u00108\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\u0006R\u0011\u0010:\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\u0006R\u0011\u0010<\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b=\u0010\u0006R\u0011\u0010>\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b?\u0010\u0006R\u0011\u0010@\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bA\u0010\u0006R\u0011\u0010B\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u0006R\u0011\u0010D\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bE\u0010\u0006R\u0011\u0010F\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010\u0006R\u0011\u0010H\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bI\u0010\u0006R\u0011\u0010J\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bK\u0010\u0006R\u0011\u0010L\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bM\u0010\u0006R\u0011\u0010N\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bO\u0010\u0006R\u0011\u0010P\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010\u0006R\u0011\u0010R\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bS\u0010\u0006R\u0011\u0010T\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bU\u0010\u0006R\u0011\u0010V\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bW\u0010\u0006R\u0011\u0010X\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bY\u0010\u0006R\u0011\u0010Z\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b[\u0010\u0006R\u0011\u0010\\\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b]\u0010\u0006R\u0011\u0010^\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b_\u0010\u0006R\u0010\u0010`\u001a\u00020aX\u0082\u0004¢\u0006\u0004\n\u0002\u0010bR\u0011\u0010c\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bd\u0010\u0006R\u0011\u0010e\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u0010\u0006R\u0011\u0010g\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bh\u0010\u0006R\u0011\u0010i\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bj\u0010\u0006R\u0011\u0010k\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bl\u0010\u0006R\u0011\u0010m\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bn\u0010\u0006R\u0011\u0010o\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bp\u0010\u0006R\u0011\u0010q\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\br\u0010\u0006R\u0011\u0010s\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u0010\u0006R\u0011\u0010u\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bv\u0010\u0006R\u0011\u0010w\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bx\u0010\u0006R\u0011\u0010y\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bz\u0010\u0006R\u0011\u0010{\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b|\u0010\u0006R\u0011\u0010}\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b~\u0010\u0006R\u0012\u0010\u007f\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0001\u0010\u0006R\u0013\u0010\u0081\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0001\u0010\u0006R\u0013\u0010\u0083\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u0006R\u0013\u0010\u0085\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0001\u0010\u0006R\u0013\u0010\u0087\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0001\u0010\u0006R\u0013\u0010\u0089\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0001\u0010\u0006R\u000f\u0010\u008b\u0001\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u008c\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010\u0006R\u0013\u0010\u008e\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008f\u0001\u0010\u0006R\u0013\u0010\u0090\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0091\u0001\u0010\u0006R\u0013\u0010\u0092\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0093\u0001\u0010\u0006R\u0013\u0010\u0094\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0095\u0001\u0010\u0006R\u0013\u0010\u0096\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0097\u0001\u0010\u0006R\u0013\u0010\u0098\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0099\u0001\u0010\u0006R\u0013\u0010\u009a\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009b\u0001\u0010\u0006R\u0013\u0010\u009c\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009d\u0001\u0010\u0006R\u0013\u0010\u009e\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009f\u0001\u0010\u0006R\u0013\u0010 \u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b¡\u0001\u0010\u0006R\u0013\u0010¢\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b£\u0001\u0010\u0006R!\u0010¤\u0001\u001a\u0014\u0012\u000f\u0012\r §\u0001*\u0005\u0018\u00010¦\u00010¦\u00010¥\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010¨\u0001\u001a\u00030©\u0001*\u00030ª\u00018BX\u0082\u0004¢\u0006\b\u001a\u0006\b¨\u0001\u0010«\u0001¨\u0006³\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategies;", "", "()V", "ABSTRACT_MODIFIER", "Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;", "getABSTRACT_MODIFIER", "()Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;", "ACTUAL_DECLARATION_NAME", "getACTUAL_DECLARATION_NAME", "ANNOTATION_USE_SITE", "getANNOTATION_USE_SITE", "ARRAY_ACCESS", "getARRAY_ACCESS", "AS_TYPE", "getAS_TYPE", "CALL_ELEMENT_WITH_DOT", "getCALL_ELEMENT_WITH_DOT", "COMMAS", "getCOMMAS$annotations", "getCOMMAS", "COMPANION_OBJECT", "getCOMPANION_OBJECT", "CONST_MODIFIER", "getCONST_MODIFIER", "DATA_MODIFIER", "getDATA_MODIFIER", "DECLARATION_NAME", "getDECLARATION_NAME", "DECLARATION_NAME_ONLY", "getDECLARATION_NAME_ONLY", "DECLARATION_RETURN_TYPE", "getDECLARATION_RETURN_TYPE", "DECLARATION_SIGNATURE", "getDECLARATION_SIGNATURE", "DECLARATION_SIGNATURE_OR_DEFAULT", "getDECLARATION_SIGNATURE_OR_DEFAULT", "DECLARATION_START_TO_NAME", "getDECLARATION_START_TO_NAME", "DECLARATION_WITH_BODY", "getDECLARATION_WITH_BODY", "DEFAULT", "getDEFAULT", "DELEGATED_SUPERTYPE_BY_KEYWORD", "getDELEGATED_SUPERTYPE_BY_KEYWORD", "DOT_BY_QUALIFIED", "getDOT_BY_QUALIFIED", "ELSE_ENTRY", "getELSE_ENTRY", "ENUM_MODIFIER", "getENUM_MODIFIER", "EXPECT_ACTUAL_MODIFIER", "getEXPECT_ACTUAL_MODIFIER", "EXTERNAL_MODIFIER", "getEXTERNAL_MODIFIER", "FIELD_KEYWORD", "getFIELD_KEYWORD", "FUN_INTERFACE", "getFUN_INTERFACE", "FUN_MODIFIER", "getFUN_MODIFIER", "IF_EXPRESSION", "getIF_EXPRESSION", "IMPORT_ALIAS", "getIMPORT_ALIAS", "IMPORT_LAST_NAME", "getIMPORT_LAST_NAME", "INLINE_FUN_MODIFIER", "getINLINE_FUN_MODIFIER", "INLINE_OR_VALUE_MODIFIER", "getINLINE_OR_VALUE_MODIFIER", "INLINE_PARAMETER_MODIFIER", "getINLINE_PARAMETER_MODIFIER", "INNER_MODIFIER", "getINNER_MODIFIER", "LABEL", "getLABEL", "LAST_CHILD", "getLAST_CHILD", "LATEINIT_MODIFIER", "getLATEINIT_MODIFIER", "LONG_LITERAL_SUFFIX", "getLONG_LITERAL_SUFFIX", "MODALITY_MODIFIER", "getMODALITY_MODIFIER", "NAME_IDENTIFIER", "getNAME_IDENTIFIER", "NAME_OF_NAMED_ARGUMENT", "getNAME_OF_NAMED_ARGUMENT", "NON_FINAL_MODIFIER_OR_NAME", "getNON_FINAL_MODIFIER_OR_NAME", "NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT", "getNOT_SUPPORTED_IN_INLINE_MOST_RELEVANT", "OBJECT_KEYWORD", "getOBJECT_KEYWORD", "OPEN_MODIFIER", "getOPEN_MODIFIER", "OPERATION_TO_END", "org/jetbrains/kotlin/diagnostics/LightTreePositioningStrategies$OPERATION_TO_END$1", "Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategies$OPERATION_TO_END$1;", "OPERATOR", "getOPERATOR", "OPERATOR_MODIFIER", "getOPERATOR_MODIFIER", "OVERRIDE_MODIFIER", "getOVERRIDE_MODIFIER", "PARAMETERS_WITH_DEFAULT_VALUE", "getPARAMETERS_WITH_DEFAULT_VALUE", "PARAMETER_DEFAULT_VALUE", "getPARAMETER_DEFAULT_VALUE", "PARAMETER_VARARG_MODIFIER", "getPARAMETER_VARARG_MODIFIER", "PRIVATE_MODIFIER", "getPRIVATE_MODIFIER", "PROPERTY_DELEGATE", "getPROPERTY_DELEGATE", "QUESTION_MARK_BY_TYPE", "getQUESTION_MARK_BY_TYPE", "REDUNDANT_NULLABLE", "getREDUNDANT_NULLABLE", "REFERENCED_NAME_BY_QUALIFIED", "getREFERENCED_NAME_BY_QUALIFIED", "REFERENCE_BY_QUALIFIED", "getREFERENCE_BY_QUALIFIED", "REIFIED_MODIFIER", "getREIFIED_MODIFIER", "RETURN_WITH_LABEL", "getRETURN_WITH_LABEL", "SAFE_ACCESS", "getSAFE_ACCESS", "SECONDARY_CONSTRUCTOR_DELEGATION_CALL", "getSECONDARY_CONSTRUCTOR_DELEGATION_CALL", "SELECTOR_BY_QUALIFIED", "getSELECTOR_BY_QUALIFIED", "SPREAD_OPERATOR", "getSPREAD_OPERATOR", "SUPERTYPES_LIST", "getSUPERTYPES_LIST", "SUSPEND_MODIFIER", "getSUSPEND_MODIFIER", "SUSPEND_OR_FUN_MODIFIER", "TAILREC_MODIFIER", "getTAILREC_MODIFIER", "TYPEALIAS_TYPE_REFERENCE", "getTYPEALIAS_TYPE_REFERENCE", "TYPE_PARAMETERS_LIST", "getTYPE_PARAMETERS_LIST", "UNREACHABLE_CODE", "getUNREACHABLE_CODE", "USELESS_ELVIS", "getUSELESS_ELVIS", "VALUE_ARGUMENTS", "getVALUE_ARGUMENTS", "VALUE_ARGUMENTS_LIST", "getVALUE_ARGUMENTS_LIST", "VAL_OR_VAR_NODE", "getVAL_OR_VAR_NODE", "VARIANCE_MODIFIER", "getVARIANCE_MODIFIER", "VISIBILITY_MODIFIER", "getVISIBILITY_MODIFIER", "WHEN_EXPRESSION", "getWHEN_EXPRESSION", "WHOLE_ELEMENT", "getWHOLE_ELEMENT", "nodeTypesWithOperation", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "isDeclaration", "", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "(Lcom/intellij/lang/LighterASTNode;)Z", "referencedTypeExpression", "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "node", "BaseDeclarationNameStrategy", "FindReferencePositioningStrategy", "InlineFunLightTreePositioningStrategy", "ModifierSetBasedLightTreePositioningStrategy", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LightTreePositioningStrategies {
    private static final LightTreePositioningStrategy ABSTRACT_MODIFIER;
    private static final LightTreePositioningStrategy ANNOTATION_USE_SITE;
    private static final LightTreePositioningStrategy ARRAY_ACCESS;
    private static final LightTreePositioningStrategy AS_TYPE;
    private static final LightTreePositioningStrategy CALL_ELEMENT_WITH_DOT;
    private static final LightTreePositioningStrategy COMMAS;
    private static final LightTreePositioningStrategy CONST_MODIFIER;
    private static final LightTreePositioningStrategy DATA_MODIFIER;
    private static final LightTreePositioningStrategy DECLARATION_WITH_BODY;
    private static final LightTreePositioningStrategy DELEGATED_SUPERTYPE_BY_KEYWORD;
    private static final LightTreePositioningStrategy DOT_BY_QUALIFIED;
    private static final LightTreePositioningStrategy ELSE_ENTRY;
    private static final LightTreePositioningStrategy ENUM_MODIFIER;
    private static final LightTreePositioningStrategy EXPECT_ACTUAL_MODIFIER;
    private static final LightTreePositioningStrategy EXTERNAL_MODIFIER;
    private static final LightTreePositioningStrategy FIELD_KEYWORD;
    private static final LightTreePositioningStrategy FUN_INTERFACE;
    private static final LightTreePositioningStrategy FUN_MODIFIER;
    private static final LightTreePositioningStrategy IF_EXPRESSION;
    private static final LightTreePositioningStrategy IMPORT_ALIAS;
    private static final LightTreePositioningStrategy IMPORT_LAST_NAME;
    private static final LightTreePositioningStrategy INLINE_FUN_MODIFIER;
    private static final LightTreePositioningStrategy INLINE_OR_VALUE_MODIFIER;
    private static final LightTreePositioningStrategy INLINE_PARAMETER_MODIFIER;
    private static final LightTreePositioningStrategy INNER_MODIFIER;
    private static final LightTreePositioningStrategy LABEL;
    private static final LightTreePositioningStrategy LATEINIT_MODIFIER;
    private static final LightTreePositioningStrategy LONG_LITERAL_SUFFIX;
    private static final LightTreePositioningStrategy MODALITY_MODIFIER;
    private static final LightTreePositioningStrategy NAME_IDENTIFIER;
    private static final LightTreePositioningStrategy NAME_OF_NAMED_ARGUMENT;
    private static final LightTreePositioningStrategy NON_FINAL_MODIFIER_OR_NAME;
    private static final LightTreePositioningStrategy NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT;
    private static final LightTreePositioningStrategy OBJECT_KEYWORD;
    private static final LightTreePositioningStrategy OPEN_MODIFIER;
    private static final LightTreePositioningStrategies$OPERATION_TO_END$1 OPERATION_TO_END;
    private static final LightTreePositioningStrategy OPERATOR;
    private static final LightTreePositioningStrategy OPERATOR_MODIFIER;
    private static final LightTreePositioningStrategy OVERRIDE_MODIFIER;
    private static final LightTreePositioningStrategy PARAMETERS_WITH_DEFAULT_VALUE;
    private static final LightTreePositioningStrategy PARAMETER_DEFAULT_VALUE;
    private static final LightTreePositioningStrategy PARAMETER_VARARG_MODIFIER;
    private static final LightTreePositioningStrategy PRIVATE_MODIFIER;
    private static final LightTreePositioningStrategy PROPERTY_DELEGATE;
    private static final LightTreePositioningStrategy QUESTION_MARK_BY_TYPE;
    private static final LightTreePositioningStrategy REDUNDANT_NULLABLE;
    private static final LightTreePositioningStrategy REFERENCED_NAME_BY_QUALIFIED;
    private static final LightTreePositioningStrategy REFERENCE_BY_QUALIFIED;
    private static final LightTreePositioningStrategy REIFIED_MODIFIER;
    private static final LightTreePositioningStrategy RETURN_WITH_LABEL;
    private static final LightTreePositioningStrategy SAFE_ACCESS;
    private static final LightTreePositioningStrategy SELECTOR_BY_QUALIFIED;
    private static final LightTreePositioningStrategy SPREAD_OPERATOR;
    private static final LightTreePositioningStrategy SUSPEND_MODIFIER;
    private static final LightTreePositioningStrategy SUSPEND_OR_FUN_MODIFIER;
    private static final LightTreePositioningStrategy TAILREC_MODIFIER;
    private static final LightTreePositioningStrategy TYPEALIAS_TYPE_REFERENCE;
    private static final LightTreePositioningStrategy TYPE_PARAMETERS_LIST;
    private static final LightTreePositioningStrategy UNREACHABLE_CODE;
    private static final LightTreePositioningStrategy USELESS_ELVIS;
    private static final LightTreePositioningStrategy VALUE_ARGUMENTS;
    private static final LightTreePositioningStrategy VALUE_ARGUMENTS_LIST;
    private static final LightTreePositioningStrategy VARIANCE_MODIFIER;
    private static final LightTreePositioningStrategy VISIBILITY_MODIFIER;
    private static final LightTreePositioningStrategy WHEN_EXPRESSION;
    private static final LightTreePositioningStrategy WHOLE_ELEMENT;
    private static final Set<IElementType> nodeTypesWithOperation;
    public static final LightTreePositioningStrategies INSTANCE = new LightTreePositioningStrategies();
    private static final LightTreePositioningStrategy DEFAULT = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DEFAULT$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            IElementType tokenType = node.getTokenType();
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.OBJECT_LITERAL)) {
                IElementType OBJECT_DECLARATION = KtNodeTypes.OBJECT_DECLARATION;
                Intrinsics.checkNotNullExpressionValue(OBJECT_DECLARATION, "OBJECT_DECLARATION");
                LighterASTNode lighterASTNodeFindDescendantByType$default = LightTreePositioningStrategiesKt.findDescendantByType$default(tree, node, OBJECT_DECLARATION, false, 4, null);
                Intrinsics.checkNotNull(lighterASTNodeFindDescendantByType$default);
                LighterASTNode lighterASTNodeObjectKeyword = LightTreePositioningStrategiesKt.objectKeyword(tree, lighterASTNodeFindDescendantByType$default);
                Intrinsics.checkNotNull(lighterASTNodeObjectKeyword);
                LighterASTNode lighterASTNodeSupertypesList = LightTreePositioningStrategiesKt.supertypesList(tree, lighterASTNodeFindDescendantByType$default);
                return LightTreePositioningStrategyKt.markRange(lighterASTNodeObjectKeyword, lighterASTNodeSupertypesList == null ? lighterASTNodeObjectKeyword : lighterASTNodeSupertypesList, startOffset, endOffset, tree, node);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.OBJECT_DECLARATION)) {
                LighterASTNode lighterASTNodeObjectKeyword2 = LightTreePositioningStrategiesKt.objectKeyword(tree, node);
                Intrinsics.checkNotNull(lighterASTNodeObjectKeyword2);
                LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(tree, node);
                return LightTreePositioningStrategyKt.markRange(lighterASTNodeObjectKeyword2, lighterASTNodeNameIdentifier == null ? lighterASTNodeObjectKeyword2 : lighterASTNodeNameIdentifier, startOffset, endOffset, tree, node);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.CONSTRUCTOR_DELEGATION_CALL)) {
                return LightTreePositioningStrategies.INSTANCE.getSECONDARY_CONSTRUCTOR_DELEGATION_CALL().mark(node, startOffset, endOffset, tree);
            }
            return super.mark(node, startOffset, endOffset, tree);
        }
    };
    private static final LightTreePositioningStrategy SUPERTYPES_LIST = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$SUPERTYPES_LIST$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            LighterASTNode lighterASTNodeSupertypesList = LightTreePositioningStrategiesKt.supertypesList(tree, node);
            if (lighterASTNodeSupertypesList == null) {
                lighterASTNodeSupertypesList = node;
            }
            return LightTreePositioningStrategyKt.markElement(lighterASTNodeSupertypesList, startOffset, endOffset, tree, node);
        }
    };
    private static final LightTreePositioningStrategy VAL_OR_VAR_NODE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$VAL_OR_VAR_NODE$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            LighterASTNode lighterASTNodeValOrVarKeyword = LightTreePositioningStrategiesKt.valOrVarKeyword(tree, node);
            if (lighterASTNodeValOrVarKeyword == null) {
                lighterASTNodeValOrVarKeyword = node;
            }
            return LightTreePositioningStrategyKt.markElement(lighterASTNodeValOrVarKeyword, startOffset, endOffset, tree, node);
        }
    };
    private static final LightTreePositioningStrategy COMPANION_OBJECT = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$COMPANION_OBJECT$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            LighterASTNode lighterASTNodeCompanionKeyword = LightTreePositioningStrategiesKt.companionKeyword(tree, node);
            if (lighterASTNodeCompanionKeyword == null) {
                lighterASTNodeCompanionKeyword = node;
            }
            return LightTreePositioningStrategyKt.markElement(lighterASTNodeCompanionKeyword, startOffset, endOffset, tree, node);
        }
    };
    private static final LightTreePositioningStrategy SECONDARY_CONSTRUCTOR_DELEGATION_CALL = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$SECONDARY_CONSTRUCTOR_DELEGATION_CALL$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            IElementType tokenType = node.getTokenType();
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.SECONDARY_CONSTRUCTOR)) {
                LighterASTNode lighterASTNodeValueParameterList = LightTreePositioningStrategiesKt.valueParameterList(tree, node);
                if (lighterASTNodeValueParameterList != null) {
                    LighterASTNode lighterASTNodeConstructorKeyword = LightTreePositioningStrategiesKt.constructorKeyword(tree, node);
                    Intrinsics.checkNotNull(lighterASTNodeConstructorKeyword);
                    LighterASTNode lighterASTNodeLastChild = LightTreePositioningStrategiesKt.lastChild(tree, lighterASTNodeValueParameterList);
                    if (lighterASTNodeLastChild == null) {
                        lighterASTNodeLastChild = lighterASTNodeValueParameterList;
                    }
                    return LightTreePositioningStrategyKt.markRange(lighterASTNodeConstructorKeyword, lighterASTNodeLastChild, startOffset, endOffset, tree, node);
                }
                return LightTreePositioningStrategyKt.markElement$default(node, startOffset, endOffset, tree, null, 16, null);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.CONSTRUCTOR_DELEGATION_CALL)) {
                IElementType CONSTRUCTOR_DELEGATION_REFERENCE = KtNodeTypes.CONSTRUCTOR_DELEGATION_REFERENCE;
                Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR_DELEGATION_REFERENCE, "CONSTRUCTOR_DELEGATION_REFERENCE");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, CONSTRUCTOR_DELEGATION_REFERENCE);
                if (lighterASTNodeFindChildByType != null && LightTreePositioningStrategiesKt.firstChild(tree, lighterASTNodeFindChildByType) == null) {
                    IElementType SECONDARY_CONSTRUCTOR = KtNodeTypes.SECONDARY_CONSTRUCTOR;
                    Intrinsics.checkNotNullExpressionValue(SECONDARY_CONSTRUCTOR, "SECONDARY_CONSTRUCTOR");
                    LighterASTNode lighterASTNodeFindParentOfType$default = LightTreePositioningStrategiesKt.findParentOfType$default(tree, node, SECONDARY_CONSTRUCTOR, false, 4, null);
                    Intrinsics.checkNotNull(lighterASTNodeFindParentOfType$default);
                    LighterASTNode lighterASTNodeValueParameterList2 = LightTreePositioningStrategiesKt.valueParameterList(tree, lighterASTNodeFindParentOfType$default);
                    if (lighterASTNodeValueParameterList2 != null) {
                        LighterASTNode lighterASTNodeConstructorKeyword2 = LightTreePositioningStrategiesKt.constructorKeyword(tree, lighterASTNodeFindParentOfType$default);
                        Intrinsics.checkNotNull(lighterASTNodeConstructorKeyword2);
                        LighterASTNode lighterASTNodeLastChild2 = LightTreePositioningStrategiesKt.lastChild(tree, lighterASTNodeValueParameterList2);
                        if (lighterASTNodeLastChild2 != null) {
                            lighterASTNodeValueParameterList2 = lighterASTNodeLastChild2;
                        }
                        return LightTreePositioningStrategyKt.markRange(lighterASTNodeConstructorKeyword2, lighterASTNodeValueParameterList2, startOffset, endOffset, tree, node);
                    }
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindParentOfType$default, startOffset, endOffset, tree, node);
                }
                if (lighterASTNodeFindChildByType == null) {
                    lighterASTNodeFindChildByType = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
            }
            throw new IllegalStateException(("unexpected element " + node).toString());
        }
    };
    private static final LightTreePositioningStrategy DECLARATION_RETURN_TYPE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DECLARATION_RETURN_TYPE$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            return LightTreePositioningStrategyKt.markElement(getElementToMark(node, tree), startOffset, endOffset, tree, node);
        }

        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public boolean isValid(LighterASTNode node, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            return super.isValid(getElementToMark(node, tree), tree);
        }

        private final LighterASTNode getElementToMark(LighterASTNode node, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Pair pair;
            if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.PROPERTY_ACCESSOR)) {
                pair = TuplesKt.to(LightTreePositioningStrategiesKt.typeReference(tree, node), LightTreePositioningStrategiesKt.accessorNamePlaceholder(tree, node));
            } else {
                pair = LightTreePositioningStrategies.INSTANCE.isDeclaration(node) ? TuplesKt.to(LightTreePositioningStrategiesKt.typeReference(tree, node), LightTreePositioningStrategiesKt.nameIdentifier(tree, node)) : TuplesKt.to(null, null);
            }
            LighterASTNode lighterASTNode = (LighterASTNode) pair.component1();
            LighterASTNode lighterASTNode2 = (LighterASTNode) pair.component2();
            if (lighterASTNode == null) {
                return lighterASTNode2 == null ? node : lighterASTNode2;
            }
            return lighterASTNode;
        }
    };
    private static final LightTreePositioningStrategy DECLARATION_START_TO_NAME = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DECLARATION_START_TO_NAME$1
        private final LighterASTNode firstNonCommentNonAnnotationLeaf(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
            LighterASTNode lighterASTNode2;
            IElementType tokenType;
            LighterASTNode lighterASTNodeFirstNonCommentNonAnnotationLeaf;
            List listFilterNotNull = ArraysKt.filterNotNull(LightTreePositioningStrategiesKt.getChildrenArray(flyweightCapableTreeStructure, lighterASTNode));
            if (listFilterNotNull.isEmpty()) {
                return lighterASTNode;
            }
            Iterator it = listFilterNotNull.iterator();
            while (it.hasNext() && (tokenType = (lighterASTNode2 = (LighterASTNode) it.next()).getTokenType()) != null) {
                if (!KtTokens.WHITE_SPACE_OR_COMMENT_BIT_SET.contains(tokenType) && !Intrinsics.areEqual(tokenType, KtNodeTypes.ANNOTATION_ENTRY) && (lighterASTNodeFirstNonCommentNonAnnotationLeaf = firstNonCommentNonAnnotationLeaf(flyweightCapableTreeStructure, lighterASTNode2)) != null) {
                    return lighterASTNodeFirstNonCommentNonAnnotationLeaf;
                }
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            LighterASTNode lighterASTNodeObjectKeyword;
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            LighterASTNode lighterASTNodeFirstNonCommentNonAnnotationLeaf = firstNonCommentNonAnnotationLeaf(tree, node);
            LighterASTNode lighterASTNode = lighterASTNodeFirstNonCommentNonAnnotationLeaf == null ? node : lighterASTNodeFirstNonCommentNonAnnotationLeaf;
            LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(tree, node);
            if (lighterASTNodeNameIdentifier != null) {
                return LightTreePositioningStrategyKt.markRange(lighterASTNode, lighterASTNodeNameIdentifier, startOffset, endOffset, tree, node);
            }
            IElementType tokenType = node.getTokenType();
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.PRIMARY_CONSTRUCTOR) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.SECONDARY_CONSTRUCTOR)) {
                lighterASTNodeObjectKeyword = LightTreePositioningStrategiesKt.constructorKeyword(tree, node);
            } else if (Intrinsics.areEqual(tokenType, KtNodeTypes.OBJECT_DECLARATION)) {
                lighterASTNodeObjectKeyword = LightTreePositioningStrategiesKt.objectKeyword(tree, node);
            } else {
                return LightTreePositioningStrategies.INSTANCE.getDEFAULT().mark(node, startOffset, endOffset, tree);
            }
            return LightTreePositioningStrategyKt.markRange(lighterASTNode, lighterASTNodeObjectKeyword == null ? node : lighterASTNodeObjectKeyword, startOffset, endOffset, tree, node);
        }
    };
    private static final LightTreePositioningStrategy DECLARATION_NAME = new BaseDeclarationNameStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DECLARATION_NAME$1
        /* JADX WARN: Code duplicated, block: B:13:0x0042  */
        /* JADX WARN: Code duplicated, block: B:15:0x005e  */
        /* JADX WARN: Code duplicated, block: B:16:0x0060 A[PHI: r0
          0x0060: PHI (r0v10 org.jetbrains.kotlin.com.intellij.lang.LighterASTNode) = 
          (r0v9 org.jetbrains.kotlin.com.intellij.lang.LighterASTNode)
          (r0v11 org.jetbrains.kotlin.com.intellij.lang.LighterASTNode)
         binds: [B:14:0x005c, B:12:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies.BaseDeclarationNameStrategy
        protected List<TextRange> markNameIdentifier(LighterASTNode nameIdentifier, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree, LighterASTNode node) {
            LighterASTNode lighterASTNodeFindChildByType;
            LighterASTNode lighterASTNode;
            Intrinsics.checkNotNullParameter(nameIdentifier, "nameIdentifier");
            Intrinsics.checkNotNullParameter(tree, "tree");
            Intrinsics.checkNotNullParameter(node, "node");
            if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.CLASS) || Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.OBJECT_DECLARATION)) {
                LighterASTNode lighterASTNodeModifierList = LightTreePositioningStrategiesKt.modifierList(tree, node);
                if (lighterASTNodeModifierList != null) {
                    KtModifierKeywordToken ENUM_KEYWORD = KtTokens.ENUM_KEYWORD;
                    Intrinsics.checkNotNullExpressionValue(ENUM_KEYWORD, "ENUM_KEYWORD");
                    lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, lighterASTNodeModifierList, ENUM_KEYWORD);
                    if (lighterASTNodeFindChildByType == null) {
                        TokenSet tokenSetCreate = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.OBJECT_KEYWORD);
                        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
                        lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, tokenSetCreate);
                        if (lighterASTNodeFindChildByType == null) {
                            lighterASTNode = node;
                        } else {
                            lighterASTNode = lighterASTNodeFindChildByType;
                        }
                    } else {
                        lighterASTNode = lighterASTNodeFindChildByType;
                    }
                } else {
                    TokenSet tokenSetCreate2 = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.OBJECT_KEYWORD);
                    Intrinsics.checkNotNullExpressionValue(tokenSetCreate2, "create(...)");
                    lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, tokenSetCreate2);
                    if (lighterASTNodeFindChildByType == null) {
                        lighterASTNode = node;
                    } else {
                        lighterASTNode = lighterASTNodeFindChildByType;
                    }
                }
                return LightTreePositioningStrategyKt.markRange(lighterASTNode, nameIdentifier, startOffset, endOffset, tree, node);
            }
            return LightTreePositioningStrategyKt.markElement(nameIdentifier, startOffset, endOffset, tree, node);
        }

        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public boolean isValid(LighterASTNode node, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.OBJECT_DECLARATION) || Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.FUN) || Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.SECONDARY_CONSTRUCTOR) || Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.OBJECT_LITERAL) || LightTreePositioningStrategiesKt.nameIdentifier(tree, node) != null) {
                return super.isValid(node, tree);
            }
            return false;
        }
    };
    private static final LightTreePositioningStrategy DECLARATION_NAME_ONLY = new BaseDeclarationNameStrategy();
    private static final LightTreePositioningStrategy ACTUAL_DECLARATION_NAME = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$ACTUAL_DECLARATION_NAME$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(tree, node);
            if (lighterASTNodeNameIdentifier != null) {
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeNameIdentifier, startOffset, endOffset, tree, node);
            }
            return LightTreePositioningStrategies.INSTANCE.getDEFAULT().mark(node, startOffset, endOffset, tree);
        }
    };
    private static final LightTreePositioningStrategy DECLARATION_SIGNATURE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DECLARATION_SIGNATURE$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            LighterASTNode lighterASTNodeValueParameterList;
            LighterASTNode lighterASTNodeReceiverTypeReference;
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            IElementType tokenType = node.getTokenType();
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.PRIMARY_CONSTRUCTOR) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.SECONDARY_CONSTRUCTOR)) {
                LighterASTNode lighterASTNodeConstructorKeyword = LightTreePositioningStrategiesKt.constructorKeyword(tree, node);
                if (lighterASTNodeConstructorKeyword != null || (lighterASTNodeConstructorKeyword = LightTreePositioningStrategiesKt.valueParameterList(tree, node)) != null) {
                    LighterASTNode lighterASTNodeValueParameterList2 = LightTreePositioningStrategiesKt.valueParameterList(tree, node);
                    if (lighterASTNodeValueParameterList2 == null && (lighterASTNodeValueParameterList2 = LightTreePositioningStrategiesKt.constructorKeyword(tree, node)) == null) {
                        return LightTreePositioningStrategyKt.markElement$default(node, startOffset, endOffset, tree, null, 16, null);
                    }
                    return LightTreePositioningStrategyKt.markRange(lighterASTNodeConstructorKeyword, lighterASTNodeValueParameterList2, startOffset, endOffset, tree, node);
                }
                return LightTreePositioningStrategyKt.markElement$default(node, startOffset, endOffset, tree, null, 16, null);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.FUN) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.FUNCTION_LITERAL)) {
                LighterASTNode lighterASTNodeTypeReference = LightTreePositioningStrategiesKt.typeReference(tree, node);
                return LightTreePositioningStrategyKt.markRange((!Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.FUNCTION_LITERAL) || ((lighterASTNodeReceiverTypeReference = LightTreePositioningStrategiesKt.receiverTypeReference(tree, node)) == null && (lighterASTNodeReceiverTypeReference = LightTreePositioningStrategiesKt.valueParameterList(tree, node)) == null)) ? node : lighterASTNodeReceiverTypeReference, (lighterASTNodeTypeReference == null && (lighterASTNodeTypeReference = LightTreePositioningStrategiesKt.valueParameterList(tree, node)) == null && (lighterASTNodeTypeReference = LightTreePositioningStrategiesKt.nameIdentifier(tree, node)) == null) ? node : lighterASTNodeTypeReference, startOffset, endOffset, tree, node);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.PROPERTY)) {
                LighterASTNode lighterASTNodeTypeReference2 = LightTreePositioningStrategiesKt.typeReference(tree, node);
                return LightTreePositioningStrategyKt.markRange(node, (lighterASTNodeTypeReference2 == null && (lighterASTNodeTypeReference2 = LightTreePositioningStrategiesKt.nameIdentifier(tree, node)) == null) ? node : lighterASTNodeTypeReference2, startOffset, endOffset, tree, node);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.PROPERTY_ACCESSOR)) {
                LighterASTNode lighterASTNodeTypeReference3 = LightTreePositioningStrategiesKt.typeReference(tree, node);
                if (lighterASTNodeTypeReference3 == null && (lighterASTNodeTypeReference3 = LightTreePositioningStrategiesKt.rightParenthesis(tree, node)) == null) {
                    lighterASTNodeTypeReference3 = LightTreePositioningStrategiesKt.accessorNamePlaceholder(tree, node);
                }
                return LightTreePositioningStrategyKt.markRange(node, lighterASTNodeTypeReference3, startOffset, endOffset, tree, node);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.CLASS)) {
                LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(tree, node);
                if (lighterASTNodeNameIdentifier != null) {
                    LighterASTNode lighterASTNodePrimaryConstructor = LightTreePositioningStrategiesKt.primaryConstructor(tree, node);
                    if (lighterASTNodePrimaryConstructor == null || (lighterASTNodeValueParameterList = LightTreePositioningStrategiesKt.valueParameterList(tree, lighterASTNodePrimaryConstructor)) == null) {
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeNameIdentifier, startOffset, endOffset, tree, node);
                    }
                    return LightTreePositioningStrategyKt.markRange(lighterASTNodeNameIdentifier, lighterASTNodeValueParameterList, startOffset, endOffset, tree, node);
                }
                return LightTreePositioningStrategyKt.markElement$default(node, startOffset, endOffset, tree, null, 16, null);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.OBJECT_DECLARATION)) {
                return LightTreePositioningStrategies.INSTANCE.getDECLARATION_NAME().mark(node, startOffset, endOffset, tree);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.CLASS_INITIALIZER)) {
                LighterASTNode lighterASTNodeInitKeyword = LightTreePositioningStrategiesKt.initKeyword(tree, node);
                Intrinsics.checkNotNull(lighterASTNodeInitKeyword);
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeInitKeyword, startOffset, endOffset, tree, node);
            }
            return super.mark(node, startOffset, endOffset, tree);
        }
    };
    private static final LightTreePositioningStrategy DECLARATION_SIGNATURE_OR_DEFAULT = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DECLARATION_SIGNATURE_OR_DEFAULT$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            if (LightTreePositioningStrategies.INSTANCE.isDeclaration(node)) {
                return LightTreePositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE().mark(node, startOffset, endOffset, tree);
            }
            return LightTreePositioningStrategies.INSTANCE.getDEFAULT().mark(node, startOffset, endOffset, tree);
        }
    };
    private static final LightTreePositioningStrategy LAST_CHILD = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$LAST_CHILD$1
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            return LightTreePositioningStrategyKt.markElement(LightTreePositioningStrategyKt.nonFillerLastChildOrSelf(node, tree), startOffset, endOffset, tree, node);
        }
    };

    public static /* synthetic */ void getCOMMAS$annotations() {
    }

    private LightTreePositioningStrategies() {
    }

    /* JADX WARN: Type inference failed for: r0v116, types: [org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$OPERATION_TO_END$1] */
    static {
        TokenSet VISIBILITY_MODIFIERS = KtTokens.VISIBILITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(VISIBILITY_MODIFIERS, "VISIBILITY_MODIFIERS");
        VISIBILITY_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(VISIBILITY_MODIFIERS);
        TokenSet MODALITY_MODIFIERS = KtTokens.MODALITY_MODIFIERS;
        Intrinsics.checkNotNullExpressionValue(MODALITY_MODIFIERS, "MODALITY_MODIFIERS");
        MODALITY_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(MODALITY_MODIFIERS);
        KtModifierKeywordToken ABSTRACT_KEYWORD = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD, "ABSTRACT_KEYWORD");
        ABSTRACT_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(ABSTRACT_KEYWORD);
        KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
        OPEN_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(OPEN_KEYWORD);
        KtModifierKeywordToken OVERRIDE_KEYWORD = KtTokens.OVERRIDE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OVERRIDE_KEYWORD, "OVERRIDE_KEYWORD");
        OVERRIDE_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(OVERRIDE_KEYWORD);
        KtModifierKeywordToken PRIVATE_KEYWORD = KtTokens.PRIVATE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(PRIVATE_KEYWORD, "PRIVATE_KEYWORD");
        PRIVATE_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(PRIVATE_KEYWORD);
        KtModifierKeywordToken LATEINIT_KEYWORD = KtTokens.LATEINIT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(LATEINIT_KEYWORD, "LATEINIT_KEYWORD");
        LATEINIT_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(LATEINIT_KEYWORD);
        KtModifierKeywordToken IN_KEYWORD = KtTokens.IN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(IN_KEYWORD, "IN_KEYWORD");
        KtModifierKeywordToken OUT_KEYWORD = KtTokens.OUT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OUT_KEYWORD, "OUT_KEYWORD");
        VARIANCE_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(IN_KEYWORD, OUT_KEYWORD);
        KtModifierKeywordToken CONST_KEYWORD = KtTokens.CONST_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CONST_KEYWORD, "CONST_KEYWORD");
        CONST_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(CONST_KEYWORD);
        KtModifierKeywordToken FUN_KEYWORD = KtTokens.FUN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(FUN_KEYWORD, "FUN_KEYWORD");
        FUN_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(FUN_KEYWORD);
        KtModifierKeywordToken SUSPEND_KEYWORD = KtTokens.SUSPEND_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SUSPEND_KEYWORD, "SUSPEND_KEYWORD");
        SUSPEND_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(SUSPEND_KEYWORD);
        KtModifierKeywordToken SUSPEND_KEYWORD2 = KtTokens.SUSPEND_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SUSPEND_KEYWORD2, "SUSPEND_KEYWORD");
        KtModifierKeywordToken FUN_KEYWORD2 = KtTokens.FUN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(FUN_KEYWORD2, "FUN_KEYWORD");
        SUSPEND_OR_FUN_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(SUSPEND_KEYWORD2, FUN_KEYWORD2);
        KtModifierKeywordToken INLINE_KEYWORD = KtTokens.INLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INLINE_KEYWORD, "INLINE_KEYWORD");
        KtModifierKeywordToken VALUE_KEYWORD = KtTokens.VALUE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(VALUE_KEYWORD, "VALUE_KEYWORD");
        INLINE_OR_VALUE_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(INLINE_KEYWORD, VALUE_KEYWORD);
        KtModifierKeywordToken INNER_KEYWORD = KtTokens.INNER_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INNER_KEYWORD, "INNER_KEYWORD");
        INNER_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(INNER_KEYWORD);
        KtModifierKeywordToken DATA_KEYWORD = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD, "DATA_KEYWORD");
        DATA_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(DATA_KEYWORD);
        KtModifierKeywordToken OPERATOR_KEYWORD = KtTokens.OPERATOR_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPERATOR_KEYWORD, "OPERATOR_KEYWORD");
        OPERATOR_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(OPERATOR_KEYWORD);
        KtModifierKeywordToken ENUM_KEYWORD = KtTokens.ENUM_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ENUM_KEYWORD, "ENUM_KEYWORD");
        ENUM_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(ENUM_KEYWORD);
        KtModifierKeywordToken TAILREC_KEYWORD = KtTokens.TAILREC_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(TAILREC_KEYWORD, "TAILREC_KEYWORD");
        TAILREC_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(TAILREC_KEYWORD);
        KtModifierKeywordToken EXTERNAL_KEYWORD = KtTokens.EXTERNAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_KEYWORD, "EXTERNAL_KEYWORD");
        EXTERNAL_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(EXTERNAL_KEYWORD);
        KtModifierKeywordToken EXPECT_KEYWORD = KtTokens.EXPECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXPECT_KEYWORD, "EXPECT_KEYWORD");
        KtModifierKeywordToken ACTUAL_KEYWORD = KtTokens.ACTUAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ACTUAL_KEYWORD, "ACTUAL_KEYWORD");
        EXPECT_ACTUAL_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(EXPECT_KEYWORD, ACTUAL_KEYWORD);
        OBJECT_KEYWORD = LightTreePositioningStrategiesKt.keywordStrategy(new Function2<FlyweightCapableTreeStructure<LighterASTNode>, LighterASTNode, LighterASTNode>() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$OBJECT_KEYWORD$1
            @Override // kotlin.jvm.functions.Function2
            public final LighterASTNode invoke(FlyweightCapableTreeStructure<LighterASTNode> keywordStrategy, LighterASTNode it) {
                Intrinsics.checkNotNullParameter(keywordStrategy, "$this$keywordStrategy");
                Intrinsics.checkNotNullParameter(it, "it");
                return LightTreePositioningStrategiesKt.objectKeyword(keywordStrategy, it);
            }
        });
        FIELD_KEYWORD = LightTreePositioningStrategiesKt.keywordStrategy(new Function2<FlyweightCapableTreeStructure<LighterASTNode>, LighterASTNode, LighterASTNode>() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$FIELD_KEYWORD$1
            @Override // kotlin.jvm.functions.Function2
            public final LighterASTNode invoke(FlyweightCapableTreeStructure<LighterASTNode> keywordStrategy, LighterASTNode it) {
                Intrinsics.checkNotNullParameter(keywordStrategy, "$this$keywordStrategy");
                Intrinsics.checkNotNullParameter(it, "it");
                return LightTreePositioningStrategiesKt.fieldKeyword(keywordStrategy, it);
            }
        });
        PROPERTY_DELEGATE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$PROPERTY_DELEGATE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType PROPERTY_DELEGATE2 = KtNodeTypes.PROPERTY_DELEGATE;
                Intrinsics.checkNotNullExpressionValue(PROPERTY_DELEGATE2, "PROPERTY_DELEGATE");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, PROPERTY_DELEGATE2);
                if (lighterASTNodeFindChildByType == null) {
                    lighterASTNodeFindChildByType = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
            }

            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public boolean isValid(LighterASTNode node, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType PROPERTY_DELEGATE2 = KtNodeTypes.PROPERTY_DELEGATE;
                Intrinsics.checkNotNullExpressionValue(PROPERTY_DELEGATE2, "PROPERTY_DELEGATE");
                return LightTreePositioningStrategiesKt.findChildByType(tree, node, PROPERTY_DELEGATE2) != null;
            }
        };
        KtModifierKeywordToken NOINLINE_KEYWORD = KtTokens.NOINLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(NOINLINE_KEYWORD, "NOINLINE_KEYWORD");
        KtModifierKeywordToken CROSSINLINE_KEYWORD = KtTokens.CROSSINLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CROSSINLINE_KEYWORD, "CROSSINLINE_KEYWORD");
        INLINE_PARAMETER_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(NOINLINE_KEYWORD, CROSSINLINE_KEYWORD);
        INLINE_FUN_MODIFIER = new InlineFunLightTreePositioningStrategy();
        OPERATOR = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$OPERATOR$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeOperationReference = LightTreePositioningStrategiesKt.operationReference(tree, node);
                if (lighterASTNodeOperationReference == null) {
                    lighterASTNodeOperationReference = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeOperationReference, startOffset, endOffset, tree, node);
            }
        };
        PARAMETER_DEFAULT_VALUE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$PARAMETER_DEFAULT_VALUE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeDefaultValue = LightTreePositioningStrategiesKt.defaultValue(tree, node);
                if (lighterASTNodeDefaultValue == null) {
                    lighterASTNodeDefaultValue = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeDefaultValue, startOffset, endOffset, tree, node);
            }
        };
        PARAMETERS_WITH_DEFAULT_VALUE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$PARAMETERS_WITH_DEFAULT_VALUE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                List listValueParameters = LightTreePositioningStrategiesKt.valueParameters(tree, node);
                ArrayList arrayList = new ArrayList();
                for (Object obj : listValueParameters) {
                    if (LightTreePositioningStrategiesKt.defaultValue(tree, (LighterASTNode) obj) != null) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayListListOf = arrayList;
                if (arrayListListOf.isEmpty()) {
                    arrayListListOf = null;
                }
                if (arrayListListOf == null) {
                    LighterASTNode lighterASTNodeValueParameterList = LightTreePositioningStrategiesKt.valueParameterList(tree, node);
                    arrayListListOf = lighterASTNodeValueParameterList != null ? CollectionsKt.listOf(lighterASTNodeValueParameterList) : null;
                    if (arrayListListOf == null) {
                        LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(tree, node);
                        List listListOf = lighterASTNodeNameIdentifier != null ? CollectionsKt.listOf(lighterASTNodeNameIdentifier) : null;
                        arrayListListOf = listListOf == null ? CollectionsKt.listOf(node) : listListOf;
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayListListOf.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList2, LightTreePositioningStrategyKt.markElement((LighterASTNode) it.next(), startOffset, endOffset, tree, node));
                }
                return arrayList2;
            }
        };
        PARAMETER_VARARG_MODIFIER = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$PARAMETER_VARARG_MODIFIER$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                LighterASTNode lighterASTNodeFindChildByType;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeModifierList = LightTreePositioningStrategiesKt.modifierList(tree, node);
                if (lighterASTNodeModifierList != null) {
                    KtModifierKeywordToken VARARG_KEYWORD = KtTokens.VARARG_KEYWORD;
                    Intrinsics.checkNotNullExpressionValue(VARARG_KEYWORD, "VARARG_KEYWORD");
                    lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, lighterASTNodeModifierList, VARARG_KEYWORD);
                } else {
                    lighterASTNodeFindChildByType = null;
                }
                if (lighterASTNodeFindChildByType == null) {
                    lighterASTNodeFindChildByType = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
            }
        };
        NAME_OF_NAMED_ARGUMENT = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$NAME_OF_NAMED_ARGUMENT$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                List<TextRange> listMarkElement;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType VALUE_ARGUMENT_NAME = KtNodeTypes.VALUE_ARGUMENT_NAME;
                Intrinsics.checkNotNullExpressionValue(VALUE_ARGUMENT_NAME, "VALUE_ARGUMENT_NAME");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, VALUE_ARGUMENT_NAME);
                return (lighterASTNodeFindChildByType == null || (listMarkElement = LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node)) == null) ? LightTreePositioningStrategyKt.markElement(node, startOffset, endOffset, tree, node) : listMarkElement;
            }
        };
        VALUE_ARGUMENTS_LIST = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$VALUE_ARGUMENTS_LIST$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType VALUE_ARGUMENT_LIST = KtNodeTypes.VALUE_ARGUMENT_LIST;
                Intrinsics.checkNotNullExpressionValue(VALUE_ARGUMENT_LIST, "VALUE_ARGUMENT_LIST");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, VALUE_ARGUMENT_LIST);
                if (lighterASTNodeFindChildByType == null) {
                    lighterASTNodeFindChildByType = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
            }
        };
        VALUE_ARGUMENTS = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$VALUE_ARGUMENTS$1
            /* JADX WARN: Code duplicated, block: B:19:0x0061  */
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                LighterASTNode lighterASTNodeFindLastChildByType;
                LighterASTNode lighterASTNodeFirstChildExpression;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.BINARY_EXPRESSION)) {
                    TokenSet ALL_ASSIGNMENTS = KtTokens.ALL_ASSIGNMENTS;
                    Intrinsics.checkNotNullExpressionValue(ALL_ASSIGNMENTS, "ALL_ASSIGNMENTS");
                    if (LightTreePositioningStrategiesKt.findDescendantByTypes(tree, node, ALL_ASSIGNMENTS) != null && (lighterASTNodeFirstChildExpression = LightTreePositioningStrategiesKt.firstChildExpression(tree, node)) != null) {
                        return LightTreePositioningStrategyKt.markElement(LightTreePositioningStrategiesKt.unwrapParenthesesLabelsAndAnnotations(tree, lighterASTNodeFirstChildExpression), startOffset, endOffset, tree, node);
                    }
                }
                IElementType tokenType = node.getTokenType();
                if (KtTokens.QUALIFIED_ACCESS.contains(tokenType)) {
                    IElementType CALL_EXPRESSION = KtNodeTypes.CALL_EXPRESSION;
                    Intrinsics.checkNotNullExpressionValue(CALL_EXPRESSION, "CALL_EXPRESSION");
                    lighterASTNodeFindLastChildByType = LightTreePositioningStrategiesKt.findLastChildByType(tree, node, CALL_EXPRESSION);
                    if (lighterASTNodeFindLastChildByType == null) {
                        lighterASTNodeFindLastChildByType = node;
                    }
                } else if (Intrinsics.areEqual(tokenType, KtNodeTypes.CLASS)) {
                    IElementType SUPER_TYPE_LIST = KtNodeTypes.SUPER_TYPE_LIST;
                    Intrinsics.checkNotNullExpressionValue(SUPER_TYPE_LIST, "SUPER_TYPE_LIST");
                    lighterASTNodeFindLastChildByType = LightTreePositioningStrategiesKt.findLastChildByType(tree, node, SUPER_TYPE_LIST);
                    if (lighterASTNodeFindLastChildByType == null) {
                        lighterASTNodeFindLastChildByType = node;
                    }
                } else {
                    lighterASTNodeFindLastChildByType = node;
                }
                LighterASTNode lighterASTNodeFindChildByType = Intrinsics.areEqual(lighterASTNodeFindLastChildByType.getTokenType(), KtNodeTypes.VALUE_ARGUMENT_LIST) ? lighterASTNodeFindLastChildByType : null;
                if (lighterASTNodeFindChildByType == null) {
                    IElementType VALUE_ARGUMENT_LIST = KtNodeTypes.VALUE_ARGUMENT_LIST;
                    Intrinsics.checkNotNullExpressionValue(VALUE_ARGUMENT_LIST, "VALUE_ARGUMENT_LIST");
                    lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, lighterASTNodeFindLastChildByType, VALUE_ARGUMENT_LIST);
                }
                if (lighterASTNodeFindChildByType != null) {
                    KtSingleValueToken RPAR = KtTokens.RPAR;
                    Intrinsics.checkNotNullExpressionValue(RPAR, "RPAR");
                    LighterASTNode lighterASTNodeFindLastChildByType2 = LightTreePositioningStrategiesKt.findLastChildByType(tree, lighterASTNodeFindChildByType, RPAR);
                    if (lighterASTNodeFindLastChildByType2 == null) {
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindLastChildByType, startOffset, endOffset, tree, node);
                    }
                    IElementType VALUE_ARGUMENT = KtNodeTypes.VALUE_ARGUMENT;
                    Intrinsics.checkNotNullExpressionValue(VALUE_ARGUMENT, "VALUE_ARGUMENT");
                    LighterASTNode lighterASTNodeFindLastChildByType3 = LightTreePositioningStrategiesKt.findLastChildByType(tree, lighterASTNodeFindChildByType, VALUE_ARGUMENT);
                    if (lighterASTNodeFindLastChildByType3 != null) {
                        return LightTreePositioningStrategyKt.markRange(lighterASTNodeFindLastChildByType3, lighterASTNodeFindLastChildByType2, startOffset, endOffset, tree, node);
                    }
                    KtSingleValueToken LPAR = KtTokens.LPAR;
                    Intrinsics.checkNotNullExpressionValue(LPAR, "LPAR");
                    LighterASTNode lighterASTNodeFindLastChildByType4 = LightTreePositioningStrategiesKt.findLastChildByType(tree, lighterASTNodeFindChildByType, LPAR);
                    return LightTreePositioningStrategyKt.markRange(lighterASTNodeFindLastChildByType4 == null ? lighterASTNodeFindLastChildByType : lighterASTNodeFindLastChildByType4, lighterASTNodeFindLastChildByType2, startOffset, endOffset, tree, node);
                }
                if (Intrinsics.areEqual(lighterASTNodeFindLastChildByType.getTokenType(), KtNodeTypes.CALL_EXPRESSION)) {
                    IElementType REFERENCE_EXPRESSION = KtNodeTypes.REFERENCE_EXPRESSION;
                    Intrinsics.checkNotNullExpressionValue(REFERENCE_EXPRESSION, "REFERENCE_EXPRESSION");
                    LighterASTNode lighterASTNodeFindChildByType2 = LightTreePositioningStrategiesKt.findChildByType(tree, lighterASTNodeFindLastChildByType, REFERENCE_EXPRESSION);
                    if (lighterASTNodeFindChildByType2 != null) {
                        lighterASTNodeFindLastChildByType = lighterASTNodeFindChildByType2;
                    }
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindLastChildByType, startOffset, endOffset, tree, node);
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindLastChildByType, startOffset, endOffset, tree, node);
            }
        };
        DOT_BY_QUALIFIED = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DOT_BY_QUALIFIED$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.BINARY_EXPRESSION)) {
                    TokenSet ALL_ASSIGNMENTS = KtTokens.ALL_ASSIGNMENTS;
                    Intrinsics.checkNotNullExpressionValue(ALL_ASSIGNMENTS, "ALL_ASSIGNMENTS");
                    if (LightTreePositioningStrategiesKt.findDescendantByTypes(tree, node, ALL_ASSIGNMENTS) != null) {
                        IElementType DOT_QUALIFIED_EXPRESSION = KtNodeTypes.DOT_QUALIFIED_EXPRESSION;
                        Intrinsics.checkNotNullExpressionValue(DOT_QUALIFIED_EXPRESSION, "DOT_QUALIFIED_EXPRESSION");
                        LighterASTNode lighterASTNodeFindDescendantByType$default = LightTreePositioningStrategiesKt.findDescendantByType$default(tree, node, DOT_QUALIFIED_EXPRESSION, false, 4, null);
                        if (lighterASTNodeFindDescendantByType$default != null) {
                            LighterASTNode lighterASTNodeDotOperator = LightTreePositioningStrategiesKt.dotOperator(tree, lighterASTNodeFindDescendantByType$default);
                            if (lighterASTNodeDotOperator != null) {
                                lighterASTNodeFindDescendantByType$default = lighterASTNodeDotOperator;
                            }
                            return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindDescendantByType$default, startOffset, endOffset, tree, node);
                        }
                    }
                }
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.DOT_QUALIFIED_EXPRESSION)) {
                    LighterASTNode lighterASTNodeDotOperator2 = LightTreePositioningStrategiesKt.dotOperator(tree, node);
                    if (lighterASTNodeDotOperator2 == null) {
                        lighterASTNodeDotOperator2 = node;
                    }
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeDotOperator2, startOffset, endOffset, tree, node);
                }
                return LightTreePositioningStrategies.INSTANCE.getREFERENCE_BY_QUALIFIED().mark(node, startOffset, endOffset, tree);
            }
        };
        SELECTOR_BY_QUALIFIED = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$SELECTOR_BY_QUALIFIED$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                LighterASTNode lighterASTNodeReferencedTypeExpression;
                LighterASTNode lighterASTNodeFindExpressionDeep;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.BINARY_EXPRESSION)) {
                    TokenSet ALL_ASSIGNMENTS = KtTokens.ALL_ASSIGNMENTS;
                    Intrinsics.checkNotNullExpressionValue(ALL_ASSIGNMENTS, "ALL_ASSIGNMENTS");
                    if (LightTreePositioningStrategiesKt.findDescendantByTypes(tree, node, ALL_ASSIGNMENTS) != null && (lighterASTNodeFindExpressionDeep = LightTreePositioningStrategiesKt.findExpressionDeep(tree, node)) != null) {
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindExpressionDeep, startOffset, endOffset, tree, node);
                    }
                }
                if (KtTokens.QUALIFIED_ACCESS.contains(node.getTokenType())) {
                    LighterASTNode lighterASTNodeSelector = LightTreePositioningStrategiesKt.selector(tree, node);
                    if (lighterASTNodeSelector != null) {
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeSelector, startOffset, endOffset, tree, node);
                    }
                    return super.mark(node, startOffset, endOffset, tree);
                }
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.IMPORT_DIRECTIVE)) {
                    IElementType REFERENCE_EXPRESSION = KtNodeTypes.REFERENCE_EXPRESSION;
                    Intrinsics.checkNotNullExpressionValue(REFERENCE_EXPRESSION, "REFERENCE_EXPRESSION");
                    LighterASTNode lighterASTNode = (LighterASTNode) CollectionsKt.lastOrNull(LightTreePositioningStrategiesKt.collectDescendantsOfType$default(tree, node, REFERENCE_EXPRESSION, null, 4, null));
                    if (lighterASTNode != null) {
                        return mark(lighterASTNode, lighterASTNode.getStartOffset(), lighterASTNode.getEndOffset(), tree);
                    }
                }
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                    TokenSet TYPE_ELEMENT_TYPES = KtTokenSets.TYPE_ELEMENT_TYPES;
                    Intrinsics.checkNotNullExpressionValue(TYPE_ELEMENT_TYPES, "TYPE_ELEMENT_TYPES");
                    LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, TYPE_ELEMENT_TYPES);
                    if (lighterASTNodeFindChildByType != null && (lighterASTNodeReferencedTypeExpression = LightTreePositioningStrategies.INSTANCE.referencedTypeExpression(tree, lighterASTNodeFindChildByType)) != null) {
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeReferencedTypeExpression, startOffset, endOffset, tree, node);
                    }
                }
                return super.mark(node, startOffset, endOffset, tree);
            }
        };
        FUN_INTERFACE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$FUN_INTERFACE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType tokenType = node.getTokenType();
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.CLASS)) {
                    return LightTreePositioningStrategies.INSTANCE.getFUN_MODIFIER().mark(node, startOffset, endOffset, tree);
                }
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.PROPERTY)) {
                    return LightTreePositioningStrategies.INSTANCE.getVAL_OR_VAR_NODE().mark(node, startOffset, endOffset, tree);
                }
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.FUN)) {
                    if (LightTreePositioningStrategiesKt.typeParametersList(tree, node) == null) {
                        return LightTreePositioningStrategies.SUSPEND_OR_FUN_MODIFIER.mark(node, startOffset, endOffset, tree);
                    }
                    return LightTreePositioningStrategies.INSTANCE.getTYPE_PARAMETERS_LIST().mark(node, startOffset, endOffset, tree);
                }
                return LightTreePositioningStrategies.INSTANCE.getDEFAULT().mark(node, startOffset, endOffset, tree);
            }
        };
        REFERENCE_BY_QUALIFIED = new FindReferencePositioningStrategy(false);
        REFERENCED_NAME_BY_QUALIFIED = new FindReferencePositioningStrategy(true);
        nodeTypesWithOperation = SetsKt.setOf((Object[]) new IElementType[]{KtNodeTypes.IS_EXPRESSION, KtNodeTypes.BINARY_WITH_TYPE, KtNodeTypes.BINARY_EXPRESSION, KtNodeTypes.POSTFIX_EXPRESSION, KtNodeTypes.PREFIX_EXPRESSION, KtNodeTypes.BINARY_EXPRESSION, KtNodeTypes.WHEN_CONDITION_IN_RANGE});
        WHEN_EXPRESSION = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$WHEN_EXPRESSION$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeWhenKeyword = LightTreePositioningStrategiesKt.whenKeyword(tree, node);
                if (lighterASTNodeWhenKeyword == null) {
                    lighterASTNodeWhenKeyword = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeWhenKeyword, startOffset, endOffset, tree, node);
            }
        };
        IF_EXPRESSION = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$IF_EXPRESSION$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeIfKeyword = LightTreePositioningStrategiesKt.ifKeyword(tree, node);
                if (lighterASTNodeIfKeyword == null) {
                    lighterASTNodeIfKeyword = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeIfKeyword, startOffset, endOffset, tree, node);
            }
        };
        ELSE_ENTRY = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$ELSE_ENTRY$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeElseKeyword = LightTreePositioningStrategiesKt.elseKeyword(tree, node);
                if (lighterASTNodeElseKeyword == null) {
                    lighterASTNodeElseKeyword = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeElseKeyword, startOffset, endOffset, tree, node);
            }
        };
        ARRAY_ACCESS = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$ARRAY_ACCESS$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType INDICES = KtNodeTypes.INDICES;
                Intrinsics.checkNotNullExpressionValue(INDICES, "INDICES");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, INDICES);
                Intrinsics.checkNotNull(lighterASTNodeFindChildByType);
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
            }
        };
        SAFE_ACCESS = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$SAFE_ACCESS$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeSafeAccess = LightTreePositioningStrategiesKt.safeAccess(tree, node);
                if (lighterASTNodeSafeAccess == null) {
                    lighterASTNodeSafeAccess = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeSafeAccess, startOffset, endOffset, tree, node);
            }
        };
        ?? r0 = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$OPERATION_TO_END$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeOperationReference = LightTreePositioningStrategiesKt.operationReference(tree, node);
                LighterASTNode lighterASTNode = lighterASTNodeOperationReference == null ? node : lighterASTNodeOperationReference;
                LighterASTNode lighterASTNodeLastChild = LightTreePositioningStrategiesKt.lastChild(tree, node);
                return LightTreePositioningStrategyKt.markRange(lighterASTNode, lighterASTNodeLastChild == null ? node : lighterASTNodeLastChild, startOffset, endOffset, tree, node);
            }
        };
        OPERATION_TO_END = r0;
        AS_TYPE = (LightTreePositioningStrategy) r0;
        USELESS_ELVIS = (LightTreePositioningStrategy) r0;
        RETURN_WITH_LABEL = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$RETURN_WITH_LABEL$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType LABEL_QUALIFIER = KtNodeTypes.LABEL_QUALIFIER;
                Intrinsics.checkNotNullExpressionValue(LABEL_QUALIFIER, "LABEL_QUALIFIER");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, LABEL_QUALIFIER);
                if (lighterASTNodeFindChildByType == null) {
                    LighterASTNode lighterASTNodeReturnKeyword = LightTreePositioningStrategiesKt.returnKeyword(tree, node);
                    if (lighterASTNodeReturnKeyword == null) {
                        lighterASTNodeReturnKeyword = node;
                    }
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeReturnKeyword, startOffset, endOffset, tree, node);
                }
                return LightTreePositioningStrategyKt.markRange(node, lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
            }
        };
        WHOLE_ELEMENT = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$WHOLE_ELEMENT$1
        };
        LONG_LITERAL_SUFFIX = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$LONG_LITERAL_SUFFIX$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.INTEGER_CONSTANT)) {
                    TextRange textRangeCreate = TextRange.create(endOffset - 1, endOffset);
                    Intrinsics.checkNotNullExpressionValue(textRangeCreate, "create(...)");
                    return CollectionsKt.listOf(textRangeCreate);
                }
                return super.mark(node, startOffset, endOffset, tree);
            }
        };
        KtModifierKeywordToken REIFIED_KEYWORD = KtTokens.REIFIED_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(REIFIED_KEYWORD, "REIFIED_KEYWORD");
        REIFIED_MODIFIER = new ModifierSetBasedLightTreePositioningStrategy(REIFIED_KEYWORD);
        TYPE_PARAMETERS_LIST = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$TYPE_PARAMETERS_LIST$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeTypeParametersList = LightTreePositioningStrategiesKt.typeParametersList(tree, node);
                if (lighterASTNodeTypeParametersList == null) {
                    lighterASTNodeTypeParametersList = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeTypeParametersList, startOffset, endOffset, tree, node);
            }
        };
        NAME_IDENTIFIER = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$NAME_IDENTIFIER$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                LighterASTNode lighterASTNodeFindLastDescendant;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(tree, node);
                if (lighterASTNodeNameIdentifier != null) {
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeNameIdentifier, startOffset, endOffset, tree, node);
                }
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.LABEL_QUALIFIER)) {
                    return super.mark(node, startOffset, endOffset - 1, tree);
                }
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.PACKAGE_DIRECTIVE) && (lighterASTNodeFindLastDescendant = LightTreePositioningStrategiesKt.findLastDescendant(tree, node, new Function1<LighterASTNode, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$NAME_IDENTIFIER$1$mark$referenceExpression$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(LighterASTNode it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it.getTokenType(), KtNodeTypes.REFERENCE_EXPRESSION));
                    }
                })) != null) {
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindLastDescendant, startOffset, endOffset, tree, node);
                }
                return LightTreePositioningStrategies.INSTANCE.getDEFAULT().mark(node, startOffset, endOffset, tree);
            }
        };
        REDUNDANT_NULLABLE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$REDUNDANT_NULLABLE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                LighterASTNode lighterASTNode;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                Ref<LighterASTNode[]> ref = new Ref<>();
                LighterASTNode lighterASTNode2 = null;
                LighterASTNode nullableChild = node;
                LighterASTNode lighterASTNode3 = null;
                LighterASTNode lighterASTNode4 = null;
                while (nullableChild != null) {
                    nullableChild = getNullableChild(tree, nullableChild, ref);
                    LighterASTNode[] lighterASTNodeArr = ref.get();
                    Intrinsics.checkNotNullExpressionValue(lighterASTNodeArr, "get(...)");
                    LighterASTNode lighterASTNode5 = (LighterASTNode) ArraysKt.getOrNull(lighterASTNodeArr, 1);
                    if (lighterASTNode4 == null) {
                        lighterASTNode4 = lighterASTNode5;
                        lighterASTNode2 = lighterASTNode3;
                        lighterASTNode3 = lighterASTNode4;
                    } else {
                        LighterASTNode lighterASTNode6 = lighterASTNode3;
                        lighterASTNode3 = lighterASTNode5;
                        lighterASTNode2 = lighterASTNode6;
                    }
                }
                if (lighterASTNode2 == null) {
                    lighterASTNode = lighterASTNode4 == null ? node : lighterASTNode4;
                } else {
                    lighterASTNode = lighterASTNode2;
                }
                return LightTreePositioningStrategyKt.markRange(lighterASTNode, lighterASTNode4 == null ? node : lighterASTNode4, startOffset, endOffset, tree, node);
            }

            private final LighterASTNode getNullableChild(FlyweightCapableTreeStructure<LighterASTNode> tree, LighterASTNode node, Ref<LighterASTNode[]> ref) {
                tree.getChildren(node, ref);
                LighterASTNode[] lighterASTNodeArr = ref.get();
                Intrinsics.checkNotNullExpressionValue(lighterASTNodeArr, "get(...)");
                LighterASTNode lighterASTNode = (LighterASTNode) ArraysKt.firstOrNull(lighterASTNodeArr);
                if (lighterASTNode != null && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.NULLABLE_TYPE)) {
                    return lighterASTNode;
                }
                return null;
            }
        };
        QUESTION_MARK_BY_TYPE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$QUESTION_MARK_BY_TYPE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                    IElementType NULLABLE_TYPE = KtNodeTypes.NULLABLE_TYPE;
                    Intrinsics.checkNotNullExpressionValue(NULLABLE_TYPE, "NULLABLE_TYPE");
                    LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, NULLABLE_TYPE);
                    if (lighterASTNodeFindChildByType != null) {
                        KtSingleValueToken QUEST = KtTokens.QUEST;
                        Intrinsics.checkNotNullExpressionValue(QUEST, "QUEST");
                        LighterASTNode lighterASTNodeFindChildByType2 = LightTreePositioningStrategiesKt.findChildByType(tree, lighterASTNodeFindChildByType, QUEST);
                        if (lighterASTNodeFindChildByType2 != null) {
                            return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType2, startOffset, endOffset, tree, node);
                        }
                    }
                }
                return super.mark(node, startOffset, endOffset, tree);
            }
        };
        ANNOTATION_USE_SITE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$ANNOTATION_USE_SITE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType ANNOTATION_TARGET = KtNodeTypes.ANNOTATION_TARGET;
                Intrinsics.checkNotNullExpressionValue(ANNOTATION_TARGET, "ANNOTATION_TARGET");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, ANNOTATION_TARGET);
                if (lighterASTNodeFindChildByType != null) {
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
                }
                return super.mark(node, startOffset, endOffset, tree);
            }
        };
        IMPORT_LAST_NAME = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$IMPORT_LAST_NAME$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType REFERENCE_EXPRESSION = KtNodeTypes.REFERENCE_EXPRESSION;
                Intrinsics.checkNotNullExpressionValue(REFERENCE_EXPRESSION, "REFERENCE_EXPRESSION");
                LighterASTNode lighterASTNode = (LighterASTNode) CollectionsKt.lastOrNull(LightTreePositioningStrategiesKt.collectDescendantsOfType$default(tree, node, REFERENCE_EXPRESSION, null, 4, null));
                if (lighterASTNode == null) {
                    lighterASTNode = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNode, startOffset, endOffset, tree, node);
            }
        };
        IMPORT_ALIAS = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$IMPORT_ALIAS$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType IMPORT_ALIAS2 = KtNodeTypes.IMPORT_ALIAS;
                Intrinsics.checkNotNullExpressionValue(IMPORT_ALIAS2, "IMPORT_ALIAS");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, IMPORT_ALIAS2);
                if (lighterASTNodeFindChildByType != null) {
                    KtToken IDENTIFIER = KtTokens.IDENTIFIER;
                    Intrinsics.checkNotNullExpressionValue(IDENTIFIER, "IDENTIFIER");
                    LighterASTNode lighterASTNodeFindChildByType2 = LightTreePositioningStrategiesKt.findChildByType(tree, lighterASTNodeFindChildByType, IDENTIFIER);
                    if (lighterASTNodeFindChildByType2 != null) {
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType2, startOffset, endOffset, tree, node);
                    }
                }
                return LightTreePositioningStrategies.INSTANCE.getIMPORT_LAST_NAME().mark(node, startOffset, endOffset, tree);
            }
        };
        SPREAD_OPERATOR = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$SPREAD_OPERATOR$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                return super.mark(node, startOffset, startOffset + 1, tree);
            }
        };
        DECLARATION_WITH_BODY = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DECLARATION_WITH_BODY$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                LighterASTNode lighterASTNodeFindChildByType;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.BLOCK)) {
                    lighterASTNodeFindChildByType = node;
                } else {
                    IElementType BLOCK = KtNodeTypes.BLOCK;
                    Intrinsics.checkNotNullExpressionValue(BLOCK, "BLOCK");
                    lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, BLOCK);
                }
                LighterASTNode lighterASTNode = lighterASTNodeFindChildByType == null ? node : lighterASTNodeFindChildByType;
                KtSingleValueToken RBRACE = KtTokens.RBRACE;
                Intrinsics.checkNotNullExpressionValue(RBRACE, "RBRACE");
                LighterASTNode lighterASTNodeFindLastChildByType = LightTreePositioningStrategiesKt.findLastChildByType(tree, lighterASTNode, RBRACE);
                if (lighterASTNodeFindLastChildByType != null) {
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindLastChildByType, startOffset, endOffset, tree, node);
                }
                if (lighterASTNodeFindChildByType != null) {
                    List<TextRange> listMarkElement = LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listMarkElement, 10));
                    Iterator<T> it = listMarkElement.iterator();
                    while (it.hasNext()) {
                        arrayList.add(lastSymbol((TextRange) it.next()));
                    }
                    return arrayList;
                }
                return super.mark(node, startOffset, endOffset, tree);
            }

            private final TextRange lastSymbol(TextRange range) {
                if (range.isEmpty()) {
                    return range;
                }
                TextRange textRangeCreate = TextRange.create(range.getEndOffset() - 1, range.getEndOffset());
                Intrinsics.checkNotNullExpressionValue(textRangeCreate, "create(...)");
                return textRangeCreate;
            }
        };
        UNREACHABLE_CODE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$UNREACHABLE_CODE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> markKtDiagnostic(KtSourceElement element, KtDiagnostic diagnostic) {
                Intrinsics.checkNotNullParameter(element, "element");
                Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
                KtDiagnosticWithParameters2 ktDiagnosticWithParameters2 = (KtDiagnosticWithParameters2) diagnostic;
                UnreachableCodeLightTreeHelper unreachableCodeLightTreeHelper = new UnreachableCodeLightTreeHelper(element.getTreeStructure());
                Iterable iterable = (Iterable) ktDiagnosticWithParameters2.getA();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    arrayList.add(((KtSourceElement) it.next()).getLighterASTNode());
                }
                Set<? extends LighterASTNode> set = CollectionsKt.toSet(arrayList);
                Iterable iterable2 = (Iterable) ktDiagnosticWithParameters2.getB();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable2, 10));
                Iterator it2 = iterable2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(((KtSourceElement) it2.next()).getLighterASTNode());
                }
                Set<? extends LighterASTNode> set2 = CollectionsKt.toSet(arrayList2);
                if (!unreachableCodeLightTreeHelper.hasChildrenInSet(element.getLighterASTNode(), set)) {
                    return super.markKtDiagnostic(element, diagnostic);
                }
                List<LighterASTNode> listRemoveReachableElementsWithMeaninglessSiblings = unreachableCodeLightTreeHelper.removeReachableElementsWithMeaninglessSiblings(unreachableCodeLightTreeHelper.getLeavesOrReachableChildren(element.getLighterASTNode(), set, set2), set);
                if (listRemoveReachableElementsWithMeaninglessSiblings.isEmpty()) {
                    return super.markKtDiagnostic(element, diagnostic);
                }
                ArrayList arrayList3 = new ArrayList();
                Iterator<T> it3 = listRemoveReachableElementsWithMeaninglessSiblings.iterator();
                while (it3.hasNext()) {
                    CollectionsKt.addAll(arrayList3, LightTreePositioningStrategyKt.markElement((LighterASTNode) it3.next(), element.getStartOffset(), element.getEndOffset(), element.getTreeStructure(), element.getLighterASTNode()));
                }
                return unreachableCodeLightTreeHelper.mergeAdjacentTextRanges(arrayList3);
            }
        };
        NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                LighterASTNode lighterASTNodeInlineModifier;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType tokenType = node.getTokenType();
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.CLASS)) {
                    KtKeywordToken CLASS_KEYWORD = KtTokens.CLASS_KEYWORD;
                    Intrinsics.checkNotNullExpressionValue(CLASS_KEYWORD, "CLASS_KEYWORD");
                    lighterASTNodeInlineModifier = LightTreePositioningStrategiesKt.findChildByType(tree, node, CLASS_KEYWORD);
                } else if (Intrinsics.areEqual(tokenType, KtNodeTypes.OBJECT_DECLARATION)) {
                    KtKeywordToken OBJECT_KEYWORD2 = KtTokens.OBJECT_KEYWORD;
                    Intrinsics.checkNotNullExpressionValue(OBJECT_KEYWORD2, "OBJECT_KEYWORD");
                    lighterASTNodeInlineModifier = LightTreePositioningStrategiesKt.findChildByType(tree, node, OBJECT_KEYWORD2);
                } else if (Intrinsics.areEqual(tokenType, KtNodeTypes.FUN)) {
                    lighterASTNodeInlineModifier = LightTreePositioningStrategiesKt.inlineModifier(tree, node);
                    if (lighterASTNodeInlineModifier == null) {
                        KtModifierKeywordToken FUN_KEYWORD3 = KtTokens.FUN_KEYWORD;
                        Intrinsics.checkNotNullExpressionValue(FUN_KEYWORD3, "FUN_KEYWORD");
                        lighterASTNodeInlineModifier = LightTreePositioningStrategiesKt.findChildByType(tree, node, FUN_KEYWORD3);
                    }
                } else {
                    lighterASTNodeInlineModifier = node;
                }
                if (lighterASTNodeInlineModifier == null) {
                    lighterASTNodeInlineModifier = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeInlineModifier, startOffset, endOffset, tree, node);
            }
        };
        LABEL = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$LABEL$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType LABEL_QUALIFIER = KtNodeTypes.LABEL_QUALIFIER;
                Intrinsics.checkNotNullExpressionValue(LABEL_QUALIFIER, "LABEL_QUALIFIER");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, LABEL_QUALIFIER);
                if (lighterASTNodeFindChildByType == null) {
                    lighterASTNodeFindChildByType = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
            }
        };
        COMMAS = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$COMMAS$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public boolean isValid(LighterASTNode node, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                return true;
            }

            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                Ref<LighterASTNode[]> ref = new Ref<>();
                tree.getChildren(node, ref);
                LighterASTNode[] lighterASTNodeArr = ref.get();
                Intrinsics.checkNotNullExpressionValue(lighterASTNodeArr, "get(...)");
                for (LighterASTNode lighterASTNode : lighterASTNodeArr) {
                    if (lighterASTNode != null && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtTokens.COMMA)) {
                        listCreateListBuilder.add(LightTreePositioningStrategyKt.markSingleElement(lighterASTNode, lighterASTNode, startOffset, endOffset, tree, node));
                    }
                }
                return CollectionsKt.build(listCreateListBuilder);
            }
        };
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.ABSTRACT_KEYWORD, KtTokens.OPEN_KEYWORD, KtTokens.SEALED_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        NON_FINAL_MODIFIER_OR_NAME = new ModifierSetBasedLightTreePositioningStrategy(tokenSetCreate);
        DELEGATED_SUPERTYPE_BY_KEYWORD = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$DELEGATED_SUPERTYPE_BY_KEYWORD$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Object next;
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                LighterASTNode parent = tree.getParent(node);
                if (parent == null || !Intrinsics.areEqual(parent.getTokenType(), KtNodeTypes.DELEGATED_SUPER_TYPE_ENTRY)) {
                    return super.mark(node, startOffset, endOffset, tree);
                }
                Iterator<T> it = LightTreeUtilsKt.getChildren(parent, tree).iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((LighterASTNode) next).getTokenType(), KtTokens.BY_KEYWORD));
                LighterASTNode lighterASTNode = (LighterASTNode) next;
                if (lighterASTNode == null) {
                    lighterASTNode = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNode, startOffset, endOffset, tree, node);
            }
        };
        CALL_ELEMENT_WITH_DOT = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$CALL_ELEMENT_WITH_DOT$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                List<TextRange> listMark = LightTreePositioningStrategies.INSTANCE.getSELECTOR_BY_QUALIFIED().mark(node, startOffset, endOffset, tree);
                if (listMark.size() != 1) {
                    return listMark;
                }
                TextRange textRange = (TextRange) CollectionsKt.first((List) listMark);
                List<TextRange> listMark2 = LightTreePositioningStrategies.INSTANCE.getSAFE_ACCESS().mark(node, startOffset, endOffset, tree);
                return listMark2.size() == 1 ? CollectionsKt.listOf(new TextRange(((TextRange) CollectionsKt.first((List) listMark2)).getStartOffset(), textRange.getEndOffset())) : listMark2;
            }
        };
        TYPEALIAS_TYPE_REFERENCE = new LightTreePositioningStrategy() { // from class: org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies$TYPEALIAS_TYPE_REFERENCE$1
            @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
            public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
                Intrinsics.checkNotNullParameter(node, "node");
                Intrinsics.checkNotNullParameter(tree, "tree");
                IElementType TYPE_REFERENCE = KtNodeTypes.TYPE_REFERENCE;
                Intrinsics.checkNotNullExpressionValue(TYPE_REFERENCE, "TYPE_REFERENCE");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, node, TYPE_REFERENCE);
                if (lighterASTNodeFindChildByType == null) {
                    lighterASTNodeFindChildByType = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
            }
        };
    }

    public final LightTreePositioningStrategy getDEFAULT() {
        return DEFAULT;
    }

    public final LightTreePositioningStrategy getSUPERTYPES_LIST() {
        return SUPERTYPES_LIST;
    }

    public final LightTreePositioningStrategy getVAL_OR_VAR_NODE() {
        return VAL_OR_VAR_NODE;
    }

    public final LightTreePositioningStrategy getCOMPANION_OBJECT() {
        return COMPANION_OBJECT;
    }

    public final LightTreePositioningStrategy getSECONDARY_CONSTRUCTOR_DELEGATION_CALL() {
        return SECONDARY_CONSTRUCTOR_DELEGATION_CALL;
    }

    public final LightTreePositioningStrategy getDECLARATION_RETURN_TYPE() {
        return DECLARATION_RETURN_TYPE;
    }

    public final LightTreePositioningStrategy getDECLARATION_START_TO_NAME() {
        return DECLARATION_START_TO_NAME;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LightTreePositioningStrategies.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0012\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\fH\u0016J<\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\u0006\u0010\u0006\u001a\u00020\u0007H\u0014¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategies$BaseDeclarationNameStrategy;", "Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;", "()V", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "startOffset", "", "endOffset", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "markNameIdentifier", "nameIdentifier", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static class BaseDeclarationNameStrategy extends LightTreePositioningStrategy {
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(tree, node);
            if (lighterASTNodeNameIdentifier != null) {
                return markNameIdentifier(lighterASTNodeNameIdentifier, startOffset, endOffset, tree, node);
            }
            if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.FUN)) {
                return LightTreePositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE().mark(node, startOffset, endOffset, tree);
            }
            return LightTreePositioningStrategies.INSTANCE.getDEFAULT().mark(node, startOffset, endOffset, tree);
        }

        protected List<TextRange> markNameIdentifier(LighterASTNode nameIdentifier, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree, LighterASTNode node) {
            Intrinsics.checkNotNullParameter(nameIdentifier, "nameIdentifier");
            Intrinsics.checkNotNullParameter(tree, "tree");
            Intrinsics.checkNotNullParameter(node, "node");
            return LightTreePositioningStrategyKt.markElement(nameIdentifier, startOffset, endOffset, tree, node);
        }
    }

    public final LightTreePositioningStrategy getDECLARATION_NAME() {
        return DECLARATION_NAME;
    }

    public final LightTreePositioningStrategy getDECLARATION_NAME_ONLY() {
        return DECLARATION_NAME_ONLY;
    }

    public final LightTreePositioningStrategy getACTUAL_DECLARATION_NAME() {
        return ACTUAL_DECLARATION_NAME;
    }

    public final LightTreePositioningStrategy getDECLARATION_SIGNATURE() {
        return DECLARATION_SIGNATURE;
    }

    public final LightTreePositioningStrategy getDECLARATION_SIGNATURE_OR_DEFAULT() {
        return DECLARATION_SIGNATURE_OR_DEFAULT;
    }

    public final LightTreePositioningStrategy getLAST_CHILD() {
        return LAST_CHILD;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isDeclaration(LighterASTNode lighterASTNode) {
        IElementType tokenType = lighterASTNode.getTokenType();
        if (Intrinsics.areEqual(tokenType, KtNodeTypes.PRIMARY_CONSTRUCTOR) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.SECONDARY_CONSTRUCTOR) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.FUN) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.FUNCTION_LITERAL) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.PROPERTY) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.PROPERTY_ACCESSOR) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.CLASS) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.OBJECT_DECLARATION)) {
            return true;
        }
        return Intrinsics.areEqual(tokenType, KtNodeTypes.CLASS_INITIALIZER);
    }

    /* JADX INFO: compiled from: LightTreePositioningStrategies.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0012\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ4\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0012H\u0016J@\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u00122\u0006\u0010\u0014\u001a\u00020\rH\u0004R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategies$ModifierSetBasedLightTreePositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;", "tokens", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "([Lcom/intellij/psi/tree/IElementType;)V", "modifierSet", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "(Lcom/intellij/psi/tree/TokenSet;)V", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "startOffset", "", "endOffset", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "markModifier", "baseNode", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static class ModifierSetBasedLightTreePositioningStrategy extends LightTreePositioningStrategy {
        private final TokenSet modifierSet;

        public ModifierSetBasedLightTreePositioningStrategy(TokenSet modifierSet) {
            Intrinsics.checkNotNullParameter(modifierSet, "modifierSet");
            this.modifierSet = modifierSet;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public ModifierSetBasedLightTreePositioningStrategy(IElementType... tokens) {
            Intrinsics.checkNotNullParameter(tokens, "tokens");
            TokenSet tokenSetCreate = TokenSet.create((IElementType[]) Arrays.copyOf(tokens, tokens.length));
            Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
            this(tokenSetCreate);
        }

        protected final List<TextRange> markModifier(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree, LighterASTNode baseNode) {
            LighterASTNode lighterASTNodeModifierList;
            LighterASTNode lighterASTNodeFindChildByType;
            Intrinsics.checkNotNullParameter(tree, "tree");
            Intrinsics.checkNotNullParameter(baseNode, "baseNode");
            if (node == null || (lighterASTNodeModifierList = LightTreePositioningStrategiesKt.modifierList(tree, node)) == null || (lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, lighterASTNodeModifierList, this.modifierSet)) == null) {
                return null;
            }
            return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, baseNode);
        }

        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            List<TextRange> listMarkModifier = markModifier(node, startOffset, endOffset, tree, node);
            if (listMarkModifier != null) {
                return listMarkModifier;
            }
            LighterASTNode lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(tree, node);
            if (lighterASTNodeNameIdentifier != null) {
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeNameIdentifier, startOffset, endOffset, tree, node);
            }
            IElementType tokenType = node.getTokenType();
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.OBJECT_DECLARATION)) {
                LighterASTNode lighterASTNodeObjectKeyword = LightTreePositioningStrategiesKt.objectKeyword(tree, node);
                Intrinsics.checkNotNull(lighterASTNodeObjectKeyword);
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeObjectKeyword, startOffset, endOffset, tree, node);
            }
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.PROPERTY_ACCESSOR)) {
                return LightTreePositioningStrategyKt.markElement(LightTreePositioningStrategiesKt.accessorNamePlaceholder(tree, node), startOffset, endOffset, tree, node);
            }
            return LightTreePositioningStrategyKt.markElement$default(node, startOffset, endOffset, tree, null, 16, null);
        }
    }

    /* JADX INFO: compiled from: LightTreePositioningStrategies.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategies$InlineFunLightTreePositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategies$ModifierSetBasedLightTreePositioningStrategy;", "()V", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "startOffset", "", "endOffset", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class InlineFunLightTreePositioningStrategy extends ModifierSetBasedLightTreePositioningStrategy {
        /* JADX WARN: Illegal instructions before constructor call */
        public InlineFunLightTreePositioningStrategy() {
            KtModifierKeywordToken INLINE_KEYWORD = KtTokens.INLINE_KEYWORD;
            Intrinsics.checkNotNullExpressionValue(INLINE_KEYWORD, "INLINE_KEYWORD");
            super(INLINE_KEYWORD);
        }

        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategies.ModifierSetBasedLightTreePositioningStrategy, org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.PROPERTY)) {
                List<TextRange> listMarkModifier = markModifier(LightTreePositioningStrategiesKt.getter(tree, node), startOffset, endOffset, tree, node);
                if (listMarkModifier != null) {
                    return listMarkModifier;
                }
                List<TextRange> listMarkModifier2 = markModifier(LightTreePositioningStrategiesKt.setter(tree, node), startOffset, endOffset, tree, node);
                return listMarkModifier2 == null ? super.mark(node, startOffset, endOffset, tree) : listMarkModifier2;
            }
            return super.mark(node, startOffset, endOffset, tree);
        }
    }

    public final LightTreePositioningStrategy getVISIBILITY_MODIFIER() {
        return VISIBILITY_MODIFIER;
    }

    public final LightTreePositioningStrategy getMODALITY_MODIFIER() {
        return MODALITY_MODIFIER;
    }

    public final LightTreePositioningStrategy getABSTRACT_MODIFIER() {
        return ABSTRACT_MODIFIER;
    }

    public final LightTreePositioningStrategy getOPEN_MODIFIER() {
        return OPEN_MODIFIER;
    }

    public final LightTreePositioningStrategy getOVERRIDE_MODIFIER() {
        return OVERRIDE_MODIFIER;
    }

    public final LightTreePositioningStrategy getPRIVATE_MODIFIER() {
        return PRIVATE_MODIFIER;
    }

    public final LightTreePositioningStrategy getLATEINIT_MODIFIER() {
        return LATEINIT_MODIFIER;
    }

    public final LightTreePositioningStrategy getVARIANCE_MODIFIER() {
        return VARIANCE_MODIFIER;
    }

    public final LightTreePositioningStrategy getCONST_MODIFIER() {
        return CONST_MODIFIER;
    }

    public final LightTreePositioningStrategy getFUN_MODIFIER() {
        return FUN_MODIFIER;
    }

    public final LightTreePositioningStrategy getSUSPEND_MODIFIER() {
        return SUSPEND_MODIFIER;
    }

    public final LightTreePositioningStrategy getINLINE_OR_VALUE_MODIFIER() {
        return INLINE_OR_VALUE_MODIFIER;
    }

    public final LightTreePositioningStrategy getINNER_MODIFIER() {
        return INNER_MODIFIER;
    }

    public final LightTreePositioningStrategy getDATA_MODIFIER() {
        return DATA_MODIFIER;
    }

    public final LightTreePositioningStrategy getOPERATOR_MODIFIER() {
        return OPERATOR_MODIFIER;
    }

    public final LightTreePositioningStrategy getENUM_MODIFIER() {
        return ENUM_MODIFIER;
    }

    public final LightTreePositioningStrategy getTAILREC_MODIFIER() {
        return TAILREC_MODIFIER;
    }

    public final LightTreePositioningStrategy getEXTERNAL_MODIFIER() {
        return EXTERNAL_MODIFIER;
    }

    public final LightTreePositioningStrategy getEXPECT_ACTUAL_MODIFIER() {
        return EXPECT_ACTUAL_MODIFIER;
    }

    public final LightTreePositioningStrategy getOBJECT_KEYWORD() {
        return OBJECT_KEYWORD;
    }

    public final LightTreePositioningStrategy getFIELD_KEYWORD() {
        return FIELD_KEYWORD;
    }

    public final LightTreePositioningStrategy getPROPERTY_DELEGATE() {
        return PROPERTY_DELEGATE;
    }

    public final LightTreePositioningStrategy getINLINE_PARAMETER_MODIFIER() {
        return INLINE_PARAMETER_MODIFIER;
    }

    public final LightTreePositioningStrategy getINLINE_FUN_MODIFIER() {
        return INLINE_FUN_MODIFIER;
    }

    public final LightTreePositioningStrategy getOPERATOR() {
        return OPERATOR;
    }

    public final LightTreePositioningStrategy getPARAMETER_DEFAULT_VALUE() {
        return PARAMETER_DEFAULT_VALUE;
    }

    public final LightTreePositioningStrategy getPARAMETERS_WITH_DEFAULT_VALUE() {
        return PARAMETERS_WITH_DEFAULT_VALUE;
    }

    public final LightTreePositioningStrategy getPARAMETER_VARARG_MODIFIER() {
        return PARAMETER_VARARG_MODIFIER;
    }

    public final LightTreePositioningStrategy getNAME_OF_NAMED_ARGUMENT() {
        return NAME_OF_NAMED_ARGUMENT;
    }

    public final LightTreePositioningStrategy getVALUE_ARGUMENTS_LIST() {
        return VALUE_ARGUMENTS_LIST;
    }

    public final LightTreePositioningStrategy getVALUE_ARGUMENTS() {
        return VALUE_ARGUMENTS;
    }

    public final LightTreePositioningStrategy getDOT_BY_QUALIFIED() {
        return DOT_BY_QUALIFIED;
    }

    public final LightTreePositioningStrategy getSELECTOR_BY_QUALIFIED() {
        return SELECTOR_BY_QUALIFIED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LighterASTNode referencedTypeExpression(FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure, LighterASTNode lighterASTNode) {
        IElementType tokenType = lighterASTNode.getTokenType();
        if (!Intrinsics.areEqual(tokenType, KtNodeTypes.USER_TYPE)) {
            if (Intrinsics.areEqual(tokenType, KtNodeTypes.NULLABLE_TYPE)) {
                TokenSet TYPE_ELEMENT_TYPES = KtTokenSets.TYPE_ELEMENT_TYPES;
                Intrinsics.checkNotNullExpressionValue(TYPE_ELEMENT_TYPES, "TYPE_ELEMENT_TYPES");
                LighterASTNode lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(flyweightCapableTreeStructure, lighterASTNode, TYPE_ELEMENT_TYPES);
                if (lighterASTNodeFindChildByType != null) {
                    return INSTANCE.referencedTypeExpression(flyweightCapableTreeStructure, lighterASTNodeFindChildByType);
                }
            }
            return null;
        }
        IElementType REFERENCE_EXPRESSION = KtNodeTypes.REFERENCE_EXPRESSION;
        Intrinsics.checkNotNullExpressionValue(REFERENCE_EXPRESSION, "REFERENCE_EXPRESSION");
        LighterASTNode lighterASTNodeFindChildByType2 = LightTreePositioningStrategiesKt.findChildByType(flyweightCapableTreeStructure, lighterASTNode, REFERENCE_EXPRESSION);
        if (lighterASTNodeFindChildByType2 != null) {
            return lighterASTNodeFindChildByType2;
        }
        IElementType ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION = KtNodeTypes.ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION;
        Intrinsics.checkNotNullExpressionValue(ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION, "ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION");
        return LightTreePositioningStrategiesKt.findChildByType(flyweightCapableTreeStructure, lighterASTNode, ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION);
    }

    public final LightTreePositioningStrategy getFUN_INTERFACE() {
        return FUN_INTERFACE;
    }

    public final LightTreePositioningStrategy getREFERENCE_BY_QUALIFIED() {
        return REFERENCE_BY_QUALIFIED;
    }

    public final LightTreePositioningStrategy getREFERENCED_NAME_BY_QUALIFIED() {
        return REFERENCED_NAME_BY_QUALIFIED;
    }

    /* JADX INFO: compiled from: LightTreePositioningStrategies.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J4\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategies$FindReferencePositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;", "locateReferencedName", "", "(Z)V", "getLocateReferencedName", "()Z", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "startOffset", "", "endOffset", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FindReferencePositioningStrategy extends LightTreePositioningStrategy {
        private final boolean locateReferencedName;

        public FindReferencePositioningStrategy(boolean z) {
            this.locateReferencedName = z;
        }

        public final boolean getLocateReferencedName() {
            return this.locateReferencedName;
        }

        /* JADX WARN: Code duplicated, block: B:16:0x004f  */
        /* JADX WARN: Code duplicated, block: B:49:0x00e6  */
        /* JADX WARN: Code duplicated, block: B:91:0x0183  */
        @Override // org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategy
        public List<TextRange> mark(LighterASTNode node, int startOffset, int endOffset, FlyweightCapableTreeStructure<LighterASTNode> tree) {
            LighterASTNode lighterASTNodeReferenceExpression;
            LighterASTNode lighterASTNodeFindChildByType;
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(tree, "tree");
            if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.BINARY_EXPRESSION)) {
                TokenSet AUGMENTED_ASSIGNMENTS = KtTokens.AUGMENTED_ASSIGNMENTS;
                Intrinsics.checkNotNullExpressionValue(AUGMENTED_ASSIGNMENTS, "AUGMENTED_ASSIGNMENTS");
                LighterASTNode lighterASTNodeFindDescendantByTypes = LightTreePositioningStrategiesKt.findDescendantByTypes(tree, node, AUGMENTED_ASSIGNMENTS);
                if (lighterASTNodeFindDescendantByTypes != null) {
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindDescendantByTypes, startOffset, endOffset, tree, node);
                }
            }
            if (!Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.BINARY_EXPRESSION)) {
                if (!Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.CALL_EXPRESSION)) {
                }
                lighterASTNodeReferenceExpression = LightTreePositioningStrategiesKt.referenceExpression(tree, node, this.locateReferencedName);
                if (lighterASTNodeReferenceExpression == null) {
                    lighterASTNodeReferenceExpression = node;
                }
                return LightTreePositioningStrategyKt.markElement(lighterASTNodeReferenceExpression, startOffset, endOffset, tree, node);
            }
            KtSingleValueToken EQ = KtTokens.EQ;
            Intrinsics.checkNotNullExpressionValue(EQ, "EQ");
            if (LightTreePositioningStrategiesKt.findDescendantByType(tree, node, EQ, false) != null) {
                LighterASTNode lighterASTNodeFindExpressionDeep = LightTreePositioningStrategiesKt.findExpressionDeep(tree, node);
                if (lighterASTNodeFindExpressionDeep != null) {
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindExpressionDeep, startOffset, endOffset, tree, node);
                }
            } else {
                if (!Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.CALL_EXPRESSION) || Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.CONSTRUCTOR_DELEGATION_CALL)) {
                    lighterASTNodeReferenceExpression = LightTreePositioningStrategiesKt.referenceExpression(tree, node, this.locateReferencedName);
                    if (lighterASTNodeReferenceExpression == null) {
                        lighterASTNodeReferenceExpression = node;
                    }
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeReferenceExpression, startOffset, endOffset, tree, node);
                }
                if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.PROPERTY_DELEGATE)) {
                    LighterASTNode lighterASTNodeFindExpressionDeep2 = LightTreePositioningStrategiesKt.findExpressionDeep(tree, node);
                    if (lighterASTNodeFindExpressionDeep2 == null) {
                        lighterASTNodeFindExpressionDeep2 = node;
                    }
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindExpressionDeep2, startOffset, endOffset, tree, node);
                }
                if (!Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.ANNOTATION_ENTRY)) {
                    if (LightTreePositioningStrategies.nodeTypesWithOperation.contains(node.getTokenType())) {
                        LighterASTNode lighterASTNodeOperationReference = LightTreePositioningStrategiesKt.operationReference(tree, node);
                        if (lighterASTNodeOperationReference == null) {
                            lighterASTNodeOperationReference = node;
                        }
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeOperationReference, startOffset, endOffset, tree, node);
                    }
                    if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.TYPE_REFERENCE)) {
                        IElementType NULLABLE_TYPE = KtNodeTypes.NULLABLE_TYPE;
                        Intrinsics.checkNotNullExpressionValue(NULLABLE_TYPE, "NULLABLE_TYPE");
                        LighterASTNode lighterASTNodeFindChildByType2 = LightTreePositioningStrategiesKt.findChildByType(tree, node, NULLABLE_TYPE);
                        if (lighterASTNodeFindChildByType2 != null) {
                            IElementType USER_TYPE = KtNodeTypes.USER_TYPE;
                            Intrinsics.checkNotNullExpressionValue(USER_TYPE, "USER_TYPE");
                            lighterASTNodeFindChildByType = LightTreePositioningStrategiesKt.findChildByType(tree, lighterASTNodeFindChildByType2, USER_TYPE);
                            if (lighterASTNodeFindChildByType == null) {
                                lighterASTNodeFindChildByType = node;
                            }
                        } else {
                            lighterASTNodeFindChildByType = node;
                        }
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType, startOffset, endOffset, tree, node);
                    }
                    if (Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.IMPORT_DIRECTIVE)) {
                        TokenSet INSIDE_DIRECTIVE_EXPRESSIONS = KtTokenSets.INSIDE_DIRECTIVE_EXPRESSIONS;
                        Intrinsics.checkNotNullExpressionValue(INSIDE_DIRECTIVE_EXPRESSIONS, "INSIDE_DIRECTIVE_EXPRESSIONS");
                        LighterASTNode lighterASTNodeFindChildByType3 = LightTreePositioningStrategiesKt.findChildByType(tree, node, INSIDE_DIRECTIVE_EXPRESSIONS);
                        if (lighterASTNodeFindChildByType3 == null) {
                            lighterASTNodeFindChildByType3 = node;
                        }
                        return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindChildByType3, startOffset, endOffset, tree, node);
                    }
                    if (!Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.DOT_QUALIFIED_EXPRESSION) && !Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.SAFE_ACCESS_EXPRESSION) && !Intrinsics.areEqual(node.getTokenType(), KtNodeTypes.CALLABLE_REFERENCE_EXPRESSION)) {
                        return super.mark(node, startOffset, endOffset, tree);
                    }
                } else {
                    IElementType CONSTRUCTOR_CALLEE = KtNodeTypes.CONSTRUCTOR_CALLEE;
                    Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR_CALLEE, "CONSTRUCTOR_CALLEE");
                    LighterASTNode lighterASTNodeFindDescendantByType$default = LightTreePositioningStrategiesKt.findDescendantByType$default(tree, node, CONSTRUCTOR_CALLEE, false, 4, null);
                    if (lighterASTNodeFindDescendantByType$default == null) {
                        lighterASTNodeFindDescendantByType$default = node;
                    }
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeFindDescendantByType$default, startOffset, endOffset, tree, node);
                }
            }
            LighterASTNode lighterASTNodeSelector = LightTreePositioningStrategiesKt.selector(tree, node);
            if (lighterASTNodeSelector != null) {
                IElementType tokenType = lighterASTNodeSelector.getTokenType();
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.REFERENCE_EXPRESSION)) {
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeSelector, startOffset, endOffset, tree, node);
                }
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.CALL_EXPRESSION) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.CONSTRUCTOR_DELEGATION_CALL) ? true : Intrinsics.areEqual(tokenType, KtNodeTypes.SUPER_TYPE_CALL_ENTRY)) {
                    LighterASTNode lighterASTNodeReferenceExpression2 = LightTreePositioningStrategiesKt.referenceExpression(tree, lighterASTNodeSelector, this.locateReferencedName);
                    if (lighterASTNodeReferenceExpression2 != null) {
                        lighterASTNodeSelector = lighterASTNodeReferenceExpression2;
                    }
                    return LightTreePositioningStrategyKt.markElement(lighterASTNodeSelector, startOffset, endOffset, tree, node);
                }
            }
            return super.mark(node, startOffset, endOffset, tree);
        }
    }

    public final LightTreePositioningStrategy getWHEN_EXPRESSION() {
        return WHEN_EXPRESSION;
    }

    public final LightTreePositioningStrategy getIF_EXPRESSION() {
        return IF_EXPRESSION;
    }

    public final LightTreePositioningStrategy getELSE_ENTRY() {
        return ELSE_ENTRY;
    }

    public final LightTreePositioningStrategy getARRAY_ACCESS() {
        return ARRAY_ACCESS;
    }

    public final LightTreePositioningStrategy getSAFE_ACCESS() {
        return SAFE_ACCESS;
    }

    public final LightTreePositioningStrategy getAS_TYPE() {
        return AS_TYPE;
    }

    public final LightTreePositioningStrategy getUSELESS_ELVIS() {
        return USELESS_ELVIS;
    }

    public final LightTreePositioningStrategy getRETURN_WITH_LABEL() {
        return RETURN_WITH_LABEL;
    }

    public final LightTreePositioningStrategy getWHOLE_ELEMENT() {
        return WHOLE_ELEMENT;
    }

    public final LightTreePositioningStrategy getLONG_LITERAL_SUFFIX() {
        return LONG_LITERAL_SUFFIX;
    }

    public final LightTreePositioningStrategy getREIFIED_MODIFIER() {
        return REIFIED_MODIFIER;
    }

    public final LightTreePositioningStrategy getTYPE_PARAMETERS_LIST() {
        return TYPE_PARAMETERS_LIST;
    }

    public final LightTreePositioningStrategy getNAME_IDENTIFIER() {
        return NAME_IDENTIFIER;
    }

    public final LightTreePositioningStrategy getREDUNDANT_NULLABLE() {
        return REDUNDANT_NULLABLE;
    }

    public final LightTreePositioningStrategy getQUESTION_MARK_BY_TYPE() {
        return QUESTION_MARK_BY_TYPE;
    }

    public final LightTreePositioningStrategy getANNOTATION_USE_SITE() {
        return ANNOTATION_USE_SITE;
    }

    public final LightTreePositioningStrategy getIMPORT_LAST_NAME() {
        return IMPORT_LAST_NAME;
    }

    public final LightTreePositioningStrategy getIMPORT_ALIAS() {
        return IMPORT_ALIAS;
    }

    public final LightTreePositioningStrategy getSPREAD_OPERATOR() {
        return SPREAD_OPERATOR;
    }

    public final LightTreePositioningStrategy getDECLARATION_WITH_BODY() {
        return DECLARATION_WITH_BODY;
    }

    public final LightTreePositioningStrategy getUNREACHABLE_CODE() {
        return UNREACHABLE_CODE;
    }

    public final LightTreePositioningStrategy getNOT_SUPPORTED_IN_INLINE_MOST_RELEVANT() {
        return NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT;
    }

    public final LightTreePositioningStrategy getLABEL() {
        return LABEL;
    }

    public final LightTreePositioningStrategy getCOMMAS() {
        return COMMAS;
    }

    public final LightTreePositioningStrategy getNON_FINAL_MODIFIER_OR_NAME() {
        return NON_FINAL_MODIFIER_OR_NAME;
    }

    public final LightTreePositioningStrategy getDELEGATED_SUPERTYPE_BY_KEYWORD() {
        return DELEGATED_SUPERTYPE_BY_KEYWORD;
    }

    public final LightTreePositioningStrategy getCALL_ELEMENT_WITH_DOT() {
        return CALL_ELEMENT_WITH_DOT;
    }

    public final LightTreePositioningStrategy getTYPEALIAS_TYPE_REFERENCE() {
        return TYPEALIAS_TYPE_REFERENCE;
    }
}
