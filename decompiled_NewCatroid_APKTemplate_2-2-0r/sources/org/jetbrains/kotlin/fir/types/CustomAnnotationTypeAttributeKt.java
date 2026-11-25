package org.jetbrains.kotlin.fir.types;

import io.reactivex.annotations.SchedulerSupport;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;

/* JADX INFO: compiled from: CustomAnnotationTypeAttribute.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001b\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u001b\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\n\u0010\r¨\u0006\u000e"}, d2 = {SchedulerSupport.CUSTOM, "Lorg/jetbrains/kotlin/fir/types/CustomAnnotationTypeAttribute;", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getCustom", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CustomAnnotationTypeAttribute;", "custom$delegate", "Lkotlin/properties/ReadOnlyProperty;", "customAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getCustomAnnotations", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Ljava/util/List;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Ljava/util/List;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CustomAnnotationTypeAttributeKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CustomAnnotationTypeAttributeKt.class, Constants.TYPE_TREE), SchedulerSupport.CUSTOM, "getCustom(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CustomAnnotationTypeAttribute;"))};
    private static final ReadOnlyProperty custom$delegate;

    static {
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor = ConeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(CustomAnnotationTypeAttribute.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
        custom$delegate = readOnlyPropertyGenerateNullableAccessor;
    }

    public static final CustomAnnotationTypeAttribute getCustom(ConeAttributes coneAttributes) {
        Intrinsics.checkNotNullParameter(coneAttributes, "<this>");
        return (CustomAnnotationTypeAttribute) custom$delegate.getValue(coneAttributes, $$delegatedProperties[0]);
    }

    public static final List<FirAnnotation> getCustomAnnotations(ConeAttributes coneAttributes) {
        Intrinsics.checkNotNullParameter(coneAttributes, "<this>");
        CustomAnnotationTypeAttribute custom = getCustom(coneAttributes);
        List<FirAnnotation> annotations = custom != null ? custom.getAnnotations() : null;
        return annotations == null ? CollectionsKt.emptyList() : annotations;
    }

    public static final List<FirAnnotation> getCustomAnnotations(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return getCustomAnnotations(coneKotlinType.getAttributes());
    }
}
