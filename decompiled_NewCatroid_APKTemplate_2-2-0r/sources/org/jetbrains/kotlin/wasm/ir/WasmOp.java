package org.jetbrains.kotlin.wasm.ir;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.bytebuddy.implementation.MethodDelegation;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'I64_EQZ' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: Operators.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000)\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0003\bù\u0001\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B+\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\"\u00020\b¢\u0006\u0002\u0010\tB'\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\n¢\u0006\u0002\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bEj\u0002\bFj\u0002\bGj\u0002\bHj\u0002\bIj\u0002\bJj\u0002\bKj\u0002\bLj\u0002\bMj\u0002\bNj\u0002\bOj\u0002\bPj\u0002\bQj\u0002\bRj\u0002\bSj\u0002\bTj\u0002\bUj\u0002\bVj\u0002\bWj\u0002\bXj\u0002\bYj\u0002\bZj\u0002\b[j\u0002\b\\j\u0002\b]j\u0002\b^j\u0002\b_j\u0002\b`j\u0002\baj\u0002\bbj\u0002\bcj\u0002\bdj\u0002\bej\u0002\bfj\u0002\bgj\u0002\bhj\u0002\bij\u0002\bjj\u0002\bkj\u0002\blj\u0002\bmj\u0002\bnj\u0002\boj\u0002\bpj\u0002\bqj\u0002\brj\u0002\bsj\u0002\btj\u0002\buj\u0002\bvj\u0002\bwj\u0002\bxj\u0002\byj\u0002\bzj\u0002\b{j\u0002\b|j\u0002\b}j\u0002\b~j\u0002\b\u007fj\u0003\b\u0080\u0001j\u0003\b\u0081\u0001j\u0003\b\u0082\u0001j\u0003\b\u0083\u0001j\u0003\b\u0084\u0001j\u0003\b\u0085\u0001j\u0003\b\u0086\u0001j\u0003\b\u0087\u0001j\u0003\b\u0088\u0001j\u0003\b\u0089\u0001j\u0003\b\u008a\u0001j\u0003\b\u008b\u0001j\u0003\b\u008c\u0001j\u0003\b\u008d\u0001j\u0003\b\u008e\u0001j\u0003\b\u008f\u0001j\u0003\b\u0090\u0001j\u0003\b\u0091\u0001j\u0003\b\u0092\u0001j\u0003\b\u0093\u0001j\u0003\b\u0094\u0001j\u0003\b\u0095\u0001j\u0003\b\u0096\u0001j\u0003\b\u0097\u0001j\u0003\b\u0098\u0001j\u0003\b\u0099\u0001j\u0003\b\u009a\u0001j\u0003\b\u009b\u0001j\u0003\b\u009c\u0001j\u0003\b\u009d\u0001j\u0003\b\u009e\u0001j\u0003\b\u009f\u0001j\u0003\b \u0001j\u0003\b¡\u0001j\u0003\b¢\u0001j\u0003\b£\u0001j\u0003\b¤\u0001j\u0003\b¥\u0001j\u0003\b¦\u0001j\u0003\b§\u0001j\u0003\b¨\u0001j\u0003\b©\u0001j\u0003\bª\u0001j\u0003\b«\u0001j\u0003\b¬\u0001j\u0003\b\u00ad\u0001j\u0003\b®\u0001j\u0003\b¯\u0001j\u0003\b°\u0001j\u0003\b±\u0001j\u0003\b²\u0001j\u0003\b³\u0001j\u0003\b´\u0001j\u0003\bµ\u0001j\u0003\b¶\u0001j\u0003\b·\u0001j\u0003\b¸\u0001j\u0003\b¹\u0001j\u0003\bº\u0001j\u0003\b»\u0001j\u0003\b¼\u0001j\u0003\b½\u0001j\u0003\b¾\u0001j\u0003\b¿\u0001j\u0003\bÀ\u0001j\u0003\bÁ\u0001j\u0003\bÂ\u0001j\u0003\bÃ\u0001j\u0003\bÄ\u0001j\u0003\bÅ\u0001j\u0003\bÆ\u0001j\u0003\bÇ\u0001j\u0003\bÈ\u0001j\u0003\bÉ\u0001j\u0003\bÊ\u0001j\u0003\bË\u0001j\u0003\bÌ\u0001j\u0003\bÍ\u0001j\u0003\bÎ\u0001j\u0003\bÏ\u0001j\u0003\bÐ\u0001j\u0003\bÑ\u0001j\u0003\bÒ\u0001j\u0003\bÓ\u0001j\u0003\bÔ\u0001j\u0003\bÕ\u0001j\u0003\bÖ\u0001j\u0003\b×\u0001j\u0003\bØ\u0001j\u0003\bÙ\u0001j\u0003\bÚ\u0001j\u0003\bÛ\u0001j\u0003\bÜ\u0001j\u0003\bÝ\u0001j\u0003\bÞ\u0001j\u0003\bß\u0001j\u0003\bà\u0001j\u0003\bá\u0001j\u0003\bâ\u0001j\u0003\bã\u0001j\u0003\bä\u0001j\u0003\bå\u0001j\u0003\bæ\u0001j\u0003\bç\u0001j\u0003\bè\u0001j\u0003\bé\u0001j\u0003\bê\u0001j\u0003\bë\u0001j\u0003\bì\u0001j\u0003\bí\u0001j\u0003\bî\u0001j\u0003\bï\u0001j\u0003\bð\u0001j\u0003\bñ\u0001j\u0003\bò\u0001j\u0003\bó\u0001j\u0003\bô\u0001j\u0003\bõ\u0001j\u0003\bö\u0001j\u0003\b÷\u0001j\u0003\bø\u0001j\u0003\bù\u0001j\u0003\bú\u0001j\u0003\bû\u0001j\u0003\bü\u0001j\u0003\bý\u0001j\u0003\bþ\u0001j\u0003\bÿ\u0001j\u0003\b\u0080\u0002j\u0003\b\u0081\u0002j\u0003\b\u0082\u0002¨\u0006\u0083\u0002"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", "", "mnemonic", "", "opcode", "", "immediates", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediateKind;", "(Ljava/lang/String;ILjava/lang/String;I[Lorg/jetbrains/kotlin/wasm/ir/WasmImmediateKind;)V", "", "(Ljava/lang/String;ILjava/lang/String;ILjava/util/List;)V", "getImmediates", "()Ljava/util/List;", "getMnemonic", "()Ljava/lang/String;", "getOpcode", "()I", "I32_EQZ", "I64_EQZ", "I32_CLZ", "I32_CTZ", "I32_POPCNT", "I64_CLZ", "I64_CTZ", "I64_POPCNT", "F32_ABS", "F32_NEG", "F32_CEIL", "F32_FLOOR", "F32_TRUNC", "F32_NEAREST", "F32_SQRT", "F64_ABS", "F64_NEG", "F64_CEIL", "F64_FLOOR", "F64_TRUNC", "F64_NEAREST", "F64_SQRT", "I32_WRAP_I64", "I32_TRUNC_F32_S", "I32_TRUNC_F32_U", "I32_TRUNC_F64_S", "I32_TRUNC_F64_U", "I64_EXTEND_I32_S", "I64_EXTEND_I32_U", "I64_TRUNC_F32_S", "I64_TRUNC_F32_U", "I64_TRUNC_F64_S", "I64_TRUNC_F64_U", "F32_CONVERT_I32_S", "F32_CONVERT_I32_U", "F32_CONVERT_I64_S", "F32_CONVERT_I64_U", "F32_DEMOTE_F64", "F64_CONVERT_I32_S", "F64_CONVERT_I32_U", "F64_CONVERT_I64_S", "F64_CONVERT_I64_U", "F64_PROMOTE_F32", "I32_REINTERPRET_F32", "I64_REINTERPRET_F64", "F32_REINTERPRET_I32", "F64_REINTERPRET_I64", "I32_EXTEND8_S", "I32_EXTEND16_S", "I64_EXTEND8_S", "I64_EXTEND16_S", "I64_EXTEND32_S", "I32_TRUNC_SAT_F32_S", "I32_TRUNC_SAT_F32_U", "I32_TRUNC_SAT_F64_S", "I32_TRUNC_SAT_F64_U", "I64_TRUNC_SAT_F32_S", "I64_TRUNC_SAT_F32_U", "I64_TRUNC_SAT_F64_S", "I64_TRUNC_SAT_F64_U", "I32_EQ", "I32_NE", "I32_LT_S", "I32_LT_U", "I32_GT_S", "I32_GT_U", "I32_LE_S", "I32_LE_U", "I32_GE_S", "I32_GE_U", "I64_EQ", "I64_NE", "I64_LT_S", "I64_LT_U", "I64_GT_S", "I64_GT_U", "I64_LE_S", "I64_LE_U", "I64_GE_S", "I64_GE_U", "F32_EQ", "F32_NE", "F32_LT", "F32_GT", "F32_LE", "F32_GE", "F64_EQ", "F64_NE", "F64_LT", "F64_GT", "F64_LE", "F64_GE", "I32_ADD", "I32_SUB", "I32_MUL", "I32_DIV_S", "I32_DIV_U", "I32_REM_S", "I32_REM_U", "I32_AND", "I32_OR", "I32_XOR", "I32_SHL", "I32_SHR_S", "I32_SHR_U", "I32_ROTL", "I32_ROTR", "I64_ADD", "I64_SUB", "I64_MUL", "I64_DIV_S", "I64_DIV_U", "I64_REM_S", "I64_REM_U", "I64_AND", "I64_OR", "I64_XOR", "I64_SHL", "I64_SHR_S", "I64_SHR_U", "I64_ROTL", "I64_ROTR", "F32_ADD", "F32_SUB", "F32_MUL", "F32_DIV", "F32_MIN", "F32_MAX", "F32_COPYSIGN", "F64_ADD", "F64_SUB", "F64_MUL", "F64_DIV", "F64_MIN", "F64_MAX", "F64_COPYSIGN", "I32_CONST", "I64_CONST", "F32_CONST", "F64_CONST", "I32_LOAD", "I64_LOAD", "F32_LOAD", "F64_LOAD", "I32_LOAD8_S", "I32_LOAD8_U", "I32_LOAD16_S", "I32_LOAD16_U", "I64_LOAD8_S", "I64_LOAD8_U", "I64_LOAD16_S", "I64_LOAD16_U", "I64_LOAD32_S", "I64_LOAD32_U", "I32_STORE", "I64_STORE", "F32_STORE", "F64_STORE", "I32_STORE8", "I32_STORE16", "I64_STORE8", "I64_STORE16", "I64_STORE32", "MEMORY_SIZE", "MEMORY_GROW", "MEMORY_INIT", "DATA_DROP", "MEMORY_COPY", "MEMORY_FILL", "TABLE_GET", "TABLE_SET", "TABLE_GROW", "TABLE_SIZE", "TABLE_FILL", "TABLE_INIT", "ELEM_DROP", "TABLE_COPY", "UNREACHABLE", "NOP", "BLOCK", "LOOP", "IF", "ELSE", "END", "BR", "BR_IF", "BR_TABLE", "RETURN", "CALL", "CALL_INDIRECT", "TRY", "CATCH", "CATCH_ALL", "DELEGATE", "THROW", "RETHROW", "DROP", "SELECT", "SELECT_TYPED", "LOCAL_GET", "LOCAL_SET", "LOCAL_TEE", "GLOBAL_GET", "GLOBAL_SET", "REF_NULL", "REF_IS_NULL", "REF_FUNC", "CALL_REF", "RETURN_CALL_REF", "REF_AS_NOT_NULL", "BR_ON_NULL", "BR_ON_NON_NULL", "STRUCT_NEW", "STRUCT_NEW_DEFAULT", "STRUCT_GET", "STRUCT_GET_S", "STRUCT_GET_U", "STRUCT_SET", "ARRAY_NEW", "ARRAY_NEW_DEFAULT", "ARRAY_GET", "ARRAY_GET_S", "ARRAY_GET_U", "ARRAY_SET", "ARRAY_LEN", "ARRAY_COPY", "ARRAY_NEW_DATA", "ARRAY_NEW_FIXED", "I31_NEW", "I31_GET_S", "I31_GET_U", "REF_EQ", "REF_TEST", "REF_TEST_NULL", "REF_CAST", "REF_CAST_NULL", "EXTERN_INTERNALIZE", "EXTERN_EXTERNALIZE", "PSEUDO_COMMENT_PREVIOUS_INSTR", "PSEUDO_COMMENT_GROUP_START", "PSEUDO_COMMENT_GROUP_END", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmOp {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ WasmOp[] $VALUES;
    public static final WasmOp DROP;
    public static final WasmOp EXTERN_EXTERNALIZE;
    public static final WasmOp EXTERN_INTERNALIZE;
    public static final WasmOp F32_ABS;
    public static final WasmOp F32_ADD;
    public static final WasmOp F32_CEIL;
    public static final WasmOp F32_CONVERT_I32_S;
    public static final WasmOp F32_CONVERT_I32_U;
    public static final WasmOp F32_CONVERT_I64_S;
    public static final WasmOp F32_CONVERT_I64_U;
    public static final WasmOp F32_COPYSIGN;
    public static final WasmOp F32_DEMOTE_F64;
    public static final WasmOp F32_DIV;
    public static final WasmOp F32_EQ;
    public static final WasmOp F32_FLOOR;
    public static final WasmOp F32_GE;
    public static final WasmOp F32_GT;
    public static final WasmOp F32_LE;
    public static final WasmOp F32_LT;
    public static final WasmOp F32_MAX;
    public static final WasmOp F32_MIN;
    public static final WasmOp F32_MUL;
    public static final WasmOp F32_NE;
    public static final WasmOp F32_NEAREST;
    public static final WasmOp F32_NEG;
    public static final WasmOp F32_REINTERPRET_I32;
    public static final WasmOp F32_SQRT;
    public static final WasmOp F32_SUB;
    public static final WasmOp F32_TRUNC;
    public static final WasmOp F64_ABS;
    public static final WasmOp F64_ADD;
    public static final WasmOp F64_CEIL;
    public static final WasmOp F64_CONVERT_I32_S;
    public static final WasmOp F64_CONVERT_I32_U;
    public static final WasmOp F64_CONVERT_I64_S;
    public static final WasmOp F64_CONVERT_I64_U;
    public static final WasmOp F64_COPYSIGN;
    public static final WasmOp F64_DIV;
    public static final WasmOp F64_EQ;
    public static final WasmOp F64_FLOOR;
    public static final WasmOp F64_GE;
    public static final WasmOp F64_GT;
    public static final WasmOp F64_LE;
    public static final WasmOp F64_LT;
    public static final WasmOp F64_MAX;
    public static final WasmOp F64_MIN;
    public static final WasmOp F64_MUL;
    public static final WasmOp F64_NE;
    public static final WasmOp F64_NEAREST;
    public static final WasmOp F64_NEG;
    public static final WasmOp F64_PROMOTE_F32;
    public static final WasmOp F64_REINTERPRET_I64;
    public static final WasmOp F64_SQRT;
    public static final WasmOp F64_SUB;
    public static final WasmOp F64_TRUNC;
    public static final WasmOp I31_GET_S;
    public static final WasmOp I31_GET_U;
    public static final WasmOp I32_ADD;
    public static final WasmOp I32_AND;
    public static final WasmOp I32_CLZ;
    public static final WasmOp I32_CTZ;
    public static final WasmOp I32_DIV_S;
    public static final WasmOp I32_DIV_U;
    public static final WasmOp I32_EQ;
    public static final WasmOp I32_EXTEND16_S;
    public static final WasmOp I32_EXTEND8_S;
    public static final WasmOp I32_GE_S;
    public static final WasmOp I32_GE_U;
    public static final WasmOp I32_GT_S;
    public static final WasmOp I32_GT_U;
    public static final WasmOp I32_LE_S;
    public static final WasmOp I32_LE_U;
    public static final WasmOp I32_LT_S;
    public static final WasmOp I32_LT_U;
    public static final WasmOp I32_MUL;
    public static final WasmOp I32_NE;
    public static final WasmOp I32_OR;
    public static final WasmOp I32_POPCNT;
    public static final WasmOp I32_REINTERPRET_F32;
    public static final WasmOp I32_REM_S;
    public static final WasmOp I32_REM_U;
    public static final WasmOp I32_ROTL;
    public static final WasmOp I32_ROTR;
    public static final WasmOp I32_SHL;
    public static final WasmOp I32_SHR_S;
    public static final WasmOp I32_SHR_U;
    public static final WasmOp I32_SUB;
    public static final WasmOp I32_TRUNC_F32_S;
    public static final WasmOp I32_TRUNC_F32_U;
    public static final WasmOp I32_TRUNC_F64_S;
    public static final WasmOp I32_TRUNC_F64_U;
    public static final WasmOp I32_TRUNC_SAT_F32_S;
    public static final WasmOp I32_TRUNC_SAT_F32_U;
    public static final WasmOp I32_TRUNC_SAT_F64_S;
    public static final WasmOp I32_TRUNC_SAT_F64_U;
    public static final WasmOp I32_WRAP_I64;
    public static final WasmOp I32_XOR;
    public static final WasmOp I64_ADD;
    public static final WasmOp I64_AND;
    public static final WasmOp I64_CLZ;
    public static final WasmOp I64_CTZ;
    public static final WasmOp I64_DIV_S;
    public static final WasmOp I64_DIV_U;
    public static final WasmOp I64_EQ;
    public static final WasmOp I64_EQZ;
    public static final WasmOp I64_EXTEND16_S;
    public static final WasmOp I64_EXTEND32_S;
    public static final WasmOp I64_EXTEND8_S;
    public static final WasmOp I64_EXTEND_I32_S;
    public static final WasmOp I64_EXTEND_I32_U;
    public static final WasmOp I64_GE_S;
    public static final WasmOp I64_GE_U;
    public static final WasmOp I64_GT_S;
    public static final WasmOp I64_GT_U;
    public static final WasmOp I64_LE_S;
    public static final WasmOp I64_LE_U;
    public static final WasmOp I64_LT_S;
    public static final WasmOp I64_LT_U;
    public static final WasmOp I64_MUL;
    public static final WasmOp I64_NE;
    public static final WasmOp I64_OR;
    public static final WasmOp I64_POPCNT;
    public static final WasmOp I64_REINTERPRET_F64;
    public static final WasmOp I64_REM_S;
    public static final WasmOp I64_REM_U;
    public static final WasmOp I64_ROTL;
    public static final WasmOp I64_ROTR;
    public static final WasmOp I64_SHL;
    public static final WasmOp I64_SHR_S;
    public static final WasmOp I64_SHR_U;
    public static final WasmOp I64_SUB;
    public static final WasmOp I64_TRUNC_F32_S;
    public static final WasmOp I64_TRUNC_F32_U;
    public static final WasmOp I64_TRUNC_F64_S;
    public static final WasmOp I64_TRUNC_F64_U;
    public static final WasmOp I64_TRUNC_SAT_F32_S;
    public static final WasmOp I64_TRUNC_SAT_F32_U;
    public static final WasmOp I64_TRUNC_SAT_F64_S;
    public static final WasmOp I64_TRUNC_SAT_F64_U;
    public static final WasmOp I64_XOR;
    public static final WasmOp PSEUDO_COMMENT_GROUP_END;
    public static final WasmOp PSEUDO_COMMENT_GROUP_START;
    public static final WasmOp PSEUDO_COMMENT_PREVIOUS_INSTR;
    public static final WasmOp REF_AS_NOT_NULL;
    public static final WasmOp REF_IS_NULL;
    public static final WasmOp SELECT;
    private final List<WasmImmediateKind> immediates;
    private final String mnemonic;
    private final int opcode;
    public static final WasmOp I32_EQZ = new WasmOp("I32_EQZ", 0, "i32.eqz", 69, null, 4, null);
    public static final WasmOp I32_CONST = new WasmOp("I32_CONST", 136, "i32.const", 65, WasmImmediateKind.CONST_I32);
    public static final WasmOp I64_CONST = new WasmOp("I64_CONST", 137, "i64.const", 66, WasmImmediateKind.CONST_I64);
    public static final WasmOp F32_CONST = new WasmOp("F32_CONST", 138, "f32.const", 67, WasmImmediateKind.CONST_F32);
    public static final WasmOp F64_CONST = new WasmOp("F64_CONST", 139, "f64.const", 68, WasmImmediateKind.CONST_F64);
    public static final WasmOp I32_LOAD = new WasmOp("I32_LOAD", 140, "i32.load", 40, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_LOAD = new WasmOp("I64_LOAD", 141, "i64.load", 41, WasmImmediateKind.MEM_ARG);
    public static final WasmOp F32_LOAD = new WasmOp("F32_LOAD", 142, "f32.load", 42, WasmImmediateKind.MEM_ARG);
    public static final WasmOp F64_LOAD = new WasmOp("F64_LOAD", 143, "f64.load", 43, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I32_LOAD8_S = new WasmOp("I32_LOAD8_S", 144, "i32.load8_s", 44, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I32_LOAD8_U = new WasmOp("I32_LOAD8_U", 145, "i32.load8_u", 45, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I32_LOAD16_S = new WasmOp("I32_LOAD16_S", 146, "i32.load16_s", 46, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I32_LOAD16_U = new WasmOp("I32_LOAD16_U", 147, "i32.load16_u", 47, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_LOAD8_S = new WasmOp("I64_LOAD8_S", 148, "i64.load8_s", 48, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_LOAD8_U = new WasmOp("I64_LOAD8_U", 149, "i64.load8_u", 49, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_LOAD16_S = new WasmOp("I64_LOAD16_S", 150, "i64.load16_s", 50, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_LOAD16_U = new WasmOp("I64_LOAD16_U", 151, "i64.load16_u", 51, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_LOAD32_S = new WasmOp("I64_LOAD32_S", 152, "i64.load32_s", 52, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_LOAD32_U = new WasmOp("I64_LOAD32_U", 153, "i64.load32_u", 53, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I32_STORE = new WasmOp("I32_STORE", 154, "i32.store", 54, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_STORE = new WasmOp("I64_STORE", 155, "i64.store", 55, WasmImmediateKind.MEM_ARG);
    public static final WasmOp F32_STORE = new WasmOp("F32_STORE", 156, "f32.store", 56, WasmImmediateKind.MEM_ARG);
    public static final WasmOp F64_STORE = new WasmOp("F64_STORE", 157, "f64.store", 57, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I32_STORE8 = new WasmOp("I32_STORE8", 158, "i32.store8", 58, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I32_STORE16 = new WasmOp("I32_STORE16", 159, "i32.store16", 59, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_STORE8 = new WasmOp("I64_STORE8", 160, "i64.store8", 60, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_STORE16 = new WasmOp("I64_STORE16", 161, "i64.store16", 61, WasmImmediateKind.MEM_ARG);
    public static final WasmOp I64_STORE32 = new WasmOp("I64_STORE32", 162, "i64.store32", 62, WasmImmediateKind.MEM_ARG);
    public static final WasmOp MEMORY_SIZE = new WasmOp("MEMORY_SIZE", 163, "memory.size", 63, WasmImmediateKind.MEMORY_IDX);
    public static final WasmOp MEMORY_GROW = new WasmOp("MEMORY_GROW", 164, "memory.grow", 64, WasmImmediateKind.MEMORY_IDX);
    public static final WasmOp MEMORY_INIT = new WasmOp("MEMORY_INIT", 165, "memory.init", 64520, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.DATA_IDX, WasmImmediateKind.MEMORY_IDX}));
    public static final WasmOp DATA_DROP = new WasmOp("DATA_DROP", 166, "data.drop", 64521, WasmImmediateKind.DATA_IDX);
    public static final WasmOp MEMORY_COPY = new WasmOp("MEMORY_COPY", 167, "memory.copy", 64522, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.MEMORY_IDX, WasmImmediateKind.MEMORY_IDX}));
    public static final WasmOp MEMORY_FILL = new WasmOp("MEMORY_FILL", 168, "memory.fill", 64523, WasmImmediateKind.MEMORY_IDX);
    public static final WasmOp TABLE_GET = new WasmOp("TABLE_GET", 169, "table.get", 37, WasmImmediateKind.TABLE_IDX);
    public static final WasmOp TABLE_SET = new WasmOp("TABLE_SET", 170, "table.set", 38, WasmImmediateKind.TABLE_IDX);
    public static final WasmOp TABLE_GROW = new WasmOp("TABLE_GROW", 171, "table.grow", 64527, WasmImmediateKind.TABLE_IDX);
    public static final WasmOp TABLE_SIZE = new WasmOp("TABLE_SIZE", 172, "table.size", 64528, WasmImmediateKind.TABLE_IDX);
    public static final WasmOp TABLE_FILL = new WasmOp("TABLE_FILL", 173, "table.fill", 64529, WasmImmediateKind.TABLE_IDX);
    public static final WasmOp TABLE_INIT = new WasmOp("TABLE_INIT", 174, "table.init", 64524, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.ELEM_IDX, WasmImmediateKind.TABLE_IDX}));
    public static final WasmOp ELEM_DROP = new WasmOp("ELEM_DROP", 175, "elem.drop", 64525, WasmImmediateKind.ELEM_IDX);
    public static final WasmOp TABLE_COPY = new WasmOp("TABLE_COPY", 176, "table.copy", 64526, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.TABLE_IDX, WasmImmediateKind.TABLE_IDX}));
    public static final WasmOp UNREACHABLE = new WasmOp("UNREACHABLE", 177, "unreachable", 0, null, 4, null);
    public static final WasmOp NOP = new WasmOp("NOP", 178, "nop", 1, null, 4, null);
    public static final WasmOp BLOCK = new WasmOp("BLOCK", 179, "block", 2, WasmImmediateKind.BLOCK_TYPE);
    public static final WasmOp LOOP = new WasmOp("LOOP", 180, "loop", 3, WasmImmediateKind.BLOCK_TYPE);
    public static final WasmOp IF = new WasmOp("IF", 181, PsiKeyword.IF, 4, WasmImmediateKind.BLOCK_TYPE);
    public static final WasmOp ELSE = new WasmOp("ELSE", 182, PsiKeyword.ELSE, 5, null, 4, null);
    public static final WasmOp END = new WasmOp("END", 183, "end", 11, null, 4, null);
    public static final WasmOp BR = new WasmOp("BR", 184, CompressorStreamFactory.BROTLI, 12, WasmImmediateKind.LABEL_IDX);
    public static final WasmOp BR_IF = new WasmOp("BR_IF", 185, "br_if", 13, WasmImmediateKind.LABEL_IDX);
    public static final WasmOp BR_TABLE = new WasmOp("BR_TABLE", 186, "br_table", 14, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.LABEL_IDX_VECTOR, WasmImmediateKind.LABEL_IDX}));
    public static final WasmOp RETURN = new WasmOp("RETURN", 187, "return", 15, null, 4, 0 == true ? 1 : 0);
    public static final WasmOp CALL = new WasmOp("CALL", 188, "call", 16, WasmImmediateKind.FUNC_IDX);
    public static final WasmOp CALL_INDIRECT = new WasmOp("CALL_INDIRECT", 189, "call_indirect", 17, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.TYPE_IDX, WasmImmediateKind.TABLE_IDX}));
    public static final WasmOp TRY = new WasmOp("TRY", 190, PsiKeyword.TRY, 6, WasmImmediateKind.BLOCK_TYPE);
    public static final WasmOp CATCH = new WasmOp("CATCH", 191, PsiKeyword.CATCH, 7, WasmImmediateKind.TAG_IDX);
    public static final WasmOp CATCH_ALL = new WasmOp("CATCH_ALL", 192, "catch_all", 25, null, 4, null);
    public static final WasmOp DELEGATE = new WasmOp("DELEGATE", 193, MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, 24, WasmImmediateKind.LABEL_IDX);
    public static final WasmOp THROW = new WasmOp("THROW", 194, PsiKeyword.THROW, 8, WasmImmediateKind.TAG_IDX);
    public static final WasmOp RETHROW = new WasmOp("RETHROW", 195, "rethrow", 9, WasmImmediateKind.LABEL_IDX);
    public static final WasmOp SELECT_TYPED = new WasmOp("SELECT_TYPED", 198, "select", 28, WasmImmediateKind.VAL_TYPE_VECTOR);
    public static final WasmOp LOCAL_GET = new WasmOp("LOCAL_GET", 199, "local.get", 32, WasmImmediateKind.LOCAL_IDX);
    public static final WasmOp LOCAL_SET = new WasmOp("LOCAL_SET", 200, "local.set", 33, WasmImmediateKind.LOCAL_IDX);
    public static final WasmOp LOCAL_TEE = new WasmOp("LOCAL_TEE", 201, "local.tee", 34, WasmImmediateKind.LOCAL_IDX);
    public static final WasmOp GLOBAL_GET = new WasmOp("GLOBAL_GET", 202, "global.get", 35, WasmImmediateKind.GLOBAL_IDX);
    public static final WasmOp GLOBAL_SET = new WasmOp("GLOBAL_SET", 203, "global.set", 36, WasmImmediateKind.GLOBAL_IDX);
    public static final WasmOp REF_NULL = new WasmOp("REF_NULL", 204, "ref.null", 208, WasmImmediateKind.HEAP_TYPE);
    public static final WasmOp REF_FUNC = new WasmOp("REF_FUNC", 206, "ref.func", 210, WasmImmediateKind.FUNC_IDX);
    public static final WasmOp CALL_REF = new WasmOp("CALL_REF", 207, "call_ref", 20, WasmImmediateKind.TYPE_IDX);
    public static final WasmOp RETURN_CALL_REF = new WasmOp("RETURN_CALL_REF", 208, "return_call_ref", 21, WasmImmediateKind.TYPE_IDX);
    public static final WasmOp BR_ON_NULL = new WasmOp("BR_ON_NULL", 210, "br_on_null", 213, WasmImmediateKind.LABEL_IDX);
    public static final WasmOp BR_ON_NON_NULL = new WasmOp("BR_ON_NON_NULL", 211, "br_on_non_null", 214, WasmImmediateKind.LABEL_IDX);
    public static final WasmOp STRUCT_NEW = new WasmOp("STRUCT_NEW", 212, "struct.new", 64256, WasmImmediateKind.STRUCT_TYPE_IDX);
    public static final WasmOp STRUCT_NEW_DEFAULT = new WasmOp("STRUCT_NEW_DEFAULT", 213, "struct.new_default", 64257, WasmImmediateKind.STRUCT_TYPE_IDX);
    public static final WasmOp STRUCT_GET = new WasmOp("STRUCT_GET", 214, "struct.get", 64258, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.STRUCT_TYPE_IDX, WasmImmediateKind.STRUCT_FIELD_IDX}));
    public static final WasmOp STRUCT_GET_S = new WasmOp("STRUCT_GET_S", 215, "struct.get_s", 64259, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.STRUCT_TYPE_IDX, WasmImmediateKind.STRUCT_FIELD_IDX}));
    public static final WasmOp STRUCT_GET_U = new WasmOp("STRUCT_GET_U", 216, "struct.get_u", 64260, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.STRUCT_TYPE_IDX, WasmImmediateKind.STRUCT_FIELD_IDX}));
    public static final WasmOp STRUCT_SET = new WasmOp("STRUCT_SET", 217, "struct.set", 64261, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.STRUCT_TYPE_IDX, WasmImmediateKind.STRUCT_FIELD_IDX}));
    public static final WasmOp ARRAY_NEW = new WasmOp("ARRAY_NEW", 218, "array.new", 64262, WasmImmediateKind.STRUCT_TYPE_IDX);
    public static final WasmOp ARRAY_NEW_DEFAULT = new WasmOp("ARRAY_NEW_DEFAULT", 219, "array.new_default", 64263, WasmImmediateKind.STRUCT_TYPE_IDX);
    public static final WasmOp ARRAY_GET = new WasmOp("ARRAY_GET", 220, "array.get", 64267, CollectionsKt.listOf(WasmImmediateKind.STRUCT_TYPE_IDX));
    public static final WasmOp ARRAY_GET_S = new WasmOp("ARRAY_GET_S", 221, "array.get_s", 64268, CollectionsKt.listOf(WasmImmediateKind.STRUCT_TYPE_IDX));
    public static final WasmOp ARRAY_GET_U = new WasmOp("ARRAY_GET_U", 222, "array.get_u", 64269, CollectionsKt.listOf(WasmImmediateKind.STRUCT_TYPE_IDX));
    public static final WasmOp ARRAY_SET = new WasmOp("ARRAY_SET", 223, "array.set", 64270, CollectionsKt.listOf(WasmImmediateKind.STRUCT_TYPE_IDX));
    public static final WasmOp ARRAY_LEN = new WasmOp("ARRAY_LEN", 224, "array.len", 64271, null, 4, 0 == true ? 1 : 0);
    public static final WasmOp ARRAY_COPY = new WasmOp("ARRAY_COPY", 225, "array.copy", 64273, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.STRUCT_TYPE_IDX, WasmImmediateKind.STRUCT_TYPE_IDX}));
    public static final WasmOp ARRAY_NEW_DATA = new WasmOp("ARRAY_NEW_DATA", 226, "array.new_data", 64265, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.STRUCT_TYPE_IDX, WasmImmediateKind.DATA_IDX}));
    public static final WasmOp ARRAY_NEW_FIXED = new WasmOp("ARRAY_NEW_FIXED", XKeySym.XK_atilde, "array.new_fixed", 64264, CollectionsKt.listOf((Object[]) new WasmImmediateKind[]{WasmImmediateKind.STRUCT_TYPE_IDX, WasmImmediateKind.CONST_I32}));
    public static final WasmOp I31_NEW = new WasmOp("I31_NEW", XKeySym.XK_adiaeresis, "i31.new", 64284, null, 4, null);
    public static final WasmOp REF_EQ = new WasmOp("REF_EQ", XKeySym.XK_ccedilla, "ref.eq", 211, 0 == true ? 1 : 0, 4, null);
    public static final WasmOp REF_TEST = new WasmOp("REF_TEST", XKeySym.XK_egrave, "ref.test", 64276, WasmImmediateKind.HEAP_TYPE);
    public static final WasmOp REF_TEST_NULL = new WasmOp("REF_TEST_NULL", XKeySym.XK_eacute, "ref.test null", 64277, WasmImmediateKind.HEAP_TYPE);
    public static final WasmOp REF_CAST = new WasmOp("REF_CAST", XKeySym.XK_ecircumflex, "ref.cast", 64278, WasmImmediateKind.HEAP_TYPE);
    public static final WasmOp REF_CAST_NULL = new WasmOp("REF_CAST_NULL", XKeySym.XK_ediaeresis, "ref.cast null", 64279, WasmImmediateKind.HEAP_TYPE);

    private static final /* synthetic */ WasmOp[] $values() {
        return new WasmOp[]{I32_EQZ, I64_EQZ, I32_CLZ, I32_CTZ, I32_POPCNT, I64_CLZ, I64_CTZ, I64_POPCNT, F32_ABS, F32_NEG, F32_CEIL, F32_FLOOR, F32_TRUNC, F32_NEAREST, F32_SQRT, F64_ABS, F64_NEG, F64_CEIL, F64_FLOOR, F64_TRUNC, F64_NEAREST, F64_SQRT, I32_WRAP_I64, I32_TRUNC_F32_S, I32_TRUNC_F32_U, I32_TRUNC_F64_S, I32_TRUNC_F64_U, I64_EXTEND_I32_S, I64_EXTEND_I32_U, I64_TRUNC_F32_S, I64_TRUNC_F32_U, I64_TRUNC_F64_S, I64_TRUNC_F64_U, F32_CONVERT_I32_S, F32_CONVERT_I32_U, F32_CONVERT_I64_S, F32_CONVERT_I64_U, F32_DEMOTE_F64, F64_CONVERT_I32_S, F64_CONVERT_I32_U, F64_CONVERT_I64_S, F64_CONVERT_I64_U, F64_PROMOTE_F32, I32_REINTERPRET_F32, I64_REINTERPRET_F64, F32_REINTERPRET_I32, F64_REINTERPRET_I64, I32_EXTEND8_S, I32_EXTEND16_S, I64_EXTEND8_S, I64_EXTEND16_S, I64_EXTEND32_S, I32_TRUNC_SAT_F32_S, I32_TRUNC_SAT_F32_U, I32_TRUNC_SAT_F64_S, I32_TRUNC_SAT_F64_U, I64_TRUNC_SAT_F32_S, I64_TRUNC_SAT_F32_U, I64_TRUNC_SAT_F64_S, I64_TRUNC_SAT_F64_U, I32_EQ, I32_NE, I32_LT_S, I32_LT_U, I32_GT_S, I32_GT_U, I32_LE_S, I32_LE_U, I32_GE_S, I32_GE_U, I64_EQ, I64_NE, I64_LT_S, I64_LT_U, I64_GT_S, I64_GT_U, I64_LE_S, I64_LE_U, I64_GE_S, I64_GE_U, F32_EQ, F32_NE, F32_LT, F32_GT, F32_LE, F32_GE, F64_EQ, F64_NE, F64_LT, F64_GT, F64_LE, F64_GE, I32_ADD, I32_SUB, I32_MUL, I32_DIV_S, I32_DIV_U, I32_REM_S, I32_REM_U, I32_AND, I32_OR, I32_XOR, I32_SHL, I32_SHR_S, I32_SHR_U, I32_ROTL, I32_ROTR, I64_ADD, I64_SUB, I64_MUL, I64_DIV_S, I64_DIV_U, I64_REM_S, I64_REM_U, I64_AND, I64_OR, I64_XOR, I64_SHL, I64_SHR_S, I64_SHR_U, I64_ROTL, I64_ROTR, F32_ADD, F32_SUB, F32_MUL, F32_DIV, F32_MIN, F32_MAX, F32_COPYSIGN, F64_ADD, F64_SUB, F64_MUL, F64_DIV, F64_MIN, F64_MAX, F64_COPYSIGN, I32_CONST, I64_CONST, F32_CONST, F64_CONST, I32_LOAD, I64_LOAD, F32_LOAD, F64_LOAD, I32_LOAD8_S, I32_LOAD8_U, I32_LOAD16_S, I32_LOAD16_U, I64_LOAD8_S, I64_LOAD8_U, I64_LOAD16_S, I64_LOAD16_U, I64_LOAD32_S, I64_LOAD32_U, I32_STORE, I64_STORE, F32_STORE, F64_STORE, I32_STORE8, I32_STORE16, I64_STORE8, I64_STORE16, I64_STORE32, MEMORY_SIZE, MEMORY_GROW, MEMORY_INIT, DATA_DROP, MEMORY_COPY, MEMORY_FILL, TABLE_GET, TABLE_SET, TABLE_GROW, TABLE_SIZE, TABLE_FILL, TABLE_INIT, ELEM_DROP, TABLE_COPY, UNREACHABLE, NOP, BLOCK, LOOP, IF, ELSE, END, BR, BR_IF, BR_TABLE, RETURN, CALL, CALL_INDIRECT, TRY, CATCH, CATCH_ALL, DELEGATE, THROW, RETHROW, DROP, SELECT, SELECT_TYPED, LOCAL_GET, LOCAL_SET, LOCAL_TEE, GLOBAL_GET, GLOBAL_SET, REF_NULL, REF_IS_NULL, REF_FUNC, CALL_REF, RETURN_CALL_REF, REF_AS_NOT_NULL, BR_ON_NULL, BR_ON_NON_NULL, STRUCT_NEW, STRUCT_NEW_DEFAULT, STRUCT_GET, STRUCT_GET_S, STRUCT_GET_U, STRUCT_SET, ARRAY_NEW, ARRAY_NEW_DEFAULT, ARRAY_GET, ARRAY_GET_S, ARRAY_GET_U, ARRAY_SET, ARRAY_LEN, ARRAY_COPY, ARRAY_NEW_DATA, ARRAY_NEW_FIXED, I31_NEW, I31_GET_S, I31_GET_U, REF_EQ, REF_TEST, REF_TEST_NULL, REF_CAST, REF_CAST_NULL, EXTERN_INTERNALIZE, EXTERN_EXTERNALIZE, PSEUDO_COMMENT_PREVIOUS_INSTR, PSEUDO_COMMENT_GROUP_START, PSEUDO_COMMENT_GROUP_END};
    }

    public static EnumEntries<WasmOp> getEntries() {
        return $ENTRIES;
    }

    public static WasmOp valueOf(String str) {
        return (WasmOp) Enum.valueOf(WasmOp.class, str);
    }

    public static WasmOp[] values() {
        return (WasmOp[]) $VALUES.clone();
    }

    private WasmOp(String str, int i, String str2, int i2, List list) {
        super(str, i);
        this.mnemonic = str2;
        this.opcode = i2;
        this.immediates = list;
    }

    public final String getMnemonic() {
        return this.mnemonic;
    }

    public final int getOpcode() {
        return this.opcode;
    }

    /* synthetic */ WasmOp(String str, int i, String str2, int i2, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, str2, i2, (i3 & 4) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final List<WasmImmediateKind> getImmediates() {
        return this.immediates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        int i = 4;
        DefaultConstructorMarker defaultConstructorMarker = null;
        List list = null;
        I64_EQZ = new WasmOp("I64_EQZ", 1, "i64.eqz", 80, list, i, defaultConstructorMarker);
        int i2 = 4;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        List list2 = null;
        I32_CLZ = new WasmOp("I32_CLZ", 2, "i32.clz", 103, list2, i2, defaultConstructorMarker2);
        I32_CTZ = new WasmOp("I32_CTZ", 3, "i32.ctz", 104, list, i, defaultConstructorMarker);
        I32_POPCNT = new WasmOp("I32_POPCNT", 4, "i32.popcnt", 105, list2, i2, defaultConstructorMarker2);
        I64_CLZ = new WasmOp("I64_CLZ", 5, "i64.clz", 121, list, i, defaultConstructorMarker);
        I64_CTZ = new WasmOp("I64_CTZ", 6, "i64.ctz", 122, list2, i2, defaultConstructorMarker2);
        I64_POPCNT = new WasmOp("I64_POPCNT", 7, "i64.popcnt", 123, list, i, defaultConstructorMarker);
        F32_ABS = new WasmOp("F32_ABS", 8, "f32.abs", 139, list2, i2, defaultConstructorMarker2);
        F32_NEG = new WasmOp("F32_NEG", 9, "f32.neg", 140, list, i, defaultConstructorMarker);
        F32_CEIL = new WasmOp("F32_CEIL", 10, "f32.ceil", 141, list2, i2, defaultConstructorMarker2);
        F32_FLOOR = new WasmOp("F32_FLOOR", 11, "f32.floor", 142, list, i, defaultConstructorMarker);
        F32_TRUNC = new WasmOp("F32_TRUNC", 12, "f32.trunc", 143, list2, i2, defaultConstructorMarker2);
        F32_NEAREST = new WasmOp("F32_NEAREST", 13, "f32.nearest", 144, list, i, defaultConstructorMarker);
        F32_SQRT = new WasmOp("F32_SQRT", 14, "f32.sqrt", 145, list2, i2, defaultConstructorMarker2);
        F64_ABS = new WasmOp("F64_ABS", 15, "f64.abs", 153, list, i, defaultConstructorMarker);
        F64_NEG = new WasmOp("F64_NEG", 16, "f64.neg", 154, list2, i2, defaultConstructorMarker2);
        F64_CEIL = new WasmOp("F64_CEIL", 17, "f64.ceil", 155, list, i, defaultConstructorMarker);
        F64_FLOOR = new WasmOp("F64_FLOOR", 18, "f64.floor", 156, list2, i2, defaultConstructorMarker2);
        F64_TRUNC = new WasmOp("F64_TRUNC", 19, "f64.trunc", 157, list, i, defaultConstructorMarker);
        F64_NEAREST = new WasmOp("F64_NEAREST", 20, "f64.nearest", 158, list2, i2, defaultConstructorMarker2);
        F64_SQRT = new WasmOp("F64_SQRT", 21, "f64.sqrt", 159, list, i, defaultConstructorMarker);
        I32_WRAP_I64 = new WasmOp("I32_WRAP_I64", 22, "i32.wrap_i64", 167, list2, i2, defaultConstructorMarker2);
        I32_TRUNC_F32_S = new WasmOp("I32_TRUNC_F32_S", 23, "i32.trunc_f32_s", 168, list, i, defaultConstructorMarker);
        I32_TRUNC_F32_U = new WasmOp("I32_TRUNC_F32_U", 24, "i32.trunc_f32_u", 169, list2, i2, defaultConstructorMarker2);
        I32_TRUNC_F64_S = new WasmOp("I32_TRUNC_F64_S", 25, "i32.trunc_f64_s", 170, list, i, defaultConstructorMarker);
        I32_TRUNC_F64_U = new WasmOp("I32_TRUNC_F64_U", 26, "i32.trunc_f64_u", 171, list2, i2, defaultConstructorMarker2);
        I64_EXTEND_I32_S = new WasmOp("I64_EXTEND_I32_S", 27, "i64.extend_i32_s", 172, list, i, defaultConstructorMarker);
        I64_EXTEND_I32_U = new WasmOp("I64_EXTEND_I32_U", 28, "i64.extend_i32_u", 173, list2, i2, defaultConstructorMarker2);
        I64_TRUNC_F32_S = new WasmOp("I64_TRUNC_F32_S", 29, "i64.trunc_f32_s", 174, list, i, defaultConstructorMarker);
        I64_TRUNC_F32_U = new WasmOp("I64_TRUNC_F32_U", 30, "i64.trunc_f32_u", 175, list2, i2, defaultConstructorMarker2);
        I64_TRUNC_F64_S = new WasmOp("I64_TRUNC_F64_S", 31, "i64.trunc_f64_s", 176, list, i, defaultConstructorMarker);
        I64_TRUNC_F64_U = new WasmOp("I64_TRUNC_F64_U", 32, "i64.trunc_f64_u", 177, list2, i2, defaultConstructorMarker2);
        F32_CONVERT_I32_S = new WasmOp("F32_CONVERT_I32_S", 33, "f32.convert_i32_s", 178, list, i, defaultConstructorMarker);
        F32_CONVERT_I32_U = new WasmOp("F32_CONVERT_I32_U", 34, "f32.convert_i32_u", 179, list2, i2, defaultConstructorMarker2);
        F32_CONVERT_I64_S = new WasmOp("F32_CONVERT_I64_S", 35, "f32.convert_i64_s", 180, list, i, defaultConstructorMarker);
        F32_CONVERT_I64_U = new WasmOp("F32_CONVERT_I64_U", 36, "f32.convert_i64_u", 181, list2, i2, defaultConstructorMarker2);
        F32_DEMOTE_F64 = new WasmOp("F32_DEMOTE_F64", 37, "f32.demote_f64", 182, list, i, defaultConstructorMarker);
        F64_CONVERT_I32_S = new WasmOp("F64_CONVERT_I32_S", 38, "f64.convert_i32_s", 183, list2, i2, defaultConstructorMarker2);
        F64_CONVERT_I32_U = new WasmOp("F64_CONVERT_I32_U", 39, "f64.convert_i32_u", 184, list, i, defaultConstructorMarker);
        F64_CONVERT_I64_S = new WasmOp("F64_CONVERT_I64_S", 40, "f64.convert_i64_s", 185, list2, i2, defaultConstructorMarker2);
        F64_CONVERT_I64_U = new WasmOp("F64_CONVERT_I64_U", 41, "f64.convert_i64_u", 186, list, i, defaultConstructorMarker);
        F64_PROMOTE_F32 = new WasmOp("F64_PROMOTE_F32", 42, "f64.promote_f32", 187, list2, i2, defaultConstructorMarker2);
        I32_REINTERPRET_F32 = new WasmOp("I32_REINTERPRET_F32", 43, "i32.reinterpret_f32", 188, list, i, defaultConstructorMarker);
        I64_REINTERPRET_F64 = new WasmOp("I64_REINTERPRET_F64", 44, "i64.reinterpret_f64", 189, list2, i2, defaultConstructorMarker2);
        F32_REINTERPRET_I32 = new WasmOp("F32_REINTERPRET_I32", 45, "f32.reinterpret_i32", 190, list, i, defaultConstructorMarker);
        F64_REINTERPRET_I64 = new WasmOp("F64_REINTERPRET_I64", 46, "f64.reinterpret_i64", 191, list2, i2, defaultConstructorMarker2);
        I32_EXTEND8_S = new WasmOp("I32_EXTEND8_S", 47, "i32.extend8_s", 192, list, i, defaultConstructorMarker);
        I32_EXTEND16_S = new WasmOp("I32_EXTEND16_S", 48, "i32.extend16_s", 193, list2, i2, defaultConstructorMarker2);
        I64_EXTEND8_S = new WasmOp("I64_EXTEND8_S", 49, "i64.extend8_s", 194, list, i, defaultConstructorMarker);
        I64_EXTEND16_S = new WasmOp("I64_EXTEND16_S", 50, "i64.extend16_s", 195, list2, i2, defaultConstructorMarker2);
        I64_EXTEND32_S = new WasmOp("I64_EXTEND32_S", 51, "i64.extend32_s", 196, list, i, defaultConstructorMarker);
        I32_TRUNC_SAT_F32_S = new WasmOp("I32_TRUNC_SAT_F32_S", 52, "i32.trunc_sat_f32_s", 64512, list2, i2, defaultConstructorMarker2);
        I32_TRUNC_SAT_F32_U = new WasmOp("I32_TRUNC_SAT_F32_U", 53, "i32.trunc_sat_f32_u", 64513, list, i, defaultConstructorMarker);
        I32_TRUNC_SAT_F64_S = new WasmOp("I32_TRUNC_SAT_F64_S", 54, "i32.trunc_sat_f64_s", 64514, list2, i2, defaultConstructorMarker2);
        I32_TRUNC_SAT_F64_U = new WasmOp("I32_TRUNC_SAT_F64_U", 55, "i32.trunc_sat_f64_u", 64515, list, i, defaultConstructorMarker);
        I64_TRUNC_SAT_F32_S = new WasmOp("I64_TRUNC_SAT_F32_S", 56, "i64.trunc_sat_f32_s", 64516, list2, i2, defaultConstructorMarker2);
        I64_TRUNC_SAT_F32_U = new WasmOp("I64_TRUNC_SAT_F32_U", 57, "i64.trunc_sat_f32_u", 64517, list, i, defaultConstructorMarker);
        I64_TRUNC_SAT_F64_S = new WasmOp("I64_TRUNC_SAT_F64_S", 58, "i64.trunc_sat_f64_s", 64518, list2, i2, defaultConstructorMarker2);
        I64_TRUNC_SAT_F64_U = new WasmOp("I64_TRUNC_SAT_F64_U", 59, "i64.trunc_sat_f64_u", 64519, list, i, defaultConstructorMarker);
        I32_EQ = new WasmOp("I32_EQ", 60, "i32.eq", 70, list2, i2, defaultConstructorMarker2);
        I32_NE = new WasmOp("I32_NE", 61, "i32.ne", 71, list, i, defaultConstructorMarker);
        I32_LT_S = new WasmOp("I32_LT_S", 62, "i32.lt_s", 72, list2, i2, defaultConstructorMarker2);
        I32_LT_U = new WasmOp("I32_LT_U", 63, "i32.lt_u", 73, list, i, defaultConstructorMarker);
        I32_GT_S = new WasmOp("I32_GT_S", 64, "i32.gt_s", 74, list2, i2, defaultConstructorMarker2);
        I32_GT_U = new WasmOp("I32_GT_U", 65, "i32.gt_u", 75, list, i, defaultConstructorMarker);
        I32_LE_S = new WasmOp("I32_LE_S", 66, "i32.le_s", 76, list2, i2, defaultConstructorMarker2);
        I32_LE_U = new WasmOp("I32_LE_U", 67, "i32.le_u", 77, list, i, defaultConstructorMarker);
        I32_GE_S = new WasmOp("I32_GE_S", 68, "i32.ge_s", 78, list2, i2, defaultConstructorMarker2);
        I32_GE_U = new WasmOp("I32_GE_U", 69, "i32.ge_u", 79, list, i, defaultConstructorMarker);
        I64_EQ = new WasmOp("I64_EQ", 70, "i64.eq", 81, list2, i2, defaultConstructorMarker2);
        I64_NE = new WasmOp("I64_NE", 71, "i64.ne", 82, list, i, defaultConstructorMarker);
        I64_LT_S = new WasmOp("I64_LT_S", 72, "i64.lt_s", 83, list2, i2, defaultConstructorMarker2);
        I64_LT_U = new WasmOp("I64_LT_U", 73, "i64.lt_u", 84, list, i, defaultConstructorMarker);
        I64_GT_S = new WasmOp("I64_GT_S", 74, "i64.gt_s", 85, list2, i2, defaultConstructorMarker2);
        I64_GT_U = new WasmOp("I64_GT_U", 75, "i64.gt_u", 86, list, i, defaultConstructorMarker);
        I64_LE_S = new WasmOp("I64_LE_S", 76, "i64.le_s", 87, list2, i2, defaultConstructorMarker2);
        I64_LE_U = new WasmOp("I64_LE_U", 77, "i64.le_u", 88, list, i, defaultConstructorMarker);
        I64_GE_S = new WasmOp("I64_GE_S", 78, "i64.ge_s", 89, list2, i2, defaultConstructorMarker2);
        I64_GE_U = new WasmOp("I64_GE_U", 79, "i64.ge_u", 90, list, i, defaultConstructorMarker);
        F32_EQ = new WasmOp("F32_EQ", 80, "f32.eq", 91, list2, i2, defaultConstructorMarker2);
        F32_NE = new WasmOp("F32_NE", 81, "f32.ne", 92, list, i, defaultConstructorMarker);
        F32_LT = new WasmOp("F32_LT", 82, "f32.lt", 93, list2, i2, defaultConstructorMarker2);
        F32_GT = new WasmOp("F32_GT", 83, "f32.gt", 94, list, i, defaultConstructorMarker);
        F32_LE = new WasmOp("F32_LE", 84, "f32.le", 95, list2, i2, defaultConstructorMarker2);
        F32_GE = new WasmOp("F32_GE", 85, "f32.ge", 96, list, i, defaultConstructorMarker);
        F64_EQ = new WasmOp("F64_EQ", 86, "f64.eq", 97, list2, i2, defaultConstructorMarker2);
        F64_NE = new WasmOp("F64_NE", 87, "f64.ne", 98, list, i, defaultConstructorMarker);
        F64_LT = new WasmOp("F64_LT", 88, "f64.lt", 99, list2, i2, defaultConstructorMarker2);
        F64_GT = new WasmOp("F64_GT", 89, "f64.gt", 100, list, i, defaultConstructorMarker);
        F64_LE = new WasmOp("F64_LE", 90, "f64.le", 101, list2, i2, defaultConstructorMarker2);
        F64_GE = new WasmOp("F64_GE", 91, "f64.ge", 102, list, i, defaultConstructorMarker);
        I32_ADD = new WasmOp("I32_ADD", 92, "i32.add", 106, list2, i2, defaultConstructorMarker2);
        I32_SUB = new WasmOp("I32_SUB", 93, "i32.sub", 107, list, i, defaultConstructorMarker);
        I32_MUL = new WasmOp("I32_MUL", 94, "i32.mul", 108, list2, i2, defaultConstructorMarker2);
        I32_DIV_S = new WasmOp("I32_DIV_S", 95, "i32.div_s", 109, list, i, defaultConstructorMarker);
        I32_DIV_U = new WasmOp("I32_DIV_U", 96, "i32.div_u", 110, list2, i2, defaultConstructorMarker2);
        I32_REM_S = new WasmOp("I32_REM_S", 97, "i32.rem_s", 111, list, i, defaultConstructorMarker);
        I32_REM_U = new WasmOp("I32_REM_U", 98, "i32.rem_u", 112, list2, i2, defaultConstructorMarker2);
        I32_AND = new WasmOp("I32_AND", 99, "i32.and", 113, list, i, defaultConstructorMarker);
        I32_OR = new WasmOp("I32_OR", 100, "i32.or", 114, list2, i2, defaultConstructorMarker2);
        I32_XOR = new WasmOp("I32_XOR", 101, "i32.xor", 115, list, i, defaultConstructorMarker);
        I32_SHL = new WasmOp("I32_SHL", 102, "i32.shl", 116, list2, i2, defaultConstructorMarker2);
        I32_SHR_S = new WasmOp("I32_SHR_S", 103, "i32.shr_s", 117, list, i, defaultConstructorMarker);
        I32_SHR_U = new WasmOp("I32_SHR_U", 104, "i32.shr_u", 118, list2, i2, defaultConstructorMarker2);
        I32_ROTL = new WasmOp("I32_ROTL", 105, "i32.rotl", 119, list, i, defaultConstructorMarker);
        I32_ROTR = new WasmOp("I32_ROTR", 106, "i32.rotr", 120, list2, i2, defaultConstructorMarker2);
        I64_ADD = new WasmOp("I64_ADD", 107, "i64.add", 124, list, i, defaultConstructorMarker);
        I64_SUB = new WasmOp("I64_SUB", 108, "i64.sub", 125, list2, i2, defaultConstructorMarker2);
        I64_MUL = new WasmOp("I64_MUL", 109, "i64.mul", 126, list, i, defaultConstructorMarker);
        I64_DIV_S = new WasmOp("I64_DIV_S", 110, "i64.div_s", 127, list2, i2, defaultConstructorMarker2);
        I64_DIV_U = new WasmOp("I64_DIV_U", 111, "i64.div_u", 128, list, i, defaultConstructorMarker);
        I64_REM_S = new WasmOp("I64_REM_S", 112, "i64.rem_s", 129, list2, i2, defaultConstructorMarker2);
        I64_REM_U = new WasmOp("I64_REM_U", 113, "i64.rem_u", 130, list, i, defaultConstructorMarker);
        I64_AND = new WasmOp("I64_AND", 114, "i64.and", 131, list2, i2, defaultConstructorMarker2);
        I64_OR = new WasmOp("I64_OR", 115, "i64.or", 132, list, i, defaultConstructorMarker);
        I64_XOR = new WasmOp("I64_XOR", 116, "i64.xor", 133, list2, i2, defaultConstructorMarker2);
        I64_SHL = new WasmOp("I64_SHL", 117, "i64.shl", 134, list, i, defaultConstructorMarker);
        I64_SHR_S = new WasmOp("I64_SHR_S", 118, "i64.shr_s", 135, list2, i2, defaultConstructorMarker2);
        I64_SHR_U = new WasmOp("I64_SHR_U", 119, "i64.shr_u", 136, list, i, defaultConstructorMarker);
        I64_ROTL = new WasmOp("I64_ROTL", 120, "i64.rotl", 137, list2, i2, defaultConstructorMarker2);
        I64_ROTR = new WasmOp("I64_ROTR", 121, "i64.rotr", 138, list, i, defaultConstructorMarker);
        F32_ADD = new WasmOp("F32_ADD", 122, "f32.add", 146, list2, i2, defaultConstructorMarker2);
        F32_SUB = new WasmOp("F32_SUB", 123, "f32.sub", 147, list, i, defaultConstructorMarker);
        F32_MUL = new WasmOp("F32_MUL", 124, "f32.mul", 148, list2, i2, defaultConstructorMarker2);
        F32_DIV = new WasmOp("F32_DIV", 125, "f32.div", 149, list, i, defaultConstructorMarker);
        F32_MIN = new WasmOp("F32_MIN", 126, "f32.min", 150, list2, i2, defaultConstructorMarker2);
        F32_MAX = new WasmOp("F32_MAX", 127, "f32.max", 151, list, i, defaultConstructorMarker);
        F32_COPYSIGN = new WasmOp("F32_COPYSIGN", 128, "f32.copysign", 152, list2, i2, defaultConstructorMarker2);
        F64_ADD = new WasmOp("F64_ADD", 129, "f64.add", 160, list, i, defaultConstructorMarker);
        F64_SUB = new WasmOp("F64_SUB", 130, "f64.sub", 161, list2, i2, defaultConstructorMarker2);
        F64_MUL = new WasmOp("F64_MUL", 131, "f64.mul", 162, list, i, defaultConstructorMarker);
        F64_DIV = new WasmOp("F64_DIV", 132, "f64.div", 163, list2, i2, defaultConstructorMarker2);
        F64_MIN = new WasmOp("F64_MIN", 133, "f64.min", 164, list, i, defaultConstructorMarker);
        F64_MAX = new WasmOp("F64_MAX", 134, "f64.max", 165, list2, i2, defaultConstructorMarker2);
        F64_COPYSIGN = new WasmOp("F64_COPYSIGN", 135, "f64.copysign", 166, list, i, defaultConstructorMarker);
        int i3 = 4;
        DefaultConstructorMarker defaultConstructorMarker3 = null;
        DROP = new WasmOp("DROP", 196, "drop", 26, null, i3, defaultConstructorMarker3);
        int i4 = 4;
        DefaultConstructorMarker defaultConstructorMarker4 = null;
        SELECT = new WasmOp("SELECT", 197, "select", 27, 0 == true ? 1 : 0, i4, defaultConstructorMarker4);
        REF_IS_NULL = new WasmOp("REF_IS_NULL", 205, "ref.is_null", 209, null, i3, defaultConstructorMarker3);
        REF_AS_NOT_NULL = new WasmOp("REF_AS_NOT_NULL", 209, "ref.as_non_null", 212, null, i3, defaultConstructorMarker3);
        I31_GET_S = new WasmOp("I31_GET_S", XKeySym.XK_aring, "i31.get_s", 64285, 0 == true ? 1 : 0, i4, defaultConstructorMarker4);
        int i5 = 4;
        DefaultConstructorMarker defaultConstructorMarker5 = null;
        I31_GET_U = new WasmOp("I31_GET_U", XKeySym.XK_ae, "i31.get_u", 64286, null, i5, defaultConstructorMarker5);
        int i6 = 4;
        DefaultConstructorMarker defaultConstructorMarker6 = null;
        List list3 = null;
        EXTERN_INTERNALIZE = new WasmOp("EXTERN_INTERNALIZE", XKeySym.XK_igrave, "extern.internalize", 64282, list3, i6, defaultConstructorMarker6);
        List list4 = null;
        EXTERN_EXTERNALIZE = new WasmOp("EXTERN_EXTERNALIZE", XKeySym.XK_iacute, "extern.externalize", 64283, list4, i5, defaultConstructorMarker5);
        int i7 = 65535;
        PSEUDO_COMMENT_PREVIOUS_INSTR = new WasmOp("PSEUDO_COMMENT_PREVIOUS_INSTR", XKeySym.XK_icircumflex, "<comment-single>", i7, list3, i6, defaultConstructorMarker6);
        PSEUDO_COMMENT_GROUP_START = new WasmOp("PSEUDO_COMMENT_GROUP_START", XKeySym.XK_idiaeresis, "<comment-group-start>", 65535, list4, i5, defaultConstructorMarker5);
        PSEUDO_COMMENT_GROUP_END = new WasmOp("PSEUDO_COMMENT_GROUP_END", 240, "<comment-group-end>", i7, list3, i6, defaultConstructorMarker6);
        WasmOp[] wasmOpArr$values = $values();
        $VALUES = wasmOpArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(wasmOpArr$values);
    }

    private WasmOp(String str, int i, String str2, int i2, WasmImmediateKind... wasmImmediateKindArr) {
        this(str, i, str2, i2, ArraysKt.toList(wasmImmediateKindArr));
    }
}
