package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;

/* JADX INFO: compiled from: ReflectJavaArrayType.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\u0014\u0010\u0003\u001a\u00020\u0004X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaArrayType;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayType;", "reflectType", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Type;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "componentType", "getComponentType", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "isDeprecatedInJavaDoc", "", "()Z", "getReflectType", "()Ljava/lang/reflect/Type;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaArrayType extends ReflectJavaType implements JavaArrayType {
    private final Collection<JavaAnnotation> annotations;
    private final ReflectJavaType componentType;
    private final boolean isDeprecatedInJavaDoc;
    private final Type reflectType;

    public ReflectJavaArrayType(Type reflectType) {
        ReflectJavaType reflectJavaTypeCreate;
        Intrinsics.checkNotNullParameter(reflectType, "reflectType");
        this.reflectType = reflectType;
        Type reflectType2 = getReflectType();
        if (!(reflectType2 instanceof GenericArrayType)) {
            if (reflectType2 instanceof Class) {
                Class cls = (Class) reflectType2;
                if (cls.isArray()) {
                    ReflectJavaType.Companion factory = ReflectJavaType.INSTANCE;
                    Class<?> componentType = cls.getComponentType();
                    Intrinsics.checkNotNullExpressionValue(componentType, "getComponentType(...)");
                    reflectJavaTypeCreate = factory.create(componentType);
                }
            }
            throw new IllegalArgumentException("Not an array type (" + getReflectType().getClass() + "): " + getReflectType());
        }
        ReflectJavaType.Companion factory2 = ReflectJavaType.INSTANCE;
        Type genericComponentType = ((GenericArrayType) reflectType2).getGenericComponentType();
        Intrinsics.checkNotNullExpressionValue(genericComponentType, "getGenericComponentType(...)");
        reflectJavaTypeCreate = factory2.create(genericComponentType);
        this.componentType = reflectJavaTypeCreate;
        this.annotations = CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaType
    protected Type getReflectType() {
        return this.reflectType;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaArrayType
    public ReflectJavaType getComponentType() {
        return this.componentType;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc, reason: from getter */
    public boolean getIsDeprecatedInJavaDoc() {
        return this.isDeprecatedInJavaDoc;
    }
}
