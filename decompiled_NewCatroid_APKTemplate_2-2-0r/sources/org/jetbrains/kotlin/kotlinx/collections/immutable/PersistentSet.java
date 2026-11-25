package org.jetbrains.kotlin.kotlinx.collections.immutable;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.archive.PathTree;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMutableSet;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: ImmutableSet.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u0012J\u001b\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH&J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH&J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H&J\u001b\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0006J\"\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00110\u0010H&J\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH&¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableSet;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentCollection;", ProductAction.ACTION_ADD, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentSet;", "addAll", PathTree.NAME_elements, "", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet$Builder;", LineReader.CLEAR, ProductAction.ACTION_REMOVE, "removeAll", "predicate", "Lkotlin/Function1;", "", "Builder", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public interface PersistentSet<E> extends ImmutableSet<E>, PersistentCollection<E> {

    /* JADX INFO: compiled from: ImmutableSet.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\u000e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet$Builder;", ExifInterface.LONGITUDE_EAST, "", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentCollection$Builder;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
    public interface Builder<E> extends Set<E>, KMutableSet, PersistentCollection.Builder<E> {
        PersistentSet<E> build();
    }

    @Override // java.util.Set, java.util.Collection
    PersistentSet<E> add(E e);

    @Override // java.util.Set, java.util.Collection
    PersistentSet<E> addAll(Collection<? extends E> elements);

    Builder<E> builder();

    @Override // java.util.Set, java.util.Collection
    PersistentSet<E> remove(E e);
}
