package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u000e\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004B<\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012'\u0010\u0006\u001a#\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\u0007¢\u0006\u0002\u0010\rJ\u0015\u0010\u000e\u001a\u00028\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010\u0011J\u001d\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0017R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R/\u0010\u0006\u001a#\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/GenericCollectionExternalizer;", ExifInterface.GPS_DIRECTION_TRUE, "C", "", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "elementExternalizer", "newCollection", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "size", "", "(Lcom/intellij/util/io/DataExternalizer;Lkotlin/jvm/functions/Function1;)V", "read", "input", "Ljava/io/DataInput;", "(Ljava/io/DataInput;)Ljava/util/Collection;", "save", "", "output", "Ljava/io/DataOutput;", "collection", "(Ljava/io/DataOutput;Ljava/util/Collection;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class GenericCollectionExternalizer<T, C extends Collection<? extends T>> implements DataExternalizer<C> {
    private final DataExternalizer<T> elementExternalizer;
    private final Function1<Integer, Collection<T>> newCollection;

    /* JADX WARN: Multi-variable type inference failed */
    public GenericCollectionExternalizer(DataExternalizer<T> elementExternalizer, Function1<? super Integer, ? extends Collection<T>> newCollection) {
        Intrinsics.checkNotNullParameter(elementExternalizer, "elementExternalizer");
        Intrinsics.checkNotNullParameter(newCollection, "newCollection");
        this.elementExternalizer = elementExternalizer;
        this.newCollection = newCollection;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, C collection) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(collection, "collection");
        output.writeInt(collection.size());
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            this.elementExternalizer.save(output, it.next());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public C read(DataInput input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        int i = input.readInt();
        C c = (C) this.newCollection.invoke(Integer.valueOf(i));
        for (int i2 = 0; i2 < i; i2++) {
            c.add(this.elementExternalizer.read(input));
        }
        Intrinsics.checkNotNull(c, "null cannot be cast to non-null type C of org.jetbrains.kotlin.incremental.storage.GenericCollectionExternalizer");
        return c;
    }
}
