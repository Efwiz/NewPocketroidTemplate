package org.jetbrains.kotlin.load.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: InternalFlexibleTypeTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/InternalFlexibleTypeTransformer;", "Lorg/jetbrains/kotlin/resolve/TypeResolver$TypeTransformerForTests;", "()V", "FLEXIBLE_TYPE_CLASSIFIER", "Lorg/jetbrains/kotlin/name/ClassId;", "transformType", "Lorg/jetbrains/kotlin/types/KotlinType;", "kotlinType", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InternalFlexibleTypeTransformer extends TypeResolver.TypeTransformerForTests {
    public static final ClassId FLEXIBLE_TYPE_CLASSIFIER;
    public static final InternalFlexibleTypeTransformer INSTANCE = new InternalFlexibleTypeTransformer();

    private InternalFlexibleTypeTransformer() {
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.internal.flexible.ft"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        FLEXIBLE_TYPE_CLASSIFIER = classId;
    }

    @Override // org.jetbrains.kotlin.resolve.TypeResolver.TypeTransformerForTests
    public KotlinType transformType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 == null || !Intrinsics.areEqual(FLEXIBLE_TYPE_CLASSIFIER.asSingleFqName().toUnsafe(), DescriptorUtils.getFqName(this$0)) || kotlinType.getArguments().size() != 2) {
            return null;
        }
        UnwrappedType unwrappedTypeUnwrap = kotlinType.getArguments().get(0).getNullableAnyType().unwrap();
        Intrinsics.checkNotNull(unwrappedTypeUnwrap, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        UnwrappedType unwrappedTypeUnwrap2 = kotlinType.getArguments().get(1).getNullableAnyType().unwrap();
        Intrinsics.checkNotNull(unwrappedTypeUnwrap2, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        return KotlinTypeFactory.flexibleType((SimpleType) unwrappedTypeUnwrap, (SimpleType) unwrappedTypeUnwrap2);
    }
}
