package org.jetbrains.kotlin.container;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: Dsl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a3\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\b\b\u001a\u001e\u0010\t\u001a\u0002H\n\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u000b*\u00020\fH\u0086\b¢\u0006\u0002\u0010\r\u001a'\u0010\u000e\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b*\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\n0\u0010¢\u0006\u0002\u0010\u0011\u001a4\u0010\u0012\u001a\u0002H\n\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u000b*\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0086\n¢\u0006\u0002\u0010\u0016\u001a)\u0010\u0017\u001a\u0004\u0018\u0001H\n\"\b\b\u0000\u0010\n*\u00020\u000b*\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\n0\u0010¢\u0006\u0002\u0010\u0011\u001a\u0016\u0010\u0018\u001a\u00020\u0007*\u00020\u00012\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a\u001a\u0019\u0010\u001b\u001a\u00020\u0007\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u000b*\u00020\u0001H\u0086\b\u001a!\u0010\u001c\u001a\u00020\u0007\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u000b*\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001eH\u0086\b\u001a\u0012\u0010\u001f\u001a\u00020\u0007*\u00020\u00012\u0006\u0010 \u001a\u00020\u000b\u001a\u0014\u0010!\u001a\u00020\u0007*\u00020\u00012\b\u0010 \u001a\u0004\u0018\u00010\u000b¨\u0006\""}, d2 = {"composeContainer", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "id", "", "parent", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "get", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/container/ComponentProvider;", "(Lorg/jetbrains/kotlin/container/ComponentProvider;)Ljava/lang/Object;", "getService", "request", "Ljava/lang/Class;", "(Lorg/jetbrains/kotlin/container/ComponentProvider;Ljava/lang/Class;)Ljava/lang/Object;", "getValue", "thisRef", "desc", "Lkotlin/reflect/KProperty;", "(Lorg/jetbrains/kotlin/container/ComponentProvider;Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "tryGetService", "useClashResolver", "clashResolver", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "useImpl", "useImplIf", "cond", "", "useInstance", "instance", "useInstanceIfNotNull", "container"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DslKt {
    public static /* synthetic */ StorageComponentContainer composeContainer$default(String str, StorageComponentContainer storageComponentContainer, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            storageComponentContainer = null;
        }
        return composeContainer(str, storageComponentContainer, function1);
    }

    public static final StorageComponentContainer composeContainer(String id2, StorageComponentContainer storageComponentContainer, Function1<? super StorageComponentContainer, Unit> init) throws Throwable {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(init, "init");
        StorageComponentContainer storageComponentContainer2 = new StorageComponentContainer(id2, storageComponentContainer);
        init.invoke(storageComponentContainer2);
        storageComponentContainer2.compose();
        return storageComponentContainer2;
    }

    public static final /* synthetic */ <T> void useImpl(StorageComponentContainer storageComponentContainer) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        ContainerKt.registerSingleton(storageComponentContainer, Object.class);
    }

    public static final /* synthetic */ <T> T get(ComponentProvider componentProvider) {
        Intrinsics.checkNotNullParameter(componentProvider, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) getService(componentProvider, Object.class);
    }

    public static final <T> T tryGetService(ComponentProvider componentProvider, Class<T> request) {
        Intrinsics.checkNotNullParameter(componentProvider, "<this>");
        Intrinsics.checkNotNullParameter(request, "request");
        ValueDescriptor valueDescriptorResolve = componentProvider.resolve(request);
        if (valueDescriptorResolve != null) {
            return (T) valueDescriptorResolve.getValue();
        }
        return null;
    }

    public static final <T> T getService(ComponentProvider componentProvider, Class<T> request) {
        Intrinsics.checkNotNullParameter(componentProvider, "<this>");
        Intrinsics.checkNotNullParameter(request, "request");
        T t = (T) tryGetService(componentProvider, request);
        if (t != null) {
            return t;
        }
        throw new UnresolvedServiceException(componentProvider, request);
    }

    public static final void useInstance(StorageComponentContainer storageComponentContainer, Object instance) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(instance, "instance");
        ContainerKt.registerInstance(storageComponentContainer, instance);
    }

    public static final void useInstanceIfNotNull(StorageComponentContainer storageComponentContainer, Object obj) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        if (obj != null) {
            ContainerKt.registerInstance(storageComponentContainer, obj);
        }
    }

    public static final void useClashResolver(StorageComponentContainer storageComponentContainer, PlatformExtensionsClashResolver<?> clashResolver) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(clashResolver, "clashResolver");
        storageComponentContainer.registerClashResolvers$container(CollectionsKt.listOf(clashResolver));
    }

    public static final /* synthetic */ <T> T getValue(ComponentProvider componentProvider, Object obj, KProperty<?> desc) {
        Intrinsics.checkNotNullParameter(componentProvider, "<this>");
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) getService(componentProvider, Object.class);
    }

    public static final /* synthetic */ <T> void useImplIf(StorageComponentContainer storageComponentContainer, boolean z) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        if (z) {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            ContainerKt.registerSingleton(storageComponentContainer, Object.class);
        }
    }
}
