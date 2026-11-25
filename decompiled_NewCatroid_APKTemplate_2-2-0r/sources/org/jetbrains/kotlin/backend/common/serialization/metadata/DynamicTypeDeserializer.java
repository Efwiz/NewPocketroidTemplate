package org.jetbrains.kotlin.backend.common.serialization.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.checker.StrictEqualityTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: DynamicTypeDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/metadata/DynamicTypeDeserializer;", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "()V", "id", "", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "flexibleId", "lowerBound", "Lorg/jetbrains/kotlin/types/SimpleType;", "upperBound", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DynamicTypeDeserializer implements FlexibleTypeDeserializer {
    public static final DynamicTypeDeserializer INSTANCE = new DynamicTypeDeserializer();
    public static final String id = "kotlin.DynamicType";

    private DynamicTypeDeserializer() {
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer
    /* JADX INFO: renamed from: create */
    public KotlinType mo7341create(ProtoBuf.Type proto, String flexibleId, SimpleType lowerBound, SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(flexibleId, "flexibleId");
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        if (!Intrinsics.areEqual(flexibleId, "kotlin.DynamicType")) {
            return ErrorUtils.createErrorType(ErrorTypeKind.UNEXPECTED_FLEXIBLE_TYPE_ID, flexibleId, lowerBound.toString(), upperBound.toString());
        }
        StrictEqualityTypeChecker strictEqualityTypeChecker = StrictEqualityTypeChecker.INSTANCE;
        SimpleType simpleType = lowerBound;
        SimpleType nothingType = TypeUtilsKt.getBuiltIns(simpleType).getNothingType();
        Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
        if (strictEqualityTypeChecker.strictEqualTypes(lowerBound, nothingType)) {
            StrictEqualityTypeChecker strictEqualityTypeChecker2 = StrictEqualityTypeChecker.INSTANCE;
            SimpleType nullableAnyType = TypeUtilsKt.getBuiltIns(upperBound).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
            if (strictEqualityTypeChecker2.strictEqualTypes(upperBound, nullableAnyType)) {
                return DynamicTypesKt.createDynamicType(TypeUtilsKt.getBuiltIns(simpleType));
            }
        }
        return ErrorUtils.createErrorType(ErrorTypeKind.ILLEGAL_TYPE_RANGE_FOR_DYNAMIC, lowerBound.toString(), upperBound.toString());
    }
}
