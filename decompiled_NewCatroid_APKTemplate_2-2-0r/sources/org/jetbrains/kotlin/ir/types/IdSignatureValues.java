package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: irTypePredicates.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IdSignatureValues;", "", "()V", "_boolean", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "_byte", "_char", "_double", "_float", "_int", "_long", "_short", "any", "array", "charSequence", "collection", "comparable", "continuation", "iterable", "kClass", "nothing", "number", "result", "sequence", "string", "uByte", "uInt", "uLong", "uShort", "unit", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdSignatureValues {
    public static final IdSignatureValues INSTANCE = new IdSignatureValues();
    public static final IdSignature.CommonSignature any = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Any");
    public static final IdSignature.CommonSignature nothing = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Nothing");
    public static final IdSignature.CommonSignature unit = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Unit");
    public static final IdSignature.CommonSignature _boolean = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Boolean");
    public static final IdSignature.CommonSignature _char = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Char");
    public static final IdSignature.CommonSignature _byte = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Byte");
    public static final IdSignature.CommonSignature _short = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Short");
    public static final IdSignature.CommonSignature _int = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Int");
    public static final IdSignature.CommonSignature _long = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Long");
    public static final IdSignature.CommonSignature _float = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Float");
    public static final IdSignature.CommonSignature _double = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Double");
    public static final IdSignature.CommonSignature number = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Number");
    public static final IdSignature.CommonSignature uByte = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "UByte");
    public static final IdSignature.CommonSignature uShort = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "UShort");
    public static final IdSignature.CommonSignature uInt = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "UInt");
    public static final IdSignature.CommonSignature uLong = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "ULong");
    public static final IdSignature.CommonSignature string = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, CommonClassNames.JAVA_LANG_STRING_SHORT);
    public static final IdSignature.CommonSignature array = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Array");
    public static final IdSignature.CommonSignature collection = IrTypePredicatesKt.getPublicSignature(StandardNames.COLLECTIONS_PACKAGE_FQ_NAME, "Collection");
    public static final IdSignature.CommonSignature kClass = IrTypePredicatesKt.getPublicSignature(StandardNames.KOTLIN_REFLECT_FQ_NAME, "KClass");
    public static final IdSignature.CommonSignature comparable = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Comparable");
    public static final IdSignature.CommonSignature charSequence = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "CharSequence");
    public static final IdSignature.CommonSignature iterable = IrTypePredicatesKt.getPublicSignature(StandardNames.COLLECTIONS_PACKAGE_FQ_NAME, "Iterable");
    public static final IdSignature.CommonSignature continuation = IrTypePredicatesKt.getPublicSignature(StandardNames.COROUTINES_PACKAGE_FQ_NAME, "Continuation");
    public static final IdSignature.CommonSignature result = IrTypePredicatesKt.getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Result");
    public static final IdSignature.CommonSignature sequence = new IdSignature.CommonSignature("kotlin.sequences", "Sequence", null, 0, "kotlin.sequences.Sequence");

    private IdSignatureValues() {
    }
}
