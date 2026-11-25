package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SourceElementPositioningStrategies.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0015\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0003\b\u009d\u0001\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006R\u0011\u0010)\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0006R\u0011\u0010/\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0006R\u0011\u00101\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0006R\u0011\u00103\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0006R\u0011\u00105\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0006R\u0011\u00107\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0006R\u0011\u00109\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\u0006R\u0011\u0010;\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\u0006R\u0011\u0010=\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\u0006R\u0011\u0010?\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\u0006R\u0011\u0010A\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0006R\u0011\u0010C\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\u0006R\u0011\u0010E\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\u0006R\u0011\u0010G\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\u0006R\u0011\u0010I\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\u0006R\u0011\u0010K\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\u0006R\u0011\u0010M\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bN\u0010\u0006R\u0011\u0010O\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010\u0006R\u0011\u0010Q\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010\u0006R\u0011\u0010S\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010\u0006R\u0011\u0010U\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bV\u0010\u0006R\u0011\u0010W\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bX\u0010\u0006R\u0011\u0010Y\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010\u0006R\u0011\u0010[\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010\u0006R\u0011\u0010]\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b^\u0010\u0006R\u0011\u0010_\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010\u0006R\u0011\u0010a\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bb\u0010\u0006R\u0011\u0010c\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bd\u0010\u0006R\u0011\u0010e\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u0010\u0006R\u0011\u0010g\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bh\u0010\u0006R\u0011\u0010i\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bj\u0010\u0006R\u0011\u0010k\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bl\u0010\u0006R\u0011\u0010m\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bn\u0010\u0006R\u0011\u0010o\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bp\u0010\u0006R\u0011\u0010q\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\br\u0010\u0006R\u0011\u0010s\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u0010\u0006R\u0011\u0010u\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bv\u0010\u0006R\u0011\u0010w\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bx\u0010\u0006R\u0011\u0010y\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bz\u0010\u0006R\u0011\u0010{\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b|\u0010\u0006R\u0011\u0010}\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b~\u0010\u0006R\u0012\u0010\u007f\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0001\u0010\u0006R\u0013\u0010\u0081\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0001\u0010\u0006R\u0013\u0010\u0083\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u0006R\u0013\u0010\u0085\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0001\u0010\u0006R\u0013\u0010\u0087\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0001\u0010\u0006R\u0013\u0010\u0089\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0001\u0010\u0006R\u0013\u0010\u008b\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008c\u0001\u0010\u0006R\u0013\u0010\u008d\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0001\u0010\u0006R\u0013\u0010\u008f\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0090\u0001\u0010\u0006R\u0013\u0010\u0091\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0092\u0001\u0010\u0006R\u0013\u0010\u0093\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0094\u0001\u0010\u0006R\u0013\u0010\u0095\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0006R\u0013\u0010\u0097\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0098\u0001\u0010\u0006R\u0013\u0010\u0099\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009a\u0001\u0010\u0006R\u0013\u0010\u009b\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009c\u0001\u0010\u0006R\u0013\u0010\u009d\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009e\u0001\u0010\u0006R\u0013\u0010\u009f\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b \u0001\u0010\u0006¨\u0006¡\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/SourceElementPositioningStrategies;", "", "()V", "ABSTRACT_MODIFIER", "Lorg/jetbrains/kotlin/diagnostics/SourceElementPositioningStrategy;", "getABSTRACT_MODIFIER", "()Lorg/jetbrains/kotlin/diagnostics/SourceElementPositioningStrategy;", "ACTUAL_DECLARATION_NAME", "getACTUAL_DECLARATION_NAME", "ANNOTATION_USE_SITE", "getANNOTATION_USE_SITE", "ARRAY_ACCESS", "getARRAY_ACCESS", "AS_TYPE", "getAS_TYPE", "CALL_ELEMENT_WITH_DOT", "getCALL_ELEMENT_WITH_DOT", "COMMAS", "getCOMMAS", "COMPANION_OBJECT", "getCOMPANION_OBJECT", "CONST_MODIFIER", "getCONST_MODIFIER", "DATA_MODIFIER", "getDATA_MODIFIER", "DECLARATION_NAME", "getDECLARATION_NAME", "DECLARATION_NAME_ONLY", "getDECLARATION_NAME_ONLY", "DECLARATION_RETURN_TYPE", "getDECLARATION_RETURN_TYPE", "DECLARATION_SIGNATURE", "getDECLARATION_SIGNATURE", "DECLARATION_SIGNATURE_OR_DEFAULT", "getDECLARATION_SIGNATURE_OR_DEFAULT", "DECLARATION_START_TO_NAME", "getDECLARATION_START_TO_NAME", "DECLARATION_WITH_BODY", "getDECLARATION_WITH_BODY", "DEFAULT", "getDEFAULT", "DELEGATED_SUPERTYPE_BY_KEYWORD", "getDELEGATED_SUPERTYPE_BY_KEYWORD", "DOT_BY_QUALIFIED", "getDOT_BY_QUALIFIED", "ELSE_ENTRY", "getELSE_ENTRY", "ENUM_MODIFIER", "getENUM_MODIFIER", "EXPECT_ACTUAL_MODIFIER", "getEXPECT_ACTUAL_MODIFIER", "EXTERNAL_MODIFIER", "getEXTERNAL_MODIFIER", "FIELD_KEYWORD", "getFIELD_KEYWORD", "FUN_INTERFACE", "getFUN_INTERFACE", "FUN_MODIFIER", "getFUN_MODIFIER", "IF_EXPRESSION", "getIF_EXPRESSION", "IMPORT_ALIAS", "getIMPORT_ALIAS", "IMPORT_LAST_NAME", "getIMPORT_LAST_NAME", "INCOMPATIBLE_DECLARATION", "getINCOMPATIBLE_DECLARATION", "INLINE_FUN_MODIFIER", "getINLINE_FUN_MODIFIER", "INLINE_OR_VALUE_MODIFIER", "getINLINE_OR_VALUE_MODIFIER", "INLINE_PARAMETER_MODIFIER", "getINLINE_PARAMETER_MODIFIER", "INNER_MODIFIER", "getINNER_MODIFIER", "LABEL", "getLABEL", "LATEINIT_MODIFIER", "getLATEINIT_MODIFIER", "LONG_LITERAL_SUFFIX", "getLONG_LITERAL_SUFFIX", "MODALITY_MODIFIER", "getMODALITY_MODIFIER", "NAME_IDENTIFIER", "getNAME_IDENTIFIER", "NAME_OF_NAMED_ARGUMENT", "getNAME_OF_NAMED_ARGUMENT", "NON_FINAL_MODIFIER_OR_NAME", "getNON_FINAL_MODIFIER_OR_NAME", "NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT", "getNOT_SUPPORTED_IN_INLINE_MOST_RELEVANT", "OBJECT_KEYWORD", "getOBJECT_KEYWORD", "OPEN_MODIFIER", "getOPEN_MODIFIER", "OPERATOR", "getOPERATOR", "OPERATOR_MODIFIER", "getOPERATOR_MODIFIER", "OVERRIDE_MODIFIER", "getOVERRIDE_MODIFIER", "PARAMETERS_WITH_DEFAULT_VALUE", "getPARAMETERS_WITH_DEFAULT_VALUE", "PARAMETER_DEFAULT_VALUE", "getPARAMETER_DEFAULT_VALUE", "PARAMETER_VARARG_MODIFIER", "getPARAMETER_VARARG_MODIFIER", "PRIVATE_MODIFIER", "getPRIVATE_MODIFIER", "PROPERTY_DELEGATE", "getPROPERTY_DELEGATE", "PROPERTY_INITIALIZER", "getPROPERTY_INITIALIZER", "QUESTION_MARK_BY_TYPE", "getQUESTION_MARK_BY_TYPE", "REDUNDANT_NULLABLE", "getREDUNDANT_NULLABLE", "REFERENCED_NAME_BY_QUALIFIED", "getREFERENCED_NAME_BY_QUALIFIED", "REFERENCE_BY_QUALIFIED", "getREFERENCE_BY_QUALIFIED", "REIFIED_MODIFIER", "getREIFIED_MODIFIER", "RETURN_WITH_LABEL", "getRETURN_WITH_LABEL", "SAFE_ACCESS", "getSAFE_ACCESS", "SECONDARY_CONSTRUCTOR_DELEGATION_CALL", "getSECONDARY_CONSTRUCTOR_DELEGATION_CALL", "SELECTOR_BY_QUALIFIED", "getSELECTOR_BY_QUALIFIED", "SPREAD_OPERATOR", "getSPREAD_OPERATOR", "SUPERTYPES_LIST", "getSUPERTYPES_LIST", "SUSPEND_MODIFIER", "getSUSPEND_MODIFIER", "TAILREC_MODIFIER", "getTAILREC_MODIFIER", "TYPEALIAS_TYPE_REFERENCE", "getTYPEALIAS_TYPE_REFERENCE", "TYPE_PARAMETERS_LIST", "getTYPE_PARAMETERS_LIST", "UNREACHABLE_CODE", "getUNREACHABLE_CODE", "USELESS_ELVIS", "getUSELESS_ELVIS", "VALUE_ARGUMENTS", "getVALUE_ARGUMENTS", "VALUE_ARGUMENTS_LIST", "getVALUE_ARGUMENTS_LIST", "VAL_OR_VAR_NODE", "getVAL_OR_VAR_NODE", "VARIANCE_MODIFIER", "getVARIANCE_MODIFIER", "VISIBILITY_MODIFIER", "getVISIBILITY_MODIFIER", "WHEN_EXPRESSION", "getWHEN_EXPRESSION", "WHOLE_ELEMENT", "getWHOLE_ELEMENT", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceElementPositioningStrategies {
    private static final SourceElementPositioningStrategy ABSTRACT_MODIFIER;
    private static final SourceElementPositioningStrategy ACTUAL_DECLARATION_NAME;
    private static final SourceElementPositioningStrategy ANNOTATION_USE_SITE;
    private static final SourceElementPositioningStrategy ARRAY_ACCESS;
    private static final SourceElementPositioningStrategy AS_TYPE;
    private static final SourceElementPositioningStrategy CALL_ELEMENT_WITH_DOT;
    private static final SourceElementPositioningStrategy COMMAS;
    private static final SourceElementPositioningStrategy COMPANION_OBJECT;
    private static final SourceElementPositioningStrategy CONST_MODIFIER;
    private static final SourceElementPositioningStrategy DATA_MODIFIER;
    private static final SourceElementPositioningStrategy DECLARATION_NAME;
    private static final SourceElementPositioningStrategy DECLARATION_NAME_ONLY;
    private static final SourceElementPositioningStrategy DECLARATION_RETURN_TYPE;
    private static final SourceElementPositioningStrategy DECLARATION_SIGNATURE;
    private static final SourceElementPositioningStrategy DECLARATION_SIGNATURE_OR_DEFAULT;
    private static final SourceElementPositioningStrategy DECLARATION_START_TO_NAME;
    private static final SourceElementPositioningStrategy DECLARATION_WITH_BODY;
    private static final SourceElementPositioningStrategy DEFAULT;
    private static final SourceElementPositioningStrategy DELEGATED_SUPERTYPE_BY_KEYWORD;
    private static final SourceElementPositioningStrategy DOT_BY_QUALIFIED;
    private static final SourceElementPositioningStrategy ELSE_ENTRY;
    private static final SourceElementPositioningStrategy ENUM_MODIFIER;
    private static final SourceElementPositioningStrategy EXPECT_ACTUAL_MODIFIER;
    private static final SourceElementPositioningStrategy EXTERNAL_MODIFIER;
    private static final SourceElementPositioningStrategy FIELD_KEYWORD;
    private static final SourceElementPositioningStrategy FUN_INTERFACE;
    private static final SourceElementPositioningStrategy FUN_MODIFIER;
    private static final SourceElementPositioningStrategy IF_EXPRESSION;
    private static final SourceElementPositioningStrategy IMPORT_ALIAS;
    private static final SourceElementPositioningStrategy IMPORT_LAST_NAME;
    private static final SourceElementPositioningStrategy INCOMPATIBLE_DECLARATION;
    private static final SourceElementPositioningStrategy INLINE_FUN_MODIFIER;
    private static final SourceElementPositioningStrategy INLINE_OR_VALUE_MODIFIER;
    private static final SourceElementPositioningStrategy INLINE_PARAMETER_MODIFIER;
    private static final SourceElementPositioningStrategy INNER_MODIFIER;
    public static final SourceElementPositioningStrategies INSTANCE = new SourceElementPositioningStrategies();
    private static final SourceElementPositioningStrategy LABEL;
    private static final SourceElementPositioningStrategy LATEINIT_MODIFIER;
    private static final SourceElementPositioningStrategy LONG_LITERAL_SUFFIX;
    private static final SourceElementPositioningStrategy MODALITY_MODIFIER;
    private static final SourceElementPositioningStrategy NAME_IDENTIFIER;
    private static final SourceElementPositioningStrategy NAME_OF_NAMED_ARGUMENT;
    private static final SourceElementPositioningStrategy NON_FINAL_MODIFIER_OR_NAME;
    private static final SourceElementPositioningStrategy NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT;
    private static final SourceElementPositioningStrategy OBJECT_KEYWORD;
    private static final SourceElementPositioningStrategy OPEN_MODIFIER;
    private static final SourceElementPositioningStrategy OPERATOR;
    private static final SourceElementPositioningStrategy OPERATOR_MODIFIER;
    private static final SourceElementPositioningStrategy OVERRIDE_MODIFIER;
    private static final SourceElementPositioningStrategy PARAMETERS_WITH_DEFAULT_VALUE;
    private static final SourceElementPositioningStrategy PARAMETER_DEFAULT_VALUE;
    private static final SourceElementPositioningStrategy PARAMETER_VARARG_MODIFIER;
    private static final SourceElementPositioningStrategy PRIVATE_MODIFIER;
    private static final SourceElementPositioningStrategy PROPERTY_DELEGATE;
    private static final SourceElementPositioningStrategy PROPERTY_INITIALIZER;
    private static final SourceElementPositioningStrategy QUESTION_MARK_BY_TYPE;
    private static final SourceElementPositioningStrategy REDUNDANT_NULLABLE;
    private static final SourceElementPositioningStrategy REFERENCED_NAME_BY_QUALIFIED;
    private static final SourceElementPositioningStrategy REFERENCE_BY_QUALIFIED;
    private static final SourceElementPositioningStrategy REIFIED_MODIFIER;
    private static final SourceElementPositioningStrategy RETURN_WITH_LABEL;
    private static final SourceElementPositioningStrategy SAFE_ACCESS;
    private static final SourceElementPositioningStrategy SECONDARY_CONSTRUCTOR_DELEGATION_CALL;
    private static final SourceElementPositioningStrategy SELECTOR_BY_QUALIFIED;
    private static final SourceElementPositioningStrategy SPREAD_OPERATOR;
    private static final SourceElementPositioningStrategy SUPERTYPES_LIST;
    private static final SourceElementPositioningStrategy SUSPEND_MODIFIER;
    private static final SourceElementPositioningStrategy TAILREC_MODIFIER;
    private static final SourceElementPositioningStrategy TYPEALIAS_TYPE_REFERENCE;
    private static final SourceElementPositioningStrategy TYPE_PARAMETERS_LIST;
    private static final SourceElementPositioningStrategy UNREACHABLE_CODE;
    private static final SourceElementPositioningStrategy USELESS_ELVIS;
    private static final SourceElementPositioningStrategy VALUE_ARGUMENTS;
    private static final SourceElementPositioningStrategy VALUE_ARGUMENTS_LIST;
    private static final SourceElementPositioningStrategy VAL_OR_VAR_NODE;
    private static final SourceElementPositioningStrategy VARIANCE_MODIFIER;
    private static final SourceElementPositioningStrategy VISIBILITY_MODIFIER;
    private static final SourceElementPositioningStrategy WHEN_EXPRESSION;
    private static final SourceElementPositioningStrategy WHOLE_ELEMENT;

    private SourceElementPositioningStrategies() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        SourceElementPositioningStrategy sourceElementPositioningStrategy = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDEFAULT(), PositioningStrategies.DEFAULT, null, 4, null);
        AbstractSourceElementPositioningStrategy.INSTANCE.setDefault(sourceElementPositioningStrategy);
        DEFAULT = sourceElementPositioningStrategy;
        VAL_OR_VAR_NODE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getVAL_OR_VAR_NODE(), PositioningStrategies.VAL_OR_VAR_NODE, 0 == true ? 1 : 0, 4, null);
        FUN_INTERFACE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getFUN_INTERFACE(), PositioningStrategies.FUN_INTERFACE, null, 4, null);
        int i = 4;
        DefaultConstructorMarker defaultConstructorMarker = null;
        OffsetsOnlyPositioningStrategy offsetsOnlyPositioningStrategy = null;
        COMPANION_OBJECT = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getCOMPANION_OBJECT(), PositioningStrategies.COMPANION_OBJECT, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        int i2 = 4;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        OffsetsOnlyPositioningStrategy offsetsOnlyPositioningStrategy2 = null;
        SECONDARY_CONSTRUCTOR_DELEGATION_CALL = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getSECONDARY_CONSTRUCTOR_DELEGATION_CALL(), PositioningStrategies.SECONDARY_CONSTRUCTOR_DELEGATION_CALL, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        DECLARATION_RETURN_TYPE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDECLARATION_RETURN_TYPE(), PositioningStrategies.DECLARATION_RETURN_TYPE, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        DECLARATION_NAME = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDECLARATION_NAME(), PositioningStrategies.DECLARATION_NAME, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        DECLARATION_NAME_ONLY = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDECLARATION_NAME_ONLY(), PositioningStrategies.DECLARATION_NAME_ONLY, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        DECLARATION_SIGNATURE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), PositioningStrategies.DECLARATION_SIGNATURE, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        DECLARATION_SIGNATURE_OR_DEFAULT = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), PositioningStrategies.DECLARATION_SIGNATURE_OR_DEFAULT, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        VISIBILITY_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getVISIBILITY_MODIFIER(), PositioningStrategies.VISIBILITY_MODIFIER, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        MODALITY_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getMODALITY_MODIFIER(), PositioningStrategies.MODALITY_MODIFIER, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        ABSTRACT_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getABSTRACT_MODIFIER(), PositioningStrategies.ABSTRACT_MODIFIER, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        OPEN_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getOPEN_MODIFIER(), PositioningStrategies.OPEN_MODIFIER, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        OVERRIDE_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getOVERRIDE_MODIFIER(), PositioningStrategies.OVERRIDE_MODIFIER, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        PRIVATE_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getPRIVATE_MODIFIER(), PositioningStrategies.PRIVATE_MODIFIER, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        LATEINIT_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getLATEINIT_MODIFIER(), PositioningStrategies.LATEINIT_MODIFIER, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        VARIANCE_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getVARIANCE_MODIFIER(), PositioningStrategies.VARIANCE_MODIFIER, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        CONST_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getCONST_MODIFIER(), PositioningStrategies.CONST_MODIFIER, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        INLINE_OR_VALUE_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getINLINE_OR_VALUE_MODIFIER(), PositioningStrategies.INLINE_OR_VALUE_MODIFIER, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        INNER_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getINNER_MODIFIER(), PositioningStrategies.INNER_MODIFIER, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        FUN_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getFUN_MODIFIER(), PositioningStrategies.FUN_MODIFIER, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        SUSPEND_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getSUSPEND_MODIFIER(), PositioningStrategies.SUSPEND_MODIFIER, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        DATA_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDATA_MODIFIER(), PositioningStrategies.DATA_MODIFIER, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        EXPECT_ACTUAL_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getEXPECT_ACTUAL_MODIFIER(), PositioningStrategies.EXPECT_ACTUAL_MODIFIER, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        OBJECT_KEYWORD = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getOBJECT_KEYWORD(), PositioningStrategies.OBJECT_KEYWORD, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        OPERATOR = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getOPERATOR(), PositioningStrategies.INSTANCE.getOPERATOR(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        PARAMETER_DEFAULT_VALUE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getPARAMETER_DEFAULT_VALUE(), PositioningStrategies.PARAMETER_DEFAULT_VALUE, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        PARAMETERS_WITH_DEFAULT_VALUE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getPARAMETERS_WITH_DEFAULT_VALUE(), PositioningStrategies.PARAMETERS_WITH_DEFAULT_VALUE, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        PARAMETER_VARARG_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getPARAMETER_VARARG_MODIFIER(), PositioningStrategies.PARAMETER_VARARG_MODIFIER, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        NAME_OF_NAMED_ARGUMENT = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getNAME_OF_NAMED_ARGUMENT(), PositioningStrategies.NAME_OF_NAMED_ARGUMENT, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        VALUE_ARGUMENTS = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getVALUE_ARGUMENTS(), PositioningStrategies.VALUE_ARGUMENTS, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        VALUE_ARGUMENTS_LIST = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getVALUE_ARGUMENTS_LIST(), PositioningStrategies.VALUE_ARGUMENTS_LIST, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        SUPERTYPES_LIST = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getSUPERTYPES_LIST(), PositioningStrategies.SUPERTYPES_LIST, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        DOT_BY_QUALIFIED = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDOT_BY_QUALIFIED(), PositioningStrategies.INSTANCE.getDOT_BY_QUALIFIED(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        SELECTOR_BY_QUALIFIED = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getSELECTOR_BY_QUALIFIED(), PositioningStrategies.INSTANCE.getSELECTOR_BY_QUALIFIED(), offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        REFERENCE_BY_QUALIFIED = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getREFERENCE_BY_QUALIFIED(), PositioningStrategies.INSTANCE.getREFERENCE_BY_QUALIFIED(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        REFERENCED_NAME_BY_QUALIFIED = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getREFERENCED_NAME_BY_QUALIFIED(), PositioningStrategies.INSTANCE.getREFERENCED_NAME_BY_QUALIFIED(), offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        WHEN_EXPRESSION = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getWHEN_EXPRESSION(), PositioningStrategies.WHEN_EXPRESSION, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        IF_EXPRESSION = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getIF_EXPRESSION(), PositioningStrategies.IF_EXPRESSION, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        ELSE_ENTRY = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getELSE_ENTRY(), PositioningStrategies.ELSE_ENTRY, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        ARRAY_ACCESS = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getARRAY_ACCESS(), PositioningStrategies.ARRAY_ACCESS, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        SAFE_ACCESS = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getSAFE_ACCESS(), PositioningStrategies.SAFE_ACCESS, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        AS_TYPE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getAS_TYPE(), PositioningStrategies.AS_TYPE, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        USELESS_ELVIS = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getUSELESS_ELVIS(), PositioningStrategies.USELESS_ELVIS, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        RETURN_WITH_LABEL = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getRETURN_WITH_LABEL(), PositioningStrategies.RETURN_WITH_LABEL, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        PROPERTY_INITIALIZER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getLAST_CHILD(), PositioningStrategies.INSTANCE.getPROPERTY_INITIALIZER(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        WHOLE_ELEMENT = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getWHOLE_ELEMENT(), PositioningStrategies.INSTANCE.getWHOLE_ELEMENT(), offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        LONG_LITERAL_SUFFIX = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getLONG_LITERAL_SUFFIX(), PositioningStrategies.LONG_LITERAL_SUFFIX, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        REIFIED_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getREIFIED_MODIFIER(), PositioningStrategies.INSTANCE.getREIFIED_MODIFIER(), offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        TYPE_PARAMETERS_LIST = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getTYPE_PARAMETERS_LIST(), PositioningStrategies.INSTANCE.getTYPE_PARAMETERS_LIST(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        NAME_IDENTIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getNAME_IDENTIFIER(), PositioningStrategies.INSTANCE.getNAME_IDENTIFIER(), offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        REDUNDANT_NULLABLE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getREDUNDANT_NULLABLE(), PositioningStrategies.REDUNDANT_NULLABLE, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        QUESTION_MARK_BY_TYPE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getQUESTION_MARK_BY_TYPE(), PositioningStrategies.QUESTION_MARK_BY_TYPE, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        ANNOTATION_USE_SITE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getANNOTATION_USE_SITE(), PositioningStrategies.INSTANCE.getANNOTATION_USE_SITE(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        IMPORT_LAST_NAME = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getIMPORT_LAST_NAME(), PositioningStrategies.INSTANCE.getIMPORT_LAST_NAME(), offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        SPREAD_OPERATOR = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getSPREAD_OPERATOR(), PositioningStrategies.INSTANCE.getSPREAD_OPERATOR(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        DECLARATION_WITH_BODY = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDECLARATION_WITH_BODY(), PositioningStrategies.DECLARATION_WITH_BODY, offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        COMMAS = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getCOMMAS(), PositioningStrategies.INSTANCE.getCOMMAS(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        UNREACHABLE_CODE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getUNREACHABLE_CODE(), PsiPositioningStrategies.INSTANCE.getUNREACHABLE_CODE(), offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        ACTUAL_DECLARATION_NAME = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getACTUAL_DECLARATION_NAME(), PsiPositioningStrategies.INSTANCE.getACTUAL_DECLARATION_NAME(), offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        LABEL = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getLABEL(), PositioningStrategies.INSTANCE.getLABEL(), offsetsOnlyPositioningStrategy, i, defaultConstructorMarker);
        INCOMPATIBLE_DECLARATION = sourceElementPositioningStrategy;
        NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getNOT_SUPPORTED_IN_INLINE_MOST_RELEVANT(), PositioningStrategies.NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT, offsetsOnlyPositioningStrategy2, i2, defaultConstructorMarker2);
        int i3 = 4;
        DefaultConstructorMarker defaultConstructorMarker3 = null;
        OffsetsOnlyPositioningStrategy offsetsOnlyPositioningStrategy3 = null;
        INLINE_PARAMETER_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getINLINE_PARAMETER_MODIFIER(), PositioningStrategies.INLINE_PARAMETER_MODIFIER, offsetsOnlyPositioningStrategy3, i3, defaultConstructorMarker3);
        int i4 = 4;
        DefaultConstructorMarker defaultConstructorMarker4 = null;
        OffsetsOnlyPositioningStrategy offsetsOnlyPositioningStrategy4 = null;
        INLINE_FUN_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getINLINE_FUN_MODIFIER(), PositioningStrategies.INLINE_FUN_MODIFIER, offsetsOnlyPositioningStrategy4, i4, defaultConstructorMarker4);
        OPERATOR_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getOPERATOR_MODIFIER(), PositioningStrategies.OPERATOR_MODIFIER, offsetsOnlyPositioningStrategy3, i3, defaultConstructorMarker3);
        NON_FINAL_MODIFIER_OR_NAME = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getNON_FINAL_MODIFIER_OR_NAME(), PositioningStrategies.INSTANCE.getNON_FINAL_MODIFIER_OR_NAME(), offsetsOnlyPositioningStrategy4, i4, defaultConstructorMarker4);
        ENUM_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getENUM_MODIFIER(), PositioningStrategies.ENUM_MODIFIER, offsetsOnlyPositioningStrategy3, i3, defaultConstructorMarker3);
        FIELD_KEYWORD = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getFIELD_KEYWORD(), PositioningStrategies.FIELD_KEYWORD, offsetsOnlyPositioningStrategy4, i4, defaultConstructorMarker4);
        TAILREC_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getTAILREC_MODIFIER(), PositioningStrategies.TAILREC_MODIFIER, offsetsOnlyPositioningStrategy3, i3, defaultConstructorMarker3);
        EXTERNAL_MODIFIER = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getEXTERNAL_MODIFIER(), PositioningStrategies.EXTERNAL_MODIFIER, offsetsOnlyPositioningStrategy4, i4, defaultConstructorMarker4);
        PROPERTY_DELEGATE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getPROPERTY_DELEGATE(), PositioningStrategies.PROPERTY_DELEGATE, offsetsOnlyPositioningStrategy3, i3, defaultConstructorMarker3);
        IMPORT_ALIAS = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getIMPORT_ALIAS(), PositioningStrategies.IMPORT_ALIAS, offsetsOnlyPositioningStrategy4, i4, defaultConstructorMarker4);
        DECLARATION_START_TO_NAME = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDECLARATION_START_TO_NAME(), PositioningStrategies.DECLARATION_START_TO_NAME, offsetsOnlyPositioningStrategy3, i3, defaultConstructorMarker3);
        DELEGATED_SUPERTYPE_BY_KEYWORD = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getDELEGATED_SUPERTYPE_BY_KEYWORD(), PositioningStrategies.INSTANCE.getDELEGATED_SUPERTYPE_BY_KEYWORD(), offsetsOnlyPositioningStrategy4, i4, defaultConstructorMarker4);
        CALL_ELEMENT_WITH_DOT = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getCALL_ELEMENT_WITH_DOT(), PositioningStrategies.CALL_ELEMENT_WITH_DOT, offsetsOnlyPositioningStrategy3, i3, defaultConstructorMarker3);
        TYPEALIAS_TYPE_REFERENCE = new SourceElementPositioningStrategy(LightTreePositioningStrategies.INSTANCE.getTYPEALIAS_TYPE_REFERENCE(), PositioningStrategies.TYPEALIAS_TYPE_REFERENCE, offsetsOnlyPositioningStrategy4, i4, defaultConstructorMarker4);
    }

    public final SourceElementPositioningStrategy getDEFAULT() {
        return DEFAULT;
    }

    public final SourceElementPositioningStrategy getVAL_OR_VAR_NODE() {
        return VAL_OR_VAR_NODE;
    }

    public final SourceElementPositioningStrategy getFUN_INTERFACE() {
        return FUN_INTERFACE;
    }

    public final SourceElementPositioningStrategy getCOMPANION_OBJECT() {
        return COMPANION_OBJECT;
    }

    public final SourceElementPositioningStrategy getSECONDARY_CONSTRUCTOR_DELEGATION_CALL() {
        return SECONDARY_CONSTRUCTOR_DELEGATION_CALL;
    }

    public final SourceElementPositioningStrategy getDECLARATION_RETURN_TYPE() {
        return DECLARATION_RETURN_TYPE;
    }

    public final SourceElementPositioningStrategy getDECLARATION_NAME() {
        return DECLARATION_NAME;
    }

    public final SourceElementPositioningStrategy getDECLARATION_NAME_ONLY() {
        return DECLARATION_NAME_ONLY;
    }

    public final SourceElementPositioningStrategy getDECLARATION_SIGNATURE() {
        return DECLARATION_SIGNATURE;
    }

    public final SourceElementPositioningStrategy getDECLARATION_SIGNATURE_OR_DEFAULT() {
        return DECLARATION_SIGNATURE_OR_DEFAULT;
    }

    public final SourceElementPositioningStrategy getVISIBILITY_MODIFIER() {
        return VISIBILITY_MODIFIER;
    }

    public final SourceElementPositioningStrategy getMODALITY_MODIFIER() {
        return MODALITY_MODIFIER;
    }

    public final SourceElementPositioningStrategy getABSTRACT_MODIFIER() {
        return ABSTRACT_MODIFIER;
    }

    public final SourceElementPositioningStrategy getOPEN_MODIFIER() {
        return OPEN_MODIFIER;
    }

    public final SourceElementPositioningStrategy getOVERRIDE_MODIFIER() {
        return OVERRIDE_MODIFIER;
    }

    public final SourceElementPositioningStrategy getPRIVATE_MODIFIER() {
        return PRIVATE_MODIFIER;
    }

    public final SourceElementPositioningStrategy getLATEINIT_MODIFIER() {
        return LATEINIT_MODIFIER;
    }

    public final SourceElementPositioningStrategy getVARIANCE_MODIFIER() {
        return VARIANCE_MODIFIER;
    }

    public final SourceElementPositioningStrategy getCONST_MODIFIER() {
        return CONST_MODIFIER;
    }

    public final SourceElementPositioningStrategy getINLINE_OR_VALUE_MODIFIER() {
        return INLINE_OR_VALUE_MODIFIER;
    }

    public final SourceElementPositioningStrategy getINNER_MODIFIER() {
        return INNER_MODIFIER;
    }

    public final SourceElementPositioningStrategy getFUN_MODIFIER() {
        return FUN_MODIFIER;
    }

    public final SourceElementPositioningStrategy getSUSPEND_MODIFIER() {
        return SUSPEND_MODIFIER;
    }

    public final SourceElementPositioningStrategy getDATA_MODIFIER() {
        return DATA_MODIFIER;
    }

    public final SourceElementPositioningStrategy getEXPECT_ACTUAL_MODIFIER() {
        return EXPECT_ACTUAL_MODIFIER;
    }

    public final SourceElementPositioningStrategy getOBJECT_KEYWORD() {
        return OBJECT_KEYWORD;
    }

    public final SourceElementPositioningStrategy getOPERATOR() {
        return OPERATOR;
    }

    public final SourceElementPositioningStrategy getPARAMETER_DEFAULT_VALUE() {
        return PARAMETER_DEFAULT_VALUE;
    }

    public final SourceElementPositioningStrategy getPARAMETERS_WITH_DEFAULT_VALUE() {
        return PARAMETERS_WITH_DEFAULT_VALUE;
    }

    public final SourceElementPositioningStrategy getPARAMETER_VARARG_MODIFIER() {
        return PARAMETER_VARARG_MODIFIER;
    }

    public final SourceElementPositioningStrategy getNAME_OF_NAMED_ARGUMENT() {
        return NAME_OF_NAMED_ARGUMENT;
    }

    public final SourceElementPositioningStrategy getVALUE_ARGUMENTS() {
        return VALUE_ARGUMENTS;
    }

    public final SourceElementPositioningStrategy getVALUE_ARGUMENTS_LIST() {
        return VALUE_ARGUMENTS_LIST;
    }

    public final SourceElementPositioningStrategy getSUPERTYPES_LIST() {
        return SUPERTYPES_LIST;
    }

    public final SourceElementPositioningStrategy getDOT_BY_QUALIFIED() {
        return DOT_BY_QUALIFIED;
    }

    public final SourceElementPositioningStrategy getSELECTOR_BY_QUALIFIED() {
        return SELECTOR_BY_QUALIFIED;
    }

    public final SourceElementPositioningStrategy getREFERENCE_BY_QUALIFIED() {
        return REFERENCE_BY_QUALIFIED;
    }

    public final SourceElementPositioningStrategy getREFERENCED_NAME_BY_QUALIFIED() {
        return REFERENCED_NAME_BY_QUALIFIED;
    }

    public final SourceElementPositioningStrategy getWHEN_EXPRESSION() {
        return WHEN_EXPRESSION;
    }

    public final SourceElementPositioningStrategy getIF_EXPRESSION() {
        return IF_EXPRESSION;
    }

    public final SourceElementPositioningStrategy getELSE_ENTRY() {
        return ELSE_ENTRY;
    }

    public final SourceElementPositioningStrategy getARRAY_ACCESS() {
        return ARRAY_ACCESS;
    }

    public final SourceElementPositioningStrategy getSAFE_ACCESS() {
        return SAFE_ACCESS;
    }

    public final SourceElementPositioningStrategy getAS_TYPE() {
        return AS_TYPE;
    }

    public final SourceElementPositioningStrategy getUSELESS_ELVIS() {
        return USELESS_ELVIS;
    }

    public final SourceElementPositioningStrategy getRETURN_WITH_LABEL() {
        return RETURN_WITH_LABEL;
    }

    public final SourceElementPositioningStrategy getPROPERTY_INITIALIZER() {
        return PROPERTY_INITIALIZER;
    }

    public final SourceElementPositioningStrategy getWHOLE_ELEMENT() {
        return WHOLE_ELEMENT;
    }

    public final SourceElementPositioningStrategy getLONG_LITERAL_SUFFIX() {
        return LONG_LITERAL_SUFFIX;
    }

    public final SourceElementPositioningStrategy getREIFIED_MODIFIER() {
        return REIFIED_MODIFIER;
    }

    public final SourceElementPositioningStrategy getTYPE_PARAMETERS_LIST() {
        return TYPE_PARAMETERS_LIST;
    }

    public final SourceElementPositioningStrategy getNAME_IDENTIFIER() {
        return NAME_IDENTIFIER;
    }

    public final SourceElementPositioningStrategy getREDUNDANT_NULLABLE() {
        return REDUNDANT_NULLABLE;
    }

    public final SourceElementPositioningStrategy getQUESTION_MARK_BY_TYPE() {
        return QUESTION_MARK_BY_TYPE;
    }

    public final SourceElementPositioningStrategy getANNOTATION_USE_SITE() {
        return ANNOTATION_USE_SITE;
    }

    public final SourceElementPositioningStrategy getIMPORT_LAST_NAME() {
        return IMPORT_LAST_NAME;
    }

    public final SourceElementPositioningStrategy getSPREAD_OPERATOR() {
        return SPREAD_OPERATOR;
    }

    public final SourceElementPositioningStrategy getDECLARATION_WITH_BODY() {
        return DECLARATION_WITH_BODY;
    }

    public final SourceElementPositioningStrategy getCOMMAS() {
        return COMMAS;
    }

    public final SourceElementPositioningStrategy getUNREACHABLE_CODE() {
        return UNREACHABLE_CODE;
    }

    public final SourceElementPositioningStrategy getACTUAL_DECLARATION_NAME() {
        return ACTUAL_DECLARATION_NAME;
    }

    public final SourceElementPositioningStrategy getLABEL() {
        return LABEL;
    }

    public final SourceElementPositioningStrategy getINCOMPATIBLE_DECLARATION() {
        return INCOMPATIBLE_DECLARATION;
    }

    public final SourceElementPositioningStrategy getNOT_SUPPORTED_IN_INLINE_MOST_RELEVANT() {
        return NOT_SUPPORTED_IN_INLINE_MOST_RELEVANT;
    }

    public final SourceElementPositioningStrategy getINLINE_PARAMETER_MODIFIER() {
        return INLINE_PARAMETER_MODIFIER;
    }

    public final SourceElementPositioningStrategy getINLINE_FUN_MODIFIER() {
        return INLINE_FUN_MODIFIER;
    }

    public final SourceElementPositioningStrategy getOPERATOR_MODIFIER() {
        return OPERATOR_MODIFIER;
    }

    public final SourceElementPositioningStrategy getNON_FINAL_MODIFIER_OR_NAME() {
        return NON_FINAL_MODIFIER_OR_NAME;
    }

    public final SourceElementPositioningStrategy getENUM_MODIFIER() {
        return ENUM_MODIFIER;
    }

    public final SourceElementPositioningStrategy getFIELD_KEYWORD() {
        return FIELD_KEYWORD;
    }

    public final SourceElementPositioningStrategy getTAILREC_MODIFIER() {
        return TAILREC_MODIFIER;
    }

    public final SourceElementPositioningStrategy getEXTERNAL_MODIFIER() {
        return EXTERNAL_MODIFIER;
    }

    public final SourceElementPositioningStrategy getPROPERTY_DELEGATE() {
        return PROPERTY_DELEGATE;
    }

    public final SourceElementPositioningStrategy getIMPORT_ALIAS() {
        return IMPORT_ALIAS;
    }

    public final SourceElementPositioningStrategy getDECLARATION_START_TO_NAME() {
        return DECLARATION_START_TO_NAME;
    }

    public final SourceElementPositioningStrategy getDELEGATED_SUPERTYPE_BY_KEYWORD() {
        return DELEGATED_SUPERTYPE_BY_KEYWORD;
    }

    public final SourceElementPositioningStrategy getCALL_ELEMENT_WITH_DOT() {
        return CALL_ELEMENT_WITH_DOT;
    }

    public final SourceElementPositioningStrategy getTYPEALIAS_TYPE_REFERENCE() {
        return TYPEALIAS_TYPE_REFERENCE;
    }
}
