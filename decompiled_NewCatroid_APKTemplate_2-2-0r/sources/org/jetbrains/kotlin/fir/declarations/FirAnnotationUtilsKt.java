package org.jetbrains.kotlin.fir.declarations;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirAnnotationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0014\u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015\u001a\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u000b*\u00020\u00102\u0006\u0010\u0017\u001a\u00020\t\u001a\u0012\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0010H\u0002\u001a\"\u0010\u0019\u001a\u0004\u0018\u00010\u0010*\b\u0012\u0004\u0012\u00020\u00100\u00152\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0010*\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a2\u0010\u0019\u001a\u0004\u0018\u00010\u0010\"\f\b\u0000\u0010\u001e*\u00020\u001d*\u00020\u001f*\n\u0012\u0006\b\u0001\u0012\u0002H\u001e0 2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a(\u0010!\u001a\u0004\u0018\u00010\u0010*\b\u0012\u0004\u0012\u00020\u00100\u00152\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040#2\u0006\u0010\u001b\u001a\u00020\u001c\u001a&\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015*\b\u0012\u0004\u0012\u00020\u00100\u00152\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a \u0010$\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015*\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0019\u0010%\u001a\u0004\u0018\u00010\u000f*\u00020\u00102\u0006\u0010\u0017\u001a\u00020\t¢\u0006\u0002\u0010&\u001a \u0010'\u001a\u0004\u0018\u00010\u0007*\u00020\u001d2\u0006\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u0002\u001a\u0014\u0010)\u001a\u0004\u0018\u00010**\u00020\u00102\u0006\u0010\u0017\u001a\u00020\t\u001a$\u0010+\u001a\u0004\u0018\u0001H,\"\u0006\b\u0000\u0010,\u0018\u0001*\u00020\u00102\u0006\u0010\u0017\u001a\u00020\tH\u0082\b¢\u0006\u0002\u0010-\u001a\u0014\u0010.\u001a\u0004\u0018\u00010\u0007*\u00020\u00102\u0006\u0010\u0017\u001a\u00020\t\u001a\u001a\u0010/\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0015*\u00020\u00102\u0006\u0010\u0017\u001a\u00020\t\u001a\f\u00100\u001a\u0004\u0018\u00010**\u000201\u001a \u00102\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u00100\u00152\u0006\u00103\u001a\u0002042\u0006\u0010\u001b\u001a\u00020\u001c\u001a \u00102\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u00100\u00152\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u001a\u00102\u001a\u00020\u000f*\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u001a\u00102\u001a\u00020\u000f*\u00020\u001f2\u0006\u00103\u001a\u0002042\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u001a\u00102\u001a\u00020\u000f*\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u001e\u00102\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030 2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a \u00105\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u00100\u00152\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u001a\u00105\u001a\u00020\u000f*\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0015\u00106\u001a\u00020\u000f*\u0002072\u0006\u0010\u001b\u001a\u00020\u001cH\u0086\b\u001a\u0012\u00108\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c\u001aF\u00109\u001a\u00020:\"\u0004\b\u0000\u0010,*\b\u0012\u0004\u0012\u00020\u00100\u00152\u0006\u0010\u001a\u001a\u00020\u00042\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H,0<2\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u0002H,0>H\u0086\bø\u0001\u0000\u001a\u001e\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015*\b\u0012\u0004\u0012\u00020\u00100\u00152\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0018\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015*\u00020\u001d2\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0016\u0010@\u001a\u0004\u0018\u00010A*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a\u0014\u0010B\u001a\u0004\u0018\u00010\u0004*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0014\u0010C\u001a\u0004\u0018\u00010\u0004*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0018\u0010D\u001a\b\u0012\u0002\b\u0003\u0018\u00010E*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0014\u0010F\u001a\u0004\u0018\u000104*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0016\u0010G\u001a\u0004\u0018\u00010H*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a\u0016\u0010I\u001a\u0004\u0018\u00010H*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a\u0010\u0010J\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015*\u00020\u000b\u001a\u0018\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\" \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\n\u001a\u0004\u0018\u00010\t*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r\"\u0015\u0010\u000e\u001a\u00020\u000f*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006L"}, d2 = {"DEFAULT_USE_SITE_TARGETS", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "LOW_PRIORITY_IN_OVERLOAD_RESOLUTION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "USE_SITE_TARGET_NAME_MAP", "", "", "sourceName", "Lorg/jetbrains/kotlin/name/Name;", "callableNameOfMetaAnnotationArgument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getCallableNameOfMetaAnnotationArgument", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Lorg/jetbrains/kotlin/name/Name;", "resolved", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getResolved", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)Z", "hasLowPriorityAnnotation", "annotations", "", "findArgumentByName", "name", "findUseSiteTargets", "getAnnotationByClassId", "classId", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getAnnotationByClassIds", "classIds", "", "getAnnotationsByClassId", "getBooleanArgument", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;Lorg/jetbrains/kotlin/name/Name;)Ljava/lang/Boolean;", "getJvmNameFromAnnotation", "target", "getKClassArgument", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getPrimitiveArgumentValue", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;Lorg/jetbrains/kotlin/name/Name;)Ljava/lang/Object;", "getStringArgument", "getStringArrayArgument", "getTargetType", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "hasAnnotation", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "hasAnnotationSafe", "hasJvmFieldAnnotation", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "isJvmFieldAnnotation", "mapAnnotationsWithClassIdTo", "", FirebaseAnalytics.Param.DESTINATION, "", "func", "Lkotlin/Function1;", "nonSourceAnnotations", "toAnnotationClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "toAnnotationClassId", "toAnnotationClassIdSafe", "toAnnotationClassLikeSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "toAnnotationClassLikeType", "toAnnotationLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "toAnnotationLookupTagSafe", "unwrapVarargValue", "useSiteTargetsFromMetaAnnotation", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationUtilsKt {
    private static final Set<AnnotationUseSiteTarget> DEFAULT_USE_SITE_TARGETS;
    private static final ClassId LOW_PRIORITY_IN_OVERLOAD_RESOLUTION_CLASS_ID;
    private static final Map<String, Set<AnnotationUseSiteTarget>> USE_SITE_TARGET_NAME_MAP;
    private static final Name sourceName;

    public static final ConeClassLikeType toAnnotationClassLikeType(FirAnnotation firAnnotation, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef());
        ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
        if (coneClassLikeType != null) {
            return TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, session, null, 2, null);
        }
        return null;
    }

    private static final ConeClassLikeLookupTag toAnnotationLookupTag(FirAnnotation firAnnotation, FirSession firSession) {
        ConeClassLikeType annotationClassLikeType = toAnnotationClassLikeType(firAnnotation, firSession);
        if (annotationClassLikeType != null) {
            return annotationClassLikeType.getLookupTag();
        }
        return null;
    }

    private static final ConeClassLikeLookupTag toAnnotationLookupTagSafe(FirAnnotation firAnnotation, FirSession firSession) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType == null || (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, firSession, null, 2, null)) == null) {
            return null;
        }
        return coneClassLikeTypeFullyExpandedType$default.getLookupTag();
    }

    public static final ClassId toAnnotationClassId(FirAnnotation firAnnotation, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeClassLikeLookupTag annotationLookupTag = toAnnotationLookupTag(firAnnotation, session);
        if (annotationLookupTag != null) {
            return annotationLookupTag.getClassId();
        }
        return null;
    }

    public static final ClassId toAnnotationClassIdSafe(FirAnnotation firAnnotation, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeClassLikeLookupTag annotationLookupTagSafe = toAnnotationLookupTagSafe(firAnnotation, session);
        if (annotationLookupTagSafe != null) {
            return annotationLookupTagSafe.getClassId();
        }
        return null;
    }

    public static final FirClassLikeSymbol<?> toAnnotationClassLikeSymbol(FirAnnotation firAnnotation, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeClassLikeLookupTag annotationLookupTag = toAnnotationLookupTag(firAnnotation, session);
        if (annotationLookupTag != null) {
            return LookupTagUtilsKt.toSymbol(annotationLookupTag, session);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirRegularClass toAnnotationClass(FirAnnotation firAnnotation, FirSession firSession) {
        FirClassLikeSymbol<?> annotationClassLikeSymbol = toAnnotationClassLikeSymbol(firAnnotation, firSession);
        FirClassLikeDeclaration firClassLikeDeclaration = annotationClassLikeSymbol != null ? (FirClassLikeDeclaration) annotationClassLikeSymbol.getFir() : null;
        if (firClassLikeDeclaration instanceof FirRegularClass) {
            return (FirRegularClass) firClassLikeDeclaration;
        }
        return null;
    }

    private static final Name getCallableNameOfMetaAnnotationArgument(FirExpression firExpression) {
        FirCallableSymbol resolvedCallableSymbol$default;
        CallableId callableId;
        FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
        if (firQualifiedAccessExpression == null || (resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null)) == null || (callableId = resolvedCallableSymbol$default.getCallableId()) == null) {
            return null;
        }
        return callableId.getCallableName();
    }

    static {
        Name nameIdentifier = Name.identifier("SOURCE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        sourceName = nameIdentifier;
        Map<String, Set<AnnotationUseSiteTarget>> mapMapOf = MapsKt.mapOf(TuplesKt.to("FIELD", SetsKt.setOf((Object[]) new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.FIELD, AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD})), TuplesKt.to("FILE", SetsKt.setOf(AnnotationUseSiteTarget.FILE)), TuplesKt.to("PROPERTY", SetsKt.setOf(AnnotationUseSiteTarget.PROPERTY)), TuplesKt.to("PROPERTY_GETTER", SetsKt.setOf(AnnotationUseSiteTarget.PROPERTY_GETTER)), TuplesKt.to("PROPERTY_SETTER", SetsKt.setOf(AnnotationUseSiteTarget.PROPERTY_SETTER)), TuplesKt.to("VALUE_PARAMETER", SetsKt.setOf((Object[]) new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER, AnnotationUseSiteTarget.RECEIVER, AnnotationUseSiteTarget.SETTER_PARAMETER})));
        USE_SITE_TARGET_NAME_MAP = mapMapOf;
        Collection<Set<AnnotationUseSiteTarget>> collectionValues = mapMapOf.values();
        Set setEmptySet = SetsKt.emptySet();
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            setEmptySet = SetsKt.plus(setEmptySet, (Iterable) it.next());
        }
        DEFAULT_USE_SITE_TARGETS = SetsKt.minus(setEmptySet, (Iterable) SetsKt.setOf(AnnotationUseSiteTarget.FILE));
        LOW_PRIORITY_IN_OVERLOAD_RESOLUTION_CLASS_ID = new ClassId(new FqName("kotlin.internal"), Name.identifier("LowPriorityInOverloadResolution"));
    }

    public static final List<FirAnnotation> nonSourceAnnotations(List<? extends FirAnnotation> list, FirSession session) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            FirRegularClass annotationClass = toAnnotationClass((FirAnnotation) obj, session);
            if (annotationClass != null) {
                List<FirAnnotation> annotations = annotationClass.getAnnotations();
                if (!(annotations instanceof Collection) || !annotations.isEmpty()) {
                    Iterator<T> it = annotations.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            FirAnnotation firAnnotation = (FirAnnotation) it.next();
                            if (Intrinsics.areEqual(toAnnotationClassId(firAnnotation, session), StandardClassIds.Annotations.INSTANCE.getRetention())) {
                                FirExpression firExpressionFindArgumentByName = findArgumentByName(firAnnotation, StandardClassIds.Annotations.ParameterNames.INSTANCE.getRetentionValue());
                                if (Intrinsics.areEqual(firExpressionFindArgumentByName != null ? getCallableNameOfMetaAnnotationArgument(firExpressionFindArgumentByName) : null, sourceName)) {
                                    break;
                                }
                            }
                        }
                    }
                }
                arrayList.add(obj);
                break;
            }
        }
        return arrayList;
    }

    public static final List<FirAnnotation> nonSourceAnnotations(FirAnnotationContainer firAnnotationContainer, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return nonSourceAnnotations(firAnnotationContainer.getAnnotations(), session);
    }

    public static final boolean hasJvmFieldAnnotation(FirProperty firProperty, FirSession session) {
        List<FirAnnotation> annotations;
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirBackingField backingField = firProperty.getBackingField();
        if (backingField == null || (annotations = backingField.getAnnotations()) == null) {
            return false;
        }
        List<FirAnnotation> list = annotations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (isJvmFieldAnnotation((FirAnnotation) it.next(), session)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isJvmFieldAnnotation(FirAnnotation firAnnotation, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return Intrinsics.areEqual(toAnnotationClassId(firAnnotation, session), StandardClassIds.Annotations.INSTANCE.getJvmField());
    }

    public static final Set<AnnotationUseSiteTarget> useSiteTargetsFromMetaAnnotation(FirAnnotation firAnnotation, FirSession session) {
        List<FirAnnotation> annotations;
        Object next;
        Set<AnnotationUseSiteTarget> setFindUseSiteTargets;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirRegularClass annotationClass = toAnnotationClass(firAnnotation, session);
        if (annotationClass != null && (annotations = annotationClass.getAnnotations()) != null) {
            Iterator<T> it = annotations.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(toAnnotationClassIdSafe((FirAnnotation) next, session), StandardClassIds.Annotations.INSTANCE.getTarget()));
            FirAnnotation firAnnotation2 = (FirAnnotation) next;
            if (firAnnotation2 != null && (setFindUseSiteTargets = findUseSiteTargets(firAnnotation2)) != null) {
                return setFindUseSiteTargets;
            }
        }
        return DEFAULT_USE_SITE_TARGETS;
    }

    private static final Set<AnnotationUseSiteTarget> findUseSiteTargets(FirAnnotation firAnnotation) {
        List<FirExpression> listUnwrapAndFlattenArgument;
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        if (firAnnotation instanceof FirAnnotationCall) {
            Iterator<FirExpression> it = ((FirAnnotationCall) firAnnotation).getArgumentList().getArguments().iterator();
            while (it.hasNext()) {
                Iterator<T> it2 = FirExpressionUtilKt.unwrapAndFlattenArgument(it.next()).iterator();
                while (it2.hasNext()) {
                    findUseSiteTargets$lambda$7$addIfMatching(setCreateSetBuilder, (FirExpression) it2.next());
                }
            }
        } else {
            FirExpression firExpression = firAnnotation.getArgumentMapping().getMapping().get(StandardClassIds.Annotations.ParameterNames.INSTANCE.getTargetAllowedTargets());
            if (firExpression != null && (listUnwrapAndFlattenArgument = FirExpressionUtilKt.unwrapAndFlattenArgument(firExpression)) != null) {
                Iterator<T> it3 = listUnwrapAndFlattenArgument.iterator();
                while (it3.hasNext()) {
                    findUseSiteTargets$lambda$7$addIfMatching(setCreateSetBuilder, (FirExpression) it3.next());
                }
            }
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    private static final void findUseSiteTargets$lambda$7$addIfMatching(Set<AnnotationUseSiteTarget> set, FirExpression firExpression) {
        Set<AnnotationUseSiteTarget> set2;
        if (firExpression instanceof FirQualifiedAccessExpression) {
            FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(((FirQualifiedAccessExpression) firExpression).getCalleeReference(), false, 1, null);
            if (resolvedCallableSymbol$default == null) {
                return;
            }
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) resolvedCallableSymbol$default);
            if (!Intrinsics.areEqual(coneClassLikeLookupTagContainingClassLookupTag != null ? coneClassLikeLookupTagContainingClassLookupTag.getClassId() : null, StandardClassIds.INSTANCE.getAnnotationTarget()) || (set2 = USE_SITE_TARGET_NAME_MAP.get(resolvedCallableSymbol$default.getCallableId().getCallableName().getIdentifier())) == null) {
                return;
            }
            set.addAll(set2);
        }
    }

    public static final boolean hasAnnotation(FirDeclaration firDeclaration, ConeClassLikeType type, FirSession session) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(session, "session");
        return hasAnnotation(firDeclaration.getAnnotations(), type, session);
    }

    public static final boolean hasAnnotation(FirDeclaration firDeclaration, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return hasAnnotation(firDeclaration.getAnnotations(), classId, session);
    }

    public static final boolean hasAnnotationSafe(FirDeclaration firDeclaration, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return hasAnnotationSafe(firDeclaration.getAnnotations(), classId, session);
    }

    public static final boolean hasAnnotation(FirBasedSymbol<?> firBasedSymbol, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return hasAnnotation(firBasedSymbol.getResolvedAnnotationsWithClassIds(), classId, session);
    }

    public static final boolean hasAnnotation(FirAnnotationContainer firAnnotationContainer, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return hasAnnotation(firAnnotationContainer.getAnnotations(), classId, session);
    }

    public static final boolean hasAnnotation(List<? extends FirAnnotation> list, ConeClassLikeType type, FirSession session) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(session, "session");
        List<? extends FirAnnotation> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(toAnnotationClassLikeType((FirAnnotation) it.next(), session), type)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean hasAnnotation(List<? extends FirAnnotation> list, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        List<? extends FirAnnotation> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(toAnnotationClassId((FirAnnotation) it.next(), session), classId)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean hasAnnotationSafe(List<? extends FirAnnotation> list, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        List<? extends FirAnnotation> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(toAnnotationClassIdSafe((FirAnnotation) it.next(), session), classId)) {
                return true;
            }
        }
        return false;
    }

    public static final <D extends FirDeclaration & FirAnnotationContainer> FirAnnotation getAnnotationByClassId(FirBasedSymbol<? extends D> firBasedSymbol, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return getAnnotationByClassId(firBasedSymbol.getFir(), classId, session);
    }

    public static final FirAnnotation getAnnotationByClassId(FirAnnotationContainer firAnnotationContainer, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return getAnnotationByClassId(firAnnotationContainer.getAnnotations(), classId, session);
    }

    public static final FirAnnotation getAnnotationByClassId(List<? extends FirAnnotation> list, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return (FirAnnotation) CollectionsKt.firstOrNull((List) getAnnotationsByClassId(list, classId, session));
    }

    public static final List<FirAnnotation> getAnnotationsByClassId(FirAnnotationContainer firAnnotationContainer, ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        return getAnnotationsByClassId(firAnnotationContainer.getAnnotations(), classId, session);
    }

    public static final List<FirAnnotation> getAnnotationsByClassId(List<? extends FirAnnotation> list, ClassId classId, FirSession session) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            FirTypeRef annotationTypeRef = ((FirAnnotation) obj).getAnnotationTypeRef();
            ClassId classId2 = null;
            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (coneClassLikeType != null && (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, session, null, 2, null)) != null && (lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag()) != null) {
                classId2 = lookupTag.getClassId();
            }
            if (Intrinsics.areEqual(classId2, classId)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final FirAnnotation getAnnotationByClassIds(List<? extends FirAnnotation> list, Collection<ClassId> classIds, FirSession session) {
        Object classId;
        Object next;
        Collection<ClassId> collection;
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(classIds, "classIds");
        Intrinsics.checkNotNullParameter(session, "session");
        Iterator<T> it = list.iterator();
        do {
            classId = null;
            if (it.hasNext()) {
                next = it.next();
                collection = classIds;
                FirTypeRef annotationTypeRef = ((FirAnnotation) next).getAnnotationTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                if (!(type instanceof ConeClassLikeType)) {
                    type = null;
                }
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                if (coneClassLikeType != null && (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, session, null, 2, null)) != null && (lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag()) != null) {
                    classId = lookupTag.getClassId();
                }
            }
            return (FirAnnotation) classId;
        } while (!CollectionsKt.contains(collection, classId));
        classId = next;
        return (FirAnnotation) classId;
    }

    public static final <T> void mapAnnotationsWithClassIdTo(List<? extends FirAnnotation> list, ClassId classId, Collection<T> destination, Function1<? super FirAnnotation, ? extends T> func) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(func, "func");
        for (FirAnnotation firAnnotation : list) {
            FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
            ClassId classId2 = null;
            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                classId2 = lookupTag.getClassId();
            }
            if (Intrinsics.areEqual(classId2, classId)) {
                destination.add(func.invoke(firAnnotation));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<FirExpression> unwrapVarargValue(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        if (firExpression instanceof FirVarargArgumentsExpression) {
            return ((FirVarargArgumentsExpression) firExpression).getArguments();
        }
        if (!(firExpression instanceof FirArrayLiteral)) {
            return CollectionsKt.listOf(firExpression);
        }
        return ((FirCall) firExpression).getArgumentList().getArguments();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirExpression findArgumentByName(FirAnnotation firAnnotation, Name name2) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        FirExpression firExpression = firAnnotation.getArgumentMapping().getMapping().get(name2);
        if (firExpression != null) {
            return firExpression;
        }
        if (!(firAnnotation instanceof FirAnnotationCall)) {
            return null;
        }
        FirCall firCall = (FirCall) firAnnotation;
        for (FirExpression firExpression2 : firCall.getArgumentList().getArguments()) {
            if (firExpression2 instanceof FirNamedArgumentExpression) {
                FirNamedArgumentExpression firNamedArgumentExpression = (FirNamedArgumentExpression) firExpression2;
                if (Intrinsics.areEqual(firNamedArgumentExpression.getName(), name2)) {
                    return firNamedArgumentExpression.getExpression();
                }
            }
        }
        return (FirExpression) CollectionsKt.singleOrNull((List) firCall.getArgumentList().getArguments());
    }

    public static final List<String> getStringArrayArgument(FirAnnotation firAnnotation, Name name2) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        FirExpression firExpressionFindArgumentByName = findArgumentByName(firAnnotation, name2);
        FirArrayLiteral firArrayLiteral = firExpressionFindArgumentByName instanceof FirArrayLiteral ? (FirArrayLiteral) firExpressionFindArgumentByName : null;
        if (firArrayLiteral == null) {
            return null;
        }
        List<FirExpression> arguments = firArrayLiteral.getArgumentList().getArguments();
        ArrayList arrayList = new ArrayList();
        for (FirExpression firExpression : arguments) {
            FirConstExpression firConstExpression = firExpression instanceof FirConstExpression ? (FirConstExpression) firExpression : null;
            Object value = firConstExpression != null ? firConstExpression.getValue() : null;
            String str = value instanceof String ? (String) value : null;
            if (str != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public static final ConeKotlinType getKClassArgument(FirAnnotation firAnnotation, Name name2) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        FirExpression firExpressionFindArgumentByName = findArgumentByName(firAnnotation, name2);
        FirGetClassCall firGetClassCall = firExpressionFindArgumentByName instanceof FirGetClassCall ? (FirGetClassCall) firExpressionFindArgumentByName : null;
        if (firGetClassCall == null) {
            return null;
        }
        return getTargetType(firGetClassCall);
    }

    public static final ConeKotlinType getTargetType(FirGetClassCall firGetClassCall) {
        Intrinsics.checkNotNullParameter(firGetClassCall, "<this>");
        ConeTypeProjection coneTypeProjection = (ConeTypeProjection) ArraysKt.getOrNull(FirTypeUtilsKt.getConeType(firGetClassCall.getTypeRef()).getTypeArguments(), 0);
        if (coneTypeProjection != null) {
            return ConeTypeProjectionKt.getType(coneTypeProjection);
        }
        return null;
    }

    public static /* synthetic */ String getJvmNameFromAnnotation$default(FirAnnotationContainer firAnnotationContainer, FirSession firSession, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        return getJvmNameFromAnnotation(firAnnotationContainer, firSession, annotationUseSiteTarget);
    }

    public static final String getJvmNameFromAnnotation(FirAnnotationContainer firAnnotationContainer, FirSession session, AnnotationUseSiteTarget annotationUseSiteTarget) {
        String str;
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Iterator<T> it = getAnnotationsByClassId(firAnnotationContainer, StandardClassIds.Annotations.INSTANCE.getJvmName(), session).iterator();
        do {
            str = null;
            if (!it.hasNext()) {
                break;
            }
            FirAnnotation firAnnotation = (FirAnnotation) it.next();
            String stringArgument = getStringArgument(firAnnotation, StandardNames.NAME);
            if (stringArgument != null && (annotationUseSiteTarget == null || firAnnotation.getUseSiteTarget() == annotationUseSiteTarget)) {
                str = stringArgument;
            }
        } while (str == null);
        return str;
    }

    public static final boolean getResolved(FirAnnotation firAnnotation) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        if (!(firAnnotation.getAnnotationTypeRef() instanceof FirResolvedTypeRef)) {
            return false;
        }
        if (!(firAnnotation instanceof FirAnnotationCall)) {
            return true;
        }
        FirAnnotationCall firAnnotationCall = (FirAnnotationCall) firAnnotation;
        return (firAnnotationCall.getCalleeReference() instanceof FirResolvedNamedReference) || (firAnnotationCall.getCalleeReference() instanceof FirErrorNamedReference);
    }

    public static final boolean hasLowPriorityAnnotation(List<? extends FirAnnotation> annotations) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        List<? extends FirAnnotation> list = annotations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            FirTypeRef annotationTypeRef = ((FirAnnotation) it.next()).getAnnotationTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) (type instanceof ConeClassLikeType ? type : null);
            if ((coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? false : Intrinsics.areEqual(lookupTag.getClassId(), LOW_PRIORITY_IN_OVERLOAD_RESOLUTION_CLASS_ID)) {
                return true;
            }
        }
        return false;
    }

    public static final Boolean getBooleanArgument(FirAnnotation firAnnotation, Name name2) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        FirExpression firExpressionFindArgumentByName = findArgumentByName(firAnnotation, name2);
        if (firExpressionFindArgumentByName == null) {
            return null;
        }
        FirConstExpression firConstExpression = firExpressionFindArgumentByName instanceof FirConstExpression ? (FirConstExpression) firExpressionFindArgumentByName : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        return (Boolean) (value instanceof Boolean ? value : null);
    }

    public static final String getStringArgument(FirAnnotation firAnnotation, Name name2) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        FirExpression firExpressionFindArgumentByName = findArgumentByName(firAnnotation, name2);
        if (firExpressionFindArgumentByName == null) {
            return null;
        }
        FirConstExpression firConstExpression = firExpressionFindArgumentByName instanceof FirConstExpression ? (FirConstExpression) firExpressionFindArgumentByName : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        return (String) (value instanceof String ? value : null);
    }
}
