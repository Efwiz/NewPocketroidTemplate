package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedSet;

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
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: PersistentOrderedSetMutableIterator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\u000e\u0010\u0010\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u000eH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSetMutableIterator;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSetIterator;", "", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSetBuilder;", "(Lkotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSetBuilder;)V", "expectedModCount", "", "lastIteratedElement", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "nextWasInvoked", "", "checkForComodification", "", "checkNextWasInvoked", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", ProductAction.ACTION_REMOVE, "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentOrderedSetMutableIterator<E> extends PersistentOrderedSetIterator<E> implements Iterator<E>, KMutableIterator {
    private final PersistentOrderedSetBuilder<E> builder;
    private int expectedModCount;
    private E lastIteratedElement;
    private boolean nextWasInvoked;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PersistentOrderedSetMutableIterator(PersistentOrderedSetBuilder<E> builder) {
        super(builder.getFirstElement(), builder.getHashMapBuilder$kotlinx_collections_immutable());
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        this.builder = builder;
        this.expectedModCount = builder.getHashMapBuilder$kotlinx_collections_immutable().getModCount();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedSet.PersistentOrderedSetIterator, java.util.Iterator
    public E next() {
        checkForComodification();
        E e = (E) super.next();
        this.lastIteratedElement = e;
        this.nextWasInvoked = true;
        return e;
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedSet.PersistentOrderedSetIterator, java.util.Iterator
    public void remove() {
        checkNextWasInvoked();
        PersistentOrderedSetBuilder<E> persistentOrderedSetBuilder = this.builder;
        E e = this.lastIteratedElement;
        if (persistentOrderedSetBuilder == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        }
        TypeIntrinsics.asMutableCollection(persistentOrderedSetBuilder).remove(e);
        this.lastIteratedElement = null;
        this.nextWasInvoked = false;
        this.expectedModCount = this.builder.getHashMapBuilder$kotlinx_collections_immutable().getModCount();
        setIndex$kotlinx_collections_immutable(getIndex() - 1);
    }

    private final void checkNextWasInvoked() {
        if (!this.nextWasInvoked) {
            throw new IllegalStateException();
        }
    }

    private final void checkForComodification() {
        if (this.builder.getHashMapBuilder$kotlinx_collections_immutable().getModCount() != this.expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
}
