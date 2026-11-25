package org.jetbrains.kotlin.load.java.lazy.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.ErasureProjectionComputer;
import org.jetbrains.kotlin.types.ErasureTypeAttributes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeParameterUpperBoundEraser;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: RawProjectionComputer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/types/RawProjectionComputer;", "Lorg/jetbrains/kotlin/types/ErasureProjectionComputer;", "()V", "computeProjection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "parameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "typeAttr", "Lorg/jetbrains/kotlin/types/ErasureTypeAttributes;", "typeParameterUpperBoundEraser", "Lorg/jetbrains/kotlin/types/TypeParameterUpperBoundEraser;", "erasedUpperBound", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RawProjectionComputer extends ErasureProjectionComputer {

    /* JADX INFO: compiled from: RawProjectionComputer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JavaTypeFlexibility.values().length];
            try {
                iArr[JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JavaTypeFlexibility.INFLEXIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.types.ErasureProjectionComputer
    public TypeProjection computeProjection(TypeParameterDescriptor parameter, ErasureTypeAttributes typeAttr, TypeParameterUpperBoundEraser typeParameterUpperBoundEraser, KotlinType erasedUpperBound) {
        TypeProjectionImpl typeProjectionImplMakeStarProjection;
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(typeAttr, "typeAttr");
        Intrinsics.checkNotNullParameter(typeParameterUpperBoundEraser, "typeParameterUpperBoundEraser");
        Intrinsics.checkNotNullParameter(erasedUpperBound, "erasedUpperBound");
        if (!(typeAttr instanceof JavaTypeAttributes)) {
            return super.computeProjection(parameter, typeAttr, typeParameterUpperBoundEraser, erasedUpperBound);
        }
        JavaTypeAttributes javaTypeAttributesWithFlexibility = (JavaTypeAttributes) typeAttr;
        if (!javaTypeAttributesWithFlexibility.isRaw()) {
            javaTypeAttributesWithFlexibility = javaTypeAttributesWithFlexibility.withFlexibility(JavaTypeFlexibility.INFLEXIBLE);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[javaTypeAttributesWithFlexibility.getFlexibility().ordinal()];
        if (i == 1) {
            return new TypeProjectionImpl(Variance.INVARIANT, erasedUpperBound);
        }
        if (i == 2 || i == 3) {
            if (!parameter.getVariance().getAllowsOutPosition()) {
                typeProjectionImplMakeStarProjection = new TypeProjectionImpl(Variance.INVARIANT, DescriptorUtilsKt.getBuiltIns(parameter).getNothingType());
            } else {
                List<TypeParameterDescriptor> parameters = erasedUpperBound.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                if (!parameters.isEmpty()) {
                    typeProjectionImplMakeStarProjection = new TypeProjectionImpl(Variance.OUT_VARIANCE, erasedUpperBound);
                } else {
                    typeProjectionImplMakeStarProjection = TypeUtils.makeStarProjection(parameter, javaTypeAttributesWithFlexibility);
                }
            }
            Intrinsics.checkNotNull(typeProjectionImplMakeStarProjection);
            return typeProjectionImplMakeStarProjection;
        }
        throw new NoWhenBranchMatchedException();
    }
}
