package org.jetbrains.kotlin.com.intellij.util;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.model.Symbol;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Trinity;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.RegistryValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public final class IdempotenceChecker {
    private static final Logger LOG = Logger.getInstance((Class<?>) IdempotenceChecker.class);
    private static final Set<Class<?>> ourReportedValueClasses = Collections.synchronizedSet(new HashSet());
    private static final ThreadLocal<Integer> ourRandomCheckNesting = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.IdempotenceChecker$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return IdempotenceChecker.lambda$static$0();
        }
    });
    private static final ThreadLocal<List<String>> ourLog = new ThreadLocal<>();
    private static final RegistryValue ourRateCheckProperty = Registry.get("platform.random.idempotence.check.rate");
    private static final Map<Class, Set<Class>> allSupersWithEquals = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.IdempotenceChecker$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return JBIterable.generate((Class) obj, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.IdempotenceChecker$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj2) {
                    return ((Class) obj2).getSuperclass();
                }
            }).filter(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.util.IdempotenceChecker$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj2) {
                    return IdempotenceChecker.lambda$static$1((Class) obj2);
                }
            }).toSet();
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "recomputeValue";
                break;
            case 3:
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/IdempotenceChecker";
                break;
            case 5:
                objArr[0] = "field";
                break;
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
            case 16:
                objArr[0] = "existing";
                break;
            case 7:
            case 9:
            case 12:
            case 14:
            case 17:
                objArr[0] = "fresh";
                break;
            case 15:
                objArr[0] = "psi";
                break;
            case 18:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            default:
                objArr[0] = "providerClass";
                break;
        }
        if (i == 3) {
            objArr[1] = "recomputeWithLogging";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/IdempotenceChecker";
        } else {
            objArr[1] = "computeWithLogging";
        }
        switch (i) {
            case 1:
                objArr[2] = "reportFailure";
                break;
            case 2:
                objArr[2] = "recomputeWithLogging";
                break;
            case 3:
            case 4:
                break;
            case 5:
            case 6:
            case 7:
                objArr[2] = "whichIsField";
                break;
            case 8:
            case 9:
                objArr[2] = "checkCachedValueData";
                break;
            case 10:
                objArr[2] = "isExpectedToHaveSaneEquals";
                break;
            case 11:
            case 12:
                objArr[2] = "objectsOfDifferentClassesCanStillBeEquivalent";
                break;
            case 13:
            case 14:
                objArr[2] = "checkPsiEquivalence";
                break;
            case 15:
                objArr[2] = "seemsToBeResolveTarget";
                break;
            case 16:
            case 17:
                objArr[2] = "checkCollectionElements";
                break;
            case 18:
                objArr[2] = "logTrace";
                break;
            default:
                objArr[2] = "checkEquivalence";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ Integer lambda$static$0() {
        return 0;
    }

    public static <T> void checkEquivalence(T t, T t2, Class<?> cls, Computable<? extends T> computable) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        String strCheckValueEquivalence = checkValueEquivalence(t, t2);
        if (strCheckValueEquivalence != null) {
            reportFailure(t, t2, cls, computable, strCheckValueEquivalence);
        }
    }

    private static <T> void reportFailure(T t, T t2, Class<?> cls, Computable<? extends T> computable, String str) {
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        if (ApplicationManager.getApplication().isUnitTestMode() || ourReportedValueClasses.add(cls)) {
            if (computable != null) {
                str = str + recomputeWithLogging(t, t2, computable);
            }
            LOG.error((Throwable) PluginException.createByClass(str, null, cls));
        }
    }

    private static <T> String recomputeWithLogging(T t, T t2, Computable<? extends T> computable) {
        String str;
        if (computable == null) {
            $$$reportNull$$$0(2);
        }
        ResultWithLog resultWithLogComputeWithLogging = computeWithLogging(computable);
        Object obj = resultWithLogComputeWithLogging.result;
        String str2 = "\n\nRecomputation gives " + objAndClass(obj);
        if (checkValueEquivalence(t, obj) == null) {
            str = str2 + " which is equivalent to 'existing'";
        } else if (checkValueEquivalence(t2, obj) == null) {
            str = str2 + " which is equivalent to 'fresh'";
        } else {
            str = str2 + " which is different from both values";
        }
        if (!resultWithLogComputeWithLogging.log.isEmpty() && !(obj instanceof ResultWithLog)) {
            str = str + "\nRecomputation log:\n" + resultWithLogComputeWithLogging.printLog();
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> ResultWithLog<T> computeWithLogging(Computable<? extends T> computable) {
        ThreadLocal<List<String>> threadLocal = ourLog;
        List<String> arrayList = threadLocal.get();
        boolean z = arrayList == null;
        if (z) {
            arrayList = new ArrayList<>();
            threadLocal.set(arrayList);
        }
        AnonymousClass1 anonymousClass1 = null;
        try {
            ResultWithLog<T> resultWithLog = new ResultWithLog<>(computable.compute(), new ArrayList(arrayList.subList(arrayList.size(), arrayList.size())));
            if (z) {
            }
            return resultWithLog;
        } finally {
            if (z) {
                ourLog.set(null);
            }
        }
    }

    private static String objAndClass(Object obj) {
        if (obj == null) {
            return "null";
        }
        String string = obj.toString();
        return (string.contains(obj.getClass().getSimpleName()) || (obj instanceof String) || (obj instanceof Number) || (obj instanceof Class)) ? string : string + " (class " + obj.getClass().getName() + Constant.AFTER_QUTO;
    }

    private static String checkValueEquivalence(Object obj, Object obj2) {
        PsiElement element;
        PsiElement element2;
        if (obj == obj2) {
            return null;
        }
        String strCheckClassEquivalence = checkClassEquivalence(obj, obj2);
        if (strCheckClassEquivalence != null) {
            return strCheckClassEquivalence;
        }
        Object[] objArrAsArray = asArray(obj);
        if (objArrAsArray != null) {
            return checkArrayEquivalence(objArrAsArray, (Object[]) Objects.requireNonNull(asArray(obj2)), obj);
        }
        if (obj instanceof ResultWithLog) {
            return whichIsField("result", obj, obj2, checkValueEquivalence(((ResultWithLog) obj).getResult(), ((ResultWithLog) obj2).getResult()));
        }
        if (obj instanceof CachedValueBase.Data) {
            return checkCachedValueData((CachedValueBase.Data) obj, (CachedValueBase.Data) obj2);
        }
        if ((obj instanceof List) || isOrderedSet(obj)) {
            return checkCollectionElements((Collection) obj, (Collection) obj2);
        }
        if (isOrderedMap(obj)) {
            return checkCollectionElements(((Map) obj).entrySet(), ((Map) obj2).entrySet());
        }
        if (obj instanceof Set) {
            return whichIsField("size", obj, obj2, checkCollectionSizes(((Set) obj).size(), ((Set) obj2).size()));
        }
        if (obj instanceof Map) {
            if (obj instanceof ConcurrentMap) {
                return null;
            }
            return whichIsField("size", obj, obj2, checkCollectionSizes(((Map) obj).size(), ((Map) obj2).size()));
        }
        if (isExpectedToHaveSaneEquals(obj) && !obj.equals(obj2)) {
            return reportProblem(obj, obj2);
        }
        if (obj instanceof PsiNamedElement) {
            return checkPsiEquivalence((PsiElement) obj, (PsiElement) obj2);
        }
        if (!(obj instanceof ResolveResult) || (element = ((ResolveResult) obj).getElement()) == (element2 = ((ResolveResult) obj2).getElement())) {
            return null;
        }
        String strCheckClassEquivalence2 = checkClassEquivalence(element, element2);
        if (strCheckClassEquivalence2 == null) {
            strCheckClassEquivalence2 = checkPsiEquivalence(element, element2);
        }
        return whichIsField(CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, obj, obj2, strCheckClassEquivalence2);
    }

    private static boolean isOrderedMap(Object obj) {
        return (obj instanceof LinkedHashMap) || (obj instanceof SortedMap);
    }

    private static boolean isOrderedSet(Object obj) {
        return (obj instanceof LinkedHashSet) || (obj instanceof SortedSet);
    }

    private static String whichIsField(String str, Object obj, Object obj2, String str2) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (obj == null) {
            $$$reportNull$$$0(6);
        }
        if (obj2 == null) {
            $$$reportNull$$$0(7);
        }
        if (str2 == null) {
            return null;
        }
        return appendDetail(str2, "which is " + str + " of " + obj + " and " + obj2);
    }

    private static Object[] asArray(Object obj) {
        if (obj instanceof Object[]) {
            return (Object[]) obj;
        }
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            return new Object[]{entry.getKey(), entry.getValue()};
        }
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            return new Object[]{pair.first, pair.second};
        }
        if (!(obj instanceof Trinity)) {
            return null;
        }
        Trinity trinity = (Trinity) obj;
        return new Object[]{trinity.first, trinity.second, trinity.third};
    }

    private static String checkCachedValueData(CachedValueBase.Data<?> data, CachedValueBase.Data<?> data2) {
        if (data == null) {
            $$$reportNull$$$0(8);
        }
        if (data2 == null) {
            $$$reportNull$$$0(9);
        }
        Object[] dependencies = data.getDependencies();
        Object[] dependencies2 = data2.getDependencies();
        Object obj = data.get();
        Object obj2 = data2.get();
        if (dependencies.length != dependencies2.length) {
            return appendDetail(appendDetail(reportProblem(Integer.valueOf(dependencies.length), Integer.valueOf(dependencies2.length)), "which is length of CachedValue dependencies: " + Arrays.toString(dependencies) + " and " + Arrays.toString(dependencies2)), "where values are  " + objAndClass(obj) + " and " + objAndClass(obj2));
        }
        return checkValueEquivalence(obj, obj2);
    }

    private static boolean isExpectedToHaveSaneEquals(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(10);
        }
        return (obj instanceof Comparable) || (obj instanceof Symbol);
    }

    private static String checkClassEquivalence(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return reportProblem(obj, obj2);
        }
        Class<?> cls = obj.getClass();
        Class<?> cls2 = obj2.getClass();
        if (cls == cls2 || objectsOfDifferentClassesCanStillBeEquivalent(obj, obj2)) {
            return null;
        }
        return whichIsField("class", obj, obj2, reportProblem(cls, cls2));
    }

    private static boolean objectsOfDifferentClassesCanStillBeEquivalent(Object obj, Object obj2) {
        if (obj == null) {
            $$$reportNull$$$0(11);
        }
        if (obj2 == null) {
            $$$reportNull$$$0(12);
        }
        if ((obj instanceof Map) && (obj2 instanceof Map) && isOrderedMap(obj) == isOrderedMap(obj2)) {
            return true;
        }
        if ((obj instanceof Set) && (obj2 instanceof Set) && isOrderedSet(obj) == isOrderedSet(obj2)) {
            return true;
        }
        if ((obj instanceof List) && (obj2 instanceof List)) {
            return true;
        }
        if ((obj instanceof PsiNamedElement) && (obj2 instanceof PsiNamedElement)) {
            return true;
        }
        Map<Class, Set<Class>> map = allSupersWithEquals;
        return ContainerUtil.intersects(map.get(obj.getClass()), map.get(obj2.getClass()));
    }

    static /* synthetic */ boolean lambda$static$1(Class cls) {
        return (cls == Object.class || ReflectionUtil.getDeclaredMethod(cls, Namer.EQUALS_METHOD_NAME, Object.class) == null) ? false : true;
    }

    private static String checkPsiEquivalence(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(14);
        }
        if (psiElement.equals(psiElement2) || psiElement.isEquivalentTo(psiElement2) || psiElement2.isEquivalentTo(psiElement)) {
            return null;
        }
        if (seemsToBeResolveTarget(psiElement) || seemsToBeResolveTarget(psiElement2)) {
            return reportProblem(psiElement, psiElement2);
        }
        return null;
    }

    private static boolean seemsToBeResolveTarget(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        if (psiElement.getIsPhysical()) {
            return true;
        }
        PsiElement navigationElement = psiElement.getNavigationElement();
        return navigationElement != null && navigationElement.getIsPhysical();
    }

    private static String checkCollectionElements(Collection<?> collection, Collection<?> collection2) {
        if (collection == null) {
            $$$reportNull$$$0(16);
        }
        if (collection2 == null) {
            $$$reportNull$$$0(17);
        }
        if (collection2.isEmpty()) {
            return null;
        }
        return checkArrayEquivalence(collection.toArray(), collection2.toArray(), collection);
    }

    private static String checkCollectionSizes(int i, int i2) {
        if (i2 == 0 || i == i2) {
            return null;
        }
        return reportProblem(Integer.valueOf(i), Integer.valueOf(i2));
    }

    private static String checkArrayEquivalence(Object[] objArr, Object[] objArr2, Object obj) {
        String str;
        int length = objArr.length;
        int length2 = objArr2.length;
        if (length != length2) {
            return appendDetail(reportProblem(Integer.valueOf(length), Integer.valueOf(length2)), "which is length of " + Arrays.toString(objArr) + " and " + Arrays.toString(objArr2));
        }
        int i = 0;
        while (i < length) {
            String strCheckValueEquivalence = checkValueEquivalence(objArr[i], objArr2[i]);
            if (strCheckValueEquivalence != null) {
                if (obj instanceof Map.Entry) {
                    str = i == 0 ? ASTExpr.DEFAULT_MAP_KEY_NAME : "value";
                } else {
                    str = i + "th element";
                }
                return whichIsField(str, Arrays.toString(objArr), Arrays.toString(objArr2), strCheckValueEquivalence);
            }
            i++;
        }
        return null;
    }

    private static String reportProblem(Object obj, Object obj2) {
        return appendDetail("Non-idempotent computation: it returns different results when invoked multiple times or on different threads:", objAndClass(obj) + " != " + objAndClass(obj2));
    }

    private static String appendDetail(String str, String str2) {
        return str + "\n  " + StringUtil.trimLog(str2, 10000);
    }

    public static boolean areRandomChecksEnabled() {
        return ApplicationManager.getApplication().isUnitTestMode() && !ApplicationManagerEx.isInStressTest();
    }

    public static <T> void applyForRandomCheck(T t, Object obj, Computable<? extends T> computable) {
        if (areRandomChecksEnabled() && shouldPerformRandomCheck()) {
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            ThreadLocal<Integer> threadLocal = ourRandomCheckNesting;
            Integer num = threadLocal.get();
            threadLocal.set(Integer.valueOf(num.intValue() + 1));
            try {
                T tCompute = computable.compute();
                if (stackStampMarkStack.mayCacheNow()) {
                    checkEquivalence(t, tCompute, obj.getClass(), computable);
                }
            } finally {
                ourRandomCheckNesting.set(num);
            }
        }
    }

    private static boolean shouldPerformRandomCheck() {
        int iAsInteger = ourRateCheckProperty.asInteger();
        return iAsInteger > 0 && ThreadLocalRandom.current().nextInt(iAsInteger) == 0;
    }

    public static boolean isLoggingEnabled() {
        return ourLog.get() != null;
    }

    public static void logTrace(String str) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        List<String> list = ourLog.get();
        if (list != null) {
            list.add(str);
        }
    }

    public static final class ResultWithLog<T> {
        private final List<String> log;
        private final T result;

        private ResultWithLog(T t, List<String> list) {
            this.result = t;
            this.log = list;
        }

        public T getResult() {
            return this.result;
        }

        static /* synthetic */ String lambda$printLog$0(String str) {
            return "  " + str;
        }

        String printLog() {
            return StringUtil.join((Collection) this.log, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.IdempotenceChecker$ResultWithLog$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return IdempotenceChecker.ResultWithLog.lambda$printLog$0((String) obj);
                }
            }, "\n");
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ResultWithLog) {
                return Arrays.deepEquals(new Object[]{this.result}, new Object[]{((ResultWithLog) obj).result});
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.result);
        }

        public String toString() {
            return "ResultWithLog{" + this.result + (this.log.isEmpty() ? "" : ", log='\n" + printLog() + '\'') + '}';
        }
    }
}
