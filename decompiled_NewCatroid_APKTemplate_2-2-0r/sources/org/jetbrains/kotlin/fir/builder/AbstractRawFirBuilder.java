package org.jetbrains.kotlin.fir.builder;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.apk.ApkUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirExpressionRef;
import org.jetbrains.kotlin.fir.FirFunctionTarget;
import org.jetbrains.kotlin.fir.FirGenerationKt;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirLoopTarget;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructedClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirErrorProperty;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousObjectBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructedClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirErrorFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirErrorPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationBuildingUtilsKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeDestructuringDeclarationsOnTopLevel;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnderscoreIsReserved;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAssignmentOperatorStatementBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAugmentedArraySetCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBlockBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirDesugaredAssignmentValueReferenceExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirFunctionCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirImplicitInvokeCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirIncrementDecrementExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirLoopBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirLoopJumpBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirReturnExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirStringConcatenationCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirUnitExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirVariableAssignmentBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirSimpleNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbolKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirErrorFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirErrorPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.parsing.ParseUtilsKt;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachementBuilderUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: AbstractRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ä\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0004Ï\u0001Ð\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J(\u00102\u001a\u0002032\u0006\u00104\u001a\u00020*2\b\u00105\u001a\u0004\u0018\u0001062\f\u00107\u001a\b\u0012\u0004\u0012\u0002080&H\u0014Jj\u00109\u001a\u00020:2\b\u0010;\u001a\u0004\u0018\u00018\u00002\u0017\u0010<\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020:0=¢\u0006\u0002\b>2\b\u0010?\u001a\u0004\u0018\u0001062.\b\u0002\u0010@\u001a(\u0012\u0004\u0012\u00020B\u0012\u0013\u0012\u00110:¢\u0006\f\bC\u0012\b\bD\u0012\u0004\b\b(;\u0012\u0004\u0012\u0002030A¢\u0006\u0002\b>H\u0002¢\u0006\u0002\u0010EJ\u0010\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u000206H\u0004J$\u0010I\u001a\u00020J2\b\u0010K\u001a\u0004\u0018\u00010J2\b\u0010L\u001a\u0004\u0018\u0001062\u0006\u0010M\u001a\u000206H\u0004J&\u0010N\u001a\u0010\u0012\u0004\u0012\u00020P\u0012\u0006\u0012\u0004\u0018\u0001060O2\u0006\u0010Q\u001a\u00020\u001e2\u0006\u0010H\u001a\u000206H\u0004J\u0006\u0010R\u001a\u00020SJ\u000e\u0010T\u001a\u00020S2\u0006\u0010D\u001a\u00020UJ\u001a\u0010V\u001a\b\u0012\u0004\u0012\u0002080&2\f\u0010W\u001a\b\u0012\u0004\u0012\u0002080&J\"\u0010X\u001a\u00020Y2\u0006\u0010Z\u001a\u00020Y2\b\u0010H\u001a\u0004\u0018\u0001062\u0006\u0010;\u001a\u00020:H\u0004J%\u0010[\u001a\u0004\u0018\u00010:2\u0006\u0010H\u001a\u00028\u00002\u0006\u0010;\u001a\u00020:2\u0006\u0010\\\u001a\u00020\"¢\u0006\u0002\u0010]J(\u0010^\u001a\u00020U2\u0006\u0010_\u001a\u00020U2\u0006\u0010`\u001a\u00020a2\u000e\u0010Q\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001e0bH\u0004J\b\u0010c\u001a\u00020dH\u0004J\b\u0010e\u001a\u0004\u0018\u00010fJ\n\u0010g\u001a\u0004\u0018\u00010fH\u0004Jb\u0010h\u001a\u00020i2\u0006\u0010;\u001a\u00020:2\b\u0010j\u001a\u0004\u0018\u0001062\b\u0010k\u001a\u0004\u0018\u0001062\u0006\u0010l\u001a\u00020m2\f\u0010n\u001a\b\u0012\u0004\u0012\u00020o0&2\b\u0010p\u001a\u0004\u0018\u00018\u00002\u0017\u0010<\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020:0=¢\u0006\u0002\b>H\u0002¢\u0006\u0002\u0010qJ\u0013\u0010r\u001a\u00020:2\u0006\u0010s\u001a\u00028\u0000¢\u0006\u0002\u0010tJP\u0010u\u001a\u00020:2\u0006\u0010v\u001a\u00028\u00002\b\u0010w\u001a\u0004\u0018\u00018\u00002\b\u0010;\u001a\u0004\u0018\u00018\u00002\u0006\u0010x\u001a\u00020U2\u0006\u0010y\u001a\u00020*2\u0017\u0010<\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020:0=¢\u0006\u0002\b>¢\u0006\u0002\u0010zJP\u0010{\u001a\u00020:2\u0006\u0010v\u001a\u00028\u00002\b\u0010w\u001a\u0004\u0018\u00018\u00002\u0006\u0010;\u001a\u00028\u00002\u0006\u0010x\u001a\u00020U2\u0006\u0010y\u001a\u00020*2\u0017\u0010<\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020:0=¢\u0006\u0002\b>H\u0002¢\u0006\u0002\u0010zJ0\u0010|\u001a\u00020}2\u0006\u0010~\u001a\u00020}2\b\u0010j\u001a\u0004\u0018\u0001062\u0006\u0010\u007f\u001a\u00020:2\f\u0010n\u001a\b\u0012\u0004\u0012\u00020o0&H\u0002J\u0011\u0010\u0080\u0001\u001a\u0002032\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001JJ\u0010\u0083\u0001\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\u0006\u00104\u001a\u00020*2\n\b\u0002\u00105\u001a\u0004\u0018\u0001062\f\u00107\u001a\b\u0012\u0004\u0012\u0002080&2\r\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00010bH\u0084\bø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001JD\u0010\u0086\u0001\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\u0006\u0010D\u001a\u00020U2\u0007\u0010\u0087\u0001\u001a\u00020*2\t\b\u0002\u0010\u0088\u0001\u001a\u00020*2\r\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00010bH\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008a\u0001J5\u0010\u008b\u0001\u001a\u0003H\u008c\u0001\"\u0005\b\u0001\u0010\u008c\u00012\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u000e\u0010\u008f\u0001\u001a\t\u0012\u0005\u0012\u0003H\u008c\u00010bH\u0084\bø\u0001\u0000¢\u0006\u0003\u0010\u0090\u0001J\u001b\u0010\u0091\u0001\u001a\u00030\u0092\u0001*\u00030\u0092\u00012\u0006\u0010s\u001a\u00028\u0000¢\u0006\u0003\u0010\u0093\u0001J'\u0010\u0094\u0001\u001a\u00030\u0095\u0001*\u00030\u0096\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u000e\u0010\u0099\u0001\u001a\t\u0012\u0005\u0012\u00030\u009a\u00010bJi\u0010\u009b\u0001\u001a\u00020i*\u0004\u0018\u00018\u00002\b\u0010j\u001a\u0004\u0018\u0001062\b\u0010k\u001a\u0004\u0018\u0001062\u0006\u0010\u007f\u001a\u00020:2\u0006\u0010l\u001a\u00020m2\f\u0010n\u001a\b\u0012\u0004\u0012\u00020o0&2\t\u0010\u009c\u0001\u001a\u0004\u0018\u00018\u00002\u0017\u0010<\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020:0=¢\u0006\u0002\b>¢\u0006\u0003\u0010\u009d\u0001J\u0014\u0010\u009e\u0001\u001a\u0004\u0018\u00018\u0000*\u00028\u0000H&¢\u0006\u0002\u0010\u001cJ\u001e\u0010\u009f\u0001\u001a\u0004\u0018\u00018\u0000*\u00028\u00002\u0007\u0010 \u0001\u001a\u00020\"H&¢\u0006\u0003\u0010¡\u0001J\u0014\u0010¢\u0001\u001a\u0004\u0018\u00018\u0000*\u00028\u0000H&¢\u0006\u0002\u0010\u001cJ\u0014\u0010£\u0001\u001a\u0004\u0018\u00010\u001e*\u00028\u0000H&¢\u0006\u0002\u0010 J\u0014\u0010¤\u0001\u001a\u0004\u0018\u00018\u0000*\u00028\u0000H&¢\u0006\u0002\u0010\u001cJ\u0013\u0010¥\u0001\u001a\u00020U*\u00028\u0000H&¢\u0006\u0003\u0010¦\u0001J\u000e\u0010§\u0001\u001a\u000203*\u00030¨\u0001H\u0004J\u000e\u0010©\u0001\u001a\u000203*\u00030ª\u0001H\u0004J\u000e\u0010«\u0001\u001a\u000203*\u00030¬\u0001H\u0004J \u0010\u00ad\u0001\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0001\u0010\u0001*\t\u0012\u0004\u0012\u0002H\u00010®\u0001¢\u0006\u0003\u0010¯\u0001J\u0016\u0010°\u0001\u001a\u00030\u0098\u0001*\u00030\u0096\u00012\u0007\u0010±\u0001\u001a\u00020\u0002J\u0018\u0010°\u0001\u001a\u00030\u0098\u0001*\u00030\u0096\u00012\t\u0010²\u0001\u001a\u0004\u0018\u00010PJ\u001e\u0010³\u0001\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u0001*\t\u0012\u0004\u0012\u0002H\u00010®\u0001¢\u0006\u0003\u0010¯\u0001J3\u0010´\u0001\u001a\u00030\u0082\u0001*\u0004\u0018\u00018\u00002\r\u0010µ\u0001\u001a\b\u0012\u0004\u0012\u0002080&2\f\u0010¶\u0001\u001a\u0007\u0012\u0002\b\u00030·\u0001H\u0004¢\u0006\u0003\u0010¸\u0001J\u001e\u0010´\u0001\u001a\u00030\u0082\u0001*\u0004\u0018\u00018\u00002\b\u0010¹\u0001\u001a\u00030º\u0001¢\u0006\u0003\u0010»\u0001J\u001e\u0010´\u0001\u001a\u00030\u0082\u0001*\u0004\u0018\u00018\u00002\b\u0010¼\u0001\u001a\u00030¨\u0001¢\u0006\u0003\u0010½\u0001J!\u0010¾\u0001\u001a\u000206*\u00028\u00002\f\b\u0002\u0010¿\u0001\u001a\u0005\u0018\u00010À\u0001H&¢\u0006\u0003\u0010Á\u0001J]\u0010Â\u0001\u001a\u00020:*\r\u0012\b\b\u0001\u0012\u0004\u0018\u00018\u00000Ã\u00012\u0007\u0010Ä\u0001\u001a\u00028\u00002\u0014\b\u0002\u0010#\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"0=2 \u0010Å\u0001\u001a\u001b\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020:0A¢\u0006\u0002\b>¢\u0006\u0003\u0010Æ\u0001J0\u0010Ç\u0001\u001a\u00030È\u0001*\u00020:2\n\b\u0002\u0010j\u001a\u0004\u0018\u0001062\u000b\b\u0002\u0010É\u0001\u001a\u0004\u0018\u00010\u001e2\t\b\u0002\u0010Ê\u0001\u001a\u00020*J\u0016\u0010Ë\u0001\u001a\u0004\u0018\u00018\u0000*\u0004\u0018\u00018\u0000H\u0002¢\u0006\u0002\u0010\u001cJ&\u0010Ì\u0001\u001a\u000203*\u00030Í\u00012\u0006\u0010D\u001a\u00020\u001e2\b\u0010K\u001a\u0004\u0018\u00018\u0000H\u0004¢\u0006\u0003\u0010Î\u0001R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u0011X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u0011X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\u0011X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0014\u0010\u0018\u001a\u00020\u0011X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u001a\u0010\u001a\u001a\u0004\u0018\u00018\u0000*\u0004\u0018\u00018\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\u00020\u001e*\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0016\u0010!\u001a\u00020\"*\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R \u0010%\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010&*\u0004\u0018\u00018\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0016\u0010)\u001a\u00020**\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010+R\u001a\u0010,\u001a\u0004\u0018\u00018\u0000*\u0004\u0018\u00018\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\u001cR\u001a\u0010.\u001a\u0004\u0018\u00018\u0000*\u0004\u0018\u00018\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b/\u0010\u001cR\u0016\u00100\u001a\u00020\u001e*\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b1\u0010 \u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006Ñ\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder;", ExifInterface.GPS_DIRECTION_TRUE, "", "baseSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "context", "Lorg/jetbrains/kotlin/fir/builder/Context;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/builder/Context;)V", "baseModuleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getBaseModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getBaseSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getContext", "()Lorg/jetbrains/kotlin/fir/builder/Context;", "implicitAnnotationType", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitBuiltinTypeRef;", "getImplicitAnnotationType", "()Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitBuiltinTypeRef;", "implicitAnyType", "getImplicitAnyType", "implicitEnumType", "getImplicitEnumType", "implicitUnitType", "getImplicitUnitType", "arrayExpression", "getArrayExpression", "(Ljava/lang/Object;)Ljava/lang/Object;", "asText", "", "getAsText", "(Ljava/lang/Object;)Ljava/lang/String;", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "getElementType", "(Ljava/lang/Object;)Lcom/intellij/psi/tree/IElementType;", "indexExpressions", "", "getIndexExpressions", "(Ljava/lang/Object;)Ljava/util/List;", "isVararg", "", "(Ljava/lang/Object;)Z", "receiverExpression", "getReceiverExpression", "selectorExpression", "getSelectorExpression", "unescapedValue", "getUnescapedValue", "addCapturedTypeParameters", "", NotificationCompat.CATEGORY_STATUS, "declarationSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "currentFirTypeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "buildBlockPossiblyUnderSafeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "receiver", "convert", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "sourceElementForError", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirBlockBuilder;", "Lkotlin/ParameterName;", "name", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/KtSourceElement;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "buildErrorTopLevelDestructuringDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", FirebaseAnalytics.Param.SOURCE, "buildExpressionWithErrorLabel", "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "errorLabelSource", "elementSource", "buildLabelAndErrorSource", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/FirLabel;", "rawName", "callableIdForClassConstructor", "Lorg/jetbrains/kotlin/name/CallableId;", "callableIdForName", "Lorg/jetbrains/kotlin/name/Name;", "constructorTypeParametersFromConstructedClass", "ownerTypeParameters", "convertFirSelector", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "firSelector", "convertUnaryPlusMinusCallOnIntegerLiteralIfNecessary", "operationToken", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lcom/intellij/psi/tree/IElementType;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "convertValueParameterName", "safeName", "valueParameterDeclaration", "Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder$ValueParameterDeclaration;", "Lkotlin/Function0;", "createNoTypeForParameterTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "currentDispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "dispatchReceiverForInnerClassConstructor", "generateAugmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "baseSource", "arrayAccessSource", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "rhs", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/expressions/FirOperation;Ljava/util/List;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "generateConstantExpressionByLiteral", "expression", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "generateIncrementOrDecrementBlock", "wholeExpression", "operationReference", "callName", "prefix", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/name/Name;ZLkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "generateIncrementOrDecrementBlockForArrayAccess", "putAssignmentToSafeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "safeCallNonAssignment", "rhsExpression", "registerSelfType", "selfType", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "withCapturedTypeParameters", "block", "(ZLorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withChildClassName", "isExpect", "forceLocalContext", "l", "(Lorg/jetbrains/kotlin/name/Name;ZZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withDefaultSourceElementKind", "R", "newDefault", "Lorg/jetbrains/kotlin/KtSourceElementKind;", "action", "(Lorg/jetbrains/kotlin/KtSourceElementKind;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "bindLabel", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirLoopJumpBuilder;", "(Lorg/jetbrains/kotlin/fir/expressions/builder/FirLoopJumpBuilder;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/builder/FirLoopJumpBuilder;", "configure", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirLoopBuilder;", "target", "Lorg/jetbrains/kotlin/fir/FirLoopTarget;", "generateBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "generateAssignment", "rhsAST", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirOperation;Ljava/util/List;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "getAnnotatedExpression", "getChildNodeByType", ModuleXmlParser.TYPE, "(Ljava/lang/Object;Lcom/intellij/psi/tree/IElementType;)Ljava/lang/Object;", "getExpressionInParentheses", "getLabelName", "getLabeledExpression", "getReferencedNameAsName", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/name/Name;", "initCompanionObjectSymbolAttr", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "initContainingClassAttr", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "initContainingClassForLocalAttr", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "pop", "", "(Ljava/util/List;)Ljava/lang/Object;", "prepareTarget", "firLabelUser", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "removeLast", "toDelegatedSelfType", "typeParameters", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Ljava/lang/Object;Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "firObject", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirAnonymousObjectBuilder;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/fir/declarations/builder/FirAnonymousObjectBuilder;)Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "firClass", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;)Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "toFirSourceElement", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/KtFakeSourceElementKind;)Lorg/jetbrains/kotlin/KtSourceElement;", "toInterpolatingCall", "", ApkUtil.DEF_MODULE_NAME, "convertTemplateEntry", "([Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "toReturn", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "labelName", "fromKtReturnExpression", "unwrap", "withSourceElementEntry", "Lorg/jetbrains/kotlin/utils/exceptions/ExceptionAttachmentBuilder;", "(Lorg/jetbrains/kotlin/utils/exceptions/ExceptionAttachmentBuilder;Ljava/lang/String;Ljava/lang/Object;)V", "DataClassMembersGenerator", "ValueParameterDeclaration", "raw-fir.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractRawFirBuilder<T> {
    private final FirModuleData baseModuleData;
    private final FirSession baseSession;
    private final Context<T> context;
    private final FirImplicitBuiltinTypeRef implicitAnnotationType;
    private final FirImplicitBuiltinTypeRef implicitAnyType;
    private final FirImplicitBuiltinTypeRef implicitEnumType;
    private final FirImplicitBuiltinTypeRef implicitUnitType;

    public abstract T getAnnotatedExpression(T t);

    public abstract T getArrayExpression(T t);

    public abstract String getAsText(T t);

    public abstract T getChildNodeByType(T t, IElementType iElementType);

    public abstract IElementType getElementType(T t);

    public abstract T getExpressionInParentheses(T t);

    public abstract List<T> getIndexExpressions(T t);

    public abstract String getLabelName(T t);

    public abstract T getLabeledExpression(T t);

    public abstract T getReceiverExpression(T t);

    public abstract Name getReferencedNameAsName(T t);

    public abstract T getSelectorExpression(T t);

    public abstract String getUnescapedValue(T t);

    public abstract boolean isVararg(T t);

    public abstract KtSourceElement toFirSourceElement(T t, KtFakeSourceElementKind ktFakeSourceElementKind);

    public AbstractRawFirBuilder(FirSession baseSession, Context<T> context) {
        Intrinsics.checkNotNullParameter(baseSession, "baseSession");
        Intrinsics.checkNotNullParameter(context, "context");
        this.baseSession = baseSession;
        this.context = context;
        this.baseModuleData = FirModuleDataKt.getModuleData(baseSession);
        this.implicitUnitType = baseSession.getBuiltinTypes().getUnitType();
        this.implicitAnyType = baseSession.getBuiltinTypes().getAnyType();
        this.implicitEnumType = baseSession.getBuiltinTypes().getEnumType();
        this.implicitAnnotationType = baseSession.getBuiltinTypes().getAnnotationType();
    }

    public /* synthetic */ AbstractRawFirBuilder(FirSession firSession, Context context, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, (i & 2) != 0 ? new Context() : context);
    }

    public final FirSession getBaseSession() {
        return this.baseSession;
    }

    public final Context<T> getContext() {
        return this.context;
    }

    public final FirModuleData getBaseModuleData() {
        return this.baseModuleData;
    }

    public static /* synthetic */ KtSourceElement toFirSourceElement$default(AbstractRawFirBuilder abstractRawFirBuilder, Object obj, KtFakeSourceElementKind ktFakeSourceElementKind, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toFirSourceElement");
        }
        if ((i & 1) != 0) {
            ktFakeSourceElementKind = null;
        }
        return abstractRawFirBuilder.toFirSourceElement(obj, ktFakeSourceElementKind);
    }

    protected final FirImplicitBuiltinTypeRef getImplicitUnitType() {
        return this.implicitUnitType;
    }

    protected final FirImplicitBuiltinTypeRef getImplicitAnyType() {
        return this.implicitAnyType;
    }

    protected final FirImplicitBuiltinTypeRef getImplicitEnumType() {
        return this.implicitEnumType;
    }

    protected final FirImplicitBuiltinTypeRef getImplicitAnnotationType() {
        return this.implicitAnnotationType;
    }

    public static /* synthetic */ Object withChildClassName$default(AbstractRawFirBuilder abstractRawFirBuilder, Name name2, boolean z, boolean z2, Function0 l, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: withChildClassName");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(l, "l");
        Context<T> context = abstractRawFirBuilder.getContext();
        FqName fqNameChild = abstractRawFirBuilder.getContext().getClassName().child(name2);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        context.setClassName(fqNameChild);
        boolean forcedLocalContext = abstractRawFirBuilder.getContext().getForcedLocalContext();
        abstractRawFirBuilder.getContext().setForcedLocalContext(z2 || abstractRawFirBuilder.getContext().getForcedLocalContext());
        boolean containerIsExpect = abstractRawFirBuilder.getContext().getContainerIsExpect();
        abstractRawFirBuilder.getContext().setContainerIsExpect(containerIsExpect || z);
        int size = abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().size();
        try {
            Object objInvoke = l.invoke();
            InlineMarker.finallyStart(1);
            if (abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                throw new IllegalArgumentException(("Wrong number of " + abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
            }
            if (abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size) {
                abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
            }
            Context<T> context2 = abstractRawFirBuilder.getContext();
            FqName fqNameParent = abstractRawFirBuilder.getContext().getClassName().parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            context2.setClassName(fqNameParent);
            abstractRawFirBuilder.getContext().setForcedLocalContext(forcedLocalContext);
            abstractRawFirBuilder.getContext().setContainerIsExpect(containerIsExpect);
            InlineMarker.finallyEnd(1);
            return objInvoke;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            if (abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                throw new IllegalArgumentException(("Wrong number of " + abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().size()).toString());
            }
            if (abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().size() > size) {
                abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(abstractRawFirBuilder.getContext().getDispatchReceiverTypesStack()));
            }
            Context<T> context3 = abstractRawFirBuilder.getContext();
            FqName fqNameParent2 = abstractRawFirBuilder.getContext().getClassName().parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
            context3.setClassName(fqNameParent2);
            abstractRawFirBuilder.getContext().setForcedLocalContext(forcedLocalContext);
            abstractRawFirBuilder.getContext().setContainerIsExpect(containerIsExpect);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final <T> T withChildClassName(Name name2, boolean isExpect, boolean forceLocalContext, Function0<? extends T> l) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(l, "l");
        Context<T> context = getContext();
        FqName fqNameChild = getContext().getClassName().child(name2);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        context.setClassName(fqNameChild);
        boolean forcedLocalContext = getContext().getForcedLocalContext();
        getContext().setForcedLocalContext(forceLocalContext || getContext().getForcedLocalContext());
        boolean containerIsExpect = getContext().getContainerIsExpect();
        getContext().setContainerIsExpect(containerIsExpect || isExpect);
        int size = getContext().getDispatchReceiverTypesStack().size();
        try {
            T tInvoke = l.invoke();
            InlineMarker.finallyStart(1);
            if (getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                throw new IllegalArgumentException(("Wrong number of " + getContext().getDispatchReceiverTypesStack().size()).toString());
            }
            if (getContext().getDispatchReceiverTypesStack().size() > size) {
                getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(getContext().getDispatchReceiverTypesStack()));
            }
            Context<T> context2 = getContext();
            FqName fqNameParent = getContext().getClassName().parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            context2.setClassName(fqNameParent);
            getContext().setForcedLocalContext(forcedLocalContext);
            getContext().setContainerIsExpect(containerIsExpect);
            InlineMarker.finallyEnd(1);
            return tInvoke;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            if (getContext().getDispatchReceiverTypesStack().size() > size + 1) {
                throw new IllegalArgumentException(("Wrong number of " + getContext().getDispatchReceiverTypesStack().size()).toString());
            }
            if (getContext().getDispatchReceiverTypesStack().size() > size) {
                getContext().getDispatchReceiverTypesStack().remove(CollectionsKt.getLastIndex(getContext().getDispatchReceiverTypesStack()));
            }
            Context<T> context3 = getContext();
            FqName fqNameParent2 = getContext().getClassName().parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
            context3.setClassName(fqNameParent2);
            getContext().setForcedLocalContext(forcedLocalContext);
            getContext().setContainerIsExpect(containerIsExpect);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final void registerSelfType(FirResolvedTypeRef selfType) {
        Intrinsics.checkNotNullParameter(selfType, "selfType");
        List<ConeClassLikeType> dispatchReceiverTypesStack = this.context.getDispatchReceiverTypesStack();
        ConeKotlinType type = selfType.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
        dispatchReceiverTypesStack.add((ConeClassLikeType) type);
    }

    public static /* synthetic */ Object withCapturedTypeParameters$default(AbstractRawFirBuilder abstractRawFirBuilder, boolean z, KtSourceElement ktSourceElement, List currentFirTypeParameters, Function0 block, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                ktSourceElement = null;
            }
            Intrinsics.checkNotNullParameter(currentFirTypeParameters, "currentFirTypeParameters");
            Intrinsics.checkNotNullParameter(block, "block");
            abstractRawFirBuilder.addCapturedTypeParameters(z, ktSourceElement, currentFirTypeParameters);
            try {
                return block.invoke();
            } finally {
                InlineMarker.finallyStart(1);
                abstractRawFirBuilder.getContext().popFirTypeParameters();
                InlineMarker.finallyEnd(1);
            }
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: withCapturedTypeParameters");
    }

    protected final <T> T withCapturedTypeParameters(boolean status, KtSourceElement declarationSource, List<? extends FirTypeParameterRef> currentFirTypeParameters, Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(currentFirTypeParameters, "currentFirTypeParameters");
        Intrinsics.checkNotNullParameter(block, "block");
        addCapturedTypeParameters(status, declarationSource, currentFirTypeParameters);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            getContext().popFirTypeParameters();
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addCapturedTypeParameters(boolean status, KtSourceElement declarationSource, List<? extends FirTypeParameterRef> currentFirTypeParameters) {
        Intrinsics.checkNotNullParameter(currentFirTypeParameters, "currentFirTypeParameters");
        this.context.pushFirTypeParameters(status, currentFirTypeParameters);
    }

    public final CallableId callableIdForName(Name name2) {
        FqName fqNameAsSingleFqName;
        Intrinsics.checkNotNullParameter(name2, "name");
        if (Intrinsics.areEqual(this.context.getClassName().shortNameOrSpecial(), SpecialNames.ANONYMOUS)) {
            return new CallableId(FirClassLikeSymbolKt.getANONYMOUS_CLASS_ID(), name2);
        }
        if (this.context.getClassName().isRoot() && !this.context.getInLocalContext()) {
            return new CallableId(this.context.getPackageFqName(), name2);
        }
        if (this.context.getInLocalContext()) {
            List<FirFunctionTarget> firFunctionTargets = this.context.getFirFunctionTargets();
            if (this.context.getClassName().isRoot()) {
                fqNameAsSingleFqName = this.context.getPackageFqName();
            } else {
                fqNameAsSingleFqName = this.context.getCurrentClassId().asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
            }
            for (FirFunctionTarget firFunctionTarget : firFunctionTargets) {
                if (!firFunctionTarget.getIsLambda() && firFunctionTarget.getLabelName() != null) {
                    String labelName = firFunctionTarget.getLabelName();
                    Intrinsics.checkNotNull(labelName);
                    fqNameAsSingleFqName = fqNameAsSingleFqName.child(Name.identifier(labelName));
                    Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "child(...)");
                }
            }
            return new CallableId(name2, fqNameAsSingleFqName);
        }
        return new CallableId(this.context.getPackageFqName(), this.context.getClassName(), name2, null, 8, null);
    }

    public final ConeClassLikeType currentDispatchReceiverType() {
        return ConversionUtilsKt.currentDispatchReceiverType(this.context);
    }

    protected final ConeClassLikeType dispatchReceiverForInnerClassConstructor() {
        List<ConeClassLikeType> dispatchReceiverTypesStack = this.context.getDispatchReceiverTypesStack();
        return (ConeClassLikeType) CollectionsKt.getOrNull(dispatchReceiverTypesStack, CollectionsKt.getLastIndex(dispatchReceiverTypesStack) - 1);
    }

    public final CallableId callableIdForClassConstructor() {
        FqName packageFqName;
        if (this.context.getInLocalContext()) {
            packageFqName = CallableId.INSTANCE.getPACKAGE_FQ_NAME_FOR_LOCAL();
        } else {
            packageFqName = this.context.getPackageFqName();
        }
        FqName fqName = packageFqName;
        if (Intrinsics.areEqual(this.context.getClassName(), FqName.ROOT)) {
            Name nameSpecial = Name.special("<anonymous-init>");
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            return new CallableId(fqName, nameSpecial);
        }
        FqName className = this.context.getClassName();
        Name nameShortName = this.context.getClassName().shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        return new CallableId(fqName, className, nameShortName, null, 8, null);
    }

    public final <T> T removeLast(List<T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return list.remove(list.size() - 1);
    }

    public final <T> T pop(List<T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        T t = (T) CollectionsKt.lastOrNull((List) list);
        if (t != null) {
            list.remove(list.size() - 1);
        }
        return t;
    }

    public static /* synthetic */ FirReturnExpression toReturn$default(AbstractRawFirBuilder abstractRawFirBuilder, FirExpression firExpression, KtSourceElement ktSourceElement, String str, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toReturn");
        }
        if ((i & 1) != 0) {
            ktSourceElement = firExpression.getSource();
        }
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return abstractRawFirBuilder.toReturn(firExpression, ktSourceElement, str, z);
    }

    public final FirResolvedTypeRef toDelegatedSelfType(T t, FirRegularClassBuilder firClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        return toDelegatedSelfType(t, firClass.getTypeParameters(), firClass.getSymbol());
    }

    public final FirResolvedTypeRef toDelegatedSelfType(T t, FirAnonymousObjectBuilder firObject) {
        Intrinsics.checkNotNullParameter(firObject, "firObject");
        return toDelegatedSelfType(t, firObject.getTypeParameters(), firObject.getSymbol());
    }

    public final List<FirTypeParameterRef> constructorTypeParametersFromConstructedClass(List<? extends FirTypeParameterRef> ownerTypeParameters) {
        Intrinsics.checkNotNullParameter(ownerTypeParameters, "ownerTypeParameters");
        ArrayList arrayList = new ArrayList();
        for (FirTypeParameterRef firTypeParameterRef : ownerTypeParameters) {
            FirConstructedClassTypeParameterRef firConstructedClassTypeParameterRefBuild = null;
            FirTypeParameter firTypeParameter = firTypeParameterRef instanceof FirTypeParameter ? (FirTypeParameter) firTypeParameterRef : null;
            if (firTypeParameter != null) {
                FirConstructedClassTypeParameterRefBuilder firConstructedClassTypeParameterRefBuilder = new FirConstructedClassTypeParameterRefBuilder();
                KtSourceElement source = firTypeParameter.getSymbol().getSource();
                firConstructedClassTypeParameterRefBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ConstructorTypeParameter.INSTANCE, 0, 0, 6, null) : null);
                firConstructedClassTypeParameterRefBuilder.setSymbol(firTypeParameter.getSymbol());
                firConstructedClassTypeParameterRefBuild = firConstructedClassTypeParameterRefBuilder.build();
            }
            if (firConstructedClassTypeParameterRefBuild != null) {
                arrayList.add(firConstructedClassTypeParameterRefBuild);
            }
        }
        return arrayList;
    }

    public final FirLoopTarget prepareTarget(FirLoopBuilder firLoopBuilder, Object firLabelUser) {
        Intrinsics.checkNotNullParameter(firLoopBuilder, "<this>");
        Intrinsics.checkNotNullParameter(firLabelUser, "firLabelUser");
        return prepareTarget(firLoopBuilder, this.context.getLastLabel(firLabelUser));
    }

    public final FirLoopTarget prepareTarget(FirLoopBuilder firLoopBuilder, FirLabel firLabel) {
        Intrinsics.checkNotNullParameter(firLoopBuilder, "<this>");
        firLoopBuilder.setLabel(firLabel);
        FirLoopTarget firLoopTarget = new FirLoopTarget(firLabel != null ? firLabel.getName() : null);
        this.context.getFirLoopTargets().add(firLoopTarget);
        return firLoopTarget;
    }

    public final FirLoop configure(FirLoopBuilder firLoopBuilder, FirLoopTarget target, Function0<? extends FirBlock> generateBlock) {
        Intrinsics.checkNotNullParameter(firLoopBuilder, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(generateBlock, "generateBlock");
        firLoopBuilder.setBlock(generateBlock.invoke());
        FirLoop firLoopBuild = firLoopBuilder.build();
        Intrinsics.areEqual(target, (FirLoopTarget) removeLast(this.context.getFirLoopTargets()));
        target.bind(firLoopBuild);
        return firLoopBuild;
    }

    public final FirLoopJumpBuilder bindLabel(FirLoopJumpBuilder firLoopJumpBuilder, T t) {
        DiagnosticKind diagnosticKind;
        Intrinsics.checkNotNullParameter(firLoopJumpBuilder, "<this>");
        String labelName = getLabelName(t);
        FirLoopTarget firLoopTarget = (FirLoopTarget) CollectionsKt.lastOrNull((List) this.context.getFirLoopTargets());
        KtSourceElement firSourceElement$default = toFirSourceElement$default(this, t, null, 1, null);
        if (labelName == null) {
            if (firLoopTarget == null) {
                firLoopTarget = new FirLoopTarget(labelName);
                firLoopTarget.bind(FirExpressionUtilKt.buildErrorLoop(firSourceElement$default, new ConeSimpleDiagnostic("Cannot bind unlabeled jump to a loop", DiagnosticKind.JumpOutsideLoop)));
            }
            firLoopJumpBuilder.setTarget(firLoopTarget);
        } else {
            for (FirLoopTarget firLoopTarget2 : CollectionsKt.asReversedMutable(this.context.getFirLoopTargets())) {
                if (Intrinsics.areEqual(firLoopTarget2.getLabelName(), labelName)) {
                    firLoopJumpBuilder.setTarget(firLoopTarget2);
                    return firLoopJumpBuilder;
                }
            }
            FirLoopTarget firLoopTarget3 = new FirLoopTarget(labelName);
            String str = "Cannot bind label " + labelName + " to a loop";
            if (firLoopTarget == null || (diagnosticKind = DiagnosticKind.NotLoopLabel) == null) {
                diagnosticKind = DiagnosticKind.JumpOutsideLoop;
            }
            firLoopTarget3.bind(FirExpressionUtilKt.buildErrorLoop(firSourceElement$default, new ConeSimpleDiagnostic(str, diagnosticKind)));
            firLoopJumpBuilder.setTarget(firLoopTarget3);
        }
        return firLoopJumpBuilder;
    }

    public final FirExpression generateConstantExpressionByLiteral(T expression) {
        Boolean boolValueOf;
        ConstantValueKind.IntegerLiteral integerLiteral;
        IElementType elementType = getElementType(expression);
        String asText = getAsText(expression);
        Object obj = null;
        KtSourceElement firSourceElement$default = toFirSourceElement$default(this, expression, null, 1, null);
        if (Intrinsics.areEqual(elementType, KtNodeTypes.INTEGER_CONSTANT) ? true : Intrinsics.areEqual(elementType, KtNodeTypes.FLOAT_CONSTANT)) {
            if (ParseUtilsKt.hasIllegalUnderscore(asText, elementType)) {
                return generateConstantExpressionByLiteral$reportIncorrectConstant(firSourceElement$default, asText, DiagnosticKind.IllegalUnderscore);
            }
            boolValueOf = ParseUtilsKt.parseNumericLiteral(asText, elementType);
        } else {
            boolValueOf = Intrinsics.areEqual(elementType, KtNodeTypes.BOOLEAN_CONSTANT) ? Boolean.valueOf(ParseUtilsKt.parseBoolean(asText)) : null;
        }
        if (Intrinsics.areEqual(elementType, KtNodeTypes.INTEGER_CONSTANT)) {
            DiagnosticKind diagnosticKind = DiagnosticKind.IllegalConstExpression;
            if (boolValueOf == null) {
                diagnosticKind = DiagnosticKind.IntLiteralOutOfRange;
                integerLiteral = ConstantValueKind.IntegerLiteral.INSTANCE;
            } else {
                if (!(boolValueOf instanceof Long)) {
                    return generateConstantExpressionByLiteral$reportIncorrectConstant(firSourceElement$default, asText, DiagnosticKind.IllegalConstExpression);
                }
                if (ParseUtilsKt.hasUnsignedLongSuffix(asText)) {
                    if (StringsKt.endsWith$default(asText, "l", false, 2, (Object) null)) {
                        diagnosticKind = DiagnosticKind.WrongLongSuffix;
                    } else {
                        obj = (Long) boolValueOf;
                    }
                    integerLiteral = ConstantValueKind.UnsignedLong.INSTANCE;
                } else if (ParseUtilsKt.hasLongSuffix(asText)) {
                    if (StringsKt.endsWith$default(asText, "l", false, 2, (Object) null)) {
                        diagnosticKind = DiagnosticKind.WrongLongSuffix;
                    } else {
                        obj = (Long) boolValueOf;
                    }
                    integerLiteral = ConstantValueKind.Long.INSTANCE;
                } else if (ParseUtilsKt.hasUnsignedSuffix(asText)) {
                    obj = (Long) boolValueOf;
                    integerLiteral = ConstantValueKind.UnsignedIntegerLiteral.INSTANCE;
                } else {
                    obj = (Long) boolValueOf;
                    integerLiteral = ConstantValueKind.IntegerLiteral.INSTANCE;
                }
            }
            return FirExpressionUtilKt.buildConstOrErrorExpression(firSourceElement$default, integerLiteral, obj, new ConeSimpleDiagnostic("Incorrect integer literal: " + asText, diagnosticKind));
        }
        if (Intrinsics.areEqual(elementType, KtNodeTypes.FLOAT_CONSTANT)) {
            if (boolValueOf instanceof Float) {
                return FirExpressionUtilKt.buildConstOrErrorExpression(firSourceElement$default, ConstantValueKind.Float.INSTANCE, boolValueOf, new ConeSimpleDiagnostic("Incorrect float: " + asText, DiagnosticKind.FloatLiteralOutOfRange));
            }
            return FirExpressionUtilKt.buildConstOrErrorExpression(firSourceElement$default, ConstantValueKind.Double.INSTANCE, boolValueOf instanceof Double ? (Double) boolValueOf : null, new ConeSimpleDiagnostic("Incorrect double: " + asText, DiagnosticKind.FloatLiteralOutOfRange));
        }
        if (Intrinsics.areEqual(elementType, KtNodeTypes.CHARACTER_CONSTANT)) {
            CharacterWithDiagnostic character = ConversionUtilsKt.parseCharacter(asText);
            ConstantValueKind.Char r0 = ConstantValueKind.Char.INSTANCE;
            Character value = character.getValue();
            String str = "Incorrect character: " + asText;
            DiagnosticKind diagnostic = character.getDiagnostic();
            if (diagnostic == null) {
                diagnostic = DiagnosticKind.IllegalConstExpression;
            }
            return FirExpressionUtilKt.buildConstOrErrorExpression(firSourceElement$default, r0, value, new ConeSimpleDiagnostic(str, diagnostic));
        }
        if (Intrinsics.areEqual(elementType, KtNodeTypes.BOOLEAN_CONSTANT)) {
            ConstantValueKind.Boolean r5 = ConstantValueKind.Boolean.INSTANCE;
            Intrinsics.checkNotNull(boolValueOf, "null cannot be cast to non-null type kotlin.Boolean");
            return FirConstExpressionBuilderKt.buildConstExpression$default(firSourceElement$default, r5, (Boolean) boolValueOf, null, false, 8, null);
        }
        if (Intrinsics.areEqual(elementType, KtNodeTypes.NULL)) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(firSourceElement$default, ConstantValueKind.Null.INSTANCE, null, null, false, 8, null);
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unknown literal type: " + elementType, null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        withSourceElementEntry(exceptionAttachmentBuilder, "literal", expression);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    protected final void withSourceElementEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, T t) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (t instanceof PsiElement) {
            ExceptionAttachementBuilderUtilsKt.withPsiEntry(exceptionAttachmentBuilder, name2, (PsiElement) t);
        } else {
            exceptionAttachmentBuilder.withEntry(name2, t, new Function1<T, String>(this) { // from class: org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder.withSourceElementEntry.1
                final /* synthetic */ AbstractRawFirBuilder<T> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(T it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.getAsText(it);
                }
            });
        }
    }

    public final FirExpression convertUnaryPlusMinusCallOnIntegerLiteralIfNecessary(T t, FirExpression receiver, IElementType operationToken) {
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(operationToken, "operationToken");
        if (!(receiver instanceof FirConstExpression)) {
            return null;
        }
        FirConstExpression firConstExpression = (FirConstExpression) receiver;
        if (!Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.IntegerLiteral.INSTANCE)) {
            return null;
        }
        if (!Intrinsics.areEqual(operationToken, KtTokens.PLUS) && !Intrinsics.areEqual(operationToken, KtTokens.MINUS)) {
            return null;
        }
        Object value = firConstExpression.getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Long");
        long jLongValue = ((Long) value).longValue();
        if (Intrinsics.areEqual(operationToken, KtTokens.MINUS)) {
            jLongValue = -jLongValue;
        } else if (!Intrinsics.areEqual(operationToken, KtTokens.PLUS)) {
            throw new IllegalStateException("Should not be here".toString());
        }
        return FirConstExpressionBuilderKt.buildConstExpression$default(toFirSourceElement$default(this, t, null, 1, null), ConstantValueKind.IntegerLiteral.INSTANCE, Long.valueOf(jLongValue), null, false, 8, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirExpression toInterpolatingCall$default(AbstractRawFirBuilder abstractRawFirBuilder, Object[] objArr, Object obj, Function1 function1, Function2 function2, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toInterpolatingCall");
        }
        if ((i & 2) != 0) {
            function1 = new Function1<T, IElementType>(abstractRawFirBuilder) { // from class: org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder.toInterpolatingCall.1
                final /* synthetic */ AbstractRawFirBuilder<T> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = abstractRawFirBuilder;
                }

                @Override // kotlin.jvm.functions.Function1
                public final IElementType invoke(T t) {
                    return this.this$0.getElementType(t);
                }
            };
        }
        return abstractRawFirBuilder.toInterpolatingCall(objArr, obj, function1, function2);
    }

    public final FirExpression generateIncrementOrDecrementBlock(T wholeExpression, T operationReference, T receiver, Name callName, boolean prefix, Function1<? super T, ? extends FirExpression> convert) {
        Intrinsics.checkNotNullParameter(callName, "callName");
        Intrinsics.checkNotNullParameter(convert, "convert");
        T tUnwrap = unwrap(receiver);
        if (tUnwrap != null) {
            if (Intrinsics.areEqual(getElementType(tUnwrap), KtNodeTypes.ARRAY_ACCESS_EXPRESSION)) {
                return generateIncrementOrDecrementBlockForArrayAccess(wholeExpression, operationReference, tUnwrap, callName, prefix, convert);
            }
            FirIncrementDecrementExpressionBuilder firIncrementDecrementExpressionBuilder = new FirIncrementDecrementExpressionBuilder();
            firIncrementDecrementExpressionBuilder.setSource(wholeExpression != null ? toFirSourceElement$default(this, wholeExpression, null, 1, null) : null);
            firIncrementDecrementExpressionBuilder.setOperationSource(operationReference != null ? toFirSourceElement$default(this, operationReference, null, 1, null) : null);
            firIncrementDecrementExpressionBuilder.setOperationName(callName);
            firIncrementDecrementExpressionBuilder.setPrefix(prefix);
            firIncrementDecrementExpressionBuilder.setExpression(convert.invoke(tUnwrap));
            return firIncrementDecrementExpressionBuilder.mo6809build();
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Inc/dec without operand"));
        return firErrorExpressionBuilder.mo6809build();
    }

    private final T unwrap(T t) {
        while (true) {
            IElementType elementType = t != null ? getElementType(t) : null;
            if (Intrinsics.areEqual(elementType, KtNodeTypes.PARENTHESIZED)) {
                t = t != null ? getExpressionInParentheses(t) : null;
            } else if (Intrinsics.areEqual(elementType, KtNodeTypes.LABELED_EXPRESSION)) {
                if (t != null) {
                    t = getLabeledExpression(t);
                }
            } else {
                if (!Intrinsics.areEqual(elementType, KtNodeTypes.ANNOTATED_EXPRESSION)) {
                    return t;
                }
                if (t != null) {
                    t = getAnnotatedExpression(t);
                }
            }
        }
    }

    private final FirExpression generateIncrementOrDecrementBlockForArrayAccess(final T wholeExpression, final T operationReference, final T receiver, final Name callName, final boolean prefix, final Function1<? super T, ? extends FirExpression> convert) {
        final T arrayExpression = getArrayExpression(receiver);
        return buildBlockPossiblyUnderSafeCall(arrayExpression, convert, toFirSourceElement$default(this, receiver, null, 1, null), new Function2<FirBlockBuilder, FirExpression, Unit>() { // from class: org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder.generateIncrementOrDecrementBlockForArrayAccess.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FirBlockBuilder firBlockBuilder, FirExpression firExpression) {
                invoke2(firBlockBuilder, firExpression);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirBlockBuilder buildBlockPossiblyUnderSafeCall, FirExpression arrayReceiver) {
                Intrinsics.checkNotNullParameter(buildBlockPossiblyUnderSafeCall, "$this$buildBlockPossiblyUnderSafeCall");
                Intrinsics.checkNotNullParameter(arrayReceiver, "arrayReceiver");
                T t = wholeExpression;
                KtSourceElement firSourceElement$default = t != null ? AbstractRawFirBuilder.toFirSourceElement$default(this, t, null, 1, null) : null;
                KtSourceElement ktSourceElementFakeElement$default = firSourceElement$default != null ? KtSourceElementKt.fakeElement$default(firSourceElement$default, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE, 0, 0, 6, null) : null;
                buildBlockPossiblyUnderSafeCall.setSource(ktSourceElementFakeElement$default);
                List<T> indexExpressions = this.getIndexExpressions(receiver);
                AbstractRawFirBuilder<T> abstractRawFirBuilder = this;
                T t2 = wholeExpression;
                if (indexExpressions == null) {
                    throw new IllegalArgumentException(("No indices in " + abstractRawFirBuilder.getAsText(t2)).toString());
                }
                FirModuleData baseModuleData = abstractRawFirBuilder.getBaseModuleData();
                T t3 = arrayExpression;
                FirProperty firPropertyGenerateTemporaryVariable$default = FirGenerationKt.generateTemporaryVariable$default(baseModuleData, t3 != null ? this.toFirSourceElement(t3, KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE) : null, SpecialNames.ARRAY, arrayReceiver, null, null, 48, null);
                buildBlockPossiblyUnderSafeCall.getStatements().add(firPropertyGenerateTemporaryVariable$default);
                List<T> list = indexExpressions;
                AbstractRawFirBuilder<T> abstractRawFirBuilder2 = this;
                Function1<T, FirExpression> function1 = convert;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                int i = 0;
                for (T t4 : list) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    FirProperty firPropertyGenerateTemporaryVariable$default2 = FirGenerationKt.generateTemporaryVariable$default(abstractRawFirBuilder2.getBaseModuleData(), abstractRawFirBuilder2.toFirSourceElement(t4, KtFakeSourceElementKind.ArrayIndexExpressionReference.INSTANCE), SpecialNames.subscribeOperatorIndex(i), function1.invoke(t4), null, null, 48, null);
                    buildBlockPossiblyUnderSafeCall.getStatements().add(firPropertyGenerateTemporaryVariable$default2);
                    arrayList.add(firPropertyGenerateTemporaryVariable$default2);
                    i = i2;
                }
                ArrayList arrayList2 = arrayList;
                if (prefix) {
                    List<FirStatement> statements = buildBlockPossiblyUnderSafeCall.getStatements();
                    AbstractRawFirBuilder<T> abstractRawFirBuilder3 = this;
                    statements.add(invoke$buildSetCall(ktSourceElementFakeElement$default, firPropertyGenerateTemporaryVariable$default, abstractRawFirBuilder3, receiver, arrayList2, invoke$buildIncDecCall(ktSourceElementFakeElement$default, operationReference, abstractRawFirBuilder3, callName, KtFakeSourceElementKind.DesugaredPrefixNameReference.INSTANCE, invoke$buildGetCall$default(ktSourceElementFakeElement$default, firPropertyGenerateTemporaryVariable$default, receiver, this, arrayList2, null, 32, null))));
                    buildBlockPossiblyUnderSafeCall.getStatements().add(invoke$buildGetCall(ktSourceElementFakeElement$default, firPropertyGenerateTemporaryVariable$default, receiver, this, arrayList2, KtFakeSourceElementKind.DesugaredPrefixSecondGetReference.INSTANCE));
                    return;
                }
                FirProperty firPropertyGenerateTemporaryVariable$default3 = FirGenerationKt.generateTemporaryVariable$default(this.getBaseModuleData(), ktSourceElementFakeElement$default, SpecialNames.UNARY, invoke$buildGetCall$default(ktSourceElementFakeElement$default, firPropertyGenerateTemporaryVariable$default, receiver, this, arrayList2, null, 32, null), null, null, 48, null);
                buildBlockPossiblyUnderSafeCall.getStatements().add(firPropertyGenerateTemporaryVariable$default3);
                List<FirStatement> statements2 = buildBlockPossiblyUnderSafeCall.getStatements();
                AbstractRawFirBuilder<T> abstractRawFirBuilder4 = this;
                FirProperty firProperty = firPropertyGenerateTemporaryVariable$default3;
                statements2.add(invoke$buildSetCall(ktSourceElementFakeElement$default, firPropertyGenerateTemporaryVariable$default, abstractRawFirBuilder4, receiver, arrayList2, invoke$buildIncDecCall(ktSourceElementFakeElement$default, operationReference, abstractRawFirBuilder4, callName, KtFakeSourceElementKind.DesugaredPostfixNameReference.INSTANCE, ConversionUtilsKt.generateResolvedAccessExpression(ktSourceElementFakeElement$default, firProperty))));
                buildBlockPossiblyUnderSafeCall.getStatements().add(ConversionUtilsKt.generateResolvedAccessExpression(ktSourceElementFakeElement$default, firProperty));
            }

            static /* synthetic */ FirFunctionCall invoke$buildGetCall$default(KtSourceElement ktSourceElement, FirProperty firProperty, Object obj, AbstractRawFirBuilder abstractRawFirBuilder, List list, KtFakeSourceElementKind ktFakeSourceElementKind, int i, Object obj2) {
                if ((i & 32) != 0) {
                    ktFakeSourceElementKind = KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE;
                }
                return invoke$buildGetCall(ktSourceElement, firProperty, obj, abstractRawFirBuilder, list, ktFakeSourceElementKind);
            }

            private static final <T> FirFunctionCall invoke$buildGetCall(KtSourceElement ktSourceElement, FirProperty firProperty, T t, AbstractRawFirBuilder<T> abstractRawFirBuilder, List<? extends FirProperty> list, KtFakeSourceElementKind ktFakeSourceElementKind) {
                FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
                firFunctionCallBuilder.setSource(ktSourceElement);
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                firSimpleNamedReferenceBuilder.setSource(t != null ? abstractRawFirBuilder.toFirSourceElement(t, ktFakeSourceElementKind) : null);
                firSimpleNamedReferenceBuilder.setName(OperatorNameConventions.GET);
                firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
                firFunctionCallBuilder.setExplicitReceiver(ConversionUtilsKt.generateResolvedAccessExpression(firProperty.getSource(), firProperty));
                FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
                for (FirProperty firProperty2 : list) {
                    firArgumentListBuilder.getArguments().add(ConversionUtilsKt.generateResolvedAccessExpression(firProperty2.getSource(), firProperty2));
                }
                firFunctionCallBuilder.setArgumentList(firArgumentListBuilder.build());
                firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
                return firFunctionCallBuilder.mo6809build();
            }

            private static final <T> FirFunctionCall invoke$buildSetCall(KtSourceElement ktSourceElement, FirProperty firProperty, AbstractRawFirBuilder<T> abstractRawFirBuilder, T t, List<? extends FirProperty> list, FirExpression firExpression) {
                FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
                firFunctionCallBuilder.setSource(ktSourceElement);
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                firSimpleNamedReferenceBuilder.setSource(AbstractRawFirBuilder.toFirSourceElement$default(abstractRawFirBuilder, t, null, 1, null));
                firSimpleNamedReferenceBuilder.setName(OperatorNameConventions.SET);
                firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
                firFunctionCallBuilder.setExplicitReceiver(ConversionUtilsKt.generateResolvedAccessExpression(firProperty.getSource(), firProperty));
                FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
                for (FirProperty firProperty2 : list) {
                    firArgumentListBuilder.getArguments().add(ConversionUtilsKt.generateResolvedAccessExpression(firProperty2.getSource(), firProperty2));
                }
                firArgumentListBuilder.getArguments().add(firExpression);
                firFunctionCallBuilder.setArgumentList(firArgumentListBuilder.build());
                firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
                return firFunctionCallBuilder.mo6809build();
            }

            private static final <T> FirFunctionCall invoke$buildIncDecCall(KtSourceElement ktSourceElement, T t, AbstractRawFirBuilder<T> abstractRawFirBuilder, Name name2, KtFakeSourceElementKind ktFakeSourceElementKind, FirExpression firExpression) {
                FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
                firFunctionCallBuilder.setSource(ktSourceElement);
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                firSimpleNamedReferenceBuilder.setSource(t != null ? abstractRawFirBuilder.toFirSourceElement(t, ktFakeSourceElementKind) : null);
                firSimpleNamedReferenceBuilder.setName(name2);
                firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
                firFunctionCallBuilder.setExplicitReceiver(firExpression);
                firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
                return firFunctionCallBuilder.mo6809build();
            }
        });
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder$buildBlockPossiblyUnderSafeCall$1, reason: invalid class name */
    /* JADX INFO: compiled from: AbstractRawFirBuilder.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/builder/FirBlockBuilder;", "it", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass1 extends Lambda implements Function2<FirBlockBuilder, FirExpression, Unit> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(2);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirBlockBuilder firBlockBuilder, FirExpression it) {
            Intrinsics.checkNotNullParameter(firBlockBuilder, "$this$null");
            Intrinsics.checkNotNullParameter(it, "it");
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(FirBlockBuilder firBlockBuilder, FirExpression firExpression) {
            invoke2(firBlockBuilder, firExpression);
            return Unit.INSTANCE;
        }
    }

    private final FirExpression buildBlockPossiblyUnderSafeCall(T receiver, Function1<? super T, ? extends FirExpression> convert, KtSourceElement sourceElementForError, Function2<? super FirBlockBuilder, ? super FirExpression, Unit> init) {
        FirErrorExpression firErrorExpressionMo6809build;
        if (receiver == null || (firErrorExpressionMo6809build = convert.invoke(receiver)) == null) {
            FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
            firErrorExpressionBuilder.setSource(sourceElementForError);
            firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("No receiver expression"));
            firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
        }
        if (firErrorExpressionMo6809build instanceof FirSafeCallExpression) {
            FirSafeCallExpression firSafeCallExpression = (FirSafeCallExpression) firErrorExpressionMo6809build;
            FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
            FirStatement selector = firSafeCallExpression.getSelector();
            FirErrorExpression firErrorExpressionMo6809build2 = selector instanceof FirExpression ? (FirExpression) selector : null;
            if (firErrorExpressionMo6809build2 == null) {
                FirErrorExpressionBuilder firErrorExpressionBuilder2 = new FirErrorExpressionBuilder();
                firErrorExpressionBuilder2.setSource(sourceElementForError);
                firErrorExpressionBuilder2.setDiagnostic(new ConeSyntaxDiagnostic("Safe call selector expected to be an expression here"));
                Unit unit = Unit.INSTANCE;
                firErrorExpressionMo6809build2 = firErrorExpressionBuilder2.mo6809build();
            }
            init.invoke(firBlockBuilder, firErrorExpressionMo6809build2);
            firSafeCallExpression.replaceSelector(firBlockBuilder.mo6809build());
            return firErrorExpressionMo6809build;
        }
        FirBlockBuilder firBlockBuilder2 = new FirBlockBuilder();
        init.invoke(firBlockBuilder2, firErrorExpressionMo6809build);
        return firBlockBuilder2.mo6809build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.Object] */
    public final FirStatement generateAssignment(T t, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2, FirExpression rhsExpression, FirOperation operation, List<? extends FirAnnotation> annotations, T t2, Function1<? super T, ? extends FirExpression> convert) {
        FirErrorExpression firErrorExpressionMo6809build;
        PsiElement psi;
        Intrinsics.checkNotNullParameter(rhsExpression, "rhsExpression");
        Intrinsics.checkNotNullParameter(operation, "operation");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(convert, "convert");
        ?? Unwrap = unwrap(t);
        if (Unwrap != null) {
            text = null;
            String text = null;
            if (Intrinsics.areEqual(getElementType(Unwrap), KtNodeTypes.ARRAY_ACCESS_EXPRESSION)) {
                if (operation == FirOperation.ASSIGN) {
                    this.context.getArraySetArgument().put(Unwrap, rhsExpression);
                }
                FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
                if (operation == FirOperation.ASSIGN) {
                    FirExpression firExpressionInvoke = convert.invoke(Unwrap);
                    firExpressionInvoke.replaceAnnotations(UtilsKt.smartPlus(firExpressionInvoke.getAnnotations(), annotations));
                    firBlockBuilder.setSource(firExpressionInvoke.getSource());
                    firBlockBuilder.getStatements().add(ConversionUtilsKt.pullUpSafeCallIfNecessary(firExpressionInvoke));
                } else {
                    FirExpression firExpressionInvoke2 = convert.invoke(Unwrap);
                    if (firExpressionInvoke2 instanceof FirSafeCallExpression) {
                        FirSafeCallExpression firSafeCallExpression = (FirSafeCallExpression) firExpressionInvoke2;
                        FirStatement selector = firSafeCallExpression.getSelector();
                        Intrinsics.checkNotNull(selector, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                        firSafeCallExpression.replaceSelector(generateAugmentedArraySetCall((FirExpression) selector, ktSourceElement, ktSourceElement2, operation, annotations, t2, convert));
                        firBlockBuilder.setSource(firExpressionInvoke2.getSource());
                        firBlockBuilder.getStatements().add(firExpressionInvoke2);
                    } else {
                        FirStatement firStatementGenerateAugmentedArraySetCall = generateAugmentedArraySetCall(firExpressionInvoke2, ktSourceElement, ktSourceElement2, operation, annotations, t2, convert);
                        firBlockBuilder.setSource(firStatementGenerateAugmentedArraySetCall.getSource());
                        firBlockBuilder.getStatements().add(firStatementGenerateAugmentedArraySetCall);
                    }
                }
                List<FirStatement> statements = firBlockBuilder.getStatements();
                FirUnitExpressionBuilder firUnitExpressionBuilder = new FirUnitExpressionBuilder();
                KtSourceElement source = firBlockBuilder.getSource();
                firUnitExpressionBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitUnit.IndexedAssignmentCoercion.INSTANCE, 0, 0, 6, null) : null);
                statements.add(firUnitExpressionBuilder.mo6809build());
                return firBlockBuilder.mo6809build();
            }
            if (FirOperation.INSTANCE.getASSIGNMENTS().contains(operation) && operation != FirOperation.ASSIGN) {
                FirExpression firExpressionInvoke3 = t != null ? convert.invoke(t) : null;
                boolean z = firExpressionInvoke3 instanceof FirSafeCallExpression;
                if (z) {
                    FirStatement selector2 = ((FirSafeCallExpression) firExpressionInvoke3).getSelector();
                    firErrorExpressionMo6809build = selector2 instanceof FirExpression ? (FirExpression) selector2 : null;
                } else {
                    firErrorExpressionMo6809build = firExpressionInvoke3;
                }
                FirAssignmentOperatorStatementBuilder firAssignmentOperatorStatementBuilder = new FirAssignmentOperatorStatementBuilder();
                firAssignmentOperatorStatementBuilder.setSource(ktSourceElement);
                firAssignmentOperatorStatementBuilder.setOperation(operation);
                if (firErrorExpressionMo6809build == null) {
                    FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                    firErrorExpressionBuilder.setSource(null);
                    StringBuilder sb = new StringBuilder("Unsupported left value of assignment: ");
                    if (ktSourceElement != null && (psi = KtSourceElementKt.getPsi(ktSourceElement)) != null) {
                        text = psi.getText();
                    }
                    firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic(sb.append(text).toString(), DiagnosticKind.ExpressionExpected));
                    firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
                }
                firAssignmentOperatorStatementBuilder.setLeftArgument(firErrorExpressionMo6809build);
                firAssignmentOperatorStatementBuilder.setRightArgument(rhsExpression);
                CollectionsKt.addAll(firAssignmentOperatorStatementBuilder.getAnnotations(), annotations);
                FirAssignmentOperatorStatement firAssignmentOperatorStatementMo6809build = firAssignmentOperatorStatementBuilder.mo6809build();
                if (z) {
                    ((FirSafeCallExpression) firExpressionInvoke3).replaceSelector(firAssignmentOperatorStatementMo6809build);
                    return firExpressionInvoke3;
                }
                return firAssignmentOperatorStatementMo6809build;
            }
            if (operation != FirOperation.ASSIGN) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (Intrinsics.areEqual(t != null ? getElementType(t) : null, KtNodeTypes.SAFE_ACCESS_EXPRESSION) && t != null) {
                FirExpression firExpressionInvoke4 = convert.invoke(t);
                FirSafeCallExpression firSafeCallExpression2 = firExpressionInvoke4 instanceof FirSafeCallExpression ? (FirSafeCallExpression) firExpressionInvoke4 : null;
                if (firSafeCallExpression2 != null) {
                    return putAssignmentToSafeCall(firSafeCallExpression2, ktSourceElement, rhsExpression, annotations);
                }
            }
            FirDesugaredAssignmentValueReferenceExpression firDesugaredAssignmentValueReferenceExpressionInvoke = convert.invoke(Unwrap);
            FirVariableAssignmentBuilder firVariableAssignmentBuilder = new FirVariableAssignmentBuilder();
            firVariableAssignmentBuilder.setSource(ktSourceElement);
            if (Intrinsics.areEqual(ktSourceElement != null ? ktSourceElement.getKind() : null, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE)) {
                FirDesugaredAssignmentValueReferenceExpressionBuilder firDesugaredAssignmentValueReferenceExpressionBuilder = new FirDesugaredAssignmentValueReferenceExpressionBuilder();
                FirExpressionRef<FirExpression> firExpressionRef = new FirExpressionRef<>();
                firExpressionRef.bind(firDesugaredAssignmentValueReferenceExpressionInvoke);
                firDesugaredAssignmentValueReferenceExpressionBuilder.setExpressionRef(firExpressionRef);
                KtSourceElement source2 = firDesugaredAssignmentValueReferenceExpressionInvoke.getSource();
                firDesugaredAssignmentValueReferenceExpressionBuilder.setSource(source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE, 0, 0, 6, null) : null);
                firDesugaredAssignmentValueReferenceExpressionInvoke = firDesugaredAssignmentValueReferenceExpressionBuilder.mo6809build();
            }
            firVariableAssignmentBuilder.setLValue(firDesugaredAssignmentValueReferenceExpressionInvoke);
            firVariableAssignmentBuilder.setRValue(rhsExpression);
            CollectionsKt.addAll(firVariableAssignmentBuilder.getAnnotations(), annotations);
            return firVariableAssignmentBuilder.mo6809build();
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder2 = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder2.setDiagnostic(new ConeSyntaxDiagnostic("Inc/dec without operand"));
        return firErrorExpressionBuilder2.mo6809build();
    }

    private final FirSafeCallExpression putAssignmentToSafeCall(FirSafeCallExpression safeCallNonAssignment, KtSourceElement baseSource, FirExpression rhsExpression, List<? extends FirAnnotation> annotations) {
        FirStatement selector = safeCallNonAssignment.getSelector();
        Intrinsics.checkNotNull(selector, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression");
        FirVariableAssignmentBuilder firVariableAssignmentBuilder = new FirVariableAssignmentBuilder();
        firVariableAssignmentBuilder.setSource(baseSource);
        firVariableAssignmentBuilder.setLValue((FirQualifiedAccessExpression) selector);
        firVariableAssignmentBuilder.setRValue(rhsExpression);
        CollectionsKt.addAll(firVariableAssignmentBuilder.getAnnotations(), annotations);
        safeCallNonAssignment.replaceSelector(firVariableAssignmentBuilder.mo6809build());
        return safeCallNonAssignment;
    }

    private final FirStatement generateAugmentedArraySetCall(FirExpression receiver, KtSourceElement baseSource, KtSourceElement arrayAccessSource, FirOperation operation, List<? extends FirAnnotation> annotations, T rhs, Function1<? super T, ? extends FirExpression> convert) {
        FirErrorExpression firErrorExpressionBuildErrorExpression$default;
        if (!(receiver instanceof FirFunctionCall)) {
            throw new IllegalArgumentException(("Array access should be desugared to a function call, but " + receiver + " is found").toString());
        }
        FirAugmentedArraySetCallBuilder firAugmentedArraySetCallBuilder = new FirAugmentedArraySetCallBuilder();
        firAugmentedArraySetCallBuilder.setSource(baseSource);
        firAugmentedArraySetCallBuilder.setOperation(operation);
        firAugmentedArraySetCallBuilder.setLhsGetCall((FirFunctionCall) receiver);
        if (rhs == null || (firErrorExpressionBuildErrorExpression$default = convert.invoke(rhs)) == null) {
            firErrorExpressionBuildErrorExpression$default = FirExpressionUtilKt.buildErrorExpression$default(null, new ConeSyntaxDiagnostic("No value for array set"), null, 4, null);
        }
        firAugmentedArraySetCallBuilder.setRhs(firErrorExpressionBuildErrorExpression$default);
        firAugmentedArraySetCallBuilder.setArrayAccessSource(arrayAccessSource);
        CollectionsKt.addAll(firAugmentedArraySetCallBuilder.getAnnotations(), annotations);
        return firAugmentedArraySetCallBuilder.mo6809build();
    }

    /* JADX INFO: compiled from: AbstractRawFirBuilder.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\u008c\u0001\u0012\u0006\u0010\u0002\u001a\u00028\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u00070\u0006\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r\u0012\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u0011\u0012\u001d\u0010\u0012\u001a\u0019\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00140\u0011¢\u0006\u0002\b\u0015¢\u0006\u0002\u0010\u0016J\u0006\u0010\u0018\u001a\u00020\u0014J\b\u0010\u0019\u001a\u00020\u0014H\u0002J\b\u0010\u001a\u001a\u00020\u0014H\u0002R%\u0010\u0012\u001a\u0019\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00140\u0011¢\u0006\u0002\b\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00028\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0017R \u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder$DataClassMembersGenerator;", "", FirebaseAnalytics.Param.SOURCE, "classBuilder", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "zippedParameters", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "classFqName", "createClassTypeRefWithSourceKind", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "createParameterTypeRefWithSourceKind", "Lkotlin/Function2;", "addValueParameterAnnotations", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirValueParameterBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder;Ljava/lang/Object;Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;Ljava/util/List;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;)V", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, CompilerOptions.GENERATE, "generateComponentFunctions", "generateCopyFunction", "raw-fir.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class DataClassMembersGenerator {
        private final Function2<FirValueParameterBuilder, T, Unit> addValueParameterAnnotations;
        private final FirRegularClassBuilder classBuilder;
        private final FqName classFqName;
        private final Function1<KtFakeSourceElementKind, FirTypeRef> createClassTypeRefWithSourceKind;
        private final Function2<FirProperty, KtFakeSourceElementKind, FirTypeRef> createParameterTypeRefWithSourceKind;
        private final FqName packageFqName;
        private final T source;
        final /* synthetic */ AbstractRawFirBuilder<T> this$0;
        private final List<Pair<T, FirProperty>> zippedParameters;

        /* JADX WARN: Multi-variable type inference failed */
        public DataClassMembersGenerator(AbstractRawFirBuilder abstractRawFirBuilder, T t, FirRegularClassBuilder classBuilder, List<? extends Pair<? extends T, ? extends FirProperty>> zippedParameters, FqName packageFqName, FqName classFqName, Function1<? super KtFakeSourceElementKind, ? extends FirTypeRef> createClassTypeRefWithSourceKind, Function2<? super FirProperty, ? super KtFakeSourceElementKind, ? extends FirTypeRef> createParameterTypeRefWithSourceKind, Function2<? super FirValueParameterBuilder, ? super T, Unit> addValueParameterAnnotations) {
            Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
            Intrinsics.checkNotNullParameter(zippedParameters, "zippedParameters");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(classFqName, "classFqName");
            Intrinsics.checkNotNullParameter(createClassTypeRefWithSourceKind, "createClassTypeRefWithSourceKind");
            Intrinsics.checkNotNullParameter(createParameterTypeRefWithSourceKind, "createParameterTypeRefWithSourceKind");
            Intrinsics.checkNotNullParameter(addValueParameterAnnotations, "addValueParameterAnnotations");
            this.this$0 = abstractRawFirBuilder;
            this.source = t;
            this.classBuilder = classBuilder;
            this.zippedParameters = zippedParameters;
            this.packageFqName = packageFqName;
            this.classFqName = classFqName;
            this.createClassTypeRefWithSourceKind = createClassTypeRefWithSourceKind;
            this.createParameterTypeRefWithSourceKind = createParameterTypeRefWithSourceKind;
            this.addValueParameterAnnotations = addValueParameterAnnotations;
        }

        public final void generate() {
            if (this.classBuilder.getClassKind() != ClassKind.OBJECT) {
                generateComponentFunctions();
                generateCopyFunction();
            }
        }

        private final void generateComponentFunctions() {
            int i = 1;
            for (Pair<T, FirProperty> pair : this.zippedParameters) {
                T tComponent1 = pair.component1();
                FirProperty firPropertyComponent2 = pair.component2();
                if (firPropertyComponent2.getIsVal() || firPropertyComponent2.getIsVar()) {
                    Name nameIdentifier = Name.identifier("component" + i);
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    i++;
                    AbstractRawFirBuilder<T> abstractRawFirBuilder = this.this$0;
                    FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
                    firSimpleFunctionBuilder.setSource(tComponent1 != null ? abstractRawFirBuilder.toFirSourceElement(tComponent1, KtFakeSourceElementKind.DataClassGeneratedMembers.INSTANCE) : null);
                    firSimpleFunctionBuilder.setModuleData(abstractRawFirBuilder.getBaseModuleData());
                    firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.Synthetic.DataClassMember.INSTANCE);
                    firSimpleFunctionBuilder.setReturnTypeRef(UtilsKt.copyWithNewSourceKind(firPropertyComponent2.getReturnTypeRef(), KtFakeSourceElementKind.DataClassGeneratedMembers.INSTANCE));
                    firSimpleFunctionBuilder.setName(nameIdentifier);
                    FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(firPropertyComponent2.getStatus().getVisibility(), Modality.FINAL);
                    firDeclarationStatusImpl.setOperator(true);
                    firSimpleFunctionBuilder.setStatus(firDeclarationStatusImpl);
                    firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(this.packageFqName, this.classFqName, nameIdentifier, null, 8, null)));
                    firSimpleFunctionBuilder.setDispatchReceiverType(abstractRawFirBuilder.currentDispatchReceiverType());
                    FirSimpleFunction firSimpleFunctionMo6809build = firSimpleFunctionBuilder.mo6809build();
                    DeclarationAttributesKt.setComponentFunctionSymbol(firPropertyComponent2, firSimpleFunctionMo6809build.getSymbol());
                    FirDeclarationBuildingUtilsKt.addDeclaration(this.classBuilder, firSimpleFunctionMo6809build);
                }
            }
        }

        private final void generateCopyFunction() {
            FirRegularClassBuilder firRegularClassBuilder = this.classBuilder;
            ClassId classId = new ClassId(this.packageFqName, this.classFqName, false);
            T t = this.source;
            ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = this.this$0.currentDispatchReceiverType();
            List<Pair<T, FirProperty>> list = this.zippedParameters;
            Function1<KtFakeSourceElementKind, FirTypeRef> function1 = this.createClassTypeRefWithSourceKind;
            Function2<FirProperty, KtFakeSourceElementKind, FirTypeRef> function2 = this.createParameterTypeRefWithSourceKind;
            final AbstractRawFirBuilder<T> abstractRawFirBuilder = this.this$0;
            Function2<T, KtFakeSourceElementKind, KtSourceElement> function3 = new Function2<T, KtFakeSourceElementKind, KtSourceElement>() { // from class: org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder$DataClassMembersGenerator$generateCopyFunction$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final KtSourceElement invoke(T t2, KtFakeSourceElementKind kind) {
                    Intrinsics.checkNotNullParameter(kind, "kind");
                    if (t2 != null) {
                        return abstractRawFirBuilder.toFirSourceElement(t2, kind);
                    }
                    return null;
                }
            };
            Function2<FirValueParameterBuilder, T, Unit> function4 = this.addValueParameterAnnotations;
            final AbstractRawFirBuilder<T> abstractRawFirBuilder2 = this.this$0;
            FirDeclarationBuildingUtilsKt.addDeclaration(firRegularClassBuilder, AbstractRawFirBuilderKt.createDataClassCopyFunction(firRegularClassBuilder, classId, t, coneClassLikeTypeCurrentDispatchReceiverType, list, function1, function2, function3, function4, new Function1<T, Boolean>() { // from class: org.jetbrains.kotlin.fir.builder.AbstractRawFirBuilder$DataClassMembersGenerator$generateCopyFunction$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(T t2) {
                    return Boolean.valueOf(abstractRawFirBuilder2.isVararg(t2));
                }
            }));
        }
    }

    protected final void initContainingClassForLocalAttr(FirRegularClass firRegularClass) {
        ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType;
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        if (!firRegularClass.getSymbol().getClassId().isLocal() || (coneClassLikeTypeCurrentDispatchReceiverType = currentDispatchReceiverType()) == null) {
            return;
        }
        ClassMembersKt.setContainingClassForLocalAttr(firRegularClass, coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag());
    }

    protected final void initCompanionObjectSymbolAttr(FirRegularClassBuilder firRegularClassBuilder) {
        T next;
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Iterator<T> it = firRegularClassBuilder.getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = (T) null;
                break;
            }
            next = it.next();
            FirRegularClass firRegularClass = (FirDeclaration) next;
            if ((firRegularClass instanceof FirRegularClass) && firRegularClass.getStatus().isCompanion()) {
                break;
            }
        }
        FirRegularClass firRegularClass2 = next;
        firRegularClassBuilder.setCompanionObjectSymbol(firRegularClass2 != null ? firRegularClass2.getSymbol() : null);
    }

    protected final void initContainingClassAttr(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        ConversionUtilsKt.initContainingClassAttr(firCallableDeclaration, this.context);
    }

    protected final <R> R withDefaultSourceElementKind(KtSourceElementKind newDefault, Function0<? extends R> action) {
        Intrinsics.checkNotNullParameter(newDefault, "newDefault");
        Intrinsics.checkNotNullParameter(action, "action");
        KtSourceElementKind forcedElementSourceKind = getContext().getForcedElementSourceKind();
        getContext().setForcedElementSourceKind(newDefault);
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            getContext().setForcedElementSourceKind(forcedElementSourceKind);
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final FirQualifiedAccessExpression convertFirSelector(FirQualifiedAccessExpression firSelector, KtSourceElement source, FirExpression receiver) {
        Intrinsics.checkNotNullParameter(firSelector, "firSelector");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        if (!(firSelector instanceof FirImplicitInvokeCall)) {
            firSelector.replaceExplicitReceiver(receiver);
            firSelector.replaceSource(source);
            return firSelector;
        }
        FirImplicitInvokeCallBuilder firImplicitInvokeCallBuilder = new FirImplicitInvokeCallBuilder();
        firImplicitInvokeCallBuilder.setSource(source);
        firImplicitInvokeCallBuilder.getAnnotations().addAll(firSelector.getAnnotations());
        firImplicitInvokeCallBuilder.getTypeArguments().addAll(firSelector.getTypeArguments());
        firImplicitInvokeCallBuilder.setExplicitReceiver(firSelector.getExplicitReceiver());
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        firArgumentListBuilder.getArguments().add(receiver);
        firArgumentListBuilder.getArguments().addAll(((FirCall) firSelector).getArgumentList().getArguments());
        firImplicitInvokeCallBuilder.setArgumentList(firArgumentListBuilder.build());
        firImplicitInvokeCallBuilder.setCalleeReference(((FirImplicitInvokeCall) firSelector).getCalleeReference());
        return firImplicitInvokeCallBuilder.mo6809build();
    }

    protected final Name convertValueParameterName(Name safeName, ValueParameterDeclaration valueParameterDeclaration, Function0<String> rawName) {
        Intrinsics.checkNotNullParameter(safeName, "safeName");
        Intrinsics.checkNotNullParameter(valueParameterDeclaration, "valueParameterDeclaration");
        Intrinsics.checkNotNullParameter(rawName, "rawName");
        return ((valueParameterDeclaration == ValueParameterDeclaration.LAMBDA && Intrinsics.areEqual(rawName.invoke(), "_")) || (valueParameterDeclaration == ValueParameterDeclaration.CATCH && Intrinsics.areEqual(safeName.asString(), "_"))) ? SpecialNames.UNDERSCORE_FOR_UNUSED_VAR : safeName;
    }

    /* JADX INFO: compiled from: AbstractRawFirBuilder.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/AbstractRawFirBuilder$ValueParameterDeclaration;", "", "shouldExplicitParameterTypeBePresent", "", "(Ljava/lang/String;IZ)V", "getShouldExplicitParameterTypeBePresent", "()Z", "FUNCTION", "CATCH", "PRIMARY_CONSTRUCTOR", "SETTER", "LAMBDA", "FOR_LOOP", "raw-fir.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ValueParameterDeclaration {
        FUNCTION(true),
        CATCH(true),
        PRIMARY_CONSTRUCTOR(true),
        SETTER(false),
        LAMBDA(false),
        FOR_LOOP(false);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final boolean shouldExplicitParameterTypeBePresent;

        public static EnumEntries<ValueParameterDeclaration> getEntries() {
            return $ENTRIES;
        }

        ValueParameterDeclaration(boolean z) {
            this.shouldExplicitParameterTypeBePresent = z;
        }

        public final boolean getShouldExplicitParameterTypeBePresent() {
            return this.shouldExplicitParameterTypeBePresent;
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0025  */
    public final FirReturnExpression toReturn(FirExpression firExpression, KtSourceElement ktSourceElement, String str, boolean z) {
        KtSourceElement ktSourceElementFakeElement$default;
        FirFunctionTarget firFunctionTarget;
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        FirReturnExpressionBuilder firReturnExpressionBuilder = new FirReturnExpressionBuilder();
        FirFunctionTarget firFunctionTarget2 = null;
        if (z) {
            if (ktSourceElement != null) {
                ktSourceElementFakeElement$default = KtSourceElementKt.realElement(ktSourceElement);
            } else {
                ktSourceElementFakeElement$default = null;
            }
        } else if (ktSourceElement != null) {
            ktSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.ImplicitReturn.FromExpressionBody.INSTANCE, 0, 0, 6, null);
        } else {
            ktSourceElementFakeElement$default = null;
        }
        firReturnExpressionBuilder.setSource(ktSourceElementFakeElement$default);
        firReturnExpressionBuilder.setResult(firExpression);
        if (str == null) {
            List<FirFunctionTarget> firFunctionTargets = this.context.getFirFunctionTargets();
            ListIterator<FirFunctionTarget> listIterator = firFunctionTargets.listIterator(firFunctionTargets.size());
            while (listIterator.hasPrevious()) {
                FirFunctionTarget firFunctionTargetPrevious = listIterator.previous();
                if (!firFunctionTargetPrevious.getIsLambda()) {
                    firFunctionTarget2 = firFunctionTargetPrevious;
                    break;
                }
            }
            FirFunctionTarget firFunctionTarget3 = firFunctionTarget2;
            if (firFunctionTarget3 != null) {
                firFunctionTarget = firFunctionTarget3;
            } else {
                FirFunctionTarget firFunctionTarget4 = new FirFunctionTarget(str, false);
                toReturn$lambda$7$bindToErrorFunction(firFunctionTarget4, ktSourceElement, this, "Cannot bind unlabeled return to a function", DiagnosticKind.ReturnNotAllowed);
                firFunctionTarget = firFunctionTarget4;
            }
            firReturnExpressionBuilder.setTarget(firFunctionTarget);
        } else {
            for (FirFunctionTarget firFunctionTarget5 : CollectionsKt.asReversedMutable(this.context.getFirFunctionTargets())) {
                if (Intrinsics.areEqual(firFunctionTarget5.getLabelName(), str)) {
                    firReturnExpressionBuilder.setTarget(firFunctionTarget5);
                }
            }
            FirFunctionTarget firFunctionTarget6 = new FirFunctionTarget(str, false);
            List<FirLabel> firLabels = this.context.getFirLabels();
            if ((firLabels instanceof Collection) && firLabels.isEmpty()) {
                toReturn$lambda$7$bindToErrorFunction(firFunctionTarget6, ktSourceElement, this, "Cannot bind label " + str + " to a function", DiagnosticKind.UnresolvedLabel);
                break;
            }
            Iterator<T> it = firLabels.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (Intrinsics.areEqual(((FirLabel) it.next()).getName(), str)) {
                        toReturn$lambda$7$bindToErrorFunction(firFunctionTarget6, ktSourceElement, this, "Label " + str + " does not target a function", DiagnosticKind.NotAFunctionLabel);
                        break;
                    }
                } else {
                    toReturn$lambda$7$bindToErrorFunction(firFunctionTarget6, ktSourceElement, this, "Cannot bind label " + str + " to a function", DiagnosticKind.UnresolvedLabel);
                    break;
                }
            }
            firReturnExpressionBuilder.setTarget(firFunctionTarget6);
        }
        return firReturnExpressionBuilder.mo6809build();
    }

    protected final FirResolvedTypeRef toDelegatedSelfType(T t, List<? extends FirTypeParameterRef> typeParameters, FirClassLikeSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(t != null ? toFirSourceElement(t, KtFakeSourceElementKind.ClassSelfTypeRef.INSTANCE) : null);
        ConeClassLikeLookupTag lookupTag = symbol.getLookupTag();
        List<? extends FirTypeParameterRef> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConeTypeParameterTypeImpl(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag(), false, null, 4, null));
        }
        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(lookupTag, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeParameterTypeImpl[0]), false, null, 8, null));
        return firResolvedTypeRefBuilder.mo6809build();
    }

    public final FirExpression toInterpolatingCall(T[] tArr, T t, Function1<? super T, ? extends IElementType> getElementType, Function2<? super T, ? super String, ? extends FirExpression> convertTemplateEntry) {
        FirErrorExpression firErrorExpressionMo6809build;
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(getElementType, "getElementType");
        Intrinsics.checkNotNullParameter(convertTemplateEntry, "convertTemplateEntry");
        FirStringConcatenationCallBuilder firStringConcatenationCallBuilder = new FirStringConcatenationCallBuilder();
        StringBuilder sb = new StringBuilder();
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        int length = tArr.length;
        int i = 0;
        boolean z = false;
        while (true) {
            if (i >= length) {
                break;
            }
            T t2 = tArr[i];
            if (t2 != null) {
                List<FirExpression> arguments = firArgumentListBuilder.getArguments();
                IElementType iElementTypeInvoke = getElementType.invoke(t2);
                if (!(Intrinsics.areEqual(iElementTypeInvoke, KtTokens.OPEN_QUOTE) ? true : Intrinsics.areEqual(iElementTypeInvoke, KtTokens.CLOSING_QUOTE))) {
                    if (Intrinsics.areEqual(iElementTypeInvoke, KtNodeTypes.LITERAL_STRING_TEMPLATE_ENTRY)) {
                        sb.append(getAsText(t2));
                        firErrorExpressionMo6809build = FirConstExpressionBuilderKt.buildConstExpression$default(toFirSourceElement$default(this, t2, null, 1, null), ConstantValueKind.String.INSTANCE, getAsText(t2), null, false, 8, null);
                    } else if (Intrinsics.areEqual(iElementTypeInvoke, KtNodeTypes.ESCAPE_STRING_TEMPLATE_ENTRY)) {
                        sb.append(getUnescapedValue(t2));
                        firErrorExpressionMo6809build = FirConstExpressionBuilderKt.buildConstExpression$default(toFirSourceElement$default(this, t2, null, 1, null), ConstantValueKind.String.INSTANCE, getUnescapedValue(t2), null, false, 8, null);
                    } else {
                        if (Intrinsics.areEqual(iElementTypeInvoke, KtNodeTypes.SHORT_STRING_TEMPLATE_ENTRY) ? true : Intrinsics.areEqual(iElementTypeInvoke, KtNodeTypes.LONG_STRING_TEMPLATE_ENTRY)) {
                            firErrorExpressionMo6809build = convertTemplateEntry.invoke(t2, "Incorrect template argument");
                        } else {
                            FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                            firErrorExpressionBuilder.setSource(toFirSourceElement$default(this, t2, null, 1, null));
                            firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Incorrect template entry: " + getAsText(t2)));
                            firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
                        }
                        z = true;
                    }
                    arguments.add(firErrorExpressionMo6809build);
                }
            }
            i++;
        }
        firStringConcatenationCallBuilder.setArgumentList(firArgumentListBuilder.build());
        firStringConcatenationCallBuilder.setSource(t != null ? toFirSourceElement$default(this, t, null, 1, null) : null);
        if (!z) {
            KtSourceElement source = firStringConcatenationCallBuilder.getSource();
            ConstantValueKind.String string = ConstantValueKind.String.INSTANCE;
            String string2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            return FirConstExpressionBuilderKt.buildConstExpression$default(source, string, string2, null, false, 8, null);
        }
        return firStringConcatenationCallBuilder.mo6809build();
    }

    protected final Pair<FirLabel, KtSourceElement> buildLabelAndErrorSource(String rawName, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(rawName, "rawName");
        Intrinsics.checkNotNullParameter(source, "source");
        FirLabelBuilder firLabelBuilder = new FirLabelBuilder();
        String strUnquoteIdentifier = KtPsiUtil.unquoteIdentifier(rawName);
        Intrinsics.checkNotNullExpressionValue(strUnquoteIdentifier, "unquoteIdentifier(...)");
        firLabelBuilder.setName(strUnquoteIdentifier);
        firLabelBuilder.setSource(source);
        FirLabel firLabelBuild = firLabelBuilder.build();
        return new Pair<>(firLabelBuild, ConversionUtilsKt.isUnderscore(rawName) ? firLabelBuild.getSource() : null);
    }

    protected final FirElement buildExpressionWithErrorLabel(FirElement element, KtSourceElement errorLabelSource, KtSourceElement elementSource) {
        Intrinsics.checkNotNullParameter(elementSource, "elementSource");
        if (element == null) {
            return FirExpressionUtilKt.buildErrorExpression$default(elementSource, new ConeSyntaxDiagnostic("Empty label"), null, 4, null);
        }
        if (errorLabelSource == null) {
            return element;
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setSource(element.getSource());
        firErrorExpressionBuilder.setExpression(element instanceof FirExpression ? (FirExpression) element : null);
        firErrorExpressionBuilder.setDiagnostic(new ConeUnderscoreIsReserved(errorLabelSource));
        return firErrorExpressionBuilder.mo6809build();
    }

    protected final FirErrorProperty buildErrorTopLevelDestructuringDeclaration(KtSourceElement source) {
        Intrinsics.checkNotNullParameter(source, "source");
        FirErrorPropertyBuilder firErrorPropertyBuilder = new FirErrorPropertyBuilder();
        firErrorPropertyBuilder.setSource(source);
        firErrorPropertyBuilder.setModuleData(this.baseModuleData);
        firErrorPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        Name nameSpecial = Name.special("<destructuring>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        firErrorPropertyBuilder.setName(nameSpecial);
        firErrorPropertyBuilder.setDiagnostic(ConeDestructuringDeclarationsOnTopLevel.INSTANCE);
        firErrorPropertyBuilder.setSymbol(new FirErrorPropertySymbol(firErrorPropertyBuilder.getDiagnostic()));
        return firErrorPropertyBuilder.mo6809build();
    }

    protected final FirErrorTypeRef createNoTypeForParameterTypeRef() {
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("No type for parameter", DiagnosticKind.ValueParameterWithNoTypeAnnotation));
        return firErrorTypeRefBuilder.mo6809build();
    }

    private static final <T> void toReturn$lambda$7$bindToErrorFunction(FirFunctionTarget firFunctionTarget, KtSourceElement ktSourceElement, AbstractRawFirBuilder<T> abstractRawFirBuilder, String str, DiagnosticKind diagnosticKind) {
        FirErrorFunctionBuilder firErrorFunctionBuilder = new FirErrorFunctionBuilder();
        firErrorFunctionBuilder.setSource(ktSourceElement);
        firErrorFunctionBuilder.setModuleData(((AbstractRawFirBuilder) abstractRawFirBuilder).baseModuleData);
        firErrorFunctionBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        firErrorFunctionBuilder.setDiagnostic(new ConeSimpleDiagnostic(str, diagnosticKind));
        firErrorFunctionBuilder.setSymbol(new FirErrorFunctionSymbol());
        firFunctionTarget.bind(firErrorFunctionBuilder.mo6809build());
    }

    private static final FirErrorExpression generateConstantExpressionByLiteral$reportIncorrectConstant(KtSourceElement ktSourceElement, String str, DiagnosticKind diagnosticKind) {
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setSource(ktSourceElement);
        firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("Incorrect constant expression: " + str, diagnosticKind));
        return firErrorExpressionBuilder.mo6809build();
    }
}
