package org.jetbrains.kotlin.daemon;

import java.io.File;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.CompiledPackagePart;
import org.jetbrains.kotlin.daemon.common.CompilerCallbackServicesFacade;
import org.jetbrains.kotlin.daemon.common.PackageMetadata;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.IrTranslationResultValue;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;

/* JADX INFO: compiled from: RemoteIncrementalDataProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R \u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R \u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00100\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR \u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001d0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\f¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteIncrementalDataProvider;", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "facade", "Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "rpcProfiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;Lorg/jetbrains/kotlin/daemon/common/Profiler;)V", "compiledPackageParts", "", "Ljava/io/File;", "Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "getCompiledPackageParts", "()Ljava/util/Map;", "getFacade", "()Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "headerMetadata", "", "getHeaderMetadata", "()[B", "metadataVersion", "", "getMetadataVersion", "()[I", "packageMetadata", "", "getPackageMetadata", "getRpcProfiler", "()Lorg/jetbrains/kotlin/daemon/common/Profiler;", "serializedIrFiles", "Lorg/jetbrains/kotlin/incremental/js/IrTranslationResultValue;", "getSerializedIrFiles", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteIncrementalDataProvider implements IncrementalDataProvider {
    private final CompilerCallbackServicesFacade facade;
    private final Profiler rpcProfiler;

    public RemoteIncrementalDataProvider(CompilerCallbackServicesFacade facade, Profiler rpcProfiler) {
        Intrinsics.checkNotNullParameter(facade, "facade");
        Intrinsics.checkNotNullParameter(rpcProfiler, "rpcProfiler");
        this.facade = facade;
        this.rpcProfiler = rpcProfiler;
    }

    public final CompilerCallbackServicesFacade getFacade() {
        return this.facade;
    }

    public final Profiler getRpcProfiler() {
        return this.rpcProfiler;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<File, IrTranslationResultValue> getSerializedIrFiles() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public byte[] getHeaderMetadata() throws RemoteException {
        Profiler profiler = this.rpcProfiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        byte[] bArrIncrementalDataProvider_getHeaderMetadata = this.facade.incrementalDataProvider_getHeaderMetadata();
        profiler.endMeasure(this, listBeginMeasure);
        return bArrIncrementalDataProvider_getHeaderMetadata;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<File, TranslationResultValue> getCompiledPackageParts() {
        Profiler profiler = this.rpcProfiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (CompiledPackagePart compiledPackagePart : this.facade.incrementalDataProvider_getCompiledPackageParts()) {
            if (((TranslationResultValue) linkedHashMap.put(new File(compiledPackagePart.getFilePath()), new TranslationResultValue(compiledPackagePart.getMetadata(), compiledPackagePart.getBinaryAst(), compiledPackagePart.getInlineData()))) != null) {
                throw new IllegalStateException(("compiledPackageParts: duplicated entry for file `" + compiledPackagePart.getFilePath() + '`').toString());
            }
        }
        profiler.endMeasure(this, listBeginMeasure);
        return linkedHashMap;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public int[] getMetadataVersion() throws RemoteException {
        Profiler profiler = this.rpcProfiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        int[] iArrIncrementalDataProvider_getMetadataVersion = this.facade.incrementalDataProvider_getMetadataVersion();
        profiler.endMeasure(this, listBeginMeasure);
        return iArrIncrementalDataProvider_getMetadataVersion;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<String, byte[]> getPackageMetadata() {
        Profiler profiler = this.rpcProfiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (PackageMetadata packageMetadata : this.facade.incrementalDataProvider_getPackageMetadata()) {
            if (((byte[]) linkedHashMap.put(packageMetadata.getPackageName(), packageMetadata.getMetadata())) != null) {
                throw new IllegalStateException(("packageMetadata: duplicated entry for package `" + packageMetadata.getPackageName() + '`').toString());
            }
        }
        profiler.endMeasure(this, listBeginMeasure);
        return linkedHashMap;
    }
}
