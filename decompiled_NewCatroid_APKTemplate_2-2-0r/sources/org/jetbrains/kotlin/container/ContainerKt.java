package org.jetbrains.kotlin.container;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Container.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006\u001a%\u0010\u0007\u001a\u0004\u0018\u00010\b\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\u0003*\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0086\b\u001a)\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\r\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\u0003*\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0086\b¨\u0006\u000e"}, d2 = {"registerInstance", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "instance", "", "registerSingleton", "klass", "Ljava/lang/Class;", "resolve", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", ExifInterface.GPS_DIRECTION_TRUE, "context", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "resolveMultiple", "", "container"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContainerKt {
    public static final StorageComponentContainer registerSingleton(StorageComponentContainer storageComponentContainer, Class<?> klass) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        return storageComponentContainer.registerDescriptors$container(CollectionsKt.listOf(new SingletonTypeComponentDescriptor(storageComponentContainer, klass)));
    }

    public static final StorageComponentContainer registerInstance(StorageComponentContainer storageComponentContainer, Object instance) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(instance, "instance");
        return storageComponentContainer.registerDescriptors$container(CollectionsKt.listOf(new InstanceComponentDescriptor(instance)));
    }

    public static /* synthetic */ ValueDescriptor resolve$default(StorageComponentContainer storageComponentContainer, ValueResolveContext context, int i, Object obj) {
        if ((i & 1) != 0) {
            context = storageComponentContainer.getUnknownContext();
        }
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return storageComponentContainer.resolve(Object.class, context);
    }

    public static final /* synthetic */ <T> ValueDescriptor resolve(StorageComponentContainer storageComponentContainer, ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return storageComponentContainer.resolve(Object.class, context);
    }

    public static /* synthetic */ Iterable resolveMultiple$default(StorageComponentContainer storageComponentContainer, ValueResolveContext context, int i, Object obj) {
        if ((i & 1) != 0) {
            context = storageComponentContainer.getUnknownContext();
        }
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return storageComponentContainer.resolveMultiple(Object.class, context);
    }

    public static final /* synthetic */ <T> Iterable<ValueDescriptor> resolveMultiple(StorageComponentContainer storageComponentContainer, ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return storageComponentContainer.resolveMultiple(Object.class, context);
    }
}
