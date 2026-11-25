package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMAssertionsMode;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmAbiStability;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmClosureGenerationScheme;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.JvmStringConcat;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;

/* JADX INFO: compiled from: JvmBackendConfig.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u0017\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\u0019\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\u001b\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\u001d\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0014R\u0011\u0010\u001e\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014R\u0011\u0010\u001f\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0014R\u0011\u0010 \u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0014R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020&¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0011\u0010)\u001a\u00020*¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010-\u001a\u00020.¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u00101\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0014R\u0011\u00103\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0014R\u0011\u00105\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0014R\u0011\u00107\u001a\u000208¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010;\u001a\u00020&¢\u0006\b\n\u0000\u001a\u0004\b<\u0010(R\u0011\u0010=\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\u0014R\u0011\u0010?\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\u0014R\u0011\u0010A\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0014R\u0011\u0010C\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\u0014R\u0011\u0010E\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\u0014R\u0011\u0010G\u001a\u00020H¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0011\u0010K\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\u0014R\u0011\u0010M\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bN\u0010\u0014R\u0011\u0010O\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bP\u0010\u0014R\u0011\u0010Q\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bR\u0010\u0014¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/JvmBackendConfig;", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "abiStability", "Lorg/jetbrains/kotlin/config/JvmAbiStability;", "getAbiStability", "()Lorg/jetbrains/kotlin/config/JvmAbiStability;", "assertionsMode", "Lorg/jetbrains/kotlin/config/JVMAssertionsMode;", "getAssertionsMode", "()Lorg/jetbrains/kotlin/config/JVMAssertionsMode;", "classFileVersion", "", "getClassFileVersion", "()I", "disableOptimization", "", "getDisableOptimization", "()Z", "functionsWithInlineClassReturnTypesMangled", "getFunctionsWithInlineClassReturnTypesMangled", "generateOptimizedCallableReferenceSuperClasses", "getGenerateOptimizedCallableReferenceSuperClasses", "generateParametersMetadata", "getGenerateParametersMetadata", "generateSmapCopyToAnnotation", "getGenerateSmapCopyToAnnotation", "isCallAssertionsDisabled", "isInlineDisabled", "isParamAssertionsDisabled", "isReceiverAssertionsDisabled", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "getJvmDefaultMode", "()Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "lambdasScheme", "Lorg/jetbrains/kotlin/config/JvmClosureGenerationScheme;", "getLambdasScheme", "()Lorg/jetbrains/kotlin/config/JvmClosureGenerationScheme;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "noNewJavaAnnotationTargets", "getNoNewJavaAnnotationTargets", "noSourceCodeInNotNullAssertionExceptions", "getNoSourceCodeInNotNullAssertionExceptions", "oldInnerClassesLogic", "getOldInnerClassesLogic", "runtimeStringConcat", "Lorg/jetbrains/kotlin/config/JvmStringConcat;", "getRuntimeStringConcat", "()Lorg/jetbrains/kotlin/config/JvmStringConcat;", "samConversionsScheme", "getSamConversionsScheme", "shouldInlineConstVals", "getShouldInlineConstVals", "shouldValidateBytecode", "getShouldValidateBytecode", "shouldValidateIr", "getShouldValidateIr", "stableTypeOf", "getStableTypeOf", "supportMultiFieldValueClasses", "getSupportMultiFieldValueClasses", "target", "Lorg/jetbrains/kotlin/config/JvmTarget;", "getTarget", "()Lorg/jetbrains/kotlin/config/JvmTarget;", "unifiedNullChecks", "getUnifiedNullChecks", "useKotlinNothingValueException", "getUseKotlinNothingValueException", "useOldManglingSchemeForFunctionsWithInlineClassesInSignatures", "getUseOldManglingSchemeForFunctionsWithInlineClassesInSignatures", "useTypeTableInSerializer", "getUseTypeTableInSerializer", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBackendConfig {
    private final JvmAbiStability abiStability;
    private final JVMAssertionsMode assertionsMode;
    private final int classFileVersion;
    private final boolean disableOptimization;
    private final boolean functionsWithInlineClassReturnTypesMangled;
    private final boolean generateOptimizedCallableReferenceSuperClasses;
    private final boolean generateParametersMetadata;
    private final boolean generateSmapCopyToAnnotation;
    private final boolean isCallAssertionsDisabled;
    private final boolean isInlineDisabled;
    private final boolean isParamAssertionsDisabled;
    private final boolean isReceiverAssertionsDisabled;
    private final JvmDefaultMode jvmDefaultMode;
    private final JvmClosureGenerationScheme lambdasScheme;
    private final LanguageVersionSettings languageVersionSettings;
    private final BinaryVersion metadataVersion;
    private final boolean noNewJavaAnnotationTargets;
    private final boolean noSourceCodeInNotNullAssertionExceptions;
    private final boolean oldInnerClassesLogic;
    private final JvmStringConcat runtimeStringConcat;
    private final JvmClosureGenerationScheme samConversionsScheme;
    private final boolean shouldInlineConstVals;
    private final boolean shouldValidateBytecode;
    private final boolean shouldValidateIr;
    private final boolean stableTypeOf;
    private final boolean supportMultiFieldValueClasses;
    private final JvmTarget target;
    private final boolean unifiedNullChecks;
    private final boolean useKotlinNothingValueException;
    private final boolean useOldManglingSchemeForFunctionsWithInlineClassesInSignatures;
    private final boolean useTypeTableInSerializer;

    /* JADX WARN: Code duplicated, block: B:13:0x003a  */
    public JvmBackendConfig(CompilerConfiguration configuration) {
        boolean z;
        JvmStringConcat jvmStringConcat;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
        this.languageVersionSettings = languageVersionSettings;
        JvmTarget jvmTarget = (JvmTarget) configuration.get(JVMConfigurationKeys.JVM_TARGET);
        jvmTarget = jvmTarget == null ? JvmTarget.DEFAULT : jvmTarget;
        this.target = jvmTarget;
        if (configuration.getBoolean(JVMConfigurationKeys.USE_OLD_INLINE_CLASSES_MANGLING_SCHEME)) {
            z = true;
        } else {
            LanguageVersion languageVersion = languageVersionSettings.getLanguageVersion();
            if (languageVersion.getMajor() != 1 || languageVersion.getMinor() >= 4) {
                z = false;
            } else {
                z = true;
            }
        }
        this.useOldManglingSchemeForFunctionsWithInlineClassesInSignatures = z;
        if (jvmTarget.getMajorVersion() >= JvmTarget.JVM_9.getMajorVersion()) {
            jvmStringConcat = (JvmStringConcat) configuration.get(JVMConfigurationKeys.STRING_CONCAT);
            jvmStringConcat = jvmStringConcat == null ? JvmStringConcat.INDY_WITH_CONSTANTS : jvmStringConcat;
            Intrinsics.checkNotNull(jvmStringConcat);
        } else {
            jvmStringConcat = JvmStringConcat.INLINE;
        }
        this.runtimeStringConcat = jvmStringConcat;
        JvmClosureGenerationScheme jvmClosureGenerationScheme = (JvmClosureGenerationScheme) configuration.get(JVMConfigurationKeys.SAM_CONVERSIONS);
        if (jvmClosureGenerationScheme == null) {
            if (languageVersionSettings.supportsFeature(LanguageFeature.SamWrapperClassesAreSynthetic)) {
                jvmClosureGenerationScheme = JvmClosureGenerationScheme.INDY;
            } else {
                jvmClosureGenerationScheme = JvmClosureGenerationScheme.CLASS;
            }
        }
        this.samConversionsScheme = jvmClosureGenerationScheme;
        JvmClosureGenerationScheme jvmClosureGenerationScheme2 = (JvmClosureGenerationScheme) configuration.get(JVMConfigurationKeys.LAMBDAS);
        if (jvmClosureGenerationScheme2 == null) {
            if (languageVersionSettings.supportsFeature(LanguageFeature.LightweightLambdas)) {
                jvmClosureGenerationScheme2 = JvmClosureGenerationScheme.INDY;
            } else {
                jvmClosureGenerationScheme2 = JvmClosureGenerationScheme.CLASS;
            }
        }
        this.lambdasScheme = jvmClosureGenerationScheme2;
        this.useKotlinNothingValueException = languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_4) >= 0 && !configuration.getBoolean(JVMConfigurationKeys.NO_KOTLIN_NOTHING_VALUE_EXCEPTION);
        this.stableTypeOf = languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_6) >= 0;
        this.generateOptimizedCallableReferenceSuperClasses = languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_4) >= 0 && !configuration.getBoolean(JVMConfigurationKeys.NO_OPTIMIZED_CALLABLE_REFERENCES);
        this.isCallAssertionsDisabled = configuration.getBoolean(JVMConfigurationKeys.DISABLE_CALL_ASSERTIONS);
        this.isReceiverAssertionsDisabled = configuration.getBoolean(JVMConfigurationKeys.DISABLE_RECEIVER_ASSERTIONS) || !languageVersionSettings.supportsFeature(LanguageFeature.NullabilityAssertionOnExtensionReceiver);
        this.isParamAssertionsDisabled = configuration.getBoolean(JVMConfigurationKeys.DISABLE_PARAM_ASSERTIONS);
        Object obj = configuration.get(JVMConfigurationKeys.ASSERTIONS_MODE, JVMAssertionsMode.DEFAULT);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        this.assertionsMode = (JVMAssertionsMode) obj;
        this.isInlineDisabled = configuration.getBoolean(CommonConfigurationKeys.DISABLE_INLINE);
        this.useTypeTableInSerializer = configuration.getBoolean(JVMConfigurationKeys.USE_TYPE_TABLE);
        boolean z2 = languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_4) >= 0 && !configuration.getBoolean(JVMConfigurationKeys.NO_UNIFIED_NULL_CHECKS);
        this.unifiedNullChecks = z2;
        this.noSourceCodeInNotNullAssertionExceptions = (languageVersionSettings.supportsFeature(LanguageFeature.NoSourceCodeInNotNullAssertionExceptions) && z2) || languageVersionSettings.getLanguageVersion().getUsesK2();
        this.generateSmapCopyToAnnotation = !configuration.getBoolean(JVMConfigurationKeys.NO_SOURCE_DEBUG_EXTENSION);
        this.functionsWithInlineClassReturnTypesMangled = languageVersionSettings.supportsFeature(LanguageFeature.MangleClassMembersReturningInlineClasses);
        this.shouldValidateIr = configuration.getBoolean(JVMConfigurationKeys.VALIDATE_IR);
        this.shouldValidateBytecode = configuration.getBoolean(JVMConfigurationKeys.VALIDATE_BYTECODE);
        this.classFileVersion = ((configuration.getBoolean(JVMConfigurationKeys.ENABLE_JVM_PREVIEW) ? 65535 : 0) << 16) + this.target.getMajorVersion();
        this.generateParametersMetadata = configuration.getBoolean(JVMConfigurationKeys.PARAMETERS_METADATA);
        this.shouldInlineConstVals = languageVersionSettings.supportsFeature(LanguageFeature.InlineConstVals);
        this.jvmDefaultMode = (JvmDefaultMode) languageVersionSettings.getFlag(JvmAnalysisFlags.getJvmDefaultMode());
        this.disableOptimization = configuration.getBoolean(JVMConfigurationKeys.DISABLE_OPTIMIZATION);
        this.metadataVersion = MetadataHelpersKt.metadataVersion$default(configuration, null, 1, null);
        this.abiStability = (JvmAbiStability) configuration.get(JVMConfigurationKeys.ABI_STABILITY);
        this.noNewJavaAnnotationTargets = configuration.getBoolean(JVMConfigurationKeys.NO_NEW_JAVA_ANNOTATION_TARGETS);
        this.oldInnerClassesLogic = configuration.getBoolean(JVMConfigurationKeys.OLD_INNER_CLASSES_LOGIC);
        this.supportMultiFieldValueClasses = languageVersionSettings.supportsFeature(LanguageFeature.ValueClasses);
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final JvmTarget getTarget() {
        return this.target;
    }

    public final boolean getUseOldManglingSchemeForFunctionsWithInlineClassesInSignatures() {
        return this.useOldManglingSchemeForFunctionsWithInlineClassesInSignatures;
    }

    public final JvmStringConcat getRuntimeStringConcat() {
        return this.runtimeStringConcat;
    }

    public final JvmClosureGenerationScheme getSamConversionsScheme() {
        return this.samConversionsScheme;
    }

    public final JvmClosureGenerationScheme getLambdasScheme() {
        return this.lambdasScheme;
    }

    public final boolean getUseKotlinNothingValueException() {
        return this.useKotlinNothingValueException;
    }

    public final boolean getStableTypeOf() {
        return this.stableTypeOf;
    }

    public final boolean getGenerateOptimizedCallableReferenceSuperClasses() {
        return this.generateOptimizedCallableReferenceSuperClasses;
    }

    /* JADX INFO: renamed from: isCallAssertionsDisabled, reason: from getter */
    public final boolean getIsCallAssertionsDisabled() {
        return this.isCallAssertionsDisabled;
    }

    /* JADX INFO: renamed from: isReceiverAssertionsDisabled, reason: from getter */
    public final boolean getIsReceiverAssertionsDisabled() {
        return this.isReceiverAssertionsDisabled;
    }

    /* JADX INFO: renamed from: isParamAssertionsDisabled, reason: from getter */
    public final boolean getIsParamAssertionsDisabled() {
        return this.isParamAssertionsDisabled;
    }

    public final JVMAssertionsMode getAssertionsMode() {
        return this.assertionsMode;
    }

    /* JADX INFO: renamed from: isInlineDisabled, reason: from getter */
    public final boolean getIsInlineDisabled() {
        return this.isInlineDisabled;
    }

    public final boolean getUseTypeTableInSerializer() {
        return this.useTypeTableInSerializer;
    }

    public final boolean getUnifiedNullChecks() {
        return this.unifiedNullChecks;
    }

    public final boolean getNoSourceCodeInNotNullAssertionExceptions() {
        return this.noSourceCodeInNotNullAssertionExceptions;
    }

    public final boolean getGenerateSmapCopyToAnnotation() {
        return this.generateSmapCopyToAnnotation;
    }

    public final boolean getFunctionsWithInlineClassReturnTypesMangled() {
        return this.functionsWithInlineClassReturnTypesMangled;
    }

    public final boolean getShouldValidateIr() {
        return this.shouldValidateIr;
    }

    public final boolean getShouldValidateBytecode() {
        return this.shouldValidateBytecode;
    }

    public final int getClassFileVersion() {
        return this.classFileVersion;
    }

    public final boolean getGenerateParametersMetadata() {
        return this.generateParametersMetadata;
    }

    public final boolean getShouldInlineConstVals() {
        return this.shouldInlineConstVals;
    }

    public final JvmDefaultMode getJvmDefaultMode() {
        return this.jvmDefaultMode;
    }

    public final boolean getDisableOptimization() {
        return this.disableOptimization;
    }

    public final BinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    public final JvmAbiStability getAbiStability() {
        return this.abiStability;
    }

    public final boolean getNoNewJavaAnnotationTargets() {
        return this.noNewJavaAnnotationTargets;
    }

    public final boolean getOldInnerClassesLogic() {
        return this.oldInnerClassesLogic;
    }

    public final boolean getSupportMultiFieldValueClasses() {
        return this.supportMultiFieldValueClasses;
    }
}
