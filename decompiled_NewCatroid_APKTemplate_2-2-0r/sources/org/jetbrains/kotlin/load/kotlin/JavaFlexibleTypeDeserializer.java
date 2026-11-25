package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.load.java.lazy.types.RawTypeImpl;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: JavaFlexibleTypeDeserializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JavaFlexibleTypeDeserializer;", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "flexibleId", "", "lowerBound", "Lorg/jetbrains/kotlin/types/SimpleType;", "upperBound", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaFlexibleTypeDeserializer implements FlexibleTypeDeserializer {
    public static final JavaFlexibleTypeDeserializer INSTANCE = new JavaFlexibleTypeDeserializer();

    private JavaFlexibleTypeDeserializer() {
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer
    /* JADX INFO: renamed from: create */
    public KotlinType mo7341create(ProtoBuf.Type proto, String flexibleId, SimpleType lowerBound, SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(flexibleId, "flexibleId");
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        if (!Intrinsics.areEqual(flexibleId, JvmProtoBufUtil.PLATFORM_TYPE_ID)) {
            return ErrorUtils.createErrorType(ErrorTypeKind.ERROR_FLEXIBLE_TYPE, flexibleId, lowerBound.toString(), upperBound.toString());
        }
        if (proto.hasExtension(JvmProtoBuf.isRaw)) {
            return new RawTypeImpl(lowerBound, upperBound);
        }
        return KotlinTypeFactory.flexibleType(lowerBound, upperBound);
    }
}
