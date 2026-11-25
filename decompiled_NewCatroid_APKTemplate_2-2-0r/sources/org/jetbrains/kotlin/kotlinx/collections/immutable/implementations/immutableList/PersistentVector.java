package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableList;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Arrays;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.CommonFunctionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.ListImplementation;

/* JADX INFO: compiled from: PersistentVector.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010*\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\t\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B5\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u001b\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0011J#\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u001d\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010\u0012\u001a\u00020\tH\u0002¢\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017H\u0016J\u0016\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00020\tH\u0096\u0002¢\u0006\u0002\u0010\u0019JG\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001c\u001a\u00020\u001dH\u0002¢\u0006\u0002\u0010\u001eJ5\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010 \u001a\u00020\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0002\u0010!J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#2\u0006\u0010\u0012\u001a\u00020\tH\u0016J?\u0010$\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u00052\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010%\u001a\u00020\u001dH\u0002¢\u0006\u0002\u0010&J3\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010(\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tH\u0002¢\u0006\u0002\u0010)JC\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u000e\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u000e\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005H\u0002¢\u0006\u0002\u0010-J?\u0010.\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0010\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u00052\u0006\u0010\u001b\u001a\u00020\t2\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005H\u0002¢\u0006\u0002\u0010/J\"\u00100\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020302H\u0016J\u0016\u00104\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0012\u001a\u00020\tH\u0016J=\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010%\u001a\u00020\u001dH\u0002¢\u0006\u0002\u0010&J;\u00106\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010(\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\tH\u0002¢\u0006\u0002\u00107J\b\u0010(\u001a\u00020\tH\u0002J#\u00108\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J?\u00109\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\t2\b\u0010:\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0002\u0010;R\u0018\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\f¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableList/PersistentVector;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableList/AbstractPersistentList;", "root", "", "", "tail", "size", "", "rootShift", "([Ljava/lang/Object;[Ljava/lang/Object;II)V", "[Ljava/lang/Object;", "getSize", "()I", ProductAction.ACTION_ADD, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentList;", "index", "(ILjava/lang/Object;)Lkotlinx/collections/immutable/PersistentList;", "bufferFor", "(I)[Ljava/lang/Object;", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableList/PersistentVectorBuilder;", "get", "(I)Ljava/lang/Object;", "insertIntoRoot", "shift", "elementCarry", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableList/ObjectRef;", "([Ljava/lang/Object;IILjava/lang/Object;Lkotlinx/collections/immutable/implementations/immutableList/ObjectRef;)[Ljava/lang/Object;", "insertIntoTail", "tailIndex", "([Ljava/lang/Object;ILjava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableList/PersistentVector;", "listIterator", "", "pullLastBuffer", "tailCarry", "([Ljava/lang/Object;IILkotlinx/collections/immutable/implementations/immutableList/ObjectRef;)[Ljava/lang/Object;", "pullLastBufferFromRoot", "rootSize", "([Ljava/lang/Object;II)Lkotlinx/collections/immutable/PersistentList;", "pushFilledTail", "filledTail", "newTail", "([Ljava/lang/Object;[Ljava/lang/Object;[Ljava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableList/PersistentVector;", "pushTail", "([Ljava/lang/Object;I[Ljava/lang/Object;)[Ljava/lang/Object;", "removeAll", "predicate", "Lkotlin/Function1;", "", "removeAt", "removeFromRootAt", "removeFromTailAt", "([Ljava/lang/Object;III)Lkotlinx/collections/immutable/PersistentList;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "setInRoot", "e", "([Ljava/lang/Object;IILjava/lang/Object;)[Ljava/lang/Object;", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentVector<E> extends AbstractPersistentList<E> implements PersistentList<E> {
    private final Object[] root;
    private final int rootShift;
    private final int size;
    private final Object[] tail;

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this.size;
    }

    public PersistentVector(Object[] root, Object[] tail, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(root, "root");
        Intrinsics.checkParameterIsNotNull(tail, "tail");
        this.root = root;
        this.tail = tail;
        this.size = i;
        this.rootShift = i2;
        if (!(size() > 32)) {
            throw new IllegalArgumentException(("Trie-based persistent vector should have at least 33 elements, got " + size()).toString());
        }
        CommonFunctionsKt.m7306assert(size() - UtilsKt.rootSize(size()) <= RangesKt.coerceAtMost(tail.length, 32));
    }

    private final int rootSize() {
        return UtilsKt.rootSize(size());
    }

    @Override // java.util.Collection, java.util.List, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList
    public PersistentList<E> add(E e) {
        int size = size() - rootSize();
        if (size < 32) {
            Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
            Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
            objArrCopyOf[size] = e;
            return new PersistentVector(this.root, objArrCopyOf, size() + 1, this.rootShift);
        }
        return pushFilledTail(this.root, this.tail, UtilsKt.presizedBufferWith(e));
    }

    private final PersistentVector<E> pushFilledTail(Object[] objArr, Object[] objArr2, Object[] objArr3) {
        int size = size() >> 5;
        int i = this.rootShift;
        if (size > (1 << i)) {
            Object[] objArrPresizedBufferWith = UtilsKt.presizedBufferWith(objArr);
            int i2 = this.rootShift + 5;
            return new PersistentVector<>(pushTail(objArrPresizedBufferWith, i2, objArr2), objArr3, size() + 1, i2);
        }
        return new PersistentVector<>(pushTail(objArr, i, objArr2), objArr3, size() + 1, this.rootShift);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    private final Object[] pushTail(Object[] root, int shift, Object[] tail) {
        Object[] objArrCopyOf;
        int iIndexSegment = UtilsKt.indexSegment(size() - 1, shift);
        if (root != null) {
            objArrCopyOf = Arrays.copyOf(root, 32);
            Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
            if (objArrCopyOf == null) {
                objArrCopyOf = new Object[32];
            }
        } else {
            objArrCopyOf = new Object[32];
        }
        if (shift == 5) {
            objArrCopyOf[iIndexSegment] = tail;
        } else {
            objArrCopyOf[iIndexSegment] = pushTail((Object[]) objArrCopyOf[iIndexSegment], shift - 5, tail);
        }
        return objArrCopyOf;
    }

    @Override // java.util.List, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList
    public PersistentList<E> add(int i, E e) {
        ListImplementation.checkPositionIndex$kotlinx_collections_immutable(i, size());
        if (i == size()) {
            return add((Object) e);
        }
        int iRootSize = rootSize();
        if (i >= iRootSize) {
            return insertIntoTail(this.root, i - iRootSize, e);
        }
        ObjectRef objectRef = new ObjectRef(null);
        return insertIntoTail(insertIntoRoot(this.root, this.rootShift, i, e, objectRef), 0, objectRef.getValue());
    }

    private final PersistentVector<E> insertIntoTail(Object[] objArr, int i, Object obj) {
        int size = size() - rootSize();
        Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        if (size < 32) {
            ArraysKt.copyInto(this.tail, objArrCopyOf, i + 1, i, size);
            objArrCopyOf[i] = obj;
            return new PersistentVector<>(objArr, objArrCopyOf, size() + 1, this.rootShift);
        }
        Object[] objArr2 = this.tail;
        Object obj2 = objArr2[31];
        ArraysKt.copyInto(objArr2, objArrCopyOf, i + 1, i, size - 1);
        objArrCopyOf[i] = obj;
        return pushFilledTail(objArr, objArrCopyOf, UtilsKt.presizedBufferWith(obj2));
    }

    private final Object[] insertIntoRoot(Object[] objArr, int i, int i2, Object obj, ObjectRef objectRef) {
        Object[] objArrCopyOf;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            if (iIndexSegment != 0) {
                objArrCopyOf = Arrays.copyOf(objArr, 32);
                Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
            } else {
                objArrCopyOf = new Object[32];
            }
            ArraysKt.copyInto(objArr, objArrCopyOf, iIndexSegment + 1, iIndexSegment, 31);
            objectRef.setValue(objArr[31]);
            objArrCopyOf[iIndexSegment] = obj;
            return objArrCopyOf;
        }
        Object[] objArrCopyOf2 = Arrays.copyOf(objArr, 32);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf2, "java.util.Arrays.copyOf(this, newSize)");
        int i3 = i - 5;
        Object obj2 = objArr[iIndexSegment];
        String str = "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>";
        if (obj2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        objArrCopyOf2[iIndexSegment] = insertIntoRoot((Object[]) obj2, i3, i2, obj, objectRef);
        int i4 = iIndexSegment + 1;
        while (i4 < 32 && objArrCopyOf2[i4] != null) {
            Object obj3 = objArr[i4];
            if (obj3 == null) {
                throw new TypeCastException(str);
            }
            Object[] objArr2 = objArrCopyOf2;
            objArr2[i4] = insertIntoRoot((Object[]) obj3, i3, 0, objectRef.getValue(), objectRef);
            i4++;
            objArrCopyOf2 = objArr2;
            str = str;
        }
        return objArrCopyOf2;
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList
    public PersistentList<E> removeAt(int index) {
        ListImplementation.checkElementIndex$kotlinx_collections_immutable(index, size());
        int iRootSize = rootSize();
        if (index >= iRootSize) {
            return removeFromTailAt(this.root, iRootSize, this.rootShift, index - iRootSize);
        }
        return removeFromTailAt(removeFromRootAt(this.root, this.rootShift, index, new ObjectRef(this.tail[0])), iRootSize, this.rootShift, 0);
    }

    private final PersistentList<E> removeFromTailAt(Object[] objArr, int i, int i2, int i3) {
        int size = size() - i;
        CommonFunctionsKt.m7306assert(i3 < size);
        if (size == 1) {
            return pullLastBufferFromRoot(objArr, i, i2);
        }
        Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        int i4 = size - 1;
        if (i3 < i4) {
            ArraysKt.copyInto(this.tail, objArrCopyOf, i3, i3 + 1, size);
        }
        objArrCopyOf[i4] = null;
        return new PersistentVector(objArr, objArrCopyOf, (i + size) - 1, i2);
    }

    private final PersistentList<E> pullLastBufferFromRoot(Object[] objArr, int i, int i2) {
        if (i2 == 0) {
            return new SmallPersistentVector(objArr);
        }
        ObjectRef objectRef = new ObjectRef(null);
        Object[] objArrPullLastBuffer = pullLastBuffer(objArr, i2, i - 1, objectRef);
        if (objArrPullLastBuffer == null) {
            Intrinsics.throwNpe();
        }
        Object value = objectRef.getValue();
        if (value == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        Object[] objArr2 = (Object[]) value;
        if (objArrPullLastBuffer[1] == null) {
            Object obj = objArrPullLastBuffer[0];
            if (obj == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
            }
            return new PersistentVector((Object[]) obj, objArr2, i, i2 - 5);
        }
        return new PersistentVector(objArrPullLastBuffer, objArr2, i, i2);
    }

    private final Object[] pullLastBuffer(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        Object[] objArrPullLastBuffer;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 5) {
            objectRef.setValue(objArr[iIndexSegment]);
            objArrPullLastBuffer = null;
        } else {
            Object obj = objArr[iIndexSegment];
            if (obj == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
            }
            objArrPullLastBuffer = pullLastBuffer((Object[]) obj, i - 5, i2, objectRef);
        }
        if (objArrPullLastBuffer == null && iIndexSegment == 0) {
            return null;
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, 32);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        objArrCopyOf[iIndexSegment] = objArrPullLastBuffer;
        return objArrCopyOf;
    }

    private final Object[] removeFromRootAt(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        Object[] objArrCopyOf;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            if (iIndexSegment != 0) {
                objArrCopyOf = Arrays.copyOf(objArr, 32);
                Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
            } else {
                objArrCopyOf = new Object[32];
            }
            ArraysKt.copyInto(objArr, objArrCopyOf, iIndexSegment, iIndexSegment + 1, 32);
            objArrCopyOf[31] = objectRef.getValue();
            objectRef.setValue(objArr[iIndexSegment]);
            return objArrCopyOf;
        }
        int iIndexSegment2 = objArr[31] == null ? UtilsKt.indexSegment(rootSize() - 1, i) : 31;
        Object[] objArrCopyOf2 = Arrays.copyOf(objArr, 32);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf2, "java.util.Arrays.copyOf(this, newSize)");
        int i3 = i - 5;
        int i4 = iIndexSegment + 1;
        if (iIndexSegment2 >= i4) {
            while (true) {
                Object obj = objArrCopyOf2[iIndexSegment2];
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                }
                objArrCopyOf2[iIndexSegment2] = removeFromRootAt((Object[]) obj, i3, 0, objectRef);
                if (iIndexSegment2 == i4) {
                    break;
                }
                iIndexSegment2--;
            }
        }
        Object obj2 = objArrCopyOf2[iIndexSegment];
        if (obj2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        objArrCopyOf2[iIndexSegment] = removeFromRootAt((Object[]) obj2, i3, i2, objectRef);
        return objArrCopyOf2;
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList
    public PersistentList<E> removeAll(Function1<? super E, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        PersistentVectorBuilder<E> persistentVectorBuilderBuilder = builder();
        persistentVectorBuilderBuilder.removeAllWithPredicate(predicate);
        return persistentVectorBuilderBuilder.build();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList
    public PersistentVectorBuilder<E> builder() {
        return new PersistentVectorBuilder<>(this, this.root, this.tail, this.rootShift);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ListIterator<E> listIterator(int index) {
        ListImplementation.checkPositionIndex$kotlinx_collections_immutable(index, size());
        Object[] objArr = this.root;
        Object[] objArr2 = this.tail;
        if (objArr2 != null) {
            return new PersistentVectorIterator(objArr, objArr2, index, size(), (this.rootShift / 5) + 1);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<E>");
    }

    private final Object[] bufferFor(int index) {
        if (rootSize() <= index) {
            return this.tail;
        }
        Object[] objArr = this.root;
        for (int i = this.rootShift; i > 0; i -= 5) {
            Object[] objArr2 = objArr[UtilsKt.indexSegment(index, i)];
            if (objArr2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
            }
            objArr = objArr2;
        }
        return objArr;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public E get(int index) {
        ListImplementation.checkElementIndex$kotlinx_collections_immutable(index, size());
        return (E) bufferFor(index)[index & 31];
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public PersistentList<E> set(int i, E e) {
        ListImplementation.checkElementIndex$kotlinx_collections_immutable(i, size());
        if (rootSize() <= i) {
            Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
            Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
            objArrCopyOf[i & 31] = e;
            return new PersistentVector(this.root, objArrCopyOf, size(), this.rootShift);
        }
        return new PersistentVector(setInRoot(this.root, this.rootShift, i, e), this.tail, size(), this.rootShift);
    }

    private final Object[] setInRoot(Object[] root, int shift, int index, Object e) {
        int iIndexSegment = UtilsKt.indexSegment(index, shift);
        Object[] objArrCopyOf = Arrays.copyOf(root, 32);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        if (shift == 0) {
            objArrCopyOf[iIndexSegment] = e;
        } else {
            Object obj = objArrCopyOf[iIndexSegment];
            if (obj == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
            }
            objArrCopyOf[iIndexSegment] = setInRoot((Object[]) obj, shift - 5, index, e);
        }
        return objArrCopyOf;
    }
}
