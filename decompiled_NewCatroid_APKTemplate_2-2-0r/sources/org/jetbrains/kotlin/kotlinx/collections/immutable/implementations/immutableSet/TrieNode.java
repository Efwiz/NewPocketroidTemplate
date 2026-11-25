package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableSet;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.MutabilityOwnership;

/* JADX INFO: compiled from: TrieNode.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0014\b\u0000\u0018\u0000 R*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001RB\u001f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006¢\u0006\u0002\u0010\u0007B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ)\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u0004¢\u0006\u0002\u0010\u001cJ#\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001fJ\u001b\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001a\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010!J\u0015\u0010\"\u001a\u00020#2\u0006\u0010\u001a\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010$J\u001b\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001a\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010!J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010'\u001a\u00020\u0004H\u0002J#\u0010(\u001a\u00020#2\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u0004¢\u0006\u0002\u0010)J\u0015\u0010*\u001a\u00028\u00002\u0006\u0010+\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010,J\u0010\u0010-\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u0004H\u0002J\u0010\u0010.\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0004H\u0002JE\u0010/\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u00100\u001a\u00020\u00042\u0006\u00101\u001a\u00028\u00002\u0006\u00102\u001a\u00020\u00042\u0006\u00103\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u00042\b\u00104\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0002\u00105J=\u00106\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u00107\u001a\u00020\u00042\u0006\u00108\u001a\u00020\u00042\u0006\u00109\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u00042\b\u00104\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0002\u0010:J3\u0010;\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u00107\u001a\u00020\u00042\u0006\u00108\u001a\u00020\u00042\u0006\u00109\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010<J5\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u00042\n\u0010>\u001a\u0006\u0012\u0002\b\u00030?¢\u0006\u0002\u0010@J+\u0010A\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00028\u00002\u0006\u00104\u001a\u00020\tH\u0002¢\u0006\u0002\u0010BJ'\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001a\u001a\u00028\u00002\n\u0010>\u001a\u0006\u0012\u0002\b\u00030?H\u0002¢\u0006\u0002\u0010DJ'\u0010E\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001a\u001a\u00028\u00002\n\u0010>\u001a\u0006\u0012\u0002\b\u00030?H\u0002¢\u0006\u0002\u0010DJ\u001e\u0010F\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010'\u001a\u00020\u00042\u0006\u00104\u001a\u00020\tH\u0002J;\u0010G\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u00107\u001a\u00020\u00042\u0006\u00108\u001a\u00020\u00042\u0006\u00109\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u00104\u001a\u00020\tH\u0002¢\u0006\u0002\u0010:J5\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u00042\n\u0010>\u001a\u0006\u0012\u0002\b\u00030?¢\u0006\u0002\u0010@J&\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010J\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u00104\u001a\u00020\tH\u0002J,\u0010K\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010L\u001a\u00020\u00042\f\u0010M\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u00104\u001a\u00020\tH\u0002J\u0016\u0010N\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010+\u001a\u00020\u0004H\u0002J)\u0010O\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u0004¢\u0006\u0002\u0010\u001cJ\u001e\u0010P\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010J\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0004H\u0002J$\u0010Q\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010L\u001a\u00020\u00042\f\u0010M\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/TrieNode;", ExifInterface.LONGITUDE_EAST, "", "bitmap", "", "buffer", "", "(I[Ljava/lang/Object;)V", "ownedBy", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/internal/MutabilityOwnership;", "(I[Ljava/lang/Object;Lkotlinx/collections/immutable/internal/MutabilityOwnership;)V", "getBitmap", "()I", "setBitmap", "(I)V", "getBuffer", "()[Ljava/lang/Object;", "setBuffer", "([Ljava/lang/Object;)V", "[Ljava/lang/Object;", "getOwnedBy", "()Lkotlinx/collections/immutable/internal/MutabilityOwnership;", "setOwnedBy", "(Lkotlinx/collections/immutable/internal/MutabilityOwnership;)V", ProductAction.ACTION_ADD, "elementHash", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "shift", "(ILjava/lang/Object;I)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "addElementAt", "positionMask", "(ILjava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "collisionAdd", "(Ljava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "collisionContainsElement", "", "(Ljava/lang/Object;)Z", "collisionRemove", "collisionRemoveElementAtIndex", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "contains", "(ILjava/lang/Object;I)Z", "elementAtIndex", "index", "(I)Ljava/lang/Object;", "hasNoCellAt", "indexOfCellAt", "makeNode", "elementHash1", "element1", "elementHash2", "element2", "owner", "(ILjava/lang/Object;ILjava/lang/Object;ILkotlinx/collections/immutable/internal/MutabilityOwnership;)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "makeNodeAtIndex", "elementIndex", "newElementHash", "newElement", "(IILjava/lang/Object;ILkotlinx/collections/immutable/internal/MutabilityOwnership;)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "moveElementToNode", "(IILjava/lang/Object;I)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "mutableAdd", "mutator", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/PersistentHashSetBuilder;", "(ILjava/lang/Object;ILkotlinx/collections/immutable/implementations/immutableSet/PersistentHashSetBuilder;)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "mutableAddElementAt", "(ILjava/lang/Object;Lkotlinx/collections/immutable/internal/MutabilityOwnership;)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "mutableCollisionAdd", "(Ljava/lang/Object;Lkotlinx/collections/immutable/implementations/immutableSet/PersistentHashSetBuilder;)Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "mutableCollisionRemove", "mutableCollisionRemoveElementAtIndex", "mutableMoveElementToNode", "mutableRemove", "mutableRemoveCellAtIndex", "cellIndex", "mutableUpdateNodeAtIndex", "nodeIndex", "newNode", "nodeAtIndex", ProductAction.ACTION_REMOVE, "removeCellAtIndex", "updateNodeAtIndex", "Companion", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class TrieNode<E> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final TrieNode EMPTY = new TrieNode(0, new Object[0]);
    private int bitmap;
    private Object[] buffer;
    private MutabilityOwnership ownedBy;

    public TrieNode(int i, Object[] buffer, MutabilityOwnership mutabilityOwnership) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        this.bitmap = i;
        this.buffer = buffer;
        this.ownedBy = mutabilityOwnership;
    }

    public final int getBitmap() {
        return this.bitmap;
    }

    public final Object[] getBuffer() {
        return this.buffer;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TrieNode(int i, Object[] buffer) {
        this(i, buffer, null);
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
    }

    private final boolean hasNoCellAt(int positionMask) {
        return (positionMask & this.bitmap) == 0;
    }

    private final int indexOfCellAt(int positionMask) {
        return Integer.bitCount((positionMask - 1) & this.bitmap);
    }

    private final E elementAtIndex(int index) {
        return (E) this.buffer[index];
    }

    private final TrieNode<E> nodeAtIndex(int index) {
        Object obj = this.buffer[index];
        if (obj != null) {
            return (TrieNode) obj;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.collections.immutable.implementations.immutableSet.TrieNode<E>");
    }

    private final TrieNode<E> addElementAt(int i, E e) {
        return new TrieNode<>(i | this.bitmap, TrieNodeKt.addElementAtIndex(this.buffer, indexOfCellAt(i), e));
    }

    private final TrieNode<E> mutableAddElementAt(int i, E e, MutabilityOwnership mutabilityOwnership) {
        int iIndexOfCellAt = indexOfCellAt(i);
        if (this.ownedBy == mutabilityOwnership) {
            this.buffer = TrieNodeKt.addElementAtIndex(this.buffer, iIndexOfCellAt, e);
            this.bitmap = i | this.bitmap;
            return this;
        }
        return new TrieNode<>(i | this.bitmap, TrieNodeKt.addElementAtIndex(this.buffer, iIndexOfCellAt, e), mutabilityOwnership);
    }

    private final TrieNode<E> updateNodeAtIndex(int nodeIndex, TrieNode<E> newNode) {
        Object[] objArr = newNode.buffer;
        if (objArr.length == 1) {
            Object obj = objArr[0];
            if (!(obj instanceof TrieNode)) {
                if (this.buffer.length == 1) {
                    newNode.bitmap = this.bitmap;
                    return newNode;
                }
                newNode = (TrieNode<E>) obj;
            }
        }
        Object[] objArr2 = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr2, objArr2.length);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        objArrCopyOf[nodeIndex] = newNode;
        return new TrieNode<>(this.bitmap, objArrCopyOf);
    }

    private final TrieNode<E> mutableUpdateNodeAtIndex(int nodeIndex, TrieNode<E> newNode, MutabilityOwnership owner) {
        Object[] objArr = newNode.buffer;
        if (objArr.length == 1) {
            Object obj = objArr[0];
            if (!(obj instanceof TrieNode)) {
                if (this.buffer.length == 1) {
                    newNode.bitmap = this.bitmap;
                    return newNode;
                }
                newNode = (TrieNode<E>) obj;
            }
        }
        if (this.ownedBy == owner) {
            this.buffer[nodeIndex] = newNode;
            return this;
        }
        Object[] objArr2 = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr2, objArr2.length);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        objArrCopyOf[nodeIndex] = newNode;
        return new TrieNode<>(this.bitmap, objArrCopyOf, owner);
    }

    private final TrieNode<E> makeNodeAtIndex(int i, int i2, E e, int i3, MutabilityOwnership mutabilityOwnership) {
        E eElementAtIndex = elementAtIndex(i);
        return makeNode(eElementAtIndex != null ? eElementAtIndex.hashCode() : 0, eElementAtIndex, i2, e, i3 + 5, mutabilityOwnership);
    }

    private final TrieNode<E> moveElementToNode(int i, int i2, E e, int i3) {
        Object[] objArr = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        objArrCopyOf[i] = makeNodeAtIndex(i, i2, e, i3, null);
        return new TrieNode<>(this.bitmap, objArrCopyOf);
    }

    private final TrieNode<E> mutableMoveElementToNode(int i, int i2, E e, int i3, MutabilityOwnership mutabilityOwnership) {
        if (this.ownedBy == mutabilityOwnership) {
            this.buffer[i] = makeNodeAtIndex(i, i2, e, i3, mutabilityOwnership);
            return this;
        }
        Object[] objArr = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        objArrCopyOf[i] = makeNodeAtIndex(i, i2, e, i3, mutabilityOwnership);
        return new TrieNode<>(this.bitmap, objArrCopyOf, mutabilityOwnership);
    }

    private final TrieNode<E> makeNode(int i, E e, int i2, E e2, int i3, MutabilityOwnership mutabilityOwnership) {
        Object[] objArr;
        if (i3 > 30) {
            return new TrieNode<>(0, new Object[]{e, e2}, mutabilityOwnership);
        }
        int iIndexSegment = TrieNodeKt.indexSegment(i, i3);
        int iIndexSegment2 = TrieNodeKt.indexSegment(i2, i3);
        if (iIndexSegment != iIndexSegment2) {
            if (iIndexSegment < iIndexSegment2) {
                objArr = new Object[]{e, e2};
            } else {
                objArr = new Object[]{e2, e};
            }
            return new TrieNode<>((1 << iIndexSegment) | (1 << iIndexSegment2), objArr, mutabilityOwnership);
        }
        return new TrieNode<>(1 << iIndexSegment, new Object[]{makeNode(i, e, i2, e2, i3 + 5, mutabilityOwnership)}, mutabilityOwnership);
    }

    private final TrieNode<E> removeCellAtIndex(int cellIndex, int positionMask) {
        return new TrieNode<>(positionMask ^ this.bitmap, TrieNodeKt.removeCellAtIndex(this.buffer, cellIndex));
    }

    private final TrieNode<E> mutableRemoveCellAtIndex(int cellIndex, int positionMask, MutabilityOwnership owner) {
        if (this.ownedBy == owner) {
            this.buffer = TrieNodeKt.removeCellAtIndex(this.buffer, cellIndex);
            this.bitmap ^= positionMask;
            return this;
        }
        return new TrieNode<>(positionMask ^ this.bitmap, TrieNodeKt.removeCellAtIndex(this.buffer, cellIndex), owner);
    }

    private final TrieNode<E> collisionRemoveElementAtIndex(int i) {
        return new TrieNode<>(0, TrieNodeKt.removeCellAtIndex(this.buffer, i));
    }

    private final TrieNode<E> mutableCollisionRemoveElementAtIndex(int i, MutabilityOwnership owner) {
        if (this.ownedBy == owner) {
            this.buffer = TrieNodeKt.removeCellAtIndex(this.buffer, i);
            return this;
        }
        return new TrieNode<>(0, TrieNodeKt.removeCellAtIndex(this.buffer, i), owner);
    }

    private final boolean collisionContainsElement(E element) {
        return ArraysKt.contains((E[]) this.buffer, element);
    }

    private final TrieNode<E> collisionAdd(E e) {
        return collisionContainsElement(e) ? this : new TrieNode<>(0, TrieNodeKt.addElementAtIndex(this.buffer, 0, e));
    }

    private final TrieNode<E> mutableCollisionAdd(E e, PersistentHashSetBuilder<?> persistentHashSetBuilder) {
        if (collisionContainsElement(e)) {
            return this;
        }
        persistentHashSetBuilder.setSize(persistentHashSetBuilder.size() + 1);
        if (this.ownedBy == persistentHashSetBuilder.getOwnership()) {
            this.buffer = TrieNodeKt.addElementAtIndex(this.buffer, 0, e);
            return this;
        }
        return new TrieNode<>(0, TrieNodeKt.addElementAtIndex(this.buffer, 0, e), persistentHashSetBuilder.getOwnership());
    }

    private final TrieNode<E> collisionRemove(E e) {
        int iIndexOf = ArraysKt.indexOf((E[]) this.buffer, e);
        return iIndexOf != -1 ? collisionRemoveElementAtIndex(iIndexOf) : this;
    }

    private final TrieNode<E> mutableCollisionRemove(E e, PersistentHashSetBuilder<?> persistentHashSetBuilder) {
        int iIndexOf = ArraysKt.indexOf((E[]) this.buffer, e);
        if (iIndexOf == -1) {
            return this;
        }
        persistentHashSetBuilder.setSize(persistentHashSetBuilder.size() - 1);
        return mutableCollisionRemoveElementAtIndex(iIndexOf, persistentHashSetBuilder.getOwnership());
    }

    public final boolean contains(int elementHash, E element, int shift) {
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(elementHash, shift);
        if (hasNoCellAt(iIndexSegment)) {
            return false;
        }
        int iIndexOfCellAt = indexOfCellAt(iIndexSegment);
        Object obj = this.buffer[iIndexOfCellAt];
        if (obj instanceof TrieNode) {
            TrieNode<E> trieNodeNodeAtIndex = nodeAtIndex(iIndexOfCellAt);
            if (shift == 30) {
                return trieNodeNodeAtIndex.collisionContainsElement(element);
            }
            return trieNodeNodeAtIndex.contains(elementHash, element, shift + 5);
        }
        return Intrinsics.areEqual(element, obj);
    }

    public final TrieNode<E> add(int i, E e, int i2) {
        TrieNode<E> trieNodeAdd;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasNoCellAt(iIndexSegment)) {
            return addElementAt(iIndexSegment, e);
        }
        int iIndexOfCellAt = indexOfCellAt(iIndexSegment);
        Object obj = this.buffer[iIndexOfCellAt];
        if (!(obj instanceof TrieNode)) {
            return Intrinsics.areEqual(e, obj) ? this : moveElementToNode(iIndexOfCellAt, i, e, i2);
        }
        TrieNode<E> trieNodeNodeAtIndex = nodeAtIndex(iIndexOfCellAt);
        if (i2 == 30) {
            trieNodeAdd = trieNodeNodeAtIndex.collisionAdd(e);
        } else {
            trieNodeAdd = trieNodeNodeAtIndex.add(i, e, i2 + 5);
        }
        return trieNodeNodeAtIndex == trieNodeAdd ? this : updateNodeAtIndex(iIndexOfCellAt, trieNodeAdd);
    }

    public final TrieNode<E> mutableAdd(int i, E e, int i2, PersistentHashSetBuilder<?> mutator) {
        TrieNode<E> trieNodeMutableAdd;
        Intrinsics.checkParameterIsNotNull(mutator, "mutator");
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasNoCellAt(iIndexSegment)) {
            mutator.setSize(mutator.size() + 1);
            return mutableAddElementAt(iIndexSegment, e, mutator.getOwnership());
        }
        int iIndexOfCellAt = indexOfCellAt(iIndexSegment);
        Object obj = this.buffer[iIndexOfCellAt];
        if (obj instanceof TrieNode) {
            TrieNode<E> trieNodeNodeAtIndex = nodeAtIndex(iIndexOfCellAt);
            if (i2 == 30) {
                trieNodeMutableAdd = trieNodeNodeAtIndex.mutableCollisionAdd(e, mutator);
            } else {
                trieNodeMutableAdd = trieNodeNodeAtIndex.mutableAdd(i, e, i2 + 5, mutator);
            }
            return trieNodeNodeAtIndex == trieNodeMutableAdd ? this : mutableUpdateNodeAtIndex(iIndexOfCellAt, trieNodeMutableAdd, mutator.getOwnership());
        }
        if (Intrinsics.areEqual(e, obj)) {
            return this;
        }
        mutator.setSize(mutator.size() + 1);
        return mutableMoveElementToNode(iIndexOfCellAt, i, e, i2, mutator.getOwnership());
    }

    public final TrieNode<E> remove(int i, E e, int i2) {
        TrieNode<E> trieNodeRemove;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasNoCellAt(iIndexSegment)) {
            return this;
        }
        int iIndexOfCellAt = indexOfCellAt(iIndexSegment);
        Object obj = this.buffer[iIndexOfCellAt];
        if (!(obj instanceof TrieNode)) {
            return Intrinsics.areEqual(e, obj) ? removeCellAtIndex(iIndexOfCellAt, iIndexSegment) : this;
        }
        TrieNode<E> trieNodeNodeAtIndex = nodeAtIndex(iIndexOfCellAt);
        if (i2 == 30) {
            trieNodeRemove = trieNodeNodeAtIndex.collisionRemove(e);
        } else {
            trieNodeRemove = trieNodeNodeAtIndex.remove(i, e, i2 + 5);
        }
        return trieNodeNodeAtIndex == trieNodeRemove ? this : updateNodeAtIndex(iIndexOfCellAt, trieNodeRemove);
    }

    public final TrieNode<E> mutableRemove(int i, E e, int i2, PersistentHashSetBuilder<?> mutator) {
        TrieNode<E> trieNodeMutableRemove;
        Intrinsics.checkParameterIsNotNull(mutator, "mutator");
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasNoCellAt(iIndexSegment)) {
            return this;
        }
        int iIndexOfCellAt = indexOfCellAt(iIndexSegment);
        Object obj = this.buffer[iIndexOfCellAt];
        if (obj instanceof TrieNode) {
            TrieNode<E> trieNodeNodeAtIndex = nodeAtIndex(iIndexOfCellAt);
            if (i2 == 30) {
                trieNodeMutableRemove = trieNodeNodeAtIndex.mutableCollisionRemove(e, mutator);
            } else {
                trieNodeMutableRemove = trieNodeNodeAtIndex.mutableRemove(i, e, i2 + 5, mutator);
            }
            return (this.ownedBy == mutator.getOwnership() || trieNodeNodeAtIndex != trieNodeMutableRemove) ? mutableUpdateNodeAtIndex(iIndexOfCellAt, trieNodeMutableRemove, mutator.getOwnership()) : this;
        }
        if (!Intrinsics.areEqual(e, obj)) {
            return this;
        }
        mutator.setSize(mutator.size() - 1);
        return mutableRemoveCellAtIndex(iIndexOfCellAt, iIndexSegment, mutator.getOwnership());
    }

    /* JADX INFO: compiled from: TrieNode.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/TrieNode$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "", "getEMPTY$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final TrieNode getEMPTY$kotlinx_collections_immutable() {
            return TrieNode.EMPTY;
        }
    }
}
