package org.jetbrains.kotlin.com.intellij.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractQuery<Result> implements Query<Result> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Comparator<Object> CRAZY_ORDER;
    private static final boolean RANDOMIZE;
    private final ThreadLocal<Boolean> myIsProcessing = new ThreadLocal<>();

    /* JADX WARN: Code duplicated, block: B:28:0x003b  */
    /* JADX WARN: Code duplicated, block: B:29:0x0040  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 3 : 2];
        if (i == 1 || i == 3 || i == 4) {
            objArr[0] = "consumer";
        } else if (i == 5) {
            objArr[0] = "query";
        } else if (i == 6) {
            objArr[0] = "consumer";
        } else if (i != 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/AbstractQuery";
        } else {
            objArr[0] = "query";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/AbstractQuery";
                break;
            case 2:
                objArr[1] = "threadSafeProcessor";
                break;
            default:
                objArr[1] = "findAll";
                break;
        }
        if (i == 1) {
            objArr[2] = "threadSafeProcessor";
        } else if (i == 3) {
            objArr[2] = "forEach";
        } else if (i == 4) {
            objArr[2] = "doProcessResults";
        } else if (i == 5 || i == 6) {
            objArr[2] = "delegateProcessResults";
        } else if (i == 7) {
            objArr[2] = "wrapInReadAction";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    private void assertNotProcessing() {
    }

    protected abstract boolean processResults(Processor<? super Result> processor);

    static {
        RANDOMIZE = ApplicationManager.getApplication().isUnitTestMode() || ApplicationManager.getApplication().isInternal();
        CRAZY_ORDER = new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.util.AbstractQuery$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return AbstractQuery.lambda$static$0(obj, obj2);
            }
        };
    }

    static /* synthetic */ int lambda$static$0(Object obj, Object obj2) {
        return -Integer.compare(System.identityHashCode(obj), System.identityHashCode(obj2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Query
    public Collection<Result> findAll() {
        assertNotProcessing();
        ArrayList arrayList = new ArrayList();
        forEach(Processors.cancelableCollectProcessor(arrayList));
        if (RANDOMIZE && arrayList.size() > 1) {
            arrayList.sort(CRAZY_ORDER);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Query, java.lang.Iterable
    public Iterator<Result> iterator() {
        assertNotProcessing();
        return new UnmodifiableIterator(super.iterator());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Query
    public Result findFirst() {
        assertNotProcessing();
        CommonProcessors.FindFirstProcessor findFirstProcessor = new CommonProcessors.FindFirstProcessor();
        forEach(findFirstProcessor);
        return (Result) findFirstProcessor.getFoundValue();
    }

    private Processor<Result> threadSafeProcessor(final Processor<? super Result> processor) {
        if (processor == null) {
            $$$reportNull$$$0(1);
        }
        final Object objSentinel = ObjectUtils.sentinel("AbstractQuery lock");
        return new Processor() { // from class: org.jetbrains.kotlin.com.intellij.util.AbstractQuery$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return AbstractQuery.lambda$threadSafeProcessor$1(objSentinel, processor, obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$threadSafeProcessor$1(Object obj, Processor processor, Object obj2) {
        boolean zProcess;
        synchronized (obj) {
            zProcess = processor.process(obj2);
        }
        return zProcess;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Query
    public boolean forEach(Processor<? super Result> processor) {
        if (processor == null) {
            $$$reportNull$$$0(3);
        }
        return doProcessResults(threadSafeProcessor(processor));
    }

    private boolean doProcessResults(Processor<? super Result> processor) {
        if (processor == null) {
            $$$reportNull$$$0(4);
        }
        assertNotProcessing();
        this.myIsProcessing.set(true);
        try {
            return processResults(processor);
        } finally {
            this.myIsProcessing.remove();
        }
    }

    protected static <T> boolean delegateProcessResults(Query<T> query, Processor<? super T> processor) {
        if (query == null) {
            $$$reportNull$$$0(5);
        }
        if (processor == null) {
            $$$reportNull$$$0(6);
        }
        if (query instanceof AbstractQuery) {
            return ((AbstractQuery) query).doProcessResults(processor);
        }
        return query.forEach(processor);
    }
}
