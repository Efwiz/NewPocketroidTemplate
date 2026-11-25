package org.jetbrains.kotlin.container;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;

/* JADX INFO: compiled from: Registry.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0000J \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\u000e2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ \u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0010\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00140\u0013J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0016\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0001`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/container/ComponentRegistry;", "", "()V", "registrationMap", "Ljava/util/HashMap;", "Ljava/lang/reflect/Type;", "Lkotlin/collections/HashMap;", "addAll", "", "descriptors", "", "Lorg/jetbrains/kotlin/container/ComponentDescriptor;", "other", "buildRegistrationMap", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "resolveClashesIfAny", "container", "Lorg/jetbrains/kotlin/container/ComponentContainer;", "clashResolvers", "", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "tryGetEntry", "request"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComponentRegistry {
    private final HashMap<Type, Object> registrationMap = new HashMap<>();

    public final MultiMap<Type, ComponentDescriptor> buildRegistrationMap(Collection<? extends ComponentDescriptor> descriptors) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        MultiMap<Type, ComponentDescriptor> multiMap = new MultiMap<>();
        for (ComponentDescriptor componentDescriptor : descriptors) {
            Iterator<Type> it = componentDescriptor.getRegistrations().iterator();
            while (it.hasNext()) {
                multiMap.putValue(it.next(), componentDescriptor);
            }
        }
        return multiMap;
    }

    public final void addAll(Collection<? extends ComponentDescriptor> descriptors) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        for (Map.Entry<Type, Collection<ComponentDescriptor>> entry : buildRegistrationMap(descriptors).entrySet()) {
            Object obj = this.registrationMap.get(entry.getKey());
            if (obj != null || entry.getValue().size() > 1) {
                ArrayList arrayList = new ArrayList();
                if (obj instanceof Collection) {
                    arrayList.addAll((Collection) obj);
                } else if (obj != null) {
                    arrayList.add((ComponentDescriptor) obj);
                }
                Collection<ComponentDescriptor> value = entry.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
                arrayList.addAll(value);
                HashMap<Type, Object> map = this.registrationMap;
                Type key = entry.getKey();
                Intrinsics.checkNotNullExpressionValue(key, "<get-key>(...)");
                ComponentDescriptor componentDescriptor = (ComponentDescriptor) CollectionsKt.singleOrNull((List) arrayList);
                Object obj2 = arrayList;
                if (componentDescriptor != null) {
                    obj2 = componentDescriptor;
                }
                map.put(key, obj2);
            } else {
                HashMap<Type, Object> map2 = this.registrationMap;
                Type key2 = entry.getKey();
                Intrinsics.checkNotNullExpressionValue(key2, "<get-key>(...)");
                Collection<ComponentDescriptor> value2 = entry.getValue();
                Intrinsics.checkNotNullExpressionValue(value2, "<get-value>(...)");
                Object objSingle = CollectionsKt.single(value2);
                Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
                map2.put(key2, objSingle);
            }
        }
    }

    public final Collection<ComponentDescriptor> tryGetEntry(Type request) {
        Intrinsics.checkNotNullParameter(request, "request");
        Object obj = this.registrationMap.get(request);
        if (obj instanceof Collection) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.container.ComponentDescriptor>");
            return (Collection) obj;
        }
        if (obj == null) {
            return CollectionsKt.emptyList();
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.container.ComponentDescriptor");
        return CollectionsKt.listOf((ComponentDescriptor) obj);
    }

    public final void addAll(ComponentRegistry other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (!this.registrationMap.isEmpty()) {
            throw new IllegalStateException("Can only copy entries from another component registry into an empty component registry");
        }
        this.registrationMap.putAll(other.registrationMap);
    }

    public final void resolveClashesIfAny(ComponentContainer container, List<? extends PlatformExtensionsClashResolver<?>> clashResolvers) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(clashResolvers, "clashResolvers");
        for (PlatformExtensionsClashResolver<?> platformExtensionsClashResolver : clashResolvers) {
            Object obj = this.registrationMap.get(platformExtensionsClashResolver.getApplicableTo());
            Collection collection = obj instanceof Collection ? (Collection) obj : null;
            if (collection != null && collection.size() > 1) {
                this.registrationMap.put(platformExtensionsClashResolver.getApplicableTo(), new ClashResolutionDescriptor(container, platformExtensionsClashResolver, CollectionsKt.toList(collection)));
            }
        }
    }
}
