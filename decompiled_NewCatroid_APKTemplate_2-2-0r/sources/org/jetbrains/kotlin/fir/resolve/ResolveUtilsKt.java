package org.jetbrains.kotlin.fir.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.DelegatedWrapperData;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirFunctionTarget;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeAmbiguousFunctionTypeKinds;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeStubDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorResolvedQualifierBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirResolvedQualifierBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirResolvedReifiedParameterReferenceBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirReturnExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirSmartCastExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirUnitExpression;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedErrorReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirPropertyWithExplicitBackingFieldResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitDispatchReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.VisibilityUtilsKt;
import org.jetbrains.kotlin.fir.resolve.dfa.PropertyStability;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeHiddenCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableWrongReceiver;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeNoCompanionObject;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeVisibilityError;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.ImportedFromObjectOrStaticData;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirErrorFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirErrorPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeIntersector;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceKt;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.ForbiddenNamedArgumentsTarget;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.types.SmartcastStability;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ResolveUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000²\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001a>\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012\u001a \u0010\u0016\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0001\u001a\"\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0000\u001a\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!H\u0000\u001a\u0012\u0010\"\u001a\u00020#*\u00020$2\u0006\u0010\u001d\u001a\u00020\u001e\u001a\f\u0010%\u001a\u00020#*\u00020&H\u0002\u001a^\u0010'\u001a\u00020(*\u00020)2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030+2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-2\u000e\b\u0002\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u00122\n\b\u0002\u00100\u001a\u0004\u0018\u00010\b2\u000e\b\u0002\u00101\u001a\b\u0012\u0004\u0012\u00020\b0\u00122\u000e\b\u0002\u00102\u001a\b\u0012\u0004\u0012\u0002030\u0012\u001a\u0016\u00104\u001a\u00020\u000e*\u0002052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u001a\u001e\u00106\u001a\u000207*\u00020$2\u0006\u0010\u001d\u001a\u00020\u001e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u001a \u00108\u001a\u0004\u0018\u000109*\u0002052\u0006\u0010\u001d\u001a\u00020\u001e2\n\b\u0002\u0010:\u001a\u0004\u0018\u00010;\u001a\u0014\u0010<\u001a\u0004\u0018\u00010=*\u00020>2\u0006\u0010\u001d\u001a\u00020\u001e\u001a\u0014\u0010?\u001a\u0004\u0018\u00010@*\u0002032\u0006\u0010\u001d\u001a\u00020\u001e\u001a\u0018\u0010A\u001a\u0006\u0012\u0002\b\u00030B*\u00020C2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002\u001a\u0012\u0010D\u001a\u00020\u0005*\u00020)2\u0006\u0010\u000b\u001a\u00020\f\u001a\u0012\u0010D\u001a\u00020\u0005*\u0002072\u0006\u0010\u000b\u001a\u00020\f\u001a\u0014\u0010E\u001a\u00020\u0001*\u00020F2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002\u001a\u001d\u0010G\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000\u001a\"\u0010H\u001a\u00020#*\u00020I2\u0006\u0010J\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010K\u001a\u00020L\u001a\u001a\u0010M\u001a\u00020#*\u00020N2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010K\u001a\u00020L\u001a\u0018\u0010O\u001a\u00020\u0001*\u00020$2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020\u00020Q\u001a\u0012\u0010R\u001a\u00020S*\u00020T2\u0006\u00100\u001a\u00020\b\u001a\u0012\u0010U\u001a\u00020\u0002*\u00020)2\u0006\u0010V\u001a\u00020W\u001a;\u0010X\u001a\u0004\u0018\u00010Y\"\b\b\u0000\u0010Z*\u00020\u0002*\u00020)2\u0006\u0010V\u001a\u0002HZ2\u0006\u0010[\u001a\u00020\\2\f\u0010]\u001a\b\u0012\u0004\u0012\u00020\u00050^H\u0002¢\u0006\u0002\u0010_\u001a\u0012\u0010`\u001a\u00020\u0002*\u00020)2\u0006\u0010a\u001a\u00020b\u001a\u0012\u0010c\u001a\u00020\u0002*\u00020)2\u0006\u0010d\u001a\u00020e\u001a\u0012\u0010f\u001a\u00020\u0019*\u00020)2\u0006\u0010g\u001a\u00020(\u001a!\u0010h\u001a\u000207\"\b\b\u0000\u0010Z*\u00020i*\u00020)2\u0006\u0010j\u001a\u0002HZ¢\u0006\u0002\u0010k\u001a\u001a\u0010h\u001a\u000207*\u00020)2\u0006\u0010j\u001a\u00020l2\u0006\u0010m\u001a\u00020n\u001a\u0018\u0010o\u001a\u000207*\u00020)2\n\u0010p\u001a\u0006\u0012\u0002\b\u00030BH\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0001*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006¨\u0006q"}, d2 = {"isCallToStatementLikeFunction", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Z", "isKindOfNothing", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "createConeDiagnosticForCandidateWithError", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "createFunctionType", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "parameters", "", "receiverType", "rawReturnType", "contextReceivers", "createKPropertyType", "isMutable", "typeForQualifierByDeclaration", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "resultType", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "typeForReifiedParameterReference", "parameterReferenceBuilder", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirResolvedReifiedParameterReferenceBuilder;", "addReturnToLastStatementIfNeeded", "", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "applyResultTypeRef", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirSmartCastExpressionBuilder;", "buildResolvedQualifierForClass", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "regularClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "sourceElement", "Lorg/jetbrains/kotlin/KtSourceElement;", "typeArgumentsForQualifier", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "diagnostic", "nonFatalDiagnostics", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "constructFunctionType", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "constructFunctionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "getAsForbiddenNamedArgumentsTarget", "Lorg/jetbrains/kotlin/resolve/ForbiddenNamedArgumentsTarget;", "originScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "getContainingClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getCorrespondingClassSymbolOrNull", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getNarrowedDownSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirPropertyWithExplicitBackingFieldResolvedNamedReference;", "initialTypeOfCandidate", "isEffectivelyFinal", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "isIntegerLiteralOrOperatorCall", "propagateTypeFromOriginalReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "nullableReceiverExpression", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "propagateTypeFromQualifiedAccessAfterNullCheck", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "shouldReturnUnit", "returnStatements", "", "toErrorReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;", "transformDesugaredAssignmentValueUsingSmartcastInfo", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "transformExpressionUsingSmartcastInfo", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", ExifInterface.GPS_DIRECTION_TRUE, "stability", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PropertyStability;", "typesFromSmartCast", "", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/resolve/dfa/PropertyStability;Ljava/util/List;)Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "transformQualifiedAccessUsingSmartcastInfo", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "transformWhenSubjectExpressionUsingSmartcastInfo", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "typeForQualifier", "resolvedQualifier", "typeFromCallee", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "access", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "Lorg/jetbrains/kotlin/fir/FirElement;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "typeFromSymbol", NativeSymbol.TYPE_NAME, "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolveUtilsKt {

    /* JADX INFO: compiled from: ResolveUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CandidateApplicability.values().length];
            try {
                iArr[CandidateApplicability.HIDDEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CandidateApplicability.K2_VISIBILITY_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CandidateApplicability.K2_NO_COMPANION_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean shouldReturnUnit(FirAnonymousFunction firAnonymousFunction, Collection<? extends FirExpression> returnStatements) {
        Intrinsics.checkNotNullParameter(firAnonymousFunction, "<this>");
        Intrinsics.checkNotNullParameter(returnStatements, "returnStatements");
        if (firAnonymousFunction.getIsLambda()) {
            Collection<? extends FirExpression> collection = returnStatements;
            if (!collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (it.hasNext()) {
                    if (((FirExpression) it.next()) instanceof FirUnitExpression) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final void addReturnToLastStatementIfNeeded(FirAnonymousFunction firAnonymousFunction, FirSession session) {
        FirBlock body;
        Intrinsics.checkNotNullParameter(firAnonymousFunction, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (ConeBuiltinTypeUtilsKt.isUnit(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firAnonymousFunction.getReturnTypeRef()), session)) || (body = firAnonymousFunction.getBody()) == null) {
            return;
        }
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) body.getStatements());
        final FirExpression firExpression = objLastOrNull instanceof FirExpression ? (FirExpression) objLastOrNull : null;
        if (firExpression == null || (firExpression instanceof FirReturnExpression)) {
            return;
        }
        FirTypeRef typeRef = body.getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        if (firResolvedTypeRef == null || FirTypeUtilsKt.isNothing(firResolvedTypeRef)) {
            return;
        }
        FirFunctionTarget firFunctionTarget = new FirFunctionTarget(null, firAnonymousFunction.getIsLambda());
        firFunctionTarget.bind(firAnonymousFunction);
        FirReturnExpressionBuilder firReturnExpressionBuilder = new FirReturnExpressionBuilder();
        KtSourceElement source = firExpression.getSource();
        firReturnExpressionBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitReturn.FromLastStatement.INSTANCE, 0, 0, 6, null) : null);
        firReturnExpressionBuilder.setResult(firExpression);
        firReturnExpressionBuilder.setTarget(firFunctionTarget);
        final FirReturnExpression firReturnExpressionBuild = firReturnExpressionBuilder.mo6809build();
        body.transformStatements(new FirTransformer() { // from class: org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt.addReturnToLastStatementIfNeeded.1
            @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
            public <E extends FirElement> E transformElement(E element, Void data) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (!Intrinsics.areEqual(element, firExpression)) {
                    return element;
                }
                FirReturnExpression firReturnExpression = firReturnExpressionBuild;
                Intrinsics.checkNotNull(firReturnExpression, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt.addReturnToLastStatementIfNeeded.<no name provided>.transformElement");
                return firReturnExpression;
            }
        }, null);
    }

    public static /* synthetic */ ConeLookupTagBasedType constructFunctionType$default(FirFunction firFunction, FunctionTypeKind functionTypeKind, int i, Object obj) {
        if ((i & 1) != 0) {
            functionTypeKind = null;
        }
        return constructFunctionType(firFunction, functionTypeKind);
    }

    public static final ConeLookupTagBasedType constructFunctionType(FirFunction firFunction, FunctionTypeKind functionTypeKind) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        FirReceiverParameter receiverParameter = ((firFunction instanceof FirSimpleFunction) || (firFunction instanceof FirAnonymousFunction)) ? firFunction.getReceiverParameter() : null;
        FirTypeRef typeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
        List<FirValueParameter> valueParameters = firFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            FirTypeRef returnTypeRef = ((FirValueParameter) it.next()).getReturnTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeErrorType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            if (type == null) {
                type = new ConeErrorType(new ConeSimpleDiagnostic("No type for parameter", DiagnosticKind.ValueParameterWithNoTypeAnnotation), false, null, null, 14, null);
            }
            arrayList.add(type);
        }
        ArrayList arrayList2 = arrayList;
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firFunction.getReturnTypeRef());
        if (functionTypeKind == null) {
            functionTypeKind = FunctionTypeKind.Function.INSTANCE;
        }
        ConeKotlinType coneType2 = typeRef != null ? FirTypeUtilsKt.getConeType(typeRef) : null;
        List<FirContextReceiver> contextReceivers = firFunction.getContextReceivers();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        Iterator<T> it2 = contextReceivers.iterator();
        while (it2.hasNext()) {
            arrayList3.add(FirTypeUtilsKt.getConeType(((FirContextReceiver) it2.next()).getTypeRef()));
        }
        return createFunctionType(functionTypeKind, arrayList2, coneType2, coneType, arrayList3);
    }

    public static /* synthetic */ FirResolvedTypeRef constructFunctionTypeRef$default(FirAnonymousFunction firAnonymousFunction, FirSession firSession, FunctionTypeKind functionTypeKind, int i, Object obj) {
        if ((i & 2) != 0) {
            functionTypeKind = null;
        }
        return constructFunctionTypeRef(firAnonymousFunction, firSession, functionTypeKind);
    }

    public static final FirResolvedTypeRef constructFunctionTypeRef(FirAnonymousFunction firAnonymousFunction, FirSession session, FunctionTypeKind functionTypeKind) {
        FunctionTypeKind.Function function;
        ConeAmbiguousFunctionTypeKinds coneAmbiguousFunctionTypeKinds;
        Intrinsics.checkNotNullParameter(firAnonymousFunction, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        List<FunctionTypeKind> listExtractAllSpecialKindsForFunction = FirFunctionTypeKindServiceKt.getFunctionTypeService(session).extractAllSpecialKindsForFunction(firAnonymousFunction.getSymbol());
        int size = listExtractAllSpecialKindsForFunction.size();
        if (size == 0) {
            function = null;
            coneAmbiguousFunctionTypeKinds = null;
        } else if (size == 1) {
            function = (FunctionTypeKind) CollectionsKt.single((List) listExtractAllSpecialKindsForFunction);
            coneAmbiguousFunctionTypeKinds = null;
        } else {
            coneAmbiguousFunctionTypeKinds = new ConeAmbiguousFunctionTypeKinds(listExtractAllSpecialKindsForFunction);
            function = FunctionTypeKind.Function.INSTANCE;
        }
        FirAnonymousFunction firAnonymousFunction2 = firAnonymousFunction;
        if (function != null) {
            functionTypeKind = function;
        }
        ConeLookupTagBasedType coneLookupTagBasedTypeConstructFunctionType = constructFunctionType(firAnonymousFunction2, functionTypeKind);
        KtSourceElement source = firAnonymousFunction.getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null) : null;
        if (coneAmbiguousFunctionTypeKinds == null) {
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setSource(ktSourceElementFakeElement$default);
            firResolvedTypeRefBuilder.setType(coneLookupTagBasedTypeConstructFunctionType);
            return firResolvedTypeRefBuilder.mo6809build();
        }
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setSource(ktSourceElementFakeElement$default);
        firErrorTypeRefBuilder.setType(coneLookupTagBasedTypeConstructFunctionType);
        firErrorTypeRefBuilder.setDiagnostic(coneAmbiguousFunctionTypeKinds);
        return firErrorTypeRefBuilder.mo6809build();
    }

    public static /* synthetic */ ConeLookupTagBasedType createFunctionType$default(FunctionTypeKind functionTypeKind, List list, ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, List list2, int i, Object obj) {
        if ((i & 16) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        return createFunctionType(functionTypeKind, list, coneKotlinType, coneKotlinType2, list2);
    }

    public static final ConeLookupTagBasedType createFunctionType(FunctionTypeKind kind, List<? extends ConeKotlinType> parameters, ConeKotlinType coneKotlinType, ConeKotlinType rawReturnType, List<? extends ConeKotlinType> contextReceivers) {
        ConeAttributes empty;
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(rawReturnType, "rawReturnType");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        List<? extends ConeKotlinType> list = contextReceivers;
        listCreateListBuilder.addAll(list);
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(listCreateListBuilder, coneKotlinType);
        listCreateListBuilder.addAll(parameters);
        listCreateListBuilder.add(rawReturnType);
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        ClassId classId = new ClassId(kind.getPackageFqName(), kind.numberedClassName(listBuild.size() - 1));
        if (!list.isEmpty()) {
            ConeAttributes.Companion companion = ConeAttributes.INSTANCE;
            List listCreateListBuilder2 = CollectionsKt.createListBuilder();
            listCreateListBuilder2.add(new CompilerConeAttributes.ContextFunctionTypeParams(contextReceivers.size()));
            if (coneKotlinType != null) {
                listCreateListBuilder2.add(CompilerConeAttributes.ExtensionFunctionType.INSTANCE);
            }
            empty = companion.create(CollectionsKt.build(listCreateListBuilder2));
        } else if (coneKotlinType != null) {
            empty = ConeAttributes.INSTANCE.getWithExtensionFunctionType();
        } else {
            empty = ConeAttributes.INSTANCE.getEmpty();
        }
        return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(classId), (ConeTypeProjection[]) listBuild.toArray(new ConeKotlinType[0]), false, empty);
    }

    public static final ConeLookupTagBasedType createKPropertyType(ConeKotlinType coneKotlinType, ConeKotlinType rawReturnType, boolean z) {
        Intrinsics.checkNotNullParameter(rawReturnType, "rawReturnType");
        List listListOf = coneKotlinType != null ? CollectionsKt.listOf((Object[]) new ConeKotlinType[]{coneKotlinType, rawReturnType}) : CollectionsKt.listOf(rawReturnType);
        return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.reflectByName("K" + (z ? "Mutable" : "") + "Property" + (listListOf.size() - 1))), (ConeTypeProjection[]) listListOf.toArray(new ConeKotlinType[0]), false, null, 8, null);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
    public static final FirResolvedQualifier buildResolvedQualifierForClass(BodyResolveComponents bodyResolveComponents, FirClassLikeSymbol<?> regularClass, KtSourceElement ktSourceElement, List<? extends FirTypeProjection> typeArgumentsForQualifier, ConeDiagnostic coneDiagnostic, List<? extends ConeDiagnostic> nonFatalDiagnostics, List<? extends FirAnnotation> annotations) {
        FirErrorResolvedQualifierBuilder firResolvedQualifierBuilder;
        FirImplicitBuiltinTypeRef firImplicitBuiltinTypeRefTypeForQualifier;
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(typeArgumentsForQualifier, "typeArgumentsForQualifier");
        Intrinsics.checkNotNullParameter(nonFatalDiagnostics, "nonFatalDiagnostics");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        ClassId classId = regularClass.getClassId();
        if (coneDiagnostic == null) {
            firResolvedQualifierBuilder = new FirResolvedQualifierBuilder();
        } else {
            FirErrorResolvedQualifierBuilder firErrorResolvedQualifierBuilder = new FirErrorResolvedQualifierBuilder();
            firErrorResolvedQualifierBuilder.setDiagnostic(coneDiagnostic);
            firResolvedQualifierBuilder = firErrorResolvedQualifierBuilder;
        }
        firResolvedQualifierBuilder.setSource(ktSourceElement);
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        firResolvedQualifierBuilder.setPackageFqName(packageFqName);
        firResolvedQualifierBuilder.setRelativeClassFqName(classId.getRelativeClassName());
        firResolvedQualifierBuilder.getTypeArguments().addAll(typeArgumentsForQualifier);
        firResolvedQualifierBuilder.setSymbol(regularClass);
        firResolvedQualifierBuilder.getNonFatalDiagnostics().addAll(nonFatalDiagnostics);
        firResolvedQualifierBuilder.getAnnotations().addAll(annotations);
        FirResolvedQualifier firResolvedQualifierBuild = firResolvedQualifierBuilder.build();
        FirResolvedQualifier firResolvedQualifier = firResolvedQualifierBuild;
        if (classId.isLocal()) {
            firImplicitBuiltinTypeRefTypeForQualifier = typeForQualifierByDeclaration(regularClass.getFir(), firResolvedQualifier.getTypeRef(), bodyResolveComponents.getSession());
            if (firImplicitBuiltinTypeRefTypeForQualifier == null) {
                firImplicitBuiltinTypeRefTypeForQualifier = bodyResolveComponents.getSession().getBuiltinTypes().getUnitType();
            }
        } else {
            firImplicitBuiltinTypeRefTypeForQualifier = typeForQualifier(bodyResolveComponents, firResolvedQualifierBuild);
        }
        firResolvedQualifier.replaceTypeRef(firImplicitBuiltinTypeRefTypeForQualifier);
        return firResolvedQualifierBuild;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirTypeRef typeForQualifier(BodyResolveComponents bodyResolveComponents, FirResolvedQualifier resolvedQualifier) {
        FirTypeRef firTypeRefTypeForQualifierByDeclaration;
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        FirClassLikeSymbol<?> symbol = resolvedQualifier.getSymbol();
        FirTypeRef typeRef = resolvedQualifier.getTypeRef();
        if (symbol != null) {
            FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol, FirResolvePhase.TYPES);
            FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir();
            if ((!(firClassLikeDeclaration instanceof FirTypeAlias) || resolvedQualifier.getTypeArguments().isEmpty()) && (firTypeRefTypeForQualifierByDeclaration = typeForQualifierByDeclaration(firClassLikeDeclaration, typeRef, bodyResolveComponents.getSession())) != null) {
                return firTypeRefTypeForQualifierByDeclaration;
            }
        }
        return bodyResolveComponents.getSession().getBuiltinTypes().getUnitType();
    }

    public static final FirTypeRef typeForReifiedParameterReference(FirResolvedReifiedParameterReferenceBuilder parameterReferenceBuilder) {
        Intrinsics.checkNotNullParameter(parameterReferenceBuilder, "parameterReferenceBuilder");
        return CopyUtilsKt.resolvedTypeFromPrototype$default(parameterReferenceBuilder.getTypeRef(), TypeConstructionUtilsKt.constructType$default((FirClassifierSymbol) parameterReferenceBuilder.getSymbol(), new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null), null, 2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirTypeRef typeForQualifierByDeclaration(FirDeclaration declaration, FirTypeRef resultType, FirSession session) {
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        FirClassLikeDeclaration firClassLikeDeclaration;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        Intrinsics.checkNotNullParameter(session, "session");
        if (declaration instanceof FirTypeAlias) {
            ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType((FirTypeAlias) declaration);
            if (expandedConeType == null || (lookupTag = expandedConeType.getLookupTag()) == null || (symbol = LookupTagUtilsKt.toSymbol(lookupTag, session)) == null || (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir()) == null) {
                return null;
            }
            return typeForQualifierByDeclaration(firClassLikeDeclaration, resultType, session);
        }
        if (declaration instanceof FirRegularClass) {
            FirRegularClass firRegularClass = (FirRegularClass) declaration;
            if (firRegularClass.getClassKind() == ClassKind.OBJECT) {
                return CopyUtilsKt.resolvedTypeFromPrototype$default(resultType, TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) firRegularClass.getSymbol(), new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null), null, 2, null);
            }
            FirRegularClassSymbol companionObjectSymbol = firRegularClass.getCompanionObjectSymbol();
            if (companionObjectSymbol != null) {
                return CopyUtilsKt.resolvedTypeFromPrototype$default(resultType, TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) companionObjectSymbol, new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null), null, 2, null);
            }
        }
        return null;
    }

    private static final boolean isEffectivelyFinal(FirPropertySymbol firPropertySymbol, FirSession firSession) {
        FirRegularClassSymbol regularClassSymbol;
        Modality modality = firPropertySymbol.getResolvedStatus().getModality();
        if (modality == null || modality == Modality.FINAL) {
            return true;
        }
        ConeSimpleKotlinType dispatchReceiverType = firPropertySymbol.getDispatchReceiverType();
        if (dispatchReceiverType == null || (regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(dispatchReceiverType, firSession)) == null) {
            return false;
        }
        return regularClassSymbol.getResolvedStatus().getModality() == Modality.FINAL && regularClassSymbol.getClassKind() != ClassKind.ENUM_CLASS;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirBasedSymbol<?> getNarrowedDownSymbol(FirPropertyWithExplicitBackingFieldResolvedNamedReference firPropertyWithExplicitBackingFieldResolvedNamedReference, FirSession firSession) {
        FirBackingFieldSymbol symbol;
        FirBasedSymbol<?> resolvedSymbol = firPropertyWithExplicitBackingFieldResolvedNamedReference.getResolvedSymbol();
        FirPropertySymbol firPropertySymbol = resolvedSymbol instanceof FirPropertySymbol ? (FirPropertySymbol) resolvedSymbol : null;
        if (firPropertySymbol == null) {
            return firPropertyWithExplicitBackingFieldResolvedNamedReference.getResolvedSymbol();
        }
        if (!(((FirProperty) firPropertySymbol.getFir()).getReturnTypeRef() instanceof FirImplicitTypeRef)) {
            FirBackingField backingField = ((FirProperty) firPropertySymbol.getFir()).getBackingField();
            if (!((backingField != null ? backingField.getReturnTypeRef() : null) instanceof FirImplicitTypeRef)) {
                if (isEffectivelyFinal(firPropertySymbol, firSession) && firPropertyWithExplicitBackingFieldResolvedNamedReference.getHasVisibleBackingField() && DeclarationAttributesKt.getCanNarrowDownGetterType(firPropertySymbol)) {
                    FirBackingField backingField2 = ((FirProperty) firPropertySymbol.getFir()).getBackingField();
                    return (backingField2 == null || (symbol = backingField2.getSymbol()) == null) ? firPropertyWithExplicitBackingFieldResolvedNamedReference.getResolvedSymbol() : symbol;
                }
                return firPropertyWithExplicitBackingFieldResolvedNamedReference.getResolvedSymbol();
            }
        }
        return firPropertyWithExplicitBackingFieldResolvedNamedReference.getResolvedSymbol();
    }

    public static final <T extends FirResolvable> FirResolvedTypeRef typeFromCallee(BodyResolveComponents bodyResolveComponents, T access) {
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(access, "access");
        return typeFromCallee(bodyResolveComponents, access, access.getCalleeReference());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirResolvedTypeRef typeFromCallee(BodyResolveComponents bodyResolveComponents, FirElement access, FirReference calleeReference) {
        ImplicitDispatchReceiverValue implicitDispatchReceiverValueLastDispatchReceiver;
        FirClassSymbol<?> boundSymbol;
        FirClass firClass;
        List<FirTypeRef> superTypeRefs;
        ConeErrorType coneErrorType;
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(access, "access");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        FirResolvedTypeRef firResolvedTypeRef = null;
        if (!(calleeReference instanceof FirErrorNamedReference)) {
            if (calleeReference instanceof FirNamedReferenceWithCandidate) {
                return typeFromSymbol(bodyResolveComponents, ((FirNamedReferenceWithCandidate) calleeReference).getCandidateSymbol());
            }
            if (calleeReference instanceof FirPropertyWithExplicitBackingFieldResolvedNamedReference) {
                return typeFromSymbol(bodyResolveComponents, getNarrowedDownSymbol((FirPropertyWithExplicitBackingFieldResolvedNamedReference) calleeReference, bodyResolveComponents.getSession()));
            }
            if (calleeReference instanceof FirResolvedNamedReference) {
                return typeFromSymbol(bodyResolveComponents, ((FirResolvedNamedReference) calleeReference).getResolvedSymbol());
            }
            if (calleeReference instanceof FirThisReference) {
                String labelName = ((FirThisReference) calleeReference).getLabelName();
                ImplicitReceiverValue<?> implicitReceiverValue = bodyResolveComponents.getImplicitReceiverStack().get(labelName);
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setSource(null);
                if (implicitReceiverValue == null || (coneErrorType = implicitReceiverValue.getType()) == null) {
                    coneErrorType = new ConeErrorType(new ConeSimpleDiagnostic("Unresolved this@" + labelName, DiagnosticKind.UnresolvedLabel), false, null, null, 14, null);
                }
                firResolvedTypeRefBuilder.setType(coneErrorType);
                return firResolvedTypeRefBuilder.mo6809build();
            }
            if (calleeReference instanceof FirSuperReference) {
                FirSuperReference firSuperReference = (FirSuperReference) calleeReference;
                String labelName2 = firSuperReference.getLabelName();
                if (labelName2 != null) {
                    ImplicitReceiverValue<?> implicitReceiverValue2 = bodyResolveComponents.getImplicitReceiverStack().get(labelName2);
                    implicitDispatchReceiverValueLastDispatchReceiver = implicitReceiverValue2 instanceof ImplicitDispatchReceiverValue ? (ImplicitDispatchReceiverValue) implicitReceiverValue2 : null;
                } else {
                    implicitDispatchReceiverValueLastDispatchReceiver = bodyResolveComponents.getImplicitReceiverStack().lastDispatchReceiver();
                }
                FirTypeRef superTypeRef = firSuperReference.getSuperTypeRef();
                FirResolvedTypeRef firResolvedTypeRef2 = superTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) superTypeRef : null;
                if (firResolvedTypeRef2 == null) {
                    FirTypeRef firTypeRef = (implicitDispatchReceiverValueLastDispatchReceiver == null || (boundSymbol = implicitDispatchReceiverValueLastDispatchReceiver.getBoundSymbol()) == null || (firClass = (FirClass) boundSymbol.getFir()) == null || (superTypeRefs = firClass.getSuperTypeRefs()) == null) ? null : (FirTypeRef) CollectionsKt.singleOrNull((List) superTypeRefs);
                    if (firTypeRef instanceof FirResolvedTypeRef) {
                        firResolvedTypeRef = (FirResolvedTypeRef) firTypeRef;
                    }
                } else {
                    firResolvedTypeRef = firResolvedTypeRef2;
                }
                if (firResolvedTypeRef != null) {
                    return firResolvedTypeRef;
                }
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setSource(calleeReference.getSource());
                Name nameIdentifier = Name.identifier(PsiKeyword.SUPER);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                firErrorTypeRefBuilder.setDiagnostic(new ConeUnresolvedNameError(nameIdentifier));
                return firErrorTypeRefBuilder.mo6809build();
            }
            throw new IllegalStateException(("Failed to extract type from: " + calleeReference).toString());
        }
        FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
        KtSourceElement source = access.getSource();
        firErrorTypeRefBuilder2.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ErrorTypeRef.INSTANCE, 0, 0, 6, null) : null);
        firErrorTypeRefBuilder2.setDiagnostic(new ConeStubDiagnostic(((FirErrorNamedReference) calleeReference).getDiagnostic()));
        return firErrorTypeRefBuilder2.mo6809build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirResolvedTypeRef typeFromSymbol(BodyResolveComponents bodyResolveComponents, FirBasedSymbol<?> firBasedSymbol) {
        if (firBasedSymbol instanceof FirCallableSymbol) {
            return (FirResolvedTypeRef) UtilsKt.copyWithNewSourceKind(bodyResolveComponents.getReturnTypeCalculator().tryCalculateReturnType((FirCallableDeclaration) ((FirCallableSymbol) firBasedSymbol).getFir()), KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE);
        }
        if (firBasedSymbol instanceof FirClassifierSymbol) {
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setSource(null);
            firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructType$default((FirClassifierSymbol) firBasedSymbol, new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null));
            return firResolvedTypeRefBuilder.mo6809build();
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Failed to extract type from symbol: " + firBasedSymbol.getClass(), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "declaration", firBasedSymbol.getFir());
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final FirExpression transformQualifiedAccessUsingSmartcastInfo(BodyResolveComponents bodyResolveComponents, FirQualifiedAccessExpression qualifiedAccessExpression) {
        FirSmartCastExpression firSmartCastExpressionTransformExpressionUsingSmartcastInfo;
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        FirQualifiedAccessExpression firQualifiedAccessExpression = qualifiedAccessExpression;
        Pair<PropertyStability, List<ConeKotlinType>> typeUsingSmartcastInfo = bodyResolveComponents.getDataFlowAnalyzer().getTypeUsingSmartcastInfo(firQualifiedAccessExpression);
        return (typeUsingSmartcastInfo == null || (firSmartCastExpressionTransformExpressionUsingSmartcastInfo = transformExpressionUsingSmartcastInfo(bodyResolveComponents, firQualifiedAccessExpression, typeUsingSmartcastInfo.component1(), typeUsingSmartcastInfo.component2())) == null) ? firQualifiedAccessExpression : firSmartCastExpressionTransformExpressionUsingSmartcastInfo;
    }

    public static final FirExpression transformWhenSubjectExpressionUsingSmartcastInfo(BodyResolveComponents bodyResolveComponents, FirWhenSubjectExpression whenSubjectExpression) {
        FirSmartCastExpression firSmartCastExpressionTransformExpressionUsingSmartcastInfo;
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        FirWhenSubjectExpression firWhenSubjectExpression = whenSubjectExpression;
        Pair<PropertyStability, List<ConeKotlinType>> typeUsingSmartcastInfo = bodyResolveComponents.getDataFlowAnalyzer().getTypeUsingSmartcastInfo(firWhenSubjectExpression);
        return (typeUsingSmartcastInfo == null || (firSmartCastExpressionTransformExpressionUsingSmartcastInfo = transformExpressionUsingSmartcastInfo(bodyResolveComponents, firWhenSubjectExpression, typeUsingSmartcastInfo.component1(), typeUsingSmartcastInfo.component2())) == null) ? firWhenSubjectExpression : firSmartCastExpressionTransformExpressionUsingSmartcastInfo;
    }

    public static final FirExpression transformDesugaredAssignmentValueUsingSmartcastInfo(BodyResolveComponents bodyResolveComponents, FirDesugaredAssignmentValueReferenceExpression expression) {
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Pair<PropertyStability, List<ConeKotlinType>> typeUsingSmartcastInfo = bodyResolveComponents.getDataFlowAnalyzer().getTypeUsingSmartcastInfo(expression.getExpressionRef().getValue());
        if (typeUsingSmartcastInfo == null) {
            return expression;
        }
        FirDesugaredAssignmentValueReferenceExpression firDesugaredAssignmentValueReferenceExpression = expression;
        FirSmartCastExpression firSmartCastExpressionTransformExpressionUsingSmartcastInfo = transformExpressionUsingSmartcastInfo(bodyResolveComponents, firDesugaredAssignmentValueReferenceExpression, typeUsingSmartcastInfo.component1(), typeUsingSmartcastInfo.component2());
        return firSmartCastExpressionTransformExpressionUsingSmartcastInfo != null ? firSmartCastExpressionTransformExpressionUsingSmartcastInfo : firDesugaredAssignmentValueReferenceExpression;
    }

    private static final boolean isKindOfNothing(ConeKotlinType coneKotlinType) {
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType);
        return ConeBuiltinTypeUtilsKt.isNothing(coneSimpleKotlinTypeLowerBoundIfFlexible) || ConeBuiltinTypeUtilsKt.isNullableNothing(coneSimpleKotlinTypeLowerBoundIfFlexible);
    }

    private static final void applyResultTypeRef(FirSmartCastExpressionBuilder firSmartCastExpressionBuilder) {
        FirTypeRef firTypeRefCopyWithNewSourceKind;
        if (firSmartCastExpressionBuilder.getSmartcastStability() == SmartcastStability.STABLE_VALUE) {
            firTypeRefCopyWithNewSourceKind = UtilsKt.copyWithNewSourceKind(firSmartCastExpressionBuilder.getSmartcastType(), KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE);
        } else {
            firTypeRefCopyWithNewSourceKind = UtilsKt.copyWithNewSourceKind(firSmartCastExpressionBuilder.getOriginalExpression().getTypeRef(), KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE);
        }
        firSmartCastExpressionBuilder.setTypeRef(firTypeRefCopyWithNewSourceKind);
    }

    private static final <T extends FirExpression> FirSmartCastExpression transformExpressionUsingSmartcastInfo(BodyResolveComponents bodyResolveComponents, T t, PropertyStability propertyStability, List<ConeKotlinType> list) {
        SmartcastStability impliedSmartcastStability = propertyStability.getImpliedSmartcastStability();
        if (impliedSmartcastStability == null) {
            if (bodyResolveComponents.getDataFlowAnalyzer().isAccessToUnstableLocalVariable(t)) {
                impliedSmartcastStability = SmartcastStability.CAPTURED_VARIABLE;
            } else {
                impliedSmartcastStability = SmartcastStability.STABLE_VALUE;
            }
        }
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(t.getTypeRef()), bodyResolveComponents.getSession());
        boolean z = coneKotlinTypeFullyExpandedType instanceof ConeStubType;
        if (!z) {
            list.add(TypeExpansionUtilsKt.fullyExpandedType(coneKotlinTypeFullyExpandedType, bodyResolveComponents.getSession()));
        }
        List<ConeKotlinType> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (!(((ConeKotlinType) it.next()) instanceof ConeDynamicType)) {
                    List<ConeKotlinType> list3 = list;
                    ConeKotlinType coneKotlinTypeIntersectTypes = ConeTypeIntersector.INSTANCE.intersectTypes(TypeComponentsKt.getTypeContext(bodyResolveComponents.getSession()), list3);
                    if (Intrinsics.areEqual(coneKotlinTypeIntersectTypes, coneKotlinTypeFullyExpandedType) && !(coneKotlinTypeIntersectTypes instanceof ConeDynamicType)) {
                        return null;
                    }
                    FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                    KtSourceElement source = t.getTypeRef().getSource();
                    firResolvedTypeRefBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.SmartCastedTypeRef.INSTANCE, 0, 0, 6, null) : null);
                    firResolvedTypeRefBuilder.setType(coneKotlinTypeIntersectTypes);
                    CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), t.getTypeRef().getAnnotations());
                    firResolvedTypeRefBuilder.setDelegatedTypeRef(t.getTypeRef());
                    FirResolvedTypeRef firResolvedTypeRefBuild = firResolvedTypeRefBuilder.mo6809build();
                    if (isKindOfNothing(coneKotlinTypeIntersectTypes) && !ConeBuiltinTypeUtilsKt.isNullableNothing(coneKotlinTypeFullyExpandedType) && !ConeBuiltinTypeUtilsKt.isNothing(coneKotlinTypeFullyExpandedType) && !z) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : list2) {
                            if (!isKindOfNothing((ConeKotlinType) obj)) {
                                arrayList.add(obj);
                            }
                        }
                        ConeKotlinType coneKotlinTypeIntersectTypes2 = ConeTypeIntersector.INSTANCE.intersectTypes(TypeComponentsKt.getTypeContext(bodyResolveComponents.getSession()), arrayList);
                        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
                        KtSourceElement source2 = t.getTypeRef().getSource();
                        firResolvedTypeRefBuilder2.setSource(source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.SmartCastedTypeRef.INSTANCE, 0, 0, 6, null) : null);
                        firResolvedTypeRefBuilder2.setType(coneKotlinTypeIntersectTypes2);
                        CollectionsKt.addAll(firResolvedTypeRefBuilder2.getAnnotations(), t.getTypeRef().getAnnotations());
                        firResolvedTypeRefBuilder2.setDelegatedTypeRef(t.getTypeRef());
                        FirResolvedTypeRef firResolvedTypeRefBuild2 = firResolvedTypeRefBuilder2.mo6809build();
                        FirSmartCastExpressionBuilder firSmartCastExpressionBuilder = new FirSmartCastExpressionBuilder();
                        firSmartCastExpressionBuilder.setOriginalExpression(t);
                        firSmartCastExpressionBuilder.setSmartcastType(firResolvedTypeRefBuild);
                        firSmartCastExpressionBuilder.setSmartcastTypeWithoutNullableNothing(firResolvedTypeRefBuild2);
                        firSmartCastExpressionBuilder.setTypesFromSmartCast(list3);
                        firSmartCastExpressionBuilder.setSmartcastStability(impliedSmartcastStability);
                        applyResultTypeRef(firSmartCastExpressionBuilder);
                        return firSmartCastExpressionBuilder.mo6809build();
                    }
                    FirSmartCastExpressionBuilder firSmartCastExpressionBuilder2 = new FirSmartCastExpressionBuilder();
                    firSmartCastExpressionBuilder2.setOriginalExpression(t);
                    firSmartCastExpressionBuilder2.setSmartcastType(firResolvedTypeRefBuild);
                    firSmartCastExpressionBuilder2.setTypesFromSmartCast(list3);
                    firSmartCastExpressionBuilder2.setSmartcastStability(impliedSmartcastStability);
                    applyResultTypeRef(firSmartCastExpressionBuilder2);
                    return firSmartCastExpressionBuilder2.mo6809build();
                }
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0031  */
    public static final void propagateTypeFromOriginalReceiver(FirCheckedSafeCallSubject firCheckedSafeCallSubject, FirExpression nullableReceiverExpression, FirSession session, FirFile file) {
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(firCheckedSafeCallSubject, "<this>");
        Intrinsics.checkNotNullParameter(nullableReceiverExpression, "nullableReceiverExpression");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(file, "file");
        FirSmartCastExpression firSmartCastExpression = nullableReceiverExpression instanceof FirSmartCastExpression ? (FirSmartCastExpression) nullableReceiverExpression : null;
        if (firSmartCastExpression != null) {
            if (!firSmartCastExpression.isStable()) {
                firSmartCastExpression = null;
            }
            if (firSmartCastExpression == null || (typeRef = firSmartCastExpression.getSmartcastTypeWithoutNullableNothing()) == null) {
                typeRef = nullableReceiverExpression.getTypeRef();
            }
        } else {
            typeRef = nullableReceiverExpression.getTypeRef();
        }
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        if (type == null) {
            return;
        }
        FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(firCheckedSafeCallSubject.getTypeRef(), TypeUtilsKt.independentInstance(TypeUtilsKt.makeConeTypeDefinitelyNotNullOrNotNull$default(TypeExpansionUtilsKt.fullyExpandedType(type, session), TypeComponentsKt.getTypeContext(session), false, 2, null)), null, 2, null);
        firCheckedSafeCallSubject.replaceTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(session);
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefResolvedTypeFromPrototype$default, firCheckedSafeCallSubject.getSource(), file.getSource());
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0051  */
    public static final void propagateTypeFromQualifiedAccessAfterNullCheck(FirSafeCallExpression firSafeCallExpression, FirSession session, FirFile file) {
        ConeClassLikeType coneClassLikeTypeConstructClassLikeType$default;
        Intrinsics.checkNotNullParameter(firSafeCallExpression, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(file, "file");
        FirStatement selector = firSafeCallExpression.getSelector();
        if (selector instanceof FirExpression) {
            FirExpression firExpression = (FirExpression) selector;
            if (!isCallToStatementLikeFunction(firExpression)) {
                FirTypeRef typeRef = firExpression.getTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                ConeKotlinType coneKotlinType = !(type instanceof ConeKotlinType) ? null : type;
                if (coneKotlinType == null) {
                    return;
                } else {
                    coneClassLikeTypeConstructClassLikeType$default = TypeUtilsKt.withNullability$default(coneKotlinType, ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(session), null, false, 12, null);
                }
            } else {
                coneClassLikeTypeConstructClassLikeType$default = TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getUnit(), new ConeTypeProjection[0], false, null, 4, null);
            }
        } else {
            coneClassLikeTypeConstructClassLikeType$default = TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getUnit(), new ConeTypeProjection[0], false, null, 4, null);
        }
        FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(firSafeCallExpression.getTypeRef(), TypeUtilsKt.independentInstance(coneClassLikeTypeConstructClassLikeType$default), null, 2, null);
        firSafeCallExpression.replaceTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(session);
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefResolvedTypeFromPrototype$default, firSafeCallExpression.getSource(), file.getSource());
        }
    }

    private static final boolean isCallToStatementLikeFunction(FirExpression firExpression) {
        FirNamedReference calleeReference;
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default;
        FirFunctionCall firFunctionCall = firExpression instanceof FirFunctionCall ? (FirFunctionCall) firExpression : null;
        return (firFunctionCall == null || (calleeReference = firFunctionCall.getCalleeReference()) == null || (resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(calleeReference, false, 1, null)) == null || ((FirFunctionCall) firExpression).getOrigin() != FirFunctionCallOrigin.Operator || !OperatorNameConventions.STATEMENT_LIKE_OPERATORS.contains(resolvedNamedFunctionSymbol$default.getName())) ? false : true;
    }

    public static final FirRegularClassSymbol getCorrespondingClassSymbolOrNull(FirAnnotation firAnnotation, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return TypeUtilsKt.toRegularClassSymbol(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef()), session), session);
    }

    public static final ConeKotlinType initialTypeOfCandidate(BodyResolveComponents bodyResolveComponents, Candidate candidate) {
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return initialTypeOfCandidate(typeFromSymbol(bodyResolveComponents, candidate.getSymbol()), candidate);
    }

    public static final ConeKotlinType initialTypeOfCandidate(FirResolvedTypeRef firResolvedTypeRef, Candidate candidate) {
        Intrinsics.checkNotNullParameter(firResolvedTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        NewConstraintSystemImpl system = candidate.getSystem();
        KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(system.buildCurrentSubstitutor(), system, candidate.getSubstitutor().substituteOrSelf(firResolvedTypeRef.getType()));
        Intrinsics.checkNotNull(kotlinTypeMarkerSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        return (ConeKotlinType) kotlinTypeMarkerSafeSubstitute;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirRegularClass getContainingClass(FirCallableDeclaration firCallableDeclaration, FirSession session) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firCallableDeclaration);
        if (coneClassLikeLookupTagContainingClassLookupTag == null) {
            return null;
        }
        FirClassLikeSymbol<?> symbolByLookupTag = LookupTagUtilsKt.getSymbolByLookupTag(FirSymbolProviderKt.getSymbolProvider(session), coneClassLikeLookupTagContainingClassLookupTag);
        FirClassLikeDeclaration firClassLikeDeclaration = symbolByLookupTag != null ? (FirClassLikeDeclaration) symbolByLookupTag.getFir() : null;
        if (firClassLikeDeclaration instanceof FirRegularClass) {
            return (FirRegularClass) firClassLikeDeclaration;
        }
        return null;
    }

    public static /* synthetic */ ForbiddenNamedArgumentsTarget getAsForbiddenNamedArgumentsTarget$default(FirFunction firFunction, FirSession firSession, FirTypeScope firTypeScope, int i, Object obj) {
        if ((i & 2) != 0) {
            firTypeScope = null;
        }
        return getAsForbiddenNamedArgumentsTarget(firFunction, firSession, firTypeScope);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11, types: [T, org.jetbrains.kotlin.resolve.ForbiddenNamedArgumentsTarget] */
    public static final ForbiddenNamedArgumentsTarget getAsForbiddenNamedArgumentsTarget(FirFunction firFunction, final FirSession session, FirTypeScope firTypeScope) {
        FirFunction firFunction2;
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firFunction.getStatus().getHasStableParameterNames()) {
            return null;
        }
        FirDeclarationOrigin origin = firFunction.getOrigin();
        if (Intrinsics.areEqual(origin, FirDeclarationOrigin.ImportedFromObjectOrStatic.INSTANCE)) {
            ImportedFromObjectOrStaticData importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(firFunction);
            if (importedFromObjectOrStaticData == null || (firFunction2 = (FirFunction) importedFromObjectOrStaticData.getOriginal()) == null) {
                return null;
            }
            return getAsForbiddenNamedArgumentsTarget$default(firFunction2, session, null, 2, null);
        }
        if (Intrinsics.areEqual(origin, FirDeclarationOrigin.IntersectionOverride.INSTANCE) ? true : origin instanceof FirDeclarationOrigin.SubstitutionOverride ? true : Intrinsics.areEqual(origin, FirDeclarationOrigin.Delegated.INSTANCE)) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            FirFunction firFunction3 = firFunction;
            while (true) {
                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firFunction3) || (firFunction3.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firFunction3) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firFunction3) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firFunction3) : null;
                    if (originalForSubstitutionOverrideAttr == null) {
                        DelegatedWrapperData delegatedWrapperData = ClassMembersKt.getDelegatedWrapperData(firFunction3);
                        originalForSubstitutionOverrideAttr = delegatedWrapperData != null ? delegatedWrapperData.getWrapped() : null;
                    }
                }
                if (originalForSubstitutionOverrideAttr == null) {
                    break;
                }
                firFunction3 = originalForSubstitutionOverrideAttr;
            }
            ?? asForbiddenNamedArgumentsTarget$default = getAsForbiddenNamedArgumentsTarget$default((FirFunction) firFunction3, session, null, 2, null);
            if (asForbiddenNamedArgumentsTarget$default == 0) {
                return null;
            }
            objectRef.element = asForbiddenNamedArgumentsTarget$default;
            if (firTypeScope != null) {
                FirFunctionSymbol<? extends FirFunction> symbol = firFunction.getSymbol();
                Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
                FirTypeScopeKt.processOverriddenFunctions(firTypeScope, (FirNamedFunctionSymbol) symbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt.getAsForbiddenNamedArgumentsTarget.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        if (ResolveUtilsKt.getAsForbiddenNamedArgumentsTarget$default((FirFunction) it.getFir(), session, null, 2, null) == null) {
                            objectRef.element = null;
                            return ProcessorAction.STOP;
                        }
                        return ProcessorAction.NEXT;
                    }
                });
            }
            return (ForbiddenNamedArgumentsTarget) objectRef.element;
        }
        if (Intrinsics.areEqual(origin, FirDeclarationOrigin.BuiltIns.INSTANCE)) {
            return ForbiddenNamedArgumentsTarget.INVOKE_ON_FUNCTION_TYPE;
        }
        if (origin instanceof FirDeclarationOrigin.Plugin) {
            return null;
        }
        return ForbiddenNamedArgumentsTarget.NON_KOTLIN_FUNCTION;
    }

    public static final boolean isIntegerLiteralOrOperatorCall(FirExpression firExpression) {
        if (firExpression instanceof FirConstExpression) {
            FirConstExpression firConstExpression = (FirConstExpression) firExpression;
            if (!Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.Int.INSTANCE) && !Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.IntegerLiteral.INSTANCE) && !Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.UnsignedInt.INSTANCE) && !Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.UnsignedIntegerLiteral.INSTANCE)) {
                return false;
            }
        } else if (!(firExpression instanceof FirIntegerLiteralOperatorCall)) {
            if (firExpression instanceof FirNamedArgumentExpression) {
                return isIntegerLiteralOrOperatorCall(((FirNamedArgumentExpression) firExpression).getExpression());
            }
            return false;
        }
        return true;
    }

    public static final ConeDiagnostic createConeDiagnosticForCandidateWithError(CandidateApplicability applicability, Candidate candidate) {
        Sequence sequenceParentDeclarationSequence$default;
        Object next;
        Intrinsics.checkNotNullParameter(applicability, "applicability");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        int i = WhenMappings.$EnumSwitchMapping$0[applicability.ordinal()];
        if (i == 1) {
            return new ConeHiddenCandidateError(candidate);
        }
        if (i != 2) {
            if (i == 3) {
                return new ConeInapplicableWrongReceiver(CollectionsKt.listOf(candidate));
            }
            if (i == 4) {
                return new ConeNoCompanionObject(candidate);
            }
            return new ConeInapplicableCandidateError(applicability, candidate);
        }
        FirSession session = candidate.getCallInfo().getSession();
        FirDeclaration fir = candidate.getSymbol().getFir();
        if (fir instanceof FirMemberDeclaration) {
            FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) fir;
            if (VisibilityUtilsKt.isVisible(FirVisibilityCheckerKt.getVisibilityChecker(session), firMemberDeclaration, candidate, true) && (sequenceParentDeclarationSequence$default = FirVisibilityCheckerKt.parentDeclarationSequence$default(firMemberDeclaration, session, candidate.getDispatchReceiver(), candidate.getCallInfo().getContainingDeclarations(), null, 8, null)) != null) {
                Iterator it = sequenceParentDeclarationSequence$default.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (FirVisibilityChecker.isVisible$default(FirVisibilityCheckerKt.getVisibilityChecker(session), (FirClassLikeDeclaration) next, session, candidate.getCallInfo().getContainingFile(), candidate.getCallInfo().getContainingDeclarations(), null, false, null, true, null, 352, null));
                FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) next;
                if (firClassLikeDeclaration != null) {
                    return new ConeVisibilityError(firClassLikeDeclaration.getSymbol());
                }
            }
        }
        return new ConeVisibilityError(candidate.getSymbol());
    }

    public static final FirNamedReference toErrorReference(FirNamedReferenceWithCandidate firNamedReferenceWithCandidate, ConeDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(firNamedReferenceWithCandidate, "<this>");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        FirBasedSymbol<?> candidateSymbol = firNamedReferenceWithCandidate.getCandidateSymbol();
        if (candidateSymbol instanceof FirErrorPropertySymbol ? true : candidateSymbol instanceof FirErrorFunctionSymbol) {
            FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
            firErrorNamedReferenceBuilder.setSource(firNamedReferenceWithCandidate.getSource());
            firErrorNamedReferenceBuilder.setDiagnostic(diagnostic);
            return firErrorNamedReferenceBuilder.build();
        }
        FirResolvedErrorReferenceBuilder firResolvedErrorReferenceBuilder = new FirResolvedErrorReferenceBuilder();
        firResolvedErrorReferenceBuilder.setSource(firNamedReferenceWithCandidate.getSource());
        firResolvedErrorReferenceBuilder.setName(firNamedReferenceWithCandidate.getName());
        firResolvedErrorReferenceBuilder.setResolvedSymbol(firNamedReferenceWithCandidate.getCandidateSymbol());
        firResolvedErrorReferenceBuilder.setDiagnostic(diagnostic);
        return firResolvedErrorReferenceBuilder.build();
    }
}
