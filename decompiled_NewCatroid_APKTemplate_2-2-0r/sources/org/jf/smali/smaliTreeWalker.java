package org.jf.smali;

import androidx.exifinterface.media.ExifInterface;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import kotlin.text.Typography;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.AnnotationVisibility;
import org.jf.dexlib2.HiddenApiRestriction;
import org.jf.dexlib2.MethodHandleType;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.VerificationError;
import org.jf.dexlib2.builder.Label;
import org.jf.dexlib2.builder.MethodImplementationBuilder;
import org.jf.dexlib2.builder.SwitchLabelElement;
import org.jf.dexlib2.builder.instruction.BuilderArrayPayload;
import org.jf.dexlib2.builder.instruction.BuilderInstruction10t;
import org.jf.dexlib2.builder.instruction.BuilderInstruction10x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction11n;
import org.jf.dexlib2.builder.instruction.BuilderInstruction11x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction12x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction20bc;
import org.jf.dexlib2.builder.instruction.BuilderInstruction20t;
import org.jf.dexlib2.builder.instruction.BuilderInstruction21c;
import org.jf.dexlib2.builder.instruction.BuilderInstruction21ih;
import org.jf.dexlib2.builder.instruction.BuilderInstruction21lh;
import org.jf.dexlib2.builder.instruction.BuilderInstruction21s;
import org.jf.dexlib2.builder.instruction.BuilderInstruction21t;
import org.jf.dexlib2.builder.instruction.BuilderInstruction22b;
import org.jf.dexlib2.builder.instruction.BuilderInstruction22c;
import org.jf.dexlib2.builder.instruction.BuilderInstruction22s;
import org.jf.dexlib2.builder.instruction.BuilderInstruction22t;
import org.jf.dexlib2.builder.instruction.BuilderInstruction22x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction23x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction30t;
import org.jf.dexlib2.builder.instruction.BuilderInstruction31c;
import org.jf.dexlib2.builder.instruction.BuilderInstruction31i;
import org.jf.dexlib2.builder.instruction.BuilderInstruction31t;
import org.jf.dexlib2.builder.instruction.BuilderInstruction32x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction35c;
import org.jf.dexlib2.builder.instruction.BuilderInstruction3rc;
import org.jf.dexlib2.builder.instruction.BuilderInstruction45cc;
import org.jf.dexlib2.builder.instruction.BuilderInstruction4rcc;
import org.jf.dexlib2.builder.instruction.BuilderInstruction51l;
import org.jf.dexlib2.builder.instruction.BuilderPackedSwitchPayload;
import org.jf.dexlib2.builder.instruction.BuilderSparseSwitchPayload;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.AnnotationElement;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.immutable.ImmutableAnnotation;
import org.jf.dexlib2.immutable.ImmutableAnnotationElement;
import org.jf.dexlib2.immutable.reference.ImmutableCallSiteReference;
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
import org.jf.dexlib2.immutable.reference.ImmutableMethodHandleReference;
import org.jf.dexlib2.immutable.reference.ImmutableMethodProtoReference;
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
import org.jf.dexlib2.immutable.reference.ImmutableReference;
import org.jf.dexlib2.immutable.reference.ImmutableTypeReference;
import org.jf.dexlib2.immutable.value.ImmutableAnnotationEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableArrayEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableBooleanEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableByteEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableCharEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableDoubleEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEnumEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableFieldEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableFloatEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableLongEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableMethodEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableMethodHandleEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableMethodTypeEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableNullEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableShortEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableStringEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableTypeEncodedValue;
import org.jf.dexlib2.util.MethodUtil;
import org.jf.dexlib2.writer.builder.BuilderField;
import org.jf.dexlib2.writer.builder.BuilderMethod;
import org.jf.dexlib2.writer.builder.BuilderTryBlock;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.util.LinearSearch;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class smaliTreeWalker extends TreeParser {
    public static final int ACCESS_SPEC = 4;
    public static final int ANNOTATION_DIRECTIVE = 5;
    public static final int ANNOTATION_VISIBILITY = 6;
    public static final int ARRAY_DATA_DIRECTIVE = 7;
    public static final int ARRAY_TYPE_PREFIX = 8;
    public static final int ARROW = 9;
    public static final int AT = 10;
    public static final int BOOL_LITERAL = 11;
    public static final int BYTE_LITERAL = 12;
    public static final int CATCHALL_DIRECTIVE = 13;
    public static final int CATCH_DIRECTIVE = 14;
    public static final int CHAR_LITERAL = 15;
    public static final int CLASS_DESCRIPTOR = 16;
    public static final int CLASS_DIRECTIVE = 17;
    public static final int CLOSE_BRACE = 18;
    public static final int CLOSE_PAREN = 19;
    public static final int COLON = 20;
    public static final int COMMA = 21;
    public static final int DOTDOT = 22;
    public static final int DOUBLE_LITERAL = 23;
    public static final int DOUBLE_LITERAL_OR_ID = 24;
    public static final int END_ANNOTATION_DIRECTIVE = 25;
    public static final int END_ARRAY_DATA_DIRECTIVE = 26;
    public static final int END_FIELD_DIRECTIVE = 27;
    public static final int END_LOCAL_DIRECTIVE = 28;
    public static final int END_METHOD_DIRECTIVE = 29;
    public static final int END_PACKED_SWITCH_DIRECTIVE = 30;
    public static final int END_PARAMETER_DIRECTIVE = 31;
    public static final int END_SPARSE_SWITCH_DIRECTIVE = 32;
    public static final int END_SUBANNOTATION_DIRECTIVE = 33;
    public static final int ENUM_DIRECTIVE = 34;
    public static final int EOF = -1;
    public static final int EPILOGUE_DIRECTIVE = 35;
    public static final int EQUAL = 36;
    public static final int FIELD_DIRECTIVE = 37;
    public static final int FIELD_OFFSET = 38;
    public static final int FLOAT_LITERAL = 39;
    public static final int FLOAT_LITERAL_OR_ID = 40;
    public static final int HIDDENAPI_RESTRICTION = 41;
    public static final int IMPLEMENTS_DIRECTIVE = 42;
    public static final int INLINE_INDEX = 43;
    public static final int INSTRUCTION_FORMAT10t = 44;
    public static final int INSTRUCTION_FORMAT10x = 45;
    public static final int INSTRUCTION_FORMAT10x_ODEX = 46;
    public static final int INSTRUCTION_FORMAT11n = 47;
    public static final int INSTRUCTION_FORMAT11x = 48;
    public static final int INSTRUCTION_FORMAT12x = 49;
    public static final int INSTRUCTION_FORMAT12x_OR_ID = 50;
    public static final int INSTRUCTION_FORMAT20bc = 51;
    public static final int INSTRUCTION_FORMAT20t = 52;
    public static final int INSTRUCTION_FORMAT21c_FIELD = 53;
    public static final int INSTRUCTION_FORMAT21c_FIELD_ODEX = 54;
    public static final int INSTRUCTION_FORMAT21c_METHOD_HANDLE = 55;
    public static final int INSTRUCTION_FORMAT21c_METHOD_TYPE = 56;
    public static final int INSTRUCTION_FORMAT21c_STRING = 57;
    public static final int INSTRUCTION_FORMAT21c_TYPE = 58;
    public static final int INSTRUCTION_FORMAT21ih = 59;
    public static final int INSTRUCTION_FORMAT21lh = 60;
    public static final int INSTRUCTION_FORMAT21s = 61;
    public static final int INSTRUCTION_FORMAT21t = 62;
    public static final int INSTRUCTION_FORMAT22b = 63;
    public static final int INSTRUCTION_FORMAT22c_FIELD = 64;
    public static final int INSTRUCTION_FORMAT22c_FIELD_ODEX = 65;
    public static final int INSTRUCTION_FORMAT22c_TYPE = 66;
    public static final int INSTRUCTION_FORMAT22cs_FIELD = 67;
    public static final int INSTRUCTION_FORMAT22s = 68;
    public static final int INSTRUCTION_FORMAT22s_OR_ID = 69;
    public static final int INSTRUCTION_FORMAT22t = 70;
    public static final int INSTRUCTION_FORMAT22x = 71;
    public static final int INSTRUCTION_FORMAT23x = 72;
    public static final int INSTRUCTION_FORMAT30t = 73;
    public static final int INSTRUCTION_FORMAT31c = 74;
    public static final int INSTRUCTION_FORMAT31i = 75;
    public static final int INSTRUCTION_FORMAT31i_OR_ID = 76;
    public static final int INSTRUCTION_FORMAT31t = 77;
    public static final int INSTRUCTION_FORMAT32x = 78;
    public static final int INSTRUCTION_FORMAT35c_CALL_SITE = 79;
    public static final int INSTRUCTION_FORMAT35c_METHOD = 80;
    public static final int INSTRUCTION_FORMAT35c_METHOD_ODEX = 81;
    public static final int INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE = 82;
    public static final int INSTRUCTION_FORMAT35c_TYPE = 83;
    public static final int INSTRUCTION_FORMAT35mi_METHOD = 84;
    public static final int INSTRUCTION_FORMAT35ms_METHOD = 85;
    public static final int INSTRUCTION_FORMAT3rc_CALL_SITE = 86;
    public static final int INSTRUCTION_FORMAT3rc_METHOD = 87;
    public static final int INSTRUCTION_FORMAT3rc_METHOD_ODEX = 88;
    public static final int INSTRUCTION_FORMAT3rc_TYPE = 89;
    public static final int INSTRUCTION_FORMAT3rmi_METHOD = 90;
    public static final int INSTRUCTION_FORMAT3rms_METHOD = 91;
    public static final int INSTRUCTION_FORMAT45cc_METHOD = 92;
    public static final int INSTRUCTION_FORMAT4rcc_METHOD = 93;
    public static final int INSTRUCTION_FORMAT51l = 94;
    public static final int INTEGER_LITERAL = 95;
    public static final int INVALID_TOKEN = 96;
    public static final int I_ACCESS_LIST = 97;
    public static final int I_ACCESS_OR_RESTRICTION_LIST = 98;
    public static final int I_ANNOTATION = 99;
    public static final int I_ANNOTATIONS = 100;
    public static final int I_ANNOTATION_ELEMENT = 101;
    public static final int I_ARRAY_ELEMENTS = 102;
    public static final int I_ARRAY_ELEMENT_SIZE = 103;
    public static final int I_CALL_SITE_EXTRA_ARGUMENTS = 104;
    public static final int I_CALL_SITE_REFERENCE = 105;
    public static final int I_CATCH = 106;
    public static final int I_CATCHALL = 107;
    public static final int I_CATCHES = 108;
    public static final int I_CLASS_DEF = 109;
    public static final int I_ENCODED_ARRAY = 110;
    public static final int I_ENCODED_ENUM = 111;
    public static final int I_ENCODED_FIELD = 112;
    public static final int I_ENCODED_METHOD = 113;
    public static final int I_ENCODED_METHOD_HANDLE = 114;
    public static final int I_END_LOCAL = 115;
    public static final int I_EPILOGUE = 116;
    public static final int I_FIELD = 117;
    public static final int I_FIELDS = 118;
    public static final int I_FIELD_INITIAL_VALUE = 119;
    public static final int I_FIELD_TYPE = 120;
    public static final int I_IMPLEMENTS = 121;
    public static final int I_LABEL = 122;
    public static final int I_LINE = 123;
    public static final int I_LOCAL = 124;
    public static final int I_LOCALS = 125;
    public static final int I_METHOD = 126;
    public static final int I_METHODS = 127;
    public static final int I_METHOD_PROTOTYPE = 128;
    public static final int I_METHOD_RETURN_TYPE = 129;
    public static final int I_ORDERED_METHOD_ITEMS = 130;
    public static final int I_PACKED_SWITCH_ELEMENTS = 131;
    public static final int I_PACKED_SWITCH_START_KEY = 132;
    public static final int I_PARAMETER = 133;
    public static final int I_PARAMETERS = 134;
    public static final int I_PARAMETER_NOT_SPECIFIED = 135;
    public static final int I_PROLOGUE = 136;
    public static final int I_REGISTERS = 137;
    public static final int I_REGISTER_LIST = 138;
    public static final int I_REGISTER_RANGE = 139;
    public static final int I_RESTART_LOCAL = 140;
    public static final int I_SOURCE = 141;
    public static final int I_SPARSE_SWITCH_ELEMENTS = 142;
    public static final int I_STATEMENT_ARRAY_DATA = 143;
    public static final int I_STATEMENT_FORMAT10t = 144;
    public static final int I_STATEMENT_FORMAT10x = 145;
    public static final int I_STATEMENT_FORMAT11n = 146;
    public static final int I_STATEMENT_FORMAT11x = 147;
    public static final int I_STATEMENT_FORMAT12x = 148;
    public static final int I_STATEMENT_FORMAT20bc = 149;
    public static final int I_STATEMENT_FORMAT20t = 150;
    public static final int I_STATEMENT_FORMAT21c_FIELD = 151;
    public static final int I_STATEMENT_FORMAT21c_METHOD_HANDLE = 152;
    public static final int I_STATEMENT_FORMAT21c_METHOD_TYPE = 153;
    public static final int I_STATEMENT_FORMAT21c_STRING = 154;
    public static final int I_STATEMENT_FORMAT21c_TYPE = 155;
    public static final int I_STATEMENT_FORMAT21ih = 156;
    public static final int I_STATEMENT_FORMAT21lh = 157;
    public static final int I_STATEMENT_FORMAT21s = 158;
    public static final int I_STATEMENT_FORMAT21t = 159;
    public static final int I_STATEMENT_FORMAT22b = 160;
    public static final int I_STATEMENT_FORMAT22c_FIELD = 161;
    public static final int I_STATEMENT_FORMAT22c_TYPE = 162;
    public static final int I_STATEMENT_FORMAT22s = 163;
    public static final int I_STATEMENT_FORMAT22t = 164;
    public static final int I_STATEMENT_FORMAT22x = 165;
    public static final int I_STATEMENT_FORMAT23x = 166;
    public static final int I_STATEMENT_FORMAT30t = 167;
    public static final int I_STATEMENT_FORMAT31c = 168;
    public static final int I_STATEMENT_FORMAT31i = 169;
    public static final int I_STATEMENT_FORMAT31t = 170;
    public static final int I_STATEMENT_FORMAT32x = 171;
    public static final int I_STATEMENT_FORMAT35c_CALL_SITE = 172;
    public static final int I_STATEMENT_FORMAT35c_METHOD = 173;
    public static final int I_STATEMENT_FORMAT35c_TYPE = 174;
    public static final int I_STATEMENT_FORMAT3rc_CALL_SITE = 175;
    public static final int I_STATEMENT_FORMAT3rc_METHOD = 176;
    public static final int I_STATEMENT_FORMAT3rc_TYPE = 177;
    public static final int I_STATEMENT_FORMAT45cc_METHOD = 178;
    public static final int I_STATEMENT_FORMAT4rcc_METHOD = 179;
    public static final int I_STATEMENT_FORMAT51l = 180;
    public static final int I_STATEMENT_PACKED_SWITCH = 181;
    public static final int I_STATEMENT_SPARSE_SWITCH = 182;
    public static final int I_SUBANNOTATION = 183;
    public static final int I_SUPER = 184;
    public static final int LINE_COMMENT = 185;
    public static final int LINE_DIRECTIVE = 186;
    public static final int LOCALS_DIRECTIVE = 187;
    public static final int LOCAL_DIRECTIVE = 188;
    public static final int LONG_LITERAL = 189;
    public static final int MEMBER_NAME = 190;
    public static final int METHOD_DIRECTIVE = 191;
    public static final int METHOD_HANDLE_TYPE_FIELD = 192;
    public static final int METHOD_HANDLE_TYPE_METHOD = 193;
    public static final int NEGATIVE_INTEGER_LITERAL = 194;
    public static final int NULL_LITERAL = 195;
    public static final int OPEN_BRACE = 196;
    public static final int OPEN_PAREN = 197;
    public static final int PACKED_SWITCH_DIRECTIVE = 198;
    public static final int PARAMETER_DIRECTIVE = 199;
    public static final int PARAM_LIST_OR_ID_PRIMITIVE_TYPE = 200;
    public static final int POSITIVE_INTEGER_LITERAL = 201;
    public static final int PRIMITIVE_TYPE = 202;
    public static final int PROLOGUE_DIRECTIVE = 203;
    public static final int REGISTER = 204;
    public static final int REGISTERS_DIRECTIVE = 205;
    public static final int RESTART_LOCAL_DIRECTIVE = 206;
    public static final int SHORT_LITERAL = 207;
    public static final int SIMPLE_NAME = 208;
    public static final int SOURCE_DIRECTIVE = 209;
    public static final int SPARSE_SWITCH_DIRECTIVE = 210;
    public static final int STRING_LITERAL = 211;
    public static final int SUBANNOTATION_DIRECTIVE = 212;
    public static final int SUPER_DIRECTIVE = 213;
    public static final int VERIFICATION_ERROR_TYPE = 214;
    public static final int VOID_TYPE = 215;
    public static final int VTABLE_INDEX = 216;
    public static final int WHITE_SPACE = 217;
    private int apiLevel;
    private int callSiteNameIndex;
    public String classType;
    private DexBuilder dexBuilder;
    protected Stack<method_scope> method_stack;
    private Opcodes opcodes;
    private boolean verboseErrors;
    public static final String[] tokenNames = {"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACCESS_SPEC", "ANNOTATION_DIRECTIVE", "ANNOTATION_VISIBILITY", "ARRAY_DATA_DIRECTIVE", "ARRAY_TYPE_PREFIX", "ARROW", "AT", "BOOL_LITERAL", "BYTE_LITERAL", "CATCHALL_DIRECTIVE", "CATCH_DIRECTIVE", "CHAR_LITERAL", "CLASS_DESCRIPTOR", "CLASS_DIRECTIVE", "CLOSE_BRACE", "CLOSE_PAREN", "COLON", "COMMA", "DOTDOT", "DOUBLE_LITERAL", "DOUBLE_LITERAL_OR_ID", "END_ANNOTATION_DIRECTIVE", "END_ARRAY_DATA_DIRECTIVE", "END_FIELD_DIRECTIVE", "END_LOCAL_DIRECTIVE", "END_METHOD_DIRECTIVE", "END_PACKED_SWITCH_DIRECTIVE", "END_PARAMETER_DIRECTIVE", "END_SPARSE_SWITCH_DIRECTIVE", "END_SUBANNOTATION_DIRECTIVE", "ENUM_DIRECTIVE", "EPILOGUE_DIRECTIVE", "EQUAL", "FIELD_DIRECTIVE", "FIELD_OFFSET", "FLOAT_LITERAL", "FLOAT_LITERAL_OR_ID", "HIDDENAPI_RESTRICTION", "IMPLEMENTS_DIRECTIVE", "INLINE_INDEX", "INSTRUCTION_FORMAT10t", "INSTRUCTION_FORMAT10x", "INSTRUCTION_FORMAT10x_ODEX", "INSTRUCTION_FORMAT11n", "INSTRUCTION_FORMAT11x", "INSTRUCTION_FORMAT12x", "INSTRUCTION_FORMAT12x_OR_ID", "INSTRUCTION_FORMAT20bc", "INSTRUCTION_FORMAT20t", "INSTRUCTION_FORMAT21c_FIELD", "INSTRUCTION_FORMAT21c_FIELD_ODEX", "INSTRUCTION_FORMAT21c_METHOD_HANDLE", "INSTRUCTION_FORMAT21c_METHOD_TYPE", "INSTRUCTION_FORMAT21c_STRING", "INSTRUCTION_FORMAT21c_TYPE", "INSTRUCTION_FORMAT21ih", "INSTRUCTION_FORMAT21lh", "INSTRUCTION_FORMAT21s", "INSTRUCTION_FORMAT21t", "INSTRUCTION_FORMAT22b", "INSTRUCTION_FORMAT22c_FIELD", "INSTRUCTION_FORMAT22c_FIELD_ODEX", "INSTRUCTION_FORMAT22c_TYPE", "INSTRUCTION_FORMAT22cs_FIELD", "INSTRUCTION_FORMAT22s", "INSTRUCTION_FORMAT22s_OR_ID", "INSTRUCTION_FORMAT22t", "INSTRUCTION_FORMAT22x", "INSTRUCTION_FORMAT23x", "INSTRUCTION_FORMAT30t", "INSTRUCTION_FORMAT31c", "INSTRUCTION_FORMAT31i", "INSTRUCTION_FORMAT31i_OR_ID", "INSTRUCTION_FORMAT31t", "INSTRUCTION_FORMAT32x", "INSTRUCTION_FORMAT35c_CALL_SITE", "INSTRUCTION_FORMAT35c_METHOD", "INSTRUCTION_FORMAT35c_METHOD_ODEX", "INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE", "INSTRUCTION_FORMAT35c_TYPE", "INSTRUCTION_FORMAT35mi_METHOD", "INSTRUCTION_FORMAT35ms_METHOD", "INSTRUCTION_FORMAT3rc_CALL_SITE", "INSTRUCTION_FORMAT3rc_METHOD", "INSTRUCTION_FORMAT3rc_METHOD_ODEX", "INSTRUCTION_FORMAT3rc_TYPE", "INSTRUCTION_FORMAT3rmi_METHOD", "INSTRUCTION_FORMAT3rms_METHOD", "INSTRUCTION_FORMAT45cc_METHOD", "INSTRUCTION_FORMAT4rcc_METHOD", "INSTRUCTION_FORMAT51l", "INTEGER_LITERAL", "INVALID_TOKEN", "I_ACCESS_LIST", "I_ACCESS_OR_RESTRICTION_LIST", "I_ANNOTATION", "I_ANNOTATIONS", "I_ANNOTATION_ELEMENT", "I_ARRAY_ELEMENTS", "I_ARRAY_ELEMENT_SIZE", "I_CALL_SITE_EXTRA_ARGUMENTS", "I_CALL_SITE_REFERENCE", "I_CATCH", "I_CATCHALL", "I_CATCHES", "I_CLASS_DEF", "I_ENCODED_ARRAY", "I_ENCODED_ENUM", "I_ENCODED_FIELD", "I_ENCODED_METHOD", "I_ENCODED_METHOD_HANDLE", "I_END_LOCAL", "I_EPILOGUE", "I_FIELD", "I_FIELDS", "I_FIELD_INITIAL_VALUE", "I_FIELD_TYPE", "I_IMPLEMENTS", "I_LABEL", "I_LINE", "I_LOCAL", "I_LOCALS", "I_METHOD", "I_METHODS", "I_METHOD_PROTOTYPE", "I_METHOD_RETURN_TYPE", "I_ORDERED_METHOD_ITEMS", "I_PACKED_SWITCH_ELEMENTS", "I_PACKED_SWITCH_START_KEY", "I_PARAMETER", "I_PARAMETERS", "I_PARAMETER_NOT_SPECIFIED", "I_PROLOGUE", "I_REGISTERS", "I_REGISTER_LIST", "I_REGISTER_RANGE", "I_RESTART_LOCAL", "I_SOURCE", "I_SPARSE_SWITCH_ELEMENTS", "I_STATEMENT_ARRAY_DATA", "I_STATEMENT_FORMAT10t", "I_STATEMENT_FORMAT10x", "I_STATEMENT_FORMAT11n", "I_STATEMENT_FORMAT11x", "I_STATEMENT_FORMAT12x", "I_STATEMENT_FORMAT20bc", "I_STATEMENT_FORMAT20t", "I_STATEMENT_FORMAT21c_FIELD", "I_STATEMENT_FORMAT21c_METHOD_HANDLE", "I_STATEMENT_FORMAT21c_METHOD_TYPE", "I_STATEMENT_FORMAT21c_STRING", "I_STATEMENT_FORMAT21c_TYPE", "I_STATEMENT_FORMAT21ih", "I_STATEMENT_FORMAT21lh", "I_STATEMENT_FORMAT21s", "I_STATEMENT_FORMAT21t", "I_STATEMENT_FORMAT22b", "I_STATEMENT_FORMAT22c_FIELD", "I_STATEMENT_FORMAT22c_TYPE", "I_STATEMENT_FORMAT22s", "I_STATEMENT_FORMAT22t", "I_STATEMENT_FORMAT22x", "I_STATEMENT_FORMAT23x", "I_STATEMENT_FORMAT30t", "I_STATEMENT_FORMAT31c", "I_STATEMENT_FORMAT31i", "I_STATEMENT_FORMAT31t", "I_STATEMENT_FORMAT32x", "I_STATEMENT_FORMAT35c_CALL_SITE", "I_STATEMENT_FORMAT35c_METHOD", "I_STATEMENT_FORMAT35c_TYPE", "I_STATEMENT_FORMAT3rc_CALL_SITE", "I_STATEMENT_FORMAT3rc_METHOD", "I_STATEMENT_FORMAT3rc_TYPE", "I_STATEMENT_FORMAT45cc_METHOD", "I_STATEMENT_FORMAT4rcc_METHOD", "I_STATEMENT_FORMAT51l", "I_STATEMENT_PACKED_SWITCH", "I_STATEMENT_SPARSE_SWITCH", "I_SUBANNOTATION", "I_SUPER", "LINE_COMMENT", "LINE_DIRECTIVE", "LOCALS_DIRECTIVE", "LOCAL_DIRECTIVE", "LONG_LITERAL", "MEMBER_NAME", "METHOD_DIRECTIVE", "METHOD_HANDLE_TYPE_FIELD", "METHOD_HANDLE_TYPE_METHOD", "NEGATIVE_INTEGER_LITERAL", "NULL_LITERAL", "OPEN_BRACE", "OPEN_PAREN", "PACKED_SWITCH_DIRECTIVE", "PARAMETER_DIRECTIVE", "PARAM_LIST_OR_ID_PRIMITIVE_TYPE", "POSITIVE_INTEGER_LITERAL", "PRIMITIVE_TYPE", "PROLOGUE_DIRECTIVE", "REGISTER", "REGISTERS_DIRECTIVE", "RESTART_LOCAL_DIRECTIVE", "SHORT_LITERAL", "SIMPLE_NAME", "SOURCE_DIRECTIVE", "SPARSE_SWITCH_DIRECTIVE", "STRING_LITERAL", "SUBANNOTATION_DIRECTIVE", "SUPER_DIRECTIVE", "VERIFICATION_ERROR_TYPE", "VOID_TYPE", "VTABLE_INDEX", "WHITE_SPACE"};
    public static final BitSet FOLLOW_I_CLASS_DEF_in_smali_file52 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_header_in_smali_file54 = new BitSet(new long[]{0, Long.MIN_VALUE});
    public static final BitSet FOLLOW_methods_in_smali_file56 = new BitSet(new long[]{0, 18014398509481984L});
    public static final BitSet FOLLOW_fields_in_smali_file58 = new BitSet(new long[]{0, 68719476736L});
    public static final BitSet FOLLOW_annotations_in_smali_file60 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_class_spec_in_header85 = new BitSet(new long[]{0, 144115188075855872L, 72057594037936128L});
    public static final BitSet FOLLOW_super_spec_in_header87 = new BitSet(new long[]{0, 144115188075855872L, 8192});
    public static final BitSet FOLLOW_implements_list_in_header90 = new BitSet(new long[]{0, 0, 8192});
    public static final BitSet FOLLOW_source_spec_in_header92 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_class_spec110 = new BitSet(new long[]{0, 8589934592L});
    public static final BitSet FOLLOW_access_list_in_class_spec112 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_SUPER_in_super_spec130 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_super_spec132 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_IMPLEMENTS_in_implements_spec152 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_implements_spec154 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_implements_spec_in_implements_list184 = new BitSet(new long[]{2, 144115188075855872L});
    public static final BitSet FOLLOW_I_SOURCE_in_source_spec213 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_string_literal_in_source_spec215 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_ACCESS_LIST_in_access_list247 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_ACCESS_SPEC_in_access_list265 = new BitSet(new long[]{24});
    public static final BitSet FOLLOW_I_ACCESS_OR_RESTRICTION_LIST_in_access_or_restriction_list308 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_ACCESS_SPEC_in_access_or_restriction_list326 = new BitSet(new long[]{2199023255576L});
    public static final BitSet FOLLOW_HIDDENAPI_RESTRICTION_in_access_or_restriction_list356 = new BitSet(new long[]{2199023255576L});
    public static final BitSet FOLLOW_I_FIELDS_in_fields405 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_field_in_fields414 = new BitSet(new long[]{8, 9007199254740992L});
    public static final BitSet FOLLOW_I_METHODS_in_methods446 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_method_in_methods455 = new BitSet(new long[]{8, 4611686018427387904L});
    public static final BitSet FOLLOW_I_FIELD_in_field480 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_SIMPLE_NAME_in_field482 = new BitSet(new long[]{0, 17179869184L});
    public static final BitSet FOLLOW_access_or_restriction_list_in_field484 = new BitSet(new long[]{0, 72057594037927936L});
    public static final BitSet FOLLOW_I_FIELD_TYPE_in_field487 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field489 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_field_initial_value_in_field492 = new BitSet(new long[]{8, 68719476736L});
    public static final BitSet FOLLOW_annotations_in_field494 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_FIELD_INITIAL_VALUE_in_field_initial_value515 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_literal_in_field_initial_value517 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_integer_literal_in_literal539 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_long_literal_in_literal547 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_short_literal_in_literal555 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_byte_literal_in_literal563 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_float_literal_in_literal571 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_double_literal_in_literal579 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_char_literal_in_literal587 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_string_literal_in_literal595 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_bool_literal_in_literal603 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_NULL_LITERAL_in_literal611 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_type_descriptor_in_literal619 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_array_literal_in_literal627 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_subannotation_in_literal635 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_field_literal_in_literal643 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_method_literal_in_literal651 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_enum_literal_in_literal659 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_method_handle_literal_in_literal667 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_method_prototype_in_literal675 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_integer_literal_in_fixed_64bit_literal_number691 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_long_literal_in_fixed_64bit_literal_number699 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_short_literal_in_fixed_64bit_literal_number707 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_byte_literal_in_fixed_64bit_literal_number715 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_float_literal_in_fixed_64bit_literal_number723 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_double_literal_in_fixed_64bit_literal_number731 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_char_literal_in_fixed_64bit_literal_number739 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_bool_literal_in_fixed_64bit_literal_number747 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_integer_literal_in_fixed_64bit_literal762 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_long_literal_in_fixed_64bit_literal770 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_short_literal_in_fixed_64bit_literal778 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_byte_literal_in_fixed_64bit_literal786 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_float_literal_in_fixed_64bit_literal794 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_double_literal_in_fixed_64bit_literal802 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_char_literal_in_fixed_64bit_literal810 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_bool_literal_in_fixed_64bit_literal818 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_integer_literal_in_fixed_32bit_literal835 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_long_literal_in_fixed_32bit_literal843 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_short_literal_in_fixed_32bit_literal851 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_byte_literal_in_fixed_32bit_literal859 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_float_literal_in_fixed_32bit_literal867 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_char_literal_in_fixed_32bit_literal875 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_bool_literal_in_fixed_32bit_literal883 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_ARRAY_ELEMENTS_in_array_elements905 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_fixed_64bit_literal_number_in_array_elements914 = new BitSet(new long[]{549764241416L, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_I_PACKED_SWITCH_ELEMENTS_in_packed_switch_elements950 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_label_ref_in_packed_switch_elements959 = new BitSet(new long[]{8, 0, 0, 65536});
    public static final BitSet FOLLOW_I_SPARSE_SWITCH_ELEMENTS_in_sparse_switch_elements994 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_fixed_32bit_literal_in_sparse_switch_elements1004 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_sparse_switch_elements1006 = new BitSet(new long[]{549755852808L, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_I_METHOD_in_method1058 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_method_name_and_prototype_in_method1066 = new BitSet(new long[]{0, 17179869184L});
    public static final BitSet FOLLOW_access_or_restriction_list_in_method1074 = new BitSet(new long[]{0, 2305843009213693952L, 516});
    public static final BitSet FOLLOW_registers_directive_in_method1101 = new BitSet(new long[]{0, 0, 4});
    public static final BitSet FOLLOW_ordered_method_items_in_method1158 = new BitSet(new long[]{0, 17592186044416L});
    public static final BitSet FOLLOW_catches_in_method1166 = new BitSet(new long[]{0, 0, 64});
    public static final BitSet FOLLOW_parameters_in_method1174 = new BitSet(new long[]{0, 68719476736L});
    public static final BitSet FOLLOW_annotations_in_method1183 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_METHOD_PROTOTYPE_in_method_prototype1207 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_I_METHOD_RETURN_TYPE_in_method_prototype1210 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_type_descriptor_in_method_prototype1212 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_method_type_list_in_method_prototype1215 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_SIMPLE_NAME_in_method_name_and_prototype1233 = new BitSet(new long[]{0, 0, 1});
    public static final BitSet FOLLOW_method_prototype_in_method_name_and_prototype1235 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_method_type_list1269 = new BitSet(new long[]{65794, 0, 0, 1024});
    public static final BitSet FOLLOW_I_CALL_SITE_REFERENCE_in_call_site_reference1300 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_SIMPLE_NAME_in_call_site_reference1304 = new BitSet(new long[]{0, 0, 0, 524288});
    public static final BitSet FOLLOW_string_literal_in_call_site_reference1308 = new BitSet(new long[]{0, 0, 1});
    public static final BitSet FOLLOW_method_prototype_in_call_site_reference1310 = new BitSet(new long[]{0, 1099511627776L});
    public static final BitSet FOLLOW_call_site_extra_arguments_in_call_site_reference1320 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_method_reference_in_call_site_reference1322 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_set_in_method_handle_type1342 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_method_handle_type_in_method_handle_reference1367 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_field_reference_in_method_handle_reference1370 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_method_reference_in_method_handle_reference1374 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_ENCODED_METHOD_HANDLE_in_method_handle_literal1391 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_method_handle_reference_in_method_handle_literal1393 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_reference_type_descriptor_in_method_reference1409 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_SIMPLE_NAME_in_method_reference1412 = new BitSet(new long[]{0, 0, 1});
    public static final BitSet FOLLOW_method_prototype_in_method_reference1414 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_reference_type_descriptor_in_field_reference1431 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_SIMPLE_NAME_in_field_reference1434 = new BitSet(new long[]{65792, 0, 0, 1024});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field_reference1436 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_REGISTERS_in_registers_directive1462 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_I_LOCALS_in_registers_directive1474 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_short_integral_literal_in_registers_directive1492 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_LABEL_in_label_def1512 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_SIMPLE_NAME_in_label_def1514 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_CATCHES_in_catches1540 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_catch_directive_in_catches1542 = new BitSet(new long[]{8, 13194139533312L});
    public static final BitSet FOLLOW_catchall_directive_in_catches1545 = new BitSet(new long[]{8, 8796093022208L});
    public static final BitSet FOLLOW_I_CATCH_in_catch_directive1558 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_catch_directive1560 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_catch_directive1564 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_catch_directive1568 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_catch_directive1572 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_CATCHALL_in_catchall_directive1588 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_label_ref_in_catchall_directive1592 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_catchall_directive1596 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_catchall_directive1600 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_PARAMETERS_in_parameters1617 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_parameter_in_parameters1620 = new BitSet(new long[]{8, 0, 32});
    public static final BitSet FOLLOW_I_PARAMETER_in_parameter1636 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_REGISTER_in_parameter1638 = new BitSet(new long[]{0, 68719476736L, 0, 524288});
    public static final BitSet FOLLOW_string_literal_in_parameter1640 = new BitSet(new long[]{0, 68719476736L});
    public static final BitSet FOLLOW_annotations_in_parameter1643 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_line_in_debug_directive1660 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_local_in_debug_directive1666 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_end_local_in_debug_directive1672 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_restart_local_in_debug_directive1678 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_prologue_in_debug_directive1684 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_epilogue_in_debug_directive1690 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_source_in_debug_directive1696 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_LINE_in_line1707 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_integral_literal_in_line1709 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_LOCAL_in_local1727 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_REGISTER_in_local1729 = new BitSet(new long[]{8, 0, 0, 524296});
    public static final BitSet FOLLOW_NULL_LITERAL_in_local1733 = new BitSet(new long[]{65800, 0, 0, 525312});
    public static final BitSet FOLLOW_string_literal_in_local1739 = new BitSet(new long[]{65800, 0, 0, 525312});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_local1742 = new BitSet(new long[]{8, 0, 0, 524288});
    public static final BitSet FOLLOW_string_literal_in_local1747 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_END_LOCAL_in_end_local1768 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_REGISTER_in_end_local1770 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_RESTART_LOCAL_in_restart_local1788 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_REGISTER_in_restart_local1790 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_PROLOGUE_in_prologue1807 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_EPILOGUE_in_epilogue1823 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_SOURCE_in_source1840 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_string_literal_in_source1842 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_CALL_SITE_EXTRA_ARGUMENTS_in_call_site_extra_arguments1868 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_literal_in_call_site_extra_arguments1871 = new BitSet(new long[]{549764307208L, 2181433216991232L, 2341871806232657921L, 8946696});
    public static final BitSet FOLLOW_I_ORDERED_METHOD_ITEMS_in_ordered_method_items1887 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_label_def_in_ordered_method_items1890 = new BitSet(new long[]{8, 2024368032503037952L, 36028797018943744L});
    public static final BitSet FOLLOW_instruction_in_ordered_method_items1894 = new BitSet(new long[]{8, 2024368032503037952L, 36028797018943744L});
    public static final BitSet FOLLOW_debug_directive_in_ordered_method_items1898 = new BitSet(new long[]{8, 2024368032503037952L, 36028797018943744L});
    public static final BitSet FOLLOW_SIMPLE_NAME_in_label_ref1914 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_REGISTER_LIST_in_register_list1939 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_REGISTER_in_register_list1948 = new BitSet(new long[]{8, 0, 0, 4096});
    public static final BitSet FOLLOW_I_REGISTER_RANGE_in_register_range1973 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_REGISTER_in_register_range1978 = new BitSet(new long[]{8, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_register_range1982 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference2005 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_field_reference_in_verification_error_reference2015 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_method_reference_in_verification_error_reference2025 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_verification_error_type2042 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format10t_in_instruction2056 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format10x_in_instruction2062 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format11n_in_instruction2068 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format11x_in_instruction2074 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format12x_in_instruction2080 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format20bc_in_instruction2086 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format20t_in_instruction2092 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21c_field_in_instruction2098 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21c_method_handle_in_instruction2104 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21c_method_type_in_instruction2110 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21c_string_in_instruction2116 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21c_type_in_instruction2122 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21ih_in_instruction2128 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21lh_in_instruction2134 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21s_in_instruction2140 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format21t_in_instruction2146 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format22b_in_instruction2152 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format22c_field_in_instruction2158 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format22c_type_in_instruction2164 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format22s_in_instruction2170 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format22t_in_instruction2176 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format22x_in_instruction2182 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format23x_in_instruction2188 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format30t_in_instruction2194 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format31c_in_instruction2200 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format31i_in_instruction2206 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format31t_in_instruction2212 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format32x_in_instruction2218 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format35c_call_site_in_instruction2224 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format35c_method_in_instruction2230 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format35c_type_in_instruction2236 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format3rc_call_site_in_instruction2242 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format3rc_method_in_instruction2248 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format3rc_type_in_instruction2254 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format45cc_method_in_instruction2260 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format4rcc_method_in_instruction2266 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_format51l_type_in_instruction2272 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_array_data_directive_in_instruction2278 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_packed_switch_directive_in_instruction2284 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_insn_sparse_switch_directive_in_instruction2290 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT10t_in_insn_format10t2314 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t2316 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_insn_format10t2318 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT10x_in_insn_format10x2341 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x2343 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT11n_in_insn_format11n2366 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n2368 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format11n2370 = new BitSet(new long[]{36864, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_short_integral_literal_in_insn_format11n2372 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT11x_in_insn_format11x2395 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x2397 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format11x2399 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT12x_in_insn_format12x2422 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_in_insn_format12x2424 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format12x2428 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format12x2432 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT20bc_in_insn_format20bc2455 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc2457 = new BitSet(new long[]{0, 0, 0, 4194304});
    public static final BitSet FOLLOW_verification_error_type_in_insn_format20bc2459 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_verification_error_reference_in_insn_format20bc2461 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT20t_in_insn_format20t2484 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t2486 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_insn_format20t2488 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_FIELD_in_insn_format21c_field2511 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_set_in_insn_format21c_field2515 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field2523 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_field_reference_in_insn_format21c_field2525 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2548 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2553 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_method_handle2556 = new BitSet(new long[]{0, 262144, 0, 3});
    public static final BitSet FOLLOW_method_handle_reference_in_insn_format21c_method_handle2558 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2581 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2586 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_method_type2589 = new BitSet(new long[]{0, 0, 1});
    public static final BitSet FOLLOW_method_prototype_in_insn_format21c_method_type2591 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_STRING_in_insn_format21c_string2614 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string2616 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_string2618 = new BitSet(new long[]{0, 0, 0, 524288});
    public static final BitSet FOLLOW_string_literal_in_insn_format21c_string2620 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_TYPE_in_insn_format21c_type2643 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type2645 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_type2647 = new BitSet(new long[]{65792, 0, 0, 1024});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type2649 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21ih_in_insn_format21ih2672 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih2674 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21ih2676 = new BitSet(new long[]{549755852800L, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21ih2678 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21lh_in_insn_format21lh2701 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh2703 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21lh2705 = new BitSet(new long[]{549764241408L, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_fixed_64bit_literal_in_insn_format21lh2707 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21s_in_insn_format21s2730 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s2732 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21s2734 = new BitSet(new long[]{36864, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_short_integral_literal_in_insn_format21s2736 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT21t_in_insn_format21t2759 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t2761 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format21t2763 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_insn_format21t2765 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22b_in_insn_format22b2788 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b2790 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22b2794 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22b2798 = new BitSet(new long[]{36864, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_short_integral_literal_in_insn_format22b2800 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22c_FIELD_in_insn_format22c_field2823 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_set_in_insn_format22c_field2827 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field2837 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field2841 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_field_reference_in_insn_format22c_field2843 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22c_TYPE_in_insn_format22c_type2866 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type2868 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type2872 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type2876 = new BitSet(new long[]{65792, 0, 0, 1024});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type2878 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22s_in_insn_format22s2901 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_in_insn_format22s2903 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22s2907 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22s2911 = new BitSet(new long[]{36864, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_short_integral_literal_in_insn_format22s2913 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22t_in_insn_format22t2936 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t2938 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22t2942 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22t2946 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_insn_format22t2948 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT22x_in_insn_format22x2971 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x2973 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22x2977 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format22x2981 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT23x_in_insn_format23x3004 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x3006 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x3010 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x3014 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x3018 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT30t_in_insn_format30t3041 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t3043 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_insn_format30t3045 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT31c_in_insn_format31c3068 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c3070 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format31c3072 = new BitSet(new long[]{0, 0, 0, 524288});
    public static final BitSet FOLLOW_string_literal_in_insn_format31c3074 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT31i_in_insn_format31i3097 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_in_insn_format31i3099 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format31i3101 = new BitSet(new long[]{549755852800L, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format31i3103 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT31t_in_insn_format31t3126 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t3128 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format31t3130 = new BitSet(new long[]{0, 0, 0, 65536});
    public static final BitSet FOLLOW_label_ref_in_insn_format31t3132 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT32x_in_insn_format32x3155 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x3157 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format32x3161 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format32x3165 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3193 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3195 = new BitSet(new long[]{0, 0, 1024});
    public static final BitSet FOLLOW_register_list_in_insn_format35c_call_site3197 = new BitSet(new long[]{0, 2199023255552L});
    public static final BitSet FOLLOW_call_site_reference_in_insn_format35c_call_site3199 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT35c_METHOD_in_insn_format35c_method3222 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method3224 = new BitSet(new long[]{0, 0, 1024});
    public static final BitSet FOLLOW_register_list_in_insn_format35c_method3226 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_method_reference_in_insn_format35c_method3228 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT35c_TYPE_in_insn_format35c_type3251 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type3253 = new BitSet(new long[]{0, 0, 1024});
    public static final BitSet FOLLOW_register_list_in_insn_format35c_type3255 = new BitSet(new long[]{65792, 0, 0, 1024});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type3257 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3285 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3287 = new BitSet(new long[]{0, 0, 2048});
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_call_site3289 = new BitSet(new long[]{0, 2199023255552L});
    public static final BitSet FOLLOW_call_site_reference_in_insn_format3rc_call_site3291 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT3rc_METHOD_in_insn_format3rc_method3314 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method3316 = new BitSet(new long[]{0, 0, 2048});
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_method3318 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_method_reference_in_insn_format3rc_method3320 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT3rc_TYPE_in_insn_format3rc_type3343 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type3345 = new BitSet(new long[]{0, 0, 2048});
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_type3347 = new BitSet(new long[]{65792, 0, 0, 1024});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type3349 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT45cc_METHOD_in_insn_format45cc_method3372 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method3374 = new BitSet(new long[]{0, 0, 1024});
    public static final BitSet FOLLOW_register_list_in_insn_format45cc_method3376 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_method_reference_in_insn_format45cc_method3378 = new BitSet(new long[]{0, 0, 1});
    public static final BitSet FOLLOW_method_prototype_in_insn_format45cc_method3380 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT4rcc_METHOD_in_insn_format4rcc_method3403 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method3405 = new BitSet(new long[]{0, 0, 2048});
    public static final BitSet FOLLOW_register_range_in_insn_format4rcc_method3407 = new BitSet(new long[]{65792, 0, 0, 65536});
    public static final BitSet FOLLOW_method_reference_in_insn_format4rcc_method3409 = new BitSet(new long[]{0, 0, 1});
    public static final BitSet FOLLOW_method_prototype_in_insn_format4rcc_method3411 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_FORMAT51l_in_insn_format51l_type3434 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l_type3436 = new BitSet(new long[]{0, 0, 0, 4096});
    public static final BitSet FOLLOW_REGISTER_in_insn_format51l_type3438 = new BitSet(new long[]{549764241408L, 2147483648L, 2305843009213693952L, 32768});
    public static final BitSet FOLLOW_fixed_64bit_literal_in_insn_format51l_type3440 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_ARRAY_DATA_in_insn_array_data_directive3463 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_I_ARRAY_ELEMENT_SIZE_in_insn_array_data_directive3466 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_short_integral_literal_in_insn_array_data_directive3468 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_array_elements_in_insn_array_data_directive3471 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_PACKED_SWITCH_in_insn_packed_switch_directive3493 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_I_PACKED_SWITCH_START_KEY_in_insn_packed_switch_directive3496 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive3498 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_packed_switch_elements_in_insn_packed_switch_directive3501 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_STATEMENT_SPARSE_SWITCH_in_insn_sparse_switch_directive3525 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_sparse_switch_elements_in_insn_sparse_switch_directive3527 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor3548 = new BitSet(new long[]{65536, 0, 0, 1024});
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_array_descriptor3552 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_array_descriptor3580 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor3598 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor3606 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_array_descriptor_in_nonvoid_type_descriptor3614 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor3635 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_array_descriptor_in_reference_type_descriptor3643 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_VOID_TYPE_in_type_descriptor3663 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_type_descriptor3671 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_long_literal_in_short_integral_literal3689 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_integer_literal_in_short_integral_literal3701 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_short_literal_in_short_integral_literal3713 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_char_literal_in_short_integral_literal3721 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_byte_literal_in_short_integral_literal3729 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_long_literal_in_integral_literal3744 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_integer_literal_in_integral_literal3756 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_short_literal_in_integral_literal3764 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_byte_literal_in_integral_literal3772 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_integer_literal3788 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_LONG_LITERAL_in_long_literal3803 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_SHORT_LITERAL_in_short_literal3818 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_BYTE_LITERAL_in_byte_literal3833 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_FLOAT_LITERAL_in_float_literal3848 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_DOUBLE_LITERAL_in_double_literal3863 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_CHAR_LITERAL_in_char_literal3878 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal3893 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_BOOL_LITERAL_in_bool_literal3912 = new BitSet(new long[]{2});
    public static final BitSet FOLLOW_I_ENCODED_ARRAY_in_array_literal3934 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_literal_in_array_literal3937 = new BitSet(new long[]{549764307208L, 2181433216991232L, 2341871806232657921L, 8946696});
    public static final BitSet FOLLOW_I_ANNOTATIONS_in_annotations3962 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_annotation_in_annotations3965 = new BitSet(new long[]{8, 34359738368L});
    public static final BitSet FOLLOW_I_ANNOTATION_in_annotation3994 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_annotation3996 = new BitSet(new long[]{0, 0, 36028797018963968L});
    public static final BitSet FOLLOW_subannotation_in_annotation3998 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_ANNOTATION_ELEMENT_in_annotation_element4019 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_SIMPLE_NAME_in_annotation_element4021 = new BitSet(new long[]{549764307200L, 2181433216991232L, 2341871806232657921L, 8946696});
    public static final BitSet FOLLOW_literal_in_annotation_element4023 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_SUBANNOTATION_in_subannotation4050 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_subannotation4060 = new BitSet(new long[]{8, 137438953472L});
    public static final BitSet FOLLOW_annotation_element_in_subannotation4071 = new BitSet(new long[]{8, 137438953472L});
    public static final BitSet FOLLOW_I_ENCODED_FIELD_in_field_literal4110 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_field_reference_in_field_literal4112 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_ENCODED_METHOD_in_method_literal4133 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_method_reference_in_method_literal4135 = new BitSet(new long[]{8});
    public static final BitSet FOLLOW_I_ENCODED_ENUM_in_enum_literal4156 = new BitSet(new long[]{4});
    public static final BitSet FOLLOW_field_reference_in_enum_literal4158 = new BitSet(new long[]{8});

    public static class access_or_restriction_list_return extends TreeRuleReturnScope {
        public Set<HiddenApiRestriction> hiddenApiRestrictions;
        public int value;
    }

    public static class class_spec_return extends TreeRuleReturnScope {
        public int accessFlags;
        public String type;
    }

    public static class field_reference_return extends TreeRuleReturnScope {
        public ImmutableFieldReference fieldReference;
    }

    public static class header_return extends TreeRuleReturnScope {
        public int accessFlags;
        public String classType;
        public List<String> implementsList;
        public String sourceSpec;
        public String superType;
    }

    public static class instruction_return extends TreeRuleReturnScope {
    }

    public static class method_handle_type_return extends TreeRuleReturnScope {
        public int methodHandleType;
    }

    public static class method_name_and_prototype_return extends TreeRuleReturnScope {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f1040name;
        public List<SmaliMethodParameter> parameters;
        public String returnType;
    }

    public static class nonvoid_type_descriptor_return extends TreeRuleReturnScope {
        public String type;
    }

    public static class reference_type_descriptor_return extends TreeRuleReturnScope {
        public String type;
    }

    public static class register_list_return extends TreeRuleReturnScope {
        public byte registerCount;
        public byte[] registers;
    }

    public static class register_range_return extends TreeRuleReturnScope {
        public int endRegister;
        public int startRegister;
    }

    public static class registers_directive_return extends TreeRuleReturnScope {
        public boolean isLocalsDirective;
        public int registers;
    }

    public static class subannotation_return extends TreeRuleReturnScope {
        public String annotationType;
        public List<AnnotationElement> elements;
    }

    public TreeParser[] getDelegates() {
        return new TreeParser[0];
    }

    public smaliTreeWalker(TreeNodeStream treeNodeStream) {
        this(treeNodeStream, new RecognizerSharedState());
    }

    public smaliTreeWalker(TreeNodeStream treeNodeStream, RecognizerSharedState recognizerSharedState) {
        super(treeNodeStream, recognizerSharedState);
        this.verboseErrors = false;
        this.apiLevel = 15;
        this.opcodes = Opcodes.forApi(15);
        this.callSiteNameIndex = 0;
        this.method_stack = new Stack<>();
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String getGrammarFileName() {
        return "smaliTreeWalker.g";
    }

    public void setDexBuilder(DexBuilder dexBuilder) {
        this.dexBuilder = dexBuilder;
    }

    public void setApiLevel(int i) {
        this.opcodes = Opcodes.forApi(i);
        this.apiLevel = i;
    }

    public void setVerboseErrors(boolean z) {
        this.verboseErrors = z;
    }

    private byte parseRegister_nibble(String str) throws SemanticException {
        int i = this.method_stack.peek().totalMethodRegisters;
        int i2 = this.method_stack.peek().methodParameterRegisters;
        int i3 = Byte.parseByte(str.substring(1));
        if (str.charAt(0) == 'p') {
            i3 += i - i2;
        }
        if (i3 < 32) {
            return (byte) i3;
        }
        throw new SemanticException(this.input, "The maximum allowed register in this context is list of registers is v15", new Object[0]);
    }

    private short parseRegister_byte(String str) throws SemanticException {
        int i = this.method_stack.peek().totalMethodRegisters;
        int i2 = this.method_stack.peek().methodParameterRegisters;
        int i3 = Short.parseShort(str.substring(1));
        if (str.charAt(0) == 'p') {
            i3 += i - i2;
        }
        if (i3 < 512) {
            return (short) i3;
        }
        throw new SemanticException(this.input, "The maximum allowed register in this context is v255", new Object[0]);
    }

    private int parseRegister_short(String str) throws SemanticException {
        int i = this.method_stack.peek().totalMethodRegisters;
        int i2 = this.method_stack.peek().methodParameterRegisters;
        int i3 = Integer.parseInt(str.substring(1));
        if (str.charAt(0) == 'p') {
            i3 += i - i2;
        }
        if (i3 < 131072) {
            return i3;
        }
        throw new SemanticException(this.input, "The maximum allowed register in this context is v65535", new Object[0]);
    }

    @Override // org.antlr.runtime.tree.TreeParser, org.antlr.runtime.BaseRecognizer
    public String getErrorMessage(RecognitionException recognitionException, String[] strArr) {
        if (recognitionException instanceof SemanticException) {
            return recognitionException.getMessage();
        }
        return super.getErrorMessage(recognitionException, strArr);
    }

    @Override // org.antlr.runtime.tree.TreeParser, org.antlr.runtime.BaseRecognizer
    public String getErrorHeader(RecognitionException recognitionException) {
        return getSourceName() + "[" + recognitionException.line + "," + recognitionException.charPositionInLine + "]";
    }

    public final ClassDef smali_file() throws RecognitionException {
        String str;
        int i;
        String str2;
        List<String> list;
        String str3;
        try {
            match(this.input, 109, FOLLOW_I_CLASS_DEF_in_smali_file52);
            match(this.input, 2, null);
            pushFollow(FOLLOW_header_in_smali_file54);
            header_return header_returnVarHeader = header();
            this.state._fsp--;
            pushFollow(FOLLOW_methods_in_smali_file56);
            List<BuilderMethod> listMethods = methods();
            this.state._fsp--;
            pushFollow(FOLLOW_fields_in_smali_file58);
            List<BuilderField> listFields = fields();
            this.state._fsp--;
            pushFollow(FOLLOW_annotations_in_smali_file60);
            Set<Annotation> setAnnotations = annotations();
            this.state._fsp--;
            match(this.input, 3, null);
            DexBuilder dexBuilder = this.dexBuilder;
            if (header_returnVarHeader != null) {
                header_return header_returnVar = header_returnVarHeader;
                str = header_returnVarHeader.classType;
            } else {
                str = null;
            }
            if (header_returnVarHeader != null) {
                header_return header_returnVar2 = header_returnVarHeader;
                i = header_returnVarHeader.accessFlags;
            } else {
                i = 0;
            }
            int i2 = i;
            if (header_returnVarHeader != null) {
                header_return header_returnVar3 = header_returnVarHeader;
                str2 = header_returnVarHeader.superType;
            } else {
                str2 = null;
            }
            if (header_returnVarHeader != null) {
                header_return header_returnVar4 = header_returnVarHeader;
                list = header_returnVarHeader.implementsList;
            } else {
                list = null;
            }
            if (header_returnVarHeader != null) {
                header_return header_returnVar5 = header_returnVarHeader;
                str3 = header_returnVarHeader.sourceSpec;
            } else {
                str3 = null;
            }
            return dexBuilder.internClassDef(str, i2, str2, list, str3, setAnnotations, listFields, listMethods);
        } catch (Exception e) {
            if (this.verboseErrors) {
                e.printStackTrace(System.err);
            }
            reportError(new SemanticException(this.input, e));
            return null;
        }
    }

    public final header_return header() throws RecognitionException {
        String strSuper_spec;
        int i;
        header_return header_returnVar = new header_return();
        header_returnVar.start = this.input.LT(1);
        try {
            pushFollow(FOLLOW_class_spec_in_header85);
            class_spec_return class_spec_returnVarClass_spec = class_spec();
            this.state._fsp--;
            String str = null;
            if (this.input.LA(1) == 184) {
                pushFollow(FOLLOW_super_spec_in_header87);
                strSuper_spec = super_spec();
                this.state._fsp--;
            } else {
                strSuper_spec = null;
            }
            pushFollow(FOLLOW_implements_list_in_header90);
            List<String> listImplements_list = implements_list();
            this.state._fsp--;
            pushFollow(FOLLOW_source_spec_in_header92);
            String strSource_spec = source_spec();
            this.state._fsp--;
            if (class_spec_returnVarClass_spec != null) {
                class_spec_return class_spec_returnVar = class_spec_returnVarClass_spec;
                str = class_spec_returnVarClass_spec.type;
            }
            this.classType = str;
            header_returnVar.classType = str;
            if (class_spec_returnVarClass_spec != null) {
                class_spec_return class_spec_returnVar2 = class_spec_returnVarClass_spec;
                i = class_spec_returnVarClass_spec.accessFlags;
            } else {
                i = 0;
            }
            header_returnVar.accessFlags = i;
            header_returnVar.superType = strSuper_spec;
            header_returnVar.implementsList = listImplements_list;
            header_returnVar.sourceSpec = strSource_spec;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return header_returnVar;
    }

    public final class_spec_return class_spec() throws RecognitionException {
        class_spec_return class_spec_returnVar = new class_spec_return();
        class_spec_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_class_spec110);
            pushFollow(FOLLOW_access_list_in_class_spec112);
            int iAccess_list = access_list();
            this.state._fsp--;
            class_spec_returnVar.type = commonTree != null ? commonTree.getText() : null;
            class_spec_returnVar.accessFlags = iAccess_list;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return class_spec_returnVar;
    }

    public final String super_spec() throws RecognitionException {
        try {
            match(this.input, 184, FOLLOW_I_SUPER_in_super_spec130);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_super_spec132);
            match(this.input, 3, null);
            if (commonTree != null) {
                return commonTree.getText();
            }
            return null;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final String implements_spec() throws RecognitionException {
        try {
            match(this.input, 121, FOLLOW_I_IMPLEMENTS_in_implements_spec152);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_implements_spec154);
            match(this.input, 3, null);
            if (commonTree != null) {
                return commonTree.getText();
            }
            return null;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final List<String> implements_list() throws RecognitionException {
        try {
            ArrayList arrayListNewArrayList = ListUtil.newArrayList();
            while (this.input.LA(1) == 121) {
                pushFollow(FOLLOW_implements_spec_in_implements_list184);
                String strImplements_spec = implements_spec();
                this.state._fsp--;
                arrayListNewArrayList.add(strImplements_spec);
            }
            if (arrayListNewArrayList.size() > 0) {
                return arrayListNewArrayList;
            }
            return null;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final String source_spec() throws RecognitionException {
        String strString_literal;
        RecognitionException e;
        try {
            int iLA = this.input.LA(1);
            if (iLA != 141) {
                if (iLA == 127) {
                    return null;
                }
                throw new NoViableAltException("", 3, 0, this.input);
            }
            match(this.input, 141, FOLLOW_I_SOURCE_in_source_spec213);
            match(this.input, 2, null);
            pushFollow(FOLLOW_string_literal_in_source_spec215);
            strString_literal = string_literal();
            this.state._fsp--;
            try {
                match(this.input, 3, null);
            } catch (RecognitionException e2) {
                e = e2;
                reportError(e);
                recover(this.input, e);
            }
            return strString_literal;
        } catch (RecognitionException e3) {
            strString_literal = null;
            e = e3;
        }
    }

    public final int access_list() throws RecognitionException {
        int value = 0;
        try {
            match(this.input, 97, FOLLOW_I_ACCESS_LIST_in_access_list247);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (this.input.LA(1) == 4) {
                    value |= AccessFlags.getAccessFlag(((CommonTree) match(this.input, 4, FOLLOW_ACCESS_SPEC_in_access_list265)).getText()).getValue();
                }
                match(this.input, 3, null);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return value;
    }

    public final access_or_restriction_list_return access_or_restriction_list() throws RecognitionException {
        char c;
        access_or_restriction_list_return access_or_restriction_list_returnVar = new access_or_restriction_list_return();
        access_or_restriction_list_returnVar.start = this.input.LT(1);
        access_or_restriction_list_returnVar.value = 0;
        ArrayList arrayList = new ArrayList();
        try {
            match(this.input, 98, FOLLOW_I_ACCESS_OR_RESTRICTION_LIST_in_access_or_restriction_list308);
            HiddenApiRestriction hiddenApiRestriction = null;
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                HiddenApiRestriction hiddenApiRestriction2 = null;
                while (true) {
                    int iLA = this.input.LA(1);
                    if (iLA == 4) {
                        c = 1;
                    } else {
                        c = iLA == 41 ? (char) 2 : (char) 3;
                    }
                    if (c == 1) {
                        access_or_restriction_list_returnVar.value = AccessFlags.getAccessFlag(((CommonTree) match(this.input, 4, FOLLOW_ACCESS_SPEC_in_access_or_restriction_list326)).getText()).getValue() | access_or_restriction_list_returnVar.value;
                    } else if (c == 2) {
                        CommonTree commonTree = (CommonTree) match(this.input, 41, FOLLOW_HIDDENAPI_RESTRICTION_in_access_or_restriction_list356);
                        if (this.opcodes.api < 29) {
                            throw new SemanticException(this.input, commonTree, "Hidden API restrictions are only supported on api 29 and above.", new Object[0]);
                        }
                        HiddenApiRestriction hiddenApiRestrictionForName = HiddenApiRestriction.forName(commonTree.getText());
                        if (hiddenApiRestrictionForName.isDomainSpecificApiFlag()) {
                            arrayList.add(hiddenApiRestrictionForName);
                        } else {
                            if (hiddenApiRestriction2 != null) {
                                throw new SemanticException(this.input, commonTree, "Only one hidden api restriction may be specified.", new Object[0]);
                            }
                            hiddenApiRestriction2 = hiddenApiRestrictionForName;
                        }
                    } else {
                        match(this.input, 3, null);
                        hiddenApiRestriction = hiddenApiRestriction2;
                        break;
                    }
                }
            }
            HashSet hashSet = new HashSet();
            if (hiddenApiRestriction != null) {
                hashSet.add(hiddenApiRestriction);
            }
            hashSet.addAll(arrayList);
            access_or_restriction_list_returnVar.hiddenApiRestrictions = hashSet;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return access_or_restriction_list_returnVar;
    }

    public final List<BuilderField> fields() throws RecognitionException {
        ArrayList arrayListNewArrayList = ListUtil.newArrayList();
        try {
            match(this.input, 118, FOLLOW_I_FIELDS_in_fields405);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (this.input.LA(1) == 117) {
                    pushFollow(FOLLOW_field_in_fields414);
                    BuilderField builderFieldField = field();
                    this.state._fsp--;
                    arrayListNewArrayList.add(builderFieldField);
                }
                match(this.input, 3, null);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return arrayListNewArrayList;
    }

    public final List<BuilderMethod> methods() throws RecognitionException {
        ArrayList arrayListNewArrayList = ListUtil.newArrayList();
        try {
            match(this.input, 127, FOLLOW_I_METHODS_in_methods446);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (this.input.LA(1) == 126) {
                    pushFollow(FOLLOW_method_in_methods455);
                    BuilderMethod builderMethodMethod = method();
                    this.state._fsp--;
                    arrayListNewArrayList.add(builderMethodMethod);
                }
                match(this.input, 3, null);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return arrayListNewArrayList;
    }

    public final BuilderField field() throws RecognitionException {
        Set<Annotation> set;
        int i;
        Set<HiddenApiRestriction> set2;
        String str;
        try {
            match(this.input, 117, FOLLOW_I_FIELD_in_field480);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_field482);
            pushFollow(FOLLOW_access_or_restriction_list_in_field484);
            access_or_restriction_list_return access_or_restriction_list_returnVarAccess_or_restriction_list = access_or_restriction_list();
            this.state._fsp--;
            match(this.input, 120, FOLLOW_I_FIELD_TYPE_in_field487);
            match(this.input, 2, null);
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_field489);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            match(this.input, 3, null);
            pushFollow(FOLLOW_field_initial_value_in_field492);
            EncodedValue encodedValueField_initial_value = field_initial_value();
            this.state._fsp--;
            if (this.input.LA(1) == 100) {
                pushFollow(FOLLOW_annotations_in_field494);
                Set<Annotation> setAnnotations = annotations();
                this.state._fsp--;
                set = setAnnotations;
            } else {
                set = null;
            }
            match(this.input, 3, null);
            if (access_or_restriction_list_returnVarAccess_or_restriction_list != null) {
                access_or_restriction_list_return access_or_restriction_list_returnVar = access_or_restriction_list_returnVarAccess_or_restriction_list;
                i = access_or_restriction_list_returnVarAccess_or_restriction_list.value;
            } else {
                i = 0;
            }
            if (access_or_restriction_list_returnVarAccess_or_restriction_list != null) {
                access_or_restriction_list_return access_or_restriction_list_returnVar2 = access_or_restriction_list_returnVarAccess_or_restriction_list;
                set2 = access_or_restriction_list_returnVarAccess_or_restriction_list.hiddenApiRestrictions;
            } else {
                set2 = null;
            }
            if (!AccessFlags.STATIC.isSet(i) && encodedValueField_initial_value != null) {
                throw new SemanticException(this.input, "Initial field values can only be specified for static fields.", new Object[0]);
            }
            DexBuilder dexBuilder = this.dexBuilder;
            String str2 = this.classType;
            String text = commonTree != null ? commonTree.getText() : null;
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
                str = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
            } else {
                str = null;
            }
            return dexBuilder.internField(str2, text, str, i, encodedValueField_initial_value, set, set2);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final EncodedValue field_initial_value() throws RecognitionException {
        try {
            int iLA = this.input.LA(1);
            if (iLA != 119) {
                if (iLA != 3 && iLA != 100) {
                    throw new NoViableAltException("", 9, 0, this.input);
                }
                return null;
            }
            match(this.input, 119, FOLLOW_I_FIELD_INITIAL_VALUE_in_field_initial_value515);
            match(this.input, 2, null);
            pushFollow(FOLLOW_literal_in_field_initial_value517);
            ImmutableEncodedValue immutableEncodedValueLiteral = literal();
            this.state._fsp--;
            match(this.input, 3, null);
            return immutableEncodedValueLiteral;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final ImmutableEncodedValue literal() throws RecognitionException {
        ImmutableEncodedValue immutableIntEncodedValue;
        ImmutableEncodedValue immutableLongEncodedValue;
        String str;
        List<AnnotationElement> list;
        try {
            int iLA = this.input.LA(1);
            char c = 11;
            if (iLA == 11) {
                c = '\t';
            } else if (iLA == 12) {
                c = 4;
            } else if (iLA == 15) {
                c = 7;
            } else if (iLA != 16) {
                switch (iLA) {
                    case 8:
                    case 202:
                    case 215:
                        break;
                    case 23:
                        c = 6;
                        break;
                    case 39:
                        c = 5;
                        break;
                    case 95:
                        c = 1;
                        break;
                    case 128:
                        c = 18;
                        break;
                    case 183:
                        c = '\r';
                        break;
                    case 189:
                        c = 2;
                        break;
                    case 195:
                        c = '\n';
                        break;
                    case 207:
                        c = 3;
                        break;
                    case 211:
                        c = '\b';
                        break;
                    default:
                        switch (iLA) {
                            case 110:
                                c = '\f';
                                break;
                            case 111:
                                c = 16;
                                break;
                            case 112:
                                c = 14;
                                break;
                            case 113:
                                c = 15;
                                break;
                            case 114:
                                c = 17;
                                break;
                            default:
                                throw new NoViableAltException("", 10, 0, this.input);
                        }
                        break;
                }
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_integer_literal_in_literal539);
                    int iInteger_literal = integer_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableIntEncodedValue(iInteger_literal);
                    return immutableIntEncodedValue;
                case 2:
                    pushFollow(FOLLOW_long_literal_in_literal547);
                    long jLong_literal = long_literal();
                    this.state._fsp--;
                    immutableLongEncodedValue = new ImmutableLongEncodedValue(jLong_literal);
                    return immutableLongEncodedValue;
                case 3:
                    pushFollow(FOLLOW_short_literal_in_literal555);
                    short sShort_literal = short_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableShortEncodedValue(sShort_literal);
                    return immutableIntEncodedValue;
                case 4:
                    pushFollow(FOLLOW_byte_literal_in_literal563);
                    byte bByte_literal = byte_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableByteEncodedValue(bByte_literal);
                    return immutableIntEncodedValue;
                case 5:
                    pushFollow(FOLLOW_float_literal_in_literal571);
                    float fFloat_literal = float_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableFloatEncodedValue(fFloat_literal);
                    return immutableIntEncodedValue;
                case 6:
                    pushFollow(FOLLOW_double_literal_in_literal579);
                    double dDouble_literal = double_literal();
                    this.state._fsp--;
                    immutableLongEncodedValue = new ImmutableDoubleEncodedValue(dDouble_literal);
                    return immutableLongEncodedValue;
                case 7:
                    pushFollow(FOLLOW_char_literal_in_literal587);
                    char cChar_literal = char_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableCharEncodedValue(cChar_literal);
                    return immutableIntEncodedValue;
                case '\b':
                    pushFollow(FOLLOW_string_literal_in_literal595);
                    String strString_literal = string_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableStringEncodedValue(strString_literal);
                    return immutableIntEncodedValue;
                case '\t':
                    pushFollow(FOLLOW_bool_literal_in_literal603);
                    boolean zBool_literal = bool_literal();
                    this.state._fsp--;
                    return ImmutableBooleanEncodedValue.forBoolean(zBool_literal);
                case '\n':
                    match(this.input, 195, FOLLOW_NULL_LITERAL_in_literal611);
                    return ImmutableNullEncodedValue.INSTANCE;
                case 11:
                    pushFollow(FOLLOW_type_descriptor_in_literal619);
                    String strType_descriptor = type_descriptor();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableTypeEncodedValue(strType_descriptor);
                    return immutableIntEncodedValue;
                case '\f':
                    pushFollow(FOLLOW_array_literal_in_literal627);
                    List<EncodedValue> listArray_literal = array_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableArrayEncodedValue(listArray_literal);
                    return immutableIntEncodedValue;
                case '\r':
                    pushFollow(FOLLOW_subannotation_in_literal635);
                    subannotation_return subannotation_returnVarSubannotation = subannotation();
                    this.state._fsp--;
                    if (subannotation_returnVarSubannotation != null) {
                        subannotation_return subannotation_returnVar = subannotation_returnVarSubannotation;
                        str = subannotation_returnVarSubannotation.annotationType;
                    } else {
                        str = null;
                    }
                    if (subannotation_returnVarSubannotation != null) {
                        subannotation_return subannotation_returnVar2 = subannotation_returnVarSubannotation;
                        list = subannotation_returnVarSubannotation.elements;
                    } else {
                        list = null;
                    }
                    immutableIntEncodedValue = new ImmutableAnnotationEncodedValue(str, list);
                    return immutableIntEncodedValue;
                case 14:
                    pushFollow(FOLLOW_field_literal_in_literal643);
                    ImmutableFieldReference immutableFieldReferenceField_literal = field_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableFieldEncodedValue(immutableFieldReferenceField_literal);
                    return immutableIntEncodedValue;
                case 15:
                    pushFollow(FOLLOW_method_literal_in_literal651);
                    ImmutableMethodReference immutableMethodReferenceMethod_literal = method_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableMethodEncodedValue(immutableMethodReferenceMethod_literal);
                    return immutableIntEncodedValue;
                case 16:
                    pushFollow(FOLLOW_enum_literal_in_literal659);
                    ImmutableFieldReference immutableFieldReferenceEnum_literal = enum_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableEnumEncodedValue(immutableFieldReferenceEnum_literal);
                    return immutableIntEncodedValue;
                case 17:
                    pushFollow(FOLLOW_method_handle_literal_in_literal667);
                    ImmutableMethodHandleReference immutableMethodHandleReferenceMethod_handle_literal = method_handle_literal();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableMethodHandleEncodedValue(immutableMethodHandleReferenceMethod_handle_literal);
                    return immutableIntEncodedValue;
                case 18:
                    pushFollow(FOLLOW_method_prototype_in_literal675);
                    ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype();
                    this.state._fsp--;
                    immutableIntEncodedValue = new ImmutableMethodTypeEncodedValue(immutableMethodProtoReferenceMethod_prototype);
                    return immutableIntEncodedValue;
                default:
                    return null;
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final Number fixed_64bit_literal_number() throws RecognitionException {
        char c;
        try {
            int iLA = this.input.LA(1);
            if (iLA == 11) {
                c = '\b';
            } else if (iLA == 12) {
                c = 4;
            } else if (iLA == 15) {
                c = 7;
            } else if (iLA == 23) {
                c = 6;
            } else if (iLA == 39) {
                c = 5;
            } else if (iLA == 95) {
                c = 1;
            } else if (iLA == 189) {
                c = 2;
            } else {
                if (iLA != 207) {
                    throw new NoViableAltException("", 11, 0, this.input);
                }
                c = 3;
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_integer_literal_in_fixed_64bit_literal_number691);
                    int iInteger_literal = integer_literal();
                    this.state._fsp--;
                    return Integer.valueOf(iInteger_literal);
                case 2:
                    pushFollow(FOLLOW_long_literal_in_fixed_64bit_literal_number699);
                    long jLong_literal = long_literal();
                    this.state._fsp--;
                    return Long.valueOf(jLong_literal);
                case 3:
                    pushFollow(FOLLOW_short_literal_in_fixed_64bit_literal_number707);
                    short sShort_literal = short_literal();
                    this.state._fsp--;
                    return Short.valueOf(sShort_literal);
                case 4:
                    pushFollow(FOLLOW_byte_literal_in_fixed_64bit_literal_number715);
                    byte bByte_literal = byte_literal();
                    this.state._fsp--;
                    return Byte.valueOf(bByte_literal);
                case 5:
                    pushFollow(FOLLOW_float_literal_in_fixed_64bit_literal_number723);
                    float fFloat_literal = float_literal();
                    this.state._fsp--;
                    return Integer.valueOf(Float.floatToRawIntBits(fFloat_literal));
                case 6:
                    pushFollow(FOLLOW_double_literal_in_fixed_64bit_literal_number731);
                    double dDouble_literal = double_literal();
                    this.state._fsp--;
                    return Long.valueOf(Double.doubleToRawLongBits(dDouble_literal));
                case 7:
                    pushFollow(FOLLOW_char_literal_in_fixed_64bit_literal_number739);
                    char cChar_literal = char_literal();
                    this.state._fsp--;
                    return Integer.valueOf(cChar_literal);
                case '\b':
                    pushFollow(FOLLOW_bool_literal_in_fixed_64bit_literal_number747);
                    boolean zBool_literal = bool_literal();
                    this.state._fsp--;
                    return Integer.valueOf(zBool_literal ? 1 : 0);
                default:
                    return null;
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final long fixed_64bit_literal() throws RecognitionException {
        char c;
        int iInteger_literal;
        try {
            int iLA = this.input.LA(1);
            if (iLA == 11) {
                c = '\b';
            } else if (iLA == 12) {
                c = 4;
            } else if (iLA == 15) {
                c = 7;
            } else if (iLA == 23) {
                c = 6;
            } else if (iLA == 39) {
                c = 5;
            } else if (iLA == 95) {
                c = 1;
            } else if (iLA == 189) {
                c = 2;
            } else {
                if (iLA != 207) {
                    throw new NoViableAltException("", 12, 0, this.input);
                }
                c = 3;
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_integer_literal_in_fixed_64bit_literal762);
                    iInteger_literal = integer_literal();
                    this.state._fsp--;
                    break;
                case 2:
                    pushFollow(FOLLOW_long_literal_in_fixed_64bit_literal770);
                    long jLong_literal = long_literal();
                    this.state._fsp--;
                    return jLong_literal;
                case 3:
                    pushFollow(FOLLOW_short_literal_in_fixed_64bit_literal778);
                    iInteger_literal = short_literal();
                    this.state._fsp--;
                    break;
                case 4:
                    pushFollow(FOLLOW_byte_literal_in_fixed_64bit_literal786);
                    iInteger_literal = byte_literal();
                    this.state._fsp--;
                    break;
                case 5:
                    pushFollow(FOLLOW_float_literal_in_fixed_64bit_literal794);
                    float fFloat_literal = float_literal();
                    this.state._fsp--;
                    return Float.floatToRawIntBits(fFloat_literal);
                case 6:
                    pushFollow(FOLLOW_double_literal_in_fixed_64bit_literal802);
                    double dDouble_literal = double_literal();
                    this.state._fsp--;
                    return Double.doubleToRawLongBits(dDouble_literal);
                case 7:
                    pushFollow(FOLLOW_char_literal_in_fixed_64bit_literal810);
                    iInteger_literal = char_literal();
                    this.state._fsp--;
                    break;
                case '\b':
                    pushFollow(FOLLOW_bool_literal_in_fixed_64bit_literal818);
                    boolean zBool_literal = bool_literal();
                    this.state._fsp--;
                    return zBool_literal ? 1L : 0L;
                default:
                    return 0L;
            }
            return iInteger_literal;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return 0L;
        }
    }

    public final int fixed_32bit_literal() throws RecognitionException {
        char c;
        boolean z;
        try {
            int iLA = this.input.LA(1);
            if (iLA == 11) {
                c = 7;
            } else if (iLA == 12) {
                c = 4;
            } else if (iLA == 15) {
                c = 6;
            } else if (iLA == 39) {
                c = 5;
            } else if (iLA == 95) {
                c = 1;
            } else if (iLA == 189) {
                c = 2;
            } else {
                if (iLA != 207) {
                    throw new NoViableAltException("", 13, 0, this.input);
                }
                c = 3;
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_integer_literal_in_fixed_32bit_literal835);
                    int iInteger_literal = integer_literal();
                    this.state._fsp--;
                    z = iInteger_literal;
                    break;
                case 2:
                    pushFollow(FOLLOW_long_literal_in_fixed_32bit_literal843);
                    long jLong_literal = long_literal();
                    this.state._fsp--;
                    LiteralTools.checkInt(jLong_literal);
                    return (int) jLong_literal;
                case 3:
                    pushFollow(FOLLOW_short_literal_in_fixed_32bit_literal851);
                    short sShort_literal = short_literal();
                    this.state._fsp--;
                    z = sShort_literal;
                    break;
                case 4:
                    pushFollow(FOLLOW_byte_literal_in_fixed_32bit_literal859);
                    byte bByte_literal = byte_literal();
                    this.state._fsp--;
                    z = bByte_literal;
                    break;
                case 5:
                    pushFollow(FOLLOW_float_literal_in_fixed_32bit_literal867);
                    float fFloat_literal = float_literal();
                    this.state._fsp--;
                    return Float.floatToRawIntBits(fFloat_literal);
                case 6:
                    pushFollow(FOLLOW_char_literal_in_fixed_32bit_literal875);
                    char cChar_literal = char_literal();
                    this.state._fsp--;
                    z = cChar_literal;
                    break;
                case 7:
                    pushFollow(FOLLOW_bool_literal_in_fixed_32bit_literal883);
                    boolean zBool_literal = bool_literal();
                    this.state._fsp--;
                    z = zBool_literal;
                    break;
                default:
                    return 0;
            }
            return z ? 1 : 0;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return 0;
        }
    }

    public final List<Number> array_elements() throws RecognitionException {
        ArrayList arrayListNewArrayList;
        RecognitionException e;
        try {
            arrayListNewArrayList = ListUtil.newArrayList();
            try {
                match(this.input, 102, FOLLOW_I_ARRAY_ELEMENTS_in_array_elements905);
                if (this.input.LA(1) == 2) {
                    match(this.input, 2, null);
                    while (true) {
                        int iLA = this.input.LA(1);
                        if ((iLA < 11 || iLA > 12) && iLA != 15 && iLA != 23 && iLA != 39 && iLA != 95 && iLA != 189 && iLA != 207) {
                            break;
                        }
                        pushFollow(FOLLOW_fixed_64bit_literal_number_in_array_elements914);
                        Number numberFixed_64bit_literal_number = fixed_64bit_literal_number();
                        this.state._fsp--;
                        arrayListNewArrayList.add(numberFixed_64bit_literal_number);
                    }
                    match(this.input, 3, null);
                }
            } catch (RecognitionException e2) {
                e = e2;
                reportError(e);
                recover(this.input, e);
            }
        } catch (RecognitionException e3) {
            arrayListNewArrayList = null;
            e = e3;
        }
        return arrayListNewArrayList;
    }

    public final List<Label> packed_switch_elements() throws RecognitionException {
        ArrayList arrayListNewArrayList = ListUtil.newArrayList();
        try {
            match(this.input, 131, FOLLOW_I_PACKED_SWITCH_ELEMENTS_in_packed_switch_elements950);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (this.input.LA(1) == 208) {
                    pushFollow(FOLLOW_label_ref_in_packed_switch_elements959);
                    Label labelLabel_ref = label_ref();
                    this.state._fsp--;
                    arrayListNewArrayList.add(labelLabel_ref);
                }
                match(this.input, 3, null);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return arrayListNewArrayList;
    }

    public final List<SwitchLabelElement> sparse_switch_elements() throws RecognitionException {
        ArrayList arrayListNewArrayList = ListUtil.newArrayList();
        try {
            match(this.input, 142, FOLLOW_I_SPARSE_SWITCH_ELEMENTS_in_sparse_switch_elements994);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (true) {
                    int iLA = this.input.LA(1);
                    if ((iLA < 11 || iLA > 12) && iLA != 15 && iLA != 39 && iLA != 95 && iLA != 189 && iLA != 207) {
                        break;
                    }
                    pushFollow(FOLLOW_fixed_32bit_literal_in_sparse_switch_elements1004);
                    int iFixed_32bit_literal = fixed_32bit_literal();
                    this.state._fsp--;
                    pushFollow(FOLLOW_label_ref_in_sparse_switch_elements1006);
                    Label labelLabel_ref = label_ref();
                    this.state._fsp--;
                    arrayListNewArrayList.add(new SwitchLabelElement(iFixed_32bit_literal, labelLabel_ref));
                }
                match(this.input, 3, null);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return arrayListNewArrayList;
    }

    protected static class method_scope {
        boolean isStatic;
        MethodImplementationBuilder methodBuilder;
        int methodParameterRegisters;
        int totalMethodRegisters;

        protected method_scope() {
        }
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0113 A[Catch: all -> 0x02e9, RecognitionException -> 0x02eb, TryCatch #0 {RecognitionException -> 0x02eb, blocks: (B:3:0x002c, B:5:0x0061, B:8:0x006a, B:10:0x0072, B:12:0x008c, B:14:0x0093, B:45:0x013f, B:47:0x0164, B:49:0x016b, B:56:0x01a4, B:61:0x01c1, B:62:0x01cc, B:67:0x01d6, B:71:0x01e0, B:73:0x01e7, B:75:0x01ed, B:77:0x01f3, B:78:0x01fc, B:79:0x01fd, B:81:0x0203, B:83:0x0209, B:84:0x0212, B:85:0x0213, B:87:0x021d, B:108:0x029d, B:110:0x02a3, B:113:0x02ac, B:116:0x02b5, B:118:0x02bd, B:90:0x022a, B:91:0x0237, B:92:0x0238, B:93:0x0245, B:97:0x024c, B:100:0x0254, B:102:0x026a, B:104:0x0270, B:106:0x0276, B:107:0x029c, B:119:0x02c5, B:120:0x02d0, B:121:0x02d1, B:122:0x02dc, B:123:0x02dd, B:124:0x02e8, B:52:0x0196, B:28:0x00ca, B:30:0x00db, B:32:0x00ed, B:34:0x00f4, B:36:0x00fe, B:38:0x0105, B:44:0x0126, B:39:0x0113, B:41:0x011d, B:43:0x0124, B:22:0x00b8, B:23:0x00c3), top: B:133:0x002c, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:41:0x011d A[Catch: all -> 0x02e9, RecognitionException -> 0x02eb, TryCatch #0 {RecognitionException -> 0x02eb, blocks: (B:3:0x002c, B:5:0x0061, B:8:0x006a, B:10:0x0072, B:12:0x008c, B:14:0x0093, B:45:0x013f, B:47:0x0164, B:49:0x016b, B:56:0x01a4, B:61:0x01c1, B:62:0x01cc, B:67:0x01d6, B:71:0x01e0, B:73:0x01e7, B:75:0x01ed, B:77:0x01f3, B:78:0x01fc, B:79:0x01fd, B:81:0x0203, B:83:0x0209, B:84:0x0212, B:85:0x0213, B:87:0x021d, B:108:0x029d, B:110:0x02a3, B:113:0x02ac, B:116:0x02b5, B:118:0x02bd, B:90:0x022a, B:91:0x0237, B:92:0x0238, B:93:0x0245, B:97:0x024c, B:100:0x0254, B:102:0x026a, B:104:0x0270, B:106:0x0276, B:107:0x029c, B:119:0x02c5, B:120:0x02d0, B:121:0x02d1, B:122:0x02dc, B:123:0x02dd, B:124:0x02e8, B:52:0x0196, B:28:0x00ca, B:30:0x00db, B:32:0x00ed, B:34:0x00f4, B:36:0x00fe, B:38:0x0105, B:44:0x0126, B:39:0x0113, B:41:0x011d, B:43:0x0124, B:22:0x00b8, B:23:0x00c3), top: B:133:0x002c, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x0123  */
    public final BuilderMethod method() throws RecognitionException {
        int i;
        Set<HiddenApiRestriction> set;
        List<SmaliMethodParameter> list;
        char c;
        registers_directive_return registers_directive_returnVarRegisters_directive;
        int i2;
        int i3;
        List<SmaliMethodParameter> list2;
        boolean z;
        String str;
        List<SmaliMethodParameter> list3;
        String str2;
        String str3;
        this.method_stack.push(new method_scope());
        this.method_stack.peek().totalMethodRegisters = 0;
        this.method_stack.peek().methodParameterRegisters = 0;
        this.method_stack.peek().isStatic = false;
        BuilderMethod builderMethodInternMethod = null;
        try {
            try {
                CommonTree commonTree = (CommonTree) match(this.input, 126, FOLLOW_I_METHOD_in_method1058);
                match(this.input, 2, null);
                pushFollow(FOLLOW_method_name_and_prototype_in_method1066);
                method_name_and_prototype_return method_name_and_prototype_returnVarMethod_name_and_prototype = method_name_and_prototype();
                boolean z2 = true;
                this.state._fsp--;
                pushFollow(FOLLOW_access_or_restriction_list_in_method1074);
                access_or_restriction_list_return access_or_restriction_list_returnVarAccess_or_restriction_list = access_or_restriction_list();
                this.state._fsp--;
                if (access_or_restriction_list_returnVarAccess_or_restriction_list != null) {
                    access_or_restriction_list_return access_or_restriction_list_returnVar = access_or_restriction_list_returnVarAccess_or_restriction_list;
                    i = access_or_restriction_list_returnVarAccess_or_restriction_list.value;
                } else {
                    i = 0;
                }
                if (access_or_restriction_list_returnVarAccess_or_restriction_list != null) {
                    access_or_restriction_list_return access_or_restriction_list_returnVar2 = access_or_restriction_list_returnVarAccess_or_restriction_list;
                    set = access_or_restriction_list_returnVarAccess_or_restriction_list.hiddenApiRestrictions;
                } else {
                    set = null;
                }
                this.method_stack.peek().isStatic = AccessFlags.STATIC.isSet(i);
                method_scope method_scopeVarPeek = this.method_stack.peek();
                if (method_name_and_prototype_returnVarMethod_name_and_prototype != null) {
                    method_name_and_prototype_return method_name_and_prototype_returnVar = method_name_and_prototype_returnVarMethod_name_and_prototype;
                    list = method_name_and_prototype_returnVarMethod_name_and_prototype.parameters;
                } else {
                    list = null;
                }
                method_scopeVarPeek.methodParameterRegisters = MethodUtil.getParameterRegisterCount(list, this.method_stack.peek().isStatic);
                int iLA = this.input.LA(1);
                if (iLA == 125 || iLA == 137) {
                    c = 1;
                } else {
                    if (iLA != 130) {
                        throw new NoViableAltException("", 17, 0, this.input);
                    }
                    c = 2;
                }
                if (c != 1) {
                    if (c == 2) {
                        this.method_stack.peek().methodBuilder = new MethodImplementationBuilder(0);
                    }
                    registers_directive_returnVarRegisters_directive = null;
                } else {
                    pushFollow(FOLLOW_registers_directive_in_method1101);
                    registers_directive_returnVarRegisters_directive = registers_directive();
                    this.state._fsp--;
                    if (registers_directive_returnVarRegisters_directive != null) {
                        registers_directive_return registers_directive_returnVar = registers_directive_returnVarRegisters_directive;
                        if (registers_directive_returnVarRegisters_directive.isLocalsDirective) {
                            method_scope method_scopeVarPeek2 = this.method_stack.peek();
                            if (registers_directive_returnVarRegisters_directive != null) {
                                registers_directive_return registers_directive_returnVar2 = registers_directive_returnVarRegisters_directive;
                                i3 = registers_directive_returnVarRegisters_directive.registers;
                            } else {
                                i3 = 0;
                            }
                            method_scopeVarPeek2.totalMethodRegisters = i3 + this.method_stack.peek().methodParameterRegisters;
                        } else {
                            method_scope method_scopeVarPeek3 = this.method_stack.peek();
                            if (registers_directive_returnVarRegisters_directive != null) {
                                registers_directive_return registers_directive_returnVar3 = registers_directive_returnVarRegisters_directive;
                                i2 = registers_directive_returnVarRegisters_directive.registers;
                            } else {
                                i2 = 0;
                            }
                            method_scopeVarPeek3.totalMethodRegisters = i2;
                        }
                    } else {
                        method_scope method_scopeVarPeek4 = this.method_stack.peek();
                        if (registers_directive_returnVarRegisters_directive != null) {
                            registers_directive_return registers_directive_returnVar4 = registers_directive_returnVarRegisters_directive;
                            i2 = registers_directive_returnVarRegisters_directive.registers;
                        } else {
                            i2 = 0;
                        }
                        method_scopeVarPeek4.totalMethodRegisters = i2;
                    }
                    this.method_stack.peek().methodBuilder = new MethodImplementationBuilder(this.method_stack.peek().totalMethodRegisters);
                }
                pushFollow(FOLLOW_ordered_method_items_in_method1158);
                ordered_method_items();
                this.state._fsp--;
                pushFollow(FOLLOW_catches_in_method1166);
                catches();
                this.state._fsp--;
                pushFollow(FOLLOW_parameters_in_method1174);
                if (method_name_and_prototype_returnVarMethod_name_and_prototype != null) {
                    method_name_and_prototype_return method_name_and_prototype_returnVar2 = method_name_and_prototype_returnVarMethod_name_and_prototype;
                    list2 = method_name_and_prototype_returnVarMethod_name_and_prototype.parameters;
                } else {
                    list2 = null;
                }
                parameters(list2);
                this.state._fsp--;
                pushFollow(FOLLOW_annotations_in_method1183);
                Set<Annotation> setAnnotations = annotations();
                this.state._fsp--;
                match(this.input, 3, null);
                if ((AccessFlags.ABSTRACT.getValue() & i) != 0) {
                    z = false;
                } else if ((AccessFlags.NATIVE.getValue() & i) != 0) {
                    z = true;
                    z2 = false;
                } else {
                    z = false;
                    z2 = false;
                }
                MethodImplementation methodImplementation = this.method_stack.peek().methodBuilder.getMethodImplementation();
                if (Iterables.isEmpty(methodImplementation.getInstructions())) {
                    if (!z2 && !z) {
                        throw new SemanticException(this.input, commonTree, "A non-abstract/non-native method must have at least 1 instruction", new Object[0]);
                    }
                    if (z2) {
                        str3 = "an abstract";
                    } else {
                        str3 = "a native";
                    }
                    if ((registers_directive_returnVarRegisters_directive != null ? (CommonTree) registers_directive_returnVarRegisters_directive.start : null) != null) {
                        if (registers_directive_returnVarRegisters_directive != null && registers_directive_returnVarRegisters_directive.isLocalsDirective) {
                            throw new SemanticException(this.input, registers_directive_returnVarRegisters_directive != null ? (CommonTree) registers_directive_returnVarRegisters_directive.start : null, "A .locals directive is not valid in %s method", str3);
                        }
                        throw new SemanticException(this.input, registers_directive_returnVarRegisters_directive != null ? (CommonTree) registers_directive_returnVarRegisters_directive.start : null, "A .registers directive is not valid in %s method", str3);
                    }
                    if (methodImplementation.getTryBlocks().size() > 0) {
                        throw new SemanticException(this.input, commonTree, "try/catch blocks cannot be present in %s method", str3);
                    }
                    if (!Iterables.isEmpty(methodImplementation.getDebugItems())) {
                        throw new SemanticException(this.input, commonTree, "debug directives cannot be present in %s method", str3);
                    }
                    methodImplementation = null;
                } else {
                    if (z2) {
                        throw new SemanticException(this.input, commonTree, "An abstract method cannot have any instructions", new Object[0]);
                    }
                    if (z) {
                        throw new SemanticException(this.input, commonTree, "A native method cannot have any instructions", new Object[0]);
                    }
                    if ((registers_directive_returnVarRegisters_directive != null ? (CommonTree) registers_directive_returnVarRegisters_directive.start : null) == null) {
                        throw new SemanticException(this.input, commonTree, "A .registers or .locals directive must be present for a non-abstract/non-final method", new Object[0]);
                    }
                    if (this.method_stack.peek().totalMethodRegisters < this.method_stack.peek().methodParameterRegisters) {
                        throw new SemanticException(this.input, registers_directive_returnVarRegisters_directive != null ? (CommonTree) registers_directive_returnVarRegisters_directive.start : null, "This method requires at least " + Integer.toString(this.method_stack.peek().methodParameterRegisters) + " registers, for the method parameters", new Object[0]);
                    }
                }
                DexBuilder dexBuilder = this.dexBuilder;
                String str4 = this.classType;
                if (method_name_and_prototype_returnVarMethod_name_and_prototype != null) {
                    method_name_and_prototype_return method_name_and_prototype_returnVar3 = method_name_and_prototype_returnVarMethod_name_and_prototype;
                    str = method_name_and_prototype_returnVarMethod_name_and_prototype.f1040name;
                } else {
                    str = null;
                }
                if (method_name_and_prototype_returnVarMethod_name_and_prototype != null) {
                    method_name_and_prototype_return method_name_and_prototype_returnVar4 = method_name_and_prototype_returnVarMethod_name_and_prototype;
                    list3 = method_name_and_prototype_returnVarMethod_name_and_prototype.parameters;
                } else {
                    list3 = null;
                }
                if (method_name_and_prototype_returnVarMethod_name_and_prototype != null) {
                    method_name_and_prototype_return method_name_and_prototype_returnVar5 = method_name_and_prototype_returnVarMethod_name_and_prototype;
                    str2 = method_name_and_prototype_returnVarMethod_name_and_prototype.returnType;
                } else {
                    str2 = null;
                }
                builderMethodInternMethod = dexBuilder.internMethod(str4, str, list3, str2, i, setAnnotations, set, methodImplementation);
            } catch (RecognitionException e) {
                reportError(e);
                recover(this.input, e);
            }
            this.method_stack.pop();
            return builderMethodInternMethod;
        } catch (Throwable th) {
            this.method_stack.pop();
            throw th;
        }
    }

    public final ImmutableMethodProtoReference method_prototype() throws RecognitionException {
        try {
            match(this.input, 128, FOLLOW_I_METHOD_PROTOTYPE_in_method_prototype1207);
            match(this.input, 2, null);
            match(this.input, 129, FOLLOW_I_METHOD_RETURN_TYPE_in_method_prototype1210);
            match(this.input, 2, null);
            pushFollow(FOLLOW_type_descriptor_in_method_prototype1212);
            String strType_descriptor = type_descriptor();
            this.state._fsp--;
            match(this.input, 3, null);
            pushFollow(FOLLOW_method_type_list_in_method_prototype1215);
            List<String> listMethod_type_list = method_type_list();
            this.state._fsp--;
            match(this.input, 3, null);
            return new ImmutableMethodProtoReference(listMethod_type_list, strType_descriptor);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final method_name_and_prototype_return method_name_and_prototype() throws RecognitionException {
        method_name_and_prototype_return method_name_and_prototype_returnVar = new method_name_and_prototype_return();
        method_name_and_prototype_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_method_name_and_prototype1233);
            pushFollow(FOLLOW_method_prototype_in_method_name_and_prototype1235);
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype();
            this.state._fsp--;
            method_name_and_prototype_returnVar.f1040name = commonTree != null ? commonTree.getText() : null;
            method_name_and_prototype_returnVar.parameters = ListUtil.newArrayList();
            int i = 0;
            for (CharSequence charSequence : immutableMethodProtoReferenceMethod_prototype.getParameterTypes()) {
                int i2 = i + 1;
                method_name_and_prototype_returnVar.parameters.add(new SmaliMethodParameter(i, charSequence.toString()));
                char cCharAt = charSequence.charAt(0);
                i = (cCharAt == 'D' || cCharAt == 'J') ? i + 2 : i2;
            }
            method_name_and_prototype_returnVar.returnType = immutableMethodProtoReferenceMethod_prototype.getReturnType();
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return method_name_and_prototype_returnVar;
    }

    public final List<String> method_type_list() throws RecognitionException {
        String str;
        ArrayList arrayListNewArrayList = ListUtil.newArrayList();
        while (true) {
            try {
                int iLA = this.input.LA(1);
                if (iLA != 8 && iLA != 16 && iLA != 202) {
                    break;
                }
                pushFollow(FOLLOW_nonvoid_type_descriptor_in_method_type_list1269);
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
                this.state._fsp--;
                if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                    nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
                    str = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
                } else {
                    str = null;
                }
                arrayListNewArrayList.add(str);
            } catch (RecognitionException e) {
                reportError(e);
                recover(this.input, e);
            }
        }
        return arrayListNewArrayList;
    }

    public final ImmutableCallSiteReference call_site_reference() throws RecognitionException {
        try {
            match(this.input, 105, FOLLOW_I_CALL_SITE_REFERENCE_in_call_site_reference1300);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_call_site_reference1304);
            pushFollow(FOLLOW_string_literal_in_call_site_reference1308);
            String strString_literal = string_literal();
            this.state._fsp--;
            pushFollow(FOLLOW_method_prototype_in_call_site_reference1310);
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype();
            this.state._fsp--;
            pushFollow(FOLLOW_call_site_extra_arguments_in_call_site_reference1320);
            List<ImmutableEncodedValue> listCall_site_extra_arguments = call_site_extra_arguments();
            this.state._fsp--;
            pushFollow(FOLLOW_method_reference_in_call_site_reference1322);
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            return new ImmutableCallSiteReference(commonTree != null ? commonTree.getText() : null, new ImmutableMethodHandleReference(4, (ImmutableReference) immutableMethodReferenceMethod_reference), strString_literal, immutableMethodProtoReferenceMethod_prototype, (List<? extends ImmutableEncodedValue>) listCall_site_extra_arguments);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final method_handle_type_return method_handle_type() throws RecognitionException {
        method_handle_type_return method_handle_type_returnVar = new method_handle_type_return();
        method_handle_type_returnVar.start = this.input.LT(1);
        try {
            if (this.input.LA(1) != 82 && (this.input.LA(1) < 192 || this.input.LA(1) > 193)) {
                throw new MismatchedSetException(null, this.input);
            }
            this.input.consume();
            this.state.errorRecovery = false;
            method_handle_type_returnVar.methodHandleType = MethodHandleType.getMethodHandleType(this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(method_handle_type_returnVar.start), this.input.getTreeAdaptor().getTokenStopIndex(method_handle_type_returnVar.start)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return method_handle_type_returnVar;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x005b  */
    public final ImmutableMethodHandleReference method_handle_reference() throws RecognitionException {
        char c;
        field_reference_return field_reference_returnVarField_reference;
        ImmutableReference immutableReference;
        try {
            pushFollow(FOLLOW_method_handle_type_in_method_handle_reference1367);
            method_handle_type_return method_handle_type_returnVarMethod_handle_type = method_handle_type();
            this.state._fsp--;
            int iLA = this.input.LA(1);
            int i = 0;
            if (iLA == 8) {
                int iLA2 = this.input.LA(2);
                if (iLA2 == 202) {
                    if (this.input.LA(3) == 208) {
                        int iLA3 = this.input.LA(4);
                        if (iLA3 == 8 || iLA3 == 16 || iLA3 == 202) {
                            c = 1;
                        } else if (iLA3 == 128) {
                            c = 2;
                        } else {
                            int iMark = this.input.mark();
                            while (i < 3) {
                                try {
                                    this.input.consume();
                                    i++;
                                } catch (Throwable th) {
                                    this.input.rewind(iMark);
                                    throw th;
                                }
                            }
                            throw new NoViableAltException("", 19, 3, this.input);
                        }
                    } else {
                        int iMark2 = this.input.mark();
                        while (i < 2) {
                            try {
                                this.input.consume();
                                i++;
                            } catch (Throwable th2) {
                                this.input.rewind(iMark2);
                                throw th2;
                            }
                        }
                        throw new NoViableAltException("", 19, 4, this.input);
                    }
                    throw th;
                }
                if (iLA2 == 16) {
                    if (this.input.LA(3) == 208) {
                        int iLA4 = this.input.LA(4);
                        if (iLA4 == 8 || iLA4 == 16 || iLA4 == 202) {
                            c = 1;
                        } else if (iLA4 == 128) {
                            c = 2;
                        } else {
                            int iMark3 = this.input.mark();
                            while (i < 3) {
                                try {
                                    this.input.consume();
                                    i++;
                                } catch (Throwable th3) {
                                    this.input.rewind(iMark3);
                                    throw th3;
                                }
                            }
                            throw new NoViableAltException("", 19, 3, this.input);
                        }
                    } else {
                        int iMark4 = this.input.mark();
                        while (i < 2) {
                            try {
                                this.input.consume();
                                i++;
                            } catch (Throwable th4) {
                                this.input.rewind(iMark4);
                                throw th4;
                            }
                        }
                        throw new NoViableAltException("", 19, 5, this.input);
                    }
                } else {
                    int iMark5 = this.input.mark();
                    try {
                        this.input.consume();
                        throw new NoViableAltException("", 19, 2, this.input);
                    } catch (Throwable th5) {
                        this.input.rewind(iMark5);
                        throw th5;
                    }
                }
                throw th;
            }
            if (iLA == 16) {
                if (this.input.LA(2) == 208) {
                    int iLA5 = this.input.LA(3);
                    if (iLA5 == 8 || iLA5 == 16 || iLA5 == 202) {
                        c = 1;
                    } else if (iLA5 == 128) {
                        c = 2;
                    } else {
                        int iMark6 = this.input.mark();
                        while (i < 2) {
                            try {
                                this.input.consume();
                                i++;
                            } catch (Throwable th6) {
                                this.input.rewind(iMark6);
                                throw th6;
                            }
                        }
                        throw new NoViableAltException("", 19, 3, this.input);
                    }
                } else {
                    int iMark7 = this.input.mark();
                    try {
                        this.input.consume();
                        throw new NoViableAltException("", 19, 1, this.input);
                    } catch (Throwable th7) {
                        this.input.rewind(iMark7);
                        throw th7;
                    }
                }
                throw th;
            }
            if (iLA == 208) {
                int iLA6 = this.input.LA(2);
                if (iLA6 == 8 || iLA6 == 16 || iLA6 == 202) {
                    c = 1;
                } else {
                    if (iLA6 != 128) {
                        int iMark8 = this.input.mark();
                        try {
                            this.input.consume();
                            throw new NoViableAltException("", 19, 3, this.input);
                        } catch (Throwable th8) {
                            this.input.rewind(iMark8);
                            throw th8;
                        }
                    }
                    c = 2;
                }
            } else {
                throw new NoViableAltException("", 19, 0, this.input);
            }
            if (c == 1) {
                pushFollow(FOLLOW_field_reference_in_method_handle_reference1370);
                field_reference_returnVarField_reference = field_reference();
                this.state._fsp--;
                immutableReference = null;
            } else if (c != 2) {
                field_reference_returnVarField_reference = null;
                immutableReference = null;
            } else {
                pushFollow(FOLLOW_method_reference_in_method_handle_reference1374);
                ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference();
                this.state._fsp--;
                immutableReference = immutableMethodReferenceMethod_reference;
                field_reference_returnVarField_reference = null;
            }
            if ((field_reference_returnVarField_reference != null ? this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(field_reference_returnVarField_reference.start), this.input.getTreeAdaptor().getTokenStopIndex(field_reference_returnVarField_reference.start)) : null) != null) {
                immutableReference = field_reference_returnVarField_reference != null ? field_reference_returnVarField_reference.fieldReference : null;
            }
            if (method_handle_type_returnVarMethod_handle_type != null) {
                method_handle_type_return method_handle_type_returnVar = method_handle_type_returnVarMethod_handle_type;
                i = method_handle_type_returnVarMethod_handle_type.methodHandleType;
            }
            return new ImmutableMethodHandleReference(i, immutableReference);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final ImmutableMethodHandleReference method_handle_literal() throws RecognitionException {
        try {
            match(this.input, 114, FOLLOW_I_ENCODED_METHOD_HANDLE_in_method_handle_literal1391);
            match(this.input, 2, null);
            pushFollow(FOLLOW_method_handle_reference_in_method_handle_literal1393);
            ImmutableMethodHandleReference immutableMethodHandleReferenceMethod_handle_reference = method_handle_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            return immutableMethodHandleReferenceMethod_handle_reference;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final ImmutableMethodReference method_reference() throws RecognitionException {
        reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor;
        String str;
        try {
            int iLA = this.input.LA(1);
            if (iLA == 8 || iLA == 16) {
                pushFollow(FOLLOW_reference_type_descriptor_in_method_reference1409);
                reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor();
                this.state._fsp--;
            } else {
                reference_type_descriptor_returnVarReference_type_descriptor = null;
            }
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_method_reference1412);
            pushFollow(FOLLOW_method_prototype_in_method_reference1414);
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype();
            this.state._fsp--;
            if ((reference_type_descriptor_returnVarReference_type_descriptor != null ? reference_type_descriptor_returnVarReference_type_descriptor.type : null) == null) {
                str = this.classType;
            } else {
                str = reference_type_descriptor_returnVarReference_type_descriptor != null ? reference_type_descriptor_returnVarReference_type_descriptor.type : null;
            }
            return new ImmutableMethodReference(str, commonTree != null ? commonTree.getText() : null, immutableMethodProtoReferenceMethod_prototype.getParameterTypes(), immutableMethodProtoReferenceMethod_prototype.getReturnType());
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final field_reference_return field_reference() throws RecognitionException {
        reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor;
        String str;
        field_reference_return field_reference_returnVar = new field_reference_return();
        field_reference_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            String str2 = null;
            if (iLA == 8 || iLA == 16) {
                pushFollow(FOLLOW_reference_type_descriptor_in_field_reference1431);
                reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor();
                this.state._fsp--;
            } else {
                reference_type_descriptor_returnVarReference_type_descriptor = null;
            }
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_field_reference1434);
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_field_reference1436);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            if ((reference_type_descriptor_returnVarReference_type_descriptor != null ? reference_type_descriptor_returnVarReference_type_descriptor.type : null) == null) {
                str = this.classType;
            } else {
                str = reference_type_descriptor_returnVarReference_type_descriptor != null ? reference_type_descriptor_returnVarReference_type_descriptor.type : null;
            }
            String text = commonTree != null ? commonTree.getText() : null;
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
                str2 = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
            }
            field_reference_returnVar.fieldReference = new ImmutableFieldReference(str, text, str2);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return field_reference_returnVar;
    }

    public final registers_directive_return registers_directive() throws RecognitionException {
        char c;
        registers_directive_return registers_directive_returnVar = new registers_directive_return();
        registers_directive_returnVar.start = this.input.LT(1);
        try {
            registers_directive_returnVar.registers = 0;
            int iLA = this.input.LA(1);
            if (iLA == 137) {
                c = 1;
            } else {
                if (iLA != 125) {
                    throw new NoViableAltException("", 22, 0, this.input);
                }
                c = 2;
            }
            if (c == 1) {
                match(this.input, 137, FOLLOW_I_REGISTERS_in_registers_directive1462);
                registers_directive_returnVar.isLocalsDirective = false;
            } else if (c == 2) {
                match(this.input, 125, FOLLOW_I_LOCALS_in_registers_directive1474);
                registers_directive_returnVar.isLocalsDirective = true;
            }
            match(this.input, 2, null);
            pushFollow(FOLLOW_short_integral_literal_in_registers_directive1492);
            short sShort_integral_literal = short_integral_literal();
            this.state._fsp--;
            registers_directive_returnVar.registers = sShort_integral_literal & 65535;
            match(this.input, 3, null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return registers_directive_returnVar;
    }

    public final void label_def() throws RecognitionException {
        try {
            match(this.input, 122, FOLLOW_I_LABEL_in_label_def1512);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_label_def1514);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addLabel(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final List<BuilderTryBlock> catches() throws RecognitionException {
        ArrayList arrayListNewArrayList = ListUtil.newArrayList();
        try {
            match(this.input, 108, FOLLOW_I_CATCHES_in_catches1540);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (this.input.LA(1) == 106) {
                    pushFollow(FOLLOW_catch_directive_in_catches1542);
                    catch_directive();
                    this.state._fsp--;
                }
                while (this.input.LA(1) == 107) {
                    pushFollow(FOLLOW_catchall_directive_in_catches1545);
                    catchall_directive();
                    this.state._fsp--;
                }
                match(this.input, 3, null);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return arrayListNewArrayList;
    }

    public final void catch_directive() throws RecognitionException {
        try {
            match(this.input, 106, FOLLOW_I_CATCH_in_catch_directive1558);
            String str = null;
            match(this.input, 2, null);
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_catch_directive1560);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            pushFollow(FOLLOW_label_ref_in_catch_directive1564);
            Label labelLabel_ref = label_ref();
            this.state._fsp--;
            pushFollow(FOLLOW_label_ref_in_catch_directive1568);
            Label labelLabel_ref2 = label_ref();
            this.state._fsp--;
            pushFollow(FOLLOW_label_ref_in_catch_directive1572);
            Label labelLabel_ref3 = label_ref();
            this.state._fsp--;
            match(this.input, 3, null);
            MethodImplementationBuilder methodImplementationBuilder = this.method_stack.peek().methodBuilder;
            DexBuilder dexBuilder = this.dexBuilder;
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
                str = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
            }
            methodImplementationBuilder.addCatch(dexBuilder.internTypeReference(str), labelLabel_ref, labelLabel_ref2, labelLabel_ref3);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void catchall_directive() throws RecognitionException {
        try {
            match(this.input, 107, FOLLOW_I_CATCHALL_in_catchall_directive1588);
            match(this.input, 2, null);
            pushFollow(FOLLOW_label_ref_in_catchall_directive1592);
            Label labelLabel_ref = label_ref();
            this.state._fsp--;
            pushFollow(FOLLOW_label_ref_in_catchall_directive1596);
            Label labelLabel_ref2 = label_ref();
            this.state._fsp--;
            pushFollow(FOLLOW_label_ref_in_catchall_directive1600);
            Label labelLabel_ref3 = label_ref();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addCatch(labelLabel_ref, labelLabel_ref2, labelLabel_ref3);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void parameters(List<SmaliMethodParameter> list) throws RecognitionException {
        try {
            match(this.input, 134, FOLLOW_I_PARAMETERS_in_parameters1617);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (this.input.LA(1) == 133) {
                    pushFollow(FOLLOW_parameter_in_parameters1620);
                    parameter(list);
                    this.state._fsp--;
                }
                match(this.input, 3, null);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void parameter(List<SmaliMethodParameter> list) throws RecognitionException {
        String strString_literal;
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 133, FOLLOW_I_PARAMETER_in_parameter1636);
            match(this.input, 2, null);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_parameter1638);
            if (this.input.LA(1) == 211) {
                pushFollow(FOLLOW_string_literal_in_parameter1640);
                strString_literal = string_literal();
                this.state._fsp--;
            } else {
                strString_literal = null;
            }
            pushFollow(FOLLOW_annotations_in_parameter1643);
            Set<Annotation> setAnnotations = annotations();
            this.state._fsp--;
            match(this.input, 3, null);
            int register_short = parseRegister_short(commonTree2 != null ? commonTree2.getText() : null);
            int i = this.method_stack.peek().totalMethodRegisters;
            int i2 = this.method_stack.peek().methodParameterRegisters;
            if (register_short >= i) {
                throw new SemanticException(this.input, commonTree, "Register %s is larger than the maximum register v%d for this method", commonTree2 != null ? commonTree2.getText() : null, Integer.valueOf(i - 1));
            }
            final int i3 = (register_short - (i - i2)) - (1 ^ (this.method_stack.peek().isStatic ? 1 : 0));
            if (i3 < 0) {
                throw new SemanticException(this.input, commonTree, "Register %s is not a parameter register.", commonTree2 != null ? commonTree2.getText() : null);
            }
            int iLinearSearch = LinearSearch.linearSearch(list, SmaliMethodParameter.COMPARATOR, new WithRegister() { // from class: org.jf.smali.smaliTreeWalker.1
                @Override // org.jf.smali.WithRegister
                public int getRegister() {
                    return i3;
                }
            }, i3);
            if (iLinearSearch < 0) {
                throw new SemanticException(this.input, commonTree, "Register %s is the second half of a wide parameter.", commonTree2 != null ? commonTree2.getText() : null);
            }
            SmaliMethodParameter smaliMethodParameter = list.get(iLinearSearch);
            smaliMethodParameter.f1039name = strString_literal;
            if (setAnnotations == null || setAnnotations.size() <= 0) {
                return;
            }
            smaliMethodParameter.annotations = setAnnotations;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void debug_directive() throws RecognitionException {
        char c;
        try {
            int iLA = this.input.LA(1);
            if (iLA == 115) {
                c = 3;
            } else if (iLA == 116) {
                c = 6;
            } else if (iLA == 123) {
                c = 1;
            } else if (iLA == 124) {
                c = 2;
            } else if (iLA == 136) {
                c = 5;
            } else if (iLA == 140) {
                c = 4;
            } else {
                if (iLA != 141) {
                    throw new NoViableAltException("", 27, 0, this.input);
                }
                c = 7;
            }
            switch (c) {
                case 1:
                    pushFollow(FOLLOW_line_in_debug_directive1660);
                    line();
                    this.state._fsp--;
                    return;
                case 2:
                    pushFollow(FOLLOW_local_in_debug_directive1666);
                    local();
                    this.state._fsp--;
                    return;
                case 3:
                    pushFollow(FOLLOW_end_local_in_debug_directive1672);
                    end_local();
                    this.state._fsp--;
                    return;
                case 4:
                    pushFollow(FOLLOW_restart_local_in_debug_directive1678);
                    restart_local();
                    this.state._fsp--;
                    return;
                case 5:
                    pushFollow(FOLLOW_prologue_in_debug_directive1684);
                    prologue();
                    this.state._fsp--;
                    return;
                case 6:
                    pushFollow(FOLLOW_epilogue_in_debug_directive1690);
                    epilogue();
                    this.state._fsp--;
                    return;
                case 7:
                    pushFollow(FOLLOW_source_in_debug_directive1696);
                    source();
                    this.state._fsp--;
                    return;
                default:
                    return;
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void line() throws RecognitionException {
        try {
            match(this.input, 123, FOLLOW_I_LINE_in_line1707);
            match(this.input, 2, null);
            pushFollow(FOLLOW_integral_literal_in_line1709);
            int iIntegral_literal = integral_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addLineNumber(iIntegral_literal);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0071 A[Catch: all -> 0x00e0, RecognitionException -> 0x00e2, TryCatch #2 {RecognitionException -> 0x00e2, blocks: (B:2:0x0000, B:31:0x009b, B:33:0x00a3, B:35:0x00a9, B:37:0x00c1, B:38:0x00c5, B:7:0x0031, B:18:0x005c, B:27:0x0081, B:29:0x0089, B:26:0x0071, B:15:0x0043, B:16:0x0054, B:39:0x00d3, B:40:0x00df), top: B:46:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0089 A[Catch: all -> 0x00e0, RecognitionException -> 0x00e2, TryCatch #2 {RecognitionException -> 0x00e2, blocks: (B:2:0x0000, B:31:0x009b, B:33:0x00a3, B:35:0x00a9, B:37:0x00c1, B:38:0x00c5, B:7:0x0031, B:18:0x005c, B:27:0x0081, B:29:0x0089, B:26:0x0071, B:15:0x0043, B:16:0x0054, B:39:0x00d3, B:40:0x00df), top: B:46:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x009a  */
    public final void local() throws RecognitionException {
        char c;
        String strString_literal;
        int iLA;
        nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
        String strString_literal2;
        try {
            match(this.input, 124, FOLLOW_I_LOCAL_in_local1727);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_local1729);
            int iLA2 = this.input.LA(1);
            if (iLA2 == 195 || iLA2 == 211) {
                int iLA3 = this.input.LA(1);
                if (iLA3 == 195) {
                    c = 1;
                } else {
                    if (iLA3 != 211) {
                        throw new NoViableAltException("", 28, 0, this.input);
                    }
                    c = 2;
                }
                if (c == 1) {
                    match(this.input, 195, FOLLOW_NULL_LITERAL_in_local1733);
                } else {
                    if (c == 2) {
                        pushFollow(FOLLOW_string_literal_in_local1739);
                        strString_literal = string_literal();
                        this.state._fsp--;
                    }
                    iLA = this.input.LA(1);
                    if (iLA != 8 || iLA == 16 || iLA == 202) {
                        pushFollow(FOLLOW_nonvoid_type_descriptor_in_local1742);
                        nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
                        this.state._fsp--;
                    } else {
                        nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = null;
                    }
                    if (this.input.LA(1) == 211) {
                        pushFollow(FOLLOW_string_literal_in_local1747);
                        strString_literal2 = string_literal();
                        this.state._fsp--;
                    } else {
                        strString_literal2 = null;
                    }
                }
                strString_literal = null;
                iLA = this.input.LA(1);
                if (iLA != 8) {
                    pushFollow(FOLLOW_nonvoid_type_descriptor_in_local1742);
                    nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
                    this.state._fsp--;
                } else {
                    pushFollow(FOLLOW_nonvoid_type_descriptor_in_local1742);
                    nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
                    this.state._fsp--;
                }
                if (this.input.LA(1) == 211) {
                    pushFollow(FOLLOW_string_literal_in_local1747);
                    strString_literal2 = string_literal();
                    this.state._fsp--;
                } else {
                    strString_literal2 = null;
                }
            } else {
                strString_literal = null;
                nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = null;
                strString_literal2 = null;
            }
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addStartLocal(parseRegister_short(commonTree != null ? commonTree.getText() : null), this.dexBuilder.internNullableStringReference(strString_literal), this.dexBuilder.internNullableTypeReference(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null ? nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type : null), this.dexBuilder.internNullableStringReference(strString_literal2));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void end_local() throws RecognitionException {
        try {
            match(this.input, 115, FOLLOW_I_END_LOCAL_in_end_local1768);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_end_local1770);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addEndLocal(parseRegister_short(commonTree != null ? commonTree.getText() : null));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void restart_local() throws RecognitionException {
        try {
            match(this.input, 140, FOLLOW_I_RESTART_LOCAL_in_restart_local1788);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_restart_local1790);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addRestartLocal(parseRegister_short(commonTree != null ? commonTree.getText() : null));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void prologue() throws RecognitionException {
        try {
            match(this.input, 136, FOLLOW_I_PROLOGUE_in_prologue1807);
            this.method_stack.peek().methodBuilder.addPrologue();
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void epilogue() throws RecognitionException {
        try {
            match(this.input, 116, FOLLOW_I_EPILOGUE_in_epilogue1823);
            this.method_stack.peek().methodBuilder.addEpilogue();
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void source() throws RecognitionException {
        String strString_literal;
        try {
            match(this.input, 141, FOLLOW_I_SOURCE_in_source1840);
            String str = null;
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                if (this.input.LA(1) == 211) {
                    pushFollow(FOLLOW_string_literal_in_source1842);
                    strString_literal = string_literal();
                    this.state._fsp--;
                } else {
                    strString_literal = null;
                }
                match(this.input, 3, null);
                str = strString_literal;
            }
            this.method_stack.peek().methodBuilder.addSetSourceFile(this.dexBuilder.internNullableStringReference(str));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final List<ImmutableEncodedValue> call_site_extra_arguments() throws RecognitionException {
        ArrayList arrayListNewArrayList;
        RecognitionException e;
        try {
            arrayListNewArrayList = ListUtil.newArrayList();
            try {
                match(this.input, 104, FOLLOW_I_CALL_SITE_EXTRA_ARGUMENTS_in_call_site_extra_arguments1868);
                if (this.input.LA(1) == 2) {
                    match(this.input, 2, null);
                    while (true) {
                        int iLA = this.input.LA(1);
                        if (iLA != 8 && ((iLA < 11 || iLA > 12) && ((iLA < 15 || iLA > 16) && iLA != 23 && iLA != 39 && iLA != 95 && ((iLA < 110 || iLA > 114) && iLA != 128 && iLA != 183 && iLA != 189 && iLA != 195 && iLA != 202 && iLA != 207 && iLA != 211 && iLA != 215)))) {
                            break;
                        }
                        pushFollow(FOLLOW_literal_in_call_site_extra_arguments1871);
                        ImmutableEncodedValue immutableEncodedValueLiteral = literal();
                        this.state._fsp--;
                        arrayListNewArrayList.add(immutableEncodedValueLiteral);
                    }
                    match(this.input, 3, null);
                }
            } catch (RecognitionException e2) {
                e = e2;
                reportError(e);
                recover(this.input, e);
            }
        } catch (RecognitionException e3) {
            arrayListNewArrayList = null;
            e = e3;
        }
        return arrayListNewArrayList;
    }

    public final void ordered_method_items() throws RecognitionException {
        char c;
        try {
            match(this.input, 130, FOLLOW_I_ORDERED_METHOD_ITEMS_in_ordered_method_items1887);
            if (this.input.LA(1) != 2) {
                return;
            }
            match(this.input, 2, null);
            while (true) {
                int iLA = this.input.LA(1);
                if (iLA != 115 && iLA != 116 && iLA != 136 && iLA != 140 && iLA != 141) {
                    switch (iLA) {
                        case 122:
                            c = 1;
                            break;
                        case 123:
                        case 124:
                            c = 3;
                            break;
                        default:
                            switch (iLA) {
                                case 143:
                                case 144:
                                case 145:
                                case 146:
                                case 147:
                                case 148:
                                case 149:
                                case 150:
                                case 151:
                                case 152:
                                case 153:
                                case 154:
                                case 155:
                                case 156:
                                case 157:
                                case 158:
                                case 159:
                                case 160:
                                case 161:
                                case 162:
                                case 163:
                                case 164:
                                case 165:
                                case 166:
                                case 167:
                                case 168:
                                case 169:
                                case 170:
                                case 171:
                                case 172:
                                case 173:
                                case 174:
                                case 175:
                                case 176:
                                case 177:
                                case 178:
                                case 179:
                                case 180:
                                case 181:
                                case 182:
                                    c = 2;
                                    break;
                                default:
                                    c = 4;
                                    break;
                            }
                            break;
                    }
                } else {
                    c = 3;
                }
                if (c == 1) {
                    pushFollow(FOLLOW_label_def_in_ordered_method_items1890);
                    label_def();
                    this.state._fsp--;
                } else if (c == 2) {
                    pushFollow(FOLLOW_instruction_in_ordered_method_items1894);
                    instruction();
                    this.state._fsp--;
                } else if (c == 3) {
                    pushFollow(FOLLOW_debug_directive_in_ordered_method_items1898);
                    debug_directive();
                    this.state._fsp--;
                } else {
                    match(this.input, 3, null);
                    return;
                }
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final Label label_ref() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_label_ref1914);
            return this.method_stack.peek().methodBuilder.getLabel(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final register_list_return register_list() throws RecognitionException {
        register_list_return register_list_returnVar = new register_list_return();
        register_list_returnVar.start = this.input.LT(1);
        register_list_returnVar.registers = new byte[5];
        register_list_returnVar.registerCount = (byte) 0;
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 138, FOLLOW_I_REGISTER_LIST_in_register_list1939);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (this.input.LA(1) == 204) {
                    CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_register_list1948);
                    if (register_list_returnVar.registerCount == 5) {
                        throw new SemanticException(this.input, commonTree, "A list of registers can only have a maximum of 5 registers. Use the <op>/range alternate opcode instead.", new Object[0]);
                    }
                    byte[] bArr = register_list_returnVar.registers;
                    byte b = register_list_returnVar.registerCount;
                    register_list_returnVar.registerCount = (byte) (b + 1);
                    bArr[b] = parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null);
                }
                match(this.input, 3, null);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return register_list_returnVar;
    }

    public final register_range_return register_range() throws RecognitionException {
        CommonTree commonTree;
        CommonTree commonTree2;
        register_range_return register_range_returnVar = new register_range_return();
        register_range_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree3 = (CommonTree) match(this.input, 139, FOLLOW_I_REGISTER_RANGE_in_register_range1973);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                if (this.input.LA(1) == 204) {
                    commonTree = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_register_range1978);
                    commonTree2 = this.input.LA(1) == 204 ? (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_register_range1982) : null;
                } else {
                    commonTree = null;
                    commonTree2 = null;
                }
                match(this.input, 3, null);
            } else {
                commonTree = null;
                commonTree2 = null;
            }
            if (commonTree == null) {
                register_range_returnVar.startRegister = 0;
                register_range_returnVar.endRegister = -1;
            } else {
                register_range_returnVar.startRegister = parseRegister_short(commonTree != null ? commonTree.getText() : null);
                if (commonTree2 == null) {
                    register_range_returnVar.endRegister = register_range_returnVar.startRegister;
                } else {
                    register_range_returnVar.endRegister = parseRegister_short(commonTree2 != null ? commonTree2.getText() : null);
                }
                if ((register_range_returnVar.endRegister - register_range_returnVar.startRegister) + 1 < 1) {
                    throw new SemanticException(this.input, commonTree3, "A register range must have the lower register listed first", new Object[0]);
                }
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return register_range_returnVar;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004b  */
    public final ImmutableReference verification_error_reference() throws RecognitionException {
        char c;
        try {
            int iLA = this.input.LA(1);
            int i = 0;
            if (iLA == 8) {
                int iLA2 = this.input.LA(2);
                if (iLA2 == 202) {
                    if (this.input.LA(3) == 208) {
                        int iLA3 = this.input.LA(4);
                        if (iLA3 == 8 || iLA3 == 16 || iLA3 == 202) {
                            c = 2;
                        } else if (iLA3 == 128) {
                            c = 3;
                        } else {
                            int iMark = this.input.mark();
                            while (i < 3) {
                                try {
                                    this.input.consume();
                                    i++;
                                } catch (Throwable th) {
                                    this.input.rewind(iMark);
                                    throw th;
                                }
                            }
                            throw new NoViableAltException("", 38, 3, this.input);
                        }
                    } else {
                        int iMark2 = this.input.mark();
                        while (i < 2) {
                            try {
                                this.input.consume();
                                i++;
                            } catch (Throwable th2) {
                                this.input.rewind(iMark2);
                                throw th2;
                            }
                        }
                        throw new NoViableAltException("", 38, 5, this.input);
                    }
                } else if (iLA2 == 16) {
                    if (this.input.LA(3) == 208) {
                        int iLA4 = this.input.LA(4);
                        if (iLA4 == 8 || iLA4 == 16 || iLA4 == 202) {
                            c = 2;
                        } else if (iLA4 == 128) {
                            c = 3;
                        } else {
                            int iMark3 = this.input.mark();
                            while (i < 3) {
                                try {
                                    this.input.consume();
                                    i++;
                                } catch (Throwable th3) {
                                    this.input.rewind(iMark3);
                                    throw th3;
                                }
                            }
                            throw new NoViableAltException("", 38, 3, this.input);
                        }
                    } else {
                        int iMark4 = this.input.mark();
                        while (i < 2) {
                            try {
                                this.input.consume();
                                i++;
                            } catch (Throwable th4) {
                                this.input.rewind(iMark4);
                                throw th4;
                            }
                        }
                        throw new NoViableAltException("", 38, 6, this.input);
                    }
                } else {
                    int iMark5 = this.input.mark();
                    try {
                        this.input.consume();
                        throw new NoViableAltException("", 38, 2, this.input);
                    } catch (Throwable th5) {
                        this.input.rewind(iMark5);
                        throw th5;
                    }
                }
            } else if (iLA == 16) {
                int iLA5 = this.input.LA(2);
                if (iLA5 == 3) {
                    c = 1;
                } else if (iLA5 == 208) {
                    int iLA6 = this.input.LA(3);
                    if (iLA6 == 8 || iLA6 == 16 || iLA6 == 202) {
                        c = 2;
                    } else if (iLA6 == 128) {
                        c = 3;
                    } else {
                        int iMark6 = this.input.mark();
                        while (i < 2) {
                            try {
                                this.input.consume();
                                i++;
                            } catch (Throwable th6) {
                                this.input.rewind(iMark6);
                                throw th6;
                            }
                        }
                        throw new NoViableAltException("", 38, 3, this.input);
                    }
                } else {
                    int iMark7 = this.input.mark();
                    try {
                        this.input.consume();
                        throw new NoViableAltException("", 38, 1, this.input);
                    } catch (Throwable th7) {
                        this.input.rewind(iMark7);
                        throw th7;
                    }
                }
            } else if (iLA == 208) {
                int iLA7 = this.input.LA(2);
                if (iLA7 == 8 || iLA7 == 16 || iLA7 == 202) {
                    c = 2;
                } else {
                    if (iLA7 != 128) {
                        int iMark8 = this.input.mark();
                        try {
                            this.input.consume();
                            throw new NoViableAltException("", 38, 3, this.input);
                        } catch (Throwable th8) {
                            this.input.rewind(iMark8);
                            throw th8;
                        }
                    }
                    c = 3;
                }
            } else {
                throw new NoViableAltException("", 38, 0, this.input);
            }
            if (c == 1) {
                CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference2005);
                return new ImmutableTypeReference(commonTree != null ? commonTree.getText() : null);
            }
            if (c != 2) {
                if (c != 3) {
                    return null;
                }
                pushFollow(FOLLOW_method_reference_in_verification_error_reference2025);
                ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference();
                this.state._fsp--;
                return immutableMethodReferenceMethod_reference;
            }
            pushFollow(FOLLOW_field_reference_in_verification_error_reference2015);
            field_reference_return field_reference_returnVarField_reference = field_reference();
            this.state._fsp--;
            if (field_reference_returnVarField_reference == null) {
                return null;
            }
            field_reference_return field_reference_returnVar = field_reference_returnVarField_reference;
            return field_reference_returnVarField_reference.fieldReference;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final int verification_error_type() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 214, FOLLOW_VERIFICATION_ERROR_TYPE_in_verification_error_type2042);
            return VerificationError.getVerificationError(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return 0;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:46:0x009a  */
    /* JADX WARN: Code duplicated, block: B:47:0x009c A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:48:0x00ad A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00be A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:50:0x00cf A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00e0 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:52:0x00f1 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:53:0x0102 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:54:0x0113 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:55:0x0124 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:56:0x0135 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:57:0x0146 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:58:0x0157 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:59:0x0168 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:60:0x0179 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:61:0x018a A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:62:0x019b A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:63:0x01ac A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:64:0x01bd A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:65:0x01ce A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:66:0x01df A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:67:0x01f0 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:68:0x0201 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:69:0x0212 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:70:0x0223 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:71:0x0234 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:72:0x0245 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:73:0x0256 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:74:0x0267 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:75:0x0278 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:76:0x0289 A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:77:0x029a A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:78:0x02ab A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:79:0x02bc A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:80:0x02cd A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:81:0x02de A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:82:0x02ef A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:83:0x02ff A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:84:0x030f A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:85:0x031f A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    /* JADX WARN: Code duplicated, block: B:86:0x032f A[Catch: all -> 0x0347, Exception -> 0x0349, TryCatch #2 {Exception -> 0x0349, blocks: (B:3:0x000f, B:4:0x0017, B:5:0x001a, B:87:0x033f, B:88:0x0346, B:45:0x0097, B:47:0x009c, B:48:0x00ad, B:49:0x00be, B:50:0x00cf, B:51:0x00e0, B:52:0x00f1, B:53:0x0102, B:54:0x0113, B:55:0x0124, B:56:0x0135, B:57:0x0146, B:58:0x0157, B:59:0x0168, B:60:0x0179, B:61:0x018a, B:62:0x019b, B:63:0x01ac, B:64:0x01bd, B:65:0x01ce, B:66:0x01df, B:67:0x01f0, B:68:0x0201, B:69:0x0212, B:70:0x0223, B:71:0x0234, B:72:0x0245, B:73:0x0256, B:74:0x0267, B:75:0x0278, B:76:0x0289, B:77:0x029a, B:78:0x02ab, B:79:0x02bc, B:80:0x02cd, B:81:0x02de, B:82:0x02ef, B:83:0x02ff, B:84:0x030f, B:85:0x031f, B:86:0x032f), top: B:94:0x000f }] */
    public final instruction_return instruction() throws RecognitionException {
        instruction_return instruction_returnVar = new instruction_return();
        instruction_returnVar.start = this.input.LT(1);
        try {
            char c = '\'';
            switch (this.input.LA(1)) {
                case 143:
                    c = Typography.amp;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 144:
                    c = 1;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 145:
                    c = 2;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 146:
                    c = 3;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 147:
                    c = 4;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 148:
                    c = 5;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 149:
                    c = 6;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 150:
                    c = 7;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 151:
                    c = '\b';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 152:
                    c = '\t';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 153:
                    c = '\n';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 154:
                    c = 11;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 155:
                    c = '\f';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 156:
                    c = '\r';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 157:
                    c = 14;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 158:
                    c = 15;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 159:
                    c = 16;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 160:
                    c = 17;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 161:
                    c = 18;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 162:
                    c = 19;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 163:
                    c = 20;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 164:
                    c = 21;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 165:
                    c = 22;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 166:
                    c = 23;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 167:
                    c = 24;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 168:
                    c = 25;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 169:
                    c = 26;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 170:
                    c = 27;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 171:
                    c = 28;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 172:
                    c = 29;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 173:
                    c = 30;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 174:
                    c = 31;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 175:
                    c = StringUtil.SPACE;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 176:
                    c = '!';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 177:
                    c = '\"';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 178:
                    c = '#';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 179:
                    c = '$';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 180:
                    c = MangleConstant.PLATFORM_FUNCTION_MARKER;
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 181:
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                case 182:
                    c = '(';
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_insn_format10t_in_instruction2056);
                            insn_format10t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 2:
                            pushFollow(FOLLOW_insn_format10x_in_instruction2062);
                            insn_format10x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 3:
                            pushFollow(FOLLOW_insn_format11n_in_instruction2068);
                            insn_format11n();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 4:
                            pushFollow(FOLLOW_insn_format11x_in_instruction2074);
                            insn_format11x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 5:
                            pushFollow(FOLLOW_insn_format12x_in_instruction2080);
                            insn_format12x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 6:
                            pushFollow(FOLLOW_insn_format20bc_in_instruction2086);
                            insn_format20bc();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 7:
                            pushFollow(FOLLOW_insn_format20t_in_instruction2092);
                            insn_format20t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\b':
                            pushFollow(FOLLOW_insn_format21c_field_in_instruction2098);
                            insn_format21c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\t':
                            pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction2104);
                            insn_format21c_method_handle();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\n':
                            pushFollow(FOLLOW_insn_format21c_method_type_in_instruction2110);
                            insn_format21c_method_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 11:
                            pushFollow(FOLLOW_insn_format21c_string_in_instruction2116);
                            insn_format21c_string();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\f':
                            pushFollow(FOLLOW_insn_format21c_type_in_instruction2122);
                            insn_format21c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\r':
                            pushFollow(FOLLOW_insn_format21ih_in_instruction2128);
                            insn_format21ih();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 14:
                            pushFollow(FOLLOW_insn_format21lh_in_instruction2134);
                            insn_format21lh();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 15:
                            pushFollow(FOLLOW_insn_format21s_in_instruction2140);
                            insn_format21s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 16:
                            pushFollow(FOLLOW_insn_format21t_in_instruction2146);
                            insn_format21t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 17:
                            pushFollow(FOLLOW_insn_format22b_in_instruction2152);
                            insn_format22b();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 18:
                            pushFollow(FOLLOW_insn_format22c_field_in_instruction2158);
                            insn_format22c_field();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 19:
                            pushFollow(FOLLOW_insn_format22c_type_in_instruction2164);
                            insn_format22c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 20:
                            pushFollow(FOLLOW_insn_format22s_in_instruction2170);
                            insn_format22s();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 21:
                            pushFollow(FOLLOW_insn_format22t_in_instruction2176);
                            insn_format22t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 22:
                            pushFollow(FOLLOW_insn_format22x_in_instruction2182);
                            insn_format22x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 23:
                            pushFollow(FOLLOW_insn_format23x_in_instruction2188);
                            insn_format23x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 24:
                            pushFollow(FOLLOW_insn_format30t_in_instruction2194);
                            insn_format30t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 25:
                            pushFollow(FOLLOW_insn_format31c_in_instruction2200);
                            insn_format31c();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 26:
                            pushFollow(FOLLOW_insn_format31i_in_instruction2206);
                            insn_format31i();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 27:
                            pushFollow(FOLLOW_insn_format31t_in_instruction2212);
                            insn_format31t();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 28:
                            pushFollow(FOLLOW_insn_format32x_in_instruction2218);
                            insn_format32x();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 29:
                            pushFollow(FOLLOW_insn_format35c_call_site_in_instruction2224);
                            insn_format35c_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 30:
                            pushFollow(FOLLOW_insn_format35c_method_in_instruction2230);
                            insn_format35c_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case 31:
                            pushFollow(FOLLOW_insn_format35c_type_in_instruction2236);
                            insn_format35c_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case ' ':
                            pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction2242);
                            insn_format3rc_call_site();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '!':
                            pushFollow(FOLLOW_insn_format3rc_method_in_instruction2248);
                            insn_format3rc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\"':
                            pushFollow(FOLLOW_insn_format3rc_type_in_instruction2254);
                            insn_format3rc_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '#':
                            pushFollow(FOLLOW_insn_format45cc_method_in_instruction2260);
                            insn_format45cc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '$':
                            pushFollow(FOLLOW_insn_format4rcc_method_in_instruction2266);
                            insn_format4rcc_method();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '%':
                            pushFollow(FOLLOW_insn_format51l_type_in_instruction2272);
                            insn_format51l_type();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '&':
                            pushFollow(FOLLOW_insn_array_data_directive_in_instruction2278);
                            insn_array_data_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '\'':
                            pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction2284);
                            insn_packed_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        case '(':
                            pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2290);
                            insn_sparse_switch_directive();
                            this.state._fsp--;
                            return instruction_returnVar;
                        default:
                            return instruction_returnVar;
                    }
                default:
                    throw new NoViableAltException("", 39, 0, this.input);
            }
        } catch (Exception e) {
            reportError(new SemanticException(this.input, (CommonTree) instruction_returnVar.start, e.getMessage(), new Object[0]));
            recover(this.input, null);
        }
    }

    public final void insn_format10t() throws RecognitionException {
        try {
            match(this.input, 144, FOLLOW_I_STATEMENT_FORMAT10t_in_insn_format10t2314);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 44, FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t2316);
            pushFollow(FOLLOW_label_ref_in_insn_format10t2318);
            Label labelLabel_ref = label_ref();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction10t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), labelLabel_ref));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format10x() throws RecognitionException {
        try {
            match(this.input, 145, FOLLOW_I_STATEMENT_FORMAT10x_in_insn_format10x2341);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 45, FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x2343);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction10x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format11n() throws RecognitionException {
        try {
            match(this.input, 146, FOLLOW_I_STATEMENT_FORMAT11n_in_insn_format11n2366);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 47, FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n2368);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format11n2370);
            pushFollow(FOLLOW_short_integral_literal_in_insn_format11n2372);
            short sShort_integral_literal = short_integral_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            byte register_nibble = parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null);
            LiteralTools.checkNibble(sShort_integral_literal);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction11n(opcodeByName, register_nibble, sShort_integral_literal));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format11x() throws RecognitionException {
        try {
            match(this.input, 147, FOLLOW_I_STATEMENT_FORMAT11x_in_insn_format11x2395);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 48, FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x2397);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format11x2399);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction11x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format12x() throws RecognitionException {
        try {
            match(this.input, 148, FOLLOW_I_STATEMENT_FORMAT12x_in_insn_format12x2422);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 49, FOLLOW_INSTRUCTION_FORMAT12x_in_insn_format12x2424);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format12x2428);
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format12x2432);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction12x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null), parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format20bc() throws RecognitionException {
        try {
            match(this.input, 149, FOLLOW_I_STATEMENT_FORMAT20bc_in_insn_format20bc2455);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 51, FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc2457);
            pushFollow(FOLLOW_verification_error_type_in_insn_format20bc2459);
            int iVerification_error_type = verification_error_type();
            this.state._fsp--;
            pushFollow(FOLLOW_verification_error_reference_in_insn_format20bc2461);
            ImmutableReference immutableReferenceVerification_error_reference = verification_error_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction20bc(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), iVerification_error_type, this.dexBuilder.internReference(immutableReferenceVerification_error_reference)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format20t() throws RecognitionException {
        try {
            match(this.input, 150, FOLLOW_I_STATEMENT_FORMAT20t_in_insn_format20t2484);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 52, FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t2486);
            pushFollow(FOLLOW_label_ref_in_insn_format20t2488);
            Label labelLabel_ref = label_ref();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction20t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), labelLabel_ref));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21c_field() throws RecognitionException {
        try {
            match(this.input, 151, FOLLOW_I_STATEMENT_FORMAT21c_FIELD_in_insn_format21c_field2511);
            ImmutableFieldReference immutableFieldReference = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) this.input.LT(1);
            if (this.input.LA(1) >= 53 && this.input.LA(1) <= 54) {
                this.input.consume();
                this.state.errorRecovery = false;
                CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_field2523);
                pushFollow(FOLLOW_field_reference_in_insn_format21c_field2525);
                field_reference_return field_reference_returnVarField_reference = field_reference();
                this.state._fsp--;
                match(this.input, 3, null);
                Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
                short register_byte = parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null);
                if (field_reference_returnVarField_reference != null) {
                    field_reference_return field_reference_returnVar = field_reference_returnVarField_reference;
                    immutableFieldReference = field_reference_returnVarField_reference.fieldReference;
                }
                this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction21c(opcodeByName, register_byte, this.dexBuilder.internFieldReference(immutableFieldReference)));
                return;
            }
            throw new MismatchedSetException(null, this.input);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21c_method_handle() throws RecognitionException {
        try {
            match(this.input, 152, FOLLOW_I_STATEMENT_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2548);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 55, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle2553);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_method_handle2556);
            pushFollow(FOLLOW_method_handle_reference_in_insn_format21c_method_handle2558);
            ImmutableMethodHandleReference immutableMethodHandleReferenceMethod_handle_reference = method_handle_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction21c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internMethodHandle(immutableMethodHandleReferenceMethod_handle_reference)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21c_method_type() throws RecognitionException {
        try {
            match(this.input, 153, FOLLOW_I_STATEMENT_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2581);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 56, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type2586);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_method_type2589);
            pushFollow(FOLLOW_method_prototype_in_insn_format21c_method_type2591);
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction21c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internMethodProtoReference(immutableMethodProtoReferenceMethod_prototype)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21c_string() throws RecognitionException {
        try {
            match(this.input, 154, FOLLOW_I_STATEMENT_FORMAT21c_STRING_in_insn_format21c_string2614);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 57, FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string2616);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_string2618);
            pushFollow(FOLLOW_string_literal_in_insn_format21c_string2620);
            String strString_literal = string_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction21c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internStringReference(strString_literal)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21c_type() throws RecognitionException {
        try {
            match(this.input, 155, FOLLOW_I_STATEMENT_FORMAT21c_TYPE_in_insn_format21c_type2643);
            String str = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 58, FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type2645);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_type2647);
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type2649);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            short register_byte = parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null);
            MethodImplementationBuilder methodImplementationBuilder = this.method_stack.peek().methodBuilder;
            DexBuilder dexBuilder = this.dexBuilder;
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
                str = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
            }
            methodImplementationBuilder.addInstruction(new BuilderInstruction21c(opcodeByName, register_byte, dexBuilder.internTypeReference(str)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21ih() throws RecognitionException {
        try {
            match(this.input, 156, FOLLOW_I_STATEMENT_FORMAT21ih_in_insn_format21ih2672);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 59, FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih2674);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21ih2676);
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21ih2678);
            int iFixed_32bit_literal = fixed_32bit_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction21ih(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), iFixed_32bit_literal));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21lh() throws RecognitionException {
        try {
            match(this.input, 157, FOLLOW_I_STATEMENT_FORMAT21lh_in_insn_format21lh2701);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 60, FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh2703);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21lh2705);
            pushFollow(FOLLOW_fixed_64bit_literal_in_insn_format21lh2707);
            long jFixed_64bit_literal = fixed_64bit_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction21lh(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), jFixed_64bit_literal));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21s() throws RecognitionException {
        try {
            match(this.input, 158, FOLLOW_I_STATEMENT_FORMAT21s_in_insn_format21s2730);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 61, FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s2732);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21s2734);
            pushFollow(FOLLOW_short_integral_literal_in_insn_format21s2736);
            short sShort_integral_literal = short_integral_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction21s(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), sShort_integral_literal));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format21t() throws RecognitionException {
        try {
            match(this.input, 159, FOLLOW_I_STATEMENT_FORMAT21t_in_insn_format21t2759);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 62, FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t2761);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21t2763);
            pushFollow(FOLLOW_label_ref_in_insn_format21t2765);
            Label labelLabel_ref = label_ref();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction21t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), labelLabel_ref));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format22b() throws RecognitionException {
        try {
            match(this.input, 160, FOLLOW_I_STATEMENT_FORMAT22b_in_insn_format22b2788);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 63, FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b2790);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22b2794);
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22b2798);
            pushFollow(FOLLOW_short_integral_literal_in_insn_format22b2800);
            short sShort_integral_literal = short_integral_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            short register_byte = parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null);
            short register_byte2 = parseRegister_byte(commonTree3 != null ? commonTree3.getText() : null);
            LiteralTools.checkByte(sShort_integral_literal);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction22b(opcodeByName, register_byte, register_byte2, sShort_integral_literal));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format22c_field() throws RecognitionException {
        try {
            match(this.input, 161, FOLLOW_I_STATEMENT_FORMAT22c_FIELD_in_insn_format22c_field2823);
            ImmutableFieldReference immutableFieldReference = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) this.input.LT(1);
            if (this.input.LA(1) >= 64 && this.input.LA(1) <= 65) {
                this.input.consume();
                this.state.errorRecovery = false;
                CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field2837);
                CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field2841);
                pushFollow(FOLLOW_field_reference_in_insn_format22c_field2843);
                field_reference_return field_reference_returnVarField_reference = field_reference();
                this.state._fsp--;
                match(this.input, 3, null);
                Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
                byte register_nibble = parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null);
                byte register_nibble2 = parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null);
                if (field_reference_returnVarField_reference != null) {
                    field_reference_return field_reference_returnVar = field_reference_returnVarField_reference;
                    immutableFieldReference = field_reference_returnVarField_reference.fieldReference;
                }
                this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction22c(opcodeByName, register_nibble, register_nibble2, this.dexBuilder.internFieldReference(immutableFieldReference)));
                return;
            }
            throw new MismatchedSetException(null, this.input);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format22c_type() throws RecognitionException {
        try {
            match(this.input, 162, FOLLOW_I_STATEMENT_FORMAT22c_TYPE_in_insn_format22c_type2866);
            String str = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 66, FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type2868);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_type2872);
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_type2876);
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type2878);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            byte register_nibble = parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null);
            byte register_nibble2 = parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null);
            MethodImplementationBuilder methodImplementationBuilder = this.method_stack.peek().methodBuilder;
            DexBuilder dexBuilder = this.dexBuilder;
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
                str = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
            }
            methodImplementationBuilder.addInstruction(new BuilderInstruction22c(opcodeByName, register_nibble, register_nibble2, dexBuilder.internTypeReference(str)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format22s() throws RecognitionException {
        try {
            match(this.input, 163, FOLLOW_I_STATEMENT_FORMAT22s_in_insn_format22s2901);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 68, FOLLOW_INSTRUCTION_FORMAT22s_in_insn_format22s2903);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22s2907);
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22s2911);
            pushFollow(FOLLOW_short_integral_literal_in_insn_format22s2913);
            short sShort_integral_literal = short_integral_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction22s(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null), parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null), sShort_integral_literal));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format22t() throws RecognitionException {
        try {
            match(this.input, 164, FOLLOW_I_STATEMENT_FORMAT22t_in_insn_format22t2936);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 70, FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t2938);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22t2942);
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22t2946);
            pushFollow(FOLLOW_label_ref_in_insn_format22t2948);
            Label labelLabel_ref = label_ref();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction22t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_nibble(commonTree2 != null ? commonTree2.getText() : null), parseRegister_nibble(commonTree3 != null ? commonTree3.getText() : null), labelLabel_ref));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format22x() throws RecognitionException {
        try {
            match(this.input, 165, FOLLOW_I_STATEMENT_FORMAT22x_in_insn_format22x2971);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 71, FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x2973);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22x2977);
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22x2981);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction22x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), parseRegister_short(commonTree3 != null ? commonTree3.getText() : null)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format23x() throws RecognitionException {
        try {
            match(this.input, 166, FOLLOW_I_STATEMENT_FORMAT23x_in_insn_format23x3004);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 72, FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x3006);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x3010);
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x3014);
            CommonTree commonTree4 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x3018);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction23x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), parseRegister_byte(commonTree3 != null ? commonTree3.getText() : null), parseRegister_byte(commonTree4 != null ? commonTree4.getText() : null)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format30t() throws RecognitionException {
        try {
            match(this.input, 167, FOLLOW_I_STATEMENT_FORMAT30t_in_insn_format30t3041);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 73, FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t3043);
            pushFollow(FOLLOW_label_ref_in_insn_format30t3045);
            Label labelLabel_ref = label_ref();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction30t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), labelLabel_ref));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format31c() throws RecognitionException {
        try {
            match(this.input, 168, FOLLOW_I_STATEMENT_FORMAT31c_in_insn_format31c3068);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 74, FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c3070);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31c3072);
            pushFollow(FOLLOW_string_literal_in_insn_format31c3074);
            String strString_literal = string_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction31c(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), this.dexBuilder.internStringReference(strString_literal)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format31i() throws RecognitionException {
        try {
            match(this.input, 169, FOLLOW_I_STATEMENT_FORMAT31i_in_insn_format31i3097);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 75, FOLLOW_INSTRUCTION_FORMAT31i_in_insn_format31i3099);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31i3101);
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format31i3103);
            int iFixed_32bit_literal = fixed_32bit_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction31i(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), iFixed_32bit_literal));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format31t() throws RecognitionException {
        try {
            match(this.input, 170, FOLLOW_I_STATEMENT_FORMAT31t_in_insn_format31t3126);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 77, FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t3128);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31t3130);
            pushFollow(FOLLOW_label_ref_in_insn_format31t3132);
            Label labelLabel_ref = label_ref();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction31t(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), labelLabel_ref));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format32x() throws RecognitionException {
        try {
            match(this.input, 171, FOLLOW_I_STATEMENT_FORMAT32x_in_insn_format32x3155);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 78, FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x3157);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format32x3161);
            CommonTree commonTree3 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format32x3165);
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction32x(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_short(commonTree2 != null ? commonTree2.getText() : null), parseRegister_short(commonTree3 != null ? commonTree3.getText() : null)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format35c_call_site() throws RecognitionException {
        byte b;
        try {
            match(this.input, 172, FOLLOW_I_STATEMENT_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3193);
            byte[] bArr = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 79, FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site3195);
            pushFollow(FOLLOW_register_list_in_insn_format35c_call_site3197);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            pushFollow(FOLLOW_call_site_reference_in_insn_format35c_call_site3199);
            ImmutableCallSiteReference immutableCallSiteReferenceCall_site_reference = call_site_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            if (register_list_returnVarRegister_list != null) {
                register_list_return register_list_returnVar = register_list_returnVarRegister_list;
                bArr = register_list_returnVarRegister_list.registers;
            }
            if (register_list_returnVarRegister_list != null) {
                register_list_return register_list_returnVar2 = register_list_returnVarRegister_list;
                b = register_list_returnVarRegister_list.registerCount;
            } else {
                b = 0;
            }
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction35c(opcodeByName, b, bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], this.dexBuilder.internCallSite(immutableCallSiteReferenceCall_site_reference)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format35c_method() throws RecognitionException {
        byte b;
        try {
            match(this.input, 173, FOLLOW_I_STATEMENT_FORMAT35c_METHOD_in_insn_format35c_method3222);
            byte[] bArr = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 80, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method3224);
            pushFollow(FOLLOW_register_list_in_insn_format35c_method3226);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            pushFollow(FOLLOW_method_reference_in_insn_format35c_method3228);
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            if (register_list_returnVarRegister_list != null) {
                register_list_return register_list_returnVar = register_list_returnVarRegister_list;
                bArr = register_list_returnVarRegister_list.registers;
            }
            if (register_list_returnVarRegister_list != null) {
                register_list_return register_list_returnVar2 = register_list_returnVarRegister_list;
                b = register_list_returnVarRegister_list.registerCount;
            } else {
                b = 0;
            }
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction35c(opcodeByName, b, bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], this.dexBuilder.internMethodReference(immutableMethodReferenceMethod_reference)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format35c_type() throws RecognitionException {
        byte[] bArr;
        byte b;
        try {
            match(this.input, 174, FOLLOW_I_STATEMENT_FORMAT35c_TYPE_in_insn_format35c_type3251);
            String str = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 83, FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type3253);
            pushFollow(FOLLOW_register_list_in_insn_format35c_type3255);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type3257);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            if (register_list_returnVarRegister_list != null) {
                register_list_return register_list_returnVar = register_list_returnVarRegister_list;
                bArr = register_list_returnVarRegister_list.registers;
            } else {
                bArr = null;
            }
            if (register_list_returnVarRegister_list != null) {
                register_list_return register_list_returnVar2 = register_list_returnVarRegister_list;
                b = register_list_returnVarRegister_list.registerCount;
            } else {
                b = 0;
            }
            MethodImplementationBuilder methodImplementationBuilder = this.method_stack.peek().methodBuilder;
            byte b2 = bArr[0];
            byte b3 = bArr[1];
            byte b4 = bArr[2];
            byte b5 = bArr[3];
            byte b6 = bArr[4];
            DexBuilder dexBuilder = this.dexBuilder;
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
                str = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
            }
            methodImplementationBuilder.addInstruction(new BuilderInstruction35c(opcodeByName, b, b2, b3, b4, b5, b6, dexBuilder.internTypeReference(str)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format3rc_call_site() throws RecognitionException {
        int i;
        try {
            match(this.input, 175, FOLLOW_I_STATEMENT_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3285);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 86, FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site3287);
            pushFollow(FOLLOW_register_range_in_insn_format3rc_call_site3289);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            pushFollow(FOLLOW_call_site_reference_in_insn_format3rc_call_site3291);
            ImmutableCallSiteReference immutableCallSiteReferenceCall_site_reference = call_site_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            int i2 = 0;
            if (register_range_returnVarRegister_range != null) {
                register_range_return register_range_returnVar = register_range_returnVarRegister_range;
                i = register_range_returnVarRegister_range.startRegister;
            } else {
                i = 0;
            }
            if (register_range_returnVarRegister_range != null) {
                register_range_return register_range_returnVar2 = register_range_returnVarRegister_range;
                i2 = register_range_returnVarRegister_range.endRegister;
            }
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction3rc(opcodeByName, i, (i2 - i) + 1, this.dexBuilder.internCallSite(immutableCallSiteReferenceCall_site_reference)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format3rc_method() throws RecognitionException {
        int i;
        try {
            match(this.input, 176, FOLLOW_I_STATEMENT_FORMAT3rc_METHOD_in_insn_format3rc_method3314);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 87, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method3316);
            pushFollow(FOLLOW_register_range_in_insn_format3rc_method3318);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            pushFollow(FOLLOW_method_reference_in_insn_format3rc_method3320);
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            int i2 = 0;
            if (register_range_returnVarRegister_range != null) {
                register_range_return register_range_returnVar = register_range_returnVarRegister_range;
                i = register_range_returnVarRegister_range.startRegister;
            } else {
                i = 0;
            }
            if (register_range_returnVarRegister_range != null) {
                register_range_return register_range_returnVar2 = register_range_returnVarRegister_range;
                i2 = register_range_returnVarRegister_range.endRegister;
            }
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction3rc(opcodeByName, i, (i2 - i) + 1, this.dexBuilder.internMethodReference(immutableMethodReferenceMethod_reference)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format3rc_type() throws RecognitionException {
        int i;
        try {
            match(this.input, 177, FOLLOW_I_STATEMENT_FORMAT3rc_TYPE_in_insn_format3rc_type3343);
            String str = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 89, FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type3345);
            pushFollow(FOLLOW_register_range_in_insn_format3rc_type3347);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type3349);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            int i2 = 0;
            if (register_range_returnVarRegister_range != null) {
                register_range_return register_range_returnVar = register_range_returnVarRegister_range;
                i = register_range_returnVarRegister_range.startRegister;
            } else {
                i = 0;
            }
            if (register_range_returnVarRegister_range != null) {
                register_range_return register_range_returnVar2 = register_range_returnVarRegister_range;
                i2 = register_range_returnVarRegister_range.endRegister;
            }
            int i3 = (i2 - i) + 1;
            MethodImplementationBuilder methodImplementationBuilder = this.method_stack.peek().methodBuilder;
            DexBuilder dexBuilder = this.dexBuilder;
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor != null) {
                nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
                str = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
            }
            methodImplementationBuilder.addInstruction(new BuilderInstruction3rc(opcodeByName, i, i3, dexBuilder.internTypeReference(str)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format45cc_method() throws RecognitionException {
        byte b;
        try {
            match(this.input, 178, FOLLOW_I_STATEMENT_FORMAT45cc_METHOD_in_insn_format45cc_method3372);
            byte[] bArr = null;
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 92, FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method3374);
            pushFollow(FOLLOW_register_list_in_insn_format45cc_method3376);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            pushFollow(FOLLOW_method_reference_in_insn_format45cc_method3378);
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference();
            this.state._fsp--;
            pushFollow(FOLLOW_method_prototype_in_insn_format45cc_method3380);
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            if (register_list_returnVarRegister_list != null) {
                register_list_return register_list_returnVar = register_list_returnVarRegister_list;
                bArr = register_list_returnVarRegister_list.registers;
            }
            if (register_list_returnVarRegister_list != null) {
                register_list_return register_list_returnVar2 = register_list_returnVarRegister_list;
                b = register_list_returnVarRegister_list.registerCount;
            } else {
                b = 0;
            }
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction45cc(opcodeByName, b, bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], this.dexBuilder.internMethodReference(immutableMethodReferenceMethod_reference), this.dexBuilder.internMethodProtoReference(immutableMethodProtoReferenceMethod_prototype)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format4rcc_method() throws RecognitionException {
        int i;
        try {
            match(this.input, 179, FOLLOW_I_STATEMENT_FORMAT4rcc_METHOD_in_insn_format4rcc_method3403);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 93, FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method3405);
            pushFollow(FOLLOW_register_range_in_insn_format4rcc_method3407);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            pushFollow(FOLLOW_method_reference_in_insn_format4rcc_method3409);
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference();
            this.state._fsp--;
            pushFollow(FOLLOW_method_prototype_in_insn_format4rcc_method3411);
            ImmutableMethodProtoReference immutableMethodProtoReferenceMethod_prototype = method_prototype();
            this.state._fsp--;
            match(this.input, 3, null);
            Opcode opcodeByName = this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null);
            int i2 = 0;
            if (register_range_returnVarRegister_range != null) {
                register_range_return register_range_returnVar = register_range_returnVarRegister_range;
                i = register_range_returnVarRegister_range.startRegister;
            } else {
                i = 0;
            }
            if (register_range_returnVarRegister_range != null) {
                register_range_return register_range_returnVar2 = register_range_returnVarRegister_range;
                i2 = register_range_returnVarRegister_range.endRegister;
            }
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction4rcc(opcodeByName, i, (i2 - i) + 1, this.dexBuilder.internMethodReference(immutableMethodReferenceMethod_reference), this.dexBuilder.internMethodProtoReference(immutableMethodProtoReferenceMethod_prototype)));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_format51l_type() throws RecognitionException {
        try {
            match(this.input, 180, FOLLOW_I_STATEMENT_FORMAT51l_in_insn_format51l_type3434);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 94, FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l_type3436);
            CommonTree commonTree2 = (CommonTree) match(this.input, 204, FOLLOW_REGISTER_in_insn_format51l_type3438);
            pushFollow(FOLLOW_fixed_64bit_literal_in_insn_format51l_type3440);
            long jFixed_64bit_literal = fixed_64bit_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderInstruction51l(this.opcodes.getOpcodeByName(commonTree != null ? commonTree.getText() : null), parseRegister_byte(commonTree2 != null ? commonTree2.getText() : null), jFixed_64bit_literal));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_array_data_directive() throws RecognitionException {
        try {
            match(this.input, 143, FOLLOW_I_STATEMENT_ARRAY_DATA_in_insn_array_data_directive3463);
            match(this.input, 2, null);
            match(this.input, 103, FOLLOW_I_ARRAY_ELEMENT_SIZE_in_insn_array_data_directive3466);
            match(this.input, 2, null);
            pushFollow(FOLLOW_short_integral_literal_in_insn_array_data_directive3468);
            short sShort_integral_literal = short_integral_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            pushFollow(FOLLOW_array_elements_in_insn_array_data_directive3471);
            List<Number> listArray_elements = array_elements();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderArrayPayload(sShort_integral_literal, listArray_elements));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_packed_switch_directive() throws RecognitionException {
        try {
            match(this.input, 181, FOLLOW_I_STATEMENT_PACKED_SWITCH_in_insn_packed_switch_directive3493);
            match(this.input, 2, null);
            match(this.input, 132, FOLLOW_I_PACKED_SWITCH_START_KEY_in_insn_packed_switch_directive3496);
            match(this.input, 2, null);
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive3498);
            int iFixed_32bit_literal = fixed_32bit_literal();
            this.state._fsp--;
            match(this.input, 3, null);
            pushFollow(FOLLOW_packed_switch_elements_in_insn_packed_switch_directive3501);
            List<Label> listPacked_switch_elements = packed_switch_elements();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderPackedSwitchPayload(iFixed_32bit_literal, listPacked_switch_elements));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final void insn_sparse_switch_directive() throws RecognitionException {
        try {
            match(this.input, 182, FOLLOW_I_STATEMENT_SPARSE_SWITCH_in_insn_sparse_switch_directive3525);
            match(this.input, 2, null);
            pushFollow(FOLLOW_sparse_switch_elements_in_insn_sparse_switch_directive3527);
            List<SwitchLabelElement> listSparse_switch_elements = sparse_switch_elements();
            this.state._fsp--;
            match(this.input, 3, null);
            this.method_stack.peek().methodBuilder.addInstruction(new BuilderSparseSwitchPayload(listSparse_switch_elements));
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
    }

    public final String array_descriptor() throws RecognitionException {
        char c;
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 8, FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor3548);
            int iLA = this.input.LA(1);
            if (iLA == 202) {
                c = 1;
            } else {
                if (iLA != 16) {
                    throw new NoViableAltException("", 40, 0, this.input);
                }
                c = 2;
            }
            if (c == 1) {
                CommonTree commonTree2 = (CommonTree) match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_array_descriptor3552);
                return (commonTree != null ? commonTree.getText() : null) + (commonTree2 != null ? commonTree2.getText() : null);
            }
            if (c != 2) {
                return null;
            }
            CommonTree commonTree3 = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_array_descriptor3580);
            return (commonTree != null ? commonTree.getText() : null) + (commonTree3 != null ? commonTree3.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final nonvoid_type_descriptor_return nonvoid_type_descriptor() throws RecognitionException {
        char c;
        nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = new nonvoid_type_descriptor_return();
        nonvoid_type_descriptor_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 8) {
                c = 3;
            } else if (iLA == 16) {
                c = 2;
            } else {
                if (iLA != 202) {
                    throw new NoViableAltException("", 41, 0, this.input);
                }
                c = 1;
            }
            if (c == 1) {
                match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor3598);
                nonvoid_type_descriptor_returnVar.type = this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(nonvoid_type_descriptor_returnVar.start), this.input.getTreeAdaptor().getTokenStopIndex(nonvoid_type_descriptor_returnVar.start));
            } else if (c == 2) {
                match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor3606);
                nonvoid_type_descriptor_returnVar.type = this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(nonvoid_type_descriptor_returnVar.start), this.input.getTreeAdaptor().getTokenStopIndex(nonvoid_type_descriptor_returnVar.start));
            } else if (c == 3) {
                pushFollow(FOLLOW_array_descriptor_in_nonvoid_type_descriptor3614);
                String strArray_descriptor = array_descriptor();
                this.state._fsp--;
                nonvoid_type_descriptor_returnVar.type = strArray_descriptor;
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return nonvoid_type_descriptor_returnVar;
    }

    public final reference_type_descriptor_return reference_type_descriptor() throws RecognitionException {
        char c;
        reference_type_descriptor_return reference_type_descriptor_returnVar = new reference_type_descriptor_return();
        reference_type_descriptor_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 16) {
                c = 1;
            } else {
                if (iLA != 8) {
                    throw new NoViableAltException("", 42, 0, this.input);
                }
                c = 2;
            }
            if (c == 1) {
                match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor3635);
                reference_type_descriptor_returnVar.type = this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(reference_type_descriptor_returnVar.start), this.input.getTreeAdaptor().getTokenStopIndex(reference_type_descriptor_returnVar.start));
            } else if (c == 2) {
                pushFollow(FOLLOW_array_descriptor_in_reference_type_descriptor3643);
                String strArray_descriptor = array_descriptor();
                this.state._fsp--;
                reference_type_descriptor_returnVar.type = strArray_descriptor;
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return reference_type_descriptor_returnVar;
    }

    public final String type_descriptor() throws RecognitionException {
        char c;
        try {
            int iLA = this.input.LA(1);
            if (iLA == 215) {
                c = 1;
            } else {
                if (iLA != 8 && iLA != 16 && iLA != 202) {
                    throw new NoViableAltException("", 43, 0, this.input);
                }
                c = 2;
            }
            if (c == 1) {
                match(this.input, 215, FOLLOW_VOID_TYPE_in_type_descriptor3663);
                return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
            }
            if (c != 2) {
                return null;
            }
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_type_descriptor3671);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            if (nonvoid_type_descriptor_returnVarNonvoid_type_descriptor == null) {
                return null;
            }
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVar = nonvoid_type_descriptor_returnVarNonvoid_type_descriptor;
            return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.type;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final short short_integral_literal() throws RecognitionException {
        char c;
        int iInteger_literal;
        try {
            int iLA = this.input.LA(1);
            if (iLA == 12) {
                c = 5;
            } else if (iLA == 15) {
                c = 4;
            } else if (iLA == 95) {
                c = 2;
            } else if (iLA == 189) {
                c = 1;
            } else {
                if (iLA != 207) {
                    throw new NoViableAltException("", 44, 0, this.input);
                }
                c = 3;
            }
            if (c == 1) {
                pushFollow(FOLLOW_long_literal_in_short_integral_literal3689);
                long jLong_literal = long_literal();
                this.state._fsp--;
                LiteralTools.checkShort(jLong_literal);
                return (short) jLong_literal;
            }
            if (c == 2) {
                pushFollow(FOLLOW_integer_literal_in_short_integral_literal3701);
                iInteger_literal = integer_literal();
                this.state._fsp--;
                LiteralTools.checkShort(iInteger_literal);
            } else {
                if (c == 3) {
                    pushFollow(FOLLOW_short_literal_in_short_integral_literal3713);
                    short sShort_literal = short_literal();
                    this.state._fsp--;
                    return sShort_literal;
                }
                if (c == 4) {
                    pushFollow(FOLLOW_char_literal_in_short_integral_literal3721);
                    iInteger_literal = char_literal();
                    this.state._fsp--;
                } else {
                    if (c != 5) {
                        return (short) 0;
                    }
                    pushFollow(FOLLOW_byte_literal_in_short_integral_literal3729);
                    iInteger_literal = byte_literal();
                    this.state._fsp--;
                }
            }
            return (short) iInteger_literal;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return (short) 0;
        }
    }

    public final int integral_literal() throws RecognitionException {
        char c;
        int iInteger_literal;
        try {
            int iLA = this.input.LA(1);
            if (iLA == 12) {
                c = 4;
            } else if (iLA == 95) {
                c = 2;
            } else if (iLA == 189) {
                c = 1;
            } else {
                if (iLA != 207) {
                    throw new NoViableAltException("", 45, 0, this.input);
                }
                c = 3;
            }
            if (c == 1) {
                pushFollow(FOLLOW_long_literal_in_integral_literal3744);
                long jLong_literal = long_literal();
                this.state._fsp--;
                LiteralTools.checkInt(jLong_literal);
                return (int) jLong_literal;
            }
            if (c == 2) {
                pushFollow(FOLLOW_integer_literal_in_integral_literal3756);
                iInteger_literal = integer_literal();
                this.state._fsp--;
            } else if (c == 3) {
                pushFollow(FOLLOW_short_literal_in_integral_literal3764);
                iInteger_literal = short_literal();
                this.state._fsp--;
            } else {
                if (c != 4) {
                    return 0;
                }
                pushFollow(FOLLOW_byte_literal_in_integral_literal3772);
                iInteger_literal = byte_literal();
                this.state._fsp--;
            }
            return iInteger_literal;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return 0;
        }
    }

    public final int integer_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 95, FOLLOW_INTEGER_LITERAL_in_integer_literal3788);
            return LiteralTools.parseInt(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return 0;
        }
    }

    public final long long_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 189, FOLLOW_LONG_LITERAL_in_long_literal3803);
            return LiteralTools.parseLong(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return 0L;
        }
    }

    public final short short_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_short_literal3818);
            return LiteralTools.parseShort(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return (short) 0;
        }
    }

    public final byte byte_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_byte_literal3833);
            return LiteralTools.parseByte(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return (byte) 0;
        }
    }

    public final float float_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 39, FOLLOW_FLOAT_LITERAL_in_float_literal3848);
            return LiteralTools.parseFloat(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return 0.0f;
        }
    }

    public final double double_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 23, FOLLOW_DOUBLE_LITERAL_in_double_literal3863);
            return LiteralTools.parseDouble(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return 0.0d;
        }
    }

    public final char char_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_char_literal3878);
            return (commonTree != null ? commonTree.getText() : null).charAt(1);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return (char) 0;
        }
    }

    public final String string_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 211, FOLLOW_STRING_LITERAL_in_string_literal3893);
            String text = commonTree != null ? commonTree.getText() : null;
            return text.substring(1, text.length() - 1);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final boolean bool_literal() throws RecognitionException {
        try {
            CommonTree commonTree = (CommonTree) match(this.input, 11, FOLLOW_BOOL_LITERAL_in_bool_literal3912);
            return Boolean.parseBoolean(commonTree != null ? commonTree.getText() : null);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return false;
        }
    }

    public final List<EncodedValue> array_literal() throws RecognitionException {
        ArrayList arrayListNewArrayList;
        RecognitionException e;
        try {
            arrayListNewArrayList = ListUtil.newArrayList();
            try {
                match(this.input, 110, FOLLOW_I_ENCODED_ARRAY_in_array_literal3934);
                if (this.input.LA(1) == 2) {
                    match(this.input, 2, null);
                    while (true) {
                        int iLA = this.input.LA(1);
                        if (iLA != 8 && ((iLA < 11 || iLA > 12) && ((iLA < 15 || iLA > 16) && iLA != 23 && iLA != 39 && iLA != 95 && ((iLA < 110 || iLA > 114) && iLA != 128 && iLA != 183 && iLA != 189 && iLA != 195 && iLA != 202 && iLA != 207 && iLA != 211 && iLA != 215)))) {
                            break;
                        }
                        pushFollow(FOLLOW_literal_in_array_literal3937);
                        ImmutableEncodedValue immutableEncodedValueLiteral = literal();
                        this.state._fsp--;
                        arrayListNewArrayList.add(immutableEncodedValueLiteral);
                    }
                    match(this.input, 3, null);
                }
            } catch (RecognitionException e2) {
                e = e2;
                reportError(e);
                recover(this.input, e);
            }
        } catch (RecognitionException e3) {
            arrayListNewArrayList = null;
            e = e3;
        }
        return arrayListNewArrayList;
    }

    public final Set<Annotation> annotations() throws RecognitionException {
        try {
            HashMap map = new HashMap();
            match(this.input, 100, FOLLOW_I_ANNOTATIONS_in_annotations3962);
            if (this.input.LA(1) == 2) {
                match(this.input, 2, null);
                while (this.input.LA(1) == 99) {
                    pushFollow(FOLLOW_annotation_in_annotations3965);
                    Annotation annotation = annotation();
                    this.state._fsp--;
                    if (((Annotation) map.put(annotation.getType(), annotation)) != null) {
                        throw new SemanticException(this.input, "Multiple annotations of type %s", annotation.getType());
                    }
                }
                match(this.input, 3, null);
            }
            return new HashSet(map.values());
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final Annotation annotation() throws RecognitionException {
        String str;
        List<AnnotationElement> list;
        try {
            match(this.input, 99, FOLLOW_I_ANNOTATION_in_annotation3994);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 6, FOLLOW_ANNOTATION_VISIBILITY_in_annotation3996);
            pushFollow(FOLLOW_subannotation_in_annotation3998);
            subannotation_return subannotation_returnVarSubannotation = subannotation();
            this.state._fsp--;
            match(this.input, 3, null);
            int visibility = AnnotationVisibility.getVisibility(commonTree != null ? commonTree.getText() : null);
            if (subannotation_returnVarSubannotation != null) {
                subannotation_return subannotation_returnVar = subannotation_returnVarSubannotation;
                str = subannotation_returnVarSubannotation.annotationType;
            } else {
                str = null;
            }
            if (subannotation_returnVarSubannotation != null) {
                subannotation_return subannotation_returnVar2 = subannotation_returnVarSubannotation;
                list = subannotation_returnVarSubannotation.elements;
            } else {
                list = null;
            }
            return new ImmutableAnnotation(visibility, str, list);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final AnnotationElement annotation_element() throws RecognitionException {
        try {
            match(this.input, 101, FOLLOW_I_ANNOTATION_ELEMENT_in_annotation_element4019);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_annotation_element4021);
            pushFollow(FOLLOW_literal_in_annotation_element4023);
            ImmutableEncodedValue immutableEncodedValueLiteral = literal();
            this.state._fsp--;
            match(this.input, 3, null);
            return new ImmutableAnnotationElement(commonTree != null ? commonTree.getText() : null, immutableEncodedValueLiteral);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final subannotation_return subannotation() throws RecognitionException {
        subannotation_return subannotation_returnVar = new subannotation_return();
        subannotation_returnVar.start = this.input.LT(1);
        try {
            ArrayList arrayListNewArrayList = ListUtil.newArrayList();
            match(this.input, 183, FOLLOW_I_SUBANNOTATION_in_subannotation4050);
            match(this.input, 2, null);
            CommonTree commonTree = (CommonTree) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_subannotation4060);
            while (this.input.LA(1) == 101) {
                pushFollow(FOLLOW_annotation_element_in_subannotation4071);
                AnnotationElement annotationElementAnnotation_element = annotation_element();
                this.state._fsp--;
                arrayListNewArrayList.add(annotationElementAnnotation_element);
            }
            match(this.input, 3, null);
            subannotation_returnVar.annotationType = commonTree != null ? commonTree.getText() : null;
            subannotation_returnVar.elements = arrayListNewArrayList;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
        }
        return subannotation_returnVar;
    }

    public final ImmutableFieldReference field_literal() throws RecognitionException {
        try {
            match(this.input, 112, FOLLOW_I_ENCODED_FIELD_in_field_literal4110);
            match(this.input, 2, null);
            pushFollow(FOLLOW_field_reference_in_field_literal4112);
            field_reference_return field_reference_returnVarField_reference = field_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            if (field_reference_returnVarField_reference == null) {
                return null;
            }
            field_reference_return field_reference_returnVar = field_reference_returnVarField_reference;
            return field_reference_returnVarField_reference.fieldReference;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final ImmutableMethodReference method_literal() throws RecognitionException {
        try {
            match(this.input, 113, FOLLOW_I_ENCODED_METHOD_in_method_literal4133);
            match(this.input, 2, null);
            pushFollow(FOLLOW_method_reference_in_method_literal4135);
            ImmutableMethodReference immutableMethodReferenceMethod_reference = method_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            return immutableMethodReferenceMethod_reference;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }

    public final ImmutableFieldReference enum_literal() throws RecognitionException {
        try {
            match(this.input, 111, FOLLOW_I_ENCODED_ENUM_in_enum_literal4156);
            match(this.input, 2, null);
            pushFollow(FOLLOW_field_reference_in_enum_literal4158);
            field_reference_return field_reference_returnVarField_reference = field_reference();
            this.state._fsp--;
            match(this.input, 3, null);
            if (field_reference_returnVarField_reference == null) {
                return null;
            }
            field_reference_return field_reference_returnVar = field_reference_returnVarField_reference;
            return field_reference_returnVarField_reference.fieldReference;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            return null;
        }
    }
}
