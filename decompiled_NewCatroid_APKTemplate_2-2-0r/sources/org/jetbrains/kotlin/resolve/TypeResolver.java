package org.jetbrains.kotlin.resolve;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtContextReceiverList;
import org.jetbrains.kotlin.psi.KtDeclarationModifierList;
import org.jetbrains.kotlin.psi.KtDynamicType;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionType;
import org.jetbrains.kotlin.psi.KtIntersectionType;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtSelfType;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.codeFragmentUtil.CodeFragmentUtilKt;
import org.jetbrains.kotlin.psi.debugText.DebugTextUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.psiUtil.ReservedCheckingKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineCallCheckerKt;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallableDescriptors;
import org.jetbrains.kotlin.resolve.checkers.TrailingCommaChecker;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.LazyScopeAdapter;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.DynamicType;
import org.jetbrains.kotlin.types.DynamicTypesSettings;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAliasExpander;
import org.jetbrains.kotlin.types.TypeAliasExpansion;
import org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorScope;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.error.ThrowingScope;
import org.jetbrains.kotlin.types.extensions.TypeAttributeTranslators;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: TypeResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000À\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\u0018\u0000 \u0082\u00012\u00020\u0001:\u0006\u0082\u0001\u0083\u0001\u0084\u0001B]\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017¢\u0006\u0002\u0010\u0018J$\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001cH\u0002J:\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\u000e\u0010%\u001a\n\u0012\u0004\u0012\u00020#\u0018\u00010\u001c2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020!0\u001cH\u0002J\u0010\u0010'\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020)H\u0002J&\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u001c2\u0006\u00100\u001a\u000201H\u0002JB\u00102\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002040\u001c\u0012\f\u0012\n\u0012\u0004\u0012\u00020#\u0018\u00010\u001c\u0018\u0001032\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u001cH\u0002J&\u0010;\u001a\u00020<2\u0006\u00105\u001a\u0002062\f\u0010=\u001a\b\u0012\u0004\u0012\u0002040\u001c2\u0006\u0010>\u001a\u00020?H\u0002J\u0010\u0010@\u001a\u00020+2\u0006\u0010A\u001a\u00020BH\u0002J\u0018\u0010C\u001a\u00020D2\u0006\u00105\u001a\u0002062\u0006\u0010E\u001a\u00020!H\u0002J\u0010\u0010F\u001a\u00020\u001a2\u0006\u00107\u001a\u000208H\u0002J\u001e\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020L2\u0006\u00100\u001a\u000201J(\u0010M\u001a\u0004\u0018\u00010N2\u0006\u0010I\u001a\u00020J2\u0006\u0010O\u001a\u00020P2\u0006\u00100\u001a\u0002012\u0006\u0010Q\u001a\u00020\u001aJ&\u0010R\u001a\u00020S2\u0006\u0010I\u001a\u00020J2\u0006\u0010O\u001a\u00020P2\u0006\u00100\u001a\u0002012\u0006\u0010Q\u001a\u00020\u001aJ\u000e\u0010T\u001a\u00020H2\u0006\u0010U\u001a\u00020)J\u0016\u0010V\u001a\u00020<2\u0006\u00105\u001a\u0002062\u0006\u0010K\u001a\u00020LJ\u0018\u0010W\u001a\u00020B2\u0006\u00105\u001a\u0002062\u0006\u0010K\u001a\u00020LH\u0002J&\u0010W\u001a\u00020B2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020L2\u0006\u00100\u001a\u0002012\u0006\u0010X\u001a\u00020\u001aJ\"\u0010Y\u001a\u00020Z2\u0006\u00100\u001a\u0002012\u0006\u0010I\u001a\u00020J2\n\u0010[\u001a\u0006\u0012\u0002\b\u00030\\J,\u0010]\u001a\u00020<2\u0006\u00105\u001a\u0002062\u0006\u0010^\u001a\u00020Z2\b\u0010_\u001a\u0004\u0018\u00010`2\b\u0010,\u001a\u0004\u0018\u00010aH\u0002J0\u0010b\u001a\u00020<2\u0006\u00105\u001a\u0002062\u0006\u0010^\u001a\u00020Z2\u0006\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020SH\u0002J.\u0010h\u001a\u00020<2\u0006\u00105\u001a\u0002062\u0006\u0010(\u001a\u00020N2\u0006\u0010g\u001a\u00020S2\u0006\u0010e\u001a\u00020f2\u0006\u0010^\u001a\u00020ZJ0\u0010i\u001a\u00020<2\u0006\u00105\u001a\u0002062\u0006\u0010^\u001a\u00020Z2\u0006\u0010(\u001a\u00020)2\u0006\u0010A\u001a\u00020f2\u0006\u0010g\u001a\u00020SH\u0002J2\u0010j\u001a\u00020B2\u0006\u00105\u001a\u0002062\u0006\u0010^\u001a\u00020Z2\u0006\u0010k\u001a\u00020!2\u0006\u0010l\u001a\u00020m2\b\u0010n\u001a\u0004\u0018\u00010oH\u0002J*\u0010p\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\u0006\u00105\u001a\u0002062\u0006\u0010q\u001a\u00020?2\f\u0010r\u001a\b\u0012\u0004\u0012\u0002040\u001cJ.\u0010s\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\u0006\u00105\u001a\u0002062\f\u0010r\u001a\b\u0012\u0004\u0012\u0002040\u001c2\b\b\u0002\u0010t\u001a\u00020uH\u0002J\u001e\u0010v\u001a\u00020B2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020L2\u0006\u00100\u001a\u000201J\u0018\u0010w\u001a\u00020\u001a2\u0006\u00105\u001a\u0002062\u0006\u0010x\u001a\u00020BH\u0002J\u000e\u0010y\u001a\u00020\u001a*\u0004\u0018\u00010aH\u0002J\u0014\u0010z\u001a\b\u0012\u0004\u0012\u0002080\u001c*\u0004\u0018\u00010NH\u0002J\u001c\u0010{\u001a\n\u0012\u0004\u0012\u00020#\u0018\u00010\u001c*\u00020J2\u0006\u0010|\u001a\u00020dH\u0002J!\u0010}\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u007f0~*\u0006\u0012\u0002\b\u00030\\H\u0000¢\u0006\u0006\b\u0080\u0001\u0010\u0081\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0085\u0001²\u0006\u000e\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0087\u0001X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/TypeResolver;", "", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "qualifiedExpressionResolver", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "typeTransformerForTests", "Lorg/jetbrains/kotlin/resolve/TypeResolver$TypeTransformerForTests;", "dynamicTypesSettings", "Lorg/jetbrains/kotlin/types/DynamicTypesSettings;", "dynamicCallableDescriptors", "Lorg/jetbrains/kotlin/resolve/calls/tasks/DynamicCallableDescriptors;", "identifierChecker", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "platformToKotlinClassMapper", "Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "upperBoundChecker", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "typeAttributeTranslators", "Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;", "(Lorg/jetbrains/kotlin/resolve/AnnotationResolver;Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/TypeResolver$TypeTransformerForTests;Lorg/jetbrains/kotlin/types/DynamicTypesSettings;Lorg/jetbrains/kotlin/resolve/calls/tasks/DynamicCallableDescriptors;Lorg/jetbrains/kotlin/resolve/IdentifierChecker;Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;)V", "isNonParenthesizedAnnotationsOnFunctionalTypesEnabled", "", "appendDefaultArgumentsForLocalClassifier", "", "Lorg/jetbrains/kotlin/types/TypeProjectionImpl;", "fromIndex", "", "constructorParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "buildFinalArgumentList", "Lorg/jetbrains/kotlin/types/TypeProjection;", "argumentsFromUserType", "argumentsForOuterClass", "parameters", "canBeUsedAsBareType", "descriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "checkNonParenthesizedAnnotationsOnFunctionalType", "", "typeElement", "Lorg/jetbrains/kotlin/psi/KtFunctionType;", "annotationEntries", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "collectArgumentsForClassifierTypeConstructor", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "c", "Lorg/jetbrains/kotlin/resolve/TypeResolutionContext;", "classifierDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "qualifierParts", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$ExpressionQualifierPart;", "createErrorTypeForTypeConstructor", "Lorg/jetbrains/kotlin/resolve/PossiblyBareType;", "arguments", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "forceResolveTypeContents", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getScopeForTypeParameter", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "typeParameterDescriptor", "isPossibleToSpecifyTypeArgumentsFor", "resolveAbbreviatedType", "Lorg/jetbrains/kotlin/types/SimpleType;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "resolveClass", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "userType", "Lorg/jetbrains/kotlin/psi/KtUserType;", "isDebuggerContext", "resolveDescriptorForType", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$TypeQualifierResolutionResult;", "resolveExpandedTypeForTypeAlias", "typeAliasDescriptor", "resolvePossiblyBareType", "resolveType", "checkBounds", "resolveTypeAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "modifierListsOwner", "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "resolveTypeElement", "annotations", "outerModifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "resolveTypeForClass", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "qualifierResolutionResult", "resolveTypeForClassifier", "resolveTypeForTypeAlias", "resolveTypeForTypeParameter", "typeParameter", "referenceExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "typeArgumentList", "Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;", "resolveTypeProjections", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "argumentElements", "resolveTypeProjectionsWithErrorConstructor", CrashHianalyticsData.MESSAGE, "", "resolveTypeWithPossibleIntersections", "shouldCheckBounds", "inType", "canHaveFunctionTypeModifiers", "classifierDescriptorsFromInnerToOuter", "findImplicitOuterClassArguments", "outerClass", "getAllModifierLists", "", "Lorg/jetbrains/kotlin/psi/KtDeclarationModifierList;", "getAllModifierLists$frontend", "(Lorg/jetbrains/kotlin/psi/KtElementImplStub;)[Lorg/jetbrains/kotlin/psi/KtDeclarationModifierList;", "Companion", "TracingTypeAliasExpansionReportStrategy", "TypeTransformerForTests", "frontend", "suspendModifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final AnnotationResolver annotationResolver;
    private final DynamicCallableDescriptors dynamicCallableDescriptors;
    private final DynamicTypesSettings dynamicTypesSettings;
    private final IdentifierChecker identifierChecker;
    private final boolean isNonParenthesizedAnnotationsOnFunctionalTypesEnabled;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor moduleDescriptor;
    private final PlatformToKotlinClassMapper platformToKotlinClassMapper;
    private final QualifiedExpressionResolver qualifiedExpressionResolver;
    private final TypeAttributeTranslators typeAttributeTranslators;
    private final TypeTransformerForTests typeTransformerForTests;
    private final UpperBoundChecker upperBoundChecker;

    /* JADX INFO: compiled from: TypeResolver.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/TypeResolver$TypeTransformerForTests;", "", "()V", "transformType", "Lorg/jetbrains/kotlin/types/KotlinType;", "kotlinType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class TypeTransformerForTests {
        public KotlinType transformType(KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return null;
        }
    }

    @JvmStatic
    public static final Variance resolveProjectionKind(KtProjectionKind ktProjectionKind) {
        return INSTANCE.resolveProjectionKind(ktProjectionKind);
    }

    public TypeResolver(AnnotationResolver annotationResolver, QualifiedExpressionResolver qualifiedExpressionResolver, ModuleDescriptor moduleDescriptor, TypeTransformerForTests typeTransformerForTests, DynamicTypesSettings dynamicTypesSettings, DynamicCallableDescriptors dynamicCallableDescriptors, IdentifierChecker identifierChecker, PlatformToKotlinClassMapper platformToKotlinClassMapper, LanguageVersionSettings languageVersionSettings, UpperBoundChecker upperBoundChecker, TypeAttributeTranslators typeAttributeTranslators) {
        Intrinsics.checkNotNullParameter(annotationResolver, "annotationResolver");
        Intrinsics.checkNotNullParameter(qualifiedExpressionResolver, "qualifiedExpressionResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(typeTransformerForTests, "typeTransformerForTests");
        Intrinsics.checkNotNullParameter(dynamicTypesSettings, "dynamicTypesSettings");
        Intrinsics.checkNotNullParameter(dynamicCallableDescriptors, "dynamicCallableDescriptors");
        Intrinsics.checkNotNullParameter(identifierChecker, "identifierChecker");
        Intrinsics.checkNotNullParameter(platformToKotlinClassMapper, "platformToKotlinClassMapper");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(upperBoundChecker, "upperBoundChecker");
        Intrinsics.checkNotNullParameter(typeAttributeTranslators, "typeAttributeTranslators");
        this.annotationResolver = annotationResolver;
        this.qualifiedExpressionResolver = qualifiedExpressionResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.typeTransformerForTests = typeTransformerForTests;
        this.dynamicTypesSettings = dynamicTypesSettings;
        this.dynamicCallableDescriptors = dynamicCallableDescriptors;
        this.identifierChecker = identifierChecker;
        this.platformToKotlinClassMapper = platformToKotlinClassMapper;
        this.languageVersionSettings = languageVersionSettings;
        this.upperBoundChecker = upperBoundChecker;
        this.typeAttributeTranslators = typeAttributeTranslators;
        this.isNonParenthesizedAnnotationsOnFunctionalTypesEnabled = languageVersionSettings.getFeatureSupport(LanguageFeature.NonParenthesizedAnnotationsOnFunctionalTypes) == LanguageFeature.State.ENABLED;
    }

    public final KotlinType resolveTypeWithPossibleIntersections(LexicalScope scope, KtTypeReference typeReference, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        Intrinsics.checkNotNullParameter(trace, "trace");
        return resolveType(new TypeResolutionContext(scope, trace, false, false, CodeFragmentUtilKt.suppressDiagnosticsInDebugMode(typeReference), false, true), typeReference);
    }

    public final KotlinType resolveType(LexicalScope scope, KtTypeReference typeReference, BindingTrace trace, boolean checkBounds) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        Intrinsics.checkNotNullParameter(trace, "trace");
        return resolveType(new TypeResolutionContext(scope, trace, checkBounds, false, CodeFragmentUtilKt.suppressDiagnosticsInDebugMode(typeReference), false), typeReference);
    }

    public final SimpleType resolveAbbreviatedType(LexicalScope scope, KtTypeReference typeReference, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        Intrinsics.checkNotNullParameter(trace, "trace");
        UnwrappedType unwrappedTypeUnwrap = resolveType(new TypeResolutionContext(scope, trace, true, false, CodeFragmentUtilKt.suppressDiagnosticsInDebugMode(typeReference), true), typeReference).unwrap();
        if (unwrappedTypeUnwrap instanceof DynamicType) {
            trace.report(Errors.TYPEALIAS_SHOULD_EXPAND_TO_CLASS.on(typeReference, unwrappedTypeUnwrap));
            return ErrorUtils.createErrorType(ErrorTypeKind.PROHIBITED_DYNAMIC_TYPE, new String[0]);
        }
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            return (SimpleType) unwrappedTypeUnwrap;
        }
        throw new IllegalStateException(("Unexpected type: " + unwrappedTypeUnwrap).toString());
    }

    public final SimpleType resolveExpandedTypeForTypeAlias(TypeAliasDescriptor typeAliasDescriptor) {
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
        return TypeAliasExpander.INSTANCE.getNON_REPORTING().expandWithoutAbbreviation(TypeAliasExpansion.INSTANCE.createWithFormalArguments(typeAliasDescriptor), TypeAttributes.INSTANCE.getEmpty());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinType resolveType(TypeResolutionContext c, KtTypeReference typeReference) {
        boolean z = c.allowBareTypes;
        KotlinType actualType = resolvePossiblyBareType(c, typeReference).getActualType();
        Intrinsics.checkNotNullExpressionValue(actualType, "getActualType(...)");
        return actualType;
    }

    public final PossiblyBareType resolvePossiblyBareType(TypeResolutionContext c, KtTypeReference typeReference) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        KotlinType kotlinType = (KotlinType) c.trace.getContext().get(BindingContext.TYPE, typeReference);
        if (kotlinType != null) {
            PossiblyBareType possiblyBareTypeType = PossiblyBareType.type(kotlinType);
            Intrinsics.checkNotNullExpressionValue(possiblyBareTypeType, "type(...)");
            return possiblyBareTypeType;
        }
        WritableSlice<KtTypeReference, KotlinType> writableSlice = c.abbreviated ? BindingContext.ABBREVIATED_TYPE : BindingContext.TYPE;
        BindingTrace trace = c.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        LexicalScope scope = c.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        PossiblyBareType possiblyBareTypeResolveTypeElement = resolveTypeElement(c, resolveTypeAnnotations(trace, scope, typeReference), typeReference.getModifierList(), typeReference.getTypeElement());
        BindingTrace trace2 = c.trace;
        Intrinsics.checkNotNullExpressionValue(trace2, "trace");
        LexicalScope scope2 = c.scope;
        Intrinsics.checkNotNullExpressionValue(scope2, "scope");
        BindingContextUtilsKt.recordScope(trace2, scope2, typeReference);
        if (!possiblyBareTypeResolveTypeElement.isBare()) {
            Iterator<TypeProjection> it = possiblyBareTypeResolveTypeElement.getActualType().getArguments().iterator();
            while (it.hasNext()) {
                KotlinType type = it.next().getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                forceResolveTypeContents(type);
            }
            c.trace.record(writableSlice, typeReference, possiblyBareTypeResolveTypeElement.getActualType());
        }
        return possiblyBareTypeResolveTypeElement;
    }

    public final KtDeclarationModifierList[] getAllModifierLists$frontend(KtElementImplStub<?> ktElementImplStub) {
        Intrinsics.checkNotNullParameter(ktElementImplStub, "<this>");
        PsiElement[] stubOrPsiChildren = ktElementImplStub.getStubOrPsiChildren(KtStubElementTypes.MODIFIER_LIST, KtStubElementTypes.MODIFIER_LIST.getArrayFactory());
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildren, "getStubOrPsiChildren(...)");
        return (KtDeclarationModifierList[]) stubOrPsiChildren;
    }

    private final void checkNonParenthesizedAnnotationsOnFunctionalType(KtFunctionType typeElement, List<? extends KtAnnotationEntry> annotationEntries, BindingTrace trace) {
        PsiElement nextSiblingIgnoringWhitespaceAndComments$default;
        ASTNode node;
        KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) CollectionsKt.lastOrNull((List) annotationEntries);
        boolean zAreEqual = Intrinsics.areEqual((ktAnnotationEntry == null || (nextSiblingIgnoringWhitespaceAndComments$default = PsiUtilsKt.getNextSiblingIgnoringWhitespaceAndComments$default(ktAnnotationEntry, false, 1, null)) == null || (node = nextSiblingIgnoringWhitespaceAndComments$default.getNode()) == null) ? null : node.getElementType(), KtTokens.RBRACKET);
        boolean z = (ktAnnotationEntry != null ? ktAnnotationEntry.getValueArgumentList() : null) != null;
        boolean z2 = typeElement.getFirstChild() instanceof KtParameterList;
        PsiElement prevSiblingIgnoringWhitespaceAndComments$default = PsiUtilsKt.getPrevSiblingIgnoringWhitespaceAndComments$default(typeElement, false, 1, null);
        boolean z3 = (prevSiblingIgnoringWhitespaceAndComments$default instanceof KtDeclarationModifierList) && PsiUtilsKt.hasSuspendModifier((KtModifierList) prevSiblingIgnoringWhitespaceAndComments$default);
        if (ktAnnotationEntry == null || !z2 || z || zAreEqual || z3) {
            return;
        }
        trace.report(Errors.NON_PARENTHESIZED_ANNOTATIONS_ON_FUNCTIONAL_TYPES.on(ktAnnotationEntry));
    }

    public final Annotations resolveTypeAnnotations(BindingTrace trace, LexicalScope scope, KtElementImplStub<?> modifierListsOwner) {
        KtTypeElement typeElement;
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(modifierListsOwner, "modifierListsOwner");
        KtDeclarationModifierList[] allModifierLists$frontend = getAllModifierLists$frontend(modifierListsOwner);
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        if (!this.isNonParenthesizedAnnotationsOnFunctionalTypesEnabled) {
            boolean z = modifierListsOwner instanceof KtNullableType;
            List<KtAnnotationEntry> annotationEntries = null;
            if (z) {
                typeElement = ((KtNullableType) modifierListsOwner).getInnerType();
            } else {
                typeElement = modifierListsOwner instanceof KtTypeReference ? ((KtTypeReference) modifierListsOwner).getTypeElement() : null;
            }
            if (z) {
                KtModifierList modifierList = ((KtNullableType) modifierListsOwner).getModifierList();
                if (modifierList != null) {
                    annotationEntries = modifierList.getAnnotationEntries();
                }
            } else if (modifierListsOwner instanceof KtTypeReference) {
                annotationEntries = ((KtTypeReference) modifierListsOwner).getAnnotationEntries();
            }
            if (typeElement instanceof KtFunctionType) {
                KtFunctionType ktFunctionType = (KtFunctionType) typeElement;
                if (ktFunctionType.getStub() == 0 && annotationEntries != null) {
                    checkNonParenthesizedAnnotationsOnFunctionalType(ktFunctionType, annotationEntries, trace);
                }
            }
        }
        int length = allModifierLists$frontend.length;
        int i = 0;
        boolean z2 = false;
        while (i < length) {
            KtDeclarationModifierList ktDeclarationModifierList = allModifierLists$frontend[i];
            if (z2) {
                trace.report(Errors.MODIFIER_LIST_NOT_ALLOWED.on(ktDeclarationModifierList));
            }
            AnnotationResolver annotationResolver = this.annotationResolver;
            List<KtAnnotationEntry> annotationEntries2 = ktDeclarationModifierList.getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries2, "getAnnotationEntries(...)");
            empty = org.jetbrains.kotlin.descriptors.annotations.AnnotationsKt.composeAnnotations(empty, annotationResolver.resolveAnnotationsWithoutArguments(scope, annotationEntries2, trace));
            i++;
            z2 = true;
        }
        return empty;
    }

    private final void forceResolveTypeContents(KotlinType type) {
        type.getAnnotations();
        if (FlexibleTypesKt.isFlexible(type)) {
            forceResolveTypeContents(FlexibleTypesKt.asFlexibleType(type).getLowerBound());
            forceResolveTypeContents(FlexibleTypesKt.asFlexibleType(type).getUpperBound());
            return;
        }
        type.getConstructor();
        for (TypeProjection typeProjection : type.getArguments()) {
            if (!typeProjection.isStarProjection()) {
                KotlinType type2 = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                forceResolveTypeContents(type2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final PossiblyBareType resolveTypeElement(final TypeResolutionContext c, final Annotations annotations, final KtModifierList outerModifierList, final KtTypeElement typeElement) {
        String debugText;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final boolean zHasModifier = outerModifierList != null ? outerModifierList.hasModifier(KtTokens.SUSPEND_KEYWORD) : false;
        Lazy lazy = LazyKt.lazy(new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.resolve.TypeResolver$resolveTypeElement$suspendModifier$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiElement invoke() {
                KtModifierList ktModifierList = outerModifierList;
                if (ktModifierList != null) {
                    return ktModifierList.getModifier(KtTokens.SUSPEND_KEYWORD);
                }
                return null;
            }
        });
        if (zHasModifier && !canHaveFunctionTypeModifiers(typeElement)) {
            BindingTrace bindingTrace = c.trace;
            DiagnosticFactory2<PsiElement, KtModifierKeywordToken, String> diagnosticFactory2 = Errors.WRONG_MODIFIER_TARGET;
            PsiElement psiElementResolveTypeElement$lambda$2 = resolveTypeElement$lambda$2(lazy);
            Intrinsics.checkNotNull(psiElementResolveTypeElement$lambda$2);
            bindingTrace.report(diagnosticFactory2.on(psiElementResolveTypeElement$lambda$2, KtTokens.SUSPEND_KEYWORD, "non-functional type"));
        } else if (zHasModifier) {
            LanguageVersionSettings languageVersionSettings = this.languageVersionSettings;
            BindingTrace trace = c.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            PsiElement psiElementResolveTypeElement$lambda$3 = resolveTypeElement$lambda$2(lazy);
            Intrinsics.checkNotNull(psiElementResolveTypeElement$lambda$3);
            CoroutineCallCheckerKt.checkCoroutinesFeature(languageVersionSettings, trace, psiElementResolveTypeElement$lambda$3);
        }
        if (typeElement != null) {
            typeElement.accept(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.resolve.TypeResolver.resolveTypeElement.1
                /* JADX WARN: Type inference failed for: r12v1, types: [T, org.jetbrains.kotlin.resolve.PossiblyBareType] */
                /* JADX WARN: Type inference failed for: r12v6, types: [T, org.jetbrains.kotlin.resolve.PossiblyBareType] */
                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitUserType(KtUserType type) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    TypeResolver typeResolver = TypeResolver.this;
                    LexicalScope scope = c.scope;
                    Intrinsics.checkNotNullExpressionValue(scope, "scope");
                    BindingTrace trace2 = c.trace;
                    Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                    QualifiedExpressionResolver.TypeQualifierResolutionResult typeQualifierResolutionResultResolveDescriptorForType = typeResolver.resolveDescriptorForType(scope, type, trace2, c.isDebuggerContext);
                    ClassifierDescriptor classifierDescriptor = typeQualifierResolutionResultResolveDescriptorForType.getClassifierDescriptor();
                    if (classifierDescriptor == null) {
                        TypeResolver typeResolver2 = TypeResolver.this;
                        TypeResolutionContext typeResolutionContext = c;
                        ErrorTypeKind errorTypeKind = ErrorTypeKind.UNRESOLVED_TYPE;
                        String text = typeElement.getText();
                        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                        objectRef.element = PossiblyBareType.type(ErrorUtils.INSTANCE.createErrorTypeWithArguments(ErrorTypeKind.UNRESOLVED_TYPE, typeResolver2.resolveTypeProjections(typeResolutionContext, ErrorUtils.createErrorType(errorTypeKind, text).getConstructor(), typeQualifierResolutionResultResolveDescriptorForType.getAllProjections()), DebugTextUtilKt.getDebugText(type)));
                        return;
                    }
                    KtSimpleNameExpression referenceExpression = type.getReferenceExpression();
                    if (referenceExpression == null) {
                        return;
                    }
                    if (!TypeResolver.this.languageVersionSettings.supportsFeature(LanguageFeature.YieldIsNoMoreReserved)) {
                        BindingTrace trace3 = c.trace;
                        Intrinsics.checkNotNullExpressionValue(trace3, "trace");
                        ReservedCheckingKt.checkReservedYield(referenceExpression, trace3);
                    }
                    c.trace.record(BindingContext.REFERENCE_TARGET, referenceExpression, classifierDescriptor);
                    objectRef.element = TypeResolver.this.resolveTypeForClassifier(c, classifierDescriptor, typeQualifierResolutionResultResolveDescriptorForType, type, annotations);
                }

                /* JADX WARN: Type inference failed for: r0v4, types: [T, org.jetbrains.kotlin.resolve.PossiblyBareType] */
                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitNullableType(KtNullableType nullableType) {
                    Intrinsics.checkNotNullParameter(nullableType, "nullableType");
                    KtTypeElement innerType = nullableType.getInnerType();
                    PossiblyBareType possiblyBareTypeCreateTypeFromInner = createTypeFromInner(nullableType, nullableType.getModifierList(), innerType);
                    if (!possiblyBareTypeCreateTypeFromInner.isBare() && (possiblyBareTypeCreateTypeFromInner.getActualType() instanceof DefinitelyNotNullType)) {
                        c.trace.report(Errors.NULLABLE_ON_DEFINITELY_NOT_NULLABLE.on(nullableType));
                    }
                    if (possiblyBareTypeCreateTypeFromInner.isNullable() || (innerType instanceof KtNullableType) || (innerType instanceof KtDynamicType)) {
                        c.trace.report(Errors.REDUNDANT_NULLABLE.on(nullableType));
                    }
                    objectRef.element = possiblyBareTypeCreateTypeFromInner.makeNullable();
                }

                /* JADX WARN: Multi-variable type inference failed */
                private final PossiblyBareType createTypeFromInner(KtTypeElement typeElement2, KtModifierList innerModifierList, KtTypeElement innerType) {
                    if (innerModifierList != null && outerModifierList != null) {
                        c.trace.report(Errors.MODIFIER_LIST_NOT_ALLOWED.on(innerModifierList));
                    }
                    Annotations annotations2 = annotations;
                    TypeResolver typeResolver = TypeResolver.this;
                    BindingTrace trace2 = c.trace;
                    Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                    LexicalScope scope = c.scope;
                    Intrinsics.checkNotNullExpressionValue(scope, "scope");
                    Intrinsics.checkNotNull(typeElement2, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElementImplStub<*>");
                    Annotations annotationsComposeAnnotations = org.jetbrains.kotlin.descriptors.annotations.AnnotationsKt.composeAnnotations(annotations2, typeResolver.resolveTypeAnnotations(trace2, scope, (KtElementImplStub) typeElement2));
                    TypeResolver typeResolver2 = TypeResolver.this;
                    TypeResolutionContext typeResolutionContext = c;
                    KtModifierList ktModifierList = outerModifierList;
                    if (ktModifierList != null) {
                        innerModifierList = ktModifierList;
                    }
                    return typeResolver2.resolveTypeElement(typeResolutionContext, annotationsComposeAnnotations, innerModifierList, innerType);
                }

                /* JADX WARN: Type inference failed for: r0v11, types: [T, org.jetbrains.kotlin.resolve.PossiblyBareType] */
                /* JADX WARN: Type inference failed for: r4v0, types: [T, org.jetbrains.kotlin.resolve.PossiblyBareType] */
                /* JADX WARN: Type inference failed for: r8v11, types: [T, org.jetbrains.kotlin.resolve.PossiblyBareType] */
                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitIntersectionType(KtIntersectionType intersectionType) {
                    Intrinsics.checkNotNullParameter(intersectionType, "intersectionType");
                    TypeResolver typeResolver = TypeResolver.this;
                    TypeResolutionContext typeResolutionContext = c;
                    KtTypeReference leftTypeRef = intersectionType.getLeftTypeRef();
                    if (leftTypeRef == null) {
                        return;
                    }
                    PossiblyBareType possiblyBareTypeResolvePossiblyBareType = typeResolver.resolvePossiblyBareType(typeResolutionContext, leftTypeRef);
                    if (possiblyBareTypeResolvePossiblyBareType.isBare()) {
                        throw new IllegalStateException("There should not be bare types for intersections".toString());
                    }
                    KotlinType actualType = possiblyBareTypeResolvePossiblyBareType.getActualType();
                    Intrinsics.checkNotNullExpressionValue(actualType, "let(...)");
                    objectRef.element = PossiblyBareType.type(actualType);
                    TypeResolver typeResolver2 = TypeResolver.this;
                    TypeResolutionContext typeResolutionContext2 = c;
                    KtTypeReference rightTypeRef = intersectionType.getRightTypeRef();
                    if (rightTypeRef == null) {
                        return;
                    }
                    PossiblyBareType possiblyBareTypeResolvePossiblyBareType2 = typeResolver2.resolvePossiblyBareType(typeResolutionContext2, rightTypeRef);
                    if (possiblyBareTypeResolvePossiblyBareType2.isBare()) {
                        throw new IllegalStateException("There should not be bare types for intersections".toString());
                    }
                    KotlinType actualType2 = possiblyBareTypeResolvePossiblyBareType2.getActualType();
                    Intrinsics.checkNotNullExpressionValue(actualType2, "let(...)");
                    if (!c.allowIntersectionTypes) {
                        if (!TypeResolver.this.languageVersionSettings.supportsFeature(LanguageFeature.DefinitelyNonNullableTypes)) {
                            c.trace.report(Errors.UNSUPPORTED_FEATURE.on(intersectionType, TuplesKt.to(LanguageFeature.DefinitelyNonNullableTypes, TypeResolver.this.languageVersionSettings)));
                            return;
                        }
                        if (!TypeUtilsKt.isTypeParameter(actualType) || actualType.getIsMarkedNullable() || !isNullableOrUninitializedTypeParameter(actualType)) {
                            BindingTrace bindingTrace2 = c.trace;
                            DiagnosticFactory0<KtTypeReference> diagnosticFactory0 = Errors.INCORRECT_LEFT_COMPONENT_OF_INTERSECTION;
                            KtTypeReference leftTypeRef2 = intersectionType.getLeftTypeRef();
                            Intrinsics.checkNotNull(leftTypeRef2);
                            bindingTrace2.report(diagnosticFactory0.on(leftTypeRef2));
                            return;
                        }
                        if (!TypeUtilsKt.isAny(actualType2)) {
                            BindingTrace bindingTrace3 = c.trace;
                            DiagnosticFactory0<KtTypeReference> diagnosticFactory1 = Errors.INCORRECT_RIGHT_COMPONENT_OF_INTERSECTION;
                            KtTypeReference rightTypeRef2 = intersectionType.getRightTypeRef();
                            Intrinsics.checkNotNull(rightTypeRef2);
                            bindingTrace3.report(diagnosticFactory1.on(rightTypeRef2));
                            return;
                        }
                        DefinitelyNotNullType definitelyNotNullTypeMakeDefinitelyNotNull$default = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$default(DefinitelyNotNullType.INSTANCE, actualType.unwrap(), false, false, 6, null);
                        if (definitelyNotNullTypeMakeDefinitelyNotNull$default == null) {
                            StringBuilder sb = new StringBuilder("Definitely not-nullable type is not created for type parameter with nullable upper bound ");
                            TypeParameterDescriptor typeParameterDescriptorOrNull = TypeUtils.getTypeParameterDescriptorOrNull(actualType);
                            Intrinsics.checkNotNull(typeParameterDescriptorOrNull);
                            throw new IllegalStateException(sb.append(typeParameterDescriptorOrNull).toString().toString());
                        }
                        objectRef.element = PossiblyBareType.type(definitelyNotNullTypeMakeDefinitelyNotNull$default);
                        return;
                    }
                    objectRef.element = PossiblyBareType.type(new IntersectionTypeConstructor(CollectionsKt.listOf((Object[]) new KotlinType[]{actualType, actualType2})).createType());
                }

                private final boolean isNullableOrUninitializedTypeParameter(KotlinType kotlinType) {
                    ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                    TypeParameterDescriptorImpl typeParameterDescriptorImpl = this$0 instanceof TypeParameterDescriptorImpl ? (TypeParameterDescriptorImpl) this$0 : null;
                    if (typeParameterDescriptorImpl == null || typeParameterDescriptorImpl.isInitialized()) {
                        return KotlinTypeKt.isNullable(kotlinType);
                    }
                    return true;
                }

                /* JADX WARN: Type inference failed for: r0v11, types: [T, org.jetbrains.kotlin.resolve.PossiblyBareType] */
                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitFunctionType(KtFunctionType type) {
                    KotlinType kotlinTypeResolveType;
                    List listEmptyList;
                    SimpleType simpleTypeResolveType;
                    Intrinsics.checkNotNullParameter(type, "type");
                    KtTypeReference receiverTypeReference = type.getReceiverTypeReference();
                    if (receiverTypeReference == null) {
                        kotlinTypeResolveType = null;
                    } else {
                        TypeResolver typeResolver = TypeResolver.this;
                        TypeResolutionContext typeResolutionContextNoBareTypes = c.noBareTypes();
                        Intrinsics.checkNotNullExpressionValue(typeResolutionContextNoBareTypes, "noBareTypes(...)");
                        kotlinTypeResolveType = typeResolver.resolveType(typeResolutionContextNoBareTypes, receiverTypeReference);
                    }
                    KtContextReceiverList contextReceiverList = type.getContextReceiverList();
                    if (contextReceiverList != null) {
                        BindingTrace trace2 = c.trace;
                        Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                        ContextReceiversUtilKt.checkContextReceiversAreEnabled(trace2, TypeResolver.this.languageVersionSettings, contextReceiverList);
                        List<KtTypeReference> listTypeReferences = contextReceiverList.typeReferences();
                        TypeResolver typeResolver2 = TypeResolver.this;
                        TypeResolutionContext typeResolutionContext = c;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listTypeReferences, 10));
                        for (KtTypeReference ktTypeReference : listTypeReferences) {
                            TypeResolutionContext typeResolutionContextNoBareTypes2 = typeResolutionContext.noBareTypes();
                            Intrinsics.checkNotNullExpressionValue(typeResolutionContextNoBareTypes2, "noBareTypes(...)");
                            arrayList.add(typeResolver2.resolveType(typeResolutionContextNoBareTypes2, ktTypeReference));
                        }
                        ArrayList arrayList2 = arrayList;
                        BindingTrace trace3 = c.trace;
                        Intrinsics.checkNotNullExpressionValue(trace3, "trace");
                        ContextReceiversUtilKt.checkSubtypingBetweenContextReceivers(trace3, contextReceiverList, arrayList2);
                        listEmptyList = arrayList2;
                    } else {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    List<KtParameter> parameters = type.getParameters();
                    Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                    List<VariableDescriptor> listResolveParametersOfFunctionType = resolveParametersOfFunctionType(parameters);
                    checkParametersOfFunctionType(listResolveParametersOfFunctionType);
                    KtTypeReference returnTypeReference = type.getReturnTypeReference();
                    if (returnTypeReference == null) {
                        SimpleType unitType = TypeResolver.this.moduleDescriptor.getBuiltIns().getUnitType();
                        Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
                        simpleTypeResolveType = unitType;
                    } else {
                        TypeResolver typeResolver3 = TypeResolver.this;
                        TypeResolutionContext typeResolutionContextNoBareTypes3 = c.noBareTypes();
                        Intrinsics.checkNotNullExpressionValue(typeResolutionContextNoBareTypes3, "noBareTypes(...)");
                        simpleTypeResolveType = typeResolver3.resolveType(typeResolutionContextNoBareTypes3, returnTypeReference);
                    }
                    KotlinType kotlinType = simpleTypeResolveType;
                    KtParameterList parameterList = type.getParameterList();
                    if ((parameterList != null ? (KotlinPlaceHolderStub) parameterList.getStub() : null) == null) {
                        TrailingCommaChecker trailingCommaChecker = TrailingCommaChecker.INSTANCE;
                        PsiElement trailingComma = parameterList != null ? parameterList.getTrailingComma() : null;
                        BindingTrace trace4 = c.trace;
                        Intrinsics.checkNotNullExpressionValue(trace4, "trace");
                        trailingCommaChecker.check(trailingComma, trace4, TypeResolver.this.languageVersionSettings);
                    }
                    Ref.ObjectRef<PossiblyBareType> objectRef2 = objectRef;
                    KotlinBuiltIns builtIns = TypeResolver.this.moduleDescriptor.getBuiltIns();
                    Annotations annotations2 = annotations;
                    List<VariableDescriptor> list = listResolveParametersOfFunctionType;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        KotlinType type2 = ((VariableDescriptor) it.next()).getType();
                        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                        arrayList3.add(type2);
                    }
                    ArrayList arrayList4 = arrayList3;
                    ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator<T> it2 = list.iterator();
                    while (it2.hasNext()) {
                        Name name2 = ((VariableDescriptor) it2.next()).getShortName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        arrayList5.add(name2);
                    }
                    objectRef2.element = PossiblyBareType.type(FunctionTypesKt.createFunctionType(builtIns, annotations2, kotlinTypeResolveType, listEmptyList, arrayList4, arrayList5, kotlinType, zHasModifier));
                }

                private final void checkParametersOfFunctionType(List<? extends VariableDescriptor> parameterDescriptors) {
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : parameterDescriptors) {
                        if (!((VariableDescriptor) obj).getShortName().isSpecial()) {
                            arrayList.add(obj);
                        }
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Object obj2 : arrayList) {
                        Name name2 = ((VariableDescriptor) obj2).getShortName();
                        Object obj3 = linkedHashMap.get(name2);
                        if (obj3 == null) {
                            obj3 = (List) new ArrayList();
                            linkedHashMap.put(name2, obj3);
                        }
                        ((List) obj3).add(obj2);
                    }
                    for (List list : linkedHashMap.values()) {
                        if (list.size() >= 2) {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                SourceElement source = ((VariableDescriptor) it.next()).getSourceElement();
                                Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                                PsiElement psi = PsiSourceElementKt.getPsi(source);
                                KtParameter ktParameter = psi instanceof KtParameter ? (KtParameter) psi : null;
                                if (ktParameter != null) {
                                    c.trace.report(Errors.DUPLICATE_PARAMETER_NAME_IN_FUNCTION_TYPE.on(ktParameter));
                                }
                            }
                        }
                    }
                }

                private final List<VariableDescriptor> resolveParametersOfFunctionType(List<? extends KtParameter> parameters) {
                    List<? extends KtParameter> list = parameters;
                    TypeResolver typeResolver = TypeResolver.this;
                    TypeResolutionContext typeResolutionContext = c;
                    for (KtParameter ktParameter : list) {
                        BindingTrace trace2 = typeResolutionContext.trace;
                        Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                        typeResolver.identifierChecker.checkDeclaration(ktParameter, trace2);
                        checkParameterInFunctionType(ktParameter);
                    }
                    TypeResolver typeResolver2 = TypeResolver.this;
                    TypeResolutionContext typeResolutionContext2 = c;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (KtParameter ktParameter2 : list) {
                        TypeResolutionContext typeResolutionContextNoBareTypes = typeResolutionContext2.noBareTypes();
                        Intrinsics.checkNotNullExpressionValue(typeResolutionContextNoBareTypes, "noBareTypes(...)");
                        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter2.mo7396getTypeReference();
                        Intrinsics.checkNotNull(ktTypeReferenceMo7396getTypeReference);
                        final KotlinType kotlinTypeResolveType = typeResolver2.resolveType(typeResolutionContextNoBareTypes, ktTypeReferenceMo7396getTypeReference);
                        final DeclarationDescriptor ownerDescriptor = typeResolutionContext2.scope.getOwnerDescriptor();
                        AnnotationResolver annotationResolver = typeResolver2.annotationResolver;
                        LexicalScope scope = typeResolutionContext2.scope;
                        Intrinsics.checkNotNullExpressionValue(scope, "scope");
                        KtModifierList modifierList = ktParameter2.getModifierList();
                        BindingTrace trace3 = typeResolutionContext2.trace;
                        Intrinsics.checkNotNullExpressionValue(trace3, "trace");
                        final Annotations annotationsResolveAnnotationsWithoutArguments = annotationResolver.resolveAnnotationsWithoutArguments(scope, modifierList, trace3);
                        final Name nameAsSafeName = ktParameter2.getNameAsSafeName();
                        Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                        final SourceElement sourceElement = KotlinSourceElementKt.toSourceElement(ktParameter2);
                        VariableDescriptorImpl variableDescriptorImpl = new VariableDescriptorImpl(ownerDescriptor, annotationsResolveAnnotationsWithoutArguments, nameAsSafeName, kotlinTypeResolveType, sourceElement) { // from class: org.jetbrains.kotlin.resolve.TypeResolver$resolveTypeElement$1$resolveParametersOfFunctionType$ParameterOfFunctionTypeDescriptor
                            @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
                            public void cleanCompileTimeInitializerCache() {
                            }

                            public Void getCompileTimeInitializer() {
                                return null;
                            }

                            @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
                            public boolean isLateInit() {
                                return false;
                            }

                            @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
                            public boolean isVar() {
                                return false;
                            }

                            @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
                            /* JADX INFO: renamed from: getCompileTimeInitializer */
                            public /* bridge */ /* synthetic */ ConstantValue mo6754getCompileTimeInitializer() {
                                return (ConstantValue) getCompileTimeInitializer();
                            }

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(ownerDescriptor, annotationsResolveAnnotationsWithoutArguments, nameAsSafeName, kotlinTypeResolveType, sourceElement);
                                Intrinsics.checkNotNullParameter(ownerDescriptor, "containingDeclaration");
                                Intrinsics.checkNotNullParameter(annotationsResolveAnnotationsWithoutArguments, "annotations");
                                Intrinsics.checkNotNullParameter(nameAsSafeName, "name");
                                Intrinsics.checkNotNullParameter(kotlinTypeResolveType, "type");
                                Intrinsics.checkNotNullParameter(sourceElement, "source");
                            }

                            @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
                            /* JADX INFO: renamed from: getVisibility */
                            public DescriptorVisibility getVisibilityImpl() {
                                DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
                                Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
                                return LOCAL;
                            }

                            @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
                            public VariableDescriptor substitute(TypeSubstitutor substitutor) {
                                Intrinsics.checkNotNullParameter(substitutor, "substitutor");
                                throw new UnsupportedOperationException("Should not be called for descriptor of type " + getClass());
                            }

                            @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                            public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
                                Intrinsics.checkNotNullParameter(visitor, "visitor");
                                return visitor.visitVariableDescriptor(this, data);
                            }
                        };
                        typeResolutionContext2.trace.record(BindingContext.VALUE_PARAMETER, ktParameter2, variableDescriptorImpl);
                        arrayList.add(variableDescriptorImpl);
                    }
                    return arrayList;
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitContextReceiverList(KtContextReceiverList contextReceiverList) {
                    Intrinsics.checkNotNullParameter(contextReceiverList, "contextReceiverList");
                    BindingTrace trace2 = c.trace;
                    Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                    ContextReceiversUtilKt.checkContextReceiversAreEnabled(trace2, TypeResolver.this.languageVersionSettings, contextReceiverList);
                }

                /* JADX WARN: Type inference failed for: r1v5, types: [T, org.jetbrains.kotlin.resolve.PossiblyBareType] */
                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitDynamicType(KtDynamicType type) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    objectRef.element = PossiblyBareType.type(TypeUtilsKt.replaceAnnotations(TypeResolver.this.dynamicCallableDescriptors.getDynamicType(), annotations));
                    if (TypeResolver.this.dynamicTypesSettings.getDynamicTypesAllowed()) {
                        return;
                    }
                    c.trace.report(Errors.UNSUPPORTED.on(type, "Dynamic types are not supported in this context"));
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitSelfType(KtSelfType type) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    c.trace.report(Errors.UNSUPPORTED.on(type, "Self-types are not supported"));
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitKtElement(KtElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    c.trace.report(Errors.UNSUPPORTED.on(element, "Self-types are not supported yet"));
                }

                private final void checkParameterInFunctionType(KtParameter param) {
                    if (param.hasDefaultValue()) {
                        BindingTrace bindingTrace2 = c.trace;
                        DiagnosticFactory1<PsiElement, String> diagnosticFactory1 = Errors.UNSUPPORTED;
                        KtExpression defaultValue = param.getDefaultValue();
                        Intrinsics.checkNotNull(defaultValue);
                        bindingTrace2.report(diagnosticFactory1.on(defaultValue, "default value of parameter in function type"));
                    }
                    if (param.getMyName() != null) {
                        Iterator<KtAnnotationEntry> it = param.getAnnotationEntries().iterator();
                        while (it.hasNext()) {
                            c.trace.report(Errors.UNSUPPORTED.on(it.next(), "annotation on parameter in function type"));
                        }
                    }
                    KtModifierList modifierList = param.getModifierList();
                    if (modifierList != null) {
                        KtModifierKeywordToken[] MODIFIER_KEYWORDS_ARRAY = KtTokens.MODIFIER_KEYWORDS_ARRAY;
                        Intrinsics.checkNotNullExpressionValue(MODIFIER_KEYWORDS_ARRAY, "MODIFIER_KEYWORDS_ARRAY");
                        ArrayList arrayList = new ArrayList();
                        for (KtModifierKeywordToken ktModifierKeywordToken : MODIFIER_KEYWORDS_ARRAY) {
                            PsiElement modifier = modifierList.getModifier(ktModifierKeywordToken);
                            if (modifier != null) {
                                arrayList.add(modifier);
                            }
                        }
                        TypeResolutionContext typeResolutionContext = c;
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            typeResolutionContext.trace.report(Errors.UNSUPPORTED.on((PsiElement) it2.next(), "modifier on parameter in function type"));
                        }
                    }
                    PsiElement valOrVarKeyword = param.getValOrVarKeyword();
                    if (valOrVarKeyword != null) {
                        c.trace.report(Errors.UNSUPPORTED.on(valOrVarKeyword, "val or var on parameter in function type"));
                    }
                }
            });
        }
        PossiblyBareType possiblyBareType = (PossiblyBareType) objectRef.element;
        if (possiblyBareType != null) {
            return possiblyBareType;
        }
        ErrorTypeKind errorTypeKind = ErrorTypeKind.NO_TYPE_SPECIFIED;
        String[] strArr = new String[1];
        if (typeElement == null || (debugText = DebugTextUtilKt.getDebugText(typeElement)) == null) {
            debugText = "unknown element";
        }
        strArr[0] = debugText;
        PossiblyBareType possiblyBareTypeType = PossiblyBareType.type(ErrorUtils.createErrorType(errorTypeKind, strArr));
        Intrinsics.checkNotNullExpressionValue(possiblyBareTypeType, "type(...)");
        return possiblyBareTypeType;
    }

    private static final PsiElement resolveTypeElement$lambda$2(Lazy<? extends PsiElement> lazy) {
        return lazy.getValue();
    }

    private final boolean canHaveFunctionTypeModifiers(KtTypeElement ktTypeElement) {
        return ktTypeElement instanceof KtFunctionType;
    }

    private final KotlinType resolveTypeForTypeParameter(TypeResolutionContext c, Annotations annotations, TypeParameterDescriptor typeParameter, KtSimpleNameExpression referenceExpression, KtTypeArgumentList typeArgumentList) {
        MemberScope scopeForTypeParameter = getScopeForTypeParameter(c, typeParameter);
        if (typeArgumentList != null) {
            TypeConstructor constructor = ErrorUtils.createErrorType(ErrorTypeKind.ERROR_TYPE_PARAMETER, new String[0]).getConstructor();
            List<KtTypeProjection> arguments = typeArgumentList.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            resolveTypeProjections(c, constructor, arguments);
            c.trace.report(Errors.TYPE_ARGUMENTS_NOT_ALLOWED.on(typeArgumentList, "for type parameters"));
        }
        DeclarationDescriptor containingDeclaration = typeParameter.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof ClassDescriptor) {
            DescriptorResolver.checkHasOuterClassInstance(c.scope, c.trace, referenceExpression, (ClassDescriptor) containingDeclaration);
        }
        if ((scopeForTypeParameter instanceof ErrorScope) && !(scopeForTypeParameter instanceof ThrowingScope)) {
            return ErrorUtils.createErrorType(ErrorTypeKind.ERROR_TYPE_PARAMETER, new String[0]);
        }
        TypeAttributeTranslators typeAttributeTranslators = this.typeAttributeTranslators;
        TypeConstructor typeConstructor = typeParameter.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        TypeAttributes attributes = typeAttributeTranslators.toAttributes(annotations, typeConstructor, containingDeclaration);
        TypeConstructor typeConstructor2 = typeParameter.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "getTypeConstructor(...)");
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(attributes, typeConstructor2, CollectionsKt.emptyList(), false, scopeForTypeParameter);
    }

    private final MemberScope getScopeForTypeParameter(TypeResolutionContext c, final TypeParameterDescriptor typeParameterDescriptor) {
        return c.checkBounds ? TypeIntersector.getUpperBoundsAsType(typeParameterDescriptor).getMemberScope() : new LazyScopeAdapter(null, new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.resolve.TypeResolver.getScopeForTypeParameter.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberScope invoke() {
                return TypeIntersector.getUpperBoundsAsType(typeParameterDescriptor).getMemberScope();
            }
        }, 1, null);
    }

    public final PossiblyBareType resolveTypeForClassifier(TypeResolutionContext c, ClassifierDescriptor descriptor, QualifiedExpressionResolver.TypeQualifierResolutionResult qualifierResolutionResult, KtElement element, Annotations annotations) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(qualifierResolutionResult, "qualifierResolutionResult");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        List<QualifiedExpressionResolver.ExpressionQualifierPart> qualifierParts = qualifierResolutionResult.getQualifierParts();
        if (element instanceof KtUserType) {
            KtUserType ktUserType = (KtUserType) element;
            if (ktUserType.getStub() == 0) {
                TrailingCommaChecker trailingCommaChecker = TrailingCommaChecker.INSTANCE;
                KtTypeArgumentList typeArgumentList = ktUserType.getTypeArgumentList();
                PsiElement trailingComma = typeArgumentList != null ? typeArgumentList.getTrailingComma() : null;
                BindingTrace trace = c.trace;
                Intrinsics.checkNotNullExpressionValue(trace, "trace");
                trailingCommaChecker.check(trailingComma, trace, this.languageVersionSettings);
            }
        }
        if (descriptor instanceof TypeParameterDescriptor) {
            qualifierParts.size();
            QualifiedExpressionResolver.ExpressionQualifierPart expressionQualifierPart = (QualifiedExpressionResolver.ExpressionQualifierPart) CollectionsKt.single((List) qualifierParts);
            PossiblyBareType possiblyBareTypeType = PossiblyBareType.type(resolveTypeForTypeParameter(c, annotations, (TypeParameterDescriptor) descriptor, expressionQualifierPart.getExpression(), expressionQualifierPart.getTypeArguments()));
            Intrinsics.checkNotNull(possiblyBareTypeType);
            return possiblyBareTypeType;
        }
        if (descriptor instanceof ClassDescriptor) {
            return resolveTypeForClass(c, annotations, (ClassDescriptor) descriptor, element, qualifierResolutionResult);
        }
        if (descriptor instanceof TypeAliasDescriptor) {
            return resolveTypeForTypeAlias(c, annotations, (TypeAliasDescriptor) descriptor, element, qualifierResolutionResult);
        }
        throw new IllegalStateException(("Unexpected classifier type: " + descriptor.getClass()).toString());
    }

    private final PossiblyBareType resolveTypeForClass(TypeResolutionContext c, Annotations annotations, ClassDescriptor classDescriptor, KtElement element, QualifiedExpressionResolver.TypeQualifierResolutionResult qualifierResolutionResult) {
        TypeConstructor typeConstructor = classDescriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        List<KtTypeProjection> allProjections = qualifierResolutionResult.getAllProjections();
        List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        int i = 0;
        if (c.allowBareTypes && allProjections.isEmpty() && isPossibleToSpecifyTypeArgumentsFor(classDescriptor)) {
            PossiblyBareType possiblyBareTypeBare = PossiblyBareType.bare(typeConstructor, false);
            Intrinsics.checkNotNullExpressionValue(possiblyBareTypeBare, "bare(...)");
            return possiblyBareTypeBare;
        }
        if (ErrorUtils.isError(classDescriptor)) {
            return createErrorTypeForTypeConstructor(c, allProjections, typeConstructor);
        }
        Pair<List<KtTypeProjection>, List<TypeProjection>> pairCollectArgumentsForClassifierTypeConstructor = collectArgumentsForClassifierTypeConstructor(c, classDescriptor, qualifierResolutionResult.getQualifierParts());
        if (pairCollectArgumentsForClassifierTypeConstructor != null) {
            List<KtTypeProjection> listComponent1 = pairCollectArgumentsForClassifierTypeConstructor.component1();
            List<TypeProjection> listComponent2 = pairCollectArgumentsForClassifierTypeConstructor.component2();
            listComponent1.size();
            parameters.size();
            List<TypeProjection> listBuildFinalArgumentList = buildFinalArgumentList(resolveTypeProjections(c, typeConstructor, listComponent1), listComponent2, parameters);
            listBuildFinalArgumentList.size();
            parameters.size();
            TypeAttributeTranslators typeAttributeTranslators = this.typeAttributeTranslators;
            TypeConstructor typeConstructor2 = classDescriptor.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "getTypeConstructor(...)");
            SimpleType simpleTypeSimpleNotNullType = KotlinTypeFactory.simpleNotNullType(typeAttributeTranslators.toAttributes(annotations, typeConstructor2, c.scope.getOwnerDescriptor()), classDescriptor, listBuildFinalArgumentList);
            KotlinType kotlinTypeTransformType = this.typeTransformerForTests.transformType(simpleTypeSimpleNotNullType);
            if (kotlinTypeTransformType != null) {
                PossiblyBareType possiblyBareTypeType = PossiblyBareType.type(kotlinTypeTransformType);
                Intrinsics.checkNotNullExpressionValue(possiblyBareTypeType, "type(...)");
                return possiblyBareTypeType;
            }
            if (shouldCheckBounds(c, simpleTypeSimpleNotNullType)) {
                TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(simpleTypeSimpleNotNullType);
                Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
                for (int size = parameters.size(); i < size; size = size) {
                    TypeParameterDescriptor typeParameterDescriptor = parameters.get(i);
                    KotlinType type = listBuildFinalArgumentList.get(i).getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    KtTypeProjection ktTypeProjection = (KtTypeProjection) CollectionsKt.getOrNull(listComponent1, i);
                    KtTypeReference typeReference = ktTypeProjection != null ? ktTypeProjection.getTypeReference() : null;
                    if (typeReference != null) {
                        UpperBoundChecker upperBoundChecker = this.upperBoundChecker;
                        Intrinsics.checkNotNull(typeParameterDescriptor);
                        BindingTrace trace = c.trace;
                        Intrinsics.checkNotNullExpressionValue(trace, "trace");
                        UpperBoundChecker.checkBounds$default(upperBoundChecker, typeReference, type, typeParameterDescriptor, typeSubstitutorCreate, trace, null, null, 96, null);
                    }
                    i++;
                }
            }
            if (TypeUtilsKt.isArrayOfNothing(simpleTypeSimpleNotNullType)) {
                c.trace.report(Errors.UNSUPPORTED.on(element, "Array<Nothing> is illegal"));
            }
            PossiblyBareType possiblyBareTypeType2 = PossiblyBareType.type(simpleTypeSimpleNotNullType);
            Intrinsics.checkNotNullExpressionValue(possiblyBareTypeType2, "type(...)");
            return possiblyBareTypeType2;
        }
        return createErrorTypeForTypeConstructor(c, allProjections, typeConstructor);
    }

    private final List<TypeProjection> buildFinalArgumentList(List<? extends TypeProjection> argumentsFromUserType, List<? extends TypeProjection> argumentsForOuterClass, List<? extends TypeParameterDescriptor> parameters) {
        List<? extends TypeProjection> list = argumentsFromUserType;
        if (argumentsForOuterClass == null) {
            argumentsForOuterClass = appendDefaultArgumentsForLocalClassifier(argumentsFromUserType.size(), parameters);
        }
        return CollectionsKt.plus((Collection) list, (Iterable) argumentsForOuterClass);
    }

    private final boolean shouldCheckBounds(TypeResolutionContext c, KotlinType inType) {
        if (c.checkBounds && !TypeUtilsKt.containsTypeAliasParameters(inType)) {
            return (c.abbreviated && TypeUtilsKt.containsTypeAliases(inType)) ? false : true;
        }
        return false;
    }

    private final PossiblyBareType resolveTypeForTypeAlias(TypeResolutionContext c, Annotations annotations, TypeAliasDescriptor descriptor, KtElement type, QualifiedExpressionResolver.TypeQualifierResolutionResult qualifierResolutionResult) {
        TypeConstructor typeConstructor = descriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        List<KtTypeProjection> allProjections = qualifierResolutionResult.getAllProjections();
        if (ErrorUtils.isError(descriptor)) {
            return createErrorTypeForTypeConstructor(c, allProjections, typeConstructor);
        }
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.TypeAliases)) {
            c.trace.report(Errors.UNSUPPORTED_FEATURE.on(type, TuplesKt.to(LanguageFeature.TypeAliases, this.languageVersionSettings)));
            return createErrorTypeForTypeConstructor(c, allProjections, typeConstructor);
        }
        List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        if (c.allowBareTypes && allProjections.isEmpty() && isPossibleToSpecifyTypeArgumentsFor(descriptor) && descriptor.getClassDescriptor() != null && canBeUsedAsBareType(descriptor)) {
            ClassDescriptor classDescriptor = descriptor.getClassDescriptor();
            Intrinsics.checkNotNull(classDescriptor);
            PossiblyBareType possiblyBareTypeBare = PossiblyBareType.bare(classDescriptor.getTypeConstructor(), TypeUtils.isNullableType(descriptor.getExpandedType()));
            Intrinsics.checkNotNullExpressionValue(possiblyBareTypeBare, "bare(...)");
            return possiblyBareTypeBare;
        }
        QualifiedExpressionResolver.ExpressionQualifierPart expressionQualifierPart = (QualifiedExpressionResolver.ExpressionQualifierPart) CollectionsKt.lastOrNull((List) qualifierResolutionResult.getQualifierParts());
        if (expressionQualifierPart == null) {
            return createErrorTypeForTypeConstructor(c, allProjections, typeConstructor);
        }
        Pair<List<KtTypeProjection>, List<TypeProjection>> pairCollectArgumentsForClassifierTypeConstructor = collectArgumentsForClassifierTypeConstructor(c, descriptor, qualifierResolutionResult.getQualifierParts());
        if (pairCollectArgumentsForClassifierTypeConstructor != null) {
            List<KtTypeProjection> listComponent1 = pairCollectArgumentsForClassifierTypeConstructor.component1();
            List<TypeProjection> listBuildFinalArgumentList = buildFinalArgumentList(resolveTypeProjections(c, typeConstructor, listComponent1), pairCollectArgumentsForClassifierTypeConstructor.component2(), parameters);
            BindingTrace trace = c.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            KtTypeArgumentList typeArguments = expressionQualifierPart.getTypeArguments();
            KtElement expression = typeArguments != null ? typeArguments : expressionQualifierPart.getExpression();
            List<TypeParameterDescriptor> declaredTypeParameters = descriptor.getDeclaredTypeParameters();
            Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
            TracingTypeAliasExpansionReportStrategy tracingTypeAliasExpansionReportStrategy = new TracingTypeAliasExpansionReportStrategy(trace, type, expression, descriptor, declaredTypeParameters, listComponent1, this.upperBoundChecker);
            if (parameters.size() != listBuildFinalArgumentList.size()) {
                tracingTypeAliasExpansionReportStrategy.wrongNumberOfTypeArguments(descriptor, parameters.size());
                return createErrorTypeForTypeConstructor(c, allProjections, typeConstructor);
            }
            TypeAttributeTranslators typeAttributeTranslators = this.typeAttributeTranslators;
            TypeConstructor typeConstructor2 = descriptor.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "getTypeConstructor(...)");
            TypeAttributes attributes = typeAttributeTranslators.toAttributes(annotations, typeConstructor2, c.scope.getOwnerDescriptor());
            if (c.abbreviated) {
                TypeConstructor typeConstructor3 = descriptor.getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor3, "getTypeConstructor(...)");
                PossiblyBareType possiblyBareTypeType = PossiblyBareType.type(KotlinTypeFactory.simpleType$default(attributes, typeConstructor3, (List) listBuildFinalArgumentList, false, (KotlinTypeRefiner) null, 16, (Object) null));
                Intrinsics.checkNotNull(possiblyBareTypeType);
                return possiblyBareTypeType;
            }
            PossiblyBareType possiblyBareTypeType2 = PossiblyBareType.type(new TypeAliasExpander(tracingTypeAliasExpansionReportStrategy, c.checkBounds).expand(TypeAliasExpansion.INSTANCE.create(null, descriptor, listBuildFinalArgumentList), attributes));
            Intrinsics.checkNotNull(possiblyBareTypeType2);
            return possiblyBareTypeType2;
        }
        return createErrorTypeForTypeConstructor(c, allProjections, typeConstructor);
    }

    private final boolean canBeUsedAsBareType(TypeAliasDescriptor descriptor) {
        ClassDescriptor classDescriptor;
        SimpleType expandedType = descriptor.getExpandedType();
        if (KotlinTypeKt.isError(expandedType) || (classDescriptor = descriptor.getClassDescriptor()) == null || !isPossibleToSpecifyTypeArgumentsFor(classDescriptor)) {
            return false;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (TypeProjection typeProjection : expandedType.getArguments()) {
            if (!typeProjection.isStarProjection()) {
                if (typeProjection.getProjectionKind() != Variance.INVARIANT) {
                    return false;
                }
                ClassifierDescriptor this$0 = typeProjection.getNullableAnyType().getConstructor().getThis$0();
                TypeParameterDescriptor typeParameterDescriptor = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
                if (typeParameterDescriptor == null || !Intrinsics.areEqual(typeParameterDescriptor.getContainingDeclaration(), descriptor) || linkedHashSet.contains(typeParameterDescriptor)) {
                    return false;
                }
                linkedHashSet.add(typeParameterDescriptor);
            }
        }
        return true;
    }

    /* JADX INFO: compiled from: TypeResolver.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J(\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020\u000bH\u0016J\"\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\b2\b\u0010#\u001a\u0004\u0018\u00010\u000b2\u0006\u0010&\u001a\u00020!H\u0016J\u0010\u0010'\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\bH\u0016J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020*H\u0016J\u0018\u0010+\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\b2\u0006\u0010,\u001a\u00020-H\u0016R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\r0\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/resolve/TypeResolver$TracingTypeAliasExpansionReportStrategy;", "Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/psi/KtElement;", "typeArgumentsOrTypeName", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "typeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "typeArguments", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "upperBoundChecker", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;)V", "mappedArguments", "", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "getType", "()Lorg/jetbrains/kotlin/psi/KtElement;", "getTypeAliasDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getTypeArgumentsOrTypeName", "getUpperBoundChecker", "()Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "boundsViolationInSubstitution", "", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "unsubstitutedArgument", "Lorg/jetbrains/kotlin/types/KotlinType;", "argument", "typeParameter", "conflictingProjection", "typeAlias", "substitutedArgument", "recursiveTypeAlias", "repeatedAnnotation", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "wrongNumberOfTypeArguments", "numberOfParameters", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TracingTypeAliasExpansionReportStrategy implements TypeAliasExpansionReportStrategy {
        private final Map<TypeParameterDescriptor, KtTypeProjection> mappedArguments;
        private final BindingTrace trace;
        private final KtElement type;
        private final TypeAliasDescriptor typeAliasDescriptor;
        private final KtElement typeArgumentsOrTypeName;
        private final UpperBoundChecker upperBoundChecker;

        public TracingTypeAliasExpansionReportStrategy(BindingTrace trace, KtElement ktElement, KtElement ktElement2, TypeAliasDescriptor typeAliasDescriptor, List<? extends TypeParameterDescriptor> typeParameters, List<? extends KtTypeProjection> typeArguments, UpperBoundChecker upperBoundChecker) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
            Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
            Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
            Intrinsics.checkNotNullParameter(upperBoundChecker, "upperBoundChecker");
            this.trace = trace;
            this.type = ktElement;
            this.typeArgumentsOrTypeName = ktElement2;
            this.typeAliasDescriptor = typeAliasDescriptor;
            this.upperBoundChecker = upperBoundChecker;
            this.mappedArguments = MapsKt.toMap(CollectionsKt.zip(typeParameters, typeArguments));
        }

        public final BindingTrace getTrace() {
            return this.trace;
        }

        public final KtElement getType() {
            return this.type;
        }

        public final KtElement getTypeArgumentsOrTypeName() {
            return this.typeArgumentsOrTypeName;
        }

        public final TypeAliasDescriptor getTypeAliasDescriptor() {
            return this.typeAliasDescriptor;
        }

        public final UpperBoundChecker getUpperBoundChecker() {
            return this.upperBoundChecker;
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void wrongNumberOfTypeArguments(TypeAliasDescriptor typeAlias, int numberOfParameters) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            if (this.typeArgumentsOrTypeName != null) {
                this.trace.report(Errors.WRONG_NUMBER_OF_TYPE_ARGUMENTS.on(this.typeArgumentsOrTypeName, Integer.valueOf(numberOfParameters), this.typeAliasDescriptor));
            }
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void conflictingProjection(TypeAliasDescriptor typeAlias, TypeParameterDescriptor typeParameter, KotlinType substitutedArgument) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(substitutedArgument, "substitutedArgument");
            KtTypeProjection ktTypeProjection = typeParameter != null ? this.mappedArguments.get(typeParameter) : null;
            if (ktTypeProjection != null) {
                this.trace.report(Errors.CONFLICTING_PROJECTION.on(ktTypeProjection, typeParameter));
            } else if (this.type != null) {
                this.trace.report(Errors.CONFLICTING_PROJECTION_IN_TYPEALIAS_EXPANSION.on(this.type, this.typeAliasDescriptor.getUnderlyingType()));
            }
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void recursiveTypeAlias(TypeAliasDescriptor typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            if (this.type != null) {
                this.trace.report(Errors.RECURSIVE_TYPEALIAS_EXPANSION.on(this.type, typeAlias));
            }
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void boundsViolationInSubstitution(TypeSubstitutor substitutor, KotlinType unsubstitutedArgument, KotlinType argument, TypeParameterDescriptor typeParameter) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            Intrinsics.checkNotNullParameter(unsubstitutedArgument, "unsubstitutedArgument");
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            KtTypeProjection ktTypeProjection = this.mappedArguments.get(unsubstitutedArgument.getConstructor().getThis$0());
            UpperBoundChecker.checkBounds$default(this.upperBoundChecker, ktTypeProjection != null ? ktTypeProjection.getTypeReference() : null, argument, typeParameter, substitutor, this.trace, this.type, null, 64, null);
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void repeatedAnnotation(AnnotationDescriptor annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            SourceElement source = annotation.getSource();
            KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
            KtElement psi = kotlinSourceElement != null ? kotlinSourceElement.getPsi() : null;
            KtAnnotationEntry ktAnnotationEntry = psi instanceof KtAnnotationEntry ? (KtAnnotationEntry) psi : null;
            if (ktAnnotationEntry == null) {
                return;
            }
            this.trace.report(Errors.REPEATED_ANNOTATION.on(ktAnnotationEntry));
        }
    }

    private final PossiblyBareType createErrorTypeForTypeConstructor(TypeResolutionContext c, List<? extends KtTypeProjection> arguments, TypeConstructor typeConstructor) {
        String string;
        Name name2;
        ErrorUtils errorUtils = ErrorUtils.INSTANCE;
        ErrorTypeKind errorTypeKind = ErrorTypeKind.TYPE_FOR_ERROR_TYPE_CONSTRUCTOR;
        List<? extends TypeProjection> listResolveTypeProjectionsWithErrorConstructor$default = resolveTypeProjectionsWithErrorConstructor$default(this, c, arguments, null, 4, null);
        String[] strArr = new String[1];
        ClassifierDescriptor this$0 = typeConstructor.getThis$0();
        if (this$0 == null || (name2 = this$0.getShortName()) == null || (string = name2.asString()) == null) {
            string = typeConstructor.toString();
        }
        Intrinsics.checkNotNull(string);
        strArr[0] = string;
        PossiblyBareType possiblyBareTypeType = PossiblyBareType.type(errorUtils.createErrorTypeWithArguments(errorTypeKind, listResolveTypeProjectionsWithErrorConstructor$default, strArr));
        Intrinsics.checkNotNullExpressionValue(possiblyBareTypeType, "type(...)");
        return possiblyBareTypeType;
    }

    private final boolean isPossibleToSpecifyTypeArgumentsFor(ClassifierDescriptorWithTypeParameters classifierDescriptor) {
        List<TypeParameterDescriptor> parameters = classifierDescriptor.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) CollectionsKt.firstOrNull((List) parameters);
        if (typeParameterDescriptor == null) {
            return false;
        }
        return typeParameterDescriptor.getOriginal().getContainingDeclaration() instanceof ClassifierDescriptorWithTypeParameters;
    }

    private final Pair<List<KtTypeProjection>, List<TypeProjection>> collectArgumentsForClassifierTypeConstructor(TypeResolutionContext c, ClassifierDescriptorWithTypeParameters classifierDescriptor, List<QualifiedExpressionResolver.ExpressionQualifierPart> qualifierParts) {
        ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters = classifierDescriptor;
        List<ClassifierDescriptorWithTypeParameters> listClassifierDescriptorsFromInnerToOuter = classifierDescriptorsFromInnerToOuter(classifierDescriptorWithTypeParameters);
        List listAsReversed = CollectionsKt.asReversed(qualifierParts);
        SmartList smartList = new SmartList();
        int iMin = Math.min(listClassifierDescriptorsFromInnerToOuter.size(), listAsReversed.size());
        int i = iMin - 1;
        if (i >= 0) {
            int i2 = 0;
            boolean z = false;
            while (true) {
                QualifiedExpressionResolver.ExpressionQualifierPart expressionQualifierPart = (QualifiedExpressionResolver.ExpressionQualifierPart) listAsReversed.get(i2);
                KtTypeArgumentList typeArguments = expressionQualifierPart.getTypeArguments();
                List<KtTypeProjection> arguments = typeArguments != null ? typeArguments.getArguments() : null;
                if (arguments == null) {
                    arguments = CollectionsKt.emptyList();
                }
                List<TypeParameterDescriptor> declaredTypeParameters = listClassifierDescriptorsFromInnerToOuter.get(i2).getDeclaredTypeParameters();
                Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
                List<TypeParameterDescriptor> listEmptyList = z ? CollectionsKt.emptyList() : declaredTypeParameters;
                if (z && !arguments.isEmpty() && !declaredTypeParameters.isEmpty()) {
                    BindingTrace bindingTrace = c.trace;
                    DiagnosticFactory0<KtTypeArgumentList> diagnosticFactory0 = Errors.TYPE_ARGUMENTS_FOR_OUTER_CLASS_WHEN_NESTED_REFERENCED;
                    KtTypeArgumentList typeArguments2 = expressionQualifierPart.getTypeArguments();
                    Intrinsics.checkNotNull(typeArguments2);
                    bindingTrace.report(diagnosticFactory0.on(typeArguments2));
                    return null;
                }
                if (arguments.size() != listEmptyList.size()) {
                    BindingTrace bindingTrace2 = c.trace;
                    DiagnosticFactory2<KtElement, Integer, DeclarationDescriptor> diagnosticFactory2 = Errors.WRONG_NUMBER_OF_TYPE_ARGUMENTS;
                    KtElement typeArguments3 = expressionQualifierPart.getTypeArguments();
                    if (typeArguments3 == null) {
                        typeArguments3 = expressionQualifierPart.getExpression();
                    }
                    bindingTrace2.report(diagnosticFactory2.on(typeArguments3, Integer.valueOf(listEmptyList.size()), listClassifierDescriptorsFromInnerToOuter.get(i2)));
                    return null;
                }
                smartList.addAll(arguments);
                z = z || !listClassifierDescriptorsFromInnerToOuter.get(i2).getIsInner();
                if (i2 == i) {
                    break;
                }
                i2++;
            }
        }
        Iterator it = listAsReversed.subList(Math.min(iMin, listAsReversed.size()), listAsReversed.size()).iterator();
        while (it.hasNext()) {
            KtTypeArgumentList ktTypeArgumentListComponent3 = ((QualifiedExpressionResolver.ExpressionQualifierPart) it.next()).getTypeArguments();
            if (ktTypeArgumentListComponent3 != null) {
                c.trace.report(Errors.TYPE_ARGUMENTS_NOT_ALLOWED.on(ktTypeArgumentListComponent3, "here"));
                return null;
            }
        }
        List<TypeParameterDescriptor> parameters = classifierDescriptor.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        if (smartList.size() < parameters.size()) {
            DeclarationDescriptor containingDeclaration = parameters.get(smartList.size()).getOriginal().getContainingDeclaration();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor == null) {
                return new Pair<>(smartList, null);
            }
            LexicalScope scope = c.scope;
            Intrinsics.checkNotNullExpressionValue(scope, "scope");
            List<TypeProjection> listFindImplicitOuterClassArguments = findImplicitOuterClassArguments(scope, classDescriptor);
            List<TypeParameterDescriptor> listSubList = parameters.subList(smartList.size(), parameters.size());
            Iterator<T> it2 = classifierDescriptorsFromInnerToOuter(classifierDescriptorWithTypeParameters).iterator();
            int size = 0;
            while (it2.hasNext()) {
                size += ((ClassifierDescriptorWithTypeParameters) it2.next()).getDeclaredTypeParameters().size();
            }
            if (listFindImplicitOuterClassArguments == null && size > smartList.size()) {
                c.trace.report(Errors.OUTER_CLASS_ARGUMENTS_REQUIRED.on(((QualifiedExpressionResolver.ExpressionQualifierPart) CollectionsKt.first((List) qualifierParts)).getExpression(), classDescriptor));
                return null;
            }
            if (listFindImplicitOuterClassArguments == null) {
                smartList.size();
                return new Pair<>(smartList, null);
            }
            listSubList.size();
            listFindImplicitOuterClassArguments.size();
            return new Pair<>(smartList, listFindImplicitOuterClassArguments);
        }
        return new Pair<>(smartList, null);
    }

    private final List<ClassifierDescriptorWithTypeParameters> classifierDescriptorsFromInnerToOuter(final ClassifierDescriptor classifierDescriptor) {
        return SequencesKt.toList(SequencesKt.generateSequence((Function0) new Function0<ClassifierDescriptorWithTypeParameters>() { // from class: org.jetbrains.kotlin.resolve.TypeResolver.classifierDescriptorsFromInnerToOuter.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassifierDescriptorWithTypeParameters invoke() {
                ClassifierDescriptor classifierDescriptor2 = classifierDescriptor;
                if (classifierDescriptor2 instanceof ClassifierDescriptorWithTypeParameters) {
                    return (ClassifierDescriptorWithTypeParameters) classifierDescriptor2;
                }
                return null;
            }
        }, (Function1) new Function1<ClassifierDescriptorWithTypeParameters, ClassifierDescriptorWithTypeParameters>() { // from class: org.jetbrains.kotlin.resolve.TypeResolver.classifierDescriptorsFromInnerToOuter.2
            @Override // kotlin.jvm.functions.Function1
            public final ClassifierDescriptorWithTypeParameters invoke(ClassifierDescriptorWithTypeParameters it) {
                Intrinsics.checkNotNullParameter(it, "it");
                DeclarationDescriptor containingDeclaration = it.getContainingDeclaration();
                if (containingDeclaration instanceof ClassifierDescriptorWithTypeParameters) {
                    return (ClassifierDescriptorWithTypeParameters) containingDeclaration;
                }
                return null;
            }
        }));
    }

    static /* synthetic */ List resolveTypeProjectionsWithErrorConstructor$default(TypeResolver typeResolver, TypeResolutionContext typeResolutionContext, List list, String str, int i, Object obj) {
        if ((i & 4) != 0) {
            str = "Error type for resolving type projections";
        }
        return typeResolver.resolveTypeProjectionsWithErrorConstructor(typeResolutionContext, list, str);
    }

    private final List<TypeProjection> resolveTypeProjectionsWithErrorConstructor(TypeResolutionContext c, List<? extends KtTypeProjection> argumentElements, String message) {
        return resolveTypeProjections(c, ErrorUtils.INSTANCE.createErrorTypeConstructor(ErrorTypeKind.TYPE_FOR_ERROR_TYPE_CONSTRUCTOR, message), argumentElements);
    }

    private final List<TypeProjectionImpl> appendDefaultArgumentsForLocalClassifier(int fromIndex, List<? extends TypeParameterDescriptor> constructorParameters) {
        List<? extends TypeParameterDescriptor> listSubList = constructorParameters.subList(fromIndex, constructorParameters.size());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSubList, 10));
        Iterator<T> it = listSubList.iterator();
        while (it.hasNext()) {
            arrayList.add(new TypeProjectionImpl(((TypeParameterDescriptor) it.next()).getOriginal().getDefaultType()));
        }
        return arrayList;
    }

    public final List<TypeProjection> resolveTypeProjections(TypeResolutionContext c, TypeConstructor constructor, List<? extends KtTypeProjection> argumentElements) {
        TypeArgumentMarker typeArgumentMarkerMakeStarProjection;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(argumentElements, "argumentElements");
        List<? extends KtTypeProjection> list = argumentElements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KtTypeProjection ktTypeProjection = (KtTypeProjection) obj;
            KtProjectionKind projectionKind = ktTypeProjection.getProjectionKind();
            Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
            BindingTrace trace = c.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            ModifierCheckerCore.INSTANCE.check(ktTypeProjection, trace, null, this.languageVersionSettings);
            if (projectionKind == KtProjectionKind.STAR) {
                List<TypeParameterDescriptor> parameters = constructor.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                if (parameters.size() > i) {
                    typeArgumentMarkerMakeStarProjection = TypeUtils.makeStarProjection(parameters.get(i));
                } else {
                    typeArgumentMarkerMakeStarProjection = (TypeProjection) new TypeProjectionImpl(Variance.OUT_VARIANCE, ErrorUtils.createErrorType(ErrorTypeKind.ERROR_TYPE_PROJECTION, new String[0]));
                }
            } else {
                TypeResolutionContext typeResolutionContextNoBareTypes = c.noBareTypes();
                Intrinsics.checkNotNullExpressionValue(typeResolutionContextNoBareTypes, "noBareTypes(...)");
                KtTypeReference typeReference = ktTypeProjection.getTypeReference();
                Intrinsics.checkNotNull(typeReference);
                KotlinType kotlinTypeResolveType = resolveType(typeResolutionContextNoBareTypes, typeReference);
                Variance varianceResolveProjectionKind = INSTANCE.resolveProjectionKind(projectionKind);
                if (constructor.getParameters().size() > i) {
                    TypeParameterDescriptor typeParameterDescriptor = constructor.getParameters().get(i);
                    if (varianceResolveProjectionKind != Variance.INVARIANT && typeParameterDescriptor.getVariance() != Variance.INVARIANT) {
                        if (varianceResolveProjectionKind == typeParameterDescriptor.getVariance()) {
                            BindingTrace bindingTrace = c.trace;
                            ClassifierDescriptor this$0 = constructor.getThis$0();
                            Intrinsics.checkNotNull(this$0);
                            bindingTrace.report(Errors.REDUNDANT_PROJECTION.on(ktTypeProjection, this$0));
                        } else {
                            BindingTrace bindingTrace2 = c.trace;
                            ClassifierDescriptor this$1 = constructor.getThis$0();
                            Intrinsics.checkNotNull(this$1);
                            bindingTrace2.report(Errors.CONFLICTING_PROJECTION.on(ktTypeProjection, this$1));
                        }
                    }
                }
                typeArgumentMarkerMakeStarProjection = (TypeProjection) new TypeProjectionImpl(varianceResolveProjectionKind, kotlinTypeResolveType);
            }
            arrayList.add(typeArgumentMarkerMakeStarProjection);
            i = i2;
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:16:0x002c  */
    /* JADX WARN: Code duplicated, block: B:8:0x001e  */
    private final List<TypeProjection> findImplicitOuterClassArguments(LexicalScope lexicalScope, ClassDescriptor classDescriptor) {
        ClassDescriptor classDescriptor2;
        LexicalScope lexicalScope2 = lexicalScope;
        do {
            if (lexicalScope2 instanceof LexicalScope) {
                LexicalScope lexicalScope3 = (LexicalScope) lexicalScope2;
                if (lexicalScope3.getKind() == LexicalScopeKind.CLASS_MEMBER_SCOPE) {
                    DeclarationDescriptor ownerDescriptor = lexicalScope3.getOwnerDescriptor();
                    Intrinsics.checkNotNull(ownerDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    classDescriptor2 = (ClassDescriptor) ownerDescriptor;
                } else {
                    classDescriptor2 = null;
                }
            } else {
                classDescriptor2 = null;
            }
            if (classDescriptor2 == null) {
                lexicalScope2 = lexicalScope2.getParent();
            }
            if (classDescriptor2 == null) {
                return null;
            }
            return DescriptorUtilsKt.findImplicitOuterClassArguments(classDescriptor2, classDescriptor);
        } while (lexicalScope2 != null);
        classDescriptor2 = null;
        if (classDescriptor2 == null) {
            return null;
        }
        return DescriptorUtilsKt.findImplicitOuterClassArguments(classDescriptor2, classDescriptor);
    }

    public final ClassifierDescriptor resolveClass(LexicalScope scope, KtUserType userType, BindingTrace trace, boolean isDebuggerContext) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(userType, "userType");
        Intrinsics.checkNotNullParameter(trace, "trace");
        return resolveDescriptorForType(scope, userType, trace, isDebuggerContext).getClassifierDescriptor();
    }

    public final QualifiedExpressionResolver.TypeQualifierResolutionResult resolveDescriptorForType(LexicalScope scope, KtUserType userType, BindingTrace trace, boolean isDebuggerContext) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(userType, "userType");
        Intrinsics.checkNotNullParameter(trace, "trace");
        if (userType.getQualifier() != null) {
            KtUserType qualifier = userType.getQualifier();
            Intrinsics.checkNotNull(qualifier);
            Iterator<KtTypeProjection> it = qualifier.getTypeArguments().iterator();
            while (it.hasNext()) {
                KtTypeReference typeReference = it.next().getTypeReference();
                if (typeReference != null) {
                    forceResolveTypeContents(resolveType(scope, typeReference, trace, false));
                }
            }
        }
        QualifiedExpressionResolver.TypeQualifierResolutionResult typeQualifierResolutionResultResolveDescriptorForType = this.qualifiedExpressionResolver.resolveDescriptorForType(userType, scope, trace, isDebuggerContext);
        if (typeQualifierResolutionResultResolveDescriptorForType.getClassifierDescriptor() != null) {
            PlatformClassesMappedToKotlinChecker.reportPlatformClassMappedToKotlin(this.platformToKotlinClassMapper, trace, userType, typeQualifierResolutionResultResolveDescriptorForType.getClassifierDescriptor());
        }
        return typeQualifierResolutionResultResolveDescriptorForType;
    }

    /* JADX INFO: compiled from: TypeResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/TypeResolver$Companion;", "", "()V", "resolveProjectionKind", "Lorg/jetbrains/kotlin/types/Variance;", "projectionKind", "Lorg/jetbrains/kotlin/psi/KtProjectionKind;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: TypeResolver.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[KtProjectionKind.values().length];
                try {
                    iArr[KtProjectionKind.IN.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[KtProjectionKind.OUT.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[KtProjectionKind.NONE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Variance resolveProjectionKind(KtProjectionKind projectionKind) {
            Intrinsics.checkNotNullParameter(projectionKind, "projectionKind");
            int i = WhenMappings.$EnumSwitchMapping$0[projectionKind.ordinal()];
            if (i == 1) {
                return Variance.IN_VARIANCE;
            }
            if (i == 2) {
                return Variance.OUT_VARIANCE;
            }
            if (i == 3) {
                return Variance.INVARIANT;
            }
            throw new IllegalStateException("Illegal projection kind:" + projectionKind);
        }
    }
}
