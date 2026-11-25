package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Pow2Util;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes10.dex */
public class IntIndex<E> {
    private static final int MAX_CAPACITY = 1073741824;
    private int nodeCount;
    private int nodeThreshold;
    private int size;
    private final Spec<E> spec;
    private Object[] table;

    /* JADX WARN: Multi-variable type inference failed */
    private E cast(Object obj) {
        return obj;
    }

    public static class Spec<E> {
        private static final int DEFAULT_MIN_CAPACITY = 16;
        private static final float DEFAULT_NODE_THRESHOLD_FACTOR = 0.25f;
        final ToIntFunction<? super E> keyFunction;
        final int minCapacity;
        final float nodeThresholdFactor;

        public Spec(ToIntFunction<? super E> toIntFunction) {
            this(toIntFunction, 16, DEFAULT_NODE_THRESHOLD_FACTOR);
        }

        public Spec(ToIntFunction<? super E> toIntFunction, int i) {
            this(toIntFunction, i, DEFAULT_NODE_THRESHOLD_FACTOR);
        }

        public Spec(ToIntFunction<? super E> toIntFunction, float f) {
            this(toIntFunction, 16, f);
        }

        public Spec(ToIntFunction<? super E> toIntFunction, int i, float f) {
            this.keyFunction = toIntFunction;
            this.minCapacity = i;
            this.nodeThresholdFactor = f;
        }
    }

    public IntIndex(Spec<E> spec) {
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
        int iApplyAsInt = this.spec.keyFunction.applyAsInt(e);
        int length = (objArr.length - 1) & iApplyAsInt;
        Object obj = objArr[length];
        if (obj == null) {
            objArr[length] = e;
            added();
            return null;
        }
        if (obj.getClass() != Node.class) {
            E eCast = cast(obj);
            int iApplyAsInt2 = this.spec.keyFunction.applyAsInt(eCast);
            if (iApplyAsInt2 == iApplyAsInt) {
                if (z) {
                    objArr[length] = e;
                }
                return eCast;
            }
            objArr[length] = new Node(iApplyAsInt2, obj, e);
            added();
            addedNode();
            return null;
        }
        while (true) {
            Node node = (Node) obj;
            if (node.key == iApplyAsInt) {
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
                int iApplyAsInt3 = this.spec.keyFunction.applyAsInt(eCast2);
                if (iApplyAsInt3 == iApplyAsInt) {
                    if (z) {
                        node.next = e;
                    }
                    return eCast2;
                }
                node.next = new Node(iApplyAsInt3, obj3, e);
                added();
                addedNode();
                return null;
            }
        }
    }

    public E get(int i) {
        Object[] objArr = this.table;
        Object obj = objArr[(objArr.length - 1) & i];
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == Node.class) {
            do {
                Node node = (Node) obj;
                if (node.key == i) {
                    return cast(node.value);
                }
                obj = node.next;
            } while (obj.getClass() == Node.class);
            E eCast = cast(obj);
            if (this.spec.keyFunction.applyAsInt(eCast) == i) {
                return eCast;
            }
            return null;
        }
        E eCast2 = cast(obj);
        if (this.spec.keyFunction.applyAsInt(eCast2) == i) {
            return eCast2;
        }
        return null;
    }

    public E remove(int i) {
        Object[] objArr = this.table;
        int length = (objArr.length - 1) & i;
        Object obj = objArr[length];
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == Node.class) {
            Node node = (Node) obj;
            if (node.key == i) {
                objArr[length] = node.next;
                removedNode();
                removed();
                return cast(node.value);
            }
            Object obj2 = node.next;
            if (obj2.getClass() != Node.class) {
                E eCast = cast(obj2);
                if (this.spec.keyFunction.applyAsInt(eCast) != i) {
                    return null;
                }
                objArr[length] = node.value;
                removedNode();
                removed();
                return eCast;
            }
            while (true) {
                Node node2 = (Node) obj2;
                if (node2.key == i) {
                    node.next = node2.next;
                    removedNode();
                    removed();
                    return cast(node2.value);
                }
                obj2 = node2.next;
                if (obj2.getClass() != Node.class) {
                    E eCast2 = cast(obj2);
                    if (this.spec.keyFunction.applyAsInt(eCast2) != i) {
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
            if (this.spec.keyFunction.applyAsInt(eCast3) != i) {
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

    /* JADX WARN: Code duplicated, block: B:42:0x0097  */
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
                            if ((node.key & i4) == i5) {
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
                        if ((this.spec.keyFunction.applyAsInt(eCast) & i4) == i5) {
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
                        objArr3[this.spec.keyFunction.applyAsInt(cast(obj2)) & i4] = obj2;
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
                        node.next = new Node(this.spec.keyFunction.applyAsInt(cast(obj)), obj, obj2);
                    } else {
                        objArr2[i5] = new Node(this.spec.keyFunction.applyAsInt(cast(obj3)), obj3, obj2);
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
        final int key;
        Object next;
        Object value;

        Node(int i, Object obj, Object obj2) {
            this.key = i;
            this.value = obj;
            this.next = obj2;
        }
    }
}
