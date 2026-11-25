package org.jetbrains.kotlin.resolve.calls.mpp;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.mpp.ClassLikeSymbolMarker;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.mpp.FunctionSymbolMarker;
import org.jetbrains.kotlin.mpp.PropertySymbolMarker;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeAliasSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeParameterSymbolMarker;
import org.jetbrains.kotlin.mpp.ValueParameterSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;

/* JADX INFO: compiled from: ExpectActualMatchingContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0002\u009a\u0001J\u0018\u0010x\u001a\u00020\u00052\u0006\u0010y\u001a\u00020\u00182\u0006\u0010z\u001a\u00020\u0018H&J \u0010{\u001a\u00020\u00052\u0006\u0010|\u001a\u00020\u00142\u0006\u0010}\u001a\u00020\u00142\u0006\u0010~\u001a\u00020\u007fH&J\u001d\u0010\u0080\u0001\u001a\u00020\u00052\b\u0010y\u001a\u0004\u0018\u00010\u00182\b\u0010z\u001a\u0004\u0018\u00010\u0018H&J4\u0010\u0081\u0001\u001a\u00030\u0082\u00012\r\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\u00132\r\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\u00132\n\u0010\u0085\u0001\u001a\u0005\u0018\u00010\u0082\u0001H&J\u001c\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u0007\u0010\u0088\u0001\u001a\u00020\u00022\u0007\u0010\u0089\u0001\u001a\u00020\u0002H\u0016J4\u0010\u008a\u0001\u001a\u00030\u0087\u00012\u0007\u0010\u0088\u0001\u001a\u00020\u00022\u001f\u0010\u008b\u0001\u001a\u001a\u0012\t\u0012\u0007\u0012\u0002\b\u00030\u008d\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00130\u008c\u0001H\u0016J\u0014\u0010\u008e\u0001\u001a\t\u0012\u0004\u0012\u00020\u001e0\u008f\u0001*\u00020bH&J\u001c\u0010\u0090\u0001\u001a\b\u0012\u0004\u0012\u00020\u00020\u0013*\u00020#2\u0007\u0010\u0091\u0001\u001a\u00020\u0005H&J\u0013\u0010\u0092\u0001\u001a\b\u0012\u0004\u0012\u00020[0\u0013*\u00020#H&J\u000f\u0010\u0093\u0001\u001a\u0004\u0018\u00010#*\u00020&H&J\u001c\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020\u00020\u0013*\u00020#2\u0007\u0010\u0095\u0001\u001a\u00020[H&J\r\u0010\u0096\u0001\u001a\u00020\u0005*\u00020\u001eH&J\r\u0010\u0097\u0001\u001a\u00020\u0005*\u00020#H&J\u0016\u0010\u0098\u0001\u001a\u00020\u0005*\u00020\u001e2\u0007\u0010\u0099\u0001\u001a\u00020#H&R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007R\u0014\u0010\f\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007R\u0012\u0010\u000e\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0007R\u0012\u0010\u0010\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0007R\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013*\u00020\u0002X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0013*\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0016\u0010!\u001a\u00020\"*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0016\u0010!\u001a\u00020\"*\u00020&X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010'R\u0016\u0010(\u001a\u00020)*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0016\u0010,\u001a\u00020\u0018*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0018\u0010/\u001a\u0004\u0018\u00010\u0018*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0018\u00102\u001a\u0004\u0018\u00010\u0018*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b3\u00101R\u0016\u00104\u001a\u00020\u0005*\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u0016\u00108\u001a\u00020\u0005*\u00020\u0002X¦\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0016\u0010;\u001a\u00020\u0005*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0016\u0010>\u001a\u00020\u0005*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0016\u0010@\u001a\u00020\u0005*\u00020AX¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010BR\u0016\u0010C\u001a\u00020\u0005*\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\bC\u00107R\u0016\u0010D\u001a\u00020\u0005*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bD\u0010=R\u0016\u0010E\u001a\u00020\u0005*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bE\u0010=R\u0016\u0010F\u001a\u00020\u0005*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\bF\u0010?R\u0016\u0010G\u001a\u00020\u0005*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bG\u0010=R\u0016\u0010H\u001a\u00020\u0005*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bH\u0010=R\u0016\u0010H\u001a\u00020\u0005*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\bH\u0010?R\u0016\u0010I\u001a\u00020\u0005*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\bI\u0010?R\u0016\u0010J\u001a\u00020\u0005*\u00020AX¦\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010BR\u0016\u0010K\u001a\u00020\u0005*\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\bK\u00107R\u0016\u0010L\u001a\u00020\u0005*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bL\u0010=R\u0016\u0010M\u001a\u00020\u0005*\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0016\u0010O\u001a\u00020\u0005*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bO\u0010=R\u0016\u0010P\u001a\u00020\u0005*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bP\u0010=R\u0016\u0010Q\u001a\u00020\u0005*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010?R\u0016\u0010R\u001a\u00020\u0005*\u00020AX¦\u0004¢\u0006\u0006\u001a\u0004\bR\u0010BR\u0016\u0010S\u001a\u00020\u0005*\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\bS\u00107R\u0018\u0010T\u001a\u0004\u0018\u00010U*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bV\u0010WR\u0018\u0010T\u001a\u0004\u0018\u00010U*\u00020XX¦\u0004¢\u0006\u0006\u001a\u0004\bV\u0010YR\u0016\u0010Z\u001a\u00020[*\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\\\u0010]R\u0016\u0010Z\u001a\u00020[*\u000205X¦\u0004¢\u0006\u0006\u001a\u0004\b\\\u0010^R\u0016\u0010_\u001a\u00020\u0018*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b`\u00101R\u0018\u0010a\u001a\u0004\u0018\u00010b*\u00020AX¦\u0004¢\u0006\u0006\u001a\u0004\bc\u0010dR\u001c\u0010e\u001a\b\u0012\u0004\u0012\u00020\u00180\u0013*\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\bf\u0010gR\u001c\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00190\u0013*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bi\u0010jR\u001c\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00190\u0013*\u00020XX¦\u0004¢\u0006\u0006\u001a\u0004\bi\u0010kR\u001e\u0010l\u001a\b\u0012\u0004\u0012\u0002050\u0013*\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bm\u0010jR\u001c\u0010l\u001a\b\u0012\u0004\u0012\u0002050\u0013*\u00020bX¦\u0004¢\u0006\u0006\u001a\u0004\bm\u0010nR\u0016\u0010o\u001a\u00020p*\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\bq\u0010rR\u0016\u0010s\u001a\u00020t*\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\bu\u0010vR\u0016\u0010s\u001a\u00020t*\u00020XX¦\u0004¢\u0006\u0006\u001a\u0004\bu\u0010wø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u009b\u0001À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "allowClassActualizationWithWiderVisibility", "", "getAllowClassActualizationWithWiderVisibility", "()Z", "allowTransitiveSupertypesActualization", "getAllowTransitiveSupertypesActualization", "enumConstructorsAreAlwaysCompatible", "getEnumConstructorsAreAlwaysCompatible", "innerClassesCapturesOuterTypeParameters", "getInnerClassesCapturesOuterTypeParameters", "shouldCheckAbsenceOfDefaultParamsInActual", "getShouldCheckAbsenceOfDefaultParamsInActual", "shouldCheckReturnTypesOfCallables", "getShouldCheckReturnTypesOfCallables", "annotations", "", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "getAnnotations", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Ljava/util/List;", "bounds", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;", "getBounds", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Ljava/util/List;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;", "getCallableId", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/name/CallableId;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;", "getClassId", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;)Lorg/jetbrains/kotlin/name/ClassId;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/ClassKind;", "defaultType", "getDefaultType", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "dispatchReceiverType", "getDispatchReceiverType", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "extensionReceiverType", "getExtensionReceiverType", "hasDefaultValue", "Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;", "getHasDefaultValue", "(Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;)Z", "hasSourceAnnotationsErased", "getHasSourceAnnotationsErased", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Z", "hasStableParameterNames", "getHasStableParameterNames", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Z", "isCompanion", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Z", "isConst", "Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Z", "isCrossinline", "isExpect", "isExternal", "isFun", "isInfix", "isInline", "isInner", "isLateinit", "isNoinline", "isOperator", "isReified", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Z", "isSuspend", "isTailrec", "isValue", "isVar", "isVararg", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Modality;", "Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Modality;", "parameterName", "Lorg/jetbrains/kotlin/name/Name;", "getParameterName", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "returnType", "getReturnType", "setter", "Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "getSetter", "(Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "superTypes", "getSuperTypes", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Ljava/util/List;", "typeParameters", "getTypeParameters", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Ljava/util/List;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Ljava/util/List;", "valueParameters", "getValueParameters", "(Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;)Ljava/util/List;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Lorg/jetbrains/kotlin/types/Variance;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "actualTypeIsSubtypeOfExpectType", "expectType", "actualType", "areAnnotationArgumentsEqual", "expectAnnotation", "actualAnnotation", "collectionArgumentsCompatibilityCheckStrategy", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "areCompatibleExpectActualTypes", "createExpectActualTypeParameterSubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "expectTypeParameters", "actualTypeParameters", "parentSubstitutor", "onMatchedMembers", "", "expectSymbol", "actualSymbol", "onMismatchedMembersFromClassScope", "actualSymbolsByIncompatibility", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "allOverriddenDeclarationsRecursive", "Lkotlin/sequences/Sequence;", "collectAllMembers", "isActualDeclaration", "collectEnumEntryNames", "expandToRegularClass", "getMembersForExpectClass", "name", "isAnnotationConstructor", "isNotSamInterface", "shouldSkipMatching", "containingExpectClass", "AnnotationCallInfo", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ExpectActualMatchingContext<T extends DeclarationSymbolMarker> extends TypeSystemContext {

    /* JADX INFO: compiled from: ExpectActualMatchingContext.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0012\u0010\f\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "", "annotationSymbol", "getAnnotationSymbol", "()Ljava/lang/Object;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "isOptIn", "", "()Z", "isRetentionSource", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface AnnotationCallInfo {
        Object getAnnotationSymbol();

        ClassId getClassId();

        boolean isOptIn();

        boolean isRetentionSource();
    }

    boolean actualTypeIsSubtypeOfExpectType(KotlinTypeMarker expectType, KotlinTypeMarker actualType);

    Sequence<CallableSymbolMarker> allOverriddenDeclarationsRecursive(FunctionSymbolMarker functionSymbolMarker);

    boolean areAnnotationArgumentsEqual(AnnotationCallInfo expectAnnotation, AnnotationCallInfo actualAnnotation, ExpectActualCollectionArgumentsCompatibilityCheckStrategy collectionArgumentsCompatibilityCheckStrategy);

    boolean areCompatibleExpectActualTypes(KotlinTypeMarker expectType, KotlinTypeMarker actualType);

    List<DeclarationSymbolMarker> collectAllMembers(RegularClassSymbolMarker regularClassSymbolMarker, boolean z);

    List<Name> collectEnumEntryNames(RegularClassSymbolMarker regularClassSymbolMarker);

    TypeSubstitutorMarker createExpectActualTypeParameterSubstitutor(List<? extends TypeParameterSymbolMarker> expectTypeParameters, List<? extends TypeParameterSymbolMarker> actualTypeParameters, TypeSubstitutorMarker parentSubstitutor);

    RegularClassSymbolMarker expandToRegularClass(TypeAliasSymbolMarker typeAliasSymbolMarker);

    default boolean getAllowClassActualizationWithWiderVisibility() {
        return false;
    }

    default boolean getAllowTransitiveSupertypesActualization() {
        return false;
    }

    List<AnnotationCallInfo> getAnnotations(DeclarationSymbolMarker declarationSymbolMarker);

    List<KotlinTypeMarker> getBounds(TypeParameterSymbolMarker typeParameterSymbolMarker);

    CallableId getCallableId(CallableSymbolMarker callableSymbolMarker);

    ClassId getClassId(RegularClassSymbolMarker regularClassSymbolMarker);

    ClassId getClassId(TypeAliasSymbolMarker typeAliasSymbolMarker);

    ClassKind getClassKind(RegularClassSymbolMarker regularClassSymbolMarker);

    KotlinTypeMarker getDefaultType(RegularClassSymbolMarker regularClassSymbolMarker);

    KotlinTypeMarker getDispatchReceiverType(CallableSymbolMarker callableSymbolMarker);

    default boolean getEnumConstructorsAreAlwaysCompatible() {
        return false;
    }

    KotlinTypeMarker getExtensionReceiverType(CallableSymbolMarker callableSymbolMarker);

    boolean getHasDefaultValue(ValueParameterSymbolMarker valueParameterSymbolMarker);

    boolean getHasSourceAnnotationsErased(DeclarationSymbolMarker declarationSymbolMarker);

    boolean getHasStableParameterNames(CallableSymbolMarker callableSymbolMarker);

    default boolean getInnerClassesCapturesOuterTypeParameters() {
        return true;
    }

    List<DeclarationSymbolMarker> getMembersForExpectClass(RegularClassSymbolMarker regularClassSymbolMarker, Name name2);

    Modality getModality(CallableSymbolMarker callableSymbolMarker);

    Modality getModality(ClassLikeSymbolMarker classLikeSymbolMarker);

    Name getParameterName(TypeParameterSymbolMarker typeParameterSymbolMarker);

    Name getParameterName(ValueParameterSymbolMarker valueParameterSymbolMarker);

    KotlinTypeMarker getReturnType(CallableSymbolMarker callableSymbolMarker);

    FunctionSymbolMarker getSetter(PropertySymbolMarker propertySymbolMarker);

    boolean getShouldCheckAbsenceOfDefaultParamsInActual();

    boolean getShouldCheckReturnTypesOfCallables();

    List<KotlinTypeMarker> getSuperTypes(RegularClassSymbolMarker regularClassSymbolMarker);

    List<TypeParameterSymbolMarker> getTypeParameters(CallableSymbolMarker callableSymbolMarker);

    List<TypeParameterSymbolMarker> getTypeParameters(ClassLikeSymbolMarker classLikeSymbolMarker);

    List<ValueParameterSymbolMarker> getValueParameters(FunctionSymbolMarker functionSymbolMarker);

    Variance getVariance(TypeParameterSymbolMarker typeParameterSymbolMarker);

    Visibility getVisibility(CallableSymbolMarker callableSymbolMarker);

    Visibility getVisibility(ClassLikeSymbolMarker classLikeSymbolMarker);

    boolean isAnnotationConstructor(CallableSymbolMarker callableSymbolMarker);

    boolean isCompanion(RegularClassSymbolMarker regularClassSymbolMarker);

    boolean isConst(PropertySymbolMarker propertySymbolMarker);

    boolean isCrossinline(ValueParameterSymbolMarker valueParameterSymbolMarker);

    boolean isExpect(CallableSymbolMarker callableSymbolMarker);

    boolean isExternal(CallableSymbolMarker callableSymbolMarker);

    boolean isFun(RegularClassSymbolMarker regularClassSymbolMarker);

    boolean isInfix(CallableSymbolMarker callableSymbolMarker);

    boolean isInline(CallableSymbolMarker callableSymbolMarker);

    boolean isInline(RegularClassSymbolMarker regularClassSymbolMarker);

    boolean isInner(RegularClassSymbolMarker regularClassSymbolMarker);

    boolean isLateinit(PropertySymbolMarker propertySymbolMarker);

    boolean isNoinline(ValueParameterSymbolMarker valueParameterSymbolMarker);

    boolean isNotSamInterface(RegularClassSymbolMarker regularClassSymbolMarker);

    boolean isOperator(CallableSymbolMarker callableSymbolMarker);

    boolean isReified(TypeParameterSymbolMarker typeParameterSymbolMarker);

    boolean isSuspend(CallableSymbolMarker callableSymbolMarker);

    boolean isTailrec(CallableSymbolMarker callableSymbolMarker);

    boolean isValue(RegularClassSymbolMarker regularClassSymbolMarker);

    boolean isVar(PropertySymbolMarker propertySymbolMarker);

    boolean isVararg(ValueParameterSymbolMarker valueParameterSymbolMarker);

    default void onMatchedMembers(DeclarationSymbolMarker expectSymbol, DeclarationSymbolMarker actualSymbol) {
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
    }

    default void onMismatchedMembersFromClassScope(DeclarationSymbolMarker expectSymbol, Map<ExpectActualCompatibility.Incompatible<?>, ? extends List<? extends DeclarationSymbolMarker>> actualSymbolsByIncompatibility) {
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        Intrinsics.checkNotNullParameter(actualSymbolsByIncompatibility, "actualSymbolsByIncompatibility");
    }

    boolean shouldSkipMatching(CallableSymbolMarker callableSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker);

    default List<ValueParameterSymbolMarker> getValueParameters(CallableSymbolMarker callableSymbolMarker) {
        List<ValueParameterSymbolMarker> valueParameters;
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        FunctionSymbolMarker functionSymbolMarker = callableSymbolMarker instanceof FunctionSymbolMarker ? (FunctionSymbolMarker) callableSymbolMarker : null;
        return (functionSymbolMarker == null || (valueParameters = getValueParameters(functionSymbolMarker)) == null) ? CollectionsKt.emptyList() : valueParameters;
    }
}
