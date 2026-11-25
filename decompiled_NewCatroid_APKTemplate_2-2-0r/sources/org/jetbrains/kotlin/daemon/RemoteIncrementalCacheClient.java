package org.jetbrains.kotlin.daemon;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.CompilerCallbackServicesFacade;
import org.jetbrains.kotlin.daemon.common.DummyProfiler;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.load.kotlin.incremental.components.JvmPackagePartProto;
import org.jetbrains.kotlin.modules.TargetId;

/* JADX INFO: compiled from: RemoteIncrementalCacheClient.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u0017H\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u0017H\u0016J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u0012H\u0016J\u0018\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00172\u0006\u0010\u001d\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteIncrementalCacheClient;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "facade", "Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "target", "Lorg/jetbrains/kotlin/modules/TargetId;", "profiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;Lorg/jetbrains/kotlin/modules/TargetId;Lorg/jetbrains/kotlin/daemon/common/Profiler;)V", "getFacade", "()Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "getProfiler", "()Lorg/jetbrains/kotlin/daemon/common/Profiler;", "getTarget", "()Lorg/jetbrains/kotlin/modules/TargetId;", "close", "", "getClassFilePath", "", "internalClassName", "getModuleMappingData", "", "getObsoleteMultifileClasses", "", "getObsoletePackageParts", "getPackagePartData", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/JvmPackagePartProto;", "partInternalName", "getStableMultifileFacadeParts", "facadeInternalName", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteIncrementalCacheClient implements IncrementalCache {
    private final CompilerCallbackServicesFacade facade;
    private final Profiler profiler;
    private final TargetId target;

    public RemoteIncrementalCacheClient(CompilerCallbackServicesFacade facade, TargetId target, Profiler profiler) {
        Intrinsics.checkNotNullParameter(facade, "facade");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(profiler, "profiler");
        this.facade = facade;
        this.target = target;
        this.profiler = profiler;
    }

    public final CompilerCallbackServicesFacade getFacade() {
        return this.facade;
    }

    public final TargetId getTarget() {
        return this.target;
    }

    public /* synthetic */ RemoteIncrementalCacheClient(CompilerCallbackServicesFacade compilerCallbackServicesFacade, TargetId targetId, DummyProfiler dummyProfiler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerCallbackServicesFacade, targetId, (i & 4) != 0 ? new DummyProfiler() : dummyProfiler);
    }

    public final Profiler getProfiler() {
        return this.profiler;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public Collection<String> getObsoletePackageParts() throws RemoteException {
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        Collection<String> collectionIncrementalCache_getObsoletePackageParts = this.facade.incrementalCache_getObsoletePackageParts(this.target);
        profiler.endMeasure(this, listBeginMeasure);
        return collectionIncrementalCache_getObsoletePackageParts;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public Collection<String> getObsoleteMultifileClasses() throws RemoteException {
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        Collection<String> collectionIncrementalCache_getObsoleteMultifileClassFacades = this.facade.incrementalCache_getObsoleteMultifileClassFacades(this.target);
        profiler.endMeasure(this, listBeginMeasure);
        return collectionIncrementalCache_getObsoleteMultifileClassFacades;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public Collection<String> getStableMultifileFacadeParts(String facadeInternalName) throws RemoteException {
        Intrinsics.checkNotNullParameter(facadeInternalName, "facadeInternalName");
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        Collection<String> collectionIncrementalCache_getMultifileFacadeParts = this.facade.incrementalCache_getMultifileFacadeParts(this.target, facadeInternalName);
        profiler.endMeasure(this, listBeginMeasure);
        return collectionIncrementalCache_getMultifileFacadeParts;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public JvmPackagePartProto getPackagePartData(String partInternalName) throws RemoteException {
        Intrinsics.checkNotNullParameter(partInternalName, "partInternalName");
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        JvmPackagePartProto jvmPackagePartProtoIncrementalCache_getPackagePartData = this.facade.incrementalCache_getPackagePartData(this.target, partInternalName);
        profiler.endMeasure(this, listBeginMeasure);
        return jvmPackagePartProtoIncrementalCache_getPackagePartData;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public byte[] getModuleMappingData() throws RemoteException {
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        byte[] bArrIncrementalCache_getModuleMappingData = this.facade.incrementalCache_getModuleMappingData(this.target);
        profiler.endMeasure(this, listBeginMeasure);
        return bArrIncrementalCache_getModuleMappingData;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public String getClassFilePath(String internalClassName) throws RemoteException {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        String strIncrementalCache_getClassFilePath = this.facade.incrementalCache_getClassFilePath(this.target, internalClassName);
        profiler.endMeasure(this, listBeginMeasure);
        return strIncrementalCache_getClassFilePath;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public void close() throws RemoteException {
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        this.facade.incrementalCache_close(this.target);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
    }
}
