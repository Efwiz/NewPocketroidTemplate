package org.jetbrains.kotlin.container;

import androidx.exifinterface.media.ExifInterface;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: Container.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0019\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0007J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0006\u0010\u0015\u001a\u00020\u0000J!\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0019H\u0016¢\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u000e\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!J\u001f\u0010\"\u001a\u00020\u00002\u0010\u0010#\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030%0$H\u0000¢\u0006\u0002\b&J\u001b\u0010'\u001a\u00020\u00002\f\u0010(\u001a\b\u0012\u0004\u0012\u00020)0$H\u0000¢\u0006\u0002\b*J\u0012\u0010+\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0018\u001a\u00020,H\u0016J\u0018\u0010+\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0018\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001cJ\u001a\u0010.\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0018\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001cH\u0002J\"\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001e002\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u00192\b\b\u0002\u0010-\u001a\u00020\u001cJ\b\u00101\u001a\u00020\u0005H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "Lorg/jetbrains/kotlin/container/ComponentContainer;", "Lorg/jetbrains/kotlin/container/ComponentProvider;", "Ljava/io/Closeable;", "id", "", "parent", "(Ljava/lang/String;Lorg/jetbrains/kotlin/container/StorageComponentContainer;)V", "componentStorage", "Lorg/jetbrains/kotlin/container/ComponentStorage;", "containerId", "getContainerId", "()Ljava/lang/String;", "unknownContext", "Lorg/jetbrains/kotlin/container/ComponentResolveContext;", "getUnknownContext", "()Lorg/jetbrains/kotlin/container/ComponentResolveContext;", "unknownContext$delegate", "Lkotlin/Lazy;", "close", "", "compose", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, ExifInterface.GPS_DIRECTION_TRUE, "request", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "createResolveContext", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "requestingDescriptor", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", ArchiveStreamFactory.DUMP, "printer", "Ljava/io/PrintStream;", "registerClashResolvers", "resolvers", "", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "registerClashResolvers$container", "registerDescriptors", "descriptors", "Lorg/jetbrains/kotlin/container/ComponentDescriptor;", "registerDescriptors$container", "resolve", "Ljava/lang/reflect/Type;", "context", "resolveIterable", "resolveMultiple", "", "toString", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StorageComponentContainer implements Closeable, ComponentContainer, ComponentProvider {
    private final ComponentStorage componentStorage;
    private final String id;

    /* JADX INFO: renamed from: unknownContext$delegate, reason: from kotlin metadata */
    private final Lazy unknownContext;

    public StorageComponentContainer(String id2, final StorageComponentContainer storageComponentContainer) {
        Intrinsics.checkNotNullParameter(id2, "id");
        this.id = id2;
        this.unknownContext = LazyKt.lazy(new Function0<ComponentResolveContext>() { // from class: org.jetbrains.kotlin.container.StorageComponentContainer$unknownContext$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ComponentResolveContext invoke() {
                StorageComponentContainer storageComponentContainer2 = this.$parent;
                return new ComponentResolveContext(this, DynamicComponentDescriptor.INSTANCE, storageComponentContainer2 != null ? new ComponentResolveContext(storageComponentContainer2, DynamicComponentDescriptor.INSTANCE, null, 4, null) : null);
            }
        });
        this.componentStorage = new ComponentStorage(id2, storageComponentContainer != null ? storageComponentContainer.componentStorage : null);
    }

    public /* synthetic */ StorageComponentContainer(String str, StorageComponentContainer storageComponentContainer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : storageComponentContainer);
    }

    public final ComponentResolveContext getUnknownContext() {
        return (ComponentResolveContext) this.unknownContext.getValue();
    }

    @Override // org.jetbrains.kotlin.container.ComponentContainer
    public ValueResolveContext createResolveContext(ValueDescriptor requestingDescriptor) {
        Intrinsics.checkNotNullParameter(requestingDescriptor, "requestingDescriptor");
        if (Intrinsics.areEqual(requestingDescriptor, DynamicComponentDescriptor.INSTANCE)) {
            return getUnknownContext();
        }
        return new ComponentResolveContext(this, requestingDescriptor, null, 4, null);
    }

    public final StorageComponentContainer compose() throws Throwable {
        this.componentStorage.compose(getUnknownContext());
        return this;
    }

    public final void dump(PrintStream printer) {
        Intrinsics.checkNotNullParameter(printer, "printer");
        this.componentStorage.dump(printer);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws ContainerConsistencyException, IOException {
        this.componentStorage.dispose();
    }

    public final ValueDescriptor resolve(Type request, ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(context, "context");
        ValueDescriptor valueDescriptorResolve = this.componentStorage.resolve(request, context);
        return valueDescriptorResolve == null ? resolveIterable(request, context) : valueDescriptorResolve;
    }

    @Override // org.jetbrains.kotlin.container.ComponentProvider
    public ValueDescriptor resolve(Type request) {
        Intrinsics.checkNotNullParameter(request, "request");
        return resolve(request, getUnknownContext());
    }

    private final ValueDescriptor resolveIterable(Type request, ValueResolveContext context) {
        if (!(request instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) request;
        if (!Intrinsics.areEqual(parameterizedType.getRawType(), Iterable.class)) {
            return null;
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            return null;
        }
        Type type = actualTypeArguments[0];
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length != 1) {
                return null;
            }
            type = upperBounds[0];
        } else if (!(type instanceof Class) && !(type instanceof ParameterizedType)) {
            return null;
        }
        ComponentStorage componentStorage = this.componentStorage;
        Intrinsics.checkNotNull(type);
        return new IterableDescriptor(componentStorage.resolveMultiple(type, context));
    }

    public static /* synthetic */ Iterable resolveMultiple$default(StorageComponentContainer storageComponentContainer, Class cls, ValueResolveContext valueResolveContext, int i, Object obj) {
        if ((i & 2) != 0) {
            valueResolveContext = storageComponentContainer.getUnknownContext();
        }
        return storageComponentContainer.resolveMultiple(cls, valueResolveContext);
    }

    public final Iterable<ValueDescriptor> resolveMultiple(Class<?> request, ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(context, "context");
        return this.componentStorage.resolveMultiple(request, context);
    }

    public final StorageComponentContainer registerDescriptors$container(List<? extends ComponentDescriptor> descriptors) throws Throwable {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        this.componentStorage.registerDescriptors$container(getUnknownContext(), descriptors);
        return this;
    }

    public final StorageComponentContainer registerClashResolvers$container(List<? extends PlatformExtensionsClashResolver<?>> resolvers) {
        Intrinsics.checkNotNullParameter(resolvers, "resolvers");
        this.componentStorage.registerClashResolvers$container(resolvers);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.container.ComponentProvider
    public <T> T create(Class<T> request) throws Throwable {
        Intrinsics.checkNotNullParameter(request, "request");
        ConstructorBinding constructorBindingBindToConstructor = ResolveKt.bindToConstructor(request, getContainerId(), getUnknownContext());
        List<ValueDescriptor> argumentDescriptors = constructorBindingBindToConstructor.getArgumentDescriptors();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(argumentDescriptors, 10));
        Iterator<T> it = argumentDescriptors.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueDescriptor) it.next()).getValue());
        }
        Object[] array = arrayList.toArray(new Object[0]);
        try {
            return (T) constructorBindingBindToConstructor.getConstructor().newInstance(Arrays.copyOf(array, array.length));
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException == null) {
                throw e;
            }
            Intrinsics.checkNotNull(targetException);
            throw targetException;
        }
    }

    @Override // org.jetbrains.kotlin.container.ComponentContainer
    public String getContainerId() {
        return "Container: " + this.id;
    }

    public String toString() {
        return getContainerId();
    }
}
