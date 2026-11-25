package org.jetbrains.kotlin.load.java.components;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;

/* JADX INFO: compiled from: JavaAnnotationMapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R+\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/JavaDeprecatedAnnotationDescriptor;", "Lorg/jetbrains/kotlin/load/java/components/JavaAnnotationDescriptor;", "annotation", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;)V", "allValueArguments", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getAllValueArguments", "()Ljava/util/Map;", "allValueArguments$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaDeprecatedAnnotationDescriptor extends JavaAnnotationDescriptor {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JavaDeprecatedAnnotationDescriptor.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};

    /* JADX INFO: renamed from: allValueArguments$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue allValueArguments;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaDeprecatedAnnotationDescriptor(JavaAnnotation javaAnnotation, LazyJavaResolverContext c) {
        super(c, javaAnnotation, StandardNames.FqNames.deprecated);
        Intrinsics.checkNotNullParameter(c, "c");
        this.allValueArguments = c.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends StringValue>>() { // from class: org.jetbrains.kotlin.load.java.components.JavaDeprecatedAnnotationDescriptor$allValueArguments$2
            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends StringValue> invoke() {
                return MapsKt.mapOf(TuplesKt.to(JavaAnnotationMapper.INSTANCE.getDEPRECATED_ANNOTATION_MESSAGE$descriptors_jvm(), new StringValue("Deprecated in Java")));
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.components.JavaAnnotationDescriptor, org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return (Map) StorageKt.getValue(this.allValueArguments, this, (KProperty<?>) $$delegatedProperties[0]);
    }
}
