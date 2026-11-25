package org.jetbrains.kotlin.name;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: StandardClassIds.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000=\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0003\b\u0091\u0001\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\b¿\u0001À\u0001Á\u0001Â\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010µ\u0001\u001a\u00020\u00042\b\u0010¶\u0001\u001a\u00030·\u0001J\u0011\u0010¸\u0001\u001a\u00020\u00042\b\u0010¶\u0001\u001a\u00030·\u0001J\u0011\u0010¹\u0001\u001a\u00020\u00042\b\u0010¶\u0001\u001a\u00030·\u0001J\u0011\u0010º\u0001\u001a\u00020\u00042\b\u0010¶\u0001\u001a\u00030·\u0001J\u0011\u0010»\u0001\u001a\u00020\u00042\b\u0010¼\u0001\u001a\u00030½\u0001J\u0011\u0010¾\u0001\u001a\u00020\u00042\b\u0010¼\u0001\u001a\u00030½\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0015\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0011\u0010\u0017\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0011\u0010\u0019\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u0011\u0010\u001b\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u0011\u0010\u001d\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0012R\u0011\u0010\u001f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0012R\u0011\u0010!\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0012R\u0011\u0010#\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0012R\u0011\u0010%\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0012R\u0011\u0010'\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0012R\u0011\u0010)\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0012R\u0011\u0010+\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0012R\u0011\u0010-\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0006R\u0011\u0010/\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0006R\u0011\u00101\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0006R\u0011\u00103\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0006R\u0011\u00105\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0006R\u0011\u00107\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0006R\u0011\u00109\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\u0006R\u0011\u0010;\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\u0006R\u0011\u0010=\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\u0006R\u0011\u0010?\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\u0006R\u0011\u0010A\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0006R\u0011\u0010C\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\u0006R\u0011\u0010E\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\u0006R\u0011\u0010G\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\u0006R\u0011\u0010I\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\u0006R\u0011\u0010K\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\u0006R\u0011\u0010M\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bN\u0010\u0006R\u0011\u0010O\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010\u0006R\u0011\u0010Q\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010\u0006R\u0011\u0010S\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010\u0006R\u0011\u0010U\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bV\u0010\u0006R\u0011\u0010W\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bX\u0010\u0006R\u0011\u0010Y\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010\u0006R\u0011\u0010[\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010\u0006R\u0011\u0010]\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b^\u0010\u0006R\u0011\u0010_\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010\u0006R\u0011\u0010a\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bb\u0010\u0006R\u0011\u0010c\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bd\u0010\u0006R\u0011\u0010e\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u0010\u0006R\u0011\u0010g\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bh\u0010\u0006R\u0011\u0010i\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bj\u0010\u0006R\u0011\u0010k\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bl\u0010\u0006R\u0011\u0010m\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bn\u0010\u0006R\u0011\u0010o\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bp\u0010\u0006R\u0011\u0010q\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\br\u0010\u0006R\u0011\u0010s\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u0010\u0006R\u0011\u0010u\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bv\u0010\u0006R\u0011\u0010w\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bx\u0010\u0006R\u0011\u0010y\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bz\u0010\u0006R\u0011\u0010{\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b|\u0010\u0006R\u0011\u0010}\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b~\u0010\u0006R\u0012\u0010\u007f\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0001\u0010\u0006R\u0013\u0010\u0081\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0001\u0010\u0006R\u0013\u0010\u0083\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u0006R\u0013\u0010\u0085\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0001\u0010\u0006R\u0013\u0010\u0087\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0001\u0010\u0006R\u0013\u0010\u0089\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0001\u0010\u0006R\u0013\u0010\u008b\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008c\u0001\u0010\u0006R\u0013\u0010\u008d\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0001\u0010\u0006R\u0013\u0010\u008f\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0090\u0001\u0010\u0006R\u0013\u0010\u0091\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0092\u0001\u0010\u0006R\u0013\u0010\u0093\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0094\u0001\u0010\u0006R\u0013\u0010\u0095\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0006R\u0013\u0010\u0097\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0098\u0001\u0010\u0006R\u0013\u0010\u0099\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009a\u0001\u0010\u0006R\u0013\u0010\u009b\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009c\u0001\u0010\u0006R\u0013\u0010\u009d\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009e\u0001\u0010\u0006R\u0013\u0010\u009f\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b \u0001\u0010\u0006R\u001b\u0010¡\u0001\u001a\t\u0012\u0004\u0012\u00020\u00100¢\u0001¢\u0006\n\n\u0000\u001a\u0006\b£\u0001\u0010¤\u0001R\u001b\u0010¥\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040¢\u0001¢\u0006\n\n\u0000\u001a\u0006\b¦\u0001\u0010¤\u0001R!\u0010§\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040¨\u0001¢\u0006\n\n\u0000\u001a\u0006\b©\u0001\u0010ª\u0001R!\u0010«\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040¨\u0001¢\u0006\n\n\u0000\u001a\u0006\b¬\u0001\u0010ª\u0001R!\u0010\u00ad\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040¨\u0001¢\u0006\n\n\u0000\u001a\u0006\b®\u0001\u0010ª\u0001R\u001b\u0010¯\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040¢\u0001¢\u0006\n\n\u0000\u001a\u0006\b°\u0001\u0010¤\u0001R!\u0010±\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040¨\u0001¢\u0006\n\n\u0000\u001a\u0006\b²\u0001\u0010ª\u0001R\u001b\u0010³\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040¢\u0001¢\u0006\n\n\u0000\u001a\u0006\b´\u0001\u0010¤\u0001¨\u0006Ã\u0001"}, d2 = {"Lorg/jetbrains/kotlin/name/StandardClassIds;", "", "()V", "Annotation", "Lorg/jetbrains/kotlin/name/ClassId;", "getAnnotation", "()Lorg/jetbrains/kotlin/name/ClassId;", "AnnotationRetention", "getAnnotationRetention", "AnnotationTarget", "getAnnotationTarget", "Any", "getAny", "Array", "getArray", "BASE_ANNOTATION_PACKAGE", "Lorg/jetbrains/kotlin/name/FqName;", "getBASE_ANNOTATION_PACKAGE", "()Lorg/jetbrains/kotlin/name/FqName;", "BASE_COLLECTIONS_PACKAGE", "getBASE_COLLECTIONS_PACKAGE", "BASE_CONCURRENT_PACKAGE", "getBASE_CONCURRENT_PACKAGE", "BASE_CONTRACTS_PACKAGE", "getBASE_CONTRACTS_PACKAGE", "BASE_COROUTINES_PACKAGE", "getBASE_COROUTINES_PACKAGE", "BASE_ENUMS_PACKAGE", "getBASE_ENUMS_PACKAGE", "BASE_INTERNAL_IR_PACKAGE", "getBASE_INTERNAL_IR_PACKAGE", "BASE_INTERNAL_PACKAGE", "getBASE_INTERNAL_PACKAGE", "BASE_JVM_INTERNAL_PACKAGE", "getBASE_JVM_INTERNAL_PACKAGE", "BASE_JVM_PACKAGE", "getBASE_JVM_PACKAGE", "BASE_KOTLIN_PACKAGE", "getBASE_KOTLIN_PACKAGE", "BASE_RANGES_PACKAGE", "getBASE_RANGES_PACKAGE", "BASE_REFLECT_PACKAGE", "getBASE_REFLECT_PACKAGE", "BASE_TEST_PACKAGE", "getBASE_TEST_PACKAGE", "Boolean", "getBoolean", "Byte", "getByte", "Char", "getChar", "CharIterator", "getCharIterator", "CharRange", "getCharRange", "CharSequence", "getCharSequence", "Cloneable", "getCloneable", "Collection", "getCollection", "Comparable", "getComparable", "Continuation", "getContinuation", "DeprecationLevel", "getDeprecationLevel", "Double", "getDouble", "Enum", "getEnum", "EnumEntries", "getEnumEntries", "Float", "getFloat", "Function", "getFunction", "Int", "getInt", "IntRange", "getIntRange", "Iterable", "getIterable", "Iterator", "getIterator", "KCallable", "getKCallable", "KClass", Namer.GET_KCLASS, StandardNames.K_FUNCTION_PREFIX, "getKFunction", StandardNames.K_MUTABLE_PROPERTY_PREFIX, "getKMutableProperty", "KMutableProperty0", "getKMutableProperty0", "KMutableProperty1", "getKMutableProperty1", "KMutableProperty2", "getKMutableProperty2", StandardNames.K_PROPERTY_PREFIX, "getKProperty", "KProperty0", "getKProperty0", "KProperty1", "getKProperty1", "KProperty2", "getKProperty2", "KType", "getKType", "List", "getList", "ListIterator", "getListIterator", "Long", "getLong", "LongRange", "getLongRange", "Map", "getMap", "MapEntry", "getMapEntry", "MutableCollection", "getMutableCollection", "MutableIterable", "getMutableIterable", "MutableIterator", "getMutableIterator", "MutableList", "getMutableList", "MutableListIterator", "getMutableListIterator", "MutableMap", "getMutableMap", "MutableMapEntry", "getMutableMapEntry", "MutableSet", "getMutableSet", "Nothing", "getNothing", "Number", "getNumber", "Result", "getResult", "Set", "getSet", "Short", "getShort", CommonClassNames.JAVA_LANG_STRING_SHORT, "getString", "Throwable", "getThrowable", "UByte", "getUByte", "UInt", "getUInt", "ULong", "getULong", "UShort", "getUShort", "Unit", "getUnit", "builtInsPackages", "", "getBuiltInsPackages", "()Ljava/util/Set;", "constantAllowedTypes", "getConstantAllowedTypes", "elementTypeByPrimitiveArrayType", "", "getElementTypeByPrimitiveArrayType", "()Ljava/util/Map;", "elementTypeByUnsignedArrayType", "getElementTypeByUnsignedArrayType", "primitiveArrayTypeByElementType", "getPrimitiveArrayTypeByElementType", "primitiveTypes", "getPrimitiveTypes", "unsignedArrayTypeByElementType", "getUnsignedArrayTypeByElementType", "unsignedTypes", "getUnsignedTypes", "FunctionN", "n", "", "KFunctionN", "KSuspendFunctionN", "SuspendFunctionN", "byName", "name", "", "reflectByName", "Annotations", "Callables", "Collections", "Java", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StandardClassIds {
    private static final ClassId Annotation;
    private static final ClassId AnnotationRetention;
    private static final ClassId AnnotationTarget;
    private static final ClassId Any;
    private static final ClassId Array;
    private static final FqName BASE_ANNOTATION_PACKAGE;
    private static final FqName BASE_COLLECTIONS_PACKAGE;
    private static final FqName BASE_CONCURRENT_PACKAGE;
    private static final FqName BASE_CONTRACTS_PACKAGE;
    private static final FqName BASE_COROUTINES_PACKAGE;
    private static final FqName BASE_ENUMS_PACKAGE;
    private static final FqName BASE_INTERNAL_IR_PACKAGE;
    private static final FqName BASE_INTERNAL_PACKAGE;
    private static final FqName BASE_JVM_INTERNAL_PACKAGE;
    private static final FqName BASE_JVM_PACKAGE;
    private static final FqName BASE_KOTLIN_PACKAGE;
    private static final FqName BASE_RANGES_PACKAGE;
    private static final FqName BASE_REFLECT_PACKAGE;
    private static final FqName BASE_TEST_PACKAGE;
    private static final ClassId Boolean;
    private static final ClassId Byte;
    private static final ClassId Char;
    private static final ClassId CharIterator;
    private static final ClassId CharRange;
    private static final ClassId CharSequence;
    private static final ClassId Cloneable;
    private static final ClassId Collection;
    private static final ClassId Comparable;
    private static final ClassId Continuation;
    private static final ClassId DeprecationLevel;
    private static final ClassId Double;
    private static final ClassId Enum;
    private static final ClassId EnumEntries;
    private static final ClassId Float;
    private static final ClassId Function;
    public static final StandardClassIds INSTANCE = new StandardClassIds();
    private static final ClassId Int;
    private static final ClassId IntRange;
    private static final ClassId Iterable;
    private static final ClassId Iterator;
    private static final ClassId KCallable;
    private static final ClassId KClass;
    private static final ClassId KFunction;
    private static final ClassId KMutableProperty;
    private static final ClassId KMutableProperty0;
    private static final ClassId KMutableProperty1;
    private static final ClassId KMutableProperty2;
    private static final ClassId KProperty;
    private static final ClassId KProperty0;
    private static final ClassId KProperty1;
    private static final ClassId KProperty2;
    private static final ClassId KType;
    private static final ClassId List;
    private static final ClassId ListIterator;
    private static final ClassId Long;
    private static final ClassId LongRange;
    private static final ClassId Map;
    private static final ClassId MapEntry;
    private static final ClassId MutableCollection;
    private static final ClassId MutableIterable;
    private static final ClassId MutableIterator;
    private static final ClassId MutableList;
    private static final ClassId MutableListIterator;
    private static final ClassId MutableMap;
    private static final ClassId MutableMapEntry;
    private static final ClassId MutableSet;
    private static final ClassId Nothing;
    private static final ClassId Number;
    private static final ClassId Result;
    private static final ClassId Set;
    private static final ClassId Short;
    private static final ClassId String;
    private static final ClassId Throwable;
    private static final ClassId UByte;
    private static final ClassId UInt;
    private static final ClassId ULong;
    private static final ClassId UShort;
    private static final ClassId Unit;
    private static final Set<FqName> builtInsPackages;
    private static final Set<ClassId> constantAllowedTypes;
    private static final Map<ClassId, ClassId> elementTypeByPrimitiveArrayType;
    private static final Map<ClassId, ClassId> elementTypeByUnsignedArrayType;
    private static final Map<ClassId, ClassId> primitiveArrayTypeByElementType;
    private static final Set<ClassId> primitiveTypes;
    private static final Map<ClassId, ClassId> unsignedArrayTypeByElementType;
    private static final Set<ClassId> unsignedTypes;

    private StandardClassIds() {
    }

    static {
        FqName fqName = new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        BASE_KOTLIN_PACKAGE = fqName;
        FqName fqNameChild = fqName.child(Name.identifier("reflect"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        BASE_REFLECT_PACKAGE = fqNameChild;
        FqName fqNameChild2 = fqName.child(Name.identifier("collections"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        BASE_COLLECTIONS_PACKAGE = fqNameChild2;
        FqName fqNameChild3 = fqName.child(Name.identifier("ranges"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
        BASE_RANGES_PACKAGE = fqNameChild3;
        FqName fqNameChild4 = fqName.child(Name.identifier("jvm"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild4, "child(...)");
        BASE_JVM_PACKAGE = fqNameChild4;
        FqName fqNameChild5 = fqNameChild4.child(Name.identifier("internal"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild5, "child(...)");
        BASE_JVM_INTERNAL_PACKAGE = fqNameChild5;
        FqName fqNameChild6 = fqName.child(Name.identifier("annotation"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild6, "child(...)");
        BASE_ANNOTATION_PACKAGE = fqNameChild6;
        FqName fqNameChild7 = fqName.child(Name.identifier("internal"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild7, "child(...)");
        BASE_INTERNAL_PACKAGE = fqNameChild7;
        FqName fqNameChild8 = fqNameChild7.child(Name.identifier(KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME));
        Intrinsics.checkNotNullExpressionValue(fqNameChild8, "child(...)");
        BASE_INTERNAL_IR_PACKAGE = fqNameChild8;
        FqName fqNameChild9 = fqName.child(Name.identifier("coroutines"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild9, "child(...)");
        BASE_COROUTINES_PACKAGE = fqNameChild9;
        FqName fqNameChild10 = fqName.child(Name.identifier("enums"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild10, "child(...)");
        BASE_ENUMS_PACKAGE = fqNameChild10;
        FqName fqNameChild11 = fqName.child(Name.identifier("contracts"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild11, "child(...)");
        BASE_CONTRACTS_PACKAGE = fqNameChild11;
        FqName fqNameChild12 = fqName.child(Name.identifier("concurrent"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild12, "child(...)");
        BASE_CONCURRENT_PACKAGE = fqNameChild12;
        FqName fqNameChild13 = fqName.child(Name.identifier("test"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild13, "child(...)");
        BASE_TEST_PACKAGE = fqNameChild13;
        builtInsPackages = SetsKt.setOf((Object[]) new FqName[]{fqName, fqNameChild2, fqNameChild3, fqNameChild6, fqNameChild, fqNameChild7, fqNameChild9});
        Nothing = StandardClassIdsKt.baseId("Nothing");
        Unit = StandardClassIdsKt.baseId("Unit");
        Any = StandardClassIdsKt.baseId("Any");
        Enum = StandardClassIdsKt.baseId("Enum");
        Annotation = StandardClassIdsKt.baseId("Annotation");
        Array = StandardClassIdsKt.baseId("Array");
        ClassId classIdBaseId = StandardClassIdsKt.baseId("Boolean");
        Boolean = classIdBaseId;
        ClassId classIdBaseId2 = StandardClassIdsKt.baseId("Char");
        Char = classIdBaseId2;
        ClassId classIdBaseId3 = StandardClassIdsKt.baseId("Byte");
        Byte = classIdBaseId3;
        ClassId classIdBaseId4 = StandardClassIdsKt.baseId("Short");
        Short = classIdBaseId4;
        ClassId classIdBaseId5 = StandardClassIdsKt.baseId("Int");
        Int = classIdBaseId5;
        ClassId classIdBaseId6 = StandardClassIdsKt.baseId("Long");
        Long = classIdBaseId6;
        ClassId classIdBaseId7 = StandardClassIdsKt.baseId("Float");
        Float = classIdBaseId7;
        ClassId classIdBaseId8 = StandardClassIdsKt.baseId("Double");
        Double = classIdBaseId8;
        UByte = StandardClassIdsKt.unsignedId(classIdBaseId3);
        UShort = StandardClassIdsKt.unsignedId(classIdBaseId4);
        UInt = StandardClassIdsKt.unsignedId(classIdBaseId5);
        ULong = StandardClassIdsKt.unsignedId(classIdBaseId6);
        CharSequence = StandardClassIdsKt.baseId("CharSequence");
        String = StandardClassIdsKt.baseId(CommonClassNames.JAVA_LANG_STRING_SHORT);
        Throwable = StandardClassIdsKt.baseId("Throwable");
        Cloneable = StandardClassIdsKt.baseId("Cloneable");
        KProperty = StandardClassIdsKt.reflectId(StandardNames.K_PROPERTY_PREFIX);
        KMutableProperty = StandardClassIdsKt.reflectId(StandardNames.K_MUTABLE_PROPERTY_PREFIX);
        KProperty0 = StandardClassIdsKt.reflectId("KProperty0");
        KMutableProperty0 = StandardClassIdsKt.reflectId("KMutableProperty0");
        KProperty1 = StandardClassIdsKt.reflectId("KProperty1");
        KMutableProperty1 = StandardClassIdsKt.reflectId("KMutableProperty1");
        KProperty2 = StandardClassIdsKt.reflectId("KProperty2");
        KMutableProperty2 = StandardClassIdsKt.reflectId("KMutableProperty2");
        KFunction = StandardClassIdsKt.reflectId(StandardNames.K_FUNCTION_PREFIX);
        KClass = StandardClassIdsKt.reflectId("KClass");
        KCallable = StandardClassIdsKt.reflectId("KCallable");
        KType = StandardClassIdsKt.reflectId("KType");
        Comparable = StandardClassIdsKt.baseId("Comparable");
        Number = StandardClassIdsKt.baseId("Number");
        Function = StandardClassIdsKt.baseId("Function");
        Set<ClassId> of = SetsKt.setOf((Object[]) new ClassId[]{classIdBaseId, classIdBaseId2, classIdBaseId3, classIdBaseId4, classIdBaseId5, classIdBaseId6, classIdBaseId7, classIdBaseId8});
        primitiveTypes = of;
        Set<ClassId> set = of;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
        for (Object obj : set) {
            Name shortClassName = ((ClassId) obj).getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            linkedHashMap.put(obj, StandardClassIdsKt.primitiveArrayId(shortClassName));
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        primitiveArrayTypeByElementType = linkedHashMap2;
        elementTypeByPrimitiveArrayType = StandardClassIdsKt.inverseMap(linkedHashMap2);
        Set<ClassId> of2 = SetsKt.setOf((Object[]) new ClassId[]{UByte, UShort, UInt, ULong});
        unsignedTypes = of2;
        Set<ClassId> set2 = of2;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set2, 10)), 16));
        for (Object obj2 : set2) {
            Name shortClassName2 = ((ClassId) obj2).getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
            linkedHashMap3.put(obj2, StandardClassIdsKt.primitiveArrayId(shortClassName2));
        }
        LinkedHashMap linkedHashMap4 = linkedHashMap3;
        unsignedArrayTypeByElementType = linkedHashMap4;
        elementTypeByUnsignedArrayType = StandardClassIdsKt.inverseMap(linkedHashMap4);
        constantAllowedTypes = SetsKt.plus((Set<? extends ClassId>) SetsKt.plus((Set) primitiveTypes, (Iterable) unsignedTypes), String);
        Continuation = StandardClassIdsKt.coroutinesId("Continuation");
        Iterator = StandardClassIdsKt.collectionsId("Iterator");
        Iterable = StandardClassIdsKt.collectionsId("Iterable");
        Collection = StandardClassIdsKt.collectionsId("Collection");
        List = StandardClassIdsKt.collectionsId("List");
        ListIterator = StandardClassIdsKt.collectionsId("ListIterator");
        Set = StandardClassIdsKt.collectionsId("Set");
        ClassId classIdCollectionsId = StandardClassIdsKt.collectionsId("Map");
        Map = classIdCollectionsId;
        MutableIterator = StandardClassIdsKt.collectionsId("MutableIterator");
        CharIterator = StandardClassIdsKt.collectionsId("CharIterator");
        MutableIterable = StandardClassIdsKt.collectionsId("MutableIterable");
        MutableCollection = StandardClassIdsKt.collectionsId("MutableCollection");
        MutableList = StandardClassIdsKt.collectionsId("MutableList");
        MutableListIterator = StandardClassIdsKt.collectionsId("MutableListIterator");
        MutableSet = StandardClassIdsKt.collectionsId("MutableSet");
        ClassId classIdCollectionsId2 = StandardClassIdsKt.collectionsId("MutableMap");
        MutableMap = classIdCollectionsId2;
        ClassId classIdCreateNestedClassId = classIdCollectionsId.createNestedClassId(Name.identifier("Entry"));
        Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
        MapEntry = classIdCreateNestedClassId;
        ClassId classIdCreateNestedClassId2 = classIdCollectionsId2.createNestedClassId(Name.identifier("MutableEntry"));
        Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId2, "createNestedClassId(...)");
        MutableMapEntry = classIdCreateNestedClassId2;
        Result = StandardClassIdsKt.baseId("Result");
        IntRange = StandardClassIdsKt.rangesId("IntRange");
        LongRange = StandardClassIdsKt.rangesId("LongRange");
        CharRange = StandardClassIdsKt.rangesId("CharRange");
        AnnotationRetention = StandardClassIdsKt.annotationId("AnnotationRetention");
        AnnotationTarget = StandardClassIdsKt.annotationId("AnnotationTarget");
        DeprecationLevel = StandardClassIdsKt.baseId("DeprecationLevel");
        EnumEntries = StandardClassIdsKt.enumsId("EnumEntries");
    }

    public final FqName getBASE_KOTLIN_PACKAGE() {
        return BASE_KOTLIN_PACKAGE;
    }

    public final FqName getBASE_REFLECT_PACKAGE() {
        return BASE_REFLECT_PACKAGE;
    }

    public final FqName getBASE_COLLECTIONS_PACKAGE() {
        return BASE_COLLECTIONS_PACKAGE;
    }

    public final FqName getBASE_RANGES_PACKAGE() {
        return BASE_RANGES_PACKAGE;
    }

    public final FqName getBASE_JVM_PACKAGE() {
        return BASE_JVM_PACKAGE;
    }

    public final FqName getBASE_JVM_INTERNAL_PACKAGE() {
        return BASE_JVM_INTERNAL_PACKAGE;
    }

    public final FqName getBASE_ANNOTATION_PACKAGE() {
        return BASE_ANNOTATION_PACKAGE;
    }

    public final FqName getBASE_INTERNAL_PACKAGE() {
        return BASE_INTERNAL_PACKAGE;
    }

    public final FqName getBASE_INTERNAL_IR_PACKAGE() {
        return BASE_INTERNAL_IR_PACKAGE;
    }

    public final FqName getBASE_COROUTINES_PACKAGE() {
        return BASE_COROUTINES_PACKAGE;
    }

    public final FqName getBASE_ENUMS_PACKAGE() {
        return BASE_ENUMS_PACKAGE;
    }

    public final FqName getBASE_CONTRACTS_PACKAGE() {
        return BASE_CONTRACTS_PACKAGE;
    }

    public final FqName getBASE_CONCURRENT_PACKAGE() {
        return BASE_CONCURRENT_PACKAGE;
    }

    public final FqName getBASE_TEST_PACKAGE() {
        return BASE_TEST_PACKAGE;
    }

    public final Set<FqName> getBuiltInsPackages() {
        return builtInsPackages;
    }

    public final ClassId getNothing() {
        return Nothing;
    }

    public final ClassId getUnit() {
        return Unit;
    }

    public final ClassId getAny() {
        return Any;
    }

    public final ClassId getEnum() {
        return Enum;
    }

    public final ClassId getAnnotation() {
        return Annotation;
    }

    public final ClassId getArray() {
        return Array;
    }

    public final ClassId getBoolean() {
        return Boolean;
    }

    public final ClassId getChar() {
        return Char;
    }

    public final ClassId getByte() {
        return Byte;
    }

    public final ClassId getShort() {
        return Short;
    }

    public final ClassId getInt() {
        return Int;
    }

    public final ClassId getLong() {
        return Long;
    }

    public final ClassId getFloat() {
        return Float;
    }

    public final ClassId getDouble() {
        return Double;
    }

    public final ClassId getUByte() {
        return UByte;
    }

    public final ClassId getUShort() {
        return UShort;
    }

    public final ClassId getUInt() {
        return UInt;
    }

    public final ClassId getULong() {
        return ULong;
    }

    public final ClassId getCharSequence() {
        return CharSequence;
    }

    public final ClassId getString() {
        return String;
    }

    public final ClassId getThrowable() {
        return Throwable;
    }

    public final ClassId getCloneable() {
        return Cloneable;
    }

    public final ClassId getKProperty() {
        return KProperty;
    }

    public final ClassId getKMutableProperty() {
        return KMutableProperty;
    }

    public final ClassId getKProperty0() {
        return KProperty0;
    }

    public final ClassId getKMutableProperty0() {
        return KMutableProperty0;
    }

    public final ClassId getKProperty1() {
        return KProperty1;
    }

    public final ClassId getKMutableProperty1() {
        return KMutableProperty1;
    }

    public final ClassId getKProperty2() {
        return KProperty2;
    }

    public final ClassId getKMutableProperty2() {
        return KMutableProperty2;
    }

    public final ClassId getKFunction() {
        return KFunction;
    }

    public final ClassId getKClass() {
        return KClass;
    }

    public final ClassId getKCallable() {
        return KCallable;
    }

    public final ClassId getKType() {
        return KType;
    }

    public final ClassId getComparable() {
        return Comparable;
    }

    public final ClassId getNumber() {
        return Number;
    }

    public final ClassId getFunction() {
        return Function;
    }

    public final ClassId byName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return StandardClassIdsKt.baseId(name2);
    }

    public final ClassId reflectByName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return StandardClassIdsKt.reflectId(name2);
    }

    public final Set<ClassId> getPrimitiveTypes() {
        return primitiveTypes;
    }

    public final Map<ClassId, ClassId> getPrimitiveArrayTypeByElementType() {
        return primitiveArrayTypeByElementType;
    }

    public final Map<ClassId, ClassId> getElementTypeByPrimitiveArrayType() {
        return elementTypeByPrimitiveArrayType;
    }

    public final Set<ClassId> getUnsignedTypes() {
        return unsignedTypes;
    }

    public final Map<ClassId, ClassId> getUnsignedArrayTypeByElementType() {
        return unsignedArrayTypeByElementType;
    }

    public final Map<ClassId, ClassId> getElementTypeByUnsignedArrayType() {
        return elementTypeByUnsignedArrayType;
    }

    public final Set<ClassId> getConstantAllowedTypes() {
        return constantAllowedTypes;
    }

    public final ClassId getContinuation() {
        return Continuation;
    }

    public final ClassId FunctionN(int n) {
        return StandardClassIdsKt.baseId("Function" + n);
    }

    public final ClassId SuspendFunctionN(int n) {
        return StandardClassIdsKt.coroutinesId("SuspendFunction" + n);
    }

    public final ClassId KFunctionN(int n) {
        return StandardClassIdsKt.reflectId(StandardNames.K_FUNCTION_PREFIX + n);
    }

    public final ClassId KSuspendFunctionN(int n) {
        return StandardClassIdsKt.reflectId(StandardNames.K_SUSPEND_FUNCTION_PREFIX + n);
    }

    public final ClassId getIterator() {
        return Iterator;
    }

    public final ClassId getIterable() {
        return Iterable;
    }

    public final ClassId getCollection() {
        return Collection;
    }

    public final ClassId getList() {
        return List;
    }

    public final ClassId getListIterator() {
        return ListIterator;
    }

    public final ClassId getSet() {
        return Set;
    }

    public final ClassId getMap() {
        return Map;
    }

    public final ClassId getMutableIterator() {
        return MutableIterator;
    }

    public final ClassId getCharIterator() {
        return CharIterator;
    }

    public final ClassId getMutableIterable() {
        return MutableIterable;
    }

    public final ClassId getMutableCollection() {
        return MutableCollection;
    }

    public final ClassId getMutableList() {
        return MutableList;
    }

    public final ClassId getMutableListIterator() {
        return MutableListIterator;
    }

    public final ClassId getMutableSet() {
        return MutableSet;
    }

    public final ClassId getMutableMap() {
        return MutableMap;
    }

    public final ClassId getMapEntry() {
        return MapEntry;
    }

    public final ClassId getMutableMapEntry() {
        return MutableMapEntry;
    }

    public final ClassId getResult() {
        return Result;
    }

    public final ClassId getIntRange() {
        return IntRange;
    }

    public final ClassId getLongRange() {
        return LongRange;
    }

    public final ClassId getCharRange() {
        return CharRange;
    }

    public final ClassId getAnnotationRetention() {
        return AnnotationRetention;
    }

    public final ClassId getAnnotationTarget() {
        return AnnotationTarget;
    }

    public final ClassId getDeprecationLevel() {
        return DeprecationLevel;
    }

    public final ClassId getEnumEntries() {
        return EnumEntries;
    }

    /* JADX INFO: compiled from: StandardClassIds.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\bK\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002MNB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006R\u0011\u0010)\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0006R\u0011\u0010/\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0006R\u0011\u00101\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0006R\u0011\u00103\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0006R\u0011\u00105\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0006R\u0011\u00107\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0006R\u0011\u00109\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\u0006R\u0011\u0010;\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\u0006R\u0011\u0010=\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\u0006R\u0011\u0010?\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\u0006R\u0011\u0010A\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0006R\u0011\u0010C\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\u0006R\u0011\u0010E\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\u0006R\u0011\u0010G\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\u0006R\u0011\u0010I\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\u0006R\u0011\u0010K\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\u0006¨\u0006O"}, d2 = {"Lorg/jetbrains/kotlin/name/StandardClassIds$Annotations;", "", "()V", "AccessibleLateinitPropertyLiteral", "Lorg/jetbrains/kotlin/name/ClassId;", "getAccessibleLateinitPropertyLiteral", "()Lorg/jetbrains/kotlin/name/ClassId;", "ContextFunctionTypeParams", "getContextFunctionTypeParams", "Deprecated", "getDeprecated", "DeprecatedSinceKotlin", "getDeprecatedSinceKotlin", "DynamicExtension", "getDynamicExtension", "EnhancedNullability", "getEnhancedNullability", "ExtensionFunctionType", "getExtensionFunctionType", "FlexibleMutability", "getFlexibleMutability", "FlexibleNullability", "getFlexibleNullability", "HidesMembers", "getHidesMembers", "ImplicitlyActualizedByJvmDeclaration", "getImplicitlyActualizedByJvmDeclaration", "InlineOnly", "getInlineOnly", "IntrinsicConstEvaluation", "getIntrinsicConstEvaluation", "JvmDefault", "getJvmDefault", "JvmField", "getJvmField", "JvmName", "getJvmName", "JvmRecord", "getJvmRecord", "JvmRepeatable", "getJvmRepeatable", "JvmStatic", "getJvmStatic", "JvmVolatile", "getJvmVolatile", "MustBeDocumented", "getMustBeDocumented", "OnlyInputTypes", "getOnlyInputTypes", "OptionalExpectation", "getOptionalExpectation", "PublishedApi", "getPublishedApi", "RawTypeAnnotation", "getRawTypeAnnotation", "Repeatable", "getRepeatable", "RequireKotlin", "getRequireKotlin", "RestrictsSuspension", "getRestrictsSuspension", "Retention", "getRetention", "SinceKotlin", "getSinceKotlin", "Suppress", "getSuppress", "Target", "getTarget", "Test", "getTest", "Throws", "getThrows", "Volatile", "getVolatile", "WasExperimental", "getWasExperimental", "Java", "ParameterNames", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Annotations {
        public static final Annotations INSTANCE = new Annotations();
        private static final ClassId Suppress = StandardClassIdsKt.baseId("Suppress");
        private static final ClassId PublishedApi = StandardClassIdsKt.baseId("PublishedApi");
        private static final ClassId SinceKotlin = StandardClassIdsKt.baseId("SinceKotlin");
        private static final ClassId ExtensionFunctionType = StandardClassIdsKt.baseId("ExtensionFunctionType");
        private static final ClassId ContextFunctionTypeParams = StandardClassIdsKt.baseId("ContextFunctionTypeParams");
        private static final ClassId Deprecated = StandardClassIdsKt.baseId("Deprecated");
        private static final ClassId DeprecatedSinceKotlin = StandardClassIdsKt.baseId("DeprecatedSinceKotlin");
        private static final ClassId RequireKotlin = StandardClassIdsKt.internalId("RequireKotlin");
        private static final ClassId HidesMembers = StandardClassIdsKt.internalId("HidesMembers");
        private static final ClassId DynamicExtension = StandardClassIdsKt.internalId("DynamicExtension");
        private static final ClassId IntrinsicConstEvaluation = StandardClassIdsKt.internalId("IntrinsicConstEvaluation");
        private static final ClassId Retention = StandardClassIdsKt.annotationId("Retention");
        private static final ClassId Target = StandardClassIdsKt.annotationId("Target");
        private static final ClassId Repeatable = StandardClassIdsKt.annotationId("Repeatable");
        private static final ClassId MustBeDocumented = StandardClassIdsKt.annotationId("MustBeDocumented");
        private static final ClassId JvmStatic = StandardClassIdsKt.jvmId("JvmStatic");
        private static final ClassId JvmName = StandardClassIdsKt.jvmId("JvmName");
        private static final ClassId JvmField = StandardClassIdsKt.jvmId("JvmField");
        private static final ClassId JvmDefault = StandardClassIdsKt.jvmId("JvmDefault");
        private static final ClassId JvmRepeatable = StandardClassIdsKt.jvmId("JvmRepeatable");
        private static final ClassId JvmRecord = StandardClassIdsKt.jvmId("JvmRecord");
        private static final ClassId JvmVolatile = StandardClassIdsKt.jvmId("Volatile");
        private static final ClassId Throws = StandardClassIdsKt.jvmId("Throws");
        private static final ClassId Volatile = StandardClassIdsKt.concurrentId("Volatile");
        private static final ClassId Test = StandardClassIdsKt.testId("Test");
        private static final ClassId RawTypeAnnotation = StandardClassIdsKt.internalIrId("RawType");
        private static final ClassId FlexibleNullability = StandardClassIdsKt.internalIrId("FlexibleNullability");
        private static final ClassId FlexibleMutability = StandardClassIdsKt.internalIrId("FlexibleMutability");
        private static final ClassId EnhancedNullability = StandardClassIdsKt.jvmInternalId("EnhancedNullability");
        private static final ClassId InlineOnly = StandardClassIdsKt.internalId("InlineOnly");
        private static final ClassId OnlyInputTypes = StandardClassIdsKt.internalId("OnlyInputTypes");
        private static final ClassId RestrictsSuspension = StandardClassIdsKt.coroutinesId("RestrictsSuspension");
        private static final ClassId WasExperimental = StandardClassIdsKt.baseId("WasExperimental");
        private static final ClassId AccessibleLateinitPropertyLiteral = StandardClassIdsKt.internalId("AccessibleLateinitPropertyLiteral");
        private static final ClassId OptionalExpectation = StandardClassIdsKt.baseId("OptionalExpectation");
        private static final ClassId ImplicitlyActualizedByJvmDeclaration = StandardClassIdsKt.jvmId("ImplicitlyActualizedByJvmDeclaration");

        private Annotations() {
        }

        public final ClassId getSuppress() {
            return Suppress;
        }

        public final ClassId getPublishedApi() {
            return PublishedApi;
        }

        public final ClassId getSinceKotlin() {
            return SinceKotlin;
        }

        public final ClassId getExtensionFunctionType() {
            return ExtensionFunctionType;
        }

        public final ClassId getContextFunctionTypeParams() {
            return ContextFunctionTypeParams;
        }

        public final ClassId getDeprecated() {
            return Deprecated;
        }

        public final ClassId getDeprecatedSinceKotlin() {
            return DeprecatedSinceKotlin;
        }

        public final ClassId getRequireKotlin() {
            return RequireKotlin;
        }

        public final ClassId getHidesMembers() {
            return HidesMembers;
        }

        public final ClassId getDynamicExtension() {
            return DynamicExtension;
        }

        public final ClassId getIntrinsicConstEvaluation() {
            return IntrinsicConstEvaluation;
        }

        public final ClassId getRetention() {
            return Retention;
        }

        public final ClassId getTarget() {
            return Target;
        }

        public final ClassId getRepeatable() {
            return Repeatable;
        }

        public final ClassId getMustBeDocumented() {
            return MustBeDocumented;
        }

        public final ClassId getJvmStatic() {
            return JvmStatic;
        }

        public final ClassId getJvmName() {
            return JvmName;
        }

        public final ClassId getJvmField() {
            return JvmField;
        }

        public final ClassId getJvmDefault() {
            return JvmDefault;
        }

        public final ClassId getJvmRepeatable() {
            return JvmRepeatable;
        }

        public final ClassId getJvmRecord() {
            return JvmRecord;
        }

        public final ClassId getJvmVolatile() {
            return JvmVolatile;
        }

        public final ClassId getThrows() {
            return Throws;
        }

        public final ClassId getVolatile() {
            return Volatile;
        }

        public final ClassId getTest() {
            return Test;
        }

        public final ClassId getRawTypeAnnotation() {
            return RawTypeAnnotation;
        }

        public final ClassId getFlexibleNullability() {
            return FlexibleNullability;
        }

        public final ClassId getFlexibleMutability() {
            return FlexibleMutability;
        }

        public final ClassId getEnhancedNullability() {
            return EnhancedNullability;
        }

        public final ClassId getInlineOnly() {
            return InlineOnly;
        }

        public final ClassId getOnlyInputTypes() {
            return OnlyInputTypes;
        }

        public final ClassId getRestrictsSuspension() {
            return RestrictsSuspension;
        }

        public final ClassId getWasExperimental() {
            return WasExperimental;
        }

        public final ClassId getAccessibleLateinitPropertyLiteral() {
            return AccessibleLateinitPropertyLiteral;
        }

        public final ClassId getOptionalExpectation() {
            return OptionalExpectation;
        }

        public final ClassId getImplicitlyActualizedByJvmDeclaration() {
            return ImplicitlyActualizedByJvmDeclaration;
        }

        /* JADX INFO: compiled from: StandardClassIds.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/name/StandardClassIds$Annotations$Java;", "", "()V", "Deprecated", "Lorg/jetbrains/kotlin/name/ClassId;", "getDeprecated", "()Lorg/jetbrains/kotlin/name/ClassId;", "Documented", "getDocumented", "ElementType", "getElementType", "Repeatable", "getRepeatable", "Retention", "getRetention", "RetentionPolicy", "getRetentionPolicy", "Target", "getTarget", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Java {
            public static final Java INSTANCE = new Java();
            private static final ClassId Deprecated = StandardClassIdsKt.javaLangId("Deprecated");
            private static final ClassId Repeatable = StandardClassIdsKt.javaAnnotationId("Repeatable");
            private static final ClassId Retention = StandardClassIdsKt.javaAnnotationId("Retention");
            private static final ClassId Documented = StandardClassIdsKt.javaAnnotationId("Documented");
            private static final ClassId Target = StandardClassIdsKt.javaAnnotationId("Target");
            private static final ClassId ElementType = StandardClassIdsKt.javaAnnotationId("ElementType");
            private static final ClassId RetentionPolicy = StandardClassIdsKt.javaAnnotationId("RetentionPolicy");

            private Java() {
            }

            public final ClassId getDeprecated() {
                return Deprecated;
            }

            public final ClassId getRepeatable() {
                return Repeatable;
            }

            public final ClassId getRetention() {
                return Retention;
            }

            public final ClassId getDocumented() {
                return Documented;
            }

            public final ClassId getTarget() {
                return Target;
            }

            public final ClassId getElementType() {
                return ElementType;
            }

            public final ClassId getRetentionPolicy() {
                return RetentionPolicy;
            }
        }

        /* JADX INFO: compiled from: StandardClassIds.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/name/StandardClassIds$Annotations$ParameterNames;", "", "()V", "deprecatedLevel", "Lorg/jetbrains/kotlin/name/Name;", "getDeprecatedLevel", "()Lorg/jetbrains/kotlin/name/Name;", "deprecatedMessage", "getDeprecatedMessage", "deprecatedSinceKotlinErrorSince", "getDeprecatedSinceKotlinErrorSince", "deprecatedSinceKotlinHiddenSince", "getDeprecatedSinceKotlinHiddenSince", "deprecatedSinceKotlinWarningSince", "getDeprecatedSinceKotlinWarningSince", "parameterNameName", "getParameterNameName", "retentionValue", "getRetentionValue", "sinceKotlinVersion", "getSinceKotlinVersion", "targetAllowedTargets", "getTargetAllowedTargets", "value", "getValue", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ParameterNames {
            public static final ParameterNames INSTANCE = new ParameterNames();
            private static final Name deprecatedLevel;
            private static final Name deprecatedMessage;
            private static final Name deprecatedSinceKotlinErrorSince;
            private static final Name deprecatedSinceKotlinHiddenSince;
            private static final Name deprecatedSinceKotlinWarningSince;
            private static final Name parameterNameName;
            private static final Name retentionValue;
            private static final Name sinceKotlinVersion;
            private static final Name targetAllowedTargets;
            private static final Name value;

            private ParameterNames() {
            }

            static {
                Name nameIdentifier = Name.identifier("value");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                value = nameIdentifier;
                retentionValue = nameIdentifier;
                Name nameIdentifier2 = Name.identifier("allowedTargets");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                targetAllowedTargets = nameIdentifier2;
                Name nameIdentifier3 = Name.identifier("version");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
                sinceKotlinVersion = nameIdentifier3;
                Name nameIdentifier4 = Name.identifier(CrashHianalyticsData.MESSAGE);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
                deprecatedMessage = nameIdentifier4;
                Name nameIdentifier5 = Name.identifier(FirebaseAnalytics.Param.LEVEL);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
                deprecatedLevel = nameIdentifier5;
                Name nameIdentifier6 = Name.identifier("warningSince");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
                deprecatedSinceKotlinWarningSince = nameIdentifier6;
                Name nameIdentifier7 = Name.identifier("errorSince");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
                deprecatedSinceKotlinErrorSince = nameIdentifier7;
                Name nameIdentifier8 = Name.identifier("hiddenSince");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier8, "identifier(...)");
                deprecatedSinceKotlinHiddenSince = nameIdentifier8;
                parameterNameName = StandardNames.NAME;
            }

            public final Name getValue() {
                return value;
            }

            public final Name getRetentionValue() {
                return retentionValue;
            }

            public final Name getTargetAllowedTargets() {
                return targetAllowedTargets;
            }

            public final Name getSinceKotlinVersion() {
                return sinceKotlinVersion;
            }

            public final Name getDeprecatedMessage() {
                return deprecatedMessage;
            }

            public final Name getDeprecatedLevel() {
                return deprecatedLevel;
            }

            public final Name getDeprecatedSinceKotlinWarningSince() {
                return deprecatedSinceKotlinWarningSince;
            }

            public final Name getDeprecatedSinceKotlinErrorSince() {
                return deprecatedSinceKotlinErrorSince;
            }

            public final Name getDeprecatedSinceKotlinHiddenSince() {
                return deprecatedSinceKotlinHiddenSince;
            }

            public final Name getParameterNameName() {
                return parameterNameName;
            }
        }
    }

    /* JADX INFO: compiled from: StandardClassIds.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/name/StandardClassIds$Callables;", "", "()V", "clone", "Lorg/jetbrains/kotlin/name/CallableId;", "getClone", "()Lorg/jetbrains/kotlin/name/CallableId;", "contract", "getContract", "coroutineContext", "getCoroutineContext", "not", "getNot", "suspend", "getSuspend", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Callables {
        public static final Callables INSTANCE = new Callables();
        private static final CallableId suspend = StandardClassIdsKt.callableId("suspend", StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE());
        private static final CallableId coroutineContext = StandardClassIdsKt.callableId("coroutineContext", StandardClassIds.INSTANCE.getBASE_COROUTINES_PACKAGE());
        private static final CallableId clone = StandardClassIdsKt.callableId("clone", StandardClassIds.INSTANCE.getCloneable());
        private static final CallableId not = StandardClassIdsKt.callableId("not", StandardClassIds.INSTANCE.getBoolean());
        private static final CallableId contract = StandardClassIdsKt.callableId("contract", StandardClassIds.INSTANCE.getBASE_CONTRACTS_PACKAGE());

        private Callables() {
        }

        public final CallableId getSuspend() {
            return suspend;
        }

        public final CallableId getCoroutineContext() {
            return coroutineContext;
        }

        public final CallableId getClone() {
            return clone;
        }

        public final CallableId getNot() {
            return not;
        }

        public final CallableId getContract() {
            return contract;
        }
    }

    /* JADX INFO: compiled from: StandardClassIds.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/name/StandardClassIds$Java;", "", "()V", "Record", "Lorg/jetbrains/kotlin/name/ClassId;", "getRecord", "()Lorg/jetbrains/kotlin/name/ClassId;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Java {
        public static final Java INSTANCE = new Java();
        private static final ClassId Record = StandardClassIdsKt.javaLangId("Record");

        private Java() {
        }

        public final ClassId getRecord() {
            return Record;
        }
    }

    /* JADX INFO: compiled from: StandardClassIds.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/name/StandardClassIds$Collections;", "", "()V", "baseCollectionToMutableEquivalent", "", "Lorg/jetbrains/kotlin/name/ClassId;", "getBaseCollectionToMutableEquivalent", "()Ljava/util/Map;", "mutableCollectionToBaseCollection", "getMutableCollectionToBaseCollection", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Collections {
        public static final Collections INSTANCE = new Collections();
        private static final Map<ClassId, ClassId> baseCollectionToMutableEquivalent;
        private static final Map<ClassId, ClassId> mutableCollectionToBaseCollection;

        private Collections() {
        }

        public final Map<ClassId, ClassId> getBaseCollectionToMutableEquivalent() {
            return baseCollectionToMutableEquivalent;
        }

        static {
            Map<ClassId, ClassId> mapMapOf = MapsKt.mapOf(TuplesKt.to(StandardClassIds.INSTANCE.getIterable(), StandardClassIds.INSTANCE.getMutableIterable()), TuplesKt.to(StandardClassIds.INSTANCE.getIterator(), StandardClassIds.INSTANCE.getMutableIterator()), TuplesKt.to(StandardClassIds.INSTANCE.getListIterator(), StandardClassIds.INSTANCE.getMutableListIterator()), TuplesKt.to(StandardClassIds.INSTANCE.getList(), StandardClassIds.INSTANCE.getMutableList()), TuplesKt.to(StandardClassIds.INSTANCE.getCollection(), StandardClassIds.INSTANCE.getMutableCollection()), TuplesKt.to(StandardClassIds.INSTANCE.getSet(), StandardClassIds.INSTANCE.getMutableSet()), TuplesKt.to(StandardClassIds.INSTANCE.getMap(), StandardClassIds.INSTANCE.getMutableMap()), TuplesKt.to(StandardClassIds.INSTANCE.getMapEntry(), StandardClassIds.INSTANCE.getMutableMapEntry()));
            baseCollectionToMutableEquivalent = mapMapOf;
            Set<Map.Entry<ClassId, ClassId>> setEntrySet = mapMapOf.entrySet();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
            Iterator<T> it = setEntrySet.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                linkedHashMap.put((ClassId) entry.getValue(), (ClassId) entry.getKey());
            }
            mutableCollectionToBaseCollection = linkedHashMap;
        }

        public final Map<ClassId, ClassId> getMutableCollectionToBaseCollection() {
            return mutableCollectionToBaseCollection;
        }
    }
}
