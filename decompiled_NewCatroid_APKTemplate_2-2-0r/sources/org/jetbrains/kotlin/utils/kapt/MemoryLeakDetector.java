package org.jetbrains.kotlin.utils.kapt;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.ReflectionUtilKt;

/* JADX INFO: compiled from: MemoryLeakDetector.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u000e\u001a\u00020\u000fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/utils/kapt/MemoryLeakDetector;", "", "()V", "classLoaderData", "", "Lorg/jetbrains/kotlin/utils/kapt/ClassLoaderData;", ProductAction.ACTION_ADD, "", "classLoader", "Ljava/lang/ClassLoader;", "inspectStatics", "", "Lorg/jetbrains/kotlin/utils/kapt/MemoryLeak;", "process", "isParanoid", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MemoryLeakDetector {
    public static final MemoryLeakDetector INSTANCE = new MemoryLeakDetector();
    private static final List<ClassLoaderData> classLoaderData = new ArrayList();

    private MemoryLeakDetector() {
    }

    public final void add(ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        List<ClassLoaderData> list = classLoaderData;
        synchronized (list) {
            list.add(new ClassLoaderData(classLoader));
        }
    }

    public final Set<MemoryLeak> process(boolean isParanoid) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        List<ClassLoaderData> list = classLoaderData;
        synchronized (list) {
            ArrayList arrayList = new ArrayList();
            for (ClassLoaderData classLoaderData2 : list) {
                ClassLoader classLoader = classLoaderData2.getRef().get();
                if (classLoader != null) {
                    Intrinsics.checkNotNull(classLoader);
                    classLoaderData2.setAge(classLoaderData2.getAge() + 1);
                    if (isParanoid || classLoaderData2.getAge() >= 5) {
                        INSTANCE.inspectStatics(classLoader);
                    } else {
                        arrayList.add(classLoaderData2);
                    }
                }
            }
            List<ClassLoaderData> list2 = classLoaderData;
            list2.clear();
            list2.addAll(arrayList);
        }
        return linkedHashSet;
    }

    private final Set<MemoryLeak> inspectStatics(ClassLoader classLoader) throws InterruptedException {
        try {
            Vector vectorLoadedClasses = MemoryLeakDetectorKt.loadedClasses(classLoader);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = vectorLoadedClasses.iterator();
            while (it.hasNext()) {
                linkedHashSet.add((Class) it.next());
            }
            LinkedHashSet<Class> linkedHashSet2 = linkedHashSet;
            LinkedHashSet linkedHashSet3 = new LinkedHashSet();
            for (Class cls : linkedHashSet2) {
                try {
                    Field[] declaredFields = cls.getDeclaredFields();
                    Intrinsics.checkNotNull(declaredFields);
                    for (Field field : declaredFields) {
                        if (Modifier.isStatic(field.getModifiers())) {
                            Intrinsics.checkNotNull(field);
                            Object safe = ReflectionUtilKt.getSafe(field, null);
                            if (safe != null) {
                                Object obj = MemoryLeakDetectorKt.isPrimitiveOrString(safe) ? null : safe;
                                if (obj != null) {
                                    if (MemoryLeakDetectorKt.isJavacComponent(obj)) {
                                        String name2 = cls.getName();
                                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                                        String name3 = field.getName();
                                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                                        linkedHashSet3.add(new MemoryLeak(name2, name3, "Field leaks an Annotation Processing component (" + obj + ")."));
                                    } else if ((obj instanceof Class) && linkedHashSet2.contains(obj)) {
                                        String name4 = cls.getName();
                                        Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                                        String name5 = field.getName();
                                        Intrinsics.checkNotNullExpressionValue(name5, "getName(...)");
                                        linkedHashSet3.add(new MemoryLeak(name4, name5, "Field leaks a class type from the same ClassLoader (" + ((Class) obj).getName() + ")."));
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable unused) {
                }
            }
            return linkedHashSet3;
        } catch (ConcurrentModificationException unused2) {
            Thread.sleep(100L);
            return inspectStatics(classLoader);
        }
    }
}
