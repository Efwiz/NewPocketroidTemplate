package org.jetbrains.kotlin.container;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;

/* JADX INFO: compiled from: Singletons.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\b\u0000\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B)\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0014H\u0016R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/container/ClashResolutionDescriptor;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "Lorg/jetbrains/kotlin/container/SingletonDescriptor;", "container", "Lorg/jetbrains/kotlin/container/ComponentContainer;", "resolver", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "clashedComponents", "", "Lorg/jetbrains/kotlin/container/ComponentDescriptor;", "(Lorg/jetbrains/kotlin/container/ComponentContainer;Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;Ljava/util/List;)V", "createInstance", "", "context", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "getDependencies", "", "Ljava/lang/reflect/Type;", "getRegistrations", ""}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClashResolutionDescriptor<E extends PlatformSpecificExtension<E>> extends SingletonDescriptor {
    private final List<ComponentDescriptor> clashedComponents;
    private final PlatformExtensionsClashResolver<E> resolver;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ClashResolutionDescriptor(ComponentContainer container, PlatformExtensionsClashResolver<E> resolver, List<? extends ComponentDescriptor> clashedComponents) {
        super(container);
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Intrinsics.checkNotNullParameter(clashedComponents, "clashedComponents");
        this.resolver = resolver;
        this.clashedComponents = clashedComponents;
    }

    @Override // org.jetbrains.kotlin.container.SingletonDescriptor
    protected Object createInstance(ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        setState(ComponentState.Initializing);
        List<? extends E> listComputeArguments = ResolveKt.computeArguments(this.clashedComponents);
        Intrinsics.checkNotNull(listComputeArguments, "null cannot be cast to non-null type kotlin.collections.List<E of org.jetbrains.kotlin.container.ClashResolutionDescriptor>");
        PlatformSpecificExtension platformSpecificExtensionResolveExtensionsClash = this.resolver.resolveExtensionsClash(listComputeArguments);
        setState(ComponentState.Initialized);
        return platformSpecificExtensionResolveExtensionsClash;
    }

    @Override // org.jetbrains.kotlin.container.ComponentDescriptor
    public Iterable<Type> getRegistrations() {
        throw new IllegalStateException("Shouldn't be called");
    }

    @Override // org.jetbrains.kotlin.container.ComponentDescriptor
    public Collection<Type> getDependencies(ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        throw new IllegalStateException("Shouldn't be called");
    }
}
