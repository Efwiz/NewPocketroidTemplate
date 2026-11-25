package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: NullFlexibleTypeDeserializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/NullFlexibleTypeDeserializer;", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "flexibleId", "", "lowerBound", "Lorg/jetbrains/kotlin/types/SimpleType;", "upperBound", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullFlexibleTypeDeserializer implements FlexibleTypeDeserializer {
    public static final NullFlexibleTypeDeserializer INSTANCE = new NullFlexibleTypeDeserializer();

    private NullFlexibleTypeDeserializer() {
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer
    /* JADX INFO: renamed from: create, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KotlinType mo7341create(ProtoBuf.Type type, String str, SimpleType simpleType, SimpleType simpleType2) {
        return (KotlinType) create(type, str, simpleType, simpleType2);
    }

    public Void create(ProtoBuf.Type proto, String flexibleId, SimpleType lowerBound, SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(flexibleId, "flexibleId");
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        throw new IllegalStateException("Illegal use of flexible type deserializer.".toString());
    }
}
