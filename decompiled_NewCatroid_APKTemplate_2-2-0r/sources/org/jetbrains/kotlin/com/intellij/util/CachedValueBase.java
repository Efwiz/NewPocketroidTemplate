package org.jetbrains.kotlin.com.intellij.util;

import java.lang.ref.Reference;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Getter;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProfiler;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.containers.NotNullList;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CachedValueBase<T> {
    private static final Logger LOG = Logger.getInstance((Class<?>) CachedValueImpl.class);
    private volatile SoftReference<Data<T>> myData;
    private final boolean myTrackValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/CachedValueBase";
                break;
            case 2:
            case 3:
                objArr[0] = "data";
                break;
            case 4:
            case 7:
                objArr[0] = "dependency";
                break;
            case 5:
                objArr[0] = "resultingDeps";
                break;
            case 6:
                objArr[0] = "dependencies";
                break;
            default:
                objArr[0] = "result";
                break;
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/CachedValueBase";
        } else {
            objArr[1] = "normalizeDependencies";
        }
        switch (i) {
            case 1:
                break;
            case 2:
                objArr[2] = "checkUpToDate";
                break;
            case 3:
                objArr[2] = "isUpToDate";
                break;
            case 4:
                objArr[2] = "isDependencyOutOfDate";
                break;
            case 5:
            case 6:
                objArr[2] = "collectDependencies";
                break;
            case 7:
                objArr[2] = "getTimeStamp";
                break;
            case 8:
                objArr[2] = "setValue";
                break;
            default:
                objArr[2] = "normalizeDependencies";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    static /* synthetic */ CachedValueProvider.Result lambda$setValue$0(CachedValueProvider.Result result) {
        return result;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: doCompute, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public abstract <P> CachedValueProvider.Result<T> m6701x9c9e63ff(P p);

    public abstract Object getValueProvider();

    public abstract boolean isFromMyProject(Project project);

    protected CachedValueBase(boolean z) {
        this.myTrackValue = z;
    }

    private Data<T> computeData(Computable<? extends CachedValueProvider.Result<T>> computable) {
        CachedValueProvider.Result<T> resultCompute;
        CachedValueProfiler.ValueTracker valueTrackerNewValueTracker;
        if (CachedValueProfiler.isProfiling()) {
            CachedValueProfiler.Frame frameNewFrame = CachedValueProfiler.newFrame();
            try {
                resultCompute = computable.compute();
                valueTrackerNewValueTracker = frameNewFrame.newValueTracker(resultCompute);
                if (frameNewFrame != null) {
                    frameNewFrame.close();
                }
            } catch (Throwable th) {
                if (frameNewFrame != null) {
                    try {
                        frameNewFrame.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } else {
            resultCompute = computable.compute();
            valueTrackerNewValueTracker = null;
        }
        if (resultCompute == null) {
            return new Data<>(null, ArrayUtilRt.EMPTY_OBJECT_ARRAY, ArrayUtil.EMPTY_LONG_ARRAY, null);
        }
        T value = resultCompute.getValue();
        Object[] objArrNormalizeDependencies = normalizeDependencies(resultCompute);
        long[] jArr = new long[objArrNormalizeDependencies.length];
        for (int i = 0; i < objArrNormalizeDependencies.length; i++) {
            jArr[i] = getTimeStamp(objArrNormalizeDependencies[i]);
        }
        return new Data<>(value, objArrNormalizeDependencies, jArr, valueTrackerNewValueTracker);
    }

    private synchronized Data<T> cacheOrGetData(Data<T> data, Data<T> data2) {
        if (data != getRawData()) {
            return null;
        }
        if (data2 == null) {
            return data;
        }
        setData(data2);
        return data2;
    }

    private synchronized void setData(Data<T> data) {
        this.myData = data == null ? null : new SoftReference<>(data);
    }

    protected Object[] normalizeDependencies(CachedValueProvider.Result<T> result) {
        if (result == null) {
            $$$reportNull$$$0(0);
        }
        Object[] dependencyItems = result.getDependencyItems();
        T value = result.getValue();
        if (this.myTrackValue && value != null) {
            dependencyItems = ArrayUtil.append(dependencyItems, value);
        }
        NotNullList notNullList = new NotNullList(dependencyItems.length);
        collectDependencies(notNullList, dependencyItems);
        Object[] objectArray = ArrayUtil.toObjectArray(notNullList);
        if (objectArray == null) {
            $$$reportNull$$$0(1);
        }
        return objectArray;
    }

    public void clear() {
        setData(null);
    }

    public boolean hasUpToDateValue() {
        return getUpToDateOrNull() != null;
    }

    public final Data<T> getUpToDateOrNull() {
        Data<T> rawData = getRawData();
        if (rawData == null || !checkUpToDate(rawData)) {
            return null;
        }
        return rawData;
    }

    private boolean checkUpToDate(Data<T> data) {
        if (data == null) {
            $$$reportNull$$$0(2);
        }
        if (isUpToDate(data)) {
            return true;
        }
        if (data.trackingInfo == null) {
            return false;
        }
        data.trackingInfo.onValueInvalidated();
        return false;
    }

    private Data<T> getRawData() {
        return (Data) SoftReference.dereference(this.myData);
    }

    protected boolean isUpToDate(Data<T> data) {
        if (data == null) {
            $$$reportNull$$$0(3);
        }
        for (int i = 0; i < ((Data) data).myDependencies.length; i++) {
            if (isDependencyOutOfDate(((Data) data).myDependencies[i], ((Data) data).myTimeStamps[i])) {
                return false;
            }
        }
        return true;
    }

    protected boolean isDependencyOutOfDate(Object obj, long j) {
        if (obj == null) {
            $$$reportNull$$$0(4);
        }
        if (obj instanceof CachedValueBase) {
            return !((CachedValueBase) obj).hasUpToDateValue();
        }
        long timeStamp = getTimeStamp(obj);
        return timeStamp < 0 || timeStamp != j;
    }

    private static void collectDependencies(List<Object> list, Object[] objArr) {
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        if (objArr == null) {
            $$$reportNull$$$0(6);
        }
        for (Object obj : objArr) {
            if (obj != ObjectUtils.NULL) {
                if (obj instanceof Object[]) {
                    collectDependencies(list, (Object[]) obj);
                } else {
                    list.add(obj);
                }
            }
        }
    }

    protected long getTimeStamp(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(7);
        }
        if (obj instanceof VirtualFile) {
            return ((VirtualFile) obj).getModificationStamp();
        }
        if (obj instanceof ModificationTracker) {
            return ((ModificationTracker) obj).getModificationCount();
        }
        if (obj instanceof Reference) {
            Object obj2 = ((Reference) obj).get();
            if (obj2 == null) {
                return -1L;
            }
            return getTimeStamp(obj2);
        }
        if (obj instanceof Ref) {
            Object obj3 = ((Ref) obj).get();
            if (obj3 == null) {
                return -1L;
            }
            return getTimeStamp(obj3);
        }
        if (obj instanceof Document) {
            return ((Document) obj).getModificationStamp();
        }
        if (obj instanceof CachedValueBase) {
            return 0L;
        }
        LOG.error("Wrong dependency type: " + obj.getClass());
        return -1L;
    }

    public T setValue(final CachedValueProvider.Result<T> result) {
        if (result == null) {
            $$$reportNull$$$0(8);
        }
        Data<T> dataComputeData = computeData(new Computable() { // from class: org.jetbrains.kotlin.com.intellij.util.CachedValueBase$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return CachedValueBase.lambda$setValue$0(result);
            }
        });
        setData(dataComputeData);
        return dataComputeData.getValue();
    }

    protected static final class Data<T> implements Getter<T> {
        private final Object[] myDependencies;
        private final long[] myTimeStamps;
        private final T myValue;
        final CachedValueProfiler.ValueTracker trackingInfo;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "timeStamps";
            } else if (i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/CachedValueBase$Data";
            } else {
                objArr[0] = "dependencies";
            }
            if (i == 2) {
                objArr[1] = "getDependencies";
            } else if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/CachedValueBase$Data";
            } else {
                objArr[1] = "getTimeStamps";
            }
            if (i != 2 && i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        Data(T t, Object[] objArr, long[] jArr, CachedValueProfiler.ValueTracker valueTracker) {
            if (objArr == null) {
                $$$reportNull$$$0(0);
            }
            if (jArr == null) {
                $$$reportNull$$$0(1);
            }
            this.myValue = t;
            this.myDependencies = objArr;
            this.myTimeStamps = jArr;
            this.trackingInfo = valueTracker;
        }

        public Object[] getDependencies() {
            Object[] objArr = this.myDependencies;
            if (objArr == null) {
                $$$reportNull$$$0(2);
            }
            return objArr;
        }

        public long[] getTimeStamps() {
            long[] jArr = this.myTimeStamps;
            if (jArr == null) {
                $$$reportNull$$$0(3);
            }
            return jArr;
        }

        @Override // java.util.function.Supplier
        public T get() {
            return getValue();
        }

        public T getValue() {
            CachedValueProfiler.ValueTracker valueTracker = this.trackingInfo;
            if (valueTracker != null) {
                valueTracker.onValueUsed();
            }
            return this.myValue;
        }
    }

    protected <P> T getValueWithLock(final P p) {
        Data<T> dataCacheOrGetData;
        Data<T> upToDateOrNull = getUpToDateOrNull();
        if (upToDateOrNull != null) {
            if (IdempotenceChecker.areRandomChecksEnabled()) {
                IdempotenceChecker.applyForRandomCheck(upToDateOrNull, getValueProvider(), new Computable() { // from class: org.jetbrains.kotlin.com.intellij.util.CachedValueBase$$ExternalSyntheticLambda2
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                    public final Object compute() {
                        return this.f$0.m6700x9b681120(p);
                    }
                });
            }
            return upToDateOrNull.getValue();
        }
        RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
        Computable computable = new Computable() { // from class: org.jetbrains.kotlin.com.intellij.util.CachedValueBase$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6702x9dd4b6de(p);
            }
        };
        Data<T> data = (Data) RecursionManager.doPreventingRecursion(this, true, computable);
        if (data == null) {
            data = (Data) computable.compute();
        } else if (stackStampMarkStack.mayCacheNow()) {
            do {
                Data<T> rawData = getRawData();
                boolean z = rawData != null && checkUpToDate(rawData);
                if (z) {
                    IdempotenceChecker.checkEquivalence(rawData, data, getValueProvider().getClass(), computable);
                }
                dataCacheOrGetData = cacheOrGetData(rawData, z ? null : data);
            } while (dataCacheOrGetData == null);
            if (data != dataCacheOrGetData && data.trackingInfo != null) {
                data.trackingInfo.onValueRejected();
            }
            return dataCacheOrGetData.getValue();
        }
        return data.getValue();
    }

    /* JADX INFO: renamed from: lambda$getValueWithLock$2$org-jetbrains-kotlin-com-intellij-util-CachedValueBase, reason: not valid java name */
    /* synthetic */ Data m6700x9b681120(final Object obj) {
        return computeData(new Computable() { // from class: org.jetbrains.kotlin.com.intellij.util.CachedValueBase$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6699x9a31be41(obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getValueWithLock$4$org-jetbrains-kotlin-com-intellij-util-CachedValueBase, reason: not valid java name */
    /* synthetic */ Data m6702x9dd4b6de(final Object obj) {
        return computeData(new Computable() { // from class: org.jetbrains.kotlin.com.intellij.util.CachedValueBase$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return this.f$0.m6701x9c9e63ff(obj);
            }
        });
    }

    public String toString() {
        return getClass().getSimpleName() + "{" + getValueProvider() + "}";
    }
}
