package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguouslyResolvedAnnotationFromPlugin;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeCyclicTypeBound;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.ImportingScopesKt;
import org.jetbrains.kotlin.fir.scopes.ScopesKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirMemberTypeParameterScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeWithSubstitutionKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.util.PrivateForInline;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: FirTypeResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¬\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\b\u0016\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001BA\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\u0002\u0010\u000fJ\u0014\u00102\u001a\u0002032\f\u00104\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u000e\u00105\u001a\u0002032\u0006\u00106\u001a\u000207J\u0016\u00108\u001a\u0002092\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;H\u0002J&\u0010=\u001a\u0002092\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020?0BH\u0002J\u000e\u0010C\u001a\u0002092\u0006\u0010D\u001a\u00020\u000eJ\u001a\u0010E\u001a\u00020F2\u0006\u0010D\u001a\u00020\u000e2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0002J\u0010\u0010H\u001a\u0002032\u0006\u0010I\u001a\u00020JH\u0002J\u0010\u0010K\u001a\u0002032\u0006\u0010L\u001a\u00020MH\u0002J\u001a\u0010N\u001a\u00020F2\u0006\u0010O\u001a\u00020P2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010Q\u001a\u00020F2\u0006\u0010R\u001a\u00020S2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010T\u001a\u00020F2\u0006\u0010U\u001a\u00020V2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010W\u001a\u00020F2\u0006\u0010X\u001a\u00020Y2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010Z\u001a\u00020F2\u0006\u0010[\u001a\u00020\\2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u0018\u0010]\u001a\u0002032\u0006\u0010^\u001a\u00020_2\b\u0010G\u001a\u0004\u0018\u00010\u0002J\u001a\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020a2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u000e\u0010c\u001a\u0002032\u0006\u0010b\u001a\u00020aJ\u001a\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020e2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020h2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010j\u001a\u00020\u000b2\u0006\u0010k\u001a\u00020\u000b2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010l\u001a\u00020m2\u0006\u0010n\u001a\u00020o2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010p\u001a\u00020M2\u0006\u0010L\u001a\u00020M2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020r2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010t\u001a\u00020F2\u0006\u0010^\u001a\u00020_2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010u\u001a\u00020v2\u0006\u0010w\u001a\u00020v2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020y2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010{\u001a\u00020|2\u0006\u0010}\u001a\u00020m2\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010~\u001a\u00020F2\u0007\u0010\u007f\u001a\u00030\u0080\u00012\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0016J\u0013\u0010\u0081\u0001\u001a\u0002032\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0002J4\u0010\u0084\u0001\u001a\u0003H\u0085\u0001\"\u0005\b\u0000\u0010\u0085\u00012\u0006\u0010^\u001a\u00020_2\u000f\u0010\u0086\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0085\u00010\u0087\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0088\u0001JH\u0010\u0089\u0001\u001a\u0003H\u0085\u0001\"\u0005\b\u0000\u0010\u0085\u00012\u0006\u0010D\u001a\u00020\u000e2\u0010\b\u0006\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u0002030\u0087\u00012\u0011\b\u0004\u0010\u0086\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0085\u00010\u0087\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u008b\u0001J4\u0010\u008c\u0001\u001a\u0003H\u008d\u0001\"\u0005\b\u0000\u0010\u008d\u00012\u0007\u0010\u008e\u0001\u001a\u00020\u00152\u0011\b\u0004\u0010\u0086\u0001\u001a\n\u0012\u0005\u0012\u0003H\u008d\u00010\u0087\u0001H\u0082\b¢\u0006\u0003\u0010\u008f\u0001J6\u0010\u0090\u0001\u001a\u0003H\u0085\u0001\"\u0005\b\u0000\u0010\u0085\u00012\u0006\u0010k\u001a\u00020\u000b2\u0011\b\u0004\u0010\u0086\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0085\u00010\u0087\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0091\u0001J.\u0010\u0092\u0001\u001a\u0003H\u008d\u0001\"\u0005\b\u0000\u0010\u008d\u00012\u0011\b\u0004\u0010\u0093\u0001\u001a\n\u0012\u0005\u0012\u0003H\u008d\u00010\u0087\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0094\u0001J\u000e\u0010\u0095\u0001\u001a\u000203*\u00030\u0096\u0001H\u0002R\"\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\u0016\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0017\u0010\u0011\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001c\u0010\u0011\u001a\u0004\b\u001d\u0010\u001eR*\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0 8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b!\u0010\u0011\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R4\u0010'\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010 2\u000e\u0010&\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010 @GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010#\"\u0004\b)\u0010%R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R*\u0010,\u001a\b\u0012\u0004\u0012\u00020\t0 8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b-\u0010\u0011\u001a\u0004\b.\u0010#\"\u0004\b/\u0010%R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0097\u0001²\u0006\u0012\u0010\u0098\u0001\u001a\t\u0012\u0004\u0012\u00020P0\u0099\u0001X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirTypeResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractTreeTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "initialScopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "initialCurrentFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "classDeclarationsStack", "Lkotlin/collections/ArrayDeque;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Ljava/util/List;Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lkotlin/collections/ArrayDeque;)V", "getClassDeclarationsStack$annotations", "()V", "getClassDeclarationsStack", "()Lkotlin/collections/ArrayDeque;", "currentDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "currentFile", "getCurrentFile$annotations", "getCurrentFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "setCurrentFile", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;)V", "getScopeSession$annotations", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "scopes", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "getScopes$annotations", "getScopes", "()Lkotlinx/collections/immutable/PersistentList;", "setScopes", "(Lkotlinx/collections/immutable/PersistentList;)V", "<set-?>", "scopesBefore", "getScopesBefore", "setScopesBefore", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "staticScopes", "getStaticScopes$annotations", "getStaticScopes", "setStaticScopes", "typeResolverTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSpecificTypeResolverTransformer;", "addScopes", "", SchemaSymbols.ATTVAL_LIST, "addTypeParametersScope", "firMemberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "annotationShouldBeMovedToField", "", "allowedTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "hasSupertypePathToParameter", "currentTypeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "typeParameter", "visited", "", "removeOuterTypeParameterScope", "firClass", "resolveClassContent", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "data", "resolveConstructedTypeRefForDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "setAccessorTypesByPropertyType", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "transformAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "transformAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "transformAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "transformBackingField", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "transformBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "transformClassTypeParameters", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "transformDelegatedConstructorCall", "transformEnumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "enumEntry", "transformField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "field", "transformFile", "file", "transformImplicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "transformProperty", "transformReceiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "receiverParameter", "transformRegularClass", "transformSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "simpleFunction", "transformTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "typeAlias", "transformTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "typeRef", "transformValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "unboundCyclesInTypeParametersSupertypes", "typeParametersOwner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "withClassDeclarationCleanup", "R", "action", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withClassScopes", "actionInsideStaticScope", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withDeclaration", ExifInterface.GPS_DIRECTION_TRUE, "declaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withFileScope", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withScopeCleanup", "l", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "moveOrDeleteIrrelevantAnnotations", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "resolve", "backingFieldAnnotations", ""}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirTypeResolveTransformer extends FirAbstractTreeTransformer<Object> {
    private final ArrayDeque<FirClass> classDeclarationsStack;
    private FirDeclaration currentDeclaration;
    private FirFile currentFile;
    private final ScopeSession scopeSession;
    private PersistentList<? extends FirScope> scopes;
    private PersistentList<? extends FirScope> scopesBefore;
    private final FirSession session;
    private PersistentList<? extends FirScope> staticScopes;
    private final FirSpecificTypeResolverTransformer typeResolverTransformer;

    /* JADX INFO: compiled from: FirTypeResolveTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[FirAnnotationResolvePhase.values().length];
            try {
                iArr[FirAnnotationResolvePhase.Unresolved.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirAnnotationResolvePhase.CompilerRequiredAnnotations.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirAnnotationResolvePhase.Types.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            $EnumSwitchMapping$1 = new int[AnnotationUseSiteTarget.values().length];
        }
    }

    @PrivateForInline
    public static /* synthetic */ void getClassDeclarationsStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getCurrentFile$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getScopeSession$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getScopes$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getStaticScopes$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public final FirSession getSession() {
        return this.session;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public /* synthetic */ FirTypeResolveTransformer(FirSession firSession, ScopeSession scopeSession, List list, FirFile firFile, ArrayDeque arrayDeque, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, (i & 4) != 0 ? CollectionsKt.emptyList() : list, (i & 8) != 0 ? null : firFile, (i & 16) != 0 ? new ArrayDeque() : arrayDeque);
    }

    public final ArrayDeque<FirClass> getClassDeclarationsStack() {
        return this.classDeclarationsStack;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirTypeResolveTransformer(FirSession session, ScopeSession scopeSession, List<? extends FirScope> initialScopes, FirFile firFile, ArrayDeque<FirClass> classDeclarationsStack) {
        super(FirResolvePhase.TYPES);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(initialScopes, "initialScopes");
        Intrinsics.checkNotNullParameter(classDeclarationsStack, "classDeclarationsStack");
        this.session = session;
        this.scopeSession = scopeSession;
        this.classDeclarationsStack = classDeclarationsStack;
        PersistentList<? extends FirScope> persistentList = ExtensionsKt.toPersistentList(CollectionsKt.asReversed(initialScopes));
        this.scopes = persistentList;
        this.staticScopes = persistentList;
        this.typeResolverTransformer = new FirSpecificTypeResolverTransformer(session, false, false, null, 14, null);
        this.currentFile = firFile;
    }

    public final PersistentList<FirScope> getScopes() {
        return this.scopes;
    }

    public final void setScopes(PersistentList<? extends FirScope> persistentList) {
        Intrinsics.checkNotNullParameter(persistentList, "<set-?>");
        this.scopes = persistentList;
    }

    public final PersistentList<FirScope> getStaticScopes() {
        return this.staticScopes;
    }

    public final void setStaticScopes(PersistentList<? extends FirScope> persistentList) {
        Intrinsics.checkNotNullParameter(persistentList, "<set-?>");
        this.staticScopes = persistentList;
    }

    public final PersistentList<FirScope> getScopesBefore() {
        return this.scopesBefore;
    }

    @PrivateForInline
    public final void setScopesBefore(PersistentList<? extends FirScope> persistentList) {
        this.scopesBefore = persistentList;
    }

    public final FirFile getCurrentFile() {
        return this.currentFile;
    }

    public final void setCurrentFile(FirFile firFile) {
        this.currentFile = firFile;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, Object data) {
        Intrinsics.checkNotNullParameter(file, "file");
        checkSessionConsistency(file);
        setCurrentFile(file);
        PersistentList<FirScope> scopes = getScopes();
        setScopesBefore(scopes);
        PersistentList<FirScope> staticScopes = getStaticScopes();
        addScopes(ImportingScopesKt.createImportingScopes$default(file, getSession(), getScopeSession(), false, 8, null));
        FirFile firFileTransformFile = super.transformFile(file, data);
        setScopes(scopes);
        setStaticScopes(staticScopes);
        return firFileTransformFile;
    }

    public final <R> R withFileScope(FirFile file, Function0<? extends R> action) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(action, "action");
        setCurrentFile(file);
        PersistentList<FirScope> scopes = getScopes();
        setScopesBefore(scopes);
        PersistentList<FirScope> staticScopes = getStaticScopes();
        addScopes(ImportingScopesKt.createImportingScopes$default(file, getSession(), getScopeSession(), false, 8, null));
        R rInvoke = action.invoke();
        setScopes(scopes);
        setStaticScopes(staticScopes);
        return rInvoke;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, Object data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        FirSession firSession = this.session;
        try {
            ArrayDeque<FirClass> classDeclarationsStack = getClassDeclarationsStack();
            classDeclarationsStack.addLast(regularClass);
            try {
                transformClassTypeParameters(regularClass, data);
                return resolveClassContent(regularClass, data);
            } finally {
                classDeclarationsStack.removeLast();
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(regularClass, th);
            throw null;
        }
    }

    public final <R> R withClassDeclarationCleanup(FirRegularClass regularClass, Function0<? extends R> action) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(action, "action");
        ArrayDeque<FirClass> classDeclarationsStack = getClassDeclarationsStack();
        classDeclarationsStack.addLast(regularClass);
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            classDeclarationsStack.removeLast();
            InlineMarker.finallyEnd(1);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousObject(FirAnonymousObject anonymousObject, Object data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        ArrayDeque<FirClass> arrayDeque = this.classDeclarationsStack;
        arrayDeque.addLast(anonymousObject);
        try {
            return resolveClassContent(anonymousObject, data);
        } finally {
            arrayDeque.removeLast();
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirConstructor transformConstructor(FirConstructor constructor, Object data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        FirSession firSession = this.session;
        try {
            PersistentList<FirScope> scopes = getScopes();
            setScopesBefore(scopes);
            PersistentList<FirScope> staticScopes = getStaticScopes();
            addTypeParametersScope(constructor);
            FirDeclaration firDeclarationTransformDeclaration = transformDeclaration(constructor, data);
            Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirConstructor");
            FirConstructor firConstructor = (FirConstructor) firDeclarationTransformDeclaration;
            if (firConstructor.getIsPrimary()) {
                for (FirValueParameter firValueParameter : firConstructor.getValueParameters()) {
                    if (ClassMembersKt.getCorrespondingProperty(firValueParameter) != null) {
                        moveOrDeleteIrrelevantAnnotations(firValueParameter);
                    }
                }
            }
            setScopes(scopes);
            setStaticScopes(staticScopes);
            return firConstructor;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(constructor, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeAlias transformTypeAlias(FirTypeAlias typeAlias, Object data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        FirSession firSession = this.session;
        try {
            PersistentList<FirScope> scopes = getScopes();
            setScopesBefore(scopes);
            PersistentList<FirScope> staticScopes = getStaticScopes();
            addTypeParametersScope(typeAlias);
            FirDeclaration firDeclarationTransformDeclaration = transformDeclaration(typeAlias, data);
            setScopes(scopes);
            setStaticScopes(staticScopes);
            Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeAlias");
            return (FirTypeAlias) firDeclarationTransformDeclaration;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(typeAlias, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirEnumEntry transformEnumEntry(FirEnumEntry enumEntry, Object data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        FirSession firSession = this.session;
        try {
            enumEntry.transformReturnTypeRef((FirTransformer<? super Object>) this, data);
            enumEntry.transformTypeParameters((FirTransformer<? super Object>) this, data);
            enumEntry.transformAnnotations((FirTransformer<? super Object>) this, data);
            return enumEntry;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(enumEntry, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirReceiverParameter transformReceiverParameter(FirReceiverParameter receiverParameter, Object data) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        FirTypeResolveTransformer firTypeResolveTransformer = this;
        return receiverParameter.transformAnnotations((FirTransformer<? super Object>) firTypeResolveTransformer, data).transformTypeRef(firTypeResolveTransformer, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirProperty transformProperty(FirProperty property, Object data) {
        List<FirValueParameter> valueParameters;
        Intrinsics.checkNotNullParameter(property, "property");
        FirSession firSession = this.session;
        try {
            PersistentList<FirScope> scopes = getScopes();
            setScopesBefore(scopes);
            PersistentList<FirScope> staticScopes = getStaticScopes();
            FirDeclaration firDeclaration = this.currentDeclaration;
            try {
                this.currentDeclaration = property;
                addTypeParametersScope(property);
                property.transformTypeParameters((FirTransformer<? super Object>) this, data).transformReturnTypeRef((FirTransformer<? super Object>) this, data).transformReceiverParameter((FirTransformer<? super Object>) this, data).transformContextReceivers(this, data).transformGetter((FirTransformer<? super Object>) this, data).transformSetter((FirTransformer<? super Object>) this, data).transformBackingField((FirTransformer<? super Object>) this, data).transformAnnotations((FirTransformer<? super Object>) this, data);
                if (Intrinsics.areEqual((Object) DeclarationAttributesKt.isFromVararg(property), (Object) true)) {
                    TransformUtilsKt.transformTypeToArrayType(property);
                    FirBackingField backingField = property.getBackingField();
                    if (backingField != null) {
                        TransformUtilsKt.transformTypeToArrayType(backingField);
                    }
                    setAccessorTypesByPropertyType(property);
                }
                if ((property.getReturnTypeRef() instanceof FirResolvedTypeRef) && property.getDelegate() != null) {
                    setAccessorTypesByPropertyType(property);
                } else if (!(property.getReturnTypeRef() instanceof FirResolvedTypeRef) && property.getInitializer() == null) {
                    FirPropertyAccessor getter = property.getGetter();
                    if ((getter != null ? getter.getReturnTypeRef() : null) instanceof FirResolvedTypeRef) {
                        FirPropertyAccessor getter2 = property.getGetter();
                        Intrinsics.checkNotNull(getter2);
                        FirTypeRef returnTypeRef = getter2.getReturnTypeRef();
                        property.replaceReturnTypeRef(UtilsKt.copyWithNewSourceKind(returnTypeRef, KtFakeSourceElementKind.PropertyTypeFromGetterReturnType.INSTANCE));
                        FirBackingField backingField2 = property.getBackingField();
                        if (backingField2 != null) {
                            backingField2.replaceReturnTypeRef(UtilsKt.copyWithNewSourceKind(returnTypeRef, KtFakeSourceElementKind.PropertyTypeFromGetterReturnType.INSTANCE));
                        }
                        FirPropertyAccessor setter = property.getSetter();
                        if (setter != null && (valueParameters = setter.getValueParameters()) != null) {
                            Iterator<T> it = valueParameters.iterator();
                            while (it.hasNext()) {
                                ((FirValueParameter) it.next()).replaceReturnTypeRef(UtilsKt.copyWithNewSourceKind(returnTypeRef, KtFakeSourceElementKind.PropertyTypeFromGetterReturnType.INSTANCE));
                            }
                        }
                    }
                }
                unboundCyclesInTypeParametersSupertypes(property);
                moveOrDeleteIrrelevantAnnotations(property);
                this.currentDeclaration = firDeclaration;
                setScopes(scopes);
                setStaticScopes(staticScopes);
                return property;
            } catch (Throwable th) {
                this.currentDeclaration = firDeclaration;
                throw th;
            }
        } catch (Throwable th2) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(property, th2);
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setAccessorTypesByPropertyType(FirProperty property) {
        List<FirValueParameter> valueParameters;
        FirPropertyAccessor getter = property.getGetter();
        if (getter != null) {
            getter.replaceReturnTypeRef(property.getReturnTypeRef());
        }
        FirPropertyAccessor setter = property.getSetter();
        if (setter == null || (valueParameters = setter.getValueParameters()) == null) {
            return;
        }
        List<FirValueParameter> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            ((FirValueParameter) it.next()).replaceReturnTypeRef(property.getReturnTypeRef());
            arrayList.add(Unit.INSTANCE);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirField transformField(FirField field, Object data) {
        Intrinsics.checkNotNullParameter(field, "field");
        FirSession firSession = this.session;
        try {
            PersistentList<FirScope> scopes = getScopes();
            setScopesBefore(scopes);
            PersistentList<FirScope> staticScopes = getStaticScopes();
            field.transformReturnTypeRef((FirTransformer<? super Object>) this, data).transformAnnotations((FirTransformer<? super Object>) this, data);
            setScopes(scopes);
            setStaticScopes(staticScopes);
            return field;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(field, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBackingField(FirBackingField backingField, Object data) {
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        FirSession firSession = this.session;
        try {
            backingField.transformAnnotations((FirTransformer<? super Object>) this, data);
            return super.transformBackingField(backingField, data);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(backingField, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirSimpleFunction transformSimpleFunction(FirSimpleFunction simpleFunction, Object data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        FirSession firSession = this.session;
        try {
            PersistentList<FirScope> scopes = getScopes();
            setScopesBefore(scopes);
            PersistentList<FirScope> staticScopes = getStaticScopes();
            FirDeclaration firDeclaration = this.currentDeclaration;
            try {
                this.currentDeclaration = simpleFunction;
                addTypeParametersScope(simpleFunction);
                FirAnnotationContainer firAnnotationContainerTransformDeclaration = transformDeclaration(simpleFunction, data);
                Intrinsics.checkNotNull(firAnnotationContainerTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner");
                unboundCyclesInTypeParametersSupertypes((FirTypeParametersOwner) firAnnotationContainerTransformDeclaration);
                KtSourceElement source = firAnnotationContainerTransformDeclaration.getSource();
                if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DataClassGeneratedMembers.INSTANCE) && (firAnnotationContainerTransformDeclaration instanceof FirSimpleFunction) && Intrinsics.areEqual(((FirSimpleFunction) firAnnotationContainerTransformDeclaration).getName(), StandardNames.DATA_CLASS_COPY)) {
                    Iterator<FirValueParameter> it = ((FirSimpleFunction) firAnnotationContainerTransformDeclaration).getValueParameters().iterator();
                    while (it.hasNext()) {
                        moveOrDeleteIrrelevantAnnotations(it.next());
                    }
                }
                this.currentDeclaration = firDeclaration;
                setScopes(scopes);
                setStaticScopes(staticScopes);
                Intrinsics.checkNotNull(firAnnotationContainerTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirSimpleFunction");
                return (FirSimpleFunction) firAnnotationContainerTransformDeclaration;
            } catch (Throwable th) {
                this.currentDeclaration = firDeclaration;
                throw th;
            }
        } catch (Throwable th2) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(simpleFunction, th2);
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void unboundCyclesInTypeParametersSupertypes(FirTypeParameterRefsOwner typeParametersOwner) {
        for (FirTypeParameterRef firTypeParameterRef : typeParametersOwner.getTypeParameters()) {
            if (firTypeParameterRef instanceof FirTypeParameter) {
                FirTypeParameter firTypeParameter = (FirTypeParameter) firTypeParameterRef;
                if (hasSupertypePathToParameter(firTypeParameter, firTypeParameter, new LinkedHashSet())) {
                    FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                    firErrorTypeRefBuilder.setDiagnostic(new ConeCyclicTypeBound(firTypeParameterRef.getSymbol(), ExtensionsKt.toImmutableList(firTypeParameter.getBounds())));
                    firTypeParameter.replaceBounds(CollectionsKt.listOf(firErrorTypeRefBuilder.mo6809build()));
                }
            }
        }
    }

    private final boolean hasSupertypePathToParameter(FirTypeParameter currentTypeParameter, FirTypeParameter typeParameter, Set<FirTypeParameter> visited) {
        FirTypeParameter firTypeParameterHasSupertypePathToParameter$toNextTypeParameter;
        if (!visited.isEmpty() && Intrinsics.areEqual(currentTypeParameter, typeParameter)) {
            return true;
        }
        if (!visited.add(currentTypeParameter)) {
            return false;
        }
        List<FirTypeRef> bounds = currentTypeParameter.getBounds();
        if (!(bounds instanceof Collection) || !bounds.isEmpty()) {
            Iterator<T> it = bounds.iterator();
            while (it.hasNext()) {
                ConeKotlinType coneTypeOrNull = FirTypeUtilsKt.getConeTypeOrNull((FirTypeRef) it.next());
                if ((coneTypeOrNull == null || (firTypeParameterHasSupertypePathToParameter$toNextTypeParameter = hasSupertypePathToParameter$toNextTypeParameter(coneTypeOrNull)) == null) ? false : hasSupertypePathToParameter(firTypeParameterHasSupertypePathToParameter$toNextTypeParameter, typeParameter, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirTypeParameter hasSupertypePathToParameter$toNextTypeParameter(ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeTypeParameterType) {
            return (FirTypeParameter) ((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().getFir();
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return hasSupertypePathToParameter$toNextTypeParameter(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, Object data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        return implicitTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirResolvedTypeRef transformTypeRef(FirTypeRef typeRef, Object data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        FirSpecificTypeResolverTransformer firSpecificTypeResolverTransformer = this.typeResolverTransformer;
        FirFile firFile = this.currentFile;
        FirFile firFile2 = firSpecificTypeResolverTransformer.currentFile;
        firSpecificTypeResolverTransformer.currentFile = firFile;
        try {
            return (FirResolvedTypeRef) typeRef.transform(this.typeResolverTransformer, new ScopeClassDeclaration(CollectionsKt.asReversed(this.scopes), this.classDeclarationsStack, null, this.currentDeclaration, 4, null));
        } finally {
            firSpecificTypeResolverTransformer.currentFile = firFile2;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformValueParameter(FirValueParameter valueParameter, Object data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        FirSession firSession = this.session;
        try {
            FirDeclaration firDeclaration = this.currentDeclaration;
            try {
                this.currentDeclaration = valueParameter;
                valueParameter.transformReturnTypeRef((FirTransformer<? super Object>) this, data);
                valueParameter.transformAnnotations((FirTransformer<? super Object>) this, data);
                TransformUtilsKt.transformVarargTypeToArrayType(valueParameter);
                return valueParameter;
            } finally {
                this.currentDeclaration = firDeclaration;
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(valueParameter, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBlock(FirBlock block, Object data) {
        Intrinsics.checkNotNullParameter(block, "block");
        return block;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotation(FirAnnotation annotation, Object data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, Object data) {
        FirTypeRef firTypeRef;
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        FirSession firSession = this.session;
        try {
            FirTypeRef annotationTypeRef = annotationCall.getAnnotationTypeRef();
            if (annotationTypeRef instanceof FirResolvedTypeRef) {
                int i = WhenMappings.$EnumSwitchMapping$0[annotationCall.getAnnotationResolvePhase().ordinal()];
                if (i == 1) {
                    if (annotationTypeRef instanceof FirErrorTypeRef) {
                        annotationCall.replaceAnnotationResolvePhase(FirAnnotationResolvePhase.Types);
                        return annotationCall;
                    }
                    AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                    throw null;
                }
                if (i == 2) {
                    annotationCall.replaceAnnotationResolvePhase(FirAnnotationResolvePhase.Types);
                    FirTypeRef delegatedTypeRef = ((FirResolvedTypeRef) annotationTypeRef).getDelegatedTypeRef();
                    if (delegatedTypeRef != null && (firTypeRef = (FirTypeRef) FirTransformerUtilKt.transformSingle(delegatedTypeRef, this, data)) != null) {
                        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(annotationTypeRef);
                        ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(firTypeRef);
                        if (!Intrinsics.areEqual(coneType2, coneType)) {
                            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                            firErrorTypeRefBuilder.setSource(annotationTypeRef.getSource());
                            firErrorTypeRefBuilder.setType(coneType);
                            firErrorTypeRefBuilder.setDelegatedTypeRef(((FirResolvedTypeRef) annotationTypeRef).getDelegatedTypeRef());
                            firErrorTypeRefBuilder.setDiagnostic(new ConeAmbiguouslyResolvedAnnotationFromPlugin(coneType, coneType2));
                            annotationCall.replaceAnnotationTypeRef(firErrorTypeRefBuilder.mo6809build());
                        }
                    }
                    return annotationCall;
                }
            } else {
                FirTypeRef firTypeRef2 = (FirTypeRef) FirTransformerUtilKt.transformSingle(annotationTypeRef, this, data);
                annotationCall.replaceAnnotationResolvePhase(FirAnnotationResolvePhase.Types);
                annotationCall.replaceAnnotationTypeRef(firTypeRef2);
            }
            return annotationCall;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(annotationCall, th);
            throw null;
        }
    }

    public final <T> T withScopeCleanup(Function0<? extends T> l) {
        Intrinsics.checkNotNullParameter(l, "l");
        PersistentList<FirScope> scopes = getScopes();
        setScopesBefore(scopes);
        PersistentList<FirScope> staticScopes = getStaticScopes();
        T tInvoke = l.invoke();
        setScopes(scopes);
        setStaticScopes(staticScopes);
        return tInvoke;
    }

    public final void transformDelegatedConstructorCall(FirConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        FirDelegatedConstructorCall delegatedConstructor = constructor.getDelegatedConstructor();
        if (delegatedConstructor != null) {
            resolveConstructedTypeRefForDelegatedConstructorCall(delegatedConstructor);
        }
    }

    public final boolean removeOuterTypeParameterScope(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        return (firClass.getStatus().isInner() || firClass.getSymbol().getClassId().isLocal()) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Object withClassScopes$default(FirTypeResolveTransformer firTypeResolveTransformer, FirClass firClass, Function0 actionInsideStaticScope, Function0 action, int i, Object obj) {
        FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope;
        if (obj == null) {
            if ((i & 2) != 0) {
                actionInsideStaticScope = new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirTypeResolveTransformer.withClassScopes.1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }
                };
            }
            Intrinsics.checkNotNullParameter(firClass, "firClass");
            Intrinsics.checkNotNullParameter(actionInsideStaticScope, "actionInsideStaticScope");
            Intrinsics.checkNotNullParameter(action, "action");
            PersistentList<FirScope> scopes = firTypeResolveTransformer.getScopes();
            firTypeResolveTransformer.setScopesBefore(scopes);
            PersistentList<FirScope> staticScopes = firTypeResolveTransformer.getStaticScopes();
            if (firTypeResolveTransformer.removeOuterTypeParameterScope(firClass)) {
                firTypeResolveTransformer.setScopes(firTypeResolveTransformer.getStaticScopes());
            }
            actionInsideStaticScope.invoke();
            List<ConeClassLikeType> listAsReversed = CollectionsKt.asReversed(SupertypeUtilsKt.lookupSuperTypes$default(firClass, false, true, firTypeResolveTransformer.getSession(), true, (SupertypeSupplier) null, 32, (Object) null));
            ArrayList arrayList = new ArrayList();
            for (ConeClassLikeType coneClassLikeType : listAsReversed) {
                FirContainingNamesAwareScope nestedClassifierScope = ScopesKt.getNestedClassifierScope(coneClassLikeType.getLookupTag(), firTypeResolveTransformer.getSession(), firTypeResolveTransformer.getScopeSession());
                if (nestedClassifierScope != null) {
                    arrayList.add(FirNestedClassifierScopeWithSubstitutionKt.wrapNestedClassifierScopeWithSubstitutionForSuperType(nestedClassifierScope, coneClassLikeType, firTypeResolveTransformer.getSession()));
                }
            }
            FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope2 = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(firTypeResolveTransformer.getSession(), firClass);
            if (firNestedClassifierScopeNestedClassifierScope2 != null) {
                arrayList.add(firNestedClassifierScopeNestedClassifierScope2);
            }
            if (firClass instanceof FirRegularClass) {
                FirRegularClassSymbol companionObjectSymbol = ((FirRegularClass) firClass).getCompanionObjectSymbol();
                FirRegularClass firRegularClass = companionObjectSymbol != null ? (FirRegularClass) companionObjectSymbol.getFir() : null;
                if (firRegularClass != null && (firNestedClassifierScopeNestedClassifierScope = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(firTypeResolveTransformer.getSession(), firRegularClass)) != null) {
                    arrayList.add(firNestedClassifierScopeNestedClassifierScope);
                }
                firTypeResolveTransformer.addScopes(arrayList);
                firTypeResolveTransformer.addTypeParametersScope(firClass);
            } else {
                firTypeResolveTransformer.addScopes(arrayList);
            }
            Object objInvoke = action.invoke();
            firTypeResolveTransformer.setScopes(scopes);
            firTypeResolveTransformer.setStaticScopes(staticScopes);
            return objInvoke;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: withClassScopes");
    }

    private final void resolveConstructedTypeRefForDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall) {
        FirTypeResolveTransformer firTypeResolveTransformer = this;
        delegatedConstructorCall.replaceConstructedTypeRef((FirTypeRef) FirTransformerUtilKt.transformSingle(delegatedConstructorCall.getConstructedTypeRef(), firTypeResolveTransformer, null));
        delegatedConstructorCall.transformCalleeReference((FirTransformer<? super Object>) firTypeResolveTransformer, (Object) null);
    }

    public final void addTypeParametersScope(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "firMemberDeclaration");
        if (firMemberDeclaration.getTypeParameters().isEmpty()) {
            return;
        }
        this.scopes = this.scopes.add(new FirMemberTypeParameterScope(firMemberDeclaration));
    }

    public final void addScopes(List<? extends FirScope> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        PersistentList<? extends FirScope> persistentList = this.scopes;
        boolean z = persistentList == this.staticScopes;
        List<? extends FirScope> list2 = list;
        PersistentList<? extends FirScope> persistentListAddAll = persistentList.addAll((Collection<? extends Object>) list2);
        this.scopes = persistentListAddAll;
        if (!z) {
            persistentListAddAll = this.staticScopes.addAll((Collection<? extends Object>) list2);
        }
        this.staticScopes = persistentListAddAll;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void moveOrDeleteIrrelevantAnnotations(final FirVariable firVariable) {
        FirBackingField backingField;
        if (firVariable.getAnnotations().isEmpty()) {
            return;
        }
        Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirTypeResolveTransformer$moveOrDeleteIrrelevantAnnotations$backingFieldAnnotations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<FirAnnotation> invoke() {
                List<FirAnnotation> annotations;
                List<FirAnnotation> mutableList;
                FirBackingField backingField2 = firVariable.getBackingField();
                return (backingField2 == null || (annotations = backingField2.getAnnotations()) == null || (mutableList = CollectionsKt.toMutableList((Collection) annotations)) == null) ? new ArrayList() : mutableList;
            }
        });
        List<FirAnnotation> annotations = firVariable.getAnnotations();
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (Object obj : annotations) {
            FirAnnotation firAnnotation = (FirAnnotation) obj;
            AnnotationUseSiteTarget useSiteTarget = firAnnotation.getUseSiteTarget();
            boolean zContains = true;
            if ((useSiteTarget == null ? -1 : WhenMappings.$EnumSwitchMapping$1[useSiteTarget.ordinal()]) == -1) {
                Set<AnnotationUseSiteTarget> setUseSiteTargetsFromMetaAnnotation = FirAnnotationUtilsKt.useSiteTargetsFromMetaAnnotation(firAnnotation, this.session);
                if (firVariable instanceof FirValueParameter) {
                    zContains = setUseSiteTargetsFromMetaAnnotation.contains(AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER);
                } else {
                    KtSourceElement source = firVariable.getSource();
                    if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE) && setUseSiteTargetsFromMetaAnnotation.contains(AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER)) {
                        zContains = false;
                    } else if ((firVariable instanceof FirProperty) && firVariable.getBackingField() != null && annotationShouldBeMovedToField(setUseSiteTargetsFromMetaAnnotation)) {
                        moveOrDeleteIrrelevantAnnotations$lambda$39(lazy).add(firAnnotation);
                        z = true;
                        zContains = false;
                    }
                }
            }
            if (zContains) {
                arrayList.add(obj);
            }
        }
        firVariable.replaceAnnotations(arrayList);
        if (!z || (backingField = firVariable.getBackingField()) == null) {
            return;
        }
        backingField.replaceAnnotations(moveOrDeleteIrrelevantAnnotations$lambda$39(lazy));
    }

    private static final List<FirAnnotation> moveOrDeleteIrrelevantAnnotations$lambda$39(Lazy<? extends List<FirAnnotation>> lazy) {
        return lazy.getValue();
    }

    private final boolean annotationShouldBeMovedToField(Set<? extends AnnotationUseSiteTarget> allowedTargets) {
        return (allowedTargets.contains(AnnotationUseSiteTarget.FIELD) || allowedTargets.contains(AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD)) && !allowedTargets.contains(AnnotationUseSiteTarget.PROPERTY);
    }

    public final void transformClassTypeParameters(FirRegularClass regularClass, Object data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        PersistentList<FirScope> scopes = getScopes();
        setScopesBefore(scopes);
        PersistentList<FirScope> staticScopes = getStaticScopes();
        addTypeParametersScope(regularClass);
        Iterator<T> it = regularClass.getTypeParameters().iterator();
        while (it.hasNext()) {
            ((FirTypeParameterRef) it.next()).accept(this, data);
        }
        unboundCyclesInTypeParametersSupertypes(regularClass);
        Unit unit = Unit.INSTANCE;
        setScopes(scopes);
        setStaticScopes(staticScopes);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirStatement resolveClassContent(FirClass firClass, Object data) {
        FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope;
        PersistentList<FirScope> scopes = getScopes();
        setScopesBefore(scopes);
        PersistentList<FirScope> staticScopes = getStaticScopes();
        if (removeOuterTypeParameterScope(firClass)) {
            setScopes(getStaticScopes());
        }
        PersistentList<FirScope> scopes2 = getScopes();
        setScopesBefore(scopes2);
        PersistentList<FirScope> staticScopes2 = getStaticScopes();
        firClass.transformAnnotations((FirTransformer<? super Object>) this, (Object) null);
        boolean z = firClass instanceof FirRegularClass;
        if (z) {
            addTypeParametersScope(firClass);
        }
        List<FirDeclaration> declarations = firClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof FirConstructor) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            transformDelegatedConstructorCall((FirConstructor) it.next());
        }
        Unit unit = Unit.INSTANCE;
        setScopes(scopes2);
        setStaticScopes(staticScopes2);
        List<ConeClassLikeType> listAsReversed = CollectionsKt.asReversed(SupertypeUtilsKt.lookupSuperTypes$default(firClass, false, true, getSession(), true, (SupertypeSupplier) null, 32, (Object) null));
        ArrayList arrayList2 = new ArrayList();
        for (ConeClassLikeType coneClassLikeType : listAsReversed) {
            FirContainingNamesAwareScope nestedClassifierScope = ScopesKt.getNestedClassifierScope(coneClassLikeType.getLookupTag(), getSession(), getScopeSession());
            if (nestedClassifierScope != null) {
                arrayList2.add(FirNestedClassifierScopeWithSubstitutionKt.wrapNestedClassifierScopeWithSubstitutionForSuperType(nestedClassifierScope, coneClassLikeType, getSession()));
            }
        }
        FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope2 = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(getSession(), firClass);
        if (firNestedClassifierScopeNestedClassifierScope2 != null) {
            arrayList2.add(firNestedClassifierScopeNestedClassifierScope2);
        }
        if (z) {
            FirRegularClassSymbol companionObjectSymbol = ((FirRegularClass) firClass).getCompanionObjectSymbol();
            FirRegularClass firRegularClass = companionObjectSymbol != null ? (FirRegularClass) companionObjectSymbol.getFir() : null;
            if (firRegularClass != null && (firNestedClassifierScopeNestedClassifierScope = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(getSession(), firRegularClass)) != null) {
                arrayList2.add(firNestedClassifierScopeNestedClassifierScope);
            }
            addScopes(arrayList2);
            addTypeParametersScope(firClass);
        } else {
            addScopes(arrayList2);
        }
        FirClass firClass2 = (FirClass) transformElement(firClass, data);
        setScopes(scopes);
        setStaticScopes(staticScopes);
        return firClass2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> R withClassScopes(FirClass firClass, Function0<Unit> actionInsideStaticScope, Function0<? extends R> action) {
        FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope;
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        Intrinsics.checkNotNullParameter(actionInsideStaticScope, "actionInsideStaticScope");
        Intrinsics.checkNotNullParameter(action, "action");
        PersistentList<FirScope> scopes = getScopes();
        setScopesBefore(scopes);
        PersistentList<FirScope> staticScopes = getStaticScopes();
        if (removeOuterTypeParameterScope(firClass)) {
            setScopes(getStaticScopes());
        }
        actionInsideStaticScope.invoke();
        List<ConeClassLikeType> listAsReversed = CollectionsKt.asReversed(SupertypeUtilsKt.lookupSuperTypes$default(firClass, false, true, getSession(), true, (SupertypeSupplier) null, 32, (Object) null));
        ArrayList arrayList = new ArrayList();
        for (ConeClassLikeType coneClassLikeType : listAsReversed) {
            FirContainingNamesAwareScope nestedClassifierScope = ScopesKt.getNestedClassifierScope(coneClassLikeType.getLookupTag(), getSession(), getScopeSession());
            if (nestedClassifierScope != null) {
                arrayList.add(FirNestedClassifierScopeWithSubstitutionKt.wrapNestedClassifierScopeWithSubstitutionForSuperType(nestedClassifierScope, coneClassLikeType, getSession()));
            }
        }
        FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope2 = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(getSession(), firClass);
        if (firNestedClassifierScopeNestedClassifierScope2 != null) {
            arrayList.add(firNestedClassifierScopeNestedClassifierScope2);
        }
        if (firClass instanceof FirRegularClass) {
            FirRegularClassSymbol companionObjectSymbol = ((FirRegularClass) firClass).getCompanionObjectSymbol();
            FirRegularClass firRegularClass = companionObjectSymbol != null ? (FirRegularClass) companionObjectSymbol.getFir() : null;
            if (firRegularClass != null && (firNestedClassifierScopeNestedClassifierScope = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(getSession(), firRegularClass)) != null) {
                arrayList.add(firNestedClassifierScopeNestedClassifierScope);
            }
            addScopes(arrayList);
            addTypeParametersScope(firClass);
        } else {
            addScopes(arrayList);
        }
        R rInvoke = action.invoke();
        setScopes(scopes);
        setStaticScopes(staticScopes);
        return rInvoke;
    }
}
