package org.jetbrains.kotlin.load.java;

import androidx.exifinterface.media.ExifInterface;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;

/* JADX INFO: compiled from: JavaNullabilityAnnotationSettings.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0011\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0012\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u0013R*\u0010\b\u001a\u001e\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00060\u0006\u0012\f\u0012\n \n*\u0004\u0018\u00018\u00008\u00000\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStatesImpl;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates;", "states", "", "Lorg/jetbrains/kotlin/name/FqName;", "(Ljava/util/Map;)V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getStates", "()Ljava/util/Map;", "storageManager", "Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "get", "fqName", "(Lorg/jetbrains/kotlin/name/FqName;)Ljava/lang/Object;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullabilityAnnotationStatesImpl<T> implements NullabilityAnnotationStates<T> {
    private final MemoizedFunctionToNullable<FqName, T> cache;
    private final Map<FqName, T> states;
    private final LockBasedStorageManager storageManager;

    /* JADX WARN: Multi-variable type inference failed */
    public NullabilityAnnotationStatesImpl(Map<FqName, ? extends T> states) {
        Intrinsics.checkNotNullParameter(states, "states");
        this.states = states;
        LockBasedStorageManager lockBasedStorageManager = new LockBasedStorageManager("Java nullability annotation states");
        this.storageManager = lockBasedStorageManager;
        MemoizedFunctionToNullable<FqName, T> memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues = lockBasedStorageManager.createMemoizedFunctionWithNullableValues(new Function1<FqName, T>(this) { // from class: org.jetbrains.kotlin.load.java.NullabilityAnnotationStatesImpl$cache$1
            final /* synthetic */ NullabilityAnnotationStatesImpl<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final T invoke(FqName fqName) {
                Intrinsics.checkNotNull(fqName);
                return (T) FqNamesUtilKt.findValueForMostSpecificFqname(fqName, this.this$0.getStates());
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues, "createMemoizedFunctionWithNullableValues(...)");
        this.cache = memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues;
    }

    public final Map<FqName, T> getStates() {
        return this.states;
    }

    public final LockBasedStorageManager getStorageManager() {
        return this.storageManager;
    }

    @Override // org.jetbrains.kotlin.load.java.NullabilityAnnotationStates
    public T get(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.cache.invoke(fqName);
    }
}
