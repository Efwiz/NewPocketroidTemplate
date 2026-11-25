package org.jetbrains.kotlin.builtins.jvm;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.CompanionObjectMapping;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: JavaToKotlinClassMap.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000f\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001=B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u0004H\u0002J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u0004H\u0002J\u0018\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0004H\u0002J\u0010\u0010&\u001a\u00020 2\u0006\u0010'\u001a\u00020\u0018H\u0002J\u001c\u0010(\u001a\u00020 2\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*2\u0006\u0010+\u001a\u00020\u0007H\u0002J\u001c\u0010(\u001a\u00020 2\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*2\u0006\u0010+\u001a\u00020\u0013H\u0002J\u0014\u0010,\u001a\u00020\u00042\n\u0010-\u001a\u0006\u0012\u0002\b\u00030*H\u0002J\u000e\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0007J\u0018\u00101\u001a\u00020/2\u0006\u0010+\u001a\u00020\u00132\u0006\u00102\u001a\u00020\rH\u0002J\u0010\u00103\u001a\u00020/2\b\u0010,\u001a\u0004\u0018\u00010\u0004J\u0010\u00103\u001a\u00020/2\b\u00104\u001a\u0004\u0018\u00010\u0013J\u0010\u00105\u001a\u00020/2\b\u0010,\u001a\u0004\u0018\u00010\u0004J\u0010\u00105\u001a\u00020/2\b\u00104\u001a\u0004\u0018\u00010\u0013J\u0010\u00106\u001a\u0004\u0018\u00010\u00042\u0006\u00100\u001a\u00020\u0007J\u0010\u00107\u001a\u0004\u0018\u00010\u00042\u0006\u00100\u001a\u00020\u0007J\u0010\u00108\u001a\u0004\u0018\u00010\u00042\u0006\u0010+\u001a\u00020\u0013J!\u00109\u001a\u00020\u0018\"\u0006\b\u0000\u0010:\u0018\u00012\u0006\u0010;\u001a\u00020\u00042\u0006\u0010<\u001a\u00020\u0007H\u0082\bJ\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00042\u0006\u0010,\u001a\u00020\u0004J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00072\b\u00104\u001a\u0004\u0018\u00010\u0013J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u00042\u0006\u0010,\u001a\u00020\u0004J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00072\b\u00104\u001a\u0004\u0018\u00010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0011\u001a\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00040\u0012j\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0004`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0015\u001a\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00040\u0012j\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0004`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR*\u0010\u001b\u001a\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00070\u0012j\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0007`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001c\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0012j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001d\u001a\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00070\u0012j\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0007`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001e\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0012j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/builtins/jvm/JavaToKotlinClassMap;", "", "()V", "CLASS_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "FUNCTION_N_CLASS_ID", "FUNCTION_N_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getFUNCTION_N_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "K_CLASS_CLASS_ID", "K_FUNCTION_CLASS_ID", "NUMBERED_FUNCTION_PREFIX", "", "NUMBERED_K_FUNCTION_PREFIX", "NUMBERED_K_SUSPEND_FUNCTION_PREFIX", "NUMBERED_SUSPEND_FUNCTION_PREFIX", "javaToKotlin", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "Lkotlin/collections/HashMap;", "kotlinToJava", "mutabilityMappings", "", "Lorg/jetbrains/kotlin/builtins/jvm/JavaToKotlinClassMap$PlatformMutabilityMapping;", "getMutabilityMappings", "()Ljava/util/List;", "mutableToReadOnly", "mutableToReadOnlyClassId", "readOnlyToMutable", "readOnlyToMutableClassId", ProductAction.ACTION_ADD, "", "javaClassId", "kotlinClassId", "addJavaToKotlin", "addKotlinToJava", "kotlinFqNameUnsafe", "addMapping", "platformMutabilityMapping", "addTopLevel", "javaClass", "Ljava/lang/Class;", "kotlinFqName", "classId", "clazz", "isJavaPlatformClass", "", "fqName", "isKotlinFunctionWithBigArity", "prefix", "isMutable", "fqNameUnsafe", "isReadOnly", "mapJavaToKotlin", "mapJavaToKotlinIncludingClassMapping", "mapKotlinToJava", "mutabilityMapping", ExifInterface.GPS_DIRECTION_TRUE, "kotlinReadOnly", "kotlinMutable", "PlatformMutabilityMapping", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaToKotlinClassMap {
    private static final ClassId CLASS_CLASS_ID;
    private static final ClassId FUNCTION_N_CLASS_ID;
    private static final FqName FUNCTION_N_FQ_NAME;
    public static final JavaToKotlinClassMap INSTANCE;
    private static final ClassId K_CLASS_CLASS_ID;
    private static final ClassId K_FUNCTION_CLASS_ID;
    private static final String NUMBERED_FUNCTION_PREFIX;
    private static final String NUMBERED_K_FUNCTION_PREFIX;
    private static final String NUMBERED_K_SUSPEND_FUNCTION_PREFIX;
    private static final String NUMBERED_SUSPEND_FUNCTION_PREFIX;
    private static final HashMap<FqNameUnsafe, ClassId> javaToKotlin;
    private static final HashMap<FqNameUnsafe, ClassId> kotlinToJava;
    private static final List<PlatformMutabilityMapping> mutabilityMappings;
    private static final HashMap<FqNameUnsafe, FqName> mutableToReadOnly;
    private static final HashMap<ClassId, ClassId> mutableToReadOnlyClassId;
    private static final HashMap<FqNameUnsafe, FqName> readOnlyToMutable;
    private static final HashMap<ClassId, ClassId> readOnlyToMutableClassId;

    private JavaToKotlinClassMap() {
    }

    static {
        JavaToKotlinClassMap javaToKotlinClassMap = new JavaToKotlinClassMap();
        INSTANCE = javaToKotlinClassMap;
        NUMBERED_FUNCTION_PREFIX = FunctionTypeKind.Function.INSTANCE.getPackageFqName().toString() + '.' + FunctionTypeKind.Function.INSTANCE.getClassNamePrefix();
        NUMBERED_K_FUNCTION_PREFIX = FunctionTypeKind.KFunction.INSTANCE.getPackageFqName().toString() + '.' + FunctionTypeKind.KFunction.INSTANCE.getClassNamePrefix();
        NUMBERED_SUSPEND_FUNCTION_PREFIX = FunctionTypeKind.SuspendFunction.INSTANCE.getPackageFqName().toString() + '.' + FunctionTypeKind.SuspendFunction.INSTANCE.getClassNamePrefix();
        NUMBERED_K_SUSPEND_FUNCTION_PREFIX = FunctionTypeKind.KSuspendFunction.INSTANCE.getPackageFqName().toString() + '.' + FunctionTypeKind.KSuspendFunction.INSTANCE.getClassNamePrefix();
        ClassId classId = ClassId.topLevel(new FqName("kotlin.jvm.functions.FunctionN"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        FUNCTION_N_CLASS_ID = classId;
        FqName fqNameAsSingleFqName = classId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        FUNCTION_N_FQ_NAME = fqNameAsSingleFqName;
        K_FUNCTION_CLASS_ID = StandardClassIds.INSTANCE.getKFunction();
        K_CLASS_CLASS_ID = StandardClassIds.INSTANCE.getKClass();
        CLASS_CLASS_ID = javaToKotlinClassMap.classId(Class.class);
        javaToKotlin = new HashMap<>();
        kotlinToJava = new HashMap<>();
        mutableToReadOnly = new HashMap<>();
        readOnlyToMutable = new HashMap<>();
        mutableToReadOnlyClassId = new HashMap<>();
        readOnlyToMutableClassId = new HashMap<>();
        ClassId classId2 = ClassId.topLevel(StandardNames.FqNames.iterable);
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        FqName fqName = StandardNames.FqNames.mutableIterable;
        FqName packageFqName = classId2.getPackageFqName();
        FqName packageFqName2 = classId2.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
        FqName fqNameTail = FqNamesUtilKt.tail(fqName, packageFqName2);
        ClassId classId3 = new ClassId(packageFqName, fqNameTail, false);
        ClassId classId4 = ClassId.topLevel(StandardNames.FqNames.iterator);
        Intrinsics.checkNotNullExpressionValue(classId4, "topLevel(...)");
        FqName fqName2 = StandardNames.FqNames.mutableIterator;
        FqName packageFqName3 = classId4.getPackageFqName();
        FqName packageFqName4 = classId4.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName4, "getPackageFqName(...)");
        ClassId classId5 = new ClassId(packageFqName3, FqNamesUtilKt.tail(fqName2, packageFqName4), false);
        ClassId classId6 = ClassId.topLevel(StandardNames.FqNames.collection);
        Intrinsics.checkNotNullExpressionValue(classId6, "topLevel(...)");
        FqName fqName3 = StandardNames.FqNames.mutableCollection;
        FqName packageFqName5 = classId6.getPackageFqName();
        FqName packageFqName6 = classId6.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName6, "getPackageFqName(...)");
        ClassId classId7 = new ClassId(packageFqName5, FqNamesUtilKt.tail(fqName3, packageFqName6), false);
        ClassId classId8 = ClassId.topLevel(StandardNames.FqNames.list);
        Intrinsics.checkNotNullExpressionValue(classId8, "topLevel(...)");
        FqName fqName4 = StandardNames.FqNames.mutableList;
        FqName packageFqName7 = classId8.getPackageFqName();
        FqName packageFqName8 = classId8.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName8, "getPackageFqName(...)");
        ClassId classId9 = new ClassId(packageFqName7, FqNamesUtilKt.tail(fqName4, packageFqName8), false);
        ClassId classId10 = ClassId.topLevel(StandardNames.FqNames.set);
        Intrinsics.checkNotNullExpressionValue(classId10, "topLevel(...)");
        FqName fqName5 = StandardNames.FqNames.mutableSet;
        FqName packageFqName9 = classId10.getPackageFqName();
        FqName packageFqName10 = classId10.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName10, "getPackageFqName(...)");
        ClassId classId11 = new ClassId(packageFqName9, FqNamesUtilKt.tail(fqName5, packageFqName10), false);
        ClassId classId12 = ClassId.topLevel(StandardNames.FqNames.listIterator);
        Intrinsics.checkNotNullExpressionValue(classId12, "topLevel(...)");
        FqName fqName6 = StandardNames.FqNames.mutableListIterator;
        FqName packageFqName11 = classId12.getPackageFqName();
        FqName packageFqName12 = classId12.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName12, "getPackageFqName(...)");
        ClassId classId13 = new ClassId(packageFqName11, FqNamesUtilKt.tail(fqName6, packageFqName12), false);
        ClassId classId14 = ClassId.topLevel(StandardNames.FqNames.map);
        Intrinsics.checkNotNullExpressionValue(classId14, "topLevel(...)");
        FqName fqName7 = StandardNames.FqNames.mutableMap;
        FqName packageFqName13 = classId14.getPackageFqName();
        FqName packageFqName14 = classId14.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName14, "getPackageFqName(...)");
        ClassId classId15 = new ClassId(packageFqName13, FqNamesUtilKt.tail(fqName7, packageFqName14), false);
        ClassId classIdCreateNestedClassId = ClassId.topLevel(StandardNames.FqNames.map).createNestedClassId(StandardNames.FqNames.mapEntry.shortName());
        Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
        FqName fqName8 = StandardNames.FqNames.mutableMapEntry;
        FqName packageFqName15 = classIdCreateNestedClassId.getPackageFqName();
        FqName packageFqName16 = classIdCreateNestedClassId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName16, "getPackageFqName(...)");
        List<PlatformMutabilityMapping> listListOf = CollectionsKt.listOf((Object[]) new PlatformMutabilityMapping[]{new PlatformMutabilityMapping(javaToKotlinClassMap.classId(Iterable.class), classId2, classId3), new PlatformMutabilityMapping(javaToKotlinClassMap.classId(Iterator.class), classId4, classId5), new PlatformMutabilityMapping(javaToKotlinClassMap.classId(Collection.class), classId6, classId7), new PlatformMutabilityMapping(javaToKotlinClassMap.classId(List.class), classId8, classId9), new PlatformMutabilityMapping(javaToKotlinClassMap.classId(Set.class), classId10, classId11), new PlatformMutabilityMapping(javaToKotlinClassMap.classId(ListIterator.class), classId12, classId13), new PlatformMutabilityMapping(javaToKotlinClassMap.classId(Map.class), classId14, classId15), new PlatformMutabilityMapping(javaToKotlinClassMap.classId(Map.Entry.class), classIdCreateNestedClassId, new ClassId(packageFqName15, FqNamesUtilKt.tail(fqName8, packageFqName16), false))});
        mutabilityMappings = listListOf;
        javaToKotlinClassMap.addTopLevel(Object.class, StandardNames.FqNames.any);
        javaToKotlinClassMap.addTopLevel(String.class, StandardNames.FqNames.string);
        javaToKotlinClassMap.addTopLevel(CharSequence.class, StandardNames.FqNames.charSequence);
        javaToKotlinClassMap.addTopLevel(Throwable.class, StandardNames.FqNames.throwable);
        javaToKotlinClassMap.addTopLevel(Cloneable.class, StandardNames.FqNames.cloneable);
        javaToKotlinClassMap.addTopLevel(Number.class, StandardNames.FqNames.number);
        javaToKotlinClassMap.addTopLevel(Comparable.class, StandardNames.FqNames.comparable);
        javaToKotlinClassMap.addTopLevel(Enum.class, StandardNames.FqNames._enum);
        javaToKotlinClassMap.addTopLevel(Annotation.class, StandardNames.FqNames.annotation);
        Iterator<PlatformMutabilityMapping> it = listListOf.iterator();
        while (it.hasNext()) {
            INSTANCE.addMapping(it.next());
        }
        for (JvmPrimitiveType jvmPrimitiveType : JvmPrimitiveType.values()) {
            JavaToKotlinClassMap javaToKotlinClassMap2 = INSTANCE;
            ClassId classId16 = ClassId.topLevel(jvmPrimitiveType.getWrapperFqName());
            Intrinsics.checkNotNullExpressionValue(classId16, "topLevel(...)");
            PrimitiveType primitiveType = jvmPrimitiveType.getPrimitiveType();
            Intrinsics.checkNotNullExpressionValue(primitiveType, "getPrimitiveType(...)");
            ClassId classId17 = ClassId.topLevel(StandardNames.getPrimitiveFqName(primitiveType));
            Intrinsics.checkNotNullExpressionValue(classId17, "topLevel(...)");
            javaToKotlinClassMap2.add(classId16, classId17);
        }
        for (ClassId classId18 : CompanionObjectMapping.INSTANCE.allClassesWithIntrinsicCompanions()) {
            JavaToKotlinClassMap javaToKotlinClassMap3 = INSTANCE;
            ClassId classId19 = ClassId.topLevel(new FqName("kotlin.jvm.internal." + classId18.getShortClassName().asString() + "CompanionObject"));
            Intrinsics.checkNotNullExpressionValue(classId19, "topLevel(...)");
            ClassId classIdCreateNestedClassId2 = classId18.createNestedClassId(SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT);
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId2, "createNestedClassId(...)");
            javaToKotlinClassMap3.add(classId19, classIdCreateNestedClassId2);
        }
        for (int i = 0; i < 23; i++) {
            JavaToKotlinClassMap javaToKotlinClassMap4 = INSTANCE;
            ClassId classId20 = ClassId.topLevel(new FqName("kotlin.jvm.functions.Function" + i));
            Intrinsics.checkNotNullExpressionValue(classId20, "topLevel(...)");
            javaToKotlinClassMap4.add(classId20, StandardNames.getFunctionClassId(i));
            javaToKotlinClassMap4.addKotlinToJava(new FqName(NUMBERED_K_FUNCTION_PREFIX + i), K_FUNCTION_CLASS_ID);
        }
        for (int i2 = 0; i2 < 22; i2++) {
            FunctionTypeKind.KSuspendFunction kSuspendFunction = FunctionTypeKind.KSuspendFunction.INSTANCE;
            INSTANCE.addKotlinToJava(new FqName((kSuspendFunction.getPackageFqName().toString() + '.' + kSuspendFunction.getClassNamePrefix()) + i2), K_FUNCTION_CLASS_ID);
        }
        JavaToKotlinClassMap javaToKotlinClassMap5 = INSTANCE;
        FqName safe = StandardNames.FqNames.nothing.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
        javaToKotlinClassMap5.addKotlinToJava(safe, javaToKotlinClassMap5.classId(Void.class));
    }

    public final FqName getFUNCTION_N_FQ_NAME() {
        return FUNCTION_N_FQ_NAME;
    }

    /* JADX INFO: compiled from: JavaToKotlinClassMap.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/builtins/jvm/JavaToKotlinClassMap$PlatformMutabilityMapping;", "", "javaClass", "Lorg/jetbrains/kotlin/name/ClassId;", "kotlinReadOnly", "kotlinMutable", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/name/ClassId;)V", "getJavaClass", "()Lorg/jetbrains/kotlin/name/ClassId;", "getKotlinMutable", "getKotlinReadOnly", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class PlatformMutabilityMapping {
        private final ClassId javaClass;
        private final ClassId kotlinMutable;
        private final ClassId kotlinReadOnly;

        public static /* synthetic */ PlatformMutabilityMapping copy$default(PlatformMutabilityMapping platformMutabilityMapping, ClassId classId, ClassId classId2, ClassId classId3, int i, Object obj) {
            if ((i & 1) != 0) {
                classId = platformMutabilityMapping.javaClass;
            }
            if ((i & 2) != 0) {
                classId2 = platformMutabilityMapping.kotlinReadOnly;
            }
            if ((i & 4) != 0) {
                classId3 = platformMutabilityMapping.kotlinMutable;
            }
            return platformMutabilityMapping.copy(classId, classId2, classId3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ClassId getJavaClass() {
            return this.javaClass;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ClassId getKotlinReadOnly() {
            return this.kotlinReadOnly;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final ClassId getKotlinMutable() {
            return this.kotlinMutable;
        }

        public final PlatformMutabilityMapping copy(ClassId javaClass, ClassId kotlinReadOnly, ClassId kotlinMutable) {
            Intrinsics.checkNotNullParameter(javaClass, "javaClass");
            Intrinsics.checkNotNullParameter(kotlinReadOnly, "kotlinReadOnly");
            Intrinsics.checkNotNullParameter(kotlinMutable, "kotlinMutable");
            return new PlatformMutabilityMapping(javaClass, kotlinReadOnly, kotlinMutable);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PlatformMutabilityMapping)) {
                return false;
            }
            PlatformMutabilityMapping platformMutabilityMapping = (PlatformMutabilityMapping) other;
            return Intrinsics.areEqual(this.javaClass, platformMutabilityMapping.javaClass) && Intrinsics.areEqual(this.kotlinReadOnly, platformMutabilityMapping.kotlinReadOnly) && Intrinsics.areEqual(this.kotlinMutable, platformMutabilityMapping.kotlinMutable);
        }

        public int hashCode() {
            return (((this.javaClass.hashCode() * 31) + this.kotlinReadOnly.hashCode()) * 31) + this.kotlinMutable.hashCode();
        }

        public String toString() {
            return "PlatformMutabilityMapping(javaClass=" + this.javaClass + ", kotlinReadOnly=" + this.kotlinReadOnly + ", kotlinMutable=" + this.kotlinMutable + ')';
        }

        public PlatformMutabilityMapping(ClassId javaClass, ClassId kotlinReadOnly, ClassId kotlinMutable) {
            Intrinsics.checkNotNullParameter(javaClass, "javaClass");
            Intrinsics.checkNotNullParameter(kotlinReadOnly, "kotlinReadOnly");
            Intrinsics.checkNotNullParameter(kotlinMutable, "kotlinMutable");
            this.javaClass = javaClass;
            this.kotlinReadOnly = kotlinReadOnly;
            this.kotlinMutable = kotlinMutable;
        }

        public final ClassId getJavaClass() {
            return this.javaClass;
        }

        public final ClassId getKotlinReadOnly() {
            return this.kotlinReadOnly;
        }

        public final ClassId getKotlinMutable() {
            return this.kotlinMutable;
        }
    }

    public final List<PlatformMutabilityMapping> getMutabilityMappings() {
        return mutabilityMappings;
    }

    public final ClassId mapJavaToKotlin(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return javaToKotlin.get(fqName.toUnsafe());
    }

    public final ClassId mapJavaToKotlinIncludingClassMapping(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return Intrinsics.areEqual(fqName, CLASS_CLASS_ID.asSingleFqName()) ? K_CLASS_CLASS_ID : mapJavaToKotlin(fqName);
    }

    public final ClassId mapKotlinToJava(FqNameUnsafe kotlinFqName) {
        Intrinsics.checkNotNullParameter(kotlinFqName, "kotlinFqName");
        if (!isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_FUNCTION_PREFIX) && !isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_SUSPEND_FUNCTION_PREFIX)) {
            if (!isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_K_FUNCTION_PREFIX) && !isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_K_SUSPEND_FUNCTION_PREFIX)) {
                return kotlinToJava.get(kotlinFqName);
            }
            return K_FUNCTION_CLASS_ID;
        }
        return FUNCTION_N_CLASS_ID;
    }

    private final boolean isKotlinFunctionWithBigArity(FqNameUnsafe kotlinFqName, String prefix) {
        Integer intOrNull;
        String strAsString = kotlinFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strSubstringAfter = StringsKt.substringAfter(strAsString, prefix, "");
        String str = strSubstringAfter;
        return str.length() > 0 && !StringsKt.startsWith$default((CharSequence) str, ExternalAnnotationProvider.NULLABLE, false, 2, (Object) null) && (intOrNull = StringsKt.toIntOrNull(strSubstringAfter)) != null && intOrNull.intValue() >= 23;
    }

    private final void addMapping(PlatformMutabilityMapping platformMutabilityMapping) {
        ClassId javaClass = platformMutabilityMapping.getJavaClass();
        ClassId kotlinReadOnly = platformMutabilityMapping.getKotlinReadOnly();
        ClassId kotlinMutable = platformMutabilityMapping.getKotlinMutable();
        add(javaClass, kotlinReadOnly);
        FqName fqNameAsSingleFqName = kotlinMutable.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        addKotlinToJava(fqNameAsSingleFqName, javaClass);
        mutableToReadOnlyClassId.put(kotlinMutable, kotlinReadOnly);
        readOnlyToMutableClassId.put(kotlinReadOnly, kotlinMutable);
        FqName fqNameAsSingleFqName2 = kotlinReadOnly.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName2, "asSingleFqName(...)");
        FqName fqNameAsSingleFqName3 = kotlinMutable.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName3, "asSingleFqName(...)");
        HashMap<FqNameUnsafe, FqName> map = mutableToReadOnly;
        FqNameUnsafe unsafe = kotlinMutable.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        map.put(unsafe, fqNameAsSingleFqName2);
        HashMap<FqNameUnsafe, FqName> map2 = readOnlyToMutable;
        FqNameUnsafe unsafe2 = fqNameAsSingleFqName2.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe2, "toUnsafe(...)");
        map2.put(unsafe2, fqNameAsSingleFqName3);
    }

    private final void add(ClassId javaClassId, ClassId kotlinClassId) {
        addJavaToKotlin(javaClassId, kotlinClassId);
        FqName fqNameAsSingleFqName = kotlinClassId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        addKotlinToJava(fqNameAsSingleFqName, javaClassId);
    }

    private final void addTopLevel(Class<?> javaClass, FqNameUnsafe kotlinFqName) {
        FqName safe = kotlinFqName.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
        addTopLevel(javaClass, safe);
    }

    private final void addTopLevel(Class<?> javaClass, FqName kotlinFqName) {
        ClassId classId = classId(javaClass);
        ClassId classId2 = ClassId.topLevel(kotlinFqName);
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        add(classId, classId2);
    }

    private final void addJavaToKotlin(ClassId javaClassId, ClassId kotlinClassId) {
        HashMap<FqNameUnsafe, ClassId> map = javaToKotlin;
        FqNameUnsafe unsafe = javaClassId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        map.put(unsafe, kotlinClassId);
    }

    private final void addKotlinToJava(FqName kotlinFqNameUnsafe, ClassId javaClassId) {
        HashMap<FqNameUnsafe, ClassId> map = kotlinToJava;
        FqNameUnsafe unsafe = kotlinFqNameUnsafe.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        map.put(unsafe, javaClassId);
    }

    public final boolean isJavaPlatformClass(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return mapJavaToKotlin(fqName) != null;
    }

    public final FqName mutableToReadOnly(FqNameUnsafe fqNameUnsafe) {
        return mutableToReadOnly.get(fqNameUnsafe);
    }

    public final FqName readOnlyToMutable(FqNameUnsafe fqNameUnsafe) {
        return readOnlyToMutable.get(fqNameUnsafe);
    }

    public final ClassId mutableToReadOnly(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return mutableToReadOnlyClassId.get(classId);
    }

    public final ClassId readOnlyToMutable(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return readOnlyToMutableClassId.get(classId);
    }

    public final boolean isMutable(FqNameUnsafe fqNameUnsafe) {
        return mutableToReadOnly.containsKey(fqNameUnsafe);
    }

    public final boolean isReadOnly(FqNameUnsafe fqNameUnsafe) {
        return readOnlyToMutable.containsKey(fqNameUnsafe);
    }

    public final boolean isMutable(ClassId classId) {
        return mutableToReadOnlyClassId.containsKey(classId);
    }

    public final boolean isReadOnly(ClassId classId) {
        return readOnlyToMutableClassId.containsKey(classId);
    }

    private final ClassId classId(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            clazz.isArray();
        }
        Class<?> declaringClass = clazz.getDeclaringClass();
        if (declaringClass == null) {
            ClassId classId = ClassId.topLevel(new FqName(clazz.getCanonicalName()));
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
            return classId;
        }
        ClassId classIdCreateNestedClassId = classId(declaringClass).createNestedClassId(Name.identifier(clazz.getSimpleName()));
        Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
        return classIdCreateNestedClassId;
    }
}
