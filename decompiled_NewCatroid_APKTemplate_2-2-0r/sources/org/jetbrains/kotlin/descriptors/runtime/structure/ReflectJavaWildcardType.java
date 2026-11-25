package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;

/* JADX INFO: compiled from: ReflectJavaWildcardType.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0010R\u0014\u0010\u0003\u001a\u00020\u0004X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaWildcardType;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaWildcardType;", "reflectType", "Ljava/lang/reflect/WildcardType;", "(Ljava/lang/reflect/WildcardType;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "bound", "getBound", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "isDeprecatedInJavaDoc", "", "()Z", "isExtends", "getReflectType", "()Ljava/lang/reflect/WildcardType;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaWildcardType extends ReflectJavaType implements JavaWildcardType {
    private final Collection<JavaAnnotation> annotations;
    private final boolean isDeprecatedInJavaDoc;
    private final WildcardType reflectType;

    public ReflectJavaWildcardType(WildcardType reflectType) {
        Intrinsics.checkNotNullParameter(reflectType, "reflectType");
        this.reflectType = reflectType;
        this.annotations = CollectionsKt.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaType
    public WildcardType getReflectType() {
        return this.reflectType;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    public ReflectJavaType getBound() {
        Type[] upperBounds = getReflectType().getUpperBounds();
        Type[] lowerBounds = getReflectType().getLowerBounds();
        if (upperBounds.length > 1 || lowerBounds.length > 1) {
            throw new UnsupportedOperationException("Wildcard types with many bounds are not yet supported: " + getReflectType());
        }
        if (lowerBounds.length == 1) {
            ReflectJavaType.Companion factory = ReflectJavaType.INSTANCE;
            Intrinsics.checkNotNull(lowerBounds);
            Object objSingle = ArraysKt.single(lowerBounds);
            Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
            return factory.create((Type) objSingle);
        }
        if (upperBounds.length == 1) {
            Intrinsics.checkNotNull(upperBounds);
            Type type = (Type) ArraysKt.single(upperBounds);
            if (!Intrinsics.areEqual(type, Object.class)) {
                ReflectJavaType.Companion factory2 = ReflectJavaType.INSTANCE;
                Intrinsics.checkNotNull(type);
                return factory2.create(type);
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    /* JADX INFO: renamed from: isExtends */
    public boolean getIsExtends() {
        Type[] upperBounds = getReflectType().getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        return !Intrinsics.areEqual(ArraysKt.firstOrNull(upperBounds), Object.class);
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
