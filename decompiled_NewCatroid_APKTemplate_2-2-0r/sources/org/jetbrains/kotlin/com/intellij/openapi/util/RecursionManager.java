package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class RecursionManager {
    private static final Logger LOG = Logger.getInstance((Class<?>) RecursionManager.class);
    private static final ThreadLocal<CalculationStack> ourStack = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return RecursionManager.lambda$static$0();
        }
    });
    private static final AtomicBoolean ourAssertOnPrevention = new AtomicBoolean();
    private static final AtomicBoolean ourAssertOnMissedCache = new AtomicBoolean();
    private static final String[] toleratedFrames = {"org.jetbrains.kotlin.com.intellij.psi.impl.source.xml.XmlAttributeImpl.getDescriptor(", "org.jetbrains.plugins.ruby.ruby.codeInsight.symbols.structure.util.SymbolHierarchy.getAncestorsCaching(", "org.jetbrains.kotlin.com.intellij.lang.aspectj.psi.impl.PsiInterTypeReferenceImpl.", "org.jetbrains.kotlin.com.intellij.psi.impl.search.JavaDirectInheritorsSearcher.processConcurrentlyIfTooMany(", "org.jetbrains.kotlin.com.intellij.lang.javascript.psi.resolve.JSEvaluatorComplexityTracker.doRunTask(", "org.jetbrains.kotlin.com.intellij.lang.javascript.ecmascript6.types.JSTypeSignatureChooser.chooseOverload(", "org.jetbrains.kotlin.com.intellij.lang.javascript.psi.resolve.JSEvaluationCache.getElementType(", "org.jetbrains.kotlin.com.intellij.lang.ecmascript6.psi.impl.ES6ImportSpecifierImpl.multiResolve(", "org.jetbrains.kotlin.com.intellij.lang.javascript.psi.types.JSTypeBaseImpl.substitute(", "org.jetbrains.kotlin.com.intellij.psi.ThreadLocalTypes.performWithTypes(", "org.jetbrains.kotlin.com.intellij.xml.impl.schema.XmlNSDescriptorImpl.getRedefinedElementDescriptor(", "org.jetbrains.kotlin.com.intellij.psi.impl.source.xml.XmlTagImpl.getDescriptor(", "org.jetbrains.kotlin.com.intellij.psi.impl.source.xml.XmlTagDelegate.getNSDescriptor(", "org.jetbrains.kotlin.com.intellij.xml.impl.schema.XmlNSDescriptorImpl.findTypeDescriptor(", "org.jetbrains.kotlin.com.intellij.psi.impl.source.xml.XmlEntityRefImpl.doResolveEntity(", "org.jetbrains.kotlin.com.intellij.xml.impl.dtd.XmlNSDescriptorImpl.getElementDescriptor(", "com.jetbrains.python.psi.PyKnownDecoratorUtil.resolveDecorator(", "com.jetbrains.python.psi.impl.references.PyReferenceImpl.multiResolve("};

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[0] = "parentDisposable";
        } else {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/RecursionManager";
        if (i == 1) {
            objArr[2] = "assertOnRecursionPrevention";
        } else if (i == 2) {
            objArr[2] = "setFlag";
        } else if (i == 3) {
            objArr[2] = "disableAssertOnRecursionPrevention";
        } else if (i == 4) {
            objArr[2] = "disableMissedCacheAssertions";
        } else if (i != 5) {
            objArr[2] = "doPreventingRecursion";
        } else {
            objArr[2] = "assertOnMissedCache";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static /* synthetic */ CalculationStack lambda$static$0() {
        return new CalculationStack();
    }

    public static <T> T doPreventingRecursion(Object obj, boolean z, Computable<T> computable) {
        if (obj == null) {
            $$$reportNull$$$0(0);
        }
        return (T) createGuard(computable.getClass().getName()).doPreventingRecursion(obj, z, computable);
    }

    public static <Key> RecursionGuard<Key> createGuard(final String str) {
        return new RecursionGuard<Key>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str2 = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "computation";
                } else if (i == 2) {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/RecursionManager$1";
                } else if (i != 3) {
                    objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                } else {
                    objArr[0] = "since";
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/RecursionManager$1";
                } else {
                    objArr[1] = "currentStack";
                }
                if (i != 2) {
                    if (i != 3) {
                        objArr[2] = "computePreventingRecursion";
                    } else {
                        objArr[2] = "prohibitResultCaching";
                    }
                }
                String str3 = String.format(str2, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str3);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard
            public <T, E extends Throwable> T computePreventingRecursion(Key key, boolean z, ThrowableComputable<T, E> throwableComputable) throws Throwable {
                MemoizedValue memoizedValue;
                if (key == null) {
                    $$$reportNull$$$0(0);
                }
                if (throwableComputable == null) {
                    $$$reportNull$$$0(1);
                }
                MyKey myKey = new MyKey(str, key, true);
                CalculationStack calculationStack = (CalculationStack) RecursionManager.ourStack.get();
                if (calculationStack.checkReentrancy(myKey)) {
                    if (!RecursionManager.ourAssertOnPrevention.get()) {
                        if (!RecursionManager.LOG.isDebugEnabled()) {
                            return null;
                        }
                        RecursionManager.LOG.debug(new StackOverflowPreventedException("Endless recursion prevention occurred on " + key));
                        return null;
                    }
                    throw new StackOverflowPreventedException("Endless recursion prevention occurred on " + key);
                }
                if (z && (memoizedValue = (MemoizedValue) calculationStack.intermediateCache.get(myKey)) != null) {
                    for (MyKey myKey2 : memoizedValue.dependencies) {
                        calculationStack.prohibitResultCaching(myKey2);
                    }
                    return (T) memoizedValue.value;
                }
                MyKey myKey3 = new MyKey(str, key, false);
                int size = calculationStack.progressMap.size();
                StackFrame stackFrameBeforeComputation = calculationStack.beforeComputation(myKey3);
                int size2 = calculationStack.progressMap.size();
                try {
                    T tCompute = throwableComputable.compute();
                    if (z && stackFrameBeforeComputation.preventionsInside != null) {
                        calculationStack.memoize(myKey3, tCompute, stackFrameBeforeComputation.preventionsInside);
                    }
                    try {
                        calculationStack.afterComputation(myKey3, size, size2);
                        calculationStack.checkDepth("4");
                        return tCompute;
                    } catch (Throwable th) {
                        throw new RuntimeException("Throwable in afterComputation", th);
                    }
                } catch (Throwable th2) {
                    try {
                        calculationStack.afterComputation(myKey3, size, size2);
                        calculationStack.checkDepth("4");
                        throw th2;
                    } catch (Throwable th3) {
                        throw new RuntimeException("Throwable in afterComputation", th3);
                    }
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard
            public List<Key> currentStack() {
                ArrayList arrayList = new ArrayList();
                for (MyKey myKey : ((CalculationStack) RecursionManager.ourStack.get()).progressMap.keySet()) {
                    if (myKey.guardId.equals(str)) {
                        arrayList.add(myKey.userObject);
                    }
                }
                List<Key> listUnmodifiableList = Collections.unmodifiableList(arrayList);
                if (listUnmodifiableList == null) {
                    $$$reportNull$$$0(2);
                }
                return listUnmodifiableList;
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard
            public void prohibitResultCaching(Object obj) {
                if (obj == null) {
                    $$$reportNull$$$0(3);
                }
                ((CalculationStack) RecursionManager.ourStack.get()).prohibitResultCaching(new MyKey(str, obj, false));
            }
        };
    }

    public static RecursionGuard.StackStamp markStack() {
        final int i = ourStack.get().reentrancyCount;
        return new RecursionGuard.StackStamp() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager.2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard.StackStamp
            public boolean mayCacheNow() {
                CalculationStack calculationStack = (CalculationStack) RecursionManager.ourStack.get();
                boolean z = i == calculationStack.reentrancyCount;
                if (z || !RecursionManager.ourAssertOnMissedCache.get() || calculationStack.isCurrentNonCachingStillTolerated()) {
                    return z;
                }
                throw new CachingPreventedException(calculationStack.preventions);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MyKey {
        final String guardId;
        private final boolean myCallEquals;
        private final int myHashCode;
        final Object userObject;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "userObject", "org/jetbrains/kotlin/com/intellij/openapi/util/RecursionManager$MyKey", "<init>"));
        }

        MyKey(String str, Object obj, boolean z) {
            if (obj == null) {
                $$$reportNull$$$0(0);
            }
            this.guardId = str;
            this.userObject = obj;
            RecursionManager.LOG.assertTrue(!obj.getClass().isArray(), "Arrays use the default hashCode/equals implementation");
            this.myHashCode = (str.hashCode() * 31) + obj.hashCode();
            this.myCallEquals = z;
        }

        public boolean equals(Object obj) {
            if (obj instanceof MyKey) {
                MyKey myKey = (MyKey) obj;
                if (this.guardId.equals(myKey.guardId)) {
                    Object obj2 = this.userObject;
                    Object obj3 = myKey.userObject;
                    if (obj2 == obj3) {
                        return true;
                    }
                    if (this.myCallEquals || myKey.myCallEquals) {
                        return obj2.equals(obj3);
                    }
                    return false;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.myHashCode;
        }

        public String toString() {
            return this.guardId + "->" + this.userObject;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CalculationStack {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int depth;
        private int enters;
        private int exits;
        private int firstLoopStart;
        private final Map<MyKey, MemoizedValue> intermediateCache;
        private final Map<MyKey, Throwable> preventions;
        private final LinkedHashMap<MyKey, StackFrame> progressMap;
        private int reentrancyCount;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "preventionsInside", "org/jetbrains/kotlin/com/intellij/openapi/util/RecursionManager$CalculationStack", "memoize"));
        }

        private CalculationStack() {
            this.firstLoopStart = Integer.MAX_VALUE;
            this.progressMap = new LinkedHashMap<>();
            this.preventions = new IdentityHashMap();
            this.intermediateCache = ContainerUtil.createSoftKeySoftValueMap();
        }

        boolean checkReentrancy(MyKey myKey) {
            if (!this.progressMap.containsKey(myKey)) {
                return false;
            }
            prohibitResultCaching(myKey);
            return true;
        }

        StackFrame beforeComputation(MyKey myKey) {
            this.enters++;
            this.progressMap.isEmpty();
            checkDepth(SchemaSymbols.ATTVAL_TRUE_1);
            int size = this.progressMap.size();
            StackFrame stackFrame = new StackFrame();
            stackFrame.reentrancyStamp = this.reentrancyCount;
            this.progressMap.put(myKey, stackFrame);
            this.depth++;
            checkDepth("2");
            int size2 = this.progressMap.size();
            if (size2 != size + 1) {
                RecursionManager.LOG.error("Key doesn't lead to the map size increase: " + size + " " + size2 + " " + myKey.userObject);
            }
            return stackFrame;
        }

        void memoize(MyKey myKey, Object obj, Set<MyKey> set) {
            if (set == null) {
                $$$reportNull$$$0(0);
            }
            this.intermediateCache.put(myKey, new MemoizedValue(obj, (MyKey[]) set.toArray(new MyKey[0])));
        }

        void afterComputation(MyKey myKey, int i, int i2) {
            this.exits++;
            if (i2 != this.progressMap.size()) {
                RecursionManager.LOG.error("Map size changed: " + this.progressMap.size() + " " + i2 + " " + myKey.userObject);
            }
            if (this.depth != this.progressMap.size()) {
                RecursionManager.LOG.error("Inconsistent depth after computation; depth=" + this.depth + "; map=" + this.progressMap);
            }
            StackFrame stackFrameRemove = this.progressMap.remove(myKey);
            this.depth--;
            if (!this.preventions.isEmpty()) {
                this.preventions.remove(myKey);
            }
            if (this.depth <= this.firstLoopStart) {
                this.firstLoopStart = Integer.MAX_VALUE;
                this.intermediateCache.clear();
            }
            if (i != this.progressMap.size()) {
                RecursionManager.LOG.error("Map size doesn't decrease: " + this.progressMap.size() + " " + i + " " + myKey.userObject);
            }
            this.reentrancyCount = stackFrameRemove.reentrancyStamp;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void prohibitResultCaching(final MyKey myKey) {
            this.reentrancyCount++;
            ArrayList arrayList = new ArrayList(this.progressMap.entrySet());
            int iIndexOf = ContainerUtil.indexOf(arrayList, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager$CalculationStack$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ((RecursionManager.MyKey) ((Map.Entry) obj).getKey()).equals(myKey);
                }
            });
            if (iIndexOf >= 0) {
                MyKey myKey2 = (MyKey) ((Map.Entry) arrayList.get(iIndexOf)).getKey();
                if (!this.preventions.containsKey(myKey2)) {
                    this.preventions.put(myKey2, RecursionManager.ourAssertOnMissedCache.get() ? new StackOverflowPreventedException(null) : null);
                }
                for (int i = iIndexOf + 1; i < arrayList.size(); i++) {
                    ((StackFrame) ((Map.Entry) arrayList.get(i)).getValue()).addPrevention(this.reentrancyCount, myKey2);
                }
                if (RecursionManager.LOG.isDebugEnabled() && iIndexOf < arrayList.size() - 1) {
                    RecursionManager.LOG.debug("Recursion prevented for " + myKey + ", caching disabled for " + ContainerUtil.map((Collection) arrayList.subList(iIndexOf, arrayList.size()), new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager$CalculationStack$$ExternalSyntheticLambda3
                        @Override // org.jetbrains.kotlin.com.intellij.util.Function
                        public final Object fun(Object obj) {
                            return (RecursionManager.MyKey) ((Map.Entry) obj).getKey();
                        }
                    }));
                }
                if (this.firstLoopStart > iIndexOf) {
                    this.firstLoopStart = iIndexOf;
                    this.intermediateCache.clear();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkDepth(String str) {
            int i = this.depth;
            if (i == this.progressMap.size()) {
                return;
            }
            this.depth = this.progressMap.size();
            throw new AssertionError("_Inconsistent depth " + str + "; depth=" + i + "; enters=" + this.enters + "; exits=" + this.exits + "; map=" + this.progressMap);
        }

        boolean isCurrentNonCachingStillTolerated() {
            return isCurrentNonCachingStillTolerated(new Throwable()) || ContainerUtil.exists(this.preventions.values(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager$CalculationStack$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return RecursionManager.CalculationStack.isCurrentNonCachingStillTolerated((Throwable) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isCurrentNonCachingStillTolerated(Throwable th) {
            final String throwableText = ExceptionUtil.getThrowableText(th);
            String[] strArr = RecursionManager.toleratedFrames;
            Objects.requireNonNull(throwableText);
            return ContainerUtil.exists(strArr, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager$CalculationStack$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return throwableText.contains((String) obj);
                }
            });
        }
    }

    private static class MemoizedValue {
        final MyKey[] dependencies;
        final Object value;

        MemoizedValue(Object obj, MyKey[] myKeyArr) {
            this.value = obj;
            this.dependencies = myKeyArr;
        }
    }

    private static class StackFrame {
        Set<MyKey> preventionsInside;
        int reentrancyStamp;

        private StackFrame() {
        }

        void addPrevention(int i, MyKey myKey) {
            this.reentrancyStamp = i;
            if (this.preventionsInside == null) {
                this.preventionsInside = new HashSet();
            }
            this.preventionsInside.add(myKey);
        }
    }

    static class CachingPreventedException extends RuntimeException {
        CachingPreventedException(Map<MyKey, Throwable> map) {
            super("Caching disabled due to recursion prevention, please get rid of cyclic dependencies. Preventions: " + new ArrayList(map.keySet()), (Throwable) ContainerUtil.getFirstItem(map.values()));
        }
    }
}
