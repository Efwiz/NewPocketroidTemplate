package org.jetbrains.kotlin.cli.common.arguments;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: K2NativeCompilerArgumentsCopyGenerated.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"copyK2NativeCompilerArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2NativeCompilerArguments;", TypedValues.TransitionType.S_FROM, "to", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2NativeCompilerArgumentsCopyGeneratedKt {
    public static final K2NativeCompilerArguments copyK2NativeCompilerArguments(K2NativeCompilerArguments from, K2NativeCompilerArguments to) {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        String[] strArr4;
        String[] strArr5;
        String[] strArr6;
        String[] strArr7;
        String[] strArr8;
        String[] strArr9;
        String[] strArr10;
        String[] strArr11;
        String[] strArr12;
        String[] strArr13;
        String[] strArr14;
        String[] strArr15;
        String[] strArr16;
        String[] strArr17;
        String[] strArr18;
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        CommonCompilerArgumentsCopyGeneratedKt.copyCommonCompilerArguments(from, to);
        to.setAllocator(from.getAllocator());
        to.setAutoCacheDir(from.getAutoCacheDir());
        String[] autoCacheableFrom = from.getAutoCacheableFrom();
        String[] strArr19 = null;
        if (autoCacheableFrom != null) {
            Object[] objArrCopyOf = Arrays.copyOf(autoCacheableFrom, autoCacheableFrom.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
            strArr = (String[]) objArrCopyOf;
        } else {
            strArr = null;
        }
        to.setAutoCacheableFrom(strArr);
        to.setBackendThreads(from.getBackendThreads());
        String[] binaryOptions = from.getBinaryOptions();
        if (binaryOptions != null) {
            Object[] objArrCopyOf2 = Arrays.copyOf(binaryOptions, binaryOptions.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf2, "copyOf(...)");
            strArr2 = (String[]) objArrCopyOf2;
        } else {
            strArr2 = null;
        }
        to.setBinaryOptions(strArr2);
        to.setBundleId(from.getBundleId());
        String[] cacheDirectories = from.getCacheDirectories();
        if (cacheDirectories != null) {
            Object[] objArrCopyOf3 = Arrays.copyOf(cacheDirectories, cacheDirectories.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf3, "copyOf(...)");
            strArr3 = (String[]) objArrCopyOf3;
        } else {
            strArr3 = null;
        }
        to.setCacheDirectories(strArr3);
        String[] cachedLibraries = from.getCachedLibraries();
        if (cachedLibraries != null) {
            Object[] objArrCopyOf4 = Arrays.copyOf(cachedLibraries, cachedLibraries.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf4, "copyOf(...)");
            strArr4 = (String[]) objArrCopyOf4;
        } else {
            strArr4 = null;
        }
        to.setCachedLibraries(strArr4);
        to.setCheckDependencies(from.getCheckDependencies());
        to.setCheckExternalCalls(from.getCheckExternalCalls());
        String[] clangOptions = from.getClangOptions();
        if (clangOptions != null) {
            Object[] objArrCopyOf5 = Arrays.copyOf(clangOptions, clangOptions.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf5, "copyOf(...)");
            strArr5 = (String[]) objArrCopyOf5;
        } else {
            strArr5 = null;
        }
        to.setClangOptions(strArr5);
        to.setCompileFromBitcode(from.getCompileFromBitcode());
        to.setCoverage(from.getCoverage());
        to.setCoverageFile(from.getCoverageFile());
        String[] coveredLibraries = from.getCoveredLibraries();
        if (coveredLibraries != null) {
            Object[] objArrCopyOf6 = Arrays.copyOf(coveredLibraries, coveredLibraries.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf6, "copyOf(...)");
            strArr6 = (String[]) objArrCopyOf6;
        } else {
            strArr6 = null;
        }
        to.setCoveredLibraries(strArr6);
        to.setDebug(from.getDebug());
        to.setDebugInfoFormatVersion(from.getDebugInfoFormatVersion());
        String[] debugPrefixMap = from.getDebugPrefixMap();
        if (debugPrefixMap != null) {
            Object[] objArrCopyOf7 = Arrays.copyOf(debugPrefixMap, debugPrefixMap.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf7, "copyOf(...)");
            strArr7 = (String[]) objArrCopyOf7;
        } else {
            strArr7 = null;
        }
        to.setDebugPrefixMap(strArr7);
        to.setDestroyRuntimeMode(from.getDestroyRuntimeMode());
        to.setEmbedBitcode(from.getEmbedBitcode());
        to.setEmbedBitcodeMarker(from.getEmbedBitcodeMarker());
        to.setEmitLazyObjCHeader(from.getEmitLazyObjCHeader());
        to.setEnableAssertions(from.getEnableAssertions());
        to.setExportKDoc(from.getExportKDoc());
        String[] exportedLibraries = from.getExportedLibraries();
        if (exportedLibraries != null) {
            Object[] objArrCopyOf8 = Arrays.copyOf(exportedLibraries, exportedLibraries.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf8, "copyOf(...)");
            strArr8 = (String[]) objArrCopyOf8;
        } else {
            strArr8 = null;
        }
        to.setExportedLibraries(strArr8);
        to.setExternalDependencies(from.getExternalDependencies());
        to.setFakeOverrideValidator(from.getFakeOverrideValidator());
        String[] filesToCache = from.getFilesToCache();
        if (filesToCache != null) {
            Object[] objArrCopyOf9 = Arrays.copyOf(filesToCache, filesToCache.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf9, "copyOf(...)");
            strArr9 = (String[]) objArrCopyOf9;
        } else {
            strArr9 = null;
        }
        to.setFilesToCache(strArr9);
        String[] frameworkImportHeaders = from.getFrameworkImportHeaders();
        if (frameworkImportHeaders != null) {
            Object[] objArrCopyOf10 = Arrays.copyOf(frameworkImportHeaders, frameworkImportHeaders.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf10, "copyOf(...)");
            strArr10 = (String[]) objArrCopyOf10;
        } else {
            strArr10 = null;
        }
        to.setFrameworkImportHeaders(strArr10);
        to.setFriendModules(from.getFriendModules());
        to.setGc(from.getGc());
        to.setGenerateDebugTrampolineString(from.getGenerateDebugTrampolineString());
        to.setGenerateNoExitTestRunner(from.getGenerateNoExitTestRunner());
        to.setGenerateTestRunner(from.getGenerateTestRunner());
        to.setGenerateWorkerTestRunner(from.getGenerateWorkerTestRunner());
        String[] includeBinaries = from.getIncludeBinaries();
        if (includeBinaries != null) {
            Object[] objArrCopyOf11 = Arrays.copyOf(includeBinaries, includeBinaries.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf11, "copyOf(...)");
            strArr11 = (String[]) objArrCopyOf11;
        } else {
            strArr11 = null;
        }
        to.setIncludeBinaries(strArr11);
        String[] includes = from.getIncludes();
        if (includes != null) {
            Object[] objArrCopyOf12 = Arrays.copyOf(includes, includes.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf12, "copyOf(...)");
            strArr12 = (String[]) objArrCopyOf12;
        } else {
            strArr12 = null;
        }
        to.setIncludes(strArr12);
        to.setIncrementalCacheDir(from.getIncrementalCacheDir());
        to.setKonanDataDir(from.getKonanDataDir());
        to.setLazyIrForCaches(from.getLazyIrForCaches());
        String[] libraries = from.getLibraries();
        if (libraries != null) {
            Object[] objArrCopyOf13 = Arrays.copyOf(libraries, libraries.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf13, "copyOf(...)");
            strArr13 = (String[]) objArrCopyOf13;
        } else {
            strArr13 = null;
        }
        to.setLibraries(strArr13);
        to.setLibraryToAddToCache(from.getLibraryToAddToCache());
        to.setLibraryVersion(from.getLibraryVersion());
        to.setLightDebugDeprecated(from.getLightDebugDeprecated());
        to.setLightDebugString(from.getLightDebugString());
        String[] linkerArguments = from.getLinkerArguments();
        if (linkerArguments != null) {
            Object[] objArrCopyOf14 = Arrays.copyOf(linkerArguments, linkerArguments.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf14, "copyOf(...)");
            strArr14 = (String[]) objArrCopyOf14;
        } else {
            strArr14 = null;
        }
        to.setLinkerArguments(strArr14);
        to.setListTargets(from.getListTargets());
        to.setLlvmVariant(from.getLlvmVariant());
        to.setMainPackage(from.getMainPackage());
        to.setMakePerFileCache(from.getMakePerFileCache());
        to.setManifestFile(from.getManifestFile());
        to.setMemoryModel(from.getMemoryModel());
        to.setMetadataKlib(from.getMetadataKlib());
        to.setModuleName(from.getModuleName());
        String[] nativeLibraries = from.getNativeLibraries();
        if (nativeLibraries != null) {
            Object[] objArrCopyOf15 = Arrays.copyOf(nativeLibraries, nativeLibraries.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf15, "copyOf(...)");
            strArr15 = (String[]) objArrCopyOf15;
        } else {
            strArr15 = null;
        }
        to.setNativeLibraries(strArr15);
        to.setNoObjcGenerics(from.getNoObjcGenerics());
        to.setNodefaultlibs(from.getNodefaultlibs());
        to.setNoendorsedlibs(from.getNoendorsedlibs());
        to.setNomain(from.getNomain());
        to.setNopack(from.getNopack());
        to.setNostdlib(from.getNostdlib());
        to.setOmitFrameworkBinary(from.getOmitFrameworkBinary());
        to.setOptimization(from.getOptimization());
        to.setOutputName(from.getOutputName());
        String[] overrideKonanProperties = from.getOverrideKonanProperties();
        if (overrideKonanProperties != null) {
            Object[] objArrCopyOf16 = Arrays.copyOf(overrideKonanProperties, overrideKonanProperties.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf16, "copyOf(...)");
            strArr16 = (String[]) objArrCopyOf16;
        } else {
            strArr16 = null;
        }
        to.setOverrideKonanProperties(strArr16);
        to.setPartialLinkageLogLevel(from.getPartialLinkageLogLevel());
        to.setPartialLinkageMode(from.getPartialLinkageMode());
        to.setPreLinkCaches(from.getPreLinkCaches());
        to.setPrintBitCode(from.getPrintBitCode());
        to.setPrintFiles(from.getPrintFiles());
        to.setPrintIr(from.getPrintIr());
        to.setProduce(from.getProduce());
        to.setPropertyLazyInitialization(from.getPropertyLazyInitialization());
        to.setPurgeUserLibs(from.getPurgeUserLibs());
        String[] refinesPaths = from.getRefinesPaths();
        if (refinesPaths != null) {
            Object[] objArrCopyOf17 = Arrays.copyOf(refinesPaths, refinesPaths.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf17, "copyOf(...)");
            strArr17 = (String[]) objArrCopyOf17;
        } else {
            strArr17 = null;
        }
        to.setRefinesPaths(strArr17);
        String[] repositories = from.getRepositories();
        if (repositories != null) {
            Object[] objArrCopyOf18 = Arrays.copyOf(repositories, repositories.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf18, "copyOf(...)");
            strArr18 = (String[]) objArrCopyOf18;
        } else {
            strArr18 = null;
        }
        to.setRepositories(strArr18);
        to.setRuntimeFile(from.getRuntimeFile());
        to.setRuntimeLogs(from.getRuntimeLogs());
        to.setSaveDependenciesPath(from.getSaveDependenciesPath());
        String[] saveLlvmIrAfter = from.getSaveLlvmIrAfter();
        Object[] objArrCopyOf19 = Arrays.copyOf(saveLlvmIrAfter, saveLlvmIrAfter.length);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf19, "copyOf(...)");
        to.setSaveLlvmIrAfter((String[]) objArrCopyOf19);
        to.setSaveLlvmIrDirectory(from.getSaveLlvmIrDirectory());
        to.setSerializedDependencies(from.getSerializedDependencies());
        to.setShortModuleName(from.getShortModuleName());
        String[] singleLinkerArguments = from.getSingleLinkerArguments();
        if (singleLinkerArguments != null) {
            Object[] objArrCopyOf20 = Arrays.copyOf(singleLinkerArguments, singleLinkerArguments.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf20, "copyOf(...)");
            strArr19 = (String[]) objArrCopyOf20;
        }
        to.setSingleLinkerArguments(strArr19);
        to.setStaticFramework(from.getStaticFramework());
        to.setTarget(from.getTarget());
        to.setTemporaryFilesDir(from.getTemporaryFilesDir());
        to.setTestDumpOutputPath(from.getTestDumpOutputPath());
        to.setVerifyBitCode(from.getVerifyBitCode());
        to.setVerifyCompiler(from.getVerifyCompiler());
        to.setVerifyIr(from.getVerifyIr());
        to.setWorkerExceptionHandling(from.getWorkerExceptionHandling());
        return to;
    }
}
