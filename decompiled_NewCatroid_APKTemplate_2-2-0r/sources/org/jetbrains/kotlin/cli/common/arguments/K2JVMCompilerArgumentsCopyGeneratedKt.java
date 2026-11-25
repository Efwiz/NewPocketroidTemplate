package org.jetbrains.kotlin.cli.common.arguments;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: K2JVMCompilerArgumentsCopyGenerated.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"copyK2JVMCompilerArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", TypedValues.TransitionType.S_FROM, "to", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2JVMCompilerArgumentsCopyGeneratedKt {
    public static final K2JVMCompilerArguments copyK2JVMCompilerArguments(K2JVMCompilerArguments from, K2JVMCompilerArguments to) {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        String[] strArr4;
        String[] strArr5;
        String[] strArr6;
        String[] strArr7;
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        CommonCompilerArgumentsCopyGeneratedKt.copyCommonCompilerArguments(from, to);
        to.setAbiStability(from.getAbiStability());
        String[] additionalJavaModules = from.getAdditionalJavaModules();
        String[] strArr8 = null;
        if (additionalJavaModules != null) {
            Object[] objArrCopyOf = Arrays.copyOf(additionalJavaModules, additionalJavaModules.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
            strArr = (String[]) objArrCopyOf;
        } else {
            strArr = null;
        }
        to.setAdditionalJavaModules(strArr);
        to.setAllowNoSourceFiles(from.getAllowNoSourceFiles());
        to.setAllowUnstableDependencies(from.getAllowUnstableDependencies());
        to.setAssertionsMode(from.getAssertionsMode());
        to.setBackendThreads(from.getBackendThreads());
        to.setBuildFile(from.getBuildFile());
        to.setClasspath(from.getClasspath());
        to.setCompileJava(from.getCompileJava());
        to.setDeclarationsOutputPath(from.getDeclarationsOutputPath());
        to.setDefaultScriptExtension(from.getDefaultScriptExtension());
        to.setDestination(from.getDestination());
        to.setDisableStandardScript(from.getDisableStandardScript());
        to.setDoNotClearBindingContext(from.getDoNotClearBindingContext());
        to.setEmitJvmTypeAnnotations(from.getEmitJvmTypeAnnotations());
        to.setEnableDebugMode(from.getEnableDebugMode());
        to.setEnableIrInliner(from.getEnableIrInliner());
        to.setEnableJvmPreview(from.getEnableJvmPreview());
        to.setEnhanceTypeParameterTypesToDefNotNull(from.getEnhanceTypeParameterTypesToDefNotNull());
        to.setExpression(from.getExpression());
        String[] friendPaths = from.getFriendPaths();
        if (friendPaths != null) {
            Object[] objArrCopyOf2 = Arrays.copyOf(friendPaths, friendPaths.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf2, "copyOf(...)");
            strArr2 = (String[]) objArrCopyOf2;
        } else {
            strArr2 = null;
        }
        to.setFriendPaths(strArr2);
        to.setIncludeRuntime(from.getIncludeRuntime());
        to.setInheritMultifileParts(from.getInheritMultifileParts());
        to.setJavaModulePath(from.getJavaModulePath());
        to.setJavaPackagePrefix(from.getJavaPackagePrefix());
        to.setJavaParameters(from.getJavaParameters());
        String[] javaSourceRoots = from.getJavaSourceRoots();
        if (javaSourceRoots != null) {
            Object[] objArrCopyOf3 = Arrays.copyOf(javaSourceRoots, javaSourceRoots.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf3, "copyOf(...)");
            strArr3 = (String[]) objArrCopyOf3;
        } else {
            strArr3 = null;
        }
        to.setJavaSourceRoots(strArr3);
        String[] javacArguments = from.getJavacArguments();
        if (javacArguments != null) {
            Object[] objArrCopyOf4 = Arrays.copyOf(javacArguments, javacArguments.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf4, "copyOf(...)");
            strArr4 = (String[]) objArrCopyOf4;
        } else {
            strArr4 = null;
        }
        to.setJavacArguments(strArr4);
        to.setJdkHome(from.getJdkHome());
        to.setJdkRelease(from.getJdkRelease());
        to.setJspecifyAnnotations(from.getJspecifyAnnotations());
        String[] jsr305 = from.getJsr305();
        if (jsr305 != null) {
            Object[] objArrCopyOf5 = Arrays.copyOf(jsr305, jsr305.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf5, "copyOf(...)");
            strArr5 = (String[]) objArrCopyOf5;
        } else {
            strArr5 = null;
        }
        to.setJsr305(strArr5);
        to.setJvmDefault(from.getJvmDefault());
        to.setJvmTarget(from.getJvmTarget());
        to.setKlibLibraries(from.getKlibLibraries());
        to.setLambdas(from.getLambdas());
        to.setLinkViaSignatures(from.getLinkViaSignatures());
        to.setModuleName(from.getModuleName());
        to.setNoCallAssertions(from.getNoCallAssertions());
        to.setNoJdk(from.getNoJdk());
        to.setNoKotlinNothingValueException(from.getNoKotlinNothingValueException());
        to.setNoNewJavaAnnotationTargets(from.getNoNewJavaAnnotationTargets());
        to.setNoOptimize(from.getNoOptimize());
        to.setNoOptimizedCallableReferences(from.getNoOptimizedCallableReferences());
        to.setNoParamAssertions(from.getNoParamAssertions());
        to.setNoReceiverAssertions(from.getNoReceiverAssertions());
        to.setNoReflect(from.getNoReflect());
        to.setNoResetJarTimestamps(from.getNoResetJarTimestamps());
        to.setNoSourceDebugExtension(from.getNoSourceDebugExtension());
        to.setNoStdlib(from.getNoStdlib());
        to.setNoUnifiedNullChecks(from.getNoUnifiedNullChecks());
        String[] nullabilityAnnotations = from.getNullabilityAnnotations();
        if (nullabilityAnnotations != null) {
            Object[] objArrCopyOf6 = Arrays.copyOf(nullabilityAnnotations, nullabilityAnnotations.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf6, "copyOf(...)");
            strArr6 = (String[]) objArrCopyOf6;
        } else {
            strArr6 = null;
        }
        to.setNullabilityAnnotations(strArr6);
        to.setOldInnerClassesLogic(from.getOldInnerClassesLogic());
        to.setProfileCompilerCommand(from.getProfileCompilerCommand());
        to.setRepeatCompileModules(from.getRepeatCompileModules());
        to.setSamConversions(from.getSamConversions());
        to.setSanitizeParentheses(from.getSanitizeParentheses());
        String[] scriptResolverEnvironment = from.getScriptResolverEnvironment();
        if (scriptResolverEnvironment != null) {
            Object[] objArrCopyOf7 = Arrays.copyOf(scriptResolverEnvironment, scriptResolverEnvironment.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf7, "copyOf(...)");
            strArr7 = (String[]) objArrCopyOf7;
        } else {
            strArr7 = null;
        }
        to.setScriptResolverEnvironment(strArr7);
        String[] scriptTemplates = from.getScriptTemplates();
        if (scriptTemplates != null) {
            Object[] objArrCopyOf8 = Arrays.copyOf(scriptTemplates, scriptTemplates.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf8, "copyOf(...)");
            strArr8 = (String[]) objArrCopyOf8;
        }
        to.setScriptTemplates(strArr8);
        to.setSerializeIr(from.getSerializeIr());
        to.setStrictMetadataVersionSemantics(from.getStrictMetadataVersionSemantics());
        to.setStringConcat(from.getStringConcat());
        to.setSupportCompatqualCheckerFrameworkAnnotations(from.getSupportCompatqualCheckerFrameworkAnnotations());
        to.setSuppressDeprecatedJvmTargetWarning(from.getSuppressDeprecatedJvmTargetWarning());
        to.setSuppressMissingBuiltinsError(from.getSuppressMissingBuiltinsError());
        to.setTypeEnhancementImprovementsInStrictMode(from.getTypeEnhancementImprovementsInStrictMode());
        to.setUseFastJarFileSystem(from.getUseFastJarFileSystem());
        to.setUseJavac(from.getUseJavac());
        to.setUseKapt4(from.getUseKapt4());
        to.setUseOldBackend(from.getUseOldBackend());
        to.setUseOldClassFilesReading(from.getUseOldClassFilesReading());
        to.setUseOldInlineClassesManglingScheme(from.getUseOldInlineClassesManglingScheme());
        to.setUseTypeTable(from.getUseTypeTable());
        to.setValidateBytecode(from.getValidateBytecode());
        to.setValidateIr(from.getValidateIr());
        to.setValueClasses(from.getValueClasses());
        return to;
    }
}
