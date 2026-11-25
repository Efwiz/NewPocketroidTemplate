package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002J\u001d\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0006J\r\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/AppendableDataExternalizer;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "append", "currentValue", "appendData", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "createNil", "()Ljava/lang/Object;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AppendableDataExternalizer<T> extends DataExternalizer<T> {
    T append(T currentValue, T appendData);

    T createNil();
}
