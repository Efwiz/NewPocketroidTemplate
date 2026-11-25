package org.jetbrains.kotlin.resolve.constants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J=\u0010\u0004\u001a\n \u0006*\u0004\u0018\u0001H\u0005H\u0005\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00070\t2\u0006\u0010\n\u001a\u0002H\u0007H\u0016¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/NullValue;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "Ljava/lang/Void;", "()V", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getType", "Lorg/jetbrains/kotlin/types/SimpleType;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullValue extends ConstantValue<Void> {
    public NullValue() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public SimpleType getType(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        SimpleType nullableNothingType = module.getBuiltIns().getNullableNothingType();
        Intrinsics.checkNotNullExpressionValue(nullableNothingType, "getNullableNothingType(...)");
        return nullableNothingType;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitNullValue(this, data);
    }
}
