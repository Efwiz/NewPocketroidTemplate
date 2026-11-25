package org.jetbrains.kotlin.fir.backend;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
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
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.impl.FirUnitExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: Fir2IrImplicitCastInserter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ä\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 ñ\u00012\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002:\u0002ñ\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J/\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020\\2\u0006\u0010^\u001a\u00020_2\b\u0010`\u001a\u0004\u0018\u00010a2\u0006\u0010b\u001a\u00020cH\u0000¢\u0006\u0002\bdJ\"\u0010e\u001a\u00020\\2\u0006\u0010]\u001a\u00020\\2\u0006\u0010f\u001a\u00020g2\b\b\u0002\u0010b\u001a\u00020cH\u0002J\"\u0010e\u001a\u00020\\2\u0006\u0010]\u001a\u00020\\2\u0006\u0010f\u001a\u00020_2\b\b\u0002\u0010b\u001a\u00020cH\u0002J\u0018\u0010h\u001a\u00020\u00032\u0006\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020\u0003H\u0016J\u0018\u0010l\u001a\u00020\u00032\u0006\u0010m\u001a\u00020n2\u0006\u0010k\u001a\u00020\u0003H\u0016J\u0018\u0010o\u001a\u00020\u00032\u0006\u0010p\u001a\u00020q2\u0006\u0010k\u001a\u00020\u0003H\u0016J\u0018\u0010r\u001a\u00020\u00032\u0006\u0010s\u001a\u00020t2\u0006\u0010k\u001a\u00020\u0003H\u0016J\u0018\u0010u\u001a\u00020\u00032\u0006\u0010v\u001a\u00020w2\u0006\u0010k\u001a\u00020\u0003H\u0016J\u0018\u0010x\u001a\u00020\u00032\u0006\u0010y\u001a\u00020z2\u0006\u0010k\u001a\u00020\u0003H\u0016J\u0018\u0010{\u001a\u00020\u00032\u0006\u0010|\u001a\u00020}2\u0006\u0010k\u001a\u00020\u0003H\u0016J\u0019\u0010~\u001a\u00020\u00032\u0007\u0010\u007f\u001a\u00030\u0080\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u0081\u0001\u001a\u00020\u00032\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u0084\u0001\u001a\u00020\u00032\b\u0010\u0085\u0001\u001a\u00030\u0086\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u0087\u0001\u001a\u00020\u00032\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J)\u0010\u008a\u0001\u001a\u00020\u0003\"\u0005\b\u0000\u0010\u008b\u00012\u000f\u0010\u008c\u0001\u001a\n\u0012\u0005\u0012\u0003H\u008b\u00010\u008d\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u008e\u0001\u001a\u00020\u00032\b\u0010\u008f\u0001\u001a\u00030\u0090\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u0091\u0001\u001a\u00020\u00032\b\u0010\u0092\u0001\u001a\u00030\u0093\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u0094\u0001\u001a\u00020\u00032\b\u0010\u0095\u0001\u001a\u00030\u0096\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u0097\u0001\u001a\u00020\u00032\b\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u009a\u0001\u001a\u00020\u00032\b\u0010\u009b\u0001\u001a\u00030\u009c\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010\u009d\u0001\u001a\u00020\u00032\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010 \u0001\u001a\u00020\u00032\b\u0010¡\u0001\u001a\u00030¢\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010£\u0001\u001a\u00020\u00032\b\u0010¤\u0001\u001a\u00030¥\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010¦\u0001\u001a\u00020\u00032\b\u0010§\u0001\u001a\u00030¨\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010©\u0001\u001a\u00020\u00032\b\u0010ª\u0001\u001a\u00030«\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010¬\u0001\u001a\u00020\u00032\b\u0010\u00ad\u0001\u001a\u00030®\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010¯\u0001\u001a\u00020\u00032\b\u0010°\u0001\u001a\u00030±\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010²\u0001\u001a\u00020\u00032\b\u0010³\u0001\u001a\u00030´\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010µ\u0001\u001a\u00020\u00032\b\u0010¶\u0001\u001a\u00030·\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010¸\u0001\u001a\u00020\u00032\b\u0010¹\u0001\u001a\u00030º\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010»\u0001\u001a\u00020\u00032\b\u0010¼\u0001\u001a\u00030½\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010¾\u0001\u001a\u00020\u00032\b\u0010¿\u0001\u001a\u00030À\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Á\u0001\u001a\u00020\u00032\b\u0010Â\u0001\u001a\u00030Ã\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Ä\u0001\u001a\u00020\u00032\b\u0010Å\u0001\u001a\u00030Æ\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Ç\u0001\u001a\u00020\u00032\b\u0010È\u0001\u001a\u00030É\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Ê\u0001\u001a\u00020\u00032\b\u0010Ë\u0001\u001a\u00030Ì\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Í\u0001\u001a\u00020\u00032\b\u0010Î\u0001\u001a\u00030Ï\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Ð\u0001\u001a\u00020\u00032\b\u0010Ñ\u0001\u001a\u00030Ò\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Ó\u0001\u001a\u00020\u00032\b\u0010Ô\u0001\u001a\u00030Õ\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001c\u0010Ö\u0001\u001a\u00030×\u00012\b\u0010Ø\u0001\u001a\u00030Ù\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Ú\u0001\u001a\u00020\u00032\b\u0010Û\u0001\u001a\u00030Ü\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010Ý\u0001\u001a\u00020\u00032\b\u0010Þ\u0001\u001a\u00030ß\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u001b\u0010à\u0001\u001a\u00020\u00032\b\u0010á\u0001\u001a\u00030â\u00012\u0006\u0010k\u001a\u00020\u0003H\u0016J\u000e\u0010ã\u0001\u001a\u00030ä\u0001*\u00020gH\u0002J/\u0010å\u0001\u001a\u00020\\*\u00020\\2\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0007\u0010æ\u0001\u001a\u00020_2\u0007\u0010ç\u0001\u001a\u00020_H\u0000¢\u0006\u0003\bè\u0001J\u0015\u0010é\u0001\u001a\u00030ê\u0001*\u00030ê\u0001H\u0000¢\u0006\u0003\bë\u0001J\u001b\u0010é\u0001\u001a\u00030ì\u0001*\u00030ì\u00012\n\b\u0002\u0010í\u0001\u001a\u00030ä\u0001H\u0002J\u0017\u0010î\u0001\u001a\u00020\\*\u00020\\2\b\u0010\u009e\u0001\u001a\u00030\u009f\u0001H\u0002J\u0016\u0010ï\u0001\u001a\u00030ð\u0001*\u00020g2\u0006\u0010b\u001a\u00020cH\u0002J\u0016\u0010ï\u0001\u001a\u00030ð\u0001*\u00020_2\u0006\u0010b\u001a\u00020cH\u0002R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001a\u001a\u00020\u001bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0012\u0010\u001e\u001a\u00020\u001fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020#X\u0096\u0005¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X\u0096\u0005¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0012\u0010*\u001a\u00020+X\u0096\u0005¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u00109R\u0018\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;X\u0096\u0005¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0012\u0010?\u001a\u00020@X\u0096\u0005¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0012\u0010C\u001a\u00020DX\u0096\u0005¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0012\u0010G\u001a\u00020HX\u0096\u0005¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0012\u0010K\u001a\u00020LX\u0096\u0005¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0012\u0010O\u001a\u00020PX\u0096\u0005¢\u0006\u0006\u001a\u0004\bQ\u0010RR\u0012\u0010S\u001a\u00020TX\u0096\u0005¢\u0006\u0006\u001a\u0004\bU\u0010VR\u0012\u0010W\u001a\u00020XX\u0096\u0005¢\u0006\u0006\u001a\u0004\bY\u0010Z¨\u0006ò\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrImplicitCastInserter;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "Lorg/jetbrains/kotlin/ir/IrElement;", "components", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "implicitCastFromDispatchReceiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "original", "originalTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "typeOrigin", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "implicitCastFromDispatchReceiver$fir2ir", "implicitCastOrExpression", "castType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "visitAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "visitAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "visitAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "visitArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "visitBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "visitBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "visitCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "visitCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "visitComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "visitConstExpression", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "visitContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "visitDoWhileLoop", "doWhileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "visitEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "visitExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "visitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "visitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "visitLambdaArgumentExpression", "lambdaArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;", "visitNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "visitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "visitResolvedQualifier", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "visitReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "visitSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "visitSpreadArgumentExpression", "spreadArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;", "visitStatement", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "visitStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "visitThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "visitThrowExpression", "throwExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "visitTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "visitTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "visitVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "visitWhenBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "whenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "visitWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "visitWhenSubjectExpression", "whenSubjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "visitWhileLoop", "whileLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "acceptsNullValues", "", "cast", "valueTypeRef", "expectedTypeRef", "cast$fir2ir", "insertImplicitCasts", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "insertImplicitCasts$fir2ir", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "coerceLastExpressionToUnit", "insertImplicitNotNullCastIfNeeded", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Companion", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrImplicitCastInserter extends FirDefaultVisitor<IrElement, IrElement> implements Fir2IrComponents {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Fir2IrComponents components;

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public AnnotationGenerator getAnnotationGenerator() {
        return this.components.getAnnotationGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsFromPluginRegistrar() {
        return this.components.getAnnotationsFromPluginRegistrar();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrBuiltIns getBuiltIns() {
        return this.components.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public CallAndReferenceGenerator getCallGenerator() {
        return this.components.getCallGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrClassifierStorage getClassifierStorage() {
        return this.components.getClassifierStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConfiguration getConfiguration() {
        return this.components.getConfiguration();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConverter getConverter() {
        return this.components.getConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrDeclarationStorage getDeclarationStorage() {
        return this.components.getDeclarationStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public DelegatedMemberGenerator getDelegatedMemberGenerator() {
        return this.components.getDelegatedMemberGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrExtensions getExtensions() {
        return this.components.getExtensions();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FakeOverrideGenerator getFakeOverrideGenerator() {
        return this.components.getFakeOverrideGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrBuiltInsOverFir getIrBuiltIns() {
        return this.components.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrFactory getIrFactory() {
        return this.components.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public List<IrProvider> getIrProviders() {
        return this.components.getIrProviders();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrLock getLock() {
        return this.components.getLock();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public ScopeSession getScopeSession() {
        return this.components.getScopeSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirSession getSession() {
        return this.components.getSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirBasedSignatureComposer getSignatureComposer() {
        return this.components.getSignatureComposer();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public SymbolTable getSymbolTable() {
        return this.components.getSymbolTable();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrTypeConverter getTypeConverter() {
        return this.components.getTypeConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrVisibilityConverter getVisibilityConverter() {
        return this.components.getVisibilityConverter();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnnotation(FirAnnotation annotation, IrElement data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnnotationCall(FirAnnotationCall annotationCall, IrElement data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitArrayLiteral(FirArrayLiteral arrayLiteral, IrElement data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitBreakExpression(FirBreakExpression breakExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, IrElement data) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject, IrElement data) {
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitComparisonExpression(FirComparisonExpression comparisonExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public <T> IrElement visitConstExpression(FirConstExpression<T> constExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitContinueExpression(FirContinueExpression continueExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitElvisExpression(FirElvisExpression elvisExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, IrElement data) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitFunctionCall(FirFunctionCall functionCall, IrElement data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitGetClassCall(FirGetClassCall getClassCall, IrElement data) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitLambdaArgumentExpression(FirLambdaArgumentExpression lambdaArgumentExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitResolvedQualifier(FirResolvedQualifier resolvedQualifier, IrElement data) {
        Intrinsics.checkNotNullParameter(resolvedQualifier, "resolvedQualifier");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitSafeCallExpression(FirSafeCallExpression safeCallExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitSpreadArgumentExpression(FirSpreadArgumentExpression spreadArgumentExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, IrElement data) {
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, IrElement data) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitWhenSubjectExpression(FirWhenSubjectExpression whenSubjectExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(whenSubjectExpression, "whenSubjectExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }

    public Fir2IrImplicitCastInserter(Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
    }

    private final IrType toIrType(FirTypeRef firTypeRef, ConversionTypeOrigin conversionTypeOrigin) {
        return getTypeConverter().toIrType(firTypeRef, conversionTypeOrigin);
    }

    private final IrType toIrType(ConeKotlinType coneKotlinType, ConversionTypeOrigin conversionTypeOrigin) {
        return Fir2IrTypeConverter.toIrType$default(getTypeConverter(), coneKotlinType, conversionTypeOrigin, null, false, false, false, 30, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitElement(FirElement element, IrElement data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        throw new NotImplementedError("An operation is not implemented: " + ("Should not be here: " + Reflection.getOrCreateKotlinClass(element.getClass()) + ": " + UtilsKt.render(element)));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitExpression(FirExpression expression, IrElement data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (expression instanceof FirBlock) {
            return insertImplicitCasts$default(this, (IrContainerExpression) data, false, 1, null);
        }
        return expression instanceof FirUnitExpression ? INSTANCE.coerceToUnitIfNeeded((IrExpression) data, getIrBuiltIns()) : data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitStatement(FirStatement statement, IrElement data) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        Intrinsics.checkNotNullParameter(data, "data");
        if (statement instanceof FirTypeAlias) {
            return data;
        }
        if (statement instanceof FirUnitExpression) {
            return INSTANCE.coerceToUnitIfNeeded((IrExpression) data, getIrBuiltIns());
        }
        return statement instanceof FirBlock ? insertImplicitCasts$default(this, (IrContainerExpression) data, false, 1, null) : (IrElement) statement.accept(this, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitWhenExpression(FirWhenExpression whenExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (data instanceof IrBlock) {
            return insertImplicitCasts$default(this, (IrContainerExpression) data, false, 1, null);
        }
        IrWhen irWhen = (IrWhen) data;
        if (irWhen.getBranches().size() != whenExpression.getBranches().size()) {
            return data;
        }
        final Map map = MapsKt.toMap(CollectionsKt.zip(irWhen.getBranches(), whenExpression.getBranches()));
        irWhen.getBranches().replaceAll(new UnaryOperator() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrImplicitCastInserter$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Fir2IrImplicitCastInserter.visitWhenExpression$lambda$2(this.f$0, map, (IrBranch) obj);
            }
        });
        return data;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrBranch visitWhenExpression$lambda$2(Fir2IrImplicitCastInserter this$0, Map firBranchMap, IrBranch it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(firBranchMap, "$firBranchMap");
        Intrinsics.checkNotNullParameter(it, "it");
        return this$0.visitWhenBranch((FirWhenBranch) MapsKt.getValue(firBranchMap, it), (IrElement) it);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrBranch visitWhenBranch(FirWhenBranch whenBranch, IrElement data) {
        Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
        Intrinsics.checkNotNullParameter(data, "data");
        IrBranch irBranch = (IrBranch) data;
        IrExpression result = irBranch.getResult();
        IrContainerExpression irContainerExpression = result instanceof IrContainerExpression ? (IrContainerExpression) result : null;
        if (irContainerExpression != null) {
            irBranch.setResult(insertImplicitCasts$default(this, irContainerExpression, false, 1, null));
        }
        return irBranch;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitDoWhileLoop(FirDoWhileLoop doWhileLoop, IrElement data) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        IrDoWhileLoop irDoWhileLoop = (IrDoWhileLoop) data;
        IrExpression body = irDoWhileLoop.getBody();
        IrContainerExpression irContainerExpression = body instanceof IrContainerExpression ? (IrContainerExpression) body : null;
        if (irContainerExpression != null) {
            irDoWhileLoop.setBody(insertImplicitCasts$default(this, irContainerExpression, false, 1, null));
        }
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitWhileLoop(FirWhileLoop whileLoop, IrElement data) {
        Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        IrWhileLoop irWhileLoop = (IrWhileLoop) data;
        IrExpression body = irWhileLoop.getBody();
        IrContainerExpression irContainerExpression = body instanceof IrContainerExpression ? (IrContainerExpression) body : null;
        if (irContainerExpression != null) {
            irWhileLoop.setBody(insertImplicitCasts$default(this, irContainerExpression, false, 1, null));
        }
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitTryExpression(FirTryExpression tryExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrTry irTry = (IrTry) data;
        IrExpression finallyExpression = irTry.getFinallyExpression();
        IrContainerExpression irContainerExpression = finallyExpression instanceof IrContainerExpression ? (IrContainerExpression) finallyExpression : null;
        if (irContainerExpression != null) {
            irTry.setFinallyExpression(insertImplicitCasts(irContainerExpression, true));
        }
        return data;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitThrowExpression(FirThrowExpression throwExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return cast$fir2ir((IrThrow) data, throwExpression, throwExpression.getException().getTypeRef(), throwExpression.getTypeRef());
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitBlock(FirBlock block, IrElement data) {
        IrContainerExpression irContainerExpressionInsertImplicitCasts$default;
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(data, "data");
        IrContainerExpression irContainerExpression = data instanceof IrContainerExpression ? (IrContainerExpression) data : null;
        return (irContainerExpression == null || (irContainerExpressionInsertImplicitCasts$default = insertImplicitCasts$default(this, irContainerExpression, false, 1, null)) == null) ? data : irContainerExpressionInsertImplicitCasts$default;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitReturnExpression(FirReturnExpression returnExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrReturn irReturn = (IrReturn) data;
        irReturn.setValue(cast$fir2ir(irReturn.getValue(), returnExpression.getResult(), returnExpression.getResult().getTypeRef(), ((FirFunction) returnExpression.getTarget().getLabeledElement()).getReturnTypeRef()));
        return data;
    }

    public final IrExpression cast$fir2ir(IrExpression irExpression, FirExpression expression, FirTypeRef valueTypeRef, FirTypeRef expectedTypeRef) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(valueTypeRef, "valueTypeRef");
        Intrinsics.checkNotNullParameter(expectedTypeRef, "expectedTypeRef");
        if (irExpression instanceof IrTypeOperatorCall) {
            return irExpression;
        }
        if (irExpression instanceof IrContainerExpression) {
            insertImplicitCasts$default(this, (IrContainerExpression) irExpression, false, 1, null);
        }
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(valueTypeRef), getSession());
        ConeKotlinType coneKotlinTypeFullyExpandedType2 = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(expectedTypeRef), getSession());
        if (!ConeBuiltinTypeUtilsKt.isUnit(coneKotlinTypeFullyExpandedType2)) {
            if (coneKotlinTypeFullyExpandedType instanceof ConeDynamicType) {
                return ((coneKotlinTypeFullyExpandedType2 instanceof ConeDynamicType) || ConeBuiltinTypeUtilsKt.isNullableAny(coneKotlinTypeFullyExpandedType2)) ? irExpression : INSTANCE.implicitCast(irExpression, toIrType(coneKotlinTypeFullyExpandedType2, ConversionTypeOrigin.DEFAULT));
            }
            return (!INSTANCE.typeCanBeEnhancedOrFlexibleNullable$fir2ir(coneKotlinTypeFullyExpandedType) || acceptsNullValues(coneKotlinTypeFullyExpandedType2)) ? irExpression : insertImplicitNotNullCastIfNeeded(irExpression, expression);
        }
        return INSTANCE.coerceToUnitIfNeeded(irExpression, getIrBuiltIns());
    }

    private final boolean acceptsNullValues(ConeKotlinType coneKotlinType) {
        return FirTypeUtilsKt.getCanBeNull(coneKotlinType) || CompilerConeAttributesKt.getHasEnhancedNullability(coneKotlinType);
    }

    private final IrExpression insertImplicitNotNullCastIfNeeded(IrExpression irExpression, FirExpression firExpression) {
        return ((irExpression instanceof IrGetEnumValue) || firExpression.getSource() == null) ? irExpression : INSTANCE.implicitNotNullCast$fir2ir(irExpression);
    }

    static /* synthetic */ IrContainerExpression insertImplicitCasts$default(Fir2IrImplicitCastInserter fir2IrImplicitCastInserter, IrContainerExpression irContainerExpression, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return fir2IrImplicitCastInserter.insertImplicitCasts(irContainerExpression, z);
    }

    private final IrContainerExpression insertImplicitCasts(IrContainerExpression irContainerExpression, boolean z) {
        if (irContainerExpression.getStatements().isEmpty()) {
            return irContainerExpression;
        }
        int lastIndex = CollectionsKt.getLastIndex(irContainerExpression.getStatements());
        int i = 0;
        for (Object obj : irContainerExpression.getStatements()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrStatement irStatement = (IrStatement) obj;
            if (!(irStatement instanceof IrErrorCallExpression) && (irStatement instanceof IrExpression) && (i != lastIndex || z)) {
                irContainerExpression.getStatements().set(i, INSTANCE.coerceToUnitIfNeeded((IrExpression) irStatement, getIrBuiltIns()));
            }
            i = i2;
        }
        return irContainerExpression;
    }

    public final IrBlockBody insertImplicitCasts$fir2ir(IrBlockBody irBlockBody) {
        Intrinsics.checkNotNullParameter(irBlockBody, "<this>");
        if (irBlockBody.getStatements().isEmpty()) {
            return irBlockBody;
        }
        int i = 0;
        for (Object obj : irBlockBody.getStatements()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrStatement irStatement = (IrStatement) obj;
            if (!(irStatement instanceof IrErrorCallExpression) && (irStatement instanceof IrExpression)) {
                irBlockBody.getStatements().set(i, INSTANCE.coerceToUnitIfNeeded((IrExpression) irStatement, getIrBuiltIns()));
            }
            i = i2;
        }
        return irBlockBody;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public IrElement visitSmartCastExpression(FirSmartCastExpression smartCastExpression, IrElement data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (smartCastExpression.isStable() && smartCastExpression.getSmartcastTypeWithoutNullableNothing() == null) {
            return implicitCastOrExpression$default(this, (IrExpression) data, smartCastExpression.getTypeRef(), null, 4, null);
        }
        return (IrExpression) data;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrExpression implicitCastFromDispatchReceiver$fir2ir(IrExpression original, FirTypeRef originalTypeRef, FirReference calleeReference, ConversionTypeOrigin typeOrigin) {
        Collection<ConeKotlinType> intersectedTypes;
        FirCallableSymbol resolvedCallableSymbol$default;
        FirCallableSymbol firCallableSymbolUnwrapCallRepresentative$default;
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(originalTypeRef, "originalTypeRef");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        FirCallableDeclaration firCallableDeclaration = (calleeReference == null || (resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null)) == null || (firCallableSymbolUnwrapCallRepresentative$default = ConversionUtilsKt.unwrapCallRepresentative$default(this, resolvedCallableSymbol$default, null, 2, null)) == null) ? null : (FirCallableDeclaration) firCallableSymbolUnwrapCallRepresentative$default.getFir();
        ConeSimpleKotlinType dispatchReceiverType = firCallableDeclaration != null ? firCallableDeclaration.getDispatchReceiverType() : null;
        ConeClassLikeType coneClassLikeType = dispatchReceiverType instanceof ConeClassLikeType ? (ConeClassLikeType) dispatchReceiverType : null;
        if (coneClassLikeType == null) {
            return implicitCastOrExpression$default(this, original, originalTypeRef, null, 4, null);
        }
        ConeClassLikeType coneClassLikeTypeReplaceArgumentsWithStarProjections = ConeTypeUtilsKt.replaceArgumentsWithStarProjections(coneClassLikeType);
        FirResolvedTypeRef firResolvedTypeRef = originalTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) originalTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeIntersectionType coneIntersectionType = (ConeIntersectionType) (type instanceof ConeIntersectionType ? type : null);
        if (coneIntersectionType != null && (intersectedTypes = coneIntersectionType.getIntersectedTypes()) != null) {
            for (ConeKotlinType coneKotlinType : intersectedTypes) {
                if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(getSession()), (KotlinTypeMarker) coneKotlinType, (KotlinTypeMarker) coneClassLikeTypeReplaceArgumentsWithStarProjections, false, 8, (Object) null)) {
                    return implicitCastOrExpression(original, coneKotlinType, typeOrigin);
                }
            }
        }
        return implicitCastOrExpression(original, originalTypeRef, typeOrigin);
    }

    private final IrExpression implicitCastOrExpression(IrExpression original, ConeKotlinType castType, ConversionTypeOrigin typeOrigin) {
        return INSTANCE.implicitCastOrExpression$fir2ir(original, toIrType(castType, typeOrigin));
    }

    static /* synthetic */ IrExpression implicitCastOrExpression$default(Fir2IrImplicitCastInserter fir2IrImplicitCastInserter, IrExpression irExpression, FirTypeRef firTypeRef, ConversionTypeOrigin conversionTypeOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return fir2IrImplicitCastInserter.implicitCastOrExpression(irExpression, firTypeRef, conversionTypeOrigin);
    }

    private final IrExpression implicitCastOrExpression(IrExpression original, FirTypeRef castType, ConversionTypeOrigin typeOrigin) {
        return INSTANCE.implicitCastOrExpression$fir2ir(original, toIrType(castType, typeOrigin));
    }

    /* JADX INFO: compiled from: Fir2IrImplicitCastInserter.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001d\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u0011J\u0015\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\tH\u0000¢\u0006\u0002\b\u0014J\u0015\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0017R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrImplicitCastInserter$Companion;", "", "()V", "hasFlexibleNullability", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getHasFlexibleNullability", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "coerceToUnitIfNeeded", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "original", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "implicitCast", "castType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "implicitCastOrExpression", "implicitCastOrExpression$fir2ir", "implicitNotNullCast", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "implicitNotNullCast$fir2ir", "typeCanBeEnhancedOrFlexibleNullable", ModuleXmlParser.TYPE, "typeCanBeEnhancedOrFlexibleNullable$fir2ir", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IrExpression implicitCastOrExpression$fir2ir(IrExpression original, IrType castType) {
            Intrinsics.checkNotNullParameter(original, "original");
            Intrinsics.checkNotNullParameter(castType, "castType");
            return Intrinsics.areEqual(IrTypesKt.makeNotNull(original.getType()), IrTypesKt.makeNotNull(castType)) ? original : implicitCast(original, castType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrExpression implicitCast(IrExpression original, IrType castType) {
            IrTypeOperator irTypeOperator;
            if (original.getType() instanceof IrDynamicType) {
                irTypeOperator = IrTypeOperator.IMPLICIT_DYNAMIC_CAST;
            } else {
                irTypeOperator = IrTypeOperator.IMPLICIT_CAST;
            }
            return new IrTypeOperatorCallImpl(original.getStartOffset(), original.getEndOffset(), castType, irTypeOperator, castType, original);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrExpression coerceToUnitIfNeeded(IrExpression original, IrBuiltIns irBuiltIns) {
            IrType type = original.getType();
            return (IrTypePredicatesKt.isUnit(type) || IrTypePredicatesKt.isNothing(type)) ? original : new IrTypeOperatorCallImpl(original.getStartOffset(), original.getEndOffset(), irBuiltIns.getUnitType(), IrTypeOperator.IMPLICIT_COERCION_TO_UNIT, irBuiltIns.getUnitType(), original);
        }

        public final IrTypeOperatorCall implicitNotNullCast$fir2ir(IrExpression original) {
            Intrinsics.checkNotNullParameter(original, "original");
            IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(IrTypesKt.removeAnnotations(original.getType(), new Function1<IrConstructorCall, Boolean>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrImplicitCastInserter$Companion$implicitNotNullCast$castType$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrConstructorCall it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    ClassId classId = AdditionalIrUtilsKt.getClassId(IrUtilsKt.getParentAsClass(it.getSymbol().getOwner()));
                    return Boolean.valueOf(Intrinsics.areEqual(classId, StandardClassIds.Annotations.INSTANCE.getEnhancedNullability()) || Intrinsics.areEqual(classId, StandardClassIds.Annotations.INSTANCE.getFlexibleNullability()));
                }
            }));
            return new IrTypeOperatorCallImpl(original.getStartOffset(), original.getEndOffset(), irTypeMakeNotNull, IrTypeOperator.IMPLICIT_NOTNULL, irTypeMakeNotNull, original);
        }

        public final boolean typeCanBeEnhancedOrFlexibleNullable$fir2ir(ConeKotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (CompilerConeAttributesKt.getHasEnhancedNullability(type)) {
                return true;
            }
            return getHasFlexibleNullability(type) && FirTypeUtilsKt.getCanBeNull(type);
        }

        private final boolean getHasFlexibleNullability(ConeKotlinType coneKotlinType) {
            if (!(coneKotlinType instanceof ConeFlexibleType)) {
                return false;
            }
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            return ConeTypeUtilsKt.isMarkedNullable(coneFlexibleType.getLowerBound()) != ConeTypeUtilsKt.isMarkedNullable(coneFlexibleType.getUpperBound());
        }
    }
}
