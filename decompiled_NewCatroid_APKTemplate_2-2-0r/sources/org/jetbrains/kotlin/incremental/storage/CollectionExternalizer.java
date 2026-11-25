package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b0\u0007¢\u0006\u0002\u0010\tJ*\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/CollectionExternalizer;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/incremental/storage/AppendableDataExternalizer;", "", "elementExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "newCollection", "Lkotlin/Function0;", "", "(Lcom/intellij/util/io/DataExternalizer;Lkotlin/jvm/functions/Function0;)V", "append", "currentValue", "appendData", "createNil", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "value", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CollectionExternalizer<T> implements AppendableDataExternalizer<Collection<? extends T>> {
    private final DataExternalizer<T> elementExternalizer;
    private final Function0<Collection<T>> newCollection;

    /* JADX WARN: Multi-variable type inference failed */
    public CollectionExternalizer(DataExternalizer<T> elementExternalizer, Function0<? extends Collection<T>> newCollection) {
        Intrinsics.checkNotNullParameter(elementExternalizer, "elementExternalizer");
        Intrinsics.checkNotNullParameter(newCollection, "newCollection");
        this.elementExternalizer = elementExternalizer;
        this.newCollection = newCollection;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public Collection<T> read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        Collection<T> collectionInvoke = this.newCollection.invoke();
        DataInputStream dataInputStream = (DataInputStream) input;
        while (dataInputStream.available() > 0) {
            collectionInvoke.add(this.elementExternalizer.read(dataInputStream));
        }
        return collectionInvoke;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, Collection<? extends T> value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(value, "value");
        Iterator<T> it = value.iterator();
        while (it.hasNext()) {
            this.elementExternalizer.save(output, it.next());
        }
    }

    @Override // org.jetbrains.kotlin.incremental.storage.AppendableDataExternalizer
    public Collection<T> createNil() {
        return this.newCollection.invoke();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.incremental.storage.AppendableDataExternalizer
    public Collection<T> append(Collection<? extends T> currentValue, Collection<? extends T> appendData) {
        Intrinsics.checkNotNullParameter(currentValue, "currentValue");
        Intrinsics.checkNotNullParameter(appendData, "appendData");
        if (TypeIntrinsics.isMutableCollection(currentValue)) {
            TypeIntrinsics.asMutableCollection(currentValue).addAll(appendData);
            return currentValue;
        }
        return CollectionsKt.plus((Collection) currentValue, (Iterable) appendData);
    }
}
