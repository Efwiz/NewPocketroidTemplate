package org.jetbrains.kotlin.load.java.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;

/* JADX INFO: compiled from: resolvers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;", "", "resolveTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "javaTypeParameter", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "EMPTY", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeParameterResolver {
    TypeParameterDescriptor resolveTypeParameter(JavaTypeParameter javaTypeParameter);

    /* JADX INFO: compiled from: resolvers.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver$EMPTY;", "Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;", "()V", "resolveTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "javaTypeParameter", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EMPTY implements TypeParameterResolver {
        public static final EMPTY INSTANCE = new EMPTY();

        @Override // org.jetbrains.kotlin.load.java.lazy.TypeParameterResolver
        public TypeParameterDescriptor resolveTypeParameter(JavaTypeParameter javaTypeParameter) {
            Intrinsics.checkNotNullParameter(javaTypeParameter, "javaTypeParameter");
            return null;
        }

        private EMPTY() {
        }
    }
}
