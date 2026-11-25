package org.jetbrains.kotlin.resolve.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SyntheticJavaPartsProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J'\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0016R\u00020\u0007¢\u0006\u0002\u0010\rJ/\u0010\u000e\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016R\u00020\u0007¢\u0006\u0002\u0010\u0013J/\u0010\u0014\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH\u0016R\u00020\u0007¢\u0006\u0002\u0010\u0015J/\u0010\u0016\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016R\u00020\u0007¢\u0006\u0002\u0010\u0013J\u001f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\u0006\u0010\b\u001a\u00020\tH\u0016R\u00020\u0007¢\u0006\u0002\u0010\u0018J\u001f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\u0006\u0010\b\u001a\u00020\tH\u0016R\u00020\u0007¢\u0006\u0002\u0010\u0018J\u001f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\u0006\u0010\b\u001a\u00020\tH\u0016R\u00020\u0007¢\u0006\u0002\u0010\u0018J!\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001cH\u0016R\u00020\u0007¢\u0006\u0002\u0010\u001eR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/CompositeSyntheticJavaPartsProvider;", "Lorg/jetbrains/kotlin/resolve/jvm/SyntheticJavaPartsProvider;", "inner", "", "(Ljava/util/List;)V", "generateConstructors", "", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "thisDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "result", "", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Ljava/util/List;)V", "generateMethods", "name", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/name/Name;Ljava/util/Collection;)V", "generateNestedClass", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/name/Name;Ljava/util/List;)V", "generateStaticFunctions", "getMethodNames", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Ljava/util/List;", "getNestedClassNames", "getStaticFunctionNames", "modifyField", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "propertyDescriptor", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;)Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositeSyntheticJavaPartsProvider implements SyntheticJavaPartsProvider {
    private final List<SyntheticJavaPartsProvider> inner;

    /* JADX WARN: Multi-variable type inference failed */
    public CompositeSyntheticJavaPartsProvider(List<? extends SyntheticJavaPartsProvider> inner) {
        Intrinsics.checkNotNullParameter(inner, "inner");
        this.inner = inner;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider
    public List<Name> getMethodNames(LazyJavaResolverContext context_receiver_0, ClassDescriptor thisDescriptor) {
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        List<SyntheticJavaPartsProvider> list = this.inner;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticJavaPartsProvider) it.next()).getMethodNames(context_receiver_0, thisDescriptor));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider
    public void generateMethods(LazyJavaResolverContext context_receiver_0, ClassDescriptor thisDescriptor, Name name2, Collection<SimpleFunctionDescriptor> result) {
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        Iterator<T> it = this.inner.iterator();
        while (it.hasNext()) {
            ((SyntheticJavaPartsProvider) it.next()).generateMethods(context_receiver_0, thisDescriptor, name2, result);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider
    public List<Name> getStaticFunctionNames(LazyJavaResolverContext context_receiver_0, ClassDescriptor thisDescriptor) {
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        List<SyntheticJavaPartsProvider> list = this.inner;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticJavaPartsProvider) it.next()).getStaticFunctionNames(context_receiver_0, thisDescriptor));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider
    public void generateStaticFunctions(LazyJavaResolverContext context_receiver_0, ClassDescriptor thisDescriptor, Name name2, Collection<SimpleFunctionDescriptor> result) {
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        Iterator<T> it = this.inner.iterator();
        while (it.hasNext()) {
            ((SyntheticJavaPartsProvider) it.next()).generateStaticFunctions(context_receiver_0, thisDescriptor, name2, result);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider
    public void generateConstructors(LazyJavaResolverContext context_receiver_0, ClassDescriptor thisDescriptor, List<ClassConstructorDescriptor> result) {
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(result, "result");
        Iterator<T> it = this.inner.iterator();
        while (it.hasNext()) {
            ((SyntheticJavaPartsProvider) it.next()).generateConstructors(context_receiver_0, thisDescriptor, result);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider
    public List<Name> getNestedClassNames(LazyJavaResolverContext context_receiver_0, ClassDescriptor thisDescriptor) {
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        List<SyntheticJavaPartsProvider> list = this.inner;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticJavaPartsProvider) it.next()).getNestedClassNames(context_receiver_0, thisDescriptor));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider
    public void generateNestedClass(LazyJavaResolverContext context_receiver_0, ClassDescriptor thisDescriptor, Name name2, List<ClassDescriptor> result) {
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        Iterator<T> it = this.inner.iterator();
        while (it.hasNext()) {
            ((SyntheticJavaPartsProvider) it.next()).generateNestedClass(context_receiver_0, thisDescriptor, name2, result);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider
    public PropertyDescriptorImpl modifyField(LazyJavaResolverContext context_receiver_0, ClassDescriptor thisDescriptor, PropertyDescriptorImpl propertyDescriptor) {
        Intrinsics.checkNotNullParameter(context_receiver_0, "$context_receiver_0");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        Iterator<T> it = this.inner.iterator();
        while (it.hasNext()) {
            propertyDescriptor = ((SyntheticJavaPartsProvider) it.next()).modifyField(context_receiver_0, thisDescriptor, propertyDescriptor);
        }
        return propertyDescriptor;
    }
}
