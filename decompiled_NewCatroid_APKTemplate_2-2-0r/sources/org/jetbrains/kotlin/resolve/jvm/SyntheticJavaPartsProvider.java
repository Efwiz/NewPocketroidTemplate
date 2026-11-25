package org.jetbrains.kotlin.resolve.jvm;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SyntheticJavaPartsProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dJ'\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&R\u00020\u0004¢\u0006\u0002\u0010\nJ/\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH&R\u00020\u0004¢\u0006\u0002\u0010\u0010J/\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH&R\u00020\u0004¢\u0006\u0002\u0010\u0012J/\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH&R\u00020\u0004¢\u0006\u0002\u0010\u0010J\u001f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u00152\u0006\u0010\u0005\u001a\u00020\u0006H&R\u00020\u0004¢\u0006\u0002\u0010\u0016J\u001f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u00152\u0006\u0010\u0005\u001a\u00020\u0006H&R\u00020\u0004¢\u0006\u0002\u0010\u0016J\u001f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\u00152\u0006\u0010\u0005\u001a\u00020\u0006H&R\u00020\u0004¢\u0006\u0002\u0010\u0016J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u001aH&R\u00020\u0004¢\u0006\u0002\u0010\u001cø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/SyntheticJavaPartsProvider;", "", "generateConstructors", "", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "thisDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "result", "", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Ljava/util/List;)V", "generateMethods", "name", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/name/Name;Ljava/util/Collection;)V", "generateNestedClass", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/name/Name;Ljava/util/List;)V", "generateStaticFunctions", "getMethodNames", "", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Ljava/util/List;", "getNestedClassNames", "getStaticFunctionNames", "modifyField", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "propertyDescriptor", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;)Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "Companion", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SyntheticJavaPartsProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void generateConstructors(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor, List<ClassConstructorDescriptor> list);

    void generateMethods(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor, Name name2, Collection<SimpleFunctionDescriptor> collection);

    void generateNestedClass(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor, Name name2, List<ClassDescriptor> list);

    void generateStaticFunctions(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor, Name name2, Collection<SimpleFunctionDescriptor> collection);

    List<Name> getMethodNames(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor);

    List<Name> getNestedClassNames(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor);

    List<Name> getStaticFunctionNames(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor);

    PropertyDescriptorImpl modifyField(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor, PropertyDescriptorImpl propertyDescriptorImpl);

    /* JADX INFO: compiled from: SyntheticJavaPartsProvider.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/SyntheticJavaPartsProvider$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/resolve/jvm/CompositeSyntheticJavaPartsProvider;", "getEMPTY", "()Lorg/jetbrains/kotlin/resolve/jvm/CompositeSyntheticJavaPartsProvider;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final CompositeSyntheticJavaPartsProvider EMPTY = new CompositeSyntheticJavaPartsProvider(CollectionsKt.emptyList());

        private Companion() {
        }

        public final CompositeSyntheticJavaPartsProvider getEMPTY() {
            return EMPTY;
        }
    }
}
