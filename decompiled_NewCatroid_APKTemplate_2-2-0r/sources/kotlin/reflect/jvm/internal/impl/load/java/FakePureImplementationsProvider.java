package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: FakePureImplementationsProvider.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class FakePureImplementationsProvider {
    public static final FakePureImplementationsProvider INSTANCE;
    private static final HashMap<FqName, FqName> pureImplementations;

    private FakePureImplementationsProvider() {
    }

    public final FqName getPurelyImplementedInterface(FqName classFqName) {
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        return pureImplementations.get(classFqName);
    }

    static {
        FakePureImplementationsProvider fakePureImplementationsProvider = new FakePureImplementationsProvider();
        INSTANCE = fakePureImplementationsProvider;
        pureImplementations = new HashMap<>();
        fakePureImplementationsProvider.implementedWith(StandardNames.FqNames.mutableList, fakePureImplementationsProvider.fqNameListOf(CommonClassNames.JAVA_UTIL_ARRAY_LIST, CommonClassNames.JAVA_UTIL_LINKED_LIST));
        fakePureImplementationsProvider.implementedWith(StandardNames.FqNames.mutableSet, fakePureImplementationsProvider.fqNameListOf(CommonClassNames.JAVA_UTIL_HASH_SET, "java.util.TreeSet", CommonClassNames.JAVA_UTIL_LINKED_HASH_SET));
        fakePureImplementationsProvider.implementedWith(StandardNames.FqNames.mutableMap, fakePureImplementationsProvider.fqNameListOf(CommonClassNames.JAVA_UTIL_HASH_MAP, "java.util.TreeMap", CommonClassNames.JAVA_UTIL_LINKED_HASH_MAP, CommonClassNames.JAVA_UTIL_CONCURRENT_HASH_MAP, "java.util.concurrent.ConcurrentSkipListMap"));
        fakePureImplementationsProvider.implementedWith(new FqName(CommonClassNames.JAVA_UTIL_FUNCTION_FUNCTION), fakePureImplementationsProvider.fqNameListOf("java.util.function.UnaryOperator"));
        fakePureImplementationsProvider.implementedWith(new FqName(CommonClassNames.JAVA_UTIL_FUNCTION_BI_FUNCTION), fakePureImplementationsProvider.fqNameListOf("java.util.function.BinaryOperator"));
    }

    private final void implementedWith(FqName fqName, List<FqName> list) {
        AbstractMap abstractMap = pureImplementations;
        for (Object obj : list) {
            abstractMap.put(obj, fqName);
        }
    }

    private final List<FqName> fqNameListOf(String... strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            i++;
            arrayList.add(new FqName(str));
        }
        return arrayList;
    }
}
