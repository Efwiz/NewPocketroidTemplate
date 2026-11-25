package org.jf.smali;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jf.dexlib2.Opcodes;

/* JADX INFO: loaded from: classes6.dex */
public class smaliParser extends Parser {
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
    static final String DFA31_eofS = "4\uffff";
    static final String DFA31_eotS = "4\uffff";
    static final short[][] DFA31_transition;
    static final String[] DFA31_transitionS;
    static final short[] DFA41_accept;
    static final String DFA41_acceptS = "4\uffff\u0001\u0001\u0002\uffff\u0001\u0002\u0001\u0003";
    static final short[] DFA41_eof;
    static final String DFA41_eofS = "9\uffff";
    static final short[] DFA41_eot;
    static final String DFA41_eotS = "9\uffff";
    static final char[] DFA41_max;
    static final String DFA41_maxS = "\u0001×\u0001Ò\u0001Ê\u000bÅ\u0001È%Å\u0001\uffff\u0001×\u0001\t\u0002\uffff";
    static final char[] DFA41_min;
    static final String DFA41_minS = "\u0001\u0004\u0001\u0005\u0001\u00101\u0014\u0001\uffff\u0001\u0004\u0001\t\u0002\uffff";
    static final short[] DFA41_special;
    static final String DFA41_specialS = "9\uffff}>";
    static final short[][] DFA41_transition;
    static final String[] DFA41_transitionS;
    static final short[] DFA43_accept;
    static final String DFA43_acceptS = "\u0001\uffff\u0001\u0002E\uffff\u0001\u0001";
    static final short[] DFA43_eof;
    static final String DFA43_eofS = "H\uffff";
    static final short[] DFA43_eot;
    static final String DFA43_eotS = "H\uffff";
    static final char[] DFA43_max;
    static final String DFA43_maxS = "\u0001ÒE\uffff\u0001\u0000\u0001\uffff";
    static final char[] DFA43_min;
    static final String DFA43_minS = "\u0001\u0005E\uffff\u0001\u0000\u0001\uffff";
    static final short[] DFA43_special;
    static final String DFA43_specialS = "F\uffff\u0001\u0000\u0001\uffff}>";
    static final short[][] DFA43_transition;
    static final String[] DFA43_transitionS;
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
    public static final int ERROR_CHANNEL = 100;
    public static final int FIELD_DIRECTIVE = 37;
    public static final int FIELD_OFFSET = 38;
    public static final int FLOAT_LITERAL = 39;
    public static final int FLOAT_LITERAL_OR_ID = 40;
    public static final BitSet FOLLOW_ACCESS_SPEC_in_access_list1381;
    public static final BitSet FOLLOW_ACCESS_SPEC_in_simple_name1895;
    public static final BitSet FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2963;
    public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_annotation2965;
    public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_simple_name2037;
    public static final BitSet FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6222;
    public static final BitSet FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor2495;
    public static final BitSet FOLLOW_ARROW_in_field_reference3336;
    public static final BitSet FOLLOW_ARROW_in_insn_sparse_switch_directive6391;
    public static final BitSet FOLLOW_ARROW_in_method_reference3306;
    public static final BitSet FOLLOW_ARROW_in_type_field_method_literal3076;
    public static final BitSet FOLLOW_AT_in_call_site_reference3185;
    public static final BitSet FOLLOW_AT_in_method_handle_reference3233;
    public static final BitSet FOLLOW_AT_in_method_handle_reference3249;
    public static final BitSet FOLLOW_AT_in_method_handle_reference3265;
    public static final BitSet FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2832;
    public static final BitSet FOLLOW_BOOL_LITERAL_in_fixed_literal2884;
    public static final BitSet FOLLOW_BOOL_LITERAL_in_literal2695;
    public static final BitSet FOLLOW_BOOL_LITERAL_in_simple_name1972;
    public static final BitSet FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2814;
    public static final BitSet FOLLOW_BYTE_LITERAL_in_fixed_literal2860;
    public static final BitSet FOLLOW_BYTE_LITERAL_in_integral_literal2786;
    public static final BitSet FOLLOW_BYTE_LITERAL_in_literal2665;
    public static final BitSet FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3548;
    public static final BitSet FOLLOW_CATCH_DIRECTIVE_in_catch_directive3496;
    public static final BitSet FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2826;
    public static final BitSet FOLLOW_CHAR_LITERAL_in_fixed_literal2878;
    public static final BitSet FOLLOW_CHAR_LITERAL_in_integral_literal2780;
    public static final BitSet FOLLOW_CHAR_LITERAL_in_literal2683;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_annotation2967;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_class_spec1300;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1341;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor2547;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor2563;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_subannotation3011;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_super_spec1320;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_type_descriptor2525;
    public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3478;
    public static final BitSet FOLLOW_CLASS_DIRECTIVE_in_class_spec1296;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_array_literal2910;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_catch_directive3512;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_catchall_directive3562;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_call_site5656;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_method5698;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5782;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_type5740;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5813;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5844;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_call_site5880;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5922;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5964;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5995;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method6037;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rms_method6068;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format45cc_method6099;
    public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format4rcc_method6147;
    public static final BitSet FOLLOW_CLOSE_PAREN_in_call_site_reference3183;
    public static final BitSet FOLLOW_CLOSE_PAREN_in_method_prototype2430;
    public static final BitSet FOLLOW_COLON_in_field1455;
    public static final BitSet FOLLOW_COLON_in_field_reference3342;
    public static final BitSet FOLLOW_COLON_in_label3365;
    public static final BitSet FOLLOW_COLON_in_label_ref3386;
    public static final BitSet FOLLOW_COLON_in_local_directive3797;
    public static final BitSet FOLLOW_COLON_in_type_field_method_literal3090;
    public static final BitSet FOLLOW_COMMA_in_array_literal2900;
    public static final BitSet FOLLOW_COMMA_in_call_site_reference3172;
    public static final BitSet FOLLOW_COMMA_in_call_site_reference3177;
    public static final BitSet FOLLOW_COMMA_in_insn_format11n4488;
    public static final BitSet FOLLOW_COMMA_in_insn_format12x4558;
    public static final BitSet FOLLOW_COMMA_in_insn_format20bc4596;
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_field4671;
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_field_odex4709;
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_method_handle4753;
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_method_type4805;
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_string4855;
    public static final BitSet FOLLOW_COMMA_in_insn_format21c_type4893;
    public static final BitSet FOLLOW_COMMA_in_insn_format21ih4931;
    public static final BitSet FOLLOW_COMMA_in_insn_format21lh4969;
    public static final BitSet FOLLOW_COMMA_in_insn_format21s5007;
    public static final BitSet FOLLOW_COMMA_in_insn_format21t5045;
    public static final BitSet FOLLOW_COMMA_in_insn_format22b5083;
    public static final BitSet FOLLOW_COMMA_in_insn_format22b5087;
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_field5127;
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_field5131;
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_field_odex5171;
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_field_odex5175;
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_type5221;
    public static final BitSet FOLLOW_COMMA_in_insn_format22c_type5225;
    public static final BitSet FOLLOW_COMMA_in_insn_format22cs_field5265;
    public static final BitSet FOLLOW_COMMA_in_insn_format22cs_field5269;
    public static final BitSet FOLLOW_COMMA_in_insn_format22s5296;
    public static final BitSet FOLLOW_COMMA_in_insn_format22s5300;
    public static final BitSet FOLLOW_COMMA_in_insn_format22t5340;
    public static final BitSet FOLLOW_COMMA_in_insn_format22t5344;
    public static final BitSet FOLLOW_COMMA_in_insn_format22x5384;
    public static final BitSet FOLLOW_COMMA_in_insn_format23x5422;
    public static final BitSet FOLLOW_COMMA_in_insn_format23x5426;
    public static final BitSet FOLLOW_COMMA_in_insn_format31c5498;
    public static final BitSet FOLLOW_COMMA_in_insn_format31i5535;
    public static final BitSet FOLLOW_COMMA_in_insn_format31t5573;
    public static final BitSet FOLLOW_COMMA_in_insn_format32x5611;
    public static final BitSet FOLLOW_COMMA_in_insn_format35c_call_site5658;
    public static final BitSet FOLLOW_COMMA_in_insn_format35c_method5700;
    public static final BitSet FOLLOW_COMMA_in_insn_format35c_method_odex5784;
    public static final BitSet FOLLOW_COMMA_in_insn_format35c_type5742;
    public static final BitSet FOLLOW_COMMA_in_insn_format35mi_method5815;
    public static final BitSet FOLLOW_COMMA_in_insn_format35ms_method5846;
    public static final BitSet FOLLOW_COMMA_in_insn_format3rc_call_site5882;
    public static final BitSet FOLLOW_COMMA_in_insn_format3rc_method5924;
    public static final BitSet FOLLOW_COMMA_in_insn_format3rc_method_odex5966;
    public static final BitSet FOLLOW_COMMA_in_insn_format3rc_type5997;
    public static final BitSet FOLLOW_COMMA_in_insn_format3rmi_method6039;
    public static final BitSet FOLLOW_COMMA_in_insn_format3rms_method6070;
    public static final BitSet FOLLOW_COMMA_in_insn_format45cc_method6101;
    public static final BitSet FOLLOW_COMMA_in_insn_format45cc_method6105;
    public static final BitSet FOLLOW_COMMA_in_insn_format4rcc_method6149;
    public static final BitSet FOLLOW_COMMA_in_insn_format4rcc_method6153;
    public static final BitSet FOLLOW_COMMA_in_insn_format51l6193;
    public static final BitSet FOLLOW_COMMA_in_local_directive3785;
    public static final BitSet FOLLOW_COMMA_in_local_directive3838;
    public static final BitSet FOLLOW_COMMA_in_parameter_directive3610;
    public static final BitSet FOLLOW_COMMA_in_register_list3405;
    public static final BitSet FOLLOW_DOTDOT_in_catch_directive3506;
    public static final BitSet FOLLOW_DOTDOT_in_catchall_directive3556;
    public static final BitSet FOLLOW_DOTDOT_in_register_range3445;
    public static final BitSet FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2626;
    public static final BitSet FOLLOW_DOUBLE_LITERAL_OR_ID_in_simple_name1961;
    public static final BitSet FOLLOW_DOUBLE_LITERAL_in_double_literal2637;
    public static final BitSet FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2976;
    public static final BitSet FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6243;
    public static final BitSet FOLLOW_END_FIELD_DIRECTIVE_in_field1489;
    public static final BitSet FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3884;
    public static final BitSet FOLLOW_END_METHOD_DIRECTIVE_in_method1614;
    public static final BitSet FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6308;
    public static final BitSet FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3636;
    public static final BitSet FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6401;
    public static final BitSet FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation3016;
    public static final BitSet FOLLOW_ENUM_DIRECTIVE_in_enum_literal3043;
    public static final BitSet FOLLOW_EOF_in_smali_file1209;
    public static final BitSet FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3955;
    public static final BitSet FOLLOW_EQUAL_in_annotation_element2936;
    public static final BitSet FOLLOW_EQUAL_in_field1460;
    public static final BitSet FOLLOW_FIELD_DIRECTIVE_in_field1449;
    public static final BitSet FOLLOW_FIELD_OFFSET_in_insn_format22cs_field5271;
    public static final BitSet FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2605;
    public static final BitSet FOLLOW_FLOAT_LITERAL_OR_ID_in_simple_name1950;
    public static final BitSet FOLLOW_FLOAT_LITERAL_in_float_literal2616;
    public static final BitSet FOLLOW_HIDDENAPI_RESTRICTION_in_simple_name1906;
    public static final BitSet FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1339;
    public static final BitSet FOLLOW_INLINE_INDEX_in_insn_format35mi_method5817;
    public static final BitSet FOLLOW_INLINE_INDEX_in_insn_format3rmi_method6041;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4403;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_simple_name2060;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4463;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_simple_name2082;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4435;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_simple_name2071;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4484;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4522;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_simple_name2093;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x4009;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_simple_name2104;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x4003;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4592;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4635;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4705;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_simple_name2126;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4667;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_simple_name2115;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle4749;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_simple_name2137;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type4801;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_simple_name2148;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4851;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_simple_name2159;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4889;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_simple_name2170;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4927;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4965;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s5003;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t5041;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_simple_name2181;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b5079;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex5167;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_simple_name2203;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field5123;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_simple_name2192;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type5217;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_simple_name2214;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field5261;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_simple_name2225;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s4030;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_simple_name2236;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s4024;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t5336;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_simple_name2247;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x5380;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x5418;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_simple_name2258;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t5462;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5494;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i4051;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_simple_name2269;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i4045;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5569;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_simple_name2280;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5607;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site5650;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_simple_name2291;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5776;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_simple_name2313;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_instruction_format35c_method4074;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_method_handle_reference3263;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_simple_name2324;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_instruction_format35c_method4068;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_simple_name2302;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5734;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_simple_name2335;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5807;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_simple_name2346;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5838;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_simple_name2357;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site5874;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5958;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5916;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5989;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method6031;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method6062;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method6093;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_simple_name2368;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method6141;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_simple_name2379;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l6189;
    public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_simple_name2390;
    public static final BitSet FOLLOW_LINE_DIRECTIVE_in_line_directive3755;
    public static final BitSet FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1840;
    public static final BitSet FOLLOW_LOCAL_DIRECTIVE_in_local_directive3780;
    public static final BitSet FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2796;
    public static final BitSet FOLLOW_LONG_LITERAL_in_fixed_literal2848;
    public static final BitSet FOLLOW_LONG_LITERAL_in_integral_literal2762;
    public static final BitSet FOLLOW_LONG_LITERAL_in_literal2647;
    public static final BitSet FOLLOW_MEMBER_NAME_in_member_name2411;
    public static final BitSet FOLLOW_METHOD_DIRECTIVE_in_method1600;
    public static final BitSet FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_method_handle_reference3231;
    public static final BitSet FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_simple_name2048;
    public static final BitSet FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_method_handle_reference3247;
    public static final BitSet FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_simple_name2054;
    public static final BitSet FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2590;
    public static final BitSet FOLLOW_NEGATIVE_INTEGER_LITERAL_in_simple_name1939;
    public static final BitSet FOLLOW_NULL_LITERAL_in_literal2701;
    public static final BitSet FOLLOW_NULL_LITERAL_in_local_directive3788;
    public static final BitSet FOLLOW_NULL_LITERAL_in_simple_name1983;
    public static final BitSet FOLLOW_OPEN_BRACE_in_array_literal2894;
    public static final BitSet FOLLOW_OPEN_BRACE_in_catch_directive3500;
    public static final BitSet FOLLOW_OPEN_BRACE_in_catchall_directive3550;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_call_site5652;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_method5694;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5778;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_type5736;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35mi_method5809;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35ms_method5840;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_call_site5876;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_method5918;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5960;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_type5991;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rmi_method6033;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rms_method6064;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format45cc_method6095;
    public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format4rcc_method6143;
    public static final BitSet FOLLOW_OPEN_PAREN_in_call_site_reference3168;
    public static final BitSet FOLLOW_OPEN_PAREN_in_method_prototype2426;
    public static final BitSet FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6289;
    public static final BitSet FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3605;
    public static final BitSet FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id1876;
    public static final BitSet FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id_primitive_type2462;
    public static final BitSet FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2579;
    public static final BitSet FOLLOW_POSITIVE_INTEGER_LITERAL_in_simple_name1928;
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor2541;
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_simple_name2015;
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_type_descriptor2519;
    public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_type_field_method_literal3150;
    public static final BitSet FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3934;
    public static final BitSet FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1816;
    public static final BitSet FOLLOW_REGISTER_in_end_local_directive3886;
    public static final BitSet FOLLOW_REGISTER_in_insn_format11n4486;
    public static final BitSet FOLLOW_REGISTER_in_insn_format11x4524;
    public static final BitSet FOLLOW_REGISTER_in_insn_format12x4556;
    public static final BitSet FOLLOW_REGISTER_in_insn_format12x4560;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field4669;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field_odex4707;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_method_handle4751;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_method_type4803;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_string4853;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21c_type4891;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21ih4929;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21lh4967;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21s5005;
    public static final BitSet FOLLOW_REGISTER_in_insn_format21t5043;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22b5081;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22b5085;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field5125;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field5129;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field_odex5169;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field_odex5173;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type5219;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type5223;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22cs_field5263;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22cs_field5267;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22s5294;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22s5298;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22t5338;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22t5342;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22x5382;
    public static final BitSet FOLLOW_REGISTER_in_insn_format22x5386;
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x5420;
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x5424;
    public static final BitSet FOLLOW_REGISTER_in_insn_format23x5428;
    public static final BitSet FOLLOW_REGISTER_in_insn_format31c5496;
    public static final BitSet FOLLOW_REGISTER_in_insn_format31i5533;
    public static final BitSet FOLLOW_REGISTER_in_insn_format31t5571;
    public static final BitSet FOLLOW_REGISTER_in_insn_format32x5609;
    public static final BitSet FOLLOW_REGISTER_in_insn_format32x5613;
    public static final BitSet FOLLOW_REGISTER_in_insn_format51l6191;
    public static final BitSet FOLLOW_REGISTER_in_local_directive3782;
    public static final BitSet FOLLOW_REGISTER_in_parameter_directive3607;
    public static final BitSet FOLLOW_REGISTER_in_register_list3402;
    public static final BitSet FOLLOW_REGISTER_in_register_list3407;
    public static final BitSet FOLLOW_REGISTER_in_register_range3442;
    public static final BitSet FOLLOW_REGISTER_in_register_range3449;
    public static final BitSet FOLLOW_REGISTER_in_restart_local_directive3911;
    public static final BitSet FOLLOW_REGISTER_in_simple_name1994;
    public static final BitSet FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3909;
    public static final BitSet FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2808;
    public static final BitSet FOLLOW_SHORT_LITERAL_in_fixed_literal2854;
    public static final BitSet FOLLOW_SHORT_LITERAL_in_integral_literal2774;
    public static final BitSet FOLLOW_SHORT_LITERAL_in_literal2659;
    public static final BitSet FOLLOW_SIMPLE_NAME_in_simple_name1889;
    public static final BitSet FOLLOW_SOURCE_DIRECTIVE_in_source_directive3976;
    public static final BitSet FOLLOW_SOURCE_DIRECTIVE_in_source_spec1360;
    public static final BitSet FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6382;
    public static final BitSet FOLLOW_STRING_LITERAL_in_call_site_reference3170;
    public static final BitSet FOLLOW_STRING_LITERAL_in_insn_format21c_string4857;
    public static final BitSet FOLLOW_STRING_LITERAL_in_insn_format31c5500;
    public static final BitSet FOLLOW_STRING_LITERAL_in_literal2689;
    public static final BitSet FOLLOW_STRING_LITERAL_in_local_directive3794;
    public static final BitSet FOLLOW_STRING_LITERAL_in_local_directive3842;
    public static final BitSet FOLLOW_STRING_LITERAL_in_parameter_directive3612;
    public static final BitSet FOLLOW_STRING_LITERAL_in_source_directive3978;
    public static final BitSet FOLLOW_STRING_LITERAL_in_source_spec1362;
    public static final BitSet FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation3009;
    public static final BitSet FOLLOW_SUPER_DIRECTIVE_in_super_spec1318;
    public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4594;
    public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_simple_name1917;
    public static final BitSet FOLLOW_VOID_TYPE_in_local_directive3800;
    public static final BitSet FOLLOW_VOID_TYPE_in_simple_name2026;
    public static final BitSet FOLLOW_VOID_TYPE_in_type_descriptor2513;
    public static final BitSet FOLLOW_VOID_TYPE_in_type_field_method_literal3156;
    public static final BitSet FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5848;
    public static final BitSet FOLLOW_VTABLE_INDEX_in_insn_format3rms_method6072;
    public static final BitSet FOLLOW_access_list_in_class_spec1298;
    public static final BitSet FOLLOW_access_or_restriction_in_access_or_restriction_list1416;
    public static final BitSet FOLLOW_access_or_restriction_list_in_field1451;
    public static final BitSet FOLLOW_access_or_restriction_list_in_method1602;
    public static final BitSet FOLLOW_annotation_element_in_annotation2973;
    public static final BitSet FOLLOW_annotation_element_in_subannotation3013;
    public static final BitSet FOLLOW_annotation_in_field1475;
    public static final BitSet FOLLOW_annotation_in_parameter_directive3623;
    public static final BitSet FOLLOW_annotation_in_smali_file1198;
    public static final BitSet FOLLOW_annotation_in_statements_and_directives1699;
    public static final BitSet FOLLOW_array_descriptor_in_nonvoid_type_descriptor2553;
    public static final BitSet FOLLOW_array_descriptor_in_reference_type_descriptor2569;
    public static final BitSet FOLLOW_array_descriptor_in_type_descriptor2531;
    public static final BitSet FOLLOW_array_literal_in_literal2707;
    public static final BitSet FOLLOW_call_site_reference_in_insn_format35c_call_site5660;
    public static final BitSet FOLLOW_call_site_reference_in_insn_format3rc_call_site5884;
    public static final BitSet FOLLOW_catch_directive_in_statements_and_directives1675;
    public static final BitSet FOLLOW_catchall_directive_in_statements_and_directives1683;
    public static final BitSet FOLLOW_class_spec_in_smali_file1150;
    public static final BitSet FOLLOW_debug_directive_in_ordered_method_item1796;
    public static final BitSet FOLLOW_double_literal_in_fixed_literal2872;
    public static final BitSet FOLLOW_double_literal_in_literal2677;
    public static final BitSet FOLLOW_end_local_directive_in_debug_directive3721;
    public static final BitSet FOLLOW_enum_literal_in_literal2725;
    public static final BitSet FOLLOW_epilogue_directive_in_debug_directive3739;
    public static final BitSet FOLLOW_field_in_smali_file1192;
    public static final BitSet FOLLOW_field_reference_in_enum_literal3045;
    public static final BitSet FOLLOW_field_reference_in_insn_format21c_field4673;
    public static final BitSet FOLLOW_field_reference_in_insn_format21c_field_odex4711;
    public static final BitSet FOLLOW_field_reference_in_insn_format22c_field5133;
    public static final BitSet FOLLOW_field_reference_in_insn_format22c_field_odex5177;
    public static final BitSet FOLLOW_field_reference_in_method_handle_reference3235;
    public static final BitSet FOLLOW_field_reference_in_verification_error_reference3482;
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21ih4933;
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21lh4971;
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format31i5537;
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive6295;
    public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive6389;
    public static final BitSet FOLLOW_fixed_literal_in_insn_array_data_directive6240;
    public static final BitSet FOLLOW_fixed_literal_in_insn_format51l6195;
    public static final BitSet FOLLOW_float_literal_in_fixed_32bit_literal2820;
    public static final BitSet FOLLOW_float_literal_in_fixed_literal2866;
    public static final BitSet FOLLOW_float_literal_in_literal2671;
    public static final BitSet FOLLOW_implements_spec_in_smali_file1169;
    public static final BitSet FOLLOW_insn_array_data_directive_in_instruction4371;
    public static final BitSet FOLLOW_insn_format10t_in_instruction4089;
    public static final BitSet FOLLOW_insn_format10x_in_instruction4095;
    public static final BitSet FOLLOW_insn_format10x_odex_in_instruction4101;
    public static final BitSet FOLLOW_insn_format11n_in_instruction4107;
    public static final BitSet FOLLOW_insn_format11x_in_instruction4113;
    public static final BitSet FOLLOW_insn_format12x_in_instruction4119;
    public static final BitSet FOLLOW_insn_format20bc_in_instruction4125;
    public static final BitSet FOLLOW_insn_format20t_in_instruction4131;
    public static final BitSet FOLLOW_insn_format21c_field_in_instruction4137;
    public static final BitSet FOLLOW_insn_format21c_field_odex_in_instruction4143;
    public static final BitSet FOLLOW_insn_format21c_method_handle_in_instruction4149;
    public static final BitSet FOLLOW_insn_format21c_method_type_in_instruction4155;
    public static final BitSet FOLLOW_insn_format21c_string_in_instruction4161;
    public static final BitSet FOLLOW_insn_format21c_type_in_instruction4167;
    public static final BitSet FOLLOW_insn_format21ih_in_instruction4173;
    public static final BitSet FOLLOW_insn_format21lh_in_instruction4179;
    public static final BitSet FOLLOW_insn_format21s_in_instruction4185;
    public static final BitSet FOLLOW_insn_format21t_in_instruction4191;
    public static final BitSet FOLLOW_insn_format22b_in_instruction4197;
    public static final BitSet FOLLOW_insn_format22c_field_in_instruction4203;
    public static final BitSet FOLLOW_insn_format22c_field_odex_in_instruction4209;
    public static final BitSet FOLLOW_insn_format22c_type_in_instruction4215;
    public static final BitSet FOLLOW_insn_format22cs_field_in_instruction4221;
    public static final BitSet FOLLOW_insn_format22s_in_instruction4227;
    public static final BitSet FOLLOW_insn_format22t_in_instruction4233;
    public static final BitSet FOLLOW_insn_format22x_in_instruction4239;
    public static final BitSet FOLLOW_insn_format23x_in_instruction4245;
    public static final BitSet FOLLOW_insn_format30t_in_instruction4251;
    public static final BitSet FOLLOW_insn_format31c_in_instruction4257;
    public static final BitSet FOLLOW_insn_format31i_in_instruction4263;
    public static final BitSet FOLLOW_insn_format31t_in_instruction4269;
    public static final BitSet FOLLOW_insn_format32x_in_instruction4275;
    public static final BitSet FOLLOW_insn_format35c_call_site_in_instruction4281;
    public static final BitSet FOLLOW_insn_format35c_method_in_instruction4287;
    public static final BitSet FOLLOW_insn_format35c_method_odex_in_instruction4299;
    public static final BitSet FOLLOW_insn_format35c_type_in_instruction4293;
    public static final BitSet FOLLOW_insn_format35mi_method_in_instruction4305;
    public static final BitSet FOLLOW_insn_format35ms_method_in_instruction4311;
    public static final BitSet FOLLOW_insn_format3rc_call_site_in_instruction4317;
    public static final BitSet FOLLOW_insn_format3rc_method_in_instruction4323;
    public static final BitSet FOLLOW_insn_format3rc_method_odex_in_instruction4329;
    public static final BitSet FOLLOW_insn_format3rc_type_in_instruction4335;
    public static final BitSet FOLLOW_insn_format3rmi_method_in_instruction4341;
    public static final BitSet FOLLOW_insn_format3rms_method_in_instruction4347;
    public static final BitSet FOLLOW_insn_format45cc_method_in_instruction4353;
    public static final BitSet FOLLOW_insn_format4rcc_method_in_instruction4359;
    public static final BitSet FOLLOW_insn_format51l_in_instruction4365;
    public static final BitSet FOLLOW_insn_packed_switch_directive_in_instruction4377;
    public static final BitSet FOLLOW_insn_sparse_switch_directive_in_instruction4383;
    public static final BitSet FOLLOW_instruction_format12x_in_insn_format12x4554;
    public static final BitSet FOLLOW_instruction_format22s_in_insn_format22s5292;
    public static final BitSet FOLLOW_instruction_format31i_in_insn_format31i5531;
    public static final BitSet FOLLOW_instruction_format35c_method_in_insn_format35c_method5692;
    public static final BitSet FOLLOW_instruction_in_ordered_method_item1790;
    public static final BitSet FOLLOW_integer_literal_in_fixed_32bit_literal2802;
    public static final BitSet FOLLOW_integer_literal_in_fixed_literal2842;
    public static final BitSet FOLLOW_integer_literal_in_integral_literal2768;
    public static final BitSet FOLLOW_integer_literal_in_literal2653;
    public static final BitSet FOLLOW_integer_literal_in_parsed_integer_literal2750;
    public static final BitSet FOLLOW_integral_literal_in_insn_format11n4490;
    public static final BitSet FOLLOW_integral_literal_in_insn_format21s5009;
    public static final BitSet FOLLOW_integral_literal_in_insn_format22b5089;
    public static final BitSet FOLLOW_integral_literal_in_insn_format22s5302;
    public static final BitSet FOLLOW_integral_literal_in_line_directive3757;
    public static final BitSet FOLLOW_integral_literal_in_registers_directive1820;
    public static final BitSet FOLLOW_integral_literal_in_registers_directive1844;
    public static final BitSet FOLLOW_label_in_ordered_method_item1784;
    public static final BitSet FOLLOW_label_ref_in_catch_directive3504;
    public static final BitSet FOLLOW_label_ref_in_catch_directive3510;
    public static final BitSet FOLLOW_label_ref_in_catch_directive3516;
    public static final BitSet FOLLOW_label_ref_in_catchall_directive3554;
    public static final BitSet FOLLOW_label_ref_in_catchall_directive3560;
    public static final BitSet FOLLOW_label_ref_in_catchall_directive3566;
    public static final BitSet FOLLOW_label_ref_in_insn_format10t4405;
    public static final BitSet FOLLOW_label_ref_in_insn_format20t4637;
    public static final BitSet FOLLOW_label_ref_in_insn_format21t5047;
    public static final BitSet FOLLOW_label_ref_in_insn_format22t5346;
    public static final BitSet FOLLOW_label_ref_in_insn_format30t5464;
    public static final BitSet FOLLOW_label_ref_in_insn_format31t5575;
    public static final BitSet FOLLOW_label_ref_in_insn_packed_switch_directive6301;
    public static final BitSet FOLLOW_label_ref_in_insn_sparse_switch_directive6393;
    public static final BitSet FOLLOW_line_directive_in_debug_directive3709;
    public static final BitSet FOLLOW_literal_in_annotation_element2938;
    public static final BitSet FOLLOW_literal_in_array_literal2897;
    public static final BitSet FOLLOW_literal_in_array_literal2902;
    public static final BitSet FOLLOW_literal_in_call_site_reference3179;
    public static final BitSet FOLLOW_literal_in_field1462;
    public static final BitSet FOLLOW_local_directive_in_debug_directive3715;
    public static final BitSet FOLLOW_member_name_in_field1453;
    public static final BitSet FOLLOW_member_name_in_field_reference3340;
    public static final BitSet FOLLOW_member_name_in_method1604;
    public static final BitSet FOLLOW_member_name_in_method_reference3310;
    public static final BitSet FOLLOW_member_name_in_type_field_method_literal3088;
    public static final BitSet FOLLOW_member_name_in_type_field_method_literal3115;
    public static final BitSet FOLLOW_method_handle_literal_in_literal2731;
    public static final BitSet FOLLOW_method_handle_reference_in_insn_format21c_method_handle4755;
    public static final BitSet FOLLOW_method_handle_reference_in_method_handle_literal3283;
    public static final BitSet FOLLOW_method_in_smali_file1186;
    public static final BitSet FOLLOW_method_prototype_in_call_site_reference3174;
    public static final BitSet FOLLOW_method_prototype_in_insn_format21c_method_type4807;
    public static final BitSet FOLLOW_method_prototype_in_insn_format45cc_method6107;
    public static final BitSet FOLLOW_method_prototype_in_insn_format4rcc_method6155;
    public static final BitSet FOLLOW_method_prototype_in_literal2737;
    public static final BitSet FOLLOW_method_prototype_in_method1606;
    public static final BitSet FOLLOW_method_prototype_in_method_reference3312;
    public static final BitSet FOLLOW_method_prototype_in_type_field_method_literal3117;
    public static final BitSet FOLLOW_method_reference_in_call_site_reference3187;
    public static final BitSet FOLLOW_method_reference_in_insn_format35c_method5702;
    public static final BitSet FOLLOW_method_reference_in_insn_format35c_method_odex5786;
    public static final BitSet FOLLOW_method_reference_in_insn_format3rc_method5926;
    public static final BitSet FOLLOW_method_reference_in_insn_format3rc_method_odex5968;
    public static final BitSet FOLLOW_method_reference_in_insn_format45cc_method6103;
    public static final BitSet FOLLOW_method_reference_in_insn_format4rcc_method6151;
    public static final BitSet FOLLOW_method_reference_in_method_handle_reference3251;
    public static final BitSet FOLLOW_method_reference_in_method_handle_reference3267;
    public static final BitSet FOLLOW_method_reference_in_verification_error_reference3486;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_catch_directive3498;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field1457;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field_reference3344;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4895;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type5227;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5744;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5999;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_local_directive3804;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_param_list2484;
    public static final BitSet FOLLOW_nonvoid_type_descriptor_in_type_field_method_literal3092;
    public static final BitSet FOLLOW_ordered_method_item_in_statements_and_directives1659;
    public static final BitSet FOLLOW_param_list_in_method_prototype2428;
    public static final BitSet FOLLOW_param_list_or_id_in_simple_name2005;
    public static final BitSet FOLLOW_param_list_or_id_primitive_type_in_param_list2477;
    public static final BitSet FOLLOW_parameter_directive_in_statements_and_directives1691;
    public static final BitSet FOLLOW_parsed_integer_literal_in_insn_array_data_directive6228;
    public static final BitSet FOLLOW_prologue_directive_in_debug_directive3733;
    public static final BitSet FOLLOW_reference_type_descriptor_in_field_reference3334;
    public static final BitSet FOLLOW_reference_type_descriptor_in_method_reference3304;
    public static final BitSet FOLLOW_reference_type_descriptor_in_type_field_method_literal3065;
    public static final BitSet FOLLOW_reference_type_descriptor_in_type_field_method_literal3074;
    public static final BitSet FOLLOW_register_list_in_insn_format35c_call_site5654;
    public static final BitSet FOLLOW_register_list_in_insn_format35c_method5696;
    public static final BitSet FOLLOW_register_list_in_insn_format35c_method_odex5780;
    public static final BitSet FOLLOW_register_list_in_insn_format35c_type5738;
    public static final BitSet FOLLOW_register_list_in_insn_format35mi_method5811;
    public static final BitSet FOLLOW_register_list_in_insn_format35ms_method5842;
    public static final BitSet FOLLOW_register_list_in_insn_format3rc_method_odex5962;
    public static final BitSet FOLLOW_register_list_in_insn_format45cc_method6097;
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_call_site5878;
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_method5920;
    public static final BitSet FOLLOW_register_range_in_insn_format3rc_type5993;
    public static final BitSet FOLLOW_register_range_in_insn_format3rmi_method6035;
    public static final BitSet FOLLOW_register_range_in_insn_format3rms_method6066;
    public static final BitSet FOLLOW_register_range_in_insn_format4rcc_method6145;
    public static final BitSet FOLLOW_registers_directive_in_statements_and_directives1667;
    public static final BitSet FOLLOW_restart_local_directive_in_debug_directive3727;
    public static final BitSet FOLLOW_set_in_array_descriptor2497;
    public static final BitSet FOLLOW_simple_name_in_annotation_element2934;
    public static final BitSet FOLLOW_simple_name_in_call_site_reference3166;
    public static final BitSet FOLLOW_simple_name_in_label3367;
    public static final BitSet FOLLOW_simple_name_in_label_ref3388;
    public static final BitSet FOLLOW_simple_name_in_member_name2405;
    public static final BitSet FOLLOW_source_directive_in_debug_directive3745;
    public static final BitSet FOLLOW_source_spec_in_smali_file1178;
    public static final BitSet FOLLOW_statements_and_directives_in_method1608;
    public static final BitSet FOLLOW_subannotation_in_literal2713;
    public static final BitSet FOLLOW_super_spec_in_smali_file1161;
    public static final BitSet FOLLOW_type_descriptor_in_method_prototype2432;
    public static final BitSet FOLLOW_type_field_method_literal_in_literal2719;
    public static final BitSet FOLLOW_verification_error_reference_in_insn_format20bc4598;
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
    protected TreeAdaptor adaptor;
    private boolean allowOdex;
    private int apiLevel;
    protected DFA31 dfa31;
    protected DFA41 dfa41;
    protected DFA43 dfa43;
    private Opcodes opcodes;
    protected Stack<smali_file_scope> smali_file_stack;
    protected Stack<statements_and_directives_scope> statements_and_directives_stack;
    private boolean verboseErrors;
    public static final String[] tokenNames = {"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACCESS_SPEC", "ANNOTATION_DIRECTIVE", "ANNOTATION_VISIBILITY", "ARRAY_DATA_DIRECTIVE", "ARRAY_TYPE_PREFIX", "ARROW", "AT", "BOOL_LITERAL", "BYTE_LITERAL", "CATCHALL_DIRECTIVE", "CATCH_DIRECTIVE", "CHAR_LITERAL", "CLASS_DESCRIPTOR", "CLASS_DIRECTIVE", "CLOSE_BRACE", "CLOSE_PAREN", "COLON", "COMMA", "DOTDOT", "DOUBLE_LITERAL", "DOUBLE_LITERAL_OR_ID", "END_ANNOTATION_DIRECTIVE", "END_ARRAY_DATA_DIRECTIVE", "END_FIELD_DIRECTIVE", "END_LOCAL_DIRECTIVE", "END_METHOD_DIRECTIVE", "END_PACKED_SWITCH_DIRECTIVE", "END_PARAMETER_DIRECTIVE", "END_SPARSE_SWITCH_DIRECTIVE", "END_SUBANNOTATION_DIRECTIVE", "ENUM_DIRECTIVE", "EPILOGUE_DIRECTIVE", "EQUAL", "FIELD_DIRECTIVE", "FIELD_OFFSET", "FLOAT_LITERAL", "FLOAT_LITERAL_OR_ID", "HIDDENAPI_RESTRICTION", "IMPLEMENTS_DIRECTIVE", "INLINE_INDEX", "INSTRUCTION_FORMAT10t", "INSTRUCTION_FORMAT10x", "INSTRUCTION_FORMAT10x_ODEX", "INSTRUCTION_FORMAT11n", "INSTRUCTION_FORMAT11x", "INSTRUCTION_FORMAT12x", "INSTRUCTION_FORMAT12x_OR_ID", "INSTRUCTION_FORMAT20bc", "INSTRUCTION_FORMAT20t", "INSTRUCTION_FORMAT21c_FIELD", "INSTRUCTION_FORMAT21c_FIELD_ODEX", "INSTRUCTION_FORMAT21c_METHOD_HANDLE", "INSTRUCTION_FORMAT21c_METHOD_TYPE", "INSTRUCTION_FORMAT21c_STRING", "INSTRUCTION_FORMAT21c_TYPE", "INSTRUCTION_FORMAT21ih", "INSTRUCTION_FORMAT21lh", "INSTRUCTION_FORMAT21s", "INSTRUCTION_FORMAT21t", "INSTRUCTION_FORMAT22b", "INSTRUCTION_FORMAT22c_FIELD", "INSTRUCTION_FORMAT22c_FIELD_ODEX", "INSTRUCTION_FORMAT22c_TYPE", "INSTRUCTION_FORMAT22cs_FIELD", "INSTRUCTION_FORMAT22s", "INSTRUCTION_FORMAT22s_OR_ID", "INSTRUCTION_FORMAT22t", "INSTRUCTION_FORMAT22x", "INSTRUCTION_FORMAT23x", "INSTRUCTION_FORMAT30t", "INSTRUCTION_FORMAT31c", "INSTRUCTION_FORMAT31i", "INSTRUCTION_FORMAT31i_OR_ID", "INSTRUCTION_FORMAT31t", "INSTRUCTION_FORMAT32x", "INSTRUCTION_FORMAT35c_CALL_SITE", "INSTRUCTION_FORMAT35c_METHOD", "INSTRUCTION_FORMAT35c_METHOD_ODEX", "INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE", "INSTRUCTION_FORMAT35c_TYPE", "INSTRUCTION_FORMAT35mi_METHOD", "INSTRUCTION_FORMAT35ms_METHOD", "INSTRUCTION_FORMAT3rc_CALL_SITE", "INSTRUCTION_FORMAT3rc_METHOD", "INSTRUCTION_FORMAT3rc_METHOD_ODEX", "INSTRUCTION_FORMAT3rc_TYPE", "INSTRUCTION_FORMAT3rmi_METHOD", "INSTRUCTION_FORMAT3rms_METHOD", "INSTRUCTION_FORMAT45cc_METHOD", "INSTRUCTION_FORMAT4rcc_METHOD", "INSTRUCTION_FORMAT51l", "INTEGER_LITERAL", "INVALID_TOKEN", "I_ACCESS_LIST", "I_ACCESS_OR_RESTRICTION_LIST", "I_ANNOTATION", "I_ANNOTATIONS", "I_ANNOTATION_ELEMENT", "I_ARRAY_ELEMENTS", "I_ARRAY_ELEMENT_SIZE", "I_CALL_SITE_EXTRA_ARGUMENTS", "I_CALL_SITE_REFERENCE", "I_CATCH", "I_CATCHALL", "I_CATCHES", "I_CLASS_DEF", "I_ENCODED_ARRAY", "I_ENCODED_ENUM", "I_ENCODED_FIELD", "I_ENCODED_METHOD", "I_ENCODED_METHOD_HANDLE", "I_END_LOCAL", "I_EPILOGUE", "I_FIELD", "I_FIELDS", "I_FIELD_INITIAL_VALUE", "I_FIELD_TYPE", "I_IMPLEMENTS", "I_LABEL", "I_LINE", "I_LOCAL", "I_LOCALS", "I_METHOD", "I_METHODS", "I_METHOD_PROTOTYPE", "I_METHOD_RETURN_TYPE", "I_ORDERED_METHOD_ITEMS", "I_PACKED_SWITCH_ELEMENTS", "I_PACKED_SWITCH_START_KEY", "I_PARAMETER", "I_PARAMETERS", "I_PARAMETER_NOT_SPECIFIED", "I_PROLOGUE", "I_REGISTERS", "I_REGISTER_LIST", "I_REGISTER_RANGE", "I_RESTART_LOCAL", "I_SOURCE", "I_SPARSE_SWITCH_ELEMENTS", "I_STATEMENT_ARRAY_DATA", "I_STATEMENT_FORMAT10t", "I_STATEMENT_FORMAT10x", "I_STATEMENT_FORMAT11n", "I_STATEMENT_FORMAT11x", "I_STATEMENT_FORMAT12x", "I_STATEMENT_FORMAT20bc", "I_STATEMENT_FORMAT20t", "I_STATEMENT_FORMAT21c_FIELD", "I_STATEMENT_FORMAT21c_METHOD_HANDLE", "I_STATEMENT_FORMAT21c_METHOD_TYPE", "I_STATEMENT_FORMAT21c_STRING", "I_STATEMENT_FORMAT21c_TYPE", "I_STATEMENT_FORMAT21ih", "I_STATEMENT_FORMAT21lh", "I_STATEMENT_FORMAT21s", "I_STATEMENT_FORMAT21t", "I_STATEMENT_FORMAT22b", "I_STATEMENT_FORMAT22c_FIELD", "I_STATEMENT_FORMAT22c_TYPE", "I_STATEMENT_FORMAT22s", "I_STATEMENT_FORMAT22t", "I_STATEMENT_FORMAT22x", "I_STATEMENT_FORMAT23x", "I_STATEMENT_FORMAT30t", "I_STATEMENT_FORMAT31c", "I_STATEMENT_FORMAT31i", "I_STATEMENT_FORMAT31t", "I_STATEMENT_FORMAT32x", "I_STATEMENT_FORMAT35c_CALL_SITE", "I_STATEMENT_FORMAT35c_METHOD", "I_STATEMENT_FORMAT35c_TYPE", "I_STATEMENT_FORMAT3rc_CALL_SITE", "I_STATEMENT_FORMAT3rc_METHOD", "I_STATEMENT_FORMAT3rc_TYPE", "I_STATEMENT_FORMAT45cc_METHOD", "I_STATEMENT_FORMAT4rcc_METHOD", "I_STATEMENT_FORMAT51l", "I_STATEMENT_PACKED_SWITCH", "I_STATEMENT_SPARSE_SWITCH", "I_SUBANNOTATION", "I_SUPER", "LINE_COMMENT", "LINE_DIRECTIVE", "LOCALS_DIRECTIVE", "LOCAL_DIRECTIVE", "LONG_LITERAL", "MEMBER_NAME", "METHOD_DIRECTIVE", "METHOD_HANDLE_TYPE_FIELD", "METHOD_HANDLE_TYPE_METHOD", "NEGATIVE_INTEGER_LITERAL", "NULL_LITERAL", "OPEN_BRACE", "OPEN_PAREN", "PACKED_SWITCH_DIRECTIVE", "PARAMETER_DIRECTIVE", "PARAM_LIST_OR_ID_PRIMITIVE_TYPE", "POSITIVE_INTEGER_LITERAL", "PRIMITIVE_TYPE", "PROLOGUE_DIRECTIVE", "REGISTER", "REGISTERS_DIRECTIVE", "RESTART_LOCAL_DIRECTIVE", "SHORT_LITERAL", "SIMPLE_NAME", "SOURCE_DIRECTIVE", "SPARSE_SWITCH_DIRECTIVE", "STRING_LITERAL", "SUBANNOTATION_DIRECTIVE", "SUPER_DIRECTIVE", "VERIFICATION_ERROR_TYPE", "VOID_TYPE", "VTABLE_INDEX", "WHITE_SPACE"};
    static final short[] DFA31_eot = DFA.unpackEncodedString("4\uffff");
    static final short[] DFA31_eof = DFA.unpackEncodedString("4\uffff");
    static final String DFA31_minS = "\u0001\u00041\u0014\u0002\uffff";
    static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars(DFA31_minS);
    static final String DFA31_maxS = "\u0001×\u000bÅ\u0001È%Å\u0002\uffff";
    static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars(DFA31_maxS);
    static final String DFA31_acceptS = "2\uffff\u0001\u0001\u0001\u0002";
    static final short[] DFA31_accept = DFA.unpackEncodedString(DFA31_acceptS);
    static final String DFA31_specialS = "4\uffff}>";
    static final short[] DFA31_special = DFA.unpackEncodedString(DFA31_specialS);

    static {
        String[] strArr = {"\u0001\u0002\u0001\uffff\u0001\u000f\u0004\uffff\u0001\t\f\uffff\u0001\b\u000f\uffff\u0001\u0007\u0001\u0003\u0002\uffff\u0001\u0012\u0001\u0013\u0001\u0014\u0001\uffff\u0001\u0015\u0001\uffff\u0001\u0016\u0002\uffff\u0001\u0017\u0001\u0018\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u001c\u0003\uffff\u0001\u001d\u0001\uffff\u0001\u001e\u0001\u001f\u0001 \u0001!\u0001\uffff\u0001\"\u0001#\u0001\uffff\u0001$\u0003\uffff\u0001%\u0001&\u0001\uffff\u0001'\u0001(\u0001)\u0001*\u0001+\u0001,\u0001-\u0006\uffff\u0001.\u0001/\u00010_\uffff\u00011\u0001\uffff\u0001\u0010\u0001\u0011\u0001\u0006\u0001\n\u0004\uffff\u0001\f\u0001\u0005\u0001\r\u0001\uffff\u0001\u000b\u0003\uffff\u0001\u0001\u0005\uffff\u0001\u0004\u0001\u000e", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013\u0002\uffff\u0001\f", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "\u00012°\uffff\u00013", "", ""};
        DFA31_transitionS = strArr;
        int length = strArr.length;
        DFA31_transition = new short[length][];
        for (int i = 0; i < length; i++) {
            DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i]);
        }
        String[] strArr2 = {"\u0001\u0004\u0001\uffff\u0001\u0011\u0001\uffff\u0001\u0002\u0002\uffff\u0001\u000b\u0004\uffff\u0001\u0001\u0007\uffff\u0001\n\u000f\uffff\u0001\t\u0001\u0005\u0002\uffff\u0001\u0014\u0001\u0015\u0001\u0016\u0001\uffff\u0001\u0017\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u001c\u0001\u001d\u0001\u001e\u0003\uffff\u0001\u001f\u0001\uffff\u0001 \u0001!\u0001\"\u0001#\u0001\uffff\u0001$\u0001%\u0001\uffff\u0001&\u0003\uffff\u0001'\u0001(\u0001\uffff\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001/\u0006\uffff\u00010\u00011\u00012_\uffff\u00013\u0001\uffff\u0001\u0012\u0001\u0013\u0001\b\u0001\f\u0004\uffff\u0001\u000e\u0001\u0007\u0001\u000f\u0001\uffff\u0001\r\u0003\uffff\u0001\u0003\u0005\uffff\u0001\u0006\u0001\u0010", "\u00014\u0001\uffff\u00014\u0001\uffff\u00015\u0003\uffff\u00024\u0005\uffff\u00014\u0007\uffff\u00024\u0005\uffff\u00014\b\uffff34[\uffff\u00034\t\uffff\u00024\u0003\uffff\u00014\u0001\uffff\u00024\u0002\uffff\u00024", "\u00016¹\uffff\u00016", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018\u0002\uffff\u0001\u000e", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "\u00017°\uffff\u00018", "", "\u0001\u0004\u0001\uffff\u0001\u0011\u0004\uffff\u0001\u000b\f\uffff\u0001\n\u000f\uffff\u0001\t\u0001\u0005\u0002\uffff\u0001\u0014\u0001\u0015\u0001\u0016\u0001\uffff\u0001\u0017\u0001\uffff\u0001\u0018\u0002\uffff\u0001\u0019\u0001\u001a\u0001\u001b\u0001\u001c\u0001\u001d\u0001\u001e\u0003\uffff\u0001\u001f\u0001\uffff\u0001 \u0001!\u0001\"\u0001#\u0001\uffff\u0001$\u0001%\u0001\uffff\u0001&\u0003\uffff\u0001'\u0001(\u0001\uffff\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001/\u0006\uffff\u00010\u00011\u00012_\uffff\u00013\u0001\uffff\u0001\u0012\u0001\u0013\u0001\b\u0001\f\u0004\uffff\u0001\u000e\u0001\u0007\u0001\u000f\u0001\uffff\u0001\r\u0003\uffff\u0001\u0003\u0005\uffff\u0001\u0006\u0001\u0010", "\u00015", "", ""};
        DFA41_transitionS = strArr2;
        DFA41_eot = DFA.unpackEncodedString("9\uffff");
        DFA41_eof = DFA.unpackEncodedString("9\uffff");
        DFA41_min = DFA.unpackEncodedStringToUnsignedChars(DFA41_minS);
        DFA41_max = DFA.unpackEncodedStringToUnsignedChars(DFA41_maxS);
        DFA41_accept = DFA.unpackEncodedString(DFA41_acceptS);
        DFA41_special = DFA.unpackEncodedString(DFA41_specialS);
        int length2 = strArr2.length;
        DFA41_transition = new short[length2][];
        for (int i2 = 0; i2 < length2; i2++) {
            DFA41_transition[i2] = DFA.unpackEncodedString(DFA41_transitionS[i2]);
        }
        String[] strArr3 = {"\u0001F\u0001\uffff\u0001\u0001\u0005\uffff\u0002\u0001\u0005\uffff\u0001\u0001\u0007\uffff\u0002\u0001\u0001\uffff\u0001\u0001\u0003\uffff\u0001\u0001\b\uffff3\u0001[\uffff\u0003\u0001\t\uffff\u0002\u0001\u0003\uffff\u0001\u0001\u0001\uffff\u0002\u0001\u0002\uffff\u0002\u0001", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "\u0001\uffff", ""};
        DFA43_transitionS = strArr3;
        DFA43_eot = DFA.unpackEncodedString("H\uffff");
        DFA43_eof = DFA.unpackEncodedString("H\uffff");
        DFA43_min = DFA.unpackEncodedStringToUnsignedChars(DFA43_minS);
        DFA43_max = DFA.unpackEncodedStringToUnsignedChars(DFA43_maxS);
        DFA43_accept = DFA.unpackEncodedString(DFA43_acceptS);
        DFA43_special = DFA.unpackEncodedString(DFA43_specialS);
        int length3 = strArr3.length;
        DFA43_transition = new short[length3][];
        for (int i3 = 0; i3 < length3; i3++) {
            DFA43_transition[i3] = DFA.unpackEncodedString(DFA43_transitionS[i3]);
        }
        FOLLOW_class_spec_in_smali_file1150 = new BitSet(new long[]{4535485595680L, 0, Long.MIN_VALUE, 2228224});
        FOLLOW_super_spec_in_smali_file1161 = new BitSet(new long[]{4535485595680L, 0, Long.MIN_VALUE, 2228224});
        FOLLOW_implements_spec_in_smali_file1169 = new BitSet(new long[]{4535485595680L, 0, Long.MIN_VALUE, 2228224});
        FOLLOW_source_spec_in_smali_file1178 = new BitSet(new long[]{4535485595680L, 0, Long.MIN_VALUE, 2228224});
        FOLLOW_method_in_smali_file1186 = new BitSet(new long[]{4535485595680L, 0, Long.MIN_VALUE, 2228224});
        FOLLOW_field_in_smali_file1192 = new BitSet(new long[]{4535485595680L, 0, Long.MIN_VALUE, 2228224});
        FOLLOW_annotation_in_smali_file1198 = new BitSet(new long[]{4535485595680L, 0, Long.MIN_VALUE, 2228224});
        FOLLOW_EOF_in_smali_file1209 = new BitSet(new long[]{2});
        FOLLOW_CLASS_DIRECTIVE_in_class_spec1296 = new BitSet(new long[]{65552});
        FOLLOW_access_list_in_class_spec1298 = new BitSet(new long[]{65536});
        FOLLOW_CLASS_DESCRIPTOR_in_class_spec1300 = new BitSet(new long[]{2});
        FOLLOW_SUPER_DIRECTIVE_in_super_spec1318 = new BitSet(new long[]{65536});
        FOLLOW_CLASS_DESCRIPTOR_in_super_spec1320 = new BitSet(new long[]{2});
        FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1339 = new BitSet(new long[]{65536});
        FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1341 = new BitSet(new long[]{2});
        FOLLOW_SOURCE_DIRECTIVE_in_source_spec1360 = new BitSet(new long[]{0, 0, 0, 524288});
        FOLLOW_STRING_LITERAL_in_source_spec1362 = new BitSet(new long[]{2});
        FOLLOW_ACCESS_SPEC_in_access_list1381 = new BitSet(new long[]{18});
        FOLLOW_access_or_restriction_in_access_or_restriction_list1416 = new BitSet(new long[]{2199023255570L});
        FOLLOW_FIELD_DIRECTIVE_in_field1449 = new BitSet(new long[]{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_access_or_restriction_list_in_field1451 = new BitSet(new long[]{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_member_name_in_field1453 = new BitSet(new long[]{1048576});
        FOLLOW_COLON_in_field1455 = new BitSet(new long[]{65792, 0, 0, 1024});
        FOLLOW_nonvoid_type_descriptor_in_field1457 = new BitSet(new long[]{68853694498L});
        FOLLOW_EQUAL_in_field1460 = new BitSet(new long[]{5180673957157771600L, 1883222383, 6917529027641081856L, 14260031});
        FOLLOW_literal_in_field1462 = new BitSet(new long[]{134217762});
        FOLLOW_annotation_in_field1475 = new BitSet(new long[]{134217762});
        FOLLOW_END_FIELD_DIRECTIVE_in_field1489 = new BitSet(new long[]{2});
        FOLLOW_METHOD_DIRECTIVE_in_method1600 = new BitSet(new long[]{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_access_or_restriction_list_in_method1602 = new BitSet(new long[]{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_member_name_in_method1604 = new BitSet(new long[]{0, 0, 0, 32});
        FOLLOW_method_prototype_in_method1606 = new BitSet(new long[]{-17557019926368L, 2147483647L, 2017612633061982208L, 420032});
        FOLLOW_statements_and_directives_in_method1608 = new BitSet(new long[]{TagBits.HasTypeVariable});
        FOLLOW_END_METHOD_DIRECTIVE_in_method1614 = new BitSet(new long[]{2});
        FOLLOW_ordered_method_item_in_statements_and_directives1659 = new BitSet(new long[]{-17557556797278L, 2147483647L, 2017612633061982208L, 420032});
        FOLLOW_registers_directive_in_statements_and_directives1667 = new BitSet(new long[]{-17557556797278L, 2147483647L, 2017612633061982208L, 420032});
        FOLLOW_catch_directive_in_statements_and_directives1675 = new BitSet(new long[]{-17557556797278L, 2147483647L, 2017612633061982208L, 420032});
        FOLLOW_catchall_directive_in_statements_and_directives1683 = new BitSet(new long[]{-17557556797278L, 2147483647L, 2017612633061982208L, 420032});
        FOLLOW_parameter_directive_in_statements_and_directives1691 = new BitSet(new long[]{-17557556797278L, 2147483647L, 2017612633061982208L, 420032});
        FOLLOW_annotation_in_statements_and_directives1699 = new BitSet(new long[]{-17557556797278L, 2147483647L, 2017612633061982208L, 420032});
        FOLLOW_label_in_ordered_method_item1784 = new BitSet(new long[]{2});
        FOLLOW_instruction_in_ordered_method_item1790 = new BitSet(new long[]{2});
        FOLLOW_debug_directive_in_ordered_method_item1796 = new BitSet(new long[]{2});
        FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1816 = new BitSet(new long[]{36864, 0, 2305843009213693952L, 33284});
        FOLLOW_integral_literal_in_registers_directive1820 = new BitSet(new long[]{2});
        FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1840 = new BitSet(new long[]{36864, 0, 2305843009213693952L, 33284});
        FOLLOW_integral_literal_in_registers_directive1844 = new BitSet(new long[]{2});
        FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id1876 = new BitSet(new long[]{2, 0, 0, 256});
        FOLLOW_SIMPLE_NAME_in_simple_name1889 = new BitSet(new long[]{2});
        FOLLOW_ACCESS_SPEC_in_simple_name1895 = new BitSet(new long[]{2});
        FOLLOW_HIDDENAPI_RESTRICTION_in_simple_name1906 = new BitSet(new long[]{2});
        FOLLOW_VERIFICATION_ERROR_TYPE_in_simple_name1917 = new BitSet(new long[]{2});
        FOLLOW_POSITIVE_INTEGER_LITERAL_in_simple_name1928 = new BitSet(new long[]{2});
        FOLLOW_NEGATIVE_INTEGER_LITERAL_in_simple_name1939 = new BitSet(new long[]{2});
        FOLLOW_FLOAT_LITERAL_OR_ID_in_simple_name1950 = new BitSet(new long[]{2});
        FOLLOW_DOUBLE_LITERAL_OR_ID_in_simple_name1961 = new BitSet(new long[]{2});
        FOLLOW_BOOL_LITERAL_in_simple_name1972 = new BitSet(new long[]{2});
        FOLLOW_NULL_LITERAL_in_simple_name1983 = new BitSet(new long[]{2});
        FOLLOW_REGISTER_in_simple_name1994 = new BitSet(new long[]{2});
        FOLLOW_param_list_or_id_in_simple_name2005 = new BitSet(new long[]{2});
        FOLLOW_PRIMITIVE_TYPE_in_simple_name2015 = new BitSet(new long[]{2});
        FOLLOW_VOID_TYPE_in_simple_name2026 = new BitSet(new long[]{2});
        FOLLOW_ANNOTATION_VISIBILITY_in_simple_name2037 = new BitSet(new long[]{2});
        FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_simple_name2048 = new BitSet(new long[]{2});
        FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_simple_name2054 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT10t_in_simple_name2060 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT10x_in_simple_name2071 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_simple_name2082 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT11x_in_simple_name2093 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_simple_name2104 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_simple_name2115 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_simple_name2126 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_simple_name2137 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_simple_name2148 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_simple_name2159 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_simple_name2170 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21t_in_simple_name2181 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_simple_name2192 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_simple_name2203 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_simple_name2214 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_simple_name2225 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_simple_name2236 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22t_in_simple_name2247 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT23x_in_simple_name2258 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_simple_name2269 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT31t_in_simple_name2280 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_simple_name2291 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_simple_name2302 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_simple_name2313 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_simple_name2324 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_simple_name2335 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_simple_name2346 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_simple_name2357 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_simple_name2368 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_simple_name2379 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT51l_in_simple_name2390 = new BitSet(new long[]{2});
        FOLLOW_simple_name_in_member_name2405 = new BitSet(new long[]{2});
        FOLLOW_MEMBER_NAME_in_member_name2411 = new BitSet(new long[]{2});
        FOLLOW_OPEN_PAREN_in_method_prototype2426 = new BitSet(new long[]{590080, 0, 0, 1280});
        FOLLOW_param_list_in_method_prototype2428 = new BitSet(new long[]{524288});
        FOLLOW_CLOSE_PAREN_in_method_prototype2430 = new BitSet(new long[]{65792, 0, 0, 8389632});
        FOLLOW_type_descriptor_in_method_prototype2432 = new BitSet(new long[]{2});
        FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id_primitive_type2462 = new BitSet(new long[]{2});
        FOLLOW_param_list_or_id_primitive_type_in_param_list2477 = new BitSet(new long[]{2, 0, 0, 256});
        FOLLOW_nonvoid_type_descriptor_in_param_list2484 = new BitSet(new long[]{65794, 0, 0, 1024});
        FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor2495 = new BitSet(new long[]{65536, 0, 0, 1024});
        FOLLOW_set_in_array_descriptor2497 = new BitSet(new long[]{2});
        FOLLOW_VOID_TYPE_in_type_descriptor2513 = new BitSet(new long[]{2});
        FOLLOW_PRIMITIVE_TYPE_in_type_descriptor2519 = new BitSet(new long[]{2});
        FOLLOW_CLASS_DESCRIPTOR_in_type_descriptor2525 = new BitSet(new long[]{2});
        FOLLOW_array_descriptor_in_type_descriptor2531 = new BitSet(new long[]{2});
        FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor2541 = new BitSet(new long[]{2});
        FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor2547 = new BitSet(new long[]{2});
        FOLLOW_array_descriptor_in_nonvoid_type_descriptor2553 = new BitSet(new long[]{2});
        FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor2563 = new BitSet(new long[]{2});
        FOLLOW_array_descriptor_in_reference_type_descriptor2569 = new BitSet(new long[]{2});
        FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2579 = new BitSet(new long[]{2});
        FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2590 = new BitSet(new long[]{2});
        FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2605 = new BitSet(new long[]{2});
        FOLLOW_FLOAT_LITERAL_in_float_literal2616 = new BitSet(new long[]{2});
        FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2626 = new BitSet(new long[]{2});
        FOLLOW_DOUBLE_LITERAL_in_double_literal2637 = new BitSet(new long[]{2});
        FOLLOW_LONG_LITERAL_in_literal2647 = new BitSet(new long[]{2});
        FOLLOW_integer_literal_in_literal2653 = new BitSet(new long[]{2});
        FOLLOW_SHORT_LITERAL_in_literal2659 = new BitSet(new long[]{2});
        FOLLOW_BYTE_LITERAL_in_literal2665 = new BitSet(new long[]{2});
        FOLLOW_float_literal_in_literal2671 = new BitSet(new long[]{2});
        FOLLOW_double_literal_in_literal2677 = new BitSet(new long[]{2});
        FOLLOW_CHAR_LITERAL_in_literal2683 = new BitSet(new long[]{2});
        FOLLOW_STRING_LITERAL_in_literal2689 = new BitSet(new long[]{2});
        FOLLOW_BOOL_LITERAL_in_literal2695 = new BitSet(new long[]{2});
        FOLLOW_NULL_LITERAL_in_literal2701 = new BitSet(new long[]{2});
        FOLLOW_array_literal_in_literal2707 = new BitSet(new long[]{2});
        FOLLOW_subannotation_in_literal2713 = new BitSet(new long[]{2});
        FOLLOW_type_field_method_literal_in_literal2719 = new BitSet(new long[]{2});
        FOLLOW_enum_literal_in_literal2725 = new BitSet(new long[]{2});
        FOLLOW_method_handle_literal_in_literal2731 = new BitSet(new long[]{2});
        FOLLOW_method_prototype_in_literal2737 = new BitSet(new long[]{2});
        FOLLOW_integer_literal_in_parsed_integer_literal2750 = new BitSet(new long[]{2});
        FOLLOW_LONG_LITERAL_in_integral_literal2762 = new BitSet(new long[]{2});
        FOLLOW_integer_literal_in_integral_literal2768 = new BitSet(new long[]{2});
        FOLLOW_SHORT_LITERAL_in_integral_literal2774 = new BitSet(new long[]{2});
        FOLLOW_CHAR_LITERAL_in_integral_literal2780 = new BitSet(new long[]{2});
        FOLLOW_BYTE_LITERAL_in_integral_literal2786 = new BitSet(new long[]{2});
        FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2796 = new BitSet(new long[]{2});
        FOLLOW_integer_literal_in_fixed_32bit_literal2802 = new BitSet(new long[]{2});
        FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2808 = new BitSet(new long[]{2});
        FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2814 = new BitSet(new long[]{2});
        FOLLOW_float_literal_in_fixed_32bit_literal2820 = new BitSet(new long[]{2});
        FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2826 = new BitSet(new long[]{2});
        FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2832 = new BitSet(new long[]{2});
        FOLLOW_integer_literal_in_fixed_literal2842 = new BitSet(new long[]{2});
        FOLLOW_LONG_LITERAL_in_fixed_literal2848 = new BitSet(new long[]{2});
        FOLLOW_SHORT_LITERAL_in_fixed_literal2854 = new BitSet(new long[]{2});
        FOLLOW_BYTE_LITERAL_in_fixed_literal2860 = new BitSet(new long[]{2});
        FOLLOW_float_literal_in_fixed_literal2866 = new BitSet(new long[]{2});
        FOLLOW_double_literal_in_fixed_literal2872 = new BitSet(new long[]{2});
        FOLLOW_CHAR_LITERAL_in_fixed_literal2878 = new BitSet(new long[]{2});
        FOLLOW_BOOL_LITERAL_in_fixed_literal2884 = new BitSet(new long[]{2});
        FOLLOW_OPEN_BRACE_in_array_literal2894 = new BitSet(new long[]{5180673957158033744L, 1883222383, 6917529027641081856L, 14260031});
        FOLLOW_literal_in_array_literal2897 = new BitSet(new long[]{2359296});
        FOLLOW_COMMA_in_array_literal2900 = new BitSet(new long[]{5180673957157771600L, 1883222383, 6917529027641081856L, 14260031});
        FOLLOW_literal_in_array_literal2902 = new BitSet(new long[]{2359296});
        FOLLOW_CLOSE_BRACE_in_array_literal2910 = new BitSet(new long[]{2});
        FOLLOW_simple_name_in_annotation_element2934 = new BitSet(new long[]{68719476736L});
        FOLLOW_EQUAL_in_annotation_element2936 = new BitSet(new long[]{5180673957157771600L, 1883222383, 6917529027641081856L, 14260031});
        FOLLOW_literal_in_annotation_element2938 = new BitSet(new long[]{2});
        FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2963 = new BitSet(new long[]{64});
        FOLLOW_ANNOTATION_VISIBILITY_in_annotation2965 = new BitSet(new long[]{65536});
        FOLLOW_CLASS_DESCRIPTOR_in_annotation2967 = new BitSet(new long[]{5180673390247151696L, 1883222383, 0, 12654351});
        FOLLOW_annotation_element_in_annotation2973 = new BitSet(new long[]{5180673390247151696L, 1883222383, 0, 12654351});
        FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2976 = new BitSet(new long[]{2});
        FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation3009 = new BitSet(new long[]{65536});
        FOLLOW_CLASS_DESCRIPTOR_in_subannotation3011 = new BitSet(new long[]{5180673398803531856L, 1883222383, 0, 12654351});
        FOLLOW_annotation_element_in_subannotation3013 = new BitSet(new long[]{5180673398803531856L, 1883222383, 0, 12654351});
        FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation3016 = new BitSet(new long[]{2});
        FOLLOW_ENUM_DIRECTIVE_in_enum_literal3043 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_field_reference_in_enum_literal3045 = new BitSet(new long[]{2});
        FOLLOW_reference_type_descriptor_in_type_field_method_literal3065 = new BitSet(new long[]{2});
        FOLLOW_reference_type_descriptor_in_type_field_method_literal3074 = new BitSet(new long[]{512});
        FOLLOW_ARROW_in_type_field_method_literal3076 = new BitSet(new long[]{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_member_name_in_type_field_method_literal3088 = new BitSet(new long[]{1048576});
        FOLLOW_COLON_in_type_field_method_literal3090 = new BitSet(new long[]{65792, 0, 0, 1024});
        FOLLOW_nonvoid_type_descriptor_in_type_field_method_literal3092 = new BitSet(new long[]{2});
        FOLLOW_member_name_in_type_field_method_literal3115 = new BitSet(new long[]{0, 0, 0, 32});
        FOLLOW_method_prototype_in_type_field_method_literal3117 = new BitSet(new long[]{2});
        FOLLOW_PRIMITIVE_TYPE_in_type_field_method_literal3150 = new BitSet(new long[]{2});
        FOLLOW_VOID_TYPE_in_type_field_method_literal3156 = new BitSet(new long[]{2});
        FOLLOW_simple_name_in_call_site_reference3166 = new BitSet(new long[]{0, 0, 0, 32});
        FOLLOW_OPEN_PAREN_in_call_site_reference3168 = new BitSet(new long[]{0, 0, 0, 524288});
        FOLLOW_STRING_LITERAL_in_call_site_reference3170 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_call_site_reference3172 = new BitSet(new long[]{0, 0, 0, 32});
        FOLLOW_method_prototype_in_call_site_reference3174 = new BitSet(new long[]{2621440});
        FOLLOW_COMMA_in_call_site_reference3177 = new BitSet(new long[]{5180673957157771600L, 1883222383, 6917529027641081856L, 14260031});
        FOLLOW_literal_in_call_site_reference3179 = new BitSet(new long[]{2621440});
        FOLLOW_CLOSE_PAREN_in_call_site_reference3183 = new BitSet(new long[]{1024});
        FOLLOW_AT_in_call_site_reference3185 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_call_site_reference3187 = new BitSet(new long[]{2});
        FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_method_handle_reference3231 = new BitSet(new long[]{1024});
        FOLLOW_AT_in_method_handle_reference3233 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_field_reference_in_method_handle_reference3235 = new BitSet(new long[]{2});
        FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_method_handle_reference3247 = new BitSet(new long[]{1024});
        FOLLOW_AT_in_method_handle_reference3249 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_method_handle_reference3251 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_method_handle_reference3263 = new BitSet(new long[]{1024});
        FOLLOW_AT_in_method_handle_reference3265 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_method_handle_reference3267 = new BitSet(new long[]{2});
        FOLLOW_method_handle_reference_in_method_handle_literal3283 = new BitSet(new long[]{2});
        FOLLOW_reference_type_descriptor_in_method_reference3304 = new BitSet(new long[]{512});
        FOLLOW_ARROW_in_method_reference3306 = new BitSet(new long[]{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_member_name_in_method_reference3310 = new BitSet(new long[]{0, 0, 0, 32});
        FOLLOW_method_prototype_in_method_reference3312 = new BitSet(new long[]{2});
        FOLLOW_reference_type_descriptor_in_field_reference3334 = new BitSet(new long[]{512});
        FOLLOW_ARROW_in_field_reference3336 = new BitSet(new long[]{5180673390213597264L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_member_name_in_field_reference3340 = new BitSet(new long[]{1048576});
        FOLLOW_COLON_in_field_reference3342 = new BitSet(new long[]{65792, 0, 0, 1024});
        FOLLOW_nonvoid_type_descriptor_in_field_reference3344 = new BitSet(new long[]{2});
        FOLLOW_COLON_in_label3365 = new BitSet(new long[]{5180673390213597264L, 1883222383, 0, 12654351});
        FOLLOW_simple_name_in_label3367 = new BitSet(new long[]{2});
        FOLLOW_COLON_in_label_ref3386 = new BitSet(new long[]{5180673390213597264L, 1883222383, 0, 12654351});
        FOLLOW_simple_name_in_label_ref3388 = new BitSet(new long[]{2});
        FOLLOW_REGISTER_in_register_list3402 = new BitSet(new long[]{2097154});
        FOLLOW_COMMA_in_register_list3405 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_register_list3407 = new BitSet(new long[]{2097154});
        FOLLOW_REGISTER_in_register_range3442 = new BitSet(new long[]{4194306});
        FOLLOW_DOTDOT_in_register_range3445 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_register_range3449 = new BitSet(new long[]{2});
        FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3478 = new BitSet(new long[]{2});
        FOLLOW_field_reference_in_verification_error_reference3482 = new BitSet(new long[]{2});
        FOLLOW_method_reference_in_verification_error_reference3486 = new BitSet(new long[]{2});
        FOLLOW_CATCH_DIRECTIVE_in_catch_directive3496 = new BitSet(new long[]{65792, 0, 0, 1024});
        FOLLOW_nonvoid_type_descriptor_in_catch_directive3498 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_catch_directive3500 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_catch_directive3504 = new BitSet(new long[]{4194304});
        FOLLOW_DOTDOT_in_catch_directive3506 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_catch_directive3510 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_catch_directive3512 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_catch_directive3516 = new BitSet(new long[]{2});
        FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3548 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_catchall_directive3550 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_catchall_directive3554 = new BitSet(new long[]{4194304});
        FOLLOW_DOTDOT_in_catchall_directive3556 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_catchall_directive3560 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_catchall_directive3562 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_catchall_directive3566 = new BitSet(new long[]{2});
        FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3605 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_parameter_directive3607 = new BitSet(new long[]{2149580834L});
        FOLLOW_COMMA_in_parameter_directive3610 = new BitSet(new long[]{0, 0, 0, 524288});
        FOLLOW_STRING_LITERAL_in_parameter_directive3612 = new BitSet(new long[]{2147483682L});
        FOLLOW_annotation_in_parameter_directive3623 = new BitSet(new long[]{2147483682L});
        FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3636 = new BitSet(new long[]{2});
        FOLLOW_line_directive_in_debug_directive3709 = new BitSet(new long[]{2});
        FOLLOW_local_directive_in_debug_directive3715 = new BitSet(new long[]{2});
        FOLLOW_end_local_directive_in_debug_directive3721 = new BitSet(new long[]{2});
        FOLLOW_restart_local_directive_in_debug_directive3727 = new BitSet(new long[]{2});
        FOLLOW_prologue_directive_in_debug_directive3733 = new BitSet(new long[]{2});
        FOLLOW_epilogue_directive_in_debug_directive3739 = new BitSet(new long[]{2});
        FOLLOW_source_directive_in_debug_directive3745 = new BitSet(new long[]{2});
        FOLLOW_LINE_DIRECTIVE_in_line_directive3755 = new BitSet(new long[]{36864, 0, 2305843009213693952L, 33284});
        FOLLOW_integral_literal_in_line_directive3757 = new BitSet(new long[]{2});
        FOLLOW_LOCAL_DIRECTIVE_in_local_directive3780 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_local_directive3782 = new BitSet(new long[]{2097154});
        FOLLOW_COMMA_in_local_directive3785 = new BitSet(new long[]{0, 0, 0, 524296});
        FOLLOW_NULL_LITERAL_in_local_directive3788 = new BitSet(new long[]{1048576});
        FOLLOW_STRING_LITERAL_in_local_directive3794 = new BitSet(new long[]{1048576});
        FOLLOW_COLON_in_local_directive3797 = new BitSet(new long[]{65792, 0, 0, 8389632});
        FOLLOW_VOID_TYPE_in_local_directive3800 = new BitSet(new long[]{2097154});
        FOLLOW_nonvoid_type_descriptor_in_local_directive3804 = new BitSet(new long[]{2097154});
        FOLLOW_COMMA_in_local_directive3838 = new BitSet(new long[]{0, 0, 0, 524288});
        FOLLOW_STRING_LITERAL_in_local_directive3842 = new BitSet(new long[]{2});
        FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3884 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_end_local_directive3886 = new BitSet(new long[]{2});
        FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3909 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_restart_local_directive3911 = new BitSet(new long[]{2});
        FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3934 = new BitSet(new long[]{2});
        FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3955 = new BitSet(new long[]{2});
        FOLLOW_SOURCE_DIRECTIVE_in_source_directive3976 = new BitSet(new long[]{2, 0, 0, 524288});
        FOLLOW_STRING_LITERAL_in_source_directive3978 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x4003 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x4009 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s4024 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s4030 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i4045 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i4051 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_instruction_format35c_method4068 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_instruction_format35c_method4074 = new BitSet(new long[]{2});
        FOLLOW_insn_format10t_in_instruction4089 = new BitSet(new long[]{2});
        FOLLOW_insn_format10x_in_instruction4095 = new BitSet(new long[]{2});
        FOLLOW_insn_format10x_odex_in_instruction4101 = new BitSet(new long[]{2});
        FOLLOW_insn_format11n_in_instruction4107 = new BitSet(new long[]{2});
        FOLLOW_insn_format11x_in_instruction4113 = new BitSet(new long[]{2});
        FOLLOW_insn_format12x_in_instruction4119 = new BitSet(new long[]{2});
        FOLLOW_insn_format20bc_in_instruction4125 = new BitSet(new long[]{2});
        FOLLOW_insn_format20t_in_instruction4131 = new BitSet(new long[]{2});
        FOLLOW_insn_format21c_field_in_instruction4137 = new BitSet(new long[]{2});
        FOLLOW_insn_format21c_field_odex_in_instruction4143 = new BitSet(new long[]{2});
        FOLLOW_insn_format21c_method_handle_in_instruction4149 = new BitSet(new long[]{2});
        FOLLOW_insn_format21c_method_type_in_instruction4155 = new BitSet(new long[]{2});
        FOLLOW_insn_format21c_string_in_instruction4161 = new BitSet(new long[]{2});
        FOLLOW_insn_format21c_type_in_instruction4167 = new BitSet(new long[]{2});
        FOLLOW_insn_format21ih_in_instruction4173 = new BitSet(new long[]{2});
        FOLLOW_insn_format21lh_in_instruction4179 = new BitSet(new long[]{2});
        FOLLOW_insn_format21s_in_instruction4185 = new BitSet(new long[]{2});
        FOLLOW_insn_format21t_in_instruction4191 = new BitSet(new long[]{2});
        FOLLOW_insn_format22b_in_instruction4197 = new BitSet(new long[]{2});
        FOLLOW_insn_format22c_field_in_instruction4203 = new BitSet(new long[]{2});
        FOLLOW_insn_format22c_field_odex_in_instruction4209 = new BitSet(new long[]{2});
        FOLLOW_insn_format22c_type_in_instruction4215 = new BitSet(new long[]{2});
        FOLLOW_insn_format22cs_field_in_instruction4221 = new BitSet(new long[]{2});
        FOLLOW_insn_format22s_in_instruction4227 = new BitSet(new long[]{2});
        FOLLOW_insn_format22t_in_instruction4233 = new BitSet(new long[]{2});
        FOLLOW_insn_format22x_in_instruction4239 = new BitSet(new long[]{2});
        FOLLOW_insn_format23x_in_instruction4245 = new BitSet(new long[]{2});
        FOLLOW_insn_format30t_in_instruction4251 = new BitSet(new long[]{2});
        FOLLOW_insn_format31c_in_instruction4257 = new BitSet(new long[]{2});
        FOLLOW_insn_format31i_in_instruction4263 = new BitSet(new long[]{2});
        FOLLOW_insn_format31t_in_instruction4269 = new BitSet(new long[]{2});
        FOLLOW_insn_format32x_in_instruction4275 = new BitSet(new long[]{2});
        FOLLOW_insn_format35c_call_site_in_instruction4281 = new BitSet(new long[]{2});
        FOLLOW_insn_format35c_method_in_instruction4287 = new BitSet(new long[]{2});
        FOLLOW_insn_format35c_type_in_instruction4293 = new BitSet(new long[]{2});
        FOLLOW_insn_format35c_method_odex_in_instruction4299 = new BitSet(new long[]{2});
        FOLLOW_insn_format35mi_method_in_instruction4305 = new BitSet(new long[]{2});
        FOLLOW_insn_format35ms_method_in_instruction4311 = new BitSet(new long[]{2});
        FOLLOW_insn_format3rc_call_site_in_instruction4317 = new BitSet(new long[]{2});
        FOLLOW_insn_format3rc_method_in_instruction4323 = new BitSet(new long[]{2});
        FOLLOW_insn_format3rc_method_odex_in_instruction4329 = new BitSet(new long[]{2});
        FOLLOW_insn_format3rc_type_in_instruction4335 = new BitSet(new long[]{2});
        FOLLOW_insn_format3rmi_method_in_instruction4341 = new BitSet(new long[]{2});
        FOLLOW_insn_format3rms_method_in_instruction4347 = new BitSet(new long[]{2});
        FOLLOW_insn_format45cc_method_in_instruction4353 = new BitSet(new long[]{2});
        FOLLOW_insn_format4rcc_method_in_instruction4359 = new BitSet(new long[]{2});
        FOLLOW_insn_format51l_in_instruction4365 = new BitSet(new long[]{2});
        FOLLOW_insn_array_data_directive_in_instruction4371 = new BitSet(new long[]{2});
        FOLLOW_insn_packed_switch_directive_in_instruction4377 = new BitSet(new long[]{2});
        FOLLOW_insn_sparse_switch_directive_in_instruction4383 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4403 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_insn_format10t4405 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4435 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4463 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4484 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format11n4486 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format11n4488 = new BitSet(new long[]{36864, 0, 2305843009213693952L, 33284});
        FOLLOW_integral_literal_in_insn_format11n4490 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4522 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format11x4524 = new BitSet(new long[]{2});
        FOLLOW_instruction_format12x_in_insn_format12x4554 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format12x4556 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format12x4558 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format12x4560 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4592 = new BitSet(new long[]{0, 0, 0, 4194304});
        FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4594 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format20bc4596 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_verification_error_reference_in_insn_format20bc4598 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4635 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_insn_format20t4637 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4667 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21c_field4669 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21c_field4671 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_field_reference_in_insn_format21c_field4673 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4705 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21c_field_odex4707 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21c_field_odex4709 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_field_reference_in_insn_format21c_field_odex4711 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle4749 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21c_method_handle4751 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21c_method_handle4753 = new BitSet(new long[]{0, 262144, 0, 3});
        FOLLOW_method_handle_reference_in_insn_format21c_method_handle4755 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type4801 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21c_method_type4803 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21c_method_type4805 = new BitSet(new long[]{0, 0, 0, 32});
        FOLLOW_method_prototype_in_insn_format21c_method_type4807 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4851 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21c_string4853 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21c_string4855 = new BitSet(new long[]{0, 0, 0, 524288});
        FOLLOW_STRING_LITERAL_in_insn_format21c_string4857 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4889 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21c_type4891 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21c_type4893 = new BitSet(new long[]{65792, 0, 0, 1024});
        FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4895 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4927 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21ih4929 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21ih4931 = new BitSet(new long[]{1649267480576L, 0, 2305843009213693952L, 33284});
        FOLLOW_fixed_32bit_literal_in_insn_format21ih4933 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4965 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21lh4967 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21lh4969 = new BitSet(new long[]{1649267480576L, 0, 2305843009213693952L, 33284});
        FOLLOW_fixed_32bit_literal_in_insn_format21lh4971 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s5003 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21s5005 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21s5007 = new BitSet(new long[]{36864, 0, 2305843009213693952L, 33284});
        FOLLOW_integral_literal_in_insn_format21s5009 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t5041 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format21t5043 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format21t5045 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_insn_format21t5047 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b5079 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22b5081 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22b5083 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22b5085 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22b5087 = new BitSet(new long[]{36864, 0, 2305843009213693952L, 33284});
        FOLLOW_integral_literal_in_insn_format22b5089 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field5123 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22c_field5125 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22c_field5127 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22c_field5129 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22c_field5131 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_field_reference_in_insn_format22c_field5133 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex5167 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22c_field_odex5169 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22c_field_odex5171 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22c_field_odex5173 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22c_field_odex5175 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_field_reference_in_insn_format22c_field_odex5177 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type5217 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22c_type5219 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22c_type5221 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22c_type5223 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22c_type5225 = new BitSet(new long[]{65792, 0, 0, 1024});
        FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type5227 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field5261 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22cs_field5263 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22cs_field5265 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22cs_field5267 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22cs_field5269 = new BitSet(new long[]{274877906944L});
        FOLLOW_FIELD_OFFSET_in_insn_format22cs_field5271 = new BitSet(new long[]{2});
        FOLLOW_instruction_format22s_in_insn_format22s5292 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22s5294 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22s5296 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22s5298 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22s5300 = new BitSet(new long[]{36864, 0, 2305843009213693952L, 33284});
        FOLLOW_integral_literal_in_insn_format22s5302 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t5336 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22t5338 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22t5340 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22t5342 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22t5344 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_insn_format22t5346 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x5380 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22x5382 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format22x5384 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format22x5386 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x5418 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format23x5420 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format23x5422 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format23x5424 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format23x5426 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format23x5428 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t5462 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_insn_format30t5464 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5494 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format31c5496 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format31c5498 = new BitSet(new long[]{0, 0, 0, 524288});
        FOLLOW_STRING_LITERAL_in_insn_format31c5500 = new BitSet(new long[]{2});
        FOLLOW_instruction_format31i_in_insn_format31i5531 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format31i5533 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format31i5535 = new BitSet(new long[]{1649267480576L, 0, 2305843009213693952L, 33284});
        FOLLOW_fixed_32bit_literal_in_insn_format31i5537 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5569 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format31t5571 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format31t5573 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_insn_format31t5575 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5607 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format32x5609 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format32x5611 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format32x5613 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site5650 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format35c_call_site5652 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_list_in_insn_format35c_call_site5654 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format35c_call_site5656 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format35c_call_site5658 = new BitSet(new long[]{5180673390213597264L, 1883222383, 0, 12654351});
        FOLLOW_call_site_reference_in_insn_format35c_call_site5660 = new BitSet(new long[]{2});
        FOLLOW_instruction_format35c_method_in_insn_format35c_method5692 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format35c_method5694 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_list_in_insn_format35c_method5696 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format35c_method5698 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format35c_method5700 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_insn_format35c_method5702 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5734 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format35c_type5736 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_list_in_insn_format35c_type5738 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format35c_type5740 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format35c_type5742 = new BitSet(new long[]{65792, 0, 0, 1024});
        FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5744 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5776 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5778 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_list_in_insn_format35c_method_odex5780 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5782 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format35c_method_odex5784 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_insn_format35c_method_odex5786 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5807 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format35mi_method5809 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_list_in_insn_format35mi_method5811 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5813 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format35mi_method5815 = new BitSet(new long[]{8796093022208L});
        FOLLOW_INLINE_INDEX_in_insn_format35mi_method5817 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5838 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format35ms_method5840 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_list_in_insn_format35ms_method5842 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5844 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format35ms_method5846 = new BitSet(new long[]{0, 0, 0, TagBits.HasUnresolvedTypeVariables});
        FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5848 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site5874 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format3rc_call_site5876 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_range_in_insn_format3rc_call_site5878 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format3rc_call_site5880 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format3rc_call_site5882 = new BitSet(new long[]{5180673390213597264L, 1883222383, 0, 12654351});
        FOLLOW_call_site_reference_in_insn_format3rc_call_site5884 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5916 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format3rc_method5918 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_range_in_insn_format3rc_method5920 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5922 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format3rc_method5924 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_insn_format3rc_method5926 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5958 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5960 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_list_in_insn_format3rc_method_odex5962 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5964 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format3rc_method_odex5966 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_insn_format3rc_method_odex5968 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5989 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format3rc_type5991 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_range_in_insn_format3rc_type5993 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5995 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format3rc_type5997 = new BitSet(new long[]{65792, 0, 0, 1024});
        FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5999 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method6031 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format3rmi_method6033 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_range_in_insn_format3rmi_method6035 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method6037 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format3rmi_method6039 = new BitSet(new long[]{8796093022208L});
        FOLLOW_INLINE_INDEX_in_insn_format3rmi_method6041 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method6062 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format3rms_method6064 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_range_in_insn_format3rms_method6066 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format3rms_method6068 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format3rms_method6070 = new BitSet(new long[]{0, 0, 0, TagBits.HasUnresolvedTypeVariables});
        FOLLOW_VTABLE_INDEX_in_insn_format3rms_method6072 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method6093 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format45cc_method6095 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_list_in_insn_format45cc_method6097 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format45cc_method6099 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format45cc_method6101 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_insn_format45cc_method6103 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format45cc_method6105 = new BitSet(new long[]{0, 0, 0, 32});
        FOLLOW_method_prototype_in_insn_format45cc_method6107 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method6141 = new BitSet(new long[]{0, 0, 0, 16});
        FOLLOW_OPEN_BRACE_in_insn_format4rcc_method6143 = new BitSet(new long[]{262144, 0, 0, 4096});
        FOLLOW_register_range_in_insn_format4rcc_method6145 = new BitSet(new long[]{262144});
        FOLLOW_CLOSE_BRACE_in_insn_format4rcc_method6147 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format4rcc_method6149 = new BitSet(new long[]{5180673390213663056L, 1883222383, 4611686018427387904L, 12654351});
        FOLLOW_method_reference_in_insn_format4rcc_method6151 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format4rcc_method6153 = new BitSet(new long[]{0, 0, 0, 32});
        FOLLOW_method_prototype_in_insn_format4rcc_method6155 = new BitSet(new long[]{2});
        FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l6189 = new BitSet(new long[]{0, 0, 0, 4096});
        FOLLOW_REGISTER_in_insn_format51l6191 = new BitSet(new long[]{2097152});
        FOLLOW_COMMA_in_insn_format51l6193 = new BitSet(new long[]{1649292646400L, 0, 2305843009213693952L, 33284});
        FOLLOW_fixed_literal_in_insn_format51l6195 = new BitSet(new long[]{2});
        FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6222 = new BitSet(new long[]{0, 0, 0, 516});
        FOLLOW_parsed_integer_literal_in_insn_array_data_directive6228 = new BitSet(new long[]{1649359755264L, 0, 2305843009213693952L, 33284});
        FOLLOW_fixed_literal_in_insn_array_data_directive6240 = new BitSet(new long[]{1649359755264L, 0, 2305843009213693952L, 33284});
        FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6243 = new BitSet(new long[]{2});
        FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6289 = new BitSet(new long[]{1649267480576L, 0, 2305843009213693952L, 33284});
        FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive6295 = new BitSet(new long[]{1074790400});
        FOLLOW_label_ref_in_insn_packed_switch_directive6301 = new BitSet(new long[]{1074790400});
        FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6308 = new BitSet(new long[]{2});
        FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6382 = new BitSet(new long[]{1653562447872L, 0, 2305843009213693952L, 33284});
        FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive6389 = new BitSet(new long[]{512});
        FOLLOW_ARROW_in_insn_sparse_switch_directive6391 = new BitSet(new long[]{1048576});
        FOLLOW_label_ref_in_insn_sparse_switch_directive6393 = new BitSet(new long[]{1653562447872L, 0, 2305843009213693952L, 33284});
        FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6401 = new BitSet(new long[]{2});
    }

    public Parser[] getDelegates() {
        return new Parser[0];
    }

    public smaliParser(TokenStream tokenStream) {
        this(tokenStream, new RecognizerSharedState());
    }

    public smaliParser(TokenStream tokenStream, RecognizerSharedState recognizerSharedState) {
        super(tokenStream, recognizerSharedState);
        this.adaptor = new CommonTreeAdaptor();
        this.verboseErrors = false;
        this.allowOdex = false;
        this.apiLevel = 15;
        this.opcodes = Opcodes.forApi(15);
        this.smali_file_stack = new Stack<>();
        this.statements_and_directives_stack = new Stack<>();
        this.dfa31 = new DFA31(this);
        this.dfa41 = new DFA41(this);
        this.dfa43 = new DFA43(this);
    }

    public void setTreeAdaptor(TreeAdaptor treeAdaptor) {
        this.adaptor = treeAdaptor;
    }

    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String getGrammarFileName() {
        return "smaliParser.g";
    }

    public void setVerboseErrors(boolean z) {
        this.verboseErrors = z;
    }

    public void setAllowOdex(boolean z) {
        this.allowOdex = z;
    }

    public void setApiLevel(int i) {
        this.opcodes = Opcodes.forApi(i);
        this.apiLevel = i;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String getErrorMessage(RecognitionException recognitionException, String[] strArr) {
        String errorMessage;
        if (this.verboseErrors) {
            List<String> ruleInvocationStack = getRuleInvocationStack(recognitionException, getClass().getName());
            if (recognitionException instanceof NoViableAltException) {
                NoViableAltException noViableAltException = (NoViableAltException) recognitionException;
                errorMessage = " no viable alt; token=" + getTokenErrorDisplay(recognitionException.token) + " (decision=" + noViableAltException.decisionNumber + " state " + noViableAltException.stateNumber + ") decision=<<" + noViableAltException.grammarDecisionDescription + ">>";
            } else {
                errorMessage = super.getErrorMessage(recognitionException, strArr);
            }
            return ruleInvocationStack + " " + errorMessage;
        }
        return super.getErrorMessage(recognitionException, strArr);
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String getTokenErrorDisplay(Token token) {
        String str;
        String strReplaceAll;
        if (!this.verboseErrors) {
            String text = token.getText();
            if (text == null) {
                if (token.getType() == -1) {
                    text = "<EOF>";
                } else {
                    text = "<" + tokenNames[token.getType()] + ">";
                }
            }
            return FormatHelper.QUOTE + text.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t") + FormatHelper.QUOTE;
        }
        CommonToken commonToken = (CommonToken) token;
        if (token.getChannel() <= 0) {
            str = "";
        } else {
            str = ",channel=" + token.getChannel();
        }
        String text2 = token.getText();
        if (text2 != null) {
            strReplaceAll = text2.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t");
        } else {
            strReplaceAll = "<no text>";
        }
        return "[@" + token.getTokenIndex() + "," + commonToken.getStartIndex() + ":" + commonToken.getStopIndex() + "='" + strReplaceAll + "',<" + tokenNames[token.getType()] + ">" + str + "," + token.getLine() + ":" + token.getCharPositionInLine() + "]";
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String getErrorHeader(RecognitionException recognitionException) {
        return getSourceName() + "[" + recognitionException.line + "," + recognitionException.charPositionInLine + "]";
    }

    private CommonTree buildTree(int i, String str, List<CommonTree> list) {
        CommonTree commonTree = new CommonTree(new CommonToken(i, str));
        Iterator<CommonTree> it = list.iterator();
        while (it.hasNext()) {
            commonTree.addChild(it.next());
        }
        return commonTree;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0091  */
    private CommonToken getParamListSubToken(CommonToken commonToken, String str, int i) {
        int i2;
        CommonToken commonToken2 = new CommonToken(commonToken);
        commonToken2.setStartIndex(commonToken.getStartIndex() + i);
        char cCharAt = str.charAt(i);
        if (cCharAt == 'F') {
            commonToken2.setType(202);
            commonToken2.setText(str.substring(i, i + 1));
            commonToken2.setStopIndex(commonToken.getStartIndex() + i);
        } else if (cCharAt == 'L') {
            int i3 = i;
            while (true) {
                i2 = i3 + 1;
                if (str.charAt(i2) == ';') {
                    break;
                }
                i3 = i2;
            }
            commonToken2.setType(16);
            commonToken2.setText(str.substring(i, i3 + 2));
            commonToken2.setStopIndex(commonToken.getStartIndex() + i2);
        } else if (cCharAt == 'S' || cCharAt == 'I' || cCharAt == 'J' || cCharAt == 'Z') {
            commonToken2.setType(202);
            commonToken2.setText(str.substring(i, i + 1));
            commonToken2.setStopIndex(commonToken.getStartIndex() + i);
        } else if (cCharAt != '[') {
            switch (cCharAt) {
                case 'B':
                case 'C':
                case 'D':
                    commonToken2.setType(202);
                    commonToken2.setText(str.substring(i, i + 1));
                    commonToken2.setStopIndex(commonToken.getStartIndex() + i);
                    break;
                default:
                    throw new RuntimeException(String.format("Invalid character '%c' in param list \"%s\" at position %d", Character.valueOf(str.charAt(i)), str, Integer.valueOf(i)));
            }
        } else {
            int i4 = i;
            do {
                i4++;
            } while (str.charAt(i4) == '[');
            commonToken2.setType(8);
            commonToken2.setText(str.substring(i, i4));
            commonToken2.setStopIndex((commonToken.getStartIndex() + i4) - 1);
        }
        return commonToken2;
    }

    private CommonTree parseParamList(CommonToken commonToken) {
        String text = commonToken.getText();
        CommonTree commonTree = new CommonTree();
        commonToken.getStartIndex();
        int length = 0;
        while (length < text.length()) {
            CommonToken paramListSubToken = getParamListSubToken(commonToken, text, length);
            commonTree.addChild(new CommonTree(paramListSubToken));
            length += paramListSubToken.getText().length();
        }
        if (commonTree.getChildCount() == 0) {
            return null;
        }
        return commonTree;
    }

    private void throwOdexedInstructionException(IntStream intStream, String str) throws OdexedInstructionException {
        throw new OdexedInstructionException(intStream, str);
    }

    protected static class smali_file_scope {
        List<CommonTree> classAnnotations;
        boolean hasClassSpec;
        boolean hasSourceSpec;
        boolean hasSuperSpec;

        protected smali_file_scope() {
        }
    }

    public static class smali_file_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final smali_file_return smali_file() throws RecognitionException {
        char c;
        this.smali_file_stack.push(new smali_file_scope());
        smali_file_return smali_file_returnVar = new smali_file_return();
        smali_file_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token EOF");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule class_spec");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule method");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = new RewriteRuleSubtreeStream(this.adaptor, "rule field");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream5 = new RewriteRuleSubtreeStream(this.adaptor, "rule super_spec");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream6 = new RewriteRuleSubtreeStream(this.adaptor, "rule implements_spec");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream7 = new RewriteRuleSubtreeStream(this.adaptor, "rule source_spec");
        smali_file_scope smali_file_scopeVarPeek = this.smali_file_stack.peek();
        smali_file_scope smali_file_scopeVarPeek2 = this.smali_file_stack.peek();
        this.smali_file_stack.peek().hasSourceSpec = false;
        smali_file_scopeVarPeek2.hasSuperSpec = false;
        smali_file_scopeVarPeek.hasClassSpec = false;
        this.smali_file_stack.peek().classAnnotations = new ArrayList();
        int i = 0;
        class_spec_return class_spec_returnVarClass_spec = null;
        while (true) {
            try {
                try {
                    int iLA = this.input.LA(1);
                    if (iLA == 17 && !this.smali_file_stack.peek().hasClassSpec) {
                        c = 1;
                    } else if (iLA == 213 && !this.smali_file_stack.peek().hasSuperSpec) {
                        c = 2;
                    } else if (iLA == 42) {
                        c = 3;
                    } else if (iLA == 209 && !this.smali_file_stack.peek().hasSourceSpec) {
                        c = 4;
                    } else if (iLA == 191) {
                        c = 5;
                    } else if (iLA == 37) {
                        c = 6;
                    } else {
                        c = iLA == 5 ? (char) 7 : '\b';
                    }
                    switch (c) {
                        case 1:
                            if (this.smali_file_stack.peek().hasClassSpec) {
                                throw new FailedPredicateException(this.input, "smali_file", "!$smali_file::hasClassSpec");
                            }
                            pushFollow(FOLLOW_class_spec_in_smali_file1150);
                            class_spec_returnVarClass_spec = class_spec();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream.add(class_spec_returnVarClass_spec.getTree());
                            this.smali_file_stack.peek().hasClassSpec = true;
                            continue;
                            i++;
                            break;
                            break;
                        case 2:
                            if (this.smali_file_stack.peek().hasSuperSpec) {
                                throw new FailedPredicateException(this.input, "smali_file", "!$smali_file::hasSuperSpec");
                            }
                            pushFollow(FOLLOW_super_spec_in_smali_file1161);
                            super_spec_return super_spec_returnVarSuper_spec = super_spec();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream5.add(super_spec_returnVarSuper_spec.getTree());
                            this.smali_file_stack.peek().hasSuperSpec = true;
                            continue;
                            i++;
                            break;
                            break;
                        case 3:
                            pushFollow(FOLLOW_implements_spec_in_smali_file1169);
                            implements_spec_return implements_spec_returnVarImplements_spec = implements_spec();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream6.add(implements_spec_returnVarImplements_spec.getTree());
                            continue;
                            i++;
                            break;
                        case 4:
                            if (this.smali_file_stack.peek().hasSourceSpec) {
                                throw new FailedPredicateException(this.input, "smali_file", "!$smali_file::hasSourceSpec");
                            }
                            pushFollow(FOLLOW_source_spec_in_smali_file1178);
                            source_spec_return source_spec_returnVarSource_spec = source_spec();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream7.add(source_spec_returnVarSource_spec.getTree());
                            this.smali_file_stack.peek().hasSourceSpec = true;
                            continue;
                            i++;
                            break;
                            break;
                        case 5:
                            pushFollow(FOLLOW_method_in_smali_file1186);
                            method_return method_returnVarMethod = method();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream3.add(method_returnVarMethod.getTree());
                            continue;
                            i++;
                            break;
                        case 6:
                            pushFollow(FOLLOW_field_in_smali_file1192);
                            field_return field_returnVarField = field();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream4.add(field_returnVarField.getTree());
                            continue;
                            i++;
                            break;
                        case 7:
                            pushFollow(FOLLOW_annotation_in_smali_file1198);
                            annotation_return annotation_returnVarAnnotation = annotation();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream2.add(annotation_returnVarAnnotation.getTree());
                            this.smali_file_stack.peek().classAnnotations.add(annotation_returnVarAnnotation != null ? (CommonTree) annotation_returnVarAnnotation.getTree() : null);
                            continue;
                            i++;
                            break;
                        default:
                            if (i < 1) {
                                throw new EarlyExitException(1, this.input);
                            }
                            rewriteRuleTokenStream.add((Token) match(this.input, -1, FOLLOW_EOF_in_smali_file1209));
                            if (!this.smali_file_stack.peek().hasClassSpec) {
                                throw new SemanticException(this.input, "The file must contain a .class directive", new Object[0]);
                            }
                            if (!this.smali_file_stack.peek().hasSuperSpec) {
                                if (!(class_spec_returnVarClass_spec != null ? class_spec_returnVarClass_spec.className : null).equals(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR)) {
                                    throw new SemanticException(this.input, "The file must contain a .super directive", new Object[0]);
                                }
                            }
                            smali_file_returnVar.tree = null;
                            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", smali_file_returnVar.getTree());
                            CommonTree commonTree = (CommonTree) this.adaptor.nil();
                            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                            TreeAdaptor treeAdaptor = this.adaptor;
                            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(109, "I_CLASS_DEF"), commonTree2);
                            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
                            if (rewriteRuleSubtreeStream5.hasNext()) {
                                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream5.nextTree());
                            }
                            rewriteRuleSubtreeStream5.reset();
                            while (rewriteRuleSubtreeStream6.hasNext()) {
                                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream6.nextTree());
                            }
                            rewriteRuleSubtreeStream6.reset();
                            if (rewriteRuleSubtreeStream7.hasNext()) {
                                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream7.nextTree());
                            }
                            rewriteRuleSubtreeStream7.reset();
                            CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
                            TreeAdaptor treeAdaptor2 = this.adaptor;
                            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(127, "I_METHODS"), commonTree4);
                            while (rewriteRuleSubtreeStream3.hasNext()) {
                                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream3.nextTree());
                            }
                            rewriteRuleSubtreeStream3.reset();
                            this.adaptor.addChild(commonTree3, commonTree5);
                            CommonTree commonTree6 = (CommonTree) this.adaptor.nil();
                            TreeAdaptor treeAdaptor3 = this.adaptor;
                            CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(118, "I_FIELDS"), commonTree6);
                            while (rewriteRuleSubtreeStream4.hasNext()) {
                                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream4.nextTree());
                            }
                            rewriteRuleSubtreeStream4.reset();
                            this.adaptor.addChild(commonTree3, commonTree7);
                            this.adaptor.addChild(commonTree3, buildTree(100, "I_ANNOTATIONS", this.smali_file_stack.peek().classAnnotations));
                            this.adaptor.addChild(commonTree, commonTree3);
                            smali_file_returnVar.tree = commonTree;
                            smali_file_returnVar.stop = this.input.LT(-1);
                            smali_file_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
                            this.adaptor.setTokenBoundaries(smali_file_returnVar.tree, smali_file_returnVar.start, smali_file_returnVar.stop);
                            break;
                            break;
                    }
                } catch (RecognitionException e) {
                    reportError(e);
                    recover(this.input, e);
                    smali_file_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, smali_file_returnVar.start, this.input.LT(-1), e);
                }
            } catch (Throwable th) {
                this.smali_file_stack.pop();
                throw th;
            }
        }
        this.smali_file_stack.pop();
        return smali_file_returnVar;
    }

    public static class class_spec_return extends ParserRuleReturnScope {
        public String className;
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final class_spec_return class_spec() throws RecognitionException {
        class_spec_return class_spec_returnVar = new class_spec_return();
        class_spec_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule access_list");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 17, FOLLOW_CLASS_DIRECTIVE_in_class_spec1296));
            pushFollow(FOLLOW_access_list_in_class_spec1298);
            access_list_return access_list_returnVarAccess_list = access_list();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(access_list_returnVarAccess_list.getTree());
            Token token = (Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_class_spec1300);
            rewriteRuleTokenStream.add(token);
            class_spec_returnVar.className = token != null ? token.getText() : null;
            class_spec_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", class_spec_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            this.adaptor.addChild(commonTree, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree());
            class_spec_returnVar.tree = commonTree;
            class_spec_returnVar.stop = this.input.LT(-1);
            class_spec_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(class_spec_returnVar.tree, class_spec_returnVar.start, class_spec_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            class_spec_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, class_spec_returnVar.start, this.input.LT(-1), e);
        }
        return class_spec_returnVar;
    }

    public static class super_spec_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final super_spec_return super_spec() throws RecognitionException {
        super_spec_return super_spec_returnVar = new super_spec_return();
        super_spec_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
        try {
            new RewriteRuleTokenStream(this.adaptor, "token SUPER_DIRECTIVE").add((Token) match(this.input, 213, FOLLOW_SUPER_DIRECTIVE_in_super_spec1318));
            rewriteRuleTokenStream.add((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_super_spec1320));
            super_spec_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", super_spec_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(184, super_spec_returnVar.start, "I_SUPER"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            super_spec_returnVar.tree = commonTree;
            super_spec_returnVar.stop = this.input.LT(-1);
            super_spec_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(super_spec_returnVar.tree, super_spec_returnVar.start, super_spec_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            super_spec_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, super_spec_returnVar.start, this.input.LT(-1), e);
        }
        return super_spec_returnVar;
    }

    public static class implements_spec_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final implements_spec_return implements_spec() throws RecognitionException {
        implements_spec_return implements_spec_returnVar = new implements_spec_return();
        implements_spec_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token IMPLEMENTS_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 42, FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1339));
            rewriteRuleTokenStream2.add((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1341));
            implements_spec_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", implements_spec_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(121, implements_spec_returnVar.start, "I_IMPLEMENTS"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            implements_spec_returnVar.tree = commonTree;
            implements_spec_returnVar.stop = this.input.LT(-1);
            implements_spec_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(implements_spec_returnVar.tree, implements_spec_returnVar.start, implements_spec_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            implements_spec_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, implements_spec_returnVar.start, this.input.LT(-1), e);
        }
        return implements_spec_returnVar;
    }

    public static class source_spec_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final source_spec_return source_spec() throws RecognitionException {
        source_spec_return source_spec_returnVar = new source_spec_return();
        source_spec_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token SOURCE_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 209, FOLLOW_SOURCE_DIRECTIVE_in_source_spec1360));
            rewriteRuleTokenStream2.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_source_spec1362));
            source_spec_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", source_spec_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(141, source_spec_returnVar.start, "I_SOURCE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            source_spec_returnVar.tree = commonTree;
            source_spec_returnVar.stop = this.input.LT(-1);
            source_spec_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(source_spec_returnVar.tree, source_spec_returnVar.start, source_spec_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            source_spec_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, source_spec_returnVar.start, this.input.LT(-1), e);
        }
        return source_spec_returnVar;
    }

    public static class access_list_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final access_list_return access_list() throws RecognitionException {
        access_list_return access_list_returnVar = new access_list_return();
        access_list_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token ACCESS_SPEC");
        while (this.input.LA(1) == 4) {
            try {
                rewriteRuleTokenStream.add((Token) match(this.input, 4, FOLLOW_ACCESS_SPEC_in_access_list1381));
            } catch (RecognitionException e) {
                reportError(e);
                recover(this.input, e);
                access_list_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, access_list_returnVar.start, this.input.LT(-1), e);
            }
        }
        access_list_returnVar.tree = null;
        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", access_list_returnVar.getTree());
        CommonTree commonTree = (CommonTree) this.adaptor.nil();
        CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
        TreeAdaptor treeAdaptor = this.adaptor;
        CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(97, access_list_returnVar.start, "I_ACCESS_LIST"), commonTree2);
        while (rewriteRuleTokenStream.hasNext()) {
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
        }
        rewriteRuleTokenStream.reset();
        this.adaptor.addChild(commonTree, commonTree3);
        access_list_returnVar.tree = commonTree;
        access_list_returnVar.stop = this.input.LT(-1);
        access_list_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
        this.adaptor.setTokenBoundaries(access_list_returnVar.tree, access_list_returnVar.start, access_list_returnVar.stop);
        return access_list_returnVar;
    }

    public static class access_or_restriction_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final access_or_restriction_return access_or_restriction() throws RecognitionException {
        access_or_restriction_return access_or_restriction_returnVar = new access_or_restriction_return();
        access_or_restriction_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token tokenLT = this.input.LT(1);
            if (this.input.LA(1) != 4 && this.input.LA(1) != 41) {
                throw new MismatchedSetException(null, this.input);
            }
            this.input.consume();
            TreeAdaptor treeAdaptor = this.adaptor;
            treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(tokenLT));
            this.state.errorRecovery = false;
            access_or_restriction_returnVar.stop = this.input.LT(-1);
            access_or_restriction_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(access_or_restriction_returnVar.tree, access_or_restriction_returnVar.start, access_or_restriction_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            access_or_restriction_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, access_or_restriction_returnVar.start, this.input.LT(-1), e);
        }
        return access_or_restriction_returnVar;
    }

    public static class access_or_restriction_list_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final access_or_restriction_list_return access_or_restriction_list() throws RecognitionException {
        int iLA;
        access_or_restriction_list_return access_or_restriction_list_returnVar = new access_or_restriction_list_return();
        int i = 1;
        access_or_restriction_list_returnVar.start = this.input.LT(1);
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule access_or_restriction");
        while (true) {
            try {
                int iLA2 = this.input.LA(i);
                if (iLA2 == 4) {
                    int iLA3 = this.input.LA(2);
                    if (iLA3 != 4 && iLA3 != 6 && iLA3 != 11 && iLA3 != 24 && ((iLA3 < 40 || iLA3 > 41) && ((iLA3 < 44 || iLA3 > 46) && iLA3 != 48 && iLA3 != 50 && ((iLA3 < 53 || iLA3 > 58) && iLA3 != 62 && ((iLA3 < 64 || iLA3 > 67) && ((iLA3 < 69 || iLA3 > 70) && iLA3 != 72 && ((iLA3 < 76 || iLA3 > 77) && ((iLA3 < 79 || iLA3 > 85) && ((iLA3 < 92 || iLA3 > 94) && iLA3 != 190 && ((iLA3 < 192 || iLA3 > 195) && ((iLA3 < 200 || iLA3 > 202) && iLA3 != 204 && iLA3 != 208 && (iLA3 < 214 || iLA3 > 215)))))))))))) {
                        break;
                    }
                    pushFollow(FOLLOW_access_or_restriction_in_access_or_restriction_list1416);
                    access_or_restriction_return access_or_restriction_returnVarAccess_or_restriction = access_or_restriction();
                    this.state._fsp--;
                    rewriteRuleSubtreeStream.add(access_or_restriction_returnVarAccess_or_restriction.getTree());
                    i = 1;
                } else {
                    if (iLA2 != 41 || ((iLA = this.input.LA(2)) != 4 && iLA != 6 && iLA != 11 && iLA != 24 && ((iLA < 40 || iLA > 41) && ((iLA < 44 || iLA > 46) && iLA != 48 && iLA != 50 && ((iLA < 53 || iLA > 58) && iLA != 62 && ((iLA < 64 || iLA > 67) && ((iLA < 69 || iLA > 70) && iLA != 72 && ((iLA < 76 || iLA > 77) && ((iLA < 79 || iLA > 85) && ((iLA < 92 || iLA > 94) && iLA != 190 && ((iLA < 192 || iLA > 195) && ((iLA < 200 || iLA > 202) && iLA != 204 && iLA != 208 && (iLA < 214 || iLA > 215))))))))))))) {
                        break;
                    }
                    pushFollow(FOLLOW_access_or_restriction_in_access_or_restriction_list1416);
                    access_or_restriction_return access_or_restriction_returnVarAccess_or_restriction2 = access_or_restriction();
                    this.state._fsp--;
                    rewriteRuleSubtreeStream.add(access_or_restriction_returnVarAccess_or_restriction2.getTree());
                    i = 1;
                }
            } catch (RecognitionException e) {
                reportError(e);
                recover(this.input, e);
                access_or_restriction_list_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, access_or_restriction_list_returnVar.start, this.input.LT(-1), e);
            }
        }
        access_or_restriction_list_returnVar.tree = null;
        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", access_or_restriction_list_returnVar.getTree());
        CommonTree commonTree = (CommonTree) this.adaptor.nil();
        CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
        TreeAdaptor treeAdaptor = this.adaptor;
        CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(98, access_or_restriction_list_returnVar.start, "I_ACCESS_AND_RESTRICTION_LIST"), commonTree2);
        while (rewriteRuleSubtreeStream.hasNext()) {
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
        }
        rewriteRuleSubtreeStream.reset();
        this.adaptor.addChild(commonTree, commonTree3);
        access_or_restriction_list_returnVar.tree = commonTree;
        access_or_restriction_list_returnVar.stop = this.input.LT(-1);
        access_or_restriction_list_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
        this.adaptor.setTokenBoundaries(access_or_restriction_list_returnVar.tree, access_or_restriction_list_returnVar.start, access_or_restriction_list_returnVar.stop);
        return access_or_restriction_list_returnVar;
    }

    public static class field_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final field_return field() throws RecognitionException {
        char c;
        CommonTree commonTree;
        CommonTree commonTree2;
        field_return field_returnVar = new field_return();
        field_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token END_FIELD_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token EQUAL");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token FIELD_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule access_or_restriction_list");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream5 = new RewriteRuleSubtreeStream(this.adaptor, "rule literal");
        ArrayList arrayList = new ArrayList();
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 37, FOLLOW_FIELD_DIRECTIVE_in_field1449));
            pushFollow(FOLLOW_access_or_restriction_list_in_field1451);
            access_or_restriction_list_return access_or_restriction_list_returnVarAccess_or_restriction_list = access_or_restriction_list();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(access_or_restriction_list_returnVarAccess_or_restriction_list.getTree());
            pushFollow(FOLLOW_member_name_in_field1453);
            member_name_return member_name_returnVarMember_name = member_name();
            this.state._fsp--;
            rewriteRuleSubtreeStream4.add(member_name_returnVarMember_name.getTree());
            rewriteRuleTokenStream4.add((Token) match(this.input, 20, FOLLOW_COLON_in_field1455));
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_field1457);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            rewriteRuleSubtreeStream3.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
            if (this.input.LA(1) == 36) {
                rewriteRuleTokenStream2.add((Token) match(this.input, 36, FOLLOW_EQUAL_in_field1460));
                pushFollow(FOLLOW_literal_in_field1462);
                literal_return literal_returnVarLiteral = literal();
                this.state._fsp--;
                rewriteRuleSubtreeStream5.add(literal_returnVarLiteral.getTree());
            }
            while (this.input.LA(1) == 5) {
                this.input.LA(2);
                if (this.input.LA(1) != 5) {
                    break;
                }
                if (this.input.LA(1) != 5) {
                    throw new FailedPredicateException(this.input, "field", "input.LA(1) == ANNOTATION_DIRECTIVE");
                }
                pushFollow(FOLLOW_annotation_in_field1475);
                annotation_return annotation_returnVarAnnotation = annotation();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(annotation_returnVarAnnotation.getTree());
                arrayList.add(annotation_returnVarAnnotation != null ? (CommonTree) annotation_returnVarAnnotation.getTree() : null);
            }
            int iLA = this.input.LA(1);
            if (iLA == 27) {
                c = 1;
            } else {
                if (iLA != -1 && iLA != 5 && iLA != 17 && iLA != 37 && iLA != 42 && iLA != 191 && iLA != 209 && iLA != 213) {
                    throw new NoViableAltException("", 6, 0, this.input);
                }
                c = 2;
            }
            if (c == 1) {
                rewriteRuleTokenStream.add((Token) match(this.input, 27, FOLLOW_END_FIELD_DIRECTIVE_in_field1489));
                field_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", field_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                CommonTree commonTree3 = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor = this.adaptor;
                CommonTree commonTree4 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(117, field_returnVar.start, "I_FIELD"), commonTree3);
                this.adaptor.addChild(commonTree4, rewriteRuleSubtreeStream4.nextTree());
                this.adaptor.addChild(commonTree4, rewriteRuleSubtreeStream2.nextTree());
                CommonTree commonTree5 = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor2 = this.adaptor;
                CommonTree commonTree6 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(120, "I_FIELD_TYPE"), commonTree5);
                this.adaptor.addChild(commonTree6, rewriteRuleSubtreeStream3.nextTree());
                this.adaptor.addChild(commonTree4, commonTree6);
                if (rewriteRuleSubtreeStream5.hasNext()) {
                    CommonTree commonTree7 = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor3 = this.adaptor;
                    CommonTree commonTree8 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(119, "I_FIELD_INITIAL_VALUE"), commonTree7);
                    this.adaptor.addChild(commonTree8, rewriteRuleSubtreeStream5.nextTree());
                    this.adaptor.addChild(commonTree4, commonTree8);
                }
                rewriteRuleSubtreeStream5.reset();
                CommonTree commonTree9 = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor4 = this.adaptor;
                CommonTree commonTree10 = (CommonTree) treeAdaptor4.becomeRoot((CommonTree) treeAdaptor4.create(100, "I_ANNOTATIONS"), commonTree9);
                while (rewriteRuleSubtreeStream.hasNext()) {
                    this.adaptor.addChild(commonTree10, rewriteRuleSubtreeStream.nextTree());
                }
                rewriteRuleSubtreeStream.reset();
                this.adaptor.addChild(commonTree4, commonTree10);
                this.adaptor.addChild(commonTree, commonTree4);
                field_returnVar.tree = commonTree;
            } else {
                if (c != 2) {
                    commonTree2 = null;
                } else {
                    this.smali_file_stack.peek().classAnnotations.addAll(arrayList);
                    field_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", field_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    CommonTree commonTree11 = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor5 = this.adaptor;
                    CommonTree commonTree12 = (CommonTree) treeAdaptor5.becomeRoot((CommonTree) treeAdaptor5.create(117, field_returnVar.start, "I_FIELD"), commonTree11);
                    this.adaptor.addChild(commonTree12, rewriteRuleSubtreeStream4.nextTree());
                    this.adaptor.addChild(commonTree12, rewriteRuleSubtreeStream2.nextTree());
                    CommonTree commonTree13 = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor6 = this.adaptor;
                    CommonTree commonTree14 = (CommonTree) treeAdaptor6.becomeRoot((CommonTree) treeAdaptor6.create(120, "I_FIELD_TYPE"), commonTree13);
                    this.adaptor.addChild(commonTree14, rewriteRuleSubtreeStream3.nextTree());
                    this.adaptor.addChild(commonTree12, commonTree14);
                    if (rewriteRuleSubtreeStream5.hasNext()) {
                        CommonTree commonTree15 = (CommonTree) this.adaptor.nil();
                        TreeAdaptor treeAdaptor7 = this.adaptor;
                        CommonTree commonTree16 = (CommonTree) treeAdaptor7.becomeRoot((CommonTree) treeAdaptor7.create(119, "I_FIELD_INITIAL_VALUE"), commonTree15);
                        this.adaptor.addChild(commonTree16, rewriteRuleSubtreeStream5.nextTree());
                        this.adaptor.addChild(commonTree12, commonTree16);
                    }
                    rewriteRuleSubtreeStream5.reset();
                    CommonTree commonTree17 = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor8 = this.adaptor;
                    this.adaptor.addChild(commonTree12, (CommonTree) treeAdaptor8.becomeRoot((CommonTree) treeAdaptor8.create(100, "I_ANNOTATIONS"), commonTree17));
                    this.adaptor.addChild(commonTree, commonTree12);
                    field_returnVar.tree = commonTree;
                }
                field_returnVar.stop = this.input.LT(-1);
                field_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree2);
                this.adaptor.setTokenBoundaries(field_returnVar.tree, field_returnVar.start, field_returnVar.stop);
                return field_returnVar;
            }
            commonTree2 = commonTree;
            field_returnVar.stop = this.input.LT(-1);
            field_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree2);
            this.adaptor.setTokenBoundaries(field_returnVar.tree, field_returnVar.start, field_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            field_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, field_returnVar.start, this.input.LT(-1), e);
        }
        return field_returnVar;
    }

    public static class method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final method_return method() throws RecognitionException {
        method_return method_returnVar = new method_return();
        method_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token END_METHOD_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token METHOD_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule access_or_restriction_list");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = new RewriteRuleSubtreeStream(this.adaptor, "rule statements_and_directives");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 191, FOLLOW_METHOD_DIRECTIVE_in_method1600));
            pushFollow(FOLLOW_access_or_restriction_list_in_method1602);
            access_or_restriction_list_return access_or_restriction_list_returnVarAccess_or_restriction_list = access_or_restriction_list();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(access_or_restriction_list_returnVarAccess_or_restriction_list.getTree());
            pushFollow(FOLLOW_member_name_in_method1604);
            member_name_return member_name_returnVarMember_name = member_name();
            this.state._fsp--;
            rewriteRuleSubtreeStream3.add(member_name_returnVarMember_name.getTree());
            pushFollow(FOLLOW_method_prototype_in_method1606);
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(method_prototype_returnVarMethod_prototype.getTree());
            pushFollow(FOLLOW_statements_and_directives_in_method1608);
            statements_and_directives_return statements_and_directives_returnVarStatements_and_directives = statements_and_directives();
            this.state._fsp--;
            rewriteRuleSubtreeStream4.add(statements_and_directives_returnVarStatements_and_directives.getTree());
            rewriteRuleTokenStream.add((Token) match(this.input, 29, FOLLOW_END_METHOD_DIRECTIVE_in_method1614));
            method_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(126, method_returnVar.start, "I_METHOD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream4.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            method_returnVar.tree = commonTree;
            method_returnVar.stop = this.input.LT(-1);
            method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(method_returnVar.tree, method_returnVar.start, method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, method_returnVar.start, this.input.LT(-1), e);
        }
        return method_returnVar;
    }

    protected static class statements_and_directives_scope {
        boolean hasRegistersDirective;
        List<CommonTree> methodAnnotations;

        protected statements_and_directives_scope() {
        }
    }

    public static class statements_and_directives_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:38:0x00b3  */
    public final statements_and_directives_return statements_and_directives() throws RecognitionException {
        this.statements_and_directives_stack.push(new statements_and_directives_scope());
        statements_and_directives_return statements_and_directives_returnVar = new statements_and_directives_return();
        statements_and_directives_returnVar.start = this.input.LT(1);
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule catchall_directive");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule registers_directive");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = new RewriteRuleSubtreeStream(this.adaptor, "rule catch_directive");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream5 = new RewriteRuleSubtreeStream(this.adaptor, "rule ordered_method_item");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream6 = new RewriteRuleSubtreeStream(this.adaptor, "rule parameter_directive");
        try {
            try {
                this.statements_and_directives_stack.peek().hasRegistersDirective = false;
                this.statements_and_directives_stack.peek().methodAnnotations = new ArrayList();
                while (true) {
                    int iLA = this.input.LA(1);
                    char c = 5;
                    if (iLA == 5) {
                        c = 6;
                    } else if (iLA == 7 || iLA == 20 || iLA == 28 || iLA == 35 || iLA == 203) {
                        c = 1;
                    } else if (iLA == 13) {
                        c = 4;
                    } else if (iLA == 14) {
                        c = 3;
                    } else if (iLA != 198) {
                        if (iLA != 199) {
                            if (iLA != 205) {
                                if (iLA != 206 && iLA != 209 && iLA != 210) {
                                    switch (iLA) {
                                        default:
                                            switch (iLA) {
                                                case 186:
                                                case 188:
                                                    break;
                                                case 187:
                                                    c = 2;
                                                    break;
                                                default:
                                                    c = 7;
                                                    break;
                                            }
                                        case 44:
                                        case 45:
                                        case 46:
                                        case 47:
                                        case 48:
                                        case 49:
                                        case 50:
                                        case 51:
                                        case 52:
                                        case 53:
                                        case 54:
                                        case 55:
                                        case 56:
                                        case 57:
                                        case 58:
                                        case 59:
                                        case 60:
                                        case 61:
                                        case 62:
                                        case 63:
                                        case 64:
                                        case 65:
                                        case 66:
                                        case 67:
                                        case 68:
                                        case 69:
                                        case 70:
                                        case 71:
                                        case 72:
                                        case 73:
                                        case 74:
                                        case 75:
                                        case 76:
                                        case 77:
                                        case 78:
                                        case 79:
                                        case 80:
                                        case 81:
                                        case 82:
                                        case 83:
                                        case 84:
                                        case 85:
                                        case 86:
                                        case 87:
                                        case 88:
                                        case 89:
                                        case 90:
                                        case 91:
                                        case 92:
                                        case 93:
                                        case 94:
                                            c = 1;
                                            break;
                                    }
                                } else {
                                    c = 1;
                                }
                            } else {
                                c = 2;
                            }
                        }
                    } else {
                        c = 1;
                    }
                    switch (c) {
                        case 1:
                            pushFollow(FOLLOW_ordered_method_item_in_statements_and_directives1659);
                            ordered_method_item_return ordered_method_item_returnVarOrdered_method_item = ordered_method_item();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream5.add(ordered_method_item_returnVarOrdered_method_item.getTree());
                            continue;
                        case 2:
                            pushFollow(FOLLOW_registers_directive_in_statements_and_directives1667);
                            registers_directive_return registers_directive_returnVarRegisters_directive = registers_directive();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream3.add(registers_directive_returnVarRegisters_directive.getTree());
                            continue;
                        case 3:
                            pushFollow(FOLLOW_catch_directive_in_statements_and_directives1675);
                            catch_directive_return catch_directive_returnVarCatch_directive = catch_directive();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream4.add(catch_directive_returnVarCatch_directive.getTree());
                            continue;
                        case 4:
                            pushFollow(FOLLOW_catchall_directive_in_statements_and_directives1683);
                            catchall_directive_return catchall_directive_returnVarCatchall_directive = catchall_directive();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream2.add(catchall_directive_returnVarCatchall_directive.getTree());
                            continue;
                        case 5:
                            pushFollow(FOLLOW_parameter_directive_in_statements_and_directives1691);
                            parameter_directive_return parameter_directive_returnVarParameter_directive = parameter_directive();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream6.add(parameter_directive_returnVarParameter_directive.getTree());
                            continue;
                        case 6:
                            pushFollow(FOLLOW_annotation_in_statements_and_directives1699);
                            annotation_return annotation_returnVarAnnotation = annotation();
                            this.state._fsp--;
                            rewriteRuleSubtreeStream.add(annotation_returnVarAnnotation.getTree());
                            this.statements_and_directives_stack.peek().methodAnnotations.add(annotation_returnVarAnnotation != null ? (CommonTree) annotation_returnVarAnnotation.getTree() : null);
                            continue;
                        default:
                            statements_and_directives_returnVar.tree = null;
                            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", statements_and_directives_returnVar.getTree());
                            CommonTree commonTree = (CommonTree) this.adaptor.nil();
                            if (rewriteRuleSubtreeStream3.hasNext()) {
                                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream3.nextTree());
                            }
                            rewriteRuleSubtreeStream3.reset();
                            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                            TreeAdaptor treeAdaptor = this.adaptor;
                            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(130, "I_ORDERED_METHOD_ITEMS"), commonTree2);
                            while (rewriteRuleSubtreeStream5.hasNext()) {
                                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream5.nextTree());
                            }
                            rewriteRuleSubtreeStream5.reset();
                            this.adaptor.addChild(commonTree, commonTree3);
                            CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
                            TreeAdaptor treeAdaptor2 = this.adaptor;
                            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(108, "I_CATCHES"), commonTree4);
                            while (rewriteRuleSubtreeStream4.hasNext()) {
                                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream4.nextTree());
                            }
                            rewriteRuleSubtreeStream4.reset();
                            while (rewriteRuleSubtreeStream2.hasNext()) {
                                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream2.nextTree());
                            }
                            rewriteRuleSubtreeStream2.reset();
                            this.adaptor.addChild(commonTree, commonTree5);
                            CommonTree commonTree6 = (CommonTree) this.adaptor.nil();
                            TreeAdaptor treeAdaptor3 = this.adaptor;
                            CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(134, "I_PARAMETERS"), commonTree6);
                            while (rewriteRuleSubtreeStream6.hasNext()) {
                                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream6.nextTree());
                            }
                            rewriteRuleSubtreeStream6.reset();
                            this.adaptor.addChild(commonTree, commonTree7);
                            this.adaptor.addChild(commonTree, buildTree(100, "I_ANNOTATIONS", this.statements_and_directives_stack.peek().methodAnnotations));
                            statements_and_directives_returnVar.tree = commonTree;
                            statements_and_directives_returnVar.stop = this.input.LT(-1);
                            statements_and_directives_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
                            this.adaptor.setTokenBoundaries(statements_and_directives_returnVar.tree, statements_and_directives_returnVar.start, statements_and_directives_returnVar.stop);
                            return statements_and_directives_returnVar;
                    }
                    this.statements_and_directives_stack.pop();
                }
            } catch (RecognitionException e) {
                reportError(e);
                recover(this.input, e);
                statements_and_directives_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, statements_and_directives_returnVar.start, this.input.LT(-1), e);
            }
        } finally {
            this.statements_and_directives_stack.pop();
        }
    }

    public static class ordered_method_item_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0052  */
    public final ordered_method_item_return ordered_method_item() throws RecognitionException {
        char c;
        CommonTree commonTree;
        CommonTree commonTree2;
        ordered_method_item_return ordered_method_item_returnVar = new ordered_method_item_return();
        ordered_method_item_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 7) {
                c = 2;
            } else if (iLA == 20) {
                c = 1;
            } else if (iLA == 28 || iLA == 35 || iLA == 186 || iLA == 188) {
                c = 3;
            } else if (iLA == 198) {
                c = 2;
            } else if (iLA == 203 || iLA == 206 || iLA == 209) {
                c = 3;
            } else if (iLA != 210) {
                switch (iLA) {
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                    case 68:
                    case 69:
                    case 70:
                    case 71:
                    case 72:
                    case 73:
                    case 74:
                    case 75:
                    case 76:
                    case 77:
                    case 78:
                    case 79:
                    case 80:
                    case 81:
                    case 82:
                    case 83:
                    case 84:
                    case 85:
                    case 86:
                    case 87:
                    case 88:
                    case 89:
                    case 90:
                    case 91:
                    case 92:
                    case 93:
                    case 94:
                        c = 2;
                        break;
                    default:
                        throw new NoViableAltException("", 8, 0, this.input);
                }
            } else {
                c = 2;
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil();
                pushFollow(FOLLOW_label_in_ordered_method_item1784);
                label_return label_returnVarLabel = label();
                this.state._fsp--;
                this.adaptor.addChild(commonTree, label_returnVarLabel.getTree());
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil();
                pushFollow(FOLLOW_instruction_in_ordered_method_item1790);
                instruction_return instruction_returnVarInstruction = instruction();
                this.state._fsp--;
                this.adaptor.addChild(commonTree, instruction_returnVarInstruction.getTree());
            } else {
                if (c != 3) {
                    commonTree2 = null;
                } else {
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_debug_directive_in_ordered_method_item1796);
                    debug_directive_return debug_directive_returnVarDebug_directive = debug_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, debug_directive_returnVarDebug_directive.getTree());
                }
                ordered_method_item_returnVar.stop = this.input.LT(-1);
                ordered_method_item_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree2);
                this.adaptor.setTokenBoundaries(ordered_method_item_returnVar.tree, ordered_method_item_returnVar.start, ordered_method_item_returnVar.stop);
                return ordered_method_item_returnVar;
            }
            commonTree2 = commonTree;
            ordered_method_item_returnVar.stop = this.input.LT(-1);
            ordered_method_item_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree2);
            this.adaptor.setTokenBoundaries(ordered_method_item_returnVar.tree, ordered_method_item_returnVar.start, ordered_method_item_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            ordered_method_item_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, ordered_method_item_returnVar.start, this.input.LT(-1), e);
        }
        return ordered_method_item_returnVar;
    }

    public static class registers_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final registers_directive_return registers_directive() throws RecognitionException {
        Object[] objArr;
        CommonTree commonTree;
        registers_directive_return registers_directive_returnVar = new registers_directive_return();
        registers_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token LOCALS_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTERS_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 205) {
                objArr = 1;
            } else {
                if (iLA != 187) {
                    throw new NoViableAltException("", 9, 0, this.input);
                }
                objArr = 2;
            }
            Token token = null;
            if (objArr == 1) {
                Token token2 = (Token) match(this.input, 205, FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1816);
                rewriteRuleTokenStream2.add(token2);
                pushFollow(FOLLOW_integral_literal_in_registers_directive1820);
                integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree());
                registers_directive_returnVar.tree = null;
                RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule regCount", integral_literal_returnVarIntegral_literal != null ? integral_literal_returnVarIntegral_literal.getTree() : null);
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", registers_directive_returnVar.getTree());
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                CommonTree commonTree3 = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor = this.adaptor;
                CommonTree commonTree4 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(137, token2, "I_REGISTERS"), commonTree3);
                this.adaptor.addChild(commonTree4, rewriteRuleSubtreeStream2.nextTree());
                this.adaptor.addChild(commonTree2, commonTree4);
                registers_directive_returnVar.tree = commonTree2;
                token = token2;
                commonTree = commonTree2;
            } else if (objArr != 2) {
                commonTree = null;
            } else {
                Token token3 = (Token) match(this.input, 187, FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1840);
                rewriteRuleTokenStream.add(token3);
                pushFollow(FOLLOW_integral_literal_in_registers_directive1844);
                integral_literal_return integral_literal_returnVarIntegral_literal2 = integral_literal();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal2.getTree());
                registers_directive_returnVar.tree = null;
                RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule regCount2", integral_literal_returnVarIntegral_literal2 != null ? integral_literal_returnVarIntegral_literal2.getTree() : null);
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", registers_directive_returnVar.getTree());
                CommonTree commonTree5 = (CommonTree) this.adaptor.nil();
                CommonTree commonTree6 = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor2 = this.adaptor;
                CommonTree commonTree7 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(125, token3, "I_LOCALS"), commonTree6);
                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream3.nextTree());
                this.adaptor.addChild(commonTree5, commonTree7);
                registers_directive_returnVar.tree = commonTree5;
                commonTree = commonTree5;
                token = token3;
            }
            if (this.statements_and_directives_stack.peek().hasRegistersDirective) {
                throw new SemanticException(this.input, token, "There can only be a single .registers or .locals directive in a method", new Object[0]);
            }
            this.statements_and_directives_stack.peek().hasRegistersDirective = true;
            registers_directive_returnVar.stop = this.input.LT(-1);
            registers_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(registers_directive_returnVar.tree, registers_directive_returnVar.start, registers_directive_returnVar.stop);
            return registers_directive_returnVar;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            registers_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, registers_directive_returnVar.start, this.input.LT(-1), e);
        }
    }

    public static class param_list_or_id_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final param_list_or_id_return param_list_or_id() throws RecognitionException {
        param_list_or_id_return param_list_or_id_returnVar = new param_list_or_id_return();
        param_list_or_id_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            int i = 0;
            while (this.input.LA(1) == 200) {
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 200, FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id1876)));
                i++;
            }
            if (i < 1) {
                throw new EarlyExitException(10, this.input);
            }
            param_list_or_id_returnVar.stop = this.input.LT(-1);
            param_list_or_id_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(param_list_or_id_returnVar.tree, param_list_or_id_returnVar.start, param_list_or_id_returnVar.stop);
            return param_list_or_id_returnVar;
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            param_list_or_id_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, param_list_or_id_returnVar.start, this.input.LT(-1), e);
        }
    }

    public static class simple_name_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Code duplicated, block: B:73:0x02cb  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final simple_name_return simple_name() throws RecognitionException {
        char c;
        simple_name_return simple_name_returnVar = new simple_name_return();
        simple_name_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token HIDDENAPI_RESTRICTION");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token ANNOTATION_VISIBILITY");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream5 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22t");
        RewriteRuleTokenStream rewriteRuleTokenStream6 = new RewriteRuleTokenStream(this.adaptor, "token VOID_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream7 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10t");
        RewriteRuleTokenStream rewriteRuleTokenStream8 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35mi_METHOD");
        RewriteRuleTokenStream rewriteRuleTokenStream9 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22s_OR_ID");
        RewriteRuleTokenStream rewriteRuleTokenStream10 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22cs_FIELD");
        RewriteRuleTokenStream rewriteRuleTokenStream11 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT12x_OR_ID");
        RewriteRuleTokenStream rewriteRuleTokenStream12 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35ms_METHOD");
        RewriteRuleTokenStream rewriteRuleTokenStream13 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD");
        RewriteRuleTokenStream rewriteRuleTokenStream14 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT45cc_METHOD");
        RewriteRuleTokenStream rewriteRuleTokenStream15 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream16 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10x");
        RewriteRuleTokenStream rewriteRuleTokenStream17 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_METHOD_HANDLE");
        RewriteRuleTokenStream rewriteRuleTokenStream18 = new RewriteRuleTokenStream(this.adaptor, "token FLOAT_LITERAL_OR_ID");
        RewriteRuleTokenStream rewriteRuleTokenStream19 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream20 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_STRING");
        RewriteRuleTokenStream rewriteRuleTokenStream21 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD_ODEX");
        RewriteRuleTokenStream rewriteRuleTokenStream22 = new RewriteRuleTokenStream(this.adaptor, "token NEGATIVE_INTEGER_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream23 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream24 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD_ODEX");
        RewriteRuleTokenStream rewriteRuleTokenStream25 = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_LITERAL_OR_ID");
        RewriteRuleTokenStream rewriteRuleTokenStream26 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31i_OR_ID");
        RewriteRuleTokenStream rewriteRuleTokenStream27 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21t");
        RewriteRuleTokenStream rewriteRuleTokenStream28 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31t");
        RewriteRuleTokenStream rewriteRuleTokenStream29 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT23x");
        RewriteRuleTokenStream rewriteRuleTokenStream30 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_CALL_SITE");
        RewriteRuleTokenStream rewriteRuleTokenStream31 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT51l");
        RewriteRuleTokenStream rewriteRuleTokenStream32 = new RewriteRuleTokenStream(this.adaptor, "token POSITIVE_INTEGER_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream33 = new RewriteRuleTokenStream(this.adaptor, "token BOOL_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream34 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10x_ODEX");
        RewriteRuleTokenStream rewriteRuleTokenStream35 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD");
        RewriteRuleTokenStream rewriteRuleTokenStream36 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD");
        RewriteRuleTokenStream rewriteRuleTokenStream37 = new RewriteRuleTokenStream(this.adaptor, "token VERIFICATION_ERROR_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream38 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT11x");
        RewriteRuleTokenStream rewriteRuleTokenStream39 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_METHOD_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream40 = new RewriteRuleTokenStream(this.adaptor, "token ACCESS_SPEC");
        RewriteRuleTokenStream rewriteRuleTokenStream41 = new RewriteRuleTokenStream(this.adaptor, "token NULL_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream42 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT4rcc_METHOD");
        RewriteRuleTokenStream rewriteRuleTokenStream43 = new RewriteRuleTokenStream(this.adaptor, "token PRIMITIVE_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream44 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD_ODEX");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule param_list_or_id");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 4) {
                c = 2;
            } else if (iLA == 6) {
                c = 15;
            } else if (iLA == 40) {
                c = 7;
            } else if (iLA == 41) {
                c = 3;
            } else if (iLA == 69) {
                c = '\"';
            } else if (iLA == 70) {
                c = '#';
            } else if (iLA == 76) {
                c = MangleConstant.PLATFORM_FUNCTION_MARKER;
            } else if (iLA != 77) {
                switch (iLA) {
                    case 6:
                        c = 15;
                        break;
                    case 11:
                        c = '\t';
                        break;
                    case 24:
                        c = '\b';
                        break;
                    case 48:
                        c = 21;
                        break;
                    case 50:
                        c = 22;
                        break;
                    case 62:
                        c = 29;
                        break;
                    case 72:
                        c = '$';
                        break;
                    case 79:
                        c = '\'';
                        break;
                    case 80:
                        c = '(';
                        break;
                    case 81:
                        c = ')';
                        break;
                    case 82:
                        c = '*';
                        break;
                    case 83:
                        c = '+';
                        break;
                    case 84:
                        c = ',';
                        break;
                    case 85:
                        c = '-';
                        break;
                    case 204:
                        c = 11;
                        break;
                    case 208:
                        c = 1;
                        break;
                    case 214:
                        c = 4;
                        break;
                    case 215:
                        c = 14;
                        break;
                    default:
                        switch (iLA) {
                            case 44:
                                c = 18;
                                break;
                            case 45:
                                c = 19;
                                break;
                            case 46:
                                c = 20;
                                break;
                            default:
                                switch (iLA) {
                                    case 53:
                                        c = 23;
                                        break;
                                    case 54:
                                        c = 24;
                                        break;
                                    case 55:
                                        c = 25;
                                        break;
                                    case 56:
                                        c = 26;
                                        break;
                                    case 57:
                                        c = 27;
                                        break;
                                    case 58:
                                        c = 28;
                                        break;
                                    default:
                                        switch (iLA) {
                                            case 64:
                                                c = 30;
                                                break;
                                            case 65:
                                                c = 31;
                                                break;
                                            case 66:
                                                c = StringUtil.SPACE;
                                                break;
                                            case 67:
                                                c = '!';
                                                break;
                                            default:
                                                switch (iLA) {
                                                    case 92:
                                                        c = '.';
                                                        break;
                                                    case 93:
                                                        c = '/';
                                                        break;
                                                    case 94:
                                                        c = ExternalAnnotationProvider.NULLABLE;
                                                        break;
                                                    default:
                                                        switch (iLA) {
                                                            case 192:
                                                                c = 16;
                                                                break;
                                                            case 193:
                                                                c = 17;
                                                                break;
                                                            case 194:
                                                                c = 6;
                                                                break;
                                                            case 195:
                                                                c = '\n';
                                                                break;
                                                            default:
                                                                switch (iLA) {
                                                                    case 200:
                                                                        c = '\f';
                                                                        break;
                                                                    case 201:
                                                                        c = 5;
                                                                        break;
                                                                    case 202:
                                                                        c = '\r';
                                                                        break;
                                                                    default:
                                                                        throw new NoViableAltException("", 11, 0, this.input);
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
            } else {
                c = Typography.amp;
            }
            CommonTree commonTree = null;
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 208, FOLLOW_SIMPLE_NAME_in_simple_name1889)));
                    break;
                case 2:
                    Token token = (Token) match(this.input, 4, FOLLOW_ACCESS_SPEC_in_simple_name1895);
                    rewriteRuleTokenStream40.add(token);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor = this.adaptor;
                    treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(208, token));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 3:
                    Token token2 = (Token) match(this.input, 41, FOLLOW_HIDDENAPI_RESTRICTION_in_simple_name1906);
                    rewriteRuleTokenStream.add(token2);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor2 = this.adaptor;
                    treeAdaptor2.addChild(commonTree, (CommonTree) treeAdaptor2.create(208, token2));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 4:
                    Token token3 = (Token) match(this.input, 214, FOLLOW_VERIFICATION_ERROR_TYPE_in_simple_name1917);
                    rewriteRuleTokenStream37.add(token3);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor3 = this.adaptor;
                    treeAdaptor3.addChild(commonTree, (CommonTree) treeAdaptor3.create(208, token3));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 5:
                    Token token4 = (Token) match(this.input, 201, FOLLOW_POSITIVE_INTEGER_LITERAL_in_simple_name1928);
                    rewriteRuleTokenStream32.add(token4);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor4 = this.adaptor;
                    treeAdaptor4.addChild(commonTree, (CommonTree) treeAdaptor4.create(208, token4));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 6:
                    Token token5 = (Token) match(this.input, 194, FOLLOW_NEGATIVE_INTEGER_LITERAL_in_simple_name1939);
                    rewriteRuleTokenStream22.add(token5);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor5 = this.adaptor;
                    treeAdaptor5.addChild(commonTree, (CommonTree) treeAdaptor5.create(208, token5));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 7:
                    Token token6 = (Token) match(this.input, 40, FOLLOW_FLOAT_LITERAL_OR_ID_in_simple_name1950);
                    rewriteRuleTokenStream18.add(token6);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor6 = this.adaptor;
                    treeAdaptor6.addChild(commonTree, (CommonTree) treeAdaptor6.create(208, token6));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '\b':
                    Token token7 = (Token) match(this.input, 24, FOLLOW_DOUBLE_LITERAL_OR_ID_in_simple_name1961);
                    rewriteRuleTokenStream25.add(token7);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor7 = this.adaptor;
                    treeAdaptor7.addChild(commonTree, (CommonTree) treeAdaptor7.create(208, token7));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '\t':
                    Token token8 = (Token) match(this.input, 11, FOLLOW_BOOL_LITERAL_in_simple_name1972);
                    rewriteRuleTokenStream33.add(token8);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor8 = this.adaptor;
                    treeAdaptor8.addChild(commonTree, (CommonTree) treeAdaptor8.create(208, token8));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '\n':
                    Token token9 = (Token) match(this.input, 195, FOLLOW_NULL_LITERAL_in_simple_name1983);
                    rewriteRuleTokenStream41.add(token9);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor9 = this.adaptor;
                    treeAdaptor9.addChild(commonTree, (CommonTree) treeAdaptor9.create(208, token9));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 11:
                    Token token10 = (Token) match(this.input, 204, FOLLOW_REGISTER_in_simple_name1994);
                    rewriteRuleTokenStream23.add(token10);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor10 = this.adaptor;
                    treeAdaptor10.addChild(commonTree, (CommonTree) treeAdaptor10.create(208, token10));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '\f':
                    pushFollow(FOLLOW_param_list_or_id_in_simple_name2005);
                    param_list_or_id_return param_list_or_id_returnVarParam_list_or_id = param_list_or_id();
                    this.state._fsp--;
                    rewriteRuleSubtreeStream.add(param_list_or_id_returnVarParam_list_or_id.getTree());
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor11 = this.adaptor;
                    treeAdaptor11.addChild(commonTree2, treeAdaptor11.create(208, param_list_or_id_returnVarParam_list_or_id != null ? this.input.toString(param_list_or_id_returnVarParam_list_or_id.start, param_list_or_id_returnVarParam_list_or_id.stop) : null));
                    simple_name_returnVar.tree = commonTree2;
                    commonTree = commonTree2;
                    break;
                case '\r':
                    Token token11 = (Token) match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_simple_name2015);
                    rewriteRuleTokenStream43.add(token11);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor12 = this.adaptor;
                    treeAdaptor12.addChild(commonTree, (CommonTree) treeAdaptor12.create(208, token11));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 14:
                    Token token12 = (Token) match(this.input, 215, FOLLOW_VOID_TYPE_in_simple_name2026);
                    rewriteRuleTokenStream6.add(token12);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor13 = this.adaptor;
                    treeAdaptor13.addChild(commonTree, (CommonTree) treeAdaptor13.create(208, token12));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 15:
                    Token token13 = (Token) match(this.input, 6, FOLLOW_ANNOTATION_VISIBILITY_in_simple_name2037);
                    rewriteRuleTokenStream3.add(token13);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor14 = this.adaptor;
                    treeAdaptor14.addChild(commonTree, (CommonTree) treeAdaptor14.create(208, token13));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 16:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 192, FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_simple_name2048)));
                    break;
                case 17:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 193, FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_simple_name2054)));
                    break;
                case 18:
                    Token token14 = (Token) match(this.input, 44, FOLLOW_INSTRUCTION_FORMAT10t_in_simple_name2060);
                    rewriteRuleTokenStream7.add(token14);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor15 = this.adaptor;
                    treeAdaptor15.addChild(commonTree, (CommonTree) treeAdaptor15.create(208, token14));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 19:
                    Token token15 = (Token) match(this.input, 45, FOLLOW_INSTRUCTION_FORMAT10x_in_simple_name2071);
                    rewriteRuleTokenStream16.add(token15);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor16 = this.adaptor;
                    treeAdaptor16.addChild(commonTree, (CommonTree) treeAdaptor16.create(208, token15));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 20:
                    Token token16 = (Token) match(this.input, 46, FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_simple_name2082);
                    rewriteRuleTokenStream34.add(token16);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor17 = this.adaptor;
                    treeAdaptor17.addChild(commonTree, (CommonTree) treeAdaptor17.create(208, token16));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 21:
                    Token token17 = (Token) match(this.input, 48, FOLLOW_INSTRUCTION_FORMAT11x_in_simple_name2093);
                    rewriteRuleTokenStream38.add(token17);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor18 = this.adaptor;
                    treeAdaptor18.addChild(commonTree, (CommonTree) treeAdaptor18.create(208, token17));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 22:
                    Token token18 = (Token) match(this.input, 50, FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_simple_name2104);
                    rewriteRuleTokenStream11.add(token18);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor19 = this.adaptor;
                    treeAdaptor19.addChild(commonTree, (CommonTree) treeAdaptor19.create(208, token18));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 23:
                    Token token19 = (Token) match(this.input, 53, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_simple_name2115);
                    rewriteRuleTokenStream35.add(token19);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor20 = this.adaptor;
                    treeAdaptor20.addChild(commonTree, (CommonTree) treeAdaptor20.create(208, token19));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 24:
                    Token token20 = (Token) match(this.input, 54, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_simple_name2126);
                    rewriteRuleTokenStream44.add(token20);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor21 = this.adaptor;
                    treeAdaptor21.addChild(commonTree, (CommonTree) treeAdaptor21.create(208, token20));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 25:
                    Token token21 = (Token) match(this.input, 55, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_simple_name2137);
                    rewriteRuleTokenStream17.add(token21);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor22 = this.adaptor;
                    treeAdaptor22.addChild(commonTree, (CommonTree) treeAdaptor22.create(208, token21));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 26:
                    Token token22 = (Token) match(this.input, 56, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_simple_name2148);
                    rewriteRuleTokenStream39.add(token22);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor23 = this.adaptor;
                    treeAdaptor23.addChild(commonTree, (CommonTree) treeAdaptor23.create(208, token22));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 27:
                    Token token23 = (Token) match(this.input, 57, FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_simple_name2159);
                    rewriteRuleTokenStream20.add(token23);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor24 = this.adaptor;
                    treeAdaptor24.addChild(commonTree, (CommonTree) treeAdaptor24.create(208, token23));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 28:
                    Token token24 = (Token) match(this.input, 58, FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_simple_name2170);
                    rewriteRuleTokenStream4.add(token24);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor25 = this.adaptor;
                    treeAdaptor25.addChild(commonTree, (CommonTree) treeAdaptor25.create(208, token24));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 29:
                    Token token25 = (Token) match(this.input, 62, FOLLOW_INSTRUCTION_FORMAT21t_in_simple_name2181);
                    rewriteRuleTokenStream27.add(token25);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor26 = this.adaptor;
                    treeAdaptor26.addChild(commonTree, (CommonTree) treeAdaptor26.create(208, token25));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 30:
                    Token token26 = (Token) match(this.input, 64, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_simple_name2192);
                    rewriteRuleTokenStream36.add(token26);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor27 = this.adaptor;
                    treeAdaptor27.addChild(commonTree, (CommonTree) treeAdaptor27.create(208, token26));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case 31:
                    Token token27 = (Token) match(this.input, 65, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_simple_name2203);
                    rewriteRuleTokenStream24.add(token27);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor28 = this.adaptor;
                    treeAdaptor28.addChild(commonTree, (CommonTree) treeAdaptor28.create(208, token27));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case ' ':
                    Token token28 = (Token) match(this.input, 66, FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_simple_name2214);
                    rewriteRuleTokenStream19.add(token28);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor29 = this.adaptor;
                    treeAdaptor29.addChild(commonTree, (CommonTree) treeAdaptor29.create(208, token28));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '!':
                    Token token29 = (Token) match(this.input, 67, FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_simple_name2225);
                    rewriteRuleTokenStream10.add(token29);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor30 = this.adaptor;
                    treeAdaptor30.addChild(commonTree, (CommonTree) treeAdaptor30.create(208, token29));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '\"':
                    Token token30 = (Token) match(this.input, 69, FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_simple_name2236);
                    rewriteRuleTokenStream9.add(token30);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor31 = this.adaptor;
                    treeAdaptor31.addChild(commonTree, (CommonTree) treeAdaptor31.create(208, token30));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '#':
                    Token token31 = (Token) match(this.input, 70, FOLLOW_INSTRUCTION_FORMAT22t_in_simple_name2247);
                    rewriteRuleTokenStream5.add(token31);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor32 = this.adaptor;
                    treeAdaptor32.addChild(commonTree, (CommonTree) treeAdaptor32.create(208, token31));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '$':
                    Token token32 = (Token) match(this.input, 72, FOLLOW_INSTRUCTION_FORMAT23x_in_simple_name2258);
                    rewriteRuleTokenStream29.add(token32);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor33 = this.adaptor;
                    treeAdaptor33.addChild(commonTree, (CommonTree) treeAdaptor33.create(208, token32));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '%':
                    Token token33 = (Token) match(this.input, 76, FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_simple_name2269);
                    rewriteRuleTokenStream26.add(token33);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor34 = this.adaptor;
                    treeAdaptor34.addChild(commonTree, (CommonTree) treeAdaptor34.create(208, token33));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '&':
                    Token token34 = (Token) match(this.input, 77, FOLLOW_INSTRUCTION_FORMAT31t_in_simple_name2280);
                    rewriteRuleTokenStream28.add(token34);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor35 = this.adaptor;
                    treeAdaptor35.addChild(commonTree, (CommonTree) treeAdaptor35.create(208, token34));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '\'':
                    Token token35 = (Token) match(this.input, 79, FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_simple_name2291);
                    rewriteRuleTokenStream30.add(token35);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor36 = this.adaptor;
                    treeAdaptor36.addChild(commonTree, (CommonTree) treeAdaptor36.create(208, token35));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '(':
                    Token token36 = (Token) match(this.input, 80, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_simple_name2302);
                    rewriteRuleTokenStream13.add(token36);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor37 = this.adaptor;
                    treeAdaptor37.addChild(commonTree, (CommonTree) treeAdaptor37.create(208, token36));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case ')':
                    Token token37 = (Token) match(this.input, 81, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_simple_name2313);
                    rewriteRuleTokenStream21.add(token37);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor38 = this.adaptor;
                    treeAdaptor38.addChild(commonTree, (CommonTree) treeAdaptor38.create(208, token37));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '*':
                    Token token38 = (Token) match(this.input, 82, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_simple_name2324);
                    rewriteRuleTokenStream2.add(token38);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor39 = this.adaptor;
                    treeAdaptor39.addChild(commonTree, (CommonTree) treeAdaptor39.create(208, token38));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '+':
                    Token token39 = (Token) match(this.input, 83, FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_simple_name2335);
                    rewriteRuleTokenStream15.add(token39);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor40 = this.adaptor;
                    treeAdaptor40.addChild(commonTree, (CommonTree) treeAdaptor40.create(208, token39));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case ',':
                    Token token40 = (Token) match(this.input, 84, FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_simple_name2346);
                    rewriteRuleTokenStream8.add(token40);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor41 = this.adaptor;
                    treeAdaptor41.addChild(commonTree, (CommonTree) treeAdaptor41.create(208, token40));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '-':
                    Token token41 = (Token) match(this.input, 85, FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_simple_name2357);
                    rewriteRuleTokenStream12.add(token41);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor42 = this.adaptor;
                    treeAdaptor42.addChild(commonTree, (CommonTree) treeAdaptor42.create(208, token41));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '.':
                    Token token42 = (Token) match(this.input, 92, FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_simple_name2368);
                    rewriteRuleTokenStream14.add(token42);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor43 = this.adaptor;
                    treeAdaptor43.addChild(commonTree, (CommonTree) treeAdaptor43.create(208, token42));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '/':
                    Token token43 = (Token) match(this.input, 93, FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_simple_name2379);
                    rewriteRuleTokenStream42.add(token43);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor44 = this.adaptor;
                    treeAdaptor44.addChild(commonTree, (CommonTree) treeAdaptor44.create(208, token43));
                    simple_name_returnVar.tree = commonTree;
                    break;
                case '0':
                    Token token44 = (Token) match(this.input, 94, FOLLOW_INSTRUCTION_FORMAT51l_in_simple_name2390);
                    rewriteRuleTokenStream31.add(token44);
                    simple_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", simple_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor45 = this.adaptor;
                    treeAdaptor45.addChild(commonTree, (CommonTree) treeAdaptor45.create(208, token44));
                    simple_name_returnVar.tree = commonTree;
                    break;
            }
            simple_name_returnVar.stop = this.input.LT(-1);
            simple_name_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(simple_name_returnVar.tree, simple_name_returnVar.start, simple_name_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            simple_name_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, simple_name_returnVar.start, this.input.LT(-1), e);
        }
        return simple_name_returnVar;
    }

    public static class member_name_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final member_name_return member_name() throws RecognitionException {
        char c;
        CommonTree commonTree;
        member_name_return member_name_returnVar = new member_name_return();
        member_name_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token MEMBER_NAME");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 4 || iLA == 6 || iLA == 11 || iLA == 24 || ((iLA >= 40 && iLA <= 41) || ((iLA >= 44 && iLA <= 46) || iLA == 48 || iLA == 50 || ((iLA >= 53 && iLA <= 58) || iLA == 62 || ((iLA >= 64 && iLA <= 67) || ((iLA >= 69 && iLA <= 70) || iLA == 72 || ((iLA >= 76 && iLA <= 77) || ((iLA >= 79 && iLA <= 85) || ((iLA >= 92 && iLA <= 94) || ((iLA >= 192 && iLA <= 195) || ((iLA >= 200 && iLA <= 202) || iLA == 204 || iLA == 208 || (iLA >= 214 && iLA <= 215)))))))))))) {
                c = 1;
            } else {
                if (iLA != 190) {
                    throw new NoViableAltException("", 12, 0, this.input);
                }
                c = 2;
            }
            if (c != 1) {
                commonTree = null;
                if (c == 2) {
                    Token token = (Token) match(this.input, 190, FOLLOW_MEMBER_NAME_in_member_name2411);
                    rewriteRuleTokenStream.add(token);
                    member_name_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", member_name_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor = this.adaptor;
                    treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(208, token));
                    member_name_returnVar.tree = commonTree;
                }
            } else {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                pushFollow(FOLLOW_simple_name_in_member_name2405);
                simple_name_return simple_name_returnVarSimple_name = simple_name();
                this.state._fsp--;
                this.adaptor.addChild(commonTree2, simple_name_returnVarSimple_name.getTree());
                commonTree = commonTree2;
            }
            member_name_returnVar.stop = this.input.LT(-1);
            member_name_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(member_name_returnVar.tree, member_name_returnVar.start, member_name_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            member_name_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, member_name_returnVar.start, this.input.LT(-1), e);
        }
        return member_name_returnVar;
    }

    public static class method_prototype_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final method_prototype_return method_prototype() throws RecognitionException {
        method_prototype_return method_prototype_returnVar = new method_prototype_return();
        method_prototype_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token OPEN_PAREN");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_PAREN");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule type_descriptor");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule param_list");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 197, FOLLOW_OPEN_PAREN_in_method_prototype2426));
            pushFollow(FOLLOW_param_list_in_method_prototype2428);
            param_list_return param_list_returnVarParam_list = param_list();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(param_list_returnVarParam_list.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 19, FOLLOW_CLOSE_PAREN_in_method_prototype2430));
            pushFollow(FOLLOW_type_descriptor_in_method_prototype2432);
            type_descriptor_return type_descriptor_returnVarType_descriptor = type_descriptor();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(type_descriptor_returnVarType_descriptor.getTree());
            method_prototype_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_prototype_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(128, method_prototype_returnVar.start, "I_METHOD_PROTOTYPE"), commonTree2);
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor2 = this.adaptor;
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(129, "I_METHOD_RETURN_TYPE"), commonTree4);
            this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, commonTree5);
            if (rewriteRuleSubtreeStream2.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            }
            rewriteRuleSubtreeStream2.reset();
            this.adaptor.addChild(commonTree, commonTree3);
            method_prototype_returnVar.tree = commonTree;
            method_prototype_returnVar.stop = this.input.LT(-1);
            method_prototype_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(method_prototype_returnVar.tree, method_prototype_returnVar.start, method_prototype_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            method_prototype_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, method_prototype_returnVar.start, this.input.LT(-1), e);
        }
        return method_prototype_returnVar;
    }

    public static class param_list_or_id_primitive_type_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final param_list_or_id_primitive_type_return param_list_or_id_primitive_type() throws RecognitionException {
        param_list_or_id_primitive_type_return param_list_or_id_primitive_type_returnVar = new param_list_or_id_primitive_type_return();
        param_list_or_id_primitive_type_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token PARAM_LIST_OR_ID_PRIMITIVE_TYPE");
        try {
            Token token = (Token) match(this.input, 200, FOLLOW_PARAM_LIST_OR_ID_PRIMITIVE_TYPE_in_param_list_or_id_primitive_type2462);
            rewriteRuleTokenStream.add(token);
            param_list_or_id_primitive_type_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", param_list_or_id_primitive_type_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(202, token));
            param_list_or_id_primitive_type_returnVar.tree = commonTree;
            param_list_or_id_primitive_type_returnVar.stop = this.input.LT(-1);
            param_list_or_id_primitive_type_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(param_list_or_id_primitive_type_returnVar.tree, param_list_or_id_primitive_type_returnVar.start, param_list_or_id_primitive_type_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            param_list_or_id_primitive_type_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, param_list_or_id_primitive_type_returnVar.start, this.input.LT(-1), e);
        }
        return param_list_or_id_primitive_type_returnVar;
    }

    public static class param_list_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final param_list_return param_list() throws RecognitionException {
        char c;
        CommonTree commonTree;
        CommonTree commonTree2;
        param_list_return param_list_returnVar = new param_list_return();
        param_list_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            int i = 0;
            if (iLA == 200) {
                c = 1;
            } else {
                if (iLA != 8 && iLA != 16 && iLA != 19 && iLA != 202) {
                    throw new NoViableAltException("", 15, 0, this.input);
                }
                c = 2;
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil();
                while (this.input.LA(1) == 200) {
                    pushFollow(FOLLOW_param_list_or_id_primitive_type_in_param_list2477);
                    param_list_or_id_primitive_type_return param_list_or_id_primitive_type_returnVarParam_list_or_id_primitive_type = param_list_or_id_primitive_type();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, param_list_or_id_primitive_type_returnVarParam_list_or_id_primitive_type.getTree());
                    i++;
                }
                if (i < 1) {
                    throw new EarlyExitException(13, this.input);
                }
            } else {
                if (c != 2) {
                    commonTree2 = null;
                } else {
                    commonTree = (CommonTree) this.adaptor.nil();
                    while (true) {
                        int iLA2 = this.input.LA(1);
                        if (iLA2 != 8 && iLA2 != 16 && iLA2 != 202) {
                            break;
                        }
                        pushFollow(FOLLOW_nonvoid_type_descriptor_in_param_list2484);
                        nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
                        this.state._fsp--;
                        this.adaptor.addChild(commonTree, nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
                    }
                }
                param_list_returnVar.stop = this.input.LT(-1);
                param_list_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree2);
                this.adaptor.setTokenBoundaries(param_list_returnVar.tree, param_list_returnVar.start, param_list_returnVar.stop);
                return param_list_returnVar;
            }
            commonTree2 = commonTree;
            param_list_returnVar.stop = this.input.LT(-1);
            param_list_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree2);
            this.adaptor.setTokenBoundaries(param_list_returnVar.tree, param_list_returnVar.start, param_list_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            param_list_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, param_list_returnVar.start, this.input.LT(-1), e);
        }
        return param_list_returnVar;
    }

    public static class array_descriptor_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final array_descriptor_return array_descriptor() throws RecognitionException {
        array_descriptor_return array_descriptor_returnVar = new array_descriptor_return();
        array_descriptor_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 8, FOLLOW_ARRAY_TYPE_PREFIX_in_array_descriptor2495)));
            Token tokenLT = this.input.LT(1);
            if (this.input.LA(1) != 16 && this.input.LA(1) != 202) {
                throw new MismatchedSetException(null, this.input);
            }
            this.input.consume();
            TreeAdaptor treeAdaptor = this.adaptor;
            treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(tokenLT));
            this.state.errorRecovery = false;
            array_descriptor_returnVar.stop = this.input.LT(-1);
            array_descriptor_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(array_descriptor_returnVar.tree, array_descriptor_returnVar.start, array_descriptor_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            array_descriptor_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, array_descriptor_returnVar.start, this.input.LT(-1), e);
        }
        return array_descriptor_returnVar;
    }

    public static class type_descriptor_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final type_descriptor_return type_descriptor() throws RecognitionException {
        char c;
        CommonTree commonTree;
        type_descriptor_return type_descriptor_returnVar = new type_descriptor_return();
        type_descriptor_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 8) {
                c = 4;
            } else if (iLA == 16) {
                c = 3;
            } else if (iLA == 202) {
                c = 2;
            } else {
                if (iLA != 215) {
                    throw new NoViableAltException("", 16, 0, this.input);
                }
                c = 1;
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 215, FOLLOW_VOID_TYPE_in_type_descriptor2513)));
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_type_descriptor2519)));
            } else if (c == 3) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_type_descriptor2525)));
            } else if (c != 4) {
                commonTree = null;
            } else {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                pushFollow(FOLLOW_array_descriptor_in_type_descriptor2531);
                array_descriptor_return array_descriptor_returnVarArray_descriptor = array_descriptor();
                this.state._fsp--;
                this.adaptor.addChild(commonTree2, array_descriptor_returnVarArray_descriptor.getTree());
                commonTree = commonTree2;
            }
            type_descriptor_returnVar.stop = this.input.LT(-1);
            type_descriptor_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(type_descriptor_returnVar.tree, type_descriptor_returnVar.start, type_descriptor_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            type_descriptor_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, type_descriptor_returnVar.start, this.input.LT(-1), e);
        }
        return type_descriptor_returnVar;
    }

    public static class nonvoid_type_descriptor_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final nonvoid_type_descriptor_return nonvoid_type_descriptor() throws RecognitionException {
        char c;
        CommonTree commonTree;
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
                    throw new NoViableAltException("", 17, 0, this.input);
                }
                c = 1;
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_nonvoid_type_descriptor2541)));
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_nonvoid_type_descriptor2547)));
            } else if (c != 3) {
                commonTree = null;
            } else {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                pushFollow(FOLLOW_array_descriptor_in_nonvoid_type_descriptor2553);
                array_descriptor_return array_descriptor_returnVarArray_descriptor = array_descriptor();
                this.state._fsp--;
                this.adaptor.addChild(commonTree2, array_descriptor_returnVarArray_descriptor.getTree());
                commonTree = commonTree2;
            }
            nonvoid_type_descriptor_returnVar.stop = this.input.LT(-1);
            nonvoid_type_descriptor_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(nonvoid_type_descriptor_returnVar.tree, nonvoid_type_descriptor_returnVar.start, nonvoid_type_descriptor_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            nonvoid_type_descriptor_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, nonvoid_type_descriptor_returnVar.start, this.input.LT(-1), e);
        }
        return nonvoid_type_descriptor_returnVar;
    }

    public static class reference_type_descriptor_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final reference_type_descriptor_return reference_type_descriptor() throws RecognitionException {
        char c;
        CommonTree commonTree;
        reference_type_descriptor_return reference_type_descriptor_returnVar = new reference_type_descriptor_return();
        reference_type_descriptor_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 16) {
                c = 1;
            } else {
                if (iLA != 8) {
                    throw new NoViableAltException("", 18, 0, this.input);
                }
                c = 2;
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_reference_type_descriptor2563)));
            } else if (c != 2) {
                commonTree = null;
            } else {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                pushFollow(FOLLOW_array_descriptor_in_reference_type_descriptor2569);
                array_descriptor_return array_descriptor_returnVarArray_descriptor = array_descriptor();
                this.state._fsp--;
                this.adaptor.addChild(commonTree2, array_descriptor_returnVarArray_descriptor.getTree());
                commonTree = commonTree2;
            }
            reference_type_descriptor_returnVar.stop = this.input.LT(-1);
            reference_type_descriptor_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(reference_type_descriptor_returnVar.tree, reference_type_descriptor_returnVar.start, reference_type_descriptor_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            reference_type_descriptor_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, reference_type_descriptor_returnVar.start, this.input.LT(-1), e);
        }
        return reference_type_descriptor_returnVar;
    }

    public static class integer_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final integer_literal_return integer_literal() throws RecognitionException {
        char c;
        integer_literal_return integer_literal_returnVar = new integer_literal_return();
        integer_literal_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token NEGATIVE_INTEGER_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token POSITIVE_INTEGER_LITERAL");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 201) {
                c = 1;
            } else {
                if (iLA != 194) {
                    throw new NoViableAltException("", 19, 0, this.input);
                }
                c = 2;
            }
            CommonTree commonTree = null;
            if (c == 1) {
                Token token = (Token) match(this.input, 201, FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2579);
                rewriteRuleTokenStream2.add(token);
                integer_literal_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", integer_literal_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor = this.adaptor;
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(95, token));
                integer_literal_returnVar.tree = commonTree;
            } else if (c == 2) {
                Token token2 = (Token) match(this.input, 194, FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2590);
                rewriteRuleTokenStream.add(token2);
                integer_literal_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", integer_literal_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor2 = this.adaptor;
                treeAdaptor2.addChild(commonTree, (CommonTree) treeAdaptor2.create(95, token2));
                integer_literal_returnVar.tree = commonTree;
            }
            integer_literal_returnVar.stop = this.input.LT(-1);
            integer_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(integer_literal_returnVar.tree, integer_literal_returnVar.start, integer_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            integer_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, integer_literal_returnVar.start, this.input.LT(-1), e);
        }
        return integer_literal_returnVar;
    }

    public static class float_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final float_literal_return float_literal() throws RecognitionException {
        char c;
        float_literal_return float_literal_returnVar = new float_literal_return();
        float_literal_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token FLOAT_LITERAL_OR_ID");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 40) {
                c = 1;
            } else {
                if (iLA != 39) {
                    throw new NoViableAltException("", 20, 0, this.input);
                }
                c = 2;
            }
            CommonTree commonTree = null;
            if (c == 1) {
                Token token = (Token) match(this.input, 40, FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2605);
                rewriteRuleTokenStream.add(token);
                float_literal_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", float_literal_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor = this.adaptor;
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(39, token));
                float_literal_returnVar.tree = commonTree;
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 39, FOLLOW_FLOAT_LITERAL_in_float_literal2616)));
            }
            float_literal_returnVar.stop = this.input.LT(-1);
            float_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(float_literal_returnVar.tree, float_literal_returnVar.start, float_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            float_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, float_literal_returnVar.start, this.input.LT(-1), e);
        }
        return float_literal_returnVar;
    }

    public static class double_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final double_literal_return double_literal() throws RecognitionException {
        char c;
        double_literal_return double_literal_returnVar = new double_literal_return();
        double_literal_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_LITERAL_OR_ID");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 24) {
                c = 1;
            } else {
                if (iLA != 23) {
                    throw new NoViableAltException("", 21, 0, this.input);
                }
                c = 2;
            }
            CommonTree commonTree = null;
            if (c == 1) {
                Token token = (Token) match(this.input, 24, FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2626);
                rewriteRuleTokenStream.add(token);
                double_literal_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", double_literal_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor = this.adaptor;
                treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(23, token));
                double_literal_returnVar.tree = commonTree;
            } else if (c == 2) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 23, FOLLOW_DOUBLE_LITERAL_in_double_literal2637)));
            }
            double_literal_returnVar.stop = this.input.LT(-1);
            double_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(double_literal_returnVar.tree, double_literal_returnVar.start, double_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            double_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, double_literal_returnVar.start, this.input.LT(-1), e);
        }
        return double_literal_returnVar;
    }

    public static class literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:214:0x01fb  */
    /* JADX WARN: Code duplicated, block: B:30:0x0062  */
    /* JADX WARN: Code duplicated, block: B:529:0x04fa  */
    public final literal_return literal() throws RecognitionException {
        char c;
        CommonTree commonTree;
        char c2;
        literal_return literal_returnVar = new literal_return();
        literal_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            char c3 = 4;
            if (iLA == 4 || iLA == 6) {
                c = '\r';
                c3 = c;
            } else if (iLA == 11) {
                c = '\r';
                int iLA2 = this.input.LA(2);
                if (iLA2 != -1 && ((iLA2 < 4 || iLA2 > 6) && iLA2 != 11 && ((iLA2 < 17 || iLA2 > 19) && iLA2 != 21 && ((iLA2 < 24 || iLA2 > 25) && iLA2 != 27 && iLA2 != 33 && iLA2 != 37 && ((iLA2 < 40 || iLA2 > 42) && ((iLA2 < 44 || iLA2 > 46) && iLA2 != 48 && iLA2 != 50 && ((iLA2 < 53 || iLA2 > 58) && iLA2 != 62 && ((iLA2 < 64 || iLA2 > 67) && ((iLA2 < 69 || iLA2 > 70) && iLA2 != 72 && ((iLA2 < 76 || iLA2 > 77) && ((iLA2 < 79 || iLA2 > 85) && ((iLA2 < 92 || iLA2 > 94) && ((iLA2 < 191 || iLA2 > 195) && ((iLA2 < 200 || iLA2 > 202) && iLA2 != 204 && ((iLA2 < 208 || iLA2 > 209) && (iLA2 < 213 || iLA2 > 215)))))))))))))))) {
                    if (iLA2 != 20 && iLA2 != 197) {
                        int iMark = this.input.mark();
                        try {
                            this.input.consume();
                            throw new NoViableAltException("", 22, 12, this.input);
                        } catch (Throwable th) {
                            this.input.rewind(iMark);
                            throw th;
                        }
                    }
                    c3 = c;
                }
                c2 = '\t';
                c3 = c2;
            } else if (iLA != 12) {
                if (iLA == 15) {
                    c3 = 7;
                } else if (iLA == 16) {
                    c = '\r';
                    c3 = c;
                } else if (iLA == 23) {
                    c3 = 6;
                } else if (iLA == 24) {
                    c = '\r';
                    int iLA3 = this.input.LA(2);
                    if (iLA3 == -1 || ((iLA3 >= 4 && iLA3 <= 6) || iLA3 == 11 || ((iLA3 >= 17 && iLA3 <= 19) || iLA3 == 21 || ((iLA3 >= 24 && iLA3 <= 25) || iLA3 == 27 || iLA3 == 33 || iLA3 == 37 || ((iLA3 >= 40 && iLA3 <= 42) || ((iLA3 >= 44 && iLA3 <= 46) || iLA3 == 48 || iLA3 == 50 || ((iLA3 >= 53 && iLA3 <= 58) || iLA3 == 62 || ((iLA3 >= 64 && iLA3 <= 67) || ((iLA3 >= 69 && iLA3 <= 70) || iLA3 == 72 || ((iLA3 >= 76 && iLA3 <= 77) || ((iLA3 >= 79 && iLA3 <= 85) || ((iLA3 >= 92 && iLA3 <= 94) || ((iLA3 >= 191 && iLA3 <= 195) || ((iLA3 >= 200 && iLA3 <= 202) || iLA3 == 204 || ((iLA3 >= 208 && iLA3 <= 209) || (iLA3 >= 213 && iLA3 <= 215)))))))))))))))) {
                        c3 = 6;
                    } else {
                        if (iLA3 != 20 && iLA3 != 197) {
                            int iMark2 = this.input.mark();
                            try {
                                this.input.consume();
                                throw new NoViableAltException("", 22, 8, this.input);
                            } catch (Throwable th2) {
                                this.input.rewind(iMark2);
                                throw th2;
                            }
                        }
                        c3 = c;
                    }
                } else if (iLA != 69 && iLA != 70 && iLA != 76 && iLA != 77) {
                    switch (iLA) {
                        case 6:
                        case 8:
                        case 48:
                        case 50:
                        case 62:
                        case 72:
                        case 79:
                        case 80:
                        case 81:
                        case 83:
                        case 84:
                        case 85:
                        case 92:
                        case 93:
                        case 94:
                        case 190:
                        case 200:
                        case 202:
                        case 204:
                        case 208:
                        case 214:
                        case 215:
                            c = '\r';
                            c3 = c;
                            break;
                        case 34:
                            c3 = 14;
                            break;
                        case 82:
                            c = '\r';
                            int iLA4 = this.input.LA(2);
                            if (iLA4 != 10) {
                                if (iLA4 != 20 && iLA4 != 197) {
                                    int iMark3 = this.input.mark();
                                    try {
                                        this.input.consume();
                                        throw new NoViableAltException("", 22, 19, this.input);
                                    } catch (Throwable th3) {
                                        this.input.rewind(iMark3);
                                        throw th3;
                                    }
                                }
                                c3 = c;
                            } else {
                                c2 = 15;
                                c3 = c2;
                            }
                            break;
                        case 189:
                            c3 = 1;
                            break;
                        case 192:
                            c = '\r';
                            int iLA5 = this.input.LA(2);
                            if (iLA5 != 10) {
                                if (iLA5 != 20 && iLA5 != 197) {
                                    int iMark4 = this.input.mark();
                                    try {
                                        this.input.consume();
                                        throw new NoViableAltException("", 22, 17, this.input);
                                    } catch (Throwable th4) {
                                        this.input.rewind(iMark4);
                                        throw th4;
                                    }
                                }
                                c3 = c;
                            }
                            c2 = 15;
                            c3 = c2;
                            break;
                        case 193:
                            c = '\r';
                            int iLA6 = this.input.LA(2);
                            if (iLA6 != 10) {
                                if (iLA6 != 20 && iLA6 != 197) {
                                    int iMark5 = this.input.mark();
                                    try {
                                        this.input.consume();
                                        throw new NoViableAltException("", 22, 18, this.input);
                                    } catch (Throwable th5) {
                                        this.input.rewind(iMark5);
                                        throw th5;
                                    }
                                }
                                c3 = c;
                            }
                            c2 = 15;
                            c3 = c2;
                            break;
                        case 194:
                            c = '\r';
                            int iLA7 = this.input.LA(2);
                            if (iLA7 == -1 || ((iLA7 >= 4 && iLA7 <= 6) || iLA7 == 11 || ((iLA7 >= 17 && iLA7 <= 19) || iLA7 == 21 || ((iLA7 >= 24 && iLA7 <= 25) || iLA7 == 27 || iLA7 == 33 || iLA7 == 37 || ((iLA7 >= 40 && iLA7 <= 42) || ((iLA7 >= 44 && iLA7 <= 46) || iLA7 == 48 || iLA7 == 50 || ((iLA7 >= 53 && iLA7 <= 58) || iLA7 == 62 || ((iLA7 >= 64 && iLA7 <= 67) || ((iLA7 >= 69 && iLA7 <= 70) || iLA7 == 72 || ((iLA7 >= 76 && iLA7 <= 77) || ((iLA7 >= 79 && iLA7 <= 85) || ((iLA7 >= 92 && iLA7 <= 94) || ((iLA7 >= 191 && iLA7 <= 195) || ((iLA7 >= 200 && iLA7 <= 202) || iLA7 == 204 || ((iLA7 >= 208 && iLA7 <= 209) || (iLA7 >= 213 && iLA7 <= 215)))))))))))))))) {
                                c3 = 2;
                            } else {
                                if (iLA7 != 20 && iLA7 != 197) {
                                    int iMark6 = this.input.mark();
                                    try {
                                        this.input.consume();
                                        throw new NoViableAltException("", 22, 3, this.input);
                                    } catch (Throwable th6) {
                                        this.input.rewind(iMark6);
                                        throw th6;
                                    }
                                }
                                c3 = c;
                            }
                            break;
                        case 195:
                            int iLA8 = this.input.LA(2);
                            if (iLA8 != -1 && ((iLA8 < 4 || iLA8 > 6) && iLA8 != 11 && ((iLA8 < 17 || iLA8 > 19) && iLA8 != 21 && ((iLA8 < 24 || iLA8 > 25) && iLA8 != 27 && iLA8 != 33 && iLA8 != 37 && ((iLA8 < 40 || iLA8 > 42) && ((iLA8 < 44 || iLA8 > 46) && iLA8 != 48 && iLA8 != 50 && ((iLA8 < 53 || iLA8 > 58) && iLA8 != 62 && ((iLA8 < 64 || iLA8 > 67) && ((iLA8 < 69 || iLA8 > 70) && iLA8 != 72 && ((iLA8 < 76 || iLA8 > 77) && ((iLA8 < 79 || iLA8 > 85) && ((iLA8 < 92 || iLA8 > 94) && ((iLA8 < 191 || iLA8 > 195) && ((iLA8 < 200 || iLA8 > 202) && iLA8 != 204 && ((iLA8 < 208 || iLA8 > 209) && (iLA8 < 213 || iLA8 > 215)))))))))))))))) {
                                if (iLA8 != 20 && iLA8 != 197) {
                                    int iMark7 = this.input.mark();
                                    try {
                                        this.input.consume();
                                        throw new NoViableAltException("", 22, 13, this.input);
                                    } catch (Throwable th7) {
                                        this.input.rewind(iMark7);
                                        throw th7;
                                    }
                                }
                                c = '\r';
                                c3 = c;
                            }
                            c2 = '\n';
                            c3 = c2;
                            break;
                        case 196:
                            c3 = 11;
                            break;
                        case 197:
                            c3 = 16;
                            break;
                        case 201:
                            int iLA9 = this.input.LA(2);
                            if (iLA9 == -1 || ((iLA9 >= 4 && iLA9 <= 6) || iLA9 == 11 || ((iLA9 >= 17 && iLA9 <= 19) || iLA9 == 21 || ((iLA9 >= 24 && iLA9 <= 25) || iLA9 == 27 || iLA9 == 33 || iLA9 == 37 || ((iLA9 >= 40 && iLA9 <= 42) || ((iLA9 >= 44 && iLA9 <= 46) || iLA9 == 48 || iLA9 == 50 || ((iLA9 >= 53 && iLA9 <= 58) || iLA9 == 62 || ((iLA9 >= 64 && iLA9 <= 67) || ((iLA9 >= 69 && iLA9 <= 70) || iLA9 == 72 || ((iLA9 >= 76 && iLA9 <= 77) || ((iLA9 >= 79 && iLA9 <= 85) || ((iLA9 >= 92 && iLA9 <= 94) || ((iLA9 >= 191 && iLA9 <= 195) || ((iLA9 >= 200 && iLA9 <= 202) || iLA9 == 204 || ((iLA9 >= 208 && iLA9 <= 209) || (iLA9 >= 213 && iLA9 <= 215)))))))))))))))) {
                                c3 = 2;
                            } else {
                                if (iLA9 != 20 && iLA9 != 197) {
                                    int iMark8 = this.input.mark();
                                    try {
                                        this.input.consume();
                                        throw new NoViableAltException("", 22, 2, this.input);
                                    } catch (Throwable th8) {
                                        this.input.rewind(iMark8);
                                        throw th8;
                                    }
                                }
                                c3 = '\r';
                            }
                            break;
                        case 207:
                            c3 = 3;
                            break;
                        case 211:
                            c3 = '\b';
                            break;
                        case 212:
                            c3 = '\f';
                            break;
                        default:
                            switch (iLA) {
                                case 39:
                                    c3 = 5;
                                    break;
                                case 40:
                                    int iLA10 = this.input.LA(2);
                                    if (iLA10 != -1 && ((iLA10 < 4 || iLA10 > 6) && iLA10 != 11 && ((iLA10 < 17 || iLA10 > 19) && iLA10 != 21 && ((iLA10 < 24 || iLA10 > 25) && iLA10 != 27 && iLA10 != 33 && iLA10 != 37 && ((iLA10 < 40 || iLA10 > 42) && ((iLA10 < 44 || iLA10 > 46) && iLA10 != 48 && iLA10 != 50 && ((iLA10 < 53 || iLA10 > 58) && iLA10 != 62 && ((iLA10 < 64 || iLA10 > 67) && ((iLA10 < 69 || iLA10 > 70) && iLA10 != 72 && ((iLA10 < 76 || iLA10 > 77) && ((iLA10 < 79 || iLA10 > 85) && ((iLA10 < 92 || iLA10 > 94) && ((iLA10 < 191 || iLA10 > 195) && ((iLA10 < 200 || iLA10 > 202) && iLA10 != 204 && ((iLA10 < 208 || iLA10 > 209) && (iLA10 < 213 || iLA10 > 215)))))))))))))))) {
                                        if (iLA10 != 20 && iLA10 != 197) {
                                            int iMark9 = this.input.mark();
                                            try {
                                                this.input.consume();
                                                throw new NoViableAltException("", 22, 6, this.input);
                                            } catch (Throwable th9) {
                                                this.input.rewind(iMark9);
                                                throw th9;
                                            }
                                        }
                                        c3 = '\r';
                                    }
                                    c2 = 5;
                                    c3 = c2;
                                    break;
                                default:
                                    switch (iLA) {
                                        default:
                                            switch (iLA) {
                                                default:
                                                    switch (iLA) {
                                                        case 64:
                                                        case 65:
                                                        case 66:
                                                        case 67:
                                                            break;
                                                        default:
                                                            throw new NoViableAltException("", 22, 0, this.input);
                                                    }
                                                    c3 = c;
                                                case 53:
                                                case 54:
                                                case 55:
                                                case 56:
                                                case 57:
                                                case 58:
                                                    c = '\r';
                                                    c3 = c;
                                                    break;
                                            }
                                        case 44:
                                        case 45:
                                        case 46:
                                            c = '\r';
                                            c3 = c;
                                            break;
                                    }
                                case 41:
                                    c = '\r';
                                    c3 = c;
                                    break;
                            }
                            break;
                    }
                } else {
                    c = '\r';
                    c3 = c;
                }
            }
            switch (c3) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 189, FOLLOW_LONG_LITERAL_in_literal2647)));
                    break;
                case 2:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_integer_literal_in_literal2653);
                    integer_literal_return integer_literal_returnVarInteger_literal = integer_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, integer_literal_returnVarInteger_literal.getTree());
                    break;
                case 3:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_literal2659)));
                    break;
                case 4:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_literal2665)));
                    break;
                case 5:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_float_literal_in_literal2671);
                    float_literal_return float_literal_returnVarFloat_literal = float_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, float_literal_returnVarFloat_literal.getTree());
                    break;
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_double_literal_in_literal2677);
                    double_literal_return double_literal_returnVarDouble_literal = double_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, double_literal_returnVarDouble_literal.getTree());
                    break;
                case 7:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_literal2683)));
                    break;
                case '\b':
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_literal2689)));
                    break;
                case '\t':
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 11, FOLLOW_BOOL_LITERAL_in_literal2695)));
                    break;
                case '\n':
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 195, FOLLOW_NULL_LITERAL_in_literal2701)));
                    break;
                case 11:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_array_literal_in_literal2707);
                    array_literal_return array_literal_returnVarArray_literal = array_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, array_literal_returnVarArray_literal.getTree());
                    break;
                case '\f':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_subannotation_in_literal2713);
                    subannotation_return subannotation_returnVarSubannotation = subannotation();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, subannotation_returnVarSubannotation.getTree());
                    break;
                case '\r':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_type_field_method_literal_in_literal2719);
                    type_field_method_literal_return type_field_method_literal_returnVarType_field_method_literal = type_field_method_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, type_field_method_literal_returnVarType_field_method_literal.getTree());
                    break;
                case 14:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_enum_literal_in_literal2725);
                    enum_literal_return enum_literal_returnVarEnum_literal = enum_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, enum_literal_returnVarEnum_literal.getTree());
                    break;
                case 15:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_method_handle_literal_in_literal2731);
                    method_handle_literal_return method_handle_literal_returnVarMethod_handle_literal = method_handle_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, method_handle_literal_returnVarMethod_handle_literal.getTree());
                    break;
                case 16:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_method_prototype_in_literal2737);
                    method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, method_prototype_returnVarMethod_prototype.getTree());
                    break;
                default:
                    commonTree = null;
                    break;
            }
            literal_returnVar.stop = this.input.LT(-1);
            literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(literal_returnVar.tree, literal_returnVar.start, literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, literal_returnVar.start, this.input.LT(-1), e);
        }
        return literal_returnVar;
    }

    public static class parsed_integer_literal_return extends ParserRuleReturnScope {
        CommonTree tree;
        public int value;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final parsed_integer_literal_return parsed_integer_literal() throws RecognitionException {
        parsed_integer_literal_return parsed_integer_literal_returnVar = new parsed_integer_literal_return();
        parsed_integer_literal_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            pushFollow(FOLLOW_integer_literal_in_parsed_integer_literal2750);
            integer_literal_return integer_literal_returnVarInteger_literal = integer_literal();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, integer_literal_returnVarInteger_literal.getTree());
            parsed_integer_literal_returnVar.value = LiteralTools.parseInt(integer_literal_returnVarInteger_literal != null ? this.input.toString(integer_literal_returnVarInteger_literal.start, integer_literal_returnVarInteger_literal.stop) : null);
            parsed_integer_literal_returnVar.stop = this.input.LT(-1);
            parsed_integer_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(parsed_integer_literal_returnVar.tree, parsed_integer_literal_returnVar.start, parsed_integer_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            parsed_integer_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, parsed_integer_literal_returnVar.start, this.input.LT(-1), e);
        }
        return parsed_integer_literal_returnVar;
    }

    public static class integral_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final integral_literal_return integral_literal() throws RecognitionException {
        char c;
        CommonTree commonTree;
        integral_literal_return integral_literal_returnVar = new integral_literal_return();
        integral_literal_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 12) {
                c = 5;
            } else if (iLA == 15) {
                c = 4;
            } else if (iLA == 189) {
                c = 1;
            } else if (iLA == 194 || iLA == 201) {
                c = 2;
            } else {
                if (iLA != 207) {
                    throw new NoViableAltException("", 23, 0, this.input);
                }
                c = 3;
            }
            if (c == 1) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 189, FOLLOW_LONG_LITERAL_in_integral_literal2762)));
            } else if (c == 2) {
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                pushFollow(FOLLOW_integer_literal_in_integral_literal2768);
                integer_literal_return integer_literal_returnVarInteger_literal = integer_literal();
                this.state._fsp--;
                this.adaptor.addChild(commonTree2, integer_literal_returnVarInteger_literal.getTree());
                commonTree = commonTree2;
            } else if (c == 3) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_integral_literal2774)));
            } else if (c == 4) {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_integral_literal2780)));
            } else if (c != 5) {
                commonTree = null;
            } else {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_integral_literal2786)));
            }
            integral_literal_returnVar.stop = this.input.LT(-1);
            integral_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(integral_literal_returnVar.tree, integral_literal_returnVar.start, integral_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            integral_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, integral_literal_returnVar.start, this.input.LT(-1), e);
        }
        return integral_literal_returnVar;
    }

    public static class fixed_32bit_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final fixed_32bit_literal_return fixed_32bit_literal() throws RecognitionException {
        char c;
        CommonTree commonTree;
        CommonTree commonTree2;
        fixed_32bit_literal_return fixed_32bit_literal_returnVar = new fixed_32bit_literal_return();
        fixed_32bit_literal_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 11) {
                c = 7;
            } else if (iLA == 12) {
                c = 4;
            } else if (iLA == 15) {
                c = 6;
            } else if (iLA == 189) {
                c = 1;
            } else if (iLA == 194 || iLA == 201) {
                c = 2;
            } else if (iLA != 207) {
                if (iLA != 39 && iLA != 40) {
                    throw new NoViableAltException("", 24, 0, this.input);
                }
                c = 5;
            } else {
                c = 3;
            }
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 189, FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2796)));
                    break;
                case 2:
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_integer_literal_in_fixed_32bit_literal2802);
                    integer_literal_return integer_literal_returnVarInteger_literal = integer_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree2, integer_literal_returnVarInteger_literal.getTree());
                    commonTree = commonTree2;
                    break;
                case 3:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2808)));
                    break;
                case 4:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2814)));
                    break;
                case 5:
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_float_literal_in_fixed_32bit_literal2820);
                    float_literal_return float_literal_returnVarFloat_literal = float_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree2, float_literal_returnVarFloat_literal.getTree());
                    commonTree = commonTree2;
                    break;
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2826)));
                    break;
                case 7:
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 11, FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2832)));
                    break;
                default:
                    commonTree = null;
                    break;
            }
            fixed_32bit_literal_returnVar.stop = this.input.LT(-1);
            fixed_32bit_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(fixed_32bit_literal_returnVar.tree, fixed_32bit_literal_returnVar.start, fixed_32bit_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            fixed_32bit_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, fixed_32bit_literal_returnVar.start, this.input.LT(-1), e);
        }
        return fixed_32bit_literal_returnVar;
    }

    public static class fixed_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final fixed_literal_return fixed_literal() throws RecognitionException {
        char c;
        CommonTree commonTree;
        CommonTree commonTree2;
        fixed_literal_return fixed_literal_returnVar = new fixed_literal_return();
        fixed_literal_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 11) {
                c = '\b';
            } else if (iLA == 12) {
                c = 4;
            } else if (iLA == 15) {
                c = 7;
            } else if (iLA == 189) {
                c = 2;
            } else if (iLA == 194 || iLA == 201) {
                c = 1;
            } else if (iLA == 207) {
                c = 3;
            } else if (iLA == 23 || iLA == 24) {
                c = 6;
            } else {
                if (iLA != 39 && iLA != 40) {
                    throw new NoViableAltException("", 25, 0, this.input);
                }
                c = 5;
            }
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_integer_literal_in_fixed_literal2842);
                    integer_literal_return integer_literal_returnVarInteger_literal = integer_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, integer_literal_returnVarInteger_literal.getTree());
                    commonTree2 = commonTree;
                    break;
                case 2:
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 189, FOLLOW_LONG_LITERAL_in_fixed_literal2848)));
                    break;
                case 3:
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 207, FOLLOW_SHORT_LITERAL_in_fixed_literal2854)));
                    break;
                case 4:
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 12, FOLLOW_BYTE_LITERAL_in_fixed_literal2860)));
                    break;
                case 5:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_float_literal_in_fixed_literal2866);
                    float_literal_return float_literal_returnVarFloat_literal = float_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, float_literal_returnVarFloat_literal.getTree());
                    commonTree2 = commonTree;
                    break;
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_double_literal_in_fixed_literal2872);
                    double_literal_return double_literal_returnVarDouble_literal = double_literal();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, double_literal_returnVarDouble_literal.getTree());
                    commonTree2 = commonTree;
                    break;
                case 7:
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 15, FOLLOW_CHAR_LITERAL_in_fixed_literal2878)));
                    break;
                case '\b':
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree2, (CommonTree) this.adaptor.create((Token) match(this.input, 11, FOLLOW_BOOL_LITERAL_in_fixed_literal2884)));
                    break;
                default:
                    commonTree2 = null;
                    break;
            }
            fixed_literal_returnVar.stop = this.input.LT(-1);
            fixed_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree2);
            this.adaptor.setTokenBoundaries(fixed_literal_returnVar.tree, fixed_literal_returnVar.start, fixed_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            fixed_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, fixed_literal_returnVar.start, this.input.LT(-1), e);
        }
        return fixed_literal_returnVar;
    }

    public static class array_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final array_literal_return array_literal() throws RecognitionException {
        array_literal_return array_literal_returnVar = new array_literal_return();
        array_literal_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule literal");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_array_literal2894));
            int iLA = this.input.LA(1);
            if (iLA == 4 || iLA == 6 || iLA == 8 || ((iLA >= 11 && iLA <= 12) || ((iLA >= 15 && iLA <= 16) || ((iLA >= 23 && iLA <= 24) || iLA == 34 || ((iLA >= 39 && iLA <= 41) || ((iLA >= 44 && iLA <= 46) || iLA == 48 || iLA == 50 || ((iLA >= 53 && iLA <= 58) || iLA == 62 || ((iLA >= 64 && iLA <= 67) || ((iLA >= 69 && iLA <= 70) || iLA == 72 || ((iLA >= 76 && iLA <= 77) || ((iLA >= 79 && iLA <= 85) || ((iLA >= 92 && iLA <= 94) || ((iLA >= 189 && iLA <= 190) || ((iLA >= 192 && iLA <= 197) || ((iLA >= 200 && iLA <= 202) || iLA == 204 || ((iLA >= 207 && iLA <= 208) || ((iLA >= 211 && iLA <= 212) || (iLA >= 214 && iLA <= 215)))))))))))))))))) {
                pushFollow(FOLLOW_literal_in_array_literal2897);
                literal_return literal_returnVarLiteral = literal();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(literal_returnVarLiteral.getTree());
                while (this.input.LA(1) == 21) {
                    rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_array_literal2900));
                    pushFollow(FOLLOW_literal_in_array_literal2902);
                    literal_return literal_returnVarLiteral2 = literal();
                    this.state._fsp--;
                    rewriteRuleSubtreeStream.add(literal_returnVarLiteral2.getTree());
                }
            } else if (iLA != 18) {
                throw new NoViableAltException("", 27, 0, this.input);
            }
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_array_literal2910));
            array_literal_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", array_literal_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(110, array_literal_returnVar.start, "I_ENCODED_ARRAY"), commonTree2);
            while (rewriteRuleSubtreeStream.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            }
            rewriteRuleSubtreeStream.reset();
            this.adaptor.addChild(commonTree, commonTree3);
            array_literal_returnVar.tree = commonTree;
            array_literal_returnVar.stop = this.input.LT(-1);
            array_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(array_literal_returnVar.tree, array_literal_returnVar.start, array_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            array_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, array_literal_returnVar.start, this.input.LT(-1), e);
        }
        return array_literal_returnVar;
    }

    public static class annotation_element_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final annotation_element_return annotation_element() throws RecognitionException {
        annotation_element_return annotation_element_returnVar = new annotation_element_return();
        annotation_element_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token EQUAL");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule simple_name");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule literal");
        try {
            pushFollow(FOLLOW_simple_name_in_annotation_element2934);
            simple_name_return simple_name_returnVarSimple_name = simple_name();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(simple_name_returnVarSimple_name.getTree());
            rewriteRuleTokenStream.add((Token) match(this.input, 36, FOLLOW_EQUAL_in_annotation_element2936));
            pushFollow(FOLLOW_literal_in_annotation_element2938);
            literal_return literal_returnVarLiteral = literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(literal_returnVarLiteral.getTree());
            annotation_element_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", annotation_element_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(101, annotation_element_returnVar.start, "I_ANNOTATION_ELEMENT"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            annotation_element_returnVar.tree = commonTree;
            annotation_element_returnVar.stop = this.input.LT(-1);
            annotation_element_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(annotation_element_returnVar.tree, annotation_element_returnVar.start, annotation_element_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            annotation_element_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, annotation_element_returnVar.start, this.input.LT(-1), e);
        }
        return annotation_element_returnVar;
    }

    public static class annotation_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final annotation_return annotation() throws RecognitionException {
        annotation_return annotation_returnVar = new annotation_return();
        annotation_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token ANNOTATION_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token ANNOTATION_VISIBILITY");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token END_ANNOTATION_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation_element");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 5, FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2963));
            rewriteRuleTokenStream2.add((Token) match(this.input, 6, FOLLOW_ANNOTATION_VISIBILITY_in_annotation2965));
            rewriteRuleTokenStream3.add((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_annotation2967));
            while (true) {
                int iLA = this.input.LA(1);
                if (iLA != 4 && iLA != 6 && iLA != 11 && iLA != 24 && ((iLA < 40 || iLA > 41) && ((iLA < 44 || iLA > 46) && iLA != 48 && iLA != 50 && ((iLA < 53 || iLA > 58) && iLA != 62 && ((iLA < 64 || iLA > 67) && ((iLA < 69 || iLA > 70) && iLA != 72 && ((iLA < 76 || iLA > 77) && ((iLA < 79 || iLA > 85) && ((iLA < 92 || iLA > 94) && ((iLA < 192 || iLA > 195) && ((iLA < 200 || iLA > 202) && iLA != 204 && iLA != 208 && (iLA < 214 || iLA > 215)))))))))))) {
                    break;
                }
                pushFollow(FOLLOW_annotation_element_in_annotation2973);
                annotation_element_return annotation_element_returnVarAnnotation_element = annotation_element();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(annotation_element_returnVarAnnotation_element.getTree());
            }
            rewriteRuleTokenStream4.add((Token) match(this.input, 25, FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2976));
            annotation_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", annotation_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(99, annotation_returnVar.start, "I_ANNOTATION"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor2 = this.adaptor;
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(183, annotation_returnVar.start, "I_SUBANNOTATION"), commonTree4);
            this.adaptor.addChild(commonTree5, rewriteRuleTokenStream3.nextNode());
            while (rewriteRuleSubtreeStream.hasNext()) {
                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree());
            }
            rewriteRuleSubtreeStream.reset();
            this.adaptor.addChild(commonTree3, commonTree5);
            this.adaptor.addChild(commonTree, commonTree3);
            annotation_returnVar.tree = commonTree;
            annotation_returnVar.stop = this.input.LT(-1);
            annotation_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(annotation_returnVar.tree, annotation_returnVar.start, annotation_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            annotation_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, annotation_returnVar.start, this.input.LT(-1), e);
        }
        return annotation_returnVar;
    }

    public static class subannotation_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final subannotation_return subannotation() throws RecognitionException {
        subannotation_return subannotation_returnVar = new subannotation_return();
        subannotation_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token SUBANNOTATION_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token END_SUBANNOTATION_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation_element");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 212, FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation3009));
            rewriteRuleTokenStream2.add((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_subannotation3011));
            while (true) {
                int iLA = this.input.LA(1);
                if (iLA != 4 && iLA != 6 && iLA != 11 && iLA != 24 && ((iLA < 40 || iLA > 41) && ((iLA < 44 || iLA > 46) && iLA != 48 && iLA != 50 && ((iLA < 53 || iLA > 58) && iLA != 62 && ((iLA < 64 || iLA > 67) && ((iLA < 69 || iLA > 70) && iLA != 72 && ((iLA < 76 || iLA > 77) && ((iLA < 79 || iLA > 85) && ((iLA < 92 || iLA > 94) && ((iLA < 192 || iLA > 195) && ((iLA < 200 || iLA > 202) && iLA != 204 && iLA != 208 && (iLA < 214 || iLA > 215)))))))))))) {
                    break;
                }
                pushFollow(FOLLOW_annotation_element_in_subannotation3013);
                annotation_element_return annotation_element_returnVarAnnotation_element = annotation_element();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(annotation_element_returnVarAnnotation_element.getTree());
            }
            rewriteRuleTokenStream3.add((Token) match(this.input, 33, FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation3016));
            subannotation_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", subannotation_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(183, subannotation_returnVar.start, "I_SUBANNOTATION"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            while (rewriteRuleSubtreeStream.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            }
            rewriteRuleSubtreeStream.reset();
            this.adaptor.addChild(commonTree, commonTree3);
            subannotation_returnVar.tree = commonTree;
            subannotation_returnVar.stop = this.input.LT(-1);
            subannotation_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(subannotation_returnVar.tree, subannotation_returnVar.start, subannotation_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            subannotation_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, subannotation_returnVar.start, this.input.LT(-1), e);
        }
        return subannotation_returnVar;
    }

    public static class enum_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final enum_literal_return enum_literal() throws RecognitionException {
        enum_literal_return enum_literal_returnVar = new enum_literal_return();
        enum_literal_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token ENUM_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule field_reference");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 34, FOLLOW_ENUM_DIRECTIVE_in_enum_literal3043));
            pushFollow(FOLLOW_field_reference_in_enum_literal3045);
            field_reference_return field_reference_returnVarField_reference = field_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree());
            enum_literal_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", enum_literal_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(111, "I_ENCODED_ENUM"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            enum_literal_returnVar.tree = commonTree;
            enum_literal_returnVar.stop = this.input.LT(-1);
            enum_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(enum_literal_returnVar.tree, enum_literal_returnVar.start, enum_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            enum_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, enum_literal_returnVar.start, this.input.LT(-1), e);
        }
        return enum_literal_returnVar;
    }

    public static class type_field_method_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:301:0x02eb  */
    /* JADX WARN: Code duplicated, block: B:406:0x03e5  */
    public final type_field_method_literal_return type_field_method_literal() throws RecognitionException {
        char c;
        CommonTree commonTree;
        CommonTree commonTree2;
        type_field_method_literal_return type_field_method_literal_returnVar = new type_field_method_literal_return();
        type_field_method_literal_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = new RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor");
        try {
            int iLA = this.input.LA(1);
            if (iLA != 4 && iLA != 6 && iLA != 40 && iLA != 41 && iLA != 69 && iLA != 70 && iLA != 76 && iLA != 77) {
                switch (iLA) {
                    case 6:
                    case 11:
                    case 24:
                    case 48:
                    case 50:
                    case 62:
                    case 72:
                    case 79:
                    case 80:
                    case 81:
                    case 82:
                    case 83:
                    case 84:
                    case 85:
                    case 190:
                    case 204:
                    case 208:
                    case 214:
                        c = 2;
                        break;
                    case 8:
                        int iLA2 = this.input.LA(2);
                        if (iLA2 != 16 && iLA2 != 202) {
                            int iMark = this.input.mark();
                            try {
                                this.input.consume();
                                throw new NoViableAltException("", 32, 2, this.input);
                            } catch (Throwable th) {
                                this.input.rewind(iMark);
                                throw th;
                            }
                        }
                        int iLA3 = this.input.LA(3);
                        if (iLA3 == -1 || ((iLA3 >= 4 && iLA3 <= 6) || iLA3 == 11 || ((iLA3 >= 17 && iLA3 <= 19) || iLA3 == 21 || ((iLA3 >= 24 && iLA3 <= 25) || iLA3 == 27 || iLA3 == 33 || iLA3 == 37 || ((iLA3 >= 40 && iLA3 <= 42) || ((iLA3 >= 44 && iLA3 <= 46) || iLA3 == 48 || iLA3 == 50 || ((iLA3 >= 53 && iLA3 <= 58) || iLA3 == 62 || ((iLA3 >= 64 && iLA3 <= 67) || ((iLA3 >= 69 && iLA3 <= 70) || iLA3 == 72 || ((iLA3 >= 76 && iLA3 <= 77) || ((iLA3 >= 79 && iLA3 <= 85) || ((iLA3 >= 92 && iLA3 <= 94) || ((iLA3 >= 191 && iLA3 <= 195) || ((iLA3 >= 200 && iLA3 <= 202) || iLA3 == 204 || ((iLA3 >= 208 && iLA3 <= 209) || (iLA3 >= 213 && iLA3 <= 215)))))))))))))))) {
                            c = 1;
                        } else if (iLA3 == 9) {
                            c = 2;
                        } else {
                            int iMark2 = this.input.mark();
                            for (int i = 0; i < 2; i++) {
                                try {
                                    this.input.consume();
                                } catch (Throwable th2) {
                                    this.input.rewind(iMark2);
                                    throw th2;
                                }
                            }
                            throw new NoViableAltException("", 32, 7, this.input);
                        }
                        break;
                    case 16:
                        int iLA4 = this.input.LA(2);
                        if (iLA4 == -1 || ((iLA4 >= 4 && iLA4 <= 6) || iLA4 == 11 || ((iLA4 >= 17 && iLA4 <= 19) || iLA4 == 21 || ((iLA4 >= 24 && iLA4 <= 25) || iLA4 == 27 || iLA4 == 33 || iLA4 == 37 || ((iLA4 >= 40 && iLA4 <= 42) || ((iLA4 >= 44 && iLA4 <= 46) || iLA4 == 48 || iLA4 == 50 || ((iLA4 >= 53 && iLA4 <= 58) || iLA4 == 62 || ((iLA4 >= 64 && iLA4 <= 67) || ((iLA4 >= 69 && iLA4 <= 70) || iLA4 == 72 || ((iLA4 >= 76 && iLA4 <= 77) || ((iLA4 >= 79 && iLA4 <= 85) || ((iLA4 >= 92 && iLA4 <= 94) || ((iLA4 >= 191 && iLA4 <= 195) || ((iLA4 >= 200 && iLA4 <= 202) || iLA4 == 204 || ((iLA4 >= 208 && iLA4 <= 209) || (iLA4 >= 213 && iLA4 <= 215)))))))))))))))) {
                            c = 1;
                        } else {
                            if (iLA4 != 9) {
                                int iMark3 = this.input.mark();
                                try {
                                    this.input.consume();
                                    throw new NoViableAltException("", 32, 1, this.input);
                                } catch (Throwable th3) {
                                    this.input.rewind(iMark3);
                                    throw th3;
                                }
                            }
                            c = 2;
                        }
                        break;
                    case 215:
                        int iLA5 = this.input.LA(2);
                        if (iLA5 == 20 || iLA5 == 197) {
                            c = 2;
                        } else {
                            if (iLA5 != -1 && ((iLA5 < 4 || iLA5 > 6) && iLA5 != 11 && ((iLA5 < 17 || iLA5 > 19) && iLA5 != 21 && ((iLA5 < 24 || iLA5 > 25) && iLA5 != 27 && iLA5 != 33 && iLA5 != 37 && ((iLA5 < 40 || iLA5 > 42) && ((iLA5 < 44 || iLA5 > 46) && iLA5 != 48 && iLA5 != 50 && ((iLA5 < 53 || iLA5 > 58) && iLA5 != 62 && ((iLA5 < 64 || iLA5 > 67) && ((iLA5 < 69 || iLA5 > 70) && iLA5 != 72 && ((iLA5 < 76 || iLA5 > 77) && ((iLA5 < 79 || iLA5 > 85) && ((iLA5 < 92 || iLA5 > 94) && ((iLA5 < 191 || iLA5 > 195) && ((iLA5 < 200 || iLA5 > 202) && iLA5 != 204 && ((iLA5 < 208 || iLA5 > 209) && (iLA5 < 213 || iLA5 > 215)))))))))))))))) {
                                int iMark4 = this.input.mark();
                                try {
                                    this.input.consume();
                                    throw new NoViableAltException("", 32, 5, this.input);
                                } catch (Throwable th4) {
                                    this.input.rewind(iMark4);
                                    throw th4;
                                }
                            }
                            c = 4;
                        }
                        break;
                    default:
                        switch (iLA) {
                            default:
                                switch (iLA) {
                                    default:
                                        switch (iLA) {
                                            default:
                                                switch (iLA) {
                                                    default:
                                                        switch (iLA) {
                                                            default:
                                                                switch (iLA) {
                                                                    case 202:
                                                                        int iLA6 = this.input.LA(2);
                                                                        if (iLA6 != 20 && iLA6 != 197) {
                                                                            if (iLA6 != -1 && ((iLA6 < 4 || iLA6 > 6) && iLA6 != 11 && ((iLA6 < 17 || iLA6 > 19) && iLA6 != 21 && ((iLA6 < 24 || iLA6 > 25) && iLA6 != 27 && iLA6 != 33 && iLA6 != 37 && ((iLA6 < 40 || iLA6 > 42) && ((iLA6 < 44 || iLA6 > 46) && iLA6 != 48 && iLA6 != 50 && ((iLA6 < 53 || iLA6 > 58) && iLA6 != 62 && ((iLA6 < 64 || iLA6 > 67) && ((iLA6 < 69 || iLA6 > 70) && iLA6 != 72 && ((iLA6 < 76 || iLA6 > 77) && ((iLA6 < 79 || iLA6 > 85) && ((iLA6 < 92 || iLA6 > 94) && ((iLA6 < 191 || iLA6 > 195) && ((iLA6 < 200 || iLA6 > 202) && iLA6 != 204 && ((iLA6 < 208 || iLA6 > 209) && (iLA6 < 213 || iLA6 > 215)))))))))))))))) {
                                                                                int iMark5 = this.input.mark();
                                                                                try {
                                                                                    this.input.consume();
                                                                                    throw new NoViableAltException("", 32, 4, this.input);
                                                                                } catch (Throwable th5) {
                                                                                    this.input.rewind(iMark5);
                                                                                    throw th5;
                                                                                }
                                                                            }
                                                                            c = 3;
                                                                            break;
                                                                        }
                                                                    case 200:
                                                                    case 201:
                                                                        c = 2;
                                                                        break;
                                                                    default:
                                                                        throw new NoViableAltException("", 32, 0, this.input);
                                                                }
                                                            case 192:
                                                            case 193:
                                                            case 194:
                                                            case 195:
                                                                c = 2;
                                                                break;
                                                        }
                                                    case 92:
                                                    case 93:
                                                    case 94:
                                                        c = 2;
                                                        break;
                                                }
                                            case 64:
                                            case 65:
                                            case 66:
                                            case 67:
                                                c = 2;
                                                break;
                                        }
                                    case 53:
                                    case 54:
                                    case 55:
                                    case 56:
                                    case 57:
                                    case 58:
                                        c = 2;
                                        break;
                                }
                            case 44:
                            case 45:
                            case 46:
                                c = 2;
                                break;
                        }
                        break;
                }
            } else {
                c = 2;
            }
            if (c != 1) {
                commonTree = null;
                if (c == 2) {
                    int iLA7 = this.input.LA(1);
                    if (iLA7 == 8 || iLA7 == 16) {
                        pushFollow(FOLLOW_reference_type_descriptor_in_type_field_method_literal3074);
                        reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor();
                        this.state._fsp--;
                        rewriteRuleSubtreeStream4.add(reference_type_descriptor_returnVarReference_type_descriptor.getTree());
                        rewriteRuleTokenStream.add((Token) match(this.input, 9, FOLLOW_ARROW_in_type_field_method_literal3076));
                    }
                    int iPredict = this.dfa31.predict(this.input);
                    if (iPredict == 1) {
                        pushFollow(FOLLOW_member_name_in_type_field_method_literal3088);
                        member_name_return member_name_returnVarMember_name = member_name();
                        this.state._fsp--;
                        rewriteRuleSubtreeStream3.add(member_name_returnVarMember_name.getTree());
                        rewriteRuleTokenStream2.add((Token) match(this.input, 20, FOLLOW_COLON_in_type_field_method_literal3090));
                        pushFollow(FOLLOW_nonvoid_type_descriptor_in_type_field_method_literal3092);
                        nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
                        this.state._fsp--;
                        rewriteRuleSubtreeStream2.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
                        type_field_method_literal_returnVar.tree = null;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", type_field_method_literal_returnVar.getTree());
                        commonTree2 = (CommonTree) this.adaptor.nil();
                        CommonTree commonTree3 = (CommonTree) this.adaptor.nil();
                        TreeAdaptor treeAdaptor = this.adaptor;
                        CommonTree commonTree4 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(112, "I_ENCODED_FIELD"), commonTree3);
                        if (rewriteRuleSubtreeStream4.hasNext()) {
                            this.adaptor.addChild(commonTree4, rewriteRuleSubtreeStream4.nextTree());
                        }
                        rewriteRuleSubtreeStream4.reset();
                        this.adaptor.addChild(commonTree4, rewriteRuleSubtreeStream3.nextTree());
                        this.adaptor.addChild(commonTree4, rewriteRuleSubtreeStream2.nextTree());
                        this.adaptor.addChild(commonTree2, commonTree4);
                        type_field_method_literal_returnVar.tree = commonTree2;
                    } else if (iPredict == 2) {
                        pushFollow(FOLLOW_member_name_in_type_field_method_literal3115);
                        member_name_return member_name_returnVarMember_name2 = member_name();
                        this.state._fsp--;
                        rewriteRuleSubtreeStream3.add(member_name_returnVarMember_name2.getTree());
                        pushFollow(FOLLOW_method_prototype_in_type_field_method_literal3117);
                        method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype();
                        this.state._fsp--;
                        rewriteRuleSubtreeStream.add(method_prototype_returnVarMethod_prototype.getTree());
                        type_field_method_literal_returnVar.tree = null;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", type_field_method_literal_returnVar.getTree());
                        commonTree2 = (CommonTree) this.adaptor.nil();
                        CommonTree commonTree5 = (CommonTree) this.adaptor.nil();
                        TreeAdaptor treeAdaptor2 = this.adaptor;
                        CommonTree commonTree6 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(113, "I_ENCODED_METHOD"), commonTree5);
                        if (rewriteRuleSubtreeStream4.hasNext()) {
                            this.adaptor.addChild(commonTree6, rewriteRuleSubtreeStream4.nextTree());
                        }
                        rewriteRuleSubtreeStream4.reset();
                        this.adaptor.addChild(commonTree6, rewriteRuleSubtreeStream3.nextTree());
                        this.adaptor.addChild(commonTree6, rewriteRuleSubtreeStream.nextTree());
                        this.adaptor.addChild(commonTree2, commonTree6);
                        type_field_method_literal_returnVar.tree = commonTree2;
                    }
                    commonTree = commonTree2;
                } else if (c == 3) {
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 202, FOLLOW_PRIMITIVE_TYPE_in_type_field_method_literal3150)));
                } else if (c == 4) {
                    commonTree = (CommonTree) this.adaptor.nil();
                    this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 215, FOLLOW_VOID_TYPE_in_type_field_method_literal3156)));
                }
            } else {
                commonTree = (CommonTree) this.adaptor.nil();
                pushFollow(FOLLOW_reference_type_descriptor_in_type_field_method_literal3065);
                reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor2 = reference_type_descriptor();
                this.state._fsp--;
                this.adaptor.addChild(commonTree, reference_type_descriptor_returnVarReference_type_descriptor2.getTree());
            }
            type_field_method_literal_returnVar.stop = this.input.LT(-1);
            type_field_method_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(type_field_method_literal_returnVar.tree, type_field_method_literal_returnVar.start, type_field_method_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            type_field_method_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, type_field_method_literal_returnVar.start, this.input.LT(-1), e);
        }
        return type_field_method_literal_returnVar;
    }

    public static class call_site_reference_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final call_site_reference_return call_site_reference() throws RecognitionException {
        call_site_reference_return call_site_reference_returnVar = new call_site_reference_return();
        call_site_reference_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_PAREN");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token AT");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream5 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_PAREN");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_reference");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule simple_name");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = new RewriteRuleSubtreeStream(this.adaptor, "rule literal");
        try {
            pushFollow(FOLLOW_simple_name_in_call_site_reference3166);
            simple_name_return simple_name_returnVarSimple_name = simple_name();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(simple_name_returnVarSimple_name.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 197, FOLLOW_OPEN_PAREN_in_call_site_reference3168));
            rewriteRuleTokenStream4.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_call_site_reference3170));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_call_site_reference3172));
            pushFollow(FOLLOW_method_prototype_in_call_site_reference3174);
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype();
            this.state._fsp--;
            rewriteRuleSubtreeStream3.add(method_prototype_returnVarMethod_prototype.getTree());
            while (this.input.LA(1) == 21) {
                rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_call_site_reference3177));
                pushFollow(FOLLOW_literal_in_call_site_reference3179);
                literal_return literal_returnVarLiteral = literal();
                this.state._fsp--;
                rewriteRuleSubtreeStream4.add(literal_returnVarLiteral.getTree());
            }
            rewriteRuleTokenStream5.add((Token) match(this.input, 19, FOLLOW_CLOSE_PAREN_in_call_site_reference3183));
            rewriteRuleTokenStream3.add((Token) match(this.input, 10, FOLLOW_AT_in_call_site_reference3185));
            pushFollow(FOLLOW_method_reference_in_call_site_reference3187);
            method_reference_return method_reference_returnVarMethod_reference = method_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree());
            call_site_reference_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", call_site_reference_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(105, "I_CALL_SITE_REFERENCE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree());
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor2 = this.adaptor;
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(104, "I_CALL_SITE_EXTRA_ARGUMENTS"), commonTree4);
            while (rewriteRuleSubtreeStream4.hasNext()) {
                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream4.nextTree());
            }
            rewriteRuleSubtreeStream4.reset();
            this.adaptor.addChild(commonTree3, commonTree5);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            call_site_reference_returnVar.tree = commonTree;
            call_site_reference_returnVar.stop = this.input.LT(-1);
            call_site_reference_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(call_site_reference_returnVar.tree, call_site_reference_returnVar.start, call_site_reference_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            call_site_reference_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, call_site_reference_returnVar.start, this.input.LT(-1), e);
        }
        return call_site_reference_returnVar;
    }

    public static class method_handle_reference_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final method_handle_reference_return method_handle_reference() throws RecognitionException {
        char c;
        method_handle_reference_return method_handle_reference_returnVar = new method_handle_reference_return();
        method_handle_reference_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token AT");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token METHOD_HANDLE_TYPE_FIELD");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token METHOD_HANDLE_TYPE_METHOD");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_reference");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule field_reference");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 82) {
                c = 3;
            } else if (iLA == 192) {
                c = 1;
            } else {
                if (iLA != 193) {
                    throw new NoViableAltException("", 34, 0, this.input);
                }
                c = 2;
            }
            CommonTree commonTree = null;
            if (c == 1) {
                rewriteRuleTokenStream3.add((Token) match(this.input, 192, FOLLOW_METHOD_HANDLE_TYPE_FIELD_in_method_handle_reference3231));
                rewriteRuleTokenStream.add((Token) match(this.input, 10, FOLLOW_AT_in_method_handle_reference3233));
                pushFollow(FOLLOW_field_reference_in_method_handle_reference3235);
                field_reference_return field_reference_returnVarField_reference = field_reference();
                this.state._fsp--;
                rewriteRuleSubtreeStream2.add(field_reference_returnVarField_reference.getTree());
                method_handle_reference_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_handle_reference_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, rewriteRuleTokenStream3.nextNode());
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream2.nextTree());
                method_handle_reference_returnVar.tree = commonTree;
            } else if (c == 2) {
                rewriteRuleTokenStream4.add((Token) match(this.input, 193, FOLLOW_METHOD_HANDLE_TYPE_METHOD_in_method_handle_reference3247));
                rewriteRuleTokenStream.add((Token) match(this.input, 10, FOLLOW_AT_in_method_handle_reference3249));
                pushFollow(FOLLOW_method_reference_in_method_handle_reference3251);
                method_reference_return method_reference_returnVarMethod_reference = method_reference();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree());
                method_handle_reference_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_handle_reference_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, rewriteRuleTokenStream4.nextNode());
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree());
                method_handle_reference_returnVar.tree = commonTree;
            } else if (c == 3) {
                rewriteRuleTokenStream2.add((Token) match(this.input, 82, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_method_handle_reference3263));
                rewriteRuleTokenStream.add((Token) match(this.input, 10, FOLLOW_AT_in_method_handle_reference3265));
                pushFollow(FOLLOW_method_reference_in_method_handle_reference3267);
                method_reference_return method_reference_returnVarMethod_reference2 = method_reference();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference2.getTree());
                method_handle_reference_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_handle_reference_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, rewriteRuleTokenStream2.nextNode());
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree());
                method_handle_reference_returnVar.tree = commonTree;
            }
            method_handle_reference_returnVar.stop = this.input.LT(-1);
            method_handle_reference_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(method_handle_reference_returnVar.tree, method_handle_reference_returnVar.start, method_handle_reference_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            method_handle_reference_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, method_handle_reference_returnVar.start, this.input.LT(-1), e);
        }
        return method_handle_reference_returnVar;
    }

    public static class method_handle_literal_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final method_handle_literal_return method_handle_literal() throws RecognitionException {
        method_handle_literal_return method_handle_literal_returnVar = new method_handle_literal_return();
        method_handle_literal_returnVar.start = this.input.LT(1);
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_handle_reference");
        try {
            pushFollow(FOLLOW_method_handle_reference_in_method_handle_literal3283);
            method_handle_reference_return method_handle_reference_returnVarMethod_handle_reference = method_handle_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(method_handle_reference_returnVarMethod_handle_reference.getTree());
            method_handle_literal_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_handle_literal_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(114, "I_ENCODED_METHOD_HANDLE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            method_handle_literal_returnVar.tree = commonTree;
            method_handle_literal_returnVar.stop = this.input.LT(-1);
            method_handle_literal_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(method_handle_literal_returnVar.tree, method_handle_literal_returnVar.start, method_handle_literal_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            method_handle_literal_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, method_handle_literal_returnVar.start, this.input.LT(-1), e);
        }
        return method_handle_literal_returnVar;
    }

    public static class method_reference_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final method_reference_return method_reference() throws RecognitionException {
        method_reference_return method_reference_returnVar = new method_reference_return();
        method_reference_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 8 || iLA == 16) {
                pushFollow(FOLLOW_reference_type_descriptor_in_method_reference3304);
                reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor();
                this.state._fsp--;
                rewriteRuleSubtreeStream3.add(reference_type_descriptor_returnVarReference_type_descriptor.getTree());
                rewriteRuleTokenStream.add((Token) match(this.input, 9, FOLLOW_ARROW_in_method_reference3306));
            }
            pushFollow(FOLLOW_member_name_in_method_reference3310);
            member_name_return member_name_returnVarMember_name = member_name();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(member_name_returnVarMember_name.getTree());
            pushFollow(FOLLOW_method_prototype_in_method_reference3312);
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(method_prototype_returnVarMethod_prototype.getTree());
            method_reference_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", method_reference_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            if (rewriteRuleSubtreeStream3.hasNext()) {
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream3.nextTree());
            }
            rewriteRuleSubtreeStream3.reset();
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree());
            method_reference_returnVar.tree = commonTree;
            method_reference_returnVar.stop = this.input.LT(-1);
            method_reference_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(method_reference_returnVar.tree, method_reference_returnVar.start, method_reference_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            method_reference_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, method_reference_returnVar.start, this.input.LT(-1), e);
        }
        return method_reference_returnVar;
    }

    public static class field_reference_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final field_reference_return field_reference() throws RecognitionException {
        field_reference_return field_reference_returnVar = new field_reference_return();
        field_reference_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 8 || iLA == 16) {
                pushFollow(FOLLOW_reference_type_descriptor_in_field_reference3334);
                reference_type_descriptor_return reference_type_descriptor_returnVarReference_type_descriptor = reference_type_descriptor();
                this.state._fsp--;
                rewriteRuleSubtreeStream3.add(reference_type_descriptor_returnVarReference_type_descriptor.getTree());
                rewriteRuleTokenStream.add((Token) match(this.input, 9, FOLLOW_ARROW_in_field_reference3336));
            }
            pushFollow(FOLLOW_member_name_in_field_reference3340);
            member_name_return member_name_returnVarMember_name = member_name();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(member_name_returnVarMember_name.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 20, FOLLOW_COLON_in_field_reference3342));
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_field_reference3344);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
            field_reference_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", field_reference_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            if (rewriteRuleSubtreeStream3.hasNext()) {
                this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream3.nextTree());
            }
            rewriteRuleSubtreeStream3.reset();
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree());
            field_reference_returnVar.tree = commonTree;
            field_reference_returnVar.stop = this.input.LT(-1);
            field_reference_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(field_reference_returnVar.tree, field_reference_returnVar.start, field_reference_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            field_reference_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, field_reference_returnVar.start, this.input.LT(-1), e);
        }
        return field_reference_returnVar;
    }

    public static class label_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final label_return label() throws RecognitionException {
        label_return label_returnVar = new label_return();
        label_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule simple_name");
        try {
            Token token = (Token) match(this.input, 20, FOLLOW_COLON_in_label3365);
            rewriteRuleTokenStream.add(token);
            pushFollow(FOLLOW_simple_name_in_label3367);
            simple_name_return simple_name_returnVarSimple_name = simple_name();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(simple_name_returnVarSimple_name.getTree());
            label_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", label_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(122, token, "I_LABEL"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            label_returnVar.tree = commonTree;
            label_returnVar.stop = this.input.LT(-1);
            label_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(label_returnVar.tree, label_returnVar.start, label_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            label_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, label_returnVar.start, this.input.LT(-1), e);
        }
        return label_returnVar;
    }

    public static class label_ref_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final label_ref_return label_ref() throws RecognitionException {
        label_ref_return label_ref_returnVar = new label_ref_return();
        label_ref_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule simple_name");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 20, FOLLOW_COLON_in_label_ref3386));
            pushFollow(FOLLOW_simple_name_in_label_ref3388);
            simple_name_return simple_name_returnVarSimple_name = simple_name();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(simple_name_returnVarSimple_name.getTree());
            label_ref_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", label_ref_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            this.adaptor.addChild(commonTree, rewriteRuleSubtreeStream.nextTree());
            label_ref_returnVar.tree = commonTree;
            label_ref_returnVar.stop = this.input.LT(-1);
            label_ref_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(label_ref_returnVar.tree, label_ref_returnVar.start, label_ref_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            label_ref_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, label_ref_returnVar.start, this.input.LT(-1), e);
        }
        return label_ref_returnVar;
    }

    public static class register_list_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final register_list_return register_list() throws RecognitionException {
        char c;
        register_list_return register_list_returnVar = new register_list_return();
        register_list_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 204) {
                c = 1;
            } else {
                if (iLA != 18) {
                    throw new NoViableAltException("", 38, 0, this.input);
                }
                c = 2;
            }
            CommonTree commonTree = null;
            if (c == 1) {
                rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_register_list3402));
                while (this.input.LA(1) == 21) {
                    rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_register_list3405));
                    rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_register_list3407));
                }
                register_list_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", register_list_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor = this.adaptor;
                CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(138, register_list_returnVar.start, "I_REGISTER_LIST"), commonTree2);
                while (rewriteRuleTokenStream2.hasNext()) {
                    this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
                }
                rewriteRuleTokenStream2.reset();
                this.adaptor.addChild(commonTree, commonTree3);
                register_list_returnVar.tree = commonTree;
            } else if (c == 2) {
                register_list_returnVar.tree = null;
                new RewriteRuleSubtreeStream(this.adaptor, "rule retval", register_list_returnVar.getTree());
                commonTree = (CommonTree) this.adaptor.nil();
                CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
                TreeAdaptor treeAdaptor2 = this.adaptor;
                this.adaptor.addChild(commonTree, (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(138, register_list_returnVar.start, "I_REGISTER_LIST"), commonTree4));
                register_list_returnVar.tree = commonTree;
            }
            register_list_returnVar.stop = this.input.LT(-1);
            register_list_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(register_list_returnVar.tree, register_list_returnVar.start, register_list_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            register_list_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, register_list_returnVar.start, this.input.LT(-1), e);
        }
        return register_list_returnVar;
    }

    public static class register_range_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final register_range_return register_range() throws RecognitionException {
        Token token;
        Token token2;
        register_range_return register_range_returnVar = new register_range_return();
        register_range_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token DOTDOT");
        try {
            if (this.input.LA(1) == 204) {
                token2 = (Token) match(this.input, 204, FOLLOW_REGISTER_in_register_range3442);
                rewriteRuleTokenStream.add(token2);
                if (this.input.LA(1) == 22) {
                    rewriteRuleTokenStream2.add((Token) match(this.input, 22, FOLLOW_DOTDOT_in_register_range3445));
                    token = (Token) match(this.input, 204, FOLLOW_REGISTER_in_register_range3449);
                    rewriteRuleTokenStream.add(token);
                } else {
                    token = null;
                }
            } else {
                token = null;
                token2 = null;
            }
            register_range_returnVar.tree = null;
            RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token startreg", token2);
            RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token endreg", token);
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", register_range_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(139, register_range_returnVar.start, "I_REGISTER_RANGE"), commonTree2);
            if (rewriteRuleTokenStream3.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            }
            rewriteRuleTokenStream3.reset();
            if (rewriteRuleTokenStream4.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
            }
            rewriteRuleTokenStream4.reset();
            this.adaptor.addChild(commonTree, commonTree3);
            register_range_returnVar.tree = commonTree;
            register_range_returnVar.stop = this.input.LT(-1);
            register_range_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(register_range_returnVar.tree, register_range_returnVar.start, register_range_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            register_range_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, register_range_returnVar.start, this.input.LT(-1), e);
        }
        return register_range_returnVar;
    }

    public static class verification_error_reference_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final verification_error_reference_return verification_error_reference() throws RecognitionException {
        CommonTree commonTree;
        CommonTree commonTree2;
        verification_error_reference_return verification_error_reference_returnVar = new verification_error_reference_return();
        verification_error_reference_returnVar.start = this.input.LT(1);
        try {
            int iPredict = this.dfa41.predict(this.input);
            if (iPredict != 1) {
                if (iPredict == 2) {
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_field_reference_in_verification_error_reference3482);
                    field_reference_return field_reference_returnVarField_reference = field_reference();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree2, field_reference_returnVarField_reference.getTree());
                } else if (iPredict != 3) {
                    commonTree = null;
                } else {
                    commonTree2 = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_method_reference_in_verification_error_reference3486);
                    method_reference_return method_reference_returnVarMethod_reference = method_reference();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree2, method_reference_returnVarMethod_reference.getTree());
                }
                commonTree = commonTree2;
            } else {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 16, FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3478)));
            }
            verification_error_reference_returnVar.stop = this.input.LT(-1);
            verification_error_reference_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(verification_error_reference_returnVar.tree, verification_error_reference_returnVar.start, verification_error_reference_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            verification_error_reference_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, verification_error_reference_returnVar.start, this.input.LT(-1), e);
        }
        return verification_error_reference_returnVar;
    }

    public static class catch_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final catch_directive_return catch_directive() throws RecognitionException {
        catch_directive_return catch_directive_returnVar = new catch_directive_return();
        catch_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token DOTDOT");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token CATCH_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 14, FOLLOW_CATCH_DIRECTIVE_in_catch_directive3496));
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_catch_directive3498);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
            rewriteRuleTokenStream.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_catch_directive3500));
            pushFollow(FOLLOW_label_ref_in_catch_directive3504);
            label_ref_return label_ref_returnVarLabel_ref = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree());
            rewriteRuleTokenStream3.add((Token) match(this.input, 22, FOLLOW_DOTDOT_in_catch_directive3506));
            pushFollow(FOLLOW_label_ref_in_catch_directive3510);
            label_ref_return label_ref_returnVarLabel_ref2 = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref2.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_catch_directive3512));
            pushFollow(FOLLOW_label_ref_in_catch_directive3516);
            label_ref_return label_ref_returnVarLabel_ref3 = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref3.getTree());
            catch_directive_returnVar.tree = null;
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule using", label_ref_returnVarLabel_ref3 != null ? label_ref_returnVarLabel_ref3.getTree() : null);
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = new RewriteRuleSubtreeStream(this.adaptor, "rule from", label_ref_returnVarLabel_ref != null ? label_ref_returnVarLabel_ref.getTree() : null);
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream5 = new RewriteRuleSubtreeStream(this.adaptor, "rule to", label_ref_returnVarLabel_ref2 != null ? label_ref_returnVarLabel_ref2.getTree() : null);
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", catch_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(106, catch_directive_returnVar.start, "I_CATCH"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream4.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream5.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            catch_directive_returnVar.tree = commonTree;
            catch_directive_returnVar.stop = this.input.LT(-1);
            catch_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(catch_directive_returnVar.tree, catch_directive_returnVar.start, catch_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            catch_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, catch_directive_returnVar.start, this.input.LT(-1), e);
        }
        return catch_directive_returnVar;
    }

    public static class catchall_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final catchall_directive_return catchall_directive() throws RecognitionException {
        catchall_directive_return catchall_directive_returnVar = new catchall_directive_return();
        catchall_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token DOTDOT");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token CATCHALL_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 13, FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3548));
            rewriteRuleTokenStream.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_catchall_directive3550));
            pushFollow(FOLLOW_label_ref_in_catchall_directive3554);
            label_ref_return label_ref_returnVarLabel_ref = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree());
            rewriteRuleTokenStream3.add((Token) match(this.input, 22, FOLLOW_DOTDOT_in_catchall_directive3556));
            pushFollow(FOLLOW_label_ref_in_catchall_directive3560);
            label_ref_return label_ref_returnVarLabel_ref2 = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref2.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_catchall_directive3562));
            pushFollow(FOLLOW_label_ref_in_catchall_directive3566);
            label_ref_return label_ref_returnVarLabel_ref3 = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref3.getTree());
            catchall_directive_returnVar.tree = null;
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule using", label_ref_returnVarLabel_ref3 != null ? label_ref_returnVarLabel_ref3.getTree() : null);
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule from", label_ref_returnVarLabel_ref != null ? label_ref_returnVarLabel_ref.getTree() : null);
            RewriteRuleSubtreeStream rewriteRuleSubtreeStream4 = new RewriteRuleSubtreeStream(this.adaptor, "rule to", label_ref_returnVarLabel_ref2 != null ? label_ref_returnVarLabel_ref2.getTree() : null);
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", catchall_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(107, catchall_directive_returnVar.start, "I_CATCHALL"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream4.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            catchall_directive_returnVar.tree = commonTree;
            catchall_directive_returnVar.stop = this.input.LT(-1);
            catchall_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(catchall_directive_returnVar.tree, catchall_directive_returnVar.start, catchall_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            catchall_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, catchall_directive_returnVar.start, this.input.LT(-1), e);
        }
        return catchall_directive_returnVar;
    }

    public static class parameter_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final parameter_directive_return parameter_directive() throws RecognitionException {
        char c;
        parameter_directive_return parameter_directive_returnVar = new parameter_directive_return();
        parameter_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token PARAMETER_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream5 = new RewriteRuleTokenStream(this.adaptor, "token END_PARAMETER_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation");
        ArrayList arrayList = new ArrayList();
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 199, FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3605));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_parameter_directive3607));
            if (this.input.LA(1) == 21) {
                rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_parameter_directive3610));
                rewriteRuleTokenStream4.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_parameter_directive3612));
            }
            while (true) {
                CommonTree commonTree = null;
                if (this.dfa43.predict(this.input) == 1) {
                    if (this.input.LA(1) != 5) {
                        throw new FailedPredicateException(this.input, "parameter_directive", "input.LA(1) == ANNOTATION_DIRECTIVE");
                    }
                    pushFollow(FOLLOW_annotation_in_parameter_directive3623);
                    annotation_return annotation_returnVarAnnotation = annotation();
                    this.state._fsp--;
                    rewriteRuleSubtreeStream.add(annotation_returnVarAnnotation.getTree());
                    if (annotation_returnVarAnnotation != null) {
                        commonTree = (CommonTree) annotation_returnVarAnnotation.getTree();
                    }
                    arrayList.add(commonTree);
                } else {
                    int iLA = this.input.LA(1);
                    if (iLA == 31) {
                        c = 1;
                    } else {
                        if (iLA != 5 && iLA != 7 && ((iLA < 13 || iLA > 14) && iLA != 20 && ((iLA < 28 || iLA > 29) && iLA != 35 && ((iLA < 44 || iLA > 94) && ((iLA < 186 || iLA > 188) && ((iLA < 198 || iLA > 199) && iLA != 203 && ((iLA < 205 || iLA > 206) && (iLA < 209 || iLA > 210)))))))) {
                            throw new NoViableAltException("", 44, 0, this.input);
                        }
                        c = 2;
                    }
                    if (c == 1) {
                        rewriteRuleTokenStream5.add((Token) match(this.input, 31, FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3636));
                        parameter_directive_returnVar.tree = null;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", parameter_directive_returnVar.getTree());
                        commonTree = (CommonTree) this.adaptor.nil();
                        CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
                        TreeAdaptor treeAdaptor = this.adaptor;
                        CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(133, parameter_directive_returnVar.start, "I_PARAMETER"), commonTree2);
                        this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
                        if (rewriteRuleTokenStream4.hasNext()) {
                            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
                        }
                        rewriteRuleTokenStream4.reset();
                        CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
                        TreeAdaptor treeAdaptor2 = this.adaptor;
                        CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(100, "I_ANNOTATIONS"), commonTree4);
                        while (rewriteRuleSubtreeStream.hasNext()) {
                            this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree());
                        }
                        rewriteRuleSubtreeStream.reset();
                        this.adaptor.addChild(commonTree3, commonTree5);
                        this.adaptor.addChild(commonTree, commonTree3);
                        parameter_directive_returnVar.tree = commonTree;
                    } else if (c == 2) {
                        this.statements_and_directives_stack.peek().methodAnnotations.addAll(arrayList);
                        parameter_directive_returnVar.tree = null;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", parameter_directive_returnVar.getTree());
                        commonTree = (CommonTree) this.adaptor.nil();
                        CommonTree commonTree6 = (CommonTree) this.adaptor.nil();
                        TreeAdaptor treeAdaptor3 = this.adaptor;
                        CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(133, parameter_directive_returnVar.start, "I_PARAMETER"), commonTree6);
                        this.adaptor.addChild(commonTree7, rewriteRuleTokenStream2.nextNode());
                        if (rewriteRuleTokenStream4.hasNext()) {
                            this.adaptor.addChild(commonTree7, rewriteRuleTokenStream4.nextNode());
                        }
                        rewriteRuleTokenStream4.reset();
                        CommonTree commonTree8 = (CommonTree) this.adaptor.nil();
                        TreeAdaptor treeAdaptor4 = this.adaptor;
                        this.adaptor.addChild(commonTree7, (CommonTree) treeAdaptor4.becomeRoot((CommonTree) treeAdaptor4.create(100, "I_ANNOTATIONS"), commonTree8));
                        this.adaptor.addChild(commonTree, commonTree7);
                        parameter_directive_returnVar.tree = commonTree;
                    }
                    parameter_directive_returnVar.stop = this.input.LT(-1);
                    parameter_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
                    this.adaptor.setTokenBoundaries(parameter_directive_returnVar.tree, parameter_directive_returnVar.start, parameter_directive_returnVar.stop);
                    return parameter_directive_returnVar;
                }
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            parameter_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, parameter_directive_returnVar.start, this.input.LT(-1), e);
        }
    }

    public static class debug_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final debug_directive_return debug_directive() throws RecognitionException {
        char c;
        CommonTree commonTree;
        debug_directive_return debug_directive_returnVar = new debug_directive_return();
        debug_directive_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            if (iLA == 28) {
                c = 3;
            } else if (iLA == 35) {
                c = 6;
            } else if (iLA == 186) {
                c = 1;
            } else if (iLA == 188) {
                c = 2;
            } else if (iLA == 203) {
                c = 5;
            } else if (iLA == 206) {
                c = 4;
            } else {
                if (iLA != 209) {
                    throw new NoViableAltException("", 45, 0, this.input);
                }
                c = 7;
            }
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_line_directive_in_debug_directive3709);
                    line_directive_return line_directive_returnVarLine_directive = line_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, line_directive_returnVarLine_directive.getTree());
                    break;
                case 2:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_local_directive_in_debug_directive3715);
                    local_directive_return local_directive_returnVarLocal_directive = local_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, local_directive_returnVarLocal_directive.getTree());
                    break;
                case 3:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_end_local_directive_in_debug_directive3721);
                    end_local_directive_return end_local_directive_returnVarEnd_local_directive = end_local_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, end_local_directive_returnVarEnd_local_directive.getTree());
                    break;
                case 4:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_restart_local_directive_in_debug_directive3727);
                    restart_local_directive_return restart_local_directive_returnVarRestart_local_directive = restart_local_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, restart_local_directive_returnVarRestart_local_directive.getTree());
                    break;
                case 5:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_prologue_directive_in_debug_directive3733);
                    prologue_directive_return prologue_directive_returnVarPrologue_directive = prologue_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, prologue_directive_returnVarPrologue_directive.getTree());
                    break;
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_epilogue_directive_in_debug_directive3739);
                    epilogue_directive_return epilogue_directive_returnVarEpilogue_directive = epilogue_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, epilogue_directive_returnVarEpilogue_directive.getTree());
                    break;
                case 7:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_source_directive_in_debug_directive3745);
                    source_directive_return source_directive_returnVarSource_directive = source_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, source_directive_returnVarSource_directive.getTree());
                    break;
                default:
                    commonTree = null;
                    break;
            }
            debug_directive_returnVar.stop = this.input.LT(-1);
            debug_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(debug_directive_returnVar.tree, debug_directive_returnVar.start, debug_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            debug_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, debug_directive_returnVar.start, this.input.LT(-1), e);
        }
        return debug_directive_returnVar;
    }

    public static class line_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final line_directive_return line_directive() throws RecognitionException {
        line_directive_return line_directive_returnVar = new line_directive_return();
        line_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token LINE_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 186, FOLLOW_LINE_DIRECTIVE_in_line_directive3755));
            pushFollow(FOLLOW_integral_literal_in_line_directive3757);
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree());
            line_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", line_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(123, line_directive_returnVar.start, "I_LINE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            line_directive_returnVar.tree = commonTree;
            line_directive_returnVar.stop = this.input.LT(-1);
            line_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(line_directive_returnVar.tree, line_directive_returnVar.start, line_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            line_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, line_directive_returnVar.start, this.input.LT(-1), e);
        }
        return line_directive_returnVar;
    }

    public static class local_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:20:0x00de  */
    /* JADX WARN: Code duplicated, block: B:31:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:33:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:34:0x00fc A[Catch: all -> 0x0214, RecognitionException -> 0x0216, TryCatch #2 {RecognitionException -> 0x0216, blocks: (B:3:0x0058, B:5:0x0080, B:14:0x00a7, B:17:0x00c3, B:36:0x0121, B:38:0x012b, B:44:0x0158, B:46:0x01ac, B:47:0x01b5, B:49:0x01be, B:50:0x01c7, B:52:0x01d0, B:53:0x01d9, B:55:0x01e2, B:56:0x01eb, B:34:0x00fc, B:35:0x0114, B:27:0x00eb, B:28:0x00f4, B:15:0x00b5, B:41:0x014b, B:42:0x0154), top: B:63:0x0058 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0114 A[Catch: all -> 0x0214, RecognitionException -> 0x0216, TryCatch #2 {RecognitionException -> 0x0216, blocks: (B:3:0x0058, B:5:0x0080, B:14:0x00a7, B:17:0x00c3, B:36:0x0121, B:38:0x012b, B:44:0x0158, B:46:0x01ac, B:47:0x01b5, B:49:0x01be, B:50:0x01c7, B:52:0x01d0, B:53:0x01d9, B:55:0x01e2, B:56:0x01eb, B:34:0x00fc, B:35:0x0114, B:27:0x00eb, B:28:0x00f4, B:15:0x00b5, B:41:0x014b, B:42:0x0154), top: B:63:0x0058 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x012b A[Catch: all -> 0x0214, RecognitionException -> 0x0216, TryCatch #2 {RecognitionException -> 0x0216, blocks: (B:3:0x0058, B:5:0x0080, B:14:0x00a7, B:17:0x00c3, B:36:0x0121, B:38:0x012b, B:44:0x0158, B:46:0x01ac, B:47:0x01b5, B:49:0x01be, B:50:0x01c7, B:52:0x01d0, B:53:0x01d9, B:55:0x01e2, B:56:0x01eb, B:34:0x00fc, B:35:0x0114, B:27:0x00eb, B:28:0x00f4, B:15:0x00b5, B:41:0x014b, B:42:0x0154), top: B:63:0x0058 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x0148  */
    public final local_directive_return local_directive() throws RecognitionException {
        Token token;
        CommonTree commonTree;
        Token token2;
        char c;
        int iLA;
        char c2;
        local_directive_return local_directive_returnVar = new local_directive_return();
        local_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token LOCAL_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token VOID_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream5 = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream6 = new RewriteRuleTokenStream(this.adaptor, "token COLON");
        RewriteRuleTokenStream rewriteRuleTokenStream7 = new RewriteRuleTokenStream(this.adaptor, "token NULL_LITERAL");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 188, FOLLOW_LOCAL_DIRECTIVE_in_local_directive3780));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_local_directive3782));
            if (this.input.LA(1) == 21) {
                rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_local_directive3785));
                int iLA2 = this.input.LA(1);
                if (iLA2 == 195) {
                    c = 1;
                } else {
                    if (iLA2 != 211) {
                        throw new NoViableAltException("", 46, 0, this.input);
                    }
                    c = 2;
                }
                if (c == 1) {
                    rewriteRuleTokenStream7.add((Token) match(this.input, 195, FOLLOW_NULL_LITERAL_in_local_directive3788));
                } else {
                    if (c == 2) {
                        token2 = (Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_local_directive3794);
                        rewriteRuleTokenStream5.add(token2);
                    }
                    rewriteRuleTokenStream6.add((Token) match(this.input, 20, FOLLOW_COLON_in_local_directive3797));
                    iLA = this.input.LA(1);
                    if (iLA == 215) {
                        c2 = 1;
                    } else {
                        if (iLA != 8 && iLA != 16 && iLA != 202) {
                            throw new NoViableAltException("", 47, 0, this.input);
                        }
                        c2 = 2;
                    }
                    if (c2 != 1) {
                        rewriteRuleTokenStream4.add((Token) match(this.input, 215, FOLLOW_VOID_TYPE_in_local_directive3800));
                    } else if (c2 != 2) {
                        pushFollow(FOLLOW_nonvoid_type_descriptor_in_local_directive3804);
                        nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
                        this.state._fsp--;
                        rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
                    }
                    if (this.input.LA(1) == 21) {
                        rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_local_directive3838));
                        token = (Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_local_directive3842);
                        rewriteRuleTokenStream5.add(token);
                    } else {
                        token = null;
                    }
                    commonTree = null;
                }
                token2 = null;
                rewriteRuleTokenStream6.add((Token) match(this.input, 20, FOLLOW_COLON_in_local_directive3797));
                iLA = this.input.LA(1);
                if (iLA == 215) {
                    c2 = 1;
                } else {
                    if (iLA != 8) {
                        throw new NoViableAltException("", 47, 0, this.input);
                    }
                    c2 = 2;
                }
                if (c2 != 1) {
                    rewriteRuleTokenStream4.add((Token) match(this.input, 215, FOLLOW_VOID_TYPE_in_local_directive3800));
                } else if (c2 != 2) {
                    pushFollow(FOLLOW_nonvoid_type_descriptor_in_local_directive3804);
                    nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor2 = nonvoid_type_descriptor();
                    this.state._fsp--;
                    rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor2.getTree());
                }
                if (this.input.LA(1) == 21) {
                    rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_local_directive3838));
                    token = (Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_local_directive3842);
                    rewriteRuleTokenStream5.add(token);
                } else {
                    token = null;
                }
                commonTree = null;
            } else {
                token = null;
                commonTree = null;
                token2 = null;
            }
            local_directive_returnVar.tree = commonTree;
            RewriteRuleTokenStream rewriteRuleTokenStream8 = new RewriteRuleTokenStream(this.adaptor, "token signature", token);
            RewriteRuleTokenStream rewriteRuleTokenStream9 = new RewriteRuleTokenStream(this.adaptor, "token name", token2);
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", local_directive_returnVar.getTree());
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            CommonTree commonTree3 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree4 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(124, local_directive_returnVar.start, "I_LOCAL"), commonTree3);
            this.adaptor.addChild(commonTree4, rewriteRuleTokenStream2.nextNode());
            if (rewriteRuleTokenStream7.hasNext()) {
                this.adaptor.addChild(commonTree4, rewriteRuleTokenStream7.nextNode());
            }
            rewriteRuleTokenStream7.reset();
            if (rewriteRuleTokenStream9.hasNext()) {
                this.adaptor.addChild(commonTree4, rewriteRuleTokenStream9.nextNode());
            }
            rewriteRuleTokenStream9.reset();
            if (rewriteRuleSubtreeStream.hasNext()) {
                this.adaptor.addChild(commonTree4, rewriteRuleSubtreeStream.nextTree());
            }
            rewriteRuleSubtreeStream.reset();
            if (rewriteRuleTokenStream8.hasNext()) {
                this.adaptor.addChild(commonTree4, rewriteRuleTokenStream8.nextNode());
            }
            rewriteRuleTokenStream8.reset();
            this.adaptor.addChild(commonTree2, commonTree4);
            local_directive_returnVar.tree = commonTree2;
            local_directive_returnVar.stop = this.input.LT(-1);
            local_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree2);
            this.adaptor.setTokenBoundaries(local_directive_returnVar.tree, local_directive_returnVar.start, local_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            local_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, local_directive_returnVar.start, this.input.LT(-1), e);
        }
        return local_directive_returnVar;
    }

    public static class end_local_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final end_local_directive_return end_local_directive() throws RecognitionException {
        end_local_directive_return end_local_directive_returnVar = new end_local_directive_return();
        end_local_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        try {
            new RewriteRuleTokenStream(this.adaptor, "token END_LOCAL_DIRECTIVE").add((Token) match(this.input, 28, FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3884));
            rewriteRuleTokenStream.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_end_local_directive3886));
            end_local_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", end_local_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(115, end_local_directive_returnVar.start, "I_END_LOCAL"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            end_local_directive_returnVar.tree = commonTree;
            end_local_directive_returnVar.stop = this.input.LT(-1);
            end_local_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(end_local_directive_returnVar.tree, end_local_directive_returnVar.start, end_local_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            end_local_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, end_local_directive_returnVar.start, this.input.LT(-1), e);
        }
        return end_local_directive_returnVar;
    }

    public static class restart_local_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final restart_local_directive_return restart_local_directive() throws RecognitionException {
        restart_local_directive_return restart_local_directive_returnVar = new restart_local_directive_return();
        restart_local_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        try {
            new RewriteRuleTokenStream(this.adaptor, "token RESTART_LOCAL_DIRECTIVE").add((Token) match(this.input, 206, FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3909));
            rewriteRuleTokenStream.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_restart_local_directive3911));
            restart_local_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", restart_local_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(140, restart_local_directive_returnVar.start, "I_RESTART_LOCAL"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            restart_local_directive_returnVar.tree = commonTree;
            restart_local_directive_returnVar.stop = this.input.LT(-1);
            restart_local_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(restart_local_directive_returnVar.tree, restart_local_directive_returnVar.start, restart_local_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            restart_local_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, restart_local_directive_returnVar.start, this.input.LT(-1), e);
        }
        return restart_local_directive_returnVar;
    }

    public static class prologue_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final prologue_directive_return prologue_directive() throws RecognitionException {
        prologue_directive_return prologue_directive_returnVar = new prologue_directive_return();
        prologue_directive_returnVar.start = this.input.LT(1);
        try {
            new RewriteRuleTokenStream(this.adaptor, "token PROLOGUE_DIRECTIVE").add((Token) match(this.input, 203, FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3934));
            prologue_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", prologue_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            this.adaptor.addChild(commonTree, (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(136, prologue_directive_returnVar.start, "I_PROLOGUE"), commonTree2));
            prologue_directive_returnVar.tree = commonTree;
            prologue_directive_returnVar.stop = this.input.LT(-1);
            prologue_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(prologue_directive_returnVar.tree, prologue_directive_returnVar.start, prologue_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            prologue_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, prologue_directive_returnVar.start, this.input.LT(-1), e);
        }
        return prologue_directive_returnVar;
    }

    public static class epilogue_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final epilogue_directive_return epilogue_directive() throws RecognitionException {
        epilogue_directive_return epilogue_directive_returnVar = new epilogue_directive_return();
        epilogue_directive_returnVar.start = this.input.LT(1);
        try {
            new RewriteRuleTokenStream(this.adaptor, "token EPILOGUE_DIRECTIVE").add((Token) match(this.input, 35, FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3955));
            epilogue_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", epilogue_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            this.adaptor.addChild(commonTree, (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(116, epilogue_directive_returnVar.start, "I_EPILOGUE"), commonTree2));
            epilogue_directive_returnVar.tree = commonTree;
            epilogue_directive_returnVar.stop = this.input.LT(-1);
            epilogue_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(epilogue_directive_returnVar.tree, epilogue_directive_returnVar.start, epilogue_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            epilogue_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, epilogue_directive_returnVar.start, this.input.LT(-1), e);
        }
        return epilogue_directive_returnVar;
    }

    public static class source_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final source_directive_return source_directive() throws RecognitionException {
        source_directive_return source_directive_returnVar = new source_directive_return();
        source_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token SOURCE_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 209, FOLLOW_SOURCE_DIRECTIVE_in_source_directive3976));
            if (this.input.LA(1) == 211) {
                rewriteRuleTokenStream2.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_source_directive3978));
            }
            source_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", source_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(141, source_directive_returnVar.start, "I_SOURCE"), commonTree2);
            if (rewriteRuleTokenStream2.hasNext()) {
                this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            }
            rewriteRuleTokenStream2.reset();
            this.adaptor.addChild(commonTree, commonTree3);
            source_directive_returnVar.tree = commonTree;
            source_directive_returnVar.stop = this.input.LT(-1);
            source_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(source_directive_returnVar.tree, source_directive_returnVar.start, source_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            source_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, source_directive_returnVar.start, this.input.LT(-1), e);
        }
        return source_directive_returnVar;
    }

    public static class instruction_format12x_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final instruction_format12x_return instruction_format12x() throws RecognitionException {
        char c;
        CommonTree commonTree;
        instruction_format12x_return instruction_format12x_returnVar = new instruction_format12x_return();
        instruction_format12x_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT12x_OR_ID");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 49) {
                c = 1;
            } else {
                if (iLA != 50) {
                    throw new NoViableAltException("", 51, 0, this.input);
                }
                c = 2;
            }
            if (c != 1) {
                commonTree = null;
                if (c == 2) {
                    Token token = (Token) match(this.input, 50, FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x4009);
                    rewriteRuleTokenStream.add(token);
                    instruction_format12x_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", instruction_format12x_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor = this.adaptor;
                    treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(49, token));
                    instruction_format12x_returnVar.tree = commonTree;
                }
            } else {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 49, FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x4003)));
            }
            instruction_format12x_returnVar.stop = this.input.LT(-1);
            instruction_format12x_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(instruction_format12x_returnVar.tree, instruction_format12x_returnVar.start, instruction_format12x_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            instruction_format12x_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, instruction_format12x_returnVar.start, this.input.LT(-1), e);
        }
        return instruction_format12x_returnVar;
    }

    public static class instruction_format22s_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final instruction_format22s_return instruction_format22s() throws RecognitionException {
        char c;
        CommonTree commonTree;
        instruction_format22s_return instruction_format22s_returnVar = new instruction_format22s_return();
        instruction_format22s_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22s_OR_ID");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 68) {
                c = 1;
            } else {
                if (iLA != 69) {
                    throw new NoViableAltException("", 52, 0, this.input);
                }
                c = 2;
            }
            if (c != 1) {
                commonTree = null;
                if (c == 2) {
                    Token token = (Token) match(this.input, 69, FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s4030);
                    rewriteRuleTokenStream.add(token);
                    instruction_format22s_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", instruction_format22s_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor = this.adaptor;
                    treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(68, token));
                    instruction_format22s_returnVar.tree = commonTree;
                }
            } else {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 68, FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s4024)));
            }
            instruction_format22s_returnVar.stop = this.input.LT(-1);
            instruction_format22s_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(instruction_format22s_returnVar.tree, instruction_format22s_returnVar.start, instruction_format22s_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            instruction_format22s_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, instruction_format22s_returnVar.start, this.input.LT(-1), e);
        }
        return instruction_format22s_returnVar;
    }

    public static class instruction_format31i_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final instruction_format31i_return instruction_format31i() throws RecognitionException {
        char c;
        CommonTree commonTree;
        instruction_format31i_return instruction_format31i_returnVar = new instruction_format31i_return();
        instruction_format31i_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31i_OR_ID");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 75) {
                c = 1;
            } else {
                if (iLA != 76) {
                    throw new NoViableAltException("", 53, 0, this.input);
                }
                c = 2;
            }
            if (c != 1) {
                commonTree = null;
                if (c == 2) {
                    Token token = (Token) match(this.input, 76, FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i4051);
                    rewriteRuleTokenStream.add(token);
                    instruction_format31i_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", instruction_format31i_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor = this.adaptor;
                    treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(75, token));
                    instruction_format31i_returnVar.tree = commonTree;
                }
            } else {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 75, FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i4045)));
            }
            instruction_format31i_returnVar.stop = this.input.LT(-1);
            instruction_format31i_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(instruction_format31i_returnVar.tree, instruction_format31i_returnVar.start, instruction_format31i_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            instruction_format31i_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, instruction_format31i_returnVar.start, this.input.LT(-1), e);
        }
        return instruction_format31i_returnVar;
    }

    public static class instruction_format35c_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final instruction_format35c_method_return instruction_format35c_method() throws RecognitionException {
        char c;
        CommonTree commonTree;
        instruction_format35c_method_return instruction_format35c_method_returnVar = new instruction_format35c_method_return();
        instruction_format35c_method_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE");
        try {
            int iLA = this.input.LA(1);
            if (iLA == 80) {
                c = 1;
            } else {
                if (iLA != 82) {
                    throw new NoViableAltException("", 54, 0, this.input);
                }
                c = 2;
            }
            if (c != 1) {
                commonTree = null;
                if (c == 2) {
                    Token token = (Token) match(this.input, 82, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_OR_METHOD_HANDLE_TYPE_in_instruction_format35c_method4074);
                    rewriteRuleTokenStream.add(token);
                    instruction_format35c_method_returnVar.tree = null;
                    new RewriteRuleSubtreeStream(this.adaptor, "rule retval", instruction_format35c_method_returnVar.getTree());
                    commonTree = (CommonTree) this.adaptor.nil();
                    TreeAdaptor treeAdaptor = this.adaptor;
                    treeAdaptor.addChild(commonTree, (CommonTree) treeAdaptor.create(80, token));
                    instruction_format35c_method_returnVar.tree = commonTree;
                }
            } else {
                commonTree = (CommonTree) this.adaptor.nil();
                this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 80, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_instruction_format35c_method4068)));
            }
            instruction_format35c_method_returnVar.stop = this.input.LT(-1);
            instruction_format35c_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(instruction_format35c_method_returnVar.tree, instruction_format35c_method_returnVar.start, instruction_format35c_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            instruction_format35c_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, instruction_format35c_method_returnVar.start, this.input.LT(-1), e);
        }
        return instruction_format35c_method_returnVar;
    }

    public static class instruction_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final instruction_return instruction() throws RecognitionException {
        CommonTree commonTree;
        instruction_return instruction_returnVar = new instruction_return();
        instruction_returnVar.start = this.input.LT(1);
        try {
            int iLA = this.input.LA(1);
            char c = 7;
            if (iLA == 7) {
                c = ExternalAnnotationProvider.NULLABLE;
            } else if (iLA == 198) {
                c = ExternalAnnotationProvider.NONNULL;
            } else if (iLA != 210) {
                switch (iLA) {
                    case 44:
                        c = 1;
                        break;
                    case 45:
                        c = 2;
                        break;
                    case 46:
                        c = 3;
                        break;
                    case 47:
                        c = 4;
                        break;
                    case 48:
                        c = 5;
                        break;
                    case 49:
                    case 50:
                        c = 6;
                        break;
                    case 51:
                        break;
                    case 52:
                        c = '\b';
                        break;
                    case 53:
                        c = '\t';
                        break;
                    case 54:
                        c = '\n';
                        break;
                    case 55:
                        c = 11;
                        break;
                    case 56:
                        c = '\f';
                        break;
                    case 57:
                        c = '\r';
                        break;
                    case 58:
                        c = 14;
                        break;
                    case 59:
                        c = 15;
                        break;
                    case 60:
                        c = 16;
                        break;
                    case 61:
                        c = 17;
                        break;
                    case 62:
                        c = 18;
                        break;
                    case 63:
                        c = 19;
                        break;
                    case 64:
                        c = 20;
                        break;
                    case 65:
                        c = 21;
                        break;
                    case 66:
                        c = 22;
                        break;
                    case 67:
                        c = 23;
                        break;
                    case 68:
                    case 69:
                        c = 24;
                        break;
                    case 70:
                        c = 25;
                        break;
                    case 71:
                        c = 26;
                        break;
                    case 72:
                        c = 27;
                        break;
                    case 73:
                        c = 28;
                        break;
                    case 74:
                        c = 29;
                        break;
                    case 75:
                    case 76:
                        c = 30;
                        break;
                    case 77:
                        c = 31;
                        break;
                    case 78:
                        c = StringUtil.SPACE;
                        break;
                    case 79:
                        c = '!';
                        break;
                    case 80:
                    case 82:
                        c = '\"';
                        break;
                    case 81:
                        c = '$';
                        break;
                    case 83:
                        c = '#';
                        break;
                    case 84:
                        c = MangleConstant.PLATFORM_FUNCTION_MARKER;
                        break;
                    case 85:
                        c = Typography.amp;
                        break;
                    case 86:
                        c = '\'';
                        break;
                    case 87:
                        c = '(';
                        break;
                    case 88:
                        c = ')';
                        break;
                    case 89:
                        c = '*';
                        break;
                    case 90:
                        c = '+';
                        break;
                    case 91:
                        c = ',';
                        break;
                    case 92:
                        c = '-';
                        break;
                    case 93:
                        c = '.';
                        break;
                    case 94:
                        c = '/';
                        break;
                    default:
                        throw new NoViableAltException("", 55, 0, this.input);
                }
            } else {
                c = '2';
            }
            switch (c) {
                case 1:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format10t_in_instruction4089);
                    insn_format10t_return insn_format10t_returnVarInsn_format10t = insn_format10t();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format10t_returnVarInsn_format10t.getTree());
                    break;
                case 2:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format10x_in_instruction4095);
                    insn_format10x_return insn_format10x_returnVarInsn_format10x = insn_format10x();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format10x_returnVarInsn_format10x.getTree());
                    break;
                case 3:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format10x_odex_in_instruction4101);
                    insn_format10x_odex_return insn_format10x_odex_returnVarInsn_format10x_odex = insn_format10x_odex();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format10x_odex_returnVarInsn_format10x_odex.getTree());
                    break;
                case 4:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format11n_in_instruction4107);
                    insn_format11n_return insn_format11n_returnVarInsn_format11n = insn_format11n();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format11n_returnVarInsn_format11n.getTree());
                    break;
                case 5:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format11x_in_instruction4113);
                    insn_format11x_return insn_format11x_returnVarInsn_format11x = insn_format11x();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format11x_returnVarInsn_format11x.getTree());
                    break;
                case 6:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format12x_in_instruction4119);
                    insn_format12x_return insn_format12x_returnVarInsn_format12x = insn_format12x();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format12x_returnVarInsn_format12x.getTree());
                    break;
                case 7:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format20bc_in_instruction4125);
                    insn_format20bc_return insn_format20bc_returnVarInsn_format20bc = insn_format20bc();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format20bc_returnVarInsn_format20bc.getTree());
                    break;
                case '\b':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format20t_in_instruction4131);
                    insn_format20t_return insn_format20t_returnVarInsn_format20t = insn_format20t();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format20t_returnVarInsn_format20t.getTree());
                    break;
                case '\t':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21c_field_in_instruction4137);
                    insn_format21c_field_return insn_format21c_field_returnVarInsn_format21c_field = insn_format21c_field();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21c_field_returnVarInsn_format21c_field.getTree());
                    break;
                case '\n':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21c_field_odex_in_instruction4143);
                    insn_format21c_field_odex_return insn_format21c_field_odex_returnVarInsn_format21c_field_odex = insn_format21c_field_odex();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21c_field_odex_returnVarInsn_format21c_field_odex.getTree());
                    break;
                case 11:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21c_method_handle_in_instruction4149);
                    insn_format21c_method_handle_return insn_format21c_method_handle_returnVarInsn_format21c_method_handle = insn_format21c_method_handle();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21c_method_handle_returnVarInsn_format21c_method_handle.getTree());
                    break;
                case '\f':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21c_method_type_in_instruction4155);
                    insn_format21c_method_type_return insn_format21c_method_type_returnVarInsn_format21c_method_type = insn_format21c_method_type();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21c_method_type_returnVarInsn_format21c_method_type.getTree());
                    break;
                case '\r':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21c_string_in_instruction4161);
                    insn_format21c_string_return insn_format21c_string_returnVarInsn_format21c_string = insn_format21c_string();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21c_string_returnVarInsn_format21c_string.getTree());
                    break;
                case 14:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21c_type_in_instruction4167);
                    insn_format21c_type_return insn_format21c_type_returnVarInsn_format21c_type = insn_format21c_type();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21c_type_returnVarInsn_format21c_type.getTree());
                    break;
                case 15:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21ih_in_instruction4173);
                    insn_format21ih_return insn_format21ih_returnVarInsn_format21ih = insn_format21ih();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21ih_returnVarInsn_format21ih.getTree());
                    break;
                case 16:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21lh_in_instruction4179);
                    insn_format21lh_return insn_format21lh_returnVarInsn_format21lh = insn_format21lh();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21lh_returnVarInsn_format21lh.getTree());
                    break;
                case 17:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21s_in_instruction4185);
                    insn_format21s_return insn_format21s_returnVarInsn_format21s = insn_format21s();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21s_returnVarInsn_format21s.getTree());
                    break;
                case 18:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format21t_in_instruction4191);
                    insn_format21t_return insn_format21t_returnVarInsn_format21t = insn_format21t();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format21t_returnVarInsn_format21t.getTree());
                    break;
                case 19:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format22b_in_instruction4197);
                    insn_format22b_return insn_format22b_returnVarInsn_format22b = insn_format22b();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format22b_returnVarInsn_format22b.getTree());
                    break;
                case 20:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format22c_field_in_instruction4203);
                    insn_format22c_field_return insn_format22c_field_returnVarInsn_format22c_field = insn_format22c_field();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format22c_field_returnVarInsn_format22c_field.getTree());
                    break;
                case 21:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format22c_field_odex_in_instruction4209);
                    insn_format22c_field_odex_return insn_format22c_field_odex_returnVarInsn_format22c_field_odex = insn_format22c_field_odex();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format22c_field_odex_returnVarInsn_format22c_field_odex.getTree());
                    break;
                case 22:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format22c_type_in_instruction4215);
                    insn_format22c_type_return insn_format22c_type_returnVarInsn_format22c_type = insn_format22c_type();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format22c_type_returnVarInsn_format22c_type.getTree());
                    break;
                case 23:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format22cs_field_in_instruction4221);
                    insn_format22cs_field_return insn_format22cs_field_returnVarInsn_format22cs_field = insn_format22cs_field();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format22cs_field_returnVarInsn_format22cs_field.getTree());
                    break;
                case 24:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format22s_in_instruction4227);
                    insn_format22s_return insn_format22s_returnVarInsn_format22s = insn_format22s();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format22s_returnVarInsn_format22s.getTree());
                    break;
                case 25:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format22t_in_instruction4233);
                    insn_format22t_return insn_format22t_returnVarInsn_format22t = insn_format22t();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format22t_returnVarInsn_format22t.getTree());
                    break;
                case 26:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format22x_in_instruction4239);
                    insn_format22x_return insn_format22x_returnVarInsn_format22x = insn_format22x();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format22x_returnVarInsn_format22x.getTree());
                    break;
                case 27:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format23x_in_instruction4245);
                    insn_format23x_return insn_format23x_returnVarInsn_format23x = insn_format23x();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format23x_returnVarInsn_format23x.getTree());
                    break;
                case 28:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format30t_in_instruction4251);
                    insn_format30t_return insn_format30t_returnVarInsn_format30t = insn_format30t();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format30t_returnVarInsn_format30t.getTree());
                    break;
                case 29:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format31c_in_instruction4257);
                    insn_format31c_return insn_format31c_returnVarInsn_format31c = insn_format31c();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format31c_returnVarInsn_format31c.getTree());
                    break;
                case 30:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format31i_in_instruction4263);
                    insn_format31i_return insn_format31i_returnVarInsn_format31i = insn_format31i();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format31i_returnVarInsn_format31i.getTree());
                    break;
                case 31:
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format31t_in_instruction4269);
                    insn_format31t_return insn_format31t_returnVarInsn_format31t = insn_format31t();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format31t_returnVarInsn_format31t.getTree());
                    break;
                case ' ':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format32x_in_instruction4275);
                    insn_format32x_return insn_format32x_returnVarInsn_format32x = insn_format32x();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format32x_returnVarInsn_format32x.getTree());
                    break;
                case '!':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format35c_call_site_in_instruction4281);
                    insn_format35c_call_site_return insn_format35c_call_site_returnVarInsn_format35c_call_site = insn_format35c_call_site();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format35c_call_site_returnVarInsn_format35c_call_site.getTree());
                    break;
                case '\"':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format35c_method_in_instruction4287);
                    insn_format35c_method_return insn_format35c_method_returnVarInsn_format35c_method = insn_format35c_method();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format35c_method_returnVarInsn_format35c_method.getTree());
                    break;
                case '#':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format35c_type_in_instruction4293);
                    insn_format35c_type_return insn_format35c_type_returnVarInsn_format35c_type = insn_format35c_type();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format35c_type_returnVarInsn_format35c_type.getTree());
                    break;
                case '$':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format35c_method_odex_in_instruction4299);
                    insn_format35c_method_odex_return insn_format35c_method_odex_returnVarInsn_format35c_method_odex = insn_format35c_method_odex();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format35c_method_odex_returnVarInsn_format35c_method_odex.getTree());
                    break;
                case '%':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format35mi_method_in_instruction4305);
                    insn_format35mi_method_return insn_format35mi_method_returnVarInsn_format35mi_method = insn_format35mi_method();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format35mi_method_returnVarInsn_format35mi_method.getTree());
                    break;
                case '&':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format35ms_method_in_instruction4311);
                    insn_format35ms_method_return insn_format35ms_method_returnVarInsn_format35ms_method = insn_format35ms_method();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format35ms_method_returnVarInsn_format35ms_method.getTree());
                    break;
                case '\'':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format3rc_call_site_in_instruction4317);
                    insn_format3rc_call_site_return insn_format3rc_call_site_returnVarInsn_format3rc_call_site = insn_format3rc_call_site();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format3rc_call_site_returnVarInsn_format3rc_call_site.getTree());
                    break;
                case '(':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format3rc_method_in_instruction4323);
                    insn_format3rc_method_return insn_format3rc_method_returnVarInsn_format3rc_method = insn_format3rc_method();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format3rc_method_returnVarInsn_format3rc_method.getTree());
                    break;
                case ')':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format3rc_method_odex_in_instruction4329);
                    insn_format3rc_method_odex_return insn_format3rc_method_odex_returnVarInsn_format3rc_method_odex = insn_format3rc_method_odex();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format3rc_method_odex_returnVarInsn_format3rc_method_odex.getTree());
                    break;
                case '*':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format3rc_type_in_instruction4335);
                    insn_format3rc_type_return insn_format3rc_type_returnVarInsn_format3rc_type = insn_format3rc_type();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format3rc_type_returnVarInsn_format3rc_type.getTree());
                    break;
                case '+':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format3rmi_method_in_instruction4341);
                    insn_format3rmi_method_return insn_format3rmi_method_returnVarInsn_format3rmi_method = insn_format3rmi_method();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format3rmi_method_returnVarInsn_format3rmi_method.getTree());
                    break;
                case ',':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format3rms_method_in_instruction4347);
                    insn_format3rms_method_return insn_format3rms_method_returnVarInsn_format3rms_method = insn_format3rms_method();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format3rms_method_returnVarInsn_format3rms_method.getTree());
                    break;
                case '-':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format45cc_method_in_instruction4353);
                    insn_format45cc_method_return insn_format45cc_method_returnVarInsn_format45cc_method = insn_format45cc_method();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format45cc_method_returnVarInsn_format45cc_method.getTree());
                    break;
                case '.':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format4rcc_method_in_instruction4359);
                    insn_format4rcc_method_return insn_format4rcc_method_returnVarInsn_format4rcc_method = insn_format4rcc_method();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format4rcc_method_returnVarInsn_format4rcc_method.getTree());
                    break;
                case '/':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_format51l_in_instruction4365);
                    insn_format51l_return insn_format51l_returnVarInsn_format51l = insn_format51l();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_format51l_returnVarInsn_format51l.getTree());
                    break;
                case '0':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_array_data_directive_in_instruction4371);
                    insn_array_data_directive_return insn_array_data_directive_returnVarInsn_array_data_directive = insn_array_data_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_array_data_directive_returnVarInsn_array_data_directive.getTree());
                    break;
                case '1':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction4377);
                    insn_packed_switch_directive_return insn_packed_switch_directive_returnVarInsn_packed_switch_directive = insn_packed_switch_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_packed_switch_directive_returnVarInsn_packed_switch_directive.getTree());
                    break;
                case '2':
                    commonTree = (CommonTree) this.adaptor.nil();
                    pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction4383);
                    insn_sparse_switch_directive_return insn_sparse_switch_directive_returnVarInsn_sparse_switch_directive = insn_sparse_switch_directive();
                    this.state._fsp--;
                    this.adaptor.addChild(commonTree, insn_sparse_switch_directive_returnVarInsn_sparse_switch_directive.getTree());
                    break;
                default:
                    commonTree = null;
                    break;
            }
            instruction_returnVar.stop = this.input.LT(-1);
            instruction_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(instruction_returnVar.tree, instruction_returnVar.start, instruction_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            instruction_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, instruction_returnVar.start, this.input.LT(-1), e);
        }
        return instruction_returnVar;
    }

    public static class insn_format10t_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format10t_return insn_format10t() throws RecognitionException {
        insn_format10t_return insn_format10t_returnVar = new insn_format10t_return();
        insn_format10t_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10t");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 44, FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4403));
            pushFollow(FOLLOW_label_ref_in_insn_format10t4405);
            label_ref_return label_ref_returnVarLabel_ref = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree());
            insn_format10t_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format10t_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(144, insn_format10t_returnVar.start, "I_STATEMENT_FORMAT10t"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format10t_returnVar.tree = commonTree;
            insn_format10t_returnVar.stop = this.input.LT(-1);
            insn_format10t_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format10t_returnVar.tree, insn_format10t_returnVar.start, insn_format10t_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format10t_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format10t_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format10t_returnVar;
    }

    public static class insn_format10x_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format10x_return insn_format10x() throws RecognitionException {
        insn_format10x_return insn_format10x_returnVar = new insn_format10x_return();
        insn_format10x_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10x");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 45, FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4435));
            insn_format10x_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format10x_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(145, insn_format10x_returnVar.start, "I_STATEMENT_FORMAT10x"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format10x_returnVar.tree = commonTree;
            insn_format10x_returnVar.stop = this.input.LT(-1);
            insn_format10x_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format10x_returnVar.tree, insn_format10x_returnVar.start, insn_format10x_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format10x_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format10x_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format10x_returnVar;
    }

    public static class insn_format10x_odex_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format10x_odex_return insn_format10x_odex() throws RecognitionException {
        insn_format10x_odex_return insn_format10x_odex_returnVar = new insn_format10x_odex_return();
        insn_format10x_odex_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token token = (Token) match(this.input, 46, FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4463);
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token));
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null);
            insn_format10x_odex_returnVar.stop = this.input.LT(-1);
            insn_format10x_odex_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format10x_odex_returnVar.tree, insn_format10x_odex_returnVar.start, insn_format10x_odex_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format10x_odex_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format10x_odex_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format10x_odex_returnVar;
    }

    public static class insn_format11n_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format11n_return insn_format11n() throws RecognitionException {
        insn_format11n_return insn_format11n_returnVar = new insn_format11n_return();
        insn_format11n_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT11n");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 47, FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4484));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format11n4486));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format11n4488));
            pushFollow(FOLLOW_integral_literal_in_insn_format11n4490);
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree());
            insn_format11n_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format11n_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(146, insn_format11n_returnVar.start, "I_STATEMENT_FORMAT11n"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format11n_returnVar.tree = commonTree;
            insn_format11n_returnVar.stop = this.input.LT(-1);
            insn_format11n_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format11n_returnVar.tree, insn_format11n_returnVar.start, insn_format11n_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format11n_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format11n_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format11n_returnVar;
    }

    public static class insn_format11x_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format11x_return insn_format11x() throws RecognitionException {
        insn_format11x_return insn_format11x_returnVar = new insn_format11x_return();
        insn_format11x_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT11x");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 48, FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4522));
            rewriteRuleTokenStream.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format11x4524));
            insn_format11x_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format11x_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(147, insn_format11x_returnVar.start, "I_STATEMENT_FORMAT11x"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format11x_returnVar.tree = commonTree;
            insn_format11x_returnVar.stop = this.input.LT(-1);
            insn_format11x_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format11x_returnVar.tree, insn_format11x_returnVar.start, insn_format11x_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format11x_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format11x_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format11x_returnVar;
    }

    public static class insn_format12x_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format12x_return insn_format12x() throws RecognitionException {
        insn_format12x_return insn_format12x_returnVar = new insn_format12x_return();
        insn_format12x_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format12x");
        try {
            pushFollow(FOLLOW_instruction_format12x_in_insn_format12x4554);
            instruction_format12x_return instruction_format12x_returnVarInstruction_format12x = instruction_format12x();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(instruction_format12x_returnVarInstruction_format12x.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format12x4556));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format12x4558));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format12x4560));
            insn_format12x_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format12x_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(148, insn_format12x_returnVar.start, "I_STATEMENT_FORMAT12x"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format12x_returnVar.tree = commonTree;
            insn_format12x_returnVar.stop = this.input.LT(-1);
            insn_format12x_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format12x_returnVar.tree, insn_format12x_returnVar.start, insn_format12x_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format12x_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format12x_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format12x_returnVar;
    }

    public static class insn_format20bc_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0092 A[Catch: all -> 0x0110, RecognitionException -> 0x0112, TryCatch #2 {RecognitionException -> 0x0112, blocks: (B:3:0x0033, B:5:0x007c, B:7:0x0080, B:9:0x0086, B:11:0x008c, B:18:0x009f, B:13:0x0092, B:15:0x0096, B:17:0x009c), top: B:25:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x0096 A[Catch: all -> 0x0110, RecognitionException -> 0x0112, TryCatch #2 {RecognitionException -> 0x0112, blocks: (B:3:0x0033, B:5:0x007c, B:7:0x0080, B:9:0x0086, B:11:0x008c, B:18:0x009f, B:13:0x0092, B:15:0x0096, B:17:0x009c), top: B:25:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x009b  */
    public final insn_format20bc_return insn_format20bc() throws RecognitionException {
        String text;
        insn_format20bc_return insn_format20bc_returnVar = new insn_format20bc_return();
        insn_format20bc_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token VERIFICATION_ERROR_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT20bc");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule verification_error_reference");
        try {
            Token token = (Token) match(this.input, 51, FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4592);
            rewriteRuleTokenStream3.add(token);
            rewriteRuleTokenStream2.add((Token) match(this.input, 214, FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4594));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format20bc4596));
            pushFollow(FOLLOW_verification_error_reference_in_insn_format20bc4598);
            verification_error_reference_return verification_error_reference_returnVarVerification_error_reference = verification_error_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(verification_error_reference_returnVarVerification_error_reference.getTree());
            if (this.allowOdex) {
                if (this.opcodes.getOpcodeByName(token != null ? token.getText() : null) == null || this.apiLevel >= 14) {
                    TokenStream tokenStream = this.input;
                    if (token != null) {
                        text = token.getText();
                    } else {
                        text = null;
                    }
                    throwOdexedInstructionException(tokenStream, text);
                }
            } else {
                TokenStream tokenStream2 = this.input;
                if (token != null) {
                    text = token.getText();
                } else {
                    text = null;
                }
                throwOdexedInstructionException(tokenStream2, text);
            }
            insn_format20bc_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format20bc_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(149, "I_STATEMENT_FORMAT20bc"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format20bc_returnVar.tree = commonTree;
            insn_format20bc_returnVar.stop = this.input.LT(-1);
            insn_format20bc_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format20bc_returnVar.tree, insn_format20bc_returnVar.start, insn_format20bc_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format20bc_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format20bc_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format20bc_returnVar;
    }

    public static class insn_format20t_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format20t_return insn_format20t() throws RecognitionException {
        insn_format20t_return insn_format20t_returnVar = new insn_format20t_return();
        insn_format20t_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT20t");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 52, FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4635));
            pushFollow(FOLLOW_label_ref_in_insn_format20t4637);
            label_ref_return label_ref_returnVarLabel_ref = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree());
            insn_format20t_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format20t_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(150, insn_format20t_returnVar.start, "I_STATEMENT_FORMAT20t"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format20t_returnVar.tree = commonTree;
            insn_format20t_returnVar.stop = this.input.LT(-1);
            insn_format20t_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format20t_returnVar.tree, insn_format20t_returnVar.start, insn_format20t_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format20t_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format20t_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format20t_returnVar;
    }

    public static class insn_format21c_field_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21c_field_return insn_format21c_field() throws RecognitionException {
        insn_format21c_field_return insn_format21c_field_returnVar = new insn_format21c_field_return();
        insn_format21c_field_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule field_reference");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 53, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4667));
            rewriteRuleTokenStream3.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_field4669));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_field4671));
            pushFollow(FOLLOW_field_reference_in_insn_format21c_field4673);
            field_reference_return field_reference_returnVarField_reference = field_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree());
            insn_format21c_field_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_field_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(151, insn_format21c_field_returnVar.start, "I_STATEMENT_FORMAT21c_FIELD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21c_field_returnVar.tree = commonTree;
            insn_format21c_field_returnVar.stop = this.input.LT(-1);
            insn_format21c_field_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21c_field_returnVar.tree, insn_format21c_field_returnVar.start, insn_format21c_field_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21c_field_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21c_field_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21c_field_returnVar;
    }

    public static class insn_format21c_field_odex_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0092 A[Catch: all -> 0x0112, RecognitionException -> 0x0114, TryCatch #2 {RecognitionException -> 0x0114, blocks: (B:3:0x0033, B:5:0x007c, B:7:0x0080, B:9:0x0086, B:11:0x008c, B:18:0x009f, B:13:0x0092, B:15:0x0096, B:17:0x009c), top: B:25:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x0096 A[Catch: all -> 0x0112, RecognitionException -> 0x0114, TryCatch #2 {RecognitionException -> 0x0114, blocks: (B:3:0x0033, B:5:0x007c, B:7:0x0080, B:9:0x0086, B:11:0x008c, B:18:0x009f, B:13:0x0092, B:15:0x0096, B:17:0x009c), top: B:25:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x009b  */
    public final insn_format21c_field_odex_return insn_format21c_field_odex() throws RecognitionException {
        String text;
        insn_format21c_field_odex_return insn_format21c_field_odex_returnVar = new insn_format21c_field_odex_return();
        insn_format21c_field_odex_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD_ODEX");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule field_reference");
        try {
            Token token = (Token) match(this.input, 54, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4705);
            rewriteRuleTokenStream3.add(token);
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_field_odex4707));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_field_odex4709));
            pushFollow(FOLLOW_field_reference_in_insn_format21c_field_odex4711);
            field_reference_return field_reference_returnVarField_reference = field_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree());
            if (this.allowOdex) {
                if (this.opcodes.getOpcodeByName(token != null ? token.getText() : null) == null || this.apiLevel >= 14) {
                    TokenStream tokenStream = this.input;
                    if (token != null) {
                        text = token.getText();
                    } else {
                        text = null;
                    }
                    throwOdexedInstructionException(tokenStream, text);
                }
            } else {
                TokenStream tokenStream2 = this.input;
                if (token != null) {
                    text = token.getText();
                } else {
                    text = null;
                }
                throwOdexedInstructionException(tokenStream2, text);
            }
            insn_format21c_field_odex_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_field_odex_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(151, insn_format21c_field_odex_returnVar.start, "I_STATEMENT_FORMAT21c_FIELD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21c_field_odex_returnVar.tree = commonTree;
            insn_format21c_field_odex_returnVar.stop = this.input.LT(-1);
            insn_format21c_field_odex_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21c_field_odex_returnVar.tree, insn_format21c_field_odex_returnVar.start, insn_format21c_field_odex_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21c_field_odex_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21c_field_odex_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21c_field_odex_returnVar;
    }

    public static class insn_format21c_method_handle_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21c_method_handle_return insn_format21c_method_handle() throws RecognitionException {
        insn_format21c_method_handle_return insn_format21c_method_handle_returnVar = new insn_format21c_method_handle_return();
        insn_format21c_method_handle_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_METHOD_HANDLE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_handle_reference");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 55, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_HANDLE_in_insn_format21c_method_handle4749));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_method_handle4751));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_method_handle4753));
            pushFollow(FOLLOW_method_handle_reference_in_insn_format21c_method_handle4755);
            method_handle_reference_return method_handle_reference_returnVarMethod_handle_reference = method_handle_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(method_handle_reference_returnVarMethod_handle_reference.getTree());
            insn_format21c_method_handle_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_method_handle_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(152, insn_format21c_method_handle_returnVar.start, "I_STATEMENT_FORMAT21c_METHOD_HANDLE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21c_method_handle_returnVar.tree = commonTree;
            insn_format21c_method_handle_returnVar.stop = this.input.LT(-1);
            insn_format21c_method_handle_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21c_method_handle_returnVar.tree, insn_format21c_method_handle_returnVar.start, insn_format21c_method_handle_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21c_method_handle_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21c_method_handle_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21c_method_handle_returnVar;
    }

    public static class insn_format21c_method_type_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21c_method_type_return insn_format21c_method_type() throws RecognitionException {
        insn_format21c_method_type_return insn_format21c_method_type_returnVar = new insn_format21c_method_type_return();
        insn_format21c_method_type_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_METHOD_TYPE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 56, FOLLOW_INSTRUCTION_FORMAT21c_METHOD_TYPE_in_insn_format21c_method_type4801));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_method_type4803));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_method_type4805));
            pushFollow(FOLLOW_method_prototype_in_insn_format21c_method_type4807);
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(method_prototype_returnVarMethod_prototype.getTree());
            insn_format21c_method_type_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_method_type_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(153, insn_format21c_method_type_returnVar.start, "I_STATEMENT_FORMAT21c_METHOD_TYPE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21c_method_type_returnVar.tree = commonTree;
            insn_format21c_method_type_returnVar.stop = this.input.LT(-1);
            insn_format21c_method_type_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21c_method_type_returnVar.tree, insn_format21c_method_type_returnVar.start, insn_format21c_method_type_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21c_method_type_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21c_method_type_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21c_method_type_returnVar;
    }

    public static class insn_format21c_string_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21c_string_return insn_format21c_string() throws RecognitionException {
        insn_format21c_string_return insn_format21c_string_returnVar = new insn_format21c_string_return();
        insn_format21c_string_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_STRING");
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 57, FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4851));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_string4853));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_string4855));
            rewriteRuleTokenStream3.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_insn_format21c_string4857));
            insn_format21c_string_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_string_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(154, insn_format21c_string_returnVar.start, "I_STATEMENT_FORMAT21c_STRING"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21c_string_returnVar.tree = commonTree;
            insn_format21c_string_returnVar.stop = this.input.LT(-1);
            insn_format21c_string_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21c_string_returnVar.tree, insn_format21c_string_returnVar.start, insn_format21c_string_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21c_string_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21c_string_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21c_string_returnVar;
    }

    public static class insn_format21c_type_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21c_type_return insn_format21c_type() throws RecognitionException {
        insn_format21c_type_return insn_format21c_type_returnVar = new insn_format21c_type_return();
        insn_format21c_type_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_TYPE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 58, FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4889));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21c_type4891));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21c_type4893));
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4895);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
            insn_format21c_type_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21c_type_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(155, insn_format21c_type_returnVar.start, "I_STATEMENT_FORMAT21c"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21c_type_returnVar.tree = commonTree;
            insn_format21c_type_returnVar.stop = this.input.LT(-1);
            insn_format21c_type_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21c_type_returnVar.tree, insn_format21c_type_returnVar.start, insn_format21c_type_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21c_type_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21c_type_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21c_type_returnVar;
    }

    public static class insn_format21ih_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21ih_return insn_format21ih() throws RecognitionException {
        insn_format21ih_return insn_format21ih_returnVar = new insn_format21ih_return();
        insn_format21ih_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21ih");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 59, FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4927));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21ih4929));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21ih4931));
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21ih4933);
            fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree());
            insn_format21ih_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21ih_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(156, insn_format21ih_returnVar.start, "I_STATEMENT_FORMAT21ih"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21ih_returnVar.tree = commonTree;
            insn_format21ih_returnVar.stop = this.input.LT(-1);
            insn_format21ih_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21ih_returnVar.tree, insn_format21ih_returnVar.start, insn_format21ih_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21ih_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21ih_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21ih_returnVar;
    }

    public static class insn_format21lh_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21lh_return insn_format21lh() throws RecognitionException {
        insn_format21lh_return insn_format21lh_returnVar = new insn_format21lh_return();
        insn_format21lh_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21lh");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 60, FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4965));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21lh4967));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21lh4969));
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21lh4971);
            fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree());
            insn_format21lh_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21lh_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(157, insn_format21lh_returnVar.start, "I_STATEMENT_FORMAT21lh"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21lh_returnVar.tree = commonTree;
            insn_format21lh_returnVar.stop = this.input.LT(-1);
            insn_format21lh_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21lh_returnVar.tree, insn_format21lh_returnVar.start, insn_format21lh_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21lh_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21lh_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21lh_returnVar;
    }

    public static class insn_format21s_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21s_return insn_format21s() throws RecognitionException {
        insn_format21s_return insn_format21s_returnVar = new insn_format21s_return();
        insn_format21s_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21s");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 61, FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s5003));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21s5005));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21s5007));
            pushFollow(FOLLOW_integral_literal_in_insn_format21s5009);
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree());
            insn_format21s_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21s_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(158, insn_format21s_returnVar.start, "I_STATEMENT_FORMAT21s"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21s_returnVar.tree = commonTree;
            insn_format21s_returnVar.stop = this.input.LT(-1);
            insn_format21s_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21s_returnVar.tree, insn_format21s_returnVar.start, insn_format21s_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21s_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21s_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21s_returnVar;
    }

    public static class insn_format21t_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format21t_return insn_format21t() throws RecognitionException {
        insn_format21t_return insn_format21t_returnVar = new insn_format21t_return();
        insn_format21t_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21t");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 62, FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t5041));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format21t5043));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format21t5045));
            pushFollow(FOLLOW_label_ref_in_insn_format21t5047);
            label_ref_return label_ref_returnVarLabel_ref = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree());
            insn_format21t_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format21t_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(159, insn_format21t_returnVar.start, "I_STATEMENT_FORMAT21t"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format21t_returnVar.tree = commonTree;
            insn_format21t_returnVar.stop = this.input.LT(-1);
            insn_format21t_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format21t_returnVar.tree, insn_format21t_returnVar.start, insn_format21t_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format21t_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format21t_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format21t_returnVar;
    }

    public static class insn_format22b_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format22b_return insn_format22b() throws RecognitionException {
        insn_format22b_return insn_format22b_returnVar = new insn_format22b_return();
        insn_format22b_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22b");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 63, FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b5079));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22b5081));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22b5083));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22b5085));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22b5087));
            pushFollow(FOLLOW_integral_literal_in_insn_format22b5089);
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(integral_literal_returnVarIntegral_literal.getTree());
            insn_format22b_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22b_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(160, insn_format22b_returnVar.start, "I_STATEMENT_FORMAT22b"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format22b_returnVar.tree = commonTree;
            insn_format22b_returnVar.stop = this.input.LT(-1);
            insn_format22b_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format22b_returnVar.tree, insn_format22b_returnVar.start, insn_format22b_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format22b_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format22b_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format22b_returnVar;
    }

    public static class insn_format22c_field_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format22c_field_return insn_format22c_field() throws RecognitionException {
        insn_format22c_field_return insn_format22c_field_returnVar = new insn_format22c_field_return();
        insn_format22c_field_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule field_reference");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 64, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field5123));
            rewriteRuleTokenStream3.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field5125));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_field5127));
            rewriteRuleTokenStream3.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field5129));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_field5131));
            pushFollow(FOLLOW_field_reference_in_insn_format22c_field5133);
            field_reference_return field_reference_returnVarField_reference = field_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree());
            insn_format22c_field_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22c_field_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(161, insn_format22c_field_returnVar.start, "I_STATEMENT_FORMAT22c_FIELD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format22c_field_returnVar.tree = commonTree;
            insn_format22c_field_returnVar.stop = this.input.LT(-1);
            insn_format22c_field_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format22c_field_returnVar.tree, insn_format22c_field_returnVar.start, insn_format22c_field_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format22c_field_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format22c_field_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format22c_field_returnVar;
    }

    public static class insn_format22c_field_odex_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x00ac A[Catch: all -> 0x0135, RecognitionException -> 0x0137, TryCatch #2 {RecognitionException -> 0x0137, blocks: (B:3:0x0033, B:5:0x0096, B:7:0x009a, B:9:0x00a0, B:11:0x00a6, B:18:0x00b9, B:13:0x00ac, B:15:0x00b0, B:17:0x00b6), top: B:25:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x00b0 A[Catch: all -> 0x0135, RecognitionException -> 0x0137, TryCatch #2 {RecognitionException -> 0x0137, blocks: (B:3:0x0033, B:5:0x0096, B:7:0x009a, B:9:0x00a0, B:11:0x00a6, B:18:0x00b9, B:13:0x00ac, B:15:0x00b0, B:17:0x00b6), top: B:25:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x00b5  */
    public final insn_format22c_field_odex_return insn_format22c_field_odex() throws RecognitionException {
        String text;
        insn_format22c_field_odex_return insn_format22c_field_odex_returnVar = new insn_format22c_field_odex_return();
        insn_format22c_field_odex_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD_ODEX");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule field_reference");
        try {
            Token token = (Token) match(this.input, 65, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex5167);
            rewriteRuleTokenStream3.add(token);
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field_odex5169));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_field_odex5171));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_field_odex5173));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_field_odex5175));
            pushFollow(FOLLOW_field_reference_in_insn_format22c_field_odex5177);
            field_reference_return field_reference_returnVarField_reference = field_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(field_reference_returnVarField_reference.getTree());
            if (this.allowOdex) {
                if (this.opcodes.getOpcodeByName(token != null ? token.getText() : null) == null || this.apiLevel >= 14) {
                    TokenStream tokenStream = this.input;
                    if (token != null) {
                        text = token.getText();
                    } else {
                        text = null;
                    }
                    throwOdexedInstructionException(tokenStream, text);
                }
            } else {
                TokenStream tokenStream2 = this.input;
                if (token != null) {
                    text = token.getText();
                } else {
                    text = null;
                }
                throwOdexedInstructionException(tokenStream2, text);
            }
            insn_format22c_field_odex_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22c_field_odex_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(161, insn_format22c_field_odex_returnVar.start, "I_STATEMENT_FORMAT22c_FIELD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format22c_field_odex_returnVar.tree = commonTree;
            insn_format22c_field_odex_returnVar.stop = this.input.LT(-1);
            insn_format22c_field_odex_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format22c_field_odex_returnVar.tree, insn_format22c_field_odex_returnVar.start, insn_format22c_field_odex_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format22c_field_odex_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format22c_field_odex_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format22c_field_odex_returnVar;
    }

    public static class insn_format22c_type_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format22c_type_return insn_format22c_type() throws RecognitionException {
        insn_format22c_type_return insn_format22c_type_returnVar = new insn_format22c_type_return();
        insn_format22c_type_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_TYPE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 66, FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type5217));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_type5219));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_type5221));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22c_type5223));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22c_type5225));
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type5227);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
            insn_format22c_type_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22c_type_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(162, insn_format22c_type_returnVar.start, "I_STATEMENT_FORMAT22c_TYPE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format22c_type_returnVar.tree = commonTree;
            insn_format22c_type_returnVar.stop = this.input.LT(-1);
            insn_format22c_type_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format22c_type_returnVar.tree, insn_format22c_type_returnVar.start, insn_format22c_type_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format22c_type_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format22c_type_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format22c_type_returnVar;
    }

    public static class insn_format22cs_field_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format22cs_field_return insn_format22cs_field() throws RecognitionException {
        insn_format22cs_field_return insn_format22cs_field_returnVar = new insn_format22cs_field_return();
        insn_format22cs_field_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token token = (Token) match(this.input, 67, FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field5261);
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22cs_field5263)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22cs_field5265)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22cs_field5267)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22cs_field5269)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 38, FOLLOW_FIELD_OFFSET_in_insn_format22cs_field5271)));
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null);
            insn_format22cs_field_returnVar.stop = this.input.LT(-1);
            insn_format22cs_field_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format22cs_field_returnVar.tree, insn_format22cs_field_returnVar.start, insn_format22cs_field_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format22cs_field_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format22cs_field_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format22cs_field_returnVar;
    }

    public static class insn_format22s_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format22s_return insn_format22s() throws RecognitionException {
        insn_format22s_return insn_format22s_returnVar = new insn_format22s_return();
        insn_format22s_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format22s");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
        try {
            pushFollow(FOLLOW_instruction_format22s_in_insn_format22s5292);
            instruction_format22s_return instruction_format22s_returnVarInstruction_format22s = instruction_format22s();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(instruction_format22s_returnVarInstruction_format22s.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22s5294));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22s5296));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22s5298));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22s5300));
            pushFollow(FOLLOW_integral_literal_in_insn_format22s5302);
            integral_literal_return integral_literal_returnVarIntegral_literal = integral_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(integral_literal_returnVarIntegral_literal.getTree());
            insn_format22s_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22s_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(163, insn_format22s_returnVar.start, "I_STATEMENT_FORMAT22s"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format22s_returnVar.tree = commonTree;
            insn_format22s_returnVar.stop = this.input.LT(-1);
            insn_format22s_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format22s_returnVar.tree, insn_format22s_returnVar.start, insn_format22s_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format22s_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format22s_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format22s_returnVar;
    }

    public static class insn_format22t_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format22t_return insn_format22t() throws RecognitionException {
        insn_format22t_return insn_format22t_returnVar = new insn_format22t_return();
        insn_format22t_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22t");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 70, FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t5336));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22t5338));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22t5340));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22t5342));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22t5344));
            pushFollow(FOLLOW_label_ref_in_insn_format22t5346);
            label_ref_return label_ref_returnVarLabel_ref = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree());
            insn_format22t_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22t_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(164, insn_format22t_returnVar.start, "I_STATEMENT_FFORMAT22t"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format22t_returnVar.tree = commonTree;
            insn_format22t_returnVar.stop = this.input.LT(-1);
            insn_format22t_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format22t_returnVar.tree, insn_format22t_returnVar.start, insn_format22t_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format22t_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format22t_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format22t_returnVar;
    }

    public static class insn_format22x_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format22x_return insn_format22x() throws RecognitionException {
        insn_format22x_return insn_format22x_returnVar = new insn_format22x_return();
        insn_format22x_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22x");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 71, FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x5380));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22x5382));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format22x5384));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format22x5386));
            insn_format22x_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format22x_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(165, insn_format22x_returnVar.start, "I_STATEMENT_FORMAT22x"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format22x_returnVar.tree = commonTree;
            insn_format22x_returnVar.stop = this.input.LT(-1);
            insn_format22x_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format22x_returnVar.tree, insn_format22x_returnVar.start, insn_format22x_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format22x_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format22x_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format22x_returnVar;
    }

    public static class insn_format23x_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format23x_return insn_format23x() throws RecognitionException {
        insn_format23x_return insn_format23x_returnVar = new insn_format23x_return();
        insn_format23x_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT23x");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 72, FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x5418));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x5420));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format23x5422));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x5424));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format23x5426));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format23x5428));
            insn_format23x_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format23x_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(166, insn_format23x_returnVar.start, "I_STATEMENT_FORMAT23x"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format23x_returnVar.tree = commonTree;
            insn_format23x_returnVar.stop = this.input.LT(-1);
            insn_format23x_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format23x_returnVar.tree, insn_format23x_returnVar.start, insn_format23x_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format23x_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format23x_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format23x_returnVar;
    }

    public static class insn_format30t_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format30t_return insn_format30t() throws RecognitionException {
        insn_format30t_return insn_format30t_returnVar = new insn_format30t_return();
        insn_format30t_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT30t");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream.add((Token) match(this.input, 73, FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t5462));
            pushFollow(FOLLOW_label_ref_in_insn_format30t5464);
            label_ref_return label_ref_returnVarLabel_ref = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree());
            insn_format30t_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format30t_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(167, insn_format30t_returnVar.start, "I_STATEMENT_FORMAT30t"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format30t_returnVar.tree = commonTree;
            insn_format30t_returnVar.stop = this.input.LT(-1);
            insn_format30t_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format30t_returnVar.tree, insn_format30t_returnVar.start, insn_format30t_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format30t_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format30t_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format30t_returnVar;
    }

    public static class insn_format31c_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format31c_return insn_format31c() throws RecognitionException {
        insn_format31c_return insn_format31c_returnVar = new insn_format31c_return();
        insn_format31c_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31c");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 74, FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5494));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31c5496));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format31c5498));
            rewriteRuleTokenStream4.add((Token) match(this.input, 211, FOLLOW_STRING_LITERAL_in_insn_format31c5500));
            insn_format31c_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format31c_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(168, insn_format31c_returnVar.start, "I_STATEMENT_FORMAT31c"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format31c_returnVar.tree = commonTree;
            insn_format31c_returnVar.stop = this.input.LT(-1);
            insn_format31c_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format31c_returnVar.tree, insn_format31c_returnVar.start, insn_format31c_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format31c_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format31c_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format31c_returnVar;
    }

    public static class insn_format31i_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format31i_return insn_format31i() throws RecognitionException {
        insn_format31i_return insn_format31i_returnVar = new insn_format31i_return();
        insn_format31i_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format31i");
        try {
            pushFollow(FOLLOW_instruction_format31i_in_insn_format31i5531);
            instruction_format31i_return instruction_format31i_returnVarInstruction_format31i = instruction_format31i();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(instruction_format31i_returnVarInstruction_format31i.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31i5533));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format31i5535));
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format31i5537);
            fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree());
            insn_format31i_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format31i_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(169, insn_format31i_returnVar.start, "I_STATEMENT_FORMAT31i"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format31i_returnVar.tree = commonTree;
            insn_format31i_returnVar.stop = this.input.LT(-1);
            insn_format31i_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format31i_returnVar.tree, insn_format31i_returnVar.start, insn_format31i_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format31i_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format31i_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format31i_returnVar;
    }

    public static class insn_format31t_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format31t_return insn_format31t() throws RecognitionException {
        insn_format31t_return insn_format31t_returnVar = new insn_format31t_return();
        insn_format31t_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31t");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 77, FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5569));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format31t5571));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format31t5573));
            pushFollow(FOLLOW_label_ref_in_insn_format31t5575);
            label_ref_return label_ref_returnVarLabel_ref = label_ref();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(label_ref_returnVarLabel_ref.getTree());
            insn_format31t_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format31t_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(170, insn_format31t_returnVar.start, "I_STATEMENT_FORMAT31t"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format31t_returnVar.tree = commonTree;
            insn_format31t_returnVar.stop = this.input.LT(-1);
            insn_format31t_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format31t_returnVar.tree, insn_format31t_returnVar.start, insn_format31t_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format31t_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format31t_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format31t_returnVar;
    }

    public static class insn_format32x_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format32x_return insn_format32x() throws RecognitionException {
        insn_format32x_return insn_format32x_returnVar = new insn_format32x_return();
        insn_format32x_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT32x");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 78, FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5607));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format32x5609));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format32x5611));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format32x5613));
            insn_format32x_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format32x_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(171, insn_format32x_returnVar.start, "I_STATEMENT_FORMAT32x"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format32x_returnVar.tree = commonTree;
            insn_format32x_returnVar.stop = this.input.LT(-1);
            insn_format32x_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format32x_returnVar.tree, insn_format32x_returnVar.start, insn_format32x_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format32x_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format32x_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format32x_returnVar;
    }

    public static class insn_format35c_call_site_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format35c_call_site_return insn_format35c_call_site() throws RecognitionException {
        insn_format35c_call_site_return insn_format35c_call_site_returnVar = new insn_format35c_call_site_return();
        insn_format35c_call_site_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_CALL_SITE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule register_list");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule call_site_reference");
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 79, FOLLOW_INSTRUCTION_FORMAT35c_CALL_SITE_in_insn_format35c_call_site5650));
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35c_call_site5652));
            pushFollow(FOLLOW_register_list_in_insn_format35c_call_site5654);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(register_list_returnVarRegister_list.getTree());
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35c_call_site5656));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35c_call_site5658));
            pushFollow(FOLLOW_call_site_reference_in_insn_format35c_call_site5660);
            call_site_reference_return call_site_reference_returnVarCall_site_reference = call_site_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(call_site_reference_returnVarCall_site_reference.getTree());
            insn_format35c_call_site_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format35c_call_site_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(172, insn_format35c_call_site_returnVar.start, "I_STATEMENT_FORMAT35c_CALL_SITE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format35c_call_site_returnVar.tree = commonTree;
            insn_format35c_call_site_returnVar.stop = this.input.LT(-1);
            insn_format35c_call_site_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format35c_call_site_returnVar.tree, insn_format35c_call_site_returnVar.start, insn_format35c_call_site_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format35c_call_site_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format35c_call_site_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format35c_call_site_returnVar;
    }

    public static class insn_format35c_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format35c_method_return insn_format35c_method() throws RecognitionException {
        insn_format35c_method_return insn_format35c_method_returnVar = new insn_format35c_method_return();
        insn_format35c_method_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format35c_method");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule method_reference");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule register_list");
        try {
            pushFollow(FOLLOW_instruction_format35c_method_in_insn_format35c_method5692);
            instruction_format35c_method_return instruction_format35c_method_returnVarInstruction_format35c_method = instruction_format35c_method();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(instruction_format35c_method_returnVarInstruction_format35c_method.getTree());
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35c_method5694));
            pushFollow(FOLLOW_register_list_in_insn_format35c_method5696);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            rewriteRuleSubtreeStream3.add(register_list_returnVarRegister_list.getTree());
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35c_method5698));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35c_method5700));
            pushFollow(FOLLOW_method_reference_in_insn_format35c_method5702);
            method_reference_return method_reference_returnVarMethod_reference = method_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(method_reference_returnVarMethod_reference.getTree());
            insn_format35c_method_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format35c_method_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(173, insn_format35c_method_returnVar.start, "I_STATEMENT_FORMAT35c_METHOD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format35c_method_returnVar.tree = commonTree;
            insn_format35c_method_returnVar.stop = this.input.LT(-1);
            insn_format35c_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format35c_method_returnVar.tree, insn_format35c_method_returnVar.start, insn_format35c_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format35c_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format35c_method_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format35c_method_returnVar;
    }

    public static class insn_format35c_type_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format35c_type_return insn_format35c_type() throws RecognitionException {
        insn_format35c_type_return insn_format35c_type_returnVar = new insn_format35c_type_return();
        insn_format35c_type_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_TYPE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule register_list");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 83, FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5734));
            rewriteRuleTokenStream3.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35c_type5736));
            pushFollow(FOLLOW_register_list_in_insn_format35c_type5738);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(register_list_returnVarRegister_list.getTree());
            rewriteRuleTokenStream4.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35c_type5740));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35c_type5742));
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5744);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
            insn_format35c_type_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format35c_type_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(174, insn_format35c_type_returnVar.start, "I_STATEMENT_FORMAT35c_TYPE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format35c_type_returnVar.tree = commonTree;
            insn_format35c_type_returnVar.stop = this.input.LT(-1);
            insn_format35c_type_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format35c_type_returnVar.tree, insn_format35c_type_returnVar.start, insn_format35c_type_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format35c_type_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format35c_type_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format35c_type_returnVar;
    }

    public static class insn_format35c_method_odex_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format35c_method_odex_return insn_format35c_method_odex() throws RecognitionException {
        insn_format35c_method_odex_return insn_format35c_method_odex_returnVar = new insn_format35c_method_odex_return();
        insn_format35c_method_odex_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token token = (Token) match(this.input, 81, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5776);
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5778)));
            pushFollow(FOLLOW_register_list_in_insn_format35c_method_odex5780);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, register_list_returnVarRegister_list.getTree());
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5782)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35c_method_odex5784)));
            pushFollow(FOLLOW_method_reference_in_insn_format35c_method_odex5786);
            method_reference_return method_reference_returnVarMethod_reference = method_reference();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, method_reference_returnVarMethod_reference.getTree());
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null);
            insn_format35c_method_odex_returnVar.stop = this.input.LT(-1);
            insn_format35c_method_odex_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format35c_method_odex_returnVar.tree, insn_format35c_method_odex_returnVar.start, insn_format35c_method_odex_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format35c_method_odex_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format35c_method_odex_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format35c_method_odex_returnVar;
    }

    public static class insn_format35mi_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format35mi_method_return insn_format35mi_method() throws RecognitionException {
        insn_format35mi_method_return insn_format35mi_method_returnVar = new insn_format35mi_method_return();
        insn_format35mi_method_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token token = (Token) match(this.input, 84, FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5807);
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35mi_method5809)));
            pushFollow(FOLLOW_register_list_in_insn_format35mi_method5811);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, register_list_returnVarRegister_list.getTree());
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5813)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35mi_method5815)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 43, FOLLOW_INLINE_INDEX_in_insn_format35mi_method5817)));
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null);
            insn_format35mi_method_returnVar.stop = this.input.LT(-1);
            insn_format35mi_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format35mi_method_returnVar.tree, insn_format35mi_method_returnVar.start, insn_format35mi_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format35mi_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format35mi_method_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format35mi_method_returnVar;
    }

    public static class insn_format35ms_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format35ms_method_return insn_format35ms_method() throws RecognitionException {
        insn_format35ms_method_return insn_format35ms_method_returnVar = new insn_format35ms_method_return();
        insn_format35ms_method_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token token = (Token) match(this.input, 85, FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5838);
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format35ms_method5840)));
            pushFollow(FOLLOW_register_list_in_insn_format35ms_method5842);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, register_list_returnVarRegister_list.getTree());
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5844)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format35ms_method5846)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 216, FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5848)));
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null);
            insn_format35ms_method_returnVar.stop = this.input.LT(-1);
            insn_format35ms_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format35ms_method_returnVar.tree, insn_format35ms_method_returnVar.start, insn_format35ms_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format35ms_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format35ms_method_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format35ms_method_returnVar;
    }

    public static class insn_format3rc_call_site_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format3rc_call_site_return insn_format3rc_call_site() throws RecognitionException {
        insn_format3rc_call_site_return insn_format3rc_call_site_returnVar = new insn_format3rc_call_site_return();
        insn_format3rc_call_site_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT3rc_CALL_SITE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule register_range");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule call_site_reference");
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 86, FOLLOW_INSTRUCTION_FORMAT3rc_CALL_SITE_in_insn_format3rc_call_site5874));
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rc_call_site5876));
            pushFollow(FOLLOW_register_range_in_insn_format3rc_call_site5878);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(register_range_returnVarRegister_range.getTree());
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rc_call_site5880));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rc_call_site5882));
            pushFollow(FOLLOW_call_site_reference_in_insn_format3rc_call_site5884);
            call_site_reference_return call_site_reference_returnVarCall_site_reference = call_site_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(call_site_reference_returnVarCall_site_reference.getTree());
            insn_format3rc_call_site_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format3rc_call_site_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(175, insn_format3rc_call_site_returnVar.start, "I_STATEMENT_FORMAT3rc_CALL_SITE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format3rc_call_site_returnVar.tree = commonTree;
            insn_format3rc_call_site_returnVar.stop = this.input.LT(-1);
            insn_format3rc_call_site_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format3rc_call_site_returnVar.tree, insn_format3rc_call_site_returnVar.start, insn_format3rc_call_site_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format3rc_call_site_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format3rc_call_site_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format3rc_call_site_returnVar;
    }

    public static class insn_format3rc_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format3rc_method_return insn_format3rc_method() throws RecognitionException {
        insn_format3rc_method_return insn_format3rc_method_returnVar = new insn_format3rc_method_return();
        insn_format3rc_method_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT3rc_METHOD");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_reference");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule register_range");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 87, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5916));
            rewriteRuleTokenStream3.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rc_method5918));
            pushFollow(FOLLOW_register_range_in_insn_format3rc_method5920);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(register_range_returnVarRegister_range.getTree());
            rewriteRuleTokenStream4.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5922));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rc_method5924));
            pushFollow(FOLLOW_method_reference_in_insn_format3rc_method5926);
            method_reference_return method_reference_returnVarMethod_reference = method_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree());
            insn_format3rc_method_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format3rc_method_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(176, insn_format3rc_method_returnVar.start, "I_STATEMENT_FORMAT3rc_METHOD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format3rc_method_returnVar.tree = commonTree;
            insn_format3rc_method_returnVar.stop = this.input.LT(-1);
            insn_format3rc_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format3rc_method_returnVar.tree, insn_format3rc_method_returnVar.start, insn_format3rc_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format3rc_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format3rc_method_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format3rc_method_returnVar;
    }

    public static class insn_format3rc_method_odex_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format3rc_method_odex_return insn_format3rc_method_odex() throws RecognitionException {
        insn_format3rc_method_odex_return insn_format3rc_method_odex_returnVar = new insn_format3rc_method_odex_return();
        insn_format3rc_method_odex_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token token = (Token) match(this.input, 88, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5958);
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5960)));
            pushFollow(FOLLOW_register_list_in_insn_format3rc_method_odex5962);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, register_list_returnVarRegister_list.getTree());
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5964)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rc_method_odex5966)));
            pushFollow(FOLLOW_method_reference_in_insn_format3rc_method_odex5968);
            method_reference_return method_reference_returnVarMethod_reference = method_reference();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, method_reference_returnVarMethod_reference.getTree());
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null);
            insn_format3rc_method_odex_returnVar.stop = this.input.LT(-1);
            insn_format3rc_method_odex_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format3rc_method_odex_returnVar.tree, insn_format3rc_method_odex_returnVar.start, insn_format3rc_method_odex_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format3rc_method_odex_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format3rc_method_odex_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format3rc_method_odex_returnVar;
    }

    public static class insn_format3rc_type_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format3rc_type_return insn_format3rc_type() throws RecognitionException {
        insn_format3rc_type_return insn_format3rc_type_returnVar = new insn_format3rc_type_return();
        insn_format3rc_type_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT3rc_TYPE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule register_range");
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 89, FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5989));
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rc_type5991));
            pushFollow(FOLLOW_register_range_in_insn_format3rc_type5993);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(register_range_returnVarRegister_range.getTree());
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5995));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rc_type5997));
            pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5999);
            nonvoid_type_descriptor_return nonvoid_type_descriptor_returnVarNonvoid_type_descriptor = nonvoid_type_descriptor();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(nonvoid_type_descriptor_returnVarNonvoid_type_descriptor.getTree());
            insn_format3rc_type_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format3rc_type_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(177, insn_format3rc_type_returnVar.start, "I_STATEMENT_FORMAT3rc_TYPE"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format3rc_type_returnVar.tree = commonTree;
            insn_format3rc_type_returnVar.stop = this.input.LT(-1);
            insn_format3rc_type_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format3rc_type_returnVar.tree, insn_format3rc_type_returnVar.start, insn_format3rc_type_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format3rc_type_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format3rc_type_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format3rc_type_returnVar;
    }

    public static class insn_format3rmi_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format3rmi_method_return insn_format3rmi_method() throws RecognitionException {
        insn_format3rmi_method_return insn_format3rmi_method_returnVar = new insn_format3rmi_method_return();
        insn_format3rmi_method_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token token = (Token) match(this.input, 90, FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method6031);
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rmi_method6033)));
            pushFollow(FOLLOW_register_range_in_insn_format3rmi_method6035);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, register_range_returnVarRegister_range.getTree());
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method6037)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rmi_method6039)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 43, FOLLOW_INLINE_INDEX_in_insn_format3rmi_method6041)));
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null);
            insn_format3rmi_method_returnVar.stop = this.input.LT(-1);
            insn_format3rmi_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format3rmi_method_returnVar.tree, insn_format3rmi_method_returnVar.start, insn_format3rmi_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format3rmi_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format3rmi_method_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format3rmi_method_returnVar;
    }

    public static class insn_format3rms_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format3rms_method_return insn_format3rms_method() throws RecognitionException {
        insn_format3rms_method_return insn_format3rms_method_returnVar = new insn_format3rms_method_return();
        insn_format3rms_method_returnVar.start = this.input.LT(1);
        try {
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            Token token = (Token) match(this.input, 91, FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method6062);
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create(token));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format3rms_method6064)));
            pushFollow(FOLLOW_register_range_in_insn_format3rms_method6066);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            this.adaptor.addChild(commonTree, register_range_returnVarRegister_range.getTree());
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format3rms_method6068)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format3rms_method6070)));
            this.adaptor.addChild(commonTree, (CommonTree) this.adaptor.create((Token) match(this.input, 216, FOLLOW_VTABLE_INDEX_in_insn_format3rms_method6072)));
            throwOdexedInstructionException(this.input, token != null ? token.getText() : null);
            insn_format3rms_method_returnVar.stop = this.input.LT(-1);
            insn_format3rms_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format3rms_method_returnVar.tree, insn_format3rms_method_returnVar.start, insn_format3rms_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format3rms_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format3rms_method_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format3rms_method_returnVar;
    }

    public static class insn_format45cc_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format45cc_method_return insn_format45cc_method() throws RecognitionException {
        insn_format45cc_method_return insn_format45cc_method_returnVar = new insn_format45cc_method_return();
        insn_format45cc_method_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT45cc_METHOD");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_reference");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule register_list");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 92, FOLLOW_INSTRUCTION_FORMAT45cc_METHOD_in_insn_format45cc_method6093));
            rewriteRuleTokenStream3.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format45cc_method6095));
            pushFollow(FOLLOW_register_list_in_insn_format45cc_method6097);
            register_list_return register_list_returnVarRegister_list = register_list();
            this.state._fsp--;
            rewriteRuleSubtreeStream3.add(register_list_returnVarRegister_list.getTree());
            rewriteRuleTokenStream4.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format45cc_method6099));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format45cc_method6101));
            pushFollow(FOLLOW_method_reference_in_insn_format45cc_method6103);
            method_reference_return method_reference_returnVarMethod_reference = method_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree());
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format45cc_method6105));
            pushFollow(FOLLOW_method_prototype_in_insn_format45cc_method6107);
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(method_prototype_returnVarMethod_prototype.getTree());
            insn_format45cc_method_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format45cc_method_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(178, insn_format45cc_method_returnVar.start, "I_STATEMENT_FORMAT45cc_METHOD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format45cc_method_returnVar.tree = commonTree;
            insn_format45cc_method_returnVar.stop = this.input.LT(-1);
            insn_format45cc_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format45cc_method_returnVar.tree, insn_format45cc_method_returnVar.start, insn_format45cc_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format45cc_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format45cc_method_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format45cc_method_returnVar;
    }

    public static class insn_format4rcc_method_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format4rcc_method_return insn_format4rcc_method() throws RecognitionException {
        insn_format4rcc_method_return insn_format4rcc_method_returnVar = new insn_format4rcc_method_return();
        insn_format4rcc_method_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
        RewriteRuleTokenStream rewriteRuleTokenStream4 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT4rcc_METHOD");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule method_reference");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream3 = new RewriteRuleSubtreeStream(this.adaptor, "rule register_range");
        try {
            rewriteRuleTokenStream4.add((Token) match(this.input, 93, FOLLOW_INSTRUCTION_FORMAT4rcc_METHOD_in_insn_format4rcc_method6141));
            rewriteRuleTokenStream2.add((Token) match(this.input, 196, FOLLOW_OPEN_BRACE_in_insn_format4rcc_method6143));
            pushFollow(FOLLOW_register_range_in_insn_format4rcc_method6145);
            register_range_return register_range_returnVarRegister_range = register_range();
            this.state._fsp--;
            rewriteRuleSubtreeStream3.add(register_range_returnVarRegister_range.getTree());
            rewriteRuleTokenStream3.add((Token) match(this.input, 18, FOLLOW_CLOSE_BRACE_in_insn_format4rcc_method6147));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format4rcc_method6149));
            pushFollow(FOLLOW_method_reference_in_insn_format4rcc_method6151);
            method_reference_return method_reference_returnVarMethod_reference = method_reference();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(method_reference_returnVarMethod_reference.getTree());
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format4rcc_method6153));
            pushFollow(FOLLOW_method_prototype_in_insn_format4rcc_method6155);
            method_prototype_return method_prototype_returnVarMethod_prototype = method_prototype();
            this.state._fsp--;
            rewriteRuleSubtreeStream2.add(method_prototype_returnVarMethod_prototype.getTree());
            insn_format4rcc_method_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format4rcc_method_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(179, insn_format4rcc_method_returnVar.start, "I_STATEMENT_FORMAT4rcc_METHOD"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream4.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream3.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream2.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format4rcc_method_returnVar.tree = commonTree;
            insn_format4rcc_method_returnVar.stop = this.input.LT(-1);
            insn_format4rcc_method_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format4rcc_method_returnVar.tree, insn_format4rcc_method_returnVar.start, insn_format4rcc_method_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format4rcc_method_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format4rcc_method_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format4rcc_method_returnVar;
    }

    public static class insn_format51l_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_format51l_return insn_format51l() throws RecognitionException {
        insn_format51l_return insn_format51l_returnVar = new insn_format51l_return();
        insn_format51l_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT51l");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_literal");
        try {
            rewriteRuleTokenStream3.add((Token) match(this.input, 94, FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l6189));
            rewriteRuleTokenStream2.add((Token) match(this.input, 204, FOLLOW_REGISTER_in_insn_format51l6191));
            rewriteRuleTokenStream.add((Token) match(this.input, 21, FOLLOW_COMMA_in_insn_format51l6193));
            pushFollow(FOLLOW_fixed_literal_in_insn_format51l6195);
            fixed_literal_return fixed_literal_returnVarFixed_literal = fixed_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(fixed_literal_returnVarFixed_literal.getTree());
            insn_format51l_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_format51l_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(180, insn_format51l_returnVar.start, "I_STATEMENT_FORMAT51l"), commonTree2);
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream3.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleTokenStream2.nextNode());
            this.adaptor.addChild(commonTree3, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree, commonTree3);
            insn_format51l_returnVar.tree = commonTree;
            insn_format51l_returnVar.stop = this.input.LT(-1);
            insn_format51l_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_format51l_returnVar.tree, insn_format51l_returnVar.start, insn_format51l_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_format51l_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_format51l_returnVar.start, this.input.LT(-1), e);
        }
        return insn_format51l_returnVar;
    }

    public static class insn_array_data_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_array_data_directive_return insn_array_data_directive() throws RecognitionException {
        int i;
        insn_array_data_directive_return insn_array_data_directive_returnVar = new insn_array_data_directive_return();
        insn_array_data_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token END_ARRAY_DATA_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token ARRAY_DATA_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule parsed_integer_literal");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_literal");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 7, FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6222));
            pushFollow(FOLLOW_parsed_integer_literal_in_insn_array_data_directive6228);
            parsed_integer_literal_return parsed_integer_literal_returnVar = parsed_integer_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(parsed_integer_literal_returnVar.getTree());
            if (parsed_integer_literal_returnVar != null) {
                i = parsed_integer_literal_returnVar.value;
            } else {
                i = 0;
            }
            if (i != 4 && i != 8 && i != 1 && i != 2) {
                throw new SemanticException(this.input, insn_array_data_directive_returnVar.start, "Invalid element width: %d. Must be 1, 2, 4 or 8", Integer.valueOf(i));
            }
            while (true) {
                int iLA = this.input.LA(1);
                if ((iLA < 11 || iLA > 12) && iLA != 15 && ((iLA < 23 || iLA > 24) && ((iLA < 39 || iLA > 40) && iLA != 189 && iLA != 194 && iLA != 201 && iLA != 207))) {
                    break;
                }
                pushFollow(FOLLOW_fixed_literal_in_insn_array_data_directive6240);
                fixed_literal_return fixed_literal_returnVarFixed_literal = fixed_literal();
                this.state._fsp--;
                rewriteRuleSubtreeStream2.add(fixed_literal_returnVarFixed_literal.getTree());
            }
            rewriteRuleTokenStream.add((Token) match(this.input, 26, FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive6243));
            insn_array_data_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_array_data_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(143, insn_array_data_directive_returnVar.start, "I_STATEMENT_ARRAY_DATA"), commonTree2);
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor2 = this.adaptor;
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(103, "I_ARRAY_ELEMENT_SIZE"), commonTree4);
            this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, commonTree5);
            CommonTree commonTree6 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor3 = this.adaptor;
            CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(102, "I_ARRAY_ELEMENTS"), commonTree6);
            while (rewriteRuleSubtreeStream2.hasNext()) {
                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream2.nextTree());
            }
            rewriteRuleSubtreeStream2.reset();
            this.adaptor.addChild(commonTree3, commonTree7);
            this.adaptor.addChild(commonTree, commonTree3);
            insn_array_data_directive_returnVar.tree = commonTree;
            insn_array_data_directive_returnVar.stop = this.input.LT(-1);
            insn_array_data_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_array_data_directive_returnVar.tree, insn_array_data_directive_returnVar.start, insn_array_data_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_array_data_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_array_data_directive_returnVar.start, this.input.LT(-1), e);
        }
        return insn_array_data_directive_returnVar;
    }

    public static class insn_packed_switch_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_packed_switch_directive_return insn_packed_switch_directive() throws RecognitionException {
        insn_packed_switch_directive_return insn_packed_switch_directive_returnVar = new insn_packed_switch_directive_return();
        insn_packed_switch_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token END_PACKED_SWITCH_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token PACKED_SWITCH_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 198, FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6289));
            pushFollow(FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive6295);
            fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal();
            this.state._fsp--;
            rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree());
            while (this.input.LA(1) == 20) {
                pushFollow(FOLLOW_label_ref_in_insn_packed_switch_directive6301);
                label_ref_return label_ref_returnVarLabel_ref = label_ref();
                this.state._fsp--;
                rewriteRuleSubtreeStream2.add(label_ref_returnVarLabel_ref.getTree());
            }
            rewriteRuleTokenStream.add((Token) match(this.input, 30, FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive6308));
            insn_packed_switch_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_packed_switch_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(181, insn_packed_switch_directive_returnVar.start, "I_STATEMENT_PACKED_SWITCH"), commonTree2);
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor2 = this.adaptor;
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(132, insn_packed_switch_directive_returnVar.start, "I_PACKED_SWITCH_START_KEY"), commonTree4);
            this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree());
            this.adaptor.addChild(commonTree3, commonTree5);
            CommonTree commonTree6 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor3 = this.adaptor;
            CommonTree commonTree7 = (CommonTree) treeAdaptor3.becomeRoot((CommonTree) treeAdaptor3.create(131, insn_packed_switch_directive_returnVar.start, "I_PACKED_SWITCH_ELEMENTS"), commonTree6);
            while (rewriteRuleSubtreeStream2.hasNext()) {
                this.adaptor.addChild(commonTree7, rewriteRuleSubtreeStream2.nextTree());
            }
            rewriteRuleSubtreeStream2.reset();
            this.adaptor.addChild(commonTree3, commonTree7);
            this.adaptor.addChild(commonTree, commonTree3);
            insn_packed_switch_directive_returnVar.tree = commonTree;
            insn_packed_switch_directive_returnVar.stop = this.input.LT(-1);
            insn_packed_switch_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_packed_switch_directive_returnVar.tree, insn_packed_switch_directive_returnVar.start, insn_packed_switch_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_packed_switch_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_packed_switch_directive_returnVar.start, this.input.LT(-1), e);
        }
        return insn_packed_switch_directive_returnVar;
    }

    public static class insn_sparse_switch_directive_return extends ParserRuleReturnScope {
        CommonTree tree;

        @Override // org.antlr.runtime.ParserRuleReturnScope, org.antlr.runtime.RuleReturnScope
        public CommonTree getTree() {
            return this.tree;
        }
    }

    public final insn_sparse_switch_directive_return insn_sparse_switch_directive() throws RecognitionException {
        insn_sparse_switch_directive_return insn_sparse_switch_directive_returnVar = new insn_sparse_switch_directive_return();
        insn_sparse_switch_directive_returnVar.start = this.input.LT(1);
        RewriteRuleTokenStream rewriteRuleTokenStream = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
        RewriteRuleTokenStream rewriteRuleTokenStream2 = new RewriteRuleTokenStream(this.adaptor, "token SPARSE_SWITCH_DIRECTIVE");
        RewriteRuleTokenStream rewriteRuleTokenStream3 = new RewriteRuleTokenStream(this.adaptor, "token END_SPARSE_SWITCH_DIRECTIVE");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
        RewriteRuleSubtreeStream rewriteRuleSubtreeStream2 = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
        try {
            rewriteRuleTokenStream2.add((Token) match(this.input, 210, FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6382));
            while (true) {
                int iLA = this.input.LA(1);
                if ((iLA < 11 || iLA > 12) && iLA != 15 && ((iLA < 39 || iLA > 40) && iLA != 189 && iLA != 194 && iLA != 201 && iLA != 207)) {
                    break;
                }
                pushFollow(FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive6389);
                fixed_32bit_literal_return fixed_32bit_literal_returnVarFixed_32bit_literal = fixed_32bit_literal();
                this.state._fsp--;
                rewriteRuleSubtreeStream.add(fixed_32bit_literal_returnVarFixed_32bit_literal.getTree());
                rewriteRuleTokenStream.add((Token) match(this.input, 9, FOLLOW_ARROW_in_insn_sparse_switch_directive6391));
                pushFollow(FOLLOW_label_ref_in_insn_sparse_switch_directive6393);
                label_ref_return label_ref_returnVarLabel_ref = label_ref();
                this.state._fsp--;
                rewriteRuleSubtreeStream2.add(label_ref_returnVarLabel_ref.getTree());
            }
            rewriteRuleTokenStream3.add((Token) match(this.input, 32, FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive6401));
            insn_sparse_switch_directive_returnVar.tree = null;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", insn_sparse_switch_directive_returnVar.getTree());
            CommonTree commonTree = (CommonTree) this.adaptor.nil();
            CommonTree commonTree2 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor = this.adaptor;
            CommonTree commonTree3 = (CommonTree) treeAdaptor.becomeRoot((CommonTree) treeAdaptor.create(182, insn_sparse_switch_directive_returnVar.start, "I_STATEMENT_SPARSE_SWITCH"), commonTree2);
            CommonTree commonTree4 = (CommonTree) this.adaptor.nil();
            TreeAdaptor treeAdaptor2 = this.adaptor;
            CommonTree commonTree5 = (CommonTree) treeAdaptor2.becomeRoot((CommonTree) treeAdaptor2.create(142, insn_sparse_switch_directive_returnVar.start, "I_SPARSE_SWITCH_ELEMENTS"), commonTree4);
            while (true) {
                if (!rewriteRuleSubtreeStream.hasNext() && !rewriteRuleSubtreeStream2.hasNext()) {
                    break;
                }
                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream.nextTree());
                this.adaptor.addChild(commonTree5, rewriteRuleSubtreeStream2.nextTree());
            }
            rewriteRuleSubtreeStream.reset();
            rewriteRuleSubtreeStream2.reset();
            this.adaptor.addChild(commonTree3, commonTree5);
            this.adaptor.addChild(commonTree, commonTree3);
            insn_sparse_switch_directive_returnVar.tree = commonTree;
            insn_sparse_switch_directive_returnVar.stop = this.input.LT(-1);
            insn_sparse_switch_directive_returnVar.tree = (CommonTree) this.adaptor.rulePostProcessing(commonTree);
            this.adaptor.setTokenBoundaries(insn_sparse_switch_directive_returnVar.tree, insn_sparse_switch_directive_returnVar.start, insn_sparse_switch_directive_returnVar.stop);
        } catch (RecognitionException e) {
            reportError(e);
            recover(this.input, e);
            insn_sparse_switch_directive_returnVar.tree = (CommonTree) this.adaptor.errorNode(this.input, insn_sparse_switch_directive_returnVar.start, this.input.LT(-1), e);
        }
        return insn_sparse_switch_directive_returnVar;
    }

    protected class DFA31 extends DFA {
        public DFA31(BaseRecognizer baseRecognizer) {
            this.recognizer = baseRecognizer;
            this.decisionNumber = 31;
            this.eot = smaliParser.DFA31_eot;
            this.eof = smaliParser.DFA31_eof;
            this.min = smaliParser.DFA31_min;
            this.max = smaliParser.DFA31_max;
            this.accept = smaliParser.DFA31_accept;
            this.special = smaliParser.DFA31_special;
            this.transition = smaliParser.DFA31_transition;
        }

        @Override // org.antlr.runtime.DFA
        public String getDescription() {
            return "725:7: ( member_name COLON nonvoid_type_descriptor -> ^( I_ENCODED_FIELD ( reference_type_descriptor )? member_name nonvoid_type_descriptor ) | member_name method_prototype -> ^( I_ENCODED_METHOD ( reference_type_descriptor )? member_name method_prototype ) )";
        }
    }

    protected class DFA41 extends DFA {
        public DFA41(BaseRecognizer baseRecognizer) {
            this.recognizer = baseRecognizer;
            this.decisionNumber = 41;
            this.eot = smaliParser.DFA41_eot;
            this.eof = smaliParser.DFA41_eof;
            this.min = smaliParser.DFA41_min;
            this.max = smaliParser.DFA41_max;
            this.accept = smaliParser.DFA41_accept;
            this.special = smaliParser.DFA41_special;
            this.transition = smaliParser.DFA41_transition;
        }

        @Override // org.antlr.runtime.DFA
        public String getDescription() {
            return "767:1: verification_error_reference : ( CLASS_DESCRIPTOR | field_reference | method_reference );";
        }
    }

    protected class DFA43 extends DFA {
        public DFA43(BaseRecognizer baseRecognizer) {
            this.recognizer = baseRecognizer;
            this.decisionNumber = 43;
            this.eot = smaliParser.DFA43_eot;
            this.eof = smaliParser.DFA43_eof;
            this.min = smaliParser.DFA43_min;
            this.max = smaliParser.DFA43_max;
            this.accept = smaliParser.DFA43_accept;
            this.special = smaliParser.DFA43_special;
            this.transition = smaliParser.DFA43_transition;
        }

        @Override // org.antlr.runtime.DFA
        public String getDescription() {
            return "()* loopback of 785:5: ({...}? annotation )*";
        }

        @Override // org.antlr.runtime.DFA
        public int specialStateTransition(int i, IntStream intStream) throws NoViableAltException {
            TokenStream tokenStream = (TokenStream) intStream;
            if (i == 0) {
                tokenStream.LA(1);
                int iIndex = tokenStream.index();
                tokenStream.rewind();
                int i2 = tokenStream.LA(1) == 5 ? 71 : 1;
                tokenStream.seek(iIndex);
                if (i2 >= 0) {
                    return i2;
                }
            }
            NoViableAltException noViableAltException = new NoViableAltException(getDescription(), 43, i, tokenStream);
            error(noViableAltException);
            throw noViableAltException;
        }
    }
}
