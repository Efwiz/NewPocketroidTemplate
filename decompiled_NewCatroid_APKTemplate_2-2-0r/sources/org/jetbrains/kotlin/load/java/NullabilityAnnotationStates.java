package org.jetbrains.kotlin.load.java;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JavaNullabilityAnnotationSettings.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u0007*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0002:\u0001\u0007J\u0018\u0010\u0003\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0004\u001a\u00020\u0005H¦\u0002¢\u0006\u0002\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates;", ExifInterface.GPS_DIRECTION_TRUE, "", "get", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)Ljava/lang/Object;", "Companion", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NullabilityAnnotationStates<T> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    T get(FqName fqName);

    /* JADX INFO: compiled from: JavaNullabilityAnnotationSettings.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates;", "", "getEMPTY", "()Lorg/jetbrains/kotlin/load/java/NullabilityAnnotationStates;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final NullabilityAnnotationStates EMPTY = new NullabilityAnnotationStatesImpl(MapsKt.emptyMap());

        private Companion() {
        }

        public final NullabilityAnnotationStates getEMPTY() {
            return EMPTY;
        }
    }
}
