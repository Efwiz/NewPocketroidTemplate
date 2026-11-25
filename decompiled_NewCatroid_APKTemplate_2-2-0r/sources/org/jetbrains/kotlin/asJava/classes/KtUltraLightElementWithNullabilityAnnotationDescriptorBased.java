package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeNullability;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: KtUltraLightElementWithNullabilityAnnotationDescriptorBased.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0005J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightElementWithNullabilityAnnotationDescriptorBased;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "D", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightElementWithNullabilityAnnotation;", "computeQualifiedNameForNullabilityAnnotation", "", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtUltraLightElementWithNullabilityAnnotationDescriptorBased<T extends KtDeclaration, D extends PsiModifierListOwner> extends KtUltraLightElementWithNullabilityAnnotation<T, D> {

    /* JADX INFO: compiled from: KtUltraLightElementWithNullabilityAnnotationDescriptorBased.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeNullability.values().length];
            try {
                iArr[TypeNullability.NOT_NULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeNullability.NULLABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeNullability.FLEXIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    default String computeQualifiedNameForNullabilityAnnotation(KotlinType kotlinType) {
        PsiType psiTypeForNullabilityAnnotation;
        if (kotlinType == null) {
            return null;
        }
        if (KotlinTypeKt.isError(kotlinType)) {
            kotlinType = null;
        }
        if (kotlinType == null || (psiTypeForNullabilityAnnotation = getPsiTypeForNullabilityAnnotation()) == null || (psiTypeForNullabilityAnnotation instanceof PsiPrimitiveType)) {
            return null;
        }
        if (TypeUtilsKt.isTypeParameter(kotlinType)) {
            if (!TypeUtils.hasNullableSuperType(kotlinType)) {
                return NotNull.class.getName();
            }
            if (!kotlinType.getIsMarkedNullable()) {
                return null;
            }
        }
        int i = WhenMappings.$EnumSwitchMapping$0[TypeUtilsKt.nullability(kotlinType).ordinal()];
        if (i == 1) {
            return NotNull.class.getName();
        }
        if (i == 2) {
            return Nullable.class.getName();
        }
        if (i == 3) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }
}
