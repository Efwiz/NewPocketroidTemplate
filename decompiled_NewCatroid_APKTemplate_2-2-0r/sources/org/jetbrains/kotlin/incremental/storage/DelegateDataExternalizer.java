package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.container.SpecTypePair;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B1\u0012\u0014\u0010\u0003\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00050\u0004\u0012\u0014\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00020\u0004¢\u0006\u0002\u0010\u0007J\u0015\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0014R\u001f\u0010\u0003\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001f\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/DelegateDataExternalizer;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", SpecTypePair.NAME_types, "", "Ljava/lang/Class;", "typesExternalizers", "(Ljava/util/List;Ljava/util/List;)V", "getTypes", "()Ljava/util/List;", "getTypesExternalizers", "read", "input", "Ljava/io/DataInput;", "(Ljava/io/DataInput;)Ljava/lang/Object;", "save", "", "output", "Ljava/io/DataOutput;", "objectToExternalize", "(Ljava/io/DataOutput;Ljava/lang/Object;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegateDataExternalizer<T> implements DataExternalizer<T> {
    private final List<Class<? extends T>> types;
    private final List<DataExternalizer<? extends T>> typesExternalizers;

    /* JADX WARN: Multi-variable type inference failed */
    public DelegateDataExternalizer(List<? extends Class<? extends T>> types, List<? extends DataExternalizer<? extends T>> typesExternalizers) {
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(typesExternalizers, "typesExternalizers");
        this.types = types;
        this.typesExternalizers = typesExternalizers;
        if (types.size() != typesExternalizers.size()) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (types.size() >= 127) {
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    public final List<Class<? extends T>> getTypes() {
        return this.types;
    }

    public final List<DataExternalizer<? extends T>> getTypesExternalizers() {
        return this.typesExternalizers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, T objectToExternalize) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Class cls = null;
        boolean z = false;
        for (T t : this.types) {
            Intrinsics.checkNotNull(objectToExternalize);
            if (((Class) t).isAssignableFrom(objectToExternalize.getClass())) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                z = true;
                cls = t;
            }
        }
        if (z) {
            int iIndexOf = this.types.indexOf(cls);
            output.writeByte(iIndexOf);
            DataExternalizer<? extends T> dataExternalizer = this.typesExternalizers.get(iIndexOf);
            Intrinsics.checkNotNull(dataExternalizer, "null cannot be cast to non-null type com.intellij.util.io.DataExternalizer<T of org.jetbrains.kotlin.incremental.storage.DelegateDataExternalizer>");
            dataExternalizer.save(output, objectToExternalize);
            return;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public T read(DataInput input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        return this.typesExternalizers.get(input.readByte()).read(input);
    }
}
