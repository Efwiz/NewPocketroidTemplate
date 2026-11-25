package org.jetbrains.kotlin.fir.builder;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtPsiSourceFile;
import org.jetbrains.kotlin.KtPsiSourceFileLinesMapping;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.EnumClassUtilsKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirExpressionRef;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;
import org.jetbrains.kotlin.fir.FirFunctionTarget;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.FirGenerationKt;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirLoopTarget;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirTarget;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder.Visitor;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.builder.FirRawContractDescriptionBuilder;
import org.jetbrains.kotlin.fir.declarations.DelegateFieldsMapKt;
import org.jetbrains.kotlin.fir.declarations.DestructuringDeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousInitializerBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousObjectBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirBackingFieldBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirCodeFragmentBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirDanglingModifierListBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirDefaultSetterValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirEnumEntryBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirErrorConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFileBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirImportBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPrimaryConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyAccessorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirScriptBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeAliasBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterRefsOwnerBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParametersOwnerBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.utils.DanglingTypeConstraint;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationBuildingUtilsKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeDanglingModifierOnTopLevel;
import org.jetbrains.kotlin.fir.diagnostics.ConeNotAnnotationContainer;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnderscoreUsageWithoutBackticks;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnsupportedClassLiteralsWithEmptyLhs;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirLazyExpression;
import org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnonymousFunctionExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnonymousObjectExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArrayLiteralBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBlockBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBreakExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCallableReferenceAccessBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCatchBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCheckNotNullCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirContinueExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirDelegatedConstructorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirDoWhileLoopBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirElseIfTrueConditionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirEmptyExpressionBlockBuilderKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirEqualityOperatorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorAnnotationCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirExpressionStubBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirFunctionCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirGetClassCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirImplicitInvokeCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirLambdaArgumentExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirLazyExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirMultiDelegatedConstructorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirNamedArgumentExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedErrorAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirReturnExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirSpreadArgumentExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirThisReceiverExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirThrowExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirTryExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirTypeOperatorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirUnitExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWhenBranchBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWhenExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWhileLoopBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWrappedDelegateExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirContractCallBlock;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitSuperReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitThisReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirPropertyFromParameterResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirSimpleNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCodeFragmentSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirDanglingModifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFileSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirDynamicTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirFunctionTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirIntersectionTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirPlaceholderProjectionBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirStarProjectionBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirUserTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.types.impl.FirQualifierPartImpl;
import org.jetbrains.kotlin.fir.types.impl.FirTypeArgumentListImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBackingField;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockCodeFragment;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBlockStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;
import org.jetbrains.kotlin.psi.KtContainerNodeForControlStructureBody;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtContractEffect;
import org.jetbrains.kotlin.psi.KtContractEffectKt;
import org.jetbrains.kotlin.psi.KtContractEffectList;
import org.jetbrains.kotlin.psi.KtContractEffectListKt;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationModifierList;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDeclarationWithInitializer;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtDynamicType;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtEscapeStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtExpressionCodeFragment;
import org.jetbrains.kotlin.psi.KtExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFileAnnotationList;
import org.jetbrains.kotlin.psi.KtFinallySection;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtFunctionType;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtImportAlias;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtIntersectionType;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtScriptInitializer;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachementBuilderUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: PsiRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001YB\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0018\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0014J\u000e\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;J\u000e\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?J$\u0010@\u001a\u0002HA\"\u0004\b\u0000\u0010A2\u000e\b\u0004\u0010B\u001a\b\u0012\u0004\u0012\u0002HA0CH\u0082\b¢\u0006\u0002\u0010DJ\f\u0010E\u001a\u000203*\u00020FH\u0014J\f\u0010G\u001a\u000203*\u00020HH\u0014J\f\u0010I\u001a\u000203*\u00020JH\u0014J\f\u0010K\u001a\u000203*\u00020LH\u0014J\u000e\u0010M\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0016J\u0016\u0010N\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u0018H\u0016J\u000e\u0010P\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0016J\u000e\u0010Q\u001a\u0004\u0018\u00010\u0014*\u00020\u0002H\u0016J\u000e\u0010R\u001a\u0004\u0018\u00010\u0002*\u00020\u0002H\u0016J\f\u0010S\u001a\u00020T*\u00020\u0002H\u0016J\u0016\u0010U\u001a\u00020V*\u00020\u00022\b\u0010W\u001a\u0004\u0018\u00010XH\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\r\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u0014*\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\u00020\u0018*\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\"\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u001c*\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\u00020 *\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010!R\u001a\u0010\"\u001a\u0004\u0018\u00010#*\u00020$8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u001c\u0010'\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\u0012R\u001c\u0010)\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\u0012R\u0018\u0010+\u001a\u00020\u0014*\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0016R\u0018\u0010-\u001a\u00020.*\u00020/8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b0\u00101¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/PsiRawFirBuilder;", "Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "baseScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "bodyBuildingMode", "Lorg/jetbrains/kotlin/fir/builder/BodyBuildingMode;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;Lorg/jetbrains/kotlin/fir/builder/BodyBuildingMode;)V", "getBaseScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "<set-?>", "mode", "getMode", "()Lorg/jetbrains/kotlin/fir/builder/BodyBuildingMode;", "arrayExpression", "getArrayExpression", "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/PsiElement;", "asText", "", "getAsText", "(Lcom/intellij/psi/PsiElement;)Ljava/lang/String;", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "getElementType", "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/tree/IElementType;", "indexExpressions", "", "getIndexExpressions", "(Lcom/intellij/psi/PsiElement;)Ljava/util/List;", "isVararg", "", "(Lcom/intellij/psi/PsiElement;)Z", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getModality", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;)Lorg/jetbrains/kotlin/descriptors/Modality;", "receiverExpression", "getReceiverExpression", "selectorExpression", "getSelectorExpression", "unescapedValue", "getUnescapedValue", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "getVisibility", "(Lorg/jetbrains/kotlin/psi/KtModifierListOwner;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "bindFunctionTarget", "", "target", "Lorg/jetbrains/kotlin/fir/FirFunctionTarget;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "buildAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "annotation", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "buildFirFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "runOnStubs", ExifInterface.GPS_DIRECTION_TRUE, "body", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "additionalBackingFieldInit", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirBackingFieldBuilder;", "additionalFunctionInit", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFunctionBuilder;", "additionalPropertyAccessorInit", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirPropertyAccessorBuilder;", "additionalPropertyInit", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirPropertyBuilder;", "getAnnotatedExpression", "getChildNodeByType", ModuleXmlParser.TYPE, "getExpressionInParentheses", "getLabelName", "getLabeledExpression", "getReferencedNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "toFirSourceElement", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "Visitor", "psi2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class PsiRawFirBuilder extends AbstractRawFirBuilder<PsiElement> {
    private final FirScopeProvider baseScopeProvider;
    private BodyBuildingMode mode;

    /* JADX INFO: compiled from: PsiRawFirBuilder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BodyBuildingMode.values().length];
            try {
                iArr[BodyBuildingMode.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BodyBuildingMode.LAZY_BODIES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected void additionalBackingFieldInit(FirBackingFieldBuilder firBackingFieldBuilder) {
        Intrinsics.checkNotNullParameter(firBackingFieldBuilder, "<this>");
    }

    protected void additionalFunctionInit(FirFunctionBuilder firFunctionBuilder) {
        Intrinsics.checkNotNullParameter(firFunctionBuilder, "<this>");
    }

    protected void additionalPropertyAccessorInit(FirPropertyAccessorBuilder firPropertyAccessorBuilder) {
        Intrinsics.checkNotNullParameter(firPropertyAccessorBuilder, "<this>");
    }

    protected void additionalPropertyInit(FirPropertyBuilder firPropertyBuilder) {
        Intrinsics.checkNotNullParameter(firPropertyBuilder, "<this>");
    }

    public final FirScopeProvider getBaseScopeProvider() {
        return this.baseScopeProvider;
    }

    public /* synthetic */ PsiRawFirBuilder(FirSession firSession, FirScopeProvider firScopeProvider, BodyBuildingMode bodyBuildingMode, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firScopeProvider, (i & 4) != 0 ? BodyBuildingMode.NORMAL : bodyBuildingMode);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRawFirBuilder(FirSession session, FirScopeProvider baseScopeProvider, BodyBuildingMode bodyBuildingMode) {
        super(session, null, 2, null);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(baseScopeProvider, "baseScopeProvider");
        Intrinsics.checkNotNullParameter(bodyBuildingMode, "bodyBuildingMode");
        this.baseScopeProvider = baseScopeProvider;
        this.mode = bodyBuildingMode;
    }

    protected void bindFunctionTarget(FirFunctionTarget target, FirFunction function) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(function, "function");
        target.bind(function);
    }

    public final BodyBuildingMode getMode() {
        return this.mode;
    }

    public final FirAnnotationCall buildAnnotationCall(KtAnnotationEntry annotation) throws Throwable {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        FirElement firElementVisitAnnotationEntry = new Visitor().visitAnnotationEntry(annotation, (FirElement) null);
        Intrinsics.checkNotNull(firElementVisitAnnotationEntry, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotationCall");
        return (FirAnnotationCall) firElementVisitAnnotationEntry;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public KtPsiSourceElement toFirSourceElement(PsiElement psiElement, KtFakeSourceElementKind ktFakeSourceElementKind) {
        KtSourceElementKind forcedElementSourceKind;
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        KtSourceElementKind ktSourceElementKind = ktFakeSourceElementKind;
        if (ktFakeSourceElementKind == null) {
            forcedElementSourceKind = getContext().getForcedElementSourceKind();
            if (forcedElementSourceKind == null) {
                ktSourceElementKind = KtRealSourceElementKind.INSTANCE;
                forcedElementSourceKind = ktSourceElementKind;
            }
        } else {
            forcedElementSourceKind = ktSourceElementKind;
        }
        if (forcedElementSourceKind instanceof KtRealSourceElementKind) {
            return new KtRealPsiSourceElement(psiElement);
        }
        if (forcedElementSourceKind instanceof KtFakeSourceElementKind) {
            return new KtFakeSourceElement(psiElement, (KtFakeSourceElementKind) forcedElementSourceKind);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public IElementType getElementType(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        IElementType elementType = psiElement.getNode().getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
        return elementType;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public String getAsText(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        String text = psiElement.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return text;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public String getUnescapedValue(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        String unescapedValue = ((KtEscapeStringTemplateEntry) psiElement).getUnescapedValue();
        Intrinsics.checkNotNullExpressionValue(unescapedValue, "getUnescapedValue(...)");
        return unescapedValue;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public PsiElement getChildNodeByType(PsiElement psiElement, IElementType type) {
        PsiElement psiElement2;
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        PsiElement[] children = psiElement.getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        PsiElement[] psiElementArr = children;
        int length = psiElementArr.length;
        for (int i = 0; i < length; i++) {
            psiElement2 = psiElementArr[i];
            if (Intrinsics.areEqual(psiElement2.getNode().getElementType(), type)) {
                return psiElement2;
            }
        }
        psiElement2 = null;
        return psiElement2;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public Name getReferencedNameAsName(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return ((KtSimpleNameExpression) psiElement).getReferencedNameAsName();
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public String getLabelName(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (psiElement instanceof KtExpressionWithLabel) {
            return ((KtExpressionWithLabel) psiElement).getLabelName();
        }
        if (!(psiElement instanceof KtNamedFunction)) {
            return null;
        }
        PsiElement parent = ((KtNamedFunction) psiElement).getLightParent();
        Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
        return getLabelName(parent);
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public PsiElement getExpressionInParentheses(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return ((KtParenthesizedExpression) psiElement).getExpression();
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public PsiElement getAnnotatedExpression(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return ((KtAnnotatedExpression) psiElement).getBaseExpression();
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public PsiElement getLabeledExpression(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return ((KtLabeledExpression) psiElement).getBaseExpression();
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public PsiElement getReceiverExpression(PsiElement psiElement) {
        KtQualifiedExpression ktQualifiedExpression = psiElement instanceof KtQualifiedExpression ? (KtQualifiedExpression) psiElement : null;
        return ktQualifiedExpression != null ? ktQualifiedExpression.getReceiverExpression() : null;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public PsiElement getSelectorExpression(PsiElement psiElement) {
        KtQualifiedExpression ktQualifiedExpression = psiElement instanceof KtQualifiedExpression ? (KtQualifiedExpression) psiElement : null;
        return ktQualifiedExpression != null ? ktQualifiedExpression.getSelectorExpression() : null;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public PsiElement getArrayExpression(PsiElement psiElement) {
        KtArrayAccessExpression ktArrayAccessExpression = psiElement instanceof KtArrayAccessExpression ? (KtArrayAccessExpression) psiElement : null;
        return ktArrayAccessExpression != null ? ktArrayAccessExpression.getArrayExpression() : null;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public List<PsiElement> getIndexExpressions(PsiElement psiElement) {
        KtArrayAccessExpression ktArrayAccessExpression = psiElement instanceof KtArrayAccessExpression ? (KtArrayAccessExpression) psiElement : null;
        if (ktArrayAccessExpression != null) {
            return ktArrayAccessExpression.getIndexExpressions();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder
    public boolean isVararg(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        KtParameter ktParameter = psiElement instanceof KtParameter ? (KtParameter) psiElement : null;
        if (ktParameter != null) {
            return ktParameter.isVarArg();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Visibility getVisibility(KtModifierListOwner ktModifierListOwner) {
        KtModifierList modifierList = ktModifierListOwner.getModifierList();
        if (modifierList == null) {
            return Visibilities.Unknown.INSTANCE;
        }
        if (modifierList.hasModifier(KtTokens.PRIVATE_KEYWORD)) {
            return Visibilities.Private.INSTANCE;
        }
        if (modifierList.hasModifier(KtTokens.PUBLIC_KEYWORD)) {
            return Visibilities.Public.INSTANCE;
        }
        if (modifierList.hasModifier(KtTokens.PROTECTED_KEYWORD)) {
            return Visibilities.Protected.INSTANCE;
        }
        return modifierList.hasModifier(KtTokens.INTERNAL_KEYWORD) ? Visibilities.Internal.INSTANCE : Visibilities.Unknown.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Modality getModality(KtDeclaration ktDeclaration) {
        KtModifierList modifierList = ktDeclaration.getModifierList();
        if (modifierList == null) {
            return null;
        }
        if (modifierList.hasModifier(KtTokens.FINAL_KEYWORD)) {
            return Modality.FINAL;
        }
        if (modifierList.hasModifier(KtTokens.SEALED_KEYWORD)) {
            if (ktDeclaration instanceof KtClassOrObject) {
                return Modality.SEALED;
            }
            return null;
        }
        if (modifierList.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
            return Modality.ABSTRACT;
        }
        if (modifierList.hasModifier(KtTokens.OPEN_KEYWORD)) {
            return Modality.OPEN;
        }
        return null;
    }

    /* JADX INFO: compiled from: PsiRawFirBuilder.kt */
    @Metadata(d1 = {"\u0000Ø\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0094\u0004\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0004J2\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0000\u0010\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u00182\u000e\b\b\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\u0082\b¢\u0006\u0002\u0010\u001aJ\u0017\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018H\u0082\bJ'\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00122\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0018H\u0082\bJ!\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020$0\u0018H\u0082\bJ\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J\u001c\u00100\u001a\b\u0012\u0004\u0012\u000202012\f\u00103\u001a\b\u0012\u0004\u0012\u00020401H\u0002J\u001e\u00105\u001a\u0004\u0018\u00010\u00022\u0006\u00106\u001a\u0002072\n\b\u0002\u00108\u001a\u0004\u0018\u00010\u0002H\u0016J(\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u00052\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0002J-\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\f\u0010F\u001a\b\u0012\u0002\b\u0003\u0018\u00010G2\b\u0010H\u001a\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010IJP\u0010J\u001a\u0004\u0018\u00010K2\b\u0010L\u001a\u0004\u0018\u00010M2\u0006\u0010D\u001a\u00020E2\u0006\u0010N\u001a\u00020\u00122\u0006\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020\u00052\f\u0010R\u001a\b\u0012\u0004\u0012\u00020S012\f\u0010T\u001a\b\u0012\u0004\u0012\u00020S01H\u0016J=\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020Z2\b\u0010[\u001a\u0004\u0018\u00010\\2\u0019\b\u0002\u0010]\u001a\u0013\u0012\u0004\u0012\u00020_\u0012\u0004\u0012\u00020`0^¢\u0006\u0002\baH\u0002J@\u0010b\u001a\u00020c2\u0006\u0010d\u001a\u00020e2\n\u0010f\u001a\u0006\u0012\u0002\b\u00030g2\n\b\u0002\u0010h\u001a\u0004\u0018\u00010\u00122\u0006\u0010i\u001a\u00020j2\u000e\b\u0002\u0010k\u001a\b\u0012\u0004\u0012\u00020S01H\u0016J\u001c\u0010l\u001a\u00020m2\u0006\u0010n\u001a\u00020o2\n\u0010p\u001a\u0006\u0012\u0002\b\u00030qH\u0002J\"\u0010r\u001a\u00020s2\u0006\u0010t\u001a\u00020u2\b\u0010v\u001a\u0004\u0018\u00010w2\u0006\u0010D\u001a\u00020EH\u0002J\u001a\u0010x\u001a\u00020y2\b\u0010z\u001a\u0004\u0018\u00010\u00062\u0006\u0010{\u001a\u00020<H\u0002J\u001a\u0010|\u001a\u00020\u00022\u0006\u0010*\u001a\u00020}2\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u007f\u001a\u00020\u00022\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010\u0082\u0001\u001a\u00020\u00022\b\u0010\u0083\u0001\u001a\u00030\u0084\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u0085\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u0086\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u0087\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u0088\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u0089\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u008a\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010\u008b\u0001\u001a\u00020\u00022\u0006\u0010*\u001a\u00020+2\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u008c\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u008d\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u008e\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u008f\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u0090\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u0091\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u0092\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u0093\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010\u0094\u0001\u001a\u00020\u00022\b\u0010\u0095\u0001\u001a\u00030\u0096\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u0097\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u0098\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u0099\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u009a\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u009b\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030\u009c\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010\u009d\u0001\u001a\u00020\u00022\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010 \u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030¡\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010¢\u0001\u001a\u00020\u00022\u0006\u0010*\u001a\u00020\u00062\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010£\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030¤\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010¥\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030¦\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010§\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030¨\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010©\u0001\u001a\u00020\u00022\u0007\u0010.\u001a\u00030ª\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010«\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030¬\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u00ad\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030®\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010¯\u0001\u001a\u00020\u00022\b\u0010°\u0001\u001a\u00030±\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010²\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030³\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010´\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030µ\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010¶\u0001\u001a\u00020\u00022\u0006\u0010D\u001a\u00020E2\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010·\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030¸\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010¹\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030º\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010»\u0001\u001a\u00020\u00022\u0006\u0010W\u001a\u00020X2\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010¼\u0001\u001a\u00020\u00022\u0006\u0010*\u001a\u00020A2\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010½\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030¾\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010¿\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030À\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010Á\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030Â\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010Ã\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030Ä\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010Å\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030Æ\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010Ç\u0001\u001a\u00020\u00022\b\u0010È\u0001\u001a\u00030É\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010Ê\u0001\u001a\u00020\u00022\u0006\u0010n\u001a\u00020o2\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010Ë\u0001\u001a\u00020\u00022\b\u0010Ì\u0001\u001a\u00030Í\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010Î\u0001\u001a\u00020\u00022\b\u0010Ï\u0001\u001a\u00030Ð\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010Ñ\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030Ò\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010Ó\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030Ô\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010Õ\u0001\u001a\u00020\u00022\u0007\u0010*\u001a\u00030Ö\u00012\b\u0010~\u001a\u0004\u0018\u00010\u0002H\u0016J \u0010×\u0001\u001a\u00020`*\u00030Ø\u00012\u0007\u0010Ù\u0001\u001a\u00020e2\u0007\u0010Ú\u0001\u001a\u00020\u0005H\u0002J%\u0010Û\u0001\u001a\u00020`*\n\u0012\u0005\u0012\u00030Ý\u00010Ü\u00012\u000e\u0010Þ\u0001\u001a\t\u0012\u0005\u0012\u00030Í\u000101H\u0002J \u0010ß\u0001\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0012\u0007\u0012\u0005\u0018\u00010á\u00010à\u0001*\u00030â\u0001H\u0002J\u0017\u0010ã\u0001\u001a\u00020\u001f*\u00030ä\u00012\u0007\u0010å\u0001\u001a\u00020\u0012H\u0002J\"\u0010ã\u0001\u001a\u0003Hæ\u0001\"\u000b\b\u0000\u0010æ\u0001\u0018\u0001*\u00020\u0002*\u000207H\u0082\b¢\u0006\u0003\u0010ç\u0001J&\u0010è\u0001\u001a\u0005\u0018\u0001Hæ\u0001\"\u000b\b\u0000\u0010æ\u0001\u0018\u0001*\u00020\u0002*\u0004\u0018\u000107H\u0082\b¢\u0006\u0003\u0010ç\u0001J\u0018\u0010é\u0001\u001a\u00020`*\u00030ê\u00012\b\u0010ë\u0001\u001a\u00030ì\u0001H\u0002J\u0018\u0010í\u0001\u001a\u00020`*\u00030ì\u00012\b\u0010î\u0001\u001a\u00030ï\u0001H\u0002J\u0018\u0010í\u0001\u001a\u00020`*\u00030ì\u00012\b\u0010î\u0001\u001a\u00030ð\u0001H\u0002J\u0018\u0010ñ\u0001\u001a\u00020`*\u00030ò\u00012\b\u0010î\u0001\u001a\u00030ó\u0001H\u0002J\u001e\u0010ô\u0001\u001a\u00020`*\u00030õ\u00012\u000e\u0010ö\u0001\u001a\t\u0012\u0004\u0012\u00020$0Ü\u0001H\u0002Jn\u0010÷\u0001\u001a\u001f\u0012\u0004\u0012\u00020\u0012\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0014\u0012\u0005\u0012\u00030ù\u0001\u0018\u00010ø\u00010à\u0001*\u00030\u0096\u00012\b\u0010î\u0001\u001a\u00030ú\u00012\t\u0010û\u0001\u001a\u0004\u0018\u00010\u00122\t\u0010ü\u0001\u001a\u0004\u0018\u00010\u00122\b\u0010ý\u0001\u001a\u00030þ\u00012\u000e\u0010ÿ\u0001\u001a\t\u0012\u0005\u0012\u00030\u0080\u0002012\u0007\u0010\u0081\u0002\u001a\u00020\u0005H\u0002J$\u0010\u0082\u0002\u001a\u00020`*\u00030\u0083\u00022\b\u0010î\u0001\u001a\u00030\u0084\u00022\n\u0010p\u001a\u0006\u0012\u0002\b\u00030qH\u0002J$\u0010\u0082\u0002\u001a\u00020`*\u00030\u0083\u00022\b\u0010î\u0001\u001a\u00030\u0085\u00022\n\u0010p\u001a\u0006\u0012\u0002\b\u00030qH\u0002JH\u0010\u0086\u0002\u001a\u00020`*\u00030â\u00012\b\u0010î\u0001\u001a\u00030\u0087\u00022\n\u0010f\u001a\u0006\u0012\u0002\b\u00030g2\u0006\u0010i\u001a\u00020j2\n\b\u0002\u0010h\u001a\u0004\u0018\u00010\u00122\u000e\b\u0002\u0010k\u001a\b\u0012\u0004\u0012\u00020S01H\u0002J-\u0010\u0088\u0002\u001a\u00020`\"\u000e\b\u0000\u0010\u0016*\u00030\u0089\u0002*\u00030\u008a\u0002*\u00030\u0083\u00022\u0007\u0010\u008b\u0002\u001a\u0002H\u0016H\u0002¢\u0006\u0003\u0010\u008c\u0002J\u0011\u0010\u008d\u0002\u001a\u0005\u0018\u00010á\u0001*\u00030â\u0001H\u0002J\u0011\u0010\u008e\u0002\u001a\u0005\u0018\u00010\u008f\u0002*\u00030\u0096\u0001H\u0002J:\u0010\u0090\u0002\u001a\u00030\u0091\u0002*\u0005\u0018\u00010\u0092\u00022\u0006\u0010D\u001a\u00020E2\u0006\u0010O\u001a\u00020P2\u0007\u0010\u0093\u0002\u001a\u00020\u00122\u000e\u0010\u0094\u0002\u001a\t\u0012\u0005\u0012\u00030\u0095\u000201H\u0002J\u000f\u0010\u0096\u0002\u001a\u00020\u001c*\u0004\u0018\u00010\u0006H\u0002J\u0090\u0001\u0010\u0097\u0002\u001a\u00030\u0098\u0002*\u0005\u0018\u00010\u0099\u00022\n\u0010\u009a\u0002\u001a\u0005\u0018\u00010\u009b\u00022\t\u0010\u009c\u0002\u001a\u0004\u0018\u00010\u00122\u0007\u0010û\u0001\u001a\u00020\u00122\b\u0010\u009d\u0002\u001a\u00030\u0096\u00012\u000e\u0010\u009e\u0002\u001a\t\u0012\u0005\u0012\u00030\u0080\u0002012\u001b\u0010\u009f\u0002\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0005\u0012\u00030\u009b\u0002\u0012\u0004\u0012\u00020\u00120à\u0001012\u0007\u0010\u0081\u0002\u001a\u00020\u00052\u0007\u0010 \u0002\u001a\u00020\u00052\t\b\u0002\u0010¡\u0002\u001a\u00020\u00052\t\b\u0002\u0010¢\u0002\u001a\u00020\u0005H\u0004J;\u0010\u0097\u0002\u001a\u00030\u0098\u0002*\u00030£\u00022\u0007\u0010¤\u0002\u001a\u00020\u00122\u0007\u0010¥\u0002\u001a\u00020\u00122\b\u0010\u009d\u0002\u001a\u00030\u0096\u00012\u000e\u0010\u009e\u0002\u001a\t\u0012\u0005\u0012\u00030\u0080\u000201H\u0004JE\u0010¦\u0002\u001a\u00030\u0089\u0002*\u00020w2\u0007\u0010§\u0002\u001a\u00020\u00122\b\u0010¨\u0002\u001a\u00030©\u00022\b\u0010\u009d\u0002\u001a\u00030\u0096\u00012\b\u0010ª\u0002\u001a\u00030ú\u00012\u000e\u0010\u009e\u0002\u001a\t\u0012\u0005\u0012\u00030\u0080\u000201H\u0002J\"\u0010«\u0002\u001a\u00030\u0089\u0002*\u00030¬\u00022\b\u0010\u00ad\u0002\u001a\u00030©\u00022\u0007\u0010®\u0002\u001a\u00020\u0005H\u0004J\u001e\u0010¯\u0002\u001a\u00020$*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00182\u0007\u0010°\u0002\u001a\u00020ZH\u0002J\u0016\u0010¯\u0002\u001a\u00020$*\u0002072\u0007\u0010±\u0002\u001a\u00020$H\u0002J.\u0010¯\u0002\u001a\u00020$*\u0004\u0018\u0001072\f\b\u0002\u0010²\u0002\u001a\u0005\u0018\u00010³\u00022\u000e\u0010´\u0002\u001a\t\u0012\u0005\u0012\u00030µ\u00020\u0018H\u0082\bJ$\u0010¯\u0002\u001a\u00020$*\u0004\u0018\u0001072\u0007\u0010°\u0002\u001a\u00020Z2\n\b\u0002\u0010¶\u0002\u001a\u00030·\u0002H\u0002J\u0010\u0010¯\u0002\u001a\u00020$*\u0005\u0018\u00010¸\u0002H\u0002J\u0010\u0010¹\u0002\u001a\u00020\u0012*\u0005\u0018\u00010Ð\u0001H\u0004J\u0010\u0010º\u0002\u001a\u00020\u0012*\u0005\u0018\u00010Ð\u0001H\u0002J\u0010\u0010»\u0002\u001a\u00020\u0012*\u0005\u0018\u00010Ð\u0001H\u0002J\u0016\u0010¼\u0002\u001a\u00020C*\u00020e2\u0007\u0010½\u0002\u001a\u00020cH\u0002J1\u0010¼\u0002\u001a\u00020C\"\u0004\b\u0000\u0010\u0016*\u00020E2\f\u0010F\u001a\b\u0012\u0002\b\u0003\u0018\u00010G2\u000e\u0010¾\u0002\u001a\t\u0012\u0004\u0012\u0002H\u00160¿\u0002H\u0002JM\u0010À\u0002\u001a\u0004\u0018\u00010K*\u0004\u0018\u00010M2\u0006\u0010D\u001a\u00020E2\u0006\u0010N\u001a\u00020\u00122\u0006\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020\u00052\f\u0010R\u001a\b\u0012\u0004\u0012\u00020S012\f\u0010T\u001a\b\u0012\u0004\u0012\u00020S01H\u0002J\u000e\u0010Á\u0002\u001a\u00030Â\u0002*\u00020\u0006H\u0002J\u001e\u0010Á\u0002\u001a\u00030Â\u0002*\u00020\u00062\r\u0010Ã\u0002\u001a\b\u0012\u0004\u0012\u00020Z0\u0018H\u0082\bJ9\u0010Ä\u0002\u001a\u00020c*\u00020e2\b\u0010h\u001a\u0004\u0018\u00010\u00122\n\u0010f\u001a\u0006\u0012\u0002\b\u00030g2\u0006\u0010i\u001a\u00020j2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020S01H\u0002J\u0010\u0010Å\u0002\u001a\u0004\u0018\u00010$*\u00030Æ\u0002H\u0002R\u0018\u0010\u0004\u001a\u00020\u0005*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006Ç\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/PsiRawFirBuilder$Visitor;", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/builder/PsiRawFirBuilder;)V", "usedAsExpression", "", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getUsedAsExpression", "(Lorg/jetbrains/kotlin/psi/KtExpression;)Z", "buildErrorTopLevelDeclarationForDanglingModifierList", "Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", "modifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "buildFieldForSupertypeDelegate", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "entry", "Lorg/jetbrains/kotlin/psi/KtDelegatedSuperTypeEntry;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "fieldOrd", "", "buildOrLazy", ExifInterface.GPS_DIRECTION_TRUE, JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lkotlin/Function0;", "lazy", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "buildOrLazyBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "buildBlock", "buildOrLazyDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "isThis", "constructedTypeRef", "buildCall", "buildOrLazyExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "sourceElement", "Lorg/jetbrains/kotlin/KtSourceElement;", "buildExpression", "configureBlockWithoutBuilding", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirBlockBuilder;", "expression", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "convertCodeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "file", "Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "convertContextReceivers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "receivers", "Lorg/jetbrains/kotlin/psi/KtContextReceiver;", "convertElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "original", "convertKtTypeElement", "Lorg/jetbrains/kotlin/fir/types/builder/FirUserTypeRefBuilder;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "isNullable", "ktUserType", "Lorg/jetbrains/kotlin/psi/KtUserType;", "reference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "convertProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "ownerRegularOrAnonymousObjectSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "ownerRegularClassTypeParametersCount", "(Lorg/jetbrains/kotlin/psi/KtProperty;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Ljava/lang/Integer;)Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "convertPropertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "propertyTypeRef", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "isGetter", "accessorAnnotationsFromProperty", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "parameterAnnotationsFromProperty", "convertScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "sourceFile", "Lorg/jetbrains/kotlin/KtSourceFile;", "setup", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirScriptBuilder;", "", "Lkotlin/ExtensionFunctionType;", "convertValueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "valueParameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "defaultTypeRef", "valueParameterDeclaration", "Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder$ValueParameterDeclaration;", "additionalAnnotations", "extractTypeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "parameter", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "declarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "obtainPropertyComponentStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "componentVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "splitToCalleeAndReceiver", "Lorg/jetbrains/kotlin/fir/builder/CalleeAndReceiver;", "calleeExpression", "defaultSource", "visitAnnotatedExpression", "Lorg/jetbrains/kotlin/psi/KtAnnotatedExpression;", "data", "visitAnnotationEntry", "annotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "visitAnonymousInitializer", "initializer", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "visitArrayAccessExpression", "Lorg/jetbrains/kotlin/psi/KtArrayAccessExpression;", "visitBinaryExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "visitBinaryWithTypeRHSExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "visitBlockExpression", "visitBreakExpression", "Lorg/jetbrains/kotlin/psi/KtBreakExpression;", "visitCallExpression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "visitCallableReferenceExpression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "visitClassLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", "visitClassOrObject", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "visitCollectionLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;", "visitConstantExpression", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "visitContinueExpression", "Lorg/jetbrains/kotlin/psi/KtContinueExpression;", "visitDestructuringDeclaration", "multiDeclaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "visitDoWhileExpression", "Lorg/jetbrains/kotlin/psi/KtDoWhileExpression;", "visitExpression", "visitForExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "visitIfExpression", "Lorg/jetbrains/kotlin/psi/KtIfExpression;", "visitIsExpression", "Lorg/jetbrains/kotlin/psi/KtIsExpression;", "visitKtFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "visitLabeledExpression", "Lorg/jetbrains/kotlin/psi/KtLabeledExpression;", "visitLambdaExpression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "visitNamedFunction", "function", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "visitObjectLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtObjectLiteralExpression;", "visitParenthesizedExpression", "Lorg/jetbrains/kotlin/psi/KtParenthesizedExpression;", "visitProperty", "visitQualifiedExpression", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "visitReturnExpression", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "visitScript", "visitSimpleNameExpression", "visitStringTemplateExpression", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "visitSuperExpression", "Lorg/jetbrains/kotlin/psi/KtSuperExpression;", "visitThisExpression", "Lorg/jetbrains/kotlin/psi/KtThisExpression;", "visitThrowExpression", "Lorg/jetbrains/kotlin/psi/KtThrowExpression;", "visitTryExpression", "Lorg/jetbrains/kotlin/psi/KtTryExpression;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "visitTypeParameter", "visitTypeProjection", "typeProjection", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "visitTypeReference", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "visitUnaryExpression", "Lorg/jetbrains/kotlin/psi/KtUnaryExpression;", "visitWhenExpression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "visitWhileExpression", "Lorg/jetbrains/kotlin/psi/KtWhileExpression;", "addAnnotationsFrom", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirValueParameterBuilder;", "ktParameter", "isFromPrimaryConstructor", "appendTypeArguments", "", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "args", "buildFirBody", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "convert", "Lorg/jetbrains/kotlin/psi/KtConstructorDelegationCall;", "delegatedType", "R", "(Lorg/jetbrains/kotlin/psi/KtElement;)Lorg/jetbrains/kotlin/fir/FirElement;", "convertSafe", "extractAnnotationsFrom", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirDefaultPropertyAccessor;", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "extractAnnotationsTo", "container", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "extractArgumentsTo", "Lorg/jetbrains/kotlin/psi/KtCallElement;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirCallBuilder;", "extractRawEffects", "Lorg/jetbrains/kotlin/psi/KtContractEffectList;", FirebaseAnalytics.Param.DESTINATION, "extractSuperTypeListEntriesTo", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;", "delegatedSelfTypeRef", "delegatedEnumSuperTypeRef", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "containerTypeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "containingClassIsExpectClass", "extractTypeParametersTo", "Lorg/jetbrains/kotlin/psi/KtTypeParameterListOwner;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirTypeParameterRefsOwnerBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirTypeParametersOwnerBuilder;", "extractValueParametersTo", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFunctionBuilder;", "fillDanglingConstraintsTo", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "to", "(Lorg/jetbrains/kotlin/psi/KtTypeParameterListOwner;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)V", "obtainContractDescription", "obtainDispatchReceiverForConstructor", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "toFirBackingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "Lorg/jetbrains/kotlin/psi/KtBackingField;", "propertyReturnType", "annotationsFromProperty", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "toFirBlock", "toFirConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "superTypeCallEntry", "Lorg/jetbrains/kotlin/psi/KtSuperTypeCallEntry;", "delegatedSuperTypeRef", "owner", "ownerTypeParameters", "allSuperTypeCallEntries", "copyConstructedTypeRefWithImplicitSource", "isErrorConstructor", "isImplicitlyActual", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "delegatedTypeRef", "selfTypeRef", "toFirDeclaration", "delegatedSuperType", "delegatedSelfType", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "ownerClassBuilder", "toFirEnumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "delegatedEnumSelfTypeRef", "ownerClassHasDefaultConstructor", "toFirExpression", HiAnalyticsConstant.HaKey.BI_KEY_ERRORREASON, "result", "sourceWhenThisIsNull", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "diagnosticFn", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "toFirOrErrorType", "toFirOrImplicitType", "toFirOrUnitType", "toFirProperty", "firParameter", "context", "Lorg/jetbrains/kotlin/fir/builder/Context;", "toFirPropertyAccessor", "toFirStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "errorReasonLazy", "toFirValueParameter", "toInitializerExpression", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithInitializer;", "psi2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected class Visitor extends KtVisitor<FirElement, FirElement> {

        /* JADX INFO: compiled from: PsiRawFirBuilder.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;
            public static final /* synthetic */ int[] $EnumSwitchMapping$1;

            static {
                int[] iArr = new int[BodyBuildingMode.values().length];
                try {
                    iArr[BodyBuildingMode.NORMAL.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[BodyBuildingMode.LAZY_BODIES.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
                int[] iArr2 = new int[KtProjectionKind.values().length];
                try {
                    iArr2[KtProjectionKind.IN.ordinal()] = 1;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr2[KtProjectionKind.OUT.ordinal()] = 2;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr2[KtProjectionKind.NONE.ordinal()] = 3;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr2[KtProjectionKind.STAR.ordinal()] = 4;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$1 = iArr2;
            }
        }

        public Visitor() {
        }

        public static /* synthetic */ FirElement convertElement$default(Visitor visitor, KtElement ktElement, FirElement firElement, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: convertElement");
            }
            if ((i & 2) != 0) {
                firElement = null;
            }
            return visitor.convertElement(ktElement, firElement);
        }

        public FirElement convertElement(KtElement element, FirElement original) {
            Intrinsics.checkNotNullParameter(element, "element");
            return (FirElement) element.accept(this, original);
        }

        public FirProperty convertProperty(KtProperty property, FirClassSymbol<?> ownerRegularOrAnonymousObjectSymbol, Integer ownerRegularClassTypeParametersCount) {
            Intrinsics.checkNotNullParameter(property, "property");
            return toFirProperty(property, ownerRegularOrAnonymousObjectSymbol, PsiRawFirBuilder.this.getContext());
        }

        public FirPropertyAccessor convertPropertyAccessor(KtPropertyAccessor accessor, KtProperty property, FirTypeRef propertyTypeRef, FirPropertySymbol propertySymbol, boolean isGetter, List<? extends FirAnnotation> accessorAnnotationsFromProperty, List<? extends FirAnnotation> parameterAnnotationsFromProperty) {
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(propertyTypeRef, "propertyTypeRef");
            Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
            Intrinsics.checkNotNullParameter(accessorAnnotationsFromProperty, "accessorAnnotationsFromProperty");
            Intrinsics.checkNotNullParameter(parameterAnnotationsFromProperty, "parameterAnnotationsFromProperty");
            return toFirPropertyAccessor(accessor, property, propertyTypeRef, propertySymbol, isGetter, accessorAnnotationsFromProperty, parameterAnnotationsFromProperty);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ FirValueParameter convertValueParameter$default(Visitor visitor, KtParameter ktParameter, FirFunctionSymbol firFunctionSymbol, FirTypeRef firTypeRef, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, List list, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: convertValueParameter");
            }
            if ((i & 4) != 0) {
                firTypeRef = null;
            }
            FirTypeRef firTypeRef2 = firTypeRef;
            if ((i & 16) != 0) {
                list = CollectionsKt.emptyList();
            }
            return visitor.convertValueParameter(ktParameter, firFunctionSymbol, firTypeRef2, valueParameterDeclaration, list);
        }

        public FirValueParameter convertValueParameter(KtParameter valueParameter, FirFunctionSymbol<?> functionSymbol, FirTypeRef defaultTypeRef, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, List<? extends FirAnnotation> additionalAnnotations) {
            Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
            Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
            Intrinsics.checkNotNullParameter(valueParameterDeclaration, "valueParameterDeclaration");
            Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
            return toFirValueParameter(valueParameter, defaultTypeRef, functionSymbol, valueParameterDeclaration, additionalAnnotations);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FirTypeRef toFirOrImplicitType(KtTypeReference ktTypeReference) {
            FirTypeRef firOrErrorType;
            return (ktTypeReference == null || (firOrErrorType = toFirOrErrorType(ktTypeReference)) == null) ? FirImplicitTypeRefImplWithoutSource.INSTANCE : firOrErrorType;
        }

        private final FirTypeRef toFirOrUnitType(KtTypeReference ktTypeReference) {
            KtTypeReference ktTypeReference2 = ktTypeReference;
            FirElement firElementConvertElement = ktTypeReference2 != null ? convertElement(ktTypeReference2, null) : null;
            FirTypeRef firTypeRef = (FirTypeRef) (firElementConvertElement instanceof FirTypeRef ? firElementConvertElement : null);
            return firTypeRef == null ? PsiRawFirBuilder.this.getImplicitUnitType() : firTypeRef;
        }

        protected final FirTypeRef toFirOrErrorType(KtTypeReference ktTypeReference) {
            KtTypeReference ktTypeReference2 = ktTypeReference;
            FirElement firElementConvertElement = ktTypeReference2 != null ? convertElement(ktTypeReference2, null) : null;
            if (!(firElementConvertElement instanceof FirTypeRef)) {
                firElementConvertElement = null;
            }
            FirTypeRef firTypeRef = (FirTypeRef) firElementConvertElement;
            if (firTypeRef != null) {
                return firTypeRef;
            }
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setSource(ktTypeReference != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktTypeReference, null, 1, null) : null);
            firErrorTypeRefBuilder.setDiagnostic(new ConeSyntaxDiagnostic(ktTypeReference == null ? "Incomplete code" : "Conversion failed"));
            return firErrorTypeRefBuilder.mo6809build();
        }

        private final FirExpression toFirExpression(Function0<? extends KtExpression> function0, String str) {
            return toFirExpression$default(this, function0.invoke(), str, null, 2, null);
        }

        static /* synthetic */ FirExpression toFirExpression$default(Visitor visitor, KtElement ktElement, String str, DiagnosticKind diagnosticKind, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toFirExpression");
            }
            if ((i & 2) != 0) {
                diagnosticKind = DiagnosticKind.ExpressionExpected;
            }
            return visitor.toFirExpression(ktElement, str, diagnosticKind);
        }

        private final FirExpression toFirExpression(KtElement ktElement, String str, DiagnosticKind diagnosticKind) {
            if (ktElement == null) {
                return FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSimpleDiagnostic(str, diagnosticKind), null, 4, null);
            }
            FirElement firElementConvertElement = convertElement(ktElement, null);
            if (firElementConvertElement instanceof FirExpression) {
                return toFirExpression(ktElement, (FirExpression) firElementConvertElement);
            }
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
            firErrorExpressionBuilder.setNonExpressionElement(firElementConvertElement);
            firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic(str, diagnosticKind));
            firErrorExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktElement, null, 1, null));
            return firErrorExpressionBuilder.mo6809build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FirExpression toFirExpression(KtElement ktElement, FirExpression firExpression) {
            KtPsiSourceElement ktPsiSourceElement;
            KtExpression calleeExpression;
            boolean z = ktElement instanceof KtCallExpression;
            KtCallExpression ktCallExpression = z ? (KtCallExpression) ktElement : null;
            PsiElement psiElementUnwrapParenthesesLabelsAndAnnotations = (ktCallExpression == null || (calleeExpression = ktCallExpression.getCalleeExpression()) == null) ? null : PsiUtilsKt.unwrapParenthesesLabelsAndAnnotations(calleeExpression);
            if ((ktElement instanceof KtNameReferenceExpression) || ((z && !(psiElementUnwrapParenthesesLabelsAndAnnotations instanceof KtLambdaExpression)) || KtPsiUtilKt.getQualifiedExpressionForSelector(ktElement) == null)) {
                return firExpression;
            }
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
            firErrorExpressionBuilder.setSource((psiElementUnwrapParenthesesLabelsAndAnnotations == null || (ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, psiElementUnwrapParenthesesLabelsAndAnnotations, null, 1, null)) == null) ? AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktElement, null, 1, null) : ktPsiSourceElement);
            firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("The expression cannot be a selector (occur after a dot)", psiElementUnwrapParenthesesLabelsAndAnnotations == null ? DiagnosticKind.IllegalSelector : DiagnosticKind.NoReceiverAllowed));
            firErrorExpressionBuilder.setExpression(firExpression);
            return firErrorExpressionBuilder.mo6809build();
        }

        private final FirStatement toFirStatement(KtExpression ktExpression) {
            FirElement firElementConvertElement = convertElement(ktExpression, null);
            if (firElementConvertElement != null) {
                return (FirStatement) firElementConvertElement;
            }
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
        }

        private final FirDeclaration toFirDeclaration(KtDeclaration ktDeclaration, FirTypeRef firTypeRef, FirResolvedTypeRef firResolvedTypeRef, KtClassOrObject ktClassOrObject, FirClassBuilder firClassBuilder, List<? extends FirTypeParameterRef> list) {
            boolean zIsEmpty;
            List<KtParameter> valueParameters;
            if (ktDeclaration instanceof KtSecondaryConstructor) {
                KtSecondaryConstructor ktSecondaryConstructor = (KtSecondaryConstructor) ktDeclaration;
                if (ktSecondaryConstructor.isDelegatedCallToThis()) {
                    firTypeRef = firResolvedTypeRef;
                }
                return toFirConstructor(ktSecondaryConstructor, firTypeRef, firResolvedTypeRef, ktClassOrObject, list);
            }
            boolean z = true;
            if (ktDeclaration instanceof KtEnumEntry) {
                KtPrimaryConstructor primaryConstructor = ktClassOrObject.getPrimaryConstructor();
                if (primaryConstructor == null || (valueParameters = primaryConstructor.getValueParameters()) == null) {
                    List<KtSecondaryConstructor> secondaryConstructors = ktClassOrObject.getSecondaryConstructors();
                    if (!secondaryConstructors.isEmpty()) {
                        List<KtSecondaryConstructor> list2 = secondaryConstructors;
                        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                            Iterator<T> it = list2.iterator();
                            do {
                                if (!it.hasNext()) {
                                    z = false;
                                    break;
                                }
                            } while (!((KtSecondaryConstructor) it.next()).getValueParameters().isEmpty());
                        } else {
                            z = false;
                            break;
                        }
                    }
                    zIsEmpty = z;
                } else {
                    zIsEmpty = valueParameters.isEmpty();
                }
                return toFirEnumEntry((KtEnumEntry) ktDeclaration, firResolvedTypeRef, zIsEmpty);
            }
            if (ktDeclaration instanceof KtProperty) {
                return convertProperty((KtProperty) ktDeclaration, ConversionUtilsKt.getOwnerRegularOrAnonymousObjectSymbol(firClassBuilder), ConversionUtilsKt.getOwnerRegularClassTypeParametersCount(firClassBuilder));
            }
            if (ktDeclaration instanceof KtDestructuringDeclaration) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                return psiRawFirBuilder.buildErrorTopLevelDestructuringDeclaration(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktDeclaration, null, 1, null));
            }
            FirElement firElementConvertElement = convertElement(ktDeclaration, null);
            if (firElementConvertElement != null) {
                return (FirDeclaration) firElementConvertElement;
            }
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclaration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code duplicated, block: B:12:0x0047  */
        public final FirBlock toFirBlock(KtExpression ktExpression) {
            FirBlock firBlock;
            if (ktExpression instanceof KtBlockExpression) {
                Object objAccept = ktExpression.accept(this, null);
                Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirBlock");
                return (FirBlock) objAccept;
            }
            if (ktExpression == null) {
                return FirEmptyExpressionBlockBuilderKt.buildEmptyExpressionBlock();
            }
            if (ktExpression instanceof KtAnnotatedExpression) {
                PsiElement[] children = ((KtAnnotatedExpression) ktExpression).getChildren();
                Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                PsiElement psiElement = (PsiElement) ArraysKt.lastOrNull(children);
                if (psiElement instanceof KtBlockExpression) {
                    firBlock = toFirBlock((KtExpression) psiElement);
                    extractAnnotationsTo((KtAnnotated) ktExpression, firBlock);
                } else {
                    firBlock = null;
                }
            } else {
                firBlock = null;
            }
            if (firBlock != null) {
                return firBlock;
            }
            FirElement firElementConvertElement = convertElement(ktExpression, null);
            if (firElementConvertElement != null) {
                return new FirSingleExpressionBlock((FirStatement) firElementConvertElement);
            }
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
        }

        private final Pair<FirBlock, FirContractDescription> buildFirBody(final KtDeclarationWithBody ktDeclarationWithBody) throws Throwable {
            Object objInvoke;
            FirContractDescription firContractDescriptionProcessLegacyContractDescription = null;
            if (ktDeclarationWithBody.hasBody()) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                PsiRawFirBuilder$Visitor$buildOrLazyBlock$1 psiRawFirBuilder$Visitor$buildOrLazyBlock$1 = PsiRawFirBuilder$Visitor$buildOrLazyBlock$1.INSTANCE;
                int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                    if (i2 == 1) {
                        objInvoke = psiRawFirBuilder$Visitor$buildOrLazyBlock$1.invoke();
                    } else {
                        if (i2 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        objInvoke = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyBlock$1));
                    }
                } else {
                    if (ktDeclarationWithBody.hasBlockBody()) {
                        KtBlockExpression bodyBlockExpression = ktDeclarationWithBody.getBodyBlockExpression();
                        FirElement firElement = bodyBlockExpression != null ? (FirElement) bodyBlockExpression.accept(this, null) : null;
                        FirBlock firBlock = firElement instanceof FirBlock ? (FirBlock) firElement : null;
                        if (!ktDeclarationWithBody.hasContractEffectList() && firBlock != null) {
                            firContractDescriptionProcessLegacyContractDescription = ConversionUtilsKt.processLegacyContractDescription(firBlock);
                        }
                        return TuplesKt.to(firBlock, firContractDescriptionProcessLegacyContractDescription);
                    }
                    FirExpression firExpression = toFirExpression(new Function0<KtExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$buildFirBody$1$result$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final KtExpression invoke() {
                            return ktDeclarationWithBody.getBodyExpression();
                        }
                    }, "Function has no body (but should)");
                    objInvoke = (FirBlock) new FirSingleExpressionBlock(AbstractRawFirBuilder.toReturn$default(psiRawFirBuilder, firExpression, firExpression.getSource(), null, false, 6, null));
                }
                return TuplesKt.to((FirBlock) objInvoke, null);
            }
            return TuplesKt.to(null, null);
        }

        private final FirExpression toFirExpression(ValueArgument valueArgument) {
            FirExpression firExpression;
            if (valueArgument == null) {
                return FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No argument given"), null, 4, null);
            }
            ValueArgumentName argumentName = valueArgument.getArgumentName();
            Name asName = argumentName != null ? argumentName.getAsName() : null;
            final KtExpression argumentExpression = valueArgument.getArgumentExpression();
            if (argumentExpression instanceof KtConstantExpression ? true : argumentExpression instanceof KtStringTemplateExpression) {
                Object objAccept = argumentExpression.accept(this, null);
                Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                firExpression = (FirExpression) objAccept;
            } else {
                firExpression = toFirExpression(new Function0<KtExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$toFirExpression$firExpression$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final KtExpression invoke() {
                        return argumentExpression;
                    }
                }, "Argument is absent");
            }
            boolean z = valueArgument.getSpreadElement() != null;
            if (asName != null) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                FirNamedArgumentExpressionBuilder firNamedArgumentExpressionBuilder = new FirNamedArgumentExpressionBuilder();
                PsiElement psiElement = valueArgument instanceof PsiElement ? (PsiElement) valueArgument : null;
                firNamedArgumentExpressionBuilder.setSource(psiElement != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, psiElement, null, 1, null) : null);
                firNamedArgumentExpressionBuilder.setExpression(firExpression);
                firNamedArgumentExpressionBuilder.setSpread(z);
                firNamedArgumentExpressionBuilder.setName(asName);
                return firNamedArgumentExpressionBuilder.mo6809build();
            }
            if (!z) {
                return firExpression;
            }
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            FirSpreadArgumentExpressionBuilder firSpreadArgumentExpressionBuilder = new FirSpreadArgumentExpressionBuilder();
            PsiElement psiElement2 = valueArgument instanceof PsiElement ? (PsiElement) valueArgument : null;
            firSpreadArgumentExpressionBuilder.setSource(psiElement2 != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, psiElement2, null, 1, null) : null);
            firSpreadArgumentExpressionBuilder.setExpression(firExpression);
            return firSpreadArgumentExpressionBuilder.mo6809build();
        }

        /* JADX WARN: Code duplicated, block: B:10:0x002e  */
        /* JADX WARN: Code duplicated, block: B:46:0x00d7  */
        private final FirPropertyAccessor toFirPropertyAccessor(KtPropertyAccessor ktPropertyAccessor, KtProperty ktProperty, FirTypeRef firTypeRef, FirPropertySymbol firPropertySymbol, boolean z, List<? extends FirAnnotation> list, List<? extends FirAnnotation> list2) throws Throwable {
            PsiRawFirBuilder psiRawFirBuilder;
            KtProperty ktProperty2;
            KtRealPsiSourceElement ktFakeSourceElement;
            FirTypeRef firOrUnitType;
            if ((ktPropertyAccessor != null ? PsiRawFirBuilder.this.getVisibility(ktPropertyAccessor) : null) != null) {
                ktProperty2 = ktPropertyAccessor;
                if (Intrinsics.areEqual(PsiRawFirBuilder.this.getVisibility(ktProperty2), Visibilities.Unknown.INSTANCE)) {
                    psiRawFirBuilder = PsiRawFirBuilder.this;
                    ktProperty2 = ktProperty;
                } else {
                    psiRawFirBuilder = PsiRawFirBuilder.this;
                }
            } else {
                psiRawFirBuilder = PsiRawFirBuilder.this;
                ktProperty2 = ktProperty;
            }
            Visibility visibility = psiRawFirBuilder.getVisibility(ktProperty2);
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, ktPropertyAccessor != null ? PsiRawFirBuilder.this.getModality(ktPropertyAccessor) : null);
            firDeclarationStatusImpl.setInline(ktProperty.hasModifier(KtTokens.INLINE_KEYWORD) || (ktPropertyAccessor != null && ktPropertyAccessor.hasModifier(KtTokens.INLINE_KEYWORD)));
            firDeclarationStatusImpl.setExternal(ktProperty.hasModifier(KtTokens.EXTERNAL_KEYWORD) || (ktPropertyAccessor != null && ktPropertyAccessor.hasModifier(KtTokens.EXTERNAL_KEYWORD)));
            FirTypeRef firTypeRefCopyWithNewSourceKind = UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE);
            if (ktPropertyAccessor != null && ktPropertyAccessor.hasBody()) {
                KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, ktPropertyAccessor, null, 1, null);
                FirFunctionTarget firFunctionTarget = new FirFunctionTarget(null, false);
                PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                FirPropertyAccessorBuilder firPropertyAccessorBuilder = new FirPropertyAccessorBuilder();
                KtPsiSourceElement ktPsiSourceElement2 = ktPsiSourceElement;
                firPropertyAccessorBuilder.setSource(ktPsiSourceElement2);
                firPropertyAccessorBuilder.setModuleData(psiRawFirBuilder2.getBaseModuleData());
                firPropertyAccessorBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                if (z) {
                    KtTypeReference returnTypeReference = ktPropertyAccessor.getReturnTypeReference();
                    if (returnTypeReference != null) {
                        FirElement firElementConvertElement = convertElement(returnTypeReference, null);
                        firOrUnitType = (FirTypeRef) (firElementConvertElement instanceof FirTypeRef ? firElementConvertElement : null);
                        if (firOrUnitType == null) {
                            firOrUnitType = firTypeRefCopyWithNewSourceKind;
                        }
                    } else {
                        firOrUnitType = firTypeRefCopyWithNewSourceKind;
                    }
                } else {
                    firOrUnitType = toFirOrUnitType(ktPropertyAccessor.getReturnTypeReference());
                }
                firPropertyAccessorBuilder.setReturnTypeRef(firOrUnitType);
                firPropertyAccessorBuilder.setGetter(z);
                firPropertyAccessorBuilder.setStatus(firDeclarationStatusImpl);
                CollectionsKt.addAll(firPropertyAccessorBuilder.getAnnotations(), list);
                extractAnnotationsTo(ktPropertyAccessor, firPropertyAccessorBuilder);
                psiRawFirBuilder2.getContext().getFirFunctionTargets().add(firFunctionTarget);
                firPropertyAccessorBuilder.setSymbol(new FirPropertyAccessorSymbol());
                KtPropertyAccessor ktPropertyAccessor2 = ktPropertyAccessor;
                extractValueParametersTo(ktPropertyAccessor2, firPropertyAccessorBuilder, firPropertyAccessorBuilder.getSymbol(), AbstractRawFirBuilder.ValueParameterDeclaration.SETTER, firTypeRefCopyWithNewSourceKind, list2);
                if (!z && firPropertyAccessorBuilder.getValueParameters().isEmpty()) {
                    List<FirValueParameter> valueParameters = firPropertyAccessorBuilder.getValueParameters();
                    FirDefaultSetterValueParameterBuilder firDefaultSetterValueParameterBuilder = new FirDefaultSetterValueParameterBuilder();
                    firDefaultSetterValueParameterBuilder.setSource(KtSourceElementKt.fakeElement$default(ktPsiSourceElement2, KtFakeSourceElementKind.DefaultAccessor.INSTANCE, 0, 0, 6, null));
                    firDefaultSetterValueParameterBuilder.setModuleData(psiRawFirBuilder2.getBaseModuleData());
                    firDefaultSetterValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                    firDefaultSetterValueParameterBuilder.setReturnTypeRef(firTypeRefCopyWithNewSourceKind);
                    firDefaultSetterValueParameterBuilder.setSymbol(new FirValueParameterSymbol(StandardNames.DEFAULT_VALUE_PARAMETER));
                    CollectionsKt.addAll(firDefaultSetterValueParameterBuilder.getAnnotations(), list2);
                    valueParameters.add(firDefaultSetterValueParameterBuilder.mo6809build());
                }
                FirContractDescription firContractDescriptionObtainContractDescription = obtainContractDescription(ktPropertyAccessor2);
                Pair<FirBlock, FirContractDescription> pairBuildFirBody = buildFirBody(ktPropertyAccessor2);
                FirBlock firBlockComponent1 = pairBuildFirBody.component1();
                FirContractDescription firContractDescriptionComponent2 = pairBuildFirBody.component2();
                firPropertyAccessorBuilder.setBody(firBlockComponent1);
                if (firContractDescriptionObtainContractDescription == null) {
                    firContractDescriptionObtainContractDescription = firContractDescriptionComponent2;
                }
                if (firContractDescriptionObtainContractDescription != null) {
                    firPropertyAccessorBuilder.setContractDescription(firContractDescriptionObtainContractDescription);
                }
                firPropertyAccessorBuilder.setPropertySymbol(firPropertySymbol);
                psiRawFirBuilder2.additionalPropertyAccessorInit(firPropertyAccessorBuilder);
                FirPropertyAccessor firPropertyAccessorMo6809build = firPropertyAccessorBuilder.mo6809build();
                PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
                psiRawFirBuilder3.initContainingClassAttr(firPropertyAccessorMo6809build);
                psiRawFirBuilder3.bindFunctionTarget(firFunctionTarget, firPropertyAccessorMo6809build);
                psiRawFirBuilder3.removeLast(psiRawFirBuilder3.getContext().getFirFunctionTargets());
                return firPropertyAccessorMo6809build;
            }
            if (!z && !ktProperty.isVar()) {
                return null;
            }
            if (ktPropertyAccessor == null || (ktFakeSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, ktPropertyAccessor, null, 1, null)) == null) {
                KtProperty ktProperty3 = ktProperty;
                KtFakeSourceElementKind.DefaultAccessor defaultAccessor = KtFakeSourceElementKind.DefaultAccessor.INSTANCE;
                if (defaultAccessor instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement = new KtRealPsiSourceElement(ktProperty3);
                } else {
                    if (!(defaultAccessor instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement = new KtFakeSourceElement(ktProperty3, defaultAccessor);
                }
            }
            FirDefaultPropertyAccessor firDefaultPropertyAccessorCreateGetterOrSetter = FirDefaultPropertyAccessor.INSTANCE.createGetterOrSetter(ktFakeSourceElement, PsiRawFirBuilder.this.getBaseModuleData(), FirDeclarationOrigin.Source.INSTANCE, firTypeRefCopyWithNewSourceKind, visibility, firPropertySymbol, z, list2);
            PsiRawFirBuilder psiRawFirBuilder4 = PsiRawFirBuilder.this;
            if (ktPropertyAccessor != null) {
                extractAnnotationsFrom(firDefaultPropertyAccessorCreateGetterOrSetter, ktPropertyAccessor);
            }
            firDefaultPropertyAccessorCreateGetterOrSetter.replaceAnnotations(UtilsKt.smartPlus(MutableOrEmptyList.m6756boximpl(firDefaultPropertyAccessorCreateGetterOrSetter.m6857getAnnotations5e3fPpI()), list));
            firDefaultPropertyAccessorCreateGetterOrSetter.setStatus(firDeclarationStatusImpl);
            psiRawFirBuilder4.initContainingClassAttr(firDefaultPropertyAccessorCreateGetterOrSetter);
            return firDefaultPropertyAccessorCreateGetterOrSetter;
        }

        private final FirDeclarationStatus obtainPropertyComponentStatus(Visibility componentVisibility, KtDeclaration declaration, KtProperty property) {
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(componentVisibility, declaration != null ? PsiRawFirBuilder.this.getModality(declaration) : null);
            boolean z = false;
            firDeclarationStatusImpl.setInline(property.hasModifier(KtTokens.INLINE_KEYWORD) || (declaration != null && declaration.hasModifier(KtTokens.INLINE_KEYWORD)));
            firDeclarationStatusImpl.setExternal(property.hasModifier(KtTokens.EXTERNAL_KEYWORD) || (declaration != null && declaration.hasModifier(KtTokens.EXTERNAL_KEYWORD)));
            if (declaration != null && declaration.hasModifier(KtTokens.LATEINIT_KEYWORD)) {
                z = true;
            }
            firDeclarationStatusImpl.setLateInit(z);
            return firDeclarationStatusImpl;
        }

        /* JADX WARN: Code duplicated, block: B:10:0x002e  */
        private final FirBackingField toFirBackingField(KtBackingField ktBackingField, KtProperty ktProperty, FirPropertySymbol firPropertySymbol, FirTypeRef firTypeRef, List<? extends FirAnnotationCall> list) {
            Visibilities.Private visibility;
            if ((ktBackingField != null ? PsiRawFirBuilder.this.getVisibility(ktBackingField) : null) != null) {
                KtBackingField ktBackingField2 = ktBackingField;
                if (!Intrinsics.areEqual(PsiRawFirBuilder.this.getVisibility(ktBackingField2), Visibilities.Unknown.INSTANCE)) {
                    visibility = PsiRawFirBuilder.this.getVisibility(ktBackingField2);
                } else {
                    visibility = Visibilities.Private.INSTANCE;
                }
            } else {
                visibility = Visibilities.Private.INSTANCE;
            }
            FirDeclarationStatus firDeclarationStatusObtainPropertyComponentStatus = obtainPropertyComponentStatus(visibility, ktBackingField, ktProperty);
            FirExpression initializerExpression = ktBackingField != null ? toInitializerExpression(ktBackingField) : null;
            FirTypeRef firOrImplicitType = toFirOrImplicitType(ktBackingField != null ? ktBackingField.getReturnTypeReference() : null);
            KtPsiSourceElement ktPsiSourceElement = ktBackingField != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, ktBackingField, null, 1, null) : null;
            if (ktBackingField != null) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                FirBackingFieldBuilder firBackingFieldBuilder = new FirBackingFieldBuilder();
                firBackingFieldBuilder.setSource(ktPsiSourceElement);
                firBackingFieldBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
                firBackingFieldBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firBackingFieldBuilder.setReturnTypeRef(firOrImplicitType);
                firBackingFieldBuilder.setStatus(firDeclarationStatusObtainPropertyComponentStatus);
                extractAnnotationsTo(ktBackingField, firBackingFieldBuilder);
                CollectionsKt.addAll(firBackingFieldBuilder.getAnnotations(), list);
                firBackingFieldBuilder.setName(StandardNames.BACKING_FIELD);
                firBackingFieldBuilder.setSymbol(new FirBackingFieldSymbol(new CallableId(firBackingFieldBuilder.getName(), (FqName) null, 2, (DefaultConstructorMarker) null)));
                firBackingFieldBuilder.setPropertySymbol(firPropertySymbol);
                firBackingFieldBuilder.setInitializer(initializerExpression);
                firBackingFieldBuilder.setVar(ktProperty.isVar());
                firBackingFieldBuilder.setVal(!ktProperty.isVar());
                psiRawFirBuilder.additionalBackingFieldInit(firBackingFieldBuilder);
                return firBackingFieldBuilder.mo6809build();
            }
            return new FirDefaultPropertyBackingField(PsiRawFirBuilder.this.getBaseModuleData(), FirDeclarationOrigin.Source.INSTANCE, PsiRawFirBuilder.this.toFirSourceElement((PsiElement) ktProperty, (KtFakeSourceElementKind) KtFakeSourceElementKind.DefaultAccessor.INSTANCE), CollectionsKt.toMutableList((Collection) list), UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.DefaultAccessor.INSTANCE), ktProperty.isVar(), firPropertySymbol, firDeclarationStatusObtainPropertyComponentStatus, null, 256, null);
        }

        private final FirValueParameter toFirValueParameter(final KtParameter ktParameter, FirTypeRef firTypeRef, FirFunctionSymbol<?> firFunctionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, List<? extends FirAnnotation> list) throws Throwable {
            Object firExpression;
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            Name nameAsSafeName = ktParameter.getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
            Name nameConvertValueParameterName = psiRawFirBuilder.convertValueParameterName(nameAsSafeName, valueParameterDeclaration, new Function0<String>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$toFirValueParameter$name$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    ASTNode node;
                    PsiElement psiElementMo7392getNameIdentifier = ktParameter.mo7392getNameIdentifier();
                    if (psiElementMo7392getNameIdentifier == null || (node = psiElementMo7392getNameIdentifier.getNode()) == null) {
                        return null;
                    }
                    return node.getText();
                }
            });
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
            FirExpression firExpression2 = null;
            firValueParameterBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, ktParameter, null, 1, null));
            firValueParameterBuilder.setModuleData(psiRawFirBuilder2.getBaseModuleData());
            firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            if (ktParameter.mo7396getTypeReference() != null) {
                firTypeRef = toFirOrErrorType(ktParameter.mo7396getTypeReference());
            } else if (firTypeRef == null) {
                firTypeRef = valueParameterDeclaration.getShouldExplicitParameterTypeBePresent() ? psiRawFirBuilder2.createNoTypeForParameterTypeRef() : toFirOrImplicitType(null);
            }
            firValueParameterBuilder.setReturnTypeRef(firTypeRef);
            firValueParameterBuilder.setName(nameConvertValueParameterName);
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(nameConvertValueParameterName));
            if (ktParameter.hasDefaultValue()) {
                PsiRawFirBuilder$Visitor$buildOrLazyExpression$1 psiRawFirBuilder$Visitor$buildOrLazyExpression$1 = new PsiRawFirBuilder$Visitor$buildOrLazyExpression$1(null);
                int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i == 1) {
                    firExpression = toFirExpression(new Function0<KtExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$toFirValueParameter$1$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final KtExpression invoke() {
                            return ktParameter.getDefaultValue();
                        }
                    }, "Should have default value");
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                    if (i2 == 1) {
                        firExpression = psiRawFirBuilder$Visitor$buildOrLazyExpression$1.invoke();
                    } else {
                        if (i2 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        firExpression = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyExpression$1));
                    }
                }
                firExpression2 = (FirExpression) firExpression;
            }
            firValueParameterBuilder.setDefaultValue(firExpression2);
            firValueParameterBuilder.setCrossinline(ktParameter.hasModifier(KtTokens.CROSSINLINE_KEYWORD));
            firValueParameterBuilder.setNoinline(ktParameter.hasModifier(KtTokens.NOINLINE_KEYWORD));
            firValueParameterBuilder.setVararg(ktParameter.isVarArg());
            firValueParameterBuilder.setContainingFunctionSymbol(firFunctionSymbol);
            addAnnotationsFrom(firValueParameterBuilder, ktParameter, valueParameterDeclaration == AbstractRawFirBuilder.ValueParameterDeclaration.PRIMARY_CONSTRUCTOR);
            CollectionsKt.addAll(firValueParameterBuilder.getAnnotations(), list);
            return firValueParameterBuilder.mo6809build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void addAnnotationsFrom(FirValueParameterBuilder firValueParameterBuilder, KtParameter ktParameter, boolean z) {
            for (KtAnnotationEntry ktAnnotationEntry : ktParameter.getAnnotationEntries()) {
                Intrinsics.checkNotNull(ktAnnotationEntry);
                FirElement firElementConvertElement = convertElement(ktAnnotationEntry, null);
                if (firElementConvertElement != null) {
                    FirAnnotation firAnnotation = (FirAnnotation) firElementConvertElement;
                    FirAnnotation firAnnotation2 = (!z || ConversionUtilsKt.appliesToPrimaryConstructorParameter(firAnnotation.getUseSiteTarget())) ? firAnnotation : null;
                    if (firAnnotation2 != null) {
                        firValueParameterBuilder.getAnnotations().add(firAnnotation2);
                    }
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotation");
                }
            }
        }

        private final FirProperty toFirProperty(KtParameter ktParameter, FirValueParameter firValueParameter) {
            FirDefaultPropertySetter firDefaultPropertySetter;
            boolean z;
            if (!ktParameter.hasValOrVar()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
            FirElement firElementConvertElement = ktTypeReferenceMo7396getTypeReference != null ? convertElement(ktTypeReferenceMo7396getTypeReference, null) : null;
            if (!(firElementConvertElement instanceof FirTypeRef)) {
                firElementConvertElement = null;
            }
            FirErrorTypeRef firErrorTypeRefCreateNoTypeForParameterTypeRef = (FirTypeRef) firElementConvertElement;
            if (firErrorTypeRefCreateNoTypeForParameterTypeRef == null) {
                firErrorTypeRefCreateNoTypeForParameterTypeRef = PsiRawFirBuilder.this.createNoTypeForParameterTypeRef();
            }
            KtParameter ktParameter2 = ktParameter;
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(PsiRawFirBuilder.this.getVisibility(ktParameter2), PsiRawFirBuilder.this.getModality(ktParameter));
            firDeclarationStatusImpl.setExpect(PsiUtilsKt.hasExpectModifier(ktParameter2) || PsiRawFirBuilder.this.getContext().getContainerIsExpect());
            firDeclarationStatusImpl.setActual(PsiUtilsKt.hasActualModifier(ktParameter2));
            firDeclarationStatusImpl.setOverride(ktParameter.hasModifier(KtTokens.OVERRIDE_KEYWORD));
            firDeclarationStatusImpl.setConst(ktParameter.hasModifier(KtTokens.CONST_KEYWORD));
            KtPsiSourceElement firSourceElement = PsiRawFirBuilder.this.toFirSourceElement((PsiElement) ktParameter, (KtFakeSourceElementKind) KtFakeSourceElementKind.PropertyFromParameter.INSTANCE);
            Name nameAsSafeName = ktParameter.getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
            ArrayList arrayList = new ArrayList();
            for (KtAnnotationEntry ktAnnotationEntry : ktParameter.getAnnotationEntries()) {
                ArrayList arrayList2 = arrayList;
                Intrinsics.checkNotNull(ktAnnotationEntry);
                FirElement firElementConvertElement2 = convertElement(ktAnnotationEntry, null);
                if (firElementConvertElement2 != null) {
                    arrayList2.add((FirAnnotationCall) firElementConvertElement2);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotationCall");
                }
            }
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
            KtPsiSourceElement ktPsiSourceElement = firSourceElement;
            firPropertyBuilder.setSource(ktPsiSourceElement);
            firPropertyBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firPropertyBuilder.setReturnTypeRef(UtilsKt.copyWithNewSourceKind(firErrorTypeRefCreateNoTypeForParameterTypeRef, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE));
            firPropertyBuilder.setName(nameAsSafeName);
            FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
            firPropertyAccessExpressionBuilder.setSource(ktPsiSourceElement);
            FirPropertyFromParameterResolvedNamedReferenceBuilder firPropertyFromParameterResolvedNamedReferenceBuilder = new FirPropertyFromParameterResolvedNamedReferenceBuilder();
            firPropertyFromParameterResolvedNamedReferenceBuilder.setSource(ktPsiSourceElement);
            firPropertyFromParameterResolvedNamedReferenceBuilder.setName(nameAsSafeName);
            firPropertyFromParameterResolvedNamedReferenceBuilder.setResolvedSymbol(firValueParameter.getSymbol());
            firPropertyAccessExpressionBuilder.setCalleeReference(firPropertyFromParameterResolvedNamedReferenceBuilder.build());
            firPropertyBuilder.setInitializer(firPropertyAccessExpressionBuilder.mo6809build());
            firPropertyBuilder.setVar(ktParameter.isMutable());
            firPropertyBuilder.setSymbol(new FirPropertySymbol(psiRawFirBuilder.callableIdForName(nameAsSafeName)));
            firPropertyBuilder.setLocal(false);
            KtSourceElement ktSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(ktPsiSourceElement, KtFakeSourceElementKind.DefaultAccessor.INSTANCE, 0, 0, 6, null);
            FirModuleData baseModuleData = psiRawFirBuilder.getBaseModuleData();
            FirDeclarationOrigin.Source source = FirDeclarationOrigin.Source.INSTANCE;
            ArrayList arrayList3 = arrayList;
            ArrayList arrayList4 = new ArrayList();
            for (Object obj : arrayList3) {
                FirAnnotationCall firAnnotationCall = (FirAnnotationCall) obj;
                if (firAnnotationCall.getUseSiteTarget() == AnnotationUseSiteTarget.FIELD || firAnnotationCall.getUseSiteTarget() == AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD) {
                    arrayList4.add(obj);
                }
            }
            FirDeclarationStatusImpl firDeclarationStatusImpl2 = firDeclarationStatusImpl;
            firPropertyBuilder.setBackingField(new FirDefaultPropertyBackingField(baseModuleData, source, ktSourceElementFakeElement$default, CollectionsKt.toMutableList((Collection) arrayList4), UtilsKt.copyWithNewSourceKind(firPropertyBuilder.getReturnTypeRef(), KtFakeSourceElementKind.DefaultAccessor.INSTANCE), firPropertyBuilder.isVar(), firPropertyBuilder.getSymbol(), UtilsKt.copy(firDeclarationStatusImpl2, (2097147 & 1) != 0 ? firDeclarationStatusImpl2.getVisibility() : null, (2097147 & 2) != 0 ? firDeclarationStatusImpl2.getModality() : null, (2097147 & 4) != 0 ? firDeclarationStatusImpl2.isExpect() : false, (2097147 & 8) != 0 ? firDeclarationStatusImpl2.isActual() : false, (2097147 & 16) != 0 ? firDeclarationStatusImpl2.isOverride() : false, (2097147 & 32) != 0 ? firDeclarationStatusImpl2.isOperator() : false, (2097147 & 64) != 0 ? firDeclarationStatusImpl2.isInfix() : false, (2097147 & 128) != 0 ? firDeclarationStatusImpl2.isInline() : false, (2097147 & 256) != 0 ? firDeclarationStatusImpl2.isTailRec() : false, (2097147 & 512) != 0 ? firDeclarationStatusImpl2.isExternal() : false, (2097147 & 1024) != 0 ? firDeclarationStatusImpl2.isConst() : false, (2097147 & 2048) != 0 ? firDeclarationStatusImpl2.isLateInit() : false, (2097147 & 4096) != 0 ? firDeclarationStatusImpl2.isInner() : false, (2097147 & 8192) != 0 ? firDeclarationStatusImpl2.isCompanion() : false, (2097147 & 16384) != 0 ? firDeclarationStatusImpl2.isData() : false, (2097147 & 32768) != 0 ? firDeclarationStatusImpl2.isSuspend() : false, (2097147 & 65536) != 0 ? firDeclarationStatusImpl2.isStatic() : false, (2097147 & 131072) != 0 ? firDeclarationStatusImpl2.isFromSealedClass() : false, (2097147 & 262144) != 0 ? firDeclarationStatusImpl2.isFromEnumClass() : false, (2097147 & 524288) != 0 ? firDeclarationStatusImpl2.isFun() : false, (2097147 & 1048576) != 0 ? firDeclarationStatusImpl2.getHasStableParameterNames() : false), null, 256, null));
            firPropertyBuilder.setStatus(firDeclarationStatusImpl2);
            FirDefaultPropertyGetter firDefaultPropertyGetter = new FirDefaultPropertyGetter(ktSourceElementFakeElement$default, psiRawFirBuilder.getBaseModuleData(), FirDeclarationOrigin.Source.INSTANCE, UtilsKt.copyWithNewSourceKind(firErrorTypeRefCreateNoTypeForParameterTypeRef, KtFakeSourceElementKind.DefaultAccessor.INSTANCE), psiRawFirBuilder.getVisibility(ktParameter2), firPropertyBuilder.getSymbol(), null, null, null, null, XKeySym.XK_Amacron, null);
            psiRawFirBuilder.initContainingClassAttr(firDefaultPropertyGetter);
            firDefaultPropertyGetter.replaceAnnotations(ConversionUtilsKt.filterUseSiteTarget(arrayList, AnnotationUseSiteTarget.PROPERTY_GETTER));
            firPropertyBuilder.setGetter(firDefaultPropertyGetter);
            if (ktParameter.isMutable()) {
                FirDefaultPropertySetter firDefaultPropertySetter2 = new FirDefaultPropertySetter(ktSourceElementFakeElement$default, psiRawFirBuilder.getBaseModuleData(), FirDeclarationOrigin.Source.INSTANCE, UtilsKt.copyWithNewSourceKind(firErrorTypeRefCreateNoTypeForParameterTypeRef, KtFakeSourceElementKind.DefaultAccessor.INSTANCE), psiRawFirBuilder.getVisibility(ktParameter2), firPropertyBuilder.getSymbol(), null, null, null, ConversionUtilsKt.filterUseSiteTarget(arrayList, AnnotationUseSiteTarget.SETTER_PARAMETER), null, 1472, null);
                psiRawFirBuilder.initContainingClassAttr(firDefaultPropertySetter2);
                firDefaultPropertySetter2.replaceAnnotations(ConversionUtilsKt.filterUseSiteTarget(arrayList, AnnotationUseSiteTarget.PROPERTY_SETTER));
                firDefaultPropertySetter = firDefaultPropertySetter2;
            } else {
                firDefaultPropertySetter = null;
            }
            firPropertyBuilder.setSetter(firDefaultPropertySetter);
            List<FirAnnotation> annotations = firPropertyBuilder.getAnnotations();
            ArrayList arrayList5 = new ArrayList();
            for (Object obj2 : arrayList3) {
                FirAnnotationCall firAnnotationCall2 = (FirAnnotationCall) obj2;
                if (firAnnotationCall2.getUseSiteTarget() == null || firAnnotationCall2.getUseSiteTarget() == AnnotationUseSiteTarget.PROPERTY) {
                    arrayList5.add(obj2);
                }
            }
            CollectionsKt.addAll(annotations, arrayList5);
            firPropertyBuilder.setDispatchReceiverType(psiRawFirBuilder.currentDispatchReceiverType());
            FirProperty firPropertyMo6809build = firPropertyBuilder.mo6809build();
            if (firValueParameter.getIsVararg()) {
                z = true;
                DeclarationAttributesKt.setFromVararg(firPropertyMo6809build, true);
            } else {
                z = true;
            }
            ClassMembersKt.setCorrespondingProperty(firValueParameter, firPropertyMo6809build);
            DeclarationAttributesKt.setFromPrimaryConstructor(firPropertyMo6809build, Boolean.valueOf(z));
            return firPropertyMo6809build;
        }

        private final void extractAnnotationsFrom(FirDefaultPropertyAccessor firDefaultPropertyAccessor, KtAnnotated ktAnnotated) {
            extractAnnotationsTo(ktAnnotated, firDefaultPropertyAccessor);
        }

        private final void extractAnnotationsTo(KtAnnotated ktAnnotated, FirAnnotationContainer firAnnotationContainer) {
            if (ktAnnotated.getAnnotationEntries().isEmpty()) {
                return;
            }
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            listCreateListBuilder.addAll(firAnnotationContainer.getAnnotations());
            for (KtAnnotationEntry ktAnnotationEntry : ktAnnotated.getAnnotationEntries()) {
                Intrinsics.checkNotNull(ktAnnotationEntry);
                FirElement firElementConvertElement = convertElement(ktAnnotationEntry, null);
                if (firElementConvertElement != null) {
                    listCreateListBuilder.add((FirAnnotation) firElementConvertElement);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotation");
                }
            }
            firAnnotationContainer.replaceAnnotations(CollectionsKt.build(listCreateListBuilder));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void extractAnnotationsTo(KtAnnotated ktAnnotated, FirAnnotationContainerBuilder firAnnotationContainerBuilder) {
            for (KtAnnotationEntry ktAnnotationEntry : ktAnnotated.getAnnotationEntries()) {
                List<FirAnnotation> annotations = firAnnotationContainerBuilder.getAnnotations();
                Intrinsics.checkNotNull(ktAnnotationEntry);
                FirElement firElementConvertElement = convertElement(ktAnnotationEntry, null);
                if (firElementConvertElement != null) {
                    annotations.add((FirAnnotation) firElementConvertElement);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotation");
                }
            }
        }

        private final void extractTypeParametersTo(KtTypeParameterListOwner ktTypeParameterListOwner, FirTypeParameterRefsOwnerBuilder firTypeParameterRefsOwnerBuilder, FirBasedSymbol<?> firBasedSymbol) {
            for (KtTypeParameter ktTypeParameter : ktTypeParameterListOwner.getTypeParameters()) {
                List<FirTypeParameterRef> typeParameters = firTypeParameterRefsOwnerBuilder.getTypeParameters();
                Intrinsics.checkNotNull(ktTypeParameter);
                typeParameters.add(extractTypeParameter(ktTypeParameter, firBasedSymbol));
            }
        }

        private final void extractTypeParametersTo(KtTypeParameterListOwner ktTypeParameterListOwner, FirTypeParametersOwnerBuilder firTypeParametersOwnerBuilder, FirBasedSymbol<?> firBasedSymbol) {
            for (KtTypeParameter ktTypeParameter : ktTypeParameterListOwner.getTypeParameters()) {
                List<FirTypeParameter> typeParameters = firTypeParametersOwnerBuilder.getTypeParameters();
                Intrinsics.checkNotNull(ktTypeParameter);
                typeParameters.add(extractTypeParameter(ktTypeParameter, firBasedSymbol));
            }
        }

        private final FirTypeParameter extractTypeParameter(KtTypeParameter parameter, FirBasedSymbol<?> declarationSymbol) throws Throwable {
            Name nameSpecial;
            KtSimpleNameExpression referenceExpression;
            List<FirQualifierPart> qualifier;
            FirQualifierPart firQualifierPart;
            Name nameAsSafeName = parameter.getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            firTypeParameterBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, parameter, null, 1, null));
            firTypeParameterBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firTypeParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firTypeParameterBuilder.setName(nameAsSafeName);
            firTypeParameterBuilder.setSymbol(new FirTypeParameterSymbol());
            firTypeParameterBuilder.setContainingDeclarationSymbol(declarationSymbol);
            Variance variance = parameter.getVariance();
            Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
            firTypeParameterBuilder.setVariance(variance);
            firTypeParameterBuilder.setReified(parameter.hasModifier(KtTokens.REIFIED_KEYWORD));
            extractAnnotationsTo(parameter, firTypeParameterBuilder);
            KtTypeReference extendsBound = parameter.getExtendsBound();
            if (extendsBound != null) {
                List<FirTypeRef> bounds = firTypeParameterBuilder.getBounds();
                FirElement firElementConvertElement = convertElement(extendsBound, null);
                if (firElementConvertElement != null) {
                    bounds.add((FirTypeRef) firElementConvertElement);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirTypeRef");
                }
            }
            KtTypeParameterListOwner ktTypeParameterListOwner = (KtTypeParameterListOwner) PsiTreeUtil.getParentOfType(parameter, KtTypeParameterListOwner.class, true);
            if (ktTypeParameterListOwner != null) {
                for (KtTypeConstraint ktTypeConstraint : ktTypeParameterListOwner.getTypeConstraints()) {
                    KtSimpleNameExpression subjectTypeParameterName = ktTypeConstraint.getSubjectTypeParameterName();
                    if (Intrinsics.areEqual(subjectTypeParameterName != null ? subjectTypeParameterName.getReferencedNameAsName() : null, nameAsSafeName)) {
                        firTypeParameterBuilder.getBounds().add(toFirOrErrorType(ktTypeConstraint.getBoundTypeReference()));
                    }
                    for (KtAnnotationEntry ktAnnotationEntry : ktTypeConstraint.getAnnotationEntries()) {
                        List<FirAnnotation> annotations = firTypeParameterBuilder.getAnnotations();
                        FirErrorAnnotationCallBuilder firErrorAnnotationCallBuilder = new FirErrorAnnotationCallBuilder();
                        Intrinsics.checkNotNull(ktAnnotationEntry);
                        firErrorAnnotationCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, ktAnnotationEntry, null, 1, null));
                        KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
                        firErrorAnnotationCallBuilder.setUseSiteTarget(useSiteTarget != null ? useSiteTarget.getAnnotationUseSiteTarget() : null);
                        firErrorAnnotationCallBuilder.setAnnotationTypeRef(toFirOrErrorType(ktAnnotationEntry.getTypeReference()));
                        firErrorAnnotationCallBuilder.setDiagnostic(new ConeSimpleDiagnostic("Type parameter annotations are not allowed inside where clauses", DiagnosticKind.AnnotationNotAllowed));
                        FirTypeRef annotationTypeRef = firErrorAnnotationCallBuilder.getAnnotationTypeRef();
                        FirUserTypeRef firUserTypeRef = annotationTypeRef instanceof FirUserTypeRef ? (FirUserTypeRef) annotationTypeRef : null;
                        if (firUserTypeRef == null || (qualifier = firUserTypeRef.getQualifier()) == null || (firQualifierPart = (FirQualifierPart) CollectionsKt.last((List) qualifier)) == null || (nameSpecial = firQualifierPart.getName()) == null) {
                            nameSpecial = Name.special("<no-annotation-name>");
                            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
                        }
                        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                        KtTypeReference typeReference = ktAnnotationEntry.getTypeReference();
                        KtTypeElement typeElement = typeReference != null ? typeReference.getTypeElement() : null;
                        KtUserType ktUserType = typeElement instanceof KtUserType ? (KtUserType) typeElement : null;
                        firSimpleNamedReferenceBuilder.setSource((ktUserType == null || (referenceExpression = ktUserType.getReferenceExpression()) == null) ? null : (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, referenceExpression, null, 1, null));
                        firSimpleNamedReferenceBuilder.setName(nameSpecial);
                        firErrorAnnotationCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
                        extractArgumentsTo(ktAnnotationEntry, firErrorAnnotationCallBuilder);
                        List<FirTypeProjection> typeArguments = firErrorAnnotationCallBuilder.getTypeArguments();
                        List<KtTypeProjection> typeArguments2 = ktAnnotationEntry.getTypeArguments();
                        Intrinsics.checkNotNullExpressionValue(typeArguments2, "getTypeArguments(...)");
                        appendTypeArguments(typeArguments, typeArguments2);
                        annotations.add(firErrorAnnotationCallBuilder.mo6809build());
                    }
                }
                FirDeclarationBuildingUtilsKt.addDefaultBoundIfNecessary(firTypeParameterBuilder);
            }
            return firTypeParameterBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitTypeParameter(KtTypeParameter parameter, FirElement data) {
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            throw new AssertionError("KtTypeParameter should be process via extractTypeParameter");
        }

        private final <T extends FirDeclaration & FirTypeParameterRefsOwner> void fillDanglingConstraintsTo(KtTypeParameterListOwner ktTypeParameterListOwner, T t) {
            Name referencedNameAsName;
            List<KtTypeParameter> typeParameters = ktTypeParameterListOwner.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                Name nameAsName = ((KtTypeParameter) it.next()).getNameAsName();
                if (nameAsName != null) {
                    arrayList.add(nameAsName);
                }
            }
            Set set = CollectionsKt.toSet(arrayList);
            List<KtTypeConstraint> typeConstraints = ktTypeParameterListOwner.getTypeConstraints();
            Intrinsics.checkNotNullExpressionValue(typeConstraints, "getTypeConstraints(...)");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            ArrayList arrayList2 = new ArrayList();
            for (KtTypeConstraint ktTypeConstraint : typeConstraints) {
                KtSimpleNameExpression subjectTypeParameterName = ktTypeConstraint.getSubjectTypeParameterName();
                DanglingTypeConstraint danglingTypeConstraint = null;
                if (subjectTypeParameterName != null && (referencedNameAsName = subjectTypeParameterName.getReferencedNameAsName()) != null && !set.contains(referencedNameAsName)) {
                    KtSimpleNameExpression subjectTypeParameterName2 = ktTypeConstraint.getSubjectTypeParameterName();
                    Intrinsics.checkNotNull(subjectTypeParameterName2);
                    danglingTypeConstraint = new DanglingTypeConstraint(referencedNameAsName, AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, subjectTypeParameterName2, null, 1, null));
                }
                if (danglingTypeConstraint != null) {
                    arrayList2.add(danglingTypeConstraint);
                }
            }
            ArrayList arrayList3 = arrayList2;
            if (arrayList3.isEmpty()) {
                return;
            }
            DeclarationAttributesKt.setDanglingTypeConstraints(t, arrayList3);
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void extractValueParametersTo$default(Visitor visitor, KtDeclarationWithBody ktDeclarationWithBody, FirFunctionBuilder firFunctionBuilder, FirFunctionSymbol firFunctionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, FirTypeRef firTypeRef, List list, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: extractValueParametersTo");
            }
            if ((i & 8) != 0) {
                firTypeRef = null;
            }
            FirTypeRef firTypeRef2 = firTypeRef;
            if ((i & 16) != 0) {
                list = CollectionsKt.emptyList();
            }
            visitor.extractValueParametersTo(ktDeclarationWithBody, firFunctionBuilder, firFunctionSymbol, valueParameterDeclaration, firTypeRef2, list);
        }

        private final void extractValueParametersTo(KtDeclarationWithBody ktDeclarationWithBody, FirFunctionBuilder firFunctionBuilder, FirFunctionSymbol<?> firFunctionSymbol, AbstractRawFirBuilder.ValueParameterDeclaration valueParameterDeclaration, FirTypeRef firTypeRef, List<? extends FirAnnotation> list) {
            for (KtParameter ktParameter : ktDeclarationWithBody.getValueParameters()) {
                List<FirValueParameter> valueParameters = firFunctionBuilder.getValueParameters();
                Intrinsics.checkNotNull(ktParameter);
                valueParameters.add(convertValueParameter(ktParameter, firFunctionSymbol, firTypeRef, valueParameterDeclaration, list));
            }
        }

        private final void extractArgumentsTo(KtCallElement ktCallElement, FirCallBuilder firCallBuilder) throws Throwable {
            Object firExpression;
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
            KtValueArgumentList valueArgumentList = ktCallElement.getValueArgumentList();
            firArgumentListBuilder.setSource(valueArgumentList != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, valueArgumentList, null, 1, null) : null);
            for (ValueArgument valueArgument : ktCallElement.getValueArguments()) {
                PsiElement psiElement = valueArgument instanceof PsiElement ? (PsiElement) valueArgument : null;
                PsiRawFirBuilder$Visitor$buildOrLazyExpression$1 psiRawFirBuilder$Visitor$buildOrLazyExpression$1 = new PsiRawFirBuilder$Visitor$buildOrLazyExpression$1(psiElement != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, psiElement, null, 1, null) : null);
                int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i == 1) {
                    firExpression = toFirExpression(valueArgument);
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                    if (i2 == 1) {
                        firExpression = psiRawFirBuilder$Visitor$buildOrLazyExpression$1.invoke();
                    } else {
                        if (i2 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        firExpression = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyExpression$1));
                    }
                }
                FirLambdaArgumentExpression firLambdaArgumentExpressionMo6809build = (FirExpression) firExpression;
                List<FirExpression> arguments = firArgumentListBuilder.getArguments();
                if (valueArgument instanceof KtLambdaArgument) {
                    FirLambdaArgumentExpressionBuilder firLambdaArgumentExpressionBuilder = new FirLambdaArgumentExpressionBuilder();
                    Intrinsics.checkNotNull(valueArgument);
                    firLambdaArgumentExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, valueArgument, null, 1, null));
                    firLambdaArgumentExpressionBuilder.setExpression(firLambdaArgumentExpressionMo6809build);
                    firLambdaArgumentExpressionMo6809build = firLambdaArgumentExpressionBuilder.mo6809build();
                }
                arguments.add(firLambdaArgumentExpressionMo6809build);
            }
            firCallBuilder.setArgumentList(firArgumentListBuilder.build());
        }

        protected final FirField buildFieldForSupertypeDelegate(final KtDelegatedSuperTypeEntry entry, FirTypeRef type, int fieldOrd) throws Throwable {
            Object firExpression;
            Intrinsics.checkNotNullParameter(entry, "entry");
            Intrinsics.checkNotNullParameter(type, "type");
            KtPsiSourceElement firSourceElement = PsiRawFirBuilder.this.toFirSourceElement((PsiElement) entry, (KtFakeSourceElementKind) KtFakeSourceElementKind.ClassDelegationField.INSTANCE);
            PsiRawFirBuilder$Visitor$buildOrLazyExpression$1 psiRawFirBuilder$Visitor$buildOrLazyExpression$1 = new PsiRawFirBuilder$Visitor$buildOrLazyExpression$1(firSourceElement);
            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
            if (i == 1) {
                firExpression = toFirExpression(new Function0<KtExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$buildFieldForSupertypeDelegate$delegateExpression$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final KtExpression invoke() {
                        return entry.getDelegateExpression();
                    }
                }, "Should have delegate");
            } else if (i == 2) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i2 == 1) {
                    firExpression = psiRawFirBuilder$Visitor$buildOrLazyExpression$1.invoke();
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    firExpression = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyExpression$1));
                }
            } else {
                throw new NoWhenBranchMatchedException();
            }
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirFieldBuilder firFieldBuilder = new FirFieldBuilder();
            firFieldBuilder.setSource(firSourceElement);
            firFieldBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firFieldBuilder.setOrigin(FirDeclarationOrigin.Synthetic.DelegateField.INSTANCE);
            firFieldBuilder.setName(NameUtils.delegateFieldName(fieldOrd));
            firFieldBuilder.setReturnTypeRef(type);
            firFieldBuilder.setSymbol(new FirFieldSymbol(new CallableId(psiRawFirBuilder.getContext().getCurrentClassId(), firFieldBuilder.getName())));
            firFieldBuilder.setVar(false);
            firFieldBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Private.INSTANCE, Modality.FINAL));
            firFieldBuilder.setInitializer((FirExpression) firExpression);
            firFieldBuilder.setDispatchReceiverType(psiRawFirBuilder.currentDispatchReceiverType());
            return firFieldBuilder.mo6809build();
        }

        private final Pair<FirTypeRef, Map<Integer, FirFieldSymbol>> extractSuperTypeListEntriesTo(KtClassOrObject ktClassOrObject, FirClassBuilder firClassBuilder, FirTypeRef firTypeRef, FirTypeRef firTypeRef2, ClassKind classKind, List<? extends FirTypeParameterRef> list, boolean z) {
            FirImplicitTypeRefImplWithoutSource implicitAnyType;
            FirTypeRef firTypeRef3;
            ConeKotlinType coneType;
            ArrayList arrayList = new ArrayList();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int i = 0;
            KtSuperTypeListEntry ktSuperTypeListEntry = null;
            FirTypeRef implicitAnyType2 = null;
            for (Object obj : ktClassOrObject.getSuperTypeListEntries()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                KtSuperTypeListEntry ktSuperTypeListEntry2 = (KtSuperTypeListEntry) obj;
                if (ktSuperTypeListEntry2 instanceof KtSuperTypeEntry) {
                    firClassBuilder.getSuperTypeRefs().add(toFirOrErrorType(((KtSuperTypeEntry) ktSuperTypeListEntry2).getTypeReference()));
                } else if (ktSuperTypeListEntry2 instanceof KtSuperTypeCallEntry) {
                    implicitAnyType2 = toFirOrErrorType(((KtSuperTypeCallEntry) ktSuperTypeListEntry2).getCalleeExpression().getTypeReference());
                    List<FirTypeRef> superTypeRefs = firClassBuilder.getSuperTypeRefs();
                    Intrinsics.checkNotNull(implicitAnyType2);
                    superTypeRefs.add(implicitAnyType2);
                    arrayList.add(TuplesKt.to(ktSuperTypeListEntry2, implicitAnyType2));
                    ktSuperTypeListEntry = ktSuperTypeListEntry2;
                } else if (ktSuperTypeListEntry2 instanceof KtDelegatedSuperTypeEntry) {
                    KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry = (KtDelegatedSuperTypeEntry) ktSuperTypeListEntry2;
                    FirTypeRef firOrErrorType = toFirOrErrorType(ktDelegatedSuperTypeEntry.getTypeReference());
                    firClassBuilder.getSuperTypeRefs().add(firOrErrorType);
                    linkedHashMap.put(Integer.valueOf(i), buildFieldForSupertypeDelegate(ktDelegatedSuperTypeEntry, firOrErrorType, linkedHashMap.size()).getSymbol());
                }
                i = i2;
            }
            boolean z2 = ktClassOrObject instanceof KtClass;
            if (z2 && classKind == ClassKind.ENUM_CLASS && ktSuperTypeListEntry == null) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(psiRawFirBuilder.getImplicitEnumType().getType().getLookupTag(), (firTypeRef == null || (coneType = FirTypeUtilsKt.getConeType(firTypeRef)) == null) ? new ConeTypeProjection[0] : new ConeKotlinType[]{coneType}, false, null, 8, null));
                implicitAnyType2 = firResolvedTypeRefBuilder.mo6809build();
                List<FirTypeRef> superTypeRefs2 = firClassBuilder.getSuperTypeRefs();
                Intrinsics.checkNotNull(implicitAnyType2);
                superTypeRefs2.add(implicitAnyType2);
            } else if (z2 && classKind == ClassKind.ANNOTATION_CLASS) {
                firClassBuilder.getSuperTypeRefs().add(PsiRawFirBuilder.this.getImplicitAnnotationType());
                implicitAnyType2 = PsiRawFirBuilder.this.getImplicitAnyType();
            }
            if (classKind == ClassKind.ENUM_ENTRY && z2) {
                implicitAnyType = firTypeRef2 == null ? PsiRawFirBuilder.this.getImplicitAnyType() : firTypeRef2;
            } else {
                implicitAnyType = firClassBuilder.getSuperTypeRefs().isEmpty() ? PsiRawFirBuilder.this.getImplicitAnyType() : FirImplicitTypeRefImplWithoutSource.INSTANCE;
            }
            if (firClassBuilder.getSuperTypeRefs().isEmpty()) {
                firClassBuilder.getSuperTypeRefs().add(PsiRawFirBuilder.this.getImplicitAnyType());
                implicitAnyType2 = PsiRawFirBuilder.this.getImplicitAnyType();
            }
            FirTypeRef firTypeRef4 = implicitAnyType2 == null ? implicitAnyType : implicitAnyType2;
            boolean z3 = ktClassOrObject.getPrimaryConstructor() != null || (!ktClassOrObject.hasSecondaryConstructors() && ((!z || classKind == ClassKind.ENUM_CLASS) && (!z2 || !((KtClass) ktClassOrObject).isInterface())));
            if (z3 || ktSuperTypeListEntry != null) {
                KtPrimaryConstructor primaryConstructor = ktClassOrObject.getPrimaryConstructor();
                KtSuperTypeCallEntry ktSuperTypeCallEntry = (KtSuperTypeCallEntry) ktSuperTypeListEntry;
                if (firTypeRef == null) {
                    Intrinsics.checkNotNull(firTypeRef4);
                    firTypeRef3 = firTypeRef4;
                } else {
                    firTypeRef3 = firTypeRef;
                }
                firClassBuilder.getDeclarations().add(toFirConstructor(primaryConstructor, ktSuperTypeCallEntry, firTypeRef4, firTypeRef3, ktClassOrObject, list, arrayList, z, true, !z3, firClassBuilder.getStatus().isActual() && (firClassBuilder.getStatus().isInline() || classKind == ClassKind.ANNOTATION_CLASS)));
            }
            Collection collectionValues = linkedHashMap.values();
            List<FirDeclaration> declarations = firClassBuilder.getDeclarations();
            Iterator it = collectionValues.iterator();
            while (it.hasNext()) {
                declarations.add((FirField) ((FirFieldSymbol) it.next()).getFir());
            }
            Intrinsics.checkNotNull(firTypeRef4);
            if (linkedHashMap.isEmpty()) {
                linkedHashMap = null;
            }
            return TuplesKt.to(firTypeRef4, linkedHashMap);
        }

        public static /* synthetic */ FirConstructor toFirConstructor$default(Visitor visitor, KtPrimaryConstructor ktPrimaryConstructor, KtSuperTypeCallEntry ktSuperTypeCallEntry, FirTypeRef firTypeRef, FirTypeRef firTypeRef2, KtClassOrObject ktClassOrObject, List list, List list2, boolean z, boolean z2, boolean z3, boolean z4, int i, Object obj) {
            if (obj == null) {
                return visitor.toFirConstructor(ktPrimaryConstructor, ktSuperTypeCallEntry, firTypeRef, firTypeRef2, ktClassOrObject, list, list2, z, z2, (i & 256) != 0 ? false : z3, (i & 512) != 0 ? false : z4);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toFirConstructor");
        }

        protected final FirConstructor toFirConstructor(KtPrimaryConstructor ktPrimaryConstructor, KtSuperTypeCallEntry ktSuperTypeCallEntry, FirTypeRef firTypeRef, FirTypeRef delegatedSelfTypeRef, KtClassOrObject owner, List<? extends FirTypeParameterRef> ownerTypeParameters, List<? extends Pair<? extends KtSuperTypeCallEntry, ? extends FirTypeRef>> allSuperTypeCallEntries, boolean z, boolean z2, boolean z3, boolean z4) throws Throwable {
            KtPsiSourceElement ktFakeSourceElement;
            FirMultiDelegatedConstructorCall firMultiDelegatedConstructorCallMo6809build;
            Visibility visibility;
            Intrinsics.checkNotNullParameter(delegatedSelfTypeRef, "delegatedSelfTypeRef");
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(ownerTypeParameters, "ownerTypeParameters");
            Intrinsics.checkNotNullParameter(allSuperTypeCallEntries, "allSuperTypeCallEntries");
            if (ktPrimaryConstructor == null || (ktFakeSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, ktPrimaryConstructor, null, 1, null)) == null) {
                KtClassOrObject ktClassOrObject = owner;
                KtFakeSourceElementKind.ImplicitConstructor implicitConstructor = KtFakeSourceElementKind.ImplicitConstructor.INSTANCE;
                if (implicitConstructor instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement = new KtRealPsiSourceElement(ktClassOrObject);
                } else {
                    if (!(implicitConstructor instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement = new KtFakeSourceElement(ktClassOrObject, implicitConstructor);
                }
            }
            KtPsiSourceElement ktPsiSourceElement = ktFakeSourceElement;
            if (allSuperTypeCallEntries.size() <= 1) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                Intrinsics.checkNotNull(firTypeRef);
                firMultiDelegatedConstructorCallMo6809build = toFirConstructor$buildDelegatedCall(psiRawFirBuilder, z, z2, this, ktPsiSourceElement, ktSuperTypeCallEntry, firTypeRef);
            } else {
                PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                FirMultiDelegatedConstructorCallBuilder firMultiDelegatedConstructorCallBuilder = new FirMultiDelegatedConstructorCallBuilder();
                List<FirDelegatedConstructorCall> delegatedConstructorCalls = firMultiDelegatedConstructorCallBuilder.getDelegatedConstructorCalls();
                Iterator<T> it = allSuperTypeCallEntries.iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    List<FirDelegatedConstructorCall> list = delegatedConstructorCalls;
                    FirDelegatedConstructorCall firConstructor$buildDelegatedCall = toFirConstructor$buildDelegatedCall(psiRawFirBuilder2, z, z2, this, ktPsiSourceElement, (KtSuperTypeCallEntry) pair.component1(), (FirTypeRef) pair.component2());
                    Intrinsics.checkNotNull(firConstructor$buildDelegatedCall);
                    list.add(firConstructor$buildDelegatedCall);
                    delegatedConstructorCalls = list;
                }
                firMultiDelegatedConstructorCallMo6809build = firMultiDelegatedConstructorCallBuilder.mo6809build();
            }
            if (ktPrimaryConstructor == null || (visibility = PsiRawFirBuilder.this.getVisibility(ktPrimaryConstructor)) == null || Intrinsics.areEqual(visibility, Visibilities.Unknown.INSTANCE)) {
                visibility = null;
            }
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility == null ? toFirConstructor$defaultVisibility(owner) : visibility, Modality.FINAL);
            firDeclarationStatusImpl.setExpect((ktPrimaryConstructor != null && PsiUtilsKt.hasExpectModifier(ktPrimaryConstructor)) || PsiRawFirBuilder.this.getContext().getContainerIsExpect());
            firDeclarationStatusImpl.setActual((ktPrimaryConstructor != null && PsiUtilsKt.hasActualModifier(ktPrimaryConstructor)) || z4);
            firDeclarationStatusImpl.setInner(!(owner.getLightParent().getLightParent() instanceof KtScript) && owner.hasModifier(KtTokens.INNER_KEYWORD));
            firDeclarationStatusImpl.setFromSealedClass(owner.hasModifier(KtTokens.SEALED_KEYWORD) && visibility != Visibilities.Private.INSTANCE);
            firDeclarationStatusImpl.setFromEnumClass(owner.hasModifier(KtTokens.ENUM_KEYWORD));
            FirAbstractConstructorBuilder firErrorConstructorBuilder = z3 ? new FirErrorConstructorBuilder() : new FirPrimaryConstructorBuilder();
            PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
            firErrorConstructorBuilder.setSource(ktPsiSourceElement);
            firErrorConstructorBuilder.setModuleData(psiRawFirBuilder3.getBaseModuleData());
            firErrorConstructorBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firErrorConstructorBuilder.setReturnTypeRef(delegatedSelfTypeRef);
            firErrorConstructorBuilder.setStatus(firDeclarationStatusImpl);
            firErrorConstructorBuilder.setDispatchReceiverType(obtainDispatchReceiverForConstructor(owner));
            firErrorConstructorBuilder.setSymbol(new FirConstructorSymbol(psiRawFirBuilder3.callableIdForClassConstructor()));
            firErrorConstructorBuilder.setDelegatedConstructor(firMultiDelegatedConstructorCallMo6809build);
            CollectionsKt.addAll(firErrorConstructorBuilder.getTypeParameters(), psiRawFirBuilder3.constructorTypeParametersFromConstructedClass(ownerTypeParameters));
            firErrorConstructorBuilder.getContextReceivers().addAll(convertContextReceivers(owner.getContextReceivers()));
            if (ktPrimaryConstructor != null) {
                extractAnnotationsTo(ktPrimaryConstructor, firErrorConstructorBuilder);
            }
            if (ktPrimaryConstructor != null) {
                extractValueParametersTo$default(this, ktPrimaryConstructor, firErrorConstructorBuilder, firErrorConstructorBuilder.getSymbol(), AbstractRawFirBuilder.ValueParameterDeclaration.PRIMARY_CONSTRUCTOR, null, null, 24, null);
            }
            firErrorConstructorBuilder.setBody(null);
            FirConstructor firConstructorMo6809build = firErrorConstructorBuilder.mo6809build();
            ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = PsiRawFirBuilder.this.currentDispatchReceiverType();
            Intrinsics.checkNotNull(coneClassLikeTypeCurrentDispatchReceiverType);
            ClassMembersKt.setContainingClassForStaticMemberAttr(firConstructorMo6809build, coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag());
            return firConstructorMo6809build;
        }

        private static final FirDelegatedConstructorCall toFirConstructor$buildDelegatedCall(PsiRawFirBuilder psiRawFirBuilder, boolean z, boolean z2, Visitor visitor, KtPsiSourceElement ktPsiSourceElement, KtSuperTypeCallEntry ktSuperTypeCallEntry, FirTypeRef firTypeRef) throws Throwable {
            Object objMo6809build;
            KtConstructorCalleeExpression calleeExpression;
            KtPsiSourceElement firSourceElement;
            KtPsiSourceElement ktPsiSourceElementFakeElement$default = null;
            KtPsiSourceElement ktPsiSourceElement2 = ktSuperTypeCallEntry != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktSuperTypeCallEntry, null, 1, null) : null;
            if (z) {
                return null;
            }
            if (z2) {
                firTypeRef = UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE);
            }
            PsiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1 psiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1 = new PsiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1(false, firTypeRef);
            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
            if (i == 1) {
                FirDelegatedConstructorCallBuilder firDelegatedConstructorCallBuilder = new FirDelegatedConstructorCallBuilder();
                firDelegatedConstructorCallBuilder.setSource(ktPsiSourceElement2 != null ? ktPsiSourceElement2 : KtSourceElementKt.fakeElement$default(ktPsiSourceElement, KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE, 0, 0, 6, null));
                firDelegatedConstructorCallBuilder.setConstructedTypeRef(firTypeRef);
                firDelegatedConstructorCallBuilder.setThis(false);
                FirExplicitSuperReferenceBuilder firExplicitSuperReferenceBuilder = new FirExplicitSuperReferenceBuilder();
                if (ktSuperTypeCallEntry == null || (calleeExpression = ktSuperTypeCallEntry.getCalleeExpression()) == null || (firSourceElement = psiRawFirBuilder.toFirSourceElement((PsiElement) calleeExpression, (KtFakeSourceElementKind) KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE)) == null) {
                    KtSourceElement source = firDelegatedConstructorCallBuilder.getSource();
                    if (source != null) {
                        ktPsiSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE, 0, 0, 6, null);
                    }
                } else {
                    ktPsiSourceElementFakeElement$default = firSourceElement;
                }
                firExplicitSuperReferenceBuilder.setSource(ktPsiSourceElementFakeElement$default);
                firExplicitSuperReferenceBuilder.setSuperTypeRef(firDelegatedConstructorCallBuilder.getConstructedTypeRef());
                firDelegatedConstructorCallBuilder.setCalleeReference(firExplicitSuperReferenceBuilder.build());
                if (ktSuperTypeCallEntry != null) {
                    visitor.extractArgumentsTo(ktSuperTypeCallEntry, firDelegatedConstructorCallBuilder);
                }
                objMo6809build = firDelegatedConstructorCallBuilder.mo6809build();
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i2 == 1) {
                    objMo6809build = psiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1.invoke();
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    objMo6809build = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1));
                }
            }
            return (FirDelegatedConstructorCall) objMo6809build;
        }

        private static final Visibility toFirConstructor$defaultVisibility(KtClassOrObject ktClassOrObject) {
            if ((ktClassOrObject instanceof KtObjectDeclaration) || ktClassOrObject.hasModifier(KtTokens.ENUM_KEYWORD) || (ktClassOrObject instanceof KtEnumEntry)) {
                return Visibilities.Private.INSTANCE;
            }
            return ktClassOrObject.hasModifier(KtTokens.SEALED_KEYWORD) ? Visibilities.Protected.INSTANCE : Visibilities.Unknown.INSTANCE;
        }

        private final ConeClassLikeType obtainDispatchReceiverForConstructor(KtClassOrObject ktClassOrObject) {
            if (ktClassOrObject.hasModifier(KtTokens.INNER_KEYWORD)) {
                return PsiRawFirBuilder.this.dispatchReceiverForInnerClassConstructor();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitKtFile(KtFile file, FirElement data) {
            FqName packageFqNameByTree;
            KtFakeSourceElement ktFakeSourceElement;
            FirFileAnnotationsContainer firFileAnnotationsContainerMo6809build;
            FirAnnotationContainer firAnnotationContainerBuildErrorTopLevelDestructuringDeclaration;
            PsiElement psiElementMo7392getNameIdentifier;
            KtFakeSourceElement ktFakeSourceElement2;
            Intrinsics.checkNotNullParameter(file, "file");
            Context<PsiElement> context = PsiRawFirBuilder.this.getContext();
            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
            if (i == 1) {
                packageFqNameByTree = file.getPackageFqNameByTree();
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                packageFqNameByTree = file.getPackageFqName();
            }
            context.setPackageFqName(packageFqNameByTree);
            final PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            final FirFileBuilder firFileBuilder = new FirFileBuilder();
            firFileBuilder.setSymbol(new FirFileSymbol());
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            firFileBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, file, null, 1, null));
            firFileBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firFileBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            String name2 = file.get$name();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            firFileBuilder.setName(name2);
            KtFile ktFile = file;
            firFileBuilder.setSourceFile(new KtPsiSourceFile(ktFile));
            firFileBuilder.setSourceFileLinesMapping(new KtPsiSourceFileLinesMapping(ktFile));
            FirPackageDirectiveBuilder firPackageDirectiveBuilder = new FirPackageDirectiveBuilder();
            firPackageDirectiveBuilder.setPackageFqName(psiRawFirBuilder.getContext().getPackageFqName());
            KtPackageDirective packageDirective = file.getPackageDirective();
            if (packageDirective != null) {
                KtPackageDirective ktPackageDirective = packageDirective;
                Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                if (obj instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement = new KtRealPsiSourceElement(ktPackageDirective);
                } else {
                    if (!(obj instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement = new KtFakeSourceElement(ktPackageDirective, (KtFakeSourceElementKind) obj);
                }
            } else {
                ktFakeSourceElement = null;
            }
            firPackageDirectiveBuilder.setSource(ktFakeSourceElement);
            firFileBuilder.setPackageDirective(firPackageDirectiveBuilder.build());
            KtFileAnnotationList fileAnnotationList = file.getFileAnnotationList();
            if (fileAnnotationList != null) {
                FirFileAnnotationsContainerBuilder firFileAnnotationsContainerBuilder = new FirFileAnnotationsContainerBuilder();
                firFileAnnotationsContainerBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
                firFileAnnotationsContainerBuilder.setContainingFileSymbol(firFileBuilder.getSymbol());
                KtFileAnnotationList ktFileAnnotationList = fileAnnotationList;
                Object obj2 = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                if (obj2 instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement2 = new KtRealPsiSourceElement(ktFileAnnotationList);
                } else {
                    if (!(obj2 instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement2 = new KtFakeSourceElement(ktFileAnnotationList, (KtFakeSourceElementKind) obj2);
                }
                firFileAnnotationsContainerBuilder.setSource(ktFakeSourceElement2);
                for (KtAnnotationEntry ktAnnotationEntry : fileAnnotationList.getAnnotationEntries()) {
                    List<FirAnnotation> annotations = firFileAnnotationsContainerBuilder.getAnnotations();
                    Intrinsics.checkNotNull(ktAnnotationEntry);
                    FirElement firElementConvertElement = convertElement(ktAnnotationEntry, null);
                    if (firElementConvertElement != null) {
                        annotations.add((FirAnnotation) firElementConvertElement);
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotation");
                    }
                }
                if (firFileAnnotationsContainerBuilder.getAnnotations().isEmpty()) {
                    firFileAnnotationsContainerBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
                }
                firFileAnnotationsContainerMo6809build = firFileAnnotationsContainerBuilder.mo6809build();
            } else {
                firFileAnnotationsContainerMo6809build = null;
            }
            firFileBuilder.setAnnotationsContainer(firFileAnnotationsContainerMo6809build);
            for (KtImportDirective ktImportDirective : file.getImportDirectives()) {
                List<FirImport> imports = firFileBuilder.getImports();
                FirImportBuilder firImportBuilder = new FirImportBuilder();
                firImportBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, ktImportDirective, null, 1, null));
                firImportBuilder.setImportedFqName(ktImportDirective.getImportedFqName());
                firImportBuilder.setAllUnder(ktImportDirective.isAllUnder());
                String aliasName = ktImportDirective.getAliasName();
                firImportBuilder.setAliasName(aliasName != null ? Name.identifier(aliasName) : null);
                KtImportAlias alias = ktImportDirective.getAlias();
                firImportBuilder.setAliasSource((alias == null || (psiElementMo7392getNameIdentifier = alias.mo7392getNameIdentifier()) == null) ? null : (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, psiElementMo7392getNameIdentifier, null, 1, null));
                imports.add(firImportBuilder.build());
            }
            if (file instanceof KtCodeFragment) {
                firFileBuilder.getDeclarations().add(convertCodeFragment((KtCodeFragment) file));
            } else {
                Iterator<KtDeclaration> it = file.getDeclarations().iterator();
                while (true) {
                    if (it.hasNext()) {
                        KtDeclaration next = it.next();
                        List<FirDeclaration> declarations = firFileBuilder.getDeclarations();
                        if (next instanceof KtScript) {
                            if ((file.getDeclarations().size() == 1 ? 1 : 0) != 0) {
                                firAnnotationContainerBuildErrorTopLevelDestructuringDeclaration = convertScript((KtScript) next, firFileBuilder.getName(), firFileBuilder.getSourceFile(), new Function1<FirScriptBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitKtFile$1$6
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Unit invoke(FirScriptBuilder firScriptBuilder) {
                                        invoke2(firScriptBuilder);
                                        return Unit.INSTANCE;
                                    }

                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2(FirScriptBuilder convertScript) {
                                        Intrinsics.checkNotNullParameter(convertScript, "$this$convertScript");
                                        Iterator<FirScriptConfiguratorExtension> it2 = FirScriptConfiguratorExtensionKt.getScriptConfigurators(FirExtensionServiceKt.getExtensionService(psiRawFirBuilder.getBaseSession())).iterator();
                                        while (it2.hasNext()) {
                                            it2.next().configureContainingFile(convertScript, firFileBuilder);
                                        }
                                    }
                                });
                            } else {
                                KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Expect the script to be the only declaration in the file");
                                ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                                exceptionAttachmentBuilder.withEntry(PsiTreeChangeEvent.PROP_FILE_NAME, file.get$name());
                                kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                                throw kotlinIllegalArgumentExceptionWithAttachments;
                            }
                        } else if (next instanceof KtDestructuringDeclaration) {
                            firAnnotationContainerBuildErrorTopLevelDestructuringDeclaration = psiRawFirBuilder.buildErrorTopLevelDestructuringDeclaration(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, next, null, 1, null));
                        } else {
                            FirElement firElementConvertElement2 = convertElement(next, null);
                            if (firElementConvertElement2 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclaration");
                            }
                            firAnnotationContainerBuildErrorTopLevelDestructuringDeclaration = (FirDeclaration) firElementConvertElement2;
                        }
                        declarations.add((FirDeclaration) firAnnotationContainerBuildErrorTopLevelDestructuringDeclaration);
                    } else {
                        for (KtModifierList ktModifierList : file.getDanglingModifierLists()) {
                            firFileBuilder.getDeclarations().add(buildErrorTopLevelDeclarationForDanglingModifierList(ktModifierList));
                        }
                    }
                }
            }
            return firFileBuilder.mo6809build();
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ FirScript convertScript$default(Visitor visitor, KtScript ktScript, String str, KtSourceFile ktSourceFile, Function1 function1, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: convertScript");
            }
            if ((i & 8) != 0) {
                function1 = new Function1<FirScriptBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$convertScript$1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirScriptBuilder firScriptBuilder) {
                        Intrinsics.checkNotNullParameter(firScriptBuilder, "$this$null");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirScriptBuilder firScriptBuilder) {
                        invoke2(firScriptBuilder);
                        return Unit.INSTANCE;
                    }
                };
            }
            return visitor.convertScript(ktScript, str, ktSourceFile, function1);
        }

        private final FirScript convertScript(KtScript script, String fileName, KtSourceFile sourceFile, Function1<? super FirScriptBuilder, Unit> setup) {
            FirErrorExpression firErrorExpressionMo6809build;
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirScriptBuilder firScriptBuilder = new FirScriptBuilder();
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            firScriptBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, script, null, 1, null));
            firScriptBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firScriptBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            Name nameSpecial = Name.special("<script-" + fileName + '>');
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            firScriptBuilder.setName(nameSpecial);
            FqName fqNameChild = psiRawFirBuilder.getContext().getPackageFqName().child(firScriptBuilder.getName());
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            firScriptBuilder.setSymbol(new FirScriptSymbol(fqNameChild));
            for (KtDeclaration ktDeclaration : script.getDeclarations()) {
                if (ktDeclaration instanceof KtScriptInitializer) {
                    KtExpression body = ((KtScriptInitializer) ktDeclaration).getBody();
                    if (body != null) {
                        firScriptBuilder.getStatements().add(toFirStatement(body));
                    }
                } else if (ktDeclaration instanceof KtDestructuringDeclaration) {
                    FirModuleData baseModuleData = psiRawFirBuilder.getBaseModuleData();
                    Intrinsics.checkNotNull(ktDeclaration);
                    KtSourceElement firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, ktDeclaration, null, 1, null);
                    KtDestructuringDeclaration ktDestructuringDeclaration = (KtDestructuringDeclaration) ktDeclaration;
                    KtExpression initializer = ktDestructuringDeclaration.getInitializer();
                    if (initializer == null) {
                        firErrorExpressionMo6809build = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("Initializer required for destructuring declaration"), null, 4, null);
                    } else {
                        FirElement firElementConvertElement = convertElement(initializer, null);
                        if (firElementConvertElement instanceof FirExpression) {
                            firErrorExpressionMo6809build = toFirExpression(initializer, (FirExpression) firElementConvertElement);
                        } else {
                            PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
                            FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                            firErrorExpressionBuilder.setNonExpressionElement(firElementConvertElement);
                            firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Initializer required for destructuring declaration"));
                            firErrorExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder3, initializer, null, 1, null));
                            firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
                        }
                    }
                    FirVariable firVariableGenerateTemporaryVariable = PsiConversionUtilsKt.generateTemporaryVariable(baseModuleData, firSourceElement$default, "destruct", firErrorExpressionMo6809build, new Function2<KtAnnotated, FirAnnotationContainerBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$convertScript$2$destructuringContainerVar$2
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(KtAnnotated ktAnnotated, FirAnnotationContainerBuilder firAnnotationContainerBuilder) {
                            invoke2(ktAnnotated, firAnnotationContainerBuilder);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(KtAnnotated generateTemporaryVariable, FirAnnotationContainerBuilder it) {
                            Intrinsics.checkNotNullParameter(generateTemporaryVariable, "$this$generateTemporaryVariable");
                            Intrinsics.checkNotNullParameter(it, "it");
                            this.this$0.extractAnnotationsTo(generateTemporaryVariable, it);
                        }
                    });
                    DestructuringDeclarationAttributesKt.setDestructuringDeclarationContainerVariable(firVariableGenerateTemporaryVariable, (Boolean) true);
                    FirBlock firBlockGenerateDestructuringBlock = PsiConversionUtilsKt.generateDestructuringBlock(psiRawFirBuilder.getBaseModuleData(), ktDestructuringDeclaration, firVariableGenerateTemporaryVariable, false, false, new Function2<KtAnnotated, FirAnnotationContainerBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$convertScript$2$destructuringBlock$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(KtAnnotated ktAnnotated, FirAnnotationContainerBuilder firAnnotationContainerBuilder) {
                            invoke2(ktAnnotated, firAnnotationContainerBuilder);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(KtAnnotated generateDestructuringBlock, FirAnnotationContainerBuilder it) {
                            Intrinsics.checkNotNullParameter(generateDestructuringBlock, "$this$generateDestructuringBlock");
                            Intrinsics.checkNotNullParameter(it, "it");
                            this.this$0.extractAnnotationsTo(generateDestructuringBlock, it);
                        }
                    }, new Function1<KtTypeReference, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$convertScript$2$destructuringBlock$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final FirTypeRef invoke(KtTypeReference ktTypeReference) {
                            return this.this$0.toFirOrImplicitType(ktTypeReference);
                        }
                    });
                    for (FirStatement firStatement : firBlockGenerateDestructuringBlock.getStatements()) {
                        Intrinsics.checkNotNull(firStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirProperty");
                        DestructuringDeclarationAttributesKt.setDestructuringDeclarationContainerVariable((FirProperty) firStatement, firVariableGenerateTemporaryVariable.getSymbol());
                    }
                    firScriptBuilder.getStatements().add(firVariableGenerateTemporaryVariable);
                    firScriptBuilder.getStatements().addAll(firBlockGenerateDestructuringBlock.getStatements());
                } else {
                    List<FirStatement> statements = firScriptBuilder.getStatements();
                    Intrinsics.checkNotNull(ktDeclaration);
                    statements.add(toFirStatement(ktDeclaration));
                }
            }
            setup.invoke(firScriptBuilder);
            if (sourceFile != null) {
                Iterator<FirScriptConfiguratorExtension> it = FirScriptConfiguratorExtensionKt.getScriptConfigurators(FirExtensionServiceKt.getExtensionService(psiRawFirBuilder.getBaseSession())).iterator();
                while (it.hasNext()) {
                    it.next().configure(firScriptBuilder, sourceFile);
                }
            }
            return firScriptBuilder.mo6809build();
        }

        private final FirCodeFragment convertCodeFragment(KtCodeFragment file) throws Throwable {
            Object objMo6809build;
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirCodeFragmentBuilder firCodeFragmentBuilder = new FirCodeFragmentBuilder();
            firCodeFragmentBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, file, null, 1, null));
            firCodeFragmentBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firCodeFragmentBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firCodeFragmentBuilder.setSymbol(new FirCodeFragmentSymbol());
            PsiRawFirBuilder$Visitor$buildOrLazyBlock$1 psiRawFirBuilder$Visitor$buildOrLazyBlock$1 = PsiRawFirBuilder$Visitor$buildOrLazyBlock$1.INSTANCE;
            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
            if (i != 1) {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i2 == 1) {
                    objMo6809build = psiRawFirBuilder$Visitor$buildOrLazyBlock$1.invoke();
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    objMo6809build = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyBlock$1));
                }
            } else if (file instanceof KtExpressionCodeFragment) {
                KtExpression contentElement = ((KtExpressionCodeFragment) file).getContentElement();
                if (contentElement == null || (objMo6809build = toFirBlock(contentElement)) == null) {
                    objMo6809build = FirEmptyExpressionBlockBuilderKt.buildEmptyExpressionBlock();
                }
            } else {
                if (!(file instanceof KtBlockCodeFragment)) {
                    throw new IllegalStateException(("Unexpected code fragment type: " + Reflection.getOrCreateKotlinClass(file.getClass())).toString());
                }
                objMo6809build = configureBlockWithoutBuilding(((KtBlockCodeFragment) file).getContentElement()).mo6809build();
            }
            firCodeFragmentBuilder.setBlock((FirBlock) objMo6809build);
            return firCodeFragmentBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitScript(KtScript script, FirElement data) {
            PsiElement psi;
            Intrinsics.checkNotNullParameter(script, "script");
            KtFile containingKtFile = script.getContainingKtFile();
            Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
            String name2 = containingKtFile.get$name();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            FirScript firScript = data instanceof FirScript ? (FirScript) data : null;
            PsiFile containingFile = (firScript == null || (psi = UtilsKt.getPsi(firScript)) == null) ? null : psi.getContainingFile();
            KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
            if (ktFile != null) {
                containingKtFile = ktFile;
            }
            return convertScript$default(this, script, name2, new KtPsiSourceFile(containingKtFile), null, 8, null);
        }

        /* JADX WARN: Code duplicated, block: B:111:0x053e  */
        /* JADX WARN: Code duplicated, block: B:113:0x054c  */
        /* JADX WARN: Code duplicated, block: B:116:0x0588  */
        /* JADX WARN: Code duplicated, block: B:59:0x0245  */
        /* JADX WARN: Code duplicated, block: B:66:0x0316 A[Catch: all -> 0x0483, LOOP:1: B:64:0x0310->B:66:0x0316, LOOP_END, TryCatch #4 {all -> 0x0483, blocks: (B:63:0x0308, B:64:0x0310, B:66:0x0316, B:67:0x033f), top: B:126:0x0308, outer: #7 }] */
        /* JADX WARN: Code duplicated, block: B:70:0x0351 A[Catch: all -> 0x0511, TryCatch #2 {all -> 0x0511, blocks: (B:62:0x02b6, B:68:0x0341, B:70:0x0351, B:72:0x035f, B:73:0x0376), top: B:122:0x02b6 }] */
        /* JADX WARN: Code duplicated, block: B:72:0x035f A[Catch: all -> 0x0511, TryCatch #2 {all -> 0x0511, blocks: (B:62:0x02b6, B:68:0x0341, B:70:0x0351, B:72:0x035f, B:73:0x0376), top: B:122:0x02b6 }] */
        /* JADX WARN: Code duplicated, block: B:76:0x03b9  */
        /* JADX WARN: Code duplicated, block: B:78:0x03c7  */
        /* JADX WARN: Code duplicated, block: B:83:0x042b  */
        /* JADX WARN: Code duplicated, block: B:85:0x0452  */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v29 */
        /* JADX WARN: Type inference failed for: r2v41 */
        /* JADX WARN: Type inference failed for: r2v46 */
        /* JADX WARN: Type inference failed for: r2v49 */
        /* JADX WARN: Type inference failed for: r2v50 */
        /* JADX WARN: Type inference failed for: r2v52 */
        /* JADX WARN: Type inference failed for: r2v6 */
        /* JADX WARN: Type inference failed for: r2v7 */
        /* JADX WARN: Type inference failed for: r2v75 */
        /* JADX WARN: Type inference failed for: r2v76 */
        /* JADX WARN: Type inference failed for: r2v8, types: [int] */
        /* JADX WARN: Type inference failed for: r3v0 */
        /* JADX WARN: Type inference failed for: r3v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r3v11 */
        /* JADX WARN: Type inference failed for: r3v12 */
        /* JADX WARN: Type inference failed for: r3v15, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r3v16 */
        /* JADX WARN: Type inference failed for: r3v18 */
        /* JADX WARN: Type inference failed for: r3v26 */
        /* JADX WARN: Type inference failed for: r3v27 */
        /* JADX WARN: Type inference failed for: r4v29 */
        /* JADX WARN: Type inference failed for: r4v30, types: [boolean] */
        /* JADX WARN: Type inference failed for: r4v36 */
        /* JADX WARN: Type inference failed for: r4v37, types: [org.jetbrains.kotlin.fir.types.FirTypeRef] */
        /* JADX WARN: Type inference failed for: r4v38 */
        /* JADX WARN: Type inference failed for: r4v39 */
        /* JADX WARN: Type inference failed for: r4v41 */
        /* JADX WARN: Type inference failed for: r4v50 */
        /* JADX WARN: Type inference failed for: r4v51 */
        /* JADX WARN: Type inference failed for: r5v18 */
        /* JADX WARN: Type inference failed for: r5v21, types: [org.jetbrains.kotlin.fir.types.FirTypeRef] */
        /* JADX WARN: Type inference failed for: r5v22 */
        /* JADX WARN: Type inference failed for: r5v23 */
        /* JADX WARN: Type inference failed for: r5v25 */
        /* JADX WARN: Type inference failed for: r5v30 */
        /* JADX WARN: Type inference failed for: r5v31 */
        /* JADX WARN: Type inference failed for: r5v5 */
        /* JADX WARN: Type inference failed for: r5v6, types: [java.lang.String] */
        protected final FirDeclaration toFirEnumEntry(KtEnumEntry ktEnumEntry, FirResolvedTypeRef delegatedEnumSelfTypeRef, boolean z) throws Throwable {
            ?? r2;
            ?? r4;
            ?? r3;
            PsiRawFirBuilder psiRawFirBuilder;
            ?? r5;
            String str;
            Object next;
            boolean z2;
            boolean z3;
            KtTypeReference typeReference;
            boolean z4;
            KtPsiSourceElement ktPsiSourceElement;
            FirResolvedTypeRef firResolvedTypeRefMo6809build;
            boolean z5;
            boolean z6;
            PsiRawFirBuilder psiRawFirBuilder2;
            boolean forcedLocalContext;
            boolean containerIsExpect;
            int size;
            FirAnonymousObjectExpression firAnonymousObjectExpressionMo6809build;
            Object obj;
            FirEnumEntryBuilder firEnumEntryBuilder;
            Object objInvoke;
            Intrinsics.checkNotNullParameter(ktEnumEntry, "<this>");
            Intrinsics.checkNotNullParameter(delegatedEnumSelfTypeRef, "delegatedEnumSelfTypeRef");
            boolean z7 = PsiUtilsKt.hasExpectModifier(ktEnumEntry) || PsiRawFirBuilder.this.getContext().getContainerIsExpect();
            PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
            FirEnumEntryBuilder firEnumEntryBuilder2 = new FirEnumEntryBuilder();
            PsiRawFirBuilder psiRawFirBuilder4 = psiRawFirBuilder3;
            firEnumEntryBuilder2.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder4, ktEnumEntry, null, 1, null));
            firEnumEntryBuilder2.setModuleData(psiRawFirBuilder3.getBaseModuleData());
            firEnumEntryBuilder2.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firEnumEntryBuilder2.setReturnTypeRef(delegatedEnumSelfTypeRef);
            Name nameAsSafeName = ktEnumEntry.getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
            firEnumEntryBuilder2.setName(nameAsSafeName);
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL);
            firDeclarationStatusImpl.setStatic(true);
            firDeclarationStatusImpl.setExpect(z7);
            firEnumEntryBuilder2.setStatus(firDeclarationStatusImpl);
            Name nameAsSafeName2 = ktEnumEntry.getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName2, "getNameAsSafeName(...)");
            firEnumEntryBuilder2.setSymbol(new FirEnumEntrySymbol(psiRawFirBuilder3.callableIdForName(nameAsSafeName2)));
            if (z && ktEnumEntry.getInitializerList() == null && ktEnumEntry.getAnnotationEntries().isEmpty() && ktEnumEntry.getBody() == null) {
                firEnumEntryBuilder = firEnumEntryBuilder2;
            } else {
                extractAnnotationsTo(ktEnumEntry, firEnumEntryBuilder2);
                PsiRawFirBuilder$Visitor$buildOrLazyExpression$1 psiRawFirBuilder$Visitor$buildOrLazyExpression$1 = new PsiRawFirBuilder$Visitor$buildOrLazyExpression$1(psiRawFirBuilder3.toFirSourceElement((PsiElement) ktEnumEntry, (KtFakeSourceElementKind) KtFakeSourceElementKind.EnumInitializer.INSTANCE));
                int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i == 1) {
                    Name nameAsSafeName3 = ktEnumEntry.getNameAsSafeName();
                    Intrinsics.checkNotNullExpressionValue(nameAsSafeName3, "getNameAsSafeName(...)");
                    Context<PsiElement> context = psiRawFirBuilder4.getContext();
                    FqName fqNameChild = psiRawFirBuilder4.getContext().getClassName().child(nameAsSafeName3);
                    Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
                    context.setClassName(fqNameChild);
                    boolean forcedLocalContext2 = psiRawFirBuilder4.getContext().getForcedLocalContext();
                    psiRawFirBuilder4.getContext().setForcedLocalContext(psiRawFirBuilder4.getContext().getForcedLocalContext());
                    boolean containerIsExpect2 = psiRawFirBuilder4.getContext().getContainerIsExpect();
                    psiRawFirBuilder4.getContext().setContainerIsExpect(containerIsExpect2);
                    int size2 = psiRawFirBuilder4.getContext().getDispatchReceiverTypesStack().size();
                    try {
                        FirAnonymousObjectExpressionBuilder firAnonymousObjectExpressionBuilder = new FirAnonymousObjectExpressionBuilder();
                        KtPsiSourceElement firSourceElement = psiRawFirBuilder3.toFirSourceElement((PsiElement) ktEnumEntry, (KtFakeSourceElementKind) KtFakeSourceElementKind.EnumInitializer.INSTANCE);
                        firAnonymousObjectExpressionBuilder.setSource(firSourceElement);
                        FirAnonymousObjectBuilder firAnonymousObjectBuilder = new FirAnonymousObjectBuilder();
                        firAnonymousObjectBuilder.setSource(firSourceElement);
                        firAnonymousObjectBuilder.setModuleData(psiRawFirBuilder3.getBaseModuleData());
                        firAnonymousObjectBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                        firAnonymousObjectBuilder.setClassKind(ClassKind.ENUM_ENTRY);
                        firAnonymousObjectBuilder.setScopeProvider(psiRawFirBuilder3.getBaseScopeProvider());
                        firAnonymousObjectBuilder.setSymbol(new FirAnonymousObjectSymbol());
                        try {
                            firAnonymousObjectBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
                            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                            firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(firAnonymousObjectBuilder.getSymbol().getLookupTag(), new ConeTypeProjection[0], false, null, 8, null));
                            FirResolvedTypeRef firResolvedTypeRefMo6809build2 = firResolvedTypeRefBuilder.mo6809build();
                            psiRawFirBuilder3.registerSelfType(firResolvedTypeRefMo6809build2);
                            firAnonymousObjectBuilder.getSuperTypeRefs().add(delegatedEnumSelfTypeRef);
                            List<KtSuperTypeListEntry> superTypeListEntries = ktEnumEntry.getSuperTypeListEntries();
                            Intrinsics.checkNotNullExpressionValue(superTypeListEntries, "getSuperTypeListEntries(...)");
                            Iterator it = superTypeListEntries.iterator();
                            do {
                                if (!it.hasNext()) {
                                    next = null;
                                    break;
                                }
                                next = it.next();
                            } while (!(next instanceof KtSuperTypeCallEntry));
                            KtSuperTypeCallEntry ktSuperTypeCallEntry = (KtSuperTypeCallEntry) next;
                            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
                            if (ktSuperTypeCallEntry == null) {
                                z2 = containerIsExpect2;
                                z3 = forcedLocalContext2;
                                z4 = true;
                                ktPsiSourceElement = null;
                                firResolvedTypeRefBuilder2.setSource(ktPsiSourceElement);
                                firResolvedTypeRefBuilder2.setType(delegatedEnumSelfTypeRef.getType());
                                firResolvedTypeRefMo6809build = firResolvedTypeRefBuilder2.mo6809build();
                                FirResolvedTypeRef firResolvedTypeRef = firResolvedTypeRefMo6809build2;
                                r4 = firResolvedTypeRefMo6809build;
                                z5 = z2;
                                r5 = firResolvedTypeRef;
                                z6 = z3;
                                psiRawFirBuilder = psiRawFirBuilder4;
                                str = "parent(...)";
                                firAnonymousObjectBuilder.getDeclarations().add(toFirConstructor$default(this, ktEnumEntry.getPrimaryConstructor(), ktSuperTypeCallEntry, r4, r5, ktEnumEntry, firAnonymousObjectBuilder.getTypeParameters(), CollectionsKt.emptyList(), z7, true, false, false, 768, null));
                                Name name2 = SpecialNames.ANONYMOUS;
                                psiRawFirBuilder2 = psiRawFirBuilder3;
                                Context<PsiElement> context2 = psiRawFirBuilder2.getContext();
                                FqName fqNameChild2 = psiRawFirBuilder2.getContext().getClassName().child(name2);
                                r3 = "child(...)";
                                Intrinsics.checkNotNullExpressionValue(fqNameChild2, r3);
                                context2.setClassName(fqNameChild2);
                                forcedLocalContext = psiRawFirBuilder2.getContext().getForcedLocalContext();
                                r2 = 1;
                                r2 = 1;
                                psiRawFirBuilder2.getContext().setForcedLocalContext(true);
                                containerIsExpect = psiRawFirBuilder2.getContext().getContainerIsExpect();
                                psiRawFirBuilder2.getContext().setContainerIsExpect(containerIsExpect);
                                size = psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size();
                                r3 = r3;
                                r4 = r4;
                                r5 = r5;
                                for (KtDeclaration ktDeclaration : ktEnumEntry.getDeclarations()) {
                                    FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRefMo6809build;
                                    KtEnumEntry ktEnumEntry2 = ktEnumEntry;
                                    FirResolvedTypeRef firResolvedTypeRef3 = firResolvedTypeRefMo6809build2;
                                    firAnonymousObjectBuilder.getDeclarations().add(toFirDeclaration(ktDeclaration, firResolvedTypeRef2, firResolvedTypeRef3, ktEnumEntry2, firAnonymousObjectBuilder, CollectionsKt.emptyList()));
                                    r2 = ktDeclaration;
                                    r3 = firResolvedTypeRef2;
                                    r4 = firResolvedTypeRef3;
                                    r5 = ktEnumEntry2;
                                }
                                Unit unit = Unit.INSTANCE;
                                if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() <= size + 1) {
                                    throw new IllegalArgumentException(("Wrong number of " + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                                }
                                if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > size) {
                                    psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                                }
                                Context<PsiElement> context3 = psiRawFirBuilder2.getContext();
                                FqName fqNameParent = psiRawFirBuilder2.getContext().getClassName().parent();
                                Intrinsics.checkNotNullExpressionValue(fqNameParent, str);
                                context3.setClassName(fqNameParent);
                                psiRawFirBuilder2.getContext().setForcedLocalContext(forcedLocalContext);
                                psiRawFirBuilder2.getContext().setContainerIsExpect(containerIsExpect);
                                firAnonymousObjectExpressionBuilder.setAnonymousObject(firAnonymousObjectBuilder.mo6809build());
                                firAnonymousObjectExpressionMo6809build = firAnonymousObjectExpressionBuilder.mo6809build();
                                if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() <= size2 + 1) {
                                    throw new IllegalArgumentException(("Wrong number of " + psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                                }
                                if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size2) {
                                    psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
                                }
                                Context<PsiElement> context4 = psiRawFirBuilder.getContext();
                                FqName fqNameParent2 = psiRawFirBuilder.getContext().getClassName().parent();
                                Intrinsics.checkNotNullExpressionValue(fqNameParent2, str);
                                context4.setClassName(fqNameParent2);
                                psiRawFirBuilder.getContext().setForcedLocalContext(z6);
                                psiRawFirBuilder.getContext().setContainerIsExpect(z5);
                                obj = firAnonymousObjectExpressionMo6809build;
                                if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() <= r2 + 1) {
                                    throw new IllegalArgumentException((((String) r5) + psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                                }
                                if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > r2) {
                                    psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
                                }
                                Context<PsiElement> context5 = psiRawFirBuilder.getContext();
                                FqName fqNameParent3 = psiRawFirBuilder.getContext().getClassName().parent();
                                Intrinsics.checkNotNullExpressionValue(fqNameParent3, str);
                                context5.setClassName(fqNameParent3);
                                psiRawFirBuilder.getContext().setForcedLocalContext(r3);
                                psiRawFirBuilder.getContext().setContainerIsExpect(r4);
                                throw th;
                            }
                            try {
                                KtConstructorCalleeExpression calleeExpression = ktSuperTypeCallEntry.getCalleeExpression();
                                if (calleeExpression == null || (typeReference = calleeExpression.getTypeReference()) == null) {
                                    z2 = containerIsExpect2;
                                    z3 = forcedLocalContext2;
                                    z4 = true;
                                    ktPsiSourceElement = null;
                                } else {
                                    z2 = containerIsExpect2;
                                    try {
                                        PsiRawFirBuilder psiRawFirBuilder5 = psiRawFirBuilder3;
                                        z3 = forcedLocalContext2;
                                        z4 = true;
                                        try {
                                            ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder5, typeReference, null, 1, null);
                                        } catch (Throwable th) {
                                            th = th;
                                            r2 = size2;
                                            r5 = "Wrong number of ";
                                            str = "parent(...)";
                                            r4 = z2;
                                            r3 = z3;
                                            psiRawFirBuilder = psiRawFirBuilder4;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        z3 = forcedLocalContext2;
                                        r2 = size2;
                                        r5 = "Wrong number of ";
                                        str = "parent(...)";
                                        r4 = z2;
                                        r3 = z3;
                                        psiRawFirBuilder = psiRawFirBuilder4;
                                    }
                                }
                                firResolvedTypeRefBuilder2.setSource(ktPsiSourceElement);
                                firResolvedTypeRefBuilder2.setType(delegatedEnumSelfTypeRef.getType());
                                firResolvedTypeRefMo6809build = firResolvedTypeRefBuilder2.mo6809build();
                                FirResolvedTypeRef firResolvedTypeRef4 = firResolvedTypeRefMo6809build2;
                                r4 = firResolvedTypeRefMo6809build;
                                z5 = z2;
                                r5 = firResolvedTypeRef4;
                                z6 = z3;
                                psiRawFirBuilder = psiRawFirBuilder4;
                                str = "parent(...)";
                                try {
                                    firAnonymousObjectBuilder.getDeclarations().add(toFirConstructor$default(this, ktEnumEntry.getPrimaryConstructor(), ktSuperTypeCallEntry, r4, r5, ktEnumEntry, firAnonymousObjectBuilder.getTypeParameters(), CollectionsKt.emptyList(), z7, true, false, false, 768, null));
                                    Name name3 = SpecialNames.ANONYMOUS;
                                    psiRawFirBuilder2 = psiRawFirBuilder3;
                                    Context<PsiElement> context6 = psiRawFirBuilder2.getContext();
                                    FqName fqNameChild3 = psiRawFirBuilder2.getContext().getClassName().child(name3);
                                    r3 = "child(...)";
                                    Intrinsics.checkNotNullExpressionValue(fqNameChild3, r3);
                                    context6.setClassName(fqNameChild3);
                                    forcedLocalContext = psiRawFirBuilder2.getContext().getForcedLocalContext();
                                    r2 = 1;
                                    r2 = 1;
                                    psiRawFirBuilder2.getContext().setForcedLocalContext(true);
                                    containerIsExpect = psiRawFirBuilder2.getContext().getContainerIsExpect();
                                    psiRawFirBuilder2.getContext().setContainerIsExpect(containerIsExpect);
                                    size = psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size();
                                    try {
                                        try {
                                            r3 = r3;
                                            r4 = r4;
                                            r5 = r5;
                                            while (r12.hasNext()) {
                                                FirResolvedTypeRef firResolvedTypeRef5 = firResolvedTypeRefMo6809build;
                                                KtEnumEntry ktEnumEntry3 = ktEnumEntry;
                                                FirResolvedTypeRef firResolvedTypeRef6 = firResolvedTypeRefMo6809build2;
                                                firAnonymousObjectBuilder.getDeclarations().add(toFirDeclaration(ktDeclaration, firResolvedTypeRef5, firResolvedTypeRef6, ktEnumEntry3, firAnonymousObjectBuilder, CollectionsKt.emptyList()));
                                                r2 = ktDeclaration;
                                                r3 = firResolvedTypeRef5;
                                                r4 = firResolvedTypeRef6;
                                                r5 = ktEnumEntry3;
                                            }
                                            Unit unit2 = Unit.INSTANCE;
                                            if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() <= size + 1) {
                                                throw new IllegalArgumentException(("Wrong number of " + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                                            }
                                            if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > size) {
                                                psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                                            }
                                            Context<PsiElement> context7 = psiRawFirBuilder2.getContext();
                                            FqName fqNameParent4 = psiRawFirBuilder2.getContext().getClassName().parent();
                                            Intrinsics.checkNotNullExpressionValue(fqNameParent4, str);
                                            context7.setClassName(fqNameParent4);
                                            psiRawFirBuilder2.getContext().setForcedLocalContext(forcedLocalContext);
                                            psiRawFirBuilder2.getContext().setContainerIsExpect(containerIsExpect);
                                            firAnonymousObjectExpressionBuilder.setAnonymousObject(firAnonymousObjectBuilder.mo6809build());
                                            firAnonymousObjectExpressionMo6809build = firAnonymousObjectExpressionBuilder.mo6809build();
                                            if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() <= size2 + 1) {
                                                throw new IllegalArgumentException(("Wrong number of " + psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                                            }
                                            if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size2) {
                                                psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
                                            }
                                            Context<PsiElement> context8 = psiRawFirBuilder.getContext();
                                            FqName fqNameParent5 = psiRawFirBuilder.getContext().getClassName().parent();
                                            Intrinsics.checkNotNullExpressionValue(fqNameParent5, str);
                                            context8.setClassName(fqNameParent5);
                                            psiRawFirBuilder.getContext().setForcedLocalContext(z6);
                                            psiRawFirBuilder.getContext().setContainerIsExpect(z5);
                                            obj = firAnonymousObjectExpressionMo6809build;
                                        } catch (Throwable th3) {
                                            if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                                                throw new IllegalArgumentException(("Wrong number of " + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                                            }
                                            if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > size) {
                                                psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                                            }
                                            Context<PsiElement> context9 = psiRawFirBuilder2.getContext();
                                            FqName fqNameParent6 = psiRawFirBuilder2.getContext().getClassName().parent();
                                            Intrinsics.checkNotNullExpressionValue(fqNameParent6, str);
                                            context9.setClassName(fqNameParent6);
                                            psiRawFirBuilder2.getContext().setForcedLocalContext(forcedLocalContext);
                                            psiRawFirBuilder2.getContext().setContainerIsExpect(containerIsExpect);
                                            throw th3;
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                    r2 = size2;
                                    r4 = z5;
                                    r3 = z6;
                                    r5 = "Wrong number of ";
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                z2 = containerIsExpect2;
                            }
                            if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() <= r2 + 1) {
                                throw new IllegalArgumentException((((String) r5) + psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                            }
                            if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > r2) {
                                psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
                            }
                            Context<PsiElement> context10 = psiRawFirBuilder.getContext();
                            FqName fqNameParent7 = psiRawFirBuilder.getContext().getClassName().parent();
                            Intrinsics.checkNotNullExpressionValue(fqNameParent7, str);
                            context10.setClassName(fqNameParent7);
                            psiRawFirBuilder.getContext().setForcedLocalContext(r3);
                            psiRawFirBuilder.getContext().setContainerIsExpect(r4);
                            throw th;
                        } catch (Throwable th7) {
                            th = th7;
                            r2 = size2;
                            r4 = containerIsExpect2;
                            r3 = forcedLocalContext2;
                            psiRawFirBuilder = psiRawFirBuilder4;
                            r5 = "Wrong number of ";
                            str = "parent(...)";
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        r2 = size2;
                    }
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                    if (i2 == 1) {
                        objInvoke = psiRawFirBuilder$Visitor$buildOrLazyExpression$1.invoke();
                    } else {
                        if (i2 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        objInvoke = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyExpression$1));
                    }
                    obj = objInvoke;
                }
                firEnumEntryBuilder = firEnumEntryBuilder2;
                firEnumEntryBuilder.setInitializer((FirExpression) obj);
            }
            FirEnumEntry firEnumEntryMo6809build = firEnumEntryBuilder.mo6809build();
            ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = PsiRawFirBuilder.this.currentDispatchReceiverType();
            Intrinsics.checkNotNull(coneClassLikeTypeCurrentDispatchReceiverType);
            ClassMembersKt.setContainingClassForStaticMemberAttr(firEnumEntryMo6809build, coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag());
            return firEnumEntryMo6809build;
        }

        private final List<FirContextReceiver> convertContextReceivers(List<KtContextReceiver> receivers) {
            String strNameForReceiverLabel;
            List<KtContextReceiver> list = receivers;
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (KtContextReceiver ktContextReceiver : list) {
                FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
                firContextReceiverBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktContextReceiver, null, 1, null));
                firContextReceiverBuilder.setCustomLabelName(ktContextReceiver.labelNameAsName());
                KtTypeReference ktTypeReferenceTypeReference = ktContextReceiver.typeReference();
                firContextReceiverBuilder.setLabelNameFromTypeRef((ktTypeReferenceTypeReference == null || (strNameForReceiverLabel = ktTypeReferenceTypeReference.nameForReceiverLabel()) == null) ? null : Name.identifier(strNameForReceiverLabel));
                KtTypeReference ktTypeReferenceTypeReference2 = ktContextReceiver.typeReference();
                FirElement firElementConvertElement = ktTypeReferenceTypeReference2 != null ? convertElement(ktTypeReferenceTypeReference2, null) : null;
                FirTypeRef firTypeRef = (FirTypeRef) (firElementConvertElement instanceof FirTypeRef ? firElementConvertElement : null);
                if (firTypeRef != null) {
                    firContextReceiverBuilder.setTypeRef(firTypeRef);
                }
                arrayList.add(firContextReceiverBuilder.build());
            }
            return arrayList;
        }

        /* JADX WARN: Code duplicated, block: B:197:0x0593  */
        /* JADX WARN: Code duplicated, block: B:199:0x05a1  */
        /* JADX WARN: Code duplicated, block: B:202:0x05dd  */
        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitClassOrObject(KtClassOrObject classOrObject, FirElement data) throws Throwable {
            boolean z;
            int i;
            boolean z2;
            String str;
            String str2;
            ClassKind classKind;
            String str3;
            PsiRawFirBuilder psiRawFirBuilder;
            Object next;
            List<KtModifierList> listEmptyList;
            FirRegularClassBuilder firRegularClassBuilder;
            String str4;
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            boolean z3 = classOrObject.isLocal() || PsiTreeUtil.getParentOfType(classOrObject, KtEnumEntry.class, true) != null;
            boolean z4 = PsiUtilsKt.hasExpectModifier(classOrObject) || PsiRawFirBuilder.this.getContext().getContainerIsExpect();
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, classOrObject, null, 1, null);
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            Name nameAsSafeName = classOrObject.getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
            final PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
            Context<PsiElement> context = psiRawFirBuilder2.getContext();
            FqName fqNameChild = psiRawFirBuilder2.getContext().getClassName().child(nameAsSafeName);
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            context.setClassName(fqNameChild);
            boolean forcedLocalContext = psiRawFirBuilder2.getContext().getForcedLocalContext();
            psiRawFirBuilder2.getContext().setForcedLocalContext(z3 || psiRawFirBuilder2.getContext().getForcedLocalContext());
            boolean containerIsExpect = psiRawFirBuilder2.getContext().getContainerIsExpect();
            psiRawFirBuilder2.getContext().setContainerIsExpect(containerIsExpect || z4);
            int size = psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size();
            try {
                if (classOrObject instanceof KtObjectDeclaration) {
                    classKind = ClassKind.OBJECT;
                } else {
                    if (!(classOrObject instanceof KtClass)) {
                        z = containerIsExpect;
                        i = size;
                        z2 = forcedLocalContext;
                        str = "Wrong number of ";
                        str2 = "parent(...)";
                        try {
                            throw new AssertionError("Unexpected class or object: " + classOrObject.getText());
                        } catch (Throwable th) {
                            th = th;
                            if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() <= i + 1) {
                                throw new IllegalArgumentException((str + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                            }
                            if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i) {
                                psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                            }
                            Context<PsiElement> context2 = psiRawFirBuilder2.getContext();
                            FqName fqNameParent = psiRawFirBuilder2.getContext().getClassName().parent();
                            Intrinsics.checkNotNullExpressionValue(fqNameParent, str2);
                            context2.setClassName(fqNameParent);
                            psiRawFirBuilder2.getContext().setForcedLocalContext(z2);
                            psiRawFirBuilder2.getContext().setContainerIsExpect(z);
                            throw th;
                        }
                    }
                    try {
                        if (((KtClass) classOrObject).isInterface()) {
                            classKind = ClassKind.INTERFACE;
                        } else if (((KtClass) classOrObject).isEnum()) {
                            classKind = ClassKind.ENUM_CLASS;
                        } else {
                            classKind = classOrObject.isAnnotation() ? ClassKind.ANNOTATION_CLASS : ClassKind.CLASS;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        z = containerIsExpect;
                        i = size;
                        z2 = forcedLocalContext;
                        str = "Wrong number of ";
                        str3 = "parent(...)";
                        str2 = str3;
                        if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() <= i + 1) {
                            throw new IllegalArgumentException((str + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                        }
                        if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i) {
                            psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                        }
                        Context<PsiElement> context3 = psiRawFirBuilder2.getContext();
                        FqName fqNameParent2 = psiRawFirBuilder2.getContext().getClassName().parent();
                        Intrinsics.checkNotNullExpressionValue(fqNameParent2, str2);
                        context3.setClassName(fqNameParent2);
                        psiRawFirBuilder2.getContext().setForcedLocalContext(z2);
                        psiRawFirBuilder2.getContext().setContainerIsExpect(z);
                        throw th;
                    }
                }
                ClassKind classKind2 = classKind;
                FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(z3 ? Visibilities.Local.INSTANCE : psiRawFirBuilder3.getVisibility(classOrObject), psiRawFirBuilder3.getModality(classOrObject));
                firDeclarationStatusImpl.setExpect(z4);
                firDeclarationStatusImpl.setActual(PsiUtilsKt.hasActualModifier(classOrObject));
                firDeclarationStatusImpl.setInner(classOrObject.hasModifier(KtTokens.INNER_KEYWORD) && !(classOrObject.getLightParent().getLightParent() instanceof KtScript));
                KtObjectDeclaration ktObjectDeclaration = classOrObject instanceof KtObjectDeclaration ? (KtObjectDeclaration) classOrObject : null;
                firDeclarationStatusImpl.setCompanion(ktObjectDeclaration != null && ktObjectDeclaration.isCompanion());
                firDeclarationStatusImpl.setData(classOrObject.hasModifier(KtTokens.DATA_KEYWORD));
                firDeclarationStatusImpl.setInline(classOrObject.hasModifier(KtTokens.INLINE_KEYWORD) || classOrObject.hasModifier(KtTokens.VALUE_KEYWORD));
                firDeclarationStatusImpl.setFun(classOrObject.hasModifier(KtTokens.FUN_KEYWORD));
                firDeclarationStatusImpl.setExternal(classOrObject.hasModifier(KtTokens.EXTERNAL_KEYWORD));
                PsiRawFirBuilder psiRawFirBuilder4 = psiRawFirBuilder3;
                try {
                    try {
                        psiRawFirBuilder4.addCapturedTypeParameters(firDeclarationStatusImpl.isInner() || z3, ktPsiSourceElement, CollectionsKt.emptyList());
                        try {
                            FirRegularClassBuilder firRegularClassBuilder2 = new FirRegularClassBuilder();
                            firRegularClassBuilder2.setSource(ktPsiSourceElement);
                            firRegularClassBuilder2.setModuleData(psiRawFirBuilder3.getBaseModuleData());
                            firRegularClassBuilder2.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                            Name nameAsSafeName2 = classOrObject.getNameAsSafeName();
                            Intrinsics.checkNotNullExpressionValue(nameAsSafeName2, "getNameAsSafeName(...)");
                            firRegularClassBuilder2.setName(nameAsSafeName2);
                            firRegularClassBuilder2.setStatus(firDeclarationStatusImpl);
                            firRegularClassBuilder2.setClassKind(classKind2);
                            firRegularClassBuilder2.setScopeProvider(psiRawFirBuilder3.getBaseScopeProvider());
                            firRegularClassBuilder2.setSymbol(new FirRegularClassSymbol(psiRawFirBuilder3.getContext().getCurrentClassId()));
                            extractAnnotationsTo(classOrObject, firRegularClassBuilder2);
                            extractTypeParametersTo(classOrObject, firRegularClassBuilder2, firRegularClassBuilder2.getSymbol());
                            boolean z5 = true;
                            psiRawFirBuilder3.getContext().appendOuterTypeParameters(true, firRegularClassBuilder2.getTypeParameters());
                            Context<PsiElement> context4 = psiRawFirBuilder3.getContext();
                            if (!firDeclarationStatusImpl.isInner() && !z3) {
                                z5 = false;
                            }
                            context4.pushFirTypeParameters(z5, firRegularClassBuilder2.getTypeParameters().subList(0, classOrObject.getTypeParameters().size()));
                            FirResolvedTypeRef delegatedSelfType = psiRawFirBuilder3.toDelegatedSelfType(classOrObject, firRegularClassBuilder2);
                            psiRawFirBuilder3.registerSelfType(delegatedSelfType);
                            psiRawFirBuilder = psiRawFirBuilder4;
                            try {
                                Pair<FirTypeRef, Map<Integer, FirFieldSymbol>> pairExtractSuperTypeListEntriesTo = extractSuperTypeListEntriesTo(classOrObject, firRegularClassBuilder2, delegatedSelfType, null, classKind2, firRegularClassBuilder2.getTypeParameters(), z4);
                                FirTypeRef firTypeRefComponent1 = pairExtractSuperTypeListEntriesTo.component1();
                                Map<Integer, FirFieldSymbol> mapComponent2 = pairExtractSuperTypeListEntriesTo.component2();
                                KtPrimaryConstructor primaryConstructor = classOrObject.getPrimaryConstructor();
                                Iterator<T> it = firRegularClassBuilder2.getDeclarations().iterator();
                                do {
                                    if (!it.hasNext()) {
                                        next = null;
                                        break;
                                    }
                                    next = it.next();
                                } while (!(((FirDeclaration) next) instanceof FirConstructor));
                                FirConstructor firConstructor = next instanceof FirConstructor ? (FirConstructor) next : null;
                                if (primaryConstructor != null && firConstructor != null) {
                                    for (Pair pair : CollectionsKt.zip(primaryConstructor.getValueParameters(), firConstructor.getValueParameters())) {
                                        KtParameter ktParameter = (KtParameter) pair.component1();
                                        FirValueParameter firValueParameter = (FirValueParameter) pair.component2();
                                        if (ktParameter.hasValOrVar()) {
                                            Intrinsics.checkNotNull(ktParameter);
                                            FirDeclarationBuildingUtilsKt.addDeclaration(firRegularClassBuilder2, toFirProperty(ktParameter, firValueParameter));
                                        }
                                    }
                                }
                                Iterator<KtDeclaration> it2 = classOrObject.getDeclarations().iterator();
                                while (it2.hasNext()) {
                                    FirDeclarationBuildingUtilsKt.addDeclaration(firRegularClassBuilder2, toFirDeclaration(it2.next(), firTypeRefComponent1, delegatedSelfType, classOrObject, firRegularClassBuilder2, firRegularClassBuilder2.getTypeParameters()));
                                    firConstructor = firConstructor;
                                    firTypeRefComponent1 = firTypeRefComponent1;
                                    mapComponent2 = mapComponent2;
                                }
                                final FirConstructor firConstructor2 = firConstructor;
                                Map<Integer, FirFieldSymbol> map = mapComponent2;
                                KtClassBody body = classOrObject.getBody();
                                if (body == null || (listEmptyList = body.getDanglingModifierLists()) == null) {
                                    listEmptyList = CollectionsKt.emptyList();
                                }
                                Iterator<KtModifierList> it3 = listEmptyList.iterator();
                                while (it3.hasNext()) {
                                    FirDanglingModifierList firDanglingModifierListBuildErrorTopLevelDeclarationForDanglingModifierList = buildErrorTopLevelDeclarationForDanglingModifierList(it3.next());
                                    ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = psiRawFirBuilder3.currentDispatchReceiverType();
                                    ClassMembersKt.setContainingClassAttr(firDanglingModifierListBuildErrorTopLevelDeclarationForDanglingModifierList, coneClassLikeTypeCurrentDispatchReceiverType != null ? coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag() : null);
                                    FirDeclarationBuildingUtilsKt.addDeclaration(firRegularClassBuilder2, firDanglingModifierListBuildErrorTopLevelDeclarationForDanglingModifierList);
                                }
                                if (!classOrObject.hasModifier(KtTokens.DATA_KEYWORD) || firConstructor2 == null) {
                                    firRegularClassBuilder = firRegularClassBuilder2;
                                    z2 = forcedLocalContext;
                                    str4 = "Wrong number of ";
                                    str3 = "parent(...)";
                                    i = size;
                                } else {
                                    try {
                                        List<KtParameter> primaryConstructorParameters = classOrObject.getPrimaryConstructorParameters();
                                        ArrayList arrayList = new ArrayList();
                                        for (Object obj : primaryConstructorParameters) {
                                            if (((KtParameter) obj).hasValOrVar()) {
                                                arrayList.add(obj);
                                            }
                                        }
                                        ArrayList arrayList2 = arrayList;
                                        List<FirDeclaration> declarations = firRegularClassBuilder2.getDeclarations();
                                        ArrayList arrayList3 = new ArrayList();
                                        for (Object obj2 : declarations) {
                                            if (obj2 instanceof FirProperty) {
                                                arrayList3.add(obj2);
                                            }
                                        }
                                        List listZip = CollectionsKt.zip(arrayList2, arrayList3);
                                        PsiRawFirBuilder psiRawFirBuilder5 = psiRawFirBuilder3;
                                        FqName packageFqName = psiRawFirBuilder3.getContext().getPackageFqName();
                                        FqName className = psiRawFirBuilder3.getContext().getClassName();
                                        Function1<KtFakeSourceElementKind, FirTypeRef> function1 = new Function1<KtFakeSourceElementKind, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitClassOrObject$1$1$1$3
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public final FirTypeRef invoke(KtFakeSourceElementKind it4) {
                                                Intrinsics.checkNotNullParameter(it4, "it");
                                                return UtilsKt.copyWithNewSourceKind(firConstructor2.getReturnTypeRef(), it4);
                                            }
                                        };
                                        firRegularClassBuilder = firRegularClassBuilder2;
                                        i = size;
                                        z2 = forcedLocalContext;
                                        str4 = "Wrong number of ";
                                        str3 = "parent(...)";
                                        try {
                                            new AbstractRawFirBuilder.DataClassMembersGenerator(psiRawFirBuilder5, classOrObject, firRegularClassBuilder, listZip, packageFqName, className, function1, new Function2<FirProperty, KtFakeSourceElementKind, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitClassOrObject$1$1$1$4
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(2);
                                                }

                                                @Override // kotlin.jvm.functions.Function2
                                                public final FirTypeRef invoke(FirProperty property, KtFakeSourceElementKind newKind) {
                                                    Intrinsics.checkNotNullParameter(property, "property");
                                                    Intrinsics.checkNotNullParameter(newKind, "newKind");
                                                    FirTypeRef returnTypeRef = property.getReturnTypeRef();
                                                    if (returnTypeRef instanceof FirErrorTypeRef) {
                                                        FirErrorTypeRef firErrorTypeRef = (FirErrorTypeRef) returnTypeRef;
                                                        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                                                        firErrorTypeRefBuilder.setSource(firErrorTypeRef.getSource());
                                                        firErrorTypeRefBuilder.setType(firErrorTypeRef.getType());
                                                        firErrorTypeRefBuilder.setDelegatedTypeRef(firErrorTypeRef.getDelegatedTypeRef());
                                                        firErrorTypeRefBuilder.setDiagnostic(firErrorTypeRef.getDiagnostic());
                                                        KtSourceElement source = returnTypeRef.getSource();
                                                        firErrorTypeRefBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, newKind, 0, 0, 6, null) : null);
                                                        return firErrorTypeRefBuilder.mo6809build();
                                                    }
                                                    PsiRawFirBuilder psiRawFirBuilder6 = psiRawFirBuilder3;
                                                    PsiRawFirBuilder.Visitor visitor = this;
                                                    KtSourceElementKind forcedElementSourceKind = psiRawFirBuilder6.getContext().getForcedElementSourceKind();
                                                    psiRawFirBuilder6.getContext().setForcedElementSourceKind(newKind);
                                                    try {
                                                        return visitor.toFirOrImplicitType((KtTypeReference) UtilsKt.getPsi(returnTypeRef));
                                                    } finally {
                                                        psiRawFirBuilder6.getContext().setForcedElementSourceKind(forcedElementSourceKind);
                                                    }
                                                }
                                            }, new Function2<FirValueParameterBuilder, PsiElement, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitClassOrObject$1$1$1$5
                                                {
                                                    super(2);
                                                }

                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                public final void invoke2(FirValueParameterBuilder $receiver, PsiElement it4) {
                                                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                                                    Intrinsics.checkNotNullParameter(it4, "it");
                                                    this.this$0.addAnnotationsFrom($receiver, (KtParameter) it4, true);
                                                }

                                                @Override // kotlin.jvm.functions.Function2
                                                public /* bridge */ /* synthetic */ Unit invoke(FirValueParameterBuilder firValueParameterBuilder, PsiElement psiElement) {
                                                    invoke2(firValueParameterBuilder, psiElement);
                                                    return Unit.INSTANCE;
                                                }
                                            }).generate();
                                        } catch (Throwable th3) {
                                            th = th3;
                                            z = containerIsExpect;
                                            str = str4;
                                            try {
                                                psiRawFirBuilder.getContext().popFirTypeParameters();
                                                throw th;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                str2 = str3;
                                                if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() <= i + 1) {
                                                    throw new IllegalArgumentException((str + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                                                }
                                                if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i) {
                                                    psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                                                }
                                                Context<PsiElement> context5 = psiRawFirBuilder2.getContext();
                                                FqName fqNameParent3 = psiRawFirBuilder2.getContext().getClassName().parent();
                                                Intrinsics.checkNotNullExpressionValue(fqNameParent3, str2);
                                                context5.setClassName(fqNameParent3);
                                                psiRawFirBuilder2.getContext().setForcedLocalContext(z2);
                                                psiRawFirBuilder2.getContext().setContainerIsExpect(z);
                                                throw th;
                                            }
                                        }
                                    } catch (Throwable th5) {
                                        th = th5;
                                        z2 = forcedLocalContext;
                                        str4 = "Wrong number of ";
                                        str3 = "parent(...)";
                                        i = size;
                                        z = containerIsExpect;
                                        str = str4;
                                        psiRawFirBuilder.getContext().popFirTypeParameters();
                                        throw th;
                                    }
                                }
                                if (classOrObject.hasModifier(KtTokens.ENUM_KEYWORD)) {
                                    EnumClassUtilsKt.generateValuesFunction$default(firRegularClassBuilder, psiRawFirBuilder3.getBaseModuleData(), psiRawFirBuilder3.getContext().getPackageFqName(), psiRawFirBuilder3.getContext().getClassName(), z4, null, 16, null);
                                    EnumClassUtilsKt.generateValueOfFunction$default(firRegularClassBuilder, psiRawFirBuilder3.getBaseModuleData(), psiRawFirBuilder3.getContext().getPackageFqName(), psiRawFirBuilder3.getContext().getClassName(), z4, null, 16, null);
                                    EnumClassUtilsKt.generateEntriesGetter$default(firRegularClassBuilder, psiRawFirBuilder3.getBaseModuleData(), psiRawFirBuilder3.getContext().getPackageFqName(), psiRawFirBuilder3.getContext().getClassName(), z4, null, 16, null);
                                }
                                psiRawFirBuilder3.initCompanionObjectSymbolAttr(firRegularClassBuilder);
                                psiRawFirBuilder3.getContext().popFirTypeParameters();
                                firRegularClassBuilder.getContextReceivers().addAll(convertContextReceivers(classOrObject.getContextReceivers()));
                                FirRegularClass firRegularClassMo6809build = firRegularClassBuilder.mo6809build();
                                DelegateFieldsMapKt.setDelegateFieldsMap(firRegularClassMo6809build, map);
                                try {
                                    psiRawFirBuilder.getContext().popFirTypeParameters();
                                    if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i + 1) {
                                        throw new IllegalArgumentException((str4 + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                                    }
                                    if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i) {
                                        psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                                    }
                                    Context<PsiElement> context6 = psiRawFirBuilder2.getContext();
                                    FqName fqNameParent4 = psiRawFirBuilder2.getContext().getClassName().parent();
                                    Intrinsics.checkNotNullExpressionValue(fqNameParent4, str3);
                                    context6.setClassName(fqNameParent4);
                                    psiRawFirBuilder2.getContext().setForcedLocalContext(z2);
                                    psiRawFirBuilder2.getContext().setContainerIsExpect(containerIsExpect);
                                    PsiRawFirBuilder psiRawFirBuilder6 = PsiRawFirBuilder.this;
                                    if (classOrObject.getLightParent() instanceof KtClassBody) {
                                        psiRawFirBuilder6.initContainingClassForLocalAttr(firRegularClassMo6809build);
                                    }
                                    fillDanglingConstraintsTo(classOrObject, firRegularClassMo6809build);
                                    return firRegularClassMo6809build;
                                } catch (Throwable th6) {
                                    th = th6;
                                    z = containerIsExpect;
                                    str = str4;
                                    str2 = str3;
                                    if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() <= i + 1) {
                                        throw new IllegalArgumentException((str + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                                    }
                                    if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i) {
                                        psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                                    }
                                    Context<PsiElement> context7 = psiRawFirBuilder2.getContext();
                                    FqName fqNameParent5 = psiRawFirBuilder2.getContext().getClassName().parent();
                                    Intrinsics.checkNotNullExpressionValue(fqNameParent5, str2);
                                    context7.setClassName(fqNameParent5);
                                    psiRawFirBuilder2.getContext().setForcedLocalContext(z2);
                                    psiRawFirBuilder2.getContext().setContainerIsExpect(z);
                                    throw th;
                                }
                            } catch (Throwable th7) {
                                th = th7;
                                z2 = forcedLocalContext;
                                str = "Wrong number of ";
                                str3 = "parent(...)";
                                i = size;
                                z = containerIsExpect;
                            }
                        } catch (Throwable th8) {
                            th = th8;
                            psiRawFirBuilder = psiRawFirBuilder4;
                            z2 = forcedLocalContext;
                            str = "Wrong number of ";
                            str3 = "parent(...)";
                            z = containerIsExpect;
                            i = size;
                        }
                    } catch (Throwable th9) {
                        th = th9;
                        z2 = forcedLocalContext;
                        str = "Wrong number of ";
                        str3 = "parent(...)";
                        z = containerIsExpect;
                        i = size;
                    }
                } catch (Throwable th10) {
                    th = th10;
                    i = size;
                    z2 = forcedLocalContext;
                    str = "Wrong number of ";
                    str3 = "parent(...)";
                    z = containerIsExpect;
                }
            } catch (Throwable th11) {
                th = th11;
                z = containerIsExpect;
                i = size;
                z2 = forcedLocalContext;
                str = "Wrong number of ";
                str2 = "parent(...)";
            }
        }

        /* JADX WARN: Code duplicated, block: B:44:0x0248  */
        /* JADX WARN: Code duplicated, block: B:46:0x0256  */
        /* JADX WARN: Code duplicated, block: B:49:0x0292  */
        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitObjectLiteralExpression(KtObjectLiteralExpression expression, FirElement data) throws Throwable {
            String str;
            boolean z;
            int i;
            boolean z2;
            List<KtModifierList> listEmptyList;
            Intrinsics.checkNotNullParameter(expression, "expression");
            Name name2 = SpecialNames.ANONYMOUS;
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            Context<PsiElement> context = psiRawFirBuilder2.getContext();
            FqName fqNameChild = psiRawFirBuilder2.getContext().getClassName().child(name2);
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            context.setClassName(fqNameChild);
            boolean forcedLocalContext = psiRawFirBuilder2.getContext().getForcedLocalContext();
            psiRawFirBuilder2.getContext().setForcedLocalContext(true);
            boolean containerIsExpect = psiRawFirBuilder2.getContext().getContainerIsExpect();
            psiRawFirBuilder2.getContext().setContainerIsExpect(containerIsExpect);
            int size = psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size();
            try {
                FirAnonymousObjectExpressionBuilder firAnonymousObjectExpressionBuilder = new FirAnonymousObjectExpressionBuilder();
                firAnonymousObjectExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
                FirAnonymousObjectBuilder firAnonymousObjectBuilder = new FirAnonymousObjectBuilder();
                KtObjectDeclaration objectDeclaration = expression.getObjectDeclaration();
                Intrinsics.checkNotNullExpressionValue(objectDeclaration, "getObjectDeclaration(...)");
                firAnonymousObjectBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, objectDeclaration, null, 1, null));
                firAnonymousObjectBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
                firAnonymousObjectBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firAnonymousObjectBuilder.setClassKind(ClassKind.CLASS);
                firAnonymousObjectBuilder.setScopeProvider(psiRawFirBuilder.getBaseScopeProvider());
                firAnonymousObjectBuilder.setSymbol(new FirAnonymousObjectSymbol());
                firAnonymousObjectBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
                psiRawFirBuilder.getContext().appendOuterTypeParameters(false, firAnonymousObjectBuilder.getTypeParameters());
                FirResolvedTypeRef delegatedSelfType = psiRawFirBuilder.toDelegatedSelfType(objectDeclaration, firAnonymousObjectBuilder);
                psiRawFirBuilder.registerSelfType(delegatedSelfType);
                extractAnnotationsTo(objectDeclaration, firAnonymousObjectBuilder);
                i = size;
                try {
                    Pair<FirTypeRef, Map<Integer, FirFieldSymbol>> pairExtractSuperTypeListEntriesTo = extractSuperTypeListEntriesTo(objectDeclaration, firAnonymousObjectBuilder, delegatedSelfType, null, ClassKind.CLASS, CollectionsKt.emptyList(), false);
                    FirTypeRef firTypeRefComponent1 = pairExtractSuperTypeListEntriesTo.component1();
                    Map<Integer, FirFieldSymbol> mapComponent2 = pairExtractSuperTypeListEntriesTo.component2();
                    Iterator<KtDeclaration> it = objectDeclaration.getDeclarations().iterator();
                    while (it.hasNext()) {
                        try {
                            FirTypeRef firTypeRef = firTypeRefComponent1;
                            z2 = containerIsExpect;
                            Map<Integer, FirFieldSymbol> map = mapComponent2;
                            try {
                                firAnonymousObjectBuilder.getDeclarations().add(toFirDeclaration(it.next(), firTypeRefComponent1, delegatedSelfType, objectDeclaration, firAnonymousObjectBuilder, CollectionsKt.emptyList()));
                                firTypeRefComponent1 = firTypeRef;
                                mapComponent2 = map;
                                containerIsExpect = z2;
                            } catch (Throwable th) {
                                th = th;
                                str = "Wrong number of ";
                                z = z2;
                                if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() <= i + 1) {
                                    throw new IllegalArgumentException((str + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                                }
                                if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i) {
                                    psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                                }
                                Context<PsiElement> context2 = psiRawFirBuilder2.getContext();
                                FqName fqNameParent = psiRawFirBuilder2.getContext().getClassName().parent();
                                Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                                context2.setClassName(fqNameParent);
                                psiRawFirBuilder2.getContext().setForcedLocalContext(forcedLocalContext);
                                psiRawFirBuilder2.getContext().setContainerIsExpect(z);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            z2 = containerIsExpect;
                            str = "Wrong number of ";
                            z = z2;
                            if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() <= i + 1) {
                                throw new IllegalArgumentException((str + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                            }
                            if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i) {
                                psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                            }
                            Context<PsiElement> context3 = psiRawFirBuilder2.getContext();
                            FqName fqNameParent2 = psiRawFirBuilder2.getContext().getClassName().parent();
                            Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
                            context3.setClassName(fqNameParent2);
                            psiRawFirBuilder2.getContext().setForcedLocalContext(forcedLocalContext);
                            psiRawFirBuilder2.getContext().setContainerIsExpect(z);
                            throw th;
                        }
                    }
                    z2 = containerIsExpect;
                    Map<Integer, FirFieldSymbol> map2 = mapComponent2;
                    KtClassBody body = objectDeclaration.getBody();
                    if (body == null || (listEmptyList = body.getDanglingModifierLists()) == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    for (KtModifierList ktModifierList : listEmptyList) {
                        List<FirDeclaration> declarations = firAnonymousObjectBuilder.getDeclarations();
                        FirDanglingModifierList firDanglingModifierListBuildErrorTopLevelDeclarationForDanglingModifierList = buildErrorTopLevelDeclarationForDanglingModifierList(ktModifierList);
                        ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = psiRawFirBuilder.currentDispatchReceiverType();
                        ClassMembersKt.setContainingClassAttr(firDanglingModifierListBuildErrorTopLevelDeclarationForDanglingModifierList, coneClassLikeTypeCurrentDispatchReceiverType != null ? coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag() : null);
                        declarations.add(firDanglingModifierListBuildErrorTopLevelDeclarationForDanglingModifierList);
                    }
                    FirAnonymousObject firAnonymousObjectMo6809build = firAnonymousObjectBuilder.mo6809build();
                    DelegateFieldsMapKt.setDelegateFieldsMap(firAnonymousObjectMo6809build, map2);
                    firAnonymousObjectExpressionBuilder.setAnonymousObject(firAnonymousObjectMo6809build);
                    FirAnonymousObjectExpression firAnonymousObjectExpressionMo6809build = firAnonymousObjectExpressionBuilder.mo6809build();
                    if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i + 1) {
                        throw new IllegalArgumentException(("Wrong number of " + psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size()).toString());
                    }
                    if (psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().size() > i) {
                        psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder2.getContext().getDispatchReceiverTypesStack()));
                    }
                    Context<PsiElement> context4 = psiRawFirBuilder2.getContext();
                    FqName fqNameParent3 = psiRawFirBuilder2.getContext().getClassName().parent();
                    Intrinsics.checkNotNullExpressionValue(fqNameParent3, "parent(...)");
                    context4.setClassName(fqNameParent3);
                    psiRawFirBuilder2.getContext().setForcedLocalContext(forcedLocalContext);
                    psiRawFirBuilder2.getContext().setContainerIsExpect(z2);
                    return firAnonymousObjectExpressionMo6809build;
                } catch (Throwable th3) {
                    th = th3;
                    str = "Wrong number of ";
                    z = containerIsExpect;
                }
            } catch (Throwable th4) {
                th = th4;
                str = "Wrong number of ";
                z = containerIsExpect;
                i = size;
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitTypeAlias(KtTypeAlias typeAlias, FirElement data) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            boolean z = PsiUtilsKt.hasExpectModifier(typeAlias) || PsiRawFirBuilder.this.getContext().getContainerIsExpect();
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            Name nameAsSafeName = typeAlias.getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            Context<PsiElement> context = psiRawFirBuilder.getContext();
            FqName fqNameChild = psiRawFirBuilder.getContext().getClassName().child(nameAsSafeName);
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            context.setClassName(fqNameChild);
            boolean forcedLocalContext = psiRawFirBuilder.getContext().getForcedLocalContext();
            psiRawFirBuilder.getContext().setForcedLocalContext(psiRawFirBuilder.getContext().getForcedLocalContext());
            boolean containerIsExpect = psiRawFirBuilder.getContext().getContainerIsExpect();
            psiRawFirBuilder.getContext().setContainerIsExpect(containerIsExpect || z);
            int size = psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size();
            try {
                FirTypeAliasBuilder firTypeAliasBuilder = new FirTypeAliasBuilder();
                firTypeAliasBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, typeAlias, null, 1, null));
                firTypeAliasBuilder.setModuleData(psiRawFirBuilder2.getBaseModuleData());
                firTypeAliasBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                Name nameAsSafeName2 = typeAlias.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName2, "getNameAsSafeName(...)");
                firTypeAliasBuilder.setName(nameAsSafeName2);
                FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(psiRawFirBuilder2.getVisibility(typeAlias), Modality.FINAL);
                firDeclarationStatusImpl.setExpect(z);
                firDeclarationStatusImpl.setActual(PsiUtilsKt.hasActualModifier(typeAlias));
                firTypeAliasBuilder.setStatus(firDeclarationStatusImpl);
                firTypeAliasBuilder.setSymbol(new FirTypeAliasSymbol(psiRawFirBuilder2.getContext().getCurrentClassId()));
                firTypeAliasBuilder.setExpandedTypeRef(toFirOrErrorType(typeAlias.getTypeReference()));
                extractAnnotationsTo(typeAlias, firTypeAliasBuilder);
                extractTypeParametersTo(typeAlias, firTypeAliasBuilder, firTypeAliasBuilder.getSymbol());
                FirTypeAlias firTypeAliasMo6809build = firTypeAliasBuilder.mo6809build();
                if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                    throw new IllegalArgumentException(("Wrong number of " + psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                }
                if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size) {
                    psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
                }
                Context<PsiElement> context2 = psiRawFirBuilder.getContext();
                FqName fqNameParent = psiRawFirBuilder.getContext().getClassName().parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                context2.setClassName(fqNameParent);
                psiRawFirBuilder.getContext().setForcedLocalContext(forcedLocalContext);
                psiRawFirBuilder.getContext().setContainerIsExpect(containerIsExpect);
                return firTypeAliasMo6809build;
            } catch (Throwable th) {
                if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                    throw new IllegalArgumentException(("Wrong number of " + psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
                }
                if (psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size) {
                    psiRawFirBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(psiRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
                }
                Context<PsiElement> context3 = psiRawFirBuilder.getContext();
                FqName fqNameParent2 = psiRawFirBuilder.getContext().getClassName().parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
                context3.setClassName(fqNameParent2);
                psiRawFirBuilder.getContext().setForcedLocalContext(forcedLocalContext);
                psiRawFirBuilder.getContext().setContainerIsExpect(containerIsExpect);
                throw th;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitNamedFunction(KtNamedFunction function, FirElement data) {
            FirTypeRef firOrImplicitType;
            T identifier;
            Visibilities.Local visibility;
            FirSimpleFunctionBuilder firSimpleFunctionBuilder;
            String name2;
            FirFunctionSymbol firFunctionSymbol;
            List<FirTypeParameter> listEmptyList;
            T identifier2;
            String name3;
            Intrinsics.checkNotNullParameter(function, "function");
            KtTypeReference ktTypeReferenceMo7396getTypeReference = function.mo7396getTypeReference();
            if (function.hasBlockBody()) {
                firOrImplicitType = toFirOrUnitType(ktTypeReferenceMo7396getTypeReference);
            } else {
                firOrImplicitType = toFirOrImplicitType(ktTypeReferenceMo7396getTypeReference);
            }
            KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = function.mo7393getReceiverTypeReference();
            FirElement firElementConvertElement = ktTypeReferenceMo7393getReceiverTypeReference != null ? convertElement(ktTypeReferenceMo7393getReceiverTypeReference, null) : null;
            if (!(firElementConvertElement instanceof FirTypeRef)) {
                firElementConvertElement = null;
            }
            FirTypeRef firTypeRef = (FirTypeRef) firElementConvertElement;
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            boolean zIsAnonymous = function.isAnonymous();
            boolean zIsLocal = function.isLocal();
            if (zIsAnonymous) {
                FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                firAnonymousFunctionBuilder.setReceiverParameter(firTypeRef != null ? ConversionUtilsKt.convertToReceiverParameter(firTypeRef) : null);
                FirAnonymousFunctionSymbol firAnonymousFunctionSymbol = new FirAnonymousFunctionSymbol();
                firAnonymousFunctionBuilder.setSymbol(firAnonymousFunctionSymbol);
                firAnonymousFunctionBuilder.setLambda(false);
                firAnonymousFunctionBuilder.setHasExplicitParameterList(true);
                firAnonymousFunctionBuilder.setLabel(psiRawFirBuilder.getContext().getLastLabel(function));
                FirLabel label = firAnonymousFunctionBuilder.getLabel();
                if (label == null || (name3 = label.getName()) == null) {
                    identifier2 = name3;
                    Name name4 = (Name) CollectionsKt.lastOrNull((List) psiRawFirBuilder.getContext().getCalleeNamesForLambda());
                    identifier2 = name4 != null ? name4.getIdentifier() : 0;
                }
                identifier2 = name3;
                objectRef.element = identifier2;
                if (function.hasModifier(KtTokens.SUSPEND_KEYWORD)) {
                    firAnonymousFunctionBuilder.setStatus(FirResolvedDeclarationStatusImpl.INSTANCE.getDEFAULT_STATUS_FOR_SUSPEND_FUNCTION_EXPRESSION());
                }
                firSimpleFunctionBuilder = firAnonymousFunctionBuilder;
                firFunctionSymbol = firAnonymousFunctionSymbol;
            } else {
                FirSimpleFunctionBuilder firSimpleFunctionBuilder2 = new FirSimpleFunctionBuilder();
                PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                firSimpleFunctionBuilder2.setReceiverParameter(firTypeRef != null ? ConversionUtilsKt.convertToReceiverParameter(firTypeRef) : null);
                Name nameAsSafeName = function.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                firSimpleFunctionBuilder2.setName(nameAsSafeName);
                FirLabel lastLabel = psiRawFirBuilder2.getContext().getLastLabel(function);
                if (lastLabel == null || (name2 = lastLabel.getName()) == null) {
                    identifier = name2;
                    identifier = !firSimpleFunctionBuilder2.getName().isSpecial() ? firSimpleFunctionBuilder2.getName().getIdentifier() : 0;
                }
                identifier = name2;
                objectRef.element = identifier;
                Name nameAsSafeName2 = function.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName2, "getNameAsSafeName(...)");
                FirNamedFunctionSymbol firNamedFunctionSymbol = new FirNamedFunctionSymbol(psiRawFirBuilder2.callableIdForName(nameAsSafeName2));
                firSimpleFunctionBuilder2.setSymbol(firNamedFunctionSymbol);
                firSimpleFunctionBuilder2.setDispatchReceiverType(!zIsLocal ? psiRawFirBuilder2.currentDispatchReceiverType() : null);
                if (!zIsLocal) {
                    visibility = psiRawFirBuilder2.getVisibility(function);
                } else {
                    visibility = Visibilities.Local.INSTANCE;
                }
                FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, psiRawFirBuilder2.getModality(function));
                KtNamedFunction ktNamedFunction = function;
                firDeclarationStatusImpl.setExpect(PsiUtilsKt.hasExpectModifier(ktNamedFunction) || psiRawFirBuilder2.getContext().getContainerIsExpect());
                firDeclarationStatusImpl.setActual(PsiUtilsKt.hasActualModifier(ktNamedFunction));
                firDeclarationStatusImpl.setOverride(function.hasModifier(KtTokens.OVERRIDE_KEYWORD));
                firDeclarationStatusImpl.setOperator(function.hasModifier(KtTokens.OPERATOR_KEYWORD));
                firDeclarationStatusImpl.setInfix(function.hasModifier(KtTokens.INFIX_KEYWORD));
                firDeclarationStatusImpl.setInline(function.hasModifier(KtTokens.INLINE_KEYWORD));
                firDeclarationStatusImpl.setTailRec(function.hasModifier(KtTokens.TAILREC_KEYWORD));
                firDeclarationStatusImpl.setExternal(function.hasModifier(KtTokens.EXTERNAL_KEYWORD));
                firDeclarationStatusImpl.setSuspend(function.hasModifier(KtTokens.SUSPEND_KEYWORD));
                firSimpleFunctionBuilder2.setStatus(firDeclarationStatusImpl);
                List<FirContextReceiver> contextReceivers = firSimpleFunctionBuilder2.getContextReceivers();
                List<KtContextReceiver> contextReceivers2 = function.getContextReceivers();
                Intrinsics.checkNotNullExpressionValue(contextReceivers2, "getContextReceivers(...)");
                contextReceivers.addAll(convertContextReceivers(contextReceivers2));
                firSimpleFunctionBuilder = firSimpleFunctionBuilder2;
                firFunctionSymbol = firNamedFunctionSymbol;
            }
            FirFunctionSymbol firFunctionSymbol2 = firFunctionSymbol;
            FirFunctionBuilder firFunctionBuilder = firSimpleFunctionBuilder;
            FirFunctionTarget firFunctionTarget = new FirFunctionTarget((String) objectRef.element, false);
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, function, null, 1, null);
            PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
            KtPsiSourceElement ktPsiSourceElement2 = ktPsiSourceElement;
            firFunctionBuilder.setSource(ktPsiSourceElement2);
            firFunctionBuilder.setModuleData(psiRawFirBuilder3.getBaseModuleData());
            firFunctionBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firFunctionBuilder.setReturnTypeRef(firOrImplicitType);
            psiRawFirBuilder3.getContext().getFirFunctionTargets().add(firFunctionTarget);
            extractAnnotationsTo(function, firFunctionBuilder);
            boolean z = firFunctionBuilder instanceof FirSimpleFunctionBuilder;
            if (z) {
                extractTypeParametersTo(function, (FirTypeParametersOwnerBuilder) firFunctionBuilder, ((FirSimpleFunctionBuilder) firFunctionBuilder).getSymbol());
            }
            for (KtParameter ktParameter : function.getValueParameters()) {
                List<FirValueParameter> valueParameters = firFunctionBuilder.getValueParameters();
                Intrinsics.checkNotNull(ktParameter);
                valueParameters.add(convertValueParameter$default(this, ktParameter, firFunctionSymbol2, null, zIsAnonymous ? AbstractRawFirBuilder.ValueParameterDeclaration.LAMBDA : AbstractRawFirBuilder.ValueParameterDeclaration.FUNCTION, null, 16, null));
                ktPsiSourceElement2 = ktPsiSourceElement2;
                zIsAnonymous = zIsAnonymous;
                z = z;
            }
            KtPsiSourceElement ktPsiSourceElement3 = ktPsiSourceElement2;
            if (z) {
                listEmptyList = ((FirSimpleFunctionBuilder) firFunctionBuilder).getTypeParameters();
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            PsiRawFirBuilder psiRawFirBuilder4 = psiRawFirBuilder3;
            psiRawFirBuilder4.addCapturedTypeParameters(true, ktPsiSourceElement3, listEmptyList);
            try {
                FirContractDescription firContractDescriptionObtainContractDescription = obtainContractDescription(function);
                Pair<FirBlock, FirContractDescription> pairBuildFirBody = buildFirBody(function);
                FirBlock firBlockComponent1 = pairBuildFirBody.component1();
                FirContractDescription firContractDescriptionComponent2 = pairBuildFirBody.component2();
                firFunctionBuilder.setBody(firBlockComponent1);
                if (firContractDescriptionObtainContractDescription == null) {
                    firContractDescriptionObtainContractDescription = firContractDescriptionComponent2;
                }
                if (firContractDescriptionObtainContractDescription != null) {
                    if (firFunctionBuilder instanceof FirSimpleFunctionBuilder) {
                        ((FirSimpleFunctionBuilder) firFunctionBuilder).setContractDescription(firContractDescriptionObtainContractDescription);
                    } else if (firFunctionBuilder instanceof FirAnonymousFunctionBuilder) {
                        ((FirAnonymousFunctionBuilder) firFunctionBuilder).setContractDescription(firContractDescriptionObtainContractDescription);
                    }
                    Unit unit = Unit.INSTANCE;
                }
                psiRawFirBuilder4.getContext().popFirTypeParameters();
                psiRawFirBuilder3.removeLast(psiRawFirBuilder3.getContext().getFirFunctionTargets());
                psiRawFirBuilder3.additionalFunctionInit(firFunctionBuilder);
                FirFunction firFunctionMo6809build = firFunctionBuilder.mo6809build();
                PsiRawFirBuilder.this.bindFunctionTarget(firFunctionTarget, firFunctionMo6809build);
                if (firFunctionMo6809build instanceof FirSimpleFunction) {
                    fillDanglingConstraintsTo(function, firFunctionMo6809build);
                }
                if (!(firFunctionMo6809build instanceof FirAnonymousFunction)) {
                    return firFunctionMo6809build;
                }
                FirAnonymousFunctionExpressionBuilder firAnonymousFunctionExpressionBuilder = new FirAnonymousFunctionExpressionBuilder();
                firAnonymousFunctionExpressionBuilder.setSource(ktPsiSourceElement3);
                firAnonymousFunctionExpressionBuilder.setAnonymousFunction((FirAnonymousFunction) firFunctionMo6809build);
                return firAnonymousFunctionExpressionBuilder.mo6809build();
            } catch (Throwable th) {
                psiRawFirBuilder4.getContext().popFirTypeParameters();
                throw th;
            }
        }

        private final FirContractDescription obtainContractDescription(KtDeclarationWithBody ktDeclarationWithBody) throws Throwable {
            KtContractEffectList contractDescription = ktDeclarationWithBody.getContractDescription();
            if (contractDescription == null) {
                return null;
            }
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirRawContractDescriptionBuilder firRawContractDescriptionBuilder = new FirRawContractDescriptionBuilder();
            firRawContractDescriptionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, contractDescription, null, 1, null));
            extractRawEffects(contractDescription, firRawContractDescriptionBuilder.getRawEffects());
            return firRawContractDescriptionBuilder.build();
        }

        private final void extractRawEffects(KtContractEffectList ktContractEffectList, List<FirExpression> list) throws Throwable {
            Object objInvoke;
            List<KtContractEffect> contractEffects = KtContractEffectListKt.getContractEffects(ktContractEffectList);
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            for (KtContractEffect ktContractEffect : contractEffects) {
                List<FirExpression> list2 = list;
                PsiRawFirBuilder$Visitor$buildOrLazyExpression$1 psiRawFirBuilder$Visitor$buildOrLazyExpression$1 = new PsiRawFirBuilder$Visitor$buildOrLazyExpression$1(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktContractEffect, null, 1, null));
                int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i == 1) {
                    Object objAccept = KtContractEffectKt.getExpression(ktContractEffect).accept(this, null);
                    Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                    objInvoke = (FirExpression) objAccept;
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                    if (i2 == 1) {
                        objInvoke = psiRawFirBuilder$Visitor$buildOrLazyExpression$1.invoke();
                    } else {
                        if (i2 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        objInvoke = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyExpression$1));
                    }
                }
                list2.add((FirExpression) objInvoke);
            }
        }

        /* JADX WARN: Code duplicated, block: B:19:0x00d7  */
        /* JADX WARN: Code duplicated, block: B:29:0x0141  */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v20, types: [T, java.lang.Object, org.jetbrains.kotlin.fir.FirFunctionTarget] */
        /* JADX WARN: Type inference failed for: r0v49 */
        /* JADX WARN: Type inference failed for: r0v53 */
        /* JADX WARN: Type inference failed for: r0v58 */
        /* JADX WARN: Type inference failed for: r11v0 */
        /* JADX WARN: Type inference failed for: r11v1, types: [java.lang.Object, org.jetbrains.kotlin.KtFakeSourceElementKind, org.jetbrains.kotlin.fir.FirElement] */
        /* JADX WARN: Type inference failed for: r11v4 */
        /* JADX WARN: Type inference failed for: r1v27 */
        /* JADX WARN: Type inference failed for: r1v31 */
        /* JADX WARN: Type inference failed for: r1v32 */
        /* JADX WARN: Type inference failed for: r25v0, types: [org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor] */
        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitLambdaExpression(KtLambdaExpression expression, FirElement data) {
            int i;
            FirSingleExpressionBlock firSingleExpressionBlockMo6809build;
            KtFakeSourceElement ktFakeSourceElement;
            FirImplicitTypeRefImplWithoutSource firImplicitTypeRefImplWithoutSource;
            FirValueParameter firValueParameterConvertValueParameter$default;
            ?? r0;
            FirImplicitTypeRefImplWithoutSource firImplicitTypeRefImplWithoutSource2;
            ?? r1;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtFunctionLiteral functionLiteral = expression.getFunctionLiteral();
            Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
            ?? r11 = 0;
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, functionLiteral, null, 1, null);
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
            KtPsiSourceElement ktPsiSourceElement2 = ktPsiSourceElement;
            firAnonymousFunctionBuilder.setSource(ktPsiSourceElement2);
            firAnonymousFunctionBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firAnonymousFunctionBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firAnonymousFunctionBuilder.setReturnTypeRef(FirImplicitTypeRefImplWithoutSource.INSTANCE);
            firAnonymousFunctionBuilder.setReceiverParameter(ConversionUtilsKt.asReceiverParameter(ktPsiSourceElement2));
            firAnonymousFunctionBuilder.setSymbol(new FirAnonymousFunctionSymbol());
            firAnonymousFunctionBuilder.setLambda(true);
            boolean z = false;
            firAnonymousFunctionBuilder.setHasExplicitParameterList(expression.getFunctionLiteral().getArrow() != null);
            ArrayList arrayList = new ArrayList();
            for (KtParameter ktParameter : functionLiteral.getValueParameters()) {
                KtDestructuringDeclaration destructuringDeclaration = ktParameter.getDestructuringDeclaration();
                List<FirValueParameter> valueParameters = firAnonymousFunctionBuilder.getValueParameters();
                if (destructuringDeclaration != null) {
                    Name name2 = SpecialNames.DESTRUCT;
                    FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
                    Intrinsics.checkNotNull(ktParameter);
                    firValueParameterBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktParameter, r11, 1, r11));
                    firValueParameterBuilder.setContainingFunctionSymbol(firAnonymousFunctionBuilder.getSymbol());
                    firValueParameterBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
                    firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                    KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
                    if (ktTypeReferenceMo7396getTypeReference != null) {
                        FirElement firElementConvertElement = convertElement(ktTypeReferenceMo7396getTypeReference, r11);
                        if (!(firElementConvertElement instanceof FirTypeRef)) {
                            r1 = firElementConvertElement;
                            r1 = r11;
                        }
                        r1 = firElementConvertElement;
                        firImplicitTypeRefImplWithoutSource2 = (FirTypeRef) r1;
                        if (firImplicitTypeRefImplWithoutSource2 == null) {
                            firImplicitTypeRefImplWithoutSource2 = FirImplicitTypeRefImplWithoutSource.INSTANCE;
                        }
                    } else {
                        firImplicitTypeRefImplWithoutSource2 = FirImplicitTypeRefImplWithoutSource.INSTANCE;
                    }
                    firValueParameterBuilder.setReturnTypeRef(firImplicitTypeRefImplWithoutSource2);
                    firValueParameterBuilder.setName(name2);
                    firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(name2));
                    firValueParameterBuilder.setCrossinline(z);
                    firValueParameterBuilder.setNoinline(z);
                    firValueParameterBuilder.setVararg(z);
                    firValueParameterConvertValueParameter$default = firValueParameterBuilder.mo6809build();
                    CollectionsKt.addAll(arrayList, PsiConversionUtilsKt.generateDestructuringBlock(psiRawFirBuilder.getBaseModuleData(), destructuringDeclaration, firValueParameterConvertValueParameter$default, false, true, new Function2<KtAnnotated, FirAnnotationContainerBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitLambdaExpression$anonymousFunction$1$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(KtAnnotated ktAnnotated, FirAnnotationContainerBuilder firAnnotationContainerBuilder) {
                            invoke2(ktAnnotated, firAnnotationContainerBuilder);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(KtAnnotated generateDestructuringBlock, FirAnnotationContainerBuilder it) {
                            Intrinsics.checkNotNullParameter(generateDestructuringBlock, "$this$generateDestructuringBlock");
                            Intrinsics.checkNotNullParameter(it, "it");
                            this.this$0.extractAnnotationsTo(generateDestructuringBlock, it);
                        }
                    }, new Function1<KtTypeReference, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitLambdaExpression$anonymousFunction$1$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final FirTypeRef invoke(KtTypeReference ktTypeReference) {
                            return this.this$0.toFirOrImplicitType(ktTypeReference);
                        }
                    }).getStatements());
                } else {
                    KtTypeReference ktTypeReferenceMo7396getTypeReference2 = ktParameter.mo7396getTypeReference();
                    if (ktTypeReferenceMo7396getTypeReference2 != null) {
                        FirElement firElementConvertElement2 = convertElement(ktTypeReferenceMo7396getTypeReference2, r11);
                        if (!(firElementConvertElement2 instanceof FirImplicitTypeRefImplWithoutSource)) {
                            r0 = firElementConvertElement2;
                            r0 = r11;
                        }
                        r0 = firElementConvertElement2;
                        firImplicitTypeRefImplWithoutSource = (FirImplicitTypeRefImplWithoutSource) r0;
                        if (firImplicitTypeRefImplWithoutSource == null) {
                            firImplicitTypeRefImplWithoutSource = FirImplicitTypeRefImplWithoutSource.INSTANCE;
                        }
                    } else {
                        firImplicitTypeRefImplWithoutSource = FirImplicitTypeRefImplWithoutSource.INSTANCE;
                    }
                    Intrinsics.checkNotNull(ktParameter);
                    firValueParameterConvertValueParameter$default = convertValueParameter$default(this, ktParameter, firAnonymousFunctionBuilder.getSymbol(), firImplicitTypeRefImplWithoutSource, AbstractRawFirBuilder.ValueParameterDeclaration.LAMBDA, null, 16, null);
                }
                valueParameters.add(firValueParameterConvertValueParameter$default);
                z = false;
                r11 = 0;
            }
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            KtPsiSourceElement ktPsiSourceElement3 = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, expression, null, 1, null);
            FirLabel lastLabel = psiRawFirBuilder.getContext().getLastLabel(expression);
            if (lastLabel == null) {
                Name name3 = (Name) CollectionsKt.lastOrNull((List) psiRawFirBuilder.getContext().getCalleeNamesForLambda());
                if (name3 != null) {
                    FirLabelBuilder firLabelBuilder = new FirLabelBuilder();
                    firLabelBuilder.setSource(KtSourceElementKt.fakeElement$default(ktPsiSourceElement3, KtFakeSourceElementKind.GeneratedLambdaLabel.INSTANCE, 0, 0, 6, null));
                    String strAsString = name3.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    firLabelBuilder.setName(strAsString);
                    lastLabel = firLabelBuilder.build();
                } else {
                    lastLabel = null;
                }
            }
            firAnonymousFunctionBuilder.setLabel(lastLabel);
            FirLabel label = firAnonymousFunctionBuilder.getLabel();
            ?? firFunctionTarget = new FirFunctionTarget(label != null ? label.getName() : null, true);
            psiRawFirBuilder.getContext().getFirFunctionTargets().add(firFunctionTarget);
            objectRef.element = firFunctionTarget;
            KtBlockExpression bodyExpression = functionLiteral.getBodyExpression();
            if (bodyExpression == null) {
                firSingleExpressionBlockMo6809build = new FirSingleExpressionBlock(AbstractRawFirBuilder.toReturn$default(psiRawFirBuilder2, FirExpressionUtilKt.buildErrorExpression$default(firAnonymousFunctionBuilder.getSource(), new ConeSyntaxDiagnostic("Lambda has no body"), null, 4, null), null, null, false, 7, null));
            } else {
                FirBlockBuilder firBlockBuilderConfigureBlockWithoutBuilding = configureBlockWithoutBuilding(bodyExpression);
                FirStatement firStatement = (FirStatement) CollectionsKt.firstOrNull((List) firBlockBuilderConfigureBlockWithoutBuilding.getStatements());
                if (firStatement == null || !ConversionUtilsKt.isContractBlockFirCheck(firStatement)) {
                    i = 0;
                } else {
                    FirFunctionCall firFunctionCall = (FirFunctionCall) firStatement;
                    firAnonymousFunctionBuilder.setContractDescription(ConversionUtilsKt.toLegacyRawContractDescription(firFunctionCall));
                    List<FirStatement> statements = firBlockBuilderConfigureBlockWithoutBuilding.getStatements();
                    FirContractCallBlock firContractCallBlock = new FirContractCallBlock(firFunctionCall);
                    i = 0;
                    statements.set(0, firContractCallBlock);
                }
                if (firBlockBuilderConfigureBlockWithoutBuilding.getStatements().isEmpty()) {
                    List<FirStatement> statements2 = firBlockBuilderConfigureBlockWithoutBuilding.getStatements();
                    FirReturnExpressionBuilder firReturnExpressionBuilder = new FirReturnExpressionBuilder();
                    KtPsiSourceElement ktPsiSourceElement4 = ktPsiSourceElement3;
                    firReturnExpressionBuilder.setSource(KtSourceElementKt.fakeElement$default(ktPsiSourceElement4, KtFakeSourceElementKind.ImplicitReturn.FromExpressionBody.INSTANCE, 0, 0, 6, null));
                    firReturnExpressionBuilder.setTarget((FirTarget) objectRef.element);
                    FirUnitExpressionBuilder firUnitExpressionBuilder = new FirUnitExpressionBuilder();
                    firUnitExpressionBuilder.setSource(KtSourceElementKt.fakeElement$default(ktPsiSourceElement4, KtFakeSourceElementKind.ImplicitUnit.LambdaCoercion.INSTANCE, 0, 0, 6, null));
                    firReturnExpressionBuilder.setResult(firUnitExpressionBuilder.mo6809build());
                    statements2.add(firReturnExpressionBuilder.mo6809build());
                }
                firBlockBuilderConfigureBlockWithoutBuilding.getStatements().addAll(i, arrayList);
                firSingleExpressionBlockMo6809build = firBlockBuilderConfigureBlockWithoutBuilding.mo6809build();
            }
            firAnonymousFunctionBuilder.setBody(firSingleExpressionBlockMo6809build);
            psiRawFirBuilder.removeLast(psiRawFirBuilder.getContext().getFirFunctionTargets());
            FirAnonymousFunction firAnonymousFunctionMo6809build = firAnonymousFunctionBuilder.mo6809build();
            PsiRawFirBuilder.this.bindFunctionTarget((FirFunctionTarget) objectRef.element, firAnonymousFunctionMo6809build);
            FirAnonymousFunctionExpressionBuilder firAnonymousFunctionExpressionBuilder = new FirAnonymousFunctionExpressionBuilder();
            KtLambdaExpression ktLambdaExpression = expression;
            Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
            if (obj instanceof KtRealSourceElementKind) {
                ktFakeSourceElement = new KtRealPsiSourceElement(ktLambdaExpression);
            } else {
                if (!(obj instanceof KtFakeSourceElementKind)) {
                    throw new NoWhenBranchMatchedException();
                }
                ktFakeSourceElement = new KtFakeSourceElement(ktLambdaExpression, (KtFakeSourceElementKind) obj);
            }
            firAnonymousFunctionExpressionBuilder.setSource(ktFakeSourceElement);
            firAnonymousFunctionExpressionBuilder.setAnonymousFunction(firAnonymousFunctionMo6809build);
            return firAnonymousFunctionExpressionBuilder.mo6809build();
        }

        protected final FirConstructor toFirConstructor(KtSecondaryConstructor ktSecondaryConstructor, FirTypeRef delegatedTypeRef, FirTypeRef selfTypeRef, KtClassOrObject owner, List<? extends FirTypeParameterRef> ownerTypeParameters) throws Throwable {
            Object objConvert;
            Intrinsics.checkNotNullParameter(ktSecondaryConstructor, "<this>");
            Intrinsics.checkNotNullParameter(delegatedTypeRef, "delegatedTypeRef");
            Intrinsics.checkNotNullParameter(selfTypeRef, "selfTypeRef");
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(ownerTypeParameters, "ownerTypeParameters");
            boolean z = false;
            FirFunctionTarget firFunctionTarget = new FirFunctionTarget(null, false);
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirConstructorBuilder firConstructorBuilder = new FirConstructorBuilder();
            firConstructorBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktSecondaryConstructor, null, 1, null));
            firConstructorBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firConstructorBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firConstructorBuilder.setReturnTypeRef(selfTypeRef);
            KtSecondaryConstructor ktSecondaryConstructor2 = ktSecondaryConstructor;
            Visibility visibility = psiRawFirBuilder.getVisibility(ktSecondaryConstructor2);
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, Modality.FINAL);
            firDeclarationStatusImpl.setExpect(PsiUtilsKt.hasExpectModifier(ktSecondaryConstructor2) || psiRawFirBuilder.getContext().getContainerIsExpect());
            firDeclarationStatusImpl.setActual(PsiUtilsKt.hasActualModifier(ktSecondaryConstructor2));
            firDeclarationStatusImpl.setInner(owner.hasModifier(KtTokens.INNER_KEYWORD));
            if (owner.hasModifier(KtTokens.SEALED_KEYWORD) && visibility != Visibilities.Private.INSTANCE) {
                z = true;
            }
            firDeclarationStatusImpl.setFromSealedClass(z);
            firDeclarationStatusImpl.setFromEnumClass(owner.hasModifier(KtTokens.ENUM_KEYWORD));
            firConstructorBuilder.setStatus(firDeclarationStatusImpl);
            firConstructorBuilder.setDispatchReceiverType(obtainDispatchReceiverForConstructor(owner));
            firConstructorBuilder.getContextReceivers().addAll(convertContextReceivers(owner.getContextReceivers()));
            firConstructorBuilder.setSymbol(new FirConstructorSymbol(psiRawFirBuilder.callableIdForClassConstructor()));
            PsiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1 psiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1 = new PsiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1(ktSecondaryConstructor.isDelegatedCallToThis(), delegatedTypeRef);
            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
            if (i == 1) {
                objConvert = convert(ktSecondaryConstructor.getDelegationCall(), delegatedTypeRef);
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i2 == 1) {
                    objConvert = psiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1.invoke();
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    objConvert = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1));
                }
            }
            firConstructorBuilder.setDelegatedConstructor((FirDelegatedConstructorCall) objConvert);
            psiRawFirBuilder.getContext().getFirFunctionTargets().add(firFunctionTarget);
            extractAnnotationsTo(ktSecondaryConstructor, firConstructorBuilder);
            CollectionsKt.addAll(firConstructorBuilder.getTypeParameters(), psiRawFirBuilder.constructorTypeParametersFromConstructedClass(ownerTypeParameters));
            KtSecondaryConstructor ktSecondaryConstructor3 = ktSecondaryConstructor;
            extractValueParametersTo$default(this, ktSecondaryConstructor3, firConstructorBuilder, firConstructorBuilder.getSymbol(), AbstractRawFirBuilder.ValueParameterDeclaration.FUNCTION, null, null, 24, null);
            Pair<FirBlock, FirContractDescription> pairBuildFirBody = buildFirBody(ktSecondaryConstructor3);
            FirBlock firBlockComponent1 = pairBuildFirBody.component1();
            FirContractDescription firContractDescriptionComponent2 = pairBuildFirBody.component2();
            if (firContractDescriptionComponent2 != null) {
                firConstructorBuilder.setContractDescription(firContractDescriptionComponent2);
            }
            firConstructorBuilder.setBody(firBlockComponent1);
            psiRawFirBuilder.removeLast(psiRawFirBuilder.getContext().getFirFunctionTargets());
            FirConstructor firConstructorMo6809build = firConstructorBuilder.mo6809build();
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = psiRawFirBuilder2.currentDispatchReceiverType();
            Intrinsics.checkNotNull(coneClassLikeTypeCurrentDispatchReceiverType);
            ClassMembersKt.setContainingClassForStaticMemberAttr(firConstructorMo6809build, coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag());
            psiRawFirBuilder2.bindFunctionTarget(firFunctionTarget, firConstructorMo6809build);
            return firConstructorMo6809build;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x0061  */
        private final FirDelegatedConstructorCall convert(KtConstructorDelegationCall ktConstructorDelegationCall, FirTypeRef firTypeRef) throws Throwable {
            KtPsiSourceElement firSourceElement;
            KtPsiSourceElement ktPsiSourceElementFakeElement$default;
            FirSuperReference firSuperReferenceBuild;
            boolean zIsCallToThis = ktConstructorDelegationCall.isCallToThis();
            if (ktConstructorDelegationCall.isImplicit()) {
                firSourceElement = PsiRawFirBuilder.this.toFirSourceElement((PsiElement) ktConstructorDelegationCall, (KtFakeSourceElementKind) KtFakeSourceElementKind.ImplicitConstructor.INSTANCE);
            } else {
                firSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, ktConstructorDelegationCall, null, 1, null);
            }
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirDelegatedConstructorCallBuilder firDelegatedConstructorCallBuilder = new FirDelegatedConstructorCallBuilder();
            KtPsiSourceElement ktPsiSourceElement = firSourceElement;
            firDelegatedConstructorCallBuilder.setSource(ktPsiSourceElement);
            firDelegatedConstructorCallBuilder.setConstructedTypeRef(UtilsKt.copyWithNewSourceKind(firTypeRef, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE));
            firDelegatedConstructorCallBuilder.setThis(zIsCallToThis);
            KtFakeSourceElementKind ktFakeSourceElementKind = ktConstructorDelegationCall.isImplicit() ? KtFakeSourceElementKind.ImplicitConstructor.INSTANCE : KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE;
            KtConstructorDelegationReferenceExpression calleeExpression = ktConstructorDelegationCall.getCalleeExpression();
            if (calleeExpression != null) {
                Intrinsics.checkNotNull(calleeExpression);
                KtPsiSourceElement firSourceElement2 = psiRawFirBuilder.toFirSourceElement((PsiElement) calleeExpression, ktFakeSourceElementKind);
                if (firSourceElement2 != null) {
                    ktPsiSourceElementFakeElement$default = firSourceElement2;
                } else {
                    ktPsiSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(ktPsiSourceElement, ktFakeSourceElementKind, 0, 0, 6, null);
                }
            } else {
                ktPsiSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(ktPsiSourceElement, ktFakeSourceElementKind, 0, 0, 6, null);
            }
            if (zIsCallToThis) {
                FirExplicitThisReferenceBuilder firExplicitThisReferenceBuilder = new FirExplicitThisReferenceBuilder();
                firExplicitThisReferenceBuilder.setSource(ktPsiSourceElementFakeElement$default);
                firSuperReferenceBuild = firExplicitThisReferenceBuilder.build();
            } else {
                FirExplicitSuperReferenceBuilder firExplicitSuperReferenceBuilder = new FirExplicitSuperReferenceBuilder();
                firExplicitSuperReferenceBuilder.setSource(ktPsiSourceElementFakeElement$default);
                firExplicitSuperReferenceBuilder.setSuperTypeRef(firDelegatedConstructorCallBuilder.getConstructedTypeRef());
                firSuperReferenceBuild = firExplicitSuperReferenceBuilder.build();
            }
            firDelegatedConstructorCallBuilder.setCalleeReference(firSuperReferenceBuild);
            extractArgumentsTo(ktConstructorDelegationCall, firDelegatedConstructorCallBuilder);
            return firDelegatedConstructorCallBuilder.mo6809build();
        }

        private final FirExpression toInitializerExpression(KtDeclarationWithInitializer ktDeclarationWithInitializer) throws Throwable {
            Object firExpression$default;
            boolean zHasInitializer = ktDeclarationWithInitializer.hasInitializer();
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            if (!zHasInitializer) {
                return null;
            }
            psiRawFirBuilder.getContext().getCalleeNamesForLambda().add(null);
            PsiRawFirBuilder$Visitor$buildOrLazyExpression$1 psiRawFirBuilder$Visitor$buildOrLazyExpression$1 = new PsiRawFirBuilder$Visitor$buildOrLazyExpression$1(null);
            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
            if (i == 1) {
                firExpression$default = toFirExpression$default(this, ktDeclarationWithInitializer.getInitializer(), "Should have initializer", null, 2, null);
            } else if (i == 2) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i2 == 1) {
                    firExpression$default = psiRawFirBuilder$Visitor$buildOrLazyExpression$1.invoke();
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    firExpression$default = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyExpression$1));
                }
            } else {
                throw new NoWhenBranchMatchedException();
            }
            FirExpression firExpression = (FirExpression) firExpression$default;
            psiRawFirBuilder.removeLast(psiRawFirBuilder.getContext().getCalleeNamesForLambda());
            return firExpression;
        }

        private final <T> FirProperty toFirProperty(KtProperty ktProperty, FirClassSymbol<?> firClassSymbol, Context<T> context) throws Throwable {
            PsiRawFirBuilder psiRawFirBuilder;
            ArrayList arrayList;
            FirPropertyBuilder firPropertyBuilder;
            boolean z;
            Object obj;
            Object objInvoke;
            ArrayList arrayList2;
            FirTypeRef firOrImplicitType = toFirOrImplicitType(ktProperty.mo7396getTypeReference());
            Name nameAsSafeName = ktProperty.getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
            boolean zIsVar = ktProperty.isVar();
            FirExpression initializerExpression = toInitializerExpression(ktProperty);
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, ktProperty, null, 1, null);
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            FirPropertyBuilder firPropertyBuilder2 = new FirPropertyBuilder();
            KtPsiSourceElement ktPsiSourceElement2 = ktPsiSourceElement;
            firPropertyBuilder2.setSource(ktPsiSourceElement2);
            firPropertyBuilder2.setModuleData(psiRawFirBuilder2.getBaseModuleData());
            firPropertyBuilder2.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firPropertyBuilder2.setReturnTypeRef(firOrImplicitType);
            firPropertyBuilder2.setName(nameAsSafeName);
            firPropertyBuilder2.setVar(zIsVar);
            KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = ktProperty.mo7393getReceiverTypeReference();
            FirElement firElementConvertElement = ktTypeReferenceMo7393getReceiverTypeReference != null ? convertElement(ktTypeReferenceMo7393getReceiverTypeReference, null) : null;
            if (!(firElementConvertElement instanceof FirTypeRef)) {
                firElementConvertElement = null;
            }
            FirTypeRef firTypeRef = (FirTypeRef) firElementConvertElement;
            firPropertyBuilder2.setReceiverParameter(firTypeRef != null ? ConversionUtilsKt.convertToReceiverParameter(firTypeRef) : null);
            firPropertyBuilder2.setInitializer(initializerExpression);
            ArrayList arrayList3 = new ArrayList();
            for (KtAnnotationEntry ktAnnotationEntry : ktProperty.getAnnotationEntries()) {
                ArrayList arrayList4 = arrayList3;
                Intrinsics.checkNotNull(ktAnnotationEntry);
                FirElement firElementConvertElement2 = convertElement(ktAnnotationEntry, null);
                if (firElementConvertElement2 != null) {
                    arrayList4.add((FirAnnotationCall) firElementConvertElement2);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotationCall");
                }
            }
            if (ktProperty.isLocal()) {
                firPropertyBuilder2.setLocal(true);
                firPropertyBuilder2.setSymbol(new FirPropertySymbol(nameAsSafeName));
                extractTypeParametersTo(ktProperty, firPropertyBuilder2, firPropertyBuilder2.getSymbol());
                firPropertyBuilder2.setBackingField(toFirBackingField(ktProperty.getFieldDeclaration(), ktProperty, firPropertyBuilder2.getSymbol(), firOrImplicitType, CollectionsKt.emptyList()));
                FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL);
                firDeclarationStatusImpl.setLateInit(ktProperty.hasModifier(KtTokens.LATEINIT_KEYWORD));
                firPropertyBuilder2.setStatus(firDeclarationStatusImpl);
                if (ktProperty.hasDelegate()) {
                    FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder = new FirWrappedDelegateExpressionBuilder();
                    FirExpression firProperty$lambda$156$extractDelegateExpression = toFirProperty$lambda$156$extractDelegateExpression(this, ktProperty);
                    KtSourceElement source = firProperty$lambda$156$extractDelegateExpression.getSource();
                    firWrappedDelegateExpressionBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.WrappedDelegate.INSTANCE, 0, 0, 6, null) : null);
                    firWrappedDelegateExpressionBuilder.setExpression(firProperty$lambda$156$extractDelegateExpression);
                    ConversionUtilsKt.generateAccessorsByDelegate$default(firPropertyBuilder2, firWrappedDelegateExpressionBuilder, psiRawFirBuilder2.getBaseModuleData(), null, context, false, null, 32, null);
                }
                arrayList = arrayList3;
                firPropertyBuilder = firPropertyBuilder2;
            } else {
                firPropertyBuilder2.setLocal(false);
                firPropertyBuilder2.setSymbol(new FirPropertySymbol(psiRawFirBuilder2.callableIdForName(nameAsSafeName)));
                firPropertyBuilder2.setDispatchReceiverType(psiRawFirBuilder2.currentDispatchReceiverType());
                extractTypeParametersTo(ktProperty, firPropertyBuilder2, firPropertyBuilder2.getSymbol());
                PsiRawFirBuilder psiRawFirBuilder3 = psiRawFirBuilder2;
                psiRawFirBuilder3.addCapturedTypeParameters(true, ktPsiSourceElement2, firPropertyBuilder2.getTypeParameters());
                try {
                    KtBackingField fieldDeclaration = ktProperty.getFieldDeclaration();
                    FirPropertySymbol symbol = firPropertyBuilder2.getSymbol();
                    ArrayList arrayList5 = new ArrayList();
                    for (T t : arrayList3) {
                        FirAnnotationCall firAnnotationCall = (FirAnnotationCall) t;
                        if (firAnnotationCall.getUseSiteTarget() == AnnotationUseSiteTarget.FIELD || firAnnotationCall.getUseSiteTarget() == AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD) {
                            arrayList5.add(t);
                        }
                    }
                    psiRawFirBuilder = psiRawFirBuilder3;
                    try {
                        firPropertyBuilder2.setBackingField(toFirBackingField(fieldDeclaration, ktProperty, symbol, firOrImplicitType, arrayList5));
                        arrayList = arrayList3;
                        firPropertyBuilder = firPropertyBuilder2;
                        firPropertyBuilder.setGetter(convertPropertyAccessor(ktProperty.getGetter(), ktProperty, firOrImplicitType, firPropertyBuilder2.getSymbol(), true, ConversionUtilsKt.filterUseSiteTarget(arrayList3, AnnotationUseSiteTarget.PROPERTY_GETTER), CollectionsKt.emptyList()));
                        firPropertyBuilder.setSetter(convertPropertyAccessor(ktProperty.getSetter(), ktProperty, firOrImplicitType, firPropertyBuilder.getSymbol(), false, ConversionUtilsKt.filterUseSiteTarget(arrayList, AnnotationUseSiteTarget.PROPERTY_SETTER), ConversionUtilsKt.filterUseSiteTarget(arrayList, AnnotationUseSiteTarget.SETTER_PARAMETER)));
                        FirDeclarationStatusImpl firDeclarationStatusImpl2 = new FirDeclarationStatusImpl(psiRawFirBuilder2.getVisibility(ktProperty), psiRawFirBuilder2.getModality(ktProperty));
                        firDeclarationStatusImpl2.setExpect(PsiUtilsKt.hasExpectModifier(ktProperty) || psiRawFirBuilder2.getContext().getContainerIsExpect());
                        firDeclarationStatusImpl2.setActual(PsiUtilsKt.hasActualModifier(ktProperty));
                        firDeclarationStatusImpl2.setOverride(ktProperty.hasModifier(KtTokens.OVERRIDE_KEYWORD));
                        firDeclarationStatusImpl2.setConst(ktProperty.hasModifier(KtTokens.CONST_KEYWORD));
                        firDeclarationStatusImpl2.setLateInit(ktProperty.hasModifier(KtTokens.LATEINIT_KEYWORD));
                        firDeclarationStatusImpl2.setExternal(ktProperty.hasModifier(KtTokens.EXTERNAL_KEYWORD));
                        firPropertyBuilder.setStatus(firDeclarationStatusImpl2);
                        if (ktProperty.hasDelegate()) {
                            final FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder2 = new FirWrappedDelegateExpressionBuilder();
                            FirExpression firProperty$lambda$156$lambda$154$extractDelegateExpression$151 = toFirProperty$lambda$156$lambda$154$extractDelegateExpression$151(this, psiRawFirBuilder2, ktProperty);
                            KtSourceElement source2 = firProperty$lambda$156$lambda$154$extractDelegateExpression$151.getSource();
                            firWrappedDelegateExpressionBuilder2.setSource(source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.WrappedDelegate.INSTANCE, 0, 0, 6, null) : null);
                            firWrappedDelegateExpressionBuilder2.setExpression(firProperty$lambda$156$lambda$154$extractDelegateExpression$151);
                            Function0<FirLazyExpression> function0 = new Function0<FirLazyExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$toFirProperty$3$2$lazyDelegateExpression$2
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final FirLazyExpression invoke() {
                                    FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder3 = firWrappedDelegateExpressionBuilder2;
                                    FirLazyExpressionBuilder firLazyExpressionBuilder = new FirLazyExpressionBuilder();
                                    firLazyExpressionBuilder.setSource(firWrappedDelegateExpressionBuilder3.getSource());
                                    return firLazyExpressionBuilder.mo6809build();
                                }
                            };
                            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                            if (i == 1) {
                                z = true;
                                obj = null;
                            } else {
                                if (i != 2) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                                z = true;
                                if (i2 == 1) {
                                    objInvoke = function0.invoke();
                                } else {
                                    if (i2 != 2) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    objInvoke = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(function0));
                                }
                                obj = objInvoke;
                            }
                            ConversionUtilsKt.generateAccessorsByDelegate(firPropertyBuilder, firWrappedDelegateExpressionBuilder2, psiRawFirBuilder2.getBaseModuleData(), firClassSymbol, context, ktProperty.mo7393getReceiverTypeReference() != null ? z : false, (FirLazyExpression) obj);
                        }
                        Unit unit = Unit.INSTANCE;
                        psiRawFirBuilder.getContext().popFirTypeParameters();
                    } catch (Throwable th) {
                        th = th;
                        psiRawFirBuilder.getContext().popFirTypeParameters();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    psiRawFirBuilder = psiRawFirBuilder3;
                }
            }
            List<FirAnnotation> annotations = firPropertyBuilder.getAnnotations();
            if (firPropertyBuilder.isLocal()) {
                arrayList2 = arrayList;
            } else {
                ArrayList arrayList6 = new ArrayList();
                for (T t2 : arrayList) {
                    FirAnnotationCall firAnnotationCall2 = (FirAnnotationCall) t2;
                    if (firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.FIELD && firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD && firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.PROPERTY_GETTER && (!zIsVar || (firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.SETTER_PARAMETER && firAnnotationCall2.getUseSiteTarget() != AnnotationUseSiteTarget.PROPERTY_SETTER))) {
                        arrayList6.add(t2);
                    }
                }
                arrayList2 = arrayList6;
            }
            CollectionsKt.addAll(annotations, arrayList2);
            List<FirContextReceiver> contextReceivers = firPropertyBuilder.getContextReceivers();
            List<KtContextReceiver> contextReceivers2 = ktProperty.getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers2, "getContextReceivers(...)");
            contextReceivers.addAll(convertContextReceivers(contextReceivers2));
            psiRawFirBuilder2.additionalPropertyInit(firPropertyBuilder);
            FirProperty firPropertyMo6809build = firPropertyBuilder.mo6809build();
            if (!ktProperty.isLocal()) {
                fillDanglingConstraintsTo(ktProperty, firPropertyMo6809build);
            }
            return firPropertyMo6809build;
        }

        private static final FirExpression toFirProperty$lambda$156$extractDelegateExpression(Visitor visitor, KtProperty ktProperty) {
            KtPropertyDelegate delegate = ktProperty.getDelegate();
            return toFirExpression$default(visitor, delegate != null ? delegate.getExpression() : null, "Incorrect delegate expression", null, 2, null);
        }

        private static final FirExpression toFirProperty$lambda$156$lambda$154$extractDelegateExpression$151(Visitor visitor, PsiRawFirBuilder psiRawFirBuilder, KtProperty ktProperty) throws Throwable {
            Object objMo6809build;
            KtExpression expression;
            PsiRawFirBuilder$Visitor$buildOrLazyExpression$1 psiRawFirBuilder$Visitor$buildOrLazyExpression$1 = new PsiRawFirBuilder$Visitor$buildOrLazyExpression$1(psiRawFirBuilder.toFirSourceElement((PsiElement) ktProperty, (KtFakeSourceElementKind) KtFakeSourceElementKind.WrappedDelegate.INSTANCE));
            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
            if (i == 1) {
                KtPropertyDelegate delegate = ktProperty.getDelegate();
                if (delegate == null || (expression = delegate.getExpression()) == null || (objMo6809build = toFirExpression$default(visitor, expression, "Should have delegate", null, 2, null)) == null) {
                    FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                    firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("Should have delegate", DiagnosticKind.ExpressionExpected));
                    objMo6809build = firErrorExpressionBuilder.mo6809build();
                }
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i2 == 1) {
                    objMo6809build = psiRawFirBuilder$Visitor$buildOrLazyExpression$1.invoke();
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    objMo6809build = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyExpression$1));
                }
            }
            return (FirExpression) objMo6809build;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitAnonymousInitializer(KtAnonymousInitializer initializer, FirElement data) throws Throwable {
            Object firBlock;
            Intrinsics.checkNotNullParameter(initializer, "initializer");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirAnonymousInitializerBuilder firAnonymousInitializerBuilder = new FirAnonymousInitializerBuilder();
            firAnonymousInitializerBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, initializer, null, 1, null));
            firAnonymousInitializerBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firAnonymousInitializerBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            PsiRawFirBuilder$Visitor$buildOrLazyBlock$1 psiRawFirBuilder$Visitor$buildOrLazyBlock$1 = PsiRawFirBuilder$Visitor$buildOrLazyBlock$1.INSTANCE;
            int i = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
            if (i == 1) {
                firBlock = toFirBlock(initializer.getBody());
            } else if (i == 2) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[PsiRawFirBuilder.this.getMode().ordinal()];
                if (i2 == 1) {
                    firBlock = psiRawFirBuilder$Visitor$buildOrLazyBlock$1.invoke();
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    firBlock = AstLoadingFilter.disallowTreeLoading(new PsiRawFirBuilder$runOnStubs$1(psiRawFirBuilder$Visitor$buildOrLazyBlock$1));
                }
            } else {
                throw new NoWhenBranchMatchedException();
            }
            firAnonymousInitializerBuilder.setBody((FirBlock) firBlock);
            firAnonymousInitializerBuilder.setDispatchReceiverType((ConeClassLikeType) CollectionsKt.lastOrNull((List) psiRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
            return firAnonymousInitializerBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitProperty(KtProperty property, FirElement data) {
            Intrinsics.checkNotNullParameter(property, "property");
            return toFirProperty(property, null, PsiRawFirBuilder.this.getContext());
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitTypeReference(KtTypeReference typeReference, FirElement data) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilderConvertKtTypeElement;
            Intrinsics.checkNotNullParameter(typeReference, "typeReference");
            KtTypeElement typeElement = typeReference.getTypeElement();
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, typeReference, null, 1, null);
            boolean z = typeElement instanceof KtNullableType;
            KtDeclarationModifierList[] ktDeclarationModifierListArrVisitTypeReference$getAllModifierLists = visitTypeReference$getAllModifierLists(typeReference);
            List listMutableListOf = CollectionsKt.mutableListOf(Arrays.copyOf(ktDeclarationModifierListArrVisitTypeReference$getAllModifierLists, ktDeclarationModifierListArrVisitTypeReference$getAllModifierLists.length));
            KtTypeElement ktTypeElementVisitTypeReference$unwrapNullable = visitTypeReference$unwrapNullable(typeElement, listMutableListOf);
            if (ktTypeElementVisitTypeReference$unwrapNullable instanceof KtDynamicType) {
                FirDynamicTypeRefBuilder firDynamicTypeRefBuilder = new FirDynamicTypeRefBuilder();
                firDynamicTypeRefBuilder.setSource(ktPsiSourceElement);
                firDynamicTypeRefBuilder.setMarkedNullable(z);
                firErrorTypeRefBuilderConvertKtTypeElement = firDynamicTypeRefBuilder;
            } else {
                boolean z2 = false;
                if (ktTypeElementVisitTypeReference$unwrapNullable instanceof KtUserType) {
                    KtUserType ktUserType = (KtUserType) ktTypeElementVisitTypeReference$unwrapNullable;
                    KtSimpleNameExpression referenceExpression = ktUserType.getReferenceExpression();
                    if (referenceExpression != null) {
                        firErrorTypeRefBuilderConvertKtTypeElement = convertKtTypeElement(ktPsiSourceElement, z, ktUserType, referenceExpression);
                    } else {
                        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                        PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                        firErrorTypeRefBuilder.setSource(ktPsiSourceElement);
                        firErrorTypeRefBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Incomplete user type"));
                        KtUserType qualifier = ktUserType.getQualifier();
                        KtSimpleNameExpression referenceExpression2 = qualifier != null ? qualifier.getReferenceExpression() : null;
                        if (qualifier != null && referenceExpression2 != null) {
                            firErrorTypeRefBuilder.setPartiallyResolvedTypeRef(convertKtTypeElement((KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, qualifier, null, 1, null), false, qualifier, referenceExpression2).mo6809build());
                        }
                        firErrorTypeRefBuilderConvertKtTypeElement = firErrorTypeRefBuilder;
                    }
                } else if (ktTypeElementVisitTypeReference$unwrapNullable instanceof KtFunctionType) {
                    FirFunctionTypeRefBuilder firFunctionTypeRefBuilder = new FirFunctionTypeRefBuilder();
                    PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                    firFunctionTypeRefBuilder.setSource(ktPsiSourceElement);
                    firFunctionTypeRefBuilder.setMarkedNullable(z);
                    List list = listMutableListOf;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            if (PsiUtilsKt.hasSuspendModifier((KtModifierList) it.next())) {
                                z2 = true;
                                break;
                            }
                        }
                    }
                    firFunctionTypeRefBuilder.setSuspend(z2);
                    KtFunctionType ktFunctionType = (KtFunctionType) ktTypeElementVisitTypeReference$unwrapNullable;
                    KtTypeReference receiverTypeReference = ktFunctionType.getReceiverTypeReference();
                    FirElement firElementConvertElement = receiverTypeReference != null ? convertElement(receiverTypeReference, null) : null;
                    if (!(firElementConvertElement instanceof FirTypeRef)) {
                        firElementConvertElement = null;
                    }
                    firFunctionTypeRefBuilder.setReceiverTypeRef((FirTypeRef) firElementConvertElement);
                    firFunctionTypeRefBuilder.setReturnTypeRef(toFirOrErrorType(ktFunctionType.getReturnTypeReference()));
                    for (KtParameter ktParameter : ktFunctionType.getParameters()) {
                        List<FirFunctionTypeParameter> parameters = firFunctionTypeRefBuilder.getParameters();
                        FirFunctionTypeParameterBuilder firFunctionTypeParameterBuilder = new FirFunctionTypeParameterBuilder();
                        Intrinsics.checkNotNull(ktParameter);
                        firFunctionTypeParameterBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, ktParameter, null, 1, null));
                        firFunctionTypeParameterBuilder.setName(ktParameter.getNameAsName());
                        firFunctionTypeParameterBuilder.setReturnTypeRef(ktParameter.mo7396getTypeReference() != null ? toFirOrErrorType(ktParameter.mo7396getTypeReference()) : psiRawFirBuilder2.createNoTypeForParameterTypeRef());
                        parameters.add(firFunctionTypeParameterBuilder.build());
                    }
                    List<FirTypeRef> contextReceiverTypeRefs = firFunctionTypeRefBuilder.getContextReceiverTypeRefs();
                    List<KtTypeReference> contextReceiversTypeReferences = ktFunctionType.getContextReceiversTypeReferences();
                    Intrinsics.checkNotNullExpressionValue(contextReceiversTypeReferences, "getContextReceiversTypeReferences(...)");
                    ArrayList arrayList = new ArrayList();
                    for (KtTypeReference ktTypeReference : contextReceiversTypeReferences) {
                        FirElement firElementConvertElement2 = ktTypeReference != null ? convertElement(ktTypeReference, null) : null;
                        if (!(firElementConvertElement2 instanceof FirTypeRef)) {
                            firElementConvertElement2 = null;
                        }
                        FirTypeRef firTypeRef = (FirTypeRef) firElementConvertElement2;
                        if (firTypeRef != null) {
                            arrayList.add(firTypeRef);
                        }
                    }
                    contextReceiverTypeRefs.addAll(arrayList);
                    firErrorTypeRefBuilderConvertKtTypeElement = firFunctionTypeRefBuilder;
                } else if (ktTypeElementVisitTypeReference$unwrapNullable instanceof KtIntersectionType) {
                    FirIntersectionTypeRefBuilder firIntersectionTypeRefBuilder = new FirIntersectionTypeRefBuilder();
                    firIntersectionTypeRefBuilder.setSource(ktPsiSourceElement);
                    firIntersectionTypeRefBuilder.setMarkedNullable(z);
                    KtIntersectionType ktIntersectionType = (KtIntersectionType) ktTypeElementVisitTypeReference$unwrapNullable;
                    firIntersectionTypeRefBuilder.setLeftType(toFirOrErrorType(ktIntersectionType.getLeftTypeRef()));
                    firIntersectionTypeRefBuilder.setRightType(toFirOrErrorType(ktIntersectionType.getRightTypeRef()));
                    firErrorTypeRefBuilderConvertKtTypeElement = firIntersectionTypeRefBuilder;
                } else if (ktTypeElementVisitTypeReference$unwrapNullable == null) {
                    FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
                    firErrorTypeRefBuilder2.setSource(ktPsiSourceElement);
                    firErrorTypeRefBuilder2.setDiagnostic(new ConeSyntaxDiagnostic("Incomplete code"));
                    firErrorTypeRefBuilderConvertKtTypeElement = firErrorTypeRefBuilder2;
                } else {
                    throw new AssertionError("Unexpected type element: " + ktTypeElementVisitTypeReference$unwrapNullable.getText());
                }
            }
            Iterator it2 = listMutableListOf.iterator();
            while (it2.hasNext()) {
                for (KtAnnotationEntry ktAnnotationEntry : ((KtModifierList) it2.next()).getAnnotationEntries()) {
                    List<FirAnnotation> annotations = firErrorTypeRefBuilderConvertKtTypeElement.getAnnotations();
                    Intrinsics.checkNotNull(ktAnnotationEntry);
                    FirElement firElementConvertElement3 = convertElement(ktAnnotationEntry, null);
                    if (firElementConvertElement3 != null) {
                        annotations.add((FirAnnotation) firElementConvertElement3);
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotation");
                    }
                }
            }
            return firErrorTypeRefBuilderConvertKtTypeElement.mo6809build();
        }

        private static final KtDeclarationModifierList[] visitTypeReference$getAllModifierLists(KtElementImplStub<?> ktElementImplStub) {
            PsiElement[] stubOrPsiChildren = ktElementImplStub.getStubOrPsiChildren(KtStubElementTypes.MODIFIER_LIST, KtStubElementTypes.MODIFIER_LIST.getArrayFactory());
            Intrinsics.checkNotNullExpressionValue(stubOrPsiChildren, "getStubOrPsiChildren(...)");
            return (KtDeclarationModifierList[]) stubOrPsiChildren;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static final KtTypeElement visitTypeReference$unwrapNullable(KtTypeElement ktTypeElement, List<KtModifierList> list) {
            if (!(ktTypeElement instanceof KtNullableType)) {
                return ktTypeElement;
            }
            CollectionsKt.addAll(list, visitTypeReference$getAllModifierLists((KtElementImplStub) ktTypeElement));
            return visitTypeReference$unwrapNullable(((KtNullableType) ktTypeElement).getInnerType(), list);
        }

        private final FirUserTypeRefBuilder convertKtTypeElement(KtPsiSourceElement source, boolean isNullable, KtUserType ktUserType, KtSimpleNameExpression reference) {
            KtFakeSourceElement ktFakeSourceElement;
            KtTypeArgumentList typeArgumentList;
            FirUserTypeRefBuilder firUserTypeRefBuilder = new FirUserTypeRefBuilder();
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            firUserTypeRefBuilder.setSource(source);
            firUserTypeRefBuilder.setMarkedNullable(isNullable);
            do {
                Intrinsics.checkNotNull(reference);
                KtSourceElement firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, reference, null, 1, null);
                Name referencedNameAsName = reference.getReferencedNameAsName();
                if (ktUserType == null || (typeArgumentList = ktUserType.getTypeArgumentList()) == null) {
                    ktFakeSourceElement = source;
                } else {
                    Intrinsics.checkNotNull(typeArgumentList);
                    KtTypeArgumentList ktTypeArgumentList = typeArgumentList;
                    Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                    if (obj instanceof KtRealSourceElementKind) {
                        ktFakeSourceElement = new KtRealPsiSourceElement(ktTypeArgumentList);
                    } else {
                        if (!(obj instanceof KtFakeSourceElementKind)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        ktFakeSourceElement = new KtFakeSourceElement(ktTypeArgumentList, (KtFakeSourceElementKind) obj);
                    }
                }
                FirTypeArgumentListImpl firTypeArgumentListImpl = new FirTypeArgumentListImpl(ktFakeSourceElement);
                List<FirTypeProjection> typeArguments = firTypeArgumentListImpl.getTypeArguments();
                Intrinsics.checkNotNull(ktUserType);
                List<KtTypeProjection> typeArguments2 = ktUserType.getTypeArguments();
                Intrinsics.checkNotNullExpressionValue(typeArguments2, "getTypeArguments(...)");
                appendTypeArguments(typeArguments, typeArguments2);
                Unit unit = Unit.INSTANCE;
                firUserTypeRefBuilder.getQualifier().add(new FirQualifierPartImpl(firSourceElement$default, referencedNameAsName, firTypeArgumentListImpl));
                ktUserType = ktUserType.getQualifier();
                reference = ktUserType != null ? ktUserType.getReferenceExpression() : null;
            } while (reference != null);
            CollectionsKt.reverse(firUserTypeRefBuilder.getQualifier());
            return firUserTypeRefBuilder;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitAnnotationEntry(KtAnnotationEntry annotationEntry, FirElement data) throws Throwable {
            Name nameSpecial;
            KtSimpleNameExpression referenceExpression;
            List<FirQualifierPart> qualifier;
            FirQualifierPart firQualifierPart;
            Intrinsics.checkNotNullParameter(annotationEntry, "annotationEntry");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirAnnotationCallBuilder firAnnotationCallBuilder = new FirAnnotationCallBuilder();
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            KtPsiSourceElement ktPsiSourceElement = null;
            firAnnotationCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, annotationEntry, null, 1, null));
            KtAnnotationUseSiteTarget useSiteTarget = annotationEntry.getUseSiteTarget();
            firAnnotationCallBuilder.setUseSiteTarget(useSiteTarget != null ? useSiteTarget.getAnnotationUseSiteTarget() : null);
            firAnnotationCallBuilder.setAnnotationTypeRef(toFirOrErrorType(annotationEntry.getTypeReference()));
            extractArgumentsTo(annotationEntry, firAnnotationCallBuilder);
            FirTypeRef annotationTypeRef = firAnnotationCallBuilder.getAnnotationTypeRef();
            FirUserTypeRef firUserTypeRef = annotationTypeRef instanceof FirUserTypeRef ? (FirUserTypeRef) annotationTypeRef : null;
            if (firUserTypeRef == null || (qualifier = firUserTypeRef.getQualifier()) == null || (firQualifierPart = (FirQualifierPart) CollectionsKt.last((List) qualifier)) == null || (nameSpecial = firQualifierPart.getName()) == null) {
                nameSpecial = Name.special("<no-annotation-name>");
                Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            }
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
            KtTypeReference typeReference = annotationEntry.getTypeReference();
            KtTypeElement typeElement = typeReference != null ? typeReference.getTypeElement() : null;
            KtUserType ktUserType = typeElement instanceof KtUserType ? (KtUserType) typeElement : null;
            if (ktUserType != null && (referenceExpression = ktUserType.getReferenceExpression()) != null) {
                ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, referenceExpression, null, 1, null);
            }
            firSimpleNamedReferenceBuilder.setSource(ktPsiSourceElement);
            firSimpleNamedReferenceBuilder.setName(nameSpecial);
            firAnnotationCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
            List<FirTypeProjection> typeArguments = firAnnotationCallBuilder.getTypeArguments();
            List<KtTypeProjection> typeArguments2 = annotationEntry.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments2, "getTypeArguments(...)");
            appendTypeArguments(typeArguments, typeArguments2);
            return firAnnotationCallBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitTypeProjection(KtTypeProjection typeProjection, FirElement data) {
            Variance variance;
            Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
            KtProjectionKind projectionKind = typeProjection.getProjectionKind();
            Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, typeProjection, null, 1, null);
            if (projectionKind != KtProjectionKind.STAR) {
                PsiElement parent = typeProjection.getLightParent();
                KtTypeArgumentList ktTypeArgumentList = parent instanceof KtTypeArgumentList ? (KtTypeArgumentList) parent : null;
                KtTypeReference typeReference = typeProjection.getTypeReference();
                if (!((ktTypeArgumentList != null ? ktTypeArgumentList.getLightParent() : null) instanceof KtCallExpression) || typeReference == null || !typeReference.isPlaceholder()) {
                    FirTypeRef firOrErrorType = toFirOrErrorType(typeReference);
                    FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
                    firTypeProjectionWithVarianceBuilder.setSource(ktPsiSourceElement);
                    firTypeProjectionWithVarianceBuilder.setTypeRef(firOrErrorType);
                    int i = WhenMappings.$EnumSwitchMapping$1[projectionKind.ordinal()];
                    if (i == 1) {
                        variance = Variance.IN_VARIANCE;
                    } else if (i == 2) {
                        variance = Variance.OUT_VARIANCE;
                    } else {
                        if (i != 3) {
                            if (i != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                            throw null;
                        }
                        variance = Variance.INVARIANT;
                    }
                    firTypeProjectionWithVarianceBuilder.setVariance(variance);
                    return firTypeProjectionWithVarianceBuilder.build();
                }
                FirPlaceholderProjectionBuilder firPlaceholderProjectionBuilder = new FirPlaceholderProjectionBuilder();
                firPlaceholderProjectionBuilder.setSource(ktPsiSourceElement);
                return firPlaceholderProjectionBuilder.build();
            }
            FirStarProjectionBuilder firStarProjectionBuilder = new FirStarProjectionBuilder();
            firStarProjectionBuilder.setSource(ktPsiSourceElement);
            return firStarProjectionBuilder.build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitBlockExpression(KtBlockExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return configureBlockWithoutBuilding(expression).mo6809build();
        }

        /* JADX WARN: Code duplicated, block: B:18:0x0088  */
        private final FirBlockBuilder configureBlockWithoutBuilding(KtBlockExpression expression) {
            FirErrorExpression firErrorExpressionMo6809build;
            boolean z;
            FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
            firBlockBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null));
            for (KtExpression ktExpression : expression.getStatements()) {
                Intrinsics.checkNotNull(ktExpression);
                FirElement firElementConvertElement = convertElement(ktExpression, null);
                if (firElementConvertElement instanceof FirStatement) {
                    firErrorExpressionMo6809build = (FirStatement) firElementConvertElement;
                } else {
                    PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                    FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                    firErrorExpressionBuilder.setNonExpressionElement(firElementConvertElement);
                    firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Statement expected: " + ktExpression.getText()));
                    firErrorExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, ktExpression, null, 1, null));
                    firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
                }
                boolean z2 = firErrorExpressionMo6809build instanceof FirBlock;
                if (z2) {
                    KtSourceElement source = firErrorExpressionMo6809build.getSource();
                    if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE)) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else {
                    z = false;
                }
                if (!z2 || z || !firErrorExpressionMo6809build.getAnnotations().isEmpty()) {
                    firBlockBuilder.getStatements().add(firErrorExpressionMo6809build);
                } else {
                    CollectionsKt.addAll(firBlockBuilder.getStatements(), ((FirBlock) firErrorExpressionMo6809build).getStatements());
                }
            }
            return firBlockBuilder;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitSimpleNameExpression(KtSimpleNameExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            KtSimpleNameExpression parent = KtPsiUtilKt.getQualifiedExpressionForSelector(expression) != null ? expression.getLightParent() : expression;
            Intrinsics.checkNotNull(parent);
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, parent, null, 1, null);
            KtPsiSourceElement ktPsiSourceElement2 = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null);
            String text = expression.getReferencedNameElement().getNode().getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            return ConversionUtilsKt.generateAccessExpression(ktPsiSourceElement, ktPsiSourceElement2, expression.getReferencedNameAsName(), ConversionUtilsKt.isUnderscore(text) ? new ConeUnderscoreUsageWithoutBackticks(ktPsiSourceElement2) : null);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitConstantExpression(KtConstantExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return PsiRawFirBuilder.this.generateConstantExpressionByLiteral(expression);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitStringTemplateExpression(KtStringTemplateExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            KtStringTemplateEntry[] entries = expression.getEntries();
            Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
            return psiRawFirBuilder.toInterpolatingCall(entries, expression, new Function1<PsiElement, IElementType>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitStringTemplateExpression$1
                @Override // kotlin.jvm.functions.Function1
                public final IElementType invoke(PsiElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    if (element instanceof KtLiteralStringTemplateEntry) {
                        IElementType LITERAL_STRING_TEMPLATE_ENTRY = KtNodeTypes.LITERAL_STRING_TEMPLATE_ENTRY;
                        Intrinsics.checkNotNullExpressionValue(LITERAL_STRING_TEMPLATE_ENTRY, "LITERAL_STRING_TEMPLATE_ENTRY");
                        return LITERAL_STRING_TEMPLATE_ENTRY;
                    }
                    if (element instanceof KtEscapeStringTemplateEntry) {
                        IElementType ESCAPE_STRING_TEMPLATE_ENTRY = KtNodeTypes.ESCAPE_STRING_TEMPLATE_ENTRY;
                        Intrinsics.checkNotNullExpressionValue(ESCAPE_STRING_TEMPLATE_ENTRY, "ESCAPE_STRING_TEMPLATE_ENTRY");
                        return ESCAPE_STRING_TEMPLATE_ENTRY;
                    }
                    if (element instanceof KtSimpleNameStringTemplateEntry) {
                        IElementType SHORT_STRING_TEMPLATE_ENTRY = KtNodeTypes.SHORT_STRING_TEMPLATE_ENTRY;
                        Intrinsics.checkNotNullExpressionValue(SHORT_STRING_TEMPLATE_ENTRY, "SHORT_STRING_TEMPLATE_ENTRY");
                        return SHORT_STRING_TEMPLATE_ENTRY;
                    }
                    if (element instanceof KtBlockStringTemplateEntry) {
                        IElementType LONG_STRING_TEMPLATE_ENTRY = KtNodeTypes.LONG_STRING_TEMPLATE_ENTRY;
                        Intrinsics.checkNotNullExpressionValue(LONG_STRING_TEMPLATE_ENTRY, "LONG_STRING_TEMPLATE_ENTRY");
                        return LONG_STRING_TEMPLATE_ENTRY;
                    }
                    KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("invalid node type " + Reflection.getOrCreateKotlinClass(element.getClass()), null);
                    ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                    ExceptionAttachementBuilderUtilsKt.withPsiEntry(exceptionAttachmentBuilder, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, element);
                    kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                    throw kotlinIllegalArgumentExceptionWithAttachments;
                }
            }, new Function2<PsiElement, String, FirExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitStringTemplateExpression$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final FirExpression invoke(PsiElement psiElement, String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    PsiRawFirBuilder.Visitor visitor = this.this$0;
                    Intrinsics.checkNotNull(psiElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression");
                    return PsiRawFirBuilder.Visitor.toFirExpression$default(visitor, ((KtStringTemplateEntryWithExpression) psiElement).getExpression(), it, null, 2, null);
                }
            });
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitReturnExpression(KtReturnExpression expression, FirElement data) {
            FirExpression firExpressionMo6809build;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtPsiSourceElement firSourceElement = PsiRawFirBuilder.this.toFirSourceElement((PsiElement) expression, (KtFakeSourceElementKind) KtFakeSourceElementKind.ImplicitUnit.Return.INSTANCE);
            KtExpression returnedExpression = expression.getReturnedExpression();
            if (returnedExpression == null || (firExpressionMo6809build = toFirExpression$default(this, returnedExpression, "Incorrect return expression", null, 2, null)) == null) {
                FirUnitExpressionBuilder firUnitExpressionBuilder = new FirUnitExpressionBuilder();
                firUnitExpressionBuilder.setSource(firSourceElement);
                firExpressionMo6809build = firUnitExpressionBuilder.mo6809build();
            }
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            KtPsiSourceElement ktPsiSourceElement = firSourceElement;
            KtSimpleNameExpression targetLabel = expression.getTargetLabel();
            return psiRawFirBuilder.toReturn(firExpressionMo6809build, ktPsiSourceElement, targetLabel != null ? targetLabel.getReferencedName() : null, true);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitTryExpression(KtTryExpression expression, FirElement data) {
            KtBlockExpression finalExpression;
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirTryExpressionBuilder firTryExpressionBuilder = new FirTryExpressionBuilder();
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            firTryExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, expression, null, 1, null));
            firTryExpressionBuilder.setTryBlock(toFirBlock(expression.getTryBlock()));
            KtFinallySection finallyBlock = expression.getFinallyBlock();
            firTryExpressionBuilder.setFinallyBlock((finallyBlock == null || (finalExpression = finallyBlock.getFinalExpression()) == null) ? null : toFirBlock(finalExpression));
            for (KtCatchClause ktCatchClause : expression.getCatchClauses()) {
                final KtParameter catchParameter = ktCatchClause.getCatchParameter();
                if (catchParameter != null) {
                    Name nameAsSafeName = catchParameter.getNameAsSafeName();
                    Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                    Name nameConvertValueParameterName = psiRawFirBuilder.convertValueParameterName(nameAsSafeName, AbstractRawFirBuilder.ValueParameterDeclaration.CATCH, new Function0<String>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitTryExpression$1$parameter$1$name$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final String invoke() {
                            ASTNode node;
                            PsiElement psiElementMo7392getNameIdentifier = catchParameter.mo7392getNameIdentifier();
                            if (psiElementMo7392getNameIdentifier == null || (node = psiElementMo7392getNameIdentifier.getNode()) == null) {
                                return null;
                            }
                            return node.getText();
                        }
                    });
                    FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
                    Intrinsics.checkNotNull(catchParameter);
                    firPropertyBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, catchParameter, null, 1, null));
                    firPropertyBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
                    firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                    firPropertyBuilder.setReturnTypeRef(catchParameter.mo7396getTypeReference() != null ? toFirOrErrorType(catchParameter.mo7396getTypeReference()) : psiRawFirBuilder.createNoTypeForParameterTypeRef());
                    firPropertyBuilder.setVar(false);
                    firPropertyBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL, EffectiveVisibility.Local.INSTANCE));
                    firPropertyBuilder.setLocal(true);
                    firPropertyBuilder.setName(nameConvertValueParameterName);
                    firPropertyBuilder.setSymbol(new FirPropertySymbol(new CallableId(nameConvertValueParameterName, (FqName) null, 2, (DefaultConstructorMarker) null)));
                    for (KtAnnotationEntry ktAnnotationEntry : catchParameter.getAnnotationEntries()) {
                        List<FirAnnotation> annotations = firPropertyBuilder.getAnnotations();
                        Intrinsics.checkNotNull(ktAnnotationEntry);
                        FirElement firElementConvertElement = convertElement(ktAnnotationEntry, null);
                        if (firElementConvertElement != null) {
                            annotations.add((FirAnnotation) firElementConvertElement);
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotation");
                        }
                    }
                    FirProperty firPropertyMo6809build = firPropertyBuilder.mo6809build();
                    ClassMembersKt.setCatchParameter(firPropertyMo6809build, true);
                    if (firPropertyMo6809build != null) {
                        List<FirCatch> catches = firTryExpressionBuilder.getCatches();
                        FirCatchBuilder firCatchBuilder = new FirCatchBuilder();
                        Intrinsics.checkNotNull(ktCatchClause);
                        firCatchBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, ktCatchClause, null, 1, null));
                        firCatchBuilder.setParameter(firPropertyMo6809build);
                        firCatchBuilder.setBlock(toFirBlock(ktCatchClause.getCatchBody()));
                        catches.add(firCatchBuilder.build());
                    }
                }
            }
            return firTryExpressionBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitIfExpression(KtIfExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirWhenExpressionBuilder firWhenExpressionBuilder = new FirWhenExpressionBuilder();
            KtRealPsiSourceElement ktFakeSourceElement = null;
            firWhenExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
            KtExpression ktExpression = expression;
            while (true) {
                KtIfExpression ktIfExpression = (KtIfExpression) ktExpression;
                KtExpression condition = ktIfExpression.getCondition();
                List<FirWhenBranch> branches = firWhenExpressionBuilder.getBranches();
                FirWhenBranchBuilder firWhenBranchBuilder = new FirWhenBranchBuilder();
                firWhenBranchBuilder.setSource(condition != null ? psiRawFirBuilder.toFirSourceElement((PsiElement) condition, (KtFakeSourceElementKind) KtFakeSourceElementKind.WhenCondition.INSTANCE) : null);
                firWhenBranchBuilder.setCondition(toFirExpression$default(this, condition, "If statement should have condition", null, 2, null));
                firWhenBranchBuilder.setResult(toFirBlock(ktIfExpression.getThen()));
                branches.add(firWhenBranchBuilder.build());
                KtExpression ktExpression2 = ktIfExpression.getElse();
                if (ktExpression2 != null) {
                    if (!(ktExpression2 instanceof KtIfExpression)) {
                        List<FirWhenBranch> branches2 = firWhenExpressionBuilder.getBranches();
                        FirWhenBranchBuilder firWhenBranchBuilder2 = new FirWhenBranchBuilder();
                        PsiElement elseKeyword = ktIfExpression.getElseKeyword();
                        if (elseKeyword != null) {
                            Intrinsics.checkNotNull(elseKeyword);
                            Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                            if (obj instanceof KtRealSourceElementKind) {
                                ktFakeSourceElement = new KtRealPsiSourceElement(elseKeyword);
                            } else {
                                if (!(obj instanceof KtFakeSourceElementKind)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                ktFakeSourceElement = new KtFakeSourceElement(elseKeyword, (KtFakeSourceElementKind) obj);
                            }
                        }
                        firWhenBranchBuilder2.setSource(ktFakeSourceElement);
                        FirElseIfTrueConditionBuilder firElseIfTrueConditionBuilder = new FirElseIfTrueConditionBuilder();
                        Unit unit = Unit.INSTANCE;
                        firWhenBranchBuilder2.setCondition(firElseIfTrueConditionBuilder.mo6809build());
                        firWhenBranchBuilder2.setResult(toFirBlock(ktIfExpression.getElse()));
                        branches2.add(firWhenBranchBuilder2.build());
                        break;
                    }
                    ktExpression = ktExpression2;
                } else {
                    break;
                }
            }
            firWhenExpressionBuilder.setUsedAsExpression(getUsedAsExpression(expression));
            return firWhenExpressionBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitWhenExpression(KtWhenExpression expression, FirElement data) {
            FirExpression firExpression$default;
            FirProperty firPropertyMo6809build;
            Iterator<KtWhenEntry> it;
            int i;
            boolean z;
            KtFakeSourceElementKind ktFakeSourceElementKind;
            boolean z2;
            FirWhenBranch firWhenBranchBuild;
            FirExpression firExpressionBuildBalancedOrExpressionTree$default;
            int i2;
            FirErrorExpression firErrorExpressionMo6809build;
            boolean z3;
            KtExpression ktExpression;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtExpression subjectExpression = expression.getSubjectExpression();
            boolean z4 = subjectExpression instanceof KtVariableDeclaration;
            KtExpression initializer = z4 ? ((KtVariableDeclaration) subjectExpression).getInitializer() : subjectExpression;
            KtFakeSourceElementKind ktFakeSourceElementKind2 = null;
            if (initializer != null) {
                firExpression$default = toFirExpression$default(this, initializer, "Incorrect when subject expression: " + (subjectExpression != null ? subjectExpression.getText() : null), null, 2, null);
            } else {
                firExpression$default = null;
            }
            boolean z5 = false;
            int i3 = 1;
            if (z4) {
                KtVariableDeclaration ktVariableDeclaration = (KtVariableDeclaration) subjectExpression;
                Name nameAsSafeName = ktVariableDeclaration.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
                firPropertyBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, subjectExpression, null, 1, null));
                firPropertyBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
                firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firPropertyBuilder.setReturnTypeRef(toFirOrImplicitType(ktVariableDeclaration.mo7396getTypeReference()));
                firPropertyBuilder.setName(nameAsSafeName);
                firPropertyBuilder.setInitializer(firExpression$default);
                firPropertyBuilder.setDelegate(null);
                firPropertyBuilder.setVar(false);
                firPropertyBuilder.setSymbol(new FirPropertySymbol(nameAsSafeName));
                firPropertyBuilder.setLocal(true);
                firPropertyBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
                firPropertyMo6809build = firPropertyBuilder.mo6809build();
            } else {
                firPropertyMo6809build = null;
            }
            boolean z6 = firExpression$default != null;
            FirExpressionRef firExpressionRef = new FirExpressionRef();
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            FirWhenExpressionBuilder firWhenExpressionBuilder = new FirWhenExpressionBuilder();
            PsiRawFirBuilder psiRawFirBuilder3 = psiRawFirBuilder2;
            firWhenExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder3, expression, null, 1, null));
            firWhenExpressionBuilder.setSubject(firExpression$default);
            firWhenExpressionBuilder.setSubjectVariable(firPropertyMo6809build);
            firWhenExpressionBuilder.setUsedAsExpression(getUsedAsExpression(expression));
            Iterator<KtWhenEntry> it2 = expression.getEntries().iterator();
            boolean z7 = z6;
            while (it2.hasNext()) {
                KtWhenEntry next = it2.next();
                Intrinsics.checkNotNull(next);
                KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder3, next, ktFakeSourceElementKind2, i3, ktFakeSourceElementKind2);
                FirBlock firBlock = toFirBlock(next.getExpression());
                List<FirWhenBranch> branches = firWhenExpressionBuilder.getBranches();
                if (next.isElse()) {
                    it = it2;
                    i = i3;
                    z = z6;
                    ktFakeSourceElementKind = ktFakeSourceElementKind2;
                    z2 = z5;
                    FirWhenBranchBuilder firWhenBranchBuilder = new FirWhenBranchBuilder();
                    firWhenBranchBuilder.setSource(ktPsiSourceElement);
                    FirElseIfTrueConditionBuilder firElseIfTrueConditionBuilder = new FirElseIfTrueConditionBuilder();
                    Unit unit = Unit.INSTANCE;
                    firWhenBranchBuilder.setCondition(firElseIfTrueConditionBuilder.mo6809build());
                    firWhenBranchBuilder.setResult(firBlock);
                    firWhenBranchBuild = firWhenBranchBuilder.build();
                } else if (!z6) {
                    KtWhenCondition[] conditions = next.getConditions();
                    Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
                    KtWhenCondition ktWhenCondition = (KtWhenCondition) ArraysKt.first(conditions);
                    FirWhenBranchBuilder firWhenBranchBuilder2 = new FirWhenBranchBuilder();
                    firWhenBranchBuilder2.setSource(ktPsiSourceElement);
                    if (next.getConditions().length == i3 && (ktWhenCondition instanceof KtWhenConditionWithExpression)) {
                        KtExpression expression2 = ((KtWhenConditionWithExpression) ktWhenCondition).getExpression();
                        if (expression2 == null) {
                            ktExpression = ktWhenCondition;
                        } else {
                            Intrinsics.checkNotNull(expression2);
                            ktExpression = expression2;
                        }
                        it = it2;
                        i = i3;
                        z = z6;
                        firExpressionBuildBalancedOrExpressionTree$default = toFirExpression(ktExpression, "No expression in condition with expression", DiagnosticKind.ExpressionExpected);
                        z2 = false;
                        ktFakeSourceElementKind = null;
                    } else {
                        KtWhenCondition[] conditions2 = next.getConditions();
                        Intrinsics.checkNotNullExpressionValue(conditions2, "getConditions(...)");
                        KtWhenCondition[] ktWhenConditionArr = conditions2;
                        ArrayList arrayList = new ArrayList(ktWhenConditionArr.length);
                        int length = ktWhenConditionArr.length;
                        int i4 = 0;
                        while (i4 < length) {
                            KtWhenCondition ktWhenCondition2 = ktWhenConditionArr[i4];
                            Iterator<KtWhenEntry> it3 = it2;
                            if (ktWhenCondition2 instanceof KtWhenConditionWithExpression) {
                                firErrorExpressionMo6809build = toFirExpression(((KtWhenConditionWithExpression) ktWhenCondition2).getExpression(), "No expression in condition with expression", DiagnosticKind.ExpressionExpected);
                                z3 = z7;
                                i2 = 1;
                            } else {
                                FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                                Intrinsics.checkNotNull(ktWhenCondition2);
                                i2 = 1;
                                firErrorExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder3, ktWhenCondition2, null, 1, null));
                                firErrorExpressionBuilder.setNonExpressionElement(PsiConversionUtilsKt.toFirWhenCondition(ktWhenCondition2, (FirExpressionRef<FirWhenExpression>) firExpressionRef, new Function2<KtExpression, String, FirExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitWhenExpression$1$2$1$1$1
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final FirExpression invoke(KtExpression ktExpression2, String it4) {
                                        Intrinsics.checkNotNullParameter(it4, "it");
                                        return PsiRawFirBuilder.Visitor.toFirExpression$default(this.this$0, ktExpression2, it4, null, 2, null);
                                    }
                                }, new Function1<KtTypeReference, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitWhenExpression$1$2$1$1$2
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final FirTypeRef invoke(KtTypeReference ktTypeReference) {
                                        return this.this$0.toFirOrErrorType(ktTypeReference);
                                    }
                                }));
                                firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("No expression in condition with expression", DiagnosticKind.ExpressionExpected));
                                firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
                                z3 = true;
                            }
                            arrayList.add(firErrorExpressionMo6809build);
                            i4++;
                            it2 = it3;
                            z6 = z6;
                            ktWhenConditionArr = ktWhenConditionArr;
                            int i5 = i2;
                            z7 = z3;
                            i3 = i5;
                        }
                        it = it2;
                        i = i3;
                        z = z6;
                        z2 = false;
                        ktFakeSourceElementKind = null;
                        firExpressionBuildBalancedOrExpressionTree$default = ConversionUtilsKt.buildBalancedOrExpressionTree$default(arrayList, 0, 0, 6, null);
                    }
                    firWhenBranchBuilder2.setCondition(firExpressionBuildBalancedOrExpressionTree$default);
                    firWhenBranchBuilder2.setResult(firBlock);
                    firWhenBranchBuild = firWhenBranchBuilder2.build();
                } else {
                    FirWhenBranchBuilder firWhenBranchBuilder3 = new FirWhenBranchBuilder();
                    firWhenBranchBuilder3.setSource(ktPsiSourceElement);
                    KtWhenCondition[] conditions3 = next.getConditions();
                    Intrinsics.checkNotNullExpressionValue(conditions3, "getConditions(...)");
                    firWhenBranchBuilder3.setCondition(PsiConversionUtilsKt.toFirWhenCondition(conditions3, (FirExpressionRef<FirWhenExpression>) firExpressionRef, new Function2<KtExpression, String, FirExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitWhenExpression$1$1$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final FirExpression invoke(KtExpression ktExpression2, String it4) {
                            Intrinsics.checkNotNullParameter(it4, "it");
                            return PsiRawFirBuilder.Visitor.toFirExpression$default(this.this$0, ktExpression2, it4, null, 2, null);
                        }
                    }, new Function1<KtTypeReference, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitWhenExpression$1$1$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final FirTypeRef invoke(KtTypeReference ktTypeReference) {
                            return this.this$0.toFirOrErrorType(ktTypeReference);
                        }
                    }));
                    firWhenBranchBuilder3.setResult(firBlock);
                    it = it2;
                    i = i3;
                    z = z6;
                    z2 = z5;
                    firWhenBranchBuild = firWhenBranchBuilder3.build();
                    ktFakeSourceElementKind = ktFakeSourceElementKind2;
                }
                branches.add(firWhenBranchBuild);
                i3 = i;
                z5 = z2;
                ktFakeSourceElementKind2 = ktFakeSourceElementKind;
                z6 = z;
                it2 = it;
            }
            FirWhenExpression firWhenExpressionMo6809build = firWhenExpressionBuilder.mo6809build();
            if (z7) {
                firExpressionRef.bind(firWhenExpressionMo6809build);
            }
            return firWhenExpressionMo6809build;
        }

        private final boolean getUsedAsExpression(KtExpression ktExpression) {
            PsiElement parent = ktExpression.getLightParent();
            while (true) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                Intrinsics.checkNotNull(parent);
                if (!Intrinsics.areEqual(psiRawFirBuilder.getElementType(parent), KtNodeTypes.ANNOTATED_EXPRESSION)) {
                    PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                    Intrinsics.checkNotNull(parent);
                    if (!Intrinsics.areEqual(psiRawFirBuilder2.getElementType(parent), KtNodeTypes.LABELED_EXPRESSION)) {
                        break;
                    }
                }
                parent = parent.getLightParent();
            }
            if (parent instanceof KtBlockExpression) {
                return false;
            }
            PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
            Intrinsics.checkNotNull(parent);
            IElementType elementType = psiRawFirBuilder3.getElementType(parent);
            if (Intrinsics.areEqual(elementType, KtNodeTypes.THEN) ? true : Intrinsics.areEqual(elementType, KtNodeTypes.ELSE) ? true : Intrinsics.areEqual(elementType, KtNodeTypes.WHEN_ENTRY)) {
                PsiElement lightParent = parent.getLightParent();
                KtExpression ktExpression2 = lightParent instanceof KtExpression ? (KtExpression) lightParent : null;
                if (ktExpression2 != null) {
                    return getUsedAsExpression(ktExpression2);
                }
                return true;
            }
            if (parent instanceof KtScriptInitializer) {
                return false;
            }
            if (!(parent instanceof KtContainerNodeForControlStructureBody)) {
                return true;
            }
            PsiRawFirBuilder psiRawFirBuilder4 = PsiRawFirBuilder.this;
            PsiElement parent2 = ((KtContainerNodeForControlStructureBody) parent).getLightParent();
            Intrinsics.checkNotNullExpressionValue(parent2, "getParent(...)");
            IElementType elementType2 = psiRawFirBuilder4.getElementType(parent2);
            return (Intrinsics.areEqual(elementType2, KtNodeTypes.FOR) || Intrinsics.areEqual(elementType2, KtNodeTypes.WHILE) || Intrinsics.areEqual(elementType2, KtNodeTypes.DO_WHILE)) ? false : true;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitDoWhileExpression(final KtDoWhileExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirDoWhileLoopBuilder firDoWhileLoopBuilder = new FirDoWhileLoopBuilder();
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            firDoWhileLoopBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, expression, null, 1, null));
            FirDoWhileLoopBuilder firDoWhileLoopBuilder2 = firDoWhileLoopBuilder;
            FirLoopTarget firLoopTargetPrepareTarget = psiRawFirBuilder2.prepareTarget(firDoWhileLoopBuilder2, expression);
            firDoWhileLoopBuilder.setCondition(toFirExpression$default(this, expression.getCondition(), "No condition in do-while loop", null, 2, null));
            return psiRawFirBuilder.configure(firDoWhileLoopBuilder2, firLoopTargetPrepareTarget, new Function0<FirBlock>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitDoWhileExpression$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirBlock invoke() {
                    return this.this$0.toFirBlock(expression.getBody());
                }
            });
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitWhileExpression(final KtWhileExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirWhileLoopBuilder firWhileLoopBuilder = new FirWhileLoopBuilder();
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            firWhileLoopBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, expression, null, 1, null));
            firWhileLoopBuilder.setCondition(toFirExpression$default(this, expression.getCondition(), "No condition in while loop", null, 2, null));
            FirWhileLoopBuilder firWhileLoopBuilder2 = firWhileLoopBuilder;
            return psiRawFirBuilder.configure(firWhileLoopBuilder2, psiRawFirBuilder2.prepareTarget(firWhileLoopBuilder2, expression), new Function0<FirBlock>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitWhileExpression$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirBlock invoke() {
                    return this.this$0.toFirBlock(expression.getBody());
                }
            });
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitForExpression(final KtForExpression expression, FirElement data) {
            final KtFakeSourceElement ktFakeSourceElement;
            KtPsiSourceElement firSourceElement;
            Intrinsics.checkNotNullParameter(expression, "expression");
            FirExpression firExpression$default = toFirExpression$default(this, expression.getLoopRange(), "No range in for loop", null, 2, null);
            final KtParameter loopParameter = expression.getLoopParameter();
            KtForExpression ktForExpression = expression;
            KtFakeSourceElementKind.DesugaredForLoop desugaredForLoop = KtFakeSourceElementKind.DesugaredForLoop.INSTANCE;
            if (desugaredForLoop instanceof KtRealSourceElementKind) {
                ktFakeSourceElement = new KtRealPsiSourceElement(ktForExpression);
            } else {
                if (!(desugaredForLoop instanceof KtFakeSourceElementKind)) {
                    throw new NoWhenBranchMatchedException();
                }
                ktFakeSourceElement = new KtFakeSourceElement(ktForExpression, desugaredForLoop);
            }
            final PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
            KtPsiSourceElement ktPsiSourceElement = ktFakeSourceElement;
            firBlockBuilder.setSource(ktPsiSourceElement);
            KtExpression loopRange = expression.getLoopRange();
            if (loopRange != null) {
                Intrinsics.checkNotNull(loopRange);
                firSourceElement = psiRawFirBuilder.toFirSourceElement((PsiElement) loopRange, (KtFakeSourceElementKind) KtFakeSourceElementKind.DesugaredForLoop.INSTANCE);
            } else {
                firSourceElement = null;
            }
            FirModuleData baseModuleData = psiRawFirBuilder.getBaseModuleData();
            KtPsiSourceElement ktPsiSourceElement2 = firSourceElement;
            Name name2 = SpecialNames.ITERATOR;
            FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
            firFunctionCallBuilder.setSource(ktPsiSourceElement);
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
            firSimpleNamedReferenceBuilder.setSource(ktPsiSourceElement);
            firSimpleNamedReferenceBuilder.setName(OperatorNameConventions.ITERATOR);
            firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
            firFunctionCallBuilder.setExplicitReceiver(firExpression$default);
            Unit unit = Unit.INSTANCE;
            final FirProperty firPropertyGenerateTemporaryVariable$default = FirGenerationKt.generateTemporaryVariable$default(baseModuleData, ktPsiSourceElement2, name2, firFunctionCallBuilder.mo6809build(), null, null, 48, null);
            firBlockBuilder.getStatements().add(firPropertyGenerateTemporaryVariable$default);
            List<FirStatement> statements = firBlockBuilder.getStatements();
            FirWhileLoopBuilder firWhileLoopBuilder = new FirWhileLoopBuilder();
            firWhileLoopBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
            FirFunctionCallBuilder firFunctionCallBuilder2 = new FirFunctionCallBuilder();
            firFunctionCallBuilder2.setSource(ktPsiSourceElement);
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder2 = new FirSimpleNamedReferenceBuilder();
            firSimpleNamedReferenceBuilder2.setSource(ktPsiSourceElement);
            firSimpleNamedReferenceBuilder2.setName(OperatorNameConventions.HAS_NEXT);
            firFunctionCallBuilder2.setCalleeReference(firSimpleNamedReferenceBuilder2.build());
            firFunctionCallBuilder2.setExplicitReceiver(ConversionUtilsKt.generateResolvedAccessExpression(ktPsiSourceElement, firPropertyGenerateTemporaryVariable$default));
            firWhileLoopBuilder.setCondition(firFunctionCallBuilder2.mo6809build());
            FirWhileLoopBuilder firWhileLoopBuilder2 = firWhileLoopBuilder;
            statements.add(psiRawFirBuilder.configure(firWhileLoopBuilder2, psiRawFirBuilder.prepareTarget(firWhileLoopBuilder2, expression), new Function0<FirBlock>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitForExpression$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirBlock invoke() {
                    Name nameAsSafeName;
                    FirBlockBuilder firBlockBuilder2 = new FirBlockBuilder();
                    firBlockBuilder2.setSource(psiRawFirBuilder.toFirSourceElement((PsiElement) expression, (KtFakeSourceElementKind) KtFakeSourceElementKind.DesugaredForLoop.INSTANCE));
                    KtParameter ktParameter = loopParameter;
                    if (ktParameter != null) {
                        KtDestructuringDeclaration destructuringDeclaration = ktParameter.getDestructuringDeclaration();
                        FirModuleData baseModuleData2 = psiRawFirBuilder.getBaseModuleData();
                        KtParameter loopParameter2 = expression.getLoopParameter();
                        KtPsiSourceElement ktPsiSourceElement3 = loopParameter2 != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, loopParameter2, null, 1, null) : null;
                        if (destructuringDeclaration != null) {
                            nameAsSafeName = SpecialNames.DESTRUCT;
                        } else {
                            nameAsSafeName = loopParameter.getNameAsSafeName();
                            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                        }
                        Name name3 = nameAsSafeName;
                        KtPsiSourceElement ktPsiSourceElement4 = ktFakeSourceElement;
                        FirProperty firProperty = firPropertyGenerateTemporaryVariable$default;
                        FirFunctionCallBuilder firFunctionCallBuilder3 = new FirFunctionCallBuilder();
                        KtPsiSourceElement ktPsiSourceElement5 = ktPsiSourceElement4;
                        firFunctionCallBuilder3.setSource(ktPsiSourceElement5);
                        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder3 = new FirSimpleNamedReferenceBuilder();
                        firSimpleNamedReferenceBuilder3.setSource(ktPsiSourceElement5);
                        firSimpleNamedReferenceBuilder3.setName(OperatorNameConventions.NEXT);
                        firFunctionCallBuilder3.setCalleeReference(firSimpleNamedReferenceBuilder3.build());
                        firFunctionCallBuilder3.setExplicitReceiver(ConversionUtilsKt.generateResolvedAccessExpression(ktPsiSourceElement5, firProperty));
                        Unit unit2 = Unit.INSTANCE;
                        FirProperty firPropertyGenerateTemporaryVariable$default2 = FirGenerationKt.generateTemporaryVariable$default(baseModuleData2, ktPsiSourceElement3, name3, firFunctionCallBuilder3.mo6809build(), this.toFirOrImplicitType(loopParameter.mo7396getTypeReference()), null, 32, null);
                        if (destructuringDeclaration != null) {
                            final PsiRawFirBuilder.Visitor visitor = this;
                            Function2<KtAnnotated, FirAnnotationContainerBuilder, Unit> function2 = new Function2<KtAnnotated, FirAnnotationContainerBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitForExpression$1$2$destructuringBlock$1
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public /* bridge */ /* synthetic */ Unit invoke(KtAnnotated ktAnnotated, FirAnnotationContainerBuilder firAnnotationContainerBuilder) {
                                    invoke2(ktAnnotated, firAnnotationContainerBuilder);
                                    return Unit.INSTANCE;
                                }

                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(KtAnnotated generateDestructuringBlock, FirAnnotationContainerBuilder it) {
                                    Intrinsics.checkNotNullParameter(generateDestructuringBlock, "$this$generateDestructuringBlock");
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    visitor.extractAnnotationsTo(generateDestructuringBlock, it);
                                }
                            };
                            final PsiRawFirBuilder.Visitor visitor2 = this;
                            firBlockBuilder2.getStatements().addAll(PsiConversionUtilsKt.generateDestructuringBlock(psiRawFirBuilder.getBaseModuleData(), destructuringDeclaration, firPropertyGenerateTemporaryVariable$default2, true, true, function2, new Function1<KtTypeReference, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitForExpression$1$2$destructuringBlock$2
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final FirTypeRef invoke(KtTypeReference ktTypeReference) {
                                    return visitor2.toFirOrImplicitType(ktTypeReference);
                                }
                            }).getStatements());
                        } else {
                            firBlockBuilder2.getStatements().add(firPropertyGenerateTemporaryVariable$default2);
                        }
                    }
                    firBlockBuilder2.getStatements().add(this.toFirBlock(expression.getBody()));
                    return firBlockBuilder2.mo6809build();
                }
            }));
            return firBlockBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitBreakExpression(KtBreakExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirBreakExpressionBuilder firBreakExpressionBuilder = new FirBreakExpressionBuilder();
            firBreakExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null));
            return psiRawFirBuilder.bindLabel(firBreakExpressionBuilder, expression).build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitContinueExpression(KtContinueExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirContinueExpressionBuilder firContinueExpressionBuilder = new FirContinueExpressionBuilder();
            firContinueExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null));
            return psiRawFirBuilder.bindLabel(firContinueExpressionBuilder, expression).build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitBinaryExpression(final KtBinaryExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IElementType operationToken = expression.getOperationToken();
            Intrinsics.checkNotNullExpressionValue(operationToken, "getOperationToken(...)");
            if (Intrinsics.areEqual(operationToken, KtTokens.IDENTIFIER)) {
                PsiRawFirBuilder.this.getContext().getCalleeNamesForLambda().add(expression.getOperationReference().getReferencedNameAsName());
            } else {
                PsiRawFirBuilder.this.getContext().getCalleeNamesForLambda().add(null);
            }
            FirExpression firExpression$default = toFirExpression$default(this, expression.getLeft(), "No left operand", null, 2, null);
            FirExpression firExpression$default2 = toFirExpression$default(this, expression.getRight(), "No right operand", null, 2, null);
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            psiRawFirBuilder.removeLast(psiRawFirBuilder.getContext().getCalleeNamesForLambda());
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null);
            if (Intrinsics.areEqual(operationToken, KtTokens.ELVIS)) {
                return ConversionUtilsKt.generateNotNullOrOther(firExpression$default, firExpression$default2, ktPsiSourceElement);
            }
            if (Intrinsics.areEqual(operationToken, KtTokens.ANDAND) ? true : Intrinsics.areEqual(operationToken, KtTokens.OROR)) {
                return ConversionUtilsKt.generateLazyLogicalOperation(firExpression$default, firExpression$default2, Intrinsics.areEqual(operationToken, KtTokens.ANDAND), ktPsiSourceElement);
            }
            ImmutableSet<KtSingleValueToken> IN_OPERATIONS = OperatorConventions.IN_OPERATIONS;
            Intrinsics.checkNotNullExpressionValue(IN_OPERATIONS, "IN_OPERATIONS");
            if (CollectionsKt.contains(IN_OPERATIONS, operationToken)) {
                PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                KtOperationReferenceExpression operationReference = expression.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                return ConversionUtilsKt.generateContainsOperation(firExpression$default2, firExpression$default, Intrinsics.areEqual(operationToken, KtTokens.NOT_IN), ktPsiSourceElement, AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, operationReference, null, 1, null));
            }
            ImmutableSet<KtSingleValueToken> COMPARISON_OPERATIONS = OperatorConventions.COMPARISON_OPERATIONS;
            Intrinsics.checkNotNullExpressionValue(COMPARISON_OPERATIONS, "COMPARISON_OPERATIONS");
            if (CollectionsKt.contains(COMPARISON_OPERATIONS, operationToken)) {
                PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
                KtOperationReferenceExpression operationReference2 = expression.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference2, "getOperationReference(...)");
                return ConversionUtilsKt.generateComparisonExpression(firExpression$default, firExpression$default2, operationToken, ktPsiSourceElement, AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder3, operationReference2, null, 1, null));
            }
            Name binaryName = ConversionUtilsKt.toBinaryName(operationToken);
            if (binaryName != null || Intrinsics.areEqual(operationToken, KtTokens.IDENTIFIER)) {
                PsiRawFirBuilder psiRawFirBuilder4 = PsiRawFirBuilder.this;
                FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
                firFunctionCallBuilder.setSource(ktPsiSourceElement);
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                KtOperationReferenceExpression operationReference3 = expression.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference3, "getOperationReference(...)");
                firSimpleNamedReferenceBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder4, operationReference3, null, 1, null));
                firSimpleNamedReferenceBuilder.setName(binaryName == null ? expression.getOperationReference().getReferencedNameAsName() : binaryName);
                firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
                firFunctionCallBuilder.setExplicitReceiver(firExpression$default);
                firFunctionCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firExpression$default2));
                firFunctionCallBuilder.setOrigin(binaryName != null ? FirFunctionCallOrigin.Operator : FirFunctionCallOrigin.Infix);
                return firFunctionCallBuilder.mo6809build();
            }
            FirOperation firOperation = ConversionUtilsKt.toFirOperation(operationToken);
            if (FirOperation.INSTANCE.getASSIGNMENTS().contains(firOperation)) {
                PsiRawFirBuilder psiRawFirBuilder5 = PsiRawFirBuilder.this;
                KtExpression left = expression.getLeft();
                KtPsiSourceElement ktPsiSourceElement2 = ktPsiSourceElement;
                KtExpression left2 = expression.getLeft();
                return psiRawFirBuilder5.generateAssignment(left, ktPsiSourceElement2, left2 != null ? (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, left2, null, 1, null) : null, firExpression$default2, firOperation, firExpression$default.getAnnotations(), expression.getRight(), new Function1<PsiElement, FirExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitBinaryExpression$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final FirExpression invoke(PsiElement generateAssignment) {
                        Intrinsics.checkNotNullParameter(generateAssignment, "$this$generateAssignment");
                        return PsiRawFirBuilder.Visitor.toFirExpression$default(this.this$0, (KtExpression) generateAssignment, "Incorrect expression in assignment: " + expression.getText(), null, 2, null);
                    }
                });
            }
            FirEqualityOperatorCallBuilder firEqualityOperatorCallBuilder = new FirEqualityOperatorCallBuilder();
            firEqualityOperatorCallBuilder.setSource(ktPsiSourceElement);
            firEqualityOperatorCallBuilder.setOperation(firOperation);
            firEqualityOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildBinaryArgumentList(firExpression$default, firExpression$default2));
            return firEqualityOperatorCallBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirTypeOperatorCallBuilder firTypeOperatorCallBuilder = new FirTypeOperatorCallBuilder();
            firTypeOperatorCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
            firTypeOperatorCallBuilder.setOperation(ConversionUtilsKt.toFirOperation(expression.getOperationReference().getReferencedNameElementType()));
            firTypeOperatorCallBuilder.setConversionTypeRef(toFirOrErrorType(expression.getRight()));
            firTypeOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(toFirExpression$default(this, expression.getLeft(), "No left operand", null, 2, null)));
            return firTypeOperatorCallBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitIsExpression(KtIsExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirTypeOperatorCallBuilder firTypeOperatorCallBuilder = new FirTypeOperatorCallBuilder();
            firTypeOperatorCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
            firTypeOperatorCallBuilder.setOperation(expression.isNegated() ? FirOperation.NOT_IS : FirOperation.IS);
            firTypeOperatorCallBuilder.setConversionTypeRef(toFirOrErrorType(expression.getTypeReference()));
            firTypeOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(toFirExpression$default(this, expression.getLeftHandSide(), "No left operand", null, 2, null)));
            return firTypeOperatorCallBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitUnaryExpression(KtUnaryExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IElementType operationToken = expression.getOperationToken();
            KtExpression baseExpression = expression.getBaseExpression();
            Intrinsics.checkNotNull(operationToken);
            Name unaryName = ConversionUtilsKt.toUnaryName(operationToken);
            if (Intrinsics.areEqual(operationToken, KtTokens.EXCLEXCL)) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                FirCheckNotNullCallBuilder firCheckNotNullCallBuilder = new FirCheckNotNullCallBuilder();
                firCheckNotNullCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
                firCheckNotNullCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(toFirExpression$default(this, baseExpression, "No operand", null, 2, null)));
                return firCheckNotNullCallBuilder.mo6809build();
            }
            if (unaryName != null) {
                ImmutableSet<KtSingleValueToken> INCREMENT_OPERATIONS = OperatorConventions.INCREMENT_OPERATIONS;
                Intrinsics.checkNotNullExpressionValue(INCREMENT_OPERATIONS, "INCREMENT_OPERATIONS");
                if (CollectionsKt.contains(INCREMENT_OPERATIONS, operationToken)) {
                    return PsiRawFirBuilder.this.generateIncrementOrDecrementBlock(expression, expression.getOperationReference(), baseExpression, unaryName, expression instanceof KtPrefixExpression, new Function1<PsiElement, FirExpression>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitUnaryExpression$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final FirExpression invoke(PsiElement generateIncrementOrDecrementBlock) {
                            Intrinsics.checkNotNullParameter(generateIncrementOrDecrementBlock, "$this$generateIncrementOrDecrementBlock");
                            return PsiRawFirBuilder.Visitor.toFirExpression$default(this.this$0, (KtExpression) generateIncrementOrDecrementBlock, "Incorrect expression inside inc/dec", null, 2, null);
                        }
                    });
                }
                FirExpression firExpression$default = toFirExpression$default(this, baseExpression, "No operand", null, 2, null);
                FirExpression firExpressionConvertUnaryPlusMinusCallOnIntegerLiteralIfNecessary = PsiRawFirBuilder.this.convertUnaryPlusMinusCallOnIntegerLiteralIfNecessary(expression, firExpression$default, operationToken);
                if (firExpressionConvertUnaryPlusMinusCallOnIntegerLiteralIfNecessary != null) {
                    return firExpressionConvertUnaryPlusMinusCallOnIntegerLiteralIfNecessary;
                }
                PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
                PsiRawFirBuilder psiRawFirBuilder3 = psiRawFirBuilder2;
                firFunctionCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder3, expression, null, 1, null));
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                KtSimpleNameExpression operationReference = expression.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                firSimpleNamedReferenceBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder3, operationReference, null, 1, null));
                firSimpleNamedReferenceBuilder.setName(unaryName);
                firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
                firFunctionCallBuilder.setExplicitReceiver(firExpression$default);
                firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
                return firFunctionCallBuilder.mo6809build();
            }
            throw new IllegalStateException("Unexpected expression: " + expression.getText());
        }

        private final CalleeAndReceiver splitToCalleeAndReceiver(KtExpression calleeExpression, KtPsiSourceElement defaultSource) {
            if (calleeExpression instanceof KtSimpleNameExpression) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                firSimpleNamedReferenceBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, calleeExpression, null, 1, null));
                firSimpleNamedReferenceBuilder.setName(((KtSimpleNameExpression) calleeExpression).getReferencedNameAsName());
                return new CalleeAndReceiver(firSimpleNamedReferenceBuilder.build(), null, false, 6, null);
            }
            if (calleeExpression instanceof KtParenthesizedExpression) {
                return splitToCalleeAndReceiver(((KtParenthesizedExpression) calleeExpression).getExpression(), defaultSource);
            }
            if (calleeExpression != null) {
                if (calleeExpression instanceof KtSuperExpression) {
                    PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                    FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
                    firErrorNamedReferenceBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, calleeExpression, null, 1, null));
                    firErrorNamedReferenceBuilder.setDiagnostic(new ConeSimpleDiagnostic("Super cannot be a callee", DiagnosticKind.SuperNotAllowed));
                    return new CalleeAndReceiver(firErrorNamedReferenceBuilder.build(), null, false, 6, null);
                }
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder2 = new FirSimpleNamedReferenceBuilder();
                firSimpleNamedReferenceBuilder2.setSource(KtSourceElementKt.fakeElement$default(defaultSource, KtFakeSourceElementKind.ImplicitInvokeCall.INSTANCE, 0, 0, 6, null));
                firSimpleNamedReferenceBuilder2.setName(OperatorNameConventions.INVOKE);
                return new CalleeAndReceiver(firSimpleNamedReferenceBuilder2.build(), toFirExpression$default(this, calleeExpression, "Incorrect invoke receiver", null, 2, null), true);
            }
            FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder2 = new FirErrorNamedReferenceBuilder();
            firErrorNamedReferenceBuilder2.setSource(defaultSource);
            firErrorNamedReferenceBuilder2.setDiagnostic(new ConeSyntaxDiagnostic("Call has no callee"));
            return new CalleeAndReceiver(firErrorNamedReferenceBuilder2.build(), null, false, 6, null);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitCallExpression(KtCallExpression expression, FirElement data) throws Throwable {
            FirAbstractFunctionCallBuilder firAbstractFunctionCallBuilder;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null);
            CalleeAndReceiver calleeAndReceiverSplitToCalleeAndReceiver = splitToCalleeAndReceiver(expression.getCalleeExpression(), ktPsiSourceElement);
            FirNamedReference reference = calleeAndReceiverSplitToCalleeAndReceiver.getReference();
            FirExpression receiverExpression = calleeAndReceiverSplitToCalleeAndReceiver.getReceiverExpression();
            boolean isImplicitInvoke = calleeAndReceiverSplitToCalleeAndReceiver.getIsImplicitInvoke();
            if (expression.getValueArgumentList() == null && expression.getLambdaArguments().isEmpty()) {
                FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
                firPropertyAccessExpressionBuilder.setSource(ktPsiSourceElement);
                firPropertyAccessExpressionBuilder.setCalleeReference(reference);
                firAbstractFunctionCallBuilder = firPropertyAccessExpressionBuilder;
            } else {
                FirAbstractFunctionCallBuilder firImplicitInvokeCallBuilder = isImplicitInvoke ? new FirImplicitInvokeCallBuilder() : new FirFunctionCallBuilder();
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                firImplicitInvokeCallBuilder.setSource(ktPsiSourceElement);
                firImplicitInvokeCallBuilder.setCalleeReference(reference);
                psiRawFirBuilder.getContext().getCalleeNamesForLambda().add(reference.getName());
                extractArgumentsTo(expression, firImplicitInvokeCallBuilder);
                psiRawFirBuilder.removeLast(psiRawFirBuilder.getContext().getCalleeNamesForLambda());
                firAbstractFunctionCallBuilder = firImplicitInvokeCallBuilder;
            }
            firAbstractFunctionCallBuilder.setExplicitReceiver(receiverExpression);
            List<FirTypeProjection> typeArguments = firAbstractFunctionCallBuilder.getTypeArguments();
            List<KtTypeProjection> typeArguments2 = expression.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments2, "getTypeArguments(...)");
            appendTypeArguments(typeArguments, typeArguments2);
            return firAbstractFunctionCallBuilder.build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitArrayAccessExpression(KtArrayAccessExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtExpression arrayExpression = expression.getArrayExpression();
            FirExpression firExpressionRemove = PsiRawFirBuilder.this.getContext().getArraySetArgument().remove(expression);
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
            boolean z = firExpressionRemove == null;
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            KtArrayAccessExpression parent = z ? expression : expression.getLightParent();
            Intrinsics.checkNotNull(parent);
            firFunctionCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, parent, null, 1, null));
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
            firSimpleNamedReferenceBuilder.setSource(KtSourceElementKt.fakeElement$default(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, expression, null, 1, null), KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE, 0, 0, 6, null));
            firSimpleNamedReferenceBuilder.setName(z ? OperatorNameConventions.GET : OperatorNameConventions.SET);
            firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
            firFunctionCallBuilder.setExplicitReceiver(toFirExpression$default(this, arrayExpression, "No array expression", null, 2, null));
            FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
            Iterator<KtExpression> it = expression.getIndexExpressions().iterator();
            while (it.hasNext()) {
                firArgumentListBuilder.getArguments().add(toFirExpression$default(this, it.next(), "Incorrect index expression", null, 2, null));
            }
            if (firExpressionRemove != null) {
                firArgumentListBuilder.getArguments().add(firExpressionRemove);
            }
            firFunctionCallBuilder.setArgumentList(firArgumentListBuilder.build());
            firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
            return ConversionUtilsKt.pullUpSafeCallIfNecessary(firFunctionCallBuilder.mo6809build());
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitQualifiedExpression(KtQualifiedExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            FirExpression firExpression$default = toFirExpression$default(this, expression.getReceiverExpression(), "Incorrect receiver expression", null, 2, null);
            KtExpression selectorExpression = expression.getSelectorExpression();
            if (selectorExpression == null) {
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                firErrorExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
                firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Qualified expression without selector"));
                firErrorExpressionBuilder.setExpression(firExpression$default);
                return firErrorExpressionBuilder.mo6809build();
            }
            FirExpression firExpression$default2 = toFirExpression$default(this, selectorExpression, "Incorrect selector expression", null, 2, null);
            if (firExpression$default2 instanceof FirQualifiedAccessExpression) {
                if (expression instanceof KtSafeQualifiedExpression) {
                    FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) firExpression$default2;
                    firQualifiedAccessExpression.replaceSource(PsiRawFirBuilder.this.toFirSourceElement((PsiElement) expression, (KtFakeSourceElementKind) KtFakeSourceElementKind.DesugaredSafeCallExpression.INSTANCE));
                    return ConversionUtilsKt.createSafeCall(firQualifiedAccessExpression, firExpression$default, AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null));
                }
                PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                return psiRawFirBuilder2.convertFirSelector((FirQualifiedAccessExpression) firExpression$default2, AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, expression, null, 1, null), firExpression$default);
            }
            if (firExpression$default2 instanceof FirErrorExpression) {
                PsiRawFirBuilder psiRawFirBuilder3 = PsiRawFirBuilder.this;
                FirQualifiedErrorAccessExpressionBuilder firQualifiedErrorAccessExpressionBuilder = new FirQualifiedErrorAccessExpressionBuilder();
                firQualifiedErrorAccessExpressionBuilder.setReceiver(firExpression$default);
                firQualifiedErrorAccessExpressionBuilder.setSelector((FirErrorExpression) firExpression$default2);
                firQualifiedErrorAccessExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder3, expression, null, 1, null));
                firQualifiedErrorAccessExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Qualified expression with unexpected selector"));
                return firQualifiedErrorAccessExpressionBuilder.mo6809build();
            }
            return firExpression$default2;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitThisExpression(KtThisExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirThisReceiverExpressionBuilder firThisReceiverExpressionBuilder = new FirThisReceiverExpressionBuilder();
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null);
            firThisReceiverExpressionBuilder.setSource(ktPsiSourceElement);
            FirExplicitThisReferenceBuilder firExplicitThisReferenceBuilder = new FirExplicitThisReferenceBuilder();
            firExplicitThisReferenceBuilder.setSource(KtSourceElementKt.fakeElement$default(ktPsiSourceElement, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE, 0, 0, 6, null));
            firExplicitThisReferenceBuilder.setLabelName(expression.getLabelName());
            firThisReceiverExpressionBuilder.setCalleeReference(firExplicitThisReferenceBuilder.build());
            return firThisReceiverExpressionBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitSuperExpression(KtSuperExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtTypeReference superTypeQualifier = expression.getSuperTypeQualifier();
            KtPsiSourceElement ktPsiSourceElement = (KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null);
            FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
            KtPsiSourceElement ktPsiSourceElement2 = ktPsiSourceElement;
            firPropertyAccessExpressionBuilder.setSource(ktPsiSourceElement2);
            FirExplicitSuperReferenceBuilder firExplicitSuperReferenceBuilder = new FirExplicitSuperReferenceBuilder();
            firExplicitSuperReferenceBuilder.setSource(KtSourceElementKt.fakeElement$default(ktPsiSourceElement2, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE, 0, 0, 6, null));
            firExplicitSuperReferenceBuilder.setLabelName(expression.getLabelName());
            firExplicitSuperReferenceBuilder.setSuperTypeRef(toFirOrImplicitType(superTypeQualifier));
            firPropertyAccessExpressionBuilder.setCalleeReference(firExplicitSuperReferenceBuilder.build());
            return firPropertyAccessExpressionBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitParenthesizedExpression(KtParenthesizedExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder.this.getContext().forwardLabelUsagePermission(expression, expression.getExpression());
            KtExpression expression2 = expression.getExpression();
            FirElement firElement = expression2 != null ? (FirElement) expression2.accept(this, data) : null;
            return firElement == null ? FirExpressionUtilKt.buildErrorExpression$default(AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null), new ConeSyntaxDiagnostic("Empty parentheses"), null, 4, null) : firElement;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitLabeledExpression(KtLabeledExpression expression, FirElement data) {
            FirElement firElement;
            KtSourceElement second;
            KtFakeSourceElement ktFakeSourceElement;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtSimpleNameExpression targetLabel = expression.getTargetLabel();
            if (targetLabel != null) {
                ASTNode node = targetLabel.getReferencedNameElement().getNode();
                Intrinsics.checkNotNull(node);
                String text = node.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                KtSimpleNameExpression ktSimpleNameExpression = targetLabel;
                Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                if (obj instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement = new KtRealPsiSourceElement(ktSimpleNameExpression);
                } else {
                    if (!(obj instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement = new KtFakeSourceElement(ktSimpleNameExpression, (KtFakeSourceElementKind) obj);
                }
                Pair<FirLabel, KtSourceElement> pairBuildLabelAndErrorSource = psiRawFirBuilder.buildLabelAndErrorSource(text, ktFakeSourceElement);
                second = pairBuildLabelAndErrorSource.getSecond();
                Context<PsiElement> context = PsiRawFirBuilder.this.getContext();
                FirLabel first = pairBuildLabelAndErrorSource.getFirst();
                KtExpression baseExpression = expression.getBaseExpression();
                context.addNewLabel(first);
                context.setNewLabelUserNode(baseExpression);
                try {
                    KtExpression baseExpression2 = expression.getBaseExpression();
                    firElement = baseExpression2 != null ? (FirElement) baseExpression2.accept(this, data) : null;
                } finally {
                    context.dropLastLabel();
                }
            } else {
                KtExpression baseExpression3 = expression.getBaseExpression();
                if (baseExpression3 != null) {
                    firElement = (FirElement) baseExpression3.accept(this, data);
                    second = null;
                } else {
                    firElement = null;
                    second = null;
                }
            }
            PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
            return psiRawFirBuilder2.buildExpressionWithErrorLabel(firElement, second, AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, expression, null, 1, null));
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitAnnotatedExpression(KtAnnotatedExpression expression, FirElement data) {
            String strRender;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtExpression baseExpression = expression.getBaseExpression();
            PsiRawFirBuilder.this.getContext().forwardLabelUsagePermission(expression, baseExpression);
            FirElement firElement = baseExpression != null ? (FirElement) baseExpression.accept(this, data) : null;
            FirErrorExpression firErrorExpressionBuildErrorExpression$default = firElement instanceof FirAnnotationContainer ? (FirAnnotationContainer) firElement : null;
            if (firErrorExpressionBuildErrorExpression$default == null) {
                KtSourceElement firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null);
                if (firElement == null || (strRender = UtilsKt.render(firElement)) == null) {
                    strRender = "???";
                }
                firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(firSourceElement$default, new ConeNotAnnotationContainer(strRender), null, 4, null);
            }
            extractAnnotationsTo(expression, firErrorExpressionBuildErrorExpression$default);
            return firErrorExpressionBuildErrorExpression$default;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitThrowExpression(KtThrowExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirThrowExpressionBuilder firThrowExpressionBuilder = new FirThrowExpressionBuilder();
            firThrowExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
            firThrowExpressionBuilder.setException(toFirExpression$default(this, expression.getThrownExpression(), "Nothing to throw", null, 2, null));
            return firThrowExpressionBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitDestructuringDeclaration(KtDestructuringDeclaration multiDeclaration, FirElement data) {
            FirErrorExpression firErrorExpressionMo6809build;
            Intrinsics.checkNotNullParameter(multiDeclaration, "multiDeclaration");
            FirModuleData baseModuleData = PsiRawFirBuilder.this.getBaseModuleData();
            KtSourceElement firSourceElement$default = AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, multiDeclaration, null, 1, null);
            KtExpression initializer = multiDeclaration.getInitializer();
            if (initializer == null) {
                firErrorExpressionMo6809build = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("Initializer required for destructuring declaration"), null, 4, null);
            } else {
                FirElement firElementConvertElement = convertElement(initializer, null);
                if (firElementConvertElement instanceof FirExpression) {
                    firErrorExpressionMo6809build = toFirExpression(initializer, (FirExpression) firElementConvertElement);
                } else {
                    PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
                    FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                    firErrorExpressionBuilder.setNonExpressionElement(firElementConvertElement);
                    firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Initializer required for destructuring declaration"));
                    firErrorExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, initializer, null, 1, null));
                    firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
                }
            }
            return PsiConversionUtilsKt.generateDestructuringBlock(PsiRawFirBuilder.this.getBaseModuleData(), multiDeclaration, PsiConversionUtilsKt.generateTemporaryVariable(baseModuleData, firSourceElement$default, "destruct", firErrorExpressionMo6809build, new Function2<KtAnnotated, FirAnnotationContainerBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitDestructuringDeclaration$baseVariable$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(KtAnnotated ktAnnotated, FirAnnotationContainerBuilder firAnnotationContainerBuilder) {
                    invoke2(ktAnnotated, firAnnotationContainerBuilder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(KtAnnotated generateTemporaryVariable, FirAnnotationContainerBuilder it) {
                    Intrinsics.checkNotNullParameter(generateTemporaryVariable, "$this$generateTemporaryVariable");
                    Intrinsics.checkNotNullParameter(it, "it");
                    this.this$0.extractAnnotationsTo(generateTemporaryVariable, it);
                }
            }), true, true, new Function2<KtAnnotated, FirAnnotationContainerBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitDestructuringDeclaration$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(KtAnnotated ktAnnotated, FirAnnotationContainerBuilder firAnnotationContainerBuilder) {
                    invoke2(ktAnnotated, firAnnotationContainerBuilder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(KtAnnotated generateDestructuringBlock, FirAnnotationContainerBuilder it) {
                    Intrinsics.checkNotNullParameter(generateDestructuringBlock, "$this$generateDestructuringBlock");
                    Intrinsics.checkNotNullParameter(it, "it");
                    this.this$0.extractAnnotationsTo(generateDestructuringBlock, it);
                }
            }, new Function1<KtTypeReference, FirTypeRef>() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$Visitor$visitDestructuringDeclaration$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final FirTypeRef invoke(KtTypeReference ktTypeReference) {
                    return this.this$0.toFirOrImplicitType(ktTypeReference);
                }
            });
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitClassLiteralExpression(KtClassLiteralExpression expression, FirElement data) {
            FirErrorExpression firErrorExpressionMo6809build;
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirGetClassCallBuilder firGetClassCallBuilder = new FirGetClassCallBuilder();
            firGetClassCallBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
            KtExpression receiverExpression = expression.getReceiverExpression();
            if (receiverExpression == null) {
                firErrorExpressionMo6809build = FirExpressionUtilKt.buildErrorExpression$default((KtPsiSourceElement) AbstractRawFirBuilder.toFirSourceElement$default(PsiRawFirBuilder.this, expression, null, 1, null), ConeUnsupportedClassLiteralsWithEmptyLhs.INSTANCE, null, 4, null);
            } else {
                FirElement firElementConvertElement = convertElement(receiverExpression, null);
                if (firElementConvertElement instanceof FirExpression) {
                    firErrorExpressionMo6809build = toFirExpression(receiverExpression, (FirExpression) firElementConvertElement);
                } else {
                    PsiRawFirBuilder psiRawFirBuilder2 = PsiRawFirBuilder.this;
                    FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                    firErrorExpressionBuilder.setNonExpressionElement(firElementConvertElement);
                    firErrorExpressionBuilder.setDiagnostic(ConeUnsupportedClassLiteralsWithEmptyLhs.INSTANCE);
                    firErrorExpressionBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, receiverExpression, null, 1, null));
                    firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
                }
            }
            firGetClassCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firErrorExpressionMo6809build));
            return firGetClassCallBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitCallableReferenceExpression(KtCallableReferenceExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirCallableReferenceAccessBuilder firCallableReferenceAccessBuilder = new FirCallableReferenceAccessBuilder();
            PsiRawFirBuilder psiRawFirBuilder2 = psiRawFirBuilder;
            firCallableReferenceAccessBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, expression, null, 1, null));
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
            KtSimpleNameExpression callableReference = expression.getCallableReference();
            Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
            firSimpleNamedReferenceBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder2, callableReference, null, 1, null));
            firSimpleNamedReferenceBuilder.setName(expression.getCallableReference().getReferencedNameAsName());
            firCallableReferenceAccessBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
            KtExpression receiverExpression = expression.getReceiverExpression();
            firCallableReferenceAccessBuilder.setExplicitReceiver(receiverExpression != null ? toFirExpression$default(this, receiverExpression, "Incorrect receiver expression", null, 2, null) : null);
            firCallableReferenceAccessBuilder.setHasQuestionMarkAtLHS(expression.getHasQuestionMarks());
            return firCallableReferenceAccessBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitCollectionLiteralExpression(KtCollectionLiteralExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirArrayLiteralBuilder firArrayLiteralBuilder = new FirArrayLiteralBuilder();
            firArrayLiteralBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
            FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
            Iterator<KtExpression> it = expression.getInnerExpressions().iterator();
            while (it.hasNext()) {
                firArgumentListBuilder.getArguments().add(toFirExpression$default(this, it.next(), "Incorrect collection literal argument", null, 2, null));
            }
            firArrayLiteralBuilder.setArgumentList(firArgumentListBuilder.build());
            return firArrayLiteralBuilder.mo6809build();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitor
        public FirElement visitExpression(KtExpression expression, FirElement data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirExpressionStubBuilder firExpressionStubBuilder = new FirExpressionStubBuilder();
            firExpressionStubBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(psiRawFirBuilder, expression, null, 1, null));
            return firExpressionStubBuilder.mo6809build();
        }

        private final void appendTypeArguments(List<FirTypeProjection> list, List<? extends KtTypeProjection> list2) {
            Iterator<? extends KtTypeProjection> it = list2.iterator();
            while (it.hasNext()) {
                List<FirTypeProjection> list3 = list;
                FirElement firElementConvertElement = convertElement(it.next(), null);
                if (firElementConvertElement != null) {
                    list3.add((FirTypeProjection) firElementConvertElement);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirTypeProjection");
                }
            }
        }

        private final FirDanglingModifierList buildErrorTopLevelDeclarationForDanglingModifierList(KtModifierList modifierList) {
            PsiRawFirBuilder psiRawFirBuilder = PsiRawFirBuilder.this;
            FirDanglingModifierListBuilder firDanglingModifierListBuilder = new FirDanglingModifierListBuilder();
            firDanglingModifierListBuilder.setSource(psiRawFirBuilder.toFirSourceElement((PsiElement) modifierList, (KtFakeSourceElementKind) KtFakeSourceElementKind.DanglingModifierList.INSTANCE));
            firDanglingModifierListBuilder.setModuleData(psiRawFirBuilder.getBaseModuleData());
            firDanglingModifierListBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firDanglingModifierListBuilder.setDiagnostic(ConeDanglingModifierOnTopLevel.INSTANCE);
            firDanglingModifierListBuilder.setSymbol(new FirDanglingModifierSymbol());
            for (KtAnnotationEntry ktAnnotationEntry : modifierList.getAnnotationEntries()) {
                List<FirAnnotation> annotations = firDanglingModifierListBuilder.getAnnotations();
                Intrinsics.checkNotNull(ktAnnotationEntry);
                FirElement firElementConvertElement = convertElement(ktAnnotationEntry, null);
                if (firElementConvertElement != null) {
                    annotations.add((FirAnnotation) firElementConvertElement);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotation");
                }
            }
            return firDanglingModifierListBuilder.mo6809build();
        }
    }

    public final FirFile buildFirFile(final KtFile file) throws Throwable {
        Object objDisallowTreeLoading;
        Intrinsics.checkNotNullParameter(file, "file");
        int i = WhenMappings.$EnumSwitchMapping$0[getMode().ordinal()];
        if (i == 1) {
            Object objAccept = file.accept(new Visitor(), null);
            Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirFile");
            objDisallowTreeLoading = (FirFile) objAccept;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            objDisallowTreeLoading = AstLoadingFilter.disallowTreeLoading(new ThrowableComputable() { // from class: org.jetbrains.kotlin.fir.builder.PsiRawFirBuilder$buildFirFile$$inlined$runOnStubs$1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                public final T compute() {
                    Object objAccept2 = file.accept(this.new Visitor(), null);
                    Intrinsics.checkNotNull(objAccept2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirFile");
                    return (T) ((FirFile) objAccept2);
                }
            });
        }
        return (FirFile) objDisallowTreeLoading;
    }
}
