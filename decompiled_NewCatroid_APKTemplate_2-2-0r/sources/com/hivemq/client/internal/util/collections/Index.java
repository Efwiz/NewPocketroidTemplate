package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Pow2Util;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class Index<E, K> {
    private static final int MAX_CAPACITY = 1073741824;
    private int nodeCount;
    private int nodeThreshold;
    private int size;
    private final Spec<E, K> spec;
    private Object[] table;

    /* JADX WARN: Multi-variable type inference failed */
    private E cast(Object obj) {
        return obj;
    }

    public static class Spec<E, K> {
        private static final int DEFAULT_MIN_CAPACITY = 16;
        private static final float DEFAULT_NODE_THRESHOLD_FACTOR = 0.25f;
        final Function<? super E, ? extends K> keyFunction;
        final int minCapacity;
        final float nodeThresholdFactor;

        public Spec(Function<? super E, ? extends K> function) {
            this(function, 16, DEFAULT_NODE_THRESHOLD_FACTOR);
        }

        public Spec(Function<? super E, ? extends K> function, int i) {
            this(function, i, DEFAULT_NODE_THRESHOLD_FACTOR);
        }

        public Spec(Function<? super E, ? extends K> function, float f) {
            this(function, 16, f);
        }

        public Spec(Function<? super E, ? extends K> function, int i, float f) {
            this.keyFunction = function;
            this.minCapacity = i;
            this.nodeThresholdFactor = f;
        }
    }

    public Index(Spec<E, K> spec) {
        this.spec = spec;
        int iRoundToPowerOf2Bits = 1 << Pow2Util.roundToPowerOf2Bits(spec.minCapacity);
        this.table = new Object[iRoundToPowerOf2Bits];
        calcThresholds(iRoundToPowerOf2Bits);
    }

    public int size() {
        return this.size;
    }

    public E put(E e) {
        return put(e, true);
    }

    public E putIfAbsent(E e) {
        return put(e, false);
    }

    private E put(E e, boolean z) {
        Object[] objArr = this.table;
        K kApply = this.spec.keyFunction.apply(e);
        int iHashCode = kApply.hashCode();
        int length = (objArr.length - 1) & iHashCode;
        Object obj = objArr[length];
        if (obj == null) {
            objArr[length] = e;
            added();
            return null;
        }
        if (obj.getClass() != Node.class) {
            E eCast = cast(obj);
            K kApply2 = this.spec.keyFunction.apply(eCast);
            if (kApply2.equals(kApply)) {
                if (z) {
                    objArr[length] = e;
                }
                return eCast;
            }
            objArr[length] = new Node(kApply2.hashCode(), obj, e);
            added();
            addedNode();
            return null;
        }
        while (true) {
            Node node = (Node) obj;
            if (node.hash == iHashCode && this.spec.keyFunction.apply(cast(node.value)).equals(kApply)) {
                Object obj2 = node.value;
                if (z) {
                    node.value = e;
                }
                return cast(obj2);
            }
            Object obj3 = node.next;
            if (obj3.getClass() == Node.class) {
                obj = obj3;
            } else {
                E eCast2 = cast(obj3);
                K kApply3 = this.spec.keyFunction.apply(eCast2);
                if (kApply3.equals(kApply)) {
                    if (z) {
                        node.next = e;
                    }
                    return eCast2;
                }
                node.next = new Node(kApply3.hashCode(), obj3, e);
                added();
                addedNode();
                return null;
            }
        }
    }

    public E get(K k) {
        Object[] objArr = this.table;
        int iHashCode = k.hashCode();
        Object obj = objArr[(objArr.length - 1) & iHashCode];
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == Node.class) {
            do {
                Node node = (Node) obj;
                if (node.hash == iHashCode && this.spec.keyFunction.apply(cast(node.value)).equals(k)) {
                    return cast(node.value);
                }
                obj = node.next;
            } while (obj.getClass() == Node.class);
            E eCast = cast(obj);
            if (this.spec.keyFunction.apply(eCast).equals(k)) {
                return eCast;
            }
            return null;
        }
        E eCast2 = cast(obj);
        if (this.spec.keyFunction.apply(eCast2).equals(k)) {
            return eCast2;
        }
        return null;
    }

    public E remove(K k) {
        Object[] objArr = this.table;
        int iHashCode = k.hashCode();
        int length = (objArr.length - 1) & iHashCode;
        Object obj = objArr[length];
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == Node.class) {
            Node node = (Node) obj;
            if (node.hash == iHashCode && this.spec.keyFunction.apply(cast(node.value)).equals(k)) {
                objArr[length] = node.next;
                removedNode();
                removed();
                return cast(node.value);
            }
            Object obj2 = node.next;
            if (obj2.getClass() != Node.class) {
                E eCast = cast(obj2);
                if (!this.spec.keyFunction.apply(eCast).equals(k)) {
                    return null;
                }
                objArr[length] = node.value;
                removedNode();
                removed();
                return eCast;
            }
            while (true) {
                Node node2 = (Node) obj2;
                if (node2.hash == iHashCode && this.spec.keyFunction.apply(cast(node2.value)).equals(k)) {
                    node.next = node2.next;
                    removedNode();
                    removed();
                    return cast(node2.value);
                }
                obj2 = node2.next;
                if (obj2.getClass() != Node.class) {
                    E eCast2 = cast(obj2);
                    if (!this.spec.keyFunction.apply(eCast2).equals(k)) {
                        return null;
                    }
                    node.next = node2.value;
                    removedNode();
                    removed();
                    return eCast2;
                }
                node = node2;
            }
        } else {
            E eCast3 = cast(obj);
            if (!this.spec.keyFunction.apply(eCast3).equals(k)) {
                return null;
            }
            objArr[length] = null;
            removed();
            return eCast3;
        }
    }

    public void clear() {
        if (this.size > 0) {
            if (this.table.length == this.spec.minCapacity) {
                Arrays.fill(this.table, (Object) null);
            } else {
                this.table = new Object[this.spec.minCapacity];
            }
            this.size = 0;
            this.nodeCount = 0;
            calcThresholds(this.spec.minCapacity);
        }
    }

    public E any() {
        for (Object obj : this.table) {
            if (obj != null) {
                if (obj.getClass() == Node.class) {
                    return cast(((Node) obj).value);
                }
                return cast(obj);
            }
        }
        throw new NoSuchElementException();
    }

    public void forEach(Consumer<? super E> consumer) {
        for (Object obj : this.table) {
            if (obj != null) {
                if (obj.getClass() == Node.class) {
                    do {
                        Node node = (Node) obj;
                        consumer.accept(cast(node.value));
                        obj = node.next;
                    } while (obj.getClass() == Node.class);
                    consumer.accept(cast(obj));
                } else {
                    consumer.accept(cast(obj));
                }
            }
        }
    }

    private void added() {
        this.size++;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x009b  */
    private void addedNode() {
        Object[] objArr;
        int i;
        Object obj;
        int i2 = this.nodeCount + 1;
        this.nodeCount = i2;
        if (i2 > this.nodeThreshold) {
            Object[] objArr2 = this.table;
            if (objArr2.length < 1073741824) {
                int length = objArr2.length;
                int i3 = length << 1;
                int i4 = i3 - 1;
                Object[] objArr3 = new Object[i3];
                int i5 = 0;
                int i6 = 0;
                while (i5 < length) {
                    Object obj2 = objArr2[i5];
                    if (obj2 == null) {
                        objArr = objArr2;
                    } else if (obj2.getClass() == Node.class) {
                        Node node = (Node) obj2;
                        int i7 = i5 + length;
                        Node node2 = null;
                        Node node3 = null;
                        Node node4 = null;
                        Node node5 = null;
                        while (true) {
                            if ((node.hash & i4) == i5) {
                                if (node3 == null) {
                                    objArr3[i5] = node;
                                } else {
                                    node3.next = node;
                                    node4 = node3;
                                }
                                node3 = node;
                            } else {
                                if (node2 == null) {
                                    objArr3[i7] = node;
                                } else {
                                    node2.next = node;
                                    node5 = node2;
                                }
                                node2 = node;
                            }
                            i = i6 + 1;
                            obj = node.next;
                            objArr = objArr2;
                            if (obj.getClass() != Node.class) {
                                break;
                            }
                            node = (Node) obj;
                            i6 = i;
                            objArr2 = objArr;
                        }
                        E eCast = cast(obj);
                        if ((this.spec.keyFunction.apply(eCast).hashCode() & i4) == i5) {
                            if (node3 == null) {
                                objArr3[i5] = eCast;
                            } else {
                                node3.next = eCast;
                            }
                            if (node2 == null) {
                                i6 = i;
                            } else if (node5 == null) {
                                objArr3[i7] = node2.value;
                            } else {
                                node5.next = node2.value;
                            }
                        } else {
                            if (node2 == null) {
                                objArr3[i7] = eCast;
                            } else {
                                node2.next = eCast;
                            }
                            if (node3 == null) {
                                i6 = i;
                            } else if (node4 == null) {
                                objArr3[i5] = node3.value;
                            } else {
                                node4.next = node3.value;
                            }
                        }
                    } else {
                        objArr = objArr2;
                        objArr3[this.spec.keyFunction.apply(cast(obj2)).hashCode() & i4] = obj2;
                    }
                    i5++;
                    objArr2 = objArr;
                }
                this.table = objArr3;
                this.nodeCount = i6;
                calcThresholds(i3);
            }
        }
    }

    private void removedNode() {
        this.nodeCount--;
    }

    private void removed() {
        Node node;
        Object obj;
        int i = this.size - 1;
        this.size = i;
        if (i >= this.nodeThreshold || this.table.length <= this.spec.minCapacity) {
            return;
        }
        Object[] objArr = this.table;
        int length = objArr.length;
        int i2 = length >> 1;
        Object[] objArr2 = new Object[i2];
        int i3 = this.nodeCount;
        System.arraycopy(objArr, 0, objArr2, 0, i2);
        for (int i4 = i2; i4 < length; i4++) {
            Object obj2 = objArr[i4];
            if (obj2 != null) {
                int i5 = i4 - i2;
                Object obj3 = objArr2[i5];
                if (obj3 == null) {
                    objArr2[i5] = obj2;
                } else {
                    if (obj3.getClass() == Node.class) {
                        while (true) {
                            node = (Node) obj3;
                            obj = node.next;
                            if (obj.getClass() != Node.class) {
                                break;
                            } else {
                                obj3 = obj;
                            }
                        }
                        node.next = new Node(this.spec.keyFunction.apply(cast(obj)).hashCode(), obj, obj2);
                    } else {
                        objArr2[i5] = new Node(this.spec.keyFunction.apply(cast(obj3)).hashCode(), obj3, obj2);
                    }
                    i3++;
                }
            }
        }
        this.table = objArr2;
        this.nodeCount = i3;
        calcThresholds(i2);
    }

    private void calcThresholds(int i) {
        this.nodeThreshold = (int) (i * this.spec.nodeThresholdFactor);
    }

    private static class Node {
        final int hash;
        Object next;
        Object value;

        Node(int i, Object obj, Object obj2) {
            this.hash = i;
            this.value = obj;
            this.next = obj2;
        }
    }
}
