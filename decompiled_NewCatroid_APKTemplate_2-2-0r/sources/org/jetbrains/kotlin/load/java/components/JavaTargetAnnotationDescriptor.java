package org.jetbrains.kotlin.load.java.components;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;

/* JADX INFO: compiled from: JavaAnnotationMapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R/\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\t\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n0\b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/JavaTargetAnnotationDescriptor;", "Lorg/jetbrains/kotlin/load/java/components/JavaAnnotationDescriptor;", "annotation", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;)V", "allValueArguments", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "", "getAllValueArguments", "()Ljava/util/Map;", "allValueArguments$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaTargetAnnotationDescriptor extends JavaAnnotationDescriptor {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JavaTargetAnnotationDescriptor.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};

    /* JADX INFO: renamed from: allValueArguments$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue allValueArguments;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaTargetAnnotationDescriptor(JavaAnnotation annotation, LazyJavaResolverContext c) {
        super(c, annotation, StandardNames.FqNames.target);
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(c, "c");
        this.allValueArguments = c.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends ConstantValue<? extends Object>>>() { // from class: org.jetbrains.kotlin.load.java.components.JavaTargetAnnotationDescriptor$allValueArguments$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends ConstantValue<? extends Object>> invoke() {
                ConstantValue<?> constantValueMapJavaTargetArguments$descriptors_jvm;
                JavaAnnotationArgument firstArgument = this.this$0.getFirstArgument();
                if (firstArgument instanceof JavaArrayAnnotationArgument) {
                    constantValueMapJavaTargetArguments$descriptors_jvm = JavaAnnotationTargetMapper.INSTANCE.mapJavaTargetArguments$descriptors_jvm(((JavaArrayAnnotationArgument) this.this$0.getFirstArgument()).getElements());
                } else {
                    constantValueMapJavaTargetArguments$descriptors_jvm = firstArgument instanceof JavaEnumValueAnnotationArgument ? JavaAnnotationTargetMapper.INSTANCE.mapJavaTargetArguments$descriptors_jvm(CollectionsKt.listOf(this.this$0.getFirstArgument())) : null;
                }
                Map<Name, ? extends ConstantValue<? extends Object>> mapMapOf = constantValueMapJavaTargetArguments$descriptors_jvm != null ? MapsKt.mapOf(TuplesKt.to(JavaAnnotationMapper.INSTANCE.getTARGET_ANNOTATION_ALLOWED_TARGETS$descriptors_jvm(), constantValueMapJavaTargetArguments$descriptors_jvm)) : null;
                return mapMapOf == null ? MapsKt.emptyMap() : mapMapOf;
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.components.JavaAnnotationDescriptor, org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public Map<Name, ConstantValue<Object>> getAllValueArguments() {
        return (Map) StorageKt.getValue(this.allValueArguments, this, (KProperty<?>) $$delegatedProperties[0]);
    }
}
