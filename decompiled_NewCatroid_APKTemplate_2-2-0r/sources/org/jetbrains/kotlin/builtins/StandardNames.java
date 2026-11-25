package org.jetbrains.kotlin.builtins;

import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: StandardNames.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001?B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0007J\u0010\u00101\u001a\u00020\u00132\u0006\u0010/\u001a\u000200H\u0007J\u0010\u00102\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0007J\u0010\u00103\u001a\u0002042\u0006\u0010/\u001a\u000200H\u0007J\u0010\u00105\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0007J\u0010\u00106\u001a\u0002042\u0006\u0010/\u001a\u000200H\u0007J\u0010\u00107\u001a\u00020\u00042\u0006\u00108\u001a\u000209H\u0007J\u0010\u0010:\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0007J\u0010\u0010;\u001a\u00020\u00132\u0006\u0010/\u001a\u000200H\u0007J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u000204H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00138\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0013X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0013X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0013X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0013X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00130)8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/builtins/StandardNames;", "", "()V", "ANNOTATION_PACKAGE_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "BACKING_FIELD", "Lorg/jetbrains/kotlin/name/Name;", "BUILT_INS_PACKAGE_FQ_NAME", "BUILT_INS_PACKAGE_FQ_NAMES", "", "BUILT_INS_PACKAGE_NAME", "CHAR_CODE", "COLLECTIONS_PACKAGE_FQ_NAME", "CONTEXT_FUNCTION_TYPE_PARAMETER_COUNT_NAME", "CONTINUATION_INTERFACE_FQ_NAME", "COROUTINES_INTRINSICS_PACKAGE_FQ_NAME", "COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME", "COROUTINES_PACKAGE_FQ_NAME", "DATA_CLASS_COMPONENT_PREFIX", "", "DATA_CLASS_COPY", "DEFAULT_VALUE_PARAMETER", "DYNAMIC_FQ_NAME", "ENUM_ENTRIES", "ENUM_VALUES", "ENUM_VALUE_OF", "HASHCODE_NAME", "IMPLICIT_LAMBDA_PARAMETER_NAME", "KOTLIN_INTERNAL_FQ_NAME", "KOTLIN_REFLECT_FQ_NAME", "K_FUNCTION_PREFIX", "K_MUTABLE_PROPERTY_PREFIX", "K_PROPERTY_PREFIX", "K_SUSPEND_FUNCTION_PREFIX", "MAIN", "NAME", "NEXT_CHAR", "NON_EXISTENT_CLASS", "getNON_EXISTENT_CLASS", "()Lorg/jetbrains/kotlin/name/FqName;", "PREFIXES", "", "RANGES_PACKAGE_FQ_NAME", "RESULT_FQ_NAME", "TEXT_PACKAGE_FQ_NAME", "getFunctionClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "parameterCount", "", "getFunctionName", "getKFunctionClassId", "getKFunctionFqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getKSuspendFunctionClassId", "getKSuspendFunctionName", "getPrimitiveFqName", "primitiveType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getSuspendFunctionClassId", "getSuspendFunctionName", "isPrimitiveArray", "", "arrayFqName", "FqNames", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StandardNames {
    public static final FqName ANNOTATION_PACKAGE_FQ_NAME;
    public static final Name BACKING_FIELD;
    public static final FqName BUILT_INS_PACKAGE_FQ_NAME;
    public static final Set<FqName> BUILT_INS_PACKAGE_FQ_NAMES;
    public static final Name BUILT_INS_PACKAGE_NAME;
    public static final Name CHAR_CODE;
    public static final FqName COLLECTIONS_PACKAGE_FQ_NAME;
    public static final Name CONTEXT_FUNCTION_TYPE_PARAMETER_COUNT_NAME;
    public static final FqName CONTINUATION_INTERFACE_FQ_NAME;
    public static final FqName COROUTINES_INTRINSICS_PACKAGE_FQ_NAME;
    public static final FqName COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME;
    public static final FqName COROUTINES_PACKAGE_FQ_NAME;
    public static final String DATA_CLASS_COMPONENT_PREFIX;
    public static final Name DATA_CLASS_COPY;
    public static final Name DEFAULT_VALUE_PARAMETER;
    public static final FqName DYNAMIC_FQ_NAME;
    public static final Name ENUM_ENTRIES;
    public static final Name ENUM_VALUES;
    public static final Name ENUM_VALUE_OF;
    public static final Name HASHCODE_NAME;
    public static final Name IMPLICIT_LAMBDA_PARAMETER_NAME;
    public static final StandardNames INSTANCE = new StandardNames();
    public static final FqName KOTLIN_INTERNAL_FQ_NAME;
    public static final FqName KOTLIN_REFLECT_FQ_NAME;
    public static final String K_FUNCTION_PREFIX = "KFunction";
    public static final String K_MUTABLE_PROPERTY_PREFIX = "KMutableProperty";
    public static final String K_PROPERTY_PREFIX = "KProperty";
    public static final String K_SUSPEND_FUNCTION_PREFIX = "KSuspendFunction";
    public static final Name MAIN;
    public static final Name NAME;
    public static final Name NEXT_CHAR;
    private static final FqName NON_EXISTENT_CLASS;
    public static final List<String> PREFIXES;
    public static final FqName RANGES_PACKAGE_FQ_NAME;
    public static final FqName RESULT_FQ_NAME;
    public static final FqName TEXT_PACKAGE_FQ_NAME;

    private StandardNames() {
    }

    static {
        Name nameIdentifier = Name.identifier("field");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        BACKING_FIELD = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        DEFAULT_VALUE_PARAMETER = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier(CompoundEntry.NAME_values);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        ENUM_VALUES = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier(TypeBlock.NAME_entries);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        ENUM_ENTRIES = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("valueOf");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        ENUM_VALUE_OF = nameIdentifier5;
        Name nameIdentifier6 = Name.identifier(ConfigConstants.CONFIG_RENAMELIMIT_COPY);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        DATA_CLASS_COPY = nameIdentifier6;
        DATA_CLASS_COMPONENT_PREFIX = "component";
        Name nameIdentifier7 = Name.identifier("hashCode");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
        HASHCODE_NAME = nameIdentifier7;
        Name nameIdentifier8 = Name.identifier(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier8, "identifier(...)");
        CHAR_CODE = nameIdentifier8;
        Name nameIdentifier9 = Name.identifier("name");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier9, "identifier(...)");
        NAME = nameIdentifier9;
        Name nameIdentifier10 = Name.identifier("main");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier10, "identifier(...)");
        MAIN = nameIdentifier10;
        Name nameIdentifier11 = Name.identifier("nextChar");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier11, "identifier(...)");
        NEXT_CHAR = nameIdentifier11;
        Name nameIdentifier12 = Name.identifier("it");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier12, "identifier(...)");
        IMPLICIT_LAMBDA_PARAMETER_NAME = nameIdentifier12;
        Name nameIdentifier13 = Name.identifier("count");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier13, "identifier(...)");
        CONTEXT_FUNCTION_TYPE_PARAMETER_COUNT_NAME = nameIdentifier13;
        DYNAMIC_FQ_NAME = new FqName(MangleConstant.DYNAMIC_MARK);
        FqName fqName = new FqName("kotlin.coroutines");
        COROUTINES_PACKAGE_FQ_NAME = fqName;
        COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME = new FqName("kotlin.coroutines.jvm.internal");
        COROUTINES_INTRINSICS_PACKAGE_FQ_NAME = new FqName("kotlin.coroutines.intrinsics");
        FqName fqNameChild = fqName.child(Name.identifier("Continuation"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        CONTINUATION_INTERFACE_FQ_NAME = fqNameChild;
        RESULT_FQ_NAME = new FqName("kotlin.Result");
        FqName fqName2 = new FqName("kotlin.reflect");
        KOTLIN_REFLECT_FQ_NAME = fqName2;
        PREFIXES = CollectionsKt.listOf((Object[]) new String[]{K_PROPERTY_PREFIX, K_MUTABLE_PROPERTY_PREFIX, K_FUNCTION_PREFIX, K_SUSPEND_FUNCTION_PREFIX});
        Name nameIdentifier14 = Name.identifier(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier14, "identifier(...)");
        BUILT_INS_PACKAGE_NAME = nameIdentifier14;
        FqName fqName3 = FqName.topLevel(nameIdentifier14);
        Intrinsics.checkNotNullExpressionValue(fqName3, "topLevel(...)");
        BUILT_INS_PACKAGE_FQ_NAME = fqName3;
        FqName fqNameChild2 = fqName3.child(Name.identifier("annotation"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        ANNOTATION_PACKAGE_FQ_NAME = fqNameChild2;
        FqName fqNameChild3 = fqName3.child(Name.identifier("collections"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
        COLLECTIONS_PACKAGE_FQ_NAME = fqNameChild3;
        FqName fqNameChild4 = fqName3.child(Name.identifier("ranges"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild4, "child(...)");
        RANGES_PACKAGE_FQ_NAME = fqNameChild4;
        FqName fqNameChild5 = fqName3.child(Name.identifier("text"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild5, "child(...)");
        TEXT_PACKAGE_FQ_NAME = fqNameChild5;
        FqName fqNameChild6 = fqName3.child(Name.identifier("internal"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild6, "child(...)");
        KOTLIN_INTERNAL_FQ_NAME = fqNameChild6;
        NON_EXISTENT_CLASS = new FqName("error.NonExistentClass");
        BUILT_INS_PACKAGE_FQ_NAMES = SetsKt.setOf((Object[]) new FqName[]{fqName3, fqNameChild3, fqNameChild4, fqNameChild2, fqName2, fqNameChild6, fqName});
    }

    public final FqName getNON_EXISTENT_CLASS() {
        return NON_EXISTENT_CLASS;
    }

    /* JADX INFO: compiled from: StandardNames.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010$\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0010\u000e\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010d\u001a\u00020\u000e2\u0006\u0010e\u001a\u00020fH\u0002J\u0010\u0010g\u001a\u00020\u000e2\u0006\u0010e\u001a\u00020fH\u0002J\u0010\u0010h\u001a\u00020\u000e2\u0006\u0010e\u001a\u00020fH\u0002J\u0010\u0010i\u001a\u00020\u00042\u0006\u0010e\u001a\u00020fH\u0002J\u0010\u0010j\u001a\u00020\u000e2\u0006\u0010e\u001a\u00020fH\u0002J\u0010\u0010k\u001a\u00020\u00042\u0006\u0010e\u001a\u00020fH\u0002J\u0010\u0010l\u001a\u00020\u00042\u0006\u0010e\u001a\u00020fH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00160\u00158\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00160!8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00100\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00103\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00105\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00106\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00107\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00108\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00109\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010:\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010;\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010<\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010>\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010@\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010A\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010B\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010C\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010E\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010F\u001a\b\u0012\u0004\u0012\u00020H0G8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010I\u001a\b\u0012\u0004\u0012\u00020H0G8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010J\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010K\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010L\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010M\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010N\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010O\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010P\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010Q\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010R\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010S\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010T\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010U\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010V\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010W\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010X\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010Y\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010Z\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010[\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\\\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010]\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010^\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010_\u001a\u00020.8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010`\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010a\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010c\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006m"}, d2 = {"Lorg/jetbrains/kotlin/builtins/StandardNames$FqNames;", "", "()V", "_boolean", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "_byte", "_char", "_double", "_enum", "_float", "_int", "_long", "_short", "accessibleLateinitPropertyLiteral", "Lorg/jetbrains/kotlin/name/FqName;", "annotation", "annotationRetention", "annotationTarget", "any", "array", "arrayClassFqNameToPrimitiveType", "", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "charSequence", "cloneable", "collection", "comparable", "contextFunctionTypeParams", "deprecated", "deprecatedSinceKotlin", "deprecationLevel", "extensionFunctionType", "fqNameToPrimitiveType", "", "functionSupertype", "intRange", "iterable", "iterator", "kCallable", "kClass", "kDeclarationContainer", "kMutableProperty0", "kMutableProperty1", "kMutableProperty2", "kMutablePropertyFqName", "kProperty", "Lorg/jetbrains/kotlin/name/ClassId;", "kProperty0", "kProperty1", "kProperty2", "kPropertyFqName", "kType", SchemaSymbols.ATTVAL_LIST, "listIterator", "longRange", "map", "mapEntry", "mustBeDocumented", "mutableCollection", "mutableIterable", "mutableIterator", "mutableList", "mutableListIterator", "mutableMap", "mutableMapEntry", "mutableSet", "nothing", "number", "parameterName", "parameterNameClassId", "primitiveArrayTypeShortNames", "", "Lorg/jetbrains/kotlin/name/Name;", "primitiveTypeShortNames", "publishedApi", "repeatable", "repeatableClassId", "replaceWith", "retention", "retentionClassId", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "string", "suppress", "target", "targetClassId", "throwable", "uByte", "uByteArrayFqName", "uByteFqName", "uInt", "uIntArrayFqName", "uIntFqName", "uLong", "uLongArrayFqName", "uLongFqName", "uShort", "uShortArrayFqName", "uShortFqName", "unit", "unsafeVariance", "annotationName", Namer.METADATA_SIMPLE_NAME, "", "collectionsFqName", "fqName", "fqNameUnsafe", "internalName", "rangesFqName", "reflect", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FqNames {
        public static final FqNames INSTANCE;
        public static final FqNameUnsafe _boolean;
        public static final FqNameUnsafe _byte;
        public static final FqNameUnsafe _char;
        public static final FqNameUnsafe _double;
        public static final FqNameUnsafe _enum;
        public static final FqNameUnsafe _float;
        public static final FqNameUnsafe _int;
        public static final FqNameUnsafe _long;
        public static final FqNameUnsafe _short;
        public static final FqName accessibleLateinitPropertyLiteral;
        public static final FqName annotation;
        public static final FqName annotationRetention;
        public static final FqName annotationTarget;
        public static final FqNameUnsafe any;
        public static final FqNameUnsafe array;
        public static final Map<FqNameUnsafe, PrimitiveType> arrayClassFqNameToPrimitiveType;
        public static final FqNameUnsafe charSequence;
        public static final FqNameUnsafe cloneable;
        public static final FqName collection;
        public static final FqName comparable;
        public static final FqName contextFunctionTypeParams;
        public static final FqName deprecated;
        public static final FqName deprecatedSinceKotlin;
        public static final FqName deprecationLevel;
        public static final FqName extensionFunctionType;
        public static final Map<FqNameUnsafe, PrimitiveType> fqNameToPrimitiveType;
        public static final FqNameUnsafe functionSupertype;
        public static final FqNameUnsafe intRange;
        public static final FqName iterable;
        public static final FqName iterator;
        public static final FqNameUnsafe kCallable;
        public static final FqNameUnsafe kClass;
        public static final FqNameUnsafe kDeclarationContainer;
        public static final FqNameUnsafe kMutableProperty0;
        public static final FqNameUnsafe kMutableProperty1;
        public static final FqNameUnsafe kMutableProperty2;
        public static final FqNameUnsafe kMutablePropertyFqName;
        public static final ClassId kProperty;
        public static final FqNameUnsafe kProperty0;
        public static final FqNameUnsafe kProperty1;
        public static final FqNameUnsafe kProperty2;
        public static final FqNameUnsafe kPropertyFqName;
        public static final FqNameUnsafe kType;
        public static final FqName list;
        public static final FqName listIterator;
        public static final FqNameUnsafe longRange;
        public static final FqName map;
        public static final FqName mapEntry;
        public static final FqName mustBeDocumented;
        public static final FqName mutableCollection;
        public static final FqName mutableIterable;
        public static final FqName mutableIterator;
        public static final FqName mutableList;
        public static final FqName mutableListIterator;
        public static final FqName mutableMap;
        public static final FqName mutableMapEntry;
        public static final FqName mutableSet;
        public static final FqNameUnsafe nothing;
        public static final FqNameUnsafe number;
        public static final FqName parameterName;
        public static final ClassId parameterNameClassId;
        public static final Set<Name> primitiveArrayTypeShortNames;
        public static final Set<Name> primitiveTypeShortNames;
        public static final FqName publishedApi;
        public static final FqName repeatable;
        public static final ClassId repeatableClassId;
        public static final FqName replaceWith;
        public static final FqName retention;
        public static final ClassId retentionClassId;
        public static final FqName set;
        public static final FqNameUnsafe string;
        public static final FqName suppress;
        public static final FqName target;
        public static final ClassId targetClassId;
        public static final FqName throwable;
        public static final ClassId uByte;
        public static final FqName uByteArrayFqName;
        public static final FqName uByteFqName;
        public static final ClassId uInt;
        public static final FqName uIntArrayFqName;
        public static final FqName uIntFqName;
        public static final ClassId uLong;
        public static final FqName uLongArrayFqName;
        public static final FqName uLongFqName;
        public static final ClassId uShort;
        public static final FqName uShortArrayFqName;
        public static final FqName uShortFqName;
        public static final FqNameUnsafe unit;
        public static final FqName unsafeVariance;

        private FqNames() {
        }

        static {
            FqNames fqNames = new FqNames();
            INSTANCE = fqNames;
            any = fqNames.fqNameUnsafe("Any");
            nothing = fqNames.fqNameUnsafe("Nothing");
            cloneable = fqNames.fqNameUnsafe("Cloneable");
            suppress = fqNames.fqName("Suppress");
            unit = fqNames.fqNameUnsafe("Unit");
            charSequence = fqNames.fqNameUnsafe("CharSequence");
            string = fqNames.fqNameUnsafe(CommonClassNames.JAVA_LANG_STRING_SHORT);
            array = fqNames.fqNameUnsafe("Array");
            _boolean = fqNames.fqNameUnsafe("Boolean");
            _char = fqNames.fqNameUnsafe("Char");
            _byte = fqNames.fqNameUnsafe("Byte");
            _short = fqNames.fqNameUnsafe("Short");
            _int = fqNames.fqNameUnsafe("Int");
            _long = fqNames.fqNameUnsafe("Long");
            _float = fqNames.fqNameUnsafe("Float");
            _double = fqNames.fqNameUnsafe("Double");
            number = fqNames.fqNameUnsafe("Number");
            _enum = fqNames.fqNameUnsafe("Enum");
            functionSupertype = fqNames.fqNameUnsafe("Function");
            throwable = fqNames.fqName("Throwable");
            comparable = fqNames.fqName("Comparable");
            intRange = fqNames.rangesFqName("IntRange");
            longRange = fqNames.rangesFqName("LongRange");
            deprecated = fqNames.fqName("Deprecated");
            deprecatedSinceKotlin = fqNames.fqName("DeprecatedSinceKotlin");
            deprecationLevel = fqNames.fqName("DeprecationLevel");
            replaceWith = fqNames.fqName("ReplaceWith");
            extensionFunctionType = fqNames.fqName("ExtensionFunctionType");
            contextFunctionTypeParams = fqNames.fqName("ContextFunctionTypeParams");
            FqName fqName = fqNames.fqName("ParameterName");
            parameterName = fqName;
            ClassId classId = ClassId.topLevel(fqName);
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
            parameterNameClassId = classId;
            annotation = fqNames.fqName("Annotation");
            FqName fqNameAnnotationName = fqNames.annotationName("Target");
            target = fqNameAnnotationName;
            ClassId classId2 = ClassId.topLevel(fqNameAnnotationName);
            Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
            targetClassId = classId2;
            annotationTarget = fqNames.annotationName("AnnotationTarget");
            annotationRetention = fqNames.annotationName("AnnotationRetention");
            FqName fqNameAnnotationName2 = fqNames.annotationName("Retention");
            retention = fqNameAnnotationName2;
            ClassId classId3 = ClassId.topLevel(fqNameAnnotationName2);
            Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(...)");
            retentionClassId = classId3;
            FqName fqNameAnnotationName3 = fqNames.annotationName("Repeatable");
            repeatable = fqNameAnnotationName3;
            ClassId classId4 = ClassId.topLevel(fqNameAnnotationName3);
            Intrinsics.checkNotNullExpressionValue(classId4, "topLevel(...)");
            repeatableClassId = classId4;
            mustBeDocumented = fqNames.annotationName("MustBeDocumented");
            unsafeVariance = fqNames.fqName("UnsafeVariance");
            publishedApi = fqNames.fqName("PublishedApi");
            accessibleLateinitPropertyLiteral = fqNames.internalName("AccessibleLateinitPropertyLiteral");
            iterator = fqNames.collectionsFqName("Iterator");
            iterable = fqNames.collectionsFqName("Iterable");
            collection = fqNames.collectionsFqName("Collection");
            list = fqNames.collectionsFqName("List");
            listIterator = fqNames.collectionsFqName("ListIterator");
            set = fqNames.collectionsFqName("Set");
            FqName fqNameCollectionsFqName = fqNames.collectionsFqName("Map");
            map = fqNameCollectionsFqName;
            FqName fqNameChild = fqNameCollectionsFqName.child(Name.identifier("Entry"));
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            mapEntry = fqNameChild;
            mutableIterator = fqNames.collectionsFqName("MutableIterator");
            mutableIterable = fqNames.collectionsFqName("MutableIterable");
            mutableCollection = fqNames.collectionsFqName("MutableCollection");
            mutableList = fqNames.collectionsFqName("MutableList");
            mutableListIterator = fqNames.collectionsFqName("MutableListIterator");
            mutableSet = fqNames.collectionsFqName("MutableSet");
            FqName fqNameCollectionsFqName2 = fqNames.collectionsFqName("MutableMap");
            mutableMap = fqNameCollectionsFqName2;
            FqName fqNameChild2 = fqNameCollectionsFqName2.child(Name.identifier("MutableEntry"));
            Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
            mutableMapEntry = fqNameChild2;
            kClass = reflect("KClass");
            kType = reflect("KType");
            kCallable = reflect("KCallable");
            kProperty0 = reflect("KProperty0");
            kProperty1 = reflect("KProperty1");
            kProperty2 = reflect("KProperty2");
            kMutableProperty0 = reflect("KMutableProperty0");
            kMutableProperty1 = reflect("KMutableProperty1");
            kMutableProperty2 = reflect("KMutableProperty2");
            FqNameUnsafe fqNameUnsafeReflect = reflect(StandardNames.K_PROPERTY_PREFIX);
            kPropertyFqName = fqNameUnsafeReflect;
            kMutablePropertyFqName = reflect(StandardNames.K_MUTABLE_PROPERTY_PREFIX);
            ClassId classId5 = ClassId.topLevel(fqNameUnsafeReflect.toSafe());
            Intrinsics.checkNotNullExpressionValue(classId5, "topLevel(...)");
            kProperty = classId5;
            kDeclarationContainer = reflect("KDeclarationContainer");
            FqName fqName2 = fqNames.fqName("UByte");
            uByteFqName = fqName2;
            FqName fqName3 = fqNames.fqName("UShort");
            uShortFqName = fqName3;
            FqName fqName4 = fqNames.fqName("UInt");
            uIntFqName = fqName4;
            FqName fqName5 = fqNames.fqName("ULong");
            uLongFqName = fqName5;
            ClassId classId6 = ClassId.topLevel(fqName2);
            Intrinsics.checkNotNullExpressionValue(classId6, "topLevel(...)");
            uByte = classId6;
            ClassId classId7 = ClassId.topLevel(fqName3);
            Intrinsics.checkNotNullExpressionValue(classId7, "topLevel(...)");
            uShort = classId7;
            ClassId classId8 = ClassId.topLevel(fqName4);
            Intrinsics.checkNotNullExpressionValue(classId8, "topLevel(...)");
            uInt = classId8;
            ClassId classId9 = ClassId.topLevel(fqName5);
            Intrinsics.checkNotNullExpressionValue(classId9, "topLevel(...)");
            uLong = classId9;
            uByteArrayFqName = fqNames.fqName("UByteArray");
            uShortArrayFqName = fqNames.fqName("UShortArray");
            uIntArrayFqName = fqNames.fqName("UIntArray");
            uLongArrayFqName = fqNames.fqName("ULongArray");
            HashSet hashSetNewHashSetWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
            for (PrimitiveType primitiveType : PrimitiveType.values()) {
                hashSetNewHashSetWithExpectedSize.add(primitiveType.getTypeName());
            }
            primitiveTypeShortNames = hashSetNewHashSetWithExpectedSize;
            HashSet hashSetNewHashSetWithExpectedSize2 = org.jetbrains.kotlin.utils.CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
            for (PrimitiveType primitiveType2 : PrimitiveType.values()) {
                hashSetNewHashSetWithExpectedSize2.add(primitiveType2.getArrayTypeName());
            }
            primitiveArrayTypeShortNames = hashSetNewHashSetWithExpectedSize2;
            HashMap mapNewHashMapWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);
            for (PrimitiveType primitiveType3 : PrimitiveType.values()) {
                FqNames fqNames2 = INSTANCE;
                String strAsString = primitiveType3.getTypeName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                mapNewHashMapWithExpectedSize.put(fqNames2.fqNameUnsafe(strAsString), primitiveType3);
            }
            fqNameToPrimitiveType = mapNewHashMapWithExpectedSize;
            HashMap mapNewHashMapWithExpectedSize2 = org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);
            for (PrimitiveType primitiveType4 : PrimitiveType.values()) {
                FqNames fqNames3 = INSTANCE;
                String strAsString2 = primitiveType4.getArrayTypeName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                mapNewHashMapWithExpectedSize2.put(fqNames3.fqNameUnsafe(strAsString2), primitiveType4);
            }
            arrayClassFqNameToPrimitiveType = mapNewHashMapWithExpectedSize2;
        }

        private final FqNameUnsafe fqNameUnsafe(String simpleName) {
            FqNameUnsafe unsafe = fqName(simpleName).toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
            return unsafe;
        }

        private final FqName fqName(String simpleName) {
            FqName fqNameChild = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(simpleName));
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            return fqNameChild;
        }

        private final FqName collectionsFqName(String simpleName) {
            FqName fqNameChild = StandardNames.COLLECTIONS_PACKAGE_FQ_NAME.child(Name.identifier(simpleName));
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            return fqNameChild;
        }

        private final FqNameUnsafe rangesFqName(String simpleName) {
            FqNameUnsafe unsafe = StandardNames.RANGES_PACKAGE_FQ_NAME.child(Name.identifier(simpleName)).toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
            return unsafe;
        }

        @JvmStatic
        public static final FqNameUnsafe reflect(String simpleName) {
            Intrinsics.checkNotNullParameter(simpleName, "simpleName");
            FqNameUnsafe unsafe = StandardNames.KOTLIN_REFLECT_FQ_NAME.child(Name.identifier(simpleName)).toUnsafe();
            Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
            return unsafe;
        }

        private final FqName annotationName(String simpleName) {
            FqName fqNameChild = StandardNames.ANNOTATION_PACKAGE_FQ_NAME.child(Name.identifier(simpleName));
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            return fqNameChild;
        }

        private final FqName internalName(String simpleName) {
            FqName fqNameChild = StandardNames.KOTLIN_INTERNAL_FQ_NAME.child(Name.identifier(simpleName));
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            return fqNameChild;
        }
    }

    @JvmStatic
    public static final String getFunctionName(int parameterCount) {
        return "Function" + parameterCount;
    }

    @JvmStatic
    public static final ClassId getFunctionClassId(int parameterCount) {
        return new ClassId(BUILT_INS_PACKAGE_FQ_NAME, Name.identifier(getFunctionName(parameterCount)));
    }

    @JvmStatic
    public static final FqNameUnsafe getKFunctionFqName(int parameterCount) {
        return FqNames.reflect(FunctionTypeKind.KFunction.INSTANCE.getClassNamePrefix() + parameterCount);
    }

    @JvmStatic
    public static final ClassId getKFunctionClassId(int parameterCount) {
        FqNameUnsafe kFunctionFqName = getKFunctionFqName(parameterCount);
        return new ClassId(kFunctionFqName.parent().toSafe(), kFunctionFqName.shortName());
    }

    @JvmStatic
    public static final String getSuspendFunctionName(int parameterCount) {
        return FunctionTypeKind.SuspendFunction.INSTANCE.getClassNamePrefix() + parameterCount;
    }

    @JvmStatic
    public static final ClassId getSuspendFunctionClassId(int parameterCount) {
        return new ClassId(COROUTINES_PACKAGE_FQ_NAME, Name.identifier(getSuspendFunctionName(parameterCount)));
    }

    @JvmStatic
    public static final FqNameUnsafe getKSuspendFunctionName(int parameterCount) {
        return FqNames.reflect(FunctionTypeKind.KSuspendFunction.INSTANCE.getClassNamePrefix() + parameterCount);
    }

    @JvmStatic
    public static final ClassId getKSuspendFunctionClassId(int parameterCount) {
        FqNameUnsafe kSuspendFunctionName = getKSuspendFunctionName(parameterCount);
        return new ClassId(kSuspendFunctionName.parent().toSafe(), kSuspendFunctionName.shortName());
    }

    @JvmStatic
    public static final boolean isPrimitiveArray(FqNameUnsafe arrayFqName) {
        Intrinsics.checkNotNullParameter(arrayFqName, "arrayFqName");
        return FqNames.arrayClassFqNameToPrimitiveType.get(arrayFqName) != null;
    }

    @JvmStatic
    public static final FqName getPrimitiveFqName(PrimitiveType primitiveType) {
        Intrinsics.checkNotNullParameter(primitiveType, "primitiveType");
        FqName fqNameChild = BUILT_INS_PACKAGE_FQ_NAME.child(primitiveType.getTypeName());
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return fqNameChild;
    }
}
