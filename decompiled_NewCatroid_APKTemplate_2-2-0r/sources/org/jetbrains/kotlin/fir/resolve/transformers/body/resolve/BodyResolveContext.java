package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataElement;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.declarations.ScriptScopesKt;
import org.jetbrains.kotlin.fir.declarations.TowerElementsForClass;
import org.jetbrains.kotlin.fir.declarations.TowerElementsForScript;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.resolve.FirCodeFragmentContext;
import org.jetbrains.kotlin.fir.resolve.FirRegularTowerDataContexts;
import org.jetbrains.kotlin.fir.resolve.FirSpecialTowerDataContexts;
import org.jetbrains.kotlin.fir.resolve.FirTowerDataMode;
import org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitExtensionReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.InaccessibleImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.dfa.DataFlowAnalyzerContext;
import org.jetbrains.kotlin.fir.resolve.inference.FirBuilderInferenceSession;
import org.jetbrains.kotlin.fir.resolve.inference.FirCallCompleter;
import org.jetbrains.kotlin.fir.resolve.inference.FirDelegatedPropertyInferenceSession;
import org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.ImportingScopesKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirMemberTypeParameterScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirWhenSubjectImportingScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.util.PrivateForInline;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: BodyResolveContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000°\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u001c\b\u0002\u0010\t\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\n¢\u0006\u0002\u0010\fJ\u001a\u0010`\u001a\u00020a2\b\u0010b\u001a\u0004\u0018\u00010\u001d2\u0006\u0010c\u001a\u00020dH\u0007J\u0010\u0010e\u001a\u00020a2\u0006\u0010f\u001a\u00020gH\u0007J\u0010\u0010h\u001a\u00020a2\u0006\u0010i\u001a\u00020jH\u0007J\u0016\u0010k\u001a\u00020a2\f\u0010l\u001a\b\u0012\u0004\u0012\u00020j0mH\u0007J*\u0010n\u001a\u00020a2\b\u0010o\u001a\u0004\u0018\u00010p2\n\u0010q\u001a\u0006\u0012\u0002\b\u00030r2\n\b\u0002\u0010s\u001a\u0004\u0018\u00010pH\u0007J\u0016\u0010t\u001a\u00020g2\u0006\u0010u\u001a\u00020v2\u0006\u0010w\u001a\u00020xJ\b\u0010y\u001a\u00020aH\u0007J\u001c\u0010z\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u000e\u0010{\u001a\u00020a2\u0006\u0010|\u001a\u00020}J\u000f\u0010~\u001a\u00020a2\u0007\u0010\u007f\u001a\u00030\u0080\u0001J,\u0010\u0081\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001J4\u0010\u0086\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0006\u0010w\u001a\u00020x2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0087\u0001J6\u0010\u0088\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0006\u0010u\u001a\u00020v2\u0006\u0010w\u001a\u00020x2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010\u0089\u0001JF\u0010\u008a\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0006\u0010u\u001a\u00020v2\b\u0010b\u001a\u0004\u0018\u00010\u001d2\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008b\u0001JF\u0010\u008c\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0006\u0010u\u001a\u00020v2\b\u0010b\u001a\u0004\u0018\u00010\u001d2\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008b\u0001JF\u0010\u008d\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0006\u0010u\u001a\u00020v2\b\u0010b\u001a\u0004\u0018\u00010\u001d2\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008b\u0001J,\u0010\u008e\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001J8\u0010\u008f\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010\u0092\u0001JP\u0010\u0093\u0001\u001a\u00020a\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0097\u00012\b\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u001c\u0010\u0083\u0001\u001a\u0017\u0012\u0005\u0012\u00030\u009b\u0001\u0012\u0005\u0012\u0003H\u0082\u00010\u009a\u0001¢\u0006\u0003\b\u009c\u0001H\u0086\bø\u0001\u0000J,\u0010\u009d\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001J\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010gJ\t\u0010\u009f\u0001\u001a\u0004\u0018\u00010gJ\u0012\u0010 \u0001\u001a\u00020a2\u0007\u0010¡\u0001\u001a\u00020PH\u0007J\u001b\u0010¢\u0001\u001a\u00020a2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u0006\u0010w\u001a\u00020xH\u0003J\u000f\u0010£\u0001\u001a\u00020a2\u0006\u0010|\u001a\u00020}J\u0018\u0010¤\u0001\u001a\u00020a2\u0007\u0010¥\u0001\u001a\u00020\u001d2\u0006\u0010w\u001a\u00020xJ\u0010\u0010¦\u0001\u001a\u00020a2\u0007\u0010\u007f\u001a\u00030\u0080\u0001J\u001b\u0010§\u0001\u001a\u00020a2\b\u0010\u0090\u0001\u001a\u00030¨\u00012\u0006\u0010w\u001a\u00020xH\u0007J\u0019\u0010©\u0001\u001a\u00020a2\b\u0010ª\u0001\u001a\u00030«\u00012\u0006\u0010w\u001a\u00020xJ&\u0010¬\u0001\u001a\u00020a2\u001a\u0010\u00ad\u0001\u001a\u0015\u0012\u0004\u0012\u00020g\u0012\u0004\u0012\u00020g0\u009a\u0001¢\u0006\u0003\b\u009c\u0001H\u0083\bJA\u0010®\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0007\u0010\u007f\u001a\u00030\u0080\u00012\u0006\u0010c\u001a\u00020d2\b\u0010¯\u0001\u001a\u00030°\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010±\u0001J6\u0010²\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010³\u0001\u001a\u00030´\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010µ\u0001J>\u0010¶\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010·\u0001\u001a\u00030¸\u00012\u0006\u0010w\u001a\u00020x2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010¹\u0001J@\u0010º\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010»\u0001\u001a\u00030¼\u00012\u0006\u0010c\u001a\u00020d2\u0011\b\u0004\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010½\u0001J5\u0010¾\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0007\u0010¿\u0001\u001a\u00020}2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010À\u0001J4\u0010Á\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0006\u0010u\u001a\u00020v2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010Â\u0001J5\u0010Ã\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0007\u0010Ä\u0001\u001a\u00020\u00132\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0087\bø\u0001\u0000¢\u0006\u0003\u0010Å\u0001J2\u0010Æ\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0007\u0010Ä\u0001\u001a\u00020\u001d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0083\b¢\u0006\u0003\u0010Ç\u0001J5\u0010È\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0007\u0010Ä\u0001\u001a\u00020\u001d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010Ç\u0001J6\u0010É\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010Ê\u0001\u001a\u00030Ë\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010Ì\u0001J<\u0010Í\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0006\u0010)\u001a\u00020(2\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010Î\u0001J4\u0010Ï\u0001\u001a\u0003HÐ\u0001\"\u0005\b\u0000\u0010Ð\u00012\u0006\u00108\u001a\u0002072\u000f\u0010Ñ\u0001\u001a\n\u0012\u0005\u0012\u0003HÐ\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010Ò\u0001J^\u0010Ó\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\t\u0010Ô\u0001\u001a\u0004\u0018\u00010p2\b\u0010Õ\u0001\u001a\u00030Ö\u00012\n\u0010×\u0001\u001a\u0005\u0018\u00010Ø\u00012\u0006\u0010c\u001a\u00020d2\n\b\u0002\u0010s\u001a\u0004\u0018\u00010p2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0083\b¢\u0006\u0003\u0010Ù\u0001J3\u0010Ú\u0001\u001a\u0003HÐ\u0001\"\u0005\b\u0000\u0010Ð\u00012\b\u0010Û\u0001\u001a\u00030´\u00012\u000f\u0010Ü\u0001\u001a\n\u0012\u0005\u0012\u0003HÐ\u00010\u0084\u0001H\u0082\b¢\u0006\u0003\u0010µ\u0001J6\u0010Ý\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010Þ\u0001JN\u0010ß\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010à\u0001\u001a\u00030á\u00012\u0006\u0010c\u001a\u00020d2\n\b\u0002\u0010â\u0001\u001a\u00030ã\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010ä\u0001J7\u0010å\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0007\u0010æ\u0001\u001a\u00020\u001d2\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010ç\u0001J8\u0010è\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010Õ\u0001\u001a\u00030é\u00012\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010ê\u0001J8\u0010ë\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010ì\u0001\u001a\u00030í\u00012\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010î\u0001J8\u0010ï\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010Õ\u0001\u001a\u00030ð\u00012\u0006\u0010c\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010ñ\u0001J>\u0010ò\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010ó\u0001\u001a\u00030¨\u00012\u0006\u0010w\u001a\u00020x2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010ô\u0001J7\u0010õ\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\t\u0010¡\u0001\u001a\u0004\u0018\u00010P2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0087\bø\u0001\u0000¢\u0006\u0003\u0010ö\u0001J,\u0010÷\u0001\u001a\u0003HÐ\u0001\"\u0005\b\u0000\u0010Ð\u00012\u000f\u0010Ü\u0001\u001a\n\u0012\u0005\u0012\u0003HÐ\u00010\u0084\u0001H\u0087\bø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001J5\u0010ø\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0007\u0010ù\u0001\u001a\u00020@2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010ú\u0001J5\u0010û\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u0007\u0010¯\u0001\u001a\u00020U2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0087\bø\u0001\u0000¢\u0006\u0003\u0010ü\u0001J,\u0010ý\u0001\u001a\u0003HÐ\u0001\"\u0005\b\u0000\u0010Ð\u00012\u000f\u0010Ü\u0001\u001a\n\u0012\u0005\u0012\u0003HÐ\u00010\u0084\u0001H\u0087\bø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001J6\u0010þ\u0001\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010Ä\u0001\u001a\u00030ÿ\u00012\u000f\u0010Ü\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0087\bø\u0001\u0000¢\u0006\u0003\u0010\u0080\u0002J>\u0010\u0081\u0002\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010\u0082\u0002\u001a\u00030\u0083\u00022\u0006\u0010w\u001a\u00020x2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0084\u0002J8\u0010\u0085\u0002\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\b\u0010\u0086\u0002\u001a\u00030\u0087\u00022\u0006\u0010w\u001a\u00020x2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001¢\u0006\u0003\u0010\u0088\u0002J,\u0010\u0089\u0002\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001JA\u0010\u008a\u0002\u001a\u0003H\u0082\u0001\"\u0005\b\u0000\u0010\u0082\u00012\n\u0010\u008b\u0002\u001a\u0005\u0018\u00010Ø\u00012\u0007\u0010\u008c\u0002\u001a\u00020d2\u000f\u0010\u0083\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0082\u00010\u0084\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008d\u0002J\u0010\u0010Ô\u0001\u001a\u0004\u0018\u00010p*\u00030Ø\u0001H\u0002J\"\u0010\u008e\u0002\u001a\u000f\u0012\u0004\u0012\u00020g\u0012\u0004\u0012\u00020g0\u008f\u0002*\u00020v2\u0006\u0010w\u001a\u00020xH\u0002J\u0011\u0010\u0090\u0002\u001a\u0005\u0018\u00010\u0091\u0002*\u00030ÿ\u0001H\u0002R\u001b\u0010\r\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R0\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u00172\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR(\u0010\u001e\u001a\u0004\u0018\u00010\u001d2\b\u0010\u0016\u001a\u0004\u0018\u00010\u001d@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R \u0010#\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001a\"\u0004\b%\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R$\u0010)\u001a\u00020(2\u0006\u0010\u0016\u001a\u00020(@GX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0017\u0010.\u001a\b\u0012\u0004\u0012\u0002000/¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0011\u00103\u001a\u0002048F¢\u0006\u0006\u001a\u0004\b5\u00106R$\u00108\u001a\u0002072\u0006\u0010\u0016\u001a\u000207@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R%\u0010\t\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R$\u0010?\u001a\u00020@8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\bA\u0010B\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u001c\u0010I\u001a\u00020J8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\bK\u0010B\u001a\u0004\bL\u0010MR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\bN\u0010\u0011R\u0017\u0010O\u001a\u00020P8F¢\u0006\f\u0012\u0004\bQ\u0010B\u001a\u0004\bR\u0010SR*\u0010V\u001a\u00020U2\u0006\u0010T\u001a\u00020U8F@FX\u0086\u000e¢\u0006\u0012\u0012\u0004\bW\u0010B\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R$\u0010\\\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010]0\u00178\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b^\u0010B\u001a\u0004\b_\u0010\u001a\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0092\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "dataFlowAnalyzerContext", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowAnalyzerContext;", "targetedLocalClasses", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "outerLocalClassForNested", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowAnalyzerContext;Ljava/util/Set;Ljava/util/Map;)V", "anonymousFunctionsAnalyzedInDependentContext", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getAnonymousFunctionsAnalyzedInDependentContext", "()Ljava/util/Set;", "containerIfAny", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getContainerIfAny", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "<set-?>", "Lkotlin/collections/ArrayDeque;", "containers", "getContainers", "()Lkotlin/collections/ArrayDeque;", "setContainers", "(Lkotlin/collections/ArrayDeque;)V", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "containingClass", "getContainingClass", "()Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "setContainingClass", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)V", "containingClassDeclarations", "getContainingClassDeclarations", "setContainingClassDeclarations", "getDataFlowAnalyzerContext", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowAnalyzerContext;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "getFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "setFile", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;)V", "fileImportsScope", "", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "getFileImportsScope", "()Ljava/util/List;", "implicitReceiverStack", "Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "getImplicitReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "inferenceSession", "getInferenceSession", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "setInferenceSession", "(Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;)V", "getOuterLocalClassForNested", "()Ljava/util/Map;", "regularTowerDataContexts", "Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts;", "getRegularTowerDataContexts$annotations", "()V", "getRegularTowerDataContexts", "()Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts;", "setRegularTowerDataContexts", "(Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts;)V", "getReturnTypeCalculator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "specialTowerDataContexts", "Lorg/jetbrains/kotlin/fir/resolve/FirSpecialTowerDataContexts;", "getSpecialTowerDataContexts$annotations", "getSpecialTowerDataContexts", "()Lorg/jetbrains/kotlin/fir/resolve/FirSpecialTowerDataContexts;", "getTargetedLocalClasses", "towerDataContext", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "getTowerDataContext$annotations", "getTowerDataContext", "()Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "value", "Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;", "towerDataMode", "getTowerDataMode$annotations", "getTowerDataMode", "()Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;", "setTowerDataMode", "(Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;)V", "whenSubjectImportingScopes", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirWhenSubjectImportingScope;", "getWhenSubjectImportingScopes$annotations", "getWhenSubjectImportingScopes", "addInaccessibleImplicitReceiverValue", "", "owningClass", "holder", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "addLocalScope", "localScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "addNonLocalTowerDataElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataElement;", "addNonLocalTowerDataElements", "newElements", "", "addReceiver", "name", "Lorg/jetbrains/kotlin/name/Name;", "implicitReceiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "additionalLabelName", "buildSecondaryConstructorParametersScope", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", LineReader.CLEAR, "createSnapshotForLocalClasses", "dropCallableReferenceContext", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "dropContextForAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "forAnnotation", ExifInterface.GPS_DIRECTION_TRUE, "f", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "forBlock", "(Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "forConstructorBody", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "forConstructorParameters", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "forConstructorParametersOrDelegatedConstructorCall", "forDelegatedConstructorCall", "forEnumEntry", "forFunctionBody", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "forPropertyDelegateAccessors", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "callCompleter", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirDelegatedPropertyInferenceSession;", "Lkotlin/ExtensionFunctionType;", "forPropertyInitializer", "getPrimaryConstructorAllParametersScope", "getPrimaryConstructorPureParametersScope", "replaceTowerDataContext", "newContext", "storeBackingField", "storeCallableReferenceContext", "storeClassIfNotNested", "klass", "storeContextForAnonymousFunction", "storeFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "storeVariable", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "updateLastScope", "transform", "withAnonymousFunction", "mode", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withAnonymousFunctionTowerDataContext", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withAnonymousInitializer", "anonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withCallableReferenceTowerDataContext", "access", "(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withConstructor", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withContainer", "declaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withContainerClass", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withContainingClass", "withField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "(Lorg/jetbrains/kotlin/fir/declarations/FirField;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withFile", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withInferenceSession", "R", "block", "(Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLabelAndReceiverType", "labelName", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lorg/jetbrains/kotlin/name/Name;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLambdaBeingAnalyzedInDependentContext", "lambda", "l", "withProperty", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withPropertyAccessor", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "forContracts", "", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;ZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withRegularClass", "regularClass", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withScopesForClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withScopesForCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withScopesForScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "(Lorg/jetbrains/kotlin/fir/declarations/FirScript;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withSimpleFunction", "simpleFunction", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withTemporaryRegularContext", "(Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withTowerDataCleanup", "withTowerDataContexts", "newContexts", "(Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withTowerDataMode", "(Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withTowerDataModeCleanup", "withTypeParametersOf", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withWhenSubjectImportingScope", "withWhenSubjectType", "subjectType", "sessionHolder", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "scopesWithPrimaryConstructorParameters", "Lkotlin/Pair;", "typeParameterScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirMemberTypeParameterScope;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BodyResolveContext {
    private final Set<FirFunctionSymbol<?>> anonymousFunctionsAnalyzedInDependentContext;
    private ArrayDeque<FirDeclaration> containers;
    private FirRegularClass containingClass;
    private ArrayDeque<FirRegularClass> containingClassDeclarations;
    private final DataFlowAnalyzerContext dataFlowAnalyzerContext;
    public FirFile file;
    private final List<FirScope> fileImportsScope;
    private FirInferenceSession inferenceSession;
    private final Map<FirClassLikeSymbol<?>, FirClassLikeSymbol<?>> outerLocalClassForNested;
    private FirRegularTowerDataContexts regularTowerDataContexts;
    private final ReturnTypeCalculator returnTypeCalculator;
    private final FirSpecialTowerDataContexts specialTowerDataContexts;
    private final Set<FirClassLikeDeclaration> targetedLocalClasses;
    private final ArrayDeque<FirWhenSubjectImportingScope> whenSubjectImportingScopes;

    @PrivateForInline
    public static /* synthetic */ void getRegularTowerDataContexts$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getSpecialTowerDataContexts$annotations() {
    }

    public static /* synthetic */ void getTowerDataContext$annotations() {
    }

    public static /* synthetic */ void getTowerDataMode$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getWhenSubjectImportingScopes$annotations() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BodyResolveContext(ReturnTypeCalculator returnTypeCalculator, DataFlowAnalyzerContext dataFlowAnalyzerContext, Set<? extends FirClassLikeDeclaration> targetedLocalClasses, Map<FirClassLikeSymbol<?>, FirClassLikeSymbol<?>> outerLocalClassForNested) {
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        Intrinsics.checkNotNullParameter(dataFlowAnalyzerContext, "dataFlowAnalyzerContext");
        Intrinsics.checkNotNullParameter(targetedLocalClasses, "targetedLocalClasses");
        Intrinsics.checkNotNullParameter(outerLocalClassForNested, "outerLocalClassForNested");
        this.returnTypeCalculator = returnTypeCalculator;
        this.dataFlowAnalyzerContext = dataFlowAnalyzerContext;
        this.targetedLocalClasses = targetedLocalClasses;
        this.outerLocalClassForNested = outerLocalClassForNested;
        this.fileImportsScope = new ArrayList();
        this.regularTowerDataContexts = new FirRegularTowerDataContexts(new FirTowerDataContext(), null, null, null, null, null, null, null, 254, null);
        this.specialTowerDataContexts = new FirSpecialTowerDataContexts();
        this.containers = new ArrayDeque<>();
        this.whenSubjectImportingScopes = new ArrayDeque<>();
        this.inferenceSession = FirInferenceSession.INSTANCE.getDEFAULT();
        this.anonymousFunctionsAnalyzedInDependentContext = new LinkedHashSet();
        this.containingClassDeclarations = new ArrayDeque<>();
    }

    public final ReturnTypeCalculator getReturnTypeCalculator() {
        return this.returnTypeCalculator;
    }

    public final DataFlowAnalyzerContext getDataFlowAnalyzerContext() {
        return this.dataFlowAnalyzerContext;
    }

    public /* synthetic */ BodyResolveContext(ReturnTypeCalculator returnTypeCalculator, DataFlowAnalyzerContext dataFlowAnalyzerContext, Set set, LinkedHashMap linkedHashMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(returnTypeCalculator, dataFlowAnalyzerContext, (i & 4) != 0 ? SetsKt.emptySet() : set, (i & 8) != 0 ? new LinkedHashMap() : linkedHashMap);
    }

    public final Set<FirClassLikeDeclaration> getTargetedLocalClasses() {
        return this.targetedLocalClasses;
    }

    public final Map<FirClassLikeSymbol<?>, FirClassLikeSymbol<?>> getOuterLocalClassForNested() {
        return this.outerLocalClassForNested;
    }

    public final List<FirScope> getFileImportsScope() {
        return this.fileImportsScope;
    }

    public final FirFile getFile() {
        FirFile firFile = this.file;
        if (firFile != null) {
            return firFile;
        }
        Intrinsics.throwUninitializedPropertyAccessException("file");
        return null;
    }

    @PrivateForInline
    public final void setFile(FirFile firFile) {
        Intrinsics.checkNotNullParameter(firFile, "<set-?>");
        this.file = firFile;
    }

    public final FirRegularTowerDataContexts getRegularTowerDataContexts() {
        return this.regularTowerDataContexts;
    }

    public final void setRegularTowerDataContexts(FirRegularTowerDataContexts firRegularTowerDataContexts) {
        Intrinsics.checkNotNullParameter(firRegularTowerDataContexts, "<set-?>");
        this.regularTowerDataContexts = firRegularTowerDataContexts;
    }

    public final FirSpecialTowerDataContexts getSpecialTowerDataContexts() {
        return this.specialTowerDataContexts;
    }

    public final FirTowerDataContext getTowerDataContext() {
        FirTowerDataContext currentContext = this.regularTowerDataContexts.getCurrentContext();
        if (currentContext != null) {
            return currentContext;
        }
        throw new AssertionError("No regular data context found, towerDataMode = " + getTowerDataMode());
    }

    public final FirTowerDataMode getTowerDataMode() {
        return this.regularTowerDataContexts.getActiveMode();
    }

    public final void setTowerDataMode(FirTowerDataMode value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.regularTowerDataContexts = this.regularTowerDataContexts.replaceTowerDataMode(value);
    }

    public final ImplicitReceiverStack getImplicitReceiverStack() {
        return getTowerDataContext().getImplicitReceiverStack();
    }

    public final ArrayDeque<FirDeclaration> getContainers() {
        return this.containers;
    }

    @PrivateForInline
    public final void setContainers(ArrayDeque<FirDeclaration> arrayDeque) {
        Intrinsics.checkNotNullParameter(arrayDeque, "<set-?>");
        this.containers = arrayDeque;
    }

    public final ArrayDeque<FirWhenSubjectImportingScope> getWhenSubjectImportingScopes() {
        return this.whenSubjectImportingScopes;
    }

    public final FirRegularClass getContainingClass() {
        return this.containingClass;
    }

    @PrivateForInline
    public final void setContainingClass(FirRegularClass firRegularClass) {
        this.containingClass = firRegularClass;
    }

    public final FirDeclaration getContainerIfAny() {
        return this.containers.lastOrNull();
    }

    public final FirInferenceSession getInferenceSession() {
        return this.inferenceSession;
    }

    @PrivateForInline
    public final void setInferenceSession(FirInferenceSession firInferenceSession) {
        Intrinsics.checkNotNullParameter(firInferenceSession, "<set-?>");
        this.inferenceSession = firInferenceSession;
    }

    public final Set<FirFunctionSymbol<?>> getAnonymousFunctionsAnalyzedInDependentContext() {
        return this.anonymousFunctionsAnalyzedInDependentContext;
    }

    public final ArrayDeque<FirRegularClass> getContainingClassDeclarations() {
        return this.containingClassDeclarations;
    }

    public final void setContainingClassDeclarations(ArrayDeque<FirRegularClass> arrayDeque) {
        Intrinsics.checkNotNullParameter(arrayDeque, "<set-?>");
        this.containingClassDeclarations = arrayDeque;
    }

    public final <T> T withTowerDataContexts(FirRegularTowerDataContexts newContexts, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(newContexts, "newContexts");
        Intrinsics.checkNotNullParameter(f, "f");
        FirRegularTowerDataContexts regularTowerDataContexts = getRegularTowerDataContexts();
        setRegularTowerDataContexts(newContexts);
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setRegularTowerDataContexts(regularTowerDataContexts);
            InlineMarker.finallyEnd(1);
        }
    }

    @PrivateForInline
    public final <T> T withContainer(FirDeclaration declaration, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(f, "f");
        getContainers().add(declaration);
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            getContainers().removeLast();
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withContainingClass(FirRegularClass declaration, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(f, "f");
        getContainingClassDeclarations().add(declaration);
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            getContainingClassDeclarations().removeLast();
            InlineMarker.finallyEnd(1);
        }
    }

    @PrivateForInline
    public final <R> R withTowerDataCleanup(Function0<? extends R> l) {
        Intrinsics.checkNotNullParameter(l, "l");
        FirTowerDataContext towerDataContext = getTowerDataContext();
        try {
            return l.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            replaceTowerDataContext(towerDataContext);
            InlineMarker.finallyEnd(1);
        }
    }

    @PrivateForInline
    public final <R> R withTowerDataModeCleanup(Function0<? extends R> l) {
        Intrinsics.checkNotNullParameter(l, "l");
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            return l.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
        }
    }

    @PrivateForInline
    public final void replaceTowerDataContext(FirTowerDataContext newContext) {
        Intrinsics.checkNotNullParameter(newContext, "newContext");
        this.regularTowerDataContexts = this.regularTowerDataContexts.replaceCurrentlyActiveContext(newContext);
    }

    @PrivateForInline
    public final void clear() {
        this.specialTowerDataContexts.clear();
        this.dataFlowAnalyzerContext.reset();
    }

    @PrivateForInline
    public final void addNonLocalTowerDataElement(FirTowerDataElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        replaceTowerDataContext(getTowerDataContext().addNonLocalTowerDataElements(CollectionsKt.listOf(element)));
    }

    @PrivateForInline
    public final void addNonLocalTowerDataElements(List<FirTowerDataElement> newElements) {
        Intrinsics.checkNotNullParameter(newElements, "newElements");
        replaceTowerDataContext(getTowerDataContext().addNonLocalTowerDataElements(newElements));
    }

    @PrivateForInline
    public final void addLocalScope(FirLocalScope localScope) {
        Intrinsics.checkNotNullParameter(localScope, "localScope");
        replaceTowerDataContext(getTowerDataContext().addLocalScope(localScope));
    }

    public static /* synthetic */ void addReceiver$default(BodyResolveContext bodyResolveContext, Name name2, ImplicitReceiverValue implicitReceiverValue, Name name3, int i, Object obj) {
        if ((i & 4) != 0) {
            name3 = null;
        }
        bodyResolveContext.addReceiver(name2, implicitReceiverValue, name3);
    }

    @PrivateForInline
    public final void addReceiver(Name name2, ImplicitReceiverValue<?> implicitReceiverValue, Name additionalLabelName) {
        Intrinsics.checkNotNullParameter(implicitReceiverValue, "implicitReceiverValue");
        replaceTowerDataContext(getTowerDataContext().addReceiver(name2, implicitReceiverValue, additionalLabelName));
    }

    @PrivateForInline
    public final <T> T withTypeParametersOf(FirMemberDeclaration declaration, Function0<? extends T> l) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(l, "l");
        if (declaration.getTypeParameters().isEmpty()) {
            return l.invoke();
        }
        FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(declaration);
        FirTowerDataContext towerDataContext = getTowerDataContext();
        try {
            addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
            return l.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            replaceTowerDataContext(towerDataContext);
            InlineMarker.finallyEnd(1);
        }
    }

    private final FirMemberTypeParameterScope typeParameterScope(FirMemberDeclaration firMemberDeclaration) {
        if (firMemberDeclaration.getTypeParameters().isEmpty()) {
            return null;
        }
        return new FirMemberTypeParameterScope(firMemberDeclaration);
    }

    public final FirLocalScope buildSecondaryConstructorParametersScope(FirConstructor constructor, FirSession session) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(session, "session");
        List<FirValueParameter> valueParameters = constructor.getValueParameters();
        FirLocalScope firLocalScope = new FirLocalScope(session);
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            firLocalScope = firLocalScope.storeVariable((FirValueParameter) it.next(), session);
        }
        return firLocalScope;
    }

    @PrivateForInline
    public final void addInaccessibleImplicitReceiverValue(FirRegularClass owningClass, SessionHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (owningClass == null) {
            return;
        }
        addReceiver$default(this, owningClass.getName(), new InaccessibleImplicitReceiverValue(owningClass.getSymbol(), ScopeUtilsKt.defaultType(owningClass), holder.getSession(), holder.getScopeSession(), false, 16, null), null, 4, null);
    }

    public final FirLocalScope getPrimaryConstructorPureParametersScope() {
        return this.regularTowerDataContexts.getPrimaryConstructorPureParametersScope();
    }

    public final FirLocalScope getPrimaryConstructorAllParametersScope() {
        return this.regularTowerDataContexts.getPrimaryConstructorAllParametersScope();
    }

    public final void storeClassIfNotNested(FirRegularClass klass, FirSession session) {
        FirLocalScope firLocalScope;
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(session, "session");
        if ((getContainerIfAny() instanceof FirClass) || (firLocalScope = (FirLocalScope) CollectionsKt.lastOrNull((List) getTowerDataContext().getLocalScopes())) == null) {
            return;
        }
        replaceTowerDataContext(getTowerDataContext().setLastLocalScope(firLocalScope.storeClass(klass, session)));
    }

    public final <R> R withInferenceSession(FirInferenceSession inferenceSession, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        Intrinsics.checkNotNullParameter(block, "block");
        FirInferenceSession inferenceSession2 = getInferenceSession();
        setInferenceSession(inferenceSession);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setInferenceSession(inferenceSession2);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withAnonymousFunctionTowerDataContext(FirAnonymousFunctionSymbol symbol, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataContext anonymousFunctionContext = getSpecialTowerDataContexts().getAnonymousFunctionContext(symbol);
        if (anonymousFunctionContext == null) {
            return f.invoke();
        }
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            FirRegularTowerDataContexts firRegularTowerDataContextsReplaceAndSetActiveRegularContext = getRegularTowerDataContexts().replaceAndSetActiveRegularContext(anonymousFunctionContext);
            FirRegularTowerDataContexts regularTowerDataContexts = getRegularTowerDataContexts();
            setRegularTowerDataContexts(firRegularTowerDataContextsReplaceAndSetActiveRegularContext);
            try {
                T tInvoke = f.invoke();
                InlineMarker.finallyStart(1);
                setRegularTowerDataContexts(regularTowerDataContexts);
                InlineMarker.finallyEnd(1);
                InlineMarker.finallyStart(1);
                setTowerDataMode(towerDataMode);
                return tInvoke;
            } finally {
                InlineMarker.finallyStart(1);
                setRegularTowerDataContexts(regularTowerDataContexts);
                InlineMarker.finallyEnd(1);
            }
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final <T> T withCallableReferenceTowerDataContext(FirCallableReferenceAccess access, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(access, "access");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataContext callableReferenceContext = getSpecialTowerDataContexts().getCallableReferenceContext(access);
        if (callableReferenceContext == null) {
            return f.invoke();
        }
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            FirRegularTowerDataContexts firRegularTowerDataContextsReplaceAndSetActiveRegularContext = getRegularTowerDataContexts().replaceAndSetActiveRegularContext(callableReferenceContext);
            FirRegularTowerDataContexts regularTowerDataContexts = getRegularTowerDataContexts();
            setRegularTowerDataContexts(firRegularTowerDataContextsReplaceAndSetActiveRegularContext);
            try {
                T tInvoke = f.invoke();
                InlineMarker.finallyStart(1);
                setRegularTowerDataContexts(regularTowerDataContexts);
                InlineMarker.finallyEnd(1);
                InlineMarker.finallyStart(1);
                setTowerDataMode(towerDataMode);
                return tInvoke;
            } finally {
                InlineMarker.finallyStart(1);
                setRegularTowerDataContexts(regularTowerDataContexts);
                InlineMarker.finallyEnd(1);
            }
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    @PrivateForInline
    public final <T> T withTemporaryRegularContext(FirTowerDataContext newContext, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        if (newContext == null) {
            return f.invoke();
        }
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            FirRegularTowerDataContexts firRegularTowerDataContextsReplaceAndSetActiveRegularContext = getRegularTowerDataContexts().replaceAndSetActiveRegularContext(newContext);
            FirRegularTowerDataContexts regularTowerDataContexts = getRegularTowerDataContexts();
            setRegularTowerDataContexts(firRegularTowerDataContextsReplaceAndSetActiveRegularContext);
            try {
                T tInvoke = f.invoke();
                InlineMarker.finallyStart(1);
                setRegularTowerDataContexts(regularTowerDataContexts);
                InlineMarker.finallyEnd(1);
                InlineMarker.finallyStart(1);
                setTowerDataMode(towerDataMode);
                return tInvoke;
            } finally {
                InlineMarker.finallyStart(1);
                setRegularTowerDataContexts(regularTowerDataContexts);
                InlineMarker.finallyEnd(1);
            }
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final void dropContextForAnonymousFunction(FirAnonymousFunction anonymousFunction) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        this.specialTowerDataContexts.dropAnonymousFunctionContext(anonymousFunction.getSymbol());
    }

    public final BodyResolveContext createSnapshotForLocalClasses(ReturnTypeCalculator returnTypeCalculator, Set<? extends FirClassLikeDeclaration> targetedLocalClasses) {
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        Intrinsics.checkNotNullParameter(targetedLocalClasses, "targetedLocalClasses");
        BodyResolveContext bodyResolveContext = new BodyResolveContext(returnTypeCalculator, this.dataFlowAnalyzerContext, targetedLocalClasses, this.outerLocalClassForNested);
        bodyResolveContext.setFile(getFile());
        CollectionsKt.addAll(bodyResolveContext.fileImportsScope, this.fileImportsScope);
        bodyResolveContext.specialTowerDataContexts.putAll(this.specialTowerDataContexts);
        bodyResolveContext.containers = this.containers;
        bodyResolveContext.containingClassDeclarations = new ArrayDeque<>(this.containingClassDeclarations);
        bodyResolveContext.containingClass = this.containingClass;
        bodyResolveContext.replaceTowerDataContext(getTowerDataContext());
        bodyResolveContext.anonymousFunctionsAnalyzedInDependentContext.addAll(this.anonymousFunctionsAnalyzedInDependentContext);
        FirInferenceSession firInferenceSession = this.inferenceSession;
        if (firInferenceSession instanceof FirBuilderInferenceSession) {
            bodyResolveContext.inferenceSession = firInferenceSession;
        }
        return bodyResolveContext;
    }

    public final <T> T withFile(FirFile file, SessionHolder holder, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        clear();
        setFile(file);
        List<FirScope> fileImportsScope = getFileImportsScope();
        int size = fileImportsScope.size();
        int i = 0;
        try {
            FirTowerDataContext towerDataContext = getTowerDataContext();
            try {
                List listCreateImportingScopes$default = ImportingScopesKt.createImportingScopes$default(file, holder.getSession(), holder.getScopeSession(), false, 8, null);
                CollectionsKt.addAll(getFileImportsScope(), listCreateImportingScopes$default);
                List list = listCreateImportingScopes$default;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(ImplicitReceiverUtilsKt.asTowerDataElement((FirScope) it.next(), false));
                }
                addNonLocalTowerDataElements(arrayList);
                T tInvoke = f.invoke();
                InlineMarker.finallyStart(1);
                replaceTowerDataContext(towerDataContext);
                InlineMarker.finallyEnd(1);
                InlineMarker.finallyStart(1);
                int size2 = fileImportsScope.size() - size;
                while (i < size2) {
                    fileImportsScope.remove(fileImportsScope.size() - 1);
                    i++;
                }
                return tInvoke;
            } finally {
                InlineMarker.finallyStart(1);
                replaceTowerDataContext(towerDataContext);
                InlineMarker.finallyEnd(1);
            }
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            int size3 = fileImportsScope.size() - size;
            while (i < size3) {
                fileImportsScope.remove(fileImportsScope.size() - 1);
                i++;
            }
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final <T> T withRegularClass(final FirRegularClass regularClass, SessionHolder holder, final Function0<? extends T> f) {
        FirTowerDataMode firTowerDataMode;
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        storeClassIfNotNested(regularClass, holder.getSession());
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            if (!regularClass.getStatus().isInner() && (getContainerIfAny() instanceof FirRegularClass)) {
                if (regularClass.getStatus().isCompanion()) {
                    firTowerDataMode = FirTowerDataMode.COMPANION_OBJECT;
                } else {
                    firTowerDataMode = FirTowerDataMode.NESTED_CLASS;
                }
                setTowerDataMode(firTowerDataMode);
            }
            return (T) withScopesForClass(regularClass, holder, new Function0<T>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext$withRegularClass$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final T invoke() {
                    BodyResolveContext bodyResolveContext = this.this$0;
                    FirRegularClass firRegularClass = regularClass;
                    Function0<T> function0 = f;
                    FirRegularClass containingClass = bodyResolveContext.getContainingClass();
                    bodyResolveContext.getContainers().add(firRegularClass);
                    bodyResolveContext.setContainingClass(firRegularClass);
                    try {
                        return function0.invoke();
                    } finally {
                        bodyResolveContext.getContainers().removeLast();
                        bodyResolveContext.setContainingClass(containingClass);
                    }
                }
            });
        } finally {
            setTowerDataMode(towerDataMode);
        }
    }

    public final <T> T withAnonymousObject(final FirAnonymousObject anonymousObject, SessionHolder holder, final Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        return (T) withScopesForClass(anonymousObject, holder, new Function0<T>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext.withAnonymousObject.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final T invoke() {
                BodyResolveContext bodyResolveContext = BodyResolveContext.this;
                FirAnonymousObject firAnonymousObject = anonymousObject;
                Function0<T> function0 = f;
                bodyResolveContext.getContainers().add(firAnonymousObject);
                try {
                    return function0.invoke();
                } finally {
                    bodyResolveContext.getContainers().removeLast();
                }
            }
        });
    }

    public final <T> T withScopesForClass(FirClass owner, SessionHolder holder, Function0<? extends T> f) {
        Name name2;
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny;
        CallableId callableId;
        FqName className;
        FirTowerDataContext firTowerDataContextAddNonLocalScopesIfNotNull;
        FirDeclaration firDeclaration;
        Pair<FirLocalScope, FirLocalScope> pairScopesWithPrimaryConstructorParameters;
        List<FirDeclaration> declarations;
        T next;
        ClassKind classKind;
        FirTowerDataContext firTowerDataContextAddReceiver$default;
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        boolean z = owner instanceof FirRegularClass;
        FirRegularClass firRegularClass = z ? (FirRegularClass) owner : null;
        if (firRegularClass != null && (nameShortName = firRegularClass.getName()) != null) {
            name2 = nameShortName;
        } else if (owner.getClassKind() != ClassKind.ENUM_ENTRY || (firConstructorSymbolPrimaryConstructorIfAny = DeclarationUtilsKt.primaryConstructorIfAny(owner, holder.getSession())) == null || (callableId = firConstructorSymbolPrimaryConstructorIfAny.getCallableId()) == null || (className = callableId.getClassName()) == null) {
            name2 = null;
        } else {
            Name nameShortName = className.shortName();
            name2 = nameShortName;
        }
        TowerElementsForClass towerElementsForClassCollectTowerDataElementsForClass = ImplicitReceiverUtilsKt.collectTowerDataElementsForClass(holder, owner, ScopeUtilsKt.defaultType(owner));
        FirTowerDataContext firTowerDataContextAddNonLocalTowerDataElements = getTowerDataContext().addNonLocalTowerDataElements(towerElementsForClassCollectTowerDataElementsForClass.getSuperClassesStaticsAndCompanionReceivers());
        FirTowerDataContext firTowerDataContextAddNonLocalScopesIfNotNull2 = firTowerDataContextAddNonLocalTowerDataElements.addNonLocalScopesIfNotNull(towerElementsForClassCollectTowerDataElementsForClass.getCompanionStaticScope(), towerElementsForClassCollectTowerDataElementsForClass.getStaticScope());
        ImplicitReceiverValue<?> companionReceiver = towerElementsForClassCollectTowerDataElementsForClass.getCompanionReceiver();
        FirTowerDataContext firTowerDataContextAddNonLocalScopesIfNotNull3 = companionReceiver == null ? firTowerDataContextAddNonLocalScopesIfNotNull2 : FirTowerDataContext.addReceiver$default(firTowerDataContextAddNonLocalTowerDataElements, null, companionReceiver, null, 4, null).addNonLocalScopesIfNotNull(towerElementsForClassCollectTowerDataElementsForClass.getCompanionStaticScope(), towerElementsForClassCollectTowerDataElementsForClass.getStaticScope());
        FirRegularClass firRegularClass2 = z ? (FirRegularClass) owner : null;
        FirMemberTypeParameterScope firMemberTypeParameterScopeTypeParameterScope = firRegularClass2 != null ? typeParameterScope(firRegularClass2) : null;
        if (firMemberTypeParameterScopeTypeParameterScope != null) {
            FirTowerDataContext firTowerDataContextAddNonLocalTowerDataElements2 = getTowerDataContext().addNonLocalScope(firMemberTypeParameterScopeTypeParameterScope).addNonLocalTowerDataElements(towerElementsForClassCollectTowerDataElementsForClass.getSuperClassesStaticsAndCompanionReceivers());
            ImplicitReceiverValue<?> companionReceiver2 = towerElementsForClassCollectTowerDataElementsForClass.getCompanionReceiver();
            if (companionReceiver2 != null && (firTowerDataContextAddReceiver$default = FirTowerDataContext.addReceiver$default(firTowerDataContextAddNonLocalTowerDataElements2, null, companionReceiver2, null, 4, null)) != null) {
                firTowerDataContextAddNonLocalTowerDataElements2 = firTowerDataContextAddReceiver$default;
            }
            firTowerDataContextAddNonLocalScopesIfNotNull = firTowerDataContextAddNonLocalTowerDataElements2.addNonLocalScopesIfNotNull(towerElementsForClassCollectTowerDataElementsForClass.getCompanionStaticScope(), towerElementsForClassCollectTowerDataElementsForClass.getStaticScope());
        } else {
            firTowerDataContextAddNonLocalScopesIfNotNull = firTowerDataContextAddNonLocalScopesIfNotNull3;
        }
        FirTowerDataContext firTowerDataContextAddContextReceiverGroup = FirTowerDataContext.addReceiver$default(firTowerDataContextAddNonLocalScopesIfNotNull, name2, towerElementsForClassCollectTowerDataElementsForClass.getThisReceiver(), null, 4, null).addContextReceiverGroup(towerElementsForClassCollectTowerDataElementsForClass.getContextReceivers());
        FirRegularClass firRegularClass3 = z ? (FirRegularClass) owner : null;
        if (firRegularClass3 != null && (classKind = firRegularClass3.getClassKind()) != null && classKind.isSingleton()) {
            firTowerDataContextAddNonLocalScopesIfNotNull3 = firTowerDataContextAddContextReceiverGroup;
        }
        FirRegularClass firRegularClass4 = z ? (FirRegularClass) owner : null;
        if (firRegularClass4 == null || (declarations = firRegularClass4.getDeclarations()) == null) {
            firDeclaration = null;
        } else {
            Iterator<T> it = declarations.iterator();
            do {
                if (!it.hasNext()) {
                    next = (T) null;
                    break;
                }
                next = it.next();
            } while (!(((FirDeclaration) next) instanceof FirConstructor));
            firDeclaration = next;
        }
        FirConstructor firConstructor = firDeclaration instanceof FirConstructor ? (FirConstructor) firDeclaration : null;
        if (firConstructor != null && firConstructor.getIsPrimary()) {
            pairScopesWithPrimaryConstructorParameters = scopesWithPrimaryConstructorParameters(firConstructor, holder.getSession());
        } else {
            pairScopesWithPrimaryConstructorParameters = TuplesKt.to(null, null);
        }
        FirRegularTowerDataContexts firRegularTowerDataContexts = new FirRegularTowerDataContexts(firTowerDataContextAddContextReceiverGroup, firTowerDataContextAddNonLocalTowerDataElements, firTowerDataContextAddNonLocalScopesIfNotNull3, firTowerDataContextAddNonLocalScopesIfNotNull2, firTowerDataContextAddNonLocalScopesIfNotNull, firTowerDataContextAddNonLocalScopesIfNotNull, pairScopesWithPrimaryConstructorParameters.component1(), pairScopesWithPrimaryConstructorParameters.component2());
        FirRegularTowerDataContexts regularTowerDataContexts = getRegularTowerDataContexts();
        setRegularTowerDataContexts(firRegularTowerDataContexts);
        try {
            return f.invoke();
        } finally {
            setRegularTowerDataContexts(regularTowerDataContexts);
        }
    }

    public final <T> T withScopesForScript(FirScript owner, SessionHolder holder, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        TowerElementsForScript towerElementsForScriptCollectTowerDataElementsForScript = ScriptScopesKt.collectTowerDataElementsForScript(holder, owner);
        FirTowerDataContext firTowerDataContextAddNonLocalTowerDataElements = getTowerDataContext().addNonLocalTowerDataElements(CollectionsKt.emptyList());
        FirTowerDataContext firTowerDataContextAddNonLocalScopeIfNotNull = firTowerDataContextAddNonLocalTowerDataElements.addNonLocalScopeIfNotNull(towerElementsForScriptCollectTowerDataElementsForScript.getStaticScope());
        List<FirVariable> parameters = owner.getParameters();
        FirLocalScope firLocalScope = new FirLocalScope(holder.getSession());
        Iterator<T> it = parameters.iterator();
        while (it.hasNext()) {
            firLocalScope = firLocalScope.storeVariable((FirVariable) it.next(), holder.getSession());
        }
        FirTowerDataContext firTowerDataContextAddContextReceiverGroup = firTowerDataContextAddNonLocalScopeIfNotNull.addLocalScope(firLocalScope).addContextReceiverGroup(towerElementsForScriptCollectTowerDataElementsForScript.getImplicitReceivers());
        FirRegularTowerDataContexts firRegularTowerDataContexts = new FirRegularTowerDataContexts(firTowerDataContextAddContextReceiverGroup, firTowerDataContextAddNonLocalTowerDataElements, firTowerDataContextAddContextReceiverGroup, firTowerDataContextAddNonLocalScopeIfNotNull, null, null, null, null);
        FirRegularTowerDataContexts regularTowerDataContexts = getRegularTowerDataContexts();
        setRegularTowerDataContexts(firRegularTowerDataContexts);
        try {
            return f.invoke();
        } finally {
            setRegularTowerDataContexts(regularTowerDataContexts);
        }
    }

    public final <T> T withScopesForCodeFragment(FirCodeFragment codeFragment, SessionHolder holder, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        FirCodeFragmentContext codeFragmentContext = org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt.getCodeFragmentContext(codeFragment);
        if (codeFragmentContext == null) {
            throw new IllegalStateException("Context is not set for a code fragment".toString());
        }
        FirTowerDataContext towerDataContext = codeFragmentContext.getTowerDataContext();
        List listComputeImportingScopes$default = ImportingScopesKt.computeImportingScopes$default(getFile(), holder.getSession(), holder.getScopeSession(), false, false, 24, null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listComputeImportingScopes$default, 10));
        Iterator<T> it = listComputeImportingScopes$default.iterator();
        while (it.hasNext()) {
            arrayList.add(ImplicitReceiverUtilsKt.asTowerDataElement((FirScope) it.next(), false));
        }
        FirTowerDataContext firTowerDataContextAddNonLocalTowerDataElements = towerDataContext.addNonLocalTowerDataElements(towerDataContext.getNonLocalTowerDataElements()).addNonLocalTowerDataElements(arrayList);
        Iterator<FirLocalScope> it2 = towerDataContext.getLocalScopes().iterator();
        FirTowerDataContext firTowerDataContextAddLocalScope = firTowerDataContextAddNonLocalTowerDataElements;
        while (it2.hasNext()) {
            firTowerDataContextAddLocalScope = firTowerDataContextAddLocalScope.addLocalScope(it2.next());
        }
        FirRegularTowerDataContexts firRegularTowerDataContexts = new FirRegularTowerDataContexts(firTowerDataContextAddLocalScope, firTowerDataContextAddLocalScope, firTowerDataContextAddLocalScope, firTowerDataContextAddLocalScope, null, null, null, null);
        FirRegularTowerDataContexts regularTowerDataContexts = getRegularTowerDataContexts();
        setRegularTowerDataContexts(firRegularTowerDataContexts);
        try {
            return f.invoke();
        } finally {
            setRegularTowerDataContexts(regularTowerDataContexts);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T withWhenSubjectType(ConeKotlinType subjectType, SessionHolder sessionHolder, Function0<? extends T> f) {
        FirRegularClassSymbol firRegularClassSymbol;
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(sessionHolder, "sessionHolder");
        Intrinsics.checkNotNullParameter(f, "f");
        FirSession session = sessionHolder.getSession();
        boolean zSupportsFeature = FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.ContextSensitiveEnumResolutionInWhen);
        if (zSupportsFeature) {
            ConeClassLikeType coneClassLikeType = subjectType instanceof ConeClassLikeType ? (ConeClassLikeType) subjectType : null;
            if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(lookupTag, session)) == null || ((FirRegularClass) firRegularClassSymbol.getFir()).getClassKind() != ClassKind.ENUM_CLASS) {
                firRegularClassSymbol = null;
            }
            getWhenSubjectImportingScopes().add(firRegularClassSymbol != null ? new FirWhenSubjectImportingScope(firRegularClassSymbol.getClassId(), session, sessionHolder.getScopeSession()) : null);
        }
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            if (zSupportsFeature) {
                getWhenSubjectImportingScopes().removeLast();
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withWhenSubjectImportingScope(Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        FirWhenSubjectImportingScope firWhenSubjectImportingScopeLastOrNull = getWhenSubjectImportingScopes().lastOrNull();
        if (firWhenSubjectImportingScopeLastOrNull == null) {
            return f.invoke();
        }
        FirRegularTowerDataContexts firRegularTowerDataContexts = new FirRegularTowerDataContexts(getTowerDataContext().addNonLocalScope(firWhenSubjectImportingScopeLastOrNull), null, null, null, null, null, null, null, 254, null);
        FirRegularTowerDataContexts regularTowerDataContexts = getRegularTowerDataContexts();
        setRegularTowerDataContexts(firRegularTowerDataContexts);
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setRegularTowerDataContexts(regularTowerDataContexts);
            InlineMarker.finallyEnd(1);
        }
    }

    private final Pair<FirLocalScope, FirLocalScope> scopesWithPrimaryConstructorParameters(FirConstructor firConstructor, FirSession firSession) {
        FirLocalScope firLocalScope = new FirLocalScope(firSession);
        FirLocalScope firLocalScope2 = new FirLocalScope(firSession);
        for (FirValueParameter firValueParameter : firConstructor.getValueParameters()) {
            FirValueParameter firValueParameter2 = firValueParameter;
            firLocalScope2 = firLocalScope2.storeVariable(firValueParameter2, firSession);
            if (ClassMembersKt.getCorrespondingProperty(firValueParameter) == null) {
                firLocalScope = firLocalScope.storeVariable(firValueParameter2, firSession);
            }
        }
        return TuplesKt.to(firLocalScope, firLocalScope2);
    }

    public final <T> T withSimpleFunction(FirSimpleFunction simpleFunction, FirSession session, Function0<? extends T> f) {
        T tInvoke;
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(f, "f");
        if (!(getContainerIfAny() instanceof FirClass)) {
            storeFunction(simpleFunction, session);
        }
        FirSimpleFunction firSimpleFunction = simpleFunction;
        if (firSimpleFunction.getTypeParameters().isEmpty()) {
            getContainers().add(simpleFunction);
            try {
                tInvoke = f.invoke();
            } finally {
                InlineMarker.finallyStart(1);
                getContainers().removeLast();
                InlineMarker.finallyEnd(1);
            }
        } else {
            FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(firSimpleFunction);
            FirTowerDataContext towerDataContext = getTowerDataContext();
            try {
                addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
                getContainers().add(simpleFunction);
                try {
                    tInvoke = f.invoke();
                    InlineMarker.finallyStart(1);
                    getContainers().removeLast();
                    InlineMarker.finallyEnd(1);
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext);
                } finally {
                    InlineMarker.finallyStart(1);
                    getContainers().removeLast();
                    InlineMarker.finallyEnd(1);
                }
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                replaceTowerDataContext(towerDataContext);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        return tInvoke;
    }

    private final Name labelName(ConeKotlinType coneKotlinType) {
        ConeClassifierLookupTag lookupTag;
        ConeLookupTagBasedType coneLookupTagBasedType = coneKotlinType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneKotlinType : null;
        if (coneLookupTagBasedType == null || (lookupTag = coneLookupTagBasedType.getLookupTag()) == null) {
            return null;
        }
        return lookupTag.getName();
    }

    public final <T> T forConstructorBody(FirConstructor constructor, FirSession session, Function0<? extends T> f) {
        T tInvoke;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(f, "f");
        if (constructor.getIsPrimary()) {
            FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
            FirTowerDataMode towerDataMode = getTowerDataMode();
            try {
                setTowerDataMode(firTowerDataMode);
                FirTowerDataContext towerDataContext = getTowerDataContext();
                try {
                    FirLocalScope primaryConstructorAllParametersScope = getPrimaryConstructorAllParametersScope();
                    if (primaryConstructorAllParametersScope != null) {
                        addLocalScope(primaryConstructorAllParametersScope);
                    }
                    tInvoke = f.invoke();
                    replaceTowerDataContext(towerDataContext);
                    setTowerDataMode(towerDataMode);
                } catch (Throwable th) {
                    replaceTowerDataContext(towerDataContext);
                    throw th;
                }
            } catch (Throwable th2) {
                setTowerDataMode(towerDataMode);
                throw th2;
            }
        } else {
            FirTowerDataContext towerDataContext2 = getTowerDataContext();
            try {
                addLocalScope(buildSecondaryConstructorParametersScope(constructor, session));
                tInvoke = f.invoke();
            } finally {
                replaceTowerDataContext(towerDataContext2);
            }
        }
        return tInvoke;
    }

    public final <T> T withAnonymousFunction(FirAnonymousFunction anonymousFunction, SessionHolder holder, ResolutionMode mode, Function0<? extends T> f) {
        ConeKotlinType coneType;
        T tInvoke;
        String name2;
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(f, "f");
        if (mode instanceof ResolutionMode.ContextDependent) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(mode instanceof ResolutionMode.LambdaResolution)) {
            storeContextForAnonymousFunction(anonymousFunction);
        }
        FirTowerDataContext towerDataContext = getTowerDataContext();
        try {
            addLocalScope(new FirLocalScope(holder.getSession()));
            FirReceiverParameter receiverParameter = anonymousFunction.getReceiverParameter();
            FirTypeRef typeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
            FirLabel label = anonymousFunction.getLabel();
            Name nameIdentifier = (label == null || (name2 = label.getName()) == null) ? null : Name.identifier(name2);
            getContainers().add(anonymousFunction);
            if (typeRef != null) {
                try {
                    coneType = FirTypeUtilsKt.getConeType(typeRef);
                } catch (Throwable th) {
                    getContainers().removeLast();
                    throw th;
                }
            } else {
                coneType = null;
            }
            FirTowerDataContext towerDataContext2 = getTowerDataContext();
            try {
                replaceTowerDataContext(getTowerDataContext().addContextReceiverGroup(ImplicitReceiverUtilsKt.createContextReceiverValues(anonymousFunction, holder)));
                if (coneType != null) {
                    ImplicitExtensionReceiverValue implicitExtensionReceiverValue = new ImplicitExtensionReceiverValue(anonymousFunction.getSymbol(), coneType, holder.getSession(), holder.getScopeSession(), false, 16, null);
                    addReceiver(nameIdentifier, implicitExtensionReceiverValue, null);
                    FirInferenceSession inferenceSession = getInferenceSession();
                    FirBuilderInferenceSession firBuilderInferenceSession = inferenceSession instanceof FirBuilderInferenceSession ? (FirBuilderInferenceSession) inferenceSession : null;
                    if (firBuilderInferenceSession != null) {
                        firBuilderInferenceSession.addLambdaImplicitReceiver(implicitExtensionReceiverValue);
                    }
                }
                if ((mode instanceof ResolutionMode.LambdaResolution) && ((ResolutionMode.LambdaResolution) mode).getExpectedReturnTypeRef() == null) {
                    FirAnonymousFunctionSymbol symbol = anonymousFunction.getSymbol();
                    this.anonymousFunctionsAnalyzedInDependentContext.add(symbol);
                    try {
                        tInvoke = f.invoke();
                        this.anonymousFunctionsAnalyzedInDependentContext.remove(symbol);
                    } catch (Throwable th2) {
                        this.anonymousFunctionsAnalyzedInDependentContext.remove(symbol);
                        throw th2;
                    }
                } else {
                    tInvoke = f.invoke();
                }
                replaceTowerDataContext(towerDataContext2);
                getContainers().removeLast();
                replaceTowerDataContext(towerDataContext);
                return tInvoke;
            } catch (Throwable th3) {
                replaceTowerDataContext(towerDataContext2);
                throw th3;
            }
        } catch (Throwable th4) {
            replaceTowerDataContext(towerDataContext);
            throw th4;
        }
    }

    public final void storeContextForAnonymousFunction(FirAnonymousFunction anonymousFunction) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        this.specialTowerDataContexts.storeAnonymousFunctionContext(anonymousFunction.getSymbol(), getTowerDataContext());
    }

    public final <T> T withField(FirField field, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            setTowerDataMode(firTowerDataMode);
            getContainers().add(field);
            try {
                FirTowerDataContext towerDataContext = getTowerDataContext();
                try {
                    FirLocalScope primaryConstructorAllParametersScope = getPrimaryConstructorAllParametersScope();
                    if (primaryConstructorAllParametersScope != null) {
                        addLocalScope(primaryConstructorAllParametersScope);
                    }
                    T tInvoke = f.invoke();
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext);
                    InlineMarker.finallyEnd(1);
                    InlineMarker.finallyStart(1);
                    getContainers().removeLast();
                    InlineMarker.finallyEnd(1);
                    InlineMarker.finallyStart(1);
                    setTowerDataMode(towerDataMode);
                    return tInvoke;
                } finally {
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext);
                    InlineMarker.finallyEnd(1);
                }
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                getContainers().removeLast();
                InlineMarker.finallyEnd(1);
                throw th;
            }
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    public final <T> T forEnumEntry(Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.ENUM_ENTRY;
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            setTowerDataMode(firTowerDataMode);
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T forAnnotation(Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        if (!(getContainerIfAny() instanceof FirRegularClass)) {
            return f.invoke();
        }
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.CLASS_HEADER_ANNOTATIONS;
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            setTowerDataMode(firTowerDataMode);
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withValueParameter(FirValueParameter valueParameter, FirSession session, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(f, "f");
        if (!valueParameter.getName().isSpecial() || !Intrinsics.areEqual(valueParameter.getName(), SpecialNames.UNDERSCORE_FOR_UNUSED_VAR)) {
            storeVariable(valueParameter, session);
        }
        getContainers().add(valueParameter);
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            getContainers().removeLast();
            InlineMarker.finallyEnd(1);
        }
    }

    public static /* synthetic */ Object withPropertyAccessor$default(BodyResolveContext bodyResolveContext, FirProperty firProperty, FirPropertyAccessor firPropertyAccessor, SessionHolder sessionHolder, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return bodyResolveContext.withPropertyAccessor(firProperty, firPropertyAccessor, sessionHolder, z, function0);
    }

    public final <T> T withPropertyAccessor(FirProperty property, FirPropertyAccessor accessor, SessionHolder holder, boolean forContracts, Function0<? extends T> f) {
        T tInvoke;
        ConeKotlinType coneType;
        FirTowerDataContext firTowerDataContext;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        if ((accessor instanceof FirDefaultPropertyAccessor) || accessor.getBody() == null) {
            if (accessor.getIsGetter()) {
                getContainers().add(accessor);
                try {
                    tInvoke = f.invoke();
                } finally {
                    getContainers().removeLast();
                }
            } else {
                FirTowerDataContext towerDataContext = getTowerDataContext();
                try {
                    addLocalScope(new FirLocalScope(holder.getSession()));
                    getContainers().add(accessor);
                    try {
                        tInvoke = f.invoke();
                        getContainers().removeLast();
                        replaceTowerDataContext(towerDataContext);
                    } catch (Throwable th) {
                        getContainers().removeLast();
                        throw th;
                    }
                } catch (Throwable th2) {
                    replaceTowerDataContext(towerDataContext);
                    throw th2;
                }
            }
            return tInvoke;
        }
        FirTowerDataContext towerDataContext2 = getTowerDataContext();
        try {
            FirReceiverParameter receiverParameter = property.getReceiverParameter();
            FirTypeRef typeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
            addLocalScope(new FirLocalScope(holder.getSession()));
            if (!forContracts && typeRef == null && !(property.getReturnTypeRef() instanceof FirImplicitTypeRef) && !property.isLocal() && property.getDelegate() == null) {
                storeBackingField(property, holder.getSession());
            }
            getContainers().add(accessor);
            if (typeRef != null) {
                try {
                    coneType = FirTypeUtilsKt.getConeType(typeRef);
                } catch (Throwable th3) {
                    getContainers().removeLast();
                    throw th3;
                }
            } else {
                coneType = null;
            }
            Name nameLabelName = coneType != null ? labelName(coneType) : null;
            Name name2 = property.getName();
            FirTowerDataContext towerDataContext3 = getTowerDataContext();
            try {
                replaceTowerDataContext(getTowerDataContext().addContextReceiverGroup(ImplicitReceiverUtilsKt.createContextReceiverValues(property, holder)));
                if (coneType != null) {
                    firTowerDataContext = towerDataContext3;
                    try {
                        ImplicitExtensionReceiverValue implicitExtensionReceiverValue = new ImplicitExtensionReceiverValue(property.getSymbol(), coneType, holder.getSession(), holder.getScopeSession(), false, 16, null);
                        addReceiver(name2, implicitExtensionReceiverValue, nameLabelName);
                        FirInferenceSession inferenceSession = getInferenceSession();
                        FirBuilderInferenceSession firBuilderInferenceSession = inferenceSession instanceof FirBuilderInferenceSession ? (FirBuilderInferenceSession) inferenceSession : null;
                        if (firBuilderInferenceSession != null) {
                            firBuilderInferenceSession.addLambdaImplicitReceiver(implicitExtensionReceiverValue);
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        replaceTowerDataContext(firTowerDataContext);
                        throw th;
                    }
                } else {
                    firTowerDataContext = towerDataContext3;
                }
                T tInvoke2 = f.invoke();
                replaceTowerDataContext(firTowerDataContext);
                getContainers().removeLast();
                replaceTowerDataContext(towerDataContext2);
                return tInvoke2;
            } catch (Throwable th5) {
                th = th5;
                firTowerDataContext = towerDataContext3;
            }
        } catch (Throwable th6) {
            replaceTowerDataContext(towerDataContext2);
            throw th6;
        }
    }

    public final <T> void forPropertyDelegateAccessors(FirProperty property, ResolutionContext resolutionContext, FirCallCompleter callCompleter, Function1<? super FirDelegatedPropertyInferenceSession, ? extends T> f) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(callCompleter, "callCompleter");
        Intrinsics.checkNotNullParameter(f, "f");
        FirDelegatedPropertyInferenceSession firDelegatedPropertyInferenceSession = new FirDelegatedPropertyInferenceSession(property, resolutionContext, callCompleter.createPostponedArgumentsAnalyzer(resolutionContext));
        FirInferenceSession inferenceSession = getInferenceSession();
        setInferenceSession(firDelegatedPropertyInferenceSession);
        try {
            f.invoke(firDelegatedPropertyInferenceSession);
        } finally {
            InlineMarker.finallyStart(1);
            setInferenceSession(inferenceSession);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T forConstructorParametersOrDelegatedConstructorCall(FirConstructor constructor, FirRegularClass owningClass, SessionHolder holder, Function0<? extends T> f) {
        T tInvoke;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            setTowerDataMode(firTowerDataMode);
            if (constructor.getIsPrimary()) {
                FirLocalScope primaryConstructorAllParametersScope = getPrimaryConstructorAllParametersScope();
                if (primaryConstructorAllParametersScope == null) {
                    tInvoke = f.invoke();
                } else {
                    FirTowerDataContext towerDataContext = getTowerDataContext();
                    try {
                        addLocalScope(primaryConstructorAllParametersScope);
                        tInvoke = f.invoke();
                        InlineMarker.finallyStart(1);
                        replaceTowerDataContext(towerDataContext);
                        InlineMarker.finallyEnd(1);
                        if (tInvoke == null) {
                            tInvoke = f.invoke();
                        }
                    } finally {
                        InlineMarker.finallyStart(1);
                        replaceTowerDataContext(towerDataContext);
                        InlineMarker.finallyEnd(1);
                    }
                }
            } else {
                addInaccessibleImplicitReceiverValue(owningClass, holder);
                FirTowerDataContext towerDataContext2 = getTowerDataContext();
                try {
                    addLocalScope(buildSecondaryConstructorParametersScope(constructor, holder.getSession()));
                    Iterator<T> it = constructor.getValueParameters().iterator();
                    while (it.hasNext()) {
                        storeVariable((FirValueParameter) it.next(), holder.getSession());
                    }
                    tInvoke = f.invoke();
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext2);
                    InlineMarker.finallyEnd(1);
                } finally {
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext2);
                    InlineMarker.finallyEnd(1);
                }
            }
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            return tInvoke;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final void storeCallableReferenceContext(FirCallableReferenceAccess callableReferenceAccess) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        this.specialTowerDataContexts.storeCallableReferenceContext(callableReferenceAccess, getTowerDataContext().createSnapshot());
    }

    public final void dropCallableReferenceContext(FirCallableReferenceAccess callableReferenceAccess) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        this.specialTowerDataContexts.dropCallableReferenceContext(callableReferenceAccess);
    }

    public final <T> T withWhenExpression(FirWhenExpression whenExpression, FirSession session, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(f, "f");
        if (whenExpression.getSubjectVariable() == null) {
            return f.invoke();
        }
        FirTowerDataContext towerDataContext = getTowerDataContext();
        try {
            addLocalScope(new FirLocalScope(session));
            return f.invoke();
        } finally {
            replaceTowerDataContext(towerDataContext);
        }
    }

    @PrivateForInline
    public final <T> T withTowerDataMode(FirTowerDataMode mode, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            setTowerDataMode(mode);
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
        }
    }

    @PrivateForInline
    public final void storeFunction(FirSimpleFunction function, FirSession session) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(session, "session");
        FirLocalScope firLocalScope = (FirLocalScope) CollectionsKt.lastOrNull((List) getTowerDataContext().getLocalScopes());
        if (firLocalScope == null) {
            return;
        }
        replaceTowerDataContext(getTowerDataContext().setLastLocalScope(firLocalScope.storeFunction(function, session)));
    }

    @PrivateForInline
    private final void storeBackingField(FirProperty property, FirSession session) {
        FirLocalScope firLocalScope = (FirLocalScope) CollectionsKt.lastOrNull((List) getTowerDataContext().getLocalScopes());
        if (firLocalScope == null) {
            return;
        }
        replaceTowerDataContext(getTowerDataContext().setLastLocalScope(firLocalScope.storeBackingField(property, session)));
    }

    public final void storeVariable(FirVariable variable, FirSession session) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(session, "session");
        FirLocalScope firLocalScope = (FirLocalScope) CollectionsKt.lastOrNull((List) getTowerDataContext().getLocalScopes());
        if (firLocalScope == null) {
            return;
        }
        replaceTowerDataContext(getTowerDataContext().setLastLocalScope(firLocalScope.storeVariable(variable, session)));
    }

    public final <T> T forFunctionBody(FirFunction function, SessionHolder holder, Function0<? extends T> f) {
        T tInvoke;
        FirTowerDataContext firTowerDataContext;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataContext towerDataContext = getTowerDataContext();
        try {
            addLocalScope(new FirLocalScope(holder.getSession()));
            if (function instanceof FirSimpleFunction) {
                Iterator<FirValueParameter> it = function.getValueParameters().iterator();
                while (it.hasNext()) {
                    storeVariable(it.next(), holder.getSession());
                }
                FirReceiverParameter receiverParameter = function.getReceiverParameter();
                FirTypeRef typeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
                ConeKotlinType coneType = typeRef != null ? FirTypeUtilsKt.getConeType(typeRef) : null;
                Name nameLabelName = coneType != null ? labelName(coneType) : null;
                Name name2 = ((FirSimpleFunction) function).getName();
                FirTowerDataContext towerDataContext2 = getTowerDataContext();
                try {
                    replaceTowerDataContext(getTowerDataContext().addContextReceiverGroup(ImplicitReceiverUtilsKt.createContextReceiverValues(function, holder)));
                    if (coneType != null) {
                        firTowerDataContext = towerDataContext2;
                        try {
                            ImplicitExtensionReceiverValue implicitExtensionReceiverValue = new ImplicitExtensionReceiverValue(function.getSymbol(), coneType, holder.getSession(), holder.getScopeSession(), false, 16, null);
                            addReceiver(name2, implicitExtensionReceiverValue, nameLabelName);
                            FirInferenceSession inferenceSession = getInferenceSession();
                            FirBuilderInferenceSession firBuilderInferenceSession = inferenceSession instanceof FirBuilderInferenceSession ? (FirBuilderInferenceSession) inferenceSession : null;
                            if (firBuilderInferenceSession != null) {
                                firBuilderInferenceSession.addLambdaImplicitReceiver(implicitExtensionReceiverValue);
                            }
                        } catch (Throwable th) {
                            th = th;
                            replaceTowerDataContext(firTowerDataContext);
                            throw th;
                        }
                    } else {
                        firTowerDataContext = towerDataContext2;
                    }
                    tInvoke = f.invoke();
                    replaceTowerDataContext(firTowerDataContext);
                } catch (Throwable th2) {
                    th = th2;
                    firTowerDataContext = towerDataContext2;
                }
            } else {
                tInvoke = f.invoke();
            }
            replaceTowerDataContext(towerDataContext);
            return tInvoke;
        } catch (Throwable th3) {
            replaceTowerDataContext(towerDataContext);
            throw th3;
        }
    }

    public final <T> T withAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, FirSession session, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataContext towerDataContext = getTowerDataContext();
        try {
            FirLocalScope primaryConstructorPureParametersScope = getPrimaryConstructorPureParametersScope();
            if (primaryConstructorPureParametersScope != null) {
                addLocalScope(primaryConstructorPureParametersScope);
            }
            addLocalScope(new FirLocalScope(session));
            getContainers().add(anonymousInitializer);
            try {
                T tInvoke = f.invoke();
                InlineMarker.finallyStart(1);
                getContainers().removeLast();
                InlineMarker.finallyEnd(1);
                InlineMarker.finallyStart(1);
                replaceTowerDataContext(towerDataContext);
                return tInvoke;
            } finally {
                InlineMarker.finallyStart(1);
                getContainers().removeLast();
                InlineMarker.finallyEnd(1);
            }
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            replaceTowerDataContext(towerDataContext);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final <T> T withProperty(FirProperty property, Function0<? extends T> f) {
        T tInvoke;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(f, "f");
        FirProperty firProperty = property;
        if (firProperty.getTypeParameters().isEmpty()) {
            getContainers().add(property);
            try {
                tInvoke = f.invoke();
            } finally {
                InlineMarker.finallyStart(1);
                getContainers().removeLast();
                InlineMarker.finallyEnd(1);
            }
        } else {
            FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(firProperty);
            FirTowerDataContext towerDataContext = getTowerDataContext();
            try {
                addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
                getContainers().add(property);
                try {
                    tInvoke = f.invoke();
                    InlineMarker.finallyStart(1);
                    getContainers().removeLast();
                    InlineMarker.finallyEnd(1);
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext);
                } finally {
                    InlineMarker.finallyStart(1);
                    getContainers().removeLast();
                    InlineMarker.finallyEnd(1);
                }
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                replaceTowerDataContext(towerDataContext);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        return tInvoke;
    }

    public final <T> T forPropertyInitializer(Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataContext towerDataContext = getTowerDataContext();
        try {
            FirLocalScope primaryConstructorPureParametersScope = getPrimaryConstructorPureParametersScope();
            if (primaryConstructorPureParametersScope != null) {
                addLocalScope(primaryConstructorPureParametersScope);
            }
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            replaceTowerDataContext(towerDataContext);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withConstructor(FirConstructor constructor, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(f, "f");
        getContainers().add(constructor);
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            getContainers().removeLast();
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T forConstructorParameters(FirConstructor constructor, FirRegularClass owningClass, SessionHolder holder, Function0<? extends T> f) {
        T tInvoke;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            setTowerDataMode(firTowerDataMode);
            if (constructor.getIsPrimary()) {
                FirLocalScope primaryConstructorAllParametersScope = getPrimaryConstructorAllParametersScope();
                if (primaryConstructorAllParametersScope != null) {
                    FirTowerDataContext towerDataContext = getTowerDataContext();
                    try {
                        addLocalScope(primaryConstructorAllParametersScope);
                        tInvoke = f.invoke();
                        InlineMarker.finallyStart(1);
                        replaceTowerDataContext(towerDataContext);
                        InlineMarker.finallyEnd(1);
                        if (tInvoke == null) {
                            tInvoke = f.invoke();
                        }
                    } finally {
                        InlineMarker.finallyStart(1);
                        replaceTowerDataContext(towerDataContext);
                        InlineMarker.finallyEnd(1);
                    }
                } else {
                    tInvoke = f.invoke();
                }
            } else {
                addInaccessibleImplicitReceiverValue(owningClass, holder);
                FirTowerDataContext towerDataContext2 = getTowerDataContext();
                try {
                    addLocalScope(buildSecondaryConstructorParametersScope(constructor, holder.getSession()));
                    Iterator<T> it = constructor.getValueParameters().iterator();
                    while (it.hasNext()) {
                        storeVariable((FirValueParameter) it.next(), holder.getSession());
                    }
                    tInvoke = f.invoke();
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext2);
                    InlineMarker.finallyEnd(1);
                } finally {
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext2);
                    InlineMarker.finallyEnd(1);
                }
            }
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            return tInvoke;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final <T> T forDelegatedConstructorCall(FirConstructor constructor, FirRegularClass owningClass, SessionHolder holder, Function0<? extends T> f) {
        T tInvoke;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
        FirTowerDataMode towerDataMode = getTowerDataMode();
        try {
            setTowerDataMode(firTowerDataMode);
            if (constructor.getIsPrimary()) {
                FirLocalScope primaryConstructorAllParametersScope = getPrimaryConstructorAllParametersScope();
                if (primaryConstructorAllParametersScope != null) {
                    FirTowerDataContext towerDataContext = getTowerDataContext();
                    try {
                        addLocalScope(primaryConstructorAllParametersScope);
                        tInvoke = f.invoke();
                        InlineMarker.finallyStart(1);
                        replaceTowerDataContext(towerDataContext);
                        InlineMarker.finallyEnd(1);
                        if (tInvoke == null) {
                            tInvoke = f.invoke();
                        }
                    } finally {
                        InlineMarker.finallyStart(1);
                        replaceTowerDataContext(towerDataContext);
                        InlineMarker.finallyEnd(1);
                    }
                } else {
                    tInvoke = f.invoke();
                }
            } else {
                addInaccessibleImplicitReceiverValue(owningClass, holder);
                FirTowerDataContext towerDataContext2 = getTowerDataContext();
                try {
                    addLocalScope(buildSecondaryConstructorParametersScope(constructor, holder.getSession()));
                    Iterator<T> it = constructor.getValueParameters().iterator();
                    while (it.hasNext()) {
                        storeVariable((FirValueParameter) it.next(), holder.getSession());
                    }
                    tInvoke = f.invoke();
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext2);
                    InlineMarker.finallyEnd(1);
                } finally {
                    InlineMarker.finallyStart(1);
                    replaceTowerDataContext(towerDataContext2);
                    InlineMarker.finallyEnd(1);
                }
            }
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            return tInvoke;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            setTowerDataMode(towerDataMode);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final <T> T forBlock(FirSession session, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(f, "f");
        FirTowerDataContext towerDataContext = getTowerDataContext();
        try {
            addLocalScope(new FirLocalScope(session));
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            replaceTowerDataContext(towerDataContext);
            InlineMarker.finallyEnd(1);
        }
    }
}
