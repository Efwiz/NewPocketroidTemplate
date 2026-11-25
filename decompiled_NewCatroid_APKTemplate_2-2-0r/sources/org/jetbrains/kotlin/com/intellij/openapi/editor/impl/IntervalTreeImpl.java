package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.MarkupIterator;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.RangeMarkerEx;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.WalkingState;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSet;

/* JADX INFO: loaded from: classes3.dex */
abstract class IntervalTreeImpl<T> extends RedBlackTree<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean DEBUG;
    private static final IntervalTreeGuide<?> INTERVAL_TREE_GUIDE_INSTANCE;
    static final Logger LOG;
    private int deadReferenceCount;
    private boolean firingBeforeRemove;
    private int keySize;
    final ReadWriteLock l = new ReentrantReadWriteLock();
    private final ReferenceQueue<T> myReferenceQueue = new ReferenceQueue<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 13 || i == 20 || i == 26 || i == 33 || i == 47 || i == 53 || i == 16 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 13 || i == 20 || i == 26 || i == 33 || i == 47 || i == 53 || i == 16 || i == 17) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "i1";
                break;
            case 2:
                objArr[0] = "i2";
                break;
            case 3:
                objArr[0] = "invalid";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[0] = "processor";
                break;
            case 12:
            case 14:
                objArr[0] = "rangeInterval";
                break;
            case 13:
            case 16:
            case 17:
            case 20:
            case 26:
            case 33:
            case 47:
            case 53:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/IntervalTreeImpl";
                break;
            case 15:
            case 29:
            case 36:
            case 39:
                objArr[0] = "node";
                break;
            case 18:
                objArr[0] = "collectedAway";
                break;
            case 19:
            case 27:
            case 28:
            case 40:
            case 41:
                objArr[0] = "interval";
                break;
            case 21:
                objArr[0] = "allValid";
                break;
            case 22:
                objArr[0] = "ids";
                break;
            case 23:
                objArr[0] = "keyCounter";
                break;
            case 24:
                objArr[0] = "nodeCounter";
                break;
            case 25:
            case 30:
            case 37:
            case 38:
                objArr[0] = "n";
                break;
            case 31:
                objArr[0] = "root";
                break;
            case 32:
                objArr[0] = "maxPred";
                break;
            case 34:
                objArr[0] = "n1";
                break;
            case 35:
                objArr[0] = "n2";
                break;
            case 42:
                objArr[0] = "gced";
                break;
            case 43:
            case 45:
                objArr[0] = "markerEx";
                break;
            case 44:
            case 46:
                objArr[0] = "reason";
                break;
            case 48:
                objArr[0] = "tree1";
                break;
            case 49:
                objArr[0] = "tree1Range";
                break;
            case 50:
                objArr[0] = "tree2";
                break;
            case 51:
                objArr[0] = "tree2Range";
                break;
            case 52:
                objArr[0] = "comparator";
                break;
            case 54:
            case 55:
                objArr[0] = "marker";
                break;
            default:
                objArr[0] = "l";
                break;
        }
        if (i == 13) {
            objArr[1] = "overlappingIterator";
        } else if (i == 20) {
            objArr[1] = "addInterval";
        } else if (i == 26) {
            objArr[1] = "maximumNode";
        } else if (i == 33) {
            objArr[1] = "swapWithMaxPred";
        } else if (i == 47) {
            objArr[1] = "getGuide";
        } else if (i == 53) {
            objArr[1] = "mergingOverlappingIterator";
        } else if (i == 16 || i == 17) {
            objArr[1] = "findOrInsert";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/IntervalTreeImpl";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "compareNodes";
                break;
            case 4:
                objArr[2] = "processAll";
                break;
            case 5:
                objArr[2] = "process";
                break;
            case 6:
            case 7:
                objArr[2] = "processOverlappingWith";
                break;
            case 8:
            case 9:
                objArr[2] = "processOverlappingWithOutside";
                break;
            case 10:
            case 11:
                objArr[2] = "processContaining";
                break;
            case 12:
                objArr[2] = "overlappingIterator";
                break;
            case 13:
            case 16:
            case 17:
            case 20:
            case 26:
            case 33:
            case 47:
            case 53:
                break;
            case 14:
                objArr[2] = "overlaps";
                break;
            case 15:
                objArr[2] = "findOrInsert";
                break;
            case 18:
                objArr[2] = "deleteNodes";
                break;
            case 19:
                objArr[2] = "addInterval";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "checkMax";
                break;
            case 25:
                objArr[2] = "maximumNode";
                break;
            case 27:
                objArr[2] = "checkBelongsToTheTree";
                break;
            case 28:
                objArr[2] = "removeInterval";
                break;
            case 29:
                objArr[2] = "removeNode";
                break;
            case 30:
                objArr[2] = "deleteNode";
                break;
            case 31:
            case 32:
                objArr[2] = "swapWithMaxPred";
                break;
            case 34:
            case 35:
                objArr[2] = "swapNodes";
                break;
            case 36:
                objArr[2] = "correctMax";
                break;
            case 37:
                objArr[2] = "rotateRight";
                break;
            case 38:
                objArr[2] = "rotateLeft";
                break;
            case 39:
                objArr[2] = "replaceNode";
                break;
            case 40:
                objArr[2] = "findMinOverlappingWith";
                break;
            case 41:
                objArr[2] = "changeData";
                break;
            case 42:
                objArr[2] = "collectGced";
                break;
            case 43:
            case 44:
                objArr[2] = "fireBeforeRemoved";
                break;
            case 45:
            case 46:
                objArr[2] = "beforeRemove";
                break;
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                objArr[2] = "mergingOverlappingIterator";
                break;
            case 54:
                objArr[2] = "findRangeMarkerAfter";
                break;
            case 55:
                objArr[2] = "findRangeMarkerBefore";
                break;
            default:
                objArr[2] = "isAcquired";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 20 && i != 26 && i != 33 && i != 47 && i != 53 && i != 16 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract int compareEqualStartIntervals(IntervalNode<T> intervalNode, IntervalNode<T> intervalNode2);

    void fireBeforeRemoved(T t, Object obj) {
        if (t == null) {
            $$$reportNull$$$0(43);
        }
        if (obj == null) {
            $$$reportNull$$$0(44);
        }
    }

    protected boolean keepIntervalsOnWeakReferences() {
        return true;
    }

    protected abstract IntervalNode<T> lookupNode(T t);

    protected abstract void setNode(T t, IntervalNode<T> intervalNode);

    IntervalTreeImpl() {
    }

    static /* synthetic */ int access$208(IntervalTreeImpl intervalTreeImpl) {
        int i = intervalTreeImpl.keySize;
        intervalTreeImpl.keySize = i + 1;
        return i;
    }

    static /* synthetic */ int access$210(IntervalTreeImpl intervalTreeImpl) {
        int i = intervalTreeImpl.keySize;
        intervalTreeImpl.keySize = i - 1;
        return i;
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) RangeMarkerTree.class);
        LOG = logger;
        DEBUG = logger.isDebugEnabled() || (ApplicationManager.getApplication() != null && ApplicationManager.getApplication().isUnitTestMode());
        INTERVAL_TREE_GUIDE_INSTANCE = new IntervalTreeGuide<>(null);
    }

    static class IntervalNode<E> extends RedBlackTree.Node<E> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final AtomicLongFieldUpdater<IntervalNode> cachedDeltaUpdater = AtomicLongFieldUpdater.newUpdater(IntervalNode.class, "cachedDeltaUpToRoot");
        private volatile long cachedDeltaUpToRoot;
        int delta;
        final List<Supplier<E>> intervals;
        int maxEnd;
        private volatile int myEnd;
        private final IntervalTreeImpl<E> myIntervalTree;
        private volatile int myStart;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 7 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 7 ? 3 : 2];
            switch (i) {
                case 1:
                case 3:
                    objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                    break;
                case 2:
                    objArr[0] = "processor";
                    break;
                case 4:
                case 6:
                    objArr[0] = "interval";
                    break;
                case 5:
                    objArr[0] = "otherNode";
                    break;
                case 7:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/IntervalTreeImpl$IntervalNode";
                    break;
                default:
                    objArr[0] = "intervalTree";
                    break;
            }
            if (i != 7) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/IntervalTreeImpl$IntervalNode";
            } else {
                objArr[1] = "getTree";
            }
            switch (i) {
                case 2:
                    objArr[2] = "processAliveKeys";
                    break;
                case 3:
                    objArr[2] = "removeInterval";
                    break;
                case 4:
                    objArr[2] = "addInterval";
                    break;
                case 5:
                    objArr[2] = "addIntervalsFrom";
                    break;
                case 6:
                    objArr[2] = "createGetter";
                    break;
                case 7:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i == 7) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean allDeltasUpAreNull(long j) {
            return ((j >> 32) & 1) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int deltaUpToRoot(long j) {
            return (int) (j >> 33);
        }

        static /* synthetic */ Object lambda$createGetter$0(Object obj) {
            return obj;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int modCount(long j) {
            return (int) j;
        }

        private static long packValues(long j, boolean z, int i) {
            return (j << 33) | (z ? 4294967296L : 0L) | ((long) i);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree.Node
        public IntervalNode<E> getLeft() {
            return (IntervalNode) this.left;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree.Node
        public IntervalNode<E> getRight() {
            return (IntervalNode) this.right;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree.Node
        public IntervalNode<E> getParent() {
            return (IntervalNode) this.parent;
        }

        public boolean processAliveKeys(Processor<? super E> processor) {
            if (processor == null) {
                $$$reportNull$$$0(2);
            }
            for (int i = 0; i < this.intervals.size(); i++) {
                E e = this.intervals.get(i).get();
                if (e != null && !processor.process(e)) {
                    return false;
                }
            }
            return true;
        }

        public boolean hasAliveKey(boolean z) {
            boolean z2 = false;
            for (int size = this.intervals.size() - 1; size >= 0; size--) {
                if (this.intervals.get(size).get() != null) {
                    if (!z) {
                        return true;
                    }
                    z2 = true;
                } else if (z) {
                    this.myIntervalTree.assertUnderWriteLock();
                    removeIntervalInternal(size);
                }
            }
            return z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean removeInterval(E e) {
            if (e == null) {
                $$$reportNull$$$0(3);
            }
            this.myIntervalTree.checkBelongsToTheTree(e, true);
            this.myIntervalTree.assertUnderWriteLock();
            for (int size = this.intervals.size() - 1; size >= 0; size--) {
                if (this.intervals.get(size).get() == e) {
                    removeIntervalInternal(size);
                    if (!this.intervals.isEmpty()) {
                        return false;
                    }
                    this.myIntervalTree.removeNode(this);
                    return true;
                }
            }
            return false;
        }

        private boolean isAttachedToTree() {
            return isFlagSet((byte) 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAttachedToTree(boolean z) {
            setFlag((byte) 2, z);
        }

        void removeIntervalInternal(int i) {
            this.intervals.remove(i);
            if (isAttachedToTree()) {
                IntervalTreeImpl.access$210(this.myIntervalTree);
            }
        }

        void addInterval(E e) {
            if (e == null) {
                $$$reportNull$$$0(4);
            }
            this.myIntervalTree.assertUnderWriteLock();
            this.intervals.add(createGetter(e));
            if (isAttachedToTree()) {
                IntervalTreeImpl.access$208(this.myIntervalTree);
                this.myIntervalTree.setNode(e, this);
            }
        }

        void addIntervalsFrom(IntervalNode<E> intervalNode) {
            if (intervalNode == null) {
                $$$reportNull$$$0(5);
            }
            Iterator<Supplier<E>> it = intervalNode.intervals.iterator();
            while (it.hasNext()) {
                E e = it.next().get();
                if (e != null) {
                    addInterval(e);
                }
            }
        }

        private Supplier<E> createGetter(final E e) {
            if (e == null) {
                $$$reportNull$$$0(6);
            }
            return this.myIntervalTree.keepIntervalsOnWeakReferences() ? new WeakReferencedGetter(e, ((IntervalTreeImpl) this.myIntervalTree).myReferenceQueue, null) : new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.impl.IntervalTreeImpl$IntervalNode$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return IntervalTreeImpl.IntervalNode.lambda$createGetter$0(e);
                }
            };
        }

        private static final class WeakReferencedGetter<T> extends WeakReference<T> implements Supplier<T> {
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "referent";
                } else {
                    objArr[0] = "q";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/IntervalTreeImpl$IntervalNode$WeakReferencedGetter";
                objArr[2] = "<init>";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            /* synthetic */ WeakReferencedGetter(Object obj, ReferenceQueue referenceQueue, AnonymousClass1 anonymousClass1) {
                this(obj, referenceQueue);
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            private WeakReferencedGetter(T t, ReferenceQueue<? super T> referenceQueue) {
                super(t, referenceQueue);
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                if (referenceQueue == null) {
                    $$$reportNull$$$0(1);
                }
            }

            public String toString() {
                return "Ref: " + get();
            }
        }

        int computeDeltaUpToRoot() {
            int iDeltaUpToRoot;
            boolean zAllDeltasUpAreNull;
            while (isValid()) {
                int modCount = this.myIntervalTree.getModCount();
                long j = this.cachedDeltaUpToRoot;
                if (modCount(j) == modCount) {
                    return deltaUpToRoot(j);
                }
                try {
                    this.myIntervalTree.l.readLock().lock();
                    IntervalNode<E> root = this.myIntervalTree.getRoot();
                    if (root == null) {
                        int i = this.delta;
                        this.myIntervalTree.l.readLock().unlock();
                        return i;
                    }
                    IntervalNode<E> left = this;
                    long j2 = 0;
                    while (true) {
                        int i2 = 1;
                        if (left == root) {
                            iDeltaUpToRoot = 0;
                            zAllDeltasUpAreNull = true;
                            break;
                        }
                        long j3 = left.cachedDeltaUpToRoot;
                        if (left.isValid() && modCount(j3) == modCount) {
                            iDeltaUpToRoot = deltaUpToRoot(j3) - left.delta;
                            zAllDeltasUpAreNull = allDeltasUpAreNull(j3);
                            break;
                        }
                        IntervalNode<E> parent = left.getParent();
                        if (parent == null) {
                            this.myIntervalTree.l.readLock().unlock();
                            return 0;
                        }
                        long j4 = j2 << 1;
                        if (parent.getLeft() == left) {
                            i2 = 0;
                        }
                        j2 = j4 | ((long) i2);
                        left = parent;
                    }
                    while (true) {
                        if (left.isValid()) {
                            int i3 = left.delta;
                            iDeltaUpToRoot += i3;
                            zAllDeltasUpAreNull &= i3 == 0;
                            if (!left.tryToSetCachedValues(iDeltaUpToRoot, zAllDeltasUpAreNull, modCount)) {
                                break;
                            }
                        }
                        if (left != this) {
                            left = (1 & j2) == 0 ? left.getLeft() : left.getRight();
                            j2 >>= 1;
                            if (left == null) {
                            }
                        }
                        this.myIntervalTree.l.readLock().unlock();
                        return iDeltaUpToRoot;
                    }
                    this.myIntervalTree.l.readLock().unlock();
                } catch (Throwable th) {
                    this.myIntervalTree.l.readLock().unlock();
                    throw th;
                }
            }
            return 0;
        }

        int changeDelta(int i) {
            if (i != 0) {
                setCachedValues(0, false, 0);
                int i2 = this.delta + i;
                this.delta = i2;
                return i2;
            }
            return this.delta;
        }

        void clearDelta() {
            if (this.delta != 0) {
                setCachedValues(0, false, 0);
                this.delta = 0;
            }
        }

        public int setIntervalStart(int i) {
            this.myStart = i;
            return i;
        }

        public int setIntervalEnd(int i) {
            this.myEnd = i;
            return i;
        }

        public boolean isValid() {
            return isFlagSet((byte) 4);
        }

        public boolean setValid(boolean z) {
            setFlag((byte) 4, z);
            return z;
        }

        public int intervalStart() {
            return this.myStart;
        }

        public int intervalEnd() {
            return this.myEnd;
        }

        public IntervalTreeImpl<E> getTree() {
            IntervalTreeImpl<E> intervalTreeImpl = this.myIntervalTree;
            if (intervalTreeImpl == null) {
                $$$reportNull$$$0(7);
            }
            return intervalTreeImpl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCachedValues(int i, boolean z, int i2) {
            this.cachedDeltaUpToRoot = packValues(i, z, i2);
        }

        private boolean tryToSetCachedValues(int i, boolean z, int i2) {
            if (this.myIntervalTree.getModCount() != i2) {
                return false;
            }
            return cachedDeltaUpdater.compareAndSet(this, this.cachedDeltaUpToRoot, packValues(i, z, i2));
        }

        public String toString() {
            return "Node: " + this.intervals;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertUnderWriteLock() {
        if (DEBUG) {
            ApplicationManagerEx.isInStressTest();
        }
    }

    private static boolean isAcquired(Lock lock) {
        if (lock == null) {
            $$$reportNull$$$0(0);
        }
        return lock.toString().contains("Locked by thread");
    }

    private void pushDeltaFromRoot(IntervalNode<T> intervalNode) {
        if (intervalNode != null) {
            long j = ((IntervalNode) intervalNode).cachedDeltaUpToRoot;
            if (IntervalNode.allDeltasUpAreNull(j) && intervalNode.isValid() && IntervalNode.modCount(j) == getModCount()) {
                return;
            }
            pushDeltaFromRoot(intervalNode.getParent());
            pushDelta(intervalNode);
        }
    }

    private int compareNodes(IntervalNode<T> intervalNode, int i, IntervalNode<T> intervalNode2, int i2, List<? super IntervalNode<T>> list) {
        if (intervalNode == null) {
            $$$reportNull$$$0(1);
        }
        if (intervalNode2 == null) {
            $$$reportNull$$$0(2);
        }
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        if (!intervalNode2.hasAliveKey(false)) {
            list.add(intervalNode2);
        }
        int iIntervalStart = intervalNode.intervalStart() + i;
        int iIntervalStart2 = intervalNode2.intervalStart() + i2;
        return iIntervalStart != iIntervalStart2 ? iIntervalStart - iIntervalStart2 : compareEqualStartIntervals(intervalNode, intervalNode2);
    }

    protected IntervalNode<T> getRoot() {
        return (IntervalNode) this.root;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.openapi.editor.impl.IntervalTreeImpl$1, reason: invalid class name */
    class AnonymousClass1 implements MarkupIterator<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private T current;
        private IntervalNode<T> currentNode;
        private int deltaUpToRootExclusive;
        private int indexInCurrentList;
        final /* synthetic */ IntervalTreeImpl this$0;
        final /* synthetic */ int val$endOffset;
        final /* synthetic */ int val$firstOverlapStart;
        final /* synthetic */ int val$modCountBefore;
        final /* synthetic */ Predicate val$nodeFilter;
        final /* synthetic */ TextRange val$rangeInterval;
        final /* synthetic */ int val$startOffset;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "root", "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/IntervalTreeImpl$1", "nextNode"));
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.current != null) {
                return true;
            }
            if (this.currentNode == null) {
                return false;
            }
            if (this.this$0.getModCount() != this.val$modCountBefore) {
                throw new ConcurrentModificationException();
            }
            if (nextInterval()) {
                return true;
            }
            while (true) {
                IntervalNode<T> intervalNodeNextNode = nextNode(this.currentNode);
                this.currentNode = intervalNodeNextNode;
                if (intervalNodeNextNode == null) {
                    return false;
                }
                if (this.this$0.overlaps(intervalNodeNextNode, this.val$rangeInterval, this.deltaUpToRootExclusive)) {
                    this.indexInCurrentList = 0;
                    if (nextInterval()) {
                        return true;
                    }
                }
            }
        }

        private boolean nextInterval() {
            List<Supplier<T>> list = this.currentNode.intervals;
            while (this.indexInCurrentList < list.size()) {
                T t = list.get(this.indexInCurrentList).get();
                this.indexInCurrentList++;
                if (t != null) {
                    this.current = t;
                    return true;
                }
            }
            this.indexInCurrentList = 0;
            return false;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T t = this.current;
            this.current = null;
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new IncorrectOperationException();
        }

        public void dispose() {
            this.this$0.l.readLock().unlock();
        }

        private IntervalNode<T> nextNode(IntervalNode<T> intervalNode) {
            Predicate predicate;
            Predicate predicate2;
            if (intervalNode == null) {
                $$$reportNull$$$0(0);
            }
            int i = this.deltaUpToRootExclusive + intervalNode.delta;
            if (this.val$startOffset > this.this$0.maxEndOf(intervalNode, this.deltaUpToRootExclusive)) {
                return null;
            }
            IntervalNode<T> right = intervalNode.getRight();
            if (right != null && ((predicate = this.val$nodeFilter) == null || predicate.test(right))) {
                if (this.val$startOffset <= this.this$0.maxEndOf(right, i)) {
                    int i2 = i + right.delta;
                    while (true) {
                        IntervalNode<T> left = right.getLeft();
                        if (left == null || (!((predicate2 = this.val$nodeFilter) == null || predicate2.test(left)) || this.val$startOffset > this.this$0.maxEndOf(left, i2))) {
                            break;
                        }
                        i2 += left.delta;
                        right = left;
                    }
                    this.deltaUpToRootExclusive = i2 - right.delta;
                    return right;
                }
            }
            while (true) {
                IntervalNode<T> parent = intervalNode.getParent();
                if (parent == null) {
                    return null;
                }
                int iIntervalStart = parent.intervalStart();
                int i3 = this.deltaUpToRootExclusive;
                if (iIntervalStart + i3 > this.val$endOffset) {
                    return null;
                }
                this.deltaUpToRootExclusive = i3 - parent.delta;
                if (parent.getLeft() == intervalNode) {
                    return parent;
                }
                intervalNode = parent;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean overlaps(IntervalNode<T> intervalNode, TextRange textRange, int i) {
        if (textRange == null) {
            $$$reportNull$$$0(14);
        }
        if (intervalNode == null) {
            return false;
        }
        int i2 = intervalNode.delta + i;
        return textRange.intersects(intervalNode.intervalStart() + i2, intervalNode.intervalEnd() + i2);
    }

    IntervalNode<T> findOrInsert(IntervalNode<T> intervalNode) {
        IntervalNode<T> intervalNode2;
        if (intervalNode == null) {
            $$$reportNull$$$0(15);
        }
        assertUnderWriteLock();
        intervalNode.setRed();
        intervalNode.setParent(null);
        intervalNode.setValid(true);
        intervalNode.maxEnd = 0;
        intervalNode.clearDelta();
        intervalNode.setLeft(null);
        intervalNode.setRight(null);
        SmartList smartList = new SmartList();
        if (this.root == null) {
            this.root = intervalNode;
        } else {
            IntervalNode<T> root = getRoot();
            while (true) {
                intervalNode2 = root;
                pushDelta(intervalNode2);
                int iCompareNodes = compareNodes(intervalNode, 0, intervalNode2, 0, smartList);
                if (iCompareNodes == 0) {
                    if (intervalNode2 == null) {
                        $$$reportNull$$$0(16);
                    }
                    return intervalNode2;
                }
                if (iCompareNodes < 0) {
                    if (intervalNode2.getLeft() == null) {
                        intervalNode2.setLeft(intervalNode);
                        break;
                    }
                    root = intervalNode2.getLeft();
                } else {
                    if (intervalNode2.getRight() == null) {
                        intervalNode2.setRight(intervalNode);
                        break;
                    }
                    root = intervalNode2.getRight();
                }
            }
            intervalNode.setParent(intervalNode2);
        }
        intervalNode.setCachedValues(0, true, getModCount());
        correctMaxUp(intervalNode);
        onInsertNode();
        this.keySize += intervalNode.intervals.size();
        insertCase1(intervalNode);
        intervalNode.setAttachedToTree(true);
        verifyProperties();
        deleteNodes(smartList);
        if (intervalNode == null) {
            $$$reportNull$$$0(17);
        }
        return intervalNode;
    }

    private void deleteNodes(List<? extends IntervalNode<T>> list) {
        if (list == null) {
            $$$reportNull$$$0(18);
        }
        if (list.isEmpty()) {
            return;
        }
        try {
            this.l.writeLock().lock();
            Iterator<? extends IntervalNode<T>> it = list.iterator();
            while (it.hasNext()) {
                removeNode(it.next());
            }
            this.l.writeLock().unlock();
        } catch (Throwable th) {
            this.l.writeLock().unlock();
            throw th;
        }
    }

    boolean checkMax(boolean z) {
        return VERIFY && doCheckMax(z);
    }

    private boolean doCheckMax(boolean z) {
        try {
            this.l.readLock().lock();
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            LongOpenHashSet longOpenHashSet = new LongOpenHashSet(this.keySize);
            checkMax(getRoot(), 0, z, atomicBoolean, new int[1], new int[1], longOpenHashSet, true);
            return atomicBoolean.get();
        } finally {
            this.l.readLock().unlock();
        }
    }

    private static final class IntTrinity {
        private final int first;
        private final int second;
        private final int third;

        /* synthetic */ IntTrinity(int i, int i2, int i3, AnonymousClass1 anonymousClass1) {
            this(i, i2, i3);
        }

        private IntTrinity(int i, int i2, int i3) {
            this.first = i;
            this.second = i2;
            this.third = i3;
        }
    }

    private IntTrinity checkMax(IntervalNode<T> intervalNode, int i, boolean z, AtomicBoolean atomicBoolean, int[] iArr, int[] iArr2, LongSet longSet, boolean z2) {
        if (atomicBoolean == null) {
            $$$reportNull$$$0(21);
        }
        if (longSet == null) {
            $$$reportNull$$$0(22);
        }
        if (iArr == null) {
            $$$reportNull$$$0(23);
        }
        if (iArr2 == null) {
            $$$reportNull$$$0(24);
        }
        AnonymousClass1 anonymousClass1 = null;
        if (intervalNode != null) {
            IntervalNode.modCount(((IntervalNode) intervalNode).cachedDeltaUpToRoot);
            getModCount();
            RangeMarkerImpl rangeMarkerImpl = null;
            for (int size = intervalNode.intervals.size() - 1; size >= 0; size--) {
                T t = intervalNode.intervals.get(size).get();
                if (t != null) {
                    checkBelongsToTheTree(t, false);
                    longSet.add(((RangeMarkerImpl) t).getId());
                    rangeMarkerImpl = t;
                }
            }
            if (z && rangeMarkerImpl != null) {
                checkBelongsToTheTree(rangeMarkerImpl, true);
            }
            iArr[0] = iArr[0] + intervalNode.intervals.size();
            iArr2[0] = iArr2[0] + 1;
            int i2 = i + (intervalNode.isValid() ? intervalNode.delta : 0);
            IntTrinity intTrinityCheckMax = checkMax(intervalNode.getLeft(), i2, z, atomicBoolean, iArr, iArr2, longSet, intervalNode.delta == 0 && z2);
            int i3 = intTrinityCheckMax.first;
            int i4 = intTrinityCheckMax.second;
            int i5 = intTrinityCheckMax.third;
            IntTrinity intTrinityCheckMax2 = checkMax(intervalNode.getRight(), i2, z, atomicBoolean, iArr, iArr2, longSet, intervalNode.delta == 0 && z2);
            int i6 = intTrinityCheckMax2.third;
            int i7 = intTrinityCheckMax2.first;
            int i8 = intTrinityCheckMax2.second;
            if (!intervalNode.isValid()) {
                atomicBoolean.set(false);
                return new IntTrinity(Math.min(i3, i7), Math.max(i4, i8), Math.max(i6, i5), null);
            }
            IntervalNode<T> parent = intervalNode.getParent();
            if (parent != null && z && intervalNode.hasAliveKey(false)) {
                compareNodes(intervalNode, i2, parent, i2 - intervalNode.delta, new SmartList<>());
            }
            int iIntervalStart = i2 + intervalNode.intervalStart();
            return new IntTrinity(Math.min(i3, iIntervalStart), Math.max(iIntervalStart, Math.max(i4, i8)), intervalNode.maxEnd + i2, null);
        }
        int i9 = Integer.MIN_VALUE;
        return new IntTrinity(Integer.MAX_VALUE, i9, i9, anonymousClass1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree
    protected RedBlackTree.Node<T> maximumNode(RedBlackTree.Node<T> node) {
        if (node == null) {
            $$$reportNull$$$0(25);
        }
        IntervalNode<T> right = (IntervalNode) node;
        pushDelta(right.getParent());
        pushDelta(right);
        while (right.getRight() != null) {
            right = right.getRight();
            pushDelta(right);
        }
        if (right == null) {
            $$$reportNull$$$0(26);
        }
        return right;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void checkBelongsToTheTree(T t, boolean z) {
        if (t == null) {
            $$$reportNull$$$0(27);
        }
        IntervalNode intervalNodeLookupNode = lookupNode(t);
        if (intervalNodeLookupNode != null && VERIFY) {
            if (z) {
                for (int size = intervalNodeLookupNode.intervals.size() - 1; size >= 0; size--) {
                    Object obj = ((Supplier) intervalNodeLookupNode.intervals.get(size)).get();
                    if (obj != null) {
                        lookupNode(obj);
                    }
                }
            }
            while (intervalNodeLookupNode.getParent() != null) {
                intervalNodeLookupNode = intervalNodeLookupNode.getParent();
            }
        }
    }

    public boolean removeInterval(T t) {
        if (t == null) {
            $$$reportNull$$$0(28);
        }
        if (!((RangeMarkerEx) t).isValid()) {
            return false;
        }
        this.l.writeLock().lock();
        try {
            incModCount();
            if (((RangeMarkerEx) t).isValid()) {
                checkBelongsToTheTree(t, true);
                checkMax(true);
                processReferenceQueue();
                IntervalNode<T> intervalNodeLookupNode = lookupNode(t);
                if (intervalNodeLookupNode != null) {
                    beforeRemove(t, "Explicit Dispose");
                    intervalNodeLookupNode.removeInterval(t);
                    return true;
                }
            }
            return false;
        } finally {
            setNode(t, null);
            this.l.writeLock().unlock();
        }
    }

    void removeNode(IntervalNode<T> intervalNode) {
        if (intervalNode == null) {
            $$$reportNull$$$0(29);
        }
        deleteNode(intervalNode);
        correctMaxUp(intervalNode.getParent());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree
    protected void deleteNode(RedBlackTree.Node<T> node) {
        if (node == 0) {
            $$$reportNull$$$0(30);
        }
        assertUnderWriteLock();
        IntervalNode<T> intervalNode = (IntervalNode) node;
        pushDeltaFromRoot(intervalNode);
        assertAllDeltasAreNull(intervalNode);
        super.deleteNode(node);
        this.keySize -= intervalNode.intervals.size();
        intervalNode.setAttachedToTree(false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree
    public int size() {
        return this.keySize;
    }

    boolean pushDelta(IntervalNode<T> intervalNode) {
        if (intervalNode != null && intervalNode.isValid()) {
            assertAllDeltasAreNull(intervalNode.getParent());
            int i = intervalNode.delta;
            intervalNode.setCachedValues(0, true, 0);
            if (i == 0) {
                intervalNode.setCachedValues(0, true, getModCount());
            } else {
                intervalNode.setIntervalStart(intervalNode.intervalStart() + i);
                intervalNode.setIntervalEnd(intervalNode.intervalEnd() + i);
                intervalNode.maxEnd += i;
                intervalNode.delta = 0;
                return incDelta(intervalNode.getRight(), i) & incDelta(intervalNode.getLeft(), i);
            }
        }
        return true;
    }

    private boolean incDelta(IntervalNode<T> intervalNode, int i) {
        if (intervalNode == null) {
            return true;
        }
        if (intervalNode.isValid()) {
            return intervalNode.changeDelta(i) == 0;
        }
        return incDelta(intervalNode.getRight(), i) & incDelta(intervalNode.getLeft(), i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree
    public IntervalNode<T> swapWithMaxPred(RedBlackTree.Node<T> node, RedBlackTree.Node<T> node2) {
        if (node == null) {
            $$$reportNull$$$0(31);
        }
        if (node2 == null) {
            $$$reportNull$$$0(32);
        }
        checkMax(false);
        IntervalNode<T> intervalNode = (IntervalNode) node;
        IntervalNode<T> intervalNode2 = (IntervalNode) node2;
        boolean zIsBlack = intervalNode.isBlack();
        boolean zIsBlack2 = intervalNode2.isBlack();
        for (IntervalNode<T> left = intervalNode.getLeft(); left != null; left = left.getRight()) {
        }
        swapNodes(intervalNode, intervalNode2);
        intervalNode.setValid(false);
        intervalNode.setColor(zIsBlack2);
        intervalNode2.setColor(zIsBlack);
        correctMaxUp(intervalNode);
        checkMax(false);
        if (intervalNode == null) {
            $$$reportNull$$$0(33);
        }
        return intervalNode;
    }

    private void swapNodes(IntervalNode<T> intervalNode, IntervalNode<T> intervalNode2) {
        if (intervalNode == null) {
            $$$reportNull$$$0(34);
        }
        if (intervalNode2 == null) {
            $$$reportNull$$$0(35);
        }
        IntervalNode<T> left = intervalNode.getLeft();
        IntervalNode<T> right = intervalNode.getRight();
        IntervalNode<T> parent = intervalNode.getParent();
        IntervalNode<T> left2 = intervalNode2.getLeft();
        IntervalNode<T> right2 = intervalNode2.getRight();
        IntervalNode<T> parent2 = intervalNode2.getParent();
        if (parent != null) {
            if (parent.getLeft() == intervalNode) {
                parent.setLeft(intervalNode2);
            } else {
                parent.setRight(intervalNode2);
            }
        } else {
            this.root = intervalNode2;
        }
        if (parent2 != null) {
            if (parent2.getLeft() == intervalNode2) {
                parent2.setLeft(parent2 == intervalNode ? left2 : intervalNode);
            } else {
                parent2.setRight(parent2 == intervalNode ? right2 : intervalNode);
            }
        } else {
            this.root = intervalNode;
        }
        if (parent2 == intervalNode) {
            parent2 = intervalNode2;
        }
        intervalNode.setParent(parent2);
        intervalNode2.setParent(parent);
        intervalNode.setLeft(left2);
        intervalNode2.setLeft(left == intervalNode2 ? intervalNode : left);
        if (left != null) {
            if (intervalNode2 != left) {
                parent = intervalNode2;
            }
            left.setParent(parent);
        }
        if (right != null) {
            right.setParent(intervalNode2);
        }
        intervalNode.setRight(right2);
        intervalNode2.setRight(right);
        if (left2 != null) {
            left2.setParent(intervalNode);
        }
        if (right2 != null) {
            right2.setParent(intervalNode);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int maxEndOf(IntervalNode<T> intervalNode, int i) {
        if (intervalNode == null) {
            return 0;
        }
        if (intervalNode.isValid()) {
            return intervalNode.maxEnd + intervalNode.delta + i;
        }
        return Math.max(maxEndOf(intervalNode.getLeft(), i), maxEndOf(intervalNode.getRight(), i));
    }

    void correctMax(IntervalNode<T> intervalNode, int i) {
        if (intervalNode == null) {
            $$$reportNull$$$0(36);
        }
        if (intervalNode.isValid()) {
            intervalNode.maxEnd = Math.max(Math.max(maxEndOf(intervalNode.getLeft(), i), maxEndOf(intervalNode.getRight(), i)), intervalNode.intervalEnd() + i) - i;
        }
    }

    private void correctMaxUp(IntervalNode<T> intervalNode) {
        int iComputeDeltaUpToRoot = intervalNode == null ? 0 : intervalNode.computeDeltaUpToRoot();
        while (intervalNode != null) {
            if (intervalNode.isValid()) {
                int i = intervalNode.delta;
                correctMax(intervalNode, iComputeDeltaUpToRoot);
                iComputeDeltaUpToRoot -= i;
            }
            intervalNode = intervalNode.getParent();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree
    protected void rotateRight(RedBlackTree.Node<T> node) {
        if (node == null) {
            $$$reportNull$$$0(37);
        }
        checkMax(false);
        IntervalNode<T> intervalNode = (IntervalNode) node;
        IntervalNode<T> left = intervalNode.getLeft();
        IntervalNode<T> right = intervalNode.getRight();
        IntervalNode<T> parent = intervalNode.getParent();
        int iComputeDeltaUpToRoot = parent == null ? 0 : parent.computeDeltaUpToRoot();
        pushDelta(intervalNode);
        pushDelta(left);
        pushDelta(right);
        super.rotateRight(intervalNode);
        if (right != null) {
            correctMax(right, iComputeDeltaUpToRoot);
        }
        correctMax(intervalNode, iComputeDeltaUpToRoot);
        correctMax(left, iComputeDeltaUpToRoot);
        assertAllDeltasAreNull(intervalNode);
        assertAllDeltasAreNull(left);
        assertAllDeltasAreNull(right);
        checkMax(false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree
    protected void rotateLeft(RedBlackTree.Node<T> node) {
        if (node == null) {
            $$$reportNull$$$0(38);
        }
        checkMax(false);
        IntervalNode<T> intervalNode = (IntervalNode) node;
        IntervalNode<T> left = intervalNode.getLeft();
        IntervalNode<T> right = intervalNode.getRight();
        IntervalNode<T> parent = intervalNode.getParent();
        int iComputeDeltaUpToRoot = parent == null ? 0 : parent.computeDeltaUpToRoot();
        pushDelta(intervalNode);
        pushDelta(left);
        pushDelta(right);
        checkMax(false);
        super.rotateLeft(intervalNode);
        if (left != null) {
            correctMax(left, iComputeDeltaUpToRoot);
        }
        correctMax(intervalNode, iComputeDeltaUpToRoot);
        correctMax(right, iComputeDeltaUpToRoot);
        assertAllDeltasAreNull(intervalNode);
        assertAllDeltasAreNull(left);
        assertAllDeltasAreNull(right);
        checkMax(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RedBlackTree
    protected void replaceNode(RedBlackTree.Node<T> node, RedBlackTree.Node<T> node2) {
        if (node == 0) {
            $$$reportNull$$$0(39);
        }
        IntervalNode<T> intervalNode = (IntervalNode) node;
        pushDelta(intervalNode);
        IntervalNode<T> intervalNode2 = (IntervalNode) node2;
        pushDelta(intervalNode2);
        super.replaceNode(node, node2);
        if (node2 == 0 || !intervalNode.isValid()) {
            return;
        }
        intervalNode2.changeDelta(intervalNode.delta);
    }

    private void assertAllDeltasAreNull(IntervalNode<T> intervalNode) {
        if (intervalNode != null && intervalNode.isValid()) {
            long unused = ((IntervalNode) intervalNode).cachedDeltaUpToRoot;
        }
    }

    private void processReferenceQueue() {
        int i = 0;
        while (this.myReferenceQueue.poll() != null) {
            i++;
        }
        int i2 = this.deadReferenceCount + i;
        this.deadReferenceCount = i2;
        if (i2 > Math.max(1, size() / 3)) {
            purgeDeadNodes();
            this.deadReferenceCount = 0;
        }
    }

    private void purgeDeadNodes() {
        assertUnderWriteLock();
        SmartList smartList = new SmartList();
        collectGced(getRoot(), smartList);
        deleteNodes(smartList);
        checkMax(true);
    }

    private void collectGced(IntervalNode<T> intervalNode, List<? super IntervalNode<T>> list) {
        if (list == null) {
            $$$reportNull$$$0(42);
        }
        if (intervalNode == null) {
            return;
        }
        if (!intervalNode.hasAliveKey(true)) {
            list.add(intervalNode);
        }
        collectGced(intervalNode.getLeft(), list);
        collectGced(intervalNode.getRight(), list);
    }

    void beforeRemove(T t, Object obj) {
        if (t == null) {
            $$$reportNull$$$0(45);
        }
        if (obj == null) {
            $$$reportNull$$$0(46);
        }
        if (this.firingBeforeRemove) {
            throw new IllegalStateException();
        }
        this.firingBeforeRemove = true;
        try {
            fireBeforeRemoved(t, obj);
        } finally {
            this.firingBeforeRemove = false;
        }
    }

    private static class IntervalTreeGuide<T> implements WalkingState.TreeGuide<IntervalNode<T>> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/IntervalTreeImpl$IntervalTreeGuide";
            if (i == 1) {
                objArr[2] = "getPrevSibling";
            } else if (i == 2) {
                objArr[2] = "getFirstChild";
            } else if (i != 3) {
                objArr[2] = "getNextSibling";
            } else {
                objArr[2] = "getParent";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private IntervalTreeGuide() {
        }

        /* synthetic */ IntervalTreeGuide(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public IntervalNode<T> getNextSibling(IntervalNode<T> intervalNode) {
            if (intervalNode == null) {
                $$$reportNull$$$0(0);
            }
            IntervalNode<T> parent = intervalNode.getParent();
            if (parent != null && parent.getLeft() == intervalNode) {
                return parent.getRight();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public IntervalNode<T> getPrevSibling(IntervalNode<T> intervalNode) {
            if (intervalNode == null) {
                $$$reportNull$$$0(1);
            }
            IntervalNode<T> parent = intervalNode.getParent();
            if (parent != null && parent.getRight() == intervalNode) {
                return parent.getLeft();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public IntervalNode<T> getFirstChild(IntervalNode<T> intervalNode) {
            if (intervalNode == null) {
                $$$reportNull$$$0(2);
            }
            IntervalNode<T> left = intervalNode.getLeft();
            return left == null ? intervalNode.getRight() : left;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState.TreeGuide
        public IntervalNode<T> getParent(IntervalNode<T> intervalNode) {
            if (intervalNode == null) {
                $$$reportNull$$$0(3);
            }
            return intervalNode.getParent();
        }
    }
}
