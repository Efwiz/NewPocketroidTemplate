package com.google.mlkit.common.sdkinternal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentDiscovery;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.ComponentRuntime;
import com.google.mlkit.common.internal.MlKitComponentDiscoveryService;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes10.dex */
public class MlKitContext {
    private static final AtomicReference<MlKitContext> zza = new AtomicReference<>();
    private ComponentRuntime zzb;

    private MlKitContext() {
    }

    public static MlKitContext getInstance() {
        MlKitContext mlKitContext = zza.get();
        Preconditions.checkState(mlKitContext != null, "MlKitContext has not been initialized");
        return mlKitContext;
    }

    public static MlKitContext initialize(Context context, List<ComponentRegistrar> list) {
        MlKitContext mlKitContext = new MlKitContext();
        ComponentRuntime componentRuntime = new ComponentRuntime(TaskExecutors.MAIN_THREAD, list, Component.of(zzb(context), (Class<Context>) Context.class, (Class<? super Context>[]) new Class[0]), Component.of(mlKitContext, (Class<MlKitContext>) MlKitContext.class, (Class<? super MlKitContext>[]) new Class[0]));
        mlKitContext.zzb = componentRuntime;
        componentRuntime.initializeEagerComponents(true);
        Preconditions.checkState(zza.getAndSet(mlKitContext) == null, "MlKitContext is already initialized");
        return mlKitContext;
    }

    public static MlKitContext zza(Context context) {
        MlKitContext mlKitContext = new MlKitContext();
        Context contextZzb = zzb(context);
        ComponentRuntime componentRuntimeBuild = ComponentRuntime.builder(TaskExecutors.MAIN_THREAD).addLazyComponentRegistrars(ComponentDiscovery.forContext(contextZzb, MlKitComponentDiscoveryService.class).discoverLazy()).addComponent(Component.of(contextZzb, (Class<Context>) Context.class, (Class<? super Context>[]) new Class[0])).addComponent(Component.of(mlKitContext, (Class<MlKitContext>) MlKitContext.class, (Class<? super MlKitContext>[]) new Class[0])).build();
        mlKitContext.zzb = componentRuntimeBuild;
        componentRuntimeBuild.initializeEagerComponents(true);
        Preconditions.checkState(zza.getAndSet(mlKitContext) == null, "MlKitContext is already initialized");
        return mlKitContext;
    }

    private static Context zzb(Context context) {
        Context applicationContext = context.getApplicationContext();
        return applicationContext != null ? applicationContext : context;
    }

    public <T> T get(Class<T> cls) {
        Preconditions.checkState(zza.get() == this, "MlKitContext has been deleted");
        Preconditions.checkNotNull(this.zzb);
        return (T) this.zzb.get(cls);
    }

    public Context getApplicationContext() {
        return (Context) get(Context.class);
    }
}
