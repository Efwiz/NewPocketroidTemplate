package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: PersistentHashMapBuilderContentIterators.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010)\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b \u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00030\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005B9\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u001e\u0010\b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u000e\u0010\u0015\u001a\u00028\u0002H\u0096\u0002¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016J5\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\r2\u000e\u0010\u001a\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001b2\u0006\u0010\u001c\u001a\u00028\u00002\u0006\u0010\u001d\u001a\u00020\rH\u0002¢\u0006\u0002\u0010\u001eR\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000e\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilderBaseIterator;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBaseIterator;", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", "path", "", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNodeBaseIterator;", "(Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;[Lkotlinx/collections/immutable/implementations/immutableMap/TrieNodeBaseIterator;)V", "expectedModCount", "", "lastIteratedKey", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "nextWasInvoked", "", "checkForComodification", "", "checkNextWasInvoked", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", ProductAction.ACTION_REMOVE, "resetPath", "keyHash", "node", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode;", ASTExpr.DEFAULT_MAP_KEY_NAME, "pathIndex", "(ILkotlinx/collections/immutable/implementations/immutableMap/TrieNode;Ljava/lang/Object;I)V", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public abstract class PersistentHashMapBuilderBaseIterator<K, V, T> extends PersistentHashMapBaseIterator<K, V, T> implements Iterator<T>, KMutableIterator {
    private final PersistentHashMapBuilder<K, V> builder;
    private int expectedModCount;
    private K lastIteratedKey;
    private boolean nextWasInvoked;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PersistentHashMapBuilderBaseIterator(PersistentHashMapBuilder<K, V> builder, TrieNodeBaseIterator<K, V, T>[] path) {
        super(builder.getNode$kotlinx_collections_immutable(), path);
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(path, "path");
        this.builder = builder;
        this.expectedModCount = builder.getModCount();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBaseIterator, java.util.Iterator
    public T next() {
        checkForComodification();
        this.lastIteratedKey = currentKey();
        this.nextWasInvoked = true;
        return (T) super.next();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBaseIterator, java.util.Iterator
    public void remove() {
        checkNextWasInvoked();
        if (getHasNext()) {
            K kCurrentKey = currentKey();
            PersistentHashMapBuilder<K, V> persistentHashMapBuilder = this.builder;
            K k = this.lastIteratedKey;
            if (persistentHashMapBuilder == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
            }
            TypeIntrinsics.asMutableMap(persistentHashMapBuilder).remove(k);
            resetPath(kCurrentKey != null ? kCurrentKey.hashCode() : 0, this.builder.getNode$kotlinx_collections_immutable(), kCurrentKey, 0);
        } else {
            PersistentHashMapBuilder<K, V> persistentHashMapBuilder2 = this.builder;
            K k2 = this.lastIteratedKey;
            if (persistentHashMapBuilder2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
            }
            TypeIntrinsics.asMutableMap(persistentHashMapBuilder2).remove(k2);
        }
        this.lastIteratedKey = null;
        this.nextWasInvoked = false;
        this.expectedModCount = this.builder.getModCount();
    }

    private final void resetPath(int i, TrieNode<?, ?> trieNode, K k, int i2) {
        int i3 = i2 * 5;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i3);
        if (trieNode.hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            getPath()[i2].reset(trieNode.getBuffer(), trieNode.entryCount$kotlinx_collections_immutable() * 2, trieNode.entryKeyIndex$kotlinx_collections_immutable(iIndexSegment));
            return;
        }
        int iNodeIndex$kotlinx_collections_immutable = trieNode.nodeIndex$kotlinx_collections_immutable(iIndexSegment);
        TrieNode<?, ?> trieNodeNodeAtIndex$kotlinx_collections_immutable = trieNode.nodeAtIndex$kotlinx_collections_immutable(iNodeIndex$kotlinx_collections_immutable);
        if (i3 == 30) {
            getPath()[i2].reset(trieNode.getBuffer(), trieNode.getBuffer().length, 0);
            while (!Intrinsics.areEqual(getPath()[i2].currentKey(), k)) {
                getPath()[i2].moveToNextKey();
            }
        } else {
            getPath()[i2].reset(trieNode.getBuffer(), trieNode.entryCount$kotlinx_collections_immutable() * 2, iNodeIndex$kotlinx_collections_immutable);
            resetPath(i, trieNodeNodeAtIndex$kotlinx_collections_immutable, k, i2 + 1);
        }
    }

    private final void checkNextWasInvoked() {
        if (!this.nextWasInvoked) {
            throw new IllegalStateException();
        }
    }

    private final void checkForComodification() {
        if (this.builder.getModCount() != this.expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
}
