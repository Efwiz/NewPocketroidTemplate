package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: FlexibleTypeDeserializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u000bJ(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "flexibleId", "", "lowerBound", "Lorg/jetbrains/kotlin/types/SimpleType;", "upperBound", "ThrowException", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FlexibleTypeDeserializer {
    /* JADX INFO: renamed from: create */
    KotlinType mo7341create(ProtoBuf.Type proto, String flexibleId, SimpleType lowerBound, SimpleType upperBound);

    /* JADX INFO: compiled from: FlexibleTypeDeserializer.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer$ThrowException;", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "flexibleId", "", "lowerBound", "Lorg/jetbrains/kotlin/types/SimpleType;", "upperBound", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ThrowException implements FlexibleTypeDeserializer {
        public static final ThrowException INSTANCE = new ThrowException();

        private ThrowException() {
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer
        /* JADX INFO: renamed from: create */
        public KotlinType mo7341create(ProtoBuf.Type proto, String flexibleId, SimpleType lowerBound, SimpleType upperBound) {
            Intrinsics.checkNotNullParameter(proto, "proto");
            Intrinsics.checkNotNullParameter(flexibleId, "flexibleId");
            Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
            Intrinsics.checkNotNullParameter(upperBound, "upperBound");
            throw new IllegalArgumentException("This method should not be used.");
        }
    }
}
