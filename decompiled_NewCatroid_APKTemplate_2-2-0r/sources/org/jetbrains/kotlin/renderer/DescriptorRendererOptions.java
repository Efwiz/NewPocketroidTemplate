package org.jetbrains.kotlin.renderer;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0017\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\fX¦\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R&\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0012X¦\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0018\u0010\u0018\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0019\u0010\u0005\"\u0004\b\u001a\u0010\u0007R\u0018\u0010\u001b\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u001c\u0010\u0005\"\u0004\b\u001d\u0010\u0007R\u0018\u0010\u001e\u001a\u00020\u001fX¦\u000e¢\u0006\f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0018\u0010$\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010\u0005\"\u0004\b&\u0010\u0007R&\u0010'\u001a\u0010\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020)\u0018\u00010\u0012X¦\u000e¢\u0006\f\u001a\u0004\b*\u0010\u0015\"\u0004\b+\u0010\u0017R\u0018\u0010,\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b-\u0010\u0005\"\u0004\b.\u0010\u0007R\u0018\u0010/\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b0\u0010\u0005\"\u0004\b1\u0010\u0007R\u001e\u00102\u001a\b\u0012\u0004\u0012\u00020403X¦\u000e¢\u0006\f\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001e\u00109\u001a\b\u0012\u0004\u0012\u00020403X¦\u000e¢\u0006\f\u001a\u0004\b:\u00106\"\u0004\b;\u00108R\u0018\u0010<\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b=\u0010\u0005\"\u0004\b>\u0010\u0007R\u0014\u0010?\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u0010\u0005R\u0014\u0010A\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010\u0005R\u0018\u0010C\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\bD\u0010\u0005\"\u0004\bE\u0010\u0007R\u0018\u0010F\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\bG\u0010\u0005\"\u0004\bH\u0010\u0007R\u001e\u0010I\u001a\b\u0012\u0004\u0012\u00020J03X¦\u000e¢\u0006\f\u001a\u0004\bK\u00106\"\u0004\bL\u00108R\u0018\u0010M\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\bN\u0010\u0005\"\u0004\bO\u0010\u0007R\u0018\u0010P\u001a\u00020QX¦\u000e¢\u0006\f\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR\u0018\u0010V\u001a\u00020WX¦\u000e¢\u0006\f\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R\u0018\u0010\\\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b]\u0010\u0005\"\u0004\b^\u0010\u0007R\u0018\u0010_\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b`\u0010\u0005\"\u0004\ba\u0010\u0007R\u0018\u0010b\u001a\u00020cX¦\u000e¢\u0006\f\u001a\u0004\bd\u0010e\"\u0004\bf\u0010gR,\u0010h\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030i\u0012\u0006\u0012\u0004\u0018\u00010)\u0018\u00010\u0012X¦\u000e¢\u0006\f\u001a\u0004\bj\u0010\u0015\"\u0004\bk\u0010\u0017R\u0018\u0010l\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\bm\u0010\u0005\"\u0004\bn\u0010\u0007R\u0018\u0010o\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\bp\u0010\u0005\"\u0004\bq\u0010\u0007R\u0018\u0010r\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\bs\u0010\u0005\"\u0004\bt\u0010\u0007R\u0018\u0010u\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\bv\u0010\u0005\"\u0004\bw\u0010\u0007R\u0018\u0010x\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\by\u0010\u0005\"\u0004\bz\u0010\u0007R\u0018\u0010{\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b|\u0010\u0005\"\u0004\b}\u0010\u0007R\u0019\u0010~\u001a\u00020\u0003X¦\u000e¢\u0006\r\u001a\u0004\b\u007f\u0010\u0005\"\u0005\b\u0080\u0001\u0010\u0007R\u001b\u0010\u0081\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b\u0082\u0001\u0010\u0005\"\u0005\b\u0083\u0001\u0010\u0007R\u001b\u0010\u0084\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b\u0085\u0001\u0010\u0005\"\u0005\b\u0086\u0001\u0010\u0007R\u001b\u0010\u0087\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b\u0088\u0001\u0010\u0005\"\u0005\b\u0089\u0001\u0010\u0007R\u001b\u0010\u008a\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b\u008b\u0001\u0010\u0005\"\u0005\b\u008c\u0001\u0010\u0007R\u001b\u0010\u008d\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b\u008e\u0001\u0010\u0005\"\u0005\b\u008f\u0001\u0010\u0007R\u001b\u0010\u0090\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b\u0091\u0001\u0010\u0005\"\u0005\b\u0092\u0001\u0010\u0007R\u001b\u0010\u0093\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b\u0094\u0001\u0010\u0005\"\u0005\b\u0095\u0001\u0010\u0007R\u001e\u0010\u0096\u0001\u001a\u00030\u0097\u0001X¦\u000e¢\u0006\u0010\u001a\u0006\b\u0098\u0001\u0010\u0099\u0001\"\u0006\b\u009a\u0001\u0010\u009b\u0001R)\u0010\u009c\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u009d\u0001\u0012\u0005\u0012\u00030\u009d\u00010\u0012X¦\u000e¢\u0006\u000e\u001a\u0005\b\u009e\u0001\u0010\u0015\"\u0005\b\u009f\u0001\u0010\u0017R\u001b\u0010 \u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b¡\u0001\u0010\u0005\"\u0005\b¢\u0001\u0010\u0007R\u001b\u0010£\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b¤\u0001\u0010\u0005\"\u0005\b¥\u0001\u0010\u0007R\u001e\u0010¦\u0001\u001a\u00030§\u0001X¦\u000e¢\u0006\u0010\u001a\u0006\b¨\u0001\u0010©\u0001\"\u0006\bª\u0001\u0010«\u0001R\u001b\u0010¬\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b\u00ad\u0001\u0010\u0005\"\u0005\b®\u0001\u0010\u0007R\u001b\u0010¯\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b°\u0001\u0010\u0005\"\u0005\b±\u0001\u0010\u0007R\u001b\u0010²\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b³\u0001\u0010\u0005\"\u0005\b´\u0001\u0010\u0007R\u001b\u0010µ\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b¶\u0001\u0010\u0005\"\u0005\b·\u0001\u0010\u0007R\u001b\u0010¸\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b¹\u0001\u0010\u0005\"\u0005\bº\u0001\u0010\u0007R\u001b\u0010»\u0001\u001a\u00020\u0003X¦\u000e¢\u0006\u000e\u001a\u0005\b¼\u0001\u0010\u0005\"\u0005\b½\u0001\u0010\u0007¨\u0006¾\u0001"}, d2 = {"Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "", "actualPropertiesInPrimaryConstructor", "", "getActualPropertiesInPrimaryConstructor", "()Z", "setActualPropertiesInPrimaryConstructor", "(Z)V", "alwaysRenderModifiers", "getAlwaysRenderModifiers", "setAlwaysRenderModifiers", "annotationArgumentsRenderingPolicy", "Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;", "getAnnotationArgumentsRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;", "setAnnotationArgumentsRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;)V", "annotationFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getAnnotationFilter", "()Lkotlin/jvm/functions/Function1;", "setAnnotationFilter", "(Lkotlin/jvm/functions/Function1;)V", "boldOnlyForNamesInHtml", "getBoldOnlyForNamesInHtml", "setBoldOnlyForNamesInHtml", "classWithPrimaryConstructor", "getClassWithPrimaryConstructor", "setClassWithPrimaryConstructor", "classifierNamePolicy", "Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "getClassifierNamePolicy", "()Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "setClassifierNamePolicy", "(Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;)V", "debugMode", "getDebugMode", "setDebugMode", "defaultParameterValueRenderer", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "", "getDefaultParameterValueRenderer", "setDefaultParameterValueRenderer", "eachAnnotationOnNewLine", "getEachAnnotationOnNewLine", "setEachAnnotationOnNewLine", "enhancedTypes", "getEnhancedTypes", "setEnhancedTypes", "excludedAnnotationClasses", "", "Lorg/jetbrains/kotlin/name/FqName;", "getExcludedAnnotationClasses", "()Ljava/util/Set;", "setExcludedAnnotationClasses", "(Ljava/util/Set;)V", "excludedTypeAnnotationClasses", "getExcludedTypeAnnotationClasses", "setExcludedTypeAnnotationClasses", "includeAdditionalModifiers", "getIncludeAdditionalModifiers", "setIncludeAdditionalModifiers", "includeAnnotationArguments", "getIncludeAnnotationArguments", "includeEmptyAnnotationArguments", "getIncludeEmptyAnnotationArguments", "includePropertyConstant", "getIncludePropertyConstant", "setIncludePropertyConstant", "informativeErrorType", "getInformativeErrorType", "setInformativeErrorType", "modifiers", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererModifier;", "getModifiers", "setModifiers", "normalizedVisibilities", "getNormalizedVisibilities", "setNormalizedVisibilities", "overrideRenderingPolicy", "Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;", "getOverrideRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;", "setOverrideRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;)V", "parameterNameRenderingPolicy", "Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;", "getParameterNameRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;", "setParameterNameRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;)V", "parameterNamesInFunctionalTypes", "getParameterNamesInFunctionalTypes", "setParameterNamesInFunctionalTypes", "presentableUnresolvedTypes", "getPresentableUnresolvedTypes", "setPresentableUnresolvedTypes", "propertyAccessorRenderingPolicy", "Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;", "getPropertyAccessorRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;", "setPropertyAccessorRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;)V", "propertyConstantRenderer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getPropertyConstantRenderer", "setPropertyConstantRenderer", "receiverAfterName", "getReceiverAfterName", "setReceiverAfterName", "renderCompanionObjectName", "getRenderCompanionObjectName", "setRenderCompanionObjectName", "renderConstructorDelegation", "getRenderConstructorDelegation", "setRenderConstructorDelegation", "renderConstructorKeyword", "getRenderConstructorKeyword", "setRenderConstructorKeyword", "renderDefaultAnnotationArguments", "getRenderDefaultAnnotationArguments", "setRenderDefaultAnnotationArguments", "renderDefaultModality", "getRenderDefaultModality", "setRenderDefaultModality", "renderDefaultVisibility", "getRenderDefaultVisibility", "setRenderDefaultVisibility", "renderFunctionContracts", "getRenderFunctionContracts", "setRenderFunctionContracts", "renderPrimaryConstructorParametersAsProperties", "getRenderPrimaryConstructorParametersAsProperties", "setRenderPrimaryConstructorParametersAsProperties", "renderTypeExpansions", "getRenderTypeExpansions", "setRenderTypeExpansions", "renderUnabbreviatedType", "getRenderUnabbreviatedType", "setRenderUnabbreviatedType", "secondaryConstructorsAsPrimary", "getSecondaryConstructorsAsPrimary", "setSecondaryConstructorsAsPrimary", "startFromDeclarationKeyword", "getStartFromDeclarationKeyword", "setStartFromDeclarationKeyword", "startFromName", "getStartFromName", "setStartFromName", "textFormat", "Lorg/jetbrains/kotlin/renderer/RenderingFormat;", "getTextFormat", "()Lorg/jetbrains/kotlin/renderer/RenderingFormat;", "setTextFormat", "(Lorg/jetbrains/kotlin/renderer/RenderingFormat;)V", "typeNormalizer", "Lorg/jetbrains/kotlin/types/KotlinType;", "getTypeNormalizer", "setTypeNormalizer", "uninferredTypeParameterAsName", "getUninferredTypeParameterAsName", "setUninferredTypeParameterAsName", "unitReturnType", "getUnitReturnType", "setUnitReturnType", "valueParametersHandler", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;", "getValueParametersHandler", "()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;", "setValueParametersHandler", "(Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;)V", "verbose", "getVerbose", "setVerbose", "withDefinedIn", "getWithDefinedIn", "setWithDefinedIn", "withSourceFileForTopLevel", "getWithSourceFileForTopLevel", "setWithSourceFileForTopLevel", "withoutReturnType", "getWithoutReturnType", "setWithoutReturnType", "withoutSuperTypes", "getWithoutSuperTypes", "setWithoutSuperTypes", "withoutTypeParameters", "getWithoutTypeParameters", "setWithoutTypeParameters", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DescriptorRendererOptions {
    boolean getActualPropertiesInPrimaryConstructor();

    boolean getAlwaysRenderModifiers();

    AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy();

    Function1<AnnotationDescriptor, Boolean> getAnnotationFilter();

    boolean getBoldOnlyForNamesInHtml();

    boolean getClassWithPrimaryConstructor();

    ClassifierNamePolicy getClassifierNamePolicy();

    boolean getDebugMode();

    Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer();

    boolean getEachAnnotationOnNewLine();

    boolean getEnhancedTypes();

    Set<FqName> getExcludedAnnotationClasses();

    Set<FqName> getExcludedTypeAnnotationClasses();

    boolean getIncludeAdditionalModifiers();

    boolean getIncludeAnnotationArguments();

    boolean getIncludeEmptyAnnotationArguments();

    boolean getIncludePropertyConstant();

    boolean getInformativeErrorType();

    Set<DescriptorRendererModifier> getModifiers();

    boolean getNormalizedVisibilities();

    OverrideRenderingPolicy getOverrideRenderingPolicy();

    ParameterNameRenderingPolicy getParameterNameRenderingPolicy();

    boolean getParameterNamesInFunctionalTypes();

    boolean getPresentableUnresolvedTypes();

    PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy();

    Function1<ConstantValue<?>, String> getPropertyConstantRenderer();

    boolean getReceiverAfterName();

    boolean getRenderCompanionObjectName();

    boolean getRenderConstructorDelegation();

    boolean getRenderConstructorKeyword();

    boolean getRenderDefaultAnnotationArguments();

    boolean getRenderDefaultModality();

    boolean getRenderDefaultVisibility();

    boolean getRenderFunctionContracts();

    boolean getRenderPrimaryConstructorParametersAsProperties();

    boolean getRenderTypeExpansions();

    boolean getRenderUnabbreviatedType();

    boolean getSecondaryConstructorsAsPrimary();

    boolean getStartFromDeclarationKeyword();

    boolean getStartFromName();

    RenderingFormat getTextFormat();

    Function1<KotlinType, KotlinType> getTypeNormalizer();

    boolean getUninferredTypeParameterAsName();

    boolean getUnitReturnType();

    DescriptorRenderer.ValueParametersHandler getValueParametersHandler();

    boolean getVerbose();

    boolean getWithDefinedIn();

    boolean getWithSourceFileForTopLevel();

    boolean getWithoutReturnType();

    boolean getWithoutSuperTypes();

    boolean getWithoutTypeParameters();

    void setActualPropertiesInPrimaryConstructor(boolean z);

    void setAlwaysRenderModifiers(boolean z);

    void setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy);

    void setAnnotationFilter(Function1<? super AnnotationDescriptor, Boolean> function1);

    void setBoldOnlyForNamesInHtml(boolean z);

    void setClassWithPrimaryConstructor(boolean z);

    void setClassifierNamePolicy(ClassifierNamePolicy classifierNamePolicy);

    void setDebugMode(boolean z);

    void setDefaultParameterValueRenderer(Function1<? super ValueParameterDescriptor, String> function1);

    void setEachAnnotationOnNewLine(boolean z);

    void setEnhancedTypes(boolean z);

    void setExcludedAnnotationClasses(Set<FqName> set);

    void setExcludedTypeAnnotationClasses(Set<FqName> set);

    void setIncludeAdditionalModifiers(boolean z);

    void setIncludePropertyConstant(boolean z);

    void setInformativeErrorType(boolean z);

    void setModifiers(Set<? extends DescriptorRendererModifier> set);

    void setNormalizedVisibilities(boolean z);

    void setOverrideRenderingPolicy(OverrideRenderingPolicy overrideRenderingPolicy);

    void setParameterNameRenderingPolicy(ParameterNameRenderingPolicy parameterNameRenderingPolicy);

    void setParameterNamesInFunctionalTypes(boolean z);

    void setPresentableUnresolvedTypes(boolean z);

    void setPropertyAccessorRenderingPolicy(PropertyAccessorRenderingPolicy propertyAccessorRenderingPolicy);

    void setPropertyConstantRenderer(Function1<? super ConstantValue<?>, String> function1);

    void setReceiverAfterName(boolean z);

    void setRenderCompanionObjectName(boolean z);

    void setRenderConstructorDelegation(boolean z);

    void setRenderConstructorKeyword(boolean z);

    void setRenderDefaultAnnotationArguments(boolean z);

    void setRenderDefaultModality(boolean z);

    void setRenderDefaultVisibility(boolean z);

    void setRenderFunctionContracts(boolean z);

    void setRenderPrimaryConstructorParametersAsProperties(boolean z);

    void setRenderTypeExpansions(boolean z);

    void setRenderUnabbreviatedType(boolean z);

    void setSecondaryConstructorsAsPrimary(boolean z);

    void setStartFromDeclarationKeyword(boolean z);

    void setStartFromName(boolean z);

    void setTextFormat(RenderingFormat renderingFormat);

    void setTypeNormalizer(Function1<? super KotlinType, ? extends KotlinType> function1);

    void setUninferredTypeParameterAsName(boolean z);

    void setUnitReturnType(boolean z);

    void setValueParametersHandler(DescriptorRenderer.ValueParametersHandler valueParametersHandler);

    void setVerbose(boolean z);

    void setWithDefinedIn(boolean z);

    void setWithSourceFileForTopLevel(boolean z);

    void setWithoutReturnType(boolean z);

    void setWithoutSuperTypes(boolean z);

    void setWithoutTypeParameters(boolean z);

    /* JADX INFO: compiled from: DescriptorRenderer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean getIncludeAnnotationArguments(DescriptorRendererOptions descriptorRendererOptions) {
            return descriptorRendererOptions.getAnnotationArgumentsRenderingPolicy().getIncludeAnnotationArguments();
        }

        public static boolean getIncludeEmptyAnnotationArguments(DescriptorRendererOptions descriptorRendererOptions) {
            return descriptorRendererOptions.getAnnotationArgumentsRenderingPolicy().getIncludeEmptyAnnotationArguments();
        }
    }
}
