package org.jetbrains.kotlin.resolve.constants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J=\u0010\u0005\u001a\n \u0007*\u0004\u0018\u0001H\u0006H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/IntValue;", "Lorg/jetbrains/kotlin/resolve/constants/IntegerValueConstant;", "", "value", "(I)V", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getType", "Lorg/jetbrains/kotlin/types/SimpleType;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntValue extends IntegerValueConstant<Integer> {
    public IntValue(int i) {
        super(Integer.valueOf(i));
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public SimpleType getType(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        SimpleType intType = module.getBuiltIns().getIntType();
        Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
        return intType;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitIntValue(this, data);
    }
}
