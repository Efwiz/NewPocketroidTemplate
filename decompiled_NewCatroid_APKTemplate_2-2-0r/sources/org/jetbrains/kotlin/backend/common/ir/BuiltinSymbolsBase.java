package org.jetbrains.kotlin.backend.common.ir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Ir.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b.\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b;\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u001e\n\u0002\b)\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010Ä\u0001\u001a\u00020\b2\b\u0010Å\u0001\u001a\u00030Æ\u0001H\u0016J\"\u0010Ç\u0001\u001a\u00020\u000e2\u0007\u0010È\u0001\u001a\u00020\u001e2\u0007\u0010É\u0001\u001a\u00020\u001f2\u0007\u0010Ê\u0001\u001a\u00020\u001fJ2\u0010Ë\u0001\u001a\u00020\b2\u0007\u0010È\u0001\u001a\u00020\u001e2\u0018\b\u0002\u0010Ì\u0001\u001a\f\u0012\u0007\b\u0001\u0012\u00030Î\u00010Í\u0001\"\u00030Î\u0001H\u0002¢\u0006\u0003\u0010Ï\u0001J\u0019\u0010Ð\u0001\u001a\u00020\u000e2\u0007\u0010È\u0001\u001a\u00020\u001e2\u0007\u0010Ñ\u0001\u001a\u00020\u001fJ\u0012\u0010Ò\u0001\u001a\u00030Ó\u00012\b\u0010Ô\u0001\u001a\u00030Õ\u0001J\u0007\u0010Ö\u0001\u001a\u00020\bJ\u0007\u0010×\u0001\u001a\u00020\bJ\u0007\u0010Ø\u0001\u001a\u00020\bJ\u0007\u0010Ù\u0001\u001a\u00020\bJ\u0007\u0010Ú\u0001\u001a\u00020\bJ\u0007\u0010Û\u0001\u001a\u00020\bJ\u0013\u0010Ü\u0001\u001a\u00020\b2\b\u0010È\u0001\u001a\u00030Î\u0001H\u0002J\u0015\u0010Ý\u0001\u001a\u0004\u0018\u00010\b2\b\u0010È\u0001\u001a\u00030Î\u0001H\u0002J\u0013\u0010Þ\u0001\u001a\u00020\b2\b\u0010Å\u0001\u001a\u00030Æ\u0001H\u0016R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR,\u0010\u001b\u001a \u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001f0\u001d\u0012\u0004\u0012\u00020\u000e0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b!\u0010\nR\u0011\u0010\"\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b'\u0010\nR\u0011\u0010(\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b)\u0010\nR\u0011\u0010*\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b+\u0010%R\u0011\u0010,\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b-\u0010\nR\u0011\u0010.\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b/\u0010\nR\u0011\u00100\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b1\u0010%R\u0011\u00102\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b3\u0010\nR\u0011\u00104\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\nR\u0011\u00106\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\nR\u0011\u00108\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\nR\u0011\u0010:\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b;\u0010\nR\u0011\u0010<\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b=\u0010\nR\u0011\u0010>\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b?\u0010\nR\u0011\u0010@\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bA\u0010\nR\u0011\u0010B\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\bC\u0010%R\u0011\u0010D\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\bE\u0010\u0010R\u0011\u0010F\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\bG\u0010\u0010R\u0014\u0010H\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0011\u0010K\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bL\u0010\nR\u0011\u0010M\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bN\u0010\nR\u0011\u0010O\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\bP\u0010%R \u0010Q\u001a\u000e\u0012\u0004\u0012\u00020S\u0012\u0004\u0012\u00020\u000e0RX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010UR\u0011\u0010V\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bW\u0010\nR\u0011\u0010X\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bY\u0010\nR\u0011\u0010Z\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b[\u0010%R\u0011\u0010\\\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b]\u0010\nR\u0011\u0010^\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b_\u0010\nR\u0017\u0010`\u001a\b\u0012\u0004\u0012\u00020\b0\u0014¢\u0006\b\n\u0000\u001a\u0004\ba\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bb\u0010cR\u0011\u0010d\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\be\u0010\nR\u0011\u0010f\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bg\u0010\nR\u0011\u0010h\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bi\u0010\nR\u0011\u0010j\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bk\u0010\nR\u0011\u0010l\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bm\u0010\nR\u0011\u0010n\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bo\u0010\nR\u0011\u0010p\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\bq\u0010%R\u0011\u0010r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bs\u0010\nR\u0011\u0010t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bu\u0010\nR\u0011\u0010v\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bw\u0010\nR\u0011\u0010x\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\by\u0010\nR\u0011\u0010z\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b{\u0010\u0010R\u0011\u0010|\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b}\u0010\u0010R\u0011\u0010~\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u007f\u0010\nR\u0013\u0010\u0080\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0081\u0001\u0010\nR\u0013\u0010\u0082\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0083\u0001\u0010\nR\u0013\u0010\u0084\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0085\u0001\u0010\nR\u0013\u0010\u0086\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0087\u0001\u0010\nR\u0013\u0010\u0088\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0089\u0001\u0010\nR\u0013\u0010\u008a\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u008b\u0001\u0010\nR\u0013\u0010\u008c\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u008d\u0001\u0010\nR \u0010\u008e\u0001\u001a\u000f\u0012\u0004\u0012\u00020\b\u0012\u0005\u0012\u00030\u008f\u00010R8F¢\u0006\u0007\u001a\u0005\b\u0090\u0001\u0010UR \u0010\u0091\u0001\u001a\u000f\u0012\u0005\u0012\u00030\u008f\u0001\u0012\u0004\u0012\u00020\b0R¢\u0006\t\n\u0000\u001a\u0005\b\u0092\u0001\u0010UR \u0010\u0093\u0001\u001a\u000f\u0012\u0005\u0012\u00030\u008f\u0001\u0012\u0004\u0012\u00020\b0R8F¢\u0006\u0007\u001a\u0005\b\u0094\u0001\u0010UR\u0019\u0010\u0095\u0001\u001a\b\u0012\u0004\u0012\u00020\b0\u0014¢\u0006\t\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0016R'\u0010\u0097\u0001\u001a\t\u0012\u0004\u0012\u00020\u001f0\u0098\u00018FX\u0086\u0084\u0002¢\u0006\u0010\n\u0006\b\u009b\u0001\u0010\u009c\u0001\u001a\u0006\b\u0099\u0001\u0010\u009a\u0001R\u0019\u0010\u009d\u0001\u001a\b\u0012\u0004\u0012\u00020\b0\u0014¢\u0006\t\n\u0000\u001a\u0005\b\u009e\u0001\u0010\u0016R\u0015\u0010\u009f\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b \u0001\u0010\nR\u0013\u0010¡\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b¢\u0001\u0010\nR\u0013\u0010£\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b¤\u0001\u0010\nR\u0013\u0010¥\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b¦\u0001\u0010\nR\u0013\u0010§\u0001\u001a\u00020#8F¢\u0006\u0007\u001a\u0005\b¨\u0001\u0010%R\u0013\u0010©\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bª\u0001\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010«\u0001\u001a\u000e\u0012\u0004\u0012\u00020S\u0012\u0004\u0012\u00020\u000e0RX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¬\u0001\u0010UR\"\u0010\u00ad\u0001\u001a\u000e\u0012\u0004\u0012\u00020S\u0012\u0004\u0012\u00020\u000e0RX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b®\u0001\u0010UR\u0015\u0010¯\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b°\u0001\u0010\nR\u0015\u0010±\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b²\u0001\u0010\nR\u0015\u0010³\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b´\u0001\u0010\nR\u0015\u0010µ\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b¶\u0001\u0010\nR\u0015\u0010·\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b¸\u0001\u0010\nR\u0015\u0010¹\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\bº\u0001\u0010\nR\u0015\u0010»\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b¼\u0001\u0010\nR\u0015\u0010½\u0001\u001a\u0004\u0018\u00010\b¢\u0006\t\n\u0000\u001a\u0005\b¾\u0001\u0010\nR\u0013\u0010¿\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\bÀ\u0001\u0010\nR \u0010Á\u0001\u001a\u000f\u0012\u0005\u0012\u00030Â\u0001\u0012\u0004\u0012\u00020\b0R8F¢\u0006\u0007\u001a\u0005\bÃ\u0001\u0010U¨\u0006ß\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/BuiltinSymbolsBase;", "", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;)V", "any", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getAny", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "array", "getArray", VarargLoweringKt.ARRAY_OF_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getArrayOf", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "arrayOfNulls", "getArrayOfNulls", "arrays", "", "getArrays", "()Ljava/util/List;", "asserts", "", "getAsserts", "()Ljava/lang/Iterable;", "binaryOperatorCache", "", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "booleanArray", "getBooleanArray", "booleanArrayType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getBooleanArrayType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "byte", "getByte", "byteArray", "getByteArray", "byteArrayType", "getByteArrayType", PsiKeyword.CHAR, "getChar", "charArray", "getCharArray", "charArrayType", "getCharArrayType", "charProgression", "getCharProgression", "charRange", "getCharRange", "charSequence", "getCharSequence", "closedRange", "getClosedRange", "collection", "getCollection", "comparable", "getComparable", "double", "getDouble", "doubleArray", "getDoubleArray", "doubleArrayType", "getDoubleArrayType", "extensionStringPlus", "getExtensionStringPlus", "extensionToString", "getExtensionToString", "externalSymbolTable", "getExternalSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "float", "getFloat", "floatArray", "getFloatArray", "floatArrayType", "getFloatArrayType", "getProgressionLastElementByReturnType", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getGetProgressionLastElementByReturnType", "()Ljava/util/Map;", "int", "getInt", "intArray", "getIntArray", "intArrayType", "getIntArrayType", "intProgression", "getIntProgression", "intRange", "getIntRange", "integerClasses", "getIntegerClasses", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "iterable", "getIterable", "iterator", "getIterator", SchemaSymbols.ATTVAL_LIST, "getList", "listIterator", "getListIterator", "long", "getLong", "longArray", "getLongArray", "longArrayType", "getLongArrayType", "longProgression", "getLongProgression", "longRange", "getLongRange", "map", "getMap", "mapEntry", "getMapEntry", "memberStringPlus", "getMemberStringPlus", "memberToString", "getMemberToString", "mutableCollection", "getMutableCollection", "mutableIterable", "getMutableIterable", "mutableIterator", "getMutableIterator", "mutableList", "getMutableList", "mutableListIterator", "getMutableListIterator", "mutableMap", "getMutableMap", "mutableMapEntry", "getMutableMapEntry", "mutableSet", "getMutableSet", "primitiveArraysToPrimitiveTypes", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveArraysToPrimitiveTypes", "primitiveIteratorsByType", "getPrimitiveIteratorsByType", "primitiveTypesToPrimitiveArrays", "getPrimitiveTypesToPrimitiveArrays", "progressionClasses", "getProgressionClasses", "progressionElementTypes", "", "getProgressionElementTypes", "()Ljava/util/Collection;", "progressionElementTypes$delegate", "Lkotlin/Lazy;", "rangeClasses", "getRangeClasses", "sequence", "getSequence", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "getSet", "short", "getShort", "shortArray", "getShortArray", "shortArrayType", "getShortArrayType", "string", "getString", "toUIntByExtensionReceiver", "getToUIntByExtensionReceiver", "toULongByExtensionReceiver", "getToULongByExtensionReceiver", "uByte", "getUByte", "uInt", "getUInt", "uIntProgression", "getUIntProgression", "uIntRange", "getUIntRange", "uLong", "getULong", "uLongProgression", "getULongProgression", "uLongRange", "getULongRange", "uShort", "getUShort", "unit", "getUnit", "unsignedTypesToUnsignedArrays", "Lorg/jetbrains/kotlin/builtins/UnsignedType;", "getUnsignedTypesToUnsignedArrays", "functionN", "n", "", "getBinaryOperator", "name", "lhsType", "rhsType", "getClass", "packageNameSegments", "", "", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/String;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getUnaryOperator", "receiverType", "isStringPlus", "", "functionSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "kmutableproperty0", "kmutableproperty1", "kmutableproperty2", "kproperty0", "kproperty1", "kproperty2", "progression", "progressionOrNull", "suspendFunctionN", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BuiltinSymbolsBase {
    private final Iterable<IrSimpleFunctionSymbol> asserts;
    private final Map<Triple<Name, IrType, IrType>, IrSimpleFunctionSymbol> binaryOperatorCache;
    private final IrClassSymbol charProgression;
    private final IrClassSymbol charRange;
    private final IrClassSymbol charSequence;
    private final IrClassSymbol closedRange;
    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getProgressionLastElementByReturnType;
    private final IrClassSymbol intProgression;
    private final IrClassSymbol intRange;
    private final List<IrClassSymbol> integerClasses;
    private final IrBuiltIns irBuiltIns;
    private final IrClassSymbol iterator;
    private final IrClassSymbol longProgression;
    private final IrClassSymbol longRange;
    private final Map<PrimitiveType, IrClassSymbol> primitiveIteratorsByType;
    private final List<IrClassSymbol> progressionClasses;

    /* JADX INFO: renamed from: progressionElementTypes$delegate, reason: from kotlin metadata */
    private final Lazy progressionElementTypes;
    private final List<IrClassSymbol> rangeClasses;
    private final IrClassSymbol sequence;
    private final IrClassSymbol string;
    private final ReferenceSymbolTable symbolTable;
    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> toUIntByExtensionReceiver;
    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> toULongByExtensionReceiver;
    private final IrClassSymbol uByte;
    private final IrClassSymbol uInt;
    private final IrClassSymbol uIntProgression;
    private final IrClassSymbol uIntRange;
    private final IrClassSymbol uLong;
    private final IrClassSymbol uLongProgression;
    private final IrClassSymbol uLongRange;
    private final IrClassSymbol uShort;

    public BuiltinSymbolsBase(IrBuiltIns irBuiltIns, ReferenceSymbolTable symbolTable) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        this.irBuiltIns = irBuiltIns;
        this.symbolTable = symbolTable;
        Name nameIdentifier = Name.identifier("Iterator");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.iterator = getClass(nameIdentifier, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "collections");
        Name nameIdentifier2 = Name.identifier("CharSequence");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        this.charSequence = getClass(nameIdentifier2, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        Name nameIdentifier3 = Name.identifier(CommonClassNames.JAVA_LANG_STRING_SHORT);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        this.string = getClass(nameIdentifier3, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        PrimitiveType[] primitiveTypeArrValues = PrimitiveType.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(primitiveTypeArrValues.length), 16));
        for (PrimitiveType primitiveType : primitiveTypeArrValues) {
            Name nameIdentifier4 = Name.identifier(primitiveType.getTypeName().asString() + "Iterator");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
            Pair pair = TuplesKt.to(primitiveType, getClass(nameIdentifier4, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "collections"));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.primitiveIteratorsByType = linkedHashMap;
        IrBuiltIns irBuiltIns2 = this.irBuiltIns;
        Name nameIdentifier5 = Name.identifier(PsiKeyword.ASSERT);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        this.asserts = irBuiltIns2.findFunctions(nameIdentifier5, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        IrBuiltIns irBuiltIns3 = this.irBuiltIns;
        Name nameIdentifier6 = Name.identifier("UByte");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        this.uByte = irBuiltIns3.findClass(nameIdentifier6, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        IrBuiltIns irBuiltIns4 = this.irBuiltIns;
        Name nameIdentifier7 = Name.identifier("UShort");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
        this.uShort = irBuiltIns4.findClass(nameIdentifier7, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        IrBuiltIns irBuiltIns5 = this.irBuiltIns;
        Name nameIdentifier8 = Name.identifier("UInt");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier8, "identifier(...)");
        this.uInt = irBuiltIns5.findClass(nameIdentifier8, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        IrBuiltIns irBuiltIns6 = this.irBuiltIns;
        Name nameIdentifier9 = Name.identifier("ULong");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier9, "identifier(...)");
        this.uLong = irBuiltIns6.findClass(nameIdentifier9, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        IrClassSymbol irClassSymbolProgressionOrNull = progressionOrNull("UIntProgression");
        this.uIntProgression = irClassSymbolProgressionOrNull;
        IrClassSymbol irClassSymbolProgressionOrNull2 = progressionOrNull("ULongProgression");
        this.uLongProgression = irClassSymbolProgressionOrNull2;
        IrClassSymbol irClassSymbolProgressionOrNull3 = progressionOrNull("UIntRange");
        this.uIntRange = irClassSymbolProgressionOrNull3;
        IrClassSymbol irClassSymbolProgressionOrNull4 = progressionOrNull("ULongRange");
        this.uLongRange = irClassSymbolProgressionOrNull4;
        IrBuiltIns irBuiltIns7 = this.irBuiltIns;
        Name nameIdentifier10 = Name.identifier("Sequence");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier10, "identifier(...)");
        this.sequence = irBuiltIns7.findClass(nameIdentifier10, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "sequences");
        IrClassSymbol irClassSymbolProgression = progression("CharProgression");
        this.charProgression = irClassSymbolProgression;
        IrClassSymbol irClassSymbolProgression2 = progression("IntProgression");
        this.intProgression = irClassSymbolProgression2;
        IrClassSymbol irClassSymbolProgression3 = progression("LongProgression");
        this.longProgression = irClassSymbolProgression3;
        this.progressionClasses = CollectionsKt.listOfNotNull((Object[]) new IrClassSymbol[]{irClassSymbolProgression, irClassSymbolProgression2, irClassSymbolProgression3, irClassSymbolProgressionOrNull, irClassSymbolProgressionOrNull2});
        IrClassSymbol irClassSymbolProgression4 = progression("CharRange");
        this.charRange = irClassSymbolProgression4;
        IrClassSymbol irClassSymbolProgression5 = progression("IntRange");
        this.intRange = irClassSymbolProgression5;
        IrClassSymbol irClassSymbolProgression6 = progression("LongRange");
        this.longRange = irClassSymbolProgression6;
        this.rangeClasses = CollectionsKt.listOfNotNull((Object[]) new IrClassSymbol[]{irClassSymbolProgression4, irClassSymbolProgression5, irClassSymbolProgression6, irClassSymbolProgressionOrNull3, irClassSymbolProgressionOrNull4});
        this.closedRange = progression("ClosedRange");
        IrBuiltIns irBuiltIns8 = this.irBuiltIns;
        Name nameIdentifier11 = Name.identifier("getProgressionLastElement");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier11, "identifier(...)");
        this.getProgressionLastElementByReturnType = irBuiltIns8.getNonBuiltinFunctionsByReturnType(nameIdentifier11, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "internal");
        IrBuiltIns irBuiltIns9 = this.irBuiltIns;
        Name nameIdentifier12 = Name.identifier(Namer.UINT_FROM_INT);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier12, "identifier(...)");
        this.toUIntByExtensionReceiver = irBuiltIns9.getNonBuiltInFunctionsByExtensionReceiver(nameIdentifier12, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        IrBuiltIns irBuiltIns10 = this.irBuiltIns;
        Name nameIdentifier13 = Name.identifier("toULong");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier13, "identifier(...)");
        this.toULongByExtensionReceiver = irBuiltIns10.getNonBuiltInFunctionsByExtensionReceiver(nameIdentifier13, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        this.integerClasses = CollectionsKt.listOf((Object[]) new IrClassSymbol[]{getByte(), getShort(), getInt(), getLong()});
        this.progressionElementTypes = LazyKt.lazy(new Function0<List<? extends IrType>>() { // from class: org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase$progressionElementTypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrType> invoke() {
                List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new IrClassSymbol[]{this.this$0.getByte(), this.this$0.getShort(), this.this$0.getInt(), this.this$0.getLong(), this.this$0.getChar(), this.this$0.getUByte(), this.this$0.getUShort(), this.this$0.getUInt(), this.this$0.getULong()});
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOfNotNull, 10));
                Iterator it = listListOfNotNull.iterator();
                while (it.hasNext()) {
                    arrayList.add(IrTypesKt.getDefaultType((IrClassSymbol) it.next()));
                }
                return arrayList;
            }
        });
        this.binaryOperatorCache = new LinkedHashMap();
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    private final IrClassSymbol getClass(Name name2, String... packageNameSegments) {
        IrClassSymbol irClassSymbolFindClass = this.irBuiltIns.findClass(name2, (String[]) Arrays.copyOf(packageNameSegments, packageNameSegments.length));
        if (irClassSymbolFindClass != null) {
            return irClassSymbolFindClass;
        }
        throw new IllegalStateException(("Class '" + name2 + "' not found in package '" + ArraysKt.joinToString$default(packageNameSegments, ".", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null) + '\'').toString());
    }

    /* JADX INFO: renamed from: getExternalSymbolTable, reason: from getter */
    public ReferenceSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final IrClassSymbol getIterator() {
        return this.iterator;
    }

    public final IrClassSymbol getCharSequence() {
        return this.charSequence;
    }

    public final IrClassSymbol getString() {
        return this.string;
    }

    public final Map<PrimitiveType, IrClassSymbol> getPrimitiveIteratorsByType() {
        return this.primitiveIteratorsByType;
    }

    public final Iterable<IrSimpleFunctionSymbol> getAsserts() {
        return this.asserts;
    }

    private final IrClassSymbol progression(String name2) {
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return getClass(nameIdentifier, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "ranges");
    }

    private final IrClassSymbol progressionOrNull(String name2) {
        IrBuiltIns irBuiltIns = this.irBuiltIns;
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return irBuiltIns.findClass(nameIdentifier, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "ranges");
    }

    public final IrClassSymbol getUByte() {
        return this.uByte;
    }

    public final IrClassSymbol getUShort() {
        return this.uShort;
    }

    public final IrClassSymbol getUInt() {
        return this.uInt;
    }

    public final IrClassSymbol getULong() {
        return this.uLong;
    }

    public final IrClassSymbol getUIntProgression() {
        return this.uIntProgression;
    }

    public final IrClassSymbol getULongProgression() {
        return this.uLongProgression;
    }

    public final IrClassSymbol getUIntRange() {
        return this.uIntRange;
    }

    public final IrClassSymbol getULongRange() {
        return this.uLongRange;
    }

    public final IrClassSymbol getSequence() {
        return this.sequence;
    }

    public final IrClassSymbol getCharProgression() {
        return this.charProgression;
    }

    public final IrClassSymbol getIntProgression() {
        return this.intProgression;
    }

    public final IrClassSymbol getLongProgression() {
        return this.longProgression;
    }

    public final List<IrClassSymbol> getProgressionClasses() {
        return this.progressionClasses;
    }

    public final IrClassSymbol getCharRange() {
        return this.charRange;
    }

    public final IrClassSymbol getIntRange() {
        return this.intRange;
    }

    public final IrClassSymbol getLongRange() {
        return this.longRange;
    }

    public final List<IrClassSymbol> getRangeClasses() {
        return this.rangeClasses;
    }

    public final IrClassSymbol getClosedRange() {
        return this.closedRange;
    }

    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getGetProgressionLastElementByReturnType() {
        return this.getProgressionLastElementByReturnType;
    }

    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getToUIntByExtensionReceiver() {
        return this.toUIntByExtensionReceiver;
    }

    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getToULongByExtensionReceiver() {
        return this.toULongByExtensionReceiver;
    }

    public final IrClassSymbol getAny() {
        return this.irBuiltIns.getAnyClass();
    }

    public final IrClassSymbol getUnit() {
        return this.irBuiltIns.getUnitClass();
    }

    public final IrClassSymbol getChar() {
        return this.irBuiltIns.getCharClass();
    }

    public final IrClassSymbol getByte() {
        return this.irBuiltIns.getByteClass();
    }

    public final IrClassSymbol getShort() {
        return this.irBuiltIns.getShortClass();
    }

    public final IrClassSymbol getInt() {
        return this.irBuiltIns.getIntClass();
    }

    public final IrClassSymbol getLong() {
        return this.irBuiltIns.getLongClass();
    }

    public final IrClassSymbol getFloat() {
        return this.irBuiltIns.getFloatClass();
    }

    public final IrClassSymbol getDouble() {
        return this.irBuiltIns.getDoubleClass();
    }

    public final List<IrClassSymbol> getIntegerClasses() {
        return this.integerClasses;
    }

    public final Collection<IrType> getProgressionElementTypes() {
        return (Collection) this.progressionElementTypes.getValue();
    }

    public final IrSimpleFunctionSymbol getArrayOf() {
        return this.irBuiltIns.getArrayOf();
    }

    public final IrSimpleFunctionSymbol getArrayOfNulls() {
        return this.irBuiltIns.getArrayOfNulls();
    }

    public final IrClassSymbol getArray() {
        return this.irBuiltIns.getArrayClass();
    }

    public final IrClassSymbol getByteArray() {
        return this.irBuiltIns.getByteArray();
    }

    public final IrClassSymbol getCharArray() {
        return this.irBuiltIns.getCharArray();
    }

    public final IrClassSymbol getShortArray() {
        return this.irBuiltIns.getShortArray();
    }

    public final IrClassSymbol getIntArray() {
        return this.irBuiltIns.getIntArray();
    }

    public final IrClassSymbol getLongArray() {
        return this.irBuiltIns.getLongArray();
    }

    public final IrClassSymbol getFloatArray() {
        return this.irBuiltIns.getFloatArray();
    }

    public final IrClassSymbol getDoubleArray() {
        return this.irBuiltIns.getDoubleArray();
    }

    public final IrClassSymbol getBooleanArray() {
        return this.irBuiltIns.getBooleanArray();
    }

    public final IrSimpleType getByteArrayType() {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(getByteArray().getOwner());
    }

    public final IrSimpleType getCharArrayType() {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(getCharArray().getOwner());
    }

    public final IrSimpleType getShortArrayType() {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(getShortArray().getOwner());
    }

    public final IrSimpleType getIntArrayType() {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(getIntArray().getOwner());
    }

    public final IrSimpleType getLongArrayType() {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(getLongArray().getOwner());
    }

    public final IrSimpleType getFloatArrayType() {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(getFloatArray().getOwner());
    }

    public final IrSimpleType getDoubleArrayType() {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(getDoubleArray().getOwner());
    }

    public final IrSimpleType getBooleanArrayType() {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(getBooleanArray().getOwner());
    }

    public final Map<PrimitiveType, IrClassSymbol> getPrimitiveTypesToPrimitiveArrays() {
        return this.irBuiltIns.getPrimitiveTypesToPrimitiveArrays();
    }

    public final Map<IrClassSymbol, PrimitiveType> getPrimitiveArraysToPrimitiveTypes() {
        return this.irBuiltIns.getPrimitiveArraysToPrimitiveTypes();
    }

    public final Map<UnsignedType, IrClassSymbol> getUnsignedTypesToUnsignedArrays() {
        return this.irBuiltIns.getUnsignedTypesToUnsignedArrays();
    }

    public final List<IrClassSymbol> getArrays() {
        return CollectionsKt.plus((Collection<? extends IrClassSymbol>) CollectionsKt.plus((Collection) getPrimitiveTypesToPrimitiveArrays().values(), (Iterable) getUnsignedTypesToUnsignedArrays().values()), getArray());
    }

    public final IrClassSymbol getCollection() {
        return this.irBuiltIns.getCollectionClass();
    }

    public final IrClassSymbol getSet() {
        return this.irBuiltIns.getSetClass();
    }

    public final IrClassSymbol getList() {
        return this.irBuiltIns.getListClass();
    }

    public final IrClassSymbol getMap() {
        return this.irBuiltIns.getMapClass();
    }

    public final IrClassSymbol getMapEntry() {
        return this.irBuiltIns.getMapEntryClass();
    }

    public final IrClassSymbol getIterable() {
        return this.irBuiltIns.getIterableClass();
    }

    public final IrClassSymbol getListIterator() {
        return this.irBuiltIns.getListIteratorClass();
    }

    public final IrClassSymbol getMutableCollection() {
        return this.irBuiltIns.getMutableCollectionClass();
    }

    public final IrClassSymbol getMutableSet() {
        return this.irBuiltIns.getMutableSetClass();
    }

    public final IrClassSymbol getMutableList() {
        return this.irBuiltIns.getMutableListClass();
    }

    public final IrClassSymbol getMutableMap() {
        return this.irBuiltIns.getMutableMapClass();
    }

    public final IrClassSymbol getMutableMapEntry() {
        return this.irBuiltIns.getMutableMapEntryClass();
    }

    public final IrClassSymbol getMutableIterable() {
        return this.irBuiltIns.getMutableIterableClass();
    }

    public final IrClassSymbol getMutableIterator() {
        return this.irBuiltIns.getMutableIteratorClass();
    }

    public final IrClassSymbol getMutableListIterator() {
        return this.irBuiltIns.getMutableListIteratorClass();
    }

    public final IrClassSymbol getComparable() {
        return this.irBuiltIns.getComparableClass();
    }

    public final IrSimpleFunctionSymbol getBinaryOperator(Name name2, IrType lhsType, IrType rhsType) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(lhsType, "lhsType");
        Intrinsics.checkNotNullParameter(rhsType, "rhsType");
        return this.irBuiltIns.getBinaryOperator(name2, lhsType, rhsType);
    }

    public final IrSimpleFunctionSymbol getUnaryOperator(Name name2, IrType receiverType) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(receiverType, "receiverType");
        return this.irBuiltIns.getUnaryOperator(name2, receiverType);
    }

    public IrClassSymbol functionN(int n) {
        return this.irBuiltIns.functionN(n).getSymbol();
    }

    public IrClassSymbol suspendFunctionN(int n) {
        return this.irBuiltIns.suspendFunctionN(n).getSymbol();
    }

    public final IrClassSymbol kproperty0() {
        return this.irBuiltIns.getKProperty0Class();
    }

    public final IrClassSymbol kproperty1() {
        return this.irBuiltIns.getKProperty1Class();
    }

    public final IrClassSymbol kproperty2() {
        return this.irBuiltIns.getKProperty2Class();
    }

    public final IrClassSymbol kmutableproperty0() {
        return this.irBuiltIns.getKMutableProperty0Class();
    }

    public final IrClassSymbol kmutableproperty1() {
        return this.irBuiltIns.getKMutableProperty1Class();
    }

    public final IrClassSymbol kmutableproperty2() {
        return this.irBuiltIns.getKMutableProperty2Class();
    }

    public final IrSimpleFunctionSymbol getExtensionToString() {
        return this.irBuiltIns.getExtensionToString();
    }

    public final IrSimpleFunctionSymbol getMemberToString() {
        return this.irBuiltIns.getMemberToString();
    }

    public final IrSimpleFunctionSymbol getExtensionStringPlus() {
        return this.irBuiltIns.getExtensionStringPlus();
    }

    public final IrSimpleFunctionSymbol getMemberStringPlus() {
        return this.irBuiltIns.getMemberStringPlus();
    }

    public final boolean isStringPlus(IrFunctionSymbol functionSymbol) {
        IrType type;
        IrSimpleFunctionSymbol extensionStringPlus;
        IrType type2;
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        IrValueParameter dispatchReceiverParameter = functionSymbol.getOwner().getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null && (type2 = dispatchReceiverParameter.getType()) != null && IrTypePredicatesKt.isString(type2)) {
            extensionStringPlus = getMemberStringPlus();
        } else {
            IrValueParameter extensionReceiverParameter = functionSymbol.getOwner().getExtensionReceiverParameter();
            if (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || !IrTypePredicatesKt.isNullableString(type)) {
                return false;
            }
            extensionStringPlus = getExtensionStringPlus();
        }
        return Intrinsics.areEqual(functionSymbol, extensionStringPlus);
    }
}
