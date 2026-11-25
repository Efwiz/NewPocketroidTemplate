package org.jetbrains.kotlin.cli.common.arguments;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonCompilerArgumentsCopyGenerated.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"copyCommonCompilerArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", TypedValues.TransitionType.S_FROM, "to", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonCompilerArgumentsCopyGeneratedKt {
    public static final CommonCompilerArguments copyCommonCompilerArguments(CommonCompilerArguments from, CommonCompilerArguments to) {
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
        CommonToolArgumentsCopyGeneratedKt.copyCommonToolArguments(from, to);
        to.setAllowAnyScriptsInSourceRoots(from.getAllowAnyScriptsInSourceRoots());
        to.setAllowKotlinPackage(from.getAllowKotlinPackage());
        to.setAllowResultReturnType(from.getAllowResultReturnType());
        to.setApiVersion(from.getApiVersion());
        to.setAutoAdvanceApiVersion(from.getAutoAdvanceApiVersion());
        to.setAutoAdvanceLanguageVersion(from.getAutoAdvanceLanguageVersion());
        to.setBuiltInsFromSources(from.getBuiltInsFromSources());
        to.setCheckPhaseConditions(from.getCheckPhaseConditions());
        to.setCheckStickyPhaseConditions(from.getCheckStickyPhaseConditions());
        String[] commonSources = from.getCommonSources();
        String[] strArr19 = null;
        if (commonSources != null) {
            Object[] objArrCopyOf = Arrays.copyOf(commonSources, commonSources.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
            strArr = (String[]) objArrCopyOf;
        } else {
            strArr = null;
        }
        to.setCommonSources(strArr);
        to.setContextReceivers(from.getContextReceivers());
        to.setDisableDefaultScriptingPlugin(from.getDisableDefaultScriptingPlugin());
        String[] disablePhases = from.getDisablePhases();
        if (disablePhases != null) {
            Object[] objArrCopyOf2 = Arrays.copyOf(disablePhases, disablePhases.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf2, "copyOf(...)");
            strArr2 = (String[]) objArrCopyOf2;
        } else {
            strArr2 = null;
        }
        to.setDisablePhases(strArr2);
        to.setDisableUltraLightClasses(from.getDisableUltraLightClasses());
        to.setDumpDirectory(from.getDumpDirectory());
        to.setDumpOnlyFqName(from.getDumpOnlyFqName());
        to.setDumpPerf(from.getDumpPerf());
        to.setEffectSystem(from.getEffectSystem());
        to.setEnableBuilderInference(from.getEnableBuilderInference());
        to.setEnableSignatureClashChecks(from.getEnableSignatureClashChecks());
        to.setExpectActualClasses(from.getExpectActualClasses());
        to.setExpectActualLinker(from.getExpectActualLinker());
        String[] experimental = from.getExperimental();
        if (experimental != null) {
            Object[] objArrCopyOf3 = Arrays.copyOf(experimental, experimental.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf3, "copyOf(...)");
            strArr3 = (String[]) objArrCopyOf3;
        } else {
            strArr3 = null;
        }
        to.setExperimental(strArr3);
        to.setExplicitApi(from.getExplicitApi());
        to.setExtendedCompilerChecks(from.getExtendedCompilerChecks());
        String[] fragmentRefines = from.getFragmentRefines();
        if (fragmentRefines != null) {
            Object[] objArrCopyOf4 = Arrays.copyOf(fragmentRefines, fragmentRefines.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf4, "copyOf(...)");
            strArr4 = (String[]) objArrCopyOf4;
        } else {
            strArr4 = null;
        }
        to.setFragmentRefines(strArr4);
        String[] fragmentSources = from.getFragmentSources();
        if (fragmentSources != null) {
            Object[] objArrCopyOf5 = Arrays.copyOf(fragmentSources, fragmentSources.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf5, "copyOf(...)");
            strArr5 = (String[]) objArrCopyOf5;
        } else {
            strArr5 = null;
        }
        to.setFragmentSources(strArr5);
        String[] fragments = from.getFragments();
        if (fragments != null) {
            Object[] objArrCopyOf6 = Arrays.copyOf(fragments, fragments.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf6, "copyOf(...)");
            strArr6 = (String[]) objArrCopyOf6;
        } else {
            strArr6 = null;
        }
        to.setFragments(strArr6);
        to.setIgnoreConstOptimizationErrors(from.getIgnoreConstOptimizationErrors());
        to.setIncrementalCompilation(from.getIncrementalCompilation());
        to.setInferenceCompatibility(from.getInferenceCompatibility());
        to.setInlineClasses(from.getInlineClasses());
        to.setIntellijPluginRoot(from.getIntellijPluginRoot());
        to.setKotlinHome(from.getKotlinHome());
        to.setLanguageVersion(from.getLanguageVersion());
        to.setLegacySmartCastAfterTry(from.getLegacySmartCastAfterTry());
        to.setListPhases(from.getListPhases());
        to.setMetadataVersion(from.getMetadataVersion());
        to.setMultiPlatform(from.getMultiPlatform());
        to.setNewInference(from.getNewInference());
        to.setNoCheckActual(from.getNoCheckActual());
        to.setNoInline(from.getNoInline());
        to.setNormalizeAbsolutePath(from.getNormalizeAbsolutePath());
        String[] optIn = from.getOptIn();
        if (optIn != null) {
            Object[] objArrCopyOf7 = Arrays.copyOf(optIn, optIn.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf7, "copyOf(...)");
            strArr7 = (String[]) objArrCopyOf7;
        } else {
            strArr7 = null;
        }
        to.setOptIn(strArr7);
        String[] phasesToDump = from.getPhasesToDump();
        if (phasesToDump != null) {
            Object[] objArrCopyOf8 = Arrays.copyOf(phasesToDump, phasesToDump.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf8, "copyOf(...)");
            strArr8 = (String[]) objArrCopyOf8;
        } else {
            strArr8 = null;
        }
        to.setPhasesToDump(strArr8);
        String[] phasesToDumpAfter = from.getPhasesToDumpAfter();
        if (phasesToDumpAfter != null) {
            Object[] objArrCopyOf9 = Arrays.copyOf(phasesToDumpAfter, phasesToDumpAfter.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf9, "copyOf(...)");
            strArr9 = (String[]) objArrCopyOf9;
        } else {
            strArr9 = null;
        }
        to.setPhasesToDumpAfter(strArr9);
        String[] phasesToDumpBefore = from.getPhasesToDumpBefore();
        if (phasesToDumpBefore != null) {
            Object[] objArrCopyOf10 = Arrays.copyOf(phasesToDumpBefore, phasesToDumpBefore.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf10, "copyOf(...)");
            strArr10 = (String[]) objArrCopyOf10;
        } else {
            strArr10 = null;
        }
        to.setPhasesToDumpBefore(strArr10);
        String[] phasesToValidate = from.getPhasesToValidate();
        if (phasesToValidate != null) {
            Object[] objArrCopyOf11 = Arrays.copyOf(phasesToValidate, phasesToValidate.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf11, "copyOf(...)");
            strArr11 = (String[]) objArrCopyOf11;
        } else {
            strArr11 = null;
        }
        to.setPhasesToValidate(strArr11);
        String[] phasesToValidateAfter = from.getPhasesToValidateAfter();
        if (phasesToValidateAfter != null) {
            Object[] objArrCopyOf12 = Arrays.copyOf(phasesToValidateAfter, phasesToValidateAfter.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf12, "copyOf(...)");
            strArr12 = (String[]) objArrCopyOf12;
        } else {
            strArr12 = null;
        }
        to.setPhasesToValidateAfter(strArr12);
        String[] phasesToValidateBefore = from.getPhasesToValidateBefore();
        if (phasesToValidateBefore != null) {
            Object[] objArrCopyOf13 = Arrays.copyOf(phasesToValidateBefore, phasesToValidateBefore.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf13, "copyOf(...)");
            strArr13 = (String[]) objArrCopyOf13;
        } else {
            strArr13 = null;
        }
        to.setPhasesToValidateBefore(strArr13);
        String[] pluginClasspaths = from.getPluginClasspaths();
        if (pluginClasspaths != null) {
            Object[] objArrCopyOf14 = Arrays.copyOf(pluginClasspaths, pluginClasspaths.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf14, "copyOf(...)");
            strArr14 = (String[]) objArrCopyOf14;
        } else {
            strArr14 = null;
        }
        to.setPluginClasspaths(strArr14);
        String[] pluginConfigurations = from.getPluginConfigurations();
        if (pluginConfigurations != null) {
            Object[] objArrCopyOf15 = Arrays.copyOf(pluginConfigurations, pluginConfigurations.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf15, "copyOf(...)");
            strArr15 = (String[]) objArrCopyOf15;
        } else {
            strArr15 = null;
        }
        to.setPluginConfigurations(strArr15);
        String[] pluginOptions = from.getPluginOptions();
        if (pluginOptions != null) {
            Object[] objArrCopyOf16 = Arrays.copyOf(pluginOptions, pluginOptions.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf16, "copyOf(...)");
            strArr16 = (String[]) objArrCopyOf16;
        } else {
            strArr16 = null;
        }
        to.setPluginOptions(strArr16);
        to.setProfilePhases(from.getProfilePhases());
        to.setProgressiveMode(from.getProgressiveMode());
        to.setProperIeee754Comparisons(from.getProperIeee754Comparisons());
        to.setReadDeserializedContracts(from.getReadDeserializedContracts());
        String[] relativePathBases = from.getRelativePathBases();
        if (relativePathBases != null) {
            Object[] objArrCopyOf17 = Arrays.copyOf(relativePathBases, relativePathBases.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf17, "copyOf(...)");
            strArr17 = (String[]) objArrCopyOf17;
        } else {
            strArr17 = null;
        }
        to.setRelativePathBases(strArr17);
        to.setRenderInternalDiagnosticNames(from.getRenderInternalDiagnosticNames());
        to.setReportOutputFiles(from.getReportOutputFiles());
        to.setReportPerf(from.getReportPerf());
        to.setScript(from.getScript());
        to.setSelfUpperBoundInference(from.getSelfUpperBoundInference());
        to.setSkipMetadataVersionCheck(from.getSkipMetadataVersionCheck());
        to.setSkipPrereleaseCheck(from.getSkipPrereleaseCheck());
        to.setSuppressVersionWarnings(from.getSuppressVersionWarnings());
        to.setUnrestrictedBuilderInference(from.getUnrestrictedBuilderInference());
        String[] useExperimental = from.getUseExperimental();
        if (useExperimental != null) {
            Object[] objArrCopyOf18 = Arrays.copyOf(useExperimental, useExperimental.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf18, "copyOf(...)");
            strArr18 = (String[]) objArrCopyOf18;
        } else {
            strArr18 = null;
        }
        to.setUseExperimental(strArr18);
        to.setUseFirExtendedCheckers(from.getUseFirExtendedCheckers());
        to.setUseFirIC(from.getUseFirIC());
        to.setUseFirLT(from.getUseFirLT());
        to.setUseK2(from.getUseK2());
        to.setUseMixedNamedArguments(from.getUseMixedNamedArguments());
        String[] verbosePhases = from.getVerbosePhases();
        if (verbosePhases != null) {
            Object[] objArrCopyOf19 = Arrays.copyOf(verbosePhases, verbosePhases.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf19, "copyOf(...)");
            strArr19 = (String[]) objArrCopyOf19;
        }
        to.setVerbosePhases(strArr19);
        return to;
    }
}
