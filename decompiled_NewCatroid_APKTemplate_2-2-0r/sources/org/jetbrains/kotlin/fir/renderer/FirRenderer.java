package org.jetbrains.kotlin.fir.renderer;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.archive.PathTree;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindExtractor;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirPackageDirective;
import org.jetbrains.kotlin.fir.FirTarget;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.description.ConeContractRenderer;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructedClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.fir.declarations.FirErrorProperty;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirOuterClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement;
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEnumEntryDeserializedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirIncrementDecrementExpression;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirLazyExpression;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirLoopJump;
import org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirWrappedDelegateExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirElseIfTrueCondition;
import org.jetbrains.kotlin.fir.expressions.impl.FirExpressionStub;
import org.jetbrains.kotlin.fir.expressions.impl.FirLazyDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirUnitExpression;
import org.jetbrains.kotlin.fir.references.FirBackingFieldReference;
import org.jetbrains.kotlin.fir.references.FirDelegateFieldReference;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReferenceWithCandidateBase;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirDynamicTypeRef;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.luaj.vm2.Lua;

/* JADX INFO: compiled from: FirRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ä\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 q2\u00020\u0001:\u0002qrBÍ\u0001\u0012\f\b\u0002\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u001c\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u001e\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 \u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$¢\u0006\u0002\u0010%J\u0010\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020QH\u0002J\u000e\u0010R\u001a\u00020O2\u0006\u0010S\u001a\u00020TJ\u000e\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020XJ\u0016\u0010Y\u001a\u00020O2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\\0[H\u0002J\u0018\u0010]\u001a\u00020V2\u0006\u0010^\u001a\u00020_2\b\b\u0002\u0010`\u001a\u00020aJ\u000e\u0010b\u001a\u00020V2\u0006\u0010^\u001a\u00020_J\u000e\u0010c\u001a\u00020O2\u0006\u0010d\u001a\u00020eJ\"\u0010f\u001a\u00020O2\f\u0010g\u001a\b\u0012\u0004\u0012\u00020_0[2\n\u0010J\u001a\u00060KR\u00020\u0000H\u0002J\u000e\u0010h\u001a\u00020O2\u0006\u0010i\u001a\u00020jJ\u0012\u0010k\u001a\u00020O*\b\u0012\u0004\u0012\u00020l0[H\u0002J\u0012\u0010m\u001a\u00020O*\b\u0012\u0004\u0012\u00020n0[H\u0002J\f\u0010o\u001a\u00020O*\u00020pH\u0002R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0016\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0016\u0010!\u001a\u0004\u0018\u00010\"X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0016\u0010#\u001a\u0004\u0018\u00010$X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0014\u0010<\u001a\u00020=X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u0014\u0010\u001d\u001a\u00020\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bB\u0010CR\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0016\u0010\u001f\u001a\u0004\u0018\u00010 X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bH\u0010IR\u0018\u0010J\u001a\u00060KR\u00020\u0000X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010M¨\u0006s"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "annotationRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "bodyRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirBodyRenderer;", "callArgumentsRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirCallArgumentsRenderer;", "classMemberRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirClassMemberRenderer;", "contractRenderer", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractRenderer;", "declarationRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;", "idRenderer", "Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "modifierRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;", "packageDirectiveRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirPackageDirectiveRenderer;", "propertyAccessorRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirPropertyAccessorRenderer;", "resolvePhaseRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirResolvePhaseRenderer;", "typeRenderer", "Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "referencedSymbolRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirSymbolRenderer;", "valueParameterRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirValueParameterRenderer;", "errorExpressionRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirErrorExpressionRenderer;", "fileAnnotationsContainerRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirFileAnnotationsContainerRenderer;", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirBodyRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirCallArgumentsRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirClassMemberRenderer;Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirPackageDirectiveRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirPropertyAccessorRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirResolvePhaseRenderer;Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirSymbolRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirValueParameterRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirErrorExpressionRenderer;Lorg/jetbrains/kotlin/fir/renderer/FirFileAnnotationsContainerRenderer;)V", "getAnnotationRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "getBodyRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirBodyRenderer;", "getCallArgumentsRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirCallArgumentsRenderer;", "getClassMemberRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirClassMemberRenderer;", "getContractRenderer", "()Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractRenderer;", "getDeclarationRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;", "getErrorExpressionRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirErrorExpressionRenderer;", "getFileAnnotationsContainerRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirFileAnnotationsContainerRenderer;", "getIdRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "getModifierRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;", "getPackageDirectiveRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirPackageDirectiveRenderer;", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPropertyAccessorRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirPropertyAccessorRenderer;", "getReferencedSymbolRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirSymbolRenderer;", "getResolvePhaseRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirResolvePhaseRenderer;", "getTypeRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "getValueParameterRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirValueParameterRenderer;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "getVisitor", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "print", "", "s", "", "renderAnnotations", "annotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "renderAsCallableDeclarationString", "", "callableDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "renderContexts", "contextReceivers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "renderElementAsString", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "trim", "", "renderElementWithTypeAsString", "renderMemberDeclarationClass", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "renderSeparated", PathTree.NAME_elements, "renderSupertypes", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "renderTypeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "renderTypeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "renderVariance", "Lorg/jetbrains/kotlin/types/Variance;", "Companion", "Visitor", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirRenderer implements FirRendererComponents {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirAnnotationRenderer annotationRenderer;
    private final FirBodyRenderer bodyRenderer;
    private final FirCallArgumentsRenderer callArgumentsRenderer;
    private final FirClassMemberRenderer classMemberRenderer;
    private final ConeContractRenderer contractRenderer;
    private final FirDeclarationRenderer declarationRenderer;
    private final FirErrorExpressionRenderer errorExpressionRenderer;
    private final FirFileAnnotationsContainerRenderer fileAnnotationsContainerRenderer;
    private final ConeIdRenderer idRenderer;
    private final FirModifierRenderer modifierRenderer;
    private final FirPackageDirectiveRenderer packageDirectiveRenderer;
    private final FirPrinter printer;
    private final FirPropertyAccessorRenderer propertyAccessorRenderer;
    private final FirSymbolRenderer referencedSymbolRenderer;
    private final FirResolvePhaseRenderer resolvePhaseRenderer;
    private final ConeTypeRenderer typeRenderer;
    private final FirValueParameterRenderer valueParameterRenderer;
    private final Visitor visitor;

    public FirRenderer() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Lua.MAXARG_sBx, null);
    }

    public FirRenderer(StringBuilder builder, FirAnnotationRenderer firAnnotationRenderer, FirBodyRenderer firBodyRenderer, FirCallArgumentsRenderer firCallArgumentsRenderer, FirClassMemberRenderer firClassMemberRenderer, ConeContractRenderer coneContractRenderer, FirDeclarationRenderer firDeclarationRenderer, ConeIdRenderer idRenderer, FirModifierRenderer firModifierRenderer, FirPackageDirectiveRenderer firPackageDirectiveRenderer, FirPropertyAccessorRenderer firPropertyAccessorRenderer, FirResolvePhaseRenderer firResolvePhaseRenderer, ConeTypeRenderer typeRenderer, FirSymbolRenderer referencedSymbolRenderer, FirValueParameterRenderer firValueParameterRenderer, FirErrorExpressionRenderer firErrorExpressionRenderer, FirFileAnnotationsContainerRenderer firFileAnnotationsContainerRenderer) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(idRenderer, "idRenderer");
        Intrinsics.checkNotNullParameter(typeRenderer, "typeRenderer");
        Intrinsics.checkNotNullParameter(referencedSymbolRenderer, "referencedSymbolRenderer");
        this.annotationRenderer = firAnnotationRenderer;
        this.bodyRenderer = firBodyRenderer;
        this.callArgumentsRenderer = firCallArgumentsRenderer;
        this.classMemberRenderer = firClassMemberRenderer;
        this.contractRenderer = coneContractRenderer;
        this.declarationRenderer = firDeclarationRenderer;
        this.idRenderer = idRenderer;
        this.modifierRenderer = firModifierRenderer;
        this.packageDirectiveRenderer = firPackageDirectiveRenderer;
        this.propertyAccessorRenderer = firPropertyAccessorRenderer;
        this.resolvePhaseRenderer = firResolvePhaseRenderer;
        this.typeRenderer = typeRenderer;
        this.referencedSymbolRenderer = referencedSymbolRenderer;
        this.valueParameterRenderer = firValueParameterRenderer;
        this.errorExpressionRenderer = firErrorExpressionRenderer;
        this.fileAnnotationsContainerRenderer = firFileAnnotationsContainerRenderer;
        this.visitor = new Visitor();
        this.printer = new FirPrinter(builder);
        FirAnnotationRenderer annotationRenderer = getAnnotationRenderer();
        if (annotationRenderer != null) {
            annotationRenderer.setComponents$tree(this);
        }
        FirBodyRenderer bodyRenderer = getBodyRenderer();
        if (bodyRenderer != null) {
            bodyRenderer.setComponents$tree(this);
        }
        FirCallArgumentsRenderer callArgumentsRenderer = getCallArgumentsRenderer();
        if (callArgumentsRenderer != null) {
            callArgumentsRenderer.setComponents$tree(this);
        }
        FirClassMemberRenderer classMemberRenderer = getClassMemberRenderer();
        if (classMemberRenderer != null) {
            classMemberRenderer.setComponents$tree(this);
        }
        ConeContractRenderer contractRenderer = getContractRenderer();
        if (contractRenderer != null) {
            contractRenderer.setComponents$tree(this);
        }
        FirDeclarationRenderer declarationRenderer = getDeclarationRenderer();
        if (declarationRenderer != null) {
            declarationRenderer.setComponents$tree(this);
        }
        getIdRenderer().setBuilder(builder);
        FirModifierRenderer modifierRenderer = getModifierRenderer();
        if (modifierRenderer != null) {
            modifierRenderer.setComponents$tree(this);
        }
        FirPackageDirectiveRenderer packageDirectiveRenderer = getPackageDirectiveRenderer();
        if (packageDirectiveRenderer != null) {
            packageDirectiveRenderer.setComponents$tree(this);
        }
        FirPropertyAccessorRenderer propertyAccessorRenderer = getPropertyAccessorRenderer();
        if (propertyAccessorRenderer != null) {
            propertyAccessorRenderer.setComponents$tree(this);
        }
        FirResolvePhaseRenderer resolvePhaseRenderer = getResolvePhaseRenderer();
        if (resolvePhaseRenderer != null) {
            resolvePhaseRenderer.setComponents$tree(this);
        }
        getTypeRenderer().setBuilder(builder);
        getTypeRenderer().setIdRenderer(getIdRenderer());
        FirRenderer firRenderer = this;
        getReferencedSymbolRenderer().setComponents$tree(firRenderer);
        FirValueParameterRenderer valueParameterRenderer = getValueParameterRenderer();
        if (valueParameterRenderer != null) {
            valueParameterRenderer.setComponents$tree(firRenderer);
        }
        FirErrorExpressionRenderer errorExpressionRenderer = getErrorExpressionRenderer();
        if (errorExpressionRenderer != null) {
            errorExpressionRenderer.setComponents$tree(firRenderer);
        }
        FirFileAnnotationsContainerRenderer fileAnnotationsContainerRenderer = getFileAnnotationsContainerRenderer();
        if (fileAnnotationsContainerRenderer == null) {
            return;
        }
        fileAnnotationsContainerRenderer.setComponents$tree(firRenderer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ FirRenderer(StringBuilder sb, FirAnnotationRenderer firAnnotationRenderer, FirBodyRenderer firBodyRenderer, FirCallArgumentsRenderer firCallArgumentsRenderer, FirClassMemberRenderer firClassMemberRenderer, ConeContractRenderer coneContractRenderer, FirDeclarationRenderer firDeclarationRenderer, ConeIdRenderer coneIdRenderer, FirModifierRenderer firModifierRenderer, FirPackageDirectiveRenderer firPackageDirectiveRenderer, FirPropertyAccessorRenderer firPropertyAccessorRenderer, FirResolvePhaseRenderer firResolvePhaseRenderer, ConeTypeRenderer coneTypeRenderer, FirSymbolRenderer firSymbolRenderer, FirValueParameterRenderer firValueParameterRenderer, FirErrorExpressionRenderer firErrorExpressionRenderer, FirFileAnnotationsContainerRenderer firFileAnnotationsContainerRenderer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        FirDeclarationRenderer firDeclarationRenderer2;
        StringBuilder sb2 = (i & 1) != 0 ? new StringBuilder() : sb;
        FirAnnotationRenderer firAnnotationRenderer2 = (i & 2) != 0 ? new FirAnnotationRenderer() : firAnnotationRenderer;
        FirBodyRenderer firBodyRenderer2 = (i & 4) != 0 ? new FirBodyRenderer() : firBodyRenderer;
        FirCallArgumentsRenderer firCallArgumentsRenderer2 = (i & 8) != 0 ? new FirCallArgumentsRenderer() : firCallArgumentsRenderer;
        FirClassMemberRenderer firClassMemberRenderer2 = (i & 16) != 0 ? new FirClassMemberRenderer() : firClassMemberRenderer;
        ConeContractRenderer coneContractRenderer2 = (i & 32) != 0 ? new ConeContractRenderer() : coneContractRenderer;
        String str = null;
        Object[] objArr = 0;
        if ((i & 64) != 0) {
            firDeclarationRenderer2 = new FirDeclarationRenderer(str, 1, objArr == true ? 1 : 0);
        } else {
            firDeclarationRenderer2 = firDeclarationRenderer;
        }
        this(sb2, firAnnotationRenderer2, firBodyRenderer2, firCallArgumentsRenderer2, firClassMemberRenderer2, coneContractRenderer2, firDeclarationRenderer2, (i & 128) != 0 ? new ConeIdRendererForDebugging() : coneIdRenderer, (i & 256) != 0 ? new FirAllModifierRenderer() : firModifierRenderer, (i & 512) != 0 ? null : firPackageDirectiveRenderer, (i & 1024) != 0 ? new FirPropertyAccessorRenderer() : firPropertyAccessorRenderer, (i & 2048) != 0 ? null : firResolvePhaseRenderer, (i & 4096) != 0 ? new ConeTypeRendererForDebugging() : coneTypeRenderer, (i & 8192) != 0 ? new FirSymbolRenderer() : firSymbolRenderer, (i & 16384) != 0 ? new FirValueParameterRenderer() : firValueParameterRenderer, (i & 32768) != 0 ? new FirErrorExpressionOnlyErrorRenderer() : firErrorExpressionRenderer, (i & 65536) != 0 ? null : firFileAnnotationsContainerRenderer);
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirAnnotationRenderer getAnnotationRenderer() {
        return this.annotationRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirBodyRenderer getBodyRenderer() {
        return this.bodyRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirCallArgumentsRenderer getCallArgumentsRenderer() {
        return this.callArgumentsRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirClassMemberRenderer getClassMemberRenderer() {
        return this.classMemberRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public ConeContractRenderer getContractRenderer() {
        return this.contractRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirDeclarationRenderer getDeclarationRenderer() {
        return this.declarationRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public ConeIdRenderer getIdRenderer() {
        return this.idRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirModifierRenderer getModifierRenderer() {
        return this.modifierRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirPackageDirectiveRenderer getPackageDirectiveRenderer() {
        return this.packageDirectiveRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirPropertyAccessorRenderer getPropertyAccessorRenderer() {
        return this.propertyAccessorRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirResolvePhaseRenderer getResolvePhaseRenderer() {
        return this.resolvePhaseRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public ConeTypeRenderer getTypeRenderer() {
        return this.typeRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirSymbolRenderer getReferencedSymbolRenderer() {
        return this.referencedSymbolRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirValueParameterRenderer getValueParameterRenderer() {
        return this.valueParameterRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirErrorExpressionRenderer getErrorExpressionRenderer() {
        return this.errorExpressionRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirFileAnnotationsContainerRenderer getFileAnnotationsContainerRenderer() {
        return this.fileAnnotationsContainerRenderer;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public Visitor getVisitor() {
        return this.visitor;
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirRendererComponents
    public FirPrinter getPrinter() {
        return this.printer;
    }

    /* JADX INFO: compiled from: FirRenderer.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0004J\u0006\u0010\u0006\u001a\u00020\u0004¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Companion;", "", "()V", "noAnnotationBodiesAccessorAndArguments", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "withDeclarationAttributes", "withResolvePhase", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirRenderer noAnnotationBodiesAccessorAndArguments() {
            return new FirRenderer(null, null, null, new FirCallNoArgumentsRenderer(), null, null, null, null, null, null, null, null, null, null, null, null, null, 130033, null);
        }

        public final FirRenderer withResolvePhase() {
            return new FirRenderer(null, null, null, null, null, null, null, null, null, null, null, new FirResolvePhaseRenderer(), null, null, null, null, new FirFileAnnotationsContainerRenderer(), 63487, null);
        }

        public final FirRenderer withDeclarationAttributes() {
            return new FirRenderer(null, null, null, null, null, null, new FirDeclarationRendererWithAttributes(), null, null, null, null, null, null, null, null, null, null, 131007, null);
        }
    }

    public static /* synthetic */ String renderElementAsString$default(FirRenderer firRenderer, FirElement firElement, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return firRenderer.renderElementAsString(firElement, z);
    }

    public final String renderElementAsString(FirElement element, boolean trim) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.accept(getVisitor());
        String string = getPrinter().toString();
        return trim ? StringsKt.trim((CharSequence) string).toString() : string;
    }

    public final String renderElementWithTypeAsString(FirElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        print(element);
        print(": ");
        return renderElementAsString$default(this, element, false, 2, null);
    }

    public final String renderAsCallableDeclarationString(FirCallableDeclaration callableDeclaration) {
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        getVisitor().visitCallableDeclaration(callableDeclaration);
        return getPrinter().toString();
    }

    public final void renderMemberDeclarationClass(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        getVisitor().visitMemberDeclaration(firClass);
    }

    public final void renderAnnotations(FirAnnotationContainer annotationContainer) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        FirAnnotationRenderer annotationRenderer = getAnnotationRenderer();
        if (annotationRenderer != null) {
            FirAnnotationRenderer.render$default(annotationRenderer, annotationContainer, null, 2, null);
        }
    }

    public final void renderSupertypes(FirRegularClass regularClass) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        if (regularClass.getSuperTypeRefs().isEmpty()) {
            return;
        }
        print(" : ");
        renderSeparated(regularClass.getSuperTypeRefs(), getVisitor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderVariance(Variance variance) {
        String label = variance.getLabel();
        print(label);
        if (label.length() > 0) {
            print(" ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderContexts(List<? extends FirContextReceiver> contextReceivers) {
        if (contextReceivers.isEmpty()) {
            return;
        }
        print("context(");
        renderSeparated(contextReceivers, getVisitor());
        print(Constant.AFTER_QUTO);
        getPrinter().newLine();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeParameters(List<? extends FirTypeParameterRef> list) {
        if (list.isEmpty()) {
            return;
        }
        print("<");
        renderSeparated(list, getVisitor());
        print(">");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeArguments(List<? extends FirTypeProjection> list) {
        if (list.isEmpty()) {
            return;
        }
        print("<");
        renderSeparated(list, getVisitor());
        print(">");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void print(Object s) {
        getPrinter().print(s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderSeparated(List<? extends FirElement> elements, Visitor visitor) {
        getPrinter().renderSeparated$tree(elements, visitor);
    }

    /* JADX INFO: compiled from: FirRenderer.kt */
    @Metadata(d1 = {"\u0000¦\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007\b\u0000¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020\u00072\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u00072\u0006\u00104\u001a\u000205H\u0016J\u0010\u00106\u001a\u00020\u00072\u0006\u00107\u001a\u000208H\u0016J\u0010\u00109\u001a\u00020\u00072\u0006\u0010:\u001a\u00020;H\u0016J\u0010\u0010<\u001a\u00020\u00072\u0006\u0010=\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020\u00072\u0006\u0010@\u001a\u00020AH\u0016J\u0010\u0010B\u001a\u00020\u00072\u0006\u0010C\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020\u00072\u0006\u0010F\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020\u00072\u0006\u0010I\u001a\u00020JH\u0016J\u0010\u0010K\u001a\u00020\u00072\u0006\u0010L\u001a\u00020MH\u0016J\u0010\u0010N\u001a\u00020\u00072\u0006\u0010O\u001a\u00020PH\u0016J\u0010\u0010Q\u001a\u00020\u00072\u0006\u0010R\u001a\u00020SH\u0016J\u001c\u0010T\u001a\u00020\u0007\"\u0004\b\u0000\u0010U2\f\u0010V\u001a\b\u0012\u0004\u0012\u0002HU0WH\u0016J\u0010\u0010X\u001a\u00020\u00072\u0006\u0010Y\u001a\u00020ZH\u0016J\u0010\u0010[\u001a\u00020\u00072\u0006\u0010\\\u001a\u00020]H\u0016J\u0010\u0010^\u001a\u00020\u00072\u0006\u0010_\u001a\u00020`H\u0016J\u0010\u0010a\u001a\u00020\u00072\u0006\u0010b\u001a\u00020cH\u0016J\u0010\u0010d\u001a\u00020\u00072\u0006\u0010e\u001a\u00020fH\u0016J\u0010\u0010g\u001a\u00020\u00072\u0006\u0010h\u001a\u00020iH\u0016J\u0010\u0010j\u001a\u00020\u00072\u0006\u0010k\u001a\u00020lH\u0016J\u0010\u0010m\u001a\u00020\u00072\u0006\u0010n\u001a\u00020oH\u0016J\u0010\u0010p\u001a\u00020\u00072\u0006\u0010q\u001a\u00020rH\u0016J\u0010\u0010s\u001a\u00020\u00072\u0006\u0010t\u001a\u00020uH\u0016J\u0010\u0010v\u001a\u00020\u00072\u0006\u0010w\u001a\u00020xH\u0016J\u0010\u0010y\u001a\u00020\u00072\u0006\u0010z\u001a\u00020{H\u0016J\u0010\u0010|\u001a\u00020\u00072\u0006\u0010}\u001a\u00020~H\u0016J\u0012\u0010\u007f\u001a\u00020\u00072\b\u0010\u0080\u0001\u001a\u00030\u0081\u0001H\u0016J\u0013\u0010\u0082\u0001\u001a\u00020\u00072\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0016J\u0013\u0010\u0085\u0001\u001a\u00020\u00072\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001H\u0016J\u0013\u0010\u0088\u0001\u001a\u00020\u00072\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001H\u0016J\u0013\u0010\u008b\u0001\u001a\u00020\u00072\b\u0010\u008c\u0001\u001a\u00030\u008d\u0001H\u0016J\u0013\u0010\u008e\u0001\u001a\u00020\u00072\b\u0010\u008f\u0001\u001a\u00030\u0090\u0001H\u0016J\u0013\u0010\u0091\u0001\u001a\u00020\u00072\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001H\u0016J\u0013\u0010\u0094\u0001\u001a\u00020\u00072\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0016J\u0013\u0010\u0097\u0001\u001a\u00020\u00072\b\u0010\u0098\u0001\u001a\u00030\u0099\u0001H\u0016J\u0013\u0010\u009a\u0001\u001a\u00020\u00072\b\u0010\u009b\u0001\u001a\u00030\u009c\u0001H\u0016J\u0013\u0010\u009d\u0001\u001a\u00020\u00072\b\u0010\u009e\u0001\u001a\u00030\u009f\u0001H\u0016J\u0013\u0010 \u0001\u001a\u00020\u00072\b\u0010¡\u0001\u001a\u00030¢\u0001H\u0016J\u0013\u0010£\u0001\u001a\u00020\u00072\b\u0010¤\u0001\u001a\u00030¥\u0001H\u0016J\u0013\u0010¦\u0001\u001a\u00020\u00072\b\u0010§\u0001\u001a\u00030¨\u0001H\u0016J\u0013\u0010©\u0001\u001a\u00020\u00072\b\u0010ª\u0001\u001a\u00030«\u0001H\u0016J\u0013\u0010¬\u0001\u001a\u00020\u00072\b\u0010\u00ad\u0001\u001a\u00030®\u0001H\u0016J\u0013\u0010¯\u0001\u001a\u00020\u00072\b\u0010°\u0001\u001a\u00030±\u0001H\u0016J\u0013\u0010²\u0001\u001a\u00020\u00072\b\u0010³\u0001\u001a\u00030´\u0001H\u0016J\u0013\u0010µ\u0001\u001a\u00020\u00072\b\u0010¶\u0001\u001a\u00030·\u0001H\u0016J\u0013\u0010¸\u0001\u001a\u00020\u00072\b\u0010¹\u0001\u001a\u00030º\u0001H\u0016J\u0013\u0010»\u0001\u001a\u00020\u00072\b\u0010¼\u0001\u001a\u00030½\u0001H\u0016J\u0013\u0010¾\u0001\u001a\u00020\u00072\b\u0010¿\u0001\u001a\u00030À\u0001H\u0016J\u0013\u0010Á\u0001\u001a\u00020\u00072\b\u0010Â\u0001\u001a\u00030Ã\u0001H\u0016J\u0013\u0010Ä\u0001\u001a\u00020\u00072\b\u0010Å\u0001\u001a\u00030Æ\u0001H\u0016J\u0012\u0010Ç\u0001\u001a\u00020\u00072\u0007\u0010È\u0001\u001a\u00020\u0005H\u0016J\u0013\u0010É\u0001\u001a\u00020\u00072\b\u0010Ê\u0001\u001a\u00030Ë\u0001H\u0016J\u0013\u0010Ì\u0001\u001a\u00020\u00072\b\u0010Í\u0001\u001a\u00030Î\u0001H\u0016J\u0013\u0010Ï\u0001\u001a\u00020\u00072\b\u0010Ð\u0001\u001a\u00030Ñ\u0001H\u0016J\u0013\u0010Ò\u0001\u001a\u00020\u00072\b\u0010Ó\u0001\u001a\u00030Ô\u0001H\u0016J\u0013\u0010Õ\u0001\u001a\u00020\u00072\b\u0010Ö\u0001\u001a\u00030×\u0001H\u0016J\u0013\u0010Ø\u0001\u001a\u00020\u00072\b\u0010Ù\u0001\u001a\u00030Ú\u0001H\u0016J\u0013\u0010Û\u0001\u001a\u00020\u00072\b\u0010Ü\u0001\u001a\u00030Ý\u0001H\u0016J\u0013\u0010Þ\u0001\u001a\u00020\u00072\b\u0010ß\u0001\u001a\u00030à\u0001H\u0016J\u0013\u0010á\u0001\u001a\u00020\u00072\b\u0010â\u0001\u001a\u00030ã\u0001H\u0016J\u0013\u0010ä\u0001\u001a\u00020\u00072\b\u0010å\u0001\u001a\u00030æ\u0001H\u0016J\u0013\u0010ç\u0001\u001a\u00020\u00072\b\u0010è\u0001\u001a\u00030é\u0001H\u0016J\u0013\u0010ê\u0001\u001a\u00020\u00072\b\u0010ë\u0001\u001a\u00030é\u0001H\u0002J\u0013\u0010ì\u0001\u001a\u00020\u00072\b\u0010í\u0001\u001a\u00030î\u0001H\u0016J\u0013\u0010ï\u0001\u001a\u00020\u00072\b\u0010ð\u0001\u001a\u00030ñ\u0001H\u0016J\u0013\u0010ò\u0001\u001a\u00020\u00072\b\u0010ó\u0001\u001a\u00030ô\u0001H\u0016J\u0013\u0010õ\u0001\u001a\u00020\u00072\b\u0010ö\u0001\u001a\u00030÷\u0001H\u0016J\u0013\u0010ø\u0001\u001a\u00020\u00072\b\u0010ù\u0001\u001a\u00030ú\u0001H\u0016J\u0013\u0010û\u0001\u001a\u00020\u00072\b\u0010ü\u0001\u001a\u00030ý\u0001H\u0016J\u0013\u0010þ\u0001\u001a\u00020\u00072\b\u0010ÿ\u0001\u001a\u00030\u0080\u0002H\u0016J\u0013\u0010\u0081\u0002\u001a\u00020\u00072\b\u0010\u0082\u0002\u001a\u00030\u0083\u0002H\u0016J\u0013\u0010\u0084\u0002\u001a\u00020\u00072\b\u0010\u0085\u0002\u001a\u00030\u0086\u0002H\u0016J\u0013\u0010\u0087\u0002\u001a\u00020\u00072\b\u0010\u0088\u0002\u001a\u00030\u0089\u0002H\u0016J\u0013\u0010\u008a\u0002\u001a\u00020\u00072\b\u0010\u008b\u0002\u001a\u00030\u008c\u0002H\u0016J\u0013\u0010\u008d\u0002\u001a\u00020\u00072\b\u0010\u008e\u0002\u001a\u00030\u008f\u0002H\u0016J\u0013\u0010\u0090\u0002\u001a\u00020\u00072\b\u0010\u0091\u0002\u001a\u00030\u0092\u0002H\u0016J\u0013\u0010\u0093\u0002\u001a\u00020\u00072\b\u0010\u0094\u0002\u001a\u00030\u0095\u0002H\u0016J\u0013\u0010\u0096\u0002\u001a\u00020\u00072\b\u0010\u0097\u0002\u001a\u00030\u0098\u0002H\u0016J\u0013\u0010\u0099\u0002\u001a\u00020\u00072\b\u0010\u009a\u0002\u001a\u00030\u009b\u0002H\u0016J\u0013\u0010\u009c\u0002\u001a\u00020\u00072\b\u0010\u009d\u0002\u001a\u00030\u009e\u0002H\u0016J\u0013\u0010\u009f\u0002\u001a\u00020\u00072\b\u0010 \u0002\u001a\u00030¡\u0002H\u0016J\u0013\u0010¢\u0002\u001a\u00020\u00072\b\u0010£\u0002\u001a\u00030¤\u0002H\u0016J\u0013\u0010¥\u0002\u001a\u00020\u00072\b\u0010¦\u0002\u001a\u00030§\u0002H\u0016J\u0013\u0010¨\u0002\u001a\u00020\u00072\b\u0010©\u0002\u001a\u00030ª\u0002H\u0016J\u0013\u0010«\u0002\u001a\u00020\u00072\b\u0010¬\u0002\u001a\u00030\u00ad\u0002H\u0016J\u0013\u0010®\u0002\u001a\u00020\u00072\b\u0010¯\u0002\u001a\u00030°\u0002H\u0016J\u0011\u0010±\u0002\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0013\u0010²\u0002\u001a\u00020\u00072\b\u0010³\u0002\u001a\u00030´\u0002H\u0016J\u0013\u0010µ\u0002\u001a\u00020\u00072\b\u0010¶\u0002\u001a\u00030·\u0002H\u0016J\u0013\u0010¸\u0002\u001a\u00020\u00072\b\u0010¹\u0002\u001a\u00030º\u0002H\u0016J\u0013\u0010»\u0002\u001a\u00020\u00072\b\u0010¼\u0002\u001a\u00030½\u0002H\u0016J\u0013\u0010¾\u0002\u001a\u00020\u00072\b\u0010¿\u0002\u001a\u00030À\u0002H\u0016J\u0013\u0010Á\u0002\u001a\u00020\u00072\b\u0010Â\u0002\u001a\u00030Ã\u0002H\u0016J\u0013\u0010Ä\u0002\u001a\u00020\u00072\b\u0010Å\u0002\u001a\u00030Æ\u0002H\u0016J\u0013\u0010Ç\u0002\u001a\u00020\u00072\b\u0010È\u0002\u001a\u00030É\u0002H\u0016J\u0013\u0010Ê\u0002\u001a\u00020\u00072\b\u0010Ë\u0002\u001a\u00030Ì\u0002H\u0016J\u0013\u0010Í\u0002\u001a\u00020\u00072\b\u0010Î\u0002\u001a\u00030Ï\u0002H\u0016J\u0013\u0010Ð\u0002\u001a\u00020\u00072\b\u0010Ñ\u0002\u001a\u00030Ò\u0002H\u0016J\u0013\u0010Ó\u0002\u001a\u00020\u00072\b\u0010Ô\u0002\u001a\u00030Õ\u0002H\u0016J\u0013\u0010Ö\u0002\u001a\u00020\u00072\b\u0010×\u0002\u001a\u00030Ø\u0002H\u0016J\u0013\u0010Ù\u0002\u001a\u00020\u00072\b\u0010Ú\u0002\u001a\u00030Û\u0002H\u0016J\u0013\u0010Ü\u0002\u001a\u00020\u00072\b\u0010Ý\u0002\u001a\u00030Þ\u0002H\u0016J\u0017\u0010ß\u0002\u001a\u0007\u0012\u0002\b\u00030à\u0002*\u0007\u0012\u0002\b\u00030à\u0002H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006á\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitorVoid;", "(Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;)V", "loopJumpStack", "Ljava/util/Stack;", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "renderTypeParameter", "", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "forOuterTypeRef", "", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "visitAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "visitAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "visitAnonymousInitializer", "anonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "visitAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "visitArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "visitAssignmentOperatorStatement", "assignmentOperatorStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "visitAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "visitBackingField", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "visitBackingFieldReference", "backingFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirBackingFieldReference;", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "visitBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "visitBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "visitCall", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "visitCallableDeclaration", "callableDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "visitCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "visitCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "visitCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "visitClassReferenceExpression", "classReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", "visitCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "visitComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "visitComponentCall", "componentCall", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "visitConstructedClassTypeParameterRef", "constructedClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitContextReceiver", "contextReceiver", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "visitContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "visitContractDescription", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "visitDanglingModifierList", "danglingModifierList", "Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", "visitDelegateFieldReference", "delegateFieldReference", "Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;", "visitDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "visitDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "visitDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "visitDynamicTypeRef", "dynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;", "visitEffectDeclaration", "effectDeclaration", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "visitEnumEntryDeserializedAccessExpression", "enumEntryDeserializedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", "visitEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "visitErrorExpression", "errorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", "visitErrorFunction", "errorFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;", "visitErrorNamedReference", "errorNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirErrorNamedReference;", "visitErrorProperty", "errorProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", "visitErrorTypeRef", "errorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "visitExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "visitField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "visitFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "visitFileAnnotationsContainer", "fileAnnotationsContainer", "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "visitFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "visitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "visitFunctionTypeRef", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "visitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "visitImplicitInvokeCall", "implicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "visitImplicitTypeRef", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "visitImport", PsiKeyword.IMPORT, "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "visitIncrementDecrementExpression", "incrementDecrementExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;", "visitIntegerLiteralOperatorCall", "integerLiteralOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "visitLambdaArgumentExpression", "lambdaArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;", "visitLazyExpression", "lazyExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;", "visitLoopJump", "loopJump", "visitMemberDeclaration", "memberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "visitMultiDelegatedConstructorCall", "multiDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "visitNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "visitNamedReference", "namedReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "visitNamedReferenceWithCandidateBase", "namedReferenceWithCandidateBase", "Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;", "visitOuterClassTypeParameterRef", "outerClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;", "visitPackageDirective", "packageDirective", "Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "visitPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "visitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "visitQualifiedAccessExpressionReceivers", "qualifiedAccess", "visitReceiverParameter", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitResolvedCallableReference", "resolvedCallableReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;", "visitResolvedErrorReference", "resolvedErrorReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;", "visitResolvedNamedReference", "resolvedNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "visitResolvedQualifier", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "visitResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "visitReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "visitSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "visitSpreadArgumentExpression", "spreadArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;", "visitStarProjection", "starProjection", "Lorg/jetbrains/kotlin/fir/types/FirStarProjection;", "visitStatement", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "visitStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "visitSuperReference", "superReference", "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", "visitThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "visitThisReference", "thisReference", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "visitThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "visitTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "visitTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "visitTypeParameter", "visitTypeParameterRef", "typeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "visitTypeProjection", "typeProjection", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "visitTypeProjectionWithVariance", "typeProjectionWithVariance", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;", "visitTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visitTypeRefWithNullability", "typeRefWithNullability", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", "visitUserTypeRef", "userTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "visitValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "visitVariable", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "visitVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "visitWhenBranch", "whenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "visitWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "visitWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "visitWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "visitWrappedDelegateExpression", "wrappedDelegateExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;", "unwrapIntersectionOverrides", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class Visitor extends FirVisitorVoid {
        private final Stack<FirLoopJump> loopJumpStack = new Stack<>();

        public Visitor() {
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        /* JADX INFO: renamed from: visitElement */
        public void mo6787visitElement(FirElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.acceptChildren(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitFile(FirFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            FirRenderer.this.getPrinter().print("FILE: ");
            FirResolvePhaseRenderer resolvePhaseRenderer = FirRenderer.this.getResolvePhaseRenderer();
            if (resolvePhaseRenderer != null) {
                resolvePhaseRenderer.render(file);
            }
            FirRenderer.this.getPrinter().println(file.getName());
            FirRenderer.this.getPrinter().pushIndent$tree();
            FirFileAnnotationsContainer annotationsContainer = file.getAnnotationsContainer();
            if (annotationsContainer != null) {
                visitFileAnnotationsContainer(annotationsContainer);
            }
            visitPackageDirective(file.getPackageDirective());
            Iterator<T> it = file.getImports().iterator();
            while (it.hasNext()) {
                ((FirImport) it.next()).accept(this);
            }
            Iterator<T> it2 = file.getDeclarations().iterator();
            while (it2.hasNext()) {
                ((FirDeclaration) it2.next()).accept(this);
            }
            FirRenderer.this.getPrinter().popIndent$tree();
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitScript(FirScript script) {
            Intrinsics.checkNotNullParameter(script, "script");
            FirRenderer.this.renderContexts(script.getContextReceivers());
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, script, null, 2, null);
            }
            FirRenderer.this.getPrinter().print("SCRIPT: ");
            FirDeclarationRenderer declarationRenderer = FirRenderer.this.getDeclarationRenderer();
            if (declarationRenderer != null) {
                declarationRenderer.renderPhaseAndAttributes$tree(script);
            } else {
                FirResolvePhaseRenderer resolvePhaseRenderer = FirRenderer.this.getResolvePhaseRenderer();
                if (resolvePhaseRenderer != null) {
                    resolvePhaseRenderer.render(script);
                }
            }
            FirRenderer.this.getPrinter().newLine();
            FirRenderer.this.getPrinter().pushIndent$tree();
            List<FirVariable> parameters = script.getParameters();
            FirRenderer firRenderer = FirRenderer.this;
            Iterator<T> it = parameters.iterator();
            while (it.hasNext()) {
                ((FirVariable) it.next()).accept(this);
                firRenderer.getPrinter().newLine();
            }
            FirRenderer.this.getPrinter().newLine();
            List<FirStatement> statements = script.getStatements();
            FirRenderer firRenderer2 = FirRenderer.this;
            Iterator<T> it2 = statements.iterator();
            while (it2.hasNext()) {
                ((FirStatement) it2.next()).accept(this);
                firRenderer2.getPrinter().newLine();
            }
            FirRenderer.this.getPrinter().popIndent$tree();
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitCodeFragment(FirCodeFragment codeFragment) {
            Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
            FirRenderer.this.getPrinter().print("CODE FRAGMENT:");
            FirBodyRenderer bodyRenderer = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer != null) {
                FirBodyRenderer.renderBody$default(bodyRenderer, codeFragment.getBlock(), null, 2, null);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitFileAnnotationsContainer(FirFileAnnotationsContainer fileAnnotationsContainer) {
            Intrinsics.checkNotNullParameter(fileAnnotationsContainer, "fileAnnotationsContainer");
            if (FirRenderer.this.getFileAnnotationsContainerRenderer() != null) {
                FirRenderer.this.getFileAnnotationsContainerRenderer().render(fileAnnotationsContainer);
                return;
            }
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, fileAnnotationsContainer, null, 2, null);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAnnotation(FirAnnotation annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.renderAnnotation$tree$default(annotationRenderer, annotation, null, 2, null);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAnnotationCall(FirAnnotationCall annotationCall) {
            Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.renderAnnotation$tree$default(annotationRenderer, annotationCall, null, 2, null);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitCallableDeclaration(FirCallableDeclaration callableDeclaration) {
            FirValueParameterRenderer valueParameterRenderer;
            FirBackingField backingField;
            List<FirAnnotation> annotations;
            Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
            FirRenderer.this.renderContexts(callableDeclaration.getContextReceivers());
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, callableDeclaration, null, 2, null);
            }
            boolean z = callableDeclaration instanceof FirProperty;
            if (z && (backingField = ((FirProperty) callableDeclaration).getBackingField()) != null && (annotations = backingField.getAnnotations()) != null && (!annotations.isEmpty())) {
                FirRenderer.this.print("field:");
                FirAnnotationRenderer annotationRenderer2 = FirRenderer.this.getAnnotationRenderer();
                if (annotationRenderer2 != null) {
                    FirAnnotationRenderer.render$default(annotationRenderer2, backingField, null, 2, null);
                }
            }
            visitMemberDeclaration(callableDeclaration);
            FirReceiverParameter receiverParameter = callableDeclaration.getReceiverParameter();
            if (!z || !Intrinsics.areEqual((Object) ClassMembersKt.isCatchParameter((FirProperty) callableDeclaration), (Object) true)) {
                FirRenderer.this.print(" ");
            }
            if (receiverParameter != null) {
                FirAnnotationRenderer annotationRenderer3 = FirRenderer.this.getAnnotationRenderer();
                if (annotationRenderer3 != null) {
                    annotationRenderer3.render(receiverParameter, AnnotationUseSiteTarget.RECEIVER);
                }
                receiverParameter.getTypeRef().accept(this);
                FirRenderer.this.print(".");
            }
            if (callableDeclaration instanceof FirSimpleFunction) {
                FirRenderer.this.getIdRenderer().renderCallableId(((FirSimpleFunction) callableDeclaration).getSymbol().getCallableId());
            } else if (callableDeclaration instanceof FirVariable) {
                FirRenderer.this.getIdRenderer().renderCallableId(((FirVariable) callableDeclaration).getSymbol().getCallableId());
            }
            if ((callableDeclaration instanceof FirFunction) && (valueParameterRenderer = FirRenderer.this.getValueParameterRenderer()) != null) {
                valueParameterRenderer.renderParameters(((FirFunction) callableDeclaration).getValueParameters());
            }
            FirRenderer.this.print(": ");
            callableDeclaration.getReturnTypeRef().accept(this);
            ConeContractRenderer contractRenderer = FirRenderer.this.getContractRenderer();
            if (contractRenderer != null) {
                contractRenderer.render(callableDeclaration);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitContextReceiver(FirContextReceiver contextReceiver) {
            Intrinsics.checkNotNullParameter(contextReceiver, "contextReceiver");
            Name customLabelName = contextReceiver.getCustomLabelName();
            if (customLabelName != null) {
                FirRenderer.this.print(customLabelName.asString() + '@');
            }
            contextReceiver.getTypeRef().accept(this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeParameterRef(FirTypeParameterRef typeParameterRef) {
            Intrinsics.checkNotNullParameter(typeParameterRef, "typeParameterRef");
            ((FirTypeParameter) typeParameterRef.getSymbol().getFir()).accept(this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitOuterClassTypeParameterRef(FirOuterClassTypeParameterRef outerClassTypeParameterRef) {
            Intrinsics.checkNotNullParameter(outerClassTypeParameterRef, "outerClassTypeParameterRef");
            renderTypeParameter((FirTypeParameter) outerClassTypeParameterRef.getSymbol().getFir(), true);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitConstructedClassTypeParameterRef(FirConstructedClassTypeParameterRef constructedClassTypeParameterRef) {
            Intrinsics.checkNotNullParameter(constructedClassTypeParameterRef, "constructedClassTypeParameterRef");
            visitTypeParameterRef(constructedClassTypeParameterRef);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitMemberDeclaration(FirMemberDeclaration memberDeclaration) {
            Intrinsics.checkNotNullParameter(memberDeclaration, "memberDeclaration");
            FirModifierRenderer modifierRenderer = FirRenderer.this.getModifierRenderer();
            if (modifierRenderer != null) {
                modifierRenderer.renderModifiers(memberDeclaration);
            }
            FirDeclarationRenderer declarationRenderer = FirRenderer.this.getDeclarationRenderer();
            if (declarationRenderer != null) {
                declarationRenderer.render(memberDeclaration);
            }
            if (memberDeclaration instanceof FirClassLikeDeclaration) {
                if (memberDeclaration instanceof FirRegularClass) {
                    FirRenderer.this.print(" " + ((FirRegularClass) memberDeclaration).getName());
                }
                if (memberDeclaration instanceof FirTypeAlias) {
                    FirRenderer.this.print(" " + ((FirTypeAlias) memberDeclaration).getName());
                }
                FirRenderer.this.renderTypeParameters(memberDeclaration.getTypeParameters());
                return;
            }
            if (!(memberDeclaration instanceof FirCallableDeclaration) || memberDeclaration.getTypeParameters().isEmpty()) {
                return;
            }
            FirRenderer.this.print(" ");
            FirRenderer.this.renderTypeParameters(memberDeclaration.getTypeParameters());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitRegularClass(FirRegularClass regularClass) {
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            FirRenderer.this.renderContexts(regularClass.getContextReceivers());
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, regularClass, null, 2, null);
            }
            visitMemberDeclaration(regularClass);
            FirRenderer.this.renderSupertypes(regularClass);
            FirClassMemberRenderer classMemberRenderer = FirRenderer.this.getClassMemberRenderer();
            if (classMemberRenderer != null) {
                classMemberRenderer.render(regularClass);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitEnumEntry(FirEnumEntry enumEntry) {
            Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
            visitCallableDeclaration(enumEntry);
            FirExpression initializer = enumEntry.getInitializer();
            if (initializer != null) {
                FirRenderer.this.print(" = ");
                initializer.accept(this);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression) {
            Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
            anonymousObjectExpression.getAnonymousObject().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAnonymousObject(FirAnonymousObject anonymousObject) {
            Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, anonymousObject, null, 2, null);
            }
            FirRenderer.this.print("object : ");
            FirRenderer.this.renderSeparated(anonymousObject.getSuperTypeRefs(), FirRenderer.this.getVisitor());
            FirClassMemberRenderer classMemberRenderer = FirRenderer.this.getClassMemberRenderer();
            if (classMemberRenderer != null) {
                classMemberRenderer.render(anonymousObject.getDeclarations());
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitVariable(FirVariable variable) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            visitCallableDeclaration(variable);
            FirBodyRenderer bodyRenderer = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer != null) {
                bodyRenderer.render(variable);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitField(FirField field) {
            Intrinsics.checkNotNullParameter(field, "field");
            visitVariable(field);
            FirRenderer.this.getPrinter().newLine();
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitProperty(FirProperty property) {
            FirPropertyAccessorRenderer propertyAccessorRenderer;
            Intrinsics.checkNotNullParameter(property, "property");
            visitVariable(property);
            if (property.getIsLocal() || (propertyAccessorRenderer = FirRenderer.this.getPropertyAccessorRenderer()) == null) {
                return;
            }
            propertyAccessorRenderer.render(property);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitErrorProperty(FirErrorProperty errorProperty) {
            Intrinsics.checkNotNullParameter(errorProperty, "errorProperty");
            FirRenderer.this.print("<ERROR PROPERTY: " + errorProperty.getDiagnostic().getReason() + '>');
            FirRenderer.this.getPrinter().newLine();
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitErrorFunction(FirErrorFunction errorFunction) {
            Intrinsics.checkNotNullParameter(errorFunction, "errorFunction");
            FirRenderer.this.print("<ERROR FUNCTION: " + errorFunction.getDiagnostic().getReason() + '>');
            FirRenderer.this.getPrinter().newLine();
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitBackingField(FirBackingField backingField) {
            Intrinsics.checkNotNullParameter(backingField, "backingField");
            FirModifierRenderer modifierRenderer = FirRenderer.this.getModifierRenderer();
            if (modifierRenderer != null) {
                modifierRenderer.renderModifiers(backingField);
            }
            FirRenderer.this.print("<explicit backing field>: ");
            Visitor visitor = this;
            backingField.getReturnTypeRef().accept(visitor);
            FirExpression initializer = backingField.getInitializer();
            if (initializer != null) {
                FirRenderer.this.print(" = ");
                initializer.accept(visitor);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitReceiverParameter(FirReceiverParameter receiverParameter) {
            Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
            FirRenderer.this.print("<explicit receiver parameter>: ");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, receiverParameter, null, 2, null);
            }
            receiverParameter.getTypeRef().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitSimpleFunction(FirSimpleFunction simpleFunction) {
            Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
            visitCallableDeclaration(simpleFunction);
            FirBodyRenderer bodyRenderer = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer != null) {
                bodyRenderer.render(simpleFunction);
            }
            if (simpleFunction.getBody() == null) {
                FirRenderer.this.getPrinter().newLine();
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitConstructor(FirConstructor constructor) {
            FirBodyRenderer bodyRenderer;
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, constructor, null, 2, null);
            }
            FirModifierRenderer modifierRenderer = FirRenderer.this.getModifierRenderer();
            if (modifierRenderer != null) {
                modifierRenderer.renderModifiers(constructor);
            }
            FirDeclarationRenderer declarationRenderer = FirRenderer.this.getDeclarationRenderer();
            if (declarationRenderer != null) {
                declarationRenderer.render(constructor);
            }
            FirRenderer.this.renderTypeParameters(constructor.getTypeParameters());
            FirValueParameterRenderer valueParameterRenderer = FirRenderer.this.getValueParameterRenderer();
            if (valueParameterRenderer != null) {
                valueParameterRenderer.renderParameters(constructor.getValueParameters());
            }
            FirRenderer.this.print(": ");
            constructor.getReturnTypeRef().accept(this);
            FirBlock body = constructor.getBody();
            FirDelegatedConstructorCall delegatedConstructor = constructor.getDelegatedConstructor();
            if (body == null && (bodyRenderer = FirRenderer.this.getBodyRenderer()) != null) {
                bodyRenderer.renderDelegatedConstructor(delegatedConstructor);
            }
            FirBodyRenderer bodyRenderer2 = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer2 != null) {
                bodyRenderer2.renderBody(body, CollectionsKt.listOfNotNull(delegatedConstructor));
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitPropertyAccessor(FirPropertyAccessor propertyAccessor) {
            Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, propertyAccessor, null, 2, null);
            }
            FirModifierRenderer modifierRenderer = FirRenderer.this.getModifierRenderer();
            if (modifierRenderer != null) {
                modifierRenderer.renderModifiers(propertyAccessor);
            }
            FirDeclarationRenderer declarationRenderer = FirRenderer.this.getDeclarationRenderer();
            if (declarationRenderer != null) {
                declarationRenderer.render(propertyAccessor);
            }
            FirValueParameterRenderer valueParameterRenderer = FirRenderer.this.getValueParameterRenderer();
            if (valueParameterRenderer != null) {
                valueParameterRenderer.renderParameters(propertyAccessor.getValueParameters());
            }
            FirRenderer.this.print(": ");
            propertyAccessor.getReturnTypeRef().accept(this);
            ConeContractRenderer contractRenderer = FirRenderer.this.getContractRenderer();
            if (contractRenderer != null) {
                contractRenderer.render(propertyAccessor);
            }
            FirBodyRenderer bodyRenderer = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer != null) {
                bodyRenderer.render(propertyAccessor);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression) {
            Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
            visitAnonymousFunction(anonymousFunctionExpression.getAnonymousFunction());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAnonymousFunction(FirAnonymousFunction anonymousFunction) {
            Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, anonymousFunction, null, 2, null);
            }
            FirModifierRenderer modifierRenderer = FirRenderer.this.getModifierRenderer();
            if (modifierRenderer != null) {
                modifierRenderer.renderModifiers(anonymousFunction);
            }
            FirDeclarationRenderer declarationRenderer = FirRenderer.this.getDeclarationRenderer();
            if (declarationRenderer != null) {
                declarationRenderer.render(anonymousFunction);
            }
            FirRenderer.this.print(" ");
            FirReceiverParameter receiverParameter = anonymousFunction.getReceiverParameter();
            if (receiverParameter != null) {
                receiverParameter.getTypeRef().accept(this);
                FirRenderer.this.print(".");
            }
            FirRenderer.this.print(SpecialNames.ANONYMOUS_STRING);
            if (anonymousFunction.getValueParameters().isEmpty() && anonymousFunction.getHasExplicitParameterList() && (anonymousFunction.getReturnTypeRef() instanceof FirImplicitTypeRef)) {
                FirRenderer.this.print("(<no-parameters>)");
            }
            FirValueParameterRenderer valueParameterRenderer = FirRenderer.this.getValueParameterRenderer();
            if (valueParameterRenderer != null) {
                valueParameterRenderer.renderParameters(anonymousFunction.getValueParameters());
            }
            FirRenderer.this.print(": ");
            anonymousFunction.getReturnTypeRef().accept(this);
            FirRenderer.this.print(" <inline=" + anonymousFunction.getInlineStatus());
            if (anonymousFunction.getInvocationKind() != null) {
                FirRenderer.this.print(", kind=" + anonymousFunction.getInvocationKind());
            }
            FirRenderer.this.print("> ");
            FirBodyRenderer bodyRenderer = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer != null) {
                bodyRenderer.render(anonymousFunction);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitFunction(FirFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            FirValueParameterRenderer valueParameterRenderer = FirRenderer.this.getValueParameterRenderer();
            if (valueParameterRenderer != null) {
                valueParameterRenderer.renderParameters(function.getValueParameters());
            }
            FirDeclarationRenderer declarationRenderer = FirRenderer.this.getDeclarationRenderer();
            if (declarationRenderer != null) {
                declarationRenderer.render(function);
            }
            FirBodyRenderer bodyRenderer = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer != null) {
                bodyRenderer.render(function);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAnonymousInitializer(FirAnonymousInitializer anonymousInitializer) {
            Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
            FirRenderer.this.print(ConfigConstants.CONFIG_INIT_SECTION);
            FirBodyRenderer bodyRenderer = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer != null) {
                FirBodyRenderer.renderBody$default(bodyRenderer, anonymousInitializer.getBody(), null, 2, null);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitDanglingModifierList(FirDanglingModifierList danglingModifierList) {
            Intrinsics.checkNotNullParameter(danglingModifierList, "danglingModifierList");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, danglingModifierList, null, 2, null);
            }
            FirRenderer.this.print("<DANGLING MODIFIER: " + danglingModifierList.getDiagnostic().getReason() + '>');
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitBlock(FirBlock block) {
            Intrinsics.checkNotNullParameter(block, "block");
            FirBodyRenderer bodyRenderer = FirRenderer.this.getBodyRenderer();
            if (bodyRenderer != null) {
                FirBodyRenderer.renderBody$default(bodyRenderer, block, null, 2, null);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeAlias(FirTypeAlias typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, typeAlias, null, 2, null);
            }
            visitMemberDeclaration(typeAlias);
            FirRenderer.this.print(" = ");
            typeAlias.getExpandedTypeRef().accept(this);
            FirRenderer.this.getPrinter().newLine();
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeParameter(FirTypeParameter typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            renderTypeParameter$default(this, typeParameter, false, 2, null);
        }

        static /* synthetic */ void renderTypeParameter$default(Visitor visitor, FirTypeParameter firTypeParameter, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            visitor.renderTypeParameter(firTypeParameter, z);
        }

        private final void renderTypeParameter(FirTypeParameter typeParameter, boolean forOuterTypeRef) {
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, typeParameter, null, 2, null);
            }
            FirModifierRenderer modifierRenderer = FirRenderer.this.getModifierRenderer();
            if (modifierRenderer != null) {
                modifierRenderer.renderModifiers(typeParameter);
            }
            FirResolvePhaseRenderer resolvePhaseRenderer = FirRenderer.this.getResolvePhaseRenderer();
            if (resolvePhaseRenderer != null) {
                resolvePhaseRenderer.render(typeParameter);
            }
            FirRenderer.this.renderVariance(typeParameter.getVariance());
            if (!forOuterTypeRef) {
                FirRenderer.this.print(typeParameter.getName());
            } else {
                FirRenderer.this.print("Outer(" + typeParameter.getName() + ')');
            }
            List<FirTypeRef> bounds = typeParameter.getBounds();
            ArrayList arrayList = new ArrayList();
            for (Object obj : bounds) {
                FirTypeRef firTypeRef = (FirTypeRef) obj;
                if (firTypeRef instanceof FirResolvedTypeRef) {
                    FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) firTypeRef;
                    if (ConeTypeUtilsKt.isNullable(firResolvedTypeRef.getType())) {
                        ConeKotlinType type = firResolvedTypeRef.getType();
                        ConeLookupTagBasedType coneLookupTagBasedType = type instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) type : null;
                        if (coneLookupTagBasedType != null) {
                            ConeClassifierLookupTag lookupTag = coneLookupTagBasedType.getLookupTag();
                            ConeClassLikeLookupTag coneClassLikeLookupTag = lookupTag instanceof ConeClassLikeLookupTag ? (ConeClassLikeLookupTag) lookupTag : null;
                            if (!Intrinsics.areEqual(coneClassLikeLookupTag != null ? coneClassLikeLookupTag.getClassId() : null, StandardClassIds.INSTANCE.getAny())) {
                            }
                        }
                    }
                }
                arrayList.add(obj);
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                return;
            }
            FirRenderer.this.print(" : ");
            FirRenderer firRenderer = FirRenderer.this;
            firRenderer.renderSeparated(arrayList2, firRenderer.getVisitor());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitSafeCallExpression(FirSafeCallExpression safeCallExpression) {
            Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
            Visitor visitor = this;
            safeCallExpression.getReceiver().accept(visitor);
            FirRenderer.this.print("?.{ ");
            safeCallExpression.getSelector().accept(visitor);
            FirRenderer.this.print(" }");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject) {
            Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
            FirRenderer.this.print("$subj$");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitValueParameter(FirValueParameter valueParameter) {
            Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
            FirValueParameterRenderer valueParameterRenderer = FirRenderer.this.getValueParameterRenderer();
            if (valueParameterRenderer != null) {
                valueParameterRenderer.renderParameter(valueParameter);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitImport(FirImport firImport) {
            Intrinsics.checkNotNullParameter(firImport, "import");
            mo6787visitElement(firImport);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitStatement(FirStatement statement) {
            Intrinsics.checkNotNullParameter(statement, "statement");
            mo6787visitElement(statement);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitReturnExpression(FirReturnExpression returnExpression) {
            Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, returnExpression, null, 2, null);
            }
            FirRenderer.this.print("^");
            FirTarget<FirFunction> target = returnExpression.getTarget();
            FirFunction firFunction = (FirFunction) target.getLabeledElement();
            if (firFunction instanceof FirSimpleFunction) {
                FirRenderer.this.print(String.valueOf(((FirSimpleFunction) firFunction).getName()));
            } else {
                String labelName = target.getLabelName();
                if (labelName != null) {
                    FirRenderer.this.print(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + labelName);
                }
            }
            FirRenderer.this.print(" ");
            returnExpression.getResult().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitWhenBranch(FirWhenBranch whenBranch) {
            Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
            FirExpression condition = whenBranch.getCondition();
            if (condition instanceof FirElseIfTrueCondition) {
                FirRenderer.this.print(PsiKeyword.ELSE);
            } else {
                condition.accept(this);
            }
            FirRenderer.this.print(" -> ");
            whenBranch.getResult().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitWhenExpression(FirWhenExpression whenExpression) {
            Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, whenExpression, null, 2, null);
            }
            FirRenderer.this.print("when (");
            FirVariable subjectVariable = whenExpression.getSubjectVariable();
            if (subjectVariable != null) {
                subjectVariable.accept(this);
            } else {
                FirExpression subject = whenExpression.getSubject();
                if (subject != null) {
                    subject.accept(this);
                }
            }
            FirRenderer.this.getPrinter().println(") {");
            FirRenderer.this.getPrinter().pushIndent$tree();
            Iterator<FirWhenBranch> it = whenExpression.getBranches().iterator();
            while (it.hasNext()) {
                it.next().accept(this);
            }
            FirRenderer.this.getPrinter().popIndent$tree();
            FirRenderer.this.getPrinter().println("}");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression) {
            Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
            FirRenderer.this.print("$subj$");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTryExpression(FirTryExpression tryExpression) {
            Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, tryExpression, null, 2, null);
            }
            FirRenderer.this.print(PsiKeyword.TRY);
            Visitor visitor = this;
            tryExpression.getTryBlock().accept(visitor);
            for (FirCatch firCatch : tryExpression.getCatches()) {
                FirRenderer.this.print("catch (");
                firCatch.getParameter().accept(visitor);
                FirRenderer.this.print(Constant.AFTER_QUTO);
                firCatch.getBlock().accept(visitor);
            }
            FirBlock finallyBlock = tryExpression.getFinallyBlock();
            if (finallyBlock == null) {
                return;
            }
            FirRenderer.this.print(PsiKeyword.FINALLY);
            finallyBlock.accept(visitor);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitDoWhileLoop(FirDoWhileLoop doWhileLoop) {
            Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
            FirLabel label = doWhileLoop.getLabel();
            if (label != null) {
                FirRenderer.this.print(label.getName() + '@');
            }
            FirRenderer.this.print(PsiKeyword.DO);
            Visitor visitor = this;
            doWhileLoop.getBlock().accept(visitor);
            FirRenderer.this.print("while(");
            doWhileLoop.getCondition().accept(visitor);
            FirRenderer.this.print(Constant.AFTER_QUTO);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitWhileLoop(FirWhileLoop whileLoop) {
            Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
            FirLabel label = whileLoop.getLabel();
            if (label != null) {
                FirRenderer.this.print(label.getName() + '@');
            }
            FirRenderer.this.print("while(");
            Visitor visitor = this;
            whileLoop.getCondition().accept(visitor);
            FirRenderer.this.print(Constant.AFTER_QUTO);
            whileLoop.getBlock().accept(visitor);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitLoopJump(FirLoopJump loopJump) {
            Intrinsics.checkNotNullParameter(loopJump, "loopJump");
            if (this.loopJumpStack.contains(loopJump)) {
                return;
            }
            this.loopJumpStack.push(loopJump);
            FirLoop firLoop = (FirLoop) loopJump.getTarget().getLabeledElement();
            FirRenderer.this.print("@@@[");
            firLoop.getCondition().accept(this);
            FirRenderer.this.print("] ");
            this.loopJumpStack.pop();
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitBreakExpression(FirBreakExpression breakExpression) {
            Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, breakExpression, null, 2, null);
            }
            FirRenderer.this.print(PsiKeyword.BREAK);
            visitLoopJump(breakExpression);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitContinueExpression(FirContinueExpression continueExpression) {
            Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, continueExpression, null, 2, null);
            }
            FirRenderer.this.print(PsiKeyword.CONTINUE);
            visitLoopJump(continueExpression);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitExpression(FirExpression expression) {
            String str;
            Intrinsics.checkNotNullParameter(expression, "expression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, expression, null, 2, null);
            }
            FirRenderer firRenderer = FirRenderer.this;
            if (expression instanceof FirExpressionStub) {
                str = "STUB";
            } else if (expression instanceof FirUnitExpression) {
                str = "Unit";
            } else if (expression instanceof FirElseIfTrueCondition) {
                str = PsiKeyword.ELSE;
            } else {
                str = expression instanceof FirNoReceiverExpression ? "" : "??? " + expression.getClass();
            }
            firRenderer.print(str);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitLazyExpression(FirLazyExpression lazyExpression) {
            Intrinsics.checkNotNullParameter(lazyExpression, "lazyExpression");
            FirRenderer.this.print("LAZY_EXPRESSION");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public <T> void visitConstExpression(FirConstExpression<T> constExpression) {
            Intrinsics.checkNotNullParameter(constExpression, "constExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, constExpression, null, 2, null);
            }
            ConstantValueKind<T> kind = constExpression.getKind();
            T value = constExpression.getValue();
            FirRenderer.this.print(new StringBuilder().append(kind).append('(').toString());
            if (!(value instanceof Character)) {
                FirRenderer.this.print(String.valueOf(value));
            } else {
                Character ch = (Character) value;
                char cCharValue = ch.charValue();
                if (' ' > cCharValue || cCharValue >= 128) {
                    FirRenderer.this.print(Integer.valueOf(ch.charValue()));
                } else {
                    FirRenderer.this.print(value);
                }
            }
            FirRenderer.this.print(Constant.AFTER_QUTO);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitWrappedDelegateExpression(FirWrappedDelegateExpression wrappedDelegateExpression) {
            Intrinsics.checkNotNullParameter(wrappedDelegateExpression, "wrappedDelegateExpression");
            wrappedDelegateExpression.getExpression().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitEnumEntryDeserializedAccessExpression(FirEnumEntryDeserializedAccessExpression enumEntryDeserializedAccessExpression) {
            Intrinsics.checkNotNullParameter(enumEntryDeserializedAccessExpression, "enumEntryDeserializedAccessExpression");
            FirRenderer.this.print(new StringBuilder().append(enumEntryDeserializedAccessExpression.getEnumClassId()).append('.').append(enumEntryDeserializedAccessExpression.getEnumEntryName()).toString());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression) {
            Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
            FirRenderer.this.print(namedArgumentExpression.getName());
            FirRenderer.this.print(" = ");
            if (namedArgumentExpression.getIsSpread()) {
                FirRenderer.this.print("*");
            }
            namedArgumentExpression.getExpression().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitSpreadArgumentExpression(FirSpreadArgumentExpression spreadArgumentExpression) {
            Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
            if (spreadArgumentExpression.getIsSpread()) {
                FirRenderer.this.print("*");
            }
            spreadArgumentExpression.getExpression().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitLambdaArgumentExpression(FirLambdaArgumentExpression lambdaArgumentExpression) {
            Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
            FirRenderer.this.print("<L> = ");
            lambdaArgumentExpression.getExpression().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression) {
            Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
            FirRenderer.this.print("vararg(");
            FirRenderer.this.renderSeparated(varargArgumentsExpression.getArguments(), FirRenderer.this.getVisitor());
            FirRenderer.this.print(Constant.AFTER_QUTO);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitCall(FirCall call) {
            Intrinsics.checkNotNullParameter(call, "call");
            FirCallArgumentsRenderer callArgumentsRenderer = FirRenderer.this.getCallArgumentsRenderer();
            if (callArgumentsRenderer != null) {
                callArgumentsRenderer.renderArguments(call.getArgumentList().getArguments());
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall) {
            Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
            FirRenderer.this.print("<strcat>");
            visitCall(stringConcatenationCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeOperatorCall(FirTypeOperatorCall typeOperatorCall) {
            Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
            FirRenderer.this.print("(");
            Visitor visitor = this;
            ((FirExpression) CollectionsKt.first((List) typeOperatorCall.getArgumentList().getArguments())).accept(visitor);
            FirRenderer.this.print(" ");
            FirRenderer.this.print(typeOperatorCall.getOperation().getOperator());
            FirRenderer.this.print(" ");
            typeOperatorCall.getConversionTypeRef().accept(visitor);
            FirRenderer.this.print(Constant.AFTER_QUTO);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall) {
            Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
            boolean z = delegatedConstructorCall instanceof FirLazyDelegatedConstructorCall;
            if (!z) {
                FirExpression dispatchReceiver = delegatedConstructorCall.getDispatchReceiver();
                if (!(dispatchReceiver instanceof FirNoReceiverExpression)) {
                    dispatchReceiver.accept(this);
                    FirRenderer.this.print(".");
                }
            }
            if (z) {
                FirRenderer.this.print("LAZY_");
            }
            if (delegatedConstructorCall.isSuper()) {
                FirRenderer.this.print("super<");
            } else if (delegatedConstructorCall.getIsThis()) {
                FirRenderer.this.print("this<");
            }
            delegatedConstructorCall.getConstructedTypeRef().accept(this);
            FirRenderer.this.print(">");
            if (z) {
                return;
            }
            visitCall(delegatedConstructorCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitMultiDelegatedConstructorCall(FirMultiDelegatedConstructorCall multiDelegatedConstructorCall) {
            Intrinsics.checkNotNullParameter(multiDelegatedConstructorCall, "multiDelegatedConstructorCall");
            boolean z = true;
            for (FirDelegatedConstructorCall firDelegatedConstructorCall : multiDelegatedConstructorCall.getDelegatedConstructorCalls()) {
                if (z) {
                    z = false;
                } else {
                    FirRenderer.this.getPrinter().println(new Object[0]);
                }
                visitDelegatedConstructorCall(firDelegatedConstructorCall);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeRef(FirTypeRef typeRef) {
            Intrinsics.checkNotNullParameter(typeRef, "typeRef");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, typeRef, null, 2, null);
            }
            mo6787visitElement(typeRef);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitErrorTypeRef(FirErrorTypeRef errorTypeRef) {
            Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, errorTypeRef, null, 2, null);
            }
            FirRenderer.this.print("<ERROR TYPE REF: " + errorTypeRef.getDiagnostic().getReason() + '>');
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitImplicitTypeRef(FirImplicitTypeRef implicitTypeRef) {
            Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
            FirRenderer.this.print("<implicit>");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeRefWithNullability(FirTypeRefWithNullability typeRefWithNullability) {
            Intrinsics.checkNotNullParameter(typeRefWithNullability, "typeRefWithNullability");
            if (typeRefWithNullability.getIsMarkedNullable()) {
                FirRenderer.this.print(TypeDescription.Generic.OfWildcardType.SYMBOL);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitDynamicTypeRef(FirDynamicTypeRef dynamicTypeRef) {
            Intrinsics.checkNotNullParameter(dynamicTypeRef, "dynamicTypeRef");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, dynamicTypeRef, null, 2, null);
            }
            FirRenderer.this.print(MangleConstant.DYNAMIC_MARK);
            visitTypeRefWithNullability(dynamicTypeRef);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitFunctionTypeRef(FirFunctionTypeRef functionTypeRef) {
            Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
            if (!functionTypeRef.getContextReceiverTypeRefs().isEmpty()) {
                FirRenderer.this.print("context(");
                FirRenderer.this.renderSeparated(functionTypeRef.getContextReceiverTypeRefs(), FirRenderer.this.getVisitor());
                FirRenderer.this.print(Constant.AFTER_QUTO);
            }
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.renderAnnotations$tree$default(annotationRenderer, FirTypeUtilsKt.dropExtensionFunctionAnnotation(functionTypeRef.getAnnotations()), null, 2, null);
            }
            FirRenderer.this.print("( ");
            FirModifierRenderer modifierRenderer = FirRenderer.this.getModifierRenderer();
            if (modifierRenderer != null) {
                modifierRenderer.renderModifiers(functionTypeRef);
            }
            FirTypeRef receiverTypeRef = functionTypeRef.getReceiverTypeRef();
            if (receiverTypeRef != null) {
                FirRenderer firRenderer = FirRenderer.this;
                receiverTypeRef.accept(this);
                firRenderer.print(".");
            }
            FirRenderer.this.getPrinter().print("(");
            int i = 0;
            for (FirFunctionTypeParameter firFunctionTypeParameter : functionTypeRef.getParameters()) {
                int i2 = i + 1;
                if (i > 0) {
                    FirRenderer.this.getPrinter().print(", ");
                }
                Name name2 = firFunctionTypeParameter.getName();
                if (name2 != null) {
                    FirRenderer firRenderer2 = FirRenderer.this;
                    FirPrinter printer = firRenderer2.getPrinter();
                    String strAsString = name2.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    printer.print(strAsString);
                    firRenderer2.getPrinter().print(": ");
                }
                firFunctionTypeParameter.getReturnTypeRef().accept(FirRenderer.this.getVisitor());
                i = i2;
            }
            FirRenderer.this.getPrinter().print(Constant.AFTER_QUTO);
            FirRenderer.this.print(" -> ");
            functionTypeRef.getReturnTypeRef().accept(this);
            FirRenderer.this.print(" )");
            visitTypeRefWithNullability(functionTypeRef);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef) {
            Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
            ConeTypeRenderer.renderAsPossibleFunctionType$default(FirRenderer.this.getTypeRenderer(), resolvedTypeRef.getType(), new Function1<ConeKotlinType, FunctionTypeKind>() { // from class: org.jetbrains.kotlin.fir.renderer.FirRenderer$Visitor$visitResolvedTypeRef$1
                @Override // kotlin.jvm.functions.Function1
                public final FunctionTypeKind invoke(ConeKotlinType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    ClassId classId = ConeTypeUtilsKt.getClassId(it);
                    if (classId == null) {
                        return null;
                    }
                    FunctionTypeKindExtractor functionTypeKindExtractor = FunctionTypeKindExtractor.INSTANCE.getDefault();
                    FqName packageFqName = classId.getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                    String strAsString = classId.getShortClassName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    return functionTypeKindExtractor.getFunctionalClassKind(packageFqName, strAsString);
                }
            }, null, 4, null);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitUserTypeRef(FirUserTypeRef userTypeRef) {
            Intrinsics.checkNotNullParameter(userTypeRef, "userTypeRef");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, userTypeRef, null, 2, null);
            }
            if (userTypeRef.getCustomRenderer()) {
                FirRenderer.this.print(userTypeRef.toString());
                return;
            }
            int i = 0;
            for (FirQualifierPart firQualifierPart : userTypeRef.getQualifier()) {
                int i2 = i + 1;
                if (i != 0) {
                    FirRenderer.this.print(".");
                }
                FirRenderer.this.print(firQualifierPart.getName());
                if (!firQualifierPart.getTypeArgumentList().getTypeArguments().isEmpty()) {
                    FirRenderer.this.print("<");
                    FirRenderer.this.renderSeparated(firQualifierPart.getTypeArgumentList().getTypeArguments(), FirRenderer.this.getVisitor());
                    FirRenderer.this.print(">");
                }
                i = i2;
            }
            visitTypeRefWithNullability(userTypeRef);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeProjection(FirTypeProjection typeProjection) {
            Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
            mo6787visitElement(typeProjection);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeProjectionWithVariance(FirTypeProjectionWithVariance typeProjectionWithVariance) {
            Intrinsics.checkNotNullParameter(typeProjectionWithVariance, "typeProjectionWithVariance");
            FirRenderer.this.renderVariance(typeProjectionWithVariance.getVariance());
            typeProjectionWithVariance.getTypeRef().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitStarProjection(FirStarProjection starProjection) {
            Intrinsics.checkNotNullParameter(starProjection, "starProjection");
            FirRenderer.this.print("*");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitNamedReference(FirNamedReference namedReference) {
            Intrinsics.checkNotNullParameter(namedReference, "namedReference");
            FirRenderer.this.print(new StringBuilder().append(namedReference.getName()).append('#').toString());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitNamedReferenceWithCandidateBase(FirNamedReferenceWithCandidateBase namedReferenceWithCandidateBase) {
            Intrinsics.checkNotNullParameter(namedReferenceWithCandidateBase, "namedReferenceWithCandidateBase");
            FirRenderer.this.print("R?C|");
            FirRenderer.this.getReferencedSymbolRenderer().printReference(namedReferenceWithCandidateBase.getCandidateSymbol());
            FirRenderer.this.print("|");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitErrorNamedReference(FirErrorNamedReference errorNamedReference) {
            Intrinsics.checkNotNullParameter(errorNamedReference, "errorNamedReference");
            FirRenderer.this.print("<" + errorNamedReference.getDiagnostic().getReason() + ">#");
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitBackingFieldReference(FirBackingFieldReference backingFieldReference) {
            Intrinsics.checkNotNullParameter(backingFieldReference, "backingFieldReference");
            FirRenderer.this.print("F|");
            FirRenderer.this.print(((FirBackingField) backingFieldReference.getResolvedSymbol().getFir()).getPropertySymbol().getCallableId());
            FirRenderer.this.print("|");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitDelegateFieldReference(FirDelegateFieldReference delegateFieldReference) {
            Intrinsics.checkNotNullParameter(delegateFieldReference, "delegateFieldReference");
            FirRenderer.this.print("D|");
            FirRenderer.this.print(delegateFieldReference.getResolvedSymbol().getCallableId());
            FirRenderer.this.print("|");
        }

        /* JADX WARN: Code duplicated, block: B:11:0x0027  */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitResolvedNamedReference(FirResolvedNamedReference resolvedNamedReference) {
            boolean z;
            Intrinsics.checkNotNullParameter(resolvedNamedReference, "resolvedNamedReference");
            FirRenderer.this.print("R|");
            FirBasedSymbol<?> resolvedSymbol = resolvedNamedReference.getResolvedSymbol();
            FirDeclaration fir = resolvedSymbol.getFir();
            FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
            int i = 0;
            if (firCallableDeclaration != null) {
                z = ClassMembersKt.isSubstitutionOverride(firCallableDeclaration);
            }
            if (z) {
                FirRenderer.this.print("SubstitutionOverride<");
            }
            FirRenderer.this.getReferencedSymbolRenderer().printReference(unwrapIntersectionOverrides(resolvedSymbol));
            if (resolvedNamedReference instanceof FirResolvedCallableReference) {
                FirResolvedCallableReference firResolvedCallableReference = (FirResolvedCallableReference) resolvedNamedReference;
                if (!firResolvedCallableReference.getInferredTypeArguments().isEmpty()) {
                    FirRenderer.this.print("<");
                    for (ConeKotlinType coneKotlinType : firResolvedCallableReference.getInferredTypeArguments()) {
                        int i2 = i + 1;
                        if (i > 0) {
                            FirRenderer.this.print(", ");
                        }
                        FirRenderer.this.getTypeRenderer().render(coneKotlinType);
                        i = i2;
                    }
                    FirRenderer.this.print(">");
                }
            }
            if (z) {
                if (resolvedSymbol instanceof FirNamedFunctionSymbol) {
                    FirRenderer.this.print(": ");
                    ((FirSimpleFunction) ((FirNamedFunctionSymbol) resolvedSymbol).getFir()).getReturnTypeRef().accept(this);
                } else if (resolvedSymbol instanceof FirPropertySymbol) {
                    FirRenderer.this.print(": ");
                    ((FirProperty) ((FirPropertySymbol) resolvedSymbol).getFir()).getReturnTypeRef().accept(this);
                }
                FirRenderer.this.print(">");
            }
            if (resolvedNamedReference instanceof FirResolvedErrorReference) {
                FirRenderer.this.print("<" + ((FirResolvedErrorReference) resolvedNamedReference).getDiagnostic().getReason() + ">#");
            }
            FirRenderer.this.print("|");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitResolvedErrorReference(FirResolvedErrorReference resolvedErrorReference) {
            Intrinsics.checkNotNullParameter(resolvedErrorReference, "resolvedErrorReference");
            visitResolvedNamedReference(resolvedErrorReference);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final FirBasedSymbol<?> unwrapIntersectionOverrides(FirBasedSymbol<?> firBasedSymbol) {
            FirCallableSymbol firCallableSymbol = firBasedSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) firBasedSymbol : null;
            if (firCallableSymbol == null) {
                return firBasedSymbol;
            }
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol.getFir();
            FirCallableDeclaration originalForIntersectionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            FirCallableSymbol<? extends FirCallableDeclaration> symbol = originalForIntersectionOverrideAttr != null ? originalForIntersectionOverrideAttr.getSymbol() : null;
            return symbol != null ? unwrapIntersectionOverrides(symbol) : firBasedSymbol;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitResolvedCallableReference(FirResolvedCallableReference resolvedCallableReference) {
            Intrinsics.checkNotNullParameter(resolvedCallableReference, "resolvedCallableReference");
            visitResolvedNamedReference(resolvedCallableReference);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitThisReference(FirThisReference thisReference) {
            Intrinsics.checkNotNullParameter(thisReference, "thisReference");
            FirRenderer.this.print("this");
            String labelName = thisReference.getLabelName();
            FirBasedSymbol<?> boundSymbol = thisReference.getBoundSymbol();
            if (boundSymbol != null) {
                FirRenderer.this.print("@R|");
                FirRenderer.this.getReferencedSymbolRenderer().printReference(boundSymbol);
                FirRenderer.this.print("|");
            } else if (labelName != null) {
                FirRenderer.this.print(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + labelName + '#');
            } else {
                FirRenderer.this.print("#");
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitSuperReference(FirSuperReference superReference) {
            Intrinsics.checkNotNullParameter(superReference, "superReference");
            FirRenderer.this.print("super<");
            superReference.getSuperTypeRef().accept(this);
            FirRenderer.this.print(">");
            String labelName = superReference.getLabelName();
            if (labelName != null) {
                FirRenderer.this.print(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + labelName + '#');
            }
        }

        private final void visitQualifiedAccessExpressionReceivers(FirQualifiedAccessExpression qualifiedAccess) {
            FirExpression explicitReceiver = qualifiedAccess.getExplicitReceiver();
            FirExpression dispatchReceiver = qualifiedAccess.getDispatchReceiver();
            FirExpression extensionReceiver = qualifiedAccess.getExtensionReceiver();
            boolean z = dispatchReceiver instanceof FirNoReceiverExpression;
            if (!z && !(extensionReceiver instanceof FirNoReceiverExpression)) {
                FirRenderer.this.print("(");
                Visitor visitor = this;
                dispatchReceiver.accept(visitor);
                FirRenderer.this.print(", ");
                extensionReceiver.accept(visitor);
                FirRenderer.this.print(Constant.AFTER_QUTO);
            } else if (!z) {
                dispatchReceiver.accept(this);
            } else if (!(extensionReceiver instanceof FirNoReceiverExpression)) {
                extensionReceiver.accept(this);
            } else if (explicitReceiver == null) {
                return;
            } else {
                explicitReceiver.accept(this);
            }
            FirRenderer.this.print(".");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitCheckNotNullCall(FirCheckNotNullCall checkNotNullCall) {
            Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
            ((FirExpression) CollectionsKt.first((List) checkNotNullCall.getArgumentList().getArguments())).accept(this);
            FirRenderer.this.print("!!");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitElvisExpression(FirElvisExpression elvisExpression) {
            Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
            Visitor visitor = this;
            elvisExpression.getLhs().accept(visitor);
            FirRenderer.this.print(" ?: ");
            elvisExpression.getRhs().accept(visitor);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess) {
            Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, callableReferenceAccess, null, 2, null);
            }
            FirExpression explicitReceiver = callableReferenceAccess.getExplicitReceiver();
            if (explicitReceiver != null) {
                explicitReceiver.accept(this);
            }
            if (callableReferenceAccess.getHasQuestionMarkAtLHS() && !(callableReferenceAccess.getExplicitReceiver() instanceof FirResolvedQualifier)) {
                FirRenderer.this.print(TypeDescription.Generic.OfWildcardType.SYMBOL);
            }
            FirRenderer.this.print("::");
            callableReferenceAccess.getCalleeReference().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression) {
            Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, qualifiedAccessExpression, null, 2, null);
            }
            visitQualifiedAccessExpressionReceivers(qualifiedAccessExpression);
            qualifiedAccessExpression.getCalleeReference().accept(this);
            FirRenderer.this.renderTypeArguments(qualifiedAccessExpression.getTypeArguments());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression) {
            Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
            visitQualifiedAccessExpression(propertyAccessExpression);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression) {
            Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
            visitQualifiedAccessExpression(thisReceiverExpression);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitSmartCastExpression(FirSmartCastExpression smartCastExpression) {
            Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
            smartCastExpression.getOriginalExpression().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression) {
            Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
            desugaredAssignmentValueReferenceExpression.getExpressionRef().getValue().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitVariableAssignment(FirVariableAssignment variableAssignment) {
            Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, variableAssignment, null, 2, null);
            }
            variableAssignment.getLValue().accept(this);
            FirRenderer.this.print(" ");
            FirRenderer.this.print(FirOperation.ASSIGN.getOperator());
            FirRenderer.this.print(" ");
            variableAssignment.getRValue().accept(FirRenderer.this.getVisitor());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall) {
            Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, augmentedArraySetCall, null, 2, null);
            }
            FirRenderer.this.print("ArraySet:[");
            Visitor visitor = this;
            augmentedArraySetCall.getLhsGetCall().accept(visitor);
            FirRenderer.this.print(" ");
            FirRenderer.this.print(augmentedArraySetCall.getOperation().getOperator());
            FirRenderer.this.print(" ");
            augmentedArraySetCall.getRhs().accept(visitor);
            FirRenderer.this.print("]");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitFunctionCall(FirFunctionCall functionCall) {
            Intrinsics.checkNotNullParameter(functionCall, "functionCall");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, functionCall, null, 2, null);
            }
            visitQualifiedAccessExpressionReceivers(functionCall);
            functionCall.getCalleeReference().accept(this);
            FirRenderer.this.renderTypeArguments(functionCall.getTypeArguments());
            visitCall(functionCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall integerLiteralOperatorCall) {
            Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
            visitFunctionCall(integerLiteralOperatorCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitImplicitInvokeCall(FirImplicitInvokeCall implicitInvokeCall) {
            Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
            visitFunctionCall(implicitInvokeCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitComparisonExpression(FirComparisonExpression comparisonExpression) {
            Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
            FirRenderer.this.print("CMP(" + comparisonExpression.getOperation().getOperator() + ", ");
            comparisonExpression.getCompareToCall().accept(this);
            FirRenderer.this.print(Constant.AFTER_QUTO);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAssignmentOperatorStatement(FirAssignmentOperatorStatement assignmentOperatorStatement) {
            Intrinsics.checkNotNullParameter(assignmentOperatorStatement, "assignmentOperatorStatement");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, assignmentOperatorStatement, null, 2, null);
            }
            FirRenderer.this.print(assignmentOperatorStatement.getOperation().getOperator());
            FirRenderer.this.print("(");
            assignmentOperatorStatement.getLeftArgument().accept(FirRenderer.this.getVisitor());
            FirRenderer.this.print(", ");
            assignmentOperatorStatement.getRightArgument().accept(FirRenderer.this.getVisitor());
            FirRenderer.this.print(Constant.AFTER_QUTO);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitIncrementDecrementExpression(FirIncrementDecrementExpression incrementDecrementExpression) {
            Intrinsics.checkNotNullParameter(incrementDecrementExpression, "incrementDecrementExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, incrementDecrementExpression, null, 2, null);
            }
            String str = Intrinsics.areEqual(incrementDecrementExpression.getOperationName(), OperatorNameConventions.INC) ? "++" : "--";
            if (incrementDecrementExpression.getIsPrefix()) {
                FirRenderer.this.print(str);
            }
            incrementDecrementExpression.getExpression().accept(FirRenderer.this.getVisitor());
            if (incrementDecrementExpression.getIsPrefix()) {
                return;
            }
            FirRenderer.this.print(str);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall) {
            Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, equalityOperatorCall, null, 2, null);
            }
            FirRenderer.this.print(equalityOperatorCall.getOperation().getOperator());
            visitCall(equalityOperatorCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitComponentCall(FirComponentCall componentCall) {
            Intrinsics.checkNotNullParameter(componentCall, "componentCall");
            visitFunctionCall(componentCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitGetClassCall(FirGetClassCall getClassCall) {
            Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, getClassCall, null, 2, null);
            }
            FirRenderer.this.print("<getClass>");
            visitCall(getClassCall);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitClassReferenceExpression(FirClassReferenceExpression classReferenceExpression) {
            Intrinsics.checkNotNullParameter(classReferenceExpression, "classReferenceExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, classReferenceExpression, null, 2, null);
            }
            FirRenderer.this.print("<getClass>");
            FirRenderer.this.print("(");
            classReferenceExpression.getClassTypeRef().accept(this);
            FirRenderer.this.print(Constant.AFTER_QUTO);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitArrayLiteral(FirArrayLiteral arrayLiteral) {
            Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, arrayLiteral, null, 2, null);
            }
            FirRenderer.this.print("<implicitArrayOf>");
            visitCall(arrayLiteral);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitThrowExpression(FirThrowExpression throwExpression) {
            Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, throwExpression, null, 2, null);
            }
            FirRenderer.this.print("throw ");
            throwExpression.getException().accept(this);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitErrorExpression(FirErrorExpression errorExpression) {
            Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
            FirErrorExpressionRenderer errorExpressionRenderer = FirRenderer.this.getErrorExpressionRenderer();
            if (errorExpressionRenderer != null) {
                errorExpressionRenderer.renderErrorExpression(errorExpression);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitResolvedQualifier(FirResolvedQualifier resolvedQualifier) {
            Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
            FirAnnotationRenderer annotationRenderer = FirRenderer.this.getAnnotationRenderer();
            if (annotationRenderer != null) {
                FirAnnotationRenderer.render$default(annotationRenderer, resolvedQualifier, null, 2, null);
            }
            FirRenderer.this.print("Q|");
            ClassId classId = resolvedQualifier.getClassId();
            if (classId != null) {
                FirRenderer firRenderer = FirRenderer.this;
                String strAsString = classId.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                firRenderer.print(strAsString);
            } else {
                FirRenderer firRenderer2 = FirRenderer.this;
                String strAsString2 = resolvedQualifier.getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                firRenderer2.print(StringsKt.replace$default(strAsString2, ".", "/", false, 4, (Object) null));
            }
            if (resolvedQualifier.getIsNullableLHSForCallableReference()) {
                FirRenderer.this.print(TypeDescription.Generic.OfWildcardType.SYMBOL);
            }
            FirRenderer.this.print("|");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression) {
            Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
            Visitor visitor = this;
            binaryLogicExpression.getLeftOperand().accept(visitor);
            FirRenderer.this.print(" " + binaryLogicExpression.getKind().getToken() + StringUtil.SPACE);
            binaryLogicExpression.getRightOperand().accept(visitor);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitEffectDeclaration(FirEffectDeclaration effectDeclaration) {
            Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
            ConeContractRenderer contractRenderer = FirRenderer.this.getContractRenderer();
            if (contractRenderer != null) {
                contractRenderer.render(effectDeclaration);
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitContractDescription(FirContractDescription contractDescription) {
            Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
            if (!(contractDescription instanceof FirEmptyContractDescription)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitPackageDirective(FirPackageDirective packageDirective) {
            Intrinsics.checkNotNullParameter(packageDirective, "packageDirective");
            FirPackageDirectiveRenderer packageDirectiveRenderer = FirRenderer.this.getPackageDirectiveRenderer();
            if (packageDirectiveRenderer != null) {
                packageDirectiveRenderer.render(packageDirective);
            }
        }
    }
}
