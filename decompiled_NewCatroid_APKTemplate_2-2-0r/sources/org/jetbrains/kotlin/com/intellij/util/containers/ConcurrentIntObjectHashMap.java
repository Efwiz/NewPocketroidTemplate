package org.jetbrains.kotlin.com.intellij.util.containers;

import com.esotericsoftware.kryo.util.Util;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.arsc.value.CompoundEntry;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.LockSupport;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
final class ConcurrentIntObjectHashMap<V> implements ConcurrentIntObjectMap<V> {
    private static final long ABASE;
    private static final int ASHIFT;
    private static final long BASECOUNT;
    private static final long CELLSBUSY;
    private static final long CELLVALUE;
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    private static final MethodHandle compareAndSwapIntHandle;
    private static final MethodHandle compareAndSwapLongHandle;
    private static final MethodHandle compareAndSwapObjectHandle;
    private static final MethodHandle getAndAddIntHandle;
    private static final MethodHandle getObjectVolatileHandle;
    private static final MethodHandle putObjectVolatileHandle;
    private volatile transient long baseCount;
    private volatile transient int cellsBusy;
    private volatile transient CounterCell[] counterCells;
    private transient EntrySetView<V> entrySet;
    private volatile transient Node<V>[] nextTable;
    private volatile transient int sizeCtl;
    volatile transient Node<V>[] table;
    private volatile transient int transferIndex;
    private transient ValuesView<V> values;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 10 || i == 12 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 10 || i == 12 || i == 13) ? 2 : 3];
        switch (i) {
            case 3:
            case 4:
            case 10:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentIntObjectHashMap";
                break;
            case 5:
            case 6:
            case 9:
            default:
                objArr[0] = "value";
                break;
            case 7:
                objArr[0] = "oldValue";
                break;
            case 8:
                objArr[0] = "newValue";
                break;
            case 11:
                objArr[0] = "defaultValue";
                break;
        }
        if (i == 3) {
            objArr[1] = CompoundEntry.NAME_values;
        } else if (i == 4) {
            objArr[1] = "entrySet";
        } else if (i == 10) {
            objArr[1] = UserMetadata.KEYDATA_FILENAME;
        } else if (i == 12 || i == 13) {
            objArr[1] = "cacheOrGet";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentIntObjectHashMap";
        }
        switch (i) {
            case 1:
                objArr[2] = "put";
                break;
            case 2:
                objArr[2] = "putVal";
                break;
            case 3:
            case 4:
            case 10:
            case 12:
            case 13:
                break;
            case 5:
                objArr[2] = "putIfAbsent";
                break;
            case 6:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 11:
                objArr[2] = "cacheOrGet";
                break;
            default:
                objArr[2] = "containsValue";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 10 && i != 12 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static int spread(int i) {
        return (i ^ (i >>> 16)) & Integer.MAX_VALUE;
    }

    private static int tableSizeFor(int i) {
        int i2 = i - 1;
        int i3 = i2 | (i2 >>> 1);
        int i4 = i3 | (i3 >>> 2);
        int i5 = i4 | (i4 >>> 4);
        int i6 = i5 | (i5 >>> 8);
        int i7 = i6 | (i6 >>> 16);
        if (i7 < 0) {
            return 1;
        }
        if (i7 >= 1073741824) {
            return 1073741824;
        }
        return 1 + i7;
    }

    static {
        try {
            MethodHandles.Lookup lookupPublicLookup = MethodHandles.publicLookup();
            Object unsafe = ReflectionUtil.getUnsafe();
            MethodHandle methodHandleFindVirtual = lookupPublicLookup.findVirtual(unsafe.getClass(), "objectFieldOffset", MethodType.methodType((Class<?>) Long.TYPE, (Class<?>) Field.class));
            SIZECTL = (long) methodHandleFindVirtual.invoke(unsafe, ConcurrentIntObjectHashMap.class.getDeclaredField("sizeCtl"));
            TRANSFERINDEX = (long) methodHandleFindVirtual.invoke(unsafe, ConcurrentIntObjectHashMap.class.getDeclaredField("transferIndex"));
            BASECOUNT = (long) methodHandleFindVirtual.invoke(unsafe, ConcurrentIntObjectHashMap.class.getDeclaredField("baseCount"));
            CELLSBUSY = (long) methodHandleFindVirtual.invoke(unsafe, ConcurrentIntObjectHashMap.class.getDeclaredField("cellsBusy"));
            CELLVALUE = (long) methodHandleFindVirtual.invoke(unsafe, CounterCell.class.getDeclaredField("value"));
            ABASE = (int) lookupPublicLookup.findVirtual(unsafe.getClass(), "arrayBaseOffset", MethodType.methodType((Class<?>) Integer.TYPE, (Class<?>) Class.class)).invoke(unsafe, Node[].class);
            int iInvoke = (int) lookupPublicLookup.findVirtual(unsafe.getClass(), "arrayIndexScale", MethodType.methodType((Class<?>) Integer.TYPE, (Class<?>) Class.class)).invoke(unsafe, Node[].class);
            if (((iInvoke - 1) & iInvoke) != 0) {
                throw new Error("data type scale not a power of two");
            }
            ASHIFT = 31 - Integer.numberOfLeadingZeros(iInvoke);
            putObjectVolatileHandle = lookupPublicLookup.findVirtual(unsafe.getClass(), "putObjectVolatile", MethodType.methodType(Void.TYPE, Object.class, Long.TYPE, Object.class)).bindTo(unsafe);
            getObjectVolatileHandle = lookupPublicLookup.findVirtual(unsafe.getClass(), "getObjectVolatile", MethodType.methodType(Object.class, Object.class, Long.TYPE)).bindTo(unsafe);
            compareAndSwapObjectHandle = lookupPublicLookup.findVirtual(unsafe.getClass(), "compareAndSwapObject", MethodType.methodType(Boolean.TYPE, Object.class, Long.TYPE, Object.class, Object.class)).bindTo(unsafe);
            compareAndSwapIntHandle = lookupPublicLookup.findVirtual(unsafe.getClass(), "compareAndSwapInt", MethodType.methodType(Boolean.TYPE, Object.class, Long.TYPE, Integer.TYPE, Integer.TYPE)).bindTo(unsafe);
            compareAndSwapLongHandle = lookupPublicLookup.findVirtual(unsafe.getClass(), "compareAndSwapLong", MethodType.methodType(Boolean.TYPE, Object.class, Long.TYPE, Long.TYPE, Long.TYPE)).bindTo(unsafe);
            getAndAddIntHandle = lookupPublicLookup.findVirtual(unsafe.getClass(), "getAndAddInt", MethodType.methodType(Integer.TYPE, Object.class, Long.TYPE, Integer.TYPE)).bindTo(unsafe);
        } catch (Throwable th) {
            throw new Error(th);
        }
    }

    static class Node<V> implements IntObjectMap.Entry<V> {
        final int hash;
        final int key;
        volatile Node<V> next;
        volatile V val;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentIntObjectHashMap$Node", "getValue"));
        }

        Node(int i, int i2, V v, Node<V> node) {
            this.hash = i;
            this.key = i2;
            this.val = v;
            this.next = node;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap.Entry
        public final int getKey() {
            return this.key;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap.Entry
        public final V getValue() {
            V v = this.val;
            if (v == null) {
                $$$reportNull$$$0(0);
            }
            return v;
        }

        public final int hashCode() {
            return this.key ^ this.val.hashCode();
        }

        public final String toString() {
            return this.key + ContainerUtils.KEY_VALUE_DELIMITER + this.val;
        }

        public final boolean equals(Object obj) {
            Object value;
            V v;
            if (obj instanceof IntObjectMap.Entry) {
                IntObjectMap.Entry entry = (IntObjectMap.Entry) obj;
                if (entry.getKey() == this.key && (value = entry.getValue()) != null && (value == (v = this.val) || value.equals(v))) {
                    return true;
                }
            }
            return false;
        }

        Node<V> find(int i, int i2) {
            Node<V> node = this;
            while (node.key != i2) {
                node = node.next;
                if (node == null) {
                    return null;
                }
            }
            return node;
        }
    }

    static <V> Node<V> tabAt(Node<V>[] nodeArr, int i) {
        try {
            return (Node) (Object) getObjectVolatileHandle.invokeExact(nodeArr, (((long) i) << ASHIFT) + ABASE);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    static <V> boolean casTabAt(Node<V>[] nodeArr, int i, Node<V> node, Node<V> node2) {
        try {
            return (boolean) compareAndSwapObjectHandle.invokeExact(nodeArr, (((long) i) << ASHIFT) + ABASE, node, node2);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    static <V> void setTabAt(Node<V>[] nodeArr, int i, Node<V> node) {
        try {
            (void) putObjectVolatileHandle.invokeExact(nodeArr, (((long) i) << ASHIFT) + ABASE, node);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    static final class CounterCell {
        volatile long value;

        CounterCell(long j) {
            this.value = j;
        }
    }

    ConcurrentIntObjectHashMap() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectMap
    public int size() {
        long jSumCount = sumCount();
        if (jSumCount < 0) {
            return 0;
        }
        if (jSumCount > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) jSumCount;
    }

    public boolean isEmpty() {
        return sumCount() <= 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap
    public V get(int i) {
        int length;
        Node<V> nodeTabAt;
        int iSpread = spread(i);
        Node<V>[] nodeArr = this.table;
        if (nodeArr != null && (length = nodeArr.length) > 0 && (nodeTabAt = tabAt(nodeArr, (length - 1) & iSpread)) != null) {
            int i2 = nodeTabAt.hash;
            if (i2 == iSpread) {
                if (nodeTabAt.key == i) {
                    return nodeTabAt.val;
                }
            } else if (i2 < 0) {
                Node<V> nodeFind = nodeTabAt.find(iSpread, i);
                if (nodeFind != null) {
                    return nodeFind.val;
                }
                return null;
            }
            while (true) {
                nodeTabAt = nodeTabAt.next;
                if (nodeTabAt == null) {
                    break;
                }
                if (nodeTabAt.hash == iSpread && nodeTabAt.key == i) {
                    return nodeTabAt.val;
                }
            }
        }
        return null;
    }

    public boolean containsValue(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(0);
        }
        Node<V>[] nodeArr = this.table;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node<V> nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                V v = nodeAdvance.val;
                if (v == obj) {
                    return true;
                }
                if (v != null && obj.equals(v)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap
    public V put(int i, V v) {
        if (v == null) {
            $$$reportNull$$$0(1);
        }
        return putVal(i, v, false);
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0070 A[EDGE_INSN: B:41:0x0070->B:42:0x0071 BREAK  A[LOOP:1: B:23:0x003f->B:31:0x0056], PHI: r3
      0x0070: PHI (r3v4 int) = (r3v1 int), (r3v1 int), (r3v9 int), (r3v12 int) binds: [B:20:0x0039, B:33:0x005c, B:40:0x006f, B:30:0x004e] A[DONT_GENERATE, DONT_INLINE]] */
    V putVal(int i, V v, boolean z) {
        V v2;
        if (v == null) {
            $$$reportNull$$$0(2);
        }
        int iSpread = spread(i);
        Node<V>[] nodeArrInitTable = this.table;
        int i2 = 0;
        while (true) {
            if (nodeArrInitTable != null) {
                int length = nodeArrInitTable.length;
                if (length != 0) {
                    int i3 = (length - 1) & iSpread;
                    Node<V> nodeTabAt = tabAt(nodeArrInitTable, i3);
                    if (nodeTabAt == null) {
                        if (casTabAt(nodeArrInitTable, i3, null, new Node(iSpread, i, v, null))) {
                            break;
                        }
                    } else {
                        int i4 = nodeTabAt.hash;
                        if (i4 == -1) {
                            nodeArrInitTable = helpTransfer(nodeArrInitTable, nodeTabAt);
                        } else {
                            synchronized (nodeTabAt) {
                                if (tabAt(nodeArrInitTable, i3) == nodeTabAt) {
                                    if (i4 < 0) {
                                        if (!(nodeTabAt instanceof TreeBin)) {
                                            v2 = null;
                                            break;
                                        }
                                        TreeNode<V> treeNodePutTreeVal = ((TreeBin) nodeTabAt).putTreeVal(iSpread, i, v);
                                        if (treeNodePutTreeVal == null) {
                                            i2 = 2;
                                            v2 = null;
                                            break;
                                        }
                                        v2 = treeNodePutTreeVal.val;
                                        if (!z) {
                                            treeNodePutTreeVal.val = v;
                                        }
                                        i2 = 2;
                                    } else {
                                        i2 = 1;
                                        Node<V> node = nodeTabAt;
                                        while (true) {
                                            if (node.key == i) {
                                                v2 = node.val;
                                                if (!z) {
                                                    node.val = v;
                                                    break;
                                                }
                                                break;
                                            }
                                            Node<V> node2 = node.next;
                                            if (node2 == null) {
                                                node.next = new Node<>(iSpread, i, v, null);
                                                v2 = null;
                                                break;
                                            }
                                            i2++;
                                            node = node2;
                                        }
                                    }
                                } else {
                                    v2 = null;
                                    break;
                                }
                            }
                            if (i2 != 0) {
                                if (i2 >= 8) {
                                    treeifyBin(nodeArrInitTable, i3);
                                }
                                if (v2 == null) {
                                    break;
                                }
                                return v2;
                            }
                        }
                    }
                }
            }
            nodeArrInitTable = initTable();
        }
        addCount(1L, i2);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap
    public V remove(int i) {
        return replaceNode(i, null, null);
    }

    /* JADX WARN: Code duplicated, block: B:53:0x008e  */
    V replaceNode(int i, V v, Object obj) {
        int length;
        int i2;
        Node<V> nodeTabAt;
        boolean z;
        V v2;
        TreeNode<V> treeNodeFindTreeNode;
        int iSpread = spread(i);
        Node<V>[] nodeArrHelpTransfer = this.table;
        while (nodeArrHelpTransfer != null && (length = nodeArrHelpTransfer.length) != 0 && (nodeTabAt = tabAt(nodeArrHelpTransfer, (i2 = (length - 1) & iSpread))) != null) {
            int i3 = nodeTabAt.hash;
            if (i3 == -1) {
                nodeArrHelpTransfer = helpTransfer(nodeArrHelpTransfer, nodeTabAt);
            } else {
                synchronized (nodeTabAt) {
                    if (tabAt(nodeArrHelpTransfer, i2) != nodeTabAt) {
                        z = false;
                        v2 = null;
                        break;
                    }
                    z = true;
                    if (i3 < 0) {
                        if (nodeTabAt instanceof TreeBin) {
                            TreeBin treeBin = (TreeBin) nodeTabAt;
                            TreeNode<V> treeNode = treeBin.root;
                            if (treeNode == null || (treeNodeFindTreeNode = treeNode.findTreeNode(iSpread, i)) == null) {
                                break;
                            }
                            v2 = treeNodeFindTreeNode.val;
                            if (obj == null || obj == v2 || (v2 != null && obj.equals(v2))) {
                                if (v != null) {
                                    treeNodeFindTreeNode.val = v;
                                } else if (treeBin.removeTreeNode(treeNodeFindTreeNode)) {
                                    setTabAt(nodeArrHelpTransfer, i2, untreeify(treeBin.first));
                                }
                            }
                        } else {
                            z = false;
                        }
                        v2 = null;
                        break;
                    }
                    Node<V> node = null;
                    Node<V> node2 = nodeTabAt;
                    while (true) {
                        if (node2.key == i) {
                            v2 = node2.val;
                            if (obj == null || obj == v2 || (v2 != null && obj.equals(v2))) {
                                if (v == null) {
                                    if (node != null) {
                                        node.next = node2.next;
                                        break;
                                    }
                                    setTabAt(nodeArrHelpTransfer, i2, node2.next);
                                    break;
                                }
                                node2.val = v;
                                break;
                            }
                        } else {
                            Node<V> node3 = node2.next;
                            if (node3 != null) {
                                node = node2;
                                node2 = node3;
                            }
                        }
                        v2 = null;
                        break;
                    }
                }
                if (z) {
                    if (v2 == null) {
                        break;
                    }
                    if (v == null) {
                        addCount(-1L, -1);
                    }
                    return v2;
                }
            }
        }
        return null;
    }

    public void clear() {
        Node<V> nodeTabAt;
        Node<V> node;
        Node<V>[] nodeArrHelpTransfer = this.table;
        long j = 0;
        loop0: while (true) {
            int i = 0;
            while (true) {
                if (nodeArrHelpTransfer == null || i >= nodeArrHelpTransfer.length) {
                    break loop0;
                }
                nodeTabAt = tabAt(nodeArrHelpTransfer, i);
                if (nodeTabAt == null) {
                    i++;
                } else {
                    int i2 = nodeTabAt.hash;
                    if (i2 == -1) {
                        break;
                    }
                    synchronized (nodeTabAt) {
                        if (tabAt(nodeArrHelpTransfer, i) == nodeTabAt) {
                            if (i2 >= 0) {
                                node = nodeTabAt;
                            } else {
                                node = nodeTabAt instanceof TreeBin ? ((TreeBin) nodeTabAt).first : null;
                            }
                            while (node != null) {
                                j--;
                                node = node.next;
                            }
                            setTabAt(nodeArrHelpTransfer, i, null);
                            i++;
                        }
                    }
                }
            }
            nodeArrHelpTransfer = helpTransfer(nodeArrHelpTransfer, nodeTabAt);
        }
        if (j != 0) {
            addCount(j, -1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap
    public Collection<V> values() {
        ValuesView<V> valuesView = this.values;
        if (valuesView == null) {
            valuesView = new ValuesView<>(this);
            this.values = valuesView;
        }
        if (valuesView == null) {
            $$$reportNull$$$0(3);
        }
        return valuesView;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap
    public Set<IntObjectMap.Entry<V>> entrySet() {
        EntrySetView<V> entrySetView = this.entrySet;
        if (entrySetView == null) {
            entrySetView = new EntrySetView<>(this);
            this.entrySet = entrySetView;
        }
        if (entrySetView == null) {
            $$$reportNull$$$0(4);
        }
        return entrySetView;
    }

    public int hashCode() {
        Node<V>[] nodeArr = this.table;
        int iHashCode = 0;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node<V> nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                iHashCode += nodeAdvance.val.hashCode() ^ nodeAdvance.key;
            }
        }
        return iHashCode;
    }

    public String toString() {
        Node<V>[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        Traverser traverser = new Traverser(nodeArr, length, 0, length);
        StringBuilder sb = new StringBuilder("{");
        Node<V> nodeAdvance = traverser.advance();
        if (nodeAdvance != null) {
            while (true) {
                int i = nodeAdvance.key;
                Object obj = nodeAdvance.val;
                sb.append(i);
                sb.append('=');
                if (obj == this) {
                    obj = "(this Map)";
                }
                sb.append(obj);
                nodeAdvance = traverser.advance();
                if (nodeAdvance == null) {
                    break;
                }
                sb.append(", ");
            }
        }
        return sb.append('}').toString();
    }

    public boolean equals(Object obj) {
        V v;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConcurrentIntObjectMap)) {
            return false;
        }
        IntObjectMap intObjectMap = (IntObjectMap) obj;
        Node<V>[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        Traverser traverser = new Traverser(nodeArr, length, 0, length);
        while (true) {
            Node<V> nodeAdvance = traverser.advance();
            if (nodeAdvance != null) {
                V v2 = nodeAdvance.val;
                Object obj2 = intObjectMap.get(nodeAdvance.key);
                if (obj2 == null || (obj2 != v2 && !obj2.equals(v2))) {
                    break;
                }
            } else {
                for (IntObjectMap.Entry<V> entry : intObjectMap.entrySet()) {
                    int key = entry.getKey();
                    V value = entry.getValue();
                    if (value == null || (v = get(key)) == null || (value != v && !value.equals(v))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectMap
    public V putIfAbsent(int i, V v) {
        if (v == null) {
            $$$reportNull$$$0(5);
        }
        return putVal(i, v, true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectMap
    public boolean remove(int i, Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(6);
        }
        return replaceNode(i, null, obj) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectMap
    public boolean replace(int i, V v, V v2) {
        if (v == null) {
            $$$reportNull$$$0(7);
        }
        if (v2 == null) {
            $$$reportNull$$$0(8);
        }
        return replaceNode(i, v2, v) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectMap
    public Enumeration<V> elements() {
        Node<V>[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        return new ValueIterator(nodeArr, length, 0, length, this);
    }

    public long mappingCount() {
        return Math.max(sumCount(), 0L);
    }

    static final class ForwardingNode<V> extends Node<V> {
        final Node<V>[] nextTable;

        ForwardingNode(Node<V>[] nodeArr) {
            super(-1, 0, null, null);
            this.nextTable = nodeArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap.Node
        Node<V> find(int i, int i2) {
            int length;
            Node<V> nodeTabAt;
            loop0: for (Node<V>[] nodeArr = this.nextTable; nodeArr != null && (length = nodeArr.length) != 0 && (nodeTabAt = ConcurrentIntObjectHashMap.tabAt(nodeArr, (length - 1) & i)) != null; nodeArr = ((ForwardingNode) nodeTabAt).nextTable) {
                while (nodeTabAt.key != i2) {
                    if (nodeTabAt.hash < 0) {
                        if (!(nodeTabAt instanceof ForwardingNode)) {
                            return nodeTabAt.find(i, i2);
                        }
                    } else {
                        nodeTabAt = nodeTabAt.next;
                        if (nodeTabAt == null) {
                            break loop0;
                        }
                    }
                }
                return nodeTabAt;
            }
            return null;
        }
    }

    static int resizeStamp(int i) {
        return Integer.numberOfLeadingZeros(i) | 32768;
    }

    private Node<V>[] initTable() {
        while (true) {
            Node<V>[] nodeArr = this.table;
            if (nodeArr != null && nodeArr.length != 0) {
                return nodeArr;
            }
            int i = this.sizeCtl;
            if (i < 0) {
                Thread.yield();
            } else if (compareAndSwapInt(this, SIZECTL, i, -1)) {
                try {
                    Node<V>[] nodeArr2 = this.table;
                    if (nodeArr2 == null || nodeArr2.length == 0) {
                        int i2 = i > 0 ? i : 16;
                        Node<V>[] nodeArr3 = new Node[i2];
                        this.table = nodeArr3;
                        i = i2 - (i2 >>> 2);
                        nodeArr2 = nodeArr3;
                    }
                    return nodeArr2;
                } finally {
                    this.sizeCtl = i;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002e  */
    /* JADX WARN: Code duplicated, block: B:15:0x0030 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:16:0x0032 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:17:0x0033  */
    /* JADX WARN: Code duplicated, block: B:6:0x0012  */
    private void addCount(long j, int i) {
        boolean z;
        int length;
        CounterCell counterCell;
        boolean zCompareAndSwapLong;
        long jSumCount;
        Node<V>[] nodeArr;
        int length2;
        Node<V>[] nodeArr2;
        CounterCell[] counterCellArr = this.counterCells;
        if (counterCellArr == null) {
            long j2 = BASECOUNT;
            long j3 = this.baseCount;
            jSumCount = j3 + j;
            if (!compareAndSwapLong(this, j2, j3, jSumCount)) {
                z = true;
                if (counterCellArr != null && (length = counterCellArr.length - 1) >= 0 && (counterCell = counterCellArr[length & ThreadLocalRandom.getProbe()]) != null) {
                    long j4 = CELLVALUE;
                    long j5 = counterCell.value;
                    zCompareAndSwapLong = compareAndSwapLong(counterCell, j4, j5, j5 + j);
                    if (!zCompareAndSwapLong) {
                        z = zCompareAndSwapLong;
                    } else if (i <= 1) {
                        return;
                    } else {
                        jSumCount = sumCount();
                    }
                }
                fullAddCount(j, z);
                return;
            }
        } else {
            z = true;
            if (counterCellArr != null) {
                long j6 = CELLVALUE;
                long j7 = counterCell.value;
                zCompareAndSwapLong = compareAndSwapLong(counterCell, j6, j7, j7 + j);
                if (!zCompareAndSwapLong) {
                    z = zCompareAndSwapLong;
                } else if (i <= 1) {
                    return;
                } else {
                    jSumCount = sumCount();
                }
            }
            fullAddCount(j, z);
            return;
        }
        if (i < 0) {
            return;
        }
        while (true) {
            int i2 = this.sizeCtl;
            if (jSumCount < i2 || (nodeArr = this.table) == null || (length2 = nodeArr.length) >= 1073741824) {
                return;
            }
            int iResizeStamp = resizeStamp(length2);
            if (i2 < 0) {
                if ((i2 >>> 16) != iResizeStamp || i2 == iResizeStamp + 1 || i2 == iResizeStamp + 65535 || (nodeArr2 = this.nextTable) == null || this.transferIndex <= 0) {
                    return;
                }
                if (compareAndSwapInt(this, SIZECTL, i2, i2 + 1)) {
                    transfer(nodeArr, nodeArr2);
                }
            } else if (compareAndSwapInt(this, SIZECTL, i2, (iResizeStamp << 16) + 2)) {
                transfer(nodeArr, null);
            }
            jSumCount = sumCount();
        }
    }

    Node<V>[] helpTransfer(Node<V>[] nodeArr, Node<V> node) {
        Node<V>[] nodeArr2;
        int i;
        if (nodeArr != null && (node instanceof ForwardingNode) && (nodeArr2 = ((ForwardingNode) node).nextTable) != null) {
            int iResizeStamp = resizeStamp(nodeArr.length);
            while (nodeArr2 == this.nextTable && this.table == nodeArr && (i = this.sizeCtl) < 0 && (i >>> 16) == iResizeStamp && i != iResizeStamp + 1 && i != 65535 + iResizeStamp && this.transferIndex > 0) {
                if (compareAndSwapInt(this, SIZECTL, i, i + 1)) {
                    transfer(nodeArr, nodeArr2);
                    break;
                }
            }
            return nodeArr2;
        }
        return this.table;
    }

    private void tryPresize(int i) {
        int length;
        Node<V>[] nodeArr;
        int iTableSizeFor = i >= 536870912 ? 1073741824 : tableSizeFor(i + (i >>> 1) + 1);
        while (true) {
            int i2 = this.sizeCtl;
            if (i2 < 0) {
                return;
            }
            Node<V>[] nodeArr2 = this.table;
            if (nodeArr2 == null || (length = nodeArr2.length) == 0) {
                int iMax = Math.max(i2, iTableSizeFor);
                if (compareAndSwapInt(this, SIZECTL, i2, -1)) {
                    try {
                        if (this.table == nodeArr2) {
                            this.table = new Node[iMax];
                            i2 = iMax - (iMax >>> 2);
                        }
                        this.sizeCtl = i2;
                    } catch (Throwable th) {
                        this.sizeCtl = i2;
                        throw th;
                    }
                } else {
                    continue;
                }
            } else {
                if (iTableSizeFor <= i2 || length >= 1073741824) {
                    return;
                }
                if (nodeArr2 == this.table) {
                    int iResizeStamp = resizeStamp(length);
                    if (i2 < 0) {
                        if ((i2 >>> 16) != iResizeStamp || i2 == iResizeStamp + 1 || i2 == iResizeStamp + 65535 || (nodeArr = this.nextTable) == null || this.transferIndex <= 0) {
                            return;
                        }
                        if (compareAndSwapInt(this, SIZECTL, i2, i2 + 1)) {
                            transfer(nodeArr2, nodeArr);
                        }
                    } else if (compareAndSwapInt(this, SIZECTL, i2, (iResizeStamp << 16) + 2)) {
                        transfer(nodeArr2, null);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    private void transfer(Node<V>[] nodeArr, Node<V>[] nodeArr2) {
        Node<V>[] nodeArr3;
        int i;
        int i2;
        int i3;
        ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap;
        char c;
        int i4;
        boolean z;
        Node treeBin;
        Node treeBin2;
        Node<V> node;
        Node<V> node2;
        ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap2 = this;
        Node<V>[] nodeArr4 = nodeArr;
        int length = nodeArr4.length;
        int i5 = NCPU;
        boolean z2 = true;
        int i6 = i5 > 1 ? (length >>> 3) / i5 : length;
        char c2 = 16;
        if (i6 < 16) {
            i6 = 16;
        }
        if (nodeArr2 == null) {
            try {
                nodeArr3 = new Node[length << 1];
                concurrentIntObjectHashMap2.nextTable = nodeArr3;
                concurrentIntObjectHashMap2.transferIndex = length;
            } catch (Throwable unused) {
                concurrentIntObjectHashMap2.sizeCtl = Integer.MAX_VALUE;
                return;
            }
        } else {
            nodeArr3 = nodeArr2;
        }
        int length2 = nodeArr3.length;
        ForwardingNode forwardingNode = new ForwardingNode(nodeArr3);
        boolean zCasTabAt = true;
        int i7 = 0;
        int i8 = 0;
        boolean z3 = false;
        while (true) {
            if (zCasTabAt) {
                i7--;
                if (i7 < i8 && !z3) {
                    int i9 = concurrentIntObjectHashMap2.transferIndex;
                    if (i9 <= 0) {
                        i7 = -1;
                    } else {
                        boolean z4 = zCasTabAt;
                        long j = TRANSFERINDEX;
                        int i10 = i9 > i6 ? i9 - i6 : 0;
                        if (compareAndSwapInt(concurrentIntObjectHashMap2, j, i9, i10)) {
                            i8 = i10;
                            i7 = i9 - 1;
                        } else {
                            zCasTabAt = z4;
                        }
                    }
                }
                zCasTabAt = false;
            } else {
                boolean z5 = zCasTabAt;
                if (i7 < 0 || i7 >= length || (i4 = i7 + length) >= length2) {
                    i = i6;
                    i2 = length2;
                    forwardingNode = forwardingNode;
                    i3 = i8;
                    boolean z6 = z3;
                    if (z6) {
                        this.nextTable = null;
                        this.table = nodeArr3;
                        this.sizeCtl = (length << 1) - (length >>> 1);
                        return;
                    }
                    concurrentIntObjectHashMap = this;
                    z2 = true;
                    long j2 = SIZECTL;
                    int i11 = concurrentIntObjectHashMap.sizeCtl;
                    if (compareAndSwapInt(concurrentIntObjectHashMap, j2, i11, i11 - 1)) {
                        c = 16;
                        if (i11 - 2 != (resizeStamp(length) << 16)) {
                            return;
                        }
                        i7 = length;
                        zCasTabAt = true;
                        z3 = true;
                    } else {
                        c = 16;
                        zCasTabAt = z5;
                        z3 = z6;
                    }
                } else {
                    Node<V> nodeTabAt = tabAt(nodeArr4, i7);
                    if (nodeTabAt == null) {
                        i = i6;
                        i2 = length2;
                        zCasTabAt = casTabAt(nodeArr4, i7, null, forwardingNode);
                        i3 = i8;
                        char c3 = c2;
                        concurrentIntObjectHashMap = concurrentIntObjectHashMap2;
                        forwardingNode = forwardingNode;
                        c = c3;
                    } else {
                        int i12 = nodeTabAt.hash;
                        if (i12 == -1) {
                            concurrentIntObjectHashMap = concurrentIntObjectHashMap2;
                            zCasTabAt = z2;
                            i = i6;
                            i2 = length2;
                            forwardingNode = forwardingNode;
                            i3 = i8;
                        } else {
                            synchronized (nodeTabAt) {
                                if (tabAt(nodeArr4, i7) == nodeTabAt) {
                                    if (i12 >= 0) {
                                        int i13 = i12 & length;
                                        Node<V> node3 = nodeTabAt;
                                        for (Node<V> node4 = nodeTabAt.next; node4 != null; node4 = node4.next) {
                                            int i14 = node4.hash & length;
                                            if (i14 != i13) {
                                                i13 = i14;
                                                node3 = node4;
                                            }
                                        }
                                        if (i13 == 0) {
                                            node2 = node3;
                                            node = null;
                                        } else {
                                            node = node3;
                                            node2 = null;
                                        }
                                        Node<V> node5 = nodeTabAt;
                                        Node<V> node6 = node;
                                        while (node5 != node3) {
                                            Node<V> node7 = node3;
                                            int i15 = node5.hash;
                                            int i16 = i6;
                                            int i17 = node5.key;
                                            int i18 = length2;
                                            V v = node5.val;
                                            if ((i15 & length) == 0) {
                                                node2 = new Node<>(i15, i17, v, node2);
                                            } else {
                                                node6 = new Node<>(i15, i17, v, node6);
                                            }
                                            node5 = node5.next;
                                            node3 = node7;
                                            i6 = i16;
                                            length2 = i18;
                                            i8 = i8;
                                        }
                                        i = i6;
                                        i2 = length2;
                                        i3 = i8;
                                        setTabAt(nodeArr3, i7, node2);
                                        setTabAt(nodeArr3, i4, node6);
                                        setTabAt(nodeArr4, i7, forwardingNode);
                                        forwardingNode = forwardingNode;
                                        z = z3;
                                    } else {
                                        i = i6;
                                        i2 = length2;
                                        i3 = i8;
                                        if (nodeTabAt instanceof TreeBin) {
                                            TreeBin treeBin3 = (TreeBin) nodeTabAt;
                                            Node node8 = treeBin3.first;
                                            int i19 = 0;
                                            TreeNode<V> treeNode = null;
                                            TreeNode<V> treeNode2 = null;
                                            TreeNode<V> treeNode3 = null;
                                            TreeNode<V> treeNode4 = null;
                                            int i20 = 0;
                                            while (node8 != null) {
                                                int i21 = node8.hash;
                                                boolean z7 = z3;
                                                ForwardingNode forwardingNode2 = forwardingNode;
                                                TreeNode<V> treeNode5 = new TreeNode<>(i21, node8.key, node8.val, null, null);
                                                if ((i21 & length) == 0) {
                                                    treeNode5.prev = treeNode4;
                                                    if (treeNode4 == null) {
                                                        treeNode3 = treeNode5;
                                                    } else {
                                                        treeNode4.next = treeNode5;
                                                    }
                                                    i20++;
                                                    treeNode4 = treeNode5;
                                                } else {
                                                    treeNode5.prev = treeNode2;
                                                    if (treeNode2 == null) {
                                                        treeNode = treeNode5;
                                                    } else {
                                                        treeNode2.next = treeNode5;
                                                    }
                                                    i19++;
                                                    treeNode2 = treeNode5;
                                                }
                                                node8 = node8.next;
                                                z3 = z7;
                                                forwardingNode = forwardingNode2;
                                            }
                                            ForwardingNode forwardingNode3 = forwardingNode;
                                            z = z3;
                                            if (i20 <= 6) {
                                                treeBin = untreeify(treeNode3);
                                            } else {
                                                treeBin = i19 != 0 ? new TreeBin(treeNode3) : treeBin3;
                                            }
                                            if (i19 <= 6) {
                                                treeBin2 = untreeify(treeNode);
                                            } else {
                                                treeBin2 = i20 != 0 ? new TreeBin(treeNode) : treeBin3;
                                            }
                                            setTabAt(nodeArr3, i7, treeBin);
                                            setTabAt(nodeArr3, i4, treeBin2);
                                            nodeArr4 = nodeArr;
                                            forwardingNode = forwardingNode3;
                                            setTabAt(nodeArr4, i7, forwardingNode);
                                        }
                                    }
                                    zCasTabAt = true;
                                } else {
                                    i = i6;
                                    i2 = length2;
                                    i3 = i8;
                                }
                                z = z3;
                                zCasTabAt = z5;
                            }
                            concurrentIntObjectHashMap = this;
                            z3 = z;
                            z2 = true;
                        }
                        c = 16;
                    }
                }
                i6 = i;
                length2 = i2;
                i8 = i3;
                char c4 = c;
                forwardingNode = forwardingNode;
                concurrentIntObjectHashMap2 = concurrentIntObjectHashMap;
                c2 = c4;
            }
        }
    }

    long sumCount() {
        CounterCell[] counterCellArr = this.counterCells;
        long j = this.baseCount;
        if (counterCellArr != null) {
            for (CounterCell counterCell : counterCellArr) {
                if (counterCell != null) {
                    j += counterCell.value;
                }
            }
        }
        return j;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0050  */
    private void fullAddCount(long j, boolean z) {
        int probe;
        boolean z2;
        CounterCell[] counterCellArr;
        boolean z3;
        int length;
        boolean z4;
        int length2;
        int probe2 = ThreadLocalRandom.getProbe();
        if (probe2 == 0) {
            ThreadLocalRandom.localInit();
            probe = ThreadLocalRandom.getProbe();
            z2 = true;
        } else {
            probe = probe2;
            z2 = z;
        }
        int iAdvanceProbe = probe;
        while (true) {
            boolean z5 = false;
            while (true) {
                counterCellArr = this.counterCells;
                if (counterCellArr != null && (length = counterCellArr.length) > 0) {
                    CounterCell counterCell = counterCellArr[(length - 1) & iAdvanceProbe];
                    if (counterCell == null) {
                        if (this.cellsBusy == 0) {
                            CounterCell counterCell2 = new CounterCell(j);
                            if (this.cellsBusy == 0 && compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                                try {
                                    CounterCell[] counterCellArr2 = this.counterCells;
                                    if (counterCellArr2 == null || (length2 = counterCellArr2.length) <= 0) {
                                        z4 = false;
                                    } else {
                                        int i = (length2 - 1) & iAdvanceProbe;
                                        if (counterCellArr2[i] == null) {
                                            counterCellArr2[i] = counterCell2;
                                            z4 = true;
                                        } else {
                                            z4 = false;
                                        }
                                    }
                                    this.cellsBusy = 0;
                                    if (z4) {
                                        return;
                                    }
                                } catch (Throwable th) {
                                    this.cellsBusy = 0;
                                    throw th;
                                }
                            }
                        }
                    } else {
                        if (z2) {
                            long j2 = CELLVALUE;
                            long j3 = counterCell.value;
                            if (compareAndSwapLong(counterCell, j2, j3, j3 + j)) {
                                return;
                            }
                            if (this.counterCells == counterCellArr && length < NCPU) {
                                if (z5) {
                                    if (this.cellsBusy == 0 && compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                                        break;
                                    }
                                } else {
                                    z5 = true;
                                }
                            }
                        } else {
                            z2 = true;
                        }
                        iAdvanceProbe = ThreadLocalRandom.advanceProbe(iAdvanceProbe);
                    }
                    z5 = false;
                    iAdvanceProbe = ThreadLocalRandom.advanceProbe(iAdvanceProbe);
                } else if (this.cellsBusy == 0 && this.counterCells == counterCellArr && compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                    try {
                        if (this.counterCells == counterCellArr) {
                            CounterCell[] counterCellArr3 = new CounterCell[2];
                            counterCellArr3[iAdvanceProbe & 1] = new CounterCell(j);
                            this.counterCells = counterCellArr3;
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        this.cellsBusy = 0;
                        if (z3) {
                            return;
                        }
                    } catch (Throwable th2) {
                        this.cellsBusy = 0;
                        throw th2;
                    }
                } else {
                    long j4 = BASECOUNT;
                    long j5 = this.baseCount;
                    if (compareAndSwapLong(this, j4, j5, j5 + j)) {
                        return;
                    }
                }
            }
            try {
                if (this.counterCells == counterCellArr) {
                    CounterCell[] counterCellArr4 = new CounterCell[length << 1];
                    for (int i2 = 0; i2 < length; i2++) {
                        counterCellArr4[i2] = counterCellArr[i2];
                    }
                    this.counterCells = counterCellArr4;
                }
                this.cellsBusy = 0;
            } catch (Throwable th3) {
                this.cellsBusy = 0;
                throw th3;
            }
        }
    }

    private void treeifyBin(Node<V>[] nodeArr, int i) {
        if (nodeArr != null) {
            int length = nodeArr.length;
            if (length < 64) {
                tryPresize(length << 1);
                return;
            }
            Node<V> nodeTabAt = tabAt(nodeArr, i);
            if (nodeTabAt == null || nodeTabAt.hash < 0) {
                return;
            }
            synchronized (nodeTabAt) {
                if (tabAt(nodeArr, i) == nodeTabAt) {
                    TreeNode<V> treeNode = null;
                    Node<V> node = nodeTabAt;
                    TreeNode<V> treeNode2 = null;
                    while (node != null) {
                        TreeNode<V> treeNode3 = new TreeNode<>(node.hash, node.key, node.val, null, null);
                        treeNode3.prev = treeNode2;
                        if (treeNode2 == null) {
                            treeNode = treeNode3;
                        } else {
                            treeNode2.next = treeNode3;
                        }
                        node = node.next;
                        treeNode2 = treeNode3;
                    }
                    setTabAt(nodeArr, i, new TreeBin(treeNode));
                }
            }
        }
    }

    static <V> Node<V> untreeify(Node<V> node) {
        Node<V> node2 = null;
        Node<V> node3 = null;
        while (node != null) {
            Node<V> node4 = new Node<>(node.hash, node.key, node.val, null);
            if (node3 == null) {
                node2 = node4;
            } else {
                node3.next = node4;
            }
            node = node.next;
            node3 = node4;
        }
        return node2;
    }

    static final class TreeNode<V> extends Node<V> {
        TreeNode<V> left;
        TreeNode<V> parent;
        TreeNode<V> prev;
        boolean red;
        TreeNode<V> right;

        TreeNode(int i, int i2, V v, Node<V> node, TreeNode<V> treeNode) {
            super(i, i2, v, node);
            this.parent = treeNode;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap.Node
        Node<V> find(int i, int i2) {
            return findTreeNode(i, i2);
        }

        TreeNode<V> findTreeNode(int i, int i2) {
            TreeNode<V> treeNodeFindTreeNode;
            TreeNode<V> treeNode = this;
            do {
                TreeNode<V> treeNode2 = treeNode.left;
                TreeNode<V> treeNode3 = treeNode.right;
                int i3 = treeNode.hash;
                if (i3 <= i) {
                    if (i3 >= i) {
                        if (treeNode.key == i2) {
                            return treeNode;
                        }
                        if (treeNode2 != null) {
                            if (treeNode3 != null && (treeNodeFindTreeNode = treeNode3.findTreeNode(i, i2)) != null) {
                                return treeNodeFindTreeNode;
                            }
                            treeNode = treeNode2;
                        }
                    }
                    treeNode = treeNode3;
                } else {
                    treeNode = treeNode2;
                }
            } while (treeNode != null);
            return null;
        }
    }

    static final class TreeBin<V> extends Node<V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long LOCKSTATE;
        volatile TreeNode<V> first;
        volatile int lockState;
        TreeNode<V> root;
        volatile Thread waiter;

        static {
            try {
                Object unsafe = ReflectionUtil.getUnsafe();
                LOCKSTATE = (long) MethodHandles.publicLookup().findVirtual(unsafe.getClass(), "objectFieldOffset", MethodType.methodType((Class<?>) Long.TYPE, (Class<?>) Field.class)).invoke(unsafe, TreeBin.class.getDeclaredField("lockState"));
            } catch (Throwable th) {
                throw new Error(th);
            }
        }

        TreeBin(TreeNode<V> treeNode) {
            byte b;
            super(-2, 0, null, null);
            this.first = treeNode;
            TreeNode<V> treeNode2 = null;
            while (treeNode != null) {
                TreeNode<V> treeNode3 = (TreeNode) treeNode.next;
                treeNode.right = null;
                treeNode.left = null;
                if (treeNode2 == null) {
                    treeNode.parent = null;
                    treeNode.red = false;
                } else {
                    int i = treeNode.hash;
                    TreeNode<V> treeNode4 = treeNode2;
                    while (true) {
                        int i2 = treeNode4.hash;
                        b = i2 > i ? (byte) -1 : i2 < i ? (byte) 1 : (byte) 0;
                        TreeNode<V> treeNode5 = b <= 0 ? treeNode4.left : treeNode4.right;
                        if (treeNode5 == null) {
                            break;
                        } else {
                            treeNode4 = treeNode5;
                        }
                    }
                    treeNode.parent = treeNode4;
                    if (b <= 0) {
                        treeNode4.left = treeNode;
                    } else {
                        treeNode4.right = treeNode;
                    }
                    treeNode = balanceInsertion(treeNode2, treeNode);
                }
                treeNode2 = treeNode;
                treeNode = treeNode3;
            }
            this.root = treeNode2;
        }

        private void lockRoot() {
            if (ConcurrentIntObjectHashMap.compareAndSwapInt(this, LOCKSTATE, 0, 1)) {
                return;
            }
            contendedLock();
        }

        private void unlockRoot() {
            this.lockState = 0;
        }

        private void contendedLock() {
            boolean z = false;
            while (true) {
                int i = this.lockState;
                if ((i & (-3)) == 0) {
                    if (ConcurrentIntObjectHashMap.compareAndSwapInt(this, LOCKSTATE, i, 1)) {
                        break;
                    }
                } else if ((i & 2) == 0) {
                    if (ConcurrentIntObjectHashMap.compareAndSwapInt(this, LOCKSTATE, i, i | 2)) {
                        this.waiter = Thread.currentThread();
                        z = true;
                    }
                } else if (z) {
                    LockSupport.park(this);
                }
            }
            if (z) {
                this.waiter = null;
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap.Node
        Node<V> find(int i, int i2) {
            Thread thread;
            Node<V> node = this.first;
            while (true) {
                TreeNode<V> treeNodeFindTreeNode = null;
                if (node == null) {
                    return null;
                }
                int i3 = this.lockState;
                if ((i3 & 3) == 0) {
                    if (ConcurrentIntObjectHashMap.compareAndSwapInt(this, LOCKSTATE, i3, i3 + 4)) {
                        try {
                            TreeNode<V> treeNode = this.root;
                            if (treeNode != null) {
                                treeNodeFindTreeNode = treeNode.findTreeNode(i, i2);
                            }
                            return treeNodeFindTreeNode;
                        } finally {
                            if (getAndAddInt(this, LOCKSTATE, -4) == 6 && (thread = this.waiter) != null) {
                                LockSupport.unpark(thread);
                            }
                        }
                    }
                } else {
                    if (node.key == i2) {
                        return node;
                    }
                    node = node.next;
                }
            }
        }

        private static int getAndAddInt(Object obj, long j, int i) {
            try {
                return (int) ConcurrentIntObjectHashMap.getAndAddIntHandle.invokeExact(obj, j, i);
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }

        TreeNode<V> putTreeVal(int i, int i2, V v) {
            byte b;
            TreeNode<V> treeNode;
            TreeNode<V> treeNodeFindTreeNode;
            TreeNode<V> treeNode2 = this.root;
            boolean z = false;
            while (treeNode2 != null) {
                int i3 = treeNode2.hash;
                if (i3 > i) {
                    b = -1;
                } else if (i3 < i) {
                    b = 1;
                } else {
                    if (treeNode2.key == i2) {
                        return treeNode2;
                    }
                    if (!z) {
                        TreeNode<V> treeNode3 = treeNode2.left;
                        if ((treeNode3 != null && (treeNodeFindTreeNode = treeNode3.findTreeNode(i, i2)) != null) || ((treeNode = treeNode2.right) != null && (treeNodeFindTreeNode = treeNode.findTreeNode(i, i2)) != null)) {
                            return treeNodeFindTreeNode;
                        }
                        z = true;
                    }
                    b = 0;
                }
                TreeNode<V> treeNode4 = b <= 0 ? treeNode2.left : treeNode2.right;
                if (treeNode4 == null) {
                    TreeNode<V> treeNode5 = this.first;
                    TreeNode<V> treeNode6 = new TreeNode<>(i, i2, v, treeNode5, treeNode2);
                    this.first = treeNode6;
                    if (treeNode5 != null) {
                        treeNode5.prev = treeNode6;
                    }
                    if (b <= 0) {
                        treeNode2.left = treeNode6;
                    } else {
                        treeNode2.right = treeNode6;
                    }
                    if (!treeNode2.red) {
                        treeNode6.red = true;
                        return null;
                    }
                    lockRoot();
                    try {
                        this.root = balanceInsertion(this.root, treeNode6);
                        return null;
                    } finally {
                        unlockRoot();
                    }
                }
                treeNode2 = treeNode4;
            }
            TreeNode<V> treeNode7 = new TreeNode<>(i, i2, v, null, null);
            this.root = treeNode7;
            this.first = treeNode7;
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:60:0x008e A[PHI: r0
          0x008e: PHI (r0v4 org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap$TreeNode<V>) = 
          (r0v3 org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap$TreeNode<V>)
          (r0v12 org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap$TreeNode<V>)
         binds: [B:58:0x008a, B:54:0x0083] A[DONT_GENERATE, DONT_INLINE]] */
        boolean removeTreeNode(TreeNode<V> treeNode) {
            TreeNode<V> treeNode2;
            TreeNode<V> treeNode3;
            TreeNode<V> treeNode4 = (TreeNode) treeNode.next;
            TreeNode<V> treeNode5 = treeNode.prev;
            if (treeNode5 == null) {
                this.first = treeNode4;
            } else {
                treeNode5.next = treeNode4;
            }
            if (treeNode4 != null) {
                treeNode4.prev = treeNode5;
            }
            if (this.first == null) {
                this.root = null;
                return true;
            }
            TreeNode<V> treeNodeBalanceDeletion = this.root;
            if (treeNodeBalanceDeletion == null || treeNodeBalanceDeletion.right == null || (treeNode2 = treeNodeBalanceDeletion.left) == null || treeNode2.left == null) {
                return true;
            }
            lockRoot();
            try {
                TreeNode<V> treeNode6 = treeNode.left;
                TreeNode<V> treeNode7 = treeNode.right;
                if (treeNode6 != null && treeNode7 != null) {
                    TreeNode<V> treeNode8 = treeNode7;
                    while (true) {
                        TreeNode<V> treeNode9 = treeNode8.left;
                        if (treeNode9 == null) {
                            break;
                        }
                        treeNode8 = treeNode9;
                    }
                    boolean z = treeNode8.red;
                    treeNode8.red = treeNode.red;
                    treeNode.red = z;
                    TreeNode<V> treeNode10 = treeNode8.right;
                    TreeNode<V> treeNode11 = treeNode.parent;
                    if (treeNode8 == treeNode7) {
                        treeNode.parent = treeNode8;
                        treeNode8.right = treeNode;
                    } else {
                        TreeNode<V> treeNode12 = treeNode8.parent;
                        treeNode.parent = treeNode12;
                        if (treeNode12 != null) {
                            if (treeNode8 == treeNode12.left) {
                                treeNode12.left = treeNode;
                            } else {
                                treeNode12.right = treeNode;
                            }
                        }
                        treeNode8.right = treeNode7;
                        if (treeNode7 != null) {
                            treeNode7.parent = treeNode8;
                        }
                    }
                    treeNode.left = null;
                    treeNode.right = treeNode10;
                    if (treeNode10 != null) {
                        treeNode10.parent = treeNode;
                    }
                    treeNode8.left = treeNode6;
                    if (treeNode6 != null) {
                        treeNode6.parent = treeNode8;
                    }
                    treeNode8.parent = treeNode11;
                    if (treeNode11 == null) {
                        treeNodeBalanceDeletion = treeNode8;
                    } else if (treeNode == treeNode11.left) {
                        treeNode11.left = treeNode8;
                    } else {
                        treeNode11.right = treeNode8;
                    }
                    if (treeNode10 != null) {
                        treeNode6 = treeNode10;
                    } else {
                        treeNode6 = treeNode;
                    }
                } else if (treeNode6 == null) {
                    if (treeNode7 != null) {
                        treeNode6 = treeNode7;
                    } else {
                        treeNode6 = treeNode;
                    }
                }
                if (treeNode6 != treeNode) {
                    TreeNode<V> treeNode13 = treeNode.parent;
                    treeNode6.parent = treeNode13;
                    if (treeNode13 == null) {
                        treeNodeBalanceDeletion = treeNode6;
                    } else if (treeNode == treeNode13.left) {
                        treeNode13.left = treeNode6;
                    } else {
                        treeNode13.right = treeNode6;
                    }
                    treeNode.parent = null;
                    treeNode.right = null;
                    treeNode.left = null;
                }
                if (!treeNode.red) {
                    treeNodeBalanceDeletion = balanceDeletion(treeNodeBalanceDeletion, treeNode6);
                }
                this.root = treeNodeBalanceDeletion;
                if (treeNode == treeNode6 && (treeNode3 = treeNode.parent) != null) {
                    if (treeNode == treeNode3.left) {
                        treeNode3.left = null;
                    } else if (treeNode == treeNode3.right) {
                        treeNode3.right = null;
                    }
                    treeNode.parent = null;
                }
                return false;
            } finally {
                unlockRoot();
            }
        }

        static <V> TreeNode<V> rotateLeft(TreeNode<V> treeNode, TreeNode<V> treeNode2) {
            TreeNode<V> treeNode3;
            if (treeNode2 != null && (treeNode3 = treeNode2.right) != null) {
                TreeNode<V> treeNode4 = treeNode3.left;
                treeNode2.right = treeNode4;
                if (treeNode4 != null) {
                    treeNode4.parent = treeNode2;
                }
                TreeNode<V> treeNode5 = treeNode2.parent;
                treeNode3.parent = treeNode5;
                if (treeNode5 == null) {
                    treeNode3.red = false;
                    treeNode = treeNode3;
                } else if (treeNode5.left == treeNode2) {
                    treeNode5.left = treeNode3;
                } else {
                    treeNode5.right = treeNode3;
                }
                treeNode3.left = treeNode2;
                treeNode2.parent = treeNode3;
            }
            return treeNode;
        }

        static <V> TreeNode<V> rotateRight(TreeNode<V> treeNode, TreeNode<V> treeNode2) {
            TreeNode<V> treeNode3;
            if (treeNode2 != null && (treeNode3 = treeNode2.left) != null) {
                TreeNode<V> treeNode4 = treeNode3.right;
                treeNode2.left = treeNode4;
                if (treeNode4 != null) {
                    treeNode4.parent = treeNode2;
                }
                TreeNode<V> treeNode5 = treeNode2.parent;
                treeNode3.parent = treeNode5;
                if (treeNode5 == null) {
                    treeNode3.red = false;
                    treeNode = treeNode3;
                } else if (treeNode5.right == treeNode2) {
                    treeNode5.right = treeNode3;
                } else {
                    treeNode5.left = treeNode3;
                }
                treeNode3.right = treeNode2;
                treeNode2.parent = treeNode3;
            }
            return treeNode;
        }

        static <V> TreeNode<V> balanceInsertion(TreeNode<V> treeNode, TreeNode<V> treeNode2) {
            TreeNode<V> treeNode3;
            treeNode2.red = true;
            while (true) {
                TreeNode<V> treeNode4 = treeNode2.parent;
                if (treeNode4 == null) {
                    treeNode2.red = false;
                    return treeNode2;
                }
                if (!treeNode4.red || (treeNode3 = treeNode4.parent) == null) {
                    return treeNode;
                }
                TreeNode<V> treeNode5 = treeNode3.left;
                if (treeNode4 == treeNode5) {
                    TreeNode<V> treeNode6 = treeNode3.right;
                    if (treeNode6 != null && treeNode6.red) {
                        treeNode6.red = false;
                        treeNode4.red = false;
                        treeNode3.red = true;
                        treeNode2 = treeNode3;
                    } else {
                        if (treeNode2 == treeNode4.right) {
                            treeNode = rotateLeft(treeNode, treeNode4);
                            TreeNode<V> treeNode7 = treeNode4.parent;
                            treeNode3 = treeNode7 == null ? null : treeNode7.parent;
                            treeNode4 = treeNode7;
                            treeNode2 = treeNode4;
                        }
                        if (treeNode4 != null) {
                            treeNode4.red = false;
                            if (treeNode3 != null) {
                                treeNode3.red = true;
                                treeNode = rotateRight(treeNode, treeNode3);
                            }
                        }
                    }
                } else if (treeNode5 != null && treeNode5.red) {
                    treeNode5.red = false;
                    treeNode4.red = false;
                    treeNode3.red = true;
                    treeNode2 = treeNode3;
                } else {
                    if (treeNode2 == treeNode4.left) {
                        treeNode = rotateRight(treeNode, treeNode4);
                        TreeNode<V> treeNode8 = treeNode4.parent;
                        treeNode3 = treeNode8 == null ? null : treeNode8.parent;
                        treeNode4 = treeNode8;
                        treeNode2 = treeNode4;
                    }
                    if (treeNode4 != null) {
                        treeNode4.red = false;
                        if (treeNode3 != null) {
                            treeNode3.red = true;
                            treeNode = rotateLeft(treeNode, treeNode3);
                        }
                    }
                }
            }
        }

        static <V> TreeNode<V> balanceDeletion(TreeNode<V> treeNode, TreeNode<V> treeNode2) {
            while (treeNode2 != null && treeNode2 != treeNode) {
                TreeNode<V> treeNode3 = treeNode2.parent;
                if (treeNode3 == null) {
                    treeNode2.red = false;
                    return treeNode2;
                }
                if (treeNode2.red) {
                    treeNode2.red = false;
                    return treeNode;
                }
                TreeNode<V> treeNode4 = treeNode3.left;
                if (treeNode4 == treeNode2) {
                    TreeNode<V> treeNode5 = treeNode3.right;
                    if (treeNode5 != null && treeNode5.red) {
                        treeNode5.red = false;
                        treeNode3.red = true;
                        treeNode = rotateLeft(treeNode, treeNode3);
                        treeNode3 = treeNode2.parent;
                        treeNode5 = treeNode3 == null ? null : treeNode3.right;
                    }
                    if (treeNode5 != null) {
                        TreeNode<V> treeNode6 = treeNode5.left;
                        TreeNode<V> treeNode7 = treeNode5.right;
                        if ((treeNode7 == null || !treeNode7.red) && (treeNode6 == null || !treeNode6.red)) {
                            treeNode5.red = true;
                        } else {
                            if (treeNode7 == null || !treeNode7.red) {
                                if (treeNode6 != null) {
                                    treeNode6.red = false;
                                }
                                treeNode5.red = true;
                                treeNode = rotateRight(treeNode, treeNode5);
                                treeNode3 = treeNode2.parent;
                                treeNode5 = treeNode3 != null ? treeNode3.right : null;
                            }
                            if (treeNode5 != null) {
                                treeNode5.red = treeNode3 == null ? false : treeNode3.red;
                                TreeNode<V> treeNode8 = treeNode5.right;
                                if (treeNode8 != null) {
                                    treeNode8.red = false;
                                }
                            }
                            if (treeNode3 != null) {
                                treeNode3.red = false;
                                treeNode = rotateLeft(treeNode, treeNode3);
                            }
                            treeNode2 = treeNode;
                        }
                    }
                    treeNode2 = treeNode3;
                } else {
                    if (treeNode4 != null && treeNode4.red) {
                        treeNode4.red = false;
                        treeNode3.red = true;
                        treeNode = rotateRight(treeNode, treeNode3);
                        treeNode3 = treeNode2.parent;
                        treeNode4 = treeNode3 == null ? null : treeNode3.left;
                    }
                    if (treeNode4 != null) {
                        TreeNode<V> treeNode9 = treeNode4.left;
                        TreeNode<V> treeNode10 = treeNode4.right;
                        if ((treeNode9 == null || !treeNode9.red) && (treeNode10 == null || !treeNode10.red)) {
                            treeNode4.red = true;
                        } else {
                            if (treeNode9 == null || !treeNode9.red) {
                                if (treeNode10 != null) {
                                    treeNode10.red = false;
                                }
                                treeNode4.red = true;
                                treeNode = rotateLeft(treeNode, treeNode4);
                                treeNode3 = treeNode2.parent;
                                treeNode4 = treeNode3 != null ? treeNode3.left : null;
                            }
                            if (treeNode4 != null) {
                                treeNode4.red = treeNode3 == null ? false : treeNode3.red;
                                TreeNode<V> treeNode11 = treeNode4.left;
                                if (treeNode11 != null) {
                                    treeNode11.red = false;
                                }
                            }
                            if (treeNode3 != null) {
                                treeNode3.red = false;
                                treeNode = rotateRight(treeNode, treeNode3);
                            }
                            treeNode2 = treeNode;
                        }
                    }
                    treeNode2 = treeNode3;
                }
            }
            return treeNode;
        }

        static <V> boolean checkInvariants(TreeNode<V> treeNode) {
            TreeNode<V> treeNode2 = treeNode.parent;
            TreeNode<V> treeNode3 = treeNode.left;
            TreeNode<V> treeNode4 = treeNode.right;
            TreeNode<V> treeNode5 = treeNode.prev;
            TreeNode treeNode6 = (TreeNode) treeNode.next;
            if (treeNode5 != null && treeNode5.next != treeNode) {
                return false;
            }
            if (treeNode6 != null && treeNode6.prev != treeNode) {
                return false;
            }
            if (treeNode2 != null && treeNode != treeNode2.left && treeNode != treeNode2.right) {
                return false;
            }
            if (treeNode3 != null && (treeNode3.parent != treeNode || treeNode3.hash > treeNode.hash)) {
                return false;
            }
            if (treeNode4 != null && (treeNode4.parent != treeNode || treeNode4.hash < treeNode.hash)) {
                return false;
            }
            if (treeNode.red && treeNode3 != null && treeNode3.red && treeNode4 != null && treeNode4.red) {
                return false;
            }
            if (treeNode3 == null || checkInvariants(treeNode3)) {
                return treeNode4 == null || checkInvariants(treeNode4);
            }
            return false;
        }
    }

    static final class TableStack<V> {
        int index;
        int length;
        TableStack<V> next;
        Node<V>[] tab;

        TableStack() {
        }
    }

    static class Traverser<V> {
        int baseIndex;
        int baseLimit;
        final int baseSize;
        int index;
        Node<V> next = null;
        TableStack<V> spare;
        TableStack<V> stack;
        Node<V>[] tab;

        Traverser(Node<V>[] nodeArr, int i, int i2, int i3) {
            this.tab = nodeArr;
            this.baseSize = i;
            this.index = i2;
            this.baseIndex = i2;
            this.baseLimit = i3;
        }

        /* JADX WARN: Code duplicated, block: B:45:0x004b A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:46:0x0047 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:47:0x0052 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:49:0x0006 A[SYNTHETIC] */
        final Node<V> advance() {
            Node<V>[] nodeArr;
            int length;
            int i;
            int i2;
            Node<V> node = this.next;
            if (node != null) {
                node = node.next;
            }
            while (node == null) {
                if (this.baseIndex >= this.baseLimit || (nodeArr = this.tab) == null || (length = nodeArr.length) <= (i = this.index) || i < 0) {
                    this.next = null;
                    return null;
                }
                Node<V> nodeTabAt = ConcurrentIntObjectHashMap.tabAt(nodeArr, i);
                if (nodeTabAt != null && nodeTabAt.hash < 0) {
                    if (nodeTabAt instanceof ForwardingNode) {
                        this.tab = ((ForwardingNode) nodeTabAt).nextTable;
                        pushState(nodeArr, i, length);
                        node = null;
                    } else {
                        node = nodeTabAt instanceof TreeBin ? ((TreeBin) nodeTabAt).first : null;
                        if (this.stack != null) {
                            recoverState(length);
                        } else {
                            i2 = i + this.baseSize;
                            this.index = i2;
                            if (i2 >= length) {
                                int i3 = this.baseIndex + 1;
                                this.baseIndex = i3;
                                this.index = i3;
                            }
                        }
                    }
                } else {
                    node = nodeTabAt;
                    if (this.stack != null) {
                        recoverState(length);
                    } else {
                        i2 = i + this.baseSize;
                        this.index = i2;
                        if (i2 >= length) {
                            int i4 = this.baseIndex + 1;
                            this.baseIndex = i4;
                            this.index = i4;
                        }
                    }
                }
            }
            this.next = node;
            return node;
        }

        private void pushState(Node<V>[] nodeArr, int i, int i2) {
            TableStack<V> tableStack = this.spare;
            if (tableStack != null) {
                this.spare = tableStack.next;
            } else {
                tableStack = new TableStack<>();
            }
            tableStack.tab = nodeArr;
            tableStack.length = i2;
            tableStack.index = i;
            tableStack.next = this.stack;
            this.stack = tableStack;
        }

        private void recoverState(int i) {
            TableStack<V> tableStack;
            while (true) {
                tableStack = this.stack;
                if (tableStack == null) {
                    break;
                }
                int i2 = this.index;
                int i3 = tableStack.length;
                int i4 = i2 + i3;
                this.index = i4;
                if (i4 < i) {
                    break;
                }
                this.index = tableStack.index;
                this.tab = tableStack.tab;
                tableStack.tab = null;
                TableStack<V> tableStack2 = tableStack.next;
                tableStack.next = this.spare;
                this.stack = tableStack2;
                this.spare = tableStack;
                i = i3;
            }
            if (tableStack == null) {
                int i5 = this.index + this.baseSize;
                this.index = i5;
                if (i5 >= i) {
                    int i6 = this.baseIndex + 1;
                    this.baseIndex = i6;
                    this.index = i6;
                }
            }
        }
    }

    static class BaseIterator<V> extends Traverser<V> {
        Node<V> lastReturned;
        final ConcurrentIntObjectHashMap<V> map;

        BaseIterator(Node<V>[] nodeArr, int i, int i2, int i3, ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap) {
            super(nodeArr, i, i2, i3);
            this.map = concurrentIntObjectHashMap;
            advance();
        }

        public final boolean hasNext() {
            return this.next != null;
        }

        public final boolean hasMoreElements() {
            return this.next != null;
        }

        public final void remove() {
            Node<V> node = this.lastReturned;
            if (node == null) {
                throw new IllegalStateException();
            }
            this.lastReturned = null;
            this.map.replaceNode(node.key, null, node.val);
        }
    }

    static final class ValueIterator<V> extends BaseIterator<V> implements Enumeration<V>, Iterator<V> {
        ValueIterator(Node<V>[] nodeArr, int i, int i2, int i3, ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap) {
            super(nodeArr, i, i2, i3, concurrentIntObjectHashMap);
        }

        @Override // java.util.Iterator
        public V next() {
            Node<V> node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            V v = node.val;
            this.lastReturned = node;
            advance();
            return v;
        }

        @Override // java.util.Enumeration
        public V nextElement() {
            return next();
        }
    }

    static final class EntryIterator<V> extends BaseIterator<V> implements Iterator<IntObjectMap.Entry<V>> {
        EntryIterator(Node<V>[] nodeArr, int i, int i2, int i3, ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap) {
            super(nodeArr, i, i2, i3, concurrentIntObjectHashMap);
        }

        @Override // java.util.Iterator
        public IntObjectMap.Entry<V> next() {
            Node<V> node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            int i = node.key;
            V v = node.val;
            this.lastReturned = node;
            advance();
            return new SimpleEntry(i, v);
        }
    }

    static abstract class CollectionView<V, E> implements Collection<E> {
        final ConcurrentIntObjectHashMap<V> map;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 4 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 1 || i == 4 || i == 5 || i == 6) ? 3 : 2];
            if (i == 1) {
                objArr[0] = "a";
            } else if (i == 4 || i == 5 || i == 6) {
                objArr[0] = "c";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentIntObjectHashMap$CollectionView";
            }
            if (i == 1 || i == 4 || i == 5 || i == 6) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentIntObjectHashMap$CollectionView";
            } else {
                objArr[1] = "toArray";
            }
            if (i == 1) {
                objArr[2] = "toArray";
            } else if (i == 4) {
                objArr[2] = "containsAll";
            } else if (i == 5) {
                objArr[2] = "removeAll";
            } else if (i == 6) {
                objArr[2] = "retainAll";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 4 && i != 5 && i != 6) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        @Override // java.util.Collection
        public abstract boolean contains(Object obj);

        @Override // java.util.Collection, java.lang.Iterable
        public abstract Iterator<E> iterator();

        CollectionView(ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap) {
            this.map = concurrentIntObjectHashMap;
        }

        @Override // java.util.Collection
        public final void clear() {
            this.map.clear();
        }

        @Override // java.util.Collection
        public final int size() {
            return this.map.size();
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.Collection
        public final Object[] toArray() {
            long jMappingCount = this.map.mappingCount();
            if (jMappingCount > 2147483639) {
                throw new OutOfMemoryError("Required array size too large");
            }
            int i = (int) jMappingCount;
            Object[] objArrCopyOf = new Object[i];
            int i2 = 0;
            for (E e : this) {
                if (i2 == i) {
                    int i3 = Util.maxArraySize;
                    if (i >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (i < 1073741819) {
                        i3 = (i >>> 1) + 1 + i;
                    }
                    objArrCopyOf = Arrays.copyOf(objArrCopyOf, i3);
                    i = i3;
                }
                objArrCopyOf[i2] = e;
                i2++;
            }
            if (i2 != i) {
                objArrCopyOf = Arrays.copyOf(objArrCopyOf, i2);
            }
            if (objArrCopyOf == null) {
                $$$reportNull$$$0(0);
            }
            return objArrCopyOf;
        }

        @Override // java.util.Collection
        public final <T> T[] toArray(T[] tArr) {
            if (tArr == null) {
                $$$reportNull$$$0(1);
            }
            long jMappingCount = this.map.mappingCount();
            if (jMappingCount > 2147483639) {
                throw new OutOfMemoryError("Required array size too large");
            }
            int i = (int) jMappingCount;
            Object[] objArr = tArr.length >= i ? tArr : (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i);
            int length = objArr.length;
            int i2 = 0;
            for (E e : this) {
                if (i2 == length) {
                    int i3 = Util.maxArraySize;
                    if (length >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (length < 1073741819) {
                        i3 = (length >>> 1) + 1 + length;
                    }
                    objArr = (T[]) Arrays.copyOf(objArr, i3);
                    length = i3;
                }
                objArr[i2] = e;
                i2++;
            }
            if (tArr == objArr && i2 < length) {
                objArr[i2] = null;
                if (objArr == null) {
                    $$$reportNull$$$0(2);
                }
                return (T[]) objArr;
            }
            if (i2 != length) {
                objArr = (T[]) Arrays.copyOf(objArr, i2);
            }
            if (objArr == null) {
                $$$reportNull$$$0(3);
            }
            return (T[]) objArr;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[");
            Iterator<E> it = iterator();
            if (it.hasNext()) {
                while (true) {
                    Object next = it.next();
                    if (next == this) {
                        next = "(this Collection)";
                    }
                    sb.append(next);
                    if (!it.hasNext()) {
                        break;
                    }
                    sb.append(", ");
                }
            }
            return sb.append(']').toString();
        }

        @Override // java.util.Collection
        public final boolean containsAll(Collection<?> collection) {
            if (collection == null) {
                $$$reportNull$$$0(4);
            }
            if (collection == this) {
                return true;
            }
            for (Object obj : collection) {
                if (obj == null || !contains(obj)) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public final boolean removeAll(Collection<?> collection) {
            if (collection == null) {
                $$$reportNull$$$0(5);
            }
            Iterator<E> it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Collection
        public final boolean retainAll(Collection<?> collection) {
            if (collection == null) {
                $$$reportNull$$$0(6);
            }
            Iterator<E> it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }
    }

    static final class ValuesView<V> extends CollectionView<V, V> implements Collection<V> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "c", "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentIntObjectHashMap$ValuesView", "addAll"));
        }

        ValuesView(ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap) {
            super(concurrentIntObjectHashMap);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap.CollectionView, java.util.Collection
        public boolean contains(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            if (obj == null) {
                return false;
            }
            Iterator<V> it = iterator();
            while (it.hasNext()) {
                if (obj.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap.CollectionView, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap = this.map;
            Node<V>[] nodeArr = concurrentIntObjectHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new ValueIterator(nodeArr, length, 0, length, concurrentIntObjectHashMap);
        }

        @Override // java.util.Collection
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            if (collection == null) {
                $$$reportNull$$$0(0);
            }
            throw new UnsupportedOperationException();
        }
    }

    static final class EntrySetView<V> extends CollectionView<V, IntObjectMap.Entry<V>> implements Set<IntObjectMap.Entry<V>> {
        EntrySetView(ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap) {
            super(concurrentIntObjectHashMap);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap.CollectionView, java.util.Collection
        public boolean contains(Object obj) {
            Object value;
            if (obj instanceof IntObjectMap.Entry) {
                IntObjectMap.Entry entry = (IntObjectMap.Entry) obj;
                V v = this.map.get(entry.getKey());
                if (v != null && (value = entry.getValue()) != null && (value == v || value.equals(v))) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            IntObjectMap.Entry entry;
            Object value;
            return (obj instanceof Map.Entry) && (entry = (IntObjectMap.Entry) obj) != null && (value = entry.getValue()) != null && this.map.remove(entry.getKey(), value);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectHashMap.CollectionView, java.util.Collection, java.lang.Iterable
        public Iterator<IntObjectMap.Entry<V>> iterator() {
            ConcurrentIntObjectHashMap<V> concurrentIntObjectHashMap = this.map;
            Node<V>[] nodeArr = concurrentIntObjectHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new EntryIterator(nodeArr, length, 0, length, concurrentIntObjectHashMap);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean add(IntObjectMap.Entry<V> entry) {
            return this.map.putVal(entry.getKey(), entry.getValue(), false) == null;
        }

        @Override // java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends IntObjectMap.Entry<V>> collection) {
            Iterator<? extends IntObjectMap.Entry<V>> it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (add((IntObjectMap.Entry) it.next())) {
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            Node<V>[] nodeArr = this.map.table;
            int iHashCode = 0;
            if (nodeArr != null) {
                Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
                while (true) {
                    Node<V> nodeAdvance = traverser.advance();
                    if (nodeAdvance == null) {
                        break;
                    }
                    iHashCode += nodeAdvance.hashCode();
                }
            }
            return iHashCode;
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(Object obj) {
            Set set;
            return (obj instanceof Set) && ((set = (Set) obj) == this || (containsAll(set) && set.containsAll(this)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean compareAndSwapInt(Object obj, long j, int i, int i2) {
        try {
            return (boolean) compareAndSwapIntHandle.invokeExact(obj, j, i, i2);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private static boolean compareAndSwapLong(Object obj, long j, long j2, long j3) {
        try {
            return (boolean) compareAndSwapLongHandle.invokeExact(obj, j, j2, j3);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentIntObjectMap
    public V cacheOrGet(int i, V v) {
        if (v == null) {
            $$$reportNull$$$0(11);
        }
        V v2 = get(i);
        if (v2 != null) {
            if (v2 == null) {
                $$$reportNull$$$0(12);
            }
            return v2;
        }
        V vPutIfAbsent = putIfAbsent(i, v);
        if (vPutIfAbsent != null) {
            v = vPutIfAbsent;
        }
        if (v == null) {
            $$$reportNull$$$0(13);
        }
        return v;
    }
}
