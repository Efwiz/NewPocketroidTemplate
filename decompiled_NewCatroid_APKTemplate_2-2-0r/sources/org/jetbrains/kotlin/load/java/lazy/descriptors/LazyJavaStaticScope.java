package org.jetbrains.kotlin.load.java.lazy.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: LazyJavaStaticScope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0014J\n\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J4\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0013H\u0014¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticScope;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;)V", "computeNonDeclaredProperties", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "result", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getDispatchReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "resolveMethodSignature", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope$MethodSignatureData;", "method", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "methodTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "returnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "valueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LazyJavaStaticScope extends LazyJavaScope {
    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected void computeNonDeclaredProperties(Name name2, Collection<PropertyDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected ReceiverParameterDescriptor getDispatchReceiverParameter() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyJavaStaticScope(LazyJavaResolverContext c) {
        super(c, null, 2, null);
        Intrinsics.checkNotNullParameter(c, "c");
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected LazyJavaScope.MethodSignatureData resolveMethodSignature(JavaMethod method, List<? extends TypeParameterDescriptor> methodTypeParameters, KotlinType returnType, List<? extends ValueParameterDescriptor> valueParameters) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(methodTypeParameters, "methodTypeParameters");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        return new LazyJavaScope.MethodSignatureData(returnType, null, valueParameters, methodTypeParameters, false, CollectionsKt.emptyList());
    }
}
