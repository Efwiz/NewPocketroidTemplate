package org.jetbrains.kotlin.descriptors.annotations;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.AnnotationMarker;

/* JADX INFO: compiled from: AnnotationDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\"\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "allValueArguments", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getAllValueArguments", "()Ljava/util/Map;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AnnotationDescriptor extends AnnotationMarker {
    Map<Name, ConstantValue<?>> getAllValueArguments();

    FqName getFqName();

    SourceElement getSource();

    KotlinType getType();

    /* JADX INFO: compiled from: AnnotationDescriptor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static FqName getFqName(AnnotationDescriptor annotationDescriptor) {
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
            if (annotationClass == null) {
                return null;
            }
            if (ErrorUtils.isError(annotationClass)) {
                annotationClass = null;
            }
            if (annotationClass != null) {
                return DescriptorUtilsKt.fqNameOrNull(annotationClass);
            }
            return null;
        }
    }
}
