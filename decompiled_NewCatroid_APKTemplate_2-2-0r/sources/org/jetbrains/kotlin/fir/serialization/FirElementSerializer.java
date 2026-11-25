package org.jetbrains.kotlin.fir.serialization;

import androidx.exifinterface.media.ExifInterface;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.Implementation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.constant.EnumValue;
import org.jetbrains.kotlin.constant.IntValue;
import org.jetbrains.kotlin.constant.StringValue;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
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
import org.jetbrains.kotlin.fir.declarations.FirValueClassRepresentationKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt;
import org.jetbrains.kotlin.fir.declarations.comparators.FirCallableDeclarationComparator;
import org.jetbrains.kotlin.fir.declarations.comparators.FirMemberDeclarationComparator;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.deserialization.ProtoEnumFlagsUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationArgumentMappingBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt;
import org.jetbrains.kotlin.fir.expressions.impl.FirEmptyAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.FirTypeAttributeExtension;
import org.jetbrains.kotlin.fir.extensions.FirTypeAttributeExtensionKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope;
import org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider;
import org.jetbrains.kotlin.fir.serialization.constant.FirToConstantValueTransformer;
import org.jetbrains.kotlin.fir.serialization.constant.FirToConstantValueTransformerData;
import org.jetbrains.kotlin.fir.serialization.constant.FirToConstantValueTransformerKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeAttribute;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttribute;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNullableAnyTypeRef;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.metadata.deserialization.VersionSpecificBehaviorKt;
import org.jetbrains.kotlin.metadata.serialization.Interner;
import org.jetbrains.kotlin.metadata.serialization.MutableTypeTable;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.RequireKotlinConstants;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirElementSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000®\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000  \u00012\u00020\u0001:\u0002 \u0001Ba\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017J\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&J\u001c\u0010'\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030)0(2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030+J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J\u0014\u00100\u001a\u0002012\n\u00102\u001a\u0006\u0012\u0002\b\u000303H\u0002J,\u00104\u001a\u0004\u0018\u0001012\u000e\u00105\u001a\n\u0012\u0004\u0012\u000201\u0018\u00010(2\u0006\u00106\u001a\u0002072\b\b\u0002\u00108\u001a\u000209H\u0002J\u0010\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020\u0007H\u0002J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J9\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\n\u0010D\u001a\u0006\u0012\u0002\b\u00030E2\u000e\u0010F\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0G2\u0006\u0010I\u001a\u00020JH\u0002¢\u0006\u0002\u0010KJ\u0018\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010L\u001a\u00020MH\u0002J\u0010\u0010N\u001a\u0004\u0018\u00010O2\u0006\u0010P\u001a\u00020QJ\u0018\u0010R\u001a\u00020J2\u0006\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020VH\u0002J\u0010\u0010W\u001a\u00020J2\u0006\u0010;\u001a\u00020XH\u0002J\u0010\u0010W\u001a\u00020J2\u0006\u00106\u001a\u000207H\u0002J\u0010\u0010Y\u001a\u00020J2\u0006\u0010Z\u001a\u00020[H\u0002J\u0010\u0010\\\u001a\u00020J2\u0006\u0010]\u001a\u00020\nH\u0002J\u0010\u0010^\u001a\u00020_2\u0006\u0010;\u001a\u00020`H\u0002J\u0010\u0010^\u001a\u00020_2\u0006\u0010;\u001a\u00020TH\u0002J,\u0010a\u001a\u00020b2\u0006\u0010c\u001a\u00020d2\f\u0010e\u001a\b\u0012\u0004\u0012\u00020f0(2\u000e\u0010g\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010hJ\u0010\u0010i\u001a\u0004\u0018\u00010j2\u0006\u0010U\u001a\u00020VJ\u0012\u0010k\u001a\u0004\u0018\u00010l2\u0006\u0010m\u001a\u000201H\u0002J\u0012\u0010n\u001a\u00020\u00122\b\u0010P\u001a\u0004\u0018\u00010QH\u0002J\u0012\u0010o\u001a\u0004\u0018\u00010p2\u0006\u0010q\u001a\u00020rH\u0002J\u0010\u0010s\u001a\u00020t2\u0006\u0010u\u001a\u00020HH\u0002J\u000e\u0010v\u001a\u00020J2\u0006\u0010L\u001a\u00020wJ\u000e\u0010v\u001a\u00020J2\u0006\u0010x\u001a\u00020yJ*\u0010z\u001a\u00020C2\u0006\u0010L\u001a\u00020w2\u0006\u0010{\u001a\u00020\u00122\b\u0010|\u001a\u0004\u0018\u00010y2\u0006\u0010}\u001a\u00020\u0012H\u0002J\u0010\u0010~\u001a\u00020\u007f2\u0006\u0010]\u001a\u00020\nH\u0002J1\u0010\u0080\u0001\u001a\u00020C2\u0006\u0010L\u001a\u00020w2\b\b\u0002\u0010{\u001a\u00020\u00122\n\b\u0002\u0010|\u001a\u0004\u0018\u00010y2\b\b\u0002\u0010}\u001a\u00020\u0012H\u0002J\u001b\u0010\u0080\u0001\u001a\u00020C2\u0006\u0010x\u001a\u00020y2\b\b\u0002\u0010{\u001a\u00020\u0012H\u0002J\t\u0010\u0081\u0001\u001a\u00020\u0012H\u0002J6\u0010\u0082\u0001\u001a\u00030\u0083\u00012\b\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0007\u0010\u0086\u0001\u001a\u00020J2\u0006\u0010P\u001a\u00020Q2\u000f\b\u0002\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u0002010(H\u0002J^\u0010\u0088\u0001\u001a\t\u0012\u0005\u0012\u0003H\u0089\u00010(\"\f\b\u0000\u0010\u0089\u0001\u0018\u0001*\u00030\u008a\u0001\"\u000e\b\u0001\u0010\u008b\u0001*\u0007\u0012\u0002\b\u00030\u008c\u0001*\u00020&2)\u0010\u008d\u0001\u001a$\u0012\u0005\u0012\u00030\u008f\u0001\u0012\u0012\u0012\u0010\u0012\u0005\u0012\u0003H\u008b\u0001\u0012\u0004\u0012\u00020A0\u0090\u0001\u0012\u0004\u0012\u00020A0\u008e\u0001H\u0082\bJ\u0013\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020/0(*\u00020&H\u0002J\u001a\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u0001*\u00030\u0094\u00012\u0006\u0010Z\u001a\u00020[H\u0082\u0002J\u0014\u0010\u0095\u0001\u001a\t\u0012\u0005\u0012\u00030\u008a\u00010(*\u00020&H\u0002J\u000e\u0010\u0096\u0001\u001a\u00030\u0097\u0001*\u00020QH\u0002J\u001b\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u0002010(*\u00020T2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\"\u0010\u0099\u0001\u001a\b\u0012\u0004\u0012\u00020J0(*\u00020\u00102\r\u0010\u009a\u0001\u001a\b\u0012\u0004\u0012\u0002010(H\u0002J\u001d\u0010\u0099\u0001\u001a\b\u0012\u0004\u0012\u00020J0(*\u00020\u00102\b\u0010\u009b\u0001\u001a\u00030\u009c\u0001H\u0002J\u0017\u0010\u009d\u0001\u001a\u00020J*\u00020\u00102\b\u0010\u009e\u0001\u001a\u00030\u009f\u0001H\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001d\u001a\u00020\u001e8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006¡\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "containingDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "typeParameters", "Lorg/jetbrains/kotlin/metadata/serialization/Interner;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/fir/serialization/FirSerializerExtension;", "typeTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableTypeTable;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "serializeTypeTableToFunction", "", "typeApproximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lorg/jetbrains/kotlin/metadata/serialization/Interner;Lorg/jetbrains/kotlin/fir/serialization/FirSerializerExtension;Lorg/jetbrains/kotlin/metadata/serialization/MutableTypeTable;Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;ZLorg/jetbrains/kotlin/types/AbstractTypeApproximator;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "contractSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirContractSerializer;", "metDefinitelyNotNullType", "providedDeclarationsService", "Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataService;", "stringTable", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/serialization/MutableTypeTable;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "computeNestedClassifiersForClass", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "constructorProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "createAnnotationForCompilerDefinedTypeAttribute", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "attribute", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "createAnnotationFromAttribute", "existingAnnotations", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "createChildSerializer", "declaration", "enumEntryProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry$Builder;", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "fillFromPossiblyInnerType", "", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "typeArgumentIndex", "", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;I)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "functionProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "getAccessorFlags", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getClassifierId", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "getSimpleNameIndex", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTypeParameterId", "typeParameter", "normalizeVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "packagePartProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package$Builder;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "actualizedExpectDeclarations", "", "propertyProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "serializeVersionRequirementFromRequireKotlin", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement$Builder;", "annotation", "shouldSetStableParameterNames", "typeAliasProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias$Builder;", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "typeArgument", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument$Builder;", "typeProjection", "typeId", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeOrTypealiasProto", "toSuper", "correspondingTypeRef", "isDefinitelyNotNullType", "typeParameterProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Builder;", "typeProto", "useTypeTable", "valueParameterProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter$Builder;", "parameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "index", "additionalAnnotations", "collectDeclarations", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "processScope", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "Lkotlin/Function1;", "constructors", "get", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "memberDeclarations", "memberKind", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$MemberKind;", "nonSourceAnnotations", "serializeVersionRequirements", "annotations", "container", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "writeVersionRequirement", "languageFeature", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "Companion", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirElementSerializer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirDeclaration containingDeclaration;
    private final FirContractSerializer contractSerializer;
    private final FirSerializerExtension extension;
    private final LanguageVersionSettings languageVersionSettings;
    private boolean metDefinitelyNotNullType;
    private final FirProvidedDeclarationsForMetadataService providedDeclarationsService;
    private final ScopeSession scopeSession;
    private final boolean serializeTypeTableToFunction;
    private final FirSession session;
    private final AbstractTypeApproximator typeApproximator;
    private final Interner<FirTypeParameter> typeParameters;
    private final MutableTypeTable typeTable;
    private final MutableVersionRequirementTable versionRequirementTable;

    public /* synthetic */ FirElementSerializer(FirSession firSession, ScopeSession scopeSession, FirDeclaration firDeclaration, Interner interner, FirSerializerExtension firSerializerExtension, MutableTypeTable mutableTypeTable, MutableVersionRequirementTable mutableVersionRequirementTable, boolean z, AbstractTypeApproximator abstractTypeApproximator, LanguageVersionSettings languageVersionSettings, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, firDeclaration, interner, firSerializerExtension, mutableTypeTable, mutableVersionRequirementTable, z, abstractTypeApproximator, languageVersionSettings);
    }

    @JvmStatic
    public static final FirElementSerializer create(FirSession firSession, ScopeSession scopeSession, FirClass firClass, FirSerializerExtension firSerializerExtension, FirElementSerializer firElementSerializer, AbstractTypeApproximator abstractTypeApproximator, LanguageVersionSettings languageVersionSettings) {
        return INSTANCE.create(firSession, scopeSession, firClass, firSerializerExtension, firElementSerializer, abstractTypeApproximator, languageVersionSettings);
    }

    @JvmStatic
    public static final FirElementSerializer createForLambda(FirSession firSession, ScopeSession scopeSession, FirSerializerExtension firSerializerExtension, AbstractTypeApproximator abstractTypeApproximator, LanguageVersionSettings languageVersionSettings) {
        return INSTANCE.createForLambda(firSession, scopeSession, firSerializerExtension, abstractTypeApproximator, languageVersionSettings);
    }

    @JvmStatic
    public static final FirElementSerializer createTopLevel(FirSession firSession, ScopeSession scopeSession, FirSerializerExtension firSerializerExtension, AbstractTypeApproximator abstractTypeApproximator, LanguageVersionSettings languageVersionSettings) {
        return INSTANCE.createTopLevel(firSession, scopeSession, firSerializerExtension, abstractTypeApproximator, languageVersionSettings);
    }

    private FirElementSerializer(FirSession firSession, ScopeSession scopeSession, FirDeclaration firDeclaration, Interner<FirTypeParameter> interner, FirSerializerExtension firSerializerExtension, MutableTypeTable mutableTypeTable, MutableVersionRequirementTable mutableVersionRequirementTable, boolean z, AbstractTypeApproximator abstractTypeApproximator, LanguageVersionSettings languageVersionSettings) {
        this.session = firSession;
        this.scopeSession = scopeSession;
        this.containingDeclaration = firDeclaration;
        this.typeParameters = interner;
        this.extension = firSerializerExtension;
        this.typeTable = mutableTypeTable;
        this.versionRequirementTable = mutableVersionRequirementTable;
        this.serializeTypeTableToFunction = z;
        this.typeApproximator = abstractTypeApproximator;
        this.languageVersionSettings = languageVersionSettings;
        this.contractSerializer = new FirContractSerializer();
        this.providedDeclarationsService = FirProvidedDeclarationsForMetadataServiceKt.getProvidedDeclarationsForMetadataService(firSession);
    }

    public final MutableTypeTable getTypeTable() {
        return this.typeTable;
    }

    public final ProtoBuf.Package.Builder packagePartProto(FqName packageFqName, List<? extends FirFile> files, Set<? extends FirDeclaration> actualizedExpectDeclarations) {
        ProtoBuf.VersionRequirementTable versionRequirementTableSerialize;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(files, "files");
        ProtoBuf.Package.Builder builderNewBuilder = ProtoBuf.Package.newBuilder();
        for (FirFile firFile : files) {
            FirSerializerExtension firSerializerExtension = this.extension;
            ConstValueProvider constValueProvider = firSerializerExtension.getConstValueProvider();
            FirFile processingFirFile = constValueProvider != null ? constValueProvider.getProcessingFirFile() : null;
            ConstValueProvider constValueProvider2 = firSerializerExtension.getConstValueProvider();
            if (constValueProvider2 != null) {
                constValueProvider2.setProcessingFirFile(firFile);
            }
            try {
                Iterator<FirDeclaration> it = firFile.getDeclarations().iterator();
                while (it.hasNext()) {
                    packagePartProto$addDeclaration(actualizedExpectDeclarations, this, builderNewBuilder, it.next(), new Function1<FirDeclaration, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$packagePartProto$1$1
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirDeclaration it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirDeclaration firDeclaration) {
                            invoke2(firDeclaration);
                            return Unit.INSTANCE;
                        }
                    });
                }
                Unit unit = Unit.INSTANCE;
                ConstValueProvider constValueProvider3 = firSerializerExtension.getConstValueProvider();
                if (constValueProvider3 != null) {
                    constValueProvider3.setProcessingFirFile(processingFirFile);
                }
            } catch (Throwable th) {
                ConstValueProvider constValueProvider4 = firSerializerExtension.getConstValueProvider();
                if (constValueProvider4 != null) {
                    constValueProvider4.setProcessingFirFile(processingFirFile);
                }
                throw th;
            }
        }
        FirSerializerExtension firSerializerExtension2 = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        firSerializerExtension2.serializePackage(packageFqName, builderNewBuilder);
        Iterator<FirDeclaration> it2 = this.providedDeclarationsService.getProvidedTopLevelDeclarations(packageFqName, this.scopeSession).iterator();
        while (it2.hasNext()) {
            packagePartProto$addDeclaration(actualizedExpectDeclarations, this, builderNewBuilder, it2.next(), new Function1<FirDeclaration, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer.packagePartProto.2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirDeclaration firDeclaration) {
                    invoke2(firDeclaration);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirDeclaration it3) {
                    Intrinsics.checkNotNullParameter(it3, "it");
                    throw new IllegalStateException(("Unsupported top-level declaration type: " + UtilsKt.render(it3)).toString());
                }
            });
        }
        ProtoBuf.TypeTable typeTableSerialize = this.typeTable.serialize();
        if (typeTableSerialize != null) {
            builderNewBuilder.setTypeTable(typeTableSerialize);
        }
        MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
        if (mutableVersionRequirementTable != null && (versionRequirementTableSerialize = mutableVersionRequirementTable.serialize()) != null) {
            builderNewBuilder.setVersionRequirementTable(versionRequirementTableSerialize);
        }
        return builderNewBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void packagePartProto$addDeclaration(Set<? extends FirDeclaration> set, FirElementSerializer firElementSerializer, ProtoBuf.Package.Builder builder, FirDeclaration firDeclaration, Function1<? super FirDeclaration, Unit> function1) {
        if (firDeclaration instanceof FirMemberDeclaration) {
            FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) firDeclaration;
            if (SerializationUtilKt.shouldBeSerialized(firMemberDeclaration, set)) {
                if (firMemberDeclaration instanceof FirProperty) {
                    ProtoBuf.Property.Builder builderPropertyProto = firElementSerializer.propertyProto((FirProperty) firDeclaration);
                    if (builderPropertyProto != null) {
                        builder.addProperty(builderPropertyProto);
                        return;
                    }
                    return;
                }
                if (firMemberDeclaration instanceof FirSimpleFunction) {
                    ProtoBuf.Function.Builder builderFunctionProto = firElementSerializer.functionProto((FirFunction) firDeclaration);
                    if (builderFunctionProto != null) {
                        builder.addFunction(builderFunctionProto);
                        return;
                    }
                    return;
                }
                if (!(firMemberDeclaration instanceof FirTypeAlias)) {
                    function1.invoke(firDeclaration);
                    return;
                }
                ProtoBuf.TypeAlias.Builder builderTypeAliasProto = firElementSerializer.typeAliasProto((FirTypeAlias) firDeclaration);
                if (builderTypeAliasProto != null) {
                    builder.addTypeAlias(builderTypeAliasProto);
                    return;
                }
                return;
            }
            return;
        }
        function1.invoke(firDeclaration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ProtoBuf.Class.Builder classProto(FirClass klass) {
        Modality modality;
        Visibilities.Local localNormalizeVisibility;
        FirRegularClassSymbol companionObjectSymbol;
        Intrinsics.checkNotNullParameter(klass, "klass");
        FirSession firSession = this.session;
        try {
            ProtoBuf.Class.Builder builderNewBuilder = ProtoBuf.Class.newBuilder();
            FirRegularClass firRegularClass = klass instanceof FirRegularClass ? (FirRegularClass) klass : null;
            if (firRegularClass == null || (modality = firRegularClass.getStatus().getModality()) == null) {
                modality = Modality.FINAL;
            }
            boolean z = klass.getClassKind() == ClassKind.ENUM_CLASS && this.languageVersionSettings.supportsFeature(LanguageFeature.EnumEntries);
            boolean z2 = !FirAnnotationUtilsKt.nonSourceAnnotations(klass, this.session).isEmpty() || this.extension.hasAdditionalAnnotations(klass);
            ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
            if (firRegularClass == null || (localNormalizeVisibility = normalizeVisibility(firRegularClass)) == null) {
                localNormalizeVisibility = Visibilities.Local.INSTANCE;
            }
            int classFlags = Flags.getClassFlags(z2, protoEnumFlags.visibility(localNormalizeVisibility), ProtoEnumFlags.INSTANCE.modality(modality), ProtoEnumFlags.INSTANCE.classKind(klass.getClassKind(), firRegularClass != null && firRegularClass.getStatus().isCompanion()), firRegularClass != null && firRegularClass.getStatus().isInner(), firRegularClass != null && firRegularClass.getStatus().isData(), firRegularClass != null && firRegularClass.getStatus().isExternal(), firRegularClass != null && firRegularClass.getStatus().isExpect(), firRegularClass != null && firRegularClass.getStatus().isInline(), firRegularClass != null && firRegularClass.getStatus().isFun(), z);
            if (classFlags != builderNewBuilder.getFlags()) {
                builderNewBuilder.setFlags(classFlags);
            }
            builderNewBuilder.setFqName(getClassifierId(klass));
            for (FirTypeParameterRef firTypeParameterRef : klass.getTypeParameters()) {
                if (firTypeParameterRef instanceof FirTypeParameter) {
                    builderNewBuilder.addTypeParameter(typeParameterProto((FirTypeParameter) firTypeParameterRef));
                }
            }
            FirClassSymbol<? extends FirClass> symbol = klass.getSymbol();
            ClassId classId = symbol.getClassId();
            if (!Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getAny()) && !Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getNothing())) {
                for (FirTypeRef firTypeRef : klass.getSuperTypeRefs()) {
                    if (useTypeTable()) {
                        builderNewBuilder.addSupertypeId(typeId(firTypeRef));
                    } else {
                        builderNewBuilder.addSupertype(typeProto$default(this, firTypeRef, false, 2, null));
                    }
                }
            }
            if (firRegularClass != null && firRegularClass.getClassKind() != ClassKind.ENUM_ENTRY) {
                Iterator<FirConstructor> it = constructors(firRegularClass).iterator();
                while (it.hasNext()) {
                    builderNewBuilder.addConstructor(constructorProto(it.next()));
                }
                Iterator it2 = CollectionsKt.sortedWith(this.providedDeclarationsService.getProvidedConstructors(symbol, this.scopeSession), FirCallableDeclarationComparator.INSTANCE).iterator();
                while (it2.hasNext()) {
                    builderNewBuilder.addConstructor(constructorProto((FirConstructor) it2.next()));
                }
            }
            List<FirCallableDeclaration> listPlus = CollectionsKt.plus((Collection) memberDeclarations(klass), (Iterable) CollectionsKt.sortedWith(this.providedDeclarationsService.getProvidedCallables(symbol, this.scopeSession), FirCallableDeclarationComparator.INSTANCE));
            for (FirCallableDeclaration firCallableDeclaration : listPlus) {
                if ((firCallableDeclaration instanceof FirEnumEntry) || !firCallableDeclaration.getStatus().isStatic()) {
                    if (firCallableDeclaration instanceof FirProperty) {
                        ProtoBuf.Property.Builder builderPropertyProto = propertyProto((FirProperty) firCallableDeclaration);
                        if (builderPropertyProto != null) {
                            builderNewBuilder.addProperty(builderPropertyProto);
                        }
                    } else if (firCallableDeclaration instanceof FirSimpleFunction) {
                        ProtoBuf.Function.Builder builderFunctionProto = functionProto((FirFunction) firCallableDeclaration);
                        if (builderFunctionProto != null) {
                            builderNewBuilder.addFunction(builderFunctionProto);
                        }
                    } else if (firCallableDeclaration instanceof FirEnumEntry) {
                        builderNewBuilder.addEnumEntry(enumEntryProto((FirEnumEntry) firCallableDeclaration));
                    }
                }
            }
            for (FirClassifierSymbol<?> firClassifierSymbol : computeNestedClassifiersForClass(symbol)) {
                if (firClassifierSymbol instanceof FirTypeAliasSymbol) {
                    ProtoBuf.TypeAlias.Builder builderTypeAliasProto = typeAliasProto((FirTypeAlias) ((FirTypeAliasSymbol) firClassifierSymbol).getFir());
                    if (builderTypeAliasProto != null) {
                        builderNewBuilder.addTypeAlias(builderTypeAliasProto);
                    }
                } else if (firClassifierSymbol instanceof FirRegularClassSymbol) {
                    builderNewBuilder.addNestedClassName(getSimpleNameIndex(((FirRegularClassSymbol) firClassifierSymbol).getName()));
                }
            }
            if ((klass instanceof FirRegularClass) && klass.getStatus().getModality() == Modality.SEALED) {
                Iterator<ClassId> it3 = SealedClassInheritorsKt.getSealedClassInheritors((FirRegularClass) klass, this.session).iterator();
                while (it3.hasNext()) {
                    builderNewBuilder.addSealedSubclassFqName(getStringTable().getQualifiedClassNameIndex(it3.next()));
                }
            }
            FirRegularClass firRegularClass2 = (firRegularClass == null || (companionObjectSymbol = firRegularClass.getCompanionObjectSymbol()) == null) ? null : (FirRegularClass) companionObjectSymbol.getFir();
            if (firRegularClass2 != null) {
                builderNewBuilder.setCompanionObjectName(getSimpleNameIndex(firRegularClass2.getName()));
            }
            FirRegularClass firRegularClass3 = klass instanceof FirRegularClass ? (FirRegularClass) klass : null;
            ValueClassRepresentation<ConeSimpleKotlinType> valueClassRepresentation = firRegularClass3 != null ? FirValueClassRepresentationKt.getValueClassRepresentation(firRegularClass3) : null;
            if (valueClassRepresentation instanceof InlineClassRepresentation) {
                builderNewBuilder.setInlineClassUnderlyingPropertyName(getSimpleNameIndex(((InlineClassRepresentation) valueClassRepresentation).getUnderlyingPropertyName()));
                Object obj = null;
                boolean z3 = false;
                for (Object obj2 : listPlus) {
                    FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) obj2;
                    if ((firCallableDeclaration2 instanceof FirProperty) && firCallableDeclaration2.getReceiverParameter() == null && Intrinsics.areEqual(((FirProperty) firCallableDeclaration2).getName(), ((InlineClassRepresentation) valueClassRepresentation).getUnderlyingPropertyName())) {
                        if (z3) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z3 = true;
                        obj = obj2;
                    }
                }
                if (z3) {
                    if (!((FirCallableDeclaration) obj).getStatus().getVisibility().getIsPublicAPI()) {
                        if (useTypeTable()) {
                            builderNewBuilder.setInlineClassUnderlyingTypeId(typeId((ConeKotlinType) ((InlineClassRepresentation) valueClassRepresentation).getUnderlyingType()));
                        } else {
                            builderNewBuilder.setInlineClassUnderlyingType(typeProto$default(this, (ConeKotlinType) ((InlineClassRepresentation) valueClassRepresentation).getUnderlyingType(), false, null, false, 14, null));
                        }
                    }
                } else {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
            } else if (valueClassRepresentation instanceof MultiFieldValueClassRepresentation) {
                List underlyingPropertyNamesToTypes = ((MultiFieldValueClassRepresentation) valueClassRepresentation).getUnderlyingPropertyNamesToTypes();
                List list = underlyingPropertyNamesToTypes;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it4 = list.iterator();
                while (it4.hasNext()) {
                    arrayList.add(Integer.valueOf(getSimpleNameIndex((Name) ((Pair) it4.next()).component1())));
                }
                builderNewBuilder.addAllMultiFieldValueClassUnderlyingName(arrayList);
                if (useTypeTable()) {
                    List list2 = underlyingPropertyNamesToTypes;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator it5 = list2.iterator();
                    while (it5.hasNext()) {
                        arrayList2.add(Integer.valueOf(typeId((ConeSimpleKotlinType) ((Pair) it5.next()).component2())));
                    }
                    builderNewBuilder.addAllMultiFieldValueClassUnderlyingTypeId(arrayList2);
                } else {
                    List list3 = underlyingPropertyNamesToTypes;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                    Iterator it6 = list3.iterator();
                    while (it6.hasNext()) {
                        arrayList3.add(typeProto$default(this, (ConeSimpleKotlinType) ((Pair) it6.next()).component2(), false, null, false, 14, null).build());
                    }
                    builderNewBuilder.addAllMultiFieldValueClassUnderlyingType(arrayList3);
                }
            }
            if (klass instanceof FirRegularClass) {
                for (FirContextReceiver firContextReceiver : ((FirRegularClass) klass).getContextReceivers()) {
                    FirTypeRef typeRef = firContextReceiver.getTypeRef();
                    if (useTypeTable()) {
                        builderNewBuilder.addContextReceiverTypeId(typeId(typeRef));
                    } else {
                        builderNewBuilder.addContextReceiverType(typeProto$default(this, firContextReceiver.getTypeRef(), false, 2, null));
                    }
                }
            }
            MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
            if (mutableVersionRequirementTable == null) {
                throw new IllegalStateException(("Version requirements must be serialized for classes: " + UtilsKt.render(klass)).toString());
            }
            builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, klass));
            FirSerializerExtension firSerializerExtension = this.extension;
            Intrinsics.checkNotNull(builderNewBuilder);
            firSerializerExtension.serializeClass(klass, builderNewBuilder, this.versionRequirementTable, this);
            if (this.metDefinitelyNotNullType) {
                builderNewBuilder.addVersionRequirement(INSTANCE.writeLanguageVersionRequirement(LanguageFeature.DefinitelyNonNullableTypes, this.versionRequirementTable));
            }
            ProtoBuf.TypeTable typeTableSerialize = this.typeTable.serialize();
            if (typeTableSerialize != null) {
                builderNewBuilder.setTypeTable(typeTableSerialize);
            }
            ProtoBuf.VersionRequirementTable versionRequirementTableSerialize = this.versionRequirementTable.serialize();
            if (versionRequirementTableSerialize != null) {
                builderNewBuilder.setVersionRequirementTable(versionRequirementTableSerialize);
            }
            return builderNewBuilder;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(klass, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirClassifierSymbol<?>> computeNestedClassifiersForClass(FirClassSymbol<?> classSymbol) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(this.session, (FirClass) classSymbol.getFir());
        if (firNestedClassifierScopeNestedClassifierScope == null) {
            return CollectionsKt.emptyList();
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        List<FirDeclaration> declarations = ((FirClass) classSymbol.getFir()).getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof FirClassLikeDeclaration) {
                arrayList.add(obj);
            }
        }
        final Map mapMapToIndex = org.jetbrains.kotlin.utils.CollectionsKt.mapToIndex(arrayList);
        Set<Name> classifierNames = firNestedClassifierScopeNestedClassifierScope.getClassifierNames();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it = classifierNames.iterator();
        while (it.hasNext()) {
            FirClassifierSymbol<?> singleClassifier = FirScopeKt.getSingleClassifier(firNestedClassifierScopeNestedClassifierScope, (Name) it.next());
            FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) (singleClassifier != null ? singleClassifier.getFir() : null);
            if (firClassLikeDeclaration != null) {
                arrayList2.add(firClassLikeDeclaration);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (mapMapToIndex.containsKey((FirClassLikeDeclaration) obj2)) {
                arrayList3.add(obj2);
            } else {
                arrayList4.add(obj2);
            }
        }
        Pair pair = new Pair(arrayList3, arrayList4);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        Iterator it2 = CollectionsKt.sortedWith(list, new Comparator() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$computeNestedClassifiersForClass$lambda$25$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues((Integer) MapsKt.getValue(mapMapToIndex, (FirClassLikeDeclaration) t), (Integer) MapsKt.getValue(mapMapToIndex, (FirClassLikeDeclaration) t2));
            }
        }).iterator();
        while (it2.hasNext()) {
            listCreateListBuilder.add(((FirClassLikeDeclaration) it2.next()).getSymbol());
        }
        List list3 = listCreateListBuilder;
        Iterator it3 = CollectionsKt.sortedWith(list2, FirMemberDeclarationComparator.INSTANCE).iterator();
        while (it3.hasNext()) {
            list3.add(((FirClassLikeDeclaration) it3.next()).getSymbol());
        }
        List<FirClassLikeSymbol<?>> providedNestedClassifiers = this.providedDeclarationsService.getProvidedNestedClassifiers(classSymbol, this.scopeSession);
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(providedNestedClassifiers, 10));
        Iterator<T> it4 = providedNestedClassifiers.iterator();
        while (it4.hasNext()) {
            arrayList5.add((FirClassLikeDeclaration) ((FirClassLikeSymbol) it4.next()).getFir());
        }
        Iterator it5 = CollectionsKt.sortedWith(arrayList5, FirMemberDeclarationComparator.INSTANCE).iterator();
        while (it5.hasNext()) {
            list3.add(((FirClassLikeDeclaration) it5.next()).getSymbol());
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    private final List<FirAnnotation> nonSourceAnnotations(FirPropertyAccessor firPropertyAccessor, FirSession firSession) {
        Intrinsics.checkNotNull(firPropertyAccessor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirAnnotationContainer");
        return FirAnnotationUtilsKt.nonSourceAnnotations(firPropertyAccessor, firSession);
    }

    public final ProtoBuf.Property.Builder propertyProto(FirProperty property) {
        boolean z;
        boolean z2;
        FirBackingField backingField;
        FirBackingField backingField2;
        List<FirAnnotation> listNonSourceAnnotations;
        Intrinsics.checkNotNullParameter(property, "property");
        FirSession firSession = this.session;
        try {
            ProtoBuf.Property.Builder builderNewBuilder = ProtoBuf.Property.newBuilder();
            FirElementSerializer firElementSerializerCreateChildSerializer = createChildSerializer(property);
            boolean z3 = !FirAnnotationUtilsKt.nonSourceAnnotations(property, this.session).isEmpty() || !((backingField = property.getBackingField()) == null || (listNonSourceAnnotations = FirAnnotationUtilsKt.nonSourceAnnotations(backingField, this.session)) == null || !(listNonSourceAnnotations.isEmpty() ^ true)) || this.extension.hasAdditionalAnnotations(property) || ((backingField2 = property.getBackingField()) != null && this.extension.hasAdditionalAnnotations(backingField2));
            Modality modality = property.getStatus().getModality();
            Intrinsics.checkNotNull(modality);
            int accessorFlags = Flags.getAccessorFlags(z3, ProtoEnumFlags.INSTANCE.visibility(normalizeVisibility(property)), ProtoEnumFlags.INSTANCE.modality(modality), false, false, false);
            FirDefaultPropertyGetter getter = property.getGetter();
            if (getter == null) {
                getter = Intrinsics.areEqual(property.getOrigin(), FirDeclarationOrigin.Delegated.INSTANCE) ? new FirDefaultPropertyGetter(null, property.getModuleData(), property.getOrigin(), property.getReturnTypeRef(), property.getStatus().getVisibility(), property.getSymbol(), null, null, null, null, XKeySym.XK_Amacron, null) : null;
            }
            if (getter != null) {
                int accessorFlags2 = getAccessorFlags(getter, property);
                if (accessorFlags2 != accessorFlags) {
                    builderNewBuilder.setGetterFlags(accessorFlags2);
                }
                z = true;
            } else {
                z = false;
            }
            FirDefaultPropertySetter setter = property.getSetter();
            if (setter == null) {
                setter = (Intrinsics.areEqual(property.getOrigin(), FirDeclarationOrigin.Delegated.INSTANCE) && property.getIsVar()) ? new FirDefaultPropertySetter(null, property.getModuleData(), property.getOrigin(), property.getReturnTypeRef(), property.getStatus().getVisibility(), property.getSymbol(), null, null, null, null, null, 1984, null) : null;
            }
            if (setter != null) {
                int accessorFlags3 = getAccessorFlags(setter, property);
                if (accessorFlags3 != accessorFlags) {
                    builderNewBuilder.setSetterFlags(accessorFlags3);
                }
                List<FirAnnotation> listNonSourceAnnotations2 = nonSourceAnnotations(setter, this.session);
                Boolean bool = Flags.IS_NOT_DEFAULT.get(accessorFlags3);
                Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
                if (bool.booleanValue()) {
                    FirElementSerializer firElementSerializerCreateChildSerializer2 = firElementSerializerCreateChildSerializer.createChildSerializer(setter);
                    Iterator<FirValueParameter> it = setter.getValueParameters().iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        int i2 = i + 1;
                        FirValueParameter next = it.next();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : listNonSourceAnnotations2) {
                            List<FirAnnotation> list = listNonSourceAnnotations2;
                            Iterator<FirValueParameter> it2 = it;
                            if (((FirAnnotation) obj).getUseSiteTarget() == AnnotationUseSiteTarget.SETTER_PARAMETER) {
                                arrayList.add(obj);
                            }
                            it = it2;
                            listNonSourceAnnotations2 = list;
                        }
                        builderNewBuilder.setSetterValueParameter(firElementSerializerCreateChildSerializer2.valueParameterProto(next, i, setter, arrayList));
                        i = i2;
                        it = it;
                        listNonSourceAnnotations2 = listNonSourceAnnotations2;
                    }
                }
                z2 = true;
            } else {
                z2 = false;
            }
            int propertyFlags = Flags.getPropertyFlags(z3, ProtoEnumFlags.INSTANCE.visibility(normalizeVisibility(property)), ProtoEnumFlags.INSTANCE.modality(modality), ProtoBuf.MemberKind.DECLARATION, property.getIsVar(), z, z2, (!property.getIsVar() && FirToConstantValueTransformerKt.hasConstantValue(property.getInitializer(), this.session)) || property.getStatus().isConst(), property.getStatus().isConst(), property.getStatus().isLateInit(), property.getStatus().isExternal(), property.getDelegateFieldSymbol() != null, property.getStatus().isExpect());
            if (propertyFlags != builderNewBuilder.getFlags()) {
                builderNewBuilder.setFlags(propertyFlags);
            }
            builderNewBuilder.setName(getSimpleNameIndex(property.getName()));
            if (useTypeTable()) {
                builderNewBuilder.setReturnTypeId(firElementSerializerCreateChildSerializer.typeId(property.getReturnTypeRef()));
            } else {
                builderNewBuilder.setReturnType(firElementSerializerCreateChildSerializer.typeProto(property.getReturnTypeRef(), true));
            }
            Iterator<FirTypeParameter> it3 = property.getTypeParameters().iterator();
            while (it3.hasNext()) {
                builderNewBuilder.addTypeParameter(firElementSerializerCreateChildSerializer.typeParameterProto(it3.next()));
            }
            for (FirContextReceiver firContextReceiver : property.getContextReceivers()) {
                FirTypeRef typeRef = firContextReceiver.getTypeRef();
                if (useTypeTable()) {
                    builderNewBuilder.addContextReceiverTypeId(typeId(typeRef));
                } else {
                    builderNewBuilder.addContextReceiverType(typeProto$default(this, firContextReceiver.getTypeRef(), false, 2, null));
                }
            }
            FirReceiverParameter receiverParameter = property.getReceiverParameter();
            if (receiverParameter != null) {
                FirTypeRef typeRef2 = receiverParameter.getTypeRef();
                if (useTypeTable()) {
                    builderNewBuilder.setReceiverTypeId(firElementSerializerCreateChildSerializer.typeId(typeRef2));
                } else {
                    builderNewBuilder.setReceiverType(typeProto$default(firElementSerializerCreateChildSerializer, typeRef2, false, 2, null));
                }
            }
            MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
            if (mutableVersionRequirementTable != null) {
                builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, property));
                if (firElementSerializerCreateChildSerializer.metDefinitelyNotNullType) {
                    builderNewBuilder.addVersionRequirement(writeVersionRequirement(mutableVersionRequirementTable, LanguageFeature.DefinitelyNonNullableTypes));
                }
            }
            FirSerializerExtension firSerializerExtension = this.extension;
            Intrinsics.checkNotNull(builderNewBuilder);
            firSerializerExtension.serializeProperty(property, builderNewBuilder, this.versionRequirementTable, firElementSerializerCreateChildSerializer);
            return builderNewBuilder;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(property, th);
            throw null;
        }
    }

    public final ProtoBuf.Function.Builder functionProto(FirFunction function) {
        Visibilities.Local localNormalizeVisibility;
        Modality modality;
        Name name2;
        ProtoBuf.TypeTable typeTableSerialize;
        Intrinsics.checkNotNullParameter(function, "function");
        FirSession firSession = this.session;
        try {
            ProtoBuf.Function.Builder builderNewBuilder = ProtoBuf.Function.newBuilder();
            FirSimpleFunction firSimpleFunction = function instanceof FirSimpleFunction ? (FirSimpleFunction) function : null;
            FirElementSerializer firElementSerializerCreateChildSerializer = createChildSerializer(function);
            boolean z = !FirAnnotationUtilsKt.nonSourceAnnotations(function, this.session).isEmpty() || this.extension.hasAdditionalAnnotations(function);
            ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
            if (firSimpleFunction == null || (localNormalizeVisibility = normalizeVisibility(firSimpleFunction)) == null) {
                localNormalizeVisibility = Visibilities.Local.INSTANCE;
            }
            ProtoBuf.Visibility visibility = protoEnumFlags.visibility(localNormalizeVisibility);
            ProtoEnumFlags protoEnumFlags2 = ProtoEnumFlags.INSTANCE;
            if (firSimpleFunction == null || (modality = firSimpleFunction.getStatus().getModality()) == null) {
                modality = Modality.FINAL;
            }
            int functionFlags = Flags.getFunctionFlags(z, visibility, protoEnumFlags2.modality(modality), memberKind(function), firSimpleFunction != null && firSimpleFunction.getStatus().isOperator(), firSimpleFunction != null && firSimpleFunction.getStatus().isInfix(), firSimpleFunction != null && firSimpleFunction.getStatus().isInline(), firSimpleFunction != null && firSimpleFunction.getStatus().isTailRec(), firSimpleFunction != null && firSimpleFunction.getStatus().isExternal(), function.getStatus().isSuspend(), firSimpleFunction != null && firSimpleFunction.getStatus().isExpect(), shouldSetStableParameterNames(function));
            if (functionFlags != builderNewBuilder.getFlags()) {
                builderNewBuilder.setFlags(functionFlags);
            }
            if (function instanceof FirSimpleFunction) {
                name2 = ((FirSimpleFunction) function).getName();
            } else if (function instanceof FirAnonymousFunction) {
                name2 = ((FirAnonymousFunction) function).getIsLambda() ? SpecialNames.ANONYMOUS : SpecialNames.NO_NAME_PROVIDED;
            } else {
                throw new AssertionError("Unsupported function: " + UtilsKt.render(function));
            }
            builderNewBuilder.setName(getSimpleNameIndex(name2));
            if (useTypeTable()) {
                builderNewBuilder.setReturnTypeId(firElementSerializerCreateChildSerializer.typeId(function.getReturnTypeRef()));
            } else {
                builderNewBuilder.setReturnType(firElementSerializerCreateChildSerializer.typeProto(function.getReturnTypeRef(), true));
            }
            for (FirTypeParameterRef firTypeParameterRef : function.getTypeParameters()) {
                if (firTypeParameterRef instanceof FirTypeParameter) {
                    builderNewBuilder.addTypeParameter(firElementSerializerCreateChildSerializer.typeParameterProto((FirTypeParameter) firTypeParameterRef));
                }
            }
            for (FirContextReceiver firContextReceiver : function.getContextReceivers()) {
                FirTypeRef typeRef = firContextReceiver.getTypeRef();
                if (useTypeTable()) {
                    builderNewBuilder.addContextReceiverTypeId(typeId(typeRef));
                } else {
                    builderNewBuilder.addContextReceiverType(typeProto$default(this, firContextReceiver.getTypeRef(), false, 2, null));
                }
            }
            FirReceiverParameter receiverParameter = function.getReceiverParameter();
            if (receiverParameter != null) {
                FirTypeRef typeRef2 = receiverParameter.getTypeRef();
                if (useTypeTable()) {
                    builderNewBuilder.setReceiverTypeId(firElementSerializerCreateChildSerializer.typeId(typeRef2));
                } else {
                    builderNewBuilder.setReceiverType(typeProto$default(firElementSerializerCreateChildSerializer, typeRef2, false, 2, null));
                }
            }
            Iterator<FirValueParameter> it = function.getValueParameters().iterator();
            int i = 0;
            while (it.hasNext()) {
                builderNewBuilder.addValueParameter(valueParameterProto$default(firElementSerializerCreateChildSerializer, it.next(), i, function, null, 8, null));
                i++;
            }
            FirContractSerializer firContractSerializer = this.contractSerializer;
            Intrinsics.checkNotNull(builderNewBuilder);
            firContractSerializer.serializeContractOfFunctionIfAny(function, builderNewBuilder, this);
            this.extension.serializeFunction(function, builderNewBuilder, this.versionRequirementTable, firElementSerializerCreateChildSerializer);
            if (this.serializeTypeTableToFunction && (typeTableSerialize = this.typeTable.serialize()) != null) {
                builderNewBuilder.setTypeTable(typeTableSerialize);
            }
            MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
            if (mutableVersionRequirementTable != null) {
                builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, function));
                if (firElementSerializerCreateChildSerializer.metDefinitelyNotNullType) {
                    builderNewBuilder.addVersionRequirement(writeVersionRequirement(mutableVersionRequirementTable, LanguageFeature.DefinitelyNonNullableTypes));
                }
            }
            return builderNewBuilder;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(function, th);
            throw null;
        }
    }

    private final ProtoBuf.MemberKind memberKind(FirFunction firFunction) {
        FirDeclarationOrigin origin = firFunction.getOrigin();
        if (Intrinsics.areEqual(origin, FirDeclarationOrigin.Delegated.INSTANCE)) {
            return ProtoBuf.MemberKind.DELEGATION;
        }
        return origin instanceof FirDeclarationOrigin.Synthetic ? ProtoBuf.MemberKind.SYNTHESIZED : ProtoBuf.MemberKind.DECLARATION;
    }

    private final boolean shouldSetStableParameterNames(FirFunction function) {
        if (function == null || !function.getStatus().getHasStableParameterNames()) {
            return Intrinsics.areEqual(function != null ? function.getOrigin() : null, FirDeclarationOrigin.Delegated.INSTANCE);
        }
        return true;
    }

    private final ProtoBuf.TypeAlias.Builder typeAliasProto(FirTypeAlias typeAlias) {
        FirSession firSession = this.session;
        try {
            ProtoBuf.TypeAlias.Builder builderNewBuilder = ProtoBuf.TypeAlias.newBuilder();
            FirElementSerializer firElementSerializerCreateChildSerializer = createChildSerializer(typeAlias);
            int typeAliasFlags = Flags.getTypeAliasFlags(!FirAnnotationUtilsKt.nonSourceAnnotations(typeAlias, this.session).isEmpty() || this.extension.hasAdditionalAnnotations(typeAlias), ProtoEnumFlags.INSTANCE.visibility(normalizeVisibility(typeAlias)));
            if (typeAliasFlags != builderNewBuilder.getFlags()) {
                builderNewBuilder.setFlags(typeAliasFlags);
            }
            builderNewBuilder.setName(getSimpleNameIndex(typeAlias.getName()));
            Iterator<FirTypeParameter> it = typeAlias.getTypeParameters().iterator();
            while (it.hasNext()) {
                builderNewBuilder.addTypeParameter(firElementSerializerCreateChildSerializer.typeParameterProto(it.next()));
            }
            ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType(typeAlias);
            Intrinsics.checkNotNull(expandedConeType);
            if (useTypeTable()) {
                builderNewBuilder.setUnderlyingTypeId(firElementSerializerCreateChildSerializer.typeId(expandedConeType));
            } else {
                builderNewBuilder.setUnderlyingType(typeProto$default(firElementSerializerCreateChildSerializer, expandedConeType, false, null, false, 14, null));
            }
            ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(expandedConeType, this.session, null, 2, null);
            if (useTypeTable()) {
                builderNewBuilder.setExpandedTypeId(firElementSerializerCreateChildSerializer.typeId(coneClassLikeTypeFullyExpandedType$default));
            } else {
                builderNewBuilder.setExpandedType(typeProto$default(firElementSerializerCreateChildSerializer, coneClassLikeTypeFullyExpandedType$default, false, null, false, 14, null));
            }
            MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
            if (mutableVersionRequirementTable != null) {
                builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, typeAlias));
                if (firElementSerializerCreateChildSerializer.metDefinitelyNotNullType) {
                    builderNewBuilder.addVersionRequirement(INSTANCE.writeLanguageVersionRequirement(LanguageFeature.DefinitelyNonNullableTypes, this.versionRequirementTable));
                }
            }
            FirSerializerExtension firSerializerExtension = this.extension;
            Intrinsics.checkNotNull(builderNewBuilder);
            firSerializerExtension.serializeTypeAlias(typeAlias, builderNewBuilder);
            return builderNewBuilder;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(typeAlias, th);
            throw null;
        }
    }

    private final ProtoBuf.EnumEntry.Builder enumEntryProto(FirEnumEntry enumEntry) {
        FirSession firSession = this.session;
        try {
            ProtoBuf.EnumEntry.Builder builderNewBuilder = ProtoBuf.EnumEntry.newBuilder();
            builderNewBuilder.setName(getSimpleNameIndex(enumEntry.getName()));
            FirSerializerExtension firSerializerExtension = this.extension;
            Intrinsics.checkNotNull(builderNewBuilder);
            firSerializerExtension.serializeEnumEntry(enumEntry, builderNewBuilder);
            return builderNewBuilder;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(enumEntry, th);
            throw null;
        }
    }

    private final ProtoBuf.Constructor.Builder constructorProto(FirConstructor constructor) {
        FirSession firSession = this.session;
        try {
            ProtoBuf.Constructor.Builder builderNewBuilder = ProtoBuf.Constructor.newBuilder();
            FirElementSerializer firElementSerializerCreateChildSerializer = createChildSerializer(constructor);
            int constructorFlags = Flags.getConstructorFlags(!FirAnnotationUtilsKt.nonSourceAnnotations(constructor, this.session).isEmpty() || this.extension.hasAdditionalAnnotations(constructor), ProtoEnumFlags.INSTANCE.visibility(normalizeVisibility(constructor)), true ^ constructor.getIsPrimary(), shouldSetStableParameterNames(constructor));
            if (constructorFlags != builderNewBuilder.getFlags()) {
                builderNewBuilder.setFlags(constructorFlags);
            }
            Iterator<FirValueParameter> it = constructor.getValueParameters().iterator();
            int i = 0;
            while (it.hasNext()) {
                builderNewBuilder.addValueParameter(valueParameterProto$default(firElementSerializerCreateChildSerializer, it.next(), i, constructor, null, 8, null));
                i++;
            }
            MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
            if (mutableVersionRequirementTable != null) {
                builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, constructor));
                if (firElementSerializerCreateChildSerializer.metDefinitelyNotNullType) {
                    builderNewBuilder.addVersionRequirement(writeVersionRequirement(mutableVersionRequirementTable, LanguageFeature.DefinitelyNonNullableTypes));
                }
            }
            FirSerializerExtension firSerializerExtension = this.extension;
            Intrinsics.checkNotNull(builderNewBuilder);
            firSerializerExtension.serializeConstructor(constructor, builderNewBuilder, firElementSerializerCreateChildSerializer);
            return builderNewBuilder;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(constructor, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ ProtoBuf.ValueParameter.Builder valueParameterProto$default(FirElementSerializer firElementSerializer, FirValueParameter firValueParameter, int i, FirFunction firFunction, List list, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            list = CollectionsKt.emptyList();
        }
        return firElementSerializer.valueParameterProto(firValueParameter, i, firFunction, list);
    }

    private final ProtoBuf.ValueParameter.Builder valueParameterProto(FirValueParameter parameter, int index, FirFunction function, List<? extends FirAnnotation> additionalAnnotations) {
        FirSession firSession = this.session;
        try {
            ProtoBuf.ValueParameter.Builder builderNewBuilder = ProtoBuf.ValueParameter.newBuilder();
            int valueParameterFlags = Flags.getValueParameterFlags((additionalAnnotations.isEmpty() && FirAnnotationUtilsKt.nonSourceAnnotations(parameter, this.session).isEmpty() && !this.extension.hasAdditionalAnnotations(parameter)) ? false : true, DeclarationUtilsKt.itOrExpectHasDefaultParameterValue(function, index), parameter.getIsCrossinline(), parameter.getIsNoinline());
            if (valueParameterFlags != builderNewBuilder.getFlags()) {
                builderNewBuilder.setFlags(valueParameterFlags);
            }
            builderNewBuilder.setName(getSimpleNameIndex(parameter.getName()));
            if (useTypeTable()) {
                builderNewBuilder.setTypeId(typeId(parameter.getReturnTypeRef()));
            } else {
                builderNewBuilder.setType(typeProto$default(this, parameter.getReturnTypeRef(), false, 2, null));
            }
            if (parameter.getIsVararg()) {
                ConeKotlinType coneKotlinTypeVarargElementType = ArrayUtilsKt.varargElementType(FirTypeUtilsKt.getConeType(parameter.getReturnTypeRef()));
                if (useTypeTable()) {
                    builderNewBuilder.setVarargElementTypeId(typeId(coneKotlinTypeVarargElementType));
                } else {
                    builderNewBuilder.setVarargElementType(typeProto$default(this, coneKotlinTypeVarargElementType, false, null, false, 14, null));
                }
            }
            FirSerializerExtension firSerializerExtension = this.extension;
            Intrinsics.checkNotNull(builderNewBuilder);
            firSerializerExtension.serializeValueParameter(parameter, builderNewBuilder);
            return builderNewBuilder;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(parameter, th);
            throw null;
        }
    }

    private final ProtoBuf.TypeParameter.Builder typeParameterProto(FirTypeParameter typeParameter) {
        FirSession firSession = this.session;
        try {
            ProtoBuf.TypeParameter.Builder builderNewBuilder = ProtoBuf.TypeParameter.newBuilder();
            builderNewBuilder.setId(getTypeParameterId(typeParameter));
            builderNewBuilder.setName(getSimpleNameIndex(typeParameter.getName()));
            if (typeParameter.getIsReified() != builderNewBuilder.getReified()) {
                builderNewBuilder.setReified(typeParameter.getIsReified());
            }
            ProtoBuf.TypeParameter.Variance variance = ProtoEnumFlags.INSTANCE.variance(typeParameter.getVariance());
            if (variance != builderNewBuilder.getVariance()) {
                builderNewBuilder.setVariance(variance);
            }
            FirSerializerExtension firSerializerExtension = this.extension;
            Intrinsics.checkNotNull(builderNewBuilder);
            firSerializerExtension.serializeTypeParameter(typeParameter, builderNewBuilder);
            List<FirTypeRef> bounds = typeParameter.getBounds();
            if (bounds.size() == 1 && (CollectionsKt.single((List) bounds) instanceof FirImplicitNullableAnyTypeRef)) {
                return builderNewBuilder;
            }
            for (FirTypeRef firTypeRef : bounds) {
                if (useTypeTable()) {
                    builderNewBuilder.addUpperBoundId(typeId(firTypeRef));
                } else {
                    builderNewBuilder.addUpperBound(typeProto$default(this, firTypeRef, false, 2, null));
                }
            }
            return builderNewBuilder;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(typeParameter, th);
            throw null;
        }
    }

    public final int typeId(FirTypeRef typeRef) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        if (typeRef instanceof FirResolvedTypeRef) {
            return typeId(((FirResolvedTypeRef) typeRef).getType());
        }
        return -1;
    }

    public final int typeId(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeTable.get(typeProto$default(this, type, false, null, false, 14, null));
    }

    static /* synthetic */ ProtoBuf.Type.Builder typeProto$default(FirElementSerializer firElementSerializer, FirTypeRef firTypeRef, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return firElementSerializer.typeProto(firTypeRef, z);
    }

    private final ProtoBuf.Type.Builder typeProto(FirTypeRef typeRef, boolean toSuper) {
        return typeProto$default(this, FirTypeUtilsKt.getConeType(typeRef), toSuper, typeRef, false, 8, null);
    }

    static /* synthetic */ ProtoBuf.Type.Builder typeProto$default(FirElementSerializer firElementSerializer, ConeKotlinType coneKotlinType, boolean z, FirTypeRef firTypeRef, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            firTypeRef = null;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        return firElementSerializer.typeProto(coneKotlinType, z, firTypeRef, z2);
    }

    private final ProtoBuf.Type.Builder typeProto(ConeKotlinType type, boolean toSuper, FirTypeRef correspondingTypeRef, boolean isDefinitelyNotNullType) {
        ProtoBuf.Type.Builder builderTypeOrTypealiasProto = typeOrTypealiasProto(type, toSuper, correspondingTypeRef, isDefinitelyNotNullType);
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = type instanceof ConeClassLikeType ? TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) type, this.session, null, 2, null) : type;
        if (coneClassLikeTypeFullyExpandedType$default == type) {
            return builderTypeOrTypealiasProto;
        }
        ProtoBuf.Type.Builder builderTypeOrTypealiasProto2 = typeOrTypealiasProto(coneClassLikeTypeFullyExpandedType$default, toSuper, correspondingTypeRef, isDefinitelyNotNullType);
        if (useTypeTable()) {
            builderTypeOrTypealiasProto2.setAbbreviatedTypeId(this.typeTable.get(builderTypeOrTypealiasProto));
        } else {
            builderTypeOrTypealiasProto2.setAbbreviatedType(builderTypeOrTypealiasProto);
        }
        return builderTypeOrTypealiasProto2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ProtoBuf.Type.Builder typeOrTypealiasProto(ConeKotlinType type, boolean toSuper, FirTypeRef correspondingTypeRef, boolean isDefinitelyNotNullType) {
        KotlinTypeMarker kotlinTypeMarkerApproximateToSubType;
        List<FirTypeParameterRef> listEmptyList;
        LanguageVersion languageVersionFromVersionString;
        ProtoBuf.Type.Builder builderNewBuilder = ProtoBuf.Type.newBuilder();
        ArrayList arrayList = new ArrayList();
        if (type instanceof ConeDefinitelyNotNullType) {
            return typeProto(((ConeDefinitelyNotNullType) type).getOriginal(), toSuper, correspondingTypeRef, true);
        }
        if (type instanceof ConeErrorType) {
            Intrinsics.checkNotNull(builderNewBuilder);
            this.extension.serializeErrorType((ConeErrorType) type, builderNewBuilder);
            return builderNewBuilder;
        }
        if (type instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) type;
            ProtoBuf.Type.Builder builderTypeProto$default = typeProto$default(this, coneFlexibleType.getLowerBound(), false, null, false, 14, null);
            ProtoBuf.Type.Builder builderTypeProto$default2 = typeProto$default(this, coneFlexibleType.getUpperBound(), false, null, false, 14, null);
            this.extension.serializeFlexibleType(coneFlexibleType, builderTypeProto$default, builderTypeProto$default2);
            if (useTypeTable()) {
                builderTypeProto$default.setFlexibleUpperBoundId(this.typeTable.get(builderTypeProto$default2));
            } else {
                builderTypeProto$default.setFlexibleUpperBound(builderTypeProto$default2);
            }
            return builderTypeProto$default;
        }
        if (type instanceof ConeClassLikeType) {
            FunctionTypeKind functionTypeKind = FunctionalTypeUtilsKt.functionTypeKind(type, this.session);
            if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE)) {
                ProtoBuf.Type.Builder builderTypeProto$default3 = typeProto$default(this, SerializationUtilKt.suspendFunctionTypeToFunctionTypeWithContinuation(type, this.session, StandardClassIds.INSTANCE.getContinuation()), false, null, false, 14, null);
                builderTypeProto$default3.setFlags(Flags.getTypeFlags(true, false));
                return builderTypeProto$default3;
            }
            if (functionTypeKind != null && !FunctionTypeKindKt.isBuiltin(functionTypeKind) && (languageVersionFromVersionString = LanguageVersion.INSTANCE.fromVersionString(functionTypeKind.getSerializeAsFunctionWithAnnotationUntil())) != null && this.languageVersionSettings.getLanguageVersion().compareTo(languageVersionFromVersionString) < 0) {
                return typeProto$default(this, FunctionalTypeUtilsKt.customFunctionTypeToSimpleFunctionType(type, this.session), false, null, false, 14, null);
            }
            Intrinsics.checkNotNull(builderNewBuilder);
            fillFromPossiblyInnerType(builderNewBuilder, (ConeClassLikeType) type);
            if (CompilerConeAttributesKt.getHasContextReceivers(type)) {
                ArrayList arrayList2 = arrayList;
                List<FirAnnotation> annotations = correspondingTypeRef != null ? correspondingTypeRef.getAnnotations() : null;
                ClassId annotation_class_id = CompilerConeAttributes.ContextFunctionTypeParams.INSTANCE.getANNOTATION_CLASS_ID();
                FirAnnotationArgumentMappingBuilder firAnnotationArgumentMappingBuilder = new FirAnnotationArgumentMappingBuilder();
                firAnnotationArgumentMappingBuilder.getMapping().put(StandardNames.CONTEXT_FUNCTION_TYPE_PARAMETER_COUNT_NAME, FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.Int.INSTANCE, Integer.valueOf(CompilerConeAttributesKt.getContextReceiversNumberForFunctionType(type)), null, false, 8, null));
                Unit unit = Unit.INSTANCE;
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, createAnnotationFromAttribute(annotations, annotation_class_id, firAnnotationArgumentMappingBuilder.build()));
            }
        } else if (type instanceof ConeTypeParameterType) {
            FirTypeParameter firTypeParameter = (FirTypeParameter) ((ConeTypeParameterType) type).getLookupTag().getTypeParameterSymbol().getFir();
            FirDeclaration firDeclaration = this.containingDeclaration;
            FirMemberDeclaration firMemberDeclaration = firDeclaration instanceof FirMemberDeclaration ? (FirMemberDeclaration) firDeclaration : null;
            if (firMemberDeclaration == null || (listEmptyList = firMemberDeclaration.getTypeParameters()) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            if (listEmptyList.contains(firTypeParameter)) {
                builderNewBuilder.setTypeParameterName(getSimpleNameIndex(firTypeParameter.getName()));
            } else {
                builderNewBuilder.setTypeParameter(getTypeParameterId(firTypeParameter));
            }
            if (isDefinitelyNotNullType) {
                this.metDefinitelyNotNullType = true;
                builderNewBuilder.setFlags(Flags.getTypeFlags(false, isDefinitelyNotNullType));
            }
        } else {
            if (type instanceof ConeIntersectionType) {
                if (toSuper) {
                    kotlinTypeMarkerApproximateToSubType = this.typeApproximator.approximateToSuperType(type, TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE);
                } else {
                    kotlinTypeMarkerApproximateToSubType = this.typeApproximator.approximateToSubType(type, TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE);
                }
                if (!Intrinsics.areEqual(kotlinTypeMarkerApproximateToSubType, type)) {
                    boolean z = kotlinTypeMarkerApproximateToSubType instanceof ConeKotlinType;
                }
                Intrinsics.checkNotNull(kotlinTypeMarkerApproximateToSubType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                return typeProto$default(this, (ConeKotlinType) kotlinTypeMarkerApproximateToSubType, false, null, false, 14, null);
            }
            if (type instanceof ConeIntegerLiteralType) {
                throw new IllegalStateException("Integer literal types should not persist up to the serializer: " + ConeTypeUtilsKt.renderForDebugging(type));
            }
            if (type instanceof ConeCapturedType) {
                throw new IllegalStateException("Captured types should not persist up to the serializer: " + ConeTypeUtilsKt.renderForDebugging(type));
            }
            throw new AssertionError("Should not be here: " + type.getClass());
        }
        if (ConeTypeUtilsKt.isMarkedNullable(type) != builderNewBuilder.getNullable()) {
            builderNewBuilder.setNullable(ConeTypeUtilsKt.isMarkedNullable(type));
        }
        ArrayList arrayList3 = new ArrayList();
        for (ConeAttribute<?> coneAttribute : type.getAttributes()) {
            if (coneAttribute instanceof CustomAnnotationTypeAttribute) {
                arrayList.addAll(FirAnnotationUtilsKt.nonSourceAnnotations(((CustomAnnotationTypeAttribute) coneAttribute).getAnnotations(), this.session));
            } else if (CompilerConeAttributes.INSTANCE.getClassIdByCompilerAttributeKey().containsKey(coneAttribute.getKey())) {
                arrayList.add(createAnnotationForCompilerDefinedTypeAttribute(coneAttribute));
            } else {
                arrayList3.add(coneAttribute);
            }
        }
        for (FirTypeAttributeExtension firTypeAttributeExtension : FirTypeAttributeExtensionKt.getTypeAttributeExtensions(FirExtensionServiceKt.getExtensionService(this.session))) {
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, firTypeAttributeExtension.convertAttributeToAnnotation((ConeAttribute) it.next()));
            }
        }
        FirSerializerExtension firSerializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        firSerializerExtension.serializeTypeAnnotations(arrayList, builderNewBuilder);
        return builderNewBuilder;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0055 A[EDGE_INSN: B:29:0x0055->B:30:0x0056 BREAK  A[LOOP:0: B:10:0x0018->B:35:?]] */
    private final FirAnnotation createAnnotationFromAttribute(List<? extends FirAnnotation> existingAnnotations, ClassId classId, FirAnnotationArgumentMapping argumentMapping) {
        boolean z;
        if (existingAnnotations == null) {
            z = false;
            break;
        }
        List<? extends FirAnnotation> list = existingAnnotations;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                FirTypeRef annotationTypeRef = ((FirAnnotation) it.next()).getAnnotationTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                if (!(type instanceof ConeClassLikeType)) {
                    type = null;
                }
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                if (Intrinsics.areEqual(coneClassLikeType != null ? ConeTypeUtilsKt.getClassId(coneClassLikeType) : null, classId)) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        if (z) {
            return null;
        }
        FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassLikeType$default(classId, new ConeTypeProjection[0], false, null, 4, null));
        firAnnotationBuilder.setAnnotationTypeRef(firResolvedTypeRefBuilder.mo6809build());
        firAnnotationBuilder.setArgumentMapping(argumentMapping);
        return firAnnotationBuilder.mo6809build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void fillFromPossiblyInnerType(ProtoBuf.Type.Builder builder, FirClassLikeSymbol<?> symbol, ConeTypeProjection[] typeArguments, int typeArgumentIndex) {
        ClassId outerClassId;
        FirClassLikeSymbol<?> symbol2;
        FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir();
        int classifierId = getClassifierId(firClassLikeDeclaration);
        if (firClassLikeDeclaration instanceof FirTypeAlias) {
            builder.setTypeAliasName(classifierId);
        } else {
            builder.setClassName(classifierId);
        }
        int size = firClassLikeDeclaration.getTypeParameters().size();
        int i = 0;
        while (i < size && (firClassLikeDeclaration.getTypeParameters().get(i) instanceof FirTypeParameter)) {
            if (typeArgumentIndex >= typeArguments.length) {
                return;
            }
            builder.addArgument(typeArgument(typeArguments[typeArgumentIndex]));
            i++;
            typeArgumentIndex++;
        }
        if (!symbol.getRawStatus().isInner() || (outerClassId = symbol.getClassId().getOuterClassId()) == null || outerClassId.isLocal() || (symbol2 = LookupTagUtilsKt.toSymbol((ConeClassLikeLookupTag) TypeConstructionUtilsKt.toLookupTag(outerClassId), this.session)) == null) {
            return;
        }
        ProtoBuf.Type.Builder builderNewBuilder = ProtoBuf.Type.newBuilder();
        Intrinsics.checkNotNull(builderNewBuilder);
        fillFromPossiblyInnerType(builderNewBuilder, symbol2, typeArguments, typeArgumentIndex);
        if (useTypeTable()) {
            builder.setOuterTypeId(this.typeTable.get(builderNewBuilder));
        } else {
            builder.setOuterType(builderNewBuilder);
        }
    }

    private final void fillFromPossiblyInnerType(ProtoBuf.Type.Builder builder, ConeClassLikeType type) {
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(type.getLookupTag(), this.session);
        if (symbol != null) {
            fillFromPossiblyInnerType(builder, symbol, type.getTypeArguments(), 0);
            return;
        }
        builder.setClassName(getClassifierId(type.getLookupTag().getClassId()));
        for (ConeTypeProjection coneTypeProjection : type.getTypeArguments()) {
            builder.addArgument(typeArgument(coneTypeProjection));
        }
    }

    private final ProtoBuf.Type.Argument.Builder typeArgument(ConeTypeProjection typeProjection) {
        ProtoBuf.Type.Argument.Builder builderNewBuilder = ProtoBuf.Type.Argument.newBuilder();
        if (typeProjection instanceof ConeStarProjection) {
            builderNewBuilder.setProjection(ProtoBuf.Type.Argument.Projection.STAR);
        } else if (typeProjection instanceof ConeKotlinTypeProjection) {
            ProtoBuf.Type.Argument.Projection projection = ProtoEnumFlagsUtilsKt.projection(ProtoEnumFlags.INSTANCE, typeProjection.getKind());
            if (projection != builderNewBuilder.getProjection()) {
                builderNewBuilder.setProjection(projection);
            }
            if (useTypeTable()) {
                builderNewBuilder.setTypeId(typeId(((ConeKotlinTypeProjection) typeProjection).getType()));
            } else {
                builderNewBuilder.setType(typeProto$default(this, ((ConeKotlinTypeProjection) typeProjection).getType(), false, null, false, 14, null));
            }
        }
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x005b  */
    private final int getAccessorFlags(FirPropertyAccessor accessor, FirProperty property) {
        boolean z = false;
        boolean z2 = !nonSourceAnnotations(accessor, this.session).isEmpty() || this.extension.hasAdditionalAnnotations(accessor);
        if (property.getIsLocal()) {
            z = true;
        } else if ((accessor instanceof FirDefaultPropertyAccessor) && !z2) {
            FirPropertyAccessor firPropertyAccessor = accessor;
            if (Intrinsics.areEqual(firPropertyAccessor.getStatus().getVisibility(), property.getStatus().getVisibility()) && !firPropertyAccessor.getStatus().isExternal() && !firPropertyAccessor.getStatus().isInline()) {
                z = true;
            }
        }
        ProtoBuf.Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(normalizeVisibility(accessor));
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        Modality modality = property.getStatus().getModality();
        Intrinsics.checkNotNull(modality);
        FirPropertyAccessor firPropertyAccessor2 = accessor;
        return Flags.getAccessorFlags(z2, visibility, protoEnumFlags.modality(modality), !z, firPropertyAccessor2.getStatus().isExternal(), firPropertyAccessor2.getStatus().isInline());
    }

    private final FirElementSerializer createChildSerializer(FirDeclaration declaration) {
        return new FirElementSerializer(this.session, this.scopeSession, declaration, new Interner(this.typeParameters), this.extension, this.typeTable, this.versionRequirementTable, false, this.typeApproximator, this.languageVersionSettings);
    }

    public final FirElementAwareStringTable getStringTable() {
        return this.extension.getStringTable();
    }

    private final boolean useTypeTable() {
        return this.extension.getUseTypeTable();
    }

    private final List<Integer> serializeVersionRequirements(MutableVersionRequirementTable mutableVersionRequirementTable, FirAnnotationContainer firAnnotationContainer) {
        return serializeVersionRequirements(mutableVersionRequirementTable, firAnnotationContainer.getAnnotations());
    }

    private final List<Integer> serializeVersionRequirements(MutableVersionRequirementTable mutableVersionRequirementTable, List<? extends FirAnnotation> list) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            ClassId annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId((FirAnnotation) obj, this.session);
            if (Intrinsics.areEqual(annotationClassId != null ? annotationClassId.asSingleFqName() : null, RequireKotlinConstants.INSTANCE.getFQ_NAME())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ProtoBuf.VersionRequirement.Builder builderSerializeVersionRequirementFromRequireKotlin = serializeVersionRequirementFromRequireKotlin((FirAnnotation) it.next());
            if (builderSerializeVersionRequirementFromRequireKotlin != null) {
                arrayList2.add(builderSerializeVersionRequirementFromRequireKotlin);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            arrayList4.add(Integer.valueOf(mutableVersionRequirementTable.get((ProtoBuf.VersionRequirement.Builder) it2.next())));
        }
        return arrayList4;
    }

    private final int writeVersionRequirement(MutableVersionRequirementTable mutableVersionRequirementTable, LanguageFeature languageFeature) {
        return INSTANCE.writeLanguageVersionRequirement(languageFeature, mutableVersionRequirementTable);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x00df  */
    /* JADX WARN: Code duplicated, block: B:51:0x0122  */
    /* JADX WARN: Code duplicated, block: B:79:0x0191  */
    private final ProtoBuf.VersionRequirement.Builder serializeVersionRequirementFromRequireKotlin(FirAnnotation annotation) {
        String value;
        MatchResult matchResultMatchEntire;
        String str;
        Integer intOrNull;
        String value2;
        String strAsString;
        String strAsString2;
        Name enumEntryName;
        Name enumEntryName2;
        Integer intOrNull2;
        Integer intOrNull3;
        Map<Name, FirExpression> mapping = annotation.getArgumentMapping().getMapping();
        FirExpression firExpression = mapping.get(RequireKotlinConstants.INSTANCE.getVERSION());
        Integer value3 = null;
        if (firExpression != null) {
            Object objAccept = firExpression.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(this.session, null));
            if (!(objAccept instanceof StringValue)) {
                objAccept = null;
            }
            StringValue stringValue = (StringValue) objAccept;
            if (stringValue != null && (value = stringValue.getValue()) != null && (matchResultMatchEntire = RequireKotlinConstants.INSTANCE.getVERSION_REGEX().matchEntire(value)) != null && (str = (String) CollectionsKt.getOrNull(matchResultMatchEntire.getGroupValues(), 1)) != null && (intOrNull = StringsKt.toIntOrNull(str)) != null) {
                int iIntValue = intOrNull.intValue();
                String str2 = (String) CollectionsKt.getOrNull(matchResultMatchEntire.getGroupValues(), 2);
                int iIntValue2 = 0;
                int iIntValue3 = (str2 == null || (intOrNull3 = StringsKt.toIntOrNull(str2)) == null) ? 0 : intOrNull3.intValue();
                String str3 = (String) CollectionsKt.getOrNull(matchResultMatchEntire.getGroupValues(), 4);
                if (str3 != null && (intOrNull2 = StringsKt.toIntOrNull(str3)) != null) {
                    iIntValue2 = intOrNull2.intValue();
                }
                final ProtoBuf.VersionRequirement.Builder builderNewBuilder = ProtoBuf.VersionRequirement.newBuilder();
                new VersionRequirement.Version(iIntValue, iIntValue3, iIntValue2).encode(new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer.serializeVersionRequirementFromRequireKotlin.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                        invoke(num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(int i) {
                        builderNewBuilder.setVersion(i);
                    }
                }, new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer.serializeVersionRequirementFromRequireKotlin.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                        invoke(num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(int i) {
                        builderNewBuilder.setVersionFull(i);
                    }
                });
                FirExpression firExpression2 = mapping.get(RequireKotlinConstants.INSTANCE.getMESSAGE());
                if (firExpression2 != null) {
                    Object objAccept2 = firExpression2.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(this.session, null));
                    if (!(objAccept2 instanceof StringValue)) {
                        objAccept2 = null;
                    }
                    StringValue stringValue2 = (StringValue) objAccept2;
                    if (stringValue2 != null) {
                        value2 = stringValue2.getValue();
                    } else {
                        value2 = null;
                    }
                } else {
                    value2 = null;
                }
                if (value2 != null) {
                    builderNewBuilder.setMessage(getStringTable().getStringIndex(value2));
                }
                FirExpression firExpression3 = mapping.get(RequireKotlinConstants.INSTANCE.getLEVEL());
                if (firExpression3 != null) {
                    Object objAccept3 = firExpression3.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(this.session, null));
                    if (!(objAccept3 instanceof EnumValue)) {
                        objAccept3 = null;
                    }
                    EnumValue enumValue = (EnumValue) objAccept3;
                    if (enumValue == null || (enumEntryName2 = enumValue.getEnumEntryName()) == null) {
                        strAsString = null;
                    } else {
                        strAsString = enumEntryName2.asString();
                    }
                } else {
                    strAsString = null;
                }
                if (strAsString != null) {
                    int iHashCode = strAsString.hashCode();
                    if (iHashCode == 66247144) {
                        strAsString.equals(MediaError.ERROR_TYPE_ERROR);
                    } else if (iHashCode != 1842428796) {
                        if (iHashCode == 2130809258 && strAsString.equals(VirtualFile.PROP_HIDDEN)) {
                            builderNewBuilder.setLevel(ProtoBuf.VersionRequirement.Level.HIDDEN);
                        }
                    } else if (strAsString.equals("WARNING")) {
                        builderNewBuilder.setLevel(ProtoBuf.VersionRequirement.Level.WARNING);
                    }
                }
                FirExpression firExpression4 = mapping.get(RequireKotlinConstants.INSTANCE.getVERSION_KIND());
                if (firExpression4 != null) {
                    Object objAccept4 = firExpression4.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(this.session, null));
                    if (!(objAccept4 instanceof EnumValue)) {
                        objAccept4 = null;
                    }
                    EnumValue enumValue2 = (EnumValue) objAccept4;
                    if (enumValue2 == null || (enumEntryName = enumValue2.getEnumEntryName()) == null) {
                        strAsString2 = null;
                    } else {
                        strAsString2 = enumEntryName.asString();
                    }
                } else {
                    strAsString2 = null;
                }
                if (strAsString2 != null) {
                    int iHashCode2 = strAsString2.hashCode();
                    if (iHashCode2 == -1625556271) {
                        strAsString2.equals("LANGUAGE_VERSION");
                    } else if (iHashCode2 != -1004593896) {
                        if (iHashCode2 == 953725811 && strAsString2.equals("API_VERSION")) {
                            builderNewBuilder.setVersionKind(ProtoBuf.VersionRequirement.VersionKind.API_VERSION);
                        }
                    } else if (strAsString2.equals("COMPILER_VERSION")) {
                        builderNewBuilder.setVersionKind(ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION);
                    }
                }
                FirExpression firExpression5 = mapping.get(RequireKotlinConstants.INSTANCE.getERROR_CODE());
                if (firExpression5 != null) {
                    Object objAccept5 = firExpression5.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(this.session, null));
                    if (!(objAccept5 instanceof IntValue)) {
                        objAccept5 = null;
                    }
                    IntValue intValue = (IntValue) objAccept5;
                    if (intValue != null) {
                        value3 = intValue.getValue();
                    }
                }
                if (value3 != null && value3.intValue() != -1) {
                    builderNewBuilder.setErrorCode(value3.intValue());
                }
                return builderNewBuilder;
            }
        }
        return null;
    }

    private final Visibility normalizeVisibility(FirPropertyAccessor declaration) {
        return declaration.getStatus().getVisibility().normalize();
    }

    private final int getClassifierId(FirClassLikeDeclaration declaration) {
        return getStringTable().getFqNameIndex(declaration);
    }

    private final int getClassifierId(ClassId classId) {
        return getStringTable().getQualifiedClassNameIndex(classId);
    }

    private final int getSimpleNameIndex(Name name2) {
        FirElementAwareStringTable stringTable = getStringTable();
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return stringTable.getStringIndex(strAsString);
    }

    private final int getTypeParameterId(FirTypeParameter typeParameter) {
        return this.typeParameters.intern(typeParameter);
    }

    /* JADX INFO: compiled from: FirElementSerializer.kt */
    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JB\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J0\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J0\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J0\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/fir/serialization/FirSerializerExtension;", "parentSerializer", "typeApproximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "createForLambda", "createTopLevel", "writeLanguageVersionRequirement", "", "languageFeature", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "writeVersionRequirement", "major", "minor", "patch", "versionKind", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement$VersionKind;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final FirElementSerializer createTopLevel(FirSession session, ScopeSession scopeSession, FirSerializerExtension extension, AbstractTypeApproximator typeApproximator, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            Intrinsics.checkNotNullParameter(extension, "extension");
            Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            return new FirElementSerializer(session, scopeSession, null, new Interner(null, 1, null), extension, new MutableTypeTable(), new MutableVersionRequirementTable(), false, typeApproximator, languageVersionSettings, null);
        }

        @JvmStatic
        public final FirElementSerializer createForLambda(FirSession session, ScopeSession scopeSession, FirSerializerExtension extension, AbstractTypeApproximator typeApproximator, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            Intrinsics.checkNotNullParameter(extension, "extension");
            Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            return new FirElementSerializer(session, scopeSession, null, new Interner(null, 1, null), extension, new MutableTypeTable(), null, true, typeApproximator, languageVersionSettings, null);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @JvmStatic
        public final FirElementSerializer create(FirSession session, ScopeSession scopeSession, FirClass klass, FirSerializerExtension extension, FirElementSerializer parentSerializer, AbstractTypeApproximator typeApproximator, LanguageVersionSettings languageVersionSettings) {
            FirElementSerializer firElementSerializerCreateTopLevel;
            MutableVersionRequirementTable mutableVersionRequirementTable;
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            Intrinsics.checkNotNullParameter(klass, "klass");
            Intrinsics.checkNotNullParameter(extension, "extension");
            Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            ClassId outerClassId = klass.getSymbol().getClassId().getOuterClassId();
            if (outerClassId != null && !outerClassId.isLocal()) {
                FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(outerClassId);
                Intrinsics.checkNotNull(classLikeSymbolByClassId);
                E fir = classLikeSymbolByClassId.getFir();
                Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirRegularClass");
                firElementSerializerCreateTopLevel = parentSerializer == null ? create(session, scopeSession, (FirRegularClass) fir, extension, null, typeApproximator, languageVersionSettings) : parentSerializer;
            } else {
                firElementSerializerCreateTopLevel = createTopLevel(session, scopeSession, extension, typeApproximator, languageVersionSettings);
            }
            FirClass firClass = klass;
            Interner interner = new Interner(firElementSerializerCreateTopLevel.typeParameters);
            MutableTypeTable mutableTypeTable = new MutableTypeTable();
            if (outerClassId != null && !VersionSpecificBehaviorKt.isKotlin1Dot4OrLater(extension.getMetadataVersion())) {
                mutableVersionRequirementTable = firElementSerializerCreateTopLevel.versionRequirementTable;
            } else {
                mutableVersionRequirementTable = new MutableVersionRequirementTable();
            }
            FirElementSerializer firElementSerializer = new FirElementSerializer(session, scopeSession, firClass, interner, extension, mutableTypeTable, mutableVersionRequirementTable, false, typeApproximator, languageVersionSettings, null);
            for (FirTypeParameterRef firTypeParameterRef : klass.getTypeParameters()) {
                if (firTypeParameterRef instanceof FirTypeParameter) {
                    firElementSerializer.typeParameters.intern(firTypeParameterRef);
                }
            }
            return firElementSerializer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int writeLanguageVersionRequirement(LanguageFeature languageFeature, MutableVersionRequirementTable versionRequirementTable) {
            LanguageVersion sinceVersion = languageFeature.getSinceVersion();
            Intrinsics.checkNotNull(sinceVersion);
            return writeVersionRequirement(sinceVersion.getMajor(), sinceVersion.getMinor(), 0, ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION, versionRequirementTable);
        }

        private final int writeVersionRequirement(int major, int minor, int patch, ProtoBuf.VersionRequirement.VersionKind versionKind, MutableVersionRequirementTable versionRequirementTable) {
            final ProtoBuf.VersionRequirement.Builder builderNewBuilder = ProtoBuf.VersionRequirement.newBuilder();
            new VersionRequirement.Version(major, minor, patch).encode(new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$Companion$writeVersionRequirement$requirement$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                    invoke(num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(int i) {
                    builderNewBuilder.setVersion(i);
                }
            }, new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$Companion$writeVersionRequirement$requirement$1$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                    invoke(num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(int i) {
                    builderNewBuilder.setVersionFull(i);
                }
            });
            if (versionKind != builderNewBuilder.getDefaultInstanceForType().getVersionKind()) {
                builderNewBuilder.setVersionKind(versionKind);
            }
            Intrinsics.checkNotNull(builderNewBuilder);
            return versionRequirementTable.get(builderNewBuilder);
        }
    }

    private final List<FirCallableDeclaration> memberDeclarations(final FirClass firClass) {
        final List listCreateListBuilder = CollectionsKt.createListBuilder();
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(firClass, this.session, this.scopeSession, false, (FirResolvePhase) null);
        final Function1<FirCallableSymbol<?>, Unit> function1 = new Function1<FirCallableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$memberDeclarations$$inlined$collectDeclarations$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirCallableSymbol<?> firCallableSymbol) {
                invoke2(firCallableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:22:0x005a  */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirCallableSymbol<?> it) {
                boolean z;
                Modality modality;
                Intrinsics.checkNotNullParameter(it, "it");
                Object fir = it.getFir();
                if (fir == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration");
                }
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) fir;
                ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firCallableDeclaration);
                FirClass firClass2 = firClass;
                if ((firClass2 instanceof FirRegularClass) && (((FirRegularClass) firClass2).getStatus().isData() || firClass.getStatus().isInline())) {
                    if (!Intrinsics.areEqual(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null ? coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId() : null, StandardClassIds.INSTANCE.getAny()) || (modality = firCallableDeclaration.getStatus().getModality()) == null || modality == Modality.FINAL) {
                        z = false;
                    } else {
                        z = true;
                    }
                } else {
                    z = false;
                }
                if (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
                    if (!z) {
                        return;
                    }
                } else if (!firCallableDeclaration.getStatus().isStatic() && !(firCallableDeclaration instanceof FirConstructor) && !Intrinsics.areEqual(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull, firClass.getSymbol().getLookupTag()) && !z) {
                    return;
                }
                listCreateListBuilder.add(firCallableDeclaration);
            }
        };
        FirTypeScope firTypeScope = firTypeScopeUnsubstitutedScope;
        FirContainingNamesAwareScopeKt.processAllFunctions(firTypeScope, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$memberDeclarations$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                function1.invoke(it);
            }
        });
        FirContainingNamesAwareScopeKt.processAllProperties(firTypeScope, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$memberDeclarations$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                function1.invoke(it);
            }
        });
        for (FirDeclaration firDeclaration : firClass.getDeclarations()) {
            if ((firDeclaration instanceof FirCallableDeclaration) && ((FirMemberDeclaration) firDeclaration).getStatus().isStatic()) {
                listCreateListBuilder.add(firDeclaration);
            }
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        List<FirDeclaration> declarations = firClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof FirCallableDeclaration) {
                arrayList.add(obj);
            }
        }
        final Map mapMapToIndex = org.jetbrains.kotlin.utils.CollectionsKt.mapToIndex(arrayList);
        List listSortedWith = CollectionsKt.sortedWith(listBuild, new Comparator() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$memberDeclarations$$inlined$collectDeclarations$2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                Integer num = (Integer) mapMapToIndex.get((FirCallableDeclaration) t);
                Integer numValueOf = Integer.valueOf(num != null ? num.intValue() : Integer.MAX_VALUE);
                Integer num2 = (Integer) mapMapToIndex.get((FirCallableDeclaration) t2);
                return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(num2 != null ? num2.intValue() : Integer.MAX_VALUE));
            }
        });
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : listSortedWith) {
            if (mapMapToIndex.containsKey((FirCallableDeclaration) obj2)) {
                arrayList2.add(obj2);
            } else {
                arrayList3.add(obj2);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        return CollectionsKt.plus((Collection) pair.component1(), (Iterable) CollectionsKt.sortedWith((List) pair.component2(), FirCallableDeclarationComparator.INSTANCE));
    }

    private final List<FirConstructor> constructors(final FirClass firClass) {
        final List listCreateListBuilder = CollectionsKt.createListBuilder();
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(firClass, this.session, this.scopeSession, false, (FirResolvePhase) null);
        final Function1<FirConstructorSymbol, Unit> function1 = new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$constructors$$inlined$collectDeclarations$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:22:0x005c  */
            /* JADX WARN: Multi-variable type inference failed */
            public final void invoke(FirConstructorSymbol it) {
                boolean z;
                Modality modality;
                Intrinsics.checkNotNullParameter(it, "it");
                D fir = it.getFir();
                if (fir == 0) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirConstructor");
                }
                FirConstructor firConstructor = (FirConstructor) fir;
                ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firConstructor);
                FirClass firClass2 = firClass;
                if ((firClass2 instanceof FirRegularClass) && (((FirRegularClass) firClass2).getStatus().isData() || firClass.getStatus().isInline())) {
                    if (!Intrinsics.areEqual(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null ? coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId() : null, StandardClassIds.INSTANCE.getAny()) || (modality = firConstructor.getStatus().getModality()) == null || modality == Modality.FINAL) {
                        z = false;
                    } else {
                        z = true;
                    }
                } else {
                    z = false;
                }
                if (ClassMembersKt.isSubstitutionOrIntersectionOverride(firConstructor)) {
                    if (!z) {
                        return;
                    }
                } else if (!firConstructor.getStatus().isStatic() && !(firConstructor instanceof FirConstructor) && !Intrinsics.areEqual(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull, firClass.getSymbol().getLookupTag()) && !z) {
                    return;
                }
                listCreateListBuilder.add(firConstructor);
            }
        };
        firTypeScopeUnsubstitutedScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$constructors$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                function1.invoke(it);
            }
        });
        for (FirDeclaration firDeclaration : firClass.getDeclarations()) {
            if ((firDeclaration instanceof FirConstructor) && ((FirMemberDeclaration) firDeclaration).getStatus().isStatic()) {
                listCreateListBuilder.add(firDeclaration);
            }
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        List<FirDeclaration> declarations = firClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof FirConstructor) {
                arrayList.add(obj);
            }
        }
        final Map mapMapToIndex = org.jetbrains.kotlin.utils.CollectionsKt.mapToIndex(arrayList);
        List listSortedWith = CollectionsKt.sortedWith(listBuild, new Comparator() { // from class: org.jetbrains.kotlin.fir.serialization.FirElementSerializer$constructors$$inlined$collectDeclarations$2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                Integer num = (Integer) mapMapToIndex.get((FirCallableDeclaration) t);
                Integer numValueOf = Integer.valueOf(num != null ? num.intValue() : Integer.MAX_VALUE);
                Integer num2 = (Integer) mapMapToIndex.get((FirCallableDeclaration) t2);
                return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(num2 != null ? num2.intValue() : Integer.MAX_VALUE));
            }
        });
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : listSortedWith) {
            if (mapMapToIndex.containsKey((FirCallableDeclaration) obj2)) {
                arrayList2.add(obj2);
            } else {
                arrayList3.add(obj2);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        return CollectionsKt.plus((Collection) pair.component1(), (Iterable) CollectionsKt.sortedWith((List) pair.component2(), FirCallableDeclarationComparator.INSTANCE));
    }

    private final FirAnnotation createAnnotationForCompilerDefinedTypeAttribute(ConeAttribute<?> attribute) {
        FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag((ClassId) MapsKt.getValue(CompilerConeAttributes.INSTANCE.getClassIdByCompilerAttributeKey(), attribute.getKey())), new ConeTypeProjection[0], false, null, 8, null));
        firAnnotationBuilder.setAnnotationTypeRef(firResolvedTypeRefBuilder.mo6809build());
        firAnnotationBuilder.setArgumentMapping(FirEmptyAnnotationArgumentMapping.INSTANCE);
        return firAnnotationBuilder.mo6809build();
    }

    private final Visibility normalizeVisibility(FirMemberDeclaration declaration) {
        return declaration.getStatus().getVisibility().normalize();
    }
}
