package org.jetbrains.kotlin.daemon.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import org.jetbrains.kotlin.incremental.components.LookupInfo;
import org.jetbrains.kotlin.incremental.js.JsInlineFunctionHash;
import org.jetbrains.kotlin.load.kotlin.incremental.components.JvmPackagePartProto;
import org.jetbrains.kotlin.modules.TargetId;

/* JADX INFO: compiled from: CompilerCallbackServicesFacade.kt */
/* JADX INFO: loaded from: classes3.dex */
@Deprecated(message = "The usages should be replaced with `compile` method and `CompilerServicesFacadeBase` implementations", replaceWith = @ReplaceWith(expression = "CompilerServicesFacadeBase", imports = {}))
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H&J\u0018\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007H&J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\rH&J\b\u0010\u000f\u001a\u00020\rH&J\b\u0010\u0010\u001a\u00020\rH&J\b\u0010\u0011\u001a\u00020\rH&J\b\u0010\u0012\u001a\u00020\rH&J\b\u0010\u0013\u001a\u00020\rH&J\b\u0010\u0014\u001a\u00020\rH&J\b\u0010\u0015\u001a\u00020\rH&J\u0018\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u0007H&J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0018\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u0007H&J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001a\u001a\u00020\u001bH&J \u0010 \u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010!2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u0007H&J\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00070!2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070!2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u001a\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010'\u001a\u00020\u0007H&J(\u0010(\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\u0007H&J\u000e\u0010,\u001a\b\u0012\u0004\u0012\u00020-0!H&J\b\u0010.\u001a\u00020\u001fH&J\b\u0010/\u001a\u000200H&J\u000e\u00101\u001a\b\u0012\u0004\u0012\u0002020!H&J\u0010\u00103\u001a\u00020\u00052\u0006\u00104\u001a\u00020\u001fH&J\u0016\u00105\u001a\u00020\u00052\f\u00106\u001a\b\u0012\u0004\u0012\u0002070!H&J\u0018\u00108\u001a\u00020\u00052\u0006\u00109\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\u001fH&J(\u0010;\u001a\u00020\u00052\u0006\u0010<\u001a\u00020\u00072\u0006\u0010=\u001a\u00020\u001f2\u0006\u0010>\u001a\u00020\u001f2\u0006\u0010?\u001a\u00020\u001fH&J(\u0010@\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010A\u001a\u00020\u00072\u0006\u0010B\u001a\u00020\u00072\u0006\u0010C\u001a\u00020\u0007H&J\b\u0010D\u001a\u00020\rH&J\u0016\u0010E\u001a\u00020\u00052\f\u0010F\u001a\b\u0012\u0004\u0012\u00020G0!H&J\b\u0010H\u001a\u00020\rH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006IÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "Ljava/rmi/Remote;", "compilationCanceledStatus_checkCanceled", "Ljava/lang/Void;", "enumWhenTracker_report", "", "whenUsageClassPath", "", "enumClassFqName", "expectActualTracker_report", "expectedFilePath", "actualFilePath", "hasCompilationCanceledStatus", "", "hasEnumWhenTracker", "hasExpectActualTracker", "hasImportTracker", "hasIncrementalCaches", "hasIncrementalDataProvider", "hasIncrementalResultsConsumer", "hasInlineConstTracker", "hasLookupTracker", "importTracker_report", "filePath", "importedFqName", "incrementalCache_close", "target", "Lorg/jetbrains/kotlin/modules/TargetId;", "incrementalCache_getClassFilePath", "internalClassName", "incrementalCache_getModuleMappingData", "", "incrementalCache_getMultifileFacadeParts", "", "internalName", "incrementalCache_getObsoleteMultifileClassFacades", "incrementalCache_getObsoletePackageParts", "incrementalCache_getPackagePartData", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/JvmPackagePartProto;", "partInternalName", "incrementalCache_registerInline", "fromPath", "jvmSignature", "toPath", "incrementalDataProvider_getCompiledPackageParts", "Lorg/jetbrains/kotlin/daemon/common/CompiledPackagePart;", "incrementalDataProvider_getHeaderMetadata", "incrementalDataProvider_getMetadataVersion", "", "incrementalDataProvider_getPackageMetadata", "Lorg/jetbrains/kotlin/daemon/common/PackageMetadata;", "incrementalResultsConsumer_processHeader", "headerMetadata", "incrementalResultsConsumer_processInlineFunctions", "functions", "Lorg/jetbrains/kotlin/incremental/js/JsInlineFunctionHash;", "incrementalResultsConsumer_processPackageMetadata", "packageName", "metadata", "incrementalResultsConsumer_processPackagePart", "sourceFilePath", "packagePartMetadata", "binaryAst", "inlineData", "inlineConstTracker_report", "owner", "name", "constType", "lookupTracker_isDoNothing", "lookupTracker_record", "lookups", "Lorg/jetbrains/kotlin/incremental/components/LookupInfo;", "lookupTracker_requiresPosition", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompilerCallbackServicesFacade extends Remote {
    Void compilationCanceledStatus_checkCanceled() throws RmiFriendlyCompilationCanceledException, RemoteException;

    void enumWhenTracker_report(String whenUsageClassPath, String enumClassFqName) throws RemoteException;

    void expectActualTracker_report(String expectedFilePath, String actualFilePath) throws RemoteException;

    boolean hasCompilationCanceledStatus() throws RemoteException;

    boolean hasEnumWhenTracker() throws RemoteException;

    boolean hasExpectActualTracker() throws RemoteException;

    boolean hasImportTracker() throws RemoteException;

    boolean hasIncrementalCaches() throws RemoteException;

    boolean hasIncrementalDataProvider() throws RemoteException;

    boolean hasIncrementalResultsConsumer() throws RemoteException;

    boolean hasInlineConstTracker() throws RemoteException;

    boolean hasLookupTracker() throws RemoteException;

    void importTracker_report(String filePath, String importedFqName) throws RemoteException;

    void incrementalCache_close(TargetId target) throws RemoteException;

    String incrementalCache_getClassFilePath(TargetId target, String internalClassName) throws RemoteException;

    byte[] incrementalCache_getModuleMappingData(TargetId target) throws RemoteException;

    Collection<String> incrementalCache_getMultifileFacadeParts(TargetId target, String internalName) throws RemoteException;

    Collection<String> incrementalCache_getObsoleteMultifileClassFacades(TargetId target) throws RemoteException;

    Collection<String> incrementalCache_getObsoletePackageParts(TargetId target) throws RemoteException;

    JvmPackagePartProto incrementalCache_getPackagePartData(TargetId target, String partInternalName) throws RemoteException;

    void incrementalCache_registerInline(TargetId target, String fromPath, String jvmSignature, String toPath) throws RemoteException;

    Collection<CompiledPackagePart> incrementalDataProvider_getCompiledPackageParts() throws RemoteException;

    byte[] incrementalDataProvider_getHeaderMetadata() throws RemoteException;

    int[] incrementalDataProvider_getMetadataVersion() throws RemoteException;

    Collection<PackageMetadata> incrementalDataProvider_getPackageMetadata() throws RemoteException;

    void incrementalResultsConsumer_processHeader(byte[] headerMetadata) throws RemoteException;

    void incrementalResultsConsumer_processInlineFunctions(Collection<JsInlineFunctionHash> functions) throws RemoteException;

    void incrementalResultsConsumer_processPackageMetadata(String packageName, byte[] metadata) throws RemoteException;

    void incrementalResultsConsumer_processPackagePart(String sourceFilePath, byte[] packagePartMetadata, byte[] binaryAst, byte[] inlineData) throws RemoteException;

    void inlineConstTracker_report(String filePath, String owner, String name2, String constType) throws RemoteException;

    boolean lookupTracker_isDoNothing() throws RemoteException;

    void lookupTracker_record(Collection<LookupInfo> lookups) throws RemoteException;

    boolean lookupTracker_requiresPosition() throws RemoteException;
}
