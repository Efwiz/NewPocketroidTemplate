package org.jetbrains.kotlin.fir.types;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ValueClassKind;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentationKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueClassRepresentationKt;
import org.jetbrains.kotlin.fir.declarations.ValueClassesUtilsKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeSystemContextKt;
import org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: ConeTypeContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ü\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b*\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0006\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u0016H\u0016J\u0018\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u0014H\u0016J\u0016\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00160\"H\u0016J\u0016\u0010\u001f\u001a\u00020\u00142\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00140\"H\u0016J\b\u0010#\u001a\u00020\u0014H\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0018\u001a\u00020\u0014H\u0016J\f\u0010&\u001a\u00020\u000f*\u00020'H\u0002J\f\u0010(\u001a\u00020)*\u00020\u0016H\u0016J\f\u0010*\u001a\u00020+*\u00020\u0014H\u0016J\u000e\u0010,\u001a\u0004\u0018\u00010-*\u00020\u0014H\u0016J\u000e\u0010.\u001a\u0004\u0018\u00010/*\u00020\u0014H\u0016J\u000e\u00100\u001a\u0004\u0018\u000101*\u000202H\u0016J\u000e\u00103\u001a\u0004\u0018\u000102*\u00020\u0016H\u0016J\u000e\u00104\u001a\u0004\u0018\u00010\u0014*\u00020\u0016H\u0016J\f\u00105\u001a\u000206*\u00020\u0016H\u0016J\u0013\u00107\u001a\b\u0012\u0004\u0012\u0002080\"*\u000209H\u0082\bJ\f\u0010:\u001a\u00020\u001a*\u00020-H\u0016J\u001c\u0010;\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\"*\u00020\u00142\u0006\u0010<\u001a\u00020\u0011H\u0016J\u0016\u0010=\u001a\u0004\u0018\u00010>*\u00020\u00162\u0006\u0010?\u001a\u00020@H\u0016J\u0014\u0010A\u001a\u000206*\u00020\u00162\u0006\u0010B\u001a\u00020)H\u0016J\u0012\u0010C\u001a\b\u0012\u0004\u0012\u0002060\"*\u00020\u0016H\u0016J\u0012\u0010D\u001a\b\u0012\u0004\u0012\u00020E0\"*\u00020\u0016H\u0016J\u000e\u0010F\u001a\u0004\u0018\u00010G*\u00020\u0011H\u0016J\u0012\u0010H\u001a\b\u0012\u0004\u0012\u00020E0\"*\u00020\u0016H\u0016J\f\u0010I\u001a\u00020J*\u00020\nH\u0016J\u0014\u0010K\u001a\u00020\n*\u00020\u00112\u0006\u0010B\u001a\u00020)H\u0016J\u0012\u0010L\u001a\b\u0012\u0004\u0012\u0002090\"*\u00020\u0011H\u0016J\u000e\u0010M\u001a\u0004\u0018\u00010N*\u00020\u0011H\u0016J\u000e\u0010O\u001a\u0004\u0018\u00010N*\u00020\u0011H\u0016J\f\u0010P\u001a\u00020\u0016*\u00020\nH\u0016J\u000e\u0010Q\u001a\u0004\u0018\u00010\u0016*\u00020\u0016H\u0016J\f\u0010R\u001a\u00020\u0016*\u000206H\u0016J\f\u0010S\u001a\u00020\u0011*\u00020\nH\u0016J\u000e\u0010T\u001a\u0004\u0018\u00010\n*\u00020\u0011H\u0016J\u000e\u0010U\u001a\u0004\u0018\u00010\u0016*\u00020\u0016H\u0016J\u0014\u0010V\u001a\u00020\u0016*\u00020\n2\u0006\u0010B\u001a\u00020)H\u0016J\u0012\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00160\"*\u00020\nH\u0016J \u0010X\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u00020\u00140Y\u0018\u00010\"*\u00020\u0011H\u0016J\f\u0010Z\u001a\u00020[*\u000206H\u0016J\f\u0010Z\u001a\u00020[*\u00020\nH\u0016J\u0014\u0010\\\u001a\u00020\u000f*\u00020\u00162\u0006\u0010?\u001a\u00020@H\u0016J\f\u0010]\u001a\u00020\u000f*\u00020\u0016H\u0016J\u0016\u0010^\u001a\u00020\u000f*\u00020\n2\b\u0010_\u001a\u0004\u0018\u00010\u0011H\u0016J\f\u0010`\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010a\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010b\u001a\u00020\u000f*\u00020\u0016H\u0016J\f\u0010c\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010d\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010e\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010f\u001a\u00020\u000f*\u00020\u0016H\u0016J\f\u0010f\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010g\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010h\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010i\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010j\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010k\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010l\u001a\u00020\u000f*\u00020\u0014H\u0016J\f\u0010m\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010n\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010o\u001a\u00020\u000f*\u00020\u0016H\u0016J\f\u0010p\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010q\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010r\u001a\u00020\u000f*\u00020\u0014H\u0016J\f\u0010s\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010t\u001a\u00020\u000f*\u00020\u0011H\u0016J\f\u0010u\u001a\u00020\u000f*\u00020\u0016H\u0016J\f\u0010v\u001a\u00020\u000f*\u00020-H\u0016J\f\u0010w\u001a\u00020\u000f*\u00020\u0014H\u0016J\f\u0010x\u001a\u00020\u000f*\u00020\u0016H\u0016J\f\u0010y\u001a\u00020\u000f*\u00020\nH\u0016J\f\u0010z\u001a\u00020\u000f*\u00020\u0014H\u0016J\f\u0010{\u001a\u00020\u000f*\u000206H\u0016J\f\u0010|\u001a\u00020\u000f*\u00020\u0014H\u0016J\f\u0010}\u001a\u00020\u000f*\u00020\u0014H\u0016J\f\u0010~\u001a\u00020\u000f*\u00020\u0014H\u0016J\f\u0010\u007f\u001a\u00020\u000f*\u00020\u0016H\u0016J\r\u0010\u0080\u0001\u001a\u00020\u000f*\u00020\u0011H\u0016J\r\u0010\u0081\u0001\u001a\u00020\u000f*\u00020\u0016H\u0016J\r\u0010\u0082\u0001\u001a\u00020\u0014*\u000202H\u0016J\u000f\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u0016*\u00020-H\u0016J\r\u0010\u0084\u0001\u001a\u00020)*\u00020\u0011H\u0016J\u0014\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u00020\u00160\u0086\u0001*\u00020\u0014H\u0016J\u000e\u0010\u0087\u0001\u001a\u000206*\u00030\u0088\u0001H\u0016J\u0016\u0010\u0089\u0001\u001a\u000206*\u0002062\u0007\u0010\u008a\u0001\u001a\u00020\u0016H\u0016J\u0014\u0010\u008b\u0001\u001a\t\u0012\u0004\u0012\u00020 0\u0086\u0001*\u00020\u0011H\u0016J\u0014\u0010\u008c\u0001\u001a\t\u0012\u0002\b\u0003\u0018\u00010\u008d\u0001*\u00020\u0011H\u0016J\u0010\u0010\u008e\u0001\u001a\u0005\u0018\u00010\u008f\u0001*\u00020\u0011H\u0002J\u000e\u0010\u0090\u0001\u001a\u00030\u0088\u0001*\u00020-H\u0016J\r\u0010\u0090\u0001\u001a\u00020\u0011*\u00020\u0014H\u0016J\u000e\u0010\u0091\u0001\u001a\u00030\u0092\u0001*\u00020\u0011H\u0002J\r\u0010\u0093\u0001\u001a\u00020\u0011*\u00020\u0011H\u0016J\r\u0010\u0094\u0001\u001a\u00020\u0014*\u000202H\u0016J\r\u0010\u0095\u0001\u001a\u00020)*\u00020\nH\u0016J\u0016\u0010\u0096\u0001\u001a\u00020\u0014*\u00020\u00142\u0007\u0010\u0097\u0001\u001a\u00020\u000fH\u0016R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0098\u0001À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemOptimizationContext;", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "areEqualTypeConstructors", "", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "arrayType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "componentType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "captureFromArguments", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", SpecTypePair.NAME_types, "", "nullableAnyType", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "allBoundsAreNullableOrUnresolved", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "argumentsCount", "", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/fir/types/ConeDynamicType;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "bounds", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "captureStatus", "fastCorrespondingSupertypes", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "getAnnotationFirstArgumentValue", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getArgument", "index", "getArguments", "getAttributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "getClassFqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getCustomAttributes", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getParameter", "getParameters", "getPrimitiveArrayType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveType", "getRepresentativeUpperBound", "getSubstitutedUnderlyingType", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUnsubstitutedUnderlyingType", "getUpperBound", "getUpperBounds", "getValueClassProperties", "Lkotlin/Pair;", "getVariance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "hasAnnotation", "hasCustomAttributes", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isArrayOrNullableArray", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDenotable", "isError", "isFinalClassOrEnumEntryOrAnnotationClassConstructor", "isInlineClass", "isInnerClass", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isInterfaceOrAnnotationClass", "isIntersection", "isLocalType", "isMarkedNullable", "isMultiFieldValueClass", "isNothingConstructor", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isRawType", "isReified", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeVariableType", "isUnderKotlinPackage", "isUninferredParameter", "lowerBound", "lowerType", "parametersCount", "possibleIntegerTypes", "", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceType", "newType", "supertypes", "toClassLikeSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "toFirRegularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "typeConstructor", "unknownConstructorError", "", "unwrapStubTypeVariableConstructor", "upperBound", "upperBoundCount", "withNullability", "nullable", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConeTypeContext extends TypeSystemCommonBackendContext, TypeCheckerProviderContext, TypeSystemContext, TypeSystemOptimizationContext {

    /* JADX INFO: compiled from: ConeTypeContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProjectionKind.values().length];
            try {
                iArr[ProjectionKind.STAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionKind.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProjectionKind.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProjectionKind.INVARIANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: getSession */
    FirSession get$session();

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* bridge */ /* synthetic */ default KotlinTypeMarker intersectTypes(List list) {
        return intersectTypes((List<? extends KotlinTypeMarker>) list);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeIntegerLiteralType;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeIntegerLiteralConstantType;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeIntegerConstantOperatorType;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (typeConstructorMarker instanceof ConeClassLikeLookupTag) {
            return ((ConeClassLikeLookupTag) typeConstructorMarker).getClassId().isLocal();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (typeConstructorMarker instanceof ConeClassLikeLookupTag) {
            return Intrinsics.areEqual(((ConeClassLikeLookupTag) typeConstructorMarker).getName(), SpecialNames.ANONYMOUS);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        if (!(typeVariableTypeConstructorMarker instanceof ConeTypeVariableTypeConstructor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeTypeVariableTypeConstructor) typeVariableTypeConstructorMarker).getOriginalTypeParameter();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Collection<ConeClassLikeType> possibleTypes;
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        ConeIntegerLiteralType coneIntegerLiteralType = simpleTypeMarker instanceof ConeIntegerLiteralType ? (ConeIntegerLiteralType) simpleTypeMarker : null;
        return (coneIntegerLiteralType == null || (possibleTypes = coneIntegerLiteralType.getPossibleTypes()) == null) ? CollectionsKt.emptyList() : possibleTypes;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return FirCorrespondingSupertypesCacheKt.getCorrespondingSupertypesCache(get$session()).getCorrespondingSupertypes((ConeKotlinType) simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return simpleTypeMarker instanceof ConeIntegerLiteralType;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        boolean z = kotlinTypeMarker instanceof ConeKotlinType;
        if (kotlinTypeMarker instanceof ConeClassLikeType) {
            return TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) kotlinTypeMarker, get$session(), null, 2, null);
        }
        if (kotlinTypeMarker instanceof ConeSimpleKotlinType) {
            return (SimpleTypeMarker) kotlinTypeMarker;
        }
        if (kotlinTypeMarker instanceof ConeFlexibleType) {
            return null;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unknown simpleType: " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass()), null);
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments2 = kotlinIllegalArgumentExceptionWithAttachments;
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, z ? (ConeKotlinType) kotlinTypeMarker : null);
        kotlinIllegalArgumentExceptionWithAttachments2.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        boolean z = kotlinTypeMarker instanceof ConeKotlinType;
        return kotlinTypeMarker instanceof ConeFlexibleType ? (ConeFlexibleType) kotlinTypeMarker : null;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        boolean z = kotlinTypeMarker instanceof ConeKotlinType;
        boolean z2 = kotlinTypeMarker instanceof ConeErrorType;
        return z2 || z2 || isError(typeConstructor(kotlinTypeMarker)) || ((kotlinTypeMarker instanceof ConeClassLikeType) && (((ConeClassLikeType) kotlinTypeMarker).getLookupTag() instanceof ConeClassLikeErrorLookupTag));
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        boolean z = kotlinTypeMarker instanceof ConeKotlinType;
        return (kotlinTypeMarker instanceof ConeErrorType) && ((ConeErrorType) kotlinTypeMarker).getIsUninferredParameter();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default ConeDynamicType asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        boolean z = flexibleTypeMarker instanceof ConeKotlinType;
        if (flexibleTypeMarker instanceof ConeDynamicType) {
            return (ConeDynamicType) flexibleTypeMarker;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeUtilsKt.isRaw((ConeKotlinType) kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        if (!(flexibleTypeMarker instanceof ConeFlexibleType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeFlexibleType) flexibleTypeMarker).getUpperBound();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        if (!(flexibleTypeMarker instanceof ConeFlexibleType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeFlexibleType) flexibleTypeMarker).getLowerBound();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return simpleTypeMarker instanceof ConeCapturedType ? (ConeCapturedType) simpleTypeMarker : null;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return simpleTypeMarker instanceof ConeDefinitelyNotNullType ? (ConeDefinitelyNotNullType) simpleTypeMarker : null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeExpansionUtilsKt.fullyExpandedType((ConeKotlinType) simpleTypeMarker, get$session()).getNullability().isNullable();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        TypeArgumentListMarker typeArgumentListMarkerWithNullability$default = TypeUtilsKt.withNullability$default(TypeExpansionUtilsKt.fullyExpandedType((ConeKotlinType) simpleTypeMarker, get$session()), ConeNullability.INSTANCE.create(z), TypeComponentsKt.getTypeContext(get$session()), null, false, 12, null);
        Intrinsics.checkNotNull(typeArgumentListMarkerWithNullability$default, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.SimpleTypeMarker");
        return (SimpleTypeMarker) typeArgumentListMarkerWithNullability$default;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (simpleTypeMarker instanceof ConeClassLikeType) {
            return ((ConeClassLikeType) simpleTypeMarker).getLookupTag();
        }
        if (simpleTypeMarker instanceof ConeTypeParameterType) {
            return ((ConeTypeParameterType) simpleTypeMarker).getLookupTag();
        }
        if (simpleTypeMarker instanceof ConeCapturedType) {
            return ((ConeCapturedType) simpleTypeMarker).getConstructor();
        }
        if (simpleTypeMarker instanceof ConeTypeVariableType) {
            return ((ConeTypeVariableType) simpleTypeMarker).getLookupTag();
        }
        if (simpleTypeMarker instanceof ConeIntersectionType) {
            return (TypeConstructorMarker) simpleTypeMarker;
        }
        if (simpleTypeMarker instanceof ConeStubType) {
            return ((ConeStubType) simpleTypeMarker).getConstructor();
        }
        if (simpleTypeMarker instanceof ConeDefinitelyNotNullType) {
            return typeConstructor((SimpleTypeMarker) ((ConeDefinitelyNotNullType) simpleTypeMarker).getOriginal());
        }
        if (simpleTypeMarker instanceof ConeIntegerLiteralType) {
            return (TypeConstructorMarker) simpleTypeMarker;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unknown simpleType: " + simpleTypeMarker.getClass(), null);
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments2 = kotlinIllegalArgumentExceptionWithAttachments;
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, simpleTypeMarker instanceof ConeKotlinType ? (ConeKotlinType) simpleTypeMarker : null);
        kotlinIllegalArgumentExceptionWithAttachments2.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        if (!(capturedTypeMarker instanceof ConeCapturedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeCapturedType) capturedTypeMarker).getConstructor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        if (!(capturedTypeMarker instanceof ConeCapturedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeCapturedType) capturedTypeMarker).getCaptureStatus();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        if (!(capturedTypeConstructorMarker instanceof ConeCapturedTypeConstructor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeCapturedTypeConstructor) capturedTypeConstructorMarker).getProjection();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ConeKotlinType) kotlinTypeMarker).getTypeArguments().length;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        TypeArgumentMarker typeArgumentMarker = (ConeTypeProjection) ArraysKt.getOrNull(((ConeKotlinType) kotlinTypeMarker).getTypeArguments(), i);
        if (typeArgumentMarker == null) {
            typeArgumentMarker = ConeStarProjection.INSTANCE;
        }
        return typeArgumentMarker;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ArraysKt.toList(((ConeKotlinType) kotlinTypeMarker).getTypeArguments());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (TypeArgumentMarker) kotlinTypeMarker;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        if (!(capturedTypeMarker instanceof ConeCapturedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!ConeTypeUtilsKt.isMarkedNullable((ConeKotlinType) capturedTypeMarker)) {
            return ((ConeCapturedType) capturedTypeMarker).getLowerType();
        }
        ConeKotlinType lowerType = ((ConeCapturedType) capturedTypeMarker).getLowerType();
        if (lowerType != null) {
            return makeNullable(lowerType);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        if (!(typeArgumentMarker instanceof ConeTypeProjection)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return typeArgumentMarker instanceof ConeStarProjection;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        if (!(typeArgumentMarker instanceof ConeTypeProjection)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        int i = WhenMappings.$EnumSwitchMapping$0[((ConeTypeProjection) typeArgumentMarker).getKind().ordinal()];
        if (i == 1) {
            throw new IllegalStateException("Nekorrektno (c) Stas".toString());
        }
        if (i == 2) {
            return TypeVariance.IN;
        }
        if (i == 3) {
            return TypeVariance.OUT;
        }
        if (i == 4) {
            return TypeVariance.INV;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        if (!(typeArgumentMarker instanceof ConeTypeProjection)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(typeArgumentMarker instanceof ConeKotlinTypeProjection)) {
            throw new IllegalArgumentException("No type for StarProjection".toString());
        }
        return ((ConeKotlinTypeProjection) typeArgumentMarker).getType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        if (!(typeArgumentMarker instanceof ConeKotlinTypeProjection)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(newType instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (typeArgumentMarker instanceof ConeKotlinType) {
            return (TypeArgumentMarker) newType;
        }
        if (typeArgumentMarker instanceof ConeStarProjection) {
            return ConeStarProjection.INSTANCE;
        }
        if (typeArgumentMarker instanceof ConeKotlinTypeProjectionOut) {
            return new ConeKotlinTypeProjectionOut((ConeKotlinType) newType);
        }
        if (typeArgumentMarker instanceof ConeKotlinTypeProjectionIn) {
            return new ConeKotlinTypeProjectionIn((ConeKotlinType) newType);
        }
        if (typeArgumentMarker instanceof ConeKotlinTypeConflictingProjection) {
            return new ConeKotlinTypeConflictingProjection((ConeKotlinType) newType);
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (typeConstructorMarker instanceof ConeTypeParameterLookupTag ? true : typeConstructorMarker instanceof ConeCapturedTypeConstructor ? true : typeConstructorMarker instanceof ErrorTypeConstructor ? true : typeConstructorMarker instanceof ConeTypeVariableTypeConstructor ? true : typeConstructorMarker instanceof ConeIntersectionType) {
            return 0;
        }
        if (typeConstructorMarker instanceof ConeClassLikeLookupTag) {
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol((ConeClassLikeLookupTag) typeConstructorMarker, get$session());
            if (symbol instanceof FirAnonymousObjectSymbol) {
                return ((FirAnonymousObject) ((FirAnonymousObjectSymbol) symbol).getFir()).getTypeParameters().size();
            }
            if (symbol instanceof FirRegularClassSymbol) {
                return ((FirRegularClass) ((FirRegularClassSymbol) symbol).getFir()).getTypeParameters().size();
            }
            if (symbol instanceof FirTypeAliasSymbol) {
                return ((FirTypeAlias) ((FirTypeAliasSymbol) symbol).getFir()).getTypeParameters().size();
            }
            return 0;
        }
        if ((typeConstructorMarker instanceof ConeIntegerLiteralType) || (typeConstructorMarker instanceof ConeStubTypeConstructor)) {
            return 0;
        }
        unknownConstructorError(typeConstructorMarker);
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FirClassLikeSymbol<?> classLikeSymbol = toClassLikeSymbol(typeConstructorMarker);
        if (classLikeSymbol instanceof FirAnonymousObjectSymbol) {
            return ((FirAnonymousObject) ((FirAnonymousObjectSymbol) classLikeSymbol).getFir()).getTypeParameters().get(i).getSymbol().getLookupTag();
        }
        if (classLikeSymbol instanceof FirRegularClassSymbol) {
            return ((FirRegularClass) ((FirRegularClassSymbol) classLikeSymbol).getFir()).getTypeParameters().get(i).getSymbol().getLookupTag();
        }
        if (classLikeSymbol instanceof FirTypeAliasSymbol) {
            return ((FirTypeAlias) ((FirTypeAliasSymbol) classLikeSymbol).getFir()).getTypeParameters().get(i).getSymbol().getLookupTag();
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unexpected FirClassLikeSymbol " + classLikeSymbol + " for " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass()), null);
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments2 = kotlinIllegalArgumentExceptionWithAttachments;
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirLookupTagEntry(exceptionAttachmentBuilder, "lookupTag", typeConstructorMarker instanceof ConeClassLikeLookupTag ? (ConeClassLikeLookupTag) typeConstructorMarker : null);
        kotlinIllegalArgumentExceptionWithAttachments2.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default List<ConeTypeParameterLookupTag> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FirClassLikeSymbol<?> classLikeSymbol = toClassLikeSymbol(typeConstructorMarker);
        if (!(classLikeSymbol instanceof FirAnonymousObjectSymbol)) {
            if (!(classLikeSymbol instanceof FirRegularClassSymbol)) {
                if (!(classLikeSymbol instanceof FirTypeAliasSymbol)) {
                    return CollectionsKt.emptyList();
                }
                List<FirTypeParameter> typeParameters = ((FirTypeAlias) ((FirTypeAliasSymbol) classLikeSymbol).getFir()).getTypeParameters();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
                Iterator<T> it = typeParameters.iterator();
                while (it.hasNext()) {
                    arrayList.add(((FirTypeParameter) it.next()).getSymbol().getLookupTag());
                }
                return arrayList;
            }
            List<FirTypeParameterRef> typeParameters2 = ((FirRegularClass) ((FirRegularClassSymbol) classLikeSymbol).getFir()).getTypeParameters();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
            Iterator<T> it2 = typeParameters2.iterator();
            while (it2.hasNext()) {
                arrayList2.add(((FirTypeParameterRef) it2.next()).getSymbol().getLookupTag());
            }
            return arrayList2;
        }
        List<FirTypeParameterRef> typeParameters3 = ((FirAnonymousObject) ((FirAnonymousObjectSymbol) classLikeSymbol).getFir()).getTypeParameters();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters3, 10));
        Iterator<T> it3 = typeParameters3.iterator();
        while (it3.hasNext()) {
            arrayList3.add(((FirTypeParameterRef) it3.next()).getSymbol().getLookupTag());
        }
        return arrayList3;
    }

    default FirClassLikeSymbol<?> toClassLikeSymbol(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        ConeClassLikeLookupTag coneClassLikeLookupTag = typeConstructorMarker instanceof ConeClassLikeLookupTag ? (ConeClassLikeLookupTag) typeConstructorMarker : null;
        if (coneClassLikeLookupTag != null) {
            return LookupTagUtilsKt.toSymbol(coneClassLikeLookupTag, get$session());
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default Collection<ConeKotlinType> supertypes(TypeConstructorMarker typeConstructorMarker) {
        List<ConeClassLikeType> listListOfNotNull;
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (typeConstructorMarker instanceof ErrorTypeConstructor) {
            return CollectionsKt.emptyList();
        }
        if (typeConstructorMarker instanceof ConeStubTypeConstructor) {
            return CollectionsKt.listOf(get$session().getBuiltinTypes().getNullableAnyType().getType());
        }
        if (typeConstructorMarker instanceof ConeTypeVariableTypeConstructor) {
            return CollectionsKt.emptyList();
        }
        if (!(typeConstructorMarker instanceof ConeTypeParameterLookupTag)) {
            if (typeConstructorMarker instanceof ConeClassLikeLookupTag) {
                FirClassLikeSymbol<?> classLikeSymbol = toClassLikeSymbol(typeConstructorMarker);
                if (classLikeSymbol != null) {
                    FirLazyDeclarationResolverKt.lazyResolveToPhase(classLikeSymbol, FirResolvePhase.TYPES);
                }
                if (classLikeSymbol instanceof FirClassSymbol) {
                    listListOfNotNull = FirDeclarationUtilKt.getSuperConeTypes((FirClass) ((FirClassSymbol) classLikeSymbol).getFir());
                } else {
                    listListOfNotNull = classLikeSymbol instanceof FirTypeAliasSymbol ? CollectionsKt.listOfNotNull(FirDeclarationUtilKt.getExpandedConeType((FirTypeAlias) ((FirTypeAliasSymbol) classLikeSymbol).getFir())) : CollectionsKt.listOf(get$session().getBuiltinTypes().getAnyType().getType());
                }
                return listListOfNotNull;
            }
            if (typeConstructorMarker instanceof ConeCapturedTypeConstructor) {
                List<ConeKotlinType> supertypes = ((ConeCapturedTypeConstructor) typeConstructorMarker).getSupertypes();
                if (supertypes == null) {
                    supertypes = CollectionsKt.emptyList();
                }
                return supertypes;
            }
            if (typeConstructorMarker instanceof ConeIntersectionType) {
                return ((ConeIntersectionType) typeConstructorMarker).getIntersectedTypes();
            }
            if (typeConstructorMarker instanceof ConeIntegerLiteralType) {
                return ((ConeIntegerLiteralType) typeConstructorMarker).getSupertypes();
            }
            unknownConstructorError(typeConstructorMarker);
            throw null;
        }
        List<FirTypeRef> bounds = ((FirTypeParameter) ((ConeTypeParameterLookupTag) typeConstructorMarker).getSymbol().getFir()).getBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds, 10));
        Iterator<T> it = bounds.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirTypeRef) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeIntersectionType;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeClassLikeLookupTag;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        FirClassLikeSymbol<?> classLikeSymbol;
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        ConeClassLikeLookupTag coneClassLikeLookupTag = typeConstructorMarker instanceof ConeClassLikeLookupTag ? (ConeClassLikeLookupTag) typeConstructorMarker : null;
        FirClassLikeDeclaration firClassLikeDeclaration = (coneClassLikeLookupTag == null || (classLikeSymbol = toClassLikeSymbol(coneClassLikeLookupTag)) == null) ? null : (FirClassLikeDeclaration) classLikeSymbol.getFir();
        FirClass firClass = firClassLikeDeclaration instanceof FirClass ? (FirClass) firClassLikeDeclaration : null;
        return (firClass != null ? firClass.getClassKind() : null) == ClassKind.INTERFACE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeSystemContextKt.convertVariance(((FirTypeParameter) ((ConeTypeParameterLookupTag) typeParameterMarker).getSymbol().getFir()).getVariance());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((FirTypeParameter) ((ConeTypeParameterLookupTag) typeParameterMarker).getSymbol().getFir()).getBounds().size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return FirTypeUtilsKt.getConeType(((FirTypeParameter) ((ConeTypeParameterLookupTag) typeParameterMarker).getSymbol().getFir()).getBounds().get(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        List<FirTypeRef> bounds = ((FirTypeParameter) ((ConeTypeParameterLookupTag) typeParameterMarker).getSymbol().getFir()).getBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(bounds, 10));
        Iterator<T> it = bounds.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirTypeRef) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (TypeConstructorMarker) typeParameterMarker;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean hasRecursiveBounds(final TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ConeTypeParameterLookupTag coneTypeParameterLookupTag = (ConeTypeParameterLookupTag) typeParameterMarker;
        FirLazyDeclarationResolverKt.lazyResolveToPhase(coneTypeParameterLookupTag.getTypeParameterSymbol(), FirResolvePhase.TYPES);
        List<FirTypeRef> bounds = ((FirTypeParameter) coneTypeParameterLookupTag.getSymbol().getFir()).getBounds();
        if ((bounds instanceof Collection) && bounds.isEmpty()) {
            return false;
        }
        for (FirTypeRef firTypeRef : bounds) {
            if (ConeTypeUtilsKt.contains(FirTypeUtilsKt.getConeType(firTypeRef), new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.types.ConeTypeContext$hasRecursiveBounds$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ConeKotlinType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(this.this$0.typeConstructor(it), this.this$0.getTypeConstructor(typeParameterMarker)));
                }
            }) && (typeConstructorMarker == null || Intrinsics.areEqual(typeConstructor(FirTypeUtilsKt.getConeType(firTypeRef)), typeConstructorMarker))) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        if ((c1 instanceof ErrorTypeConstructor) || (c2 instanceof ErrorTypeConstructor)) {
            return false;
        }
        return Intrinsics.areEqual(c1, c2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (typeConstructorMarker instanceof ConeClassLikeLookupTag ? true : typeConstructorMarker instanceof ConeTypeParameterLookupTag) {
            return true;
        }
        if (typeConstructorMarker instanceof ConeStubTypeConstructor ? true : typeConstructorMarker instanceof ConeCapturedTypeConstructor ? true : typeConstructorMarker instanceof ErrorTypeConstructor ? true : typeConstructorMarker instanceof ConeTypeVariableTypeConstructor ? true : typeConstructorMarker instanceof ConeIntegerLiteralType ? true : typeConstructorMarker instanceof ConeIntersectionType) {
            return false;
        }
        unknownConstructorError(typeConstructorMarker);
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FirClassLikeSymbol<?> classLikeSymbol = toClassLikeSymbol(typeConstructorMarker);
        if (classLikeSymbol == null) {
            return false;
        }
        if (classLikeSymbol instanceof FirAnonymousObjectSymbol) {
            return true;
        }
        FirRegularClassSymbol firRegularClassSymbol = classLikeSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbol : null;
        if (firRegularClassSymbol == null) {
            return false;
        }
        FirRegularClass firRegularClass = (FirRegularClass) firRegularClassSymbol.getFir();
        return (firRegularClass.getStatus().getModality() != Modality.FINAL || firRegularClass.getClassKind() == ClassKind.ENUM_ENTRY || firRegularClass.getClassKind() == ClassKind.ANNOTATION_CLASS) ? false : true;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeUtilsKt.captureFromExpressionInternal(this, (ConeKotlinType) type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        if (!(type instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (SimpleTypeMarker) TypeUtilsKt.captureFromArgumentsInternal(this, (ConeKotlinType) type, status);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (!(simpleTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (TypeArgumentListMarker) simpleTypeMarker;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    default boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        if (!(a instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (b instanceof ConeKotlinType) {
            return ((ConeKotlinType) a).getTypeArguments() == ((ConeKotlinType) b).getTypeArguments();
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return (typeConstructorMarker instanceof ConeClassLikeLookupTag) && Intrinsics.areEqual(((ConeClassLikeLookupTag) typeConstructorMarker).getClassId(), StandardClassIds.INSTANCE.getAny());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return (typeConstructorMarker instanceof ConeClassLikeLookupTag) && Intrinsics.areEqual(((ConeClassLikeLookupTag) typeConstructorMarker).getClassId(), StandardClassIds.INSTANCE.getNothing());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (isError(simpleTypeMarker)) {
            return false;
        }
        if (simpleTypeMarker instanceof ConeCapturedType) {
            return true;
        }
        if ((simpleTypeMarker instanceof ConeTypeVariableType) || (simpleTypeMarker instanceof ConeIntersectionType)) {
            return false;
        }
        if ((simpleTypeMarker instanceof ConeIntegerLiteralType) || (simpleTypeMarker instanceof ConeStubType) || (simpleTypeMarker instanceof ConeDefinitelyNotNullType)) {
            return true;
        }
        if (!(simpleTypeMarker instanceof ConeLookupTagBasedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeConstructor(simpleTypeMarker);
        return (typeConstructorMarkerTypeConstructor instanceof ConeClassLikeLookupTag) || (typeConstructorMarkerTypeConstructor instanceof ConeTypeParameterLookupTag);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (simpleTypeMarker instanceof ConeClassLikeType) {
            return ConeBuiltinTypeUtilsKt.isPrimitive((ConeKotlinType) simpleTypeMarker);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return CollectionsKt.toList(((ConeKotlinType) kotlinTypeMarker).getAttributes());
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Map<KClass<? extends ConeAttribute<?>>, ClassId> classIdByCompilerAttributeKey = CompilerConeAttributes.INSTANCE.getClassIdByCompilerAttributeKey();
        Iterable<ConeAttribute> attributes = ((ConeKotlinType) kotlinTypeMarker).getAttributes();
        if ((attributes instanceof Collection) && ((Collection) attributes).isEmpty()) {
            return false;
        }
        for (ConeAttribute coneAttribute : attributes) {
            if (!classIdByCompilerAttributeKey.containsKey(coneAttribute.getKey()) && !(coneAttribute instanceof CustomAnnotationTypeAttribute)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Map<KClass<? extends ConeAttribute<?>>, ClassId> classIdByCompilerAttributeKey = CompilerConeAttributes.INSTANCE.getClassIdByCompilerAttributeKey();
        ConeAttributes attributes = ((ConeKotlinType) kotlinTypeMarker).getAttributes();
        ArrayList arrayList = new ArrayList();
        for (ConeAttribute<?> coneAttribute : attributes) {
            ConeAttribute<?> coneAttribute2 = coneAttribute;
            if (!classIdByCompilerAttributeKey.containsKey(coneAttribute2.getKey()) && !(coneAttribute2 instanceof CustomAnnotationTypeAttribute)) {
                arrayList.add(coneAttribute);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return simpleTypeMarker instanceof ConeStubType;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return simpleTypeMarker instanceof ConeStubTypeForTypeVariableInSubtyping;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return simpleTypeMarker instanceof ConeStubTypeForChainInference;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (!(typeConstructorMarker instanceof ConeStubTypeConstructor)) {
            return typeConstructorMarker;
        }
        ConeStubTypeConstructor coneStubTypeConstructor = (ConeStubTypeConstructor) typeConstructorMarker;
        return (coneStubTypeConstructor.isTypeVariableInSubtyping() || coneStubTypeConstructor.isForFixation()) ? typeConstructorMarker : coneStubTypeConstructor.getVariable().getTypeConstructor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    default SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        ConeTypeIntersector coneTypeIntersector = ConeTypeIntersector.INSTANCE;
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeInferenceContext");
        TypeArgumentListMarker typeArgumentListMarkerIntersectTypes = coneTypeIntersector.intersectTypes((ConeInferenceContext) this, types);
        Intrinsics.checkNotNull(typeArgumentListMarkerIntersectTypes, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.SimpleTypeMarker");
        return (SimpleTypeMarker) typeArgumentListMarkerIntersectTypes;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default ConeKotlinType intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        ConeTypeIntersector coneTypeIntersector = ConeTypeIntersector.INSTANCE;
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeInferenceContext");
        return coneTypeIntersector.intersectTypes((ConeInferenceContext) this, types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        ConeClassLikeType coneClassLikeTypeDirectExpansionType$default;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (ConeTypeUtilsKt.isMarkedNullable((ConeKotlinType) kotlinTypeMarker)) {
            return true;
        }
        if (kotlinTypeMarker instanceof ConeFlexibleType) {
            return isNullableType(((ConeFlexibleType) kotlinTypeMarker).getUpperBound());
        }
        if (kotlinTypeMarker instanceof ConeTypeParameterType) {
            return allBoundsAreNullableOrUnresolved(((ConeTypeParameterType) kotlinTypeMarker).getLookupTag().getSymbol());
        }
        if (kotlinTypeMarker instanceof ConeStubType) {
            TypeParameterMarker originalTypeParameter = ((ConeStubType) kotlinTypeMarker).getConstructor().getVariable().getDefaultType().getLookupTag().getOriginalTypeParameter();
            ConeTypeParameterLookupTag coneTypeParameterLookupTag = originalTypeParameter instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) originalTypeParameter : null;
            FirTypeParameterSymbol symbol = coneTypeParameterLookupTag != null ? coneTypeParameterLookupTag.getSymbol() : null;
            if (symbol == null || allBoundsAreNullableOrUnresolved(symbol)) {
                return true;
            }
        } else if (!(kotlinTypeMarker instanceof ConeIntersectionType)) {
            if ((kotlinTypeMarker instanceof ConeClassLikeType) && (coneClassLikeTypeDirectExpansionType$default = TypeExpansionUtilsKt.directExpansionType$default((ConeClassLikeType) kotlinTypeMarker, get$session(), null, 2, null)) != null) {
                return isNullableType(coneClassLikeTypeDirectExpansionType$default);
            }
        } else {
            Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) kotlinTypeMarker).getIntersectedTypes();
            if ((intersectedTypes instanceof Collection) && intersectedTypes.isEmpty()) {
                return true;
            }
            Iterator<T> it = intersectedTypes.iterator();
            while (it.hasNext()) {
                if (!isNullableType((ConeKotlinType) it.next())) {
                }
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private default boolean allBoundsAreNullableOrUnresolved(FirTypeParameterSymbol firTypeParameterSymbol) {
        for (FirTypeRef firTypeRef : ((FirTypeParameter) firTypeParameterSymbol.getFir()).getBounds()) {
            if (!(firTypeRef instanceof FirResolvedTypeRef)) {
                return true;
            }
            if (!isNullableType(((FirResolvedTypeRef) firTypeRef).getType())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private default FirRegularClass toFirRegularClass(TypeConstructorMarker typeConstructorMarker) {
        FirClassLikeSymbol<?> classLikeSymbol = toClassLikeSymbol(typeConstructorMarker);
        FirClassLikeDeclaration firClassLikeDeclaration = classLikeSymbol != null ? (FirClassLikeDeclaration) classLikeSymbol.getFir() : null;
        if (firClassLikeDeclaration instanceof FirRegularClass) {
            return (FirRegularClass) firClassLikeDeclaration;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    default SimpleTypeMarker nullableAnyType() {
        return get$session().getBuiltinTypes().getNullableAnyType().getType();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default SimpleTypeMarker arrayType(KotlinTypeMarker componentType) {
        Intrinsics.checkNotNullParameter(componentType, "componentType");
        if (!(componentType instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ArrayUtilsKt.createArrayType$default((ConeTypeProjection) componentType, false, false, 2, null);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return Intrinsics.areEqual(ConeTypeUtilsKt.getClassId((ConeKotlinType) kotlinTypeMarker), StandardClassIds.INSTANCE.getArray());
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FirRegularClass firRegularClass = toFirRegularClass(typeConstructorMarker);
        if (firRegularClass == null) {
            return false;
        }
        ClassKind classKind = firRegularClass.getClassKind();
        return classKind == ClassKind.ENUM_ENTRY || classKind == ClassKind.ANNOTATION_CLASS || classKind == ClassKind.OBJECT || (classKind == ClassKind.CLASS && firRegularClass.getSymbol().getResolvedStatus().getModality() == Modality.FINAL);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        ConeKotlinType coneKotlinTypeFullyExpandedType;
        ClassId classId;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        KClass<? extends ConeAttribute<?>> kClass = CompilerConeAttributes.INSTANCE.getCompilerAttributeKeyByFqName().get(fqName);
        if (kClass != null) {
            return ((ConeKotlinType) kotlinTypeMarker).getAttributes().contains(kClass);
        }
        List<FirAnnotation> customAnnotations = CustomAnnotationTypeAttributeKt.getCustomAnnotations(((ConeKotlinType) kotlinTypeMarker).getAttributes());
        if ((customAnnotations instanceof Collection) && customAnnotations.isEmpty()) {
            return false;
        }
        Iterator<T> it = customAnnotations.iterator();
        while (it.hasNext()) {
            FirTypeRef typeRef = ((FirAnnotation) it.next()).getTypeRef();
            FqName fqNameAsSingleFqName = null;
            FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            if (type != null && (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(type, get$session())) != null && (classId = ConeTypeUtilsKt.getClassId(coneKotlinTypeFullyExpandedType)) != null) {
                fqNameAsSingleFqName = classId.asSingleFqName();
            }
            if (Intrinsics.areEqual(fqNameAsSingleFqName, fqName)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        Object next;
        FirAnnotationContainer expression;
        ConeKotlinType type;
        ConeKotlinType coneKotlinTypeFullyExpandedType;
        ClassId classId;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Iterator<T> it = CustomAnnotationTypeAttributeKt.getCustomAnnotations(((ConeKotlinType) kotlinTypeMarker).getAttributes()).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            FirTypeRef typeRef = ((FirAnnotation) next).getTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
            type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
        } while (!Intrinsics.areEqual((type == null || (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(type, get$session())) == null || (classId = ConeTypeUtilsKt.getClassId(coneKotlinTypeFullyExpandedType)) == null) ? null : classId.asSingleFqName(), fqName));
        FirAnnotation firAnnotation = (FirAnnotation) next;
        if (firAnnotation == null || (expression = (FirExpression) CollectionsKt.firstOrNull(firAnnotation.getArgumentMapping().getMapping().values())) == null) {
            return null;
        }
        if (expression instanceof FirVarargArgumentsExpression) {
            expression = (FirExpression) CollectionsKt.firstOrNull((List) ((FirVarargArgumentsExpression) expression).getArguments());
        } else if (expression instanceof FirArrayLiteral) {
            expression = (FirExpression) CollectionsKt.firstOrNull((List) ((FirCall) expression).getArgumentList().getArguments());
        } else if (expression instanceof FirNamedArgumentExpression) {
            expression = ((FirNamedArgumentExpression) expression).getExpression();
        }
        if (expression == null) {
            return null;
        }
        FirConstExpression firConstExpression = expression instanceof FirConstExpression ? (FirConstExpression) expression : null;
        if (firConstExpression != null) {
            return firConstExpression.getValue();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) typeConstructorMarker : null;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        List<Pair<Name, SimpleTypeMarker>> valueClassProperties = getValueClassProperties(typeConstructorMarker);
        return valueClassProperties != null && ValueClassRepresentationKt.valueClassLoweringKind(this, valueClassProperties) == ValueClassKind.Inline;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        List<Pair<Name, SimpleTypeMarker>> valueClassProperties = getValueClassProperties(typeConstructorMarker);
        return valueClassProperties != null && ValueClassRepresentationKt.valueClassLoweringKind(this, valueClassProperties) == ValueClassKind.MultiField;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker) {
        ValueClassRepresentation<ConeSimpleKotlinType> valueClassRepresentation;
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FirRegularClass firRegularClass = toFirRegularClass(typeConstructorMarker);
        if (firRegularClass == null || (valueClassRepresentation = FirValueClassRepresentationKt.getValueClassRepresentation(firRegularClass)) == null) {
            return null;
        }
        return valueClassRepresentation.getUnderlyingPropertyNamesToTypes();
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean isInnerClass(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FirRegularClass firRegularClass = toFirRegularClass(typeConstructorMarker);
        return firRegularClass != null && firRegularClass.getStatus().isInner();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
        ConeKotlinType coneType;
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FirTypeRef firTypeRef = (FirTypeRef) CollectionsKt.getOrNull(((FirTypeParameter) ((ConeTypeParameterLookupTag) typeParameterMarker).getSymbol().getFir()).getBounds(), 0);
        return (firTypeRef == null || (coneType = FirTypeUtilsKt.getConeType(firTypeRef)) == null) ? get$session().getBuiltinTypes().getNullableAnyType().getType() : coneType;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ValueClassesUtilsKt.unsubstitutedUnderlyingTypeForInlineClass((ConeKotlinType) kotlinTypeMarker, get$session());
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ValueClassesUtilsKt.substitutedUnderlyingTypeForInlineClass((ConeKotlinType) kotlinTypeMarker, get$session(), this);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FqNameUnsafe classFqNameUnsafe = getClassFqNameUnsafe(typeConstructorMarker);
        if (classFqNameUnsafe != null) {
            return StandardNames.FqNames.fqNameToPrimitiveType.get(classFqNameUnsafe);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FqNameUnsafe classFqNameUnsafe = getClassFqNameUnsafe(typeConstructorMarker);
        if (classFqNameUnsafe != null) {
            return StandardNames.FqNames.arrayClassFqNameToPrimitiveType.get(classFqNameUnsafe);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        FqNameUnsafe classFqNameUnsafe = getClassFqNameUnsafe(typeConstructorMarker);
        return classFqNameUnsafe != null && classFqNameUnsafe.startsWith(StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE().shortName());
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        if (typeConstructorMarker instanceof ConeClassLikeLookupTag) {
            return ((ConeClassLikeLookupTag) typeConstructorMarker).getClassId().asSingleFqName().toUnsafe();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default Name getName(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return ((ConeTypeParameterLookupTag) typeParameterMarker).getName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean isReified(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((FirTypeParameter) ((ConeTypeParameterLookupTag) typeParameterMarker).getTypeParameterSymbol().getFir()).getIsReified();
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    default boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker) {
        ClassKind classKind;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        FirRegularClass firRegularClass = toFirRegularClass(typeConstructor(kotlinTypeMarker));
        if (firRegularClass == null || (classKind = firRegularClass.getClassKind()) == null) {
            return false;
        }
        return classKind == ClassKind.ANNOTATION_CLASS || classKind == ClassKind.INTERFACE;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return typeConstructorMarker instanceof ErrorTypeConstructor;
    }

    private default Void unknownConstructorError(TypeConstructorMarker typeConstructorMarker) {
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unknown type constructor: " + typeConstructorMarker.getClass(), null);
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments2 = kotlinIllegalArgumentExceptionWithAttachments;
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        exceptionAttachmentBuilder.withEntry(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, typeConstructorMarker, new Function1<TypeConstructorMarker, String>() { // from class: org.jetbrains.kotlin.fir.types.ConeTypeContext$unknownConstructorError$1$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(TypeConstructorMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.toString();
            }
        });
        FirExceptionUtilsKt.withFirLookupTagEntry(exceptionAttachmentBuilder, "constructorAsLookupTag", typeConstructorMarker instanceof ConeClassifierLookupTag ? (ConeClassifierLookupTag) typeConstructorMarker : null);
        kotlinIllegalArgumentExceptionWithAttachments2.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        final ConeSubstitutor.Empty emptySubstitutorByMap;
        ConeClassLikeType type2;
        FirClassLikeSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(type, "type");
        if (argumentsCount(type) == 0) {
            return TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
        }
        if (!(type instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ConeKotlinType coneKotlinType = (ConeKotlinType) type;
        FirClassLikeDeclaration firClassLikeDeclaration = (!(coneKotlinType instanceof ConeClassLikeType) || (symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) type).getLookupTag(), get$session())) == null) ? null : (FirClassLikeDeclaration) symbol.getFir();
        if (firClassLikeDeclaration instanceof FirTypeParameterRefsOwner) {
            List<Pair> listZip = CollectionsKt.zip(firClassLikeDeclaration.getTypeParameters(), coneKotlinType.getTypeArguments());
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
            for (Pair pair : listZip) {
                FirTypeParameterRef firTypeParameterRef = (FirTypeParameterRef) pair.component1();
                ConeTypeProjection coneTypeProjection = (ConeTypeProjection) pair.component2();
                FirTypeParameterSymbol symbol2 = firTypeParameterRef.getSymbol();
                ConeKotlinTypeProjection coneKotlinTypeProjection = coneTypeProjection instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) coneTypeProjection : null;
                if (coneKotlinTypeProjection == null || (type2 = coneKotlinTypeProjection.getType()) == null) {
                    type2 = get$session().getBuiltinTypes().getNullableAnyType().getType();
                }
                Pair pair2 = TuplesKt.to(symbol2, type2);
                linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
            }
            emptySubstitutorByMap = SubstitutorsKt.substitutorByMap(linkedHashMap, get$session());
        } else {
            emptySubstitutorByMap = ConeSubstitutor.Empty.INSTANCE;
        }
        return new TypeCheckerState.SupertypesPolicy.DoCustomTransform() { // from class: org.jetbrains.kotlin.fir.types.ConeTypeContext.substitutionSupertypePolicy.1
            @Override // org.jetbrains.kotlin.types.TypeCheckerState.SupertypesPolicy
            /* JADX INFO: renamed from: transformType */
            public SimpleTypeMarker mo7474transformType(TypeCheckerState state, KotlinTypeMarker type3) {
                Intrinsics.checkNotNullParameter(state, "state");
                Intrinsics.checkNotNullParameter(type3, "type");
                Object objLowerBoundIfFlexible = ConeTypeContext.this.lowerBoundIfFlexible(type3);
                if (!(objLowerBoundIfFlexible instanceof ConeKotlinType)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                TypeArgumentListMarker typeArgumentListMarkerSubstituteOrSelf = emptySubstitutorByMap.substituteOrSelf((ConeKotlinType) objLowerBoundIfFlexible);
                Intrinsics.checkNotNull(typeArgumentListMarkerSubstituteOrSelf, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.SimpleTypeMarker");
                return (SimpleTypeMarker) typeArgumentListMarkerSubstituteOrSelf;
            }
        };
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    default boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return kotlinTypeMarker instanceof ConeTypeVariableType;
    }
}
