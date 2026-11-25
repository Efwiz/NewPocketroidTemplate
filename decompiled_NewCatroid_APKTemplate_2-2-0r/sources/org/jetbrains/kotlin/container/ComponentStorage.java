package org.jetbrains.kotlin.container;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ContainerUtils;
import io.netty.util.internal.StringUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: Storage.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0005J4\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000e2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00100\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0013H\u0002J\u000e\u0010$\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u001e\u0010%\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0&H\u0002J\u0006\u0010'\u001a\u00020\u001dJ\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u000eH\u0002J\u000e\u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020+J\u000e\u0010,\u001a\b\u0012\u0004\u0012\u00020\u000e0-H\u0002J\u001e\u0010.\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001e\u001a\u00020\u001f2\n\u0010/\u001a\u0006\u0012\u0002\b\u000300H\u0002J\u0018\u00101\u001a\u00020\u001d2\u0006\u00102\u001a\u0002032\u0006\u0010\u001e\u001a\u000204H\u0002J\u001e\u00101\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000e0&H\u0002J$\u00106\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0&H\u0002J\u001f\u00107\u001a\u00020\u001d2\u0010\u00108\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0-H\u0000¢\u0006\u0002\b9J\u0018\u0010:\u001a\u00020\u001d2\u0006\u0010;\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u000204H\u0002J#\u0010<\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u000e0-H\u0000¢\u0006\u0002\b>J\u001a\u0010?\u001a\u0004\u0018\u00010@2\u0006\u0010;\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u000204H\u0016J\u001c\u0010A\u001a\b\u0012\u0004\u0012\u00020@0B2\u0006\u0010;\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u000204R\u0018\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR/\u0010\f\u001a#\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u000e0\u000e\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u00100\u00100\r¢\u0006\u0002\b\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/container/ComponentStorage;", "Lorg/jetbrains/kotlin/container/ValueResolver;", "myId", "", "parent", "(Ljava/lang/String;Lorg/jetbrains/kotlin/container/ComponentStorage;)V", "clashResolvers", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "containerId", "getContainerId", "()Ljava/lang/String;", "dependencies", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "Lorg/jetbrains/kotlin/container/ComponentDescriptor;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Ljava/lang/reflect/Type;", "Lorg/jetbrains/annotations/NotNull;", "descriptors", "Ljava/util/LinkedHashSet;", "registry", "Lorg/jetbrains/kotlin/container/ComponentRegistry;", "state", "Lorg/jetbrains/kotlin/container/ComponentStorageState;", "getState", "()Lorg/jetbrains/kotlin/container/ComponentStorageState;", "setState", "(Lorg/jetbrains/kotlin/container/ComponentStorageState;)V", "collectAdhocComponents", "", "context", "Lorg/jetbrains/kotlin/container/ComponentResolveContext;", "descriptor", "visitedTypes", "Ljava/util/HashSet;", "adhocDescriptors", "compose", "composeDescriptors", "", "dispose", "disposeDescriptor", ArchiveStreamFactory.DUMP, "printer", "Ljava/io/PrintStream;", "getDescriptorsInDisposeOrder", "", "getImplicitlyDefinedDependency", "rawType", "Ljava/lang/Class;", "injectProperties", "instance", "", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "components", "inspectDependenciesAndRegisterAdhoc", "registerClashResolvers", "resolvers", "registerClashResolvers$container", "registerDependency", "request", "registerDescriptors", FirebaseAnalytics.Param.ITEMS, "registerDescriptors$container", "resolve", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", "resolveMultiple", "", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComponentStorage implements ValueResolver {
    private final ArrayList<PlatformExtensionsClashResolver<?>> clashResolvers;
    private final MultiMap<ComponentDescriptor, Type> dependencies;
    private final LinkedHashSet<ComponentDescriptor> descriptors;
    private final String myId;
    private final ComponentRegistry registry;
    private ComponentStorageState state;

    public ComponentStorage(String myId, ComponentStorage componentStorage) {
        Intrinsics.checkNotNullParameter(myId, "myId");
        this.myId = myId;
        this.state = ComponentStorageState.Initial;
        this.descriptors = new LinkedHashSet<>();
        MultiMap<ComponentDescriptor, Type> multiMapCreateLinkedSet = MultiMap.createLinkedSet();
        Intrinsics.checkNotNullExpressionValue(multiMapCreateLinkedSet, "createLinkedSet(...)");
        this.dependencies = multiMapCreateLinkedSet;
        ArrayList<PlatformExtensionsClashResolver<?>> arrayList = new ArrayList<>();
        this.clashResolvers = arrayList;
        ComponentRegistry componentRegistry = new ComponentRegistry();
        this.registry = componentRegistry;
        if (componentStorage != null) {
            componentRegistry.addAll(componentStorage.registry);
            arrayList.addAll(componentStorage.clashResolvers);
        }
    }

    public final ComponentStorageState getState() {
        return this.state;
    }

    public final void setState(ComponentStorageState componentStorageState) {
        Intrinsics.checkNotNullParameter(componentStorageState, "<set-?>");
        this.state = componentStorageState;
    }

    private static final boolean resolve$isDefaultComponent(ComponentDescriptor componentDescriptor) {
        return (componentDescriptor instanceof DefaultInstanceComponentDescriptor) || (componentDescriptor instanceof DefaultSingletonTypeComponentDescriptor);
    }

    @Override // org.jetbrains.kotlin.container.ValueResolver
    public ValueDescriptor resolve(Type request, ValueResolveContext context) throws ContainerConsistencyException, InvalidCardinalityException {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.state == ComponentStorageState.Initial) {
            throw new ContainerConsistencyException("Container was not composed before resolving");
        }
        Collection<ComponentDescriptor> collectionTryGetEntry = this.registry.tryGetEntry(request);
        if (collectionTryGetEntry.isEmpty()) {
            return null;
        }
        registerDependency(request, context);
        if (collectionTryGetEntry.size() == 1) {
            return (ValueDescriptor) CollectionsKt.single(collectionTryGetEntry);
        }
        Collection<ComponentDescriptor> collection = collectionTryGetEntry;
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (!resolve$isDefaultComponent((ComponentDescriptor) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return (ValueDescriptor) CollectionsKt.first(collection);
        }
        ComponentDescriptor componentDescriptor = (ComponentDescriptor) CollectionsKt.singleOrNull((List) arrayList2);
        if (componentDescriptor != null) {
            return componentDescriptor;
        }
        throw new InvalidCardinalityException(getContainerId() + ": Request " + request + " cannot be satisfied because there is more than one type registered\nClashed registrations: " + CollectionsKt.joinToString$default(collection, null, null, null, 0, null, null, 63, null));
    }

    private final void registerDependency(Type request, ValueResolveContext context) {
        if (context instanceof ComponentResolveContext) {
            ValueDescriptor requestingDescriptor = ((ComponentResolveContext) context).getRequestingDescriptor();
            if (requestingDescriptor instanceof ComponentDescriptor) {
                this.dependencies.putValue((ComponentDescriptor) requestingDescriptor, request);
            }
        }
    }

    public final void dump(PrintStream printer) {
        Intrinsics.checkNotNullParameter(printer, "printer");
        String containerId = getContainerId();
        printer.println(containerId);
        printer.println(StringsKt.repeat(ContainerUtils.KEY_VALUE_DELIMITER, containerId.length()));
        printer.println();
        for (ComponentDescriptor componentDescriptor : getDescriptorsInDisposeOrder()) {
            printer.println(componentDescriptor);
            Collection<Type> collection = this.dependencies.get(componentDescriptor);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            for (Type type : collection) {
                printer.print("   -> ");
                String string = type.toString();
                printer.print(StringsKt.substringBefore$default(string, " ", (String) null, 2, (Object) null));
                printer.print(" ");
                printer.print(StringsKt.substringAfterLast$default(string, ".", (String) null, 2, (Object) null));
                ComponentRegistry componentRegistry = this.registry;
                Intrinsics.checkNotNull(type);
                Collection<ComponentDescriptor> collectionTryGetEntry = componentRegistry.tryGetEntry(type);
                printer.print(" as ");
                printer.print(collectionTryGetEntry);
                printer.println();
            }
            printer.println();
        }
    }

    public final String getContainerId() {
        return "Container: " + this.myId;
    }

    public final Iterable<ValueDescriptor> resolveMultiple(Type request, ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(context, "context");
        registerDependency(request, context);
        return this.registry.tryGetEntry(request);
    }

    public final void registerClashResolvers$container(List<? extends PlatformExtensionsClashResolver<?>> resolvers) {
        Intrinsics.checkNotNullParameter(resolvers, "resolvers");
        this.clashResolvers.addAll(resolvers);
    }

    public final void registerDescriptors$container(ComponentResolveContext context, List<? extends ComponentDescriptor> items) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(items, "items");
        if (this.state == ComponentStorageState.Disposed) {
            throw new ContainerConsistencyException("Cannot register descriptors in " + this.state + " state");
        }
        Iterator<? extends ComponentDescriptor> it = items.iterator();
        while (it.hasNext()) {
            this.descriptors.add(it.next());
        }
        if (this.state == ComponentStorageState.Initialized) {
            composeDescriptors(context, items);
        }
    }

    public final void compose(ComponentResolveContext context) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.state != ComponentStorageState.Initial) {
            throw new ContainerConsistencyException(getContainerId() + StringUtil.SPACE + this.myId + " was already composed.");
        }
        this.state = ComponentStorageState.Initialized;
        composeDescriptors(context, this.descriptors);
    }

    private final void composeDescriptors(ComponentResolveContext context, Collection<? extends ComponentDescriptor> descriptors) throws Throwable {
        if (descriptors.isEmpty()) {
            return;
        }
        this.registry.addAll(descriptors);
        LinkedHashSet<ComponentDescriptor> linkedHashSetInspectDependenciesAndRegisterAdhoc = inspectDependenciesAndRegisterAdhoc(context, descriptors);
        this.registry.resolveClashesIfAny(context.getContainer(), this.clashResolvers);
        injectProperties(context, CollectionsKt.plus((Collection) descriptors, (Iterable) linkedHashSetInspectDependenciesAndRegisterAdhoc));
    }

    private final void injectProperties(ComponentResolveContext context, Collection<? extends ComponentDescriptor> components) throws Throwable {
        for (ComponentDescriptor componentDescriptor : components) {
            if (componentDescriptor.getShouldInjectProperties()) {
                injectProperties(componentDescriptor.getValue(), context.getContainer().createResolveContext(componentDescriptor));
            }
        }
    }

    private final LinkedHashSet<ComponentDescriptor> inspectDependenciesAndRegisterAdhoc(ComponentResolveContext context, Collection<? extends ComponentDescriptor> descriptors) {
        LinkedHashSet<ComponentDescriptor> linkedHashSet = new LinkedHashSet<>();
        HashSet<Type> hashSet = new HashSet<>();
        Iterator<? extends ComponentDescriptor> it = descriptors.iterator();
        while (it.hasNext()) {
            collectAdhocComponents(context, it.next(), hashSet, linkedHashSet);
        }
        this.registry.addAll(linkedHashSet);
        return linkedHashSet;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0043  */
    private final void collectAdhocComponents(ComponentResolveContext context, ComponentDescriptor descriptor, HashSet<Type> visitedTypes, LinkedHashSet<ComponentDescriptor> adhocDescriptors) {
        Class<?> cls;
        ComponentDescriptor implicitlyDefinedDependency;
        for (Type type : descriptor.getDependencies(context)) {
            if (visitedTypes.add(type) && this.registry.tryGetEntry(type).isEmpty()) {
                if (type instanceof Class) {
                    cls = (Class) type;
                } else if (type instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) type).getRawType();
                    if (rawType instanceof Class) {
                        cls = (Class) rawType;
                    } else {
                        cls = null;
                    }
                } else {
                    cls = null;
                }
                if (cls != null && (implicitlyDefinedDependency = getImplicitlyDefinedDependency(context, cls)) != null) {
                    adhocDescriptors.add(implicitlyDefinedDependency);
                    collectAdhocComponents(context, implicitlyDefinedDependency, visitedTypes, adhocDescriptors);
                }
            }
        }
    }

    private final ComponentDescriptor getImplicitlyDefinedDependency(ComponentResolveContext context, Class<?> rawType) throws NoSuchFieldException {
        Object obj;
        if (!Modifier.isAbstract(rawType.getModifiers()) && !rawType.isPrimitive()) {
            return new ImplicitSingletonTypeComponentDescriptor(context.getContainer(), rawType);
        }
        Class<?> defaultImplementation = CacheKt.getInfo(rawType).getDefaultImplementation();
        if (defaultImplementation != null && CacheKt.getInfo(defaultImplementation).getConstructorInfo() != null) {
            return new DefaultSingletonTypeComponentDescriptor(context.getContainer(), defaultImplementation);
        }
        DefaultInstanceComponentDescriptor defaultInstanceComponentDescriptor = null;
        if (defaultImplementation == null) {
            return null;
        }
        Field field = defaultImplementation.getField(JvmAbi.INSTANCE_FIELD);
        if (field != null && (obj = field.get(null)) != null) {
            defaultInstanceComponentDescriptor = new DefaultInstanceComponentDescriptor(obj);
        }
        return defaultInstanceComponentDescriptor;
    }

    private final void injectProperties(Object instance, ValueResolveContext context) throws Throwable {
        Iterator<T> it = CacheKt.getInfo(instance.getClass()).getSetterInfos().iterator();
        while (it.hasNext()) {
            ResolveKt.bindToMethod(((SetterInfo) it.next()).getMethod(), getContainerId(), context).invoke(instance);
        }
    }

    public final void dispose() throws ContainerConsistencyException, IOException {
        if (this.state != ComponentStorageState.Initialized) {
            if (this.state != ComponentStorageState.Initial) {
                throw new ContainerConsistencyException("Component container cannot be disposed in the " + this.state + " state.");
            }
            return;
        }
        this.state = ComponentStorageState.Disposing;
        Iterator<ComponentDescriptor> it = getDescriptorsInDisposeOrder().iterator();
        while (it.hasNext()) {
            disposeDescriptor(it.next());
        }
        this.state = ComponentStorageState.Disposed;
    }

    private final List<ComponentDescriptor> getDescriptorsInDisposeOrder() {
        return DataStructuresKt.topologicalSort$default(this.descriptors, false, new Function1<ComponentDescriptor, Iterable<? extends ComponentDescriptor>>() { // from class: org.jetbrains.kotlin.container.ComponentStorage.getDescriptorsInDisposeOrder.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Iterable<ComponentDescriptor> invoke(ComponentDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ArrayList arrayList = new ArrayList();
                for (Type type : ComponentStorage.this.dependencies.get(it)) {
                    ComponentRegistry componentRegistry = ComponentStorage.this.registry;
                    Intrinsics.checkNotNull(type);
                    Iterator<ComponentDescriptor> it2 = componentRegistry.tryGetEntry(type).iterator();
                    while (it2.hasNext()) {
                        arrayList.add(it2.next());
                    }
                }
                return arrayList;
            }
        }, 2, null);
    }

    private final void disposeDescriptor(ComponentDescriptor descriptor) throws IOException {
        if (descriptor instanceof Closeable) {
            ((Closeable) descriptor).close();
        }
    }
}
