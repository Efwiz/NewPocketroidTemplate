package org.jetbrains.kotlin.types.checker;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ClassicTypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0000\u001a\u0012\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00040\u0007H\u0002\u001a\b\u0010\t\u001a\u00020\nH\u0002\u001a\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\r\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u000fH\u0002\u001a\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012H\u0002\u001a\n\u0010\u0013\u001a\u00020\u0014*\u00020\u0015\u001a\r\u0010\u0016\u001a\u00020\u0017*\u00020\u0018H\u0082\b¨\u0006\u0019"}, d2 = {"captureFromExpressionInternal", "Lorg/jetbrains/kotlin/types/UnwrappedType;", ModuleXmlParser.TYPE, "containsInternal", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "errorSupportedOnlyInTypeInference", "", "hasExactInternal", "hasNoInferInternal", "makeDefinitelyNotNullOrNotNullInternal", "makeSimpleTypeDefinitelyNotNullOrNotNullInternal", "Lorg/jetbrains/kotlin/types/SimpleType;", "singleBestRepresentative", "collection", "", "convertVariance", "Lorg/jetbrains/kotlin/types/Variance;", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassicTypeSystemContextKt {

    /* JADX INFO: compiled from: ClassicTypeSystemContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.INV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final Variance convertVariance(TypeVariance typeVariance) {
        Intrinsics.checkNotNullParameter(typeVariance, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[typeVariance.ordinal()];
        if (i == 1) {
            return Variance.INVARIANT;
        }
        if (i == 2) {
            return Variance.IN_VARIANCE;
        }
        if (i == 3) {
            return Variance.OUT_VARIANCE;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UnwrappedType captureFromExpressionInternal(UnwrappedType unwrappedType) {
        return NewCapturedTypeKt.captureFromExpression(unwrappedType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasNoInferInternal(UnwrappedType unwrappedType) {
        return AnnotationsForResolveUtilsKt.hasNoInferAnnotation(unwrappedType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasExactInternal(UnwrappedType unwrappedType) {
        return AnnotationsForResolveUtilsKt.hasExactAnnotation(unwrappedType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UnwrappedType makeDefinitelyNotNullOrNotNullInternal(UnwrappedType unwrappedType) {
        return SpecialTypesKt.makeDefinitelyNotNullOrNotNull$default(unwrappedType, false, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SimpleType makeSimpleTypeDefinitelyNotNullOrNotNullInternal(SimpleType simpleType) {
        return SpecialTypesKt.makeSimpleTypeDefinitelyNotNullOrNotNull$default(simpleType, false, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean containsInternal(KotlinType kotlinType, Function1<? super KotlinTypeMarker, Boolean> function1) {
        return TypeUtilsKt.contains(kotlinType, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KotlinType singleBestRepresentative(Collection<? extends KotlinType> collection) {
        return FlexibleTypesKt.singleBestRepresentative(collection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void errorSupportedOnlyInTypeInference() {
        throw new IllegalStateException("supported only in type inference context".toString());
    }
}
