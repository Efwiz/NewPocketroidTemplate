package org.jetbrains.kotlin.cli.common.arguments;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: K2JSCompilerArgumentsCopyGenerated.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"copyK2JSCompilerArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", TypedValues.TransitionType.S_FROM, "to", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2JSCompilerArgumentsCopyGeneratedKt {
    public static final K2JSCompilerArguments copyK2JSCompilerArguments(K2JSCompilerArguments from, K2JSCompilerArguments to) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        CommonCompilerArgumentsCopyGeneratedKt.copyCommonCompilerArguments(from, to);
        to.setCacheDirectory(from.getCacheDirectory());
        to.setEnableJsScripting(from.getEnableJsScripting());
        to.setErrorTolerancePolicy(from.getErrorTolerancePolicy());
        to.setExtensionFunctionsInExternals(from.getExtensionFunctionsInExternals());
        to.setFakeOverrideValidator(from.getFakeOverrideValidator());
        to.setForceDeprecatedLegacyCompilerUsage(from.getForceDeprecatedLegacyCompilerUsage());
        to.setFriendModules(from.getFriendModules());
        to.setFriendModulesDisabled(from.getFriendModulesDisabled());
        to.setGenerateDts(from.getGenerateDts());
        to.setGeneratePolyfills(from.getGeneratePolyfills());
        to.setIncludes(from.getIncludes());
        to.setIrBaseClassInMetadata(from.getIrBaseClassInMetadata());
        to.setIrBuildCache(from.getIrBuildCache());
        to.setIrDce(from.getIrDce());
        to.setIrDceDumpDeclarationIrSizesToFile(from.getIrDceDumpDeclarationIrSizesToFile());
        to.setIrDceDumpReachabilityInfoToFile(from.getIrDceDumpReachabilityInfoToFile());
        to.setIrDcePrintReachabilityInfo(from.getIrDcePrintReachabilityInfo());
        to.setIrDceRuntimeDiagnostic(from.getIrDceRuntimeDiagnostic());
        to.setIrGenerateInlineAnonymousFunctions(from.getIrGenerateInlineAnonymousFunctions());
        to.setIrKeep(from.getIrKeep());
        to.setIrMinimizedMemberNames(from.getIrMinimizedMemberNames());
        to.setIrModuleName(from.getIrModuleName());
        to.setIrNewIr2Js(from.getIrNewIr2Js());
        to.setIrOnly(from.getIrOnly());
        to.setIrPerFile(from.getIrPerFile());
        to.setIrPerModule(from.getIrPerModule());
        to.setIrPerModuleOutputName(from.getIrPerModuleOutputName());
        to.setIrProduceJs(from.getIrProduceJs());
        to.setIrProduceKlibDir(from.getIrProduceKlibDir());
        to.setIrProduceKlibFile(from.getIrProduceKlibFile());
        to.setIrPropertyLazyInitialization(from.getIrPropertyLazyInitialization());
        to.setIrSafeExternalBoolean(from.getIrSafeExternalBoolean());
        to.setIrSafeExternalBooleanDiagnostic(from.getIrSafeExternalBooleanDiagnostic());
        to.setLibraries(from.getLibraries());
        to.setMain(from.getMain());
        to.setMetaInfo(from.getMetaInfo());
        to.setMetadataOnly(from.getMetadataOnly());
        to.setModuleKind(from.getModuleKind());
        to.setModuleName(from.getModuleName());
        to.setNoStdlib(from.getNoStdlib());
        to.setOptimizeGeneratedJs(from.getOptimizeGeneratedJs());
        to.setOutputDir(from.getOutputDir());
        to.setOutputFile(from.getOutputFile());
        to.setOutputPostfix(from.getOutputPostfix());
        to.setOutputPrefix(from.getOutputPrefix());
        to.setPartialLinkageLogLevel(from.getPartialLinkageLogLevel());
        to.setPartialLinkageMode(from.getPartialLinkageMode());
        to.setSourceMap(from.getSourceMap());
        to.setSourceMapBaseDirs(from.getSourceMapBaseDirs());
        to.setSourceMapEmbedSources(from.getSourceMapEmbedSources());
        to.setSourceMapNamesPolicy(from.getSourceMapNamesPolicy());
        to.setSourceMapPrefix(from.getSourceMapPrefix());
        to.setStrictImplicitExportType(from.getStrictImplicitExportType());
        to.setTarget(from.getTarget());
        to.setTypedArrays(from.getTypedArrays());
        to.setUseEsClasses(from.getUseEsClasses());
        to.setWasm(from.getWasm());
        to.setWasmDebug(from.getWasmDebug());
        to.setWasmEnableArrayRangeChecks(from.getWasmEnableArrayRangeChecks());
        to.setWasmEnableAsserts(from.getWasmEnableAsserts());
        to.setWasmGenerateWat(from.getWasmGenerateWat());
        to.setWasmKClassFqn(from.getWasmKClassFqn());
        to.setWasmTarget(from.getWasmTarget());
        to.setWasmUseTrapsInsteadOfExceptions(from.getWasmUseTrapsInsteadOfExceptions());
        return to;
    }
}
