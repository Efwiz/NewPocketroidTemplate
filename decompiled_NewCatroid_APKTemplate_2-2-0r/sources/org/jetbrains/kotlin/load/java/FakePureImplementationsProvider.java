package org.jetbrains.kotlin.load.java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FakePureImplementationsProvider.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0\f\"\u00020\rH\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0010\u001a\u00020\u0005J\u0010\u0010\u000f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0010\u001a\u00020\bJ\u001b\u0010\u0011\u001a\u00020\u0012*\u00020\u00052\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\nH\u0082\u0004R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/load/java/FakePureImplementationsProvider;", "", "()V", "pureImplementationsClassIds", "", "Lorg/jetbrains/kotlin/name/ClassId;", "pureImplementationsFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "fqNameListOf", "", "names", "", "", "([Ljava/lang/String;)Ljava/util/List;", "getPurelyImplementedInterface", "classFqName", "implementedWith", "", "implementations", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakePureImplementationsProvider {
    public static final FakePureImplementationsProvider INSTANCE;
    private static final Map<ClassId, ClassId> pureImplementationsClassIds;
    private static final Map<FqName, FqName> pureImplementationsFqNames;

    private FakePureImplementationsProvider() {
    }

    public final ClassId getPurelyImplementedInterface(ClassId classFqName) {
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        return pureImplementationsClassIds.get(classFqName);
    }

    public final FqName getPurelyImplementedInterface(FqName classFqName) {
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        return pureImplementationsFqNames.get(classFqName);
    }

    static {
        FakePureImplementationsProvider fakePureImplementationsProvider = new FakePureImplementationsProvider();
        INSTANCE = fakePureImplementationsProvider;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        pureImplementationsClassIds = linkedHashMap;
        fakePureImplementationsProvider.implementedWith(StandardClassIds.INSTANCE.getMutableList(), fakePureImplementationsProvider.fqNameListOf(CommonClassNames.JAVA_UTIL_ARRAY_LIST, CommonClassNames.JAVA_UTIL_LINKED_LIST));
        fakePureImplementationsProvider.implementedWith(StandardClassIds.INSTANCE.getMutableSet(), fakePureImplementationsProvider.fqNameListOf(CommonClassNames.JAVA_UTIL_HASH_SET, "java.util.TreeSet", CommonClassNames.JAVA_UTIL_LINKED_HASH_SET));
        fakePureImplementationsProvider.implementedWith(StandardClassIds.INSTANCE.getMutableMap(), fakePureImplementationsProvider.fqNameListOf(CommonClassNames.JAVA_UTIL_HASH_MAP, "java.util.TreeMap", CommonClassNames.JAVA_UTIL_LINKED_HASH_MAP, CommonClassNames.JAVA_UTIL_CONCURRENT_HASH_MAP, "java.util.concurrent.ConcurrentSkipListMap"));
        ClassId classId = ClassId.topLevel(new FqName(CommonClassNames.JAVA_UTIL_FUNCTION_FUNCTION));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        fakePureImplementationsProvider.implementedWith(classId, fakePureImplementationsProvider.fqNameListOf("java.util.function.UnaryOperator"));
        ClassId classId2 = ClassId.topLevel(new FqName(CommonClassNames.JAVA_UTIL_FUNCTION_BI_FUNCTION));
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        fakePureImplementationsProvider.implementedWith(classId2, fakePureImplementationsProvider.fqNameListOf("java.util.function.BinaryOperator"));
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            arrayList.add(TuplesKt.to(((ClassId) entry.getKey()).asSingleFqName(), ((ClassId) entry.getValue()).asSingleFqName()));
        }
        pureImplementationsFqNames = MapsKt.toMap(arrayList);
    }

    private final void implementedWith(ClassId classId, List<ClassId> list) {
        Map<ClassId, ClassId> map = pureImplementationsClassIds;
        for (Object obj : list) {
            map.put((ClassId) obj, classId);
        }
    }

    private final List<ClassId> fqNameListOf(String... names) {
        ArrayList arrayList = new ArrayList(names.length);
        for (String str : names) {
            arrayList.add(ClassId.topLevel(new FqName(str)));
        }
        return arrayList;
    }
}
