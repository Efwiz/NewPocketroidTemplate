package org.jetbrains.kotlin.fir.extensions;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.util.ConeTypeRegistry;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.ComponentArrayOwner;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirExtensionService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001a2\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00030\u00012\u00020\u0004:\u0001\u001aB\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003H\u0007J*\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00172\u0010\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190\u0003H\u0007R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR&\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00030\u00108TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;", "Lorg/jetbrains/kotlin/util/ComponentArrayOwner;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "<set-?>", "", "registeredExtensionsSize", "getRegisteredExtensionsSize", "()I", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "typeRegistry", "Lorg/jetbrains/kotlin/util/TypeRegistry;", "getTypeRegistry", "()Lorg/jetbrains/kotlin/util/TypeRegistry;", "getAllExtensions", "registerExtensions", "", "extensionClass", "Lkotlin/reflect/KClass;", "extensionFactories", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExtensionService extends ComponentArrayOwner<FirExtension, List<? extends FirExtension>> implements FirSessionComponent {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int registeredExtensionsSize;
    private final FirSession session;

    /* JADX INFO: compiled from: FirExtensionService.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J=\u0010\u0005\u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u0003\u0012\u0004\u0012\u0002H\u00070\u0006\"\n\b\u0000\u0010\b\u0018\u0001*\u00020\u0002\"\u000e\b\u0001\u0010\u0007*\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0086\bJF\u0010\u0005\u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u0003\u0012\u0004\u0012\u0002H\u00070\u0006\"\b\b\u0000\u0010\b*\u00020\u0002\"\u000e\b\u0001\u0010\u0007*\b\u0012\u0004\u0012\u0002H\b0\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService$Companion;", "Lorg/jetbrains/kotlin/fir/util/ConeTypeRegistry;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "", "()V", "registeredExtensions", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "P", "kClass", "Lkotlin/reflect/KClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ConeTypeRegistry<FirExtension, List<? extends FirExtension>> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final /* synthetic */ <P extends FirExtension, V extends List<? extends P>> ArrayMapAccessor<FirExtension, List<FirExtension>, V> registeredExtensions() {
            Intrinsics.reifiedOperationMarker(4, "P");
            KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(FirExtension.class);
            List listEmptyList = CollectionsKt.emptyList();
            Intrinsics.checkNotNull(listEmptyList, "null cannot be cast to non-null type V of org.jetbrains.kotlin.fir.extensions.FirExtensionService.Companion.registeredExtensions");
            return (ArrayMapAccessor<FirExtension, List<FirExtension>, V>) generateAccessor(orCreateKotlinClass, listEmptyList);
        }

        public final <P extends FirExtension, V extends List<? extends P>> ArrayMapAccessor<FirExtension, List<FirExtension>, V> registeredExtensions(KClass<P> kClass) {
            Intrinsics.checkNotNullParameter(kClass, "kClass");
            List listEmptyList = CollectionsKt.emptyList();
            Intrinsics.checkNotNull(listEmptyList, "null cannot be cast to non-null type V of org.jetbrains.kotlin.fir.extensions.FirExtensionService.Companion.registeredExtensions");
            return (ArrayMapAccessor<FirExtension, List<FirExtension>, V>) generateAccessor(kClass, listEmptyList);
        }
    }

    public FirExtensionService(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected TypeRegistry<FirExtension, List<FirExtension>> getTypeRegistry() {
        return INSTANCE;
    }

    public final int getRegisteredExtensionsSize() {
        return this.registeredExtensionsSize;
    }

    @PluginServicesInitialization
    public final void registerExtensions(KClass<? extends FirExtension> extensionClass, List<? extends FirExtension.Factory<?>> extensionFactories) {
        Intrinsics.checkNotNullParameter(extensionClass, "extensionClass");
        Intrinsics.checkNotNullParameter(extensionFactories, "extensionFactories");
        this.registeredExtensionsSize += extensionFactories.size();
        List<? extends FirExtension.Factory<?>> list = extensionFactories;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirExtension.Factory) it.next()).create(this.session));
        }
        registerComponent(extensionClass, arrayList);
    }

    @PluginServicesInitialization
    public final List<FirExtension> getAllExtensions() {
        return CollectionsKt.flatten(getArrayMap());
    }
}
