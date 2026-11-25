package org.jetbrains.kotlin.fir.analysis.collectors;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirTargetElement;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineDeclarationCheckerKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceivers;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorLoop;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirLoopJump;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.impl.FirContractCallBlock;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: AbstractDiagnosticCollectorVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ä\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000fH$J\b\u0010\u0010\u001a\u00020\u0002H\u0016J\"\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0082\b¢\u0006\u0002\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\"\u0010\u001b\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0082\b¢\u0006\u0002\u0010\u0015J\u001a\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010 \u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020#2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010$\u001a\u00020\u00022\u0006\u0010%\u001a\u00020&2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010'\u001a\u00020\u00022\u0006\u0010(\u001a\u00020)2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010*\u001a\u00020\u00022\u0006\u0010+\u001a\u00020,2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010-\u001a\u00020\u00022\u0006\u0010.\u001a\u00020/2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00100\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u0002012\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00102\u001a\u00020\u00022\u0006\u00103\u001a\u0002042\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u0018\u00105\u001a\u00020\u00022\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u000209H\u0002J\u001a\u0010:\u001a\u00020\u00022\u0006\u0010;\u001a\u00020<2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010=\u001a\u00020\u00022\u0006\u0010>\u001a\u00020?2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010@\u001a\u00020\u00022\u0006\u0010A\u001a\u00020B2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010C\u001a\u00020\u00022\u0006\u0010D\u001a\u00020E2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010F\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010G\u001a\u00020\u00022\u0006\u0010H\u001a\u00020I2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020L2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010M\u001a\u00020\u00022\u0006\u0010N\u001a\u00020O2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010P\u001a\u00020\u00022\u0006\u0010Q\u001a\u00020R2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010S\u001a\u00020\u00022\u0006\u0010T\u001a\u00020U2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010V\u001a\u00020\u00022\u0006\u0010W\u001a\u00020XH\u0002J\u0010\u0010Y\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\u001a\u0010Z\u001a\u00020\u00022\u0006\u0010[\u001a\u00020\\2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010]\u001a\u00020\u00022\u0006\u0010^\u001a\u00020_2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010`\u001a\u00020\u00022\u0006\u0010a\u001a\u00020b2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010c\u001a\u00020\u00022\u0006\u0010d\u001a\u00020e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010f\u001a\u00020\u00022\u0006\u0010g\u001a\u00020h2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010i\u001a\u00020\u00022\u0006\u0010j\u001a\u00020k2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010l\u001a\u00020\u00022\u0006\u0010m\u001a\u00020n2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010o\u001a\u00020\u00022\u0006\u0010p\u001a\u00020q2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010r\u001a\u00020\u00022\u0006\u0010s\u001a\u00020t2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010u\u001a\u00020\u00022\u0006\u0010v\u001a\u00020w2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010x\u001a\u00020\u00022\u0006\u0010y\u001a\u00020z2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010{\u001a\u00020\u00022\u0006\u0010|\u001a\u00020}2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001b\u0010~\u001a\u00020\u00022\u0007\u0010\u007f\u001a\u00030\u0080\u00012\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0016J\u0013\u0010\u0081\u0001\u001a\u00020\u00022\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0002J%\u0010\u0084\u0001\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014H\u0084\bø\u0001\u0000J'\u0010\u0085\u0001\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\n\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0087\u00012\b\u0010g\u001a\u0004\u0018\u00010hH\u0002J%\u0010\u0088\u0001\u001a\u00020\u00022\u0006\u0010N\u001a\u00020O2\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014H\u0084\bø\u0001\u0000J\u0011\u0010\u0089\u0001\u001a\u00020\u00022\u0006\u0010T\u001a\u00020UH\u0002J/\u0010\u008a\u0001\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008b\u0001J1\u0010\u008c\u0001\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008d\u0001J/\u0010\u008e\u0001\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008f\u0001J2\u0010\u0090\u0001\u001a\u0003H\u0091\u0001\"\u0005\b\u0000\u0010\u0091\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\r\u0010\u0013\u001a\t\u0012\u0005\u0012\u0003H\u0091\u00010\u0014H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0092\u0001J/\u0010\u0093\u0001\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u0006\u0010N\u001a\u00020O2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0094\u0001J/\u0010\u0095\u0001\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u0006\u0010T\u001a\u00020U2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0096\u0001J:\u0010\u0097\u0001\u001a\u0003H\u0091\u0001\"\u0005\b\u0000\u0010\u0091\u00012\b\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u0007\u0010\u009a\u0001\u001a\u00020\u001a2\r\u0010\u0013\u001a\t\u0012\u0005\u0012\u0003H\u0091\u00010\u0014H\u0082\b¢\u0006\u0003\u0010\u009b\u0001JG\u0010\u009c\u0001\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\n\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0087\u00012\u0007\u0010\u009d\u0001\u001a\u00020\u00182\t\u00108\u001a\u0005\u0018\u00010\u009e\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u009f\u0001J,\u0010 \u0001\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u000b\u001a\u00020z2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0082\b¢\u0006\u0003\u0010¡\u0001R\u001c\u0010\u0004\u001a\u00020\u0005@\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006¢\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/AbstractDiagnosticCollectorVisitor;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContextForProvider;", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContextForProvider;)V", "getContext", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContextForProvider;", "setContext", "addSuppressedDiagnosticsToContext", "annotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "checkElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "checkSettings", "insideContractBody", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "onDeclarationExit", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "shouldVisitDeclaration", "", "suppressInlineFunctionBodyContext", "visitAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "data", "visitAnnotationContainer", "visitAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "visitAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "visitAnonymousInitializer", "anonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "visitAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "visitBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "visitBreakExpression", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "visitClassAndChildren", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "visitContractDescription", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "visitDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "visitElement", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "visitErrorTypeRef", "errorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "visitFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "visitFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "visitGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "visitJump", "loopJump", "Lorg/jetbrains/kotlin/fir/expressions/FirLoopJump;", "visitNestedElements", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "visitPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "visitQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "visitReceiverParameter", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "visitTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visitValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "visitVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "visitWithCallOrAssignment", "callOrAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "visitWithDeclaration", "visitWithDeclarationAndReceiver", "labelName", "Lorg/jetbrains/kotlin/name/Name;", "visitWithFile", "visitWithGetClassCall", "withAnnotationContainer", "(Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withCallOrAssignment", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withDeclaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withElement", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/fir/FirElement;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withFile", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withGetClassCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withInlineFunctionBodyIfApplicable", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "isInline", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;ZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLabelAndReceiverType", "owner", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withTypeRefAnnotationContainer", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDiagnosticCollectorVisitor extends FirDefaultVisitor {
    private CheckerContextForProvider context;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void checkElement(FirElement element);

    public void checkSettings() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDeclarationExit(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean shouldVisitDeclaration(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnnotationCall(FirAnnotationCall firAnnotationCall, Object obj) {
        visitAnnotationCall(firAnnotationCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnnotationContainer(FirAnnotationContainer firAnnotationContainer, Object obj) {
        visitAnnotationContainer(firAnnotationContainer, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousFunction(FirAnonymousFunction firAnonymousFunction, Object obj) {
        visitAnonymousFunction(firAnonymousFunction, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousFunctionExpression(FirAnonymousFunctionExpression firAnonymousFunctionExpression, Object obj) {
        visitAnonymousFunctionExpression(firAnonymousFunctionExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousInitializer(FirAnonymousInitializer firAnonymousInitializer, Object obj) {
        visitAnonymousInitializer(firAnonymousInitializer, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousObject(FirAnonymousObject firAnonymousObject, Object obj) {
        visitAnonymousObject(firAnonymousObject, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousObjectExpression(FirAnonymousObjectExpression firAnonymousObjectExpression, Object obj) {
        visitAnonymousObjectExpression(firAnonymousObjectExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitBlock(FirBlock firBlock, Object obj) {
        visitBlock(firBlock, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitBreakExpression(FirBreakExpression firBreakExpression, Object obj) {
        visitBreakExpression(firBreakExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitConstructor(FirConstructor firConstructor, Object obj) {
        visitConstructor(firConstructor, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitContinueExpression(FirContinueExpression firContinueExpression, Object obj) {
        visitContinueExpression(firContinueExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitContractDescription(FirContractDescription firContractDescription, Object obj) {
        visitContractDescription(firContractDescription, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitDelegatedConstructorCall(FirDelegatedConstructorCall firDelegatedConstructorCall, Object obj) {
        visitDelegatedConstructorCall(firDelegatedConstructorCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitElement(FirElement firElement, Object obj) {
        visitElement(firElement, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitEnumEntry(FirEnumEntry firEnumEntry, Object obj) {
        visitEnumEntry(firEnumEntry, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitErrorTypeRef(FirErrorTypeRef firErrorTypeRef, Object obj) {
        visitErrorTypeRef(firErrorTypeRef, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitFile(FirFile firFile, Object obj) {
        visitFile(firFile, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitFunctionCall(FirFunctionCall firFunctionCall, Object obj) {
        visitFunctionCall(firFunctionCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitGetClassCall(FirGetClassCall firGetClassCall, Object obj) {
        visitGetClassCall(firGetClassCall, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitProperty(FirProperty firProperty, Object obj) {
        visitProperty(firProperty, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitPropertyAccessExpression(FirPropertyAccessExpression firPropertyAccessExpression, Object obj) {
        visitPropertyAccessExpression(firPropertyAccessExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitPropertyAccessor(FirPropertyAccessor firPropertyAccessor, Object obj) {
        visitPropertyAccessor(firPropertyAccessor, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitQualifiedAccessExpression(FirQualifiedAccessExpression firQualifiedAccessExpression, Object obj) {
        visitQualifiedAccessExpression(firQualifiedAccessExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitReceiverParameter(FirReceiverParameter firReceiverParameter, Object obj) {
        visitReceiverParameter(firReceiverParameter, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitRegularClass(FirRegularClass firRegularClass, Object obj) {
        visitRegularClass(firRegularClass, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitResolvedTypeRef(FirResolvedTypeRef firResolvedTypeRef, Object obj) {
        visitResolvedTypeRef(firResolvedTypeRef, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitScript(FirScript firScript, Object obj) {
        visitScript(firScript, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitSimpleFunction(FirSimpleFunction firSimpleFunction, Object obj) {
        visitSimpleFunction(firSimpleFunction, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitTypeAlias(FirTypeAlias firTypeAlias, Object obj) {
        visitTypeAlias(firTypeAlias, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitTypeRef(FirTypeRef firTypeRef, Object obj) {
        visitTypeRef(firTypeRef, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitValueParameter(FirValueParameter firValueParameter, Object obj) {
        visitValueParameter(firValueParameter, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitVariableAssignment(FirVariableAssignment firVariableAssignment, Object obj) {
        visitVariableAssignment(firVariableAssignment, (Void) obj);
        return Unit.INSTANCE;
    }

    public final CheckerContextForProvider getContext() {
        return this.context;
    }

    @PrivateForInline
    public final void setContext(CheckerContextForProvider checkerContextForProvider) {
        Intrinsics.checkNotNullParameter(checkerContextForProvider, "<set-?>");
        this.context = checkerContextForProvider;
    }

    public AbstractDiagnosticCollectorVisitor(CheckerContextForProvider context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    protected void visitNestedElements(FirElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.acceptChildren(this, null);
    }

    public void visitElement(FirElement element, Void data) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element instanceof FirAnnotationContainer) {
            FirAnnotationContainer firAnnotationContainer = (FirAnnotationContainer) element;
            CheckerContextForProvider context = getContext();
            setContext(getContext().addElement(firAnnotationContainer));
            try {
                FirSession session = getContext().getSession();
                try {
                    context = getContext();
                    addSuppressedDiagnosticsToContext(firAnnotationContainer);
                    boolean zIsEmpty = firAnnotationContainer.getAnnotations().isEmpty();
                    if (!zIsEmpty) {
                        setContext(getContext().addAnnotationContainer(firAnnotationContainer));
                    }
                    try {
                        checkElement(element);
                        visitNestedElements(element);
                        Unit unit = Unit.INSTANCE;
                        if (!zIsEmpty) {
                            context.dropAnnotationContainer();
                        }
                        setContext(context);
                        context.dropElement();
                        return;
                    } finally {
                        if (!zIsEmpty) {
                            context.dropAnnotationContainer();
                        }
                        setContext(context);
                    }
                } catch (Throwable th) {
                    UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(firAnnotationContainer, th);
                    throw null;
                }
            } catch (Throwable th2) {
                context.dropElement();
                setContext(context);
                throw th2;
            }
        }
        CheckerContextForProvider context2 = getContext();
        setContext(getContext().addElement(element));
        try {
            FirSession session2 = getContext().getSession();
            try {
                checkElement(element);
                visitNestedElements(element);
                Unit unit2 = Unit.INSTANCE;
                context2.dropElement();
                setContext(context2);
            } catch (Throwable th3) {
                UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(element, th3);
                throw null;
            }
        } catch (Throwable th4) {
            context2.dropElement();
            setContext(context2);
            throw th4;
        }
    }

    public void visitBreakExpression(FirBreakExpression breakExpression, Void data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        visitJump(breakExpression);
    }

    public void visitContinueExpression(FirContinueExpression continueExpression, Void data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        visitJump(continueExpression);
    }

    public void visitAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, Void data) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        anonymousObjectExpression.getAnonymousObject().accept(this, data);
    }

    public void visitAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, Void data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        visitAnonymousFunction(anonymousFunctionExpression.getAnonymousFunction(), data);
    }

    public void visitPropertyAccessor(FirPropertyAccessor propertyAccessor, Void data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        Object objLast = CollectionsKt.last((List<? extends Object>) this.context.getContainingDeclarations());
        Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirProperty");
        FirProperty firProperty = (FirProperty) objLast;
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(propertyAccessor));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(propertyAccessor);
                boolean zIsEmpty = propertyAccessor.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(propertyAccessor));
                }
                try {
                    boolean z = propertyAccessor.getStatus().isInline() || firProperty.getStatus().isInline();
                    if (z) {
                        try {
                            CheckerContextForProvider checkerContextForProvider = this.context;
                            this.context = checkerContextForProvider.setInlineFunctionBodyContext(FirInlineDeclarationCheckerKt.createInlineFunctionBodyContext(propertyAccessor, checkerContextForProvider.getSession()));
                        } catch (Throwable th) {
                            if (z) {
                                this.context = this.context.unsetInlineFunctionBodyContext();
                            }
                            throw th;
                        }
                    }
                    visitWithDeclarationAndReceiver(propertyAccessor, firProperty.getName(), firProperty.getReceiverParameter());
                    Unit unit = Unit.INSTANCE;
                    if (z) {
                        this.context = this.context.unsetInlineFunctionBodyContext();
                    }
                    Unit unit2 = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    context.dropElement();
                    setContext(context);
                } catch (Throwable th2) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th2;
                }
            } catch (Throwable th3) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(propertyAccessor, th3);
                throw null;
            }
        } catch (Throwable th4) {
            context.dropElement();
            setContext(context);
            throw th4;
        }
    }

    public void visitBlock(FirBlock block, Void data) {
        Intrinsics.checkNotNullParameter(block, "block");
        if (!(block instanceof FirContractCallBlock)) {
            visitExpression(block, data);
            return;
        }
        this.context = this.context.enterContractBody();
        try {
            visitExpression(block, data);
            Unit unit = Unit.INSTANCE;
        } finally {
            this.context = this.context.exitContractBody();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x002a, code lost:
    
        r8 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void visitTypeRef(org.jetbrains.kotlin.fir.types.FirTypeRef r7, java.lang.Void r8) {
        /*
            Method dump skipped, instruction units count: 205
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollectorVisitor.visitTypeRef(org.jetbrains.kotlin.fir.types.FirTypeRef, java.lang.Void):void");
    }

    public void visitErrorTypeRef(FirErrorTypeRef errorTypeRef, Void data) {
        Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
        visitResolvedTypeRef((FirResolvedTypeRef) errorTypeRef, data);
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x0038, code lost:
    
        r2 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void visitResolvedTypeRef(org.jetbrains.kotlin.fir.types.FirResolvedTypeRef r9, java.lang.Void r10) {
        /*
            Method dump skipped, instruction units count: 253
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollectorVisitor.visitResolvedTypeRef(org.jetbrains.kotlin.fir.types.FirResolvedTypeRef, java.lang.Void):void");
    }

    public void visitFunctionCall(FirFunctionCall functionCall, Void data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        visitWithCallOrAssignment(functionCall);
    }

    public void visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, Void data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        visitWithCallOrAssignment(qualifiedAccessExpression);
    }

    public void visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, Void data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        visitWithCallOrAssignment(propertyAccessExpression);
    }

    public void visitAnnotationCall(FirAnnotationCall annotationCall, Void data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        visitWithCallOrAssignment(annotationCall);
    }

    public void visitVariableAssignment(FirVariableAssignment variableAssignment, Void data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        visitWithCallOrAssignment(variableAssignment);
    }

    public void visitDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, Void data) {
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        visitWithCallOrAssignment(delegatedConstructorCall);
    }

    public void visitGetClassCall(FirGetClassCall getClassCall, Void data) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        visitWithGetClassCall(getClassCall);
    }

    public static /* synthetic */ void visitWithDeclaration$default(AbstractDiagnosticCollectorVisitor abstractDiagnosticCollectorVisitor, final FirDeclaration declaration, Function0 block, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                block = new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollectorVisitor.visitWithDeclaration.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        AbstractDiagnosticCollectorVisitor.this.visitNestedElements(declaration);
                    }
                };
            }
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(block, "block");
            if (abstractDiagnosticCollectorVisitor.shouldVisitDeclaration(declaration)) {
                abstractDiagnosticCollectorVisitor.checkElement(declaration);
                CheckerContextForProvider context = abstractDiagnosticCollectorVisitor.getContext();
                abstractDiagnosticCollectorVisitor.setContext(abstractDiagnosticCollectorVisitor.getContext().addDeclaration(declaration));
                try {
                    FirSession session = abstractDiagnosticCollectorVisitor.getContext().getSession();
                    try {
                        block.invoke();
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        context.dropDeclaration();
                        abstractDiagnosticCollectorVisitor.setContext(context);
                        InlineMarker.finallyEnd(1);
                        abstractDiagnosticCollectorVisitor.onDeclarationExit(declaration);
                        return;
                    } catch (Throwable th) {
                        UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(declaration, th);
                        throw null;
                    }
                } catch (Throwable th2) {
                    InlineMarker.finallyStart(1);
                    context.dropDeclaration();
                    abstractDiagnosticCollectorVisitor.setContext(context);
                    InlineMarker.finallyEnd(1);
                    throw th2;
                }
            }
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: visitWithDeclaration");
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x004f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final void visitWithDeclaration(org.jetbrains.kotlin.fir.declarations.FirDeclaration r4, kotlin.jvm.functions.Function0<kotlin.Unit> r5) {
        /*
            r3 = this;
            java.lang.String r0 = "declaration"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "block"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            boolean r0 = access$shouldVisitDeclaration(r3, r4)
            if (r0 == 0) goto L5d
            r0 = r4
            org.jetbrains.kotlin.fir.FirElement r0 = (org.jetbrains.kotlin.fir.FirElement) r0
            access$checkElement(r3, r0)
            org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider r0 = r3.getContext()
            org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider r1 = r3.getContext()
            org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider r1 = r1.addDeclaration(r4)
            r3.setContext(r1)
            r1 = 1
            org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider r2 = r3.getContext()     // Catch: java.lang.Throwable -> L4f
            org.jetbrains.kotlin.fir.FirSession r2 = r2.getSession()     // Catch: java.lang.Throwable -> L4f
            r5.invoke()     // Catch: java.lang.Throwable -> L43
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L43
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            r0.dropDeclaration()
            r3.setContext(r0)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            access$onDeclarationExit(r3, r4)
            goto L5d
        L43:
            r5 = move-exception
            org.jetbrains.kotlin.fir.FirExceptionHandler r2 = org.jetbrains.kotlin.fir.UtilsKt.getExceptionHandler(r2)     // Catch: java.lang.Throwable -> L4f
            org.jetbrains.kotlin.fir.FirElement r4 = (org.jetbrains.kotlin.fir.FirElement) r4     // Catch: java.lang.Throwable -> L4f
            r2.handleExceptionOnElementAnalysis(r4, r5)     // Catch: java.lang.Throwable -> L4f
            r4 = 0
            throw r4     // Catch: java.lang.Throwable -> L4f
        L4f:
            r4 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            r0.dropDeclaration()
            r3.setContext(r0)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            throw r4
        L5d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollectorVisitor.visitWithDeclaration(org.jetbrains.kotlin.fir.declarations.FirDeclaration, kotlin.jvm.functions.Function0):void");
    }

    public static /* synthetic */ void visitWithFile$default(AbstractDiagnosticCollectorVisitor abstractDiagnosticCollectorVisitor, final FirFile file, Function0 block, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                block = new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollectorVisitor.visitWithFile.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        AbstractDiagnosticCollectorVisitor.this.visitNestedElements(file);
                    }
                };
            }
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(block, "block");
            CheckerContextForProvider context = abstractDiagnosticCollectorVisitor.getContext();
            abstractDiagnosticCollectorVisitor.setContext(abstractDiagnosticCollectorVisitor.getContext().enterFile(file));
            try {
                if (abstractDiagnosticCollectorVisitor.shouldVisitDeclaration(file)) {
                    abstractDiagnosticCollectorVisitor.checkElement(file);
                    CheckerContextForProvider context2 = abstractDiagnosticCollectorVisitor.getContext();
                    abstractDiagnosticCollectorVisitor.setContext(abstractDiagnosticCollectorVisitor.getContext().addDeclaration(file));
                    try {
                        FirSession session = abstractDiagnosticCollectorVisitor.getContext().getSession();
                        try {
                            block.invoke();
                            Unit unit = Unit.INSTANCE;
                            InlineMarker.finallyStart(1);
                            context2.dropDeclaration();
                            abstractDiagnosticCollectorVisitor.setContext(context2);
                            InlineMarker.finallyEnd(1);
                            abstractDiagnosticCollectorVisitor.onDeclarationExit(file);
                        } catch (Throwable th) {
                            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(file, th);
                            throw null;
                        }
                    } catch (Throwable th2) {
                        InlineMarker.finallyStart(1);
                        context2.dropDeclaration();
                        abstractDiagnosticCollectorVisitor.setContext(context2);
                        InlineMarker.finallyEnd(1);
                        throw th2;
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                InlineMarker.finallyStart(1);
                context.exitFile(file);
                abstractDiagnosticCollectorVisitor.setContext(context);
                InlineMarker.finallyEnd(1);
                return;
            } catch (Throwable th3) {
                InlineMarker.finallyStart(1);
                context.exitFile(file);
                abstractDiagnosticCollectorVisitor.setContext(context);
                InlineMarker.finallyEnd(1);
                throw th3;
            }
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: visitWithFile");
    }

    public final <R> R withCallOrAssignment(FirStatement callOrAssignment, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(callOrAssignment, "callOrAssignment");
        Intrinsics.checkNotNullParameter(block, "block");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addCallOrAssignment(callOrAssignment));
        try {
            FirSession session = getContext().getSession();
            try {
                R rInvoke = block.invoke();
                InlineMarker.finallyStart(1);
                context.dropCallOrAssignment();
                setContext(context);
                InlineMarker.finallyEnd(1);
                return rInvoke;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(callOrAssignment, th);
                throw null;
            }
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            context.dropCallOrAssignment();
            setContext(context);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    public final <R> R withGetClassCall(FirGetClassCall getClassCall, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        Intrinsics.checkNotNullParameter(block, "block");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addGetClassCall(getClassCall));
        try {
            FirSession session = getContext().getSession();
            try {
                R rInvoke = block.invoke();
                InlineMarker.finallyStart(1);
                context.dropGetClassCall();
                setContext(context);
                InlineMarker.finallyEnd(1);
                return rInvoke;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(getClassCall, th);
                throw null;
            }
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            context.dropGetClassCall();
            setContext(context);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    public final <R> R withDeclaration(FirDeclaration declaration, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(block, "block");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addDeclaration(declaration));
        try {
            FirSession session = getContext().getSession();
            try {
                R rInvoke = block.invoke();
                InlineMarker.finallyStart(1);
                context.dropDeclaration();
                setContext(context);
                InlineMarker.finallyEnd(1);
                return rInvoke;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(declaration, th);
                throw null;
            }
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            context.dropDeclaration();
            setContext(context);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    public final <R> R withFile(FirFile file, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(block, "block");
        CheckerContextForProvider context = getContext();
        setContext(getContext().enterFile(file));
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            context.exitFile(file);
            setContext(context);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withElement(FirElement element, Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(block, "block");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(element));
        try {
            FirSession session = getContext().getSession();
            try {
                T tInvoke = block.invoke();
                InlineMarker.finallyStart(1);
                context.dropElement();
                setContext(context);
                InlineMarker.finallyEnd(1);
                return tInvoke;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(element, th);
                throw null;
            }
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            context.dropElement();
            setContext(context);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    public final <R> R withLabelAndReceiverType(Name labelName, FirDeclaration owner, ConeKotlinType type, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(block, "block");
        ImplicitReceivers implicitReceiversCollectImplicitReceivers = ImplicitReceiverUtilsKt.collectImplicitReceivers(getContext().getSessionHolder(), type, owner);
        ImplicitReceiverValue<?> implicitReceiverValueComponent1 = implicitReceiversCollectImplicitReceivers.component1();
        List<ImplicitReceiverValue<?>> listComponent2 = implicitReceiversCollectImplicitReceivers.component2();
        CheckerContextForProvider context = getContext();
        Iterator<T> it = listComponent2.iterator();
        while (it.hasNext()) {
            setContext(getContext().addImplicitReceiver(null, (ImplicitReceiverValue) it.next()));
        }
        if (implicitReceiverValueComponent1 != null) {
            setContext(getContext().addImplicitReceiver(labelName, implicitReceiverValueComponent1));
        }
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setContext(context);
            InlineMarker.finallyEnd(1);
        }
    }

    public final void addSuppressedDiagnosticsToContext(FirAnnotationContainer annotationContainer) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        List<String> diagnosticsSuppressedForContainer = AbstractDiagnosticCollector.INSTANCE.getDiagnosticsSuppressedForContainer(annotationContainer);
        if (diagnosticsSuppressedForContainer == null) {
            return;
        }
        this.context = this.context.addSuppressedDiagnostics(diagnosticsSuppressedForContainer, diagnosticsSuppressedForContainer.contains(AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS), diagnosticsSuppressedForContainer.contains(AbstractDiagnosticCollector.SUPPRESS_ALL_WARNINGS), diagnosticsSuppressedForContainer.contains("errors"));
    }

    public void visitAnnotationContainer(FirAnnotationContainer annotationContainer, Void data) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(annotationContainer));
        try {
            FirSession session = getContext().getSession();
            try {
                context = getContext();
                addSuppressedDiagnosticsToContext(annotationContainer);
                boolean zIsEmpty = annotationContainer.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(annotationContainer));
                }
                try {
                    checkElement(annotationContainer);
                    visitNestedElements(annotationContainer);
                    Unit unit = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                    context.dropElement();
                } finally {
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                }
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(annotationContainer, th);
                throw null;
            }
        } catch (Throwable th2) {
            context.dropElement();
            setContext(context);
            throw th2;
        }
    }

    private final void visitJump(FirLoopJump loopJump) {
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(loopJump));
        try {
            FirSession session = getContext().getSession();
            try {
                context = getContext();
                addSuppressedDiagnosticsToContext(loopJump);
                boolean zIsEmpty = loopJump.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(loopJump));
                }
                try {
                    checkElement(loopJump);
                    FirTargetElement labeledElement = loopJump.getTarget().getLabeledElement();
                    if (!(((FirLoop) labeledElement) instanceof FirErrorLoop)) {
                        labeledElement = null;
                    }
                    FirLoop firLoop = (FirLoop) labeledElement;
                    if (firLoop != null) {
                        firLoop.accept(this, null);
                        Unit unit = Unit.INSTANCE;
                    }
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                    context.dropElement();
                } finally {
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                }
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(loopJump, th);
                throw null;
            }
        } catch (Throwable th2) {
            context.dropElement();
            setContext(context);
            throw th2;
        }
    }

    private final void visitClassAndChildren(FirClass klass, ConeClassLikeType type) {
        FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(type);
        firReceiverParameterBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
        FirReceiverParameter firReceiverParameterMo6809build = firReceiverParameterBuilder.mo6809build();
        FirClass firClass = klass;
        FirRegularClass firRegularClass = klass instanceof FirRegularClass ? (FirRegularClass) klass : null;
        visitWithDeclarationAndReceiver(firClass, firRegularClass != null ? firRegularClass.getName() : null, firReceiverParameterMo6809build);
    }

    public void visitRegularClass(FirRegularClass regularClass, Void data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(regularClass));
        try {
            FirSession session = getContext().getSession();
            try {
                context = getContext();
                addSuppressedDiagnosticsToContext(regularClass);
                boolean zIsEmpty = regularClass.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(regularClass));
                }
                try {
                    visitClassAndChildren(regularClass, ScopeUtilsKt.defaultType(regularClass));
                    Unit unit = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                    context.dropElement();
                } finally {
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                }
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(regularClass, th);
                throw null;
            }
        } catch (Throwable th2) {
            context.dropElement();
            setContext(context);
            throw th2;
        }
    }

    public void visitAnonymousObject(FirAnonymousObject anonymousObject, Void data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(anonymousObject));
        try {
            FirSession session = getContext().getSession();
            try {
                context = getContext();
                addSuppressedDiagnosticsToContext(anonymousObject);
                boolean zIsEmpty = anonymousObject.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(anonymousObject));
                }
                try {
                    visitClassAndChildren(anonymousObject, ScopeUtilsKt.defaultType(anonymousObject));
                    Unit unit = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                    context.dropElement();
                } finally {
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                }
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousObject, th);
                throw null;
            }
        } catch (Throwable th2) {
            context.dropElement();
            setContext(context);
            throw th2;
        }
    }

    public void visitScript(FirScript script, Void data) {
        Intrinsics.checkNotNullParameter(script, "script");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(script));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(script);
                boolean zIsEmpty = script.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(script));
                }
                try {
                    checkElement(script);
                    CheckerContextForProvider context3 = getContext();
                    setContext(getContext().addDeclaration(script));
                    try {
                        FirSession session2 = getContext().getSession();
                        try {
                            visitNestedElements(script);
                            Unit unit = Unit.INSTANCE;
                            context3.dropDeclaration();
                            setContext(context3);
                            Unit unit2 = Unit.INSTANCE;
                            if (!zIsEmpty) {
                                context2.dropAnnotationContainer();
                            }
                            setContext(context2);
                            context.dropElement();
                            setContext(context);
                        } catch (Throwable th) {
                            UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(script, th);
                            throw null;
                        }
                    } catch (Throwable th2) {
                        context3.dropDeclaration();
                        setContext(context3);
                        throw th2;
                    }
                } catch (Throwable th3) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th3;
                }
            } catch (Throwable th4) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(script, th4);
                throw null;
            }
        } catch (Throwable th5) {
            context.dropElement();
            setContext(context);
            throw th5;
        }
    }

    public void visitSimpleFunction(FirSimpleFunction simpleFunction, Void data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(simpleFunction));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(simpleFunction);
                boolean zIsEmpty = simpleFunction.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(simpleFunction));
                }
                try {
                    boolean zIsInline = simpleFunction.getStatus().isInline();
                    if (zIsInline) {
                        try {
                            CheckerContextForProvider checkerContextForProvider = this.context;
                            this.context = checkerContextForProvider.setInlineFunctionBodyContext(FirInlineDeclarationCheckerKt.createInlineFunctionBodyContext(simpleFunction, checkerContextForProvider.getSession()));
                        } catch (Throwable th) {
                            if (zIsInline) {
                                this.context = this.context.unsetInlineFunctionBodyContext();
                            }
                            throw th;
                        }
                    }
                    visitWithDeclarationAndReceiver(simpleFunction, simpleFunction.getName(), simpleFunction.getReceiverParameter());
                    Unit unit = Unit.INSTANCE;
                    if (zIsInline) {
                        this.context = this.context.unsetInlineFunctionBodyContext();
                    }
                    Unit unit2 = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    context.dropElement();
                    setContext(context);
                } catch (Throwable th2) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th2;
                }
            } catch (Throwable th3) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(simpleFunction, th3);
                throw null;
            }
        } catch (Throwable th4) {
            context.dropElement();
            setContext(context);
            throw th4;
        }
    }

    public void visitConstructor(FirConstructor constructor, Void data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(constructor));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(constructor);
                boolean zIsEmpty = constructor.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(constructor));
                }
                try {
                    FirConstructor firConstructor = constructor;
                    if (shouldVisitDeclaration(constructor)) {
                        checkElement(constructor);
                        CheckerContextForProvider context3 = getContext();
                        setContext(getContext().addDeclaration(constructor));
                        try {
                            FirSession session2 = getContext().getSession();
                            try {
                                visitNestedElements(firConstructor);
                                Unit unit = Unit.INSTANCE;
                                context3.dropDeclaration();
                                setContext(context3);
                                onDeclarationExit(constructor);
                            } catch (Throwable th) {
                                UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(constructor, th);
                                throw null;
                            }
                        } catch (Throwable th2) {
                            context3.dropDeclaration();
                            setContext(context3);
                            throw th2;
                        }
                    }
                    Unit unit2 = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    context.dropElement();
                    setContext(context);
                } catch (Throwable th3) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th3;
                }
            } catch (Throwable th4) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(constructor, th4);
                throw null;
            }
        } catch (Throwable th5) {
            context.dropElement();
            setContext(context);
            throw th5;
        }
    }

    public void visitAnonymousFunction(FirAnonymousFunction anonymousFunction, Void data) {
        String name2;
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(anonymousFunction));
        try {
            FirSession session = getContext().getSession();
            try {
                context = getContext();
                addSuppressedDiagnosticsToContext(anonymousFunction);
                boolean zIsEmpty = anonymousFunction.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(anonymousFunction));
                }
                try {
                    FirLabel label = anonymousFunction.getLabel();
                    visitWithDeclarationAndReceiver(anonymousFunction, (label == null || (name2 = label.getName()) == null) ? null : Name.identifier(name2), anonymousFunction.getReceiverParameter());
                    Unit unit = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                    context.dropElement();
                } finally {
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                }
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousFunction, th);
                throw null;
            }
        } catch (Throwable th2) {
            context.dropElement();
            setContext(context);
            throw th2;
        }
    }

    public void visitProperty(FirProperty property, Void data) {
        Intrinsics.checkNotNullParameter(property, "property");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(property));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(property);
                boolean zIsEmpty = property.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(property));
                }
                try {
                    FirProperty firProperty = property;
                    if (shouldVisitDeclaration(property)) {
                        checkElement(property);
                        CheckerContextForProvider context3 = getContext();
                        setContext(getContext().addDeclaration(property));
                        try {
                            FirSession session2 = getContext().getSession();
                            try {
                                visitNestedElements(firProperty);
                                Unit unit = Unit.INSTANCE;
                                context3.dropDeclaration();
                                setContext(context3);
                                onDeclarationExit(property);
                            } catch (Throwable th) {
                                UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(property, th);
                                throw null;
                            }
                        } catch (Throwable th2) {
                            context3.dropDeclaration();
                            setContext(context3);
                            throw th2;
                        }
                    }
                    Unit unit2 = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    context.dropElement();
                    setContext(context);
                } catch (Throwable th3) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th3;
                }
            } catch (Throwable th4) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(property, th4);
                throw null;
            }
        } catch (Throwable th5) {
            context.dropElement();
            setContext(context);
            throw th5;
        }
    }

    public void visitTypeAlias(FirTypeAlias typeAlias, Void data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(typeAlias));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(typeAlias);
                boolean zIsEmpty = typeAlias.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(typeAlias));
                }
                try {
                    FirTypeAlias firTypeAlias = typeAlias;
                    if (shouldVisitDeclaration(typeAlias)) {
                        checkElement(typeAlias);
                        CheckerContextForProvider context3 = getContext();
                        setContext(getContext().addDeclaration(typeAlias));
                        try {
                            FirSession session2 = getContext().getSession();
                            try {
                                visitNestedElements(firTypeAlias);
                                Unit unit = Unit.INSTANCE;
                                context3.dropDeclaration();
                                setContext(context3);
                                onDeclarationExit(typeAlias);
                            } catch (Throwable th) {
                                UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(typeAlias, th);
                                throw null;
                            }
                        } catch (Throwable th2) {
                            context3.dropDeclaration();
                            setContext(context3);
                            throw th2;
                        }
                    }
                    Unit unit2 = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    context.dropElement();
                    setContext(context);
                } catch (Throwable th3) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th3;
                }
            } catch (Throwable th4) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(typeAlias, th4);
                throw null;
            }
        } catch (Throwable th5) {
            context.dropElement();
            setContext(context);
            throw th5;
        }
    }

    public void visitReceiverParameter(FirReceiverParameter receiverParameter, Void data) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(receiverParameter));
        try {
            FirSession session = getContext().getSession();
            try {
                context = getContext();
                addSuppressedDiagnosticsToContext(receiverParameter);
                boolean zIsEmpty = receiverParameter.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(receiverParameter));
                }
                try {
                    visitNestedElements(receiverParameter);
                    Unit unit = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                    context.dropElement();
                } finally {
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                }
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(receiverParameter, th);
                throw null;
            }
        } catch (Throwable th2) {
            context.dropElement();
            setContext(context);
            throw th2;
        }
    }

    public void visitValueParameter(FirValueParameter valueParameter, Void data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(valueParameter));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(valueParameter);
                boolean zIsEmpty = valueParameter.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(valueParameter));
                }
                try {
                    FirValueParameter firValueParameter = valueParameter;
                    if (shouldVisitDeclaration(valueParameter)) {
                        checkElement(valueParameter);
                        CheckerContextForProvider context3 = getContext();
                        setContext(getContext().addDeclaration(valueParameter));
                        try {
                            FirSession session2 = getContext().getSession();
                            try {
                                visitNestedElements(firValueParameter);
                                Unit unit = Unit.INSTANCE;
                                context3.dropDeclaration();
                                setContext(context3);
                                onDeclarationExit(valueParameter);
                            } catch (Throwable th) {
                                UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(valueParameter, th);
                                throw null;
                            }
                        } catch (Throwable th2) {
                            context3.dropDeclaration();
                            setContext(context3);
                            throw th2;
                        }
                    }
                    Unit unit2 = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    context.dropElement();
                    setContext(context);
                } catch (Throwable th3) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th3;
                }
            } catch (Throwable th4) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(valueParameter, th4);
                throw null;
            }
        } catch (Throwable th5) {
            context.dropElement();
            setContext(context);
            throw th5;
        }
    }

    public void visitEnumEntry(FirEnumEntry enumEntry, Void data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(enumEntry));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(enumEntry);
                boolean zIsEmpty = enumEntry.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(enumEntry));
                }
                try {
                    FirEnumEntry firEnumEntry = enumEntry;
                    if (shouldVisitDeclaration(enumEntry)) {
                        checkElement(enumEntry);
                        CheckerContextForProvider context3 = getContext();
                        setContext(getContext().addDeclaration(enumEntry));
                        try {
                            FirSession session2 = getContext().getSession();
                            try {
                                visitNestedElements(firEnumEntry);
                                Unit unit = Unit.INSTANCE;
                                context3.dropDeclaration();
                                setContext(context3);
                                onDeclarationExit(enumEntry);
                            } catch (Throwable th) {
                                UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(enumEntry, th);
                                throw null;
                            }
                        } catch (Throwable th2) {
                            context3.dropDeclaration();
                            setContext(context3);
                            throw th2;
                        }
                    }
                    Unit unit2 = Unit.INSTANCE;
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    context.dropElement();
                    setContext(context);
                } catch (Throwable th3) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th3;
                }
            } catch (Throwable th4) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(enumEntry, th4);
                throw null;
            }
        } catch (Throwable th5) {
            context.dropElement();
            setContext(context);
            throw th5;
        }
    }

    public void visitFile(FirFile file, Void data) {
        Intrinsics.checkNotNullParameter(file, "file");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(file));
        try {
            FirSession session = getContext().getSession();
            try {
                CheckerContextForProvider context2 = getContext();
                addSuppressedDiagnosticsToContext(file);
                boolean zIsEmpty = file.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(file));
                }
                try {
                    CheckerContextForProvider context3 = getContext();
                    setContext(getContext().enterFile(file));
                    try {
                        if (shouldVisitDeclaration(file)) {
                            checkElement(file);
                            CheckerContextForProvider context4 = getContext();
                            setContext(getContext().addDeclaration(file));
                            try {
                                FirSession session2 = getContext().getSession();
                                try {
                                    visitNestedElements(file);
                                    Unit unit = Unit.INSTANCE;
                                    context4.dropDeclaration();
                                    setContext(context4);
                                    onDeclarationExit(file);
                                } catch (Throwable th) {
                                    UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(file, th);
                                    throw null;
                                }
                            } catch (Throwable th2) {
                                context4.dropDeclaration();
                                setContext(context4);
                                throw th2;
                            }
                        }
                        Unit unit2 = Unit.INSTANCE;
                        context3.exitFile(file);
                        setContext(context3);
                        Unit unit3 = Unit.INSTANCE;
                        if (!zIsEmpty) {
                            context2.dropAnnotationContainer();
                        }
                        setContext(context2);
                        context.dropElement();
                        setContext(context);
                    } catch (Throwable th3) {
                        context3.exitFile(file);
                        setContext(context3);
                        throw th3;
                    }
                } catch (Throwable th4) {
                    if (!zIsEmpty) {
                        context2.dropAnnotationContainer();
                    }
                    setContext(context2);
                    throw th4;
                }
            } catch (Throwable th5) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(file, th5);
                throw null;
            }
        } catch (Throwable th6) {
            context.dropElement();
            setContext(context);
            throw th6;
        }
    }

    public void visitAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, Void data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(anonymousInitializer));
        try {
            FirSession session = getContext().getSession();
            try {
                FirAnonymousInitializer firAnonymousInitializer = anonymousInitializer;
                if (shouldVisitDeclaration(anonymousInitializer)) {
                    checkElement(anonymousInitializer);
                    CheckerContextForProvider context2 = getContext();
                    setContext(getContext().addDeclaration(anonymousInitializer));
                    try {
                        FirSession session2 = getContext().getSession();
                        try {
                            visitNestedElements(firAnonymousInitializer);
                            Unit unit = Unit.INSTANCE;
                            context2.dropDeclaration();
                            setContext(context2);
                            onDeclarationExit(anonymousInitializer);
                        } catch (Throwable th) {
                            UtilsKt.getExceptionHandler(session2).handleExceptionOnElementAnalysis(anonymousInitializer, th);
                            throw null;
                        }
                    } catch (Throwable th2) {
                        context2.dropDeclaration();
                        setContext(context2);
                        throw th2;
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                context.dropElement();
                setContext(context);
            } catch (Throwable th3) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousInitializer, th3);
                throw null;
            }
        } catch (Throwable th4) {
            context.dropElement();
            setContext(context);
            throw th4;
        }
    }

    public void visitContractDescription(FirContractDescription contractDescription, Void data) {
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext = this.context.getInlineFunctionBodyContext();
        if (inlineFunctionBodyContext != null) {
            this.context = this.context.unsetInlineFunctionBodyContext();
        } else {
            inlineFunctionBodyContext = null;
        }
        try {
            visitElement((FirElement) contractDescription, data);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (inlineFunctionBodyContext != null) {
                this.context = this.context.setInlineFunctionBodyContext(inlineFunctionBodyContext);
            }
        }
    }

    protected final void visitWithFile(FirFile file, Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(block, "block");
        CheckerContextForProvider context = getContext();
        setContext(getContext().enterFile(file));
        try {
            if (shouldVisitDeclaration(file)) {
                checkElement(file);
                CheckerContextForProvider context2 = getContext();
                setContext(getContext().addDeclaration(file));
                try {
                    FirSession session = getContext().getSession();
                    try {
                        block.invoke();
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        context2.dropDeclaration();
                        setContext(context2);
                        InlineMarker.finallyEnd(1);
                        onDeclarationExit(file);
                    } catch (Throwable th) {
                        UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(file, th);
                        throw null;
                    }
                } catch (Throwable th2) {
                    InlineMarker.finallyStart(1);
                    context2.dropDeclaration();
                    setContext(context2);
                    InlineMarker.finallyEnd(1);
                    throw th2;
                }
            }
            Unit unit2 = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            context.exitFile(file);
            setContext(context);
            InlineMarker.finallyEnd(1);
        } catch (Throwable th3) {
            InlineMarker.finallyStart(1);
            context.exitFile(file);
            setContext(context);
            InlineMarker.finallyEnd(1);
            throw th3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0033  */
    private final void visitWithDeclarationAndReceiver(FirDeclaration declaration, Name labelName, FirReceiverParameter receiverParameter) {
        ConeKotlinType coneType;
        if (shouldVisitDeclaration(declaration)) {
            checkElement(declaration);
            CheckerContextForProvider context = getContext();
            setContext(getContext().addDeclaration(declaration));
            try {
                FirSession session = getContext().getSession();
                if (receiverParameter != null) {
                    try {
                        FirTypeRef typeRef = receiverParameter.getTypeRef();
                        if (typeRef != null) {
                            coneType = FirTypeUtilsKt.getConeType(typeRef);
                        } else {
                            coneType = null;
                        }
                    } catch (Throwable th) {
                        UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(declaration, th);
                        throw null;
                    }
                } else {
                    coneType = null;
                }
                ImplicitReceivers implicitReceiversCollectImplicitReceivers = ImplicitReceiverUtilsKt.collectImplicitReceivers(getContext().getSessionHolder(), coneType, declaration);
                ImplicitReceiverValue<?> implicitReceiverValueComponent1 = implicitReceiversCollectImplicitReceivers.component1();
                List<ImplicitReceiverValue<?>> listComponent2 = implicitReceiversCollectImplicitReceivers.component2();
                CheckerContextForProvider context2 = getContext();
                Iterator<T> it = listComponent2.iterator();
                while (it.hasNext()) {
                    setContext(getContext().addImplicitReceiver(null, (ImplicitReceiverValue) it.next()));
                }
                if (implicitReceiverValueComponent1 != null) {
                    setContext(getContext().addImplicitReceiver(labelName, implicitReceiverValueComponent1));
                }
                try {
                    visitNestedElements(declaration);
                    Unit unit = Unit.INSTANCE;
                    setContext(context2);
                    Unit unit2 = Unit.INSTANCE;
                    context.dropDeclaration();
                    setContext(context);
                    onDeclarationExit(declaration);
                } catch (Throwable th2) {
                    setContext(context2);
                    throw th2;
                }
            } catch (Throwable th3) {
                context.dropDeclaration();
                setContext(context);
                throw th3;
            }
        }
    }

    private final void visitWithCallOrAssignment(FirStatement callOrAssignment) {
        CheckerContextForProvider context = getContext();
        setContext(getContext().addCallOrAssignment(callOrAssignment));
        try {
            FirSession session = getContext().getSession();
            try {
                visitElement((FirElement) callOrAssignment, (Void) null);
                Unit unit = Unit.INSTANCE;
                context.dropCallOrAssignment();
                setContext(context);
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(callOrAssignment, th);
                throw null;
            }
        } catch (Throwable th2) {
            context.dropCallOrAssignment();
            setContext(context);
            throw th2;
        }
    }

    private final void visitWithGetClassCall(FirGetClassCall getClassCall) {
        CheckerContextForProvider context = getContext();
        setContext(getContext().addGetClassCall(getClassCall));
        try {
            FirSession session = getContext().getSession();
            try {
                visitElement((FirElement) getClassCall, (Void) null);
                Unit unit = Unit.INSTANCE;
                context.dropGetClassCall();
                setContext(context);
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(getClassCall, th);
                throw null;
            }
        } catch (Throwable th2) {
            context.dropGetClassCall();
            setContext(context);
            throw th2;
        }
    }

    public final <R> R withAnnotationContainer(FirAnnotationContainer annotationContainer, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        Intrinsics.checkNotNullParameter(block, "block");
        CheckerContextForProvider context = getContext();
        setContext(getContext().addElement(annotationContainer));
        try {
            FirSession session = getContext().getSession();
            try {
                context = getContext();
                addSuppressedDiagnosticsToContext(annotationContainer);
                boolean zIsEmpty = annotationContainer.getAnnotations().isEmpty();
                if (!zIsEmpty) {
                    setContext(getContext().addAnnotationContainer(annotationContainer));
                }
                try {
                    R rInvoke = block.invoke();
                    InlineMarker.finallyStart(1);
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                    InlineMarker.finallyEnd(1);
                    InlineMarker.finallyStart(1);
                    context.dropElement();
                    return rInvoke;
                } finally {
                    InlineMarker.finallyStart(1);
                    if (!zIsEmpty) {
                        context.dropAnnotationContainer();
                    }
                    setContext(context);
                    InlineMarker.finallyEnd(1);
                }
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(annotationContainer, th);
                throw null;
            }
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            context.dropElement();
            setContext(context);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }
}
